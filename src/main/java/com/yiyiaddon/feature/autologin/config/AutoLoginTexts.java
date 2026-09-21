package com.yiyiaddon.feature.autologin.config;

import com.yiyiaddon.ui.screen.HelpPanelScreen;

/**
 * 自动登入模块的用户可见文案唯一来源。
 *
 * <p><b>逐字资产</b>：模块名、设置名、设置描述、分组名、说明面板六章、重连控件与状态串，
 * 全部逐字取自旧项目 {@code autologin/AutoLoginModule.java} 与
 * {@code autologin/config/AutoLoginSettings.java}（开发习惯第十六章：旧项目已有的中文显示文本
 * 必须逐字保留）。<b>模块说明是唯一例外</b>：现为「进服自动注册登录并执行指令」，
 * 原为旧项目的长句，2026-09-21 按「一行放得下的中文短注」重写（模块清单行不截断，
 * 见 166 号复盘第十一节）。本类只做「同一句话只有一处」的收口，供设置载体、控制台各页、模块页与模块共用
 * （第 169 条），不承担任何业务逻辑。</p>
 *
 * <p><b>重名键登记</b>：旧项目「每步等待（tick）」在「进服路线」与「自用配置」两处各有一项设置，
 * 名字完全相同（{@code menuActionDelay} 与 {@code leyuanStepDelay}）。旧框架的设置是扁平名字表，
 * 同名两项实际共用同一个落盘键；本项目按 1:1 口径保留同一行为（两处读写同一个键），只登记不修改。</p>
 */
public final class AutoLoginTexts {

    private AutoLoginTexts() {
    }

    // ── 模块元数据 ──

    /** 模块中文显示名（模块列表、播报前缀、控制台标题共用；逐字 = 旧模块名） */
    public static final String MODULE_NAME = "自动登入";

    /** 模块说明（模块页标题下与控制台标题下同一份）；按「一行放得下的中文短注」写，清单行不截断 */
    public static final String DESCRIPTION =
        "进服自动注册登录并执行指令";

    // ── 页签 / 折叠块名（= 旧项目设置组名与旧源码分节标题，不新造分类，第 183 条） ──

    /** 概览页签（旧项目没有概览页，本页只读运行状态与重连控件，不新增任何设置项） */
    public static final String TAB_OVERVIEW = "概览";
    /** 旧 {@code settings.createGroup("登录认证")} */
    public static final String GROUP_AUTH = "登录认证";
    /** 旧 {@code settings.createGroup("自动重连")} */
    public static final String GROUP_RECONNECT = "自动重连";
    /** 旧 {@code settings.createGroup("自动执行指令")} */
    public static final String GROUP_COMMANDS = "自动执行指令";
    /** 旧 {@code settings.createGroup("进服路线")} 的「通用子服」分节 */
    public static final String GROUP_ROUTE = "进服路线";
    /** 旧 {@code settings.createGroup("进服路线")} 的「自用配置」分节 */
    public static final String GROUP_LEYUAN = "自用配置";
    /** 旧 {@code settings.createGroup("账号与调试")} */
    public static final String GROUP_ACCOUNT = "账号与调试";

    // ── 设置名（逐字 = 旧设置名；同时就是落盘键名，改名即换键，第 174 条） ──

    public static final String NAME_AUTO_LOGIN = "自动登录";
    public static final String NAME_GUI_LOGIN = "GUI 自动登录";
    public static final String NAME_NO_LOGIN_DETECTION = "服务器免登录检测";
    public static final String NAME_DETECT_SUBSERVER = "检测服务器子服";
    public static final String NAME_LOGIN_PASSWORD = "登录密码";
    public static final String NAME_LOGIN_DELAY = "登录延迟（tick）";
    public static final String NAME_AUTO_REGISTER = "自动注册";
    public static final String NAME_REGISTER_PASSWORD = "注册密码";
    public static final String NAME_REGISTER_DELAY = "注册延迟（tick）";
    public static final String NAME_WORLD_LOAD_WAIT = "世界加载等待（tick）";
    public static final String NAME_AUTH_DETECT_TIMEOUT = "认证检测超时（tick）";

    public static final String NAME_AUTO_RECONNECT = "自动重连";
    public static final String NAME_RECONNECT_DELAY = "重连等待（tick）";
    public static final String NAME_ALWAYS_RECONNECT = "无限重连";
    public static final String NAME_MAX_RECONNECT_ATTEMPTS = "最大重连次数";

    public static final String NAME_AUTO_COMMAND = "登录后执行指令";
    public static final String NAME_AUTO_COMMAND_TEXT = "执行指令";
    public static final String NAME_COMMAND_DELAY = "指令延迟（tick）";

    public static final String NAME_AUTO_ENTER_SUBSERVER = "自动进入目标区域";
    public static final String NAME_SERVER_ENTRY_MODE = "进入方式";
    public static final String NAME_MENU_TOOL = "菜单工具";
    public static final String NAME_MENU_ITEM_KEYWORDS = "菜单物品关键词";
    public static final String NAME_MENU_CLICK_STEPS = "菜单点击顺序";
    /** 通用子服的「每步等待」（与自用配置的同名项共用同一个落盘键，见类注释） */
    public static final String NAME_MENU_ACTION_DELAY = "每步等待（tick）";
    public static final String NAME_MENU_TIMEOUT = "菜单超时（tick）";
    public static final String NAME_TARGET_AREA_KEYWORDS = "目标区域关键词";
    public static final String NAME_REQUIRE_TARGET_KEYWORD = "必须命中目标关键词";
    public static final String NAME_TARGET_STABLE_DELAY = "到达稳定等待（tick）";
    public static final String NAME_SUBSERVER_COMMAND = "到达目标后执行指令";
    public static final String NAME_SUBSERVER_COMMAND_TEXT = "目标区域指令";
    public static final String NAME_SUBSERVER_COMMAND_DELAY = "额外指令延迟（tick）";

    public static final String NAME_LEYUAN_ENABLED = "启用自用配置";
    public static final String NAME_LEYUAN_MENU_TOOL = "自用菜单工具";
    public static final String NAME_LEYUAN_MENU_ITEM_KEYWORDS = "菜单工具关键词";
    public static final String NAME_LEYUAN_LOGIN_SURVIVAL_KEYWORD = "登录服按钮";
    public static final String NAME_LEYUAN_WELCOME_ENTRY_MODE = "欢迎页入口";
    public static final String NAME_LEYUAN_WORLD_TRANSFER_KEYWORD = "主城世界传送按钮";
    public static final String NAME_LEYUAN_SURVIVAL_FIRST_KEYWORD = "第一层生存按钮";
    public static final String NAME_LEYUAN_SURVIVAL_SECOND_KEYWORD = "第二层生存按钮";
    public static final String NAME_LEYUAN_TARGET_SERVER_KEYWORD = "最终目标子服按钮";
    public static final String NAME_LEYUAN_MAIN_CITY_KEYWORDS = "主城到达关键词";
    public static final String NAME_LEYUAN_AFK_AREA_KEYWORDS = "挂机区识别关键词";
    public static final String NAME_LEYUAN_AFK_RECOVERY_ENABLED = "挂机区自动回服";
    public static final String NAME_LEYUAN_AFK_MENU_DELAY_MINUTES = "挂机区菜单延迟（分钟）";
    public static final String NAME_LEYUAN_RETURN_MAIN_CITY_KEYWORDS = "返回主城按钮关键词";
    public static final String NAME_LEYUAN_TARGET_AREA_KEYWORDS = "到达确认关键词";
    /** 自用配置的「每步等待」（与通用子服的同名项共用同一个落盘键，见类注释） */
    public static final String NAME_LEYUAN_STEP_DELAY = "每步等待（tick）";
    public static final String NAME_LEYUAN_STEP_TIMEOUT = "单步超时（tick）";
    public static final String NAME_LEYUAN_CITY_MENU_FALLBACK = "主城钟快捷键兜底（Shift＋F）";
    public static final String NAME_LEYUAN_CITY_MENU_FALLBACK_DELAY = "主城钟兜底等待（tick）";
    public static final String NAME_LEYUAN_RECOVERY_WAIT_MINUTES = "异常恢复等待上限（分钟）";
    public static final String NAME_LEYUAN_RECOVERY_RETRY_SECONDS = "异常恢复初始重试（秒）";
    public static final String NAME_LEYUAN_RECOVERY_MAX_RETRIES = "异常恢复最大重试次数";

    public static final String NAME_AUTO_CHECK_ACCOUNT = "进服时自动检测";
    public static final String NAME_CHECK_ACCOUNT_NOW = "立即检测账号";
    public static final String NAME_DEBUG_MODE = "调试模式";

    // ── 设置描述（逐字 = 旧设置描述，同时作为控制台行的悬停提示，第 213 条） ──

    public static final String DESC_AUTO_LOGIN = "检测到登录提示时自动发送登录指令。";
    public static final String DESC_GUI_LOGIN = "自动识别并填写 GUI 登录框（服务器弹出的密码输入界面）。";
    public static final String DESC_NO_LOGIN_DETECTION =
        "适用于服务器两小时免登录：每次进服先监听成功登录或已登入消息；命中时保持自动登录关闭，未命中或收到登录指令提示时自动开启登录。";
    public static final String DESC_DETECT_SUBSERVER =
        "大厅完成登录后，进入子服时沿用大厅登录状态，不重复发送登录指令。关闭后每个新服务器连接都重新检测登录。";
    public static final String DESC_LOGIN_PASSWORD = "自动登录使用的密码（不会显示在聊天栏）。";
    public static final String DESC_LOGIN_DELAY = "收到登录提示后等待多少 tick 再发送指令（20 tick = 1 秒）。";
    public static final String DESC_AUTO_REGISTER = "检测到注册提示时自动发送注册指令。";
    public static final String DESC_REGISTER_PASSWORD = "自动注册使用的密码（不会显示在聊天栏）。";
    public static final String DESC_REGISTER_DELAY = "收到注册提示后等待多少 tick 再发送指令（20 tick = 1 秒）。";
    public static final String DESC_WORLD_LOAD_WAIT = "进入服务器后等待多少 tick 再开始认证流程（20 tick = 1 秒）。";
    public static final String DESC_AUTH_DETECT_TIMEOUT =
        "开始监听后等待多少 tick 仍未收到登录/注册提示，则判定该服务器无需登录，直接进入就绪状态（20 tick = 1 秒）。";

    public static final String DESC_AUTO_RECONNECT =
        "断线后在主菜单或断线界面等待指定时间，再自动连接上一次服务器。";
    public static final String DESC_RECONNECT_DELAY = "断线后等待多少 tick 再重连（20 tick = 1 秒）。";
    public static final String DESC_ALWAYS_RECONNECT =
        "开启后忽略最大重连次数，持续尝试连接，直到成功进服或关闭自动重连。";
    public static final String DESC_MAX_RECONNECT_ATTEMPTS = "连续失败达到该次数后停止重连。";

    public static final String DESC_AUTO_COMMAND =
        "仅在自动登录成功或认证检测超时后执行自定义指令；登录和注册均关闭时不会执行。";
    public static final String DESC_AUTO_COMMAND_TEXT = "登录完成后执行的指令（含斜杠，如 /home）。";
    public static final String DESC_COMMAND_DELAY = "登录完成后等待多少 tick 再执行指令（20 tick = 1 秒）。";

    public static final String DESC_AUTO_ENTER_SUBSERVER = "认证完成后按照选择的进入方式到达目标区域。";
    public static final String DESC_SERVER_ENTRY_MODE =
        "直接进入、菜单传送、子服网络或自用多阶段回服路线，四种模式互斥运行。";
    public static final String DESC_MENU_TOOL = "选择用于打开服务器菜单的快捷栏物品。";
    public static final String DESC_MENU_ITEM_KEYWORDS = "辅助匹配改名物品的名称或 Lore。";
    public static final String DESC_MENU_CLICK_STEPS = "每一项是一步，按从上到下的顺序匹配物品名称或 Lore。";
    public static final String DESC_MENU_ACTION_DELAY = "使用菜单物品或点击按钮后等待的时间。";
    public static final String DESC_MENU_TIMEOUT = "等待菜单或目标关键词的最长时间，超时后停止，避免乱点。";
    public static final String DESC_TARGET_AREA_KEYWORDS = "扫描侧边栏确认到达目标区域。";
    public static final String DESC_REQUIRE_TARGET_KEYWORD = "开启后只有侧边栏命中目标区域关键词才确认到达。";
    public static final String DESC_TARGET_STABLE_DELAY = "确认目标区域后等待多少 tick 再执行指令。";
    public static final String DESC_SUBSERVER_COMMAND = "确认进入目标区域并等待稳定后执行一次指令。";
    public static final String DESC_SUBSERVER_COMMAND_TEXT = "进入目标区域后执行的指令，例如 /home。";
    public static final String DESC_SUBSERVER_COMMAND_DELAY = "到达稳定等待结束后额外等待多少 tick 再执行指令。";

    public static final String DESC_LEYUAN_ENABLED =
        "自用总开关：控制认证完成后的首次进服路线；关闭后仍可单独保留挂机区自动回服。";
    public static final String DESC_LEYUAN_MENU_TOOL =
        "仅在快捷栏识别到该物品时，主城才会转向空气并右键打开菜单。";
    public static final String DESC_LEYUAN_MENU_ITEM_KEYWORDS = "菜单工具名称或 Lore 的辅助关键词。";
    public static final String DESC_LEYUAN_LOGIN_SURVIVAL_KEYWORD = "登录服菜单中传送到欢迎页的按钮关键词。";
    public static final String DESC_LEYUAN_WELCOME_ENTRY_MODE =
        "选择扫描自用入口，或点击欢迎菜单中可直达主城的无名书本。";
    public static final String DESC_LEYUAN_WORLD_TRANSFER_KEYWORD = "到达主城后重新打开菜单并点击的按钮关键词。";
    public static final String DESC_LEYUAN_SURVIVAL_FIRST_KEYWORD = "世界传送页面第一层资源大区按钮关键词。";
    public static final String DESC_LEYUAN_SURVIVAL_SECOND_KEYWORD = "下一页面再次出现的资源大区按钮关键词。";
    public static final String DESC_LEYUAN_TARGET_SERVER_KEYWORD = "最后选择资源一区或资源二区的按钮关键词。";
    public static final String DESC_LEYUAN_MAIN_CITY_KEYWORDS =
        "欢迎页点击后仅通过侧边栏确认已到达主城，避免误把其他子服当作主城。";
    public static final String DESC_LEYUAN_AFK_AREA_KEYWORDS =
        "侧边栏命中任一关键词时识别为挂机区；是否自动回服由「挂机区自动回服」单独控制。";
    public static final String DESC_LEYUAN_AFK_RECOVERY_ENABLED =
        "挂机区独立开关：检测到挂机区后执行返回主城大区、世界传送、资源大区、资源大区、资源二区；不受自用总开关影响。";
    public static final String DESC_LEYUAN_AFK_MENU_DELAY_MINUTES =
        "检测到挂机区后等待多久再打开菜单，避免服务器重启后菜单尚未准备好。默认 10 分钟，设置为 0 立即打开。";
    public static final String DESC_LEYUAN_RETURN_MAIN_CITY_KEYWORDS = "挂机区 Shift＋F 菜单中返回主城大区按钮的关键词。";
    public static final String DESC_LEYUAN_TARGET_AREA_KEYWORDS =
        "最终进入目标子服后，侧边栏用于确认成功；应与最终目标子服按钮一致。";
    public static final String DESC_LEYUAN_STEP_DELAY = "菜单打开、按钮点击和页面切换之间的等待时间。";
    public static final String DESC_LEYUAN_STEP_TIMEOUT = "每个路线阶段的最长等待时间，超时后停止以避免乱点。";
    public static final String DESC_LEYUAN_CITY_MENU_FALLBACK = "主城快捷栏未识别到钟时，直接尝试 Shift＋F 快捷菜单。";
    public static final String DESC_LEYUAN_CITY_MENU_FALLBACK_DELAY =
        "已识别到钟但右键未打开菜单时，等待多久再触发 Shift＋F。20 tick = 1 秒。";
    public static final String DESC_LEYUAN_RECOVERY_WAIT_MINUTES =
        "自用路线断线后最多等待多久继续恢复当前步骤，按分钟设置，最大 30 分钟。";
    public static final String DESC_LEYUAN_RECOVERY_RETRY_SECONDS =
        "自用路线异常恢复的首次重试等待，后续按次数递增退避。";
    public static final String DESC_LEYUAN_RECOVERY_MAX_RETRIES = "自用路线异常恢复期间允许的最多连接尝试次数。";

    public static final String DESC_AUTO_CHECK_ACCOUNT =
        "每次进入服务器后自动输出账号类型（正版 / 离线）；与自动登录、自动注册和自动指令相互独立。";
    public static final String DESC_CHECK_ACCOUNT_NOW =
        "点击后立即在聊天栏输出当前账号的检测结果（不发送任何网络请求）。结果仅供参考，不参与登录判断。";
    public static final String DESC_DEBUG_MODE = "在聊天栏输出当前状态、识别到的指令等调试信息。";

    // ── 名单 / 物品行文案（沿用本项目选择器与自由名单行的既有措辞） ──

    public static final String SELECT = "§b选择";
    public static final String SELECT_HINT =
        "打开物品选择器：条目带图标、分组可折叠、点任意一行即选中";
    public static final String ITEM_UNSELECTED = "未选择";
    public static final String LIST_ADD = "添加";
    public static final String LIST_ADD_HINT = "把输入框里的内容加入名单（重复项跳过）";
    public static final String LIST_REMOVE_HINT = "从名单里移除这一项";
    public static final String LIST_EMPTY = "未填写";
    /** 物品选择器窗口标题：沿用既有「选择 + 设置名」拼法 */
    public static final String SELECT_TITLE_PREFIX = "选择";

    // ── 重连控件与状态串（逐字 = 旧 {@code getWidget} / {@code getInfoString}） ──

    public static final String RECONNECT_COUNTDOWN_PREFIX = "§e重连倒计时：§f";
    public static final String RECONNECT_COUNTDOWN_SUFFIX = " 秒";
    public static final String RECONNECT_CONNECTING = "§a正在连接...";
    public static final String RECONNECT_NOW = "立即重连";
    public static final String RECONNECT_STOP = "§c停止重连";
    public static final String STATE_READY = "§a就绪";
    public static final String STATE_CONNECTING = "§a连接中";
    public static final String STATE_NONE = "§8无";

    /** 立即检测账号按钮的点击结果前缀（旧设置的伪按钮回调原文，{@code §f§l} + 检测报告） */
    public static final String CHECK_ACCOUNT_PREFIX = "§f§l";

    // ── 旧项目的说明面板原文（{@code buildHelpContent} 六章，逐字保留） ──

    /** 说明章节标题（逐字 = 旧 {@code buildHelpContent} 的各章标题） */
    public static final String HELP_SECTION_QUICK_START = "快速上手";
    public static final String HELP_SECTION_FLOW = "自动流程";
    public static final String HELP_SECTION_LEYUAN = "自用配置";
    public static final String HELP_SECTION_ROUTE = "路线与指令";
    public static final String HELP_SECTION_STATE = "状态说明";
    public static final String HELP_SECTION_NOTICE = "注意事项";

    public static final String[] HELP_QUICK_START = {
        "  §8├─ §f按服务器需求开启自动登录或自动注册，并填写对应密码",
        "  §8├─ §f需要进入目标区域时，开启「自动进入目标区域」并选择进入方式",
        "  §8└─ §f开启模块后按配置完成认证、回服、重连和到达确认"
    };

    public static final String[] HELP_FLOW = {
        "  §a[1] §f等待世界加载并监听登录、注册或免登录提示",
        "  §a[2] §f根据认证结果发送一次登录或注册指令",
        "  §a[3] §f认证完成后执行进服指令或目标区域路线",
        "  §a[4] §f断线后按自动重连设置恢复上一次服务器连接",
        "  §a[5] §f到达目标区域并稳定等待后，可执行一次目标区域指令"
    };

    public static final String[] HELP_LEYUAN = {
        "  §b▸ §f选择「自用配置」后，「启用自用配置」控制首次进服路线",
        "  §b▸ §f「挂机区自动回服」是独立开关，可单独检测挂机区并启动回服",
        "  §b▸ §f回服顺序固定：返回主城大区 §8→ §7世界传送 §8→ §7资源大区 §8→ §7资源二区",
        "  §b▸ §f默认用欢迎页「书本直达主城」入口，也可切换为扫描自用入口",
        "  §b▸ §f主城菜单优先用快捷栏物品，失败时可用 Shift＋F 兜底"
    };

    public static final String[] HELP_ROUTE = {
        "  §8├─ §f通用子服路线仅用于直接进入以外的前三种普通模式",
        "  §8├─ §f开启「检测服务器子服」后，子服连接沿用大厅认证状态",
        "  §8├─ §f自用路线通过侧边栏关键词确认主城、挂机区和最终目标",
        "  §8├─ §f挂机区菜单延迟只影响首次菜单打开，不影响登录和其他路线",
        "  §8└─ §f每步等待和单步超时均按 §e20 tick = 1 秒 §f计算"
    };

    public static final String[] HELP_STATE = {
        "  §a就绪     §8— §f已完成登录，模块正常运行",
        "  §e重连中   §8— §f断线后等待重连倒计时",
        "  §7无状态   §8— §f未进服或正在初始化"
    };

    public static final String[] HELP_NOTICE = {
        "  §c⚠ §f单人世界会自动关闭；正版验证服务器可开启，将自动跳过登录流程",
        "  §c⚠ §f自动登录与自动注册同时开启：老账号关「自动注册」，新账号关「自动登录」",
        "  §c⚠ §f自用配置路线仅适用于该服务器，请勿用于其他服务器",
        "  §c⚠ §f密码明文存储在配置文件中，请勿在公共电脑使用"
    };

    /** 说明面板六章（模块页内嵌与控制台概览共用的同一份） */
    public static HelpPanelScreen.HelpSection[] helpSections() {
        return new HelpPanelScreen.HelpSection[]{
            new HelpPanelScreen.HelpSection(HELP_SECTION_QUICK_START, HELP_QUICK_START),
            new HelpPanelScreen.HelpSection(HELP_SECTION_FLOW, HELP_FLOW),
            new HelpPanelScreen.HelpSection(HELP_SECTION_LEYUAN, HELP_LEYUAN),
            new HelpPanelScreen.HelpSection(HELP_SECTION_ROUTE, HELP_ROUTE),
            new HelpPanelScreen.HelpSection(HELP_SECTION_STATE, HELP_STATE),
            new HelpPanelScreen.HelpSection(HELP_SECTION_NOTICE, HELP_NOTICE)
        };
    }
}
