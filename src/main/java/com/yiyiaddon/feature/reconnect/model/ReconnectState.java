package com.yiyiaddon.feature.reconnect.model;

/**
 * 「自动重连」的运行状态（显示用，共四态）。
 *
 * <p><b>状态怎么来</b>：不额外维护一套状态机，而是由模块按「引擎是否已调度 + 是否已在服务器里」直接判定
 * —— 判据只有引擎那一份（第 169 条）。因此界面显示的永远是真实调度状态，不存在状态与调度不一致的中间态。</p>
 *
 * <p><b>映射规则</b>：</p>
 * <ol>
 *     <li>引擎有待执行的重连 → {@link #WAITING}（概览页显示剩余秒数）；</li>
 *     <li>没有待执行的、且从没发起过重连，但玩家在服务器里 → {@link #READY}；</li>
 *     <li>连接动作已在飞（等待倒计时结束、连接界面出现，玩家实体还没到） → {@link #CONNECTING}；</li>
 *     <li>其余（未进服 / 从未记录服务器 / 已放弃） → {@link #IDLE}。</li>
 * </ol>
 */
public enum ReconnectState {

    IDLE("§8空闲（未连接）"),
    WAITING("§e等待重连"),
    CONNECTING("§a正在连接..."),
    READY("§a已就绪");

    private final String title;

    ReconnectState(String title) {
        this.title = title;
    }

    /** 显示文案（带状态色） */
    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
