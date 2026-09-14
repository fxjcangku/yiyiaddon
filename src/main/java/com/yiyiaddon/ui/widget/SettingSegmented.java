package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 分段控件：一排互斥按钮，例如 {@code [物品] [方块] [实体]}。
 *
 * <p>两种用法：</p>
 * <ul>
 *     <li><b>选择型</b>：给出选中下标来源，选中项用强调色实底 + 反色文字，视觉沿用主界面导航
 *         选中块；点击即把新下标写回。</li>
 *     <li><b>动作型</b>：不给出选中下标，各段等权，点击执行对应动作，被点的那一段按压缩放并
 *         混入强调色作为反馈。</li>
 * </ul>
 *
 * <p>各段宽度在构造时按文案量好，绘制与命中用同一套宽度推算，文案变化不会错位。</p>
 */
public class SettingSegmented extends SettingWidget {

    private static final float SEG_HEIGHT = 28f;
    private static final float SEG_GAP = 4f;
    private static final float SEG_RADIUS = 8f;
    private static final float MIN_SEG_WIDTH = 64f;
    private static final float TEXT_PADDING = 22f;
    private static final float TEXT_INSET = 8f;
    private static final float TEXT_SIZE = 12f;
    /** 动作型按压时强调色的最大混入比例。 */
    private static final float PRESS_TINT = 0.6f;

    private final List<String> labels;
    private final Supplier<Integer> selected;
    private final Consumer<Integer> onPick;
    private final float[] widths;
    private final PressState[] press;
    private final Paint paint = new Paint().setAntiAlias(true);
    private final float totalWidth;

    /**
     * 选择型分段控件。
     *
     * @param labels   分段文案，顺序即下标
     * @param selected 当前选中下标来源
     * @param onPick   选中变化时的写回
     */
    public SettingSegmented(List<String> labels, Supplier<Integer> selected, Consumer<Integer> onPick) {
        this.labels = List.copyOf(labels);
        this.selected = selected;
        this.onPick = onPick;
        this.widths = new float[this.labels.size()];
        this.press = new PressState[this.labels.size()];
        float total = 0f;
        for (int i = 0; i < this.labels.size(); i++) {
            this.widths[i] = Math.max(MIN_SEG_WIDTH,
                    FontRenderer.measureTextWidth(this.labels.get(i), TEXT_SIZE) + TEXT_PADDING);
            this.press[i] = new PressState();
            total += this.widths[i];
        }
        this.totalWidth = total + SEG_GAP * Math.max(0, this.labels.size() - 1);
    }

    /** 动作型分段控件：没有选中态，点击执行动作。 */
    public SettingSegmented(List<String> labels, Consumer<Integer> onPick) {
        this(labels, null, onPick);
    }

    @Override
    public float getWidth() {
        return totalWidth;
    }

    @Override
    public float getHeight() {
        return SEG_HEIGHT;
    }

    @Override
    public void update(float dt) {
        for (PressState state : press) state.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
        int selectedIndex = currentIndex();
        float cursor = x;
        for (int i = 0; i < widths.length; i++) {
            float width = widths[i];
            boolean isSelected = i == selectedIndex;
            int background = isSelected
                    ? tc.accent
                    : GlassPanel.mix(tc.buttonBackground, tc.accent, press[i].progress() * PRESS_TINT);
            int textColor = isSelected ? tc.accentOn : tc.subModuleText;
            boolean scaled = press[i].apply(canvas, cursor, y, width, SEG_HEIGHT);
            paint.setColor(GlassPanel.withAlpha(background, rowAlpha));
            canvas.drawRRect(RRect.makeXYWH(cursor, y, width, SEG_HEIGHT, SEG_RADIUS), paint);
            String shown = CardLayout.ellipsize(labels.get(i), width - TEXT_INSET * 2f, TEXT_SIZE);
            float textWidth = FontRenderer.measureTextWidth(shown, TEXT_SIZE);
            FontRenderer.drawText(canvas, shown, cursor + (width - textWidth) / 2f,
                    CardLayout.baseline(y + SEG_HEIGHT / 2f, TEXT_SIZE), TEXT_SIZE,
                    GlassPanel.withAlpha(textColor, alpha));
            if (scaled) canvas.restore();
            cursor += width + SEG_GAP;
        }
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, int button) {
        if (button != 0 || my < y || my > y + SEG_HEIGHT) return false;
        float cursor = x;
        for (int i = 0; i < widths.length; i++) {
            float width = widths[i];
            if (mx >= cursor && mx <= cursor + width) {
                press[i].pulse();
                onPick.accept(i);
                return true;
            }
            cursor += width + SEG_GAP;
        }
        return false;
    }

    /** 当前选中下标；无选中态或取值越界时返回 -1。 */
    private int currentIndex() {
        if (selected == null) return -1;
        Integer value = selected.get();
        if (value == null || value < 0 || value >= widths.length) return -1;
        return value;
    }
}
