package com.yiyiaddon.feature.autofarm.controller;

import net.minecraft.client.Minecraft;

/**
 * 农场状态观察器：提供世界 / 玩家 / 背包等运行态观察结果。
 *
 * 只负责「看」，不负责「决定」。Controller 与 FarmDecision 依据这里的观察结果做判断。
 */
public final class FarmObserver {

    /** 世界与玩家是否就绪 */
    public boolean worldReady() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player != null && mc.level != null && mc.getConnection() != null;
    }

    /** 玩家是否存活（死亡则停止作业） */
    public boolean playerAlive() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player != null && !mc.player.isDeadOrDying();
    }

    /** 背包空槽数量（0~35 主背包区） */
    public int freeInventorySlots() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;

        int free = 0;
        for (int i = 0; i < 36; i++) {
            if (mc.player.getInventory().getItem(i).isEmpty()) free++;
        }
        return free;
    }
}
