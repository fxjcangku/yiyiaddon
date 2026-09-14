package com.yiyiaddon.feature.stardew.command;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.memory.FarmCellMemory;
import com.yiyiaddon.feature.stardew.memory.FarmMemoryStore;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.RuleEvidence;
import com.yiyiaddon.feature.stardew.profile.StardewCropLifecycle;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestAction;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRule;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.recognition.CropRuntimeStateResolver;
import com.yiyiaddon.feature.stardew.service.StardewProfileAssembler;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 星露谷指令查询与补全支撑：作物名解析、补全候选项、种植意图、人工标记成熟，以及
 * 真实作物状态数据源 {@link CropRuntimeSource}。
 *
 * <p>逐字搬运自旧项目 {@code stardew/StardewFarmModule.java:1449-1828}。旧模块字段
 * {@code index / activeHarvestRules / activeCropSignatures / pointManager / memory / 六类选择}
 * 由本类持有或经构造器注入；档案写盘统一走 {@link StardewProfileAssembler}。</p>
 */
public final class StardewQuerySupport {

    private final StardewResourceIndex index;
    private final StardewProfileAssembler assembler;
    private final StardewPointManager pointManager;
    private final FarmMemoryStore memory;
    private final StardewSettings settings;

    public StardewQuerySupport(StardewResourceIndex index, StardewProfileAssembler assembler,
                               StardewPointManager pointManager, FarmMemoryStore memory,
                               StardewSettings settings) {
        this.index = index;
        this.assembler = assembler;
        this.pointManager = pointManager;
        this.memory = memory;
        this.settings = settings;
    }

    /** 作物 key 的中文显示名（指令播报用；找不到原样返回 key） */
    public String cropDisplayName(String cropKey) {
        return cropDisplayName(index, cropKey);
    }

    /** 作物 key 的中文显示名（资源索引口径的静态形式；档案装配层播报共用） */
    public static String cropDisplayName(StardewResourceIndex index, String cropKey) {
        CropDefinition crop = index.cropByKey(cropKey);
        return crop == null ? "未识别作物（作物 ID：" + cropKey + "）" : crop.chineseName();
    }

    /** 该 cropKey 是否属于当前服务器资源索引（指令前置校验用） */
    public boolean isKnownCrop(String cropKey) {
        return cropKey != null && index.cropByKey(cropKey) != null;
    }

    /**
     * 把玩家输入的作物名 / cropKey 解析为当前服务器唯一的 canonical cropKey。
     *
     * <p>只认「当前资源索引里真实存在」的作物；中文显示名重复时返回 null，
     * 强制玩家改用唯一 cropKey——绝不替玩家猜一个。</p>
     *
     * @return 唯一确定的 cropKey；不存在或不唯一返回 null
     */
    public String canonicalCropKey(String input) {
        if (input == null) return null;
        String text = stripQuotes(input.trim());
        if (text.isEmpty()) return null;

        CropDefinition direct = index.cropByKey(text);
        if (direct != null) return direct.cropKey();

        CropDefinition matched = null;
        for (CropDefinition crop : index.crops()) {
            if (!text.equals(crop.chineseName())) continue;
            if (matched != null) return null;   // 中文名重复：必须用唯一 cropKey
            matched = crop;
        }
        if (matched != null) return matched.cropKey();

        for (CropDefinition crop : index.crops()) {
            if (text.equalsIgnoreCase(crop.cropKey())) return crop.cropKey();
        }
        return null;
    }

    /**
     * 作物补全候选项：canonical cropKey + 中文名（都只来自当前服务器资源索引）。
     *
     * <p>中文名重复的作物只给 cropKey，避免补全出一个解析不唯一的项。</p>
     *
     * @param prefix 已输入前缀（大小写不敏感；带引号时会自动剥离再过滤）
     * @param quoted 是否把候选项包成 Brigadier 字符串参数可解析的带引号形式
     */
    public List<String> cropCompletions(String prefix, boolean quoted) {
        String trimmed = prefix == null ? "" : prefix.trim();
        // 非引号分支（word 参数）无法解析带引号的输入：此时不能补全，否则补出来的文本一按下去就解析失败
        if (!quoted && !trimmed.isEmpty() && (trimmed.charAt(0) == '"' || trimmed.charAt(0) == '\'')) {
            return List.of();
        }
        String lower = stripQuotes(trimmed).toLowerCase(Locale.ROOT);

        Map<String, Integer> nameCounts = new LinkedHashMap<>();
        for (CropDefinition crop : index.crops()) {
            String name = crop.chineseName();
            if (name == null || name.isBlank()) continue;
            nameCounts.merge(name, 1, Integer::sum);
        }

        List<String> result = new ArrayList<>();
        for (CropDefinition crop : index.crops()) {
            addCompletion(result, crop.cropKey(), lower, quoted);
            String name = crop.chineseName();
            if (name == null || name.isBlank()) continue;
            if (nameCounts.getOrDefault(name, 0) > 1) continue;   // 重名只留 cropKey
            addCompletion(result, name, lower, quoted);
        }
        return result;
    }

    /**
     * 阶段补全候选项：只包含该作物在当前服务器资源包里真实存在的阶段。
     *
     * <p>数据来自资源扫描（{@link StardewResourceIndex#stagesOf(String)}），
     * 再加上本服务器档案里已记录过的人工成熟阶段（同样是该服务器的真实证据）。
     * 绝不生成 {@code stage_1~stage_10}，也不混入其它作物的阶段。</p>
     */
    public List<String> stageCompletions(String cropInput, String prefix) {
        String cropKey = canonicalCropKey(cropInput);
        if (cropKey == null) return List.of();

        List<String> stages = new ArrayList<>(index.stagesOf(cropKey));
        StardewHarvestRule rule = assembler.activeHarvestRules().get(cropKey);
        if (rule != null && rule.hasMatureStage() && !stages.contains(rule.matureStage())) {
            stages.add(rule.matureStage());
        }

        String lower = prefix == null ? "" : prefix.trim().toLowerCase(Locale.ROOT);
        List<String> result = new ArrayList<>();
        for (String stage : stages) {
            if (lower.isEmpty() || stage.toLowerCase(Locale.ROOT).startsWith(lower)) result.add(stage);
        }
        return result;
    }

    /** 追加一个补全项（按前缀过滤；带引号模式包成 {@code "名称"}） */
    private static void addCompletion(List<String> out, String value, String lowerPrefix, boolean quoted) {
        if (value == null || value.isBlank()) return;
        if (!lowerPrefix.isEmpty() && !value.toLowerCase(Locale.ROOT).startsWith(lowerPrefix)) return;
        String text = quoted ? "\"" + value + "\"" : value;
        if (!out.contains(text)) out.add(text);
    }

    /**
     * 去掉 Brigadier 字符串参数的包裹引号。
     *
     * <p>已闭合（{@code "番茄"}）整体去掉；只敲了起始引号（{@code "番}）也去掉，
     * 这样玩家边打中文边按 TAB 时补全依然可用。</p>
     */
    private static String stripQuotes(String text) {
        if (text.isEmpty()) return text;
        char first = text.charAt(0);
        if (first != '"' && first != '\'') return text;
        String body = text.substring(1);
        if (!body.isEmpty() && body.charAt(body.length() - 1) == first) {
            body = body.substring(0, body.length() - 1);
        }
        return body.trim();
    }

    /**
     * 真实作物状态数据源：把本模块「当前 ServerKey + 指纹隔离域」里的规则与资源索引
     * 暴露给唯一判定组件 {@link CropRuntimeStateResolver}。
     *
     * <p>只读快照式查询，不提供任何写入方法：人工校准与自动学习的写入口依然唯一
     * （{@link #markMature(String, String, boolean)} 与
     * {@link StardewProfileAssembler#saveLearnedHarvestRule}），因此不可能出现第二套成熟规则库。</p>
     */
    public final class CropRuntimeSource implements CropRuntimeStateResolver.CropRuntimeSource {

        @Override public boolean knownCrop(String cropKey) {
            return cropKey != null && index.cropByKey(cropKey) != null;
        }

        @Override public String cropDisplayName(String cropKey) {
            CropDefinition crop = index.cropByKey(cropKey);
            return crop == null ? null : crop.chineseName();
        }

        @Override public String matureStage(String cropKey) {
            StardewHarvestRule rule = assembler.activeHarvestRules().get(cropKey);
            return rule == null || !rule.hasMatureStage() ? null : rule.matureStage();
        }

        @Override public RuleEvidence ruleEvidence(String cropKey) {
            StardewHarvestRule rule = assembler.activeHarvestRules().get(cropKey);
            return rule == null || !rule.hasMatureStage() ? null : rule.evidence();
        }

        @Override public StardewCropLifecycle lifecycle(String cropKey) {
            StardewHarvestRule rule = assembler.activeHarvestRules().get(cropKey);
            return rule == null || rule.lifecycle() == null ? StardewCropLifecycle.UNKNOWN : rule.lifecycle();
        }

        @Override public String afterHarvestStage(String cropKey) {
            StardewHarvestRule rule = assembler.activeHarvestRules().get(cropKey);
            return rule == null ? null : rule.afterHarvestStage();
        }

        @Override public List<String> stagesOf(String cropKey) {
            return index.stagesOf(cropKey);
        }

        /**
         * 物品 ↔ 作物归属：真实身份键优先，其次 item_model 组件值。
         *
         * <p>顺序必须是「种子 → 品质/特殊变种 → 普通产物」：种子最高优先级，变种先于
         * 普通产物，和库存后勤的唯一角色口径保持一致。</p>
         */
        @Override public CropRuntimeStateResolver.CropRoleRef roleOfItem(String itemModel, String identityKey) {
            for (CropDefinition crop : index.crops()) {
                if (matchesItem(crop.seedKey(), crop.seedModel(), itemModel, identityKey)) {
                    return role(crop, CropRuntimeStateResolver.CropRole.SEED);
                }
            }
            for (CropDefinition crop : index.crops()) {
                if (matchesAny(crop.variantKeys(), crop.variantModels(), itemModel, identityKey)) {
                    return role(crop, CropRuntimeStateResolver.CropRole.VARIANT);
                }
            }
            for (CropDefinition crop : index.crops()) {
                if (matchesAny(crop.produceKeys(), crop.produceModels(), itemModel, identityKey)) {
                    return role(crop, CropRuntimeStateResolver.CropRole.PRODUCE);
                }
            }
            return null;
        }

        private CropRuntimeStateResolver.CropRoleRef role(CropDefinition crop, CropRuntimeStateResolver.CropRole kind) {
            return new CropRuntimeStateResolver.CropRoleRef(crop.cropKey(), crop.chineseName(), kind);
        }

        private boolean matchesItem(String key, String model, String itemModel, String identityKey) {
            if (key != null && identityKey != null && key.equals(identityKey)) return true;
            return model != null && model.equals(itemModel);
        }

        private boolean matchesAny(List<String> keys, List<String> models, String itemModel, String identityKey) {
            if (keys != null && identityKey != null && keys.contains(identityKey)) return true;
            return models != null && itemModel != null && models.contains(itemModel);
        }
    }

    /** 供模块安装到唯一判定组件（旧模块 {@code new CropRuntimeSource()} 的落点） */
    public CropRuntimeStateResolver.CropRuntimeSource runtimeSource() {
        return new CropRuntimeSource();
    }

    /** 给整个农田范围设置种植目标（写入长期记忆） */
    public boolean setRegionPlantIntent(String cropKey) {
        if (!GameProbe.isMultiplayer() || !ResourceExtractionService.isReady() || !settings.selectedCropKeys.contains(cropKey)) return false;
        if (index.cropByKey(cropKey) == null) return false;
        StardewPointManager.StardewPoint start = pointManager.get(StardewPointType.START);
        StardewPointManager.StardewPoint end = pointManager.get(StardewPointType.END);
        if (start == null || end == null) return false;
        int minX = Math.min(start.x(), end.x());
        int maxX = Math.max(start.x(), end.x());
        int minZ = Math.min(start.z(), end.z());
        int maxZ = Math.max(start.z(), end.z());
        int y = start.y();
        String serverKey = StardewContext.serverKey();
        String dimension = StardewContext.dimension();
        boolean saved = true;
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                FarmCellMemory cell = new FarmCellMemory(x, y, z, cropKey, true);
                if (!memory.upsert(serverKey, dimension, cell)) saved = false;
            }
        }
        return saved;
    }

    /**
     * 人工标记成熟的结果码。
     *
     * <p>用结果码而不是 {@code boolean}：调用方必须能区分「阶段不存在」「特殊变种」
     * 「与已有规则冲突」「资源未就绪」这些完全不同的失败，才能给出正确的中文提示；
     * 也才能保证冲突时一个字节都没写盘。</p>
     */
    public enum MatureMarkStatus {
        /** 已保存（首次建立规则，或显式「强制」纠错后覆盖） */
        SAVED,
        /** 已有规则与本次阶段完全一致：幂等确认，未改动任何文件 */
        UNCHANGED,
        /** 与已有可靠成熟规则冲突（普通模式拒绝覆盖） */
        CONFLICT,
        /** 特殊变种阶段（金色 / 巨大 / 变种），不能作为普通成熟阶段 */
        SPECIAL_STAGE,
        /** 阶段不是该作物在当前服务器资源里真实存在的阶段 */
        UNKNOWN_STAGE,
        /** 资源未就绪（环境 / 检测 / 索引 / ServerKey 任一不满足） */
        NOT_READY,
        /** 作物不在当前服务器资源索引 */
        UNKNOWN_CROP,
        /** 落盘失败 */
        FAILED
    }

    /**
     * 人工标记成熟的结果。
     *
     * @param status           结果码
     * @param cropKey          作物键
     * @param stageName        本次要写入的阶段（已归一为资源阶段名）
     * @param previousStage    改动前已记录的成熟阶段；无规则为 null
     * @param previousEvidence 改动前的规则来源；无规则为 null
     */
    public record MatureMarkOutcome(MatureMarkStatus status, String cropKey, String stageName,
                                    String previousStage, RuleEvidence previousEvidence) {
    }

    /**
     * 人工标记成熟的前置闸门：环境 / 资源 / 索引 / ServerKey 任一未满足都不得进入解析。
     *
     * <p>资源尚未检测时真正的问题不是玩家输入，因此调用方必须先问这里，
     * 未通过就既不解析 crop 也不解析 stage，更不写盘。</p>
     */
    public boolean matureMarkAllowed() {
        return GameProbe.isMultiplayer()
            && ResourceExtractionService.isReady()
            && StardewContext.serverKey() != null
            && !index.isEmpty();
    }

    /**
     * 人工标记某作物的成熟阶段（全项目唯一写入口：准星模式 / 参数模式 / 「强制」纠错共用）。
     *
     * <p><b>写盘前必须依次通过：</b></p>
     * <ol>
     *   <li>环境与资源闸门（{@link #matureMarkAllowed()}）；</li>
     *   <li>作物必须存在于当前服务器资源索引；</li>
     *   <li>阶段必须属于该作物在当前服务器资源里真实存在的阶段（或与已记录成熟阶段一致）——
     *       绝不接受任意字符串，也绝不生成 {@code stage_1~stage_10}；</li>
     *   <li>特殊变种阶段（{@code golden/giant/gigantic/variation}）一律拒绝，{@code force} 也不例外：
     *       它们需要独立的收割 / 行为规则；</li>
     *   <li>冲突保护：已有成熟规则且与本阶段不同时，普通模式直接拒绝；
     *       只有显式 {@code force=true}（{@code .stardew 标记成熟 强制}）才允许覆盖。</li>
     * </ol>
     *
     * <p>任何一步失败都<b>不写盘、不覆盖旧规则、不创建规则文件</b>；
     * {@code ServerKey + fingerprint + cropKey} 三层隔离与自动
     * DOCUMENTED / LEARNING / VERIFIED 架构完全不受影响。</p>
     *
     * @param force 仅由显式「强制」分支传入 true（普通模式恒为 false）
     */
    public MatureMarkOutcome markMature(String cropKey, String stageName, boolean force) {
        if (!matureMarkAllowed())
            return new MatureMarkOutcome(MatureMarkStatus.NOT_READY, cropKey, stageName, null, null);
        if (cropKey == null || index.cropByKey(cropKey) == null)
            return new MatureMarkOutcome(MatureMarkStatus.UNKNOWN_CROP, cropKey, stageName, null, null);

        String stage = normalizeStageName(stageName);
        if (stage == null)
            return new MatureMarkOutcome(MatureMarkStatus.UNKNOWN_STAGE, cropKey, stageName, null, null);

        // 冲突判断前必须先让规则表反映本隔离域的已存规则，否则会把已存规则当成「没有规则」而静默覆盖
        String serverKey = StardewContext.serverKey();
        if (assembler.profile() == null) assembler.reload(serverKey);

        StardewHarvestRule previous = assembler.activeHarvestRules().get(cropKey);
        boolean hasPrevious = previous != null && previous.hasMatureStage();
        String previousStage = hasPrevious ? previous.matureStage() : null;
        RuleEvidence previousEvidence = hasPrevious ? previous.evidence() : null;

        if (!isRealStage(cropKey, stage, previousStage))
            return new MatureMarkOutcome(MatureMarkStatus.UNKNOWN_STAGE, cropKey, stage, previousStage, previousEvidence);

        if (CropRuntimeStateResolver.isSpecialStage(stage))
            return new MatureMarkOutcome(MatureMarkStatus.SPECIAL_STAGE, cropKey, stage, previousStage, previousEvidence);

        if (previousStage != null) {
            if (previousStage.equalsIgnoreCase(stage))
                return new MatureMarkOutcome(MatureMarkStatus.UNCHANGED, cropKey, stage, previousStage, previousEvidence);
            if (!force)
                return new MatureMarkOutcome(MatureMarkStatus.CONFLICT, cropKey, stage, previousStage, previousEvidence);
        }

        String fingerprint = ResourceExtractionService.fingerprint();
        String signature = assembler.activeCropSignatures().get(cropKey);
        if (fingerprint == null || signature == null)
            return new MatureMarkOutcome(MatureMarkStatus.NOT_READY, cropKey, stage, previousStage, previousEvidence);

        StardewHarvestRule manual = new StardewHarvestRule(stage,
            StardewHarvestAction.RIGHT_CLICK,
            previous == null ? StardewCropLifecycle.UNKNOWN : previous.lifecycle(),
            previous == null ? null : previous.afterHarvestStage(), RuleEvidence.VERIFIED, signature);
        Map<String, StardewHarvestRule> rules = new LinkedHashMap<>(assembler.activeHarvestRules());
        rules.put(cropKey, manual);
        if (!assembler.saveRulesAndRebuild(serverKey, fingerprint, rules))
            return new MatureMarkOutcome(MatureMarkStatus.FAILED, cropKey, stage, previousStage, previousEvidence);
        return new MatureMarkOutcome(MatureMarkStatus.SAVED, cropKey, stage, previousStage, previousEvidence);
    }

    /**
     * 阶段是否属于该作物在当前服务器资源里真实存在的阶段。
     *
     * <p>数据源是资源扫描结果 {@link StardewResourceIndex#stagesOf(String)}；另外允许
     * 「与该作物已记录的成熟阶段一致」——那是本服务器档案里的真实证据，不是玩家随便输入的字符串。</p>
     */
    private boolean isRealStage(String cropKey, String stage, String recordedStage) {
        for (String real : index.stagesOf(cropKey)) {
            if (real != null && real.equalsIgnoreCase(stage)) return true;
        }
        return recordedStage != null && recordedStage.equalsIgnoreCase(stage);
    }

    /**
     * 把玩家输入的阶段统一成资源阶段名。
     *
     * <p>兼容误输入的完整身份（{@code customcrops:tomato_stage_4}）与大小写差异；
     * 只做字符串归一，不猜测、不生成任何阶段。</p>
     */
    private static String normalizeStageName(String input) {
        if (input == null) return null;
        String text = input.trim();
        if (text.isEmpty()) return null;
        String path = CropRuntimeStateResolver.pathOf(text).toLowerCase(Locale.ROOT);
        String stage = CropRuntimeStateResolver.stageName(path);
        return stage == null ? text : stage;
    }
}
