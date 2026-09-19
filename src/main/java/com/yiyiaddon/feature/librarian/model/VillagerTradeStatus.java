package com.yiyiaddon.feature.librarian.model;

/**
 * 自动图书管理员 · 村民交易状态。
 *
 * <p>描述单个村民交易界面从「未打开」到「验证完成」的完整生命周期，
 * 每个阶段对应状态机 {@code LibrarianState} 中交易环节的推进。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/VillagerTradeStatus}（25 行）。
 * 旧项目该枚举仅被 {@link VillagerTarget} 的构造校验使用；按 D1 拍板整份照搬。</p>
 */
public enum VillagerTradeStatus {
    /** 交易界面尚未打开 */
    NOT_OPENED,
    /** 正在向服务端请求打开交易界面 */
    OPENING,
    /** 交易界面已就绪，可以开始扫描报价 */
    READY,
    /** 交易列表已扫描完毕 */
    SCANNED,
    /** 已匹配到目标附魔报价 */
    MATCHED,
    /** 正在处理交易（购买中） */
    PROCESSING,
    /** 交易已完成库存验证 */
    VERIFIED
}
