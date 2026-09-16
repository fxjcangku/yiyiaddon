package com.yiyiaddon.feature.water.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.ShapeMode;

/**
 * 水源显示模块设置项的数据载体：10 个设置项的名称、描述、默认值与取值域
 * 逐字来自旧项目 {@code WaterESPModule :82-151}（见 51 号第五节），本类只做字段编解码。
 *
 * <p>旧滑条形态不保留（差异 D-13-01）：{@code 扫描半径 / 渲染距离} 走数字框，值域不变；
 * 样式枚举存下标（线框 0 / 面 1 / 两者 2，{@link ShapeMode#labels()} 逐字）。</p>
 */
public final class WaterSettings {

    /** 扫描玩家周围多少格内的水源（旧默认 12，域 4~32） */
    public int scanRadius = 12;
    /** 只渲染玩家周围多少格内的框（旧默认 16，域 4~64） */
    public int renderDistance = 16;

    /** 显示每桶水能覆盖的 9×9 耕地范围 */
    public boolean renderRange = true;
    /** 灌溉范围的颜色（旧默认 30,144,255,60） */
    public final EspColor rangeColor = new EspColor();
    /** 在已有水源的上下左右显示可放水位置 */
    public boolean renderSuggestion = true;
    /** 建议放水点的颜色（旧默认 255,0,0,180） */
    public final EspColor suggestionColor = new EspColor();
    /** 建议点样式（旧默认 Both） */
    public ShapeMode suggestionShapeMode = ShapeMode.Both;
    /** 用小框标出水源方块本身 */
    public boolean renderSource = false;
    /** 水源方块颜色（旧默认 0,0,139,180） */
    public final EspColor sourceColor = new EspColor();
    /** 水源方块样式（旧默认 Both） */
    public ShapeMode sourceShapeMode = ShapeMode.Both;

    /** 构造期把颜色默认值置为旧项目原值（EspColor 是可变对象，不能在字段处写死） */
    public WaterSettings() {
        rangeColor.rgb(0x1E90FF).alpha(60);
        suggestionColor.rgb(0xFF0000).alpha(180);
        sourceColor.rgb(0x00008B).alpha(180);
    }

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回旧项目取值域 */
    public void load(JsonObject json) {
        if (json == null) return;
        scanRadius = clamp(intOf(json, "scanRadius", scanRadius), 4, 32);
        renderDistance = clamp(intOf(json, "renderDistance", renderDistance), 4, 64);

        renderRange = boolOf(json, "renderRange", renderRange);
        loadColor(json, "rangeColor", rangeColor, 0x1E90FF, 60);
        renderSuggestion = boolOf(json, "renderSuggestion", renderSuggestion);
        loadColor(json, "suggestionColor", suggestionColor, 0xFF0000, 180);
        suggestionShapeMode = shapeOf(json, "suggestionShapeMode", suggestionShapeMode);
        renderSource = boolOf(json, "renderSource", renderSource);
        loadColor(json, "sourceColor", sourceColor, 0x00008B, 180);
        sourceShapeMode = shapeOf(json, "sourceShapeMode", sourceShapeMode);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键） */
    public void save(JsonObject json) {
        json.addProperty("scanRadius", scanRadius);
        json.addProperty("renderDistance", renderDistance);

        json.addProperty("renderRange", renderRange);
        saveColor(json, "rangeColor", rangeColor);
        json.addProperty("renderSuggestion", renderSuggestion);
        saveColor(json, "suggestionColor", suggestionColor);
        json.addProperty("suggestionShapeMode", suggestionShapeMode.name());
        json.addProperty("renderSource", renderSource);
        saveColor(json, "sourceColor", sourceColor);
        json.addProperty("sourceShapeMode", sourceShapeMode.name());
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

    private static ShapeMode shapeOf(JsonObject json, String key, ShapeMode fallback) {
        JsonElement e = json.get(key);
        if (e == null || !e.isJsonPrimitive()) return fallback;
        try {
            return ShapeMode.valueOf(e.getAsString());
        } catch (IllegalArgumentException ignored) {
            return fallback;
        }
    }

    /** 颜色五键：rgb / alpha / 彩虹三键（与自动农场设置同一套键口径） */
    private static void loadColor(JsonObject json, String key, EspColor color, int fallbackRgb, int fallbackAlpha) {
        color.rgb(intOf(json, key + "Rgb", fallbackRgb));
        color.alpha(clamp(intOf(json, key + "Alpha", fallbackAlpha), 0, 255));
        color.rainbow(boolOf(json, key + "Rainbow", false));
        JsonElement speed = json.get(key + "RainbowSpeed");
        if (speed != null && speed.isJsonPrimitive()) color.rainbowSpeed(speed.getAsDouble());
    }

    private static void saveColor(JsonObject json, String key, EspColor color) {
        json.addProperty(key + "Rgb", color.rgb());
        json.addProperty(key + "Alpha", color.alpha());
        json.addProperty(key + "Rainbow", color.rainbow());
        json.addProperty(key + "RainbowSpeed", color.rainbowSpeed());
    }
}
