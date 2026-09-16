package com.yiyiaddon.feature.autofarm.ui;

import com.yiyiaddon.ui.screen.HelpPanelScreen;

/**
 * 自动农场使用说明七章节（旧 {@code AutoFarmMatrix.buildHelpContent :803-853} 逐字全文，
 * 含 {@code §} 颜色码与符号；框线标题与章节标头由 {@link HelpPanelScreen} 统一生成）。
 *
 * <p>章节标题逐字：{@code 准备工作 / 点位设置 §7(指令) / 作物选择 / 单种子独立配置 /
 * 收割模式 / 工作流程 / 注意事项}。</p>
 */
public final class AutoFarmHelpContent {

    /** 七章节（帮助窗口与控制台「概览」页共用同一份数据，禁止各写一份） */
    public static final HelpPanelScreen.HelpSection[] SECTIONS = {
        new HelpPanelScreen.HelpSection("准备工作",
            "  §8├─ §f建好农田 §7(耕地或对应底盘)",
            "  §8├─ §f用 .farm 设置 农场点位1 / 农场点位2 框出矩形范围",
            "  §8├─ §f智能绑定单作物箱/种子补货箱/多作物箱 §7(单物品→单箱，双物品→种子+多箱)",
            "  §8└─ §f绑定杂物箱 §7(独立处理毒马铃薯与仙人掌花)"
        ),
        new HelpPanelScreen.HelpSection("点位设置 §7(指令)",
            "  §8> §3.farm 设置 农场点位1 §8— §7准星对准农田对角起点",
            "  §8> §3.farm 设置 农场点位2 §8— §7准星对准农田对角终点",
            "  §8> §3.farm 设置 单作物箱 §8— §7准星对准箱子(单物品作物)",
            "  §8> §3.farm 设置 种子补货箱 §8— §7准星对准箱子(双物品种子)",
            "  §8> §3.farm 设置 多作物箱 §8— §7准星对准箱子(双物品成熟掉落物)",
            "  §8> §3.farm 设置 杂物箱 §8— §7准星对准箱子",
            "  §8> §3.farm 扩展 4 §8— §7面朝方向把农田向外扩 4 格(东/南/西/北)"
        ),
        new HelpPanelScreen.HelpSection("作物选择",
            "  §a▸ §f最多同时启用 3 种作物",
            "  §a▸ §f普通作物 §8- §7小麦、胡萝卜、马铃薯、甜菜根、下界疣",
            "  §a▸ §f柱状物 §8- §7竹子、甘蔗、仙人掌",
            "  §a▸ §f果实 §8- §7南瓜、西瓜",
            "  §c▸ §f不支持 §7海带、甜浆果"
        ),
        new HelpPanelScreen.HelpSection("单种子独立配置",
            "  §a▸ §f启用几种作物 §8- §7面板就自动显示几种独立配置",
            "  §a▸ §f卸货数量 §8- §7该作物产物超过此组数才卸入作物箱",
            "  §a▸ §f补货种子数量 §8- §7种植材料低于此组数自动补货",
            "  §e▸ §f双物品成熟掉落物卸入多作物箱 §8· §7其余卸入单作物箱"
        ),
        new HelpPanelScreen.HelpSection("收割模式",
            "  §a▸ §f单个收割 §8- §7每次只收一个成熟目标，收完补种拾取后再观察",
            "  §a▸ §f批量收割 §8- §7一次性锁定全部成熟目标，瞬间暴力破坏",
            "  §e▸ §f批量收割无数量上限 §8- §7默认无限量智能收割智能补种"
        ),
        new HelpPanelScreen.HelpSection("工作流程",
            "  §a[0] §f锄地 §8→ §7范围内草方块/泥土自动锄成耕地",
            "  §a[1] §f观察 §8→ §7分帧扫描农场，发现成熟目标",
            "  §a[2] §f收割 §8→ §7熟一颗收一颗，验证后接补种/拾取",
            "  §a[3] §f补种 §8→ §7需要补种的作物自动播种",
            "  §a[4] §f拾取 §8→ §7就近等待掉落物进入背包",
            "  §a[5] §f物流 §8→ §7卸货/补货/杂物独立处理"
        ),
        new HelpPanelScreen.HelpSection("注意事项",
            "  §c⚠ §f模块运行中无法修改点位，必须先关闭模块",
            "  §c⚠ §f已绑定点位不允许覆盖，必须先删除再重新设置",
            "  §c⚠ §f自动寻路依赖 Baritone，不可用时移动任务会失败并重新规划",
            "  §c⚠ §f关闭模块立即停止，重新开启会重新观察"
        )
    };

    private AutoFarmHelpContent() {
    }
}
