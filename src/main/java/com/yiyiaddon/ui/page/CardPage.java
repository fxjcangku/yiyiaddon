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
 * <p>滚动、命中与悬停动画的几何计算完全相同，因此集中在这里；子类只需给出卡片高度、单张卡片的
 * 绘制与点击行为，以及需要时覆写一组逐张卡片生效的几何钩子（列宽 / 左右上下坐标 / 命中 / 总高度）。</p>
 *
 * <p>默认几何＝整页等宽网格，列宽、行列坐标与总高度全部来自 {@link CardLayout}；行宽随行变化的页面
 * （模块中心：分类头独占一行、其下的模块横向并排）覆写钩子即可，绘制、悬停、点击与滚动总高度共用
 * 同一份结果，卡片紧凑后鼠标命中仍与画面一致。行距默认 {@link CardLayout#GAP_Y}，紧凑清单页可覆写
 * {@link #rowGap()} 收得更紧。</p>
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
    /** 卡片区顶部留白：页面副标题与第一行卡片之间，避免卡片顶边（含投影）贴住标题区。 */
    private static final float TOP_INSET = CardLayout.TOP_INSET;
    /** 视口外剔除的余量：卡片投影会向外糊开，正好压着上下边界的那一张仍要画，否则边界上会出现一条硬切。 */
    private static final float CULL_MARGIN = 24f;

    private Spring[] hoverSpring;
    private PressState[] pressState;
    private int cardCount;

    private float lastOriginX;
    private float lastOriginY;
    private float lastContentW;
    /** 本帧内容区可视高度；视口外剔除用（见 {@link #draw}）。 */
    private float lastContentH;
    private float lastMouseX = Float.NaN;
    private float lastMouseY = Float.NaN;

    protected CardPage(int cardCount) {
        setCardCount(cardCount);
    }

    /**
     * 重建卡片数量（给「按分类展开 / 收起的清单」用）。
     *
     * <p>数量变化时重排动画状态数组：同下标的状态原地保留，因此展开一个分组不会让已在过渡中的行
     * 突然起跳，新增的下标从零开始。数量不变时是空操作。</p>
     */
    protected final void setCardCount(int count) {
        int next = Math.max(0, count);
        if (hoverSpring != null && next == cardCount) return;
        int keep = hoverSpring == null ? 0 : Math.min(hoverSpring.length, next);
        Spring[] springs = new Spring[next];
        PressState[] presses = new PressState[next];
        for (int i = 0; i < next; i++) {
            if (i < keep) {
                springs[i] = hoverSpring[i];
                presses[i] = pressState[i];
            } else {
                springs[i] = Spring.critical(HOVER_SETTLE);
                presses[i] = new PressState();
            }
        }
        this.cardCount = next;
        this.hoverSpring = springs;
        this.pressState = presses;
    }

    protected int cardCount() {
        return cardCount;
    }

    /**
     * 卡片列数（整页等宽网格的页面覆写）；默认单列。
     *
     * <p>行宽随行变化的页面<b>不</b>覆写它，而是覆写下面那一组几何钩子：模块中心的分类头要独占
     * 一整行、其下的模块又要按网格横向并排，一张卡片的宽度不再由「全页列数」决定，只有逐张算
     * 才排得对。</p>
     */
    protected int columns() {
        return 1;
    }

    /** 单张卡片高度。 */
    protected abstract float cardHeight();

    /**
     * 第 index 张卡片的宽度。
     *
     * <p>默认实现＝整页等宽网格。绘制、悬停、点击与滚动总高度<b>必须</b>全部走
     * {@link #cardWidth} / {@link #cardX} / {@link #cardY} / {@link #indexAt} / {@link #contentHeight}
     * 这一组钩子：几何只要有两处算法，命中就会与画面错位。</p>
     */
    protected float cardWidth(float contentW, int index) {
        return CardLayout.cardWidth(contentW, columns());
    }

    /** 第 index 张卡片的左边界。 */
    protected float cardX(float originX, float contentW, int index) {
        return CardLayout.cardX(originX, contentW, columns(), index);
    }

    /** 第 index 张卡片的顶边界。 */
    protected float cardY(float originY, float contentW, int index) {
        return CardLayout.cardY(originY, cardHeight(), rowGap(), columns(), index);
    }

    /** 卡片内容的总高度（不含顶部留白）；滚动范围与画面共用同一份结果。 */
    protected float contentHeight(float contentW) {
        return CardLayout.totalHeight(cardCount, cardHeight(), rowGap(), columns());
    }

    /**
     * 命中测试：返回命中的卡片下标，未命中返回 {@code -1}。
     *
     * @param originY 卡片区顶部（已扣除滚动偏移）
     */
    protected int indexAt(float mx, float my, float originX, float originY, float contentW) {
        return CardLayout.indexAt(mx, my, originX, originY, contentW, cardHeight(), rowGap(), columns(), cardCount);
    }

    /**
     * 行间距；默认 {@link CardLayout#GAP_Y}（14，给带投影的卡片网格留的）。
     *
     * <p>紧凑清单页可以覆写成更小值：模块中心的行高只有 {@link com.yiyiaddon.ui.component.ModuleRow#HEIGHT}，
     * 再按 14 排行会显得松散、展开后的整组节奏断裂（用户 2026-09-16 要「做小一点」）。行距必须由
     * 绘制、命中、悬停、总高度共用同一个值，否则命中框会与画面错位。</p>
     */
    protected float rowGap() {
        return CardLayout.GAP_Y;
    }

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
        if (cardCount == 0) return TOP_INSET + EMPTY_STATE_HEIGHT;
        // 宽度来自上一帧的绘制（滚动上限在绘制之前刷新），页面自己兜底处理「还不知道宽度」的情形
        return TOP_INSET + contentHeight(lastContentW);
    }

    @Override
    public void update(float dt) {
        int target = Float.isNaN(lastMouseX)
                ? -1
                : indexAt(lastMouseX, lastMouseY, lastOriginX, lastOriginY, lastContentW);
        for (int i = 0; i < cardCount; i++) {
            hoverSpring[i].setTarget(i == target ? 1f : 0f);
            hoverSpring[i].update(dt);
            pressState[i].update(dt);
        }
    }

    /**
     * 本帧指针横坐标（设计空间）。
     *
     * <p>卡片绘制时需要它做两件事：判断指针是否落在自己身上、以及把悬停浮层锚在指针旁边。
     * {@code drawCard} 拿不到指针坐标，因此由这里给出本帧 {@link #draw} 收到的同一份值。</p>
     */
    protected final float frameMouseX() {
        return lastMouseX;
    }

    /** 本帧指针纵坐标（设计空间）；与 {@link #frameMouseX()} 同一来源。 */
    protected final float frameMouseY() {
        return lastMouseY;
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float contentW, float contentH, float alpha, float scrollOffset,
                     float mouseX, float mouseY) {
        lastOriginX = x;
        lastOriginY = y + TOP_INSET - scrollOffset;
        lastContentW = contentW;
        lastContentH = contentH;
        lastMouseX = mouseX;
        lastMouseY = mouseY;

        if (cardCount == 0) {
            drawEmptyState(canvas, x, y + TOP_INSET - scrollOffset, contentW, alpha);
            return;
        }

        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float cardH = cardHeight();
        // 视口外剔除：模块中心把多个分组全部展开后，清单长度会超过一屏，屏幕裁剪之外的行本来也看不见，
        // 再把它们逐行布局、逐层画一遍只是白烧帧时（用户 2026-09-21：「特别是展开多个分组的时候 挤满了画面」；
        // 这一帧的帧时会被拉长，正在跑的展开动画就顿一下）。命中不在这里判，因此剔除不影响可点性。
        float viewTop = lastOriginY - TOP_INSET;
        float viewBottom = viewTop + lastContentH;
        for (int i = 0; i < cardCount; i++) {
            float hover = hoverSpring[i].value();
            // 宽度逐张问：分类头独占一行、模块按网格并排的页面，一行里每张卡的宽度并不相同
            float cardW = cardWidth(contentW, i);
            float cx = cardX(x, contentW, i);
            float cy = cardY(lastOriginY, contentW, i) - hover * HOVER_LIFT;
            if (cy + cardH + CULL_MARGIN < viewTop || cy - CULL_MARGIN > viewBottom) continue;
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
        int index = indexAt(mx, my, contentX, contentY + TOP_INSET - scrollOffset, contentW);
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
