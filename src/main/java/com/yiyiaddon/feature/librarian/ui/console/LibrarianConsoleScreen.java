package com.yiyiaddon.feature.librarian.ui.console;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.librarian.AutoLibrarianModule;
import com.yiyiaddon.feature.librarian.fsm.LibrarianState;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleHeaderBar;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingKeybind;
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自动图书管理员控制台：把 12 项设置与运行实况按用途拆成整屏页签（D4 拍板：三页签）。
 *
 * <p><b>骨架逐字照自动村民交易 / 自动农场控制台</b>（通用件
 * {@link com.yiyiaddon.ui.console.ConsoleWidgets} 与 {@link ConsoleHost} 直接复用）：
 * 顶栏（状态文字 / 快捷键徽章 / 模块开关）、状态条（两行八格）、页签行、可整体重建的正文容器、
 * 每秒自动刷新、底部「刷新 / 关闭」、tooltip 走全局 {@link TooltipLayer} —— 同一套写法与同一套数值。</p>
 *
 * <p><b>页签划分（D4）：</b>概览（只读实况）/ 设置（目标 / 行为 / 通知 / 调试四个折叠组 +
 * 暂停快捷键收尾）/ 日志（状态播报流水）。本模块旧项目<b>没有点位库、没有 ESP 渲染</b>，
 * 故不设点位页（第 163/171 条：旧项目没有的一律不做）。</p>
 *
 * <p><b>键位录制：</b>设置页「暂停快捷键」行用自研键位控件
 * {@link com.yiyiaddon.ui.widget.SettingKeybind}，本窗口负责把按键与鼠标点击转发给录制
 * （{@link com.yiyiaddon.ui.widget.SettingKeybind#keyPressed} / {@code #mousePressed}）——
 * 不转发的话录制态永远等不到输入。</p>
 *
 * <p><b>折叠记忆：</b>折叠状态存本窗口实例（{@link #collapsedSections()}），整页重建
 * （刷新 / 切页签）会丢弃全部控件实例，状态存窗口侧才不会每次回到默认值。</p>
 */
public final class LibrarianConsoleScreen extends PanelScreen implements ConsoleHost {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.librarian.console";

    /** 自动刷新间隔：20 tick 一次（其它控制台同口径） */
    private static final int AUTO_REFRESH_TICKS = 20;

    /** 窗口标题：模块名 + 控制台（与自动村民交易 / 自动农场控制台同一命名口径） */
    private static final String WINDOW_TITLE = AutoLibrarianModule.MESSAGE_MODULE + "控制台";

    // 排版常量（与其它控制台共用同一套数值）
    private static final float LINE_GAP = 6f;
    private static final float TEXT_SIZE = 11f;

    /** 页签：一屏只显示一类内容（D4 固定三页，无点位页）。 */
    private enum Tab {
        OVERVIEW("概览"),
        SETTINGS("设置"),
        LOGS("日志");

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final AutoLibrarianModule module;
    private final Body body = new Body();

    /** 当前页签 */
    private Tab tab = Tab.OVERVIEW;
    /** 自动刷新计数 */
    private int autoRefreshTicks;
    /** 顶部状态条的只读快照：每秒刷新一次（其余页不重建正文，只换它） */
    private Status status = Status.empty();

    /** 已收起的折叠块键集合（本窗口生命周期内有效；整页重建不丢） */
    private final Set<String> collapsedSections = new HashSet<>();

    /** 模块页入口使用的一参构造：上级窗口取当前屏幕 */
    public LibrarianConsoleScreen(AutoLibrarianModule module) {
        this(currentScreen(), module);
    }

    /**
     * @param parent 上级屏幕（模块页）——ESC / 返回键回到它
     * @param module 归属模块：控制台读写它的设置与日志，不新增任何模块侧成员
     */
    public LibrarianConsoleScreen(Screen parent, AutoLibrarianModule module) {
        super(WINDOW_TITLE, parent);
        this.module = module;
        // 标题下的模块说明：与模块页标题下那行同源
        setSubtitle(module::description);
        content().add(body);
        reload();
    }

    /** 当前屏幕（模块页）；客户端未就绪时返回 {@code null}（ESC 直接回游戏，不抛异常） */
    private static Screen currentScreen() {
        Minecraft client = Minecraft.getInstance();
        return client == null ? null : client.gui.screen();
    }

    // ── 生命周期 ──

    @Override
    protected void init() {
        super.init();
        // 同一所有者重复订阅会覆盖，因此回到本窗口时不会残留多个监听
        ClientEventBus.subscribe(TICK_OWNER, ClientEventType.TICK, event -> onTick());
        // 从子界面（目标附魔选择器）返回时重建正文：行上的计数是构建时快照
        if (minecraft != null) minecraft.execute(this::reload);
    }

    @Override
    public void removed() {
        ClientEventBus.unsubscribeAll(TICK_OWNER);
        super.removed();
    }

    /**
     * 键盘转发：录制态优先（与 {@code ModuleScreen#keyPressed} 同序：先录制、再输入框、最后父类）。
     */
    @Override
    public boolean keyPressed(KeyEvent event) {
        if (SettingKeybind.keyPressed(event)) return true;
        return super.keyPressed(event);
    }

    /** 鼠标转发：录制态优先（键位可以绑鼠标按键），录制中吞掉整次点击 */
    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean consumed) {
        if (event != null && SettingKeybind.mousePressed(event.button())) return true;
        return super.mouseClicked(event, consumed);
    }

    /** 每秒取一次新快照：概览页整页重画（纯读数），设置与日志页只换状态条不重建正文（保护输入框） */
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

    /** 左 / 右键当前是否按下（重建前的安全闸门，取值口径与其它控制台一致） */
    private static boolean mousePressed(int button) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.getWindow() == null) return false;
        return GLFW.glfwGetMouseButton(client.getWindow().handle(), button) == GLFW.GLFW_PRESS;
    }

    // ── 重建 ──

    /** 重建整页内容（取新状态条快照 + 重排当前页） */
    public void reload() {
        // 重建会丢弃整棵控件树，而文本框焦点与键位录制态都是静态字段：不清就会留在已被丢弃的实例上
        SettingTextBox.clearFocus();
        SettingKeybind.clearCapture();
        status = Status.capture(module);
        body.rebuild();
    }

    /**
     * 延后一 tick 重建整页。
     *
     * <p><b>做什么</b>：把 {@link #reload()} 推到下一次客户端 tick 执行。</p>
     * <p><b>为什么</b>：设置行里改值会影响页面结构（如「提示音」关掉后音效行整行消失），当场重建会把正在响应的
     * 那个控件连同控件树一起摘掉，抬起事件落到空处，实机表现为「点了没反应」。与 {@link #switchTab} 同一手法。</p>
     */
    public void scheduleReload() {
        if (minecraft != null) {
            minecraft.execute(this::reload);
        } else {
            reload();
        }
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

    /** 宿主客户端实例（页内打开选择器等子窗口用） */
    public Minecraft client() {
        return minecraft;
    }

    /** 已收起的折叠块键集合；页内折叠块用它记住展开 / 收起状态（重建后不丢失） */
    public Set<String> collapsedSections() {
        return collapsedSections;
    }

    /** 登记本帧要显示的 tooltip：走项目全局 TooltipLayer（PanelScreen 每帧自动绘制） */
    @Override
    public void tip(String text, float x, float y) {
        TooltipLayer.show(text, x, y);
    }

    // ── 页面装配 ──

    private void buildInto(CompactStack stack) {
        // 顶栏：状态文字 / 快捷键徽章 / 模块开关，压缩右对齐
        stack.add(new ConsoleHeaderBar(module));
        stack.add(new StatusStrip());
        buildTabs(stack);

        switch (tab) {
            case OVERVIEW -> new LibrarianOverviewPage(this, module).build(stack);
            case SETTINGS -> new LibrarianSettingsPage(this, module).build(stack);
            case LOGS -> new LibrarianLogPage(this, module).build(stack);
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

    /**
     * 状态条两行八格的只读快照
     * （格位：模块 / 状态机 / 目标附魔 / 搜索半径 / 最高价格 / 命中策略 / 背包 / 自检）。
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

        /**
         * 取当前快照。
         *
         * <p><b>为什么每次都现读模块</b>：状态条是「实况」，不缓存任何模块字段；
         * 自检格与模块启用前的自检同一份判据（{@link AutoLibrarianModule#selfCheck()}），
         * 缺项数一眼可见，避免进了控制台还要去开模块才知道缺什么。</p>
         */
        private static Status capture(AutoLibrarianModule module) {
            if (module == null) return empty();
            List<String> missing = module.selfCheck();
            LibrarianState state = module.currentState();
            return new Status(new String[]{
                "§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用"),
                "§7状态机：" + (module.isEnabled() && state != null ? "§f" + state.displayName() : "§8未初始化"),
                "§7目标附魔：§f" + module.settings().targetEnchantments().size() + " 项",
                "§7搜索半径：§f" + module.settings().searchRadius + " 格",
                "§7最高价格：§f" + module.settings().maximumEmeraldPrice,
                "§7命中策略：§f" + (module.settings().removeTargetOnFound ? "找到后移除" : "找到后保留"),
                "§7背包：§f讲台" + module.countItem(net.minecraft.world.item.Items.LECTERN)
                    + " 书" + module.countItem(net.minecraft.world.item.Items.BOOK)
                    + " 绿宝石" + module.countItem(net.minecraft.world.item.Items.EMERALD),
                missing.isEmpty() ? "§a自检通过" : "§e自检缺 " + missing.size() + " 项"
            });
        }

        private String cell(int index) {
            return cells[index];
        }
    }

    private final class StatusStrip implements CompactElement {

        /** 状态条每行行高：与其它控制台同一档 */
        private static final float ROW_HEIGHT = 18f;
        private static final float CELL_PAD = 6f;
        private static final int COLUMNS = 4;
        /** 列间留白：文字按「列宽 − 该值」截断，避免长文本压到下一列 */
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

    /** 整页内容容器：{@link #rebuild()} 直接换掉内部堆叠，页签切换 / 刷新不重开窗口、不重播入场动画 */
    private final class Body implements CompactElement {

        private CompactStack stack = new CompactStack(LINE_GAP);

        private void rebuild() {
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
    }
}
