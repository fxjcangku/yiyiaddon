package com.yiyiaddon.command;

import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.service.ChatService;
import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * 内置指令：跨服聊天与在线玩家查询。
 *
 * <p>子命令：{@code 在线} 查在线名单、{@code 帮助} 看用法、{@code 说 <内容>} 发到在线频道、
 * {@code 私聊 <玩家> <内容>} 定向私信、{@code 回复 <玩家> <内容>} 与私聊同义但语义更清楚。</p>
 *
 * <p>文案与结构逐字继承旧项目 {@code commands/YiyiaddonChatCommand}（Meteor 的 Brigadier 命令树），
 * 差别只在参数解析：旧项目由 Brigadier 提供 {@code greedyString}，本项目是自研扁平指令系统
 * （{@link CommandContext} 按位置取参），所以「内容」由 {@link CommandContext#join(int)} 拼回。</p>
 *
 * <p>网络请求全部放在 {@link BackgroundTasks} 的守护线程上（线程名沿用旧项目），
 * 回到主线程再播报：{@link CommandMessageFormatter} 的标签对齐要用游戏字体测量，只能在主线程做。</p>
 */
public final class ChatCommand extends ClientCommand {

    /** 回执前缀使用的功能名（逐字 = 旧项目 MODULE_NAME） */
    private static final String MODULE_NAME = "聊天";

    private static final List<String> SUBCOMMANDS = List.of("在线", "帮助", "说", "私聊", "回复");

    @Override
    public String name() {
        return "chat";
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public List<String> aliases() {
        return List.of("聊天");
    }

    @Override
    public String description() {
        return "跨服聊天：查在线玩家、发频道消息与私聊";
    }

    @Override
    public String usage() {
        return CommandManager.prefix() + "聊天 <在线|帮助|说|私聊|回复>";
    }

    @Override
    public void execute(CommandContext context) {
        if (context.isEmpty()) {
            showHelp("聊天系统");
            return;
        }
        String action = context.arg(0);
        switch (action) {
            case "在线" -> listOnlineAsync();
            case "帮助" -> showHelp("聊天指令用法");
            case "说" -> sendAsync(context, null, context.join(1));
            case "私聊", "回复" -> {
                String target = context.arg(1);
                if (target == null || context.join(2).isBlank()) {
                    context.usage(usage());
                    return;
                }
                sendAsync(context, target, context.join(2));
            }
            default -> {
                context.error("未知子命令：" + action);
                context.usage(usage());
            }
        }
    }

    /**
     * 子命令补全。
     *
     * <p>与旧项目口径一致：Brigadier 里「玩家」「内容」是词/贪婪字符串节点，没有候选；
     * 本项目同样只在第一级给候选，越往后越不长出候选。</p>
     */
    @Override
    public List<String> complete(CommandContext context) {
        return context.isEmpty() ? SUBCOMMANDS : List.of();
    }

    /** 帮助播报：标题 + 字段，与全项目指令排版一致 */
    private void showHelp(String title) {
        CommandMessageFormatter.of(MODULE_NAME, title)
                .field("在线", CommandManager.prefix() + "聊天 在线 查看当前在线玩家")
                .field("帮助", CommandManager.prefix() + "聊天 帮助 查看全部指令")
                .field("说", CommandManager.prefix() + "聊天 说 <内容> 发送到在线频道")
                .field("私聊", CommandManager.prefix() + "聊天 私聊 <玩家> <内容> 发送私信")
                .field("回复", CommandManager.prefix() + "聊天 回复 <玩家> <内容> 回复玩家")
                .field("说明", "仅同步本扩展聊天内容，不读取服务器聊天或其他指令")
                .send();
    }

    /** 在后台线程请求在线名单，避免网络请求阻塞 Minecraft 主线程。 */
    private void listOnlineAsync() {
        BackgroundTasks.run("yiyiaddon-chat-online", () -> {
            List<String> names = ChatService.onlinePlayers();
            String joined = names.isEmpty() ? null : String.join("§8、§f", names);
            Minecraft.getInstance().execute(() -> CommandMessageFormatter.of(MODULE_NAME, "在线玩家")
                    .field("人数", names.size() + " 人")
                    .field("名单", joined == null ? "§8当前没有在线玩家" : "§f" + joined)
                    .status(CommandMessageFormatter.Level.SUCCESS, "查询完成")
                    .send());
        });
    }

    /** 发送频道消息或定向私信，并限制长度与空消息，降低刷屏和数据库膨胀风险。 */
    private void sendAsync(CommandContext context, String targetName, String message) {
        String text = message == null ? "" : message.trim();
        if (text.isEmpty() || text.length() > ChatService.MESSAGE_LIMIT) {
            context.error("消息不能为空且不能超过 " + ChatService.MESSAGE_LIMIT + " 个字符");
            return;
        }
        BackgroundTasks.run("yiyiaddon-chat-send", () -> {
            boolean success = ChatService.send(targetName, text);
            String targetLabel = targetName == null ? "在线频道" : targetName;
            Minecraft.getInstance().execute(() -> CommandMessageFormatter
                    .of(MODULE_NAME, success ? "消息已发送" : "消息发送失败")
                    .field("对象", targetLabel)
                    .status(success ? CommandMessageFormatter.Level.SUCCESS
                            : CommandMessageFormatter.Level.FAILURE, success ? "已发送" : "未发送，请稍后重试")
                    .send());
        });
    }
}
