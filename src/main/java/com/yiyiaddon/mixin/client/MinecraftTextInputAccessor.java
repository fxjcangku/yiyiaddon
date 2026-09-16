package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.platform.TextInputManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * 直取原版文本输入状态机。
 *
 * <p>2026-09-16 实测定性：{@code Minecraft#onTextInputFocusChange(元素, true)} 在本项目运行环境里
 * 不会真的调到 {@code TextInputManager#startTextInput()}（探针在 {@code stopTextInput} 上能触发、
 * 在 {@code startTextInput} 上全程零触发）。结果是 {@code textInputEnabled} 恒为 false，而
 * {@code tickOutsideTextInput()} 一发现玩家把输入法切到中文（读回 1）就立刻
 * {@code setIMEInputMode(false)} 关掉它 —— 现象正是「自绘输入框里怎么切输入法都只能打英文」。</p>
 *
 * <p>本访问器只用于让自绘输入框能像原版 {@code EditBox} 一样把「正在文本输入」置为真，
 * 不改变任何原版行为。</p>
 */
@Mixin(Minecraft.class)
public interface MinecraftTextInputAccessor {

    /** 原版 {@code Minecraft#textInputManager} */
    @Accessor("textInputManager")
    TextInputManager yiyiaddon$textInputManager();
}
