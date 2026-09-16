package com.yiyiaddon.feature.enchant.model;

/**
 * 成功提示音类型。
 *
 * <p>逐字来自旧项目 {@code AutoEnchantBook.SuccessSound}（{@code :512-536}），
 * 12 项、顺序与中文名一字不改。默认 {@link #CHALLENGE_COMPLETE}。</p>
 */
public enum EnchantSuccessSound {

    CHALLENGE_COMPLETE("挑战完成"),
    LEVEL_UP("升级"),
    ENCHANTMENT_TABLE("附魔台"),
    NOTE_PLING("音符叮"),
    BELL("钟声"),
    FIREWORK("烟花"),
    EXPERIENCE("经验球"),
    VILLAGER("村民庆祝"),
    TRIDENT_THUNDER("三叉戟雷鸣"),
    ATTACK_CRIT("暴击"),
    CAT("猫叫"),
    THUNDER("雷声");

    private final String title;

    EnchantSuccessSound(String title) {
        this.title = title;
    }

    /** 中文名（分段控件显示用） */
    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
