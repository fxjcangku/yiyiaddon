package com.yiyiaddon.seed.model;

/**
 * 种子挖矿正式模块 · 矿物种类。
 *
 * <p><b>为什么用枚举而不是「钻石坐标」这类专用类型</b>：本轮虽然只做钻石，但模型的名字与结构
 * 以后要能直接扩到其它矿物，不能出现「DiamondPosition」这种一扩就废的命名
 * （正式化第一阶段口径第十节）。</p>
 *
 * <p><b>当前唯一实现</b>：{@link #DIAMOND}。其余矿物 <b>本轮禁止实现</b>，
 * 将来扩展时在 {@code OreType} 里追加，并由 {@code worldgen} 层的读取器与
 * {@code prediction} 层的预测器各自给出对应的方块/来源覆盖声明。</p>
 */
public enum OreType {

    /** 钻石：原版 {@code diamond_ore} / {@code deepslate_diamond_ore} 两种方块形态。 */
    DIAMOND("钻石");

    /** 中文显示名（玩家可见文本一律中文；内部类名保持英文）。 */
    private final String displayNameCn;

    OreType(String displayNameCn) {
        this.displayNameCn = displayNameCn;
    }

    /** 中文显示名。 */
    public String displayNameCn() {
        return displayNameCn;
    }
}
