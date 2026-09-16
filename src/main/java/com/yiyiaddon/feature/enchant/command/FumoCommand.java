package com.yiyiaddon.feature.enchant.command;

import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandContext;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.feature.enchant.repository.EnchantPointStore;

import java.util.Arrays;
import java.util.List;

/**
 * 自动附魔指令 {@code .fumo}：为三种目标模式设置统一点位节点（旧 {@code command/FumoCommand}，292 行）。
 *
 * <p><b>用户交互资产（逐字，禁止改写）：</b>指令名 {@code fumo}、无别名、描述
 * {@code 自动附魔坐标设置指令}（旧 {@code :39}）；子命令字面量 {@code 设置} / {@code 移除} /
 * {@code 状态} / {@code 清空}；{@code 设置} 与 {@code 移除} 下各挂 9 个点位字面量
 * <b>{@code 书} / {@code 青晶石} / {@code 工具护甲箱} / {@code 附魔台} / {@code 砂轮} /
 * {@code 铁砧} / {@code 铁砧箱} / {@code 挂机位} / {@code 成品箱}</b>——
 * 节点名与点位显示名不同（{@code 青晶石}≠青金石箱、{@code 挂机位}≠挂机点、
 * {@code 工具护甲箱}≠工具/护甲箱），一律照旧，不得「顺手统一」。</p>
 *
 * <p><b>绑定 / 移除只有一处实现：</b>全部转调
 * {@link com.yiyiaddon.feature.enchant.service.EnchantBindingService}
 * （控制台「点位」页的按钮走同一处，旧 {@code FumoCommand.setPoint/removePoint:189-200} 亦然）。
 * 本类不复制任何一条校验，只负责指令树解析、状态回执与模块未就绪回执。</p>
 *
 * <p><b>与旧项目的一处必然差异：</b>旧 {@code .fumo} 根节点没有 {@code executes}（不挂
 * {@code 状态}），Brigadier 对「只有子节点、自身不可执行」的输入给的是解析失败；本项目指令框架
 * 没有 Brigadier 的报错通道，裸 {@code .fumo} 改用框架统一的用法提示（内容只有指令名，不新增文案）。</p>
 */
public final class FumoCommand extends ClientCommand {

    /** 回执前缀：旧 {@code FumoCommand.MODULE_NAME} 原文（旧 {@code :39}） */
    private static final String MODULE_NAME = EnchantModule.MESSAGE_MODULE;

    /** 根节点子命令，顺序照旧 {@code build:45-80} 的注册顺序 */
    private static final List<String> SUBCOMMANDS = List.of("设置", "移除", "状态", "清空");

    /** {@code 设置} / {@code 移除} 下的点位字面量（顺序即 {@link EnchantPointType} 声明顺序，与旧注册顺序一致） */
    private static final List<String> POINT_NODES = Arrays.stream(EnchantPointType.values())
        .map(EnchantPointType::node)
        .toList();

    @Override
    public String name() {
        return "fumo";
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public String description() {
        return "自动附魔坐标设置指令";
    }

    @Override
    public void execute(CommandContext context) {
        if (context.isEmpty()) {
            context.usage(usage());
            return;
        }
        // 指令没有「打开界面」这种天然的重读时机，入口先按当前服务器装载点位：
        // 不装载会读到空表（状态显示未设置、覆盖保护失效、清空清不动）
        EnchantModule module = module();
        if (module != null) module.pointStore().reload();

        switch (context.arg(0)) {
            case "设置" -> setPoint(context);
            case "移除" -> removePoint(context);
            case "状态" -> printStatus();
            case "清空" -> clearPoints();
            default -> {
                // 旧项目此处是 Brigadier 解析失败，无对应文案；沿用本项目其它指令的未知子命令提示
                context.error("未知子命令：" + context.arg(0));
                context.usage(usage());
            }
        }
    }

    /**
     * 参数补全：旧项目全部是 Brigadier literal（无 {@code .suggests}），literal 自身即候选，
     * 根节点列四个子命令，{@code 设置} / {@code 移除} 下列九个点位节点。
     */
    @Override
    public List<String> complete(CommandContext context) {
        if (context.isEmpty()) return SUBCOMMANDS;
        if (context.size() == 1 && ("设置".equals(context.arg(0)) || "移除".equals(context.arg(0)))) {
            return POINT_NODES;
        }
        return List.of();
    }

    // ── 子命令实现 ──

    /** {@code .fumo 设置 <节点>}：全部校验与回执在 {@link com.yiyiaddon.feature.enchant.service.EnchantBindingService} */
    private void setPoint(CommandContext context) {
        EnchantPointType type = pointTypeOf(context);
        if (type == null) return;
        EnchantModule module = module();
        if (module == null) return;
        module.bindingService().bind(type);
    }

    /** {@code .fumo 移除 <节点>} */
    private void removePoint(CommandContext context) {
        EnchantPointType type = pointTypeOf(context);
        if (type == null) return;
        EnchantModule module = module();
        if (module == null) return;
        module.bindingService().remove(type);
    }

    /** {@code .fumo 清空} */
    private void clearPoints() {
        EnchantModule module = module();
        if (module == null) return;
        module.bindingService().clear();
    }

    /**
     * {@code .fumo 状态}：标题 {@code 点位状态} + <b>全部 9 个点位</b>（旧 :202-220 遍历的是
     * {@code PointType.all()}，不是只遍历当前模式所需）+ 末行世界匹配状态。
     *
     * <p>未绑定显示 {@code §c未设置}；已绑定显示 {@code §7X§f<x> §7Y§f<y> §7Z§f<z>}；
     * 末行判据是整套点位归属的服务器与维度是否与当前世界一致（旧 {@code matchesCurrentPointContext:1691-1693}）。</p>
     */
    private void printStatus() {
        EnchantModule module = module();
        if (module == null) return;

        EnchantPointStore store = module.pointStore();
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "点位状态").world();
        for (EnchantPointType type : EnchantPointType.values()) {
            EnchantPoint pos = store.get(type);
            formatter.field(type.title(), pos == null
                ? "§c未设置"
                : "§7X§f" + pos.x() + " §7Y§f" + pos.y() + " §7Z§f" + pos.z());
        }
        if (store.matchesCurrentContext()) {
            formatter.status(CommandMessageFormatter.Level.SUCCESS, "当前世界与点位匹配");
        } else {
            formatter.status(CommandMessageFormatter.Level.WARNING, "当前世界与点位不匹配");
        }
        formatter.send();
    }

    // ── 解析辅助 ──

    /**
     * 取点位节点字面量。
     *
     * <p>模块未注册的回执由 {@link #module()} 统一给出（旧 {@code getModule:224-228} 在四个分支里
     * 都会打印 {@code 模块未就绪}，因此这里先取模块再报参数错误）。</p>
     */
    private EnchantPointType pointTypeOf(CommandContext context) {
        String input = context.arg(1);
        if (input == null) {
            context.usage(usage());
            return null;
        }
        EnchantPointType type = EnchantPointType.ofNode(input);
        if (type == null) {
            context.error("未知点位：" + input);
            context.usage(usage());
        }
        return type;
    }

    /**
     * 实例所在模块；为 {@code null} 时打印 {@code 模块未就绪}（旧 {@code getModule}）。
     *
     * <p>与 {@code WkCommand} / {@code AutoChestCommand} 同一套取实例方式，不自造全局单例。</p>
     */
    private static EnchantModule module() {
        EnchantModule module = ModuleManager.byId(EnchantModule.MODULE_ID) instanceof EnchantModule enchant
            ? enchant : null;
        if (module == null) {
            CommandMessageFormatter.of(MODULE_NAME, "模块未就绪")
                .field("原因", "找不到 自动附魔 模块，请确认已注册")
                .status(CommandMessageFormatter.Level.FAILURE, "操作未完成")
                .send();
        }
        return module;
    }
}
