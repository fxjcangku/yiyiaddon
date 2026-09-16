package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 自动挖矿控制台「Baritone调优」页：秒破 3 项 + 开关类 19 项 + 数值类 8 项。
 *
 * <p>逐字搬运自 {@code AutoMinerPage.buildBaritoneGroup()}；顺序、设置名、描述、取值域一字未改，
 * 改动后下调 Baritone 的回调体（键名与调用）与配置页逐字一致，可见性联动三处
 * （{@code 怪物规避半径} ← {@code 怪物规避}、{@code 暴露矿石检测距离} ← {@code 仅挖暴露矿石}、
 * {@code 合法挖掘高度} ← {@code 合法挖掘模式}）用同一份 {@link #visible} 包一层，
 * 与配置页写法相同。</p>
 *
 * <p>不下调 Baritone 的三项同配置页：{@code 寻路物流破坏方块}（只由状态机在物流态压
 * {@code allowBreak}）、{@code 岩浆透视} 与 {@code 岩浆透视范围}（模块自用）。</p>
 */
public final class MiningBaritonePage {

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;

    public MiningBaritonePage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
    }

    /**
     * 装配本页内容：秒破 / 开关 / 数值三段折叠。
     *
     * <p>30 行平铺约 1254px，可视区只有约 404px，找一项要滚三屏；改成
     * {@link FoldSection} 后「开关」「数值」两段默认收起（键写在窗口的
     * {@code collapsedSections()} 里，重建不丢），点标题即可展开。</p>
     */
    public void build(CompactStack stack) {
        MiningSettings settings = module.settings();

        FoldSection fastBreak = new FoldSection("§7§l秒破 §8(点标题可收起)",
            "baritone:fastbreak", owner.collapsedSections());
        CompactStack fastBreakRows = fastBreak.content();

        fastBreakRows.add(new ConsoleRow(owner, () -> "快速破坏（秒破）",
            "使用 START→服务端 0.7 最早阈值→STOP 的真实发包流程加速破坏；硬方块会等待服务端所需 tick，不提前制造客户端空气墙",
            null, List.of(new Ctl(toggle(() -> settings.fastBreak,
                value -> settings.fastBreak = value, null)))));

        fastBreakRows.add(new ConsoleRow(owner, () -> "绕过反作弊",
            "兼容旧配置：STOP 后对相邻位置补发一次 ABORT；不能保证绕过服务器反作弊，异常时请关闭",
            null, List.of(new Ctl(toggle(() -> settings.bypassAnticheat,
                value -> settings.bypassAnticheat = value, null)))));

        fastBreakRows.add(new ConsoleRow(owner, () -> "秒破间隔（tick）",
            "服务端确认方块变化后，开始下一块前的最小等待 tick；不会用于提前重复发送 STOP",
            null, List.of(new Ctl(intBox(0, 20, () -> settings.breakInterval,
                value -> settings.breakInterval = value, null)))));
        stack.add(fastBreak);

        FoldSection toggles = new FoldSection("§7§lBaritone 开关 §8(点标题可收起)",
            "baritone:toggles", owner.collapsedSections());
        CompactStack toggleRows = toggles.content();

        // ── 开关类（除寻路物流破坏方块 / 岩浆透视外，逐项同键下调 Baritone） ──
        toggleRows.add(new ConsoleRow(owner, () -> "破坏阻挡方块",
            "挖掘时允许破坏阻挡路径的方块（石头、泥土等）", null,
            List.of(new Ctl(toggle(() -> settings.allowBreak, value -> settings.allowBreak = value,
                "allowBreak")))));

        toggleRows.add(new ConsoleRow(owner, () -> "寻路物流破坏方块",
            "前往矿物箱/食物箱/挂机点寻路时，是否允许破坏阻挡方块抄近路（关闭后旁边有路就绕行，不再挖墙）",
            null, List.of(new Ctl(toggle(() -> settings.logisticsBreakBlocks,
                value -> settings.logisticsBreakBlocks = value, null)))));

        toggleRows.add(new ConsoleRow(owner, () -> "放置方块",
            "允许搭桥或填坑（需要背包里有方块）", null,
            List.of(new Ctl(toggle(() -> settings.allowPlace, value -> settings.allowPlace = value,
                "allowPlace")))));

        toggleRows.add(new ConsoleRow(owner, () -> "自动整理物品栏",
            "允许Baritone自动将物品从背包移到快捷栏（工具、方块等）", null,
            List.of(new Ctl(toggle(() -> settings.allowInventory, value -> settings.allowInventory = value,
                "allowInventory")))));

        toggleRows.add(new ConsoleRow(owner, () -> "自动切换工具",
            "挖掘时自动选择最佳工具（镐子挖石头、铲子挖土等）", null,
            List.of(new Ctl(toggle(() -> settings.autoTool, value -> settings.autoTool = value,
                "autoTool")))));

        toggleRows.add(new ConsoleRow(owner, () -> "避开岩浆",
            "禁止 Baritone 将岩浆作为正常寻路路径", null,
            List.of(new Ctl(toggle(() -> settings.avoidLava, value -> settings.avoidLava = value,
                "avoidLava")))));

        toggleRows.add(new ConsoleRow(owner, () -> "岩浆透视",
            "高亮显示附近岩浆方块，挖矿时更直观看到岩浆位置", null,
            List.of(new Ctl(toggle(() -> settings.lavaEsp, value -> settings.lavaEsp = value, null)))));

        toggleRows.add(new ConsoleRow(owner, () -> "岩浆透视范围",
            "透视岩浆的扫描半径（格）", null,
            List.of(new Ctl(intBox(2, 16, () -> settings.lavaEspRange,
                value -> settings.lavaEspRange = value, null)))));

        toggleRows.add(new ConsoleRow(owner, () -> "怪物规避",
            "提高怪物附近路径代价，尽量绕开危险区域", null,
            List.of(new Ctl(toggle(() -> settings.mobAvoidance, value -> settings.mobAvoidance = value,
                "avoidance")))));

        toggleRows.add(new ConsoleRow(owner, () -> "掉落方块暂停",
            "遇到沙子、沙砾等掉落方块时暂停挖掘。关闭后不掉方块不暂停，挖矿更流畅（会塌方区域建议手动开启）",
            null, List.of(new Ctl(toggle(() -> settings.pauseMiningForFallingBlocks,
                value -> settings.pauseMiningForFallingBlocks = value, "pauseMiningForFallingBlocks")))));

        toggleRows.add(new ConsoleRow(owner, () -> "疾跑上坡",
            "上坡时提前一格疾跑+跳跃，提升速度", null,
            List.of(new Ctl(toggle(() -> settings.sprintAscends, value -> settings.sprintAscends = value,
                "sprintAscends")))));

        toggleRows.add(new ConsoleRow(owner, () -> "允许跑酷",
            "允许跨越1-4格的跑酷跳跃（有一定风险）", null,
            List.of(new Ctl(toggle(() -> settings.allowParkour, value -> settings.allowParkour = value,
                "allowParkour")))));

        toggleRows.add(new ConsoleRow(owner, () -> "跑酷搭桥",
            "跑酷跳跃中途放置方块来延长距离（需开启放置方块）", null,
            List.of(new Ctl(toggle(() -> settings.allowParkourPlace,
                value -> settings.allowParkourPlace = value, "allowParkourPlace")))));

        toggleRows.add(new ConsoleRow(owner, () -> "对角线上升",
            "允许斜向上跳跃，速度更快但消耗更多饥饿值", null,
            List.of(new Ctl(toggle(() -> settings.allowDiagonalAscend,
                value -> settings.allowDiagonalAscend = value, "allowDiagonalAscend")))));

        toggleRows.add(new ConsoleRow(owner, () -> "对角线下降",
            "允许斜向下降，速度更快但有一定风险（地狱慎用）", null,
            List.of(new Ctl(toggle(() -> settings.allowDiagonalDescend,
                value -> settings.allowDiagonalDescend = value, "allowDiagonalDescend")))));

        toggleRows.add(new ConsoleRow(owner, () -> "仅挖暴露矿石",
            "只挖掘能从指定距离看到的矿石，减少无效挖掘", null,
            List.of(new Ctl(toggle(() -> settings.allowOnlyExposedOres,
                value -> settings.allowOnlyExposedOres = value, "allowOnlyExposedOres")))));

        toggleRows.add(new ConsoleRow(owner, () -> "失败目标暂时跳过",
            "矿点无法到达时跳过最近目标，避免反复卡住", null,
            List.of(new Ctl(toggle(() -> settings.blacklistClosestOnFailure,
                value -> settings.blacklistClosestOnFailure = value, "blacklistClosestOnFailure")))));

        toggleRows.add(new ConsoleRow(owner, () -> "合法挖掘模式",
            "启用合法挖掘限制（关闭可提启效率但可能被检测）", null,
            List.of(new Ctl(toggle(() -> settings.legitMine, value -> settings.legitMine = value,
                "legitMine")))));

        toggleRows.add(new ConsoleRow(owner, () -> "合法挖掘检测对角矿石",
            "合法挖掘时检测与已发现矿石对角相邻的矿石", null,
            List.of(new Ctl(toggle(() -> settings.legitMineIncludeDiagonals,
                value -> settings.legitMineIncludeDiagonals = value, "legitMineIncludeDiagonals")))));
        stack.add(toggles);

        FoldSection values = new FoldSection("§7§lBaritone 数值 §8(点标题可收起)",
            "baritone:values", owner.collapsedSections());
        CompactStack valueRows = values.content();

        // ── 数值类 ──
        valueRows.add(new ConsoleRow(owner, () -> "矿点刷新间隔",
            "每隔多少tick重新扫描矿点（值越小越优先挖近矿；过小会导致寻路线乱闪、人物频繁停顿，40tick约2秒最稳定）",
            null, List.of(new Ctl(intBox(1, 100, () -> settings.mineGoalUpdateInterval,
                value -> settings.mineGoalUpdateInterval = value, "mineGoalUpdateInterval")))));

        valueRows.add(new ConsoleRow(owner, () -> "矿点缓存数量",
            "Baritone一次缓存的最大矿点数量。太少会找不到矿（寻路失败），太多会路闪。64 缓存充足且稳定",
            null, List.of(new Ctl(intBox(1, 256, () -> settings.mineMaxOreLocationsCount,
                value -> settings.mineMaxOreLocationsCount = value, "mineMaxOreLocationsCount")))));

        valueRows.add(visible(() -> settings.mobAvoidance, new ConsoleRow(owner, () -> "怪物规避半径",
            "计算怪物危险区域的半径", null,
            List.of(new Ctl(intBox(1, 16, () -> settings.mobAvoidanceRadius,
                value -> settings.mobAvoidanceRadius = value, "mobAvoidanceRadius"))))));

        valueRows.add(new ConsoleRow(owner, () -> "最大坠落高度",
            "允许从多高的地方跳下（超过会绕路）", null,
            List.of(new Ctl(intBox(0, 20, () -> settings.maxFallHeight,
                value -> settings.maxFallHeight = value, "maxFallHeightNoWater")))));

        valueRows.add(visible(() -> settings.allowOnlyExposedOres, new ConsoleRow(owner, () -> "暴露矿石检测距离",
            "判断矿石是否暴露时使用的检测距离", null,
            List.of(new Ctl(intBox(1, 8, () -> settings.allowOnlyExposedOresDistance,
                value -> settings.allowOnlyExposedOresDistance = value, "allowOnlyExposedOresDistance"))))));

        valueRows.add(new ConsoleRow(owner, () -> "最低挖掘高度",
            "Baritone 挖矿时不会低于此高度", null,
            List.of(new Ctl(intBox(-64, 320, () -> settings.minYLevelWhileMining,
                value -> settings.minYLevelWhileMining = value, "minYLevelWhileMining")))));

        valueRows.add(new ConsoleRow(owner, () -> "最高挖掘高度",
            "Baritone 挖矿时不会高于此高度", null,
            List.of(new Ctl(intBox(-64, 320, () -> settings.maxYLevelWhileMining,
                value -> settings.maxYLevelWhileMining = value, "maxYLevelWhileMining")))));

        valueRows.add(visible(() -> settings.legitMine, new ConsoleRow(owner, () -> "合法挖掘高度",
            "合法挖掘模式进行条带探索时使用的高度", null,
            List.of(new Ctl(intBox(-64, 320, () -> settings.legitMineYLevel,
                value -> settings.legitMineYLevel = value, "legitMineYLevel"))))));
        stack.add(values);
    }

    /**
     * 开关行：改动落盘；{@code baritoneKey} 非空时同步下调 Baritone（旧项目 {@code onChanged} 同键）。
     */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter, String baritoneKey) {
        return new SettingToggle(getter, value -> {
            setter.accept(value);
            module.persistSettings();
            if (baritoneKey != null) module.getBaritone().updateSetting(baritoneKey, value);
        });
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

    /** 条件元素：不满足可见性条件时高度为 0，绘制与命中全部跳过（与配置页同一实现） */
    private static CompactElement visible(BooleanSupplier condition, CompactElement inner) {
        return new CompactElement() {
            @Override
            public float height() {
                return condition.getAsBoolean() ? inner.height() : 0f;
            }

            @Override
            public void update(float dt) {
                inner.update(dt);
            }

            @Override
            public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
                if (condition.getAsBoolean()) inner.draw(canvas, x, y, width, alpha, mouseX, mouseY);
            }

            @Override
            public boolean onClick(float mx, float my, float x, float y, float width, int button) {
                return condition.getAsBoolean() && inner.onClick(mx, my, x, y, width, button);
            }

            @Override
            public boolean onDrag(float mx, float my, float x, float y, float width) {
                return condition.getAsBoolean() && inner.onDrag(mx, my, x, y, width);
            }
        };
    }
}
