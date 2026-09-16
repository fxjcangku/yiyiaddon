package com.yiyiaddon.feature.autofarm.controller;

import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.feature.autofarm.model.FarmTarget;
import com.yiyiaddon.feature.autofarm.scan.FarmScanner;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 统一结果验证器：读取世界实际 BlockState 确认动作是否真的生效。
 *
 * 与旧架构「发破坏包返回 true 就算收割成功」相反，这里必须读到世界状态
 * 真正发生变化才判定成功，服务器回滚或发包失败都会判定失败并重新规划。
 */
public final class FarmVerifier {

    /**
     * 收割是否成功：目标方块已不再是「可收割的成熟状态」。
     * 被破坏变空气、或方块类型发生变化（不再匹配作物）都算成功。
     */
    public boolean harvestSucceeded(FarmTarget target) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;

        BlockState state = mc.level.getBlockState(target.pos());
        if (state.isAir()) return true;

        CropProfile profile = CropProfile.byBlock(state.getBlock());
        if (profile == null) return true;
        return !profile.isHarvestable(state, mc.level, target.pos());
    }

    /**
     * 补种是否成功：底盘上方的方块已经变成目标作物本体。
     */
    public boolean plantSucceeded(FarmTarget target) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;

        BlockState above = mc.level.getBlockState(target.pos().above());
        return above.is(target.profile().block());
    }

    /**
     * 锄地是否成功：目标方块已经变成耕地。
     */
    public boolean tillSucceeded(FarmTarget target) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;

        return mc.level.getBlockState(target.pos()).is(Blocks.FARMLAND);
    }

    /** 目标是否仍然有效（仍可收割），用于执行前二次确认 */
    public boolean targetStillValid(FarmTarget target) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;

        if (target.type() == FarmTarget.TargetType.HARVEST) {
            BlockState state = mc.level.getBlockState(target.pos());
            CropProfile profile = CropProfile.byBlock(state.getBlock());
            return profile != null && profile.isHarvestable(state, mc.level, target.pos());
        }

        if (target.type() == FarmTarget.TargetType.TILL) {
            return FarmScanner.isTillable(mc.level, target.pos());
        }

        // 补种目标：底盘仍正确且上方仍为空
        return target.profile().isPlantable(mc.level, target.pos());
    }
}
