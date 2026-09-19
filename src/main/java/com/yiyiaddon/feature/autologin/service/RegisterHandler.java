package com.yiyiaddon.feature.autologin.service;

import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import com.yiyiaddon.feature.autologin.model.AuthRule;
import net.minecraft.client.Minecraft;

/**
 * 注册处理器（逐字照旧项目 {@code autologin/service/RegisterHandler}）。
 * 收到注册提示后，延迟指定 tick 数再发送注册指令。
 */
public final class RegisterHandler {

    private final Minecraft mc;
    private final AutoLoginSettings settings;
    private final Runnable onComplete;

    private AuthRule registerRule;
    private int delayTicks;
    private boolean active;

    public RegisterHandler(Minecraft mc, AutoLoginSettings settings, Runnable onComplete) {
        this.mc = mc;
        this.settings = settings;
        this.onComplete = onComplete;
    }

    public void reset() {
        registerRule = null;
        delayTicks = 0;
        active = false;
    }

    /** 触发注册流程。 */
    public void start(AuthRule rule) {
        this.registerRule = rule;
        this.delayTicks = 0;
        this.active = true;
    }

    /** 每 tick 调用。倒计时完成后发送注册指令。 */
    public void tick() {
        if (!active || registerRule == null) return;

        delayTicks++;
        if (delayTicks >= settings.registerDelay) {
            String password = settings.registerPassword;
            if (!password.isEmpty()) {
                String cmd = registerRule.buildCommand(password);
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
