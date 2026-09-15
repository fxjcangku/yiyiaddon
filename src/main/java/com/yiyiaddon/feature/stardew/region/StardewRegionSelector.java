package com.yiyiaddon.feature.stardew.region;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
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
import org.lwjgl.glfw.GLFW;

/**
 * 种植区域选区模式：空手左键点第一个角、右键点对角，WorldEdit 那种实时预览手感。
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
 * <p><b>为什么只认物理按键：</b>本模组自己的自动交互走的是同一个
 * {@code MultiPlayerGameMode} 通道，同样会触发这两个事件。模式内若不加区分，模块自己的
 * 播种 / 收割会被拦下来。这里只在「鼠标键真的被按下」时才接管。</p>
 *
 * <p><b>手持别的东西时也拦、但不算选点：</b>模式内的一切左右键都不该落到世界里，因此拦下范围
 * 与「手上是什么」无关；只有空手（或手持已设定的选点工具）时，这一击才算「点角」。
 * 该不该手持工具在进入模式时就已经检查过，所以不存在「默默拦住却不生效」的困惑。</p>
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

    /** 当前选区绑定的作物（null = 不在选区模式） */
    private String cropKey;
    private String cropName;
    /** 已记下的第一个角 */
    private BlockPos firstCorner;

    public StardewRegionSelector(StardewFarmModule module) {
        this.module = module;
        instance = this;
        if (installed) return;
        installed = true;

        AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
            StardewRegionSelector selector = instance;
            if (selector == null || !selector.shouldTakeOver(player, hand, GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
                return InteractionResult.PASS;
            }
            if (selector.toolReady()) selector.markFirstCorner(pos);
            return InteractionResult.FAIL;
        });
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            StardewRegionSelector selector = instance;
            if (selector == null || !selector.shouldTakeOver(player, hand, GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
                return InteractionResult.PASS;
            }
            if (selector.toolReady()) selector.markSecondCorner(hitResult);
            return InteractionResult.FAIL;
        });
        // 右键没指到方块（点到空气）走的是「使用物品」这条路，同样拦下，免得模式内把食物吃了
        UseItemCallback.EVENT.register((player, level, hand) -> {
            StardewRegionSelector selector = instance;
            return selector != null && selector.shouldTakeOver(player, hand, GLFW.GLFW_MOUSE_BUTTON_RIGHT)
                ? InteractionResult.FAIL : InteractionResult.PASS;
        });
        // 左键指到生物时原版会攻击，模式内一并拦下
        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            StardewRegionSelector selector = instance;
            return selector != null && selector.shouldTakeOver(player, hand, GLFW.GLFW_MOUSE_BUTTON_LEFT)
                ? InteractionResult.FAIL : InteractionResult.PASS;
        });
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            StardewRegionSelector selector = instance;
            return selector != null && selector.isActive();
        });
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  进入 / 退出
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public boolean isActive() {
        return cropKey != null;
    }

    /** 当前选区绑定的作物中文名（不在模式内返回 null） */
    public String cropName() {
        return cropName;
    }

    /** 是否已经点下第一个角 */
    public boolean hasFirstCorner() {
        return firstCorner != null;
    }

    /**
     * 进入选区模式。
     *
     * @return 失败原因；成功返回 {@code null} 并已发出进入提示
     */
    public String enter(String cropKey, String cropName) {
        if (!module.regionPlantingOn()) return "「分区种植」还没开启：请先在设置里打开这个实验开关";
        if (cropKey == null || cropName == null) return "请指定要绑定到这块地的作物";
        this.cropKey = cropKey;
        this.cropName = cropName;
        this.firstCorner = null;
        WorldOverlay.register(LAYER_ID, this::render);
        CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "种植区域 ▶ 作物 " + cropName)
            .field("怎么选", module.regionToolHint())
            .field("退出", ".stardew 种植区域 取消")
            .status(CommandMessageFormatter.Level.SUCCESS, "等第一个角")
            .send();
        return null;
    }

    /** 退出选区模式；{@code silent} 为 true 时不播报（切服 / 关闭模块 / 死亡停机用） */
    public void cancel(boolean silent) {
        boolean wasActive = isActive();
        cropKey = null;
        cropName = null;
        firstCorner = null;
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

    private void markFirstCorner(BlockPos pos) {
        firstCorner = pos;
        CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "已记下第一个角")
            .field("作物", cropName)
            .coord(pos.getX(), pos.getY(), pos.getZ())
            .status(CommandMessageFormatter.Level.SUCCESS, module.regionSecondCornerHint())
            .send();
    }

    private void markSecondCorner(BlockHitResult hit) {
        if (firstCorner == null) {
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "还没记下第一个角")
                .field("怎么选", module.regionToolHint())
                .status(CommandMessageFormatter.Level.FAILURE, "请先用左键点一个角")
                .send();
            return;
        }
        String failure = module.createRegion(cropKey, cropName, firstCorner, hit.getBlockPos());
        if (failure != null) {
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "设置种植区域失败")
                .field("作物", cropName)
                .field("原因", failure)
                .status(CommandMessageFormatter.Level.FAILURE, "未保存")
                .send();
            return;
        }
        // 建区成功即退出模式：一块地对应一个作物，继续圈下一块要重新敲一次指令
        cancel(true);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  接管判定
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 这一次点击要不要被选区接管（接管 = 返回 FAIL，不给服务器发包）。
     *
     * <p>接管范围是「模式内 + 原版世界内 + 主手 + 鼠标真的按下」，与「手上是不是选点工具」无关：
     * 手持别的东西时也拦（不挖、不放、不浇水、不开箱），只是不算选点，避免玩家在模式内误操作。</p>
     */
    private boolean shouldTakeOver(Player player, InteractionHand hand, int glfwButton) {
        if (!isActive()) return false;
        if (mc.player == null || mc.level == null) return false;
        if (player != mc.player || hand != InteractionHand.MAIN_HAND) return false;
        if (mc.screen != null) return false;
        if (!module.regionPlantingOn()) return false;
        return physicalMouseDown(glfwButton);
    }

    /** 手上是不是允许的选点工具（空手，或手持已设定的工具） */
    private boolean toolReady() {
        return module.regionToolUsable();
    }

    /** 只认物理按下的鼠标键：模组自己的自动交互不按鼠标，因此不会误拦 */
    private boolean physicalMouseDown(int glfwButton) {
        var window = mc.getWindow();
        return window != null && GLFW.glfwGetMouseButton(window.handle(), glfwButton) == GLFW.GLFW_PRESS;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  预览绘制
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private void render(EspRenderer renderer) {
        if (!isActive() || firstCorner == null) return;
        if (mc.player == null || mc.level == null) return;
        BlockPos cursor = crosshairBlock();
        if (cursor == null) return;
        renderer.box(rect(firstCorner, cursor), PREVIEW_SIDE, PREVIEW_LINE, ShapeMode.Lines, LINE_THICKNESS);
        renderer.blockBox(firstCorner.getX(), firstCorner.getY(), firstCorner.getZ(),
            FIRST_CORNER, FIRST_CORNER, ShapeMode.Lines, LINE_THICKNESS);
        int sizeX = Math.abs(firstCorner.getX() - cursor.getX()) + 1;
        int sizeZ = Math.abs(firstCorner.getZ() - cursor.getZ()) + 1;
        renderer.text(sizeX + " × " + sizeZ + " · " + cropName,
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
