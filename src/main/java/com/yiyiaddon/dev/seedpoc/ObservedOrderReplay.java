package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿 PoC 第七轮 · Debug-only「按观测顺序重放」（<b>因果实验装置，不是预测器</b>）。
 *
 * <p><b>它允许什么、禁止什么</b>（用户口径第十八、十九节）：</p>
 * <ul>
 *     <li>允许：把「某一个真实世界里观测到的 viewer / feature 先后」当作输入，
 *         在共享离线世界里按同一个顺序执行相关 viewer 的 {@code FEATURES}；</li>
 *     <li>禁止：被正式 Predictor（{@link TargetChunkPredictor}）、正式业务、UI 引用。
 *         本类只被 {@link Round7Runner} 调用，属于一次性因果实验装置。</li>
 * </ul>
 *
 * <p><b>为什么它能证明因果</b>：同一份离线世界（同种子、同注册表、同 worldgen 配置）里，
 * 唯一变化的量就是 viewer 的执行先后。若只改这个顺序就得到不同的最终钻石集合，
 * 那就说明「最终集合不是 Seed 的纯函数，还依赖执行历史」——这正是本轮要判定的问题。</p>
 */
final class ObservedOrderReplay {

    private ObservedOrderReplay() {
    }

    /**
     * 按给定顺序在共享离线世界里重放，返回目标区块最终的钻石集合。
     *
     * @param host   环境宿主（只提供维度类型 / 世界高度 / 注册表 / 模板管理器 / 调色板工厂）
     * @param seed   被试种子
     * @param target 目标区块
     * @param order  viewer 执行顺序（必须覆盖写半径内的全部 viewer，否则结果不可比）
     */
    static Outcome replay(ServerLevel host, long seed, ChunkPos target, List<ChunkPos> order) {
        OfflinePredictionSession session = OfflinePredictionSession.open(host, seed);
        session.prepareFor(target, TargetViewerSet.writeRadius());
        List<String> executed = new ArrayList<>();
        for (ChunkPos viewer : order) {
            session.decorate(viewer);
            executed.add("(" + viewer.x() + "," + viewer.z() + ")");
        }
        Set<BlockPos> diamonds = FinalOreCollector.collect(session.finalChunk(target)).all();
        return new Outcome(Set.copyOf(diamonds), List.copyOf(executed));
    }

    /**
     * 一次重放的产物。
     *
     * @param diamonds 目标区块最终的钻石集合
     * @param executed 实际执行顺序（文本，进报告）
     */
    record Outcome(Set<BlockPos> diamonds, List<String> executed) {

        /** 集合摘要（跨重放比对用）。 */
        String digest() {
            return "h=" + Integer.toHexString(diamonds.hashCode()) + " n=" + diamonds.size();
        }

        /** 执行顺序文本。 */
        String orderCn() {
            return String.join(" → ", executed);
        }
    }

    /**
     * 把「观测到的装饰先后」转成 viewer 执行顺序。
     *
     * <p>口径：批号越小越先（批号 = 进入 {@code applyBiomeDecoration} 的先后）。
     * 没有批号的 viewer（= 实验窗口开始前就已经生成完毕的区块）排在最前，并在报告里如实标注——
     * 它们真实世界里确实先于本窗口存在。</p>
     */
    static List<ChunkPos> observedOrder(ChunkPos target, Map<ChunkPos, Integer> batches) {
        List<ChunkPos> viewers = TargetViewerSet.viewers(target);
        Map<ChunkPos, Integer> effective = new LinkedHashMap<>();
        for (ChunkPos viewer : viewers) {
            Integer batch = batches.get(viewer);
            effective.put(viewer, batch == null ? Integer.MIN_VALUE : batch);
        }
        List<ChunkPos> ordered = new ArrayList<>(viewers);
        ordered.sort((left, right) -> {
            int leftBatch = effective.get(left);
            int rightBatch = effective.get(right);
            if (leftBatch != rightBatch) {
                return Integer.compare(leftBatch, rightBatch);
            }
            if (left.x() != right.x()) {
                return Integer.compare(left.x(), right.x());
            }
            return Integer.compare(left.z(), right.z());
        });
        return List.copyOf(ordered);
    }

    /** 反事实顺序：把两个指定 viewer 的真实先后对调（其余保持观测顺序）。 */
    static List<ChunkPos> swapOrder(List<ChunkPos> observed, ChunkPos first, ChunkPos second) {
        List<ChunkPos> swapped = new ArrayList<>(observed);
        int firstIndex = swapped.indexOf(first);
        int secondIndex = swapped.indexOf(second);
        if (firstIndex < 0 || secondIndex < 0) {
            return swapped;
        }
        swapped.set(firstIndex, second);
        swapped.set(secondIndex, first);
        return List.copyOf(swapped);
    }

    /** 反事实顺序：整体倒序（没有指定交换对时的兜底口径）。 */
    static List<ChunkPos> reversedOrder(List<ChunkPos> observed) {
        List<ChunkPos> reversed = new ArrayList<>(observed);
        java.util.Collections.reverse(reversed);
        return List.copyOf(reversed);
    }
}
