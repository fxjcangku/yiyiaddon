package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.ModuleRow;
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
    /**
     * 图标行的表头行高：直接取模块中心的模块行（{@link ModuleRow#HEIGHT}），行距由页面给
     * （{@code BasePage.moduleGap()} 读的也是 {@link ModuleRow#ROW_GAP}）。
     *
     * <p>用户 2026-09-16 看过「设置」「界面」两页的截图后说「这些也要啊」——两页的行要和模块中心
     * 长成一样：行首一个 Material 图标、行高收到模块行那一档。行高是共享的值：总高度、命中判定与
     * 绘制都按它算，写两份就是「点得到看不见、看得见点不到」。</p>
     */
    private static final float ICON_ROW_H = ModuleRow.HEIGHT;
    /**
     * 图标行的子项行高。
     *
     * <p><b>为什么比经典的 {@link #SUB_H}（44）矮：</b>一级行只有 24，二级还留 44 + 圆角底板，
     * 实机看起来「子项的框比分组标题还大」（用户 2026-09-18 对 ESP 全局设置的截图反馈）。
     * 收到 36（底板 30）之后层级关系恢复正常，两行文字（标题 + 说明）仍放得下。</p>
     */
    private static final float ICON_ROW_SUB_H = 36f;
    /**
     * 图标行的标题 / 说明字号。
     *
     * <p>就是这两页原来的 14 / 11——收行高时<b>一个都没降</b>：说明从第二行挪到标题右侧同一行
     * （模块行也是「名称 + 说明」一行），放不下的部分照旧省略，完整原文由悬停浮层给出。</p>
     */
    private static final float ICON_ROW_TITLE_SIZE = 14f;
    private static final float ICON_ROW_SUB_SIZE = 11f;
    /** 展开箭头字形：图标行与经典表头共用（文字让位时也按它算宽度）。 */
    private static final float EXPAND_ARROW_SIZE = 12f;
    /** 展开箭头距行右边界的距离（经典表头的原口径，图标行不改右基线）。 */
    private static final float EXPAND_ARROW_INSET = 5f;
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
    /**
     * 紧凑双列里格内左右内缩。
     *
     * <p><b>比经典的 {@link #PAD_X}（20）小：</b>20 是经典大卡片的呼吸位，而双列的格子只有 241 宽，
     * 左右各留 20 就吃掉 40——标题可用宽度被压到 93，于是「区块边界获取上限」这种 8 字标题（实测 96）
     * 正好差 3 个单位放不下，被 {@link #spansWholeRow} 判成「必须独占整行」。实机看到的就是
     * 「排版乱七八糟」：这些 8 字标题的项目一条一条单独占满整行、标题与控件之间空出一大片，
     * 而 7 字标题的项目照旧两列——一页里混着三种行型（用户 2026-09-16 截图反馈）。</p>
     *
     * <p>收到 12 后标题可用宽度 109（放得下 9 个全角字），量级相符的项都落回半格，整页回到
     * 整齐的双列；只剩真正宽的控件（文本框 220 / 只读值 320 / 三维偏移）才独占整行。</p>
     */
    private static final float COMPACT_PAD_X = 12f;
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
    /**
     * 行首图标码点（Material 符号）；非 null 时表头走「图标 + 标题 + 说明 + 右侧控件」的紧凑单行。
     *
     * <p>见 {@link #icon(String)}。</p>
     */
    private String icon;

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

    /**
     * 行首图标：表头压成模块中心同款的紧凑单行（{@link #ICON_ROW_H}，24）。
     *
     * <p><b>给谁用：</b>「设置」「界面」这种「一行一项」的清单页——一行里放下「图标 + 标题 + 说明 +
     * 右侧控件」，和模块中心的模块行一个观感（用户 2026-09-16 看过这两页截图后要求「这些也要啊」）。
     * 子项层（展开后的卡片）沿用原样，本轮只对齐一级行。</p>
     *
     * <p><b>字号不降：</b>标题仍是 14、说明仍是 11，说明只是从第二行挪到标题右侧；说明放不下时照旧
     * 省略，完整原文悬停可见（同 {@link #compact()} 的做法，宿主屏幕需支持浮层）。</p>
     *
     * <p><b>码点必须已验证：</b>字形直接进字体绘制，字体里没有就是一块豆腐——码点只能取自项目已验真
     * 的集合（开发习惯第 140 条），详见各页面图标常量的注释。</p>
     *
     * @param glyph Material 符号码点；{@code null} / 空串表示不画图标，表头回到经典布局
     */
    public SettingModule icon(String glyph) {
        icon = glyph == null || glyph.isEmpty() ? null : glyph;
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

    /**
     * 表头是否走图标行。
     *
     * <p><b>与 {@link #compact()} 可以同时开</b>（用户 2026-09-18：「baritone 界面点进去改成 esp设置这样」）：
     * 图标管一级行（24 高、图标 + 标题 + 说明一行），{@code compact} 只管二级子项的排布（单行 + 双列）。
     * 两者原本互斥只是因为子项高度口径不同（30 / 44），现在各管一段，混在一起不再错位。</p>
     */
    private boolean iconRow() {
        return icon != null;
    }

    /** 模块占的槽高：图标行 24 / 紧凑双列 40 / 经典 56。总高度与页面排序都读它。 */
    private float moduleHeight() {
        if (iconRow()) return ICON_ROW_H;
        return compactLayout() ? COMPACT_MODULE_H : MODULE_H;
    }

    /**
     * 表头（可点的标题那一块）的绘制高度。
     *
     * <p>经典 / 紧凑双列的老口径是「槽高 - 8」——那 8 是子项卡片与投影的呼吸位；图标行的 24 就是行本身，
     * 行距由页面给（6，同模块中心），不再留这 8。绘制、控件居中与命中判定都读这一个口径。</p>
     */
    private float headerHeight() {
        return iconRow() ? ICON_ROW_H : moduleHeight() - 8f;
    }

    private float subHeight() {
        if (compactLayout()) return COMPACT_SUB_H;
        return iconRow() ? ICON_ROW_SUB_H : SUB_H;
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
        return COMPACT_CELL_W - COMPACT_PAD_X * 2f - widgetW - LABEL_GAP;
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
        drawStaticContent(canvas, x, y, contentW, alpha, viewportTop, viewportBottom, expandProgress, mouseX, mouseY);
        // 表头说明被截断时给完整原文：紧凑模式的单行表头与图标行都只画一行，长句必然省略
        if ((compactLayout() || iconRow()) && mouseY >= y && mouseY <= y + headerHeight()
                && mouseX >= x && mouseX <= x + contentW) {
            TooltipLayer.show(subtitle, mouseX, mouseY);
        }
        if (mainWidget != null || isKeybindable()) {
            float widgetWidth = mainWidget == null ? 0f : mainWidget.getWidth();
            float widgetHeight = mainWidget == null ? KEYBIND_H : mainWidget.getHeight();
            float wx = x + contentW - PAD_X - widgetWidth;
            float wy = y + (headerHeight() - widgetHeight) / 2f;
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
        float sy = y + moduleHeight();
        float subH = subHeight();
        float boxH = subH - 6f;
        for (SubEntry sub : subEntries) {
            if (!sub.isVisible()) continue;
            float subBottom = sy + boxH;
            if (subBottom > viewportTop && sy < viewportBottom && sub.widget != null) {
                float wx = x + contentW - PAD_X - sub.widget.getWidth();
                float wy = sy + (boxH - sub.widget.getHeight()) / 2f;
                sub.widget.hover(mouseX, mouseY, wx, wy, sub.widget.getWidth());
                sub.widget.draw(canvas, wx, wy, alpha * expandProgress);
            }
            sy += subH;
            if (sub.group && sub.childProgress > 0.01f) {
                float childAlpha = alpha * expandProgress * sub.childProgress;
                for (SubEntry child : sub.children) {
                    if (!child.isVisible()) continue;
                    float childBottom = sy + boxH;
                    if (childBottom > viewportTop && sy < viewportBottom && child.widget != null) {
                        float wx = x + contentW - PAD_X - child.widget.getWidth();
                        float wy = sy + (boxH - child.widget.getHeight()) / 2f;
                        child.widget.hover(mouseX, mouseY, wx, wy, child.widget.getWidth());
                        child.widget.draw(canvas, wx, wy, childAlpha);
                    }
                    sy += subH;
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
            float labelWidth = Math.max(40f, widgetX - (cx + COMPACT_PAD_X) - LABEL_GAP);
            String label = CardLayout.ellipsize(sub.title, labelWidth, SUB_LABEL_SIZE);
            FontRenderer.drawText(canvas, label, cx + COMPACT_PAD_X, cy + ch / 2f + 4.5f, SUB_LABEL_SIZE,
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
        keybindRed += ((bound && hovered && !capturing ? 1f : 0f) - keybindRed) * 0.2f;
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

    private void drawStaticContent(Canvas canvas, float x, float y, float contentW, float alpha, float viewportTop, float viewportBottom, float progress, float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
        float headH = headerHeight();
        // 图标行是「行本身」：圆角按行高收窄、底色略淡，与模块中心的模块行同一观感；
        // 经典表头沿用 16 的大圆角与 0.70 底
        float radius = iconRow() ? GlassPanel.rowRadius(headH) : 16f;
        GlassPanel.frost(canvas, x, y, contentW, headH, radius, tc.module, iconRow() ? 0.55f : 0.70f, rowAlpha);
        GlassPanel.rim(canvas, x, y, contentW, headH, radius, tc.rim, rowAlpha, iconRow() ? 0.06f : 0.10f);
        if (iconRow()) {
            drawIconHeader(canvas, x, y, contentW, alpha);
        } else if (compactLayout()) {
            // 单行表头：两行表头在两百多项的页面上太费地方；说明改由悬停浮层给出，
            // 名称按可用宽度截断（右侧依次是小标与展开箭头）
            float badgeW = 0f;
            if (badge != null && !badge.isEmpty()) {
                badgeW = FontRenderer.measureTextWidth(badge, 11f);
                FontRenderer.drawText(canvas, badge, x + contentW - 18f - badgeW,
                        y + headH / 2f + 4.5f, 11f, withAlpha(tc.labelTertiary, alpha));
            }
            String label = CardLayout.ellipsize(title, contentW - PAD_X * 2f - 24f - badgeW, 14f);
            FontRenderer.drawTextBold(canvas, label, x + PAD_X, y + headH / 2f + 5f, 14f,
                    withAlpha(tc.primaryText, alpha));
        } else {
            // 经典表头也要与右侧控件分栏：标题 / 说明原来无宽度上限，长文案会一路铺到控件底下
            // （控件在垂向上与两行文字重叠，量到就是「字压在按钮上」）。标题优先整份显示，同图标行。
            float textW = Math.max(24f, rowTextRight(x, contentW) - (x + PAD_X));
            float titleMax = Math.min(FontRenderer.measureTextWidthBold(title, 14f), textW);
            String titleText = CardLayout.ellipsize(title, titleMax, 14f);
            String descText = subtitle == null ? "" : CardLayout.ellipsize(subtitle, textW, 11f);
            FontRenderer.drawTextBold(canvas, titleText, x + PAD_X, y + 24f, 14f, withAlpha(tc.primaryText, alpha));
            FontRenderer.drawText(canvas, descText, x + PAD_X, y + 43f, 11f, withAlpha(tc.labelTertiary, alpha));
            if (mouseX >= x && mouseX <= x + contentW && mouseY >= y && mouseY <= y + headH) {
                String hint = truncatedHint(title, titleText, subtitle, descText);
                if (hint != null) TooltipLayer.show(hint, mouseX, mouseY);
            }
        }
        // 紧凑模式的子项底板由 drawCompactCells 画（它同时还要画控件与标题），这里只画经典模式的
        if (progress > 0.01f && !compactLayout()) {
            float subH = subHeight();
            float boxH = subH - 6f;
            float sy = y + moduleHeight();
            for (SubEntry sub : subEntries) {
                if (!sub.isVisible()) continue;
                float subBottom = sy + boxH;
                if (subBottom > viewportTop && sy < viewportBottom) {
                    float subAlpha = alpha * progress;
                    GlassPanel.frost(canvas, x + 8f, sy, contentW - 8f, boxH, 12f, tc.subModule, 0.55f,
                            ClickGuiThemeColors.panelBackgroundAlpha(subAlpha));
                    GlassPanel.rim(canvas, x + 8f, sy, contentW - 8f, boxH, 12f, tc.rim, subAlpha, 0.05f);
                    drawClassicSubText(canvas, x, sy, contentW, 8f, sub, subAlpha, mouseX, mouseY);
                    if (sub.group && sub.hasVisibleChildren()) {
                        String arrow = sub.childProgress > 0.5f ? ARROW_EXPANDED : ARROW_COLLAPSED;
                        float aw = FontRenderer.measureTextWidth(arrow, 12f, FontRenderer.MATERIAL_SYMBOLS);
                        FontRenderer.drawText(canvas, arrow, x + contentW - 13f - aw, sy + boxH / 2f + 5.5f, 12f, withAlpha(tc.mutedText, subAlpha), FontRenderer.MATERIAL_SYMBOLS);
                    }
                }
                sy += subH;
                if (sub.group && sub.childProgress > 0.01f) {
                    float subAlpha = alpha * progress * sub.childProgress;
                    for (SubEntry child : sub.children) {
                        if (!child.isVisible()) continue;
                        float childBottom = sy + boxH;
                        if (childBottom > viewportTop && sy < viewportBottom) {
                            GlassPanel.frost(canvas, x + 16f, sy, contentW - 16f, boxH, 12f, tc.subModule, 0.55f,
                                    ClickGuiThemeColors.panelBackgroundAlpha(subAlpha));
                            GlassPanel.rim(canvas, x + 16f, sy, contentW - 16f, boxH, 12f, tc.rim, subAlpha, 0.05f);
                            drawClassicSubText(canvas, x, sy, contentW, 16f, child, subAlpha, mouseX, mouseY);
                        }
                        sy += subH;
                    }
                }
            }
        }
        if (hasVisibleSubEntries()) {
            String arrow = progress > 0.5f ? ARROW_EXPANDED : ARROW_COLLAPSED;
            float aw = FontRenderer.measureTextWidth(arrow, EXPAND_ARROW_SIZE, FontRenderer.MATERIAL_SYMBOLS);
            FontRenderer.drawText(canvas, arrow, x + contentW - EXPAND_ARROW_INSET - aw,
                    y + headH / 2f + 5.5f, EXPAND_ARROW_SIZE, withAlpha(tc.mutedText, alpha), FontRenderer.MATERIAL_SYMBOLS);
        }
    }

    /**
     * 图标行的表头：图标 + 标题 + 说明 + 右侧让位，与模块中心的模块行同一套左度量
     * （{@link ModuleRow#PAD_X} / {@link ModuleRow#drawIcon}）。
     *
     * <p><b>为什么标题与说明同一行：</b>行高压到 {@link #ICON_ROW_H}（24）后放不下两行文字，
     * 而字号是这两页原有的 14 / 11，一个都不降（用户要的是「方便阅读」）——于是说明挪到标题右侧，
     * 和模块行的「模块名 + 描述」同一排版；放不下的部分照旧省略，完整原文由悬停浮层给出。</p>
     *
     * <p><b>右侧一字未动：</b>控件仍是 {@code x + contentW - PAD_X - 宽}、按键块仍在控件左侧、
     * 展开箭头仍在最右（{@link #EXPAND_ARROW_INSET}），这里只是把它们的左边界算出来给文字让位。</p>
     *
     * <p><b>为什么不缩进：</b>图标行是页面的一级行，没有「上一行分类」可归属——{@link ModuleRow#INDENT}
     * 表达的是模块行隶属于分类头，照搬到这两页只会让整列凭空内缩。</p>
     */
    private void drawIconHeader(Canvas canvas, float x, float y, float contentW, float alpha) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float centerY = y + ICON_ROW_H / 2f;
        float cursor = ModuleRow.drawIcon(canvas, icon, x + ModuleRow.PAD_X, centerY, alpha, tc);

        float textRight = rowTextRight(x, contentW);
        float available = textRight - cursor;
        if (available <= 0f) return;

        // 标题优先整份显示：14 号比模块行的 12 号宽，按固定比例切栏会把「Baritone设置」截成省略号
        float titleMax = Math.min(FontRenderer.measureTextWidthBold(title, ICON_ROW_TITLE_SIZE), available);
        String titleText = CardLayout.ellipsize(title, titleMax, ICON_ROW_TITLE_SIZE);
        FontRenderer.drawTextBold(canvas, titleText, cursor, CardLayout.baseline(centerY, ICON_ROW_TITLE_SIZE),
                ICON_ROW_TITLE_SIZE, withAlpha(tc.primaryText, alpha));

        if (badge != null && !badge.isEmpty()) {
            // 小标写在让位区里（文本右界的右侧），与展开箭头、右侧控件都不重叠
            FontRenderer.drawText(canvas, badge, textRight + LABEL_GAP,
                    CardLayout.baseline(centerY, ICON_ROW_SUB_SIZE), ICON_ROW_SUB_SIZE,
                    withAlpha(tc.labelTertiary, alpha));
        }

        if (subtitle == null || subtitle.isEmpty()) return;
        float descX = cursor + FontRenderer.measureTextWidthBold(titleText, ICON_ROW_TITLE_SIZE) + LABEL_GAP;
        float descMax = textRight - descX;
        if (descMax < 16f) return;
        FontRenderer.drawText(canvas, CardLayout.ellipsize(subtitle, descMax, ICON_ROW_SUB_SIZE), descX,
                CardLayout.baseline(centerY, ICON_ROW_SUB_SIZE), ICON_ROW_SUB_SIZE,
                withAlpha(tc.labelTertiary, alpha));
    }

    /**
     * 图标行里文字可用的右边界：右侧控件 / 按键块 / 展开箭头里最靠左的那个，再让出 {@link #LABEL_GAP}。
     *
     * <p>控件宽度是动态的（按键块绑上键位后会变宽、数字框绘制期间才报宽度），所以每帧现算，
     * 与 {@link #draw} 摆控件用的是同一组表达式。</p>
     */
    private float rowTextRight(float x, float contentW) {
        float right = x + contentW - PAD_X;
        if (mainWidget != null) right -= mainWidget.getWidth();
        if (isKeybindable() && !keybindId().isBlank()) right -= 8f + keybindWidth;
        if (hasVisibleSubEntries()) {
            right = Math.min(right, x + contentW - EXPAND_ARROW_INSET
                    - FontRenderer.measureTextWidth(ARROW_COLLAPSED, EXPAND_ARROW_SIZE, FontRenderer.MATERIAL_SYMBOLS));
        }
        // 小标（「共 N 项」）也占位：不让标题 / 说明一路铺到它下面
        if (badge != null && !badge.isEmpty()) {
            right -= FontRenderer.measureTextWidth(badge, ICON_ROW_SUB_SIZE) + LABEL_GAP;
        }
        return right - LABEL_GAP;
    }

    /**
     * 经典子项的两行文字（标题 + 灰色说明），必要时截断并登记悬停浮层。
     *
     * <p><b>为什么必须限宽：</b>这两行原来是「从行内左缩进起、不设上限」直接画的，说明稍长就一路铺到
     * 右侧控件底下——实机看到的是「说明文字钻进数字框 / 开关 / 循环框里，一直压到按钮下面」。
     * 现在按右侧控件的左边界截断，被省略的原文交给悬停浮层，口径同 {@link ModuleRow}：
     * <b>省略号必须配全文</b>，不出现「只剩省略号、看不到内容」。</p>
     *
     * <p>宽度每帧现算（控件宽度是动态的），表达式与 {@link #draw} 摆控件用的是同一组；
     * 左边界、基线、字号与改动前逐字一致，本轮只补一个右边界。</p>
     *
     * @param indent 行内缩进：子项 8、子项的子项 16（与底板缩进同源）
     */
    private void drawClassicSubText(Canvas canvas, float x, float sy, float contentW, float indent,
                                    SubEntry sub, float alpha, float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float boxH = subHeight() - 6f;
        float textX = x + indent + PAD_X;
        float textW = classicTextWidth(contentW, sub, indent);
        String titleText = CardLayout.ellipsize(sub.title, textW, 13f);
        String descText = sub.subtitle == null ? "" : CardLayout.ellipsize(sub.subtitle, textW, 11f);
        // 图标行的二级底板只有 30 高，两行文字的基线随之收（原来按 38 高的底板排 16 / 30）
        float titleBaseline = iconRow() ? sy + 13f : sy + 16f;
        float descBaseline = iconRow() ? sy + 26f : sy + 30f;
        if (descText.isEmpty()) {
            FontRenderer.drawText(canvas, titleText, textX, sy + boxH / 2f + 4.5f, 13f,
                    withAlpha(tc.subModuleText, alpha));
        } else {
            FontRenderer.drawText(canvas, titleText, textX, titleBaseline, 13f, withAlpha(tc.subModuleText, alpha));
            FontRenderer.drawText(canvas, descText, textX, descBaseline, 11f, withAlpha(tc.labelTertiary, alpha));
        }
        if (mouseX < x || mouseX > x + contentW || mouseY < sy || mouseY > sy + boxH) return;
        String hint = truncatedHint(sub.title, titleText, sub.subtitle, descText);
        if (hint != null) TooltipLayer.show(hint, mouseX, mouseY);
    }

    /**
     * 经典子项里文字可用的宽度（相对页面内容左边界）：从行内缩进 + {@link #PAD_X} 起，
     * 到右侧控件（或分组展开箭头）的左边再让出 {@link #LABEL_GAP}。
     */
    private static float classicTextWidth(float contentW, SubEntry sub, float indent) {
        float right;
        if (sub.group && sub.hasVisibleChildren()) {
            // 分组行的右端是展开箭头而不是控件：按箭头的左边界让位（表达式同 drawStaticContent 里的箭头）
            right = contentW - EXPAND_ARROW_INSET
                    - FontRenderer.measureTextWidth(ARROW_COLLAPSED, EXPAND_ARROW_SIZE, FontRenderer.MATERIAL_SYMBOLS);
        } else {
            right = contentW - PAD_X;
        }
        if (sub.widget != null) right -= sub.widget.getWidth();
        return Math.max(40f, right - LABEL_GAP - (indent + PAD_X));
    }

    /** 悬停浮层文案：标题或说明被省略时给出全文；两者都完整时返回 {@code null}（没有要补的内容）。 */
    private static String truncatedHint(String title, String shownTitle, String subtitle, String shownDesc) {
        boolean titleCut = title != null && !title.isEmpty() && !title.equals(shownTitle);
        boolean descCut = subtitle != null && !subtitle.isEmpty() && !subtitle.equals(shownDesc);
        if (!titleCut) return descCut ? subtitle : null;
        return descCut ? title + "\n§7" + subtitle : title;
    }

    /**
     * 紧凑模式下某个子项控件的摆放框。
     *
     * <p>绘制与命中读同一份几何——紧凑模式最典型的 bug 就是「看到的」与「点到的」错开一行，
     * 所以这里只允许有一个算法。</p>
     */
    private static Box compactWidgetBox(float cx, float cw, float cy, SettingWidget widget) {
        float w = widget.getWidth();
        return new Box(cx + cw - COMPACT_PAD_X - w, cy + (COMPACT_SUB_H - 6f - widget.getHeight()) / 2f, w);
    }

    /** 控件摆放框：左上角 + 宽度（高度由控件自报）。 */
    private record Box(float x, float y, float width) {
    }

    public boolean onClick(float mx, float my, float x, float y, float contentW, int button) {
        float moduleBottom = y + headerHeight();
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
                float keybindY = y + (headerHeight() - KEYBIND_H) / 2f + 1.5f;
                if (mx >= keybindX && mx <= keybindX + keybindWidth && my >= keybindY && my <= keybindY + KEYBIND_H) {
                    // 已绑定的按一下就是清空（与模块页徽章同一套交互；界面快捷键同样可清，
                    // 判据见 ModuleKeybindManager 的显式清空标记）
                    if (ModuleKeybindManager.hasBinding(id)) {
                        ModuleKeybindManager.clearBinding(id);
                    } else {
                        ModuleKeybindManager.beginCapture(id);
                    }
                    return true;
                }
            }
            if (button == 0 && mainWidget != null) {
                float wx = x + contentW - PAD_X - mainWidget.getWidth();
                float wy = y + (headerHeight() - mainWidget.getHeight()) / 2f;
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
            float sy = y + moduleHeight();
            float subH = subHeight();
            float boxH = subH - 6f;
            for (SubEntry sub : subEntries) {
                if (!sub.isVisible()) continue;
                float subBottom = sy + boxH;
                if (my >= sy && my <= subBottom) {
                    if (sub.group) {
                        sub.expanded = !sub.expanded;
                        return true;
                    }
                    if (sub.widget != null) {
                        float wx = x + contentW - PAD_X - sub.widget.getWidth();
                        float wy = sy + (boxH - sub.widget.getHeight()) / 2f;
                        return sub.widget.onClick(mx, my, wx, wy, button);
                    }
                }
                sy += subH;
                if (sub.group && sub.childProgress > 0.01f) {
                    for (SubEntry child : sub.children) {
                        if (!child.isVisible()) continue;
                        float childBottom = sy + boxH;
                        if (my >= sy && my <= childBottom && child.widget != null) {
                            float wx = x + contentW - PAD_X - child.widget.getWidth();
                            float wy = sy + (boxH - child.widget.getHeight()) / 2f;
                            return child.widget.onClick(mx, my, wx, wy, button);
                        }
                        sy += subH;
                    }
                }
            }
        }
        return false;
    }

    public boolean onDrag(float mx, float my, float x, float y, float contentW) {
        if (mainWidget instanceof SettingTextBox textBox) {
            float wx = x + contentW - PAD_X - textBox.getWidth();
            float wy = y + (headerHeight() - textBox.getHeight()) / 2f;
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
            float sy = y + moduleHeight();
            float subH = subHeight();
            float boxH = subH - 6f;
            for (SubEntry sub : subEntries) {
                if (!sub.isVisible()) continue;
                if (sub.widget instanceof SettingTextBox textBox) {
                    float wx = x + contentW - PAD_X - textBox.getWidth();
                    float wy = sy + (boxH - textBox.getHeight()) / 2f;
                    if (textBox.onDrag(mx, my, wx, wy)) return true;
                }
                sy += subH;
                if (sub.group && sub.childProgress > 0.01f) {
                    for (SubEntry child : sub.children) {
                        if (!child.isVisible()) continue;
                        if (child.widget instanceof SettingTextBox textBox) {
                            float wx = x + contentW - PAD_X - textBox.getWidth();
                            float wy = sy + (boxH - textBox.getHeight()) / 2f;
                            if (textBox.onDrag(mx, my, wx, wy)) return true;
                        }
                        sy += subH;
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
        float subH = subHeight();
        for (SubEntry sub : subEntries) {
            if (!sub.isVisible()) continue;
            height += subH;
            if (sub.group && sub.childProgress > 0.001f) {
                height += sub.childProgress * sub.visibleChildCount() * subH;
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
