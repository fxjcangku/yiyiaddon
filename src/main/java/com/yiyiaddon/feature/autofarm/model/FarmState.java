package com.yiyiaddon.feature.autofarm.model;

/**
 * 自动农场的顶层状态。
 *
 * 状态只表达「当前这一秒农场在做什么」，任务内部的 ACT / WAIT / VERIFY / RESULT
 * 属于 FarmTask 自身的子阶段，不在这里展开。决策（DECIDE）与资源检查（RESOURCE_CHECK）
 * 都发生在 OBSERVE 状态、且仅在 currentTask 为空时执行，因此不单列状态。
 *
 * 寻路过渡（前往目标）不播报，避免每 tick 刷屏。
 */
public enum FarmState {

    /** 观察：无任务，分帧扫描农场、等待目标、必要时做决策与资源检查 */
    OBSERVE("观察"),

    /** 收割：HarvestTask 执行中 */
    HARVEST("收割"),

    /** 补种：PlantTask 执行中 */
    PLANT("补种"),

    /** 锄地：TillTask 执行中，把草方块/泥土锄成耕地 */
    TILL("锄地"),

    /** 拾取：CollectTask 执行中 */
    COLLECT("拾取"),

    /** 卸货：UnloadTask 执行中（独占） */
    UNLOAD("卸货"),

    /** 补货：RestockTask 执行中（独占） */
    RESTOCK("补货"),

    /** 杂物处理：PoisonDumpTask 执行中（独占） */
    POISON_DUMP("杂物处理");

    private final String cn;

    FarmState(String cn) {
        this.cn = cn;
    }

    /** 面向玩家的中文状态名 */
    public String cn() {
        return cn;
    }
}
