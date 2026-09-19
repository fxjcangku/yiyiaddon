package com.yiyiaddon.feature.autochest.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.model.autochest.ContainerType;
import com.yiyiaddon.model.autochest.ContainerTypeRegistry;
import com.yiyiaddon.model.autochest.EspStyle;
import com.yiyiaddon.model.autochest.ScanMode;
import com.yiyiaddon.model.autochest.WithdrawMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动箱子全部设置项的数据载体。
 *
 * <p>设置名、描述、默认值与取值域逐字来自旧项目
 * {@code autochest/config/AutoChestSettings.java:80-266}，禁止增删设置项、禁止改名、禁止改默认值。</p>
 *
 * <p><b>唯一例外（用户 2026-09-19）</b>：{@link #labelSize}「字牌大小」是本项目新增项 —— 用户要求把标点类
 * 模块的点位渲染设置向星露谷农场点位页对齐（点位颜色可自定义 / 文字大小可自定义 / ESP 样式）；
 * 旧项目与本次移植版本都没有这一项（旧 {@code AutoChestRenderer} 只画框、不画文字），
 * 故不适用「逐字搬旧项目」的约束，其余设置项仍一字未改。</p>
 *
 * <p>「目标物品」不在本类内：旧项目该设置项的数据源唯一来自 ID 配置管理，本项目对应
 * {@code config/identity/IdentityTargetConfig}（同一份数据，不另起炉灶）。本类只保留
 * 「每种物品数量」映射，键为身份键。</p>
 *
 * <p>颜色以 ARGB 打包整数保存，便于 JSON 持久化；解码用 {@link #r(int)} / {@link #g(int)} /
 * {@link #b(int)} / {@link #a(int)}。</p>
 */
public final class AutoChestSettings {

    // ━━━ 运行模式 ━━━

    /** 运行模式，默认 {@code 玩家控制模式} */
    public ScanMode scanMode = ScanMode.PLAYER_CONTROL;

    // ━━━ 玩家控制模式 ━━━

    /** 触发距离，默认 4，取值域 1~4 */
    public int triggerDistance = 4;

    // ━━━ 容器 ━━━

    /** 已启用的容器类型 id；默认全部启用 */
    public final List<String> containerTypeIds = new ArrayList<>();

    /** 检测范围（格），默认 16，最小 4 */
    public int scanRadius = 16;

    /** 扫描周期（Tick），默认 20，最小 1 */
    public int scanInterval = 20;

    /** 已处理记录过期（分钟），默认 30，最小 0 */
    public int recordExpireMinutes = 30;

    // ━━━ 保护 ━━━

    /** 多人保护，默认 true */
    public boolean multiplayerProtect = true;

    /** 玩家检测距离，默认 3，最小 1 */
    public int playerDetectDistance = 3;

    /** 最大重试次数，默认 3，最小 1 */
    public int maxRetries = 3;

    /** 临时冷却（Tick），默认 100，最小 20 */
    public int cooldownTicks = 100;

    // ━━━ 取物 ━━━

    /** 取物模式，默认 {@code 按目标数量取} */
    public WithdrawMode withdrawMode = WithdrawMode.TARGET_COUNT;

    /** 每种物品数量：身份键 → 目标数量（默认空） */
    public final Map<String, Integer> itemQuantities = new LinkedHashMap<>();

    /** 动作延迟（Tick），默认 2，最小 1 */
    public int actionDelay = 2;

    // ━━━ 渲染 ━━━

    /** ESP 高亮，默认 true */
    public boolean renderEsp = true;

    /** ESP 框样式，默认 {@code 线+面} */
    public EspStyle espStyle = EspStyle.BOTH;

    /** 未处理颜色，默认 (0,255,0,80) */
    public int unprocessedColor = 0x5000FF00;

    /** 已处理颜色，默认 (255,0,0,80) */
    public int processedColor = 0x50FF0000;

    /** 处理中颜色，默认 (255,200,0,80) */
    public int processingColor = 0x50FFC800;

    /**
     * 字牌大小（容器头顶文字字号），默认 12，取值域 {@link #LABEL_SIZE_MIN}~{@link #LABEL_SIZE_MAX}。
     *
     * <p><b>默认值与取值域的依据</b>：本模块（含旧项目）此前只画容器框、从不绘制文字，没有可继承的
     * 旧字号；故直接采用共用件 {@code PointRenderSection.labelSizeRow} 的字牌口径 —— 星露谷点位页
     * {@code StardewSettings.labelSize} 的默认 12、取值域 6~32，与用户「所有标点选择点位位置的模块
     * 参照星露谷农场的点位设置」的口径一致。</p>
     *
     * <p><b>消费方现状（如实登记）</b>：{@code AutoChestRenderer} 只画容器框、不画文字（旧项目同样如此，
     * 见 {@code villager/render/ContainerESP} 里「只画线框，不画文字」的同类登记），因此本项目前只由
     * 控制台「渲染」页 / 「点位」页的数字框读写与落盘，尚无渲染消费点。若以后给容器加字牌，字号取本项，
     * 并按本项目既有口径在 {@code EspRenderer.text} 内自动乘全局 {@code EspGlobalSettings.textScale()}
     * （即实际字号 = labelSize × 全局倍率），本项只作模块自己的基准字号。</p>
     */
    public int labelSize = 12;

    /** 字牌大小取值下限（与星露谷点位页同一口径） */
    public static final int LABEL_SIZE_MIN = 6;

    /** 字牌大小取值上限（与星露谷点位页同一口径） */
    public static final int LABEL_SIZE_MAX = 32;

    /** 字牌大小行名（与共用件口径同源的文案） */
    public static final String NAME_LABEL_SIZE = "字牌大小";

    /** 字牌大小行描述（本模块画的是容器，故把共用文案里的「点位」写成「容器」） */
    public static final String DESC_LABEL_SIZE = "容器头顶文字的字号（字越大越远也看得清，越容易挡住视线）";

    /** 数量上限：36 格 × 64 = 2304（旧项目 {@code ItemQuantitySetting.MAX_COUNT}） */
    public static final int MAX_COUNT = 36 * 64;

    /** 数量默认值（旧项目 {@code ItemQuantityScreen} 输入框默认） */
    public static final int DEFAULT_COUNT = 64;

    public AutoChestSettings() {
        for (ContainerType type : ContainerTypeRegistry.all()) containerTypeIds.add(type.id());
    }

    /** 已启用的容器类型对象列表（顺序与注册表一致） */
    public List<ContainerType> enabledTypes() {
        List<ContainerType> result = new ArrayList<>();
        for (ContainerType type : ContainerTypeRegistry.all()) {
            if (containerTypeIds.contains(type.id())) result.add(type);
        }
        return result;
    }

    /** 切换某容器类型的启用状态 */
    public void setContainerTypeEnabled(String id, boolean enabled) {
        if (id == null) return;
        if (enabled) {
            if (!containerTypeIds.contains(id)) containerTypeIds.add(id);
        } else {
            containerTypeIds.remove(id);
        }
    }

    /** 恢复默认：全部容器类型启用 */
    public void resetContainerTypes() {
        containerTypeIds.clear();
        for (ContainerType type : ContainerTypeRegistry.all()) containerTypeIds.add(type.id());
    }

    /** 某身份键的目标数量；未配置返回默认 64（旧项目 {@code ItemQuantitySetting.quantityOf}） */
    public int quantityOf(String identityKey) {
        Integer value = itemQuantities.get(identityKey);
        return value == null ? DEFAULT_COUNT : value;
    }

    /** 设置某身份键的目标数量（覆盖，仅下限 1，与旧项目 {@code setQuantity} 一致） */
    public void setQuantity(String identityKey, int count) {
        if (identityKey == null) return;
        itemQuantities.put(identityKey, Math.max(1, count));
    }

    /** 已配置数量的条目数（计数标签用） */
    public int configuredQuantityCount() {
        return itemQuantities.size();
    }

    /** 清空全部数量配置 */
    public void resetQuantities() {
        itemQuantities.clear();
    }

    // ━━━ 颜色解码 ━━━

    public static int a(int argb) {
        return (argb >> 24) & 0xFF;
    }

    public static int r(int argb) {
        return (argb >> 16) & 0xFF;
    }

    public static int g(int argb) {
        return (argb >> 8) & 0xFF;
    }

    public static int b(int argb) {
        return argb & 0xFF;
    }

    /** 打包为 ARGB */
    public static int argb(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

    // ━━━ 持久化 ━━━

    /** 写入 JSON（键名与本项目其它模块配置文件保持一致风格） */
    public void save(JsonObject json) {
        json.addProperty("scanMode", scanMode.name());
        json.addProperty("triggerDistance", triggerDistance);
        JsonArray types = new JsonArray();
        for (String id : containerTypeIds) types.add(id);
        json.add("containerTypes", types);
        json.addProperty("scanRadius", scanRadius);
        json.addProperty("scanInterval", scanInterval);
        json.addProperty("recordExpireMinutes", recordExpireMinutes);
        json.addProperty("multiplayerProtect", multiplayerProtect);
        json.addProperty("playerDetectDistance", playerDetectDistance);
        json.addProperty("maxRetries", maxRetries);
        json.addProperty("cooldownTicks", cooldownTicks);
        json.addProperty("withdrawMode", withdrawMode.name());
        JsonObject quantities = new JsonObject();
        for (Map.Entry<String, Integer> entry : itemQuantities.entrySet()) {
            quantities.addProperty(entry.getKey(), entry.getValue());
        }
        json.add("itemQuantities", quantities);
        json.addProperty("actionDelay", actionDelay);
        json.addProperty("renderEsp", renderEsp);
        json.addProperty("espStyle", espStyle.name());
        json.addProperty("unprocessedColor", unprocessedColor);
        json.addProperty("processedColor", processedColor);
        json.addProperty("processingColor", processingColor);
        json.addProperty("labelSize", labelSize);
    }

    /** 读取 JSON；缺项保留默认值，非法枚举值回退默认 */
    public void load(JsonObject json) {
        if (json == null) return;
        scanMode = enumOf(json, "scanMode", ScanMode.class, scanMode);
        triggerDistance = clamp(intOf(json, "triggerDistance", triggerDistance), 1, 4);
        if (json.has("containerTypes") && json.get("containerTypes").isJsonArray()) {
            containerTypeIds.clear();
            for (JsonElement element : json.getAsJsonArray("containerTypes")) {
                if (element.isJsonPrimitive()) containerTypeIds.add(element.getAsString());
            }
        }
        scanRadius = Math.max(4, intOf(json, "scanRadius", scanRadius));
        scanInterval = Math.max(1, intOf(json, "scanInterval", scanInterval));
        recordExpireMinutes = Math.max(0, intOf(json, "recordExpireMinutes", recordExpireMinutes));
        multiplayerProtect = boolOf(json, "multiplayerProtect", multiplayerProtect);
        playerDetectDistance = Math.max(1, intOf(json, "playerDetectDistance", playerDetectDistance));
        maxRetries = Math.max(1, intOf(json, "maxRetries", maxRetries));
        cooldownTicks = Math.max(20, intOf(json, "cooldownTicks", cooldownTicks));
        withdrawMode = enumOf(json, "withdrawMode", WithdrawMode.class, withdrawMode);
        itemQuantities.clear();
        if (json.has("itemQuantities") && json.get("itemQuantities").isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject("itemQuantities").entrySet()) {
                if (entry.getValue().isJsonPrimitive()) {
                    itemQuantities.put(entry.getKey(),
                        Math.max(1, entry.getValue().getAsInt()));
                }
            }
        }
        actionDelay = Math.max(1, intOf(json, "actionDelay", actionDelay));
        renderEsp = boolOf(json, "renderEsp", renderEsp);
        espStyle = enumOf(json, "espStyle", EspStyle.class, espStyle);
        unprocessedColor = intOf(json, "unprocessedColor", unprocessedColor);
        processedColor = intOf(json, "processedColor", processedColor);
        processingColor = intOf(json, "processingColor", processingColor);
        labelSize = clamp(intOf(json, "labelSize", labelSize), LABEL_SIZE_MIN, LABEL_SIZE_MAX);
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

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        try {
            return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsBoolean() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static <E extends Enum<E>> E enumOf(JsonObject json, String key, Class<E> type, E fallback) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive()) return fallback;
        try {
            return Enum.valueOf(type, json.get(key).getAsString());
        } catch (Exception ignored) {
            return fallback;
        }
    }
}
