package com.yiyiaddon.ui.screen;

import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.ListRow;
import com.yiyiaddon.ui.component.SearchRow;
import com.yiyiaddon.ui.component.SplitPanels;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 通用选择器窗口：左栏候选、右栏已选，两栏各自按分组排列。
 *
 * <p><b>两栏各是「什么」：</b>左栏是候选（分组折叠 + 组头「全选」一键加整组，行右侧 ＋ 加入）；
 * 右栏是已选（<b>同样按分组排列</b>，组头按钮「清空」一键删整组，行右侧 − 移除）。
 * 用户 2026-09-16 先对早期版本截图说「为什么重叠了」——那时两栏各带一份同样的分组标题，
 * 试过把右栏改成平铺后又被指「添加进去的右边没分类啊 左右都要有分类」；真正的原因是两栏标题
 * 落在了同一高度，因此现在两栏都带分组，但右栏顶部先给一条总标题（「§b§l▌ 已选 N」）把整体下压，
 * 且右栏各组默认展开（左栏默认收起），两侧内容自然错开。</p>
 *
 * <p><b>候选支持两级分组</b>：候选项给出 {@link Entry#parentGroup()} 时按「父 → 子」两级折叠展示
 * （父级标题原样、子级缩进小一号），两级各自可折叠、各自带一键全选（同日：「能不能弄好分类啊
 * 按品质 工具 装备」——装备库本就是「大类 → 品质 → 类型」三级结构，一级分组会把三级压成一坨）。
 * 不给父级的条目行为完全不变。</p>
 *
 * <p><b>组头右侧是「全选 / 清空」</b>（同日：「旧框架不是有 一键勾选分组的功能？为什么我没有」）：
 * 点标题区折叠 / 展开该组，点右侧按钮把该组<b>当前可见（过滤后）</b>的条目一次性加入或移除。
 * 两种操作落在同一行的不同区域，互不吞并对方的点击。</p>
 *
 * <p><b>交互与文字照旧项目：</b>顶部搜索框（旧项目该窗无标签无提示）打开即输入过滤，匹配显示名与技术 ID、
 * 忽略大小写；分组标题由调用方给出完整原文（含 {@code §} 颜色码与 {@code ▌} 前缀），本类不做任何拼接或
 * 配色，避免与旧项目原文产生差异。组内为空时显示 {@code §8无}。<b>添加 / 移除后有回执</b>
 * （旧项目只有计数变化，用户 2026-09-15 拍板的增强；用户 2026-09-21 又拍板「全部做成弹窗的」
 * —— 回执从聊天框改成面板内顶部弹窗，唯一出口见 {@link SelectionReceipt}：
 * 单条是「已添加 / 已移除 &lt;名&gt;」，组头「全选 / 清空」只发一条汇总「已添加 N 项」）。</p>
 *
 * <p><b>分组标题可折叠、且默认收起</b>（用户 2026-09-16：「装备选择列表要按大类分组且可折叠」）：
 * 标题右侧常显该组条目数（收起状态也看得出有多少项），折叠状态记在会话级集合
 * {@link #expandedGroups} 里，过滤词变化重建也不丢。依据《开发习惯》第二十九章第 195~196 条
 * （候选多到需要滚动必须先分类；每个分类必须可折叠且默认收起）。空分组保持旧行为（静态标题 +「§8无」）。</p>
 *
 * <p><b>搜索时分组一律展开、空组不显示</b>（用户 2026-09-18：「搜索到了 但是还要自己点开分组才能看到」
 * ——要的是「输入『草』，下面直接列出带草字的关键字」）：过滤词非空时折叠记忆整体让位，命中的行直接铺在
 * 各自标题下面，没命中的组连标题都不出现；清空搜索框后折叠状态与空组口径原样恢复。</p>
 *
 * <p>本类不持有选中数据：读 {@code selectedKeys}、写走 {@code onAdd} / {@code onRemove}，
 * 数据源始终唯一在业务侧，避免出现第二份副本。</p>
 */
public final class SelectorScreen extends PanelScreen {

    /** 候选项。 */
    public interface Entry {

        /** 唯一键，选中状态以它为准。 */
        String key();

        /** 行标题，可含 {@code §} 颜色码。 */
        String title();

        /** 分组标题（完整原文，含颜色码与 {@code ▌} 前缀）；返回 {@code null} 或空表示不分组。 */
        String group();

        /**
         * 上一级分组标题（第二级分类，如装备按「大类 → 品质」两级分时的「工具」）。
         *
         * <p>默认 {@code null} = 只有 {@link #group()} 这一级（既有的目标实体、名单等页面口径不变）。
         * 给了父级之后，父级标题按第 1 级样式画、{@link #group()} 按第 2 级缩进小字画，两级各自可折叠、
         * 各自带一键全选。</p>
         */
        default String parentGroup() {
            return null;
        }

        /** 图标绘制；坐标与尺寸由宿主给出，返回是否真的画出。 */
        boolean drawIcon(Canvas canvas, float x, float y, float size);

        /**
         * 名称右侧的补充信息（技术 ID 等）；返回 {@code null} 表示不显示。
         *
         * <p>默认不显示。实体这类「显示名认不出来」的条目由调用方给出 {@code minecraft:giant}
         * 这样的真实 ID —— 用户 2026-09-16 对着「巨人」一行问「巨人是什么？？」，显示名是原版
         * 译名，只有配上看键才说得清是哪一条。</p>
         */
        default String detail() {
            return null;
        }

        /**
         * 图标对应的物品；给了就让宿主在开窗时预热图标缓存。
         *
         * <p><b>为什么要预热</b>（用户 2026-09-18：「点击这些分组的时候 有几率那个贴图会在闪一下
         * 所有的选择器都要防止」）：分组默认收起，展开那一刻才第一次绘制该组的图标，
         * 而 {@code ItemIconCache} 的图标要排队一两帧才渲染入库 —— 于是展开分组时行里先是空的图标位、
         * 图标随后补上，看着就是「闪一下」。宿主在 {@link #rebuild()} 时把这批图标一次性排队，
         * 展开时已经在缓存里，闪动消失。</p>
         *
         * <p>默认 {@code null} = 不预热（行为与之前完全一致）；没有图标、或图标不来自物品
         * （纯字形 / 自绘）的条目保持返回 {@code null} 即可。</p>
         */
        default ItemStack iconStack() {
            return null;
        }

        /**
         * 图标来自实体时的实体类型；给了就让宿主在开窗时预热该实体的图标（刷怪蛋或实体模型）。
         *
         * <p>与 {@link #iconStack()} 同一条口径，只是实体的图标走另一条缓存链路
         * （{@code ItemIconCache#prefetchEntity}）。两者都返回 {@code null} 表示不预热。</p>
         */
        default EntityType<?> iconEntity() {
            return null;
        }
    }

    private static final float SPLIT_MIN_HEIGHT = 200f;
    private static final float SPLIT_ROW_GAP = 4f;
    private static final float GROUP_TITLE_HEIGHT = 22f;
    private static final float GROUP_TITLE_SIZE = 11f;
    /** 二级分组标题的字号：比一级小一号，配合右缩进，眼睛能立刻分出层级 */
    private static final float GROUP_SUB_TITLE_SIZE = 10f;
    /** 每深一级的右缩进 */
    private static final float GROUP_INDENT = 12f;
    /** 二级箭头的字号（比一级再小一点，缩进后与标题同一视觉重量） */
    private static final float GROUP_SUB_ARROW_SIZE = 12f;
    /**
     * 折叠状态的键分隔符：两级分组的键要唯一（「工具」下的「木」与「护甲」下的「木」不是同一组），
     * 用一个不会出现在标题里的控制字符拼前缀，单级模式下仍直接用标题作键（既有页面口径不变）。
     */
    private static final char FOLD_KEY_SEPARATOR = '\u0001';
    private static final float EMPTY_HEIGHT = 20f;
    private static final float SEARCH_MAX_LENGTH = 64f;
    /** 分组标题的左内边距：与折叠前的 {@link TextLine} 一致（6），改标题写法不能把标题整列平移 */
    private static final float GROUP_TITLE_PAD_X = 6f;
    /** 标题基线比例：{@link TextLine} 用的是 {@code 行高/2 + 字号 × 0.36}，同一条标题折叠前后必须落同一高度 */
    private static final float GROUP_TITLE_BASELINE = 0.36f;
    /** 分组条目数的写法：{@code §8· §7N}（颜色码交给 {@link MinecraftText} 解析，浅色主题自动换深色版） */
    private static final String GROUP_COUNT_PREFIX = " §8· §7";
    /** 已选计数的写法；只在有选中项时追加 */
    private static final String GROUP_SELECTED_PREFIX = " §8· §a已选 ";
    /** 条目数与折叠箭头之间的留白 */
    private static final float GROUP_COUNT_GAP = 6f;
    /** 折叠指示箭头（Material Symbols：chevron_right），与 {@code FoldSection} 同一个码点 */
    private static final String GROUP_ARROW = "\uE5CC";
    private static final float GROUP_ARROW_SIZE = 13f;
    /** 展开时箭头右转 90°（朝下），收起时朝右——与 {@code FoldSection} 同一口径 */
    private static final float GROUP_ARROW_SPIN = 90f;
    private static final float GROUP_ARROW_CENTER_RATIO = 0.36f;
    /** 组头右侧「全选 / 清空」按钮与标题文字之间的最小留白：两侧各留一点，中间的空档仍算标题区（点它折叠） */
    private static final float GROUP_BUTTON_GAP = 8f;

    /** 组头右侧按钮文案：该组还没全选 → 全选；已全选 → 清空 */
    private static final String TEXT_SELECT_ALL = "全选";
    private static final String TEXT_CLEAR_ALL = "清空";

    /** 右栏（已选列）顶部的总标题：本级不折叠，只报总数；其下各组才是可折叠的分组标题 */
    private static final String SELECTED_TITLE = "§b§l▌ 已选";

    /**
     * 候选列最多铺这么多行。
     *
     * <p>方块 / 物品注册表各有一千多项，全铺出来既没人看得到底、也要每帧绘制上千个行控件。
     * 超过就截断，剩下一律靠搜索收窄（截断处会写明还有多少没列）。</p>
     */
    private static final int MAX_ROWS = 200;

    /**
     * 开窗时预热的图标条数上限（见 {@link #prefetchIcons}）。
     *
     * <p>取值 4096 = 实际意义上的「整表预热心」（物块 / 物品注册表最大也就一千多项）。</p>
     *
     * <p><b>为什么从 200 放开到整表</b>（用户 2026-09-22：「点击目标选择器分组的时候 选东西的时候
     * 会闪…就目标选择器会这样」）：只热前两百条时，<b>排在第 200 条之后的分组</b>（自定义物品 / 自定义
     * 方块，以及搜索命中的靠后条目）展开那一刻才第一次请求图标，而图标要排队一两帧才入库 ——
     * 行里先是空图标位、随后补上，就是那个「一闪而过」。分组型选择器（附魔、村民交易、装备…）整表
     * 都在两百条内，所以只有上千项的目标选择器看得见。</p>
     *
     * <p>早先不敢全热的顾虑是「会把图标缓存挤到清理阈值、把刚缓存的图标丢掉」——那条已经被改掉：
     * 现在撞上上限只淘汰最旧的一批，不再整张清空（见 {@code ItemIconCache#evictOldest}）。
     * 代价是开窗后约 6 秒的后台截取（12 张/帧），期间界面照常可用。</p>
     */
    private static final int PREFETCH_LIMIT = 4096;

    /** Material Symbols：add / remove。 */
    private static final String GLYPH_ADD = "\uE145";
    private static final String GLYPH_REMOVE = "\uE15B";

    /** 空态；与旧项目一致。 */
    private static final String EMPTY_TEXT = "  §8无";

    /** 单选模式下的一句用法说明（该模式没有「已选名单」，因此直接写在搜索框下方） */
    private static final String PICK_HINT = "  §8点任意一行即可选中";

    /**
     * 「加入准入」判定：{@code null} = 放行，非空 = 拒绝并把这句话弹成顶部提示。
     *
     * <p>给「只能选一个」这类名单用（食物白名单）：点第二条候选时拦下并说明原因，而不是默默
     * 覆盖或默默追加。见 {@link #addGuard(AddGuard)}。</p>
     */
    public interface AddGuard {

        /** @return {@code null} 表示允许加入；否则返回要弹给玩家的原因（可含 {@code §} 颜色码） */
        String rejectReason(String key);
    }

    private final String windowTitle;
    private final List<Entry> entries;
    private final Supplier<List<String>> selectedKeys;
    private final Consumer<String> onAdd;
    private final Consumer<String> onRemove;
    /** 单选模式的选中回调；{@code null} = 常规多选（左加右减） */
    private final Consumer<String> onPick;
    /** 加入准入判定；{@code null} = 一律放行（见 {@link #addGuard}） */
    private AddGuard addGuard;

    private final SplitPanels split = new SplitPanels(SPLIT_MIN_HEIGHT, SPLIT_ROW_GAP);
    /**
     * 搜索行与单选提示：每次重建都要连同列表一起重新铺进 {@link #content()}，
     * 因此存引用而不是在 {@code build()} 里就地 add——{@link CompactStack#clear()} 会把它们一起清掉。
     */
    private SearchRow searchRow;
    private TextLine pickHint;
    /**
     * <b>已展开</b>的分组标题（会话级：本窗口实例存活期间有效，过滤词变化触发 {@link #rebuild()} 也不丢）。
     *
     * <p><b>为什么记「展开」而不是记「收起」：</b>第 196 条要求默认收起——集合初始为空即全部收起；
     * {@code ConsoleWidgets.FoldSection} 那套是记「收起」（不记即展开），口径正好相反，不能照抄。
     * 键取分组标题原文（调用方给出的完整原文在候选表里唯一）。</p>
     */
    private final Set<String> expandedGroups = new HashSet<>();
    /**
     * <b>已收起</b>的右栏（已选列）分组标题。
     *
     * <p>右栏的默认值与左栏相反：那里装的是用户刚放进去的东西，默认收起来会让人以为「加丢了」，
     * 因此默认展开、记的是「谁被手动收起」。两栏的折叠状态分属两个集合，互不影响。</p>
     */
    private final Set<String> collapsedGroups = new HashSet<>();
    private String filter = "";

    /** 栏位：决定候选来源、行按钮语义、组头按钮文案与折叠默认值。 */
    private enum Column {
        /** 左栏候选：只列未选中的，组头按钮「全选」（一键加入整组），默认收起 */
        CANDIDATE,
        /** 右栏已选：只列已选中的，组头按钮「清空」（一键移除整组），默认展开 */
        SELECTED,
        /** 单栏点选：全部条目，点行即选中并关窗（无右栏） */
        PICK
    }

    public SelectorScreen(String windowTitle, Screen parent, List<Entry> entries,
                          Supplier<List<String>> selectedKeys,
                          Consumer<String> onAdd, Consumer<String> onRemove) {
        this(windowTitle, parent, entries, selectedKeys, onAdd, onRemove, null);
    }

    private SelectorScreen(String windowTitle, Screen parent, List<Entry> entries,
                           Supplier<List<String>> selectedKeys,
                           Consumer<String> onAdd, Consumer<String> onRemove,
                           Consumer<String> onPick) {
        super(windowTitle, parent);
        // 聊天回执用窗口标题作前缀：本窗口是通用选择器，没有所属模块名可借
        this.windowTitle = windowTitle;
        this.entries = entries == null ? List.of() : List.copyOf(entries);
        this.selectedKeys = selectedKeys;
        this.onAdd = onAdd;
        this.onRemove = onRemove;
        this.onPick = onPick;
        build();
    }

    /**
     * 单选模式：点一行即选中并关窗，没有候选 / 已选两栏，行内也不出现加减按钮。
     *
     * <p>给「挑一个方块当映射键 / 当替代方块」这类场景用：那里要的是「选哪一个」而不是
     * 「维护一张名单」，因此点行即定。</p>
     */
    public static SelectorScreen pick(String windowTitle, Screen parent, List<Entry> entries,
                                      Consumer<String> onPick) {
        return new SelectorScreen(windowTitle, parent, entries, () -> List.of(),
            key -> { }, key -> { }, onPick);
    }

    /**
     * 设置「加入准入」判定：{@code null} 放行，非空则拒绝并把它弹成顶部提示。
     *
     * <p><b>为什么要有这一层</b>（用户 2026-09-19：「为什么能选两个食物？只能选一个目标选择器，
     * 如果选两个弹个动态小框提示玩家」）：名单本身的约束（比如「只能选一个」）只有调用方知道，
     * 但「点下去之后界面怎么反应」是窗口的事 —— 拒绝时既不能悄悄追加、也不该弹聊天回执，
     * 而要在窗口里当场提示。因此把判定交给调用方、把反馈留在窗口。</p>
     *
     * <p>行内 ＋、点整行加入、以及组头「全选」都走这条判定；被拒的条目一律不产生聊天回执。</p>
     */
    public SelectorScreen addGuard(AddGuard guard) {
        this.addGuard = guard;
        return this;
    }

    private void build() {
        // 搜索框：旧项目该窗为无标签输入框（ItemTargetSelectScreen:34），不加标签与提示
        searchRow = new SearchRow(new SettingTextBox(() -> filter, this::applyFilter, (int) SEARCH_MAX_LENGTH));
        pickHint = onPick == null ? null : new TextLine(PICK_HINT).height(EMPTY_HEIGHT);
        rebuild();
    }

    private void applyFilter(String value) {
        filter = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
        rebuild();
    }

    /** 按当前选中集合与过滤词重建两栏（整页内容一起重铺，搜索框始终在最上面）。 */
    private void rebuild() {
        CompactStack content = content();
        content.clear();
        content.add(searchRow);
        if (pickHint != null) content.add(pickHint);

        prefetchIcons();

        List<String> keys = selectedKeys.get();
        Set<String> selected = new HashSet<>(keys == null ? List.of() : keys);

        if (onPick != null) {
            // 单选：只有一条列表（点行即选中），铺在页面内容栈上
            buildColumn(content, Column.PICK, selected);
            return;
        }
        split.reset();
        content.add(split);
        buildColumn(split.left(), Column.CANDIDATE, selected);
        buildColumn(split.right(), Column.SELECTED, selected);
    }

    /**
     * 预热候选 / 已选的图标（见 {@link Entry#iconStack()}）。
     *
     * <p>为什么预热：见 {@link #PREFETCH_LIMIT} 与 {@code Entry#iconStack()} 的说明。
     * 每次 {@link #rebuild()} 都调一次 —— {@code prefetch} 幂等，已缓存 / 已在途的键直接跳过，
     * 这里只剩一次遍历的开销。</p>
     */
    private void prefetchIcons() {
        ItemIconCache cache = ItemIconCache.getInstance();
        int limit = Math.min(entries.size(), PREFETCH_LIMIT);
        for (int i = 0; i < limit; i++) {
            Entry entry = entries.get(i);
            ItemStack stack = entry.iconStack();
            if (stack != null) {
                cache.prefetch(stack);
                continue;
            }
            EntityType<?> type = entry.iconEntity();
            if (type != null) cache.prefetchEntity(type);
        }
    }

    /**
     * 铺一栏：两级分组（父 → 子）→ 条目。
     *
     * <p>父级标题为空的条目实际只有一级，共用同一段渲染（子级按第 1 级样式画），
     * 因此既有的单级页面像素与命中口径都不变。组顺序按调用方给出的条目顺序固定：
     * 空分组同样保留标题（旧项目口径，见类注释）。</p>
     *
     * <p>两栏走同一段代码，只在 {@link Column} 上分叉：候选列跳过已选、已选列只留已选，
     * 行按钮与组头按钮的语义、折叠默认值随之确定。用户 2026-09-16：「左右都要有分类
     * 一键添加分组 一键删除分组」——左栏组头「全选」一键加整组、右栏组头「清空」一键删整组。</p>
     */
    private void buildColumn(CompactStack target, Column column, Set<String> selected) {
        // 右栏空的时候只给一条总标题 + 「无」：一个都没选时铺一串空分组标题纯属噪音
        if (column == Column.SELECTED && selected.isEmpty()) {
            target.add(selectedTitle(0));
            target.add(new TextLine(EMPTY_TEXT).height(EMPTY_HEIGHT));
            return;
        }
        if (column == Column.SELECTED) target.add(selectedTitle(selected.size()));

        Map<String, Map<String, List<Entry>>> tree = new LinkedHashMap<>();
        for (Entry entry : entries) {
            tree.computeIfAbsent(parentKey(entry), key -> new LinkedHashMap<>())
                    .computeIfAbsent(groupKey(entry), key -> new ArrayList<>());
        }
        for (Entry entry : entries) {
            if (!matches(entry)) continue;
            boolean chosen = selected.contains(entry.key());
            if (column == Column.CANDIDATE && chosen) continue;
            if (column == Column.SELECTED && !chosen) continue;
            tree.get(parentKey(entry)).get(groupKey(entry)).add(entry);
        }

        int shown = 0;
        boolean truncated = false;
        for (Map.Entry<String, Map<String, List<Entry>>> parent : tree.entrySet()) {
            Map<String, List<Entry>> children = parent.getValue();
            boolean parentTitled = !parent.getKey().isEmpty();
            List<Entry> all = flatten(children);
            if (parentTitled) {
                if (all.isEmpty() && (column == Column.SELECTED || !filter.isEmpty())) continue;
                target.add(all.isEmpty()
                        ? groupTitle(parent.getKey(), 1)
                        : header(column, parent.getKey(), parent.getKey(), all, selected, 1));
                if (all.isEmpty()) {
                    // 单级模式下空组沿用旧项目口径：标题 + 「无」
                    target.add(new TextLine(EMPTY_TEXT).height(EMPTY_HEIGHT));
                    continue;
                }
                // 收起的一级组只留标题：条目数已写在标题右侧，展开后行数与之一致
                if (!isGroupExpanded(column, parent.getKey())) continue;
            }
            for (Map.Entry<String, List<Entry>> child : children.entrySet()) {
                List<Entry> items = child.getValue();
                boolean childTitled = !child.getKey().isEmpty();
                String foldKey = foldKey(parent.getKey(), child.getKey());
                if (items.isEmpty()) {
                    // 空组只在「候选 / 点选」且单级结构时给一行「无」；二级结构下空子组直接跳过
                    // （大类标题上已有总数，再铺一串「无」只是噪音）。
                    // 搜索时同样跳过：命中结果里混着一串「无」的分组标题，只会把真正匹配的那组推下去
                    if (!filter.isEmpty()) continue;
                    if (column != Column.SELECTED && !parentTitled && childTitled) {
                        target.add(groupTitle(child.getKey(), 1));
                        target.add(new TextLine(EMPTY_TEXT).height(EMPTY_HEIGHT));
                    }
                    continue;
                }
                if (childTitled) {
                    target.add(header(column, child.getKey(), foldKey, items, selected, parentTitled ? 2 : 1));
                    if (!isGroupExpanded(column, foldKey)) continue;
                }
                for (Entry entry : items) {
                    if (shown >= MAX_ROWS) {
                        truncated = true;
                        break;
                    }
                    target.add(column == Column.PICK ? pickRow(entry) : toggleRow(entry, column == Column.SELECTED));
                    shown++;
                }
                if (truncated) break;
            }
            if (truncated) break;
        }
        if (truncated) {
            target.add(new TextLine("  §8只列前 " + MAX_ROWS + " 个，再输入几个字缩小范围").height(EMPTY_HEIGHT));
        }
    }

    /** 右栏（已选列）的总标题：数量写在这里，下面各分组只管自己的那一档。 */
    private static TextLine selectedTitle(int count) {
        return new TextLine(SELECTED_TITLE + GROUP_COUNT_PREFIX + count)
                .height(GROUP_TITLE_HEIGHT)
                .size(GROUP_TITLE_SIZE)
                .bold(true);
    }

    /** 组头：候选 / 点选列用「全选」，已选列用「清空」；折叠默认值随栏位。 */
    private GroupHeader header(Column column, String title, String foldKey, List<Entry> items,
                               Set<String> selected, int level) {
        return new GroupHeader(title, foldKey, items, selected, level, column == Column.SELECTED);
    }

    /** 把两级结构里的全部条目摊平成一段（一级标题上的计数与全选要看整个子树）。 */
    private static List<Entry> flatten(Map<String, List<Entry>> children) {
        List<Entry> all = new ArrayList<>();
        for (List<Entry> items : children.values()) all.addAll(items);
        return all;
    }

    private static String parentKey(Entry entry) {
        return entry.parentGroup() == null ? "" : entry.parentGroup();
    }

    private static String groupKey(Entry entry) {
        return entry.group() == null ? "" : entry.group();
    }

    /** 折叠状态的键：单级模式直接用子级标题（与既有页面一致），两级模式用「父 + 分隔符 + 子」。 */
    private static String foldKey(String parentTitle, String childTitle) {
        return parentTitle.isEmpty() ? childTitle : parentTitle + FOLD_KEY_SEPARATOR + childTitle;
    }

    /** 静态分组标题（空分组用）：与折叠前的写法逐字一致；二级标题缩进并小一号。 */
    private static TextLine groupTitle(String title, int level) {
        return new TextLine(title)
                .height(GROUP_TITLE_HEIGHT)
                .indent(level > 1 ? GROUP_INDENT : 0f)
                .size(level > 1 ? GROUP_SUB_TITLE_SIZE : GROUP_TITLE_SIZE)
                .bold(true);
    }

    /**
     * 该分组当前是否展开。
     *
     * <p><b>搜索时一律视为展开</b>（用户 2026-09-18：「搜索到了 但是还要自己点开分组才能看到」
     * ——要的是「输入『草』下面就列出带草字的关键字」）：过滤词非空时折叠记忆让位，命中的行直接铺在
     * 各自标题下面；<b>集合本身不动</b>，清空搜索框后原来的折叠状态原样恢复。</p>
     */
    private boolean isGroupExpanded(Column column, String groupKey) {
        if (!filter.isEmpty()) return true;
        return column == Column.SELECTED
                ? !collapsedGroups.contains(groupKey)
                : expandedGroups.contains(groupKey);
    }

    /**
     * 可折叠的分组标题（候选列表的分组头）。
     *
     * <p><b>外观不改</b>：第 196 条要求「分类标题只作章节标记：不铺底色、不做成按钮样式、不与普通条目
     * 撞脸」，因此这里只有标题原文 + 条目数 + 一个 Material Symbols 小箭头，没有底色与悬停高亮；
     * 与折叠前的唯一差别就是标题后面多了「§8· 12」与箭头。</p>
     *
     * <p><b>两个热区：</b>标题区（含标题右侧的空档）点一下折叠 / 展开——与 {@code FoldSection} 同一命中
     * 口径；最右侧的「全选 / 清空」是<b>普通按钮</b>，点它把该组当前可见条目一次性加入或移除。
     * 按钮先命中，返回 true 就不再看折叠，因此两种操作不会互相吞并。</p>
     */
    private final class GroupHeader implements CompactElement {

        private final String title;
        /** 折叠状态的键：一级组就是标题，二级组是「父 + 分隔符 + 子」（同名品质分属不同大类） */
        private final String foldKey;
        /** 该组当前可见（过滤后）的条目；「全选 / 清空」的作用范围就是它，与标题上的数字一致 */
        private final List<Entry> items;
        /** 1 = 大类标题；2 = 品质标题（缩进 + 小一号） */
        private final int level;
        /**
         * true = 右栏（已选）的组头。
         *
         * <p>两处差别都源出这一点：按钮恒为「清空」（该组里的条目必然都已选中）、默认展开
         * （收起来会让人以为刚加的东西丢了，因此记的是「谁被手动收起」）。</p>
         */
        private final boolean selectedColumn;
        private final int selectedCount;
        private final Button toggle;

        private GroupHeader(String title, String foldKey, List<Entry> items, Set<String> selected, int level,
                            boolean selectedColumn) {
            this.title = title;
            this.foldKey = foldKey;
            this.items = List.copyOf(items);
            this.level = level;
            this.selectedColumn = selectedColumn;
            int count = 0;
            for (Entry entry : items) {
                if (selected.contains(entry.key())) count++;
            }
            this.selectedCount = count;
            // 文案随选中状态实时变化（读的是业务侧的真值，不是构造时的快照）
            this.toggle = new Button(() -> selectedColumn || allSelected() ? TEXT_CLEAR_ALL : TEXT_SELECT_ALL,
                    this::toggleAll)
                    .small()
                    .ghost();
        }

        /** 该组是否展开：右栏默认展开、左栏默认收起，两个集合各记一份；搜索时一律展开（见 {@link #isGroupExpanded}）。 */
        private boolean expanded() {
            if (!filter.isEmpty()) return true;
            return selectedColumn ? !collapsedGroups.contains(foldKey) : expandedGroups.contains(foldKey);
        }

        private void toggleFold() {
            Set<String> state = selectedColumn ? collapsedGroups : expandedGroups;
            if (!state.remove(foldKey)) state.add(foldKey);
            rebuild();
        }

        private boolean allSelected() {
            if (items.isEmpty()) return false;
            List<String> keys = selectedKeys.get();
            if (keys == null) return false;
            for (Entry entry : items) {
                if (!keys.contains(entry.key())) return false;
            }
            return true;
        }

        /**
         * 一键勾选 / 取消勾选整组。
         *
         * <p>范围是<b>该组当前可见的条目</b>（过滤后的那一批），与标题上显示的条目数同一批：
         * 搜索框里打了字之后点「全选」，选中的就是眼前看到的这些，不会把看不见的也一起选上。</p>
         *
         * <p>右栏的组头恒为「清空」（那里面的条目必然都已选中），因此判据与按钮文案同源，
         * 避免出现「按钮写着清空、点下去反而在加」的错位。</p>
         */
        private void toggleAll() {
            boolean clear = selectedColumn || allSelected();
            String reject = null;
            int changed = 0;
            for (Entry entry : items) {
                if (clear) {
                    onRemove.accept(entry.key());
                    changed++;
                    continue;
                }
                // 整组加入同样要过准入判定：被拒的跳过并留一句提示（如「食物白名单只能选一个」）
                String reason = addGuard == null ? null : addGuard.rejectReason(entry.key());
                if (reason != null) {
                    reject = reason;
                    continue;
                }
                onAdd.accept(entry.key());
                changed++;
            }
            if (reject != null) TooltipLayer.notify(reject);
            // 整组只发一条汇总（逐条发会把弹窗刷成走马灯）；0 项时由回执自己说明原因
            if (clear) {
                SelectionReceipt.removedMany(changed);
            } else {
                SelectionReceipt.addedMany(changed);
            }
            rebuild();
        }

        @Override
        public float height() {
            return GROUP_TITLE_HEIGHT;
        }

        @Override
        public void update(float dt) {
            toggle.update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            ClickGuiThemeColors tc = ClickGuiThemeColors.current();
            // 二级标题缩进一层、小一号：与一级标题同一段代码，只按 level 换两个数
            float indent = level > 1 ? GROUP_INDENT : 0f;
            float titleSize = level > 1 ? GROUP_SUB_TITLE_SIZE : GROUP_TITLE_SIZE;
            float arrowSize = level > 1 ? GROUP_SUB_ARROW_SIZE : GROUP_ARROW_SIZE;

            float toggleWidth = toggle.getWidth();
            float toggleX = x + width - GROUP_TITLE_PAD_X - toggleWidth;
            float toggleY = y + (GROUP_TITLE_HEIGHT - toggle.getHeight()) / 2f;
            toggle.hover(mouseX, mouseY, toggleX, toggleY, toggleWidth);
            toggle.drawAt(canvas, toggleX, toggleY, toggleWidth, alpha);

            float centerY = y + GROUP_TITLE_HEIGHT / 2f;
            float baseline = centerY + titleSize * GROUP_TITLE_BASELINE;
            float cursor = x + GROUP_TITLE_PAD_X + indent;
            cursor += MinecraftText.draw(canvas, title, cursor, baseline, titleSize,
                    tc.primaryText, alpha, true);
            // 条目数：收起 / 展开都显示（用户要求「收起状态也要能看出有多少项」）
            cursor += MinecraftText.draw(canvas, GROUP_COUNT_PREFIX + items.size(), cursor, baseline,
                    titleSize, tc.primaryText, alpha, false);
            if (selectedCount > 0) {
                MinecraftText.draw(canvas, GROUP_SELECTED_PREFIX + selectedCount, cursor, baseline,
                        titleSize, tc.primaryText, alpha, false);
            }
            drawArrow(canvas, cursor + GROUP_COUNT_GAP + arrowSize / 2f, centerY, arrowSize, tc, alpha);
        }

        /** 指示箭头：展开时朝下、收起时朝右（与 {@code FoldSection} 同一码点与旋转口径） */
        private void drawArrow(Canvas canvas, float centerX, float centerY, float arrowSize,
                               ClickGuiThemeColors tc, float alpha) {
            float arrowWidth = FontRenderer.measureTextWidth(GROUP_ARROW, arrowSize, FontRenderer.MATERIAL_SYMBOLS);
            canvas.save();
            canvas.translate(centerX, centerY);
            canvas.rotate(expanded() ? GROUP_ARROW_SPIN : 0f);
            canvas.translate(-centerX, -centerY);
            FontRenderer.drawText(canvas, GROUP_ARROW, centerX - arrowWidth / 2f,
                    centerY + arrowSize * GROUP_ARROW_CENTER_RATIO, arrowSize,
                    GlassPanel.withAlpha(tc.mutedText, alpha), FontRenderer.MATERIAL_SYMBOLS);
            canvas.restore();
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            if (button != 0) return false;
            if (mx < x || mx > x + width || my < y || my > y + GROUP_TITLE_HEIGHT) return false;
            float toggleWidth = toggle.getWidth();
            float toggleX = x + width - GROUP_TITLE_PAD_X - toggleWidth;
            float toggleY = y + (GROUP_TITLE_HEIGHT - toggle.getHeight()) / 2f;
            if (toggle.onClickAt(mx, my, toggleX, toggleY, toggleWidth, button)) return true;
            if (mx > toggleX - GROUP_BUTTON_GAP) return false;
            toggleFold();
            return true;
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }

    /** 单选模式的行：点一下即选中并关窗，不出现加减按钮。 */
    private ListRow pickRow(Entry entry) {
        return new ListRow(entry.title())
                .icon(entry::drawIcon)
                .detail(entry::detail)
                .onActivate(() -> {
                    onPick.accept(entry.key());
                    requestClose();
                });
    }

    /** 多选模式的行：{@code selected} 为真 = 右栏（已选）那一行，点它移除；否则左栏候选，点它加入。 */
    private ListRow toggleRow(Entry entry, boolean selected) {
        IconButton action = new IconButton(selected ? GLYPH_REMOVE : GLYPH_ADD, () -> toggle(entry, selected));
        if (selected) action.danger();
        return new ListRow(entry.title())
                .icon(entry::drawIcon)
                .detail(entry::detail)
                .selected(selected)
                .action(action)
                .onActivate(() -> toggle(entry, selected));
    }

    /** 加入 / 移除一条，并发出回执；被准入判定拒绝时不改动名单，只弹顶部提示。 */
    private void toggle(Entry entry, boolean selected) {
        if (selected) {
            onRemove.accept(entry.key());
            SelectionReceipt.removed(entry.title());
        } else {
            String reject = addGuard == null ? null : addGuard.rejectReason(entry.key());
            if (reject != null) {
                TooltipLayer.notify(reject);
                rebuild();
                return;
            }
            onAdd.accept(entry.key());
            SelectionReceipt.added(entry.title());
        }
        rebuild();
    }

    private boolean matches(Entry entry) {
        if (filter.isEmpty()) return true;
        String title = stripFormatting(entry.title()).toLowerCase(Locale.ROOT);
        return title.contains(filter) || entry.key().toLowerCase(Locale.ROOT).contains(filter);
    }

    /** 去掉 {@code §x} 颜色码后再参与过滤，否则输入的字会被颜色码打断。 */
    private static String stripFormatting(String text) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder result = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '§' && i + 1 < text.length()) {
                i++;
                continue;
            }
            result.append(c);
        }
        return result.toString();
    }
}
