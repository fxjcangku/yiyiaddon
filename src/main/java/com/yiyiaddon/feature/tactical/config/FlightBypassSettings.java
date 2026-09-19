package com.yiyiaddon.feature.tactical.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.tactical.core.FlightPolicy;

/**
 * 飞行绕过设置载体：6 项 / 2 组（模式选择 / 参数调整）。
 *
 * <p><b>逐字资产</b>：设置名、默认值、取值域、分组全部来自旧项目
 * {@code tactical/FlightBypass.java:63-130}；落盘键名直接用旧设置名本身
 * （与传送 / 图书管理员 / 村民交易的做法一致），改一个键名都会让老档读不出来。</p>
 *
 * <p><b>枚举落盘口径</b>：旧项目把 {@link FlightPolicy.FlightMode} 的中文显示名写入配置
 * （旧 {@code FlightPolicy:24} 注释：「显示名与配置存档名保持一致，迁移时用户配置不丢失」），
 * 因此这里同样以显示名存读，读不到的旧值回退默认模式。</p>
 *
 * <p><b>三项数字设置的控件形态</b>：旧项目均为 {@code IntSetting} + {@code noSlider()}，
 * 本项目控制台用 {@code SettingNumberBox}（第 123 条禁滑块），行为一致。</p>
 */
public final class FlightBypassSettings {

    // ── 取值域（逐字 = 旧设置声明） ──

    /** 滑翔速度档位域（旧 {@code IntSetting} 1~30） */
    public static final int GLIDE_SPEED_MIN = 1;
    public static final int GLIDE_SPEED_MAX = 30;
    /** 跳跃间隔域（旧 {@code IntSetting} 1~10） */
    public static final int JUMP_INTERVAL_MIN = 1;
    public static final int JUMP_INTERVAL_MAX = 10;
    /** 垫脚延迟域（旧 {@code IntSetting} 80~200） */
    public static final int SCAFFOLD_DELAY_MIN = 80;
    public static final int SCAFFOLD_DELAY_MAX = 200;
    /** 拉回降级阈值域（旧 {@code IntSetting} 2~10） */
    public static final int DEGRADE_THRESHOLD_MIN = 2;
    public static final int DEGRADE_THRESHOLD_MAX = 10;

    // ── 落盘键名（逐字 = 旧设置名） ──

    private static final String KEY_MODE = "飞行模式";
    private static final String KEY_GLIDE_SPEED = "滑翔速度（0.01格/tick）";
    private static final String KEY_JUMP_INTERVAL = "跳跃间隔（tick）";
    private static final String KEY_SCAFFOLD_DELAY = "垫脚延迟（ms）";
    private static final String KEY_ADAPTIVE = "自适应降级";
    private static final String KEY_DEGRADE_THRESHOLD = "拉回降级阈值";

    // ── 组① 模式选择 ──

    /** 飞行模式（默认发包飞行） */
    public FlightPolicy.FlightMode mode = FlightPolicy.FlightMode.PACKET_FLY;

    // ── 组② 参数调整 ──

    /** 滑翔速度档位，1 = 0.01 格/tick（默认 3） */
    public int glideSpeed = 3;
    /** 原版连跳间隔（默认 3 tick） */
    public int vanillaJumpInterval = 3;
    /** 垫脚放置后延迟拆除（默认 100 ms） */
    public int scaffoldDelay = 100;
    /** 自适应降级（默认开） */
    public boolean adaptiveSlowdown = true;
    /** 拉回降级阈值（默认 3 次） */
    public int rubberBandThreshold = 3;

    // ── 编解码（键名与旧设置名逐字对应） ──

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回取值域 */
    public void load(JsonObject json) {
        if (json == null) return;

        mode = modeOf(json, KEY_MODE, mode);
        glideSpeed = clamp(intOf(json, KEY_GLIDE_SPEED, glideSpeed), GLIDE_SPEED_MIN, GLIDE_SPEED_MAX);
        vanillaJumpInterval = clamp(intOf(json, KEY_JUMP_INTERVAL, vanillaJumpInterval),
            JUMP_INTERVAL_MIN, JUMP_INTERVAL_MAX);
        scaffoldDelay = clamp(intOf(json, KEY_SCAFFOLD_DELAY, scaffoldDelay),
            SCAFFOLD_DELAY_MIN, SCAFFOLD_DELAY_MAX);
        adaptiveSlowdown = boolOf(json, KEY_ADAPTIVE, adaptiveSlowdown);
        rubberBandThreshold = clamp(intOf(json, KEY_DEGRADE_THRESHOLD, rubberBandThreshold),
            DEGRADE_THRESHOLD_MIN, DEGRADE_THRESHOLD_MAX);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键） */
    public void save(JsonObject json) {
        json.addProperty(KEY_MODE, mode.displayName);

        json.addProperty(KEY_GLIDE_SPEED, glideSpeed);
        json.addProperty(KEY_JUMP_INTERVAL, vanillaJumpInterval);
        json.addProperty(KEY_SCAFFOLD_DELAY, scaffoldDelay);
        json.addProperty(KEY_ADAPTIVE, adaptiveSlowdown);
        json.addProperty(KEY_DEGRADE_THRESHOLD, rubberBandThreshold);
    }

    // ── 原语 ──

    /** 按中文显示名读枚举（旧项目的配置存档口径）；读不到保持回退值 */
    private static FlightPolicy.FlightMode modeOf(JsonObject json, String key, FlightPolicy.FlightMode fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive()) return fallback;
        String text = element.getAsString();
        for (FlightPolicy.FlightMode candidate : FlightPolicy.FlightMode.values()) {
            if (candidate.displayName.equals(text)) return candidate;
        }
        return fallback;
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsInt() : fallback;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
