package com.yiyiaddon.seed.validation;

import com.yiyiaddon.seed.model.OreSource;
import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿正式模块 · <b>一条种子验证证据</b>（正式化第六阶段 234）。
 *
 * <p><b>它不是 {@code confirmedCount}</b>（口径第十一节）：只存一个计数无法回答
 * 「这条证据是谁、在哪个区块、哪种确定性、看到了什么真实方块、属于哪一个独立样本组」，
 * 而这几项恰恰是 234 要留下的取证材料。</p>
 *
 * <p><b>它是纯数据 + 不可变</b>：由 {@link SeedValidationService} 在客户端主线程构造，
 * 界面与报告只读它。唯一的「演进」方式是
 * {@link #withObservation(OreObservationState, String, long)} 产生一份新记录
 * （当前观察状态与时间戳会变，但 {@link #firstObservedAt} 与 {@link #everConfirmed} 只会前进）。</p>
 *
 * @param position         候选方块坐标
 * @param targetChunk      这条候选所属的<b>目标区块</b>（覆盖调度提交的那个区块，不是坐标所在区块）
 * @param certainty        预测确定性（{@link PredictionCertainty}）
 * @param oreSource        预测来源分类；本阶段恒为 {@code UNATTRIBUTED}
 * @param originViewer     是哪一次 {@code FEATURES}（哪个区块自己的装饰）把它写进目标区块的；
 *                         未知为 {@code null}。它是「不同 worldgen placement」的判别依据
 * @param oreType          矿物种类；本阶段恒为钻石
 * @param observationState 当前观察状态（{@link OreObservationState}）
 * @param actualBlockState 最近一次看到的真实方块注册名；从未看到为空串
 * @param firstObservedAt  首次拿到有效观察的时间（毫秒时间戳；从未为 0）
 * @param lastObservedAt   最近一次拿到有效观察的时间（毫秒时间戳；从未为 0）
 * @param everConfirmed    是否<b>曾经</b>被观察到「真的就是该矿物」（口径第十六、四十六节：
 *                         这条锁存是「被挖掉容错」的关键 —— 矿被挖走不撤销历史证据）
 * @param groupId          所属<b>有效证据单元</b>的编号（由 {@link SeedValidationEvidenceGroup} 赋予）
 */
public record SeedValidationEvidence(BlockPos position, ChunkPos targetChunk, PredictionCertainty certainty,
                                     OreSource oreSource, ChunkPos originViewer, OreType oreType,
                                     OreObservationState observationState, String actualBlockState,
                                     long firstObservedAt, long lastObservedAt, boolean everConfirmed,
                                     long groupId) {

    public SeedValidationEvidence {
        Objects.requireNonNull(position, "position");
        Objects.requireNonNull(targetChunk, "targetChunk");
        Objects.requireNonNull(certainty, "certainty");
        Objects.requireNonNull(oreSource, "oreSource");
        Objects.requireNonNull(oreType, "oreType");
        Objects.requireNonNull(observationState, "observationState");
        actualBlockState = actualBlockState == null ? "" : actualBlockState;
    }

    /** 新建一条证据（还没有分组编号）。 */
    public static SeedValidationEvidence create(BlockPos position, ChunkPos targetChunk,
                                                PredictionCertainty certainty, OreSource oreSource,
                                                ChunkPos originViewer, OreType oreType) {
        return new SeedValidationEvidence(position, targetChunk, certainty, oreSource, originViewer, oreType,
                OreObservationState.UNOBSERVED, "", 0L, 0L, false, SeedValidationEvidenceGroup.UNGROUPED);
    }

    /** 位置键（{@link BlockPos#asLong()}；证据表的索引键）。 */
    public long positionKey() {
        return position.asLong();
    }

    /** 当前是否「已确认」（真实方块就是该矿物）。 */
    public boolean confirmed() {
        return observationState == OreObservationState.CONFIRMED;
    }

    /** 是否已经被实际观察过（已确认或当前缺失）。 */
    public boolean observed() {
        return observationState == OreObservationState.CONFIRMED
                || observationState == OreObservationState.MISSING;
    }

    /**
     * 落一条新的观察结果，返回新记录。
     *
     * <p>两条只前进不后退的字段：{@link #firstObservedAt}（首次观察时间）与
     * {@link #everConfirmed}（曾经确认过的锁存）。其余字段按最新一次观察覆盖。</p>
     *
     * @param state        新的观察状态
     * @param blockId      该状态对应的真实方块注册名（空串 = 未知）
     * @param observedAt   本次观察时间（毫秒）
     */
    public SeedValidationEvidence withObservation(OreObservationState state, String blockId, long observedAt) {
        boolean effective = state == OreObservationState.CONFIRMED || state == OreObservationState.MISSING;
        long first = firstObservedAt;
        if (effective && first == 0L) {
            first = observedAt;
        }
        return new SeedValidationEvidence(position, targetChunk, certainty, oreSource, originViewer, oreType,
                state, blockId, first, effective ? observedAt : lastObservedAt,
                everConfirmed || state == OreObservationState.CONFIRMED, groupId);
    }

    /** 赋予有效证据单元编号，返回新记录。 */
    public SeedValidationEvidence withGroup(long newGroupId) {
        return new SeedValidationEvidence(position, targetChunk, certainty, oreSource, originViewer, oreType,
                observationState, actualBlockState, firstObservedAt, lastObservedAt, everConfirmed, newGroupId);
    }

    /** 一行中文摘要（日志 / 报告用）。 */
    public String describeCn() {
        return "(" + position.getX() + "," + position.getY() + "," + position.getZ() + ")"
                + " 目标区块(" + targetChunk.x() + "," + targetChunk.z() + ")"
                + " " + oreType.displayNameCn() + "/" + certainty.displayNameCn() + "/" + oreSource.displayNameCn()
                + (originViewer == null ? "" : "/写入者(" + originViewer.x() + "," + originViewer.z() + ")")
                + " → " + observationState.displayNameCn()
                + "（实际 " + (actualBlockState.isEmpty() ? "未知" : actualBlockState) + "）"
                + (everConfirmed ? "【曾确认】" : "");
    }
}
