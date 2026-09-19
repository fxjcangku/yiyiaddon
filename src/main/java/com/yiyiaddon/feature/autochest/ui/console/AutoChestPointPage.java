package com.yiyiaddon.feature.autochest.ui.console;

import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.ui.AutoChestConsoleScreen;
import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.model.autochest.ScanMode;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.console.PointRenderSection;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.widget.Button;

import java.util.List;

/**
 * 自动箱子控制台「点位」页：标点模式的点位卡片与全部点位 / 记录管理动作。
 *
 * <p>逐字搬自旧项目配置页头部的标点区（{@code AutoChestPage.buildMarkerActions} /
 * {@code pointCard}）：四个按钮文案（{@code 设置箱子点位（对准容器）} / {@code 清空全部点位} /
 * {@code 清除当前维度处理记录} / {@code 清除全部处理记录}）、点位卡片六段文案
 * （{@code §b■ §f类型名} / 坐标串 / {@code §8▸ §7维度 §8▸ §f维度名} / {@code §a未处理} / {@code §c已处理} /
 * {@code §c删除}）、空态 {@code §8当前维度暂无箱子点位}、三个二次确认窗文案，一个字未改。</p>
 *
 * <p><b>可见性照旧</b>：这些内容整体属于「标点模式」专属（旧项目用 {@code visible(...)} 整体包裹），
 * 非标点模式下旧项目一样不显示——本页非标点模式时只留一行导航提示，避免玩家点进空白页以为坏了。</p>
 *
 * <p><b>形态差异（登记）</b>：旧卡片是「标题 / 明细 / 状态徽章」三列，控制台行没有徽章列，
 * 处理状态并入行尾注释（{@code … §8▸ §f维度名  §c已处理}）；分隔线由页签本身替代。</p>
 *
 * <p><b>新增一行（用户 2026-09-19）</b>：「字牌大小」行 —— 与星露谷点位页同一行构件
 * （共用件 {@code PointRenderSection.labelSizeRow}），加在点位清单之后、清空类动作之前。
 * 它只存在于标点模式分支内，上面「非标点模式只显示一行提示」的分支逻辑一字未动。</p>
 */
public final class AutoChestPointPage {

    /** 非标点模式下的导航提示（控制台新增文案：不含任何设置项与播报） */
    private static final String NOT_MARKER_HINT =
        "§8当前运行模式不是「标点模式」，点位与处理记录管理只在标点模式下出现";

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final AutoChestSettings DEFAULTS = new AutoChestSettings();

    private final AutoChestConsoleScreen owner;
    private final AutoChestModule module;

    public AutoChestPointPage(AutoChestConsoleScreen owner, AutoChestModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        AutoChestSettings settings = module.settings();

        if (settings.scanMode != ScanMode.MARKER) {
            stack.add(new Note(owner, NOT_MARKER_HINT));
            return;
        }

        stack.add(new ButtonStrip(owner, List.of(new Ctl(new Button("设置箱子点位（对准容器）", () -> {
            module.addPointFromCrosshair();
            closeToGame();
        }))), ButtonStrip.BUTTON_HEIGHT));

        List<ChestTarget> points = module.pointStore().pointsInCurrentDimension();
        if (points.isEmpty()) {
            stack.add(new Note(owner, "§8当前维度暂无箱子点位"));
        } else {
            for (ChestTarget point : points) stack.add(pointRow(point));
        }

        // 字牌大小（用户 2026-09-19）：与星露谷点位页同一行构件，放在点位清单之后、清空类动作之前。
        // 只加在标点模式分支内，非标点模式的「一行提示即返回」分支一个字没动。
        stack.add(new PointRenderSection(owner, module::persistSettings, owner::reload, null, List.of())
            .labelSizeRow(AutoChestSettings.NAME_LABEL_SIZE, AutoChestSettings.DESC_LABEL_SIZE,
                AutoChestSettings.LABEL_SIZE_MIN, AutoChestSettings.LABEL_SIZE_MAX,
                () -> settings.labelSize,
                value -> settings.labelSize = value,
                DEFAULTS.labelSize));

        stack.add(actionRow("清空全部点位",
            () -> confirm("清空点位", "确定要清空全部标点吗？此操作不可恢复。", module::clearAllPoints)));
        stack.add(actionRow("清除当前维度处理记录",
            () -> confirm("清除记录", "确定清除当前维度的已处理记录吗？", module::clearCurrentDimensionRecords)));
        stack.add(actionRow("清除全部处理记录",
            () -> confirm("清除记录", "确定清除全部服务器的已处理记录吗？", module::clearAllRecords)));
    }

    /** 整行按钮（旧项目 {@code ButtonRow}）：控制台里用单按钮页签条等宽铺满，视觉与旧面板一致 */
    private ButtonStrip actionRow(String label, Runnable action) {
        return new ButtonStrip(owner,
            List.of(new Ctl(new Button(label, action))), ButtonStrip.BUTTON_HEIGHT);
    }

    /**
     * 单个箱子点位行：容器类型 + 坐标 + 维度 + 处理状态 + 删除。
     *
     * <p>处理状态在页面构建时判定一次（与旧项目 {@code buildPointCard} 一致），避免逐帧触碰记录存储。</p>
     */
    private ConsoleRow pointRow(ChestTarget point) {
        // 容器显示名与 ESP 字牌共用一处实现（认不出的类型两边回退文案也一致）
        String typeName = AutoChestModule.formatContainerName(point.containerType());
        String dim = WorldIdentity.dimensionDisplayName(point.dimension());
        long expireMs = module.settings().recordExpireMinutes * 60_000L;
        String status = module.recordStore().isProcessed(
            point.pos(), point.dimension(), point.containerType(), expireMs)
            ? "§c已处理" : "§a未处理";

        return ConsoleRow.liveComment(owner, () -> "§b■ §f" + typeName, null,
            () -> AutoChestModule.formatCoords(
                point.pos().getX(), point.pos().getY(), point.pos().getZ())
                + " §8▸ §7维度 §8▸ §f" + dim + "  " + status,
            List.of(new Ctl(new Button("§c删除", () -> {
                module.deletePoint(point);
                closeToGame();
            }))));
    }

    /** 二次确认窗（与旧项目同一构造与文案）；确认动作沿用旧项目「执行后回到游戏」 */
    private void confirm(String title, String message, Runnable action) {
        if (owner.client() == null) return;
        owner.client().setScreen(new ConfirmPanelScreen(title, List.of(message),
            "§c§l确认", action, owner.client().screen));
    }

    /** 执行后直接回到游戏（旧项目 {@code mc.setScreen(null)}） */
    private void closeToGame() {
        if (owner.client() != null) owner.client().setScreen(null);
    }
}
