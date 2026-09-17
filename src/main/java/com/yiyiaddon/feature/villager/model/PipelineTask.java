package com.yiyiaddon.feature.villager.model;

import com.yiyiaddon.feature.villager.model.VillagerTradeTarget;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

import java.util.ArrayList;
import java.util.List;

/**
 * Pipeline 任务定义
 *
 * 代表流水线中的单个任务：
 * · 目标职业
 * · 目标物品列表
 * · 本任务的价格上限与购买总量（每任务独立，互不影响）
 * · 任务完成状态
 */
public class PipelineTask {

    private final VillagerProfession profession;
    private final List<VillagerTradeTarget> targets;
    private final int maxPrice;
    private final int targetQuantity;
    private boolean completed;

    /** 默认任务：价格上限与购买总量都取 64 */
    public PipelineTask(VillagerProfession profession, List<VillagerTradeTarget> targets) {
        this(profession, targets, 64, 64);
    }

    /**
     * 完整任务构造
     *
     * 关键判据：价格上限与购买总量各自钳制到最小 1（Math.max(1, x)），
     * 避免上游传入 0 或负数导致「永远不买」；目标列表复制一份，不共享外部可变列表。
     */
    public PipelineTask(VillagerProfession profession, List<VillagerTradeTarget> targets,
                        int maxPrice, int targetQuantity) {
        this.profession = profession;
        this.targets = new ArrayList<>(targets);
        this.maxPrice = Math.max(1, maxPrice);
        this.targetQuantity = Math.max(1, targetQuantity);
        this.completed = false;
    }

    /** 本任务的目标职业（决定找什么村民） */
    public VillagerProfession getProfession() {
        return profession;
    }

    /** 本任务的目标物品列表（构造时已复制，可直接遍历） */
    public List<VillagerTradeTarget> getTargets() {
        return targets;
    }

    /** 本任务允许的单次最高价（单位：绿宝石） */
    public int getMaxPrice() {
        return maxPrice;
    }

    /** 本任务需要购买的总数量 */
    public int getTargetQuantity() {
        return targetQuantity;
    }

    /** 任务是否已完成 */
    public boolean isCompleted() {
        return completed;
    }

    /** 标记任务完成状态（由流水线调度方写入） */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /** 调试/播报用字符串：职业、目标数、价格上限、数量与「完成 / 进行中」 */
    @Override
    public String toString() {
        return String.format("Task[%s, %d targets, price<=%d, qty=%d, %s]",
            profession, targets.size(), maxPrice, targetQuantity, completed ? "完成" : "进行中");
    }
}
