package com.yiyiaddon.feature.autochest.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.model.autochest.ContainerType;
import com.yiyiaddon.model.autochest.ContainerTypeRegistry;
import com.yiyiaddon.model.autochest.ScanMode;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.platform.world.WorldIdentity;
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
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * 自动箱子模块页：只留入口与标点区，**全部设置项都在控制台里**。
 *
 * <p><b>形态照星露谷模块页</b>（{@code StardewResourcePanelPage}；用户 2026-09-16 指令
 * 「自动箱子也做成控制台样式」）：顶部信息块 → 控制台入口 → 使用说明 → 旧项目本页原有的标点区。
 * 模块的 8 个设置分组共 21 项（{@code 运行模式 / 玩家控制模式 / 标点模式 / 容器 / 保护 /
 * 目标物品 / 取物 / 渲染}）**只在 {@link AutoChestConsoleScreen} 里出现一次**，本页不再平铺。</p>
 *
 * <p><b>使用说明改为独立窗口</b>：旧项目规约（{@code YiyiaddonConvention §使用说明窗口规范}）
 * 要求「所有模块的使用说明统一使用 HelpScreen 独立窗口、设置面板顶部必须有 §e 黄色
 * 「查看使用说明」按钮」，本模块旧实现是内嵌在面板底部的 —— 这里回到规约形态，
 * 章节正文一字未改（转成 {@link HelpPanelScreen.HelpSection} 标准格式，标题框与
 * {@code [#]} 编号由窗口统一生成）。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）：</b>标点区四个按钮文案、点位卡片六段文案、
 * 空态 {@code §8当前维度暂无箱子点位}、三个二次确认窗标题与正文、帮助页 4 个章节全部正文。</p>
 *
 * <p><b>标点区照旧**整体**受「标点模式」约束</b>：旧项目用 {@code visible(markerMode, …)} 包裹
 * 头部动作区与点位卡片，非标点模式下本页不显示这些内容（控制台「点位」页同理）。</p>
 */
public final class AutoChestPage extends CompactModulePage implements ModulePage {

    // ── 入口按钮文案（与星露谷 / 自动挖矿模块页同构） ──

    private static final String CONSOLE_BUTTON = "§b打开控制台";
    private static final String CONSOLE_HINT =
        "按用途分页：概览 / 点位 / 运行模式 / 容器 / 保护 / 取物 / 渲染";
    private static final String HELP_BUTTON = "§e查看使用说明";
    private static final String HELP_HINT = "打开自动箱子的完整使用说明";

    /** 使用说明章节：旧项目 {@code AutoChestModule.buildSections()} 逐字（首行总标题由窗口标题承载，不再重复） */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("§e§l▌ 使用方法",
            "§f  1. 先用「ID识别」或指令 §e.id 物品§f 添加目标物品ID",
            "§f  2. 在设置页选择运行模式（玩家控制/寻路/标点）",
            "§f  · 开启后自动处理容器，取走目标物品"),
        new HelpPanelScreen.HelpSection("§a§l▌ 三种模式",
            "§f  · 玩家控制模式：玩家自己走，进入触发距离自动处理",
            "§f  · 寻路模式：自动扫描并寻路到容器面前安全站位",
            "§f  · 标点模式：只处理 .autochest 保存的点位"),
        new HelpPanelScreen.HelpSection("§d§l▌ 保护机制",
            "§f  · 目标锁：同一容器同时只处理一次",
            "§f  · 多人保护：他人正在用箱不抢，临时跳过",
            "§f  · 有限重试 + 临时冷却：失败不无限卡箱"),
        new HelpPanelScreen.HelpSection("§c§l▌ 注意",
            "§f  · 目标物品来自 ID 配置管理，本模块不建独立物品库",
            "§f  · 后台挂机不抢鼠标/焦点，走客户端内部 API")
    };

    private static final float TEXT_HEIGHT = 22f;
    private static final float DIVIDER_HEIGHT = 13f;

    private final AutoChestModule module;
    private final AutoChestSettings settings;

    /** 页面内容是否已构建（{@code module.page()} 会在模组初始化阶段被调用一次，见 {@link #createPage}） */
    private boolean built;

    public AutoChestPage(AutoChestModule module) {
        this.module = module;
        this.settings = module == null ? null : module.settings();
    }

    /**
     * 页面内容在「真正打开页面」时才构建。
     *
     * <p>{@code module.page()} 会在模组初始化阶段被调用一次（{@code ModuleEntries.of} 用它判空），
     * 那时不能碰点位存储 —— 点位卡片要显示磁盘上的真实绑定，推迟到打开页面时构建。</p>
     */
    @Override
    public BasePage createPage(ModuleEntry entry) {
        if (!built) {
            built = true;
            build();
        }
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

    // ── 构建 ──

    private void build() {
        setHeader(new ModuleStatusBar(
                () -> module.isEnabled() ? "运行中" : "未启用",
                module::isEnabled,
                new KeybindBadge(module.keybindId()),
                new SettingToggle(module::isEnabled,
                        value -> ModuleManager.setEnabled(module.id(), value))));

        // ① 控制台入口（星露谷同款：提示行 + 居中按钮）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // ② 使用说明：独立窗口（旧项目规约 §使用说明窗口规范）
        addCore(new CompactRow("", () -> HELP_HINT,
            new Button(HELP_BUTTON, this::openHelp)).centeredControl());

        // ③ 标点区（旧项目面板头部：标点管理 + 处理记录清除，整体仅标点模式出现）
        buildMarkerActions();
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

    // ── 界面跳转 ──

    /** 打开使用说明（独立窗口，窗口标题 {@code 自动箱子 - 使用说明}） */
    private void openHelp() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new HelpPanelScreen(AutoChestModule.MESSAGE_MODULE,
            HelpPanelScreen.buildHelpContent(HELP_SECTIONS), client.screen));
    }

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new AutoChestConsoleScreen(client.screen, module));
    }

    private void confirm(String title, String message, Runnable action) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new ConfirmPanelScreen(title, List.of(message), "§c§l确认", action, client.screen));
    }

    /** 执行后直接回到游戏（旧项目 {@code mc.setScreen(null)}） */
    private void closeToGame() {
        Minecraft client = Minecraft.getInstance();
        if (client != null) client.setScreen(null);
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
}
