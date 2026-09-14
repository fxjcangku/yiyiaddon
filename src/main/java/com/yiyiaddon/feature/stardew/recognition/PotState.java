package com.yiyiaddon.feature.stardew.recognition;

/**
 * 种植盆干湿状态。
 *
 * <p>DRY / WET 是基础观察，不等于准确水量；WET 不能直接当满水。真实水量优先
 * 从土壤检测仪 / 物品组件读取，读不到时保持 UNKNOWN，用已验证的干湿与交互规则。</p>
 */
public enum PotState {
    /** 干燥 */
    DRY("干燥"),
    /** 湿润 */
    WET("湿润"),
    /** 未知（无法可靠识别干湿） */
    UNKNOWN("未知");

    private final String displayName;

    PotState(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
