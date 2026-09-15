package com.yiyiaddon.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

/**
 * 客户端指令的原版补全桥：把自研指令的候选交给原版补全组件（输入框上方那个候选弹窗）。
 *
 * <p><b>为什么要过 Brigadier：</b>聊天框的 Tab 由原版补全组件处理，它只认 Brigadier 的
 * {@code ParseResults} 与 {@code Suggestions}。因此这里为「本轮输入」临时搭一棵只用于补全的命令树，
 * 让原版按它自己的逻辑列候选、翻候选、把候选写回输入框——补全的显示与按键行为因此与原版
 * （以及旧项目所依赖的同一套机制）完全一致，不再往聊天框里打印任何东西。</p>
 *
 * <p><b>树形状由输入决定，所有词位都用整词参数 {@link #TOKEN}，一个字面量都不用：</b></p>
 * <ul>
 *   <li>已完整输入的 token 原样串成一条参数链（参数不校验内容，原样即必然解析通过）；</li>
 *   <li>正在输入的那个词挂在链尾，候选由指令自己的 {@link ClientCommand#complete} 给出并按已输入
 *       前缀过滤（不区分大小写）；过滤后为空即没有候选——原版据此不显示弹窗。</li>
 * </ul>
 *
 * <p><b>这棵树只用于补全，不承载任何执行逻辑：</b>所有节点都没有 {@code executes}，
 * 参数解析结果不会被任何人读取；指令的实际执行仍然只走 {@link CommandManager#handle}。</p>
 */
public final class ClientCommandSuggestions {

    /**
     * 补全树里唯一的参数类型：读到下一个空格（或结尾）为止的整词。
     *
     * <p>不能用字面量、也不能用 {@code StringArgumentType}：Brigadier 的字面量匹配与
     * {@code word()} / {@code string()} 都只认 ASCII 的 {@code [0-9A-Za-z_.+-]}，
     * {@code 控制台}、{@code 关}、{@code 清空} 这类中文词在里面一个字符都读不进去。
     * 一旦中文词被串进链里（补全补出尾随空格后就会发生），解析必然失败，
     * 原版随即把「错误的命令参数 / 参数后应有空格分隔」的红字挂到输入框上方。</p>
     *
     * <p>这里只按空格切词、不校验内容：中英文都能整词吞下，解析永不抛异常，红字因此不会出现。</p>
     */
    private static final ArgumentType<String> TOKEN = new ArgumentType<>() {
        @Override
        public String parse(StringReader reader) {
            int start = reader.getCursor();
            while (reader.canRead() && reader.peek() != ' ') reader.skip();
            return reader.getString().substring(start, reader.getCursor());
        }
    };

    /** 只用于补全的指令名参数名（不出现在用户可见文案里，原版提示区最多显示一次节点名） */
    private static final String NAME_ARGUMENT = "指令";

    /** 只用于补全的参数名，同上 */
    private static final String ARGUMENT = "参数";

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/command");

    private ClientCommandSuggestions() {
    }

    /** 本轮输入的一棵临时补全树与它的解析结果 */
    public record Plan(CommandDispatcher<ClientSuggestionProvider> dispatcher,
                       ParseResults<ClientSuggestionProvider> parse) {
    }

    /** 该输入是否由客户端指令接管（以配置的指令前缀开头） */
    public static boolean applies(String input) {
        return CommandManager.isCommand(input);
    }

    /**
     * 为本轮输入构造补全树。
     *
     * @param input 输入框全文（含前缀）
     */
    public static Plan plan(String input) {
        String prefix = CommandManager.prefix();
        String body = CommandManager.normalizeSeparators(input.substring(prefix.length()));
        boolean trailingSpace = !body.isEmpty() && body.charAt(body.length() - 1) == ' ';
        String trimmed = body.strip();

        List<String> chain = new ArrayList<>();
        List<String> candidates;
        if (trimmed.isEmpty()) {
            // 只输了前缀：列出全部指令名与英文别名
            candidates = CommandRegistry.allNames();
        } else {
            String[] tokens = trimmed.split("\\s+");
            if (tokens.length == 1 && !trailingSpace) {
                // 正在输入指令名：同样列全部名称与别名，由 Brigadier 按已输入前缀过滤
                candidates = CommandRegistry.allNames();
            } else {
                ClientCommand command = CommandRegistry.find(tokens[0]);
                int completed = trailingSpace ? tokens.length : tokens.length - 1;
                if (command == null) {
                    candidates = List.of();
                } else {
                    for (int i = 0; i < completed; i++) chain.add(tokens[i]);
                    candidates = safeComplete(command,
                            command.context(Arrays.copyOfRange(tokens, 1, Math.max(1, completed))));
                }
            }
        }
        return build(input, prefix, chain, candidates);
    }

    /**
     * 自底向上组装补全树：先备好链尾的候选节点，再从后往前挂父节点。
     *
     * <p>必须自底向上：Brigadier 的 {@code then(子构建器)} 会立刻把子节点 build 出来并放进父节点，
     * 之后再往那个子构建器上挂东西就再也进不了最终的树（挂在了已建好的节点之外）。自顶向下拼链时，
     * 链尾的候选节点就是这么被丢掉的——表现为「补完子命令再按 Tab 什么都没有」，
     * 同时因为父节点没有子节点、原版用法条目为空，输入框上方还会冒出「错误的命令参数」红字。</p>
     */
    private static Plan build(String input, String prefix, List<String> chain, List<String> candidates) {
        CommandDispatcher<ClientSuggestionProvider> dispatcher = new CommandDispatcher<>();

        RequiredArgumentBuilder<ClientSuggestionProvider, String> node = token(
                chain.isEmpty() ? NAME_ARGUMENT : ARGUMENT, candidates);
        for (int i = chain.size() - 1; i >= 0; i--) {
            RequiredArgumentBuilder<ClientSuggestionProvider, String> parent = token(chain.get(i), List.of());
            parent.then(node);
            node = parent;
        }
        dispatcher.getRoot().addChild(node.build());

        // 归一化只换分隔符、长度不变，因此解析用的光标位置与真实输入完全对齐
        StringReader reader = new StringReader(CommandManager.normalizeSeparators(input));
        reader.setCursor(reader.getCursor() + prefix.length());
        return new Plan(dispatcher, dispatcher.parse(reader, source()));
    }

    /** 链上的一个整词参数；只有链尾那个带候选 */
    private static RequiredArgumentBuilder<ClientSuggestionProvider, String> token(String name, List<String> candidates) {
        return RequiredArgumentBuilder.<ClientSuggestionProvider, String>argument(name, TOKEN)
                .suggests((com.mojang.brigadier.context.CommandContext<ClientSuggestionProvider> ctx,
                           SuggestionsBuilder builder) -> suggest(builder, candidates));
    }

    /** 按已输入前缀过滤候选（不区分大小写），前缀为空时列出全部 */
    private static CompletableFuture<Suggestions> suggest(SuggestionsBuilder builder, List<String> candidates) {
        String typed = builder.getRemaining().toLowerCase(Locale.ROOT);
        for (String candidate : candidates) {
            if (candidate == null || candidate.isBlank()) continue;
            if (candidate.toLowerCase(Locale.ROOT).startsWith(typed)) builder.suggest(candidate);
        }
        return builder.buildFuture();
    }

    /** 指令自己的补全异常不影响输入：记为错误并当作无候选 */
    private static List<String> safeComplete(ClientCommand command, CommandContext context) {
        try {
            List<String> candidates = command.complete(context);
            return candidates == null ? List.of() : candidates;
        } catch (Throwable error) {
            LOGGER.error("指令 {} 补全异常", command.name(), error);
            return List.of();
        }
    }

    /** 补全解析只需要一个 source 占位：节点没有权限要求，且没有人会读取解析结果 */
    private static ClientSuggestionProvider source() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null || minecraft.getConnection() == null) return null;
        return minecraft.getConnection().getSuggestionsProvider();
    }
}
