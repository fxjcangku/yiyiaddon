package com.yiyiaddon.model.identity;

import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Objects;

/**
 * 方块身份：把世界中的方块抽象成「可持久化、可消费」的完整标识。
 *
 * <p>与物品、实体身份严格区分：本类描述世界中的方块状态与方块实体，不做「方块 → 物品」的强行转换。
 * 暂时无法理解但可安全序列化的数据（方块实体原始 SNBT）按原始结构保存，不因解析器不识别而丢弃。</p>
 *
 * <p><b>身份与状态分离：</b></p>
 * <ul>
 *   <li>{@link #identityKey()}：服务器 + 维度 + 方块 ID + 坐标，稳定身份，用于身份库去重；</li>
 *   <li>{@link #stateFingerprint()}：再加上完整方块状态与方块实体数据，用于状态快照去重。</li>
 * </ul>
 *
 * <p>本类为纯数据模型：不访问注册表、不访问游戏状态。</p>
 */
public record BlockIdentity(
    String blockId,
    String blockName,
    String blockState,
    String blockEntityTypeId,
    String blockEntityData,
    String dimension,
    String server,
    int x,
    int y,
    int z,
    int dataVersion,
    String semanticModel,
    String semanticIdentity,
    String semanticName,
    String semanticSource,
    String semanticCertainty,
    String semanticReason
) {

    /** 序列化为 JSON（中文字段，兼容既有存档格式） */
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("方块ID", blockId);
        if (blockName != null && !blockName.isBlank()) obj.addProperty("方块名称", blockName);
        obj.addProperty("方块状态", blockState);
        if (blockEntityTypeId != null) obj.addProperty("方块实体类型", blockEntityTypeId);
        if (blockEntityData != null) obj.addProperty("方块实体数据", blockEntityData);
        obj.addProperty("维度", dimension);
        if (server != null) obj.addProperty("服务器", server);
        obj.addProperty("坐标X", x);
        obj.addProperty("坐标Y", y);
        obj.addProperty("坐标Z", z);
        obj.addProperty("数据版本", dataVersion);
        // 资源包语义字段（缺失不写入，保持旧档结构不变）
        if (semanticModel != null) obj.addProperty("资源模型", semanticModel);
        if (semanticIdentity != null) obj.addProperty("自定义身份", semanticIdentity);
        if (semanticName != null) obj.addProperty("自定义名称", semanticName);
        if (semanticSource != null) obj.addProperty("解析来源", semanticSource);
        if (semanticCertainty != null) obj.addProperty("解析状态", semanticCertainty);
        if (semanticReason != null) obj.addProperty("解析说明", semanticReason);
        return obj;
    }

    /** 从 JSON 反序列化（中文字段优先，兼容旧版英文字段） */
    public static BlockIdentity fromJsonObject(JsonObject obj) {
        if (obj == null) return null;
        String blockId = strBoth(obj, "方块ID", "blockId", null);
        if (blockId == null || blockId.isBlank()) return null;
        return new BlockIdentity(
            blockId,
            strBoth(obj, "方块名称", "blockName", null),
            strBoth(obj, "方块状态", "blockState", ""),
            strBoth(obj, "方块实体类型", "blockEntityTypeId", null),
            strBoth(obj, "方块实体数据", "blockEntityData", null),
            strBoth(obj, "维度", "dimension", ""),
            strBoth(obj, "服务器", "server", "unknown"),
            intBoth(obj, "坐标X", "x", 0),
            intBoth(obj, "坐标Y", "y", 0),
            intBoth(obj, "坐标Z", "z", 0),
            intBoth(obj, "数据版本", "dataVersion", 0),
            strBoth(obj, "资源模型", "semanticModel", null),
            strBoth(obj, "自定义身份", "semanticIdentity", null),
            strBoth(obj, "自定义名称", "semanticName", null),
            strBoth(obj, "解析来源", "semanticSource", null),
            strBoth(obj, "解析状态", "semanticCertainty", null),
            strBoth(obj, "解析说明", "semanticReason", null)
        );
    }

    /** 稳定身份键：服务器 + 维度 + 方块 ID + 坐标（不纳入动态状态） */
    public String identityKey() {
        String s = server == null || server.isBlank() ? "unknown" : server;
        String d = dimension == null ? "" : dimension;
        return s + "|" + d + "|" + blockId + "@" + x + "," + y + "," + z;
    }

    /**
     * 展示名：已确认的中文语义名 → 自定义语义身份 → 原版方块中文名 → 原版 ID。
     *
     * <p>优先展示可读的自定义语义；未解析出语义时退回方块中文名（若已采集），最后才是原版 ID。</p>
     */
    public String displayName() {
        if (semanticName != null && !semanticName.isBlank()) return semanticName;
        if (semanticIdentity != null && !semanticIdentity.isBlank()) return semanticIdentity;
        if (blockName != null && !blockName.isBlank()) return blockName;
        return blockId;
    }

    /** 是否已确认的语义身份；用于界面「已确认 / 未知」筛选 */
    public boolean isConfirmed() {
        return "已确认".equals(semanticCertainty);
    }

    /**
     * 状态指纹：服务器 + 维度 + 坐标 + 方块 ID + 完整方块状态 + 方块实体数据的 SHA-256 前 12 位。
     *
     * <p>同一坐标同一方块的不同状态（干盆 / 湿盆、作物不同阶段）指纹不同，允许分别保存快照；
     * 状态相同则指纹相同，自动去重。</p>
     */
    public String stateFingerprint() {
        return fingerprintKey(server, dimension, blockId, x, y, z, blockState, blockEntityData);
    }

    /**
     * 旧版状态指纹（不含服务器与方块 ID）。
     *
     * <p>仅供读取历史快照时兼容比对，<b>不得</b>用于新数据写入。</p>
     */
    public String legacyStateFingerprint() {
        return fingerprintKey(null, dimension, null, x, y, z, blockState, blockEntityData);
    }

    /** 统一计算指纹；字段顺序固定，避免同一状态因拼接差异重复保存 */
    private static String fingerprintKey(String server, String dimension, String blockId,
                                        int x, int y, int z, String blockState, String blockEntityData) {
        String key = (server == null ? "" : server) + "|"
            + (dimension == null ? "" : dimension) + "|"
            + (blockId == null ? "" : blockId) + "|"
            + x + "," + y + "," + z + "|"
            + (blockState == null ? "" : blockState) + "|"
            + (blockEntityData == null ? "" : blockEntityData);
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                .digest(key.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest, 0, 6);
        } catch (Exception ignored) {
            // 极端降级：退回基于内容 hashCode 的短标识，保证不崩且仍可用于去重
            return Integer.toHexString(Objects.hash(server, dimension, blockId, x, y, z,
                blockState, blockEntityData));
        }
    }

    /**
     * 展示用方块状态：剥掉原版 {@code Block{...}} 包装，只留 {@code 命名空间:ID[属性]}。
     *
     * <p>存储与指纹仍使用原始方块状态，保证旧存档指纹不因展示格式变化失效。</p>
     */
    public String blockStateDisplay() {
        String state = blockState == null ? "" : blockState;
        if (state.startsWith("Block{")) {
            int close = state.indexOf('}');
            if (close > 6) state = state.substring(6, close) + state.substring(close + 1);
        }
        return state.isBlank() ? "无" : state;
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
}
