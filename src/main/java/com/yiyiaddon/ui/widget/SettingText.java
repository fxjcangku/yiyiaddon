package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.render.FontRenderer;
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

    public SettingText(Supplier<String> text) {
        this(text, DEFAULT_WIDTH);
    }

    public SettingText(Supplier<String> text, float width) {
        this.text = text;
        this.width = width;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return 20f;
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        String shown = CardLayout.ellipsize(resolve(), width, FONT_SIZE);
        float textWidth = FontRenderer.measureTextWidth(shown, FONT_SIZE);
        FontRenderer.drawText(canvas, shown, x + width - textWidth, y + 14f, FONT_SIZE,
                withAlpha(ClickGuiThemeColors.current().secondaryText, alpha));
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
