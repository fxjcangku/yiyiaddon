package com.yiyiaddon.feature.stardew.ui;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.RuleEvidence;
import com.yiyiaddon.feature.stardew.profile.StardewCropNameStore;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.recognition.CropPotGroups;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.selector.StardewPreview;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.SearchRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.PreeditEvent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 星露谷单类物品多选界面（七类共用；左侧「可添加」、右侧「已选择」）。
 *
 * <p><b>逐字搬运自旧项目</b> {@code stardew/selector/StardewTargetSelectScreen.java}：
 * 窗口标题 {@code "选择" + 类别中文名}、搜索框（无标签、输入即过滤）、两栏标题
 * {@code §a§l▌ 可添加<类别名>} 与 {@code §b§l▌ 已选择}、行状态词 {@code §b已选择} /
 * {@code §a可添加}、空态 {@code §8无（资源包未发现或已全部选中）}、水壶页顶部提示行与其
 * 三段 tooltip、作物名行 / 作物种子行 / 工具行三种 tooltip 模板、缺失图标 {@code §8[?]}
 * 与 tooltip {@code §c缺失图标\n§7<诊断>}，全部照旧。</p>
 *
 * <p><b>为什么没有复用 {@code ui/screen/SelectorScreen}</b>：该通用选择器承载不了旧页的必要内容——
 * 每行两个图标（成熟产物 + 种子）、每行 tooltip 原文、水壶页顶部提示行、右栏标题与旧空态文案
 * 都无处安放（其空态固定为 {@code §8无}，右栏也不画标题）。因此这里按本项目面板骨架自建
 * {@link PanelScreen} 子类，但控件形态、行外观、hover / 按压反馈全部沿用本项目既有资产。</p>
 *
 * <p><b>tooltip 说明</b>：本项目通用控件没有 tooltip 原语，这里在面板内部自绘一个轻量悬浮层
 * （两栏画完后再画，因此不会被其它行盖住），文案仍经 {@link MinecraftText} 解析 {@code §} 颜色码，
 * 多行用 {@code \n} 拆行——与旧项目逐行 tooltip 同一份原文。</p>
 */
public final class StardewTargetSelectScreen extends PanelScreen {

    /** 空态：旧项目原文（左右两栏共用一句） */
    private static final String EMPTY_TEXT = "§8无（资源包未发现或已全部选中）";
    /** 左栏标题前缀 + 类别中文名 */
    private static final String LEFT_TITLE_PREFIX = "§a§l▌ 可添加";
    /** 右栏标题 */
    private static final String RIGHT_TITLE = "§b§l▌ 已选择";

    /** 水壶类别专属提示：选中即自动联动补水（只出现在水壶选择页） */
    private static final String WATER_CAN_HINT =
        "§8▸ §7自动联动补水：水壶用尽自动回补水点、同一 tick 连发多包一次补满，无需额外设置";
    /** 水壶提示的三段 tooltip，逐字照旧 {@code WATER_CAN_HINT_DETAIL} */
    private static final String WATER_CAN_HINT_DETAIL =
        "§7容量来源（按可信度）：Tooltip / LORE 的「当前/上限」数字 → custom_data 容量键 → 耐久镜像"
            + " → 水位条（资源包字形优先，换资源包也能靠「端帽 + 重复格」结构识别，再用真实水量反证）。\n"
            + "§7读得到 → 按「还差多少 ÷ 单包增量」一轮发完；读不到 → 先按每包 +1 试探并记住单包增量。\n"
            + "§7单 tick 最多 16 包、整壶最多 32 包；多发的包服务端会忽略，不会溢出。";

    /** 搜索框最大长度（旧项目为无标签输入框） */
    private static final int SEARCH_MAX_LENGTH = 64;
    /** 行内操作按钮图标（Material Symbols：add / remove） */
    private static final String GLYPH_ADD = "\uE145";
    private static final String GLYPH_REMOVE = "\uE15B";

    private static final float ROW_HEIGHT = 36f;
    private static final float ROW_GAP = 6f;
    private static final float COLUMN_GAP = 12f;
    private static final float COLUMNS_MIN_HEIGHT = 48f;
    private static final float PAD_X = 12f;
    private static final float ICON = 24f;
    private static final float ICON_TEXT_SIZE = 10f;
    private static final float GAP = 10f;
    private static final float NAME_SIZE = 11f;
    private static final float STATUS_SIZE = 10f;
    private static final float ACTION = 24f;
    private static final float TITLE_HEIGHT = 22f;
    private static final float TITLE_SIZE = 11f;
    private static final float HINT_LINE_HEIGHT = 20f;
    private static final float HINT_SIZE = 10f;

    /** 用户状态列在「可添加 / 已选择」实测宽度之外额外占用的像素 */
    private static final float STATUS_PAD = 10f;
    /** 名称列自然宽度之外额外占用的像素（旧项目 recomputeColumns 的 {@code +12}） */
    private static final float NAME_PAD = 12f;
    /** 名称列压缩下限：再挤也不会低于这个宽度，否则文字全成省略号（旧项目 COL_NAME_MIN） */
    private static final float NAME_MIN = 56f;
    /** 种子名称列压缩下限（旧项目 COL_SEED_NAME_MIN） */
    private static final float SEED_NAME_MIN = 48f;

    private static final float TIP_SIZE = 10f;
    private static final float TIP_LINE = 12f;
    private static final float TIP_PAD = 6f;
    private static final float TIP_RADIUS = 6f;
    private static final float TIP_OFFSET_X = 14f;
    private static final float TIP_OFFSET_Y = 16f;

    /**
     * 名称 / tooltip 基准色：旧项目作物名是 {@code §f}，那是深色底。
     *
     * <p>浅色主题下面板与浮层底色都是白的，白字压白底等于看不见，因此浅色主题改用主文字色；
     * 深色主题一个像素不改；文案自带 {@code §} 颜色码时照旧覆盖基准色。</p>
     */
    private static int nameColor() {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        return tc != null && !tc.dark ? tc.primaryText : 0xFFFFFF;
    }
    /** 种子名基准色：旧项目 {@code §7} */
    private static final int SEED_NAME_COLOR = 0xAAAAAA;

    private final StardewFarmModule module;
    private final StardewSelectorCategory category;
    /** 单选模式（区域换作物）的写回；null = 多选模式（模块的目标选择集合） */
    private final Consumer<CropDefinition> pickHandler;
    /** 单选模式下「右栏已选择」显示什么：这块地当前绑的作物键 */
    private final String pickCurrentKey;
    private final Columns columns = new Columns();

    private String filter = "";

    /** 本类别全部候选里最长的真实名称宽度（名称列）与最长种子名宽度（种子名称列） */
    private float nameNatural;
    private float seedNatural;

    /** 本帧待绘制的 tooltip 文本与锚点；两栏画完后由 {@link Columns} 统一绘制并清空 */
    private String tipText;
    private float tipX;
    private float tipY;

    public StardewTargetSelectScreen(Screen parent, StardewFarmModule module, StardewSelectorCategory category) {
        super("选择" + category.title(), parent);
        this.module = module;
        this.category = category;
        this.pickHandler = null;
        this.pickCurrentKey = null;
        build();
    }

    /**
     * 单选模式：点一下某个作物就把键交回调用方（区域列表「换作物」用）。
     *
     * <p>与多选模式<strong>共用同一套界面</strong>（搜索框、两栏、行外观、图标与 tooltip 全部照旧），
     * 只差三处：右栏「已选择」显示的是这块地当前绑的作物、点击不经目标选择集合、写完由调用方决定
     * 下一屏（这里不自行关窗，避免和调用方的重建互相覆盖）。</p>
     *
     * @param currentKey 这块地当前绑的作物键；用来把当前项放进右栏做对照，可为 null
     * @param onPick     选中一个作物时的写回
     */
    public StardewTargetSelectScreen(Screen parent, StardewFarmModule module, StardewSelectorCategory category,
                                     String currentKey, Consumer<CropDefinition> onPick) {
        super("选择" + category.title(), parent);
        this.module = module;
        this.category = category;
        this.pickHandler = Objects.requireNonNull(onPick, "onPick");
        this.pickCurrentKey = currentKey;
        build();
    }

    // ── 构建 ──

    private void build() {
        // 打开界面这一刻先学一遍名字：部分服务器的资源包只翻译了一部分作物（其余 38 种在语言文件里
        // 根本不存在），中文名只能从服务器下发的物品名 / 阶段名学。学完立刻重建索引，下面构建出来的行
        // 才用得上中文名——否则界面里会一直显示 chinese_cabbage 这种技术键。
        StardewCropNameStore.observeNow();
        // 搜索框：旧项目该窗为无标签输入框，输入即过滤；整栏宽，与下面两栏的整体宽度对齐
        content().add(new SearchRow(
            new SettingTextBox(() -> filter, this::applyFilter, SEARCH_MAX_LENGTH)));
        if (category == StardewSelectorCategory.WATERING_CAN) {
            content().add(new HintLine(WATER_CAN_HINT, WATER_CAN_HINT_DETAIL));
        }
        content().add(columns);
        rebuild();
    }

    private void applyFilter(String value) {
        filter = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
        rebuild();
    }

    // ── 键盘输入转发 ──

    /**
     * 搜索框需要真正的键盘输入：{@link PanelScreen} 骨架本身不转发按键（只有主界面与模块屏幕转发），
     * 这里按 {@code ModuleScreen} 的同一口径把按键 / 字符 / 输入法预编辑交给当前聚焦的输入框，
     * 其余按键照旧交给父类（ESC 关闭窗口）。没有这一段，旧项目「输入即过滤」的交互会失效。
     */
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

    /** 按当前选择集合与过滤词重建两栏（旧 {@code rebuild()} 同口径） */
    private void rebuild() {
        recomputeColumns();
        columns.reset();
        CompactStack left = columns.left();
        CompactStack right = columns.right();
        // 盆型按维度分档保存，标题必须写出当前维度，否则「我明明选了普通盆」和
        // 「现在显示的是下界那份」两种状态在界面上分不出来
        String dimensionSuffix = category == StardewSelectorCategory.POT
            ? "§8 · " + WorldContextFormatter.dimensionDisplayName() : "";
        left.add(new TextLine(LEFT_TITLE_PREFIX + category.title() + dimensionSuffix)
            .height(TITLE_HEIGHT).size(TITLE_SIZE).bold(true));
        right.add(new TextLine(RIGHT_TITLE + dimensionSuffix).height(TITLE_HEIGHT).size(TITLE_SIZE).bold(true));

        List<String> keys = selectedKeys();
        boolean leftEmpty = true;
        boolean rightEmpty = true;
        if (category == StardewSelectorCategory.CROP) {
            // 按盆型分组排列：主世界（通用）→ 下界 → 末地，同一组的作物挨在一起。
            // 原来按资源包发现顺序排，三种盆型的作物混在一起，看不出哪些要配下界 / 末地盆。
            for (CropDefinition crop : cropsInGroupOrder()) {
                if (!matches(crop)) continue;
                if (keys.contains(crop.cropKey())) {
                    right.add(cropRow(crop, true));
                    rightEmpty = false;
                } else {
                    left.add(cropRow(crop, false));
                    leftEmpty = false;
                }
            }
        } else {
            for (StardewToolDefinition entry : module.index().entriesFor(category)) {
                if (!matches(entry)) continue;
                if (keys.contains(entry.key())) {
                    right.add(toolRow(entry, true));
                    rightEmpty = false;
                } else {
                    left.add(toolRow(entry, false));
                    leftEmpty = false;
                }
            }
        }
        if (leftEmpty) left.add(new TextLine(EMPTY_TEXT).height(TITLE_HEIGHT));
        if (rightEmpty) right.add(new TextLine(EMPTY_TEXT).height(TITLE_HEIGHT));
    }

    /**
     * 按「真实字体像素」量出名称两列的自然宽度（旧项目 {@code recomputeColumns()} 的等价物）。
     *
     * <p>只依赖本类别的完整候选集，不依赖搜索词——输入关键字时列宽不会跳动。工具类的种子列
     * 自然宽度为 0，仍按 {@link #SEED_NAME_MIN} 占位，保证与作物行同列对齐。</p>
     */
    private void recomputeColumns() {
        float name = 0f;
        float seed = 0f;
        if (category == StardewSelectorCategory.CROP) {
            for (CropDefinition crop : module.index().crops()) {
                name = Math.max(name, MinecraftText.measure(safe(crop.chineseName()), NAME_SIZE, false));
                seed = Math.max(seed, MinecraftText.measure(safe(crop.seedDisplayName()), NAME_SIZE, false));
            }
        } else {
            for (StardewToolDefinition entry : module.index().entriesFor(category)) {
                name = Math.max(name, MinecraftText.measure(safe(entry.displayName()), NAME_SIZE, false));
            }
        }
        nameNatural = name;
        seedNatural = seed;
    }

    // ── 行构建（文案与 tooltip 逐字照旧） ──

    /**
     * 作物按盆型分组排序：主世界（通用）→ 下界 → 末地。
     *
     * <p>组内保持资源包原有顺序（{@code sort} 稳定），同一组的作物始终挨在一起，
     * 玩家能一眼看出「哪些是通用、哪些要配下界盆、哪些要配末地盆」。</p>
     */
    private List<CropDefinition> cropsInGroupOrder() {
        List<CropDefinition> ordered = new ArrayList<>(module.index().crops());
        ordered.sort(Comparator.comparingInt(crop -> groupOrder(CropPotGroups.of(crop.cropKey()))));
        return ordered;
    }

    private static int groupOrder(PotGroup group) {
        return switch (group) {
            case NORMAL -> 0;
            case NETHER -> 1;
            case END -> 2;
        };
    }

    private CompactElement cropRow(CropDefinition crop, boolean selected) {
        String produce = cropProduceModel(crop);
        return new Row(produce, cropNameWithGroup(crop), cropNameTooltip(crop, produce),
            crop.seedModel(), safe(crop.seedDisplayName()), seedTooltip(crop),
            crop.cropKey(), selected);
    }

    /**
     * 作物名带上盆型分组标签。
     *
     * <p>分组来自资源包声明的维度限制（见 {@code CropPotGroups}），不是手写名单，所以下界与
     * 末地两种作物都会被标出来。标在名字前面，玩家一眼能看出哪些是通用作物、哪些要配下界 /
     * 末地盆，不用去猜为什么某个作物种下去不长。</p>
     */
    private String cropNameWithGroup(CropDefinition crop) {
        String name = safe(crop.chineseName());
        PotGroup group = CropPotGroups.of(crop.cropKey());
        if (group == PotGroup.NORMAL) return name;
        String tag = group == PotGroup.NETHER ? "§c[下界]" : "§5[末地]";
        // 实测得来的分组标一个记号，与资源包声明的分开，便于玩家核实
        String mark = CropPotGroups.isLearned(crop.cropKey()) ? "§7•" : "";
        return tag + mark + "§r " + name;
    }

    private CompactElement toolRow(StardewToolDefinition entry, boolean selected) {
        return new Row(entry.itemModel(), safe(entry.displayName()), toolTooltip(entry),
            null, null, null, entry.key(), selected);
    }

    /** 作物名行 tooltip：逐字照旧（技术ID / 成熟产物 / 识别状态） */
    private static String cropNameTooltip(CropDefinition crop, String produce) {
        return "§f" + safe(crop.chineseName())
            + "\n§7技术ID §8▸ §f" + safe(crop.cropKey())
            + "\n§a成熟产物 §8▸ §f" + safe(produce)
            + "\n§7识别状态 §8▸ §7" + evidenceLabel(crop.evidence());
    }

    /** 作物种子行 tooltip：逐字照旧（种子 / 技术ID） */
    private static String seedTooltip(CropDefinition crop) {
        return "§7种子 §8▸ §f" + safe(crop.seedDisplayName())
            + "\n§7技术ID §8▸ §f" + safe(crop.seedModel());
    }

    /** 工具行 tooltip：逐字照旧（名称 / 技术ID / 识别状态） */
    private static String toolTooltip(StardewToolDefinition entry) {
        return "§f" + safe(entry.displayName())
            + "\n§7技术ID §8▸ §f" + safe(entry.itemModel())
            + "\n§7识别状态 §8▸ §7" + evidenceLabel(entry.evidence().displayName());
    }

    /** 作物主图标：优先成熟产物模型，无产物回退种子模型（口径见 {@link CropDefinition#iconModel()}） */
    private static String cropProduceModel(CropDefinition crop) {
        return crop.iconModel();
    }

    /** 资源识别可信度（已确认 / 攻略 / 候选 / 未知）——旧项目 evidenceLabel 同口径 */
    private static String evidenceLabel(String evidence) {
        if (evidence == null) return "未知";
        if (evidence.equals(RuleEvidence.VERIFIED.displayName())) return "已确认";
        if (evidence.equals(RuleEvidence.DOCUMENTED.displayName())) return "攻略";
        if (evidence.equals(RuleEvidence.CANDIDATE.displayName())) return "候选";
        return "未知";
    }

    private static String safe(String value) {
        return value == null || value.isBlank() ? "无" : value;
    }

    private static String lower(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT);
    }

    // ── 过滤（字段与旧项目一致） ──

    private boolean matches(CropDefinition crop) {
        if (filter.isEmpty()) return true;
        if (lower(crop.cropKey()).contains(filter)) return true;
        if (lower(crop.chineseName()).contains(filter)) return true;
        if (crop.seedName() != null && lower(crop.seedName()).contains(filter)) return true;
        for (String name : crop.produceNames()) {
            if (name != null && lower(name).contains(filter)) return true;
        }
        return false;
    }

    private boolean matches(StardewToolDefinition entry) {
        if (filter.isEmpty()) return true;
        if (entry.displayName() != null && lower(entry.displayName()).contains(filter)) return true;
        return entry.itemModel() != null && lower(entry.itemModel()).contains(filter);
    }

    // ── 选择写回（旧 setSelected 语义：加入或移除键 → 落盘 → 重建界面） ──

    /** 本类别当前选择的内存镜像（与模块运行时使用的是同一个 List 实例）；单选模式即这块地当前绑的作物 */
    private List<String> selectedKeys() {
        if (pickHandler != null) return pickCurrentKey == null ? List.of() : List.of(pickCurrentKey);
        return switch (category) {
            case CROP -> module.settings().selectedCropKeys;
            case POT -> module.settings().selectedPotKeys;
            case FERTILIZER -> module.settings().selectedFertilizerKeys;
            case POTION -> module.settings().selectedPotionKeys;
            case WATERING_CAN -> module.settings().selectedCanKeys;
            case SPRINKLER -> module.settings().selectedSprinklerKeys;
            case SHELTER -> module.settings().selectedShelterKeys;
        };
    }

    private void setSelected(String key, boolean selected) {
        // 单选模式（区域换作物）：把点中的作物交回调用方即可，不碰模块的目标选择集合、也不自行关窗
        if (pickHandler != null) {
            CropDefinition picked = cropByKey(key);
            if (picked != null) pickHandler.accept(picked);
            return;
        }
        List<String> keys = selectedKeys();
        if (selected) {
            // 盆型互斥：三种盆对应三套互不相通的物料（水 / 岩浆 / 龙息），同时管两种盆会让同一趟
            // 任务里既要浇水又要倒岩浆。这里在选中新盆型时把别的盆型组踢掉，同组盆型不受影响。
            if (category == StardewSelectorCategory.POT) keys.removeIf(other -> !samePotGroup(other, key));
            if (!keys.contains(key)) keys.add(key);
        } else {
            keys.remove(key);
        }
        persist();
        rebuild();
    }

    /** 两个盆型键是否属于同一盆型组；索引里查不到定义时按普通盆处理（与识图层同一口径） */
    private boolean samePotGroup(String a, String b) {
        return potGroupOf(a) == potGroupOf(b);
    }

    private PotGroup potGroupOf(String key) {
        return module.index() == null ? PotGroup.NORMAL : module.index().potGroupOf(key);
    }

    private CropDefinition cropByKey(String cropKey) {
        if (cropKey == null || module.index() == null) return null;
        for (CropDefinition crop : module.index().crops()) {
            if (cropKey.equals(crop.cropKey())) return crop;
        }
        return null;
    }

    /**
     * 写回当前服务器档案：直接交给模块的选择对象，与旧 {@code StardewTargetSetting.persist()}
     * 同一语义（只在已绑定服务器时写盘，落点即该服务器的 ServerKey）。
     */
    private void persist() {
        module.selection(category).persist();
    }

    // ── tooltip 悬浮层 ──

    /** 登记本帧要显示的 tooltip（锚点为设计坐标下的鼠标位置） */
    private void setTip(String text, float x, float y) {
        if (text == null || text.isEmpty()) return;
        tipText = text;
        tipX = x;
        tipY = y;
    }

    /** 绘制并清空本帧的 tooltip：多行按 {@code \n} 拆行，颜色码由 MinecraftText 解析 */
    private void paintTip(Canvas canvas, float alpha) {
        String text = tipText;
        tipText = null;
        if (text == null || text.isEmpty()) return;

        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        String[] lines = text.split("\n", -1);
        float width = 0f;
        for (String line : lines) width = Math.max(width, MinecraftText.measure(line, TIP_SIZE, false));
        width += TIP_PAD * 2f;
        float height = lines.length * TIP_LINE + TIP_PAD * 2f;

        GlassPanel.shadow(canvas, tipX, tipY, width, height, TIP_RADIUS, tc.shadow, alpha, 0.9f);
        GlassPanel.frost(canvas, tipX, tipY, width, height, TIP_RADIUS, tc.window, 0.94f, alpha);
        GlassPanel.rim(canvas, tipX, tipY, width, height, TIP_RADIUS, tc.rim, alpha, 0.22f);

        float cursorY = tipY + TIP_PAD;
        int base = nameColor();
        for (String line : lines) {
            MinecraftText.draw(canvas, line, tipX + TIP_PAD, cursorY + TIP_SIZE, TIP_SIZE, base, alpha);
            cursorY += TIP_LINE;
        }
    }

    // ── 两栏容器（先画两栏，再画 tooltip，保证提示不被行覆盖） ──

    private final class Columns implements CompactElement {

        private CompactStack left = new CompactStack(ROW_GAP);
        private CompactStack right = new CompactStack(ROW_GAP);

        private CompactStack left() {
            return left;
        }

        private CompactStack right() {
            return right;
        }

        private void reset() {
            left = new CompactStack(ROW_GAP);
            right = new CompactStack(ROW_GAP);
        }

        @Override
        public float height() {
            return Math.max(COLUMNS_MIN_HEIGHT, Math.max(left.height(), right.height()));
        }

        @Override
        public void update(float dt) {
            left.update(dt);
            right.update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
            float columnWidth = (width - COLUMN_GAP) * 0.5f;
            if (columnWidth <= 0f) return;
            left.draw(canvas, x, y, columnWidth, alpha, -Float.MAX_VALUE, Float.MAX_VALUE, mouseX, mouseY);
            right.draw(canvas, x + columnWidth + COLUMN_GAP, y, columnWidth, alpha,
                -Float.MAX_VALUE, Float.MAX_VALUE, mouseX, mouseY);
            paintTip(canvas, alpha);
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            float columnWidth = (width - COLUMN_GAP) * 0.5f;
            if (columnWidth <= 0f) return false;
            if (mx <= x + columnWidth) {
                return left.onClick(mx, my, x, y, columnWidth, Float.MAX_VALUE, button);
            }
            return right.onClick(mx, my, x + columnWidth + COLUMN_GAP, y, columnWidth, Float.MAX_VALUE, button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }

        @Override
        public void releaseDrag() {
            left.releaseDrag();
            right.releaseDrag();
        }
    }

    // ── 一行：[图标][名称][种子图标][种子名称][状态][+/-] ──

    private final class Row implements CompactElement {

        private final String iconModel;
        private final String nameText;
        private final String nameTip;
        private final String seedModel;
        private final String seedText;
        private final String seedTip;
        private final String key;
        private final boolean selected;
        private final IconButton action;

        private boolean hovered;
        private float hover;

        private Row(String iconModel, String nameText, String nameTip,
                    String seedModel, String seedText, String seedTip,
                    String key, boolean selected) {
            this.iconModel = iconModel;
            this.nameText = nameText;
            this.nameTip = nameTip;
            this.seedModel = seedModel;
            this.seedText = seedText;
            this.seedTip = seedTip;
            this.key = key;
            this.selected = selected;
            // 单选模式下每行都是「选它」（当前那一行也是同一个动作：选中即关闭），
            // 所以不显示多选才有的减号——否则看起来像「取消绑定这块地」
            this.action = new IconButton(pickHandler != null || !selected ? GLYPH_ADD : GLYPH_REMOVE,
                () -> setSelected(key, !selected));
        }

        @Override
        public float height() {
            return ROW_HEIGHT;
        }

        @Override
        public void update(float dt) {
            action.update(dt);
            hover += ((hovered ? 1f : 0f) - hover) * Math.min(1f, Math.max(0f, dt) * 12f);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
            ClickGuiThemeColors tc = ClickGuiThemeColors.current();
            float radius = GlassPanel.rowRadius(ROW_HEIGHT);
            float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
            GlassPanel.frost(canvas, x, y, width, ROW_HEIGHT, radius, tc.module, 0.70f, rowAlpha);
            GlassPanel.rim(canvas, x, y, width, ROW_HEIGHT, radius, tc.rim, alpha, 0.10f);

            hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + ROW_HEIGHT;
            if (hover > 0.01f) {
                GlassPanel.fill(canvas, x, y, width, ROW_HEIGHT, radius, tc.surfaceHover, rowAlpha * hover);
            }

            float centerY = y + ROW_HEIGHT / 2f;
            float[] geometry = geometry(x, width);
            float iconX = geometry[0];
            float nameX = geometry[1];
            float nameWidth = geometry[2];
            float seedIconX = geometry[3];
            float seedX = geometry[4];
            float seedWidth = geometry[5];
            float statusX = geometry[6];
            float actionX = geometry[7];

            if (iconModel != null) {
                drawIconCell(canvas, iconModel, iconX, centerY, alpha, mouseX, mouseY, y);
            }
            drawTextCell(canvas, nameText, "§f", nameColor(), nameX, nameWidth, centerY, alpha,
                mouseX, mouseY, y, nameTip);
            if (seedModel != null) {
                drawIconCell(canvas, seedModel, seedIconX, centerY, alpha, mouseX, mouseY, y);
            }
            if (seedText != null) {
                drawTextCell(canvas, seedText, "§7", SEED_NAME_COLOR, seedX, seedWidth, centerY, alpha,
                    mouseX, mouseY, y, seedTip);
            }

            MinecraftText.draw(canvas, selected ? "§b已选择" : "§a可添加", statusX,
                CardLayout.baseline(centerY, STATUS_SIZE), STATUS_SIZE, nameColor(), alpha);

            float actionY = centerY - ACTION / 2f;
            action.hover(mouseX, mouseY, actionX, actionY, ACTION);
            action.draw(canvas, actionX, actionY, alpha);
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            if (button != 0 || my < y || my > y + ROW_HEIGHT) return false;
            float[] geometry = geometry(x, width);
            float actionX = geometry[7];
            return action.onClick(mx, my, actionX, y + (ROW_HEIGHT - ACTION) / 2f, button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }

        /**
         * 列几何（**绝对坐标**，已含行左边界 {@code x}）：{@code [0]} 产物图标 X、{@code [1]} 名称列 X、
         * {@code [2]} 名称列宽、{@code [3]} 种子图标 X、{@code [4]} 种子名称 X、{@code [5]} 种子名称宽、
         * {@code [6]} 状态列 X、{@code [7]} 操作列 X。
         *
         * <p>依次为：产物图标 → 名称 → 种子图标 → 种子名 → 状态 → 加减号。绘制与点击共用同一份结果，
         * 因此点得到的一定是画出来的那个位置。</p>
         *
         * <p>与旧项目 {@code recomputeColumns()} 同口径：名称两列按本类别「最长真实名称 + 呼吸量」
         * 给足（各有压缩下限），两列合计超出可用宽度时按比例压缩；状态列按「可添加 / 已选择」的
         * 实测宽度给足，永不出现省略号。工具类没有种子名称，仍按种子列占位，因此六类列表的
         * 状态列与加减号永远同一 X。</p>
         */
        private float[] geometry(float x, float width) {
            float statusWidth = Math.max(MinecraftText.measure("可添加", STATUS_SIZE, false),
                MinecraftText.measure("已选择", STATUS_SIZE, false)) + STATUS_PAD;
            float reserved = PAD_X * 2f + ICON * 2f + GAP * 5f + statusWidth + ACTION;
            float free = Math.max(0f, width - reserved);

            float nameWidth = Math.max(NAME_MIN, nameNatural + NAME_PAD);
            float seedWidth = Math.max(SEED_NAME_MIN, seedNatural + NAME_PAD);
            float sum = nameWidth + seedWidth;
            if (sum > free && sum > 0f) {
                float fit = free / sum;
                nameWidth *= fit;
                seedWidth *= fit;
            }

            float iconX = x + PAD_X;
            float nameX = iconX + ICON + GAP;
            float seedIconX = nameX + nameWidth + GAP;
            float seedX = seedIconX + ICON + GAP;
            float statusX = seedX + seedWidth + GAP;
            float actionX = x + width - PAD_X - ACTION;
            return new float[]{iconX, nameX, nameWidth, seedIconX, seedX, seedWidth, statusX, actionX};
        }

        /** 图标列：资源包确实提供 items/ 定义才画真实物品，缺失时给可读的 {@code §8[?]} 与完整诊断 */
        private void drawIconCell(Canvas canvas, String model, float x, float centerY, float alpha,
                                  float mouseX, float mouseY, float rowY) {
            ItemStack stack = StardewPreview.of(model);
            if (stack.isEmpty()) {
                MinecraftText.draw(canvas, "§8[?]", x, CardLayout.baseline(centerY, ICON_TEXT_SIZE),
                    ICON_TEXT_SIZE, nameColor(), alpha);
                if (inCell(mouseX, mouseY, x, ICON, rowY)) {
                    setTip("§c缺失图标\n§7" + StardewPreview.diagnose(model),
                        mouseX + TIP_OFFSET_X, mouseY + TIP_OFFSET_Y);
                }
                return;
            }
            ItemIconCache.getInstance().draw(canvas, stack, x, centerY - ICON / 2f, ICON);
        }

        /** 文本列：超宽按可用宽度加省略号，完整原文在 tooltip 里 */
        private void drawTextCell(Canvas canvas, String text, String prefix, int baseColor, float x, float width,
                                  float centerY, float alpha, float mouseX, float mouseY, float rowY,
                                  String tip) {
            // 前缀是旧项目该列的颜色代码（名称 §f、种子名 §7）：走 MinecraftText 既能在深色主题保持
            // 原样，也能在浅色主题映射成可读的深色，不会出现「白字画在白底上」。
            String shown = CardLayout.ellipsize(text, Math.max(0f, width), NAME_SIZE);
            MinecraftText.draw(canvas, prefix + shown, x, CardLayout.baseline(centerY, NAME_SIZE), NAME_SIZE,
                baseColor, alpha);
            if (tip != null && inCell(mouseX, mouseY, x, width, rowY)) {
                setTip(tip, mouseX + TIP_OFFSET_X, mouseY + TIP_OFFSET_Y);
            }
        }

        private boolean inCell(float mouseX, float mouseY, float x, float width, float rowY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= rowY && mouseY <= rowY + ROW_HEIGHT;
        }
    }

    // ── 水壶页顶部提示行（文案与 tooltip 逐字照旧） ──

    private final class HintLine implements CompactElement {

        private final String text;
        private final String detail;

        private HintLine(String text, String detail) {
            this.text = text;
            this.detail = detail;
        }

        @Override
        public float height() {
            return HINT_LINE_HEIGHT;
        }

        @Override
        public void update(float dt) {
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
            MinecraftText.draw(canvas, text, x + TIP_PAD, CardLayout.baseline(y + HINT_LINE_HEIGHT / 2f, HINT_SIZE),
                HINT_SIZE, nameColor(), alpha);
            if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HINT_LINE_HEIGHT) {
                setTip(detail, mouseX + TIP_OFFSET_X, mouseY + TIP_OFFSET_Y);
            }
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
}
