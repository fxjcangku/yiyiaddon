package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.anim.Spring;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.Rect;

import java.util.function.Supplier;

/**
 * 折叠区：标题行可点击展开 / 收起，内容用弹簧做高度过渡。
 *
 * <p>标题行右侧的箭头随展开进度旋转 90 度（收起朝右、展开朝下），收起时可在箭头左侧显示一句
 * 次要摘要，展开过程中淡出，避免与内容争视觉。</p>
 *
 * <p>内容只在动画高度内绘制与命中：绘制用 {@code clipRect} 裁到当前高度，命中也把下边界限制
 * 在同一高度，因此半展开状态不会出现「看不见却能点到」。</p>
 */
public final class CollapsibleSection implements CompactElement {

    /** 标题行高度。 */
    public static final float HEADER_HEIGHT = 36f;

    private static final float CONTENT_GAP = 6f;
    private static final float PAD_X = 14f;
    private static final float TITLE_SIZE = 13f;
    private static final float SUMMARY_SIZE = 11f;
    private static final float SUMMARY_GAP = 10f;
    private static final float HOVER_SMOOTHING = 12f;

    /** 收起时朝右的箭头，展开时顺时针转 90 度朝下。 */
    private static final String ARROW = "\uE5CC";
    private static final float ARROW_SIZE = 14f;
    private static final float ARROW_SPIN = 90f;
    /** 字形视觉中心相对基线的偏移比例，用于把旋转中心对准箭头自身中心。 */
    private static final float ARROW_CENTER_RATIO = 0.36f;

    private final String title;
    private final Supplier<String> summary;
    private final CompactStack content = new CompactStack(CONTENT_GAP);
    private final Spring expand = Spring.critical(0.24f);
    private final PressState press = new PressState();

    private boolean expanded;
    private boolean hovered;
    private float hover;

    /**
     * @param title   标题行文字
     * @param summary 收起时显示的次要摘要来源，可为 null
     */
    public CollapsibleSection(String title, Supplier<String> summary) {
        this.title = title == null ? "" : title;
        this.summary = summary;
    }

    /** 折叠区内容；由页面把低频设置行加进来。 */
    public CompactStack content() {
        return content;
    }

    @Override
    public float height() {
        float contentHeight = content.isEmpty() ? 0f : CONTENT_GAP + expand.value() * content.height();
        return HEADER_HEIGHT + contentHeight;
    }

    @Override
    public void update(float dt) {
        hover += ((hovered ? 1f : 0f) - hover) * Math.min(1f, Math.max(0f, dt) * HOVER_SMOOTHING);
        press.update(dt);
        expand.setTarget(expanded ? 1f : 0f);
        expand.update(dt);
        content.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float radius = GlassPanel.rowRadius(HEADER_HEIGHT);
        float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
        GlassPanel.frost(canvas, x, y, width, HEADER_HEIGHT, radius, tc.module, 0.70f, rowAlpha);
        GlassPanel.rim(canvas, x, y, width, HEADER_HEIGHT, radius, tc.rim, alpha, 0.10f);

        hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HEADER_HEIGHT;
        if (hover > 0.01f) {
            GlassPanel.fill(canvas, x, y, width, HEADER_HEIGHT, radius, tc.surfaceHover, rowAlpha * hover);
        }

        boolean pressed = press.apply(canvas, x, y, width, HEADER_HEIGHT);
        float centerY = y + HEADER_HEIGHT / 2f;
        FontRenderer.drawTextBold(canvas, title, x + PAD_X, CardLayout.baseline(centerY, TITLE_SIZE), TITLE_SIZE,
                GlassPanel.withAlpha(tc.primaryText, alpha));

        float progress = expand.value();
        float arrowWidth = FontRenderer.measureTextWidth(ARROW, ARROW_SIZE, FontRenderer.MATERIAL_SYMBOLS);
        float arrowCenterX = x + width - PAD_X - arrowWidth / 2f;

        if (summary != null && progress < 0.99f) {
            String text = summary.get();
            if (text != null && !text.isBlank()) {
                float available = width - PAD_X * 2f - arrowWidth - SUMMARY_GAP
                        - FontRenderer.measureTextWidthBold(title, TITLE_SIZE) - SUMMARY_GAP;
                if (available > 24f) {
                    String shown = CardLayout.ellipsize(text, available, SUMMARY_SIZE);
                    float textWidth = FontRenderer.measureTextWidth(shown, SUMMARY_SIZE);
                    FontRenderer.drawText(canvas, shown, arrowCenterX - arrowWidth / 2f - SUMMARY_GAP - textWidth,
                            CardLayout.baseline(centerY, SUMMARY_SIZE), SUMMARY_SIZE,
                            GlassPanel.withAlpha(tc.labelTertiary, alpha * (1f - progress)));
                }
            }
        }

        // 箭头绕自身中心旋转，收起朝右、展开朝下
        canvas.save();
        canvas.translate(arrowCenterX, centerY);
        canvas.rotate(ARROW_SPIN * progress);
        canvas.translate(-arrowCenterX, -centerY);
        FontRenderer.drawText(canvas, ARROW, arrowCenterX - arrowWidth / 2f,
                centerY + ARROW_SIZE * ARROW_CENTER_RATIO, ARROW_SIZE,
                GlassPanel.withAlpha(tc.mutedText, alpha), FontRenderer.MATERIAL_SYMBOLS);
        canvas.restore();
        if (pressed) canvas.restore();

        if (progress <= 0.01f || content.isEmpty()) return;
        float contentTop = y + HEADER_HEIGHT + CONTENT_GAP;
        float visibleHeight = progress * content.height();
        canvas.save();
        canvas.clipRect(Rect.makeXYWH(x, contentTop, width, visibleHeight));
        try {
            content.draw(canvas, x, contentTop, width, alpha * progress, contentTop, contentTop + visibleHeight,
                    mouseX, mouseY);
        } finally {
            canvas.restore();
        }
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        if (button != 0) return false;
        if (mx >= x && mx <= x + width && my >= y && my <= y + HEADER_HEIGHT) {
            expanded = !expanded;
            press.pulse();
            return true;
        }
        if (expand.value() <= 0.01f || content.isEmpty()) return false;
        float contentTop = y + HEADER_HEIGHT + CONTENT_GAP;
        return content.onClick(mx, my, x, contentTop, width, contentTop + expand.value() * content.height(), button);
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        if (expand.value() <= 0.01f || content.isEmpty()) return false;
        float contentTop = y + HEADER_HEIGHT + CONTENT_GAP;
        return content.onDrag(mx, my, x, contentTop, width, contentTop + expand.value() * content.height());
    }
}
