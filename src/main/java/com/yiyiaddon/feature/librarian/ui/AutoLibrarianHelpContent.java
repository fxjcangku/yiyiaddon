package com.yiyiaddon.feature.librarian.ui;

import com.yiyiaddon.ui.screen.HelpPanelScreen;

/**
 * 自动图书管理员使用说明五章节（旧 {@code AutoLibrarianModule.buildHelpContent :89-118} 逐字全文，
 * 含全部 {@code §} 颜色码、{@code §8├─} / {@code §8└─} 树形符号、{@code §a[N]} 编号、
 * {@code §7▸} / {@code §6▸} 要点与 {@code §c⚠} 警告）。
 *
 * <p>框线标题（{@code ┏━┓ / ┃ 使用说明 ┃ / ┗━┛}）与章节标头 {@code §3[§b#§3] §f标题} 由
 * {@link HelpPanelScreen#buildHelpContent} 统一生成，本类只承载正文行，不自己拼框线
 * （第 137 条）。说明正文内嵌在模块页（第 210 条），不再单摆「查看使用说明」按钮。</p>
 *
 * <p>章节标题逐字：{@code 准备 / 自动流程 / 静默交易 / 场地要求 / 注意}。</p>
 */
public final class AutoLibrarianHelpContent {

    /** 五章节（模块页与控制台「概览」页共用同一份数据，禁止各写一份） */
    public static final HelpPanelScreen.HelpSection[] SECTIONS = {
        new HelpPanelScreen.HelpSection("准备",
            "  §8├─ §f背包携带：讲台 × N、书 × N、绿宝石 × 足够数量",
            "  §8├─ §f在「目标附魔」中设置想要的附魔类型",
            "  §8├─ §f站在已搭建好的岩浆块工位阵列附近",
            "  §8└─ §f开启模块即自动运行"
        ),
        new HelpPanelScreen.HelpSection("自动流程",
            "  §a[1] §f在搜索半径内寻找失业村民",
            "  §a[2] §f识别岩浆块工位，清除障碍方块",
            "  §a[3] §f放置讲台，等待村民接受图书管理员职业",
            "  §a[4] §f静默读取交易列表（不打开界面），命中目标附魔则自动发包购买",
            "  §a[5] §f未命中则拆除讲台，刷新村民交易，重复循环"
        ),
        new HelpPanelScreen.HelpSection("静默交易",
            "  §7▸ §f交易全程不打开交易界面，发包静默完成",
            "  §7▸ §f命中目标附魔后自动选中并领取附魔书"
        ),
        new HelpPanelScreen.HelpSection("场地要求",
            "  §6▸ §f每个村民工位：岩浆块 + 相邻空地（用于放置讲台）",
            "  §6▸ §f支持活版门卡位场地（村民无法逃跑）",
            "  §6▸ §f安装 Baritone 可自动寻路移动到村民位置"
        ),
        new HelpPanelScreen.HelpSection("注意",
            "  §c⚠ §f背包缺少讲台/书/绿宝石时模块会报错并自动关闭",
            "  §c⚠ §f绿宝石价格超过「最大价格」上限的交易会跳过"
        )
    };

    private AutoLibrarianHelpContent() {
    }
}
