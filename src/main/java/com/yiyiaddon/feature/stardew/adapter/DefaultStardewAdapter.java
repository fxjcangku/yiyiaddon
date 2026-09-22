package com.yiyiaddon.feature.stardew.adapter;

import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.platform.navigation.FarmNav;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.identity.ItemIdentityMatcher;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

/**
 * 星露谷世界交互默认实现。
 *
 * <p>移动走 {@link FarmNav}（Baritone 隔离层，失败返回 false 而非卡死）；
 * 交互走 {@link BlockPacketSender}（26.1.2 发包层，处理 sequence 预测）；
 * 物品栏切换用 26.1.2 的 {@code handleContainerInput} SWAP。</p>
 */
public final class DefaultStardewAdapter implements StardewAdapter {

    private static final Minecraft mc = Minecraft.getInstance();
    /** 副手在 {@code Inventory} 里的索引（0~8 快捷栏 / 9~35 背包 / 36~39 护甲 / 40 副手） */
    private static final int OFFHAND_INV_INDEX = 40;
    private final Set<Block> passableCarriers = new HashSet<>();

    @Override
    public boolean goTo(BlockPos pos, int radius) {
        return FarmNav.goToThroughFarmCarriers(pos, radius, passableCarriers);
    }

    @Override
    public void updatePassableCarriers(Set<Block> carriers) {
        passableCarriers.clear();
        if (carriers != null) passableCarriers.addAll(carriers);
    }

    @Override
    public boolean pathing() {
        return FarmNav.pathing();
    }

    @Override
    public void cancelPath() {
        FarmNav.cancel();
    }

    @Override
    public boolean arrived(BlockPos pos, double reach) {
        return FarmNav.arrived(pos, reach);
    }

    @Override
    public boolean face(BlockPos pos) {
        if (mc.player == null || mc.getConnection() == null || pos == null) return false;
        Vec3 eye = mc.player.getEyePosition();
        Vec3 target = Vec3.atCenterOf(pos);
        Vec3 diff = target.subtract(eye);
        double horizontal = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
        float yaw = (float) Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90.0F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diff.y, horizontal));
        // 只同步服务端视角，不突然扭动玩家画面；水源服通常会校验朝向与交互射线。
        ClientPacketSender.sendMoveRotation(yaw, pitch, mc.player.onGround(), mc.player.horizontalCollision);
        return true;
    }

    @Override
    public boolean breakBlock(BlockPos pos, Direction face) {
        return BlockPacketSender.breakBlock(pos, face);
    }

    @Override
    public boolean useOnBlock(InteractionHand hand, BlockPos soilPos) {
        return BlockPacketSender.useOnBlock(hand, soilPos);
    }

    @Override
    public boolean interactBlock(InteractionHand hand, BlockPos pos, Direction face) {
        return BlockPacketSender.interactBlock(hand, pos, face);
    }

    @Override
    public int findHotbar(ItemIdentity identity) {
        if (mc.player == null) return -1;
        for (int i = 0; i < 9; i++) {
            if (ItemIdentityMatcher.matches(mc.player.getInventory().getItem(i), identity)) return i;
        }
        return -1;
    }

    @Override
    public int findAny(ItemIdentity identity) {
        if (mc.player == null) return -1;
        for (int i = 0; i < 36; i++) {
            if (ItemIdentityMatcher.matches(mc.player.getInventory().getItem(i), identity)) return i;
        }
        return -1;
    }

    @Override
    public void selectHotbar(int slot) {
        // 发包层直接发 UseItemOn，不经过 gameMode 的 ensureHasSentCarriedItem；
        // 因而必须在同一有序连接中先同步选中槽，否则服务端仍会按旧手持物处理右键与破坏。
        // 走 ClientPacketSender 的绕行通道 —— 这条包一旦被本模组的发包规则拦下，
        // 客户端与服务端就会分叉在「手上那件」上（见该方法的说明）。
        ClientPacketSender.selectHotbar(slot);
    }

    @Override
    public boolean swapToHotbar(int invSlot) {
        return ClientPacketSender.swapToHotbar(invSlot);
    }

    @Override
    public boolean swapOffhandWith(int invSlot) {
        // 按钮 40 = 副手在 Inventory 里的索引（0~8 快捷栏 / 9~35 背包 / 36~39 护甲 / 40 副手）。
        return ClientPacketSender.swapOffhandWith(invSlot, OFFHAND_INV_INDEX);
    }

    @Override
    public ItemStack heldItem() {
        return mc.player == null ? ItemStack.EMPTY : mc.player.getMainHandItem();
    }
}
