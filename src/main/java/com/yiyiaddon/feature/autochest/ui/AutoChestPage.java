package com.yiyiaddon.feature.autochest.ui;

import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.model.autochest.ContainerType;
import com.yiyiaddon.model.autochest.ContainerTypeRegistry;
import com.yiyiaddon.model.autochest.EspStyle;
import com.yiyiaddon.model.autochest.ScanMode;
import com.yiyiaddon.model.autochest.WithdrawMode;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.component.ButtonRow;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ListRow;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 自动箱子模块的独立页面（旧项目 {@code AutoChestModule.getWidget} 的移植）。
 *
 * <p><b>用户交互资产（逐字，禁止改写）：</b>模块头部按钮、点位卡片行、空态、三个二次确认窗、
 * 8 个设置分组名、21 个设置项的名称与描述、使用说明章节、全部计数标签与播报文案，
 * 全部来自旧项目原文；主题、按钮样式、控件形态、排布方式全部用本项目现有体系。</p>
 *
 * <p><b>可见性条件与旧项目一一对应：</b>标点按钮/卡片仅「标点模式」出现；
 * 触发距离仅「玩家控制模式」；标点管理仅「标点模式」；玩家检测距离仅在「多人保护」开启时；
 * 目标物品仅在取物模式非「全部拿空」时；每种物品数量仅在「按目标数量取」时；
 * ESP 框样式与三个颜色仅在「ESP高亮」开启时。</p>
 *
 * <p>界面跳转与旧项目一致：「设置箱子点位」与点位卡「删除」执行后直接回到游戏；
 * 三个二次确认窗（清空点位 / 清除记录 ×2）确认或取消后回到游戏；
 * 容器类型 / 每种物品数量 / 目标物品三个独立窗口关闭后回到本页面。</p>
 */
public final class AutoChestPage extends CompactModulePage implements ModulePage {

    // ━━━ 设置项描述（旧项目 autochest/config/AutoChestSettings.java 逐字） ━━━

    private static final String DESC_SCAN_MODE =
            "玩家控制模式：玩家自己走位，进入触发距离自动处理；寻路模式：自动扫描并寻路；标点模式：只处理保存点位。";
    private static final String DESC_CURRENT_MODE = "当前选中的运行模式（实时显示）。";
    private static final String DESC_TRIGGER_DISTANCE =
            "玩家控制模式下，距离容器多少格内自动处理（受开箱可达距离约 4.5 格限制，上限 4）。";
    private static final String DESC_MARKER = "标点通过说明面板按钮或指令管理，模块只处理已保存的点位。";
    private static final String DESC_CONTAINER_TYPES =
            "选择要识别的合法容器类型（箱子/陷阱箱/16色潜影盒/木桶/铜箱）。";
    private static final String DESC_SCAN_RADIUS = "扫描附近合法容器的半径（格），负责发现容器。";
    private static final String DESC_SCAN_INTERVAL = "每多少 Tick 推进一轮扫描（分帧扫描，不整世界全扫）。";
    private static final String DESC_RECORD_EXPIRE = "已处理容器记录多少分钟后失效，可被再次处理。";
    private static final String DESC_MULTIPLAYER_PROTECT =
            "检测到其他玩家正在使用目标容器时，不抢箱，暂时跳过并进入冷却。";
    private static final String DESC_PLAYER_DISTANCE = "其他玩家距离容器多少格内视为正在使用，触发多人保护。";
    private static final String DESC_MAX_RETRIES = "开箱/寻路/交互失败后最多重试几次，超过则本轮跳过该容器。";
    private static final String DESC_COOLDOWN = "连续失败或多人保护后，容器进入暂时不可用的冷却时长（Tick）。";
    private static final String DESC_WITHDRAW_MODE =
            "按目标数量取：每种目标物品单独配置数量；目标物品拿空：只拿空目标列表物品；全部拿空：忽略目标列表取走所有合法物品。";
    private static final String DESC_TARGET_ITEMS = "选择 AutoChest 要取的目标物品（数据源唯一来自 ID 配置管理）。";
    private static final String DESC_QUANTITY = "按目标数量取模式下，为每种目标物品单独配置目标数量。";
    private static final String DESC_ACTION_DELAY = "两次槽位操作之间的 Tick 间隔。";
    private static final String DESC_RENDER_ESP = "高亮显示已发现但未处理的容器。";
    private static final String DESC_ESP_STYLE = "容器的 ESP 渲染样式：仅线条 / 仅面 / 线+面。";
    private static final String DESC_UNPROCESSED_COLOR = "未处理容器的 ESP 颜色。";
    private static final String DESC_PROCESSED_COLOR = "已处理容器的 ESP 颜色。";
    private static final String DESC_PROCESSING_COLOR = "正在处理容器的 ESP 颜色（处理中不显示为已处理红色）。";

    /** 「标点管理」的实时值：旧项目 {@code AutoChestSettings.java:118} 原文 */
    private static final String MARKER_HINT =
            "§7面板按钮或 §e.autochest 添加/移除/清空/状态§7 管理点位；目标物品用 §e.id 物品§7 识别";

    /** 使用说明章节：旧项目 {@code AutoChestModule.buildSections()} 逐字 */
    private static final String[][] SECTIONS = {
            {"§l自动箱子 · 使用说明"},
            {"§e§l▌ 使用方法",
                    "§f  1. 先用「ID识别」或指令 §e.id 物品§f 添加目标物品ID",
                    "§f  2. 在设置页选择运行模式（玩家控制/寻路/标点）",
                    "§f  · 开启后自动处理容器，取走目标物品"},
            {"§a§l▌ 三种模式",
                    "§f  · 玩家控制模式：玩家自己走，进入触发距离自动处理",
                    "§f  · 寻路模式：自动扫描并寻路到容器面前安全站位",
                    "§f  · 标点模式：只处理 .autochest 保存的点位"},
            {"§d§l▌ 保护机制",
                    "§f  · 目标锁：同一容器同时只处理一次",
                    "§f  · 多人保护：他人正在用箱不抢，临时跳过",
                    "§f  · 有限重试 + 临时冷却：失败不无限卡箱"},
            {"§c§l▌ 注意",
                    "§f  · 目标物品来自 ID 配置管理，本模块不建独立物品库",
                    "§f  · 后台挂机不抢鼠标/焦点，走客户端内部 API"}
    };

    private static final List<String> SCAN_MODE_LABELS = List.of(
            ScanMode.PLAYER_CONTROL.displayName(), ScanMode.PATHING.displayName(), ScanMode.MARKER.displayName());
    private static final List<String> WITHDRAW_MODE_LABELS = List.of(
            WithdrawMode.TARGET_COUNT.displayName(), WithdrawMode.TARGET_EMPTY.displayName(),
            WithdrawMode.TAKE_ALL.displayName());
    private static final List<String> ESP_STYLE_LABELS = List.of(
            EspStyle.LINES.displayName(), EspStyle.SIDES.displayName(), EspStyle.BOTH.displayName());

    /** 「重置」按钮图标：Material Symbols refresh（旧项目此处是图标按钮，不是文字按钮） */
    private static final String GLYPH_RESET = "\uE5D5";

    private static final float GROUP_HEIGHT = 26f;
    private static final float GROUP_SIZE = 12f;
    private static final float TEXT_HEIGHT = 22f;
    private static final float COUNT_WIDTH = 240f;
    private static final float FOOTER_GAP_HEIGHT = 8f;
    private static final float DIVIDER_HEIGHT = 13f;

    private final AutoChestModule module;
    private final AutoChestSettings settings;

    public AutoChestPage(AutoChestModule module) {
        this.module = module;
        this.settings = module.settings();
        build();
    }

    @Override
    public BasePage createPage(ModuleEntry entry) {
        return this;
    }

    @Override
    public String getTitle() {
        return module.displayName();
    }

    @Override
    public String getSubtitle() {
        return module.description();
    }

    @Override
    public void update(float dt) {
        // 调色板窗口直接改颜色载体，关闭后把 RGB 与透明度写回设置项并落盘
        module.syncColorsToSettings();
        super.update(dt);
    }

    // ── 构建 ──

    private void build() {
        setHeader(new ModuleStatusBar(
                () -> module.isEnabled() ? "运行中" : "未启用",
                module::isEnabled,
                new KeybindBadge(module.keybindId()),
                new SettingToggle(module::isEnabled,
                        value -> ModuleManager.setEnabled(module.id(), value))));

        buildMarkerActions();
        buildSettings();
        buildHelp();
    }

    /**
     * 头部动作区（旧项目面板头部）：标点管理（含处理记录清除）仅「标点模式」出现。
     *
     * <p>点位卡片与空态按页面构建时的当前维度点位生成，整体挂在标点模式的可见性条件下，
     * 运行模式在页面内切换时与旧项目 {@code screen.reload()} 等效。</p>
     */
    private void buildMarkerActions() {
        BooleanSupplier markerMode = () -> settings.scanMode == ScanMode.MARKER;

        addCore(visible(markerMode, new ButtonRow(new Button("设置箱子点位（对准容器）", () -> {
            module.addPointFromCrosshair();
            closeToGame();
        }))));

        List<ChestTarget> points = module.pointStore().pointsInCurrentDimension();
        if (points.isEmpty()) {
            addCore(visible(markerMode, new TextLine("§8当前维度暂无箱子点位").height(TEXT_HEIGHT)));
        } else {
            addCore(visible(markerMode, divider()));
            for (ChestTarget point : points) {
                addCore(visible(markerMode, pointCard(point)));
            }
        }

        addCore(visible(markerMode, divider()));
        addCore(visible(markerMode, new ButtonRow(new Button("清空全部点位", () ->
                confirm("清空点位", "确定要清空全部标点吗？此操作不可恢复。", module::clearAllPoints)))));

        // 处理记录清除：与标点管理同属标点模式专属动作，非标点模式不出现
        addCore(visible(markerMode, new ButtonRow(new Button("清除当前维度处理记录", () ->
                confirm("清除记录", "确定清除当前维度的已处理记录吗？", module::clearCurrentDimensionRecords)))));
        addCore(visible(markerMode, new ButtonRow(new Button("清除全部处理记录", () ->
                confirm("清除记录", "确定清除全部服务器的已处理记录吗？", module::clearAllRecords)))));
    }

    /** 单个箱子点位卡片行：容器类型 + 坐标 + 维度 + 处理状态 + 删除按钮 */
    private CompactElement pointCard(ChestTarget point) {
        ContainerType type = ContainerTypeRegistry.byId(point.containerType());
        String typeName = type == null
                ? "未知容器（类型 ID：" + point.containerType() + "）"
                : type.displayName();
        String dim = WorldIdentity.dimensionDisplayName(point.dimension());
        long expireMs = settings.recordExpireMinutes * 60_000L;
        // 处理状态在页面构建时判定一次（与旧项目 buildPointCard 一致），避免逐帧触碰记录存储
        String status = module.recordStore().isProcessed(
                point.pos(), point.dimension(), point.containerType(), expireMs)
                ? "§c已处理" : "§a未处理";

        return new ListRow(() -> "§b■ §f" + typeName)
                .detail(() -> AutoChestModule.formatCoords(
                        point.pos().getX(), point.pos().getY(), point.pos().getZ())
                        + " §8▸ §7维度 §8▸ §f" + dim)
                .badge(() -> status, -1)
                .action(new Button("§c删除", () -> {
                    module.deletePoint(point);
                    closeToGame();
                }));
    }

    /** 8 个设置分组、21 个设置项，顺序与可见性条件全部照旧项目（Meteor 按分组渲染顺序） */
    private void buildSettings() {
        // ── 运行模式 ──
        addCore(group("运行模式"));
        addCore(new CompactRow("运行模式", () -> DESC_SCAN_MODE,
                new SettingSegmented(SCAN_MODE_LABELS,
                        () -> settings.scanMode.ordinal(), this::pickScanMode)));
        addCore(new CompactRow("当前模式", () -> DESC_CURRENT_MODE,
                new SettingText(() -> "§a§l" + settings.scanMode.displayName())));

        // ── 玩家控制模式 ──
        addCore(group("玩家控制模式"));
        addCore(visible(() -> settings.scanMode == ScanMode.PLAYER_CONTROL,
                new CompactRow("触发距离", () -> DESC_TRIGGER_DISTANCE,
                        intBox(1, 4, () -> settings.triggerDistance, value -> settings.triggerDistance = value))));

        // ── 标点模式 ──
        addCore(group("标点模式"));
        addCore(visible(() -> settings.scanMode == ScanMode.MARKER,
                new CompactRow("标点管理", () -> DESC_MARKER, null)));
        addCore(visible(() -> settings.scanMode == ScanMode.MARKER,
                new TextLine(MARKER_HINT).height(TEXT_HEIGHT)));

        // ── 容器 ──
        addCore(group("容器"));
        addCore(new CompactRow("容器类型", () -> DESC_CONTAINER_TYPES,
                new Button("选择容器类型", () -> openScreen(new ContainerTypePage(currentScreen(), module)))));
        addCore(ButtonRow.split(new SettingText(this::containerTypeCountText, COUNT_WIDTH).alignLeft(),
                resetButton(() -> {
                    settings.resetContainerTypes();
                    module.persistSettings();
                })));
        addCore(new CompactRow("检测范围", () -> DESC_SCAN_RADIUS,
                intBox(4, Integer.MAX_VALUE, () -> settings.scanRadius, value -> settings.scanRadius = value)));
        addCore(new CompactRow("扫描周期", () -> DESC_SCAN_INTERVAL,
                intBox(1, Integer.MAX_VALUE, () -> settings.scanInterval, value -> settings.scanInterval = value)));
        addCore(new CompactRow("已处理记录过期", () -> DESC_RECORD_EXPIRE,
                intBox(0, Integer.MAX_VALUE, () -> settings.recordExpireMinutes,
                        value -> settings.recordExpireMinutes = value)));

        // ── 保护 ──
        addCore(group("保护"));
        addCore(new CompactRow("多人保护", () -> DESC_MULTIPLAYER_PROTECT,
                toggle(() -> settings.multiplayerProtect, value -> settings.multiplayerProtect = value)));
        addCore(visible(() -> settings.multiplayerProtect,
                new CompactRow("玩家检测距离", () -> DESC_PLAYER_DISTANCE,
                        intBox(1, Integer.MAX_VALUE, () -> settings.playerDetectDistance,
                                value -> settings.playerDetectDistance = value))));
        addCore(new CompactRow("最大重试次数", () -> DESC_MAX_RETRIES,
                intBox(1, Integer.MAX_VALUE, () -> settings.maxRetries, value -> settings.maxRetries = value)));
        addCore(new CompactRow("临时冷却", () -> DESC_COOLDOWN,
                intBox(20, Integer.MAX_VALUE, () -> settings.cooldownTicks, value -> settings.cooldownTicks = value)));

        // ── 目标物品（数据源唯一来自 ID 配置管理） ──
        addCore(group("目标物品"));
        addCore(visible(this::needsTargetItems,
                new CompactRow("目标物品", () -> DESC_TARGET_ITEMS,
                        new Button("选择目标物品", this::openTargetSelector))));
        addCore(visible(this::needsTargetItems,
                ButtonRow.split(new SettingText(this::targetItemsCountText, COUNT_WIDTH).alignLeft(),
                        resetButton(IdentityTargetConfig::reset))));

        // ── 取物 ──
        addCore(group("取物"));
        addCore(new CompactRow("取物模式", () -> DESC_WITHDRAW_MODE,
                new SettingSegmented(WITHDRAW_MODE_LABELS,
                        () -> settings.withdrawMode.ordinal(), this::pickWithdrawMode)));
        addCore(visible(() -> settings.withdrawMode == WithdrawMode.TARGET_COUNT,
                new CompactRow("每种物品数量", () -> DESC_QUANTITY,
                        new Button("配置每种物品数量", () -> openScreen(new ItemQuantityPage(currentScreen(), module))))));
        addCore(visible(() -> settings.withdrawMode == WithdrawMode.TARGET_COUNT,
                ButtonRow.split(new SettingText(this::quantityCountText, COUNT_WIDTH).alignLeft(),
                        resetButton(() -> {
                            settings.resetQuantities();
                            module.persistSettings();
                        }))));
        addCore(new CompactRow("动作延迟", () -> DESC_ACTION_DELAY,
                intBox(1, Integer.MAX_VALUE, () -> settings.actionDelay, value -> settings.actionDelay = value)));

        // ── 渲染 ──
        addCore(group("渲染"));
        addCore(new CompactRow("ESP高亮", () -> DESC_RENDER_ESP,
                toggle(() -> settings.renderEsp, value -> settings.renderEsp = value)));
        addCore(visible(() -> settings.renderEsp,
                new CompactRow("ESP框样式", () -> DESC_ESP_STYLE,
                        new SettingSegmented(ESP_STYLE_LABELS,
                                () -> settings.espStyle.ordinal(), this::pickEspStyle))));
        addCore(visible(() -> settings.renderEsp,
                colorRow("未处理颜色", () -> DESC_UNPROCESSED_COLOR, module.unprocessedColor())));
        addCore(visible(() -> settings.renderEsp,
                colorRow("已处理颜色", () -> DESC_PROCESSED_COLOR, module.processedColor())));
        addCore(visible(() -> settings.renderEsp,
                colorRow("处理中颜色", () -> DESC_PROCESSING_COLOR, module.processingColor())));
    }

    /** 使用说明章节（旧项目内嵌在模块面板中，本项目落在页面底部；章节之间保留空行） */
    private void buildHelp() {
        for (int section = 0; section < SECTIONS.length; section++) {
            if (section > 0) addFooter(new TextLine(" ").height(FOOTER_GAP_HEIGHT));
            for (String line : SECTIONS[section]) {
                addFooter(new TextLine(line).height(TEXT_HEIGHT));
            }
        }
    }

    // ── 控件构造 ──

    /** 分组标题：只显示旧项目分组名原文，不添加任何前缀字符 */
    private TextLine group(String name) {
        return new TextLine(name).height(GROUP_HEIGHT).size(GROUP_SIZE).bold(true);
    }

    private CompactRow colorRow(String name, Supplier<String> hint, EspColor color) {
        return new CompactRow(name, hint, new SettingColorPicker(name, color));
    }

    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return new SettingToggle(getter, value -> {
            setter.accept(value);
            module.persistSettings();
        });
    }

    /**
     * 整数设置框：步进 1。
     *
     * <p>旧项目无可配置上限的整数项（检测范围 / 扫描周期 / 已处理记录过期 / 玩家检测距离 /
     * 最大重试次数 / 临时冷却 / 动作延迟）用 {@link Integer#MAX_VALUE} 作为上限，
     * 保持与旧项目相同的取值范围。</p>
     */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, IntConsumer setter) {
        return new SettingNumberBox(min, max, 1, "%.0f",
                () -> (double) getter.get(),
                value -> {
                    setter.accept((int) Math.round(value));
                    module.persistSettings();
                });
    }

    private IconButton resetButton(Runnable action) {
        return new IconButton(GLYPH_RESET, action);
    }

    private void pickScanMode(int index) {
        settings.scanMode = enumAt(ScanMode.values(), index, settings.scanMode);
        module.persistSettings();
    }

    private void pickWithdrawMode(int index) {
        settings.withdrawMode = enumAt(WithdrawMode.values(), index, settings.withdrawMode);
        module.persistSettings();
    }

    private void pickEspStyle(int index) {
        settings.espStyle = enumAt(EspStyle.values(), index, settings.espStyle);
        module.persistSettings();
    }

    private static <E> E enumAt(E[] values, int index, E fallback) {
        return index < 0 || index >= values.length ? fallback : values[index];
    }

    // ── 交互 ──

    private boolean needsTargetItems() {
        return settings.withdrawMode != WithdrawMode.TAKE_ALL;
    }

    /** 打开「目标物品」选择器：数据源唯一来自 ID 配置管理，改动即时生效（无确定按钮） */
    private void openTargetSelector() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;

        List<SelectorScreen.Entry> entries = new ArrayList<>();
        List<ItemIdentity> custom = new ArrayList<>();
        for (ItemIdentity identity : IdentityService.shared().allItems()) {
            if (identity.isVanilla()) entries.add(new ItemEntry(identity, true));
            else custom.add(identity);
        }
        for (ItemIdentity identity : custom) entries.add(new ItemEntry(identity, false));

        client.setScreen(new SelectorScreen("目标物品", client.screen, entries,
                () -> new ArrayList<>(IdentityTargetConfig.selectedItemKeys()),
                key -> IdentityTargetConfig.setItemSelected(key, true),
                key -> IdentityTargetConfig.setItemSelected(key, false)));
    }

    private void confirm(String title, String message, Runnable action) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new ConfirmPanelScreen(title, List.of(message), "§c§l确认", action, client.screen));
    }

    private void openScreen(Screen screen) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || screen == null) return;
        client.setScreen(screen);
    }

    /** 上级屏幕（独立窗口关闭后回到本页面） */
    private Screen currentScreen() {
        Minecraft client = Minecraft.getInstance();
        return client == null ? null : client.screen;
    }

    /** 执行后直接回到游戏（旧项目 {@code mc.setScreen(null)}） */
    private void closeToGame() {
        Minecraft client = Minecraft.getInstance();
        if (client != null) client.setScreen(null);
    }

    // ── 计数标签（旧项目逐字） ──

    /** 容器类型：{@code 已选 N / M 类} */
    private String containerTypeCountText() {
        return "已选 " + settings.containerTypeIds.size() + " / "
                + ContainerTypeRegistry.all().size() + " 类";
    }

    /** 目标物品：{@code 未选择目标（共 N 项）} / {@code 已选 X / N 项} */
    private String targetItemsCountText() {
        int total = IdentityService.shared().itemCount();
        int selected = IdentityTargetConfig.selectedItemKeys().size();
        if (selected == 0) return "未选择目标（共 " + total + " 项）";
        return "已选 " + selected + " / " + total + " 项";
    }

    /** 每种物品数量：{@code 已配置 X / Y 项} */
    private String quantityCountText() {
        return "已配置 " + settings.configuredQuantityCount() + " / "
                + IdentityTargetConfig.selectedItemKeys().size() + " 项";
    }

    // ── 组合元素 ──

    /** 条件元素：不满足可见性条件时高度为 0，绘制与命中全部跳过 */
    private static CompactElement visible(BooleanSupplier condition, CompactElement inner) {
        return new CompactElement() {
            @Override
            public float height() {
                return condition.getAsBoolean() ? inner.height() : 0f;
            }

            @Override
            public void update(float dt) {
                inner.update(dt);
            }

            @Override
            public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
                if (condition.getAsBoolean()) inner.draw(canvas, x, y, width, alpha, mouseX, mouseY);
            }

            @Override
            public boolean onClick(float mx, float my, float x, float y, float width, int button) {
                return condition.getAsBoolean() && inner.onClick(mx, my, x, y, width, button);
            }

            @Override
            public boolean onDrag(float mx, float my, float x, float y, float width) {
                return condition.getAsBoolean() && inner.onDrag(mx, my, x, y, width);
            }
        };
    }

    /** 水平分隔线（旧项目面板头部用它把标点区与清空区分开） */
    private static CompactElement divider() {
        return new CompactElement() {
            @Override
            public float height() {
                return DIVIDER_HEIGHT;
            }

            @Override
            public void update(float dt) {
            }

            @Override
            public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
                GlassPanel.divider(canvas, x + 6f, y + DIVIDER_HEIGHT / 2f, width - 12f,
                        ClickGuiThemeColors.current().separator, alpha);
            }

            @Override
            public boolean onClick(float mx, float my, float x, float y, float width, int button) {
                return false;
            }

            @Override
            public boolean onDrag(float mx, float my, float x, float y, float width) {
                return false;
            }
        };
    }

    /** 目标选择器条目：原版物品能取到贴图，自定义物品回退纯文字 */
    private static final class ItemEntry implements SelectorScreen.Entry {

        private final ItemIdentity identity;
        private final boolean vanilla;

        private ItemEntry(ItemIdentity identity, boolean vanilla) {
            this.identity = identity;
            this.vanilla = vanilla;
        }

        @Override
        public String key() {
            return identity.identityKey();
        }

        @Override
        public String title() {
            return "§a" + identity.displayName();
        }

        @Override
        public String group() {
            return vanilla ? "§a§l▌ 原版物品" : "§d§l▌ 自定义物品";
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            // 原版 / 自定义都按「真实载体物品」取贴图：自定义物品的底层仍是某个注册表物品
            // （如沙子、纸），取不到才回退纯文字，不再按原版/自定义一刀切不画。
            Item item = itemById(identity.itemId());
            return item != null && ItemIconCache.getInstance().draw(canvas, item.getDefaultInstance(), x, y, size);
        }

        private static Item itemById(String itemId) {
            Identifier id = Identifier.tryParse(itemId);
            if (id == null) return null;
            Item item = BuiltInRegistries.ITEM.getValue(id);
            return item == null || item == Items.AIR ? null : item;
        }
    }
}
