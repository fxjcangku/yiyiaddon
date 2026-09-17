package com.yiyiaddon.feature.combat.ui;

import com.yiyiaddon.feature.combat.config.KillAuraTexts;
import com.yiyiaddon.ui.screen.HelpPanelScreen;

/**
 * 杀戮光环使用说明五章节：**全部由本模块已有的中文界面文案组装，一个字都没有新写**
 * （用户 2026-09-17：「水源提示没有使用说明 杀截也是」→ 拍板「用现有设置文案组装」）。
 *
 * <p><b>为什么是组装而不是搬运</b>：杀戮光环在旧项目里是 Meteor 原生模块（旧项目只有汉化词条
 * {@code module.kill-aura.*} 与混淆映射，没有本模组的源文件，也没有 {@code HelpScreen} 调用），
 * 26 项设置的中文名与描述是本项目自己落的中文文案（{@link KillAuraTexts} 类注释：
 * 蓝本 GUI 显示英文 {@code Utils.nameToTitle}，中文为用户 2026-09-16 拍板新增）——
 * 说明只能由这批既有文案组装，不引入任何自拟描述句。</p>
 *
 * <p><b>逐行出处（本模块唯一承载界面 {@code KillAuraConsoleScreen} 的四个页签）</b></p>
 * <ul>
 *   <li>章节标题：{@code 概览 / 常规 / 目标 / 时机} = 控制台页签名逐字
 *       （{@code KillAuraConsoleScreen.java:73-76}）；{@code 杀戮光环控制台} = 控制台窗口标题逐字（{@code :104}）。</li>
 *   <li>{@code 概览} 章只列概览页的三个分区名（{@code 运行状态 / 目标 / 自检}，逐字取自
 *       {@code KillAuraOverviewPage.java:38/43/48}）—— <b>一个取值文案都没引用</b>：
 *       说明是静态文本，写死「未选择 / 0 无」会被读成实时状态（用户 2026-09-17 反馈
 *       「我明明都选择实体 跟武器 没同步」）。实时数据只在概览页本身看，说明书不承载运行态。</li>
 *   <li>{@code 常规} 9 项 / {@code 目标} 10 项 / {@code 时机} 7 项的「名称 §8- 描述」全部来自
 *       {@link KillAuraTexts}（{@code NAME_*} / {@code DESC_*} 常量逐字，共 26 项，
 *       顺序即设置面顺序与三页页签的排列顺序）。</li>
 *   <li>{@code §b打开控制台} = 模块页入口按钮文案逐字（{@code KillAuraPage.java:31}）；
 *       {@code §7刷新} 及其说明「顶部状态条每秒自动刷新；概览页整页每秒重画，其余页按这个按钮重排最新数据」
 *       = 控制台页脚按钮与悬停说明逐字（{@code KillAuraConsoleScreen.java:214-215}）；
 *       {@code §7关闭} = 页脚按钮逐字（{@code :216}）。</li>
 * </ul>
 *
 * <p><b>只加框、不加词</b>：章节标头 {@code §3[§b#§3] §f标题} 与窗框由
 * {@link HelpPanelScreen#buildHelpContent} 统一生成，正文行只沿用本项目既有的
 * {@code §8├─} / {@code §8└─} 树形符号与 {@code §8-} 分隔符（与
 * {@code AutoVillagerTradeHelpContent} 同一套排版）。</p>
 *
 * <p><b>维护口径（第 212 条）</b>：{@link KillAuraTexts} 或控制台页签改一个字，本类必须同步改，
 * 两处不一致即视为说明过时。</p>
 */
public final class KillAuraHelpContent {

    /** 五章节（模块页内嵌说明的唯一数据源，禁止在页面里再拼一份） */
    public static final HelpPanelScreen.HelpSection[] SECTIONS = {
        new HelpPanelScreen.HelpSection("概览",
            "  §8├─ §7§l运行状态",
            "  §8├─ §7§l目标",
            "  §8└─ §7§l自检"
        ),
        new HelpPanelScreen.HelpSection("常规",
            "  §8├─ §f" + KillAuraTexts.NAME_ATTACK_WHEN_HOLDING + " §8- §7" + KillAuraTexts.DESC_ATTACK_WHEN_HOLDING,
            "  §8├─ §f" + KillAuraTexts.NAME_WEAPONS + " §8- §7" + KillAuraTexts.DESC_WEAPONS,
            "  §8├─ §f" + KillAuraTexts.NAME_ROTATION + " §8- §7" + KillAuraTexts.DESC_ROTATION,
            "  §8├─ §f" + KillAuraTexts.NAME_AUTO_SWITCH + " §8- §7" + KillAuraTexts.DESC_AUTO_SWITCH,
            "  §8├─ §f" + KillAuraTexts.NAME_SWAP_BACK + " §8- §7" + KillAuraTexts.DESC_SWAP_BACK,
            "  §8├─ §f" + KillAuraTexts.NAME_SHIELD_MODE + " §8- §7" + KillAuraTexts.DESC_SHIELD_MODE,
            "  §8├─ §f" + KillAuraTexts.NAME_ONLY_ON_CLICK + " §8- §7" + KillAuraTexts.DESC_ONLY_ON_CLICK,
            "  §8├─ §f" + KillAuraTexts.NAME_ONLY_ON_LOOK + " §8- §7" + KillAuraTexts.DESC_ONLY_ON_LOOK,
            "  §8└─ §f" + KillAuraTexts.NAME_PAUSE_BARITONE + " §8- §7" + KillAuraTexts.DESC_PAUSE_BARITONE
        ),
        new HelpPanelScreen.HelpSection("目标",
            "  §8├─ §f" + KillAuraTexts.NAME_ENTITY_TYPES + " §8- §7" + KillAuraTexts.DESC_ENTITY_TYPES,
            "  §8├─ §f" + KillAuraTexts.NAME_PRIORITY + " §8- §7" + KillAuraTexts.DESC_PRIORITY,
            "  §8├─ §f" + KillAuraTexts.NAME_MAX_TARGETS + " §8- §7" + KillAuraTexts.DESC_MAX_TARGETS,
            "  §8├─ §f" + KillAuraTexts.NAME_RANGE + " §8- §7" + KillAuraTexts.DESC_RANGE,
            "  §8├─ §f" + KillAuraTexts.NAME_WALLS_RANGE + " §8- §7" + KillAuraTexts.DESC_WALLS_RANGE,
            "  §8├─ §f" + KillAuraTexts.NAME_PASSIVE_MOB_AGE_FILTER + " §8- §7" + KillAuraTexts.DESC_PASSIVE_MOB_AGE_FILTER,
            "  §8├─ §f" + KillAuraTexts.NAME_HOSTILE_MOB_AGE_FILTER + " §8- §7" + KillAuraTexts.DESC_HOSTILE_MOB_AGE_FILTER,
            "  §8├─ §f" + KillAuraTexts.NAME_IGNORE_NAMED + " §8- §7" + KillAuraTexts.DESC_IGNORE_NAMED,
            "  §8├─ §f" + KillAuraTexts.NAME_IGNORE_PASSIVE + " §8- §7" + KillAuraTexts.DESC_IGNORE_PASSIVE,
            "  §8└─ §f" + KillAuraTexts.NAME_IGNORE_TAMED + " §8- §7" + KillAuraTexts.DESC_IGNORE_TAMED
        ),
        new HelpPanelScreen.HelpSection("时机",
            "  §8├─ §f" + KillAuraTexts.NAME_PAUSE_ON_LAG + " §8- §7" + KillAuraTexts.DESC_PAUSE_ON_LAG,
            "  §8├─ §f" + KillAuraTexts.NAME_PAUSE_ON_USE + " §8- §7" + KillAuraTexts.DESC_PAUSE_ON_USE,
            "  §8├─ §f" + KillAuraTexts.NAME_PAUSE_ON_CA + " §8- §7" + KillAuraTexts.DESC_PAUSE_ON_CA,
            "  §8├─ §f" + KillAuraTexts.NAME_TPS_SYNC + " §8- §7" + KillAuraTexts.DESC_TPS_SYNC,
            "  §8├─ §f" + KillAuraTexts.NAME_CUSTOM_DELAY + " §8- §7" + KillAuraTexts.DESC_CUSTOM_DELAY,
            "  §8├─ §f" + KillAuraTexts.NAME_HIT_DELAY + " §8- §7" + KillAuraTexts.DESC_HIT_DELAY,
            "  §8└─ §f" + KillAuraTexts.NAME_SWITCH_DELAY + " §8- §7" + KillAuraTexts.DESC_SWITCH_DELAY
        ),
        new HelpPanelScreen.HelpSection("杀戮光环控制台",
            "  §8├─ §b打开控制台",
            "  §8├─ §7刷新 §8- §7顶部状态条每秒自动刷新；概览页整页每秒重画，其余页按这个按钮重排最新数据",
            "  §8└─ §7关闭"
        )
    };

    private KillAuraHelpContent() {
    }
}
