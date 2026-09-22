package com.yiyiaddon.feature.stardew.profile;

import com.yiyiaddon.platform.identity.ItemIdentifier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

/**
 * 特殊变种（金色 / 巨型 / 变种）的收割口径：<b>动作 + 手持条件</b>，按「服务器 + 资源指纹 + 作物」学与存。
 *
 * <p><b>为什么动作之外还要记手持</b>：各服口径不止「左键 / 右键」两种（用户 2026-09-22：
 * 「要特殊工具收割的，写死用什么挖就好了」）—— 本服 jmy 的巨型作物是「拿什么都能破坏，空手也行」，
 * 老服是「手持原版金锄头右键」，还有的服是「必须手持某件自定义道具」。所以一次观测要把
 * <b>当时手上那件</b>一起学下来，执行时照着换手；背包里没有那件就只提示、跳过这一格，
 * 不动机器、也不影响收别的作物。</p>
 *
 * <p><b>工具怎么认</b>：原版物品（金锄头这类）没有自定义身份，只按物品本体比对；
 * 带自定义名 / 显示名 / 模型的道具（资源包里的自定义镰刀、锄头）按
 * {@link ItemIdentifier#coreKeyOf} 的身份键比 —— 资源包里同基底（{@code minecraft:golden_hoe}）
 * 的道具会撞车，只按本体认会拿错那一把（第 167、194 条的教训）。</p>
 */
public record StardewSpecialHarvestRecipe(StardewSpecialHarvestAction action, String toolItemId,
                                          String toolIdentityKey, String toolLabel) {

    /** 工具不限：有工具就拿工具挖，没有就拿任何东西（含空手）破坏 */
    public static StardewSpecialHarvestRecipe anyTool(StardewSpecialHarvestAction action) {
        return new StardewSpecialHarvestRecipe(action, null, null, null);
    }

    /** 没学过任何口径时的默认（老服口径：手持原版金锄头右键）；绝不自动试探左键 —— 不可逆 */
    public static final StardewSpecialHarvestRecipe DEFAULT = new StardewSpecialHarvestRecipe(
        StardewSpecialHarvestAction.RIGHT_CLICK, "minecraft:golden_hoe", null, "金锄头");

    /** 工具是否不限（{@code toolItemId} 为空即不限） */
    public boolean toolUnlimited() {
        return toolItemId == null || toolItemId.isBlank();
    }

    /** 口径的稳定比较键（动作 + 工具）：判断「玩家这次的动作是否与本服已知口径不同」用它 */
    public String compareKey() {
        return action.name() + "|"
            + (toolUnlimited() ? "*" : (toolIdentityKey == null || toolIdentityKey.isBlank() ? toolItemId : toolIdentityKey));
    }

    /** 工具显示名（没记到名字时退回物品 ID；不限时读作「不限」） */
    public String toolDisplayName() {
        if (toolUnlimited()) return "不限";
        return toolLabel == null || toolLabel.isBlank() ? toolItemId : toolLabel;
    }

    /** 玩家看得懂的口径描述（播报与日志共用） */
    public String displayName() {
        return toolUnlimited() ? action.displayName() + "（工具不限）" : action.displayName() + "（手持" + toolDisplayName() + "）";
    }

    /** 一件物品是不是本口径要求的那件（不限时永远成立；指定时空手不算） */
    public boolean matchesTool(ItemStack stack) {
        if (toolUnlimited()) return true;
        if (stack == null || stack.isEmpty()) return false;
        if (!BuiltInRegistries.ITEM.getKey(stack.getItem()).toString().equals(toolItemId)) return false;
        if (toolIdentityKey == null || toolIdentityKey.isBlank()) return true;
        return toolIdentityKey.equals(ItemIdentifier.coreKeyWithoutEnchantmentsOf(stack));
    }

    /**
     * 从「玩家刚做的收割动作 + 当时手上那件」生成口径。
     *
     * <p>空手 → 工具不限；原版物品（无自定义名 / 显示名 / 模型）→ 只记本体 ID；
     * 自定义道具 → 记身份键（<b>不含附魔</b>，避免同基底撞车又不受附魔改动影响）。</p>
     *
     * @param action 动作，调用方已校验非空
     */
    public static StardewSpecialHarvestRecipe of(StardewSpecialHarvestAction action, ItemStack held) {
        if (held == null || held.isEmpty()) return anyTool(action);
        String itemId = BuiltInRegistries.ITEM.getKey(held.getItem()).toString();
        boolean vanilla = !held.has(DataComponents.CUSTOM_NAME) && !held.has(DataComponents.ITEM_NAME)
            && !held.has(DataComponents.ITEM_MODEL);
        return new StardewSpecialHarvestRecipe(action, itemId,
            vanilla ? null : ItemIdentifier.coreKeyWithoutEnchantmentsOf(held), held.getHoverName().getString());
    }
}
