package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.model.VillagerStation;

import java.util.Objects;

/**
 * 自动图书管理员 · 交易位验证器。
 *
 * <p>只做一件事：拿 {@link MarkerBlockAccess} 给的方块事实判定工位是否可用。
 * 校验分两步——岩浆块存在性（硬失败）与讲台朝向（信息性，不拦）。</p>
 *
 * <p><b>为什么朝向命中与否都返回成功</b>：这是 1:1 照搬旧项目的既有行为
 * （旧注释原文：「不再检测上方方块类型，直接尝试放置，让游戏决定」）。
 * 讲台尚未放置时朝向自然不匹配，若在此判失败则第一次放置永远进不去；
 * 于是该判据实际空转，真正的放置校验由 {@code LecternOps#validatePlacement} 承担。
 * 迁移不「顺手修正」这一段（第 168 条：旧项目本来如此的行为必须保留并登记）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/MarkerBlockValidator}（27 行），判据逐条照搬。</p>
 */
public final class MarkerBlockValidator {
    private final MarkerBlockAccess blockAccess;

    public MarkerBlockValidator(MarkerBlockAccess blockAccess) {
        this.blockAccess = Objects.requireNonNull(blockAccess, "blockAccess");
    }

    /** 校验固定交易位是否有效（岩浆块标记 + 讲台朝向） */
    public MarkerBlockValidation validate(VillagerStation station) {
        Objects.requireNonNull(station, "station");
        if (!blockAccess.isMagmaBlock(station.markerBlockPosition())) {
            return MarkerBlockValidation.invalid("固定交易位无效：未检测到岩浆块。");
        }
        if (blockAccess.isLecternFacing(station.lecternPosition(), station.lecternFacing())) {
            return MarkerBlockValidation.success();
        }
        // 不再检测上方方块类型，直接尝试放置，让游戏决定
        return MarkerBlockValidation.success();
    }
}
