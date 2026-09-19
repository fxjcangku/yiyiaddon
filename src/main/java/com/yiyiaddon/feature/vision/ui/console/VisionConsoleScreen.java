package com.yiyiaddon.feature.vision.ui.console;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.vision.VisionModule;
import com.yiyiaddon.feature.vision.config.VisionSettings;
import com.yiyiaddon.feature.vision.config.VisionTexts;
import com.yiyiaddon.feature.vision.ui.VisionSelectors;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleHeaderBar;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.ShapeMode;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_COLOR;
import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_CYCLE;

/**
 * 透视控制台：方块与实体两个模式的唯一设置界面（用户口径「做在一个界面也是控制台样式」）。
 *
 * <p><b>骨架逐字照既有控制台</b>（管理员检测 / 水源显示 / 杀戮光环同一套）：窗口标题、标题下的模块说明、
 * 顶栏（状态文字 / 快捷键徽章 / 模块开关）、两行六格状态条、页签行、可整体替换的内容容器、
 * 每秒自动刷新、底部「刷新 / 关闭」、tooltip 走项目全局 {@link TooltipLayer} —— 全部同一套写法与数值，
 * 不自造第二套分页壳（第 181 条）。</p>
 *
 * <p><b>页签</b>：{@code 概览 / 方块 / 实体}。方块与实体两页的设置项一一对应（开关 / 目标 / 范围 /
 * 显示框 / 显示射线 / 框样式 / 颜色），行构件由本类统一提供，避免两页各写一份（第 169 条）。</p>
 *
 * <p><b>改动即写盘</b>：所有设置行的回调都走 {@code module.persistSettings()}（第 173 条）。</p>
 */
public final class VisionConsoleScreen extends PanelScreen implements ConsoleHost {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.vision.console";

    /** 概览页自动刷新间隔：20 tick 一次（其余控制台同口径） */
    private static final int AUTO_REFRESH_TICKS = 20;

    /** 内容行间距（与其它控制台同一数值） */
    private static final float LINE_GAP = 6f;

    /** 正文字号（与其它控制台同一数值） */
    private static final float TEXT_SIZE = 11f;

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源（两个页共用） */
    static final VisionSettings DEFAULTS = new VisionSettings();

    /** 页签：一屏只显示一个模式的内容 */
    private enum Tab {
        OVERVIEW(VisionTexts.TAB_OVERVIEW),
        BLOCK(VisionTexts.TAB_BLOCK),
        ENTITY(VisionTexts.TAB_ENTITY);

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final VisionModule module;
    private final Body body = new Body();

    /** 已收起的折叠块键集合（本窗口生命周期内有效，重建不丢） */
    private final Set<String> collapsedSections = new HashSet<>();

    /** 当前页签 */
    private Tab tab = Tab.OVERVIEW;
    /** 自动刷新计数 */
    private int autoRefreshTicks;
    /** 顶部状态条的只读快照：每秒刷新一次（其余页不重建正文，只换它） */
    private Status status = Status.empty();

    /**
     * @param parent 上级屏幕（模块页）——ESC / 返回键回到它
     * @param module 归属模块：控制台读写它的设置，不新增任何模块侧成员
     */
    public VisionConsoleScreen(Screen parent, VisionModule module) {
        super("透视控制台", parent);
        this.module = module;
        // 标题下的模块说明：与模块页标题下那行同源（用户 2026-09-17 口径：控制台里也要有说明）
        setSubtitle(module::description);
        content().add(body);
        reload();
    }

    // ── ConsoleHost ──

    /** 登记本帧要显示的 tooltip：走项目全局 TooltipLayer */
    @Override
    public void tip(String text, float x, float y) {
        TooltipLayer.show(text, x, y);
    }

    // ── 对外（页与页内构件用） ──

    /** 宿主客户端实例（页内打开选择器用） */
    public Minecraft client() {
        return minecraft;
    }

    /** 已收起的折叠块键集合 */
    public Set<String> collapsedSections() {
        return collapsedSections;
    }

    /** 触发整页重建（选择器返回后各页调用；与 {@link #reload()} 同一入口） */
    public void refresh() {
        reload();
    }

    // ── 生命周期 ──

    @Override
    protected void init() {
        super.init();
        // 本项目在 init 里订阅，同一所有者重复订阅会覆盖，因此回到本窗口时不会残留多个监听
        ClientEventBus.subscribe(TICK_OWNER, ClientEventType.TICK, event -> onTick());
        // 从子界面（选择器）返回时重建正文：行上的名单状态文字是构建时的快照
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
     * <p>概览页整页重画（纯读数，重画无副作用）；其余页只换顶部状态条快照，不重建正文 ——
     * 正文带可交互控件，每秒重建会把正在编辑的输入框与正在点的开关一起抹掉。</p>
     */
    private void onTick() {
        if (++autoRefreshTicks < AUTO_REFRESH_TICKS) return;
        autoRefreshTicks = 0;
        // 鼠标按住时不重建：正在按的那个按钮会被摘掉，抬起事件落到空处
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

    /** 重建整页内容（取新状态条快照 + 重排当前页） */
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

    // ── 页面装配 ──

    private void buildInto(CompactStack stack) {
        // 顶栏：状态文字 / 快捷键徽章 / 模块开关，压缩右对齐（用户 2026-09-17 口径）
        stack.add(new ConsoleHeaderBar(module));
        stack.add(new StatusStrip());
        buildTabs(stack);

        switch (tab) {
            case OVERVIEW -> new VisionOverviewPage(this, module).build(stack);
            case BLOCK -> new VisionBlockPage(this, module).build(stack);
            case ENTITY -> new VisionEntityPage(this, module).build(stack);
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

    // ── 行构件（方块页与实体页共用，同源只此一份） ──

    /** 开关行：改动即写盘（第 173 条）；行尾 ↺ 恢复本行默认值 */
    ConsoleRow toggleRow(String label, String hint, Supplier<Boolean> getter, Consumer<Boolean> setter,
                         Supplier<Boolean> defaultValue) {
        return new ConsoleRow(this, () -> label, hint, null,
            List.of(new Ctl(new SettingToggle(getter, value -> {
                setter.accept(value);
                module.persistSettings();
            })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    module.persistSettings();
                    refresh();
                }, label)));
    }

    /** 范围行：整数值数字框（第 123 条：数值一律「输入框带加减」，禁止滑块）；行尾 ↺ 恢复本行默认值 */
    ConsoleRow rangeRow(String label, String hint, Supplier<Integer> getter, Consumer<Integer> setter,
                        Supplier<Integer> defaultValue) {
        return new ConsoleRow(this, () -> label, hint, null,
            List.of(new Ctl(new SettingNumberBox(VisionSettings.RANGE_MIN, VisionSettings.RANGE_MAX, 1, "%.0f",
                () -> (double) getter.get(),
                value -> {
                    setter.accept(value.intValue());
                    module.persistSettings();
                })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    module.persistSettings();
                    refresh();
                }, label)));
    }

    /**
     * 颜色行：调色板直接改传入的 EspColor，关闭窗口即生效（第 151 条）；行尾带可见提示（第 213 条）。
     *
     * <p>颜色字段是 {@code final} 对象，行尾 ↺ 只能把出厂色的分量就地写回同一个对象。</p>
     */
    ConsoleRow colorRow(String label, String hint, EspColor color, EspColor defaultColor) {
        return new ConsoleRow(this, () -> label, hint, COMMENT_COLOR,
            List.of(new Ctl(new SettingColorPicker(label, color, module::persistSettings)),
                ConsoleWidgets.resetCtl(() -> {
                    copyColor(color, defaultColor);
                    module.persistSettings();
                    refresh();
                }, label)));
    }

    /** 框样式行：候选 = 线框 / 面 / 两者（第 143 条逐字），改动即写盘；行尾 ↺ 恢复本行默认值 */
    ConsoleRow shapeRow(String label, String hint, Supplier<ShapeMode> getter, Consumer<ShapeMode> setter,
                        Supplier<Integer> defaultValue) {
        return new ConsoleRow(this, () -> label, hint, COMMENT_CYCLE,
            List.of(new Ctl(new SettingCycle(List.of(ShapeMode.labels()),
                () -> getter.get().index(), index -> {
                setter.accept(ShapeMode.of(index));
                module.persistSettings();
            })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(ShapeMode.of(defaultValue.get()));
                    module.persistSettings();
                    refresh();
                }, label)));
    }

    /**
     * 名单行：名称 + 状态文字 …… [选择] [清空] [↺]。
     *
     * <p>状态文字写进行标签（与杀戮光环的目标实体行同一写法），打开的选择器是项目通用
     * {@code SelectorScreen}（带图标、分组折叠、左右两栏多选，见 {@link VisionSelectors}）。</p>
     *
     * @param status 名单状态文字（未选 / 已选 N / M 项）的取值器
     * @param open   打开选择器
     * @param clear  清空名单（名单为空时无动作）
     * @param resetToDefaults 把名单恢复成出厂值（本模块出厂为空名单）并落盘刷新
     */
    ConsoleRow listRow(String label, String hint, Supplier<String> status, Runnable open, Runnable clear,
                       Runnable resetToDefaults) {
        return new ConsoleRow(this, () -> label + " §8· §7" + status.get(), hint, null,
            List.of(new Ctl(new Button(VisionTexts.SELECT, open), VisionTexts.SELECT_HINT_BLOCK),
                new Ctl(new Button(VisionTexts.CLEAR, () -> {
                    clear.run();
                    refresh();
                }), VisionTexts.CLEAR_HINT),
                ConsoleWidgets.resetCtl(resetToDefaults, label)));
    }

    /** 把出厂颜色就地写给行内控件持有的那个颜色对象（字段是 final，不能换引用） */
    private static void copyColor(EspColor target, EspColor source) {
        target.rgb(source.rgb()).alpha(source.alpha()).rainbow(source.rainbow())
            .rainbowSpeed(source.rainbowSpeed()).rainbowOffset(source.rainbowOffset());
    }

    // ── 顶部状态条：不管在哪一页都看得到两个模式现在在干什么 ──

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

        private static Status capture(VisionModule module) {
            if (module == null) return empty();
            VisionSettings settings = module.settings();
            int blockHits = module.blockScanner().total();
            String blockScan = module.blockScanner().scanning() ? " §8· §7扫描中" : "";
            String blockOverflow = blockHits > module.blockScanner().visible().size()
                ? " §8· §7只画最近 " + module.blockScanner().visible().size() : "";
            return new Status(new String[]{
                "§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用"),
                "§7方块透视：" + (settings.blockEnabled ? VisionTexts.ON : VisionTexts.OFF),
                "§7方块目标：§f" + settings.blockTargets.size() + " §7项",
                "§7方块命中：§f" + blockHits + " §7个" + blockScan + blockOverflow,
                "§7实体透视：" + (settings.entityEnabled ? VisionTexts.ON : VisionTexts.OFF),
                "§7实体命中：§f" + module.entityScanner().visible().size() + " §7个"
            });
        }

        private String cell(int index) {
            return cells[index];
        }
    }

    private final class StatusStrip implements CompactElement {

        /** 状态条每行行高（与其它控制台同一数值） */
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

    /** 整页内容容器：{@link #rebuild()} 换掉内部堆叠，刷新不重开窗口、不重播入场动画 */
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
