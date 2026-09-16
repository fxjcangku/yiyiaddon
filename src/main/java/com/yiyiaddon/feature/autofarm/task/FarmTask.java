package com.yiyiaddon.feature.autofarm.task;

/**
 * 自动农场统一任务接口。
 *
 * 每个任务内部按 ACT → WAIT_FOR_UPDATE → VERIFY → RESULT 推进，
 * 不把 Verify 拆成独立状态机。任务结束后返回明确的 TaskResult 供 Controller 决策。
 */
public interface FarmTask {

    /** 每 tick 推进一次，返回当前结果 */
    TaskResult tick();

    /** 是否独占任务（卸货/补货/毒马铃薯处理期间，Scanner 只能观察不能抢任务） */
    boolean exclusive();

    /** 取消任务并清理副作用（关闭容器、取消导航、恢复临时手持状态） */
    void cancel();
}
