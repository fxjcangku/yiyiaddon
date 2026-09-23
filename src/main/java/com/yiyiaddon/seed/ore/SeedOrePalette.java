package com.yiyiaddon.seed.ore;

import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.observation.OreObservationState;

/**
 * 种子挖矿正式模块 · <b>矿物调色板</b>（正式化第八阶段 236；26.2 语义移植）。
 *
 * <p><b>它解决什么</b>：236 起世界里会同时存在多种矿物的预测框，玩家必须能一眼分辨
 * 「这是红石还是青金石」。因此把颜色收进这一处按 {@link OreType} 索引的调色板，
 * <b>禁止</b>再把 {@code 0xEB4FA8FF} 这类魔法常量散落在渲染代码里。</p>
 *
 * <h2>两个维度不能混成一个视觉通道</h2>
 * <p>233/234 已经定下一条规则：{@code PredictionCertainty}（调度敏感）与
 * {@link OreObservationState}（观察状态）<b>不合并</b>，调度敏感用「内缩琥珀细框」这个独立通道表达。
 * 本调色板在同一原则下再划分两个通道：</p>
 * <ul>
 *     <li><b>色相 = 矿物种类</b>（本类负责）；</li>
 *     <li><b>明亮度 + 是否填充 = 观察状态</b>：{@link OreObservationState#UNOBSERVED} 是同色亮线 + 淡面，
 *         {@link OreObservationState#CONFIRMED} 是同色更实的面（更「实体化」），
 *         {@link OreObservationState#MISSING} 一律用统一灰细线（不填充）——
 *         它的语义是「预测里有、现在实际不是矿」，此时「是哪种矿」已经没有意义。</li>
 * </ul>
 *
 * <p><b>钻石的两档颜色与 233/235 完全一致</b>（预测青蓝 / 已确认绿），
 * 因此 235 的实机目视验收截图在 236 依然成立，不构成视觉回归。</p>
 *
 * <p><b>怎么读这些数字</b>：ARGB。线色取高 alpha（不透明感强），面色取低 alpha（半透明填充）。</p>
 */
public final class SeedOrePalette {

    /** 当前缺失的统一色（灰细线、不填充）：与矿物种类无关。 */
    public static final int MISSING_LINE = 0x789AA0A6;

    /** 调度敏感标记色（内缩细框；与矿物、观察状态都无关的第三个通道）。 */
    public static final int SCHEDULE_LINE = 0xF5FFB020;

    private SeedOrePalette() {
    }

    /**
     * 该矿物在「预测（未观察）」状态下的一对颜色。
     *
     * @return {@code [line, side]}
     */
    public static int[] predicted(OreType oreType) {
        return switch (oreType) {
            // 钻石：233/235 冻结色（青蓝线 + 淡青面）
            case DIAMOND -> new int[]{0xEB4FA8FF, 0x2D2F6FD0};
            // 红石：正红（与钻石的冷色、金/铜的暖黄拉开）
            case REDSTONE -> new int[]{0xEBFF4A3D, 0x2DD8392B};
            // 青金石：靛蓝（比钻石更深更紫，靠明度区分）
            case LAPIS -> new int[]{0xEB6E5CFF, 0x2D3A2FB0};
            // 金：金黄
            case GOLD -> new int[]{0xEBFFD24A, 0x2DB8901F};
            // 铁：米白（接近原版铁矿石的点状灰白）
            case IRON -> new int[]{0xEBE4E0D8, 0x2D9A958C};
            // 铜：铜橙
            case COPPER -> new int[]{0xEBFF9A5A, 0x2DB06334};
            // 煤：墨黑带蓝灰（保证在暗处仍可见轮廓）
            case COAL -> new int[]{0xEB9AA3AD, 0x2D3A3F45};
            // 绿宝石：翠绿（与钻石的「已确认绿」靠色相偏青区分）
            case EMERALD -> new int[]{0xEB3BF5A0, 0x2D1FA06A};
            // 远古残骸：暗紫褐（下界专用色，与下界方块基调接近但仍可辨）
            case ANCIENT_DEBRIS -> new int[]{0xEBC08A7A, 0x2D6B4A41};
            // 下界石英：冷白
            case NETHER_QUARTZ -> new int[]{0xEBF2ECFF, 0x2DA9A2C4};
            // 下界金：亮橙金（与主世界金靠色相偏橙区分）
            case NETHER_GOLD -> new int[]{0xEBFFB03A, 0x2DC4781B};
        };
    }

    /**
     * 该矿物在「已确认」状态下的一对颜色。
     *
     * <p>与 {@link #predicted(OreType)} 同一个色相家族，但更亮、面更实：玩家看到「更实」就知道
     * 「这一格现在真的是这种矿」。钻石保持 233 的绿色（不回归）。</p>
     *
     * @return {@code [line, side]}
     */
    public static int[] confirmed(OreType oreType) {
        return switch (oreType) {
            // 钻石：233/235 冻结色（绿线 + 淡绿面）
            case DIAMOND -> new int[]{0xF53FE07A, 0x3721A34A};
            case REDSTONE -> new int[]{0xF5FF7A6E, 0x3AC2453A};
            case LAPIS -> new int[]{0xF59B8DFF, 0x3A5546C9};
            case GOLD -> new int[]{0xF5FFE07A, 0x3AD8A82C};
            case IRON -> new int[]{0xF5F5F2EC, 0x3AB2ADA3};
            case COPPER -> new int[]{0xF5FFB47A, 0x3ACB7C46};
            case COAL -> new int[]{0xF5C3CBD4, 0x3A5A6068};
            case EMERALD -> new int[]{0xF56BFFBE, 0x3A2FC07F};
            case ANCIENT_DEBRIS -> new int[]{0xF5D9A99A, 0x3A8A6255};
            case NETHER_QUARTZ -> new int[]{0xF5FFFFFF, 0x3ACBC5E0};
            case NETHER_GOLD -> new int[]{0xF5FFC96B, 0x3AD98F2C};
        };
    }

    /** 观察状态是否需要用「该矿物的预测色」画（否则就是缺失灰）。 */
    public static boolean usesPredictedColor(OreObservationState state) {
        return state == OreObservationState.UNOBSERVED;
    }
}
