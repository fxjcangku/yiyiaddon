package com.yiyiaddon.feature.autofarm.task;

/**
 * 任务执行结果。
 *
 * 与旧架构「发包即成功」相反，这里区分进行中、成功与各类明确失败，
 * 由 Controller 依据结果决定下一步（Fresh Observe / Replan / 停机）。
 */
public enum TaskResult {

    /** 进行中，继续下一 tick */
    IN_PROGRESS,

    /** 成功完成 */
    SUCCESS,

    /** 本波完成、仍有剩余（批量分波交错收割：收一波→补一波→捡一波后继续） */
    WAVE_DONE,

    /** 目标已失效（被他人破坏 / 已不再成熟） */
    TARGET_INVALID,

    /** 收割失败（发破坏包后世界状态未按预期变化） */
    HARVEST_FAILED,

    /** 补种失败 */
    PLANT_FAILED,

    /** 种植材料不足 */
    RESOURCE_INSUFFICIENT,

    /** 目标容器未绑定或已消失 */
    CONTAINER_MISSING,

    /** 容器已满，无法继续存入 */
    CONTAINER_FULL,

    /** 容器为空，无可提取 */
    CONTAINER_EMPTY,

    /** 容器同步失败 */
    CONTAINER_SYNC_FAILED,

    /** 容器打开失败 */
    CONTAINER_OPEN_FAILED,

    /** 导航失败（Baritone 不可用或目标不可达） */
    NAVIGATION_FAILED,

    /** 毒马铃薯箱已满 */
    POISON_CONTAINER_FULL,

    /** 维度不匹配 */
    DIMENSION_MISMATCH,

    /** 农场范围非法 */
    FARM_AREA_INVALID,

    /** 被取消 */
    CANCELLED;

    /** 是否已结束（不再 IN_PROGRESS） */
    public boolean done() {
        return this != IN_PROGRESS;
    }

    /** 是否成功 */
    public boolean ok() {
        return this == SUCCESS;
    }
}
