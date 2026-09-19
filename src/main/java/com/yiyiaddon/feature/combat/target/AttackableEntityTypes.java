package com.yiyiaddon.feature.combat.target;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 「可攻击实体类型」的唯一来源。
 *
 * <p>杀戮光环有两处需要这份清单，且分属不同层：</p>
 * <ul>
 *   <li>界面层：目标实体选择器的候选表（{@code ui/console/KillAuraTargetingPage#entityCandidates()}）——
 *       需要显示名与分组，由该页自行加工；</li>
 *   <li>设置层：目标实体名单的默认值（{@code config/KillAuraSettings#defaultEntityTypes()}）——
 *       只需要登记 ID。</li>
 * </ul>
 *
 * <p>黑名单只有这一份。设置层若直接复用界面层的候选表，会造成 config → UI 的反向依赖
 * （载入设置时会顺带类加载 UI），故把过滤口径下沉到本类，两层各自加工。</p>
 *
 * <p><b>黑名单来源</b>：旧框架蓝本 {@code EntityUtils.isAttackable}（{@code EntityUtils.java:46-48}），
 * 逐项 16 个：区域效果云 / 箭 / 下落方块 / 烟花火箭 / 掉落物 / 羊驼唾沫 / 光灵箭 / 末影珍珠 /
 * 喷溅药水 / 滞留药水 / 三叉戟（投掷物）/ 闪电 / 鱼漂 / 经验球 / 经验瓶 / 鸡蛋。</p>
 */
public final class AttackableEntityTypes {

    /** 不可攻击实体（蓝本 16 项，逐字） */
    private static final Set<EntityType<?>> NOT_ATTACKABLE = Set.of(
        EntityType.AREA_EFFECT_CLOUD, EntityType.ARROW, EntityType.FALLING_BLOCK, EntityType.FIREWORK_ROCKET,
        EntityType.ITEM, EntityType.LLAMA_SPIT, EntityType.SPECTRAL_ARROW, EntityType.ENDER_PEARL,
        EntityType.EXPERIENCE_BOTTLE, EntityType.SPLASH_POTION, EntityType.LINGERING_POTION, EntityType.TRIDENT,
        EntityType.LIGHTNING_BOLT, EntityType.FISHING_BOBBER, EntityType.EXPERIENCE_ORB, EntityType.EGG);

    /** 注册表迭代序（原版在前、模组实体在后，与注册表一致；需要别的顺序由调用方自行排） */
    private static List<EntityType<?>> cache;

    private AttackableEntityTypes() {
    }

    /**
     * 全部可攻击实体类型（按注册表迭代序）。
     *
     * <p>注册表在本模组初始化后基本不变，故惰性缓存一次；调用方需要别的顺序（中文名 / ID 字典序）
     * 自行排序，本类不做排序，避免多处口径不一。</p>
     */
    public static List<EntityType<?>> attackable() {
        if (cache == null) {
            List<EntityType<?>> result = new ArrayList<>();
            for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
                if (NOT_ATTACKABLE.contains(type)) continue;
                result.add(type);
            }
            cache = List.copyOf(result);
        }
        return cache;
    }
}
