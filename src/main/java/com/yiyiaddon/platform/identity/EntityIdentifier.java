package com.yiyiaddon.platform.identity;

import com.yiyiaddon.model.identity.EntityIdentity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.HashSet;
import java.util.Set;

/**
 * 实体识别适配层：从 {@link Entity} 提取完整 {@link EntityIdentity}。
 *
 * <p>与物品识别职责分离：实体直接识别为实体身份，不强行转换成物品。除类型身份外，
 * 额外采集 UUID 与坐标等实例辅助信息。</p>
 *
 * <p><b>26.2 口径</b>：实体类型常量从 {@code EntityType} 拆到了
 * {@code net.minecraft.world.entity.EntityTypeIds}，且那边给的是
 * {@link ResourceKey}（登记键）而不是 {@link EntityType} 实例本身。
 * 「常量 → 实例」这一步由本类统一承担，调用方不再直接摸注册表。</p>
 */
public final class EntityIdentifier {

    private EntityIdentifier() {
    }

    /**
     * 把 26.2 的实体类型登记键解析成实体类型实例；未注册返回 {@code null}。
     *
     * <p>注册表里的实体类型是单例，因此解析结果可以直接用 {@code ==} 比较。</p>
     */
    public static EntityType<?> typeOf(ResourceKey<EntityType<?>> key) {
        if (key == null) return null;
        return BuiltInRegistries.ENTITY_TYPE.getValue(key);
    }

    /**
     * 批量解析实体类型登记键，顺序与入参一致。
     *
     * <p>未注册的键直接跳过，不会把 {@code null} 塞进结果集 —— 调用方随后多用
     * {@code Set.copyOf} / {@code Map.of} 这类拒绝空值的容器，交给它们会直接抛异常。</p>
     */
    @SafeVarargs
    public static Set<EntityType<?>> typesOf(ResourceKey<EntityType<?>>... keys) {
        Set<EntityType<?>> result = new HashSet<>();
        if (keys != null) {
            for (ResourceKey<EntityType<?>> key : keys) {
                EntityType<?> type = typeOf(key);
                if (type != null) result.add(type);
            }
        }
        return Set.copyOf(result);
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
