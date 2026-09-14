package com.yiyiaddon.ui.render.world;

import com.google.gson.JsonObject;

/**
 * ESP 用的颜色值：RGB + 透明度 + 彩虹开关。
 *
 * <p>模块侧持有本对象即可，绘制时调 {@link #argb()} 拿到「此刻应该用的颜色」——
 * 彩虹开启时它按时间返回循环色相，关闭时返回固定 RGB。因此模块不需要关心变色时机。</p>
 *
 * <p>可变对象：调色板窗口直接改它，渲染侧立刻生效。</p>
 */
public final class EspColor {

    private static final String SUFFIX_RGB = "Rgb";
    private static final String SUFFIX_ALPHA = "Alpha";
    private static final String SUFFIX_RAINBOW = "Rainbow";
    private static final String SUFFIX_SPEED = "RainbowSpeed";
    private static final String SUFFIX_OFFSET = "RainbowOffset";

    /** 默认取第一个预设色，避免与 {@link ColorPresets} 出现第二份定义。 */
    private int rgb = ColorPresets.rgb(0, 0xFF) & 0xFFFFFF;
    private int alpha = 0xFF;
    private boolean rainbow;
    private double rainbowSpeed = Rainbow.DEFAULT_SPEED;
    private double rainbowOffset;

    public EspColor() {
    }

    public EspColor(int rgb, int alpha) {
        this.rgb = rgb & 0xFFFFFF;
        this.alpha = alpha & 0xFF;
    }

    /** 从颜色预设建立：{@code presetIndex} 对应 {@link ColorPresets#rgb(int)} 的下标。 */
    public static EspColor preset(int presetIndex, int alpha) {
        return new EspColor(ColorPresets.rgb(presetIndex, 0xFF), alpha);
    }

    // ── 取值 / 链式赋值 ──

    public int rgb() {
        return rgb;
    }

    public EspColor rgb(int value) {
        this.rgb = value & 0xFFFFFF;
        return this;
    }

    public int alpha() {
        return alpha;
    }

    public EspColor alpha(int value) {
        this.alpha = value & 0xFF;
        return this;
    }

    public boolean rainbow() {
        return rainbow;
    }

    public EspColor rainbow(boolean value) {
        this.rainbow = value;
        return this;
    }

    public double rainbowSpeed() {
        return rainbowSpeed;
    }

    public EspColor rainbowSpeed(double value) {
        this.rainbowSpeed = Rainbow.clampSpeed(value);
        return this;
    }

    public double rainbowOffset() {
        return rainbowOffset;
    }

    public EspColor rainbowOffset(double value) {
        this.rainbowOffset = value;
        return this;
    }

    // ── 解析 ──

    /** 此刻应用于绘制的 ARGB。 */
    public int argb() {
        if (rainbow) return Rainbow.argb(rainbowSpeed, rainbowOffset, alpha);
        return ((alpha & 0xFF) << 24) | rgb;
    }

    /** 此刻的 RGB（不含透明度），供不接受 alpha 的接口使用。 */
    public int currentRgb() {
        return argb() & 0xFFFFFF;
    }

    /** 当前色相 0-1，供调色板指示器定位。 */
    public float hue() {
        int current = rainbow ? currentRgb() : rgb;
        float[] hsb = new float[3];
        java.awt.Color.RGBtoHSB((current >> 16) & 0xFF, (current >> 8) & 0xFF, current & 0xFF, hsb);
        return hsb[0];
    }

    public float saturation() {
        int current = rainbow ? currentRgb() : rgb;
        float[] hsb = new float[3];
        java.awt.Color.RGBtoHSB((current >> 16) & 0xFF, (current >> 8) & 0xFF, current & 0xFF, hsb);
        return hsb[1];
    }

    public float brightness() {
        int current = rainbow ? currentRgb() : rgb;
        float[] hsb = new float[3];
        java.awt.Color.RGBtoHSB((current >> 16) & 0xFF, (current >> 8) & 0xFF, current & 0xFF, hsb);
        return hsb[2];
    }

    /** 按 HSV 写回 RGB，并关闭彩虹（手动取色即视为定色）。 */
    public void applyHsv(float hue, float saturation, float brightness, boolean keepRainbow) {
        int packed = java.awt.Color.HSBtoRGB(clamp01(hue), clamp01(saturation), clamp01(brightness)) & 0xFFFFFF;
        this.rgb = packed;
        if (!keepRainbow) this.rainbow = false;
    }

    private static float clamp01(float value) {
        if (value < 0f) return 0f;
        if (value > 1f) return 1f;
        return value;
    }

    // ── 持久化 ──

    public void load(JsonObject json, String prefix) {
        if (json == null || prefix == null) return;
        if (json.has(prefix + SUFFIX_RGB)) rgb = json.get(prefix + SUFFIX_RGB).getAsInt() & 0xFFFFFF;
        if (json.has(prefix + SUFFIX_ALPHA)) alpha = json.get(prefix + SUFFIX_ALPHA).getAsInt() & 0xFF;
        if (json.has(prefix + SUFFIX_RAINBOW)) rainbow = json.get(prefix + SUFFIX_RAINBOW).getAsBoolean();
        if (json.has(prefix + SUFFIX_SPEED)) rainbowSpeed = Rainbow.clampSpeed(json.get(prefix + SUFFIX_SPEED).getAsDouble());
        if (json.has(prefix + SUFFIX_OFFSET)) rainbowOffset = json.get(prefix + SUFFIX_OFFSET).getAsDouble();
    }

    public void save(JsonObject json, String prefix) {
        if (json == null || prefix == null) return;
        json.addProperty(prefix + SUFFIX_RGB, rgb);
        json.addProperty(prefix + SUFFIX_ALPHA, alpha);
        json.addProperty(prefix + SUFFIX_RAINBOW, rainbow);
        json.addProperty(prefix + SUFFIX_SPEED, rainbowSpeed);
        json.addProperty(prefix + SUFFIX_OFFSET, rainbowOffset);
    }
}
