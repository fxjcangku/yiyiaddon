package com.yiyiaddon.feature.autochest.ui;

import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.model.autochest.ContainerType;
import com.yiyiaddon.model.autochest.ContainerTypeRegistry;
import com.yiyiaddon.ui.component.ButtonRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.gui.screens.Screen;

/**
 * 容器类型多选窗口（旧项目 {@code autochest/ui/ContainerTypeSelectScreen}）。
 *
 * <p><b>用户交互资产（逐字）：</b>窗口标题 {@code 容器类型}；顶部标签
 * {@code §7点击切换容器类型，默认全部启用}；每行一个切换按钮，文案为
 * {@code (§a✓ | §c✗) + 类型显示名}，点击即时切换（无确定按钮）。</p>
 *
 * <p>数据源唯一来自 {@link ContainerTypeRegistry}，新增类型后本界面自动呈现，无需改动。
 * 窗口外壳（面板、圆角、玻璃、滚动与返回按钮）全部用本项目现有体系。</p>
 */
public final class ContainerTypePage extends PanelScreen {

    /** 顶部标签：旧项目 {@code ContainerTypeSelectScreen.java:32} 原文 */
    private static final String HEADER = "§7点击切换容器类型，默认全部启用";

    private static final float HEADER_HEIGHT = 22f;
    private static final float HEADER_SIZE = 11f;

    private final AutoChestModule module;
    private final AutoChestSettings settings;

    public ContainerTypePage(Screen parent, AutoChestModule module) {
        super("容器类型", parent);
        this.module = module;
        this.settings = module.settings();
        build();
    }

    private void build() {
        content().add(new TextLine(HEADER).height(HEADER_HEIGHT).size(HEADER_SIZE));
        for (ContainerType type : ContainerTypeRegistry.all()) {
            // 文案每次绘制时按当前启用状态取值，点击后立即反映（旧项目 rebuild 的效果）
            content().add(new ButtonRow(new Button(() -> toggleLabel(type), () -> toggle(type))));
        }
    }

    /** 行文案：{@code (§a✓ | §c✗) + 类型显示名} */
    private String toggleLabel(ContainerType type) {
        return (settings.containerTypeIds.contains(type.id()) ? "§a✓ " : "§c✗ ") + type.displayName();
    }

    /** 点击即时切换启用状态并落盘（旧项目 ContainerTypeSelectScreen.setEnabled） */
    private void toggle(ContainerType type) {
        boolean enabled = settings.containerTypeIds.contains(type.id());
        settings.setContainerTypeEnabled(type.id(), !enabled);
        module.persistSettings();
    }
}
