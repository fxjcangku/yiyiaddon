package com.yiyiaddon.feature.identity.ui;

import com.google.gson.GsonBuilder;
import com.yiyiaddon.feature.identity.IdIdentifyModule;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.screen.PanelScreen;
import net.minecraft.client.gui.screens.Screen;

/**
 * 物品识别结果窗口（旧项目 {@code IdResultScreen}）。
 *
 * <p>由「聊天复制/显示」识别模式弹出：完整展示一次识别产出的物品身份，并提供复制与保存操作。
 * 字段行、附魔行、五个按钮的文字与先后顺序全部沿用旧项目；每次操作的反馈走聊天栏，前缀为模块名
 * {@code ID识别}。</p>
 *
 * <p>作物归属字段（所属作物 / cropKey / 类型）依赖星露谷资源索引，按既定口径不迁移，故本窗口
 * 不出现该组字段。</p>
 */
public final class IdResultScreen extends PanelScreen {

    /** 窗口标题：旧项目原文。 */
    public static final String WINDOW_TITLE = "识别结果";

    private static final String MODULE = IdIdentifyModule.MESSAGE_MODULE;

    private final ItemIdentity identity;

    public IdResultScreen(ItemIdentity identity, Screen parent) {
        super(WINDOW_TITLE, parent);
        exitToGame();
        this.identity = identity;
        buildContent();
    }

    private void buildContent() {
        addSectionTitle("§b§l▌ 识别结果");
        addField("物品ID", identity.itemId());
        addField("显示名称", identity.displayName());
        addField("原始名称", identity.baseName());
        addField("物品类型", identity.typeName());
        addField("数量", String.valueOf(identity.quantity()));
        if (identity.customName() != null) {
            addField("自定义名称", identity.customName());
        }
        if (identity.customLogicId() != null) {
            addField("自定义逻辑ID", identity.customLogicId());
        }
        if (identity.waterValue() != null) {
            addField("当前水量", String.valueOf(identity.waterValue()));
        }
        addField("数据版本", String.valueOf(identity.dataVersion()));
        addField("数据组件", identity.dataComponents() == null ? "无" : "有（见 JSON 文件）");

        if (identity.hasEnchantments()) {
            addSectionTitle("§7附魔");
            for (ItemIdentity.EnchantmentEntry entry : identity.enchantments()) {
                content().add(new com.yiyiaddon.ui.component.TextLine("  §8▸ §a" + entry.displayName()
                        + " §8▸ §f" + entry.id() + " §8▸ §e等级 " + entry.level()));
            }
        }

        addGap();
        addButton("复制 Item ID", this::copyItemId);
        addButton("复制完整信息", this::copyFullJson);
        addButton("保存 ID", () -> saveToLibrary());
        addButton("另存快照", this::saveSnapshot);
        addButton("添加到 ID 配置", this::saveAndClose);
    }

    // ── 操作（反馈走聊天栏，文案照旧项目） ──

    private void copyItemId() {
        copyToClipboard(identity.itemId());
        feedback(MODULE, "§a§l✓ 已复制 Item ID");
    }

    private void copyFullJson() {
        copyToClipboard(new GsonBuilder().setPrettyPrinting().create().toJson(identity.toJsonObject()));
        feedback(MODULE, "§a§l✓ 已复制完整识别信息");
    }

    /** 保存 ID：写入身份库，窗口保持打开供继续操作。 */
    private boolean saveToLibrary() {
        String fileName = IdentityService.shared().addItem(identity);
        if (fileName == null) {
            feedback(MODULE, "§e该物品已在 ID 配置中");
            return false;
        }
        feedback(MODULE, "§a§l✓ 已添加到 ID 配置 §8▸ §a§l" + identity.displayName() + " §8▸ §f" + fileName);
        return true;
    }

    /** 另存快照：即使状态相同也保存为新文件。 */
    private void saveSnapshot() {
        String fileName = IdentityService.shared().addItemSnapshot(identity, true);
        if (fileName == null) {
            feedback(MODULE, "§e快照保存失败");
            return;
        }
        feedback(MODULE, "§a§l✓ 已另存快照 §8▸ §a§l" + identity.displayName() + " §8▸ §f" + fileName);
    }

    /** 添加到 ID 配置：保存成功后关闭窗口。 */
    private void saveAndClose() {
        if (saveToLibrary()) requestClose();
    }
}
