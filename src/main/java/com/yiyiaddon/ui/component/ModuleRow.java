package com.yiyiaddon.ui.component;

import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

/**
 * 模块中心清单里的紧凑模块行（{@link #HEIGHT} = 24）。
 *
 * <p>整行左侧缩进一格，表示它隶属于上一行的分类头；一行内放下「图标 + 模块名 + 描述 + 状态 +
 * 进入箭头」，整行可点，滚动时一屏能看十行以上。</p>
 *
 * <p>行高刻意压到 24：模块中心要的是「一屏尽量多、滚动距离尽量短」，因此一行只放一行内容，
 * 不重复卡片那套多行基线排版。</p>
 *
 * <p><b>为什么 36 → 28 → 24 一路往下收</b>：用户 2026-09-16 先反馈「展开的分组之后下面的模块
 * 能不能 做小一点 不要做那么大」（36 → 28：36 的行高配 22 的图标底框，在单列清单里一展开就是
 * 一片笨重的大色块）；看过后又说「不用 现在很满意 整体都缩小一下 方便阅读」，于是在已满意的
 * 28 上再统一收一档。左侧那一整套度量（内边距 / 图标底框 / 圆角 / 字形 / 图标与文字间距）按
 * 同一比例跟着收，但<b>模块名与描述的字号一个都没动</b>（12 / 9.5 是这类单行清单的可读性下限，
 * 压字号换来的紧凑得不偿失——用户要的正是「方便阅读」）。</p>
 *
 * <p>右侧的状态标记与箭头<b>一个数值都没改</b>：要求是「已启用 / 未启用 + 箭头仍然贴右对齐，
 * 与同一页的分类头、其它行同一条右基线」，动任何一个都会让模块行的右基线相对分类头漂移。</p>
 *
 * <p>圆角走 {@link GlassPanel#rowRadius}：按行高收窄、以主题圆角为上限，矮行不会被圆角削成胶囊
 * （控制台、星露谷那些紧凑行也是同一口径）。</p>
 *
 * <p><b>网格单元</b>：模块中心把展开后的模块改成横向并排（用户 2026-09-16「模块展开之后 下面的
 * 模块能不能变成一排一排的」），一格一个模块用 {@link #drawCell}——行高、圆角、底色、描边、悬停
 * 反馈与 {@link #drawEntry} 同一套，只是内容按「一格里放得下什么」重排。见该方法。</p>
 */
public final class ModuleRow {

    /**
     * 行高。模块中心的行网格（绘制 / 命中 / 悬停）按这个值排布。
     *
     * <p>用户 2026-09-16 对 28 版「很满意」，只要求「整体都缩小一下 方便阅读」，故再收一档到 24。
     * 这是<b>模块行与分类头共用</b>的值（{@code ModuleCenterPage.ROW_HEIGHT} 直接读它），改它必须
     * 两处同时生效，否则绘制与命中会错位。</p>
     */
    public static final float HEIGHT = 24f;

    /**
     * 行间距：模块中心的清单（分类头与模块行共用）按它排行。
     *
     * <p>公开出来是因为「设置 / 界面」两页的行也要落进同一个节奏（用户 2026-09-16 看过这两页截图后
     * 说「这些也要啊」）：行高只有 24 的行若仍按全局 {@link CardLayout#GAP_Y}（14）排，整组会松散、
     * 与模块中心断裂。两页的行距直接读这一个值（{@code BasePage.moduleGap()}），不各写一份。</p>
     */
    public static final float ROW_GAP = 6f;

    /** 左侧缩进（相对页面内容左边界），用来表达「属于上一行的分类」。 */
    public static final float INDENT = 14f;

    /**
     * 行内左内边距、图标底框边长、字形字号与图标之后的间距。
     *
     * <p>公开给同样是「图标 + 文字 + 右侧控件」的紧凑行（{@code SettingModule.icon}）用：图标的那一套
     * 度量只留一份，两处各写一组数字，改一处必漏一处。</p>
     */
    public static final float PAD_X = 8f;
    public static final float ICON_BOX = 16f;
    public static final float ICON_GLYPH = 11.5f;
    public static final float ICON_GAP = 6f;
    private static final float ICON_RADIUS = 4f;

    /**
     * 分类头与分组标题共用的字号（模块中心的分类头、模块页里的「服务器资源」这类分组标题）。
     *
     * <p>用户 2026-09-16 点进模块页后说「还有点击进去的时候 模块也要缩小 现在都不对称」——模块页的
     * 分组标题原来自己写 12，比模块中心分类头的 11.5 还大，两页并排看就是两个量级。字号只留这一份：
     * 分类头与分组标题都读它，改一处两处同时生效（同 {@link #HEIGHT} / {@link #ROW_GAP} 的做法）。</p>
     */
    public static final float HEADER_TITLE_SIZE = 11.5f;
    /** 模块名与描述的字号：收窄行高时<b>不动</b>（用户要的是「方便阅读」，宁可该行留白紧一点也不压字号）。 */
    private static final float TITLE_SIZE = 12f;
    private static final float CAPTION_SIZE = 9.5f;
    /** 右侧三兄弟（箭头字形 / 右内缩 / 状态与箭头的间距）刻意保持原值：右基线不许漂。 */
    private static final float ARROW_GLYPH = 13f;
    private static final float ARROW_INSET = 14f;
    private static final float BADGE_GAP = 10f;
    /** 模块名占「名称 + 描述」可用宽度的比例，其余留给描述；两栏固定比例，多行之间名称与描述对齐。 */
    private static final float NAME_RATIO = 0.42f;
    /**
     * 网格单元里画描述所需的最小宽度：再窄只能画一两个字，不如整段留给悬停浮层。
     *
     * <p>网格单元窄，描述基本都吃不到这个宽度（见 {@link #drawCell}），它是「窗口很宽、单元很宽时
     * 顺手把描述也画出来」的下限，不是网格里的常态。</p>
     */
    private static final float DESC_MIN_WIDTH = 24f;
    private static final String ARROW = "\uE5CC";

    private ModuleRow() {
    }

    /**
     * 一行的左侧图标：强调色底框 + 居中字形，返回图标之后的光标位置。
     *
     * <p>模块行、分类入口行与「设置 / 界面」两页的图标行都从这里出——底色、底框、圆角、字形字号、
     * 颜色与间距只有这一份实现（用户要的是「同一套图标」，不是三处各画一个长得像的）。</p>
     *
     * @param icon    图标码点，必须落在项目已验证集合内（开发习惯第 140 条）
     * @param x       图标底框左边界
     * @param centerY 行垂直中心
     * @param alpha   整体不透明度
     * @return 图标之后的文字起始 x
     */
    public static float drawIcon(Canvas canvas, String icon, float x, float centerY,
                                 float alpha, ClickGuiThemeColors tc) {
        GlassPanel.fill(canvas, x, centerY - ICON_BOX / 2f, ICON_BOX, ICON_BOX, ICON_RADIUS,
                tc.accent, alpha * 0.16f);
        CardIcons.drawCentered(canvas, icon, x + ICON_BOX / 2f, centerY, ICON_GLYPH,
                GlassPanel.withAlpha(tc.accent, alpha));
        return x + ICON_BOX + ICON_GAP;
    }

    /** 模块行：图标 / 名称 / 描述 / 启用状态都取自模块注册表（状态色：启用绿、未启用红，第 141 条色槽） */
    public static void draw(Canvas canvas, ModuleEntry entry, float x, float y, float w,
                            float alpha, float hover, ClickGuiThemeColors tc) {
        boolean enabled = entry.enabled();
        drawEntry(canvas, entry.icon(), entry.displayName(), entry.description(),
                enabled ? "已启用" : "未启用", enabled ? tc.stateOn : tc.stateOff,
                x, y, w, alpha, hover, tc);
    }

    /**
     * 通用入口行：自带设置页的分类入口（例如 Baritone设置）复用它，右侧状态传「点击进入」。
     *
     * @param stateText 右侧状态文字；{@code null} 表示不画状态标记
     * @param stateColor 状态语义色（圆点与文字同色）
     */
    public static void drawEntry(Canvas canvas, String icon, String title, String description,
                                 String stateText, int stateColor, float x, float y, float w,
                                 float alpha, float hover, ClickGuiThemeColors tc) {
        float radius = GlassPanel.rowRadius(HEIGHT);
        float rowX = x + INDENT;
        float rowW = Math.max(0f, w - INDENT);
        int background = GlassPanel.mix(tc.module, tc.surfaceHover, hover);

        GlassPanel.frost(canvas, rowX, y, rowW, HEIGHT, radius, background, 0.55f, alpha);
        GlassPanel.rim(canvas, rowX, y, rowW, HEIGHT, radius, tc.rim, alpha, 0.06f + 0.14f * hover);

        float centerY = y + HEIGHT / 2f;
        float cursor = drawIcon(canvas, icon, rowX + PAD_X, centerY, alpha, tc);

        // 右侧：状态标记 + 进入箭头（都右对齐，先算出状态标记的左边界，再定左边的文字区）
        float arrowX = rowX + rowW - ARROW_INSET;
        float badgeRight = arrowX - ARROW_GLYPH - BADGE_GAP;
        float badgeWidth = stateText == null ? 0f : StatusBadge.width(stateText);
        float badgeX = stateText == null ? badgeRight : Math.max(cursor, badgeRight - badgeWidth);
        if (stateText != null) {
            StatusBadge.draw(canvas, badgeX, centerY, stateText, stateColor, alpha);
        }
        CardIcons.drawCentered(canvas, ARROW, arrowX, centerY, ARROW_GLYPH,
                GlassPanel.withAlpha(GlassPanel.mix(tc.labelTertiary, tc.accent, hover), alpha));

        float available = Math.max(0f, badgeX - BADGE_GAP - cursor);
        if (available <= 0f) return;
        float nameMax = available * NAME_RATIO;
        FontRenderer.drawTextBold(canvas, CardLayout.ellipsize(title, nameMax, TITLE_SIZE), cursor,
                CardLayout.baseline(centerY, TITLE_SIZE), TITLE_SIZE,
                GlassPanel.withAlpha(tc.primaryText, alpha));

        float descX = cursor + nameMax + ICON_GAP;
        float descMax = Math.max(0f, badgeX - BADGE_GAP - descX);
        if (descMax < 16f) return;
        FontRenderer.drawText(canvas, CardLayout.ellipsize(description, descMax, CAPTION_SIZE), descX,
                CardLayout.baseline(centerY, CAPTION_SIZE), CAPTION_SIZE,
                GlassPanel.withAlpha(tc.labelTertiary, alpha));
    }

    /**
     * 网格单元：同一分类的模块横向并排时的一格，绘制入口与 {@link #draw} 并列（两者互不影响）。
     *
     * <p><b>为什么另开一个入口</b>：一格里放不下「图标 + 名称 + 描述 + 状态 + 箭头」五件东西。
     * 按模块中心的实际几何（面板 740、页面可用宽 501，减掉一格缩进 14 与两个 6 的间距，三列时每格
     * 约 158），名称（12 号，四个汉字约 48）与状态（约 45）之后只剩十来个像素——再塞箭头或描述就
     * 只能把模块名截成三个字。因此单元里只画「图标 + 名称 + 状态」，名称优先吃满可用宽度，
     * 描述整段交给悬停浮层，箭头不再出现（整格可点、悬停有反馈，可点性由行本身表达）。</p>
     *
     * <p><b>省略号必须配全文</b>：用户要求「描述文字在网格里放不下时一律走悬停浮层」，
     * 即被截掉的部分一律在 {@link TooltipLayer} 里给出全文，绝不出现「只剩省略号、看不到内容」。</p>
     *
     * <p>行高、圆角（{@link GlassPanel#rowRadius}）、底色与描边强度、悬停淡反馈都与 {@link #drawEntry}
     * 同源，网格只是把同一行内容改成一排里的一个格子，不是另一套样式。</p>
     *
     * @param mouseX 本帧指针横坐标（与 {@code x、y、w} 同一坐标系）：既用于判定本格是否被悬停，
     *               也作为浮层锚点
     * @param mouseY 本帧指针纵坐标
     */
    public static void drawCell(Canvas canvas, ModuleEntry entry, float x, float y, float w,
                                float mouseX, float mouseY, float alpha, float hover, ClickGuiThemeColors tc) {
        boolean enabled = entry.enabled();
        float radius = GlassPanel.rowRadius(HEIGHT);
        int background = GlassPanel.mix(tc.module, tc.surfaceHover, hover);

        GlassPanel.frost(canvas, x, y, w, HEIGHT, radius, background, 0.55f, alpha);
        GlassPanel.rim(canvas, x, y, w, HEIGHT, radius, tc.rim, alpha, 0.06f + 0.14f * hover);

        float centerY = y + HEIGHT / 2f;
        float cursor = drawIcon(canvas, entry.icon(), x + PAD_X, centerY, alpha, tc);

        // 状态标记先贴右定下来，名称再吃剩下的：名称必须完整可读，宁可让描述整段去浮层
        String stateText = enabled ? "已启用" : "未启用";
        float badgeWidth = StatusBadge.width(stateText);
        float badgeX = Math.max(cursor, x + w - PAD_X - badgeWidth);
        StatusBadge.draw(canvas, badgeX, centerY, stateText, enabled ? tc.stateOn : tc.stateOff, alpha);

        String name = entry.displayName();
        String description = entry.description();
        float available = Math.max(0f, badgeX - BADGE_GAP - cursor);
        float nameWidth = FontRenderer.measureTextWidthBold(name, TITLE_SIZE);
        boolean nameFits = nameWidth <= available;
        float nameMax = Math.min(nameWidth, available);
        if (nameMax > 0f) {
            FontRenderer.drawTextBold(canvas, CardLayout.ellipsize(name, nameMax, TITLE_SIZE), cursor,
                    CardLayout.baseline(centerY, TITLE_SIZE), TITLE_SIZE,
                    GlassPanel.withAlpha(tc.primaryText, alpha));
        }

        // 描述只吃名称之后的空隙：宽到能整段放下才画，放不下整段交给浮层（不画半截省略号）
        float descX = cursor + nameMax + ICON_GAP;
        float descMax = Math.max(0f, badgeX - BADGE_GAP - descX);
        boolean descFits = descMax >= DESC_MIN_WIDTH
                && FontRenderer.measureTextWidth(description, CAPTION_SIZE) <= descMax;
        if (descFits) {
            FontRenderer.drawText(canvas, description, descX, CardLayout.baseline(centerY, CAPTION_SIZE),
                    CAPTION_SIZE, GlassPanel.withAlpha(tc.labelTertiary, alpha));
        }

        if (mouseX < x || mouseX > x + w || mouseY < y || mouseY > y + HEIGHT) return;
        String tip = tooltipText(name, description, nameFits, descFits);
        if (tip != null) TooltipLayer.show(tip, mouseX, mouseY);
    }

    /** 悬停浮层文案：名称或描述被省略时给出全文；两者都完整时不登记（没有需要补的内容）。 */
    private static String tooltipText(String name, String description, boolean nameFits, boolean descFits) {
        if (nameFits && descFits) return null;
        if (nameFits) return description;
        return description == null || description.isEmpty() ? name : name + "\n" + description;
    }
}
