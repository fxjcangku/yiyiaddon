package com.yiyiaddon.ui.render;

import com.mojang.blaze3d.opengl.FrameBufferAttachment;
import com.mojang.blaze3d.opengl.GlDevice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.GpuDeviceBackend;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yiyiaddon.mixin.client.GpuDeviceAccessor;
import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.FramebufferFormat;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceOrigin;
import net.minecraft.client.Minecraft;

import java.util.List;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColorMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER_BINDING;
import static org.lwjgl.opengl.GL30.GL_MAJOR_VERSION;
import static org.lwjgl.opengl.GL30.GL_MINOR_VERSION;
import static org.lwjgl.opengl.GL30.glGetIntegerv;

public final class SkiaGlBackend {
    private DirectContext context;
    private BackendRenderTarget renderTarget;
    private Surface surface;
    private Canvas canvas;
    private SkiaGlState state;
    private int width = -1;
    private int height = -1;
    private int framebufferId = -1;
    private boolean drawing = false;

    /**
     * 取 Minecraft 主 RenderTarget 的 GL Framebuffer 名字。
     *
     * <p><b>26.2 口径</b>：主 RenderTarget 不再挂在 {@code Minecraft} 上（26.1.2 的
     * {@code Minecraft#getMainRenderTarget} 已移除），改由 {@code GameRenderer#mainRenderTarget} 提供；
     * Framebuffer 的拼装也从 {@code GlTexture#getFbo} 收进了 {@code FrameBufferCache}，
     * 因此这里取颜色纹理与深度纹理两个 attachment 后交给缓存去建 / 复用同一个 FBO。</p>
     *
     * <p>{@code RenderSystem.getDevice()} 返回的是持有后端的 {@code GpuDevice} 包装，
     * 真正的 {@code GlDevice} 在 {@code backend} 字段里，因此经 {@link GpuDeviceAccessor}
     * 取出后再判定类型。取不到时回落到当前绑定的 draw framebuffer。</p>
     */
    public static int mainFramebufferId() {
        Minecraft minecraft = Minecraft.getInstance();
        RenderTarget mainTarget = minecraft.gameRenderer.mainRenderTarget();
        if (mainTarget != null && mainTarget.getColorTexture() instanceof FrameBufferAttachment color) {
            GpuDeviceBackend backend = ((GpuDeviceAccessor) RenderSystem.getDevice()).yiyiaddon$backend();
            if (backend instanceof GlDevice glDevice) {
                FrameBufferAttachment depth = mainTarget.getDepthTexture() instanceof FrameBufferAttachment attachment
                        ? attachment
                        : null;
                return glDevice.frameBufferCache().getFbo(
                        glDevice.directStateAccess(), List.of(color), depth);
            }
        }
        int[] binding = new int[1];
        glGetIntegerv(GL_DRAW_FRAMEBUFFER_BINDING, binding);
        return binding[0];
    }

    /**
     * 全项目共享的 Skija GL 上下文。
     *
     * <p><b>为什么必须共享：</b>同一 GL context 上并存多个 {@code GrDirectContext} 时，
     * 用 A 上下文「收养」的纹理在 B 上下文的画布上不保证可用（跨上下文贴图会被丢弃）。
     * 本项目有多个绘制后端（主界面 / 模块页 / 独立面板 / 世界叠加层）以及离屏贴图采集，
     * 全部走这一个上下文才能保证「甲处采集的贴图，乙处能正常绘制」。</p>
     */
    private static DirectContext sharedContext;

    /**
     * 「无面板收尾」用的共享后端实例。
     *
     * <p>隐藏格子的画面备份正常情况下由画格子的界面在帧末的面板绘制里写回；切屏那一帧没有任何
     * 面板绘制，需要一条独立的绘制路径（见 {@code ItemIconCache#flushBackdrop}）。这里给一个长驻实例：
     * 每次 new 都会重建一份 {@code Surface}（且不会释放），共用一个才不会反复吃 GPU 资源。</p>
     */
    private static SkiaGlBackend sharedBackend;

    public static SkiaGlBackend shared() {
        if (sharedBackend == null) sharedBackend = new SkiaGlBackend();
        return sharedBackend;
    }

    /** 取值即创建（要求当前线程已有可用的 GL 上下文，故只在渲染线程调用）。 */
    public static DirectContext sharedContext() {
        if (sharedContext == null) sharedContext = DirectContext.makeGL();
        return sharedContext;
    }

    public Canvas begin() {
        return begin(0);
    }

    public Canvas begin(int targetFramebufferId) {
        if (drawing) return canvas;
        var window = Minecraft.getInstance().getWindow();
        int targetW = Math.max(1, window.getWidth());
        int targetH = Math.max(1, window.getHeight());
        ensureState();
        state.push();
        try {
            ensureSurface(targetW, targetH, targetFramebufferId);
            if (surface == null || canvas == null) {
                state.pop();
                return null;
            }

            context.resetGLAll();
            glDisable(GL_CULL_FACE);
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_SCISSOR_TEST);
            glDisable(GL_STENCIL_TEST);
            glDepthMask(false);
            glColorMask(true, true, true, true);
            glEnable(GL_BLEND);
            glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
            glViewport(0, 0, targetW, targetH);
            glClearColor(0f, 0f, 0f, 0f);

            canvas.restoreToCount(1);
            canvas.resetMatrix();
            canvas.save();
            canvas.scale((float) window.getGuiScale(), (float) window.getGuiScale());
            drawing = true;
            // 主帧缓冲开始绘制：若本帧有隐藏格子的画面备份，先画回去，格子对玩家彻底不可见
            // （时机正好在面板绘制之前，面板玻璃采样到的也就是干净画面）。
            if (targetFramebufferId == mainFramebufferId()) {
                ItemIconCache.getInstance().paintBackdrop(canvas);
            }
            return canvas;
        } catch (RuntimeException e) {
            state.pop();
            throw e;
        }
    }

    public void end() {
        if (!drawing || surface == null) return;
        try {
            canvas.restore();
            context.flushAndSubmit(surface);
        } finally {
            drawing = false;
            state.pop();
        }
    }

    public boolean isDrawing() {
        return drawing;
    }

    public boolean hasSurface() {
        return surface != null;
    }

    public DirectContext getContext() {
        return context;
    }

    public void resetCanvasState() {
        drawing = false;
        if (canvas != null) {
            canvas.restoreToCount(1);
            canvas.resetMatrix();
        }
    }

    public void runWithSavedState(Runnable action) {
        ensureState();
        state.push();
        try {
            action.run();
        } finally {
            state.pop();
        }
    }

    public void destroy() {
        if (drawing) {
            end();
        }
        SkiaGlState savedState = state;
        if (savedState != null) {
            savedState.push();
        }
        try {
            resetCanvasState();
            if (surface != null) {
                surface.close();
                surface = null;
            }
            if (renderTarget != null) {
                renderTarget.close();
                renderTarget = null;
            }
            // 上下文是全项目共享的，销毁单个后端时绝不关闭它（否则其它界面与已采集的贴图一起失效）
            context = null;
            canvas = null;
            width = -1;
            height = -1;
            framebufferId = -1;
        } finally {
            if (savedState != null) {
                savedState.pop();
            }
            state = null;
        }
    }

    private void ensureSurface(int targetW, int targetH, int targetFramebufferId) {
        ensureContext();
        if (surface != null && targetW == width && targetH == height && targetFramebufferId == framebufferId) return;

        if (surface != null) {
            surface.close();
            surface = null;
        }
        if (renderTarget != null) {
            renderTarget.close();
            renderTarget = null;
        }

        renderTarget = BackendRenderTarget.makeGL(targetW, targetH, 0, 8, targetFramebufferId, FramebufferFormat.GR_GL_RGBA8);
        surface = Surface.wrapBackendRenderTarget(
                context,
                renderTarget,
                SurfaceOrigin.BOTTOM_LEFT,
                ColorType.RGBA_8888,
                ColorSpace.getSRGB()
        );
        canvas = surface.getCanvas();
        width = targetW;
        height = targetH;
        framebufferId = targetFramebufferId;
    }

    private void ensureContext() {
        if (context != null) return;
        context = sharedContext();
    }

    private void ensureState() {
        if (state != null) return;
        state = new SkiaGlState(readGlVersion());
    }

    private static int readGlVersion() {
        int[] major = new int[1];
        int[] minor = new int[1];
        glGetIntegerv(GL_MAJOR_VERSION, major);
        glGetIntegerv(GL_MINOR_VERSION, minor);
        return major[0] * 100 + minor[0] * 10;
    }
}
