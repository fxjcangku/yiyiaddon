package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.service.ToolDurability;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 自动挖矿控制台「触发条件」页：满载 / 食物 / 耐久 / 自动断线 / 潜影盒打包机 / 状态播报。
 *
 * <p>前四行逐字搬自旧项目配置页的 {@code 触发条件} 分组；顺序、设置名、描述、取值域与落盘时机一字未改，
 * 只把行容器换成本项目的控制台行构件。2026-09-16 起本页是这四行的唯一落点（配置页不再平铺设置）；
 * 「自动断线」一行是 2026-09-18 本项目追加项（旧项目没有对应项），插在三个阈值之后、同为「到线触发」；
 * 「状态播报」一行是 2026-09-18 本项目追加项（旧项目没有对应项）。</p>
 */
public final class MiningThresholdPage {

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final MiningSettings DEFAULTS = new MiningSettings();

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;

    public MiningThresholdPage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        MiningSettings settings = module.settings();
        // 自用模式隐藏两行与卸货流程绑定的项（用户 2026-09-21：「触发条件 在自用模式启动下也不要出现隐藏」）：
        // 「满载组数」判的是卸货触发，自用模式根本不走 UNLOADING（挖满直接 SELL_TRAVEL，阈值在「自用模式」页的
        // 「触发组数」）；「潜影盒打包机」绑的是矿物箱填装，自用模式连矿物箱都不绑定。留着只会让人以为要设。
        boolean personal = module.isPersonalMode();

        if (!personal) {
            stack.add(new ConsoleRow(owner, () -> "满载组数",
                "背包矿物达到多少组时触发卸货", null,
                List.of(new Ctl(intBox(1, 36, () -> settings.unloadThreshold,
                        value -> settings.unloadThreshold = value, null)),
                    resetInt("满载组数", () -> DEFAULTS.unloadThreshold, null,
                        value -> settings.unloadThreshold = value))));
        }

        stack.add(new ConsoleRow(owner, () -> "食物阈值",
            "背包食物少于此数量时触发补给", null,
            List.of(new Ctl(intBox(1, 64, () -> settings.hungerThreshold,
                    value -> settings.hungerThreshold = value, null)),
                resetInt("食物阈值", () -> DEFAULTS.hungerThreshold, null,
                    value -> settings.hungerThreshold = value))));

        // 耐久阈值上限自动跟着手持工具走：主手优先、主手不是工具时看副手（用户 2026-09-19 拍板
        // 「上限改成只看手持那把」→ 随后确认「副手也算作手持」），两边都不是工具时用原版最高工具耐久兜底。
        // 用户原话：「你要自动算手里拿的镐子自动计算耐久度」
        // —— 上限写死成 3000 那种就会高于工具满耐久，一设就变成「工具不是满耐久就得修」的往返死循环。
        // 上限在每次打开 / 切页签 / 点刷新重建本页时重算（控制台重建走 rebuild）。
        int toolMax = ToolDurability.heldToolDurability(Minecraft.getInstance().player);
        int thresholdMax = Math.max(toolMax, MiningSettings.DURABILITY_THRESHOLD_FALLBACK_MAX);

        stack.add(new ConsoleRow(owner, () -> "耐久阈值",
            personal
                ? "自用模式不移交挂机点修补：没带经验修补的镐低于此值时提示换镐（带经验修补的不管，挖矿自带经验会自修；剩 1 点耐久的镐一律不再用来挖）"
                : "工具剩余耐久低于此值时前往挂机点修补（上限自动取你手持工具的满耐久：木镐 59 / 钻石镐 1561 / 下界合金镐 2031，主手不是工具时看副手，都没拿工具时按 2031）；无经验修补的工具修不了，不前往挂机点、只提示", null,
            List.of(new Ctl(intBox(1, thresholdMax, () -> settings.durabilityThreshold,
                    value -> settings.durabilityThreshold = value, null)),
                resetInt("耐久阈值", () -> DEFAULTS.durabilityThreshold, null,
                    value -> settings.durabilityThreshold = value))));

        // 自动断线（用户 2026-09-18 追加：服务器死亡掉落时，血量到线先退服保命）。
        // 摆位与排布照本页既有口径：紧跟三个阈值之后（同一类「到线触发」），
        // 数值框在前、开关在后（与「快速停止键」行「控件在前、辅助在后」一致）。
        stack.add(new ConsoleRow(owner, () -> "自动断线",
            "血量掉到设定的格数时立即断开服务器连接，避免死亡掉落（一格血 = 2 点血量，默认 2 格）",
            null,
            List.of(new Ctl(intBox(1, 20, "%.0f 格", () -> settings.autoDisconnectHealth,
                    value -> settings.autoDisconnectHealth = value, null),
                    "断线血量：血量掉到这一格数（含）时断开连接"),
                new Ctl(toggle(() -> settings.autoDisconnect,
                    value -> settings.autoDisconnect = value),
                    "开关自动断线；关闭后血量再低也不会断线"),
                resetInt("自动断线", () -> DEFAULTS.autoDisconnectHealth, null,
                    value -> settings.autoDisconnectHealth = value),
                resetToggle("自动断线", () -> DEFAULTS.autoDisconnect,
                    value -> settings.autoDisconnect = value))));

        if (!personal) {
            stack.add(new ConsoleRow(owner, () -> "潜影盒打包机",
                "卸货时把矿物箱(潜影盒)填满，检测到满后等红石推盒换新盒，自动重开箱继续放，直到背包目标矿放完才RTP。给搭配潜影盒打包机的挂机用户使用。",
                null,
                List.of(new Ctl(toggle(() -> settings.shulkerPacker,
                        value -> settings.shulkerPacker = value)),
                    resetToggle("潜影盒打包机", () -> DEFAULTS.shulkerPacker,
                        value -> settings.shulkerPacker = value))));
        }

        // 状态播报（用户 2026-09-18：「加一个播报状态的按钮，默认开启，提示用户可以在配置页面关闭播报，
        // 或者弄个自动折叠信息的」——两个都做了：开关在运行时关掉全部状态类播报，
        // 折叠让同一条文本 5 秒内只出现一次）
        stack.add(new ConsoleRow(owner, () -> "状态播报",
            "关闭后模块运行时不再发状态类播报（错误提示仍保留）；相同内容的播报 5 秒内自动折叠，只显示一次",
            null,
            List.of(new Ctl(toggle(() -> settings.statusBroadcast,
                    value -> settings.statusBroadcast = value)),
                resetToggle("状态播报", () -> DEFAULTS.statusBroadcast,
                    value -> settings.statusBroadcast = value))));
    }

    /**
     * 行内「恢复默认」：把该行写回出厂值（与改动同一条写入路径：落盘 + 按需下调 Baritone），再刷新本页。
     */
    private Ctl resetInt(String label, Supplier<Integer> defaultValue, String baritoneKey, IntConsumer setter) {
        return ConsoleWidgets.resetCtl(() -> {
            int value = defaultValue.get();
            setter.accept(value);
            module.persistSettings();
            if (baritoneKey != null) module.getBaritone().updateSetting(baritoneKey, value);
            owner.reload();
        }, label);
    }

    /** 行内「恢复默认」（开关行，写法同上） */
    private Ctl resetToggle(String label, Supplier<Boolean> defaultValue, Consumer<Boolean> setter) {
        return ConsoleWidgets.resetCtl(() -> {
            setter.accept(defaultValue.get());
            module.persistSettings();
            owner.reload();
        }, label);
    }

    /** 开关行：改动落盘（与配置页同一个 {@code persistSettings} 时机） */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return new SettingToggle(getter, value -> {
            setter.accept(value);
            module.persistSettings();
        });
    }

    /** 整数设置框：步进 1、无滑块（旧项目全部 {@code noSlider}），改动落盘 */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, IntConsumer setter,
                                    String baritoneKey) {
        return intBox(min, max, "%.0f", getter, setter, baritoneKey);
    }

    /**
     * 整数设置框（自定义显示格式）。
     *
     * <p>第 124 条：带单位的设置项必须把单位写进格式串（如 {@code %.0f 格}），
     * 数值框按该格式显示、也按同一格式回读。</p>
     */
    private SettingNumberBox intBox(int min, int max, String format, Supplier<Integer> getter,
                                    IntConsumer setter, String baritoneKey) {
        return new SettingNumberBox(min, max, 1, format,
            () -> (double) getter.get(),
            value -> {
                int next = (int) Math.round(value);
                setter.accept(next);
                module.persistSettings();
                if (baritoneKey != null) module.getBaritone().updateSetting(baritoneKey, next);
            });
    }
}
