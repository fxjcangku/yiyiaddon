package com.yiyiaddon.feature.identity;

import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.feature.identity.service.IdentityActions;
import com.yiyiaddon.feature.identity.ui.IdConfigPage;
import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.page.ModulePage;

import java.util.List;

/**
 * ID 配置管理模块（旧项目 {@code IdConfigModule}）：只管已识别的身份数据。
 *
 * <p><b>用户交互资产：</b>模块中文名 {@code ID配置管理}、description 沿用旧项目原文；分类不属迁移资产，
 * 本模块归入本项目唯一的模块分组 {@link CategoryRegistry#MODULE_GROUP_ID}。旧项目该模块
 * <b>不注册任何设置项</b>，功能全部以界面按钮与列表呈现，因此本类同样不定义设置。</p>
 *
 * <p><b>未迁移部分：</b>旧项目该模块的完整管理界面（搜索、筛选、分页、逐条删除、清空、手动添加
 * 物品 ID、打开各类数据目录、数据清理屏）尚未迁移，本阶段只提供当前已有的统计、刷新与清理入口。
 * 文案随该界面在后续阶段整体迁移，禁止在此凭印象补写旧文案。</p>
 */
public final class IdConfigModule extends Module {

    /** 模块 ID，同时作为状态文件键与快捷键键名后缀 */
    public static final String MODULE_ID = "id_config";

    /** 回执前缀使用的模块名：旧项目模块显示名原文 */
    public static final String MESSAGE_MODULE = "ID配置管理";

    public IdConfigModule() {
        super(MODULE_ID, MESSAGE_MODULE, CategoryRegistry.MODULE_GROUP_ID,
                "管理已识别的物品/实体/方块ID：搜索、筛选、分页、删除、清空、打开目录。点击按钮查看说明。");
    }

    @Override
    public String name() {
        return "IdConfig";
    }

    @Override
    public int order() {
        return 20;
    }

    @Override
    public String version() {
        return "1.0.0";
    }

    // ── 生命周期 ──

    @Override
    protected void onEnable() {
        IdentityService.shared().load();
    }

    // ── 界面 ──

    @Override
    public ModulePage page() {
        return new IdConfigPage(this);
    }

    // ── 供页面调用的功能入口 ──

    /** 重读磁盘上的身份数据（旧项目面板按钮原文：刷新（重读磁盘）） */
    public void reloadFromDisk() {
        IdentityService.shared().reload();
        ClientChat.send(MESSAGE_MODULE, "§a§l✓ 已重读磁盘");
    }

    /** 输出身份库统计到聊天栏 */
    public void reportStats() {
        IdentityService service = IdentityService.shared();
        CommandMessageFormatter.of(MESSAGE_MODULE, "身份库")
                .field("物品", "§f" + service.itemCount() + " 项")
                .field("实体", "§f" + service.entityCount() + " 项")
                .field("方块", "§f" + service.blockCount() + " 项")
                .field("物品快照", "§f" + service.itemSnapshotCount() + " 项")
                .field("方块快照", "§f" + service.blockSnapshotCount() + " 项")
                .field("已选目标", "§f" + IdentityActions.selectedTargetCount() + " 项")
                .field("数据目录", "§f" + GamePaths.identityRoot())
                .status(CommandMessageFormatter.Level.INFO, "统计完成")
                .send();
    }

    /** 清理失效的识别目标 */
    public void pruneTargets() {
        int pruned = IdentityActions.pruneInvalidTargets();
        ClientChat.send(MESSAGE_MODULE, pruned == 0 ? "§7没有失效的识别目标" : "§7已清理 " + pruned + " 项失效的识别目标");
    }

    /** 身份清单汇总行（旧项目列表表头口径：共 N 条） */
    public String countsText() {
        return "共 " + (IdentityActions.itemCount() + IdentityActions.entityCount() + IdentityActions.blockCount()) + " 条";
    }

    public List<String> typeCounts() {
        return List.of(
                "物品 " + IdentityActions.itemCount(),
                "实体 " + IdentityActions.entityCount(),
                "方块 " + IdentityActions.blockCount());
    }
}
