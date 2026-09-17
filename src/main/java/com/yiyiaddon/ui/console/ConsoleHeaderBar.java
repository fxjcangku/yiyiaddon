package com.yiyiaddon.ui.console;

import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

/**
 * 控制台顶栏：一横排压缩件 —— <b>[状态圆点 + 状态文字] [快捷键徽章] [模块开关]</b>，整排右对齐。
 *
 * <p><b>用户 2026-09-17 口径（两次拍板合并）</b>：
 * 「按钮开关 能不能移动到控制台里面？所有包含控制台的模块」「右上角居中」「未启用这些 还有快捷键设置的
 * 都没有移动过去」「不可以那么长 压缩到控制台里面的跟启动按钮的旁边」。
 * 于是：<b>模块页顶栏整条撤掉</b>（原来的 {@code ModuleStatusBar} 已删除），
 * 状态文字 / 快捷键徽章 / 开关三件一起压进控制台，摆在控制台**内容第一行的右端**、三者紧挨、
 * 只占右侧一小段（不是原来那条横贯整屏的长卡）。</p>
 *
 * <p><b>文案与来源一个字未改</b>：状态文字仍是模块页原来那句
 * 「{@code module.isEnabled() ? "运行中" : "未启用"}」，颜色仍是启用绿 / 未启用红
 * （{@link ClickGuiThemeColors#stateOn} / {@link ClickGuiThemeColors#stateOff}，第 141 条）；
 * 快捷键徽章仍是 {@link KeybindBadge}（点一下录制 / 已绑定时点一下清空，第 214 条）；
 * 开关仍是 {@link ConsoleModuleSwitch}（44×24、走 {@code ModuleManager}）。</p>
 *
 * <p><b>右对齐排布</b>：从右边界内缩 {@link #PAD_X} 起往左排 —— 开关 → 快捷键徽章 → 状态文字
 * （圆点在文字左侧），件与件之间留 {@link #GAP}。绘制矩形与命中矩形用同一组 x 算式，不会错位。</p>
 */
public final class ConsoleHeaderBar implements CompactElement {

    /** 整条高度：与模块中心的行同高（{@code ModuleRow.HEIGHT}），进出模块页与控制台的行节奏一致 */
    public static final float HEIGHT = 24f;

    /** 右边界内缩：与控制台行内缩同口径（{@code ConsoleWidgets.PAD_X}） */
    private static final float PAD_X = 14f;

    private static final float DOT_SIZE = 7f;
    private static final float DOT_GAP = 8f;
    private static final float STATE_SIZE = 12f;

    /** 三件之间的间距（状态文字 / 徽章 / 开关） */
    private static final float GAP = 12f;

    private final Module module;
    private final KeybindBadge keybind;
    private final ConsoleModuleSwitch moduleSwitch;

    /** @param module 该控制台所属模块（状态文字、启用语义、快捷键 ID 全部从它取，与模块页原来同源） */
    public ConsoleHeaderBar(Module module) {
        this.module = module;
        this.keybind = new KeybindBadge(module.keybindId());
        this.moduleSwitch = new ConsoleModuleSwitch(module.id());
    }

    @Override
    public float height() {
        return HEIGHT;
    }

    @Override
    public void update(float dt) {
        keybind.update(dt);
        moduleSwitch.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float centerY = y + HEIGHT / 2f;

        // 从右往左：开关 → 快捷键徽章 → 状态文字（圆点在文字左侧）
        float switchLeft = switchX(x, width);
        moduleSwitch.drawAt(canvas, switchLeft, centerY, alpha);

        float cursor = switchLeft - GAP;
        if (keybind.active()) {
            float badgeX = cursor - keybind.width();
            keybind.draw(canvas, badgeX, centerY - KeybindBadge.HEIGHT / 2f, alpha, mouseX, mouseY);
            cursor = badgeX - GAP;
        }

        boolean running = module.isEnabled();
        int stateColor = running ? tc.stateOn : tc.stateOff;
        String stateText = running ? "运行中" : "未启用";
        float textWidth = MinecraftText.measure(stateText, STATE_SIZE, true);
        float textX = cursor - textWidth;
        MinecraftText.draw(canvas, stateText, textX, CardLayout.baseline(centerY, STATE_SIZE),
                STATE_SIZE, stateColor, alpha, true);
        GlassPanel.fill(canvas, textX - DOT_GAP - DOT_SIZE, centerY - DOT_SIZE / 2f, DOT_SIZE, DOT_SIZE,
                DOT_SIZE / 2f, stateColor, alpha);
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        float centerY = y + HEIGHT / 2f;
        if (moduleSwitch.onClickAt(mx, my, switchX(x, width), centerY, button)) return true;
        if (!keybind.active()) return false;
        float badgeX = switchX(x, width) - GAP - keybind.width();
        return keybind.onClick(mx, my, badgeX, centerY - KeybindBadge.HEIGHT / 2f);
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        return false;
    }

    /** 开关左边界；绘制与命中共用。 */
    private float switchX(float x, float width) {
        return x + width - PAD_X - moduleSwitch.width();
    }
}
