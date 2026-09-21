package com.yiyiaddon.ui.render;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.RRect;

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

    /**
     * 浮层文字基准色。
     *
     * <p>旧项目 tooltip 正文是白字，那是深色底；白色主题的浮层底色同样是白的，白字压白底等于看不见
     * （实机表现就是「悬停在设置上看不到中文注释」）。因此浅色主题改用主题主文字色，
     * 深色主题一个像素不改；文案里自带的 {@code §} 颜色码照旧覆盖基准色。</p>
     */
    private static int baseColor(ClickGuiThemeColors tc) {
        return tc != null && !tc.dark ? tc.primaryText : 0xFFFFFF;
    }

    private static String text;
    private static float anchorX;
    private static float anchorY;

    /** 顶部提示的停留时长、淡入与淡出时长（毫秒）。 */
    private static final long NOTICE_HOLD_MS = 1900L;
    private static final long NOTICE_FADE_IN_MS = 180L;
    private static final long NOTICE_FADE_OUT_MS = 300L;
    /** 顶部提示与视口顶边的距离（设计空间）。 */
    private static final float NOTICE_TOP = 22f;
    /**
     * 顶部提示的字号、内边距与圆角。
     *
     * <p>原本与悬停浮层同为 10（同一套度量，看起来是同一种框），用户 2026-09-21 实机反馈
     * 「有点小看不清」，因此正文提到面板正文档的 {@code 12}（与 {@code ModuleRow} 的模块名、
     * {@code TextLine} 的行位同级），内边距与圆角同步放宽一档。框的形状语言不变 —— 圆角比例、
     * 投影与材质仍与悬停浮层那一套同参，只是整体大一号。</p>
     */
    private static final float NOTICE_SIZE = 12f;
    private static final float NOTICE_PAD_X = 12f;
    private static final float NOTICE_PAD_Y = 8f;
    private static final float NOTICE_RADIUS = 8f;
    /** 进场时自上而下归位的距离与最小缩放、退场时额外上浮的距离：与面板「下沉 + 淡入」同一观感，幅度更轻 */
    private static final float NOTICE_RISE = 7f;
    private static final float NOTICE_EXIT_LIFT = 5f;
    private static final float NOTICE_MIN_SCALE = 0.94f;

    /** 顶部提示的文案与起止时刻（毫秒）；{@code null} = 当前没有提示。 */
    private static String noticeText;
    private static long noticeStartMs;
    private static long noticeEndMs;

    private TooltipLayer() {
    }

    /**
     * 弹一条屏幕顶部的玻璃提示（约两秒后自动淡出）。
     *
     * <p>用途：操作被拦下时给即时反馈（如选择器「只能选一个」）。与悬停浮层不同，它<b>不跟随指针</b>、
     * 固定在视口顶部居中，并<b>跨帧存活</b> — 因此单独一份状态，{@link #beginFrame()} 只清悬停浮层。</p>
     *
     * <p>重复调用只刷新同一条：文案替换、计时重置，不会叠出多条。</p>
     */
    public static void notify(String text) {
        if (text == null || text.isBlank()) return;
        long now = System.currentTimeMillis();
        noticeText = text;
        noticeStartMs = now;
        noticeEndMs = now + NOTICE_HOLD_MS;
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
        if (canvas == null) return;
        // 顶部提示独立于悬停浮层：它跨帧存活，悬停浮层为空时也必须画，因此不能排在下面的提前返回之后。
        if (alpha > 0.01f) drawNotice(canvas, viewportWidth, alpha);
        if (value == null || value.isEmpty() || alpha <= 0.01f) return;

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
        int base = baseColor(tc);
        for (String line : lines) {
            MinecraftText.draw(canvas, line, drawX + TIP_PAD, cursorY + TIP_SIZE, TIP_SIZE, base, alpha);
            cursorY += TIP_LINE;
        }
    }

    /**
     * 绘制顶部提示（{@link #notify}）。
     *
     * <p>到期的提示在这里顺手清掉，不需要外部计时器。</p>
     *
     * <p><b>材质与面板同源</b>（用户 2026-09-19：「顶上弹窗的ui优化一下 是不是主题一起联动的 跟个ui一样
     * 玻璃质感的淡出淡入的」）：投影 / 霜化 / 环境光 / 高光内描边四项与 {@code PanelScreen.drawPanel}
     * 逐项同参（连模糊开关的 0.62 / 0.94 两档都一致），颜色全部取自 {@link ClickGuiThemeColors} ——
     * 深浅主题、强调色一换，弹窗跟着换，不会出现一块与界面无关的色块。</p>
     *
     * <p><b>动效</b>：进场自上而下归位 + 由 0.94 放大到 1，退场反向（再上浮一点），位移与透明度同步过渡；
     * 与面板「下沉 + 淡入」是同一种观感，幅度按弹窗体量收轻。</p>
     */
    private static void drawNotice(Canvas canvas, float viewportWidth, float alpha) {
        String value = noticeText;
        if (value == null) return;
        long now = System.currentTimeMillis();
        long remain = noticeEndMs - now;
        if (remain <= 0L) {
            noticeText = null;
            return;
        }
        float enter = Math.min(1f, (now - noticeStartMs) / (float) NOTICE_FADE_IN_MS);
        float leave = Math.min(1f, remain / (float) NOTICE_FADE_OUT_MS);
        float progress = Math.min(enter, leave);
        float shownAlpha = alpha * Math.max(0f, progress);
        if (shownAlpha <= 0.01f) return;

        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float boxWidth = MinecraftText.measure(value, NOTICE_SIZE, false) + NOTICE_PAD_X * 2f;
        float boxHeight = NOTICE_SIZE + NOTICE_PAD_Y * 2f;
        float boxX = Math.max(NOTICE_PAD_X, (viewportWidth - boxWidth) / 2f);
        float boxY = NOTICE_TOP - (1f - enter) * NOTICE_RISE + (1f - leave) * NOTICE_EXIT_LIFT;
        float radius = NOTICE_RADIUS;
        float scale = NOTICE_MIN_SCALE + (1f - NOTICE_MIN_SCALE) * progress;

        canvas.save();
        try {
            float centerX = boxX + boxWidth / 2f;
            float centerY = boxY + boxHeight / 2f;
            canvas.translate(centerX, centerY);
            canvas.scale(scale, scale);
            canvas.translate(-centerX, -centerY);

            GlassPanel.shadow(canvas, boxX, boxY, boxWidth, boxHeight, radius, tc.shadow, shownAlpha, 1.15f);
            GlassPanel.frost(canvas, boxX, boxY, boxWidth, boxHeight, radius, tc.window,
                    AddonConfig.panelBlur ? 0.62f : 0.94f, shownAlpha);
            canvas.save();
            try {
                canvas.clipRRect(RRect.makeXYWH(boxX, boxY, boxWidth, boxHeight, radius), true);
                GlassPanel.ambientGlow(canvas, boxX, boxY, boxWidth, boxHeight, tc, shownAlpha, 0.46f);
                GlassPanel.rim(canvas, boxX, boxY, boxWidth, boxHeight, radius, tc.rim, shownAlpha, 0.26f);
            } finally {
                canvas.restore();
            }
            MinecraftText.draw(canvas, value, boxX + NOTICE_PAD_X, boxY + NOTICE_PAD_Y + NOTICE_SIZE,
                    NOTICE_SIZE, baseColor(tc), shownAlpha);
        } finally {
            canvas.restore();
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
