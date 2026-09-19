package com.yiyiaddon.core.event;

/**
 * 客户端统一事件类型。
 *
 * <p>全部事件由 {@link EventDispatcher} 从唯一入口派发，业务模块不直接注册任何 Fabric 事件，
 * 避免出现「谁先注册谁先收到」的顺序不确定问题。</p>
 *
 * <p>各类型的载荷约定：</p>
 * <ul>
 *     <li>{@link #TICK}：载荷为 {@code Minecraft} 实例；</li>
 *     <li>{@link #JOIN_SERVER} / {@link #DISCONNECT}：无载荷；</li>
 *     <li>{@link #PACKET_RECEIVE} / {@link #PACKET_SEND}：载荷为数据包类名（字符串，不携带包对象）；</li>
 *     <li>{@link #SERVER_TEXT}：载荷为 {@link ServerTextEvent}（抽取后的只读文本，仍不携带包对象）；</li>
 *     <li>{@link #SERVER_POSITION}：载荷为 {@link ServerPositionEvent}（换算后的只读绝对坐标，仍不携带包对象）；</li>
 *     <li>{@link #SERVER_BLOCK}：载荷为 {@link ServerBlockEvent}（只读方块反馈快照，仍不携带包对象）；</li>
 *     <li>{@link #SERVER_CHANNEL}：载荷为插件频道 id（字符串）；</li>
 *     <li>{@link #CLIENT_COMMAND}：载荷为指令原文（字符串，不含前导斜杠，已在主线程派发时抽出）；</li>
 *     <li>{@link #CLIENT_CHAT}：载荷为聊天原文（字符串）；</li>
 *     <li>{@link #SCREEN_OPEN} / {@link #SCREEN_CLOSE}：载荷为界面类名（字符串）。</li>
 * </ul>
 *
 * <p>界面与数据包事件只携带类名：包的原始对象在到达客户端后会立即被消费，长期持有引用
 * 既没有意义，也会让模块误以为可以回写。确有读文本需求的模块改订阅 {@link #SERVER_TEXT}，
 * 由核心在派发前抽出文本、派发后立即丢弃包引用。</p>
 */
public enum ClientEventType {

    /** 每客户端刻 */
    TICK("每刻"),

    /** 进入服务器或单人世界 */
    JOIN_SERVER("进入世界"),

    /** 断开连接 */
    DISCONNECT("断开连接"),

    /** 收到数据包 */
    PACKET_RECEIVE("收到数据包"),

    /** 发出数据包 */
    PACKET_SEND("发出数据包"),

    /** 收到服务器文本（标题 / 动作栏 / 聊天 / 玩家列表 / 记分板 / BOSS 栏的原文） */
    SERVER_TEXT("服务器文本"),

    /** 收到服务端权威位置（玩家 / 载具位置纠正包，换算为绝对坐标后派发） */
    SERVER_POSITION("服务端位置"),

    /** 收到方块权威反馈（序列号确认 / 关注坐标的方块状态更新，见 {@code BlockWatchService}） */
    SERVER_BLOCK("方块反馈"),

    /** 收到插件自定义负载（频道 id；原版 brand/register/unregister 已在核心侧剔除） */
    SERVER_CHANNEL("插件频道"),

    /** 客户端发出服务器指令（聊天栏敲的 /xxx 原文，不含前导斜杠） */
    CLIENT_COMMAND("发出指令"),

    /** 客户端发出聊天消息（原文；被发包闸门拦下的消息同样在此交接，供模块排队重发） */
    CLIENT_CHAT("发出聊天"),

    /** 打开界面 */
    SCREEN_OPEN("打开界面"),

    /** 关闭界面 */
    SCREEN_CLOSE("关闭界面");

    private final String displayName;

    ClientEventType(String displayName) {
        this.displayName = displayName;
    }

    /** 中文名，用于指令与播报 */
    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
