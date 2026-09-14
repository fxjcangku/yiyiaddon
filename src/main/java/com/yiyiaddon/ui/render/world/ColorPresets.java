package com.yiyiaddon.ui.render.world;

/**
 * 世界空间图形的颜色预设。
 *
 * <p>作为 ESP 颜色的<b>默认值来源</b>（`EspColor` 的初始色也取自这里），避免各模块各写一套 RGB 字面量。
 * 精细取色走调色板 `ColorPickerScreen`，本类只提供「一档一色」的快捷值。</p>
 */
public final class ColorPresets {

    private static final String[] NAMES = {"红", "绿", "蓝", "青", "黄", "紫", "白", "橙", "黑"};
    private static final int[] RGB = {
            0xFF4C4C, 0x4CFF4C, 0x4C8CFF, 0x4CFFFF, 0xFFFF4C, 0xC04CFF, 0xFFFFFF, 0xFF9C4C, 0x1A1A1A
    };

    /** 填充面的默认不透明度：足够看清轮廓，又不遮住方块本体。 */
    public static final int DEFAULT_FILL_ALPHA = 0x30;

    private ColorPresets() {
    }

    /** 预设名，顺序与 {@link #rgb(int)} 下标一致。 */
    public static String[] names() {
        return NAMES.clone();
    }

    public static int count() {
        return RGB.length;
    }

    /** 取不透明 RGB；下标越界时回落第一个预设。 */
    public static int rgb(int index) {
        return rgb(index, 0xFF);
    }

    /** 取带指定 alpha 的 ARGB（Skija 用 ARGB 顺序）。 */
    public static int rgb(int index, int alpha) {
        int value = RGB[clamp(index)];
        return ((alpha & 0xFF) << 24) | value;
    }

    /** 下标越界时回落第一个预设。 */
    private static int clamp(int index) {
        if (index < 0 || index >= RGB.length) return 0;
        return index;
    }
}
