package com.yiyiaddon.ui.render.world;

import com.yiyiaddon.ui.render.SkiaGlBackend;
import io.github.humbleui.skija.Canvas;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 世界空间叠加层（ESP）的注册中心与帧钩子。
 *
 * <p>业务模块在启用时注册一层绘制回调，关闭时注销；模块内只描述「画什么」，
 * 不关心投影、画布来源与渲染时机。</p>
 *
 * <p>一帧分两个阶段，各由一个钩子驱动：</p>
 * <ul>
 *   <li>{@link #collectGeometry()}：由 Fabric {@code LevelRenderEvents.BEFORE_GIZMOS} 每帧调用，
 *       此时原版已注册 gizmo 收集器、尚未定稿。各层回调在这里跑一遍：<b>框 / 线 / 面直接
 *       交给原版 {@code Gizmos}，由 GPU 在世界空间绘制</b>（顶点随世界一起变换，所以走动时框
 *       严丝合缝贴住方块，没有屏幕空间描边的抖动与滞后）；字牌这类只能 2D 画的元素被记进
 *       {@link #DEFERRED} 队列。</li>
 *   <li>{@link #renderOverlay()}：由 {@code GuiRendererMixin} 固定在 {@code GuiRenderer.render}
 *       的 HEAD 调用。此时世界已画进主 Framebuffer、原版 GUI 尚未绘制，因此叠加的字牌压在
 *       世界之上、GUI 之下。<b>只有队列非空时才会开 Skija 画布</b>——没有 2D 元素时整帧零 GL
 *       状态开销。</li>
 * </ul>
 */
public final class WorldOverlay {

    /** 单层绘制回调。 */
    @FunctionalInterface
    public interface Layer {
        void render(EspRenderer renderer);
    }

    private static final Map<String, Layer> LAYERS = new ConcurrentHashMap<>();

    /** 已上报过异常的层：同一层只报一次，避免逐帧刷日志。 */
    private static final Set<String> REPORTED = ConcurrentHashMap.newKeySet();
    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/world-overlay");
    private static final SkiaGlBackend BACKEND = new SkiaGlBackend();
    private static volatile boolean surfaceCreated;

    /** 本帧待画的 2D 元素：几何阶段写入，叠加阶段画完即清。 */
    private static final List<EspRenderer.Deferred> DEFERRED = new ArrayList<>();
    /** 本帧是否已完成几何收集；没有收集（例如不在世界）时叠加阶段整帧不画。 */
    private static boolean collectedThisFrame;

    static {
        // 几何必须原版收集 gizmo 期间提交：Gizmos 在没有收集器时会直接抛异常。
        // BEFORE_GIZMOS 的注入点就在原版 finalizeGizmoCollection() 之前，正好是这个窗口。
        LevelRenderEvents.BEFORE_GIZMOS.register(context -> collectGeometry());
    }

    private WorldOverlay() {
    }

    /**
     * 注册一层绘制回调。
     *
     * @param ownerId 所有者标识，同一所有者重复注册会覆盖
     */
    public static void register(String ownerId, Layer layer) {
        if (ownerId == null || ownerId.isEmpty() || layer == null) return;
        LAYERS.put(ownerId, layer);
        REPORTED.remove(ownerId);
    }

    public static void unregister(String ownerId) {
        if (ownerId == null) return;
        LAYERS.remove(ownerId);
        REPORTED.remove(ownerId);
    }

    public static boolean isRegistered(String ownerId) {
        return ownerId != null && LAYERS.containsKey(ownerId);
    }

    public static void clear() {
        LAYERS.clear();
        REPORTED.clear();
        DEFERRED.clear();
        collectedThisFrame = false;
    }

    public static int layerCount() {
        return LAYERS.size();
    }

    /**
     * 几何阶段（原版收集 gizmo 期间）：把所有层的几何交给原版 gizmo 体系，2D 元素入队。
     *
     * <p>没有注册层、不在世界、ESP 总开关关闭时直接短路——既不投影也不产生任何对象。</p>
     */
    public static void collectGeometry() {
        DEFERRED.clear();
        collectedThisFrame = false;
        if (LAYERS.isEmpty()) return;
        // ESP 全局总开关：关掉后连相机快照都不取，各绘制层也完全跳过
        if (!EspGlobalSettings.get().enabled()) return;

        RenderCamera camera = RenderCamera.capture();
        if (camera == null) return;

        EspRenderer renderer = EspRenderer.geometry(camera, DEFERRED);
        for (Map.Entry<String, Layer> entry : LAYERS.entrySet()) {
            Layer layer = entry.getValue();
            if (layer == null) continue;
            try {
                layer.render(renderer);
            } catch (Throwable error) {
                // 单层异常不影响其它层与本帧渲染；同一层只上报一次
                if (REPORTED.add(entry.getKey())) {
                    LOGGER.error("世界渲染层 {} 绘制异常", entry.getKey(), error);
                }
            }
        }
        collectedThisFrame = true;
    }

    /**
     * 叠加阶段（{@code GuiRenderer.render} 的 HEAD）：把字牌与屏幕空间元素压到世界之上、GUI 之下。
     *
     * <p>必须显式指定主 RenderTarget 的 Framebuffer：此刻默认 Framebuffer 不是呈现目标，
     * 画上去会被后续的 blitToScreen 整体覆盖。</p>
     *
     * <p><b>这里只能开普通画布，不能写回隐藏格子备份</b>：本方法跑在 GUI 通道开始之前，
     * 而隐藏格子的画面备份是一次性的、必须留给帧末画面板的界面路径消费
     * （{@code SkiaGlBackend#beginScreenFrame}）。在这里抢先取画布会把备份白白用掉，
     * 随后 GUI 通道画上的格子就无人覆盖 —— 面板透明处会露出两行放大的物品图标。</p>
     */
    public static void renderOverlay() {
        if (!collectedThisFrame) return;
        collectedThisFrame = false;
        if (DEFERRED.isEmpty()) return;

        RenderCamera camera = RenderCamera.capture();
        if (camera == null) {
            DEFERRED.clear();
            return;
        }

        Canvas canvas = BACKEND.begin(SkiaGlBackend.mainFramebufferId());
        if (canvas == null) {
            DEFERRED.clear();
            return;
        }
        surfaceCreated = true;

        try {
            EspRenderer overlay = EspRenderer.overlay(canvas, new WorldProjector(camera), camera);
            overlay.drawDeferred(DEFERRED);
        } catch (Throwable error) {
            if (REPORTED.add("overlay")) {
                LOGGER.error("ESP 叠加层绘制异常", error);
            }
        } finally {
            BACKEND.end();
            DEFERRED.clear();
        }
    }

    /** 资源重载或分辨率变化后调用，强制重建 Skija 表面。 */
    public static void invalidateSurface() {
        if (!surfaceCreated) return;
        BACKEND.destroy();
        surfaceCreated = false;
    }
}
