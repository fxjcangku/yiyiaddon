package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.ui.screen.PanelScreen;
import net.minecraft.client.gui.screens.Screen;

/**
 * ID 更多管理窗口（旧项目 {@code IdManagementScreen}）。
 *
 * <p>把低频动作从主清单移出：手动添加、四类目录入口与数据清理。按钮文字、先后顺序与分隔线位置
 * 全部沿用旧项目；每个动作由调用方注入，窗口本身不持有任何管理器。</p>
 */
public final class IdManagementScreen extends PanelScreen {

    /** 窗口标题：旧项目原文。 */
    public static final String WINDOW_TITLE = "ID 更多管理";

    public IdManagementScreen(Screen parent,
                              Runnable openAddScreen,
                              Runnable openItemsDirectory,
                              Runnable openEntitiesDirectory,
                              Runnable openBlocksDirectory,
                              Runnable openRootDirectory,
                              Runnable openDataCleanScreen) {
        super(WINDOW_TITLE, parent);
        exitToGame();
        addButton("手动添加物品 ID", openAddScreen);
        addButton("打开物品 ID 目录", openItemsDirectory);
        addButton("打开实体 ID 目录", openEntitiesDirectory);
        addButton("打开方块目录", openBlocksDirectory);
        addButton("打开 ID 总目录", openRootDirectory);
        addDivider();
        addButton("§c数据清理 >", openDataCleanScreen);
        addDivider();
        addButton("§7返回", this::requestClose);
    }
}
