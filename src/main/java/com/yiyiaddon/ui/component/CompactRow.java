package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingWidget;
import io.github.humbleui.skija.Canvas;

import java.util.function.Supplier;

/**
 * 紧凑行：左侧标签 + 右侧控件，行高远小于旧的设置行。
 *
 * <p>默认不显示说明文字，说明只在鼠标悬停时以三级小字淡入，并按「标签结束到控件开始」之间的
 * 实际空隙截断，因此永远不会压住控件或标签。标签留空时控件左对齐，用于「一排分段按钮」这种
 * 没有标签的整行控件。</p>
 *
 * <p>控件矩形由私有方法统一给出，绘制、悬停、点击、拖动共用，行高变矮也不会错位。</p>
 */
public final class CompactRow implements CompactElement {

    /** 行高：明显低于旧设置行的 56。 */
    public static final float HEIGHT = 36f;

    private static final float PAD_X = 14f;
    private static final float LABEL_SIZE = 13f;
    private static final float HINT_SIZE = 10f;
    private static final float HINT_GAP = 12f;
    private static final float HINT_MIN_WIDTH = 28f;
    private static final float HOVER_SMOOTHING = 12f;

    private final String label;
    private final Supplier<String> hint;
    private final SettingWidget control;

    private boolean hovered;
    private float hover;

    /**
     * @param label   左侧标签，留空表示控件左对齐整行
     * @param hint    悬停提示文字来源，可为 null 表示没有提示
     * @param control 右侧控件
     */
    public CompactRow(String label, Supplier<String> hint, SettingWidget control) {
        this.label = label == null ? "" : label;
        this.hint = hint;
        this.control = control;
    }

    /** 无悬停提示的紧凑行。 */
    public CompactRow(String label, SettingWidget control) {
        this(label, null, control);
    }

    @Override
    public float height() {
        return HEIGHT;
    }

    @Override
    public void update(float dt) {
        if (control != null) control.update(dt);
        hover += ((hovered ? 1f : 0f) - hover) * Math.min(1f, Math.max(0f, dt) * HOVER_SMOOTHING);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float radius = GlassPanel.rowRadius(HEIGHT);
        float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
        GlassPanel.frost(canvas, x, y, width, HEIGHT, radius, tc.module, 0.70f, rowAlpha);
        GlassPanel.rim(canvas, x, y, width, HEIGHT, radius, tc.rim, alpha, 0.10f);

        hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HEIGHT;
        if (hover > 0.01f) {
            GlassPanel.fill(canvas, x, y, width, HEIGHT, radius, tc.surfaceHover, rowAlpha * hover);
        }

        float centerY = y + HEIGHT / 2f;
        if (!label.isEmpty()) {
            MinecraftText.draw(canvas, label, x + PAD_X, CardLayout.baseline(centerY, LABEL_SIZE),
                    LABEL_SIZE, tc.primaryText, alpha, true);
        }
        drawHint(canvas, x, y, width, alpha, tc);
        if (control != null) {
            float cx = controlX(x, width);
            float cy = controlY(y);
            control.hover(mouseX, mouseY, cx, cy, control.getWidth());
            control.draw(canvas, cx, cy, alpha);
        }
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        if (control == null || button != 0) return false;
        if (mx < x || mx > x + width || my < y || my > y + HEIGHT) return false;
        float cx = controlX(x, width);
        float cy = controlY(y);
        if (mx < cx || mx > cx + control.getWidth() || my < cy || my > cy + control.getHeight()) return false;
        return control.onClick(mx, my, cx, cy, button);
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        if (control instanceof SettingTextBox textBox) {
            return textBox.onDrag(mx, my, controlX(x, width), controlY(y));
        }
        return false;
    }

    /** 控件左边界；绘制与命中共用。 */
    private float controlX(float x, float width) {
        return label.isEmpty() ? x + PAD_X : x + width - PAD_X - control.getWidth();
    }

    /** 控件顶部；绘制与命中共用。 */
    private float controlY(float y) {
        return y + (HEIGHT - control.getHeight()) / 2f;
    }

    /** 悬停提示：只在指针悬停时淡入，并按实际空隙截断。 */
    private void drawHint(Canvas canvas, float x, float y, float width, float alpha, ClickGuiThemeColors tc) {
        if (hint == null || hover < 0.02f) return;
        String text = hint.get();
        if (text == null || text.isBlank()) return;

        float startX;
        float endX;
        if (label.isEmpty()) {
            // 无标签时控件左对齐，提示放在控件右侧的空隙里
            startX = control == null ? x + PAD_X : controlX(x, width) + control.getWidth() + HINT_GAP;
            endX = x + width - PAD_X;
        } else {
            startX = x + PAD_X + MinecraftText.measure(label, LABEL_SIZE, true) + HINT_GAP;
            endX = control == null ? x + width - PAD_X : controlX(x, width) - HINT_GAP;
        }
        float available = endX - startX;
        if (available < HINT_MIN_WIDTH) return;

        FontRenderer.drawText(canvas, CardLayout.ellipsize(text, available, HINT_SIZE), startX,
                CardLayout.baseline(y + HEIGHT / 2f, HINT_SIZE), HINT_SIZE,
                GlassPanel.withAlpha(tc.labelTertiary, alpha * hover));
    }
}
