package com.yiyiaddon.feature.enchant.gear;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 原版装备极品附魔 · 统一目标模型（TargetProfile）。
 *
 * <p>这是「附魔评分 → 铁砧规划 → 最终验收」全链路的唯一目标依据，
 * 描述某件装备当前选中的极品方案，以及方案下每个目标附魔的等级与排除状态。</p>
 *
 * <p>本类为不可变快照，由 {@link GearEnchantData} 解析 JSON 后经用户 UI 配置生成，
 * 后续规划逻辑只读取本对象，不反向依赖 UI 或 JSON 文件。</p>
 */
public final class TargetProfile {

    /** 单个目标附魔：附魔 ID、显示名、目标等级、是否被排除、是否必需核心附魔 */
    public record TargetEnchantment(String id, String name, int level, boolean excluded, boolean required) {
        public TargetEnchantment {
            Objects.requireNonNull(id, "id");
            Objects.requireNonNull(name, "name");
            if (level < 1) throw new IllegalArgumentException("附魔等级必须大于 0");
        }

        /** 是否为必需核心附魔（false 表示可选，XP 不足时可放弃） */
        public boolean isRequired() {
            return required;
        }
    }

    /** 装备 ID（如 minecraft:diamond_sword） */
    private final String gearId;
    /** 装备中文名 */
    private final String gearName;
    /** 装备类别（weapon / armor） */
    private final String category;
    /** 方案 ID */
    private final String profileId;
    /** 方案中文名 */
    private final String profileName;
    /** 方案下全部目标附魔（含被排除项，excluded 标记状态） */
    private final List<TargetEnchantment> targets;
    /** 与本方案互斥的其他方案 ID 列表 */
    private final List<String> exclusiveProfiles;
    /** 目标禁止拥有的附魔 ID（FORBIDDEN 语义：最终成品不允许出现） */
    private final List<String> forbiddenIds;

    public TargetProfile(String gearId, String gearName, String category, String profileId, String profileName,
                         List<TargetEnchantment> targets, List<String> exclusiveProfiles) {
        this(gearId, gearName, category, profileId, profileName, targets, exclusiveProfiles, List.of());
    }

    public TargetProfile(String gearId, String gearName, String category, String profileId, String profileName,
                         List<TargetEnchantment> targets, List<String> exclusiveProfiles, List<String> forbiddenIds) {
        this.gearId = Objects.requireNonNull(gearId, "gearId");
        this.gearName = Objects.requireNonNull(gearName, "gearName");
        this.category = Objects.requireNonNull(category, "category");
        this.profileId = Objects.requireNonNull(profileId, "profileId");
        this.profileName = Objects.requireNonNull(profileName, "profileName");
        this.targets = List.copyOf(targets);
        this.exclusiveProfiles = List.copyOf(exclusiveProfiles);
        this.forbiddenIds = List.copyOf(forbiddenIds);
    }

    /** 返回最终生效的目标附魔（过滤掉被排除项） */
    public List<TargetEnchantment> activeTargets() {
        List<TargetEnchantment> active = new ArrayList<>();
        for (TargetEnchantment t : targets) {
            if (!t.excluded()) active.add(t);
        }
        return active;
    }

    /** 返回必需核心目标（未排除且 required=true），AnvilPlanner 必须 100% 达成 */
    public List<TargetEnchantment> requiredTargets() {
        List<TargetEnchantment> required = new ArrayList<>();
        for (TargetEnchantment t : targets) {
            if (!t.excluded() && t.required()) required.add(t);
        }
        return required;
    }

    /** 返回可选目标（未排除且 required=false），XP 不足时可放弃 */
    public List<TargetEnchantment> optionalTargets() {
        List<TargetEnchantment> optional = new ArrayList<>();
        for (TargetEnchantment t : targets) {
            if (!t.excluded() && !t.required()) optional.add(t);
        }
        return optional;
    }

    public String gearId() { return gearId; }
    public String gearName() { return gearName; }
    public String category() { return category; }
    public String profileId() { return profileId; }
    public String profileName() { return profileName; }
    public List<TargetEnchantment> targets() { return targets; }
    public List<String> exclusiveProfiles() { return exclusiveProfiles; }
    public List<String> forbiddenIds() { return forbiddenIds; }

    /** 是否为空目标（没有任何启用附魔） */
    public boolean isEmpty() {
        return activeTargets().isEmpty();
    }
}
