package com.yiyiaddon.feature.autochest.scan;

import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.model.autochest.ContainerType;
import com.yiyiaddon.model.autochest.ContainerTypeRegistry;
import com.yiyiaddon.platform.world.WorldIdentity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 容器扫描器：以玩家为中心，按帧分批扫描附近启用的容器类型。
 *
 * <p>逐字复刻旧项目 {@code autochest/scan/ContainerScanner.java}。绝对禁止每 tick 全量扫整个范围——
 * 一个半径 32 的立方体就是 27 万格。这里采用「扫描周期 + 分帧 + 稳定快照」三层缓存：
 * 由模块按扫描周期 {@code requestScan} 启动一轮，每 tick 只扫固定预算，
 * 一整轮结束后替换对外快照并进入空闲，期间状态机读到的一直是上一轮稳定结果。</p>
 *
 * <p>容器合法判定以 {@link ContainerTypeRegistry} 的启用类型为准（按 Block 类型匹配），
 * 而非粗糙的 {@code BlockEntity instanceof Container}，从而排除漏斗、发射器等。</p>
 */
public final class ContainerScanner {

    /** 每 tick 最多检查多少格，超过就留到下一 tick（旧项目 {@code ContainerScanner.java:29}） */
    private static final int BUDGET_PER_TICK = 512;

    /** 中心移动超过该平方距离才重启扫描（避免玩家原地小抖动反复重启，旧项目 {@code ContainerScanner.java:32}） */
    private static final int RECENTER_SQR = 4;

    private final Minecraft mc;
    private final Supplier<List<ContainerType>> enabledTypes;

    private int radius = 16;

    // 扫描范围与游标（x → z → y 推进）
    private int minX, minY, minZ, maxX, maxY, maxZ;
    private int cursorX, cursorY, cursorZ;
    private boolean scanning;

    private BlockPos lastCenter;
    private final List<ChestTarget> buffer = new ArrayList<>();
    private List<ChestTarget> snapshot = List.of();

    public ContainerScanner(Minecraft mc, Supplier<List<ContainerType>> enabledTypes) {
        this.mc = mc;
        this.enabledTypes = enabledTypes;
    }

    /** 设置扫描半径（格），设置后立即重启扫描（半径下限 clamp 至 1，旧项目 {@code ContainerScanner.java:54-57}） */
    public void setRadius(int radius) {
        this.radius = Math.max(1, radius);
        restart(BlockPos.ZERO);
    }

    /**
     * 请求开始新一轮扫描：空闲时启动；中心位移过大时重启，避免旧范围与玩家新位置脱节。
     *
     * <p>由模块按「扫描周期」调用，不在每 tick 反复触发。旧项目 {@code ContainerScanner.java:64-69}。</p>
     */
    public void requestScan(BlockPos center) {
        if (mc.level == null) return;
        if (!scanning || lastCenter == null || center.distSqr(lastCenter) > RECENTER_SQR) {
            restart(center);
        }
    }

    /**
     * 每 tick 调用，推进分帧扫描。
     *
     * @return 本轮扫描是否刚好完成（供调用方做记录失效对账等一次性动作）
     */
    public boolean tick(BlockPos center) {
        if (mc.level == null) {
            scanning = false;
            return false;
        }
        if (!scanning) return false;

        int budget = BUDGET_PER_TICK;
        while (budget-- > 0) {
            if (cursorY > maxY) {
                // 一整轮扫描完成：替换稳定快照后进入空闲，等待下次 requestScan
                snapshot = new ArrayList<>(buffer);
                buffer.clear();
                scanning = false;
                return true;
            }

            BlockPos pos = new BlockPos(cursorX, cursorY, cursorZ);
            String typeId = containerTypeAt(pos);
            if (typeId != null) {
                buffer.add(new ChestTarget(pos, WorldIdentity.dimension(),
                    WorldIdentity.server(), typeId));
            }

            // 推进游标：x → z → y
            cursorX++;
            if (cursorX > maxX) {
                cursorX = minX;
                cursorZ++;
            }
            if (cursorZ > maxZ) {
                cursorZ = minZ;
                cursorY++;
            }
        }
        return false;
    }

    /** 对外暴露的稳定快照（上一轮完整扫描结果） */
    public List<ChestTarget> results() {
        return snapshot;
    }

    /**
     * 当前稳定快照中位于当前维度的容器坐标集合。
     *
     * <p>等价旧项目 {@code AutoChestModule.reconcileDestroyed} 中由 {@code results()} 过滤出的
     * {@code seen} 集合（{@code AutoChestModule.java:272-288}）：只保留当前维度，供记录失效对账使用。</p>
     */
    public Set<BlockPos> seen() {
        Set<BlockPos> seen = new HashSet<>();
        for (ChestTarget target : snapshot) {
            if (target.inCurrentDimension()) seen.add(target.pos());
        }
        return seen;
    }

    /** 是否正在扫描中 */
    public boolean isScanning() {
        return scanning;
    }

    /** 重置扫描器 */
    public void reset() {
        snapshot = List.of();
        buffer.clear();
        scanning = false;
        lastCenter = null;
    }

    private void restart(BlockPos center) {
        minX = center.getX() - radius;
        minY = center.getY() - radius;
        minZ = center.getZ() - radius;
        maxX = center.getX() + radius;
        maxY = center.getY() + radius;
        maxZ = center.getZ() + radius;
        cursorX = minX;
        cursorY = minY;
        cursorZ = minZ;
        scanning = true;
        lastCenter = center;
    }

    /**
     * 判定方块是否命中启用的容器类型，命中返回其稳定键，否则返回 null。
     *
     * <p>按 Block 类型匹配（容器选择器语义），普通箱子/陷阱箱/铜箱虽同是
     * ChestBlockEntity，但可按 Block 区分。旧项目 {@code ContainerScanner.java:152-157}。</p>
     */
    private String containerTypeAt(BlockPos pos) {
        if (mc.level == null) return null;
        Block block = mc.level.getBlockState(pos).getBlock();
        ContainerType type = ContainerTypeRegistry.match(block, enabledTypes.get());
        return type == null ? null : type.id();
    }
}
