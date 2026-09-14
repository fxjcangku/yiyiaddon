package com.yiyiaddon.ui.render.world;

import com.yiyiaddon.ui.render.SkiaGlBackend;
import io.github.humbleui.skija.Canvas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 世界空间叠加层（ESP）的注册中心与帧钩子。
 *
 * <p>业务模块在启用时注册一层绘制回调，关闭时注销；模块内只描述「画什么」，
 * 不关心投影、画布来源与渲染时机。</p>
 *
 * <pre>{@code
 * WorldOverlay.register(id(), renderer -> {
 *     for (Entity target : targets) {
 *         renderer.box(target.getBoundingBox(), lineColor, sideColor, mode, 1.5f);
 *         renderer.tracer(target.position(), lineColor, 1.2f);
 *     }
 * });
 * }</pre>
 *
 * <p>绘制时机由 {@code GuiRendererMixin} 固定在 {@code GuiRenderer.render} 的 HEAD：
 * 此时世界已经画进主 Framebuffer、原版 GUI 尚未绘制，因此 ESP 压在世界之上、GUI 之下。
 * 这也意味着 ESP 天然不受深度缓冲遮挡——即透视效果。</p>
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
    }

    public static int layerCount() {
        return LAYERS.size();
    }

    /** 由 Mixin 每帧调用；没有注册层或不在世界时不产生任何开销。 */
    public static void renderFrame() {
        if (LAYERS.isEmpty()) return;

        RenderCamera camera = RenderCamera.capture();
        if (camera == null) return;

        // 必须显式指定主 RenderTarget 的 Framebuffer：此刻默认 Framebuffer 不是呈现目标，
        // 画上去会被后续的 blitToScreen 整体覆盖。
        Canvas canvas = BACKEND.begin(SkiaGlBackend.mainFramebufferId());
        if (canvas == null) return;
        surfaceCreated = true;

        try {
            EspRenderer renderer = new EspRenderer(canvas, new WorldProjector(camera), camera);
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
        } finally {
            BACKEND.end();
        }
    }

    /** 资源重载或分辨率变化后调用，强制重建 Skija 表面。 */
    public static void invalidateSurface() {
        if (!surfaceCreated) return;
        BACKEND.destroy();
        surfaceCreated = false;
    }
}
