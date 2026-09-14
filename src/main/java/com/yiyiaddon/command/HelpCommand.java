package com.yiyiaddon.command;

import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.CommandMessageFormatter;

import java.util.List;

/**
 * 内置指令：列出全部客户端指令，或查看单个指令的别名与用法。
 *
 * <p>回执走旧项目的统一卡片排版（标题 + {@code §8▸} 字段 + 状态行），不自行拼字符串。</p>
 */
public final class HelpCommand extends ClientCommand {

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String prefixName() {
        return "帮助";
    }

    @Override
    public List<String> aliases() {
        return List.of("h", "帮助");
    }

    @Override
    public String description() {
        return "列出全部客户端指令，或查看指定指令的用法";
    }

    @Override
    public String usage() {
        return CommandManager.PREFIX + "help [指令名]";
    }

    @Override
    public void execute(CommandContext context) {
        if (context.isEmpty()) {
            printAll();
            return;
        }
        ClientCommand command = CommandRegistry.find(context.arg(0));
        if (command == null) {
            context.error("未找到指令：" + context.arg(0));
            return;
        }
        CommandMessageFormatter.of(prefixName(), CommandManager.PREFIX + command.name())
                .field("别名", command.aliases().isEmpty() ? "无" : String.join("、", command.aliases()))
                .field("用法", command.usage())
                .field("说明", command.description())
                .status(CommandMessageFormatter.Level.INFO, "共 " + CommandRegistry.count() + " 条指令")
                .send();
    }

    @Override
    public List<String> complete(CommandContext context) {
        return CommandRegistry.allNames();
    }

    private void printAll() {
        List<ClientCommand> commands = CommandRegistry.all();
        CommandMessageFormatter formatter = CommandMessageFormatter.of(prefixName(), "客户端指令");
        for (ClientCommand command : commands) {
            StringBuilder label = new StringBuilder(CommandManager.PREFIX).append(command.name());
            if (!command.aliases().isEmpty()) {
                label.append("（").append(String.join("、", command.aliases())).append("）");
            }
            formatter.field(label.toString(), "§f" + command.description());
        }
        formatter.status(CommandMessageFormatter.Level.INFO,
                "共 " + commands.size() + " 个（前缀 " + CommandManager.PREFIX + "）").send();
        ClientChat.send(prefixName(), "§7输入 " + CommandManager.PREFIX + "help 指令名 查看单条用法");
    }
}
