package com.yiyiaddon.feature.stardew.service;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.command.StardewQuerySupport;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.RuleEvidence;
import com.yiyiaddon.feature.stardew.profile.StardewCropLifecycle;
import com.yiyiaddon.feature.stardew.profile.StardewCropResourceSignature;
import com.yiyiaddon.feature.stardew.profile.StardewDocumentedRules;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestAction;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRule;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRuleStore;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewServerProfile;
import com.yiyiaddon.feature.stardew.status.StardewStatusReporter;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务器档案装配：把「资源索引 + 逐作物资源签名 + 三层隔离的收获规则」装配成识别器所需的
 * {@link StardewServerProfile}，并负责自动学习成功后的落盘与重建。
 *
 * <p>逐字搬运自旧项目 {@code stardew/StardewFarmModule.java:1301-1378}（档案装配与学习落盘）
 * 与 {@code :641-658}（资源档案就绪判定、就地重建索引）；只把旧模块字段
 * {@code profile / activeHarvestRules / activeCropSignatures / harvestRuleStore} 收进本类，
 * 调用点由模块改为 {@code assembler.xxx()}。</p>
 */
public final class StardewProfileAssembler {

    private final StardewResourceIndex index;
    private final StardewHarvestRuleStore harvestRuleStore = new StardewHarvestRuleStore();
    private final StardewStatusReporter statusReporter;

    private StardewServerProfile profile = null;
    /** 当前 ServerKey + fingerprint 下通过签名复核的规则；其它隔离域绝不进入此表。 */
    private Map<String, StardewHarvestRule> activeHarvestRules = new LinkedHashMap<>();
    /** 当前资源版本逐作物签名，学习保存时必须原样写入并在下次加载复核。 */
    private Map<String, String> activeCropSignatures = new LinkedHashMap<>();

    public StardewProfileAssembler(StardewResourceIndex index, StardewStatusReporter statusReporter) {
        this.index = index;
        this.statusReporter = statusReporter;
    }

    // ── 状态访问（模块与查询层的调用点） ──

    public StardewServerProfile profile() {
        return profile;
    }

    public Map<String, StardewHarvestRule> activeHarvestRules() {
        return activeHarvestRules;
    }

    public Map<String, String> activeCropSignatures() {
        return activeCropSignatures;
    }

    /** 资源会话失效：清空上一服务器的一切档案运行时数据（旧 {@code profile = null; activeHarvestRules = ...}） */
    public void reset() {
        profile = null;
        activeHarvestRules = new LinkedHashMap<>();
        activeCropSignatures = new LinkedHashMap<>();
    }

    /** 载入当前服务器的资源档案（旧模块 {@code profile = loadProfileWithDocumentedRules(serverKey)}） */
    public StardewServerProfile reload(String serverKey) {
        profile = loadProfileWithDocumentedRules(serverKey);
        return profile;
    }

    /**
     * 落盘规则并立即用复核后的规则表重建识别 Profile。
     *
     * <p>旧 {@code markMature()} 的写盘尾部 {@code harvestRuleStore.save → activeHarvestRules = rules
     * → profile = buildActiveProfile(serverKey)} 原样收在这里。</p>
     *
     * @return 是否写盘成功
     */
    public boolean saveRulesAndRebuild(String serverKey, String fingerprint, Map<String, StardewHarvestRule> rules) {
        if (!harvestRuleStore.save(serverKey, fingerprint, rules)) return false;
        activeHarvestRules = rules;
        profile = buildActiveProfile(serverKey);
        return true;
    }

    /**
     * 当前服务器资源档案是否已就绪：环境允许、资源已解析、档案存在且 ServerKey 与当前服务器一致。
     *
     * <p>不满足时整份自检只剩一条「先下载资源包」——其余判据全部依赖资源包解析结果。</p>
     */
    public boolean resourceProfileReady() {
        return ResourceExtractionService.isReady() && profile != null
            && java.util.Objects.equals(profile.serverKey(), ResourceExtractionService.serverKey());
    }

    /**
     * 自检失败后的就地纠错：用当前真实生效的资源包重建一次索引，再重新判定。
     *
     * <p>只重建、<b>不</b>调用 {@code pruneSelectors()}：裁剪会写盘，失败态下不能动玩家选择，
     * 否则「本次旧扫描看不到的键」会被误删。</p>
     *
     * @return true 表示确实重建过，调用方必须重新收集一次问题
     */
    public boolean rebuildIndexForRetry() {
        if (StardewContext.isSingleplayer() || !ResourceExtractionService.isReady()) return false;
        index.rebuild();
        return true;
    }

    /**
     * 载入严格隔离的收获规则，并按逐作物资源签名决定是否允许参考规则进入当前 Profile。
     *
     * <p>旧版仅按服务器保存、缺少资源指纹的阶段档案一律不迁移，避免把历史规则绑定到错误
     * 资源版本；当前版本的人工覆盖也直接写入三层隔离存储。未知资源只进入 LEARNING。</p>
     */
    private StardewServerProfile loadProfileWithDocumentedRules(String serverKey) {
        String fingerprint = ResourceExtractionService.fingerprint();
        Map<String, StardewCropResourceSignature.Signature> computed =
            StardewCropResourceSignature.compute(index.crops(),
                cropKey -> !index.stagesOf(cropKey).isEmpty());
        activeCropSignatures = new LinkedHashMap<>();
        computed.forEach((cropKey, signature) -> activeCropSignatures.put(cropKey, signature.value()));

        Map<String, StardewHarvestRule> stored = harvestRuleStore.load(serverKey, fingerprint);
        activeHarvestRules = new LinkedHashMap<>();
        for (Map.Entry<String, StardewHarvestRule> entry : stored.entrySet()) {
            String currentSignature = activeCropSignatures.get(entry.getKey());
            if (currentSignature != null && currentSignature.equals(entry.getValue().cropResourceSignature())) {
                activeHarvestRules.put(entry.getKey(), entry.getValue());
            }
        }

        LinkedHashMap<String, String> verified = new LinkedHashMap<>();
        LinkedHashMap<String, String> documented = new LinkedHashMap<>();
        for (CropDefinition crop : index.crops()) {
            StardewHarvestRule rule = activeHarvestRules.get(crop.cropKey());
            if (rule != null && rule.hasMatureStage() && rule.evidence() == RuleEvidence.VERIFIED) {
                verified.put(crop.cropKey(), rule.matureStage());
                continue;
            }
            String signature = activeCropSignatures.get(crop.cropKey());
            String stage = StardewDocumentedRules.matureStage(crop.cropKey());
            if (stage == null || !StardewDocumentedRules.matchesReference(crop.cropKey(), signature)) continue;
            documented.put(crop.cropKey(), stage);
            activeHarvestRules.put(crop.cropKey(), new StardewHarvestRule(stage,
                StardewHarvestAction.RIGHT_CLICK, StardewCropLifecycle.UNKNOWN, null,
                RuleEvidence.DOCUMENTED, signature));
        }
        List<String> regrow = activeHarvestRules.entrySet().stream()
            .filter(entry -> entry.getValue().completeVerified()
                && entry.getValue().lifecycle() == StardewCropLifecycle.REGROW)
            .map(Map.Entry::getKey).toList();
        return new StardewServerProfile(serverKey, verified, regrow, List.of(),
            StardewServerProfile.VERSION, documented);
    }

    /** 自动学习成功后原子保存完整 VERIFIED 规则，并立即刷新识别 Profile。 */
    public void saveLearnedHarvestRule(StardewCoordinator.LearnedHarvest learned) {
        if (learned == null || learned.cropKey() == null) return;
        String serverKey = StardewContext.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        String signature = activeCropSignatures.get(learned.cropKey());
        if (fingerprint == null || signature == null || index.cropByKey(learned.cropKey()) == null) {
            // 学出来却存不下去必须说出来：静默返回会让玩家看到「正在收获」循环却永远没有规则，
            // 完全不知道断在哪（实机反馈：熟了不收、也不报错）。
            if (signature == null && index.cropByKey(learned.cropKey()) != null) {
                statusReporter.state("LEARN_NO_SIGNATURE:" + learned.cropKey(), "收获规则未能保存",
                    "当前服务器资源里找不到该作物的阶段证据，无法建立逐作物签名");
            }
            return;
        }
        StardewHarvestRule rule = new StardewHarvestRule(learned.matureStage(),
            StardewHarvestAction.RIGHT_CLICK, learned.lifecycle(), learned.afterHarvestStage(),
            RuleEvidence.VERIFIED, signature);
        Map<String, StardewHarvestRule> updated = new LinkedHashMap<>(activeHarvestRules);
        updated.put(learned.cropKey(), rule);
        if (!harvestRuleStore.save(serverKey, fingerprint, updated)) {
            statusReporter.critical("LEARN_SAVE_FAIL:" + learned.cropKey(), "收获学习保存失败", "当前结果未启用");
            return;
        }
        activeHarvestRules = updated;
        profile = buildActiveProfile(serverKey);
        // 成功文案把「成熟阶段」写出来（实机反馈：只看到「收获规则已确认 · 菠萝，一次性补种」时
        // 玩家分不清这算不算学习成功，也不知道学到的成熟阶段是哪一个）
        statusReporter.state("LEARN_OK:" + learned.cropKey(), "收获规则已确认",
            StardewQuerySupport.cropDisplayName(index, learned.cropKey())
                + "（成熟阶段 " + learned.matureStage() + "），"
                + (learned.lifecycle() == StardewCropLifecycle.REGROW ? "保株再生" : "一次性补种"));
    }

    /** 从当前已复核规则表重建识别器所需的轻量服务器 Profile。 */
    private StardewServerProfile buildActiveProfile(String serverKey) {
        LinkedHashMap<String, String> verified = new LinkedHashMap<>();
        LinkedHashMap<String, String> documented = new LinkedHashMap<>();
        List<String> regrow = new ArrayList<>();
        for (Map.Entry<String, StardewHarvestRule> entry : activeHarvestRules.entrySet()) {
            StardewHarvestRule rule = entry.getValue();
            if (!rule.hasMatureStage()) continue;
            if (rule.evidence() == RuleEvidence.VERIFIED) verified.put(entry.getKey(), rule.matureStage());
            else if (rule.evidence() == RuleEvidence.DOCUMENTED) documented.put(entry.getKey(), rule.matureStage());
            if (rule.completeVerified() && rule.lifecycle() == StardewCropLifecycle.REGROW) regrow.add(entry.getKey());
        }
        return new StardewServerProfile(serverKey, verified, regrow, List.of(),
            StardewServerProfile.VERSION, documented);
    }
}
