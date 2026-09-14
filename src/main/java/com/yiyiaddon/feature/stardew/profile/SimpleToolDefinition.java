package com.yiyiaddon.feature.stardew.profile;

import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;

/**
 * 简单工具逻辑定义（肥料 / 魔法药剂）。
 *
 * <p>肥料与药剂是一对一的物品模型（无需聚合多个世界模型），因此直接用一个简单逻辑对象承载。
 * 分类语义由 {@link StardewSelectorCategory} 严格按 {@code customcrops} 命名空间与家族前缀判定，
 * 绝不使用 {@code contains("magic")} 这类跨命名空间宽泛匹配。</p>
 */
public record SimpleToolDefinition(
    String key,
    String displayName,
    String itemModel,
    String identityKey,
    RuleEvidence evidence,
    String source,
    StardewSelectorCategory category
) implements StardewToolDefinition {
}
