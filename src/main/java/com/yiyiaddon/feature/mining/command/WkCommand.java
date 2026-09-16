package com.yiyiaddon.feature.mining.command;

import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandContext;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.repository.MiningPointStore;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import net.minecraft.client.Minecraft;

import java.util.Arrays;
import java.util.List;

/**
 * 自动挖矿指令 {@code .wk}：挖矿坐标绑定（矿物箱、食物箱、挂机修复点）。
 *
 * <p><b>用户交互资产（与旧项目逐字一致，禁止改写）：</b>指令名 {@code wk}、无别名、描述
 * {@code 挖矿坐标绑定（矿物箱、食物箱、挂机修复点）}（旧 {@code WKCommand:82}）；裸 {@code .wk}
 * 输出「坐标绑定状态」而不是帮助（旧 {@code :88-91}，等价于 {@code .wk 状态}）；子命令字面量
 * {@code 状态} / {@code 设置} / {@code 移除} / {@code 清空} 与点位字面量
 * {@code 矿物箱} / {@code 食物箱} / {@code 挂机修复点}（旧 {@code :93-117}）全部原样；
 * 回执排版走 {@link CommandMessageFormatter}（旧 {@code core/CommandMessageFormatter}）。</p>
 *
 * <p><b>留白（一行都没写）：</b>{@code .wk 检测假矿}（旧 {@code :120}、{@code :587-599}）随种子模式
 * （{@code OrePredictor}）一起留待后续批次，故命令树与补全候选里都不出现该字面量；随之留白的
 * 还有它专用的兜底文案 {@code §6自动挖矿模块未加载}（旧 {@code :593}），此处不另造替代文案。</p>
 *
 * <p><b>绑定 / 移除只有一处实现：</b>全部转调 {@link MiningBindingService} 的指令路径
 * （{@code fromGui = false}）。准星判定、容器判定、维度取值都在 service
 * 与 {@link MiningPointStore} 里，本类不复制任何一条判断，也不自行播报绑定结果。</p>
 *
 * <p><b>两套文案并存不统一：</b>配置页卡片走 {@code fromGui = true}（多「请重新设置」、解绑走
 * {@code §c§l✗ 已删除 X 绑定}），指令走 {@code fromGui = false}（旧 {@code :141 / :146 / :276-301}）。
 * 这是旧项目现状，禁止合并成一套。</p>
 */
public final class WkCommand extends ClientCommand {

    /** 回执前缀：旧项目 {@code WKCommand.MODULE_NAME} 原文 {@code 自动挖矿}（旧 {@code :52}） */
    private static final String MODULE_NAME = AutoMinerModule.MESSAGE_MODULE;

    /**
     * 根节点子命令（旧 {@code WKCommand:93-117} 的 literal，注册顺序原样）。
     *
     * <p>旧树的第五个 literal {@code 检测假矿} 本轮留白，不进候选。</p>
     */
    private static final List<String> SUBCOMMANDS = List.of("状态", "设置", "移除", "清空");

    /** {@code 设置} / {@code 移除} 下的点位字面量（旧 {@code :101-103}、{@code :110-112}），取自枚举中文名 */
    private static final List<String> POINT_NAMES = Arrays.stream(MiningPointType.values())
        .map(MiningPointType::displayName)
        .toList();

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public String name() {
        return "wk";
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public String description() {
        return "挖矿坐标绑定（矿物箱、食物箱、挂机修复点）";
    }

    @Override
    public void execute(CommandContext context) {
        // 指令入口统一按当前服务器装载点位：指令没有「打开界面」这个天然的重读时机，
        // 不装载就会读到空表（状态页显示未绑定、清空清不动、覆盖保护失效）
        AutoMinerModule module = module();
        if (module != null) module.reloadStore();

        // 根节点可直接执行：旧 Brigadier 根节点 executes 指向 showStatus（旧 :88-91）
        if (context.isEmpty()) {
            showStatus();
            return;
        }
        switch (context.arg(0)) {
            case "状态" -> showStatus();
            case "设置" -> bindPoint(context);
            case "移除" -> unbindPoint(context);
            case "清空" -> clearAll();
            default -> {
                // 旧项目是 Brigadier 解析失败，没有对应文案；此处沿用本项目其它指令的未知子命令提示
                context.error("未知子命令：" + context.arg(0));
                context.usage(usage());
            }
        }
    }

    /**
     * 参数补全。
     *
     * <p>旧项目全部是 Brigadier literal（无 {@code .suggests}），literal 自身即候选：根节点列
     * {@code 状态 / 设置 / 移除 / 清空 / 检测假矿}，{@code 设置} 与 {@code 移除} 下列三个点位名。
     * 本项目补全属框架能力（{@link ClientCommand#complete}），候选只能是旧项目真实存在的字面量；
     * 其中 {@code 检测假矿} 随种子模式留白，故不出现在候选里。内部数据键
     * {@code mineral / food / afk} 不参与补全（旧项目亦然）。</p>
     */
    @Override
    public List<String> complete(CommandContext context) {
        if (context.isEmpty()) return SUBCOMMANDS;
        if (context.size() == 1 && ("设置".equals(context.arg(0)) || "移除".equals(context.arg(0)))) {
            return POINT_NAMES;
        }
        return List.of();
    }

    // ── 子命令实现 ──

    /** {@code .wk 设置 <点位>}：全部校验与播报在 {@link com.yiyiaddon.feature.mining.service.MiningBindingService} */
    private void bindPoint(CommandContext context) {
        MiningPointType type = pointTypeOf(context);
        if (type == null) return;
        AutoMinerModule module = module();
        // 模块未注册时无绑定数据可写，静默返回（旧 :593 的兜底文案属「检测假矿」，随该子命令留白）
        if (module == null) return;
        module.bindingService().bind(type, false);
    }

    /** {@code .wk 移除 <点位>}：解绑无绑定的 {@code §6该坐标本来就没有绑定} 由 service 给出 */
    private void unbindPoint(CommandContext context) {
        MiningPointType type = pointTypeOf(context);
        if (type == null) return;
        AutoMinerModule module = module();
        if (module == null) return;
        module.bindingService().remove(type, false);
    }

    /** {@code .wk 清空}：整块清空全部绑定（旧 {@code :303-320}） */
    private void clearAll() {
        AutoMinerModule module = module();
        if (module == null) return;

        MiningPointStore store = module.pointStore();
        // 无绑定时不落盘、不播报标题（旧 :304-308 的提前返回）
        if (store.size() == 0) {
            CommandMessageFormatter.sendLine(MODULE_NAME, "§6当前没有任何绑定");
            return;
        }

        int count = store.clearAll();
        CommandMessageFormatter.of(MODULE_NAME, "已清空全部绑定")
            .world()
            .field("范围", String.join(" + ", POINT_NAMES))
            .field("数量", count + " 个")
            .status(CommandMessageFormatter.Level.SUCCESS, "已清空")
            .send();
    }

    // ── 状态显示（旧 {@code :326-336} + {@code :349-365}） ──

    /** {@code .wk} / {@code .wk 状态}：标题 + 三条点位字段 + 绑定位数 */
    private void showStatus() {
        AutoMinerModule module = module();
        if (module == null) return;

        MiningPointStore store = module.pointStore();
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "坐标绑定状态").world();
        appendBinding(formatter, store, MiningPointType.MINERAL);
        appendBinding(formatter, store, MiningPointType.FOOD);
        appendBinding(formatter, store, MiningPointType.AFK);
        // 计数口径与旧项目一致：DATA_STORE.size()（三个 key 里已绑的条数）
        formatter.status(CommandMessageFormatter.Level.INFO, store.size() + " / 3 已绑定");
        formatter.send();
    }

    /**
     * 追加单个点位字段：未绑定 {@code §8未绑定}；已绑定为
     * {@code §7X§f{x} §7Y§f{y} §7Z§f{z} §8▸ §b{维度名}}，玩家已加载时追加 {@code §8▸ §e{%.0f}m}。
     *
     * <p>维度名取 {@link WorldContextFormatter#dimensionSummary(String)}（旧 {@code WKData.dimensionName}
     * 就是它），距离按玩家脚下方块坐标算（旧 {@code :356-363}）。</p>
     */
    private void appendBinding(CommandMessageFormatter formatter, MiningPointStore store, MiningPointType type) {
        MiningPoint point = store.get(type);
        if (point == null) {
            formatter.field(type.displayName(), "§8未绑定");
            return;
        }
        StringBuilder value = new StringBuilder("§7X§f").append(point.x())
            .append(" §7Y§f").append(point.y())
            .append(" §7Z§f").append(point.z())
            .append(" §8▸ §b").append(WorldContextFormatter.dimensionSummary(point.dimension()));
        if (mc.player != null) {
            double distance = Math.sqrt(mc.player.blockPosition().distSqr(point.pos()));
            value.append(" §8▸ §e").append(String.format("%.0f", distance)).append("m");
        }
        formatter.field(type.displayName(), value.toString());
    }

    // ── 解析辅助 ──

    /** 点位中文字面量 → 点位类型（旧 {@code :286-291} 的 switch，字面量统一取自枚举中文名） */
    private static MiningPointType pointTypeOf(String zh) {
        if (zh == null) return null;
        for (MiningPointType type : MiningPointType.values()) {
            if (type.displayName().equals(zh)) return type;
        }
        return null;
    }

    /** 取点位字面量并给出参数提示；字面量非法返回 {@code null}（旧 Brigadier 在此处是解析失败） */
    private MiningPointType pointTypeOf(CommandContext context) {
        String input = context.arg(1);
        if (input == null) {
            context.usage(usage());
            return null;
        }
        MiningPointType type = pointTypeOf(input);
        if (type == null) {
            context.error("未知点位：" + input);
            context.usage(usage());
        }
        return type;
    }

    /** 实例所在模块：与 AutoChestCommand / StardewCommand 同一套取实例方式，不自造全局单例 */
    private static AutoMinerModule module() {
        return ModuleManager.byId(AutoMinerModule.MODULE_ID) instanceof AutoMinerModule mining ? mining : null;
    }
}
