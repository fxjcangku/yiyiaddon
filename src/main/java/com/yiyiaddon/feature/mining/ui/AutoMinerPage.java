package com.yiyiaddon.feature.mining.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.Minecraft;

/**
 * 自动挖矿模块页：只留控制台入口与使用说明，**全部设置与点位都在控制台里**。
 *
 * <p><b>形态照星露谷模块页</b>（{@code StardewResourcePanelPage}，用户 2026-09-16 指令
 * 「跟星露谷物语天差地别…已经做进控制台了下面还一堆设置」）：顶部信息块 → 控制台入口 → 使用说明。
 * 模块的 5 个设置分组（{@code 目标选择 / 传送指令 / 触发条件 / 物品管理 / Baritone调优}）
 * 与三个点位卡片**只在控制台出现一次**，本页不再平铺 —— 两份控件写同一份 {@code MiningSettings}，
 * 同页并存只会互相看对方为旧值。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）：</b>按钮 {@code §b打开控制台} / {@code §e查看使用说明}、
 * 帮助页 8 个章节的全部正文，来自旧项目原文（{@code AutoMinerModule.getWidget} +
 * {@code buildHelpContent}，{@code :1503-1683}）。</p>
 *
 * <p><b>点位绑定：</b>控制台点位页与 {@code .wk} 指令共用唯一实现
 * {@link com.yiyiaddon.feature.mining.service.MiningBindingService}（GUI 路径的文案多「请重新设置」、
 * 解绑回执带 {@code §c§l✗}，与指令路径两套并存，禁止统一）。</p>
 *
 * <p><b>与旧项目差异（登记）：</b>{@code 检测假矿} 按钮<b>不落地</b>（随种子模式留白，用户裁定）；
 * 帮助页「指令系统」章节里 {@code §8> §3.wk 检测假矿…} 那一行按同一裁定留白不写，但「种子挖矿」
 * 章节标题与逐字正文作为帮助文案资产完整保留。用户 2026-09-16 指令「没打开控制台之前的那个界面的
 * 点位删掉」，故本页原有的三行点位卡片已删除，「准备工作」「点位设置」两节帮助文案里指向旧卡片的
 * 句子同步改写成指向控制台点位页。</p>
 */
public final class AutoMinerPage extends CompactModulePage implements ModulePage {

    // ── 入口按钮文案（逐字） ──

    private static final String CONSOLE_BUTTON = "§b打开控制台";
    private static final String CONSOLE_HINT = "按用途分页：概览 / 点位 / 目标选择 / 传送指令 / 触发条件 / Baritone调优";
    private static final String HELP_BUTTON = "§e查看使用说明";
    private static final String HELP_HINT = "打开自动挖矿的完整使用说明";

    /** 帮助页 8 个章节（旧 {@code buildHelpContent :1618-1683} 逐字；框线与 {@code [#]} 格式由 HelpPanelScreen 生成） */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("准备工作",
            "  §8├─ §f准备好挖矿工具 §7(推荐附魔耐久、效率)",
            "  §8├─ §f准备好武器 §7(修补耐久时用)",
            "  §8├─ §f放置矿物箱、食物箱 §7(装满食物)",
            "  §8├─ §f选好挂机修复点 §7(安全区域，怪物可到达)",
            "  §8└─ §f在控制台「点位」页设置三个点位"
        ),
        new HelpPanelScreen.HelpSection("点位设置 §7(两种方式)",
            "  §b▸ §e方式1 §8- §f控制台点位页",
            "    §7准星对准箱子 §8→ §f点击「设置」按钮",
            "    §7箱子类型：矿物箱、食物箱自动检测容器",
            "    §7挂机修复点：直接站在目标位置即可绑定",
            "",
            "  §b▸ §e方式2 §8- §f指令系统",
            "    §8> §3.wk 设置 矿物箱 §8— §7准星对准箱子，绑定矿物贮箱",
            "    §8> §3.wk 设置 食物箱 §8— §7准星对准箱子，绑定食物补给箱",
            "    §8> §3.wk 设置 挂机修复点 §8— §7站在目标位置后自动绑定 §7(含视角)",
            "",
            "  §7§o容器检测：箱子类点位会自动检测目标方块是否为容器",
            "  §7§o不是容器 §8→ §7自动拒绝并提示重新设置，避免卡死"
        ),
        new HelpPanelScreen.HelpSection("指令系统",
            "  §8> §3.wk 状态 §8— §7查看绑定状态 §7(含坐标、维度、视角)",
            "  §8> §3.wk 移除 §c<目标> §8— §7解绑单个坐标",
            "  §8> §3.wk 清空 §8— §7清空所有绑定"
        ),
        new HelpPanelScreen.HelpSection("状态机流程",
            "  §a[1] §f前往挖矿 §8→ §7发送挖矿指令，等区块加载完成",
            "  §a[2] §f采掘 §8→ §7Baritone自动挖矿，满载/饿/耐久触发转换",
            "  §a[3] §f卸货循环 §8→ §7传送到矿物箱，卸货，返回野外",
            "  §a[4] §f补给循环 §8→ §7传送到箱，拿食物，吃饱，返回"
        ),
        new HelpPanelScreen.HelpSection("物品管理 §7(默认全丢)",
            "  §c▸ §f丢弃逻辑：除保留项外，背包其余物品全部自动丢弃",
            "  §a▸ §f默认保留：任意品质工具 §7(镐/铲/斧/剑/锄)§f、白名单食物、目标矿物",
            "  §a▸ §f搭路方块 §7(圆石/地狱岩) §f只保留各一组，多余自动丢弃",
            "  §e▸ §f保留白名单：不想被扔的物品/方块加进去就不会丢",
            "  §6⚠ §f启动前记得把想留的东西加进「保留白名单」"
        ),
        new HelpPanelScreen.HelpSection("参数建议",
            "  §6▸ §f满载组数 §8= §e36 §7(标准背包容量)",
            "  §6▸ §f食物阈值 §8= §e14 §7(7格肉约14饱食度)",
            "  §6▸ §f耐久阈值 §8= §e50 §7(低于50时自动修复)",
            "  §6▸ §f传送等待 §8= §e10秒 §7(RTP加载缓冲)"
        ),
        new HelpPanelScreen.HelpSection("种子挖矿 §7(可选)",
            "  §d▸ §f启用后状态机切换采集流程 §7(两种模式)",
            "    §7普通模式：Baritone mine 挖视野内所有目标矿",
            "    §7种子模式：逐块寻路到预测真矿，原版合法破坏，无视假矿",
            "  §d▸ §f预测位置无矿自动跳过，附近挖完自动重新RTP换区",
            "  §d▸ §f检测假矿：对准可疑方块 §8→ §f点击「检测假矿」按钮",
            "  §d▸ §f假矿判定：预测无矿但显示有矿 §8= §c假矿",
            "  §d▸ §f适用场景：防止挖到管理员放置的诱饵矿"
        ),
        new HelpPanelScreen.HelpSection("注意事项",
            "  §c⚠ §f模块运行中无法修改点位，必须先关闭模块",
            "  §c⚠ §f已绑定点位不允许覆盖，必须先删除再重新设置",
            "  §c⚠ §f传送指令需服务器支持，否则无法自动返回",
            "  §c⚠ §f挂机修复点会记录视角，用于精准对准修补工作台",
            "  §c⚠ §f默认全丢垃圾！想留下的物品务必先加进「保留白名单」"
        )
    };

    private final AutoMinerModule module;

    /** 页面内容是否已构建（见 {@link #createPage(ModuleEntry)} 的时序说明） */
    private boolean built;

    public AutoMinerPage(AutoMinerModule module) {
        this.module = module;
    }

    /**
     * 页面内容在「真正打开页面」时才构建。
     *
     * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次
     * （{@code ModuleEntries.of} 用它判空，见 {@code ModuleEntries:35}）。点位卡片要显示磁盘上的
     * 真实绑定，而点位装载只在自检 / 启用时发生 —— 玩家可能先进页面配点位，因此这里先
     * {@link AutoMinerModule#reloadStore()} 再构建；推迟到打开页面时也避免在初始化阶段读盘。</p>
     */
    @Override
    public BasePage createPage(ModuleEntry entry) {
        if (!built) {
            built = true;
            module.reloadStore();
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

        // ② 使用说明（与上一行同一形态；「检测假矿」按钮随种子模式留白，不落地）
        addCore(new CompactRow("", () -> HELP_HINT,
            new Button(HELP_BUTTON, this::openHelp)).centeredControl());
    }

    // ── 界面跳转 ──

    /** 打开使用说明（旧 {@code :1509-1511}）：窗口标题为「自动挖矿 - 使用说明」 */
    private void openHelp() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new HelpPanelScreen(AutoMinerModule.MESSAGE_MODULE,
            HelpPanelScreen.buildHelpContent(HELP_SECTIONS), client.screen));
    }

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new MiningConsoleScreen(client.screen, module));
    }
}
