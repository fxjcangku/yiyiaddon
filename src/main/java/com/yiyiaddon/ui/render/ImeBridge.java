package com.yiyiaddon.ui.render;

import com.yiyiaddon.ui.component.PanelFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * 自绘输入框 ↔ 游戏的「正在文本输入」焦点桥。
 *
 * <p><b>为什么必须有一个真输入框：</b>输入法开关这件事不由界面决定，而由游戏自己的文本输入状态机
 * （{@code TextInputManager}）与输入法接管模组（IMBlocker 等）决定，而这两者的判定口径完全一致 ——
 * <b>只看原版 GUI 控件的焦点</b>：{@code AbstractWidget#setFocused} 之后 {@code EditBox#canConsumeInput()}
 * 为真，才算「有文本输入」。本项目所有输入框都是 Skija 自绘的，在这套体系里根本不存在，于是被恒判为
 * 「没有文本输入」：自绘输入框里按中英切换键不生效、输入法每 tick 被关掉，表现就是
 * 「搜索框打不进中文，聊天框可以」。</p>
 *
 * <p><b>为什么不能自己去开 IME：</b>直接推 {@code glfwSetInputMode(GLFW_IME, TRUE)} 是徒劳的 ——
 * 状态机每 tick 都会按自己的判定把结论改回来（项目 2026-09-16 的实测日志正是「推上去立刻掉回 0」）。
 * 任何绕过焦点判定的强推都会和它互相打架。</p>
 *
 * <p><b>做法：</b>放一个真正参与焦点体系的原版 {@link EditBox} 作为锚点。它铺在自绘输入框的位置上，
 * 不描边、文字与光标全透明（{@code textColor} 的 alpha 为 0），既不接收事件也不显示任何东西，
 * 只负责让状态机与输入法接管模组看到「有一个文本框正在输入」，并把光标矩形交给它们定位候选窗与
 * 预编辑串。自绘输入框获得焦点时调 {@link #focus}、失去焦点时调 {@link #blur}、每帧调 {@link #move}
 * 跟随滚动，其余一律不插手。</p>
 *
 * <p><b>锚点必须每帧真的被绘制：</b>输入法接管模组用「这一帧渲染过没有」判断文本框是否还在界面上
 * （离开绘制即视为不可见并交还焦点）。因此锚点只在 {@code renderables} 里、不在 {@code children} 里，
 * 由 {@link SkiaScreen#extractRenderState} 每帧带过一遍。</p>
 */
public final class ImeBridge {

    /** 与自绘输入框一一对应的隐形原版输入框；全项目共用同一个。 */
    private static EditBox sink;
    /** 锚点当前挂在哪个界面上；换界面要重新挂。 */
    private static Screen host;
    /** 当前输入法组合串（拼音等）；由 GLFW 回调线程写入，故为 volatile。 */
    private static volatile String preedit;

    private ImeBridge() {
    }

    /**
     * 记录当前预编辑串。两个来源都走这里：原版派发（无输入法接管模组时）与
     * {@code PreeditCaptureMixin}（接管模组 cancel 掉原版派发时）。
     */
    public static void setPreedit(String text) {
        preedit = text == null || text.isEmpty() ? null : text;
    }

    /** 清空预编辑串：提交、失去焦点、换输入框。 */
    public static void clearPreedit() {
        preedit = null;
    }

    /** 当前预编辑串；没有正在组合的内容时为 null。 */
    public static String preeditText() {
        return preedit;
    }

    /**
     * 自绘输入框获得焦点。
     *
     * @param screen  宿主界面；不是自绘界面时不挂锚点
     * @param designX 输入框左上角（设计空间横坐标）
     * @param designY 输入框左上角（设计空间纵坐标）
     * @param designW 输入框宽度（设计空间单位）
     * @param designH 输入框高度（设计空间单位）
     */
    public static void focus(Screen screen, float designX, float designY, float designW, float designH) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null || !(screen instanceof SkiaScreen)) {
            blur();
            return;
        }
        if (host != screen) {
            detach();
            host = screen;
        }
        if (sink == null) {
            sink = new EditBox(minecraft.font, 0, 0, 1, 1, Component.literal("yiyiaddon-ime"));
            sink.setBordered(false);
            // 文字与光标共用这一份颜色，置成全透明即整块不可见（空串时只剩光标，同样被透明掉）
            sink.setTextColor(0x00000000);
            sink.setValue("");
        }
        // 先摆位再聚焦：聚焦那一刻状态机就会按锚点几何算一次光标矩形，先聚焦会算出 (0,0)
        move(designX, designY, designW, designH);
        sink.setFocused(true);
    }

    /**
     * 每帧跟随自绘输入框（滚动、窗口变化、动画缩放都会移动它）。
     *
     * <p>顺带把锚点重新登记进绘制列表：界面重建控件列表时会把锚点一起清掉，而锚点一旦不在
     * 绘制列表里，输入法接管模组就会认为输入框已经不可见。</p>
     */
    public static void move(float designX, float designY, float designW, float designH) {
        if (sink == null) return;
        if (host instanceof SkiaScreen skia) {
            skia.attachImeSink(sink);
        }
        PanelFrame frame = PanelFrame.rendering();
        if (frame == null) return;
        int x = Math.round(frame.renderX(designX));
        int y = Math.round(frame.renderY(designY));
        int width = Math.max(1, Math.round(frame.toScreenLength(designW)));
        int height = Math.max(1, Math.round(frame.toScreenLength(designH)));
        if (sink.getX() == x && sink.getY() == y && sink.getWidth() == width && sink.getHeight() == height) {
            return;
        }
        sink.setX(x);
        sink.setY(y);
        sink.setWidth(width);
        sink.setHeight(height);
    }

    /** 自绘输入框失去焦点：交还焦点，输入法随之交回状态机判定。 */
    public static void blur() {
        detach();
    }

    /** 界面销毁时强制复位，避免锚点残留在已关闭的界面上。 */
    public static void reset() {
        detach();
    }

    private static void detach() {
        if (sink != null) {
            sink.setFocused(false);
            if (host instanceof SkiaScreen skia) {
                skia.detachImeSink(sink);
            }
        }
        host = null;
        preedit = null;
    }
}
