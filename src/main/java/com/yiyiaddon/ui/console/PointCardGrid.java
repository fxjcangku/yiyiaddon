package com.yiyiaddon.ui.console;

import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import io.github.humbleui.skija.Canvas;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

/**
 * 点位卡片与点位卡网格（2026-09-16 由星露谷控制台「点位」页抽出，<b>星露谷与自动挖矿共用</b>）。
 *
 * <p>{@link PointCard} 逐字搬自 {@code StardewPointPage.PointCard}：常量、绘制与命中逻辑一字未改，
 * 只把页内私有构造器改为 {@code public}（跨类构造需要）。{@link Grid} 是纯卡片网格，只做
 * 「N 张卡按 {@link CardLayout} 两列排布」这一件事；星露谷那页的「农田卡 + 用法说明 + 卡片网格」
 * 混合布局仍留在页内，不进本件。</p>
 *
 * <p>抽出的理由：两处点位页的卡片外观、等高口径与命中算法必须完全一致，各写一份必然走偏。</p>
 */
public final class PointCardGrid {

    private PointCardGrid() {
    }

    /** 单张卡片：标题 / 状态两行 + 若干行按钮（每行 1~2 枚，行数与卡片高度一一对应） */
    public static final class PointCard implements CompactElement {

        private static final float TITLE_HEIGHT = 20f;
        private static final float INFO_HEIGHT = 18f;
        private static final float BUTTON_HEIGHT = 24f;
        private static final float CARD_GAP = 6f;
        private static final float CARD_PAD = 10f;
        private static final float TITLE_SIZE = 11f;
        private static final float INFO_SIZE = 11f;
        /** 标题与两行信息加留白的固定高度；按钮区从这儿往下排 */
        private static final float HEAD_HEIGHT = 70f;
        /** 卡片头图标的绘制边长：取控制台行内图标同一档，标题行高内放得下 */
        private static final float ICON_SIZE = 14f;
        /** 图标与标题文字之间的间距 */
        private static final float TITLE_ICON_GAP = 4f;

        private final String title;
        private final Supplier<String> info1;
        private final Supplier<String> info2;
        private final List<List<Button>> rows;
        /**
         * 可选卡片头图标（{@code null} = 不画，既有星露谷 / 挖矿调用点外观与命中口径不变）。
         *
         * <p>自动农场的六张点位卡按用户拍板 D9 要求「卡片头带图标，未绑定时也要有图标位，
         * 不留空洞」；图标画在标题行左侧居中，标题行布局为「图标 + 文字」整体居中。</p>
         */
        private Supplier<ItemStack> icon;

        /** 固定文案的两行信息（点位卡用：内容不随开关变化，取完就离开本页）。 */
        public PointCard(String title, String info1, String info2, List<List<Button>> rows) {
            this(title, () -> info1, () -> info2, rows);
        }

        /**
         * 每帧现读的两行信息（农田卡用）。
         *
         * <p>卡片只在页面构建时生成一次，写死字符串会让按钮看起来"点了没反应"——
         * 模式/作物/区域数都必须走 {@link Supplier} 现读。</p>
         */
        public PointCard(String title, Supplier<String> info1, Supplier<String> info2, List<List<Button>> rows) {
            this.title = title;
            this.info1 = info1;
            this.info2 = info2;
            this.rows = rows;
            this.icon = null;
        }

        /**
         * 给卡片头挂图标（链式）：画在标题行左侧，标题文字随之右移半格图标宽。
         *
         * <p>图标走 {@link ItemIconCache} 全屏统一链路；返回 {@code null} 或空物品时不画、
         * 也不占横向空间（与 {@code ConsoleRow.icon} 同一口径）。</p>
         */
        public PointCard icon(Supplier<ItemStack> icon) {
            this.icon = icon;
            return this;
        }

        @Override
        public float height() {
            return HEAD_HEIGHT + rows.size() * (BUTTON_HEIGHT + CARD_GAP);
        }

        @Override
        public void update(float dt) {
            for (List<Button> row : rows) {
                for (Button button : row) button.update(dt);
            }
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            ClickGuiThemeColors tc = ClickGuiThemeColors.current();
            float height = height();
            float radius = GlassPanel.rowRadius(height);
            float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
            GlassPanel.frost(canvas, x, y, width, height, radius, tc.module, 0.70f, rowAlpha);
            GlassPanel.rim(canvas, x, y, width, height, radius, tc.rim, alpha, 0.10f);

            float centerX = x + width / 2f;
            float cursorY = y + CARD_GAP;
            // 标题行：无图标时纯文字居中（既有调用点不变）；有图标时「图标 + 文字」整体居中
            ItemStack iconStack = icon == null ? null : icon.get();
            boolean drawIcon = iconStack != null && !iconStack.isEmpty();
            if (drawIcon) {
                float textWidth = MinecraftText.measure(title, TITLE_SIZE, true);
                float block = ICON_SIZE + TITLE_ICON_GAP + textWidth;
                float iconX = centerX - block / 2f;
                ItemIconCache.getInstance().draw(canvas, iconStack, iconX,
                    cursorY + (TITLE_HEIGHT - ICON_SIZE) / 2f, ICON_SIZE);
                MinecraftText.draw(canvas, title, iconX + ICON_SIZE + TITLE_ICON_GAP,
                    CardLayout.baseline(cursorY + TITLE_HEIGHT / 2f, TITLE_SIZE), TITLE_SIZE,
                    ClickGuiThemeColors.current().primaryText, alpha, true);
            } else {
                drawCentered(canvas, title, centerX, cursorY, TITLE_HEIGHT, TITLE_SIZE, alpha, true);
            }
            cursorY += TITLE_HEIGHT;
            drawCentered(canvas, info1.get(), centerX, cursorY, INFO_HEIGHT, INFO_SIZE, alpha, false);
            cursorY += INFO_HEIGHT + 2f;
            drawCentered(canvas, info2.get(), centerX, cursorY, INFO_HEIGHT, INFO_SIZE, alpha, false);
            cursorY = y + HEAD_HEIGHT;

            for (List<Button> row : rows) {
                float itemWidth = rowWidth(width, row.size());
                for (int i = 0; i < row.size(); i++) {
                    float buttonX = x + CARD_PAD + i * (itemWidth + CARD_GAP);
                    row.get(i).hover(mouseX, mouseY, buttonX, cursorY, itemWidth);
                    row.get(i).drawAt(canvas, buttonX, cursorY, itemWidth, alpha);
                }
                cursorY += BUTTON_HEIGHT + CARD_GAP;
            }
        }

        /** 一行 n 枚按钮时每枚的宽度（并排等分，中间留 CARD_GAP） */
        private float rowWidth(float cardWidth, int count) {
            float total = cardWidth - CARD_PAD * 2f;
            return (total - CARD_GAP * (count - 1)) / count;
        }

        private void drawCentered(Canvas canvas, String text, float centerX, float y, float rowHeight,
                                  float size, float alpha, boolean bold) {
            if (text == null || text.isEmpty()) return;
            float textWidth = MinecraftText.measure(text, size, bold);
            MinecraftText.draw(canvas, text, centerX - textWidth / 2f,
                CardLayout.baseline(y + rowHeight / 2f, size), size,
                ClickGuiThemeColors.current().primaryText, alpha, bold);
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            if (button != 0) return false;
            float cursorY = y + HEAD_HEIGHT;
            for (List<Button> row : rows) {
                float itemWidth = rowWidth(width, row.size());
                for (int i = 0; i < row.size(); i++) {
                    float buttonX = x + CARD_PAD + i * (itemWidth + CARD_GAP);
                    if (row.get(i).onClickAt(mx, my, buttonX, cursorY, itemWidth, button)) return true;
                }
                cursorY += BUTTON_HEIGHT + CARD_GAP;
            }
            return false;
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }

    /**
     * 纯卡片网格：N 张卡按 {@link CardLayout} 的固定列数排布（行优先、等高）。
     *
     * <p>卡片等高按第一张的高度取（卡片按钮行数一致时本来就等高，与星露谷点位卡网格同一口径）。</p>
     */
    public static final class Grid implements CompactElement {

        /** 列数：与星露谷点位页同一口径（点位卡两列排） */
        private static final int COLUMNS = 2;

        private final List<PointCard> cards;

        public Grid(List<PointCard> cards) {
            this.cards = List.copyOf(cards);
        }

        private float cardHeight() {
            return cards.isEmpty() ? 0f : cards.get(0).height();
        }

        @Override
        public float height() {
            return CardLayout.totalHeight(cards.size(), cardHeight(), COLUMNS);
        }

        @Override
        public void update(float dt) {
            for (PointCard card : cards) card.update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            if (cards.isEmpty()) return;
            float cardWidth = CardLayout.cardWidth(width, COLUMNS);
            float cardHeight = cardHeight();
            for (int i = 0; i < cards.size(); i++) {
                cards.get(i).draw(canvas, CardLayout.cardX(x, width, COLUMNS, i),
                    CardLayout.cardY(y, cardHeight, COLUMNS, i), cardWidth, alpha, mouseX, mouseY);
            }
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            if (cards.isEmpty()) return false;
            float cardWidth = CardLayout.cardWidth(width, COLUMNS);
            float cardHeight = cardHeight();
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).onClick(mx, my, CardLayout.cardX(x, width, COLUMNS, i),
                    CardLayout.cardY(y, cardHeight, COLUMNS, i), cardWidth, button)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }
}
