package com.yiyiaddon.feature.mining.fsm;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 水中卡死脱困：破坏卡住玩家的方块（用户 2026-09-18 新增需求）。
 *
 * <p>需求原话：<i>「加一个 检测到被水卡住上下跳动 自动破坏周围的方块逃离 继续进入状态机」</i>。</p>
 *
 * <h2>它解决的是哪一段</h2>
 * <p>旧实现（旧项目 {@code :492-551} 逐字移植）在「水中水平停滞 10 秒」后只有一个动作：
 * {@code findNearestLand()} + 寻路到最近陆地。如果玩家是被<b>地形封在水里</b>
 * （头顶是石头、四周是墙，只有一格水），那个「最近陆地」在 Baritone 看来根本走不到——
 * 它只能原地尝试、被判不可达，最终 20 秒超时走 RTP 换区。本类补上中间一级：
 * 先把卡住人的那几块挖开，再交回状态机走原有的「寻路到陆地 / 超时换区」。</p>
 *
 * <h2>走的是哪条破坏通道</h2>
 * <p>直接调用原版 {@code MultiPlayerGameMode#startDestroyBlock / continueDestroyBlock}：
 * 秒破开着时这两个入口被 {@code MultiPlayerGameModeFastBreakMixin} 接管，
 * 于是这些方块享受和挖矿一样的秒破速度与协议（START → 服务端 0.7 阈值 → STOP）；
 * 秒破关着时走原版进度累积，按原速挖。本类因此不需要自己拼任何破坏包。</p>
 *
 * <h2>候选与顺序</h2>
 * <p>候选只收<b>当前交互距离内、可破坏、非空气非流体</b>的方块，按脱困优先级排队：
 * 头顶 → 头侧四向 → 脚侧四向。头顶优先，因为「上下跳动」的典型场景就是水深刚过头顶、
 * 上方一格被方块封住；先把它挖开，玩家自己就浮上去了。</p>
 *
 * <p>单块最长 {@link #BLOCK_TIMEOUT_TICKS}（5 秒）挖不动就换下一块（工具不对 / 受保护方块），
 * 一次脱困最多 {@link #MAX_BLOCKS} 块——这是脱困不是采矿，开出通道就交还状态机。</p>
 */
public final class WaterEscapeBreaker {

    /** 单块最长破坏时长（刻）：5 秒挖不掉就换下一块 */
    private static final int BLOCK_TIMEOUT_TICKS = 100;

    /** 一次脱困最多破坏几块 */
    private static final int MAX_BLOCKS = 6;

    /** 候选上限（头顶 1 + 头侧 4 + 脚侧 4，去重后最多 9） */
    private static final int MAX_CANDIDATES = 9;

    private final AutoMinerModule module;
    private final Minecraft mc = Minecraft.getInstance();

    /** 候选方块（按脱困优先级排列） */
    private final Deque<BlockPos> candidates = new ArrayDeque<>();

    /** 本轮是否在破坏脱困中 */
    private boolean active;
    /** 当前正在破坏的方块；null 表示需要从候选里取下一块 */
    private BlockPos target;
    /** 当前方块已破坏的刻数（超时判定用） */
    private int targetTicks;
    /** 本轮已破坏的方块数 */
    private int brokenCount;

    public WaterEscapeBreaker(AutoMinerModule module) {
        this.module = module;
    }

    /** 是否正在破坏脱困（状态机据此避让其它挖掘逻辑） */
    public boolean isActive() {
        return active;
    }

    /** 当前正在破坏的方块（状态机用它把视角对准作业面）；没有返回 null */
    public BlockPos target() {
        return target;
    }

    /** 本轮已破坏的方块数（播报用） */
    public int brokenCount() {
        return brokenCount;
    }

    /**
     * 开始一轮破坏脱困。
     *
     * @return true 表示已找到可破坏的方块并开始破坏；false 表示周围没有可破坏的方块
     *         （调用方应直接走「寻路到最近陆地」这一级）
     */
    public boolean start() {
        reset();
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) return false;

        BlockPos feet = player.blockPosition();
        List<BlockPos> ordered = new ArrayList<>(MAX_CANDIDATES);
        ordered.add(feet.above(2));                      // 头顶：挖开就能浮上去
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            ordered.add(feet.above(1).relative(dir));    // 头侧：开横向通道
        }
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            ordered.add(feet.relative(dir));             // 脚侧
        }
        for (BlockPos pos : ordered) {
            if (candidates.size() >= MAX_CANDIDATES) break;
            if (!candidates.contains(pos) && isBreakable(player, pos)) candidates.add(pos);
        }
        if (candidates.isEmpty()) return false;

        active = true;
        brokenCount = 0;
        target = null;
        targetTicks = 0;
        return true;
    }

    /**
     * 每刻推进破坏。
     *
     * @return true 表示本轮仍在进行；false 表示本轮已结束（破坏完 / 已脱困 / 没有可挖的方块），
     *         调用方应接着走下一级脱困流程
     */
    public boolean tick() {
        LocalPlayer player = mc.player;
        if (!active) return false;
        if (player == null || mc.level == null || mc.gameMode == null) {
            reset();
            return false;
        }
        // 已经不在水里（挖开头顶后自己浮上来了）或已破坏够数：收摊
        if (!player.isInWater() || brokenCount >= MAX_BLOCKS) {
            finish();
            return false;
        }

        if (target != null) {
            targetTicks++;
            boolean gone = mc.level.getBlockState(target).isAir();
            boolean timeout = targetTicks > BLOCK_TIMEOUT_TICKS;
            if (gone || timeout) {
                if (gone) brokenCount++;
                else module.info("§e⚠ 脱困方块挖不动 §8▸ 换下一块（工具或保护限制）");
                dropTarget();
            }
        }

        if (target == null && !pickNext(player)) {
            finish();
            return false;
        }

        Direction face = breakFace(player, target);
        if (targetTicks == 0) {
            // 换新方块的第一刻：走原版 startDestroyBlock（秒破开着时由 Mixin 接管成秒破通道）
            mc.gameMode.startDestroyBlock(target, face);
        } else {
            mc.gameMode.continueDestroyBlock(target, face);
        }
        return true;
    }

    /** 中止并清空状态（脱困成功、换区、模块关闭时调用） */
    public void reset() {
        if (active && mc.gameMode != null && target != null) mc.gameMode.stopDestroyBlock();
        active = false;
        candidates.clear();
        target = null;
        targetTicks = 0;
        brokenCount = 0;
    }

    // ── 内部 ────────────────────────────────────────────────────────────────

    /** 收摊：结束本轮（不视为失败，交给状态机走下一级） */
    private void finish() {
        if (mc.gameMode != null && target != null) mc.gameMode.stopDestroyBlock();
        active = false;
        candidates.clear();
        target = null;
        targetTicks = 0;
    }

    /** 丢弃当前方块，准备取下一块 */
    private void dropTarget() {
        target = null;
        targetTicks = 0;
    }

    /** 从候选里取下一个还成立的方块；没有则返回 false */
    private boolean pickNext(LocalPlayer player) {
        while (!candidates.isEmpty()) {
            BlockPos next = candidates.poll();
            if (!isBreakable(player, next)) continue;
            target = next;
            targetTicks = 0;
            return true;
        }
        return false;
    }

    /** 该方块此刻是否值得挖：非空气、非流体、挖得动、在交互距离内、且不是容器类 */
    private boolean isBreakable(LocalPlayer player, BlockPos pos) {
        if (mc.level == null) return false;
        BlockState state = mc.level.getBlockState(pos);
        if (state.isAir() || !state.getFluidState().isEmpty()) return false;
        // 硬度 < 0 = 挖不动的方块（基岩 / 屏障 / 受保护方块）
        if (state.getBlock().defaultDestroyTime() < 0.0F) return false;
        // 带方块实体的方块一个都不挖（箱子 / 熔炉 / 刷怪笼 / 告示牌…）：
        // 脱困是随手挖路，挖掉玩家的箱子等于毁掉一箱东西，代价远大于绕开它
        if (state.hasBlockEntity()) return false;
        return player.isWithinBlockInteractionRange(pos, 1.0);
    }

    /** 破坏朝向：从玩家指向方块的方向（原版对破坏包不校验朝向，这里只求语义自然） */
    private static Direction breakFace(LocalPlayer player, BlockPos pos) {
        return Direction.getNearest(pos.subtract(player.blockPosition()), Direction.UP);
    }
}
