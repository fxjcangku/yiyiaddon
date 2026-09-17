package com.yiyiaddon.feature.villager.model;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * 单条容器绑定：一个坐标 + 一个维度。
 *
 * <p>对应旧项目 {@code CunminCommand.CunminData} 里按 key（{@code emerald_chest} /
 * {@code unload_chest}）分开存的 {@code positions} 与 {@code dimensions} 两张表的一条记录，
 * 这里合成一个不可变值对象，便于读盘与快照。</p>
 *
 * <p><b>维度口径（D2 拍板：沿用旧三值）</b>：维度只认
 * {@code minecraft:overworld} / {@code minecraft:the_nether} / {@code minecraft:the_end}，
 * 其余（含自定义维度）一律归主世界。这与旧项目 {@code CunminData.toDimensionId} 和读盘归一
 * 完全一致，因而旧存档零迁移即可读回。</p>
 */
public final class VillagerBinding {

    /** 主世界维度 ID（旧项目所有兜底分支都落在这里） */
    public static final String OVERWORLD_ID = "minecraft:overworld";
    /** 下界维度 ID */
    public static final String NETHER_ID = "minecraft:the_nether";
    /** 末地维度 ID */
    public static final String END_ID = "minecraft:the_end";

    private final BlockPos pos;
    /** 维度裸 ID（三值之一），不是 {@code ResourceKey[...]} 包装串 */
    private final String dimensionId;

    public VillagerBinding(BlockPos pos, String dimensionId) {
        this.pos = pos;
        this.dimensionId = dimensionId == null ? OVERWORLD_ID : normalizeDimensionId(dimensionId);
    }

    public BlockPos pos() {
        return pos;
    }

    public String dimensionId() {
        return dimensionId;
    }

    /** 维度裸 ID → {@code ResourceKey<Level>}（三值映射，旧 {@code CunminData.getDimension} 口径）。 */
    public ResourceKey<Level> dimensionKey() {
        return switch (dimensionId) {
            case NETHER_ID -> Level.NETHER;
            case END_ID -> Level.END;
            default -> Level.OVERWORLD;
        };
    }

    /**
     * {@code ResourceKey<Level>} → 维度裸 ID。
     *
     * <p>用 {@code contains} 而不是等值比较：旧项目读盘时可能拿到
     * {@code ResourceKey[minecraft:dimension/minecraft:the_end]} 这类包装串，
     * 只要包含标准 ID 就认定该维度（旧 {@code CunminData.toDimensionId} 同口径）。</p>
     */
    public static String toDimensionId(ResourceKey<Level> dimension) {
        if (dimension == null) return OVERWORLD_ID;
        return normalizeDimensionId(dimension.toString());
    }

    /** 任意维度字符串 → 三值裸 ID；认不出的一律归主世界（旧读盘归一分支）。 */
    public static String normalizeDimensionId(String raw) {
        if (raw == null) return OVERWORLD_ID;
        if (raw.contains(NETHER_ID)) return NETHER_ID;
        if (raw.contains(END_ID)) return END_ID;
        return OVERWORLD_ID;
    }
}
