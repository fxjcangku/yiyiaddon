package com.yiyiaddon.feature.teleport.command;

import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandContext;
import com.yiyiaddon.command.CommandManager;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.teleport.TeleportModule;

import java.util.List;

/**
 * 传送指令：{@code .tp X Y Z} 立即把玩家传送到指定坐标（当前维度，整数坐标）。
 * 目标不可站立时模块自动回退到邻近安全落点。
 *
 * <p>与旧项目 {@code TpCommand} 同一行为：三参数缺一不可；模块未开启时**不传送**，
 * 只给出「原因 / 未传送」回执（文案逐字，见 §3.4）。</p>
 */
public final class TpCommand extends ClientCommand {

    /** 回执前缀使用的功能名（与播报前缀同源，逐字 = 旧 {@code MODULE_NAME}） */
    private static final String MODULE_NAME = "传送";

    /** 模块未开启时的回执标题（逐字 = 旧 {@code TpCommand:36}） */
    private static final String TITLE_NOT_EXECUTED = "传送未执行";

    private final TeleportModule module;

    public TpCommand(TeleportModule module) {
        this.module = module;
    }

    @Override
    public String name() {
        return "tp";
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public String description() {
        return "传送到指定坐标（当前维度，整数坐标）。";
    }

    @Override
    public String usage() {
        return CommandManager.prefix() + "tp <x> <y> <z>";
    }

    @Override
    public void execute(CommandContext context) {
        Integer x = context.intArg(0);
        Integer y = context.intArg(1);
        Integer z = context.intArg(2);

        // 旧项目走 Brigadier 的 IntegerArgumentType，三个参数缺一不可
        if (x == null || y == null || z == null) {
            context.usage(usage());
            return;
        }

        if (!module.isEnabled()) {
            CommandMessageFormatter.of(MODULE_NAME, TITLE_NOT_EXECUTED)
                .coord(x, y, z)
                .field("原因", "请先开启传送模块")
                .status(CommandMessageFormatter.Level.FAILURE, "未传送")
                .send();
            return;
        }

        module.teleportToCoord(x, y, z);
    }

    @Override
    public List<String> complete(CommandContext context) {
        return List.of();
    }
}
