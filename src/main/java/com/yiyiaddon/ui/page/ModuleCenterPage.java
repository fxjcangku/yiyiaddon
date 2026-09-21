package com.yiyiaddon.ui.page;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.module.ModuleCategory;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.module.ModuleRegistry;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.anim.Easing;
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
import io.github.humbleui.types.Rect;

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
 *
 * <p><b>展开 / 收起是「整块高度动画」</b>（2026-09-21，用户「ui分组展开 不流畅有卡顿」）：收起的分类也把
 * 它的成员行留在清单里，只是这些行占的高度 = 展开度 × 内容高（{@link #groupReveal}），于是展开时整块
 * 连续长出来、块下面的行跟着这一份高度同步移动，收起时反向缩回去。旧实现是「行直接插入 / 移除 + 新行抬
 * 14 像素滑入」，同一件事被拆成两段（先占位、再滑动），而且插入行会打乱基类按下标保存的悬停 / 按下状态。
 * 几何全部走 {@link #layoutSlots()}：一次排出「行顶边 + 窗口底边 + 行的展开度」，绘制、命中、滚动上限
 * 都只读这一份结果（第 169 条同源口径）。</p>
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
     * <p>它只负责「换了个位次」的那一段位移（拖动分类换位、收藏后常用块增删行）：几何钩子
     * （{@link #cardY} 与 {@link #indexAt}）读同一份偏移，画面与命中框一起动。展开 / 收起不走它 ——
     * 那是折叠块的高度在变，见 {@link #groupReveal}。</p>
     */
    private final Map<String, Spring> rowShift = new HashMap<>();

    /**
     * 折叠块的展开度弹簧（分类 id / {@link #FAVORITES_ID} → 0~1）：块内容占的高度 = 展开度 × 内容高。
     *
     * <p><b>为什么是「整块高度动画」而不是「行插入 + 行滑动」</b>（用户 2026-09-21：
     * 「ui分组展开 不流畅有卡顿」）：旧实现展开时把成员行直接插进清单 —— 块的高度<b>立刻</b>占满，
     * 下面的行再滑过去让位，看起来就是「先跳一下、再滑一段」；插入还会改变行数，而基类的悬停 / 按下
     * 动画状态是按<b>下标</b>存的（{@code CardPage#setCardCount} 原地保留同下标的状态），于是动画途中
     * 总有几行突然亮起悬停底色或缩一下按下尺寸。现在成员行始终留在清单里、只按展开度取高度：
     * 展开是一次连续的长高，行数恒定、动画状态不会串行。</p>
     */
    private final Map<String, Spring> groupReveal = new HashMap<>();

    /** 每行所属的折叠块 id（取展开度、裁剪与命中都用它）；下标与 {@link #rows} 一一对应。 */
    private final List<String> rowBlock = new ArrayList<>();

    /**
     * 每行的唯一标识（折叠块 id + 内容标识）：下标与 {@link #rows} 一一对应。
     *
     * <p>带块前缀是必需的：同一个模块可以同时在「常用」块与它自己的分类里（收藏像书签，不是搬家），
     * 只按模块 id 取标识会让这两行共用一根位移动画弹簧 —— 一行动、另一行跟着动。</p>
     */
    private final List<String> rowKeys = new ArrayList<>();

    /** 每个折叠块的行数（含块头）：块窗口高 = 展开度 ×（行数 − 1）× {@link #PITCH}。 */
    private final Map<String, Integer> blockSizes = new HashMap<>();

    /** 排版结果（下标同 {@link #rows}）：行顶边 / 裁剪下边界（所在块的窗口底边）/ 行自身的展开度。 */
    private float[] slotTop = new float[0];
    private float[] slotClipBottom = new float[0];
    private float[] slotReveal = new float[0];

    /**
     * 收尾态的行顶边（下标同 {@link #rows}）：按展开度的<b>目标值</b>（0 或 1）排出来的静态位置。
     *
     * <p>与动画中的 {@link #slotTop} 分开记，是因为滚动上限与行位移动画的起算点都必须<b>无视动画中间态</b>：
     * 动画每帧都在改 {@link #slotTop}，拿它当基准会得到两个后果（用户 2026-09-21：「模块分组展开越多
     * 就上下抖动、滚动条一直在抖在挤压」）——
     * ① 滚动条长度 = 可视高 / 内容高，内容高逐帧变，滑块就逐帧被挤压、抖动；
     * ② 展开途中再点一下（或点星标）会重建清单，位移量按「上一帧的动画中间位置」算，整块行先跳回去再滑下来。</p>
     */
    private float[] slotSettled = new float[0];

    /**
     * 收尾态的清单内容总高（排版走到的底边扣掉末尾那一个行距）：<b>滚动上限与滑块长度只读它</b>，
     * 于是展开动画全程滚动条稳如静止，动画结束也不必再修一次几何。
     */
    private float settledTotal;

    /** 上一次装配时的收尾态顶边（行标识 → 顶边）：只给「因换位而改变位置」的行起位移弹簧。 */
    private final Map<String, Float> lastTops = new HashMap<>();

    /** 行位移动画的稳定时间（秒）：够快跟手，又不至于跳。 */
    private static final float ROW_SLIDE_SETTLE = 0.22f;

    /**
     * 折叠块展开 / 收起的稳定时间（秒）：比行位移慢一档。
     *
     * <p>一整块最多 8 行（240 像素）要长出来，太快会看成「页面跳了一下」；用户 2026-09-21 报的
     * 「ui分组展开 不流畅有卡顿」正是这一档手感。</p>
     */
    private static final float GROUP_REVEAL_SETTLE = 0.28f;

    /** 一行的纵向节距（行高 + 行距）：块内各行、块窗口高度都按它推。 */
    private static final float PITCH = ROW_HEIGHT + ROW_GAP;

    /**
     * 「指针不在这一行上」用的坐标：绘制半展开的行时传它。
     *
     * <p>半展开的行不参与命中（见 {@link #hittable}），但 {@code ModuleRow} 自己会按指针位置决定
     * 悬停底色与浮层；传一个必然落在行外的坐标，浮层就不会在动画半路上闪出来（不用 {@code NaN}：
     * 行内的比较全写成「小于 / 大于」，NaN 会让它们全部为假，反而进到画浮层的分支）。</p>
     */
    private static final float MOUSE_OFF_ROW = -1.0e6f;

    public ModuleCenterPage(PageRouter router, Consumer<ModuleEntry> moduleOpener) {
        super(0);
        this.router = router;
        this.moduleOpener = moduleOpener;
        rebuildRows();
    }

    /**
     * 按分类装配清单：分类头 + 页面入口行 + 各模块行。
     *
     * <p><b>收起的分类也把内容行留在清单里</b>（展开度 0 让它不占高度、不画、点不到）：行数在展开 /
     * 收起前后恒定，基类按下标保存的悬停 / 按下状态才不会串行 —— 这是本轮「展开不流畅」的一半原因，
     * 详见 {@link #groupReveal}。</p>
     *
     * <p>末尾兜底一次：分类注册表里查不到的模块也追加进清单，避免模块在界面上凭空消失。</p>
     */
    private void rebuildRows() {
        rows.clear();
        rowBlock.clear();
        rowKeys.clear();
        blockSizes.clear();
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
            remaining.removeAll(entries);
            // 一个分类 = 一个折叠块：块头（分类头）+ 块内容（页面入口行与模块行）。
            // 收起的分类也把内容行留在清单里，靠展开度把高度收成 0（见 groupReveal 的注释）
            blockSizes.put(category.id(), entries.size() + (pageEntry ? 1 : 0) + 1);
            addRow(new Row(category, null, false), category.id());
            if (pageEntry) addRow(new Row(category, null, true), category.id());
            for (ModuleEntry entry : entries) addRow(new Row(category, entry, false), category.id());
        }
        // 末尾兜底：分类注册表里查不到的模块也进清单，各自成块（单行块，没有内容行）
        for (ModuleEntry entry : remaining) {
            String block = "orphan:" + entry.id();
            blockSizes.put(block, 1);
            addRow(new Row(null, entry, false), block);
        }
        setCardCount(rows.size());
        ensureReveals();
        layoutSlots();
        seedRowShift();
        rememberTops();
    }

    /**
     * 往清单末尾追加一行，并记下它所属的折叠块与唯一标识。
     *
     * <p>同一个块的各行必须<b>连续</b>排布（{@link #layoutSlots} 按「块头 + 内容行数」整块跳过），
     * 因此本方法只允许在同一块的块头之后依次调用。</p>
     */
    private void addRow(Row row, String block) {
        rows.add(row);
        rowBlock.add(block);
        rowKeys.add(block + '/' + rowKey(row));
    }

    /**
     * 保证每个折叠块都有一根展开度弹簧，并落定它首次出现时的取值。
     *
     * <p>首次出现（进页面、新增分类）直接落到目标值：已经是展开态的分类不该在进页面时再播一次
     * 展开动画（与 {@code CollapsibleSection#expanded} 同一口径）。</p>
     *
     * <p>顺带把所有弹簧的目标同步成 {@link #EXPANDED} 的当前状态：展开 / 收起<b>不再重建清单</b>
     * （见 {@link #onCardActivated}），目标必须在这里就位，否则本帧的收尾态几何会拿着上一次的目标
     * 算，位移动画与滚动上限都会晚一拍。</p>
     */
    private void ensureReveals() {
        for (String block : blockSizes.keySet()) {
            groupReveal.computeIfAbsent(block, id -> {
                Spring spring = Spring.critical(GROUP_REVEAL_SETTLE);
                spring.set(EXPANDED.contains(id) ? 1f : 0f);
                return spring;
            });
        }
        // 分类被移除 / 收藏清空后不再有状态，避免弹簧表无限长
        groupReveal.keySet().retainAll(blockSizes.keySet());
        for (Map.Entry<String, Spring> block : groupReveal.entrySet()) {
            block.getValue().setTarget(EXPANDED.contains(block.getKey()) ? 1f : 0f);
        }
    }

    /**
     * 排版：块头永远占满一整行，块内容排在块头下方的窗口里，窗口高 = 展开度 × 内容行数 × {@link #PITCH}。
     *
     * <p>行顶边与窗口底边一次算出来存进三份数组（下标同 {@link #rows}）：绘制、命中与内容总高都只读
     * 这一份结果，因此「块长到一半」时画面与命中框仍然严格一致（第 169 条同源口径）。</p>
     *
     * <p>块内各行按<b>自然节距</b>排列、由窗口底边裁掉：展开时看到的是「一行行从窗口里露出来」，
     * 而不是整块被拉扁。</p>
     *
     * <p>同一趟顺带按<b>目标展开度</b>排出收尾态顶边与收尾态总高（{@link #slotSettled} /
     * {@link #settledTotal}）：它们不含动画中间态，专供滚动上限与行位移动画（见字段注释）。</p>
     */
    private void layoutSlots() {
        int count = rows.size();
        if (slotTop.length != count) {
            slotTop = new float[count];
            slotClipBottom = new float[count];
            slotReveal = new float[count];
            slotSettled = new float[count];
        }
        if (count == 0) {
            settledTotal = 0f;
            return;
        }
        float y = 0f;
        float settledY = 0f;
        for (int i = 0; i < count; ) {
            String block = rowBlock.get(i);
            int content = Math.max(0, blockSizes.getOrDefault(block, 1) - 1);
            float reveal = revealOf(block);
            float target = settledRevealOf(block);
            slotTop[i] = y;                                 // 块头：不缩进、不随展开度变化
            slotClipBottom[i] = y + ROW_HEIGHT;
            slotReveal[i] = 1f;
            slotSettled[i] = settledY;                      // 块头同样不随展开度变化
            float contentTop = y + PITCH;
            float window = reveal * content * PITCH;
            for (int k = 0; k < content; k++) {
                int index = i + 1 + k;
                slotTop[index] = contentTop + k * PITCH;
                slotClipBottom[index] = contentTop + window;
                slotReveal[index] = reveal;
                slotSettled[index] = settledY + PITCH + k * PITCH;
            }
            y = contentTop + window;
            settledY += PITCH + target * content * PITCH;
            i += content + 1;                               // 块内各行连续，排完直接跳过
        }
        settledTotal = Math.max(0f, settledY - ROW_GAP);
    }

    /** 折叠块当前的展开度（0~1）；没有弹簧的块（独立行）恒为 1。 */
    private float revealOf(String block) {
        Spring spring = groupReveal.get(block);
        return spring == null ? 1f : Easing.clamp01(spring.value());
    }

    /**
     * 折叠块展开度的<b>目标值</b>（0 或 1）：动画要到达的收尾态，与动画进度无关。
     *
     * <p>滚动上限与行位移动画读它，动画途中再点几下也不会被中间态带偏（见 {@link #slotSettled}）。</p>
     */
    private float settledRevealOf(String block) {
        Spring spring = groupReveal.get(block);
        return spring == null ? 1f : Easing.clamp01(spring.target());
    }

    // ── 行位移动画（用户 2026-09-21：「动画有吗？」） ──

    /** 行的稳定内容标识：模块行用模块 id、页面入口行与分类头用分类 id —— 换位后仍认得出是同一行。 */
    private static String rowKey(Row row) {
        if (row.module() != null) return "m:" + row.module().id();
        return (row.pageEntry() ? "p:" : "c:") + row.category().id();
    }

    /**
     * 给位移动画铺场：换过位次的行从「它原来的位置」滑向新位置。
     *
     * <p>位移量 = 上一次的<b>收尾态</b>顶边 − 这一次的收尾态顶边（{@link #lastTops} / {@link #slotSettled}）。
     * 两个都是收尾态，因此：展开 / 收起带来的高度变化（动画自己在动）不会误判成位移，来回点几下也不会
     * 让整块行「先跳回去再滑下来」——用户 2026-09-21 报的抖动就是拿动画中间态当基准算出来的。</p>
     *
     * <p>逐行比较顶边而不是按「下标 × 节距」推：折叠块高度不一样，按下标推出来的位置根本不对。</p>
     *
     * <p>新出现的行不铺位移：它由折叠块的展开动画带出来，再叠一层位移就成了两次动作。
     * 收尾态位置没变的行<b>不重置弹簧</b>：拖动时清单反复装配，重置会把正在滑动的行打断成跳变。</p>
     */
    private void seedRowShift() {
        Set<String> alive = new HashSet<>(rowKeys);
        for (int i = 0; i < rows.size(); i++) {
            String key = rowKeys.get(i);
            Spring spring = rowShift.computeIfAbsent(key, k -> Spring.critical(ROW_SLIDE_SETTLE));
            Float old = lastTops.get(key);
            if (old == null) continue;
            float moved = old - slotSettled[i];
            if (Math.abs(moved) > 0.5f) startRowShift(spring, moved);
        }
        // 收起 / 筛选掉的行不再有动画状态，避免弹簧表无限长
        rowShift.keySet().retainAll(alive);
    }

    /** 记下这一次的收尾态顶边，供下一次装配算位移（见 {@link #seedRowShift}）。 */
    private void rememberTops() {
        lastTops.clear();
        for (int i = 0; i < rows.size(); i++) lastTops.put(rowKeys.get(i), slotSettled[i]);
    }

    /**
     * 从「偏移 offset 处」起步，目标恒为 0（= 回到自己的静态槽位）。
     *
     * <p>必须两步走：{@link Spring#set} 是「直接落到某个值」，它把目标也一起设成那个值，
     * 只调它会让行永远停在偏移上（166 号复盘里实测踩过：所有行卡在起始偏移不走）。</p>
     */
    private static void startRowShift(Spring spring, float offset) {
        spring.set(offset);
        spring.setTarget(0f);
    }

    /** 第 index 行当前的视觉偏移（已到位就是 0）；绘制与命中都加它。 */
    private float rowShiftOf(int index) {
        Spring spring = rowShift.get(rowKeys.get(index));
        return spring == null ? 0f : spring.value();
    }

    @Override
    public void update(float dt) {
        // 折叠块按 EXPANDED 推进展开度，行位移按各自的弹簧收敛
        for (Map.Entry<String, Spring> block : groupReveal.entrySet()) {
            block.getValue().setTarget(EXPANDED.contains(block.getKey()) ? 1f : 0f);
            block.getValue().update(dt);
        }
        for (Spring spring : rowShift.values()) spring.update(dt);
        // 先按推进后的展开度重排槽位，再交给基类做悬停命中：同一帧的画面与命中读的是同一份几何
        layoutSlots();
        // 收尾态顶边逐帧留档：展开 / 收起不再重建清单（见 onCardActivated），只有每帧记一次，
        // 下一次「换位」（拖动、收藏增删行）才有正确的起算点
        rememberTops();
        super.update(dt);
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
        // 与分类同样是一「块」：块头 + 收藏的模块行（收起时行还在清单里，高度由展开度收成 0）
        blockSizes.put(FAVORITES_ID, favorites.size() + 1);
        addRow(new Row(FAVORITES_CATEGORY, null, false), FAVORITES_ID);
        for (ModuleEntry entry : favorites) addRow(new Row(FAVORITES_CATEGORY, entry, false), FAVORITES_ID);
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
     * 切换某个模块的收藏状态并立即落盘，同时给一句面板内回执。
     *
     * <p>落盘走 {@link AddonConfig}（UI 偏好与主题 / 缩放同一份配置），成功与否都重建清单：
     * 常用区与首页「常用模块」要立刻反映变化（第 214 条，判据与动作读同一份数据）。</p>
     *
     * <p>回执走 {@link SelectionReceipt}（面板内顶部弹窗）：面板开着时 MC 把整个 HUD 藏起来，
     * 往聊天框发等于点了没反应。</p>
     */
    private static void toggleFavorite(ModuleEntry entry) {
        boolean added = FAVORITES.add(entry.id());
        if (!added) FAVORITES.remove(entry.id());
        AddonConfig.favoriteModules = String.join(";", FAVORITES);
        AddonConfig.save();
        SelectionReceipt.favorited(added, entry.displayName());
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
        return originY + slotTop[index] + rowShiftOf(index);
    }

    /**
     * 滚动总高度：<b>收尾态</b>排版走到的底边扣掉末尾那一个行距（末行之后不需要再留缝）。
     *
     * <p>取收尾态而不是动画中的高度，是为了让滚动上限与滚动条长度在展开动画全程保持不变：拿动画高度算，
     * 滑块会随每一帧的内容高被挤压、抖动，滚到底时页面还会被边界钳着来回弹（用户 2026-09-21 报的
     * 「滚动条一直在抖、在挤压」）。展开时视口下方短暂留白，动画走完正好填满。</p>
     *
     * <p>直接在 {@link #layoutSlots} 里算好：本页的行高不是一个常数（折叠块按展开度取高度），
     * 再按「行数 × 行高」推就会与画面脱节。</p>
     */
    @Override
    protected float contentHeight(float contentW) {
        return settledTotal;
    }

    @Override
    protected int indexAt(float mx, float my, float originX, float originY, float contentW) {
        // 吸顶条先命中：它画在视口顶部，与它下面滚过的卡片位置重叠，判定必须与绘制同源（见 stickyIndex）
        int sticky = stickyIndex(originY);
        if (sticky >= 0 && hittable(sticky)) {
            float top = originY - CardLayout.TOP_INSET;
            float left = cardX(originX, contentW, sticky);
            if (my >= top && my <= top + ROW_HEIGHT
                    && mx >= left && mx <= left + cardWidth(contentW, sticky)) {
                return sticky;
            }
        }
        for (int i = 0; i < rows.size(); i++) {
            if (!hittable(i)) continue;
            float left = cardX(originX, contentW, i);
            if (mx < left || mx > left + cardWidth(contentW, i)) continue;
            float top = originY + slotTop[i] + rowShiftOf(i);
            if (my >= top && my <= top + ROW_HEIGHT) return i;
        }
        return -1;
    }

    /**
     * 该行现在能不能点：折叠块的内容行在展开 / 收起途中不参与命中。
     *
     * <p>半露的行（或还排在窗口下面的行）看着就不是一个完整的行，点它等于让玩家点一个正在动的东西；
     * 块头的展开度恒为 1，因此收起的分类照样点得开（与 {@code CollapsibleSection} 的命中口径一致：
     * 只能点到看得见的部分）。</p>
     */
    private boolean hittable(int index) {
        return slotReveal[index] >= 1f;
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
            if (originY + slotTop[i] < viewTop + 0.5f) sticky = i;
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

    /**
     * 一行的绘制入口：先按折叠块的展开度决定画不画、裁到哪里。
     *
     * <p>展开度 0（收起）：整行在窗口外，直接跳过；展开度 &lt; 1（正在展开 / 收起）：把这一行压到块窗口
     * 里、并按展开度淡化 —— 与折叠区 {@code CollapsibleSection} 的「内容只在动画高度内绘制」同一口径，
     * 因此半展开时既不会画出窗口外的一段，也不会有「看不见却能点到」的行（命中见 {@link #hittable}）。</p>
     */
    @Override
    protected void drawCard(Canvas canvas, int index, float x, float y, float w, float alpha, float hover,
                            ClickGuiThemeColors tc) {
        float reveal = slotReveal[index];
        if (reveal <= 0.01f) return;
        // 这一行能露出的高度 = 块窗口底边 − 行顶边（两者都在同一套「相对内容原点」的坐标里，
        // 而 y 是画布坐标，绝不可与 slot* 直接比大小：踩过这一脚，整页行全被判成窗口外、全不画）
        float visible = slotClipBottom[index] - slotTop[index];
        if (visible <= 0.01f) return;
        boolean clipped = visible < ROW_HEIGHT - 0.01f;
        // 半展开的行不参与悬停：指针坐标传一个必然落在行外的值，浮层不会在动画半路上闪出来
        float mouseX = reveal >= 1f ? frameMouseX() : MOUSE_OFF_ROW;
        float mouseY = reveal >= 1f ? frameMouseY() : MOUSE_OFF_ROW;
        if (clipped) {
            canvas.save();
            canvas.clipRect(Rect.makeXYWH(x, y, w, visible));
        }
        drawRowContent(canvas, index, x, y, w, alpha * reveal, hover, mouseX, mouseY, tc);
        if (clipped) canvas.restore();
    }

    /** 单个行的内容（按类型分派）；块窗口裁剪与展开度淡化由 {@link #drawCard} 统一负责。 */
    private void drawRowContent(Canvas canvas, int index, float x, float y, float w, float alpha, float hover,
                                float mouseX, float mouseY, ClickGuiThemeColors tc) {
        Row row = rows.get(index);
        if (row.module() != null) {
            // 整行一条：图标 + 名称 + 描述 + 星标 + 状态 + 箭头（星标是可点的收藏键）
            ModuleRow.drawRow(canvas, row.module(), x, y, w, mouseX, mouseY, alpha, hover,
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
        // 分类头：展开 / 收起。
        // 这里<b>不</b>重建清单：收起的块也把成员行留在清单里（只按展开度取高度），行数与内容都没变，
        // 重建反而会把「行位移动画」的起算点按动画中间态重记一遍 —— 展开途中再点一下，整块行先跳回去再滑下来。
        // 展开度目标由 ensureReveals / update 按 EXPANDED 同步，动画照常播。
        String id = row.category().id();
        if (!EXPANDED.remove(id)) EXPANDED.add(id);
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
        // 行顶边与绘制的同一份几何（静态槽位 + 位移动画）：星标命中框跟着画面一起动
        float rowY = originY + slotTop[index] + rowShiftOf(index);
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
        // 没拖动过 = 点了一下分类头：展开 / 收起。同样不重建清单（理由见 onCardActivated）
        if (!EXPANDED.remove(pressed)) EXPANDED.add(pressed);
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
        // 名称放不下也不截断（同 ModuleRow：整页不出现省略号），分类名本就短，实际永远放得下
        String title = FontRenderer.measureTextWidthBold(category.displayName(), HEADER_TITLE_SIZE) <= titleMax
                ? category.displayName() : "";
        return new HeaderLayout(title, titleX, upX, downX, countText, countRight);
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
