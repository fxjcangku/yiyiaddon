package com.yiyiaddon.service.network;

import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

/**
 * 发包服务：面向业务模块的方块交互唯一入口。
 *
 * <p>业务模块只调用本服务，不直接接触 {@code net.minecraft.network.protocol.*}；
 * 所有 sequence 取号、预测窗口、包体构造由 {@link BlockPacketSender} 完成。</p>
 *
 * <p>本服务不持有状态，可被多个模块共用；调用前请自行确认目标方块与手持物已就绪。</p>
 */
public final class PacketService {

    private PacketService() {
    }

    /**
     * 破坏方块（瞬破路径：START + STOP 同 tick 连发）。
     *
     * @param face 破坏朝向，作物一般给 {@link Direction#UP}
     * @return 是否成功发包
     */
    public static boolean breakBlock(BlockPos pos, Direction face) {
        return BlockPacketSender.breakBlock(pos, face);
    }

    /** 对方块使用物品（播种，命中底盘上表面） */
    public static boolean useOnBlock(InteractionHand hand, BlockPos soilPos) {
        return BlockPacketSender.useOnBlock(hand, soilPos);
    }

    /** 对方块本体右键（开容器 / 交互方块实体） */
    public static boolean interactBlock(InteractionHand hand, BlockPos pos, Direction face) {
        return BlockPacketSender.interactBlock(hand, pos, face);
    }

    /** 对方块本体右键使用锄头（锄地） */
    public static boolean tillBlock(InteractionHand hand, BlockPos pos) {
        return BlockPacketSender.tillBlock(hand, pos);
    }

    /**
     * 计算物品剩余耐久；不可损坏物品、已标记不可破坏物品返回 {@link Integer#MAX_VALUE}。
     *
     * <p>供工具防爆使用：剩余耐久低于阈值时应停止破坏换回空手。</p>
     */
    public static int remainingDurability(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return 0;
        if (!stack.isDamageableItem()) return Integer.MAX_VALUE;
        if (stack.has(DataComponents.UNBREAKABLE)) return Integer.MAX_VALUE;
        return stack.getMaxDamage() - stack.getDamageValue();
    }
}
