package com.yiyiaddon.feature.mining.fsm;

/**
 * 挖矿状态机的 9 个状态。
 *
 * <p>逐字照旧项目 {@code mining/fsm/MinerFSM.java:1821-1841} 的内部枚举 {@code MinerState}；
 * {@link #cn()} 的中文名用于状态播报与调试显示，一个字都不许改。</p>
 *
 * <p>状态流转（旧项目类注释 {@code :37-41} 逐字）：</p>
 * <pre>
 * IDLE → GO_WILD → MINING → [UNLOADING/SUPPLY/REPAIR] → GO_WILD → MINING ...
 *
 * 死亡事件拦截：
 * ANY_STATE → DEATH_HANDLING → RESPAWN_WAIT → GO_WILD
 * </pre>
 */
public enum MinerState {

    IDLE("待机"),
    GO_WILD("前往野外"),
    MINING("采掘中"),
    UNLOADING("卸货中"),
    SUPPLY("补给中"),
    EATING("进食中"),
    REPAIR("修补中"),
    DEATH_HANDLING("死亡处理"),
    RESPAWN_WAIT("复活等待");

    private final String cn;

    MinerState(String cn) {
        this.cn = cn;
    }

    public String cn() {
        return cn;
    }
}
