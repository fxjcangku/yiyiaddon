package com.yiyiaddon.seed.prediction;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.worldgen.OfflineChunkCache;
import com.yiyiaddon.seed.worldgen.OfflineChunkPipeline;
import com.yiyiaddon.seed.worldgen.OfflineWorldgenContext;
import com.yiyiaddon.seed.worldgen.OreChunkReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;

/**
 * 种子挖矿正式模块 · 调度敏感分析器。
 *
 * <p><b>它要回答的问题</b>（228 报告定案后的产品语义）：某个被预测出来的钻石坐标，
 * 是不是只要换一种<b>同样合法</b>的 FEATURES 执行情形就会消失？</p>
 *
 * <p><b>本阶段的分类原则（正式化第一阶段口径第二十一、二十二节，逐条对应）</b>：</p>
 * <ol>
 *     <li><b>只做正面证据</b>：只有当「至少两种合法候选执行情形会改变该 BlockPos 是否为目标矿」
 *         被真实观测到时，才标 {@link PredictionCertainty#SCHEDULE_SENSITIVE}。
 *         这里的观测形式是：同一种子下用两种合法 viewer 执行顺序各跑一次纯离线预测，比较最终集合；</li>
 *     <li><b>不做反面推断</b>：<b>禁止</b>「跑了几种顺序结果都一样 ⇒ DETERMINISTIC」。
 *         本轮不产出 {@link PredictionCertainty#DETERMINISTIC}，因为缺少证明
 *         「<b>所有</b>合法并发历史结果相同」的程序；</li>
 *     <li><b>不做 9! 全排列</b>：不穷举 viewer 排列，也不做「9 个 viewer 固定排列比较」，
 *         更不把「邻域先 vs 目标先」这两种情形当成整个状态空间（228 报告已证明真实调度可以细到
 *         feature/write 交错，viewer 整体先后都描述不全）；</li>
 *     <li><b>不依赖 Debug 台账</b>：本类只吃 种子 + 离线 worldgen 自己的输出，
 *         <b>禁止</b>读取 真实世界写入台账 / 真实装饰批号 / 真实生成顺序
 *         （正式化第一阶段口径第二十五节）。</li>
 * </ol>
 *
 * <p><b>为什么不“算了就完事”</b>：未出现差异 ≠ 确定。差异只出现在<b>被观测到有其它 writer
 * 写进目标区块</b>的情形上；本类在没有这种观测时直接放弃复核，把坐标留给 UNRESOLVED——
 * 这样做的代价只是分类更保守，而不会把调度敏感矿误标成确定性矿。</p>
 *
 * <p><b>与世界隔离</b>：复核必须在一份<b>独立</b>的离线世界（自己的 {@link OfflineChunkCache}）里跑，
 * 否则反向顺序写进去的方块会污染基线世界的后续预测。它复用同一个
 * {@link OfflineWorldgenContext}（只读的种子上下文），因此不会重复构造 BiomeSource / RandomState。</p>
 *
 * <p><b>线程安全</b>：本类不做同步，由持有它的 {@code PredictionSession} 串行调用。</p>
 */
public final class ScheduleSensitivityAnalyzer {

    /** 只读的种子上下文（与基线共享，不持有任何世界状态）。 */
    private final OfflineWorldgenContext context;

    /** 复核世界自己的区块缓存（与基线世界完全隔离）。 */
    private final OfflineChunkCache cache = new OfflineChunkCache();

    /** 复核世界自己的流水线。 */
    private final OfflineChunkPipeline pipeline;

    /** 最近一次分析的诊断行（顺序稳定，可进报告）。 */
    private final List<String> notes = new ArrayList<>();

    /** 最近一次分析是否真的执行了复核。 */
    private boolean executed;

    public ScheduleSensitivityAnalyzer(OfflineWorldgenContext context) {
        this.context = context;
        this.pipeline = new OfflineChunkPipeline(context, cache);
    }

    /** 复核流水线（性能统计用）。 */
    public OfflineChunkPipeline pipeline() {
        return pipeline;
    }

    /** 复核世界当前持有的区块数（缓存规模用）。 */
    public int cachedChunks() {
        return cache.size();
    }

    /** 最近一次分析是否执行了反向顺序复核。 */
    public boolean executed() {
        return executed;
    }

    /** 最近一次分析的诊断行。 */
    public List<String> notes() {
        return List.copyOf(notes);
    }

    /**
     * 对当前目标区块做一次保守的调度敏感分析。
     *
     * @param target              目标区块
     * @param oreType             矿物种类
     * @param baselineOres        基线顺序下的预测集合（本方法只判定它的成员是否稳定，
     *                            不会往里添加新坐标）
     * @param foreignWriterViewers 基线执行中「除目标自身外、真的往目标区块里写过方块」的 viewer 个数
     *                             （按目标区块全区块方块指纹判定；= 跨 viewer 竞争存在的前提）
     * @return 应当标 {@link PredictionCertainty#SCHEDULE_SENSITIVE} 的坐标集合
     */
    public Set<BlockPos> analyze(ChunkPos target, OreType oreType, Set<BlockPos> baselineOres,
                                 int foreignWriterViewers) {
        notes.clear();
        executed = false;
        if (foreignWriterViewers <= 0) {
            notes.add("调度敏感分析：基线执行中没有任何其它 viewer 改过目标区块的方块"
                    + " → 不做反向顺序复核；本目标全部按未解析保守登记（不声称确定性）");
            return Set.of();
        }

        executed = true;
        Set<BlockPos> alternate = decorateAlternateOrder(target, oreType);
        Set<BlockPos> sensitive = new LinkedHashSet<>();
        int disappeared = 0;
        int appeared = 0;
        for (BlockPos pos : baselineOres) {
            if (!alternate.contains(pos)) {
                sensitive.add(pos);
                disappeared++;
            }
        }
        for (BlockPos pos : alternate) {
            if (!baselineOres.contains(pos)) {
                appeared++;
            }
        }
        notes.add("调度敏感分析：反向顺序复核（两种合法执行情形）"
                + " → 基线 " + baselineOres.size() + " 个 / 反向 " + alternate.size()
                + " 个；基线有而反向没有 " + disappeared + " 个、反向有而基线没有 " + appeared + " 个");
        notes.add("调度敏感分析判定：将 " + sensitive.size()
                + " 个坐标标为调度敏感（存在两种合法顺序给出不同结果，属正面证据）；"
                + "其余坐标本轮没有证明程序 ⇒ 未解析（不声称确定性）");
        return sensitive;
    }

    /** 清空复核世界（会话生命周期管理用；由 {@code PredictionSession#clear} 调用）。 */
    public void clear() {
        cache.clear();
        notes.clear();
        executed = false;
    }

    /**
     * 在复核世界里按「基线顺序的整体反向」执行一遍合法 FEATURES 调度，返回目标区块的最终矿物集合。
     *
     * <p>反向顺序是<b>另一种完全合法的 Chunk 请求顺序</b>（目标区块先装饰、邻域由近到远随后），
     * 与基线「由远到近、目标最后」构成一对合法候选情形——这正是 228 报告在真实世界上取证过的
     * 那对顺序，因此它给出的差异是真实存在的调度歧义，而不是装置造出来的假差异。</p>
     */
    private Set<BlockPos> decorateAlternateOrder(ChunkPos target, OreType oreType) {
        int writeRadius = OfflineChunkPipeline.featureWriteRadius();
        pipeline.prepare(target, writeRadius);
        List<ChunkPos> viewers = new ArrayList<>(OfflineChunkPipeline.viewersFor(target));
        Collections.reverse(viewers);
        for (ChunkPos viewer : viewers) {
            pipeline.decorate(viewer);
        }
        ChunkAccess chunk = pipeline.featuresChunk(target);
        if (chunk == null) {
            throw new IllegalStateException("复核世界里目标区块未跑到 FEATURES：(" + target.x() + "," + target.z() + ")");
        }
        return OreChunkReader.collect(chunk, oreType);
    }
}
