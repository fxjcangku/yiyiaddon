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
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.core.BlockPos;

import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页：一屏看完「现在在哪、在干什么、配好了没有」。
 *
 * <p><b>为什么重做：</b>旧版首屏是后端数据（排名 / 人数 / 地区 / IP / 同步时间）——这些和「我正在玩的这一局」
 * 没关系，实机反馈「首页真的很不实用」。现在四块内容全部是当场就能用上的东西：</p>
 *
 * <ol>
 *   <li><b>当前状态</b>：服务器 / 维度 / 坐标 / 帧率 / 延迟 / 在线时长 / 版本 / 服务器资源；</li>
 *   <li><b>模块开关</b>：全部模块当场开关，不用先进模块中心；</li>
 *   <li><b>功能状态</b>：每个模块此刻在做什么（星露谷给任务 + 地块数，自动箱子给状态机，ID识别给当前模式）；</li>
 *   <li><b>星露谷 · 本维度</b>：资源包 / 区域 / 作物 / 季节 / 补水点 / 洒水器——上号前一眼核对配置齐不齐。</li>
 * </ol>
 *
 * <p>高度全部按内容算，绘制与命中共用同一组算式（{@link #switchCardY} 等），
 * 模块数量变化时行数自动跟着变。</p>
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

    /** 「当前状态」卡：4 列 × 2 行的键值单元。 */
    private static final int DATA_COLUMNS = 4;
    private static final float STATUS_H = 96f;
    private static final float LABEL_BASELINE = 15f;
    private static final float VALUE_BASELINE = 35f;
    private static final float CELL_ROW_H = 44f;

    /** 「模块开关」卡。 */
    private static final float SWITCH_HEADER_H = 30f;
    private static final float SWITCH_ROW_H = 34f;
    private static final int SWITCH_COLUMNS = 3;
    private static final float TOGGLE_W = 44f;
    private static final float TOGGLE_H = 24f;

    /** 底部双卡的标题高度与行高。 */
    private static final float BOTTOM_HEADER_H = 30f;
    private static final float BOTTOM_ROW_H = 24f;
    /** 右侧「星露谷 · 本维度」的固定行数（资源包 / 区域 / 作物 / 季节 / 补水点 / 洒水器）。 */
    private static final int DIMENSION_ROWS = 6;

    /** 数值缺省占位。 */
    private static final String UNKNOWN = "--";
    /** 不算「正在干活」的状态文案：与各模块自己的空闲文案一致。 */
    private static final List<String> IDLE_STATES = List.of(
            "空闲", "未启用", "未注册", "未启动", "已停止", "Idle", "Disabled", "Not registered");
    /** 会话起点：用于「在线时长」，进程启动那一刻。 */
    private static final long SESSION_START = System.currentTimeMillis();

    /** 画刷静态复用：卡片底，避免每帧新建 Skia 原生对象。 */
    private static final Paint CARD_BG = new Paint().setAntiAlias(true);

    /** 卡片里列出的全部模块（顺序来自注册表）。 */
    private final List<ModuleEntry> modules = ModuleRegistry.all();
    /** 每个模块一个开关控件；绘制与命中都按模块下标取用。 */
    private final List<SettingToggle> toggles = new ArrayList<>();

    public HomePage() {
        for (ModuleEntry entry : modules) {
            toggles.add(new SettingToggle(entry::enabled, value -> ModuleManager.setEnabled(entry.id(), value)));
        }
    }

    @Override
    public String getTitle() {
        return UiText.t("首页", "Home");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("当前状态、模块开关与配置核对", "Live status, module switches and setup check");
    }

    @Override
    public float getTotalHeight() {
        return TOP_INSET + STATUS_H + SECTION_GAP + switchCardH() + SECTION_GAP + bottomCardH() + PAD_BOTTOM;
    }

    @Override
    public void update(float dt) {
        for (SettingToggle toggle : toggles) toggle.update(dt);
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

    /** 模块开关卡顶部（已含滚动偏移，屏幕坐标）。 */
    private float switchCardY(float pageY, float scrollOffset) {
        return pageY + TOP_INSET + STATUS_H + SECTION_GAP - scrollOffset;
    }

    /** 底部双卡顶部（已含滚动偏移，屏幕坐标）。 */
    private float bottomRowY(float pageY, float scrollOffset) {
        return switchCardY(pageY, scrollOffset) + switchCardH() + SECTION_GAP;
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
        drawStatusCard(canvas, x, y + TOP_INSET - scrollOffset, contentW, alpha, tc);
        drawSwitchCard(canvas, x, switchCardY(y, scrollOffset), contentW, alpha, tc);

        float bottomY = bottomRowY(y, scrollOffset);
        float bottomH = bottomCardH();
        float colW = (contentW - SECTION_GAP) / 2f;
        drawModuleStatusCard(canvas, x, bottomY, colW, bottomH, alpha, tc);
        drawDimensionCard(canvas, x + colW + SECTION_GAP, bottomY, colW, bottomH, alpha, tc);
    }

    /** 当前状态卡：服务器 / 维度 / 坐标 / 帧率 / 延迟 / 在线时长 / 版本 / 服务器资源。 */
    private void drawStatusCard(Canvas canvas, float x, float y, float w, float alpha, ClickGuiThemeColors tc) {
        drawCardBg(canvas, x, y, w, STATUS_H, alpha, tc);

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

            SettingToggle toggle = toggles.get(i);
            toggle.draw(canvas, toggleX(cellX, colW), cellY + (SWITCH_ROW_H - TOGGLE_H) / 2f, alpha);
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

            float nameMax = w * 0.42f;
            FontRenderer.drawText(canvas, CardLayout.ellipsize(entry.displayName(), nameMax, 11f),
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

    /** 星露谷 · 本维度卡：上号前一眼核对「资源包 / 区域 / 作物 / 季节 / 补水点 / 洒水器」。 */
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
            toggles.get(i).toggle();
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

    /** 「星露谷 · 本维度」六行：读的是模块自己的状态快照与点位表，界面不另算一套。 */
    private static String[][] dimensionRows() {
        if (!(ModuleManager.byId(StardewFarmModule.MODULE_ID) instanceof StardewFarmModule farm)) {
            return unknownDimensionRows();
        }
        StardewStatusSnapshot snapshot = farm.statusReporter().snapshot();
        boolean water = farm.pointManager().get(StardewPointType.WATER_SOURCE) != null;
        int sprinklers = farm.pointManager().getInCurrentDimension(StardewPointType.SPRINKLER).size();
        return new String[][]{
                {UiText.t("服务器资源", "Resources"), orUnknown(snapshot.resource())},
                {UiText.t("本维度区域", "Plots"), farm.regionsInDimension().size() + UiText.t(" 块", " plots")},
                {UiText.t("目标作物", "Crops"), orUnknown(snapshot.crops())},
                {UiText.t("季节", "Season"), orUnknown(snapshot.season())},
                {UiText.t("补水点", "Water Source"),
                        water ? UiText.t("已绑定", "Bound") : UiText.t("未绑定", "Not bound")},
                {UiText.t("洒水器", "Sprinklers"), sprinklers + UiText.t(" 台", " units")},
        };
    }

    private static String[][] unknownDimensionRows() {
        String[][] rows = new String[DIMENSION_ROWS][2];
        String[] labels = {
                UiText.t("服务器资源", "Resources"), UiText.t("本维度区域", "Plots"),
                UiText.t("目标作物", "Crops"), UiText.t("季节", "Season"),
                UiText.t("补水点", "Water Source"), UiText.t("洒水器", "Sprinklers")
        };
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
}
