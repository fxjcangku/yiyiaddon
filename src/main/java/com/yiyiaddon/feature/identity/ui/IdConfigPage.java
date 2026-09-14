package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.identity.IdConfigModule;
import com.yiyiaddon.feature.identity.service.IdentityActions;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeyValueRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.widget.SettingButton;
import com.yiyiaddon.ui.widget.SettingText;

import java.util.List;

/**
 * ID 配置管理模块的独立页面。
 *
 * <p><b>只呈现当前已有的能力</b>（统计、重读磁盘、清理失效目标、数据目录）。旧项目该模块的完整管理
 * 界面（搜索、筛选、分页、逐条删除、清空、手动添加、打开目录、数据清理屏）尚未迁移，其原文随界面
 * 在后续阶段整体搬入；本页不得凭印象补写旧文案。</p>
 */
public final class IdConfigPage extends CompactModulePage implements ModulePage {

    private final IdConfigModule module;

    public IdConfigPage(IdConfigModule module) {
        this.module = module;
        build();
    }

    @Override
    public BasePage createPage(ModuleEntry entry) {
        return this;
    }

    @Override
    public String getTitle() {
        return module.displayName();
    }

    @Override
    public String getSubtitle() {
        return module.description();
    }

    private void build() {
        setHeader(new ModuleStatusBar(
                () -> module.isEnabled() ? "运行中" : "未启用",
                module::isEnabled,
                new KeybindBadge(module.keybindId()),
                new com.yiyiaddon.ui.widget.SettingToggle(module::isEnabled,
                        value -> ModuleManager.setEnabled(module.id(), value))));

        addCore(new CompactRow("重读磁盘", () -> "重新从磁盘载入物品 / 实体 / 方块身份数据",
                new SettingButton("刷新", module::reloadFromDisk)));
        addCore(new CompactRow("清理失效目标", () -> "删除选中项中已不存在的身份引用",
                new SettingButton("清理", module::pruneTargets)));
        addCore(new CompactRow("输出统计", () -> "把身份库统计输出到聊天栏",
                new SettingButton("输出", module::reportStats)));

        addFooter(new KeyValueRow("当前ID清单", List.of(
                KeyValueRow.Value.of("物品", () -> String.valueOf(IdentityActions.itemCount())),
                KeyValueRow.Value.of("实体", () -> String.valueOf(IdentityActions.entityCount())),
                KeyValueRow.Value.of("方块", () -> String.valueOf(IdentityActions.blockCount())))));
        addFooter(new KeyValueRow("快照与目标", List.of(
                KeyValueRow.Value.of("物品快照", () -> String.valueOf(IdentityActions.itemSnapshotCount())),
                KeyValueRow.Value.of("方块快照", () -> String.valueOf(IdentityActions.blockSnapshotCount())),
                KeyValueRow.Value.of("已选目标", () -> String.valueOf(IdentityActions.selectedTargetCount())))));
        addFooter(new CompactRow("数据目录", () -> "身份数据在磁盘上的位置",
                new SettingText(() -> GamePaths.identityRoot().toString(), 280f)));
    }
}
