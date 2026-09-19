package com.yiyiaddon.feature.tactical.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.tactical.ServerDetectorModule.ResourcePackMode;

/**
 * 服务器检测设置载体：8 项 / 2 组（底裤侦测 / 资源包劫持）。
 *
 * <p><b>逐字资产</b>：设置名、描述、默认值、取值域、可见性条件、分组名全部来自旧项目
 * {@code tactical/ServerDetector.java:59-128}；落盘键名直接用旧设置名本身
 * （与飞行绕过 / 传送的做法一致），改一个键名都会让老档读不出来（第 172-175 条）。</p>
 *
 * <p><b>枚举落盘口径</b>：与 {@code FlightBypassSettings} 同口径——枚举按中文显示名存读
 * （旧项目的配置存档名与显示名一致），读不到的旧值回退默认模式。</p>
 *
 * <p><b>数值设置的控件形态</b>：旧项目 3 项数字设置均为 {@code IntSetting + noSlider()}，
 * 本项目控制台用 {@code SettingNumberBox}（第 123 条禁滑块），整数步进 1，行为一致。</p>
 */
public final class ServerDetectorSettings {

    // ── 取值域（逐字 = 旧设置声明） ──

    /** 侦测延迟域（旧 {@code IntSetting} 1~15 秒） */
    public static final int DETECT_DELAY_MIN = 1;
    public static final int DETECT_DELAY_MAX = 15;
    /** 下载重试次数域（旧 {@code IntSetting} 1~10 次） */
    public static final int DOWNLOAD_RETRIES_MIN = 1;
    public static final int DOWNLOAD_RETRIES_MAX = 10;
    /** 读取超时域（旧 {@code IntSetting} 10~300 秒） */
    public static final int DOWNLOAD_TIMEOUT_MIN = 10;
    public static final int DOWNLOAD_TIMEOUT_MAX = 300;

    // ── 落盘键名（逐字 = 旧设置名） ──

    private static final String KEY_DETECT_CORE = "检测服务器核心";
    private static final String KEY_DETECT_ANTICHEAT = "检测反作弊";
    private static final String KEY_ANNOUNCE = "公屏播报";
    private static final String KEY_DETECT_DELAY = "侦测延迟（秒）";
    private static final String KEY_RESOURCE_PACK_MODE = "资源包模式";
    private static final String KEY_DOWNLOAD_RETRIES = "重试次数";
    private static final String KEY_DOWNLOAD_TIMEOUT = "读取超时（秒）";
    private static final String KEY_RESUME_DOWNLOAD = "断点续传";

    // ── 组① 底裤侦测 ──

    /** 识别服务器核心（默认开） */
    public boolean detectCore = true;

    /** 识别反作弊（默认开） */
    public boolean detectAntiCheat = true;

    /** 侦测完成后在聊天栏输出结果（默认开） */
    public boolean announceDetection = true;

    /** 进服后等待多久开始侦测，单位秒（默认 3） */
    public int detectDelay = 3;

    // ── 组② 资源包劫持 ──

    /**
     * 资源包处理模式（默认自动白嫖）。
     *
     * <p>用户 2026-09-18：「服务器核心的资源包下载 默认打开白嫖模式」——服务器下发的资源包一律下载留档，
     * 而不是直接绕过不接。已经存过盘的旧配置仍以存档里的值为准（要改在控制台「资源包劫持」页切换）。</p>
     */
    public ResourcePackMode resourcePackMode = ResourcePackMode.AUTO_DOWNLOAD;

    /** 下载失败后的重试次数（默认 5；仅自动白嫖可见） */
    public int downloadRetries = 5;

    /** 单次读取超时，单位秒（默认 60；仅自动白嫖可见） */
    public int downloadTimeout = 60;

    /** 重试时用 Range 请求接着传（默认开；仅自动白嫖可见） */
    public boolean resumeDownload = true;

    // ── 编解码（键名与旧设置名逐字对应） ──

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回取值域 */
    public void load(JsonObject json) {
        if (json == null) return;

        detectCore = boolOf(json, KEY_DETECT_CORE, detectCore);
        detectAntiCheat = boolOf(json, KEY_DETECT_ANTICHEAT, detectAntiCheat);
        announceDetection = boolOf(json, KEY_ANNOUNCE, announceDetection);
        detectDelay = clamp(intOf(json, KEY_DETECT_DELAY, detectDelay), DETECT_DELAY_MIN, DETECT_DELAY_MAX);
        resourcePackMode = modeOf(json, KEY_RESOURCE_PACK_MODE, resourcePackMode);
        downloadRetries = clamp(intOf(json, KEY_DOWNLOAD_RETRIES, downloadRetries),
            DOWNLOAD_RETRIES_MIN, DOWNLOAD_RETRIES_MAX);
        downloadTimeout = clamp(intOf(json, KEY_DOWNLOAD_TIMEOUT, downloadTimeout),
            DOWNLOAD_TIMEOUT_MIN, DOWNLOAD_TIMEOUT_MAX);
        resumeDownload = boolOf(json, KEY_RESUME_DOWNLOAD, resumeDownload);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键） */
    public void save(JsonObject json) {
        json.addProperty(KEY_DETECT_CORE, detectCore);
        json.addProperty(KEY_DETECT_ANTICHEAT, detectAntiCheat);
        json.addProperty(KEY_ANNOUNCE, announceDetection);
        json.addProperty(KEY_DETECT_DELAY, detectDelay);
        json.addProperty(KEY_RESOURCE_PACK_MODE, resourcePackMode.displayName);
        json.addProperty(KEY_DOWNLOAD_RETRIES, downloadRetries);
        json.addProperty(KEY_DOWNLOAD_TIMEOUT, downloadTimeout);
        json.addProperty(KEY_RESUME_DOWNLOAD, resumeDownload);
    }

    // ── 原语 ──

    /** 按中文显示名读枚举（与飞行绕过同一口径）；读不到保持回退值 */
    private static ResourcePackMode modeOf(JsonObject json, String key, ResourcePackMode fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive()) return fallback;
        String text = element.getAsString();
        for (ResourcePackMode candidate : ResourcePackMode.values()) {
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
