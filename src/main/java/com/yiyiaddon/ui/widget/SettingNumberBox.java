package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 数值输入框（左右带减号 / 加号微调）。
 *
 * <p>项目铁律：设置项一律使用本控件，禁止使用滑块。结构为 {@code [-] [ 数值框 ] [+]}：
 * 点两侧按钮按 {@code step} 步进，也可直接点击数值框键入数字。</p>
 *
 * <p>聚焦时显示原始输入文本并允许自由编辑（含 {@code -}、{@code 1.} 这类中间态）；
 * 失去焦点时统一归一化为 {@code format} 指定格式，并把取值夹到 {@code [min, max]}。
 * 解析用宽松前缀提取，因此带单位后缀的格式（如 {@code %.1fx}）也能正常回读。</p>
 */
public class SettingNumberBox extends SettingTextBox {

    /** 单个加减按钮宽度。 */
    private static final float BTN_W = 17f;
    /**
     * 数值框宽度。
     *
     * <p>从 82 收到 60：紧凑双列里控件的宽度直接吃掉标题的位置（一格只有 240），
     * 收到 60 后「禁止破坏方块列表」这种长标题也能完整显示；60 仍放得下 {@code -1234.5678}。</p>
     */
    private static final float BOX_W = 60f;
    /** 按钮与数值框的间距。 */
    private static final float GAP = 3f;
    /** 圆角半径。 */
    private static final float RADIUS = 7f;
    /** 输入串长度上限，足够容纳 {@code -1234.5678}。 */
    private static final int EDIT_MAX_LENGTH = 16;

    private final double min;
    private final double max;
    private final double step;
    private final String format;
    private final Supplier<Double> getter;
    private final Consumer<Double> setter;

    private final PressState minusPress = new PressState();
    private final PressState plusPress = new PressState();
    private final Paint buttonPaint = new Paint().setAntiAlias(true);

    /** 非 null 表示正在编辑，值即用户当前输入的原文。 */
    private String edit;
    private boolean wasFocused;
    /** 绘制内部数值框期间把宽度暴露给父类绘制逻辑。 */
    private float paintWidth = -1f;

    public SettingNumberBox(double min, double max, double step, String format,
                            Supplier<Double> getter, Consumer<Double> setter) {
        super(() -> "", value -> { }, EDIT_MAX_LENGTH);
        this.min = min;
        this.max = max;
        this.step = step;
        this.format = format;
        this.getter = getter;
        this.setter = setter;
    }

    private static float totalWidth() {
        return BTN_W * 2f + GAP * 2f + BOX_W;
    }

    @Override public float getWidth() {
        return paintWidth > 0f ? paintWidth : totalWidth();
    }

    @Override public float getHeight() { return 24f; }

    // ── 焦点与取值 ──

    /** 每帧核对焦点变化：获得焦点时进入编辑态，失去焦点时归一化并回写。 */
    private void syncFocusState() {
        boolean nowFocused = focused == this;
        if (nowFocused == wasFocused) return;
        wasFocused = nowFocused;
        if (nowFocused) {
            edit = formatValue(current());
            return;
        }
        if (edit != null) {
            Double parsed = parse(edit);
            if (parsed != null) setter.accept(clamp(parsed));
            edit = null;
        }
    }

    private double current() {
        Double value = getter.get();
        return value == null ? min : clamp(value);
    }

    private double clamp(double value) {
        return Math.max(min, Math.min(max, value));
    }

    private String formatValue(double value) {
        return String.format(Locale.ROOT, format, clamp(value));
    }

    private String displayText() {
        return edit != null ? edit : formatValue(current());
    }

    @Override protected String getValue() { return displayText(); }

    @Override protected String getDisplayText() { return displayText(); }

    @Override protected void setValue(String value) {
        edit = trimToMaxLength(value == null ? "" : value);
        Double parsed = parse(edit);
        if (parsed != null) setter.accept(clamp(parsed));
    }

    /** 宽松解析：取串首的合法十进制数字前缀，「1.2x」「1.2 x」均可回读。 */
    private static Double parse(String text) {
        if (text == null) return null;
        String source = text.strip();
        if (source.isEmpty()) return null;
        int end = 0;
        boolean dot = false;
        boolean digit = false;
        for (int i = 0; i < source.length(); i++) {
            char ch = source.charAt(i);
            if (i == 0 && (ch == '+' || ch == '-')) {
                end = 1;
                continue;
            }
            if (ch >= '0' && ch <= '9') {
                digit = true;
                end = i + 1;
                continue;
            }
            if (ch == '.' && !dot) {
                dot = true;
                end = i + 1;
                continue;
            }
            break;
        }
        if (!digit) return null;
        try {
            return Double.parseDouble(source.substring(0, end));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private void step(int direction) {
        double next = clamp(current() + direction * step);
        setter.accept(next);
        if (focused == this) edit = formatValue(next);
    }

    // ── 绘制与交互 ──

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        syncFocusState();
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        drawStepButton(canvas, x, y, alpha, "-", minusPress, tc);
        drawStepButton(canvas, x + totalWidth() - BTN_W, y, alpha, "+", plusPress, tc);

        paintWidth = BOX_W;
        try {
            canvas.save();
            canvas.translate(x + BTN_W + GAP, y);
            try {
                super.draw(canvas, 0f, 0f, alpha);
            } finally {
                canvas.restore();
            }
        } finally {
            paintWidth = -1f;
        }
    }

    private void drawStepButton(Canvas canvas, float x, float y, float alpha, String glyph,
                                PressState press, ClickGuiThemeColors tc) {
        buttonPaint.setColor(withAlpha(tc.buttonBackground, ClickGuiThemeColors.panelBackgroundAlpha(alpha)));
        boolean pressed = press.apply(canvas, x, y, BTN_W, getHeight());
        canvas.drawRRect(RRect.makeXYWH(x, y, BTN_W, getHeight(), RADIUS), buttonPaint);
        float glyphWidth = FontRenderer.measureTextWidthBold(glyph, 12f);
        FontRenderer.drawTextBold(canvas, glyph, x + (BTN_W - glyphWidth) * 0.5f, y + 16f, 12f,
                withAlpha(tc.subModuleText, alpha));
        if (pressed) canvas.restore();
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, int button) {
        if (button != 0) return false;
        if (my < y || my > y + getHeight()) return false;
        if (mx >= x && mx <= x + BTN_W) {
            minusPress.pulse();
            step(-1);
            return true;
        }
        float boxX = x + BTN_W + GAP;
        if (mx >= boxX && mx <= boxX + BOX_W) {
            return super.onClick(mx, my, boxX, y, button);
        }
        if (mx >= boxX + BOX_W + GAP && mx <= x + totalWidth()) {
            plusPress.pulse();
            step(1);
            return true;
        }
        return false;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        minusPress.update(dt);
        plusPress.update(dt);
    }

    @Override
    public boolean isAnimating() {
        return super.isAnimating() || !minusPress.isIdle() || !plusPress.isIdle();
    }
}
