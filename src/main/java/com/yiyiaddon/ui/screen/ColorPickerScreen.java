package com.yiyiaddon.ui.screen;

import com.yiyiaddon.ui.component.ColorPreview;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.HueStrip;
import com.yiyiaddon.ui.component.SaturationBrightnessPad;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.Rainbow;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.gui.screens.Screen;

/**
 * 调色板窗口：预览 + 色相条 + 饱和度亮度面板 + RGBA 数值 + 彩虹开关。
 *
 * <p>调色板直接改传入的 {@link EspColor}，关闭窗口后渲染侧立刻使用新颜色，无需「确定」按钮。</p>
 *
 * <p>手动取色（点色相条、点面板、改 RGBA）会同时关闭彩虹——否则用户调完颜色会被彩虹立刻覆盖，
 * 看起来像「调了没反应」。</p>
 */
public final class ColorPickerScreen extends PanelScreen {

    private final EspColor color;

    public ColorPickerScreen(String windowTitle, EspColor color, Screen parent) {
        super(windowTitle, parent);
        this.color = color;
        build();
    }

    private void build() {
        addSectionTitle("§b§l▌ 预览");
        content().add(new ColorPreview(color));
        addGap();

        addSectionTitle("§b§l▌ 色相");
        content().add(new HueStrip(color));
        addGap();

        addSectionTitle("§b§l▌ 饱和度与亮度");
        content().add(new SaturationBrightnessPad(color));
        addGap();

        addSectionTitle("§b§l▌ 数值");
        content().add(new CompactRow("红", () -> "0 - 255", numberBox(16)));
        content().add(new CompactRow("绿", () -> "0 - 255", numberBox(8)));
        content().add(new CompactRow("蓝", () -> "0 - 255", numberBox(0)));
        content().add(new CompactRow("透明", () -> "0 全透明，255 不透明",
                new SettingNumberBox(0, 255, 1, "%.0f",
                        () -> (double) color.alpha(),
                        value -> color.alpha(value.intValue()))));
        addGap();

        addSectionTitle("§b§l▌ 彩虹");
        content().add(new CompactRow("彩虹", () -> "颜色随时间循环变化",
                new SettingToggle(color::rainbow, value -> color.rainbow(value))));
        content().add(new CompactRow("速度", () -> "每秒推进的色环数，越大变色越快",
                new SettingNumberBox(0.02, Rainbow.MAX_SPEED, 0.02, "%.2f",
                        color::rainbowSpeed,
                        value -> color.rainbowSpeed(value))));

        addDivider();
        addButton("§7关闭", this::requestClose);
    }

    /** 单个颜色通道的数值框；手动改动会关闭彩虹。 */
    private SettingNumberBox numberBox(int shift) {
        int mask = 0xFF << shift;
        return new SettingNumberBox(0, 255, 1, "%.0f",
                () -> (double) ((color.rgb() >> shift) & 0xFF),
                value -> {
                    color.rgb((color.rgb() & ~mask) | ((value.intValue() & 0xFF) << shift));
                    color.rainbow(false);
                });
    }
}
