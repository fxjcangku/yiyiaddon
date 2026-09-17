package com.yiyiaddon.feature.autochest.ui;

import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.Minecraft;

/**
 * 自动箱子模块页：只留入口与使用说明，**全部设置项与点位管理都在控制台里**。
 *
 * <p><b>形态照星露谷模块页</b>（{@code StardewResourcePanelPage}；用户 2026-09-16 指令
 * 「自动箱子也做成控制台样式」）：顶部信息块 → 控制台入口 → 使用说明正文。
 * 模块的 8 个设置分组共 21 项（{@code 运行模式 / 玩家控制模式 / 标点模式 / 容器 / 保护 /
 * 目标物品 / 取物 / 渲染}）**只在 {@link AutoChestConsoleScreen} 里出现一次**，本页不再平铺。</p>
 *
 * <p><b>标点区不在本页</b>（用户 2026-09-17：「控制台里面已经有点位了 为什么控制台外面还有」，
 * 同批口径已应用于村民交易与自动农场）：旧项目面板头部的标点管理（{@code 设置箱子点位（对准容器）} /
 * 点位卡片 / {@code 清空全部点位} / 两个处理记录清除）与点位卡片，在本项目里统一由控制台「点位」页
 * （{@code AutoChestPointPage}）承载。原先模块页的 {@code pointCard} 与控制台点位页的 {@code pointRow}
 * 是同一份信息的**两套渲染代码**，本次收敛为控制台那一套，同源信息不再两处维护（第 169 条）。
 * 按钮文案、卡片文案、空态与三个二次确认窗原文一字未改，只是承载位置收敛到控制台。</p>
 *
 * <p><b>使用说明内嵌</b>（用户 2026-09-17 口径：不再单摆「查看使用说明」按钮）：正文直接铺在
 * 「打开控制台」入口下方，超出时由模块页自身滚动条上下查看；章节正文一字未改，
 * 只去掉独立窗口的外框三行（见 {@link HelpPanelScreen#inlineContent}）。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）：</b>标点区四个按钮文案、点位卡片六段文案、
 * 空态 {@code §8当前维度暂无箱子点位}、三个二次确认窗标题与正文、帮助 4 个章节全部正文。</p>
 */
public final class AutoChestPage extends CompactModulePage implements ModulePage {

    // ── 入口按钮文案（与星露谷 / 自动挖矿模块页同构） ──

    private static final String CONSOLE_BUTTON = "§b打开控制台";
    private static final String CONSOLE_HINT =
        "按用途分页：概览 / 点位 / 运行模式 / 容器 / 保护 / 取物 / 渲染";

    /** 使用说明章节：旧项目 {@code AutoChestModule.buildSections()} 逐字（首行总标题由窗口标题承载，不再重复） */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("§e§l▌ 使用方法",
            "§f  1. 先用「ID识别」或指令 §e.id 物品§f 添加目标物品ID",
            "§f  2. 在控制台「运行模式」页选择运行模式（玩家控制/寻路/标点）",
            "§f  · 开启后自动处理容器，取走目标物品"),
        new HelpPanelScreen.HelpSection("§a§l▌ 三种模式",
            "§f  · 玩家控制模式：玩家自己走，进入触发距离自动处理",
            "§f  · 寻路模式：自动扫描并寻路到容器面前安全站位",
            "§f  · 标点模式：只处理 .autochest 添加的点位"),
        new HelpPanelScreen.HelpSection("§d§l▌ 保护机制",
            "§f  · 目标锁：同一容器同时只处理一次",
            "§f  · 多人保护：他人正在用箱不抢，临时跳过",
            "§f  · 有限重试 + 临时冷却：失败不无限卡箱"),
        new HelpPanelScreen.HelpSection("§c§l▌ 注意",
            "§f  · 目标物品来自 ID 配置管理，本模块不建独立物品库",
            "§f  · 后台挂机不抢鼠标/焦点，走客户端内部 API")
    };

    /** 内嵌说明行（去掉窗口外框三行）：章节标题与正文逐字保留，超出时由模块页滚动条上下查看 */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

    private final AutoChestModule module;

    /** 页面内容是否已构建（{@code module.page()} 会在模组初始化阶段被调用一次，见 {@link #createPage}） */
    private boolean built;

    public AutoChestPage(AutoChestModule module) {
        this.module = module;
    }

    /**
     * 页面内容在「真正打开页面」时才构建。
     *
     * <p>时序口径保留：{@code module.page()} 会在模组初始化阶段被调用一次（{@code ModuleEntries.of}
     * 用它判空），那时不碰任何游戏状态与磁盘；本页已不含点位卡片，但这条时序约定继续遵守，
     * 与其它模块页保持同一形态。</p>
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
        // ① 控制台入口（星露谷同款：提示行 + 居中按钮）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // ② 使用说明内嵌在控制台入口下方（用户 2026-09-17 口径），章节正文逐字不变
        for (String line : HELP_LINES) addCore(new TextLine(line));

        // ③ 标点区不在这里：点位列表、设置点位与记录清除统一由控制台「点位」页承载（见类注释）
    }

    // ── 界面跳转 ──

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new AutoChestConsoleScreen(client.screen, module));
    }
}
