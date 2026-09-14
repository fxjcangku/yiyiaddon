package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.identity.IdentityModule;
import com.yiyiaddon.feature.identity.config.IdentityModuleConfig;
import com.yiyiaddon.feature.identity.service.IdentityActions;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.ui.component.CollapsibleSection;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeyValueRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.widget.SettingButton;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;

/**
 * ID 模块的独立页面：紧凑型模块控制面板。
 *
 * <p>按「顶部状态 → 中间核心配置 → 底部状态信息」三段排布，高频操作一屏可见，低频项收进折叠的
 * 高级设置，不再是一条需要长滚动的设置列表。</p>
 *
 * <p>设置项的读写路径完全保持原样：模块开关仍然写 {@code ModuleManager.setEnabled}，识别模式仍然
 * 读写 {@code IdentityModuleConfig} 的模式字段，详细输出仍然读写详细输出字段，三个识别动作仍然
 * 调用模块自己的识别入口；统计与最近结果每帧从身份服务与识别目标配置读取，不保存任何副本。</p>
 *
 * <p>页面本身即 {@link ModulePage} 实现，每次被点击打开都会新建一份，因此折叠状态与动画不会跨次
 * 打开残留。</p>
 */
public final class IdentityModulePage extends CompactModulePage implements ModulePage {

    /** 识别动作分段：下标 0 物品、1 方块、2 实体，顺序与所属动作一一对应。 */
    private static final List<String> ACTION_LABELS = List.of("物品", "方块", "实体");

    private final IdentityModule module;

    public IdentityModulePage(IdentityModule module) {
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
        // 顶部：状态、模块开关与快捷键
        setHeader(new ModuleStatusBar(
                () -> module.isEnabled() ? "运行中" : "未启用",
                module::isEnabled,
                new KeybindBadge(module.keybindId()),
                new SettingToggle(module::isEnabled, value -> ModuleManager.setEnabled(module.id(), value))));

        // 中间：核心配置，全部一屏可见
        addCore(new CompactRow("识别模式",
                () -> IdentityModuleConfig.describe(module.config().mode()),
                new SettingSegmented(module.modeLabels(), module::modeIndex, module::setModeIndex)));
        addCore(new CompactRow("", () -> "点击执行对应识别动作",
                new SettingSegmented(ACTION_LABELS, this::runIdentify)));
        addCore(new CompactRow("详细输出", () -> "开启后额外输出全部识别字段",
                new SettingToggle(module::verbose, module::setVerbose)));

        // 底部：运行结果与统计
        addFooter(new KeyValueRow("最近结果", List.of(KeyValueRow.Value.of(module::latestText))));
        addFooter(new KeyValueRow("数据统计", List.of(
                KeyValueRow.Value.of("物品", () -> String.valueOf(IdentityActions.itemCount())),
                KeyValueRow.Value.of("实体", () -> String.valueOf(IdentityActions.entityCount())),
                KeyValueRow.Value.of("方块", () -> String.valueOf(IdentityActions.blockCount())))));

        // 折叠：低频设置与身份库维护
        CollapsibleSection advanced = new CollapsibleSection("高级设置", () -> "快照、目标与身份库维护");
        advanced.content()
                .add(new CompactRow("物品快照", () -> "已保存的物品状态快照数量",
                        new SettingText(() -> IdentityActions.itemSnapshotCount() + " 项")))
                .add(new CompactRow("方块快照", () -> "已保存的方块状态快照数量",
                        new SettingText(() -> IdentityActions.blockSnapshotCount() + " 项")))
                .add(new CompactRow("已选识别目标", () -> "识别目标配置中仍有效的选中项",
                        new SettingText(() -> IdentityActions.selectedTargetCount() + " 项")))
                .add(new CompactRow("数据目录", () -> "身份数据在磁盘上的位置",
                        new SettingText(() -> GamePaths.identityRoot().toString(), 280f)))
                .add(new CompactRow("输出统计", () -> "把身份库统计输出到聊天栏",
                        new SettingButton("输出统计", module::reportStats)))
                .add(new CompactRow("清理失效目标", () -> "删除选中项中已不存在的身份引用",
                        new SettingButton("清理失效目标", module::pruneTargets)));
        addFooter(advanced);
    }

    /** 分段动作：下标与识别动作一一对应。 */
    private void runIdentify(int index) {
        switch (index) {
            case 0 -> module.identifyItem();
            case 1 -> module.identifyBlock();
            case 2 -> module.identifyEntity();
            default -> {
            }
        }
    }
}
