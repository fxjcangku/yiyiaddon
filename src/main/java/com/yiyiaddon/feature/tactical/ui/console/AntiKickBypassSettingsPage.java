package com.yiyiaddon.feature.tactical.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.tactical.AntiKickBypassModule;
import com.yiyiaddon.feature.tactical.config.AntiKickBypassSettings;
import com.yiyiaddon.feature.tactical.ui.AntiKickBypassConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

/**
 * 控制台设置页：旧项目原 6 个设置组（伪装客户端 / 聊天排队 / 防挂机 / 限制发包 / 拉回分析 / 模拟真人）
 * 各一个折叠块，与 {@link AntiKickBypassConsoleScreen} 的页签一一对应。
 *
 * <p><b>设置逐字</b>：名称 / 描述 / 默认值 / 取值域全部来自旧
 * {@code tactical/AntiKickBypass.java:60-244} 的设置声明，分组名沿用旧组名，不新造分类（第 183 条）。
 * 数字一律 {@link SettingNumberBox}（第 123 条禁滑块，整数步进 1）；布尔走 {@link SettingToggle}；
 * 旧设置的描述原文留在行悬停提示里（第 213 条），不改写成另一套说法。</p>
 *
 * <p><b>行级显隐照旧条件</b>：{@code 每条消息间隔（毫秒）} 仅
 * {@code 开启聊天排队}；{@code 每秒最多挖几个 / 每秒最多放几个} 各自跟随对应限速开关；
 * {@code 累积几次后分析} 仅 {@code 开启拉回分析}；{@code 抖动幅度} 仅 {@code 视角抖动}；
 * {@code 最小延迟（毫秒）/ 最大延迟（毫秒）} 仅 {@code 网络延迟}。整页可重建，条件不满足的行
 * 压根不加入堆叠（等价旧设置的 {@code visible(...)}）；改开关会触发重建，行立刻出现或消失。</p>
 *
 * <p>改动立即 {@link ModuleManager#saveSettings(com.yiyiaddon.core.module.Module)}
 * （第 172-175 条：改设置必须落盘、重启仍生效，读写键名对称）。</p>
 */
public final class AntiKickBypassSettingsPage {

    // ── 折叠块的记忆键（= 旧项目设置组名，与页签名同源） ──

    private static final String SECTION_KEY_DISGUISE = "group:伪装客户端";
    private static final String SECTION_KEY_CHAT = "group:聊天排队";
    private static final String SECTION_KEY_ANTI_AFK = "group:防挂机";
    private static final String SECTION_KEY_THROTTLE = "group:限制发包";
    private static final String SECTION_KEY_ANALYSIS = "group:拉回分析";
    private static final String SECTION_KEY_HUMAN = "group:模拟真人";

    // ── 行名（逐字 = 旧设置名） ──

    private static final String LABEL_FAKE_BRAND = "改客户端名字";
    private static final String LABEL_BLOCK_MOD_CHANNELS = "拦截 Mod 通信";
    private static final String LABEL_BLOCK_FAKE_SNEAK = "拦截假潜行";
    private static final String LABEL_BLOCK_FAKE_SPRINT = "拦截假疾跑";
    private static final String LABEL_CHAT_QUEUE = "开启聊天排队";
    private static final String LABEL_CHAT_INTERVAL = "每条消息间隔（毫秒）";
    private static final String LABEL_ANTI_AFK = "防挂机检测";
    private static final String LABEL_LIMIT_DIGGING = "限制挖掘速度";
    private static final String LABEL_MAX_DIG = "每秒最多挖几个";
    private static final String LABEL_LIMIT_INTERACT = "限制放置速度";
    private static final String LABEL_MAX_INTERACT = "每秒最多放几个";
    private static final String LABEL_ENABLE_ANALYSIS = "开启拉回分析";
    private static final String LABEL_ANALYSIS_THRESHOLD = "累积几次后分析";
    private static final String LABEL_VIEW_SHAKE = "视角抖动";
    private static final String LABEL_SHAKE_INTENSITY = "抖动幅度";
    private static final String LABEL_NETWORK_DELAY = "网络延迟";
    private static final String LABEL_MIN_DELAY = "最小延迟（毫秒）";
    private static final String LABEL_MAX_DELAY = "最大延迟（毫秒）";

    // ── 行描述（逐字 = 旧设置描述，同时作为悬停提示） ──

    private static final String DESC_FAKE_BRAND = "服务器问你用什么客户端时回答 vanilla（原版）";
    private static final String DESC_BLOCK_MOD_CHANNELS =
        "26.1.2 官方已移除模组列表握手，服务器只能靠 fabric 自建频道注册识别 litematica/tweakeroo 等模组；"
            + "本开关拦截 register/unregister 本体与全部非原版命名空间 payload 频道，让频道侦察零收获";
    private static final String DESC_BLOCK_FAKE_SNEAK = "潜行标记与移动速度矛盾时摘掉潜行标记（Tweakeroo 假潜行特征）";
    private static final String DESC_BLOCK_FAKE_SPRINT = "疾跑标记与移动方向矛盾（后退/无前进仍疾跑）时摘掉疾跑标记";
    private static final String DESC_CHAT_QUEUE = "消息由本模块排队慢发，防止刷屏被踢";
    private static final String DESC_CHAT_INTERVAL = "两条消息之间等多久";
    private static final String DESC_ANTI_AFK = "每 5 秒发一个微小转身包刷新服务端活跃时间戳";
    private static final String DESC_LIMIT_DIGGING = "挖方块太快会被踢，这个功能帮你限速";
    private static final String DESC_MAX_DIG = "原版最快 5 个/秒，调太高等于没限制";
    private static final String DESC_LIMIT_INTERACT = "放方块/右键太快会被踢，这个功能帮你限速";
    private static final String DESC_MAX_INTERACT = "原版最快 4 个/秒";
    private static final String DESC_ENABLE_ANALYSIS = "记录你每次被拉回时在做什么，累积到阈值后输出分析报告";
    private static final String DESC_ANALYSIS_THRESHOLD = "被拉回多少次后给你一份分析报告";
    private static final String DESC_VIEW_SHAKE = "移动时视角轻微抖动，模拟手抖";
    private static final String DESC_SHAKE_INTENSITY = "抖多厉害，2° 刚好，太大会被识别成机器人";
    private static final String DESC_NETWORK_DELAY =
        "发包时随机延迟 20-80 毫秒，模拟网络卡顿（移动包不延迟，避免与飞行注入互相抖动）";
    private static final String DESC_MIN_DELAY = "延迟下限";
    private static final String DESC_MAX_DELAY = "延迟上限，不要超过 100 毫秒，会卡";

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final AntiKickBypassSettings DEFAULTS = new AntiKickBypassSettings();

    private final AntiKickBypassConsoleScreen host;
    private final AntiKickBypassModule module;

    public AntiKickBypassSettingsPage(AntiKickBypassConsoleScreen host, AntiKickBypassModule module) {
        this.host = host;
        this.module = module;
    }

    // ── 组① 伪装客户端（让服务器认为你是原版玩家） ──

    public void buildDisguise(CompactStack stack) {
        AntiKickBypassSettings settings = module.settings();
        FoldSection section = new FoldSection("§f伪装客户端", SECTION_KEY_DISGUISE, host.collapsedSections());

        section.content().add(toggleRow(LABEL_FAKE_BRAND, DESC_FAKE_BRAND,
            () -> settings.fakeBrand,
            value -> settings.fakeBrand = value, () -> DEFAULTS.fakeBrand));
        section.content().add(toggleRow(LABEL_BLOCK_MOD_CHANNELS, DESC_BLOCK_MOD_CHANNELS,
            () -> settings.blockModChannels,
            value -> settings.blockModChannels = value, () -> DEFAULTS.blockModChannels));
        section.content().add(toggleRow(LABEL_BLOCK_FAKE_SNEAK, DESC_BLOCK_FAKE_SNEAK,
            () -> settings.blockFakeSneak,
            value -> settings.blockFakeSneak = value, () -> DEFAULTS.blockFakeSneak));
        section.content().add(toggleRow(LABEL_BLOCK_FAKE_SPRINT, DESC_BLOCK_FAKE_SPRINT,
            () -> settings.blockFakeSprint,
            value -> settings.blockFakeSprint = value, () -> DEFAULTS.blockFakeSprint));

        stack.add(section);
    }

    // ── 组② 聊天排队（防止发消息太快被踢） ──

    public void buildChatQueue(CompactStack stack) {
        AntiKickBypassSettings settings = module.settings();
        FoldSection section = new FoldSection("§f聊天排队", SECTION_KEY_CHAT, host.collapsedSections());

        section.content().add(toggleRow(LABEL_CHAT_QUEUE, DESC_CHAT_QUEUE,
            () -> settings.enableChatQueue,
            value -> settings.enableChatQueue = value, () -> DEFAULTS.enableChatQueue));

        // 间隔只在开启聊天排队时出现（等价旧设置声明的 visible 条件）
        if (settings.enableChatQueue) {
            section.content().add(numberRow(LABEL_CHAT_INTERVAL, DESC_CHAT_INTERVAL,
                AntiKickBypassSettings.CHAT_INTERVAL_MIN, AntiKickBypassSettings.CHAT_INTERVAL_MAX,
                () -> (double) settings.chatInterval,
                value -> settings.chatInterval = (int) Math.round(value),
                () -> (double) DEFAULTS.chatInterval));
        }

        stack.add(section);
    }

    // ── 组③ 防挂机（假装你在玩游戏） ──

    public void buildAntiAfk(CompactStack stack) {
        AntiKickBypassSettings settings = module.settings();
        FoldSection section = new FoldSection("§f防挂机", SECTION_KEY_ANTI_AFK, host.collapsedSections());

        section.content().add(toggleRow(LABEL_ANTI_AFK, DESC_ANTI_AFK,
            () -> settings.antiAfk,
            value -> settings.antiAfk = value, () -> DEFAULTS.antiAfk));

        stack.add(section);
    }

    // ── 组④ 限制发包（防止挖太快/放太快被踢） ──

    public void buildThrottle(CompactStack stack) {
        AntiKickBypassSettings settings = module.settings();
        FoldSection section = new FoldSection("§f限制发包", SECTION_KEY_THROTTLE, host.collapsedSections());

        section.content().add(toggleRow(LABEL_LIMIT_DIGGING, DESC_LIMIT_DIGGING,
            () -> settings.limitDigging,
            value -> settings.limitDigging = value, () -> DEFAULTS.limitDigging));
        // 挖掘阈值只在限制挖掘速度开启时出现（等价旧设置声明的 visible 条件）
        if (settings.limitDigging) {
            section.content().add(numberRow(LABEL_MAX_DIG, DESC_MAX_DIG,
                AntiKickBypassSettings.MAX_DIG_MIN, AntiKickBypassSettings.MAX_DIG_MAX,
                () -> (double) settings.maxDigPerSecond,
                value -> settings.maxDigPerSecond = (int) Math.round(value),
                () -> (double) DEFAULTS.maxDigPerSecond));
        }

        section.content().add(toggleRow(LABEL_LIMIT_INTERACT, DESC_LIMIT_INTERACT,
            () -> settings.limitInteract,
            value -> settings.limitInteract = value, () -> DEFAULTS.limitInteract));
        if (settings.limitInteract) {
            section.content().add(numberRow(LABEL_MAX_INTERACT, DESC_MAX_INTERACT,
                AntiKickBypassSettings.MAX_INTERACT_MIN, AntiKickBypassSettings.MAX_INTERACT_MAX,
                () -> (double) settings.maxInteractPerSecond,
                value -> settings.maxInteractPerSecond = (int) Math.round(value),
                () -> (double) DEFAULTS.maxInteractPerSecond));
        }

        stack.add(section);
    }

    // ── 组⑤ 拉回分析（记录什么操作容易被拉回） ──

    public void buildAnalysis(CompactStack stack) {
        AntiKickBypassSettings settings = module.settings();
        FoldSection section = new FoldSection("§f拉回分析", SECTION_KEY_ANALYSIS, host.collapsedSections());

        section.content().add(toggleRow(LABEL_ENABLE_ANALYSIS, DESC_ENABLE_ANALYSIS,
            () -> settings.enableAnalysis,
            value -> settings.enableAnalysis = value, () -> DEFAULTS.enableAnalysis));

        if (settings.enableAnalysis) {
            section.content().add(numberRow(LABEL_ANALYSIS_THRESHOLD, DESC_ANALYSIS_THRESHOLD,
                AntiKickBypassSettings.ANALYSIS_THRESHOLD_MIN, AntiKickBypassSettings.ANALYSIS_THRESHOLD_MAX,
                () -> (double) settings.analysisThreshold,
                value -> settings.analysisThreshold = (int) Math.round(value),
                () -> (double) DEFAULTS.analysisThreshold));
        }

        stack.add(section);
    }

    // ── 组⑥ 模拟真人（让你的操作看起来像真人） ──

    public void buildHuman(CompactStack stack) {
        AntiKickBypassSettings settings = module.settings();
        FoldSection section = new FoldSection("§f模拟真人", SECTION_KEY_HUMAN, host.collapsedSections());

        section.content().add(toggleRow(LABEL_VIEW_SHAKE, DESC_VIEW_SHAKE,
            () -> settings.enableViewShake,
            value -> settings.enableViewShake = value, () -> DEFAULTS.enableViewShake));
        // 抖动幅度只在视角抖动开启时出现（等价旧设置声明的 visible 条件）
        if (settings.enableViewShake) {
            section.content().add(decimalRow(LABEL_SHAKE_INTENSITY, DESC_SHAKE_INTENSITY,
                AntiKickBypassSettings.SHAKE_INTENSITY_MIN, AntiKickBypassSettings.SHAKE_INTENSITY_MAX,
                () -> settings.shakeIntensity,
                value -> settings.shakeIntensity = value,
                () -> DEFAULTS.shakeIntensity));
        }

        section.content().add(toggleRow(LABEL_NETWORK_DELAY, DESC_NETWORK_DELAY,
            () -> settings.enableNetworkDelay,
            value -> settings.enableNetworkDelay = value, () -> DEFAULTS.enableNetworkDelay));
        // 延迟上下限只在网络延迟开启时出现（等价旧设置声明的 visible 条件）
        if (settings.enableNetworkDelay) {
            section.content().add(numberRow(LABEL_MIN_DELAY, DESC_MIN_DELAY,
                AntiKickBypassSettings.MIN_DELAY_MIN, AntiKickBypassSettings.MIN_DELAY_MAX,
                () -> (double) settings.minDelay,
                value -> settings.minDelay = (int) Math.round(value),
                () -> (double) DEFAULTS.minDelay));
            section.content().add(numberRow(LABEL_MAX_DELAY, DESC_MAX_DELAY,
                AntiKickBypassSettings.MAX_DELAY_MIN, AntiKickBypassSettings.MAX_DELAY_MAX,
                () -> (double) settings.maxDelay,
                value -> settings.maxDelay = (int) Math.round(value),
                () -> (double) DEFAULTS.maxDelay));
        }

        stack.add(section);
    }

    // ── 行构件组装 ──

    /** 整数数值行：步进 1（旧项目 6 项数字设置均为 noSlider），改动即写盘；旧描述同时作为悬停提示 */
    private ConsoleRow numberRow(String label, String desc, int min, int max,
                                 Supplier<Double> getter, DoubleConsumer setter,
                                 Supplier<Double> defaultValue) {
        SettingNumberBox box = new SettingNumberBox(min, max, 1, "%.0f", getter, value -> {
            setter.accept(value);
            persist();
        });
        return new ConsoleRow(host, () -> label, desc, null,
            List.of(new Ctl(box, desc),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    persist();
                    host.reload();
                }, label)));
    }

    /** 小数值行：步进 0.1、格式一位小数（旧 {@code 抖动幅度} 是 DoubleSetting + noSlider） */
    private ConsoleRow decimalRow(String label, String desc, double min, double max,
                                  Supplier<Double> getter, DoubleConsumer setter,
                                  Supplier<Double> defaultValue) {
        SettingNumberBox box = new SettingNumberBox(min, max, 0.1, "%.1f", getter, value -> {
            setter.accept(value);
            persist();
        });
        return new ConsoleRow(host, () -> label, desc, null,
            List.of(new Ctl(box, desc),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    persist();
                    host.reload();
                }, label)));
    }

    /** 开关行：改动即写盘；写盘后整页重建，跟随本开关显隐的行立刻出现或消失 */
    private ConsoleRow toggleRow(String label, String desc,
                                 Supplier<Boolean> getter, Consumer<Boolean> setter,
                                 Supplier<Boolean> defaultValue) {
        SettingToggle toggle = new SettingToggle(getter, value -> {
            setter.accept(value);
            persist();
            host.reload();
        });
        return new ConsoleRow(host, () -> label, desc, null,
            List.of(new Ctl(toggle, desc),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    persist();
                    host.reload();
                }, label)));
    }

    /** 改设置即落盘（第 172-175 条） */
    private void persist() {
        ModuleManager.saveSettings(module);
    }
}
