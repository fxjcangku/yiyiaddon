package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.keybind.AddonKeybind;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 键位设置控件：显示当前绑定，点一下进入录制，按键即绑定。
 *
 * <p><b>来源与自研说明</b>：旧项目用框架的 {@code KeybindSetting} + {@code WKeybind}
 * （原始源码 {@code 26.1.2/01-开发参考库/Meteor原始源码/.../gui/widgets/WKeybind.java}）。
 * 本项目不依赖旧框架，用户 2026-09-17 明确要求自研（「Meteor 的源代码也有参考价值」），
 * 本控件照 {@code WKeybind} 的交互与取值模型实现，**语义逐条对齐**：</p>
 * <ul>
 *   <li>初始/结束后显示绑定名（{@link AddonKeybind#displayName()}，未绑定显示 {@code None}）；</li>
 *   <li>点击进入录制，录制期间显示 {@code ...}（旧 {@code WKeybind#init} 的 {@code button.set("...")}）；</li>
 *   <li>录制中收到输入，经 {@link AddonKeybind#canBindTo} 通过即写入并结束录制
 *       （旧 {@code WKeybind#onAction}）；不通过（{@code ESC} / 修饰键本身 / 鼠标左右键）则结束录制、不落绑定
 *       —— {@code ESC} 在本项目控制台还是「退出界面」，必须吃掉这一键，不能漏给屏幕；</li>
 *   <li>右键清空绑定（旧 {@code WKeybind#onClear} → {@code keybind.reset()}；本项目右键是「清空」的既有语义）。</li>
 * </ul>
 *
 * <p><b>静态转发</b>：录制态是「当前唯一的」，与 {@link SettingTextBox} 的焦点同一手法——
 * 控件把自己记进静态槽，界面在自己被通知按键/点击时先问这里
 * （{@link #keyPressed(KeyEvent)} / {@link #mousePressed(int)}，两者都放在输入框之前：录制态优先）。
 * 整页重建前必须调 {@link #clearCapture()}，否则静态槽会指向已被丢弃的实例。</p>
 *
 * <p><b>落盘</b>：控件不碰文件，写入走构造时给的 {@code setter}（调用方在其中 {@code persistSettings()}），
 * 与同页其它设置行同一口径（第 172 条：界面改动立即落盘）。</p>
 */
public class SettingKeybind extends SettingWidget {

    /** 录制中显示文本（逐字照旧 {@code WKeybind}） */
    private static final String LISTENING_LABEL = "...";

    /** 正在录制的控件；同一时刻只有一个（与 {@link SettingTextBox#focused} 同理） */
    private static SettingKeybind capturing;

    private final Supplier<AddonKeybind> getter;
    private final Consumer<AddonKeybind> setter;
    private final PressState press = new PressState();
    private final Paint bgPaint = new Paint().setAntiAlias(true);

    /** 控件宽度（默认 160：放得下 {@code Mouse Forward}、{@code Num Lock + F12} 这类长名） */
    private float width = 160f;

    /** 上一帧绘制用的绑定值与量好的文本宽度（绑定是不可变对象，按值缓存即可） */
    private AddonKeybind cachedBind;
    private boolean cachedListening;
    private String cachedLabel = "";
    private float cachedTextWidth;

    public SettingKeybind(Supplier<AddonKeybind> getter, Consumer<AddonKeybind> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    /** 链式设置控件宽度（与 {@link SettingTextBox#width(float)} 同形） */
    public SettingKeybind width(float width) {
        this.width = Math.max(1f, width);
        return this;
    }

    @Override public float getWidth() { return width; }
    @Override public float getHeight() { return 24f; }

    @Override
    public void update(float dt) {
        press.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        boolean listening = capturing == this;
        AddonKeybind bind = getter.get();
        if (bind == null) bind = AddonKeybind.none();

        // 录制态与绑定值都可能变，两者任一变化才重量文本宽度（逐帧测字会白烧 CPU）
        if (listening != cachedListening || (!listening && !bind.equals(cachedBind))) {
            cachedListening = listening;
            cachedBind = listening ? null : bind;
            cachedLabel = listening ? LISTENING_LABEL : bind.displayName();
            cachedTextWidth = FontRenderer.measureTextWidth(cachedLabel, 12f);
        }

        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        // 与数值框 / 循环块同一族（输入域色），读起来是「这里有个值」，不是按钮底色压暗的小黑块
        bgPaint.setColor(withAlpha(tc.field, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
        boolean pressed = press.apply(canvas, x, y, getWidth(), getHeight());
        canvas.drawRRect(RRect.makeXYWH(x, y, getWidth(), getHeight(), 6f), bgPaint);
        FontRenderer.drawText(canvas, cachedLabel, x + (getWidth() - cachedTextWidth) / 2f, y + 16f, 12f,
            withAlpha(listening ? tc.accent : tc.subModuleText, alpha));
        if (pressed) canvas.restore();
    }

    @Override
    public boolean isAnimating() {
        return !press.isIdle();
    }

    /** 左键进入录制；右键清空绑定（录制中则取消录制） */
    @Override
    public boolean onClick(float mx, float my, float x, float y, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            press.pulse();
            capturing = this;
            return true;
        }
        if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            press.pulse();
            if (capturing == this) {
                capturing = null;
            } else {
                setter.accept(AddonKeybind.none());
            }
            return true;
        }
        return false;
    }

    // ── 静态转发（界面按键/点击时先问这里） ──

    /**
     * 转发一次按键。
     *
     * @return 是否被录制态消费（{@code true} 时界面必须直接返回，不再往下传）
     */
    public static boolean keyPressed(KeyEvent event) {
        SettingKeybind target = capturing;
        if (target == null || event == null) return false;
        int key = event.key();
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            capturing = null;
            return true;
        }
        if (AddonKeybind.canBindTo(true, key, event.modifiers())) {
            target.setter.accept(AddonKeybind.ofKey(key, event.modifiers()));
        }
        capturing = null;
        return true;
    }

    /** 转发一次鼠标点击（录制中按键绑定，左右键不通过判据时仅取消录制） */
    public static boolean mousePressed(int button) {
        SettingKeybind target = capturing;
        if (target == null) return false;
        if (AddonKeybind.canBindTo(false, button, 0)) {
            target.setter.accept(AddonKeybind.ofButton(button));
        }
        capturing = null;
        return true;
    }

    /** 是否正在录制（供界面判断是否需要吃掉输入） */
    public static boolean isCapturing() {
        return capturing != null;
    }

    /** 结束录制（页面重建 / 关闭界面时必须调用，避免静态槽指向已丢弃的实例） */
    public static void clearCapture() {
        capturing = null;
    }
}
