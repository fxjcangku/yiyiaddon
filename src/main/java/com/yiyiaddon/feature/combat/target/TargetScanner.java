package com.yiyiaddon.feature.combat.target;

import com.yiyiaddon.feature.combat.config.KillAuraSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 杀戮光环的目标筛选：在半径内挑候选实体、逐条套用过滤链、按优先级排序截断。
 *
 * <p><b>蓝本</b>：{@code KillAura.java:398-451}（过滤链 {@code entityCheck}）+
 * {@code utils/entity/TargetUtils.java:41-57}（候选收集与排序）。框架层（{@code TargetUtils} /
 * {@code PlayerUtils} / {@code EntityUtils}）按本项目做法重写，只用原版 API，无任何第三方依赖。</p>
 *
 * <p><b>过滤链判定顺序与判据逐条对齐蓝本</b>（行号为蓝本 {@code KillAura.java} 实测）：</p>
 * <ol>
 *     <li>{@code :399} 自己 / 相机实体 → 排除；</li>
 *     <li>{@code :400-401} 已死亡 / 正在死亡 / 非存活 → 排除；</li>
 *     <li>{@code :403-409} 距离：玩家坐标夹到目标碰撞箱上再比 {@code range}（等于「碰撞箱最近点」，
 *         不是脚点）；</li>
 *     <li>{@code :411} 实体种类白名单；</li>
 *     <li>{@code :412} {@code ignoreNamed} 且有自定义名 → 排除；</li>
 *     <li>{@code :413} 视线不通且超出 {@code wallsRange} → 排除；</li>
 *     <li>{@code :414-419} {@code ignoreTamed} 且目标是你驯服的 → 排除；</li>
 *     <li>{@code :420-424} {@code ignorePassive}：末影人未激怒、猪灵/僵尸猪灵/狼未敌对 → 排除；</li>
 *     <li>{@code :425-430} 玩家分支：创造模式 → 排除；{@code shieldMode == Ignore} 且举盾 → 排除；</li>
 *     <li>{@code :431-449} 幼年过滤：敌对生物（僵尸 / 猪灵 / 疣猪兽 / 僵尸疣猪兽）按
 *         {@code hostileMobAgeFilter}，被动生物（{@code AgeableMob} 且非青蛙非鹦鹉）按
 *         {@code passiveMobAgeFilter}；</li>
 *     <li>其余放行（蓝本 {@code :450}）。</li>
 * </ol>
 *
 * <p><b>与蓝本的差异（用户裁定，不可自行改回）</b>：</p>
 * <ul>
 *     <li>蓝本 {@code :427} 的好友过滤 {@code Friends.get().shouldAttack(player)} <b>整条删除</b>
 *         ——本项目不建好友体系（依据 {@code 39-阶段10-附录A} 12.4 与 {@code 37-阶段10} 5.2）；</li>
 *     <li>蓝本 {@code :429} 的假人免伤分支（{@code FakePlayerEntity.noHit}）<b>留空</b>
 *         ——本项目无假人体系，该类不存在，判据恒假（不因此排除任何目标）；</li>
 *     <li>蓝本 {@code TargetUtils.java:48-50} 会把假人名单里的实体也加进候选，
 *         本项目同样留空（无假人体系）。</li>
 * </ul>
 */
public final class TargetScanner {

    private final Minecraft mc = Minecraft.getInstance();

    /** 设置项的唯一数据源（调用方的模块持有） */
    private final KillAuraSettings settings;

    public TargetScanner(KillAuraSettings settings) {
        this.settings = settings;
    }

    /**
     * 收集半径内的候选目标并按优先级排序、按上限截断。
     *
     * <p>对应蓝本 {@code TargetUtils.getList}（{@code TargetUtils.java:41-57}）：
     * 遍历 {@code mc.level.entitiesForRendering()} 逐个过 {@link #entityCheck(Entity)}，排序后
     * 直接截断到 {@code maxCount}（蓝本 {@code :52-56} 的 fast list trimming）。</p>
     */
    public void scan(List<Entity> out, SortPriority priority, int maxCount) {
        out.clear();
        if (mc.level == null) return;

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity != null && entityCheck(entity)) out.add(entity);
        }

        out.sort(priority);
        if (out.size() > maxCount) out.subList(maxCount, out.size()).clear();
    }

    /** 蓝本 {@code KillAura.entityCheck}，{@code :398-451}（差异见类注释） */
    public boolean entityCheck(Entity entity) {
        if (entity == null || mc.player == null || mc.level == null) return false;

        // 蓝本 :399 排除自己与相机实体
        if (entity.equals(mc.player) || entity.equals(mc.getCameraEntity())) return false;

        // 蓝本 :400-401 排除已死亡与正在死亡的实体
        if ((entity instanceof LivingEntity living && living.isDeadOrDying()) || !entity.isAlive()) return false;

        // 蓝本 :403-409 距离按碰撞箱最近点算
        AABB hitbox = entity.getBoundingBox();
        if (!isWithin(
                Mth.clamp(mc.player.getX(), hitbox.minX, hitbox.maxX),
                Mth.clamp(mc.player.getY(), hitbox.minY, hitbox.maxY),
                Mth.clamp(mc.player.getZ(), hitbox.minZ, hitbox.maxZ),
                settings.range)) return false;

        // 蓝本 :411 实体种类白名单
        if (!settings.includesEntityTypeId(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString())) {
            return false;
        }

        // 蓝本 :412 命名生物
        if (settings.ignoreNamed && entity.hasCustomName()) return false;

        // 蓝本 :413 视线不通时的穿墙阈值
        if (!canSeeEntity(entity) && !isWithinEntity(entity, settings.wallsRange)) return false;

        // 蓝本 :414-419 已驯服生物
        if (settings.ignoreTamed) {
            if (entity instanceof OwnableEntity tameable
                    && tameable.getOwner() != null
                    && tameable.getOwner().equals(mc.player)) {
                return false;
            }
        }

        // 蓝本 :420-424 被动生物：未激怒就不碰
        if (settings.ignorePassive) {
            if (entity instanceof EnderMan enderman && !enderman.isCreepy()) return false;
            if ((entity instanceof Piglin || entity instanceof ZombifiedPiglin || entity instanceof Wolf)
                    && !((Mob) entity).isAggressive()) {
                return false;
            }
        }

        // 蓝本 :425-430 玩家分支
        if (entity instanceof Player player) {
            if (player.isCreative()) return false;
            // 蓝本 :427 好友过滤：本项目按用户裁定整条删除（无好友体系）
            if (settings.shieldMode == KillAuraSettings.ShieldMode.IGNORE && player.isBlocking()) return false;
            // 蓝本 :429 假人免伤（FakePlayerEntity.noHit）：本项目无假人体系 → 判据恒假，留空
        }

        // 蓝本 :431-449 幼年过滤
        if (entity instanceof LivingEntity living) {
            // 有幼年变种的敌对生物：僵尸 / 猪灵 / 疣猪兽 / 僵尸疣猪兽
            if (entity instanceof Zombie || entity instanceof Piglin
                    || entity instanceof Hoglin || entity instanceof Zoglin) {
                return switch (settings.hostileMobAgeFilter) {
                    case BABY -> living.isBaby();
                    case ADULT -> !living.isBaby();
                    case BOTH -> true;
                };
            }
            // 有幼年变种的被动生物：动物与村民（青蛙、鹦鹉不算）
            if (entity instanceof AgeableMob && (!(entity instanceof Frog || entity instanceof Parrot))) {
                return switch (settings.passiveMobAgeFilter) {
                    case BABY -> living.isBaby();
                    case ADULT -> !living.isBaby();
                    case BOTH -> true;
                };
            }
        }

        // 蓝本 :450 其余放行
        return true;
    }

    // ── 框架层重写（蓝本 PlayerUtils 对应物，逐条注明出处） ──

    /** 蓝本 {@code PlayerUtils.isWithin(double,double,double,double)}（{@code PlayerUtils.java:288-290}）：平方距离 ≤ r² */
    private boolean isWithin(double x, double y, double z, double range) {
        double dx = mc.player.getX() - x;
        double dy = mc.player.getY() - y;
        double dz = mc.player.getZ() - z;
        return dx * dx + dy * dy + dz * dz <= range * range;
    }

    /** 蓝本 {@code PlayerUtils.isWithin(Entity, double)}（{@code PlayerUtils.java:276-278}） */
    private boolean isWithinEntity(Entity entity, double range) {
        return isWithin(entity.getX(), entity.getY(), entity.getZ(), range);
    }

    /**
     * 蓝本 {@code PlayerUtils.canSeeEntity}（{@code PlayerUtils.java:115-128}）。
     *
     * <p>从玩家眼睛分别看向目标的脚与眼，两段里有一段没被方块挡住就算看得见
     * （{@code ClipContext.Block.COLLIDER} + 不计流体，与蓝本一致）。</p>
     */
    private boolean canSeeEntity(Entity entity) {
        if (mc.level == null || mc.player == null) return false;

        double eyeX = mc.player.getX();
        double eyeY = mc.player.getY() + mc.player.getEyeHeight();
        double eyeZ = mc.player.getZ();

        boolean canSeeFeet = clipMiss(eyeX, eyeY, eyeZ, entity.getX(), entity.getY(), entity.getZ());
        boolean canSeeEyes = clipMiss(eyeX, eyeY, eyeZ, entity.getX(),
                entity.getY() + entity.getEyeHeight(), entity.getZ());

        return canSeeFeet || canSeeEyes;
    }

    /** 两点之间是否无方块阻挡（{@code HitResult.Type.MISS} 即无命中） */
    private boolean clipMiss(double x1, double y1, double z1, double x2, double y2, double z2) {
        if (mc.level == null) return true;
        return mc.level.clip(new ClipContext(
                new Vec3(x1, y1, z1),
                new Vec3(x2, y2, z2),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                mc.player)).getType() == HitResult.Type.MISS;
    }
}
