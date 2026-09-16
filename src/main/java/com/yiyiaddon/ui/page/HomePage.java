package com.yiyiaddon.ui.page;

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
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.PlayerFaceCache;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * 首页仪表盘：全部内容都是本次会话的真实状态。
 *
 * <p>五块内容自上而下：</p>
 * <ol>
 *   <li><b>后端数据</b>：账户 / 排名 / 人数 / 后端 / 地区 / 网络 / IP / 同步时间（{@link HomeStats}）；</li>
 *   <li><b>当前状态</b>：服务器 / 维度 / 坐标 / 帧率 / 延迟 / 在线时长 / 版本 / 服务器资源；</li>
 *   <li><b>模块开关</b>：全部模块当场开关，不用先进模块中心；</li>
 *   <li><b>功能状态</b>（左）与<b>星露谷 · 本维度</b>（右）：各模块此刻在做什么、本维度配置齐不齐；</li>
 *   <li><b>最近活动</b>：模块开关与异常的流水（{@link ActivityLog}）。</li>
 * </ol>
 *
 * <p>高度全部按内容算，绘制与命中共用同一组算式（{@link #switchCardY} 等），模块数量变化时行数自动跟着变；
 * 内容超出可视高度时整页可滚动（总高由 {@link #getTotalHeight()} 给出，滚动由外层负责）。</p>
 *
 * <p><b>注意</b>：第 1 块是原有的后端统计，不属于「可裁剪」的装饰——它由 {@link HomeStats} 60 秒后台刷新，
 * 渲染线程只读 volatile 字段，不做网络等待。</p>
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

    /** 键值单元的标签 / 数值基线相对单元顶部的偏移，以及单元行高。 */
    private static final float LABEL_BASELINE = 15f;
    private static final float VALUE_BASELINE = 35f;
    private static final float CELL_ROW_H = 44f;
    /** 顶部两张键值卡的列数与高度（4 列 × 2 行）。 */
    private static final int DATA_COLUMNS = 4;
    private static final float DATA_H = 96f;

    /** 后端数据卡顶部的账户条：头像 + 玩家名 + 会话 ID + 正版/离线徽标。 */
    private static final float ACCOUNT_H = 46f;
    private static final float ACCOUNT_AVATAR = 32f;

    /** 模块开关卡：标题高度、每行高度、列数。 */
    private static final float SWITCH_HEADER_H = 30f;
    private static final float SWITCH_ROW_H = 34f;
    private static final int SWITCH_COLUMNS = 3;

    /** 底部双卡与活动卡的标题高度、行高。 */
    private static final float BOTTOM_HEADER_H = 30f;
    private static final float BOTTOM_ROW_H = 24f;
    /** 右侧「星露谷 · 本维度」的固定行数。 */
    private static final int DIMENSION_ROWS = 5;

    /** 最近活动展示条数与卡片高度。 */
    private static final int ACTIVITY_LINES = 3;
    private static final float ACTIVITY_LINE_H = 22f;
    private static final float ACTIVITY_H = BOTTOM_HEADER_H + ACTIVITY_LINES * ACTIVITY_LINE_H + 8f;

    /** 开关控件尺寸，与 {@link SettingToggle} 内部一致，用于排版与命中。 */
    private static final float TOGGLE_W = 44f;
    private static final float TOGGLE_H = 24f;

    /** 数值缺省占位。 */
    private static final String UNKNOWN = "--";
    /** 最后同步时间只用到时分秒。 */
    private static final DateTimeFormatter SYNC_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
    /** 不算「正在干活」的状态文案：与各模块自己的空闲文案保持一致。 */
    private static final List<String> IDLE_STATES = List.of(
            "空闲", "未启用", "未注册", "未启动", "已停止", "Idle", "Disabled", "Not registered");
    /** 会话起点：用于「在线时长」，进程启动那一刻。 */
    private static final long SESSION_START = System.currentTimeMillis();

    /** 常见出口地区码 → 中文名；未收录的码原样展示。 */
    private static final Map<String, String> REGION_NAMES = Map.ofEntries(
            Map.entry("CN", "中国"), Map.entry("HK", "香港"), Map.entry("TW", "台湾"),
            Map.entry("MO", "澳门"), Map.entry("JP", "日本"), Map.entry("KR", "韩国"),
            Map.entry("SG", "新加坡"), Map.entry("MY", "马来西亚"), Map.entry("TH", "泰国"),
            Map.entry("VN", "越南"), Map.entry("PH", "菲律宾"), Map.entry("ID", "印度尼西亚"),
            Map.entry("IN", "印度"), Map.entry("US", "美国"), Map.entry("CA", "加拿大"),
            Map.entry("MX", "墨西哥"), Map.entry("BR", "巴西"), Map.entry("AR", "阿根廷"),
            Map.entry("GB", "英国"), Map.entry("DE", "德国"), Map.entry("FR", "法国"),
            Map.entry("NL", "荷兰"), Map.entry("BE", "比利时"), Map.entry("CH", "瑞士"),
            Map.entry("AT", "奥地利"), Map.entry("SE", "瑞典"), Map.entry("NO", "挪威"),
            Map.entry("FI", "芬兰"), Map.entry("DK", "丹麦"), Map.entry("PL", "波兰"),
            Map.entry("ES", "西班牙"), Map.entry("IT", "意大利"), Map.entry("PT", "葡萄牙"),
            Map.entry("IE", "爱尔兰"), Map.entry("CZ", "捷克"), Map.entry("RO", "罗马尼亚"),
            Map.entry("UA", "乌克兰"), Map.entry("TR", "土耳其"), Map.entry("RU", "俄罗斯"),
            Map.entry("AE", "阿联酋"), Map.entry("SA", "沙特阿拉伯"), Map.entry("IL", "以色列"),
            Map.entry("AU", "澳大利亚"), Map.entry("NZ", "新西兰"), Map.entry("ZA", "南非"));

    /** 画刷静态复用：卡片底，避免每帧新建 Skia 原生对象。 */
    private static final Paint CARD_BG = new Paint().setAntiAlias(true);

    /** 卡片里列出的全部模块（顺序来自注册表）。 */
    private final List<ModuleEntry> modules = ModuleRegistry.all();
    /** 每个模块一个开关控件；绘制与命中都按模块下标取用。 */
    private final Map<String, SettingToggle> toggles = new LinkedHashMap<>();

    public HomePage() {
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
        return TOP_INSET + dataCardH() + SECTION_GAP + DATA_H + SECTION_GAP + switchCardH()
                + SECTION_GAP + bottomCardH() + SECTION_GAP + ACTIVITY_H + PAD_BOTTOM;
    }

    @Override
    public void update(float dt) {
        for (SettingToggle toggle : toggles.values()) toggle.update(dt);
    }

    // ── 布局：绘制与命中必须用同一组算式 ──

    /** 模块开关卡高度：标题 + 若干行（至少一行，空注册表也有标题）。 */
    private float switchCardH() {
        int rows = Math.max(1, (modules.size() + SWITCH_COLUMNS - 1) / SWITCH_COLUMNS);
        return SWITCH_HEADER_H + rows * SWITCH_ROW_H;
    }

    /** 底部双卡高度：取「模块行数」与「本维度行数」里多的那个。 */
    private float bottomCardH() {
        int rows = Math.max(1, Math.max(modules.size(), DIMENSION_ROWS));
        return BOTTOM_HEADER_H + rows * BOTTOM_ROW_H + 8f;
    }

    /** 后端数据卡顶部（已含滚动偏移，屏幕坐标）。 */
    private float dataCardY(float pageY, float scrollOffset) {
        return pageY + TOP_INSET - scrollOffset;
    }

    /** 后端数据卡总高：账户条 + 键值网格。 */
    private float dataCardH() {
        return ACCOUNT_H + DATA_H;
    }

    /** 当前状态卡顶部。 */
    private float statusCardY(float pageY, float scrollOffset) {
        return dataCardY(pageY, scrollOffset) + dataCardH() + SECTION_GAP;
    }

    /** 模块开关卡顶部。 */
    private float switchCardY(float pageY, float scrollOffset) {
        return statusCardY(pageY, scrollOffset) + DATA_H + SECTION_GAP;
    }

    /** 底部双卡顶部。 */
    private float bottomRowY(float pageY, float scrollOffset) {
        return switchCardY(pageY, scrollOffset) + switchCardH() + SECTION_GAP;
    }

    /** 最近活动卡顶部。 */
    private float activityCardY(float pageY, float scrollOffset) {
        return bottomRowY(pageY, scrollOffset) + bottomCardH() + SECTION_GAP;
    }

    /** 单元格宽度：卡片左右内边距之后的可用宽按列数均分。 */
    private static float cellW(float cardW) {
        return (cardW - CARD_PAD * 2f) / SWITCH_COLUMNS;
    }

    /** 开关控件左边界：贴在单元格右侧内缩 8px 处；绘制与命中共用。 */
    private static float toggleX(float cellX, float cellWidth) {
        return cellX + cellWidth - 8f - TOGGLE_W;
    }

    // ── 绘制 ──

    @Override
    public void draw(Canvas canvas, float x, float y, float contentW, float contentH, float alpha, float scrollOffset,
                     float mouseX, float mouseY) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        drawDataCard(canvas, x, dataCardY(y, scrollOffset), contentW, alpha, tc);
        drawStatusCard(canvas, x, statusCardY(y, scrollOffset), contentW, alpha, tc);
        drawSwitchCard(canvas, x, switchCardY(y, scrollOffset), contentW, alpha, tc);

        float bottomY = bottomRowY(y, scrollOffset);
        float bottomH = bottomCardH();
        float colW = (contentW - SECTION_GAP) / 2f;
        drawModuleStatusCard(canvas, x, bottomY, colW, bottomH, alpha, tc);
        drawDimensionCard(canvas, x + colW + SECTION_GAP, bottomY, colW, bottomH, alpha, tc);

        drawActivityCard(canvas, x, activityCardY(y, scrollOffset), contentW, alpha, tc);
    }

    /** 后端数据卡：账户条 + 账户 / 排名 / 人数 / 后端 / 地区 / 网络 / IP / 同步时间，4 列两行。 */
    private void drawDataCard(Canvas canvas, float x, float y, float w, float alpha, ClickGuiThemeColors tc) {
        drawCardBg(canvas, x, y, w, dataCardH(), alpha, tc);

        boolean premium = HomeStats.premium();
        boolean backend = HomeStats.backendOnline();
        boolean network = HomeStats.networkReachable();
        int rank = HomeStats.rank();
        int totalUsers = HomeStats.totalUsers();

        drawAccountStrip(canvas, x, y, w, alpha, tc, premium);

        int labelC = GlassPanel.withAlpha(tc.secondaryText, alpha);
        int valueC = GlassPanel.withAlpha(tc.primaryText, alpha);
        int goodC = GlassPanel.withAlpha(0x22C55E, alpha);
        int badC = GlassPanel.withAlpha(0xEF4444, alpha);

        String[] labels = {
                UiText.t("账户状态", "Account"), UiText.t("用户排名", "Rank"),
                UiText.t("使用人数", "Users"), UiText.t("后端状态", "Backend"),
                UiText.t("网络地区", "Region"), UiText.t("网络状态", "Network"),
                UiText.t("IP", "IP"), UiText.t("最后同步", "Last Sync")
        };
        String[] values = {
                premium ? UiText.t("正版", "Premium") : UiText.t("离线", "Offline"),
                rank > 0 ? "#" + rank : UNKNOWN,
                totalUsers > 0 ? String.format(Locale.ROOT, "%,d", totalUsers) : UNKNOWN,
                backend ? UiText.t("正常", "Online") : UiText.t("异常", "Offline"),
                regionName(HomeStats.countryCode()),
                network ? UiText.t("已连接", "Connected") : UiText.t("异常", "Error"),
                HomeStats.ip() == null ? UNKNOWN : HomeStats.ip(),
                syncTime()
        };
        int[] colors = {
                premium ? goodC : badC, valueC, valueC,
                backend ? goodC : badC, valueC,
                network ? goodC : badC, valueC, valueC
        };

        float rowW = w - CARD_PAD * 2f;
        float gridY = y + ACCOUNT_H;
        drawCellRow(canvas, x + CARD_PAD, gridY + 12f, rowW, labels, values, colors, labelC, 0);
        drawCellRow(canvas, x + CARD_PAD, gridY + 12f + CELL_ROW_H, rowW, labels, values, colors, labelC, DATA_COLUMNS);
    }

    /**
     * 账户条：头像 + 玩家名 + 会话 ID + 正版/离线徽标。
     *
     * <p>身份一律取<b>会话账户</b>（{@link ClientIdentity}）而不是玩家实体 —— 正版链路下服务器会重写
     * 实体 UUID，用实体 UUID 会跟后端统计口径对不上（同 {@code ClientIdentity} 的既有约定）。
     * 头像走 {@link PlayerFaceCache}：只画皮肤贴图的脸与帽子层，是 2D 头像，不需要 3D 模型与联网。</p>
     */
    private void drawAccountStrip(Canvas canvas, float x, float y, float w, float alpha,
                                  ClickGuiThemeColors tc, boolean premium) {
        int nameC = GlassPanel.withAlpha(tc.primaryText, alpha);
        int idC = GlassPanel.withAlpha(tc.secondaryText, alpha);
        int badgeC = GlassPanel.withAlpha(premium ? 0x22C55E : 0xEF4444, alpha);

        float avatarX = x + CARD_PAD;
        float avatarY = y + (ACCOUNT_H - ACCOUNT_AVATAR) / 2f;
        PlayerFaceCache.draw(canvas, localSkin(), avatarX, avatarY, ACCOUNT_AVATAR);

        float textX = avatarX + ACCOUNT_AVATAR + 10f;
        String name = ClientIdentity.name();
        FontRenderer.drawTextBold(canvas, name == null || name.isBlank() ? UNKNOWN : name,
                textX, CardLayout.baseline(y + 8f, 13f), 13f, nameC);

        String uuid = ClientIdentity.uuidString();
        FontRenderer.drawText(canvas, UiText.t("ID", "ID") + " " + (uuid == null ? UNKNOWN : uuid),
                textX, CardLayout.baseline(y + 24f, 10f), 10f, idC);

        String badge = premium ? UiText.t("正版", "Premium") : UiText.t("离线", "Offline");
        FontRenderer.drawText(canvas, badge,
                x + w - CARD_PAD - FontRenderer.measureTextWidth(badge, 11f),
                CardLayout.baseline(y + (ACCOUNT_H - 11f) / 2f, 11f), 11f, badgeC);
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

    /** 当前状态卡：服务器 / 维度 / 坐标 / 帧率 / 延迟 / 在线时长 / 客户端版本 / 服务器资源。 */
    private void drawStatusCard(Canvas canvas, float x, float y, float w, float alpha, ClickGuiThemeColors tc) {
        drawCardBg(canvas, x, y, w, DATA_H, alpha, tc);

        int labelC = GlassPanel.withAlpha(tc.secondaryText, alpha);
        int valueC = GlassPanel.withAlpha(tc.primaryText, alpha);
        int goodC = GlassPanel.withAlpha(0x22C55E, alpha);
        int badC = GlassPanel.withAlpha(0xEF4444, alpha);

        boolean ready = ResourceExtractionService.isReady();
        String[] labels = {
                UiText.t("服务器", "Server"), UiText.t("维度", "Dimension"),
                UiText.t("坐标", "Position"), UiText.t("帧率", "FPS"),
                UiText.t("延迟", "Ping"), UiText.t("在线时长", "Session"),
                UiText.t("客户端版本", "Version"), UiText.t("服务器资源", "Server Resources")
        };
        String[] values = {
                serverLabel(), dimensionLabel(), positionLabel(), fpsLabel(),
                latencyLabel(), sessionLabel(), ClientIdentity.version(),
                ready ? UiText.t("已就绪", "Ready") : ResourceExtractionService.phase().label()
        };
        int[] colors = {valueC, valueC, valueC, valueC, valueC, valueC, valueC, ready ? goodC : badC};

        float rowW = w - CARD_PAD * 2f;
        drawCellRow(canvas, x + CARD_PAD, y + 12f, rowW, labels, values, colors, labelC, 0);
        drawCellRow(canvas, x + CARD_PAD, y + 12f + CELL_ROW_H, rowW, labels, values, colors, labelC, DATA_COLUMNS);
    }

    /** 模块开关卡：标题带「运行 N / M」，下方按列铺开全部模块的开关。 */
    private void drawSwitchCard(Canvas canvas, float x, float y, float w, float alpha, ClickGuiThemeColors tc) {
        drawCardBg(canvas, x, y, w, switchCardH(), alpha, tc);

        float titleY = CardLayout.baseline(y + 16f, 13f);
        FontRenderer.drawTextBold(canvas, UiText.t("模块开关", "Modules"), x + CARD_PAD, titleY, 13f,
                GlassPanel.withAlpha(tc.primaryText, alpha));

        String count = ModuleManager.enabledIds().size() + " / " + modules.size();
        FontRenderer.drawText(canvas, count,
                x + w - CARD_PAD - FontRenderer.measureTextWidth(count, 11f), titleY, 11f,
                GlassPanel.withAlpha(tc.secondaryText, alpha));

        if (modules.isEmpty()) {
            FontRenderer.drawText(canvas, UiText.t("还没有注册任何模块", "No modules registered"),
                    x + CARD_PAD, CardLayout.baseline(y + SWITCH_HEADER_H + SWITCH_ROW_H / 2f, 11f), 11f,
                    GlassPanel.withAlpha(tc.labelTertiary, alpha));
            return;
        }

        float colW = cellW(w);
        int labelC = GlassPanel.withAlpha(tc.primaryText, alpha);
        for (int i = 0; i < modules.size(); i++) {
            ModuleEntry entry = modules.get(i);
            float cellX = x + CARD_PAD + (i % SWITCH_COLUMNS) * colW;
            float cellY = y + SWITCH_HEADER_H + (i / SWITCH_COLUMNS) * SWITCH_ROW_H;

            float textMax = colW - 8f - TOGGLE_W - 8f;
            FontRenderer.drawText(canvas, CardLayout.ellipsize(entry.displayName(), textMax, 12f),
                    cellX, CardLayout.baseline(cellY + SWITCH_ROW_H / 2f, 12f), 12f, labelC);

            SettingToggle toggle = toggles.get(entry.id());
            if (toggle != null) {
                toggle.draw(canvas, toggleX(cellX, colW), cellY + (SWITCH_ROW_H - TOGGLE_H) / 2f, alpha);
            }
        }
    }

    /** 功能状态卡：每个模块一行——左边模块名，右边此刻在做什么，下面一行小字补细节。 */
    private void drawModuleStatusCard(Canvas canvas, float x, float y, float w, float h, float alpha,
                                      ClickGuiThemeColors tc) {
        drawCardBg(canvas, x, y, w, h, alpha, tc);
        FontRenderer.drawTextBold(canvas, UiText.t("功能状态", "Module Status"), x + CARD_PAD,
                CardLayout.baseline(y + 16f, 13f), 13f, GlassPanel.withAlpha(tc.primaryText, alpha));

        if (modules.isEmpty()) {
            FontRenderer.drawText(canvas, UiText.t("还没有注册任何模块", "No modules registered"),
                    x + CARD_PAD, y + BOTTOM_HEADER_H + 14f, 11f,
                    GlassPanel.withAlpha(tc.labelTertiary, alpha));
            return;
        }

        int nameC = GlassPanel.withAlpha(tc.primaryText, alpha);
        int detailC = GlassPanel.withAlpha(tc.labelTertiary, alpha);
        for (int i = 0; i < modules.size(); i++) {
            ModuleEntry entry = modules.get(i);
            float rowY = y + BOTTOM_HEADER_H + i * BOTTOM_ROW_H;
            ModuleStatus status = statusOf(entry);

            FontRenderer.drawText(canvas, CardLayout.ellipsize(entry.displayName(), w * 0.42f, 11f),
                    x + CARD_PAD, CardLayout.baseline(rowY + 8f, 11f), 11f, nameC);

            int stateC = stateIsActive(status.state())
                    ? GlassPanel.withAlpha(0x22C55E, alpha)
                    : GlassPanel.withAlpha(tc.labelTertiary, alpha);
            String state = CardLayout.ellipsize(status.state(), w * 0.45f, 11f);
            FontRenderer.drawTextBold(canvas, state,
                    x + w - CARD_PAD - FontRenderer.measureTextWidth(state, 11f),
                    CardLayout.baseline(rowY + 8f, 11f), 11f, stateC);

            if (!status.detail().isBlank()) {
                FontRenderer.drawText(canvas, CardLayout.ellipsize(status.detail(), w - CARD_PAD * 2f, 10f),
                        x + CARD_PAD, CardLayout.baseline(rowY + 22f, 10f), 10f, detailC);
            }
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
            float rowY = y + BOTTOM_HEADER_H + i * BOTTOM_ROW_H;
            FontRenderer.drawText(canvas, rows[i][0], x + CARD_PAD, CardLayout.baseline(rowY + 8f, 11f), 11f, labelC);
            String value = CardLayout.ellipsize(rows[i][1], w * 0.62f, 11f);
            FontRenderer.drawTextBold(canvas, value,
                    x + w - CARD_PAD - FontRenderer.measureTextWidth(value, 11f),
                    CardLayout.baseline(rowY + 8f, 11f), 11f, valueC);
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

    /** 一行键值单元：等宽分列，上标签下数值，超宽自动截断。 */
    private void drawCellRow(Canvas canvas, float x, float y, float w, String[] labels, String[] values,
                             int[] valueColors, int labelC, int from) {
        float colW = w / DATA_COLUMNS;
        for (int i = 0; i < DATA_COLUMNS; i++) {
            int index = from + i;
            float cx = x + i * colW;
            FontRenderer.drawText(canvas, labels[index], cx, y + LABEL_BASELINE, 10f, labelC);
            FontRenderer.drawTextBold(canvas, CardLayout.ellipsize(values[index], colW - 14f, 12f),
                    cx, y + VALUE_BASELINE, 12f, valueColors[index]);
        }
    }

    /** 卡片圆角底。 */
    private void drawCardBg(Canvas canvas, float x, float y, float w, float h, float alpha, ClickGuiThemeColors tc) {
        CARD_BG.setColor(GlassPanel.withAlpha(tc.module, alpha * 0.4f));
        canvas.drawRRect(RRect.makeXYWH(x, y, w, h, CARD_RADIUS), CARD_BG);
    }

    // ── 输入 ──

    @Override
    public boolean onClick(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset,
                           int button) {
        if (button != GLFW.GLFW_MOUSE_BUTTON_LEFT || modules.isEmpty()) return false;
        float colW = cellW(contentW);
        float cardTop = switchCardY(contentY, scrollOffset);
        for (int i = 0; i < modules.size(); i++) {
            float cellX = contentX + CARD_PAD + (i % SWITCH_COLUMNS) * colW;
            float cellY = cardTop + SWITCH_HEADER_H + (i / SWITCH_COLUMNS) * SWITCH_ROW_H;
            float tx = toggleX(cellX, colW);
            float ty = cellY + (SWITCH_ROW_H - TOGGLE_H) / 2f;
            if (mx < tx || mx > tx + TOGGLE_W || my < ty || my > ty + TOGGLE_H) continue;
            SettingToggle toggle = toggles.get(modules.get(i).id());
            if (toggle == null) return false;
            toggle.toggle();
            return true;
        }
        return false;
    }

    @Override
    public boolean onDrag(float mx, float my, float contentX, float contentY, float contentW, float scrollOffset) {
        return false;
    }

    // ── 数据读数 ──

    /** 一个模块此刻的状态与补充说明。 */
    private record ModuleStatus(String state, String detail) {
    }

    /** 按模块取实时状态；未启用时不读业务数据，避免在停机状态下报出过期内容。 */
    private static ModuleStatus statusOf(ModuleEntry entry) {
        if (ModuleManager.byId(entry.id()) == null) {
            return new ModuleStatus(UiText.t("未注册", "Not registered"), "");
        }
        if (!entry.enabled()) {
            return new ModuleStatus(UiText.t("未启用", "Disabled"), "");
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

    /** 地区码转中文名；未收录时原样返回，无数据返回占位。 */
    private static String regionName(String countryCode) {
        if (countryCode == null || countryCode.isBlank()) return UNKNOWN;
        String code = countryCode.trim().toUpperCase(Locale.ROOT);
        return REGION_NAMES.getOrDefault(code, code);
    }

    /** 最近一次成功同步的本地时间；从未成功过返回占位。 */
    private static String syncTime() {
        long millis = HomeStats.lastSyncMillis();
        if (millis <= 0L) return UNKNOWN;
        return SYNC_TIME.format(Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()));
    }
}
