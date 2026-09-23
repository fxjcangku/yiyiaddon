package com.yiyiaddon.seed.validation;

import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.observation.SeedOreObservationTracker;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.runtime.SeedRuntimeIdentity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式模块 · <b>种子验证服务</b>（正式化第六阶段 234）。
 *
 * <p><b>它回答口径第五节的第 3 问</b>：「当前用户填写的种子，是否已经获得足够多的真实世界证据支持」。
 * 链路是：</p>
 *
 * <pre>
 * 用户输入种子 → Prediction（Worker） → Observation（客户端实际方块）
 *              → Validation Evidence（本类：按有效证据单元聚票）
 *              → SeedValidationState → （满足严格条件才允许后续 Suspicion 分类）
 * </pre>
 *
 * <p><b>它只消费两样东西</b>（口径第五十八、七十四节）：正式预测结果（{@link PredictionResult}，
 * 由预测缓存提供）与观察状态（{@link SeedOreObservationTracker}）。它<b>不</b>读 Worker 的
 * ServerLevel、<b>不</b>修改 {@code PredictionResult}、<b>不</b>扫描任何未加载区块、
 * 也<b>不</b>在渲染帧或后台线程里跑。</p>
 *
 * <p><b>三条硬规则</b>：</p>
 * <ol>
 *     <li><b>多样本、多区块、fail-closed</b>（口径第六、十三节）：一个位置确认、一个区块确认、
 *         预测数量相同、{@code parseLong} 成功 —— 这些<b>都不算</b>验证；</li>
 *     <li><b>只认正向证据</b>（口径第十六节）：MISSING 不扣分、不当负票，因为玩家会挖、服务器会改；</li>
 *     <li><b>绑定运行时身份</b>（口径第十九节）：世界 / 种子 / 维度 / 版本 / 会话代号任何一项变化，
 *         整份证据立刻清空 —— Server A 的验证绝不能带到 Server B。</li>
 * </ol>
 *
 * <p><b>运行时专用，不落盘</b>（口径第二十节）：验证结果<b>刻意不</b>写进配置文件，
 * 避免「写死一个 VERIFIED=true 以后每次进服都盲信」。每次进服务器都要重新收集当前会话证据。</p>
 *
 * <p><b>线程模型</b>：只在客户端主线程调用（{@code SeedMiningService.onTick} 的观察变更分支）。
 * 内部因此不加锁 —— 加锁只会掩盖「谁在别的线程碰了客户端状态」这类真问题。</p>
 */
public final class SeedValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed");

    /** 日志关键词。 */
    private static final String LOG_KEY = "种子挖矿｜验证";

    /** 位置键 → 证据（只在客户端主线程读写）。 */
    private final Map<Long, SeedValidationEvidence> evidences = new LinkedHashMap<>();

    /** 当前证据组清单（每次重算后整体替换；不可变）。 */
    private List<SeedValidationEvidenceGroup> groups = List.of();

    /** 当前绑定的运行时身份；{@code null} = 未绑定（此时一律不产出任何结论）。 */
    private SeedRuntimeIdentity identity;

    /**
     * 验证锁存：一旦达到 VERIFIED 就再也不会因为普通 MISSING 掉回去（口径第十八、四十六节）。
     *
     * <p>解锁的唯一条件是「硬冲突判据成立」，而该判据在当前模型下不可达（见
     * {@link SeedValidationPolicy}）。</p>
     */
    private boolean verifiedLatch;

    /** 当前状态与依据。 */
    private SeedValidationState state = SeedValidationState.UNVERIFIED;
    private String reasonCn = "尚未开始验证";

    /**
     * 本次收集窗口的起点（毫秒时间戳；0 = 不设窗口，全部观察都算证据）。
     *
     * <p>它只服务于「重新开始验证」按钮（口径第四十一节）：按下之后，<b>之前</b>已经落在观察层里的
     * 旧读数不再计入验证证据，必须由<b>新的</b>观察（方块更新 / 区块重新加载）产生新样本。
     * 这样按钮才真的等于「重新收集」，而不是「原地重算一遍马上又变已验证」。</p>
     *
     * <p>它<b>不</b>清预测缓存、<b>不</b>清世界、<b>不</b>改种子（口径第四十一节），
     * 也不动观察层（观察层属于 233 冻结范围，本阶段不得修改）。</p>
     */
    private long collectFromMillis;

    /** 对外只读快照（整体替换，界面每帧读它没有任何计算）。 */
    private SeedValidationSnapshot snapshot = SeedValidationSnapshot.EMPTY;

    /** 修订号：结论或证据组发生变化时 +1（诊断 / 开发装置用）。 */
    private long revision;

    /** 累计本次会话确认过的单元数峰值（诊断：报告里的「验证是靠多少单元达成的」）。 */
    private int peakConfirmedUnits;
    private int peakConfirmedChunks;

    // ────────────────────────────────────────────────────────────────────────
    // 生命周期
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 绑定运行时身份；身份不同则<b>整份证据与结论清空</b>。
     *
     * @return 是否发生了清空
     */
    public boolean bind(SeedRuntimeIdentity next) {
        if (Objects.equals(identity, next)) {
            return false;
        }
        boolean cleared = !evidences.isEmpty() || verifiedLatch;
        clearState();
        identity = next;
        if (cleared) {
            LOGGER.info("{}：运行时身份变化，验证证据与结论已清空（{}）", LOG_KEY,
                    next == null ? "未绑定" : next.describeCn());
        }
        return cleared;
    }

    /**
     * 清空验证（退出世界 / 换服务器 / 换维度 / 改种子 / 关功能 / 用户手动「重新开始验证」）。
     *
     * <p>调用方是服务层的唯一失效收口 {@code invalidateRuntime}，因此这六条入口
     * 天然共用同一条清理路径（口径第四十七~五十节）。</p>
     */
    public void reset(String reasonCn) {
        boolean hadSomething = !evidences.isEmpty() || verifiedLatch;
        clearState();
        identity = null;
        if (hadSomething) {
            LOGGER.info("{}：验证已清空（原因：{}）→ 状态回到「{}」", LOG_KEY, reasonCn,
                    state.displayNameCn());
        }
    }

    /** 内部清空：证据、证据组、锁存、结论、读数全部回到初始。 */
    private void clearState() {
        evidences.clear();
        groups = List.of();
        verifiedLatch = false;
        state = SeedValidationState.UNVERIFIED;
        reasonCn = "尚未开始验证";
        collectFromMillis = 0L;
        peakConfirmedUnits = 0;
        peakConfirmedChunks = 0;
        snapshot = SeedValidationSnapshot.EMPTY;
        revision++;
    }

    /**
     * 「重新开始验证」（口径第四十一节）：清空当前 runtime 验证证据，并从这里重新收集。
     *
     * <p>只作用于验证层：不清预测缓存、不清观察层、不改种子、不碰世界。</p>
     *
     * @return 被清掉的证据条数（诊断 / 报告用）
     */
    public int restart() {
        int dropped = evidences.size();
        long now = System.currentTimeMillis();
        evidences.clear();
        groups = List.of();
        verifiedLatch = false;
        state = SeedValidationState.UNVERIFIED;
        reasonCn = "已重新开始验证：旧观察样本不计入，等待新的观察样本";
        collectFromMillis = now;
        peakConfirmedUnits = 0;
        peakConfirmedChunks = 0;
        publish(state, reasonCn, SeedValidationPolicy.inputsOf(groups));
        revision++;
        LOGGER.info("{}：重新开始验证（丢弃旧证据 {} 条；预测缓存 / 观察层 / 种子 / 世界一律未动；"
                + "需要产生新的观察样本 —— 重新加载区块或发生方块更新）", LOG_KEY, dropped);
        return dropped;
    }

    /** 当前是否已绑定运行时身份（未绑定时不产出任何结论）。 */
    public boolean bound() {
        return identity != null;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 推进（客户端主线程；只在观察这一维真的变了时被调用）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 用当前预测结果 + 当前观察状态重算证据与结论。
     *
     * <p>调用时机由服务层保证（观察层修订号变化后才调），因此本方法的代价
     * O(候选数)（默认范围 3 约 1100）只在新观察落地时付一次，<b>不是</b>每刻、更不是每帧。</p>
     *
     * @param results 当前预测缓存里的全部正式结果
     * @param tracker 观察层
     */
    public void update(Collection<PredictionResult> results, SeedOreObservationTracker tracker) {
        if (identity == null || tracker == null || results == null) {
            return;
        }
        long now = System.currentTimeMillis();
        Map<Long, SeedValidationEvidence> rebuilt = new LinkedHashMap<>();
        for (PredictionResult result : results) {
            if (result == null || result.failed()) {
                continue;
            }
            for (PredictedOre ore : result.ores()) {
                long key = ore.position().asLong();
                SeedValidationEvidence previous = evidences.get(key);
                SeedValidationEvidence base = previous != null && previous.targetChunk().equals(result.request().chunk())
                        ? previous
                        : SeedValidationEvidence.create(ore.position(), result.request().chunk(),
                                ore.certainty(), ore.source(), ore.originViewer(), ore.oreType());
                OreObservationState observed = tracker.stateOf(key);
                long observedAt = tracker.lastObservedAt(ore.position());
                boolean effective = observed == OreObservationState.CONFIRMED
                        || observed == OreObservationState.MISSING;
                if (effective && observedAt >= collectFromMillis) {
                    rebuilt.put(key, base.withObservation(observed, tracker.lastObservedBlockId(ore.position()),
                            observedAt > 0L ? observedAt : now));
                } else if (previous != null && (base.observed() || base.everConfirmed())) {
                    // 曾经在本次收集窗口里被观察到（或曾经确认过）：区块卸载 / 缓存淘汰不撤销历史证据
                    // （口径第十六、四十六节：「这里曾经真的是钻石」本身就是对种子正确的证明）
                    rebuilt.put(key, base.withObservation(OreObservationState.UNOBSERVED,
                            base.actualBlockState(), now));
                }
            }
        }
        evidences.clear();
        evidences.putAll(rebuilt);

        // 有效证据单元：按「目标区块 + 写入者 + 来源 + 矿物」整桶聚合，桶内不再裂票
        // （234.1 conservative independence；空间连通簇只作展示，见 SeedValidationEvidenceGroup 类注释）
        List<SeedValidationEvidenceGroup> built = SeedValidationEvidenceGroup.build(new ArrayList<>(evidences.values()));
        Map<Long, Long> groupIdByPosition = new LinkedHashMap<>();
        for (SeedValidationEvidenceGroup group : built) {
            for (BlockPos member : group.members()) {
                groupIdByPosition.put(member.asLong(), group.id());
            }
        }
        Map<Long, SeedValidationEvidence> grouped = new LinkedHashMap<>();
        for (Map.Entry<Long, SeedValidationEvidence> entry : evidences.entrySet()) {
            Long groupId = groupIdByPosition.get(entry.getKey());
            grouped.put(entry.getKey(), groupId == null ? entry.getValue() : entry.getValue().withGroup(groupId));
        }
        evidences.clear();
        evidences.putAll(grouped);
        groups = built;

        // 判定（唯一阈值来源：SeedValidationPolicy）
        SeedValidationPolicy.Inputs inputs = SeedValidationPolicy.inputsOf(groups);
        SeedValidationPolicy.Verdict verdict = SeedValidationPolicy.evaluate(inputs);
        if (verdict.state() == SeedValidationState.VERIFIED) {
            verifiedLatch = true;
        }
        SeedValidationState next = verdict.state();
        String reason = verdict.reasonCn();
        if (verifiedLatch && next != SeedValidationState.CONFLICTING) {
            // 锁存：普通观察变化（例如钻石被挖走）不允许把已验证打回去（口径第十八、四十六节）
            next = SeedValidationState.VERIFIED;
            if (state != SeedValidationState.VERIFIED) {
                reason = "已验证（锁存）：本次会话曾经满足全部验证阈值（" + reason + "）";
            }
        }
        peakConfirmedUnits = Math.max(peakConfirmedUnits, inputs.everConfirmedUnits());
        peakConfirmedChunks = Math.max(peakConfirmedChunks, inputs.everConfirmedChunks());
        publish(next, reason, inputs);
        revision++;
    }

    /** 替换对外快照（界面每帧只读它）。 */
    private void publish(SeedValidationState next, String reason, SeedValidationPolicy.Inputs inputs) {
        if (next != state) {
            LOGGER.info("{}：验证状态 {} → {}（{}）", LOG_KEY, state.displayNameCn(), next.displayNameCn(), reason);
        }
        state = next;
        reasonCn = reason;
        int confirmedPositions = 0;
        int missingPositions = 0;
        java.util.Set<Long> sampleChunks = new java.util.HashSet<>();
        for (SeedValidationEvidence evidence : evidences.values()) {
            if (evidence.observationState() == OreObservationState.CONFIRMED) {
                confirmedPositions++;
            } else if (evidence.observationState() == OreObservationState.MISSING) {
                missingPositions++;
            }
        }
        for (SeedValidationEvidenceGroup group : groups) {
            if (group.observed()) {
                sampleChunks.add(group.targetChunk().pack());
            }
        }
        snapshot = new SeedValidationSnapshot(next, reason, groups.size(), inputs.everConfirmedUnits(),
                inputs.everConfirmedChunks(), inputs.observedUnits(), inputs.ratio(), sampleChunks.size(),
                confirmedPositions, missingPositions, next.allowsAutomatedUse(),
                SeedValidationPolicy.thresholdsCn());
    }

    // ────────────────────────────────────────────────────────────────────────
    // 读数
    // ────────────────────────────────────────────────────────────────────────

    /** 当前验证快照（界面唯一读数入口）。 */
    public SeedValidationSnapshot snapshot() {
        return snapshot;
    }

    /** 当前验证状态。 */
    public SeedValidationState state() {
        return state;
    }

    /** 当前结论依据（中文）。 */
    public String reasonCn() {
        return reasonCn;
    }

    /**
     * <b>未来 AutoMiner 的唯一正式安全门</b>（口径第十节）。
     *
     * <p>规则只有一条：{@code state == VERIFIED} 才返回 {@code true}，其它状态全部 {@code false}。
     * 235 只允许消费本方法，<b>不允许</b>自己重新定义验证规则。</p>
     */
    public boolean mayUseForAutomatedMining() {
        return state.allowsAutomatedUse();
    }

    /** 当前有效证据单元清单（报告与开发装置用；不可变）。 */
    public List<SeedValidationEvidenceGroup> groups() {
        return List.copyOf(groups);
    }

    /**
     * <b>逐单元诊断明细（dev diagnostics 专用）</b>（口径第十三节）。
     *
     * <p>它回答的是「报告里写的『N 个确认单元』究竟由哪些方块、哪些 provenance 构成」：
     * 每个已确认单元单独占一段，段内列出单元编号、目标区块、写入者、来源、矿物、放置身份
     * （正式层没有 → 明确写「未知」）、候选坐标全清单、成员数、确定性构成、provenance 是否已知、
     * 单元内空间簇数。</p>
     *
     * <p><b>只给开发装置用</b>：界面不消费它（口径第五十八节：详细 provenance 只进 dev diagnostics）。</p>
     *
     * @param confirmedOnly 是否只输出「曾经确认过」的单元
     */
    public List<String> unitDiagnosticsCn(boolean confirmedOnly) {
        List<String> lines = new ArrayList<>();
        for (SeedValidationEvidenceGroup unit : groups) {
            if (confirmedOnly && !unit.everConfirmed()) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("单元 #").append(unit.id())
                    .append(" 目标区块(").append(unit.targetChunk().x()).append(',')
                    .append(unit.targetChunk().z()).append(") ")
                    .append(unit.originViewer() == null ? "写入者=未知"
                            : "写入者=(" + unit.originViewer().x() + "," + unit.originViewer().z() + ")")
                    .append(" 来源=").append(unit.oreSource().name())
                    .append(" 矿物=").append(unit.oreType().name())
                    .append(" 放置身份=未知（正式层未逐 placed_feature 归属）")
                    .append(" provenanceKnown=").append(unit.provenanceKnown())
                    .append(" 空间簇=").append(unit.spatialComponents())
                    .append(" 成员=").append(unit.size())
                    .append(" 确定性构成=").append(certaintyCompositionCn(unit))
                    .append(" 当前已确认=").append(unit.confirmedCount())
                    .append(" 当前缺失=").append(unit.missingCount())
                    .append(unit.everConfirmed() ? " 【曾确认】" : "")
                    .append(" 坐标=");
            for (int index = 0; index < unit.members().size(); index++) {
                if (index > 0) {
                    builder.append(' ');
                }
                BlockPos member = unit.members().get(index);
                builder.append('(').append(member.getX()).append(',')
                        .append(member.getY()).append(',').append(member.getZ()).append(')');
            }
            lines.add(builder.toString());
        }
        return lines;
    }

    /** 单元内确定性构成（例如「{调度敏感=3, 未解析=5}」；按首次出现顺序，稳定）。 */
    private String certaintyCompositionCn(SeedValidationEvidenceGroup unit) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (BlockPos member : unit.members()) {
            SeedValidationEvidence evidence = evidences.get(member.asLong());
            String key = evidence == null ? unit.certainty().displayNameCn() : evidence.certainty().displayNameCn();
            counts.merge(key, 1, Integer::sum);
        }
        return counts.toString();
    }

    /** 当前证据清单（报告与开发装置用；不可变）。 */
    public List<SeedValidationEvidence> evidenceList() {
        return List.copyOf(evidences.values());
    }

    /** 某个位置的证据（没有则返回 {@code null}）。 */
    public SeedValidationEvidence evidenceAt(BlockPos pos) {
        return pos == null ? null : evidences.get(pos.asLong());
    }

    /** 修订号（诊断）。 */
    public long revision() {
        return revision;
    }

    /** 本次会话确认单元数峰值 / 覆盖区块峰值（诊断：报告里的「靠多少样本达标」）。 */
    public int peakConfirmedGroups() {
        return peakConfirmedUnits;
    }

    /** 本次会话确认覆盖区块数峰值。 */
    public int peakConfirmedChunks() {
        return peakConfirmedChunks;
    }

    /** 一行诊断（日志 / 报告用）。 */
    public String describeCn() {
        return snapshot.describeCn() + "；证据 " + evidences.size() + " 条，身份 "
                + (identity == null ? "未绑定" : identity.describeCn())
                + "，锁存 " + (verifiedLatch ? "已锁定" : "未锁定");
    }

    /** 硬冲突判据的可用性说明（报告用；当前必为「不可达」）。 */
    public String conflictAvailabilityCn() {
        return SeedValidationPolicy.strongConflictReachable()
                ? "硬冲突判据可用（正式预测器会产出确定性候选）"
                : "硬冲突判据不可达：" + SeedValidationPolicy.strongConflictDisabledReasonCn();
    }
}
