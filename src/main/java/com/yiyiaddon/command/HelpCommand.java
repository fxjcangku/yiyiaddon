package com.yiyiaddon.command;

import com.yiyiaddon.core.ClientChat;

import java.util.List;

/**
 * 内置指令：列出全部客户端指令，或查看单个指令的别名与用法。
 */
public final class HelpCommand extends ClientCommand {

    @Override
    public String name() {
        return "help";
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
        List<String> aliases = command.aliases();
        ClientChat.send("§b" + CommandManager.PREFIX + command.name() + " §7" + command.description());
        ClientChat.send("§7别名：" + (aliases.isEmpty() ? "无" : String.join("、", aliases)));
        ClientChat.send("§7用法：" + command.usage());
    }

    @Override
    public List<String> complete(CommandContext context) {
        return CommandRegistry.allNames();
    }

    private void printAll() {
        List<ClientCommand> commands = CommandRegistry.all();
        ClientChat.send("§b客户端指令（共 " + commands.size() + " 个，前缀 " + CommandManager.PREFIX + "）");
        for (ClientCommand command : commands) {
            List<String> aliases = command.aliases();
            String aliasText = aliases.isEmpty() ? "" : "§8（" + String.join("、", aliases) + "）";
            ClientChat.send("§f" + CommandManager.PREFIX + command.name() + aliasText + " §7" + command.description());
        }
    }
}
