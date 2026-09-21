package com.yiyiaddon.ui.render;

import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.RRect;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * HUD 顶部横幅：在游戏画面里画一条状态栏式玻璃提示，用于「管理员消息」这类必须当场看到的内容。
 *
 * <p><b>为什么不是聊天栏那一行</b>（用户 2026-09-21：「这个回复 能不能做成 ui 弹窗 状态栏那种
 * 这个停留久一点 10 秒」）：管理员消息原先直接 {@code ClientChat.raw} 进聊天栏，混在服务器刷屏里
 * 一眼看不见，还拖着一句「(使用 .回复 &lt;内容&gt; 回复)」。改为顶部横幅后，标题行、正文、
 * 底部提示分三层排布，停留 {@link #HOLD_MS} 十秒。</p>
 *
 * <p><b>为什么由 {@code GuiRendererMixin} 驱动、而不是自己注册 HUD 回调</b>：本项目所有自绘内容
 * 都走同一条 Skija 画布路径（世界叠加层也在那里取画布），多一条 HUD 注册点就会多一份 GL 状态与
 * 画布归属的坑。画布口径见 {@link SkiaGlBackend#begin(int)}：它已按 GUI 缩放做过变换，
 * 因此这里的坐标一律是 <b>GUI 逻辑坐标</b>。</p>
 *
 * <p><b>计时从「真正上屏」那一帧开始</b>：面板（{@link SkiaScreen} 子类）开着时整个 HUD 被盖住、
 * 画了也看不见，此时既不绘制也不计时，等面板关掉再开始十秒 —— 否则玩家一开面板就等于把消息吞掉了。</p>
 *
 * <p><b>材质与面板同源</b>：投影 / 霜化 / 环境光 / 高光内描边四项与 {@code TooltipLayer} 的顶部提示
 * 同一套参数与同一份主题取色，换主题时横幅跟着换，不会出现一块与界面无关的色块。</p>
 */
public final class HudBanner {

    /** 单条横幅：标题行 + 正文 + 底部提示（提示为空串时不占行）。 */
    private record Entry(String title, String body, String hint) {
    }

    /** 停留时长（毫秒）：用户 2026-09-21 指定的十秒。 */
    private static final long HOLD_MS = 10_000L;
    private static final long FADE_IN_MS = 220L;
    private static final long FADE_OUT_MS = 420L;

    /** 待显示队列上限：管理员连发时逐条排队，超过上限丢最早一条（宁可少显示，不无限堆积）。 */
    private static final int MAX_QUEUE = 5;

    /** 横幅与视口顶边的距离（GUI 逻辑坐标）。 */
    private static final float TOP = 16f;
    private static final float OUTER_MARGIN = 10f;
    private static final float PAD_X = 14f;
    private static final float PAD_Y = 10f;
    private static final float RADIUS = 10f;
    private static final float TITLE_SIZE = 9.5f;
    private static final float BODY_SIZE = 13f;
    private static final float BODY_MIN_SIZE = 9f;
    private static final float HINT_SIZE = 9.5f;
    /** 块间距（标题 / 正文 / 提示之间）与正文行距。 */
    private static final float BLOCK_GAP = 5f;
    private static final float BODY_LINE_GAP = 3f;
    /** 正文缩字号的触发行数：短消息不缩字号，超过它才逐档缩到 {@link #BODY_MIN_SIZE} 为止。 */
    private static final int BODY_LINES_KEEP_SIZE = 2;
    /** 标题行前的强调色圆点与它到标题的距离。 */
    private static final float DOT = 4f;
    private static final float DOT_GAP = 6f;
    /** 进场自上而下归位、退场额外上浮的距离：与面板顶部提示同一观感，幅度按横幅体积收轻。 */
    private static final float RISE = 8f;
    private static final float EXIT_LIFT = 6f;

    private static final Queue<Entry> QUEUE = new ConcurrentLinkedQueue<>();
    private static final SkiaGlBackend BACKEND = new SkiaGlBackend();

    /** 正在显示的横幅与它的时间窗；只在渲染线程读写。 */
    private static Entry showing;
    private static long startMs;
    private static long endMs;

    private HudBanner() {
    }

    /**
     * 排队一条横幅；正文为空则忽略。
     *
     * <p>可在任意线程调用（消息轮询在后台线程）。队列满时丢最早一条。</p>
     *
     * @param title 标题行（如「管理员消息」）
     * @param body  正文
     * @param hint  底部提示（如「使用 .回复 &lt;内容&gt; 回复」）；为空则不占行
     */
    public static void show(String title, String body, String hint) {
        if (body == null || body.isBlank()) return;
        while (QUEUE.size() >= MAX_QUEUE) QUEUE.poll();
        QUEUE.add(new Entry(title == null || title.isBlank() ? "提示" : title, body, hint == null ? "" : hint));
    }

    /**
     * 清空待显示队列与当前横幅。
     *
     * <p>断线时调用：上一个服务器的管理员消息不该在新服务器里冒出来。</p>
     */
    public static void clear() {
        QUEUE.clear();
        showing = null;
        startMs = 0L;
        endMs = 0L;
    }

    /**
     * 每帧绘制入口，由 {@code GuiRendererMixin} 在 {@code GuiRenderer#render} 的 HEAD 调用。
     *
     * <p>没有待显示内容时是一次空判，不开画布、不产生任何 GL 开销。</p>
     */
    public static void render() {
        // 面板开着时整个 HUD 被面板盖住：这一帧不画、也不计时（计时从真正上屏那一帧起算）
        if (SkiaScreen.isOpen()) return;

        long now = System.currentTimeMillis();
        if (showing == null) {
            showing = QUEUE.poll();
            if (showing == null) return;
            startMs = now;
            endMs = now + HOLD_MS;
        }

        long remain = endMs - now;
        if (remain <= 0L) {
            showing = null;
            startMs = 0L;
            endMs = 0L;
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client == null || client.getWindow() == null) return;

        float enter = Math.min(1f, (now - startMs) / (float) FADE_IN_MS);
        float leave = Math.min(1f, remain / (float) FADE_OUT_MS);
        float alpha = Math.max(0f, Math.min(enter, leave));
        if (alpha <= 0.01f) return;

        Canvas canvas = BACKEND.begin(SkiaGlBackend.mainFramebufferId());
        if (canvas == null) return;
        try {
            draw(canvas, client.getWindow().getGuiScaledWidth(), alpha, enter, leave);
        } finally {
            BACKEND.end();
        }
    }

    /** 画一条横幅：先按内容量出块尺寸，再画底与文字（尺寸计算与绘制顺序逐项对应）。 */
    private static void draw(Canvas canvas, float viewportWidth, float alpha, float enter, float leave) {
        Entry entry = showing;
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();

        // 正文先按上限宽度折行；行数过多才逐档缩字号（短消息始终保持 BODY_SIZE）
        float textLimit = Math.max(80f, viewportWidth - (OUTER_MARGIN + PAD_X) * 2f);
        float bodySize = BODY_SIZE;
        List<String> body = MinecraftText.wrap(entry.body(), bodySize, textLimit);
        while (body.size() > BODY_LINES_KEEP_SIZE + 1 && bodySize > BODY_MIN_SIZE) {
            bodySize = Math.max(BODY_MIN_SIZE, bodySize - 0.5f);
            body = MinecraftText.wrap(entry.body(), bodySize, textLimit);
        }

        boolean hasHint = !entry.hint().isBlank();
        float titleWidth = DOT + DOT_GAP + MinecraftText.measure(entry.title(), TITLE_SIZE, true);
        float bodyWidth = 0f;
        for (String line : body) bodyWidth = Math.max(bodyWidth, MinecraftText.measure(line, bodySize, false));
        float hintHeight = hasHint ? HINT_SIZE : 0f;
        float hintWidth = hasHint ? MinecraftText.measure(entry.hint(), HINT_SIZE, false) : 0f;

        float contentWidth = Math.max(titleWidth, Math.max(bodyWidth, hintWidth));
        float bodyHeight = body.size() * bodySize + Math.max(0, body.size() - 1) * BODY_LINE_GAP;
        float contentHeight = TITLE_SIZE + BLOCK_GAP + bodyHeight + (hasHint ? BLOCK_GAP + hintHeight : 0f);

        float boxWidth = Math.min(contentWidth + PAD_X * 2f, viewportWidth - OUTER_MARGIN * 2f);
        float boxHeight = contentHeight + PAD_Y * 2f;
        float boxX = Math.max(OUTER_MARGIN, (viewportWidth - boxWidth) / 2f);
        float boxY = TOP - (1f - enter) * RISE + (1f - leave) * EXIT_LIFT;

        GlassPanel.shadow(canvas, boxX, boxY, boxWidth, boxHeight, RADIUS, tc.shadow, alpha, 1.05f);
        GlassPanel.frost(canvas, boxX, boxY, boxWidth, boxHeight, RADIUS, tc.window, 0.93f, alpha);
        canvas.save();
        try {
            canvas.clipRRect(RRect.makeXYWH(boxX, boxY, boxWidth, boxHeight, RADIUS), true);
            GlassPanel.ambientGlow(canvas, boxX, boxY, boxWidth, boxHeight, tc, alpha, 0.42f);
            GlassPanel.rim(canvas, boxX, boxY, boxWidth, boxHeight, RADIUS, tc.rim, alpha, 0.26f);
        } finally {
            canvas.restore();
        }

        float textX = boxX + PAD_X;
        float cursorY = boxY + PAD_Y;

        GlassPanel.fill(canvas, textX, cursorY + (TITLE_SIZE - DOT) * 0.5f, DOT, DOT, DOT * 0.5f, tc.accent, alpha);
        MinecraftText.draw(canvas, entry.title(), textX + DOT + DOT_GAP, cursorY + TITLE_SIZE,
                TITLE_SIZE, tc.accent, alpha, true);
        cursorY += TITLE_SIZE + BLOCK_GAP;

        for (int i = 0; i < body.size(); i++) {
            MinecraftText.draw(canvas, body.get(i), textX, cursorY + bodySize, bodySize, tc.primaryText, alpha);
            cursorY += bodySize;
            if (i < body.size() - 1) cursorY += BODY_LINE_GAP;
        }

        if (hasHint) {
            cursorY += BLOCK_GAP;
            MinecraftText.draw(canvas, entry.hint(), textX, cursorY + HINT_SIZE, HINT_SIZE, tc.mutedText, alpha);
        }
    }
}
