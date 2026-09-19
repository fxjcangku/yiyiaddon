package com.yiyiaddon.feature.villager.render;

import com.yiyiaddon.feature.villager.config.VillagerTradeSettings;
import com.yiyiaddon.feature.villager.repository.VillagerBindingStore;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.PointLabelText;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 容器 ESP 渲染器：给绑定的绿宝石箱与成品交易箱画方框 + 头顶字牌。
 *
 * <p>逐字迁移自旧项目 {@code villager/render/ContainerESP}（82 行）：两个绑定的判定、64 格距离门限、
 * 大箱子合并成一个包围盒的算法（{@code ChestBlock.TYPE} 非单箱时沿 {@code getConnectedDirection}
 * 找另一半，两边取并集）全部原样；绘制路线换成本项目既定的「EspRenderer + 原版 gizmo」基建
 * （开发习惯第 147 条），本类只描述「画什么」。</p>
 *
 * <p><b>调用关系</b>：模块 {@code AutoVillagerTradeModule} 在启用时把本类注册进
 * {@code WorldOverlay} 世界渲染层、关闭时注销（等价旧 {@code @EventHandler Render3DEvent} +
 * {@code if (!isActive()) return;}），由 {@link EspRenderer} 每帧回调 {@link #render(EspRenderer)}；
 * 本类不做任何交易决策，只读设置里的两个渲染对象。</p>
 *
 * <p><b>配色与渲染模式（用户 2026-09-19：把所有标点选择点位位置的模块 参照星露谷农场的点位设置）</b>：
 * 不再用类内写死的 {@code ColorPresets} 预设与 {@code ShapeMode.Lines}，改为逐类读模块设置里的
 * {@link EspRenderObject}——显示开关决定画不画、颜色（含彩虹）与渲染模式（线框 / 面 / 两者）
 * 由玩家在「点位」页自行设置。出厂默认仍是「绿宝石箱 = 绿、成品交易箱 = 青、线框」，
 * 与旧实现的观感一致（默认值定义在 {@link VillagerTradeSettings}，本类不持任何颜色字面量）。</p>
 *
 * <p><b>字牌（本次按用户要求补上）</b>：旧类注释写「在绑定的绿宝石箱和成品交易箱上方显示文字标签」，
 * 但实现里 {@code renderLabel} 只调 {@code event.renderer.box(...)}，收下的 {@code text} 参数仅用于
 * 判颜色（{@code text.contains("绿宝石")}），从未绘制文字（旧 {@code :51-81} 实测）。本次按用户要求
 * 补上字牌：两个箱子都是容器，写「[世界]名字」，样式统一走 {@link PointLabelText}
 * （加粗 + UI 主题色 + 底板，用户 2026-09-19：「跟随我的主题颜色同步切换」），字号取设置的「字牌大小」。</p>
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

    private final Minecraft mc = Minecraft.getInstance();

    /** 模块设置：两个渲染对象（显示 / 颜色 / 渲染模式）与字牌字号都从这里现读 */
    private final VillagerTradeSettings settings;

    public ContainerESP(VillagerTradeSettings settings) {
        this.settings = settings;
    }

    /**
     * 渲染容器标记（旧 {@code render :31-46} 逐字 + 本次补的字牌）。
     *
     * <p><b>做什么</b>：绿宝石箱 / 成品交易箱各有绑定时，各画一个合并包围盒 + 头顶字牌；
     * 该类的显示开关关掉就整类不画（颜色、渲染模式、字号都在 {@link #renderBox} 里现读）。</p>
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
        renderBox(renderer, binding.getEmeraldBox(), settings.renderEmeraldBox, "绿宝石箱",
            VillagerBindingStore.getEmeraldChestDimension());

        // 渲染成品交易箱
        renderBox(renderer, binding.getUnloadBox(), settings.renderUnloadBox, "成品交易箱",
            VillagerBindingStore.getUnloadChestDimension());
    }

    /**
     * 渲染单个绑定箱：自动识别大箱子，画合并后的 2x1x1 包围盒与头顶字牌
     * （旧 {@code renderLabel :51-81} 的绘制内容 + 本次补的字牌）。
     *
     * <p><b>为什么按原坐标算距离而不是按合并框中心</b>：旧实现取的是绑定那一格的方块中心
     * （{@code pos + 0.5}）到玩家眼睛的距离，随格数判 64 格门限；这里保持同一判据，
     * 免得大箱子因中心外移半格而在极限距离上忽隐忽现。</p>
     * <p><b>关键判据</b>：距离 &gt; {@link #RENDER_DISTANCE} 直接返回；方框的模式与颜色都取该对象
     * 自己的（出厂为 {@code Lines} + 绿 / 青，与旧实现观感一致，不再写死）。</p>
     *
     * @param object    该类渲染对象（显示开关 / 颜色 / 渲染模式）
     * @param label     字牌文本（绿宝石箱 / 成品交易箱）
     * @param dimension 该绑定的维度键（用于字牌里的维度名；未绑定时为 {@code null}）
     */
    private void renderBox(EspRenderer renderer, BlockPos pos, EspRenderObject object, String label,
                           ResourceKey<Level> dimension) {
        if (pos == null || !object.show) return;

        Vec3 playerPos = mc.player.getEyePosition();
        Vec3 targetPos = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        if (playerPos.distanceTo(targetPos) > RENDER_DISTANCE) return;

        // 大箱子（TYPE 为 LEFT/RIGHT 时是双箱子的一半）：沿 getConnectedDirection 找另一半，两边取并集
        AABB box = unionBox(pos, connectedChestHalf(pos));

        EspColor color = object.color;
        renderer.box(box, color.argb(), color.argb(), object.mode, LINE_THICKNESS);

        // 字牌：两个箱子都是容器，写「[世界]名字」（共用件 PointLabelText：加粗 + 主题强调色 +
        // 底板，用户 2026-09-19）；锚点取实际画出来的框中心（大箱子居中在并集框中心），
        // 字号取设置的「字牌大小」
        double centerX = (box.minX + box.maxX) * 0.5;
        double centerZ = (box.minZ + box.maxZ) * 0.5;
        double labelY = pos.getY() + 1.4;
        PointLabelText.containerLabel(renderer, label, dimension, centerX, labelY, centerZ, settings.labelSize);
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
