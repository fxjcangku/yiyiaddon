package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

import java.util.function.Supplier;

/**
 * 只读状态文本控件：在设置行右侧显示动态运行状态、计数一类的值。
 *
 * <p>不响应点击（不会看起来像按钮），文本每帧从 {@link Supplier} 读取，超宽时截断。
 * 取值异常时显示占位文案，不把异常抛进渲染循环。</p>
 */
public class SettingText extends SettingWidget {

    private static final float DEFAULT_WIDTH = 200f;
    private static final float FONT_SIZE = 11f;
    private static final String UNAVAILABLE = "状态不可用";

    private final Supplier<String> text;
    private final float width;
    /** 动态列宽：非 null 时 {@link #getWidth()} 以它为准（表格列口径，见构造器注释） */
    private final Supplier<Float> dynamicWidth;
    /** 文字左对齐；默认右对齐，贴住自身宽度区域的右边界（设置行右侧的状态值口径）。 */
    private boolean alignLeft;

    public SettingText(Supplier<String> text) {
        this(text, DEFAULT_WIDTH, null);
    }

    public SettingText(Supplier<String> text, float width) {
        this(text, width, null);
    }

    /**
     * 列宽由外部每帧给出的「表格列」用法。
     *
     * <p>固定 200 宽的默认值在「一列状态文字」的场景里会留出大片空白，把同一行的按钮顶到行中间
     * 去（实机表现就是按钮看着没靠齐、位移了）。表格列应当按「同列最宽文本」定宽：
     * 文字左对齐后左右都贴住相邻控件，按钮也不再飘在行中间。</p>
     */
    public SettingText(Supplier<String> text, Supplier<Float> dynamicWidth) {
        this.text = text;
        this.width = DEFAULT_WIDTH;
        this.dynamicWidth = dynamicWidth;
    }

    private SettingText(Supplier<String> text, float width, Supplier<Float> dynamicWidth) {
        this.text = text;
        this.width = width;
        this.dynamicWidth = dynamicWidth;
    }

    /**
     * 改为左对齐。
     *
     * <p>整行状态行（如「已选 N / M 项」）用左对齐：默认右对齐会把文字推到自身宽度区域的右边界，
     * 于是整组内容看起来飘在行中间，与上下「左标签 ＋ 右控件」的行对不齐。旧项目该处是普通 label，
     * 即左对齐。</p>
     */
    public SettingText alignLeft() {
        this.alignLeft = true;
        return this;
    }

    @Override
    public float getWidth() {
        if (dynamicWidth == null) return width;
        Float value = dynamicWidth.get();
        return value == null ? width : Math.max(1f, value);
    }

    @Override
    public float getHeight() {
        return 20f;
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        String shown = resolve();
        if (shown.isEmpty()) return;
        float textWidth = MinecraftText.measure(shown, FONT_SIZE, false);
        if (textWidth > width) {
            // 超宽：退化为纯文本截断，保证不溢出控件区域（颜色码在截断时丢弃）
            shown = CardLayout.ellipsize(MinecraftText.strip(shown), width, FONT_SIZE);
            textWidth = FontRenderer.measureTextWidth(shown, FONT_SIZE);
        }
        MinecraftText.draw(canvas, shown, alignLeft ? x : x + width - textWidth, y + 14f, FONT_SIZE,
                ClickGuiThemeColors.current().secondaryText, alpha);
    }

    private String resolve() {
        try {
            String value = text.get();
            return value == null ? "" : value;
        } catch (Throwable ignored) {
            return UNAVAILABLE;
        }
    }
}
