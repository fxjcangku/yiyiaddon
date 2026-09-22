package com.yiyiaddon.ui.render;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.EncodedImageFormat;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.impl.Library;
import io.github.humbleui.types.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.block.Block;
import org.joml.Matrix3x2fStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
 *   <li><b>抽帧阶段</b>（{@link #renderPending}）：在「面板玻璃盖得住的矩形」里排出一片隐藏格子，
 *       先给上下两块记录不透明底色（上块纯黑、下块纯白），再把本帧待加载的物品分别画到两块上。
 *       格子落点由面板尺寸反推（见 {@link #fit}），截取与覆盖发生在同一帧内，玩家看不到这一过程。</li>
 *   <li><b>Skija 阶段</b>（{@link #capturePending}）：把两个色块各回读一次，用「黑白两版之差」
 *       反解出物品真实 alpha，自建预乘位图，按「物品 id + {@code ITEM_MODEL} 组件」缓存。</li>
 *   <li><b>绘制</b>（{@link #draw}）：后续帧直接贴图；未命中缓存的物品当帧入队、暂不绘制，
 *       下一帧起可用。</li>
 * </ol>
 *
 * <p>缓存的贴图尺寸固定为 {@link #ICON_SIZE} 逻辑像素，缩放由绘制方决定，因此同一物品只需
 * 一份贴图。</p>
 *
 * <p><b>实体渲染图</b>（{@link #drawEntityModel}）走的是同一条链路，只是格子里的内容换成原版 GUI 的
 * 实体通道（{@code GuiGraphicsExtractor#entity}）：截取、反解 alpha、缓存、绘制全部复用，因此实体模型
 * 图标与物品图标是同一张缓存里的两类条目。注意 {@link #drawEntity} <b>不</b>是实体渲染图 ——
 * 它画的是刷怪蛋物品图标。</p>
 */
public final class ItemIconCache {

    private static final ItemIconCache INSTANCE = new ItemIconCache();

    /** 缓存与截取的图标边长（GUI 逻辑像素）。 */
    public static final int ICON_SIZE = 32;
    /** 单帧最多加载的图标数。 */
    private static final int BATCH = 12;
    /** 隐藏格之间的间距。 */
    private static final float SLOT_GAP = 4f;
    /** 单格占位（图标 + 间距）：横向步进与纵向步进共用，排版算宽度、行数都按它算。 */
    private static final float CELL = ICON_SIZE + SLOT_GAP;
    /** 渲染放大倍数：原版物品图标为 16×16 逻辑像素，放大到 32。 */
    private static final float SCALE = 2f;
    /**
     * 缓存条目上限。撞上上限时<b>只淘汰最旧的一批</b>（见 {@link #evictOldest}），不再整张清空。
     *
     * <p><b>1024 → 4096、并且不再 {@code clear()} 的原因</b>（用户 2026-09-22：「点击目标选择器分组
     * 的时候 选东西的时候 会闪，就是那些贴图还是物品图标的东西 一闪而过…就目标选择器会这样」）：
     * 目标选择器列的是<b>全物品 / 全方块注册表</b>（26.1.2 上约 1500 + 1050 条），加上各类选择器翻一遍，
     * 累计图标很容易越过 1024。旧写法一到上限就 {@code clear()} —— 整张缓存连同在途请求一起没了，
     * 屏幕上<b>所有</b>物品图标当场消失、再一张张慢慢补回来，看着就是「图标一闪而过」。
     * 全项目只有目标选择器有这么大的表，所以只有它会这样。</p>
     *
     * <p>4096 张 32×32 位图约 16 MB，装得下两张注册表全量图标；真撞上上限时淘汰的是最早入库的那批
     * （那些图标早滚出视野了），不会出现「整屏图标同时消失」。</p>
     */
    private static final int CAPACITY = 4096;
    /** 撞上上限时一次淘汰的条数：按插入顺序取最旧的一批（约 1/8，够腾出余量又不至于反复淘汰） */
    private static final int EVICT_BATCH = 512;
    /** 色块数：第 0 块铺纯黑底、第 1 块铺纯白底，两版相减反解真实 alpha（每块内部可以有多行，见 {@link Grid}）。 */
    private static final int ROWS = 2;
    /** 两个色块之间留出的空隙：它整体居中，因此正中永远是空的，屏幕正中 15×15 的准星不会被截进格子。 */
    private static final float ROW_GAP = 16f;
    /** 实体模型图标的缓存键前缀：实体模型与物品分开取键（物品键是注册表 id，不会以它开头）。 */
    private static final String MODEL_KEY_PREFIX = "entity:";
    /**
     * 实体模型占用的槽位：格子区的第 0 格（那一帧物品批次顺延一格、少取一个，见 {@link #renderPending}）。
     *
     * <p>为什么不另开一格：格子区能铺多大由面板能盖住多大决定（见 {@link #fit}），余量本来就要留着
     * 给「面板变窄」用；实体模型借用第 0 格不占额外面积，窄面板下也不会把最后一格挤出玻璃。</p>
     */
    private static final int MODEL_SLOT = 0;
    /**
     * 实体模型在格子里的取景系数：包围盒高度占格子边长的比例。
     *
     * <p>取自原版 {@code InventoryScreen.extractEntityInInventoryFollowsMouse} 的口径——那里用
     * {@code size=30} 画 70 逻辑像素高的框（玩家 1.8 格高占框约 77%），这里按包围盒高度归一，
     * 使得任意体型的生物在 32 像素格子里都占满约八成，不会出现「只看得见一条腿」。</p>
     */
    private static final float MODEL_FILL = 0.77f;
    /** 取景用的最小包围盒高度：防止体型极扁的实体（如岩浆怪）把缩放推到离谱的倍数。 */
    private static final float MODEL_MIN_BLOCKS = 0.5f;
    /** 实体拍照时的抬升量：与 {@code InventoryScreen} 一致（1/16 格），让模型在格子里居中。 */
    private static final float MODEL_Y_OFFSET = 0.0625f;

    /**
     * 已缓存的图标。用 {@link LinkedHashMap} 而不是 HashMap：淘汰要按「入库先后」取最旧的一批，
     * 插入顺序正好就是入库顺序（同一键重新入库会排到队尾，视作最新，符合预期）。
     */
    private final Map<String, Image> icons = new LinkedHashMap<>();
    private final Set<String> loading = new HashSet<>();
    private final ArrayDeque<Request> pending = new ArrayDeque<>();
    private final List<Request> rendered = new ArrayList<>();
    /**
     * 实体模型请求队列：每帧只取一个。
     *
     * <p><b>为什么必须一帧一个：</b>原版 PIP（{@code GuiEntityRenderer}）每种渲染器只有<b>一张</b>
     * 共用纹理，同帧多个实体状态会依次清屏重画，最终所有 blit 拿到的都是<b>最后一个</b>实体的画面。
     * 两块（黑白底）画的是同一个实体，互相覆盖也不会画错；不同实体则必须排队一帧一个。</p>
     */
    private final ArrayDeque<Request> pendingModels = new ArrayDeque<>();
    /** 模型路径确实不可用的实体：没有渲染器 / 合成不出实体 / 渲出来整格全透明（如标记实体的空渲染器）。 */
    private final Set<EntityType<?>> modelUnsupported = new HashSet<>();

    private boolean nativeLoaded;
    /** 实体 → 刷怪蛋映射；null 表示尚未构建。 */
    private Map<EntityType<?>, Item> spawnEggByEntity;

    /** 本帧备份下来的格子区域画面；由 {@link #paintBackdrop} 画回后立即释放。 */
    private Image backdropImage;
    /** 备份区域在 GUI 逻辑坐标下的位置与尺寸。 */
    private float[] backdropGuiBox;
    /** 本帧格子区的排布；只在 {@link #renderPending} 与同一帧的 {@link #capturePending} 之间有效。 */
    private Grid frameGrid;

    private ItemIconCache() {
    }

    public static ItemIconCache getInstance() {
        return INSTANCE;
    }

    // ── 抽帧阶段：给隐藏格子铺底并把待加载图标画进去 ──

    /**
     * 在 {@code Screen#extractRenderState} 里调用，把本帧待加载的图标（物品与实体模型）画到隐藏格子。
     *
     * <p><b>底色为什么是「记录绘制指令」而不是擦主帧缓冲：</b>界面背景（原版那层压暗渐变）同样在
     * 抽帧阶段记录、且排在 {@code extractRenderState} 之前，真正落地比本方法早。在世界画完后用
     * {@code glClear} 擦出来的透明底会被这层背景重新盖成半透明黑，截出来就是「图标带黑底」。
     * 因此这里改成给格子先记录两次不透明填充（黑底块、白底块）：它们的落地顺序必定晚于
     * 界面背景、早于物品，底色完全可控，也顺带摆脱了「主帧缓冲能不能写 alpha」这个不可控项。</p>
     *
     * @param graphics 抽帧绘图上下文
     * @param cover    本帧面板玻璃完全不透明的内接矩形（GUI 逻辑坐标，见 {@code SkiaScreen#coverRegion}）：
     *                 整片格子都排在它里面。{@code null} 表示界面没给出可盖区域，退回整屏可用
     */
    public void renderPending(GuiGraphicsExtractor graphics, float[] cover) {
        rendered.clear();
        frameGrid = null;
        if (graphics == null || (pending.isEmpty() && pendingModels.isEmpty())) {
            // 本帧没有格子要落地：上一帧的备份完成使命（写回是「可重放」的，不能被它拖到下一帧）
            releaseBackdrop();
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.getWindow() == null) {
            pending.clear();
            pendingModels.clear();
            return;
        }

        // 实体模型每帧只取一个（原因见 pendingModels）
        Request model = pendingModels.poll();
        if (model != null && client.level == null) {
            // 还没进世界：合成实体要一张地图。放回队首下一帧再试，不记为不可用
            pendingModels.addFirst(model);
            model = null;
        }

        // 实体模型占用格子区的第 0 格，因此<b>取批之前</b>就要按让位算好上限：先按 BATCH 取满再回头减一，
        // 多出来的那个请求已被 poll 出队、既不入 rendered 也回不了队，而它的 key 早在 request 时就进了
        // loading —— 那张图标此后一辈子都渲不出来（并白占一个 loading 名额）。
        if (model != null) model.slot = MODEL_SLOT;
        int count = Math.min(model == null ? BATCH : BATCH - 1, pending.size());
        int slots = count + (model == null ? 0 : 1);
        if (slots <= 0) {
            releaseBackdrop();
            return;
        }

        Grid grid = fit(cover, slots, client);
        if (grid == null) {
            // 面板小到连一格都盖不住：这一帧什么都不截，请求原样留在队里等下帧（面板变大即恢复）
            if (model != null) pendingModels.addFirst(model);
            return;
        }
        // 面板可盖高度也可能排不下这么多行：本帧只截装得下的那些，剩下的留在队里下一帧继续
        count = Math.min(count, grid.capacity() - (model == null ? 0 : 1));
        int used = count + (model == null ? 0 : 1);
        frameGrid = grid;

        List<Request> batch = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            batch.add(pending.poll());
        }
        int firstItemSlot = model == null ? 0 : 1;

        // 底色用未缩放的 GUI 坐标记录：不管抽帧管线是否对 fill 应用姿态矩阵，两者都落在同一个矩形上
        for (int row = 0; row < ROWS; row++) {
            int color = row == 0 ? 0xFF000000 : 0xFFFFFFFF;
            for (int slot = 0; slot < used; slot++) {
                int left = Math.round(grid.x(slot));
                int top = Math.round(grid.y(row, slot));
                graphics.fill(left, top, left + ICON_SIZE, top + ICON_SIZE, color);
            }
        }

        Matrix3x2fStack pose = graphics.pose();
        pose.pushMatrix();
        pose.scale(SCALE, SCALE);
        try {
            for (int i = 0; i < count; i++) {
                Request request = batch.get(i);
                int slot = firstItemSlot + i;
                request.slot = slot;
                for (int row = 0; row < ROWS; row++) {
                    graphics.item(request.stack, (int) (grid.x(slot) / SCALE),
                            (int) (grid.y(row, slot) / SCALE));
                }
                rendered.add(request);
            }
        } finally {
            pose.popMatrix();
        }

        if (model != null) {
            if (renderModel(graphics, model, client, grid)) {
                rendered.add(model);
            } else {
                // 渲不出来（没有渲染器 / 合成不出实体）：永久记为不可用，让调用方退回静态图标
                failModel(model);
            }
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
        Grid grid = frameGrid;
        if (client == null || client.getWindow() == null || grid == null) {
            rendered.clear();
            return;
        }
        for (Request request : rendered) {
            Capture capture = captureIcon(request.slot, client, grid);
            if (capture == null) {
                loading.remove(request.key);
                continue;
            }
            if (request.type != null && !capture.visible()) {
                // 实体模型整格全透明 = 这个实体本来就没画东西（如标记实体的空渲染器）：
                // 记入不可用并丢弃这张空图，否则列表里会留一个看不见的空图标
                capture.image().close();
                failModel(request);
                continue;
            }
            if (icons.size() >= CAPACITY) evictOldest();
            Image previous = icons.put(request.key, capture.image());
            if (previous != null) previous.close();
        }
        rendered.clear();
        // 备份的画面不在这里写回：写回需要一个 Skija 画布，交给界面绘制路径
        // （SkiaGlBackend#beginScreenFrame）与 SkiaScreen#renderSkiaFrame 兜底那一步。
    }

    /**
     * 淘汰最旧的一批图标（按入库先后），而不是清空整张缓存。
     *
     * <p>与 {@link #clear()} 的两点区别，都是「别把还看得见的东西一起删掉」：</p>
     * <ul>
     *   <li>只删最早入库的 {@link #EVICT_BATCH} 张 —— 它们要么早滚出视野、要么在别的界面里，
     *       重新需要时按需再截一次即可，不会有「整屏图标同时消失」；</li>
     *   <li>{@code modelUnsupported}（渲不出来的实体黑名单）与在途请求<b>保留</b>：它们跟容量无关，
     *       清掉只会让那些实体被反复重试、排在队里的图标白等一帧。</li>
     * </ul>
     */
    private void evictOldest() {
        Iterator<Map.Entry<String, Image>> it = icons.entrySet().iterator();
        for (int i = 0; i < EVICT_BATCH && it.hasNext(); i++) {
            Map.Entry<String, Image> entry = it.next();
            entry.getValue().close();
            it.remove();
        }
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
        drawScaled(canvas, image, x, y, size);
        return true;
    }

    /** 按物品 id 查询是否已缓存。 */
    public boolean isReady(ItemStack stack) {
        return stack != null && !stack.isEmpty() && icons.containsKey(keyOf(stack));
    }

    /**
     * 预热物品图标：把请求排队，交给抽帧管线在随后的帧里渲染入库（调用方不需要 Canvas）。
     *
     * <p><b>为什么需要它</b>（用户 2026-09-18：「点击这些分组的时候 有几率那个贴图会在闪一下
     * 所有的选择器都要防止」）：{@link #draw} 在图标还没入库时返回 false，那一两帧里行上是空的图标位，
     * 图标随后才补上，看起来就是「闪一下」。选择器的分组默认收起，展开那一刻才第一次请求该组的图标，
     * 于是每次点开分组都可能闪。开窗时先把整表排队，展开时图标已经在缓存里。</p>
     *
     * <p>幂等：已在缓存或已在途的键由 {@link #request} 直接跳过，重复调用不会排出多份请求。</p>
     */
    public void prefetch(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;
        request(stack, keyOf(stack));
    }

    /**
     * 预热实体图标：有刷怪蛋就热刷怪蛋那一版（同 {@link #drawEntity}），否则热实体模型那一版
     * （同 {@link #drawEntityModel}）。
     *
     * <p>用途与 {@link #prefetch(ItemStack)} 相同。模型版一帧只渲一个（原因见 {@link #pendingModels}），
     * 排队多时会分摊到随后若干帧 —— 但只要在开窗时就排上，展开分组时通常已经入库。</p>
     */
    public void prefetchEntity(EntityType<?> type) {
        if (type == null || modelUnsupported.contains(type)) return;
        Item egg = spawnEggByEntity().get(type);
        if (egg != null) {
            prefetch(egg.getDefaultInstance());
            return;
        }
        Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        if (id == null) return;
        requestModel(type, MODEL_KEY_PREFIX + id + "|" + ICON_SIZE);
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

    /** 该实体是否有刷怪蛋（有则 {@link #drawEntity} 能画出图标；刷怪蛋贴图本身就是按生物形象绘制的）。 */
    public boolean hasSpawnEgg(EntityType<?> type) {
        return type != null && spawnEggByEntity().containsKey(type);
    }

    /** 该实体的模型渲染图确实不可用（没有渲染器 / 合成不出实体 / 渲出来是空的）；调用方据此退回静态图标。 */
    public boolean isEntityModelUnsupported(EntityType<?> type) {
        return type != null && modelUnsupported.contains(type);
    }

    /**
     * 绘制实体的<b>真实渲染图</b>（原版实体模型 + 贴图本身）。
     *
     * <p><b>与 {@link #drawEntity} 的区别：</b>{@code drawEntity} 画的是<b>刷怪蛋物品</b>（遍历物品注册表
     * 找带 {@code ENTITY_DATA} 的蛋），没有刷怪蛋的实体它直接返回 false —— 巨人、幻术师这类原版不留刷怪蛋的
     * 生物因此拿不到生物形象。本方法借原版 GUI 的实体渲染通道（{@code GuiGraphicsExtractor#entity}，
     * 物品栏玩家预览走的就是它）把实体模型画进隐藏格子，再沿用同一条黑白两版截取链路，得到的就是实体自己的样子。</p>
     *
     * <p><b>不可用的情况</b>（返回 false 且 {@link #isEntityModelUnsupported} 变为 true）：合成不出实体实例、
     * 实体没有注册渲染器（如玩家、假人这类只有专用渲染器的实体）、或渲染结果整格全透明（如标记实体的空渲染器）。
     * 这类实体由调用方退回物品图标；渲染器可用但本帧还没截取好的情况返回 false 而<b>不</b>记入不可用。</p>
     *
     * @return true 表示已绘制；false 表示还没有（本帧已入队，下一帧起可绘制）或该实体渲不出来
     */
    public boolean drawEntityModel(Canvas canvas, EntityType<?> type, float x, float y, float size) {
        if (canvas == null || type == null || modelUnsupported.contains(type)) return false;
        Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        if (id == null) return false;
        String key = MODEL_KEY_PREFIX + id + "|" + ICON_SIZE;
        Image image = icons.get(key);
        if (image == null) {
            requestModel(type, key);
            return false;
        }
        drawScaled(canvas, image, x, y, size);
        return true;
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

    /**
     * 清空缓存并释放贴图；资源包重载后应调用。
     *
     * <p>一并清掉「模型不可用」的判定：重载会重建实体渲染器，上一轮因为贴图 / 模型还没就绪而渲成空的
     * 实体有机会恢复正常（真正不可用的那几类最多再试一帧）。</p>
     */
    public void clear() {
        for (Image image : icons.values()) image.close();
        icons.clear();
        loading.clear();
        pending.clear();
        pendingModels.clear();
        rendered.clear();
        modelUnsupported.clear();
        releaseBackdrop();
    }

    /** 释放全部原生资源；渲染后端销毁时调用。 */
    public void close() {
        clear();
        // 上下文由 SkiaGlBackend 全项目共享，这里不关闭（关了会让其它界面与已采集贴图一起失效）
    }

    // ── 内部 ──

    /** 把缓存好的图标贴到目标矩形（物品与实体模型共用）。 */
    private static void drawScaled(Canvas canvas, Image image, float x, float y, float size) {
        float width = image.getWidth();
        float height = image.getHeight();
        canvas.drawImageRect(image,
                Rect.makeXYWH(0f, 0f, width, height),
                Rect.makeXYWH(x, y, size, size),
                SamplingMode.LINEAR, null, true);
    }

    private void request(ItemStack stack, String key) {
        if (loading.contains(key) || icons.containsKey(key)) return;
        loading.add(key);
        pending.add(new Request(stack.copy(), null, key));
    }

    /** 实体模型请求入队（每帧只消费一个，见 {@link #pendingModels}）。 */
    private void requestModel(EntityType<?> type, String key) {
        if (loading.contains(key) || icons.containsKey(key)) return;
        loading.add(key);
        pendingModels.add(new Request(null, type, key));
    }

    /** 模型路径判定为不可用：清掉在途标记（不再重试）并记入黑名单，让调用方退回静态图标。 */
    private void failModel(Request request) {
        loading.remove(request.key);
        if (request.type != null) modelUnsupported.add(request.type);
    }

    /**
     * 把一个实体模型记录进隐藏格子的两块（黑白底色各一次）。
     *
     * <p><b>为什么走 {@code Graphics#entity}：</b>26.1.2 的界面渲染有原版实体通道
     * （{@code GuiEntityRenderState}，即 PIP 实景渲染），物品栏右下角的玩家预览用的就是它。
     * 借它渲出来的是实体自己的模型与贴图，而不是刷怪蛋那种物品图标。</p>
     *
     * <p><b>取景口径</b>逐字照原版的玩家预览：姿态归零、正对镜头（{@code bodyRot = 180}），
     * 按包围盒高度定缩放、抬升半个身高使模型居中；带渲染缩放的实体先按 {@code scale} 归一包围盒，
     * 否则巨人（12 格高）整格只看得见一条腿。</p>
     *
     * <p><b>实体实例只现造不缓存：</b>每个实体类型一辈子只会请求一两次（成功即入贴图缓存、失败即记入
     * {@link #modelUnsupported}），现造一次比留一份缓存更省心——缓存下来的实例会一直引用旧地图对象，
     * 换世界后既渲染出陈旧数据也拖着整张地图不放。</p>
     *
     * <p><b>为什么两块都记同一份状态：</b>截取靠黑白两版反解 alpha，两块必须是同一个实体；
     * 而原版 PIP 的实体渲染器只有一张共用纹理，同帧多个状态会互相覆盖——这里两块同源，
     * 覆盖也不会画错，不同实体则必须一帧一个（见 {@link #pendingModels}）。</p>
     *
     * @return false 表示这个实体渲不出来，调用方应退回静态图标
     */
    private boolean renderModel(GuiGraphicsExtractor graphics, Request request, Minecraft client, Grid grid) {
        try {
            Entity entity = request.type.create(client.level, EntitySpawnReason.COMMAND);
            if (entity == null) return false;
            EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
            if (dispatcher == null || dispatcher.getRenderer(entity) == null) return false;

            EntityRenderState state = dispatcher.extractEntity(entity, 1f);
            if (state == null) return false;
            // 与玩家预览同一套「拍照」设置：去掉脚下阴影片与发光描边
            state.shadowPieces.clear();
            state.outlineColor = EntityRenderState.NO_OUTLINE;
            if (state instanceof LivingEntityRenderState living) {
                if (living.scale > 0f) {
                    living.boundingBoxHeight /= living.scale;
                    living.boundingBoxWidth /= living.scale;
                    living.scale = 1f;
                }
                living.bodyRot = 180f;
                living.yRot = 0f;
                living.xRot = 0f;
            }

            float blocks = Math.max(MODEL_MIN_BLOCKS, state.boundingBoxHeight);
            float modelScale = MODEL_FILL * ICON_SIZE / blocks;
            Vector3f translation = new Vector3f(0f, state.boundingBoxHeight / 2f + MODEL_Y_OFFSET, 0f);
            Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI);
            Quaternionf cameraRotation = new Quaternionf();

            int left = Math.round(grid.x(MODEL_SLOT));
            for (int row = 0; row < ROWS; row++) {
                int top = Math.round(grid.y(row, MODEL_SLOT));
                graphics.entity(state, modelScale, translation, rotation, cameraRotation,
                        left, top, left + ICON_SIZE, top + ICON_SIZE);
            }
            return true;
        } catch (Throwable error) {
            // 渲染状态抽取失败（原版会包成 ReportedException）、渲染器内部异常，或第三方模组实体的
            // 构造/初始化问题（NoClassDefFoundError 之类）：一律判为不可用，交给静态兜底。
            // 这里必须连 Error 一起拦：列表里含模组实体，一个坏实体不该把界面（乃至整个客户端）带崩
            return false;
        }
    }

    /**
     * 缓存键：注册表物品 id + {@code ITEM_MODEL} 组件 + {@code CUSTOM_MODEL_DATA} 组件 + 图标边长。
     *
     * <p><b>必须带上 {@code ITEM_MODEL}：</b>资源包的自定义物品没有注册表条目，预览时统一用
     * {@code Items.PAPER} 承载并靠 {@code ITEM_MODEL} 指定真实模型（见
     * {@code StardewPreview}），只按注册表 id 取键会让所有自定义物品挤成同一个键，
     * 于是除第一件外永远命中不到自己的图标。</p>
     *
     * <p><b>也必须带上 {@code CUSTOM_MODEL_DATA}：</b>旧布局（ItemsAdder / Nexo 等）整服的自定义物品
     * 共用同一个原版载体，图标之间的区别<b>只</b>体现在 {@code custom_model_data} 上（真机取证：
     * {@code minecraft:paper} 一张派发表承载 221 个条目、{@code minecraft:apple} 承载全部作物产物）。
     * 先前少了这一段，种子/肥料/洒水器/温室玻璃全挤成 {@code minecraft:paper|32} 一个键，
     * 于是每一行都画成第一件物品的贴图——用户 2026-09-20 反馈的「图标全部错误」。</p>
     */
    private static String keyOf(ItemStack stack) {
        String base = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        Identifier model = stack.get(DataComponents.ITEM_MODEL);
        CustomModelData custom = stack.get(DataComponents.CUSTOM_MODEL_DATA);
        StringBuilder key = new StringBuilder(base);
        if (model != null) key.append('#').append(model);
        if (custom != null) key.append('@').append(custom);
        return key.append('|').append(ICON_SIZE).toString();
    }

    /**
     * 隐藏格子区的排布：整片格子由上下两个色块（黑底 / 白底）组成，每块内部再按
     * {@code cols × rowsPerBlock} 网格铺开，行优先（先左到右、再上到下）。
     *
     * <p><b>为什么是「网格」而不是固定一排 12 格</b>（用户 2026-09-22：「物品选择器两旁出现了大图标」）：
     * 一排 12 格的宽度是 {@code CELL × 12 − SLOT_GAP = 428} 逻辑像素，与窗口无关；而面板宽度随
     * 「界面大小」（默认 75%）与 GUI 缩放变化 —— 1440p + GUI 缩放 4 下面板只有约 277 逻辑像素，
     * 一排铺不下，左右各两格放大图标直接露在面板外面。改成按面板可盖面积排版后，格子永远落在玻璃里。</p>
     *
     * @param cols         每行的格子数
     * @param rowsPerBlock 每个色块的行数
     * @param capacity     本排布最多装得下的格子数（可能少于本帧想截的张数）
     * @param left         整片格子左边缘（GUI 逻辑坐标）
     * @param blockTop     上面那个色块的上边缘
     */
    private record Grid(int cols, int rowsPerBlock, int capacity, float left, float blockTop) {

        /** 第 {@code slot} 格的横坐标。 */
        private float x(int slot) {
            return left + (slot % cols) * CELL;
        }

        /** 第 {@code slot} 格在色块 {@code colorRow}（0 = 黑底、1 = 白底）里的纵坐标。 */
        private float y(int colorRow, int slot) {
            float blockHeight = rowsPerBlock * CELL - SLOT_GAP;
            return blockTop + colorRow * (blockHeight + ROW_GAP) + (slot / cols) * CELL;
        }
    }

    /**
     * 按「面板玻璃盖得住的矩形」排出本帧的格子布局；返回 {@code null} 表示连一格都排不下（本帧不截图标）。
     *
     * <p><b>两个方向都要够</b>：格子露在面板外的部分就是屏幕上直接可见的放大图标，因此横向按
     * {@code cover} 宽度定列数、纵向按高度定行数。整体以屏幕中心居中、两个色块之间留 {@link #ROW_GAP}，
     * 因此两块的缝隙仍落在屏幕正中（正中的准星不会被截进任何一格）。</p>
     *
     * <p>{@code cover} 为 {@code null}（界面没给出可盖区域）时退回整屏可用：宁可照旧铺满，
     * 也不要让图标彻底加载不出来。</p>
     */
    private static Grid fit(float[] cover, int slots, Minecraft client) {
        float guiW = guiWidth(client);
        float guiH = guiHeight(client);
        float availW = cover == null ? guiW : Math.min(guiW, cover[2]);
        float availH = cover == null ? guiH : Math.min(guiH, cover[3]);
        // cols × CELL − SLOT_GAP ≤ availW
        int cols = (int) Math.min(slots, Math.floor((availW + SLOT_GAP) / CELL));
        // 两块各占 rows 行、块间留 ROW_GAP：rows × CELL − SLOT_GAP ≤ (availH − ROW_GAP) / 2
        int maxRows = (int) Math.floor(((availH - ROW_GAP) / 2f + SLOT_GAP) / CELL);
        if (cols < 1 || maxRows < 1) return null;
        int capacity = Math.min(slots, cols * maxRows);
        int rowsPerBlock = (capacity + cols - 1) / cols;
        float blockW = cols * CELL - SLOT_GAP;
        float blockH = rowsPerBlock * CELL - SLOT_GAP;
        // 取整以免与回读区域出现半像素错位
        return new Grid(cols, rowsPerBlock, capacity,
                Math.round(guiW / 2f - blockW / 2f),
                Math.round(guiH / 2f - (2f * blockH + ROW_GAP) / 2f));
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
    private static int[] slotScissorBox(int row, int slot, Minecraft client, Grid grid) {
        double scale = client.getWindow().getGuiScale();
        int windowW = client.getWindow().getWidth();
        int windowH = client.getWindow().getHeight();
        if (scale <= 0) return new int[]{0, 0, 1, 1};

        float x = grid.x(slot);
        float y = grid.y(row, slot);
        int left = Math.max(0, (int) Math.round(x * scale));
        int top = Math.max(0, (int) Math.round(y * scale));
        int right = Math.min(windowW, (int) Math.round((x + ICON_SIZE) * scale));
        int bottom = Math.min(windowH, (int) Math.round((y + ICON_SIZE) * scale));
        return new int[]{left, Math.max(0, windowH - bottom),
                Math.max(1, right - left), Math.max(1, bottom - top)};
    }

    /**
     * 把一个隐藏格子的两个色块各回读一次，反解出物品真实 alpha，自建预乘位图。
     *
     * <p><b>为什么必须两版：</b>物品是按 alpha 混合画到底色上的。黑底那版拿到的正是<b>预乘颜色</b>
     * {@code Cs·As}；白底那版在此基础上又多叠了 {@code (1-As)} 的白。两版逐通道相减恰好等于
     * {@code 1-As}，于是 alpha 能直接算出来——既不需要主帧缓冲可写 alpha，也不需要 Skija 尊重
     * 收养纹理的 alpha 类型（{@code adoptGLTextureFrom} 只接颜色类型、不接 alpha 类型，
     * 透明区会被当成不透明画成黑块）。</p>
     */
    private Capture captureIcon(int slot, Minecraft client, Grid grid) {
        if (!ensureContext()) return null;
        byte[][] passes = new byte[ROWS][];
        int width = 1;
        int height = 1;
        for (int row = 0; row < ROWS; row++) {
            int[] box = slotScissorBox(row, slot, client, grid);
            byte[] pixels = readRegion(box);
            if (pixels == null) return null;
            passes[row] = pixels;
            width = box[2];
            height = box[3];
        }

        byte[] dark = passes[0];
        byte[] light = passes[1];
        byte[] out = new byte[width * height * 4];
        boolean visible = false;
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
            if (a > 0) visible = true;
            // 预乘格式要求「颜色 ≤ alpha」，相减取整可能让颜色多出 1，夹一下避免越界像素
            if (r > a) r = a;
            if (g > a) g = a;
            if (b > a) b = a;
            out[i] = (byte) r;
            out[i + 1] = (byte) g;
            out[i + 2] = (byte) b;
            out[i + 3] = (byte) a;
        }
        Image image = Image.makeRasterFromBytes(
                new ImageInfo(width, height, ColorType.RGBA_8888, ColorAlphaType.PREMUL, ColorSpace.getSRGB()),
                out, width * 4);
        return new Capture(image, visible);
    }

    /**
     * 回读主 Framebuffer 的一块区域，返回行序已翻成自上而下的 RGBA 字节。
     *
     * <p>经临时 GL 纹理 + FBO 用 {@code glBlitFramebuffer} 中转，而不是直接对主 Framebuffer 调
     * {@code glReadPixels}：主 Framebuffer 可能是多重采样的，多重采样缓冲上直接回读是非法操作。</p>
     *
     * <p><b>像素打包状态必须整体隔离</b>（用户侧真机取证 2026-09-20，见复盘 149）：{@code glReadPixels}
     * 往我们这块 ByteBuffer 里写多少、按什么行距写，由上下文级的 PBO 绑定与 {@code GL_PACK_*}
     * 决定，而 LWJGL 的容量校验只按 {@code width × height × 4} 算。别处（Skija 自身的读回、
     * 截图类模组、Iris / Sodium / Voxy）一旦留下非 0 的 {@code PACK_ROW_LENGTH} 或还绑着
     * PBO，驱动就会按错误的行距往缓冲区<b>外</b>写 —— 那台机器上的表现是 nvoglv64.dll 在
     * glReadPixels 里 {@code EXCEPTION_ACCESS_VIOLATION}（写越界）直接闪退整个客户端。
     * 因此这里把打包状态保存 → 清零 → 还原，哪怕外面留着脏状态，我们这一次回读也是紧致行距。</p>
     *
     * <p>三道防线（同一台机器第二次取证 2026-09-20 23:40，见复盘 150）：进 {@code glReadPixels}
     * 前复核 PBO 确为 0（否则指针会被当成缓冲偏移量，写到无关地址）、目标缓冲区末尾留一行 + 64 字节
     * 冗余、主帧缓冲名字取不到（回落 0）时直接放弃本帧回读。任一道不过就返回 null，让调用方走保守路径。</p>
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
        int[] oldPackBuffer = new int[1];
        int[] oldPackRowLength = new int[1];
        int[] oldPackAlignment = new int[1];
        int[] oldPackSkipPixels = new int[1];
        int[] oldPackSkipRows = new int[1];
        int[] oldPackImageHeight = new int[1];
        int[] oldPackSkipImages = new int[1];
        boolean packSwapBytes = glIsEnabled(GL_PACK_SWAP_BYTES);
        boolean packLsbFirst = glIsEnabled(GL_PACK_LSB_FIRST);
        boolean framebufferSrgb = glIsEnabled(GL_FRAMEBUFFER_SRGB);
        glGetIntegerv(GL_ACTIVE_TEXTURE, oldActiveTexture);
        glActiveTexture(GL_TEXTURE0);
        glGetIntegerv(GL_TEXTURE_BINDING_2D, oldTexture);
        glGetIntegerv(GL_SAMPLER_BINDING, oldSampler);
        glGetIntegerv(GL_READ_FRAMEBUFFER_BINDING, oldReadFramebuffer);
        glGetIntegerv(GL_DRAW_FRAMEBUFFER_BINDING, oldDrawFramebuffer);
        glGetIntegerv(GL_VIEWPORT, oldViewport);
        glGetIntegerv(GL_SCISSOR_BOX, oldScissorBox);
        glGetIntegerv(GL_PIXEL_PACK_BUFFER_BINDING, oldPackBuffer);
        glGetIntegerv(GL_PACK_ROW_LENGTH, oldPackRowLength);
        glGetIntegerv(GL_PACK_ALIGNMENT, oldPackAlignment);
        glGetIntegerv(GL_PACK_SKIP_PIXELS, oldPackSkipPixels);
        glGetIntegerv(GL_PACK_SKIP_ROWS, oldPackSkipRows);
        glGetIntegerv(GL_PACK_IMAGE_HEIGHT, oldPackImageHeight);
        glGetIntegerv(GL_PACK_SKIP_IMAGES, oldPackSkipImages);

        // 清零打包状态：解绑 PBO（否则 pixels 会被驱动当成该缓冲的偏移量）、行距回归紧致
        // width × 4、对齐 1（RGBA8 下与默认 4 等价，但把「别人改过的 8」也一并摁回默认值）
        glBindBuffer(GL_PIXEL_PACK_BUFFER, 0);
        glPixelStorei(GL_PACK_ROW_LENGTH, 0);
        glPixelStorei(GL_PACK_ALIGNMENT, 1);
        glPixelStorei(GL_PACK_SKIP_PIXELS, 0);
        glPixelStorei(GL_PACK_SKIP_ROWS, 0);
        glPixelStorei(GL_PACK_IMAGE_HEIGHT, 0);
        glPixelStorei(GL_PACK_SKIP_IMAGES, 0);
        glPixelStorei(GL_PACK_SWAP_BYTES, GL_FALSE);
        glPixelStorei(GL_PACK_LSB_FIRST, GL_FALSE);

        int textureId = 0;
        int framebufferId = 0;
        try {
            // 取不到主帧缓冲的名字（回落值 0 = 窗口默认帧缓冲）时宁可不回读：那个缓冲区的内容不受
            // 我们控制，而且正是驱动最不擅长的读写目标 —— 返回 null 让调用方走「本帧不画格子」的
            // 保守路径，比冒险读窗口后缓冲稳。
            int sourceFramebuffer = SkiaGlBackend.mainFramebufferId();
            if (sourceFramebuffer == 0) return null;

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

            glBindFramebuffer(GL_READ_FRAMEBUFFER, sourceFramebuffer);
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
                // 解绑 PBO 是本方法的性命所在，调用前再核一次：若此刻仍绑着非 0 的 PBO，驱动会把
                // pixels 这个指针当成该缓冲的字节偏移量，写到与我们缓冲区毫不相干的地址去（那台机器
                // 上写地址落在 JVM 已预留未提交的堆区，直接 EXCEPTION_ACCESS_VIOLATION）。宁可本帧
                // 不回读，也不带着这个前提进 glReadPixels。
                int[] packBuffer = new int[1];
                glGetIntegerv(GL_PIXEL_PACK_BUFFER_BINDING, packBuffer);
                if (packBuffer[0] != 0) return null;

                // 必须用堆外内存而不是 MemoryStack：LWJGL 的栈每个线程只有 64 KB，而备份整块格子
                // 区域时这里要一次拿到 1 MB 以上，用 stack.malloc 会直接 OutOfMemoryError（曾崩过一次）。
                // 末尾多留一行 + 64 字节：万一驱动在行距/对齐上仍多写一点，落点还在我们自己的分配里，
                // 而不是踩到未映射页把整个进程带走（复盘 149、150）。
                ByteBuffer pixels = BufferUtils.createByteBuffer(rowBytes * height + rowBytes + 64);
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
            // 打包状态逐个还原（先还参数、最后还 PBO 绑定：绑定还原后 pixels 语义就回到外面那套了）
            glPixelStorei(GL_PACK_ROW_LENGTH, oldPackRowLength[0]);
            glPixelStorei(GL_PACK_ALIGNMENT, oldPackAlignment[0]);
            glPixelStorei(GL_PACK_SKIP_PIXELS, oldPackSkipPixels[0]);
            glPixelStorei(GL_PACK_SKIP_ROWS, oldPackSkipRows[0]);
            glPixelStorei(GL_PACK_IMAGE_HEIGHT, oldPackImageHeight[0]);
            glPixelStorei(GL_PACK_SKIP_IMAGES, oldPackSkipImages[0]);
            glPixelStorei(GL_PACK_SWAP_BYTES, packSwapBytes ? GL_TRUE : GL_FALSE);
            glPixelStorei(GL_PACK_LSB_FIRST, packLsbFirst ? GL_TRUE : GL_FALSE);
            glBindBuffer(GL_PIXEL_PACK_BUFFER, oldPackBuffer[0]);
            if (framebufferSrgb) {
                glEnable(GL_FRAMEBUFFER_SRGB);
            } else {
                glDisable(GL_FRAMEBUFFER_SRGB);
            }
        }
    }

    // ── 隐藏格子矩形的写回（当前无产出，保底接口） ──
    //
    // 这里曾经有一套「抽帧阶段回读整块格子区域 → 帧末写回」的掩盖动作：格子固定一排 12 格（428 逻辑像素）
    // 居中铺开，比面板宽，只能靠把画面备份糊回去挡住。它同时也是用户 2026-09-20 ~ 21 报的那一串怪象
    // （正中横带 / 白糊 / 一块纯黑 / 一条更亮的带子）的来源 —— 回读拿到的是上一帧的合成结果，写回与
    // 周围本帧画面的差异会逐帧累积。现在格子改由 {@link #fit} 按面板可盖面积排版、永远在玻璃里，
    // 「格子露在面板外」这个前提不存在了，产出备份的入口（原 saveBackdrop）已删除。
    // 下面这几个方法保留为保底接口：外部调用点（SkiaGlBackend#beginScreenFrame、
    // SkiaScreen#renderSkiaFrame、RenderTargetMixin）不必跟着改，且备份恒为 null 时它们全都是空操作。
    // 将来真遇到「面板盖不住格子」的界面，正确做法是让那个界面给出更准的 coverRegion，而不是把回读写回来。

    /**
     * 把备份的画面画回格子矩形。
     *
     * <p><b>可重放</b>：画完<b>不</b>释放备份，同一帧里谁来调都能再画一次（内容相同，重复画也幂等）。
     * 原因见 {@link #flushBackdrop}：写回可能被 ESP 叠加层抢在面板之前执行一次，帧末界面路径必须还能再画。
     * 释放统一交给「本帧没有格子」的 {@link #renderPending} 早退分支与 {@link #clear()}。</p>
     *
     * <p>画回到 MSAA 主帧缓冲只能靠绘制（{@code glBlitFramebuffer} 不允许「单采样 → 多采样」），
     * 所以这里走 Skija 而不是 GL blit。</p>
     */
    public void paintBackdrop(Canvas canvas) {
        Image image = backdropImage;
        float[] box = backdropGuiBox;
        if (image == null) return;
        try {
            if (canvas == null || box == null) return;
            canvas.drawImageRect(image,
                    Rect.makeXYWH(0f, 0f, image.getWidth(), image.getHeight()),
                    Rect.makeXYWH(box[0], box[1], box[2], box[3]),
                    SamplingMode.DEFAULT, null, true);
        } catch (RuntimeException ignored) {
            // 画布/贴图异常（surface 重建等）：留给下一帧重新备份，不让它把帧末流程带崩
        }
    }

    /**
     * 兜底写回：用共享后端把格子备份画回主帧缓冲（换屏 / 关屏那种「面板这一帧不画」的收尾）。
     *
     * <p><b>它不是主路径。</b>主路径是界面自己的后端：{@code SkiaGlBackend#beginScreenFrame}
     * 在画面板之前写回并 flush —— 那条路径与面板玻璃同在后端、同一个 Skija 表面，行为由面板绘制本身
     * 背书。本方法走的是常驻共享后端，历史上曾出现「画了但没落到呈现上」，所以只用来兜住
     * 「帧末没有界面绘制」的那两种切屏：{@code SkiaScreen#renderSkiaFrame} 的跳过分支与
     * {@code RenderTargetMixin}。</p>
     *
     * <p>与 {@link #paintBackdrop} 一样是幂等的：同一帧里主路径画过之后这里再画一次也无副作用
     * （写回不再是一次性动作，见 {@link #paintBackdrop}）。</p>
     */
    public void flushBackdrop() {
        if (backdropImage == null) return;
        SkiaGlBackend backend = SkiaGlBackend.shared();
        Canvas canvas = backend.begin(SkiaGlBackend.mainFramebufferId());
        if (canvas == null) {
            releaseBackdrop();
            return;
        }
        try {
            paintBackdrop(canvas);
        } finally {
            backend.end();
        }
    }

    /**
     * 收尾：本帧确定不会再画面板（换屏 / 关屏）时调用 —— 先把备份写回，再释放它。
     *
     * <p>写回是幂等的（见 {@link #paintBackdrop}），这里额外释放是为了不让陈旧备份被后续帧反复画到
     * 屏幕上：那些帧已经没有格子要盖，旧备份留在手里就会变成一块贴在画面上的「上一帧切片」。</p>
     */
    public void finishBackdropFrame() {
        flushBackdrop();
        releaseBackdrop();
    }

    /** 释放尚未写回的备份。 */
    private void releaseBackdrop() {
        if (backdropImage != null) {
            backdropImage.close();
            backdropImage = null;
        }
        backdropGuiBox = null;
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

    /** 本帧被渲染进隐藏格子的请求：物品与实体模型共用，靠 {@code stack}/{@code type} 区分。 */
    private static final class Request {
        /** 物品请求的物品堆；实体模型请求为 {@code null}。 */
        private final ItemStack stack;
        /** 实体模型请求的实体类型；物品请求为 {@code null}。 */
        private final EntityType<?> type;
        private final String key;
        private int slot;

        private Request(ItemStack stack, EntityType<?> type, String key) {
            this.stack = stack;
            this.type = type;
            this.key = key;
        }
    }

    /** 一次截取的结果：反解出的预乘位图 + 是否含有可见像素（整格全透明说明这一格什么都没画上）。 */
    private record Capture(Image image, boolean visible) {
    }
}
