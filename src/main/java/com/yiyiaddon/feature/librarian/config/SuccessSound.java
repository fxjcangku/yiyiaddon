package com.yiyiaddon.feature.librarian.config;

/**
 * 自动图书管理员 · 附魔成功音效选项。
 *
 * <p>找到目标附魔时可选播放的音效集合；{@code toString()} 返回中文显示名，
 * 供设置界面的循环控件直接显示（枚举顺序即控件顺序，禁止调整）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/config/SuccessSound}（47 行）：22 个取值、
 * 中文名与顺序逐字照搬（旧项目按字数分行注释，此处保留同样的分组注释）。</p>
 */
public enum SuccessSound {
    // 2字
    BELL("钟声"),
    ATTACK_CRIT("暴击"),
    CAT("猫叫"),
    THUNDER("雷鸣"),
    // 3字
    EXPERIENCE_ORB("经验球"),
    // 4字
    CHALLENGE_COMPLETE("成就完成"),
    PLAYER_LEVELUP("玩家升级"),
    NOTE_PLING("音符盒叮"),
    CHEST_OPEN("宝箱打开"),
    FIREWORK_BLAST("烟花爆炸"),
    VILLAGER_CELEBRATE("村民欢庆"),
    ZOMBIE_VILLAGER_CURE("村民治愈"),
    GOAT_SCREAM("山羊嚎叫"),
    GHAST_SCREAM("恶魂嚎叫"),
    ALLAY_AMBIENT("悦灵鸣叫"),
    // 5字
    ENCHANTMENT_TABLE("附魔台使用"),
    TRIDENT_THUNDER("三叉戟雷鸣"),
    PANDA_SNEEZE("熊猫打喷嚏"),
    WARDEN_ROAR("监守者咆哮"),
    DRAGON_GROWL("末影龙龙吼"),
    END_PORTAL("末地传送门"),
    // 7字
    ELDER_GUARDIAN_CURSE("远古守卫者诅咒");

    private final String displayName;

    SuccessSound(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
