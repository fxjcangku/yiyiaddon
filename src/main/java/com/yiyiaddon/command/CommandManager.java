package com.yiyiaddon.command;

import com.yiyiaddon.core.ClientChat;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 客户端指令管理器：聊天输入拦截、指令派发与参数补全。
 *
 * <p>拦截方式是 Fabric 的聊天发送事件，不注入原版界面：以 {@link #PREFIX} 开头的消息在客户端被
 * 拦下并本地执行，不会发送到服务端。未知指令名同样被拦下并给出中文提示，避免误发到服务器。</p>
 *
 * <p>补全由 {@link #applyTabCompletion} 提供，聊天框内按 Tab 生效；补全候选按「指令名 →
 * 该指令自己声明的参数候选」两级展开。</p>
 */
public final class CommandManager {

    /** 指令前缀，与传统客户端模组保持一致的 {@code .} */
    public static final String PREFIX = ".";

    /** 单次补全最多展示的候选数量 */
    private static final int MAX_PRINTED_CANDIDATES = 30;

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/command");

    private static boolean bootstrapped;

    private CommandManager() {
    }

    /** 注册内置指令并接管聊天输入；重复调用无效 */
    public static void bootstrap() {
        if (bootstrapped) return;
        bootstrapped = true;

        CommandRegistry.register(new HelpCommand());
        CommandRegistry.register(new ModuleCommand());

        ClientSendMessageEvents.ALLOW_CHAT.register(message -> !handle(message));
    }

    /** 是否为本模组的客户端指令 */
    public static boolean isCommand(String message) {
        return message != null && message.startsWith(PREFIX);
    }

    /**
     * 执行一条客户端指令。
     *
     * @return 是否已被本模组消费（返回 true 时调用方必须拦截该消息，不再发给服务端）
     */
    public static boolean handle(String message) {
        if (!isCommand(message)) return false;

        String body = message.substring(PREFIX.length()).strip();
        if (body.isEmpty()) {
            ClientChat.send("客户端指令共 " + CommandRegistry.count() + " 个，输入 " + PREFIX + "help 查看全部");
            return true;
        }

        String[] tokens = body.split("\\s+");
        ClientCommand command = CommandRegistry.find(tokens[0]);
        if (command == null) {
            ClientChat.send("§c未知客户端指令：" + tokens[0] + "（输入 " + PREFIX + "help 查看全部指令）");
            return true;
        }

        CommandContext context = new CommandContext(Arrays.copyOfRange(tokens, 1, tokens.length));
        try {
            command.execute(context);
        } catch (Throwable error) {
            LOGGER.error("指令 {} 执行异常", command.name(), error);
            ClientChat.send("§c指令 " + PREFIX + command.name() + " 执行异常："
                    + error.getClass().getSimpleName()
                    + (error.getMessage() == null ? "" : "：" + error.getMessage()));
        }
        return true;
    }

    // ── 补全 ──

    /**
     * 应用一次 Tab 补全。
     *
     * @param input 聊天框当前文本
     * @return 补全后的文本；无可补全内容时原样返回
     */
    public static String applyTabCompletion(String input) {
        if (input == null || !input.startsWith(PREFIX)) return input;

        String body = input.substring(PREFIX.length());
        boolean trailingSpace = !body.isEmpty() && body.charAt(body.length() - 1) == ' ';
        String trimmed = body.strip();
        if (trimmed.isEmpty()) {
            ClientChat.send("客户端指令共 " + CommandRegistry.count() + " 个，输入 " + PREFIX + "help 查看全部");
            return input;
        }

        String[] tokens = trimmed.split("\\s+");
        if (tokens.length == 1 && !trailingSpace) {
            return applyCommandNameCompletion(tokens[0], input);
        }

        ClientCommand command = CommandRegistry.find(tokens[0]);
        if (command == null) return input;

        String currentWord = trailingSpace ? "" : tokens[tokens.length - 1];
        int completed = trailingSpace ? tokens.length : tokens.length - 1;
        String[] args = Arrays.copyOfRange(tokens, 1, Math.max(1, completed));

        List<String> candidates = safeComplete(command, new CommandContext(args), currentWord);
        return applyCandidates(candidates, currentWord, tokens, completed, input);
    }

    /** 指令名补全 */
    private static String applyCommandNameCompletion(String token, String input) {
        List<String> candidates = filter(CommandRegistry.allNames(), token);
        if (candidates.isEmpty()) return input;
        if (candidates.size() == 1) return PREFIX + candidates.get(0) + " ";

        String common = commonPrefix(candidates);
        if (common.length() > token.length()) return PREFIX + common;
        printCandidates(candidates);
        return input;
    }

    /** 参数补全：唯一候选直接采用，多候选取公共前缀，无法推进时列出候选 */
    private static String applyCandidates(List<String> candidates, String currentWord, String[] tokens,
                                          int completed, String input) {
        if (candidates == null || candidates.isEmpty()) return input;
        if (candidates.size() == 1) {
            return buildInput(tokens, completed, candidates.get(0));
        }
        String common = commonPrefix(candidates);
        if (common.length() > currentWord.length()) {
            return buildInput(tokens, completed, common);
        }
        printCandidates(candidates);
        return input;
    }

    private static String buildInput(String[] tokens, int completed, String last) {
        StringBuilder builder = new StringBuilder(PREFIX);
        for (int i = 0; i < completed; i++) {
            builder.append(tokens[i]).append(' ');
        }
        builder.append(last).append(' ');
        return builder.toString();
    }

    private static List<String> safeComplete(ClientCommand command, CommandContext context, String currentWord) {
        try {
            return filter(command.complete(context), currentWord);
        } catch (Throwable error) {
            LOGGER.error("指令 {} 补全异常", command.name(), error);
            return List.of();
        }
    }

    /** 前缀匹配（不区分大小写）；无前缀命中时退回包含匹配 */
    private static List<String> filter(List<String> source, String prefix) {
        List<String> result = new ArrayList<>();
        if (source == null || source.isEmpty()) return result;
        String needle = prefix == null ? "" : prefix.toLowerCase(Locale.ROOT);
        for (String candidate : source) {
            if (candidate == null || candidate.isBlank()) continue;
            if (candidate.toLowerCase(Locale.ROOT).startsWith(needle)) result.add(candidate);
        }
        return result;
    }

    /** 列表公共前缀（不区分大小写则按首元素原样返回） */
    private static String commonPrefix(List<String> values) {
        String first = values.get(0);
        int length = first.length();
        for (String value : values) {
            int limit = Math.min(length, value.length());
            int index = 0;
            while (index < limit && Character.toLowerCase(first.charAt(index)) == Character.toLowerCase(value.charAt(index))) {
                index++;
            }
            length = index;
            if (length == 0) return "";
        }
        return first.substring(0, length);
    }

    private static void printCandidates(List<String> candidates) {
        int shown = Math.min(candidates.size(), MAX_PRINTED_CANDIDATES);
        StringBuilder text = new StringBuilder("§7补全候选（" + candidates.size() + " 项）：§f");
        text.append(String.join("§7，§f", candidates.subList(0, shown)));
        if (shown < candidates.size()) text.append("§7，…");
        ClientChat.send(text.toString());
    }
}
