package com.yiyiaddon.feature.reconnect.config;

import com.yiyiaddon.ui.screen.HelpPanelScreen;

/**
 * 「自动重连」模块的用户可见文案唯一来源。
 *
 * <p><b>模块性质</b>：本模块是<b>用户点名新增</b>（旧项目没有独立的自动重连模块，重连原先长在自动登入里），
 * 因此文案不是旧项目资产，而是按「自动登入 · 自动重连」那一组既有中文措辞续写的自造文案：
 * 设置名、状态串与播报句式都沿用自动登入同名字段的说法（{@code 自动重连 / 重连等待（tick） /
 * 无限重连 / 最大重连次数}），玩家在两个模块里看到的是同一套词。文案只在本类出现一处，
 * 供设置载体、控制台各页、模块页与模块共用（第 169 条）。</p>
 */
public final class ReconnectTexts {

    private ReconnectTexts() {
    }

    // ── 模块元数据 ──

    /** 模块中文显示名（模块列表、播报前缀、控制台标题共用） */
    public static final String MODULE_NAME = "自动重连";

    /** 模块说明（模块页标题下与控制台标题下同一份） */
    public static final String DESCRIPTION =
        "断线后自动连回上一次进入的服务器；独立于自动登入，可在控制台一键断开测试。";

    // ── 页签 / 分组名 ──

    /** 概览页签：只读运行状态与三个重连动作，不承载设置项 */
    public static final String TAB_OVERVIEW = "概览";

    /** 设置组名（= 页签名）：本模块自己的分组，不新造第二套分类（第 183 条） */
    public static final String GROUP_SETTINGS = "重连设置";

    // ── 设置名（同时就是落盘键名，改名即换键，第 174 条） ──

    public static final String NAME_ENABLED = "自动重连";
    public static final String NAME_DELAY = "重连等待（tick）";
    public static final String NAME_UNLIMITED = "无限重连";
    public static final String NAME_MAX_ATTEMPTS = "最大重连次数";
    public static final String NAME_STABLE_TICKS = "连接稳定判定（tick）";
    public static final String NAME_CANCEL_ON_MENU = "回主菜单时取消重连";

    public static final String DESC_ENABLED =
        "断线后自动连回上一次进入的服务器。关闭后本模块只显示状态，不做任何连接动作。";
    public static final String DESC_DELAY =
        "断线后等待多少 tick 再重连（20 tick = 1 秒）。";
    public static final String DESC_UNLIMITED =
        "开启后忽略「最大重连次数」，一直尝试连接。";
    public static final String DESC_MAX_ATTEMPTS =
        "连续失败达到该次数后停止重连。";
    public static final String DESC_STABLE_TICKS =
        "重新连上后多少 tick 视为稳定，失败计数归零（20 tick = 1 秒）。";
    public static final String DESC_CANCEL_ON_MENU =
        "等待重连期间手动返回服务器列表或主菜单时，取消本次重连。";

    // ── 按钮与行尾提示 ──

    public static final String BTN_CONSOLE = "§b打开控制台";
    public static final String BTN_RECONNECT_NOW = "立即重连";
    public static final String BTN_STOP = "§c停止重连";
    public static final String BTN_TEST = "§e测试重连";
    public static final String BTN_REFRESH = "§7刷新";
    public static final String BTN_CLOSE = "§7关闭";

    public static final String HINT_RECONNECT_NOW = "跳过等待时间，立刻连接上一次的服务器";
    public static final String HINT_STOP = "取消本次等待（保留服务器记录，之后仍可立即重连）";
    public static final String HINT_TEST =
        "主动断开当前连接，随后按设置自动连回，用来验证断线重连这条链路";
    public static final String HINT_TEST_DISABLED = "当前不在服务器里，无法测试";

    // ── 测试键的二次确认 ──

    public static final String CONFIRM_TEST_TITLE = "测试重连";
    public static final String CONFIRM_TEST_HEADLINE = "§e这会把当前连接真的断开，随后自动连回：";
    public static final String CONFIRM_TEST_ITEM_1 = "正在进行的挂机任务会被打断";
    public static final String CONFIRM_TEST_ITEM_2 = "断开到连回期间人物会短暂离线";
    public static final String CONFIRM_TEST_ITEM_3 = "若等待时间设为 0，断线界面一闪而过即开始连接";
    public static final String CONFIRM_TEST_LABEL = "§e确认断开并重连";

    // ── 状态串 ──

    /**
     * 倒计时的前缀 / 后缀（中间夹秒数）。
     *
     * <p>四种状态的固定文案由 {@code model/ReconnectState} 自带（枚举自带显示名是本项目 model 层口径），
     * 这里只放本模块独有的这一段拼装文案，两边不重复。</p>
     */
    public static final String STATE_WAITING_PREFIX = "§e重连倒计时：§f";
    public static final String STATE_WAITING_SUFFIX = " 秒";
    /** 总开关关闭时概览页的状态行取值（与「空闲」区分：这是被设置关掉的） */
    public static final String STATE_DISABLED = "§8已关闭（设置里未开启）";

    // ── 播报 ──

    public static final String MSG_STARTUP = "§a§l已启动 §8│ §f§l等待进入服务器...";
    public static final String MSG_DISCONNECTED_PREFIX = "服务器断开，";
    public static final String MSG_DISCONNECTED_SUFFIX = " 秒后重新连接。";
    public static final String MSG_RECONNECTING_PREFIX = "正在重新连接... (第 ";
    public static final String MSG_RECONNECTING_SUFFIX = " 次)";
    public static final String MSG_READY = "§a§l已重新连接成功。";
    public static final String MSG_GIVE_UP = "已达到最大重连次数，自动重连停止。";
    public static final String MSG_STOPPED = "§c已停止自动重连。";
    public static final String MSG_MANUAL_CANCEL = "§e已检测到手动返回主菜单，自动重连已取消。";
    public static final String MSG_TEST_ARMED = "§e测试重连：即将主动断开当前连接，随后按设置自动连回。";
    public static final String MSG_TEST_NO_CONNECTION = "§c当前不在服务器里，没有可断开的连接。";
    public static final String MSG_SUPPRESSED = "§7本次断开是主动断线（管理员检测），自动重连不介入。";
    public static final String MSG_NO_SERVER = "§c没有可重连的服务器记录：请先进入一次服务器。";
    /** 自动登入模块已接管重连时的一句话（只在玩家主动点「测试重连」时播报，避免每次断线都刷屏） */
    public static final String MSG_YIELD = "§7重连由「自动登入」模块接管，本模块不重复调度。";
    /** 概览页「谁在接管」状态行的取值 */
    public static final String YIELD_TEXT = "§e自动登入接管";

    // ── 使用说明（模块页内嵌；本模块新增，文案为自造，口径与其余模块的说明同格式） ──

    private static final String SECTION_WHAT = "§e§l▌ 它能做什么";
    private static final String SECTION_SETTINGS = "§a§l▌ 设置项";
    private static final String SECTION_TEST = "§b§l▌ 测试断线重连";
    private static final String SECTION_MANUAL = "§d§l▌ 什么时候不会重连";
    private static final String SECTION_CONSOLE = "§c§l▌ 控制台在哪";

    private static final String[] HELP_WHAT = {
        "§f  1. 记录你进入的每一台服务器（地址与服务器信息）",
        "§f  2. 被踢出或服务器掉线时，按「重连等待」倒计时自动连回",
        "§f  3. 连上并稳定后把失败计数清零，下一轮断线重新从第 1 次算起"
    };
    private static final String[] HELP_SETTINGS = {
        "§f  · 自动重连：总开关，关掉后本模块不发起任何连接",
        "§f  · 重连等待（tick）：断线后等多久再连，20 tick = 1 秒",
        "§f  · 无限重连：忽略「最大重连次数」，一直尝试",
        "§f  · 最大重连次数：连续失败到该次数就停下并播报",
        "§f  · 连接稳定判定（tick）：连上多久算稳定，失败计数归零",
        "§f  · 回主菜单时取消重连：手动退出到主菜单就放弃本次重连"
    };
    private static final String[] HELP_TEST = {
        "§f  1. 先进入任意服务器（本模块需要一条服务器记录）",
        "§f  2. 打开控制台 → 概览页 → 点「§e测试重连§f」→ 二次确认里点「确认断开」",
        "§f  3. 客户端会真的断开并出现断线界面，随后按「重连等待」自动连回",
        "§f  4. 连回成功后聊天栏播报「已重新连接成功」，概览页状态回到「已就绪」"
    };
    private static final String[] HELP_MANUAL = {
        "§f  · 自己点「断开连接」退出服务器：原版会回到主菜单，不属于掉线，不重连",
        "§f  · 在等待倒计时期间走进主菜单 / 服务器列表：按设置取消本次重连",
        "§f  · 管理员检测的「立即断线」：那是保命动作，重连不会把它撤销",
        "§f  · 从未进过服务器：没有服务器记录，无法重连（会播报提示）"
    };
    private static final String[] HELP_CONSOLE = {
        "§f  · 模块页 →「§b打开控制台§f」按钮",
        "§f  · 控制台分两页：概览（状态 + 三个动作） / " + GROUP_SETTINGS + "（六项设置）",
        "§f  · 所有改动即时保存，关窗与重启游戏后仍然生效"
    };

    /** 模块页与使用说明窗共用的章节（顺序即显示顺序） */
    public static HelpPanelScreen.HelpSection[] helpSections() {
        return new HelpPanelScreen.HelpSection[]{
            new HelpPanelScreen.HelpSection(SECTION_WHAT, HELP_WHAT),
            new HelpPanelScreen.HelpSection(SECTION_SETTINGS, HELP_SETTINGS),
            new HelpPanelScreen.HelpSection(SECTION_TEST, HELP_TEST),
            new HelpPanelScreen.HelpSection(SECTION_MANUAL, HELP_MANUAL),
            new HelpPanelScreen.HelpSection(SECTION_CONSOLE, HELP_CONSOLE)
        };
    }
}
