package com.yiyiaddon.feature.visuals.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.visuals.EspTestModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.ButtonRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingToggle;

/**
 * ESP 渲染测试页面。
 *
 * <p>每个按钮是一个开关：点亮即注册对应绘制层，再点一次注销。开关状态由模块持有，
 * 页面重建后仍显示真实状态。每一项用不同颜色，便于在画面上分辨是哪一项在生效。</p>
 *
 * <p>主题、按钮样式、控件形态全部用本项目现有资产（第十九章第 126 条），本页没有任何自定义视觉。</p>
 */
public final class EspTestPage extends CompactModulePage implements ModulePage {

    private static final float SECTION_HEIGHT = 24f;
    private static final float STATUS_HEIGHT = 22f;
    private static final float FOOTER_HEIGHT = 34f;
    private static final float FOOTER_SIZE = 10f;

    private final EspTestModule module;

    public EspTestPage(EspTestModule module) {
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
                new SettingToggle(module::isEnabled,
                        value -> ModuleManager.setEnabled(module.id(), value))));

        addCore(new TextLine("§b§l▌ 渲染测试项 §8▸ §e点击点亮，进入世界后观察").height(SECTION_HEIGHT));

        addCore(new ButtonRow(test("玩家线框盒（3D）", EspTestModule.T_PLAYER_BOX)));
        addCore(new ButtonRow(test("玩家屏幕包围框（2D）", EspTestModule.T_PLAYER_BOX2D)));
        addCore(new ButtonRow(test("准星方块高亮", EspTestModule.T_CROSSHAIR)));
        addCore(new ButtonRow(test("实体线框 + 射线", EspTestModule.T_ENTITIES)));
        addCore(new ButtonRow(test("彩虹射线（每目标错色）", EspTestModule.T_RAINBOW)));
        addCore(new ButtonRow(test("浮空字（近大远小）", EspTestModule.T_TEXT)));
        addCore(new ButtonRow(test("渐变面 + 双色线", EspTestModule.T_GRADIENT)));

        addCore(new TextLine(this::statusText).height(STATUS_HEIGHT));
        addCore(new ButtonRow(new Button("§c清空全部测试层", module::clearTests).danger()));

        addFooter(new TextLine("§7建议顺序：线框盒 → 屏幕框 → 准星方块 → 实体+射线 → 彩虹 → 浮空字 → 渐变")
                .height(FOOTER_HEIGHT)
                .size(FOOTER_SIZE));
    }

    private Button test(String label, String id) {
        return new Button(label, () -> module.toggleTest(id))
                .selected(() -> module.isTestActive(id));
    }

    private String statusText() {
        return "§7激活测试层 §f" + module.activeCount()
                + " §8▸ §7渲染器注册总数 §f" + WorldOverlay.layerCount();
    }
}
