package com.yiyiaddon.feature.autochest.ui;

import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingWidget;
import io.github.humbleui.skija.Canvas;
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
                    new QuantityControl(new SettingNumberBox(1, AutoChestSettings.MAX_COUNT, 1, "%.0f",
                            () -> (double) currentQuantity(key),
                            value -> {
                                settings.setQuantity(key, (int) Math.round(value));
                                module.persistSettings();
                            }),
                        () -> {
                            // 出厂值 = 这一项没有单独配置（数量读回默认 64）
                            settings.itemQuantities.remove(key);
                            module.persistSettings();
                        })));
        }
    }

    /** 当前目标数量；未配置时按旧项目语义显示默认 64 */
    private int currentQuantity(String key) {
        int value = settings.quantityOf(key);
        return value == 0 ? AutoChestSettings.DEFAULT_COUNT : value;
    }

    /**
     * 行内「数量框 + ↺」复合控件。
     *
     * <p>本页行构件是 {@code CompactRow}（一行只容纳一个控件），而这一页每行要同时给出数量框
     * 与行尾恢复默认，故把两者合成一个控件交出：绘制、命中、拖动全部按内部控件自己的口径下发给它，
     * 行高与两者间的间距与别处行内控件一致。</p>
     */
    private static final class QuantityControl extends SettingWidget {

        /** 数量框与 ↺ 之间的间距（与控制台行内控件间距同值） */
        private static final float GAP = 8f;

        private final SettingNumberBox box;
        private final IconButton reset;

        private QuantityControl(SettingNumberBox box, Runnable resetAction) {
            this.box = box;
            this.reset = new IconButton(ConsoleMetrics.GLYPH_RESET, resetAction);
        }

        @Override
        public float getWidth() {
            return box.getWidth() + GAP + reset.getWidth();
        }

        @Override
        public float getHeight() {
            return Math.max(box.getHeight(), reset.getHeight());
        }

        @Override
        public void update(float dt) {
            box.update(dt);
            reset.update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float alpha) {
            box.draw(canvas, x, boxY(y), alpha);
            reset.draw(canvas, x + box.getWidth() + GAP, resetY(y), alpha);
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, int button) {
            return box.onClick(mx, my, x, boxY(y), button)
                || reset.onClick(mx, my, x + box.getWidth() + GAP, resetY(y), button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y) {
            return box.onDrag(mx, my, x, boxY(y));
        }

        /** 内部控件在行内垂直居中（行高由两者中较高者决定） */
        private float boxY(float y) {
            return y + (getHeight() - box.getHeight()) / 2f;
        }

        private float resetY(float y) {
            return y + (getHeight() - reset.getHeight()) / 2f;
        }
    }
}
