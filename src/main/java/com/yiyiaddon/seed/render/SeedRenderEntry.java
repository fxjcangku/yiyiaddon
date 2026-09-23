package com.yiyiaddon.seed.render;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import java.util.Objects;
import net.minecraft.core.BlockPos;

/**
 * 种子挖矿正式模块 · <b>一条待渲染的预测钻石</b>（正式化第五阶段 233）。
 *
 * <p><b>它是纯数据</b>：只在快照重建时构造，之后被渲染层逐帧读取。它不持有世界引用、
 * 不能反查方块状态、也不能回写任何东西 —— 渲染层因此永远不可能「顺手读一下当前世界」
 * （口径第三十二节）。</p>
 *
 * <p><b>两个维度各自独立</b>（口径第八、二十六节）：{@link #certainty} 是 Seed 预测的确定性，
 * {@link #state} 是客户端实际观察状态。两者<b>不合并</b>成第三个枚举，也不互相推导：
 * 调度敏感可以同时是未观察 / 已确认 / 当前缺失（口径第十三节）。</p>
 *
 * @param position  方块坐标（可为负坐标、负 Y，渲染层按原样画）
 * @param oreType   预测矿物种类（本阶段只有钻石）
 * @param certainty Seed 预测确定性
 * @param state     客户端实际观察状态
 */
public record SeedRenderEntry(BlockPos position, OreType oreType, PredictionCertainty certainty,
                              OreObservationState state) {

    public SeedRenderEntry {
        Objects.requireNonNull(position, "position");
        Objects.requireNonNull(oreType, "oreType");
        Objects.requireNonNull(certainty, "certainty");
        Objects.requireNonNull(state, "state");
    }

    /** 该位置是否由原版调度歧义影响（渲染层据此画「调度敏感」小标记，口径第三十六节）。 */
    public boolean scheduleSensitive() {
        return certainty == PredictionCertainty.SCHEDULE_SENSITIVE;
    }

    /** 一行中文摘要（日志 / 报告用）。 */
    public String describeCn() {
        return "(" + position.getX() + "," + position.getY() + "," + position.getZ() + ") "
                + oreType.displayNameCn() + " / " + certainty.displayNameCn() + " / " + state.displayNameCn();
    }
}
