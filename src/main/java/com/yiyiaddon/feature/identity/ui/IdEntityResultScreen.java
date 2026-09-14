package com.yiyiaddon.feature.identity.ui;

import com.google.gson.GsonBuilder;
import com.yiyiaddon.feature.identity.IdIdentifyModule;
import com.yiyiaddon.model.identity.EntityIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.screen.PanelScreen;
import net.minecraft.client.gui.screens.Screen;

/**
 * 实体识别结果窗口（旧项目 {@code EntityResultScreen}）。
 *
 * <p>供独立指令 {@code .id 实体} 使用：完整展示一次识别产出的实体身份，并提供复制与保存操作。
 * 字段、按钮文字与反馈文案全部沿用旧项目。</p>
 */
public final class IdEntityResultScreen extends PanelScreen {

    /** 窗口标题：旧项目原文。 */
    public static final String WINDOW_TITLE = "实体识别结果";

    private static final String MODULE = IdIdentifyModule.MESSAGE_MODULE;

    private final EntityIdentity identity;

    public IdEntityResultScreen(EntityIdentity identity, Screen parent) {
        super(WINDOW_TITLE, parent);
        exitToGame();
        this.identity = identity;
        buildContent();
    }

    private void buildContent() {
        addSectionTitle("§b§l▌ 实体识别结果");
        addField("实体ID", identity.entityId());
        addField("显示名称", identity.displayName());
        addField("原始名称", identity.baseName());
        if (identity.customName() != null) {
            addField("自定义名称", identity.customName());
        }
        if (identity.uuid() != null) {
            addField("UUID", identity.uuid());
        }
        addField("坐标", identity.blockX() + ", " + identity.blockY() + ", " + identity.blockZ());
        addField("数据版本", String.valueOf(identity.dataVersion()));

        addGap();
        addButton("复制实体ID", this::copyEntityId);
        addButton("复制完整信息", this::copyFullJson);
        addButton("保存实体ID", () -> save());
        addButton("保存并关闭", this::saveAndClose);
    }

    private void copyEntityId() {
        copyToClipboard(identity.entityId());
        feedback(MODULE, "§a§l✓ 已复制实体ID");
    }

    private void copyFullJson() {
        copyToClipboard(new GsonBuilder().setPrettyPrinting().create().toJson(identity.toJsonObject()));
        feedback(MODULE, "§a§l✓ 已复制完整识别信息");
    }

    /** 写入实体 ID 配置；窗口保持打开供继续操作。 */
    private boolean save() {
        String fileName = IdentityService.shared().addEntity(identity);
        if (fileName == null) {
            feedback(MODULE, "§e该实体已在实体 ID 配置中");
            return false;
        }
        feedback(MODULE, "§a§l✓ 已保存实体 §8▸ §a§l" + identity.displayName() + " §8▸ §f" + fileName);
        return true;
    }

    private void saveAndClose() {
        if (save()) requestClose();
    }
}
