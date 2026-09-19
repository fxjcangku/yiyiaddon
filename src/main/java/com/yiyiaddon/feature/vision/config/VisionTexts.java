package com.yiyiaddon.feature.vision.config;

/**
 * 透视模块全部用户可见文案的唯一来源。
 *
 * <p><b>为什么集中一处</b>：本模块是新功能（旧项目没有对应物，见 92 号报告第二节），文案没有旧原文可对，
 * 因此把「模块名 / 描述 / 每个设置项的名称与描述 / 选择器标题与按钮 / 状态条取值」全部收在本类，
 * 便于逐条审计，也避免同一句话在控制台与说明里各写一份（第 169 条：同源不留两份）。</p>
 *
 * <p><b>配色口径</b>：状态色按第 114 条八级语义取（成功 {@code §a} / 失败 {@code §c} /
 * 信息 {@code §b} / 禁用 {@code §7}），界面提示语按第 213 条给可见灰字提示。</p>
 */
public final class VisionTexts {

    private VisionTexts() {
    }

    // ── 模块元数据 ──

    /** 模块中文名（同时作为播报前缀与模块列表显示名） */
    public static final String MODULE_NAME = "透视";

    /** 模块说明（模块页标题下与控制台标题下同一份） */
    public static final String DESCRIPTION = "透视方块与实体：目标自己选，框与射线可分别开关。";

    // ── 控制台页签 ──

    public static final String TAB_OVERVIEW = "概览";
    public static final String TAB_BLOCK = "方块";
    public static final String TAB_ENTITY = "实体";

    // ── 方块模式设置项 ──

    public static final String NAME_BLOCK_ENABLED = "方块透视";
    public static final String DESC_BLOCK_ENABLED = "打开后开始扫描并绘制目标方块；目标方块一个都没选时不画";

    public static final String NAME_BLOCK_TARGETS = "目标方块";
    public static final String DESC_BLOCK_TARGETS =
        "点击「选择」打开方块选择器：条目带图标、按分组折叠、左右两栏多选；默认什么都不选，由你自己挑";

    public static final String NAME_BLOCK_RANGE = "方块范围";
    public static final String DESC_BLOCK_RANGE =
        "以你为中心的水平半径（格）；竖直方向固定为上下各 64 格，所以脚下的方块也能看到";

    public static final String NAME_BLOCK_BOX = "显示框";
    public static final String DESC_BLOCK_BOX = "给目标方块画方框（关掉就只剩射线）";

    public static final String NAME_BLOCK_TRACER = "显示射线";
    public static final String DESC_BLOCK_TRACER = "从屏幕底部中心连一条线到目标中心（关掉就只剩框）";

    public static final String NAME_BLOCK_SHAPE = "方块框样式";
    public static final String DESC_BLOCK_SHAPE = "线框 / 面 / 两者";

    public static final String NAME_BLOCK_COLOR = "方块颜色";
    public static final String DESC_BLOCK_COLOR = "方块框与射线的颜色";

    // ── 实体模式设置项 ──

    public static final String NAME_ENTITY_ENABLED = "实体透视";
    public static final String DESC_ENTITY_ENABLED = "打开后开始绘制目标实体；目标实体一个都没选时不画";

    public static final String NAME_ENTITY_TARGETS = "目标实体";
    public static final String DESC_ENTITY_TARGETS =
        "点击「选择」打开实体选择器：条目带图标、按生物分类折叠、左右两栏多选；默认什么都不选，由你自己挑";

    public static final String NAME_ENTITY_RANGE = "实体范围";
    public static final String DESC_ENTITY_RANGE =
        "以你为中心的水平半径（格）；竖直方向不限制，所以脚下的实体也能看到";

    public static final String NAME_ENTITY_BOX = "显示框";
    public static final String DESC_ENTITY_BOX = "给目标实体画包围框（关掉就只剩射线）";

    public static final String NAME_ENTITY_TRACER = "显示射线";
    public static final String DESC_ENTITY_TRACER = "从屏幕底部中心连一条线到实体中心（关掉就只剩框）";

    public static final String NAME_ENTITY_SHAPE = "实体框样式";
    public static final String DESC_ENTITY_SHAPE = "线框 / 面 / 两者";

    public static final String NAME_ENTITY_COLOR = "实体颜色";
    public static final String DESC_ENTITY_COLOR = "实体框与射线的颜色";

    // ── 选择器行 ──

    /** 按钮文案：颜色码与文字分开两处，说明里引用不带色码的名字，避免拼出双层色码 */
    public static final String SELECT_NAME = "选择";
    public static final String SELECT = "§b" + SELECT_NAME;
    public static final String SELECT_HINT_BLOCK = "打开方块选择器：条目带图标、分组可折叠、左候选右已选两栏多选";
    public static final String SELECT_HINT_ENTITY = "打开实体选择器：条目带图标、按生物分类分组可折叠";
    public static final String CLEAR_NAME = "清空";
    public static final String CLEAR = "§7" + CLEAR_NAME;
    public static final String CLEAR_HINT = "清空已选名单（名单为空时无动作）";

    /** 选择器窗口标题（两个选择器各自一个，避免共用标题分不清在选什么） */
    public static final String SELECT_BLOCK_TITLE = "选择目标方块";
    public static final String SELECT_ENTITY_TITLE = "选择目标实体";

    // ── 状态条与概览读数（写法与其它控制台同一口径：§7标签 §f值） ──

    public static final String ON = "§a开";
    public static final String OFF = "§8关";
    public static final String NONE = "§8无";
    /** 扫描进度：一轮扫完全部区块后显示「已完成」，扫到一半显示百分比 */
    public static final String SCAN_DONE = "§a已完成";
}
