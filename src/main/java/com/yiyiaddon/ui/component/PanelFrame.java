package com.yiyiaddon.ui.component;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.anim.Easing;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;

/**
 * 面板几何：设计空间尺寸、整体缩放、开合动画与鼠标坐标互转。
 *
 * <p>设计空间固定为「窗口像素的一半」，面板在该空间内居中并按需等比缩小，再映射到 GUI
 * 逻辑坐标，因此面板在任何窗口尺寸与 GUI 缩放下都居中且不会超出窗口。主界面与模块页共用这
 * 套几何，设计尺寸同为 {@link #CARD_W} x {@link #CARD_H}，窗口位置、圆角与整体缩放因此完全
 * 一致。</p>
 *
 * <p>绘制与命中测试必须用同一次 {@link #update} 得到的 {@link #scale()}：两边各算一次会在
 * 动画期间产生偏差。</p>
 */
public final class PanelFrame {

    /** 面板基准尺寸（设计空间单位），主界面与模块页共用。 */
    public static final float CARD_W = 740f;
    public static final float CARD_H = 500f;
    /** 面板基准圆角。 */
    public static final float CARD_RADIUS = 22f;
    /** 屏幕外边距：面板与窗口边缘的总留白（设计空间单位）。 */
    private static final float SCREEN_MARGIN = 24f;

    private static final float OPEN_DURATION = 0.16f;
    private static final float OPEN_MIN_SCALE = 0.88f;
    private static final float UI_SCALE_SMOOTHING = 12f;
    private static final float[] UI_SCALE_OPTIONS = {0.75f, 1.0f, 1.25f};

    private float cardW = CARD_W;
    private float cardH = CARD_H;
    private float designWidth = 1f;
    private float designHeight = 1f;
    private float cardX;
    private float cardY;
    private float scale = 1f;
    private float animationScale = 1f;
    private float animatedUiScale = -1f;
    private float openProgress;

    private boolean closing;

    /** 主界面尺寸的面板。 */
    public PanelFrame() {
    }

    /** 指定设计尺寸的面板。 */
    public PanelFrame(float cardWidth, float cardHeight) {
        setDesignSize(cardWidth, cardHeight);
    }

    /** 更新设计尺寸。 */
    public void setDesignSize(float cardWidth, float cardHeight) {
        this.cardW = Math.max(1f, cardWidth);
        this.cardH = Math.max(1f, cardHeight);
    }

    /** 面板设计宽度。 */
    public float cardWidth() {
        return cardW;
    }

    /** 面板设计高度。 */
    public float cardHeight() {
        return cardH;
    }

    /** 请求关闭：开合动画反向播放。 */
    public void beginClose() {
        closing = true;
    }

    /**
     * 推进一帧并刷新几何。
     *
     * @param dt 帧间隔（秒）；传 0 只刷新几何，不推进动画
     * @return true 表示关闭动画已播放完，界面可以真正关闭
     */
    public boolean update(Minecraft minecraft, float dt) {
        float step = Math.max(0f, dt) / OPEN_DURATION;
        openProgress = Easing.clamp01(closing ? openProgress - step : openProgress + step);
        animationScale = OPEN_MIN_SCALE + (1f - OPEN_MIN_SCALE) * animationAlpha();

        float targetUiScale = UI_SCALE_OPTIONS[Math.max(0, Math.min(AddonConfig.uiScale, UI_SCALE_OPTIONS.length - 1))];
        if (animatedUiScale < 0f) animatedUiScale = targetUiScale;
        animatedUiScale += (targetUiScale - animatedUiScale) * Math.min(1f, Math.max(0f, dt) * UI_SCALE_SMOOTHING);
        if (Math.abs(animatedUiScale - targetUiScale) < 0.001f) animatedUiScale = targetUiScale;

        designWidth = designSpace(minecraft == null ? 0 : minecraft.getWindow().getWidth());
        designHeight = designSpace(minecraft == null ? 0 : minecraft.getWindow().getHeight());
        cardX = (designWidth - cardW) / 2f;
        cardY = (designHeight - cardH) / 2f;

        // 等比缩放上限必须把「界面大小」一起算进去：只按 100% 计算会让 125% 把面板顶出窗口
        float fitX = (designWidth - SCREEN_MARGIN) / (cardW * animatedUiScale);
        float fitY = (designHeight - SCREEN_MARGIN) / (cardH * animatedUiScale);
        float fit = Math.max(0.05f, Math.min(1f, Math.min(fitX, fitY)));
        float guiScale = minecraft == null ? 2f : Math.max(1f, (float) minecraft.getWindow().getGuiScale());
        scale = fit * 2f / guiScale * animatedUiScale * animationScale;
        return closing && openProgress <= 0.005f;
    }

    /** 面板左上角在设计空间中的横坐标。 */
    public float cardX() {
        return cardX;
    }

    /** 面板左上角在设计空间中的纵坐标。 */
    public float cardY() {
        return cardY;
    }

    /** 当前缩放，绘制与命中测试共用。 */
    public float scale() {
        return scale;
    }

    /** 开合淡入系数，0 表示刚打开。 */
    public float animationAlpha() {
        return Easing.easeOutCubic(openProgress);
    }

    /** 叠加开合动画后的圆角，保证缩放期间圆角视觉一致。 */
    public float cardRadius() {
        return CARD_RADIUS / animationScale;
    }

    /** 设计空间换算：窗口物理像素的一半，与 GUI 缩放无关。 */
    private static float designSpace(int windowPixels) {
        return Math.max(1f, Math.round(windowPixels * 0.5f));
    }

    /** 把画布坐标系变换到设计空间；调用方负责配对的 canvas.save()/restore()。 */
    public void applyTransform(Canvas canvas, int screenWidth, int screenHeight) {
        canvas.translate(screenWidth / 2f, screenHeight / 2f);
        canvas.scale(scale, scale);
        canvas.translate(-designWidth * 0.5f, -designHeight * 0.5f);
    }

    /** GUI 逻辑坐标 → 设计空间横坐标。 */
    public float toDesignX(double mouseX, int screenWidth) {
        return designWidth * 0.5f + ((float) mouseX - screenWidth * 0.5f) / scale;
    }

    /** GUI 逻辑坐标 → 设计空间纵坐标。 */
    public float toDesignY(double mouseY, int screenHeight) {
        return designHeight * 0.5f + ((float) mouseY - screenHeight * 0.5f) / scale;
    }

    /** 设计空间横坐标转换为 GUI 逻辑坐标，供面板背景采样与实际绘制严格对齐。 */
    public float toScreenX(float designX, int screenWidth) {
        return screenWidth * 0.5f + (designX - designWidth * 0.5f) * scale;
    }

    /** 设计空间纵坐标转换为 GUI 逻辑坐标，供面板背景采样与实际绘制严格对齐。 */
    public float toScreenY(float designY, int screenHeight) {
        return screenHeight * 0.5f + (designY - designHeight * 0.5f) * scale;
    }

    /** 设计空间长度转换为 GUI 逻辑长度。 */
    public float toScreenLength(float designLength) {
        return designLength * scale;
    }
}
