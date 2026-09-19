package com.yiyiaddon.feature.librarian.platform;

import com.yiyiaddon.feature.librarian.model.BlockPosition;
import com.yiyiaddon.feature.librarian.model.HorizontalDirection;
import com.yiyiaddon.feature.librarian.model.VillagerStation;
import com.yiyiaddon.feature.librarian.service.ActionResult;
import com.yiyiaddon.feature.librarian.service.LecternPlacementService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * 自动图书管理员 · 讲台与清障实现（Minecraft 适配）。
 *
 * <p>在固定交易位上放置 / 拆除讲台、清除障碍方块。挖掘采用分步状态机
 * （选工具 → 等工具到位 → 持续挖掘），放置前会校验岩浆块底座并同步玩家朝向。</p>
 *
 * <p><b>三个易错点（照搬旧项目结论，不要"优化"）</b>：</p>
 * <ol>
 *   <li><b>只查 Y+1</b>：障碍与拆除目标都只看讲台所在那一格，Y+2 的活板门卡位不动；</li>
 *   <li><b>先同步朝向再放置</b>：{@code ServerboundMovePlayerPacket.Rot} 必须在 {@code useItemOn}
 *       之前发出（单机同 tick 有序），否则讲台朝向会按旧视角落位；</li>
 *   <li><b>方块名用本地化名</b>：{@code Block#toString()} 输出 {@code Block{minecraft:xxx}}，
 *       给玩家看等于报内部 ID，一律走 {@code getName().getString()}。</li>
 * </ol>
 *
 * <p><b>原语替换（旧框架 → 26.1.2）</b>：{@code InvUtils.findInHotbar/find} → 本地
 * {@link #findHotbar} / {@link #findInventory}；{@code InvUtils.move().from().toHotbar()} →
 * {@link #moveToHotbar}（{@code ContainerInput.SWAP}）；{@code InvUtils.swap()} → {@link #selectHotbar}
 * （{@code setSelectedSlot} + 携带物同步包）。语义与旧项目逐条等价。</p>
 *
 * <p>迁移自旧项目 {@code librarian/integration/FabricLecternPlacementService}（295 行），判据逐条照搬。</p>
 */
public final class LecternOps implements LecternPlacementService {
    /** 工具速度比较基准：与旧项目一致，只有严格快于空手才算「找到工具」 */
    private static final float BASE_DESTROY_SPEED = 1f;
    /** 热键栏槽位上限（0~8） */
    private static final int HOTBAR_LAST_SLOT = 8;
    /** 背包主区槽位范围（9~35） */
    private static final int INVENTORY_FIRST_SLOT = 9;
    private static final int INVENTORY_LAST_SLOT = 35;
    /** 工具搬运落点热键栏槽位（旧项目固定搬到 0 号位） */
    private static final int TOOL_HOTBAR_TARGET = 0;

    // 挖掘状态：0=未初始化，1=等待工具到位，2=正在挖
    private int breakState = 0;
    private int breakWaitTick = 0;
    private BlockPos activeBreakTarget;
    private boolean breaking;

    @Override
    public boolean hasObstacle(VillagerStation station) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;
        BlockPos pos = toPos(station.lecternPosition());
        // 只检查Y+1（讲台位），Y+2是活版门卡位不动
        var state = mc.level.getBlockState(pos);
        if (state.isAir()) return false;
        if (state.is(Blocks.LECTERN)) return false;
        return true;
    }

    /** 返回需要挖的障碍方块（只看Y+1讲台位），无障碍返回null */
    private BlockPos currentBreakTarget(Minecraft mc, VillagerStation station) {
        BlockPos lectern = toPos(station.lecternPosition());
        var state = mc.level.getBlockState(lectern);
        if (state.isAir()) return null;
        if (state.is(Blocks.LECTERN)) return null;
        return lectern;
    }

    @Override
    public ActionResult breakObstacle(VillagerStation station) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || mc.gameMode == null) return ActionResult.retry("游戏未就绪");

        BlockPos pos = currentBreakTarget(mc, station);
        if (pos == null) {
            resetBreaking(mc);
            return ActionResult.success();
        }

        var blockState = mc.level.getBlockState(pos);

        if (activeBreakTarget == null || !activeBreakTarget.equals(pos)) {
            resetBreaking(mc);
            activeBreakTarget = pos.immutable();
        }

        if (breakState == 0) {
            int hotbarBest = bestToolInHotbar(mc, blockState);
            if (hotbarBest >= 0) {
                mc.player.getInventory().setSelectedSlot(hotbarBest);
                breakState = 2;
            } else {
                int invBest = bestToolInInventory(mc, blockState);
                if (invBest >= 0) {
                    moveToHotbar(mc, invBest, TOOL_HOTBAR_TARGET);
                } else {
                    mc.player.getInventory().setSelectedSlot(0);
                }
                breakState = 1;
                breakWaitTick = 0;
            }
            return ActionResult.waiting();
        }

        if (breakState == 1) {
            breakWaitTick++;
            if (breakWaitTick >= 2) {
                mc.player.getInventory().setSelectedSlot(0);
                breakState = 2;
            }
            return ActionResult.waiting();
        }

        // breakState == 2：当前目标方块切换时重置状态，确保重新选工具
        if (!mc.level.getBlockState(pos).equals(blockState)) {
            resetBreaking(mc);
            return ActionResult.waiting();
        }

        Direction hitFace = toDirection(station.lecternFacing());
        if (!breaking) {
            mc.gameMode.startDestroyBlock(pos, hitFace);
            breaking = true;
        } else if (!mc.gameMode.continueDestroyBlock(pos, hitFace)) {
            mc.gameMode.startDestroyBlock(pos, hitFace);
        }
        return ActionResult.waiting();
    }

    @Override
    public ActionResult place(VillagerStation station) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.gameMode == null) {
            return ActionResult.retry("游戏世界尚未就绪。");
        }
        BlockPos target = toPos(station.lecternPosition());

        if (mc.level.getBlockState(target).is(Blocks.LECTERN)) {
            return ActionResult.success();
        }
        if (!mc.level.getBlockState(target).isAir()) {
            // Block 的 toString() 是 "Block{minecraft:xxx}"，给玩家看等于报内部 ID，改用本地化方块名
            return ActionResult.failed("讲台目标位置已被占用：" + blockName(mc.level.getBlockState(target).getBlock()));
        }
        if (!mc.level.getBlockState(target.below()).is(Blocks.MAGMA_BLOCK)) {
            return ActionResult.failed("讲台下方未检测到岩浆块，下方是："
                + blockName(mc.level.getBlockState(target.below()).getBlock()));
        }
        int lecternSlot = findHotbar(mc, Items.LECTERN);
        if (lecternSlot < 0) {
            int inventorySlot = findInventory(mc, Items.LECTERN);
            if (inventorySlot < 0) return ActionResult.failed("背包中没有讲台。");
            moveToHotbar(mc, inventorySlot, TOOL_HOTBAR_TARGET);
            lecternSlot = findHotbar(mc, Items.LECTERN);
            if (lecternSlot < 0) return ActionResult.retry("讲台移至热键栏失败，下次重试。");
        }
        int previousSlot = mc.player.getInventory().getSelectedSlot();
        selectHotbar(mc, lecternSlot);
        // 先同步朝向给服务端，单机环境同tick处理有序，确保放置方向正确
        applyFacing(mc, station.lecternFacing());
        BlockHitResult hit = new BlockHitResult(
            Vec3.atBottomCenterOf(target), Direction.UP, target.below(), false
        );
        InteractionResult result = mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, hit);
        selectHotbar(mc, previousSlot);
        return result == InteractionResult.FAIL
            ? ActionResult.retry("讲台放置交互被拒绝。")
            : ActionResult.waiting();
    }

    @Override
    public ActionResult breakLectern(VillagerStation station) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.gameMode == null) {
            return ActionResult.retry("游戏世界尚未就绪。");
        }
        BlockPos pos = toPos(station.lecternPosition());
        if (mc.level.getBlockState(pos).isAir()) {
            resetBreaking(mc);
            return ActionResult.success();
        }
        if (!mc.level.getBlockState(pos).is(Blocks.LECTERN)) {
            return ActionResult.failed("固定讲台位置存在非讲台方块，拒绝拆除。");
        }
        var blockState = mc.level.getBlockState(pos);
        if (activeBreakTarget == null || !activeBreakTarget.equals(pos)) {
            resetBreaking(mc);
            activeBreakTarget = pos.immutable();
        }

        if (breakState == 0) {
            int hotbarBest = bestToolInHotbar(mc, blockState);
            if (hotbarBest >= 0) {
                mc.player.getInventory().setSelectedSlot(hotbarBest);
                breakState = 2;
            } else {
                int invBest = bestToolInInventory(mc, blockState);
                if (invBest >= 0) {
                    moveToHotbar(mc, invBest, TOOL_HOTBAR_TARGET);
                } else {
                    mc.player.getInventory().setSelectedSlot(0);
                }
                breakState = 1;
                breakWaitTick = 0;
            }
            return ActionResult.waiting();
        }

        if (breakState == 1) {
            breakWaitTick++;
            if (breakWaitTick >= 2) {
                mc.player.getInventory().setSelectedSlot(0);
                breakState = 2;
            }
            return ActionResult.waiting();
        }

        Direction hitFace = toDirection(station.lecternFacing());
        if (!breaking) {
            mc.gameMode.startDestroyBlock(pos, hitFace);
            breaking = true;
        } else if (!mc.gameMode.continueDestroyBlock(pos, hitFace)) {
            mc.gameMode.startDestroyBlock(pos, hitFace);
        }
        return ActionResult.waiting();
    }

    @Override
    public boolean validatePlacement(VillagerStation station) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;
        return mc.level.getBlockState(toPos(station.lecternPosition())).is(Blocks.LECTERN);
    }

    @Override
    public boolean validateRemoval(VillagerStation station) {
        Minecraft mc = Minecraft.getInstance();
        return mc.level != null && mc.level.getBlockState(toPos(station.lecternPosition())).isAir();
    }

    // ═══════════════════════════════════════════════════════════════════
    //  物品栏原语（旧项目走旧框架 InvUtils；本项目换 26.1.2 原语）
    // ═══════════════════════════════════════════════════════════════════

    /** 在热键栏 0~8 中找指定物品，返回槽位号，找不到返回 -1 */
    private int findHotbar(Minecraft mc, Item item) {
        if (mc.player == null) return -1;
        for (int slot = 0; slot <= HOTBAR_LAST_SLOT; slot++) {
            if (mc.player.getInventory().getItem(slot).is(item)) return slot;
        }
        return -1;
    }

    /** 在背包主区 9~35 中找指定物品，返回槽位号，找不到返回 -1 */
    private int findInventory(Minecraft mc, Item item) {
        if (mc.player == null) return -1;
        for (int slot = INVENTORY_FIRST_SLOT; slot <= INVENTORY_LAST_SLOT; slot++) {
            if (mc.player.getInventory().getItem(slot).is(item)) return slot;
        }
        return -1;
    }

    /**
     * 背包格 → 热键栏格。
     *
     * <p>对应旧项目 {@code InvUtils.move().from(背包槽).toHotbar(热键栏槽)}。26.1.2 的等价原语是
     * {@code ContainerInput.SWAP}：{@code slotNum} 传背包槽下标（9~35，与 Inventory 下标同值），
     * {@code buttonNum} 传热键栏下标（0~8），服务端在 {@code AbstractContainerMenu#doClick}
     * 里把两格互换，热键栏为空时即完成整组搬运。</p>
     */
    private void moveToHotbar(Minecraft mc, int inventorySlot, int hotbarSlot) {
        if (mc.player == null || mc.gameMode == null) return;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, inventorySlot, hotbarSlot,
            ContainerInput.SWAP, mc.player);
    }

    /**
     * 切到指定热键栏槽位（旧 {@code InvUtils.swap(slot, false)}）。
     *
     * <p>{@code setSelectedSlot} 只改本地选择槽，必须同时发一次携带物同步包，
     * 否则服务端仍按旧手持物处理右键。</p>
     */
    private void selectHotbar(Minecraft mc, int slot) {
        if (mc.player == null || slot < 0 || slot > HOTBAR_LAST_SLOT) return;
        mc.player.getInventory().setSelectedSlot(slot);
        if (mc.getConnection() != null) {
            mc.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
        }
    }

    /** 在热键栏0-8中找最快工具，返回槽位号，找不到返回-1 */
    private int bestToolInHotbar(Minecraft mc, BlockState blockState) {
        int best = -1;
        float bestSpeed = BASE_DESTROY_SPEED;
        for (int i = 0; i <= HOTBAR_LAST_SLOT; i++) {
            float speed = mc.player.getInventory().getItem(i).getDestroySpeed(blockState);
            if (speed > bestSpeed) { bestSpeed = speed; best = i; }
        }
        return best;
    }

    /** 在背包9-35中找最快工具，返回槽位号，找不到返回-1 */
    private int bestToolInInventory(Minecraft mc, BlockState blockState) {
        int best = -1;
        float bestSpeed = BASE_DESTROY_SPEED;
        for (int i = INVENTORY_FIRST_SLOT; i <= INVENTORY_LAST_SLOT; i++) {
            float speed = mc.player.getInventory().getItem(i).getDestroySpeed(blockState);
            if (speed > bestSpeed) { bestSpeed = speed; best = i; }
        }
        return best;
    }

    /** 把玩家视角朝向同步给服务端（放置方向由服务端的朝向决定，必须先发） */
    private void applyFacing(Minecraft mc, HorizontalDirection facing) {
        Direction placementPlayerFacing = toDirection(facing);
        float yaw = switch (placementPlayerFacing) {
            case SOUTH -> 0.0f;
            case WEST -> 90.0f;
            case NORTH -> 180.0f;
            case EAST -> -90.0f;
            default -> mc.player.getYRot();
        };
        if (mc.getConnection() != null) {
            mc.getConnection().send(
                new ServerboundMovePlayerPacket.Rot(
                    yaw, mc.player.getXRot(), mc.player.onGround(), mc.player.horizontalCollision
                )
            );
        }
    }

    private Direction toDirection(HorizontalDirection direction) {
        return switch (direction) {
            case NORTH -> Direction.NORTH;
            case SOUTH -> Direction.SOUTH;
            case EAST -> Direction.EAST;
            case WEST -> Direction.WEST;
        };
    }

    private BlockPos toPos(BlockPosition position) {
        return new BlockPos(position.x(), position.y(), position.z());
    }

    /** 方块本地化显示名；Block.toString() 是 "Block{minecraft:xxx}"，不能给玩家看 */
    private static String blockName(Block block) {
        return block == null ? "未知方块" : block.getName().getString();
    }

    private void resetBreaking(Minecraft mc) {
        if (mc.gameMode != null && breaking) mc.gameMode.stopDestroyBlock();
        breakState = 0;
        breakWaitTick = 0;
        activeBreakTarget = null;
        breaking = false;
    }
}
