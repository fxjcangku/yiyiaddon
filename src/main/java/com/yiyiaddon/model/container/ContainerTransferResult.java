package com.yiyiaddon.model.container;

/**
 * 容器搬运单次操作的结果。
 *
 * <p>调用方据此区分「已移动 / 箱子满 / 无匹配 / 未同步」，避免拿真实背包数据源与
 * 本地菜单槽位交叉判断造成误判。</p>
 */
public enum ContainerTransferResult {

    /** 已发出一次快速移动 */
    MOVED("已移动"),

    /** 有匹配物品但箱子放不下 */
    CHEST_FULL("箱子已满"),

    /** 无匹配物品（已全部移完） */
    NONE("无匹配物品"),

    /** 容器尚未同步稳定，调用方应继续等待而不是关闭容器 */
    NOT_READY("容器未同步");

    private final String displayName;

    ContainerTransferResult(String displayName) {
        this.displayName = displayName;
    }

    /** 中文文案，供界面与提示使用 */
    public String displayName() {
        return displayName;
    }

    /** 是否属于「本次没有实际搬运，但也不算失败」的等待态 */
    public boolean isWaiting() {
        return this == NOT_READY;
    }

    /** 是否属于「本次没有可搬运内容」的终止态 */
    public boolean isEmptyResult() {
        return this == NONE || this == CHEST_FULL;
    }
}
