package com.yiyiaddon.feature.reconnect.config;

import com.google.gson.JsonObject;

/**
 * 「自动重连」设置载体：6 项落盘设置，全部落在同一个分组（{@link ReconnectTexts#GROUP_SETTINGS}）。
 *
 * <p><b>键名 = 中文设置名</b>（开发习惯第二十五章第 172-175 条）：读写用同一个常量，
 * 改名即换键、老档读不出来，因此四个与自动登入同名的字段沿用自动登入的同一套设置名，
 * 两个模块的设置含义、默认值、取值范围完全一致（玩家不必学两套口径）。</p>
 *
 * <p><b>两项是本模块特有的</b>：{@code 连接稳定判定（tick）} 把自动登入里写死的稳定阈值
 * （旧 {@code RECONNECT_STABLE_TICKS = 200}）变成可调项；{@code 回主菜单时取消重连}
 * 把自动登入里固定打开的行为（等待期间走主菜单即取消）变成可关的开关。两者都只影响本模块，
 * 自动登入的旧行为一字未动。</p>
 *
 * <p><b>不做设置隔离</b>：重连设置与具体服务器无关，{@code settingsScope()} 保持 {@code null}。</p>
 */
public final class ReconnectSettings {

    // ── 取值域 ──

    /** 等待时间下界：沿用自动登入「重连等待（tick）」的 20（1 秒），防手滑设成 0 秒连击 */
    public static final int DELAY_MIN = 20;
    /** 次数下界 */
    public static final int MAX_ATTEMPTS_MIN = 1;
    /** 稳定判定下界：20 tick（1 秒） */
    public static final int STABLE_TICKS_MIN = 20;
    /** 未给上界的一组（沿用自动登入同类项的口径：上界放开） */
    public static final int VALUE_MAX = Integer.MAX_VALUE;

    // ── 字段（顺序 = 控制台设置页的显示顺序） ──

    /** 总开关：断线后是否自动连回上一次的服务器（默认开） */
    public boolean enabled = true;
    /** 断线后等待多少 tick 再重连（默认 100 = 5 秒） */
    public int delayTicks = 100;
    /** 无限重连：忽略次数上限（默认关） */
    public boolean unlimited;
    /** 连续失败上限（默认 5） */
    public int maxAttempts = 5;
    /** 连上后多少 tick 视为稳定、失败计数归零（默认 200 = 10 秒） */
    public int stableTicks = 200;
    /** 等待重连期间手动回到服务器列表 / 主菜单时取消本次重连（默认开） */
    public boolean cancelOnMenu = true;

    /** 从模块设置对象载入（字段缺失或类型不符时保留默认值） */
    public void load(JsonObject json) {
        if (json == null) return;
        enabled = boolOf(json, ReconnectTexts.NAME_ENABLED, enabled);
        delayTicks = clamp(intOf(json, ReconnectTexts.NAME_DELAY, delayTicks), DELAY_MIN, VALUE_MAX);
        unlimited = boolOf(json, ReconnectTexts.NAME_UNLIMITED, unlimited);
        maxAttempts = clamp(intOf(json, ReconnectTexts.NAME_MAX_ATTEMPTS, maxAttempts),
            MAX_ATTEMPTS_MIN, VALUE_MAX);
        stableTicks = clamp(intOf(json, ReconnectTexts.NAME_STABLE_TICKS, stableTicks),
            STABLE_TICKS_MIN, VALUE_MAX);
        cancelOnMenu = boolOf(json, ReconnectTexts.NAME_CANCEL_ON_MENU, cancelOnMenu);
    }

    /** 写入模块设置对象 */
    public void save(JsonObject json) {
        if (json == null) return;
        json.addProperty(ReconnectTexts.NAME_ENABLED, enabled);
        json.addProperty(ReconnectTexts.NAME_DELAY, delayTicks);
        json.addProperty(ReconnectTexts.NAME_UNLIMITED, unlimited);
        json.addProperty(ReconnectTexts.NAME_MAX_ATTEMPTS, maxAttempts);
        json.addProperty(ReconnectTexts.NAME_STABLE_TICKS, stableTicks);
        json.addProperty(ReconnectTexts.NAME_CANCEL_ON_MENU, cancelOnMenu);
    }

    // ── 读取工具（读不出来就用旧值，不抛异常、不写盘） ──

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsBoolean() : fallback;
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive()) return fallback;
        try {
            return json.get(key).getAsInt();
        } catch (RuntimeException error) {
            return fallback;
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
