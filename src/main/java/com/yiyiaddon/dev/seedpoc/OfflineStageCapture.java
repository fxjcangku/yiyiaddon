package com.yiyiaddon.dev.seedpoc;

import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * 第四轮 · 离线侧 pre-diamond 捕获。
 *
 * <p><b>捕获点与真实侧逐字相同</b>：{@code PlacedFeature#placeWithBiomeCheck} 的 HEAD，
 * 且只在该 feature 是钻石四条之一、且该区块还没抓过时抓一次。
 * 真实侧在第三轮用的是同一条件（{@link GenStageCapture#onBeforePlacedFeature}），
 * 因此两侧的「第一条钻石 placed_feature 执行前」是同一个语义时点，可以直接逐 BlockPos 比。</p>
 *
 * <p><b>为什么由探针「接管」而不是加开关</b>：离线 pipeline 跑 FEATURES 时会真的调用原版
 * {@code applyBiomeDecoration} → {@code placeWithBiomeCheck}，于是第三轮那三个真实侧探针
 * （GenStageCapture / DiamondFeatureOracle / OreVeinTrace）都会被触发。这里让
 * {@link #onFeatureHead} 对「离线 region」一律返回 true，探针据此直接跳过真实侧记录——
 * 这样真实侧的 oracle 绝不会被离线生成污染（离线区域只可能出现在离线链路上，
 * 所以这个判定不会误伤真实生成）。</p>
 */
public final class OfflineStageCapture {

    /** 离线快照自己的方块状态编号表（与真实侧分开，避免编号互相干扰）。 */
    private static final StatePalette PALETTE = new StatePalette();

    /** 各区块「离线：第一条钻石 feature 执行前」的快照。 */
    private static final Map<Long, GenStageSnapshot> PRE_DIAMOND = new HashMap<>();

    /** 当前观察窗口的中心区块；null = 没有窗口（此时只接管、不记录）。 */
    private static volatile ChunkPos armedViewer;

    /** 捕获序号（离线侧自己的序列）。 */
    private static int order;

    /** 钻石四条的身份集合（按注册表实例比对，避免值比较）。 */
    private static volatile Set<PlacedFeature> diamondFeatures;

    /** 捕获次数统计（报告用）。 */
    private static int captures;
    private static int featureEvents;

    private OfflineStageCapture() {
    }

    /** 打开观察窗口：只记录这个区块的 pre-diamond。 */
    static void beginViewer(ChunkPos viewer) {
        armedViewer = viewer;
        PRE_DIAMOND.remove(viewer.pack());
    }

    /** 关闭观察窗口。 */
    static void endViewer() {
        armedViewer = null;
    }

    /** 取走某区块的离线 pre-diamond 快照（取走即移除）。 */
    static GenStageSnapshot take(ChunkPos viewer) {
        return PRE_DIAMOND.remove(viewer.pack());
    }

    /** 已捕获的离线 pre-diamond 份数。 */
    static int capturedCount() {
        return captures;
    }

    /** 见过的 placed_feature 调用次数（报告用：为 0 说明离线 FEATURES 根本没跑到）。 */
    static int featureEvents() {
        return featureEvents;
    }

    /**
     * 探针入口：{@code PlacedFeature#placeWithBiomeCheck} HEAD。
     *
     * @return true = 本次调用属于离线链路（真实侧探针必须跳过）；false = 与离线无关，照常走真实侧。
     */
    public static boolean onFeatureHead(WorldGenLevel level, BlockPos origin, PlacedFeature feature) {
        if (!(level instanceof OfflineChunkRegion region)) {
            return false;
        }
        featureEvents++;
        ChunkPos viewer = armedViewer;
        if (viewer == null || !viewer.equals(region.getCenter())) {
            return true;
        }
        if (!isDiamondFeature(region, feature) || PRE_DIAMOND.containsKey(viewer.pack())) {
            return true;
        }
        GenStageSnapshot snapshot = StageSnapshotCapture.capture(region, viewer,
                "离线：第一条钻石 placed_feature 执行前", PALETTE, ++order);
        PRE_DIAMOND.put(viewer.pack(), snapshot);
        captures++;
        return true;
    }

    /** 是否属于离线链路的调用（真实侧探针用它做 RETURN 侧对称跳过）。 */
    public static boolean isOfflineCall(WorldGenLevel level) {
        return level instanceof OfflineChunkRegion;
    }

    /** 是否钻石四条之一（按注册表实例身份比对）。 */
    private static boolean isDiamondFeature(WorldGenLevel level, PlacedFeature feature) {
        Set<PlacedFeature> diamonds = diamondFeatures;
        if (diamonds == null) {
            diamonds = Collections.newSetFromMap(new IdentityHashMap<>());
            var registry = level.registryAccess().lookupOrThrow(Registries.PLACED_FEATURE);
            for (String id : SeedPocConstants.DIAMOND_PLACED_FEATURES) {
                Holder.Reference<PlacedFeature> holder = registry.getOrThrow(ResourceKey.create(
                        Registries.PLACED_FEATURE, Identifier.withDefaultNamespace(id)));
                diamonds.add(holder.value());
            }
            diamondFeatures = diamonds;
        }
        return diamonds.contains(feature);
    }

    /** 报告用一行摘要。 */
    static String describe() {
        return "离线 pre-diamond 捕获：" + captures + " 份；离线 FEATURES 期间的 placed_feature 调用 "
                + featureEvents + " 次；方块状态编号表 " + PALETTE.size() + " 种";
    }
}
