package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.identity.IdIdentifyModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.Minecraft;

import java.util.List;

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

    /**
     * 使用说明（页脚）。
     *
     * <p>旧项目该模块也没有说明页，这里是按本模块的实际行为写的：识别动作、三种模式的分流、
     * 指令入口与「开启即自关」的一次性用法，逐条对得上 {@code IdIdentifyModule} 与
     * {@code IdentityCommand} 的实现，不虚构行为。</p>
     */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("这个模块做什么",
            "识别手持物品、准星方块或准星实体，取出它们的稳定身份（原版 ID / 自定义 ID / 资源包语义）。",
            "「自动保存」模式下识别结果直接写进 ID 配置，供自动箱子等模块按身份匹配；",
            "另外两种模式只弹结果窗口，识别本身不写盘（窗口里可以自己保存）。"),
        new HelpPanelScreen.HelpSection("怎么识别",
            "① 在模块卡片上点开关：开启即按当前模式识别一次，约 0.4 秒后自动关闭。",
            "② 用指令随时识别（不看模块开关）：",
            "§f.id 物品 §7识别手持物品（主手 → 副手）　§f.id 方块 §7识别准星方块　"
                + "§f.id 实体 §7识别准星实体"),
        new HelpPanelScreen.HelpSection("三种识别模式",
            "聊天复制/显示：识别后弹出结果窗口，不写盘。",
            "自动保存：识别后直接写入 ID 配置，聊天栏播报保存结果。",
            "准星方块识别：识别准星命中的方块并弹结果窗口（原版方块与自定义方块统一采集）。"),
        new HelpPanelScreen.HelpSection("注意事项",
            "未进入世界时不能启用（识别依赖主手物品、准星命中结果与世界数据）。",
            "换服务器会重新读盘，并自动清理已经失效的识别目标。",
            "「方块语义调试」只在排查解析失败时开：它会把完整解析过程写进 latest.log，日志会变大。"),
    };

    /** 内嵌说明行（去掉窗口外框三行） */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

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

        // 本模块没有控制台，参数就摆在这一页，因此「恢复默认」也放在这里
        // （用户 2026-09-18：「还有很多模块的参数设置 都没有刷新 恢复默认的设置 检查一下遗漏的都加上去」）
        addCore(new CompactRow("", () -> "把本模块的设置（识别模式、方块语义调试）恢复为出厂值，需二次确认。",
                new Button("§7恢复默认", this::confirmReset)).centeredControl());

        // 使用说明：本模块是「点一下就走」的一次性工具，设置只有三项，没有说明页会显得空
        // （用户 2026-09-18 实机截图：「点进去 下面没有使用说明太空了」）
        for (String line : HELP_LINES) addFooter(new TextLine(line));
    }

    /**
     * 恢复默认：先二次确认，再把模块设置整份读回装配期快照并落盘。
     *
     * <p>与各控制台底部那颗按钮同一实现（{@code ModuleManager.resetToDefaults}），只是本模块没有控制台，
     * 所以按钮落在模块页上。不需要额外刷新：本页控件全部按 supplier 实时取值，
     * 设置一改，下一帧显示的就是默认值。</p>
     */
    private void confirmReset() {
        Minecraft client = Minecraft.getInstance();
        client.setScreen(ConfirmPanelScreen.inPlace("恢复默认设置",
            List.of("§7把「§f" + module.displayName() + "§7」的全部设置恢复为出厂值。",
                "§8确认后立刻写回配置文件，无法撤销。"),
            "§a§l恢复默认",
            () -> ModuleManager.resetToDefaults(module),
            client.screen));
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
