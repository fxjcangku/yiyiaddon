package com.yiyiaddon.feature.admindetect.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理员检测全部设置项的数据载体。
 *
 * <p><b>旧项目来源</b>：旧 {@code admdetector/AdminDetectorModule.java} 两组共 7 项 ——
 * 检测组 5 项（{@code :59-90}）、名单组 2 项（{@code :92-102}）。名称与描述逐字取自旧项目
 * {@code IntSetting / BoolSetting / StringListSetting} 的 {@code .name(...)} 与
 * {@code .description(...)}。本类按 {@code feature/combat/config/KillAuraSettings} 的既有做法，
 * 用一个纯字段数据类承载，分组体现在下方三节注释里，界面按这三节落地。</p>
 *
 * <p><b>与旧项目的差异（用户 2026-09-16 拍板，勿自行改回）</b>：</p>
 * <ul>
 *     <li><b>检测范围默认值</b> {@code 10 → 64}（用户原话「玩家 64 格内 …一律识别」）；
 *         取值域仍是旧项目的 1~64，未改。</li>
 *     <li><b>白名单 / 黑名单的描述</b>改写：旧描述尾句是「逗号分隔多个名字」，承载控件按用户要求
 *         换成在线玩家选择器后该句不再成立（第 108 条：确需修改旧原文须单独说明理由，理由即本行）。</li>
 *     <li><b>新增「显示与警报」三项</b>（ESP 画框 / 射线 / 警报声），默认全开 ——
 *         旧项目没有这三项，属用户新增需求，不是迁移资产。</li>
 * </ul>
 */
public final class AdminDetectorSettings {

    /** 检测范围下限（旧项目 {@code .min(1)} 逐字） */
    public static final int RANGE_MIN = 1;

    /** 检测范围上限（旧项目 {@code .max(64)} 逐字） */
    public static final int RANGE_MAX = 64;

    /** 检测范围默认值：用户 2026-09-16 拍板 64（旧项目默认 10） */
    public static final int RANGE_DEFAULT = 64;

    // ━━━ 检测（旧 {@code sgDetect}） ━━━

    /** 旧 {@code detectRange}：默认见 {@link #RANGE_DEFAULT}，取值 {@value #RANGE_MIN}~{@value #RANGE_MAX} */
    public int detectRange = RANGE_DEFAULT;

    /** 旧 {@code detectSpectator}，默认 {@code true} */
    public boolean detectSpectator = true;

    /** 旧 {@code detectCreative}，默认 {@code true} */
    public boolean detectCreative = true;

    /** 旧 {@code detectInvisible}，默认 {@code true} */
    public boolean detectInvisible = true;

    /** 旧 {@code detectHidden}，默认 {@code true} */
    public boolean detectHidden = true;

    // ━━━ 名单（旧 {@code sgList}） ━━━

    /** 旧 {@code whitelist}，默认空；承载控件由逗号分隔文本改为在线玩家选择器（见类注释） */
    public final List<String> whitelist = new ArrayList<>();

    /** 旧 {@code blacklist}，默认空；承载控件同上 */
    public final List<String> blacklist = new ArrayList<>();

    // ━━━ 显示与警报（本项目新增，用户 2026-09-16 要求，默认全开） ━━━

    /** 命中时给危险玩家画一个描边框（新增，默认开） */
    public boolean espBox = true;

    /** 命中时从自己画一条射线到危险玩家（新增，默认开） */
    public boolean tracer = true;

    /** 命中时播放警报音效（新增，默认开） */
    public boolean alarmSound = true;

    // ━━━ 查询辅助 ━━━

    /**
     * 名字是否在名单内（忽略大小写与首尾空格）。
     *
     * <p>口径照旧项目 {@code AdminDetectorModule.containsName}（{@code :225-230}）逐字。</p>
     */
    public static boolean containsName(List<String> list, String name) {
        if (list == null || name == null) return false;
        for (String entry : list) {
            if (entry != null && entry.trim().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    // ━━━ 持久化（缺项留默认、数值按取值域 clamp） ━━━

    /** 写入 JSON */
    public void save(JsonObject json) {
        json.addProperty("detectRange", detectRange);
        json.addProperty("detectSpectator", detectSpectator);
        json.addProperty("detectCreative", detectCreative);
        json.addProperty("detectInvisible", detectInvisible);
        json.addProperty("detectHidden", detectHidden);
        json.add("whitelist", stringArray(whitelist));
        json.add("blacklist", stringArray(blacklist));
        json.addProperty("espBox", espBox);
        json.addProperty("tracer", tracer);
        json.addProperty("alarmSound", alarmSound);
    }

    /** 读取 JSON；缺项保留默认值，非法数值按取值域 clamp */
    public void load(JsonObject json) {
        if (json == null) return;
        detectRange = clampRange(intOf(json, "detectRange", detectRange));
        detectSpectator = boolOf(json, "detectSpectator", detectSpectator);
        detectCreative = boolOf(json, "detectCreative", detectCreative);
        detectInvisible = boolOf(json, "detectInvisible", detectInvisible);
        detectHidden = boolOf(json, "detectHidden", detectHidden);
        readStrings(json, "whitelist", whitelist);
        readStrings(json, "blacklist", blacklist);
        espBox = boolOf(json, "espBox", espBox);
        tracer = boolOf(json, "tracer", tracer);
        alarmSound = boolOf(json, "alarmSound", alarmSound);
    }

    /** 把检测范围夹到旧项目声明过的取值域内 */
    public static int clampRange(int value) {
        return Math.max(RANGE_MIN, Math.min(RANGE_MAX, value));
    }

    private static JsonArray stringArray(List<String> values) {
        JsonArray array = new JsonArray();
        for (String value : values) array.add(value);
        return array;
    }

    /** 读取字符串列表；键缺失时保留原值，键存在但为空数组时按空列表（= 用户清空）处理 */
    private static void readStrings(JsonObject json, String key, List<String> target) {
        if (!json.has(key) || !json.get(key).isJsonArray()) return;
        target.clear();
        for (JsonElement element : json.getAsJsonArray(key)) {
            if (element.isJsonPrimitive()) target.add(element.getAsString());
        }
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
}
