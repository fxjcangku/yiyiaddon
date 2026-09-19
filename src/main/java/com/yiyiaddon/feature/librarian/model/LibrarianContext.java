package com.yiyiaddon.feature.librarian.model;

import java.util.Objects;
import java.util.Optional;

/**
 * 自动图书管理员 · 运行上下文。
 *
 * <p>持有一次运行的全部可变状态：全局任务进度、当前村民周期、讲台刷新尝试、
 * 交易验证上下文与库存采样序列。通过内部生命周期子类组织不同粒度的状态，
 * 并对外提供只读查询与受控变更入口。</p>
 *
 * <p><b>三层生命周期的边界</b>（照搬旧项目语义，不合并、不简化）：</p>
 * <ul>
 *   <li><b>全局任务</b>：目标进度 + 最后一次失败原因，跨越所有村民周期；</li>
 *   <li><b>村民周期</b>：当前村民、讲台位置、交易位，换村民时整块清空；</li>
 *   <li><b>刷新尝试</b>：一轮「读报价 → 命中判定」的中间数据，未命中即丢弃重来。</li>
 * </ul>
 *
 * <p>交易验证上下文只在交易进行中存在，且期间禁止覆盖报价与命中目标
 * （否则验证会对着被改写的报价做库存比对）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/AutoLibrarianContext}（224 行），逐字照搬。</p>
 */
public final class LibrarianContext {
    /** 全局任务生命周期（目标进度 + 最后失败原因） */
    private final GlobalTaskLifecycle globalTask;
    /** 当前村民周期生命周期 */
    private final VillagerCycleLifecycle villagerCycle = new VillagerCycleLifecycle();
    /** 讲台刷新尝试生命周期 */
    private final RefreshAttemptLifecycle refreshAttempt = new RefreshAttemptLifecycle();
    /** 当前交易验证上下文（交易进行中才非空） */
    private TradeVerificationContext tradeVerification;
    /** 库存采样序号（单调递增，区分先后快照） */
    private long inventorySampleSequence;

    public LibrarianContext(TargetProgress targetProgress) {
        globalTask = new GlobalTaskLifecycle(targetProgress);
    }

    /** 清空本轮刷新尝试（保留村民与讲台） */
    public void resetRefreshAttempt() {
        refreshAttempt.clear();
        tradeVerification = null;
    }

    /** 讲台已拆除：把讲台位置回退为交易位上记录的位置 */
    public void completeLecternRemoval() {
        villagerCycle.clearLectern();
    }

    /** 锁定本次交易：报价 + 命中目标 + 村民 + 讲台四者必须齐备 */
    public void beginTradeProcess() {
        if (villagerCycle.villagerTarget == null
            || villagerCycle.lecternPosition == null
            || refreshAttempt.tradeOffer == null
            || refreshAttempt.matchedTarget == null) {
            throw new IllegalStateException("目标交易上下文不完整");
        }
        if (tradeVerification != null) throw new IllegalStateException("当前交易验证尚未结束");
        tradeVerification = new TradeVerificationContext(refreshAttempt.matchedTarget, refreshAttempt.tradeOffer);
        refreshAttempt.clear();
    }

    /** 标记当前目标完成（默认不移除） */
    public void completeCurrentTarget() {
        completeVerifiedCurrentTarget();
    }

    /** 标记当前目标完成（默认不移除），要求交易已通过库存验证 */
    public void completeVerifiedCurrentTarget() {
        completeVerifiedCurrentTarget(false);
    }

    /** 标记当前目标完成，可选择从目标集合移除 */
    public void completeVerifiedCurrentTarget(boolean removeCompletedTarget) {
        TradeVerificationContext verification = requireTradeVerification();
        if (verification.verificationStatus() != TradeVerificationContext.VerificationStatus.SUCCEEDED) {
            throw new IllegalStateException("当前目标交易尚未通过库存验证");
        }
        globalTask.targetProgress.complete(verification.target(), removeCompletedTarget);
        tradeVerification = null;
    }

    /** 结束当前村民周期（村民 + 讲台 + 交易位 + 刷新尝试全清） */
    public void clearVillagerCycle() {
        villagerCycle.clear();
        resetRefreshAttempt();
    }

    /** 目标进度 */
    public TargetProgress targetProgress() {
        return globalTask.targetProgress;
    }

    /** 当前村民目标 */
    public Optional<VillagerTarget> villagerTarget() {
        return Optional.ofNullable(villagerCycle.villagerTarget);
    }

    /** 设置当前村民目标（一个周期只能设一次） */
    public void setVillagerTarget(VillagerTarget villagerTarget) {
        villagerCycle.begin(villagerTarget);
    }

    /** 当前讲台位置 */
    public Optional<BlockPosition> lecternPosition() {
        return Optional.ofNullable(villagerCycle.lecternPosition);
    }

    /** 设置讲台位置 */
    public void setLecternPosition(BlockPosition lecternPosition) {
        villagerCycle.lecternPosition = Objects.requireNonNull(lecternPosition, "lecternPosition");
    }

    /** 当前固定交易位 */
    public Optional<VillagerStation> villagerStation() {
        return Optional.ofNullable(villagerCycle.villagerStation);
    }

    /** 设置固定交易位（必须属于当前村民；同时写入讲台位置） */
    public void setVillagerStation(VillagerStation villagerStation) {
        VillagerStation station = Objects.requireNonNull(villagerStation, "villagerStation");
        if (villagerCycle.villagerTarget == null) throw new IllegalStateException("当前村民周期尚未开始");
        if (!villagerCycle.villagerTarget.uuid().equals(station.villagerUuid())) {
            throw new IllegalArgumentException("交易位必须属于当前村民");
        }
        villagerCycle.villagerStation = station;
        villagerCycle.lecternPosition = station.lecternPosition();
    }

    /** 当前交易报价（交易验证中用锁定值，否则用本轮刷新值） */
    public Optional<TradeOfferSnapshot> tradeOffer() {
        return tradeVerification == null
            ? Optional.ofNullable(refreshAttempt.tradeOffer)
            : Optional.of(tradeVerification.tradeOffer());
    }

    /** 记录本轮交易报价（交易验证期间禁止覆盖） */
    public void setTradeOffer(TradeOfferSnapshot tradeOffer) {
        if (tradeVerification != null) throw new IllegalStateException("交易验证期间不能覆盖当前报价");
        refreshAttempt.tradeOffer = Objects.requireNonNull(tradeOffer, "tradeOffer");
    }

    /** 当前命中目标（交易验证中用锁定值，否则用本轮刷新值） */
    public Optional<EnchantmentTarget> matchedTarget() {
        return tradeVerification == null
            ? Optional.ofNullable(refreshAttempt.matchedTarget)
            : Optional.of(tradeVerification.target());
    }

    /** 记录命中目标（交易验证期间禁止覆盖） */
    public void setMatchedTarget(EnchantmentTarget matchedTarget) {
        if (tradeVerification != null) throw new IllegalStateException("交易验证期间不能覆盖当前目标");
        refreshAttempt.matchedTarget = Objects.requireNonNull(matchedTarget, "matchedTarget");
    }

    /** 购买前背包中目标附魔书数量（无交易验证时为 0） */
    public int matchingBooksBeforePurchase() {
        return tradeVerification == null
            ? 0
            : tradeVerification.beforePurchase().map(InventorySnapshot::matchingBookCount).orElse(0);
    }

    /** 记录购买前的目标附魔书数量 */
    public void setMatchingBooksBeforePurchase(int count) {
        if (count < 0) throw new IllegalArgumentException("库存数量不能小于 0");
        TradeVerificationContext verification = requireTradeVerification();
        verification.captureBeforePurchase(snapshot(verification.target(), count));
    }

    /**
     * 用当前库存数量完成一次成交验证。
     *
     * <p>首次调用会依次补齐「请求已提交 → 已接受 → 开始等待同步」三步，
     * 随后记录同步后快照并比对；因此编排器只要每 tick 传当前数量即可。</p>
     */
    public boolean verifyCurrentPurchase(int matchingBookCount) {
        if (matchingBookCount < 0) throw new IllegalArgumentException("库存数量不能小于 0");
        TradeVerificationContext verification = requireTradeVerification();
        if (verification.beforePurchase().isEmpty()) throw new IllegalStateException("缺少购买前库存快照");
        if (verification.requestStatus() == TradeVerificationContext.RequestStatus.NOT_SUBMITTED) {
            verification.markRequestSubmitted();
            verification.markRequestAccepted();
        }
        if (verification.synchronizationStatus() == TradeVerificationContext.SynchronizationStatus.NOT_WAITING) {
            verification.beginSynchronizationWait();
        }
        verification.recordSynchronizedSnapshot(snapshot(verification.target(), matchingBookCount));
        return verification.verifyPurchase();
    }

    /** 当前交易验证上下文 */
    public Optional<TradeVerificationContext> tradeVerification() {
        return Optional.ofNullable(tradeVerification);
    }

    /** 交易流程是否进行中 */
    public boolean tradeProcessActive() {
        return tradeVerification != null;
    }

    /** 是否存在待处理讲台 */
    public boolean hasPendingLectern() {
        return villagerCycle.lecternPosition != null;
    }

    /** 最后一次失败原因 */
    public String lastFailureReason() {
        return globalTask.lastFailureReason;
    }

    /** 记录最后一次失败原因 */
    public void setLastFailureReason(String reason) {
        globalTask.lastFailureReason = Objects.requireNonNullElse(reason, "");
    }

    /** 生成库存快照并推进采样序号 */
    private InventorySnapshot snapshot(EnchantmentTarget target, int matchingBookCount) {
        return new InventorySnapshot(target.identifier(), target.level(), matchingBookCount, inventorySampleSequence++);
    }

    /** 取交易验证上下文，不存在即抛 */
    private TradeVerificationContext requireTradeVerification() {
        if (tradeVerification == null) throw new IllegalStateException("当前没有可用的交易验证上下文");
        return tradeVerification;
    }

    /** 全局任务生命周期：目标进度 + 最后失败原因 */
    private static final class GlobalTaskLifecycle {
        private final TargetProgress targetProgress;
        private String lastFailureReason = "";

        private GlobalTaskLifecycle(TargetProgress targetProgress) {
            this.targetProgress = Objects.requireNonNull(targetProgress, "targetProgress");
        }
    }

    /** 村民周期生命周期：村民 / 讲台 / 交易位 */
    private static final class VillagerCycleLifecycle {
        private VillagerTarget villagerTarget;
        private BlockPosition lecternPosition;
        private VillagerStation villagerStation;

        private void begin(VillagerTarget target) {
            if (villagerTarget != null) throw new IllegalStateException("当前村民周期尚未结束");
            villagerTarget = Objects.requireNonNull(target, "villagerTarget");
        }

        /** 拆除完成后讲台位置回退为交易位记录的位置（无交易位则置空） */
        private void clearLectern() {
            lecternPosition = villagerStation == null ? null : villagerStation.lecternPosition();
        }

        private void clear() {
            villagerTarget = null;
            lecternPosition = null;
            villagerStation = null;
        }
    }

    /** 刷新尝试生命周期：本轮报价 + 命中目标 */
    private static final class RefreshAttemptLifecycle {
        private TradeOfferSnapshot tradeOffer;
        private EnchantmentTarget matchedTarget;

        private void clear() {
            tradeOffer = null;
            matchedTarget = null;
        }
    }
}
