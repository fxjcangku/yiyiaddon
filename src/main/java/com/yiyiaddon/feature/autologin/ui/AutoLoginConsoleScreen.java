package com.yiyiaddon.feature.autologin.ui;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.autologin.AutoLoginModule;
import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import com.yiyiaddon.feature.autologin.config.AutoLoginTexts;
import com.yiyiaddon.feature.autologin.service.ReconnectHandler;
import com.yiyiaddon.feature.autologin.ui.console.AutoLoginOverviewPage;
import com.yiyiaddon.feature.autologin.ui.console.AutoLoginSettingsPage;
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
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自动登入控制台：把全部设置按<strong>旧项目原 5 个设置组</strong>拆成页签
 * （概览 / 登录认证 / 自动重连 / 自动执行指令 / 进服路线 / 自用配置 / 账号与调试）。
 *
 * <p><b>页签名 = 旧项目设置组名</b>（第 183 条）：旧 {@code settings.createGroup} 四个组名
 * 「登录认证 / 自动重连 / 自动执行指令 / 账号与调试」原样沿用；旧「进服路线」组内分两节，
 * 一页铺 35 项过长，按旧源码的分节标题拆为「进服路线」（通用子服）与「自用配置」两页，
 * 名称逐字取自旧源码分节，不新造分类。</p>
 *
 * <p><b>骨架逐字照既有控制台</b>（自动骨粉 / 自动附魔 / 自动村民；同源件
 * {@link com.yiyiaddon.ui.console.ConsoleWidgets} 直接复用）：窗口标题、模块说明副标题、
 * 顶栏 {@link ConsoleHeaderBar}（状态文字 + 快捷键徽章 + 模块开关）、状态条（两行六格）、
 * 页签行、可整体替换的 {@link Body}、{@link #reload()} 重建、{@link #switchTab} 延到下一 tick、
 * 每秒自动刷新（只在概览页整页重建）、底部「刷新 / 关闭」、tooltip 悬浮层 —— 全部同一套写法与同一套数值。</p>
 *
 * <p><b>设置项一字未改</b>：每行都来自 {@link AutoLoginSettings} 的同名字段，名称、描述、默认值、
 * 取值域与可见性条件逐字取自旧项目 {@code autologin/config/AutoLoginSettings.java} 的设置声明；
 * 控制台只负责「搬到哪一页、按什么顺序排、按行级显隐照旧条件的等价实现」，不新增设置项。</p>
 */
public final class AutoLoginConsoleScreen extends PanelScreen implements ConsoleHost {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.autologin.console";

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

    /** 页签：一屏只显示一类内容（页签名 = 旧项目设置组名 / 旧源码分节标题）。 */
    private enum Tab {
        OVERVIEW(AutoLoginTexts.TAB_OVERVIEW),
        AUTH(AutoLoginTexts.GROUP_AUTH),
        RECONNECT(AutoLoginTexts.GROUP_RECONNECT),
        COMMANDS(AutoLoginTexts.GROUP_COMMANDS),
        ROUTE(AutoLoginTexts.GROUP_ROUTE),
        LEYUAN(AutoLoginTexts.GROUP_LEYUAN),
        ACCOUNT(AutoLoginTexts.GROUP_ACCOUNT);

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final AutoLoginModule module;

    /** 折叠块记忆（整页重建不丢失） */
    private final Set<String> collapsedSections = new HashSet<>();

    /**
     * 自由名单输入框的未提交草稿：键 = 设置名，值 = 输入框当前内容。
     *
     * <p>页面对象每次重建都新建，草稿不能放页面里；整页重建（每秒自动刷新 / 切页签 / 增删一项）
     * 不会丢未提交内容（与自动附魔「自定义附魔目标」同一处置，草稿存在窗口侧）。</p>
     */
    private final Map<String, String> drafts = new HashMap<>();

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
    public AutoLoginConsoleScreen(Screen parent, AutoLoginModule module) {
        super(AutoLoginTexts.MODULE_NAME + "控制台", parent);
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

    /** 某一项自由名单输入框的未提交草稿（无草稿时返回空串） */
    public String draft(String key) {
        return drafts.getOrDefault(key, "");
    }

    /** 记下某一项自由名单输入框的草稿 */
    public void draft(String key, String value) {
        if (key == null) return;
        drafts.put(key, value == null ? "" : value);
    }

    // ── 生命周期 ──

    @Override
    protected void init() {
        super.init();
        // 本项目在 init 里订阅，同一所有者重复订阅会覆盖，因此回到本窗口时不会残留多个监听。
        ClientEventBus.subscribe(TICK_OWNER, ClientEventType.TICK, event -> onTick());
        // 从子界面（选择器）返回时重建正文：行上的状态文字都是构建时快照
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
        // 鼠标按住时不重建：正在按的那个按钮会被摘掉，抬起事件落到空处，表现为按钮「卡住」
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

    /**
     * 重建整页内容（取新状态条快照 + 重排当前页）。
     *
     * <p>重建会丢弃整棵控件树，而文本框焦点是静态字段：不清就会留在已被丢弃的实例上。</p>
     */
    public void reload() {
        SettingTextBox.clearFocus();
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
            case OVERVIEW -> new AutoLoginOverviewPage(this, module).build(stack);
            case AUTH -> new AutoLoginSettingsPage(this, module).buildAuth(stack);
            case RECONNECT -> new AutoLoginSettingsPage(this, module).buildReconnect(stack);
            case COMMANDS -> new AutoLoginSettingsPage(this, module).buildCommands(stack);
            case ROUTE -> new AutoLoginSettingsPage(this, module).buildRoute(stack);
            case LEYUAN -> new AutoLoginSettingsPage(this, module).buildLeyuan(stack);
            case ACCOUNT -> new AutoLoginSettingsPage(this, module).buildAccount(stack);
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

        /** 取值全部来自模块只读接口，本类不改任何状态 */
        private static Status capture(AutoLoginModule module) {
            if (module == null) return empty();
            AutoLoginSettings settings = module.settings();
            ReconnectHandler reconnect = module.reconnectHandler();
            String hud = module.hudStatus();

            return new Status(new String[]{
                "§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用"),
                // 旧 HUD 状态串：未进入世界或未完成认证时为空
                "§7状态：" + (hud == null ? AutoLoginTexts.STATE_NONE : hud),
                "§7重连：" + (reconnect.isScheduled()
                    ? "§e" + (reconnect.getTicksLeft() / 20) + " 秒" : AutoLoginTexts.STATE_NONE)
                    + " §8/ §7次数：§f" + reconnect.getReconnectAttempts(),
                "§7路线：" + routeText(module),
                "§7子服检测：" + onOff(settings.detectSubserver)
                    + " §8/ §7自动重连：" + onOff(settings.autoReconnect),
                "§7免登录检测：" + onOff(settings.noLoginDetection)
                    + " §8/ §7调试模式：" + onOff(settings.debugMode)
            });
        }

        /** 当前路线：自用回服路线在跑就显示它的阶段名，否则显示通用子服菜单路线是否在跑 */
        private static String routeText(AutoLoginModule module) {
            if (module.leyuanRoute().isRunning()) return "§f自用 · " + module.leyuanRoute().stateName();
            if (module.subserverRoute().isRunning()) return "§f通用子服菜单";
            return AutoLoginTexts.STATE_NONE;
        }

        /** 开关的只读显示：绿开 / 灰关（只读状态一览口径，与概览页一致） */
        private static String onOff(boolean value) {
            return value ? "§a开" : "§8关";
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
