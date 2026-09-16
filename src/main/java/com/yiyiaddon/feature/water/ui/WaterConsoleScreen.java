package com.yiyiaddon.feature.water.ui;

import com.yiyiaddon.feature.water.WaterESPModule;
import com.yiyiaddon.feature.water.config.WaterSettings;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import com.yiyiaddon.ui.render.world.ShapeMode;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.gui.screens.Screen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 水源显示控制台：10 个设置项的唯一承载界面（第 181 条：移植模块一律控制台样式）。
 *
 * <p><b>设置项逐字</b>（旧 {@code WaterESPModule :82-151}，51 号第五节全表）：
 * 名称 / 描述 / 默认值 / 取值域原样；旧滑条形态不保留（差异 D-13-01）——
 * {@code 扫描半径}(4~32) 与 {@code 渲染距离}(4~64) 走 {@link SettingNumberBox}；
 * 样式枚举走 {@link SettingCycle}，候选 = {@code ShapeMode.labels()}
 * （{@code 线框 / 面 / 两者}，第 143 条）。</p>
 *
 * <p><b>分组</b>：{@code 灌溉范围显示 / 建议放水点 / 高级选项} 三个分组名逐字
 * （旧 {@code :44-46}）；旧默认组两项（扫描半径 / 渲染距离）在旧框架无组名平铺，
 * 本页同样平铺在前，不造组名。颜色 / 样式行随其开关所在分组折叠，不做逐行动态隐藏
 * （差异 D-13-05，自研壳无 visible 原语）。改动即写盘（第 173 条）。</p>
 */
public final class WaterConsoleScreen extends PanelScreen implements ConsoleHost {

    private final WaterESPModule module;
    private final WaterSettings settings;
    private final Body body = new Body();

    /** 已收起的折叠块键集合（本窗口生命周期内有效；重建不丢） */
    private final Set<String> collapsedSections = new HashSet<>();

    /** @param parent 上级屏幕（模块页）——ESC / 返回键回到它 */
    public WaterConsoleScreen(Screen parent, WaterESPModule module) {
        super("水源显示控制台", parent);
        this.module = module;
        this.settings = module.settings();
        content().add(body);
        reload();
    }

    /** 登记本帧要显示的 tooltip：走项目全局 TooltipLayer（实现 {@link ConsoleHost}） */
    @Override
    public void tip(String text, float x, float y) {
        TooltipLayer.show(text, x, y);
    }

    /** 已收起的折叠块键集合 */
    public Set<String> collapsedSections() {
        return collapsedSections;
    }

    /** 重排整页内容 */
    public void reload() {
        com.yiyiaddon.ui.widget.SettingTextBox.clearFocus();
        CompactStack next = new CompactStack(6f);
        buildInto(next);
        body.swap(next);
    }

    private void buildInto(CompactStack stack) {
        // ── 旧默认组两项（无组名平铺，照旧框架 UI 事实） ──
        stack.add(numberRow("扫描半径", "扫描玩家周围多少格内的水源",
            4, 32, () -> (double) settings.scanRadius,
            value -> settings.scanRadius = value.intValue()));
        stack.add(numberRow("渲染距离", "只渲染玩家周围多少格内的框（可以比扫描半径大）",
            4, 64, () -> (double) settings.renderDistance,
            value -> settings.renderDistance = value.intValue()));

        // ── 灌溉范围显示 ──
        FoldSection range = new FoldSection("灌溉范围显示", "range", collapsedSections());
        range.content().add(toggleRow("显示灌溉范围", "显示每桶水能覆盖的 9×9 耕地范围（蓝色大框）",
            () -> settings.renderRange, value -> settings.renderRange = value));
        range.content().add(colorRow("范围颜色", "灌溉范围的颜色", settings.rangeColor));
        stack.add(range);

        // ── 建议放水点 ──
        FoldSection suggest = new FoldSection("建议放水点", "suggest", collapsedSections());
        suggest.content().add(toggleRow("显示建议点", "在已有水源的上下左右显示可放水位置（红色框）",
            () -> settings.renderSuggestion, value -> settings.renderSuggestion = value));
        suggest.content().add(colorRow("建议点颜色", "建议放水点的颜色", settings.suggestionColor));
        suggest.content().add(cycleRow("建议点样式", "Lines 线框 / Sides 面 / Both 两者",
            () -> settings.suggestionShapeMode, mode -> settings.suggestionShapeMode = mode));
        stack.add(suggest);

        // ── 高级选项 ──
        FoldSection advanced = new FoldSection("高级选项", "advanced", collapsedSections());
        advanced.content().add(toggleRow("显示水源方块",
            "用小框标出水源方块本身（通常不需要，主要看灌溉范围即可）",
            () -> settings.renderSource, value -> settings.renderSource = value));
        advanced.content().add(colorRow("水源方块颜色", null, settings.sourceColor));
        advanced.content().add(cycleRow("水源方块样式", "Lines 线框 / Sides 面 / Both 两者",
            () -> settings.sourceShapeMode, mode -> settings.sourceShapeMode = mode));
        stack.add(advanced);

        // 底部（照挖矿控制台同款页脚）
        stack.add(new ButtonStrip(this, List.of(
            new Ctl(new com.yiyiaddon.ui.widget.Button("§7刷新", this::reload),
                "按最新设置值重排本页"),
            new Ctl(new com.yiyiaddon.ui.widget.Button("§7关闭", () -> {
                if (minecraft != null) minecraft.setScreen(null);
            }))), ButtonStrip.BUTTON_HEIGHT));
    }

    // ── 行构件组装（与自动农场设置页同一套写法） ──

    /** 开关行：改动即写盘（第 173 条） */
    private ConsoleRow toggleRow(String label, String hint,
                                 java.util.function.Supplier<Boolean> getter,
                                 java.util.function.Consumer<Boolean> setter) {
        return new ConsoleRow(this, () -> label, hint, null,
            List.of(new Ctl(new SettingToggle(getter, value -> {
                setter.accept(value);
                module.persistSettings();
            }))));
    }

    /** 数字行（旧滑条 → 数字框，D-13-01）：步进 1、整数值，改动即写盘 */
    private ConsoleRow numberRow(String label, String hint, double min, double max,
                                 java.util.function.Supplier<Double> getter,
                                 java.util.function.Consumer<Double> setter) {
        return new ConsoleRow(this, () -> label, hint, null,
            List.of(new Ctl(new SettingNumberBox(min, max, 1, "%.0f",
                getter, value -> {
                setter.accept(value);
                module.persistSettings();
            }))));
    }

    /** 颜色行：调色板直接改传入的 EspColor，关闭窗口即生效（第 151 条），改动即写盘 */
    private ConsoleRow colorRow(String label, String hint,
                                com.yiyiaddon.ui.render.world.EspColor color) {
        return new ConsoleRow(this, () -> label, hint, null,
            List.of(new Ctl(new SettingColorPicker(label, color, module::persistSettings))));
    }

    /** 样式行：候选 = 线框 / 面 / 两者（第 143 条逐字），改动即写盘 */
    private ConsoleRow cycleRow(String label, String hint,
                                java.util.function.Supplier<ShapeMode> getter,
                                java.util.function.Consumer<ShapeMode> setter) {
        return new ConsoleRow(this, () -> label, hint, null,
            List.of(new Ctl(new SettingCycle(List.of(ShapeMode.labels()),
                () -> getter.get().index(), index -> {
                setter.accept(ShapeMode.of(index));
                module.persistSettings();
            }))));
    }

    /** 整页内容容器：{@link #swap} 换掉内部堆叠，刷新不重开窗口、不重播入场动画 */
    private final class Body implements CompactElement {

        private CompactStack stack = new CompactStack(6f);

        private void swap(CompactStack next) {
            stack = next;
        }

        @Override
        public float height() {
            return stack.height();
        }

        @Override
        public void update(float dt) {
            stack.update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            stack.draw(canvas, x, y, width, alpha, y, y + stack.height(), mouseX, mouseY);
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            return stack.onClick(mx, my, x, y, width, Float.MAX_VALUE, button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return stack.onDrag(mx, my, x, y, width, Float.MAX_VALUE);
        }

        @Override
        public void releaseDrag() {
            stack.releaseDrag();
        }
    }
}
