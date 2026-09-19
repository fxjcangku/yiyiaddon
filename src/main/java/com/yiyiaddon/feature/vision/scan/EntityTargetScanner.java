package com.yiyiaddon.feature.vision.scan;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 实体目标扫描器：按名单 + 水平半径挑出要透视的实体。
 *
 * <p><b>范围口径（圆柱，不是球）</b>：只比水平距离，竖直方向不裁剪 —— 脚下的怪物、掉落物与
 * 头顶的目标都要看得见（用户 2026-09-18：「我要看见底下的方块 实体」）。</p>
 *
 * <p><b>遍历口径</b>：用既有世界的 {@code entitiesForRendering()}（与 {@code EspTestModule}、
 * {@code TargetScanner} 同源），只含已加载区块里的实体，数量天然有界。</p>
 *
 * <p><b>快照交接</b>：每刻重建一次不可变列表，渲染侧只读快照，不直接遍历世界。</p>
 */
public final class EntityTargetScanner {

    /** 同时参与绘制的实体上限（实体数量天然有界，这里只是兜底） */
    public static final int MAX_TARGETS = 512;

    private List<Entity> visible = List.of();
    private Set<String> targets = Set.of();
    private List<String> targetIds = List.of();

    /** 绘制用实体快照；渲染线程只读此值 */
    public List<Entity> visible() {
        return visible;
    }

    /** 清空快照（模块关闭时调用） */
    public void clear() {
        visible = List.of();
    }

    /**
     * 每刻重建一次快照。
     *
     * @param level  客户端世界
     * @param player 本地玩家（排除在外，自己不需要透视自己）
     * @param radius 水平半径（格）
     * @param ids    目标实体登记 ID 名单（空 = 不画）
     */
    public void tick(ClientLevel level, Player player, int radius, List<String> ids) {
        if (!ids.equals(targetIds)) {
            targetIds = List.copyOf(ids);
            targets = new HashSet<>(ids);
        }
        if (targets.isEmpty()) {
            if (!visible.isEmpty()) visible = List.of();
            return;
        }

        double playerX = player.getX();
        double playerZ = player.getZ();
        int radiusSqr = radius * radius;
        List<Entity> found = new ArrayList<>();

        for (Entity entity : level.entitiesForRendering()) {
            if (entity == null || entity == player || entity.isRemoved()) continue;
            if (entity.level() != level) continue;
            String typeId = typeId(entity);
            if (typeId == null || !targets.contains(typeId)) continue;
            double dx = entity.getX() - playerX;
            double dz = entity.getZ() - playerZ;
            if (dx * dx + dz * dz > radiusSqr) continue;
            found.add(entity);
        }

        if (found.size() > MAX_TARGETS) {
            found.sort(Comparator.comparingDouble(entity -> entity.distanceToSqr(playerX, entity.getY(), playerZ)));
            found = new ArrayList<>(found.subList(0, MAX_TARGETS));
        }
        visible = List.copyOf(found);
    }

    /** 实体 → 登记 ID（{@code minecraft:zombie} 这种形式）；取不到键的实体跳过，不猜 */
    private static String typeId(Entity entity) {
        Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return id == null ? null : id.toString();
    }
}
