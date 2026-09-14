package com.yiyiaddon.feature.identity.service;

import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.feature.identity.model.IdentitySummary;
import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.model.identity.EntityIdentity;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.identity.BlockIdentifier;
import com.yiyiaddon.platform.identity.EntityIdentifier;
import com.yiyiaddon.platform.identity.ItemIdentifier;
import com.yiyiaddon.service.identity.IdentityService;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * ID 模块的业务服务：把「识别动作」与「数据落盘」串起来。
 *
 * <p>无状态：不持有任何运行状态，展示状态由 {@code IdentityModule} 持有，数据由第五阶段的
 * 识别适配层与 {@link IdentityService} 持有。识别本身不做任何写操作，只有明确传入
 * {@code save = true} 时才写入身份库。</p>
 *
 * <p>不重复实现第五阶段的识别与存储逻辑，只做调用与结果整理。</p>
 */
public final class IdentityActions {

    /** 单项字段文本的最大长度，超出截断，避免刷屏 */
    private static final int MAX_FIELD_LENGTH = 120;

    private IdentityActions() {
    }

    // ── 识别动作 ──

    /** 识别主手物品 */
    public static IdentitySummary identifyItem(boolean save) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return IdentitySummary.failed(IdentitySummary.Kind.ITEM, "玩家未加载");

        ItemStack stack = client.player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack == null || stack.isEmpty()) {
            return IdentitySummary.failed(IdentitySummary.Kind.ITEM, "没有可识别物品：主手和副手都是空的");
        }

        ItemIdentity identity = ItemIdentifier.identifyItem(stack);
        if (identity == null) {
            return IdentitySummary.failed(IdentitySummary.Kind.ITEM, "无法解析该物品的稳定身份");
        }

        String fileName = save ? IdentityService.shared().addItem(identity) : null;
        List<IdentitySummary.Row> rows = new ArrayList<>();
        rows.add(new IdentitySummary.Row("名称", identity.displayName()));
        rows.add(new IdentitySummary.Row("物品 ID", identity.itemId()));
        rows.add(new IdentitySummary.Row("物品类型", identity.typeName()));
        if (identity.customLogicId() != null) {
            rows.add(new IdentitySummary.Row("自定义身份", identity.customLogicId()));
        }
        if (identity.itemModel() != null) {
            rows.add(new IdentitySummary.Row("资源模型", identity.itemModel()));
        }
        if (identity.isRenamed()) {
            rows.add(new IdentitySummary.Row("自定义名称", identity.customName()));
        }
        if (identity.itemName() != null) {
            rows.add(new IdentitySummary.Row("原始名称", identity.itemName()));
        }
        if (identity.hasEnchantments()) {
            rows.add(new IdentitySummary.Row("附魔", enchantmentText(identity.enchantments())));
        }
        if (identity.waterValue() != null) {
            rows.add(new IdentitySummary.Row("当前水量", String.valueOf(identity.waterValue())));
        }
        rows.add(new IdentitySummary.Row("数量", String.valueOf(identity.quantity())));
        rows.add(new IdentitySummary.Row("身份键", identity.identityKey()));

        return IdentitySummary.ok(IdentitySummary.Kind.ITEM, identity.displayName(), rows, fileName);
    }

    /** 识别准星命中的方块 */
    public static IdentitySummary identifyBlock(boolean save) {
        BlockIdentity identity = BlockIdentifier.identify();
        if (identity == null) {
            Minecraft client = Minecraft.getInstance();
            String reason = client.player == null ? "玩家未加载" : "准星当前没有指向有效方块";
            return IdentitySummary.failed(IdentitySummary.Kind.BLOCK, reason);
        }

        String fileName = save ? IdentityService.shared().addBlock(identity) : null;
        List<IdentitySummary.Row> rows = new ArrayList<>();
        rows.add(new IdentitySummary.Row("名称", identity.displayName()));
        rows.add(new IdentitySummary.Row("方块 ID", identity.blockId()));
        rows.add(new IdentitySummary.Row("方块状态", identity.blockStateDisplay()));
        rows.add(new IdentitySummary.Row("坐标", identity.x() + ", " + identity.y() + ", " + identity.z()));
        rows.add(new IdentitySummary.Row("维度", identity.dimension()));
        rows.add(new IdentitySummary.Row("服务器", identity.server()));
        if (identity.blockEntityTypeId() != null) {
            rows.add(new IdentitySummary.Row("方块实体类型", identity.blockEntityTypeId()));
        }
        rows.add(new IdentitySummary.Row("解析状态", identity.semanticCertainty() == null ? "未解析" : identity.semanticCertainty()));
        if (identity.semanticIdentity() != null) {
            rows.add(new IdentitySummary.Row("自定义身份", identity.semanticIdentity()));
        }
        if (identity.semanticModel() != null) {
            rows.add(new IdentitySummary.Row("资源模型", identity.semanticModel()));
        }
        rows.add(new IdentitySummary.Row("身份键", identity.identityKey()));

        return IdentitySummary.ok(IdentitySummary.Kind.BLOCK, identity.displayName(), rows, fileName);
    }

    /** 识别准星命中的实体 */
    public static IdentitySummary identifyEntity(boolean save) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return IdentitySummary.failed(IdentitySummary.Kind.ENTITY, "玩家未加载");

        Entity target = client.crosshairPickEntity;
        if (target == null) return IdentitySummary.failed(IdentitySummary.Kind.ENTITY, "当前准星未指向可识别实体");

        EntityIdentity identity = EntityIdentifier.identifyEntity(target);
        if (identity == null) return IdentitySummary.failed(IdentitySummary.Kind.ENTITY, "无法解析该实体的稳定身份");

        String fileName = save ? IdentityService.shared().addEntity(identity) : null;
        List<IdentitySummary.Row> rows = new ArrayList<>();
        rows.add(new IdentitySummary.Row("名称", identity.displayName()));
        rows.add(new IdentitySummary.Row("实体 ID", identity.entityId()));
        rows.add(new IdentitySummary.Row("原始名称", identity.baseName()));
        if (identity.isNamed()) {
            rows.add(new IdentitySummary.Row("自定义名称", identity.customName()));
        }
        if (identity.uuid() != null) {
            rows.add(new IdentitySummary.Row("UUID", identity.uuid()));
        }
        rows.add(new IdentitySummary.Row("坐标", identity.blockX() + ", " + identity.blockY() + ", " + identity.blockZ()));
        rows.add(new IdentitySummary.Row("身份键", identity.identityKey()));

        return IdentitySummary.ok(IdentitySummary.Kind.ENTITY, identity.displayName(), rows, fileName);
    }

    // ── 身份数据统计 ──

    public static int itemCount() {
        return IdentityService.shared().itemCount();
    }

    public static int entityCount() {
        return IdentityService.shared().entityCount();
    }

    public static int blockCount() {
        return IdentityService.shared().blockCount();
    }

    public static int itemSnapshotCount() {
        return IdentityService.shared().itemSnapshotCount();
    }

    public static int blockSnapshotCount() {
        return IdentityService.shared().blockSnapshotCount();
    }

    /** 清理已失效的选中目标，返回移除数量 */
    public static int pruneInvalidTargets() {
        return IdentityTargetConfig.pruneInvalid(IdentityService.shared());
    }

    // ── 内部工具 ──

    private static String enchantmentText(List<ItemIdentity.EnchantmentEntry> enchantments) {
        List<String> names = new ArrayList<>();
        for (ItemIdentity.EnchantmentEntry entry : enchantments) {
            String name = entry.displayName() == null || entry.displayName().isBlank()
                    ? entry.chineseName() : entry.displayName();
            names.add(name + " " + entry.level());
        }
        return truncate(String.join("、", names));
    }

    private static String truncate(String text) {
        if (text == null) return "";
        return text.length() <= MAX_FIELD_LENGTH ? text : text.substring(0, MAX_FIELD_LENGTH) + "...";
    }
}
