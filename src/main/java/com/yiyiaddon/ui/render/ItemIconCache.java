package com.yiyiaddon.ui.render;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.impl.Library;
import io.github.humbleui.types.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.joml.Matrix3x2fStack;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.lwjgl.opengl.GL45.*;

/**
 * 物品图标缓存：把 {@link ItemStack} 渲染成 Skija 贴图并按物品缓存。
 *
 * <p><b>为什么需要它</b>：26.1.2 的界面是「抽帧」模型，物品只能通过
 * {@link GuiGraphicsExtractor#item} 记录绘制指令、由原版管线画进主 Framebuffer；
 * 而本项目界面是 Skija 即绘、在帧末直接画主 Framebuffer，两者无法混用。因此这里走
 * 「借位渲染 + 截取」：</p>
 *
 * <ol>
 *   <li><b>抽帧阶段</b>（{@link #renderPending}）：给屏幕中央两行隐藏格子先记录不透明底色
 *       （上行纯黑、下行纯白），再把本帧待加载的物品分别画到两行上。屏幕中央必定被面板覆盖，
 *       且截取与覆盖发生在同一帧内，因此玩家看不到这一过程。</li>
 *   <li><b>Skija 阶段</b>（{@link #capturePending}）：把两行格子各回读一次，用「黑白两版之差」
 *       反解出物品真实 alpha，自建预乘位图，按「物品 id + {@code ITEM_MODEL} 组件」缓存。</li>
 *   <li><b>绘制</b>（{@link #draw}）：后续帧直接贴图；未命中缓存的物品当帧入队、暂不绘制，
 *       下一帧起可用。</li>
 * </ol>
 *
 * <p>缓存的贴图尺寸固定为 {@link #ICON_SIZE} 逻辑像素，缩放由绘制方决定，因此同一物品只需
 * 一份贴图。</p>
 */
public final class ItemIconCache {

    private static final ItemIconCache INSTANCE = new ItemIconCache();

    /** 缓存与截取的图标边长（GUI 逻辑像素）。 */
    public static final int ICON_SIZE = 32;
    /** 单帧最多加载的图标数。 */
    private static final int BATCH = 12;
    /** 隐藏格之间的间距。 */
    private static final float SLOT_GAP = 4f;
    /** 渲染放大倍数：原版物品图标为 16×16 逻辑像素，放大到 32。 */
    private static final float SCALE = 2f;
    /** 缓存条目上限，超出后整体重建，避免贴图无限增长。 */
    private static final int CAPACITY = 1024;
    /** 隐藏格子的行数：第 0 行铺纯黑底、第 1 行铺纯白底，两版相减反解真实 alpha。 */
    private static final int ROWS = 2;
    /** 两行之间留出的空隙，正好让开屏幕正中 15×15 的准星。 */
    private static final float ROW_GAP = 16f;

    private final Map<String, Image> icons = new HashMap<>();
    private final Set<String> loading = new HashSet<>();
    private final ArrayDeque<Request> pending = new ArrayDeque<>();
    private final List<Request> rendered = new ArrayList<>();

    private boolean nativeLoaded;
    /** 实体 → 刷怪蛋映射；null 表示尚未构建。 */
    private Map<EntityType<?>, Item> spawnEggByEntity;

    /** 本帧备份下来的格子区域画面；由 {@link #paintBackdrop} 画回后立即释放。 */
    private Image backdropImage;
    /** 备份区域在 GUI 逻辑坐标下的位置与尺寸。 */
    private float[] backdropGuiBox;

    private ItemIconCache() {
    }

    public static ItemIconCache getInstance() {
        return INSTANCE;
    }

    // ── 抽帧阶段：给隐藏格子铺底并把待加载图标画进去 ──

    /**
     * 在 {@code Screen#extractRenderState} 里调用，把本帧待加载的物品画到隐藏格子。
     *
     * <p><b>底色为什么是「记录绘制指令」而不是擦主帧缓冲：</b>界面背景（原版那层压暗渐变）同样在
     * 抽帧阶段记录、且排在 {@code extractRenderState} 之前，真正落地比本方法早。在世界画完后用
     * {@code glClear} 擦出来的透明底会被这层背景重新盖成半透明黑，截出来就是「图标带黑底」。
     * 因此这里改成给两行格子先记录两次不透明填充（上行纯黑、下行纯白）：它们的落地顺序必定晚于
     * 界面背景、早于物品，底色完全可控，也顺带摆脱了「主帧缓冲能不能写 alpha」这个不可控项。</p>
     *
     * @param graphics 抽帧绘图上下文
     */
    public void renderPending(GuiGraphicsExtractor graphics) {
        rendered.clear();
        if (graphics == null || pending.isEmpty()) return;
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.getWindow() == null) {
            pending.clear();
            return;
        }

        int count = Math.min(BATCH, pending.size());
        List<Request> batch = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            batch.add(pending.poll());
        }

        // 先备份格子要占的整块矩形：格子总宽最宽 428 逻辑像素（12 格 × 36 − 4），比面板还宽，
        // 两侧会露在面板外；格子里的纯黑/纯白底与放大图标也会透过半透明玻璃。备份 + 帧末写回
        // 让这段过程对画面完全无痕（详见 saveBackdrop 注释）。只备份本帧真的要用到的那几格。
        saveBackdrop(client, count);

        // 底色用未缩放的 GUI 坐标记录：不管抽帧管线是否对 fill 应用姿态矩阵，两者都落在同一个矩形上
        for (int row = 0; row < ROWS; row++) {
            int color = row == 0 ? 0xFF000000 : 0xFFFFFFFF;
            int top = Math.round(slotY(row, client));
            for (int slot = 0; slot < count; slot++) {
                int left = Math.round(slotX(slot, client));
                graphics.fill(left, top, left + ICON_SIZE, top + ICON_SIZE, color);
            }
        }

        Matrix3x2fStack pose = graphics.pose();
        pose.pushMatrix();
        pose.scale(SCALE, SCALE);
        try {
            for (int slot = 0; slot < count; slot++) {
                Request request = batch.get(slot);
                request.slot = slot;
                for (int row = 0; row < ROWS; row++) {
                    graphics.item(request.stack, (int) (slotX(slot, client) / SCALE),
                            (int) (slotY(row, client) / SCALE));
                }
                rendered.add(request);
            }
        } finally {
            pose.popMatrix();
        }
    }

    // ── Skija 阶段：回读隐藏格子并入库 ──

    /**
     * 在本帧 Skija 绘制之前调用，把隐藏格子截取成贴图并写入缓存。
     * 必须在面板绘制之前执行，截取完成后隐藏格子即被面板覆盖。
     */
    public void capturePending() {
        if (rendered.isEmpty()) return;
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.getWindow() == null) {
            rendered.clear();
            return;
        }
        for (Request request : rendered) {
            Image image = captureIcon(request.slot, client);
            if (image != null) {
                if (icons.size() >= CAPACITY) clear();
                Image previous = icons.put(request.key, image);
                if (previous != null) previous.close();
            } else {
                loading.remove(request.key);
            }
        }
        rendered.clear();
        // 备份的画面不在这里写回：写回需要一个 Skija 画布，统一交给 SkiaGlBackend#begin
        // 在开始画主帧缓冲时调用 paintBackdrop（那个时机正好在面板绘制之前）。
    }

    // ── 绘制 ──

    /**
     * 绘制物品图标。
     *
     * @return true 表示已绘制；false 表示该物品尚未缓存（本次已入队，下一帧起可绘制）
     */
    public boolean draw(Canvas canvas, ItemStack stack, float x, float y, float size) {
        if (canvas == null || stack == null || stack.isEmpty()) return false;
        String key = keyOf(stack);
        Image image = icons.get(key);
        if (image == null) {
            request(stack, key);
            return false;
        }
        float width = image.getWidth();
        float height = image.getHeight();
        canvas.drawImageRect(image,
                Rect.makeXYWH(0f, 0f, width, height),
                Rect.makeXYWH(x, y, size, size),
                SamplingMode.LINEAR, null, true);
        return true;
    }

    /** 按物品 id 查询是否已缓存。 */
    public boolean isReady(ItemStack stack) {
        return stack != null && !stack.isEmpty() && icons.containsKey(keyOf(stack));
    }

    // ── 方块与实体：都是转成 ItemStack 后走同一条链路 ──

    /**
     * 绘制方块图标。
     *
     * <p>方块以物品形式渲染；没有物品形式的方块（水、火、活塞头等）返回 false。</p>
     */
    public boolean drawBlock(Canvas canvas, Block block, float x, float y, float size) {
        if (block == null) return false;
        Item item = block.asItem();
        if (item == null || item == Items.AIR) return false;
        return draw(canvas, item.getDefaultInstance(), x, y, size);
    }

    /**
     * 绘制实体图标。
     *
     * <p>实体以刷怪蛋物品渲染：遍历物品注册表，取带 {@code ENTITY_DATA} 组件且实体类型匹配的
     * 物品。没有对应刷怪蛋的实体（玩家、投射物、掉落物等）返回 false。</p>
     */
    public boolean drawEntity(Canvas canvas, EntityType<?> type, float x, float y, float size) {
        if (type == null) return false;
        Item egg = spawnEggByEntity().get(type);
        if (egg == null) return false;
        return draw(canvas, egg.getDefaultInstance(), x, y, size);
    }

    /**
     * 实体 → 刷怪蛋物品的映射，首次使用时构建一次。
     *
     * <p>必须在运行期构建：注册表绑定完成前读取物品组件会抛
     * {@code Components not bound yet}。</p>
     */
    private Map<EntityType<?>, Item> spawnEggByEntity() {
        if (spawnEggByEntity != null) return spawnEggByEntity;
        Map<EntityType<?>, Item> map = new HashMap<>();
        for (Item item : BuiltInRegistries.ITEM) {
            var data = item.components().get(DataComponents.ENTITY_DATA);
            if (data == null || data.type() == null) continue;
            map.putIfAbsent(data.type(), item);
        }
        spawnEggByEntity = map;
        return map;
    }

    /** 清空缓存并释放贴图；资源包重载后应调用。 */
    public void clear() {
        for (Image image : icons.values()) image.close();
        icons.clear();
        loading.clear();
        pending.clear();
        rendered.clear();
        releaseBackdrop();
    }

    /** 释放全部原生资源；渲染后端销毁时调用。 */
    public void close() {
        clear();
        // 上下文由 SkiaGlBackend 全项目共享，这里不关闭（关了会让其它界面与已采集贴图一起失效）
    }

    // ── 内部 ──

    private void request(ItemStack stack, String key) {
        if (loading.contains(key) || icons.containsKey(key)) return;
        loading.add(key);
        pending.add(new Request(stack.copy(), key));
    }

    /**
     * 缓存键：注册表物品 id + {@code ITEM_MODEL} 组件 + 图标边长。
     *
     * <p><b>必须带上 {@code ITEM_MODEL}：</b>资源包的自定义物品没有注册表条目，预览时统一用
     * {@code Items.PAPER} 承载并靠 {@code ITEM_MODEL} 指定真实模型（见
     * {@code StardewPreview}），只按注册表 id 取键会让所有自定义物品挤成同一个键，
     * 于是除第一件外永远命中不到自己的图标。</p>
     */
    private static String keyOf(ItemStack stack) {
        String base = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        Identifier model = stack.get(DataComponents.ITEM_MODEL);
        return (model == null ? base : base + "#" + model) + "|" + ICON_SIZE;
    }

    /** 第 {@code index} 个隐藏格子的 X：整行居中，取整以免与回读区域出现半像素错位。 */
    private static float slotX(int index, Minecraft client) {
        float total = BATCH * (ICON_SIZE + SLOT_GAP) - SLOT_GAP;
        return Math.round(guiWidth(client) / 2f - total / 2f) + index * (ICON_SIZE + SLOT_GAP);
    }

    /** 第 {@code row} 行隐藏格子的 Y：两行以屏幕中心上下对称，中间留 {@link #ROW_GAP} 让开准星。 */
    private static float slotY(int row, Minecraft client) {
        float blockTop = Math.round(guiHeight(client) / 2f - (ROWS * ICON_SIZE + ROW_GAP) / 2f);
        return blockTop + row * (ICON_SIZE + ROW_GAP);
    }

    private static float guiWidth(Minecraft client) {
        double scale = client.getWindow().getGuiScale();
        return scale <= 0 ? client.getWindow().getWidth() : (float) (client.getWindow().getWidth() / scale);
    }

    private static float guiHeight(Minecraft client) {
        double scale = client.getWindow().getGuiScale();
        return scale <= 0 ? client.getWindow().getHeight() : (float) (client.getWindow().getHeight() / scale);
    }

    /** 隐藏格子在 Framebuffer 像素坐标下的区域：{@code [x, y（自下往上）, 宽, 高]}。 */
    private static int[] slotScissorBox(int row, int slot, Minecraft client) {
        double scale = client.getWindow().getGuiScale();
        int windowW = client.getWindow().getWidth();
        int windowH = client.getWindow().getHeight();
        if (scale <= 0) return new int[]{0, 0, 1, 1};

        float x = slotX(slot, client);
        float y = slotY(row, client);
        int left = Math.max(0, (int) Math.round(x * scale));
        int top = Math.max(0, (int) Math.round(y * scale));
        int right = Math.min(windowW, (int) Math.round((x + ICON_SIZE) * scale));
        int bottom = Math.min(windowH, (int) Math.round((y + ICON_SIZE) * scale));
        return new int[]{left, Math.max(0, windowH - bottom),
                Math.max(1, right - left), Math.max(1, bottom - top)};
    }

    /**
     * 把一个隐藏格子的两行各回读一次，反解出物品真实 alpha，自建预乘位图。
     *
     * <p><b>为什么必须两版：</b>物品是按 alpha 混合画到底色上的。黑底那版拿到的正是<b>预乘颜色</b>
     * {@code Cs·As}；白底那版在此基础上又多叠了 {@code (1-As)} 的白。两版逐通道相减恰好等于
     * {@code 1-As}，于是 alpha 能直接算出来——既不需要主帧缓冲可写 alpha，也不需要 Skija 尊重
     * 收养纹理的 alpha 类型（{@code adoptGLTextureFrom} 只接颜色类型、不接 alpha 类型，
     * 透明区会被当成不透明画成黑块）。</p>
     */
    private Image captureIcon(int slot, Minecraft client) {
        if (!ensureContext()) return null;
        byte[][] passes = new byte[ROWS][];
        int width = 1;
        int height = 1;
        for (int row = 0; row < ROWS; row++) {
            int[] box = slotScissorBox(row, slot, client);
            byte[] pixels = readRegion(box);
            if (pixels == null) return null;
            passes[row] = pixels;
            width = box[2];
            height = box[3];
        }

        byte[] dark = passes[0];
        byte[] light = passes[1];
        byte[] out = new byte[width * height * 4];
        for (int i = 0; i < out.length; i += 4) {
            int r = dark[i] & 0xFF;
            int g = dark[i + 1] & 0xFF;
            int b = dark[i + 2] & 0xFF;
            int spread = ((light[i] & 0xFF) - r) + ((light[i + 1] & 0xFF) - g) + ((light[i + 2] & 0xFF) - b);
            int a = 255 - Math.round(spread / 3f);
            if (a < 0) {
                a = 0;
            } else if (a > 255) {
                a = 255;
            }
            // 预乘格式要求「颜色 ≤ alpha」，相减取整可能让颜色多出 1，夹一下避免越界像素
            if (r > a) r = a;
            if (g > a) g = a;
            if (b > a) b = a;
            out[i] = (byte) r;
            out[i + 1] = (byte) g;
            out[i + 2] = (byte) b;
            out[i + 3] = (byte) a;
        }
        return Image.makeRasterFromBytes(
                new ImageInfo(width, height, ColorType.RGBA_8888, ColorAlphaType.PREMUL, ColorSpace.getSRGB()),
                out, width * 4);
    }

    /**
     * 回读主 Framebuffer 的一块区域，返回行序已翻成自上而下的 RGBA 字节。
     *
     * <p>经临时 GL 纹理 + FBO 用 {@code glBlitFramebuffer} 中转，而不是直接对主 Framebuffer 调
     * {@code glReadPixels}：主 Framebuffer 可能是多重采样的，多重采样缓冲上直接回读是非法操作。</p>
     *
     * @param box {@code [x, y（自下往上）, 宽, 高]}，Framebuffer 像素坐标
     * @return 像素字节；失败返回 null
     */
    private byte[] readRegion(int[] box) {
        int width = box[2];
        int height = box[3];
        int rowBytes = width * 4;

        int[] oldTexture = new int[1];
        int[] oldActiveTexture = new int[1];
        int[] oldSampler = new int[1];
        int[] oldReadFramebuffer = new int[1];
        int[] oldDrawFramebuffer = new int[1];
        int[] oldViewport = new int[4];
        int[] oldScissorBox = new int[4];
        boolean framebufferSrgb = glIsEnabled(GL_FRAMEBUFFER_SRGB);
        glGetIntegerv(GL_ACTIVE_TEXTURE, oldActiveTexture);
        glActiveTexture(GL_TEXTURE0);
        glGetIntegerv(GL_TEXTURE_BINDING_2D, oldTexture);
        glGetIntegerv(GL_SAMPLER_BINDING, oldSampler);
        glGetIntegerv(GL_READ_FRAMEBUFFER_BINDING, oldReadFramebuffer);
        glGetIntegerv(GL_DRAW_FRAMEBUFFER_BINDING, oldDrawFramebuffer);
        glGetIntegerv(GL_VIEWPORT, oldViewport);
        glGetIntegerv(GL_SCISSOR_BOX, oldScissorBox);

        int textureId = 0;
        int framebufferId = 0;
        try {
            textureId = glGenTextures();
            framebufferId = glGenFramebuffers();
            glBindTexture(GL_TEXTURE_2D, textureId);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0L);

            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebufferId);
            glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureId, 0);
            if (glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) return null;

            glBindFramebuffer(GL_READ_FRAMEBUFFER, SkiaGlBackend.mainFramebufferId());
            if (glCheckFramebufferStatus(GL_READ_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) return null;

            glDisable(GL_FRAMEBUFFER_SRGB);
            glBindSampler(0, 0);
            glBlitFramebuffer(
                    box[0], box[1], box[0] + width, box[1] + height,
                    0, 0, width, height,
                    GL_COLOR_BUFFER_BIT,
                    GL_NEAREST
            );

            glBindFramebuffer(GL_READ_FRAMEBUFFER, framebufferId);
            {
                // 必须用堆外内存而不是 MemoryStack：LWJGL 的栈每个线程只有 64 KB，而备份整块格子
                // 区域时这里要一次拿到 1 MB 以上，用 stack.malloc 会直接 OutOfMemoryError（曾崩过一次）。
                ByteBuffer pixels = BufferUtils.createByteBuffer(rowBytes * height);
                glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

                byte[] flipped = new byte[rowBytes * height];
                for (int row = 0; row < height; row++) {
                    pixels.position((height - 1 - row) * rowBytes);
                    pixels.get(flipped, row * rowBytes, rowBytes);
                }
                return flipped;
            }
        } catch (RuntimeException e) {
            return null;
        } finally {
            glDeleteFramebuffers(framebufferId);
            glDeleteTextures(textureId);

            glBindFramebuffer(GL_READ_FRAMEBUFFER, oldReadFramebuffer[0]);
            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, oldDrawFramebuffer[0]);
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

    // ── 隐藏格子矩形备份（让抽帧过程对画面无痕） ──

    /**
     * 在隐藏格子落地之前，把它占的整块矩形回读成一张位图备份。
     *
     * <p><b>为什么必须备份：</b>格子画在屏幕正中、总宽 428 逻辑像素（12 格 × 36 − 4），比面板还宽；
     * 格子里的纯黑/纯白底与放大到 32 逻辑像素的物品图标，一部分会露在面板之外的场景上，另一部分
     * 会透过半透明玻璃显形。没有待加载图标时格子根本不存在（看不到），可一旦滚动列表（每帧都有新
     * 图标入队）就会持续闪出放大的物品图标 —— 即用户 2026-09-16 反馈的「UI 滚动时图标贴图闪烁」。</p>
     *
     * <p>时机：本方法在抽帧阶段（{@code renderPending}）调用，此时格子还只是绘制指令、尚未落地，
     * 回读到的是干净的场景画面；写回（{@link #paintBackdrop}）发生在面板绘制之前，于是格子存在的
     * 整段时间都被画面自己的备份盖住，玩家完全看不到。</p>
     */
    private void saveBackdrop(Minecraft client, int slots) {
        releaseBackdrop();
        int[] box = backdropBox(client, slots);
        if (box[2] <= 2 || box[3] <= 2) return;

        byte[] pixels = readRegion(box);
        if (pixels == null) return;
        try {
            // 帧内容已是合成后的不透明画面，其 alpha 常为 0（不透明渲染的常见结果），
            // 因此必须按 OPAQUE 建图：按 PREMUL 建会被 alpha=0 抹成纯黑。
            backdropImage = Image.makeRasterFromBytes(
                    new ImageInfo(box[2], box[3], ColorType.RGBA_8888, ColorAlphaType.OPAQUE, ColorSpace.getSRGB()),
                    pixels, box[2] * 4);
        } catch (RuntimeException error) {
            backdropImage = null;
            return;
        }

        double scale = client.getWindow().getGuiScale();
        int windowH = client.getWindow().getHeight();
        backdropGuiBox = new float[]{
                (float) (box[0] / scale),
                (float) ((windowH - box[1] - box[3]) / scale),
                (float) (box[2] / scale),
                (float) (box[3] / scale)
        };
    }

    /**
     * 把备份的画面画回格子矩形。
     *
     * <p>由 {@code SkiaGlBackend#begin} 在开始绘制主帧缓冲时调用：那个时机正好在面板绘制之前，
     * 写回之后面板玻璃采样到的就是干净画面；写回到 MSAA 主帧缓冲只能靠绘制（{@code glBlitFramebuffer}
     * 不允许「单采样 → 多采样」），所以这里走 Skija 而不是 GL blit。</p>
     */
    public void paintBackdrop(Canvas canvas) {
        Image image = backdropImage;
        float[] box = backdropGuiBox;
        backdropImage = null;
        backdropGuiBox = null;
        if (image == null) return;
        try {
            if (canvas == null || box == null) return;
            canvas.drawImageRect(image,
                    Rect.makeXYWH(0f, 0f, image.getWidth(), image.getHeight()),
                    Rect.makeXYWH(box[0], box[1], box[2], box[3]),
                    SamplingMode.DEFAULT, null, true);
        } finally {
            image.close();
        }
    }

    /** 释放尚未写回的备份。 */
    private void releaseBackdrop() {
        if (backdropImage != null) {
            backdropImage.close();
            backdropImage = null;
        }
        backdropGuiBox = null;
    }

    /** 隐藏格子整体的 Framebuffer 像素区域：{@code [x, y（自下往上）, 宽, 高]}。 */
    private int[] backdropBox(Minecraft client, int slots) {
        double scale = client.getWindow().getGuiScale();
        int windowW = client.getWindow().getWidth();
        int windowH = client.getWindow().getHeight();
        if (scale <= 0) return new int[]{0, 0, 1, 1};

        float left = slotX(0, client);
        float top = slotY(0, client);
        float total = Math.max(1, slots) * (ICON_SIZE + SLOT_GAP) - SLOT_GAP;
        float blockHeight = ROWS * ICON_SIZE + ROW_GAP;

        int x0 = Math.max(0, (int) Math.round(left * scale));
        int y0 = Math.max(0, (int) Math.round(top * scale));
        int x1 = Math.min(windowW, (int) Math.round((left + total) * scale));
        int y1 = Math.min(windowH, (int) Math.round((top + blockHeight) * scale));
        return new int[]{x0, Math.max(0, windowH - y1), Math.max(1, x1 - x0), Math.max(1, y1 - y0)};
    }

    /**
     * 确保 Skija 原生库与 GL 上下文可用。
     *
     * <p>上下文取自 {@link SkiaGlBackend#sharedContext()}——必须与画面板用的是同一个，
     * 否则本类自建的贴图在别的上下文上画不出来（表现为图标永远不显示）。</p>
     */
    private boolean ensureContext() {
        if (!nativeLoaded) {
            Library.load();
            nativeLoaded = true;
        }
        return SkiaGlBackend.sharedContext() != null;
    }

    /** 本帧被渲染进隐藏格子的请求。 */
    private static final class Request {
        private final ItemStack stack;
        private final String key;
        private int slot;

        private Request(ItemStack stack, String key) {
            this.stack = stack;
            this.key = key;
        }
    }
}
