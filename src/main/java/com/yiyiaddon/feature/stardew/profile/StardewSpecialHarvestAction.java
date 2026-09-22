package com.yiyiaddon.feature.stardew.profile;

/**
 * 特殊变种（金色 / 巨型 / 变种阶段）的收割动作。
 *
 * <p><b>为什么不和 {@link StardewHarvestAction} 合并：</b>普通成熟作物的动作全服统一
 * （空手右键），不需要也不允许按服务器分叉；而特殊阶段的动作<b>确实随服务器不同</b>——
 * 老服（金番茄等）实测是「手持原版金锄头右键，收完回退植株」，而 jmy.seasonmc.xyz 的巨型菠萝
 * 实测是「左键破坏，任何工具都行、拿锄头更快」（用户 2026-09-22 两次实机确认）。</p>
 *
 * <p>因此这里的取值必须按「服务器 + 资源指纹 + 作物」持久化，见 {@link StardewSpecialHarvestStore}；
 * 没学到时一律退回 {@link #RIGHT_CLICK}（既有行为），绝不因为一个服的口径去改别的服。</p>
 */
public enum StardewSpecialHarvestAction {

    /** 手持原版金锄头对作物格右键；收完回退植株（老服口径，也是未学到时的默认值） */
    RIGHT_CLICK("手持金锄头右键"),

    /** 左键破坏作物格；工具不限、拿锄头更快（本服巨型作物口径） */
    BREAK("左键破坏");

    private final String displayName;

    StardewSpecialHarvestAction(String displayName) {
        this.displayName = displayName;
    }

    /** 玩家看得懂的动作名，用于状态播报与日志 */
    public String displayName() {
        return displayName;
    }
}
