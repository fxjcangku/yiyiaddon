package com.yiyiaddon.feature.fishing;

import com.yiyiaddon.core.module.Module;

/**
 * 星露谷钓鱼：<b>只占了入口，功能还没写</b>。
 *
 * <p>用户 2026-09-21：「在星露谷的分组下新建一个星露谷钓鱼模块，只是剪个入口而已，代码我晚点再做，
 * 中文注释写清楚开发中」。本类只做两件事，让这个「还没做的模块」在界面上不骗人：</p>
 *
 * <ol>
 *     <li>{@code description()} 写清「开发中」——模块行注释与模块页副标题共用它，
 *         长度按「一行放得下的中文短注」控制（166 号复盘第十一节）；</li>
 *     <li>{@link #environmentRefusal()} 常驻拒因：玩家真去开它时收到一条中文说明，
 *         而不是「已开启」之后什么都不发生（这条走的是运行时既有的环境闸门，
 *         不满足条件时静默等待，不会每次进服刷屏）。</li>
 * </ol>
 *
 * <p><b>行状态标记不再单独覆写</b>（同日追加：「星露谷钓鱼 跟星露谷农场这里没对齐 统改成未启动」）：
 * 原先把模块行右侧的红色文字覆写成「无法使用」，但它比同页其它行宽一个字，两行的状态圆点落不到
 * 同一条竖线上，宽的那一档还会顶到星标上；现在统一走默认口径（关着就是「未启动」，红色同一档），
 * 「这个模块还不能用」由行注释（「开发中，暂时无法使用」）与点击后的拒因说明表达。</p>
 *
 * <p><b>还没有的部分（留给后续实现）</b>：没有 {@code page()}（点进去落到通用占位页
 * {@code ModuleDetailPage}，显示「未接入」）、没有设置项、没有指令、没有事件订阅、
 * 没有 {@code onTick}。钓鱼玩法（鱼竿 / 鱼饵 / 浮漂 / 小游戏 / 鱼类产物 / 季节天气条件）
 * 动手前先读旧项目留下的资源参考 {@code D:/mcaddon/26.1.2/11-星露谷钓鱼参考/}
 * （玩法证据报告、鱼类与鱼饵 ID 索引、字体与图标索引、真机验证清单）。</p>
 *
 * <p>实现时把上面两条「占位说明」一并撤掉：补 {@code page()}、删 {@code environmentRefusal()} 覆写、
 * 按实际玩法重写 {@code description()}。</p>
 */
public final class StardewFishingModule extends Module {

    public static final String MODULE_ID = "stardew_fishing";
    public static final String MODULE_NAME = "星露谷钓鱼";

    /** 所属分类：与「星露谷农场」同组（分类注册见 {@code AddonModules#registerCategories}）。 */
    private static final String CATEGORY = "stardew";

    /**
     * 图标字形：暂借星露谷分类那枚 {@code \uE8CD}，它已在本项目分类头实机渲染，
     * 属开发习惯第 140 条「必须落在已验证集合内」里的一员。
     *
     * <p><b>不是钓鱼专属字形</b>：现成证据里没有钓鱼图标，也没有对候选码点做过字体覆盖验真，
     * 所以不硬猜一个新码点；实现钓鱼玩法时按第 140 条的流程（解析 cmap + 渲染 PNG 目视）
     * 换成合适的字形。</p>
     */
    private static final String ICON = "\uE8CD";

    /** 行注释（模块行 / 模块页副标题共用）：开发中 + 暂时用不了，一眼看清这模块还不能用。 */
    private static final String DESCRIPTION = "开发中，暂时无法使用";

    /** 玩家试图启用时的说明：配合模块名播报成「星露谷钓鱼：还在开发中，暂时无法使用」。 */
    private static final String REFUSAL = "还在开发中，暂时无法使用";

    public StardewFishingModule() {
        super(MODULE_ID, MODULE_NAME, CATEGORY, DESCRIPTION);
    }

    @Override
    public String name() {
        return "StardewFishing";
    }

    @Override
    public String icon() {
        return ICON;
    }

    /** 分类内排序：排在星露谷农场（10）之后。 */
    @Override
    public int order() {
        return 11;
    }

    /** 只剪了入口：功能没写，别让它被打开。 */
    @Override
    public String environmentRefusal() {
        return REFUSAL;
    }
}
