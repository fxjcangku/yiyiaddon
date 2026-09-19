package com.yiyiaddon.feature.teleport.ui;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.teleport.TeleportModule;
import com.yiyiaddon.feature.teleport.config.TeleportSettings;
import com.yiyiaddon.feature.teleport.model.TeleportContext;
import com.yiyiaddon.feature.teleport.ui.console.TeleportKeysPage;
import com.yiyiaddon.feature.teleport.ui.console.TeleportOverviewPage;
import com.yiyiaddon.feature.teleport.ui.console.TeleportSettingsPage;
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
import com.yiyiaddon.ui.widget.SettingKeybind;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * 传送控制台：把 15 项设置与三个功能键按旧项目原 6 个设置组拆成页签
 * （概览 / 触发按键 / TP地面 / TP穿墙 / TP坐标 / 验证与调试）。
 *
 * <p><b>骨架逐字照既有控制台</b>（管理员检测 / 自动图书管理员 / 星露谷）：窗口标题、模块说明副标题、
 * 状态条（两行六格）、页签行、可整体替换的 {@link Body}、{@link #reload()} 重建、
 * {@link #switchTab} 延到下一 tick、每秒自动刷新（只在概览页整页重建）、底部「刷新 / 关闭」、
 * tooltip 悬浮层 —— 全部同一套写法与同一套数值。</p>
 *
 * <p><b>设置项一字未改</b>：每行都来自 {@link TeleportSettings} 的同名字段，名称与描述逐字取自旧模块
 * 的设置声明（{@code TeleportModule:82-208}）；控制台只负责「搬到哪一页、按什么顺序排」，
 * 不新增设置项、不改默认值与取值域、不做手动触发按钮（旧项目只有三键与 {@code .tp}）。</p>
 */
public final class TeleportConsoleScreen extends PanelScreen implements ConsoleHost {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.teleport.console";

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

    /** 页签：一屏只显示一类内容（页签名逐字沿用旧项目 6 个设置组名）。 */
    private enum Tab {
        OVERVIEW("概览"),
        KEYS("触发按键"),
        GROUND("TP地面"),
        WALL("TP穿墙"),
        COORD("TP坐标"),
        VERIFY("验证与调试");

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final TeleportModule module;

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
    public TeleportConsoleScreen(Screen parent, TeleportModule module) {
        super("传送控制台", parent);
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
        // 重建会丢弃整棵控件树，而键位录制态是静态字段：不清就会留在已被丢弃的实例上
        // （「触发按键」页的键位块正在录制时按「刷新」/ 切页签，录制态必须一起丢掉）
        SettingKeybind.clearCapture();
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

    /** 宿主客户端实例（各页打开子窗口用） */
    public Minecraft client() {
        return minecraft;
    }

    /** 登记本帧要显示的 tooltip */
    @Override
    public void tip(String text, float x, float y) {
        body.tip(text, x, y);
    }

    // ── 页面装配 ──

    private void buildInto(CompactStack stack) {
        stack.add(new ConsoleHeaderBar(module));
        stack.add(new StatusStrip());
        buildTabs(stack);

        switch (tab) {
            case OVERVIEW -> new TeleportOverviewPage(this, module).build(stack);
            case KEYS -> new TeleportKeysPage(this, module).build(stack);
            case GROUND -> new TeleportSettingsPage(this, module).buildGround(stack);
            case WALL -> new TeleportSettingsPage(this, module).buildWall(stack);
            case COORD -> new TeleportSettingsPage(this, module).buildCoord(stack);
            case VERIFY -> new TeleportSettingsPage(this, module).buildVerify(stack);
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

        private static Status capture(TeleportModule module) {
            if (module == null) return empty();
            TeleportSettings settings = module.settings();
            TeleportContext last = module.coordinator().last();

            String lastMode = last == null || last.mode == null ? "§8无" : "§f" + last.mode;
            String lastTarget = last == null || last.target == null ? "§8无" : "§f" + last.target.posText();
            String rubberband = last == null || last.rubberbandDist < 0
                ? "§8无"
                : "§c" + String.format(Locale.ROOT, "%.1f", last.rubberbandDist) + "§7 格";

            return new Status(new String[]{
                "§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用"),
                "§7状态：" + (module.coordinator().busy() ? "§e传送中" : "§8空闲"),
                "§7最近模式：" + lastMode,
                "§7最近目标：" + lastTarget,
                "§7回弹偏差：" + rubberband,
                "§7调试渲染：" + (settings.debugRender ? "§a开" : "§8关")
            });
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
