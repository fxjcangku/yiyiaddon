package com.yiyiaddon.feature.enchant.model;

/**
 * 运行模式：每个目标模式一个独立开关，互不干扰。
 *
 * <p>逐字来自旧项目 {@code AutoEnchantBook.RunMode}（{@code :482-496}）。
 * {@link #DRAIN} 只消耗当前经验、不足则停机；{@link #EXPERIENCE} 自动前往挂机点刷经验。</p>
 */
public enum EnchantRunMode {

    DRAIN("纯附魔模式"),
    EXPERIENCE("挂机循环");

    private final String title;

    EnchantRunMode(String title) {
        this.title = title;
    }

    /** 中文名（播报与界面显示） */
    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
