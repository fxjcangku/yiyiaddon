package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.FontRenderer;

/**
 * 卡片清单布局与文本适配。
 *
 * <p>模块中心（单列清单）与模块列表（双列网格）共用同一套列宽、间距与命中判定；列数由页面传入，
 * 因此不存在「两个页面各写一份几何计算」。</p>
 *
 * <p>间距为 14。曾用过 10：卡片自带向外的投影，相邻两排（两列）的投影会糊在一起，
 * 实机反馈要拉开，因此定在 14。</p>
 *
 * <p>绘制与命中都走 {@link #cardX} / {@link #cardY} / {@link #indexAt}，鼠标命中、
 * 悬停与滚动始终与画面一致。最后一行卡片数不足列数时，只按真实卡片数绘制与命中，不会多出空位，
 * 总高度也不含空行。</p>
 */
public final class CardLayout {

    /** 模块列表列数：模块名与描述较长，两列可完整显示。 */
    public static final int MODULE_COLUMNS = 2;

    /** 列间距。 */
    public static final float GAP_X = 14f;
    /** 行间距。 */
    public static final float GAP_Y = 14f;

    /**
     * 卡片区顶部留白：页面副标题与第一行卡片之间，避免卡片顶边（含投影）贴住标题区。
     *
     * <p>所有「一屏卡片」的页面共用这一份（首页与卡片页各写一份时，首卡高度会不一致）。</p>
     */
    public static final float TOP_INSET = 20f;

    private CardLayout() {
    }

    /** 单张卡片宽度，列数至少按 1 处理。 */
    public static float cardWidth(float contentW, int columns) {
        int cols = Math.max(1, columns);
        return (contentW - GAP_X * (cols - 1)) / cols;
    }

    /** 第 index 张卡片的左边界；绘制与命中共用。 */
    public static float cardX(float originX, float contentW, int columns, int index) {
        int cols = Math.max(1, columns);
        return originX + (index % cols) * (cardWidth(contentW, cols) + GAP_X);
    }

    /** 第 index 张卡片的顶边界；绘制与命中共用。 */
    public static float cardY(float originY, float cardH, int columns, int index) {
        int cols = Math.max(1, columns);
        return originY + (index / cols) * (cardH + GAP_Y);
    }

    /** 总行数；卡片数不是列数整数倍时最后一行按剩余卡片计。 */
    public static int rows(int count, int columns) {
        if (count <= 0) return 0;
        int cols = Math.max(1, columns);
        return (count + cols - 1) / cols;
    }

    /** 全部卡片占用的总高度。 */
    public static float totalHeight(int count, float cardH, int columns) {
        int rows = rows(count, columns);
        if (rows <= 0) return 0f;
        return rows * cardH + (rows - 1) * GAP_Y;
    }

    /**
     * 命中测试。
     *
     * @param originY 卡片区顶部（已扣除滚动偏移前的坐标）
     * @return 命中的卡片下标，未命中返回 -1
     */
    public static int indexAt(float mx, float my, float originX, float originY, float contentW, float cardH,
                              int columns, int count) {
        if (count <= 0) return -1;
        float cardW = cardWidth(contentW, columns);
        for (int i = 0; i < count; i++) {
            float cx = cardX(originX, contentW, columns, i);
            float cy = cardY(originY, cardH, columns, i);
            if (mx >= cx && mx <= cx + cardW && my >= cy && my <= cy + cardH) return i;
        }
        return -1;
    }

    /** 文本垂直居中时的基线位置。 */
    public static float baseline(float centerY, float size) {
        return centerY + size * 0.46f;
    }

    /** 超宽文本截断并追加省略号；未超宽时原样返回。 */
    public static String ellipsize(String text, float maxWidth, float size) {
        if (text == null || text.isEmpty()) return "";
        if (FontRenderer.measureTextWidth(text, size) <= maxWidth) return text;

        String ellipsis = "…";
        float ellipsisWidth = FontRenderer.measureTextWidth(ellipsis, size);
        int end = text.length();
        while (end > 0) {
            String candidate = text.substring(0, end);
            if (FontRenderer.measureTextWidth(candidate, size) + ellipsisWidth <= maxWidth) {
                return candidate + ellipsis;
            }
            end--;
        }
        return ellipsis;
    }
}
