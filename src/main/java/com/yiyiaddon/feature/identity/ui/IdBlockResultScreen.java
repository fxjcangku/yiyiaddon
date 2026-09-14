package com.yiyiaddon.feature.identity.ui;

import com.google.gson.GsonBuilder;
import com.yiyiaddon.feature.identity.IdIdentifyModule;
import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.screen.PanelScreen;
import net.minecraft.client.gui.screens.Screen;

/**
 * 方块识别结果窗口（旧项目 {@code BlockResultScreen}）。
 *
 * <p>供「准星方块识别」模式使用：完整展示一次识别产出的方块身份（含资源包语义解析结果），
 * 并提供复制与保存操作。字段、按钮文字与反馈文案全部沿用旧项目。</p>
 *
 * <p>旧项目另有「作物真实状态」分区，数据来自星露谷判定入口，按既定口径不迁移，故本窗口不出现
 * 该分区。</p>
 */
public final class IdBlockResultScreen extends PanelScreen {

    /** 窗口标题：旧项目原文。 */
    public static final String WINDOW_TITLE = "方块识别结果";

    private static final String MODULE = IdIdentifyModule.MESSAGE_MODULE;

    private final BlockIdentity identity;

    public IdBlockResultScreen(BlockIdentity identity, Screen parent) {
        super(WINDOW_TITLE, parent);
        exitToGame();
        this.identity = identity;
        buildContent();
    }

    private void buildContent() {
        addSectionTitle("§b§l▌ 方块识别结果");
        addField("方块ID", identity.blockId());
        addField("方块状态", identity.blockStateDisplay());
        if (identity.blockEntityTypeId() != null) {
            addField("方块实体类型", identity.blockEntityTypeId());
        }
        addField("维度", WorldIdentity.dimensionDisplayName(identity.dimension()));
        addField("坐标", identity.x() + ", " + identity.y() + ", " + identity.z());
        addField("数据版本", String.valueOf(identity.dataVersion()));

        if (identity.semanticModel() != null) {
            addField("资源模型", identity.semanticModel());
        }
        if (identity.semanticIdentity() != null) {
            addField("自定义身份", identity.semanticIdentity());
        }
        if (identity.semanticName() != null) {
            addField("自定义名称", identity.semanticName());
        }
        if (identity.semanticSource() != null) {
            addField("解析来源", identity.semanticSource());
        }
        if (identity.semanticCertainty() != null) {
            addField("解析状态", identity.semanticCertainty());
        }
        if (identity.semanticReason() != null) {
            addField("解析说明", identity.semanticReason());
        }

        addGap();
        addButton("复制方块ID", this::copyBlockId);
        addButton("复制完整信息", this::copyFullJson);
        addButton("保存方块", () -> save());
        addButton("另存状态快照", this::saveSnapshot);
        addButton("保存并关闭", this::saveAndClose);
    }

    private void copyBlockId() {
        copyToClipboard(identity.blockId());
        feedback(MODULE, "§a§l✓ 已复制方块ID");
    }

    private void copyFullJson() {
        copyToClipboard(new GsonBuilder().setPrettyPrinting().create().toJson(identity.toJsonObject()));
        feedback(MODULE, "§a§l✓ 已复制完整方块信息");
    }

    /** 写入方块记录；窗口保持打开供继续操作。 */
    private boolean save() {
        String fileName = IdentityService.shared().addBlock(identity);
        if (fileName == null) {
            feedback(MODULE, "§e该方块已在方块记录中");
            return false;
        }
        feedback(MODULE, "§a§l✓ 已保存方块 §8▸ §a§l" + identity.blockId() + " §8▸ §f" + fileName);
        return true;
    }

    /** 另存状态快照：即使状态相同也保存，不覆盖已有文件。 */
    private void saveSnapshot() {
        String fileName = IdentityService.shared().addBlockSnapshot(identity, true);
        if (fileName == null) {
            feedback(MODULE, "§c快照保存失败");
            return;
        }
        feedback(MODULE, "§a§l✓ 已另存方块状态快照 §8▸ §f" + fileName);
    }

    private void saveAndClose() {
        if (save()) requestClose();
    }
}
