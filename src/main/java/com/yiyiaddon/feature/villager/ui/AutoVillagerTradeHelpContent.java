package com.yiyiaddon.feature.villager.ui;

import com.yiyiaddon.ui.screen.HelpPanelScreen;

/**
 * 自动村民交易使用说明七章节（旧 {@code AutoVillagerTradeModule.buildHelpContent :721-765} 逐字全文，
 * 含全部 {@code §} 颜色码、{@code §8> §eN§8.} 编号、{@code §8├─} / {@code §8└─} 树形符号、
 * {@code §b▸} 要点、{@code §c⚠} 警告与 {@code §d▸} 提示）。
 *
 * <p>框线标题（{@code ┏━┓ / ┃ 使用说明 ┃ / ┗━┛}）与章节标头 {@code §3[§b#§3] §f标题} 由
 * {@link HelpPanelScreen#buildHelpContent} 统一生成，本类只承载正文行，不自己拼框线
 * （开发习惯第 137 条）。</p>
 *
 * <p>章节标题逐字：{@code 模块定位 / 三种模式 / 默认榨干 §7(已内置，无需开关) / 使用流程 /
 * 点位设置 §7(三种模式通用) / 状态反馈 / 注意事项}。</p>
 */
public final class AutoVillagerTradeHelpContent {

    /** 七章节（帮助窗口与控制台「概览」页共用同一份数据，禁止各写一份） */
    public static final HelpPanelScreen.HelpSection[] SECTIONS = {
        new HelpPanelScreen.HelpSection("模块定位",
            "  §8├─ §f交易通过真实打开村民交易界面发包 §7(26.1.2 协议，无静默交易)",
            "  §8├─ §f面向固定村民交易所：村民提前手动解锁至大师级",
            "  §8└─ §f自检通过才能启动，缺项一次性列全"
        ),
        new HelpPanelScreen.HelpSection("三种模式",
            "  §b▸ §e原地交易 §8- §f不寻路工作站，直接与身边村民交易",
            "    §7绿宝石不足/背包满时自动去箱子补给/卸货",
            "  §b▸ §e寻路单点 §8- §fBaritone 寻路到工作站自动交易",
            "    §7绿宝石不足自动去绿宝石箱补给，背包满自动卸货",
            "  §b▸ §e多任务 §8- §f所有已选择物品的职业组成队列依次执行",
            "    §7顺序 盔甲匠→…→武器匠，完成 1 再做 2，可为不同村民"
        ),
        new HelpPanelScreen.HelpSection("默认榨干 §7(已内置，无需开关)",
            "  §d▸ §f交易默认就是榨干模式，一路买到目标交易全部「售罄/锁死」才收工",
            "  §d▸ §f补给卸货循环照常，直到目标交易榨干/锁死为止",
            "  §d▸ §f买不到、村民消失或绿宝石箱也空了才停"
        ),
        new HelpPanelScreen.HelpSection("使用流程",
            "  §8> §e1§8. §f选择模式、目标职业、目标物品",
            "  §8> §e2§8. §f图书管理员可同时勾选附魔书 §7(自动忽略等级)",
            "  §8> §e3§8. §f设置价格上限，可调绿宝石补给量",
            "  §8> §e4§8. §f先绑定绿宝石箱与成品交易箱再开模块",
            "  §8> §e5§8. §f自检通过即开始；快速停止键可随时终止"
        ),
        new HelpPanelScreen.HelpSection("点位设置 §7(三种模式通用)",
            "  §8> §3.cunmin 设置 绿宝石箱 §8— §7准星对准箱子绑定",
            "  §8> §3.cunmin 设置 成品交易箱 §8— §7准星对准箱子绑定",
            "  §8> §3.cunmin 状态 §8— §7查看绑定状态",
            "  §8> §3.cunmin 移除 绿宝石箱 §8— §7解绑",
            "  §7§o也可直接点击配置页底部卡片中的「设置」按钮"
        ),
        new HelpPanelScreen.HelpSection("状态反馈",
            "  §a✓ §f每笔交易成功播报 + 村民交易提示音",
            "  §e✗ §f确认失败自动重发 1 次，连败自动跳过",
            "  §7启动播报：模式/职业/物品/价格/总量，一目了然"
        ),
        new HelpPanelScreen.HelpSection("注意事项",
            "  §c⚠ §f原地模式交易距离仅约 3 格，请站在村民旁边",
            "  §c⚠ §f多任务模式至少给一个职业选择物品，否则无法启动"
        )
    };

    private AutoVillagerTradeHelpContent() {
    }
}
