package com.yiyiaddon.mixin.client;

import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.PreeditEvent;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Locale;

/**
 * TODO 临时排障：逐次记录原版入口收到的「字符 / 预编辑」事件，用来判定中文到底断在哪一层。
 *
 * <p>判定口径：原版聊天框能输入中文、本项目自研输入框不能，因此要让同一会话里两条路径都留下记录。
 * 非 ASCII 逐次记（上限 30 条），ASCII 只记首例（避免刷屏）。定位后整个文件连同
 * mixins.json 里的注册一起删。</p>
 */
@Mixin(KeyboardHandler.class)
public class KeyboardHandlerProbeMixin {

    @Unique
    private static final Logger YIYIADDON$LOGGER = LogUtils.getLogger();
    @Unique
    private static final int YIYIADDON$LOG_LIMIT = 30;

    @Unique
    private static boolean yiyiaddon$asciiSeen;
    @Unique
    private static int yiyiaddon$charCount;
    @Unique
    private static int yiyiaddon$preeditCount;

    @Inject(method = "charTyped", at = @At("HEAD"))
    private void yiyiaddon$probeChar(long handle, CharacterEvent event, CallbackInfo info) {
        int codepoint = event.codepoint();
        if (codepoint <= 0x7F) {
            if (!yiyiaddon$asciiSeen) {
                yiyiaddon$asciiSeen = true;
                YIYIADDON$LOGGER.info("[yiyiaddon] [临时] 原版收到字符（ASCII 首例）：U+{}，screen={}",
                        hex(codepoint), yiyiaddon$screenName());
            }
            return;
        }
        if (yiyiaddon$charCount++ >= YIYIADDON$LOG_LIMIT) return;
        YIYIADDON$LOGGER.info("[yiyiaddon] [临时] 原版收到字符（非 ASCII 第 {} 个）：U+{}，screen={}",
                yiyiaddon$charCount, hex(codepoint), yiyiaddon$screenName());
    }

    @Inject(method = "preeditCallback", at = @At("HEAD"))
    private void yiyiaddon$probePreedit(long handle, PreeditEvent event, CallbackInfo info) {
        if (yiyiaddon$preeditCount++ >= YIYIADDON$LOG_LIMIT) return;
        YIYIADDON$LOGGER.info("[yiyiaddon] [临时] 原版收到预编辑（第 {} 个）：{}，screen={}",
                yiyiaddon$preeditCount, event == null ? "null" : event.fullText(), yiyiaddon$screenName());
    }

    @Unique
    private static String hex(int codepoint) {
        return Integer.toHexString(codepoint).toUpperCase(Locale.ROOT);
    }

    @Unique
    private static String yiyiaddon$screenName() {
        Screen screen = Minecraft.getInstance().screen;
        return screen == null ? "null" : screen.getClass().getSimpleName();
    }
}
