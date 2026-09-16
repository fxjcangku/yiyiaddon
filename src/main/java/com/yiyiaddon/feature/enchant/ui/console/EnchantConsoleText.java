package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.repository.EnchantPointStore;
import com.yiyiaddon.platform.world.WorldContextFormatter;

/**
 * 控制台各页共用的只读点位文案：概览页与点位页读同一处，避免两页各写一份后走形。
 *
 * <p>文字逐字来自旧项目 {@code AutoEnchantBook.buildPointCard:684-689}：坐标串
 * {@code §7X§f<x> §7Y§f<y> §7Z§f<z>}，维度行 {@code §7维度 §f<维度中文名>}（旧 {@code 维度中文名()}，
 * 取自 {@link WorldContextFormatter#dimensionSummary(String)}；整套点位共用一份归属世界记录）。
 * 卡片是两行，控制台行只有一行，两段之间以两个空格分隔，文字一字未改。</p>
 */
final class EnchantConsoleText {

    private EnchantConsoleText() {
    }

    /** 已绑定点位明细：坐标串 + 维度行 */
    static String pointDetail(EnchantPoint point, EnchantPointStore store) {
        String dimension = WorldContextFormatter.dimensionSummary(store.pointDimension());
        return "§7X§f" + point.x() + " §7Y§f" + point.y() + " §7Z§f" + point.z()
            + "  §7维度 §f" + dimension;
    }
}
