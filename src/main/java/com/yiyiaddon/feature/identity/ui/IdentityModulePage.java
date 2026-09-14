package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.identity.IdIdentifyModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.component.CollapsibleSection;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeyValueRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.widget.Button;
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

    /**
     * 「识别模式」说明：旧项目 {@code IdIdentifyModule} 设置项 description 原文，按模式逐段对应。
     */
    private static final String MODE_CHAT_COPY = "聊天复制/显示：识别手持物品后弹出结果屏幕";
    private static final String MODE_AUTO_SAVE = "自动保存：识别手持物品后直接写入 ID 配置";
    private static final String MODE_CROSSHAIR = "准星方块识别：识别准星真实命中的方块";

    private final IdIdentifyModule module;

    public IdentityModulePage(IdIdentifyModule module) {
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
        addCore(new CompactRow("识别模式", this::modeHint,
                new SettingSegmented(module.modeLabels(), module::modeIndex, module::setModeIndex)));
        addCore(new CompactRow("", () -> "点击执行对应识别动作",
                new SettingSegmented(ACTION_LABELS, this::runIdentify)));
        addCore(new CompactRow("方块语义调试",
                () -> "开启后，.id 方块 会把方块资源包语义解析的完整过程输出到日志 latest.log（前缀 [BlockSemanticDebug]），用于定位真机解析失败原因。默认关闭。",
                new SettingToggle(module::blockSemanticDebug, module::setBlockSemanticDebug)));

        // 底部：身份库分层统计（与 ID 配置管理共用同一份身份服务数据）
        addFooter(new KeyValueRow("数据统计", List.of(
                KeyValueRow.Value.of("物品", () -> String.valueOf(IdentityService.shared().itemCount())),
                KeyValueRow.Value.of("实体", () -> String.valueOf(IdentityService.shared().entityCount())),
                KeyValueRow.Value.of("方块", () -> String.valueOf(IdentityService.shared().blockCount())))));

        // 折叠：低频设置与身份库维护
        CollapsibleSection advanced = new CollapsibleSection("高级设置", () -> "快照、目标与身份库维护");
        advanced.content()
                .add(new CompactRow("物品快照", () -> "已保存的物品状态快照数量",
                        new SettingText(() -> IdentityService.shared().itemSnapshotCount() + " 项")))
                .add(new CompactRow("方块快照", () -> "已保存的方块状态快照数量",
                        new SettingText(() -> IdentityService.shared().blockSnapshotCount() + " 项")))
                .add(new CompactRow("已选识别目标", () -> "识别目标配置中仍有效的选中项",
                        new SettingText(() -> IdentityTargetConfig.selectedItems(IdentityService.shared()).size() + " 项")))
                .add(new CompactRow("数据目录", () -> "身份数据在磁盘上的位置",
                        new SettingText(() -> GamePaths.identityRoot().toString(), 280f)))
                .add(new CompactRow("清理失效目标", () -> "删除选中项中已不存在的身份引用",
                        new Button("清理失效目标", module::pruneTargets)));
        addFooter(advanced);
    }

    /** 当前识别模式的说明文本。 */
    private String modeHint() {
        return switch (module.config().mode()) {
            case CHAT_COPY -> MODE_CHAT_COPY;
            case AUTO_SAVE -> MODE_AUTO_SAVE;
            case CROSSHAIR_BLOCK -> MODE_CROSSHAIR;
        };
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
