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
    /** 冰霜层压暗叠加（6% 黑）：让被模糊的内容沉下去。属材质合成参数，与主题配色无关。 */
    private static final int FROST_DARKEN = 0x10000000;
    private final Paint blurPaint = new Paint().setAntiAlias(true);
    private final Paint frostPaint = new Paint().setAntiAlias(true);
    private final Paint tintPaint = new Paint().setAntiAlias(true);
    /** 折射仅绘制窄边带，不对每个控件重复捕获或模糊场景。 */
    private final Paint refractionPaint = new Paint().setAntiAlias(true);
    private final SkiaGlBackend framebufferBackend = new SkiaGlBackend();
    private ImageFilter linearizeFilter;
    private ImageFilter blurFilter;
    private ImageFilter encodeFilter;
    private float filterSigma = Float.NaN;
    private boolean nativeLoaded = false;

    private SkiaBlurRenderer() {}

    public static SkiaBlurRenderer getInstance() {
        return INSTANCE;
    }

    public static int currentDrawFramebufferId() {
        int[] framebuffer = new int[1];
        glGetIntegerv(GL_DRAW_FRAMEBUFFER_BINDING, framebuffer);
        return framebuffer[0];
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
        Capture capture = captureRegion(context, client, sourceFramebufferId, x, y, width, height, scale, Math.max(MIN_CAPTURE_MARGIN, blurSigma * 2f));
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
            frostPaint.setColor(ClickGuiThemeColors.withAlpha(ClickGuiThemeColors.current().window, 0.035f));
            canvas.drawRRect(RRect.makeXYWH(x, y, width, height, radius), frostPaint);

            tintPaint.setColor(glassTint(tintColor));
            canvas.drawRRect(RRect.makeXYWH(x, y, width, height, radius), tintPaint);
            return true;
        } finally {
            blurPaint.setImageFilter(null);
            canvas.restore();
            capture.image.close();
        }
    }

    private boolean renderRegions(Canvas canvas, DirectContext context, Minecraft client, int sourceFramebufferId,
                                  List<Region> regions, float x, float y, float width, float height, int tintColor, float strength) {
        ensureNativeLoaded();
        float scale = (float) client.getWindow().getGuiScale();
        boolean blurEnabled = strength > 0.001f;
        float blurSigma = blurEnabled ? blurSigma(strength) : 0f;
        Capture capture = captureRegion(context, client, sourceFramebufferId, x, y, width, height, scale, Math.max(MIN_CAPTURE_MARGIN, blurSigma * 2f));
        if (capture.image == null) return false;

        if (blurEnabled) ensureFilters(blurSigma);
        canvas.save();
        try {
            blurPaint.setImageFilter(blurEnabled ? encodeFilter : null);
            frostPaint.setColor(ClickGuiThemeColors.withAlpha(ClickGuiThemeColors.current().window, 0.035f));
            tintPaint.setColor(glassTint(tintColor));
            Rect source = Rect.makeXYWH(0f, 0f, capture.width, capture.height);
            Rect destination = Rect.makeXYWH(capture.dstX, capture.dstY, capture.dstW, capture.dstH);
            for (Region region : regions) {
                RRect shape = RRect.makeXYWH(region.x(), region.y(), region.width(), region.height(), region.radius());
                canvas.save();
                canvas.clipRRect(shape, true);
                canvas.drawImageRect(capture.image, source, destination, SamplingMode.LINEAR, blurPaint, true);
                drawRefraction(canvas, capture, region.x(), region.y(), region.width(), region.height(), region.radius());
                canvas.drawRRect(shape, frostPaint);
                canvas.drawRRect(shape, tintPaint);
                canvas.restore();
            }
            return true;
        } finally {
            blurPaint.setImageFilter(null);
            canvas.restore();
            capture.image.close();
        }
    }

    /** 色调取自当前主题，保留透明度设置的控制作用，浅色主题不再被固定黑色污染。 */
    private static int glassTint(int requested) {
        return ClickGuiThemeColors.withAlpha(ClickGuiThemeColors.current().window,
                ((requested >>> 24) / 255f) * 0.45f);
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
                    left / scale, top / scale, copyW / scale, copyH / scale);
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
        private static final Capture EMPTY = new Capture(null, 0, 0, 0f, 0f, 0f, 0f);

        private final Image image;
        private final int width;
        private final int height;
        private final float dstX;
        private final float dstY;
        private final float dstW;
        private final float dstH;

        private Capture(Image image, int width, int height, float dstX, float dstY, float dstW, float dstH) {
            this.image = image;
            this.width = width;
            this.height = height;
            this.dstX = dstX;
            this.dstY = dstY;
            this.dstW = dstW;
            this.dstH = dstH;
        }
    }

    public record Region(float x, float y, float width, float height, float radius) {}

    private record CaptureTarget(int textureId, int framebufferId, int width, int height, Image image) {}
}
