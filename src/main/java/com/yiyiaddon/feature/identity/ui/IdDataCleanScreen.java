package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.ui.screen.PanelScreen;
import net.minecraft.client.gui.screens.Screen;

/**
 * ID 数据清理窗口（旧项目 {@code IdDataCleanScreen}）。
 *
 * <p>集中全部不可逆清空操作，与「ID 更多管理」分层。常规清空与「最高危险」清空用分隔线分区，
 * 避免危险等级混淆；二次确认由调用方注入的动作负责。按钮文字与分区顺序沿用旧项目。</p>
 */
public final class IdDataCleanScreen extends PanelScreen {

    /** 窗口标题：旧项目原文。 */
    public static final String WINDOW_TITLE = "ID 数据清理";

    public IdDataCleanScreen(Screen parent,
                             Runnable clearItems,
                             Runnable clearEntities,
                             Runnable clearBlocks,
                             Runnable clearSnapshots,
                             Runnable clearAllBlocks,
                             Runnable clearAllIds) {
        super(WINDOW_TITLE, parent);
        addButton("§c清空全部物品 ID", clearItems);
        addButton("§c清空全部实体 ID", clearEntities);
        addButton("§c清空方块稳定记录", clearBlocks);
        addButton("§c清空方块历史快照", clearSnapshots);
        addButton("§c清空全部方块数据", clearAllBlocks);
        addDivider();
        addButton("§c§l【最高危险】清空全部 ID 数据", clearAllIds);
        addDivider();
        addButton("§7返回", this::requestClose);
    }
}
