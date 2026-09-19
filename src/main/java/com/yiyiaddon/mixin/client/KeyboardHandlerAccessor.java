package com.yiyiaddon.mixin.client;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * 键盘处理器访问器：暴露 {@code KeyboardHandler#keyPress(long, int, KeyEvent)}。
 *
 * <p><b>用途</b>：自动登入模块的自用回服路线需要模拟一次真实的 Shift＋F 按键事件
 * （主城快捷菜单的兜底手段），见 {@code feature/autologin/service/KeyboardSimulator}。
 * 26.1.2 的 {@code keyPress} 是私有方法，没有公开 API，因此用 {@code @Invoker} 提供访问接口。</p>
 *
 * <p><b>影响范围</b>：不修改游戏行为，只提供调用入口；不注入任何逻辑。</p>
 */
@Mixin(KeyboardHandler.class)
public interface KeyboardHandlerAccessor {

    /**
     * 注入一次键盘事件。
     *
     * @param window GLFW 窗口句柄（{@code mc.getWindow().handle()}）
     * @param action 按键动作：{@code GLFW_PRESS(1)} / {@code GLFW_RELEASE(0)}
     * @param event  按键事件（key / scancode / modifiers）
     */
    @Invoker("keyPress")
    void yiyiaddon$keyPress(long window, int action, KeyEvent event);
}
