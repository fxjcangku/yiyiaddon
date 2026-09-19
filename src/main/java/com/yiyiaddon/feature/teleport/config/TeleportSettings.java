package com.yiyiaddon.feature.teleport.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.ui.keybind.AddonKeybind;

/**
 * 传送模块设置载体：15 项 / 6 组（触发按键 / TP地面 / TP穿墙 / TP坐标 / 验证 / 调试）。
 *
 * <p><b>逐字资产</b>：设置名、默认值、取值域、分组全部来自旧项目
 * {@code TeleportModule:82-208}；落盘键名直接用旧设置名本身（与自动图书管理员 /
 * 自动村民交易的做法一致），改一个键名都会让老档读不出来。</p>
 *
 * <p><b>三项小数设置</b>（最大穿墙距离 / 落点修正范围 / 回弹判定阈值）在旧项目是
 * {@code DoubleSetting}，本项目控制台用 {@link com.yiyiaddon.ui.widget.SettingNumberBox}
 * 承载（第 123 条禁滑块），步进见控制台页面。</p>
 */
public final class TeleportSettings {

    // ── 取值域（逐字 = 旧设置声明） ──

    /** 地表扫描上限域（旧 {@code IntSetting} 16~320） */
    public static final int MAX_RISE_MIN = 16;
    public static final int MAX_RISE_MAX = 320;
    /** 最大穿墙距离域（旧 {@code DoubleSetting} 4~64） */
    public static final double MAX_DISTANCE_MIN = 4.0;
    public static final double MAX_DISTANCE_MAX = 64.0;
    /** 落点修正范围域（旧 {@code DoubleSetting} 1~8） */
    public static final double MAX_DEVIATION_MIN = 1.0;
    public static final double MAX_DEVIATION_MAX = 8.0;
    /** 最大下落落差域（旧 {@code IntSetting} 0~20） */
    public static final int MAX_FALL_MIN = 0;
    public static final int MAX_FALL_MAX = 20;
    /** 坐标域（逐字 = 旧设置声明） */
    public static final int COORD_XZ_MIN = -30000000;
    public static final int COORD_XZ_MAX = 30000000;
    public static final int COORD_Y_MIN = -64;
    public static final int COORD_Y_MAX = 640;
    /** 回退搜索半径域（旧 {@code IntSetting} 0~8） */
    public static final int FALLBACK_RADIUS_MIN = 0;
    public static final int FALLBACK_RADIUS_MAX = 8;
    /** 回弹判定阈值域（旧 {@code DoubleSetting} 0.25~4） */
    public static final double VERIFY_THRESHOLD_MIN = 0.25;
    public static final double VERIFY_THRESHOLD_MAX = 4.0;
    /** 验证窗口域（旧 {@code IntSetting} 2~20） */
    public static final int VERIFY_WINDOW_MIN = 2;
    public static final int VERIFY_WINDOW_MAX = 20;

    /** 玩家眼高兜底（旧 {@code TeleportRequest.eyeHeight} 默认 1.62） */
    public static final double DEFAULT_EYE_HEIGHT = 1.62;

    // ── 落盘键名（逐字 = 旧设置名） ──

    private static final String KEY_GROUND_BIND = "TP地面键";
    private static final String KEY_WALL_BIND = "TP穿墙键";
    private static final String KEY_COORD_BIND = "TP坐标键";
    private static final String KEY_MAX_RISE = "地表扫描上限";
    private static final String KEY_MAX_DISTANCE = "最大穿墙距离";
    private static final String KEY_MAX_DEVIATION = "落点修正范围";
    private static final String KEY_MAX_FALL = "最大下落落差";
    private static final String KEY_NO_FALL_DAMAGE = "摔落无伤";
    private static final String KEY_COORD_X = "坐标 X";
    private static final String KEY_COORD_Y = "坐标 Y";
    private static final String KEY_COORD_Z = "坐标 Z";
    private static final String KEY_FALLBACK_RADIUS = "回退搜索半径";
    private static final String KEY_VERIFY_THRESHOLD = "回弹判定阈值";
    private static final String KEY_VERIFY_WINDOW = "验证窗口";
    private static final String KEY_DEBUG_RENDER = "调试渲染";

    // ── 组① 触发按键（默认均未绑定，松开触发） ──

    /** TP地面键 */
    public AddonKeybind groundKey = AddonKeybind.none();
    /** TP穿墙键 */
    public AddonKeybind wallKey = AddonKeybind.none();
    /** TP坐标键 */
    public AddonKeybind coordKey = AddonKeybind.none();

    // ── 组② TP地面 ──

    /** 地表扫描上限（默认 200） */
    public int maxRise = 200;

    // ── 组③ TP穿墙 ──

    /** 最大穿墙距离（默认 16.0） */
    public double maxDistance = 16.0;
    /** 落点修正范围（默认 3.0） */
    public double maxDeviation = 3.0;
    /** 最大下落落差（默认 6） */
    public int maxFall = 6;
    /** 摔落无伤（默认 false） */
    public boolean noFallDamage = false;

    // ── 组④ TP坐标 ──

    /** 坐标 X（默认 0） */
    public int coordX = 0;
    /** 坐标 Y（默认 100） */
    public int coordY = 100;
    /** 坐标 Z（默认 0） */
    public int coordZ = 0;
    /** 回退搜索半径（默认 4） */
    public int fallbackRadius = 4;

    // ── 组⑤ 验证 ──

    /** 回弹判定阈值（默认 1.5） */
    public double verifyThreshold = 1.5;
    /** 验证窗口（默认 8） */
    public int verifyWindow = 8;

    // ── 组⑥ 调试 ──

    /** 调试渲染（默认 true：用户 2026-09-18 要求默认打开，调试期一眼看得见落点与候选格） */
    public boolean debugRender = true;

    // ── 编解码（键名与旧设置名逐字对应） ──

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回取值域。 */
    public void load(JsonObject json) {
        if (json == null) return;

        groundKey = AddonKeybind.fromJson(json.get(KEY_GROUND_BIND), groundKey);
        wallKey = AddonKeybind.fromJson(json.get(KEY_WALL_BIND), wallKey);
        coordKey = AddonKeybind.fromJson(json.get(KEY_COORD_BIND), coordKey);

        maxRise = clamp(intOf(json, KEY_MAX_RISE, maxRise), MAX_RISE_MIN, MAX_RISE_MAX);
        maxDistance = clamp(doubleOf(json, KEY_MAX_DISTANCE, maxDistance), MAX_DISTANCE_MIN, MAX_DISTANCE_MAX);
        maxDeviation = clamp(doubleOf(json, KEY_MAX_DEVIATION, maxDeviation), MAX_DEVIATION_MIN, MAX_DEVIATION_MAX);
        maxFall = clamp(intOf(json, KEY_MAX_FALL, maxFall), MAX_FALL_MIN, MAX_FALL_MAX);
        noFallDamage = boolOf(json, KEY_NO_FALL_DAMAGE, noFallDamage);

        coordX = clamp(intOf(json, KEY_COORD_X, coordX), COORD_XZ_MIN, COORD_XZ_MAX);
        coordY = clamp(intOf(json, KEY_COORD_Y, coordY), COORD_Y_MIN, COORD_Y_MAX);
        coordZ = clamp(intOf(json, KEY_COORD_Z, coordZ), COORD_XZ_MIN, COORD_XZ_MAX);
        fallbackRadius = clamp(intOf(json, KEY_FALLBACK_RADIUS, fallbackRadius),
            FALLBACK_RADIUS_MIN, FALLBACK_RADIUS_MAX);

        verifyThreshold = clamp(doubleOf(json, KEY_VERIFY_THRESHOLD, verifyThreshold),
            VERIFY_THRESHOLD_MIN, VERIFY_THRESHOLD_MAX);
        verifyWindow = clamp(intOf(json, KEY_VERIFY_WINDOW, verifyWindow),
            VERIFY_WINDOW_MIN, VERIFY_WINDOW_MAX);

        debugRender = boolOf(json, KEY_DEBUG_RENDER, debugRender);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键）。 */
    public void save(JsonObject json) {
        json.add(KEY_GROUND_BIND, groundKey.toJson());
        json.add(KEY_WALL_BIND, wallKey.toJson());
        json.add(KEY_COORD_BIND, coordKey.toJson());

        json.addProperty(KEY_MAX_RISE, maxRise);
        json.addProperty(KEY_MAX_DISTANCE, maxDistance);
        json.addProperty(KEY_MAX_DEVIATION, maxDeviation);
        json.addProperty(KEY_MAX_FALL, maxFall);
        json.addProperty(KEY_NO_FALL_DAMAGE, noFallDamage);

        json.addProperty(KEY_COORD_X, coordX);
        json.addProperty(KEY_COORD_Y, coordY);
        json.addProperty(KEY_COORD_Z, coordZ);
        json.addProperty(KEY_FALLBACK_RADIUS, fallbackRadius);

        json.addProperty(KEY_VERIFY_THRESHOLD, verifyThreshold);
        json.addProperty(KEY_VERIFY_WINDOW, verifyWindow);

        json.addProperty(KEY_DEBUG_RENDER, debugRender);
    }

    // ── 原语 ──

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsInt() : fallback;
    }

    private static double doubleOf(JsonObject json, String key, double fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsDouble() : fallback;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
