package com.yiyiaddon.feature.teleport.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.teleport.TeleportModule;
import com.yiyiaddon.feature.teleport.config.TeleportSettings;
import com.yiyiaddon.feature.teleport.ui.TeleportConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

/**
 * 控制台设置页：旧 4 个设置组（TP地面 / TP穿墙 / TP坐标 / 验证与调试）各一个折叠块。
 *
 * <p><b>设置逐字</b>：名称 / 描述 / 默认值 / 取值域全部来自旧 {@code TeleportModule:108-208}，
 * 分组名沿用旧组名，不新造分类（第 183 条）。数字一律 {@link SettingNumberBox}（第 123 条禁滑块），
 * 步进按「比例型取最小可感知增量」：整数项 1，格距 0.5，阈值 0.25（登记 D-17-09）。</p>
 *
 * <p>改动立即 {@link ModuleManager#saveSettings(TeleportModule)}（第 172-175 条：改设置必须落盘、
 * 重启仍生效，读写键名对称）。</p>
 */
public final class TeleportSettingsPage {

    private static final String SECTION_KEY_GROUND = "group:TP地面";
    private static final String SECTION_KEY_WALL = "group:TP穿墙";
    private static final String SECTION_KEY_COORD = "group:TP坐标";
    private static final String SECTION_KEY_VERIFY = "group:验证与调试";

    // ── 描述（逐字 = 旧设置声明里的 description） ──

    private static final String DESC_MAX_RISE = "从当前高度向上扫描真正地表的最大格数";
    private static final String DESC_MAX_DISTANCE = "沿准星方向智能搜索落点的最大距离（格），实际是否被接受由服务端验证决定";
    private static final String DESC_MAX_DEVIATION = "理想落点无法容纳玩家时，就近搜索安全落点的修正半径（格）";
    private static final String DESC_MAX_FALL = "墙后自动下落到支撑的最大落差（格）";
    private static final String DESC_NO_FALL_DAMAGE = "玩家传送时用目标包后 0.001 格上移包触发 26.1.2 服务端清空下落累计；乘坐载具时为避免误伤会拒绝执行";
    private static final String DESC_COORD_X = "TP坐标键的目标 X 坐标";
    private static final String DESC_COORD_Y = "TP坐标键的目标 Y 坐标";
    private static final String DESC_COORD_Z = "TP坐标键的目标 Z 坐标";
    private static final String DESC_FALLBACK_RADIUS = "目标坐标不可站立时的邻近安全落点搜索半径（格，0 = 不回退）";
    private static final String DESC_VERIFY_THRESHOLD = "服务端权威位置与预期位置的距离超过该值判定为回弹（格）";
    private static final String DESC_VERIFY_WINDOW = "发送位置后等待服务端权威包的最大时长（tick），超时无包即视为接受";
    private static final String DESC_DEBUG_RENDER = "渲染最近一次传送的目标点/射线/候选格（仅自己可见）";

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final TeleportSettings DEFAULTS = new TeleportSettings();

    private final TeleportConsoleScreen host;
    private final TeleportModule module;

    public TeleportSettingsPage(TeleportConsoleScreen host, TeleportModule module) {
        this.host = host;
        this.module = module;
    }

    // ── 组② TP地面 ──

    public void buildGround(CompactStack stack) {
        FoldSection section = new FoldSection("§fTP地面", SECTION_KEY_GROUND, host.collapsedSections());
        section.content().add(numberRow("地表扫描上限", DESC_MAX_RISE,
            TeleportSettings.MAX_RISE_MIN, TeleportSettings.MAX_RISE_MAX, 1.0, "%.0f",
            () -> (double) module.settings().maxRise,
            value -> module.settings().maxRise = (int) value,
            () -> (double) DEFAULTS.maxRise));
        stack.add(section);
    }

    // ── 组③ TP穿墙 ──

    public void buildWall(CompactStack stack) {
        FoldSection section = new FoldSection("§fTP穿墙", SECTION_KEY_WALL, host.collapsedSections());

        section.content().add(numberRow("最大穿墙距离", DESC_MAX_DISTANCE,
            TeleportSettings.MAX_DISTANCE_MIN, TeleportSettings.MAX_DISTANCE_MAX, 0.5, "%.1f",
            () -> module.settings().maxDistance,
            value -> module.settings().maxDistance = value,
            () -> DEFAULTS.maxDistance));

        section.content().add(numberRow("落点修正范围", DESC_MAX_DEVIATION,
            TeleportSettings.MAX_DEVIATION_MIN, TeleportSettings.MAX_DEVIATION_MAX, 0.5, "%.1f",
            () -> module.settings().maxDeviation,
            value -> module.settings().maxDeviation = value,
            () -> DEFAULTS.maxDeviation));

        section.content().add(numberRow("最大下落落差", DESC_MAX_FALL,
            TeleportSettings.MAX_FALL_MIN, TeleportSettings.MAX_FALL_MAX, 1.0, "%.0f",
            () -> (double) module.settings().maxFall,
            value -> module.settings().maxFall = (int) value,
            () -> (double) DEFAULTS.maxFall));

        section.content().add(toggleRow("摔落无伤", DESC_NO_FALL_DAMAGE,
            () -> module.settings().noFallDamage,
            value -> module.settings().noFallDamage = value,
            () -> DEFAULTS.noFallDamage));

        stack.add(section);
    }

    // ── 组④ TP坐标 ──

    public void buildCoord(CompactStack stack) {
        FoldSection section = new FoldSection("§fTP坐标", SECTION_KEY_COORD, host.collapsedSections());

        section.content().add(numberRow("坐标 X", DESC_COORD_X,
            TeleportSettings.COORD_XZ_MIN, TeleportSettings.COORD_XZ_MAX, 1.0, "%.0f",
            () -> (double) module.settings().coordX,
            value -> module.settings().coordX = (int) value,
            () -> (double) DEFAULTS.coordX));

        section.content().add(numberRow("坐标 Y", DESC_COORD_Y,
            TeleportSettings.COORD_Y_MIN, TeleportSettings.COORD_Y_MAX, 1.0, "%.0f",
            () -> (double) module.settings().coordY,
            value -> module.settings().coordY = (int) value,
            () -> (double) DEFAULTS.coordY));

        section.content().add(numberRow("坐标 Z", DESC_COORD_Z,
            TeleportSettings.COORD_XZ_MIN, TeleportSettings.COORD_XZ_MAX, 1.0, "%.0f",
            () -> (double) module.settings().coordZ,
            value -> module.settings().coordZ = (int) value,
            () -> (double) DEFAULTS.coordZ));

        section.content().add(numberRow("回退搜索半径", DESC_FALLBACK_RADIUS,
            TeleportSettings.FALLBACK_RADIUS_MIN, TeleportSettings.FALLBACK_RADIUS_MAX, 1.0, "%.0f",
            () -> (double) module.settings().fallbackRadius,
            value -> module.settings().fallbackRadius = (int) value,
            () -> (double) DEFAULTS.fallbackRadius));

        stack.add(section);
    }

    // ── 组⑤ 验证 + 组⑥ 调试 ──

    public void buildVerify(CompactStack stack) {
        FoldSection verify = new FoldSection("§f验证", SECTION_KEY_VERIFY, host.collapsedSections());

        verify.content().add(numberRow("回弹判定阈值", DESC_VERIFY_THRESHOLD,
            TeleportSettings.VERIFY_THRESHOLD_MIN, TeleportSettings.VERIFY_THRESHOLD_MAX, 0.25, "%.2f",
            () -> module.settings().verifyThreshold,
            value -> module.settings().verifyThreshold = value,
            () -> DEFAULTS.verifyThreshold));

        verify.content().add(numberRow("验证窗口", DESC_VERIFY_WINDOW,
            TeleportSettings.VERIFY_WINDOW_MIN, TeleportSettings.VERIFY_WINDOW_MAX, 1.0, "%.0f",
            () -> (double) module.settings().verifyWindow,
            value -> module.settings().verifyWindow = (int) value,
            () -> (double) DEFAULTS.verifyWindow));

        stack.add(verify);

        FoldSection debug = new FoldSection("§f调试", SECTION_KEY_VERIFY + ":debug", host.collapsedSections());
        debug.content().add(toggleRow("调试渲染", DESC_DEBUG_RENDER,
            () -> module.settings().debugRender,
            value -> module.settings().debugRender = value,
            () -> DEFAULTS.debugRender));
        stack.add(debug);
    }

    // ── 行构件组装 ──

    /** 数值行：改动即写盘；描述同时作为悬停提示 */
    private ConsoleRow numberRow(String label, String desc, double min, double max,
                                 double step, String format,
                                 Supplier<Double> getter, DoubleConsumer setter,
                                 Supplier<Double> defaultValue) {
        SettingNumberBox box = new SettingNumberBox(min, max, step, format, getter, value -> {
            setter.accept(value);
            persist();
        });
        return new ConsoleRow(host, () -> label, desc, null,
            List.of(new Ctl(box, desc),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    persist();
                    host.reload();
                }, label)));
    }

    /** 开关行：改动即写盘；行尾 ↺ 恢复本行默认值 */
    private ConsoleRow toggleRow(String label, String desc,
                                 Supplier<Boolean> getter, Consumer<Boolean> setter,
                                 Supplier<Boolean> defaultValue) {
        SettingToggle toggle = new SettingToggle(getter, value -> {
            setter.accept(value);
            persist();
        });
        return new ConsoleRow(host, () -> label, desc, null,
            List.of(new Ctl(toggle, desc),
                ConsoleWidgets.resetCtl(() -> {
                    setter.accept(defaultValue.get());
                    persist();
                    host.reload();
                }, label)));
    }

    /** 改设置即落盘（第 172-175 条） */
    private void persist() {
        ModuleManager.saveSettings(module);
    }
}
