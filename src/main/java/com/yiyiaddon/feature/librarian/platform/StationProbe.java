package com.yiyiaddon.feature.librarian.platform;

import com.yiyiaddon.feature.librarian.model.BlockPosition;
import com.yiyiaddon.feature.librarian.model.HorizontalDirection;
import com.yiyiaddon.feature.librarian.model.StationValidationStatus;
import com.yiyiaddon.feature.librarian.model.VillagerStation;
import com.yiyiaddon.feature.librarian.model.VillagerTarget;
import com.yiyiaddon.feature.librarian.service.MarkerBlockAccess;
import com.yiyiaddon.feature.librarian.service.MarkerBlockValidation;
import com.yiyiaddon.feature.librarian.service.MarkerBlockValidator;
import com.yiyiaddon.feature.librarian.service.VillagerStationService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * 自动图书管理员 · 固定交易位实现（Minecraft 适配）。
 *
 * <p>根据村民位置与朝向探测可用交易位（岩浆块 + 玩家站位 + 讲台位），
 * 并执行交易位校验与方块查询。</p>
 *
 * <p><b>两遍探测策略（行为的一部分，禁止简化）</b>：</p>
 * <ol>
 *   <li><b>第一遍（优先）</b>：只接受「讲台位已空闲」的岩浆块（{@code canPlaceLectern} 直接为真），
 *       命中即返回 —— 这样 Baritone 不用为了放讲台先去挖方块；</li>
 *   <li><b>第二遍（fallback）</b>：没有裸露岩浆块时收集所有候选（岩浆块 + 站位可站立），
 *       取讲台位<b>离玩家最近</b>的一个 —— 不按方向枚举顺序取，否则会优先选中村民背后的被埋岩浆块。</li>
 * </ol>
 * 扫描层级固定 {@code y ∈ {0, -1}}：岩浆块在地面时村民站在其上方，两个层级都要看。</p>
 *
 * <p>迁移自旧项目 {@code librarian/integration/FabricVillagerStationService}（173 行），判据逐条照搬。</p>
 */
public final class StationProbe implements VillagerStationService, MarkerBlockAccess {
    /** UUID 兜底扫描半径（格） */
    private static final int UUID_FALLBACK_RADIUS = 64;

    private final MarkerBlockValidator validator = new MarkerBlockValidator(this);

    @Override
    public Optional<VillagerStation> detect(VillagerTarget target) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return Optional.empty();
        // 先用 entity ID 快速查，失败时用 UUID 扫描兜底
        Villager villager = resolve(mc, target);
        if (villager == null) return Optional.empty();

        BlockPosition villagerPos = new BlockPosition(villager.getBlockX(), villager.getBlockY(), villager.getBlockZ());

        // 检查同Y层和Y-1层（岩浆块在地面时村民站在上方）
        int[] yOffsets = {0, -1};

        // 第一遍：优先选择讲台位无障碍的岩浆块（canPlaceLectern 直接为 true），
        // 避免优先选中被方块覆盖的后方岩浆块导致 Baritone 去挖方块
        for (int dy : yOffsets) {
            BlockPosition scanPos = new BlockPosition(villagerPos.x(), villagerPos.y() + dy, villagerPos.z());
            for (HorizontalDirection dir : HorizontalDirection.values()) {
                BlockPosition adjacent = scanPos.offset(dir);
                BlockPosition rawStand = adjacent.offset(dir);
                BlockPosition standPos = new BlockPosition(rawStand.x(), villagerPos.y(), rawStand.z());
                if (mc.level.getBlockState(toPos(adjacent)).is(Blocks.MAGMA_BLOCK)
                        && isPlayerStandable(mc, standPos)
                        && canPlaceLectern(adjacent.up())) {  // 讲台位已经空闲，无需挖方块
                    BlockPosition effectiveVillagerPos = dy == 0 ? villagerPos
                        : new BlockPosition(villagerPos.x(), villagerPos.y() - 1, villagerPos.z());
                    return Optional.of(VillagerStation.create(
                        target.uuid(), effectiveVillagerPos, dir, StationValidationStatus.UNVALIDATED
                    ));
                }
            }
        }

        // 第二遍：无裸露岩浆块时，接受需要清除障碍的岩浆块（fallback）
        // 收集所有候选，选离玩家最近的，避免因方向枚举顺序优先选中背后被埋的岩浆块
        Vec3 playerPos = mc.player != null
            ? mc.player.position()
            : new Vec3(villagerPos.x(), villagerPos.y(), villagerPos.z());
        BlockPosition bestAdjacentFallback = null;
        HorizontalDirection bestDirFallback = null;
        int bestDyFallback = 0;
        double bestDistFallback = Double.MAX_VALUE;
        for (int dy : yOffsets) {
            BlockPosition scanPos = new BlockPosition(villagerPos.x(), villagerPos.y() + dy, villagerPos.z());
            for (HorizontalDirection dir : HorizontalDirection.values()) {
                BlockPosition adjacent = scanPos.offset(dir);
                BlockPosition rawStand = adjacent.offset(dir);
                BlockPosition standPos = new BlockPosition(rawStand.x(), villagerPos.y(), rawStand.z());
                if (mc.level.getBlockState(toPos(adjacent)).is(Blocks.MAGMA_BLOCK)
                        && isPlayerStandable(mc, standPos)) {
                    // 计算讲台放置位（岩浆块上方）到玩家的距离
                    BlockPosition lecternPos = adjacent.up();
                    Vec3 lecternVec = new Vec3(lecternPos.x(), lecternPos.y(), lecternPos.z());
                    double dist = playerPos.distanceToSqr(lecternVec);
                    if (dist < bestDistFallback) {
                        bestDistFallback = dist;
                        bestAdjacentFallback = adjacent;
                        bestDirFallback = dir;
                        bestDyFallback = dy;
                    }
                }
            }
        }
        if (bestAdjacentFallback != null) {
            BlockPosition effectiveVillagerPos = bestDyFallback == 0 ? villagerPos
                : new BlockPosition(villagerPos.x(), villagerPos.y() - 1, villagerPos.z());
            return Optional.of(VillagerStation.create(
                target.uuid(), effectiveVillagerPos, bestDirFallback, StationValidationStatus.UNVALIDATED
            ));
        }
        return Optional.empty();
    }

    @Override
    public MarkerBlockValidation validate(VillagerStation station) {
        return validator.validate(station);
    }

    @Override
    public boolean isMagmaBlock(BlockPosition position) {
        Minecraft mc = Minecraft.getInstance();
        return mc.level != null && mc.level.getBlockState(toPos(position)).is(Blocks.MAGMA_BLOCK);
    }

    @Override
    public boolean isAir(BlockPosition position) {
        Minecraft mc = Minecraft.getInstance();
        return mc.level != null && mc.level.getBlockState(toPos(position)).isAir();
    }

    @Override
    public boolean isLecternFacing(BlockPosition position, HorizontalDirection facing) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;
        var state = mc.level.getBlockState(toPos(position));
        return state.is(Blocks.LECTERN) && state.getValue(LecternBlock.FACING) == toDirection(facing);
    }

    @Override
    public boolean canPlaceLectern(BlockPosition position) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;
        BlockPos pos = toPos(position);
        var state = mc.level.getBlockState(pos);
        // 活版门视为可放置位
        boolean placeable = state.isAir() || state.getBlock() instanceof TrapDoorBlock;
        return placeable && mc.level.getBlockState(pos.below()).is(Blocks.MAGMA_BLOCK);
    }

    /** 解析村民实体：实体 ID 快路径 → UUID 兜底扫描 */
    private Villager resolve(Minecraft mc, VillagerTarget target) {
        if (mc.level.getEntity(target.entityId()) instanceof Villager v
                && target.uuid().equals(v.getUUID())) {
            return v;
        }
        if (mc.player == null) return null;
        AABB box = mc.player.getBoundingBox().inflate(UUID_FALLBACK_RADIUS);
        return mc.level.getEntitiesOfClass(Villager.class, box,
                e -> target.uuid().equals(e.getUUID()))
            .stream().findFirst().orElse(null);
    }

    private Direction toDirection(HorizontalDirection direction) {
        return switch (direction) {
            case NORTH -> Direction.NORTH;
            case SOUTH -> Direction.SOUTH;
            case EAST -> Direction.EAST;
            case WEST -> Direction.WEST;
        };
    }

    /** 检查玩家站位是否可通行：脚和头两格都不能是完整实体方块（空气、铁链、活版门等均可通行） */
    private boolean isPlayerStandable(Minecraft mc, BlockPosition pos) {
        if (mc.level == null) return false;
        BlockPos feet = toPos(pos);
        BlockPos head = feet.above();
        return !mc.level.getBlockState(feet).isCollisionShapeFullBlock(mc.level, feet)
            && !mc.level.getBlockState(head).isCollisionShapeFullBlock(mc.level, head);
    }

    private BlockPos toPos(BlockPosition position) {
        return new BlockPos(position.x(), position.y(), position.z());
    }
}
