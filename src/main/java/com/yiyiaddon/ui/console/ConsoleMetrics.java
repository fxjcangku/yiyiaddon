package com.yiyiaddon.ui.console;

/**
 * 控制台窗口的排版常量（原先集中在星露谷控制台窗口类里，2026-09-16 抽为通用件）。
 *
 * <p>值一字未改；星露谷控制台窗口仍以同名常量转发（{@code StardewConsoleScreen.SECTION_HEIGHT}
 * 等引用点不受影响），好让两套控制台永远同一套排版。</p>
 */
public final class ConsoleMetrics {

    /** 内容左右内边距 */
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
