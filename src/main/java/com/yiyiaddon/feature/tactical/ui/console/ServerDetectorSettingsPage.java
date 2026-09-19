package com.yiyiaddon.feature.tactical.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.tactical.ServerDetectorModule;
import com.yiyiaddon.feature.tactical.ServerDetectorModule.ResourcePackMode;
import com.yiyiaddon.feature.tactical.config.ServerDetectorSettings;
import com.yiyiaddon.feature.tactical.ui.ServerDetectorConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

/**
 * 控制台设置页：旧项目原 2 个设置组（底裤侦测 / 资源包劫持）各一个折叠块。
 *
 * <p><b>设置逐字</b>：名称 / 描述 / 默认值 / 取值域全部来自旧 {@code tactical/ServerDetector.java:59-128}
 * 的设置声明，分组名沿用旧组名，不新造分类（第 183 条）。数字一律 {@link SettingNumberBox}
 * （第 123 条禁滑块，整数步进 1）；枚举走 {@link SettingSegmented}；布尔走 {@link SettingToggle}。
 * 旧设置的描述原文留在行悬停提示里（第 213 条），不改写成另一套说法。</p>
 *
 * <p><b>行级显隐照旧条件</b>：{@code 重试次数 / 读取超时（秒）/ 断点续传} 仅在
 * {@code 资源包模式 = 自动白嫖} 时出现——整页可重建，条件不满足的行压根不加入堆叠
 * （等价旧设置的 {@code visible(...)}）；改模式会触发重建，三行立刻出现或消失。</p>
 *
 * <p>改动立即 {@link ModuleManager#saveSettings(com.yiyiaddon.core.module.Module)}
 * （第 172-175 条：改设置必须落盘、重启仍生效，读写键名对称）。</p>
 */
public final class ServerDetectorSettingsPage {

    private static final String SECTION_KEY_DETECTION = "group:底裤侦测";
    private static final String SECTION_KEY_RESOURCE_PACK = "group:资源包劫持";

    // ── 行名（逐字 = 旧设置名） ──

    private static final String LABEL_DETECT_CORE = "检测服务器核心";
    private static final String LABEL_DETECT_ANTICHEAT = "检测反作弊";
    private static final String LABEL_ANNOUNCE = "公屏播报";
    private static final String LABEL_DETECT_DELAY = "侦测延迟（秒）";
    private static final String LABEL_RESOURCE_PACK_MODE = "资源包模式";
    private static final String LABEL_DOWNLOAD_RETRIES = "重试次数";
    private static final String LABEL_DOWNLOAD_TIMEOUT = "读取超时（秒）";
    private static final String LABEL_RESUME_DOWNLOAD = "断点续传";

    // ── 行描述（逐字 = 旧设置描述，悬停提示） ──

    private static final String DESC_DETECT_CORE =
        "识别 Paper / Purpur / Leaves / Folia / 混合端 / 代理层等三十余种核心";
    private static final String DESC_DETECT_ANTICHEAT =
        "通过指令树与插件频道识别反作弊，覆盖国际主流与国内常见实现";
    private static final String DESC_ANNOUNCE = "侦测完成后在聊天栏输出结果";
    private static final String DESC_DETECT_DELAY = "进服后等待多久开始侦测。指令树需要服务端下发完成，太早会漏判";
    private static final String DESC_RESOURCE_PACK_MODE = "选择如何处理服务器资源包";
    private static final String DESC_DOWNLOAD_RETRIES = "下载失败后的重试次数，每次重试都会尝试断点续传";
    private static final String DESC_DOWNLOAD_TIMEOUT = "大资源包在慢速服务器上很容易超时，调大可显著提升成功率";
    private static final String DESC_RESUME_DOWNLOAD = "重试时用 Range 请求接着传，避免大包每次从零开始";

    /** 枚举候选：顺序即枚举序，文案逐字取自 {@link ResourcePackMode} 的中文显示名 */
    private static final List<String> MODE_LABELS = Arrays.stream(ResourcePackMode.values())
        .map(mode -> mode.displayName)
        .toList();

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final ServerDetectorSettings DEFAULTS = new ServerDetectorSettings();

    private final ServerDetectorConsoleScreen host;
    private final ServerDetectorModule module;

    public ServerDetectorSettingsPage(ServerDetectorConsoleScreen host, ServerDetectorModule module) {
        this.host = host;
        this.module = module;
    }

    // ── 组① 底裤侦测 ──

    public void buildDetection(CompactStack stack) {
        ServerDetectorSettings settings = module.settings();
        FoldSection section = new FoldSection("§f底裤侦测", SECTION_KEY_DETECTION, host.collapsedSections());

        section.content().add(toggleRow(LABEL_DETECT_CORE, DESC_DETECT_CORE,
            () -> settings.detectCore,
            value -> settings.detectCore = value, () -> DEFAULTS.detectCore));
        section.content().add(toggleRow(LABEL_DETECT_ANTICHEAT, DESC_DETECT_ANTICHEAT,
            () -> settings.detectAntiCheat,
            value -> settings.detectAntiCheat = value, () -> DEFAULTS.detectAntiCheat));
        section.content().add(toggleRow(LABEL_ANNOUNCE, DESC_ANNOUNCE,
            () -> settings.announceDetection,
            value -> settings.announceDetection = value, () -> DEFAULTS.announceDetection));
        section.content().add(numberRow(LABEL_DETECT_DELAY, DESC_DETECT_DELAY,
            ServerDetectorSettings.DETECT_DELAY_MIN, ServerDetectorSettings.DETECT_DELAY_MAX,
            () -> (double) settings.detectDelay,
            value -> settings.detectDelay = (int) Math.round(value),
            () -> (double) DEFAULTS.detectDelay));

        stack.add(section);
    }

    // ── 组② 资源包劫持 ──

    public void buildResourcePack(CompactStack stack) {
        ServerDetectorSettings settings = module.settings();
        FoldSection section = new FoldSection("§f资源包劫持", SECTION_KEY_RESOURCE_PACK, host.collapsedSections());

        SettingSegmented control = new SettingSegmented(MODE_LABELS,
            () -> settings.resourcePackMode.ordinal(),
            index -> {
                ResourcePackMode[] modes = ResourcePackMode.values();
                if (index < 0 || index >= modes.length) return;
                settings.resourcePackMode = modes[index];
                persist();
                // 三行下载参数随模式显隐，改模式即整页重建
                host.reload();
            });
        section.content().add(new ConsoleRow(host, () -> LABEL_RESOURCE_PACK_MODE, DESC_RESOURCE_PACK_MODE, null,
            List.of(new Ctl(control, DESC_RESOURCE_PACK_MODE),
                ConsoleWidgets.resetCtl(() -> {
                    settings.resourcePackMode = DEFAULTS.resourcePackMode;
                    persist();
                    host.reload();
                }, LABEL_RESOURCE_PACK_MODE))));

        // 下载参数只在自动白嫖下出现（等价旧设置声明的 visible 条件）
        if (settings.resourcePackMode == ResourcePackMode.AUTO_DOWNLOAD) {
            section.content().add(numberRow(LABEL_DOWNLOAD_RETRIES, DESC_DOWNLOAD_RETRIES,
                ServerDetectorSettings.DOWNLOAD_RETRIES_MIN, ServerDetectorSettings.DOWNLOAD_RETRIES_MAX,
                () -> (double) settings.downloadRetries,
                value -> settings.downloadRetries = (int) Math.round(value),
                () -> (double) DEFAULTS.downloadRetries));
            section.content().add(numberRow(LABEL_DOWNLOAD_TIMEOUT, DESC_DOWNLOAD_TIMEOUT,
                ServerDetectorSettings.DOWNLOAD_TIMEOUT_MIN, ServerDetectorSettings.DOWNLOAD_TIMEOUT_MAX,
                () -> (double) settings.downloadTimeout,
                value -> settings.downloadTimeout = (int) Math.round(value),
                () -> (double) DEFAULTS.downloadTimeout));
            section.content().add(toggleRow(LABEL_RESUME_DOWNLOAD, DESC_RESUME_DOWNLOAD,
                () -> settings.resumeDownload,
                value -> settings.resumeDownload = value, () -> DEFAULTS.resumeDownload));
        }

        stack.add(section);

        // 资源包缓存目录入口：旧项目该按钮在模块面板上（getWidget），模块页结构已固定为
        // 「打开控制台 + 内嵌说明」，故按其归属挪到本页，按钮文字逐字未改
        stack.add(new ButtonStrip(host, List.of(
            new Ctl(new Button("§b查看下载资源包", module::openResourcePackFolder))), ButtonStrip.BUTTON_HEIGHT));
    }

    // ── 行构件组装 ──

    /** 整数数值行：步进 1（旧项目均为 noSlider），改动即写盘；旧描述同时作为悬停提示 */
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

    /** 开关行：改动即写盘；写盘后整页重建，行内控件重新按最新设置取值 */
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
