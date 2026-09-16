package com.yiyiaddon.feature.mining.ui;

import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.screen.SelectorScreen;
import io.github.humbleui.skija.Canvas;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

/**
 * 自动挖矿「登记表辅助」的唯一实现：选择器候选（物品 / 方块 / 食物）的构建与缓存、
 * 登记 ID ↔ 对象互转、空气判定、显示名解析，以及候选条目 {@link SelectorScreen.Entry} 的两个实现。
 *
 * <p><b>为什么抽出来：</b>这批成员原先只存在于模块页 {@link AutoMinerPage}，且全部是
 * {@code private static}；控制台「目标选择」页 {@code MiningTargetPage} 跨包用不了，便把整份
 * （约 160 行、含三个静态缓存字段）复制了一遍，改一处漏一处的风险由此而来。搬到本类后
 * <b>静态缓存只有这一份</b>（{@code allItems} / {@code allBlocks} / {@code allFoods} /
 * {@code COLLATOR}），两个页面共用同一批候选对象与同一次排序结果，不再各建一份。</p>
 *
 * <p><b>共用方：</b>{@link AutoMinerPage}（同包，模块页）与
 * {@code com.yiyiaddon.feature.mining.ui.console.MiningTargetPage}（跨包，控制台目标选择页）。
 * 跨包访问决定了本类成员需要 {@code public}。</p>
 *
 * <p><b>行为不变：</b>方法体逐字搬自 {@link AutoMinerPage} 的原实现（与
 * {@code MiningTargetPage} 的复制件逐字一致，仅可见性不同），未改任何取值域、剔除口径与文案。</p>
 */
public final class MiningRegistry {

    private MiningRegistry() {
    }

    /** 选择器分组标题：与本项目其它选择器（自动箱子目标物品 / Baritone 名单）同一口径 */
    private static final String GROUP_VANILLA_ITEM = "§a§l▌ 原版物品";
    private static final String GROUP_CUSTOM_ITEM = "§d§l▌ 自定义物品";
    private static final String GROUP_VANILLA_BLOCK = "§a§l▌ 原版方块";
    private static final String GROUP_CUSTOM_BLOCK = "§d§l▌ 自定义方块";

    // ── 选择器候选（只建一次并缓存：注册表运行期不变，显示名解析不便宜） ──

    private static final Collator COLLATOR = Collator.getInstance(Locale.CHINA);

    /** 全物品候选（含「空气」：它就是「未选择」的选项） */
    private static List<SelectorScreen.Entry> allItems;
    /** 全方块候选（含「空气」） */
    private static List<SelectorScreen.Entry> allBlocks;
    /** 能吃的物品（带 {@code FOOD} 数据组件），旧食物白名单 filter 同义 */
    private static List<SelectorScreen.Entry> allFoods;

    public static List<SelectorScreen.Entry> itemEntries() {
        if (allItems == null) allItems = buildItems(false);
        return allItems;
    }

    public static List<SelectorScreen.Entry> blockEntries() {
        if (allBlocks == null) allBlocks = buildBlocks();
        return allBlocks;
    }

    public static List<SelectorScreen.Entry> foodEntries() {
        if (allFoods == null) allFoods = buildItems(true);
        return allFoods;
    }

    private static List<SelectorScreen.Entry> buildItems(boolean foodOnly) {
        List<SelectorScreen.Entry> vanilla = new ArrayList<>();
        List<SelectorScreen.Entry> custom = new ArrayList<>();
        for (Item item : BuiltInRegistries.ITEM) {
            Identifier id = BuiltInRegistries.ITEM.getKey(item);
            if (id == null) continue;
            // 食物白名单只列「真能吃的」：旧项目 filter 要求默认实例带 FOOD 数据组件
            if (foodOnly && !item.components().has(DataComponents.FOOD)) continue;
            SelectorScreen.Entry entry = new ItemEntry(id.toString(), item);
            ("minecraft".equals(id.getNamespace()) ? vanilla : custom).add(entry);
        }
        return merge(vanilla, custom);
    }

    private static List<SelectorScreen.Entry> buildBlocks() {
        List<SelectorScreen.Entry> vanilla = new ArrayList<>();
        List<SelectorScreen.Entry> custom = new ArrayList<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if (id == null) continue;
            SelectorScreen.Entry entry = new BlockEntry(id.toString(), block);
            ("minecraft".equals(id.getNamespace()) ? vanilla : custom).add(entry);
        }
        return merge(vanilla, custom);
    }

    /** 两组各自按中文名排序后首尾相接：组内有序，组间固定「原版 → 自定义」（本项目选择器既有口径） */
    private static List<SelectorScreen.Entry> merge(List<SelectorScreen.Entry> vanilla,
                                                    List<SelectorScreen.Entry> custom) {
        Comparator<SelectorScreen.Entry> byName =
            Comparator.comparing(SelectorScreen.Entry::title, COLLATOR).thenComparing(SelectorScreen.Entry::key);
        vanilla.sort(byName);
        custom.sort(byName);
        List<SelectorScreen.Entry> all = new ArrayList<>(vanilla.size() + custom.size());
        all.addAll(vanilla);
        all.addAll(custom);
        return List.copyOf(all);
    }

    public static List<SelectorScreen.Entry> filter(List<SelectorScreen.Entry> source, Predicate<String> keep) {
        List<SelectorScreen.Entry> result = new ArrayList<>();
        for (SelectorScreen.Entry entry : source) {
            if (keep.test(entry.key())) result.add(entry);
        }
        return result;
    }

    // ── 登记 ID ↔ 名称 / 对象 ──

    /** 空气不是候选项：模块判定层为对齐旧 filter 会放行空气（＝未选择），界面层在这里剔除 */
    public static boolean isAirItem(String itemId) {
        return BuiltInRegistries.ITEM.getKey(Items.AIR).toString().equals(itemId);
    }

    public static boolean isAirBlock(String blockId) {
        return BuiltInRegistries.BLOCK.getKey(Blocks.AIR).toString().equals(blockId);
    }

    /** 产物名（空气 = 未选择，显示为「空气」；旧项目设置行同样显示该项名称） */
    public static String itemDisplayName(String itemId) {
        Item item = itemOf(itemId);
        return (item == null ? Items.AIR : item).getDefaultInstance().getHoverName().getString();
    }

    public static String blockDisplayName(String blockId) {
        Block block = blockOf(blockId);
        return (block == null || block == Blocks.AIR ? Blocks.AIR : block).getName().getString();
    }

    /** 登记 ID → 物品；ID 非法、不存在或为空气返回 {@code null}（空气 = 未选择） */
    public static Item itemOf(String itemId) {
        if (itemId == null || itemId.isBlank()) return null;
        Identifier id = Identifier.tryParse(itemId);
        if (id == null) return null;
        Item item = BuiltInRegistries.ITEM.getValue(id);
        return item == null || item == Items.AIR ? null : item;
    }

    /** 登记 ID → 方块；ID 非法、不存在或为空气返回 {@code null} */
    public static Block blockOf(String blockId) {
        if (blockId == null || blockId.isBlank()) return null;
        Identifier id = Identifier.tryParse(blockId);
        if (id == null) return null;
        Block block = BuiltInRegistries.BLOCK.getValue(id);
        return block == null || block == Blocks.AIR ? null : block;
    }

    /** 搭路方块名单：登记 ID → 方块（认不出的条目跳过，不猜） */
    public static List<Block> blockList(List<String> blockIds) {
        List<Block> blocks = new ArrayList<>();
        for (String id : blockIds) {
            Block block = blockOf(id);
            if (block != null) blocks.add(block);
        }
        return blocks;
    }

    // ── 选择器条目 ──

    /** 物品候选：贴图走物品图标缓存，未缓存当帧入队、下一帧起可画 */
    private record ItemEntry(String key, Item item) implements SelectorScreen.Entry {

        @Override
        public String title() {
            return item.getDefaultInstance().getHoverName().getString();
        }

        @Override
        public String group() {
            return key.startsWith("minecraft:") ? GROUP_VANILLA_ITEM : GROUP_CUSTOM_ITEM;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            return ItemIconCache.getInstance().draw(canvas, item.getDefaultInstance(), x, y, size);
        }
    }

    /** 方块候选：贴图取方块的物品形式 */
    private record BlockEntry(String key, Block block) implements SelectorScreen.Entry {

        @Override
        public String title() {
            return block.getName().getString();
        }

        @Override
        public String group() {
            return key.startsWith("minecraft:") ? GROUP_VANILLA_BLOCK : GROUP_CUSTOM_BLOCK;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            return ItemIconCache.getInstance().drawBlock(canvas, block, x, y, size);
        }
    }
}
