package com.yiyiaddon.platform.identity;

import com.yiyiaddon.model.identity.ItemIdentity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * 物品身份匹配层：把待匹配物品与目标身份集合做「完整身份」匹配。
 *
 * <p>链路为 {@code ItemStack → ItemIdentifier → ItemIdentity → 匹配}。匹配判据与
 * {@link ItemIdentity#identityKey()} 完全一致：物品 ID + 自定义逻辑 ID + 身份显示名 + 附魔。
 * 动态组件（如 {@code craftengine:network_data}）与数量不参与，因此同为基础物品的普通纸与
 * 种植盆可以精确区分。</p>
 */
public final class ItemIdentityMatcher {

    private ItemIdentityMatcher() {
    }

    /**
     * 在目标身份集合中查找第一个命中给定物品的身份。
     *
     * <p>命中的身份连同其身份键一起返回，供差额取物时回查目标数量。</p>
     *
     * @param stack   待匹配物品
     * @param targets 目标身份集合
     * @return 命中的身份；无命中返回 {@code null}
     */
    public static ItemIdentity matchTarget(ItemStack stack, List<ItemIdentity> targets) {
        if (stack == null || stack.isEmpty() || targets == null) return null;
        String key = ItemIdentifier.coreKeyOf(stack);
        if (key.isEmpty()) return null;
        for (ItemIdentity identity : targets) {
            if (identity.identityKey().equals(key)) return identity;
        }
        return null;
    }

    /** 单个物品是否命中给定身份 */
    public static boolean matches(ItemStack stack, ItemIdentity identity) {
        if (stack == null || stack.isEmpty() || identity == null) return false;
        return identity.identityKey().equals(ItemIdentifier.coreKeyOf(stack));
    }
}
