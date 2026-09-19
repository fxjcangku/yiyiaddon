package com.yiyiaddon.feature.teleport.core;

import com.yiyiaddon.feature.teleport.geo.SurfaceScanner;
import com.yiyiaddon.feature.teleport.geo.WallRayScanner;
import com.yiyiaddon.feature.teleport.model.TeleportContext;
import com.yiyiaddon.feature.teleport.model.TeleportMode;
import com.yiyiaddon.feature.teleport.model.TeleportRequest;
import com.yiyiaddon.feature.teleport.model.TeleportState;
import com.yiyiaddon.feature.teleport.model.TeleportSubject;
import com.yiyiaddon.feature.teleport.model.TeleportTarget;
import com.yiyiaddon.feature.teleport.move.PositionExecutor;
import com.yiyiaddon.feature.teleport.safety.SafePositionFinder;
import com.yiyiaddon.feature.teleport.verify.RubberbandVerifier;
import java.util.Locale;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

/**
 * 传送协调器（决策层）：Observe → Decide → Execute → Verify 状态机，
 * 三个模式共享同一生命周期。
 *
 * <p>观察与决策在触发瞬间同步完成；执行后进入验证窗口，由服务端
 * 位置权威包（回弹=拒绝）与窗口超时（无包=接受）共同裁决结果。
 * 决策层只消费 TeleportRequest 参数包，不依赖任何模块/设置对象；
 * 播报与高亮色统一经 Sink 回调回到模块基类方法，保证前缀颜色规范。</p>
 */
public final class TeleportCoordinator {

    /** 播报与高亮回调：由模块实现，高亮统一走基类 highlight* 方法 */
    public interface Sink {
        /** 播报整条消息（模块负责加前缀） */
        void broadcast(String message);

        /** 文本高亮（§a 亮绿） */
        String text(String s);

        /** 功能/模式高亮（§b 亮青） */
        String func(String s);

        /** 数值高亮（§e 黄） */
        String num(String s);

        /** 坐标高亮（§d 粉） */
        String loc(String s);
    }

    /** 进行中传送（非空即忙） */
    private TeleportContext active;

    /** 最近一次结束的传送（渲染与报告复用） */
    private TeleportContext last;

    /** 当前状态机状态 */
    private TeleportState state = TeleportState.IDLE;

    /** 忙时重复触发提示节流（tick 计数） */
    private long lastBusyNotice = -1;

    private final Sink sink;

    public TeleportCoordinator(Sink sink) {
        this.sink = sink;
    }

    /** 是否正在执行传送 */
    public boolean busy() {
        return active != null;
    }

    /** 最近一次结束的传送上下文（调试渲染用，可空） */
    public TeleportContext last() {
        return last;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  触发入口
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 按键/指令统一入口：锁定快照 → 决策 → 执行 → 进入验证窗口。
     */
    public void request(LocalPlayer player, ClientLevel level, TeleportRequest req) {
        if (player == null || level == null || req == null) return;

        if (active != null) {
            long now = level.getGameTime();
            if (now - lastBusyNotice > 20) {
                lastBusyNotice = now;
                broadcast("执行中 ▸ 上一次传送尚未结束，请稍候");
            }
            return;
        }
        if (!player.isAlive()) {
            broadcast("传送失败 ▸ 死亡状态下不可传送");
            return;
        }

        state = TeleportState.OBSERVE;
        TeleportContext ctx = new TeleportContext();
        ctx.mode = req.mode;
        ctx.origin = player.position();
        ctx.dimension = level.dimension();
        ctx.rayOrigin = req.rayOrigin;
        ctx.rayDir = req.rayDir;
        ctx.rayLength = req.maxDistance;
        ctx.verifyThreshold = req.verifyThreshold;
        ctx.verifyWindow = req.verifyWindow;
        ctx.noFallDamage = req.noFallDamage;
        ctx.debug("状态 ▸ " + TeleportState.OBSERVE.cn() + " ▸ 锁定维度/起点快照");
        active = ctx;

        state = TeleportState.DECIDE;
        ctx.debug("状态 ▸ " + TeleportState.DECIDE.cn() + " ▸ 模式 " + req.mode);
        String fail = decide(player, level, ctx, req);
        if (fail != null) {
            fail(fail);
            return;
        }
        if (ctx.skipExecute) {
            completeNoMove();
            return;
        }

        // 执行前二次校验：目标计算完成后、真正发包位移前，
        // 重新确认维度/玩家/目标碰撞与危险仍然有效，使用过期坐标直接取消
        String stale = recheck(player, level, ctx);
        if (stale != null) {
            fail(stale);
            return;
        }

        state = TeleportState.EXECUTE;
        ctx.debug("状态 ▸ " + TeleportState.EXECUTE.cn());
        broadcast("§7正在传送 ▸ " + sink.func(req.mode.toString()) + " ▸ 目标 " + sink.loc(ctx.target.posText()));
        fail = PositionExecutor.execute(player, level, ctx);
        if (fail != null) {
            fail(fail);
            return;
        }

        state = TeleportState.VERIFY;
        ctx.verifyEndTick = level.getGameTime() + ctx.verifyWindow;
        ctx.debug("状态 ▸ " + TeleportState.VERIFY.cn() + " ▸ 窗口 " + ctx.verifyWindow + " tick");
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  每 tick 推进：环境校验 + 验证超时
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public void tick(LocalPlayer player, ClientLevel level) {
        if (active == null) return;
        if (state != TeleportState.EXECUTE && state != TeleportState.VERIFY) return;

        // 断点校验：任何环境变化立即中止，防止状态残留
        if (!active.dimension.equals(level.dimension())) {
            fail("传送期间世界变化，已中止");
            return;
        }
        if (!player.isAlive()) {
            fail("传送期间玩家死亡，已中止");
            return;
        }
        if (active.vehicleSnapshot != null && player.getRootVehicle() != active.vehicleSnapshot) {
            fail("传送期间载具关系变化，已中止");
            return;
        }

        // 验证窗口超时且无权威包 = 服务端接受了本次位置
        if (state == TeleportState.VERIFY && level.getGameTime() >= active.verifyEndTick) {
            success();
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  服务端权威位置观察（验证窗口内生效）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 服务端权威位置到达：本体包与载具包都在此汇合。
     *
     * <p>绝对坐标换算（含相对修正）已由平台层 {@code SERVER_POSITION} 派发完成一次，
     * 本层只做来源匹配与距离裁决（第 169 条：同源逻辑只留一份）。</p>
     *
     * @param serverPos     服务端权威位置（绝对坐标）
     * @param vehiclePacket 该位置来自载具包
     * @param player        本地玩家
     */
    public void onServerPosition(Vec3 serverPos, boolean vehiclePacket, LocalPlayer player) {
        if (active == null || state != TeleportState.VERIFY || serverPos == null || player == null) return;

        int verdict = RubberbandVerifier.classify(serverPos, vehiclePacket, active);
        if (verdict == RubberbandVerifier.CONFIRM) {
            success();
        } else if (verdict == RubberbandVerifier.RUBBERBAND) {
            rubberband();
        }
    }

    /** 模块关闭等外部中止 */
    public void cancel(String reason) {
        if (active != null) {
            fail(reason);
        }
    }

    /** 模块关闭：清空进行中与最近一次渲染上下文，彻底清理 ESP 临时渲染状态 */
    public void clear() {
        active = null;
        last = null;
        state = TeleportState.IDLE;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  决策：三模式目标计算（同步完成）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** @return 失败原因（中文），成功返回 null */
    private String decide(LocalPlayer player, ClientLevel level, TeleportContext ctx, TeleportRequest req) {
        // 本轮真正移动的对象：玩家单独 或 根载具 + 全部乘客（真实碰撞箱）
        TeleportSubject subject = TeleportSubject.of(player);

        switch (req.mode) {
            case GROUND -> {
                SurfaceScanner.Result r = SurfaceScanner.scan(level, subject, player.position(), req.maxRise);
                ctx.debug("地面扫描 ▸ 位移 " + r.rise + (r.target == null ? " ▸ " + r.failReason : ""));
                if (r.target == null) return r.failReason;
                ctx.target = r.target;
                ctx.stageRise = r.rise;
                if (r.rise == 0) ctx.skipExecute = true;
            }
            case WALL -> {
                if (req.rayOrigin == null || req.rayDir == null) return "视线快照缺失，请重试";

                // 主路：沿锁定三维准星方向采样搜索（穿墙 + 方向赶路一体，
                // 不强制前方必须有墙，普通方块/门窗/半砖/栅栏均不是阻挡）
                WallRayScanner.Landing landing = WallRayScanner.findLanding(
                    level, subject, req.rayOrigin, req.rayDir, req.eyeHeight, req.maxDistance, req.maxFall, ctx.debugCells);
                ctx.wallLayers = landing.layers;
                ctx.debug("墙体扫描 ▸ 穿透层数 " + landing.layers
                    + (landing.target != null ? " ▸ 越过障碍后最近落点命中" : " ▸ 主路无候选"));

                if (landing.target != null) {
                    ctx.target = landing.target;
                } else {
                    // 主路无候选：先区分「障碍太厚/数据未加载」与「需要局部修正」
                    if (landing.reachedUnloaded) return "前方世界数据未加载，扩大搜索前请先走近";
                    if (landing.exhaustedInsideSolid) return "障碍太厚，超出最大穿墙距离";

                    // 局部修正：以理想落点为中心在「落点修正范围」内搜索，
                    // 受准星偏离上限约束 + 必须沿射线前进，绝不把玩家传回近侧
                    SafePositionFinder.SearchParams p = new SafePositionFinder.SearchParams();
                    p.center = landing.idealFeet;
                    p.radius = (int) Math.ceil(req.maxDeviation);
                    p.rayOrigin = req.rayOrigin;
                    p.rayDir = req.rayDir;
                    p.maxDeviation = req.maxDeviation;
                    p.minAhead = WallRayScanner.START;
                    p.debugCells = ctx.debugCells;
                    TeleportTarget wt = SafePositionFinder.find(level, subject, p);
                    if (wt == null) return "准星方向的落点修正范围内没有可站立位置";
                    ctx.debug("墙体扫描 ▸ 理想落点不可容纳 ▸ 局部修正命中");
                    ctx.target = wt;
                }
            }
            case COORD -> {
                Vec3 center = req.coord;
                if (center == null) return "未指定传送坐标";

                String problem = subject.checkStand(level, center.x(), center.y(), center.z());
                if (problem != null) {
                    ctx.debug("坐标不安全 ▸ " + problem + " ▸ 启动邻近搜索");
                    ctx.fallbackSearched = true;
                    SafePositionFinder.SearchParams p = new SafePositionFinder.SearchParams();
                    p.center = center;
                    p.radius = req.fallbackRadius;
                    p.debugCells = ctx.debugCells;
                    TeleportTarget alt = SafePositionFinder.find(level, subject, p);
                    if (alt == null) return "目标坐标不安全，邻近搜索无可用落点";
                    ctx.target = alt;
                } else {
                    ctx.target = new TeleportTarget(center, containing(center), 0.0);
                }
            }
            default -> {
                return "未知传送模式";
            }
        }

        // 同步给 ESP 渲染：目标位置的实际移动对象碰撞箱（单一事实来源）
        ctx.subjectBoxes = subject.boxesAt(ctx.target.feet().x(), ctx.target.feet().y(), ctx.target.feet().z());
        return null;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  执行前二次校验
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 目标计算完成后、发包位移前的最终复核：维度/玩家/目标碰撞/危险
     * 任何一项失效立即取消本次执行，不使用过期目标坐标。
     *
     * @return 失败原因（中文），全部通过返回 null
     */
    private String recheck(LocalPlayer player, ClientLevel level, TeleportContext ctx) {
        if (!ctx.dimension.equals(level.dimension())) return "传送前世界已变化，已取消执行";
        if (!player.isAlive()) return "传送前玩家状态异常，已取消执行";
        if (ctx.target == null) return "目标缺失，已取消执行";

        // 最终复核：重新读取移动对象当前实际碰撞箱（Pose/载具/乘客可能已变化），
        // 不用搜索阶段缓存的旧碰撞箱，任何失效立即取消执行
        TeleportSubject subject = TeleportSubject.of(player);
        String problem = subject.checkStand(level,
            ctx.target.feet().x(), ctx.target.feet().y(), ctx.target.feet().z());
        if (problem != null) return "目标区域已变化（" + problem + "），已取消执行";

        // 刷新给 ESP 的移动对象碰撞箱（执行前最终状态）
        ctx.subjectBoxes = subject.boxesAt(ctx.target.feet().x(), ctx.target.feet().y(), ctx.target.feet().z());
        return null;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  结果处理
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 服务端接受（验证窗口内无回弹即视为接受） */
    private void success() {
        TeleportContext c = active;
        state = TeleportState.SUCCESS;
        c.debug("状态 ▸ " + TeleportState.SUCCESS.cn());

        StringBuilder sb = new StringBuilder("§a✓ 传送完成 ▸ ");
        sb.append(sink.loc(c.target.posText()));
        sb.append(" ▸ 距离 ").append(sink.num(String.format(Locale.ROOT, "%.1f", c.origin.distanceTo(c.target.feet())))).append(" 格");
        if (c.mode == TeleportMode.GROUND) {
            sb.append(" ▸ ").append(c.stageRise >= 0 ? "上升 " : "下降 ").append(sink.num(Math.abs(c.stageRise) + " 格"));
        }
        if (c.mode == TeleportMode.WALL) {
            if (c.wallLayers > 0) {
                sb.append(" ▸ 穿透 ").append(sink.num(c.wallLayers + " 层"));
            }
            if (c.target.deviation() > 0.5) {
                sb.append(" ▸ 局部修正 ").append(sink.num(String.format(Locale.ROOT, "%.1f", c.target.deviation()))).append(" 格");
            }
        }
        if (c.mode == TeleportMode.COORD && c.fallbackSearched) {
            sb.append(" ▸ 已修正至邻近安全点");
        }
        broadcast(sb.toString());
        finish();
    }

    /** 已在地表无需移动 */
    private void completeNoMove() {
        state = TeleportState.SUCCESS;
        broadcast("§a✓ 已在真正地表 ▸ 无需传送");
        finish();
    }

    /** 服务器回弹：位置已被权威包修正，判定失败（不自动重试、不重复发包） */
    private void rubberband() {
        TeleportContext c = active;
        state = TeleportState.FAILED;
        c.debug("状态 ▸ " + TeleportState.FAILED.cn() + " ▸ 服务器回弹");
        broadcast("§c✗ 传送失败 ▸ 服务器位置被修正 ▸ 偏差 "
            + sink.num(String.format(Locale.ROOT, "%.1f", c.rubberbandDist)) + " 格 ▸ 已跟随服务器");
        finish();
    }

    /** 失败（决策/执行/环境中止） */
    private void fail(String reason) {
        state = TeleportState.FAILED;
        if (active != null) {
            active.debug("状态 ▸ " + TeleportState.FAILED.cn() + " ▸ " + reason);
        }
        broadcast("§c✗ 传送失败 ▸ " + sink.text(reason));
        finish();
    }

    /** 结束本次传送：移交最近上下文供渲染，清空活动位 */
    private void finish() {
        last = active;
        active = null;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  输出与微工具
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void broadcast(String message) {
        sink.broadcast(message);
    }

    /** 坐标所在格 */
    private static BlockPos containing(Vec3 v) {
        return new BlockPos((int) Math.floor(v.x()), (int) Math.floor(v.y()), (int) Math.floor(v.z()));
    }
}
