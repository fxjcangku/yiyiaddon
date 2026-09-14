package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.identity.IdIdentifyModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingToggle;

/**
 * ID 识别模块（{@code ID识别}）的独立页面。
 *
 * <p>页面外壳为本项目紧凑面板；<b>页面上的字段标签与说明全部为旧项目 {@code IdIdentifyModule} 原文</b>，
 * 与其 {@code sgIdentify}（「识别」组）的三个设置项一一对应：{@code 识别模式}、{@code 当前模式}、
 * {@code 方块语义调试}。旧项目没有的设置项一律不得出现（《开发习惯》第 126 / 128 / 163 条）。</p>
 *
 * <p>数据管理类操作（重读磁盘、清理失效目标、打开数据目录）归 {@code ID配置管理} 模块，
 * 不在本页出现。</p>
 */
public final class IdentityModulePage extends CompactModulePage implements ModulePage {

    /** 旧项目「识别模式」设置项描述原文（一条完整描述，未拆分改写） */
    private static final String MODE_DESCRIPTION =
            "聊天复制/显示：识别手持物品后弹出结果屏幕；自动保存：识别手持物品后直接写入 ID 配置；准星方块识别：识别准星真实命中的方块。";

    /** 旧项目「当前模式」设置项描述原文 */
    private static final String CURRENT_MODE_DESCRIPTION = "当前选中的识别模式（实时显示）。";

    /** 旧项目「方块语义调试」设置项描述原文 */
    private static final String BLOCK_SEMANTIC_DEBUG_DESCRIPTION =
            "开启后，.id 方块 会把方块资源包语义解析的完整过程输出到日志 latest.log（前缀 [BlockSemanticDebug]），用于定位真机解析失败原因。默认关闭。";

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

        // 「识别」组：识别模式 → 当前模式 → 方块语义调试（对应旧项目 sgIdentify）
        addCore(new CompactRow("识别模式", () -> MODE_DESCRIPTION,
                new SettingSegmented(module.modeLabels(), module::modeIndex, module::setModeIndex)));
        addCore(new CompactRow("当前模式", () -> CURRENT_MODE_DESCRIPTION,
                new SettingText(() -> "§a§l" + module.config().mode().displayName())));
        addCore(new CompactRow("方块语义调试", () -> BLOCK_SEMANTIC_DEBUG_DESCRIPTION,
                new SettingToggle(module::blockSemanticDebug, module::setBlockSemanticDebug)));
    }
}
