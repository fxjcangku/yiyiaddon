package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.ui.anim.PressState;
import com.yiyiaddon.ui.anim.Spring;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingWidget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.Rect;

import java.util.List;
import java.util.function.Supplier;

import static com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen.LABEL_SIZE;
import static com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen.NOTE_SIZE;
import static com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen.PAD_X;
import static com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen.TIP_OFFSET_X;
import static com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen.TIP_OFFSET_Y;

/**
 * 星露谷控制台的页内自造构件（逐字搬运自 {@code StardewConsoleScreen}）。
 *
 * <p>{@link ConsoleRow} / {@link FoldSection} / {@link Note} 是为「标签解析 §、行内多控件、
 * 独立 tooltip」而做的页内构件，**不是**新壳的通用控件：外观、高度、命中口径一字未改，
 * 只把「向宿主登记 tooltip」从内部类改为显式持有宿主窗口引用。</p>
 */
public final class StardewConsoleWidgets {

    private StardewConsoleWidgets() {
    }

    /** 行内控件 + 它的 tooltip（tooltip 可为 null） */
    public record Ctl(SettingWidget widget, Supplier<String> hint) {

        public Ctl(SettingWidget widget) {
            this(widget, (Supplier<String>) null);
        }

        public Ctl(SettingWidget widget, String hint) {
            this(widget, hint == null ? null : () -> hint);
        }

        private String resolvedHint() {
            return hint == null ? null : hint.get();
        }
    }

    /**
     * 「左标签 + 灰色行尾注释 + 右侧控件」行。
     *
     * <p>标签与注释都经 {@link MinecraftText} 绘制（旧项目文案带 {@code §} 颜色码，必须逐字保留）；
     * 控件右对齐、按声明顺序依次排列，每个控件可带自己的悬停说明。</p>
     */
    public static final class ConsoleRow implements CompactElement {

        private static final float HEIGHT = 36f;
        private static final float COMMENT_SIZE = 10f;
        private static final float CONTROL_GAP = 10f;
        private static final float HINT_GAP = 12f;
        private static final float HINT_MIN_WIDTH = 24f;
        private static final float HOVER_SMOOTHING = 12f;

        private final StardewConsoleScreen owner;
        private final Supplier<String> label;
        private final Supplier<String> labelHint;
        private final Supplier<String> comment;
        private final List<Ctl> controls;

        private boolean hovered;
        private float hover;

        public ConsoleRow(StardewConsoleScreen owner, Supplier<String> label, String labelHint, String comment, List<Ctl> controls) {
            this.owner = owner;
            this.label = label == null ? () -> "" : label;
            this.labelHint = labelHint == null ? null : () -> labelHint;
            this.comment = comment == null ? null : () -> comment;
            this.controls = List.copyOf(controls);
        }

        @Override
        public float height() {
            return HEIGHT;
        }

        @Override
        public void update(float dt) {
            for (Ctl ctl : controls) ctl.widget().update(dt);
            hover += ((hovered ? 1f : 0f) - hover) * Math.min(1f, Math.max(0f, dt) * HOVER_SMOOTHING);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            ClickGuiThemeColors tc = ClickGuiThemeColors.current();
            float radius = GlassPanel.rowRadius(HEIGHT);
            float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
            GlassPanel.frost(canvas, x, y, width, HEIGHT, radius, tc.module, 0.70f, rowAlpha);
            GlassPanel.rim(canvas, x, y, width, HEIGHT, radius, tc.rim, alpha, 0.10f);

            hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HEIGHT;
            if (hover > 0.01f) {
                GlassPanel.fill(canvas, x, y, width, HEIGHT, radius, tc.surfaceHover, rowAlpha * hover);
            }

            float centerY = y + HEIGHT / 2f;
            float cursor = x + PAD_X;
            String labelText = label.get();
            if (labelText != null && !labelText.isEmpty()) {
                MinecraftText.draw(canvas, labelText, cursor, CardLayout.baseline(centerY, LABEL_SIZE),
                    LABEL_SIZE, tc.primaryText, alpha);
                cursor += MinecraftText.measure(labelText, LABEL_SIZE, false) + HINT_GAP;
            }

            float controlsX = controlsStartX(x, width);
            String commentText = comment == null ? null : comment.get();
            if (commentText != null && !commentText.isEmpty()) {
                float available = Math.max(0f, controlsX - cursor - HINT_GAP);
                if (available >= HINT_MIN_WIDTH) {
                    MinecraftText.draw(canvas,
                        CardLayout.ellipsize(MinecraftText.strip(commentText), available, COMMENT_SIZE),
                        cursor, CardLayout.baseline(centerY, COMMENT_SIZE), COMMENT_SIZE,
                        tc.labelTertiary, alpha);
                }
            }

            float controlX = controlsX;
            for (Ctl ctl : controls) {
                SettingWidget widget = ctl.widget();
                float widgetY = y + (HEIGHT - widget.getHeight()) / 2f;
                widget.hover(mouseX, mouseY, controlX, widgetY, widget.getWidth());
                widget.draw(canvas, controlX, widgetY, alpha);
                controlX += widget.getWidth() + CONTROL_GAP;
            }

            // 悬停说明：优先控件自己的（旧项目按钮 tooltip），否则整行标签的说明
            String hint = hovered ? hoveredHint(mouseX, mouseY, x, y, width) : null;
            if (hint != null) owner.tip(hint, mouseX + TIP_OFFSET_X, mouseY + TIP_OFFSET_Y);
        }

        /** 悬停命中的说明：先逐控件，再整行标签 */
        private String hoveredHint(float mouseX, float mouseY, float x, float y, float width) {
            float controlX = controlsStartX(x, width);
            for (Ctl ctl : controls) {
                SettingWidget widget = ctl.widget();
                float widgetY = y + (HEIGHT - widget.getHeight()) / 2f;
                boolean inside = mouseX >= controlX && mouseX <= controlX + widget.getWidth()
                    && mouseY >= widgetY && mouseY <= widgetY + widget.getHeight();
                if (inside) {
                    String hint = ctl.resolvedHint();
                    if (hint != null && !hint.isBlank()) return hint;
                }
                controlX += widget.getWidth() + CONTROL_GAP;
            }
            String labelText = label.get();
            if (labelText != null && !labelText.isEmpty()) {
                float labelEnd = x + PAD_X + MinecraftText.measure(labelText, LABEL_SIZE, false);
                if (mouseX <= labelEnd + HINT_GAP) return labelHint == null ? null : labelHint.get();
            }
            return null;
        }

        /** 第一个控件的左边界（整组控件右对齐） */
        private float controlsStartX(float x, float width) {
            float total = 0f;
            for (Ctl ctl : controls) total += ctl.widget().getWidth() + CONTROL_GAP;
            if (total > 0f) total -= CONTROL_GAP;
            return x + width - PAD_X - total;
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            if (button != 0 || my < y || my > y + HEIGHT) return false;
            float controlX = controlsStartX(x, width);
            for (Ctl ctl : controls) {
                SettingWidget widget = ctl.widget();
                float widgetY = y + (HEIGHT - widget.getHeight()) / 2f;
                if (widget.onClick(mx, my, controlX, widgetY, button)) return true;
                controlX += widget.getWidth() + CONTROL_GAP;
            }
            return false;
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            float controlX = controlsStartX(x, width);
            for (Ctl ctl : controls) {
                SettingWidget widget = ctl.widget();
                float widgetY = y + (HEIGHT - widget.getHeight()) / 2f;
                if (widget.onDrag(mx, my, controlX, widgetY)) return true;
                controlX += widget.getWidth() + CONTROL_GAP;
            }
            return false;
        }
    }

    /**
     * 只读文字行（页脚灰字、空态、任务细节、分区标题、日志行）。
     *
     * <p>整行文字经 {@link MinecraftText} 绘制，颜色码逐字保留；带说明的行悬停时显示 tooltip。</p>
     */
    public static final class Note implements CompactElement {

        private static final float HEIGHT = 20f;

        private final StardewConsoleScreen owner;
        private final Supplier<String> text;
        private final String hint;
        private final float height;
        private final float size;

        public Note(StardewConsoleScreen owner, String text) {
            this(owner, () -> text, null, HEIGHT, NOTE_SIZE);
        }

        public Note(StardewConsoleScreen owner, Supplier<String> text) {
            this(owner, text, null, HEIGHT, NOTE_SIZE);
        }

        public Note(StardewConsoleScreen owner, String text, String hint) {
            this(owner, () -> text, hint, HEIGHT, NOTE_SIZE);
        }

        public Note(StardewConsoleScreen owner, String text, String hint, float height, float size) {
            this(owner, () -> text, hint, height, size);
        }

        public Note(StardewConsoleScreen owner, Supplier<String> text, String hint, float height, float size) {
            this.owner = owner;
            this.text = text == null ? () -> "" : text;
            this.hint = hint;
            this.height = height;
            this.size = size;
        }

        @Override
        public float height() {
            return height;
        }

        @Override
        public void update(float dt) {
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            String value = text.get();
            if (value != null && !value.isEmpty()) {
                MinecraftText.draw(canvas, value, x + 6f, CardLayout.baseline(y + height / 2f, size),
                    size, ClickGuiThemeColors.current().primaryText, alpha);
            }
            if (hint != null && mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
                owner.tip(hint, mouseX + TIP_OFFSET_X, mouseY + TIP_OFFSET_Y);
            }
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

    /** 只读多列表格（概览页的作物进度）：表头 + 数据行，列宽按权重分配 */
    public static final class Table implements CompactElement {

        private static final float HEADER_ROW = 24f;
        private static final float DATA_ROW = 22f;
        private static final float CELL_PAD = 6f;
        private static final float CELL_SIZE = 11f;

        private final List<String> header;
        private final List<List<String>> rows;
        private final float[] weights;

        public Table(List<String> header, List<List<String>> rows, float[] weights) {
            this.header = header;
            this.rows = rows;
            this.weights = weights;
        }

        @Override
        public float height() {
            return (header == null ? 0f : HEADER_ROW) + rows.size() * DATA_ROW;
        }

        @Override
        public void update(float dt) {
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            float available = width - CELL_PAD * 2f;
            float cursorY = y;
            if (header != null) {
                drawRow(canvas, header, x, cursorY, available, HEADER_ROW, alpha);
                cursorY += HEADER_ROW;
            }
            for (List<String> row : rows) {
                drawRow(canvas, row, x, cursorY, available, DATA_ROW, alpha);
                cursorY += DATA_ROW;
            }
        }

        private void drawRow(Canvas canvas, List<String> cells, float x, float y, float available,
                             float rowHeight, float alpha) {
            float cursorX = x + CELL_PAD;
            for (int i = 0; i < cells.size(); i++) {
                MinecraftText.draw(canvas, cells.get(i), cursorX,
                    CardLayout.baseline(y + rowHeight / 2f, CELL_SIZE),
                    CELL_SIZE, ClickGuiThemeColors.current().primaryText, alpha);
                cursorX += weight(i) * available;
            }
        }

        private float weight(int index) {
            if (weights != null && index < weights.length) return weights[index];
            return 1f / Math.max(1, header == null ? 1 : header.size());
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

    /**
     * 可折叠作物配置块（标题默认展开、点标题收起）。
     *
     * <p><b>为什么不用 {@code CollapsibleSection}：</b>它的标题走 {@code FontRenderer.drawTextBold}，
     * 不解析 {@code §} 颜色码，而旧项目该标题是 {@code §b§l<作物名> §8· §7独立配置  §8(点标题可收起)}；
     * 直接复用会把 {@code §b§l} 原样画到界面上。因此这里用同款外观（玻璃行 + 箭头 + 弹簧高度过渡）
     * 但标题经 {@link MinecraftText} 绘制，文案逐字保留。</p>
     */
    public static final class FoldSection implements CompactElement {

        private static final float HEADER_HEIGHT = 36f;
        private static final float CONTENT_GAP = 6f;
        private static final float TITLE_SIZE = 12f;
        private static final float ARROW_SIZE = 14f;
        private static final float ARROW_SPIN = 90f;
        private static final float ARROW_CENTER_RATIO = 0.36f;
        private static final float HOVER_SMOOTHING = 12f;
        private static final String ARROW = "\uE5CC";

        private final String title;
        private final CompactStack content = new CompactStack(CONTENT_GAP);
        private final Spring expand = Spring.critical(0.24f);
        private final PressState press = new PressState();

        /** 默认展开：玩家点进来就要看到「种多少」（旧项目 LeftAlignedSection 传 true） */
        private boolean expanded = true;
        private boolean hovered;
        private float hover;

        public FoldSection(String title) {
            this.title = title == null ? "" : title;
        }

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
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
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
            MinecraftText.draw(canvas, title, x + PAD_X, CardLayout.baseline(centerY, TITLE_SIZE),
                TITLE_SIZE, tc.primaryText, alpha, true);

            float progress = expand.value();
            float arrowWidth = MinecraftText.measure(ARROW, ARROW_SIZE, false);
            float arrowCenterX = x + width - PAD_X - arrowWidth / 2f;

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
                content.draw(canvas, x, contentTop, width, alpha * progress, contentTop,
                    contentTop + visibleHeight, mouseX, mouseY);
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
            return content.onClick(mx, my, x, contentTop, width, contentTop + expand.value() * content.height(),
                button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            if (expand.value() <= 0.01f || content.isEmpty()) return false;
            float contentTop = y + HEADER_HEIGHT + CONTENT_GAP;
            return content.onDrag(mx, my, x, contentTop, width, contentTop + expand.value() * content.height());
        }

        @Override
        public void releaseDrag() {
            content.releaseDrag();
        }
    }

    /** 一排等宽按钮（页签行、页脚、日志动作、清空全部点位）：每个按钮可带自己的悬停说明 */
    public static final class ButtonStrip implements CompactElement {

        public static final float BUTTON_HEIGHT = 24f;
        public static final float TAB_HEIGHT = 28f;
        private static final float GAP = 6f;

        private final StardewConsoleScreen owner;
        private final List<Ctl> buttons;
        private final float rowHeight;

        public ButtonStrip(StardewConsoleScreen owner, List<Ctl> buttons, float rowHeight) {
            this.owner = owner;
            this.buttons = List.copyOf(buttons);
            this.rowHeight = rowHeight;
        }

        @Override
        public float height() {
            return rowHeight;
        }

        @Override
        public void update(float dt) {
            for (Ctl ctl : buttons) ctl.widget().update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            if (buttons.isEmpty()) return;
            float buttonWidth = buttonWidth(width);
            float cursor = x;
            for (Ctl ctl : buttons) {
                SettingWidget widget = ctl.widget();
                widget.hover(mouseX, mouseY, cursor, y, buttonWidth);
                if (widget instanceof Button button) {
                    button.drawAt(canvas, cursor, y, buttonWidth, alpha);
                } else {
                    widget.draw(canvas, cursor, y, alpha);
                }
                boolean inside = mouseX >= cursor && mouseX <= cursor + buttonWidth
                    && mouseY >= y && mouseY <= y + rowHeight;
                String hint = inside ? ctl.resolvedHint() : null;
                if (hint != null && !hint.isBlank()) {
                    owner.tip(hint, mouseX + TIP_OFFSET_X, mouseY + TIP_OFFSET_Y);
                }
                cursor += buttonWidth + GAP;
            }
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            if (buttons.isEmpty() || button != 0) return false;
            if (my < y || my > y + rowHeight) return false;
            float buttonWidth = buttonWidth(width);
            float cursor = x;
            for (Ctl ctl : buttons) {
                SettingWidget widget = ctl.widget();
                if (widget instanceof Button target) {
                    if (target.onClickAt(mx, my, cursor, y, buttonWidth, button)) return true;
                } else if (widget.onClick(mx, my, cursor, y, button)) {
                    return true;
                }
                cursor += buttonWidth + GAP;
            }
            return false;
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }

        private float buttonWidth(float width) {
            int count = buttons.size();
            return Math.max(24f, (width - GAP * (count - 1)) / count);
        }
    }
}
