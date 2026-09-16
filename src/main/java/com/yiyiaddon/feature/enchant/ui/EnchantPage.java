package com.yiyiaddon.feature.enchant.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.enchant.EnchantModule;
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
 * 自动附魔模块页：② 打开控制台 → ④ 查看使用说明（顺序照开发习惯第 182 条，不可颠倒）。
 *
 * <p>全部设置项与点位按钮都在控制台里（按用途分页），本页只留两个入口，不再平铺任何设置分组 ——
 * 两份控件写同一份 {@code EnchantSettings}，同页并存只会互相看对方为旧值。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）：</b>按钮 {@code §b打开控制台} / {@code §e查看使用说明}，
 * 以及帮助页 8 个章节的全部正文，全部来自旧项目 {@code AutoEnchantBook.buildHelpContent()}（{@code :773-847}）。
 * 说明正文里的「装备箱」「重设六个点位」等与实现不一致之处照抄原文，不做「修正」。</p>
 */
public final class EnchantPage extends CompactModulePage implements ModulePage {

    // ── 入口按钮文案（逐字） ──

    private static final String CONSOLE_BUTTON = "§b打开控制台";
    private static final String CONSOLE_HINT = "按用途分页：概览 / 点位 / 基础设置 / 模式专属设置";
    private static final String HELP_BUTTON = "§e查看使用说明";
    private static final String HELP_HINT = "打开自动附魔的完整使用说明";

    /** 帮助页 8 个章节（旧 {@code buildHelpContent :773-847} 逐字；框线与 {@code [#]} 由 HelpPanelScreen 生成） */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("首次配置",
            "  §8├─ §f先在要使用的服务器和维度执行 §e.fumo 清空 §7(清空旧点位)",
            "  §8├─ §f准星对准对应方块，点击下方卡片「设置」按钮依次绑定：",
            "  §8│    §7书 / 青晶石 / 成品箱 / 附魔台 / 砂轮",
            "  §8│    §7原版装备模式：装备箱 / 青晶石 / 附魔台 / 砂轮 / 铁砧 / 铁砧箱 / 成品箱",
            "  §8├─ §f挂机循环模式站在刷怪点调好杀怪视角，再绑定「挂机位」 §7(纯附魔模式不需要)",
            "  §8├─ §f在自动附魔分类或原版附魔分类勾选要收集的目标词条",
            "  §8├─ §f把带「横扫之刃」的剑放背包或快捷栏任意位置",
            "  §8└─ §f书箱放空白书、青金石箱放青金石、成品箱预留空间"
        ),
        new HelpPanelScreen.HelpSection("点位设置 §7(两种方式)",
            "  §b▸ §e方式1 §8- §f配置页面卡片按钮",
            "    §7准星对准方块 §8→ §f点击对应卡片「设置」",
            "    §7书/青晶石/成品箱：必须是箱子/桶/潜影盒",
            "    §7附魔台/砂轮：必须对准对应方块",
            "    §7挂机位：直接站在目标位置即可绑定 §7(含视角)",
            "",
            "  §b▸ §e方式2 §8- §f指令系统",
            "    §8> §3.fumo 设置 <节点> §8— §7节点：书/青晶石/成品箱/附魔台/砂轮/挂机位/装备箱/铁砧/铁砧箱",
            "    §8> §3.fumo 移除 <节点> §8— §7删除单个点位",
            "    §8> §3.fumo 状态 §8— §7查看坐标与当前地点匹配",
            "    §8> §3.fumo 清空 §8— §7清空全部点位"
        ),
        new HelpPanelScreen.HelpSection("运行流程",
            "  §a▸ §f物资不足时自动前往对应补给箱取书或青金石",
            "  §a▸ §f经验不足时，挂机循环返回挂机位；纯附魔模式低于 30 级停止",
            "  §a▸ §f横扫之刃剑在主背包时自动换入当前快捷栏并选中",
            "  §a▸ §f达到目标等级后自动前往附魔台执行 30 级附魔",
            "  §a▸ §f未命中目标词条时前往砂轮洗练，再继续下一次附魔",
            "  §a▸ §f命中目标词条时播放提示音并将附魔书存入成品箱"
        ),
        new HelpPanelScreen.HelpSection("原版装备附魔",
            "  §b▸ §f从装备箱取目标装备 → 附魔台随机附魔 → 评分判定",
            "  §b▸ §f零命中/禁止/互斥 → 砂轮洗练后重新附魔",
            "  §b▸ §f部分命中 → 保留，多件互补时铁砧合并升级",
            "  §b▸ §f合并后 100% 达标 → 存入成品箱，达到「极品附魔数量」自动停机",
            "  §b▸ §f铁砧损坏 → 自动去铁砧箱取备用铁砧原位补放"
        ),
        new HelpPanelScreen.HelpSection("参数说明",
            "  §6▸ §e单轮抽取次数 §f— 附魔书/自定义模式每轮计划执行的附魔次数",
            "  §6▸ §eGUI操作延迟 §f— 服务器卡顿或吞点击时适当调大",
            "  §6▸ §e发包打开距离 §f— 发包开箱/开附魔台/开砂轮/开铁砧的最大距离，超出先寻路靠近",
            "  §6▸ §e书本/青金石补给组数 §f— 每次补给希望保有的组数",
            "  §6▸ §e每批取用数量 §f— 原版装备模式每次从装备箱取用的目标装备数量",
            "  §6▸ §e极品附魔数量 §f— 原版装备模式最终产出的极品装备数量，达标自动停机",
            "  §6▸ §e运行模式 §f— 每个目标模式独立开关：纯附魔只消耗当前经验、不足停机；挂机循环自动补经验",
            "  §6▸ §e成功提示音 §f— 命中目标词条时播放所选音效"
        ),
        new HelpPanelScreen.HelpSection("原版附魔分类",
            "  §7· 仅收录普通书通过附魔台随机附魔可获得的词条",
            "  §7· 每种词条只提供附魔台实际能刷出的最高等级",
            "  §7· 锋利/效率/力量等书本附魔最高 IV，不显示无法直接刷出的 V",
            "  §7· 不含经验修补、冰霜行者、灵魂疾行、迅捷潜行和诅咒"
        ),
        new HelpPanelScreen.HelpSection("换服、换维度与指令",
            "  §d▸ §f一套点位只能用于设置它时所在的服务器和维度",
            "  §d▸ §f去其他服务器/维度时旧点位不会运行，也不会乱跑",
            "  §d▸ §f新地点使用：先 §e.fumo 清空§f，再重设六个点位",
            "  §d▸ §f回原服务器/维度时旧点位可直接使用，不必重设"
        ),
        new HelpPanelScreen.HelpSection("注意",
            "  §c⚠ §f单人世界与服务器均可使用",
            "  §c⚠ §f至少勾选一个目标词条，否则模块不启动",
            "  §c⚠ §f成品箱满、书箱/青金石箱空时会提示并自动停机",
            "  §c⚠ §f模块运行中无法修改点位，先关闭模块再设置"
        )
    };

    private final EnchantModule module;

    /** 页面内容是否已构建（见 {@link #createPage(ModuleEntry)} 的时序说明） */
    private boolean built;

    public EnchantPage(EnchantModule module) {
        this.module = module;
    }

    /**
     * 页面内容在「真正打开页面」时才构建。
     *
     * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次（{@code ModuleEntries}
     * 用它判空）。控制台要显示磁盘上的真实点位，而点位装载只在自检 / 启用时发生 —— 玩家可能先开页面
     * 配点位，因此推迟到打开页面时再读盘（开发习惯第 180 条）。</p>
     */
    @Override
    public BasePage createPage(ModuleEntry entry) {
        if (!built) {
            built = true;
            module.pointStore().reload();
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

        // ① 控制台入口（提示行 + 居中按钮）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // ② 使用说明（顺序：入口在前、说明在后，开发习惯第 182 条）
        addCore(new CompactRow("", () -> HELP_HINT,
            new Button(HELP_BUTTON, this::openHelp)).centeredControl());
    }

    // ── 界面跳转 ──

    /** 打开使用说明：窗口标题为「自动附魔 - 使用说明」，底部按钮「关闭」 */
    private void openHelp() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new HelpPanelScreen(EnchantModule.MESSAGE_MODULE,
            HelpPanelScreen.buildHelpContent(HELP_SECTIONS), client.screen));
    }

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        // 控制台「点位」页要显示磁盘上的真实绑定，打开前重读一次（点位写入全部即时落盘，重读不丢数据）
        module.pointStore().reload();
        client.setScreen(new EnchantConsoleScreen(client.screen, module));
    }
}
