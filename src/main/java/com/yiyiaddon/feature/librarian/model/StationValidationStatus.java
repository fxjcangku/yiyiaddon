package com.yiyiaddon.feature.librarian.model;

/**
 * 自动图书管理员 · 固定交易位验证状态。
 *
 * <p>描述一个村民交易位（岩浆块 + 讲台 + 玩家站位）的校验结果，
 * 只有 {@link #VALID} 的交易位才允许进入放置讲台流程。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/StationValidationStatus}（17 行）。
 * 实测旧项目只用到 {@link #UNVALIDATED}（工位探测写入时固定该值，
 * 后续校验走 {@code MarkerBlockValidator} 而不回写本字段），其余取值为既有结构，按 D1 拍板照搬。</p>
 */
public enum StationValidationStatus {
    /** 尚未校验 */
    UNVALIDATED,
    /** 校验通过，交易位可用 */
    VALID,
    /** 校验失败，交易位不可用 */
    INVALID
}
