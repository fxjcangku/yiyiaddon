package com.yiyiaddon.feature.villager.render;

import com.yiyiaddon.feature.villager.repository.VillagerBindingStore;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 容器 ESP 渲染器：给绑定的绿宝石箱与成品交易箱画发光线框。
 *
 * <p>逐字迁移自旧项目 {@code villager/render/ContainerESP}（82 行）：两个绑定的判定、64 格距离门限、
 * 大箱子合并成一个包围盒的算法（{@code ChestBlock.TYPE} 非单箱时沿 {@code getConnectedDirection}
 * 找另一半，两边取并集）全部原样；绘制路线换成本项目既定的「EspRenderer + 原版 gizmo」基建
 * （开发习惯第 147 条），本类只描述「画什么」。</p>
 *
 * <p><b>调用关系</b>：模块 {@code AutoVillagerTradeModule} 在启用时把本类注册进
 * {@code WorldOverlay} 世界渲染层、关闭时注销（等价旧 {@code @EventHandler Render3DEvent} +
 * {@code if (!isActive()) return;}），由 {@link EspRenderer} 每帧回调 {@link #render(EspRenderer)}；
 * 本类不做任何业务决策，也不读模块设置。</p>
 *
 * <p><b>配色（第 141 / 146 条：禁止颜色字面量散落）</b>：旧实现用框架色 {@code Color.GREEN}
 * （角色 = 绿宝石箱）与 {@code Color.CYAN}（角色 = 成品交易箱）；本项目统一走
 * {@link com.yiyiaddon.ui.render.world.ColorPresets} 的「绿」（下标 1）/「青」（下标 3），
 * 下标以本类具名常量给出，绘制瞬间按 {@link EspColor#argb()} 解析（彩虹支持随调色板免费获得）。</p>
 *
 * <p><b>只画线框，不画文字</b>：旧类注释写「在绑定的绿宝石箱和成品交易箱上方显示文字标签」，
 * 但实现里 {@code renderLabel} 只调 {@code event.renderer.box(...)}，收下的 {@code text} 参数仅用于
 * 判颜色（{@code text.contains("绿宝石")}），从未绘制文字（旧 {@code :51-81} 实测）。本类按实测行为
 * 1:1 只画线框，并把「用哪一色」从「猜文本」改为调用点显式传参（等价且不再依赖文案字面量）。</p>
 *
 * <p><b>另一处登记差异</b>：旧实现 {@code event.renderer.box(..., ShapeMode.Lines, 0)} 的线宽传 0
 * （旧框架内部固定线宽）；本项目 {@code EspRenderer} 的线宽参与计算，传 0 会因像素宽为 0 而整框不画，
 * 故显式给 {@link #LINE_THICKNESS}（照 {@code AutoChestRenderer} / {@code StardewRenderState} 先例）。</p>
 *
 * <p>旧实现不校验维度（{@code ContainerBinding} 的维度取值未被使用），本类保持 1:1 不校验；
 * 绑定坐标若在其它维度，与旧项目一样只是同坐标画的框（距离门限照常生效）。</p>
 */
public final class ContainerESP {

    /** 渲染距离：超过这个距离的绑定箱不画（旧 {@code renderLabel :56} 的 64 格） */
    private static final double RENDER_DISTANCE = 64.0;

    /** 线框线宽（GUI 缩放坐标）；旧渲染器固定线宽，本项目在此显式给出 */
    private static final float LINE_THICKNESS = 1.5f;

    /** 绿宝石箱配色在 {@link com.yiyiaddon.ui.render.world.ColorPresets} 里的下标（「绿」，旧 {@code Color.GREEN} 的角色位） */
    private static final int EMERALD_PRESET = 1;
    /** 成品交易箱配色在 {@code ColorPresets} 里的下标（「青」，旧 {@code Color.CYAN} 的角色位） */
    private static final int UNLOAD_PRESET = 3;
    /** 不透明度：与旧项目 ESP 一致，画的是实心描边 */
    private static final int PRESET_ALPHA = 0xFF;

    /** 绿宝石箱线框色（绿） */
    private static final EspColor EMERALD_COLOR = EspColor.preset(EMERALD_PRESET, PRESET_ALPHA);
    /** 成品交易箱线框色（青） */
    private static final EspColor UNLOAD_COLOR = EspColor.preset(UNLOAD_PRESET, PRESET_ALPHA);

    private final Minecraft mc = Minecraft.getInstance();

    public ContainerESP() {
    }

    /**
     * 渲染容器标记（旧 {@code render :31-46} 逐字）。
     *
     * <p><b>做什么</b>：绿宝石箱 / 成品交易箱各有绑定时，各画一个合并包围盒的线框。</p>
     * <p><b>为什么先判玩家与世界</b>：相机位置与方块状态都取自客户端世界，未进世界时什么也算不出来
     * （旧实现同一前置守卫）。</p>
     * <p><b>关键判据</b>：只在绑定（{@code getEmeraldBox() / getUnloadBox()} 非 {@code null}）时画 ——
     * 未绑定的箱子不该有标记；绑定视图由仓库每次访问现取，因此解除绑定后下一帧即消失。</p>
     */
    public void render(EspRenderer renderer) {
        // 全局「各模块 ESP」总闸（用户 2026-09-18）
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.VILLAGER)) return;
        if (mc.player == null || mc.level == null) return;

        VillagerBindingStore.ContainerBinding binding = VillagerBindingStore.getBinding();

        // 渲染绿宝石箱
        if (binding.getEmeraldBox() != null) {
            renderLabel(renderer, binding.getEmeraldBox(), EMERALD_COLOR);
        }

        // 渲染成品交易箱
        if (binding.getUnloadBox() != null) {
            renderLabel(renderer, binding.getUnloadBox(), UNLOAD_COLOR);
        }
    }

    /**
     * 渲染单个箱子线框：自动识别大箱子，画合并后的 2x1x1 包围盒
     * （旧 {@code renderLabel :51-81}，颜色由调用点给出而不是靠文本里是否含「绿宝石」猜）。
     *
     * <p><b>为什么按原坐标算距离而不是按合并框中心</b>：旧实现取的是绑定那一格的方块中心
     * （{@code pos + 0.5}）到玩家眼睛的距离，随格数判 64 格门限；这里保持同一判据，
     * 免得大箱子因中心外移半格而在极限距离上忽隐忽现。</p>
     * <p><b>关键判据</b>：距离 &gt; {@link #RENDER_DISTANCE} 直接返回；线框恒定 {@link ShapeMode#Lines}
     * （旧值），不填面。</p>
     *
     * @param color 线框色（绿宝石箱绿 / 成品交易箱青，同旧 {@code Color.GREEN} / {@code Color.CYAN} 的角色位）
     */
    private void renderLabel(EspRenderer renderer, BlockPos pos, EspColor color) {
        Vec3 playerPos = mc.player.getEyePosition();
        Vec3 targetPos = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        if (playerPos.distanceTo(targetPos) > RENDER_DISTANCE) return;

        // 大箱子（TYPE 为 LEFT/RIGHT 时是双箱子的一半）：沿 getConnectedDirection 找另一半，两边取并集
        AABB box = unionBox(pos, connectedChestHalf(pos));

        renderer.box(box, color, color, ShapeMode.Lines, LINE_THICKNESS);
    }

    /**
     * 大箱子时返回另一半的坐标；单箱、非箱子方块或方块状态取不到时返回 {@code null}
     * （旧 {@code :62-70} 原样，含 {@code state.getBlock() instanceof ChestBlock} 前置判定）。
     *
     * <p>旧实现不校验相邻那格是否真是箱子（{@code INSTANCE} 化或错位状态下可能取到空气格），
     * 本项目同样不校验：多算的半格只是线框宽一格，不会画错坐标。</p>
     */
    private BlockPos connectedChestHalf(BlockPos pos) {
        BlockState state = mc.level.getBlockState(pos);
        if (!(state.getBlock() instanceof ChestBlock)) return null;
        ChestType type = state.getValue(ChestBlock.TYPE);
        if (type == ChestType.SINGLE) return null;
        Direction connected = ChestBlock.getConnectedDirection(state);
        return pos.relative(connected);
    }

    /** 两格（或一格与它自己）的并集包围盒，端点各自 +1 覆盖整格（旧 {@code :72-77} 的 min/max 算法等价物） */
    private static AABB unionBox(BlockPos a, BlockPos b) {
        BlockPos other = b == null ? a : b;
        return new AABB(
            Math.min(a.getX(), other.getX()), Math.min(a.getY(), other.getY()), Math.min(a.getZ(), other.getZ()),
            Math.max(a.getX(), other.getX()) + 1.0, Math.max(a.getY(), other.getY()) + 1.0,
            Math.max(a.getZ(), other.getZ()) + 1.0);
    }
}
