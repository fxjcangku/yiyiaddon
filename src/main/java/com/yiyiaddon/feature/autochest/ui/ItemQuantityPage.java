package com.yiyiaddon.feature.autochest.ui;

import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;

/**
 * 每种目标物品数量配置窗口（旧项目 {@code autochest/ui/ItemQuantityScreen}）。
 *
 * <p><b>用户交互资产（逐字）：</b>窗口标题 {@code 每种物品数量}；顶部标签
 * {@code §7为每种目标物品设置目标数量（已有部分只补差额）}；空态
 * {@code §8尚未选择目标物品，请先在上方「目标物品」中添加}；
 * 每行 {@code §a{显示名}} + 数量输入（1~2304，未配置默认 64）。</p>
 *
 * <p>列出目标物品选择器当前选中的物品，数量含义为「玩家最终想持有的该物品总数」，
 * 取物时只补差额。数据源唯一来自 {@link IdentityTargetConfig}。</p>
 */
public final class ItemQuantityPage extends PanelScreen {

    /** 顶部标签：旧项目 {@code ItemQuantityScreen.java:33} 原文 */
    private static final String HEADER = "§7为每种目标物品设置目标数量（已有部分只补差额）";

    /** 空态：旧项目 {@code ItemQuantityScreen.java:42} 原文 */
    private static final String EMPTY = "§8尚未选择目标物品，请先在上方「目标物品」中添加";

    private static final float HEADER_HEIGHT = 22f;
    private static final float HEADER_SIZE = 11f;

    private final AutoChestModule module;
    private final AutoChestSettings settings;

    public ItemQuantityPage(Screen parent, AutoChestModule module) {
        super("每种物品数量", parent);
        this.module = module;
        this.settings = module.settings();
        build();
    }

    private void build() {
        content().add(new TextLine(HEADER).height(HEADER_HEIGHT).size(HEADER_SIZE));

        List<ItemIdentity> targets = IdentityTargetConfig.selectedItems(IdentityService.shared());
        if (targets.isEmpty()) {
            content().add(new TextLine(EMPTY));
            return;
        }

        for (ItemIdentity identity : targets) {
            String key = identity.identityKey();
            content().add(new CompactRow("§a" + identity.displayName(),
                    new SettingNumberBox(1, AutoChestSettings.MAX_COUNT, 1, "%.0f",
                            () -> (double) currentQuantity(key),
                            value -> {
                                settings.setQuantity(key, (int) Math.round(value));
                                module.persistSettings();
                            })));
        }
    }

    /** 当前目标数量；未配置时按旧项目语义显示默认 64 */
    private int currentQuantity(String key) {
        int value = settings.quantityOf(key);
        return value == 0 ? AutoChestSettings.DEFAULT_COUNT : value;
    }
}
