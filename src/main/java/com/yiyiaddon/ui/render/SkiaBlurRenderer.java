package com.yiyiaddon.ui.render;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ClipMode;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.ColorFilter;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.FilterTileMode;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageFilter;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.SurfaceOrigin;
import io.github.humbleui.skija.impl.Library;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import net.minecraft.client.Minecraft;

import java.util.List;

import static org.lwjgl.opengl.GL45.*;

/** 背景玻璃合成：一次捕获供磨砂主体和圆角折射边带共同使用。 */
public final class SkiaBlurRenderer {
    private static final SkiaBlurRenderer INSTANCE = new SkiaBlurRenderer();
    private static final float MIN_CAPTURE_MARGIN = 18f;
    /**
     * 世界帧截取时在面板矩形之外再留的余量（GUI 逻辑像素）。
     *
     * <p>要同时容下模糊半径（{@link #blurSigma} 最大 21，需要约 2 倍余量）与开合动画期间面板一帧的位移，
     * 否则玻璃会采到面板矩形之外、或退化成现场采样（自反馈）。</p>
     */
    private static final float WORLD_FRAME_PAD = 48f;
    private final Paint blurPaint = new Paint().setAntiAlias(true);
    /** 主题色蒙版：玻璃画完之后叠在面板区域上，决定「这块玻璃有多深」。 */
    private final Paint tintPaint = new Paint().setAntiAlias(true);
    /** 折射仅绘制窄边带，不对每个控件重复捕获或模糊场景。 */
    private final Paint refractionPaint = new Paint().setAntiAlias(true);
    private final SkiaGlBackend framebufferBackend = new SkiaGlBackend();
    private ImageFilter linearizeFilter;
    private ImageFilter blurFilter;
    private ImageFilter encodeFilter;
    private float filterSigma = Float.NaN;
    private boolean nativeLoaded = false;

    /**
     * 本帧登记的「面板玻璃区域」（GUI 逻辑坐标，已含外扩）；由画玻璃的 {@link #render} 顺手登记。
     *
     * <p><b>为什么要单独登记、而不是玻璃自己现场采样</b>（用户 2026-09-21：「黑块没有了 但是蓝色的还有」）：
     * 玻璃绘制发生在帧末，那时主帧缓冲里面板区域已经是<b>上一帧的面板自身</b>（行底色、文字）。
     * 现场采样等于「把上一帧的面板再模糊一次盖回面板」，逐帧累积成一片扩散的蓝灰雾 —— 面板上原来那层
     * 不透明霜化把这条路遮住了，霜化撤掉后自反馈就显形。正解是让玻璃采样<b>世界帧</b>：
     * GUI 通道开始画之前（世界已画完、界面还没上屏）把这块区域截下来存着，帧末玻璃用它当背景。</p>
     */
    private float[] worldFrameRequest;
    /** GUI 通道之前截下的世界帧贴图（GPU 纹理，不回落 CPU），以及它在 GUI 逻辑坐标下的矩形。 */
    private Image worldFrameImage;
    private float[] worldFrameBox;

    private SkiaBlurRenderer() {}

    public static SkiaBlurRenderer getInstance() {
        return INSTANCE;
    }

    public static int currentDrawFramebufferId() {
        int[] framebuffer = new int[1];
        glGetIntegerv(GL_DRAW_FRAMEBUFFER_BINDING, framebuffer);
        return framebuffer[0];
    }

    /**
     * 登记本帧要采样的面板玻璃区域（GUI 逻辑坐标，调用方已含外扩）。
     *
     * <p>由画玻璃的 {@link #render} 顺手登记：本帧画到哪里，就为**下一帧**的
     * {@link #captureWorldFrame()}（GUI 通道之前）准备要截的世界区域。玻璃是模糊背景，
     * 用上一帧截下的世界帧在观感上没有差别；外扩留出模糊半径与开合动画一帧的位移。</p>
     */
    public void requestWorldFrame(float x, float y, float width, float height) {
        worldFrameRequest = new float[]{x, y, width, height};
    }

    /**
     * 在 GUI 通道开始绘制之前截下登记区域的世界帧（世界已画完、界面还没上屏）。
     *
     * <p>由 {@code GuiRendererMixin} 在 {@code GuiRenderer#render} 的 HEAD 调用，紧接着才轮到 ESP
     * 叠加层与界面绘制。截取走 {@code glBlitFramebuffer} 到临时纹理 + Skija 收养，不回落 CPU，
     * 每帧一次、区域为一个面板大小，开销可忽略。</p>
     */
    public void captureWorldFrame() {
        float[] request = worldFrameRequest;
        if (request == null) {
            // 这一帧没有玻璃要画（或没登记）：保留上一帧的世界帧。丢掉它只会让玻璃退回现场采样，
            // 把上一帧的面板再糊一遍 —— 那正是要避免的蓝雾；旧一帧的世界在模糊背景里看不出来。
            return;
        }
        worldFrameRequest = null;
        Minecraft client = Minecraft.getInstance();
        if (request[2] <= 1f || request[3] <= 1f) return;
        if (client == null || client.getWindow() == null || client.gameRenderer.mainRenderTarget() == null) return;
        ensureNativeLoaded();
        DirectContext context = SkiaGlBackend.sharedContext();
        if (context == null) return;
        Capture capture = captureRegion(context, client, mainFramebufferId(client),
                request[0], request[1], request[2], request[3],
                (float) client.getWindow().getGuiScale(), 0f);
        if (capture.image == null) return;
        releaseWorldFrame();
        worldFrameImage = capture.image;
        worldFrameBox = new float[]{capture.dstX, capture.dstY, capture.dstW, capture.dstH};
    }

    /** 释放本帧的世界帧贴图（下一帧截取前、或资源重载 / 分辨率变化时调用）。 */
    public void releaseWorldFrame() {
        if (worldFrameImage != null) {
            worldFrameImage.close();
            worldFrameImage = null;
        }
        worldFrameBox = null;
    }

    public boolean render(Minecraft client, float x, float y, float width, float height, float radius, int tintColor, float strength) {
        if (client == null || client.getWindow() == null || client.gameRenderer.mainRenderTarget() == null) return false;
        int framebufferId = mainFramebufferId(client);
        Canvas canvas = framebufferBackend.begin(framebufferId);
        DirectContext context = framebufferBackend.getContext();
        if (canvas == null || context == null) {
            framebufferBackend.end();
            return false;
        }
        try {
            return render(canvas, context, client, framebufferId, x, y, width, height, radius, tintColor, strength);
        } finally {
            framebufferBackend.end();
        }
    }

    /**
     * 把主 Framebuffer 的指定区域截取为一张 Skija 图像。
     *
     * <p>供离屏贴图采集使用（例如把原版 GUI 画好的物品图标取出成贴图）。坐标与尺寸均为
     * GUI 逻辑坐标，内部按 GUI Scale 换算到物理像素。返回的图像由调用方负责 {@code close()}；
     * 采集失败返回 {@code null}。</p>
     */
    public Image captureRegionImage(DirectContext context, float x, float y, float width, float height) {
        Minecraft client = Minecraft.getInstance();
        if (context == null || client == null || client.getWindow() == null || client.gameRenderer.mainRenderTarget() == null) {
            return null;
        }
        ensureNativeLoaded();
        float scale = (float) client.getWindow().getGuiScale();
        Capture capture = captureRegion(context, client, mainFramebufferId(client), x, y, width, height, scale, 0f);
        return capture.image;
    }

    public boolean renderRegions(Minecraft client, List<Region> regions, int tintColor, float strength) {
        if (client == null || client.getWindow() == null || client.gameRenderer.mainRenderTarget() == null || regions == null || regions.isEmpty()) {
            return false;
        }
        float left = Float.MAX_VALUE;
        float top = Float.MAX_VALUE;
        float right = -Float.MAX_VALUE;
        float bottom = -Float.MAX_VALUE;
        for (Region region : regions) {
            left = Math.min(left, region.x());
            top = Math.min(top, region.y());
            right = Math.max(right, region.x() + region.width());
            bottom = Math.max(bottom, region.y() + region.height());
        }

        int framebufferId = mainFramebufferId(client);
        Canvas canvas = framebufferBackend.begin(framebufferId);
        DirectContext context = framebufferBackend.getContext();
        if (canvas == null || context == null) {
            framebufferBackend.end();
            return false;
        }
        try {
            return renderRegions(canvas, context, client, framebufferId, regions, left, top, right - left, bottom - top, tintColor, strength);
        } finally {
            framebufferBackend.end();
        }
    }

    public boolean render(Canvas canvas, DirectContext context, Minecraft client, int sourceFramebufferId,
                          float x, float y, float width, float height, float radius, int tintColor, float strength) {
        if (canvas == null || context == null || client == null || client.getWindow() == null) return false;
        ensureNativeLoaded();

        float scale = (float) client.getWindow().getGuiScale();
        boolean blurEnabled = strength > 0.001f;
        float blurSigma = blurEnabled ? blurSigma(strength) : 0f;
        // 为下一帧的世界帧截取登记区域（模糊半径 + 动画位移都留出来）
        requestWorldFrame(x - WORLD_FRAME_PAD, y - WORLD_FRAME_PAD,
                width + WORLD_FRAME_PAD * 2f, height + WORLD_FRAME_PAD * 2f);
        Capture capture = backgroundCapture(context, client, sourceFramebufferId, x, y, width, height, scale,
                Math.max(MIN_CAPTURE_MARGIN, blurSigma * 2f));
        if (capture.image == null) return false;

        if (blurEnabled) ensureFilters(blurSigma);
        canvas.save();
        try {
            canvas.clipRRect(RRect.makeXYWH(x, y, width, height, radius), true);
            blurPaint.setImageFilter(blurEnabled ? encodeFilter : null);
            canvas.drawImageRect(capture.image,
                    Rect.makeXYWH(0f, 0f, capture.width, capture.height),
                    Rect.makeXYWH(capture.dstX, capture.dstY, capture.dstW, capture.dstH),
                    SamplingMode.LINEAR,
                    blurPaint,
                    true);

            drawRefraction(canvas, capture, x, y, width, height, radius);
            // 主题色蒙版：用户 2026-09-21「怎么感觉你把我的主题调浅色了 没以前深色了」——
            // 这块颜色原来就在，之前跟霜化层一起被撤掉了，面板于是比以往亮。它叠在玻璃图像之上，
            // 属于「不透明玻璃」的一部分，不参与跨帧累积，可以放心保留。
            tintPaint.setColor(glassTint(tintColor));
            canvas.drawRRect(RRect.makeXYWH(x, y, width, height, radius), tintPaint);
            return true;
        } finally {
            blurPaint.setImageFilter(null);
            canvas.restore();
            releaseCapture(capture);
        }
    }

    /**
     * 玻璃背景的来源：优先用 GUI 通道之前截下的**世界帧**（见 {@link #requestWorldFrame}），
     * 取不到才现场对主帧缓冲做区域采样。
     *
     * <p>现场采样在帧末拿到的是上一帧的面板自身 —— 直接把面板再模糊一次盖回面板，会形成自反馈；
     * 世界帧是界面还没上屏时的画面，才是玻璃真正该透出的背景。</p>
     */
    private Capture backgroundCapture(DirectContext context, Minecraft client, int sourceFramebufferId,
                                      float x, float y, float width, float height, float scale, float margin) {
        Image frame = worldFrameImage;
        float[] box = worldFrameBox;
        if (frame != null && box != null) {
            return new Capture(frame, frame.getWidth(), frame.getHeight(), box[0], box[1], box[2], box[3], true);
        }
        return captureRegion(context, client, sourceFramebufferId, x, y, width, height, scale, margin);
    }

    /** 释放一次采样的贴图；借来的世界帧归 {@link #releaseWorldFrame()} 管，不能在这里关。 */
    private static void releaseCapture(Capture capture) {
        if (capture != null && !capture.borrowed && capture.image != null) {
            capture.image.close();
        }
    }

    private boolean renderRegions(Canvas canvas, DirectContext context, Minecraft client, int sourceFramebufferId,
                                  List<Region> regions, float x, float y, float width, float height, int tintColor, float strength) {
        ensureNativeLoaded();
        float scale = (float) client.getWindow().getGuiScale();
        boolean blurEnabled = strength > 0.001f;
        float blurSigma = blurEnabled ? blurSigma(strength) : 0f;
        requestWorldFrame(x - WORLD_FRAME_PAD, y - WORLD_FRAME_PAD,
                width + WORLD_FRAME_PAD * 2f, height + WORLD_FRAME_PAD * 2f);
        Capture capture = backgroundCapture(context, client, sourceFramebufferId, x, y, width, height, scale,
                Math.max(MIN_CAPTURE_MARGIN, blurSigma * 2f));
        if (capture.image == null) return false;

        if (blurEnabled) ensureFilters(blurSigma);
        canvas.save();
        try {
            blurPaint.setImageFilter(blurEnabled ? encodeFilter : null);
            Rect source = Rect.makeXYWH(0f, 0f, capture.width, capture.height);
            Rect destination = Rect.makeXYWH(capture.dstX, capture.dstY, capture.dstW, capture.dstH);
            for (Region region : regions) {
                RRect shape = RRect.makeXYWH(region.x(), region.y(), region.width(), region.height(), region.radius());
                canvas.save();
                canvas.clipRRect(shape, true);
                canvas.drawImageRect(capture.image, source, destination, SamplingMode.LINEAR, blurPaint, true);
                drawRefraction(canvas, capture, region.x(), region.y(), region.width(), region.height(), region.radius());
                tintPaint.setColor(glassTint(tintColor));
                canvas.drawRRect(shape, tintPaint);
                canvas.restore();
            }
            return true;
        } finally {
            blurPaint.setImageFilter(null);
            canvas.restore();
            releaseCapture(capture);
        }
    }

    /**
     * 玻璃色调：取自当前主题的窗面色，透明度按设置里的玻璃色调 alpha 折算。
     *
     * <p><b>两个档位</b>（用户 2026-09-21：「颜色帮我调浅了好多 我想深空灰」）：设置里的
     * {@code blurTint} 只当「档位」用，实际蒙版强度由主题深浅决定 —— 暗色主题（深空灰）
     * 必须给足，否则 0.45 那档只留 14% 的自身窗面色，明亮场景一透过玻璃就把面板冲成浅灰
     * （实测截图里那块亮度接近 #737373）；浅色主题保持原来的轻蒙版，白底不会被黑色污染。</p>
     */
    private static final float TINT_SCALE_DARK = 2.2f;
    private static final float TINT_SCALE_LIGHT = 0.45f;

    private static int glassTint(int requested) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float strength = (requested >>> 24) / 255f;
        return ClickGuiThemeColors.withAlpha(tc.window, strength * (tc.dark ? TINT_SCALE_DARK : TINT_SCALE_LIGHT));
    }

    /**
     * 六条圆角边带对同一背景作递减放大采样，模拟厚玻璃边缘的光线偏折。
     * 中心保留磨砂；最外缘位移最强，向内衰减，不移动文字或交互坐标。
     */
    private void drawRefraction(Canvas canvas, Capture capture, float x, float y,
                                float width, float height, float radius) {
        float depth = Math.min(9f, Math.min(width, height) * 0.08f);
        if (depth < 1f) return;
        Rect source = Rect.makeXYWH(0f, 0f, capture.width, capture.height);
        for (int band = 0; band < 6; band++) {
            float inset = depth * band / 6f;
            float next = depth * (band + 1) / 6f;
            float falloff = 1f - band / 6f;
            float magnify = 1f + 0.035f * falloff * falloff;
            float cx = x + width * 0.5f;
            float cy = y + height * 0.5f;
            canvas.save();
            try {
                canvas.clipRRect(RRect.makeXYWH(x + inset, y + inset, width - 2f * inset,
                        height - 2f * inset, Math.max(0f, radius - inset)), true);
                canvas.clipRRect(RRect.makeXYWH(x + next, y + next, width - 2f * next,
                        height - 2f * next, Math.max(0f, radius - next)), ClipMode.DIFFERENCE, true);
                refractionPaint.setColor(ClickGuiThemeColors.withAlpha(ClickGuiThemeColors.current().rim,
                        0.58f * falloff));
                canvas.drawImageRect(capture.image, source,
                        Rect.makeXYWH(cx + (capture.dstX - cx) * magnify,
                                cy + (capture.dstY - cy) * magnify,
                                capture.dstW * magnify, capture.dstH * magnify),
                        SamplingMode.LINEAR, refractionPaint, true);
            } finally {
                canvas.restore();
            }
        }
    }

    private Capture captureRegion(DirectContext context, Minecraft client, int sourceFramebufferId,
                                  float x, float y, float width, float height, float scale, float margin) {
        int framebufferW = client.getWindow().getWidth();
        int framebufferH = client.getWindow().getHeight();
        int left = Math.max(0, (int) Math.floor((x - margin) * scale));
        int top = Math.max(0, (int) Math.floor((y - margin) * scale));
        int right = Math.min(framebufferW, (int) Math.ceil((x + width + margin) * scale));
        int bottom = Math.min(framebufferH, (int) Math.ceil((y + height + margin) * scale));
        int copyW = Math.max(1, right - left);
        int copyH = Math.max(1, bottom - top);
        int sourceY = Math.max(0, framebufferH - bottom);

        int[] oldTexture = new int[1];
        int[] oldActiveTexture = new int[1];
        int[] oldSampler = new int[1];
        int[] oldReadFramebuffer = new int[1];
        int[] oldDrawFramebuffer = new int[1];
        int[] oldReadBuffer = new int[1];
        int[] oldDrawBuffer = new int[1];
        int[] oldViewport = new int[4];
        int[] oldScissorBox = new int[4];
        boolean framebufferSrgb = glIsEnabled(GL_FRAMEBUFFER_SRGB);
        glGetIntegerv(GL_ACTIVE_TEXTURE, oldActiveTexture);
        glActiveTexture(GL_TEXTURE0);
        glGetIntegerv(GL_TEXTURE_BINDING_2D, oldTexture);
        glGetIntegerv(GL_SAMPLER_BINDING, oldSampler);
        glGetIntegerv(GL_READ_FRAMEBUFFER_BINDING, oldReadFramebuffer);
        glGetIntegerv(GL_DRAW_FRAMEBUFFER_BINDING, oldDrawFramebuffer);
        glGetIntegerv(GL_READ_BUFFER, oldReadBuffer);
        glGetIntegerv(GL_DRAW_BUFFER, oldDrawBuffer);
        glGetIntegerv(GL_VIEWPORT, oldViewport);
        glGetIntegerv(GL_SCISSOR_BOX, oldScissorBox);
        CaptureTarget target = null;
        boolean handedOff = false;
        try {
            target = ensureCaptureTarget(context, copyW, copyH);
            if (target == null) return Capture.EMPTY;

            glDisable(GL_FRAMEBUFFER_SRGB);
            glBindTexture(GL_TEXTURE_2D, target.textureId);
            glBindSampler(0, 0);

            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, target.framebufferId);
            glDrawBuffer(GL_COLOR_ATTACHMENT0);
            if (glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
                return Capture.EMPTY;
            }

            int readBuffer = prepareReadFramebuffer(sourceFramebufferId);
            if (readBuffer == 0) {
                return Capture.EMPTY;
            }
            glReadBuffer(readBuffer);
            glBlitFramebuffer(
                    left, sourceY, left + copyW, sourceY + copyH,
                    0, 0, copyW, copyH,
                    GL_COLOR_BUFFER_BIT,
                    GL_NEAREST
            );
            glFlush();
            glDeleteFramebuffers(target.framebufferId);
            handedOff = true;
            return new Capture(target.image, copyW, copyH,
                    left / scale, top / scale, copyW / scale, copyH / scale, false);
        } finally {
            if (target != null && !handedOff) {
                glDeleteFramebuffers(target.framebufferId);
                target.image.close();
            }
            glBindFramebuffer(GL_READ_FRAMEBUFFER, oldReadFramebuffer[0]);
            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, oldDrawFramebuffer[0]);
            restoreReadBuffer(oldReadFramebuffer[0], oldReadBuffer[0]);
            restoreDrawBuffer(oldDrawFramebuffer[0], oldDrawBuffer[0]);
            glViewport(oldViewport[0], oldViewport[1], oldViewport[2], oldViewport[3]);
            glScissor(oldScissorBox[0], oldScissorBox[1], oldScissorBox[2], oldScissorBox[3]);
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, oldTexture[0]);
            glBindSampler(0, oldSampler[0]);
            glActiveTexture(oldActiveTexture[0]);
            if (framebufferSrgb) {
                glEnable(GL_FRAMEBUFFER_SRGB);
            } else {
                glDisable(GL_FRAMEBUFFER_SRGB);
            }
        }
    }

    private CaptureTarget ensureCaptureTarget(DirectContext context, int requiredW, int requiredH) {
        int textureId = glGenTextures();
        int framebufferId = glGenFramebuffers();
        Image image = null;
        CaptureTarget created = null;
        try {
            glBindTexture(GL_TEXTURE_2D, textureId);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, requiredW, requiredH, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0L);

            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebufferId);
            glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureId, 0);
            glDrawBuffer(GL_COLOR_ATTACHMENT0);
            if (glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
                return null;
            }

            image = Image.adoptGLTextureFrom(context, textureId, GL_TEXTURE_2D, requiredW, requiredH,
                    GL_RGBA8, SurfaceOrigin.BOTTOM_LEFT, ColorType.RGB_888X);
            created = new CaptureTarget(textureId, framebufferId, requiredW, requiredH, image);
            return created;
        } finally {
            if (created == null) {
                if (framebufferId != 0) glDeleteFramebuffers(framebufferId);
                if (image != null) {
                    image.close();
                } else if (textureId != 0) {
                    glDeleteTextures(textureId);
                }
            }
        }
    }

    private void ensureFilters(float sigma) {
        if (encodeFilter != null && Math.abs(filterSigma - sigma) < 0.001f) return;
        destroyFilters();
        linearizeFilter = ImageFilter.makeColorFilter(ColorFilter.getSRGBToLinearGamma(), null);
        blurFilter = ImageFilter.makeBlur(sigma, sigma, FilterTileMode.CLAMP, linearizeFilter, (Rect) null);
        encodeFilter = ImageFilter.makeColorFilter(ColorFilter.getLinearToSRGBGamma(), blurFilter);
        filterSigma = sigma;
    }

    private void destroyFilters() {
        if (encodeFilter != null) encodeFilter.close();
        if (blurFilter != null) blurFilter.close();
        if (linearizeFilter != null) linearizeFilter.close();
        encodeFilter = null;
        blurFilter = null;
        linearizeFilter = null;
        filterSigma = Float.NaN;
    }

    private int prepareReadFramebuffer(int framebufferId) {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, framebufferId);
        if (glCheckFramebufferStatus(GL_READ_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            return 0;
        }
        if (framebufferId == 0) {
            return GL_BACK;
        }
        int attachmentType = glGetFramebufferAttachmentParameteri(
                GL_READ_FRAMEBUFFER,
                GL_COLOR_ATTACHMENT0,
                GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE
        );
        return attachmentType == GL_NONE ? 0 : GL_COLOR_ATTACHMENT0;
    }

    private void restoreReadBuffer(int framebufferId, int readBuffer) {
        if (readBuffer == GL_NONE) {
            glReadBuffer(GL_NONE);
            return;
        }
        if (framebufferId == 0) {
            glReadBuffer(isDefaultFramebufferReadBuffer(readBuffer) ? readBuffer : GL_BACK);
            return;
        }
        glReadBuffer(isColorAttachmentReadBuffer(readBuffer) ? readBuffer : GL_COLOR_ATTACHMENT0);
    }

    private void restoreDrawBuffer(int framebufferId, int drawBuffer) {
        if (drawBuffer == GL_NONE) {
            glDrawBuffer(GL_NONE);
            return;
        }
        if (framebufferId == 0) {
            glDrawBuffer(isDefaultFramebufferReadBuffer(drawBuffer) ? drawBuffer : GL_BACK);
            return;
        }
        glDrawBuffer(isColorAttachmentReadBuffer(drawBuffer) ? drawBuffer : GL_COLOR_ATTACHMENT0);
    }

    private boolean isDefaultFramebufferReadBuffer(int readBuffer) {
        return readBuffer == GL_FRONT
                || readBuffer == GL_BACK
                || readBuffer == GL_LEFT
                || readBuffer == GL_RIGHT
                || readBuffer == GL_FRONT_LEFT
                || readBuffer == GL_FRONT_RIGHT
                || readBuffer == GL_BACK_LEFT
                || readBuffer == GL_BACK_RIGHT;
    }

    private boolean isColorAttachmentReadBuffer(int readBuffer) {
        return readBuffer >= GL_COLOR_ATTACHMENT0 && readBuffer <= GL_COLOR_ATTACHMENT0 + 31;
    }

    private int mainFramebufferId(Minecraft client) {
        return SkiaGlBackend.mainFramebufferId();
    }

    private float blurSigma(float strength) {
        float clamped = Math.max(0f, Math.min(2f, strength));
        return clamped * 10.5f;
    }

    private void ensureNativeLoaded() {
        if (nativeLoaded) return;
        Library.load();
        nativeLoaded = true;
    }

    private static class Capture {
        private static final Capture EMPTY = new Capture(null, 0, 0, 0f, 0f, 0f, 0f, false);

        private final Image image;
        private final int width;
        private final int height;
        private final float dstX;
        private final float dstY;
        private final float dstW;
        private final float dstH;
        /** true 表示这张图是借来的（世界帧），本类不得关闭它。 */
        private final boolean borrowed;

        private Capture(Image image, int width, int height, float dstX, float dstY, float dstW, float dstH,
                        boolean borrowed) {
            this.image = image;
            this.width = width;
            this.height = height;
            this.dstX = dstX;
            this.dstY = dstY;
            this.dstW = dstW;
            this.dstH = dstH;
            this.borrowed = borrowed;
        }
    }

    public record Region(float x, float y, float width, float height, float radius) {}

    private record CaptureTarget(int textureId, int framebufferId, int width, int height, Image image) {}
}
