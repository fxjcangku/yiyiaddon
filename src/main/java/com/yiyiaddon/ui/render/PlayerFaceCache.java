package com.yiyiaddon.ui.render;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.textures.GpuTexture;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.types.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerSkin;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.lwjgl.opengl.GL45.*;

/**
 * 玩家头像：把 {@link PlayerSkin} 的皮肤贴图读成 Skija 贴图，只画「脸」与「帽子层」两块 8×8，
 * 因此是纯 2D 头像，不依赖 3D 玩家模型，也不需要联网解析皮肤。
 *
 * <p><b>为什么不能复用 {@link ItemIconCache}</b>：它的缓存键是「物品 id + {@code ITEM_MODEL} 组件」
 * （{@code ItemIconCache#keyOf}），<b>不含 {@code PROFILE} 组件</b> —— 所有玩家头颅的键完全相同，
 * 即便渲染成功也只能命中到「第一个被采集到的玩家」的那张脸，其余玩家会共用它。
 * 对一个「每人一张」的头像需求，那是设计层面的不匹配，不是参数问题。</p>
 *
 * <p><b>取像方式</b>：皮肤贴图本身就是一张 RGBA 位图，脸在 (8,8) 起 8×8、帽子层在 (40,8) 起 8×8
 * （都是 64×64 皮肤坐标）。这里用 {@code glGetTexImage} 一次性把整张贴图回读到 CPU，
 * 建成一张 {@link Image} 后按子区域绘制：既避开了「收养 GL 贴图」的生命周期与 alpha 类型限制，
 * 也避开了「借位渲染 + 截取」那套（那套正是 {@code ItemIconCache} 走的路，对 3D 特化物品不成立）。</p>
 *
 * <p><b>线程与时机</b>：只能在渲染线程、且已有 GL 上下文时调用（与 {@link ItemIconCache} 同一约束）。
 * 同一张皮肤只回读一次，之后每帧只是贴图。</p>
 */
public final class PlayerFaceCache {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/player-face");

    /** 脸与帽子层在皮肤贴图上的相对位置与边长（原版布局：均为整张图的 1/8，脸在 1/8 处、帽子层在 5/8 处）。 */
    private static final float FACE_FRACTION = 8f / 64f;
    private static final float HAT_FRACTION = 40f / 64f;

    /** 单张皮肤最多允许的像素数：高清皮肤 128×128=16384、256×256=65536，取后者当上限。 */
    private static final int MAX_PIXELS = 65536;

    /** 缓存条目上限，超出后整体重建（皮肤贴图很小，这个上限只是防御性兜底）。 */
    private static final int CAPACITY = 256;

    /** 皮肤贴图路径 → 整张皮肤位图。 */
    private static final Map<Identifier, Image> SKINS = new HashMap<>();

    /** 回读失败过的皮肤路径：不再逐帧重试，避免刷屏与浪费。 */
    private static final Set<Identifier> FAILED = new HashSet<>();

    private PlayerFaceCache() {
    }

    /**
     * 画一个玩家头像（脸 + 帽子层叠画）。
     *
     * @param skin 玩家皮肤；{@code null} 返回 false
     * @param size 头像边长（GUI 逻辑像素）
     * @return true 表示已绘制；false 表示这一帧还画不了（贴图没读到或已标记失败）
     */
    public static boolean draw(Canvas canvas, PlayerSkin skin, float x, float y, float size) {
        if (canvas == null || skin == null || size <= 0f) return false;

        Image image = skinImage(skin);
        if (image == null) return false;

        float width = image.getWidth();
        float height = image.getHeight();
        // 脸区域的像素边长：按贴图尺寸换算（64×64 → 8、128×128 → 16），高清皮肤因此能出更多细节
        float part = Math.min(width, height) * FACE_FRACTION;
        if (part < 1f) return false;

        // DEFAULT = FilterMode.NEAREST（Skija 的 SamplingMode 只有 DEFAULT/LINEAR/两种三次采样），
        // 放大 8 倍时用最近邻才是像素质感；LINEAR 会把 8×8 糊成一团，看着像「像素太低」
        canvas.drawImageRect(image,
                Rect.makeXYWH(width * FACE_FRACTION, height * FACE_FRACTION, part, part),
                Rect.makeXYWH(x, y, size, size),
                SamplingMode.DEFAULT, null, true);

        // 帽子层：多数皮肤是透明的，只有戴帽/发型覆盖层时才有内容；有就叠在脸上
        canvas.drawImageRect(image,
                Rect.makeXYWH(width * HAT_FRACTION, height * FACE_FRACTION, part, part),
                Rect.makeXYWH(x, y, size, size),
                SamplingMode.DEFAULT, null, true);
        return true;
    }

    /** 释放全部贴图；资源包重载后与后端销毁时调用。 */
    public static void clear() {
        for (Image image : SKINS.values()) image.close();
        SKINS.clear();
        FAILED.clear();
    }

    // ── 取皮肤位图 ──

    /** 取（或首次回读）某张皮肤的位图；回读失败会记入 FAILED 并不再重试。 */
    private static Image skinImage(PlayerSkin skin) {
        Identifier path = skin.body().texturePath();
        if (path == null) return null;

        Image cached = SKINS.get(path);
        if (cached != null) return cached;
        if (FAILED.contains(path)) return null;

        Image created = readSkin(path);
        if (created == null) {
            FAILED.add(path);
            return null;
        }
        if (SKINS.size() >= CAPACITY) clear();
        SKINS.put(path, created);
        return created;
    }

    /**
     * 回读一张皮肤贴图。
     *
     * <p>贴图行序即上传时的行序（原版按 {@code NativeImage} 原样上传，不做上下翻转），
     * 因此回读结果的第 0 行就是图像顶行，脸区域直接取 (8,8) 起 8×8，不需要翻转。</p>
     */
    private static Image readSkin(Identifier path) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return null;

        AbstractTexture texture;
        try {
            texture = client.getTextureManager().getTexture(path);
        } catch (RuntimeException error) {
            return null;
        }
        if (texture == null) return null;

        GpuTexture gpu = texture.getTexture();
        if (!(gpu instanceof GlTexture glTexture)) return null;

        int width = gpu.getWidth(0);
        int height = gpu.getHeight(0);
        if (width <= 0 || height <= 0 || width * height > MAX_PIXELS) return null;

        byte[] pixels = readPixels(glTexture.glId(), width, height);
        if (pixels == null) return null;

        try {
            return Image.makeRasterFromBytes(
                    new ImageInfo(width, height, ColorType.RGBA_8888, ColorAlphaType.PREMUL, ColorSpace.getSRGB()),
                    premultiply(pixels), width * 4);
        } catch (RuntimeException error) {
            LOGGER.warn("玩家头像贴图构建失败：{}", path, error);
            return null;
        }
    }

    /**
     * 直通 alpha → 预乘 alpha（原地）。
     *
     * <p>皮肤贴图在 GL 里是直通 alpha（PNG 原值，上传不做预乘）；Skija 侧统一按
     * {@link ColorAlphaType#PREMUL} 建图（与 {@link ItemIconCache} 同一口径，那是本项目里
     * 唯一已被验证能正确画出透明像素的建图方式）。不做预乘而直接按 {@code UNPREMUL} 建图，
     * 透明区在本机会被画成黑块（用户 2026-09-16 反馈的「首页头像黑底」）。</p>
     */
    private static byte[] premultiply(byte[] rgba) {
        for (int i = 0; i < rgba.length; i += 4) {
            int alpha = rgba[i + 3] & 0xFF;
            if (alpha == 255) continue;
            if (alpha == 0) {
                rgba[i] = 0;
                rgba[i + 1] = 0;
                rgba[i + 2] = 0;
                continue;
            }
            rgba[i] = (byte) (((rgba[i] & 0xFF) * alpha + 127) / 255);
            rgba[i + 1] = (byte) (((rgba[i + 1] & 0xFF) * alpha + 127) / 255);
            rgba[i + 2] = (byte) (((rgba[i + 2] & 0xFF) * alpha + 127) / 255);
        }
        return rgba;
    }

    /**
     * 把一张 GL 贴图的 0 级画面回读成「自上而下」的 RGBA 字节；失败返回 null。
     *
     * <p><b>像素打包状态必须整体隔离</b>（与 {@link ItemIconCache#readRegion} 同一口径，
     * 取证见复盘 149、150）：{@code glGetTexImage} 往这块 ByteBuffer 里写多少、按什么行距写，
     * 由上下文级的 PBO 绑定与 {@code GL_PACK_*} 决定。别处（Skija 自身的读回、截图类模组、
     * Iris / Sodium / Voxy）一旦留下非 0 的 {@code PACK_ROW_LENGTH} 或还绑着 PBO，驱动就会按错误的
     * 行距往缓冲区<b>外</b>写 —— 那台 NVIDIA 591.86 机器上的表现是 nvoglv64.dll 写越界、
     * 整个客户端原生闪退。因此这里同样「保存 → 清零 → 还原」。</p>
     */
    private static byte[] readPixels(int textureId, int width, int height) {
        int[] previous = new int[1];
        int[] savedPackBuffer = new int[1];
        int[] savedPackRowLength = new int[1];
        int[] savedPackAlignment = new int[1];
        int[] savedPackSkipPixels = new int[1];
        int[] savedPackSkipRows = new int[1];
        glGetIntegerv(GL_TEXTURE_BINDING_2D, previous);
        glGetIntegerv(GL_PIXEL_PACK_BUFFER_BINDING, savedPackBuffer);
        glGetIntegerv(GL_PACK_ROW_LENGTH, savedPackRowLength);
        glGetIntegerv(GL_PACK_ALIGNMENT, savedPackAlignment);
        glGetIntegerv(GL_PACK_SKIP_PIXELS, savedPackSkipPixels);
        glGetIntegerv(GL_PACK_SKIP_ROWS, savedPackSkipRows);

        glBindBuffer(GL_PIXEL_PACK_BUFFER, 0);
        glPixelStorei(GL_PACK_ROW_LENGTH, 0);
        glPixelStorei(GL_PACK_ALIGNMENT, 1);
        glPixelStorei(GL_PACK_SKIP_PIXELS, 0);
        glPixelStorei(GL_PACK_SKIP_ROWS, 0);
        try {
            // 解绑 PBO 是这次回读的前提，调用前再核一次：若此刻仍绑着非 0 的 PBO，驱动会把 buffer
            // 指针当成该缓冲的字节偏移量，写到与我们毫不相干的地址去。宁可这次不取头像，也不带着
            // 这个前提进原生调用。
            int[] packBuffer = new int[1];
            glGetIntegerv(GL_PIXEL_PACK_BUFFER_BINDING, packBuffer);
            if (packBuffer[0] != 0) return null;

            glBindTexture(GL_TEXTURE_2D, textureId);
            // 末尾多留一行 + 64 字节：万一驱动在行距/对齐上仍多写一点，落点还在我们自己的分配里，
            // 而不是踩到未映射页把整个进程带走。实际只取前 width × height × 4 字节，行为不变。
            ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4 + width * 4 + 64);
            glGetTexImage(GL_TEXTURE_2D, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            byte[] out = new byte[width * height * 4];
            buffer.get(out);
            return out;
        } catch (RuntimeException error) {
            return null;
        } finally {
            // 必须还原绑定：调用方（原版渲染管线）随时可能直接用当前绑定的贴图
            glBindTexture(GL_TEXTURE_2D, previous[0]);
            glPixelStorei(GL_PACK_ROW_LENGTH, savedPackRowLength[0]);
            glPixelStorei(GL_PACK_ALIGNMENT, savedPackAlignment[0]);
            glPixelStorei(GL_PACK_SKIP_PIXELS, savedPackSkipPixels[0]);
            glPixelStorei(GL_PACK_SKIP_ROWS, savedPackSkipRows[0]);
            glBindBuffer(GL_PIXEL_PACK_BUFFER, savedPackBuffer[0]);
        }
    }
}
