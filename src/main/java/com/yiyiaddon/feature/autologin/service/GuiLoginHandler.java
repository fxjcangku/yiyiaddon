package com.yiyiaddon.feature.autologin.service;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.glfw.GLFW;

/**
 * GUI 自动登录处理器（逐字照旧项目 {@code autologin/service/GuiLoginHandler}）。
 *
 * <p>设计目的：
 *   部分离线服务器不走聊天指令登录，而是在客户端弹出「密码输入界面」强制认证。
 *   {@link LoginHandler} 只处理 /login 命令，无法应对这种界面，本处理器负责自动填写并提交。</p>
 *
 * <p>界面形态（实测有两种，必须同时兼容）：
 *   1. 服务端对话屏：输入框被布局包进容器，按钮也由布局承载，<b>不是 Screen.children 的一级子节点</b>。
 *   2. 插件自定义 Screen：EditBox 与 Button 直接挂在 Screen.children 上。
 *   因此控件收集必须递归展开布局，只看一级子节点会完全识别不到对话屏的输入框。</p>
 *
 * <p>识别策略：
 *   1. 递归收集当前屏幕的全部 AbstractWidget
 *   2. 取第一个 EditBox 视为密码框（密码框在登录界面里是唯一输入框）
 *   3. 遍历 Button，按文本关键词匹配登录按钮
 *   4. 分阶段执行：先填密码、再等几 tick、最后模拟点击提交</p>
 *
 * <p>分阶段而非同 tick 完成，是为了让服务端看到的是一个「填写后停顿再提交」的操作序列，
 * 避免被登录插件的机器人检测判定为脚本。</p>
 *
 * <p>限制：
 *   - 仅支持单个密码输入框的界面
 *   - 按钮识别依赖文本关键词
 *   - 不支持验证码、二次确认等流程</p>
 */
public final class GuiLoginHandler {

    /** 第几个 tick 点击登录按钮（约 0.3 秒，足够让服务端看到输入变化） */
    private static final int PRESS_TICK = 6;

    // 登录按钮文本关键词（中英文混合，覆盖常见离线服插件）
    private static final String[] LOGIN_BUTTON_KEYWORDS = {
        "登录", "登陆", "确认", "确定", "进入",
        "login", "confirm", "enter", "ok"
    };

    /** 当前跟踪的屏幕，换屏即重置阶段状态 */
    private Screen trackedScreen;
    /** 当前屏幕已推进的 tick 数 */
    private int ticks;
    /** 当前屏幕是否已提交过（避免服务端未关屏时反复发包） */
    private boolean submitted;

    /**
     * 推进一次 GUI 登录处理，由模块每 tick 调用。
     *
     * <p>状态保存在本实例上，模块不要每 tick 新建处理器，
     * 否则 ticks 永远回到 1，只会填密码、永远点不到登录按钮。</p>
     *
     * @param screen   当前屏幕（模块传 mc.gui.screen()）
     * @param password 登录密码
     * @return true 表示本 tick 首次识别到登录界面（调用方据此提示一次）
     */
    public boolean tryHandle(Screen screen, String password) {
        if (screen == null || password == null || password.isEmpty()) {
            return false;
        }

        // 换屏重置：对话屏是每次新弹一个 Screen 实例，按实例判定最可靠
        if (screen != trackedScreen) {
            trackedScreen = screen;
            ticks = 0;
            submitted = false;
        }
        if (submitted) {
            return false;
        }

        List<AbstractWidget> widgets = collectWidgets(screen);
        EditBox passwordField = findPasswordField(widgets);
        if (passwordField == null) {
            return false;
        }
        Button loginButton = findLoginButton(widgets);
        if (loginButton == null) {
            return false;
        }

        // 对话屏的取值器是 () -> editBox.getValue() 的惰性 Supplier，
        // 每次点击时才读取，因此这里持续写入即可，无需触发 responder 回调。
        if (!password.equals(passwordField.getValue())) {
            passwordField.setValue(password);
        }

        ticks++;
        if (ticks < PRESS_TICK) {
            return ticks == 1;
        }

        submitted = true;
        // 直接调用 onPress 等价于玩家点按钮：对话屏的 OnPress 会取出当前输入值
        // 生成动作（自定义包或指令模板）并执行，不需要真实鼠标事件。
        loginButton.onPress(new KeyEvent(GLFW.GLFW_KEY_ENTER, 0, 0));
        return false;
    }

    /**
     * 递归收集屏幕内的全部控件。
     *
     * <p>直接子节点可能是布局（LinearLayout、HeaderAndFooterLayout、labeledElement 等），
     * 调用 LayoutElement.visitWidgets 会递归展开到最底层控件，这是对话屏输入框的唯一可达路径。</p>
     */
    private List<AbstractWidget> collectWidgets(Screen screen) {
        List<AbstractWidget> widgets = new ArrayList<>();
        for (GuiEventListener child : screen.children()) {
            if (child instanceof LayoutElement element) {
                element.visitWidgets(widgets::add);
            }
        }
        return widgets;
    }

    /** 取第一个可见输入框作为密码框 */
    private EditBox findPasswordField(List<AbstractWidget> widgets) {
        for (AbstractWidget widget : widgets) {
            if (widget instanceof EditBox editBox && editBox.visible) {
                return editBox;
            }
        }
        return null;
    }

    /** 按关键词匹配可见且可用的登录按钮 */
    private Button findLoginButton(List<AbstractWidget> widgets) {
        for (AbstractWidget widget : widgets) {
            if (!(widget instanceof Button button) || !button.visible || !button.active) {
                continue;
            }
            String text = button.getMessage().getString();
            if (text == null || text.isEmpty()) {
                continue;
            }
            String normalized = text.toLowerCase(Locale.ROOT).replaceAll("§[0-9a-fk-or]", "").trim();
            for (String keyword : LOGIN_BUTTON_KEYWORDS) {
                if (normalized.contains(keyword)) {
                    return button;
                }
            }
        }
        return null;
    }

    /**
     * 重置处理状态。
     *
     * <p>使用场景：模块关闭或停用时调用，避免状态残留导致下次进服被 submitted 挡住。</p>
     */
    public void reset() {
        trackedScreen = null;
        ticks = 0;
        submitted = false;
    }
}
