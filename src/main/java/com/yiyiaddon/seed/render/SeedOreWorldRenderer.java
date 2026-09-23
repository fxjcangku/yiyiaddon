package com.yiyiaddon.seed.render;

import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.ore.SeedOrePalette;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.AABB;

/**
 * 种子挖矿正式模块 · <b>预测钻石世界渲染器</b>（正式化第五阶段 233）。
 *
 * <p><b>它画什么</b>（口径第三十四、三十五节）：只画预测钻石的方框，支持透墙、camera-relative 坐标、
 * 负 X/Z、负 Y（主世界 -64 ~ 319）、不同 FOV 与窗口缩放。第一版刻意<b>不做</b>复杂 3D 模型 / 粒子 /
 * 动画 / tracer / 满屏文字 —— 先把稳定性做扎实。</p>
 *
 * <p><b>三色语义</b>（口径第三十五、三十六节）：</p>
 * <ul>
 *     <li>{@link OreObservationState#UNOBSERVED} —— 青色框：<b>预测钻石</b>（客户端还没合法看到该区块）；</li>
 *     <li>{@link OreObservationState#CONFIRMED} —— 绿色框：<b>已确认钻石</b>（客户端实际看到就是钻石矿）；</li>
 *     <li>{@link OreObservationState#MISSING} —— 灰色细框：<b>当前缺失</b>（预测位置现在不是钻石矿），
 *         默认不画（口径第三十七节「避免世界里大量无效框」），打开后也只淡化显示；
 *         它<b>不是</b>假矿、不是服务器作弊、不是种子错误（口径第十一、四十八节）。</li>
 *     <li>{@link com.yiyiaddon.seed.prediction.PredictionCertainty#SCHEDULE_SENSITIVE} —— 内缩一圈的琥珀细框：
 *         「调度敏感」小标记。它与上面三种观察状态是<b>两个维度</b>，因此用「叠加标记」而不是换颜色
 *         （口径第八、三十六节：绝不把二者混成一个枚举、也绝不把调度敏感画成假矿）。</li>
 * </ul>
 *
 * <p><b>每帧只做两件事</b>（口径第三十一、三十二、五十一节）：读不可变 {@link SeedRenderSnapshot} +
 * 画方框。本类<b>不</b>访问预测缓存、<b>不</b>访问客户端世界、<b>不</b>执行观察、<b>不</b>触发 Worker 预测；
 * 快照重建在服务层（客户端主线程）完成，本类只在收到脏标记后读取新快照。</p>
 *
 * <p><b>线程模型</b>：{@link #render(EspRenderer)} 由原版渲染帧驱动（与客户端主线程同一条线程）；
 * {@link #snapshot()} 可从界面线程读（volatile 不可变对象）。</p>
 */
public final class SeedOreWorldRenderer {

    /** 世界渲染层所有者标识（重复注册会覆盖，注销即整层消失）。 */
    public static final String LAYER_ID = "seed.prediction";

    /** 外框线宽（GUI 缩放坐标；比自动挖矿点位框 3.0 细，避免抢视觉）。 */
    private static final float THICKNESS = 2.0f;

    /** 调度敏感标记的线宽与内缩比例。 */
    private static final float MARKER_THICKNESS = 1.2f;
    private static final double MARKER_INSET = 0.28d;

    private final BooleanSupplier visible;
    private final BooleanSupplier showMissing;

    /** 当前渲染快照（不可变，只整体替换）。 */
    private volatile SeedRenderSnapshot snapshot = SeedRenderSnapshot.EMPTY;

    /** 快照是否需要重建（仅客户端主线程读写）。 */
    private boolean dirty;

    /** 本层是否已注册进世界渲染层（仅客户端主线程读写）。 */
    private boolean attached;

    /**
     * @param visible     现在该不该画（服务层给出：种子挖矿已启用 + 显示预测钻石已打开 + 主世界）
     * @param showMissing 是否把「当前缺失」也画出来（口径第三十七节，默认关）
     */
    public SeedOreWorldRenderer(BooleanSupplier visible, BooleanSupplier showMissing) {
        this.visible = visible;
        this.showMissing = showMissing;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 生命周期（服务层调用）
    // ────────────────────────────────────────────────────────────────────────

    /** 注册渲染层（幂等）。 */
    public void attach() {
        if (attached) {
            return;
        }
        attached = true;
        WorldOverlay.register(LAYER_ID, this::render);
    }

    /**
     * 注销渲染层并清空快照（幂等）。
     *
     * <p>口径第四十节：关闭功能 / 退世界 / 换服 / 换维度后，<b>世界里一个预测框都不能残留</b>。
     * 注销 + 快照清空双保险：即使某一帧的渲染回调还在路上，看到的也是空快照。</p>
     */
    public void detach() {
        if (attached) {
            attached = false;
            WorldOverlay.unregister(LAYER_ID);
        }
        snapshot = SeedRenderSnapshot.EMPTY;
        dirty = false;
    }

    /** 数据变了，快照需要重建（服务层在预测落地 / 观察变化 / 缓存淘汰后调用）。 */
    public void markDirty() {
        dirty = true;
    }

    /** 快照是否需要重建。 */
    public boolean dirty() {
        return dirty;
    }

    /** 换上新快照（服务层在主线程重建后调用）。 */
    public void updateSnapshot(SeedRenderSnapshot next) {
        snapshot = next == null ? SeedRenderSnapshot.EMPTY : next;
        dirty = false;
    }

    /** 当前快照（界面统计与渲染共用同一份读数）。 */
    public SeedRenderSnapshot snapshot() {
        return snapshot;
    }

    /** 本层是否已注册。 */
    public boolean attached() {
        return attached;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 渲染（每帧）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 世界渲染层回调（原版收集 gizmo 期间调用）。
     *
     * <p>本方法<b>只读快照</b>：不重建快照、不访问缓存、不读世界、不产生 BlockPos / String。</p>
     *
     * <p>它唯一做的事情是「按需计一次时」：{@link SeedRenderFrameProfiler} 默认关闭，关闭时这条分支恒不跳转，
     * 绘制路径与 233 完全一致（口径第五十五节：采样不得留在正式热路径，或必须默认关闭）。</p>
     */
    public void render(EspRenderer renderer) {
        if (!SeedRenderFrameProfiler.enabled()) {
            draw(renderer);
            return;
        }
        long startedNanos = System.nanoTime();
        draw(renderer);
        SeedRenderFrameProfiler.record(startedNanos, System.nanoTime(), snapshot);
    }

    /**
     * 真正的每帧绘制（口径第五十五节：帧级 CPU 采样只包在它外面，采样关闭时就直接走这里）。
     */
    private void draw(EspRenderer renderer) {
        if (renderer == null || !visible.getAsBoolean()) {
            return;
        }
        SeedRenderSnapshot current = snapshot;
        if (current.empty()) {
            return;
        }
        boolean includeMissing = showMissing.getAsBoolean();
        // 透墙显示（口径第三十四节）：交给原版 gizmo 忽略深度，始终画在最上层
        renderer.occlusion(false);

        List<SeedRenderEntry> entries = current.entries();
        for (int i = 0; i < entries.size(); i++) {
            SeedRenderEntry entry = entries.get(i);
            OreObservationState state = entry.state();
            if (state == OreObservationState.MISSING && !includeMissing) {
                continue;
            }
            int x = entry.position().getX();
            int y = entry.position().getY();
            int z = entry.position().getZ();
            if (state == OreObservationState.MISSING) {
                // 「预测里有、现在实际不是矿」：用统一灰细线，不填充（见 SeedOrePalette 的通道划分）
                renderer.blockBox(x, y, z, 0, SeedOrePalette.MISSING_LINE, ShapeMode.Lines, THICKNESS);
            } else {
                // 236：色相 = 矿物种类，明亮度 + 是否填充 = 观察状态（预测 / 已确认）
                int[] palette = state == OreObservationState.CONFIRMED
                        ? SeedOrePalette.confirmed(entry.oreType())
                        : SeedOrePalette.predicted(entry.oreType());
                renderer.blockBox(x, y, z, palette[1], palette[0], ShapeMode.Both, THICKNESS);
            }
            if (entry.scheduleSensitive()) {
                renderer.box(markerBox(x, y, z), 0, SeedOrePalette.SCHEDULE_LINE, ShapeMode.Lines, MARKER_THICKNESS);
            }
        }
    }

    /**
     * 「调度敏感」小标记的内缩方框。
     *
     * <p><b>为什么用叠加标记而不是换颜色</b>：调度敏感是<b>预测</b>维度，而三种颜色是<b>观察</b>维度。
     * 换颜色就等于把两个维度压进一个视觉通道，玩家会以为「琥珀 = 另一种矿」
     * （口径第八、三十六节禁止这么做）。</p>
     */
    private static AABB markerBox(int x, int y, int z) {
        return new AABB(x + MARKER_INSET, y + MARKER_INSET, z + MARKER_INSET,
                x + 1d - MARKER_INSET, y + 1d - MARKER_INSET, z + 1d - MARKER_INSET);
    }

    /**
     * 该目标区块是否在渲染快照里（开发诊断 / 报告用；例如核对「Chunk 卸载后框没了」）。
     */
    public boolean containsChunk(ChunkPos chunk) {
        if (chunk == null) {
            return false;
        }
        for (SeedRenderEntry entry : snapshot.entries()) {
            int cx = entry.position().getX() >> 4;
            int cz = entry.position().getZ() >> 4;
            if (cx == chunk.x() && cz == chunk.z()) {
                return true;
            }
        }
        return false;
    }
}
