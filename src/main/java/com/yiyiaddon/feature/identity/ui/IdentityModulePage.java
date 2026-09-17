package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.feature.identity.IdIdentifyModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingToggle;

/**
 * ID 模块的独立页面：紧凑型模块控制面板。
 *
 * <p>页面内容与旧项目 {@code IdIdentifyModule} 的设置项一一对应，且只有这三项：识别模式、当前模式、
 * 方块语义调试。旧项目该模块没有统计块，没有快照数量 / 已选识别目标 / 数据目录这些信息行，也没有
 * 「清理失效目标」按钮（失效目标由自动箱子监听器自动清理），因此本页面同样不提供。识别动作由模块
 * 开关自身与 {@code .id} 指令承担，页面不再另设动作分段控件。</p>
 *
 * <p>设置项的读写路径完全保持原样：模块开关仍然写 {@code ModuleManager.setEnabled}，识别模式仍然
 * 读写 {@code IdentityModuleConfig} 的模式字段。</p>
 *
 * <p>页面本身即 {@link ModulePage} 实现，每次被点击打开都会新建一份，因此折叠状态与动画不会跨次
 * 打开残留。</p>
 */
public final class IdentityModulePage extends CompactModulePage implements ModulePage {

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
        // 三个设置项，与旧项目 IdIdentifyModule 一一对应
        addCore(new CompactRow("识别模式", this::modeHint,
                new SettingSegmented(module.modeLabels(), module::modeIndex, module::setModeIndex)));
        addCore(new CompactRow("当前模式", () -> "当前选中的识别模式（实时显示）。",
                new SettingText(() -> "§a§l" + module.config().mode().displayName())));
        addCore(new CompactRow("方块语义调试",
                () -> "开启后，.id 方块 会把方块资源包语义解析的完整过程输出到日志 latest.log（前缀 [BlockSemanticDebug]），用于定位真机解析失败原因。默认关闭。",
                new SettingToggle(module::blockSemanticDebug, module::setBlockSemanticDebug)));
    }

    /** 当前识别模式的说明文本。 */
    private String modeHint() {
        return switch (module.config().mode()) {
            case CHAT_COPY -> MODE_CHAT_COPY;
            case AUTO_SAVE -> MODE_AUTO_SAVE;
            case CROSSHAIR_BLOCK -> MODE_CROSSHAIR;
        };
    }
}
