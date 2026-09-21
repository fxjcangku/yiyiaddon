package com.yiyiaddon.feature.autochest.ui.console;

import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.ui.AutoChestConsoleScreen;
import com.yiyiaddon.feature.autochest.ui.ItemQuantityPage;
import com.yiyiaddon.model.autochest.WithdrawMode;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 自动箱子控制台「取物」页：{@code 目标物品} 与 {@code 取物} 两个分组。
 *
 * <p>逐字搬自旧项目配置页的 {@code 目标物品}（{@code 目标物品} 选择按钮 + 计数 + 重置图标）与
 * {@code 取物}（{@code 取物模式} 分段、{@code 每种物品数量} 配置按钮 + 计数 + 重置图标、
 * {@code 动作延迟}）：设置名、描述、默认值与取值域一字未改。组名用页内小节标题承载。</p>
 *
 * <p>可见性联动照旧：{@code 目标物品} 整组仅在取物模式非「全部拿空」时出现；{@code 每种物品数量}
 * 仅在「按目标数量取」时出现——整页可重建，条件不满足的行压根不加入堆叠，取物模式改动后重建本页。</p>
 *
 * <p><b>目标物品的数据源唯一来自 ID 配置管理</b>（{@link IdentityTargetConfig}），本页只做选择器
 * 入口与计数展示，不建第二份物品库（旧项目同一口径）。</p>
 */
public final class AutoChestWithdrawPage {

    private static final String DESC_WITHDRAW_MODE =
        "按目标数量取：每种目标物品单独配置数量；目标物品拿空：只拿空目标列表物品；全部拿空：忽略目标列表取走所有合法物品。";
    private static final String DESC_TARGET_ITEMS = "选择 AutoChest 要取的目标物品（数据源唯一来自 ID 配置管理）。";
    private static final String DESC_QUANTITY = "按目标数量取模式下，为每种目标物品单独配置目标数量。";
    private static final String DESC_ACTION_DELAY = "两次槽位操作之间的 Tick 间隔。";

    private static final List<String> WITHDRAW_MODE_LABELS = List.of(
        WithdrawMode.TARGET_COUNT.displayName(), WithdrawMode.TARGET_EMPTY.displayName(),
        WithdrawMode.TAKE_ALL.displayName());

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final AutoChestSettings DEFAULTS = new AutoChestSettings();

    private final AutoChestConsoleScreen owner;
    private final AutoChestModule module;

    public AutoChestWithdrawPage(AutoChestConsoleScreen owner, AutoChestModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        AutoChestSettings settings = module.settings();

        // ── 目标物品（仅取物模式非「全部拿空」时出现） ──
        if (needsTargetItems()) {
            stack.add(section("目标物品"));
            stack.add(new ConsoleRow(owner, () -> "目标物品", DESC_TARGET_ITEMS, null,
                List.of(new Ctl(new Button("选择目标物品", this::openTargetSelector)))));

            stack.add(new ConsoleRow(owner, this::targetItemsCountText, null, null,
                List.of(new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, () -> {
                    // 清空前的真实条数：reset() 一执行集合就空了，之后再数只能得到 0
                    int cleared = IdentityTargetConfig.selectedItemKeys().size();
                    IdentityTargetConfig.reset();
                    owner.reload();
                    // 原来这行点下去毫无反馈；面板开着时 HUD 被藏起来，改成面板内顶部弹窗
                    SelectionReceipt.cleared(cleared);
                }), "清空本行已选目标物品"))));
        }

        // ── 取物 ──
        stack.add(section("取物"));
        stack.add(new ConsoleRow(owner, () -> "取物模式", DESC_WITHDRAW_MODE, null,
            List.of(new Ctl(new SettingSegmented(WITHDRAW_MODE_LABELS,
                () -> settings.withdrawMode.ordinal(), pickWithdrawMode())),
                ConsoleWidgets.resetCtl(() -> {
                    settings.withdrawMode = DEFAULTS.withdrawMode;
                    module.persistSettings();
                    owner.reload();
                }, "取物模式"))));

        if (settings.withdrawMode == WithdrawMode.TARGET_COUNT) {
            stack.add(new ConsoleRow(owner, () -> "每种物品数量", DESC_QUANTITY, null,
                List.of(new Ctl(new Button("配置每种物品数量",
                    () -> openSubScreen(new ItemQuantityPage(owner, module)))))));

            stack.add(new ConsoleRow(owner, this::quantityCountText, null, null,
                List.of(new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, () -> {
                    // 同上：清空前的真实条数要先取，resetQuantities() 之后计数已归零
                    int cleared = settings.configuredQuantityCount();
                    settings.resetQuantities();
                    module.persistSettings();
                    owner.reload();
                    SelectionReceipt.cleared(cleared);
                }), "清空全部数量配置"))));
        }

        stack.add(new ConsoleRow(owner, () -> "动作延迟", DESC_ACTION_DELAY, null,
            List.of(new Ctl(intBox(1, Integer.MAX_VALUE, () -> settings.actionDelay,
                value -> {
                    settings.actionDelay = value;
                })),
                ConsoleWidgets.resetCtl(() -> {
                    settings.actionDelay = DEFAULTS.actionDelay;
                    module.persistSettings();
                    owner.reload();
                }, "动作延迟"))));
    }

    /** 分区标题（与星露谷 / 挖矿控制台各页同一套样式） */
    private Note section(String title) {
        return new Note(owner, "§7§l" + title, null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE);
    }

    /** 是否需要目标物品：取物模式非「全部拿空」（旧项目 {@code visible} 同一判据） */
    private boolean needsTargetItems() {
        return module.settings().withdrawMode != WithdrawMode.TAKE_ALL;
    }

    /** 取物模式分段：改动落盘后整页重建（改变目标物品组与每种物品数量行的可见性） */
    private Consumer<Integer> pickWithdrawMode() {
        return index -> {
            WithdrawMode[] values = WithdrawMode.values();
            if (index < 0 || index >= values.length) return;
            module.settings().withdrawMode = values[index];
            module.persistSettings();
            owner.reload();
        };
    }

    /**
     * 打开「目标物品」选择器：数据源唯一来自 ID 配置管理，改动即时生效（无确定按钮）。
     *
     * <p>排序与分组照旧项目：先原版物品、后自定义物品，各自带分组标题。</p>
     */
    private void openTargetSelector() {
        Minecraft client = owner.client();
        if (client == null) return;

        List<SelectorScreen.Entry> entries = new ArrayList<>();
        List<ItemIdentity> custom = new ArrayList<>();
        for (ItemIdentity identity : IdentityService.shared().allItems()) {
            if (identity.isVanilla()) entries.add(new ItemEntry(identity, true));
            else custom.add(identity);
        }
        for (ItemIdentity identity : custom) entries.add(new ItemEntry(identity, false));

        client.gui.setScreen(new SelectorScreen("目标物品", client.gui.screen(), entries,
            () -> new ArrayList<>(IdentityTargetConfig.selectedItemKeys()),
            key -> IdentityTargetConfig.setItemSelected(key, true),
            key -> IdentityTargetConfig.setItemSelected(key, false)));
    }

    /** 打开子界面（每种物品数量）；关闭后回到控制台 */
    private void openSubScreen(Screen screen) {
        if (owner.client() == null || screen == null) return;
        owner.client().gui.setScreen(screen);
    }

    /** 目标物品计数（与旧项目逐字同源）：{@code 未选择目标（共 N 项）} / {@code 已选 X / N 项} */
    private String targetItemsCountText() {
        int total = IdentityService.shared().itemCount();
        int selected = IdentityTargetConfig.selectedItemKeys().size();
        if (selected == 0) return "未选择目标（共 " + total + " 项）";
        return "已选 " + selected + " / " + total + " 项";
    }

    /** 每种物品数量计数（与旧项目逐字同源）：{@code 已配置 X / Y 项} */
    private String quantityCountText() {
        return "已配置 " + module.settings().configuredQuantityCount() + " / "
            + IdentityTargetConfig.selectedItemKeys().size() + " 项";
    }

    /** 整数设置框：步进 1，改动落盘（取值域与旧项目一致） */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, Consumer<Integer> setter) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                module.persistSettings();
            });
    }

    /** 目标选择器条目：原版物品能取到贴图，自定义物品回退纯文字（与旧项目配置页同一实现） */
    private static final class ItemEntry implements SelectorScreen.Entry {

        private final ItemIdentity identity;
        private final boolean vanilla;

        private ItemEntry(ItemIdentity identity, boolean vanilla) {
            this.identity = identity;
            this.vanilla = vanilla;
        }

        @Override
        public String key() {
            return identity.identityKey();
        }

        @Override
        public String title() {
            return "§a" + identity.displayName();
        }

        @Override
        public String group() {
            return vanilla ? "§a§l▌ 原版物品" : "§d§l▌ 自定义物品";
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            // 原版 / 自定义都按「真实载体物品」取贴图：自定义物品的底层仍是某个注册表物品
            // （如沙子、纸），取不到才回退纯文字，不再按原版/自定义一刀切不画。
            Item item = itemById(identity.itemId());
            return item != null && ItemIconCache.getInstance().draw(canvas, item.getDefaultInstance(), x, y, size);
        }

        /** 与 drawIcon 同一份图标：按物品登记 ID 取到的物品（取不到返回 null）。 */
        @Override
        public ItemStack iconStack() {
            Item item = itemById(identity.itemId());
            return item == null ? null : item.getDefaultInstance();
        }

        private static Item itemById(String itemId) {
            Identifier id = Identifier.tryParse(itemId);
            if (id == null) return null;
            Item item = BuiltInRegistries.ITEM.getValue(id);
            return item == null || item == Items.AIR ? null : item;
        }
    }
}
