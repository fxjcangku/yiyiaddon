package com.yiyiaddon.feature.librarian.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 自动图书管理员 · 目标进度。
 *
 * <p>管理本次运行的目标附魔集合与完成进度，提供「当前目标」「未完成目标」
 * 「是否全部完成」等查询，并在成交验证通过后标记或移除已完成目标。</p>
 *
 * <p><b>两种完成口径</b>：默认只把目标标记为 {@code completed}（保留在列表里，便于
 * 统计已完成数）；设置项「找到后移除目标」为真时直接从列表移除。</p>
 *
 * <p>迁移自旧项目 {@code librarian/model/TargetProgress}（84 行），逐字照搬。</p>
 */
public final class TargetProgress {
    /** 目标附魔列表（可变，完成时按需移除） */
    private final List<EnchantmentTarget> targets;
    /** 初始配置的目标数量（用于统计已完成数） */
    private final int configuredTargetCount;

    public TargetProgress(List<EnchantmentTarget> configuredTargets) {
        List<EnchantmentTarget> copiedTargets = List.copyOf(Objects.requireNonNull(configuredTargets, "configuredTargets"));
        if (copiedTargets.isEmpty()) throw new IllegalArgumentException("至少需要一个目标附魔");
        targets = new ArrayList<>(copiedTargets);
        configuredTargetCount = copiedTargets.size();
    }

    /** 返回当前待处理目标（第一个未完成的），全部完成时为空 */
    public Optional<EnchantmentTarget> currentTarget() {
        return targets.stream()
            .filter(target -> !target.completed())
            .findFirst();
    }

    /** 返回所有未完成目标 */
    public List<EnchantmentTarget> incompleteTargets() {
        return targets.stream()
            .filter(target -> !target.completed())
            .toList();
    }

    /** 标记目标完成（默认不移除，仅置 completed 标记） */
    public void complete(EnchantmentTarget target) {
        complete(target, false);
    }

    /** 标记目标完成，可选择直接移除已完成目标 */
    public void complete(EnchantmentTarget target, boolean removeCompletedTarget) {
        Objects.requireNonNull(target, "target");
        int index = findTargetIndex(target);
        if (removeCompletedTarget) {
            targets.remove(index);
        } else {
            targets.set(index, targets.get(index).asCompleted());
        }
    }

    /** 判断指定目标是否已完成 */
    public boolean isCompleted(EnchantmentTarget target) {
        return targets.get(findTargetIndex(target)).completed();
    }

    /** 是否所有目标均已完成 */
    public boolean allCompleted() {
        return currentTarget().isEmpty();
    }

    /** 返回已完成目标数量（含已移除的） */
    public int completedCount() {
        int removedCount = configuredTargetCount - targets.size();
        return removedCount + (int) targets.stream().filter(EnchantmentTarget::completed).count();
    }

    /** 按附魔 ID + 等级查找目标在列表中的索引，未找到抛异常 */
    private int findTargetIndex(EnchantmentTarget target) {
        for (int i = 0; i < targets.size(); i++) {
            EnchantmentTarget configuredTarget = targets.get(i);
            if (configuredTarget.identifier().equals(target.identifier()) && configuredTarget.level() == target.level()) {
                return i;
            }
        }
        throw new IllegalArgumentException("目标不在配置列表中");
    }
}
