package com.yiyiaddon.feature.mining.fsm;

/**
 * 挖矿状态机的 14 个状态。
 *
 * <p>前 9 个逐字照旧项目 {@code mining/fsm/MinerFSM.java:1821-1841} 的内部枚举 {@code MinerState}；
 * {@link #cn()} 的中文名用于状态播报与调试显示，一个字都不许改。</p>
 *
 * <p>第 10 个（{@link #COMBAT}）是用户 2026-09-17 新增需求带来的战斗拦截态：
 * 「发现被怪物攻击、或自身 6 格内扫到怪物 → 自动战斗，安全了再回去挖矿」，实现在 {@link MiningCombat}。
 * 2026-09-18 用户裁定「不躲了，苦力怕也直接打，只是控制好距离」——原来的 {@code EVADE}（撤离威胁）
 * 已删除，苦力怕改在 {@code COMBAT} 里用「打了就退」的走位处理，不再有独立躲避态。</p>
 *
 * <p>第 11~14 个（{@link #SELL_TRAVEL} / {@link #SELL_PATH} / {@link #SELL_TRADE} /
 * {@link #SELL_RETURN}）是用户 2026-09-20 的「自用模式」需求：挖够触发组数（或背包先满）就
 * 自己去把矿卖掉再回来接着挖。四个态串成一条链，只在 {@code personalMode} 打开时会进入
 * （触发点在 {@code MiningStateMachine#tickMining}），走向是
 * {@code MINING → SELL_TRAVEL → SELL_PATH → SELL_TRADE → SELL_RETURN → GO_WILD}。</p>
 *
 * <p>状态流转（旧项目类注释 {@code :37-41} 逐字 + 本项目战斗拦截与自用出售链）：</p>
 * <pre>
 * IDLE → GO_WILD → MINING → [UNLOADING/SUPPLY/REPAIR] → GO_WILD → MINING ...
 *
 * 战斗拦截（仅在 GO_WILD / MINING 触发）：
 * GO_WILD/MINING → COMBAT → MINING
 *
 * 自用出售链（仅自用模式，入口在 MINING）：
 * MINING → SELL_TRAVEL → SELL_PATH → SELL_TRADE → SELL_RETURN → GO_WILD
 *
 * 死亡事件拦截：
 * ANY_STATE → DEATH_HANDLING → RESPAWN_WAIT → GO_WILD（自用模式为 → MINING，就地接着挖）
 * </pre>
 */
public enum MinerState {

    IDLE("待机"),
    GO_WILD("前往野外"),
    MINING("采掘中"),
    UNLOADING("卸货中"),

    // ── 自用模式（用户 2026-09-20）：挖够就自己去卖掉，四态串成一条链 ──

    /** 回城中：发流程指令 → 在快捷菜单里点「返回主城」，落到主城大厅（收购 NPC 所在） */
    SELL_TRAVEL("回城中"),

    /** 寻路商铺：从主城落点 Baritone 寻路到收购 NPC 旁 */
    SELL_PATH("寻路商铺"),

    /** 出售中：交互 NPC 打开市场菜单 → 点矿石 → 点「全部」→ 点「确认出售」，循环到背包清零 */
    SELL_TRADE("出售中"),

    /** 返回服务器：点「跨服传送」→ 勾选的目标子服 → 落地后接回 GO_WILD 继续 RTP 挖矿 */
    SELL_RETURN("返回服务器"),
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
