package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.TooltipLayer;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BooleanSupplier;

public class SettingModule {
    public final String title;
    public final String subtitle;
    public final SettingWidget mainWidget;
    private final List<SubEntry> subEntries = new ArrayList<>();
    private BooleanSupplier visibleSupplier = () -> true;
    private String bindingId = "";
    private String keybindActionId = "";
    private boolean expanded;
    private float expandProgress;
    private float keybindWidth = 24f;
    private float keybindHover;
    private float keybindRed;

    private static final float MODULE_H = 56f;
    private static final float SUB_H = 44f;
    /** 紧凑模式：单行表头（说明移到悬停浮层）、单行子项（44 → 30），子项两列排布 */
    private static final float COMPACT_MODULE_H = 40f;
    private static final float COMPACT_SUB_H = 30f;
    private static final float COMPACT_COLUMN_GAP = 12f;
    /** 控件宽过这个值就独占一行（文本框 / 只读值 / 分段选择器放不下半列） */
    private static final float COMPACT_MAX_CELL_W = 200f;
    /**
     * 紧凑双列里一格的宽度（设计空间单位）。
     *
     * <p>面板几何全部在设计空间里（{@link com.yiyiaddon.ui.component.PanelFrame#CARD_W} = 740，
     * 与窗口尺寸无关），主界面再减去侧栏 190、页面内缩 14 与滚动条预留 34，内容宽 501，
     * 一格按 {@code (501 - 8 - 12) / 2} 折半即 240.5。装箱时用它判断标题放不放得下，
     * 因此这个值是「算出来的常量」，不是估的。</p>
     */
    private static final float COMPACT_CELL_W = 240.5f;
    /** 标题与控件之间至少留出的间距 */
    private static final float LABEL_GAP = 8f;
    /** 子项标题字号 */
    private static final float SUB_LABEL_SIZE = 12f;
    private static final float PAD_X = 20f;
    private static final float KEYBIND_H = 21f;
    private static final float KEYBIND_BASE_W = 21f;
    private static final String ARROW_EXPANDED = "\uE5CF";
    private static final String ARROW_COLLAPSED = "\uE5CC";
    private static final String KEYBIND_ICON = "\uE9FE";
    private static final String CLEAR_ICON = "\uF508";
    private final Paint modulePaint = new Paint().setAntiAlias(true);
    private final Paint subPaint = new Paint().setAntiAlias(true);
    private final Paint keybindPaint = new Paint().setAntiAlias(true);

    /** 是否用紧凑双列渲染（见 {@link #compact()}） */
    private boolean compact;
    /** 表头右侧的小标（折叠态下唯一能看到的量，例如「96 项」）；null = 不画 */
    private String badge;

    public SettingModule(String title, String subtitle, SettingWidget mainWidget) {
        this.title = title;
        this.subtitle = subtitle;
        this.mainWidget = mainWidget;
    }

    /**
     * 紧凑双列：表头压成单行、子项压成单行（名称 + 控件，说明改由悬停浮层显示），
     * 子项左右两列排布——一屏能看到的项数约为原来的四倍（Baritone 设置两百多项，经典排版要滚十几屏）。
     *
     * <p><b>只对平铺项生效：</b>带 {@link #addSubGroup} 分组的模块仍走经典布局——
     * 分组是多一层展开的树，塞进两列会把分组语义弄乱。本项目里只有 Baritone 设置页用本模式，
     * 而它全是平铺项。</p>
     *
     * <p><b>悬停浮层需要宿主支持：</b>说明文字走 {@code TooltipLayer}，宿主屏幕必须调用它的
     * {@code beginFrame} / {@code draw}（{@code ClickGuiScreen} 已接）。</p>
     */
    public SettingModule compact() {
        compact = true;
        return this;
    }

    /** 展开 / 收起（搜索时统一展开，以便直接看到命中项）。 */
    public SettingModule setExpanded(boolean value) {
        expanded = value;
        return this;
    }

    /** 表头右侧的小标，紧凑模式专用（折叠态下玩家只能看到这一行，得有个「多少项」的量）。 */
    public SettingModule badge(String text) {
        badge = text;
        return this;
    }

    public boolean isExpanded() {
        return expanded;
    }

    /** 紧凑布局是否真的生效（带分组的模块退回经典布局，高度口径也要跟着退） */
    private boolean compactLayout() {
        if (!compact) return false;
        for (SubEntry sub : subEntries) {
            if (sub.group) return false;
        }
        return true;
    }

    private float moduleHeight() {
        return compactLayout() ? COMPACT_MODULE_H : MODULE_H;
    }

    private float subHeight() {
        return compactLayout() ? COMPACT_SUB_H : SUB_H;
    }

    // ── 紧凑双列布局 ──

    /**
     * 紧凑模式的一格：可见子项按「行 × 列」排布，宽控件独占一整行。
     *
     * @param index 子项下标
     * @param row   第几行（0 起）
     * @param col   左列 0 / 右列 1（{@code span} 为 true 时恒为 0）
     * @param span  是否独占整行
     */
    private record Cell(int index, int row, int col, boolean span) {
    }

    /**
     * 排出紧凑双列的全部格子。
     *
     * <p>绘制、命中、总高度三处都读这一份结果，几何口径只有一处——否则「看到的」与「点到的」
     * 迟早会错开（紧凑模式最典型的 bug 就是点错行）。</p>
     */
    private List<Cell> compactCells() {
        List<Cell> cells = new ArrayList<>();
        int row = 0;
        int col = 0;
        for (int i = 0; i < subEntries.size(); i++) {
            SubEntry sub = subEntries.get(i);
            if (!sub.isVisible()) continue;
            if (spansWholeRow(sub)) {
                // 宽项 / 长标题：先给半行收尾，再独占一整行
                if (col != 0) {
                    row++;
                    col = 0;
                }
                cells.add(new Cell(i, row, 0, true));
                row++;
            } else {
                cells.add(new Cell(i, row, col, false));
                col++;
                if (col >= 2) {
                    col = 0;
                    row++;
                }
            }
        }
        return cells;
    }

    /**
     * 这一项是否必须独占整行：宽控件放不下半列，或者标题在半列里显示不全。
     *
     * <p><b>为什么不再截断标题：</b>实机反馈「新界面全是……省略了」——双列本来是为了少滚动，
     * 但标题被截成「方块放…」之后根本认不出是哪一项，等于拿可读性换行数。改成「放不下就占整行」后，
     * 标题永远完整，只有真的长的那些才多占一行（开关、短标题的项照样两列）。</p>
     */
    private boolean spansWholeRow(SubEntry sub) {
        if (sub.group || sub.widget == null) return true;
        float widgetW = sub.widget.getWidth();
        if (widgetW > COMPACT_MAX_CELL_W) return true;
        return titleWidth(sub.title) > compactLabelWidth(widgetW);
    }

    /**
     * 标题占位宽度：取「字体实测」与「按码位估算」里更大的那个。
     *
     * <p>为什么不能只用字体实测：字形尚未就绪（换主题 / 首次打开）时测量可能偏小甚至返回异常值，
     * 一旦偏小，装箱就会把控件摆到标题上面去——实机看到的就是「重复建造偏移」被值输入框压住。
     * 估算值只做下限：CJK 按一个字宽算，其余按 0.55 字宽，宁可多占一行也绝不重叠。</p>
     */
    private static float titleWidth(String title) {
        if (title == null || title.isEmpty()) return 0f;
        float estimate = 0f;
        for (int i = 0; i < title.length(); ) {
            int codepoint = title.codePointAt(i);
            i += Character.charCount(codepoint);
            estimate += isWide(codepoint) ? SUB_LABEL_SIZE : SUB_LABEL_SIZE * 0.55f;
        }
        return Math.max(FontRenderer.measureTextWidth(title, SUB_LABEL_SIZE), estimate);
    }

    /** 全角字符（中日韩、全角标点等）：按一个字宽计算 */
    private static boolean isWide(int codepoint) {
        return codepoint >= 0x1100 && (codepoint <= 0x115F
            || (codepoint >= 0x2E80 && codepoint <= 0xA4CF)
            || (codepoint >= 0xAC00 && codepoint <= 0xD7A3)
            || (codepoint >= 0xF900 && codepoint <= 0xFAFF)
            || (codepoint >= 0xFE30 && codepoint <= 0xFE6F)
            || (codepoint >= 0xFF00 && codepoint <= 0xFF60)
            || (codepoint >= 0xFFE0 && codepoint <= 0xFFE6)
            || (codepoint >= 0x20000 && codepoint <= 0x3FFFD));
    }

    /** 半列里标题可用的宽度：从格内左侧内缩起，到控件左侧再让出 {@link #LABEL_GAP}（与绘制同一公式） */
    private static float compactLabelWidth(float widgetW) {
        return COMPACT_CELL_W - PAD_X * 2f - widgetW - LABEL_GAP;
    }

    private int compactRowCount() {
        List<Cell> cells = compactCells();
        return cells.isEmpty() ? 0 : cells.getLast().row() + 1;
    }

    private static float compactCellWidth(float contentW) {
        return (contentW - 8f - COMPACT_COLUMN_GAP) / 2f;
    }

    private static float compactCellX(float x, float contentW, Cell cell) {
        if (cell.span()) return x + 8f;
        return x + 8f + cell.col() * (compactCellWidth(contentW) + COMPACT_COLUMN_GAP);
    }

    private static float compactCellW(float contentW, Cell cell) {
        return cell.span() ? contentW - 8f : compactCellWidth(contentW);
    }

    private float compactCellY(float y, Cell cell) {
        return y + moduleHeight() + cell.row() * subHeight();
    }

    public SettingModule addSub(String title, String subtitle, SettingWidget widget) {
        subEntries.add(new SubEntry(title, subtitle, widget, () -> true, false));
        return this;
    }

    public SettingModule addSub(String title, String subtitle, SettingWidget widget, BooleanSupplier visibleSupplier) {
        subEntries.add(new SubEntry(title, subtitle, widget, visibleSupplier, false));
        return this;
    }

    public SettingModule addSubWhen(BooleanSupplier visibleSupplier, String title, String subtitle, SettingWidget widget) {
        subEntries.add(new SubEntry(title, subtitle, widget, visibleSupplier, false));
        return this;
    }

    public SettingModule addSubGroup(String title, String subtitle) {
        subEntries.add(new SubEntry(title, subtitle, null, () -> true, true));
        return this;
    }

    public SettingModule addSubChild(String title, String subtitle, SettingWidget widget) {
        if (!subEntries.isEmpty()) {
            subEntries.getLast().children.add(new SubEntry(title, subtitle, widget, () -> true, false));
        }
        return this;
    }

    public SettingModule addSubChildWhen(BooleanSupplier visibleSupplier, String title, String subtitle, SettingWidget widget) {
        if (!subEntries.isEmpty()) {
            subEntries.getLast().children.add(new SubEntry(title, subtitle, widget, visibleSupplier, false));
        }
        return this;
    }

    public SettingModule visibleWhen(BooleanSupplier supplier) {
        visibleSupplier = supplier;
        return this;
    }

    public void setBindingId(String id) {
        if (bindingId.isEmpty()) {
            bindingId = id == null ? "" : id;
        }
    }

    public String getBindingId() {
        return bindingId;
    }

    public boolean isToggleable() {
        return mainWidget instanceof SettingToggle;
    }

    public SettingModule keybindAction(String id) {
        keybindActionId = id == null ? "" : id;
        return this;
    }

    public boolean usesActionKeybind() {
        return !keybindActionId.isBlank();
    }

    public void toggleFromKeybind() {
        if (mainWidget instanceof SettingToggle toggle) toggle.toggle();
    }

    public boolean isVisible() {
        return visibleSupplier.getAsBoolean();
    }

    private boolean isKeybindable() {
        return isToggleable() || !keybindActionId.isBlank();
    }

    private String keybindId() {
        return keybindActionId.isBlank() ? bindingId : keybindActionId;
    }

    public boolean matchesSearch(String query) {
        if (query == null || query.isBlank()) return true;
        String needle = query.toLowerCase(Locale.ROOT);
        if (UiText.matchesSearch(title, needle) || UiText.matchesSearch(subtitle, needle)) {
            return true;
        }
        for (SubEntry sub : subEntries) {
            if (UiText.matchesSearch(sub.title, needle) || UiText.matchesSearch(sub.subtitle, needle)) {
                return true;
            }
            for (SubEntry child : sub.children) {
                if (UiText.matchesSearch(child.title, needle) || UiText.matchesSearch(child.subtitle, needle)) {
                    return true;
                }
            }
        }
        return false;
    }

    public float getTotalHeight() {
        return moduleHeight() + expandProgress * visibleSubHeight();
    }

    public boolean isAnimating() {
        if (Math.abs((expanded ? 1f : 0f) - expandProgress) > 0.01f) return true;
        if (isKeybindable() && (Math.abs(keybindWidth - targetKeybindWidth()) > 0.01f || keybindHover > 0.01f || keybindRed > 0.01f)) return true;
        if (mainWidget != null && mainWidget.isAnimating()) return true;
        for (SubEntry sub : subEntries) {
            if (!sub.isVisible()) continue;
            if (Math.abs((sub.expanded ? 1f : 0f) - sub.childProgress) > 0.01f) return true;
            if (sub.widget != null && sub.widget.isAnimating()) return true;
            for (SubEntry child : sub.children) {
                if (child.isVisible() && child.widget != null && child.widget.isAnimating()) return true;
            }
        }
        return false;
    }

    public void update(float dt) {
        expandProgress += ((expanded ? 1f : 0f) - expandProgress) * Math.min(1f, dt * 14f);
        if (expandProgress < 0.001f) expandProgress = 0f;
        if (expandProgress > 0.999f) expandProgress = 1f;
        if (isKeybindable()) keybindWidth += (targetKeybindWidth() - keybindWidth) * Math.min(1f, dt * 15f);
        if (mainWidget != null) mainWidget.update(dt);
        for (SubEntry sub : subEntries) {
            if (!sub.isVisible()) continue;
            sub.childProgress += ((sub.expanded ? 1f : 0f) - sub.childProgress) * Math.min(1f, dt * 14f);
            if (sub.childProgress < 0.001f) sub.childProgress = 0f;
            if (sub.childProgress > 0.999f) sub.childProgress = 1f;
            if (sub.widget != null) sub.widget.update(dt);
            for (SubEntry child : sub.children) {
                if (child.isVisible() && child.widget != null) child.widget.update(dt);
            }
        }
    }

    public void draw(Canvas canvas, float x, float y, float contentW, float alpha, float viewportTop, float viewportBottom, float mouseX, float mouseY) {
        ModuleKeybindManager.registerModule(this);
        drawStaticContent(canvas, x, y, contentW, alpha, viewportTop, viewportBottom, expandProgress);
        // 紧凑模式的表头说明走悬停浮层；子项稍后登记，鼠标在子项上时后者覆盖前者（同一帧只留最后一条）
        if (compactLayout() && mouseY >= y && mouseY <= y + moduleHeight() - 8f
                && mouseX >= x && mouseX <= x + contentW) {
            TooltipLayer.show(subtitle, mouseX, mouseY);
        }
        if (mainWidget != null || isKeybindable()) {
            float widgetWidth = mainWidget == null ? 0f : mainWidget.getWidth();
            float widgetHeight = mainWidget == null ? KEYBIND_H : mainWidget.getHeight();
            float wx = x + contentW - PAD_X - widgetWidth;
            float wy = y + (moduleHeight() - 8f - widgetHeight) / 2f;
            drawKeybind(canvas, wx, wy, alpha, mouseX, mouseY);
            if (mainWidget != null) {
                mainWidget.hover(mouseX, mouseY, wx, wy, widgetWidth);
                mainWidget.draw(canvas, wx, wy, alpha);
            }
        }
        if (expandProgress <= 0.01f) return;
        if (compactLayout()) {
            drawCompactCells(canvas, x, y, contentW, alpha, viewportTop, viewportBottom, mouseX, mouseY);
            return;
        }
        float sy = y + MODULE_H;
        for (SubEntry sub : subEntries) {
            if (!sub.isVisible()) continue;
            float subBottom = sy + SUB_H - 6f;
            if (subBottom > viewportTop && sy < viewportBottom && sub.widget != null) {
                float wx = x + contentW - PAD_X - sub.widget.getWidth();
                float wy = sy + (SUB_H - 6f - sub.widget.getHeight()) / 2f;
                sub.widget.hover(mouseX, mouseY, wx, wy, sub.widget.getWidth());
                sub.widget.draw(canvas, wx, wy, alpha * expandProgress);
            }
            sy += SUB_H;
            if (sub.group && sub.childProgress > 0.01f) {
                float childAlpha = alpha * expandProgress * sub.childProgress;
                for (SubEntry child : sub.children) {
                    if (!child.isVisible()) continue;
                    float childBottom = sy + SUB_H - 6f;
                    if (childBottom > viewportTop && sy < viewportBottom && child.widget != null) {
                        float wx = x + contentW - PAD_X - child.widget.getWidth();
                        float wy = sy + (SUB_H - 6f - child.widget.getHeight()) / 2f;
                        child.widget.hover(mouseX, mouseY, wx, wy, child.widget.getWidth());
                        child.widget.draw(canvas, wx, wy, childAlpha);
                    }
                    sy += SUB_H;
                }
            }
        }
    }

    /** 紧凑双列：控件与标题各画各的，说明文字登记到悬停浮层（宿主帧末统一画） */
    private void drawCompactCells(Canvas canvas, float x, float y, float contentW, float alpha,
                                  float viewportTop, float viewportBottom, float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        for (Cell cell : compactCells()) {
            SubEntry sub = subEntries.get(cell.index());
            float cx = compactCellX(x, contentW, cell);
            float cw = compactCellW(contentW, cell);
            float cy = compactCellY(y, cell);
            float ch = subHeight() - 6f;
            // 视口外整格跳过：两百多项的列表里，这一步决定了滚动是否顺滑
            if (cy + ch < viewportTop || cy > viewportBottom) continue;

            float subAlpha = alpha * expandProgress;
            GlassPanel.frost(canvas, cx, cy, cw, ch, 10f, tc.subModule, 0.55f,
                    ClickGuiThemeColors.panelBackgroundAlpha(subAlpha));
            GlassPanel.rim(canvas, cx, cy, cw, ch, 10f, tc.rim, subAlpha, 0.05f);

            // 分组格只画标题与展开箭头（本项目紧凑模式下不会出现分组，保留以免将来踩空）
            if (sub.group) {
                FontRenderer.drawText(canvas, sub.title, cx + PAD_X, cy + ch / 2f + 4.5f, 13f,
                        withAlpha(tc.subModuleText, subAlpha));
                String arrow = sub.childProgress > 0.5f ? ARROW_EXPANDED : ARROW_COLLAPSED;
                float aw = FontRenderer.measureTextWidth(arrow, 12f, FontRenderer.MATERIAL_SYMBOLS);
                FontRenderer.drawText(canvas, arrow, cx + cw - 13f - aw, cy + ch / 2f + 5.5f, 12f,
                        withAlpha(tc.mutedText, subAlpha), FontRenderer.MATERIAL_SYMBOLS);
                continue;
            }

            float widgetW = sub.widget.getWidth();
            Box box = compactWidgetBox(cx, cw, cy, sub.widget);
            float widgetX = box.x();
            // 标题可用宽度与装箱判定同一公式（放不下的项已经独占整行了，这里的截断只是兜底）
            float labelWidth = Math.max(40f, widgetX - (cx + PAD_X) - LABEL_GAP);
            String label = CardLayout.ellipsize(sub.title, labelWidth, SUB_LABEL_SIZE);
            FontRenderer.drawText(canvas, label, cx + PAD_X, cy + ch / 2f + 4.5f, SUB_LABEL_SIZE,
                    withAlpha(tc.subModuleText, subAlpha));

            if (mouseX >= cx && mouseX <= cx + cw && mouseY >= cy && mouseY <= cy + ch) {
                // 名称可能被截断，浮层里一并给出完整名称，避免「只看到前半截猜不出来」
                TooltipLayer.show(sub.title + "\n§7" + sub.subtitle, mouseX, mouseY);
            }
            sub.widget.hover(mouseX, mouseY, widgetX, box.y(), widgetW);
            sub.widget.draw(canvas, widgetX, box.y(), subAlpha);
        }
    }

    private void drawKeybind(Canvas canvas, float widgetX, float widgetY, float alpha, float mouseX, float mouseY) {
        String id = keybindId();
        if (!isKeybindable() || id.isBlank()) return;
        float x = widgetX - 8f - keybindWidth;
        float y = widgetY + 1.5f;
        boolean hovered = mouseX >= x && mouseX <= x + keybindWidth && mouseY >= y && mouseY <= y + KEYBIND_H;
        boolean bound = ModuleKeybindManager.hasBinding(id);
        boolean capturing = ModuleKeybindManager.isCapturing(id);
        keybindHover += ((hovered ? 1f : 0f) - keybindHover) * 0.2f;
        keybindRed += ((bound && hovered && !capturing && !ModuleKeybindManager.ACTION_CLICK_GUI.equals(id) ? 1f : 0f) - keybindRed) * 0.2f;
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        int baseGray = tc.keybindBackground;
        int hoverGray = tc.keybindHoverBackground;
        int unbindRed = tc.keybindUnbindBackground;
        int hoverColor = lerpColor(baseGray, hoverGray, keybindHover);
        float buttonAlpha = alpha * (0.34f + keybindHover * 0.18f + keybindRed * 0.32f);
        keybindPaint.setColor(withAlpha(lerpColor(hoverColor, unbindRed, keybindRed), buttonAlpha));
        canvas.drawRRect(RRect.makeXYWH(x, y, keybindWidth, KEYBIND_H, 5f), keybindPaint);
        if (capturing) {
            String text = UiText.t("按下任意键...", "Press any key...");
            drawCenteredText(canvas, text, x, y, keybindWidth, 9f, alpha);
        } else if (bound && keybindRed > 0.12f) {
            drawCenteredIcon(canvas, CLEAR_ICON, x, y, keybindWidth, alpha);
        } else if (bound) {
            drawCenteredText(canvas, ModuleKeybindManager.keyName(id), x, y, keybindWidth, 9f, alpha);
        } else {
            drawCenteredIcon(canvas, KEYBIND_ICON, x, y, keybindWidth, alpha);
        }
    }

    private float targetKeybindWidth() {
        String id = keybindId();
        if (!isKeybindable() || id.isBlank()) return KEYBIND_BASE_W;
        if (ModuleKeybindManager.isCapturing(id)) {
            return Math.max(110f, FontRenderer.measureTextWidth(UiText.t("按下任意键...", "Press any key..."), 9f) + 20f);
        }
        String keyName = ModuleKeybindManager.keyName(id);
        return keyName.isBlank() ? KEYBIND_BASE_W : Math.max(KEYBIND_BASE_W, FontRenderer.measureTextWidth(keyName, 9f) + 12f);
    }

    private void drawCenteredText(Canvas canvas, String text, float x, float y, float width, float size, float alpha) {
        float textW = FontRenderer.measureTextWidth(text, size);
        FontRenderer.drawText(canvas, text, x + (width - textW) / 2f, y + KEYBIND_H / 2f + 3.5f, size, withAlpha(0xFFFFFF, alpha));
    }

    private void drawCenteredIcon(Canvas canvas, String icon, float x, float y, float width, float alpha) {
        float iconW = FontRenderer.measureTextWidth(icon, 12f, FontRenderer.MATERIAL_SYMBOLS);
        FontRenderer.drawText(canvas, icon, x + (width - iconW) / 2f, y + KEYBIND_H / 2f + 6.2f, 12f, withAlpha(0xFFFFFF, alpha), FontRenderer.MATERIAL_SYMBOLS);
    }

    private void drawStaticContent(Canvas canvas, float x, float y, float contentW, float alpha, float viewportTop, float viewportBottom, float progress) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
        float headH = moduleHeight();
        GlassPanel.frost(canvas, x, y, contentW, headH - 8f, 16f, tc.module, 0.70f, rowAlpha);
        GlassPanel.rim(canvas, x, y, contentW, headH - 8f, 16f, tc.rim, rowAlpha, 0.10f);
        if (compactLayout()) {
            // 单行表头：两行表头在两百多项的页面上太费地方；说明改由悬停浮层给出，
            // 名称按可用宽度截断（右侧依次是小标与展开箭头）
            float badgeW = 0f;
            if (badge != null && !badge.isEmpty()) {
                badgeW = FontRenderer.measureTextWidth(badge, 11f);
                FontRenderer.drawText(canvas, badge, x + contentW - 18f - badgeW,
                        y + (headH - 8f) / 2f + 4.5f, 11f, withAlpha(tc.labelTertiary, alpha));
            }
            String label = CardLayout.ellipsize(title, contentW - PAD_X * 2f - 24f - badgeW, 14f);
            FontRenderer.drawTextBold(canvas, label, x + PAD_X, y + (headH - 8f) / 2f + 5f, 14f,
                    withAlpha(tc.primaryText, alpha));
        } else {
            FontRenderer.drawTextBold(canvas, title, x + PAD_X, y + 24f, 14f, withAlpha(tc.primaryText, alpha));
            FontRenderer.drawText(canvas, subtitle, x + PAD_X, y + 43f, 11f, withAlpha(tc.labelTertiary, alpha));
        }
        // 紧凑模式的子项底板由 drawCompactCells 画（它同时还要画控件与标题），这里只画经典模式的
        if (progress > 0.01f && !compactLayout()) {
            float sy = y + MODULE_H;
            for (SubEntry sub : subEntries) {
                if (!sub.isVisible()) continue;
                float subBottom = sy + SUB_H - 6f;
                if (subBottom > viewportTop && sy < viewportBottom) {
                    float subAlpha = alpha * progress;
                    GlassPanel.frost(canvas, x + 8f, sy, contentW - 8f, SUB_H - 6f, 12f, tc.subModule, 0.55f,
                            ClickGuiThemeColors.panelBackgroundAlpha(subAlpha));
                    GlassPanel.rim(canvas, x + 8f, sy, contentW - 8f, SUB_H - 6f, 12f, tc.rim, subAlpha, 0.05f);
                    if (sub.subtitle == null || sub.subtitle.isEmpty()) {
                        FontRenderer.drawText(canvas, sub.title, x + PAD_X + 8f, sy + (SUB_H - 6f) / 2f + 4.5f, 13f, withAlpha(tc.subModuleText, subAlpha));
                    } else {
                        FontRenderer.drawText(canvas, sub.title, x + PAD_X + 8f, sy + 16f, 13f, withAlpha(tc.subModuleText, subAlpha));
                        FontRenderer.drawText(canvas, sub.subtitle, x + PAD_X + 8f, sy + 30f, 11f, withAlpha(tc.labelTertiary, subAlpha));
                    }
                    if (sub.group && sub.hasVisibleChildren()) {
                        String arrow = sub.childProgress > 0.5f ? ARROW_EXPANDED : ARROW_COLLAPSED;
                        float aw = FontRenderer.measureTextWidth(arrow, 12f, FontRenderer.MATERIAL_SYMBOLS);
                        FontRenderer.drawText(canvas, arrow, x + contentW - 13f - aw, sy + (SUB_H - 6f) / 2f + 5.5f, 12f, withAlpha(tc.mutedText, subAlpha), FontRenderer.MATERIAL_SYMBOLS);
                    }
                }
                sy += SUB_H;
                if (sub.group && sub.childProgress > 0.01f) {
                    float subAlpha = alpha * progress * sub.childProgress;
                    for (SubEntry child : sub.children) {
                        if (!child.isVisible()) continue;
                        float childBottom = sy + SUB_H - 6f;
                        if (childBottom > viewportTop && sy < viewportBottom) {
                            GlassPanel.frost(canvas, x + 16f, sy, contentW - 16f, SUB_H - 6f, 12f, tc.subModule, 0.55f,
                                    ClickGuiThemeColors.panelBackgroundAlpha(subAlpha));
                            GlassPanel.rim(canvas, x + 16f, sy, contentW - 16f, SUB_H - 6f, 12f, tc.rim, subAlpha, 0.05f);
                            if (child.subtitle == null || child.subtitle.isEmpty()) {
                                 FontRenderer.drawText(canvas, child.title, x + PAD_X + 16f, sy + (SUB_H - 6f) / 2f + 4.5f, 13f, withAlpha(tc.subModuleText, subAlpha));
                            } else {
                                FontRenderer.drawText(canvas, child.title, x + PAD_X + 16f, sy + 16f, 13f, withAlpha(tc.subModuleText, subAlpha));
                                FontRenderer.drawText(canvas, child.subtitle, x + PAD_X + 16f, sy + 30f, 11f, withAlpha(tc.labelTertiary, subAlpha));
                            }
                        }
                        sy += SUB_H;
                    }
                }
            }
        }
        if (hasVisibleSubEntries()) {
            String arrow = progress > 0.5f ? ARROW_EXPANDED : ARROW_COLLAPSED;
            float aw = FontRenderer.measureTextWidth(arrow, 12f, FontRenderer.MATERIAL_SYMBOLS);
            FontRenderer.drawText(canvas, arrow, x + contentW - 5f - aw, y + (headH - 8f) / 2f + 5.5f, 12f, withAlpha(tc.mutedText, alpha), FontRenderer.MATERIAL_SYMBOLS);
        }
    }

    /**
     * 紧凑模式下某个子项控件的摆放框。
     *
     * <p>绘制与命中读同一份几何——紧凑模式最典型的 bug 就是「看到的」与「点到的」错开一行，
     * 所以这里只允许有一个算法。</p>
     */
    private static Box compactWidgetBox(float cx, float cw, float cy, SettingWidget widget) {
        float w = widget.getWidth();
        return new Box(cx + cw - PAD_X - w, cy + (COMPACT_SUB_H - 6f - widget.getHeight()) / 2f, w);
    }

    /** 控件摆放框：左上角 + 宽度（高度由控件自报）。 */
    private record Box(float x, float y, float width) {
    }

    public boolean onClick(float mx, float my, float x, float y, float contentW, int button) {
        float moduleBottom = y + moduleHeight() - 8f;
        if (my >= y && my <= moduleBottom) {
            // 右键展开；没有主控件也没有快捷键的「纯标题分组」左键同样可以展开，
            // 否则用户左键点标题毫无反应（例如 Baritone设置 的七个分组）。
            boolean headerOnly = mainWidget == null && !isKeybindable();
            if ((button == 1 || (button == 0 && headerOnly)) && hasVisibleSubEntries()) {
                expanded = !expanded;
                return true;
            }
            String id = keybindId();
            if (button == 0 && isKeybindable() && !id.isBlank()) {
                float widgetX = x + contentW - PAD_X - (mainWidget == null ? 0f : mainWidget.getWidth());
                float keybindX = widgetX - 8f - keybindWidth;
                float keybindY = y + (moduleHeight() - 8f - KEYBIND_H) / 2f + 1.5f;
                if (mx >= keybindX && mx <= keybindX + keybindWidth && my >= keybindY && my <= keybindY + KEYBIND_H) {
                    if (ModuleKeybindManager.hasBinding(id) && !ModuleKeybindManager.ACTION_CLICK_GUI.equals(id)) {
                        ModuleKeybindManager.clearBinding(id);
                    } else {
                        ModuleKeybindManager.beginCapture(id);
                    }
                    return true;
                }
            }
            if (button == 0 && mainWidget != null) {
                float wx = x + contentW - PAD_X - mainWidget.getWidth();
                float wy = y + (moduleHeight() - 8f - mainWidget.getHeight()) / 2f;
                return mainWidget.onClick(mx, my, wx, wy, button);
            }
        }
        if (expanded && expandProgress > 0.5f) {
            if (compactLayout()) {
                for (Cell cell : compactCells()) {
                    SubEntry sub = subEntries.get(cell.index());
                    if (sub.group || sub.widget == null) continue;
                    float cx = compactCellX(x, contentW, cell);
                    float cw = compactCellW(contentW, cell);
                    float cy = compactCellY(y, cell);
                    if (my < cy || my > cy + subHeight() - 6f) continue;
                    if (mx < cx || mx > cx + cw) continue;
                    Box box = compactWidgetBox(cx, cw, cy, sub.widget);
                    return sub.widget.onClick(mx, my, box.x(), box.y(), button);
                }
                return false;
            }
            float sy = y + MODULE_H;
            for (SubEntry sub : subEntries) {
                if (!sub.isVisible()) continue;
                float subBottom = sy + SUB_H - 6f;
                if (my >= sy && my <= subBottom) {
                    if (sub.group) {
                        sub.expanded = !sub.expanded;
                        return true;
                    }
                    if (sub.widget != null) {
                        float wx = x + contentW - PAD_X - sub.widget.getWidth();
                        float wy = sy + (SUB_H - 6f - sub.widget.getHeight()) / 2f;
                        return sub.widget.onClick(mx, my, wx, wy, button);
                    }
                }
                sy += SUB_H;
                if (sub.group && sub.childProgress > 0.01f) {
                    for (SubEntry child : sub.children) {
                        if (!child.isVisible()) continue;
                        float childBottom = sy + SUB_H - 6f;
                        if (my >= sy && my <= childBottom && child.widget != null) {
                            float wx = x + contentW - PAD_X - child.widget.getWidth();
                            float wy = sy + (SUB_H - 6f - child.widget.getHeight()) / 2f;
                            return child.widget.onClick(mx, my, wx, wy, button);
                        }
                        sy += SUB_H;
                    }
                }
            }
        }
        return false;
    }

    public boolean onDrag(float mx, float my, float x, float y, float contentW) {
        if (mainWidget instanceof SettingTextBox textBox) {
            float wx = x + contentW - PAD_X - textBox.getWidth();
            float wy = y + (moduleHeight() - 8f - textBox.getHeight()) / 2f;
            if (textBox.onDrag(mx, my, wx, wy)) return true;
        }
        if (expanded) {
            if (compactLayout()) {
                // 文本框在紧凑模式下是宽项（独占整行），几何与绘制同源
                for (Cell cell : compactCells()) {
                    SubEntry sub = subEntries.get(cell.index());
                    if (!(sub.widget instanceof SettingTextBox textBox)) continue;
                    float cx = compactCellX(x, contentW, cell);
                    float cw = compactCellW(contentW, cell);
                    float cy = compactCellY(y, cell);
                    Box box = compactWidgetBox(cx, cw, cy, textBox);
                    if (textBox.onDrag(mx, my, box.x(), box.y())) return true;
                }
                return false;
            }
            float sy = y + MODULE_H;
            for (SubEntry sub : subEntries) {
                if (!sub.isVisible()) continue;
                if (sub.widget instanceof SettingTextBox textBox) {
                    float wx = x + contentW - PAD_X - textBox.getWidth();
                    float wy = sy + (SUB_H - 6f - textBox.getHeight()) / 2f;
                    if (textBox.onDrag(mx, my, wx, wy)) return true;
                }
                sy += SUB_H;
                if (sub.group && sub.childProgress > 0.01f) {
                    for (SubEntry child : sub.children) {
                        if (!child.isVisible()) continue;
                        if (child.widget instanceof SettingTextBox textBox) {
                            float wx = x + contentW - PAD_X - textBox.getWidth();
                            float wy = sy + (SUB_H - 6f - textBox.getHeight()) / 2f;
                            if (textBox.onDrag(mx, my, wx, wy)) return true;
                        }
                        sy += SUB_H;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 释放拖动状态。
     *
     * <p>本项目控件没有需要显式收尾的拖动会话（文本框拖动即时跟随光标、松手即结束），
     * 与 {@code CompactElement.releaseDrag} 的默认行为一致，此处只保证宿主链路完整。</p>
     */
    public void releaseDrag() {
    }

    private float visibleSubHeight() {
        if (compactLayout()) return compactRowCount() * subHeight();
        float height = 0f;
        for (SubEntry sub : subEntries) {
            if (!sub.isVisible()) continue;
            height += SUB_H;
            if (sub.group && sub.childProgress > 0.001f) {
                height += sub.childProgress * sub.visibleChildCount() * SUB_H;
            }
        }
        return height;
    }

    private boolean hasVisibleSubEntries() {
        for (SubEntry sub : subEntries) if (sub.isVisible()) return true;
        return false;
    }

    private static int withAlpha(int color, float alpha) {
        return ((int) (alpha * 255) << 24) | (color & 0x00FFFFFF);
    }

    private static int lerpColor(int a, int b, float t) {
        t = Math.max(0f, Math.min(1f, t));
        int ar = (a >> 16) & 0xFF, ag = (a >> 8) & 0xFF, ab = a & 0xFF;
        int br = (b >> 16) & 0xFF, bg = (b >> 8) & 0xFF, bb = b & 0xFF;
        return ((int) (ar + (br - ar) * t) << 16) | ((int) (ag + (bg - ag) * t) << 8) | (int) (ab + (bb - ab) * t);
    }

    private final class SubEntry {
        private final String title;
        private final String subtitle;
        private final SettingWidget widget;
        private final BooleanSupplier visibleSupplier;
        private final boolean group;
        private final List<SubEntry> children = new ArrayList<>();
        private boolean expanded;
        private float childProgress;

        private SubEntry(String title, String subtitle, SettingWidget widget, BooleanSupplier visibleSupplier, boolean group) {
            this.title = title;
            this.subtitle = subtitle;
            this.widget = widget;
            this.visibleSupplier = visibleSupplier;
            this.group = group;
        }

        private boolean isVisible() {
            return visibleSupplier.getAsBoolean();
        }

        private boolean hasVisibleChildren() {
            for (SubEntry child : children) if (child.isVisible()) return true;
            return false;
        }

        private int visibleChildCount() {
            int count = 0;
            for (SubEntry child : children) if (child.isVisible()) count++;
            return count;
        }
    }
}
