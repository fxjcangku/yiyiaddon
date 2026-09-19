package com.yiyiaddon.feature.bonemeal.ui;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.bonemeal.AutoBoneMealModule;
import com.yiyiaddon.feature.bonemeal.config.BonemealSettings;
import com.yiyiaddon.feature.bonemeal.config.BonemealTexts;
import com.yiyiaddon.feature.bonemeal.ui.console.BonemealOverviewPage;
import com.yiyiaddon.feature.bonemeal.ui.console.BonemealSettingsPage;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.console.ConsoleHeaderBar;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自动骨粉控制台：把 21 项设置按旧项目原 3 个设置组 + 基础参数拆成页签
 * （概览 / 基础参数 / 目标方块 / 防作弊绕过 / ESP渲染）。
 *
 * <p><b>骨架逐字照既有控制台</b>（发包秒破 / 飞行绕过 / 传送；同源件
 * {@link com.yiyiaddon.ui.console.ConsoleWidgets} 直接复用）：窗口标题、模块说明副标题、
 * 顶栏 {@link ConsoleHeaderBar}（状态文字 + 快捷键徽章 + 模块开关）、状态条（两行六格）、
 * 页签行、可整体替换的 {@link Body}、{@link #reload()} 重建、{@link #switchTab} 延到下一 tick、
 * 每秒自动刷新（只在概览页整页重建）、底部「刷新 / 关闭」、tooltip 悬浮层 —— 全部同一套写法与同一套数值。</p>
 *
 * <p><b>设置项一字未改</b>：每行都来自 {@link BonemealSettings} 的同名字段，名称、描述、默认值与取值域
 * 逐字取自旧项目 {@code bonemeal/AutoBoneMeal.java:59-243} 的设置声明；控制台只负责
 * 「搬到哪一页、按什么顺序排」，不新增设置项（旧项目没有面板动作，故本模块也没有任何「手动催熟」按钮）。</p>
 */
public final class BonemealConsoleScreen extends PanelScreen implements ConsoleHost {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.bonemeal.console";

    /** 概览页自动刷新间隔：20 tick 一次（其余控制台同口径） */
    private static final int AUTO_REFRESH_TICKS = 20;

    private static final float LINE_GAP = 6f;
    private static final float TEXT_SIZE = 11f;

    /** tooltip 悬浮层（本项目通用控件没有 tooltip 原语，面板内自绘） */
    private static final float TIP_SIZE = 10f;
    private static final float TIP_LINE = 12f;
    private static final float TIP_PAD = 6f;
    private static final float TIP_RADIUS = 6f;
    private static final float TIP_MAX_WIDTH = 320f;

    /** tooltip 文字基准色：深色主题白字；浅色主题用主文字色 */
    private static int tipColor(ClickGuiThemeColors tc) {
        return tc != null && !tc.dark ? tc.primaryText : 0xFFFFFF;
    }

    /** 页签：一屏只显示一类内容（页签名 = 旧项目 3 个设置组名 + 基础参数）。 */
    private enum Tab {
        OVERVIEW(BonemealTexts.TAB_OVERVIEW),
        BASIC(BonemealTexts.GROUP_BASIC),
        TARGETS(BonemealTexts.GROUP_TARGETS),
        BYPASS(BonemealTexts.GROUP_BYPASS),
        ESP(BonemealTexts.GROUP_ESP);

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final AutoBoneMealModule module;

    /** 折叠块记忆（整页重建不丢失） */
    private final Set<String> collapsedSections = new HashSet<>();

    private final Body body = new Body();

    /** 当前页签 */
    private Tab tab = Tab.OVERVIEW;
    /** 自动刷新计数 */
    private int autoRefreshTicks;
    /** 顶部状态条的只读快照：每秒刷新一次 */
    private Status status = Status.empty();

    /**
     * @param parent 上级屏幕（模块页）——ESC / 返回键回到它
     * @param module 归属模块：控制台读写它的设置，不新增任何模块侧成员
     */
    public BonemealConsoleScreen(Screen parent, AutoBoneMealModule module) {
        super(BonemealTexts.MODULE_NAME + "控制台", parent);
        this.module = module;
        // 标题下的模块说明：与模块页标题下那行同源
        setSubtitle(module::description);
        content().add(body);
        reload();
    }

    /** 折叠块状态（供各页的 {@code FoldSection} 读写） */
    public Set<String> collapsedSections() {
        return collapsedSections;
    }

    // ── 生命周期 ──

    @Override
    protected void init() {
        super.init();
        ClientEventBus.subscribe(TICK_OWNER, ClientEventType.TICK, event -> onTick());
        if (minecraft != null) minecraft.execute(this::reload);
    }

    @Override
    public void removed() {
        ClientEventBus.unsubscribeAll(TICK_OWNER);
        super.removed();
    }

    /**
     * 每秒取一次新快照。
     *
     * <p><b>概览页：</b>整页重画（纯读数，无副作用）。<b>其余页：</b>只换状态条，不重建正文 ——
     * 正文带可交互控件，每秒重建会把正在编辑的输入框与正在点的开关一起抹掉。</p>
     */
    private void onTick() {
        if (++autoRefreshTicks < AUTO_REFRESH_TICKS) return;
        autoRefreshTicks = 0;
        if (minecraft == null || minecraft.gui.screen() != this) return;
        if (mousePressed(GLFW.GLFW_MOUSE_BUTTON_LEFT) || mousePressed(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) return;

        if (tab == Tab.OVERVIEW) {
            reload();
            return;
        }
        status = Status.capture(module);
    }

    private static boolean mousePressed(int button) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.getWindow() == null) return false;
        return GLFW.glfwGetMouseButton(client.getWindow().handle(), button) == GLFW.GLFW_PRESS;
    }

    // ── 重建 ──

    /** 重建整页内容（取新状态条快照 + 重排当前页） */
    public void reload() {
        status = Status.capture(module);
        body.rebuild();
    }

    /** 切换页签：延到下一 tick 再重建（当场重建会把正在响应的按钮从树上摘掉） */
    private void switchTab(Tab value) {
        tab = value;
        if (minecraft != null) {
            minecraft.execute(this::reload);
        } else {
            reload();
        }
    }

    /** 登记本帧要显示的 tooltip */
    @Override
    public void tip(String text, float x, float y) {
        body.tip(text, x, y);
    }

    // ── 页面装配 ──

    private void buildInto(CompactStack stack) {
        // 顶栏：状态文字 / 快捷键徽章 / 模块开关，压缩右对齐（模块页那条已撤掉）
        stack.add(new ConsoleHeaderBar(module));
        stack.add(new StatusStrip());
        buildTabs(stack);

        switch (tab) {
            case OVERVIEW -> new BonemealOverviewPage(this, module).build(stack);
            case BASIC -> new BonemealSettingsPage(this, module).buildBasic(stack);
            case TARGETS -> new BonemealSettingsPage(this, module).buildTargets(stack);
            case BYPASS -> new BonemealSettingsPage(this, module).buildBypass(stack);
            case ESP -> new BonemealSettingsPage(this, module).buildEsp(stack);
        }

        buildFooter(stack);
    }

    private void buildTabs(CompactStack stack) {
        List<Ctl> tabs = new ArrayList<>();
        for (Tab value : Tab.values()) {
            boolean active = value == tab;
            Button button = new Button(active ? "§b§l" + value.title() : "§7" + value.title(),
                () -> switchTab(value));
            tabs.add(new Ctl(button, () -> value == tab ? null : "切换到「" + value.title() + "」"));
        }
        stack.add(new ButtonStrip(this, tabs, ButtonStrip.TAB_HEIGHT));
    }

    private void buildFooter(CompactStack stack) {
        stack.add(new ButtonStrip(this, List.of(
            new Ctl(new Button("§7刷新", this::reload),
                "顶部状态条每秒自动刷新；概览页整页每秒重画，其余页按这个按钮重排最新数据"),
            ConsoleWidgets.resetDefaultsCtl(this, module, this::reload),
            new Ctl(new Button("§7关闭", () -> {
                if (minecraft != null) minecraft.gui.setScreen(null);
            }))), ButtonStrip.BUTTON_HEIGHT));
    }

    // ── 顶部状态条：不管在哪一页都看得到「现在到底在干什么」 ──

    /** 状态条两行六格的只读快照：构建时取一次、之后每秒重取 */
    private static final class Status {

        private final String[] cells;

        private Status(String[] cells) {
            this.cells = cells;
        }

        /** 构造期占位快照（此时还没有模块可读） */
        private static Status empty() {
            return new Status(new String[]{"", "", "", "", "", ""});
        }

        /** 取值全部来自模块只读接口与协调器只读接口，本类不改任何状态 */
        private static Status capture(AutoBoneMealModule module) {
            if (module == null) return empty();
            BonemealSettings settings = module.settings();
            String hud = module.hudStatus();

            return new Status(new String[]{
                "§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用"),
                "§7触发模式：§f" + settings.triggerMode.label(),
                "§7候选 / 队列：§f" + module.candidateCount() + " §7块 §8/ §f"
                    + module.queueSize() + " §7个",
                "§7骨粉：" + (module.hasBoneMealInHand() ? "§a主手/副手有" : "§c无")
                    + (module.pausedNoBoneMeal() ? " §8/ §c已暂停" : ""),
                // 旧 HUD 状态串：未进入世界时为空
                "§7状态指示：" + (hud == null ? "§8无" : hud),
                "§7服务器：" + (TacticalCoordinator.isServerLagging() ? "§c卡顿" : "§a流畅")
                    + " §8/ §7反作弊：" + antiCheatText()
            });
        }

        /** 反作弊显示名：未检测 / 未发现 / 未知时压成灰字，命中高风险名单时红字 */
        private static String antiCheatText() {
            String name = TacticalCoordinator.getDetectedAntiCheat();
            if (name == null || name.isBlank() || "未知".equals(name)) return "§8未知";
            if ("未检测".equals(name) || "未发现".equals(name)) return "§8" + name;
            return TacticalCoordinator.hasAdvancedAntiCheat() ? "§c" + name : "§f" + name;
        }

        private String cell(int index) {
            return cells[index];
        }
    }

    private final class StatusStrip implements CompactElement {

        private static final float ROW_HEIGHT = 18f;
        private static final float CELL_PAD = 6f;
        private static final int COLUMNS = 3;

        @Override
        public float height() {
            return ROW_HEIGHT * 2f;
        }

        @Override
        public void update(float dt) {
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            Status snapshot = status;
            for (int i = 0; i < COLUMNS * 2; i++) {
                int row = i / COLUMNS;
                int column = i % COLUMNS;
                float cellX = x + CELL_PAD + Math.max(0f, width - CELL_PAD * 2f) * column / COLUMNS;
                drawCell(canvas, snapshot.cell(i), cellX, y + ROW_HEIGHT * row,
                    Math.max(0f, width - CELL_PAD * 2f) / COLUMNS, alpha);
            }
        }

        private void drawCell(Canvas canvas, String text, float x, float y, float maxWidth, float alpha) {
            MinecraftText.draw(canvas, MinecraftText.fit(text, TEXT_SIZE, maxWidth), x,
                CardLayout.baseline(y + ROW_HEIGHT / 2f, TEXT_SIZE),
                TEXT_SIZE, ClickGuiThemeColors.current().primaryText, alpha);
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            return false;
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }

    // ── 页面容器：整页可重建 + tooltip 悬浮层 ──

    private final class Body implements CompactElement {

        private CompactStack stack = new CompactStack(LINE_GAP);
        private String tip;
        private float tipX;
        private float tipY;

        private void rebuild() {
            tip = null;
            CompactStack next = new CompactStack(LINE_GAP);
            buildInto(next);
            stack = next;
        }

        @Override
        public float height() {
            return stack.height();
        }

        @Override
        public void update(float dt) {
            stack.update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            stack.draw(canvas, x, y, width, alpha, y, y + stack.height(), mouseX, mouseY);
            paintTip(canvas, x, width, alpha);
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            return stack.onClick(mx, my, x, y, width, Float.MAX_VALUE, button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return stack.onDrag(mx, my, x, y, width, Float.MAX_VALUE);
        }

        @Override
        public void releaseDrag() {
            stack.releaseDrag();
        }

        /** 登记本帧要显示的 tooltip */
        private void tip(String text, float x, float y) {
            if (text == null || text.isEmpty()) return;
            tip = text;
            tipX = x;
            tipY = y;
        }

        /** 绘制并清空本帧的 tooltip：先按面板宽度折行，再逐行绘制 */
        private void paintTip(Canvas canvas, float originX, float width, float alpha) {
            String text = tip;
            tip = null;
            if (text == null || text.isEmpty()) return;

            ClickGuiThemeColors tc = ClickGuiThemeColors.current();
            List<String> lines = wrapTip(text, TIP_MAX_WIDTH);
            float tipWidth = 0f;
            for (String line : lines) tipWidth = Math.max(tipWidth, MinecraftText.measure(line, TIP_SIZE, false));
            tipWidth += TIP_PAD * 2f;
            float tipHeight = lines.size() * TIP_LINE + TIP_PAD * 2f;

            float drawX = Math.max(originX, Math.min(tipX, originX + width - tipWidth));
            float drawY = Math.max(0f, tipY - tipHeight);

            GlassPanel.shadow(canvas, drawX, drawY, tipWidth, tipHeight, TIP_RADIUS, tc.shadow, alpha, 0.9f);
            GlassPanel.frost(canvas, drawX, drawY, tipWidth, tipHeight, TIP_RADIUS, tc.window, 0.94f, alpha);
            GlassPanel.rim(canvas, drawX, drawY, tipWidth, tipHeight, TIP_RADIUS, tc.rim, alpha, 0.22f);

            float cursorY = drawY + TIP_PAD;
            int base = tipColor(tc);
            for (String line : lines) {
                MinecraftText.draw(canvas, line, drawX + TIP_PAD, cursorY + TIP_SIZE, TIP_SIZE, base, alpha);
                cursorY += TIP_LINE;
            }
        }
    }

    /** tooltip 折行：先按 {@code \n} 分行，再把每行按最大宽度折到下一行（颜色码整对带过） */
    private static List<String> wrapTip(String text, float maxWidth) {
        List<String> lines = new ArrayList<>();
        for (String paragraph : text.split("\n", -1)) {
            if (paragraph.isEmpty()) {
                lines.add("");
                continue;
            }
            StringBuilder line = new StringBuilder();
            float width = 0f;
            for (int i = 0; i < paragraph.length(); ) {
                int codePoint = paragraph.codePointAt(i);
                int charCount = Character.charCount(codePoint);
                String chunk = paragraph.substring(i, i + charCount);
                i += charCount;
                if (codePoint == '\u00A7' && i < paragraph.length()) {
                    chunk += paragraph.charAt(i);
                    i++;
                }
                float chunkWidth = MinecraftText.measure(chunk, TIP_SIZE, false);
                if (width + chunkWidth > maxWidth && line.length() > 0) {
                    lines.add(line.toString());
                    line.setLength(0);
                    width = 0f;
                }
                line.append(chunk);
                width += chunkWidth;
            }
            lines.add(line.toString());
        }
        return lines;
    }
}
