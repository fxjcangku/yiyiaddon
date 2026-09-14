package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

import java.util.function.Supplier;

/**
 * 单行文本元素：左对齐、垂直居中，支持 Minecraft 颜色码。
 *
 * <p>用于面板里的纯文字行——汇总、清单表头、结果字段行。字段行用 {@link #field} 生成旧项目
 * 统一的 {@code §7标签 §8▸ §f值} 格式。</p>
 */
public class TextLine implements CompactElement {

    /** 默认行高。 */
    public static final float DEFAULT_HEIGHT = 22f;

    private static final float DEFAULT_SIZE = 11f;
    private static final float PAD_X = 6f;
    private static final float BASELINE_RATIO = 0.36f;

    private final Supplier<String> text;
    private float height = DEFAULT_HEIGHT;
    private float size = DEFAULT_SIZE;
    private boolean bold;
    /** 文字基准色；0 表示跟随主题主文字色。 */
    private int color;

    public TextLine(String text) {
        this(() -> text);
    }

    public TextLine(Supplier<String> text) {
        this.text = text == null ? () -> "" : text;
    }

    /** 旧项目字段行格式：{@code §7标签 §8▸ §f值}。 */
    public static TextLine field(String label, String value) {
        return new TextLine("§7" + label + " §8▸ §f" + (value == null ? "无" : value));
    }

    public TextLine height(float height) {
        if (height > 0f) this.height = height;
        return this;
    }

    public TextLine size(float size) {
        if (size > 0f) this.size = size;
        return this;
    }

    public TextLine bold(boolean bold) {
        this.bold = bold;
        return this;
    }

    /** 覆盖文字基准色（RGB）；0 表示跟随主题。 */
    public TextLine color(int rgb) {
        this.color = rgb & 0xFFFFFF;
        return this;
    }

    @Override
    public float height() {
        return height;
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
        String value = text.get();
        if (value == null || value.isEmpty()) return;
        int base = color == 0 ? ClickGuiThemeColors.current().primaryText : color;
        MinecraftText.draw(canvas, value, x + PAD_X, y + height / 2f + size * BASELINE_RATIO,
                size, base, alpha, bold);
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, float width, int button) {
        return false;
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y, float width) {
        return false;
    }
}
