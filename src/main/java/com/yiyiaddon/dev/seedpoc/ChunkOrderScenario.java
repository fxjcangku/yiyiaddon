package com.yiyiaddon.dev.seedpoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿 PoC 第五轮 · Chunk 生成顺序场景定义。
 *
 * <p><b>本轮唯一要回答的问题</b>：同一个 Minecraft 版本、同一个世界 Seed、同一批目标区块、同一套原版
 * worldgen 配置，<b>只改变 Chunk 被请求（因而被装饰）的先后顺序</b>，最终生成完成后的钻石 BlockPos
 * 集合会不会变化？</p>
 *
 * <p><b>为什么场景不是「按区块逐个请求」</b>（这是本轮的第一个实测发现，必须先讲清楚）：
 * 26.1.2 里请求一个区块到 {@code FULL} 会建一个 {@code ChunkGenerationTask}，逐层铺
 * {@code EMPTY…FULL}，每层的覆盖半径 =
 * {@code ChunkPyramid.GENERATION_PYRAMID.getStepTo(FULL).getAccumulatedRadiusOf(该状态)}。
 * 用 {@code ChunkStep#buildAccumulatedDependencies} 逐级算出来，{@code FULL} 这一步的累积依赖是
 * {@code [FULL, INITIALIZE_LIGHT, CARVERS, BIOMES, STRUCTURE_STARTS×8]}，
 * 于是 {@code FEATURES} 的层半径 = <b>1</b>（{@code ChunkDependencies#getRadiusOf}）。
 * 也就是说：<b>「请求 1 个区块」在数据上等价于「装饰它自己 + 一圈邻域，共 3x3 个区块」</b>，
 * 原版根本不存在「把 3x3 里的某个区块单独挑出来装饰」这种操作。</p>
 *
 * <p>实测佐证：{@code run A1} 里第一条请求就让目标 3x3 的 9 个区块在<b>同一步</b>内全部拿到装饰批号，
 * 后续 8 次请求全部「本步新增 0 格」——说明它们已经装饰完了。</p>
 *
 * <p><b>于是「顺序」这个变量的可实施形式是</b>：改变「目标 3x3 里有哪些区块在目标自己之前被装饰」。
 * 做法是用<b>距目标 2 个区块</b>的请求（它的 3x3 覆盖目标的一圈邻域、但不覆盖目标本身）先把目标某一侧
 * 的邻域装饰掉，最后再请求目标区块。四个场景就是这样设计的，且都仍然<b>完全走原版流水线</b>
 * （{@code getChunk} → {@code ChunkMap} → {@code ChunkGenerationTask} → {@code ChunkStatusTasks}），
 * 没有一处手工伪造装饰顺序。</p>
 *
 * <p><b>坐标约定</b>：主世界 +X 为东、+Z 为南。因此「西侧」= 目标 3x3 里 x 减 1 的那一列。</p>
 */
public record ChunkOrderScenario(String id, String nameCn, ChunkPos target, List<ChunkPos> order) {

    /** 四个场景字母（A 中心优先 / B 邻域优先 / C 西侧先行 / D 东侧先行）。 */
    public static final List<String> LETTERS = List.of("A", "B", "C", "D");

    /**
     * 按「场景名 + 目标区块」构造一轮场景。
     *
     * <p>场景名格式：字母 + 重复序号（{@code A1} / {@code A2} / {@code B1}…）。字母决定请求顺序，
     * 序号只用于「同一请求顺序重复两个全新世界」这层对照（用户口径第二十三节）。</p>
     *
     * @param rawId  场景名（如 {@code A1}）
     * @param target 目标区块（3x3 的中心）
     */
    public static ChunkOrderScenario of(String rawId, ChunkPos target) {
        String id = rawId == null ? "" : rawId.trim().toUpperCase(Locale.ROOT);
        if (id.isEmpty()) {
            throw new IllegalArgumentException("场景名不能为空");
        }
        String letter = id.substring(0, 1);
        // 第七轮：允许用 -Dyiyiaddon.seedpoc.order.explicit=dx,dz;… 直接指定请求偏移序列。
        // 存在的理由：字母场景只有四组固定偏移，覆盖不到「把任意两个邻区块的真实先后调过来」，
        // 而请求方式仍然是原版 ServerLevel#getChunk，没有任何伪造装饰。
        List<long[]> explicit = SeedPocFlags.orderExplicit();
        List<int[]> offsets = explicit != null ? explicitOffsets(explicit) : offsetsOf(letter);
        List<ChunkPos> order = new ArrayList<>(offsets.size());
        for (int[] offset : offsets) {
            order.add(new ChunkPos(target.x() + offset[0], target.z() + offset[1]));
        }
        String name = explicit != null
                ? letter + "（显式偏移表）｜" + explicitText(offsets)
                : letter + "｜" + nameOf(letter);
        return new ChunkOrderScenario(id, name, target, List.copyOf(order));
    }

    /** 把系统属性里的显式偏移表转成偏移数组（最后一项必须是目标自己，否则顺序实验不成立）。 */
    private static List<int[]> explicitOffsets(List<long[]> raw) {
        List<int[]> offsets = new ArrayList<>();
        for (long[] pair : raw) {
            offsets.add(new int[]{(int) pair[0], (int) pair[1]});
        }
        return offsets;
    }

    /** 显式偏移表文本。 */
    private static String explicitText(List<int[]> offsets) {
        StringBuilder text = new StringBuilder();
        for (int[] offset : offsets) {
            if (text.length() > 0) {
                text.append(" → ");
            }
            text.append('(').append(offset[0]).append(',').append(offset[1]).append(')');
        }
        return text.toString();
    }

    /** 场景字母对应的中文说明。 */
    public static String nameOf(String letter) {
        return switch (letter) {
            case "A" -> "中心优先：不做任何预装饰，直接请求目标区块"
                    + "（目标与 8 个邻区块在同一次请求的同一层里被装饰，批内先后由原版调度决定）";
            case "B" -> "邻域优先：先用 8 个「距目标 2 区块」的请求把 8 个邻区块全部先装饰掉，"
                    + "最后才请求目标区块（目标之前 8 个邻区块全部已装饰）";
            case "C" -> "西侧先行：先用「距目标 2 区块」的请求把目标 3x3 的西侧一列装饰掉，"
                    + "再请求目标区块（西侧一列确定先于目标；其余邻区块与目标同层、批内先后由原版调度决定）";
            case "D" -> "东侧先行：先用「距目标 2 区块」的请求把目标 3x3 的东侧一列装饰掉，"
                    + "再请求目标区块（东侧一列确定先于目标；其余邻区块与目标同层、批内先后由原版调度决定）";
            default -> "未登记的场景字母";
        };
    }

    /** 场景字母 → 请求偏移序列（相对目标区块，最后一项一定是目标自己）。 */
    private static List<int[]> offsetsOf(String letter) {
        List<int[]> offsets = new ArrayList<>();
        switch (letter) {
            case "A" -> offsets.add(new int[]{0, 0});
            case "B" -> {
                // 8 个「距离 2」的中心：它们的 3x3 各自覆盖目标的一角/一边，但不覆盖目标本身
                offsets.add(new int[]{2, 2});
                offsets.add(new int[]{2, -2});
                offsets.add(new int[]{-2, 2});
                offsets.add(new int[]{-2, -2});
                offsets.add(new int[]{2, 0});
                offsets.add(new int[]{-2, 0});
                offsets.add(new int[]{0, 2});
                offsets.add(new int[]{0, -2});
                offsets.add(new int[]{0, 0});
            }
            case "C" -> {
                // 西侧一列：中心 (-2,0) 的 3x3 恰好覆盖 x-1 那一列（含最北与最南角）而不含目标
                offsets.add(new int[]{-2, 0});
                offsets.add(new int[]{0, 0});
            }
            case "D" -> {
                offsets.add(new int[]{2, 0});
                offsets.add(new int[]{0, 0});
            }
            default -> throw new IllegalArgumentException("未登记的场景字母：" + letter);
        }
        return offsets;
    }

    /** 请求顺序里目标区块排第几步（1 起；= 第几步被装饰）。 */
    public int targetStep() {
        for (int index = 0; index < order.size(); index++) {
            if (order.get(index).equals(target)) {
                return index + 1;
            }
        }
        return -1;
    }

    /** 顺序表文本（报告用）。 */
    public String orderCn() {
        StringBuilder text = new StringBuilder();
        for (int index = 0; index < order.size(); index++) {
            if (index > 0) {
                text.append(" → ");
            }
            ChunkPos pos = order.get(index);
            text.append('(').append(pos.x()).append(',').append(pos.z()).append(')');
            if (pos.equals(target)) {
                text.append("＝目标");
            }
        }
        return text.toString();
    }
}
