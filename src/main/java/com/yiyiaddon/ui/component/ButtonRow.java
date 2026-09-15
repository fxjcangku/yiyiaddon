package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingWidget;
import io.github.humbleui.skija.Canvas;

import java.util.Arrays;
import java.util.List;

/**
 * 横向按钮行：把多个控件排布成一行。
 *
 * <p>用于旧项目最常见的三种排布：</p>
 * <ul>
 *     <li><b>满宽单按钮</b>：只放一个 {@link Button}，即撑满整行（对应旧项目
 *         {@code addUniformButton} 的一行一个按钮）。</li>
 *     <li><b>等宽多按钮</b>：放多个 {@link Button}，剩余宽度被平分（对应旧项目的筛选按钮行、
 *         确认框的「确认 / 取消」、点位卡的「设置 / 删除」）。</li>
 *     <li><b>两端对齐</b>：{@link #split}，左端贴一个控件、右端贴一个控件，中间留白
 *         （对应「计数 ＋ 重置」这类状态行）。</li>
 * </ul>
 *
 * <p>非 {@link Button} 的控件（如方形图标按钮）保持自身宽度、不参与平分，只按行高垂直居中，
 * 因此可与按钮混排。</p>
 *
 * <p><b>约定</b>：控件必须在加入本行之前完成尺寸配置（例如先 {@code button.large()}
 * 再传入构造），行高按每帧实际读取的最大高度计算，事后改尺寸不会错位但会导致本行高度变化。</p>
 */
public class ButtonRow implements CompactElement {

    /** 默认按钮间距。 */
    public static final float DEFAULT_GAP = 8f;

    /** 可缩放按钮的最小宽度，防止一行内按钮过多时被压成一条。 */
    private static final float MIN_BUTTON_WIDTH = 24f;

    private final List<SettingWidget> widgets;
    private final float gap;
    /** 两端对齐：首控件贴行首、末控件贴行尾，其余紧跟在首控件之后。 */
    private final boolean split;

    public ButtonRow(SettingWidget... widgets) {
        this(DEFAULT_GAP, false, widgets);
    }

    public ButtonRow(float gap, SettingWidget... widgets) {
        this(gap, false, widgets);
    }

    private ButtonRow(float gap, boolean split, SettingWidget... widgets) {
        this.gap = gap > 0f ? gap : DEFAULT_GAP;
        this.split = split;
        this.widgets = Arrays.asList(widgets);
    }

    /**
     * 两端对齐排布：左侧控件贴行首、右侧控件贴行尾，中间留白。
     *
     * <p>用于「计数 ＋ 重置」这类状态行。默认的等宽排布会把非按钮控件全部挤在行首，而计数文字
     * 又各自右对齐到自身宽度，于是整组看起来飘在行中间，与上下「左标签 ＋ 右控件」的行对不齐。</p>
     */
    public static ButtonRow split(SettingWidget left, SettingWidget right) {
        return new ButtonRow(DEFAULT_GAP, true, left, right);
    }

    @Override
    public float height() {
        float max = 0f;
        for (SettingWidget widget : widgets) {
            max = Math.max(max, widget.getHeight());
        }
        return max;
    }

    @Override
    public void update(float dt) {
        for (SettingWidget widget : widgets) widget.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        if (widgets.isEmpty()) return;
        float[] widths = layout(width);
        float[] xs = positions(x, width, widths);
        float rowHeight = height();
        for (int i = 0; i < widgets.size(); i++) {
            SettingWidget widget = widgets.get(i);
            float widgetHeight = widget.getHeight();
            float widgetY = y + (rowHeight - widgetHeight) * 0.5f;
            widget.hover(mouseX, mouseY, xs[i], widgetY, widths[i]);
            if (widget instanceof Button button) {
                button.drawAt(canvas, xs[i], widgetY, widths[i], alpha);
            } else {
                widget.draw(canvas, xs[i], widgetY, alpha);
            }
        }
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        return false;
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        if (widgets.isEmpty() || button != 0) return false;
        float[] widths = layout(width);
        float[] xs = positions(x, width, widths);
        float rowHeight = height();
        for (int i = 0; i < widgets.size(); i++) {
            SettingWidget widget = widgets.get(i);
            float widgetHeight = widget.getHeight();
            float widgetY = y + (rowHeight - widgetHeight) * 0.5f;
            if (widget instanceof Button target) {
                if (target.onClickAt(mx, my, xs[i], widgetY, widths[i], button)) return true;
            } else if (widget.onClick(mx, my, xs[i], widgetY, button)) {
                return true;
            }
        }
        return false;
    }

    /** 按行宽分配每个控件的宽度：按钮平分剩余宽度，其余控件保持自身宽度。 */
    private float[] layout(float width) {
        int count = widgets.size();
        float[] widths = new float[count];
        if (split) {
            // 两端对齐下所有控件都按自身宽度排布，不参与平分
            for (int i = 0; i < count; i++) widths[i] = widgets.get(i).getWidth();
            return widths;
        }
        float fixedTotal = 0f;
        int flexibleCount = 0;
        for (SettingWidget widget : widgets) {
            if (widget instanceof Button) {
                flexibleCount++;
            } else {
                fixedTotal += widget.getWidth();
            }
        }
        float available = width - fixedTotal - gap * Math.max(0, count - 1);
        float flexibleWidth = flexibleCount == 0
                ? 0f
                : Math.max(MIN_BUTTON_WIDTH, available / flexibleCount);
        for (int i = 0; i < count; i++) {
            SettingWidget widget = widgets.get(i);
            widths[i] = widget instanceof Button ? flexibleWidth : widget.getWidth();
        }
        return widths;
    }

    /** 每个控件的左边界；绘制与命中共用同一份结果，因此点得到的一定是画出来的那个位置。 */
    private float[] positions(float x, float width, float[] widths) {
        int count = widths.length;
        float[] xs = new float[count];
        if (count == 0) return xs;
        if (!split) {
            float cursor = x;
            for (int i = 0; i < count; i++) {
                xs[i] = cursor;
                cursor += widths[i] + gap;
            }
            return xs;
        }
        xs[0] = x;
        float cursor = x + widths[0] + gap;
        for (int i = 1; i < count; i++) {
            xs[i] = i == count - 1 ? x + width - widths[i] : cursor;
            cursor += widths[i] + gap;
        }
        return xs;
    }
}
