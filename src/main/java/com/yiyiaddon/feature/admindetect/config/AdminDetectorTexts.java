package com.yiyiaddon.feature.admindetect.config;

/**
 * 管理员检测的设置项名称与描述。
 *
 * <p><b>来源</b>：旧 {@code admdetector/AdminDetectorModule.java} 里
 * {@code IntSetting / BoolSetting / StringListSetting} 的 {@code .name(...)} 与 {@code .description(...)}
 * 原文，逐字搬运，改一字即不合格（第 96、97 条：配置名称与中文显示文本属不可随意修改资产）。</p>
 *
 * <p><b>两处例外（用户 2026-09-16 拍板，登记在 {@link AdminDetectorSettings} 类注释里）</b>：
 * 白名单 / 黑名单的描述尾句「逗号分隔多个名字」被替换 —— 承载控件已按用户要求改成在线玩家选择器；
 * 「显示与警报」三项是新增设置项，旧项目没有对应原文。</p>
 */
public final class AdminDetectorTexts {

    private AdminDetectorTexts() {
    }

    // ━━━ 检测（旧 {@code sgDetect}） ━━━

    public static final String NAME_RANGE = "检测范围（格）";
    public static final String DESC_RANGE = "危险玩家进入多少格内触发检测";

    public static final String NAME_SPECTATOR = "检测旁观者";
    public static final String DESC_SPECTATOR = "旁观模式玩家靠近即命中（管理员视察常见形态）";

    public static final String NAME_CREATIVE = "检测创造";
    public static final String DESC_CREATIVE = "创造模式玩家靠近即命中";

    public static final String NAME_INVISIBLE = "检测隐身";
    public static final String DESC_INVISIBLE = "隐身玩家靠近即命中（管理员隐身视察）";

    public static final String NAME_HIDDEN = "检测隐藏玩家";
    public static final String DESC_HIDDEN = "不在 Tab 列表的玩家靠近即命中（vanish 插件隐藏的管理员典型特征）";

    // ━━━ 名单（旧 {@code sgList}；描述尾句按用户拍板改写，见类注释） ━━━

    public static final String NAME_WHITELIST = "白名单（来了不退出）";
    public static final String DESC_WHITELIST = "白名单内玩家接近不检测不提示。点击按钮从在线玩家列表里选。";

    public static final String NAME_BLACKLIST = "黑名单（来了就退出）";
    public static final String DESC_BLACKLIST = "黑名单内玩家接近立即断线，无视危险特征。点击按钮从在线玩家列表里选。";

    // ━━━ 显示与警报（本项目新增，旧项目无对应原文） ━━━

    public static final String NAME_ESP_BOX = "ESP 画框";
    public static final String DESC_ESP_BOX = "命中时给危险玩家画一个红色描边框";

    public static final String NAME_TRACER = "射线";
    public static final String DESC_TRACER = "命中时从自己画一条红色射线到危险玩家";

    public static final String NAME_ALARM = "警报声";
    public static final String DESC_ALARM = "命中时播放警报音效";
}
