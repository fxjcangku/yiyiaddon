package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.model.BlockPosition;

/**
 * 自动图书管理员 · 移动契约。
 *
 * <p>寻路六问：下发、停止、是否在跑、是否到达、当前状态、服务是否可用。
 * 到达判定用「玩家脚下方块到目标方块的距离平方 ≤ 半径²」，由实现负责。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/MovementService}（24 行），逐字照搬。
 * 旧项目另有零调用实现 {@code NoOpMovementService}，按 D1 拍板不搬并登记。</p>
 */
public interface MovementService {
    /** 寻路到目标位置（进入半径内视为到达） */
    MovementStartResult gotoPosition(BlockPosition position, int radiusBlocks);

    /** 停止当前寻路 */
    void stop();

    /** 是否正在寻路 */
    boolean isPathing();

    /** 是否已到达目标位置 */
    boolean hasArrived();

    /** 获取当前移动状态 */
    MovementStatus getStatus();

    /** 移动服务（Baritone）是否可用 */
    boolean isAvailable();
}
