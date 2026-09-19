package com.yiyiaddon.platform.identity;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.yiyiaddon.model.identity.ItemIdentity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 物品识别适配层：从 {@link ItemStack} 提取完整 {@link ItemIdentity}。
 *
 * <p>基于 26.1.2 数据组件 API（非旧 NBT）结构化提取：</p>
 * <ul>
 *   <li>itemId：注册表键（{@code minecraft:paper}）；</li>
 *   <li>customName：{@code minecraft:custom_name} 组件（原版改名）；</li>
 *   <li>itemName：{@code minecraft:item_name} 组件（服务器自定义显示名）；</li>
 *   <li>itemModel：{@code minecraft:item_model} 组件（26.1.2 中为 Identifier）；</li>
 *   <li>customLogicId：从 {@code minecraft:custom_data} 提取（优先 CraftEngine 键，
 *       其次任意非 minecraft 命名空间的 ID 型字符串；回落为 item_model 值）；</li>
 *   <li>enchantments：{@code minecraft:enchantments} + {@code minecraft:stored_enchantments}；</li>
 *   <li>dataComponents：完整组件补丁 JSON（原始结构保留，未知数据不丢弃）。</li>
 * </ul>
 */
public final class ItemIdentifier {

    /** 形似 {@code minecraft:path} 或 {@code customcrops:dry_pot_1} 的 ID 型字符串 */
    private static final Pattern ID_PATTERN = Pattern.compile("^[a-z0-9._-]+:[a-z0-9._/-]+$");

    private ItemIdentifier() {
    }

    /** 提取物品 ID；空物品返回 {@code minecraft:air} */
    public static String itemIdOf(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return "minecraft:air";
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
    }

    /** 该物品 ID 是否存在于当前注册表 */
    public static boolean exists(String itemId) {
        if (itemId == null || itemId.isBlank()) return false;
        Identifier id = Identifier.tryParse(itemId);
        if (id == null) return false;
        Item item = BuiltInRegistries.ITEM.getValue(id);
        return item != null && item != Items.AIR;
    }

    /** 识别一个物品为完整身份；空物品返回 {@code null} */
    public static ItemIdentity identifyItem(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;

        Item item = stack.getItem();
        String itemId = BuiltInRegistries.ITEM.getKey(item).toString();

        // 默认中文名走注册表项的默认实例
        String baseName = clean(item.getDefaultInstance().getHoverName().getString());
        String customName = componentPlain(stack, DataComponents.CUSTOM_NAME);
        String itemName = componentPlain(stack, DataComponents.ITEM_NAME);

        Identifier model = stack.get(DataComponents.ITEM_MODEL);
        String itemModel = model == null ? null : model.toString();

        CompoundTag customData = customDataTag(stack);
        String customDataSnbt = (customData != null && !customData.isEmpty()) ? customData.toString() : null;
        String customLogicId = extractCustomLogicId(itemModel, customData);
        Integer waterValue = extractWaterValue(customData);

        // 显示名优先级：改名组件 > 服务器显示名 > 资源包中文名（按自定义逻辑 ID 查）> 默认名
        String langName = resolveResourceName(customLogicId);
        String displayName = resolveDisplayName(customName, itemName, langName, baseName);

        List<ItemIdentity.EnchantmentEntry> enchantments = extractEnchantments(stack);
        String dataComponents = serializeComponents(stack, registryAccess());
        int dataVersion = currentDataVersion();

        return new ItemIdentity(itemId, displayName, baseName, customName, itemName,
            customLogicId, itemModel, customDataSnbt, dataVersion, enchantments,
            dataComponents, stack.getCount(), waterValue);
    }

    /**
     * 提取物品核心身份键（轻量版，不序列化组件补丁）。
     *
     * <p>只提取参与身份判定的字段，供每个容器槽位高频匹配使用，开销远小于完整识别。</p>
     */
    public static String coreKeyOf(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return "";
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        String customName = componentPlain(stack, DataComponents.CUSTOM_NAME);
        String itemName = componentPlain(stack, DataComponents.ITEM_NAME);
        String identityName = customName != null ? customName : itemName;
        CompoundTag customData = customDataTag(stack);
        String customLogicId = extractCustomLogicId(modelOf(stack), customData);
        List<ItemIdentity.EnchantmentEntry> enchantments = extractEnchantments(stack);
        return ItemIdentity.buildIdentityKey(itemId, customLogicId, identityName, enchantments);
    }

    // ── 身份构造（依赖注册表的构建入口统一放在适配层） ──

    /** 从纯物品 ID 构造身份（手动添加原版物品用）；非法 ID 或注册表不存在返回 {@code null} */
    public static ItemIdentity fromItemId(String itemId) {
        if (itemId == null || itemId.isBlank()) return null;
        Identifier id = Identifier.tryParse(itemId);
        if (id == null) return null;
        Item item = BuiltInRegistries.ITEM.getValue(id);
        if (item == null || item == Items.AIR) return null;
        return identifyItem(item.getDefaultInstance());
    }

    /** 从物品 + 自定义名构造改名物品身份；自定义名为空返回 {@code null} */
    public static ItemIdentity fromItemAndCustomName(Item item, String customName) {
        if (item == null || item == Items.AIR) return null;
        String cn = clean(customName);
        if (cn.isBlank()) return null;
        return new ItemIdentity(registryId(item), cn, defaultName(item), cn, null, null, null, null,
            currentDataVersion(), Collections.emptyList(), null, 1, null);
    }

    /**
     * 从物品 + 自定义身份规格构造自定义物品身份（手动添加用，不要求手持实物）。
     *
     * <p>基础物品必须真实存在，否则返回 {@code null}（禁止凭空构造）。</p>
     */
    public static ItemIdentity fromCustomSpec(Item item, String name, String customLogicId, String itemModel) {
        if (item == null || item == Items.AIR) return null;
        String cn = clean(name);
        if (cn.isBlank()) return null;
        String logicId = clean(customLogicId);
        String model = clean(itemModel);
        return new ItemIdentity(registryId(item), cn, defaultName(item), null, cn,
            logicId.isBlank() ? null : logicId,
            model.isBlank() ? null : model,
            null, currentDataVersion(), Collections.emptyList(), null, 1, null);
    }

    /**
     * 按中文显示名精确匹配原版物品（全等比较，非包含匹配）。
     *
     * <p>可能存在多个同名物品，调用方需对多结果展示候选列表，不得随机选择。</p>
     */
    public static List<Item> findVanillaByChineseName(String chineseName) {
        List<Item> result = new ArrayList<>();
        String target = clean(chineseName);
        if (target.isEmpty()) return result;
        for (Item item : BuiltInRegistries.ITEM) {
            if (item == Items.AIR) continue;
            if (clean(item.getDefaultInstance().getHoverName().getString()).equals(target)) {
                result.add(item);
            }
        }
        return result;
    }

    // ── 自定义身份提取 ──

    /**
     * 提取服务器或模组自定义逻辑 ID。
     *
     * <p>判定顺序：自定义数据顶层 {@code craftengine:id} → 自定义数据中任意非 minecraft 命名空间的
     * ID 型字符串 → 非 minecraft 命名空间的 item_model 值。全部无命中返回 {@code null}。</p>
     */
    public static String extractCustomLogicId(String itemModel, CompoundTag customData) {
        if (customData != null && !customData.isEmpty()) {
            String craftEngineId = findCraftEngineId(customData);
            if (craftEngineId != null) return craftEngineId;
            String generic = findGenericCustomId(customData);
            if (generic != null) return generic;
        }
        if (itemModel != null && !itemModel.isBlank()
            && !minecraftNamespace(itemModel) && ID_PATTERN.matcher(itemModel).matches()) {
            return itemModel;
        }
        return null;
    }

    /** CraftEngine 逻辑 ID：兼容扁平键 {@code craftengine:id} 与嵌套 {@code craftengine{id}} */
    private static String findCraftEngineId(CompoundTag tag) {
        Tag flat = tag.get("craftengine:id");
        if (flat instanceof StringTag flatId) {
            String value = flatId.value().trim();
            if (ID_PATTERN.matcher(value).matches()) return value;
        }
        Tag nested = tag.get("craftengine");
        if (nested instanceof CompoundTag ce && ce.get("id") instanceof StringTag nestedId) {
            String value = nestedId.value().trim();
            if (ID_PATTERN.matcher(value).matches()) return value;
        }
        return null;
    }

    /** 递归扫描任意非 minecraft 命名空间的 ID 型字符串（通用兜底） */
    private static String findGenericCustomId(CompoundTag tag) {
        for (var entry : tag.entrySet()) {
            Tag value = entry.getValue();
            if (value instanceof StringTag st) {
                String text = st.value().trim();
                if (ID_PATTERN.matcher(text).matches() && !minecraftNamespace(text)) return text;
            } else if (value instanceof CompoundTag child) {
                String found = findGenericCustomId(child);
                if (found != null) return found;
            }
        }
        return null;
    }

    /**
     * 从自定义数据解析水量。
     *
     * <p>兼容顶层数值键 {@code water}、嵌套 {@code CustomCrops{water:N}} 以及任意嵌套结构内的
     * {@code water} 数值键。该值仅用于展示，不参与身份判定。</p>
     */
    public static Integer extractWaterValue(CompoundTag customData) {
        if (customData == null || customData.isEmpty()) return null;
        return findWaterRecursive(customData);
    }

    private static Integer findWaterRecursive(Tag tag) {
        if (tag instanceof CompoundTag ct) {
            Tag direct = ct.get("water");
            if (direct instanceof NumericTag nt) return nt.intValue();
            if (ct.get("CustomCrops") instanceof CompoundTag cc && cc.get("water") instanceof NumericTag wn) {
                return wn.intValue();
            }
            for (var entry : ct.entrySet()) {
                if (entry.getValue() instanceof CompoundTag child) {
                    Integer found = findWaterRecursive(child);
                    if (found != null) return found;
                }
            }
        }
        return null;
    }

    /**
     * 按自定义逻辑 ID 解析资源包中文名。
     *
     * <p>逻辑 ID 形如 {@code customcrops:tomato_seeds}，据此查语言键
     * {@code item.customcrops.tomato_seeds}；查不到返回 {@code null}。</p>
     */
    public static String resolveResourceName(String customLogicId) {
        if (customLogicId == null || customLogicId.isBlank()) return null;
        Identifier id = Identifier.tryParse(customLogicId);
        if (id == null) return null;
        String langKey = "item." + id.getNamespace() + "." + id.getPath();
        net.minecraft.locale.Language language = net.minecraft.locale.Language.getInstance();
        if (language == null || !language.has(langKey)) return null;
        return clean(language.getOrDefault(langKey));
    }

    // ── 内部提取工具 ──

    private static String defaultName(Item item) {
        return clean(item.getDefaultInstance().getHoverName().getString());
    }

    /** 物品注册表键（如 {@code minecraft:diamond}） */
    private static String registryId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    private static String resolveDisplayName(String customName, String itemName, String langName, String baseName) {
        if (customName != null && !customName.isBlank()) return customName;
        if (itemName != null && !itemName.isBlank()) return itemName;
        if (langName != null && !langName.isBlank()) return langName;
        return baseName;
    }

    private static boolean minecraftNamespace(String value) {
        return value.startsWith("minecraft:");
    }

    private static CompoundTag customDataTag(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null || data.isEmpty()) return null;
        return data.copyTag();
    }

    private static String modelOf(ItemStack stack) {
        Identifier model = stack.get(DataComponents.ITEM_MODEL);
        return model == null ? null : model.toString();
    }

    /** 读取文本类组件并剥离颜色代码；无组件或空文本返回 {@code null} */
    private static String componentPlain(ItemStack stack, net.minecraft.core.component.DataComponentType<Component> type) {
        Component component = stack.get(type);
        if (component == null) return null;
        String text = clean(component.getString());
        return text.isEmpty() ? null : text;
    }

    /** 「经验修补」在 zh_cn / en_us 下的显示名：内联附魔（取不到 id）时只能按玩家看到的名字认 */
    private static final Set<String> MENDING_NAMES = Set.of("经验修补", "Mending");

    /**
     * 物品是否带「经验修补」（装备附魔 + 存储附魔都看，口径同 {@link #extractEnchantments}）。
     *
     * <p><b>替换掉的旧写法</b>：从世界注册表取 {@code Enchantments.MENDING} 的 holder，再
     * {@code enchantments.getLevel(holder) > 0}。那条路依赖 holder <b>实例</b>：
     * {@link ItemEnchantments} 内部是 {@code Object2IntOpenHashMap<Holder<Enchantment>>}，而
     * {@code Holder.Reference} 没有覆写 {@code equals/hashCode}（引用比较）。只要物品上那条附魔的
     * holder 不是注册表里那一个实例（服务器用数据包 / 插件给的自定义「经验修补」，或内联附魔
     * {@code Holder.Direct}），旧写法就查成 0 级 —— 表现是「明明带着经验修补的工具被判没有经验修补」
     * （用户 2026-09-19：带经验修补的铲子被判没附魔，于是不回去挂机点修）。</p>
     *
     * <p>现在直接遍历物品自己的附魔条目，命中任一条即算：</p>
     * <ol>
     *   <li>key 就是 {@code minecraft:mending}（{@code ResourceKey} 是 interned 的，引用比较即可）；</li>
     *   <li>id 的路径段是 {@code mending}（自定义命名空间下的同名附魔，如 {@code xxx:mending}）；</li>
     *   <li>显示名是「经验修补 / Mending」（连 id 都取不到的内联附魔，按玩家看到的名字认）。</li>
     * </ol>
     */
    public static boolean hasMending(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return anyMending(stack.get(DataComponents.ENCHANTMENTS))
            || anyMending(stack.get(DataComponents.STORED_ENCHANTMENTS));
    }

    private static boolean anyMending(ItemEnchantments enchantments) {
        if (enchantments == null || enchantments.isEmpty()) return false;
        for (var entry : enchantments.entrySet()) {
            if (isMending(entry.getKey())) return true;
        }
        return false;
    }

    /** 单条附魔是不是「经验修补」，判据见 {@link #hasMending} */
    private static boolean isMending(Holder<Enchantment> holder) {
        if (holder.is(Enchantments.MENDING)) return true;
        var key = holder.unwrapKey().orElse(null);
        if (key != null && "mending".equals(key.identifier().getPath())) return true;
        try {
            return MENDING_NAMES.contains(clean(holder.value().description().getString()));
        } catch (Exception ignored) {
            return false; // 内联附魔解不出来时不猜
        }
    }

    /** 提取附魔（装备附魔 + 附魔书存储附魔） */
    private static List<ItemIdentity.EnchantmentEntry> extractEnchantments(ItemStack stack) {
        List<ItemIdentity.EnchantmentEntry> result = new ArrayList<>();
        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments != null) collectEnchantments(enchantments, result);
        ItemEnchantments stored = stack.get(DataComponents.STORED_ENCHANTMENTS);
        if (stored != null) collectEnchantments(stored, result);
        return result;
    }

    private static void collectEnchantments(ItemEnchantments enchantments,
                                            List<ItemIdentity.EnchantmentEntry> out) {
        for (var entry : enchantments.entrySet()) {
            Holder<Enchantment> holder = entry.getKey();
            int level = entry.getIntValue();
            // 附魔 ID 走 unwrapKey().identifier()，避免 ResourceKey.toString() 的包装格式
            String id = holder.unwrapKey().map(key -> key.identifier().toString()).orElse("");
            String chineseName = clean(holder.value().description().getString());
            String fullName = clean(Enchantment.getFullname(holder, level).getString());
            out.add(new ItemIdentity.EnchantmentEntry(id, chineseName, level, fullName));
        }
    }

    /** 序列化完整数据组件补丁（未知数据不丢弃）；失败返回 {@code null} 而不阻塞识别 */
    private static String serializeComponents(ItemStack stack, RegistryAccess access) {
        if (access == null) return null;
        try {
            DataComponentPatch patch = stack.getComponentsPatch();
            if (patch == null || patch.isEmpty()) return null;
            RegistryOps<com.google.gson.JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, access);
            DataResult<com.google.gson.JsonElement> result = DataComponentPatch.CODEC.encodeStart(ops, patch);
            com.google.gson.JsonElement json = result.result().orElse(null);
            return json == null ? null : json.toString();
        } catch (Exception ignored) {
            return null;
        }
    }

    private static RegistryAccess registryAccess() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) return mc.player.registryAccess();
        if (mc.level != null) return mc.level.registryAccess();
        return null;
    }

    private static int currentDataVersion() {
        try {
            return net.minecraft.SharedConstants.getCurrentVersion().dataVersion().version();
        } catch (Exception ignored) {
            return 0;
        }
    }

    /** 剥离颜色代码并去首尾空格 */
    private static String clean(String text) {
        if (text == null) return "";
        return text.replaceAll("§[0-9a-fk-orA-FK-ORx]", "").trim();
    }
}
