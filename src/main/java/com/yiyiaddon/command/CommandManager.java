package com.yiyiaddon.command;

import com.yiyiaddon.config.AddonConfig;
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
 * <p>拦截方式是 Fabric 的聊天发送事件，不注入原版界面：以 {@link #prefix()} 开头的消息在客户端被
 * 拦下并本地执行，不会发送到服务端。未知指令名同样被拦下并给出中文提示，避免误发到服务器。</p>
 *
 * <p>补全由 {@link #applyTabCompletion} 提供，聊天框内按 Tab 生效；补全候选按「指令名 →
 * 该指令自己声明的参数候选」两级展开。</p>
 *
 * <p>前缀可在「设置」页修改并持久化到 {@code AddonConfig}；非法值（空、以 {@code /} 开头、
 * 含空白或颜色码、超长）一律回落到 {@link #DEFAULT_PREFIX}，保证指令始终可用。</p>
 */
public final class CommandManager {

    /** 默认指令前缀，与传统客户端模组保持一致的 {@code .} */
    public static final String DEFAULT_PREFIX = ".";

    /** 前缀固定为单个字符 */
    private static final int MAX_PREFIX_LENGTH = 1;

    /** 框架级回执前缀（指令框架自身提示的归属功能名） */
    public static final String FRAMEWORK_PREFIX = "帮助";

    /** 单次补全最多展示的候选数量 */
    private static final int MAX_PRINTED_CANDIDATES = 30;

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/command");

    private static boolean bootstrapped;

    private static volatile String prefixCache = DEFAULT_PREFIX;
    private static volatile String prefixCacheSource;

    private CommandManager() {
    }

    /**
     * 当前生效的指令前缀。
     *
     * <p>取值为设置页保存的原始文本经 {@link #normalizePrefix} 规范化后的结果；带原始值缓存，
     * 避免聊天拦截与补全每次输入都重新规范化。</p>
     */
    public static String prefix() {
        String raw = AddonConfig.commandPrefix;
        if (raw != null && raw.equals(prefixCacheSource)) return prefixCache;
        String normalized = normalizePrefix(raw);
        prefixCache = normalized;
        prefixCacheSource = raw;
        return normalized;
    }

    /**
     * 规范化指令前缀。
     *
     * <p>合法条件：去空白后非空、不以 {@code /} 开头（该前缀被原版指令占用，聊天事件不会送达）、
     * 不含空白与颜色码、字符数不超过 {@value #MAX_PREFIX_LENGTH}。任一不满足即回落到默认前缀。</p>
     */
    public static String normalizePrefix(String raw) {
        if (raw == null) return DEFAULT_PREFIX;
        String trimmed = raw.strip();
        if (trimmed.isEmpty()) return DEFAULT_PREFIX;
        if (trimmed.charAt(0) == '/') return DEFAULT_PREFIX;
        if (trimmed.codePointCount(0, trimmed.length()) > MAX_PREFIX_LENGTH) return DEFAULT_PREFIX;
        for (int i = 0; i < trimmed.length(); i++) {
            char ch = trimmed.charAt(i);
            if (ch <= ' ' || ch == '§' || ch == '\u007F') return DEFAULT_PREFIX;
        }
        return trimmed;
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
        return message != null && message.startsWith(prefix());
    }

    /**
     * 执行一条客户端指令。
     *
     * @return 是否已被本模组消费（返回 true 时调用方必须拦截该消息，不再发给服务端）
     */
    public static boolean handle(String message) {
        if (!isCommand(message)) return false;

        String prefix = prefix();
        String body = message.substring(prefix.length()).strip();
        if (body.isEmpty()) {
            ClientChat.send(FRAMEWORK_PREFIX, "客户端指令共 " + CommandRegistry.count() + " 个，输入 " + prefix + "help 查看全部");
            return true;
        }

        String[] tokens = body.split("\\s+");
        ClientCommand command = CommandRegistry.find(tokens[0]);
        if (command == null) {
            ClientChat.send(FRAMEWORK_PREFIX, "§c未知客户端指令：" + tokens[0] + "（输入 " + prefix + "help 查看全部指令）");
            return true;
        }

        CommandContext context = command.context(Arrays.copyOfRange(tokens, 1, tokens.length));
        try {
            command.execute(context);
        } catch (Throwable error) {
            LOGGER.error("指令 {} 执行异常", command.name(), error);
            ClientChat.send(FRAMEWORK_PREFIX, "§c指令 " + prefix + command.name() + " 执行异常："
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
        String prefix = prefix();
        if (input == null || !input.startsWith(prefix)) return input;

        String body = input.substring(prefix.length());
        boolean trailingSpace = !body.isEmpty() && body.charAt(body.length() - 1) == ' ';
        String trimmed = body.strip();
        if (trimmed.isEmpty()) {
            ClientChat.send(FRAMEWORK_PREFIX, "客户端指令共 " + CommandRegistry.count() + " 个，输入 " + prefix + "help 查看全部");
            return input;
        }

        String[] tokens = trimmed.split("\\s+");
        if (tokens.length == 1 && !trailingSpace) {
            return applyCommandNameCompletion(tokens[0], input, prefix);
        }

        ClientCommand command = CommandRegistry.find(tokens[0]);
        if (command == null) return input;

        String currentWord = trailingSpace ? "" : tokens[tokens.length - 1];
        int completed = trailingSpace ? tokens.length : tokens.length - 1;
        String[] args = Arrays.copyOfRange(tokens, 1, Math.max(1, completed));

        List<String> candidates = safeComplete(command, command.context(args), currentWord);
        return applyCandidates(candidates, currentWord, tokens, completed, input, prefix);
    }

    /** 指令名补全 */
    private static String applyCommandNameCompletion(String token, String input, String prefix) {
        List<String> candidates = filter(CommandRegistry.allNames(), token);
        if (candidates.isEmpty()) return input;
        if (candidates.size() == 1) return prefix + candidates.get(0) + " ";

        String common = commonPrefix(candidates);
        if (common.length() > token.length()) return prefix + common;
        printCandidates(candidates);
        return input;
    }

    /** 参数补全：唯一候选直接采用，多候选取公共前缀，无法推进时列出候选 */
    private static String applyCandidates(List<String> candidates, String currentWord, String[] tokens,
                                          int completed, String input, String prefix) {
        if (candidates == null || candidates.isEmpty()) return input;
        if (candidates.size() == 1) {
            return buildInput(tokens, completed, candidates.get(0), prefix);
        }
        String common = commonPrefix(candidates);
        if (common.length() > currentWord.length()) {
            return buildInput(tokens, completed, common, prefix);
        }
        printCandidates(candidates);
        return input;
    }

    private static String buildInput(String[] tokens, int completed, String last, String prefix) {
        StringBuilder builder = new StringBuilder(prefix);
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
        ClientChat.send(FRAMEWORK_PREFIX, text.toString());
    }
}
