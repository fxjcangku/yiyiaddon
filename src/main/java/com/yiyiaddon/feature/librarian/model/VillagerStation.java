package com.yiyiaddon.feature.librarian.model;

import java.util.Objects;
import java.util.UUID;

/**
 * 自动图书管理员 · 固定交易位。
 *
 * <p>描述一个失业村民的「刷附魔工位」几何布局：村民 + 岩浆块（标记块）+
 * 讲台 + 玩家站位。所有坐标由村民坐标与朝向推导，保证几何一致性。</p>
 *
 * <p><b>构造期强校验（逐条照搬旧项目）</b>：四项几何关系任一不成立即抛异常，
 * 防止状态机拿着错位的工位去放讲台：
 * 岩浆块 = 村民 + 朝向；讲台 = 岩浆块上方；玩家站位 = 岩浆块 + 同向；阅读面 = 村民朝向反向。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/VillagerStation}（97 行），逐字照搬。</p>
 */
public record VillagerStation(
    /** 村民 UUID */
    UUID villagerUuid,
    /** 村民坐标 */
    BlockPosition villagerPosition,
    /** 村民朝向 */
    HorizontalDirection villagerFacing,
    /** 岩浆块（标记块）坐标，位于村民前方一格 */
    BlockPosition markerBlockPosition,
    /** 讲台坐标，位于岩浆块上方 */
    BlockPosition lecternPosition,
    /** 玩家站位坐标，位于岩浆块沿村民朝向继续偏移一格 */
    BlockPosition playerStandPosition,
    /** 讲台阅读面朝向（与村民朝向相反） */
    HorizontalDirection lecternFacing,
    /** 交易位验证状态 */
    StationValidationStatus validationStatus
) {
    public VillagerStation {
        Objects.requireNonNull(villagerUuid, "villagerUuid");
        Objects.requireNonNull(villagerPosition, "villagerPosition");
        Objects.requireNonNull(villagerFacing, "villagerFacing");
        Objects.requireNonNull(markerBlockPosition, "markerBlockPosition");
        Objects.requireNonNull(lecternPosition, "lecternPosition");
        Objects.requireNonNull(playerStandPosition, "playerStandPosition");
        Objects.requireNonNull(lecternFacing, "lecternFacing");
        Objects.requireNonNull(validationStatus, "validationStatus");
        if (!markerBlockPosition.equals(villagerPosition.offset(villagerFacing))) {
            throw new IllegalArgumentException("岩浆块必须位于村民前方一格");
        }
        if (!lecternPosition.equals(markerBlockPosition.up())) {
            throw new IllegalArgumentException("讲台必须位于岩浆块上方");
        }
        if (!playerStandPosition.equals(markerBlockPosition.offset(villagerFacing))) {
            throw new IllegalArgumentException("玩家站位必须位于岩浆块沿村民朝向继续偏移一格的位置");
        }
        if (lecternFacing != villagerFacing.opposite()) {
            throw new IllegalArgumentException("讲台阅读面必须朝向玩家交互侧");
        }
    }

    /** 根据村民坐标与朝向推导完整交易位（其余坐标自动计算） */
    public static VillagerStation create(
        UUID villagerUuid,
        BlockPosition villagerPosition,
        HorizontalDirection villagerDirection,
        StationValidationStatus validationStatus
    ) {
        BlockPosition markerBlockPosition = villagerPosition.offset(villagerDirection);
        return new VillagerStation(
            villagerUuid,
            villagerPosition,
            villagerDirection,
            markerBlockPosition,
            markerBlockPosition.up(),
            markerBlockPosition.offset(villagerDirection),
            villagerDirection.opposite(),
            validationStatus
        );
    }

    /** 克隆并更新验证状态 */
    public VillagerStation withValidationStatus(StationValidationStatus currentStatus) {
        return new VillagerStation(
            villagerUuid,
            villagerPosition,
            villagerFacing,
            markerBlockPosition,
            lecternPosition,
            playerStandPosition,
            lecternFacing,
            currentStatus
        );
    }

    /** 返回村民朝向（别名，语义更清晰） */
    public HorizontalDirection villagerDirection() {
        return villagerFacing;
    }

    /** 返回讲台阅读面朝向（别名，语义更清晰） */
    public HorizontalDirection lecternDirection() {
        return lecternFacing;
    }
}
