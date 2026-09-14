package com.yiyiaddon.ui.render;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.SurfaceOrigin;
import io.github.humbleui.skija.impl.Library;
import io.github.humbleui.types.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.joml.Matrix3x2fStack;

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
 *   <li><b>抽帧阶段</b>（{@link #renderPending}）：把本帧待加载的物品画到屏幕中心的隐藏格子里。
 *       屏幕中心必定被面板覆盖，且截取与覆盖发生在同一帧内，因此玩家看不到这一过程。</li>
 *   <li><b>Skija 阶段</b>（{@link #capturePending}）：从主 Framebuffer 把隐藏格子截取成
 *       {@link Image}，按物品 id 缓存。</li>
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

    private final Map<String, Image> icons = new HashMap<>();
    private final Set<String> loading = new HashSet<>();
    private final ArrayDeque<Request> pending = new ArrayDeque<>();
    private final List<Request> rendered = new ArrayList<>();

    private DirectContext context;
    private boolean nativeLoaded;
    /** 实体 → 刷怪蛋映射；null 表示尚未构建。 */
    private Map<EntityType<?>, Item> spawnEggByEntity;

    private ItemIconCache() {
    }

    public static ItemIconCache getInstance() {
        return INSTANCE;
    }

    // ── 抽帧阶段：把待加载图标画进隐藏格子 ──

    /**
     * 在 {@code Screen#extractRenderState} 里调用，把本帧待加载的物品画到隐藏格子。
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

        Matrix3x2fStack pose = graphics.pose();
        pose.pushMatrix();
        pose.scale(SCALE, SCALE);
        try {
            int slot = 0;
            while (slot < BATCH && !pending.isEmpty()) {
                Request request = pending.poll();
                graphics.item(request.stack, (int) (slotX(slot, client) / SCALE), (int) (slotY(client) / SCALE));
                request.slot = slot;
                rendered.add(request);
                slot++;
            }
        } finally {
            pose.popMatrix();
        }
    }

    // ── Skija 阶段：截取隐藏格子并入库 ──

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
            Image image = capture(slotX(request.slot, client), slotY(client), ICON_SIZE, ICON_SIZE);
            if (image != null) {
                if (icons.size() >= CAPACITY) clear();
                Image previous = icons.put(request.key, image);
                if (previous != null) previous.close();
            } else {
                loading.remove(request.key);
            }
        }
        rendered.clear();
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
    }

    /** 释放全部原生资源；渲染后端销毁时调用。 */
    public void close() {
        clear();
        if (context != null) {
            context.close();
            context = null;
        }
    }

    // ── 内部 ──

    private void request(ItemStack stack, String key) {
        if (loading.contains(key) || icons.containsKey(key)) return;
        loading.add(key);
        pending.add(new Request(stack.copy(), key));
    }

    private static String keyOf(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString() + "|" + ICON_SIZE;
    }

    private static float slotX(int index, Minecraft client) {
        float centerX = guiWidth(client) / 2f;
        float total = BATCH * (ICON_SIZE + SLOT_GAP) - SLOT_GAP;
        return centerX - total / 2f + index * (ICON_SIZE + SLOT_GAP);
    }

    private static float slotY(Minecraft client) {
        return guiHeight(client) / 2f;
    }

    private static float guiWidth(Minecraft client) {
        double scale = client.getWindow().getGuiScale();
        return scale <= 0 ? client.getWindow().getWidth() : (float) (client.getWindow().getWidth() / scale);
    }

    private static float guiHeight(Minecraft client) {
        double scale = client.getWindow().getGuiScale();
        return scale <= 0 ? client.getWindow().getHeight() : (float) (client.getWindow().getHeight() / scale);
    }

    /**
     * 把主 Framebuffer 的指定区域截取为 Skija 图像。
     *
     * <p>与 {@code SkiaBlurRenderer} 的截取同一套做法：临时建一个 GL 纹理 + FBO，
     * 用 {@code glBlitFramebuffer} 把区域拷进去，再用 {@link Image#adoptGLTextureFrom}
     * 交给 Skija；纹理所有权随之转移，无需手动删除。</p>
     */
    private Image capture(float x, float y, float width, float height) {
        Minecraft client = Minecraft.getInstance();
        if (!ensureContext() || client == null) return null;

        int windowW = client.getWindow().getWidth();
        int windowH = client.getWindow().getHeight();
        double scale = client.getWindow().getGuiScale();
        if (scale <= 0) return null;

        int left = Math.max(0, (int) Math.floor(x * scale));
        int top = Math.max(0, (int) Math.floor(y * scale));
        int right = Math.min(windowW, (int) Math.ceil((x + width) * scale));
        int bottom = Math.min(windowH, (int) Math.ceil((y + height) * scale));
        int copyW = Math.max(1, right - left);
        int copyH = Math.max(1, bottom - top);
        int sourceY = Math.max(0, windowH - bottom);

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

        int textureId = 0;
        int framebufferId = 0;
        Image image = null;
        boolean handedOff = false;
        try {
            textureId = glGenTextures();
            framebufferId = glGenFramebuffers();
            glBindTexture(GL_TEXTURE_2D, textureId);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, copyW, copyH, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0L);

            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebufferId);
            glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureId, 0);
            glDrawBuffer(GL_COLOR_ATTACHMENT0);
            if (glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) return null;

            glBindFramebuffer(GL_READ_FRAMEBUFFER, SkiaGlBackend.mainFramebufferId());
            if (glCheckFramebufferStatus(GL_READ_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) return null;

            glDisable(GL_FRAMEBUFFER_SRGB);
            glBindSampler(0, 0);
            glReadBuffer(GL_COLOR_ATTACHMENT0);
            glBlitFramebuffer(
                    left, sourceY, left + copyW, sourceY + copyH,
                    0, 0, copyW, copyH,
                    GL_COLOR_BUFFER_BIT,
                    GL_NEAREST
            );
            glFlush();

            image = Image.adoptGLTextureFrom(context, textureId, GL_TEXTURE_2D, copyW, copyH,
                    GL_RGBA8, SurfaceOrigin.BOTTOM_LEFT, ColorType.RGB_888X);
            handedOff = image != null;
            return image;
        } catch (RuntimeException e) {
            return null;
        } finally {
            glDeleteFramebuffers(framebufferId);
            if (!handedOff && textureId != 0) glDeleteTextures(textureId);

            glBindFramebuffer(GL_READ_FRAMEBUFFER, oldReadFramebuffer[0]);
            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, oldDrawFramebuffer[0]);
            restoreBuffer(true, oldReadFramebuffer[0], oldReadBuffer[0]);
            restoreBuffer(false, oldDrawFramebuffer[0], oldDrawBuffer[0]);
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

    private void restoreBuffer(boolean read, int framebufferId, int buffer) {
        if (buffer == GL_NONE) {
            if (read) glReadBuffer(GL_NONE);
            else glDrawBuffer(GL_NONE);
            return;
        }
        boolean defaultFramebuffer = framebufferId == 0;
        boolean valid = defaultFramebuffer ? isDefaultBuffer(buffer) : isColorAttachment(buffer);
        int restored = valid ? buffer : (defaultFramebuffer ? GL_BACK : GL_COLOR_ATTACHMENT0);
        if (read) glReadBuffer(restored);
        else glDrawBuffer(restored);
    }

    private static boolean isDefaultBuffer(int buffer) {
        return buffer == GL_FRONT || buffer == GL_BACK || buffer == GL_LEFT || buffer == GL_RIGHT
                || buffer == GL_FRONT_LEFT || buffer == GL_FRONT_RIGHT
                || buffer == GL_BACK_LEFT || buffer == GL_BACK_RIGHT;
    }

    private static boolean isColorAttachment(int buffer) {
        return buffer >= GL_COLOR_ATTACHMENT0 && buffer <= GL_COLOR_ATTACHMENT0 + 31;
    }

    private boolean ensureContext() {
        if (!nativeLoaded) {
            Library.load();
            nativeLoaded = true;
        }
        if (context == null) {
            context = DirectContext.makeGL();
        }
        return context != null;
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
