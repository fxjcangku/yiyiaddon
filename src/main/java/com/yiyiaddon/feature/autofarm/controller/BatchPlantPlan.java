package com.yiyiaddon.feature.autofarm.controller;

import com.yiyiaddon.feature.autofarm.model.FarmTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量补种计划：一次补种决策锁定的多个空耕地目标清单。
 *
 * 与批量收割计划同理，生命周期严格：创建 → 顺序连续补种 → 计划耗尽后立即丢弃。
 * 计划绝不跨越：卸货/补货/毒马铃薯、世界切换、模块关闭、死亡、断线、维度切换。
 * 一旦发生上述事件，Controller 会直接丢弃本计划并重新 Observe。
 */
public final class BatchPlantPlan {

    private final List<FarmTarget> targets;
    private int cursor;

    public BatchPlantPlan(List<FarmTarget> targets) {
        this.targets = new ArrayList<>(targets);
        this.cursor = 0;
    }

    /** 是否还有待补种目标 */
    public boolean hasNext() {
        return cursor < targets.size();
    }

    /** 取出下一个目标并推进游标，调用方需自行校验目标是否仍然可种 */
    public FarmTarget next() {
        return targets.get(cursor++);
    }
}
