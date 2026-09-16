package com.yiyiaddon.feature.enchant.gear;

import java.util.ArrayList;
import java.util.List;

/**
 * 铁砧操作计划（AnvilPlanner 生成的完整合并方案）。
 *
 * <p>记录操作步骤序列、当前步骤与最大操作次数上限，避免异常情况下无限操作。</p>
 */
public final class AnvilPlan {

    /** 操作步骤列表（按顺序） */
    private final List<AnvilStep> steps;
    /** 最大铁砧操作次数（默认 6，防无限操作） */
    private final int maxOperations;
    /** 当前步骤索引（0 起） */
    private int currentStepIndex;

    public AnvilPlan(List<AnvilStep> steps, int maxOperations) {
        this.steps = new ArrayList<>(steps);
        this.maxOperations = maxOperations;
        this.currentStepIndex = 0;
    }

    public List<AnvilStep> steps() {
        return steps;
    }

    public int maxOperations() {
        return maxOperations;
    }

    public int currentStepIndex() {
        return currentStepIndex;
    }

    /** 是否还有下一步可执行 */
    public boolean hasNext() {
        return currentStepIndex < steps.size() && currentStepIndex < maxOperations;
    }

    /** 取下一步（调用方执行后再调用 advance） */
    public AnvilStep next() {
        if (!hasNext()) return null;
        return steps.get(currentStepIndex);
    }

    /** 推进到下一步 */
    public void advance() {
        if (hasNext()) currentStepIndex++;
    }

    /** 是否已达到最大操作次数上限 */
    public boolean reachedLimit() {
        return currentStepIndex >= maxOperations;
    }

    /** 计划内尚未执行的可执行步骤数 */
    public int remainingCount() {
        int count = 0;
        for (AnvilStep step : steps) {
            if (step.executable()) count++;
        }
        return count;
    }
}
