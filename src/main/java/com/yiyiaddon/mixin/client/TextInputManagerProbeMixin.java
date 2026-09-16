package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.platform.TextInputManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.logging.LogUtils;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * TODO 临时排障：盯住输入法状态机（原版唯一真正操作 GLFW IME 的地方）。
 *
 * <p>原版 {@code TextInputManager} 只做三件事：{@code startTextInput()} 里按 {@code imeRequested}
 * 决定是否开输入法、{@code tickOutsideTextInput()} 里按 {@code getIMEStatus()} 决定是否关掉、
 * 以及 {@code setIMEInputMode} 这一处真正落到 GLFW。把这三处加每 tick 的读回采样记下来，就能分清
 * 「我们的输入框没让它开」还是「GLFW 层开不了」。定位后整个文件连同 mixins.json 的注册一起删。</p>
 */
@Mixin(TextInputManager.class)
public class TextInputManagerProbeMixin {

    /** 原版硬编码的 GLFW IME 输入模式（{@code TextInputManager:39}） */
    @Unique
    private static final int YIYIADDON$IME_MODE = 208903;

    @Unique
    private static final Logger YIYIADDON$LOGGER = LogUtils.getLogger();

    @Unique
    private static int yiyiaddon$lastMode = -1;

    @Shadow
    @Final
    private Window window;

    @Shadow
    private boolean textInputEnabled;

    @Shadow
    private boolean imeRequested;

    @Shadow
    private boolean cachedIMEStatus;

    /** 每 tick 采一次读回值，只在变化时落日志（能看清玩家切输入法、聚焦不同输入框的差别） */
    @Inject(method = "tick", at = @At("HEAD"))
    private void yiyiaddon$sampleIme(CallbackInfo info) {
        int mode = GLFW.glfwGetInputMode(this.window.handle(), YIYIADDON$IME_MODE);
        if (mode == yiyiaddon$lastMode) return;
        yiyiaddon$lastMode = mode;
        YIYIADDON$LOGGER.info(
                "[yiyiaddon] [临时] GLFW IME 读回变化：{}（文本输入中={}，imeRequested={}，缓存状态={}，窗口聚焦={}）",
                mode, this.textInputEnabled, this.imeRequested, this.cachedIMEStatus, this.window.isFocused());
    }

    @Inject(method = "startTextInput", at = @At("HEAD"))
    private void yiyiaddon$onStart(CallbackInfo info) {
        YIYIADDON$LOGGER.info("[yiyiaddon] [临时] startTextInput：imeRequested={}", this.imeRequested);
    }

    @Inject(method = "stopTextInput", at = @At("HEAD"))
    private void yiyiaddon$onStop(CallbackInfo info) {
        YIYIADDON$LOGGER.info("[yiyiaddon] [临时] stopTextInput");
    }

    @Inject(method = "setIMEInputMode", at = @At("HEAD"))
    private void yiyiaddon$onSetIme(boolean value, CallbackInfo info) {
        YIYIADDON$LOGGER.info("[yiyiaddon] [临时] 原版 setIMEInputMode({})", value);
    }
}
