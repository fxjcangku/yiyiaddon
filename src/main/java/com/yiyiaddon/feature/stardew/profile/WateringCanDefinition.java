package com.yiyiaddon.feature.stardew.profile;

import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;

/**
 * 水壶逻辑定义。
 *
 * <p>把同一把水壶的 item 模型（如 {@code customcrops:item/wateringcan/watering_can_1}）与
 * 真实 ItemIdentity 聚合为一个用户选择项，排除 {@code default:item/watering_can} 等模板资源。
 * 容量 / 使用范围 / 补水规则等玩法数值一律以服务器实测为准，不由资源层伪造。</p>
 *
 * <ul>
 *   <li>{@code canIndex}   水壶等级序号（1~4）；</li>
 *   <li>{@code itemModel}  用于预览 / 匹配的物品模型。</li>
 * </ul>
 */
public record WateringCanDefinition(
    String key,
    String displayName,
    String itemModel,
    String identityKey,
    RuleEvidence evidence,
    String source,
    int canIndex
) implements StardewToolDefinition {

    @Override
    public StardewSelectorCategory category() {
        return StardewSelectorCategory.WATERING_CAN;
    }
}
