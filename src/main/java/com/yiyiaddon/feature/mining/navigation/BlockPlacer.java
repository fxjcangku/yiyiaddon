package com.yiyiaddon.feature.mining.navigation;

import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 原地往指定空气格里放一块搭路方块（封堵岩浆流入口 / 岩浆垫脚铺路共用，用户 2026-09-18）。
 *
 * <p>两处消费方，同源逻辑只留一份（开发习惯第 169 条）：</p>
 * <ul>
 *   <li>{@code MiningVeinMiner#trySealHole}：贴岩浆的矿物挖开后，往洞里放一块堵住岩浆流入口；</li>
 *   <li>{@code MiningStateMachine} 垫脚铺路：脚层平面附近的岩浆面逐块垫上搭路方块，铺出通路。</li>
 * </ul>
 *
 * <p><b>放置链路</b>：目标空气格 6 面里挑一个「贴得住 + <b>玩家看得见</b>」的面当锚点
 * （面必须 sturdy，否则放出来是掉落状态；看不见就跳过，见 {@link #isFaceVisible} ——
 * 用户 2026-09-18：「不可以穿墙放哈」）→ 只从<b>快捷栏</b>调白名单方块（用户 2026-09-18：「不用副手
 * 快捷栏调用就行了」，白名单由用户在设置里配置）临时切过去 →
 * {@link com.yiyiaddon.platform.network.BlockPacketSender#placeBlockOn}（平台层绕行直发 + 本地立刻落块，
 * 见该方法的说明）→ 挥手 → <b>同刻还原槽位</b>。
 * 同刻还原是硬要求：跨刻持有方块会和秒破的「换工具」互顶主手（秒破每刻按工具写主手，
 * 两边交替覆盖会把工具/方块来回切，表现成卡顿）。</p>
 *
 * <p><b>为什么走平台层绕行通道</b>（用户 2026-09-19：「填岩浆的时候是一个一个放的，能不能一次性铺满」）：
 * 原版 {@code gameMode.useItemOn} 的包要过「发包防踢」的放置限速（默认每秒 8 个），同一刻连放多块会被
 * 按秒拦掉大半 —— 只能 3 刻一块地慢慢铺（见 {@code MiningStateMachine#BRIDGE_PLACES_PER_TICK}）。
 * 垫脚是模块按任务节奏主动发起的批量摆放（与星露谷一次补水连发 8~32 包同类），
 * 走平台层绕行直发才是既定口径（见 {@code ClientPacketSender} 类注释「谁能额外绕行」）。</p>
 */
public final class BlockPlacer {

    private BlockPlacer() {
    }

    /**
     * 往 {@code targetAir}（必须是空气/可替换格）放一块搭路白名单里的方块。
     *
     * @return true 表示放置包已发出（本地已立刻落块）；false = 目标格放不进去 / 没锚点 /
     *         身上没有白名单方块，调用方自行决定是否换目标
     */
    public static boolean placeAt(Minecraft mc, LocalPlayer player, List<String> placeBlocks, BlockPos targetAir) {
        if (mc.level == null || player == null || targetAir == null) return false;

        // 0) 目标格必须真的能放（空气或可替换格）：本地落块是乐观预测，放不进去就会留下幽灵方块
        BlockState targetState = mc.level.getBlockState(targetAir);
        if (!targetState.isAir() && !targetState.canBeReplaced()) return false;

        // 1) 锚点：目标格 6 面里挑一个「贴得住 + 玩家看得见」的面，放的面朝向目标格。
        //    看不见的跳过（用户 2026-09-18：「不可以穿墙放哈」）——隔着岩体的岩浆源、被墙挡住的面，
        //    射线会先撞到墙，于是这一面不算，整体找不到可放面就放弃这一格
        BlockPos anchor = null;
        Direction placeFace = null;
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = targetAir.relative(dir);
            if (!mc.level.getBlockState(neighbor).isFaceSturdy(mc.level, neighbor, dir.getOpposite())) continue;
            Direction face = dir.getOpposite();
            if (!isFaceVisible(mc, player, neighbor, face)) continue;
            anchor = neighbor;
            placeFace = face;
            break;
        }
        if (anchor == null) return false;

        // 2) 找手：只从快捷栏调用（用户 2026-09-18：「不用副手 快捷栏调用就行了」，白名单已由用户配置）
        int placeSlot = -1;
        for (int slot = 0; slot < 9; slot++) {
            if (isPlaceItem(player.getInventory().getItem(slot), placeBlocks)) {
                placeSlot = slot;
                break;
            }
        }
        if (placeSlot == -1) return false;
        Block placeBlock = Block.byItem(player.getInventory().getItem(placeSlot).getItem());
        if (placeBlock == Blocks.AIR) return false;

        int restoreSlot = -1;
        if (player.getInventory().getSelectedSlot() != placeSlot) {
            restoreSlot = player.getInventory().getSelectedSlot();
            player.getInventory().setSelectedSlot(placeSlot);
            if (mc.getConnection() != null) {
                mc.getConnection().send(new ServerboundSetCarriedItemPacket(placeSlot));
            }
        }

        // 3) 放置（平台层绕行直发 + 本地立刻落块）→ 挥手 → 还原槽位（顺序不可换，见类注释「同刻还原」）
        boolean placed = BlockPacketSender.placeBlockOn(InteractionHand.MAIN_HAND, anchor, placeFace,
            targetAir, placeBlock.defaultBlockState());
        if (placed) player.swing(InteractionHand.MAIN_HAND);
        if (restoreSlot >= 0) {
            player.getInventory().setSelectedSlot(restoreSlot);
            if (mc.getConnection() != null) {
                mc.getConnection().send(new ServerboundSetCarriedItemPacket(restoreSlot));
            }
        }
        return placed;
    }

    /** 快捷栏里有没有搭路白名单里的方块——调用方用来区分「没方块」与「没锚点」两种失败 */
    public static boolean hasPlaceBlock(LocalPlayer player, List<String> placeBlocks) {
        for (int slot = 0; slot < 9; slot++) {
            if (isPlaceItem(player.getInventory().getItem(slot), placeBlocks)) return true;
        }
        return false;
    }

    /** 这个物品是不是搭路白名单里的方块（与 {@code MiningContainer#isPlaceBlock} 同一口径） */
    private static boolean isPlaceItem(ItemStack stack, List<String> placeBlocks) {
        if (stack.isEmpty() || placeBlocks.isEmpty()) return false;
        Block block = Block.byItem(stack.getItem());
        if (block == Blocks.AIR) return false;
        return placeBlocks.contains(BuiltInRegistries.BLOCK.getKey(block).toString());
    }

    /**
     * 玩家是否<b>看得见</b>这个面——不穿墙放的判据（用户 2026-09-18：「不可以穿墙放哈」）。
     *
     * <p>做法与原版右键同源：从眼睛朝该面内侧打一条方块轮廓射线
     * （{@code ClipContext.Block.OUTLINE}，与原版客户端拾取方块用的是同一档），
     * 要求<b>第一个命中的就是这块方块、且命中面正是要放的那一面</b>；同时要求该方块在交互距离内。
     * 隔着岩体的岩浆源、被墙挡住的面，射线先撞到墙 → 判不可见 → 换面；六面都不可见就整格放弃，
     * 绝不会隔着墙凭空出现方块。</p>
     *
     * <p>流体不挡射线（{@code ClipContext.Fluid.NONE}）：往岩浆里填方块时，眼睛到锚点那一段
     * 通常泡在岩浆里，按流体挡视线判就一格都封不了。</p>
     */
    private static boolean isFaceVisible(Minecraft mc, LocalPlayer player, BlockPos anchor, Direction face) {
        if (!player.isWithinBlockInteractionRange(anchor, 1.0)) return false;
        Vec3 eye = player.getEyePosition();
        // 瞄点落在锚点内部、离该面 0.05 格处：射线必定穿进这块方块，不会因端点正好贴在表面而漏判
        Vec3 aim = Vec3.atCenterOf(anchor).add(
            face.getStepX() * 0.45, face.getStepY() * 0.45, face.getStepZ() * 0.45);
        BlockHitResult hit = mc.level.clip(new ClipContext(
            eye, aim, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        return hit.getType() == HitResult.Type.BLOCK
            && hit.getBlockPos().equals(anchor)
            && hit.getDirection() == face;
    }
}
