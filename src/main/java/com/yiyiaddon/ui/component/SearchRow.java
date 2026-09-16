package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;

import java.util.function.Supplier;

/**
 * 整栏宽的搜索行：输入框<b>横向铺满</b>宿主分配给本行的宽度。
 *
 * <p><b>为什么不能套 {@link CompactRow}：</b>那个行控件把宽度交给控件自己定
 * （{@link SettingTextBox} 默认 150），于是搜索框只在行里占一小截、右侧空一大片；
 * 把控件设成 150 之外的固定值也不行 —— 面板宽度随窗口变，写死的值永远对不上。
 * 用户 2026-09-16 两次指出这件事：「图一看上面的搜索栏 还是没填满看见了吗」、
 * 「并不是所有点击搜索的输入框都是铺满了」。搜索框在本项目里是<b>页面元素</b>（与清单行同层、
 * 整栏宽），因此单独一个元素：按分配宽度给输入框设宽，其余行为（悬停 / 焦点 / 光标 /
 * 输入法 / 拖动选字）原样转发给控件。</p>
 *
 * <p><b>两种形态：</b>不带标签时输入框起点就是行起点，与下面的清单行左右严格对齐；
 * 带标签时标签占左侧一列（内缩与字号照 {@link CompactRow}），输入框铺满剩余宽度。
 * 两种形态共用同一次宽度计算，绘制与命中不会各算一遍。</p>
 */
public final class SearchRow implements CompactElement {

    /** 行高：与 {@link CompactRow} 同高，整页行节奏一致。 */
    public static final float HEIGHT = CompactRow.HEIGHT;

    private static final float LABEL_SIZE = 13f;
    /** 标签与输入框之间的留白 */
    private static final float LABEL_GAP = 12f;
    /** 输入框最小宽度：标签再长也不会把输入框压没 */
    private static final float MIN_BOX_WIDTH = 80f;

    private final String label;
    private final SettingTextBox box;

    /** 悬停提示来源：可空。搜索框铺满后行内没有空隙再摆说明文字，因此说明走浮层。 */
    private Supplier<String> tooltip;

    /** 无标签：输入框与清单行左右对齐，整栏宽。 */
    public SearchRow(SettingTextBox box) {
        this("", box);
    }

    /** 带标签：标签占左侧一列，输入框铺满剩余宽度。 */
    public SearchRow(String label, SettingTextBox box) {
        this.label = label == null ? "" : label;
        this.box = box;
    }

    /** 登记悬停浮层说明（多行、不截断）。 */
    public SearchRow tooltip(Supplier<String> text) {
        this.tooltip = text;
        return this;
    }

    /** 输入框顶边：行内垂直居中。 */
    private float boxY(float y) {
        return y + (HEIGHT - box.getHeight()) / 2f;
    }

    /**
     * 按当前分配宽度给输入框设宽，返回输入框左边界。
     *
     * <p>绘制、点击、拖动三处都走这里：宽度随面板宽度实时变化，任何一处漏算都会让
     * 「看到的」和「点到的」错开。</p>
     */
    private float layout(float x, float width) {
        float start = label.isEmpty() ? x : boxStart(x);
        box.width(Math.max(MIN_BOX_WIDTH, x + width - start));
        return start;
    }

    private float boxStart(float x) {
        return x + CompactRow.PAD_X + MinecraftText.measure(label, LABEL_SIZE, false) + LABEL_GAP;
    }

    @Override
    public float height() {
        return HEIGHT;
    }

    @Override
    public void update(float dt) {
        box.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha,
                     float mouseX, float mouseY) {
        float boxX = layout(x, width);
        if (!label.isEmpty()) {
            MinecraftText.draw(canvas, label, x + CompactRow.PAD_X,
                    CardLayout.baseline(y + HEIGHT / 2f, LABEL_SIZE), LABEL_SIZE,
                    ClickGuiThemeColors.current().primaryText, alpha, false);
        }
        boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HEIGHT;
        if (hovered && tooltip != null) {
            TooltipLayer.show(tooltip.get(), mouseX, mouseY);
        }
        box.hover(mouseX, mouseY, boxX, boxY(y), box.getWidth());
        box.draw(canvas, boxX, boxY(y), alpha);
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        if (my < y || my > y + HEIGHT) return false;
        return box.onClick(mx, my, layout(x, width), boxY(y), button);
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        return box.onDrag(mx, my, layout(x, width), boxY(y));
    }
}
