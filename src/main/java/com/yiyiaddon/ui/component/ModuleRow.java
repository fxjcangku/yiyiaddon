package com.yiyiaddon.ui.component;

import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

/**
 * 模块中心清单里的紧凑模块行（{@link #HEIGHT} = 24）。
 *
 * <p>整行左侧缩进一格（由调用方的几何给出，见 {@link #INDENT}），表示它隶属于上一行的分类头；
 * 一行内放下「图标 + 模块名 + 描述 + 星标 + 状态 + 进入箭头」，整行可点，滚动时一屏能看十行以上。</p>
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
 * <p>右侧的状态标记与箭头<b>同一页所有行共用同一条基线</b>：箭头贴右（{@link #ARROW_INSET}），
 * 状态标记紧贴星标左侧（{@link #stateX}），星标位置只由行宽决定（{@link #starCenterX}）。
 * 2026-09-21 起状态标记从「按文字宽度右对齐」改成「固定左基线」：旧写法文案一变长
 * （「未启用」3 字 → 「无法使用」4 字）整块就往左挪，两行的圆点既不同一条竖线，宽的那一档
 * 还会顶到星标上（用户原话「星露谷钓鱼 跟星露谷农场这里没对齐」）。</p>
 *
 * <p>圆角走 {@link GlassPanel#rowRadius}：按行高收窄、以主题圆角为上限，矮行不会被圆角削成胶囊
 * （控制台、星露谷那些紧凑行也是同一口径）。</p>
 *
 * <p><b>不再横向并排</b>（用户 2026-09-21 定稿）：曾经的网格单元把同一分类的模块并排放，
 * 一格里只剩「图标 + 名称 + 状态」，最长名「自动图书管理员」只能截成「自动图书…」，
 * 用户原话是「帮我设计一套好看能看清全部字没有....的」。现在每个模块独占一整行，
 * 名称按可用宽度量过后整段画出，描述放不下就整段交给悬停浮层——整页不出现省略号。</p>
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

    /** 星标字形（Material Symbols 的 {@code star}，与首页「常用模块」同一枚，已按第 140 条核实字体覆盖）。 */
    private static final String STAR = "\uE838";
    /** 星标字形字号与盒子边长。 */
    private static final float STAR_GLYPH = 13f;
    private static final float STAR_BOX = 15f;

    /**
     * 星标中心距行右边缘的距离：让出「状态标记 + 进入箭头」那一块的宽度。
     *
     * <p><b>为什么是一个固定值而不是「状态标记左边再退一格」</b>：命中判定与绘制各算一次几何，一旦
     * 依赖状态文字宽度，两边只要有一处换文案就会错位（第 169 条：同源几何只留一份算式）。
     * 固定值下星标位置只跟行宽有关，两处都只读这一个常量。</p>
     *
     * <p><b>为什么是 112</b>（2026-09-21 状态标记改左对齐后重算）：星标要给最宽的一档状态文字让位 ——
     * 星标右缘（右 − 104.5）+ 10 留白 = 状态文字左缘（右 − 94.5），再放最宽的「无法使用」（实测 56）
     * 之后右缘停在右 − 38.5，刚好落在箭头（右 − 20.5 ~ 右 − 7.5）左边的留白里，任何一档文案都不压箭头。</p>
     */
    private static final float STAR_RIGHT_INSET = 112f;

    /**
     * 星标按钮的命中半径（正方形半边长）：比字形大一圈，好点。
     *
     * <p>绘制（悬停反馈）与页面点击判定共用它，避免「看着点到了、实际没点到」。</p>
     */
    public static final float STAR_HIT = 9f;

    /** 模块名与描述的字号：收窄行高时<b>不动</b>（用户要的是「方便阅读」，宁可该行留白紧一点也不压字号）。 */
    private static final float TITLE_SIZE = 12f;
    private static final float CAPTION_SIZE = 9.5f;
    /** 右侧三兄弟（箭头字形 / 右内缩 / 状态与箭头的间距）刻意保持原值：右基线不许漂。 */
    private static final float ARROW_GLYPH = 13f;
    private static final float ARROW_INSET = 14f;
    private static final float BADGE_GAP = 10f;
    /**
     * 画描述所需的最小宽度：再窄只能画一两个字，不如整段留给悬停浮层。
     *
     * <p>描述<b>放不下就整段不画</b>（不画半截省略号），有它的宽度就整段画出来。</p>
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

    /**
     * 星标按钮的中心横坐标（绘制与命中共用同一份算式）。
     *
     * @param x 行的最终左边界（缩进已由调用方的几何给出）
     * @param w 行的最终宽度
     */
    public static float starCenterX(float x, float w) {
        // 行极窄时贴着图标放，绝不越过图标与文字区；两处几何都走这一个方法，恒不错位
        float rightAligned = x + w - STAR_RIGHT_INSET;
        float afterIcon = x + PAD_X + ICON_BOX + STAR_BOX;
        return Math.max(afterIcon, rightAligned);
    }

    /** 星标按钮的中心纵坐标（行垂直中心；命中判定与绘制同源）。 */
    public static float starCenterY(float y) {
        return y + HEIGHT / 2f;
    }

    /**
     * 状态标记的左边界：<b>紧贴星标右侧</b>，同一页所有行共用这一个横坐标。
     *
     * <p><b>为什么不按文字宽度从右往左排</b>（用户 2026-09-21：「星露谷钓鱼 跟星露谷农场这里没对齐」）：
     * 旧写法是「箭头左边往回退一个文字宽」，文案一变长整块就往左挪一格 —— 两行的圆点不在同一条竖线上，
     * 宽的那一档（「无法使用」）还会顶到星标上（截图上两个图形叠在一起）。现在这个位置只由星标决定，
     * 而星标只由行宽决定，因此<b>行的状态文字左缘与圆点严格对齐，与文案长短无关</b>。</p>
     *
     * <p>宽度预算 = 箭头左边的留白 − 本位置 ≈ 64，「无法使用」实测 56；宽度不足时调用方不画这段文字
     * （见 {@code drawRow} / {@code drawEntry} 的兜底），绝不会压到箭头。</p>
     *
     * @param x 行的最终左边界
     * @param w 行的最终宽度
     */
    public static float stateX(float x, float w) {
        return starCenterX(x, w) + STAR_BOX / 2f + BADGE_GAP;
    }

    /** 状态标记文字能用的最右边界：箭头字形左边再留一格，超出的文案一律不画（不压箭头）。 */
    public static float stateRightLimit(float x, float w) {
        return x + Math.max(0f, w) - ARROW_INSET - ARROW_GLYPH / 2f - BADGE_GAP;
    }

    /**
     * 通用入口行：自带设置页的分类入口（例如 Baritone设置）复用它，右侧状态传「点击进入」。
     *
     * <p>与 {@link #drawRow} 同一口径：{@code x、w} 就是这一行的最终矩形（左侧缩进由调用方的几何
     * 给出，例如模块中心把「属于分类头」的那一行整体缩进 {@link #INDENT}），本方法不再自行缩进
     * —— 否则绘制比命中框多缩一次，点左边会点空。</p>
     *
     * @param stateText 右侧状态文字；{@code null} 表示不画状态标记
     * @param stateColor 状态语义色（圆点与文字同色）
     */
    public static void drawEntry(Canvas canvas, String icon, String title, String description,
                                 String stateText, int stateColor, float x, float y, float w,
                                 float alpha, float hover, ClickGuiThemeColors tc) {
        float radius = GlassPanel.rowRadius(HEIGHT);
        float rowX = x;
        float rowW = Math.max(0f, w);
        int background = GlassPanel.mix(tc.module, tc.surfaceHover, hover);

        GlassPanel.frost(canvas, rowX, y, rowW, HEIGHT, radius, background, 0.55f, alpha);
        GlassPanel.rim(canvas, rowX, y, rowW, HEIGHT, radius, tc.rim, alpha, 0.06f + 0.14f * hover);

        float centerY = y + HEIGHT / 2f;
        float cursor = drawIcon(canvas, icon, rowX + PAD_X, centerY, alpha, tc);

        // 右侧：状态标记 + 进入箭头（状态标记与模块行共用同一条左基线，箭头贴右）
        float arrowX = rowX + rowW - ARROW_INSET;
        float badgeX = Math.max(cursor, stateX(x, w));
        if (stateText != null && !stateText.isEmpty()
                && badgeX + StatusBadge.width(stateText) <= stateRightLimit(x, w)) {
            StatusBadge.draw(canvas, badgeX, centerY, stateText, stateColor, alpha);
        }
        CardIcons.drawCentered(canvas, ARROW, arrowX, centerY, ARROW_GLYPH,
                GlassPanel.withAlpha(GlassPanel.mix(tc.labelTertiary, tc.accent, hover), alpha));

        float available = Math.max(0f, badgeX - BADGE_GAP - cursor);
        if (available <= 0f) return;
        float titleWidth = FontRenderer.measureTextWidthBold(title, TITLE_SIZE);
        boolean titleFits = titleWidth <= available;
        if (titleFits) {
            FontRenderer.drawTextBold(canvas, title, cursor, CardLayout.baseline(centerY, TITLE_SIZE), TITLE_SIZE,
                    GlassPanel.withAlpha(tc.primaryText, alpha));
        }

        float descX = cursor + (titleFits ? titleWidth : 0f) + ICON_GAP;
        float descMax = Math.max(0f, badgeX - BADGE_GAP - descX);
        boolean descFits = titleFits && descMax >= DESC_MIN_WIDTH && description != null
                && !description.isEmpty()
                && FontRenderer.measureTextWidth(description, CAPTION_SIZE) <= descMax;
        if (descFits) {
            FontRenderer.drawText(canvas, description, descX, CardLayout.baseline(centerY, CAPTION_SIZE),
                    CAPTION_SIZE, GlassPanel.withAlpha(tc.labelTertiary, alpha));
        }
    }

    /**
     * 模块行：整行一条，从左到右依次是「图标 + 模块名 + 描述 + 星标 + 状态 + 进入箭头」。
     *
     * <p><b>名字优先完整、描述放不下就不画，两者都不截断</b>（用户 2026-09-21：
     * 「帮我设计一套好看能看清全部字没有....的」；同日再次确认「记得不可以带.....，宁愿简洁一点中文话术
     * 也不要出现省略号」）：名称先按可用宽度量一次，放得下才整段画出；剩下的宽度才轮到描述，
     * 描述放不下时整段不画、交给悬停浮层。这里<b>不再调 {@code CardLayout#ellipsize}</b>——
     * 宽度不够时宁可这一行只留名字，也不出现半截省略号。
     *
     * <p>因此各模块的 {@code description()} 按「一行放得下的中文短注」来写（见
     * {@code Module} 构造参数与 {@code AddonModules} 的注册清单），放不下的长文留在模块页的
     * 「使用说明」里，不靠截断来塞进清单行。</p>
     *
     * <p>行高、圆角（{@link GlassPanel#rowRadius}）、底色与描边强度、悬停淡反馈与 {@link #drawEntry}
     * 同源；整行可点（点进模块页），星标那一小块是收藏键（点击判定见 {@code ModuleCenterPage#onClick}）。</p>
     *
     * <p><b>{@code x、w} 就是这一行的最终矩形</b>：左侧缩进（{@link #INDENT}）由调用方的几何给出，
     * 绘制与命中读同一份矩形，本方法不再自行缩进。</p>
     *
     * @param mouseX 本帧指针横坐标（与 {@code x、y、w} 同一坐标系）：用于星标悬停反馈与浮层锚点
     * @param mouseY 本帧指针纵坐标
     * @param favorite 是否已收藏（顶部「常用」区）：是则描边换成强调色、星标填成实心
     */
    public static void drawRow(Canvas canvas, ModuleEntry entry, float x, float y, float w,
                               float mouseX, float mouseY, float alpha, float hover, boolean favorite,
                               ClickGuiThemeColors tc) {
        boolean enabled = entry.enabled();
        float radius = GlassPanel.rowRadius(HEIGHT);
        float rowX = x;
        float rowW = Math.max(0f, w);

        GlassPanel.frost(canvas, rowX, y, rowW, HEIGHT, radius,
                GlassPanel.mix(tc.module, tc.surfaceHover, hover), 0.55f, alpha);
        // 收藏的行用强调色描边（不额外占宽度）：在分类里也一眼可辨哪些已经收藏
        GlassPanel.rim(canvas, rowX, y, rowW, HEIGHT, radius, favorite ? tc.accent : tc.rim, alpha,
                favorite ? 0.30f + 0.22f * hover : 0.06f + 0.14f * hover);

        float centerY = y + HEIGHT / 2f;
        float cursor = drawIcon(canvas, entry.icon(), rowX + PAD_X, centerY, alpha, tc);

        // 右侧三件从右往左定：进入箭头 → 状态标记 → 星标（星标位置只由行宽决定，见 starCenterX）
        float arrowX = rowX + rowW - ARROW_INSET;
        CardIcons.drawCentered(canvas, ARROW, arrowX, centerY, ARROW_GLYPH,
                GlassPanel.withAlpha(GlassPanel.mix(tc.labelTertiary, tc.accent, hover), alpha));

        // 状态标记贴着星标左对齐：两行的圆点因此落在同一条竖线上（见 stateX 的注释）。
        // 文案口径「已启用 / 未启动」（用户 2026-09-21：「星露谷钓鱼 跟星露谷农场这里没对齐 统改成未启动」）
        String stateText = entry.statusText() == null ? (enabled ? "已启用" : "未启动") : entry.statusText();
        float badgeX = Math.max(cursor, stateX(x, w));
        if (badgeX + StatusBadge.width(stateText) <= stateRightLimit(x, w)) {
            StatusBadge.draw(canvas, badgeX, centerY, stateText, enabled ? tc.stateOn : tc.stateOff, alpha);
        }

        float starX = starCenterX(x, w);
        drawStar(canvas, starX, centerY, favorite, mouseX, mouseY, alpha, tc);

        // 星标左边才是文字区：名称必须完整可读，描述只吃名称之后的空隙
        String name = entry.displayName();
        String description = entry.description();
        float textRight = starX - STAR_BOX / 2f - BADGE_GAP;
        float available = Math.max(0f, textRight - cursor);
        float nameWidth = FontRenderer.measureTextWidthBold(name, TITLE_SIZE);
        boolean nameFits = nameWidth <= available;
        float nameMax = nameFits ? nameWidth : 0f;
        if (nameFits) {
            FontRenderer.drawTextBold(canvas, name, cursor, CardLayout.baseline(centerY, TITLE_SIZE), TITLE_SIZE,
                    GlassPanel.withAlpha(tc.primaryText, alpha));
        }

        float descX = cursor + nameMax + ICON_GAP;
        float descMax = Math.max(0f, textRight - descX);
        boolean descFits = nameFits && descMax >= DESC_MIN_WIDTH && description != null
                && !description.isEmpty()
                && FontRenderer.measureTextWidth(description, CAPTION_SIZE) <= descMax;
        if (descFits) {
            FontRenderer.drawText(canvas, description, descX, CardLayout.baseline(centerY, CAPTION_SIZE),
                    CAPTION_SIZE, GlassPanel.withAlpha(tc.labelTertiary, alpha));
        }

        if (mouseX < rowX || mouseX > rowX + rowW || mouseY < y || mouseY > y + HEIGHT) return;
        String tip = tooltipText(name, description, nameFits, descFits);
        if (tip != null) TooltipLayer.show(tip, mouseX, mouseY);
    }

    /**
     * 星标：<b>已收藏 = 实心</b>，未收藏 = 描边（悬停时描边转成强调色并垫一层淡底，表明这一小块是可点的收藏键）。
     *
     * <p><b>为什么是填色而不是换个颜色</b>（用户 2026-09-21：「点击收藏之后这个星星为什么没填满？
     * 做区分 应该是实心的啊」）：24 高的行上，同一枚描边星只改颜色，「已收藏」和「未收藏」扫一眼分不出来。
     * 现在收藏态由 {@link CardIcons#drawCenteredFilled} 把同一枚字形的外轮廓填满，形状本身就带状态，
     * 而且填出来的星与描边星<b>外缘完全重合</b>，点击时是「这颗星被填满」而不是「换了一颗星」。</p>
     */
    private static void drawStar(Canvas canvas, float centerX, float centerY, boolean favorite,
                                 float mouseX, float mouseY, float alpha, ClickGuiThemeColors tc) {
        boolean hover = Math.abs(mouseX - centerX) <= STAR_HIT && Math.abs(mouseY - centerY) <= STAR_HIT;
        if (hover) {
            GlassPanel.frost(canvas, centerX - STAR_HIT, centerY - STAR_HIT, STAR_HIT * 2f, STAR_HIT * 2f,
                    STAR_HIT, tc.field, 0.40f, alpha);
        }
        if (favorite) {
            CardIcons.drawCenteredFilled(canvas, STAR, centerX, centerY, STAR_GLYPH,
                    GlassPanel.withAlpha(tc.accent, alpha));
            return;
        }
        int color = GlassPanel.mix(tc.labelTertiary, tc.accent, hover ? 1f : 0f);
        CardIcons.drawCentered(canvas, STAR, centerX, centerY, STAR_GLYPH,
                GlassPanel.withAlpha(color, alpha));
    }

    /** 悬停浮层文案：名称或描述没画全时给出全文；两者都完整时不登记（没有需要补的内容）。 */
    private static String tooltipText(String name, String description, boolean nameFits, boolean descFits) {
        if (nameFits && descFits) return null;
        if (nameFits) return description;
        return description == null || description.isEmpty() ? name : name + "\n" + description;
    }
}
