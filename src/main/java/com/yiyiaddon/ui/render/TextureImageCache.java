package com.yiyiaddon.ui.render;

import com.mojang.blaze3d.platform.NativeImage;
import com.yiyiaddon.platform.resource.ResourcePackAccess;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.types.Rect;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 资源包贴图缓存：把 {@code assets/<ns>/textures/**.png} 读成 Skija 图像并常驻。
 *
 * <p><b>为什么需要它</b>：ESP 世界字牌的图标画在 GUI 通道开始<b>之前</b>
 * （见 {@code WorldOverlay#renderOverlay}），那一刻没有 {@code GuiGraphicsExtractor}，
 * {@link ItemIconCache} 的「隐藏格子借位渲染 + 回读反解」链路在这里根本无从驱动；
 * 这条路径上唯一可用的做法是直接读资源包 PNG。逐帧读盘 + 解码显然不可接受，故在此常驻。</p>
 *
 * <p><b>失败不重试</b>：资源包里没有这张图（换服 / 换资源包）就记进 {@link #FAILED}，
 * 之后每帧直接跳过，既不刷日志也不逐帧重试。</p>
 *
 * <p><b>线程与时机</b>：只在渲染线程、且已有 Skija 画布时调用（与 {@link PlayerFaceCache} 同一约束）。</p>
 */
public final class TextureImageCache {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/texture");

    /** 常驻上限：图标都是 16×16 级别的小图，这个上限只是防御性兜底。 */
    private static final int CAPACITY = 512;

    /** 资源路径 → 解好的图像。 */
    private static final Map<Identifier, Image> IMAGES = new HashMap<>();

    /** 读取失败过的资源路径：不再逐帧重试。 */
    private static final Set<Identifier> FAILED = new HashSet<>();

    /** 透明度画笔：Skija 的 Paint 是原生资源，全类复用一份，每帧只改 alpha。 */
    private static final Paint TINT = new Paint().setAntiAlias(true);

    private TextureImageCache() {
    }

    /**
     * 把一张资源包贴图画进 {@code size × size} 的方格里（等比缩放、居中）。
     *
     * @param texture 贴图资源路径（含 {@code textures/} 前缀与 {@code .png} 后缀）
     * @return true 表示已绘制；false 表示这张图当前画不了（没读到 / 已标记失败 / 透明度为零）
     */
    public static boolean draw(Canvas canvas, Identifier texture, float x, float y, float size, float alpha) {
        if (canvas == null || texture == null || size <= 0f || alpha <= 0.004f) return false;

        Image image = image(texture);
        if (image == null) return false;

        float width = image.getWidth();
        float height = image.getHeight();
        if (width <= 0f || height <= 0f) return false;

        // 等比缩放进 size×size 的方格：非正方形贴图（部分作物图更长）不会被拉变形
        float scale = Math.min(size / width, size / height);
        float drawWidth = width * scale;
        float drawHeight = height * scale;
        TINT.setAlphaf(Math.min(1f, alpha));
        // DEFAULT = 最近邻：16×16 的原图放大后才保持像素质感（与 PlayerFaceCache 同一口径）；
        // LINEAR 会把小图糊成一团，看着像「贴图烂」
        canvas.drawImageRect(image, Rect.makeWH(width, height),
            Rect.makeXYWH(x + (size - drawWidth) * 0.5f, y + (size - drawHeight) * 0.5f, drawWidth, drawHeight),
            SamplingMode.DEFAULT, TINT, true);
        return true;
    }

    /**
     * 释放全部贴图。
     *
     * <p>缓存超限时由内部整体重建时调用；也留给「资源包重载 / 渲染后端销毁」这类时机
     * （与 {@link PlayerFaceCache#clear()} 同一口径，项目当前没有这类钩子）。</p>
     */
    public static void clear() {
        for (Image image : IMAGES.values()) image.close();
        IMAGES.clear();
        FAILED.clear();
    }

    // ── 取图 ──

    /** 取（或首次读取）某张贴图；读取失败会记入 {@link #FAILED} 并不再重试。 */
    private static Image image(Identifier texture) {
        Image cached = IMAGES.get(texture);
        if (cached != null) return cached;
        if (FAILED.contains(texture)) return null;

        Image created = read(texture);
        if (created == null) {
            FAILED.add(texture);
            return null;
        }
        if (IMAGES.size() >= CAPACITY) clear();
        IMAGES.put(texture, created);
        return created;
    }

    /**
     * 按资源优先级读取并解码这张 PNG。
     *
     * <p>与 {@code StardewPreview} 同一口径：{@code ResourceManager#listPacks()} 是「低优先级在前」，
     * 必须倒序找，才能取到被服务器资源包覆盖后的那张图。</p>
     */
    private static Image read(Identifier texture) {
        for (PackResources pack : ResourcePackAccess.packsByPriority()) {
            IoSupplier<InputStream> supplier;
            try {
                supplier = pack.getResource(PackType.CLIENT_RESOURCES, texture);
            } catch (Exception ignored) {
                // 单个包读取异常：继续找下一个包
                continue;
            }
            if (supplier == null) continue;

            try (InputStream in = supplier.get()) {
                return decode(in);
            } catch (Exception error) {
                // 命中但解不开：如实记一条，随后按「该资源不可用」处理
                LOGGER.warn("贴图解不开：{}（{}）", texture, error.toString());
                return null;
            }
        }
        return null;
    }

    /**
     * 用原版解码器把 PNG 解成像素，再按 Skija 的光栅图建出来。
     *
     * <p><b>为什么不用 Skija 自带的解码</b>（真机日志实证 2026-09-22 02:47:47）：本机
     * {@code Image.makeDeferredFromEncodedBytes} 对这批作物贴图一律失败
     * （{@code Failed to Image::makeFromEncoded}，四张区域图全挂在同一个点上，路径本身是对的）。
     * 原版解码器就是客户端渲染这些贴图用的那一个 —— 能画出来就一定能解开，也不再挑资源包 PNG 的
     * 具体编码（调色板 / 真彩 / 隔行通吃），这正是「适配多种服务器」要的口径。</p>
     *
     * <p>建图口径与 {@link PlayerFaceCache} / {@link ItemIconCache} 完全一致：原版
     * {@link NativeImage.Format#RGBA} 是直通 alpha（内存里就是 R,G,B,A 四个字节），必须预乘后再按
     * {@link ColorAlphaType#PREMUL} 建图，否则透明区在本机会被画成黑块（用户 2026-09-16 反馈的
     * 「首页头像黑底」就是同一条教训）。</p>
     */
    private static Image decode(InputStream in) throws IOException {
        try (NativeImage pixels = NativeImage.read(NativeImage.Format.RGBA, in)) {
            int width = pixels.getWidth();
            int height = pixels.getHeight();
            if (width <= 0 || height <= 0) return null;
            int length = width * height * 4;
            byte[] rgba = new byte[length];
            // 直接按原版那块原生内存取一份：RGBA 格式下它就是逐像素 R,G,B,A，行序自上而下
            MemoryUtil.memByteBuffer(pixels.getPointer(), length).get(rgba);
            premultiply(rgba);
            return Image.makeRasterFromBytes(
                new ImageInfo(width, height, ColorType.RGBA_8888, ColorAlphaType.PREMUL, ColorSpace.getSRGB()),
                rgba, width * 4);
        }
    }

    /** 直通 alpha → 预乘 alpha（原地）；与 {@link PlayerFaceCache} 同一算法。 */
    private static void premultiply(byte[] rgba) {
        for (int i = 0; i < rgba.length; i += 4) {
            int alpha = rgba[i + 3] & 0xFF;
            if (alpha == 255) continue;
            if (alpha == 0) {
                rgba[i] = 0;
                rgba[i + 1] = 0;
                rgba[i + 2] = 0;
                continue;
            }
            rgba[i] = (byte) ((rgba[i] & 0xFF) * alpha / 255);
            rgba[i + 1] = (byte) ((rgba[i + 1] & 0xFF) * alpha / 255);
            rgba[i + 2] = (byte) ((rgba[i + 2] & 0xFF) * alpha / 255);
        }
    }
}
