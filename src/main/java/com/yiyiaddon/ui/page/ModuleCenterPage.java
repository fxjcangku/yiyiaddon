package com.yiyiaddon.ui.page;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.module.ModuleRegistry;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.anim.Spring;
import com.yiyiaddon.ui.component.CardIcons;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.ModuleRow;
import com.yiyiaddon.ui.navigation.PageRouter;
import com.yiyiaddon.ui.navigation.UiNavigationMemory;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import io.github.humbleui.skija.Canvas;

import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 模块中心：<b>按分类分组的模块清单</b>，每个分类头可点击收起 / 展开，整页纵向滚动。
 *
 * <p><b>为什么是清单</b>：原先是一列到底的大卡（高 74、一张卡三行内容），模块一多就要长距离滚动、
 * 也看不出归属。现在按分类分组：分类头一行（图标 + 分类名 + 模块数 + 展开箭头），
 * 其下是该分类的模块行（{@link ModuleRow} 高 24，缩进一格表示归属）。</p>
 *
 * <p><b>展开后一行一个模块</b>（用户 2026-09-21 定稿）：展开过的版本是横向并排的网格，
 * 一格里只剩「图标 + 名称 + 状态」，最长名「自动图书管理员」被截成「自动图书…」，
 * 用户原话「帮我设计一套好看能看清全部字没有....的」。现在模块行独占整行，
 * 名称完整画出、描述放不下就整段走悬停浮层——整页不出现省略号。</p>
 *
 * <p>分类头与模块行<b>同高</b>：网格的命中、滚动与悬停几何只认一套高度，两行不同高会让鼠标命中
 * 与画面错位。因此每次收窄都是收这一套共用行高：用户 2026-09-16 先说「展开的分组之后下面的模块
 * 做小一点」（36 → 28），看过后又说「不用 现在很满意 整体都缩小一下 方便阅读」，于是 28 → 24
 * 再统一收一档——本轮分类头的外观参数（图标框、字形、字号、强调条、左右内边距）也跟着按同一比例
 * 收了一档，但颜色与左对齐口径不变，命中判定仍与画面严格同源。</p>
 *
 * <p><b>几何怎么算</b>：分类头独占一整行、模块行缩进一格后也独占一整行，因此行宽只有两种取值，
 * 由下面那组几何钩子给出（绘制、悬停、点击、滚动总高度全部读同一份结果）。</p>
 *
 * <p>自带设置页的分类（{@code category.page() != null}，例如 Baritone设置）仍排在自己分类的
 * 第一行，右侧写「点击进入」，点它直接进该分类自己的页面；它是带描述的入口行，仍独占一整行。</p>
 *
 * <p><b>分类头外观</b>：不铺底色、图标不带底框的「章节标签」形态，靠左侧强调条与悬停反馈辨识；
 * 模块行才是卡片。详见 {@link #drawGroupHeader}。</p>
 *
 * <p><b>交互</b>（用户 2026-09-21 定稿）：</p>
 * <ul>
 *   <li><b>星标收藏</b>：每行右侧一枚星标，点它收藏 / 取消收藏，收藏的进顶部「常用」块与首页
 *       「常用模块」（{@link AddonConfig#favoriteModules}）。原先的「右键模块收藏」已取消
 *       ——星标看得见、点得准，右键没有提示也发现不了。</li>
 *   <li><b>分类顺序自己调</b>：分类头上的 ▲ / ▼ 按钮把该分类上移 / 下移一位，顺序落
 *       {@link AddonConfig#moduleCategoryOrder}，重启保留；也可以<b>按住分类头上下拖动</b>直接换位
 *       （用户 2026-09-21：「可以拖拉排序吗？」），两套手势改的是同一份顺序。</li>
 * </ul>
 *
 * <p>展开状态存在 {@link #EXPANDED} 静态集合里（<b>默认全部收起</b>，用户 2026-09-18 起）：本页每次
 * 导航都会重建对象，状态不能随对象丢。此外：分类头在滚动时吸顶（本页 {@code draw} 覆写），收藏的模块
 * 排在最前的「常用」块（{@link #FAVORITES}）。</p>
 */
public final class ModuleCenterPage extends CardPage {

    /** 行高：分类头与模块行共用，直接取自 {@link ModuleRow#HEIGHT}（两处各写一个字面量早晚跑偏）。 */
    private static final float ROW_HEIGHT = ModuleRow.HEIGHT;

    /**
     * 行间距：比全局 {@link CardLayout#GAP_Y}（14）更紧。
     *
     * <p>用户 2026-09-16 要求模块行「做小一点」，随后又说「整体都缩小一下 方便阅读」。行高收窄后
     * 若仍按 14 排行，整组会松散、节奏断裂；14 是留给带投影的卡片网格的（投影会向外糊开），
     * 而模块行只有 {@code frost + rim}、没有投影，6 就够分开了。分类头与模块行共用这一份间距，
     * 展开后整组节奏统一。</p>
     *
     * <p>值取自 {@link ModuleRow#ROW_GAP}：同一天「设置 / 界面」两页也改成这种紧凑行，
     * 四个页面的行节奏必须是同一个数（各写一份早晚跑偏）。</p>
     */
    private static final float ROW_GAP = ModuleRow.ROW_GAP;

    /**
     * 分类头度量：与模块行同一比例收一档（用户「整体都缩小一下 方便阅读」），字号仍压在模块名之下。
     *
     * <p>标题字号读 {@link ModuleRow#HEADER_TITLE_SIZE}：同一天模块页里的分组标题也要和分类头同一量级
     * （用户「还有点击进去的时候 模块也要缩小 现在都不对称」），两处各写一个数早晚跑偏。</p>
     */
    private static final float HEADER_PAD_X = 9f;
    private static final float HEADER_ICON_BOX = 20.5f;
    private static final float HEADER_ICON_GLYPH = 13.5f;
    private static final float HEADER_GAP = 8f;
    private static final float HEADER_TITLE_SIZE = ModuleRow.HEADER_TITLE_SIZE;
    private static final float HEADER_COUNT_SIZE = 9.5f;
    private static final float HEADER_ARROW_GLYPH = 13f;
    private static final float HEADER_ARROW_INSET = 14f;

    /**
     * 分类头左侧的强调条：左内缩、宽、上下内缩（章节的起头标记）。
     *
     * <p>随行高 28 → 24 按同比例收（宽与内缩保持原比例），上下内缩同步收一点让强调条不会被
     * 矮行挤成一条顶天立地的竖杠。</p>
     */
    private static final float HEADER_BAR_INSET = 3.5f;
    private static final float HEADER_BAR_WIDTH = 2.5f;
    private static final float HEADER_BAR_MARGIN = 8f;

    /** 标题往强调色偏的比重：让分类名读起来是「章节标签」，不是又一个可点的模块名。 */
    private static final float HEADER_TITLE_ACCENT = 0.30f;

    /**
     * 分类头的「上移 / 下移」按钮：盒子边长、两键间距、字形字号与「模块数 → 按钮」的间距。
     *
     * <p>字形取自 Material Symbols 的 {@code arrow_drop_up} / {@code arrow_drop_down}，
     * 已按开发习惯第 140 条核实字体覆盖（cmap 命中、项目内无占用）。盒子 18 是点击靶的下限：
     * 再小在 24 高的行里就点不准了。</p>
     */
    private static final float ORDER_BOX = 18f;
    private static final float ORDER_GAP = 2f;
    private static final float ORDER_GLYPH = 12f;
    private static final float ORDER_LEAD = 10f;
    private static final String ORDER_UP = "\uE5C7";
    private static final String ORDER_DOWN = "\uE5C5";

    /**
     * 拖动阈值（像素）：按下后纵向移动超过它才算「拖动换位」，否则松手按「点了一下」处理。
     *
     * <p>4 是「手不抖」的下限：再小会把正常点击判成拖动，再大则拖动起步发木。</p>
     */
    private static final float DRAG_THRESHOLD = 4f;

    /** 分类头的展开 / 收起箭头（Material 符号，均已验真存在于 MaterialSymbolsRounded.ttf）。 */
    private static final String ARROW_EXPANDED = "\uE5CF";
    private static final String ARROW_COLLAPSED = "\uE5CC";

    /**
     * <b>已展开</b>的分类 id；静态保存，本页重建后仍保持。
     *
     * <p><b>默认全部收起</b>（用户 2026-09-18 最终口径：「一进去全展开了」不可接受）：进页先看到
     * 六个分类头与各自的模块数，点哪个展开哪个。存「已展开」而不是「已收起」：空集合即「全部收起」，
     * 不需要初始化时预填，新增分类也自动是收起的
     * （与 {@code SelectorScreen.expandedGroups} 同一口径）。</p>
     */
    private static final Set<String> EXPANDED = new HashSet<>();

    /** 「常用」块的标识：它只是收藏视图，不是真分类（见 {@link #FAVORITES_CATEGORY}）。 */
    private static final String FAVORITES_ID = "favorites";

    /** 「常用」块的星标字形（Material Symbols 的 {@code star}；已按第 140 条验真：cmap 存在且未占用）。 */
    private static final String FAVORITES_ICON = "\uE838";

    /**
     * 「常用」块的分类对象。
     *
     * <p>刻意<b>不进</b> {@link CategoryRegistry}：收藏是视图而不是分类，注册进去会让「模块中心显示
     * 全部注册分类」的口径多出一个伪分类，其它页面按分类遍历时也会多出一项空分类。</p>
     */
    private static final ModuleCategory FAVORITES_CATEGORY =
            new ModuleCategory(FAVORITES_ID, "常用", "收藏的模块；点模块行右侧的星标可收藏 / 取消", FAVORITES_ICON, -1);

    /** 收藏的模块 id（按收藏先后有序）；与 {@link AddonConfig#favoriteModules} 同源，改动即落盘。 */
    private static final Set<String> FAVORITES = new LinkedHashSet<>();

    /** 收藏是否已从配置读过（配置只在首次访问时读一次，之后以内存中的集合为准）。 */
    private static boolean favoritesLoaded;

    /** 分类自定义顺序（分类 id，按显示先后）；与 {@link AddonConfig#moduleCategoryOrder} 同源。 */
    private static final List<String> CATEGORY_ORDER = new ArrayList<>();

    /** 分类顺序是否已从配置读过（同 {@link #favoritesLoaded} 的口径）。 */
    private static boolean categoryOrderLoaded;

    /** 清单里的一行：分类头（{@code module == null && !pageEntry}）、页面入口（{@code pageEntry}）或模块行。 */
    private record Row(ModuleCategory category, ModuleEntry module, boolean pageEntry) {
    }

    /**
     * 分类头一行的排版结果：标题（已按可用宽截断）、标题起点、▲ / ▼ 按钮左边界、模块数文案与右边界。
     *
     * <p><b>绘制与命中必须共用这一份</b>：按钮位置若两边各算一次，只要标题文案或字号有一处变化，
     * 就会出现「看得到点不到」；模块数文案也随之一起给（空分类不显示「0 个模块」）。</p>
     */
    private record HeaderLayout(String title, float titleX, float upX, float downX,
                                String countText, float countRight) {
    }

    private final PageRouter router;
    private final Consumer<ModuleEntry> moduleOpener;
    private final List<Row> rows = new ArrayList<>();

    /** 本轮清单里的分类（按当前显示顺序）：▲ / ▼ 的「能不能移」与移动目标都读它。 */
    private final List<ModuleCategory> categories = new ArrayList<>();

    /** 指针按住的分类 id；{@code null} = 当前没有按住任何分类头。 */
    private String pressedCategoryId;
    /** 按下时的指针 y；与 {@link #dragMoved} 一起区分「点一下（展开 / 收起）」和「拖一下（换位）」。 */
    private float pressedY;
    /** 是否已越过拖动阈值（越过才算拖动），见 {@link #onDrag}。 */
    private boolean dragMoved;
    /** 按下前的分类顺序快照：手势被取消时用它还原，绝不把半途的顺序留在内存里。 */
    private List<String> pressedOrder = List.of();

    /**
     * 行位移动画：行标识 → 当前视觉偏移（相对静态槽位，弹簧目标恒为 0，到位即 0）。
     *
     * <p>拖动换位、展开让位、新行插入都靠它：几何钩子（{@link #cardY} 与 {@link #indexAt}）
     * 读同一份偏移，画面与命中框一起动。</p>
     */
    private final Map<String, Spring> rowShift = new HashMap<>();

    /** 行位移动画的稳定时间（秒）：够快跟手，又不至于跳。 */
    private static final float ROW_SLIDE_SETTLE = 0.22f;
    /** 新出现的行从上方多少像素滑入（展开分类时新插入的模块行走这个）。 */
    private static final float ROW_INSERT_LIFT = 14f;

    public ModuleCenterPage(PageRouter router, Consumer<ModuleEntry> moduleOpener) {
        super(0);
        this.router = router;
        this.moduleOpener = moduleOpener;
        rebuildRows();
    }

    /**
     * 按分类装配清单：分类头 + （未收起时）页面入口行 + 各模块行。
     *
     * <p>末尾兜底一次：分类注册表里查不到的模块也追加进清单，避免模块在界面上凭空消失。</p>
     */
    private void rebuildRows() {
        Map<String, Float> before = new HashMap<>();
        for (int i = 0; i < rows.size(); i++) before.put(rowKey(rows.get(i)), i * (ROW_HEIGHT + ROW_GAP));
        rows.clear();
        categories.clear();
        loadFavorites();
        List<ModuleEntry> remaining = new ArrayList<>(ModuleRegistry.all());
        addFavoritesBlock();
        for (ModuleCategory category : orderedCategories()) {
            // 归到「设置」导航的分类不在模块中心出现（沿用原口径）
            if (category.settingsEntry()) continue;
            List<ModuleEntry> entries = new ArrayList<>(ModuleRegistry.byCategory(category.id()));
            entries.retainAll(remaining);
            boolean pageEntry = category.page() != null;
            if (entries.isEmpty() && !pageEntry) continue;

            categories.add(category);
            rows.add(new Row(category, null, false));
            remaining.removeAll(entries);
            if (!EXPANDED.contains(category.id())) continue;
            if (pageEntry) rows.add(new Row(category, null, true));
            for (ModuleEntry entry : entries) rows.add(new Row(category, entry, false));
        }
        for (ModuleEntry entry : remaining) rows.add(new Row(null, entry, false));
        setCardCount(rows.size());
        seedRowShift(before);
    }

    // ── 行位移动画（用户 2026-09-21：「动画有吗？」） ──

    /** 行的稳定标识：模块行用模块 id、页面入口行与分类头用分类 id —— 换位 / 展开后仍认得出是同一行。 */
    private static String rowKey(Row row) {
        if (row.module() != null) return "m:" + row.module().id();
        return (row.pageEntry() ? "p:" : "c:") + row.category().id();
    }

    /**
     * 每次重建清单后给位移动画铺场：老行从「它原来的槽位」滑向新槽位，新出现的行从上方滑入。
     *
     * <p>偏移存成「相对静态槽位的差值」，弹簧目标恒为 0，于是每帧只要 {@code update(dt)}，
     * 不必再记「谁动过」。静态位置没变的行<b>不重置弹簧</b>——拖动时清单会反复重建，
     * 重置会把正在滑动的行动画打断成跳变。</p>
     */
    private void seedRowShift(Map<String, Float> before) {
        Set<String> alive = new HashSet<>();
        for (int i = 0; i < rows.size(); i++) {
            String key = rowKey(rows.get(i));
            alive.add(key);
            Spring spring = rowShift.computeIfAbsent(key, k -> Spring.critical(ROW_SLIDE_SETTLE));
            Float old = before.get(key);
            if (old == null) {
                // 全新的一行：从上方一点滑进来（展开分类时新出现的模块行）
                startRowShift(spring, -ROW_INSERT_LIFT);
                continue;
            }
            float moved = old - i * (ROW_HEIGHT + ROW_GAP);
            if (Math.abs(moved) > 0.5f) startRowShift(spring, moved);
        }
        // 收起 / 筛选掉的行不再有动画状态，避免弹簧表无限长
        rowShift.keySet().removeIf(key -> !alive.contains(key));
    }

    /**
     * 从「偏移 offset 处」起步，目标恒为 0（= 回到自己的静态槽位）。
     *
     * <p>必须两步走：{@link Spring#set} 是「直接落到某个值」，它把目标也一起设成那个值，
     * 只调它会让行永远停在偏移上（本轮实测踩过：所有行卡在 -14 不走）。</p>
     */
    private static void startRowShift(Spring spring, float offset) {
        spring.set(offset);
        spring.setTarget(0f);
    }

    /** 第 index 行当前的视觉偏移（已到位就是 0）；绘制与命中都加它。 */
    private float rowShiftOf(int index) {
        Spring spring = rowShift.get(rowKey(rows.get(index)));
        return spring == null ? 0f : spring.value();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        for (Spring spring : rowShift.values()) spring.update(dt);
    }

    // ── 分类顺序（用户 2026-09-21：分类顺序自己调） ──

    /** 首次访问时从 {@link AddonConfig} 读分类顺序（{@code ;} 分隔）；失败或为空都按注册表权重。 */
    private static void loadCategoryOrder() {
        if (categoryOrderLoaded) return;
        categoryOrderLoaded = true;
        String raw = AddonConfig.moduleCategoryOrder;
        if (raw == null || raw.isBlank()) return;
        for (String id : raw.split(";")) {
            String trimmed = id.trim();
            if (!trimmed.isEmpty()) CATEGORY_ORDER.add(trimmed);
        }
    }

    /**
     * 分类显示顺序：先按玩家自定义的顺序取，注册表里新增的分类按原权重补在后面。
     *
     * <p>「补在后面」而不是「插进原位置」是有意的：玩家的调整要保住，版本更新新增的分类也不该
     * 打乱已经排好的顺序（新增项出现在末尾，一眼看得到）。</p>
     */
    private static List<ModuleCategory> orderedCategories() {
        loadCategoryOrder();
        List<ModuleCategory> remaining = new ArrayList<>(CategoryRegistry.all());
        List<ModuleCategory> ordered = new ArrayList<>();
        for (String id : CATEGORY_ORDER) {
            ModuleCategory category = CategoryRegistry.byId(id);
            if (category != null && remaining.remove(category)) ordered.add(category);
        }
        ordered.addAll(remaining);
        return ordered;
    }

    /** 该分类在本轮清单里的位次；不在清单里（如「常用」伪分类）返回 {@code -1}。 */
    private int indexOfCategory(String id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).id().equals(id)) return i;
        }
        return -1;
    }

    /** 该分类能否往 {@code delta} 方向移（首位不能再上移、末位不能再下移、伪分类不参与）。 */
    private boolean canMoveCategory(ModuleCategory category, int delta) {
        if (category == null) return false;
        int index = indexOfCategory(category.id());
        int target = index + delta;
        return index >= 0 && target >= 0 && target < categories.size();
    }

    /**
     * 分类上移 / 下移一位（▲▼ 按钮用）并立即落盘。
     *
     * <p>先把「当前显示顺序」整体固化成 id 列表再交换相邻两项：只动这一步，其余分类的相对顺序
     * 一位不变（不会出现「调了两个分类、第三个跟着跳」）。</p>
     */
    private void moveCategory(ModuleCategory category, int delta) {
        if (!canMoveCategory(category, delta)) return;
        int from = indexOfCategory(category.id());
        reorderCategory(from, from + delta, category.id());
        persistCategoryOrder();
        rebuildRows();
    }

    /** 当前显示顺序的 id 列表（拖动与 ▲▼ 都先把顺序固化成这一份再改）。 */
    private List<String> currentOrderIds() {
        List<String> order = new ArrayList<>();
        for (ModuleCategory item : categories) order.add(item.id());
        return order;
    }

    /** 把 {@code id} 从第 {@code from} 位搬到第 {@code to} 位（只改内存里的自定义顺序，不落盘、不重建）。 */
    private void reorderCategory(int from, int to, String id) {
        if (from < 0 || to < 0 || from == to || from >= categories.size() || to >= categories.size()) return;
        List<String> order = currentOrderIds();
        order.remove(from);
        order.add(to, id);
        CATEGORY_ORDER.clear();
        CATEGORY_ORDER.addAll(order);
    }

    /** 把当前分类顺序写进 {@link AddonConfig}（拖动松手、▲▼ 点击后各调一次）。 */
    private void persistCategoryOrder() {
        AddonConfig.moduleCategoryOrder = String.join(";", CATEGORY_ORDER);
        AddonConfig.save();
    }

    /**
     * 「常用」块：收藏的模块排在最前，顺序即收藏先后。
     *
     * <p>收藏的模块<b>同时保留在原分类里</b>（像书签，不是搬家）：从常用区点进去和从分类点进去是同一张
     * 卡片、同一份状态，<b>不</b>把它从 {@code remaining} 里移除。一个都没收藏时不插这一块，免得空占一行。</p>
     */
    private void addFavoritesBlock() {
        List<ModuleEntry> favorites = new ArrayList<>();
        for (String id : FAVORITES) {
            ModuleEntry entry = ModuleRegistry.byId(id);
            if (entry != null) favorites.add(entry);
        }
        if (favorites.isEmpty()) return;
        rows.add(new Row(FAVORITES_CATEGORY, null, false));
        if (!EXPANDED.contains(FAVORITES_ID)) return;
        for (ModuleEntry entry : favorites) rows.add(new Row(FAVORITES_CATEGORY, entry, false));
    }

    /** 首次访问时从 {@link AddonConfig} 读收藏（{@code ;} 分隔）；失败或为空都保持空集合。 */
    private static void loadFavorites() {
        if (favoritesLoaded) return;
        favoritesLoaded = true;
        String raw = AddonConfig.favoriteModules;
        if (raw == null || raw.isBlank()) return;
        for (String id : raw.split(";")) {
            String trimmed = id.trim();
            if (!trimmed.isEmpty()) FAVORITES.add(trimmed);
        }
    }

    /**
     * 切换某个模块的收藏状态并立即落盘，同时在聊天栏给一句反馈。
     *
     * <p>落盘走 {@link AddonConfig}（UI 偏好与主题 / 缩放同一份配置），成功与否都重建清单：
     * 常用区与首页「常用模块」要立刻反映变化（第 214 条，判据与动作读同一份数据）。</p>
     */
    private static void toggleFavorite(ModuleEntry entry) {
        boolean added = FAVORITES.add(entry.id());
        if (!added) FAVORITES.remove(entry.id());
        AddonConfig.favoriteModules = String.join(";", FAVORITES);
        AddonConfig.save();
        ClientChat.send("模块中心", (added ? "§a已收藏§r " : "§7已取消收藏§r ") + entry.displayName());
    }

    @Override
    public String getTitle() {
        return UiText.t("模块中心", "Modules");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("点击进入设置 · 点右侧星标收藏 · 分类头可上下调序",
                "Click to open · Star to favorite · Reorder groups");
    }

    @Override
    protected int columns() {
        // 本页的行宽逐行不同（分类头与模块行各占一整行、模块行还要缩进一格），几何由下面那组钩子给出
        return 1;
    }

    @Override
    protected float cardHeight() {
        return ROW_HEIGHT;
    }

    /**
     * 行间距：见 {@link #ROW_GAP}。必须由绘制、命中、悬停与总高度共用这一个值
     * （{@link CardPage} 已经统一走它），否则行高变了以后命中框会与画面错位。
     */
    @Override
    protected float rowGap() {
        return ROW_GAP;
    }

    // ── 几何（绘制 / 悬停 / 点击 / 总高度共用） ──

    /** 该行是否缩进一格：分类头不缩进，属于它的页面入口行与模块行都缩进（表示归属）。 */
    private boolean indented(int index) {
        Row row = rows.get(index);
        return row.module() != null || row.pageEntry();
    }

    @Override
    protected float cardWidth(float contentW, int index) {
        return Math.max(0f, indented(index) ? contentW - ModuleRow.INDENT : contentW);
    }

    @Override
    protected float cardX(float originX, float contentW, int index) {
        return indented(index) ? originX + ModuleRow.INDENT : originX;
    }

    @Override
    protected float cardY(float originY, float contentW, int index) {
        return topOf(originY, index) + rowShiftOf(index);
    }

    /** 第 index 行的顶边界：一行一格，直接按行高与行距推。 */
    private static float topOf(float originY, int index) {
        return originY + index * (ROW_HEIGHT + ROW_GAP);
    }

    /** 滚动总高度：行数 × 行高 + 行间距，再扣掉末尾多算的一个行距。 */
    @Override
    protected float contentHeight(float contentW) {
        int count = rows.size();
        return count <= 0 ? 0f : count * ROW_HEIGHT + (count - 1) * ROW_GAP;
    }

    @Override
    protected int indexAt(float mx, float my, float originX, float originY, float contentW) {
        // 吸顶条先命中：它画在视口顶部，与它下面滚过的卡片位置重叠，判定必须与绘制同源（见 stickyIndex）
        int sticky = stickyIndex(originY);
        if (sticky >= 0) {
            float top = originY - CardLayout.TOP_INSET;
            float left = cardX(originX, contentW, sticky);
            if (my >= top && my <= top + ROW_HEIGHT
                    && mx >= left && mx <= left + cardWidth(contentW, sticky)) {
                return sticky;
            }
        }
        for (int i = 0; i < rows.size(); i++) {
            float left = cardX(originX, contentW, i);
            if (mx < left || mx > left + cardWidth(contentW, i)) continue;
            float top = topOf(originY, i) + rowShiftOf(i);
            if (my >= top && my <= top + ROW_HEIGHT) return i;
        }
        return -1;
    }

    /**
     * 当前应当吸顶的分类头下标；没有（还没滚过任何分类头）返回 {@code -1}。
     *
     * <p>取「已经滚到视口上方的分类头里最靠下的那一个」：它的模块正在屏幕上，视口顶部就该显示它的名字。
     * 判据里的视口顶 = {@code originY - TOP_INSET}（{@code originY} 已扣掉滚动偏移），
     * 绘制（{@link #drawStickyHeader}）与命中（{@link #indexAt}）共用这一个方法，两处不会错位。</p>
     */
    private int stickyIndex(float originY) {
        float viewTop = originY - CardLayout.TOP_INSET;
        int sticky = -1;
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            if (row.category() == null || row.module() != null || row.pageEntry()) continue;
            if (topOf(originY, i) < viewTop + 0.5f) sticky = i;
        }
        return sticky;
    }

    /**
     * 分类头吸顶：已经滚出视口顶部的那个分类头，固定画在顶部一行，滚动时始终知道自己在看哪个分类
     * （用户 2026-09-18：「像真正的脚本辅助」）。
     *
     * <p>画在 {@code super.draw} <b>之后</b>：吸顶条要盖在滚过去的模块之上，否则两行内容叠在一起。
     * 位置与命中同源（都走 {@link #stickyIndex}），所以点吸顶条 = 点那个分类头（收起 / 展开）。</p>
     *
     * <p>吸顶条上不画 ▲ / ▼：它盖在内容之上，按钮位置会被滚动行挡出一层歧义，调序请点原位的分类头
     * （吸顶条点一下就会把该分类展开，位置随即回到原位）。</p>
     */
    @Override
    public void draw(Canvas canvas, float x, float y, float contentW, float contentH, float alpha,
                     float scrollOffset, float mouseX, float mouseY) {
        super.draw(canvas, x, y, contentW, contentH, alpha, scrollOffset, mouseX, mouseY);
        int sticky = stickyIndex(y + CardLayout.TOP_INSET - scrollOffset);
        if (sticky < 0) return;
        float sx = cardX(x, contentW, sticky);
        float sw = cardWidth(contentW, sticky);
        float hover = topHover(sx, y, sw);
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        drawHeaderBackground(canvas, sx, y, sw, alpha, hover, tc, true);
        drawHeaderContent(canvas, rows.get(sticky).category(), sx, y, sw, alpha, hover, tc, false);
    }

    /** 吸顶条是否被悬停：它就画在视口顶部那一行，与滚动偏移无关。 */
    private float topHover(float x, float y, float w) {
        float mx = frameMouseX();
        float my = frameMouseY();
        return mx >= x && mx <= x + w && my >= y && my <= y + ROW_HEIGHT ? 1f : 0f;
    }

    @Override
    protected void drawCard(Canvas canvas, int index, float x, float y, float w, float alpha, float hover,
                            ClickGuiThemeColors tc) {
        Row row = rows.get(index);
        if (row.module() != null) {
            // 整行一条：图标 + 名称 + 描述 + 星标 + 状态 + 箭头（星标是可点的收藏键）
            ModuleRow.drawRow(canvas, row.module(), x, y, w, frameMouseX(), frameMouseY(), alpha, hover,
                    FAVORITES.contains(row.module().id()), tc);
            return;
        }
        if (row.pageEntry()) {
            ModuleCategory category = row.category();
            ModuleRow.drawEntry(canvas, category.icon(), category.displayName(), category.description(),
                    UiText.t("点击进入", "Open"), tc.labelTertiary, x, y, w, alpha, hover, tc);
            return;
        }
        drawGroupHeader(canvas, row.category(), x, y, w, alpha,
                row.category().id().equals(pressedCategoryId) ? 1f : hover, tc);
    }

    @Override
    protected void onCardActivated(int index) {
        Row row = rows.get(index);
        if (row.module() != null) {
            moduleOpener.accept(row.module());
            return;
        }
        if (row.pageEntry()) {
            ModuleCategory category = row.category();
            router.open(category.page().get(), UiNavigationMemory.token(UiNavigationMemory.TOKEN_PAGE, category.id()));
            return;
        }
        // 分类头：展开 / 收起
        String id = row.category().id();
        if (!EXPANDED.remove(id)) EXPANDED.add(id);
        rebuildRows();
    }

    /**
     * 左键两处特判，其余交给基类：
     *
     * <ul>
     *   <li><b>模块行右侧的星标</b> → 收藏 / 取消收藏（命中框与绘制同源，见 {@code ModuleRow#starCenterX}）；</li>
     *   <li><b>分类头上的 ▲ / ▼</b> → 该分类上移 / 下移一位（位置与绘制同源，见 {@link #headerLayout}）。</li>
     * </ul>
     *
     * <p>原先是「右键模块收藏」：右键没有任何视觉提示，装完没人知道能这么用（用户 2026-09-21
     * 「取消右键收藏，改成星标收藏」），故移除右键分支，右键在本页不再有任何行为。</p>
     */
    @Override
    public boolean onClick(float mx, float my, float contentX, float contentY, float contentW,
                           float scrollOffset, int button) {
        if (button != GLFW.GLFW_MOUSE_BUTTON_LEFT) return false;
        float originY = contentY + CardLayout.TOP_INSET - scrollOffset;
        int index = indexAt(mx, my, contentX, originY, contentW);
        if (index < 0) return false;

        Row row = rows.get(index);
        float rowX = cardX(contentX, contentW, index);
        float rowY = topOf(originY, index);
        float rowW = cardWidth(contentW, index);

        // 吸顶条上不画 ▲▼（见 draw 的注释）：命中到吸顶条时只做展开 / 收起，
        // 否则会点到一个看不见的按钮——同一块地方画了什么，就只允许点什么（第 169 条）
        if (index == stickyIndex(originY) && my <= originY - CardLayout.TOP_INSET + ROW_HEIGHT) {
            return super.onClick(mx, my, contentX, contentY, contentW, scrollOffset, button);
        }

        if (row.module() != null) {
            float starX = ModuleRow.starCenterX(rowX, rowW);
            float starY = ModuleRow.starCenterY(rowY);
            if (Math.abs(mx - starX) <= ModuleRow.STAR_HIT && Math.abs(my - starY) <= ModuleRow.STAR_HIT) {
                toggleFavorite(row.module());
                rebuildRows();
                return true;
            }
            return super.onClick(mx, my, contentX, contentY, contentW, scrollOffset, button);
        }

        if (!row.pageEntry()) {
            ModuleCategory category = row.category();
            HeaderLayout layout = headerLayout(category, rowX, rowW);
            if (canMoveCategory(category, -1) && orderButtonHit(mx, my, layout.upX(), rowY)) {
                moveCategory(category, -1);
                return true;
            }
            if (canMoveCategory(category, 1) && orderButtonHit(mx, my, layout.downX(), rowY)) {
                moveCategory(category, 1);
                return true;
            }
            // 分类头其余区域：先按住记状态，松手时按「拖过没有」决定 换位 / 展开、收起（见 releasePress）
            if (indexOfCategory(category.id()) >= 0) {
                pressedCategoryId = category.id();
                pressedY = my;
                dragMoved = false;
                pressedOrder = currentOrderIds();
                return true;
            }
        }
        return super.onClick(mx, my, contentX, contentY, contentW, scrollOffset, button);
    }

    /**
     * 拖动分类头 = 换位（用户 2026-09-21：「可以拖拉排序吗？」）。
     *
     * <p><b>越过阈值才算拖动</b>：按下后纵向移动不足 {@link #DRAG_THRESHOLD} 像素时什么都不做，松手仍走
     * 「展开 / 收起」——否则手抖一下就会被判成调序（误触不许改配置）。</p>
     *
     * <p><b>实时换位</b>：拖动过程中就把被拖的分类插到指针所在分类的位次上并重建清单，于是那一行始终
     * 跟在指针下，不必再画一层「拖影」；落盘只在松手时做一次，中途出事也不会留下半截顺序。</p>
     */
    @Override
    public boolean onDrag(float mx, float my, float contentX, float contentY, float contentW,
                          float scrollOffset) {
        if (pressedCategoryId == null) return false;
        if (!dragMoved && Math.abs(my - pressedY) < DRAG_THRESHOLD) return true;
        dragMoved = true;

        float originY = contentY + CardLayout.TOP_INSET - scrollOffset;
        int index = indexAt(mx, my, contentX, originY, contentW);
        if (index < 0) return true;
        ModuleCategory target = rows.get(index).category();
        if (target == null) return true;
        int from = indexOfCategory(pressedCategoryId);
        int to = indexOfCategory(target.id());
        if (from < 0 || to < 0 || from == to) return true;
        reorderCategory(from, to, pressedCategoryId);
        rebuildRows();
        return true;
    }

    /**
     * 松手：拖动过 → 落盘（顺序已在拖动中实时换好）；没拖动过 → 当作「点了一下」= 展开 / 收起。
     *
     * <p>展开放在松手而不是按下，是为了让「按住拖动」与「点开合」共用同一段按下手势：按下即开合的话，
     * 一动鼠标就会先把分类展开，看着像误触。</p>
     */
    @Override
    public void releasePress() {
        super.releasePress();
        String pressed = pressedCategoryId;
        boolean moved = dragMoved;
        pressedCategoryId = null;
        dragMoved = false;
        pressedOrder = List.of();
        if (pressed == null) return;
        if (moved) {
            persistCategoryOrder();
            rebuildRows();
            return;
        }
        if (!EXPANDED.remove(pressed)) EXPANDED.add(pressed);
        rebuildRows();
    }

    /** 手势被取消（按下期间界面被关掉等）：还原按下前的顺序，半途的顺序不进配置、也不留在内存。 */
    @Override
    public void cancelPress() {
        super.cancelPress();
        boolean restore = dragMoved && !pressedOrder.isEmpty();
        pressedCategoryId = null;
        dragMoved = false;
        if (restore) {
            CATEGORY_ORDER.clear();
            CATEGORY_ORDER.addAll(pressedOrder);
            rebuildRows();
        }
        pressedOrder = List.of();
    }

    /** ▲ / ▼ 按钮的命中框（与 {@link #drawHeaderContent} 画的是同一块地方）。 */
    private static boolean orderButtonHit(float mx, float my, float buttonX, float rowY) {
        float top = orderButtonY(rowY);
        return mx >= buttonX && mx <= buttonX + ORDER_BOX && my >= top && my <= top + ORDER_BOX;
    }

    /** ▲ / ▼ 按钮的顶边：在行内垂直居中。 */
    private static float orderButtonY(float rowY) {
        return rowY + (ROW_HEIGHT - ORDER_BOX) / 2f;
    }

    @Override
    protected String emptyStateText() {
        return UiText.t("还没有注册任何功能模块", "No modules registered yet");
    }

    // ── 分类头 ──

    /**
     * 分类头：<b>「章节标签」形态</b>——不铺底色、图标不带底框，只有左侧强调条 + 悬停时的一层淡反馈。
     *
     * <p><b>为什么不铺底色</b>：模块行是卡片（{@code tc.module} 素底 + 描边），分类头若也用卡片底，
     * 五个分类一展开就是一排大色块，跟模块行抢视线、整页糊成一片（用户 2026-09-16：「也太丑了吧」
     * 「展开跟合并的样式太像了 没有鲜明的对比 然后看花眼」）。改成「静章节 + 亮卡片」两级对比后，
     * 分类一眼可辨，视觉重心落在真正可点的模块行上。</p>
     *
     * <p><b>图标为什么不带底框</b>：模块行的图标是「底框 + 字形」，分类头不给底框、字号略小、
     * 颜色偏弱，两者就不会长成同一个东西。文字起始位置仍按 {@link #HEADER_ICON_BOX} 推进，
     * 保证分类名与模块名左对齐。</p>
     *
     * <p><b>手势</b>：点一下 = 展开 / 收起（在松手时判定，见 {@link #releasePress}）；
     * 按住纵向拖动 = 换位（见 {@link #onDrag}）；右侧 ▲ / ▼ = 上下移一位。</p>
     */
    private void drawGroupHeader(Canvas canvas, ModuleCategory category, float x, float y, float w,
                                 float alpha, float hover, ClickGuiThemeColors tc) {
        drawHeaderBackground(canvas, x, y, w, alpha, hover, tc, false);
        drawHeaderContent(canvas, category, x, y, w, alpha, hover, tc, true);
    }

    /**
     * 分类头的底。
     *
     * <p>常态<b>不铺底色</b>（「章节标签」形态，见 {@link #drawGroupHeader} 的注释），只有悬停时一层
     * 很淡的反馈标明「这一行可点」。</p>
     *
     * <p>{@code sticky = true} 是吸顶条（本页 {@code draw} 覆写里画的那一条）的用法：改成实底 + 一条
     * 底线 —— 吸顶条画在滚动内容之上，必须能盖住下面滚过的模块，否则两行文字会叠在一起。</p>
     */
    private void drawHeaderBackground(Canvas canvas, float x, float y, float w, float alpha, float hover,
                                      ClickGuiThemeColors tc, boolean sticky) {
        float radius = ClickGuiThemeManager.current().metrics().moduleRadius();
        if (sticky) {
            GlassPanel.frost(canvas, x, y, w, ROW_HEIGHT, radius, tc.module, 0.92f, alpha);
            GlassPanel.fill(canvas, x, y + ROW_HEIGHT - 1f, w, 1f, 0f, tc.rim, alpha * 0.35f);
            return;
        }
        if (hover > 0.01f) {
            GlassPanel.frost(canvas, x, y, w, ROW_HEIGHT, radius, tc.surfaceHover, 0.30f * hover, alpha);
        }
    }

    /**
     * 分类头一行的排版：从右往左放「展开箭头 → ▲▼ → 模块数 →（标题）」。
     *
     * <p>按钮与模块数都从右边界反推，标题吃剩下的宽度；标题按最终可用宽截断后再被两边共用，
     * 于是绘制与命中拿到的永远是同一串文本、同一组坐标。</p>
     *
     * @param withOrderButtons {@code false} 时不画 ▲▼（吸顶条上不画：见 {@code draw} 的注释），
     *                         此时模块数直接贴到展开箭头左侧
     */
    private HeaderLayout headerLayout(ModuleCategory category, float x, float w, boolean withOrderButtons) {
        float arrowX = x + w - HEADER_ARROW_INSET;
        int moduleCount = FAVORITES_ID.equals(category.id())
                ? FAVORITES.size()
                : ModuleRegistry.byCategory(category.id()).size();
        String countText = moduleCount == 0
                ? ""
                : UiText.t(moduleCount + " 个模块", moduleCount + " modules");
        float countWidth = countText.isEmpty() ? 0f : FontRenderer.measureTextWidth(countText, HEADER_COUNT_SIZE);

        float downX = withOrderButtons ? arrowX - HEADER_ARROW_GLYPH / 2f - ORDER_LEAD - ORDER_BOX : arrowX;
        float upX = withOrderButtons ? downX - ORDER_GAP - ORDER_BOX : arrowX;
        float countRight = arrowX - HEADER_ARROW_GLYPH / 2f - ORDER_LEAD
                - (withOrderButtons ? ORDER_BOX * 2f + ORDER_GAP + ORDER_LEAD : 0f);
        float titleX = x + HEADER_PAD_X + HEADER_ICON_BOX + HEADER_GAP;
        float titleMax = Math.max(0f, countRight - countWidth - HEADER_GAP - titleX);
        return new HeaderLayout(CardLayout.ellipsize(category.displayName(), titleMax, HEADER_TITLE_SIZE),
                titleX, upX, downX, countText, countRight);
    }

    /** 与 {@link #headerLayout} 配套的重载：常规分类头都画 ▲▼。 */
    private HeaderLayout headerLayout(ModuleCategory category, float x, float w) {
        return headerLayout(category, x, w, true);
    }

    /**
     * 分类头的内容：强调条 + 图标 + 名称 + 模块数 + ▲▼ + 展开箭头。
     * 吸顶条与常规行共用这一份，两处不会画歪。
     *
     * @param withOrderButtons 是否画 ▲▼（吸顶条传 {@code false}）
     */
    private void drawHeaderContent(Canvas canvas, ModuleCategory category, float x, float y, float w,
                                   float alpha, float hover, ClickGuiThemeColors tc, boolean withOrderButtons) {
        // 左侧强调条：章节的起头标记，悬停时更亮
        GlassPanel.fill(canvas, x + HEADER_BAR_INSET, y + HEADER_BAR_MARGIN,
                HEADER_BAR_WIDTH, ROW_HEIGHT - HEADER_BAR_MARGIN * 2f,
                HEADER_BAR_WIDTH / 2f, tc.accent, alpha * (0.55f + 0.45f * hover));

        float centerY = y + ROW_HEIGHT / 2f;
        CardIcons.drawCentered(canvas, category.icon(), x + HEADER_PAD_X + HEADER_ICON_BOX / 2f, centerY,
                HEADER_ICON_GLYPH, GlassPanel.withAlpha(GlassPanel.mix(tc.accent, tc.primaryText, 0.25f), alpha));

        HeaderLayout layout = headerLayout(category, x, w, withOrderButtons);
        FontRenderer.drawTextBold(canvas, layout.title(), layout.titleX(),
                CardLayout.baseline(centerY, HEADER_TITLE_SIZE), HEADER_TITLE_SIZE,
                GlassPanel.withAlpha(GlassPanel.mix(tc.primaryText, tc.accent, HEADER_TITLE_ACCENT), alpha));

        // 模块数右对齐到按钮区左侧（与 ▲▼、展开箭头同一条右基线）；空分类不写「0 个模块」
        if (!layout.countText().isEmpty()) {
            FontRenderer.drawText(canvas, layout.countText(),
                    layout.countRight() - FontRenderer.measureTextWidth(layout.countText(), HEADER_COUNT_SIZE),
                    CardLayout.baseline(centerY, HEADER_COUNT_SIZE), HEADER_COUNT_SIZE,
                    GlassPanel.withAlpha(tc.labelTertiary, alpha));
        }

        float arrowX = x + w - HEADER_ARROW_INSET;
        boolean expanded = EXPANDED.contains(category.id());
        CardIcons.drawCentered(canvas, expanded ? ARROW_EXPANDED : ARROW_COLLAPSED, arrowX, centerY,
                HEADER_ARROW_GLYPH,
                GlassPanel.withAlpha(expanded
                        ? GlassPanel.mix(tc.labelTertiary, tc.accent, hover)
                        : GlassPanel.mix(tc.accent, tc.primaryText, hover * 0.6f), alpha));

        if (!withOrderButtons) return;
        drawOrderButton(canvas, ORDER_UP, layout.upX(), y, UiText.t("上移", "Move up"),
                canMoveCategory(category, -1), alpha, tc);
        drawOrderButton(canvas, ORDER_DOWN, layout.downX(), y, UiText.t("下移", "Move down"),
                canMoveCategory(category, 1), alpha, tc);
    }

    /**
     * 分类头上的 ▲ / ▼：把该分类上移 / 下移一位。
     *
     * <p>首位不画 ▲、末位不画 ▼（位置照旧占住，标题与模块数不会左右跳），因此不存在「点了没反应」的
     * 死按钮；悬停时叠一层淡底并转强调色，表示可点。</p>
     */
    private void drawOrderButton(Canvas canvas, String glyph, float x, float y, String tip, boolean enabled,
                                 float alpha, ClickGuiThemeColors tc) {
        if (!enabled) return;
        float top = orderButtonY(y);
        boolean hover = orderButtonHit(frameMouseX(), frameMouseY(), x, y);
        if (hover) {
            GlassPanel.frost(canvas, x, top, ORDER_BOX, ORDER_BOX, ORDER_BOX / 2f, tc.field, 0.40f, alpha);
        }
        CardIcons.drawCentered(canvas, glyph, x + ORDER_BOX / 2f, y + ROW_HEIGHT / 2f, ORDER_GLYPH,
                GlassPanel.withAlpha(GlassPanel.mix(tc.labelTertiary, tc.accent, hover ? 1f : 0.25f), alpha));
        if (hover) TooltipLayer.show(tip, frameMouseX(), frameMouseY());
    }
}
