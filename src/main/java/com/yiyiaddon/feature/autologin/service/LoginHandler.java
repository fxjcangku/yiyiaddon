package com.yiyiaddon.feature.autologin.service;

import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import com.yiyiaddon.feature.autologin.model.AuthRule;
import net.minecraft.client.Minecraft;

/**
 * 登录处理器（逐字照旧项目 {@code autologin/service/LoginHandler}）。
 * 收到登录提示后，延迟指定 tick 数再发送登录指令。
 */
public final class LoginHandler {

    private final Minecraft mc;
    private final AutoLoginSettings settings;
    private final Runnable onComplete;

    private AuthRule loginRule;
    private int delayTicks;
    private boolean active;

    public LoginHandler(Minecraft mc, AutoLoginSettings settings, Runnable onComplete) {
        this.mc = mc;
        this.settings = settings;
        this.onComplete = onComplete;
    }

    public void reset() {
        loginRule = null;
        delayTicks = 0;
        active = false;
    }

    /** 触发登录流程。 */
    public void start(AuthRule rule) {
        this.loginRule = rule;
        this.delayTicks = 0;
        this.active = true;
    }

    /** 每 tick 调用。倒计时完成后发送登录指令。 */
    public void tick() {
        if (!active || loginRule == null) return;

        delayTicks++;
        if (delayTicks >= settings.loginDelay) {
            String password = settings.loginPassword;
            if (!password.isEmpty()) {
                String cmd = loginRule.buildCommand(password);
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
