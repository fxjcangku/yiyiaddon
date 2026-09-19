package com.yiyiaddon.feature.autofarm.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.feature.autofarm.model.HarvestMode;
import com.yiyiaddon.feature.autofarm.model.PlantMode;
import com.yiyiaddon.feature.autofarm.resource.FarmResourceManager;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.render.world.ShapeMode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动农场设置项的数据载体：名称、描述、默认值与取值域逐字来自旧项目
 * {@code AutoFarmMatrix} 构造器（{@code :136-281}），本类只做字段编解码与运行期取值。
 *
 * <p><b>键名口径</b>：落盘键沿用本项目的英文技术键；六条锚点串沿用旧项目隐藏设置的
 * 原键名（{@code _anchor_start} 等），由模块直接读写 {@code module-state.json}，
 * 保证旧存档点位串（{@code x,y,z,维度}）无需迁移即可读回。</p>
 */
public final class AutoFarmSettings {

    // ─── 辅助工具（旧分组「辅助工具」） ───
    /** 农田范围内拦截跳跃键，避免踩坏耕地 */
    public boolean antiTrample = true;
    /** 农田范围内发现草方块/泥土时，自动拿锄头锄成耕地；背包无锄头则跳过 */
    public boolean autoTill = true;

    // ─── 作物选择（旧分组「作物选择」） ───
    /** 双作物：种子与产物分离（小麦、甜菜根），值为方块注册表 ID */
    public Map<String, Boolean> cropsDouble = new LinkedHashMap<>();
    /** 单作物：产物即种子（胡萝卜、马铃薯、下界疣） */
    public Map<String, Boolean> cropsSingle = new LinkedHashMap<>();
    /** 柱状物：切根部上方（竹子、甘蔗、仙人掌） */
    public Map<String, Boolean> cropsPillar = new LinkedHashMap<>();
    /** 果实：只砍果实（南瓜、西瓜） */
    public Map<String, Boolean> cropsFruit = new LinkedHashMap<>();

    // ─── 逐作物独立配置（旧分组「逐作物独立配置」，键为作物枚举名） ───
    /** 每作物独立卸货数量（组） */
    public Map<String, Integer> perCropUnload = new LinkedHashMap<>();
    /** 每作物独立补货种子数量（组） */
    public Map<String, Integer> perCropRestock = new LinkedHashMap<>();

    // ─── 运行参数（旧分组「运行参数」） ───
    /** 收割模式：单个 / 批量 */
    public HarvestMode harvestMode = HarvestMode.SINGLE;
    /** 补种模式：顺序 / 轮转 / 就近 */
    public PlantMode plantMode = PlantMode.SEQUENTIAL;
    /** 杂物（毒马铃薯 + 仙人掌花）攒够多少个才卸货一次 */
    public int poisonUnloadThreshold = 64;
    /** 每 tick 最多发送多少个破坏/播种/容器操作包 */
    public int bpt = 10;
    /** 能操作多远的方块（格） */
    public int reachDistance = 4;

    // ─── 渲染显示（旧分组「渲染显示」） ───
    // 2026-09-19：本组渲染项整体换成共用件 EspRenderObject（显示 / 颜色 / 渲染模式各自独立），
    // 与星露谷农场点位设置同一套载体与落盘键（用户要求「所有标点选择点位位置的模块参照星露谷」）。
    // 对象名是落盘键的一部分，一经发布不得再改：改名等于换键，老存档的开关与颜色会读不回来。

    /** 农田边界：只画外框一圈，默认白色 50（与旧 renderBounds + boundsColor 观感一致） */
    public final EspRenderObject renderBounds = new EspRenderObject("农田边界",
        "只渲染农场范围的外框一圈（不填面），大农场也不卡", 0xFFFFFF, 50, ShapeMode.Lines);
    /** 当前目标：默认绿色 75、线 + 面（与旧 renderTarget + targetColor 观感一致） */
    public final EspRenderObject renderTarget = new EspRenderObject("当前目标",
        "高亮当前正在作业的目标方块", 0x00FF64, 75, ShapeMode.Both);
    /** 单作物箱：默认金 */
    public final EspRenderObject renderSingleBox = new EspRenderObject("单作物箱",
        "高亮单作物箱方块", 0xFFC800, 160, ShapeMode.Lines);
    /** 多作物箱：默认粉紫 */
    public final EspRenderObject renderMultiBox = new EspRenderObject("多作物箱",
        "高亮多作物箱方块", 0xFF66FF, 160, ShapeMode.Lines);
    /** 种子补货箱：默认青 */
    public final EspRenderObject renderSeedBox = new EspRenderObject("种子补货箱",
        "高亮种子补货箱方块", 0x55FFFF, 160, ShapeMode.Lines);
    /** 杂物箱：默认红 */
    public final EspRenderObject renderPoisonBox = new EspRenderObject("杂物箱",
        "高亮杂物箱方块", 0xFF5555, 160, ShapeMode.Lines);
    /** 点位字牌：只有显示开关（加粗、颜色跟随界面主题），没有单独颜色与渲染模式 */
    public final EspRenderObject renderLabels = new EspRenderObject("点位字牌",
        "各绑定点位头顶的文字标签（加粗 + 底板，颜色跟随界面主题，只有显示开关，没有单独颜色与渲染模式）",
        0xFFFFFF, 255, null, false);

    /**
     * 各绑定点位头顶文字的字号（GUI 缩放坐标），默认 10。
     *
     * <p>字号是「一类排版参数」，不随单个点位类别变化，因此单独一个字段，不塞进
     * {@link EspRenderObject}（与星露谷同一口径）。</p>
     */
    public int labelSize = 10;

    /** 全部渲染对象，顺序即界面顺序 */
    private final List<EspRenderObject> renderObjects = List.of(
        renderBounds, renderTarget,
        renderSingleBox, renderMultiBox, renderSeedBox, renderPoisonBox,
        renderLabels);

    /** 某作物的卸货数量（组），未配置走旧默认 8 */
    public int unloadGroups(CropProfile crop) {
        return perCropUnload.getOrDefault(crop.name(), FarmResourceManager.DEFAULT_UNLOAD_GROUPS);
    }

    /** 某作物的补货种子数量（组），未配置走旧默认 3 */
    public int restockGroups(CropProfile crop) {
        return perCropRestock.getOrDefault(crop.name(), FarmResourceManager.DEFAULT_RESTOCK_GROUPS);
    }

    // ── 编解码 ──

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回旧项目取值域 */
    public void load(JsonObject json) {
        if (json == null) return;
        antiTrample = boolOf(json, "antiTrample", antiTrample);
        autoTill = boolOf(json, "autoTill", autoTill);

        cropsDouble = loadSelection(json, "cropsDouble");
        cropsSingle = loadSelection(json, "cropsSingle");
        cropsPillar = loadSelection(json, "cropsPillar");
        cropsFruit = loadSelection(json, "cropsFruit");

        perCropUnload = loadGroups(json, "perCropUnload");
        perCropRestock = loadGroups(json, "perCropRestock");

        harvestMode = enumOf(json, "harvestMode", HarvestMode.class, harvestMode);
        plantMode = enumOf(json, "plantMode", PlantMode.class, plantMode);
        poisonUnloadThreshold = clamp(intOf(json, "poisonUnloadThreshold", poisonUnloadThreshold), 1, 64);
        bpt = clamp(intOf(json, "bpt", bpt), 1, 30);
        reachDistance = clamp(intOf(json, "reachDistance", reachDistance), 3, 8);

        labelSize = clamp(intOf(json, "labelSize", labelSize), LABEL_SIZE_MIN, LABEL_SIZE_MAX);
        for (EspRenderObject object : renderObjects) {
            object.load(json, "render." + object.name() + ".");
        }
        migrateLegacyRender(json);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键） */
    public void save(JsonObject json) {
        json.addProperty("antiTrample", antiTrample);
        json.addProperty("autoTill", autoTill);

        saveSelection(json, "cropsDouble", cropsDouble);
        saveSelection(json, "cropsSingle", cropsSingle);
        saveSelection(json, "cropsPillar", cropsPillar);
        saveSelection(json, "cropsFruit", cropsFruit);

        saveGroups(json, "perCropUnload", perCropUnload);
        saveGroups(json, "perCropRestock", perCropRestock);

        json.addProperty("harvestMode", harvestMode.name());
        json.addProperty("plantMode", plantMode.name());
        json.addProperty("poisonUnloadThreshold", poisonUnloadThreshold);
        json.addProperty("bpt", bpt);
        json.addProperty("reachDistance", reachDistance);

        json.addProperty("labelSize", labelSize);
        for (EspRenderObject object : renderObjects) {
            object.save(json, "render." + object.name() + ".");
        }
    }

    // ── 渲染对象与老存档迁移 ──

    /**
     * 全部渲染对象（顺序即界面顺序）。
     *
     * <p>载体是共用件 {@link EspRenderObject}（用户 2026-09-19：「所有标点选择点位位置的模块参照
     * 星露谷农场的点位设置」）：显示 / 颜色 / 渲染模式三件与编解码只此一份，自动农场的点位页
     * 与星露谷等模块共用同一套界面行与设置窗口。</p>
     */
    public List<EspRenderObject> renderObjects() {
        return renderObjects;
    }

    /**
     * 老存档一次性迁移：旧键还在、而对应的新键一个都没写出时，把旧值接进新的渲染对象。
     *
     * <p><b>为什么按「新键不存在」判断：</b>{@code save} 现在只写新键
     * （{@code render.<对象名>.show / colorRgb …}），因此新键存在就说明这份存档已经升级过、
     * 不能再被旧键覆盖；旧键只在这条一次性通道里读取，读到的开关与颜色原样接上，
     * 玩家不必重新设置一遍。</p>
     */
    private void migrateLegacyRender(JsonObject json) {
        // 农田边界：旧 renderBounds + boundsColor* → 新「农田边界」
        migrateShow(json, "renderBounds", renderBounds);
        migrateColor(json, "boundsColor", renderBounds, 0xFFFFFF, 50);
        // 当前目标：旧 renderTarget + targetColor* → 新「当前目标」
        migrateShow(json, "renderTarget", renderTarget);
        migrateColor(json, "targetColor", renderTarget, 0x00FF64, 75);
        // 点位字牌：旧 renderLabels → 新「点位字牌」（只有显示开关）
        migrateShow(json, "renderLabels", renderLabels);
    }

    /** 旧布尔开关 → 新对象的 show；已写出新键或旧键缺失时不动 */
    private static void migrateShow(JsonObject json, String legacyKey, EspRenderObject object) {
        if (!json.has(legacyKey)) return;
        String prefix = "render." + object.name() + ".";
        if (json.has(prefix + "show")) return;
        object.show = boolOf(json, legacyKey, object.show);
    }

    /** 旧颜色五键 → 新对象的颜色；已写出新颜色或旧键缺失时不动 */
    private static void migrateColor(JsonObject json, String legacyKey, EspRenderObject object,
                                     int fallbackRgb, int fallbackAlpha) {
        if (!json.has(legacyKey + "Rgb") && !json.has(legacyKey + "Alpha")) return;
        String prefix = "render." + object.name() + ".";
        if (json.has(prefix + "colorRgb")) return;
        object.color.rgb(intOf(json, legacyKey + "Rgb", fallbackRgb))
            .alpha(clamp(intOf(json, legacyKey + "Alpha", fallbackAlpha), 0, 255))
            .rainbow(boolOf(json, legacyKey + "Rainbow", false))
            .rainbowSpeed(doubleOf(json, legacyKey + "RainbowSpeed", 0.4))
            .rainbowOffset(doubleOf(json, legacyKey + "RainbowOffset", 0.0));
    }

    // ── 渲染取值域与界面文案（点位页与服务层共用同一份，禁止各自再写一遍） ──

    public static final int LABEL_SIZE_MIN = 6;
    public static final int LABEL_SIZE_MAX = 32;

    public static final String NAME_LABEL_SIZE = "字牌大小";
    public static final String DESC_LABEL_SIZE = "各绑定点位头顶文字的字号（取值域 6~32，默认 10）；"
        + "字越大越远也看得清，越容易挡住视线";

    // ── 原语 ──

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        JsonElement e = json.get(key);
        return e != null && e.isJsonPrimitive() ? e.getAsBoolean() : fallback;
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        JsonElement e = json.get(key);
        return e != null && e.isJsonPrimitive() ? e.getAsInt() : fallback;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static <T extends Enum<T>> T enumOf(JsonObject json, String key, Class<T> type, T fallback) {
        JsonElement e = json.get(key);
        if (e == null || !e.isJsonPrimitive()) return fallback;
        try {
            return Enum.valueOf(type, e.getAsString());
        } catch (IllegalArgumentException ignored) {
            return fallback;
        }
    }

    /** 选择器选择集：键 = 方块注册表 ID，值恒 true（与本项目选择器的选中集同构） */
    private static Map<String, Boolean> loadSelection(JsonObject json, String key) {
        Map<String, Boolean> out = new LinkedHashMap<>();
        JsonElement e = json.get(key);
        if (e != null && e.isJsonArray()) {
            for (JsonElement item : e.getAsJsonArray()) {
                if (item.isJsonPrimitive()) out.put(item.getAsString(), true);
            }
        }
        return out;
    }

    private static void saveSelection(JsonObject json, String key, Map<String, Boolean> selection) {
        com.google.gson.JsonArray array = new com.google.gson.JsonArray();
        for (String id : selection.keySet()) array.add(id);
        json.add(key, array);
    }

    /** 逐作物数量表：键 = 作物枚举名 */
    private static Map<String, Integer> loadGroups(JsonObject json, String key) {
        Map<String, Integer> out = new LinkedHashMap<>();
        JsonElement e = json.get(key);
        if (e != null && e.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : e.getAsJsonObject().entrySet()) {
                if (entry.getValue().isJsonPrimitive()) {
                    out.put(entry.getKey(), entry.getValue().getAsInt());
                }
            }
        }
        return out;
    }

    private static void saveGroups(JsonObject json, String key, Map<String, Integer> groups) {
        JsonObject object = new JsonObject();
        for (Map.Entry<String, Integer> entry : groups.entrySet()) {
            object.addProperty(entry.getKey(), entry.getValue());
        }
        json.add(key, object);
    }

    private static double doubleOf(JsonObject json, String key, double fallback) {
        JsonElement e = json.get(key);
        return e != null && e.isJsonPrimitive() ? e.getAsDouble() : fallback;
    }
}
