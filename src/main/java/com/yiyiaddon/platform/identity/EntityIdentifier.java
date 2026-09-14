package com.yiyiaddon.platform.identity;

import com.yiyiaddon.model.identity.EntityIdentity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

/**
 * 实体识别适配层：从 {@link Entity} 提取完整 {@link EntityIdentity}。
 *
 * <p>与物品识别职责分离：实体直接识别为实体身份，不强行转换成物品。除类型身份外，
 * 额外采集 UUID 与坐标等实例辅助信息。</p>
 */
public final class EntityIdentifier {

    private EntityIdentifier() {
    }

    /** 提取实体类型 ID（如 {@code minecraft:zombie}）；空实体返回 {@code null} */
    public static String entityIdOf(Entity entity) {
        if (entity == null) return null;
        return BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
    }

    /** 该实体类型 ID 是否存在于当前注册表 */
    public static boolean exists(String entityId) {
        if (entityId == null || entityId.isBlank()) return false;
        Identifier id = Identifier.tryParse(entityId);
        if (id == null) return false;
        return BuiltInRegistries.ENTITY_TYPE.getValue(id) != null;
    }

    /**
     * 识别一个实体为完整身份；空实体返回 {@code null}。
     *
     * <p>类型身份（实体类型 ID + 自定义名）是唯一性判据；UUID 与坐标仅作为实例辅助信息记录，
     * 不参与去重。</p>
     */
    public static EntityIdentity identifyEntity(Entity entity) {
        if (entity == null) return null;

        String entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
        String baseName = clean(entity.getType().getDescription().getString());
        Component customComponent = entity.getCustomName();
        String customName = (customComponent != null && !customComponent.getString().isBlank())
            ? clean(customComponent.getString())
            : null;
        String displayName = customName != null ? customName : baseName;

        String uuid = entity.getUUID().toString();
        var pos = entity.blockPosition();

        return new EntityIdentity(entityId, displayName, baseName, customName,
            currentDataVersion(), uuid, pos.getX(), pos.getY(), pos.getZ());
    }

    /** 剥离颜色代码并去首尾空格 */
    private static String clean(String text) {
        if (text == null) return "";
        return text.replaceAll("§[0-9a-fk-orA-FK-ORx]", "").trim();
    }

    private static int currentDataVersion() {
        try {
            return net.minecraft.SharedConstants.getCurrentVersion().dataVersion().version();
        } catch (Exception ignored) {
            return 0;
        }
    }
}
