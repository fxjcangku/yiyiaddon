package com.yiyiaddon.feature.enchant.gear;

import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import net.minecraft.world.item.ItemStack;

/**
 * 原版装备附魔 · 单件装备任务状态。
 *
 * <p>每件装备一个独立任务，记录任务 ID、装备身份、目标方案、当前装备、
 * 阶段、步骤、经验、点位、状态与错误原因，供状态机独立推进与异常恢复。</p>
 */
public final class GearEnchantTask {

    /** 任务执行阶段 */
    public enum Stage {
        IDLE("待机"),
        ENCHANTING("附魔中"),
        EVALUATING("评估中"),
        GRINDING("挂机刷经验"),
        ANVIL_PLANNING("铁砧规划"),
        ANVIL_MERGING("铁砧合并"),
        VALIDATING("最终验收"),
        STORING("存入成品箱"),
        ERROR("异常"),
        DONE("完成");

        private final String title;

        Stage(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    /** 任务状态 */
    public enum Status {
        PENDING("待处理"),
        PROCESSING("处理中"),
        ERROR("异常"),
        DONE("完成");

        private final String title;

        Status(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private final int taskId;
    /** 装备身份（初始快照） */
    private final ItemStack gear;
    /** 目标方案 */
    private final TargetProfile profile;

    /** 当前装备（附魔/铁砧过程中被修改） */
    private ItemStack currentGear;
    /** 当前阶段 */
    private Stage stage;
    /** 当前步骤 */
    private int step;
    /** 当前经验等级 */
    private int currentXp;
    /** 当前目标经验等级 */
    private int targetXp;
    /** 当前所在点位 */
    private EnchantPointType currentPoint;
    /** 任务状态 */
    private Status status;
    /** 错误原因（仅 ERROR 状态有意义） */
    private String errorReason;

    public GearEnchantTask(int taskId, ItemStack gear, TargetProfile profile) {
        this.taskId = taskId;
        this.gear = gear;
        this.profile = profile;
        this.currentGear = gear;
        this.stage = Stage.IDLE;
        this.step = 0;
        this.currentXp = 0;
        this.targetXp = 0;
        this.status = Status.PENDING;
    }

    /** 最终验收：完整解析实际装备并与目标比较，只有 100% 满足才通过 */
    public boolean accept(ItemStack finalGear) {
        EnchantEvaluationResult result = EnchantEvaluationService.evaluate(
            finalGear, profile, AcceptanceStrategy.STRICT, 1.0);
        return result.complete();
    }

    /** 标记失败（装备处理失败，跳过该件继续下一件） */
    public void markError(TaskErrorReason reason) {
        this.stage = Stage.ERROR;
        this.status = Status.ERROR;
        this.errorReason = reason.toString();
    }

    /** 标记完成（已通过最终验收） */
    public void markDone() {
        this.stage = Stage.DONE;
        this.status = Status.DONE;
        this.errorReason = null;
    }

    public int taskId() {
        return taskId;
    }

    public ItemStack gear() {
        return gear;
    }

    public TargetProfile profile() {
        return profile;
    }

    public ItemStack currentGear() {
        return currentGear;
    }

    public void currentGear(ItemStack currentGear) {
        this.currentGear = currentGear;
    }

    public Stage stage() {
        return stage;
    }

    public void stage(Stage stage) {
        this.stage = stage;
    }

    public int step() {
        return step;
    }

    public void step(int step) {
        this.step = step;
    }

    public int currentXp() {
        return currentXp;
    }

    public void currentXp(int currentXp) {
        this.currentXp = currentXp;
    }

    public int targetXp() {
        return targetXp;
    }

    public void targetXp(int targetXp) {
        this.targetXp = targetXp;
    }

    public EnchantPointType currentPoint() {
        return currentPoint;
    }

    public void currentPoint(EnchantPointType currentPoint) {
        this.currentPoint = currentPoint;
    }

    public Status status() {
        return status;
    }

    public String errorReason() {
        return errorReason;
    }

    public boolean isError() {
        return status == Status.ERROR;
    }

    public boolean isDone() {
        return status == Status.DONE;
    }
}
