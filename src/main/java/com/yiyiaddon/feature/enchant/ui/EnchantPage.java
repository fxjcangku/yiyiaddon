package com.yiyiaddon.feature.enchant.ui;

import com.yiyiaddon.feature.enchant.EnchantModule;
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
 * 自动附魔模块页：② 打开控制台 → 使用说明正文内嵌在入口下方（用户 2026-09-17 口径）。
 *
 * <p>全部设置项与点位按钮都在控制台里（按用途分页），本页只留控制台一个入口 + 紧随其后的说明正文，
 * 不再平铺任何设置分组 —— 两份控件写同一份 {@code EnchantSettings}，同页并存只会互相看对方为旧值。
 * 说明超出一屏时由本页自身的滚动条上下查看。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）：</b>按钮 {@code §b打开控制台}，
 * 以及帮助页 8 个章节的全部正文，全部来自旧项目 {@code AutoEnchantBook.buildHelpContent()}（{@code :773-847}）。
 * 说明正文按旧原文照抄，例外只有两处（用户 2026-09-17 口径「使用说明必须是最新的」）：
 * <ol>
 *   <li>「点击<b>下方卡片</b>／<b>配置页面</b>卡片按钮」→「控制台「点位」页卡片」：
 *       本项目点位卡在控制台「点位」页（{@code EnchantPointPage}），模块页已无卡片，旧措辞会指到不存在的元素；</li>
 *   <li>{@code .fumo 设置} 的节点清单里「装备箱」→「工具护甲箱」：指令字面量取自
 *       {@code EnchantPointType#node()}，其中并没有「装备箱」这个节点，照抄会让玩家照着打却报错。
 *       （正文里的「装备箱」「重设六个点位」等描述性措辞仍照抄原文 —— 前者的实现侧显示名同样是「装备箱」，
 *       后者指纯附魔模式的六个点位，均与实现一致。）</li>
 *   <li>「首次配置」第 1 行与「换服、换维度与指令」整节：点位已改为<b>按服务器分文件</b>
 *       （2026-09-21 用户定稿），旧原文「先执行 .fumo 清空 再重设六个点位」会教玩家做多余操作，
 *       故改写为「换服直接设点、不必先清空」。</li>
 * </ol></p>
 */
public final class EnchantPage extends CompactModulePage implements ModulePage {

    // ── 入口按钮文案（逐字） ──

    private static final String CONSOLE_BUTTON = "§b打开控制台";
    private static final String CONSOLE_HINT = "按用途分页：概览 / 点位 / 基础设置 / 模式专属设置";

    /** 帮助页 8 个章节（旧 {@code buildHelpContent :773-847} 逐字；框线与 {@code [#]} 由 HelpPanelScreen 生成） */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("首次配置",
            "  §8├─ §f点位按服务器和维度分别记录，换服 / 换维度直接设点，不必先清空",
            "  §8├─ §f准星对准对应方块，到控制台「点位」页点卡片「设置」按钮依次绑定：",
            "  §8│    §7书 / 青晶石 / 成品箱 / 附魔台 / 砂轮",
            "  §8│    §7原版装备模式：装备箱 / 青晶石 / 附魔台 / 砂轮 / 铁砧 / 铁砧箱 / 成品箱",
            "  §8├─ §f挂机循环模式站在刷怪点调好杀怪视角，再绑定「挂机位」 §7(纯附魔模式不需要)",
            "  §8├─ §f在自动附魔分类或原版附魔分类勾选要收集的目标词条",
            "  §8├─ §f把带「横扫之刃」的剑放背包或快捷栏任意位置",
            "  §8└─ §f书箱放空白书、青金石箱放青金石、成品箱预留空间"
        ),
        new HelpPanelScreen.HelpSection("点位设置 §7(两种方式)",
            "  §b▸ §e方式1 §8- §f控制台「点位」页卡片按钮",
            "    §7准星对准方块 §8→ §f点击对应卡片「设置」",
            "    §7书/青晶石/成品箱：必须是箱子/桶/潜影盒",
            "    §7附魔台/砂轮：必须对准对应方块",
            "    §7挂机位：直接站在目标位置即可绑定 §7(含视角)",
            "",
            "  §b▸ §e方式2 §8- §f指令系统",
            "    §8> §3.fumo 设置 <节点> §8— §7节点：书/青晶石/成品箱/附魔台/砂轮/挂机位/工具护甲箱/铁砧/铁砧箱",
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
            "  §d▸ §f每个服务器 / 存档各存一套点位，互不覆盖",
            "  §d▸ §f去其他服务器时旧点位不会运行，也不会乱跑，直接设新点位即可",
            "  §d▸ §f同一套点位只用于设置它时所在的维度，换维度不生效，先切回该维度使用",
            "  §d▸ §f回原服务器 / 维度时旧点位可直接使用，不必重设"
        ),
        new HelpPanelScreen.HelpSection("注意",
            "  §c⚠ §f单人世界与服务器均可使用",
            "  §c⚠ §f至少勾选一个目标词条，否则模块不启动",
            "  §c⚠ §f成品箱满、书箱/青金石箱空时会提示并自动停机",
            "  §c⚠ §f模块运行中无法修改点位，先关闭模块再设置"
        )
    };

    /**
     * 内嵌说明行（去掉窗口外框三行）：本页把使用说明直接铺在「打开控制台」入口下方
     * （用户 2026-09-17 口径：不再单摆「查看使用说明」按钮，超出可上下滚动查看）。
     *
     * <p>声明在 {@link #HELP_SECTIONS} 之后，静态初始化顺序才保证读到的不是 null。</p>
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

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
        // ① 控制台入口（提示行 + 居中按钮）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // ② 使用说明内嵌在控制台入口下方（用户 2026-09-17 口径），章节标题与正文逐字不变
        for (String line : HELP_LINES) addCore(new TextLine(line));
    }

    // ── 界面跳转 ──

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        // 控制台「点位」页要显示磁盘上的真实绑定，打开前重读一次（点位写入全部即时落盘，重读不丢数据）
        module.pointStore().reload();
        client.gui.setScreen(new EnchantConsoleScreen(client.gui.screen(), module));
    }
}
