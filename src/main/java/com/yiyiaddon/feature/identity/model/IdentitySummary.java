package com.yiyiaddon.feature.identity.model;

import java.util.List;

/**
 * 识别结果摘要：一次识别的可展示产出。
 *
 * <p>把「识别到什么」与「怎么显示」分开：识别层只产出本对象，聊天播报与模块页面都消费它，
 * 因此同一份识别结果不会出现两套描述逻辑。</p>
 *
 * @param kind     识别类型
 * @param success  是否识别成功
 * @param title    结果标题（识别成功时为名称，失败时为失败说明）
 * @param rows     关键字段行
 * @param fileName 写入身份库的文件名；未写入为 {@code null}
 */
public record IdentitySummary(Kind kind, boolean success, String title, List<Row> rows, String fileName) {

    /** 识别类型 */
    public enum Kind {
        ITEM("物品"),
        BLOCK("方块"),
        ENTITY("实体");

        private final String displayName;

        Kind(String displayName) {
            this.displayName = displayName;
        }

        public String displayName() {
            return displayName;
        }
    }

    /** 关键字段行 */
    public record Row(String label, String value) {
    }

    public static IdentitySummary ok(Kind kind, String title, List<Row> rows, String fileName) {
        return new IdentitySummary(kind, true, title, List.copyOf(rows), fileName);
    }

    public static IdentitySummary failed(Kind kind, String reason) {
        return new IdentitySummary(kind, false, "未识别到" + kind.displayName(),
                List.of(new Row("原因", reason)), null);
    }

    /** 是否已写入身份库 */
    public boolean saved() {
        return fileName != null && !fileName.isBlank();
    }
}
