package com.yiyiaddon.feature.bonemeal.model;

import java.util.Arrays;

/**
 * 自动骨粉的触发模式：范围自动扫描 / 准星精准指向。
 *
 * <p><b>逐字资产</b>：两个枚举常量名逐字取自旧项目 {@code bonemeal/AutoBoneMeal.java:50-53}
 * （旧框架的枚举名本身就是中文，且按 {@code name()} 落盘）。本项目同样用常量名读写，
 * 因此旧存档里写的 {@code 范围自动扫描} 仍能读回，且设置名、显示名、落盘名三处同源，
 * 不会出现「界面显示中文、存档存另一套」的错位（开发习惯第 172-175 条）。</p>
 */
public enum TriggerMode {

    /** 自动搜索周围所有目标，每轮（节流周期）可同时催熟多个 */
    范围自动扫描,

    /** 仅对准星看着的方块生效，准星离开即暂停 */
    准星精准指向;

    /** 设置项标签 / 控制台显示名 / 落盘名（三者必须同源） */
    public String label() {
        return name();
    }

    /** 枚举候选文案：顺序即枚举序（分段控件按它排列） */
    public static String[] labels() {
        return Arrays.stream(values()).map(TriggerMode::label).toArray(String[]::new);
    }

    /** 按落盘名读回；读不到回退 {@code null}，由调用方决定默认值 */
    public static TriggerMode ofName(String name) {
        for (TriggerMode mode : values()) {
            if (mode.name().equals(name)) return mode;
        }
        return null;
    }
}
