package com.yiyiaddon.feature.stardew.profile;

import java.util.Map;

/**
 * 星露谷「文档化规则」基线（DOCUMENTED 证据级，非 VERIFIED）。
 *
 * <p>开发期证据来自攻略与参考资源包，发布时已离线固化到 JAR 内的
 * {@code assets/yiyiaddon/stardew/reference-harvest-rules.json}。运行时只读取该类路径资源，
 * 不访问开发目录、外部 JSON 或开发者本地 Profile。</p>
 *
 * <p>与「资源索引」职责分离：索引回答「资源包有什么」，本类回答「攻略/资源能推断出什么规则」。
 * 这些规则一律是 DOCUMENTED（攻略记载），绝不当 VERIFIED（真机确认）；真机观察到不同行为时，
 * 签名不匹配时进入低风险自动学习；用户命令只保留为特殊服务器的人工覆盖兜底。</p>
 *
 * <p>番茄专项：资源包 {@code tomato} 存在 stage_1~stage_4 + stage_golden + stage_variation。
 * 其中 stage_4 模型（tomato_3_4 贴图 + 完整果实立方体）是挂果形态，stage_golden 是金色番茄
 * 特殊变种（需金锄头），stage_variation 是变种模型。攻略明确「火龙果和番茄成熟后回到中段继续
 * 生长」，故番茄成熟阶段记为 stage_4 但标注为 DOCUMENTED，真机若观察到阶段顺序不同
 * （如 stage_1→stage_2→stage_4→stage_3），必须用 mark-mature 覆盖，绝不放任自动收割。</p>
 */
public final class StardewDocumentedRules {

    private StardewDocumentedRules() {
        // 工具类，禁止实例化
    }

    /** 文档化成熟阶段；无记录返回 null */
    public static String matureStage(String cropKey) {
        StardewReferenceRuleLibrary.ReferenceRule rule = StardewReferenceRuleLibrary.rule(cropKey);
        return rule == null ? null : rule.matureStage();
    }

    /** 当前作物资源签名是否与参考 Profile 完全一致。 */
    public static boolean matchesReference(String cropKey, String cropSignature) {
        if (cropKey == null || cropSignature == null) return false;
        StardewReferenceRuleLibrary.ReferenceRule rule = StardewReferenceRuleLibrary.rule(cropKey);
        return rule != null && rule.harvestAction() == StardewHarvestAction.RIGHT_CLICK
            && cropSignature.equals(rule.cropResourceSignature());
    }

    /** 文档化重复采摘（收割后回退）判定 */
    public static boolean regrows(String cropKey) {
        StardewReferenceRuleLibrary.ReferenceRule rule = StardewReferenceRuleLibrary.rule(cropKey);
        return rule != null && rule.documentedLifecycle() == StardewCropLifecycle.REGROW;
    }

    /** 文档化特殊收割判定 */
    public static boolean special(String cropKey) {
        StardewReferenceRuleLibrary.ReferenceRule rule = StardewReferenceRuleLibrary.rule(cropKey);
        return rule != null && rule.specialHarvest();
    }

    /** 基线成熟阶段条目数（供状态页展示「文档化规则 N 条」） */
    public static int matureCount() {
        return StardewReferenceRuleLibrary.size();
    }

    /** 只读成熟阶段快照（供状态页展示） */
    public static Map<String, String> matureStages() {
        Map<String, String> result = new java.util.LinkedHashMap<>();
        StardewReferenceRuleLibrary.snapshot().forEach((cropKey, rule) -> result.put(cropKey, rule.matureStage()));
        return result;
    }
}
