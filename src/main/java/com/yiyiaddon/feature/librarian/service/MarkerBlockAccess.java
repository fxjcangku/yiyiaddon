package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.model.BlockPosition;
import com.yiyiaddon.feature.librarian.model.HorizontalDirection;

/**
 * 自动图书管理员 · 方块查询契约。
 *
 * <p>把「工位校验需要看的四个方块事实」抽成接口，使 {@link MarkerBlockValidator}
 * 不直接依赖 Minecraft：岩浆块、空气、讲台朝向、能否放置讲台。
 * 实现落 {@code platform/StationProbe}。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/MarkerBlockAccess}（19 行），逐字照搬。</p>
 */
public interface MarkerBlockAccess {
    /** 指定位置是否为岩浆块 */
    boolean isMagmaBlock(BlockPosition position);

    /** 指定位置是否为空气 */
    boolean isAir(BlockPosition position);

    /** 讲台是否朝向指定方向 */
    boolean isLecternFacing(BlockPosition position, HorizontalDirection facing);

    /** 指定位置能否放置讲台 */
    boolean canPlaceLectern(BlockPosition position);
}
