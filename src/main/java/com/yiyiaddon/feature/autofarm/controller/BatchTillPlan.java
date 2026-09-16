package com.yiyiaddon.feature.autofarm.controller;

import com.yiyiaddon.feature.autofarm.model.FarmTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量锄地计划：一次锄地决策锁定的多个草方块/泥土目标清单。
 *
 * 生命周期与批量收割/补种计划同理：创建 → 顺序连续锄地 → 计划耗尽后立即丢弃。
 * 计划绝不跨越：卸货/补货/毒马铃薯、世界切换、模块关闭、死亡、断线、维度切换。
 */
public final class BatchTillPlan {

    private final List<FarmTarget> targets;
    private int cursor;

    public BatchTillPlan(List<FarmTarget> targets) {
        this.targets = new ArrayList<>(targets);
        this.cursor = 0;
    }

    /** 是否还有待锄地目标 */
    public boolean hasNext() {
        return cursor < targets.size();
    }

    /** 取出下一个目标并推进游标，调用方需自行校验目标是否仍可开垦 */
    public FarmTarget next() {
        return targets.get(cursor++);
    }
}
