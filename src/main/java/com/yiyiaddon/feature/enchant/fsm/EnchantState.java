package com.yiyiaddon.feature.enchant.fsm;

/**
 * 自动附魔状态机的 30 个状态（通用 12 + 原版装备附魔 18）。
 *
 * <p>逐字来自旧项目 {@code AutoEnchantBook.State}（{@code :366-390}）：枚举名、中文名、顺序
 * 与注释一字不改。中文名 {@link #cn()} 只用于状态播报
 * （{@code "§7正在" + cn() + "..."}，旧 {@code setState:2042-2044}）。</p>
 */
public enum EnchantState {

    IDLE("待机"),
    WALK_TO_FARM("前往刷经验点"), FARMING("刷经验"),
    WALK_TO_ENCHANT("前往附魔台"), ENCHANTING("附魔中"),
    CHECKING("检查附魔结果"),
    WALK_TO_GRIND("前往砂轮"), GRINDING("磨书"),
    WALK_TO_STORE("前往存书"), STORING("存书"),
    WALK_TO_RESTOCK("前往补给"), RESTOCKING("补给"),
    // 原版装备附魔（GEAR 模式，复用同一状态机，不另建 GearStateMachine）
    GEAR_IDLE("待机"),
    GEAR_WALK_EQUIPMENT("前往装备箱"), GEAR_TAKE_GEAR("取装备"),
    GEAR_WALK_ENCHANT("前往附魔台"), GEAR_ENCHANTING("附魔装备"),
    GEAR_WALK_LAPIS("前往青金石箱"), GEAR_RESTOCK_LAPIS("取青金石"),
    GEAR_EVALUATE("评估附魔"),
    GEAR_WALK_GRIND("前往砂轮"), GEAR_GRINDING("磨装备"),
    GEAR_WALK_ANVIL("前往铁砧"), GEAR_ANVIL("铁砧合并"),
    GEAR_WALK_ANVIL_BOX("前往铁砧箱"), GEAR_TAKE_ANVIL("取铁砧"),
    GEAR_WALK_ANVIL_POS("返回铁砧位"), GEAR_PLACE_ANVIL("放置铁砧"),
    GEAR_WALK_OUTPUT("前往成品箱"), GEAR_STORE_OUTPUT("存成品");

    private final String cn;

    EnchantState(String cn) {
        this.cn = cn;
    }

    /** 状态中文名（用于状态播报） */
    public String cn() {
        return cn;
    }
}
