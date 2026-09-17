package com.yiyiaddon.feature.water.ui;

import com.yiyiaddon.ui.screen.HelpPanelScreen;

/**
 * 水源显示使用说明五章节：**全部由本模块已有的中文界面文案组装，一个字都没有新写**
 * （用户 2026-09-17：「水源提示没有使用说明 杀截也是」→ 拍板「用现有设置文案组装」）。
 *
 * <p><b>为什么是组装而不是搬运</b>：旧项目 {@code water/} 包只有 {@code WaterESPModule} 一个文件，
 * 该类没有覆写 {@code getWidget}、没有 {@code HelpScreen} 调用，全库也没有任何水源功能的使用说明文本
 * （逐字机对无源可对）——所以本说明的每一行都必须能指回本项目的既有点，不引入任何新句子。</p>
 *
 * <p><b>逐行出处（本模块单一承载界面 {@link WaterConsoleScreen}，行号为 2026-09-17 原文）</b></p>
 * <ul>
 *   <li>章节标题：{@code 扫描半径 / 渲染距离} 由两个设置项名直接拼接（这两项在旧项目默认组里无组名，
 *       本页同样平铺，见 {@code WaterConsoleScreen:81} 的注释）；{@code 灌溉范围显示} /
 *       {@code 建议放水点} / {@code 高级选项} = 三个分组名逐字（{@code :90 / :97 / :106}）；
 *       {@code 水源显示控制台} = 控制台窗口标题逐字（{@code :54}）。</li>
 *   <li>设置项名称与说明：{@code 扫描半径 :82}、{@code 渲染距离 :85}、{@code 显示灌溉范围 :91}、
 *       {@code 范围颜色 :93}、{@code 显示建议点 :98}、{@code 建议点颜色 :100}、
 *       {@code 建议点样式 :101}、{@code 显示水源方块 :107}、{@code 水源方块颜色 :110}、
 *       {@code 水源方块样式 :111}（名称与描述均逐字，含 {@code Lines 线框 / Sides 面 / Both 两者}）。</li>
 *   <li>行尾提示：{@code §7点击色块打开调色板} = {@code ConsoleWidgets.COMMENT_COLOR} 逐字，
 *       {@code §7点击切换} = {@code ConsoleWidgets.COMMENT_CYCLE} 逐字（说明里出现的词与界面上看到的完全一致）。</li>
 *   <li>{@code §b打开控制台} = 模块页入口按钮文案逐字（{@code WaterPage:28}）；
 *       {@code §7刷新} 及其说明「按最新设置值重排本页」= 控制台页脚按钮与悬停说明逐字（{@code :117-118}）；
 *       {@code §7关闭} = 页脚按钮逐字（{@code :119}）。</li>
 * </ul>
 *
 * <p><b>只加框、不加词</b>：章节标头 {@code §3[§b#§3] §f标题} 与窗框由
 * {@link HelpPanelScreen#buildHelpContent} 统一生成，正文行只沿用本项目既有的
 * {@code §8├─} / {@code §8└─} 树形符号与 {@code §8-} 分隔符（与
 * {@code AutoVillagerTradeHelpContent} 同一套排版），未出现任何自拟描述句。</p>
 *
 * <p><b>维护口径（第 212 条）</b>：控制台新增 / 改名 / 改描述任何一项设置时，本类必须同步改，
 * 两处文案不一致即视为缺陷。</p>
 */
public final class WaterHelpContent {

    /** 五章节（模块页内嵌说明的唯一数据源，禁止在页面里再拼一份） */
    public static final HelpPanelScreen.HelpSection[] SECTIONS = {
        new HelpPanelScreen.HelpSection("扫描半径 / 渲染距离",
            "  §8├─ §f扫描半径 §8- §7扫描玩家周围多少格内的水源",
            "  §8└─ §f渲染距离 §8- §7只渲染玩家周围多少格内的框（可以比扫描半径大）"
        ),
        new HelpPanelScreen.HelpSection("灌溉范围显示",
            "  §8├─ §f显示灌溉范围 §8- §7显示每桶水能覆盖的 9×9 耕地范围（蓝色大框）",
            "  §8└─ §f范围颜色 §8- §7灌溉范围的颜色 §8· §7点击色块打开调色板"
        ),
        new HelpPanelScreen.HelpSection("建议放水点",
            "  §8├─ §f显示建议点 §8- §7在已有水源的上下左右显示可放水位置（红色框）",
            "  §8├─ §f建议点颜色 §8- §7建议放水点的颜色 §8· §7点击色块打开调色板",
            "  §8└─ §f建议点样式 §8- §7Lines 线框 / Sides 面 / Both 两者 §8· §7点击切换"
        ),
        new HelpPanelScreen.HelpSection("高级选项",
            "  §8├─ §f显示水源方块 §8- §7用小框标出水源方块本身（通常不需要，主要看灌溉范围即可）",
            "  §8├─ §f水源方块颜色 §8· §7点击色块打开调色板",
            "  §8└─ §f水源方块样式 §8- §7Lines 线框 / Sides 面 / Both 两者 §8· §7点击切换"
        ),
        new HelpPanelScreen.HelpSection("水源显示控制台",
            "  §8├─ §b打开控制台",
            "  §8├─ §7刷新 §8- §7按最新设置值重排本页",
            "  §8└─ §7关闭"
        )
    };

    private WaterHelpContent() {
    }
}
