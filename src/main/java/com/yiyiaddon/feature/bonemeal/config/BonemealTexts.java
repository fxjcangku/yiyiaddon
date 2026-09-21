package com.yiyiaddon.feature.bonemeal.config;

/**
 * 自动骨粉模块的用户可见文案唯一来源。
 *
 * <p><b>逐字资产</b>：设置名、设置描述、分组名、全部播报原文逐字取自旧项目
 * {@code bonemeal/AutoBoneMeal.java}（设置声明 66-243 行、说明面板 268-296 行、播报 349-375 行、
 * 状态串 647-654 行）；一个字都不改（开发习惯第十六章：旧项目已有的中文显示文本必须逐字保留）。
 * 本类只做「同一句话只有一处」的收口，供设置载体、控制台各页与模块页共用（第 169 条），
 * 不承担任何业务逻辑。</p>
 *
 * <p><b>选择器行文案</b>：旧项目的五组目标方块由旧框架的列表设置控件承载（框架自带「选择 / 清空」
 * 与候选窗口，不属迁移资产，第 34 条）；本项目改用通用选择器
 * {@code ui/screen/SelectorScreen}，行文案沿用本项目选择器行的既有措辞
 * （与 {@code feature/packetbreak/config/PacketBreakTexts} 一致），不另造一套说法。
 * 窗口标题沿用既有「选择 + 设置名」的拼法。</p>
 */
public final class BonemealTexts {

    private BonemealTexts() {
    }

    // ── 模块元数据 ──

    /** 模块中文显示名（模块列表、播报前缀、控制台标题共用；逐字 = 旧模块名） */
    public static final String MODULE_NAME = "自动骨粉";

    /**
     * 模块说明（模块页标题下与控制台标题下同一份）。
     *
     * <p>按「一行放得下的中文短注」写（用户 2026-09-21：「所有模块都可以带中文注释…记得不可以带.....，
     * 宁愿简洁一点中文话术也不要出现省略号」）：模块清单行只在整段放得下时才画它，放不下的长文留在
     * 模块页的使用说明里；原文是旧项目构造器实参，为塞进清单行已改写。</p>
     */
    public static final String DESCRIPTION =
        "自动补骨粉催熟作物";

    // ── 设置分组名（页签 = 旧源码的分节标题与设置组名，不新造分类，第 183 条） ──

    /** 概览页签（旧项目没有概览页，本页只读运行状态，不新增任何设置项） */
    public static final String TAB_OVERVIEW = "概览";
    /** 旧 {@code sgGeneral}（默认组）承载的 5 项，旧源码分节标题即「一、基础参数」 */
    public static final String GROUP_BASIC = "基础参数";
    /** 旧 {@code settings.createGroup("目标方块")} */
    public static final String GROUP_TARGETS = "目标方块";
    /** 旧 {@code settings.createGroup("防作弊绕过")} */
    public static final String GROUP_BYPASS = "防作弊绕过";
    /** 旧 {@code settings.createGroup("ESP渲染")} */
    public static final String GROUP_ESP = "ESP渲染";

    // ── 设置名（逐字 = 旧设置名；同时就是落盘键名，改名即换键，第 174 条） ──

    public static final String NAME_TRIGGER_MODE = "触发模式";
    public static final String NAME_RANGE = "作用半径";
    public static final String NAME_CROSSHAIR_HINT = "准星提示";
    public static final String NAME_CHECK_OCCLUSION = "遮挡射线检测";
    public static final String NAME_NO_TARGET_HINT = "无目标提示";

    public static final String NAME_TARGET_CROPS = "农作物";
    public static final String NAME_TARGET_SAPLINGS = "树苗";
    public static final String NAME_TARGET_FLOWERS = "花卉";
    public static final String NAME_TARGET_MUSHROOMS = "蘑菇 / 菌类";
    public static final String NAME_TARGET_AQUATIC_NETHER = "水下 / 下界";

    public static final String NAME_TICK_DELAY = "动作节流（Tick）";
    public static final String NAME_MAX_PER_TICK = "每轮最大催熟数";
    public static final String NAME_ROTATE_SILENT = "视角静默同步";
    public static final String NAME_OFFHAND_FIRST = "副手优先";
    public static final String NAME_SWING_HAND = "摆动手臂";
    public static final String NAME_RESPECT_LAG = "服务器卡顿自停";
    public static final String NAME_AUTO_THROTTLE = "反作弊自动降速";

    public static final String NAME_ESP_ENABLED = "启用ESP";
    public static final String NAME_SHAPE_MODE = "形状模式";
    public static final String NAME_LINE_COLOR = "线框颜色";
    public static final String NAME_FILL_COLOR = "填充颜色";

    // ── 设置描述（逐字 = 旧设置描述，同时作为控制台行的悬停提示，第 213 条） ──

    public static final String DESC_TRIGGER_MODE =
        "范围自动扫描：自动搜索周围所有目标；准星精准指向：仅对准星看着的方块生效。";
    public static final String DESC_RANGE = "范围扫描的最大半径（格）。";
    public static final String DESC_CROSSHAIR_HINT =
        "准星对着不在目标列表的可催熟方块时，在聊天框提示方块名称。";
    public static final String DESC_CHECK_OCCLUSION =
        "开启后跳过视线被方块遮挡的目标（更严格，防服务端隔墙判定）；关闭后大范围也能催熟，适合密集农场。";
    public static final String DESC_NO_TARGET_HINT =
        "范围扫描/准星模式下，附近/准星处没有可催熟目标时聊天栏提示一次。";

    public static final String DESC_TARGET_CROPS =
        "小麦/胡萝卜/马铃薯/甜菜根/瓜茎/火把花/瓶子草/可可豆/甜浆果丛/洞穴藤蔓";
    public static final String DESC_TARGET_SAPLINGS =
        "橡树/云杉/白桦/丛林/金合欢/深色橡树/樱花/红树胎生苗/苍白橡树";
    public static final String DESC_TARGET_FLOWERS =
        "大型双格花（向日葵/丁香/玫瑰丛/牡丹）+ 粉红花瓣/野花/杜鹃花丛";
    public static final String DESC_TARGET_MUSHROOMS =
        "棕色蘑菇/红色蘑菇 + 下界绯红菌菇/诡异菌菇";
    public static final String DESC_TARGET_AQUATIC_NETHER =
        "海带 + 扭曲藤蔓/垂泪藤蔓 + 苔藓块/发光地衣/小型垂泪叶";

    public static final String DESC_TICK_DELAY =
        "每隔多少 Tick 执行一轮催熟，0=每帧最暴力，建议 0~2。";
    public static final String DESC_MAX_PER_TICK =
        "每轮（节流周期）最多同时催熟多少个方块。0=不限制（最暴力）。";
    public static final String DESC_ROTATE_SILENT =
        "发包瞬间附加 LookAndOnGround 包，令服务端认为准星正对目标，防止判定隔墙点击。";
    public static final String DESC_OFFHAND_FIRST = "优先检测副手是否持有骨粉，再检测主手。";
    public static final String DESC_SWING_HAND =
        "催熟成功后播放挥手动画（关闭可减少服务端行为特征）。";
    public static final String DESC_RESPECT_LAG =
        "检测到服务器 TPS 过低或被拉回时暂停催熟发包，避免雪上加霜被踢。";
    public static final String DESC_AUTO_THROTTLE =
        "检测到 Grim/Matrix 等高强度反作弊时自动提高动作节流，规避右键连点检测。";

    public static final String DESC_ESP_ENABLED = "对范围内所有候选目标方块绘制半透明边界框。";
    public static final String DESC_SHAPE_MODE = "渲染方式：填充+轮廓 / 仅轮廓 / 仅填充。";
    /** 旧 {@code lineColor} / {@code fillColor} 两个颜色设置没有写描述，因此这两项没有悬停提示 */
    public static final String DESC_LINE_COLOR = null;
    public static final String DESC_FILL_COLOR = null;

    // ── 选择器行（沿用本项目选择器行既有措辞） ──

    public static final String SELECT = "§b选择";
    public static final String SELECT_HINT =
        "打开方块选择器：条目带图标、分组可折叠、左候选右已选两栏多选";
    public static final String CLEAR = "§7清空";
    public static final String CLEAR_HINT = "清空已选名单（名单为空时无动作）";

    /** 选择器窗口标题：沿用既有「选择 + 设置名」拼法 */
    public static String selectTitle(String settingName) {
        return "选择" + settingName;
    }

    // ── 旧项目的说明面板原文（{@code getWidget} / {@code buildInfoWidget}，逐字保留） ──
    //
    // 旧面板的第一个参数是总标题「自动骨粉 · 使用说明」，本项目由模块页/控制台的标题承载同一句模块名，
    // 不再重复画一遍（与自动箱子模块页同一处理），故这里只保留五个章节的标题与正文。

    /** 说明章节标题（逐字 = 旧 {@code §e§l▌ …} 原文） */
    public static final String HELP_SECTION_PREPARE = "§e§l▌ 准备";
    public static final String HELP_SECTION_TRIGGER = "§a§l▌ 触发模式";
    public static final String HELP_SECTION_ANTICHEAT = "§b§l▌ 防作弊";
    public static final String HELP_SECTION_SUPPLY = "§d§l▌ 背包补给";
    public static final String HELP_SECTION_CROSSHAIR = "§c§l▌ 准星提示";

    /** 说明正文（逐字 = 旧 {@code buildInfoWidget} 的各段落行） */
    public static final String[] HELP_PREPARE = {
        "§f  1. 主手/副手携带骨粉，或背包备足",
        "§f  2. 在「目标方块」中勾选需要催熟的作物",
        "§f  3. 选择触发模式后开启模块"
    };
    public static final String[] HELP_TRIGGER = {
        "§f  范围扫描 — 自动搜索周围可催熟方块，每Tick同时催熟多个",
        "§f  准星指向 — 仅对准星正对的方块生效，准星离开则暂停"
    };
    public static final String[] HELP_ANTICHEAT = {
        "§f  节流发包：每隔 N Tick 发一次，防频率检测",
        "§f  视角同步：发包附加视角包，防隔墙判定"
    };
    public static final String[] HELP_SUPPLY = {
        "§f  快捷栏耗尽 → 自动从背包补入；背包也空 → 提示并暂停"
    };
    public static final String[] HELP_CROSSHAIR = {
        "§f  准星模式下，对着未登记的可催熟方块时聊天栏提示一次"
    };

    // ── 播报原文（逐字 = 旧模块的业务播报，禁止改写） ──

    /** 启动报告首行 */
    public static final String REPORT_TITLE = "§a§l✓ 自动骨粉 · 启动报告";
    /** 启动报告字段标签（旧实现为「标签　§8▸ 值」，全角空格分隔） */
    public static final String REPORT_TRIGGER = "触发模式";
    public static final String REPORT_TARGETS = "目标方块";
    public static final String REPORT_RANGE = "作用半径";
    public static final String REPORT_TICK_DELAY = "动作节流";
    public static final String REPORT_ROTATE_SILENT = "视角同步";
    /** 视角静默同步的两种取值（旧实现直接写死在报告里） */
    public static final String REPORT_ON = "§a§l已开启";
    public static final String REPORT_OFF = "§c§l已关闭";

    /** 世界就绪提示（旧 {@code onActivate} 原文） */
    public static final String MUST_IN_WORLD = "必须在进入世界后才能启动模块。";

    /** 自检缺项（旧 {@code selfCheck} 原文） */
    public static final String MISSING_NO_TARGET = "§a目标方块§f·未勾选任何可催熟方块";

    /** 无目标提示（旧 {@code onTick} 原文） */
    public static final String HINT_NO_TARGET_CROSSHAIR = "§7准星处没有可催熟的目标";
    public static final String HINT_NO_TARGET_NEARBY = "§7附近没有可催熟的目标";

    /** 骨粉耗尽 / 恢复（旧 {@code onTick} 原文） */
    public static final String NO_BONE_MEAL = "§c✗ 骨粉耗尽 §8▸ 已自动暂停";
    public static final String BONE_MEAL_RESTORED = "§a✓ 检测到骨粉 §8▸ 恢复工作";

    /** 准星对着未登记方块（旧 {@code getCrosshairTarget} 原文；方块名在中间拼接） */
    public static final String CROSSHAIR_UNLISTED_PREFIX = "§e准星对着 §f";
    public static final String CROSSHAIR_UNLISTED_SUFFIX = " §e不在目标列表，可前往设置添加";

    /** 自动降速播报（旧 {@code onAntiCheatDetected} 原文；反作弊名与「3 Tick」为旧字面量） */
    public static final String AUTO_THROTTLE_PREFIX = "检测到 ";
    public static final String AUTO_THROTTLE_SUFFIX = "，已自动提高动作节流到 3 Tick";

    // ── 旧 HUD 状态串（{@code getInfoString} 原文） ──

    public static final String HUD_NO_BONE_MEAL = "§c无骨粉";
    public static final String HUD_WAITING_CROSSHAIR = "§7等待准星";
    public static final String HUD_SCANNING = "§7扫描中";
}
