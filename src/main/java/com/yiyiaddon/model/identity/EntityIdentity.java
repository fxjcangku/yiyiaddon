package com.yiyiaddon.model.identity;

import com.google.gson.JsonObject;

import java.util.Objects;

/**
 * 实体身份：把实体抽象成「可去重、可匹配、可持久化」的标识。
 *
 * <p><b>类型身份判据</b>（{@link #equals} / {@link #hashCode} / {@link #identityKey()} 一致）：
 * 实体类型 ID + 自定义名。同一种僵尸、同命名的实体重复识别只保留一份记录，不会无限生成
 * {@code 僵尸_2.json}。UUID 与坐标属于具体实例的辅助信息，仅记录与展示。</p>
 *
 * <p>本类为纯数据模型：不访问注册表、不访问游戏状态。</p>
 */
public final class EntityIdentity {

    /** 实体类型 ID，如 {@code minecraft:zombie} */
    private final String entityId;
    /** 中文显示名（命名实体显示自定义名） */
    private final String displayName;
    /** 实体默认中文名（类型名） */
    private final String baseName;
    /** 实体自定义名（命名牌命名），未命名为 {@code null} */
    private final String customName;
    /** 数据版本 */
    private final int dataVersion;
    /** 实体 UUID（实例辅助信息，不参与类型身份去重） */
    private final String uuid;
    /** 识别时所在坐标（实例辅助信息，不参与类型身份去重） */
    private final int blockX;
    private final int blockY;
    private final int blockZ;

    public EntityIdentity(String entityId, String displayName, String baseName, String customName,
                          int dataVersion, String uuid, int blockX, int blockY, int blockZ) {
        this.entityId = entityId;
        this.displayName = displayName;
        this.baseName = baseName;
        this.customName = customName;
        this.dataVersion = dataVersion;
        this.uuid = uuid;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
    }

    public String entityId() {
        return entityId;
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

    public int dataVersion() {
        return dataVersion;
    }

    public String uuid() {
        return uuid;
    }

    public int blockX() {
        return blockX;
    }

    public int blockY() {
        return blockY;
    }

    public int blockZ() {
        return blockZ;
    }

    /** 是否命名实体（命名牌命名） */
    public boolean isNamed() {
        return customName != null && !customName.isBlank();
    }

    /**
     * 稳定的类型身份键（判据与 {@link #equals} 一致）。
     *
     * <p>UUID 与坐标不参与：同一个实体实例移动后仍是同一种实体身份；不同命名的僵尸与普通僵尸
     * 是两个身份。</p>
     */
    public String identityKey() {
        StringBuilder sb = new StringBuilder(entityId);
        if (isNamed()) sb.append('#').append(customName);
        return sb.toString();
    }

    /** 序列化为 JSON（中文字段，兼容既有存档格式） */
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("实体ID", entityId);
        obj.addProperty("显示名称", displayName);
        obj.addProperty("原始名称", baseName);
        if (customName != null) obj.addProperty("自定义名称", customName);
        if (uuid != null) obj.addProperty("UUID", uuid);
        obj.addProperty("坐标X", blockX);
        obj.addProperty("坐标Y", blockY);
        obj.addProperty("坐标Z", blockZ);
        obj.addProperty("数据版本", dataVersion);
        return obj;
    }

    /** 从 JSON 反序列化（中文字段优先，兼容旧版英文字段） */
    public static EntityIdentity fromJsonObject(JsonObject obj) {
        if (obj == null) return null;
        String entityId = strBoth(obj, "实体ID", "entityId", null);
        if (entityId == null || entityId.isBlank()) return null;

        String displayName = strBoth(obj, "显示名称", "displayName", entityId);
        String baseName = strBoth(obj, "原始名称", "baseName", displayName);
        String customName = strBoth(obj, "自定义名称", "customName", null);
        int dataVersion = intBoth(obj, "数据版本", "dataVersion", 0);
        String uuid = strBoth(obj, "UUID", "uuid", null);
        int x = intBoth(obj, "坐标X", "blockX", 0);
        int y = intBoth(obj, "坐标Y", "blockY", 0);
        int z = intBoth(obj, "坐标Z", "blockZ", 0);
        return new EntityIdentity(entityId, displayName, baseName, customName,
            dataVersion, uuid, x, y, z);
    }

    private static String strBoth(JsonObject obj, String cnKey, String enKey, String fallback) {
        if (obj.has(cnKey) && !obj.get(cnKey).isJsonNull()) return obj.get(cnKey).getAsString();
        if (obj.has(enKey) && !obj.get(enKey).isJsonNull()) return obj.get(enKey).getAsString();
        return fallback;
    }

    private static int intBoth(JsonObject obj, String cnKey, String enKey, int fallback) {
        if (obj.has(cnKey) && !obj.get(cnKey).isJsonNull()) return obj.get(cnKey).getAsInt();
        if (obj.has(enKey) && !obj.get(enKey).isJsonNull()) return obj.get(enKey).getAsInt();
        return fallback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityIdentity other)) return false;
        return entityId.equals(other.entityId) && Objects.equals(customName, other.customName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, customName);
    }

    @Override
    public String toString() {
        return entityId;
    }
}
