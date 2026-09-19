package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.model.VillagerTarget;

import java.util.Optional;

/**
 * 自动图书管理员 · 村民搜索契约。
 *
 * <p>把「找谁刷附魔」的判定收敛成四个查询：搜索、存在性、失业、已就职。
 * 实现落 {@code platform/VillagerSearchProbe}（Minecraft 侧读取实体）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/VillagerSearchService}（20 行），逐字照搬。</p>
 */
public interface VillagerSearchService {
    /** 在半径内查找最近的失业村民 */
    Optional<VillagerTarget> findNearestUnemployedVillager(int radiusBlocks);

    /** 目标村民是否仍有效（存在且存活） */
    boolean isValid(VillagerTarget target);

    /** 目标村民是否为失业状态 */
    boolean isUnemployed(VillagerTarget target);

    /** 目标村民是否为图书管理员 */
    boolean isLibrarian(VillagerTarget target);
}
