package com.yiyiaddon.feature.stardew.profile;

import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;

/**
 * 洒水器逻辑定义。
 *
 * <p>Item 模型与 Block 模型是同一个洒水器的不同表现，不是不同洒水器。本定义把同一等级的
 * item 模型（预览）与 block 模型（世界识别）聚合为一个用户选择项，排除
 * {@code default:item/sprinkler} 等模板资源。ESP / 点位 / 定时检查 / 补水都引用同一个
 * {@link SprinklerDefinition}，绝不 GUI 一份、世界识别另一份。</p>
 *
 * <ul>
 *   <li>{@code sprinklerIndex} 洒水器等级序号（1~4）；</li>
 *   <li>{@code blockModel}     世界识别模型（如 {@code customcrops:block/sprinkler/sprinkler_1}）；</li>
 *   <li>{@code itemModel}      用于预览 / 匹配的物品模型。</li>
 * </ul>
 */
public record SprinklerDefinition(
    String key,
    String displayName,
    String itemModel,
    String identityKey,
    RuleEvidence evidence,
    String source,
    int sprinklerIndex,
    String blockModel
) implements StardewToolDefinition {

    @Override
    public StardewSelectorCategory category() {
        return StardewSelectorCategory.SPRINKLER;
    }

    /** 世界识别：给定方块语义身份末段（如 {@code sprinkler_1}）是否命中本洒水器 */
    public boolean matchesWorldKey(String worldKey) {
        if (worldKey == null || blockModel == null) return false;
        String lower = worldKey.toLowerCase(java.util.Locale.ROOT);
        String m = blockModel.toLowerCase(java.util.Locale.ROOT);
        return m.endsWith("/" + lower) || m.equals(lower);
    }
}
