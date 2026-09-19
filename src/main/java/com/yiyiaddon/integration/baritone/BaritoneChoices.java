package com.yiyiaddon.integration.baritone;

import baritone.api.Settings;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.screen.SelectorScreen;
import io.github.humbleui.skija.Canvas;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Block;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Baritone 方块 / 物品类设置的候选表与取值转换——「不用手打登记名」的唯一实现处。
 *
 * <p><b>只收实体方块</b>：候选一律要求「碰撞箱占满一格」。名单里的设置都跟「这一格是什么方块」有关
 * （破坏 / 避让 / 建造忽略 / 垫脚），所以蜡烛、台阶、栅栏这类占不满一格的方块，以及马鞍这类根本
 * 放不下去的物品都不列（实机反馈）。</p>
 *
 * <p><b>候选表只建一次并缓存</b>：过滤后仍有上千项，解析显示名不便宜，而注册表在运行时不变。
 * 顺序是「原版在前、自定义在后」，组内按中文名排序（用排序规则，让「安山岩」排在「白桦木」前面）。</p>
 *
 * <p><b>贴图交给 {@link ItemIconCache}</b>：方块取它的物品形式，未缓存的图标当帧入队、下一帧起可画。</p>
 *
 * <p><b>取值转换</b>：读给界面的是登记名字符串（选择器只认 key），写回的是强类型的
 * {@code List<Block>} / {@code List<Item>}，与 Baritone 字段声明的泛型一致。</p>
 */
final class BaritoneChoices {

    /** 候选来源 */
    enum Kind {
        BLOCK, ITEM
    }

    private static final String VANILLA_BLOCKS = "§a§l▌ 原版实体方块";
    private static final String CUSTOM_BLOCKS = "§d§l▌ 自定义实体方块";
    private static final String VANILLA_ITEMS = "§a§l▌ 原版可放置方块的物品";
    private static final String CUSTOM_ITEMS = "§d§l▌ 自定义可放置方块的物品";
    private static final String MINECRAFT = "minecraft";

    private static final Collator COLLATOR = Collator.getInstance(Locale.CHINA);

    private static List<SelectorScreen.Entry> blockEntries;
    private static List<SelectorScreen.Entry> itemEntries;

    private BaritoneChoices() {
    }

    /** 候选表（原版在前，组内按中文名） */
    static List<SelectorScreen.Entry> entries(Kind kind) {
        if (kind == Kind.BLOCK) {
            if (blockEntries == null) blockEntries = buildBlocks();
            return blockEntries;
        }
        if (itemEntries == null) itemEntries = buildItems();
        return itemEntries;
    }

    /** 当前已选的登记名（给选择器的「右栏已选」用） */
    static List<String> ids(Settings.Setting<?> setting, Kind kind) {
        List<String> result = new ArrayList<>();
        if (!(setting.value instanceof List<?> list)) return result;
        for (Object element : list) {
            Identifier id = keyOf(element, kind);
            if (id != null) result.add(id.toString());
        }
        return result;
    }

    /**
     * 加入 / 移出一个登记名后的整份列表。
     *
     * @return 新的整份列表；没有任何变化（名字不认识 / 已在表里 / 不在表里）返回 {@code null}，
     *         由调用方决定不写回，避免把无意义的操作也落盘
     */
    static List<?> changed(Settings.Setting<?> setting, Kind kind, String id, boolean add) {
        if (kind == Kind.BLOCK) {
            List<Block> current = blocksOf(setting);
            Block element = blockOf(id);
            if (element == null) return null;
            if (add) {
                if (current.contains(element)) return null;
                current.add(element);
            } else if (!current.remove(element)) {
                return null;
            }
            return current;
        }
        List<Item> current = itemsOf(setting);
        Item element = itemOf(id);
        if (element == null) return null;
        if (add) {
            if (current.contains(element)) return null;
            current.add(element);
        } else if (!current.remove(element)) {
            return null;
        }
        return current;
    }

    private static List<Block> blocksOf(Settings.Setting<?> setting) {
        List<Block> result = new ArrayList<>();
        if (!(setting.value instanceof List<?> list)) return result;
        for (Object element : list) {
            if (element instanceof Block block) result.add(block);
        }
        return result;
    }

    private static List<Item> itemsOf(Settings.Setting<?> setting) {
        List<Item> result = new ArrayList<>();
        if (!(setting.value instanceof List<?> list)) return result;
        for (Object element : list) {
            if (element instanceof Item item) result.add(item);
        }
        return result;
    }

    private static Identifier keyOf(Object element, Kind kind) {
        if (kind == Kind.BLOCK) {
            return element instanceof Block block ? BuiltInRegistries.BLOCK.getKey(block) : null;
        }
        return element instanceof Item item ? BuiltInRegistries.ITEM.getKey(item) : null;
    }

    static Block blockOf(String id) {
        Identifier identifier = Identifier.tryParse(id);
        return identifier == null ? null : BuiltInRegistries.BLOCK.getValue(identifier);
    }

    static Item itemOf(String id) {
        Identifier identifier = Identifier.tryParse(id);
        return identifier == null ? null : BuiltInRegistries.ITEM.getValue(identifier);
    }

    // ── 建表 ──

    private static List<SelectorScreen.Entry> buildBlocks() {
        List<SelectorScreen.Entry> vanilla = new ArrayList<>();
        List<SelectorScreen.Entry> custom = new ArrayList<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if (id == null || !isSolidBlock(block)) continue;
            SelectorScreen.Entry entry = new BlockEntry(id.toString(), block);
            (MINECRAFT.equals(id.getNamespace()) ? vanilla : custom).add(entry);
        }
        return merge(vanilla, custom);
    }

    private static List<SelectorScreen.Entry> buildItems() {
        List<SelectorScreen.Entry> vanilla = new ArrayList<>();
        List<SelectorScreen.Entry> custom = new ArrayList<>();
        for (Item item : BuiltInRegistries.ITEM) {
            Identifier id = BuiltInRegistries.ITEM.getKey(item);
            // 物品名单（可消耗垫脚物品）里要的也是「能放下去当垫脚的方块」：马鞍这类根本放不下去的物品、
            // 蜡烛这类放下去也不占一格的方块都不该出现（实机反馈：怎么还有马鞍 蜡烛都出来了）
            if (id == null || !(item instanceof BlockItem blockItem) || !isSolidBlock(blockItem.getBlock())) continue;
            SelectorScreen.Entry entry = new ItemEntry(id.toString(), item);
            (MINECRAFT.equals(id.getNamespace()) ? vanilla : custom).add(entry);
        }
        return merge(vanilla, custom);
    }

    /** 两组各自按中文名排序后首尾相接：组内有序，组间固定「原版 → 自定义」 */
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

    /**
     * 是否「实体方块」：碰撞箱就是完整一格。
     *
     * <p>用碰撞箱判定，而不是「有物品形式」：有物品形式 ≠ 能当方块用——蜡烛、台阶、楼梯、栅栏、告示牌
     * 都有物品形式，但放下去只占一小块，既垫不了脚，也不能当作「原理图里的这一格是什么」。空气、
     * 水、岩浆、火把、花这些则连一格都占不满，同样排除。</p>
     *
     * <p>这里拿不到真实世界，所以用 {@link EmptyBlockGetter} 求默认状态下的碰撞箱——原版自己在
     * {@code BlockBehaviour} 里判断「完整方块」用的也是这套办法。</p>
     */
    private static boolean isSolidBlock(Block block) {
        return block.defaultBlockState().isCollisionShapeFullBlock(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
    }

    private record BlockEntry(String key, Block block) implements SelectorScreen.Entry {

        @Override
        public String title() {
            return block.getName().getString();
        }

        @Override
        public String group() {
            return key.startsWith(MINECRAFT + ":") ? VANILLA_BLOCKS : CUSTOM_BLOCKS;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            return ItemIconCache.getInstance().drawBlock(canvas, block, x, y, size);
        }

        /** 与 drawIcon 同一份图标：方块的物品形式（水、火这类没有物品形式的返回 null）。 */
        @Override
        public ItemStack iconStack() {
            Item item = block.asItem();
            return item == Items.AIR ? null : item.getDefaultInstance();
        }
    }

    private record ItemEntry(String key, Item item) implements SelectorScreen.Entry {

        @Override
        public String title() {
            return new ItemStack(item).getHoverName().getString();
        }

        @Override
        public String group() {
            return key.startsWith(MINECRAFT + ":") ? VANILLA_ITEMS : CUSTOM_ITEMS;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            return ItemIconCache.getInstance().draw(canvas, item.getDefaultInstance(), x, y, size);
        }

        /** 与 drawIcon 同一份图标。 */
        @Override
        public ItemStack iconStack() {
            return item.getDefaultInstance();
        }
    }
}
