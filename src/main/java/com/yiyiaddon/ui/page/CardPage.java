package com.yiyiaddon.ui.page;

import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.anim.Spring;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import io.github.humbleui.skija.Canvas;

import org.lwjgl.glfw.GLFW;

/**
 * 卡片式页面基类。
 *
 * <p>模块中心与模块列表都是「一屏卡片网格」，滚动、命中与悬停动画的几何计算完全相同，
 * 因此集中在这里；子类只需给出卡片列数、卡片高度、单张卡片的绘制与点击行为。</p>
 *
 * <p>列宽、行列坐标与总高度全部来自 {@link CardLayout}，绘制、悬停、点击共用同一组结果，
 * 卡片紧凑后鼠标命中仍与画面一致。</p>
 *
 * <p>悬停强度用临界阻尼弹簧过渡到目标值，卡片同时上抬 1px；按下时卡片缩到 0.97。
 * 两套动画状态都在构造时一次性分配，绘制期间不产生新对象。</p>
 */
public abstract class CardPage extends BasePage {

    /** 悬停过渡时间：临界阻尼，约 160ms 到位。 */
    private static final float HOVER_SETTLE = 0.16f;
    /** 悬停上抬距离（像素）。 */
    private static final float HOVER_LIFT = 1f;
    private static final float EMPTY_STATE_HEIGHT = 96f;

    private final Spring[] hoverSpring;
    private final PressState[] pressState;
    private final int cardCount;

    private float lastOriginX;
    private float lastOriginY;
    private float lastContentW;
    private float lastMouseX = Float.NaN;
    private float lastMouseY = Float.NaN;

    protected CardPage(int cardCount) {
        this.cardCount = Math.max(0, cardCount);
        this.hoverSpring = new Spring[this.cardCount];
        this.pressState = new PressState[this.cardCount];
        for (int i = 0; i < this.cardCount; i++) {
            hoverSpring[i] = Spring.critical(HOVER_SETTLE);
            pressState[i] = new PressState();
        }
    }

    protected int cardCount() {
        return cardCount;
    }

    /** 卡片列数；由页面按卡片内容长度给出，绘制与命中都读这一个值。 */
    protected abstract int columns();

    /** 单张卡片高度。 */
    protected abstract float cardHeight();

    /** 绘制第 index 张卡片，(x, y) 为卡片左上角。 */
    protected abstract void drawCard(Canvas canvas, int index, float x, float y, float w, float alpha, float hover,
                                     ClickGuiThemeColors tc);

    /** 卡片被左键点击。 */
    protected abstract void onCardActivated(int index);

    /** 空状态文案。 */
    protected String emptyStateText() {
        return "暂无内容";
    }

    @Override
    public float getTotalHeight() {
        if (cardCount == 0) return EMPTY_STATE_HEIGHT;
        return CardLayout.totalHeight(cardCount, cardHeight(), columns());
    }

    @Override
    public void update(float dt) {
        int columns = columns();
        int target = Float.isNaN(lastMouseX)
                ? -1
                : CardLayout.indexAt(lastMouseX, lastMouseY, lastOriginX, lastOriginY, lastContentW, cardHeight(),
                        columns, cardCount);
        for (int i = 0; i < cardCount; i++) {
            hoverSpring[i].setTarget(i == target ? 1f : 0f);
            hoverSpring[i].update(dt);
            pressState[i].update(dt);
        }
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float contentW, float contentH, float alpha, float scrollOffset,
                     float mouseX, float mouseY) {
        lastOriginX = x;
        lastOriginY = y - scrollOffset;
        lastContentW = contentW;
        lastMouseX = mouseX;
        lastMouseY = mouseY;

        if (cardCount == 0) {
            drawEmptyState(canvas, x, y - scrollOffset, contentW, alpha);
            return;
        }

        int columns = columns();
        float cardW = CardLayout.cardWidth(contentW, columns);
        float cardH = cardHeight();
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        for (int i = 0; i < cardCount; i++) {
            float hover = hoverSpring[i].value();
            float cx = CardLayout.cardX(x, contentW, columns, i);
            float cy = CardLayout.cardY(lastOriginY, cardH, columns, i) - hover * HOVER_LIFT;
            float pressScale = pressState[i].scale();
            boolean pressed = Math.abs(pressScale - 1f) > 0.0005f;
            if (pressed) {
                float centerX = cx + cardW / 2f;
                float centerY = cy + cardH / 2f;
                canvas.save();
                canvas.translate(centerX, centerY);
                canvas.scale(pressScale, pressScale);
                canvas.translate(-centerX, -centerY);
            }
            drawCard(canvas, i, cx, cy, cardW, alpha, hover, tc);
            if (pressed) canvas.restore();
        }
    }

    @Override
    public boolean onClick(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset,
                           int button) {
        if (button != GLFW.GLFW_MOUSE_BUTTON_LEFT || cardCount == 0) return false;
        int index = CardLayout.indexAt(mx, my, contentX, contentY - scrollOffset, contentW, cardHeight(), columns(),
                cardCount);
        if (index < 0) return false;
        pressState[index].press();
        onCardActivated(index);
        return true;
    }

    @Override
    public void releasePress() {
        for (PressState press : pressState) press.release();
    }

    @Override
    public void cancelPress() {
        for (PressState press : pressState) press.cancel();
    }

    private void drawEmptyState(Canvas canvas, float x, float y, float contentW, float alpha) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float radius = ClickGuiThemeManager.current().metrics().moduleRadius();
        GlassPanel.shadow(canvas, x, y, contentW, EMPTY_STATE_HEIGHT, radius, tc.shadow, alpha, 0.45f);
        GlassPanel.frost(canvas, x, y, contentW, EMPTY_STATE_HEIGHT, radius, tc.module, 0.70f, alpha);
        GlassPanel.rim(canvas, x, y, contentW, EMPTY_STATE_HEIGHT, radius, tc.rim, alpha, 0.10f);
        String text = emptyStateText();
        float textWidth = FontRenderer.measureTextWidth(text, 11f);
        FontRenderer.drawText(canvas, text, x + (contentW - textWidth) / 2f,
                CardLayout.baseline(y + EMPTY_STATE_HEIGHT / 2f, 11f), 11f,
                GlassPanel.withAlpha(tc.labelTertiary, alpha));
    }
}
