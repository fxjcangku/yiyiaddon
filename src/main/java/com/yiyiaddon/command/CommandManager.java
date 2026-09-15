package com.yiyiaddon.command;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.core.ClientChat;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 客户端指令管理器：聊天输入拦截与指令派发。
 *
 * <p>拦截方式是 Fabric 的聊天发送事件，不注入原版界面：以 {@link #prefix()} 开头的消息在客户端被
 * 拦下并本地执行，不会发送到服务端。未知指令名同样被拦下并给出中文提示，避免误发到服务器。</p>
 *
 * <p>补全不在这里做：候选由各指令自己的 {@link ClientCommand#complete} 声明，聊天框的 Tab 与候选
 * 弹窗统一交给原版补全组件，桥接见 {@link ClientCommandSuggestions}。</p>
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
     * 把全角空格等非 ASCII 空白统一成半角空格，长度不变。
     *
     * <p>中文输入法下打出的分隔符常是 {@code U+3000}（全角空格）、{@code U+00A0} 等，若不归一化，
     * 「{@code .stardew　控制台}」会被当成一个参数——原版补全还会因此报「参数后应有空格分隔」。
     * 长度不变是为了让归一化后的字符串仍能按原光标位置解析补全。</p>
     */
    public static String normalizeSeparators(String text) {
        if (text == null || text.isEmpty()) return text;
        StringBuilder builder = null;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == ' ') continue;
            if (!Character.isWhitespace(ch) && !Character.isSpaceChar(ch) && ch != '\uFEFF') continue;
            if (builder == null) builder = new StringBuilder(text);
            builder.setCharAt(i, ' ');
        }
        return builder == null ? text : builder.toString();
    }

    /**
     * 执行一条客户端指令。
     *
     * @return 是否已被本模组消费（返回 true 时调用方必须拦截该消息，不再发给服务端）
     */
    public static boolean handle(String message) {
        if (!isCommand(message)) return false;

        String prefix = prefix();
        String body = normalizeSeparators(message.substring(prefix.length())).strip();
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
}
