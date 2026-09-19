package com.yiyiaddon.feature.librarian.platform;

import com.yiyiaddon.feature.librarian.model.BlockPosition;
import com.yiyiaddon.feature.librarian.model.VillagerTarget;
import com.yiyiaddon.feature.librarian.service.DebugLoggerService;
import com.yiyiaddon.feature.librarian.service.VillagerSearchService;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

/**
 * 自动图书管理员 · 村民搜索实现（Minecraft 适配）。
 *
 * <p>在玩家附近搜索失业村民，支持按实体 ID / UUID 解析村民、判断职业，
 * 供状态机锁定目标村民后推进放置讲台流程。</p>
 *
 * <p><b>实体解析为什么两步走</b>：区块重载后客户端实体 ID 会变，先用 ID 快路径命中，
 * 失败再用玩家周围 64 格的 UUID 扫描兜底，避免目标在寻路途中「凭空消失」。</p>
 *
 * <p>迁移自旧项目 {@code librarian/integration/FabricVillagerSearchService}（99 行）。
 * 命名去掉旧框架前缀，落本项目 {@code platform/}（Minecraft 适配层）。</p>
 */
public final class VillagerSearchProbe implements VillagerSearchService {
    /** UUID 兜底扫描半径（格）：与旧项目逐字一致 */
    private static final int UUID_FALLBACK_RADIUS = 64;

    /** 日志出口：与旧项目同构造签名（旧项目本类内部不产出任何日志，保留以便与模块装配逐字对齐） */
    private final DebugLoggerService logger;
    /** 调试开关：同上，旧项目本类未消费该开关 */
    private final BooleanSupplier debugEnabled;

    public VillagerSearchProbe(DebugLoggerService logger, BooleanSupplier debugEnabled) {
        this.logger = logger;
        this.debugEnabled = debugEnabled;
    }

    @Override
    public Optional<VillagerTarget> findNearestUnemployedVillager(int radiusBlocks) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return Optional.empty();
        AABB box = mc.player.getBoundingBox().inflate(radiusBlocks);
        List<Villager> candidates = mc.level.getEntitiesOfClass(Villager.class, box, this::usable);
        return candidates.stream()
            .filter(this::unemployed)
            .min(Comparator.comparingDouble(entity -> entity.distanceToSqr(mc.player)))
            .map(this::snapshot);
    }

    @Override
    public boolean isValid(VillagerTarget target) {
        return resolve(target).filter(this::usable).isPresent();
    }

    @Override
    public boolean isUnemployed(VillagerTarget target) {
        return resolve(target).filter(this::unemployed).isPresent();
    }

    @Override
    public boolean isLibrarian(VillagerTarget target) {
        return resolve(target).filter(v -> isProfession(v, VillagerProfession.LIBRARIAN)).isPresent();
    }

    /** 解析目标村民实体：实体 ID 快路径 → UUID 兜底扫描 */
    private Optional<Villager> resolve(VillagerTarget target) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return Optional.empty();
        // 先用 entity ID 快速查找
        if (mc.level.getEntity(target.entityId()) instanceof Villager villager
                && target.uuid().equals(villager.getUUID())) {
            return Optional.of(villager);
        }
        // entity ID 在 chunk 重载后可能失效，兜底用 UUID 扫描附近村民
        if (mc.player == null) return Optional.empty();
        AABB box = mc.player.getBoundingBox().inflate(UUID_FALLBACK_RADIUS);
        return mc.level.getEntitiesOfClass(Villager.class, box,
                v -> target.uuid().equals(v.getUUID()))
            .stream().findFirst();
    }

    private boolean usable(Villager villager) {
        return villager.isAlive() && !villager.isRemoved();
    }

    private boolean unemployed(Villager villager) {
        return isProfession(villager, VillagerProfession.NONE);
    }

    /**
     * 职业判定。
     *
     * <p>26.1.2 里 {@code VillagerProfession} 常量（如 {@code LIBRARIAN} / {@code NONE}）是
     * {@code ResourceKey}，注册表值是 Record；不能用 {@code ==} 或 {@code value().equals()} 比较
     * （会把傻子与失业村民误匹配），必须比注册表键。本项目统一用
     * {@code Holder#is(Identifier)} 这一个判据（与自动村民交易模块同源，第 169 条）。</p>
     */
    private boolean isProfession(Villager villager, ResourceKey<VillagerProfession> key) {
        return villager.getVillagerData().profession().is(key.identifier());
    }

    /** 生成只含身份与坐标的村民快照（与旧项目同一入口） */
    private VillagerTarget snapshot(Villager villager) {
        return new VillagerTarget(
            villager.getUUID(),
            villager.getId(),
            new BlockPosition(villager.getBlockX(), villager.getBlockY(), villager.getBlockZ())
        );
    }
}
