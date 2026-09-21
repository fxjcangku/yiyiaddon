package com.yiyiaddon.command;

import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.service.ChatService;
import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * 内置指令：回复管理员从后台发来的消息。
 *
 * <p>{@code .回复 <内容>} 与 {@code .聊天 回复 <玩家> <内容>} 不是一回事：前者固定发给管理员
 * （后端 {@code /api/messages/reply}，落库时 {@code target_uuid = __ADMIN__}），后者是玩家之间的私信。
 * 这条指令与 {@link ChatCommand} 一起，补上了旧项目 {@code commands/ReplyAdminCommand} 的等价物 ——
 * 缺了它，聊天栏里那句「使用 .回复 &lt;内容&gt; 回复」指向的就是一个不存在的指令。</p>
 *
 * <p>旧项目命令里裸开线程发 HTTP，这里换成 {@link BackgroundTasks} 的守护线程，
 * 回到主线程再播报（{@link CommandMessageFormatter} 的标签对齐要用游戏字体测量）。</p>
 */
public final class ReplyCommand extends ClientCommand {

    /** 回执前缀使用的功能名（逐字 = 旧项目 MODULE_NAME） */
    private static final String MODULE_NAME = "管理员消息";

    @Override
    public String name() {
        return "reply";
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public List<String> aliases() {
        return List.of("回复");
    }

    @Override
    public String description() {
        return "回复管理员从后台发来的消息";
    }

    @Override
    public String usage() {
        return CommandManager.prefix() + "回复 <内容>";
    }

    @Override
    public void execute(CommandContext context) {
        // 旧项目靠 Brigadier 的 greedyString 保证「至少一个字符」；扁平指令系统要在自己这里补上这个下界。
        String text = context.join(0).trim();
        if (text.isEmpty()) {
            context.usage(usage());
            return;
        }
        BackgroundTasks.run("yiyiaddon-admin-reply", () -> {
            boolean success = ChatService.reply(text);
            Minecraft.getInstance().execute(() -> {
                CommandMessageFormatter formatter = CommandMessageFormatter
                        .of(MODULE_NAME, success ? "回复已发送" : "回复发送失败")
                        .field("对象", "管理员");
                if (!success) formatter.field("原因", "服务未接受回复请求");
                formatter.status(success ? CommandMessageFormatter.Level.SUCCESS
                        : CommandMessageFormatter.Level.FAILURE, success ? "已发送" : "未发送").send();
            });
        });
    }
}
