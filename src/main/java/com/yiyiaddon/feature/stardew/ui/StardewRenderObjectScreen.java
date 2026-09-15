package com.yiyiaddon.feature.stardew.ui;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.render.world.ShapeMode;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingToggle;
import com.yiyiaddon.ui.widget.SettingWidget;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;
import java.util.function.Supplier;

/**
 * 单个渲染对象的配置页：显示 / 颜色 / 彩虹 / 渲染模式（点位字牌只有显示开关）。
 *
 * <p><b>逐字搬运自旧项目</b> {@code stardew/selector/StardewRenderObjectScreen.java}：
 * 窗口标题 {@code "渲染设置 · " + 对象名}，四行标签 {@code §7显示 §8▶}、{@code §7颜色 §8▶}、
 * {@code §7彩虹 §8▶}（tooltip「颜色随时间自动循环（与配色页里的「彩虹」是同一个开关）」）、
 * {@code §7渲染模式 §8▶}（仅 {@code object.hasMode()} 时出现），底部说明
 * {@code §8本页只影响「<对象名>」，与其它渲染对象互不影响}。
 * 颜色 / 彩虹两行仅 {@code object.colorEditable()} 时出现（点位字牌的颜色跟随对应方框）。</p>
 *
 * <p><b>为什么直接读写对象自己的字段：</b>本页不新建任何状态，显示 / 颜色 / 彩虹 / 渲染模式
 * 全部落在 {@link StardewSettings.RenderObject} 上，改完立即 {@link StardewFarmModule#persistSettings()}
 * 落盘，与主页、渲染逻辑完全同源。</p>
 *
 * <p><b>标签为什么自绘：</b>旧标签是 Meteor 的 {@code theme.label("§7显示 §8▶")}，含 {@code §} 颜色码；
 * 本项目 {@code CompactRow} 的标签不解析 {@code §}（直接走字体绘制），直接用会把 {@code §7} 原样画出来，
 * 因此这里用一个只做「左标签 + 右控件」的小行元素，标签经 {@link MinecraftText} 绘制，文案逐字保留。</p>
 */
public final class StardewRenderObjectScreen extends PanelScreen {

    /** 「打开颜色选择器」：旧项目颜色行上编辑按钮的 tooltip 原文 */
    private static final String HINT_OPEN_PICKER = "打开颜色选择器";
    /** 彩虹开关 tooltip 原文（与配色页里的「彩虹」是同一个开关） */
    private static final String HINT_RAINBOW = "颜色随时间自动循环（与配色页里的「彩虹」是同一个开关）";

    private final StardewFarmModule module;
    private final StardewSettings.RenderObject object;

    /**
     * @param parent 上级屏幕（控制台「点位」页）——控制台需要调的入口就是本构造签名
     * @param module 归属模块：改动后立即写回设置
     * @param object 本页唯一编辑的渲染对象
     */
    public StardewRenderObjectScreen(Screen parent, StardewFarmModule module, StardewSettings.RenderObject object) {
        super("渲染设置 · " + object.name(), parent);
        this.module = module;
        this.object = object;
        build();
    }

    private void build() {
        // 显示：同一个对象字段，勾选立即生效
        content().add(new Row("§7显示 §8▶", null,
            new SettingToggle(() -> object.show, value -> {
                object.show = value;
                module.persistSettings();
            })));

        // 颜色：色块点击打开调色板；彩虹是同一个 EspColor 上的开关。
        // 点位字牌没有这一项：它的颜色跟随对应点位方框，摆一个点了没反应的色块只会误导。
        if (object.colorEditable()) {
            content().add(new Row("§7颜色 §8▶", () -> HINT_OPEN_PICKER,
                new SettingColorPicker(object.name() + "颜色", object.color, module::persistSettings)));
            content().add(new Row("§7彩虹 §8▶", () -> HINT_RAINBOW,
                new SettingToggle(() -> object.color.rainbow(), value -> {
                    object.color.rainbow(value);
                    module.persistSettings();
                })));
        }

        // 渲染模式：线框 / 面 / 两者（点位字牌没有这一项）
        if (object.hasMode()) {
            content().add(new Row("§7渲染模式 §8▶", null,
                new SettingSegmented(List.of(ShapeMode.labels()),
                    () -> object.mode.index(),
                    index -> {
                        object.mode = ShapeMode.of(index);
                        module.persistSettings();
                    })));
        }

        // 说明：让玩家清楚「本对象独立」，避免以为改了这里会连带其它对象
        content().add(new TextLine("§8本页只影响「" + object.name() + "」，与其它渲染对象互不影响"));
    }

    /**
     * 「左标签 + 右控件」行：标签含 {@code §} 颜色码，经 {@link MinecraftText} 绘制；
     * 有 tooltip 时在指针悬停时于标签与控件之间淡入（旧项目 Meteor 控件 tooltip 的等价展示位）。
     */
    private static final class Row implements CompactElement {

        private static final float HEIGHT = 36f;
        private static final float PAD_X = 14f;
        private static final float LABEL_SIZE = 13f;
        private static final float HINT_SIZE = 10f;
        private static final float HINT_GAP = 12f;
        private static final float HINT_MIN_WIDTH = 28f;
        private static final float HOVER_SMOOTHING = 12f;

        private final String label;
        private final Supplier<String> hint;
        private final SettingWidget control;

        private boolean hovered;
        private float hover;

        private Row(String label, Supplier<String> hint, SettingWidget control) {
            this.label = label == null ? "" : label;
            this.hint = hint;
            this.control = control;
        }

        @Override
        public float height() {
            return HEIGHT;
        }

        @Override
        public void update(float dt) {
            if (control != null) control.update(dt);
            hover += ((hovered ? 1f : 0f) - hover) * Math.min(1f, Math.max(0f, dt) * HOVER_SMOOTHING);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
            ClickGuiThemeColors tc = ClickGuiThemeColors.current();
            float radius = GlassPanel.rowRadius(HEIGHT);
            float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
            GlassPanel.frost(canvas, x, y, width, HEIGHT, radius, tc.module, 0.70f, rowAlpha);
            GlassPanel.rim(canvas, x, y, width, HEIGHT, radius, tc.rim, alpha, 0.10f);

            hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HEIGHT;
            if (hover > 0.01f) {
                GlassPanel.fill(canvas, x, y, width, HEIGHT, radius, tc.surfaceHover, rowAlpha * hover);
            }

            float centerY = y + HEIGHT / 2f;
            MinecraftText.draw(canvas, label, x + PAD_X, CardLayout.baseline(centerY, LABEL_SIZE), LABEL_SIZE,
                    tc.primaryText, alpha);
            drawHint(canvas, x, y, width, alpha, tc);
            if (control != null) {
                float cx = controlX(x, width);
                float cy = controlY(y);
                control.hover(mouseX, mouseY, cx, cy, control.getWidth());
                control.draw(canvas, cx, cy, alpha);
            }
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            if (control == null || button != 0) return false;
            if (mx < x || mx > x + width || my < y || my > y + HEIGHT) return false;
            float cx = controlX(x, width);
            float cy = controlY(y);
            if (mx < cx || mx > cx + control.getWidth() || my < cy || my > cy + control.getHeight()) return false;
            return control.onClick(mx, my, cx, cy, button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }

        /** 控件左边界；绘制与命中共用 */
        private float controlX(float x, float width) {
            return x + width - PAD_X - control.getWidth();
        }

        /** 控件顶部；绘制与命中共用 */
        private float controlY(float y) {
            return y + (HEIGHT - control.getHeight()) / 2f;
        }

        /** 悬停提示：只在指针悬停时淡入，并按「标签结束到控件开始」的实际空隙截断 */
        private void drawHint(Canvas canvas, float x, float y, float width, float alpha, ClickGuiThemeColors tc) {
            if (hint == null || hover < 0.02f) return;
            String text = hint.get();
            if (text == null || text.isBlank()) return;

            float startX = x + PAD_X + MinecraftText.measure(label, LABEL_SIZE, false) + HINT_GAP;
            float endX = control == null ? x + width - PAD_X : controlX(x, width) - HINT_GAP;
            float available = endX - startX;
            if (available < HINT_MIN_WIDTH) return;

            FontRenderer.drawText(canvas, CardLayout.ellipsize(MinecraftText.strip(text), available, HINT_SIZE),
                    startX, CardLayout.baseline(y + HEIGHT / 2f, HINT_SIZE), HINT_SIZE,
                    GlassPanel.withAlpha(tc.labelTertiary, alpha * hover));
        }
    }
}
