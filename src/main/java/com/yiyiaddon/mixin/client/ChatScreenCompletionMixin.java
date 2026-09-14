package com.yiyiaddon.mixin.client;

import com.yiyiaddon.command.CommandManager;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 聊天框补全：输入以客户端指令前缀开头时，Tab 交给自研指令系统补全。
 *
 * <p>只在文本以 {@code .} 开头时接管 Tab。原版补全只响应 {@code /} 开头的输入，因此正常聊天
 * 与服务器指令的 Tab 行为不受影响。</p>
 *
 * <p>无论是否有候选，接管后都返回已处理：避免文本已被改写而原版继续按旧光标位置处理 Tab。</p>
 */
@Mixin(ChatScreen.class)
public abstract class ChatScreenCompletionMixin {

    @Shadow
    protected EditBox input;

    @Inject(method = "keyPressed(Lnet/minecraft/client/input/KeyEvent;)Z", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$completeClientCommand(KeyEvent event, CallbackInfoReturnable<Boolean> info) {
        if (event.key() != GLFW.GLFW_KEY_TAB) return;
        if (input == null) return;

        String value = input.getValue();
        if (value == null || !CommandManager.isCommand(value)) return;

        String completed = CommandManager.applyTabCompletion(value);
        if (!completed.equals(value)) input.setValue(completed);
        info.setReturnValue(true);
    }
}
