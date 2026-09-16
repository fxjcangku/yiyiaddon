package com.yiyiaddon.feature.enchant.gear;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

/**
 * 原版装备附魔 · 装备安全守卫（耐久保护）。
 *
 * <p>附魔 / 砂轮 / 铁砧操作会消耗装备耐久，为避免把装备操作到即将损坏，
 * 低于最低耐久比例时不再处理，进入异常流程。</p>
 */
public final class GearSafetyGuard {

    /** 最低耐久比例（默认 20%，低于则停止处理） */
    public static final double MIN_DURABILITY_RATIO = 0.20;

    private GearSafetyGuard() {
    }

    /**
     * 装备是否可安全继续处理。
     * 不可损坏 / 不可破坏 / 无法损毁的装备恒为安全。
     */
    public static boolean isSafe(ItemStack gear) {
        if (gear == null || gear.isEmpty()) return false;
        if (!gear.isDamageableItem()) return true;
        if (gear.has(DataComponents.UNBREAKABLE)) return true;
        int max = gear.getMaxDamage();
        if (max <= 0) return true;
        int remaining = max - gear.getDamageValue();
        return (double) remaining / max >= MIN_DURABILITY_RATIO;
    }

    /** 装备剩余耐久比例（0~1），不可损坏 / 不可破坏返回 1.0，空装备返回 0 */
    public static double durabilityRatio(ItemStack gear) {
        if (gear == null || gear.isEmpty()) return 0.0;
        if (!gear.isDamageableItem() || gear.has(DataComponents.UNBREAKABLE)) return 1.0;
        int max = gear.getMaxDamage();
        if (max <= 0) return 1.0;
        return (double) (max - gear.getDamageValue()) / max;
    }

    /** 装备剩余耐久值，不可损坏返回 {@link Integer#MAX_VALUE} */
    public static int remainingDurability(ItemStack gear) {
        if (gear == null || gear.isEmpty()) return 0;
        if (!gear.isDamageableItem() || gear.has(DataComponents.UNBREAKABLE)) return Integer.MAX_VALUE;
        return gear.getMaxDamage() - gear.getDamageValue();
    }
}
