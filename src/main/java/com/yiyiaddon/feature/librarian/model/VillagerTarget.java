package com.yiyiaddon.feature.librarian.model;

import java.util.Objects;
import java.util.UUID;

/**
 * 自动图书管理员 · 村民目标。
 *
 * <p>描述一个被选中用于刷附魔的失业村民及其讲台、交易位、交易进度的完整快照。
 * 采用不可变设计，所有状态变更都通过 {@code withXxx()} 克隆出新实例，保证
 * 状态机转换安全。</p>
 *
 * <p><b>构造期强校验（逐条照搬旧项目）</b>：{@code entityId >= -1}；
 * 「未解析」与 {@code entityId == -1} 双向互锁；讲台位置与讲台状态互锁；
 * 绑定交易位时要求 UUID、朝向、讲台位置三者与交易位一致。</p>
 *
 * <p><b>实测使用面</b>：旧项目服务层只用 3 参构造 {@code (uuid, entityId, position)}
 * 生成快照，其余字段与 {@code withXxx()} 克隆方法零调用；按 D1 拍板整份照搬
 * （不擅自删减旧项目既有结构，最小改动）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/VillagerTarget}（182 行），逐字照搬。</p>
 */
public record VillagerTarget(
    /** 村民 UUID */
    UUID uuid,
    /** 客户端实体 ID（未解析时为 -1） */
    int entityId,
    /** 村民坐标 */
    BlockPosition position,
    /** 目标状态 */
    VillagerTargetStatus status,
    /** 讲台状态 */
    LecternStatus lecternStatus,
    /** 讲台位置（未定位时为 null） */
    BlockPosition lecternPosition,
    /** 村民朝向 */
    HorizontalDirection direction,
    /** 交易状态 */
    VillagerTradeStatus tradeStatus,
    /** 固定交易位（可为 null） */
    VillagerStation station
) {
    /** 未解析实体 ID 常量 */
    public static final int UNRESOLVED_ENTITY_ID = -1;

    public VillagerTarget {
        Objects.requireNonNull(uuid, "uuid");
        Objects.requireNonNull(position, "position");
        Objects.requireNonNull(status, "status");
        Objects.requireNonNull(lecternStatus, "lecternStatus");
        Objects.requireNonNull(tradeStatus, "tradeStatus");
        if (entityId < UNRESOLVED_ENTITY_ID) throw new IllegalArgumentException("entityId 不能小于 -1");
        if (status == VillagerTargetStatus.UNRESOLVED && entityId != UNRESOLVED_ENTITY_ID) {
            throw new IllegalArgumentException("未解析村民必须使用未解析实体 ID");
        }
        if (status != VillagerTargetStatus.UNRESOLVED && entityId == UNRESOLVED_ENTITY_ID) {
            throw new IllegalArgumentException("仅未解析村民可使用未解析实体 ID");
        }
        boolean positionRequired = lecternStatus == LecternStatus.LOCATED
            || lecternStatus == LecternStatus.PLACING
            || lecternStatus == LecternStatus.PLACED
            || lecternStatus == LecternStatus.REMOVING;
        if (positionRequired && lecternPosition == null) {
            throw new IllegalArgumentException("当前讲台状态必须包含讲台位置");
        }
        if (lecternStatus == LecternStatus.UNLOCATED && lecternPosition != null) {
            throw new IllegalArgumentException("未定位讲台不能包含讲台位置");
        }
        if (station != null) {
            if (!uuid.equals(station.villagerUuid())) throw new IllegalArgumentException("交易位必须属于当前村民");
            if (direction != station.villagerDirection()) throw new IllegalArgumentException("村民朝向必须与交易位一致");
            if (!station.lecternPosition().equals(lecternPosition)) {
                throw new IllegalArgumentException("当前讲台位置必须与交易位一致");
            }
        }
    }

    /** 便捷构造：无朝向、无交易位、交易状态为未打开 */
    public VillagerTarget(
        UUID uuid,
        int entityId,
        BlockPosition position,
        VillagerTargetStatus status,
        LecternStatus lecternStatus,
        BlockPosition lecternPosition
    ) {
        this(
            uuid,
            entityId,
            position,
            status,
            lecternStatus,
            lecternPosition,
            null,
            VillagerTradeStatus.NOT_OPENED,
            null
        );
    }

    /** 便捷构造：默认选中状态、讲台未定位 */
    public VillagerTarget(UUID uuid, int entityId, BlockPosition position) {
        this(uuid, entityId, position, VillagerTargetStatus.SELECTED, LecternStatus.UNLOCATED, null);
    }

    /** 克隆并更新村民坐标 */
    public VillagerTarget withPosition(BlockPosition currentPosition) {
        return new VillagerTarget(
            uuid, entityId, currentPosition, status, lecternStatus, lecternPosition, direction, tradeStatus, station
        );
    }

    /** 克隆并解析实体：更新实体 ID、坐标，标记为可用 */
    public VillagerTarget withResolvedEntity(int currentEntityId, BlockPosition currentPosition) {
        return new VillagerTarget(
            uuid,
            currentEntityId,
            currentPosition,
            VillagerTargetStatus.AVAILABLE,
            lecternStatus,
            lecternPosition,
            direction,
            tradeStatus,
            station
        );
    }

    /** 克隆并标记为未解析（实体 ID 置 -1） */
    public VillagerTarget asUnresolved() {
        return new VillagerTarget(
            uuid,
            UNRESOLVED_ENTITY_ID,
            position,
            VillagerTargetStatus.UNRESOLVED,
            lecternStatus,
            lecternPosition,
            direction,
            tradeStatus,
            station
        );
    }

    /** 克隆并更新目标状态（未解析时同步置实体 ID） */
    public VillagerTarget withStatus(VillagerTargetStatus currentStatus) {
        int currentEntityId = currentStatus == VillagerTargetStatus.UNRESOLVED ? UNRESOLVED_ENTITY_ID : entityId;
        return new VillagerTarget(
            uuid, currentEntityId, position, currentStatus, lecternStatus, lecternPosition, direction, tradeStatus, station
        );
    }

    /** 克隆并更新讲台状态与位置（位置变化时清空交易位） */
    public VillagerTarget withLectern(LecternStatus currentLecternStatus, BlockPosition currentLecternPosition) {
        VillagerStation currentStation = station;
        if (currentStation != null && !currentStation.lecternPosition().equals(currentLecternPosition)) {
            currentStation = null;
        }
        return new VillagerTarget(
            uuid,
            entityId,
            position,
            status,
            currentLecternStatus,
            currentLecternPosition,
            direction,
            tradeStatus,
            currentStation
        );
    }

    /** 克隆并绑定交易位（同步更新讲台位置与村民朝向） */
    public VillagerTarget withStation(VillagerStation currentStation, LecternStatus currentLecternStatus) {
        Objects.requireNonNull(currentStation, "currentStation");
        return new VillagerTarget(
            uuid,
            entityId,
            position,
            status,
            currentLecternStatus,
            currentStation.lecternPosition(),
            currentStation.villagerDirection(),
            tradeStatus,
            currentStation
        );
    }

    /** 克隆并更新交易状态 */
    public VillagerTarget withTradeStatus(VillagerTradeStatus currentTradeStatus) {
        return new VillagerTarget(
            uuid, entityId, position, status, lecternStatus, lecternPosition, direction, currentTradeStatus, station
        );
    }
}
