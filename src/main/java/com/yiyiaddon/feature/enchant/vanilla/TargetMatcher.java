package com.yiyiaddon.feature.enchant.vanilla;

import com.yiyiaddon.feature.enchant.gear.EnchantEvaluationService;
import com.yiyiaddon.feature.enchant.gear.TargetProfile;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 原版装备极品附魔 · 目标匹配器（TargetMatcher）。
 *
 * <p>职责：把 {@code 实际 ItemStack} 与 {@link TargetProfile}（JSON 极品方案）做严格对比，
 * 返回六态判定 + 明细，供砂轮 / 保留 / 铁砧 / 卸货决策使用：</p>
 * <ul>
 *   <li>{@code EMPTY}：无附魔；</li>
 *   <li>{@code COMPLETE}：全部活动目标达到目标等级且无禁止/互斥残留；</li>
 *   <li>{@code PARTIAL}：部分达标或等级不足，可保留继续培养；</li>
 *   <li>{@code FORBIDDEN_PRESENT}：存在被排除或方案禁止的附魔，必须砂轮；</li>
 *   <li>{@code CONFLICT_PRESENT}：存在 26.1.2 互斥附魔对，必须砂轮；</li>
 *   <li>{@code UNREACHABLE}：必需附魔无法通过 30 级附魔台获得且当前未满足，规划不可达。</li>
 * </ul>
 *
 * <p>铁律：等级必须精确匹配（§51），效率 IV 不能当成效率 V；禁止自动把用户没要求的
 * 属性当目标（§52）；最终验收只认本类 COMPLETE。</p>
 */
public final class TargetMatcher {

    private TargetMatcher() {
    }

    /** 目标匹配状态 */
    public enum State {
        /** 空装备（无附魔），不参与匹配 */
        EMPTY,
        /** 全部活动目标达标，且无禁止 / 互斥 / 排除残留 */
        COMPLETE,
        /** 部分达标或等级不足，可保留继续培养 */
        PARTIAL,
        /** 存在被排除或明确禁止的附魔，必须砂轮 */
        FORBIDDEN_PRESENT,
        /** 存在互斥附魔对（26.1.2 exclusive_set），必须砂轮 */
        CONFLICT_PRESENT,
        /** 必需附魔无法通过 30 级附魔台获得且当前未满足，规划不可达 */
        UNREACHABLE
    }

    /** 匹配结果快照 */
    public record Result(State state,
                         Set<String> satisfied,
                         Set<String> underleveled,
                         Set<String> missing,
                         Set<String> forbiddenPresent,
                         Set<String> conflicts,
                         Set<String> unreachable,
                         String detail) {

        /** 是否最终达标（COMPLETE） */
        public boolean complete() {
            return state == State.COMPLETE;
        }

        /** 是否必须砂轮（禁止或互斥） */
        public boolean junk() {
            return state == State.FORBIDDEN_PRESENT || state == State.CONFLICT_PRESENT;
        }

        /** 是否有保留价值（至少命中一个活动目标，且无禁止/互斥） */
        public boolean worthKeeping() {
            if (junk()) return false;
            return !satisfied.isEmpty() || !underleveled.isEmpty();
        }
    }

    /**
     * 实际装备 vs 目标方案：六态严格匹配。
     *
     * @param stack   实际装备（必须是方案对应类型的装备）
     * @param profile 目标极品方案
     */
    public static Result match(ItemStack stack, TargetProfile profile) {
        Map<String, Integer> actual = EnchantEvaluationService.readEnchantments(stack);
        if (actual.isEmpty()) {
            return new Result(State.EMPTY, Set.of(), Set.of(), Set.of(), Set.of(), Set.of(), Set.of(), "无附魔");
        }

        VanillaEnchantDatabase db = VanillaEnchantDatabase.get();

        Set<String> satisfied = new LinkedHashSet<>();
        Set<String> underleveled = new LinkedHashSet<>();
        Set<String> missing = new LinkedHashSet<>();

        // 严格等级匹配：等级不足不能算达标（§51）
        for (TargetProfile.TargetEnchantment t : profile.activeTargets()) {
            Integer level = actual.get(t.id());
            if (level == null) missing.add(t.id());
            else if (level >= t.level()) satisfied.add(t.id());
            else underleveled.add(t.id());
        }

        // 禁止：被排除的目标实际存在 ∪ 方案级 forbidden 列表（§39 / §52 / §69）
        Set<String> forbiddenPresent = new LinkedHashSet<>();
        for (TargetProfile.TargetEnchantment t : profile.targets()) {
            if (t.excluded() && actual.containsKey(t.id())) forbiddenPresent.add(t.id());
        }
        for (String f : profile.forbiddenIds()) {
            if (actual.containsKey(f)) forbiddenPresent.add(f);
        }

        // 互斥：实装附魔中的 26.1.2 exclusive_set 冲突对
        Set<String> conflicts = new LinkedHashSet<>();
        String[] ids = actual.keySet().toArray(new String[0]);
        for (int i = 0; i < ids.length; i++) {
            for (int j = i + 1; j < ids.length; j++) {
                if (db.conflictsWith(ids[i], ids[j])) {
                    conflicts.add(ids[i]);
                    conflicts.add(ids[j]);
                }
            }
        }
        // 互斥补充：装备带的目标外附魔与方案目标附魔冲突（如时运镐在精准采集方案下，
        // fortune 与目标 silk_touch 互斥），应判垃圾送砂轮，避免铁砧合并后「太昂贵」
        for (String actualId : actual.keySet()) {
            for (TargetProfile.TargetEnchantment t : profile.activeTargets()) {
                if (!actualId.equals(t.id()) && db.conflictsWith(actualId, t.id())) {
                    conflicts.add(actualId);
                }
            }
        }

        // 不可达：活动必需附魔无法从 30 级附魔台获得且当前未满足（§18 / §19）
        Set<String> unreachable = new LinkedHashSet<>();
        for (TargetProfile.TargetEnchantment t : profile.requiredTargets()) {
            if (satisfied.contains(t.id())) continue;
            if (!db.tableReachable(profile.gearId(), t.id())) unreachable.add(t.id());
        }

        State state;
        if (!forbiddenPresent.isEmpty()) {
            state = State.FORBIDDEN_PRESENT;
        } else if (!conflicts.isEmpty()) {
            state = State.CONFLICT_PRESENT;
        } else if (!unreachable.isEmpty()) {
            state = State.UNREACHABLE;
        } else if (satisfied.size() == profile.activeTargets().size() && missing.isEmpty()) {
            state = State.COMPLETE;
        } else {
            state = State.PARTIAL;
        }

        return new Result(state, satisfied, underleveled, missing,
            forbiddenPresent, conflicts, unreachable, buildDetail(state, satisfied, underleveled, missing,
                forbiddenPresent, conflicts, unreachable));
    }

    /** 是否必须砂轮（无保留价值）：禁止 / 互斥 / 无任何目标命中 */
    public static boolean isJunk(ItemStack stack, TargetProfile profile) {
        Result r = match(stack, profile);
        return r.junk() || !r.worthKeeping();
    }

    /**
     * 是否需要砂轮磨掉（统一判定：规划引擎决策与执行层共用）。
     * <p>磨除条件：禁止/互斥/零命中，或「低密度且离满级远」。低密度单命中装备若其附魔
     * 等级已接近目标（差 1 级），保留作为凑满级的关键中间态；否则合并次数多、PWP 累积快，
     * 易触发「太昂贵」，磨掉重附。</p>
     *
     * @param r       匹配结果
     * @param profile 目标方案
     * @param actual  装备实际附魔（等级来源）
     */
    public static boolean shouldGrind(Result r, TargetProfile profile, Map<String, Integer> actual) {
        if (r.complete()) return false;
        if (r.junk()) return true;
        if (!r.worthKeeping()) return true;
        // 低密度单命中：命中附魔离目标差 1 级以内保留，差超过 1 级磨掉重附
        if (r.satisfied().isEmpty() && r.underleveled().size() == 1) {
            String id = r.underleveled().iterator().next();
            Integer lv = actual.get(id);
            Integer target = 目标等级(profile, id);
            return target == null || lv == null || target - lv > 1;
        }
        return false;
    }

    /** 基于实际装备判定是否需要砂轮磨掉（自动推导匹配结果与实际附魔） */
    public static boolean shouldGrind(ItemStack stack, TargetProfile profile) {
        return shouldGrind(match(stack, profile), profile, EnchantEvaluationService.readEnchantments(stack));
    }

    /** 查目标方案里某附魔的目标等级，未命中返回 null */
    private static Integer 目标等级(TargetProfile profile, String id) {
        for (TargetProfile.TargetEnchantment t : profile.activeTargets()) {
            if (t.id().equals(id)) return t.level();
        }
        return null;
    }

    /** 是否最终达标（COMPLETE）：全部活动目标满级且无禁止/互斥/不可达残留，最终验收专用 */
    public static boolean isComplete(ItemStack stack, TargetProfile profile) {
        return match(stack, profile).complete();
    }

    /** 生成中文明细（状态播报与报告用） */
    private static String buildDetail(State state, Set<String> satisfied, Set<String> underleveled,
                                      Set<String> missing, Set<String> forbidden, Set<String> conflicts,
                                      Set<String> unreachable) {
        StringBuilder sb = new StringBuilder();
        switch (state) {
            case COMPLETE -> sb.append("已达成 ").append(satisfied.size()).append(" 项目标");
            case PARTIAL -> {
                sb.append("达成 ").append(satisfied.size()).append(" 项");
                if (!underleveled.isEmpty()) sb.append("，等级不足 ").append(underleveled.size()).append(" 项");
                if (!missing.isEmpty()) sb.append("，缺失 ").append(missing.size()).append(" 项");
            }
            case FORBIDDEN_PRESENT -> sb.append("含禁止附魔 ").append(names(forbidden));
            case CONFLICT_PRESENT -> sb.append("含互斥附魔 ").append(names(conflicts));
            case UNREACHABLE -> sb.append("必需附魔不可达 ").append(names(unreachable));
            default -> sb.append("无附魔");
        }
        return sb.toString();
    }

    /** 附魔 id 去命名空间后拼接展示 */
    private static String names(Set<String> ids) {
        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            if (sb.length() > 0) sb.append("、");
            sb.append(id.indexOf(':') < 0 ? id : id.substring(id.indexOf(':') + 1));
        }
        return sb.toString();
    }
}
