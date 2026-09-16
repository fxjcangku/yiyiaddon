package com.yiyiaddon.feature.stardew.profile;

import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;

/**
 * 温室玻璃逻辑定义。
 *
 * <p>物品模型（预览 / 选择器）与世界方块模型（盆上方识别）是同一件东西的两个表现，聚合为一个
 * 用户选择项，排除其它命名空间的玻璃类资源。选择器持久化 {@code key}，世界探测用
 * {@code blockModel} / {@code identityKey} / {@code itemModel} 三选一命中。</p>
 *
 * <ul>
 *   <li>{@code blockModel}  世界识别模型（如 {@code customcrops:block/greenhouse_glass}）；</li>
 *   <li>{@code identityKey} 绑定身份键（如 {@code customcrops:greenhouse_glass}）；</li>
 *   <li>{@code itemModel}   物品模型（选择器图标）。</li>
 * </ul>
 */
public record ShelterDefinition(
    String key,
    String displayName,
    String itemModel,
    String identityKey,
    RuleEvidence evidence,
    String source,
    String blockModel
) implements StardewToolDefinition {

    @Override
    public StardewSelectorCategory category() {
        return StardewSelectorCategory.SHELTER;
    }
}
