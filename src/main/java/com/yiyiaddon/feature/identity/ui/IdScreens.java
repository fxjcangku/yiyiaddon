package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.feature.identity.IdConfigModule;
import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.model.identity.EntityIdentity;
import com.yiyiaddon.model.identity.ItemIdentity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

/**
 * ID 模块的窗口入口集合：集中全部独立窗口的打开与互相跳转。
 *
 * <p>业务模块与指令只调这里的静态方法，不直接 new 屏幕，因此「哪个窗口返回哪个窗口」这类导航关系
 * 只在一处定义。以后移植别的模块时照本类结构复制即可。</p>
 *
 * <p><b>跳转关系与旧项目一致</b>：只有「数据清理」的「返回」回到「更多管理」，其余窗口的
 * 关闭 / 取消 / 返回 / 确认都是直接回到游戏（{@code setScreen(null)}）。</p>
 */
public final class IdScreens {

    private IdScreens() {
    }

    /** 打开「ID 更多管理」；「返回」直接回游戏。 */
    public static void openManagement(Screen parent, IdConfigModule module) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        // 持有自身引用：「数据清理」的「返回」要回到本窗口
        IdManagementScreen[] self = new IdManagementScreen[1];
        IdManagementScreen management = new IdManagementScreen(parent,
                () -> openAdd(parent),
                module::openItemsDirectory,
                module::openEntitiesDirectory,
                module::openBlocksDirectory,
                module::openRootDirectory,
                () -> openDataClean(self[0], module));
        self[0] = management;
        client.setScreen(management);
    }

    /** 打开「ID 数据清理」；「返回」回到「ID 更多管理」。 */
    public static void openDataClean(Screen parent, IdConfigModule module) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new IdDataCleanScreen(parent,
                module::confirmClearItems,
                module::confirmClearEntities,
                module::confirmClearBlocks,
                module::confirmClearSnapshots,
                module::confirmClearAllBlocks,
                module::confirmClearAllIds));
    }

    /** 打开「添加物品」；取消与添加成功后直接回游戏。 */
    public static void openAdd(Screen parent) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new IdAddScreen(parent));
    }

    /** 打开物品识别结果窗口。 */
    public static void openItemResult(ItemIdentity identity, Screen parent) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new IdResultScreen(identity, parent));
    }

    /** 打开方块识别结果窗口。 */
    public static void openBlockResult(BlockIdentity identity, Screen parent) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new IdBlockResultScreen(identity, parent));
    }

    /** 打开实体识别结果窗口。 */
    public static void openEntityResult(EntityIdentity identity, Screen parent) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new IdEntityResultScreen(identity, parent));
    }
}
