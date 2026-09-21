package com.yiyiaddon.feature.packetbreak.config;

/**
 * 发包秒破模块的用户可见文案唯一来源。
 *
 * <p><b>逐字资产</b>：设置名、设置描述、分组名、页签名、选择器文案全部来自旧项目
 * {@code tactical/packetbreak/PacketInstantBreak.java:111-272} 的设置声明；一个字都不改
 * （开发习惯第十六章：旧项目已有的中文显示文本必须逐字保留）。本类只做「同一句话只有一处」的收口，
 * 供控制台各页与概览页共用（第 169 条），不承担任何业务逻辑。</p>
 *
 * <p><b>选择器行文案</b>：旧项目的「目标方块」由旧框架的列表设置控件承载（框架自带「选择 / 清空」
 * 与候选窗口，不属迁移资产，第 34 条）；本项目改用通用选择器 {@code ui/screen/SelectorScreen}，
 * 行文案沿用本项目选择器行的既有措辞（与 {@code feature/vision/config/VisionTexts} 一致），
 * 不另造一套说法。</p>
 */
public final class PacketBreakTexts {

    private PacketBreakTexts() {
    }

    // ── 模块元数据 ──

    /** 模块中文显示名（模块列表、播报前缀、控制台标题共用） */
    public static final String MODULE_NAME = "发包秒破";

    /** 模块说明（模块页标题下与控制台标题下同一份）；按「一行放得下的中文短注」写，清单行不截断 */
    public static final String DESCRIPTION =
        "发数据包秒破方块，无视硬度";

    // ── 设置分组名（页签名 = 旧项目 4 个设置组名） ──

    public static final String GROUP_TARGET = "目标选择";
    public static final String GROUP_PACKET = "发包参数";
    public static final String GROUP_ANTICHEAT = "防同步与反作弊";
    public static final String GROUP_RENDER = "进度显示";

    /** 概览页签（旧项目没有概览页，本页只读运行状态，不新增任何设置项） */
    public static final String TAB_OVERVIEW = "概览";

    // ── 设置名（逐字 = 旧设置名） ──

    public static final String NAME_TARGET_MODE = "目标模式";
    public static final String NAME_BREAK_MODE = "破坏方式";
    public static final String NAME_RANGE = "扫描半径";
    public static final String NAME_TARGET_BLOCKS = "目标方块";
    public static final String NAME_DELAY = "方块间隔（tick）";
    public static final String NAME_ROTATE = "转向发包";
    public static final String NAME_AUTO_SWITCH = "自动换工具";
    public static final String NAME_SWING = "挥动手臂";
    public static final String NAME_OBSCURE_PROGRESS = "混淆破坏进度";
    public static final String NAME_BYPASS_ANTICHEAT = "绕过反作弊";
    public static final String NAME_RESPECT_LAG = "服务器卡顿自停";
    public static final String NAME_RENDER = "显示进度";
    public static final String NAME_ESP_STYLE = "框线样式";
    public static final String NAME_SHRINK_PROGRESS = "进度收缩";
    public static final String NAME_SIDE_COLOR = "未完成方块面色";
    public static final String NAME_LINE_COLOR = "未完成方块线色";
    public static final String NAME_READY_SIDE_COLOR = "可破坏方块面色";
    public static final String NAME_READY_LINE_COLOR = "可破坏方块线色";
    public static final String NAME_SHOW_PERCENT = "显示百分比";
    public static final String NAME_PROGRESS_COLOR = "百分比颜色";
    public static final String NAME_LABEL_STYLE = "标签内容";

    // ── 设置描述（逐字 = 旧设置描述，同时作为控制台行的悬停提示） ──

    public static final String DESC_TARGET_MODE =
        "瞄准破坏：按住左键瞄准目标方块发包挖掘；范围自动：自动扫描周围方块批量发包挖掘";
    public static final String DESC_BREAK_MODE =
        "极速卡点：按服务器 0.7 破坏阈值卡点停挖，最快合法速度；原版速度：进度满格才停挖，任何服务器都稳";
    public static final String DESC_RANGE = "范围自动模式下，以玩家为中心扫描的半径（格）";
    public static final String DESC_TARGET_BLOCKS = "范围自动模式下只挖这些方块；留空则挖所有可破坏方块";
    public static final String DESC_DELAY =
        "每个方块挖完确认后，开始挖下一个方块前的等待 tick 数，避免发包过快被踢";
    public static final String DESC_ROTATE = "挖掘时先发送视角转向包对准方块，服务端视角校验更宽松的服需要";
    public static final String DESC_AUTO_SWITCH =
        "挖掘前自动切换到最快工具后按该工具算破坏速度，挖完自动切回；不开则按当前手持物品算，工具不对可能挖不动";
    public static final String DESC_SWING = "发包挖掘时同步挥动手臂动画，看起来更像真人在挖";
    public static final String DESC_OBSCURE_PROGRESS =
        "破坏结束后额外刷 ABORT 包，掩盖方块破坏进度，避免其他玩家看到挖掘裂纹";
    public static final String DESC_BYPASS_ANTICHEAT =
        "仅强反作弊服务器（Grim 等）开启：破坏后额外补发 ABORT 包混淆破坏时序";
    public static final String DESC_RESPECT_LAG = "检测到服务器 TPS 过低时暂停发包，避免雪上加霜被踢";
    public static final String DESC_RENDER = "是否渲染正在发包挖掘的方块与进度";
    public static final String DESC_ESP_STYLE = "进度框的渲染样式：仅线条 / 仅面 / 线+面";
    public static final String DESC_SHRINK_PROGRESS =
        "方块框随破坏进度向中心收缩，直观体现挖掘进度（0%满格 → 100%缩到中心）";
    public static final String DESC_SIDE_COLOR = "正在挖掘中方块的面颜色";
    public static final String DESC_LINE_COLOR = "正在挖掘中方块的线颜色";
    public static final String DESC_READY_SIDE_COLOR = "进度已满、即将破坏方块的面颜色";
    public static final String DESC_READY_LINE_COLOR = "进度已满、即将破坏方块的线颜色";
    public static final String DESC_SHOW_PERCENT = "在方块上方显示挖掘进度百分比标签";
    public static final String DESC_PROGRESS_COLOR = "进度百分比标签的文字颜色";
    public static final String DESC_LABEL_STYLE =
        "百分比标签展示的内容：仅百分比 / 百分比+方块名 / 百分比+剩余tick";

    // ── 选择器行（沿用本项目选择器行既有措辞） ──

    public static final String SELECT = "§b选择";
    public static final String SELECT_HINT = "打开方块选择器：条目带图标、分组可折叠、左候选右已选两栏多选";
    public static final String CLEAR = "§7清空";
    public static final String CLEAR_HINT = "清空已选名单（名单为空时无动作）";
    public static final String SELECT_TITLE = "选择目标方块";
}
