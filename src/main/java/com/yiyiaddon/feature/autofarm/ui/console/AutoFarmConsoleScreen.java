package com.yiyiaddon.feature.autofarm.ui.console;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleHeaderBar;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.screen.PanelScreen;
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
 * 自动农场控制台：把旧项目模块页承载不下的设置项按用途拆成整屏页签（D2 拍板）。
 *
 * <p><b>骨架逐字照自动挖矿控制台</b>（通用件 {@link com.yiyiaddon.ui.console.ConsoleWidgets}
 * 直接复用）：窗口标题、状态条（两行八格）、页签行、可整体重建的正文容器、每秒自动刷新、
 * 底部「刷新 / 关闭」、tooltip 悬浮层——同一套写法与同一套数值。</p>
 *
 * <p><b>页签划分（50 号第七节 7.2，D2）：</b>概览（只读实况）/
 * 设置（16 个可视静态项的四个分组）/ 逐作物（15 项进独立窗口的入口，D3）/
 * 点位（五张点位卡：农田范围 + 四个箱子，与模块页同一份数据）/ 日志（状态播报流水）。</p>
 */
public final class AutoFarmConsoleScreen extends PanelScreen implements ConsoleHost {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.autofarm.console";

    /** 概览页自动刷新间隔：20 tick 一次（挖矿 / 星露谷控制台同口径） */
    private static final int AUTO_REFRESH_TICKS = 20;

    // 排版常量（与挖矿控制台共用同一套数值：行内构件的尺寸取自 ConsoleMetrics）
    private static final float LINE_GAP = 6f;
    private static final float TEXT_SIZE = 11f;

    /** 页签：一屏只显示一类内容（50 号 7.2 固定五页）。 */
    private enum Tab {
        OVERVIEW("概览"),
        SETTINGS("设置"),
        PER_CROP("逐作物"),
        POINTS("点位"),
        LOGS("日志");

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final AutoFarmModule module;
    private final Body body = new Body();

    /** 当前页签 */
    private Tab tab = Tab.OVERVIEW;
    /** 自动刷新计数 */
    private int autoRefreshTicks;
    /** 顶部状态条的只读快照：每秒刷新一次（其余页不重建正文，只换它） */
    private Status status = Status.empty();

    /**
     * 已收起的折叠块键集合（本窗口生命周期内有效）。
     *
     * <p>分组默认状态照旧项目（51 号第二节）：{@code 辅助工具 / 运行参数 / 渲染显示} 默认收起、
     * {@code 作物选择} 默认展开。折叠状态存窗口侧：整页重建会丢弃全部控件实例，
     * 不存这里每次重建都会回到默认值。</p>
     */
    private final Set<String> collapsedSections =
        new HashSet<>(Set.of("settings:helper", "settings:logistics", "settings:render"));

    /**
     * @param parent 上级屏幕（模块页）——ESC / 返回键回到它
     * @param module 归属模块：控制台读写它的设置与点位，不新增任何模块侧成员
     */
    public AutoFarmConsoleScreen(Screen parent, AutoFarmModule module) {
        super("自动农场控制台", parent);
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
        // 从子界面（选择器 / 逐作物窗口 / 帮助页）返回时重建正文：行上的状态文字都是构建时快照
        if (minecraft != null) minecraft.execute(this::reload);
    }

    @Override
    public void removed() {
        ClientEventBus.unsubscribeAll(TICK_OWNER);
        super.removed();
    }

    /** 每秒取一次新快照：概览页整页重画（纯读数），其余页只换状态条不重建正文（保护输入框） */
    private void onTick() {
        if (++autoRefreshTicks < AUTO_REFRESH_TICKS) return;
        autoRefreshTicks = 0;
        if (minecraft == null || minecraft.screen != this) return;
        // 鼠标按住时不重建：正在按的那个按钮会被摘掉，抬起事件落到空处，表现为按钮「卡住」
        if (mousePressed(GLFW.GLFW_MOUSE_BUTTON_LEFT) || mousePressed(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) return;

        if (tab == Tab.OVERVIEW || tab == Tab.POINTS) {
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
        // 重建会丢弃整棵控件树，而文本框的焦点是静态字段：不清就会留在已被丢弃的实例上
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

    /** 登记本帧要显示的 tooltip：走项目全局 TooltipLayer（PanelScreen 每帧自动绘制） */
    @Override
    public void tip(String text, float x, float y) {
        com.yiyiaddon.ui.render.TooltipLayer.show(text, x, y);
    }

    // ── 页面装配 ──

    private void buildInto(CompactStack stack) {
        // 顶栏：状态文字 / 快捷键徽章 / 模块开关，压缩右对齐（用户 2026-09-17 口径；模块页那条已撤掉）
        stack.add(new ConsoleHeaderBar(module));
        stack.add(new StatusStrip());
        buildTabs(stack);

        switch (tab) {
            case OVERVIEW -> new FarmOverviewPage(this, module).build(stack);
            case SETTINGS -> new FarmSettingsPage(this, module).build(stack);
            case PER_CROP -> new FarmPerCropPage(this, module).build(stack);
            case POINTS -> new FarmPointPage(this, module).build(stack);
            case LOGS -> new FarmLogPage(this, module).build(stack);
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
                "顶部状态条每秒自动刷新；概览与点位页整页每秒重画，其余页按这个按钮重排最新数据"),
            ConsoleWidgets.resetDefaultsCtl(this, module, this::reload),
            new Ctl(new Button("§7关闭", () -> {
                if (minecraft != null) minecraft.setScreen(null);
            }))), ButtonStrip.BUTTON_HEIGHT));
    }

    // ── 顶部状态条：不管在哪一页都看得到「现在到底在干什么」 ──

    /**
     * 状态条两行八格的只读快照（格位：模块 / 状态机 / 维度 / 农田范围 / 作物 / 锄地 / 收割模式 / 自检）。
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

        private static Status capture(AutoFarmModule module) {
            if (module == null) return empty();
            var settings = module.settings();
            List<String> missing = module.selfCheck();
            return new Status(new String[]{
                "§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用"),
                "§7状态机：§f" + module.controller().state().cn(),
                "§7锄地：" + (settings.autoTill ? "§a开" : "§c关"),
                "§7收割：§f" + settings.harvestMode,
                "§7补种：§f" + settings.plantMode,
                "§7作物：§f" + cropSummary(module),
                "§7点位：§f" + boundCount(module) + " / 6",
                missing.isEmpty() ? "§a自检通过" : "§e自检缺 " + missing.size() + " 项"
            });
        }

        /** 「作物」格：启用作物中文名拼接，未选择时灰字 */
        private static String cropSummary(AutoFarmModule module) {
            var enabled = module.getEnabledCrops();
            if (enabled.isEmpty()) return "§8未选择";
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (var crop : enabled) {
                if (i++ > 0) sb.append("、");
                sb.append(crop.displayName());
            }
            return sb.toString();
        }

        /** 「点位」格：已绑定锚点数 */
        private static int boundCount(AutoFarmModule module) {
            int bound = 0;
            for (var type : com.yiyiaddon.feature.autofarm.model.SiteType.values()) {
                if (module.site(type) != null) bound++;
            }
            return bound;
        }

        private String cell(int index) {
            return cells[index];
        }
    }

    private final class StatusStrip implements CompactElement {

        /** 状态条每行行高：与挖矿控制台同一档 */
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

    /**
     * 整页内容容器：{@link #rebuild()} 直接换掉内部堆叠，页签切换 / 刷新不重开窗口、
     * 不重播入场动画（与挖矿控制台同款做法）。
     */
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
