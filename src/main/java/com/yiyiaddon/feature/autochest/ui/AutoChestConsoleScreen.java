package com.yiyiaddon.feature.autochest.ui;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.ui.console.AutoChestContainerPage;
import com.yiyiaddon.feature.autochest.ui.console.AutoChestOverviewPage;
import com.yiyiaddon.feature.autochest.ui.console.AutoChestPointPage;
import com.yiyiaddon.feature.autochest.ui.console.AutoChestProtectPage;
import com.yiyiaddon.feature.autochest.ui.console.AutoChestRenderPage;
import com.yiyiaddon.feature.autochest.ui.console.AutoChestRunPage;
import com.yiyiaddon.feature.autochest.ui.console.AutoChestWithdrawPage;
import com.yiyiaddon.model.autochest.ContainerTypeRegistry;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.console.ConsoleHost;
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
import java.util.List;

/**
 * 自动箱子控制台：把配置页里的设置项与点位按用途拆成页签。
 *
 * <p><b>骨架逐字照星露谷控制台</b>（{@code StardewConsoleScreen} → 通用件
 * {@link com.yiyiaddon.ui.console.ConsoleWidgets} → 自动挖矿 {@code MiningConsoleScreen}）：
 * 窗口标题、状态条（两行八格）、页签行、{@link Body}（可整体替换的内容容器）、{@link #reload()}
 * 重建、{@link #switchTab} 延到下一 tick、每秒自动刷新（只在概览页整页重建）、
 * 底部「刷新 / 关闭」、tooltip 悬浮层——全部同一套写法与同一套数值。</p>
 *
 * <p><b>文案与设置项一字未改：</b>七页里的每一行都来自旧项目配置页的 8 个设置分组
 * （{@code 运行模式 / 玩家控制模式 / 标点模式 / 容器 / 保护 / 目标物品 / 取物 / 渲染}；
 * 同一份 {@link AutoChestSettings} 字段、同一套控件、同一批 tooltip）。页签名取旧分组名，
 * 分组数多于页签数时**在页内用分组名做小节标题**（如「运行模式」页含「玩家控制模式」小节，
 * 「取物」页含「目标物品」小节），与自动挖矿控制台同一口径，不新造分类。</p>
 *
 * <p><b>2026-09-16 起设置项只在这里出现一次</b>（用户指令「自动箱子也做成控制台样式」）：
 * {@link AutoChestPage} 已按星露谷模块页形态精简为「控制台入口 + 使用说明 + 标点区」，
 * 不再平铺任何设置分组。</p>
 */
public final class AutoChestConsoleScreen extends PanelScreen implements ConsoleHost {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.autochest.console";

    /** 概览页自动刷新间隔：20 tick 一次（星露谷 / 挖矿控制台同口径） */
    private static final int AUTO_REFRESH_TICKS = 20;

    // 排版常量（与星露谷 / 挖矿控制台共用同一套数值：行内构件的尺寸取自 ConsoleMetrics）
    private static final float LINE_GAP = 6f;
    private static final float TEXT_SIZE = 11f;

    /** tooltip 悬浮层（本项目通用控件没有 tooltip 原语，面板内自绘） */
    private static final float TIP_SIZE = 10f;
    private static final float TIP_LINE = 12f;
    private static final float TIP_PAD = 6f;
    private static final float TIP_RADIUS = 6f;
    private static final float TIP_MAX_WIDTH = 320f;

    /** tooltip 文字基准色：深色主题白字；浅色主题用主文字色，否则白字压在白玻璃上等于看不见 */
    private static int tipColor(ClickGuiThemeColors tc) {
        return tc != null && !tc.dark ? tc.primaryText : 0xFFFFFF;
    }

    /** 页签：一屏只显示一类内容。 */
    private enum Tab {
        OVERVIEW("概览"),
        POINTS("点位"),
        RUN("运行模式"),
        CONTAINER("容器"),
        PROTECT("保护"),
        WITHDRAW("取物"),
        RENDER("渲染");

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final AutoChestModule module;
    private final Body body = new Body();

    /** 当前页签 */
    private Tab tab = Tab.OVERVIEW;
    /** 自动刷新计数 */
    private int autoRefreshTicks;
    /** 顶部状态条的只读快照：每秒刷新一次（其余页不重建正文，只换它） */
    private Status status = Status.empty();

    /**
     * @param parent 上级屏幕（模块页）——ESC / 返回键回到它
     * @param module 归属模块：控制台读写它的设置与点位，不新增任何模块侧成员
     */
    public AutoChestConsoleScreen(Screen parent, AutoChestModule module) {
        super("自动箱子控制台", parent);
        this.module = module;
        content().add(body);
        reload();
    }

    // ── 生命周期 ──

    @Override
    protected void init() {
        super.init();
        // 本项目在 init 里订阅，同一所有者重复订阅会覆盖，因此回到本窗口时不会残留多个监听。
        ClientEventBus.subscribe(TICK_OWNER, ClientEventType.TICK, event -> onTick());
        // 从子界面（容器类型 / 每种物品数量 / 目标物品 / 使用说明）返回时重建正文：行上的计数标签
        // 与容器类型文案都是构建时取的快照，不重建就会看到「选了容器类型还显示旧数量」这种旧值。
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
     * <p><b>概览页：</b>整页重画（纯读数，重画无副作用）。</p>
     *
     * <p><b>其余页：</b>只换顶部状态条的快照，<b>不重建正文</b>——正文带可交互控件，每秒重建会把正在
     * 编辑的输入框（光标与未提交内容）、正在点的开关一起抹掉。</p>
     */
    private void onTick() {
        if (++autoRefreshTicks < AUTO_REFRESH_TICKS) return;
        autoRefreshTicks = 0;
        // 鼠标按住时不重建：正在按的那个按钮会被摘掉，抬起事件落到空处，表现为按钮「卡住」
        if (minecraft == null || minecraft.screen != this) return;
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
        // 重建会丢弃整棵控件树，而文本框的焦点是静态字段：不清就会留在已被丢弃的实例上，
        // 之后按键被它吞掉（值写进设置但界面既无光标也无字符）、IME 预编辑画进黑洞、
        // 第一次 ESC 只被它用来清焦点（表现为「输入框点了没反应、窗口还关不掉」）
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

    /** 宿主客户端实例（各页打开子窗口用；与 {@code Minecraft.getInstance()} 同源） */
    public Minecraft client() {
        return minecraft;
    }

    /** 登记本帧要显示的 tooltip（页内构件悬停时调用，实现在 {@link Body}） */
    @Override
    public void tip(String text, float x, float y) {
        body.tip(text, x, y);
    }

    // ── 页面装配 ──

    private void buildInto(CompactStack stack) {
        stack.add(new StatusStrip());
        buildTabs(stack);

        switch (tab) {
            case OVERVIEW -> new AutoChestOverviewPage(this, module).build(stack);
            case POINTS -> new AutoChestPointPage(this, module).build(stack);
            case RUN -> new AutoChestRunPage(this, module).build(stack);
            case CONTAINER -> new AutoChestContainerPage(this, module).build(stack);
            case PROTECT -> new AutoChestProtectPage(this, module).build(stack);
            case WITHDRAW -> new AutoChestWithdrawPage(this, module).build(stack);
            case RENDER -> new AutoChestRenderPage(this, module).build(stack);
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
            new Ctl(new Button("§7关闭", () -> {
                if (minecraft != null) minecraft.setScreen(null);
            }))), ButtonStrip.BUTTON_HEIGHT));
    }

    // ── 顶部状态条：不管在哪一页都看得到「现在到底在干什么」 ──

    /**
     * 状态条两行八格的只读快照。
     *
     * <p>取数节奏照星露谷 / 挖矿控制台：构建时取一次、之后每秒重取；未进世界时模块的
     * {@code selfCheck()} 返回空列表，自检格按「通过」显示（与模块自身的口径一致），
     * 点位格走 {@code pointsInCurrentDimension()}（维度为空时返回空表，不会抛）。</p>
     */
    private static final class Status {

        private final String[] cells;

        private Status(String[] cells) {
            this.cells = cells;
        }

        /** 构造期占位快照（此时还没有模块可读） */
        private static Status empty() {
            return new Status(new String[]{"", "", "", "", "", "", "", ""});
        }

        private static Status capture(AutoChestModule module) {
            if (module == null) return empty();
            AutoChestSettings settings = module.settings();
            List<String> missing = module.selfCheck();
            return new Status(new String[]{
                "§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用"),
                "§7状态机：§f" + module.stateMachine().state(),
                "§7维度：§f" + WorldIdentity.dimensionDisplayName(WorldIdentity.dimension()),
                "§7运行模式：§f" + settings.scanMode.displayName(),
                "§7容器类型：§f" + settings.containerTypeIds.size() + " / "
                    + ContainerTypeRegistry.all().size() + " 类",
                "§7标点点位：§f" + module.pointStore().pointsInCurrentDimension().size() + " 个",
                "§7处理记录：§f" + module.recordStore().size() + " 条",
                missing.isEmpty() ? "§a自检通过" : "§e自检缺 " + missing.size() + " 项"
            });
        }

        private String cell(int index) {
            return cells[index];
        }
    }

    private final class StatusStrip implements CompactElement {

        /** 状态条每行行高：随控制台整页收窄一档（20 → 18），{@link #height()} 与绘制同读这一个值 */
        private static final float ROW_HEIGHT = 18f;
        private static final float CELL_PAD = 6f;
        private static final int COLUMNS = 4;
        /** 列间留白：文字按「列宽 − 该值」截断，避免长文本（如很长的模式名）压到下一列 */
        private static final float COLUMN_GAP = 8f;

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
            // 四列等分内容区（两侧各留 CELL_PAD）：列宽必须按 1/COLUMNS 算，
            // 星露谷那版是「每行一格、写死两次 drawCell」，照抄它的半宽间距会把第 2 列起画到面板外
            float inner = Math.max(0f, width - CELL_PAD * 2f);
            float cellWidth = Math.max(0f, inner / COLUMNS - COLUMN_GAP);
            for (int i = 0; i < COLUMNS * 2; i++) {
                int row = i / COLUMNS;
                int column = i % COLUMNS;
                float cellX = x + CELL_PAD + inner * column / COLUMNS;
                drawCell(canvas, snapshot.cell(i), cellX, y + ROW_HEIGHT * row, cellWidth, alpha);
            }
        }

        private void drawCell(Canvas canvas, String text, float x, float y, float maxWidth, float alpha) {
            MinecraftText.draw(canvas, fit(text, maxWidth), x,
                CardLayout.baseline(y + ROW_HEIGHT / 2f, TEXT_SIZE),
                TEXT_SIZE, ClickGuiThemeColors.current().primaryText, alpha);
        }

        /**
         * 按可见宽度截断（保留颜色码），超宽补省略号。
         *
         * <p>本窗口没有裁剪原语，超宽文本会直接压到右侧列上。测量走
         * {@link MinecraftText#measure}（不含颜色码字符），不能用 {@code CardLayout.ellipsize}
         * ——后者按 {@code FontRenderer} 口径会把 {@code §x} 也算进宽度。</p>
         */
        private static String fit(String text, float maxWidth) {
            if (text == null || text.isEmpty()) return "";
            if (maxWidth <= 0f) return "";
            if (MinecraftText.measure(text, TEXT_SIZE, false) <= maxWidth) return text;
            for (int end = text.length() - 1; end > 0; end--) {
                if (text.charAt(end - 1) == '§') continue; // 不要把颜色码切一半
                String candidate = text.substring(0, end) + "…";
                if (MinecraftText.measure(candidate, TEXT_SIZE, false) <= maxWidth) return candidate;
            }
            return "…";
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

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  页面容器：整页可重建 + tooltip 悬浮层
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 整页内容容器。
     *
     * <p>{@link #rebuild()} 直接换掉内部堆叠，因此页签切换 / 手动刷新 / 概览页每秒自动刷新都不会
     * 重开窗口、不会重播入场动画。堆叠画完后统一绘制本帧登记的 tooltip，保证提示不被后面的行盖住。</p>
     */
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

        /** 登记本帧要显示的 tooltip（锚点为设计坐标下的鼠标位置） */
        private void tip(String text, float x, float y) {
            if (text == null || text.isEmpty()) return;
            tip = text;
            tipX = x;
            tipY = y;
        }

        /** 绘制并清空本帧的 tooltip：先按面板宽度折行，再逐行绘制（颜色码由 MinecraftText 解析） */
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

            // 贴边修正：tip 跟随鼠标，但绝不越出内容区左右边界
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

    /**
     * tooltip 折行：先按 {@code \n} 分行，再把每行按最大宽度折到下一行
     * （与星露谷 / 挖矿控制台同一实现：颜色码整对带过，宽度按可见文本量）。
     */
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
                // 颜色码整对带过，不单独参与宽度累加之外的处理
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
