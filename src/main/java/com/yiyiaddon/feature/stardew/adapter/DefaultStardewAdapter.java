package com.yiyiaddon.feature.stardew.adapter;

import com.yiyiaddon.feature.stardew.navigation.FarmNav;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.identity.ItemIdentityMatcher;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.ContainerInput;
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
        mc.getConnection().send(new ServerboundMovePlayerPacket.Rot(
            yaw, pitch, mc.player.onGround(), mc.player.horizontalCollision));
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
        if (mc.player == null || mc.getConnection() == null || slot < 0 || slot > 8) return;
        mc.player.getInventory().setSelectedSlot(slot);
        // 发包层直接发 UseItemOn，不经过 gameMode 的 ensureHasSentCarriedItem；
        // 因而必须在同一有序连接中先同步空手槽，否则服务端仍会按旧手持物处理右键。
        mc.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
    }

    @Override
    public boolean swapToHotbar(int invSlot) {
        if (mc.player == null || mc.gameMode == null) return false;
        if (invSlot < 0 || invSlot >= 36) return false;
        int selected = mc.player.getInventory().getSelectedSlot();
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, invSlot, selected, ContainerInput.SWAP, mc.player);
        return true;
    }

    @Override
    public boolean swapOffhandWith(int invSlot) {
        if (mc.player == null || mc.gameMode == null) return false;
        // 背包槽 9~35：玩家物品栏菜单里这一段与 Inventory 索引一一对应（快捷栏是 36~44，别混用）。
        if (invSlot < 9 || invSlot >= 36) return false;
        // 按钮 40 = 副手在 Inventory 里的索引（0~8 快捷栏 / 9~35 背包 / 36~39 护甲 / 40 副手）。
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, invSlot, OFFHAND_INV_INDEX,
            ContainerInput.SWAP, mc.player);
        return true;
    }

    @Override
    public ItemStack heldItem() {
        return mc.player == null ? ItemStack.EMPTY : mc.player.getMainHandItem();
    }
}
