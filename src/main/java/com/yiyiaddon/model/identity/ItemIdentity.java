package com.yiyiaddon.model.identity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;

/**
 * 物品身份：把物品抽象成「可比较、可匹配、可持久化」的完整标识。
 *
 * <p>这是识别体系的核心数据单元：识别产出它、配置管理存储它、匹配层消费它。身份不再是单一的
 * itemId 字符串，而是完整记录物品 ID、显示名、自定义显示名、自定义逻辑 ID、物品模型、
 * 自定义数据、附魔、数据组件补丁与数据版本。</p>
 *
 * <p><b>核心身份判据</b>（{@link #equals} / {@link #identityKey()} / 匹配层共用同一判据）：
 * itemId + 自定义逻辑 ID + 身份显示名（自定义名优先，其次服务器显示名）+ 附魔。
 * 数量、动态网络数据（如 {@code craftengine:network_data}）与完整组件补丁均不参与，
 * 保证同一种自定义物品在动态数据变化后仍能稳定匹配。</p>
 *
 * <p>本类为纯数据模型：不访问注册表、不访问游戏状态、不含任何业务动作。</p>
 */
public final class ItemIdentity {

    /** 物品 ID，如 {@code minecraft:paper} */
    private final String itemId;
    /** 中文显示名（已剥离颜色代码） */
    private final String displayName;
    /** 物品默认中文名（注册表默认实例名） */
    private final String baseName;
    /** 原版改名组件的值，未改名为 {@code null} */
    private final String customName;
    /** 服务器自定义显示名组件的值，未设置为 {@code null} */
    private final String itemName;
    /** 服务器或模组自定义逻辑 ID，无则为 {@code null} */
    private final String customLogicId;
    /** 物品模型组件的值，未设置为 {@code null} */
    private final String itemModel;
    /** 自定义数据的 SNBT 原始结构，无则为 {@code null} */
    private final String customDataSnbt;
    /** 识别时的数据版本 */
    private final int dataVersion;
    /** 附魔列表（不参与数量与动态数据，但参与身份判据） */
    private final List<EnchantmentEntry> enchantments;
    /** 数据组件补丁的完整 JSON 文本，无组件为 {@code null} */
    private final String dataComponents;
    /** 识别时的物品数量（仅展示与落盘，不参与身份判据） */
    private final int quantity;
    /** 当前水量（从自定义数据解析，仅展示，不参与身份判据） */
    private final Integer waterValue;

    public ItemIdentity(String itemId, String displayName, String baseName, String customName,
                        String itemName, String customLogicId, String itemModel,
                        String customDataSnbt, int dataVersion, List<EnchantmentEntry> enchantments,
                        String dataComponents, int quantity, Integer waterValue) {
        this.itemId = itemId;
        this.displayName = displayName;
        this.baseName = baseName;
        this.customName = customName;
        this.itemName = itemName;
        this.customLogicId = customLogicId;
        this.itemModel = itemModel;
        this.customDataSnbt = customDataSnbt;
        this.dataVersion = dataVersion;
        this.enchantments = enchantments == null ? Collections.emptyList() : List.copyOf(enchantments);
        this.dataComponents = dataComponents;
        this.quantity = Math.max(1, quantity);
        this.waterValue = waterValue;
    }

    public String itemId() {
        return itemId;
    }

    public String displayName() {
        return displayName;
    }

    public String baseName() {
        return baseName;
    }

    public String customName() {
        return customName;
    }

    public String itemName() {
        return itemName;
    }

    public String customLogicId() {
        return customLogicId;
    }

    public String itemModel() {
        return itemModel;
    }

    public String customDataSnbt() {
        return customDataSnbt;
    }

    public int dataVersion() {
        return dataVersion;
    }

    public List<EnchantmentEntry> enchantments() {
        return enchantments;
    }

    public String dataComponents() {
        return dataComponents;
    }

    public int quantity() {
        return quantity;
    }

    public Integer waterValue() {
        return waterValue;
    }

    /** 身份显示名：改名组件优先，其次服务器显示名组件；均无返回 {@code null} */
    public String identityName() {
        if (customName != null && !customName.isBlank()) return customName;
        return itemName != null && !itemName.isBlank() ? itemName : null;
    }

    /** 是否原版改名物品（存在改名组件且与默认名不同） */
    public boolean isRenamed() {
        return customName != null && !customName.isBlank() && !customName.equals(baseName);
    }

    /**
     * 是否自定义物品：存在「明确的服务器或模组自定义身份证据」。
     *
     * <p>证据包括：非 minecraft 命名空间的 itemId、自定义逻辑 ID、服务器显示名组件、原版改名。
     * 仅存在附魔 / 描述 / 组件补丁等原版合法数据不构成自定义身份。</p>
     *
     * <p>服务器显示名组件（item_name）只有在<b>与注册表默认名不同</b>时才构成证据：大量服务器会给
     * 物品挂上与默认中文名完全相同的 {@code item_name}（如 {@code minecraft:sand} →「沙子」），
     * 只看「非空」会把整片原版物品误判成自定义。</p>
     */
    public boolean isCustom() {
        return !itemId.startsWith("minecraft:")
            || (customLogicId != null && !customLogicId.isBlank())
            || (itemName != null && !itemName.isBlank() && !itemName.equals(baseName))
            || isRenamed();
    }

    /** 是否原版物品 */
    public boolean isVanilla() {
        return !isCustom();
    }

    /** 物品类型中文名：原版 / 自定义 */
    public String typeName() {
        return isVanilla() ? "原版" : "自定义";
    }

    /** 是否带有附魔数据 */
    public boolean hasEnchantments() {
        return !enchantments.isEmpty();
    }

    /**
     * 生成稳定的身份键（判据与 {@link #equals} 完全一致）。
     *
     * <p>无自定义逻辑 ID 时与历史格式键保持兼容，已保存的选择项不会失效。</p>
     */
    public String identityKey() {
        return buildIdentityKey(itemId, customLogicId, identityName(), enchantments);
    }

    /** 核心身份键构造，供识别层对任意物品提取同格式键 */
    public static String buildIdentityKey(String itemId, String customLogicId,
                                          String identityName, List<EnchantmentEntry> enchantments) {
        StringBuilder sb = new StringBuilder(itemId);
        if (customLogicId != null && !customLogicId.isBlank()) {
            sb.append('#').append(customLogicId);
        }
        if (identityName != null && !identityName.isBlank()) {
            sb.append('#').append(identityName);
        }
        if (enchantments != null && !enchantments.isEmpty()) {
            sb.append('#');
            for (int i = 0; i < enchantments.size(); i++) {
                if (i > 0) sb.append(',');
                EnchantmentEntry entry = enchantments.get(i);
                sb.append(entry.id()).append(':').append(entry.level());
            }
        }
        return sb.toString();
    }

    /**
     * 完整状态键：稳定身份 + 完整组件数据 + 数量。
     *
     * <p>与 {@link #identityKey()} 严格区分：状态键纳入组件补丁（含自定义数据里的水量、耐久等
     * 动态组件），因此同一把水壶剩余水量不同会产生不同状态键，允许分别保存快照。</p>
     */
    public String stateKey() {
        return itemId + "|" + (dataComponents == null ? "" : dataComponents) + "|" + quantity;
    }

    /**
     * 状态指纹：对 {@link #stateKey()} 做 SHA-256 后取前 12 位十六进制。
     *
     * <p>仅用于快照去重与文件名短标识，绝不参与稳定身份判定，避免把动态水量塞进身份键。</p>
     */
    public String stateFingerprint() {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                .digest(stateKey().getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest, 0, 6);
        } catch (Exception ignored) {
            // 极端降级：退回基于状态键 hashCode 的短标识，保证不崩且仍可用于去重
            return Integer.toHexString(stateKey().hashCode());
        }
    }

    /** 序列化为 JSON（中文字段，便于直接阅读；兼容既有存档格式） */
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("物品ID", itemId);
        obj.addProperty("显示名称", displayName);
        obj.addProperty("原始名称", baseName);
        obj.addProperty("物品类型", typeName());
        obj.addProperty("自定义物品", isCustom());
        if (customName != null) obj.addProperty("自定义名称", customName);
        if (itemName != null) obj.addProperty("物品名称", itemName);
        if (customLogicId != null) obj.addProperty("自定义逻辑ID", customLogicId);
        if (itemModel != null) obj.addProperty("物品模型", itemModel);
        if (customDataSnbt != null) obj.addProperty("自定义数据", customDataSnbt);
        obj.addProperty("数量", quantity);
        if (waterValue != null) obj.addProperty("当前水量", waterValue);
        obj.addProperty("数据版本", dataVersion);
        if (!enchantments.isEmpty()) {
            JsonArray arr = new JsonArray();
            for (EnchantmentEntry e : enchantments) {
                JsonObject eo = new JsonObject();
                eo.addProperty("附魔ID", e.id());
                eo.addProperty("附魔名称", e.chineseName());
                eo.addProperty("等级", e.level());
                eo.addProperty("显示名称", e.displayName());
                arr.add(eo);
            }
            obj.add("附魔", arr);
        }
        if (dataComponents != null && !dataComponents.isBlank()) {
            JsonElement parsed = parseJson(dataComponents);
            if (parsed != null) obj.add("数据组件", parsed);
        }
        return obj;
    }

    /**
     * 从 JSON 反序列化（中文字段优先，兼容旧版英文字段）。
     *
     * <p>旧档缺失的新增字段按 {@code null} / 默认值处理，可正常读取；重新保存时升级为新格式。</p>
     */
    public static ItemIdentity fromJsonObject(JsonObject obj) {
        if (obj == null) return null;
        String itemId = strBoth(obj, "物品ID", "itemId", null);
        if (itemId == null || itemId.isBlank()) return null;

        String displayName = strBoth(obj, "显示名称", "displayName", itemId);
        String baseName = strBoth(obj, "原始名称", "baseName", displayName);
        String customName = strBoth(obj, "自定义名称", "customName", null);
        String itemName = strBoth(obj, "物品名称", "itemName", null);
        String customLogicId = strBoth(obj, "自定义逻辑ID", "customLogicId", null);
        String itemModel = strBoth(obj, "物品模型", "itemModel", null);
        String customDataSnbt = strBoth(obj, "自定义数据", "customData", null);
        int dataVersion = intBoth(obj, "数据版本", "dataVersion", 0);
        int quantity = intBoth(obj, "数量", "quantity", 1);

        Integer water = null;
        JsonElement waterEl = jsonBoth(obj, "当前水量", "waterValue");
        if (waterEl != null && waterEl.isJsonPrimitive()) {
            try {
                water = waterEl.getAsInt();
            } catch (Exception ignored) {
                // 非法水量值按空处理
            }
        }

        List<EnchantmentEntry> enchants = new ArrayList<>();
        JsonElement enchantEl = jsonBoth(obj, "附魔", "enchantments");
        if (enchantEl != null && enchantEl.isJsonArray()) {
            for (JsonElement el : enchantEl.getAsJsonArray()) {
                if (!el.isJsonObject()) continue;
                JsonObject eo = el.getAsJsonObject();
                enchants.add(new EnchantmentEntry(
                    strBoth(eo, "附魔ID", "id", ""),
                    strThree(eo, "附魔名称", "中文名称", "chineseName", ""),
                    intBoth(eo, "等级", "level", 0),
                    strBoth(eo, "显示名称", "displayName", "")
                ));
            }
        }

        String dataComponents = null;
        JsonElement dc = jsonBoth(obj, "数据组件", "dataComponents");
        if (dc != null) dataComponents = dc.toString();

        return new ItemIdentity(itemId, displayName, baseName, customName, itemName,
            customLogicId, itemModel, customDataSnbt, dataVersion, enchants,
            dataComponents, quantity, water);
    }

    private static JsonElement parseJson(String json) {
        try {
            return JsonParser.parseString(json);
        } catch (Exception ignored) {
            return null;
        }
    }

    /** 读取中文字段（优先）或英文字段（兼容旧档），均缺失回退 fallback */
    private static String strBoth(JsonObject obj, String cnKey, String enKey, String fallback) {
        if (obj.has(cnKey) && !obj.get(cnKey).isJsonNull()) return obj.get(cnKey).getAsString();
        if (obj.has(enKey) && !obj.get(enKey).isJsonNull()) return obj.get(enKey).getAsString();
        return fallback;
    }

    /** 读取三个候选字段（依次优先，兼容历史字段改名） */
    private static String strThree(JsonObject obj, String k1, String k2, String k3, String fallback) {
        if (obj.has(k1) && !obj.get(k1).isJsonNull()) return obj.get(k1).getAsString();
        if (obj.has(k2) && !obj.get(k2).isJsonNull()) return obj.get(k2).getAsString();
        if (obj.has(k3) && !obj.get(k3).isJsonNull()) return obj.get(k3).getAsString();
        return fallback;
    }

    private static int intBoth(JsonObject obj, String cnKey, String enKey, int fallback) {
        if (obj.has(cnKey) && !obj.get(cnKey).isJsonNull()) return obj.get(cnKey).getAsInt();
        if (obj.has(enKey) && !obj.get(enKey).isJsonNull()) return obj.get(enKey).getAsInt();
        return fallback;
    }

    private static JsonElement jsonBoth(JsonObject obj, String cnKey, String enKey) {
        if (obj.has(cnKey) && !obj.get(cnKey).isJsonNull()) return obj.get(cnKey);
        if (obj.has(enKey) && !obj.get(enKey).isJsonNull()) return obj.get(enKey);
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemIdentity other)) return false;
        return itemId.equals(other.itemId)
            && Objects.equals(customLogicId, other.customLogicId)
            && Objects.equals(identityName(), other.identityName())
            && enchantments.equals(other.enchantments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, customLogicId, identityName(), enchantments);
    }

    @Override
    public String toString() {
        return itemId;
    }

    /** 附魔条目：附魔 ID + 中文名 + 等级 + 含等级的全名 */
    public record EnchantmentEntry(String id, String chineseName, int level, String displayName) {
    }
}
