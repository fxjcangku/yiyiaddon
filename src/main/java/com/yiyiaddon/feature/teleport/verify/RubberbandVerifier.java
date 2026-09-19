package com.yiyiaddon.feature.teleport.verify;

import com.yiyiaddon.feature.teleport.model.TeleportContext;
import net.minecraft.world.phys.Vec3;

/**
 * 服务器回弹侦测（验证层）：观察服务端位置权威包得出的「绝对坐标」——
 * 玩家 {@code ClientboundPlayerPositionPacket} / 载具 {@code ClientboundMoveVehiclePacket}，
 * 与服务端预期位置对比得出「接受 / 回弹」结论。
 *
 * <p>只观察不拦截：包照常交原版处理，保证客户端最终与服务端一致；
 * 传送后若服务端接受则不会回位置包（超时即成功），回位置包即权威修正。</p>
 *
 * <p><b>绝对坐标换算在平台层完成一次</b>（旧项目两行
 * {@code PositionMoveRotation.calculateAbsolute(PositionMoveRotation.of(player), p.change(), p.relatives())}
 * 的同源实现，现由 {@code SERVER_POSITION} 派发统一承担，第 169 条），
 * 本类只做「包来源匹配 + 距离裁决」，因此不再接触包对象。</p>
 */
public final class RubberbandVerifier {

    /** 与本传送无关的包（或非验证状态） */
    public static final int IGNORED = 0;
    /** 服务端位置 ≈ 预期：接受本次传送 */
    public static final int CONFIRM = 1;
    /** 服务端位置偏离预期：回弹/拉回 */
    public static final int RUBBERBAND = 2;

    private RubberbandVerifier() {
    }

    /**
     * 裁决：仅在验证窗口内由协调器调用。
     * 命中权威包时会把服务端位置与偏差记入上下文（供播报与渲染）。
     *
     * @param serverPos     服务端权威位置（绝对坐标，平台层已换算）
     * @param vehiclePacket 该位置来自载具包（{@code ClientboundMoveVehiclePacket}）
     * @param ctx           当前传送上下文
     */
    public static int classify(Vec3 serverPos, boolean vehiclePacket, TeleportContext ctx) {
        if (ctx == null || ctx.expectedPos == null || serverPos == null) return IGNORED;
        // 与旧项目一致：本体传送只看玩家包，载具传送只看载具包
        if (vehiclePacket != ctx.executedAsVehicle) return IGNORED;
        return judge(serverPos, ctx);
    }

    /** 距离裁决并记录观测值 */
    private static int judge(Vec3 serverPos, TeleportContext ctx) {
        double dist = serverPos.distanceTo(ctx.expectedPos);
        ctx.rubberbandPos = serverPos;
        ctx.rubberbandDist = dist;
        return dist <= ctx.verifyThreshold ? CONFIRM : RUBBERBAND;
    }
}
