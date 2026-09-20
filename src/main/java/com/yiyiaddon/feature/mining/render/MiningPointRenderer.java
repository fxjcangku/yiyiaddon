package com.yiyiaddon.feature.mining.render;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.PointLabelText;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

/**
 * 挖矿 ESP：三点位（矿物箱 / 食物箱 / 挂机修复点）标记 + 岩浆透视。
 *
 * <p>挂在本项目 {@code ui/render/world} 的 {@code WorldOverlay} 世界渲染层上：世界空间几何与字牌
 * 由 {@link EspRenderer} 封装，本类只描述「画什么」。线宽 / 不透明度 / 显示距离 /
 * 每帧图元上限全部走「ESP 全局设置」页，本类不另建第二套渲染配置。</p>
 *
 * <p><b>字牌字号、内容与颜色的归属</b>：字号一律乘模块设置 {@link MiningSettings#espScale}
 * （旧项目隐藏项，现已在控制台「点位」页可调）；容器（矿物箱 / 食物箱）的头顶文字再额外乘
 * {@link MiningSettings#espContainerTextScale}，颜色默认跟随该点位自己的方框色
 * （用户 2026-09-21：「不同颜色合理分配」——矿物箱金字、食物箱绿字），只有
 * {@link MiningSettings#espContainerTextColor} 非 0 时才以它为主色（用户 2026-09-17 追加的该项）。
 * <b>挂机修复点不是容器</b>：不乘容器倍率、不参与容器主色，字牌跟随自己的方框色；
 * 三个点位都写 {@code [世界]名字}（用户 2026-09-19 定稿：「全都要标上，除了那两个农场的选点区域
 * 之外都要标上」+「[主世界]点位名字 距离不要了」，排版同一天由用户给出）。</p>
 *
 * <p><b>呈现口径（含历史沿革）</b>：</p>
 * <ul>
 *   <li>旧项目标签文本 {@code AutoMinerModule.onRender2D}（旧 {@code :1099-1111}）给的是
 *       {@code §6[矿物箱] §7(维度)} 这类主体，{@code AutoMinerModule_ESP.renderLabel}
 *       （旧 {@code :31-65}）在其后**再追加**「当前维度名 + 距离」，最终显示
 *       {@code §6[矿物箱] §7(主世界) §7(主世界) §8[45m]}（维度名写两遍）。</li>
 *   <li>用户 2026-09-16 曾拍板「保留重复，一比一」；<b>2026-09-19 用户给出统一排版
 *       {@code [世界]名字} 后，本模块并入该排版，重复的维度名随之去掉</b>（后令覆盖前令）。
 *       距离段同样按用户 2026-09-19 后续指令「距离不要了」去掉。
 *       点位名（矿物箱 / 食物箱 / 挂机修复点）只留纯名字，方括号归 {@link PointLabelText#text} 的维度段用
 *       （用户 2026-09-19：「点位文字显示格式都改成 {@code [维度]名称}，名称不用带 {@code []}」）。</li>
 *   <li>点位方块描边框：旧项目点位只有浮空文字、无框线；本项目按自己的 ESP 语言加了描边框
 *       （<b>用户 2026-09-16 拍板：留着</b>），属 UI 呈现差异，文案与配色不变。
 *       2026-09-18 起框支持大箱子：双箱按原版连接方向并成 2×1×1 的整框（判据照星露谷农场的
 *       {@code StardewRenderState#connectedChestHalf}），标签同步移到合并框
 *       正上方中心（用户实机反馈「没渲染大箱子」+「要居中字体」），单箱与挂机修复点的画法与
 *       锚点跟原来完全一致。</li>
 *   <li>距离门限：{@code > 128} 格不画；距离口径为 {@code mc.player.position()} 到
 *       「方块中心上方 1.5 格」的直线距离（旧 {@code :34-37}）。</li>
 *   <li>岩浆颜色：填充与描边都是一对<b>深红</b>（用户 2026-09-19：「岩浆 esp 渲染默认改成深红色
 *       现在跟寻路线条撞色了」——上一版描边是亮青，与接管后的 Baritone 寻路主线撞在一起）；
 *       距离门限与每 10 tick 重扫一次同旧项目。渲染模式为 {@code Both}
 *       （底部可透出地形的深红块 + 深红棱线），详见 {@link #LAVA_SIDE} 的注释。</li>
 * </ul>
 */
public final class MiningPointRenderer {

    /** 渲染距离：超过这个距离的点位与岩浆不画（旧项目 {@code renderLabel} / {@code renderLava} 的 128 格） */
    private static final double RENDER_DISTANCE = 128.0;

    /**
     * 线框线宽（GUI 缩放坐标）；旧项目由框架渲染器固定，本项目在此显式给出。
     *
     * <p>2026-09-18 由 1.5 提到 3.0（用户：「把自动挖矿的 esp 加粗一下，跟男中音的做鲜明对比」）：
     * 男中音的路径线是 {@code pathRenderLineWidthPixels} 指定的<b>物理像素</b>（默认个位数），
     * 而这里的值还要乘 GUI 缩放（{@code EspRenderer#pixelWidth}），因此 3.0 在实际画面上明显更粗；
     * 想更粗仍可在「ESP 全局设置 ▸ 线宽倍率」（0.5~3.0）上再乘一档，不必再改本常量。</p>
     */
    private static final float LINE_THICKNESS = 3.0f;

    /** 标签高度偏移：方块顶面以上 0.5 格（旧项目 {@code pos.getY() + 1.5}） */
    private static final double LABEL_Y_OFFSET = 1.5;

    /** 岩浆重扫间隔（tick）：避免每帧全量扫方块（旧项目 {@code :1122}） */
    private static final int LAVA_SCAN_INTERVAL = 10;

    /**
     * 岩浆固定配色：填充（半透明）/ 描边（不透明）。
     *
     * <p><b>2026-09-18</b>（用户：「透视岩浆还是没框，那种纯色的方块裸露的没显示」）：描边曾用
     * {@code 0xFF5A00}，与岩浆自身的橙红几乎同色，于是只有被石头埋着的岩浆看得到框、裸露岩浆整片糊住；
     * 当时改成亮青 {@code 0x36E2FF}，靠<b>色相</b>拉开对比。</p>
     *
     * <p><b>2026-09-19</b>（用户：「岩浆 esp 渲染默认改成深红色 现在跟寻路线条撞色了」）：亮青那条反而和
     * 接管后的 Baritone 寻路主线（薄荷青 {@code 0x63C9B8}）撞了 —— 寻路时满屏都是青线，岩浆框混在里面
     * 认不出来。现换成一对<b>深红</b>：给岩浆的固有印象色，且与现有 ESP 主色（青 / 蓝 / 绿 / 琥珀）
     * 都不同族；与岩浆本体则靠<b>亮度差</b>分开（深红亮度约为岩浆的 1/4）—— 裸露岩浆上是深红棱线压橙红面，
     * 被埋的岩浆上是深红棱线压灰石面，两边都还有对比。填充同时从 {@code 0xFF3200 @55} 收深到深红，
     * 让整块岩浆透出「这是危险格」的暗红体感，而不是原来那种几乎看不见的橙红。</p>
     */
    private static final EspColor LAVA_SIDE = new EspColor(0x7A0F12, 95);
    private static final EspColor LAVA_LINE = new EspColor(0xC02832, 245);

    /** 岩浆框线宽：比点位框粗一档（点位框 3.0 → 此处 4.5），保证在明亮岩浆面上也能看清棱线 */
    private static final float LAVA_LINE_THICKNESS = 4.5f;

    private final Minecraft mc = Minecraft.getInstance();
    private final AutoMinerModule module;

    /**
     * 上一次岩浆扫描的 tick（旧项目 {@code lastLavaScanTick}）。
     *
     * <p><b>本轮修正</b>（用户 2026-09-17：「透视岩浆 ESP 好像没生效」）：旧值用
     * {@link Integer#MIN_VALUE} 作「从未扫描」哨兵，而 {@code tick - lastLavaScanTick} 会
     * <b>int 溢出成负数</b>（如 {@code 5000 - (-2147483648) = -2147478648}），
     * 于是 {@code >= LAVA_SCAN_INTERVAL} 永远不成立、{@code lastLavaScanTick} 也永远不更新
     * ——岩浆透视一次都不会画。现用显式哨兵判断，避免参与减法。</p>
     */
    private int lastLavaScanTick = Integer.MIN_VALUE;

    /** 岩浆位置缓存（旧项目 {@code cachedLavaPositions}） */
    private Set<BlockPos> cachedLavaPositions = Set.of();

    public MiningPointRenderer(AutoMinerModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        // 全局「各模块 ESP」总闸（用户 2026-09-18，ESP 全局设置页 ▸ 各模块 ESP）：
        // 关掉挖矿这一层时整层不画（点位框 + 岩浆框），模块自身的设置与运行状态一概不动
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.MINING)) return;
        if (mc.player == null || mc.level == null) return;

        MiningSettings settings = module.settings();
        draw(renderer, MiningPointType.MINERAL, "矿物箱", settings.renderMineralBox);
        draw(renderer, MiningPointType.FOOD, "食物箱", settings.renderFoodBox);
        draw(renderer, MiningPointType.AFK, "挂机修复点", settings.renderAfkPoint);

        renderLava(renderer);
    }

    // ── 三点点位 ──

    /**
     * 画单条点位：方块线框 + 浮空标签（仅本维度已绑定、128 格内）。
     *
     * <p>显示开关、颜色与渲染模式全部读该点位自己的渲染对象 {@code object}（用户 2026-09-19 起与
     * 星露谷点位同款）：关掉一类不影响其余两类，渲染模式（线框 / 面 / 两者）也各自独立。</p>
     */
    private void draw(EspRenderer renderer, MiningPointType type, String labelHead, EspRenderObject object) {
        if (!object.show) return;
        MiningPoint point = module.pointStore().get(type);
        if (point == null || !point.inCurrentDimension()) return;

        // 大箱子（双箱）适配：只框绑定那一格时画面上只有半个箱子被框住（用户 2026-09-18 实机反馈
        // 「没有渲染大箱子，没适配」）。这里把两格并成一个包围盒，标签也移到合并框的正上方中心，
        // 于是框与字都对着整只箱子居中（用户同一条反馈的后半句「要居中字体」）。
        AABB box = pointBox(point);
        Vec3 center = box.getCenter();
        Vec3 labelPos = new Vec3(center.x, point.y() + LABEL_Y_OFFSET, center.z);
        double distance = mc.player.position().distanceTo(labelPos);
        if (distance > RENDER_DISTANCE) return;

        renderer.box(box, object.color, object.color, object.mode, LINE_THICKNESS);

        // 容器（矿物箱 / 食物箱）的头顶文字走模块内两项追加设置：额外字号倍率 + 可选统一主色；
        // 挂机修复点不是容器，不乘容器倍率、不受容器主色影响。
        MiningSettings settings = module.settings();
        boolean container = type != MiningPointType.AFK;
        float size = (float) (settings.espScale * (container ? settings.espContainerTextScale : 1.0));

        // 三个点位统一排版「[世界]名字」（用户 2026-09-19 定稿，取代旧项目那份「维度名写两遍 + 距离」的
        // 原文输出），样式走共用件（加粗 + 底板 + 居中）。颜色（用户 2026-09-21：「不同颜色合理分配」）：
        // 跟随各自点位的方框色（矿物箱金字、食物箱绿字、挂机修复点品红字），改方框色即改字色；
        // 只有容器才受「容器标签文字颜色」约束 —— 该项非 0 时以它为先，0 表示跟随各自的方框色。
        int override = container && settings.espContainerTextColor != 0
            ? settings.espContainerTextColor : object.color.currentRgb();
        PointLabelText.rawLabel(renderer, PointLabelText.text(labelHead, point.dimension()),
            labelPos.x, labelPos.y, labelPos.z, size, override);
    }

    /**
     * 点位方块的世界包围盒：大箱子（双箱）沿原版连接方向并成 2×1×1 的整框，其余方块就是自身一格。
     *
     * <p><b>判据照星露谷农场那套实现</b>
     * （{@code stardew/render/StardewRenderState#renderPointBox} / {@code #connectedChestHalf}，
     * 用户 2026-09-18 指定参考它）：非双箱时只框自己那一格，双箱才把两格并成一个 {@link AABB}。
     * 两格各画一框会在中间叠出两条棱，很难看 —— 这是那段代码写合并的原始理由，本模块同理。</p>
     */
    private AABB pointBox(MiningPoint point) {
        BlockPos pos = point.pos();
        BlockPos half = connectedChestHalf(pos);
        return half == null ? new AABB(pos) : unionBox(pos, half);
    }

    /**
     * 大箱子时返回另一半的坐标；单箱、非箱子方块、或相邻那格不是箱子时返回 {@code null}
     * （判据与 {@code StardewRenderState#connectedChestHalf} 逐条一致）。
     *
     * <p>方块类型判 {@link ChestBlock}：铜箱 {@code CopperChestBlock} 继承它，一并覆盖。
     * 额外校一次「相邻那格确实是箱子」是为了排除错位 / 被拆掉一半等异常状态 ——
     * 那时 {@code ChestBlock.TYPE} 仍可能是 {@code LEFT/RIGHT}，照连会让框凭空宽出一格。</p>
     */
    private BlockPos connectedChestHalf(BlockPos pos) {
        if (mc.level == null) return null;
        BlockState state = mc.level.getBlockState(pos);
        if (!(state.getBlock() instanceof ChestBlock) || state.getValue(ChestBlock.TYPE) == ChestType.SINGLE) {
            return null;
        }
        BlockPos connected = pos.relative(ChestBlock.getConnectedDirection(state));
        return mc.level.getBlockState(connected).getBlock() instanceof ChestBlock ? connected : null;
    }

    /** 两格方块的外包围方框（角点取两格并集，端点各自 +1 覆盖整格） */
    private static AABB unionBox(BlockPos a, BlockPos b) {
        return new AABB(
            Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()),
            Math.max(a.getX(), b.getX()) + 1.0, Math.max(a.getY(), b.getY()) + 1.0, Math.max(a.getZ(), b.getZ()) + 1.0);
    }

    // ── 岩浆透视 ──

    /** 岩浆透视：按需重扫 + 逐块画框（旧 {@code onRender3D :1119-1129}） */
    private void renderLava(EspRenderer renderer) {
        if (!module.settings().lavaEsp) return;

        int tick = mc.player.tickCount;
        if (lastLavaScanTick == Integer.MIN_VALUE || tick - lastLavaScanTick >= LAVA_SCAN_INTERVAL) {
            lastLavaScanTick = tick;
            cachedLavaPositions = scanLava(module.settings().lavaEspRange);
        }
        if (cachedLavaPositions.isEmpty()) return;

        BlockPos center = mc.player.blockPosition();
        for (BlockPos pos : cachedLavaPositions) {
            if (center.distSqr(pos) > RENDER_DISTANCE * RENDER_DISTANCE) continue;
            renderer.blockBox(pos.getX(), pos.getY(), pos.getZ(), LAVA_SIDE, LAVA_LINE,
                ShapeMode.Both, LAVA_LINE_THICKNESS);
        }
    }

    /** 扫描玩家周围 radius 格的岩浆方块位置（旧 {@code scanLava :1150-1164} 逐字） */
    private Set<BlockPos> scanLava(int radius) {
        Set<BlockPos> result = new HashSet<>();
        if (mc.level == null) return result;
        BlockPos center = mc.player.blockPosition();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (mc.level.getBlockState(pos).getBlock() == Blocks.LAVA) {
                        result.add(pos);
                    }
                }
            }
        }
        return result;
    }
}
