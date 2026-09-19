package com.yiyiaddon.feature.vision.scan;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 方块目标扫描器：用「区块轮转 + 段级预筛」找玩家附近的目标方块。
 *
 * <p><b>为什么不用立方体游标</b>（既有 {@code ContainerScanner} / {@code WaterESPModule} 的写法）：
 * 半径 64 的立方体是 129³ ≈ 214 万格，按 512 格/刻要 4000 多刻（三分多钟）才扫完一轮，走动时明显跟不上。
 * 本类改为**按区块轮转**：每 tick 处理 {@link #CHUNKS_PER_TICK} 个区块，一轮覆盖玩家周围全部区块。</p>
 *
 * <p><b>段级预筛（关键性能来源）</b>：原版 {@link LevelChunkSection#maybeHas} 会先查该 16³ 段的调色板，
 * 段里没有目标方块的登记类型就直接否掉，**不必逐格扫描**（原版自己用它做实体 AI 的羊毛预筛）。
 * 只有预筛命中的段才逐格枚举 16³。因此「世界里没有目标方块」时成本极低。</p>
 *
 * <p><b>范围口径（圆柱，不是球）</b>：水平用设置的半径做圆，竖直固定 ±{@link #VERTICAL_REACH} —— 球口径
 * 会把脚下的目标一并筛掉（用户 2026-09-18 原话：「我要看见底下的方块」）。</p>
 *
 * <p><b>结果缓存按区块存</b>：重扫某区块时整体替换该区块的条目，不整表清空 —— 玩家走动 / 重扫时
 * 不会出现「框整片闪一下」。区块卸载时移除对应条目。</p>
 *
 * <p><b>线程</b>：全部在主线程（模块 {@code onTick}）调用，与渲染线程之间靠 {@link #visible()} 的
 * 不可变快照交接，渲染侧不读内部可变状态。</p>
 */
public final class BlockTargetScanner {

    /** 每 tick 处理多少个区块（一轮区块数 / 本值 = 一轮所需刻数） */
    public static final int CHUNKS_PER_TICK = 4;

    /** 竖直方向固定覆盖玩家上下各多少格（不给设置项，避免设置项膨胀） */
    public static final int VERTICAL_REACH = 64;

    /** 同时参与绘制的目标上限：目标方块选到石头这类常见方块时，命中数会到几万，必须截断 */
    public static final int MAX_TARGETS = 2048;

    /** 绘制快照的重建间隔（刻）：避免每 tick 对几万个坐标排序 */
    private static final int VISIBLE_REBUILD_TICKS = 5;

    /** 区块键 → 该区块内的目标方块（重扫整体替换，不整表清空） */
    private final Map<Long, List<BlockPos>> byChunk = new HashMap<>();

    /** 当前目标方块类型集合（由登记 ID 解析而来） */
    private Set<Block> targets = Set.of();
    /** 上一次的名单原始值：用于检测「名单变了」 */
    private List<String> targetIds = List.of();

    /** 本轮待扫区块键与游标 */
    private long[] queue = new long[0];
    private int cursor;

    /** 上一次建立队列时的中心区块与半径 */
    private long lastCenterChunk = Long.MIN_VALUE;
    private int lastRadius = -1;

    /** 绘制快照（不可变）、目标总数、重建计时 */
    private List<BlockPos> visible = List.of();
    private int total;
    private int rebuildTimer;

    /** 快照排序用的玩家水平位置（重建时取当刻值） */
    private double centerX;
    private double centerZ;

    /** 绘制用目标快照；渲染线程只读此值 */
    public List<BlockPos> visible() {
        return visible;
    }

    /** 当前范围内的目标总数（可能大于 {@link #visible()} 的长度，超限时如实报出） */
    public int total() {
        return total;
    }

    /** 本轮是否尚未走完（概览页显示扫描进度用） */
    public boolean scanning() {
        return cursor < queue.length;
    }

    /** 本轮进度 0~1（未建立队列时按已完成处理） */
    public float progress() {
        if (queue.length == 0) return 1f;
        return Math.min(1f, (float) cursor / queue.length);
    }

    /** 清空全部缓存与快照（模块关闭时调用） */
    public void clear() {
        byChunk.clear();
        visible = List.of();
        total = 0;
        queue = new long[0];
        cursor = 0;
        lastCenterChunk = Long.MIN_VALUE;
        lastRadius = -1;
    }

    /**
     * 每刻推进一次。
     *
     * @param level  客户端世界
     * @param center 玩家所在方块坐标（水平范围的圆心）
     * @param radius 水平半径（格）
     * @param ids    目标方块登记 ID 名单（变化时自动重建目标集合与缓存）
     */
    public void tick(ClientLevel level, BlockPos center, int radius, List<String> ids) {
        centerX = center.getX() + 0.5;
        centerZ = center.getZ() + 0.5;

        if (!ids.equals(targetIds)) {
            targetIds = List.copyOf(ids);
            targets = resolve(ids);
            // 名单变了：旧结果全部失效，从头再扫
            clear();
            centerX = center.getX() + 0.5;
            centerZ = center.getZ() + 0.5;
        }
        if (targets.isEmpty()) {
            if (!visible.isEmpty()) visible = List.of();
            if (total != 0) total = 0;
            return;
        }

        refreshQueue(center, radius);

        for (int i = 0; i < CHUNKS_PER_TICK && cursor < queue.length; i++, cursor++) {
            long key = queue[cursor];
            scanChunk(level, unpackX(key), unpackZ(key), center, radius);
        }

        if (++rebuildTimer >= VISIBLE_REBUILD_TICKS || cursor >= queue.length) {
            rebuildTimer = 0;
            rebuildVisible();
        }
    }

    // ── 队列 ──

    /** 中心区块 / 半径变化时重建队列，并按新范围裁剪旧结果（保留仍在范围内的框，不闪） */
    private void refreshQueue(BlockPos center, int radius) {
        int centerChunkX = center.getX() >> 4;
        int centerChunkZ = center.getZ() >> 4;
        long centerKey = key(centerChunkX, centerChunkZ);
        if (centerKey == lastCenterChunk && radius == lastRadius) return;
        lastCenterChunk = centerKey;
        lastRadius = radius;

        int chunkRadius = (radius >> 4) + 1;
        long[] next = new long[(chunkRadius * 2 + 1) * (chunkRadius * 2 + 1)];
        int index = 0;
        for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
            for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
                next[index++] = key(centerChunkX + dx, centerChunkZ + dz);
            }
        }
        queue = next;
        cursor = 0;

        // 移出水平范围 / 竖直范围的旧结果（竖直口径变化也一并裁掉）
        int radiusSqr = radius * radius;
        int centerY = center.getY();
        byChunk.values().forEach(list -> list.removeIf(pos ->
            horizontalDistanceSqr(pos) > radiusSqr || Math.abs(pos.getY() - centerY) > VERTICAL_REACH));
        byChunk.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        rebuildVisible();
    }

    /** 扫一个区块：段级预筛 → 命中段逐格枚举 */
    private void scanChunk(ClientLevel level, int chunkX, int chunkZ, BlockPos center, int radius) {
        long key = key(chunkX, chunkZ);
        LevelChunk chunk = level.getChunkSource().getChunk(chunkX, chunkZ, ChunkStatus.FULL, false);
        if (chunk == null) {
            // 未加载的区块：移除旧结果，避免留下已经不存在的框
            byChunk.remove(key);
            return;
        }

        List<BlockPos> found = new ArrayList<>();
        LevelChunkSection[] sections = chunk.getSections();
        int minSectionY = level.getMinSectionY();
        int centerY = center.getY();
        int radiusSqr = radius * radius;
        int baseX = chunkX << 4;
        int baseZ = chunkZ << 4;

        for (int index = 0; index < sections.length; index++) {
            LevelChunkSection section = sections[index];
            if (section == null || section.hasOnlyAir()) continue;

            int sectionBaseY = (minSectionY + index) << 4;
            // 段中心与玩家的竖直距离超过「覆盖范围 + 半个段高」的段整段跳过
            if (Math.abs(sectionBaseY + LevelChunkSection.SECTION_HEIGHT / 2 - centerY) > VERTICAL_REACH + 8) continue;
            // 段级预筛：该段的调色板里没有目标方块类型 → 直接跳过，不逐格
            if (!section.maybeHas(state -> targets.contains(state.getBlock()))) continue;

            for (int localY = 0; localY < LevelChunkSection.SECTION_HEIGHT; localY++) {
                int worldY = sectionBaseY + localY;
                if (Math.abs(worldY - centerY) > VERTICAL_REACH) continue;
                for (int localZ = 0; localZ < LevelChunkSection.SECTION_WIDTH; localZ++) {
                    for (int localX = 0; localX < LevelChunkSection.SECTION_WIDTH; localX++) {
                        BlockState state = section.getBlockState(localX, localY, localZ);
                        if (!targets.contains(state.getBlock())) continue;
                        double dx = baseX + localX + 0.5 - centerX;
                        double dz = baseZ + localZ + 0.5 - centerZ;
                        if (dx * dx + dz * dz > radiusSqr) continue;
                        found.add(new BlockPos(baseX + localX, worldY, baseZ + localZ));
                    }
                }
            }
        }

        if (found.isEmpty()) {
            byChunk.remove(key);
        } else {
            byChunk.put(key, found);
        }
    }

    // ── 绘制快照 ──

    /** 汇总各区块结果并按水平距离取最近的 {@link #MAX_TARGETS} 个（超限时如实记下总数） */
    private void rebuildVisible() {
        List<BlockPos> all = new ArrayList<>();
        for (List<BlockPos> list : byChunk.values()) all.addAll(list);
        total = all.size();
        if (all.size() > MAX_TARGETS) {
            all.sort(Comparator.comparingDouble(this::horizontalDistanceSqr));
            all = new ArrayList<>(all.subList(0, MAX_TARGETS));
        }
        visible = List.copyOf(all);
    }

    private double horizontalDistanceSqr(BlockPos pos) {
        double dx = pos.getX() + 0.5 - centerX;
        double dz = pos.getZ() + 0.5 - centerZ;
        return dx * dx + dz * dz;
    }

    // ── 名单解析与区块键 ──

    /** 登记 ID → 方块类型（认不出的条目跳过；空气不可能是透视目标，一并剔除） */
    private static Set<Block> resolve(List<String> ids) {
        Set<Block> blocks = new HashSet<>();
        for (String id : ids) {
            Identifier identifier = Identifier.tryParse(id);
            if (identifier == null) continue;
            Block block = BuiltInRegistries.BLOCK.getValue(identifier);
            if (block != null && block != Blocks.AIR) blocks.add(block);
        }
        return blocks;
    }

    /** 区块键：高位放 x、低位放 z（不使用 ChunkPos，避免依赖其静态方法的可见性） */
    private static long key(int chunkX, int chunkZ) {
        return ((long) chunkX << 32) ^ (chunkZ & 0xFFFFFFFFL);
    }

    private static int unpackX(long key) {
        return (int) (key >> 32);
    }

    private static int unpackZ(long key) {
        return (int) key;
    }
}
