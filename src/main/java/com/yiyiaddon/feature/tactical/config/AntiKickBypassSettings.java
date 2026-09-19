package com.yiyiaddon.feature.tactical.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 发包防踢设置载体：18 项 / 6 组（伪装客户端 / 聊天排队 / 防挂机 / 限制发包 / 拉回分析 / 模拟真人）。
 *
 * <p><b>逐字资产</b>：设置名、默认值、取值域、可见性条件、分组名全部来自旧项目
 * {@code tactical/AntiKickBypass.java:60-244}；落盘键名直接用旧设置名本身
 * （与飞行绕过 / 服务器检测 / 传送的做法一致），改一个键名都会让老档读不出来（第 172-175 条）。</p>
 *
 * <p><b>组序号跳过 sg5</b>：旧项目设置组编号为 sg1~sg4、sg6、sg7（没有 sg5），
 * 这里按旧项目的<b>声明顺序</b>落成 6 组，分组名逐字不变，不补也不删。</p>
 *
 * <p><b>数值设置的控件形态</b>：旧项目 6 项数字设置均为 {@code IntSetting + noSlider()}、
 * 1 项为 {@code DoubleSetting + noSlider()}；本项目控制台一律 {@code SettingNumberBox}
 * （第 123 条禁滑块）——整数步进 1，比例型步进 0.1、格式 {@code %.1f}。</p>
 *
 * <p><b>可见性条件不在本类实现</b>：旧设置的 {@code visible(...)} 由控制台的设置页按
 * 「条件不满足就不把该行加入堆叠」的方式等价实现（整页可重建，改开关立刻增删行）。</p>
 *
 * <p><b>volatile 的范围（并发口径）</b>：发包规则在网络线程被同步调用，会读本类里的开关与阈值，
 * 而它们由主线程（设置页点击 / 通道绑定读取）写入。因此<b>规则会读的 12 个字段</b>一律声明
 * {@code volatile}（伪装四个开关、聊天排队开关、限速两个开关与两个阈值、网络延迟开关与上下限），
 * 保证网络线程读到的是最新值；只由主线程读写的字段（防挂机、间隔、拉回分析、视角抖动）保持普通字段，
 * 不引入无谓的 volatile 写。动态量（限速配额 / 拉回倍率 / 玩家侧快照）由模块各自的原子量与
 * volatile 快照承担，不在本类。</p>
 */
public final class AntiKickBypassSettings {

    // ── 取值域（逐字 = 旧设置声明） ──

    /** 每条消息间隔域（旧 {@code IntSetting} 1000~3000 毫秒） */
    public static final int CHAT_INTERVAL_MIN = 1000;
    public static final int CHAT_INTERVAL_MAX = 3000;
    /** 每秒最多挖几个域（旧 {@code IntSetting} 2~20） */
    public static final int MAX_DIG_MIN = 2;
    public static final int MAX_DIG_MAX = 20;
    /** 每秒最多放几个域（旧 {@code IntSetting} 2~20） */
    public static final int MAX_INTERACT_MIN = 2;
    public static final int MAX_INTERACT_MAX = 20;
    /** 累积几次后分析域（旧 {@code IntSetting} 5~50） */
    public static final int ANALYSIS_THRESHOLD_MIN = 5;
    public static final int ANALYSIS_THRESHOLD_MAX = 50;
    /** 抖动幅度域（旧 {@code DoubleSetting} 0.5~5.0） */
    public static final double SHAKE_INTENSITY_MIN = 0.5;
    public static final double SHAKE_INTENSITY_MAX = 5.0;
    /** 最小延迟域（旧 {@code IntSetting} 0~100 毫秒） */
    public static final int MIN_DELAY_MIN = 0;
    public static final int MIN_DELAY_MAX = 100;
    /** 最大延迟域（旧 {@code IntSetting} 0~200 毫秒） */
    public static final int MAX_DELAY_MIN = 0;
    public static final int MAX_DELAY_MAX = 200;

    // ── 落盘键名（逐字 = 旧设置名） ──

    // 组① 伪装客户端
    private static final String KEY_FAKE_BRAND = "改客户端名字";
    private static final String KEY_BLOCK_MOD_CHANNELS = "拦截 Mod 通信";
    private static final String KEY_BLOCK_FAKE_SNEAK = "拦截假潜行";
    private static final String KEY_BLOCK_FAKE_SPRINT = "拦截假疾跑";
    // 组② 聊天排队
    private static final String KEY_CHAT_QUEUE = "开启聊天排队";
    private static final String KEY_CHAT_INTERVAL = "每条消息间隔（毫秒）";
    // 组③ 防挂机
    private static final String KEY_ANTI_AFK = "防挂机检测";
    // 组④ 限制发包
    private static final String KEY_LIMIT_DIGGING = "限制挖掘速度";
    private static final String KEY_MAX_DIG = "每秒最多挖几个";
    private static final String KEY_LIMIT_INTERACT = "限制放置速度";
    private static final String KEY_MAX_INTERACT = "每秒最多放几个";
    // 组⑤ 拉回分析
    private static final String KEY_ENABLE_ANALYSIS = "开启拉回分析";
    private static final String KEY_ANALYSIS_THRESHOLD = "累积几次后分析";
    // 组⑥ 模拟真人
    private static final String KEY_VIEW_SHAKE = "视角抖动";
    private static final String KEY_SHAKE_INTENSITY = "抖动幅度";
    private static final String KEY_NETWORK_DELAY = "网络延迟";
    private static final String KEY_MIN_DELAY = "最小延迟（毫秒）";
    private static final String KEY_MAX_DELAY = "最大延迟（毫秒）";

    // ── 组① 伪装客户端（让服务器认为你是原版玩家） ──

    /** 服务器问你用什么客户端时回答 vanilla（默认开；网络线程只读，故 volatile） */
    public volatile boolean fakeBrand = true;
    /** 拦截 register/unregister 本体与全部非原版命名空间 payload 频道（默认开；网络线程只读，故 volatile） */
    public volatile boolean blockModChannels = true;
    /** 潜行标记与移动速度矛盾时摘掉潜行标记（默认开；网络线程只读，故 volatile） */
    public volatile boolean blockFakeSneak = true;
    /** 疾跑标记与移动方向矛盾时摘掉疾跑标记（默认开；网络线程只读，故 volatile） */
    public volatile boolean blockFakeSprint = true;

    // ── 组② 聊天排队（防止发消息太快被踢） ──

    /** 消息由本模块排队慢发（默认开；网络线程只读，故 volatile） */
    public volatile boolean enableChatQueue = true;
    /** 两条消息之间等多久（默认 1500 毫秒；仅聊天排队开启时可见。仅主线程读写，故非 volatile） */
    public int chatInterval = 1500;

    // ── 组③ 防挂机（假装你在玩游戏） ──

    /** 每 5 秒发一个微小转身包刷新服务端活跃时间戳（默认开；仅主线程读写，故非 volatile） */
    public boolean antiAfk = true;

    // ── 组④ 限制发包（防止挖太快/放太快被踢） ──

    /** 挖方块限速（默认开；网络线程只读，故 volatile） */
    public volatile boolean limitDigging = true;
    /** 每秒最多挖几个（默认 8；仅限制挖掘速度开启时可见。网络线程只读，故 volatile） */
    public volatile int maxDigPerSecond = 8;
    /** 放置限速（默认开；网络线程只读，故 volatile） */
    public volatile boolean limitInteract = true;
    /** 每秒最多放几个（默认 8；仅限制放置速度开启时可见。网络线程只读，故 volatile） */
    public volatile int maxInteractPerSecond = 8;

    // ── 组⑤ 拉回分析（记录什么操作容易被拉回） ──

    /** 记录每次被拉回时在做什么（默认开；仅主线程读写，故非 volatile） */
    public boolean enableAnalysis = true;
    /** 被拉回多少次后输出报告（默认 10；仅拉回分析开启时可见。仅主线程读写，故非 volatile） */
    public int analysisThreshold = 10;

    // ── 组⑥ 模拟真人（让你的操作看起来像真人） ──

    /** 移动时视角轻微抖动（默认关，用户 2026-09-18：「把发包防踢『视角抖动』的默认值改成关」；仅主线程读写，故非 volatile） */
    public boolean enableViewShake = false;
    /** 抖多厉害，2° 刚好（默认 2.0；仅视角抖动开启时可见。仅主线程读写，故非 volatile） */
    public double shakeIntensity = 2.0;
    /** 发包时随机延迟（默认关；网络线程只读，故 volatile） */
    public volatile boolean enableNetworkDelay = false;
    /** 延迟下限（默认 20；仅网络延迟开启时可见。网络线程只读，故 volatile） */
    public volatile int minDelay = 20;
    /** 延迟上限（默认 80；仅网络延迟开启时可见。网络线程只读，故 volatile） */
    public volatile int maxDelay = 80;

    // ── 编解码（键名与旧设置名逐字对应） ──

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回取值域 */
    public void load(JsonObject json) {
        if (json == null) return;

        fakeBrand = boolOf(json, KEY_FAKE_BRAND, fakeBrand);
        blockModChannels = boolOf(json, KEY_BLOCK_MOD_CHANNELS, blockModChannels);
        blockFakeSneak = boolOf(json, KEY_BLOCK_FAKE_SNEAK, blockFakeSneak);
        blockFakeSprint = boolOf(json, KEY_BLOCK_FAKE_SPRINT, blockFakeSprint);

        enableChatQueue = boolOf(json, KEY_CHAT_QUEUE, enableChatQueue);
        chatInterval = clamp(intOf(json, KEY_CHAT_INTERVAL, chatInterval),
            CHAT_INTERVAL_MIN, CHAT_INTERVAL_MAX);

        antiAfk = boolOf(json, KEY_ANTI_AFK, antiAfk);

        limitDigging = boolOf(json, KEY_LIMIT_DIGGING, limitDigging);
        maxDigPerSecond = clamp(intOf(json, KEY_MAX_DIG, maxDigPerSecond), MAX_DIG_MIN, MAX_DIG_MAX);
        limitInteract = boolOf(json, KEY_LIMIT_INTERACT, limitInteract);
        maxInteractPerSecond = clamp(intOf(json, KEY_MAX_INTERACT, maxInteractPerSecond),
            MAX_INTERACT_MIN, MAX_INTERACT_MAX);

        enableAnalysis = boolOf(json, KEY_ENABLE_ANALYSIS, enableAnalysis);
        analysisThreshold = clamp(intOf(json, KEY_ANALYSIS_THRESHOLD, analysisThreshold),
            ANALYSIS_THRESHOLD_MIN, ANALYSIS_THRESHOLD_MAX);

        enableViewShake = boolOf(json, KEY_VIEW_SHAKE, enableViewShake);
        shakeIntensity = clamp(doubleOf(json, KEY_SHAKE_INTENSITY, shakeIntensity),
            SHAKE_INTENSITY_MIN, SHAKE_INTENSITY_MAX);
        enableNetworkDelay = boolOf(json, KEY_NETWORK_DELAY, enableNetworkDelay);
        minDelay = clamp(intOf(json, KEY_MIN_DELAY, minDelay), MIN_DELAY_MIN, MIN_DELAY_MAX);
        maxDelay = clamp(intOf(json, KEY_MAX_DELAY, maxDelay), MAX_DELAY_MIN, MAX_DELAY_MAX);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键） */
    public void save(JsonObject json) {
        json.addProperty(KEY_FAKE_BRAND, fakeBrand);
        json.addProperty(KEY_BLOCK_MOD_CHANNELS, blockModChannels);
        json.addProperty(KEY_BLOCK_FAKE_SNEAK, blockFakeSneak);
        json.addProperty(KEY_BLOCK_FAKE_SPRINT, blockFakeSprint);

        json.addProperty(KEY_CHAT_QUEUE, enableChatQueue);
        json.addProperty(KEY_CHAT_INTERVAL, chatInterval);

        json.addProperty(KEY_ANTI_AFK, antiAfk);

        json.addProperty(KEY_LIMIT_DIGGING, limitDigging);
        json.addProperty(KEY_MAX_DIG, maxDigPerSecond);
        json.addProperty(KEY_LIMIT_INTERACT, limitInteract);
        json.addProperty(KEY_MAX_INTERACT, maxInteractPerSecond);

        json.addProperty(KEY_ENABLE_ANALYSIS, enableAnalysis);
        json.addProperty(KEY_ANALYSIS_THRESHOLD, analysisThreshold);

        json.addProperty(KEY_VIEW_SHAKE, enableViewShake);
        json.addProperty(KEY_SHAKE_INTENSITY, shakeIntensity);
        json.addProperty(KEY_NETWORK_DELAY, enableNetworkDelay);
        json.addProperty(KEY_MIN_DELAY, minDelay);
        json.addProperty(KEY_MAX_DELAY, maxDelay);
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
