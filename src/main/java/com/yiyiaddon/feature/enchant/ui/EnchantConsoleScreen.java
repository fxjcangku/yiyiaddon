package com.yiyiaddon.feature.enchant.ui;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.config.EnchantSettings;
import com.yiyiaddon.feature.enchant.model.EnchantTargetMode;
import com.yiyiaddon.feature.enchant.repository.EnchantPointStore;
import com.yiyiaddon.feature.enchant.ui.console.EnchantBasicPage;
import com.yiyiaddon.feature.enchant.ui.console.EnchantCustomPage;
import com.yiyiaddon.feature.enchant.ui.console.EnchantGearPage;
import com.yiyiaddon.feature.enchant.ui.console.EnchantOverviewPage;
import com.yiyiaddon.feature.enchant.ui.console.EnchantPointPage;
import com.yiyiaddon.feature.enchant.ui.console.EnchantSelectPage;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.console.ConsoleHeaderBar;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自动附魔控制台：把基础设置、模式专属设置与点位按用途拆成页签。
 *
 * <p><b>骨架逐字照自动挖矿控制台</b>（{@link com.yiyiaddon.feature.mining.ui.MiningConsoleScreen}）：
 * 窗口标题、两行八格状态条、页签行、可整体替换的 {@link Body}、{@link #reload()} 重建、
 * {@link #switchTab} 延到下一 tick、每秒自动刷新（只在概览页整页重建）、底部「刷新 / 关闭」、
 * tooltip 悬浮层与折行——同一套写法、同一套数值（{@code ConsoleMetrics}）。</p>
 *
 * <p><b>页签划分（逐字沿用旧分组名与顺序，44 号 §五）</b>：</p>
 * <ul>
 *   <li>GEAR：概览 · 点位 · 基础设置 · 原版装备附魔</li>
 *   <li>BOOK：概览 · 点位 · 基础设置 · 原版附魔分类</li>
 *   <li>CUSTOM：概览 · 点位 · 基础设置 · 自动附魔分类 · 自定义附魔</li>
 * </ul>
 *
 * <p><b>切模式即重排页签</b>：{@link #onTargetModeChanged()} 等价旧
 * {@code 目标模式.onChanged(m -> {同步模式分组(); 刷新模式界面();})}（{@code :114-117}），
 * 其中 {@link #syncTabs()} 保留旧 {@code 同步模式分组()}（{@code :635-649}）的<b>幂等保护</b>——
 * 顺序一致时直接返回、不重建集合，避免在设置装载 / 遍历页签期间重建导致启动闪退。</p>
 *
 * <p><b>文案与设置项一字未改</b>：页签顺序、设置名、描述、取值域、点位名与卡片文案全部来自
 * 旧项目（{@code AutoEnchantBook} 的各设置分组 + {@code buildPointCard}），控制台只负责「按什么顺序
 * 排到哪一页」，不新增任何设置项或播报。旧项目 {@code ESP标点} / {@code 返回挂机视角}
 * 是 {@code .visible(() -> false)}，控制台同样<b>不出现</b>（值照旧读写）。</p>
 */
public final class EnchantConsoleScreen extends PanelScreen implements ConsoleHost {

    /** 自动刷新的 tick 订阅所有者标识（本窗口独占） */
    private static final String TICK_OWNER = "screen.enchant.console";

    /** 概览页自动刷新间隔：20 tick 一次（自动挖矿控制台同口径） */
    private static final int AUTO_REFRESH_TICKS = 20;

    private static final float LINE_GAP = 6f;
    private static final float TEXT_SIZE = 11f;

    /** tooltip 悬浮层 */
    private static final float TIP_SIZE = 10f;
    private static final float TIP_LINE = 12f;
    private static final float TIP_PAD = 6f;
    private static final float TIP_RADIUS = 6f;
    private static final float TIP_MAX_WIDTH = 320f;

    /** 页签：一屏只显示一类内容；页签名逐字沿用旧分组名。 */
    private enum Tab {
        OVERVIEW("概览"),
        POINTS("点位"),
        BASIC("基础设置"),
        GEAR("原版装备附魔"),
        VANILLA("原版附魔分类"),
        CUSTOM_GROUPS("自动附魔分类"),
        CUSTOM_TARGETS("自定义附魔");

        private final String title;

        Tab(String title) {
            this.title = title;
        }

        private String title() {
            return title;
        }
    }

    private final EnchantModule module;
    private final Body body = new Body();

    /** 当前模式可见的页签（顺序即显示顺序）；由 {@link #syncTabs()} 按目标模式同步 */
    private List<Tab> tabs = List.of();
    /** 当前页签 */
    private Tab tab = Tab.OVERVIEW;
    /** 自动刷新计数 */
    private int autoRefreshTicks;
    /** 顶部状态条的只读快照：每秒刷新一次（其余页不重建正文，只换它） */
    private Status status = Status.empty();

    /**
     * 折叠块的收起键集合（本窗口生命周期内有效）。
     *
     * <p>13 个多选组与「目标附魔」列表都靠它记住收起状态：整页重建（刷新 / 切页签 / 切模式）
     * 会丢弃全部控件实例，不存这里每次重建都会回到默认展开。</p>
     */
    private final Set<String> collapsedSections = new HashSet<>();

    /** 「自定义附魔目标」输入框的未提交草稿：页面对象每次重建都会新建，草稿必须存在窗口侧 */
    private String customDraft = "";

    /**
     * @param parent 上级屏幕（模块页）——ESC / 返回键回到它
     * @param module 归属模块：控制台读写它的设置与点位，不新增任何模块侧成员
     */
    public EnchantConsoleScreen(Screen parent, EnchantModule module) {
        super("自动附魔控制台", parent);
        this.module = module;
        // 标题下的模块说明：与模块页标题下那行同源（用户 2026-09-17 口径：控制台里也要有说明）
        setSubtitle(module::description);
        // 先同步页签再建正文：第一次打开时页签还不存在，不同步就会画出一排空页签
        syncTabs();
        content().add(body);
        reload();
    }

    // ── 生命周期 ──

    @Override
    protected void init() {
        super.init();
        // 本项目在 init 里订阅，同一所有者重复订阅会覆盖，因此回到本窗口时不会残留多个监听
        ClientEventBus.subscribe(TICK_OWNER, ClientEventType.TICK, event -> onTick());
        // 从子界面（选择器 / 帮助页）返回时重建正文：选择器的结果与行上的状态文字都是构建时取的
        // 快照，不重建就会看到「清空了还显示已选」这种旧值
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
     * <p>概览页整页重画（纯读数，重画无副作用）；其余页只换顶部状态条，
     * <b>不重建正文</b>——正文带可交互控件，每秒重建会把正在编辑的输入框（光标与未提交内容）、
     * 正在点的开关一起抹掉。</p>
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
        // 重建会丢弃整棵控件树，而文本框的焦点是静态字段：不清就会留在已被丢弃的实例上
        SettingTextBox.clearFocus();
        syncTabs();
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

    /**
     * 目标模式切换后的整页重排（旧 {@code 目标模式.onChanged} 的等价物）：
     * 先按新模式同步页签集合，再延到下一 tick 重建整页（旧 {@code 刷新模式界面()} 同样是
     * {@code mc.execute} 延后一帧，避免在控件回调栈里清掉正在交互的控件）。
     */
    public void onTargetModeChanged() {
        syncTabs();
        if (minecraft != null) {
            minecraft.execute(this::reload);
        } else {
            reload();
        }
    }

    /**
     * 按当前目标模式同步页签集合（旧 {@code 同步模式分组()}，{@code :635-649}）。
     *
     * <p><b>幂等保护逐字保留</b>：顺序一致时直接返回、不重建列表。否则「目标模式」在
     * 设置装载 / {@code fromTag} 遍历页签期间同步清空重建，会抛
     * {@code ConcurrentModificationException} 导致启动闪退。当前页签不在新集合里时退回「概览」，
     * 不保留一个已经不存在的页签。</p>
     */
    private void syncTabs() {
        List<Tab> ordered = tabsFor(module.settings().targetMode);
        if (tabs.equals(ordered)) return;
        tabs = ordered;
        if (!tabs.contains(tab)) tab = Tab.OVERVIEW;
    }

    /** 某模式下可见的页签（顺序照旧 {@code ordered}：基础设置在前，模式专属页在后） */
    private static List<Tab> tabsFor(EnchantTargetMode mode) {
        List<Tab> ordered = new ArrayList<>();
        ordered.add(Tab.OVERVIEW);
        ordered.add(Tab.POINTS);
        ordered.add(Tab.BASIC);
        if (mode == EnchantTargetMode.GEAR) ordered.add(Tab.GEAR);
        if (mode == EnchantTargetMode.BOOK) ordered.add(Tab.VANILLA);
        if (mode == EnchantTargetMode.CUSTOM) {
            ordered.add(Tab.CUSTOM_GROUPS);
            ordered.add(Tab.CUSTOM_TARGETS);
        }
        return ordered;
    }

    /** 宿主客户端实例（各页打开子窗口用；与 {@code Minecraft.getInstance()} 同源） */
    public Minecraft client() {
        return minecraft;
    }

    /** 已收起的折叠块键集合；页内折叠块用它记住展开 / 收起状态（重建后不丢失） */
    public Set<String> collapsedSections() {
        return collapsedSections;
    }

    /** 「自定义附魔目标」草稿（输入框内容在窗口侧保存，页面重建不丢未提交输入） */
    public String customDraft() {
        return customDraft;
    }

    public void customDraft(String value) {
        customDraft = value == null ? "" : value;
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
            case OVERVIEW -> new EnchantOverviewPage(this, module).build(stack);
            case POINTS -> new EnchantPointPage(this, module).build(stack);
            case BASIC -> new EnchantBasicPage(this, module).build(stack);
            case GEAR -> new EnchantGearPage(this, module).build(stack);
            case VANILLA -> new EnchantSelectPage(this, module, EnchantSettings.BOOK_GROUPS, "book").build(stack);
            case CUSTOM_GROUPS -> new EnchantSelectPage(this, module, EnchantSettings.CUSTOM_GROUPS, "custom").build(stack);
            case CUSTOM_TARGETS -> new EnchantCustomPage(this, module).build(stack);
        }

        buildFooter(stack);
    }

    private void buildTabs(CompactStack stack) {
        List<Ctl> buttons = new ArrayList<>();
        for (Tab value : tabs) {
            boolean active = value == tab;
            Button button = new Button(active ? "§b§l" + value.title() : "§7" + value.title(),
                () -> switchTab(value));
            buttons.add(new Ctl(button, () -> value == tab ? null : "切换到「" + value.title() + "」"));
        }
        stack.add(new ButtonStrip(this, buttons, ButtonStrip.TAB_HEIGHT));
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

    /** 状态条两行八格的只读快照（构建时取一次、之后每秒重取） */
    private static final class Status {

        private final String[] cells;

        private Status(String[] cells) {
            this.cells = cells;
        }

        private static Status empty() {
            return new Status(new String[]{"", "", "", "", "", "", "", ""});
        }

        private static Status capture(EnchantModule module) {
            if (module == null) return empty();
            EnchantSettings settings = module.settings();
            EnchantPointStore store = module.pointStore();
            List<String> missing = module.selfCheck();
            return new Status(new String[]{
                "§7模块：" + (module.isEnabled() ? "§a运行中" : "§8未启用"),
                "§7状态机：§f" + module.fsm().state().cn(),
                "§7目标模式：§f" + settings.targetMode.title(),
                "§7运行模式：§f" + module.currentRunMode().title(),
                "§7维度：§f" + WorldIdentity.dimensionDisplayName(WorldIdentity.dimension()),
                "§7点位：§f" + store.size() + " / 9",
                store.matchesCurrentContext() ? "§7点位世界：§a匹配" : "§7点位世界：§6不匹配",
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

    // ━━━ 页面容器：整页可重建 + tooltip 悬浮层 ━━━

    /** 整页内容容器：{@link #rebuild()} 直接换掉内部堆叠，因此切页签 / 刷新都不会重开窗口 */
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

            // 贴边修正：tip 跟随鼠标，但绝不越出内容区左右边界
            float drawX = Math.max(originX, Math.min(tipX, originX + width - tipWidth));
            float drawY = Math.max(0f, tipY - tipHeight);

            GlassPanel.shadow(canvas, drawX, drawY, tipWidth, tipHeight, TIP_RADIUS, tc.shadow, alpha, 0.9f);
            GlassPanel.frost(canvas, drawX, drawY, tipWidth, tipHeight, TIP_RADIUS, tc.window, 0.94f, alpha);
            GlassPanel.rim(canvas, drawX, drawY, tipWidth, tipHeight, TIP_RADIUS, tc.rim, alpha, 0.22f);

            float cursorY = drawY + TIP_PAD;
            int base = tc != null && !tc.dark ? tc.primaryText : 0xFFFFFF;
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
