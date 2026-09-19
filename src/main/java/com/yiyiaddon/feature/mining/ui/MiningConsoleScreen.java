package com.yiyiaddon.feature.mining.ui;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.ui.console.MiningBaritonePage;
import com.yiyiaddon.feature.mining.ui.console.MiningOverviewPage;
import com.yiyiaddon.feature.mining.ui.console.MiningPointPage;
import com.yiyiaddon.feature.mining.ui.console.MiningTargetPage;
import com.yiyiaddon.feature.mining.ui.console.MiningTeleportPage;
import com.yiyiaddon.feature.mining.ui.console.MiningThresholdPage;
import com.yiyiaddon.platform.world.WorldIdentity;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自动挖矿控制台：把配置页里的设置项与点位按用途拆成页签。
 *
 * <p><b>骨架逐字照星露谷控制台</b>（{@code StardewConsoleScreen}，2026-09-16 抽出的通用件
 * {@link com.yiyiaddon.ui.console.ConsoleWidgets} 直接复用）：窗口标题、状态条（两行八格）、
 * 页签行、{@link Body}（可整体替换的内容容器）、{@link #reload()} 重建、
 * {@link #switchTab} 延到下一 tick、每秒自动刷新（只在概览页整页重建）、
 * 底部「刷新 / 关闭」、tooltip 悬浮层——全部同一套写法与同一套数值。</p>
 *
 * <p><b>文案与设置项一字未改：</b>六页里的每一行都来自旧项目配置页的五个设置分组
 * （{@code 目标选择 / 传送指令 / 触发条件 / 物品管理 / Baritone调优}；同一份
 * {@link MiningSettings} 字段、同一套控件、同一批 tooltip），控制台只负责「按什么顺序排到哪一页」，
 * 不新增任何设置项、指令或播报。</p>
 *
 * <p><b>2026-09-16 起设置项只在这里出现一次</b>（用户指令：「已经做进控制台了下面还一堆设置」）：
 * {@link AutoMinerPage} 已按星露谷模块页形态精简为「控制台入口 + 使用说明 + 三个点位卡片」，
 * 不再平铺任何设置分组 —— 两份控件写同一份设置，同页并存只会互相看对方为旧值。</p>
 *
 * <p><b>2026-09-18 配置记录入口不在本窗口</b>（用户两次纠正后的最终位置）：入口是模块页
 * 「打开控制台」按钮下方的 {@code §b服务器记录复原} 按钮（{@link AutoMinerPage}），点开
 * {@link MiningRecordScreen}（一键保存整套配置 = 全部设置 + 三个点位；每条记录读取 / 替换 / 详情 / 删除）。
 * <b>不要再往本窗口的页脚加记录控件</b>：本窗页脚由六个页签共用，加在这里等于每个页签底部各一份，
 * 用户已明确否掉（「你弄去每个页面干嘛」）。</p>
 */
public final class MiningConsoleScreen extends PanelScreen implements ConsoleHost {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.mining.console";

    /** 概览页自动刷新间隔：20 tick 一次（星露谷控制台同口径） */
    private static final int AUTO_REFRESH_TICKS = 20;

    // 排版常量（与星露谷控制台共用同一套数值：行内构件的尺寸取自 ConsoleMetrics）
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
        TARGET("目标选择"),
        TELEPORT("传送指令"),
        THRESHOLD("触发条件"),
        BARITONE("Baritone调优");

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final AutoMinerModule module;
    private final Body body = new Body();

    /** 当前页签 */
    private Tab tab = Tab.OVERVIEW;
    /** 自动刷新计数 */
    private int autoRefreshTicks;
    /** 顶部状态条的只读快照：每秒刷新一次（其余页不重建正文，只换它） */
    private Status status = Status.empty();

    /**
     * 折叠块的收起键集合（本窗口生命周期内有效）。
     *
     * <p>预置 Baritone 页的「开关」「数值」两段：那个页签 30 行平铺要滚三屏，
     * 默认收起后整页不用滚，点标题即可展开。折叠状态存窗口侧是因为整页重建
     * （刷新 / 切页签）会丢弃全部控件实例，不存这里每次重建都会回到默认值。</p>
     */
    private final Set<String> collapsedSections = new HashSet<>(Set.of("baritone:toggles", "baritone:values"));

    /**
     * @param parent 上级屏幕（模块页）——ESC / 返回键回到它
     * @param module 归属模块：控制台读写它的设置与点位，不新增任何模块侧成员
     */
    public MiningConsoleScreen(Screen parent, AutoMinerModule module) {
        super("自动挖矿控制台", parent);
        this.module = module;
        // 标题下的模块说明：与模块页标题下那行同源（用户 2026-09-17 口径：控制台里也要有说明）
        setSubtitle(module::description);
        content().add(body);
        reload();
    }

    // ── 生命周期 ──

    @Override
    protected void init() {
        super.init();
        // 本项目在 init 里订阅，同一所有者重复订阅会覆盖，因此回到本窗口时不会残留多个监听。
        ClientEventBus.subscribe(TICK_OWNER, ClientEventType.TICK, event -> onTick());
        // 从子界面（选择器 / 帮助页）返回时重建正文：选择器的结果与行上的状态文字都是构建时取的
        // 快照，不重建就会看到「清空了还显示已选」这种旧值。
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

    /** 已收起的折叠块键集合；页内折叠块用它记住展开 / 收起状态（重建后不丢失） */
    public Set<String> collapsedSections() {
        return collapsedSections;
    }

    /** 登记本帧要显示的 tooltip（页内构件悬停时调用，实现在 {@link Body}） */
    @Override
    public void tip(String text, float x, float y) {
        body.tip(text, x, y);
    }

    // ── 页面装配 ──

    private void buildInto(CompactStack stack) {
        // 顶栏：状态文字 / 快捷键徽章 / 模块开关，压缩右对齐（用户 2026-09-17 口径；模块页那条已撤掉）
        stack.add(new ConsoleHeaderBar(module));
        stack.add(new StatusStrip());
        buildTabs(stack);

        switch (tab) {
            case OVERVIEW -> new MiningOverviewPage(this, module).build(stack);
            case POINTS -> new MiningPointPage(this, module).build(stack);
            case TARGET -> new MiningTargetPage(this, module).build(stack);
            case TELEPORT -> new MiningTeleportPage(this, module).build(stack);
            case THRESHOLD -> new MiningThresholdPage(this, module).build(stack);
            case BARITONE -> new MiningBaritonePage(this, module).build(stack);
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
                if (minecraft != null) minecraft.setScreen(null);
            }))), ButtonStrip.BUTTON_HEIGHT));
    }

    // ── 顶部状态条：不管在哪一页都看得到「现在到底在干什么」 ──

    /**
     * 状态条两行八格的只读快照。
     *
     * <p>取数节奏照星露谷控制台：构建时取一次、之后每秒重取；模块未进世界时
     * {@code selfCheck()} 返回空列表，自检格按「通过」显示（与模块自身的口径一致）。</p>
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

        private static Status capture(AutoMinerModule module) {
            if (module == null) return empty();
            MiningSettings settings = module.settings();
            List<String> missing = module.selfCheck();
            return new Status(new String[]{
                "§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用"),
                "§7状态机：§f" + module.fsm().state().cn(),
                "§7维度：§f" + WorldIdentity.dimensionDisplayName(WorldIdentity.dimension()),
                "§7目标：§f" + targetName(settings),
                pointCell(module, MiningPointType.MINERAL),
                pointCell(module, MiningPointType.FOOD),
                pointCell(module, MiningPointType.AFK),
                missing.isEmpty() ? "§a自检通过" : "§e自检缺 " + missing.size() + " 项"
            });
        }

        /** 三格点位：`矿物箱 §a✓` / `矿物箱 §8✗`（用绑定表实况，不猜） */
        private static String pointCell(AutoMinerModule module, MiningPointType type) {
            String mark = module.pointStore().has(type) ? "§a✓" : "§8✗";
            return "§7" + type.displayName() + " " + mark;
        }

        /** 「目标」格：三个单值目标三选一（模块自检同一口径：只能选 1 个），都空则未选择 */
        private static String targetName(MiningSettings settings) {
            if (!settings.overworldOreTarget.isBlank()) {
                return MiningTargetPage.itemDisplayName(settings.overworldOreTarget);
            }
            if (!settings.netherOreTarget.isBlank()) {
                return MiningTargetPage.itemDisplayName(settings.netherOreTarget);
            }
            if (!settings.blockTarget.isBlank()) {
                return MiningTargetPage.blockDisplayName(settings.blockTarget);
            }
            return "§8未选择";
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
        /** 列间留白：文字按「列宽 − 该值」截断，避免长文本（如很长的目标名）压到下一列 */
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
     * （与星露谷控制台同一实现：颜色码整对带过，宽度按可见文本量）。
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
