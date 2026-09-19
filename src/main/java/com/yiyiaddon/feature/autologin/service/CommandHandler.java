package com.yiyiaddon.feature.autologin.service;

import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import net.minecraft.client.Minecraft;

/**
 * 自动执行指令处理器（逐字照旧项目 {@code autologin/service/CommandHandler}）。
 * 登录完成后延迟执行玩家设置的自定义指令。
 */
public final class CommandHandler {

    private final Minecraft mc;
    private final AutoLoginSettings settings;
    private final Runnable onComplete;

    private int delayTicks;
    private boolean active;

    public CommandHandler(Minecraft mc, AutoLoginSettings settings, Runnable onComplete) {
        this.mc = mc;
        this.settings = settings;
        this.onComplete = onComplete;
    }

    public void reset() {
        delayTicks = 0;
        active = false;
    }

    /** 触发自动执行指令流程。 */
    public void start() {
        this.delayTicks = 0;
        this.active = true;
    }

    /** 每 tick 调用。倒计时完成后发送自定义指令。 */
    public void tick() {
        if (!active) return;

        delayTicks++;
        if (delayTicks >= settings.commandDelay) {
            String cmd = settings.autoCommandText.trim();
            if (!cmd.isEmpty()) {
                // 去掉开头的斜杠（如果有）
                if (cmd.startsWith("/")) {
                    cmd = cmd.substring(1);
                }
                if (mc.getConnection() != null) {
                    mc.getConnection().sendCommand(cmd);
                }
            }
            active = false;
            onComplete.run();
        }
    }

    public boolean isActive() {
        return active;
    }
}
