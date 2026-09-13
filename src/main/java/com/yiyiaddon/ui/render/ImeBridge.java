package com.yiyiaddon.ui.render;

import net.minecraft.client.Minecraft;

/**
 * IME / 文本输入焦点桥。
 *
 * <p>26.1.2 提供了原生的文本输入管理：{@code Minecraft#onTextInputFocusChange} 会切换
 * 文本输入模式并在焦点变化时重投递 IME 预编辑事件。界面侧只需要在获得/失去文本框焦点时
 * 调用这里，不需要再自行禁用 Windows IMM。</p>
 *
 * <p>幂等：重复设置同一状态不会重复通知原版，避免无谓的状态抖动。</p>
 */
public final class ImeBridge {

    private static boolean active;

    private ImeBridge() {
    }

    public static void setTextInputActive(boolean value) {
        if (active == value) {
            return;
        }
        active = value;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft != null && minecraft.screen != null) {
            minecraft.onTextInputFocusChange(minecraft.screen, value);
        }
    }

    public static boolean isTextInputActive() {
        return active;
    }

    /** 界面销毁时强制复位，避免焦点残留。 */
    public static void reset() {
        setTextInputActive(false);
    }
}
