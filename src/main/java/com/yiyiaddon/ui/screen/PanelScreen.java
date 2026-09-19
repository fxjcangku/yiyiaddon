package com.yiyiaddon.ui.screen;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.ui.component.BackButton;
import com.yiyiaddon.ui.component.ButtonRow;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.PanelFrame;
import com.yiyiaddon.ui.component.ScrollViewport;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.ImeBridge;
import com.yiyiaddon.ui.render.SkiaGlBackend;
import com.yiyiaddon.ui.render.SkiaBlurRenderer;
import com.yiyiaddon.ui.render.SkiaScreen;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingKeybind;
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.PreeditEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

/**
 * 通用独立面板窗口：本项目全部「独立小窗口」的统一骨架。
 *
 * <p>负责面板玻璃、圆角、阴影、标题栏、返回按钮、内容滚动与命中分发；子类只往 {@link #content()}
 * 里追加元素，不接触几何与输入处理。模块的独立页面用 {@code ModuleScreen}，一次性小窗口用本类。</p>
 *
 * <p>内容元素按设计高度单列排布，超出可视高度时滚动；面板尺寸与主界面一致
 * （{@link PanelFrame#CARD_W} x {@link PanelFrame#CARD_H}），因此样式与主界面完全统一。</p>
 *
 * <p><b>用法</b>：子类构造末尾调用内容助手填内容，例如：</p>
 *
 * <pre>
 * public MyScreen(Screen parent) {
 *     super("窗口标题", parent);
 *     addSectionTitle("§b§l▌ 分区标题");
 *     addField("标签", "值");
 *     addDivider();
 *     addButton("确定", this::confirm);
 * }
 * </pre>
 */
public abstract class PanelScreen extends SkiaScreen {

    /** 内容区左右留白。 */
    protected static final float CONTENT_PAD = 18f;
    /** 内容区顶部（标题区下方）。 */
    protected static final float CONTENT_TOP = 70f;
    /** 元素之间的默认间距。 */
    protected static final float LINE_GAP = 6f;

    private static final float BACK_X = 18f;
    private static final float BACK_Y = 16f;
    private static final float TITLE_X = BACK_X + BackButton.SIZE + 12f;
    /** 有副标题时的标题基线：标题与副标题上下成组，占的是同一个标题区。 */
    private static final float TITLE_Y = 27f;
    private static final float TITLE_SIZE = 19f;
    /**
     * 标题基线比例：文字视觉中心 = 基线 − 字号 × 本比例。
     *
     * <p>与 {@code TextLine}、选择器分组标题同一口径（{@code 0.36}），三处都在同一块玻璃上，
     * 比例不一致会出现「同样是标题，一行偏上一行偏下」。</p>
     */
    private static final float TITLE_BASELINE_RATIO = 0.36f;
    /**
     * 无副标题时的标题基线：与返回按钮垂直居中。
     *
     * <p><b>用户 2026-09-19 口径</b>：「主世界矿石标题没跟返回键对齐 偏上了现在 所有标题都要这样
     * 对齐返回键 除了底下有介绍的不用」—— 没有副标题的窗口（双栏选择器这类）标题只占返回键那一行，
     * 就该与返回键同轴；有副标题的窗口标题与其下的说明成组，保持原位不动。</p>
     */
    private static final float TITLE_Y_ALIGNED =
            BACK_Y + BackButton.SIZE / 2f + TITLE_SIZE * TITLE_BASELINE_RATIO;
    /** 副标题（模块说明）纵坐标与字号：与模块页 {@code ModuleScreen#SUBTITLE_Y} 同一档，两个窗口的标题区看起来是一套。 */
    private static final float SUBTITLE_Y = 44f;
    private static final float SUBTITLE_SIZE = 11f;
    /** 副标题最多占用的宽度 = 内容宽 − 这个留白（与 {@code ModuleScreen} 同一算式） */
    private static final float SUBTITLE_RESERVED_W = 120f;
    private static final float PAGE_INSET_X = 10f;
    /** 页面左右为滚动条等预留的宽度；子类要算「一行到底有多宽」时需要它（如整栏宽的搜索框）。 */
    protected static final float PAGE_RESERVED_W = 40f;
    private static final float CONTENT_BOTTOM_PAD = 8f;
    private static final float TRACK_INSET = 8f;
    private static final float TRACK_TOP_PAD = 4f;

    private static final float SECTION_TITLE_HEIGHT = 26f;
    private static final float SECTION_TITLE_SIZE = 12f;
    private static final float GAP_HEIGHT = 8f;
    private static final float DIVIDER_HEIGHT = 13f;
    /** 入场时面板的下沉距离：与缩放、淡入一起构成「弹出」观感；关闭时反向播放。 */
    private static final float ENTER_RISE = 16f;

    /**
     * 整屏控制台的面板尺寸上限（设计空间单位）。
     *
     * <p>主界面/模块页仍是 {@link PanelFrame#CARD_W} × {@link PanelFrame#CARD_H}（740×500）；
     * 控制台放宽到 1080 × 760：正文区从约 250 高变成约 510 高，常见页一屏铺满。</p>
     */
    private static final float CONSOLE_CARD_W = 1080f;
    private static final float CONSOLE_CARD_H = 760f;
    /** 面板与窗口边缘的总留白（与 {@code PanelFrame} 内的同名常量同值，那边是私有的）。 */
    private static final float DESIGN_MARGIN = 24f;
    /** 自适应时的最小面板尺寸：窗口再小也不至于把正文压成一条。 */
    private static final float MIN_CARD_W = 520f;
    private static final float MIN_CARD_H = 380f;

    private final String windowTitle;
    /** 副标题（窗口标题下方那行灰字）；null = 不画（多数面板窗口没有副标题）。 */
    private Supplier<String> subtitle;
    private final CompactStack content = new CompactStack(LINE_GAP).enterAnimation(true);
    private final PanelFrame frame = new PanelFrame();
    private final ScrollViewport scroll = new ScrollViewport();
    private final BackButton backButton = new BackButton();
    private final SkiaGlBackend glBackend = new SkiaGlBackend();

    private boolean closingRequested;
    private boolean draggingInContent;
    private boolean draggingScrollbar;
    private boolean exitToGame;
    private long lastRenderMs;

    protected PanelScreen(String windowTitle, Screen parent) {
        super(Component.literal(windowTitle), parent);
        this.windowTitle = windowTitle;
    }

    // ── 内容构建助手 ──

    /**
     * 设置标题下方那行说明（副标题）。控制台窗口用它显示**所属模块的说明**，与模块页标题下那行
     * 同源同度量（{@code module::description}，位置 44、字号 11、次要文字色、超宽省略号）。
     *
     * <p><b>用户 2026-09-17 口径</b>：「控制台外面的模块标题下面有说明 但是控制台里面没有 修复一下
     * 所有带控制台的都要」—— 9 个控制台一律调用本方法，说明文案不另写一份。</p>
     *
     * @param subtitle 说明来源；传 {@code null} 表示不显示（默认不显示）
     */
    protected final void setSubtitle(Supplier<String> subtitle) {
        this.subtitle = subtitle;
    }

    /** 内容容器；子类在此追加任意 {@link CompactElement}。 */
    protected final CompactStack content() {
        return content;
    }

    /** 分区标题（旧项目格式 {@code §b§l▌ 标题}）。 */
    protected final void addSectionTitle(String text) {
        content.add(new TextLine(text).height(SECTION_TITLE_HEIGHT).size(SECTION_TITLE_SIZE).bold(true));
    }

    /** 字段行（旧项目格式 {@code §7标签 §8▸ §f值}）。 */
    protected final void addField(String label, String value) {
        content.add(TextLine.field(label, value));
    }

    /** 满宽按钮：单个按钮撑满整行（本项目尺寸自动适配，不设固定宽度）。 */
    protected final void addButton(String label, Runnable action) {
        content.add(new ButtonRow(new Button(label, action)));
    }

    /** 一行多个等宽按钮。 */
    protected final void addButtons(Button... buttons) {
        content.add(new ButtonRow(buttons));
    }

    /** 空行：用于把操作按钮与信息区分开。 */
    protected final void addGap() {
        content.add(new TextLine(" ").height(GAP_HEIGHT));
    }

    /** 水平分隔线：旧项目用它把危险操作与常规操作分区。 */
    protected final void addDivider() {
        content.add(new CompactElement() {
            @Override
            public float height() {
                return DIVIDER_HEIGHT;
            }

            @Override
            public void update(float dt) {
            }

            @Override
            public void draw(Canvas canvas, float x, float y, float width, float alpha,
                             float mouseX, float mouseY) {
                GlassPanel.divider(canvas, x + 6f, y + DIVIDER_HEIGHT / 2f, width - 12f,
                        ClickGuiThemeColors.current().separator, alpha);
            }

            @Override
            public boolean onClick(float mx, float my, float x, float y, float width, int button) {
                return false;
            }

            @Override
            public boolean onDrag(float mx, float my, float x, float y, float width) {
                return false;
            }
        });
    }

    // ── 反馈与剪贴板 ──

    /** 聊天栏反馈：前缀为模块名（第十七章第 110-113 条）。 */
    protected final void feedback(String moduleName, String message) {
        ClientChat.send(moduleName, message);
    }

    /** 复制到系统剪贴板。 */
    protected static void copyToClipboard(String text) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.keyboardHandler == null) return;
        client.keyboardHandler.setClipboard(text == null ? "" : text);
    }

    // ── 窗口骨架 ──

    @Override
    protected void init() {
        super.init();
        applyDesignSize();
        frame.update(minecraft, 0f);
    }

    /**
     * 面板设计尺寸：默认与主界面一致，整屏控制台放大到「可用设计空间内尽量大」。
     *
     * <p><b>为什么控制台要更大</b>（用户 2026-09-18：「控制台的页面可以加长加宽一下吗 方便看见全部设置
     * 不用上下滑动」）：控制台一页动辄十几行参数，740×500 的正文区只放得下八九行，改一项就得滚。
     * 放大后常见页基本一屏铺满，滚动只剩自动登入那种三十多行的长页。</p>
     *
     * <p><b>为什么按可用空间自适应、而不是写死一个大尺寸</b>：{@link PanelFrame} 会把超出窗口的面板
     * 整体等比缩小（字一起变小）。所以取「可用设计空间 − 边距」与上限的较小值：大屏吃满上限，
     * 小屏自动收窄，字号始终按设计字号渲染。设计空间 = 窗口像素的一半（与 {@code PanelFrame} 同口径）。</p>
     *
     * <p>每帧都调（窗口尺寸、界面大小都可能变），命中与绘制用的是同一次 {@code frame.update} 的结果。</p>
     */
    protected void applyDesignSize() {
        if (!(this instanceof ConsoleHost)) {
            frame.setDesignSize(PanelFrame.CARD_W, PanelFrame.CARD_H);
            return;
        }
        Minecraft client = Minecraft.getInstance();
        float availableW = CONSOLE_CARD_W;
        float availableH = CONSOLE_CARD_H;
        if (client != null && client.getWindow() != null) {
            availableW = Math.max(MIN_CARD_W, client.getWindow().getWidth() * 0.5f - DESIGN_MARGIN);
            availableH = Math.max(MIN_CARD_H, client.getWindow().getHeight() * 0.5f - DESIGN_MARGIN);
        }
        frame.setDesignSize(Math.min(CONSOLE_CARD_W, availableW), Math.min(CONSOLE_CARD_H, availableH));
    }

    /** 为紧凑通知窗指定尺寸，缩放、裁剪和命中仍统一走公共面板几何。 */
    protected final void setPanelDesignSize(float width, float height) {
        frame.setDesignSize(width, height);
    }

    private float contentHeight() {
        return frame.cardHeight() - CONTENT_TOP - CONTENT_PAD;
    }

    /**
     * 面板停在最终位置后才动隐藏格子。
     *
     * <p>开合动画期间面板每帧都在改缩放与下沉量，而隐藏格子的画面备份取自上一帧的主帧缓冲
     * （含上一帧的面板），画回去之后与当前面板对不齐，透在半透明玻璃里就是正文重影
     * （用户 2026-09-16：「字糊了」，详情见 {@code SkiaScreen#canCaptureIcons}）。</p>
     */
    @Override
    protected boolean canCaptureIcons() {
        return frame.animationAlpha() >= 1f;
    }

    @Override
    protected void drawFrame(int width, int height, int mouseX, int mouseY, float delta) {
        if (minecraft == null) return;
        Canvas canvas = glBackend.begin(SkiaGlBackend.mainFramebufferId());
        if (canvas == null) return;
        try {
            drawPanel(canvas, width, height, mouseX, mouseY);
        } finally {
            glBackend.end();
        }
    }

    /**
     * 标题基线：无副标题的窗口与返回按钮垂直居中，有副标题的保持原位（口径见 {@link #TITLE_Y_ALIGNED}）。
     */
    private float titleBaselineY() {
        return hasSubtitle() ? TITLE_Y : TITLE_Y_ALIGNED;
    }

    /** 本窗口是否真的会画出副标题：{@link #setSubtitle} 给出的说明取到非空文本才算（取不到等同没有）。 */
    private boolean hasSubtitle() {
        if (subtitle == null) return false;
        String text = subtitle.get();
        return text != null && !text.isEmpty();
    }

    /**
     * 画标题下方那行说明（未调用 {@link #setSubtitle} 时什么都不画）。
     *
     * <p>度量与模块页副标题逐项相同（{@code ModuleScreen#drawHeader}）：x = 标题 x、y = {@link #SUBTITLE_Y}、
     * 字号 {@link #SUBTITLE_SIZE}、次要文字色、超宽按 {@code contentW − SUBTITLE_RESERVED_W} 省略号截断，
     * 保证「模块页 → 控制台」两处说明看起来是同一行字。</p>
     */
    private void drawSubtitle(Canvas canvas, float cardX, float cardY, float contentW, float alpha,
                              ClickGuiThemeColors tc) {
        if (subtitle == null) return;
        String text = subtitle.get();
        if (text == null || text.isEmpty()) return;
        FontRenderer.drawText(canvas,
                CardLayout.ellipsize(text, contentW - SUBTITLE_RESERVED_W, SUBTITLE_SIZE),
                cardX + TITLE_X, cardY + SUBTITLE_Y, SUBTITLE_SIZE,
                GlassPanel.withAlpha(tc.secondaryText, alpha));
    }

    private void drawPanel(Canvas canvas, int width, int height, int mouseX, int mouseY) {
        TooltipLayer.beginFrame();
        long now = System.currentTimeMillis();
        float dt = lastRenderMs == 0L ? 0.016f : Math.min((now - lastRenderMs) / 1000f, 0.033f);
        lastRenderMs = now;

        applyDesignSize();
        if (frame.update(minecraft, dt)) {
            closing();
            return;
        }

        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        float alpha = frame.animationAlpha();
        float cardRadius = frame.cardRadius();
        float cardX = frame.cardX();
        float cardY = frame.cardY();
        float cardW = frame.cardWidth();
        float cardH = frame.cardHeight();
        float designMouseX = frame.toDesignX(mouseX, width);
        float designMouseY = frame.toDesignY(mouseY, height);

        float contentX = cardX + CONTENT_PAD;
        float contentY = cardY + CONTENT_TOP;
        float contentW = cardW - CONTENT_PAD * 2f;
        float contentH = contentHeight();
        // 关闭时返回按钮跟随整体淡出，不瞬间消失
        boolean backVisible = alpha > 0.01f;

        backButton.update(designMouseX, designMouseY, cardX + BACK_X, cardY + BACK_Y, dt, backVisible);
        scroll.layout(content.height() + CONTENT_BOTTOM_PAD, contentH);
        scroll.update(dt);
        content.update(dt);

        if (AddonConfig.panelBlur) {
            float rise = (1f - alpha) * ENTER_RISE;
            SkiaBlurRenderer.getInstance().render(canvas, glBackend.getContext(), minecraft,
                    SkiaGlBackend.mainFramebufferId(), frame.toScreenX(cardX, width),
                    frame.toScreenY(cardY + rise, height), frame.toScreenLength(cardW),
                    frame.toScreenLength(cardH), frame.toScreenLength(cardRadius),
                    AddonConfig.blurTintColor(), AddonConfig.blurStrength);
        }

        // 轻微环境压暗提升玻璃与游戏场景的景深差异，不影响面板内文字对比度。
        GlassPanel.fill(canvas, 0f, 0f, width, height, 0f, tc.shadow,
                alpha * (tc.dark ? 0.16f : 0.10f));

        canvas.save();
        frame.applyTransform(canvas, width, height);
        // 面板自下方略微上浮归位；关闭时反向下沉
        canvas.translate(0f, (1f - alpha) * ENTER_RISE);
        try {
            GlassPanel.shadow(canvas, cardX, cardY, cardW, cardH, cardRadius, tc.shadow, alpha, 1.15f);
            GlassPanel.frost(canvas, cardX, cardY, cardW, cardH, cardRadius, tc.window,
                    AddonConfig.panelBlur ? 0.62f : 0.94f, alpha);

            canvas.save();
            canvas.clipRRect(RRect.makeXYWH(cardX, cardY, cardW, cardH, cardRadius), true);
            try {
                GlassPanel.ambientGlow(canvas, cardX, cardY, cardW, cardH, tc, alpha, 0.46f);
                GlassPanel.rim(canvas, cardX, cardY, cardW, cardH, cardRadius, tc.rim, alpha, 0.26f);
                backButton.draw(canvas, cardX + BACK_X, cardY + BACK_Y, alpha, tc, backVisible);
                FontRenderer.drawTextBold(canvas, windowTitle, cardX + TITLE_X, cardY + titleBaselineY(),
                        TITLE_SIZE, GlassPanel.withAlpha(tc.primaryText, alpha));
                drawSubtitle(canvas, cardX, cardY, contentW, alpha, tc);

                // 滚动偏移并入元素起点，不做画布平移——与既有 BasePage 体系一致
                // （BasePage 的 onDraw/onClick 同样是「宿主把偏移算进 y」，避免两套滚动语义并存）
                float scrollValue = scroll.value();

                canvas.save();
                canvas.clipRect(Rect.makeXYWH(contentX, contentY, contentW, contentH));
                try {
                    content.draw(canvas, contentX + PAGE_INSET_X, contentY - scrollValue, contentW - PAGE_RESERVED_W,
                            alpha, contentY, contentY + contentH, designMouseX, designMouseY);
                } finally {
                    canvas.restore();
                }
                scroll.drawScrollbar(canvas, contentX + contentW - TRACK_INSET, contentY + TRACK_TOP_PAD,
                        contentH - TRACK_TOP_PAD * 2f, alpha, tc);
            } finally {
                canvas.restore();
            }
            // 浮层在面板变换内、内容裁剪外绘制：不会被内容裁掉，也不被后续行覆盖。
            // 视口取设计空间屏幕尺寸（PanelFrame 未暴露，按其居中几何 cardX=(designW-cardW)/2 反推）。
            TooltipLayer.draw(canvas, frame.cardX() * 2f + frame.cardWidth(),
                    frame.cardY() * 2f + frame.cardHeight(), alpha);
        } finally {
            canvas.restore();
        }
    }

    // ── 输入 ──

    /**
     * 键盘 / 输入法转发。
     *
     * <p>{@code SettingTextBox} 的焦点、光标与输入法状态都是静态的，界面只负责把事件转交给它；
     * 少了这几个转发，本骨架下的输入框点得到焦点也打不进字——{@code ModuleScreen} 与
     * {@code ClickGuiScreen} 各自都写了同样的转发，这里补齐，避免 PanelScreen 系窗口
     * （双栏选择器、调色盘、更多管理页等）的输入框全部失灵。</p>
     *
     * <p><b>键位录制优先于输入框</b>（第 169 条同源）：{@link SettingKeybind} 的录制态同样是静态槽，
     * 界面不转交就永远收不到按键——传送控制台「触发按键」页点开键位块后一直停在 {@code ...}
     * （用户 2026-09-18 两次实机反馈）。</p>
     */
    @Override
    public boolean keyPressed(KeyEvent event) {
        if (SettingKeybind.keyPressed(event)) return true;
        // 快捷键捕获：控制台（本骨架）里的「按键行」点开后要能接住任意键
        if (ModuleKeybindManager.captureKey(event.key())) return true;
        if (SettingTextBox.keyPressed(event)) return true;
        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (SettingTextBox.charTyped(event)) return true;
        return super.charTyped(event);
    }

    @Override
    public boolean preeditUpdated(PreeditEvent event) {
        SettingTextBox.onPreedit(event);
        return true;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean consumed) {
        if (closingRequested) return false;
        // 键位录制优先：录制中可以绑鼠标侧键，录制中吞掉整次点击，免得同时点到下层控件
        if (SettingKeybind.mousePressed(event.button())) {
            draggingInContent = false;
            draggingScrollbar = false;
            return true;
        }
        // 快捷键捕获：捕获中时鼠标键也要能绑（与 ModuleScreen 同款；否则点徽章后按鼠标键会误触下层控件）
        if (ModuleKeybindManager.captureMouseButton(event.button())) {
            draggingInContent = false;
            draggingScrollbar = false;
            return true;
        }
        if (event.button() > GLFW.GLFW_MOUSE_BUTTON_RIGHT) return true;

        float mouseX = frame.toDesignX(event.x(), this.width);
        float mouseY = frame.toDesignY(event.y(), this.height);
        float cardX = frame.cardX();
        float cardY = frame.cardY();
        float contentX = cardX + CONTENT_PAD;
        float contentY = cardY + CONTENT_TOP;
        float contentW = frame.cardWidth() - CONTENT_PAD * 2f;
        float contentH = contentHeight();

        if (event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT
                && backButton.hit(mouseX, mouseY, cardX + BACK_X, cardY + BACK_Y)) {
            backButton.press();
            requestClose();
            return true;
        }
        if (mouseX < contentX || mouseX > contentX + contentW
                || mouseY < contentY || mouseY > contentY + contentH) {
            SettingTextBox.clearFocus();
            return false;
        }

        float trackTop = contentY + TRACK_TOP_PAD;
        float trackHeight = contentH - TRACK_TOP_PAD * 2f;
        scroll.layout(content.height() + CONTENT_BOTTOM_PAD, contentH);
        if (event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT && scroll.hasScrollbar()
                && scroll.isInTrack(mouseX, mouseY, contentX + contentW - TRACK_INSET, trackTop, trackHeight)) {
            draggingScrollbar = true;
            scroll.beginDrag(mouseY, trackTop, trackHeight);
            return true;
        }
        // 元素起点并入滚动偏移；鼠标与可见边界都用屏幕坐标，与绘制口径一致
        float scrollValue = scroll.value();
        // 按钮可能持有输入框焦点，因此命中内容时不抢先清焦点
        boolean hit = content.onClick(mouseX, mouseY, contentX + PAGE_INSET_X, contentY - scrollValue,
                contentW - PAGE_RESERVED_W, contentY + contentH, event.button());
        if (!hit) SettingTextBox.clearFocus();
        if (hit && event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            draggingInContent = true;
        }
        return hit;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        float mouseX = frame.toDesignX(event.x(), this.width);
        float mouseY = frame.toDesignY(event.y(), this.height);
        float cardX = frame.cardX();
        float cardY = frame.cardY();
        float contentX = cardX + CONTENT_PAD;
        float contentY = cardY + CONTENT_TOP;
        float contentW = frame.cardWidth() - CONTENT_PAD * 2f;
        float contentH = contentHeight();

        if (draggingScrollbar) {
            scroll.layout(content.height() + CONTENT_BOTTOM_PAD, contentH);
            float trackTop = contentY + TRACK_TOP_PAD;
            scroll.dragTo(mouseY, trackTop, contentH - TRACK_TOP_PAD * 2f);
            return true;
        }
        if (draggingInContent) {
            content.onDrag(mouseX, mouseY, contentX + PAGE_INSET_X, contentY - scroll.value(),
                    contentW - PAGE_RESERVED_W, contentY + contentH);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        draggingInContent = false;
        draggingScrollbar = false;
        content.releaseDrag();
        backButton.release();
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontal, double vertical) {
        float designX = frame.toDesignX(mouseX, this.width);
        float designY = frame.toDesignY(mouseY, this.height);
        float contentX = frame.cardX() + CONTENT_PAD;
        float contentY = frame.cardY() + CONTENT_TOP;
        float contentW = frame.cardWidth() - CONTENT_PAD * 2f;
        float contentH = contentHeight();

        if (designX >= contentX && designX <= contentX + contentW
                && designY >= contentY && designY <= contentY + contentH) {
            scroll.layout(content.height() + CONTENT_BOTTOM_PAD, contentH);
            scroll.scrollBy(vertical, AddonConfig.scrollSpeed);
            return true;
        }
        return false;
    }

    // ── 关闭 ──

    /**
     * 标记本窗口关闭后直接回到游戏，而不是返回上级窗口。
     *
     * <p>旧项目里「使用说明 / 手动添加 / 二次确认 / 识别结果」这类窗口的关闭按钮都是
     * {@code setScreen(null)}，即连同整个 GUI 一起关掉；只有「数据清理」的「返回」是回到
     * 「更多管理」。本方法用于复刻前一种行为。</p>
     */
    protected final void exitToGame() {
        this.exitToGame = true;
    }

    /** 播放关闭动画，动画结束后回到上级界面。 */
    protected final void requestClose() {
        if (closingRequested) return;
        closingRequested = true;
        SettingTextBox.clearFocus();
        backButton.cancel();
        frame.beginClose();
    }

    @Override
    protected void closing() {
        if (exitToGame && this.minecraft != null) {
            this.minecraft.setScreen(null);
            return;
        }
        super.closing();
    }

    @Override
    public void onClose() {
        requestClose();
    }

    @Override
    public void removed() {
        SettingTextBox.clearFocus();
        ImeBridge.reset();
        glBackend.destroy();
        super.removed();
    }
}
