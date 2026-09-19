package com.yiyiaddon.feature.librarian.model;

import java.util.Objects;
import java.util.Optional;

/**
 * 自动图书管理员 · 单次交易验证上下文。
 *
 * <p>管理一次附魔书购买的完整验证生命周期：记录购买前库存快照 → 提交并等待
 * 服务端接受交易请求 → 等待服务器同步 → 对比库存变化判定成交是否成功。
 * 各阶段通过内部状态枚举严格串行推进，防止非法跳步。</p>
 *
 * <p><b>为什么用库存计数而不是报价使用次数</b>：旧项目本条以「购买前后背包里
 * 目标附魔书数量是否增加」为唯一成交判据，报价的 uses / 售罄标记只作准入条件；
 * 迁移必须保持同一判据（第 169 条：同源判据不得各自演化）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/TradeVerificationContext}（203 行），逐字照搬。</p>
 */
public final class TradeVerificationContext {
    /** 交易请求状态（客户端发包视角） */
    public enum RequestStatus {
        /** 尚未提交购买请求 */
        NOT_SUBMITTED,
        /** 已提交购买请求 */
        SUBMITTED,
        /** 服务端已接受购买请求 */
        ACCEPTED,
        /** 购买请求失败 */
        FAILED
    }

    /** 服务器同步等待状态 */
    public enum SynchronizationStatus {
        /** 未等待同步 */
        NOT_WAITING,
        /** 正在等待服务器同步 */
        WAITING,
        /** 服务器已同步 */
        SYNCHRONIZED,
        /** 同步超时 */
        TIMED_OUT
    }

    /** 购买验证结果状态 */
    public enum VerificationStatus {
        /** 尚未验证 */
        NOT_VERIFIED,
        /** 验证成功（库存增加） */
        SUCCEEDED,
        /** 验证失败 */
        FAILED
    }

    /** 目标附魔 */
    private final EnchantmentTarget target;
    /** 锁定的交易报价 */
    private final TradeOfferSnapshot tradeOffer;
    /** 购买前库存快照 */
    private InventorySnapshot beforePurchase;
    /** 服务器同步后库存快照 */
    private InventorySnapshot afterSynchronization;
    /** 交易请求状态 */
    private RequestStatus requestStatus = RequestStatus.NOT_SUBMITTED;
    /** 服务器同步状态 */
    private SynchronizationStatus synchronizationStatus = SynchronizationStatus.NOT_WAITING;
    /** 验证结果状态 */
    private VerificationStatus verificationStatus = VerificationStatus.NOT_VERIFIED;
    /** 失败原因（成功时为空串） */
    private String failureReason = "";

    public TradeVerificationContext(EnchantmentTarget target, TradeOfferSnapshot tradeOffer) {
        this.target = Objects.requireNonNull(target, "target");
        this.tradeOffer = Objects.requireNonNull(tradeOffer, "tradeOffer");
        if (!target.identifier().equals(tradeOffer.enchantmentIdentifier())
            || target.level() != tradeOffer.enchantmentLevel()) {
            throw new IllegalArgumentException("锁定报价必须匹配当前目标附魔 ID 和准确等级");
        }
    }

    /** 记录购买前库存快照（仅允许记录一次） */
    public void captureBeforePurchase(InventorySnapshot snapshot) {
        requireMatchingSnapshot(snapshot);
        if (beforePurchase != null) throw new IllegalStateException("购买前库存快照已经记录");
        beforePurchase = snapshot;
    }

    /** 标记购买请求已提交 */
    public void markRequestSubmitted() {
        requireRequestStatus(RequestStatus.NOT_SUBMITTED);
        requestStatus = RequestStatus.SUBMITTED;
    }

    /** 标记服务端已接受购买请求 */
    public void markRequestAccepted() {
        requireRequestStatus(RequestStatus.SUBMITTED);
        requestStatus = RequestStatus.ACCEPTED;
    }

    /** 标记购买请求提交失败（已接受则不允许回退） */
    public void markRequestFailed(String reason) {
        if (requestStatus == RequestStatus.ACCEPTED) throw new IllegalStateException("已接受请求不能标记为提交失败");
        requestStatus = RequestStatus.FAILED;
        fail(reason);
    }

    /** 开始等待服务器同步（要求请求已接受且已有购买前快照） */
    public void beginSynchronizationWait() {
        if (requestStatus != RequestStatus.ACCEPTED) throw new IllegalStateException("交易请求尚未被接受");
        if (beforePurchase == null) throw new IllegalStateException("缺少购买前库存快照");
        if (synchronizationStatus != SynchronizationStatus.NOT_WAITING) {
            throw new IllegalStateException("服务器同步等待已经开始");
        }
        synchronizationStatus = SynchronizationStatus.WAITING;
    }

    /** 记录服务器同步后的库存快照 */
    public void recordSynchronizedSnapshot(InventorySnapshot snapshot) {
        if (synchronizationStatus != SynchronizationStatus.WAITING) {
            throw new IllegalStateException("当前未等待服务器同步");
        }
        requireMatchingSnapshot(snapshot);
        afterSynchronization = snapshot;
        synchronizationStatus = SynchronizationStatus.SYNCHRONIZED;
    }

    /** 标记服务器同步超时 */
    public void markSynchronizationTimedOut(String reason) {
        if (synchronizationStatus != SynchronizationStatus.WAITING) {
            throw new IllegalStateException("当前未等待服务器同步");
        }
        synchronizationStatus = SynchronizationStatus.TIMED_OUT;
        fail(reason);
    }

    /** 对比前后库存快照，判定购买是否成功 */
    public boolean verifyPurchase() {
        if (synchronizationStatus != SynchronizationStatus.SYNCHRONIZED
            || beforePurchase == null
            || afterSynchronization == null) {
            return false;
        }
        if (afterSynchronization.hasIncreaseFrom(beforePurchase)) {
            verificationStatus = VerificationStatus.SUCCEEDED;
            failureReason = "";
            return true;
        }
        fail("服务器同步后目标附魔书库存数量未增加");
        return false;
    }

    /** 标记验证失败并记录原因 */
    public void fail(String reason) {
        verificationStatus = VerificationStatus.FAILED;
        failureReason = Objects.requireNonNullElse(reason, "");
    }

    /** 返回目标附魔 */
    public EnchantmentTarget target() {
        return target;
    }

    /** 返回锁定的交易报价 */
    public TradeOfferSnapshot tradeOffer() {
        return tradeOffer;
    }

    /** 返回购买前库存快照（可为空） */
    public Optional<InventorySnapshot> beforePurchase() {
        return Optional.ofNullable(beforePurchase);
    }

    /** 返回服务器同步后库存快照（可为空） */
    public Optional<InventorySnapshot> afterSynchronization() {
        return Optional.ofNullable(afterSynchronization);
    }

    /** 返回交易请求状态 */
    public RequestStatus requestStatus() {
        return requestStatus;
    }

    /** 返回服务器同步状态 */
    public SynchronizationStatus synchronizationStatus() {
        return synchronizationStatus;
    }

    /** 返回验证结果状态 */
    public VerificationStatus verificationStatus() {
        return verificationStatus;
    }

    /** 返回失败原因（成功时为空串） */
    public String failureReason() {
        return failureReason;
    }

    /** 校验库存快照必须匹配当前目标附魔 ID 与等级 */
    private void requireMatchingSnapshot(InventorySnapshot snapshot) {
        if (!Objects.requireNonNull(snapshot, "snapshot").matchesTarget(target)) {
            throw new IllegalArgumentException("库存快照必须匹配当前目标附魔 ID 和准确等级");
        }
    }

    /** 校验当前请求状态必须等于期望值 */
    private void requireRequestStatus(RequestStatus expected) {
        if (requestStatus != expected) throw new IllegalStateException("交易请求状态不允许当前操作");
    }
}
