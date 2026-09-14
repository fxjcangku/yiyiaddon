package com.yiyiaddon.platform;

import com.yiyiaddon.model.PlayerActivity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

/**
 * 玩家状态采样：把 {@link LocalPlayer} 的实时状态整理成后端需要的活动快照。
 */
public final class PlayerSampler {

    private PlayerSampler() {
    }

    /** 采样当前活动；玩家不在世界内返回 {@code null}。 */
    public static PlayerActivity sample() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return null;
        try {
            return new PlayerActivity(
                    player.getX(), player.getY(), player.getZ(),
                    dimensionName(player),
                    player.getHealth(),
                    player.getFoodData().getFoodLevel(),
                    mc.gameMode == null ? "unknown" : mc.gameMode.getPlayerMode().getName(),
                    activity(player));
        } catch (Exception e) {
            return null;
        }
    }

    /** 维度标识：主世界 / 下界 / 末地，其余原样返回原始键。 */
    public static String dimensionName(LocalPlayer player) {
        try {
            String key = player.level().dimension().identifier().getPath();
            if (key.contains("overworld")) return "overworld";
            if (key.contains("the_nether")) return "the_nether";
            if (key.contains("the_end")) return "the_end";
            return key;
        } catch (Exception e) {
            return "unknown";
        }
    }

    /** 活动描述，按优先级依次判定。 */
    public static String activity(LocalPlayer player) {
        Minecraft mc = Minecraft.getInstance();
        try {
            double dx = player.getX() - player.xOld;
            double dy = player.getY() - player.yOld;
            double dz = player.getZ() - player.zOld;
            double horizontal = Math.sqrt(dx * dx + dz * dz);

            if (player.hurtTime > 0) return "战斗中";
            if (player.getAbilities().flying) return "飞行中";
            if (player.isSprinting() && horizontal > 0.1) return "疾跑中";
            if (player.isShiftKeyDown()) return "潜行中";
            if (horizontal > 0.05) return "移动中";
            if (Math.abs(dy) > 0.1) return dy > 0 ? "跳跃中" : "下落中";
            if (player.isInWater()) return "游泳中";
            if (mc.gameMode != null && mc.gameMode.isDestroying()) return "挖掘中";
            return "静止";
        } catch (Exception e) {
            return "unknown";
        }
    }
}
