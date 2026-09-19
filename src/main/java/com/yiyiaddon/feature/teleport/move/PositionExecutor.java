package com.yiyiaddon.feature.teleport.move;

import com.yiyiaddon.feature.teleport.model.TeleportContext;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * 发送执行器（L2 执行层）：把决策好的目标落点落到「本地位置 + 服务端发包」。
 *
 * <p>26.1.2 官方机制：本体走 ServerboundMovePlayerPacket.Pos；
 * 载具走 ServerboundMoveVehiclePacket.fromEntity（与 LocalPlayer.tick 的
 * 发送路径完全一致）。onGround 恒为 true —— 目标经过安全判据保证脚下
 * 有真实支撑；horizontalCollision 恒为 false。</p>
 */
public final class PositionExecutor {

    private PositionExecutor() {
    }

    /**
     * 执行位移与发包。
     *
     * @return null 表示发送成功；否则返回失败原因（中文）
     */
    public static String execute(LocalPlayer player, ClientLevel level, TeleportContext ctx) {
        Vec3 feet = ctx.target.feet();
        double x = feet.x();
        double y = feet.y();
        double z = feet.z();

        if (player.isPassenger()) {
            if (ctx.noFallDamage) {
                // 26.1.2 的载具移动处理没有玩家分支的 movedUpwards→resetFallDistance。
                // 继续执行会让设置名为“无伤”却仍可能由载具把摔落伤害传给乘客。
                return "乘坐载具时服务端没有安全的下落累计重置路径，请先下车再使用摔落无伤传送";
            }
            Entity vehicle = player.getRootVehicle();
            // 非本机权威载具（如骑乘其他玩家实体）无法由客户端整体传送
            if (vehicle == null || !vehicle.isLocalInstanceAuthoritative()) {
                return "当前乘坐的载具无法由客户端传送";
            }
            // 保持乘客与载具的相对偏移不变，整体平移载具
            Vec3 offset = player.position().subtract(vehicle.position());
            Vec3 vt = new Vec3(x - offset.x, y - offset.y, z - offset.z);
            vehicle.absSnapTo(vt.x, vt.y, vt.z, vehicle.getYRot(), vehicle.getXRot());
            player.connection.send(ServerboundMoveVehiclePacket.fromEntity(vehicle));

            ctx.executedAsVehicle = true;
            ctx.vehicleSnapshot = vehicle;
            ctx.expectedPos = vt;
        } else {
            player.absSnapTo(x, y, z, player.getYRot(), player.getXRot());
            if (ctx.noFallDamage) {
                // 摔落无伤：先瞬移到目标（onGround=false 避免服务端立即结算摔伤），
                // 再向上微抬 0.001 格触发服务端 movedUpwards→resetFallDistance 清空下落累计，
                // 随后玩家自然回落 0.001 格（不足 3 格无伤害），实现真正无伤落地
                player.resetFallDistance();
                player.connection.send(new ServerboundMovePlayerPacket.Pos(x, y, z, false, false));
                player.connection.send(new ServerboundMovePlayerPacket.Pos(x, y + 0.001, z, false, false));
                // 第二个包才是服务端最终接受的位置；本地同步到同一坐标，避免下一 tick
                // 立刻发送一次反向 0.001 位移造成不必要的客户端/服务端位置差。
                player.absSnapTo(x, y + 0.001, z, player.getYRot(), player.getXRot());
                ctx.expectedPos = new Vec3(x, y + 0.001, z);
            } else {
                player.connection.send(new ServerboundMovePlayerPacket.Pos(x, y, z, true, false));
                ctx.expectedPos = feet;
            }

            ctx.executedAsVehicle = false;
        }

        ctx.executeTick = level.getGameTime();
        return null;
    }
}
