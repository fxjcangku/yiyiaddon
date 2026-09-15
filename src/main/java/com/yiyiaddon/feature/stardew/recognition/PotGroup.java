package com.yiyiaddon.feature.stardew.recognition;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Locale;

/**
 * 种植盆型组：盆型同时决定「用什么东西补水」与「能不能在当前维度种」。
 *
 * <p><b>盆型从哪来：</b>不需要资源包额外声明字段——盆的稳定键里就带着序号
 * （{@code dry_pot_1} / {@code wet_pot_1} → 1，{@code dry_pot_2} / {@code wet_pot_2} → 2 …），
 * 与 {@code PotDefinition.potIndex} 同一口径，识别层已经解析出来了。</p>
 *
 * <ul>
 *   <li>{@link #NORMAL} 普通种植盆：补水用<b>水</b>（水壶 / 洒水器 / 补水点），
 *       <b>任何维度都能种</b>；</li>
 *   <li>{@link #NETHER} 下界种植盆：补水用<b>岩浆</b>（熔岩桶右键，+3 返空桶），
 *       <b>只能在下界种</b>；</li>
 *   <li>{@link #END} 末地种植盆：补水用<b>龙息</b>（龙息右键，+1 返玻璃瓶），
 *       <b>只能在末地种</b>。</li>
 * </ul>
 *
 * <p>三种盆型的物料互斥：一种盆只用一种物料，因此选择器只允许选中一种盆型
 * （见 {@code StardewSelectionBinding} 的盆型单选），脚本不会在同一趟里既浇水又倒岩浆。</p>
 */
public enum PotGroup {

    NORMAL(1, "普通种植盆", "水", null, null, null, 0),
    NETHER(2, "下界种植盆", "岩浆", "minecraft:the_nether", Items.LAVA_BUCKET, Items.BUCKET, 3),
    END(3, "末地种植盆", "龙息", "minecraft:the_end", Items.DRAGON_BREATH, Items.GLASS_BOTTLE, 10);

    private final int potIndex;
    private final String displayName;
    private final String materialName;
    /** 只允许在此维度种植；{@code null} 表示任何维度都可以 */
    private final String dimensionId;
    /** 补水时消耗的物品；{@code null} 表示走水壶链路 */
    private final Item refillItem;
    /** 物料用完后返还的空容器（空桶 / 玻璃瓶）；{@code null} 表示没有 */
    private final Item emptyItem;
    /** 一趟从箱子取多少料：岩浆 3 桶、龙息 10 个（用完再去补） */
    private final int refillBatch;

    PotGroup(int potIndex, String displayName, String materialName, String dimensionId,
             Item refillItem, Item emptyItem, int refillBatch) {
        this.potIndex = potIndex;
        this.displayName = displayName;
        this.materialName = materialName;
        this.dimensionId = dimensionId;
        this.refillItem = refillItem;
        this.emptyItem = emptyItem;
        this.refillBatch = refillBatch;
    }

    /** 盆型序号（与资源索引的 potIndex 一致） */
    public int potIndex() {
        return potIndex;
    }

    public String displayName() {
        return displayName;
    }

    /** 补水物料中文名：水 / 岩浆 / 龙息 */
    public String materialName() {
        return materialName;
    }

    /** 补水消耗的物品；{@code null} 表示这种盆走水壶链路，没有直接消耗物 */
    public Item refillItem() {
        return refillItem;
    }

    /** 物料用完后返还的空容器（空桶 / 玻璃瓶）；{@code null} 表示没有，取料时也无从回收 */
    public Item emptyItem() {
        return emptyItem;
    }

    /** 一趟从物料箱取多少料：岩浆 3 桶、龙息 10 个；普通盆为 0（不走箱子） */
    public int refillBatch() {
        return refillBatch;
    }

    /** 是否限定维度种植 */
    public boolean dimensionRestricted() {
        return dimensionId != null;
    }

    /** 该盆型是否允许在指定维度种植（普通盆永远允许） */
    public boolean allowsDimension(String dimension) {
        return dimensionId == null || dimensionId.equals(dimension);
    }

    /** 允许种植的维度标识；{@code null} 表示不限 */
    public String dimensionId() {
        return dimensionId;
    }

    /** 序号 → 盆型；未知序号按普通盆处理（资源索引新增盆型时不会炸） */
    public static PotGroup ofIndex(int index) {
        for (PotGroup group : values()) {
            if (group.potIndex == index) return group;
        }
        return NORMAL;
    }

    /**
     * 盆键 → 盆型，键形如 {@code dry_pot_2} / {@code wet_pot_2}（大小写不敏感）。
     *
     * <p>无法解析出序号时按普通盆处理：普通盆是唯一不限维度、走水壶链路的一种，
     * 退到它不会误把盆当成下界盆去倒岩浆。</p>
     */
    public static PotGroup ofPotKey(String potKey) {
        if (potKey == null || potKey.isBlank()) return NORMAL;
        String lower = potKey.toLowerCase(Locale.ROOT);
        int end = lower.length();
        int start = end;
        while (start > 0 && Character.isDigit(lower.charAt(start - 1))) start--;
        if (start == end) return NORMAL;
        try {
            return ofIndex(Integer.parseInt(lower.substring(start, end)));
        } catch (NumberFormatException e) {
            return NORMAL;
        }
    }
}
