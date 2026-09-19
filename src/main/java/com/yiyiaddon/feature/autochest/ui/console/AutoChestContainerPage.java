package com.yiyiaddon.feature.autochest.ui.console;

import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.ui.AutoChestConsoleScreen;
import com.yiyiaddon.feature.autochest.ui.ContainerTypePage;
import com.yiyiaddon.model.autochest.ContainerTypeRegistry;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 自动箱子控制台「容器」页：{@code 容器} 分组的五项。
 *
 * <p>逐字搬自旧项目配置页的 {@code 容器} 分组：{@code 容器类型}（选择按钮 + 「已选 N / M 类」计数 +
 * 重置图标）、{@code 检测范围}、{@code 扫描周期}、{@code 已处理记录过期}——设置名、描述、默认值与
 * 取值域一字未改（三项无可配置上限的整数项仍以 {@link Integer#MAX_VALUE} 为上限，与旧项目同域）。</p>
 *
 * <p>旧页把计数与重置图标排成按钮下方独立一行（{@code ButtonRow.split}）；控制台行把计数放进
 * 左标签、重置图标放右侧控件位，信息一字不变。</p>
 */
public final class AutoChestContainerPage {

    private static final String DESC_CONTAINER_TYPES =
        "选择要识别的合法容器类型（箱子/陷阱箱/16色潜影盒/木桶/铜箱）。";
    private static final String DESC_SCAN_RADIUS = "扫描附近合法容器的半径（格），负责发现容器。";
    private static final String DESC_SCAN_INTERVAL = "每多少 Tick 推进一轮扫描（分帧扫描，不整世界全扫）。";
    private static final String DESC_RECORD_EXPIRE = "已处理容器记录多少分钟后失效，可被再次处理。";

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final AutoChestSettings DEFAULTS = new AutoChestSettings();

    private final AutoChestConsoleScreen owner;
    private final AutoChestModule module;

    public AutoChestContainerPage(AutoChestConsoleScreen owner, AutoChestModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        AutoChestSettings settings = module.settings();

        stack.add(new ConsoleRow(owner, () -> "容器类型", DESC_CONTAINER_TYPES, null,
            List.of(new Ctl(new Button("选择容器类型",
                () -> openSubScreen(new ContainerTypePage(owner, module)))))));

        // 计数行：左标签承载「已选 N / M 类」，右侧只放重置图标（旧页同一行的两部分）
        stack.add(new ConsoleRow(owner, this::containerTypeCountText, null, null,
            List.of(new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, () -> {
                settings.resetContainerTypes();
                module.persistSettings();
                owner.reload();
            }), "恢复默认：全部容器类型启用"))));

        stack.add(new ConsoleRow(owner, () -> "检测范围", DESC_SCAN_RADIUS, null,
            List.of(new Ctl(intBox(4, Integer.MAX_VALUE, () -> settings.scanRadius,
                value -> {
                    settings.scanRadius = value;
                })),
                ConsoleWidgets.resetCtl(() -> {
                    settings.scanRadius = DEFAULTS.scanRadius;
                    module.persistSettings();
                    owner.reload();
                }, "检测范围"))));

        stack.add(new ConsoleRow(owner, () -> "扫描周期", DESC_SCAN_INTERVAL, null,
            List.of(new Ctl(intBox(1, Integer.MAX_VALUE, () -> settings.scanInterval,
                value -> {
                    settings.scanInterval = value;
                })),
                ConsoleWidgets.resetCtl(() -> {
                    settings.scanInterval = DEFAULTS.scanInterval;
                    module.persistSettings();
                    owner.reload();
                }, "扫描周期"))));

        stack.add(new ConsoleRow(owner, () -> "已处理记录过期", DESC_RECORD_EXPIRE, null,
            List.of(new Ctl(intBox(0, Integer.MAX_VALUE, () -> settings.recordExpireMinutes,
                value -> {
                    settings.recordExpireMinutes = value;
                })),
                ConsoleWidgets.resetCtl(() -> {
                    settings.recordExpireMinutes = DEFAULTS.recordExpireMinutes;
                    module.persistSettings();
                    owner.reload();
                }, "已处理记录过期"))));
    }

    /** 容器类型计数（与旧项目逐字同源）：{@code 已选 N / M 类} */
    private String containerTypeCountText() {
        return "已选 " + module.settings().containerTypeIds.size() + " / "
            + ContainerTypeRegistry.all().size() + " 类";
    }

    /** 打开子界面（容器类型勾选）；关闭后回到控制台，由 {@code init} 里的重建刷新计数 */
    private void openSubScreen(Screen screen) {
        if (owner.client() == null || screen == null) return;
        owner.client().gui.setScreen(screen);
    }

    /** 整数设置框：步进 1，改动落盘（取值域与旧项目一致，无可配置上限） */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, Consumer<Integer> setter) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                module.persistSettings();
            });
    }
}
