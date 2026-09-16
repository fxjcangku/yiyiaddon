package com.yiyiaddon.feature.combat.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.combat.KillAuraModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.Minecraft;

/**
 * 杀戮光环模块页：状态条 + 一个「打开控制台」入口的薄壳页（形态照星露谷
 * {@code StardewResourcePanelPage} 与自动挖矿 {@code AutoMinerPage}）。
 *
 * <p>模块的 26 项设置不在这里平铺：全部由整屏控制台 {@link KillAuraConsoleScreen} 按用途分页承载
 * （概览 / 常规 / 目标 / 时机），本页只负责入口与模块开关。</p>
 *
 * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次
 * （{@code ModuleEntries.of:35}），因此构造函数里<b>不碰任何游戏注册表</b>——不构造
 * {@code ItemStack}、不取物品 / 实体类型的显示名、不建候选表；页面内容推迟到
 * {@link #createPage(ModuleEntry)}（用户真正打开页面时）才构建。</p>
 */
public final class KillAuraPage extends CompactModulePage implements ModulePage {

    /** 控制台入口按钮（逐字照星露谷模块页） */
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    /** 入口行的说明（本任务新造文案） */
    private static final String CONSOLE_HINT = "按用途分页：概览 / 常规 / 目标 / 时机";

    private final KillAuraModule module;

    /** 页面内容是否已构建（见类注释的时序约束） */
    private boolean built;

    public KillAuraPage(KillAuraModule module) {
        this.module = module;
    }

    @Override
    public BasePage createPage(ModuleEntry entry) {
        if (!built) {
            built = true;
            build();
        }
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

    // ── 构建 ──

    private void build() {
        setHeader(new ModuleStatusBar(
            () -> module.isEnabled() ? "运行中" : "未启用",
            module::isEnabled,
            new KeybindBadge(module.keybindId()),
            new SettingToggle(module::isEnabled,
                value -> ModuleManager.setEnabled(module.id(), value))));

        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());
    }

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new KillAuraConsoleScreen(client.screen, module));
    }
}
