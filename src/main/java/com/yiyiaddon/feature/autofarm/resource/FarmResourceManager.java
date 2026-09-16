package com.yiyiaddon.feature.autofarm.resource;

import com.yiyiaddon.feature.autofarm.model.CropProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 农场资源管理器：负责种植材料的安全库存、卸货/补货判定与毒马铃薯识别。
 *
 * 每个作物独立维护两份数量（组）：卸货数量（产物超过才卸）与补货种子数量（低于才补）。
 * 卸货按「物品去向」智能分三类：双物品作物种子去种子补货箱、单物品收获物（含柱状物/果实）去单作物箱、
 * 双物品收获物去多作物箱；多作物箱与种子补货箱使用同一套「是不是双作物」判定。
 */
public final class FarmResourceManager {

    /** 未配置时的默认卸货数量（组） */
    public static final int DEFAULT_UNLOAD_GROUPS = 8;
    /** 未配置时的默认补货种子数量（组） */
    public static final int DEFAULT_RESTOCK_GROUPS = 3;

    private final Set<CropProfile> enabled = EnumSet.noneOf(CropProfile.class);

    /** 每作物独立卸货数量（组） */
    private Map<CropProfile, Integer> unloadGroups = Map.of();
    /** 每作物独立补货种子数量（组） */
    private Map<CropProfile, Integer> restockGroups = Map.of();

    /** 更新启用作物集合与每作物独立卸货/补货配置 */
    public void configure(Set<CropProfile> crops, Map<CropProfile, Integer> unloadGroups, Map<CropProfile, Integer> restockGroups) {
        this.enabled.clear();
        this.enabled.addAll(crops);
        this.unloadGroups = unloadGroups;
        this.restockGroups = restockGroups;
    }

    /** 某作物配置的卸货数量（组），缺失时走默认 */
    public int unloadGroups(CropProfile crop) {
        return unloadGroups.getOrDefault(crop, DEFAULT_UNLOAD_GROUPS);
    }

    /** 某作物配置的补货种子数量（组），缺失时走默认 */
    public int restockGroups(CropProfile crop) {
        return restockGroups.getOrDefault(crop, DEFAULT_RESTOCK_GROUPS);
    }

    /** 某作物种植材料的安全库存数量（个）= 补货种子数量组 × 64 */
    public int safetyStock(CropProfile crop) {
        return restockGroups(crop) * 64;
    }

    /** 某作物产物的卸货触发数量（个）= 卸货数量组 × 64 */
    public int unloadStock(CropProfile crop) {
        return unloadGroups(crop) * 64;
    }

    /**
     * 某作物在补货判定时需要保留的数量（个）。
     * 单物品作物（种子==收获物）的保留数量取补货种子数量与卸货数量中的较小者，
     * 避免「卸货数量」小于「补货种子数量」时补货阈值反超卸货阈值，导致卸货永远不触发。
     */
    private int reserve(CropProfile crop) {
        if (crop.plantItem() == crop.harvestItem()) {
            return Math.min(safetyStock(crop), unloadStock(crop));
        }
        return safetyStock(crop);
    }

    /** 背包里指定物品的总数量（主背包 36 格 + 副手） */
    public int countItem(Item item) {
        if (item == null) return 0;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;

        int total = 0;
        for (ItemStack stack : mc.player.getInventory().getNonEquipmentItems()) {
            if (stack.is(item)) total += stack.getCount();
        }
        if (mc.player.getOffhandItem().is(item)) total += mc.player.getOffhandItem().getCount();
        return total;
    }

    /** 某作物是否需要补货（种植材料低于保留数量） */
    public boolean needsRestock(CropProfile crop) {
        return crop.needsReplant() && countItem(crop.plantItem()) < reserve(crop);
    }

    /** 是否存在任一启用作物需要补货 */
    public boolean anyNeedsRestock() {
        for (CropProfile crop : enabled) {
            if (needsRestock(crop)) return true;
        }
        return false;
    }

    /** 取第一个需要补货的作物，没有返回 null */
    public CropProfile firstNeedsRestock() {
        for (CropProfile crop : enabled) {
            if (needsRestock(crop)) return crop;
        }
        return null;
    }

    /** 判断物品是否属于杂物（毒马铃薯 + 仙人掌花这类不进常规作物箱的副产物） */
    public boolean isJunkItem(Item item) {
        if (item == Items.POISONOUS_POTATO) return true;
        for (CropProfile crop : enabled) {
            if (crop.junk() && item == crop.harvestItem()) return true;
        }
        return false;
    }

    /** 背包里杂物（毒马铃薯 + 仙人掌花）的总数量，用于杂物卸货阈值判断 */
    public int countJunk() {
        int total = countItem(Items.POISONOUS_POTATO);
        for (CropProfile crop : enabled) {
            if (crop.junk()) total += countItem(crop.harvestItem());
        }
        return total;
    }

    /** 判断物品是否应卸入杂物箱（触发后全卸，不做库存保护） */
    public boolean shouldDepositJunk(ItemStack stack) {
        return isJunkItem(stack.getItem());
    }

    /**
     * 判断物品是否应卸入种子补货箱（双物品作物的种子，超出补货种子数量才卸）。
     * 双作物判定：需要补种 且 种植材料 ≠ 收获物（小麦/甜菜根的种子与果实分离）。
     */
    public boolean shouldDepositSeed(ItemStack stack) {
        Item item = stack.getItem();
        for (CropProfile crop : enabled) {
            if (crop.needsReplant()
                && item == crop.plantItem()
                && crop.plantItem() != crop.harvestItem()
                && countItem(item) > safetyStock(crop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断物品是否应卸入单作物箱（单物品作物与柱状物/果实的收获物）。
     * 不补种的柱状物/果实（甘蔗/竹子/仙人掌/南瓜/西瓜）产物单一，超过独立卸货数量即卸；
     * 单物品作物（马铃薯/胡萝卜/下界疣，种子==收获物）超过独立卸货数量即卸，卸货数量即保留数量。
     */
    public boolean shouldDepositSingle(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Items.POISONOUS_POTATO) return false;
        for (CropProfile crop : enabled) {
            if (crop.junk()) continue;
            if (item != crop.harvestItem()) continue;
            if (!crop.needsReplant()) return countItem(item) > unloadStock(crop);
            if (crop.plantItem() == crop.harvestItem()) {
                return countItem(item) > unloadStock(crop);
            }
        }
        return false;
    }

    /**
     * 判断物品是否应卸入多作物箱（双物品作物的收获物，超过独立卸货数量才卸）。
     * 与种子补货箱同套双作物判定：必须需要补种 且 种植材料 ≠ 收获物，
     * 柱状物/果实不在此列，其收获物归单作物箱。
     */
    public boolean shouldDepositDual(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Items.POISONOUS_POTATO) return false;
        for (CropProfile crop : enabled) {
            if (crop.junk()) continue;
            if (crop.needsReplant()
                && item == crop.harvestItem()
                && crop.plantItem() != crop.harvestItem()
                && countItem(item) > unloadStock(crop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 卸入种子补货箱的实际筛选（触发后不再看卸货数量阈值，只保留补货库存）。
     * 双物品作物的种子卸到安全库存为止，避免种子被卸光。
     */
    public boolean shouldUnloadSeed(ItemStack stack) {
        Item item = stack.getItem();
        for (CropProfile crop : enabled) {
            if (crop.needsReplant()
                && item == crop.plantItem()
                && crop.plantItem() != crop.harvestItem()
                && countItem(item) > safetyStock(crop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 卸入单作物箱的实际筛选（触发后不再看卸货数量阈值，避免只卸一组）。
     * 不补种作物（柱状物/果实）全卸；单物品作物（种子==收获物）只保留补货种子数量为止，
     * 与「补货种子数量：卸货时始终保留这批材料」的语义一致，避免误留「卸货数量」那么多在背包。
     */
    public boolean shouldUnloadSingle(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Items.POISONOUS_POTATO) return false;
        for (CropProfile crop : enabled) {
            if (crop.junk()) continue;
            if (item != crop.harvestItem()) continue;
            if (!crop.needsReplant()) return countItem(item) > 0;
            if (crop.plantItem() == crop.harvestItem()) return countItem(item) > safetyStock(crop);
        }
        return false;
    }

    /** 卸入多作物箱的实际筛选：双物品作物的成熟掉落物全卸（种子由种子补货箱单独处理） */
    public boolean shouldUnloadDual(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Items.POISONOUS_POTATO) return false;
        for (CropProfile crop : enabled) {
            if (crop.junk()) continue;
            if (crop.needsReplant()
                && item == crop.harvestItem()
                && crop.plantItem() != crop.harvestItem()) {
                return countItem(item) > 0;
            }
        }
        return false;
    }

    /** 背包里是否存在应卸入种子补货箱的种子（超补货种子数量） */
    public boolean hasDepositableSeed() {
        return hasDepositable(this::shouldDepositSeed);
    }

    /** 背包里是否存在应卸入单作物箱的单物品收获物 */
    public boolean hasDepositableSingle() {
        return hasDepositable(this::shouldDepositSingle);
    }

    /** 背包里是否存在应卸入多作物箱的双物品收获物 */
    public boolean hasDepositableDual() {
        return hasDepositable(this::shouldDepositDual);
    }

    /** 遍历背包判断是否存在满足指定卸货规则的物品 */
    private boolean hasDepositable(Predicate<ItemStack> filter) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        for (ItemStack stack : mc.player.getInventory().getNonEquipmentItems()) {
            if (!stack.isEmpty() && filter.test(stack)) return true;
        }
        ItemStack offhand = mc.player.getOffhandItem();
        return !offhand.isEmpty() && filter.test(offhand);
    }
}
