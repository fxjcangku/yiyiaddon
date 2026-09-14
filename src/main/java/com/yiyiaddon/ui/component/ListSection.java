package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 列表区：纵向排布若干行元素，行数可变，为空时显示空态文案。
 *
 * <p>用于「一条记录一行」的清单（ID 清单、选择器候选、点位列表）。行元素通常是
 * {@link ListRow}，但任意 {@link CompactElement} 都可放入。行数与内容由调用方在数据变化时通过
 * {@link #setRows} 重设，本类不做缓存也不持有业务状态。</p>
 *
 * <p>高度按行实时求和，因此宿主（如 {@code CompactModulePage}）每帧推算布局时会自动跟随行数变化。</p>
 */
public class ListSection implements CompactElement {

    /** 行间距。 */
    public static final float GAP = 6f;
    /** 空态区域高度。 */
    public static final float EMPTY_HEIGHT = 64f;

    private static final float EMPTY_SIZE = 11f;
    private static final float EMPTY_PAD_X = 6f;

    private final List<CompactElement> rows = new ArrayList<>();
    private Supplier<String> emptyText = () -> "";

    public ListSection() {
    }

    public ListSection(Supplier<String> emptyText) {
        setEmptyText(emptyText);
    }

    /** 重设全部行；传入 null 等同清空。 */
    public void setRows(List<? extends CompactElement> newRows) {
        rows.clear();
        if (newRows != null) rows.addAll(newRows);
    }

    public void clear() {
        rows.clear();
    }

    public int size() {
        return rows.size();
    }

    public void setEmptyText(Supplier<String> text) {
        if (text != null) this.emptyText = text;
    }

    @Override
    public float height() {
        if (rows.isEmpty()) return EMPTY_HEIGHT;
        float total = 0f;
        for (CompactElement row : rows) total += row.height();
        return total + GAP * (rows.size() - 1);
    }

    @Override
    public void update(float dt) {
        for (CompactElement row : rows) row.update(dt);
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        if (rows.isEmpty()) {
            String text = emptyText.get();
            if (text == null || text.isEmpty()) return;
            MinecraftText.draw(canvas, text, x + EMPTY_PAD_X,
                    y + EMPTY_HEIGHT / 2f + EMPTY_SIZE * 0.36f, EMPTY_SIZE,
                    ClickGuiThemeColors.current().labelTertiary, alpha);
            return;
        }
        float cursor = y;
        for (CompactElement row : rows) {
            row.draw(canvas, x, cursor, width, alpha, mouseX, mouseY);
            cursor += row.height() + GAP;
        }
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        float cursor = y;
        for (CompactElement row : rows) {
            float rowHeight = row.height();
            if (my >= cursor && my <= cursor + rowHeight
                    && row.onClick(mx, my, x, cursor, width, button)) {
                return true;
            }
            cursor += rowHeight + GAP;
        }
        return false;
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        float cursor = y;
        for (CompactElement row : rows) {
            float rowHeight = row.height();
            if (my >= cursor - GAP && my <= cursor + rowHeight + GAP
                    && row.onDrag(mx, my, x, cursor, width)) {
                return true;
            }
            cursor += rowHeight + GAP;
        }
        return false;
    }
}
