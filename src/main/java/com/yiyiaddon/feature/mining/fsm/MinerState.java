package com.yiyiaddon.feature.mining.fsm;

/**
 * 挖矿状态机的 10 个状态。
 *
 * <p>前 9 个逐字照旧项目 {@code mining/fsm/MinerFSM.java:1821-1841} 的内部枚举 {@code MinerState}；
 * {@link #cn()} 的中文名用于状态播报与调试显示，一个字都不许改。</p>
 *
 * <p>第 10 个（{@link #COMBAT}）是用户 2026-09-17 新增需求带来的战斗拦截态：
 * 「发现被怪物攻击、或自身 6 格内扫到怪物 → 自动战斗，安全了再回去挖矿」，实现在 {@link MiningCombat}。
 * 2026-09-18 用户裁定「不躲了，苦力怕也直接打，只是控制好距离」——原来的 {@code EVADE}（撤离威胁）
 * 已删除，苦力怕改在 {@code COMBAT} 里用「打了就退」的走位处理，不再有独立躲避态。</p>
 *
 * <p>状态流转（旧项目类注释 {@code :37-41} 逐字 + 本项目战斗拦截）：</p>
 * <pre>
 * IDLE → GO_WILD → MINING → [UNLOADING/SUPPLY/REPAIR] → GO_WILD → MINING ...
 *
 * 战斗拦截（仅在 GO_WILD / MINING 触发）：
 * GO_WILD/MINING → COMBAT → MINING
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
    RESPAWN_WAIT("复活等待"),

    /** 战斗拦截：附近有怪物，自动切武器杀死它（苦力怕额外做「打了就退」的走位，见 MiningCombat） */
    COMBAT("战斗中");

    private final String cn;

    MinerState(String cn) {
        this.cn = cn;
    }

    public String cn() {
        return cn;
    }
}
