package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
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
 * <p>逐字搬运自 {@code AutoMinerPage.buildCommandGroup()}；顺序、设置名、描述、取值域与落盘时机
 * 一字未改。唯一与配置页不同的地方是<b>可见性联动</b>的实现方式：控制台页整页可重建，因此
 * {@code GUI按钮关键词} 行在开关为假时压根不加入堆叠（配置页的 {@code visible(...)} 等价物），
 * 开关改动后重建本页。</p>
 */
public final class MiningTeleportPage {

    /** 文本类设置的输入框宽度与长度上限；同本项目其它自由文本设置 */
    private static final float TEXT_BOX_WIDTH = 220f;
    private static final int TEXT_MAX_LENGTH = 512;

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
            List.of(new Ctl(textBox(() -> settings.wildCommand, value -> settings.wildCommand = value)))));

        stack.add(new ConsoleRow(owner, () -> "RTP需要GUI选择",
            "指令后自动扫描GUI点击匹配按钮", null,
            List.of(new Ctl(new SettingToggle(() -> settings.rtpGuiEnabled, value -> {
                settings.rtpGuiEnabled = value;
                module.persistSettings();
                // 下一行的显示随开关切换，本页整页重建后才会出现 / 消失
                owner.reload();
            })))));

        // GUI按钮关键词：只在开关为真时加入（对应配置页的 visible(...)）
        if (settings.rtpGuiEnabled) {
            stack.add(new ConsoleRow(owner, () -> "GUI按钮关键词",
                "输入纯文本（如'主世界'会匹配'§a主 §e世 §b界'），自动忽略颜色和空格", null,
                List.of(new Ctl(textBox(() -> settings.rtpGuiKeyword,
                    value -> settings.rtpGuiKeyword = value)))));
        }

        stack.add(new ConsoleRow(owner, () -> "返回卸货指令",
            "传送到卸货箱的指令", null,
            List.of(new Ctl(textBox(() -> settings.unloadCommand, value -> settings.unloadCommand = value)))));

        stack.add(new ConsoleRow(owner, () -> "前往补给指令",
            "传送到食物箱的指令", null,
            List.of(new Ctl(textBox(() -> settings.supplyCommand, value -> settings.supplyCommand = value)))));

        stack.add(new ConsoleRow(owner, () -> "前往修复指令",
            "传送到挂机修补点", null,
            List.of(new Ctl(textBox(() -> settings.afkCommand, value -> settings.afkCommand = value)))));

        stack.add(new ConsoleRow(owner, () -> "死亡返回指令",
            "复活后返回挂机点", null,
            List.of(new Ctl(textBox(() -> settings.respawnCommand, value -> settings.respawnCommand = value)))));

        stack.add(new ConsoleRow(owner, () -> "传送等待时长",
            "执行传送指令后等待秒数", null,
            List.of(new Ctl(intBox(1, 120, () -> settings.teleportDelay,
                value -> settings.teleportDelay = value, null)))));

        stack.add(new ConsoleRow(owner, () -> "RTP冷却时长",
            "服务器 RTP 传送冷却秒数：传送失败后等这么久再重试，避免冷却期空发指令", null,
            List.of(new Ctl(intBox(1, 3600, () -> settings.rtpCooldown,
                value -> settings.rtpCooldown = value, null)))));
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
