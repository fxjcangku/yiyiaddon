package com.yiyiaddon.feature.mining.render;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
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
 * <p><b>字牌字号与容器标签主色的归属</b>：字号一律乘模块设置 {@link MiningSettings#espScale}
 * （旧项目隐藏项，现已在控制台「点位」页可调）；容器（矿物箱 / 食物箱）的头顶文字再额外乘
 * {@link MiningSettings#espContainerTextScale}，并在 {@link MiningSettings#espContainerTextColor}
 * 非 0 时改用该色作主色（用户 2026-09-17 追加的两项）。挂机修复点不是容器，两项都不参与。</p>
 *
 * <p><b>文案与门限逐字来自旧项目</b>：</p>
 * <ul>
 *   <li>标签文本 {@code AutoMinerModule.onRender2D}（旧 {@code :1099-1111}）给的是
 *       {@code §6[矿物箱] §7(维度)} 这类主体，{@code AutoMinerModule_ESP.renderLabel}
 *       （旧 {@code :31-65}）在其后**再追加**「当前维度名 + 距离」，因此最终显示为
 *       {@code §6[矿物箱] §7(主世界) §7(主世界) §8[45m]}。维度名重复是旧项目原样输出，
 *       <b>用户 2026-09-16 拍板：保留重复，一比一，不许"顺手修好"</b>。</li>
 *   <li>点位方块描边框：旧项目点位只有浮空文字、无框线；本项目按自己的 ESP 语言加了描边框
 *       （<b>用户 2026-09-16 拍板：留着</b>），属 UI 呈现差异，文案与配色不变。
 *       2026-09-18 起框支持大箱子：双箱按原版连接方向并成 2×1×1 的整框（判据照星露谷农场的
 *       {@code StardewRenderState#connectedChestHalf}），标签同步移到合并框
 *       正上方中心（用户实机反馈「没渲染大箱子」+「要居中字体」），单箱与挂机修复点的画法与
 *       锚点跟原来完全一致。</li>
 *   <li>距离门限：{@code > 128} 格不画；距离口径为 {@code mc.player.position()} 到
 *       「方块中心上方 1.5 格」的直线距离（旧 {@code :34-37}）。</li>
 *   <li>岩浆颜色：填充沿用旧项目（{@code 0xFF3200 @40} 系半透明岩浆红），描边改为亮青
 *       （旧项目的 {@code 0xFF5A00} 描边与岩浆同色，裸露岩浆上等于隐形，用户 2026-09-18 反馈）；
 *       距离门限与每 10 tick 重扫一次同旧项目。渲染模式为 {@code Both}
 *       （底部可透出地形的橙红块 + 清晰棱线），详见 {@link #LAVA_SIDE} 的注释。</li>
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
     * <p><b>本轮修正</b>（用户 2026-09-18：「透视岩浆还是没框，那种纯色的方块裸露的没显示」）：
     * 之前描边用的 {@code 0xFF5A00} 与岩浆自身的橙红几乎同色，于是<b>只有被石头埋着的岩浆</b>
     * （背景是灰石）看得到框，<b>裸露在外的岩浆</b>整片糊在一起——正是用户描述的现象。
     * 现按「填充保留旧项目的岩浆红、描边改成与岩浆互补的亮青」配成一对：对比度不依赖背景，
     * 埋着的和裸露的都一眼可见。</p>
     */
    private static final EspColor LAVA_SIDE = new EspColor(0xFF3200, 55);
    private static final EspColor LAVA_LINE = new EspColor(0x36E2FF, 245);

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

        draw(renderer, MiningPointType.MINERAL, "§6[矿物箱]", module.mineralColor());
        draw(renderer, MiningPointType.FOOD, "§2[食物箱]", module.foodColor());
        draw(renderer, MiningPointType.AFK, "§d[挂机修复点]", module.afkColor());

        renderLava(renderer);
    }

    // ── 三点点位 ──

    /** 画单条点位：方块线框 + 浮空标签（仅当前维度、128 格内） */
    private void draw(EspRenderer renderer, MiningPointType type, String labelHead, EspColor color) {
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

        renderer.box(box, color, color, ShapeMode.Lines, LINE_THICKNESS);

        // 容器（矿物箱 / 食物箱）的头顶文字走模块内两项追加设置：额外字号倍率 + 可选统一主色；
        // 挂机修复点不是容器，保持原口径（只乘 ESP 字号倍率、基准色取自己的颜色）。
        MiningSettings settings = module.settings();
        boolean container = type != MiningPointType.AFK;
        float size = (float) (settings.espScale * (container ? settings.espContainerTextScale : 1.0));
        int textColor = color.argb();
        String head = labelHead;
        if (container && settings.espContainerTextColor != 0) {
            textColor = settings.espContainerTextColor;
            // 必须摘掉标签头部的类型色码：MinecraftText 按 §x 逐段取色，色码会压过传入的基准色，
            // 不摘的话「容器标签文字颜色」就只能改到标签里的几个空格（表现为改了没反应）
            head = stripHeadColorCode(labelHead);
        }

        // 文字由 EspRenderer 在投影点水平居中绘制，因此锚点给合并框中心即可（大箱子也不会偏半格）
        renderer.text(labelText(head, point, distance), labelPos.x, labelPos.y, labelPos.z,
            size, textColor, 1f, true);
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

    /** 去掉标签头部的颜色码（{@code §6[矿物箱]} → {@code [矿物箱]}）；只用于覆盖色生效时（见 {@link #draw}） */
    private static String stripHeadColorCode(String text) {
        return text.length() >= 2 && text.charAt(0) == '§' ? text.substring(2) : text;
    }

    /**
     * 旧项目标签全文：主体 + 当前维度名 + 距离后缀（旧 {@code renderLabel :47-60} 逐字拼接）。
     *
     * <p>主体里的维度取**点位自身维度**（{@code WKData.dimensionName()}），追加段里的维度取
     * **当前世界维度**（{@code mc.level.dimension()}）——两者只在当前维度的点位上渲染，故实际一致。</p>
     */
    private String labelText(String labelHead, MiningPoint point, double distance) {
        return labelHead + " §7(" + dimensionName(point.dimension()) + ")"
            + " " + "§7(" + dimensionName(currentDimensionKey()) + ")"
            + " " + String.format("§8[%.0fm]", distance);
    }

    /** 当前世界维度键（旧 {@code mc.level.dimension().toString()} 的等价物，用本项目统一口径） */
    private String currentDimensionKey() {
        return mc.level == null ? "" : mc.level.dimension().identifier().toString();
    }

    /** 维度中文名（旧 {@code renderLabel :47-54} 的四种取值逐字） */
    private static String dimensionName(String dimension) {
        if (dimension == null) return "未知";
        if (dimension.contains("overworld")) return "主世界";
        if (dimension.contains("nether")) return "下界";
        if (dimension.contains("end")) return "末地";
        int colon = dimension.lastIndexOf(':');
        return colon < 0 ? dimension : dimension.substring(colon + 1);
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
