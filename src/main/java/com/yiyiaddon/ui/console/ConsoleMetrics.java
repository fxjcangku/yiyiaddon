package com.yiyiaddon.ui.console;

/**
 * 控制台窗口的排版常量（原先集中在星露谷控制台窗口类里，2026-09-16 抽为通用件）。
 *
 * <p>值一字未改；星露谷控制台窗口仍以同名常量转发（{@code StardewConsoleScreen.SECTION_HEIGHT}
 * 等引用点不受影响），好让两套控制台永远同一套排版。</p>
 *
 * <p><b>2026-09-16 收控制台行高时核对过这两个「没动」的值</b>（用户原话「控制台里面 也要缩小啊
 * 你只缩小外面的 控制台里面都没变」）：</p>
 * <ul>
 *   <li>{@link #PAD_X} 必须<b>保持 14</b>：外层的 {@code CompactRow} 用同一个数（它的注释写明
 *       「与控制台的 {@code ConsoleMetrics.PAD_X} 同值」），控制台的标签列与外面标签列落在同一竖线上，
 *       谁单独改谁就让两页的标签错开一列；</li>
 *   <li>{@link #SECTION_HEIGHT} 本来就是 24（= {@code ModuleRow.HEIGHT}，外面那一档），不需要再收。</li>
 * </ul>
 */
public final class ConsoleMetrics {

    /** 内容左右内边距（与外层 {@code CompactRow.PAD_X} 同值，标签列才对齐同一列） */
    public static final float PAD_X = 14f;
    /** 行标签字号 */
    public static final float LABEL_SIZE = 13f;
    /** 注释行字号 */
    public static final float NOTE_SIZE = 11f;
    /** 分区标题行高与字号（借 {@code Note} 画「§7§l标题」那种行） */
    public static final float SECTION_HEIGHT = 24f;
    public static final float SECTION_SIZE = 12f;
    /** 行内「重置」图标：Material Symbols refresh（旧项目此处是图标按钮，不是文字按钮） */
    public static final String GLYPH_RESET = "\uE5D5";
    /** tooltip 相对鼠标的偏移 */
    public static final float TIP_OFFSET_X = 14f;
    public static final float TIP_OFFSET_Y = 16f;

    private ConsoleMetrics() {
    }
}
