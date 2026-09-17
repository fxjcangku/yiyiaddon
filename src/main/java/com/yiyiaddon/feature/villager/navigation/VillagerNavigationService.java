package com.yiyiaddon.feature.villager.navigation;

import com.yiyiaddon.platform.navigation.FarmNav;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 村民导航服务
 *
 * 封装平台寻路底座 {@link FarmNav}（Baritone 隔离层），提供：
 * · 工作站寻路（计算正前方站位）
 * · 容器寻路（绿宝石箱/成品交易箱）
 * · 到达判定
 * · 卡死检测
 * · 停止导航
 *
 * <p>底座差异（已登记，不是本类可改项）：旧实现直接把 Goal 交给 Baritone 的
 * {@code setGoalAndPath}，且任何一次寻路异常都把自身永久置为 {@code disabled}；
 * 本项目 {@link FarmNav} 每次调用独立判断，失败只返回 false，由调用方 FSM
 * 既有的失败分支（重发寻路 / NAV_TIMEOUT 回退）兜底。因此本类不再持有
 * disabled 字段，单次失败不会吞掉后续所有寻路。</p>
 *
 * <p>坐标口径：所有目标与站位都是方块坐标（BlockPos，整数格）。
 * 到达判定比较玩家<b>脚下方块</b>与目标方块坐标的距离平方（不是实体中心坐标，
 * 也不是眼高坐标），与旧实现逐字一致。交互半径由调用方 FSM 传入
 * （容器 3.0 格、村民精确站位 0.5 格）。</p>
 */
public final class VillagerNavigationService {

    private final Minecraft mc;
    private BlockPos villagerStandTarget = null;   // 当前村民精确站位（null 表示回退近程寻路）

    // 卡死检测
    private BlockPos lastPos = BlockPos.ZERO;
    private int stuckTicks = 0;
    private int lastCheckTick = 0;
    private static final int STUCK_CHECK_INTERVAL = 3600; // 3分钟
    private static final int STUCK_DISTANCE_THRESHOLD = 5; // 5格

    public VillagerNavigationService() {
        this.mc = Minecraft.getInstance();
    }

    /**
     * 寻路到容器（绿宝石箱/成品交易箱）
     *
     * <p>先算容器外围可站立站位，算不出时退化为直接走向容器本体（旧行为）。
     * 交互本身不在本类做，FSM 用 {@code hasArrived(box, 3.0)} 判定后开箱。</p>
     *
     * @param container 容器坐标
     * @return true 表示成功启动寻路
     */
    public boolean pathToContainer(BlockPos container) {
        // 计算容器前方站位
        BlockPos standingPos = findStandingPosition(container);
        if (standingPos == null) {
            standingPos = container; // 退化方案：直接走向容器
        }
        // 半径 0 等价旧 GoalBlock：GoalNear 的 rangeSq = 0*0 = 0，
        // 判据是「玩家脚下方块 == 站位方块」，且其启发函数与 GoalBlock.calculate 相同，
        // 因此是精确落点而非「附近」。
        return startPath(standingPos, 0);
    }

    /**
     * 寻路到村民正前方（工作方块对面），让玩家、村民、工作方块三者共线，从正面交互。
     *
     * <p>村民被困死后，只有「工作方块对面」一侧是开放交互面，站侧面会隔着墙/角度不正。
     * 正前方不可站立（被墙围死等极端情况）时回退近程寻路（玩家站到村民 2 格内即可），
     * 让底座自行找可达位置，避免精确站位不可达导致反复重新寻路、满屏路径线。</p>
     *
     * @param villagerPos    村民脚下坐标
     * @param workstationPos 村民的工作方块坐标（可能为 null，未解析到时回退近程寻路）
     * @return true 表示成功启动寻路
     */
    public boolean pathToVillager(BlockPos villagerPos, BlockPos workstationPos) {
        if (workstationPos != null) {
            BlockPos front = findFrontStandingPosition(villagerPos, workstationPos);
            if (front != null) {
                villagerStandTarget = front;
                // 半径 0 = 精确站位（等价旧 GoalBlock(front)）
                return startPath(front, 0);
            }
        }
        villagerStandTarget = null;
        // 半径 2：等价旧 GoalNear(villagerPos, 2)，rangeSq = 4，即在村民 2 格内即可。
        return startPath(villagerPos, 2);
    }

    /**
     * 当前村民的精确站位目标；为 null 表示回退了近程寻路（此时按村民距离判定到达）。
     */
    public BlockPos getVillagerStandTarget() {
        return villagerStandTarget;
    }

    /**
     * 计算村民正前方站位：玩家站在工作方块前面（村民 → 工作方块 方向再延伸一格），
     * 使 玩家 - 工作方块 - 村民 三者共线，隔着工作方块从正面交互村民。
     *
     * <p>村民被困死后面向工作方块，只有工作方块前面一侧是开放交互面；
     * 站到村民背对工作方块的一侧（工作方块 → 村民 方向）就会站到侧面/背面，方向是反的。
     * 工作方块在村民正上/正下方时没有水平正前方，返回 null 交由近程寻路回退。</p>
     */
    private BlockPos findFrontStandingPosition(BlockPos villagerPos, BlockPos workstationPos) {
        if (mc.level == null) return null;

        // 工作方块相对村民的水平方向（村民 → 工作方块）
        int dx = workstationPos.getX() - villagerPos.getX();
        int dz = workstationPos.getZ() - villagerPos.getZ();

        // 工作方块在正上/正下方：没有水平正前方
        if (dx == 0 && dz == 0) return null;

        Direction front;
        // 偏移量绝对值大的轴优先（水平距离更远的一侧才是真正的「正前方」），相等时取 X 轴
        if (Math.abs(dx) >= Math.abs(dz)) {
            front = dx > 0 ? Direction.EAST : Direction.WEST;
        } else {
            front = dz > 0 ? Direction.SOUTH : Direction.NORTH;
        }

        // 站到工作方块前面（工作方块相对村民方向再延伸一格），隔工作方块正面交互
        BlockPos standPos = workstationPos.relative(front);
        return isStandable(standPos) ? standPos : null;
    }

    /**
     * 统一下发寻路并重置卡死检测。
     *
     * <p>旧签名是 {@code startPath(Goal)}，Goal 属 Baritone 类型，故按平台原语改为
     * 「坐标 + 停靠半径」两个参数；{@code radius == 0} 等价旧 GoalBlock（精确站位），
     * {@code radius == 2} 等价旧 GoalNear(villagerPos, 2)。</p>
     *
     * <p>失败语义：底座 FarmNav 内部吞异常，Baritone 缺失或算路异常都只返回 false，
     * 本方法原样把 false 交给调用方（不做永久禁用）；且仅在成功下发后才重置卡死检测，
     * 与旧实现「异常时不重置」的顺序一致。</p>
     *
     * @param pos    寻路目标方块坐标
     * @param radius 停靠半径（格），0 表示精确站位
     * @return true 表示成功下发寻路
     */
    private boolean startPath(BlockPos pos, int radius) {
        if (mc.player == null) {
            return false;
        }

        // 不启用 modifyBlocks：村民交互只走位，不允许破坏/放置方块改地形。
        if (!FarmNav.goTo(pos, radius)) {
            return false;
        }

        // 重置卡死检测
        lastPos = mc.player.blockPosition();
        stuckTicks = 0;
        lastCheckTick = 0;

        return true;
    }

    /**
     * 计算目标方块前方的可站立位置
     *
     * 优先级：北 > 南 > 西 > 东
     *
     * @param target 目标方块坐标
     * @return 站位坐标，失败返回 null
     */
    private BlockPos findStandingPosition(BlockPos target) {
        if (mc.level == null) return null;

        // 按优先级尝试四个方向
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

        for (Direction dir : directions) {
            BlockPos candidate = target.relative(dir);

            if (isStandable(candidate)) {
                return candidate;
            }
        }

        // 所有方向都不可站立，返回 null
        return null;
    }

    /**
     * 判断位置是否可站立
     *
     * 条件：
     * 1. 脚下方块可站立（固体方块）
     * 2. 脚部空间可通过
     * 3. 头部空间可通过
     *
     * <p>关键判据：1.21.5+ 不再用 blocksMotion()，统一用
     * {@code isCollisionShapeFullBlock}（完整碰撞形状才算障碍）；因此台阶、栅栏一类
     * 非完整碰撞方块会被判为「可通过」，与旧实现口径一致，不要改成 isAir 或
     * getCollisionShape().isEmpty()。</p>
     */
    private boolean isStandable(BlockPos pos) {
        if (mc.level == null) return false;

        // 脚下必须是完整碰撞方块（1.21.5+ 替代 blocksMotion()）
        BlockPos below = pos.below();
        BlockState belowState = mc.level.getBlockState(below);
        if (!belowState.isCollisionShapeFullBlock(mc.level, below)) {
            return false;
        }

        // 脚部和头部必须可通过
        BlockState feetState = mc.level.getBlockState(pos);
        BlockState headState = mc.level.getBlockState(pos.above());

        return !feetState.isCollisionShapeFullBlock(mc.level, pos)
            && !headState.isCollisionShapeFullBlock(mc.level, pos.above());
    }

    /**
     * 判断是否已到达目标
     *
     * <p>纯坐标判定，不查底座：用玩家脚下方块到目标方块的距离平方与 range² 比较。
     * 与 {@link FarmNav#arrived(BlockPos, double)} 不同（后者用方块中心到实体坐标），
     * 这里保持旧口径，FSM 的 0.5 / 3.0 半径阈值依赖它。</p>
     *
     * @param target 目标坐标
     * @param range 到达判定距离（格）
     * @return true 表示已到达
     */
    public boolean hasArrived(BlockPos target, double range) {
        if (mc.player == null) return false;

        BlockPos playerPos = mc.player.blockPosition();
        return playerPos.distSqr(target) <= range * range;
    }

    /**
     * 判断是否正在寻路
     *
     * <p>底座差异：旧实现只查 {@code pathingBehavior.isPathing()}（算路期间为 false）；
     * {@link FarmNav#pathing()} 额外并入 {@code customGoalProcess.isActive()}，
     * 把「已下发目标但仍在算路」也算作寻路中。这正好配合 FSM 的重发条件
     * （{@code !navigation.isPathing() && stateTicks % 40 == 0}），避免算路被打断重发。</p>
     */
    public boolean isPathing() {
        return FarmNav.pathing();
    }

    /**
     * 停止导航
     *
     * <p>{@link FarmNav#cancel()} 内部依次做 cancelEverything + onLostControl，
     * 与旧实现两步等价，且自身吞异常；旧 catch 分支只做 disabled 标记，
     * 底座无此语义，故此处不再包 try/catch。</p>
     */
    public void stop() {
        FarmNav.cancel();
        resetStuckDetection();
        villagerStandTarget = null;
    }

    /**
     * 检测是否卡死（3分钟内位移 < 5格）
     *
     * <p>关键判据：只有在寻路状态下才累计 tick；每 {@code STUCK_CHECK_INTERVAL}
     * （3600 tick = 3 分钟）结算一次，比较玩家脚下方块与上次结算基准点的欧氏距离，
     * 不足 {@code STUCK_DISTANCE_THRESHOLD}（5 格）判定卡死。结算时无论结论如何都
     * 更新基准点与 lastCheckTick，避免同一段时间被重复判定。</p>
     *
     * <p>返回值语义：只有恰好落在结算 tick 上才可能返回 true，其余 tick 一律 false；
     * 调用方（FSM）需要每 tick 调用它来推进计数。</p>
     */
    public boolean isStuck() {
        if (mc.player == null) {
            return false;
        }

        // 只有在寻路状态下才检测卡死
        if (!FarmNav.pathing()) {
            return false;
        }

        stuckTicks++;

        // 每3分钟检测一次
        if (stuckTicks - lastCheckTick < STUCK_CHECK_INTERVAL) {
            return false;
        }

        BlockPos currentPos = mc.player.blockPosition();
        double distance = Math.sqrt(currentPos.distSqr(lastPos));

        lastCheckTick = stuckTicks;
        lastPos = currentPos;

        // 3分钟内位移小于5格，判定卡死
        return distance < STUCK_DISTANCE_THRESHOLD;
    }

    /**
     * 重置卡死检测
     *
     * <p>基准点取玩家当前脚下方块；下发新目标（{@link #startPath}）与停止导航
     * （{@link #stop()}）时都会调用，保证每段寻路独立计时。</p>
     */
    public void resetStuckDetection() {
        if (mc.player != null) {
            lastPos = mc.player.blockPosition();
        }
        stuckTicks = 0;
        lastCheckTick = 0;
    }

    /**
     * 寻路底座是否可用
     *
     * <p>旧实现是 {@code !disabled && getBaritone() != null}；底座没有 disabled，
     * 直接对应 {@link FarmNav#available()}（内部吞异常，不可用时返回 false）。</p>
     */
    public boolean isAvailable() {
        return FarmNav.available();
    }
}
