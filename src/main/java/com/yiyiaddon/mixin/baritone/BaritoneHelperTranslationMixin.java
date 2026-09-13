package com.yiyiaddon.mixin.baritone;

import baritone.api.utils.Helper;
import com.yiyiaddon.integration.baritone.BaritoneChatTranslations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Baritone 聊天消息翻译 Mixin
 * 
 * 拦截 Baritone 的所有聊天输出，包括：
 * 1. 消息前缀样式（改为紫色加粗）
 * 2. 聊天组件数组（翻译为中文）
 * 3. 通知消息（翻译为中文）
 */
@Mixin(value = Helper.class, remap = false)
public interface BaritoneHelperTranslationMixin {
    
    /**
     * 美化 Baritone 消息前缀
     * 原始：[Baritone] （白色）
     * 修改：[Baritone] （亮紫色+加粗）
     */
    @Inject(
        method = "getPrefix()Lnet/minecraft/network/chat/Component;",
        at = @At("RETURN"),
        cancellable = true
    )
    private static void yiyiaddon$colorBaritonePrefix(CallbackInfoReturnable<Component> cir) {
        cir.setReturnValue(Component.literal("[Baritone]").withStyle(style ->
            style.withColor(ChatFormatting.LIGHT_PURPLE).withBold(true)
        ));
    }

    /**
     * 翻译 Baritone 聊天消息组件数组
     * 拦截所有通过 logDirect 输出的消息
     */
    @ModifyVariable(
        method = "logDirect(Z[Lnet/minecraft/network/chat/Component;)V",
        at = @At("HEAD"),
        argsOnly = true
    )
    private Component[] yiyiaddon$translateChatComponents(Component[] components) {
        return BaritoneChatTranslations.translate(components);
    }

    /**
     * 翻译 Baritone 通知消息字符串
     * 拦截所有通过 logNotificationDirect 输出的消息
     */
    @ModifyVariable(
        method = "logNotificationDirect(Ljava/lang/String;Z)V",
        at = @At("HEAD"),
        argsOnly = true,
        ordinal = 0
    )
    private String yiyiaddon$translateNotification(String message) {
        return BaritoneChatTranslations.translate(message);
    }
}
