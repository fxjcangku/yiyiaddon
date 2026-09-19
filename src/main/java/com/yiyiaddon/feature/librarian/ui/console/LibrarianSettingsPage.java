package com.yiyiaddon.feature.librarian.ui.console;

import com.yiyiaddon.feature.librarian.AutoLibrarianModule;
import com.yiyiaddon.feature.librarian.config.LibrarianSettings;
import com.yiyiaddon.feature.librarian.config.SuccessSound;
import com.yiyiaddon.feature.librarian.ui.LibrarianSelectors;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.keybind.AddonKeybind;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingKeybind;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.yiyiaddon.ui.console.ConsoleWidgets.CLEAR_BUTTON;
import static com.yiyiaddon.ui.console.ConsoleWidgets.CLEAR_HINT;
import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_CYCLE;
import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_KEYBIND;

/**
 * 控制台「设置」页：旧项目 12 项设置 / 5 组，按 D4 拍板折成「目标 / 行为 / 通知 / 调试」四个折叠组，
 * 快捷「暂停快捷键」行排整页最末（第 214 条：快捷键行末置）。
 *
 * <p><b>设置项逐字</b>：名称 / 描述 / 默认值来自旧 {@code AutoLibrarianSettings}（旧 {@code :19-143}），
 * 分组名沿用旧分组名（目标 / 行为 / 通知 / 调试 / 快捷键），不新造分类。</p>
 *
 * <p><b>可见性 1:1</b>：旧项目 {@code 附魔成功音效} 的可见条件是
 * {@code visible(playNotificationSound::get)}（旧 {@code :107}），因此「提示音」关闭时该行整行不铺 ——
 * 本项目「提示音」行的开关带 {@code scheduleReload()}，关掉即整页重排。</p>
 *
 * <p><b>数字一律 {@link SettingNumberBox}</b>（禁止滑块，第 123 条）；数值 / 开关 / 循环改动
 * 全部立即 {@link AutoLibrarianModule#persistSettings()}（第 173 条）。
 * 旧项目只有下界的五项在本项目的数字框上界见 {@link LibrarianSettings} 类注释（登记 D-15-10）。</p>
 *
 * <p><b>暂停快捷键按 D2 拍板照实现</b>：描述文本逐字保留
 * （{@code 按下后切换暂停/继续状态，保留当前村民和附魔进度。}），实际行为是按下即强制结束
 * （旧项目 {@code onTick} 的既有实现），两者不一致属旧项目原样，不静默修正。</p>
 */
public final class LibrarianSettingsPage {

    /** 折叠块状态键（窗口侧记忆展开 / 收起） */
    private static final String SECTION_KEY_TARGET = "group:目标";
    private static final String SECTION_KEY_BEHAVIOR = "group:行为";
    private static final String SECTION_KEY_NOTIFY = "group:通知";
    private static final String SECTION_KEY_DEBUG = "group:调试";

    /** 各设置项的描述（旧项目设置定义里的描述，逐字） */
    private static final String DESC_RADIUS = "固定交易站村民搜索半径（格）。";
    private static final String DESC_PRICE = "允许购买的单本附魔书最高绿宝石成本。";
    private static final String DESC_PROFESSION_TIMEOUT = "等待村民职业同步的最大 Tick 数。";
    private static final String DESC_ACTION_DELAY = "普通业务动作之间的 Tick 间隔。";
    private static final String DESC_RESET_DELAY = "拆除与重新放置讲台之间的最小 Tick 间隔。";
    private static final String DESC_REMOVE_TARGET = "成交验证成功后，从本次运行目标集合移除已完成目标。";
    private static final String DESC_NOTIFICATION_SOUND = "找到目标和完成目标时播放提示音。";
    private static final String DESC_SUCCESS_SOUND = "找到目标附魔时播放的音效。";
    private static final String DESC_DEBUG_MODE = "输出状态与移动诊断信息。";
    private static final String DESC_CHAT_FEEDBACK = "在聊天栏输出业务反馈。";

    /** 暂停快捷键描述（旧 {@code :138-142} 逐字） */
    private static final String DESC_PAUSE_KEYBIND = "按下后切换暂停/继续状态，保留当前村民和附魔进度。";

    /** 键位块宽度：放得下 {@code Mouse Forward} / {@code Num Lock + F12} 这类长名，同时给清空按钮留位 */
    private static final float KEYBIND_WIDTH = 140f;
    /** 清空按钮宽度 */
    private static final float CLEAR_WIDTH = 72f;

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final LibrarianSettings DEFAULTS = new LibrarianSettings();

    private final LibrarianConsoleScreen host;
    private final AutoLibrarianModule module;

    public LibrarianSettingsPage(LibrarianConsoleScreen host, AutoLibrarianModule module) {
        this.host = host;
        this.module = module;
    }

    /** 页面装配：目标 → 行为 → 通知 → 调试 → 暂停快捷键（末置） */
    public void build(CompactStack stack) {
        stack.add(targetSection());
        stack.add(behaviorSection());
        stack.add(notifySection());
        stack.add(debugSection());
        stack.add(pauseKeybindRow());
    }

    // ── 目标组 ──

    /** 目标组（旧 {@code sgTarget}）：目标附魔选择器入口行 */
    private FoldSection targetSection() {
        FoldSection section = new FoldSection("§f目标", SECTION_KEY_TARGET, host.collapsedSections());
        section.content().add(LibrarianSelectors.targetRow(host, module));
        return section;
    }

    // ── 行为组 ──

    /** 行为组（旧 {@code sgBehavior}）：搜索半径 / 价格上限 / 职业等待 / 动作延迟 / 刷新延迟 / 找到后移除目标 */
    private FoldSection behaviorSection() {
        LibrarianSettings settings = module.settings();
        FoldSection section = new FoldSection("§f行为", SECTION_KEY_BEHAVIOR, host.collapsedSections());
        section.content().add(numberRow("村民搜索半径", DESC_RADIUS,
            LibrarianSettings.RADIUS_MIN, LibrarianSettings.RADIUS_MAX,
            () -> (double) settings.searchRadius, value -> settings.searchRadius = value.intValue(),
            () -> (double) DEFAULTS.searchRadius));
        section.content().add(numberRow("最高绿宝石价格", DESC_PRICE,
            LibrarianSettings.PRICE_MIN, LibrarianSettings.PRICE_MAX,
            () -> (double) settings.maximumEmeraldPrice,
            value -> settings.maximumEmeraldPrice = value.intValue(),
            () -> (double) DEFAULTS.maximumEmeraldPrice));
        section.content().add(numberRow("职业等待超时", DESC_PROFESSION_TIMEOUT,
            LibrarianSettings.PROFESSION_TIMEOUT_MIN, LibrarianSettings.PROFESSION_TIMEOUT_MAX,
            () -> (double) settings.professionTimeout, value -> settings.professionTimeout = value.intValue(),
            () -> (double) DEFAULTS.professionTimeout));
        section.content().add(numberRow("动作延迟", DESC_ACTION_DELAY,
            LibrarianSettings.ACTION_DELAY_MIN, LibrarianSettings.ACTION_DELAY_MAX,
            () -> (double) settings.actionDelay, value -> settings.actionDelay = value.intValue(),
            () -> (double) DEFAULTS.actionDelay));
        section.content().add(numberRow("刷新延迟", DESC_RESET_DELAY,
            LibrarianSettings.RESET_DELAY_MIN, LibrarianSettings.RESET_DELAY_MAX,
            () -> (double) settings.resetDelay, value -> settings.resetDelay = value.intValue(),
            () -> (double) DEFAULTS.resetDelay));
        section.content().add(toggleRow("找到后移除目标", DESC_REMOVE_TARGET,
            () -> settings.removeTargetOnFound, value -> settings.removeTargetOnFound = value, null,
            () -> DEFAULTS.removeTargetOnFound));
        return section;
    }

    // ── 通知组 ──

    /** 通知组（旧 {@code sgNotify}）：提示音 + 附魔成功音效（仅提示音为真时铺） */
    private FoldSection notifySection() {
        LibrarianSettings settings = module.settings();
        FoldSection section = new FoldSection("§f通知", SECTION_KEY_NOTIFY, host.collapsedSections());
        // 提示音开关影响「附魔成功音效」行的可见性，改完必须整页重排
        section.content().add(toggleRow("提示音", DESC_NOTIFICATION_SOUND,
            () -> settings.playNotificationSound,
            value -> settings.playNotificationSound = value, host::scheduleReload,
            () -> DEFAULTS.playNotificationSound));
        if (settings.playNotificationSound) {
            List<String> options = Arrays.stream(SuccessSound.values()).map(SuccessSound::toString).toList();
            section.content().add(new ConsoleRow(host, () -> "附魔成功音效", DESC_SUCCESS_SOUND, COMMENT_CYCLE,
                List.of(new Ctl(new SettingCycle(options, () -> settings.successSound.ordinal(),
                    index -> {
                        settings.successSound = SuccessSound.values()[index];
                        module.persistSettings();
                    })),
                    ConsoleWidgets.resetCtl(() -> {
                        settings.successSound = DEFAULTS.successSound;
                        module.persistSettings();
                        host.reload();
                    }, "附魔成功音效"))));
        }
        return section;
    }

    // ── 调试组 ──

    /** 调试组（旧 {@code sgDebug}）：调试模式 + 聊天反馈 */
    private FoldSection debugSection() {
        LibrarianSettings settings = module.settings();
        FoldSection section = new FoldSection("§f调试", SECTION_KEY_DEBUG, host.collapsedSections());
        section.content().add(toggleRow("调试模式", DESC_DEBUG_MODE,
            () -> settings.debugMode, value -> settings.debugMode = value, null,
            () -> DEFAULTS.debugMode));
        section.content().add(toggleRow("聊天反馈", DESC_CHAT_FEEDBACK,
            () -> settings.chatFeedback, value -> settings.chatFeedback = value, null,
            () -> DEFAULTS.chatFeedback));
        return section;
    }

    // ── 快捷键行（整页最末） ──

    /**
     * 暂停快捷键行（旧 {@code :138-142}）：自研键位控件 {@link SettingKeybind} + 清空按钮。
     *
     * <p><b>为什么还要一个清空按钮</b>：键位控件自带的右键清空在控制台里点不到 ——
     * 行构件 {@code ConsoleRow#onClick} 只把左键转给控件，右键根本到不了控件。
     * 补一个显式按钮承载「取消绑定」这一旧项目本就有的能力（旧框架键位控件的清除动作）；
     * 未绑定时按钮为禁用态，不存在点了没反应的悬案（与自动村民交易同一处理）。</p>
     */
    private ConsoleRow pauseKeybindRow() {
        SettingKeybind keybind = new SettingKeybind(
            () -> module.settings().pauseKey,
            value -> {
                module.settings().pauseKey = value;
                module.persistSettings();
            }).width(KEYBIND_WIDTH);
        Button clear = new Button(CLEAR_BUTTON, this::clearPauseKey)
            .width(CLEAR_WIDTH)
            .disabledWhen(() -> !module.settings().pauseKey.isSet());
        return new ConsoleRow(host, () -> "暂停快捷键", DESC_PAUSE_KEYBIND, COMMENT_KEYBIND,
            List.of(new Ctl(keybind, DESC_PAUSE_KEYBIND + "（点击后按任意键绑定）"),
                new Ctl(clear, CLEAR_HINT),
                // 出厂值 = 未绑定，动作与「清空」同源（复用同一段清空逻辑）
                ConsoleWidgets.resetCtl(() -> {
                    clearPauseKey();
                    host.reload();
                }, "暂停快捷键")));
    }

    /** 清空暂停键绑定（未绑定时按钮为禁用态，正常点不到） */
    private void clearPauseKey() {
        module.settings().pauseKey = AddonKeybind.none();
        module.persistSettings();
    }

    // ── 行构件组装 ──

    /** 开关行：改动即写盘（第 173 条）；{@code afterChange} 用于「影响页面结构」的开关 */
    private ConsoleRow toggleRow(String label, String hint,
                                 Supplier<Boolean> getter, Consumer<Boolean> setter,
                                 Runnable afterChange, Supplier<Boolean> defaultValue) {
        return new ConsoleRow(host, () -> label, hint, null,
            List.of(new Ctl(new SettingToggle(getter, value -> {
                setter.accept(value);
                module.persistSettings();
                if (afterChange != null) afterChange.run();
            })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    module.persistSettings();
                    host.reload();
                }, label)));
    }

    /** 数字行（旧滑条 → 数字框，第 123 条）：步进 1、整数值，改动即写盘；行尾 ↺ 恢复本行默认值 */
    private ConsoleRow numberRow(String label, String hint, double min, double max,
                                 Supplier<Double> getter, Consumer<Double> setter,
                                 Supplier<Double> defaultValue) {
        return new ConsoleRow(host, () -> label, hint, null,
            List.of(new Ctl(new SettingNumberBox(min, max, 1, "%.0f",
                getter, value -> {
                setter.accept(value);
                module.persistSettings();
            })),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    module.persistSettings();
                    host.reload();
                }, label)));
    }
}
