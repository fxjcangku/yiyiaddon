package com.yiyiaddon.feature.autofarm.scan;

import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.feature.autofarm.model.FarmTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 农田分帧扫描器。
 *
 * 保留旧实现的分帧思想：一个 200x200 的农场就是 4 万格，每 tick 全量扫一遍必然掉帧，
 * 因此每 tick 只扫固定格数（BUDGET_PER_TICK），游标循环推进，持续刷新缓存。
 *
 * 这里只维护「当前已知的成熟/可补种坐标」两个持续刷新的缓存，不生成任何批次快照。
 * 作业方每次只取一个最近目标，实现「熟一颗收一颗」，扫描没有「一轮」概念。
 */
public final class FarmScanner {

    /** 每 tick 最多检查多少格，超过就留到下一 tick */
    private static final int BUDGET_PER_TICK = 512;

    private BlockPos min = BlockPos.ZERO;
    private BlockPos max = BlockPos.ZERO;
    private boolean bounded;

    /** 扫描游标，按 x → z → y 顺序循环推进 */
    private int cursorX;
    private int cursorY;
    private int cursorZ;

    /** 当前已知的成熟作物坐标缓存，持续刷新 */
    private final Set<BlockPos> matureBlocks = new HashSet<>();
    /** 当前已知的可补种底盘坐标缓存，持续刷新 */
    private final Set<BlockPos> plantableBlocks = new HashSet<>();
    /** 当前已知的待锄地草方块/泥土坐标缓存，持续刷新 */
    private final Set<BlockPos> tillableBlocks = new HashSet<>();

    private final Set<CropProfile> enabled = EnumSet.noneOf(CropProfile.class);

    /** 设定扫描范围。两个锚点是对角，内部归一化为 min/max。 */
    public void setBounds(BlockPos a, BlockPos b) {
        min = new BlockPos(
            Math.min(a.getX(), b.getX()),
            Math.min(a.getY(), b.getY()),
            Math.min(a.getZ(), b.getZ()));
        max = new BlockPos(
            Math.max(a.getX(), b.getX()),
            Math.max(a.getY(), b.getY()),
            Math.max(a.getZ(), b.getZ()));
        // 补种检测需要识别作物下方的耕地层（作物在耕地正上方），
        // 而锚点通常对准地面本身；柱状物（仙人掌/甘蔗/竹子）的收割段在底盘上方第二格，
        // 因此向上多扩 3 格确保覆盖生长高度，避免「Y 轴没往上走」导致柱状物收不到。
        min = new BlockPos(min.getX(), min.getY() - 1, min.getZ());
        max = new BlockPos(max.getX(), max.getY() + 3, max.getZ());
        bounded = true;
        restart();
    }

    /** 更新启用的作物集合，会立刻重启扫描避免用旧图鉴的残留结果 */
    public void setEnabledCrops(Set<CropProfile> crops) {
        if (enabled.equals(crops)) return;
        enabled.clear();
        enabled.addAll(crops);
        restart();
    }

    /** 当前启用的作物集合（只读视图，供决策层按补种模式选作物） */
    public Set<CropProfile> enabledCrops() {
        return java.util.Collections.unmodifiableSet(enabled);
    }

    public boolean bounded() {
        return bounded;
    }

    public BlockPos min() {
        return min;
    }

    public BlockPos max() {
        return max;
    }

    /** 范围内的总格数，自检时用于拦下「起点终点设成同一格」这类误操作 */
    public long volume() {
        if (!bounded) return 0;
        long dx = (long) max.getX() - min.getX() + 1;
        long dy = (long) max.getY() - min.getY() + 1;
        long dz = (long) max.getZ() - min.getZ() + 1;
        return dx * dy * dz;
    }

    /** 取离玩家最近的至多 maxCount 个成熟目标，按距离升序；实际数量不足则返回实际数量 */
    public List<FarmTarget> nearestHarvests(int maxCount) {
        List<FarmTarget> result = new ArrayList<>();
        if (maxCount <= 0 || matureBlocks.isEmpty()) return result;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return result;

        List<BlockPos> sorted = new ArrayList<>(matureBlocks);
        sorted.sort(Comparator.comparingDouble(this::distanceSqToPlayer));

        for (BlockPos pos : sorted) {
            if (result.size() >= maxCount) break;
            CropProfile profile = CropProfile.byBlock(mc.level.getBlockState(pos).getBlock());
            if (profile == null) continue;
            result.add(FarmTarget.harvest(profile, pos));
        }
        return result;
    }

    /** 取离玩家最近的至多 maxCount 个可补种底盘坐标（不含作物，由决策层按补种模式选作物），按距离升序 */
    public List<BlockPos> nearestPlantablePositions(int maxCount) {
        List<BlockPos> result = new ArrayList<>();
        if (maxCount <= 0 || plantableBlocks.isEmpty()) return result;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return result;

        List<BlockPos> sorted = new ArrayList<>(plantableBlocks);
        sorted.sort(Comparator.comparingDouble(this::distanceSqToPlayer));

        for (BlockPos pos : sorted) {
            if (result.size() >= maxCount) break;
            result.add(pos);
        }
        return result;
    }

    /** 取离玩家最近的至多 maxCount 个待锄地坐标（草方块/泥土），按距离升序 */
    public List<BlockPos> nearestTillablePositions(int maxCount) {
        List<BlockPos> result = new ArrayList<>();
        if (maxCount <= 0 || tillableBlocks.isEmpty()) return result;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return result;

        List<BlockPos> sorted = new ArrayList<>(tillableBlocks);
        sorted.sort(Comparator.comparingDouble(this::distanceSqToPlayer));

        for (BlockPos pos : sorted) {
            if (result.size() >= maxCount) break;
            result.add(pos);
        }
        return result;
    }

    /** 当前是否有待锄地目标（O(1) 判定，供决策层快速判断锄地工作是否存在） */
    public boolean hasTillable() {
        return !tillableBlocks.isEmpty();
    }

    /** 坐标是否落在扫描范围内 */
    public boolean contains(BlockPos pos) {
        if (!bounded) return false;
        return pos.getX() >= min.getX() && pos.getX() <= max.getX()
            && pos.getY() >= min.getY() && pos.getY() <= max.getY()
            && pos.getZ() >= min.getZ() && pos.getZ() <= max.getZ();
    }

    /** 主动移除某个坐标（目标被处理或失效后调用，避免等下一轮扫描才刷新） */
    public void invalidate(BlockPos pos) {
        matureBlocks.remove(pos);
        plantableBlocks.remove(pos);
        tillableBlocks.remove(pos);
    }

    public void reset() {
        bounded = false;
        matureBlocks.clear();
        plantableBlocks.clear();
        tillableBlocks.clear();
        restart();
    }

    /** 把扫描游标归位到范围起点 */
    private void restart() {
        cursorX = min.getX();
        cursorY = min.getY();
        cursorZ = min.getZ();
    }

    /** 推进一帧扫描。每 tick 调用一次。 */
    public void tick() {
        if (!bounded || enabled.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null) return;

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int i = 0; i < BUDGET_PER_TICK; i++) {
            cursor.set(cursorX, cursorY, cursorZ);
            classify(level, cursor);
            advance();
        }
    }

    /**
     * 一次性全量扫描整个范围（不设预算，超大农田上可能造成单帧卡顿）。
     * 用于模块刚开启时立即拿到全部成熟/可补种/待锄地目标，让补种「一瞬间补满」；
     * 之后仍由分帧扫描持续刷新缓存。
     */
    public void fullScan() {
        if (!bounded || enabled.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null) return;

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int z = min.getZ(); z <= max.getZ(); z++) {
                for (int y = min.getY(); y <= max.getY(); y++) {
                    cursor.set(x, y, z);
                    classify(level, cursor);
                }
            }
        }
        restart();
    }

    /** 游标前进一格，越过边界就回卷到起点，循环推进 */
    private void advance() {
        cursorX++;
        if (cursorX <= max.getX()) return;

        cursorX = min.getX();
        cursorZ++;
        if (cursorZ <= max.getZ()) return;

        cursorZ = min.getZ();
        cursorY++;
        if (cursorY <= max.getY()) return;

        cursorX = min.getX();
        cursorY = min.getY();
        cursorZ = min.getZ();
    }

    /** 判定单格属于成熟目标、可补种空地还是无关方块，并持续刷新缓存 */
    private void classify(ClientLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        if (state.isAir()) {
            matureBlocks.remove(pos);
            plantableBlocks.remove(pos);
            tillableBlocks.remove(pos);
            return;
        }

        CropProfile profile = CropProfile.byBlock(state.getBlock());
        if (profile != null && enabled.contains(profile) && profile.isHarvestable(state, level, pos)) {
            matureBlocks.add(pos.immutable());
        } else {
            matureBlocks.remove(pos);
        }

        // 该格是底盘时，检查上方能否补种
        boolean plantable = false;
        for (CropProfile candidate : enabled) {
            if (candidate.isPlantable(level, pos)) {
                plantable = true;
                break;
            }
        }
        if (plantable) {
            plantableBlocks.add(pos.immutable());
        } else {
            plantableBlocks.remove(pos);
        }

        // 该格是草方块/泥土且上方为空气时，视为待锄地目标
        if (isTillable(level, pos)) {
            tillableBlocks.add(pos.immutable());
        } else {
            tillableBlocks.remove(pos);
        }
    }

    /** 判定某坐标是否为可用锄头开垦成耕地的草方块/泥土（要求上方为空气，否则锄头无法生效） */
    public static boolean isTillable(BlockGetter level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(Blocks.GRASS_BLOCK) && !state.is(Blocks.DIRT)) return false;
        return level.getBlockState(pos.above()).isAir();
    }

    /** 计算某坐标到玩家眼睛的平方距离，用于按距离排序选目标 */
    private double distanceSqToPlayer(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        double dx = pos.getX() + 0.5 - mc.player.getX();
        double dy = pos.getY() + 0.5 - mc.player.getEyeY();
        double dz = pos.getZ() + 0.5 - mc.player.getZ();
        return dx * dx + dy * dy + dz * dz;
    }
}
