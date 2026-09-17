package com.yiyiaddon.feature.villager.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.villager.AutoVillagerTradeModule;
import com.yiyiaddon.feature.villager.config.VillagerTradeSettings;
import com.yiyiaddon.feature.villager.ui.console.VillagerConsoleScreen;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.PointCardGrid;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.Minecraft;

/**
 * 自动村民交易模块页：旧配置页的元素一个不少 —— 使用说明按钮（旧 {@code :693-695}）、多任务模式说明行
 * （旧 {@code :699-700} 逐字）、两张点位卡片（旧 {@code :704-713}），外加本项目新增的控制台入口
 * （第 182 条：入口必须排在「查看使用说明」上面）与一行状态摘要。
 *
 * <p><b>元素顺序（与旧行号对位）</b></p>
 * <ol>
 *   <li>顶部信息块 {@link ModuleStatusBar}（本项目统一壳件，等价旧框架的模块标题行 / 启用开关）；</li>
 *   <li>状态行 {@link TextLine}：状态机状态中文名 + 模式 / 职业 / 价格上限（旧页面无此行，形态照
 *       {@code AutoFarmPage} 的 {@code TextLine} 摘要与 {@code AutoFarmModule.statusSummary}）；</li>
 *   <li>控制台入口 {@link CompactRow} + {@code §b打开控制台}（本项目新增壳件，文案与形态照
 *       {@code WaterPage} / {@code AutoFarmPage}）；</li>
 *   <li>使用说明 {@link CompactRow} + {@code §e查看使用说明}（旧 {@code :693} 文案逐字，
 *       点击弹 {@link HelpPanelScreen}，标题由它拼成「自动村民交易 - 使用说明」）；</li>
 *   <li>多任务模式说明行（旧 {@code :699-700} 逐字，全宽一行）；</li>
 *   <li>两张点位卡片 {@link PointCardGrid.Grid}（旧 {@code :704-713} 两列布局，卡片本体走
 *       {@link VillagerPointCards}，与控制台「点位」页共用同一份实现）。</li>
 * </ol>
 *
 * <p><b>状态行的中文名来源</b>：旧项目状态机 {@code State} 枚举（旧 {@code :1178-1197}）没有中文名，
 * 旧模块页也没有状态行；中文名逐条取自旧项目自己的用词（状态枚举注释、状态日志与帮助正文），
 * 现已收敛到枚举上这一处：{@link com.yiyiaddon.feature.villager.fsm.VillagerTradeState#cn()}，
 * 取词依据逐条写在该枚举的类 javadoc 里，控制台状态条与本页共用同一份，不再各写一张表（第 169 条）。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次（{@code ModuleEntries} 用它判空），
 * 构造函数里不碰任何游戏注册表与配置文件；页面内容推迟到 {@link #createPage(ModuleEntry)} 构建（第 180 条）。
 * 点位卡要显示磁盘上的真实绑定，仓库的 {@code ensureLoaded()} 会在首次访问时按当前服务器读盘。</p>
 */
public final class AutoVillagerTradePage extends CompactModulePage implements ModulePage {

    // ── 按钮文案与提示（逐字 / 既有口径） ──

    /** 控制台入口按钮（逐字照既有模块页口径） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 控制台入口提示：村民交易控制台承载内容（不虚构页签名） */
    private static final String CONSOLE_HINT = "整屏承载全部设置项、两张点位卡与运行日志";
    /** 使用说明按钮（旧 {@code :693} 逐字） */
    private static final String HELP_BUTTON = "§e查看使用说明";
    /** 使用说明提示（与其它模块页同形：模块名替换） */
    private static final String HELP_HINT = "打开自动村民交易的完整使用说明";

    /**
     * 多任务模式说明行（旧 {@code :699-700} 两段字符串拼接后逐字）。
     *
     * <p>原文跨两行书写，这里合成一行常量，字符一字未改。</p>
     */
    private static final String MULTI_TASK_LINE =
        "§7多任务模式 = 依次执行所有「已选择物品」的职业，顺序为 盔甲匠→…→武器匠，完成一个再下一个";

    private final AutoVillagerTradeModule module;

    /** 页面内容是否已构建（第 180 条时序约束，参照 {@code AutoFarmPage}） */
    private boolean built;

    public AutoVillagerTradePage(AutoVillagerTradeModule module) {
        this.module = module;
    }

    /** 页面内容在「真正打开页面」时才构建（第 180 条；此时读盘、建卡都不影响模组初始化） */
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

    // ── 构建（顺序见类注释的对位表） ──

    private void build() {
        setHeader(new ModuleStatusBar(
            () -> module.isEnabled() ? "运行中" : "未启用",
            module::isEnabled,
            new KeybindBadge(module.keybindId()),
            new SettingToggle(module::isEnabled,
                value -> ModuleManager.setEnabled(module.id(), value))));

        // 状态摘要（TextLine 现读，未启用时给灰字）
        addCore(new TextLine(this::statusLine));

        // 控制台入口（第 182 条：排在「查看使用说明」之前）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // 使用说明（旧 :693-695）
        addCore(new CompactRow("", () -> HELP_HINT,
            new Button(HELP_BUTTON, this::openHelp)).centeredControl());

        // 多任务模式说明（旧 :699-700）
        addCore(new TextLine(MULTI_TASK_LINE));

        // 两张点位卡（旧 :704-713；与控制台「点位」页共用 VillagerPointCards）
        addCore(new PointCardGrid.Grid(VillagerPointCards.all()));
    }

    // ── 状态摘要 ──

    /**
     * 状态行文本：{@code §7当前状态 §8▸ <中文状态> §8│ §7模式 §8▸ … §8│ §7职业 §8▸ … §8│ §7价格上限 §8▸ ≤N}。
     *
     * <p><b>为什么这样拼</b>：旧模块页没有状态行，新项目每个模块页都要一眼看清「在干什么、按什么参数干」，
     * 于是照 {@code AutoFarmModule.statusSummary} 既有的「{@code §7标签 §8▸ 值} + {@code §8│} 分隔」口径，
     * 取状态机当前状态与三项关键参数（模式 / 职业 / 价格上限，取值口径同旧启动报告
     * {@code AutoVillagerTradeModule.announceStartup}）。未启用时状态给灰字「未启用」，
     * 其余参数照旧显示设置值（玩家常在未启用时核对参数）。</p>
     */
    private String statusLine() {
        VillagerTradeSettings settings = module.settings();
        String state = module.isEnabled()
            ? "§f" + module.fsm().getCurrentState().cn()
            : "§8未启用";
        return "§7当前状态 §8▸ " + state
            + " §8│ §7模式 §8▸ §f" + settings.mode
            + " §8│ §7职业 §8▸ §f" + settings.profession.name()
            + " §8│ §7价格上限 §8▸ §f≤" + settings.priceLimit(settings.profession.name());
    }

    // ── 界面跳转 ──

    /** 打开使用说明（旧 {@code :694}）：窗口标题为「自动村民交易 - 使用说明」 */
    private void openHelp() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new HelpPanelScreen(AutoVillagerTradeModule.MESSAGE_MODULE,
            HelpPanelScreen.buildHelpContent(AutoVillagerTradeHelpContent.SECTIONS), client.screen));
    }

    /** 打开控制台（整屏分页）；ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new VillagerConsoleScreen(module));
    }
}
