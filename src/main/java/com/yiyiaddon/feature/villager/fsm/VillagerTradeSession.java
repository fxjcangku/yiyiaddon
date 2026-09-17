package com.yiyiaddon.feature.villager.fsm;

import com.yiyiaddon.feature.villager.model.VillagerTradeTarget;
import com.yiyiaddon.feature.villager.trade.TradeEngine;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 一次「交易会话」的全部可变状态与两项判定：选哪一笔、这笔成交了没有。
 *
 * <p>职责拆分自旧项目 {@code VillagerTradeFSM}（原类 1,221 行，按第 38 条先拆后迁）。
 * 会话只管「打开界面期间」的数据：子阶段、跳过集合、当前 offer 与 uses 快照、重试计数、
 * 发包节流、成交计数与每物品购入量。**状态迁移、发包、播报、音效都不在这里**——
 * 那些是 {@link VillagerTradeFSM} 的裁决权，会话只回一个判定结果。</p>
 *
 * <p><b>交易协议判据（逐字照搬，禁止"优化"）：</b></p>
 * <ul>
 *   <li>成功信号<b>只认 offer 的 {@code uses} 递增</b>（服务端真正成交才会 increaseUses）；
 *       不能用「绿宝石减少」——{@code SelectTrade} 会把绿宝石挪进付款槽，造成假阳性
 *       （旧 {@code VillagerTradeFSM:688-690} 的注释即此判据）；</li>
 *   <li>等待窗口 {@link #CONFIRM_TICKS}=20 tick；同一 offer 连败
 *       {@link #MAX_CONFIRM_FAILS}=2 次（即最多重发 1 次）后跳过该 offer；</li>
 *   <li>重发前<b>刷新 uses 快照</b>：每一发只评判「自己这一发」的结果，避免上一发延迟到账
 *       与本发成功落在同一快照上，导致计数与真实购买量脱钩（旧 {@code :724-728} 注释）；</li>
 *   <li>换新 offer 后连败计数清零，跳过 offer 后也清零（旧 {@code :674}、{@code :721}）。</li>
 * </ul>
 */
final class VillagerTradeSession {

    /** 交易确认等待（1 秒，避免同步延迟误判重发导致重复购买）——旧 {@code CONFIRM_TICKS} */
    static final int CONFIRM_TICKS = 20;
    /** 同一 offer 连败次数（最多重发 1 次）——旧 {@code MAX_CONFIRM_FAILS} */
    static final int MAX_CONFIRM_FAILS = 2;
    /** 交易发包节流（2 tick，仅等服务端确认，实现「一次买满」连续交易）——旧字段 {@code sendCooldownTicks = 2} */
    static final int SEND_COOLDOWN_TICKS = 2;

    /** 交易子阶段（旧 {@code TradePhase}） */
    private TradePhase phase = TradePhase.SELECT;
    /** 已确认无货/超价/连败的 offer 下标，本次会话内不再选（旧 {@code skipOfferIndexes}） */
    private final Set<Integer> skipOfferIndexes = new HashSet<>();

    /** 当前正在确认的 offer 下标（旧 {@code offerIndex}） */
    private int offerIndex = -1;
    /** 发包瞬间记录的 offer 用量快照（旧 {@code usesSnapshot}） */
    private int usesSnapshot = -1;
    /** 本发已等待的 tick 数（旧 {@code confirmTicks}） */
    private int confirmTicks = 0;
    /** 同一 offer 连续未确认次数（旧 {@code failStreak}） */
    private int failStreak = 0;
    /** 发包节流剩余 tick（旧 {@code sendCooldown}） */
    private int sendCooldown = 0;

    /** 本村民 / 本任务累计购入件数（旧 {@code purchasedCount}） */
    private int purchasedCount = 0;
    /** 本村民 / 本任务累计成交笔数（旧 {@code tradeCount}） */
    private int tradeCount = 0;
    /** 每物品累计购入量，供下一笔轮换（旧 {@code perItemPurchased}） */
    private final Map<Item, Integer> perItemPurchased = new HashMap<>();
    /** 最近一次成交发生时的状态 tick，用于僵局检测（旧 {@code lastProgressAt}） */
    private int lastProgressAt = 0;

    /** 最近一次成功那一笔的获得件数（供状态机逐字播报用，旧实现是方法内局部变量） */
    private int lastGained = 0;
    /** 最近一次判定时的连败次数（跳过/重发播报要念它，旧实现也是方法内局部变量） */
    private int lastFailStreak = 0;

    /** 会话判定结果：状态机据此发包、播报、迁移状态。 */
    enum ConfirmOutcome {
        /** 本 tick 还没到判定点，继续等 */
        WAITING,
        /** 成交（uses 递增） */
        SUCCESS,
        /** 未确认但未到连败上限：重发同一笔 */
        RESEND,
        /** 达到连败上限：跳过该 offer */
        SKIP
    }

    // ── 状态访问 ───────────────────────────────────────────────────────────

    TradePhase phase() {
        return phase;
    }

    int offerIndex() {
        return offerIndex;
    }

    int purchasedCount() {
        return purchasedCount;
    }

    int tradeCount() {
        return tradeCount;
    }

    int lastGained() {
        return lastGained;
    }

    int lastFailStreak() {
        return lastFailStreak;
    }

    // ── 复位（三处，语义与旧实现一一对应） ─────────────────────────────────

    /** 整场复位（旧 {@code resetSession()} 的会话部分）：启动 / 停止时调用。 */
    void reset() {
        skipOfferIndexes.clear();
        phase = TradePhase.SELECT;
        offerIndex = -1;
        usesSnapshot = -1;
        confirmTicks = 0;
        failStreak = 0;
        sendCooldown = 0;
        purchasedCount = 0;
        tradeCount = 0;
        perItemPurchased.clear();
        lastProgressAt = 0;
    }

    /** 打开村民界面时复位（旧 {@code prepareTradeSession()} 的会话部分）：保留全局累计计数不动。 */
    void prepare() {
        skipOfferIndexes.clear();
        phase = TradePhase.SELECT;
        offerIndex = -1;
        usesSnapshot = -1;
        confirmTicks = 0;
        failStreak = 0;
        sendCooldown = 0;
        lastProgressAt = 0;
    }

    /** 换村民时清空跳过集合（旧 {@code dispatchRoute} 的 {@code SEARCH_NEXT} 分支只做这一件事）。 */
    void clearSkips() {
        skipOfferIndexes.clear();
    }

    /** 切换 Pipeline 任务时复位（旧 {@code loadTask()} 的会话部分）：每任务重新计数。 */
    void resetForTask() {
        skipOfferIndexes.clear();
        purchasedCount = 0;
        tradeCount = 0;
        perItemPurchased.clear();
    }

    // ── 发包节流与僵局检测 ─────────────────────────────────────────────────

    /** 每 tick 递减发包节流（旧 {@code tickTrading} 里的 {@code if (sendCooldown > 0) sendCooldown--;}）。 */
    void tickCooldown() {
        if (sendCooldown > 0) sendCooldown--;
    }

    /** 是否仍在发包冷却中（冷却中不选单，旧 {@code tickTradeSelect} 的 {@code if (sendCooldown > 0) return;}）。 */
    boolean onCooldown() {
        return sendCooldown > 0;
    }

    /**
     * 僵局检测：距上次成交是否已超过 {@code idleTimeout} tick（旧 {@code stateTicks - lastProgressAt > TRADE_IDLE_TIMEOUT}）。
     */
    boolean stalled(int stateTicks, int idleTimeout) {
        return stateTicks - lastProgressAt > idleTimeout;
    }

    // ── 选单 ───────────────────────────────────────────────────────────────

    /**
     * 选出下一笔要买的 offer 下标（旧 {@code TradeEngine.findBestOfferIndex(...)} 调用点）。
     *
     * <p>单次遍历按「已购最少优先 + 同物品最便宜」轮换选择，勾选多个目标时轮流买入；
     * 返回 -1 表示所有匹配交易都已售罄 / 超价 / 被跳过。</p>
     */
    int chooseOffer(MerchantOffers offers, List<VillagerTradeTarget> targets, int maxPrice) {
        return TradeEngine.findBestOfferIndex(offers, targets, maxPrice, skipOfferIndexes, perItemPurchased);
    }

    /**
     * 发包<b>前</b>锁定本次会话：记 offer 下标、刷 uses 快照、清零连败计数。
     *
     * <p>必须与 {@link #finishSend()} 分两步调用，中间夹着真正的发包；这与旧实现
     * （旧 {@code tickTradeSelect} 尾部：先记 offerIndex / usesSnapshot / failStreak，
     * 再 {@code TradeEngine.executeTrade}，最后 {@code confirmTicks = 0}、
     * {@code sendCooldown = sendCooldownTicks}、{@code tradePhase = CONFIRM}）严格同序，
     * 快照取的是「发包前」的 uses，避免把本发自身的影响算进基线。</p>
     */
    void beginSend(int index, MerchantOffers offers) {
        offerIndex = index;
        usesSnapshot = offers.get(index).getUses();
        failStreak = 0; // 换新 offer 后重开连败计数，避免上一 offer 的连败残留累加
    }

    /** 发包<b>后</b>收尾：起确认窗口、起发包冷却、进入 CONFIRM（见 {@link #beginSend}）。 */
    void finishSend() {
        confirmTicks = 0;
        sendCooldown = SEND_COOLDOWN_TICKS;
        phase = TradePhase.CONFIRM;
    }

    // ── 确认 ───────────────────────────────────────────────────────────────

    /**
     * 推进一次确认判定（旧 {@code tickTradeConfirm} 的全部状态变更）。
     *
     * <p>成功后顺带完成计数：成交笔数 +1、购入件数 += 本次 uses 差值 × 单次产出、
     * 每物品累计量合并（供下一笔轮换，避免只买最便宜的一种）、连败清零、回到 SELECT。</p>
     *
     * @param stateTicks 当前状态已持续的 tick（用于记录「最近一次成交」的时间点）
     * @return 判定结果；{@link ConfirmOutcome#SUCCESS} 时状态机读 {@link #lastGained()} 播报
     */
    ConfirmOutcome tickConfirm(MerchantOffers offers, int stateTicks) {
        confirmTicks++;

        int usesNow = (offerIndex < offers.size()) ? offers.get(offerIndex).getUses() : -1;
        if (usesNow > usesSnapshot) {
            tradeCount++;
            lastProgressAt = stateTicks;
            // QUICK_MOVE 会循环购买多次：实际成交次数 = uses 差值，乘以单次产出得到本笔获得数量
            int trades = usesNow - usesSnapshot;
            int gained = trades * TradeEngine.safeResultCount(offers, offerIndex);
            purchasedCount += gained;
            // 记录每个物品的累计购入量，供下一笔轮换选择（避免只买最便宜的一种）
            if (offerIndex < offers.size() && !offers.get(offerIndex).getResult().isEmpty()) {
                perItemPurchased.merge(offers.get(offerIndex).getResult().getItem(), gained, Integer::sum);
            }
            failStreak = 0;
            phase = TradePhase.SELECT;
            lastGained = gained;
            return ConfirmOutcome.SUCCESS;
        }

        // 等待窗口结束仍未确认
        if (confirmTicks >= CONFIRM_TICKS) {
            failStreak++;
            lastFailStreak = failStreak;
            if (failStreak >= MAX_CONFIRM_FAILS) {
                skipOfferIndexes.add(offerIndex);
                failStreak = 0; // 跳过当前 offer 后复位，下一 offer 从 0 重新累计
                phase = TradePhase.SELECT;
                return ConfirmOutcome.SKIP;
            }
            // 重发同一笔完整交易（含结果槽点击），重发前刷新快照：见类 javadoc 的重发判据
            usesSnapshot = (offerIndex < offers.size()) ? offers.get(offerIndex).getUses() : -1;
            confirmTicks = 0;
            return ConfirmOutcome.RESEND;
        }

        return ConfirmOutcome.WAITING;
    }
}
