package com.yiyiaddon.feature.enchant.gear;

import java.util.ArrayList;
import java.util.List;

/**
 * 原版装备附魔 · 配置编解码（旧项目 {@code gear/GearEnchantSetting.java} 的纯逻辑部分）。
 *
 * <p>沿用旧项目的字符串列表持久化格式（旧 {@code :26-31}）：
 * <pre>
 *   [0] 装备 ID（minecraft:diamond_sword）
 *   [1] 方案 ID（fortune）
 *   [2..] 每个目标附魔 "enchantId:level:excludedFlag"（flag 0/1）
 * </pre>
 * </p>
 *
 * <p>职责边界：本类只负责「状态序列化 + 读取」，不解析 JSON，不做目标判断。
 * 旧类是本项目不存在的 {@code StringListSetting} 子类，此处<b>不再继承设置壳</b>，
 * 载体改为外部传入的 {@link List}（调用方传 {@code EnchantSettings.gearEnchantConfig}），
 * 所有读写直接落在该列表上，序列化键名与条目格式与旧项目逐字一致。</p>
 *
 * <p><b>未搬项</b>：旧壳的 UI / 图标 / 注册相关成员（{@code createWidget}、{@code refreshSummary}、
 * {@code summaryText}、{@code currentIconStack}、{@code register}、{@code summaryLabel}、
 * {@code iconLabel}）与摘要文案（{@code 未选择装备} / {@code 未选择方案} /
 * {@code §a…§8▸ §b…§8▸ §eN 项目标}）均属界面文案与入口，见批次5。</p>
 */
public final class GearEnchantConfig {

    private static final String EMPTY = "";

    /** 配置载体（调用方的列表对象，本类只在其上读写，不持有副本） */
    private final List<String> backing;

    public GearEnchantConfig(List<String> backing) {
        this.backing = backing;
    }

    /** 当前配置条目（只读视图语义与原 get() 一致） */
    private List<String> get() {
        return backing;
    }

    /** 整体覆盖配置（原 set() 语义：原地替换载体内容，保持外部列表引用不变） */
    private void set(List<String> values) {
        backing.clear();
        backing.addAll(values);
    }

    /** 当前装备 ID，未选择返回 null */
    public String gearId() {
        List<String> v = get();
        return v.isEmpty() ? null : emptyToNull(v.get(0));
    }

    /** 当前方案 ID，未选择返回 null */
    public String profileId() {
        List<String> v = get();
        return v.size() < 2 ? null : emptyToNull(v.get(1));
    }

    /** 应用装备选择：切到该装备的默认方案，并重置附魔为该方案默认值 */
    public void applyGear(String gearId) {
        GearEnchantData.GearDefinition gear = GearEnchantData.get().gear(gearId);
        if (gear == null || gear.profiles.isEmpty()) {
            set(List.of(gearId, EMPTY));
            return;
        }
        GearEnchantData.GearProfile profile = defaultProfile(gear);
        applyProfileInternal(gearId, profile);
    }

    /** 应用方案选择：重置附魔为该方案默认值 */
    public void applyProfile(String profileId) {
        String gearId = gearId();
        if (gearId == null) return;
        GearEnchantData.GearDefinition gear = GearEnchantData.get().gear(gearId);
        if (gear == null) return;
        for (GearEnchantData.GearProfile profile : gear.profiles) {
            if (profile.id.equals(profileId)) {
                applyProfileInternal(gearId, profile);
                return;
            }
        }
    }

    private void applyProfileInternal(String gearId, GearEnchantData.GearProfile profile) {
        List<String> encoded = new ArrayList<>();
        encoded.add(gearId);
        encoded.add(profile.id);
        for (GearEnchantData.TargetDefinition target : profile.targets) {
            encoded.add(target.id + ":" + target.level + ":0");
        }
        set(encoded);
    }

    /** 调整某附魔等级（受真实最大等级限制，clamp 到 [1, max]；注册表不可用时只做下限保护） */
    public void setLevel(String enchantId, int level) {
        int max = GearEnchantData.get().maxLevelOf(enchantId);
        int clamped = Math.max(1, level);
        // maxLevelOf 返回 -1 表示注册表尚未就绪/附魔不存在，此时不强制降级为 1，
        // 否则会出现「点减号从 V 直接跳到 I、点加号无反应」——把 -1 误当 max=1 所致
        if (max >= 1) clamped = Math.min(clamped, max);
        mutateTarget(enchantId, clamped, -1);
    }

    /** 切换某附魔的排除状态 */
    public void setExcluded(String enchantId, boolean excluded) {
        mutateTarget(enchantId, -1, excluded ? 1 : 0);
    }

    /** 读取某附魔当前等级，不存在返回 -1 */
    public int levelOf(String enchantId) {
        for (int i = 2; i < get().size(); i++) {
            String[] parts = parseTarget(get().get(i));
            if (parts != null && parts[0].equals(enchantId)) {
                try { return Integer.parseInt(parts[1]); } catch (NumberFormatException ignored) { return -1; }
            }
        }
        return -1;
    }

    /** 读取某附魔是否被排除 */
    public boolean isExcluded(String enchantId) {
        for (int i = 2; i < get().size(); i++) {
            String[] parts = parseTarget(get().get(i));
            if (parts != null && parts[0].equals(enchantId)) return "1".equals(parts[2]);
        }
        return false;
    }

    /**
     * 从右向左解析「附魔ID:等级:排除标记」条目。
     * 附魔 ID 自带命名空间冒号（如 minecraft:sharpness），不能直接 split(":")，
     * 必须定位最后两个冒号：前面的等级、最后的标记，剩余部分是完整 ID。
     * 非法条目返回 null。
     */
    private static String[] parseTarget(String entry) {
        if (entry == null) return null;
        int flagColon = entry.lastIndexOf(':');
        if (flagColon <= 0) return null;
        int levelColon = entry.lastIndexOf(':', flagColon - 1);
        if (levelColon <= 0) return null;
        return new String[]{
            entry.substring(0, levelColon),
            entry.substring(levelColon + 1, flagColon),
            entry.substring(flagColon + 1)
        };
    }

    /** 生成当前配置对应的 TargetProfile 快照（后续评分 / 规划的唯一依据） */
    public TargetProfile currentProfile() {
        String gearId = gearId();
        String profileId = profileId();
        GearEnchantData.GearDefinition gear = GearEnchantData.get().gear(gearId == null ? "" : gearId);
        GearEnchantData.GearProfile profile = findProfile(gear, profileId);
        if (gear == null) return null;

        List<TargetProfile.TargetEnchantment> targets = new ArrayList<>();
        for (int i = 2; i < get().size(); i++) {
            String[] parts = parseTarget(get().get(i));
            if (parts == null) continue;
            int level;
            try { level = Integer.parseInt(parts[1]); } catch (NumberFormatException e) { continue; }
            boolean excluded = "1".equals(parts[2]);
            boolean required = !isExcludable(profile, parts[0]);
            targets.add(new TargetProfile.TargetEnchantment(parts[0], enchantName(parts[0]), level, excluded, required));
        }

        return new TargetProfile(
            gear.id, gear.name, gear.category,
            profileId == null ? "" : profileId,
            profile == null ? "" : profile.name,
            targets,
            profile == null ? List.of() : profile.exclusiveWith,
            profile == null ? List.of() : profile.forbidden
        );
    }

    private void mutateTarget(String enchantId, int level, int excludedFlag) {
        List<String> next = new ArrayList<>(get());
        for (int i = 2; i < next.size(); i++) {
            String[] parts = parseTarget(next.get(i));
            if (parts == null || !parts[0].equals(enchantId)) continue;
            String lvl = level >= 0 ? String.valueOf(level) : parts[1];
            String ex = excludedFlag >= 0 ? String.valueOf(excludedFlag) : parts[2];
            next.set(i, parts[0] + ":" + lvl + ":" + ex);
            set(next);
            return;
        }
    }

    private GearEnchantData.GearProfile findProfile(GearEnchantData.GearDefinition gear, String profileId) {
        if (gear == null || profileId == null) return null;
        for (GearEnchantData.GearProfile profile : gear.profiles) {
            if (profile.id.equals(profileId)) return profile;
        }
        return null;
    }

    /** 判断某附魔是否可排除（核心附魔 excludable=false 不可排除，返回 false 表示必需） */
    private boolean isExcludable(GearEnchantData.GearProfile profile, String enchantId) {
        if (profile == null) return true;
        for (GearEnchantData.TargetDefinition target : profile.targets) {
            if (target.id.equals(enchantId)) return target.excludable;
        }
        return true;
    }

    private GearEnchantData.GearProfile defaultProfile(GearEnchantData.GearDefinition gear) {
        for (GearEnchantData.GearProfile profile : gear.profiles) {
            if (profile.isDefault) return profile;
        }
        return gear.profiles.get(0);
    }

    private String enchantName(String enchantId) {
        GearEnchantData.GearDefinition gear = GearEnchantData.get().gear(gearId() == null ? "" : gearId());
        GearEnchantData.GearProfile profile = findProfile(gear, profileId());
        if (profile != null) {
            for (GearEnchantData.TargetDefinition target : profile.targets) {
                if (target.id.equals(enchantId)) return target.name;
            }
        }
        return enchantId;
    }

    private static String emptyToNull(String value) {
        return (value == null || value.isEmpty()) ? null : value;
    }
}
