package com.yiyiaddon.feature.stardew.region;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.ui.StardewRegionCropScreen;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * 种植区域选区模式：左键点第一个角、右键点对角，WorldEdit 那种实时预览手感。
 *
 * <p><b>为什么用事件而不是每 tick 读鼠标：</b>选区必须与「原版真的做了什么」对齐。左键用
 * {@link AttackBlockCallback}，右键用 {@link UseBlockCallback}，两者返回
 * {@link InteractionResult#FAIL} 时<b>不会向服务器发包</b>（见 Fabric 事件说明），
 * 因此模式内不可能挖到方块、浇水、开箱、播种。</p>
 *
 * <p><b>为什么还要挡「按住不放」：</b>左键按住时原版走的是 {@code Minecraft.continueAttack}，
 * 它不经过方块交互回调，会持续累积破坏进度。这里挂
 * {@link ClientPreAttackCallback}，模式内把 {@code startAttack} 与 {@code continueAttack}
 * 一并取消（Fabric 内部同一个开关），按住不放也挖不动。</p>
 *
 * <p><b>为什么不再按物理鼠标状态判定：</b>原来这几个回调都要先问一句
 * {@code glfwGetMouseButton(...) == PRESS}，用来把「模组自己的自动交互」排除掉。但<b>点一下</b>
 * （按下与抬起落在同一帧）时，等原版这一 tick 走到回调，物理键早就是 RELEASE 了，判定为「不是物理点击」
 * → 不接管 → 原版的破坏照常发出，作物被打掉（实机反馈「区域选点左键点击会破坏农作物」）。
 * 而这套排除本来就是多余的：模组自己的交互走 {@code BlockPacketSender} 直接构造
 * {@code ServerboundUseItemOnPacket} 发包，完全不经过 {@code MultiPlayerGameMode}，
 * 也就不会触发这里的任何回调。所以现在模式内一律接管，不再看鼠标状态。</p>
 *
 * <p><b>手持别的东西时也拦、但默认同样算点角：</b>模式内的一切左右键都不该落到世界里，因此拦下范围
 * 与「手上是什么」无关。<b>默认「不限」</b>——手持任何物品都算点角（站在地里多半正拿着锄头或种子，
 * 不该逼人先切空手）；只有在设置里指定了选点工具时，它才变成白名单，其余物品只被拦下、不落点。
 * 该不该手持工具在进入模式时就已经检查过，所以不存在「默默拦住却不生效」的困惑。</p>
 *
 * <p><b>作物可以留到点完角再定：</b>从控制台「圈地」或 {@code .stardew 种植区域 选择} 进来时
 * 不预先绑作物，第二个角点完弹窗，让玩家点一下这块地种哪种已勾选作物（见
 * {@link StardewRegionCropScreen}）。指令里直接点名作物则照旧不弹。</p>
 *
 * <p><b>半成品不落盘：</b>只点了第一个角就切服 / 退出世界 / 关闭模块时，选区直接丢弃。</p>
 */
public final class StardewRegionSelector {

    /** 选区预览渲染层：只在选区模式内挂载 */
    private static final String LAYER_ID = "stardew-region-select";

    private static final float LINE_THICKNESS = 1.5f;
    /** 第一个角：亮绿（与「当前目标」同一族配色，一眼看出已经记下了） */
    private static final EspColor FIRST_CORNER = new EspColor(0x00FF64, 200);
    /** 预览矩形：淡青，与最终区域框区分开 */
    private static final EspColor PREVIEW_SIDE = new EspColor(0x00C8FF, 40);
    private static final EspColor PREVIEW_LINE = new EspColor(0x00C8FF, 160);

    /** 当前实例：事件回调只装一次，永远转给最新的这个实例 */
    private static StardewRegionSelector instance;
    /** 事件回调是否已装（避免重复注册导致一次点击建出两个区域） */
    private static boolean installed;

    private final Minecraft mc = Minecraft.getInstance();
    private final StardewFarmModule module;

    /** 是否在选区模式（作物可以先不定，所以不能拿 cropKey 当开关） */
    private boolean active;
    /** 当前选区绑定的作物；{@code null} = 作物待定，点完两个角再弹窗选 */
    private String cropKey;
    private String cropName;
    /** 已记下的第一个角 */
    private BlockPos firstCorner;
    /** 待弹窗时暂存的第二个角 */
    private BlockPos secondCorner;

    public StardewRegionSelector(StardewFarmModule module) {
        this.module = module;
        instance = this;
        if (installed) return;
        installed = true;

        AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
            StardewRegionSelector selector = instance;
            if (selector == null || !selector.shouldTakeOver(player, hand)) {
                return InteractionResult.PASS;
            }
            if (selector.toolReady()) selector.markFirstCorner(pos);
            return InteractionResult.FAIL;
        });
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            StardewRegionSelector selector = instance;
            if (selector == null || !selector.shouldTakeOver(player, hand)) {
                return InteractionResult.PASS;
            }
            if (selector.toolReady()) selector.markSecondCorner(hitResult);
            return InteractionResult.FAIL;
        });
        // 右键没指到方块（点到空气）走的是「使用物品」这条路，同样拦下，免得模式内把食物吃了
        UseItemCallback.EVENT.register((player, level, hand) -> {
            StardewRegionSelector selector = instance;
            return selector != null && selector.shouldTakeOver(player, hand)
                ? InteractionResult.FAIL : InteractionResult.PASS;
        });
        // 左键指到生物时原版会攻击，模式内一并拦下
        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            StardewRegionSelector selector = instance;
            return selector != null && selector.shouldTakeOver(player, hand)
                ? InteractionResult.FAIL : InteractionResult.PASS;
        });
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            StardewRegionSelector selector = instance;
            if (selector == null || !selector.shouldTakeOver(player, InteractionHand.MAIN_HAND)) {
                return false;
            }
            // 记第一个角必须在这里做：返回 true 会取消原版 startAttack，于是 gameMode.startDestroyBlock
            // 不执行，AttackBlockCallback 根本收不到这一次点击（实机反馈「左键点了没反应」就是这条）。
            if (selector.toolReady()) {
                BlockPos pos = selector.crosshairBlock();
                if (pos != null) selector.markFirstCorner(pos);
            }
            return true;
        });
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  进入 / 退出
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public boolean isActive() {
        return active;
    }

    /** 当前选区绑定的作物中文名（不在模式内返回 null；作物待定时返回「待选作物」） */
    public String cropName() {
        return displayCrop();
    }

    /** 是否已经点下第一个角 */
    public boolean hasFirstCorner() {
        return firstCorner != null;
    }

    /** 播报与预览统一用这个：作物待定时不能显示 null */
    private String displayCrop() {
        return cropName == null ? "待选作物" : cropName;
    }

    /**
     * 进入选区模式。
     *
     * @param cropKey  作物键；{@code null} = 作物待定，第二个角点完弹窗选
     * @param cropName 作物中文名；随 {@code cropKey} 一起为 null
     * @return 失败原因；成功返回 {@code null} 并已发出进入提示
     */
    public String enter(String cropKey, String cropName) {
        this.active = true;
        this.cropKey = cropKey;
        this.cropName = cropName;
        this.firstCorner = null;
        this.secondCorner = null;
        WorldOverlay.register(LAYER_ID, this::render);
        CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "种植区域 ▶ " + displayCrop())
            .field("怎么选", module.regionToolHint())
            .field("退出", ".stardew 种植区域 取消")
            .status(CommandMessageFormatter.Level.SUCCESS,
                cropKey == null ? "等第一个角（作物点完角再选）" : "等第一个角")
            .send();
        return null;
    }

    /** 退出选区模式；{@code silent} 为 true 时不播报（切服 / 关闭模块 / 死亡停机用） */
    public void cancel(boolean silent) {
        boolean wasActive = isActive();
        active = false;
        cropKey = null;
        cropName = null;
        firstCorner = null;
        secondCorner = null;
        WorldOverlay.unregister(LAYER_ID);
        if (!silent && wasActive) {
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "种植区域已取消")
                .status(CommandMessageFormatter.Level.SUCCESS, "半成品已丢弃，没有写入任何区域")
                .send();
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  选点
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void markFirstCorner(BlockPos clicked) {
        // 盆上有作物时射线打中的是作物那一格，归一到盆那一层再记：否则区域框整体浮高一格
        BlockPos pos = StardewFarmScanner.normalizeToPot(clicked);
        // 鼠标这一击可能被两条路都看到（PreAttack 与 AttackBlock）：同一格只记一次，免得重复播报
        if (pos.equals(firstCorner)) return;
        firstCorner = pos.immutable();
        CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "已记下第一个角")
            .field("这块地", displayCrop())
            .coord(pos.getX(), pos.getY(), pos.getZ())
            .status(CommandMessageFormatter.Level.SUCCESS, module.regionSecondCornerHint())
            .send();
    }

    private void markSecondCorner(BlockHitResult hit) {
        // 与第一个角同一口径：先归一到盆那一层，区域框才落在地里而不是作物层上
        BlockPos pos = StardewFarmScanner.normalizeToPot(hit.getBlockPos());
        if (firstCorner == null) {
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "还没记下第一个角")
                .field("怎么选", module.regionToolHint())
                .status(CommandMessageFormatter.Level.FAILURE, "请先用左键点一个角")
                .send();
            return;
        }
        // 作物待定：先弹窗问「这块地种什么」，玩家点定之后才建区
        if (cropKey == null) {
            secondCorner = pos.immutable();
            openCropChooser();
            return;
        }
        createRegion(cropKey, cropName, pos);
    }

    /**
     * 弹出「这块地种什么」窗口（作物待定模式）。
     *
     * <p>面板打开期间 {@code mc.screen != null}，选区接管判定自然失效，鼠标可以正常点按钮。</p>
     */
    private void openCropChooser() {
        if (mc.screen != null) return;
        mc.setScreen(new StardewRegionCropScreen(null, module, firstCorner, secondCorner,
            (key, name) -> createRegion(key, name, secondCorner), () -> cancel(false)));
    }

    /** 建区收口：成功即退出模式；失败留在模式里，玩家可以换个角再右键试一次 */
    private void createRegion(String cropKey, String cropName, BlockPos secondCorner) {
        String failure = module.createRegion(cropKey, cropName, firstCorner, secondCorner);
        if (failure != null) {
            this.secondCorner = null;
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "设置种植区域失败")
                .field("这块地", cropName)
                .field("原因", failure)
                .status(CommandMessageFormatter.Level.FAILURE, "未保存")
                .send();
            return;
        }
        // 建区成功即退出模式：一块地对应一套种法，继续圈下一块要重新敲一次指令
        cancel(true);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  接管判定
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 这一次点击要不要被选区接管（接管 = 返回 FAIL，不给服务器发包）。
     *
     * <p>接管范围是「模式内 + 原版世界内 + 主手 + 没有别的界面开着」，<b>不看鼠标状态</b>：
     * 模组自己的自动交互走 {@code BlockPacketSender} 直接发包，根本不会进到这里，
     * 而按鼠标状态判定会让「点一下」（按下与抬起同一帧）漏接管、把作物打掉（实机反馈）。
     * 手持别的东西时也拦（不挖、不放、不浇水、不开箱），只是不算选点，避免玩家在模式内误操作。</p>
     */
    private boolean shouldTakeOver(Player player, InteractionHand hand) {
        if (!isActive()) return false;
        if (mc.player == null || mc.level == null) return false;
        if (player != mc.player || hand != InteractionHand.MAIN_HAND) return false;
        return mc.screen == null;
    }

    /** 手上这一击算不算点角（默认「不限」，指定选点工具后变成白名单） */
    private boolean toolReady() {
        return module.regionToolUsable();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  预览绘制
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void render(EspRenderer renderer) {
        if (!isActive() || firstCorner == null) return;
        if (mc.player == null || mc.level == null) return;
        BlockPos cursor = StardewFarmScanner.normalizeToPot(crosshairBlock());
        if (cursor == null) return;
        renderer.box(rect(firstCorner, cursor), PREVIEW_SIDE, PREVIEW_LINE, ShapeMode.Lines, LINE_THICKNESS);
        renderer.blockBox(firstCorner.getX(), firstCorner.getY(), firstCorner.getZ(),
            FIRST_CORNER, FIRST_CORNER, ShapeMode.Lines, LINE_THICKNESS);
        int sizeX = Math.abs(firstCorner.getX() - cursor.getX()) + 1;
        int sizeZ = Math.abs(firstCorner.getZ() - cursor.getZ()) + 1;
        // §l = 加粗（用户 2026-09-19：「所有的点位模块都要字体加粗」，MinecraftText 的测量与绘制都认）
        renderer.text("§l" + sizeX + " × " + sizeZ + " · " + displayCrop(),
            (Math.min(firstCorner.getX(), cursor.getX()) + Math.max(firstCorner.getX(), cursor.getX())) / 2.0 + 0.5,
            Math.min(firstCorner.getY(), cursor.getY()) + 1.6,
            (Math.min(firstCorner.getZ(), cursor.getZ()) + Math.max(firstCorner.getZ(), cursor.getZ())) / 2.0 + 0.5,
            module.regionLabelSize(), PREVIEW_LINE, 1.0f, true);
    }

    /** 预览矩形的世界包围盒：两角取最小 / 最大，底面对齐两角中较低的那一层 */
    static AABB rect(BlockPos a, BlockPos b) {
        int minX = Math.min(a.getX(), b.getX());
        int maxX = Math.max(a.getX(), b.getX());
        int minY = Math.min(a.getY(), b.getY());
        int minZ = Math.min(a.getZ(), b.getZ());
        int maxZ = Math.max(a.getZ(), b.getZ());
        return new AABB(minX, minY, minZ, maxX + 1.0, minY + 1.0, maxZ + 1.0);
    }

    /** 准星指向的方块；没指到方块返回 null */
    private BlockPos crosshairBlock() {
        HitResult hit = mc.hitResult;
        return hit instanceof BlockHitResult block && hit.getType() == HitResult.Type.BLOCK ? block.getBlockPos() : null;
    }
}
