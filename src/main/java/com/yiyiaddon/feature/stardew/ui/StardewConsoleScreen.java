package com.yiyiaddon.feature.stardew.ui;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.ButtonStrip;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.Ctl;
import com.yiyiaddon.feature.stardew.ui.console.StardewLogPage;
import com.yiyiaddon.feature.stardew.ui.console.StardewLogisticsPage;
import com.yiyiaddon.feature.stardew.ui.console.StardewOverviewPage;
import com.yiyiaddon.feature.stardew.ui.console.StardewPlantingPage;
import com.yiyiaddon.feature.stardew.ui.console.StardewPointPage;
import com.yiyiaddon.feature.stardew.ui.console.StardewRunPage;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.PreeditEvent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 星露谷农场控制台：把原来平铺在设置页里的内容按用途拆成页签。
 *
 * <p><b>逐字搬运自旧项目</b> {@code stardew/ui/StardewConsoleScreen.java}（6 页签、状态条、
 * 各页结构、按钮文案与 tooltip、确认弹窗、日志页、刷新节奏），只把外壳换成本项目的
 * {@link PanelScreen} 骨架。旧项目那些「直接渲染设置页那批 Setting」的做法在本项目没有
 * 等价物（本项目已无 Meteor Setting 体系），因此四页的可编辑项按
 * {@code StardewSettings} / {@code StardewLogisticsStore} / {@code StardewCropPlanStore}
 * 的真实数据源逐项重建，编辑的仍是执行层在用的同一份值。</p>
 *
 * <p><b>数据一律来自 {@link StardewConsoleData}（模块侧快照）：</b>本页不自己算配额、不自己数
 * 库存、不自己判断季节是否识别，只负责排版；快照在每次重建时取一次，与旧项目
 * {@code initWidgets()} 的取数节奏一致。</p>
 *
 * <p><b>重建而非换窗口：</b>旧项目 {@code reload()} 是「清空窗口内容再 initWidgets」，
 * 窗口本身不重开。本项目 {@link PanelScreen} 的内容容器不支持清空，因此这里把整页内容装进一个
 * 可整体替换的 {@link Body} 元素：{@link #reload()} 只换 Body 内部堆叠，不重开窗口、不重播
 * 入场动画，滚动位置也保持不变（与旧 reload 观感一致）。</p>
 *
 * <p><b>拆分：</b>本类只留骨架（窗口标题、顶部状态条、6 个页签、自动刷新、底部按钮、页面调度），
 * 页内构件落在 {@code ui/console/StardewConsoleWidgets}，六个页各自一个类
 * （{@code ui/console/StardewOverviewPage} 等）；页内方法体原样搬运，只改可见性与调用点。</p>
 */
public final class StardewConsoleScreen extends PanelScreen {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.stardew.console";

    /** 概览页自动刷新间隔：20 tick 一次（旧项目同口径） */
    private static final int AUTO_REFRESH_TICKS = 20;

    public static final float PAD_X = 14f;
    private static final float LINE_GAP = 6f;
    public static final float LABEL_SIZE = 13f;
    private static final float TEXT_SIZE = 11f;
    public static final float NOTE_SIZE = 11f;
    public static final float SECTION_HEIGHT = 24f;
    public static final float SECTION_SIZE = 12f;

    /** 行内「重置」图标：Material Symbols refresh（旧项目此处是图标按钮，不是文字按钮） */
    public static final String GLYPH_RESET = "\uE5D5";

    /** tooltip 悬浮层（本项目通用控件没有 tooltip 原语，面板内自绘） */
    private static final float TIP_SIZE = 10f;
    private static final float TIP_LINE = 12f;
    private static final float TIP_PAD = 6f;
    private static final float TIP_RADIUS = 6f;
    private static final float TIP_MAX_WIDTH = 320f;
    public static final float TIP_OFFSET_X = 14f;
    public static final float TIP_OFFSET_Y = 16f;
    /** tooltip 文字基准色：深色主题白字；浅色主题用主文字色，否则白字压在白玻璃上等于看不见 */
    private static int tipColor(ClickGuiThemeColors tc) {
        return tc != null && !tc.dark ? tc.primaryText : 0xFFFFFF;
    }

    /** 页签：一屏只显示一类内容。 */
    private enum Tab {
        OVERVIEW("概览"),
        PLANTING("种植"),
        RUN("运行"),
        LOGISTICS("后勤"),
        POINTS("点位"),
        LOG("日志");

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final StardewFarmModule module;
    private final Body body = new Body();

    /**
     * 已收起的折叠块键集合（本窗口生命周期内有效）。
     *
     * <p>整页重建（刷新 / 切页签）会丢弃全部控件实例，折叠状态必须存在窗口侧才不会
     * 每次重建都回到默认展开。</p>
     */
    private final Set<String> collapsedSections = new HashSet<>();

    /** 当前页签 */
    private Tab tab = Tab.OVERVIEW;
    /** 自动刷新计数 */
    private int autoRefreshTicks;
    /** 本帧重建时取的只读快照（与旧项目 initWidgets 的取数节奏一致） */
    private StardewConsoleData data = StardewConsoleData.empty();
    /** 顶部状态条专用的实时快照：每秒单独刷新，与正文快照解耦（正文只在重排时更新） */
    private StardewConsoleData status = data;

    /**
     * @param parent 上级屏幕（模块页）——ESC / 返回键回到它，与旧项目 {@code parent = mc.screen} 一致
     * @param module 归属模块：控制台只读它的状态与快照，编辑写回它的设置与存储
     */
    public StardewConsoleScreen(Screen parent, StardewFarmModule module) {
        super("星露谷农场控制台", parent);
        this.module = module;
        content().add(body);
        reload();
    }

    // ── 生命周期 ──

    @Override
    protected void init() {
        super.init();
        // 旧项目由 WidgetScreen.init() 自动订阅 TickEvent.Post；本项目在 init 里订阅，
        // 同一所有者重复订阅会覆盖，因此回到本窗口时不会残留多个监听。
        ClientEventBus.subscribe(TICK_OWNER, ClientEventType.TICK, event -> onTick());
        // 从子界面（洒水器点位列表 / 二次确认）返回时重建正文：卡片上的坐标与数量、开关状态都是
        // 构建时取的快照，不重建就会看到「清空了还显示已绑定 6 个」这种旧值。
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
     * 编辑的输入框（光标与未提交内容）、正在点的开关、正在滚动看的长列表一起抹掉。所以「资源包 /
     * 季节 / 作物 / 任务」这四项在所有页都是准的，正文内容要最新值就按左下角「刷新」。</p>
     */
    private void onTick() {
        if (++autoRefreshTicks < AUTO_REFRESH_TICKS) return;
        autoRefreshTicks = 0;
        // 鼠标按住时不重建：正在按的那个按钮会被摘掉，抬起事件落到空处，表现为按钮「卡住」
        if (minecraft == null || minecraft.screen != this) return;
        if (mousePressed(GLFW.GLFW_MOUSE_BUTTON_LEFT) || mousePressed(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) return;

        StardewConsoleData next = module.consoleData();
        if (tab == Tab.OVERVIEW) {
            data = next;
            status = next;
            body.rebuild();
            return;
        }
        // 状态条四项有变化才换：没变化就不动，避免无谓的引用替换
        if (!statusSignature(next).equals(statusSignature(status))) status = next;
    }

    /** 状态条四项的内容指纹（只有它变了才需要换状态条快照） */
    private static String statusSignature(StardewConsoleData snapshot) {
        return snapshot.resource() + '\u0000' + snapshot.season() + '\u0000'
            + snapshot.cropsSummary() + '\u0000' + snapshot.task();
    }

    private static boolean mousePressed(int button) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.getWindow() == null) return false;
        return GLFW.glfwGetMouseButton(client.getWindow().handle(), button) == GLFW.GLFW_PRESS;
    }

    // ── 键盘输入转发（数值框需要真正键入） ──

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (SettingTextBox.keyPressed(event)) return true;
        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (SettingTextBox.charTyped(event)) return true;
        return super.charTyped(event);
    }

    @Override
    public boolean preeditUpdated(PreeditEvent event) {
        SettingTextBox.onPreedit(event);
        return true;
    }

    // ── 关闭 ──

    /**
     * 关闭本窗口并直接回到游戏。
     *
     * <p>旧项目点位卡片的「设置 / 删除」与底部「关闭」按钮在动作成功后就是当场
     * {@code mc.setScreen(null)}，没有任何过渡动画。这里照旧立即切屏：走关闭动画要等整段动画
     * 播完才真正关窗，观感上就是「点了没反应、界面还开着」。</p>
     */
    public void closeToGame() {
        if (minecraft != null) minecraft.setScreen(null);
    }

    // ── 重建 ──

    /** 重建整页内容（旧项目 {@code reload()} 的等价物：取新快照 + 重排当前页） */
    public void reload() {
        data = module.consoleData();
        status = data;
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

    /** 本帧的只读快照（各页排版读取；快照只在 {@link #reload()} 时更新） */
    public StardewConsoleData data() {
        return data;
    }

    /** 已收起的折叠块键集合；页内折叠块用它记住展开 / 收起状态（重建后不丢失） */
    public Set<String> collapsedSections() {
        return collapsedSections;
    }

    /** 登记本帧要显示的 tooltip（页内构件悬停时调用，实现在 {@link Body}） */
    public void tip(String text, float x, float y) {
        body.tip(text, x, y);
    }

    /** 宿主客户端实例（各页打开子窗口用；与 {@code Minecraft.getInstance()} 同源） */
    public Minecraft client() {
        return minecraft;
    }

    // ── 页面装配 ──

    private void buildInto(CompactStack stack) {
        stack.add(new StatusStrip());
        buildTabs(stack);

        switch (tab) {
            case OVERVIEW -> new StardewOverviewPage(this).build(stack);
            case PLANTING -> new StardewPlantingPage(this, module).build(stack);
            case RUN -> new StardewRunPage(this, module).build(stack);
            case LOGISTICS -> new StardewLogisticsPage(this, module).build(stack);
            case POINTS -> new StardewPointPage(this, module).build(stack);
            case LOG -> new StardewLogPage(this, module).build(stack);
        }

        buildFooter(stack);
    }

    // ── 顶部状态条：不管在哪一页都看得到「现在到底在干什么」 ──

    private final class StatusStrip implements CompactElement {

        private static final float ROW_HEIGHT = 20f;
        private static final float CELL_PAD = 6f;

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
            StardewConsoleData snapshot = status;
            String resource = "§7资源包 §8▸ " + (snapshot.resourceReady() ? "§a" : "§e") + snapshot.resource();
            String season = "§7季节 §8▸ " + seasonColor(snapshot.season()) + snapshot.season();
            String crops = "§7作物 §8▸ §d" + snapshot.cropsSummary();
            String task = "§7任务 §8▸ " + taskColor(snapshot.task()) + snapshot.task();

            float columnX = x + width * 0.5f;
            drawCell(canvas, resource, x + CELL_PAD, y, alpha);
            drawCell(canvas, season, columnX, y, alpha);
            drawCell(canvas, crops, x + CELL_PAD, y + ROW_HEIGHT, alpha);
            drawCell(canvas, task, columnX, y + ROW_HEIGHT, alpha);
        }

        private void drawCell(Canvas canvas, String text, float x, float y, float alpha) {
            MinecraftText.draw(canvas, text, x, CardLayout.baseline(y + ROW_HEIGHT / 2f, TEXT_SIZE),
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

    /** 季节语义色：春绿 / 夏金 / 秋橙 / 冬蓝，未识别走灰（不猜色） */
    private static String seasonColor(String season) {
        if (season == null || season.isBlank() || season.startsWith("未知")) return "§7";
        if (season.startsWith("春")) return "§a";
        if (season.startsWith("夏")) return "§e";
        if (season.startsWith("秋")) return "§6";
        if (season.startsWith("冬")) return "§b";
        return "§7";
    }

    /** 任务语义色：空闲灰 / 等季节黄 / 干活青，与播报里的级别色保持一致 */
    private static String taskColor(String task) {
        if (task == null || task.isBlank() || task.startsWith("空闲")) return "§7";
        if (task.startsWith("等待季节")) return "§e";
        return "§b";
    }

    // ── 页签 ──

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
            new Ctl(new Button("§7关闭", this::closeToGame))), ButtonStrip.BUTTON_HEIGHT));
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  页面容器：整页可重建 + tooltip 悬浮层
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 整页内容容器。
     *
     * <p>{@link #rebuild()} 直接换掉内部堆叠（旧 {@code clear()+initWidgets()} 的等价物），
     * 因此页签切换 / 手动刷新 / 概览页每秒自动刷新都不会重开窗口、不会重播入场动画。
     * 堆叠画完后统一绘制本帧登记的 tooltip，保证提示不被后面的行盖住。</p>
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
     * tooltip 折行：先按 {@code \n} 分行，再把每行按最大宽度折到下一行。
     *
     * <p>旧项目 tooltip 由 Meteor 按固定宽度折行；本项目通用控件没有 tooltip 原语，
     * 因此在这里补一个按可见宽度（含颜色码解析）折行的实现。</p>
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
