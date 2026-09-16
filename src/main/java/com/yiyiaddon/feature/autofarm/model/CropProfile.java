package com.yiyiaddon.feature.autofarm.model;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * 作物规则中心：把正式支持的十种作物固化成数据表，供扫描器、任务与资源管理器查询。
 *
 * 成熟识别必须基于世界实际 BlockState，绝不通过「背包有没有种子」判断。
 * 每类作物的种植材料只用于 PlantTask 与 FarmResourceManager。
 */
public enum CropProfile {

    // ── 普通农作物：需要补种，底盘为耕地/灵魂沙 ──
    WHEAT("小麦", Kind.CROP, Blocks.WHEAT, Items.WHEAT_SEEDS, Items.WHEAT, Blocks.FARMLAND),
    CARROT("胡萝卜", Kind.CROP, Blocks.CARROTS, Items.CARROT, Items.CARROT, Blocks.FARMLAND),
    // 马铃薯额外掉落毒马铃薯，须进入独立毒马铃薯箱
    POTATO("马铃薯", Kind.CROP, Blocks.POTATOES, Items.POTATO, Items.POTATO, Blocks.FARMLAND, Set.of(Items.POISONOUS_POTATO)),
    BEETROOT("甜菜根", Kind.CROP, Blocks.BEETROOTS, Items.BEETROOT_SEEDS, Items.BEETROOT, Blocks.FARMLAND),
    NETHER_WART("下界疣", Kind.CROP, Blocks.NETHER_WART, Items.NETHER_WART, Items.NETHER_WART, Blocks.SOUL_SAND),

    // ── 柱状物：垂直生长，切根部上方，不补种 ──
    BAMBOO("竹子", Kind.PILLAR, Blocks.BAMBOO, null, Items.BAMBOO, null),
    SUGAR_CANE("甘蔗", Kind.PILLAR, Blocks.SUGAR_CANE, null, Items.SUGAR_CANE, null),
    CACTUS("仙人掌", Kind.PILLAR, Blocks.CACTUS, null, Items.CACTUS, null),

    // ── 杂物：仙人掌花是仙人掌自然长出的副产物，与毒马铃薯一样独立进杂物箱，不计入常规作物箱 ──
    CACTUS_FLOWER("仙人掌花", Kind.FRUIT, Blocks.CACTUS_FLOWER, null, Items.CACTUS_FLOWER, null),

    // ── 果实：只砍果实方块，不补种，由茎再生 ──
    PUMPKIN("南瓜", Kind.FRUIT, Blocks.PUMPKIN, null, Items.PUMPKIN, null),
    MELON("西瓜", Kind.FRUIT, Blocks.MELON, null, Items.MELON_SLICE, null);

    /** 作物形态分类，决定收割与补种策略 */
    public enum Kind {
        /** 普通农作物：读 age 属性判成熟，需要补种 */
        CROP,
        /** 柱状物：垂直生长，切根部上方 */
        PILLAR,
        /** 果实：独立果实方块，存在即可收割 */
        FRUIT
    }

    private final String displayName;
    private final Kind kind;
    /** 成熟作物方块 */
    private final Block block;
    /** 种植材料物品，柱状物与果实为 null */
    private final Item plantItem;
    /** 收获主产物物品 */
    private final Item harvestItem;
    /** 播种所需底盘方块，柱状物与果实为 null */
    private final Block soil;
    /** 附带掉落物（如毒马铃薯），需独立处理，不进普通作物箱 */
    private final Set<Item> extraLoot;

    CropProfile(String displayName, Kind kind, Block block, Item plantItem, Item harvestItem, Block soil) {
        this(displayName, kind, block, plantItem, harvestItem, soil, Set.of());
    }

    CropProfile(String displayName, Kind kind, Block block, Item plantItem, Item harvestItem, Block soil, Set<Item> extraLoot) {
        this.displayName = displayName;
        this.kind = kind;
        this.block = block;
        this.plantItem = plantItem;
        this.harvestItem = harvestItem;
        this.soil = soil;
        this.extraLoot = extraLoot;
    }

    public String displayName() {
        return displayName;
    }

    public Kind kind() {
        return kind;
    }

    public Block block() {
        return block;
    }

    /** 种植材料物品，无需补种时为 null */
    public Item plantItem() {
        return plantItem;
    }

    public Item harvestItem() {
        return harvestItem;
    }

    public Block soil() {
        return soil;
    }

    /** 附带掉落物（毒马铃薯之类），需进入独立毒马铃薯箱 */
    public Set<Item> extraLoot() {
        return extraLoot;
    }

    /** 该作物的主产物是否为杂物（进杂物箱，不进单/多作物箱），仙人掌花属于此类 */
    public boolean junk() {
        return this == CACTUS_FLOWER;
    }

    /** 中文显示名，供配置界面选择器与枚举设置项序列化使用 */
    @Override
    public String toString() {
        return displayName;
    }

    /** 是否需要补种。柱状物与果实按规则不补种 */
    public boolean needsReplant() {
        return kind == Kind.CROP;
    }

    /** 该作物在卸货时需要截留的种植材料物品 */
    public Item retainItem() {
        return needsReplant() ? plantItem : null;
    }

    /**
     * 该作物贡献到「产物统计」的全部物品：主产物 + 种植材料（双作物果实与种子分离时两者都算）。
     */
    public Set<Item> produceItems() {
        Set<Item> items = new java.util.HashSet<>();
        items.add(harvestItem);
        if (plantItem != null) items.add(plantItem);
        items.addAll(extraLoot);
        return Collections.unmodifiableSet(items);
    }

    /**
     * 判定给定坐标是否为可收割目标（基于世界实际 BlockState）。
     *
     * @param state 该坐标方块状态，调用方保证 block 已匹配
     * @param level 世界访问器
     * @param pos   目标坐标
     */
    public boolean isHarvestable(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.is(block)) return false;

        return switch (kind) {
            // 果实方块存在即可收割
            case FRUIT -> true;
            // 柱状物：根部之上第一格已长出（下方是同种或竹笋，且再下方不是同种）
            case PILLAR -> {
                BlockPos below = pos.below();
                BlockState belowState = level.getBlockState(below);
                boolean belowIsRoot = belowState.is(block) || isPillarRoot(belowState);
                if (!belowIsRoot) yield false;
                yield !level.getBlockState(below.below()).is(block);
            }
            // 普通农作物：读 age 属性是否到顶
            case CROP -> isMaxAge(state);
        };
    }

    /**
     * 判定给定底盘坐标是否为待补种空地：底盘正确且其上方为空气。
     */
    public boolean isPlantable(BlockGetter level, BlockPos soilPos) {
        if (!needsReplant() || soil == null) return false;
        if (!level.getBlockState(soilPos).is(soil)) return false;
        return level.getBlockState(soilPos.above()).isAir();
    }

    /** 通用成熟度判定：在方块状态属性表里找 age 整型属性，取可能值上限比对当前值 */
    public static boolean isMaxAge(BlockState state) {
        IntegerProperty age = findAgeProperty(state);
        if (age == null) return false;

        int max = Integer.MIN_VALUE;
        for (int value : age.getPossibleValues()) {
            if (value > max) max = value;
        }
        return max != Integer.MIN_VALUE && state.getValue(age) >= max;
    }

    private static IntegerProperty findAgeProperty(BlockState state) {
        Collection<Property<?>> properties = state.getProperties();
        for (Property<?> property : properties) {
            if (property instanceof IntegerProperty integerProperty && "age".equals(property.getName())) {
                return integerProperty;
            }
        }
        return null;
    }

    /** 按方块反查图鉴条目，未收录返回 null */
    public static CropProfile byBlock(Block block) {
        for (CropProfile profile : values()) {
            if (profile.block == block) return profile;
        }
        return null;
    }

    /** 竹笋是竹子的根部形态，扫描时需要一并识别 */
    public static boolean isPillarRoot(BlockState state) {
        return state.is(Blocks.BAMBOO_SAPLING);
    }
}
