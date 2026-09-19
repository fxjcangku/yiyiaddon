package com.yiyiaddon.feature.librarian.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.ui.keybind.AddonKeybind;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动图书管理员的设置载体：12 项设置 / 5 组（目标 / 行为 / 通知 / 调试 / 快捷键）。
 *
 * <p><b>逐字资产</b>：设置名、默认值、描述、分组全部来自旧项目
 * {@code AutoLibrarianSettings}（旧 {@code :19-143}），落盘键名直接用设置名本身
 * （与自动村民交易 / 自动农场的做法一致），改一个键名都会让老档读不出来。</p>
 *
 * <p><b>旧项目只有 {@code min} 没有 {@code max} 的项</b>：村民搜索半径 / 最高绿宝石价格 /
 * 职业等待超时 / 动作延迟 / 刷新延迟五项在旧项目里只声明了下界。本项目控制台的数字控件
 * 必须给上界（第 123 条禁止滑块，数值框要域），故按「原版可达到的最大值」收口：
 * 价格上限取 64（大师级图书管理员附魔书成交价上限）、搜索半径 256、职业等待 1200 tick、
 * 动作延迟 20 tick、刷新延迟 200 tick。这是界面域的收口，不改默认值，也不改变
 * 旧项目实际可用的取值区间（登记 D-15-10）。</p>
 *
 * <p><b>四项不受设置项控制的值</b>（到达半径 3 / 交易界面超时 100 / 交易同步超时 100 /
 * 移动超时 1200）在 {@link LibrarianConfig#defaults()} 里硬编码，本载体不提供字段，
 * 与旧项目「只有 {@code AutoLibrarianConfig} 里有」的结构完全一致。</p>
 */
public final class LibrarianSettings {

    /** 村民搜索半径取值域（旧项目仅声明 min 1） */
    public static final int RADIUS_MIN = 1;
    public static final int RADIUS_MAX = 256;
    /** 最高绿宝石价格取值域（旧项目仅声明 min 1；原版成交价上限 64） */
    public static final int PRICE_MIN = 1;
    public static final int PRICE_MAX = 64;
    /** 职业等待超时取值域（旧项目仅声明 min 20） */
    public static final int PROFESSION_TIMEOUT_MIN = 20;
    public static final int PROFESSION_TIMEOUT_MAX = 1200;
    /** 动作延迟取值域（旧项目仅声明 min 1） */
    public static final int ACTION_DELAY_MIN = 1;
    public static final int ACTION_DELAY_MAX = 20;
    /** 刷新延迟取值域（旧项目仅声明 min 1） */
    public static final int RESET_DELAY_MIN = 1;
    public static final int RESET_DELAY_MAX = 200;
    /** 目标附魔默认值：旧 {@code Enchantments.MENDING} */
    public static final String DEFAULT_ENCHANTMENT = "minecraft:mending";

    /** 落盘键名（逐字 = 旧设置名） */
    private static final String KEY_TARGETS = "目标附魔";
    private static final String KEY_RADIUS = "村民搜索半径";
    private static final String KEY_PRICE = "最高绿宝石价格";
    private static final String KEY_PROFESSION_TIMEOUT = "职业等待超时";
    private static final String KEY_ACTION_DELAY = "动作延迟";
    private static final String KEY_RESET_DELAY = "刷新延迟";
    private static final String KEY_REMOVE_TARGET = "找到后移除目标";
    private static final String KEY_NOTIFICATION_SOUND = "提示音";
    private static final String KEY_SUCCESS_SOUND = "附魔成功音效";
    private static final String KEY_DEBUG_MODE = "调试模式";
    private static final String KEY_CHAT_FEEDBACK = "聊天反馈";
    private static final String KEY_PAUSE_KEYBIND = "暂停快捷键";

    // ── 目标组 ──

    /** 目标附魔（注册表 ID 列表，默认 {@code minecraft:mending}） */
    private final List<String> targetEnchantments = new ArrayList<>(List.of(DEFAULT_ENCHANTMENT));

    // ── 行为组 ──

    /** 村民搜索半径（默认 32） */
    public int searchRadius = 32;
    /** 最高绿宝石价格（默认 64） */
    public int maximumEmeraldPrice = 64;
    /** 职业等待超时（默认 200 tick） */
    public int professionTimeout = 200;
    /** 动作延迟（默认 2 tick） */
    public int actionDelay = 2;
    /** 刷新延迟（默认 10 tick） */
    public int resetDelay = 10;
    /** 找到后移除目标（默认 false） */
    public boolean removeTargetOnFound = false;

    // ── 通知组 ──

    /** 提示音（默认 true） */
    public boolean playNotificationSound = true;
    /** 附魔成功音效（默认 成就完成；仅「提示音」为真时可见） */
    public SuccessSound successSound = SuccessSound.CHALLENGE_COMPLETE;

    // ── 调试组 ──

    /** 调试模式（默认 false） */
    public boolean debugMode = false;
    /** 聊天反馈（默认 true） */
    public boolean chatFeedback = true;

    // ── 快捷键组 ──

    /**
     * 暂停快捷键（旧 {@code KeybindSetting}，默认 none → 界面显示 {@code None}）。
     *
     * <p>承载方式与自动村民交易的「快速停止键」同源（第 169 条）：自研键位控件
     * {@link AddonKeybind} + 控制台 {@code SettingKeybind} 行 + 落盘在本设置对象里。
     * 实际行为见 {@code AutoLibrarianModule#onTick}（D2 拍板：照旧项目的实现，按下即强制结束）。</p>
     */
    public AddonKeybind pauseKey = AddonKeybind.none();

    /** 目标附魔 ID 列表（返回活引用，供选择器写入） */
    public List<String> targetEnchantments() {
        return targetEnchantments;
    }

    /** 覆盖目标附魔选择（选择器加减一个候选时调用） */
    public void setTargetEnchantments(List<String> ids) {
        targetEnchantments.clear();
        targetEnchantments.addAll(ids);
    }

    // ── 编解码（键名与旧设置名逐字对应） ──

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回取值域。 */
    public void load(JsonObject json) {
        if (json == null) return;

        if (json.has(KEY_TARGETS)) {
            List<String> ids = stringsOf(json.get(KEY_TARGETS));
            targetEnchantments.clear();
            targetEnchantments.addAll(ids);
        }
        searchRadius = clamp(intOf(json, KEY_RADIUS, searchRadius), RADIUS_MIN, RADIUS_MAX);
        maximumEmeraldPrice = clamp(intOf(json, KEY_PRICE, maximumEmeraldPrice), PRICE_MIN, PRICE_MAX);
        professionTimeout = clamp(intOf(json, KEY_PROFESSION_TIMEOUT, professionTimeout),
            PROFESSION_TIMEOUT_MIN, PROFESSION_TIMEOUT_MAX);
        actionDelay = clamp(intOf(json, KEY_ACTION_DELAY, actionDelay), ACTION_DELAY_MIN, ACTION_DELAY_MAX);
        resetDelay = clamp(intOf(json, KEY_RESET_DELAY, resetDelay), RESET_DELAY_MIN, RESET_DELAY_MAX);
        removeTargetOnFound = boolOf(json, KEY_REMOVE_TARGET, removeTargetOnFound);
        playNotificationSound = boolOf(json, KEY_NOTIFICATION_SOUND, playNotificationSound);
        successSound = soundOf(json, KEY_SUCCESS_SOUND, successSound);
        debugMode = boolOf(json, KEY_DEBUG_MODE, debugMode);
        chatFeedback = boolOf(json, KEY_CHAT_FEEDBACK, chatFeedback);
        pauseKey = AddonKeybind.fromJson(json.get(KEY_PAUSE_KEYBIND), pauseKey);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键）。 */
    public void save(JsonObject json) {
        json.add(KEY_TARGETS, stringsToJson(targetEnchantments));
        json.addProperty(KEY_RADIUS, searchRadius);
        json.addProperty(KEY_PRICE, maximumEmeraldPrice);
        json.addProperty(KEY_PROFESSION_TIMEOUT, professionTimeout);
        json.addProperty(KEY_ACTION_DELAY, actionDelay);
        json.addProperty(KEY_RESET_DELAY, resetDelay);
        json.addProperty(KEY_REMOVE_TARGET, removeTargetOnFound);
        json.addProperty(KEY_NOTIFICATION_SOUND, playNotificationSound);
        json.addProperty(KEY_SUCCESS_SOUND, successSound.name());
        json.addProperty(KEY_DEBUG_MODE, debugMode);
        json.addProperty(KEY_CHAT_FEEDBACK, chatFeedback);
        json.add(KEY_PAUSE_KEYBIND, pauseKey.toJson());
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

    /** 音效枚举名解析：非法值回落默认（与旧框架设置反序列化同口径） */
    private static SuccessSound soundOf(JsonObject json, String key, SuccessSound fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive()) return fallback;
        try {
            return SuccessSound.valueOf(element.getAsString());
        } catch (IllegalArgumentException ignored) {
            return fallback;
        }
    }

    private static List<String> stringsOf(JsonElement element) {
        List<String> out = new ArrayList<>();
        if (element == null || !element.isJsonArray()) return out;
        for (JsonElement item : element.getAsJsonArray()) {
            if (item.isJsonPrimitive()) out.add(item.getAsString());
        }
        return out;
    }

    private static JsonArray stringsToJson(List<String> values) {
        JsonArray array = new JsonArray();
        for (String value : values) array.add(value);
        return array;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
