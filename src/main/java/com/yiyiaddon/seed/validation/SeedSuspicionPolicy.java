package com.yiyiaddon.seed.validation;

import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.prediction.PredictionCertainty;
import java.util.ArrayList;
import java.util.List;

/**
 * 种子挖矿正式模块 · <b>SUSPICIOUS 正式语义与硬前提</b>（正式化第六阶段 234）。
 *
 * <h2>一、正式语义（口径第二十一节）</h2>
 * <blockquote>
 *     「客户端当前看到一个钻石矿，但在当前<b>已验证</b>的 Seed + 当前版本 + 当前 worldgen model 下，
 *     它不属于任何已知合法 Seed Candidate。」
 * </blockquote>
 *
 * <h2>二、硬前提（口径第二十二节，八条全部必须同时成立）</h2>
 * <ol>
 *     <li>{@link SeedValidationState#VERIFIED}；</li>
 *     <li>Minecraft 版本正确；</li>
 *     <li>维度受支持（主世界）；</li>
 *     <li>当前模型明确是「26.1.2 原版主世界」；</li>
 *     <li>该区块已由客户端合法加载；</li>
 *     <li>实际方块是 {@code diamond_ore} 或 {@code deepslate_diamond_ore}；</li>
 *     <li>该位置不属于当前模型的任何已知合法钻石候选；</li>
 *     <li><b>已证明「当前 Candidate Universe 对该判断足够完备」</b>。</li>
 * </ol>
 *
 * <h2>三、本阶段的正式结论：自动 SUSPICIOUS <b>继续禁用</b>（口径第二十三、二十四、二十六节）</h2>
 * <p>第 8 条<b>没有证据</b>：当前的 {@code SeedOrePredictor}（236 前叫 {@code DiamondSeedPredictor}）
 * 只能给出<b>当前离线调度模型下</b>
 * 的候选，无法证明它覆盖了「所有合法 FEATURES 细粒度 interleaving」可能产生的最终钻石坐标。
 * 更关键的是 228 已实测：同一 Seed / 版本 / worldgen / 目标区块下，
 * <b>只改变合法的 Chunk 请求顺序</b>，真实最终钻石 BlockPos 就会变 —— 因此
 * 「某位置不在当前候选集里」与「任何合法调度都不可能在那里出现钻石」<b>不是同一句话</b>。</p>
 *
 * <p>于是 234 的正确产物是：<b>SUSPICIOUS 的语义与硬前提被正式定义，自动产生保持禁用</b>。
 * 不为了「本阶段要做 SUSPICIOUS」硬造一个不可靠的假矿检测器。</p>
 *
 * <p><b>与 {@link PredictionCertainty#SCHEDULE_SENSITIVE} 的关系</b>（口径第二十四节）：
 * 调度敏感候选<b>永远不是</b> SUSPICIOUS，{@link #mayClassify} 里第一条否决就是它。</p>
 *
 * <p><b>线程模型</b>：纯函数、无状态。</p>
 */
public final class SeedSuspicionPolicy {

    /**
     * 自动 SUSPICIOUS 是否启用。
     *
     * <p>恒为 {@code false}：第 8 条硬前提（Candidate Universe 完备性）在本阶段无法证明。
     * 要打开它，必须先给出「当前候选集覆盖了全部合法调度可能产生的最终钻石坐标」的证据 ——
     * 那是后续阶段（Round 8 级别）的研究工作，不属于 234。</p>
     */
    public static final boolean AUTO_SUSPICIOUS_ENABLED = false;

    private SeedSuspicionPolicy() {
    }

    /** 自动产生 SUSPICIOUS 是否已启用（235 及以后只允许读这一个判断，不允许自己写判据）。 */
    public static boolean autoSuspiciousEnabled() {
        return AUTO_SUSPICIOUS_ENABLED;
    }

    /** 为什么保持禁用（界面 / 报告共用一份口径，禁止各写一套说法）。 */
    public static String disabledReasonCn() {
        return "自动可疑判定保持禁用：只有先证明「当前候选宇宙覆盖了全部合法世界生成调度可能产生的钻石坐标」，"
                + "才有资格判定某位置「不该出现钻石」。合法 FEATURES 顺序本身就会改变同一位置的最终结果，"
                + "因此调度敏感候选永远不能当作可疑证据。";
    }

    /**
     * 一个待判定位置（调用方提供的读数；本类只做判据检查，不读世界）。
     *
     * @param validationState  当前种子验证状态
     * @param minecraftVersion 当前 Minecraft 版本
     * @param overworld        是否主世界
     * @param vanillaModel    模型是否明确为 26.1.2 原版主世界
     * @param chunkLoaded      该区块是否已由客户端合法加载（P0 红线：绝不主动加载）
     * @param diamondBlock     实际方块是否是钻石矿（两种形态）
     * @param knownCandidate   该位置是否属于当前模型的已知合法候选
     * @param scheduleSensitive 该位置是否被当前模型标为调度敏感
     * @param universeComplete 是否已证明候选宇宙完备
     */
    public record Probe(SeedValidationState validationState, String minecraftVersion, boolean overworld,
                        boolean vanillaModel, boolean chunkLoaded, boolean diamondBlock, boolean knownCandidate,
                        boolean scheduleSensitive, boolean universeComplete) {
    }

    /**
     * 判定某个位置此刻是否允许被标成 {@link OreObservationState#SUSPICIOUS}。
     *
     * <p>返回 {@code null} 表示「不允许」；返回一段中文表示「允许，且原因是这一条」。
     * 当前实现恒返回 {@code null}（禁用）。</p>
     */
    public static String mayClassify(Probe probe) {
        List<String> blockers = blockers(probe);
        return blockers.isEmpty() ? "满足全部硬前提（当前模型下不可能出现）" : null;
    }

    /**
     * 逐条列出「为什么现在不允许标可疑」（诊断 / 报告 / 开发装置用）。
     *
     * @return 未满足的硬前提清单（空表 = 全部满足）
     */
    public static List<String> blockers(Probe probe) {
        List<String> blockers = new ArrayList<>();
        if (!AUTO_SUSPICIOUS_ENABLED) {
            blockers.add("硬前提 8 未满足：候选宇宙完备性未证明 → 自动可疑判定整体禁用");
        }
        if (probe == null) {
            blockers.add("缺少判定读数");
            return blockers;
        }
        if (probe.validationState() != SeedValidationState.VERIFIED) {
            blockers.add("硬前提 1 未满足：种子验证状态为「" + probe.validationState().displayNameCn() + "」");
        }
        if (probe.minecraftVersion() == null || probe.minecraftVersion().isBlank()) {
            blockers.add("硬前提 2 未满足：Minecraft 版本未知");
        }
        if (!probe.overworld()) {
            blockers.add("硬前提 3 未满足：当前不是主世界");
        }
        if (!probe.vanillaModel()) {
            blockers.add("硬前提 4 未满足：当前模型未声明为原版主世界");
        }
        if (!probe.chunkLoaded()) {
            blockers.add("硬前提 5 未满足：该区块尚未由客户端合法加载");
        }
        if (!probe.diamondBlock()) {
            blockers.add("硬前提 6 未满足：实际方块不是钻石矿");
        }
        if (probe.knownCandidate()) {
            blockers.add("硬前提 7 未满足：该位置属于当前模型的已知合法候选");
        }
        if (probe.scheduleSensitive()) {
            blockers.add("调度敏感候选永远不是可疑（228 已实测：合法 FEATURES 顺序会改变该位置最终结果）");
        }
        if (!probe.universeComplete()) {
            blockers.add("硬前提 8 未满足：候选宇宙完备性未证明");
        }
        return blockers;
    }

    /** 对外唯一允许的可疑相关文案（口径第五十二节：界面不得写成「假矿」）。 */
    public static String displayNameCn() {
        return "与当前已验证 Seed 模型不一致";
    }
}
