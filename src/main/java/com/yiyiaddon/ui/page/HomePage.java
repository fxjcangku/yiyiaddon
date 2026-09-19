package com.yiyiaddon.ui.page;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.identity.IdIdentifyModule;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.status.StardewStatusSnapshot;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.module.ModuleRegistry;
import com.yiyiaddon.platform.ClientIdentity;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.service.ActivityLog;
import com.yiyiaddon.service.HomeStats;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.ui.RegionNames;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.ModuleRow;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.PlayerFaceCache;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.navigation.PageRouter;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.PlayerSkin;

import org.lwjgl.glfw.GLFW;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 首页仪表盘：五块内容自上而下，每一块都是「此刻能拿来判断 / 拿来动手」的东西。
 *
 * <ol>
 *   <li><b>账户</b>：头像旁直接显示「用户名：正版 / 离线」，下面是排名 / 人数 / 后端 / 地区 / IP / 同步时间；</li>
 *   <li><b>本次会话</b>：服务器 / 维度 / 坐标 / 帧率 / 延迟 / 在线时长 / 服务器资源 / 启用模块数；</li>
 *   <li><b>常用模块</b>（用户自己收藏的模块，一键开关）与 <b>需要处理</b>（启用中但自检不通过的模块）；</li>
 *   <li><b>运行中</b>（只列启用中的模块与此刻在做什么）与 <b>星露谷 · 本维度</b>；</li>
 *   <li><b>最近活动</b>：模块开关与异常的流水（{@link ActivityLog}）。</li>
 * </ol>
 *
 * <p><b>为什么不再铺 22 个模块开关</b>（用户 2026-09-18：「没必要带模块的开关 不实用 你帮我重做一下」）：
 * 22 个开关一屏铺不完、九成时间里九成都是关着的，既看不完也点不准；真正有用的是
 * 「我常用的那几个」+「哪个开着却没配好」。于是首页只留<b>收藏模块</b>的开关（模块中心右键即可收藏），
 * 另加一张「需要处理」卡把自检缺项直接列出来并点进去修；其余模块归模块中心。</p>
 *
 * <p>高度全部按内容算，绘制、命中、总高共用同一组算式（{@code cardY} 一族），所以模块数 / 收藏数变化时
 * 只改一处。列表里的行都可点：收藏行点开关、问题行与运行行点进模块页、卡片底部的链接切到模块中心。</p>
 */
public final class HomePage extends BasePage {

    /** 页头下方留白与内容区底部收口。 */
    private static final float TOP_INSET = 18f;
    private static final float PAD_BOTTOM = 10f;
    /** 区块之间的间隙。 */
    private static final float SECTION_GAP = 12f;
    /** 卡片圆角与内边距。 */
    private static final float CARD_RADIUS = 10f;
    private static final float CARD_PAD = 14f;

    /** 数据格的标签 / 数值基线相对单元顶部的偏移，以及单元行高。 */
    private static final float LABEL_BASELINE = 14f;
    private static final float VALUE_BASELINE = 32f;
    private static final float CELL_ROW_H = 42f;
    private static final float CELL_GAP = 6f;
    /**
     * 数值字号：基准 12 号，放不下逐档降到 {@link #FITTED_MIN_SIZE}。
     *
     * <p>降档而不是一律截断，是为了让长值（IPv6、长模块名）尽量完整可读；再小就伤可读性，
     * 所以到 9 号为止，剩下的交给 {@link CardLayout#ellipsize}。</p>
     */
    private static final float VALUE_SIZE = 12f;
    private static final float FITTED_MIN_SIZE = 9f;
    /** 键值网格顶边相对卡片顶部的间距（账户卡与「本次会话」卡共用）。 */
    private static final float GRID_TOP_GAP = 8f;
    /** 「本次会话」标题占据的高度；数据格从标题下方开始。 */
    private static final float SESSION_HEADER_H = 28f;
    /**
     * 键值网格列数与卡片高度。
     *
     * <p><b>高度按绘制算式反推、不是拍一个数</b>（用户 2026-09-18：「又跑到框外了」）：卡片高度必须
     * ≥ 网格上间距 + 两行单元 + 底部内边距，否则第二行的数值基线会落到卡片底边之外 —— 看着就是文字
     * 挂在卡外。原先写死的 96 比这本账少 18，账户卡那组更少 26（见 {@link #dataCardH()}）。</p>
     */
    private static final int DATA_COLUMNS = 4;
    private static final float DATA_H = SESSION_HEADER_H + 2f * CELL_ROW_H + CELL_GAP + CARD_PAD;
    /** 账户卡的列数（三列两行，比四列宽松，六项都读得清）。 */
    private static final int ACCOUNT_COLUMNS = 3;

    /** 账户条：头像旁紧跟「玩家名：正版 / 离线」，不再展示过长 UUID。 */
    private static final float ACCOUNT_H = 50f;
    private static final float ACCOUNT_AVATAR = 36f;

    /** 卡片标题高度与卡片底部留白（列表行数之外的那点余量）。 */
    private static final float CARD_HEADER_H = 30f;
    private static final float CARD_FOOT_PAD = 8f;
    /** 收藏行高（放得下 24 高的开关）与开关宽度，与 {@link SettingToggle} 内部一致。 */
    private static final float FAVORITE_ROW_H = 30f;
    private static final float TOGGLE_W = 44f;
    private static final float TOGGLE_H = 24f;
    /** 卡片底部的链接行高。 */
    private static final float LINK_ROW_H = 26f;
    /** 各类列表最多显示多少行（超出的部分给一行汇总，不让卡片无限长）。 */
    private static final int FAVORITE_MAX = 6;
    private static final int ISSUE_MAX = 5;
    private static final int RUNNING_MAX = 8;
    /** 右侧「星露谷 · 本维度」的固定行数。 */
    private static final int DIMENSION_ROWS = 5;
    /**
     * 列表行高。
     *
     * <p><b>为什么不用 {@link ModuleRow#HEIGHT}（24）：</b>那 24 里还含 8 的呼吸位，
     * 并排卡片里放「16 的图标盒 + 12 号文字」会贴边；28 让图标与文字都有一格余量。</p>
     */
    private static final float ROW_H = 28f;

    /** 最近活动展示条数与卡片高度。 */
    private static final int ACTIVITY_LINES = 3;
    private static final float ACTIVITY_LINE_H = 22f;
    private static final float ACTIVITY_H = CARD_HEADER_H + ACTIVITY_LINES * ACTIVITY_LINE_H + 8f;

    /** 数值缺省占位。 */
    private static final String UNKNOWN = "--";
    /** 最后同步时间只用到时分秒。 */
    private static final DateTimeFormatter SYNC_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
    /** 不算「正在干活」的状态文案：与各模块自己的空闲文案保持一致。 */
    private static final List<String> IDLE_STATES = List.of(
            "空闲", "未启用", "未注册", "未启动", "已停止", "Idle", "Disabled", "Not registered");
    /** 会话起点：用于「在线时长」，进程启动那一刻。 */
    private static final long SESSION_START = System.currentTimeMillis();

    /** 语义色：正常 / 异常 / 提示（与卡片其它位置同一组色值，不新造配色）。 */
    private static final int COLOR_GOOD = 0x22C55E;
    private static final int COLOR_BAD = 0xEF4444;
    private static final int COLOR_WARN = 0xF59E0B;

    /** 画刷静态复用：卡片底，避免每帧新建 Skia 原生对象。 */
    private static final Paint CARD_BG = new Paint().setAntiAlias(true);

    /** 页面路由：底部「打开模块中心」需要切导航；由宿主注入（第 180 条：构造期不碰注册表） */
    private final PageRouter router;
    /** 打开模块独立页的入口：与模块中心点模块卡片走同一条路径 */
    private final Consumer<ModuleEntry> moduleOpener;

    /** 全部模块（顺序来自注册表） */
    private final List<ModuleEntry> modules = ModuleRegistry.all();
    /** 每个模块一个开关控件；收藏行的绘制与命中都按 id 取用 */
    private final Map<String, SettingToggle> toggles = new LinkedHashMap<>();
    /** 收藏模块（每帧从 {@link AddonConfig#favoriteModules} 重读：在模块中心改过收藏，回首页立刻生效） */
    private final List<ModuleEntry> favorites = new ArrayList<>();

    public HomePage(PageRouter router, Consumer<ModuleEntry> moduleOpener) {
        this.router = router;
        this.moduleOpener = moduleOpener;
        for (ModuleEntry entry : modules) {
            toggles.put(entry.id(), new SettingToggle(entry::enabled,
                    value -> ModuleManager.setEnabled(entry.id(), value)));
        }
    }

    @Override
    public String getTitle() {
        return UiText.t("首页", "Home");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("yiyiaddon 客户端控制中心", "yiyiaddon client control centre");
    }

    @Override
    public float getTotalHeight() {
        return TOP_INSET + dataCardH() + SECTION_GAP + DATA_H + SECTION_GAP + listRowH()
                + SECTION_GAP + sessionListH() + SECTION_GAP + ACTIVITY_H + PAD_BOTTOM;
    }

    @Override
    public void update(float dt) {
        for (SettingToggle toggle : toggles.values()) toggle.update(dt);
    }

    // ── 布局：绘制、命中与总高必须用同一组算式 ──

    /**
     * 账户卡总高：账户条 + 网格上间距 + 三列两行的键值网格 + 卡片下内边距。
     *
     * <p><b>这几个数是一本账，少一项就出框</b>（用户 2026-09-18：「又跑到框外了」）：原先写的是
     * {@code ACCOUNT_H + 2 * CELL_ROW_H}（= 134），漏了网格上间距 12 与底部内边距 —— 第二行的值
     * （网络地区 / IP / 最后同步）基线落在 137，比卡片底边还低，看着就是文字挂在卡外。
     * 现在按绘制用的同一组算式反推，绘制与命中同源。</p>
     */
    private float dataCardH() {
        return ACCOUNT_H + GRID_TOP_GAP + 2f * CELL_ROW_H + CELL_GAP + CARD_PAD;
    }

    /** 「常用模块」卡高度：标题 + 收藏行（至少一行，用于空态提示） */
    private float favoriteCardH() {
        return CARD_HEADER_H + Math.max(1, Math.min(favorites.size(), FAVORITE_MAX)) * FAVORITE_ROW_H
                + CARD_FOOT_PAD;
    }

    /** 「需要处理」卡高度：标题 + 问题行（至少一行，用于「全部正常」）+ 底部链接行 */
    private float issueCardH() {
        return CARD_HEADER_H + Math.max(1, Math.min(issues().size(), ISSUE_MAX)) * ROW_H
                + LINK_ROW_H + CARD_FOOT_PAD;
    }

    /** 第三段两张卡片的高度（并排，取高的一张，短的那张下方留白） */
    private float listRowH() {
        return Math.max(favoriteCardH(), issueCardH());
    }

    /** 「运行中」卡高度：标题 + 最多 {@link #RUNNING_MAX} 行 */
    private float runningCardH() {
        return CARD_HEADER_H + Math.max(1, Math.min(running().size(), RUNNING_MAX)) * ROW_H + CARD_FOOT_PAD;
    }

    /** 「星露谷 · 本维度」卡高度：固定五行 */
    private float dimensionCardH() {
        return CARD_HEADER_H + DIMENSION_ROWS * ROW_H + CARD_FOOT_PAD;
    }

    /** 第四段两张卡片的高度 */
    private float sessionListH() {
        return Math.max(runningCardH(), dimensionCardH());
    }

    /** 账户卡顶部（已含滚动偏移，屏幕坐标） */
    private float dataCardY(float pageY, float scrollOffset) {
        return pageY + TOP_INSET - scrollOffset;
    }

    /** 本次会话卡顶部 */
    private float statusCardY(float pageY, float scrollOffset) {
        return dataCardY(pageY, scrollOffset) + dataCardH() + SECTION_GAP;
    }

    /** 第三段（常用模块 + 需要处理）顶部 */
    private float listRowY(float pageY, float scrollOffset) {
        return statusCardY(pageY, scrollOffset) + DATA_H + SECTION_GAP;
    }

    /** 第四段（运行中 + 星露谷本维度）顶部 */
    private float sessionRowY(float pageY, float scrollOffset) {
        return listRowY(pageY, scrollOffset) + listRowH() + SECTION_GAP;
    }

    /** 最近活动卡顶部 */
    private float activityCardY(float pageY, float scrollOffset) {
        return sessionRowY(pageY, scrollOffset) + sessionListH() + SECTION_GAP;
    }

    /** 并排两卡的左卡宽 */
    private static float halfWidth(float contentW) {
        return (contentW - SECTION_GAP) / 2f;
    }

    /** 收藏行里开关的左边界：贴单元格右侧内缩 8px；绘制与命中共用 */
    private static float toggleX(float cardX, float cardW) {
        return cardX + cardW - CARD_PAD - 8f - TOGGLE_W;
    }

    /**
     * 并排卡片里的行底：圆角毛玻璃 + 悬停描边（收藏行与状态行共用同一份外观）。
     *
     * @param rowH 底板高度（比行高少 6，留出行距）
     */
    private static void drawRowBackground(Canvas canvas, float x, float y, float w, float rowH,
                                          boolean hover, float alpha, ClickGuiThemeColors tc) {
        float radius = GlassPanel.rowRadius(rowH);
        int background = GlassPanel.mix(tc.module, tc.surfaceHover, hover ? 1f : 0f);
        GlassPanel.frost(canvas, x, y, w, rowH, radius, background, 0.55f, alpha);
        GlassPanel.rim(canvas, x, y, w, rowH, radius, tc.rim, alpha, 0.06f + 0.14f * (hover ? 1f : 0f));
    }

    /**
     * 并排卡片里的单行：图标 + 左侧模块名 + 右侧状态文字（只有一行，全文交给悬停浮层）。
     *
     * <p><b>为什么不用 {@link ModuleRow#drawEntry}：</b>那是给整页宽（501）的行设计的，
     * 「名称 40% + 说明 + 状态标记 + 箭头」四件东西挤进半宽卡片（约 244）时，模块名只剩三四个字
     * ——用户 2026-09-18 已经明确反对「字超出框 根本看不见名字」。这里只留最要紧的两件：
     * 名字（左，优先吃满）与状态（右，语义色），放不下的部分由悬停浮层给出原文。</p>
     *
     * @param state 行尾状态文字（空串则不画）
     * @param color 状态语义色（裸色，透明度由这里乘）
     * @param hint  悬停浮层文案（空串则不给浮层）
     */
    private static void drawListRow(Canvas canvas, ModuleEntry entry, String state, int color, String hint,
                                    float x, float y, float w, float alpha, ClickGuiThemeColors tc,
                                    float mouseX, float mouseY) {
        boolean hover = hovered(mouseX, mouseY, x, y, w, ROW_H);
        float rowH = ROW_H - 6f;
        drawRowBackground(canvas, x, y, w, rowH, hover, alpha, tc);

        float centerY = y + rowH / 2f;
        float cursor = ModuleRow.drawIcon(canvas, entry.icon(), x + 6f, centerY, alpha, tc);
        // 状态文字最多占半行：先截断再量宽，右对齐用的才是真正画出来的那一串
        String stateText = state == null || state.isBlank()
                ? "" : CardLayout.ellipsize(state, w * 0.5f, 11f);
        float stateW = stateText.isEmpty() ? 0f : FontRenderer.measureTextWidth(stateText, 11f);
        float nameMax = Math.max(24f, x + w - 8f - stateW - (stateW > 0f ? 8f : 0f) - cursor);
        FontRenderer.drawTextBold(canvas, CardLayout.ellipsize(entry.displayName(), nameMax, 12f),
                cursor, CardLayout.baseline(centerY, 12f), 12f,
                GlassPanel.withAlpha(tc.primaryText, alpha));

        if (stateW > 0f) {
            FontRenderer.drawText(canvas, stateText, x + w - 8f - stateW,
                    CardLayout.baseline(centerY, 11f), 11f, GlassPanel.withAlpha(color, alpha));
        }
        if (hover && hint != null && !hint.isBlank()) TooltipLayer.show(hint, mouseX, mouseY);
    }

    // ── 绘制 ──

    @Override
    public void draw(Canvas canvas, float x, float y, float contentW, float contentH, float alpha, float scrollOffset,
                     float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        refreshFavorites();

        drawDataCard(canvas, x, dataCardY(y, scrollOffset), contentW, alpha, tc);
        drawStatusCard(canvas, x, statusCardY(y, scrollOffset), contentW, alpha, tc);

        float listY = listRowY(y, scrollOffset);
        float colW = halfWidth(contentW);
        drawFavoriteCard(canvas, x, listY, colW, alpha, tc, mouseX, mouseY);
        drawIssueCard(canvas, x + colW + SECTION_GAP, listY, colW, alpha, tc, mouseX, mouseY);

        float sessionY = sessionRowY(y, scrollOffset);
        drawRunningCard(canvas, x, sessionY, colW, alpha, tc, mouseX, mouseY);
        drawDimensionCard(canvas, x + colW + SECTION_GAP, sessionY, colW, sessionListH(), alpha, tc);

        drawActivityCard(canvas, x, activityCardY(y, scrollOffset), contentW, alpha, tc);
    }

    /**
     * 账户卡：紧凑账户条 + 六项数据格（首行四项等宽，次行 1:2 —— 跨两列的 IP 格才放得下 IPv6）。
     *
     * <p>格子顺序：用户排名 / 使用人数 / 后端状态 / 最后同步，网络地区 / IP。
     * 里面的「网络状态」被 IP 顶掉了 —— 出口 IP 探得出来就说明网络通，同一件事不再占两格；
     * IP 按数据上色（主题高亮色），不再挤在账户条的 ID 后面（用户 2026-09-18：「IP 跑到框外了」）。
     * IP 格还兼挂出口提示（探测到代理时右上角一枚角标），具体见 {@link #proxyBadge()}。</p>
     */
    private void drawDataCard(Canvas canvas, float x, float y, float w, float alpha, ClickGuiThemeColors tc) {
        drawCardBg(canvas, x, y, w, dataCardH(), alpha, tc);

        boolean premium = HomeStats.premium();
        boolean backend = HomeStats.backendOnline();
        boolean network = HomeStats.networkReachable();
        int rank = HomeStats.rank();
        int totalUsers = HomeStats.totalUsers();
        String ip = HomeStats.ip();

        drawAccountStrip(canvas, x, y, w, alpha, tc, premium);

        int labelC = GlassPanel.withAlpha(tc.secondaryText, alpha);
        int valueC = GlassPanel.withAlpha(tc.primaryText, alpha);
        int goodC = GlassPanel.withAlpha(COLOR_GOOD, alpha);
        int badC = GlassPanel.withAlpha(COLOR_BAD, alpha);
        int accentC = GlassPanel.withAlpha(tc.accent, alpha);

        String[] labels = {
                UiText.t("用户排名", "Rank"), UiText.t("使用人数", "Users"),
                UiText.t("后端状态", "Backend"),
                UiText.t("网络地区", "Region"), UiText.t("IP", "IP"), UiText.t("最后同步", "Last Sync")
        };
        String[] values = {
                rank > 0 ? "#" + rank : UNKNOWN,
                totalUsers > 0 ? String.format(Locale.ROOT, "%,d", totalUsers) : UNKNOWN,
                backend ? UiText.t("正常", "Online") : UiText.t("异常", "Offline"),
                regionLabel(HomeStats.countryCode(), HomeStats.region()),
                ip == null || ip.isBlank() ? UNKNOWN : ip,
                syncTime()
        };
        // 后端可达看后端、地区与 IP 的取数看网络：探测失败时 IP 那格会显示占位而不是空着
        int[] colors = {valueC, valueC, backend ? goodC : badC, valueC,
                network ? accentC : badC, valueC};
        String[] badges = {null, null, null, null, proxyBadge(), null};

        float rowW = w - CARD_PAD * 2f;
        float gridY = y + ACCOUNT_H + GRID_TOP_GAP;
        drawMetricRow(canvas, x + CARD_PAD, gridY, rowW, ACCOUNT_COLUMNS, 0,
                labels, values, colors, labelC, alpha, tc);
        drawMetricRow(canvas, x + CARD_PAD, gridY + CELL_ROW_H + CELL_GAP,
                rowW, ACCOUNT_COLUMNS, ACCOUNT_COLUMNS,
                labels, values, colors, labelC, alpha, tc, badges, COLOR_WARN);
    }

    /**
     * 账户条：头像旁直接显示「玩家名：正版 / 离线」。
     *
     * <p>身份一律取<b>会话账户</b>（{@link ClientIdentity}）而不是玩家实体 —— 正版链路下服务器会重写
     * 实体 UUID，用实体 UUID 会跟后端统计口径对不上（同 {@code ClientIdentity} 的既有约定）。
     * 头像走 {@link PlayerFaceCache}：只画皮肤贴图的脸与帽子层，是 2D 头像，不需要 3D 模型与联网。</p>
     *
     * <p>UUID 不属于首页需要持续判断的信息，而且 36 个字符会把身份状态推到卡片远端；首页因此不再显示 UUID。
     * 身份标签放在用户名<b>前面</b>（`正版：yiyijia` / `离线：yiyijia`），先看到自己是不是正版；
     * 分段着色：正版为绿色，离线为红色。{@code FontRenderer} 是 Skia 自绘、
     * 不解析 {@code §} 颜色码，所以颜色必须靠分段绘制传入，不能写进字符串。</p>
     */
    private void drawAccountStrip(Canvas canvas, float x, float y, float w, float alpha,
                                  ClickGuiThemeColors tc, boolean premium) {
        int nameC = GlassPanel.withAlpha(tc.primaryText, alpha);
        int accountC = GlassPanel.withAlpha(premium ? COLOR_GOOD : COLOR_BAD, alpha);

        float avatarX = x + CARD_PAD;
        float avatarY = y + (ACCOUNT_H - ACCOUNT_AVATAR) / 2f;
        PlayerFaceCache.draw(canvas, localSkin(), avatarX, avatarY, ACCOUNT_AVATAR);

        // 标签在前、名字在后；标签先量宽，名字按剩余宽度省略，保证标签不会被挤掉。
        String account = (premium ? UiText.t("正版", "Premium") : UiText.t("离线", "Offline")) + "：";
        float accountW = FontRenderer.measureTextWidth(account, 12f);
        float textX = avatarX + ACCOUNT_AVATAR + 10f;

        String name = ClientIdentity.name();
        String nameLabel = CardLayout.ellipsize(
                name == null || name.isBlank() ? UNKNOWN : name,
                Math.max(60f, x + w - CARD_PAD - accountW - 8f - textX), 13f);
        float baseline = CardLayout.baseline(y + ACCOUNT_H / 2f, 13f);
        FontRenderer.drawTextBold(canvas, account, textX, baseline, 12f, accountC);
        FontRenderer.drawTextBold(canvas, nameLabel,
                textX + accountW + 4f, baseline, 13f, nameC);
    }

    /**
     * 会话账户的皮肤：在世界里直接取本地玩家实体上已解析好的那一份（不发任何网络请求），
     * 不在世界时退回原版按 UUID 派生的默认皮肤。
     */
    private static PlayerSkin localSkin() {
        Minecraft client = Minecraft.getInstance();
        if (client != null && client.player != null) {
            PlayerSkin skin = client.player.getSkin();
            if (skin != null) return skin;
        }
        UUID id = ClientIdentity.uuid();
        return id == null ? DefaultPlayerSkin.getDefaultSkin() : DefaultPlayerSkin.get(id);
    }

    /**
     * 本次会话卡：服务器 / 维度 / 坐标 / 帧率 / 延迟 / 在线时长 / 服务器资源 / 启用模块数。
     *
     * <p>原来的「客户端版本」在这里删掉了：左上角侧栏本来就常显模组版本，同一屏里出现两次是废信息；
     * 空出的格子换成「模块 运行中 N / M」——原先那张铺满 22 个开关的卡片被撤掉后，
     * 玩家仍需要一个「一共开着几个」的总览。</p>
     */
    private void drawStatusCard(Canvas canvas, float x, float y, float w, float alpha, ClickGuiThemeColors tc) {
        drawCardBg(canvas, x, y, w, DATA_H, alpha, tc);

        FontRenderer.drawTextBold(canvas, UiText.t("本次会话", "Current Session"), x + CARD_PAD,
                CardLayout.baseline(y + 16f, 13f), 13f,
                GlassPanel.withAlpha(tc.primaryText, alpha));

        int labelC = GlassPanel.withAlpha(tc.secondaryText, alpha);
        int valueC = GlassPanel.withAlpha(tc.primaryText, alpha);
        int goodC = GlassPanel.withAlpha(COLOR_GOOD, alpha);
        int badC = GlassPanel.withAlpha(COLOR_BAD, alpha);

        boolean ready = ResourceExtractionService.isReady();
        String[] labels = {
                UiText.t("服务器", "Server"), UiText.t("维度", "Dimension"),
                UiText.t("坐标", "Position"), UiText.t("帧率", "FPS"),
                UiText.t("延迟", "Ping"), UiText.t("在线时长", "Session"),
                UiText.t("服务器资源", "Server Resources"), UiText.t("模块", "Modules")
        };
        String[] values = {
                serverLabel(), dimensionLabel(), positionLabel(), fpsLabel(),
                latencyLabel(), sessionLabel(),
                ready ? UiText.t("已就绪", "Ready") : ResourceExtractionService.phase().label(),
                ModuleManager.enabledIds().size() + " / " + modules.size()
        };
        int[] colors = {valueC, valueC, valueC, valueC, valueC, valueC, ready ? goodC : badC, valueC};

        float rowW = w - CARD_PAD * 2f;
        float gridY = y + SESSION_HEADER_H;
        drawMetricRow(canvas, x + CARD_PAD, gridY, rowW, DATA_COLUMNS, 0,
                labels, values, colors, labelC, alpha, tc);
        drawMetricRow(canvas, x + CARD_PAD, gridY + CELL_ROW_H + CELL_GAP,
                rowW, DATA_COLUMNS, DATA_COLUMNS,
                labels, values, colors, labelC, alpha, tc);
    }

    /**
     * 常用模块卡：只列玩家自己收藏的模块，每行「图标 + 名字 + 开关」，点开关直接开关模块。
     *
     * <p>空态给一句可执行的指引（去模块中心右键），不放「暂无数据」这种没法行动的提示。</p>
     */
    private void drawFavoriteCard(Canvas canvas, float x, float y, float w, float alpha, ClickGuiThemeColors tc,
                                  float mouseX, float mouseY) {
        drawCardBg(canvas, x, y, w, favoriteCardH(), alpha, tc);

        float titleY = CardLayout.baseline(y + 16f, 13f);
        FontRenderer.drawTextBold(canvas, UiText.t("常用模块", "Favorites"), x + CARD_PAD, titleY, 13f,
                GlassPanel.withAlpha(tc.primaryText, alpha));
        String count = favorites.isEmpty()
                ? UiText.t("未收藏", "none")
                : favorites.size() + " " + UiText.t("个", "items");
        FontRenderer.drawText(canvas, count,
                x + w - CARD_PAD - FontRenderer.measureTextWidth(count, 11f), titleY, 11f,
                GlassPanel.withAlpha(tc.secondaryText, alpha));

        if (favorites.isEmpty()) {
            int hintC = GlassPanel.withAlpha(tc.labelTertiary, alpha);
            FontRenderer.drawText(canvas, UiText.t("在模块中心右键模块即可收藏", "Right-click a module to pin it"),
                    x + CARD_PAD, CardLayout.baseline(y + CARD_HEADER_H + 10f, 11f), 11f, hintC);
            FontRenderer.drawText(canvas, UiText.t("收藏后出现在这里，一键开关", "Pinned modules show up here"),
                    x + CARD_PAD, CardLayout.baseline(y + CARD_HEADER_H + 26f, 10f), 10f, hintC);
            return;
        }

        int nameC = GlassPanel.withAlpha(tc.primaryText, alpha);
        float rowX = x + CARD_PAD;
        float rowW = w - CARD_PAD * 2f;
        float toggleLeft = toggleX(x, w);
        for (int i = 0; i < Math.min(favorites.size(), FAVORITE_MAX); i++) {
            ModuleEntry entry = favorites.get(i);
            float rowY = y + CARD_HEADER_H + i * FAVORITE_ROW_H;
            boolean isHovered = hovered(mouseX, mouseY, x, rowY, w, FAVORITE_ROW_H);

            float centerY = rowY + FAVORITE_ROW_H / 2f;
            drawRowBackground(canvas, rowX, rowY + 3f, rowW, FAVORITE_ROW_H - 6f, isHovered, alpha, tc);
            float cursor = ModuleRow.drawIcon(canvas, entry.icon(), rowX + 6f, centerY, alpha, tc);
            float nameMax = Math.max(24f, toggleLeft - 8f - cursor);
            FontRenderer.drawTextBold(canvas, CardLayout.ellipsize(entry.displayName(), nameMax, 12f),
                    cursor, CardLayout.baseline(centerY, 12f), 12f, nameC);

            SettingToggle toggle = toggles.get(entry.id());
            if (toggle != null) {
                toggle.draw(canvas, toggleLeft, centerY - TOGGLE_H / 2f, alpha);
            }
            if (isHovered && entry.description() != null && !entry.description().isBlank()) {
                TooltipLayer.show(entry.displayName() + "\n§7" + entry.description(), mouseX, mouseY);
            }
        }
    }

    /**
     * 需要处理卡：列出「已启用但自检不通过」的模块，点一行直接进该模块页去补配置。
     *
     * <p><b>只看启用中的模块</b>：没开的模块缺配置是正常状态（还没用），列出来只会变成噪音。
     * <b>「未进入世界」这条被过滤掉</b>：它是运行时对「不在世界里」的统一说法，在主菜单/标题界面
     * 每个模块都会报，属于当前场景而非配置缺项。</p>
     */
    private void drawIssueCard(Canvas canvas, float x, float y, float w, float alpha, ClickGuiThemeColors tc,
                               float mouseX, float mouseY) {
        drawCardBg(canvas, x, y, w, issueCardH(), alpha, tc);

        List<Issue> issues = issues();
        float titleY = CardLayout.baseline(y + 16f, 13f);
        FontRenderer.drawTextBold(canvas, UiText.t("需要处理", "Needs Attention"), x + CARD_PAD, titleY, 13f,
                GlassPanel.withAlpha(tc.primaryText, alpha));
        String count = issues.isEmpty() ? UiText.t("无", "none") : issues.size() + " " + UiText.t("项", "items");
        FontRenderer.drawText(canvas, count,
                x + w - CARD_PAD - FontRenderer.measureTextWidth(count, 11f), titleY, 11f,
                GlassPanel.withAlpha(issues.isEmpty() ? tc.secondaryText : COLOR_WARN, alpha));

        if (issues.isEmpty()) {
            FontRenderer.drawText(canvas, UiText.t("启用中的模块都已就绪", "All enabled modules are ready"),
                    x + CARD_PAD, CardLayout.baseline(y + CARD_HEADER_H + ROW_H / 2f, 11f), 11f,
                    GlassPanel.withAlpha(COLOR_GOOD, alpha));
        } else {
            float rowX = x + CARD_PAD;
            float rowW = w - CARD_PAD * 2f;
            for (int i = 0; i < Math.min(issues.size(), ISSUE_MAX); i++) {
                Issue issue = issues.get(i);
                float rowY = y + CARD_HEADER_H + i * ROW_H;
                drawListRow(canvas, issue.entry(), issue.detail(), COLOR_WARN,
                        issue.entry().displayName() + "\n§7" + issue.detail(),
                        rowX, rowY, rowW, alpha, tc, mouseX, mouseY);
            }
        }

        // 底部链接行：模块中心才是「所有模块」的家，首页只负责指路
        float linkY = y + issueCardH() - CARD_FOOT_PAD - LINK_ROW_H;
        boolean linkHover = hovered(mouseX, mouseY, x, linkY, w, LINK_ROW_H);
        int linkC = GlassPanel.withAlpha(GlassPanel.mix(tc.accent, tc.primaryText, linkHover ? 1f : 0f), alpha);
        FontRenderer.drawTextBold(canvas, UiText.t("打开模块中心", "Open Module Center"),
                x + CARD_PAD, CardLayout.baseline(linkY + LINK_ROW_H / 2f, 11f), 11f, linkC);
    }

    /** 运行中卡：只列启用中的模块与此刻在做什么，点一行进该模块页。 */
    private void drawRunningCard(Canvas canvas, float x, float y, float w, float alpha, ClickGuiThemeColors tc,
                                 float mouseX, float mouseY) {
        drawCardBg(canvas, x, y, w, runningCardH(), alpha, tc);

        List<ModuleEntry> running = running();
        float titleY = CardLayout.baseline(y + 16f, 13f);
        FontRenderer.drawTextBold(canvas, UiText.t("运行中", "Running"), x + CARD_PAD, titleY, 13f,
                GlassPanel.withAlpha(tc.primaryText, alpha));
        String count = running.size() + " / " + modules.size();
        FontRenderer.drawText(canvas, count,
                x + w - CARD_PAD - FontRenderer.measureTextWidth(count, 11f), titleY, 11f,
                GlassPanel.withAlpha(tc.secondaryText, alpha));

        if (running.isEmpty()) {
            FontRenderer.drawText(canvas, UiText.t("没有启用中的模块", "No module is running"),
                    x + CARD_PAD, CardLayout.baseline(y + CARD_HEADER_H + ROW_H / 2f, 11f), 11f,
                    GlassPanel.withAlpha(tc.labelTertiary, alpha));
            return;
        }

        float rowX = x + CARD_PAD;
        float rowW = w - CARD_PAD * 2f;
        for (int i = 0; i < Math.min(running.size(), RUNNING_MAX); i++) {
            ModuleEntry entry = running.get(i);
            float rowY = y + CARD_HEADER_H + i * ROW_H;
            ModuleStatus status = statusOf(entry);
            boolean active = stateIsActive(status.state());
            String hint = status.detail() == null || status.detail().isBlank()
                    ? entry.displayName() : entry.displayName() + "\n§7" + status.detail();
            drawListRow(canvas, entry, status.state(), active ? tc.stateOn : tc.labelTertiary, hint,
                    rowX, rowY, rowW, alpha, tc, mouseX, mouseY);
        }
    }

    /** 星露谷 · 本维度卡：上号前一眼核对「区域 / 作物 / 季节 / 补水点 / 洒水器」。 */
    private void drawDimensionCard(Canvas canvas, float x, float y, float w, float h, float alpha,
                                   ClickGuiThemeColors tc) {
        drawCardBg(canvas, x, y, w, h, alpha, tc);
        FontRenderer.drawTextBold(canvas, UiText.t("星露谷 · 本维度", "Stardew · This Dimension"), x + CARD_PAD,
                CardLayout.baseline(y + 16f, 13f), 13f, GlassPanel.withAlpha(tc.primaryText, alpha));

        int labelC = GlassPanel.withAlpha(tc.secondaryText, alpha);
        int valueC = GlassPanel.withAlpha(tc.primaryText, alpha);
        String[][] rows = dimensionRows();
        for (int i = 0; i < rows.length; i++) {
            float rowY = y + CARD_HEADER_H + i * ROW_H;
            float centerY = rowY + ROW_H / 2f;
            FontRenderer.drawText(canvas, rows[i][0], x + CARD_PAD, CardLayout.baseline(centerY, 11f), 11f, labelC);
            String value = CardLayout.ellipsize(rows[i][1], w * 0.62f, 11f);
            FontRenderer.drawTextBold(canvas, value,
                    x + w - CARD_PAD - FontRenderer.measureTextWidth(value, 11f),
                    CardLayout.baseline(centerY, 11f), 11f, valueC);
        }
    }

    /** 最近活动卡：整宽，最新在前。 */
    private void drawActivityCard(Canvas canvas, float x, float y, float w, float alpha, ClickGuiThemeColors tc) {
        drawCardBg(canvas, x, y, w, ACTIVITY_H, alpha, tc);
        FontRenderer.drawTextBold(canvas, UiText.t("最近活动", "Recent Activity"), x + CARD_PAD,
                CardLayout.baseline(y + 16f, 13f), 13f, GlassPanel.withAlpha(tc.primaryText, alpha));

        float rowY = y + 38f;
        List<String> recent = ActivityLog.recent(ACTIVITY_LINES);
        if (recent.isEmpty()) {
            FontRenderer.drawText(canvas, UiText.t("暂无活动记录", "No recent activity"), x + CARD_PAD,
                    rowY, 11f, GlassPanel.withAlpha(tc.labelTertiary, alpha));
            return;
        }
        for (int i = 0; i < recent.size(); i++) {
            FontRenderer.drawText(canvas, CardLayout.ellipsize(recent.get(i), w - CARD_PAD * 2f, 11f),
                    x + CARD_PAD, rowY + i * ACTIVITY_LINE_H, 11f, GlassPanel.withAlpha(tc.secondaryText, alpha));
        }
    }

    /**
     * 一行等宽数据格：每项拥有自己的弱底与边框，上标签下数值，超宽自动截断。
     * 独立底板让列边界一眼可见，避免旧布局中标签和值像散落在大卡片上的错位感。
     */
    private void drawMetricRow(Canvas canvas, float x, float y, float w, int columns, int from,
                               String[] labels, String[] values, int[] valueColors, int labelC,
                               float alpha, ClickGuiThemeColors tc) {
        drawMetricRow(canvas, x, y, w, columns, from, labels, values, valueColors,
                labelC, alpha, tc, null, 0);
    }

    /**
     * 一行等宽数据格 + 角标：{@code badges} 与 {@code values} 同序，某项为 null 时不画。
     * 角标画在<b>本格内</b>的标签行右端，不另开格子、也不占数值行的宽度。
     */
    private void drawMetricRow(Canvas canvas, float x, float y, float w, int columns, int from,
                               String[] labels, String[] values, int[] valueColors, int labelC,
                               float alpha, ClickGuiThemeColors tc, String[] badges, int badgeColor) {
        float colW = (w - CELL_GAP * (columns - 1)) / columns;
        for (int i = 0; i < columns; i++) {
            int index = from + i;
            float cx = x + i * (colW + CELL_GAP);
            GlassPanel.frost(canvas, cx, y, colW, CELL_ROW_H, 8f, tc.field, 0.42f, alpha);
            GlassPanel.rim(canvas, cx, y, colW, CELL_ROW_H, 8f, tc.rim, alpha, 0.05f);
            FontRenderer.drawText(canvas, labels[index], cx + 9f, y + LABEL_BASELINE, 10f, labelC);

            String badge = badges == null ? null : badges[index];
            if (badge != null) {
                float badgeW = FontRenderer.measureTextWidth(badge, 10f);
                FontRenderer.drawText(canvas, badge, cx + colW - 9f - badgeW, y + LABEL_BASELINE,
                        10f, GlassPanel.withAlpha(badgeColor, alpha));
            }
            float valueMax = colW - 18f;
            String value = drawFittedText(values[index], valueMax);
            if (!value.isEmpty()) {
                FontRenderer.drawTextBold(canvas, value, cx + 9f, y + VALUE_BASELINE,
                        drawFittedSize(values[index], valueMax), valueColors[index]);
            }
        }
    }

    /**
     * 数值排版：从 12 号逐档缩到 9 号，优先整条放下，真放不下才截断。
     *
     * <p>IP 长度不固定（IPv4 15 字符、IPv6 最长 39 字符），固定 12 号在窄格里只能截成
     * {@code 2600:1f14:...} 把尾段丢掉（用户 2026-09-20：「ip太长成....了」）。
     * 宽度一律用与 {@link CardLayout#ellipsize} 相同的度量，保证「量得下」就等于「画得下」。</p>
     */
    private static float drawFittedSize(String text, float maxWidth) {
        if (text == null || text.isBlank()) return FITTED_MIN_SIZE;
        for (float size = VALUE_SIZE; size > FITTED_MIN_SIZE; size -= 1f) {
            if (FontRenderer.measureTextWidth(text, size) <= maxWidth) return size;
        }
        return FITTED_MIN_SIZE;
    }

    /** 与 {@link #drawFittedSize} 配套的文本：放得下原样返回，放不下才按最终字号截断。 */
    private static String drawFittedText(String text, float maxWidth) {
        if (text == null) return "";
        return CardLayout.ellipsize(text, maxWidth, drawFittedSize(text, maxWidth));
    }

    /** 卡片圆角底。 */
    private void drawCardBg(Canvas canvas, float x, float y, float w, float h, float alpha, ClickGuiThemeColors tc) {
        CARD_BG.setColor(GlassPanel.withAlpha(tc.module, alpha * 0.4f));
        canvas.drawRRect(RRect.makeXYWH(x, y, w, h, CARD_RADIUS), CARD_BG);
    }

    /** 指针是否落在某一行内（绘制里的悬停反馈与命中判定共用）。 */
    private static boolean hovered(float mouseX, float mouseY, float x, float y, float w, float h) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    // ── 输入 ──

    /**
     * 三段可点的东西，按屏幕位置自上而下判定：收藏行的开关 → 需要处理的问题行 → 底部链接行 → 运行中的行。
     *
     * <p>命中的横坐标只跟卡片几何有关（与 {@link #draw} 同一组算式），纵坐标用同一组 {@code *CardY}，
     * 因此不会出现「看得到点不到」。</p>
     */
    @Override
    public boolean onClick(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset,
                           int button) {
        if (button != GLFW.GLFW_MOUSE_BUTTON_LEFT) return false;
        refreshFavorites();
        float colW = halfWidth(contentW);

        // ① 常用模块：点开关切换；空态不响应
        float favY = listRowY(contentY, scrollOffset);
        if (!favorites.isEmpty() && my >= favY && my <= favY + favoriteCardH()) {
            float toggleLeft = toggleX(contentX, colW);
            for (int i = 0; i < Math.min(favorites.size(), FAVORITE_MAX); i++) {
                float rowY = favY + CARD_HEADER_H + i * FAVORITE_ROW_H;
                float ty = rowY + (FAVORITE_ROW_H - TOGGLE_H) / 2f;
                if (mx < toggleLeft || mx > toggleLeft + TOGGLE_W || my < ty || my > ty + TOGGLE_H) continue;
                SettingToggle toggle = toggles.get(favorites.get(i).id());
                if (toggle == null) return false;
                toggle.toggle();
                return true;
            }
        }

        // ② 需要处理：问题行开模块页，底部链接行切到模块中心
        float issueX = contentX + colW + SECTION_GAP;
        float issueY = listRowY(contentY, scrollOffset);
        if (mx >= issueX && my >= issueY && my <= issueY + issueCardH()) {
            List<Issue> issues = issues();
            for (int i = 0; i < Math.min(issues.size(), ISSUE_MAX); i++) {
                float rowY = issueY + CARD_HEADER_H + i * ROW_H;
                if (my < rowY || my > rowY + ROW_H) continue;
                moduleOpener.accept(issues.get(i).entry());
                return true;
            }
            if (my >= issueY + issueCardH() - CARD_FOOT_PAD - LINK_ROW_H) {
                openModuleCenter();
                return true;
            }
        }

        // ③ 运行中：点一行开模块页
        float runY = sessionRowY(contentY, scrollOffset);
        if (mx >= contentX && mx <= contentX + colW && my >= runY && my <= runY + runningCardH()) {
            List<ModuleEntry> running = running();
            for (int i = 0; i < Math.min(running.size(), RUNNING_MAX); i++) {
                float rowY = runY + CARD_HEADER_H + i * ROW_H;
                if (my < rowY || my > rowY + ROW_H) continue;
                moduleOpener.accept(running.get(i));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onDrag(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset) {
        return false;
    }

    /** 切到模块中心这张根页（按类型找，不写死导航下标） */
    private void openModuleCenter() {
        List<BasePage> roots = router.roots();
        for (int i = 0; i < roots.size(); i++) {
            if (roots.get(i) instanceof ModuleCenterPage) {
                router.select(i);
                return;
            }
        }
    }

    // ── 数据读数 ──

    /** 一条待处理项：模块 + 一句话说清缺什么。 */
    private record Issue(ModuleEntry entry, String detail) {
    }

    /** 一个模块此刻的状态与补充说明。 */
    private record ModuleStatus(String state, String detail) {
    }

    /** 从配置重读收藏（`;` 分隔的模块 id）：模块中心里改过收藏，回首页立刻生效。 */
    private void refreshFavorites() {
        favorites.clear();
        String raw = AddonConfig.favoriteModules;
        if (raw == null || raw.isBlank()) return;
        for (String id : raw.split(";")) {
            String trimmed = id.trim();
            if (trimmed.isEmpty()) continue;
            ModuleEntry entry = ModuleRegistry.byId(trimmed);
            if (entry != null) favorites.add(entry);
        }
    }

    /** 已启用模块，按注册表顺序。 */
    private List<ModuleEntry> running() {
        List<ModuleEntry> result = new ArrayList<>();
        for (ModuleEntry entry : modules) {
            if (entry.enabled()) result.add(entry);
        }
        return result;
    }

    /**
     * 待处理项：启用中、且自检有缺项的模块。
     *
     * <p>只取第一项缺项做摘要（一行的宽度放得下），完整列表在模块自己的控制台里。</p>
     */
    private static List<Issue> issues() {
        List<Issue> result = new ArrayList<>();
        for (ModuleEntry entry : ModuleRegistry.all()) {
            if (!entry.enabled()) continue;
            Module module = ModuleManager.byId(entry.id());
            if (module == null) continue;
            String detail = firstRealProblem(ModuleManager.problemsOf(module));
            if (detail != null) result.add(new Issue(entry, detail));
        }
        return result;
    }

    /** 取第一条非「未进入世界」的缺项；没有则返回 {@code null}。 */
    private static String firstRealProblem(List<String> problems) {
        if (problems == null || problems.isEmpty()) return null;
        for (String problem : problems) {
            if (problem == null || problem.isBlank()) continue;
            if (problem.contains("未进入世界")) continue;
            return problem;
        }
        return null;
    }

    /** 按模块取实时状态；未启用时不读业务数据（调用方只对启用中的模块调用）。 */
    private static ModuleStatus statusOf(ModuleEntry entry) {
        if (ModuleManager.byId(entry.id()) == null) {
            return new ModuleStatus(UiText.t("未注册", "Not registered"), "");
        }
        if (entry.id().equals(StardewFarmModule.MODULE_ID)
                && ModuleManager.byId(entry.id()) instanceof StardewFarmModule farm) {
            StardewStatusSnapshot snapshot = farm.statusReporter().snapshot();
            String task = snapshot.task() == null || snapshot.task().isBlank()
                    ? UiText.t("空闲", "Idle") : snapshot.task();
            String detail = snapshot.detail() == null || snapshot.detail().isBlank()
                    ? UiText.t("本维度 ", "This dimension: ") + farm.regionsInDimension().size()
                        + UiText.t(" 块地", " plots")
                    : snapshot.detail();
            return new ModuleStatus(task, detail);
        }
        if (entry.id().equals(AutoChestModule.MODULE_ID)
                && ModuleManager.byId(entry.id()) instanceof AutoChestModule chest) {
            return new ModuleStatus(chest.stateMachine().state().toString(), "");
        }
        if (entry.id().equals(IdIdentifyModule.MODULE_ID)
                && ModuleManager.byId(entry.id()) instanceof IdIdentifyModule identify) {
            List<String> labels = identify.modeLabels();
            int index = identify.modeIndex();
            String mode = index >= 0 && index < labels.size() ? labels.get(index) : UNKNOWN;
            return new ModuleStatus(UiText.t("运行中", "Running"), UiText.t("识别模式：", "Mode: ") + mode);
        }
        return new ModuleStatus(UiText.t("运行中", "Running"), "");
    }

    /** 「星露谷 · 本维度」五行：读的是模块自己的状态快照与点位表，界面不另算一套。 */
    private static String[][] dimensionRows() {
        if (!(ModuleManager.byId(StardewFarmModule.MODULE_ID) instanceof StardewFarmModule farm)) {
            return unknownDimensionRows();
        }
        StardewStatusSnapshot snapshot = farm.statusReporter().snapshot();
        boolean water = farm.pointManager().get(StardewPointType.WATER_SOURCE) != null;
        int sprinklers = farm.pointManager().getInCurrentDimension(StardewPointType.SPRINKLER).size();
        return new String[][]{
                {UiText.t("本维度区域", "Plots"), farm.regionsInDimension().size() + UiText.t(" 块", " plots")},
                {UiText.t("目标作物", "Crops"), orUnknown(snapshot.crops())},
                {UiText.t("季节", "Season"), orUnknown(snapshot.season())},
                {UiText.t("补水点", "Water Source"),
                        water ? UiText.t("已绑定", "Bound") : UiText.t("未绑定", "Not bound")},
                {UiText.t("洒水器", "Sprinklers"), sprinklers + UiText.t(" 台", " units")},
        };
    }

    private static String[][] unknownDimensionRows() {
        String[] labels = {
                UiText.t("本维度区域", "Plots"), UiText.t("目标作物", "Crops"), UiText.t("季节", "Season"),
                UiText.t("补水点", "Water Source"), UiText.t("洒水器", "Sprinklers")
        };
        String[][] rows = new String[DIMENSION_ROWS][2];
        for (int i = 0; i < DIMENSION_ROWS; i++) {
            rows[i][0] = labels[i];
            rows[i][1] = UNKNOWN;
        }
        return rows;
    }

    private static String orUnknown(String value) {
        return value == null || value.isBlank() ? UNKNOWN : value;
    }

    /** 服务器：多人显示地址，单机显示存档目录名（与数据隔离用的身份一致）。 */
    private static String serverLabel() {
        Minecraft mc = Minecraft.getInstance();
        ServerData data = mc.getCurrentServer();
        if (data != null && data.ip != null && !data.ip.isBlank()) return data.ip;
        String world = WorldIdentity.singleplayerWorldName();
        return world == null ? UiText.t("单机", "Singleplayer") : UiText.t("单机 · ", "Singleplayer · ") + world;
    }

    /** 当前维度（中文名）；不在世界内时给出占位。 */
    private static String dimensionLabel() {
        if (Minecraft.getInstance().level == null) return UNKNOWN;
        return WorldIdentity.dimensionDisplayName(WorldIdentity.dimension());
    }

    /** 玩家所在方块坐标。 */
    private static String positionLabel() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return UNKNOWN;
        BlockPos pos = mc.player.blockPosition();
        return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
    }

    private static String fpsLabel() {
        Minecraft mc = Minecraft.getInstance();
        int fps = mc.getFps();
        return fps <= 0 ? UNKNOWN : fps + " FPS";
    }

    /** 与当前服务器的往返延迟；单机没有这条数据。 */
    private static String latencyLabel() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getConnection() == null) return UNKNOWN;
        PlayerInfo info = mc.getConnection().getPlayerInfo(mc.player.getUUID());
        return info == null ? UNKNOWN : info.getLatency() + " ms";
    }

    /** 本次会话已运行时长：客户端进程启动至今。 */
    private static String sessionLabel() {
        long seconds = Math.max(0L, (System.currentTimeMillis() - SESSION_START) / 1000L);
        long hours = seconds / 3600L;
        long minutes = (seconds % 3600L) / 60L;
        if (hours > 0L) return hours + UiText.t(" 小时 ", " h ") + minutes + UiText.t(" 分", " min");
        if (minutes > 0L) return minutes + UiText.t(" 分 ", " min ") + (seconds % 60L) + UiText.t(" 秒", " s");
        return seconds + UiText.t(" 秒", " s");
    }

    /** 「空闲 / 未启用 / 未注册」不算活动状态，用弱化色显示。 */
    private static boolean stateIsActive(String state) {
        if (state == null || state.isBlank()) return false;
        return !IDLE_STATES.contains(state);
    }

    /** 网络地区：国名 + 一级行政区；与国名重名（港澳台）时只显示一次，无数据返回占位。 */
    private static String regionLabel(String countryCode, String region) {
        String country = RegionNames.country(countryCode);
        String area = RegionNames.region(region);
        if (country == null) return area == null ? UNKNOWN : area;
        if (area == null || area.equals(country)) return country;
        return country + " · " + area;
    }

    /**
     * IP 格右上角的出口提示：探测到代理才挂，移动网络（{@code MOBILE}）不算 ——
     * 蜂窝出口被探测源标成 proxy，但那是运营商，不是代理，写成「疑似 VPN」会误导。
     */
    private static String proxyBadge() {
        String type = HomeStats.proxyType();
        if (type == null || type.isBlank() || "MOBILE".equalsIgnoreCase(type)) return null;
        if ("TOR".equalsIgnoreCase(type)) return UiText.t("疑似 TOR", "Likely TOR");
        if ("PROXY".equalsIgnoreCase(type)) return UiText.t("疑似代理", "Likely proxy");
        return UiText.t("疑似 VPN", "Likely VPN");
    }

    /** 最近一次成功同步的本地时间；从未成功过返回占位。 */
    private static String syncTime() {
        long millis = HomeStats.lastSyncMillis();
        if (millis <= 0L) return UNKNOWN;
        return SYNC_TIME.format(Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()));
    }
}
