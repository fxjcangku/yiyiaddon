package com.yiyiaddon.feature.enchant.gear;

import java.util.ArrayList;
import java.util.List;

/**
 * 原版装备附魔 · 任务队列。
 *
 * <p>每件装备独立推进。某件失败只标记该件为异常并继续下一件，不会让整批死掉。
 * 队列只做推进与状态统计，异常恢复/存取物由状态机完成。批次数量由「每批取用数量」
 * 配置决定，本类不再写死上限。</p>
 */
public final class GearTaskQueue {

    /** 安全上限（实际批次数量由「每批取用数量」配置决定） */
    public static final int MAX_TASKS = 16;

    private final List<GearEnchantTask> tasks;
    private int index;

    public GearTaskQueue(List<GearEnchantTask> tasks) {
        this.tasks = new ArrayList<>(tasks);
        this.index = 0;
    }

    /** 当前任务，无则返回 null */
    public GearEnchantTask current() {
        return hasNext() ? tasks.get(index) : null;
    }

    /** 推进到下一件（无论当前成功/异常） */
    public void advance() {
        if (hasNext()) index++;
    }

    /** 是否还有待处理任务 */
    public boolean hasNext() {
        return index < tasks.size();
    }

    /** 任务总数 */
    public int size() {
        return tasks.size();
    }

    /** 剩余任务数 */
    public int remaining() {
        return tasks.size() - index;
    }

    /** 是否全部处理完（含异常） */
    public boolean isComplete() {
        return !hasNext();
    }

    /** 异常任务数 */
    public int errorCount() {
        int count = 0;
        for (GearEnchantTask task : tasks) {
            if (task.isError()) count++;
        }
        return count;
    }

    /** 已完成任务数 */
    public int doneCount() {
        int count = 0;
        for (GearEnchantTask task : tasks) {
            if (task.isDone()) count++;
        }
        return count;
    }

    /** 全部任务（只读视图） */
    public List<GearEnchantTask> tasks() {
        return List.copyOf(tasks);
    }
}
