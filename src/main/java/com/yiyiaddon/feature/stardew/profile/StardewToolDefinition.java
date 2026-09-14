package com.yiyiaddon.feature.stardew.profile;

import com.yiyiaddon.feature.stardew.selector.StardewPreview;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

/**
 * 星露谷「逻辑农场对象」定义（资源层第二层产物）。
 *
 * <p>这是「资源发现 → 语义解析 → 逻辑对象聚合 → 去重 → 六类选择器」里的核心抽象：扫描到的
 * 每一个 Item Model / Block Model 只是 Raw Resource Entry，不能直接变成用户选择项；必须先按
 * 语义家族 + 数字索引聚合成一个 {@link StardewToolDefinition}，再作为 Selector Entry 展示。</p>
 *
 * <p>例如种植盆不会出现 {@code dry_pot_1} / {@code wet_pot_1} 两个选择项，而是聚合成一个
 * {@link PotDefinition}（普通种植盆），内部同时绑定 item 身份 + dry 世界模型 + wet 世界模型。</p>
 *
 * <p>六类选择器持久化的是 {@link #key()}（稳定逻辑键），不持久化临时列表下标或仅中文名。</p>
 */
public sealed interface StardewToolDefinition
    permits PotDefinition, WateringCanDefinition, SprinklerDefinition, SimpleToolDefinition {

    /** 所属六类选择器类别 */
    StardewSelectorCategory category();

    /** 稳定逻辑键（跨资源重载稳定，选择器持久化此值） */
    String key();

    /** 中文显示名（绑定身份优先，其次资源语言文件，最后文档化兜底） */
    String displayName();

    /** 用于预览与 item_model 匹配的物品模型引用（可空） */
    String itemModel();

    /** 已绑定的真实 ItemIdentity 身份键（可空，未 .id 时为 null） */
    String identityKey();

    /** 证据等级 */
    RuleEvidence evidence();

    /** 数据来源说明（用于状态页与排查） */
    String source();

    /** 是否已绑定真实 ItemIdentity（可用于库存精确匹配） */
    default boolean isBound() {
        return identityKey() != null && !identityKey().isBlank();
    }

    /** 是否可信到可以安全执行破坏性动作 */
    default boolean verified() {
        return evidence() == RuleEvidence.VERIFIED || evidence() == RuleEvidence.DOCUMENTED;
    }

    /** 面向玩家的状态标签 */
    default String statusLabel() {
        return switch (evidence()) {
            case VERIFIED -> "已确认";
            case DOCUMENTED -> "攻略记载";
            case CANDIDATE -> "候选推导";
            case UNKNOWN -> "未知";
        };
    }

    /** 用 item_model 组件精确匹配一个真实 ItemStack（不依赖 .id 手工入库） */
    default boolean matchesByModel(ItemStack stack) {
        if (stack == null || stack.isEmpty() || itemModel() == null) return false;
        Identifier model = stack.get(DataComponents.ITEM_MODEL);
        return model != null && model.toString().equals(itemModel());
    }

    /**
     * 生成可交给界面渲染的真实预览 ItemStack。
     *
     * <p>统一委托 {@link StardewPreview#of(String)}：先校验 {@code items/<id>.json} 是否真实存在
     * （{@code DataComponents.ITEM_MODEL} 的合法取值只能是 items/ 键），不存在则返回
     * {@link ItemStack#EMPTY} 并输出完整诊断链，由界面显示可读缺失项，绝不渲染黑紫。</p>
     */
    default ItemStack previewStack() {
        return StardewPreview.of(itemModel());
    }
}
