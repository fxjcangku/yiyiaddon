package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.model.VillagerStation;
import com.yiyiaddon.feature.librarian.model.VillagerTarget;

import java.util.Optional;

/**
 * 自动图书管理员 · 固定交易位契约。
 *
 * <p>「探测工位」与「校验工位」分两步：探测只在到达村民附近后做一次（含方向遍历与
 * 两遍候选策略），校验是纯静态判定（只查岩浆块与讲台朝向，不重新探测，避免村民
 * 临时转身造成误判）。实现落 {@code platform/StationProbe}。</p>
 *
 * <p>迁移自旧项目 {@code librarian/service/VillagerStationService}（22 行）。
 * 按 D1 拍板，删除旧项目中零调用的默认方法 {@code calculateMarkerBlockPosition}
 * （工位坐标一律由 {@code VillagerStation.create} 推导，不需要该入口）。</p>
 */
public interface VillagerStationService {
    /** 检测村民当前所在的固定交易位（岩浆块标记） */
    Optional<VillagerStation> detect(VillagerTarget target);

    /** 校验交易位是否有效（岩浆块 + 讲台朝向） */
    MarkerBlockValidation validate(VillagerStation station);
}
