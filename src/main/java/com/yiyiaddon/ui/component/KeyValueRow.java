package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

import java.util.List;
import java.util.function.Supplier;

/**
 * 键值行：上方一个标签，下方一行数值，用于「最近结果」「数据统计」这类状态信息。
 *
 * <p>只有一个数值时整行左对齐显示；多个数值时按等宽列排开，每列是「名称 + 数值」，因此
 * 「物品 1 实体 0 方块 0」这类计数可以一行排完，不需要多行。</p>
 *
 * <p>取值异常时显示占位文案，不把异常抛进渲染循环；数值超宽按列宽截断。</p>
 */
public final class KeyValueRow implements CompactElement {

    /** 行高：标签行 + 数值行。 */
    public static final float HEIGHT = 48f;

    private static final float PAD_X = 16f;
    private static final float LABEL_SIZE = 11f;
    private static final float NAME_SIZE = 11f;
    private static final float VALUE_SIZE = 12f;
    private static final float NAME_GAP = 6f;
    private static final float COLUMN_GAP = 14f;
    private static final float LABEL_BASELINE = 18f;
    private static final float VALUE_BASELINE = 37f;
    private static final String UNAVAILABLE = "状态不可用";

    private final String label;
    private final List<Value> values;

    /**
     * @param label  行标签
     * @param values 一个或多个数值项
     */
    public KeyValueRow(String label, List<Value> values) {
        this.label = label == null ? "" : label;
        this.values = List.copyOf(values);
    }

    @Override
    public float height() {
        return HEIGHT;
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float radius = GlassPanel.rowRadius(HEIGHT);
        GlassPanel.frost(canvas, x, y, width, HEIGHT, radius, tc.module, 0.70f,
                ClickGuiThemeColors.panelBackgroundAlpha(alpha));
        GlassPanel.rim(canvas, x, y, width, HEIGHT, radius, tc.rim, alpha, 0.10f);

        if (!label.isEmpty()) {
            FontRenderer.drawText(canvas, label, x + PAD_X, CardLayout.baseline(y + LABEL_BASELINE, LABEL_SIZE),
                    LABEL_SIZE, GlassPanel.withAlpha(tc.labelTertiary, alpha));
        }
        if (values.isEmpty()) return;

        float valueY = CardLayout.baseline(y + VALUE_BASELINE, VALUE_SIZE);
        if (values.size() == 1) {
            drawSingle(canvas, values.get(0), x + PAD_X, valueY, width - PAD_X * 2f, alpha, tc);
            return;
        }
        float columnWidth = (width - PAD_X * 2f) / values.size();
        for (int i = 0; i < values.size(); i++) {
            Value value = values.get(i);
            float columnX = x + PAD_X + i * columnWidth;
            float nameWidth = FontRenderer.measureTextWidth(value.name, NAME_SIZE);
            FontRenderer.drawText(canvas, value.name, columnX, valueY, NAME_SIZE,
                    GlassPanel.withAlpha(tc.labelTertiary, alpha));
            float textX = columnX + nameWidth + NAME_GAP;
            float available = columnWidth - nameWidth - NAME_GAP - COLUMN_GAP;
            FontRenderer.drawTextBold(canvas, CardLayout.ellipsize(resolve(value.text), Math.max(0f, available), VALUE_SIZE),
                    textX, valueY, VALUE_SIZE, GlassPanel.withAlpha(tc.primaryText, alpha));
        }
    }

    private void drawSingle(Canvas canvas, Value value, float x, float baseline, float available,
                            float alpha, ClickGuiThemeColors tc) {
        String text = resolve(value.text);
        if (value.name.isEmpty()) {
            FontRenderer.drawTextBold(canvas, CardLayout.ellipsize(text, available, VALUE_SIZE), x, baseline,
                    VALUE_SIZE, GlassPanel.withAlpha(tc.primaryText, alpha));
            return;
        }
        float nameWidth = FontRenderer.measureTextWidth(value.name, NAME_SIZE);
        FontRenderer.drawText(canvas, value.name, x, baseline, NAME_SIZE,
                GlassPanel.withAlpha(tc.labelTertiary, alpha));
        float textX = x + nameWidth + NAME_GAP;
        FontRenderer.drawTextBold(canvas,
                CardLayout.ellipsize(text, Math.max(0f, available - nameWidth - NAME_GAP), VALUE_SIZE), textX,
                baseline, VALUE_SIZE, GlassPanel.withAlpha(tc.primaryText, alpha));
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        return false;
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        return false;
    }

    private static String resolve(Supplier<String> text) {
        try {
            String value = text.get();
            return value == null ? "" : value;
        } catch (Throwable ignored) {
            return UNAVAILABLE;
        }
    }

    /** 一个数值项：可带名称，名称留空时只显示数值。 */
    public static final class Value {

        private final String name;
        private final Supplier<String> text;

        private Value(String name, Supplier<String> text) {
            this.name = name == null ? "" : name;
            this.text = text;
        }

        /** 只有数值的项。 */
        public static Value of(Supplier<String> text) {
            return new Value("", text);
        }

        /** 「名称 + 数值」项，用于一行排开的多个计数。 */
        public static Value of(String name, Supplier<String> text) {
            return new Value(name, text);
        }
    }
}
