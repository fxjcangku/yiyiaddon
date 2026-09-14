package com.yiyiaddon.feature.stardew.service;

import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.identity.ItemIdentifier;
import com.yiyiaddon.platform.identity.ItemIdentityMatcher;
import com.yiyiaddon.service.identity.IdentityService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 星露谷真实库存服务。
 *
 * <p>只统计「真实背包」里的数量，绝不根据计划 / 掉落概率推算或预扣。匹配同时支持
 * 真实身份键（IdentityService）与 item_model 组件（资源包扫描候选），因此没有逐个 .id
 * 的作物也能准确统计种子与成品，不再把「没手动入库」误判成「没有资源」。</p>
 */
public final class StardewInventoryService {

    private static final Minecraft mc = Minecraft.getInstance();

    /** 「当前/上限」水量对，兼容半角与全角斜杠 */
    private static final Pattern WATER_PAIR = Pattern.compile("(\\d{1,6})\\s*[/／]\\s*(\\d{1,6})");

    /** custom_data 里明确的容量键 */
    private static final Set<String> CAPACITY_KEYS = Set.of(
        "max_water", "maxwater", "water_max", "watermax", "capacity", "water_capacity", "watercapacity");

    /** 仅当同层存在 water 键时才认这些笼统上限键 */
    private static final List<String> GENERIC_MAX_KEYS =
        List.of("max", "maximum", "max_amount", "max_value", "cap");

    private final IdentityService identityService;

    public StardewInventoryService(IdentityService identityService) {
        this.identityService = identityService;
    }

    /** 背包中某物品身份的总数量（0-35 全部槽位） */
    public int count(ItemIdentity identity) {
        if (identity == null || mc.player == null) return 0;
        int total = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (ItemIdentityMatcher.matches(stack, identity)) total += stack.getCount();
        }
        return total;
    }

    /** 按身份键统计数量 */
    public int countByKey(String identityKey) {
        if (identityKey == null) return 0;
        return count(identityService.findItem(identityKey));
    }

    /** 按身份键集合统计总数量 */
    public int countKeys(List<String> identityKeys) {
        if (identityKeys == null || identityKeys.isEmpty()) return 0;
        int total = 0;
        for (String key : identityKeys) {
            ItemIdentity id = identityService.findItem(key);
            if (id != null) total += count(id);
        }
        return total;
    }

    /** 某作物对应种子的库存数量（真实身份 + item_model 双源） */
    public int countSeed(CropDefinition crop) {
        if (crop == null || mc.player == null) return 0;
        int total = 0;
        for (int i = 0; i < 36; i++) {
            if (matchesSeed(mc.player.getInventory().getItem(i), crop)) {
                total += mc.player.getInventory().getItem(i).getCount();
            }
        }
        return total;
    }

    /** 返回背包中第一份真实目标种子，供 Tooltip/Data Components 等只读规则解析。 */
    public ItemStack findSeedStack(CropDefinition crop) {
        if (crop == null || mc.player == null) return ItemStack.EMPTY;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (matchesSeed(stack, crop)) return stack;
        }
        return ItemStack.EMPTY;
    }

    /** 某作物全部成熟产物（普通 + 品质 + 特殊变种）的库存数量（真实身份 + item_model 双源） */
    public int countProduce(CropDefinition crop) {
        if (crop == null || mc.player == null) return 0;
        int total = 0;
        for (int i = 0; i < 36; i++) {
            if (matchesProduce(mc.player.getInventory().getItem(i), crop)) {
                total += mc.player.getInventory().getItem(i).getCount();
            }
        }
        return total;
    }

    /** 背包中所有命中某身份键集合的物品槽位（用于卸货时逐格快速移动） */
    public List<Integer> slotsOfKeys(List<String> identityKeys) {
        List<Integer> slots = new ArrayList<>();
        if (identityKeys == null || identityKeys.isEmpty() || mc.player == null) return slots;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;
            for (String key : identityKeys) {
                ItemIdentity id = identityService.findItem(key);
                if (id != null && ItemIdentityMatcher.matches(stack, id)) {
                    slots.add(i);
                    break;
                }
            }
        }
        return slots;
    }

    // ── 统一匹配（真实身份键 + item_model 组件） ──

    /** 某 ItemStack 是否命中一个工具候选（真实身份优先，其次 item_model） */
    public boolean matchesEntry(ItemStack stack, StardewToolDefinition entry) {
        if (stack == null || stack.isEmpty() || entry == null) return false;
        if (entry.isBound()) {
            ItemIdentity id = identityService.findItem(entry.identityKey());
            if (id != null && ItemIdentityMatcher.matches(stack, id)) return true;
        }
        return entry.matchesByModel(stack);
    }

    /** 某 ItemStack 是否是某作物种子 */
    public boolean matchesSeed(ItemStack stack, CropDefinition crop) {
        if (stack == null || stack.isEmpty() || crop == null) return false;
        if (crop.seedKey() != null) {
            ItemIdentity id = identityService.findItem(crop.seedKey());
            if (id != null && ItemIdentityMatcher.matches(stack, id)) return true;
        }
        return matchesModel(stack, crop.seedModel());
    }

    /** 某 ItemStack 是否是某作物成熟产物（含品质与特殊变种） */
    public boolean matchesProduce(ItemStack stack, CropDefinition crop) {
        return roleOf(stack, crop).unloadable();
    }

    /**
     * 作物物品的唯一后勤角色。身份键与 item_model 双源匹配，顺序固定为
     * SEED → VARIANT → PRODUCE；即使资源索引候选发生重叠，种子也不会落入卸货角色。
     */
    public StardewItemRole roleOf(ItemStack stack, CropDefinition crop) {
        if (stack == null || stack.isEmpty() || crop == null) return StardewItemRole.OTHER;
        if (matchesSeed(stack, crop)) return StardewItemRole.SEED;
        if (matchesAnyKey(stack, crop.variantKeys()) || matchesAnyModel(stack, crop.variantModels())) {
            return StardewItemRole.VARIANT;
        }
        if (matchesAnyKey(stack, crop.produceKeys()) || matchesAnyModel(stack, crop.produceModels())) {
            return StardewItemRole.PRODUCE;
        }
        return StardewItemRole.OTHER;
    }

    /** 背包真实槽位按玩家看到的物品名聚合；用于事务前后 delta 播报。 */
    public Map<String, Integer> countByDisplay(CropDefinition crop, boolean seeds, boolean unloadable) {
        Map<String, Integer> result = new LinkedHashMap<>();
        if (crop == null || mc.player == null) return result;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            StardewItemRole role = roleOf(stack, crop);
            if (!(seeds && role == StardewItemRole.SEED) && !(unloadable && role.unloadable())) continue;
            result.merge(stack.getHoverName().getString(), stack.getCount(), Integer::sum);
        }
        return result;
    }

    private boolean matchesAnyKey(ItemStack stack, List<String> keys) {
        if (keys == null) return false;
        for (String key : keys) {
            ItemIdentity id = identityService.findItem(key);
            if (id != null && ItemIdentityMatcher.matches(stack, id)) return true;
        }
        return false;
    }

    private boolean matchesAnyModel(ItemStack stack, List<String> models) {
        if (models == null) return false;
        for (String model : models) {
            if (matchesModel(stack, model)) return true;
        }
        return false;
    }

    /** 读取 ItemStack 的 item_model 组件值；无则 null */
    public static String itemModelOf(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        Identifier model = stack.get(DataComponents.ITEM_MODEL);
        return model == null ? null : model.toString();
    }

    /** 用 item_model 组件精确匹配 */
    public static boolean matchesModel(ItemStack stack, String model) {
        if (model == null) return false;
        String actual = itemModelOf(stack);
        return model.equals(actual);
    }

    /** 在背包 / 快捷栏里找第一个命中工具候选的槽位；hotbarOnly 时只扫 0-8，否则扫 0-35 */
    public int findSlotEntry(StardewToolDefinition entry, boolean hotbarOnly) {
        if (entry == null || mc.player == null) return -1;
        int end = hotbarOnly ? 9 : 36;
        for (int i = 0; i < end; i++) {
            if (matchesEntry(mc.player.getInventory().getItem(i), entry)) return i;
        }
        return -1;
    }

    /** 在背包 / 快捷栏里找第一个命中作物种子的槽位 */
    public int findSlotSeed(CropDefinition crop, boolean hotbarOnly) {
        if (crop == null || mc.player == null) return -1;
        int end = hotbarOnly ? 9 : 36;
        for (int i = 0; i < end; i++) {
            if (matchesSeed(mc.player.getInventory().getItem(i), crop)) return i;
        }
        return -1;
    }

    /**
     * 从 ItemStack 读取真实水量（CustomCrops.water）。
     *
     * <p>复用 {@link ItemIdentifier#extractWaterValue}：兼容顶层 {@code water}、嵌套
     * {@code CustomCrops{water:N}} 与任意嵌套 CompoundTag。读不到返回 null（保持 UNKNOWN，
     * 绝不伪造水量、绝不无限浇水）。</p>
     */
    public static Integer readWater(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null || data.isEmpty()) return null;
        CompoundTag tag = data.copyTag();
        return tag == null || tag.isEmpty() ? null : ItemIdentifier.extractWaterValue(tag);
    }

    /**
     * 读取水壶的水量上限（容量）。
     *
     * <p><b>四个来源，按可信度排序：</b></p>
     * <ol>
     *   <li>Tooltip / LORE 里的「当前/上限」数字（{@code dynamic-lore} 的 {@code {current}/{storage}}）；</li>
     *   <li>custom_data 里明确的容量键（{@code max_water} / {@code capacity} 等）；</li>
     *   <li>耐久条镜像：{@code 当前水量 == 最大耐久 − 已损耐久} 时容量即最大耐久；</li>
     *   <li>Tooltip / LORE 里的<b>水位条</b>：资源包字形（{@code font/bars/*} 指向的 codepoint）优先，
     *       抓不到就用与命名无关的通用结构探测兜底，两者都用真实水量反证
     *       （见 {@link StardewWaterBarGlyphMap} / {@link StardewWaterBarProbe}）。</li>
     * </ol>
     *
     * <p>三者都读不到一律返回 null —— 绝不猜、绝不伪造，由调用方退回自学习节奏。</p>
     */
    public static Integer readWaterCapacity(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        Integer water = readWater(stack);

        List<Component> tooltip = tooltipLines(stack);
        Integer fromTooltip = capacityFromLines(tooltip);
        if (fromTooltip != null) return fromTooltip;
        ItemLore lore = stack.get(DataComponents.LORE);
        List<Component> loreLines = lore == null ? List.of() : lore.lines();
        Integer fromLore = capacityFromLines(loreLines);
        if (fromLore != null) return fromLore;

        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        Integer fromTag = data == null || data.isEmpty() ? null : capacityFromTag(data.copyTag());
        if (fromTag != null) return fromTag;

        Integer fromDurability = capacityFromDurability(stack, water);
        if (fromDurability != null) return fromDurability;

        Integer fromBar = capacityFromWaterBar(tooltip, water);
        return fromBar != null ? fromBar : capacityFromWaterBar(loreLines, water);
    }

    /**
     * 从耐久条反推容量：部分服务器把水量镜像到耐久显示（1 点水 = 1 点耐久）。
     *
     * <p>只在「当前水量 = 最大耐久 − 已损耐久」严格成立时才采信；对不上说明这把壶的耐久
     * 与水量无关（例如普通剑类材料），返回 null 交给其它来源。</p>
     */
    private static Integer capacityFromDurability(ItemStack stack, Integer water) {
        if (water == null || !stack.isDamageableItem()) return null;
        int max = stack.getMaxDamage();
        if (max <= 1) return null;
        return water == max - stack.getDamageValue() ? max : null;
    }

    /**
     * 从 Tooltip / LORE 里的水位条还原容量。
     *
     * <p><b>两条判据，互补覆盖不同服务器：</b></p>
     * <ol>
     *   <li><b>资源包字形（强证据）</b>：字形来自 {@code assets/<ns>/font/*.json} 指向
     *       {@code .../bars/...} 的 codepoint，同一行同时出现格子与端帽才算（见
     *       {@link StardewWaterBarGlyphMap}）；</li>
     *   <li><b>通用结构（不依赖命名）</b>：只认「端帽 + N 个重复格」结构 + 真实水量反证
     *       （见 {@link StardewWaterBarProbe}）。换一套资源包、换一个目录名同样成立。</li>
     * </ol>
     *
     * <p>两者都用「格数 ≥ 当前水量」过滤，并在多个候选里取较小值：估小只会多补一轮，
     * 估大才会白发包，所以永远偏保守。</p>
     */
    private static Integer capacityFromWaterBar(List<Component> lines, Integer water) {
        if (lines == null || lines.isEmpty()) return null;
        StardewWaterBarGlyphMap map = StardewWaterBarGlyphMap.current();
        int anchored = 0;
        int structural = 0;
        for (Component line : lines) {
            String text = line.getString();
            if (map.configured()) {
                StardewWaterBarGlyphMap.LineCount count = map.countLine(text);
                if (count.looksLikeBar() && (water == null || count.cells() >= water)) {
                    anchored = Math.max(anchored, count.cells());
                    continue;
                }
            }
            Integer cells = StardewWaterBarProbe.cellsIn(text, water);
            if (cells != null) structural = Math.max(structural, cells);
        }
        if (anchored > 0) return anchored;
        return structural > 0 ? structural : null;
    }

    private static List<Component> tooltipLines(ItemStack stack) {
        if (mc.player == null) return List.of();
        return stack.getTooltipLines(Item.TooltipContext.of(mc.level), mc.player, TooltipFlag.NORMAL);
    }

    /** 逐行找「当前/上限」；行内必须出现水语义或水量条字符，避免把其它 N/M 数据当成容量。 */
    private static Integer capacityFromLines(List<Component> lines) {
        if (lines == null) return null;
        for (Component line : lines) {
            String text = line.getString();
            if (!looksLikeWaterLine(text)) continue;
            Matcher matcher = WATER_PAIR.matcher(text);
            while (matcher.find()) {
                Integer capacity = saneCapacity(matcher.group(2));
                if (capacity != null) return capacity;
            }
        }
        return null;
    }

    private static boolean looksLikeWaterLine(String text) {
        if (text == null || text.isBlank()) return false;
        if (text.contains("水") || text.toLowerCase(Locale.ROOT).contains("water")) return true;
        return text.indexOf('\u2588') >= 0 || text.indexOf('\u258C') >= 0 || text.indexOf('|') >= 0;
    }

    private static Integer capacityFromTag(CompoundTag tag) {
        if (tag == null || tag.isEmpty()) return null;
        for (var entry : tag.entrySet()) {
            if (CAPACITY_KEYS.contains(entry.getKey().toLowerCase(Locale.ROOT))
                && entry.getValue() instanceof NumericTag numeric) {
                Integer capacity = saneCapacity(String.valueOf(numeric.intValue()));
                if (capacity != null) return capacity;
            }
        }
        // 同层同时有 water 与 max 时才认 max，避免把无关的 max 当容量
        if (tag.contains("water") || tag.contains("Water")) {
            for (String key : GENERIC_MAX_KEYS) {
                if (tag.get(key) instanceof NumericTag numeric) {
                    Integer capacity = saneCapacity(String.valueOf(numeric.intValue()));
                    if (capacity != null) return capacity;
                }
            }
        }
        for (var entry : tag.entrySet()) {
            if (entry.getValue() instanceof CompoundTag child) {
                Integer nested = capacityFromTag(child);
                if (nested != null) return nested;
            }
        }
        return null;
    }

    private static Integer saneCapacity(String raw) {
        try {
            int value = Integer.parseInt(raw);
            return value > 0 && value <= 10000 ? value : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
