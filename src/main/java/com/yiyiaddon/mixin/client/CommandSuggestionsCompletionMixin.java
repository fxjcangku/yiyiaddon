package com.yiyiaddon.mixin.client;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.suggestion.Suggestions;
import com.yiyiaddon.command.ClientCommandSuggestions;
import com.yiyiaddon.command.CommandManager;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.util.FormattedCharSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 客户端指令的聊天框补全：输入以指令前缀开头时，把候选交给原版补全组件。
 *
 * <p><b>为什么挂这里：</b>原版补全组件只服务 {@code /} 开头的服务器指令，聊天框的 Tab、候选列表、
 * 候选写入全都由它统一处理。此处把「本轮输入的补全树」换成自研指令的（见
 * {@link ClientCommandSuggestions}），于是自研指令获得与原版完全一致的补全交互，且不再往聊天框
 * 打印任何候选文案；不是指令前缀开头的输入直接放行走原版（普通聊天的玩家名补全不受影响）。</p>
 *
 * <p><b>为什么自己重做原版在注入点之前的那几步：</b>注入点在 {@code updateCommandInfo} 的 HEAD，
 * 而原版是在「构造 reader」之后才进入补全流程；原版在此之前会做三件事——文本变化即丢弃上一轮
 * {@code currentParse}、清掉旧候选列表、清空提示区。跳到 HEAD 就必须照做，否则会拿上一次的解析
 * 结果去算新的补全（候选位置与内容都会错）。</p>
 *
 * <p><b>与 Stendhal 之类模组的关系：</b>有的模组会在 {@code ChatScreen#keyPressed} 的 HEAD 注入、
 * 先调用本组件、再按它的返回值就地取消整个方法。挂在聊天界面上的注入点会被那条路径跳过，
 * 挂在补全组件上则所有路径都必经此处。</p>
 */
@Mixin(CommandSuggestions.class)
public abstract class CommandSuggestionsCompletionMixin {

    @Shadow
    @Final
    private EditBox input;

    @Shadow
    @Final
    private List<FormattedCharSequence> commandUsage;

    @Shadow
    private ParseResults<ClientSuggestionProvider> currentParse;

    @Shadow
    private boolean currentParseIsCommand;

    @Shadow
    private boolean currentParseIsMessage;

    @Shadow
    private CompletableFuture<Suggestions> pendingSuggestions;

    @Shadow
    private CommandSuggestions.SuggestionsList suggestions;

    @Shadow
    private boolean keepSuggestions;

    @Shadow
    protected abstract void updateUsageInfo(ParseResults<ClientSuggestionProvider> currentParse, Suggestions suggestions);

    private static final Logger YIYIADDON$LOGGER = LoggerFactory.getLogger("yiyiaddon/command");

    /** 本轮补全树：解析结果存进 {@code currentParse} 供原版使用，树本体留着算候选 */
    @Unique
    private ClientCommandSuggestions.Plan yiyiaddon$plan;

    /** 越界现场只记录一次，避免每帧刷屏 */
    @Unique
    private boolean yiyiaddon$overflowReported;

    @Inject(method = "updateCommandInfo", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$suggestClientCommands(CallbackInfo info) {
        String text = input.getValue();
        if (!ClientCommandSuggestions.applies(text)) return;

        if (currentParse != null && !currentParse.getReader().getString().equals(text)) {
            currentParse = null;
            currentParseIsCommand = false;
            currentParseIsMessage = false;
        }
        if (!keepSuggestions) {
            input.setSuggestion(null);
            suggestions = null;
        }
        commandUsage.clear();

        yiyiaddon$plan = ClientCommandSuggestions.plan(text);
        currentParse = yiyiaddon$plan.parse();

        int cursor = input.getCursorPosition();
        if (cursor >= CommandManager.prefix().length() && (suggestions == null || !keepSuggestions)) {
            pendingSuggestions = yiyiaddon$plan.dispatcher().getCompletionSuggestions(currentParse, cursor);
            pendingSuggestions.thenAccept(result -> {
                if (pendingSuggestions.isDone()) updateUsageInfo(currentParse, result);
            });
        }
        info.cancel();
    }

    /**
     * 高亮渲染守卫：把原版 {@code formatText} 的隐含前提显式校验一遍，会越界就不渲染。
     *
     * <p>原版在切分输入框高亮时，直接做 {@code text.substring(unformattedStart, start)}，其中
     * {@code unformattedStart} 来自「参数的 range」、{@code start} 来自「reader 游标」。它隐含假定
     * 两者单调递增（参数范围都排在游标之前）。该前提一旦被破坏，原版会抛
     * {@code StringIndexOutOfBoundsException: Range [a, b) out of bounds} 把整个客户端崩掉。
     * 这里在渲染前照同一条切分路径预演位置：只要会越界，就取消这一次高亮渲染（返回 null，
     * 输入框退化为无高亮的纯文本，输入与补全本身完全不受影响），并留下一次现场日志。</p>
     */
    @Inject(method = "formatChat", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$guardUnparsedHighlight(String text, int offset,
                                                  CallbackInfoReturnable<FormattedCharSequence> info) {
        ParseResults<ClientSuggestionProvider> parse = currentParse;
        if (parse == null || !yiyiaddon$wouldOverflow(parse, text, offset)) return;

        info.setReturnValue(null);
        if (yiyiaddon$overflowReported) return;
        yiyiaddon$overflowReported = true;

        StringBuilder arguments = new StringBuilder();
        for (ParsedArgument<ClientSuggestionProvider, ?> argument
                : parse.getContext().getLastChild().getArguments().values()) {
            arguments.append(argument.getRange()).append(' ');
        }
        YIYIADDON$LOGGER.error("已拦截一次补全高亮越界：text=[{}] len={} offset={} reader={} cursor={} canRead={} 参数范围=[{}]",
                text, text.length(), offset, parse.getReader().getString().length(),
                parse.getReader().getCursor(), parse.getReader().canRead(), arguments.toString().trim());
    }

    /**
     * 预演原版 {@code formatText} 的高亮切分，只在真的会 substring 越界时返回 {@code true}。
     *
     * <p>算法与原版逐行对应，只是把 {@code substring} 换成位置比较，因此不会漏判也不会误判。</p>
     */
    @Unique
    private static boolean yiyiaddon$wouldOverflow(ParseResults<ClientSuggestionProvider> parse,
                                                   String text, int offset) {
        int unformattedStart = 0;
        for (ParsedArgument<ClientSuggestionProvider, ?> argument
                : parse.getContext().getLastChild().getArguments().values()) {
            int start = Math.max(argument.getRange().getStart() - offset, 0);
            if (start >= text.length()) break;
            int end = Math.min(argument.getRange().getEnd() - offset, text.length());
            if (end <= 0) continue;
            if (unformattedStart > start) return true;
            unformattedStart = end;
        }
        if (parse.getReader().canRead()) {
            int start = Math.max(parse.getReader().getCursor() - offset, 0);
            if (start < text.length() && unformattedStart > start) return true;
        }
        return false;
    }
}
