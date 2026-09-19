package com.yiyiaddon.feature.librarian.model;

/**
 * 自动图书管理员 · 讲台事实状态。
 *
 * <p>描述讲台在「定位 → 放置 → 拆除」刷新交易流程中的当前位置状态，
 * 每个状态对应状态机中讲台相关环节的推进。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/LecternStatus}（23 行）。
 * 旧项目该枚举仅被 {@link VillagerTarget} 的构造校验使用；按 D1 拍板整份照搬。</p>
 */
public enum LecternStatus {
    /** 讲台位置尚未定位 */
    UNLOCATED,
    /** 讲台位置已确定 */
    LOCATED,
    /** 正在放置讲台 */
    PLACING,
    /** 讲台已放置到位 */
    PLACED,
    /** 正在拆除讲台 */
    REMOVING,
    /** 讲台已拆除 */
    REMOVED
}
