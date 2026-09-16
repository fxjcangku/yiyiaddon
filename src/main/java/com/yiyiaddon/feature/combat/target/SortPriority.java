package com.yiyiaddon.feature.combat.target;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Comparator;

/**
 * 目标排序优先级（本项目对蓝本 {@code utils/entity/SortPriority} 的重写，语义逐字对齐）。
 *
 * <p>蓝本对应物：{@code utils/entity/SortPriority.java:17-61}，五个枚举项与比较逻辑一一对应：</p>
 * <ul>
 *     <li>{@code LowestDistance}（{@code :18}）：距离近的在前；</li>
 *     <li>{@code HighestDistance}（{@code :19}）：距离远的在前；</li>
 *     <li>{@code LowestHealth}（{@code :20}）：血量低的在前；</li>
 *     <li>{@code HighestHealth}（{@code :21}）：血量高的在前；</li>
 *     <li>{@code ClosestAngle}（{@code :22}）：与准星角度差最小的在前（蓝本 KillAura 默认值）。</li>
 * </ul>
 *
 * <p>血量与角度的比较对「非生物实体」有一套固定的偏序（蓝本 {@code sortHealth :35-44} /
 * {@code sortAngle :46-61}）：两边都不是生物时相等，一个是生物一个不是时生物排前。
 * 这一段照抄，不自行简化。</p>
 */
public enum SortPriority implements Comparator<Entity> {

    /** 蓝本 {@code LowestDistance} */
    LOWEST_DISTANCE("最近距离", Comparator.comparingDouble(SortPriority::squaredDistanceTo)),

    /** 蓝本 {@code HighestDistance} */
    HIGHEST_DISTANCE("最远距离",
            (a, b) -> Double.compare(squaredDistanceTo(b), squaredDistanceTo(a))),

    /** 蓝本 {@code LowestHealth} */
    LOWEST_HEALTH("最低血量", SortPriority::sortHealth),

    /** 蓝本 {@code HighestHealth} */
    HIGHEST_HEALTH("最高血量", (a, b) -> sortHealth(b, a)),

    /** 蓝本 {@code ClosestAngle} */
    CLOSEST_ANGLE("最近角度", SortPriority::sortAngle);

    private final String displayName;
    private final Comparator<Entity> comparator;

    SortPriority(String displayName, Comparator<Entity> comparator) {
        this.displayName = displayName;
        this.comparator = comparator;
    }

    /** 中文文案，供界面使用 */
    public String displayName() {
        return displayName;
    }

    @Override
    public int compare(Entity a, Entity b) {
        return comparator.compare(a, b);
    }

    @Override
    public String toString() {
        return displayName;
    }

    /** 蓝本 {@code PlayerUtils.squaredDistanceTo(Entity)}（{@code PlayerUtils.java:257-259}） */
    private static double squaredDistanceTo(Entity entity) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;
        double dx = mc.player.getX() - entity.getX();
        double dy = mc.player.getY() - entity.getY();
        double dz = mc.player.getZ() - entity.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    /** 蓝本 {@code sortHealth}，{@code :35-44} 逐字 */
    private static int sortHealth(Entity a, Entity b) {
        boolean aLiving = a instanceof LivingEntity;
        boolean bLiving = b instanceof LivingEntity;

        if (!aLiving && !bLiving) return 0;
        if (aLiving && !bLiving) return 1;
        if (!aLiving) return -1;

        return Float.compare(((LivingEntity) a).getHealth(), ((LivingEntity) b).getHealth());
    }

    /** 蓝本 {@code sortAngle}，{@code :46-61} 逐字（yaw 与 pitch 的角度差平方和） */
    private static int sortAngle(Entity a, Entity b) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;

        boolean aLiving = a instanceof LivingEntity;
        boolean bLiving = b instanceof LivingEntity;

        if (!aLiving && !bLiving) return 0;
        if (aLiving && !bLiving) return 1;
        if (!aLiving) return -1;

        double aYaw = Math.abs(AimAngles.yaw(a) - mc.player.getYRot());
        double bYaw = Math.abs(AimAngles.yaw(b) - mc.player.getYRot());

        double aPitch = Math.abs(AimAngles.pitch(a) - mc.player.getXRot());
        double bPitch = Math.abs(AimAngles.pitch(b) - mc.player.getXRot());

        return Double.compare(aYaw * aYaw + aPitch * aPitch, bYaw * bYaw + bPitch * bPitch);
    }
}
