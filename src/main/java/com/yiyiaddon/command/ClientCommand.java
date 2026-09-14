package com.yiyiaddon.command;

import java.util.List;

/**
 * 客户端指令基类。
 *
 * <p>自研指令系统不依赖原版命令树，也不需要服务端支持：整条指令在客户端拦截、在客户端执行，
 * 不产生任何网络流量。</p>
 *
 * <p>子类只实现自己的参数解析与业务调用；参数校验失败通过 {@link CommandContext#error} 给出中文
 * 提示并直接返回，抛出的异常由 {@link CommandManager} 统一兜底。</p>
 */
public abstract class ClientCommand {

    /** 指令名，不含前缀 */
    public abstract String name();

    /** 别名，可含中文；返回空列表表示无别名 */
    public List<String> aliases() {
        return List.of();
    }

    /** 功能说明 */
    public abstract String description();

    /** 用法示例，含前缀 */
    public String usage() {
        return CommandManager.PREFIX + name();
    }

    /** 执行 */
    public abstract void execute(CommandContext context);

    /**
     * 参数补全：返回当前位置的候选列表。
     *
     * @param context 已完成参数构成的上下文（不含正在输入的当前词）
     * @return 候选；无候选返回空列表
     */
    public List<String> complete(CommandContext context) {
        return List.of();
    }

    /** 全部可输入名称（主名 + 别名），用于指令名补全 */
    public List<String> allNames() {
        List<String> names = new java.util.ArrayList<>();
        names.add(name());
        names.addAll(aliases());
        return names;
    }
}
