package com.yiyiaddon.feature.enchant.model;

/**
 * 目标模式：三模式互斥切换，只显示对应配置页，业务流程隔离。
 *
 * <p>逐字来自旧项目 {@code AutoEnchantBook.TargetMode}（{@code :465-480}），
 * 中文名即 {@code toString()} 的返回值，用于播报与界面显示，禁止改写。</p>
 */
public enum EnchantTargetMode {

    GEAR("原版装备附魔"),
    BOOK("原版附魔书"),
    CUSTOM("自定义附魔");

    private final String title;

    EnchantTargetMode(String title) {
        this.title = title;
    }

    /** 中文名（播报、分段控件、状态页用） */
    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
