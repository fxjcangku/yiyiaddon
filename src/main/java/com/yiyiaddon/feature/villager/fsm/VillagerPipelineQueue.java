package com.yiyiaddon.feature.villager.fsm;

import com.yiyiaddon.feature.villager.model.PipelineTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Pipeline（多任务）队列：任务列表 + 当前执行下标。
 *
 * <p>职责拆分自旧项目 {@code VillagerTradeFSM} 的两个字段（{@code pipelineTasks} /
 * {@code currentTaskIndex}）与 {@code startPipeline} / {@code tickNextTask} 里的队列推进部分。
 * <b>任务的「内容」不在这里</b>：把任务的职业/物品/价格应用到状态机字段由
 * {@link VillagerTradeFSM#loadTask(int)} 负责（与旧实现同形）。</p>
 *
 * <p>推进语义：{@code NEXT_TASK} 时先播报上一任务收官，再 {@link #advance()}；
 * 越界即表示队列跑完（旧 {@code currentTaskIndex++ >= pipelineTasks.size()} 分支）。</p>
 */
final class VillagerPipelineQueue {

    /** 任务队列（旧 {@code pipelineTasks}） */
    private final List<PipelineTask> tasks = new ArrayList<>();
    /** 当前任务下标（旧 {@code currentTaskIndex}，默认 0） */
    private int currentTaskIndex = 0;

    /** 装载队列并回到第 0 个任务（旧 {@code startPipeline} 的队列部分）。 */
    void load(List<PipelineTask> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        this.currentTaskIndex = 0;
    }

    /** 队列长度（旧 {@code pipelineTasks.size()}，播报里念进度用）。 */
    int size() {
        return tasks.size();
    }

    /** 当前任务下标（旧 {@code currentTaskIndex}，播报里念进度用）。 */
    int currentIndex() {
        return currentTaskIndex;
    }

    /** 取指定下标任务；越界返回 {@code null}。 */
    PipelineTask at(int index) {
        if (index < 0 || index >= tasks.size()) return null;
        return tasks.get(index);
    }

    /**
     * 推进到下一个任务。
     *
     * @return {@code true} = 还有下一个任务（已就位）；{@code false} = 队列已跑完
     */
    boolean advance() {
        currentTaskIndex++;
        return currentTaskIndex < tasks.size();
    }
}
