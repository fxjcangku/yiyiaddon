package com.yiyiaddon.seed.ore;

import com.yiyiaddon.seed.model.OreType;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 种子挖矿正式模块 · <b>一条矿物定义</b>（正式化第八阶段 236；26.2 语义移植）。
 *
 * <p><b>它是什么</b>：{@code (维度, 矿物)} 这一个组合的<b>唯一真源</b>。它把「这种矿在这个维度里
 * 长什么样、由哪几条 vanilla 地物产生、要扫描哪一段 Y、什么方块算命中」全部收在一处，
 * 供读取器（{@link com.yiyiaddon.seed.worldgen.OreChunkReader}）、观察层
 * （{@link com.yiyiaddon.seed.observation.SeedOreObservationTracker}）、UI 与报告共用。</p>
 *
 * <p><b>它不是什么</b>：它<b>不实现</b>任何世界生成算法。矿物到底长在哪里，永远由 Worker 里
 * 真实的 vanilla {@code applyBiomeDecoration} 决定（236 沿用既定路线：
 * 预测 = 在隔离进程里跑原生 worldgen，而不是重写一遍 OreFeature）。
 * 本记录里的 {@code configuredFeatures} / {@code placedFeatures} / 频率与高度文字
 * 是<b>对照来源</b>：它们让「我们按哪套规则算的」可被逐条核对，而不是让代码去复述算法。</p>
 *
 * <p><b>扫描窗口为什么要有</b>：读取器每次预测要对目标区块逐 section 扫描很多遍
 * （每个 viewer 前后各一次），范围越准成本越低。窗口由 vanilla 的高度区间
 * <b>加矿脉自身半径余量</b>得出，并且<b>必须覆盖该矿物所有写入路径</b>：
 * 例如铁的上界来自 {@code ore_iron_upper} 的三角分布（80~384），下界来自矿脉的 y −60，
 * 因此窗口是整列；红石两段都在低位，窗口就只有 6 个 section。</p>
 *
 * @param oreType            矿物种类
 * @param dimension          维度档案
 * @param blocks             该矿物在本维度算作命中的方块（含深层变种；同矿不同维度可以不同）
 * @param configuredFeatures 对应的 vanilla configured_feature 标识（对照用）
 * @param placedFeatures     对应的 vanilla placed_feature 标识（对照用）
 * @param frequencyCn        频率的中文描述（{@code CountPlacement} / {@code RarityFilter}，对照用）
 * @param heightCn           高度区间的中文描述（对照用）
 * @param biomeScopeCn       生物群系范围的中文描述（{@link net.minecraft.world.level.levelgen.placement.BiomeFilter}
 *                           会按目标生物群系自己的 {@code BiomeGenerationSettings} 过滤，对照用）
 * @param discardChanceMaxCn 空气暴露丢弃率的中文描述（{@code OreConfiguration.discardChanceOnAirExposure}，对照用）
 * @param scanMinY           扫描窗口下界（绝对 Y，含；已含矿脉半径余量）
 * @param scanMaxY           扫描窗口上界（绝对 Y，含；已含矿脉半径余量）
 * @param writePaths         本维度里该矿物的全部写入路径（见 {@link SeedOreWritePath}）
 * @param autoMinerEligible  是否已具备进入 AutoMiner 的资格证据（236 只有钻石为 true，其余一律 false）
 * @param evidenceCn         参数出处（源码文件与行号；「不凭记忆写参数」的落点）
 */
public record SeedOreDefinition(OreType oreType, SeedDimensionProfile dimension, List<Block> blocks,
                                List<String> configuredFeatures, List<String> placedFeatures,
                                String frequencyCn, String heightCn, String biomeScopeCn,
                                String discardChanceMaxCn, int scanMinY, int scanMaxY,
                                Set<SeedOreWritePath> writePaths, boolean autoMinerEligible,
                                String evidenceCn) {

    public SeedOreDefinition {
        Objects.requireNonNull(oreType, "oreType");
        Objects.requireNonNull(dimension, "dimension");
        blocks = List.copyOf(blocks);
        configuredFeatures = List.copyOf(configuredFeatures);
        placedFeatures = List.copyOf(placedFeatures);
        writePaths = Set.copyOf(writePaths);
        Objects.requireNonNull(evidenceCn, "evidenceCn");
        if (scanMinY > scanMaxY) {
            throw new IllegalArgumentException(oreType + " 的扫描窗口上下界颠倒：" + scanMinY + " > " + scanMaxY);
        }
    }

    /** 该方块状态是否满足本矿物（{@code diamond_ore} 与 {@code deepslate_diamond_ore} 都算钻石）。 */
    public boolean matches(BlockState state) {
        if (state == null) {
            return false;
        }
        for (int index = 0; index < blocks.size(); index++) {
            if (state.is(blocks.get(index))) {
                return true;
            }
        }
        return false;
    }

    /** 方块清单的中文摘要（界面 / 报告用）。 */
    public String blocksCn() {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < blocks.size(); index++) {
            if (index > 0) {
                builder.append(" / ");
            }
            builder.append(blocks.get(index).getName().getString());
        }
        return builder.toString();
    }

    /** 写入路径的中文摘要。 */
    public String writePathsCn() {
        StringBuilder builder = new StringBuilder();
        for (SeedOreWritePath path : SeedOreWritePath.values()) {
            if (!writePaths.contains(path)) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(" + ");
            }
            builder.append(path.displayNameCn());
        }
        return builder.toString();
    }

    /** 扫描窗口的中文摘要。 */
    public String scanWindowCn() {
        return "y ∈ [" + scanMinY + ", " + scanMaxY + "]";
    }

    /** 一行中文摘要（日志 / 报告用）。 */
    public String describeCn() {
        return dimension.displayNameCn() + " · " + oreType.displayNameCn() + "：" + blocksCn()
                + "；来源 " + writePathsCn() + "；扫描 " + scanWindowCn()
                + "；频率 " + frequencyCn + "；高度 " + heightCn;
    }
}
