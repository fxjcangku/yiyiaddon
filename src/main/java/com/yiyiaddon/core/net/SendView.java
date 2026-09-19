package com.yiyiaddon.core.net;

import net.minecraft.core.BlockPos;

/**
 * 发包视图：模块判据能看到的**全部**信息，是一个不可变的只读快照。
 *
 * <p><b>为什么要有这一层：</b>旧框架把待发的数据包对象直接交给模块，模块既能读字段也能改写包体。
 * 新架构明令禁止模块持有游戏对象（包引用一旦外流，模块就能长期持有、回写、甚至把包存下来跨线程用）。
 * 因此核心在发包链路上把「判据所需的字段」抽成视图交给模块，模块只能返回「怎么处置」，
 * 真正的取消 / 改写 / 延迟全部由核心执行。</p>
 *
 * <p><b>线程：</b>{@link SendRule#decide(SendView)} 在**网络线程**上同步调用（包必须在写出之前决定
 * 是否放行）。视图对象在调用期内有效，规则不得把视图或其字段保存到别处。</p>
 *
 * @param kind      包类别（决定其余字段是否有意义）
 * @param text      聊天原文（仅 {@link Kind#CHAT}）
 * @param detail    细分类别名：方块动作名 / 自定义负载频道 id / 资源包响应名 / 指令动作名
 * @param pos       方块坐标（仅 {@link Kind#PLAYER_ACTION} 与 {@link Kind#USE_ITEM_ON}）
 * @param inputFlags 玩家输入标志位组合（仅 {@link Kind#PLAYER_INPUT}，见 {@code INPUT_*} 常量）
 */
public record SendView(Kind kind, String text, String detail, BlockPos pos, int inputFlags) {

    /** 包类别：只覆盖需要判据的包，其余一律 {@link #OTHER} */
    public enum Kind {
        /** 聊天消息 */
        CHAT,
        /** 玩家输入（按键标志） */
        PLAYER_INPUT,
        /** 自定义负载（Brand / 频道注册等） */
        CUSTOM_PAYLOAD,
        /** 玩家方块动作（开始/停止/中止破坏、丢弃物品等） */
        PLAYER_ACTION,
        /** 使用物品 */
        USE_ITEM,
        /** 对方块使用物品 */
        USE_ITEM_ON,
        /** 玩家位置 */
        MOVE_PLAYER,
        /** 载具位置 */
        MOVE_VEHICLE,
        /** 心跳 */
        KEEP_ALIVE,
        /** 传送确认 */
        ACCEPT_TELEPORT,
        /** 能力同步（飞行态） */
        ABILITIES,
        /** 玩家指令动作（起伞等） */
        PLAYER_COMMAND,
        /** 资源包响应 */
        RESOURCE_PACK,
        /** 其余包 */
        OTHER
    }

    // ── 玩家输入标志位（与旧项目 Input 的七个字段一一对应） ──

    public static final int INPUT_FORWARD = 1;
    public static final int INPUT_BACKWARD = 1 << 1;
    public static final int INPUT_LEFT = 1 << 2;
    public static final int INPUT_RIGHT = 1 << 3;
    public static final int INPUT_JUMP = 1 << 4;
    public static final int INPUT_SHIFT = 1 << 5;
    public static final int INPUT_SPRINT = 1 << 6;

    /** 该标志位是否被按下 */
    public boolean hasInput(int flag) {
        return (inputFlags & flag) != 0;
    }

    /** 是否按下了任一移动方向键（前后左右） */
    public boolean hasDirectionInput() {
        return hasInput(INPUT_FORWARD) || hasInput(INPUT_BACKWARD)
            || hasInput(INPUT_LEFT) || hasInput(INPUT_RIGHT);
    }

    // ── 工厂 ──

    static SendView of(Kind kind) {
        return new SendView(kind, "", "", null, 0);
    }

    static SendView chat(String message) {
        return new SendView(Kind.CHAT, message == null ? "" : message, "", null, 0);
    }

    static SendView input(int flags) {
        return new SendView(Kind.PLAYER_INPUT, "", "", null, flags);
    }

    static SendView payload(String channelId) {
        return new SendView(Kind.CUSTOM_PAYLOAD, "", channelId == null ? "" : channelId, null, 0);
    }

    static SendView action(String actionName, BlockPos pos) {
        return new SendView(Kind.PLAYER_ACTION, "", actionName == null ? "" : actionName, pos, 0);
    }

    static SendView useItemOn(BlockPos pos) {
        return new SendView(Kind.USE_ITEM_ON, "", "", pos, 0);
    }

    static SendView detail(Kind kind, String detail) {
        return new SendView(kind, "", detail == null ? "" : detail, null, 0);
    }
}
