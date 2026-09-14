package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.feature.identity.IdConfigModule;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.identity.ItemIdentifier;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.ListRow;
import com.yiyiaddon.ui.component.ListSection;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingTextBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * 手动添加物品窗口（旧项目 {@code IdAddScreen}）。
 *
 * <p>输入可以是物品 ID 或中文名称；解析顺序与旧项目一致：直接物品 ID → 已保存配置的中文名精确匹配
 * → 注册表原版中文名精确匹配（多命中展示候选列表，不随机选择）。可选项「自定义名称 / 自定义逻辑ID /
 * 物品模型」用于在不手持实物的情况下构造改名物品或自定义物品。</p>
 *
 * <p>候选列表用本项目列表行呈现（带物品图标），点击一行即选中。</p>
 */
public final class IdAddScreen extends PanelScreen {

    /** 窗口标题：旧项目原文。 */
    public static final String WINDOW_TITLE = "添加物品";

    private static final String MODULE = IdConfigModule.MESSAGE_MODULE;
    private static final float INPUT_WIDTH = 420f;
    private static final int INPUT_MAX_LENGTH = 128;

    private String inputText = "";
    private String customNameText = "";
    private String customLogicIdText = "";
    private String itemModelText = "";

    private final ListSection candidateSection = new ListSection();

    public IdAddScreen(Screen parent) {
        super(WINDOW_TITLE, parent);
        exitToGame();
        buildContent();
    }

    private void buildContent() {
        content().add(new TextLine("§7输入物品 ID 或中文名称（如 §f§eminecraft:diamond§7 或 §f§e钻石§7）"));
        content().add(inputRow(() -> inputText, value -> inputText = value));

        content().add(new TextLine("§7自定义名称（可选，填写后作为改名物品的 custom_name）"));
        content().add(inputRow(() -> customNameText, value -> customNameText = value));

        content().add(new TextLine("§7自定义逻辑ID（可选，如 §f§ecustomcrops:dry_pot_1§7，填写后识别为自定义物品）"));
        content().add(inputRow(() -> customLogicIdText, value -> customLogicIdText = value));

        content().add(new TextLine("§7物品模型（可选，如 §f§ecustomcrops:dry_pot_1§7）"));
        content().add(inputRow(() -> itemModelText, value -> itemModelText = value));

        addGap();
        addButtons(
                new Button("§a查找并添加", this::resolveAndAdd),
                new Button("§c取消", this::requestClose));

        addGap();
        content().add(candidateSection);
    }

    /** 输入行：无标签，输入框左对齐。 */
    private CompactRow inputRow(java.util.function.Supplier<String> supplier,
                                java.util.function.Consumer<String> consumer) {
        return new CompactRow("", new SettingTextBox(supplier, consumer, INPUT_MAX_LENGTH).width(INPUT_WIDTH));
    }

    // ── 解析与添加（与旧项目同一顺序） ──

    private void resolveAndAdd() {
        String raw = inputText.trim();
        if (raw.isEmpty()) {
            feedback(MODULE, "§c✗ 请输入物品 ID 或中文名称");
            return;
        }

        Item byRawId = itemOf(raw);
        if (byRawId != null) {
            buildAndAdd(byRawId);
            return;
        }

        for (ItemIdentity saved : IdentityService.shared().allItems()) {
            if (saved.displayName().equals(raw)) {
                addIdentity(saved);
                return;
            }
        }

        List<Item> matched = ItemIdentifier.findVanillaByChineseName(raw);
        if (matched.size() == 1) {
            buildAndAdd(matched.get(0));
            return;
        }
        if (matched.size() > 1) {
            showCandidates(matched);
            return;
        }
        feedback(MODULE, "§c✗ 未找到对应物品 §8▸ 请先手持识别，或填写真实物品 ID（如 minecraft:diamond）");
    }

    /** 按可选项（自定义名称 / 自定义逻辑ID / 物品模型）构造并添加身份。 */
    private void buildAndAdd(Item item) {
        if (item == null) {
            feedback(MODULE, "§c✗ 未找到对应物品 §8▸ 请先手持识别，或填写真实物品 ID");
            return;
        }
        String customName = customNameText.trim();
        String customLogicId = customLogicIdText.trim();
        String itemModel = itemModelText.trim();

        ItemIdentity identity;
        if (!customLogicId.isEmpty() || !itemModel.isEmpty()) {
            String name = customName.isEmpty()
                    ? item.getDefaultInstance().getHoverName().getString()
                    : customName;
            identity = ItemIdentifier.fromCustomSpec(item, name, customLogicId, itemModel);
        } else if (!customName.isEmpty()) {
            identity = ItemIdentifier.fromItemAndCustomName(item, customName);
        } else {
            identity = ItemIdentifier.fromItemId(BuiltInRegistries.ITEM.getKey(item).toString());
        }
        addIdentity(identity);
    }

    /** 展示多个中文名命中候选，点击一行选择其一。 */
    private void showCandidates(List<Item> items) {
        List<CompactElement> rows = new ArrayList<>();
        rows.add(new TextLine("§e§l▌ 找到多个匹配，请选择：").height(24f).size(12f).bold(true));
        for (Item item : items) {
            String itemId = BuiltInRegistries.ITEM.getKey(item).toString();
            String name = item.getDefaultInstance().getHoverName().getString();
            rows.add(new ListRow("§a" + name)
                    .icon((canvas, x, y, size) -> ItemIconCache.getInstance()
                            .draw(canvas, item.getDefaultInstance(), x, y, size))
                    .detail(() -> "§8▸ §7" + itemId)
                    .onActivate(() -> buildAndAdd(item)));
        }
        candidateSection.setRows(rows);
    }

    /** 写入 ID 配置：成功关闭窗口，重复保持打开。 */
    private void addIdentity(ItemIdentity identity) {
        if (identity == null) {
            feedback(MODULE, "§c✗ 未找到对应物品 §8▸ 请先手持识别，或填写真实物品 ID");
            return;
        }
        String fileName = IdentityService.shared().addItem(identity);
        if (fileName == null) {
            feedback(MODULE, "§e该物品已在 ID 配置中 §8▸ " + identity.displayName());
            return;
        }
        feedback(MODULE, "§a§l✓ 已添加物品 §8▸ §a§l" + identity.displayName()
                + " §8▸ §f" + identity.itemId()
                + (identity.isRenamed() ? " §8▸ §d自定义" : ""));
        requestClose();
    }

    /** 按完整物品 ID 解析；非法或不存在返回 null。 */
    private static Item itemOf(String itemId) {
        Identifier id = Identifier.tryParse(itemId);
        if (id == null) return null;
        Item item = BuiltInRegistries.ITEM.getValue(id);
        return item == null || item == Items.AIR ? null : item;
    }
}
