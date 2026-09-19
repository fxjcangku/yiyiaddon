package com.yiyiaddon.feature.autologin.service;

import com.yiyiaddon.mixin.client.KeyboardHandlerAccessor;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;

/**
 * 快捷键模拟（自用回服路线的「Shift＋F 兜底」专用）。
 *
 * <p><b>来源</b>：旧项目 {@code autologin/AutoLoginModule.java} 里
 * {@code tickLeyuanCityMenuFallback} 与 {@code releaseLeyuanShortcutKeys} 两处按键代码
 * 逐字搬来，只是收口到一处（同一段逻辑原本在模块内重复写着 PRESET/RELEASE 两套）。</p>
 *
 * <p><b>为什么需要 mixin</b>：26.1.2 的 {@code KeyboardHandler#keyPress(long, int, KeyEvent)}
 * 是私有方法，本项目没有现成的访问器，因此新增
 * {@link KeyboardHandlerAccessor}（{@code @Invoker}，不修改游戏行为，只提供访问接口）。</p>
 *
 * <p><b>为什么先 {@code KeyMapping.set} 再注入事件</b>：{@code KeyMapping.set} 只改按键状态位，
 * 不会触发 {@code KeyMapping} 的按下/松开回调；而服务器快捷键通常挂在回调上，
 * 因此两者都要做（旧实现同样两步）。</p>
 */
public final class KeyboardSimulator {

    /** 左 Shift（旧 {@code GLFW.GLFW_KEY_LEFT_SHIFT}） */
    private static final InputConstants.Key SHIFT_KEY =
        InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_LEFT_SHIFT);
    /** F（旧 {@code GLFW.GLFW_KEY_F}） */
    private static final InputConstants.Key F_KEY =
        InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_F);

    private KeyboardSimulator() {
    }

    /** 按下左 Shift（旧 phase0 → phase1 的第一段） */
    public static void pressShift() {
        KeyMapping.set(SHIFT_KEY, true);
        inject(GLFW.GLFW_PRESS, GLFW.GLFW_KEY_LEFT_SHIFT, 0);
    }

    /** 按下 F，带 Shift 修饰（旧 phase1） */
    public static void pressFWithShift() {
        KeyMapping.set(F_KEY, true);
        inject(GLFW.GLFW_PRESS, GLFW.GLFW_KEY_F, GLFW.GLFW_MOD_SHIFT);
    }

    /** 松开 F，带 Shift 修饰（旧 phase2） */
    public static void releaseFWithShift() {
        KeyMapping.set(F_KEY, false);
        inject(GLFW.GLFW_RELEASE, GLFW.GLFW_KEY_F, GLFW.GLFW_MOD_SHIFT);
    }

    /** 松开左 Shift（旧 phase3） */
    public static void releaseShift() {
        KeyMapping.set(SHIFT_KEY, false);
        inject(GLFW.GLFW_RELEASE, GLFW.GLFW_KEY_LEFT_SHIFT, 0);
    }

    /** 松开 F，不带修饰（旧 {@code releaseLeyuanShortcutKeys} 的兜底两连发之一） */
    public static void releaseF() {
        KeyMapping.set(F_KEY, false);
        inject(GLFW.GLFW_RELEASE, GLFW.GLFW_KEY_F, 0);
    }

    /** 松开左 Shift，不带修饰（旧 {@code releaseLeyuanShortcutKeys} 的兜底两连发之二） */
    public static void releaseShiftBare() {
        KeyMapping.set(SHIFT_KEY, false);
        inject(GLFW.GLFW_RELEASE, GLFW.GLFW_KEY_LEFT_SHIFT, 0);
    }

    /**
     * 把一次按键事件注入 {@code KeyboardHandler}。
     *
     * <p>窗口句柄从当前 {@code Minecraft} 取；键盘处理器不是目标类型时静默跳过
     * （旧实现为强制转型，本项目按「不制造崩溃」的口径加一次类型判定）。</p>
     */
    private static void inject(int action, int key, int modifiers) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.getWindow() == null) return;
        if (!(mc.keyboardHandler instanceof KeyboardHandlerAccessor keyboard)) return;
        keyboard.yiyiaddon$keyPress(mc.getWindow().handle(), action, new KeyEvent(key, 0, modifiers));
    }
}
