package com.yiyiaddon.feature.enchant.vanilla;

import com.mojang.logging.LogUtils;
import com.yiyiaddon.feature.enchant.gear.TargetProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 原版装备极品附魔 · 规则校验器（VanillaEnchantRuleValidator）。
 *
 * <p>启动 / 开发测试时运行，逐条核对静态数据库与 {@link TargetProfile} 的合法性，
 * 拒绝「不存在的装备」「互斥目标」「非法等级」「宝藏附魔冒充 30 级候选」等错误配置。
 * 校验分两部分：</p>
 * <ol>
 *   <li>静态一致性（无需进世界）：互斥双向、候选可达等级 ≤ 最大等级、装备候选池覆盖；</li>
 *   <li>注册表一致性（需进世界）：装备 / 附魔真实存在、等级 ≤ 注册表上限、装备支持该附魔。</li>
 * </ol>
 */
public final class VanillaEnchantRuleValidator {

    private static final Logger LOG = LogUtils.getLogger();

    private VanillaEnchantRuleValidator() {
    }

    /** 校验静态数据库一致性，返回错误列表（空 = 通过） */
    public static List<String> validateDatabase() {
        List<String> errors = new ArrayList<>();
        VanillaEnchantDatabase db = VanillaEnchantDatabase.get();
        errors.addAll(db.loadErrors());

        // 1. 互斥关系双向一致（§36.5）
        for (VanillaEnchantDatabase.EnchantmentRule r : db.rules()) {
            Set<String> cset = db.conflictSetOf(r.id());
            for (String other : cset) {
                if (!db.conflictSetOf(other).contains(r.id())) {
                    errors.add("互斥关系非双向：" + r.id() + " → " + other);
                }
            }
        }

        // 2. 候选可达等级 ≤ 静态最大等级（§36.3）
        for (VanillaEnchantDatabase.GearCandidateRule gear : db.gears()) {
            for (String enchId : gear.reachableLevels().keySet()) {
                int max = db.maxLevelOf(enchId);
                if (max < 0) {
                    errors.add("候选池引用未知附魔：" + gear.itemId() + " → " + enchId);
                    continue;
                }
                for (int level : gear.reachable(enchId)) {
                    if (level > max) {
                        errors.add("候选等级超上限：" + gear.itemId() + " → " + enchId + " 等级 " + level);
                    }
                }
            }
        }

        // 3. 附魔规则自检：等级成本单调性（maxLevel 至少 1）
        for (VanillaEnchantDatabase.EnchantmentRule r : db.rules()) {
            if (r.maxLevel() < 1) errors.add("附魔最大等级非法：" + r.id());
        }
        return errors;
    }

    /** 校验目标方案合法性（§37 / §41），返回错误列表（空 = 通过） */
    public static List<String> validateProfile(TargetProfile profile) {
        List<String> errors = new ArrayList<>();
        if (profile == null) {
            errors.add("目标方案为空");
            return errors;
        }
        VanillaEnchantDatabase db = VanillaEnchantDatabase.get();
        String gearId = profile.gearId();

        // 装备是否存在（静态库）
        VanillaEnchantDatabase.GearCandidateRule gear = db.gear(gearId);
        if (gear == null) {
            errors.add("装备不存在：" + gearId);
            return errors; // 装备都不存在，后续无法判断，直接返回
        }

        Set<String> activeIds = new java.util.HashSet<>();
        for (TargetProfile.TargetEnchantment t : profile.activeTargets()) {
            activeIds.add(t.id());
            // 附魔规则存在
            VanillaEnchantDatabase.EnchantmentRule rule = db.rule(t.id());
            if (rule == null) {
                errors.add("附魔不存在：" + t.id());
                continue;
            }
            // 等级合法
            if (t.level() < 1 || t.level() > rule.maxLevel()) {
                errors.add("附魔等级非法：" + t.id() + " 等级 " + t.level() + "（上限 " + rule.maxLevel() + "）");
            }
            // 必需附魔必须能由 30 级附魔台获得，否则不可达（§18 / §19）
            if (t.required() && !gear.tableReachable(t.id())) {
                errors.add("必需附魔无法由 30 级附魔台获得：" + t.id() + "（" + profile.gearName() + "）");
            }
        }

        // 目标内互斥（§37）
        String[] ids = activeIds.toArray(new String[0]);
        for (int i = 0; i < ids.length; i++) {
            for (int j = i + 1; j < ids.length; j++) {
                if (db.conflictsWith(ids[i], ids[j])) {
                    errors.add("目标含互斥附魔：" + ids[i] + " vs " + ids[j]);
                }
            }
        }

        // 禁止列表附魔需真实存在
        for (String f : profile.forbiddenIds()) {
            if (db.rule(f) == null) errors.add("禁止附魔不存在：" + f);
        }

        // 注册表一致性（进入世界后）
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            validateAgainstRegistry(mc, profile, gear, errors);
        }
        return errors;
    }

    /** 用 26.1.2 注册表二次校验（装备 / 附魔真实存在、装备支持附魔） */
    private static void validateAgainstRegistry(Minecraft mc, TargetProfile profile,
                                                VanillaEnchantDatabase.GearCandidateRule gear, List<String> errors) {
        Identifier gearId = Identifier.tryParse(profile.gearId());
        if (gearId == null) return;
        Item item = mc.level.registryAccess().lookupOrThrow(Registries.ITEM)
            .get(ResourceKey.create(Registries.ITEM, gearId)).map(h -> h.value()).orElse(null);
        if (item == null) {
            // 注册表查不到时拿不到本地化名称，只保留技术 ID 作为附加信息
            errors.add("注册表里不存在该装备（技术 ID：" + profile.gearId() + "）");
            return;
        }
        ItemStack stack = item.getDefaultInstance();
        for (TargetProfile.TargetEnchantment t : profile.activeTargets()) {
            Identifier enchId = Identifier.tryParse(t.id());
            if (enchId == null) continue;
            Enchantment enchantment = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                .get(ResourceKey.create(Registries.ENCHANTMENT, enchId)).map(h -> h.value()).orElse(null);
            if (enchantment == null) {
                errors.add("注册表里不存在该附魔（技术 ID：" + t.id() + "）");
                continue;
            }
            if (!enchantment.isSupportedItem(stack)) {
                // 玩家看中文名，技术 ID 只作附加信息
                errors.add("装备不支持该附魔：" + stack.getHoverName().getString()
                    + " 不能附魔 " + enchantment.description().getString()
                    + "（装备 ID：" + profile.gearId() + " / 附魔 ID：" + t.id() + "）");
            }
        }
    }

    /** 启动时汇总校验并写日志，返回是否有错误 */
    public static boolean reportStartupValidation() {
        List<String> errors = validateDatabase();
        if (!errors.isEmpty()) {
            LOG.error("[vanilla-enchant] 规则数据库校验失败：");
            for (String e : errors) LOG.error("  - " + e);
        }
        return errors.isEmpty();
    }
}
