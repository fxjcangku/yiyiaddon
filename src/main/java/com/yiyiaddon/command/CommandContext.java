package com.yiyiaddon.command;

import com.yiyiaddon.core.ClientChat;

import java.util.Arrays;

/**
 * 指令执行上下文：已完成解析的参数与统一的中文回显出口。
 *
 * <p>不引入任何参数类型系统：参数按位置取出，缺参由指令自己判断并给出用法提示。整数解析等
 * 常见校验放在这里，避免每个指令重复写 try/catch。</p>
 */
public final class CommandContext {

    private final String[] args;

    public CommandContext(String[] args) {
        this.args = args == null ? new String[0] : args;
    }

    public String[] args() {
        return Arrays.copyOf(args, args.length);
    }

    public int size() {
        return args.length;
    }

    /** 按位置取参数；越界返回 {@code null} */
    public String arg(int index) {
        return index < 0 || index >= args.length ? null : args[index];
    }

    /** 从指定位置起拼接全部参数 */
    public String join(int from) {
        if (from < 0 || from >= args.length) return "";
        return String.join(" ", Arrays.copyOfRange(args, from, args.length));
    }

    /** 解析整数；非法返回 {@code null} */
    public Integer intArg(int index) {
        String value = arg(index);
        if (value == null) return null;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public boolean isEmpty() {
        return args.length == 0;
    }

    /** 普通提示 */
    public void reply(String text) {
        ClientChat.send(text);
    }

    /** 错误提示 */
    public void error(String text) {
        ClientChat.send("§c" + text);
    }

    /** 用法提示 */
    public void usage(String usage) {
        ClientChat.send("§e用法：" + usage);
    }
}
