package com.yiyiaddon.ui.render;

import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * 壳级悬停浮层：控件在绘制时登记说明，屏幕骨架在最末统一绘制。
 *
 * <p>旧项目每个设置 / 按钮悬停都会浮出一段带 {@code §} 颜色码、可能多行的说明；新壳原本只有
 * 「行内截断提示」，放不下一行长说明，更放不下多行。这里把「登记 + 帧末统一绘制」抽成壳级能力：
 * 控件在绘制时调 {@link #show}，屏幕骨架每帧 {@link #beginFrame} 并在最末调用 {@link #draw}，
 * 浮层便永远盖在所有内容之上，不会被后续行覆盖。</p>
 *
 * <p>文本逐行经 {@link MinecraftText} 绘制，{@code §} 颜色码逐字保留；坐标沿用设计空间
 * （与各控件收到的鼠标坐标同一坐标系），因此调用方需在面板变换之后、内容绘制之后调用
 * {@link #draw}。</p>
 *
 * <p>登记口径：一帧只保留<b>最后一条</b>登记（后登记者覆盖前者），行为最简且可预期。</p>
 */
public final class TooltipLayer {

    private static final float TIP_SIZE = 10f;
    private static final float TIP_LINE = 12f;
    private static final float TIP_PAD = 6f;
    private static final float TIP_RADIUS = 6f;
    private static final float TIP_MAX_WIDTH = 320f;
    /** 相对鼠标的锚点偏移：浮层落在指针右下方，再按视口贴边修正。 */
    private static final float TIP_OFFSET_X = 14f;
    private static final float TIP_OFFSET_Y = 16f;
    /** 浮层文字基准色（旧项目 tooltip 正文为白字，行内颜色码自行覆盖）。 */
    private static final int TIP_COLOR = 0xFFFFFF;

    private static String text;
    private static float anchorX;
    private static float anchorY;

    private TooltipLayer() {
    }

    /** 帧首清空本帧登记（由屏幕骨架在开始绘制时调用）。 */
    public static void beginFrame() {
        text = null;
    }

    /**
     * 登记本帧要显示的浮层说明。
     *
     * @param text   说明文字，可含 {@code §} 颜色码与 {@code \n} 换行；空则忽略
     * @param mouseX 设计空间下的指针横坐标
     * @param mouseY 设计空间下的指针纵坐标
     */
    public static void show(String text, float mouseX, float mouseY) {
        if (text == null || text.isBlank()) return;
        TooltipLayer.text = text;
        anchorX = mouseX;
        anchorY = mouseY;
    }

    /**
     * 绘制并清空本帧登记。
     *
     * <p>折行后先按最长行与行数算出浮层宽高，再画背景与描边，最后逐行绘制文字。</p>
     *
     * @param viewportWidth  设计空间下的可用宽度（贴边修正用，不越出右边界）
     * @param viewportHeight 设计空间下的可用高度（贴边修正用，不越出下边界）
     * @param alpha          整体不透明度，跟随面板淡入淡出
     */
    public static void draw(Canvas canvas, float viewportWidth, float viewportHeight, float alpha) {
        String value = text;
        text = null;
        if (canvas == null || value == null || value.isEmpty() || alpha <= 0.01f) return;

        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float maxWidth = Math.min(TIP_MAX_WIDTH, Math.max(TIP_PAD * 2f, viewportWidth - TIP_PAD * 2f));
        List<String> lines = wrap(value, maxWidth);

        // 行数与折行后高度在画背景前先算好（背景尺寸由它决定）
        float tipWidth = 0f;
        for (String line : lines) tipWidth = Math.max(tipWidth, MinecraftText.measure(line, TIP_SIZE, false));
        tipWidth += TIP_PAD * 2f;
        float tipHeight = lines.size() * TIP_LINE + TIP_PAD * 2f;

        // 贴边修正：跟随指针右下，但不越出视口右 / 下边界，也不越出左上边界
        float drawX = Math.max(0f, Math.min(anchorX + TIP_OFFSET_X, viewportWidth - tipWidth));
        float drawY = Math.max(0f, Math.min(anchorY + TIP_OFFSET_Y, viewportHeight - tipHeight));

        GlassPanel.shadow(canvas, drawX, drawY, tipWidth, tipHeight, TIP_RADIUS, tc.shadow, alpha, 0.9f);
        GlassPanel.frost(canvas, drawX, drawY, tipWidth, tipHeight, TIP_RADIUS, tc.window, 0.94f, alpha);
        GlassPanel.rim(canvas, drawX, drawY, tipWidth, tipHeight, TIP_RADIUS, tc.rim, alpha, 0.22f);

        float cursorY = drawY + TIP_PAD;
        for (String line : lines) {
            MinecraftText.draw(canvas, line, drawX + TIP_PAD, cursorY + TIP_SIZE, TIP_SIZE, TIP_COLOR, alpha);
            cursorY += TIP_LINE;
        }
    }

    /**
     * 折行：先按 {@code \n} 分行，再把每行按最大宽度折到下一行。
     *
     * <p>宽度按可见宽度计算（{@code §} 颜色码整对带过、不占宽度），因此带颜色码的文案折行位置
     * 与绘制结果一致。</p>
     */
    private static List<String> wrap(String text, float maxWidth) {
        List<String> lines = new ArrayList<>();
        for (String paragraph : text.split("\n", -1)) {
            if (paragraph.isEmpty()) {
                lines.add("");
                continue;
            }
            StringBuilder line = new StringBuilder();
            float width = 0f;
            for (int i = 0; i < paragraph.length(); ) {
                int codePoint = paragraph.codePointAt(i);
                int charCount = Character.charCount(codePoint);
                String chunk = paragraph.substring(i, i + charCount);
                i += charCount;
                // 颜色码整对带过，宽度由 MinecraftText 解析后为 0
                if (codePoint == '\u00A7' && i < paragraph.length()) {
                    chunk += paragraph.charAt(i);
                    i++;
                }
                float chunkWidth = MinecraftText.measure(chunk, TIP_SIZE, false);
                if (width + chunkWidth > maxWidth && line.length() > 0) {
                    lines.add(line.toString());
                    line.setLength(0);
                    width = 0f;
                }
                line.append(chunk);
                width += chunkWidth;
            }
            lines.add(line.toString());
        }
        return lines;
    }
}
