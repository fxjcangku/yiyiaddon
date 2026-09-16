package com.yiyiaddon.feature.combat.target;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

/**
 * 看向目标所需的偏航角 / 俯仰角（本项目对蓝本 {@code Rotations} 的重写，语义逐字对齐）。
 *
 * <p>蓝本对应物：{@code utils/player/Rotations.getYaw(Entity)}（{@code Rotations.java:167-169}）与
 * {@code Rotations.getPitch(Entity, Target.Body)}（{@code :185-199}）。蓝本这两条写法的语义是
 * 「在玩家当前朝向上求目标角度」，返回值等价于绝对角度
 * （{@code playerYaw + wrapDegrees(目标角度 - playerYaw)}），因此既可直接用于转头，
 * 也可用于角度差排序（{@link SortPriority#CLOSEST_ANGLE}）。</p>
 *
 * <p>俯仰角取实体<b>身体中心</b>（{@code getY() + getBbHeight() / 2}），与蓝本一致 ——
 * 蓝本 KillAura 出手时用的就是 {@code Target.Body}，不是头部也不是脚底。</p>
 *
 * <p>本类只做角度计算，不做任何网络动作：本项目没有蓝本 {@code Rotations} 那套
 * 「先发旋转包再动手」的调度层，转头由 {@code KillAuraModule} 用
 * {@code LocalPlayer#setYRot/setXRot} 分步完成（与自动箱子 / 自动挖矿同一做法）。</p>
 */
public final class AimAngles {

    private AimAngles() {
    }

    /** 蓝本 {@code Rotations.getYaw(Entity)}，{@code :167-169} 逐字 */
    public static double yaw(Entity entity) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || entity == null) return 0;
        return mc.player.getYRot() + Mth.wrapDegrees((float) Math.toDegrees(
                Math.atan2(entity.getZ() - mc.player.getZ(), entity.getX() - mc.player.getX()))
                - 90f - mc.player.getYRot());
    }

    /** 蓝本 {@code Rotations.getPitch(Entity, Target.Body)}，{@code :185-199} 逐字 */
    public static double pitch(Entity entity) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || entity == null) return 0;

        double targetY = entity.getY() + entity.getBbHeight() / 2;
        double diffX = entity.getX() - mc.player.getX();
        double diffY = targetY - (mc.player.getY() + mc.player.getEyeHeight());
        double diffZ = entity.getZ() - mc.player.getZ();
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        return mc.player.getXRot() + Mth.wrapDegrees((float) -Math.toDegrees(Math.atan2(diffY, diffXZ))
                - mc.player.getXRot());
    }
}
