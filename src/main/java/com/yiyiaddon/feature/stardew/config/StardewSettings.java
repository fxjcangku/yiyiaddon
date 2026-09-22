package com.yiyiaddon.feature.stardew.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.render.world.ShapeMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 星露谷农场全部设置项的数据载体。
 *
 * <p>设置名、描述、默认值与取值域逐字来自旧项目
 * {@code stardew/StardewFarmModule.java:280-421}（旧框架的 {@code Setting} 定义），
 * 禁止增删设置项、禁止改名、禁止改默认值。</p>
 *
 * <p><b>六类选择不在这里落盘：</b>旧项目里它们虽然也是设置项，但真正的持久化是
 * {@code StardewSelectionStore} 按 ServerKey 隔离写 {@code StardewFarm/selection/<服务器>.json}，
 * 内存值切服即被替换。本类只保留内存镜像，不写进模块设置文件。</p>
 *
 * <p><b>渲染配色：</b>每类渲染对象各自独立持有「显示 / 颜色 / 渲染模式」，颜色用
 * {@link EspColor}（含彩虹开关），渲染模式用 {@link ShapeMode}；点位字牌没有渲染模式。</p>
 */
public final class StardewSettings {

    // ━━━ 运行参数：启动 ━━━

    /** 进服自启，默认 false */
    public boolean autoStart = false;

    // ━━━ 运行参数：移动与交互 ━━━

    /** 交互距离，默认 4，取值域 3~8 */
    public int reach = 4;
    /** 扫描预算，默认 16，取值域 4~64 */
    public int scanBudget = 16;
    /** 回中心等待时间，默认 10，取值域 1~300 */
    public int returnCenterDelay = 10;
    /** 批量动作（右键 / 破坏同 tick 连发上限），默认 8，取值域 1~15 */
    public int batchActions = 8;

    // ━━━ 运行参数：浇水 ━━━

    /** 自动浇水，默认 true */
    public boolean autoWater = true;
    /** 自动切换水壶，默认 true */
    public boolean switchCan = true;
    /** 恢复原手持，默认 true */
    public boolean restoreHand = true;

    // ━━━ 运行参数：施肥 / 药剂 ━━━

    /** 自动施肥，默认 false */
    public boolean autoFertilize = false;
    /** 自动用药剂，默认 false */
    public boolean autoPotion = false;

    // ━━━ 运行参数：洒水器 ━━━

    /** 洒水器维护，默认 false */
    public boolean sprinklerMaintenance = false;
    /** 洒水器检查间隔（tick），默认 1200，取值域 200~12000 */
    public int sprinklerInterval = 1200;

    // ━━━ 运行参数：播报 ━━━

    /** 状态提示，默认 true */
    public boolean statusHints = true;

    // ━━━ 运行参数：分区种植 ━━━

    /** 分区错位自动清理（挖掉种错的作物再补种），默认开启 */
    public boolean autoClearMismatch = true;

    /** 选点工具的物品键；{@code null} = 不限（默认，手持任何物品都算选点）。指定后只有空手或手持该物品才算选点。 */
    public String regionToolKey = null;

    /** 选点工具的显示名（只用于回执与界面显示；键才是判据） */
    public String regionToolName = null;

    // ━━━ 后勤参数 ━━━

    /**
     * 简化后勤，默认 false（走逐作物那四个后勤阈值）。
     *
     * <p>用户 2026-09-22：「卸货补货那些种子单独设置那个简单精简模式能不能默认关闭」。
     * 关掉≠改数值：{@code CropLogistics.DEFAULT} 就是简化那组（种子少于 2 补、补到 8；
     * 成品攒 8 卸、不留底），所以没单独设过的作物行为和开着完全一样，只是界面按作物展开，
     * 你想给某个作物单独调阈值时立刻就能生效。</p>
     */
    public boolean logisticsSimple = false;

    // ━━━ 渲染：点位渲染（每类独立） ━━━

    /** 种植区域：每块已划分的地一个框；淡白线框，提亮到约 35% 才看得清 */
    public final EspRenderObject renderRegions = new EspRenderObject("种植区域",
        "渲染已划分的种植区域范围（每块地一个框，字牌写区域号与作物）", 0xFFFFFF, 90, ShapeMode.Lines);
    /** 洒水器本体方块：深蓝，与浅青的覆盖范围分层 */
    public final EspRenderObject renderSprinklerBody = new EspRenderObject("洒水器本体",
        "高亮已绑定的洒水器本体方块（与覆盖范围互不影响）", 0x1B3FBF, 160, ShapeMode.Both);
    /** 洒水器覆盖范围：浅青蓝，压在本体上面也能看清 */
    public final EspRenderObject renderSprinklerCoverage = new EspRenderObject("洒水器覆盖范围",
        "高亮洒水器覆盖范围（半径按洒水器等级 1~4 推导，仅用于观察，不参与决策）", 0x66D9FF, 70, ShapeMode.Lines);
    /** 洒水器点位标记：亮青，压在深蓝本体上做点位标记 */
    public final EspRenderObject renderSprinklerPoint = new EspRenderObject("洒水器点位",
        "在每个已绑定洒水器中心显示点位标记（用于确认点位已绑定）", 0x00E5FF, 200, ShapeMode.Lines);
    /** 种子箱：绿（种子） */
    public final EspRenderObject renderSeedBox = new EspRenderObject("种子箱",
        "高亮种子箱方块", 0x35C759, 160, ShapeMode.Lines);
    /** 成品箱：金（收成） */
    public final EspRenderObject renderOutputBox = new EspRenderObject("成品箱",
        "高亮成品箱方块", 0xFFB300, 160, ShapeMode.Lines);
    /** 补水点：水蓝（水；用户 2026-09-21 指定该取值） */
    public final EspRenderObject renderWaterSource = new EspRenderObject("补水点",
        "高亮补水点方块", 0x35B7FF, 180, ShapeMode.Lines);
    /** 岩浆箱：橙红（岩浆），下界盆取岩浆、存空桶 */
    public final EspRenderObject renderLavaBox = new EspRenderObject("岩浆箱",
        "高亮岩浆箱方块（下界种植盆取岩浆、存回空桶）", 0xFF6A00, 180, ShapeMode.Lines);
    /** 龙息箱：紫（龙息），末地盆取龙息、存玻璃瓶 */
    public final EspRenderObject renderBreathBox = new EspRenderObject("龙息箱",
        "高亮龙息箱方块（末地种植盆取龙息、存回玻璃瓶）", 0xB44FFF, 180, ShapeMode.Lines);
    /** 点位字牌：只有显示开关（加粗、颜色跟随各类点位方框色），没有渲染模式、没有单独颜色 */
    public final EspRenderObject renderLabels = new EspRenderObject("点位字牌",
        "各绑定点位头顶的文字标签（加粗 + 底板，颜色跟随各类点位方框色，只有显示开关，没有单独颜色与渲染模式）",
        0xFFFFFF, 255, null, false);

    /**
     * 点位字牌字号（GUI 缩放坐标），默认 12。
     *
     * <p>字牌大小与显示开关 / 颜色分开：字号是「一类排版参数」，不随单个点位类别变化，
     * 因此单独一个字段，不塞进 {@link EspRenderObject}。</p>
     */
    public int labelSize = 12;

    /** 全部渲染对象，顺序即界面顺序 */
    private final List<EspRenderObject> renderObjects = List.of(
        renderRegions,
        renderSprinklerBody, renderSprinklerCoverage, renderSprinklerPoint,
        renderSeedBox, renderOutputBox, renderWaterSource, renderLavaBox, renderBreathBox, renderLabels);

    /** 调色板版本：小于它的旧存档会被 {@link #migratePalette()} 升级一次 */
    private static final int PALETTE_REVISION = 3;

    /**
     * 后勤默认值版本：小于它的旧存档会被迁到「简化后勤 = 关闭」。
     *
     * <p>旧版把默认值 {@code true} 写进了每一份存档，所以光改字段默认值对老档无效 ——
     * 用户要的是「默认关闭」，于是按版本迁一次。这次迁移<b>不改任何数值</b>：
     * 逐作物阈值的初始值（{@code CropLogistics.DEFAULT}）就是简化那组（2 / 8 / 8 / 0），
     * 没单独调过的作物，迁前迁后行为完全一样；手动调到过阈值的作物反而从此按他调的值走。
     * 想回到简化档，后勤页把「简化后勤」勾上即可。</p>
     */
    private static final int LOGISTICS_DEFAULT_REVISION = 2;

    /**
     * 改过名的渲染对象：当前名 → 旧名。
     *
     * <p><b>为什么必须有：</b>设置键是 {@code render.<对象名>.show}，对象改名等于换键，老存档里的
     * 开关 / 颜色 / 渲染模式全部读不回来，实机表现就是「改完重启就回默认」。这里在「新键不存在」时
     * 接着读旧键（{@code save} 只写新键），旧值因此能原样接上，不必让玩家重设一遍。</p>
     */
    private static final Map<String, String> LEGACY_RENDER_NAME = Map.of(
        "种植区域", "农田边界");

    /** 历版出厂默认色（按渲染对象名，一名可多版），只用来判断「这个颜色玩家有没有动过」 */
    private static final Map<String, int[][]> LEGACY_PALETTE = Map.of(
        "洒水器本体", new int[][]{{0x00B4FF, 60}},
        "洒水器覆盖范围", new int[][]{{0x00C8FF, 40}},
        "洒水器点位", new int[][]{{0x00FFB4, 150}},
        "种子箱", new int[][]{{0x00C8FF, 120}},
        "成品箱", new int[][]{{0xFFAA00, 120}},
        "补水点", new int[][]{{0xFF00FF, 120}, {0x1E6FFF, 180}});

    // ━━━ 六类选择（内存镜像，按 ServerKey 落盘见 StardewSelectionStore） ━━━

    public final List<String> selectedCropKeys = new ArrayList<>();
    public final List<String> selectedPotKeys = new ArrayList<>();
    public final List<String> selectedFertilizerKeys = new ArrayList<>();
    public final List<String> selectedPotionKeys = new ArrayList<>();
    public final List<String> selectedCanKeys = new ArrayList<>();
    public final List<String> selectedSprinklerKeys = new ArrayList<>();
    /** 已选温室玻璃（盆上方 5 格内有它 → 作物不因季节枯萎，非当季照常播种） */
    public final List<String> selectedShelterKeys = new ArrayList<>();

    // ━━━ 取值域（界面输入框与服务层共用同一份，禁止各自再写一遍） ━━━

    public static final int REACH_MIN = 3;
    public static final int REACH_MAX = 8;
    public static final int SCAN_BUDGET_MIN = 4;
    public static final int SCAN_BUDGET_MAX = 64;
    public static final int RETURN_CENTER_DELAY_MIN = 1;
    public static final int RETURN_CENTER_DELAY_MAX = 300;
    public static final int BATCH_ACTIONS_MIN = 1;
    public static final int BATCH_ACTIONS_MAX = 15;
    public static final int SPRINKLER_INTERVAL_MIN = 200;
    public static final int SPRINKLER_INTERVAL_MAX = 12000;
    public static final int LABEL_SIZE_MIN = 6;
    public static final int LABEL_SIZE_MAX = 32;

    // ━━━ 界面文案：设置名与描述（逐字，禁止改写） ━━━

    public static final String NAME_AUTO_START = "进服自启";
    public static final String DESC_AUTO_START = "进入正确世界且世界就绪后自动启动农场（默认关闭，手动停止优先）";

    public static final String NAME_REACH = "交互距离";
    public static final String DESC_REACH = "能操作多远的方块，原版上限约 4.5 格";

    public static final String NAME_SCAN_BUDGET = "扫描预算";
    public static final String DESC_SCAN_BUDGET = "每 tick 最多扫描多少格，过大导致资源包解析卡顿";

    public static final String NAME_RETURN_CENTER_DELAY = "回中心等待时间";
    public static final String DESC_RETURN_CENTER_DELAY = "没有可执行任务时，玩家连续静止多少秒才自动返回农田中心";

    public static final String NAME_BATCH_ACTIONS = "批量动作";
    public static final String DESC_BATCH_ACTIONS = "同一 tick 最多对几个格子连发交互包（1 = 关闭）：收割 / 浇水 / 播种 / 施肥发右键，"
        + "清枯苗 / 清错位 / 清杂物发左键破坏（只打盆上方那格，不碰盆）。默认 8，上限 15；填得越高越快——同 tick 多个交互包"
        + "更容易被服务器丢掉或反作弊注意到";

    public static final String NAME_AUTO_WATER = "自动浇灌";
    public static final String DESC_AUTO_WATER = "盆干了就自动浇灌：普通盆浇水（水壶 / 补水点），"
        + "下界盆浇岩浆，末地盆浇龙息；岩浆 / 龙息会自动去对应的料箱取。关掉 = 浇灌我自己来，"
        + "脚本只保留播种 / 收割 / 清理";

    public static final String NAME_SWITCH_CAN = "自动切换水壶";
    public static final String DESC_SWITCH_CAN = "浇水 / 补水前自动把已选水壶切到主手（默认开启）";

    public static final String NAME_RESTORE_HAND = "恢复原手持";
    public static final String DESC_RESTORE_HAND = "浇水 / 补水完成后恢复切换前的主手物品（默认开启）";

    public static final String NAME_AUTO_FERTILIZE = "自动施肥";
    public static final String DESC_AUTO_FERTILIZE = "开启后才对空盆施肥（默认关闭，选择肥料不等于自动施肥）";

    public static final String NAME_AUTO_POTION = "自动用药剂";
    public static final String DESC_AUTO_POTION = "开启后才使用魔法药剂（默认关闭，选择药剂不等于自动用药剂）";

    public static final String NAME_SPRINKLER_MAINTENANCE = "洒水器维护";
    public static final String DESC_SPRINKLER_MAINTENANCE = "定时检查已绑定洒水器并按需补水（默认关闭）";

    public static final String NAME_SPRINKLER_INTERVAL = "洒水器检查间隔";
    public static final String DESC_SPRINKLER_INTERVAL = "洒水器维护的检查间隔（tick，20=1秒）";

    public static final String NAME_STATUS_HINTS = "状态提示";
    public static final String DESC_STATUS_HINTS = "在聊天栏显示农场运行状态（正在浇水 / 收获 / 播种 / 补货 / 卸货 / 种子回收等）。"
        + "默认开启；启动自检结论、季节限制与解除、错误与失效提示不受本开关影响";

    public static final String NAME_REGION_TOOL = "选点工具";
    public static final String DESC_REGION_TOOL = "圈地时拿什么当「笔」：默认「不限」——手持任何物品都能点角"
        + "（选区模式内左右键一律不落到世界里，不会挖到作物）。指定一个物品后它变成白名单："
        + "只有空手或手持它才算点角，其余物品只被拦下、不落点";

    public static final String NAME_AUTO_CLEAR_MISMATCH = "自动清理错位作物";
    public static final String DESC_AUTO_CLEAR_MISMATCH = "某块单一作物区里种了别的作物时，自动走过去把它挖掉，"
        + "空出的盆按这块地绑定的作物补种（默认开启）；关掉则只把错位格用红框标出并停住，等你手动清理。"
        + "本开关同时管盆上杂物（认不出的方块）：开着照挖、关掉不动手。分区内的枯死作物不受本开关影响，"
        + "一律静默清掉再补种——冬天冻死成片时不再报「区域里的作物不对」，也不停机。"
        + "混种区域种什么都算对，不参与错位判定；挖除量受「批量动作」档位约束（1 = 一格一格挖）";

    public static final String NAME_LOGISTICS_SIMPLE = "简化后勤";
    public static final String DESC_LOGISTICS_SIMPLE = "开启时不用设置补货 / 卸货：种子少于 2 去补、补到 8；成品攒到 8 去卸、不留底。"
        + "关闭后才会逐作物显示那四个后勤阈值（默认关闭）。关掉不改数值：没单独设过的作物用的就是上面这组，"
        + "和开启时完全一样 —— 区别只是你能给某个作物单独调";

    public static final String NAME_LABEL_SIZE = "字牌大小";
    public static final String DESC_LABEL_SIZE = "各绑定点位头顶文字的字号（取值域 6~32，默认 12）；"
        + "字越大越远也看得清，越容易挡住视线";

    // ━━━ 全部渲染对象（顺序即界面顺序） ━━━

    /**
     * 全部渲染对象。
     *
     * <p>载体是共用件 {@link EspRenderObject}（用户 2026-09-19：「所有标点选择点位位置的模块 参照
     * 星露谷农场的点位设置」）：显示 / 颜色 / 渲染模式三件与编解码只此一份，星露谷、
     * 自动挖矿、自动农场、村民交易、自动箱子、自动附魔六个点位页共用同一套界面行与设置窗口。</p>
     */
    public List<EspRenderObject> renderObjects() {
        return renderObjects;
    }

    // ━━━ 持久化 ━━━

    /** 写入 JSON；渲染对象按对象名做键前缀，颜色走 {@link EspColor#save} */
    public void save(JsonObject json) {
        if (json == null) return;
        json.addProperty("autoStart", autoStart);
        json.addProperty("reach", reach);
        json.addProperty("scanBudget", scanBudget);
        json.addProperty("returnCenterDelay", returnCenterDelay);
        json.addProperty("batchActions", batchActions);
        json.addProperty("autoWater", autoWater);
        json.addProperty("switchCan", switchCan);
        json.addProperty("restoreHand", restoreHand);
        json.addProperty("autoFertilize", autoFertilize);
        json.addProperty("autoPotion", autoPotion);
        json.addProperty("sprinklerMaintenance", sprinklerMaintenance);
        json.addProperty("sprinklerInterval", sprinklerInterval);
        json.addProperty("statusHints", statusHints);
        json.addProperty("autoClearMismatch", autoClearMismatch);
        if (regionToolKey != null) json.addProperty("regionToolKey", regionToolKey);
        if (regionToolName != null) json.addProperty("regionToolName", regionToolName);
        json.addProperty("logisticsSimple", logisticsSimple);
        json.addProperty("logisticsRevision", LOGISTICS_DEFAULT_REVISION);
        json.addProperty("labelSize", labelSize);
        json.addProperty("paletteRevision", PALETTE_REVISION);
        for (EspRenderObject object : renderObjects) {
            object.save(json, "render." + object.name() + ".");
        }
    }

    /** 读取 JSON；缺项保留默认值，非法值一律夹到取值域内 */
    public void load(JsonObject json) {
        if (json == null) return;
        autoStart = boolOf(json, "autoStart", autoStart);
        reach = clamp(intOf(json, "reach", reach), REACH_MIN, REACH_MAX);
        scanBudget = clamp(intOf(json, "scanBudget", scanBudget), SCAN_BUDGET_MIN, SCAN_BUDGET_MAX);
        returnCenterDelay = clamp(intOf(json, "returnCenterDelay", returnCenterDelay),
            RETURN_CENTER_DELAY_MIN, RETURN_CENTER_DELAY_MAX);
        batchActions = clamp(intOf(json, "batchActions", batchActions), BATCH_ACTIONS_MIN, BATCH_ACTIONS_MAX);
        autoWater = boolOf(json, "autoWater", autoWater);
        switchCan = boolOf(json, "switchCan", switchCan);
        restoreHand = boolOf(json, "restoreHand", restoreHand);
        autoFertilize = boolOf(json, "autoFertilize", autoFertilize);
        autoPotion = boolOf(json, "autoPotion", autoPotion);
        sprinklerMaintenance = boolOf(json, "sprinklerMaintenance", sprinklerMaintenance);
        sprinklerInterval = clamp(intOf(json, "sprinklerInterval", sprinklerInterval),
            SPRINKLER_INTERVAL_MIN, SPRINKLER_INTERVAL_MAX);
        statusHints = boolOf(json, "statusHints", statusHints);
        autoClearMismatch = boolOf(json, "autoClearMismatch", autoClearMismatch);
        regionToolKey = stringOf(json, "regionToolKey");
        regionToolName = stringOf(json, "regionToolName");
        logisticsSimple = boolOf(json, "logisticsSimple", logisticsSimple);
        // 旧档里的 logisticsSimple 是旧默认（true）写下的，不是玩家开的：按版本关掉，
        // 换成逐作物那四个阈值（初始值与简化那组相同，行为不变；见 LOGISTICS_DEFAULT_REVISION）
        if (intOf(json, "logisticsRevision", 1) < LOGISTICS_DEFAULT_REVISION) logisticsSimple = false;
        labelSize = clamp(intOf(json, "labelSize", labelSize), LABEL_SIZE_MIN, LABEL_SIZE_MAX);
        for (EspRenderObject object : renderObjects) {
            object.load(json, renderPrefix(json, object));
        }
        if (intOf(json, "paletteRevision", 1) < PALETTE_REVISION) migratePalette();
    }

    /**
     * 该对象当前该读哪个前缀：优先新键；新键一个都没有、而这个对象改过名时，改读旧键。
     *
     * <p>判据取 {@code show} 与 {@code colorRgb} 两个键——它们是每个对象必然写出的项，
     * 只要有一个在，就说明这份存档用的是这个对象名。</p>
     */
    private static String renderPrefix(JsonObject json, EspRenderObject object) {
        String prefix = "render." + object.name() + ".";
        if (json.has(prefix + "show") || json.has(prefix + "colorRgb")) return prefix;
        String legacyName = LEGACY_RENDER_NAME.get(object.name());
        return legacyName == null ? prefix : "render." + legacyName + ".";
    }

    /**
     * 调色板升级（旧存档一次性）：把「还等于历版出厂默认色」的对象换成新默认色。
     *
     * <p>玩家自己调过的颜色一律保留，所以升级不会抹掉手工配置；颜色本来就已经是别的值的也不动。
     * 历版出厂值按对象存成一组（补水点原为洋红 {@code 0xFF00FF}、后为 {@code 0x1E6FFF}，
     * 现为水蓝 {@code 0x35B7FF}），命中任意一版即视为「没动过」，升到当前默认色。</p>
     */
    private void migratePalette() {
        for (EspRenderObject object : renderObjects) {
            int[][] past = LEGACY_PALETTE.get(object.name());
            if (past == null) continue;
            for (int[] color : past) {
                if (object.color.rgb() != color[0] || object.color.alpha() != color[1]) continue;
                object.color.rgb(object.defaultRgb()).alpha(object.defaultAlpha());
                break;
            }
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        try {
            return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsInt() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    /** 读取字符串字段；缺失 / null / 空白一律返回 null（默认「不限」） */
    private static String stringOf(JsonObject json, String key) {
        try {
            JsonElement element = json.get(key);
            if (element == null || !element.isJsonPrimitive()) return null;
            String value = element.getAsString();
            return value == null || value.isBlank() ? null : value;
        } catch (Exception ignored) {
            return null;
        }
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        try {
            JsonElement element = json.get(key);
            return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }
}
