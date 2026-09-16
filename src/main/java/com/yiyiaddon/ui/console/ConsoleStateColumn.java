package com.yiyiaddon.ui.console;

import com.yiyiaddon.ui.render.MinecraftText;

import java.util.function.Supplier;

/**
 * 控制台「状态列」宽度：同一页多行共用一个宽度，且<b>只增不减</b>。
 *
 * <p><b>为什么需要它：</b>{@code ConsoleRow} 的行内控件是整组右对齐排的
 * （{@code ConsoleRow#controlsStartX}：总宽从左往右累加，再从行右边界往回顶）。状态文字若按
 * 「当前文案实测宽度」给宽度，文案一变列宽就变 —— 排在前面的「点击选择」按钮会被顶着左右浮动。
 * 用户 2026-09-16 的名单页截图正是这个现象：{@code 已选 10 项} 与 {@code 未选择} 两行的按钮
 * 不在同一竖线上（原话「点击选择 按钮不对齐」）。</p>
 *
 * <p>口径照项目里既有的那一处正确实现（{@code StardewPlantingPage#stateColumnWidth()}）：</p>
 * <ul>
 *   <li><b>同页共用一个宽度</b>：各行都把自己当前的文案登记进来，列宽取本页最宽的那条；</li>
 *   <li><b>下界取「可能出现的最长文案」</b>（构造参数，如 {@code 已选 999 项}），计数项数再多也
 *       不会把按钮顶着走；</li>
 *   <li><b>只增不减</b>：某行从长文案换成短文案时列宽不回收 —— 回收会让整组控件左右跳一下，
 *       看起来仍然像「在位移」。</li>
 * </ul>
 *
 * <p>文字左对齐放在固定列里，等价于「不足补空白」，因此不需要真的往文案尾部补空格。</p>
 *
 * <p><b>为什么不做成通用排版件：</b>它只服务「控制台状态列」这一种排版（右对齐的控件组 + 只读状态
 * 文字），与 {@code CardLayout} / {@code GlassPanel} 那类与页面无关的几何件不是一类，故与
 * {@link ConsoleMetrics} / {@link ConsoleWidgets} 一起放在 {@code ui/console} 内。</p>
 */
public final class ConsoleStateColumn {

    /**
     * 量宽字号：必须与 {@code SettingText} 内部绘制状态文字的字号一致（同为 11），否则列宽与文字
     * 宽度不是同一把尺子，固定列会偏窄或偏宽。
     */
    private static final float FONT_SIZE = 11f;

    private final float minWidth;
    private float width;

    /**
     * @param longestText 该列可能出现的最长文案（如 {@code 已选 999 项} / {@code 未选择（共 999 项）}），
     *                    用作列宽下界
     */
    public ConsoleStateColumn(String longestText) {
        minWidth = Math.max(1f, MinecraftText.measure(longestText, FONT_SIZE, false));
        width = minWidth;
    }

    /** 共享列宽（任何时候都不小于构造时给的下界）。 */
    public float width() {
        return width;
    }

    /**
     * 登记一行的文案并返回共享列宽：直接当 {@code SettingText} 的宽度来源用。
     *
     * <p>{@code SettingText} 每帧绘制与每次命中都会问一次宽度，因此同页各行都会把自己的当前文案
     * 登记进来，列宽当帧即收敛到「本页最宽那条」。</p>
     */
    public float widthOf(Supplier<String> text) {
        String value = text == null ? null : text.get();
        if (value != null && !value.isEmpty()) {
            width = Math.max(width, MinecraftText.measure(value, FONT_SIZE, false));
        }
        return Math.max(width, minWidth);
    }
}
