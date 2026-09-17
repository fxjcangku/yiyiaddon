package com.yiyiaddon.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;

/**
 * 模块开关（控制台顶栏右端的那一个）：点一下开模块、再点一下关，读写走统一的 {@link ModuleManager}。
 *
 * <p><b>为什么开关只在控制台里</b>（用户 2026-09-17 口径）：「按钮开关 能不能移动到控制台里面？
 * 所有包含控制台的模块」。原本模块页顶栏与控制台各摆一个同一个开关，属同一份设置两处承载
 * （第 209 条）。现在**模块页顶栏整条撤掉**（状态文字 / 快捷键徽章 / 开关一起搬进控制台，
 * 见 {@link ConsoleHeaderBar}），可点的开关全项目只剩控制台这一处。</p>
 *
 * <p><b>尺寸不新定</b>：宽高一律读 {@link SettingToggle#getWidth()} / {@link SettingToggle#getHeight()}
 * （44×24），与项目里其它开关同一尺寸 —— 用户 2026-09-17 明确「开关尺寸不变，只换位置」。</p>
 *
 * <p><b>定位由调用方给左边界</b>（{@link #drawAt} / {@link #onClickAt}）：本类不假设自己被摆在什么矩形里，
 * 排布交给 {@link ConsoleHeaderBar}（它按右对齐把 状态文字 / 快捷键徽章 / 开关 依次往左排）。</p>
 */
public final class ConsoleModuleSwitch {

    private final SettingToggle toggle;

    /** @param moduleId 该控制台所属模块的 ID（读写走统一的 {@link ModuleManager}） */
    public ConsoleModuleSwitch(String moduleId) {
        this.toggle = new SettingToggle(
            () -> ModuleManager.isEnabled(moduleId),
            value -> ModuleManager.setEnabled(moduleId, value));
    }

    /** 开关宽（= 统一开关宽） */
    public float width() {
        return toggle.getWidth();
    }

    /** 开关高（= 统一开关高） */
    public float height() {
        return toggle.getHeight();
    }

    /** 每帧推进滑块动画；不调用则开关只会停在初值不滑动 */
    public void update(float dt) {
        toggle.update(dt);
    }

    /**
     * 在指定左边界 + 垂直中心绘制。
     *
     * @param leftX   开关左边界
     * @param centerY 开关垂直中心
     */
    public void drawAt(Canvas canvas, float leftX, float centerY, float alpha) {
        toggle.draw(canvas, leftX, centerY - toggle.getHeight() / 2f, alpha);
    }

    /**
     * 点击命中：只认落在开关矩形内的左键。
     *
     * @return true 表示这一击已由开关消费，顶栏不再把它当成别的操作
     */
    public boolean onClickAt(float mx, float my, float leftX, float centerY, int button) {
        if (button != 0) return false;
        float top = centerY - toggle.getHeight() / 2f;
        if (mx < leftX || mx > leftX + toggle.getWidth() || my < top || my > top + toggle.getHeight()) {
            return false;
        }
        return toggle.onClick(mx, my, leftX, top, button);
    }
}
