package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 自动挖矿控制台「传送指令」页：五条传送指令 + GUI 选择开关与关键词 + 两个等待时长。
 *
 * <p>逐字搬自旧项目配置页的 {@code 传送指令} 分组；顺序、设置名、描述、取值域与落盘时机一字未改。
 * 2026-09-16 起本页是这九项的唯一落点。可见性联动在控制台里换了个实现方式：整页可重建，因此
 * {@code GUI按钮关键词} 行在开关为假时压根不加入堆叠（配置页原 {@code visible(...)} 的等价物），
 * 开关改动后重建本页。</p>
 */
public final class MiningTeleportPage {

    /** 文本类设置的输入框宽度与长度上限；同本项目其它自由文本设置 */
    private static final float TEXT_BOX_WIDTH = 220f;
    private static final int TEXT_MAX_LENGTH = 512;

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final MiningSettings DEFAULTS = new MiningSettings();

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;

    public MiningTeleportPage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        MiningSettings settings = module.settings();

        stack.add(new ConsoleRow(owner, () -> "前往挖矿指令",
            "传送到挖矿区域的指令（支持带/或不带/）", null,
            List.of(new Ctl(textBox(() -> settings.wildCommand, value -> settings.wildCommand = value)),
                resetText("前往挖矿指令", () -> DEFAULTS.wildCommand,
                    value -> settings.wildCommand = value))));

        stack.add(new ConsoleRow(owner, () -> "RTP需要GUI选择",
            "指令后自动扫描GUI点击匹配按钮", null,
            List.of(new Ctl(new SettingToggle(() -> settings.rtpGuiEnabled, value -> {
                settings.rtpGuiEnabled = value;
                module.persistSettings();
                // 下一行的显示随开关切换，本页整页重建后才会出现 / 消失
                owner.reload();
            })),
                resetToggle("RTP需要GUI选择", () -> DEFAULTS.rtpGuiEnabled,
                    value -> settings.rtpGuiEnabled = value))));

        // GUI按钮关键词：只在开关为真时加入（对应配置页的 visible(...)）
        if (settings.rtpGuiEnabled) {
            stack.add(new ConsoleRow(owner, () -> "GUI按钮关键词",
                "输入纯文本（如'主世界'会匹配'§a主 §e世 §b界'），自动忽略颜色和空格", null,
                List.of(new Ctl(textBox(() -> settings.rtpGuiKeyword,
                        value -> settings.rtpGuiKeyword = value)),
                    resetText("GUI按钮关键词", () -> DEFAULTS.rtpGuiKeyword,
                        value -> settings.rtpGuiKeyword = value))));
        }

        stack.add(new ConsoleRow(owner, () -> "返回卸货指令",
            "传送到卸货箱的指令", null,
            List.of(new Ctl(textBox(() -> settings.unloadCommand, value -> settings.unloadCommand = value)),
                resetText("返回卸货指令", () -> DEFAULTS.unloadCommand,
                    value -> settings.unloadCommand = value))));

        stack.add(new ConsoleRow(owner, () -> "前往补给指令",
            "传送到食物箱的指令", null,
            List.of(new Ctl(textBox(() -> settings.supplyCommand, value -> settings.supplyCommand = value)),
                resetText("前往补给指令", () -> DEFAULTS.supplyCommand,
                    value -> settings.supplyCommand = value))));

        stack.add(new ConsoleRow(owner, () -> "前往修复指令",
            "传送到挂机修补点", null,
            List.of(new Ctl(textBox(() -> settings.afkCommand, value -> settings.afkCommand = value)),
                resetText("前往修复指令", () -> DEFAULTS.afkCommand,
                    value -> settings.afkCommand = value))));

        stack.add(new ConsoleRow(owner, () -> "死亡返回指令",
            "复活后返回挂机点", null,
            List.of(new Ctl(textBox(() -> settings.respawnCommand, value -> settings.respawnCommand = value)),
                resetText("死亡返回指令", () -> DEFAULTS.respawnCommand,
                    value -> settings.respawnCommand = value))));

        // 下面三行的行下注释（用户 2026-09-18：「没懂啊 能不能加下面加中文注释」）：
        // 白话讲清「这两个数在什么时候起作用、出问题该往哪边调」。
        // 注意 Note 是单行不换行的构件（超出面板宽度会被直接裁掉），所以每条都拆成两行短句。
        stack.add(new ConsoleRow(owner, () -> "传送等待时长",
            "发出传送指令后等多少秒：这段时间内位置没变化就算「传送未生效」", null,
            List.of(new Ctl(intBox(1, 120, () -> settings.teleportDelay,
                    value -> settings.teleportDelay = value, null)),
                resetInt("传送等待时长", () -> DEFAULTS.teleportDelay,
                    value -> settings.teleportDelay = value))));
        stack.add(new Note(owner, "§8人还在原地没动，就认为这次传送没生效"));
        stack.add(new Note(owner, "§8服务器传得慢（比如 RTP 要排队几秒）就把这个数调大"));

        stack.add(new ConsoleRow(owner, () -> "RTP冷却时长",
            "判定「传送未生效」后等多久才重发指令：等服务器 RTP 冷却过去，避免冷却期空发", null,
            List.of(new Ctl(intBox(1, 3600, () -> settings.rtpCooldown,
                    value -> settings.rtpCooldown = value, null)),
                resetInt("RTP冷却时长", () -> DEFAULTS.rtpCooldown,
                    value -> settings.rtpCooldown = value))));
        stack.add(new Note(owner, "§8判定「没生效」之后，等这么久才再发一次指令"));
        stack.add(new Note(owner, "§8为了躲开服务器自己的传送冷却；只在下面开关打开时才会用到"));

        // 用户 2026-09-18 追加：服务器自身有传送冷却 / RTP 排队延迟时，重发会造成「已经传过去了又被传一次」
        stack.add(new ConsoleRow(owner, () -> "传送失败自动重试",
            "关掉后：超时不再重发传送指令，只继续等传送生效", null,
            List.of(new Ctl(new SettingToggle(() -> settings.teleportRetryEnabled, value -> {
                settings.teleportRetryEnabled = value;
                module.persistSettings();
            })),
                resetToggle("传送失败自动重试", () -> DEFAULTS.teleportRetryEnabled,
                    value -> settings.teleportRetryEnabled = value))));
        stack.add(new Note(owner, "§8关掉后：超时也不再帮你发指令，就一直等你传过去"));
        stack.add(new Note(owner, "§8服务器有传送冷却 / RTP 排队时建议关掉，免得「传走了又被传一次」"));
    }

    /**
     * 行内「恢复默认」：把该行写回出厂值（与改动同一条写入路径：落盘），再刷新本页。
     */
    private Ctl resetInt(String label, Supplier<Integer> defaultValue, IntConsumer setter) {
        return ConsoleWidgets.resetCtl(() -> {
            setter.accept(defaultValue.get());
            module.persistSettings();
            owner.reload();
        }, label);
    }

    /** 行内「恢复默认」（开关行，写法同 {@link #resetInt}） */
    private Ctl resetToggle(String label, Supplier<Boolean> defaultValue, Consumer<Boolean> setter) {
        return ConsoleWidgets.resetCtl(() -> {
            setter.accept(defaultValue.get());
            module.persistSettings();
            owner.reload();
        }, label);
    }

    /** 行内「恢复默认」（文本行，写法同 {@link #resetInt}） */
    private Ctl resetText(String label, Supplier<String> defaultValue, Consumer<String> setter) {
        return ConsoleWidgets.resetCtl(() -> {
            setter.accept(defaultValue.get());
            module.persistSettings();
            owner.reload();
        }, label);
    }

    /** 整数设置框：步进 1、无滑块（旧项目全部 {@code noSlider}），改动落盘并可按旧键下调 Baritone */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, IntConsumer setter,
                                    String baritoneKey) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                int next = (int) Math.round(value);
                setter.accept(next);
                module.persistSettings();
                if (baritoneKey != null) module.getBaritone().updateSetting(baritoneKey, next);
            });
    }

    /** 单行文本设置（5 条传送指令与 GUI 按钮关键词） */
    private SettingTextBox textBox(Supplier<String> getter, Consumer<String> setter) {
        return new SettingTextBox(getter, value -> {
            setter.accept(value);
            module.persistSettings();
        }, TEXT_MAX_LENGTH).width(TEXT_BOX_WIDTH);
    }
}
