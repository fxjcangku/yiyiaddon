package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.model.VillagerStation;

/**
 * 自动图书管理员 · 讲台放置契约。
 *
 * <p>讲台全生命周期：放置 / 拆除 / 障碍检测 / 清障 / 结果校验。
 * 清障与拆除都是「每 tick 调用一次直到返回 SUCCESS」的分步状态机，
 * 调用方按 {@link ActionStatus} 决定继续等还是转失败。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/LecternPlacementService}（24 行），逐字照搬。</p>
 */
public interface LecternPlacementService {
    /** 在交易位放置讲台 */
    ActionResult place(VillagerStation station);

    /** 拆除交易位的讲台 */
    ActionResult breakLectern(VillagerStation station);

    /** 检测讲台位是否有非目标障碍方块 */
    boolean hasObstacle(VillagerStation station);

    /** 挖掉讲台位的障碍方块（自动选最快工具），每 tick 调用一次直到返回 SUCCESS */
    ActionResult breakObstacle(VillagerStation station);

    /** 校验讲台是否可放置 */
    boolean validatePlacement(VillagerStation station);

    /** 校验讲台是否可拆除 */
    boolean validateRemoval(VillagerStation station);
}
