package com.yiyiaddon.feature.bonemeal.service;

import com.yiyiaddon.feature.bonemeal.config.BonemealSettings;
import com.yiyiaddon.feature.bonemeal.config.BonemealTexts;
import com.yiyiaddon.feature.bonemeal.config.TargetList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 目标识别器：把「周围哪些方块能催熟」这件事从模块主类里独立出来。
 *
 * <p><b>逐字搬运</b>：方法体逐字照旧项目 {@code bonemeal/AutoBoneMeal.java}
 * 的 {@code collectTargets:475-484} / {@code getCrosshairTarget:487-511} / {@code scanRange:514-533} /
 * {@code isTargeted:543-549} / {@code isFertilizable:551-554} / {@code isOccluded:557-570}。
 * 三重循环的坐标顺序（dx → dy → dz）、半径平方判据、眼睛到方块中心的距离判据、
 * 遮挡射线的 {@code 0.1} 容差、准星提示的去重字段与复位时机，全部一字未改。</p>
 *
 * <p><b>框架适配（旧 → 新，逐条）</b></p>
 * <ol>
 *     <li><b>目标名单</b>：旧 {@code isTargeted} 判 {@code List<Block>} 是否包含该方块；
 *         本项目设置里存的是登记 ID，故每轮收集前先把五组 ID 还原成 {@code Set<Block>}
 *         （认不出的 ID 跳过，不猜），判定语义不变。</li>
 *     <li><b>提示出口</b>：旧 {@code getCrosshairTarget} 直接调基类 {@code notify(...)}；
 *         识别器不持有播报能力，改为经 {@code hint} 回调把旧原文交给模块播报（措辞一字未改）。</li>
 *     <li><b>候选集合</b>：旧 {@code candidates} 是模块字段，本类按「写入调用方给的 List」实现，
 *         模块仍持有唯一的候选集合（渲染层与状态读数都读它，不留第二份）。</li>
 * </ol>
 *
 * <p><b>线程</b>：全部在主线程（客户端刻）调用；渲染线程与客户端刻是同一线程，渲染层直接读候选集合。</p>
 */
public final class BoneMealScanner {

    private final Minecraft mc = Minecraft.getInstance();

    /**
     * 上次已提示过的方块：防止准星停在同一方块上每 tick 刷屏
     * （旧 {@code lastHintedBlock}，离开后复位，下次换别的方块还能再提示）。
     */
    private Block lastHintedBlock;

    /**
     * 一次候选收集（旧 {@code collectTargets}）：准星模式取单点、范围模式扫半径，结果写入 {@code out}。
     *
     * @param settings 当前设置
     * @param out      候选输出集合（调用方已清空）
     * @param hint     提示出口：准星对着未登记的可催熟方块时，把旧原文交出去播报
     */
    public void collect(BonemealSettings settings, List<BlockPos> out, Consumer<String> hint) {
        Set<Block> targets = resolveTargets(settings);
        switch (settings.triggerMode) {
            case 准星精准指向 -> {
                // 准星没对准 → 不产出候选，本刻不动作
                BlockPos crosshair = crosshairTarget(settings, targets, hint);
                if (crosshair != null) out.add(crosshair);
            }
            case 范围自动扫描 -> scanRange(settings, targets, out);
        }
    }

    /** 清掉「上次提示过的方块」（旧 {@code onActivate} / {@code onDeactivate} 里的 {@code lastHintedBlock = null}） */
    public void resetHint() {
        lastHintedBlock = null;
    }

    // ── 候选收集 ──

    /** 准星命中的方块；不在目标列表或已成熟则返回 {@code null}（旧 {@code getCrosshairTarget}） */
    private BlockPos crosshairTarget(BonemealSettings settings, Set<Block> targets, Consumer<String> hint) {
        if (!(mc.hitResult instanceof BlockHitResult bhr)) return null;
        if (bhr.getType() != HitResult.Type.BLOCK) return null;
        BlockPos pos = bhr.getBlockPos();
        BlockState state = mc.level.getBlockState(pos);
        Block block = state.getBlock();

        // 准星提示：对着可催熟但不在目标列表的方块时，聊天框提示一次
        if (settings.crosshairHint
                && !isTargeted(targets, block)
                && block instanceof BonemealableBlock
                && block != lastHintedBlock) {
            lastHintedBlock = block;
            String name = BuiltInRegistries.BLOCK.getKey(block).getPath()
                .replace("_", " ");
            hint.accept(BonemealTexts.CROSSHAIR_UNLISTED_PREFIX + name
                + BonemealTexts.CROSSHAIR_UNLISTED_SUFFIX);
        }

        if (!isTargeted(targets, block)) return null;
        if (!isFertilizable(pos, state)) return null;

        // 离开后重置，下次换别的方块还能再提示
        lastHintedBlock = null;
        return pos;
    }

    /** 范围扫描：收集半径内所有合法目标（旧 {@code scanRange}，三重循环顺序原样） */
    private void scanRange(BonemealSettings settings, Set<Block> targets, List<BlockPos> out) {
        int r = settings.range;
        double rangeSq = settings.range * settings.range;
        BlockPos center = mc.player.blockPosition();

        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    Vec3 posCenter = Vec3.atCenterOf(pos);
                    if (mc.player.getEyePosition().distanceToSqr(posCenter) > rangeSq) continue;
                    BlockState state = mc.level.getBlockState(pos);
                    if (!isTargeted(targets, state.getBlock())) continue;
                    if (!isFertilizable(pos, state)) continue;
                    if (settings.checkOcclusion && isOccluded(posCenter)) continue;
                    out.add(pos);
                }
            }
        }
    }

    // ── 合法性校验 ──

    /** 该方块是否属于五组目标名单之一（旧 {@code isTargeted}） */
    private boolean isTargeted(Set<Block> targets, Block block) {
        return targets.contains(block);
    }

    /**
     * 调用 MC 原生 {@code BonemealableBlock#isValidBonemealTarget}：
     * 内部自动判断 Age 是否已满（成熟则返回 false），不浪费骨粉（旧 {@code isFertilizable}）。
     */
    private boolean isFertilizable(BlockPos pos, BlockState state) {
        if (!(state.getBlock() instanceof BonemealableBlock fert)) return false;
        return fert.isValidBonemealTarget(mc.level, pos, state);
    }

    /** 视线遮挡射线检测：眼睛 → 目标中心，中途撞到实体方块则跳过（旧 {@code isOccluded}） */
    private boolean isOccluded(Vec3 targetCenter) {
        Vec3 eye = mc.player.getEyePosition();
        HitResult hit = mc.level.clip(new ClipContext(
            eye, targetCenter,
            ClipContext.Block.COLLIDER,
            ClipContext.Fluid.NONE,
            mc.player
        ));
        if (hit.getType() == HitResult.Type.MISS) return false;
        if (hit instanceof BlockHitResult bhr) {
            return eye.distanceToSqr(bhr.getLocation()) < eye.distanceToSqr(targetCenter) - 0.1;
        }
        return false;
    }

    // ── 名单还原 ──

    /**
     * 五组登记 ID → 方块集合（旧 {@code targetCrops/targetSaplings/targetFlowers/targetMushrooms/
     * targetAquaticNether} 五个 {@code List<Block>} 的等价物）。
     *
     * <p>认不出的 ID 一律跳过，不猜、不抛（与 {@code MiningRegistry#blockOf} 同一口径）；
     * 每轮收集重建一次，成本是 5 组共约 40 次注册表查表，相对随后的半径扫描可忽略。</p>
     */
    private Set<Block> resolveTargets(BonemealSettings settings) {
        Set<Block> targets = new HashSet<>();
        for (TargetList list : TargetList.values()) {
            for (String id : list.of(settings)) {
                Block block = blockOf(id);
                if (block != null) targets.add(block);
            }
        }
        return targets;
    }

    /** 登记 ID → 方块；ID 非法或不存在返回 {@code null} */
    private static Block blockOf(String blockId) {
        if (blockId == null || blockId.isBlank()) return null;
        Identifier id = Identifier.tryParse(blockId);
        return id == null ? null : BuiltInRegistries.BLOCK.getValue(id);
    }
}
