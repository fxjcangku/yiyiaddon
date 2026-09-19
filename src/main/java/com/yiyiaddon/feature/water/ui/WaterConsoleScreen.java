package com.yiyiaddon.feature.water.ui;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.water.WaterESPModule;
import com.yiyiaddon.feature.water.config.WaterSettings;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleHeaderBar;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_COLOR;
import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_CYCLE;

/**
 * 水源显示控制台：10 个设置项按旧项目原 3 个设置组 + 基础参数拆成页签
 * （概览 / 灌溉范围显示 / 建议放水点 / 高级选项）。
 *
 * <p><b>骨架逐字照既有控制台</b>（自动骨粉 / 发包秒破 / 传送；同源件
 * {@link com.yiyiaddon.ui.console.ConsoleWidgets} 直接复用）：窗口标题、模块说明副标题、
 * 顶栏 {@link ConsoleHeaderBar}（状态文字 + 快捷键徽章 + 模块开关）、顶部状态条（两行六格）、
 * 页签行、可整体替换的 {@link Body}、{@link #reload()} 重建、{@link #switchTab} 延到下一 tick、
 * 每秒自动刷新（只在概览页整页重建）、底部「刷新 / 关闭」、tooltip 悬浮层 —— 全部同一套写法与同一套数值。</p>
 *
 * <p><b>为什么要这套骨架</b>：本控制台此前是一个「长列表 + 顶栏」的简化页（无页签、无状态条），
 * 与其它 20 多个模块的控制台形态不一致，玩家在模块中心点进来会觉得「这个模块没做完」
 * （用户 2026-09-18 实机截图）。设置项本身 <b>一字未改</b>，只是补上分类与状态。</p>
 *
 * <p><b>设置项逐字</b>（旧 {@code WaterESPModule :82-151}，51 号第五节全表）：
 * 名称 / 描述 / 默认值 / 取值域原样；旧滑条形态不保留（差异 D-13-01）——
 * {@code 扫描半径}(4~32) 与 {@code 渲染距离}(4~64) 走 {@link SettingNumberBox}；
 * 样式枚举走 {@link SettingCycle}，候选 = {@code ShapeMode.labels()}
 * （{@code 线框 / 面 / 两者}，第 143 条）。</p>
 *
 * <p><b>分组</b>：{@code 灌溉范围显示 / 建议放水点 / 高级选项} 三个分组名逐字
 * （旧 {@code :44-46}）；旧默认组两项（扫描半径 / 渲染距离）在旧框架无组名平铺，本控制台
 * <b>不新造分类名</b>（第 209~210 条），把它们放进「概览」页的「基础参数」块里。
 * 颜色 / 样式行随其开关所在分组折叠。改动即写盘（第 173 条）。</p>
 */
public final class WaterConsoleScreen extends PanelScreen implements ConsoleHost {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.water.console";

    /** 概览页自动刷新间隔：20 tick 一次（其余控制台同口径） */
    private static final int AUTO_REFRESH_TICKS = 20;

    private static final float LINE_GAP = 6f;
    private static final float TEXT_SIZE = 11f;

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final WaterSettings DEFAULTS = new WaterSettings();

    /** 页签：一屏只显示一类内容（页签名 = 旧项目设置组名 + 概览）。 */
    private enum Tab {
        OVERVIEW("概览"),
        RANGE("灌溉范围显示"),
        SUGGEST("建议放水点"),
        ADVANCED("高级选项");

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final WaterESPModule module;
    private final WaterSettings settings;
    private final Body body = new Body();

    /** 折叠块记忆（整页重建不丢失） */
    private final Set<String> collapsedSections = new HashSet<>();

    /** 当前页签 */
    private Tab tab = Tab.OVERVIEW;
    /** 自动刷新计数 */
    private int autoRefreshTicks;
    /** 顶部状态条的只读快照：每秒刷新一次 */
    private Status status = Status.empty();

    /** @param parent 上级屏幕（模块页）——ESC / 返回键回到它 */
    public WaterConsoleScreen(Screen parent, WaterESPModule module) {
        super("水源显示控制台", parent);
        this.module = module;
        // 标题下的模块说明：与模块页标题下那行同源（用户 2026-09-17 口径：控制台里也要有说明）
        setSubtitle(module::description);
        this.settings = module.settings();
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
     * <p><b>概览页：</b>整页重画（纯读数 + 两个数字框，无编辑态）。<b>其余页：</b>只换状态条，不重建正文
     * —— 正文带可交互控件，每秒重建会把正在编辑的输入框与正在点的开关一起抹掉。</p>
     */
    private void onTick() {
        if (++autoRefreshTicks < AUTO_REFRESH_TICKS) return;
        autoRefreshTicks = 0;
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
        com.yiyiaddon.ui.widget.SettingTextBox.clearFocus();
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

    /** 登记本帧要显示的 tooltip：走项目全局 TooltipLayer（实现 {@link ConsoleHost}） */
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
            case OVERVIEW -> buildOverview(stack);
            case RANGE -> buildRange(stack);
            case SUGGEST -> buildSuggest(stack);
            case ADVANCED -> buildAdvanced(stack);
        }

        buildFooter(stack);
    }

    private void buildTabs(CompactStack stack) {
        List<Ctl> tabs = new java.util.ArrayList<>();
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

    // ── 概览页：只读现状 + 基础参数（旧框架无组名平铺的两项） ──

    private void buildOverview(CompactStack stack) {
        FoldSection state = new FoldSection("当前状态", "ov:state", collapsedSections());
        state.content().add(new Note(this, "§7模块："
            + (module.isEnabled() ? "§a运行中" : "§8未启用")));
        state.content().add(new Note(this, "§7扫描到的水源：§f" + module.waterCount() + " §7处"
            + " §8/ §7建议放水点：§f" + module.suggestionCount() + " §7个"));
        state.content().add(new Note(this, "§7显示开关：灌溉范围" + onOff(settings.renderRange)
            + " §8/ §7建议点" + onOff(settings.renderSuggestion)
            + " §8/ §7水源方块" + onOff(settings.renderSource)));
        state.content().add(new Note(this, "§7扫描与渲染范围：半径 §f" + settings.scanRadius
            + " §7格 / 距离 §f" + settings.renderDistance + " §7格"));
        stack.add(state);

        FoldSection basic = new FoldSection("基础参数", "ov:basic", collapsedSections());
        basic.content().add(numberRow("扫描半径", "扫描玩家周围多少格内的水源",
            4, 32, () -> (double) settings.scanRadius,
            value -> settings.scanRadius = value.intValue(),
            () -> (double) DEFAULTS.scanRadius));
        basic.content().add(numberRow("渲染距离", "只渲染玩家周围多少格内的框（可以比扫描半径大）",
            4, 64, () -> (double) settings.renderDistance,
            value -> settings.renderDistance = value.intValue(),
            () -> (double) DEFAULTS.renderDistance));
        stack.add(basic);

        FoldSection guide = new FoldSection("设置指引", "ov:guide", collapsedSections());
        guide.content().add(new Note(this, "§7按旧项目设置组分页：§f灌溉范围显示 §7/ §f建议放水点 §7/ §f高级选项"));
        guide.content().add(new Note(this, "§7颜色与样式跟随各自开关所在的分组；改动即时保存，关窗与重启后仍生效"));
        stack.add(guide);
    }

    private void buildRange(CompactStack stack) {
        FoldSection range = new FoldSection("灌溉范围显示", "range", collapsedSections());
        range.content().add(toggleRow("显示灌溉范围", "显示每桶水能覆盖的 9×9 耕地范围（蓝色大框）",
            () -> settings.renderRange, value -> settings.renderRange = value,
            () -> DEFAULTS.renderRange));
        range.content().add(colorRow("范围颜色", "灌溉范围的颜色", settings.rangeColor, DEFAULTS.rangeColor));
        stack.add(range);
    }

    private void buildSuggest(CompactStack stack) {
        FoldSection suggest = new FoldSection("建议放水点", "suggest", collapsedSections());
        suggest.content().add(toggleRow("显示建议点", "在已有水源的上下左右显示可放水位置（红色框）",
            () -> settings.renderSuggestion, value -> settings.renderSuggestion = value,
            () -> DEFAULTS.renderSuggestion));
        suggest.content().add(colorRow("建议点颜色", "建议放水点的颜色",
            settings.suggestionColor, DEFAULTS.suggestionColor));
        suggest.content().add(cycleRow("建议点样式", "Lines 线框 / Sides 面 / Both 两者",
            () -> settings.suggestionShapeMode, mode -> settings.suggestionShapeMode = mode,
            () -> DEFAULTS.suggestionShapeMode.index()));
        stack.add(suggest);
    }

    private void buildAdvanced(CompactStack stack) {
        FoldSection advanced = new FoldSection("高级选项", "advanced", collapsedSections());
        advanced.content().add(toggleRow("显示水源方块",
            "用小框标出水源方块本身（通常不需要，主要看灌溉范围即可）",
            () -> settings.renderSource, value -> settings.renderSource = value,
            () -> DEFAULTS.renderSource));
        advanced.content().add(colorRow("水源方块颜色", null, settings.sourceColor, DEFAULTS.sourceColor));
        advanced.content().add(cycleRow("水源方块样式", "Lines 线框 / Sides 面 / Both 两者",
            () -> settings.sourceShapeMode, mode -> settings.sourceShapeMode = mode,
            () -> DEFAULTS.sourceShapeMode.index()));
        stack.add(advanced);
    }

    private static String onOff(boolean value) {
        return value ? "§a开" : "§8关";
    }

    // ── 行构件组装（与自动农场设置页同一套写法） ──

    /** 开关行：改动即写盘（第 173 条）；行尾 ↺ 恢复本行默认值 */
    private ConsoleRow toggleRow(String label, String hint,
                                 java.util.function.Supplier<Boolean> getter,
                                 java.util.function.Consumer<Boolean> setter,
                                 java.util.function.Supplier<Boolean> defaultValue) {
        return new ConsoleRow(this, () -> label, hint, null,
            List.of(new Ctl(new SettingToggle(getter, value -> {
                setter.accept(value);
                module.persistSettings();
            })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    module.persistSettings();
                    reload();
                }, label)));
    }

    /** 数字行（旧滑条 → 数字框，D-13-01）：步进 1、整数值，改动即写盘；行尾 ↺ 恢复本行默认值 */
    private ConsoleRow numberRow(String label, String hint, double min, double max,
                                 java.util.function.Supplier<Double> getter,
                                 java.util.function.Consumer<Double> setter,
                                 java.util.function.Supplier<Double> defaultValue) {
        return new ConsoleRow(this, () -> label, hint, null,
            List.of(new Ctl(new SettingNumberBox(min, max, 1, "%.0f",
                getter, value -> {
                setter.accept(value);
                module.persistSettings();
            })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    module.persistSettings();
                    reload();
                }, label)));
    }

    /**
     * 颜色行：调色板直接改传入的 EspColor，改动即写盘；行尾带可见提示（第 213 条）。
     *
     * <p>颜色字段是 {@code final} 对象，行尾 ↺ 只能把出厂色的分量就地写回同一个对象。</p>
     */
    private ConsoleRow colorRow(String label, String hint,
                                com.yiyiaddon.ui.render.world.EspColor color,
                                com.yiyiaddon.ui.render.world.EspColor defaultColor) {
        return new ConsoleRow(this, () -> label, hint, COMMENT_COLOR,
            List.of(new Ctl(new SettingColorPicker(label, color, module::persistSettings)),
                ConsoleWidgets.resetCtl(() -> {
                    copyColor(color, defaultColor);
                    module.persistSettings();
                    reload();
                }, label)));
    }

    /** 样式行：候选 = 线框 / 面 / 两者（第 143 条逐字），改动即写盘；行尾带可见提示（第 213 条） */
    private ConsoleRow cycleRow(String label, String hint,
                                java.util.function.Supplier<ShapeMode> getter,
                                java.util.function.Consumer<ShapeMode> setter,
                                java.util.function.Supplier<Integer> defaultValue) {
        return new ConsoleRow(this, () -> label, hint, COMMENT_CYCLE,
            List.of(new Ctl(new SettingCycle(List.of(ShapeMode.labels()),
                () -> getter.get().index(), index -> {
                setter.accept(ShapeMode.of(index));
                module.persistSettings();
            })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(ShapeMode.of(defaultValue.get()));
                    module.persistSettings();
                    reload();
                }, label)));
    }

    /** 把出厂颜色就地写给行内控件持有的那个颜色对象（字段是 final，不能换引用） */
    private static void copyColor(com.yiyiaddon.ui.render.world.EspColor target,
                                  com.yiyiaddon.ui.render.world.EspColor source) {
        target.rgb(source.rgb()).alpha(source.alpha()).rainbow(source.rainbow())
            .rainbowSpeed(source.rainbowSpeed()).rainbowOffset(source.rainbowOffset());
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

        /** 取值全部来自模块只读接口与设置当前值，本类不改任何状态 */
        private static Status capture(WaterESPModule module) {
            if (module == null) return empty();
            WaterSettings settings = module.settings();
            return new Status(new String[]{
                "§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用"),
                "§7扫描半径：§f" + settings.scanRadius + " §7格",
                "§7渲染距离：§f" + settings.renderDistance + " §7格",
                "§7水源：§f" + module.waterCount() + " §7处",
                "§7建议点：§f" + module.suggestionCount() + " §7个",
                "§7显示：灌溉范围" + onOff(settings.renderRange)
                    + " §8/ §7建议" + onOff(settings.renderSuggestion)
                    + " §8/ §7方块" + onOff(settings.renderSource)
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

    // ── 页面容器：整页可重建 ──

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
