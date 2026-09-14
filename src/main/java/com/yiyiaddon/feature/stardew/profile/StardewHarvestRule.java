package com.yiyiaddon.feature.stardew.profile;

/**
 * 单个作物、单个服务器资源版本下的收获规则。
 *
 * <p>完整 VERIFIED 规则必须同时具备成熟阶段、右键动作、生命周期和作物资源签名。
 * 重复采摘还必须记录收获后的回退阶段；任何字段不完整时都只能继续 LEARNING，
 * 不能当成可无条件复用的已确认规则。</p>
 */
public record StardewHarvestRule(
    String matureStage,
    StardewHarvestAction harvestAction,
    StardewCropLifecycle lifecycle,
    String afterHarvestStage,
    RuleEvidence evidence,
    String cropResourceSignature
) {

    /** 当前规则是否拥有可直接执行并验证的完整真机证据。 */
    public boolean completeVerified() {
        if (evidence != RuleEvidence.VERIFIED || blank(matureStage)
            || harvestAction != StardewHarvestAction.RIGHT_CLICK
            || blank(cropResourceSignature) || lifecycle == null || lifecycle == StardewCropLifecycle.UNKNOWN) {
            return false;
        }
        return lifecycle != StardewCropLifecycle.REGROW || !blank(afterHarvestStage);
    }

    /** 文档匹配或人工阶段覆盖是否至少提供了可识别的成熟阶段。 */
    public boolean hasMatureStage() {
        return !blank(matureStage);
    }

    /** 空白判断集中处理，防止损坏 JSON 的空字符串被当成有效规则。 */
    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }
}
