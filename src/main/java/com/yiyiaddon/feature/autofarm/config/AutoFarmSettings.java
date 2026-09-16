package com.yiyiaddon.feature.autofarm.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.feature.autofarm.model.HarvestMode;
import com.yiyiaddon.feature.autofarm.model.PlantMode;
import com.yiyiaddon.feature.autofarm.resource.FarmResourceManager;
import com.yiyiaddon.ui.render.world.EspColor;

import java.util.LinkedHashMap;
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
    /** 只渲染农场范围的外框一圈（不填面） */
    public boolean renderBounds = true;
    /** 边界框颜色（旧默认 255,255,255,50） */
    public final EspColor boundsColor = new EspColor();
    /** 高亮当前正在作业的目标方块 */
    public boolean renderTarget = true;
    /** 目标颜色（旧默认 0,255,100,75） */
    public final EspColor targetColor = new EspColor();
    /** 各绑定箱头顶显示防呆标签 */
    public boolean renderLabels = true;

    /** 构造期把颜色默认值置为旧项目原值（EspColor 是可变对象，不能在字段处写死） */
    public AutoFarmSettings() {
        boundsColor.rgb(0xFFFFFF).alpha(50);
        targetColor.rgb(0x00FF64).alpha(75);
    }

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

        renderBounds = boolOf(json, "renderBounds", renderBounds);
        loadColor(json, "boundsColor", boundsColor, 0xFFFFFF, 50);
        renderTarget = boolOf(json, "renderTarget", renderTarget);
        loadColor(json, "targetColor", targetColor, 0x00FF64, 75);
        renderLabels = boolOf(json, "renderLabels", renderLabels);
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

        json.addProperty("renderBounds", renderBounds);
        saveColor(json, "boundsColor", boundsColor);
        json.addProperty("renderTarget", renderTarget);
        saveColor(json, "targetColor", targetColor);
        json.addProperty("renderLabels", renderLabels);
    }

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

    /** 颜色三键：rgb / alpha / rainbow（与本项目其它 ESP 模块同一套键） */
    private static void loadColor(JsonObject json, String key, EspColor color, int fallbackRgb, int fallbackAlpha) {
        color.rgb(intOf(json, key + "Rgb", fallbackRgb));
        color.alpha(clamp(intOf(json, key + "Alpha", fallbackAlpha), 0, 255));
        color.rainbow(boolOf(json, key + "Rainbow", false));
        color.rainbowSpeed(doubleOf(json, key + "RainbowSpeed", 0.4));
        color.rainbowOffset(doubleOf(json, key + "RainbowOffset", 0.0));
    }

    private static void saveColor(JsonObject json, String key, EspColor color) {
        json.addProperty(key + "Rgb", color.rgb());
        json.addProperty(key + "Alpha", color.alpha());
        json.addProperty(key + "Rainbow", color.rainbow());
        json.addProperty(key + "RainbowSpeed", color.rainbowSpeed());
        json.addProperty(key + "RainbowOffset", color.rainbowOffset());
    }

    private static double doubleOf(JsonObject json, String key, double fallback) {
        JsonElement e = json.get(key);
        return e != null && e.isJsonPrimitive() ? e.getAsDouble() : fallback;
    }
}
