package com.yiyiaddon.feature.autofarm.controller;

import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.feature.autofarm.model.FarmSite;
import com.yiyiaddon.feature.autofarm.model.FarmState;
import com.yiyiaddon.feature.autofarm.model.FarmTarget;
import com.yiyiaddon.feature.autofarm.model.SiteType;
import com.yiyiaddon.feature.autofarm.resource.FarmResourceManager;
import com.yiyiaddon.feature.autofarm.scan.FarmScanner;
import com.yiyiaddon.feature.autofarm.task.BatchHarvestTask;
import com.yiyiaddon.feature.autofarm.task.BatchPlantTask;
import com.yiyiaddon.feature.autofarm.task.CollectTask;
import com.yiyiaddon.feature.autofarm.task.FarmTask;
import com.yiyiaddon.feature.autofarm.task.HarvestTask;
import com.yiyiaddon.feature.autofarm.task.PlantTask;
import com.yiyiaddon.feature.autofarm.task.PoisonDumpTask;
import com.yiyiaddon.feature.autofarm.task.RestockTask;
import com.yiyiaddon.feature.autofarm.task.TaskResult;
import com.yiyiaddon.feature.autofarm.task.TillTask;
import com.yiyiaddon.feature.autofarm.task.UnloadTask;
import com.yiyiaddon.platform.navigation.FarmNav;
import com.yiyiaddon.service.container.ContainerService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 农场总调度器：任务生命周期与状态机。
 *
 * 核心不变量（Observe → Decide → Act → Verify → Replan）：
 * currentTask 非空时只执行该任务，Scanner 继续观察但新目标不创建任务、不抢占 Baritone；
 * currentTask 为空时才允许决策。物流任务（卸货/补货/毒马铃薯）期间同样独占。
 *
 * 批量模式：一次 Decision 锁定多个目标，瞬间批量破坏后紧跟快速批量补种（边收边种），
 * 补种完最后统一拾取掉落物；收割与补种均为一帧内批量操作，整轮很快，掉落物不会滞留超时。
 * 计划绝不跨越物流、世界切换、模块关闭、死亡、断线。
 */
public final class FarmController {

    private final FarmScanner scanner;
    private final FarmResourceManager resources;
    private final FarmObserver observer;
    private final FarmVerifier verifier;
    private final ContainerService broker;
    private final FarmDecision decision;

    private FarmTask currentTask;
    private FarmState state = FarmState.OBSERVE;

    /** 结果播报回调（参考自动村民交易的 logger 机制），由模块注入 notify */
    private Consumer<String> logger = msg -> {};

    /** 批量收割计划（可能为 null），一次 Decision 锁定、严格串行消耗 */
    private BatchHarvestPlan batchPlan;

    /** 批量补种计划（可能为 null），一次补种决策锁定、连续快速补种 */
    private BatchPlantPlan batchPlantPlan;

    /** 批量锄地计划（可能为 null），一次锄地决策锁定、连续快速锄地 */
    private BatchTillPlan batchTillPlan;

    /** 「正在锄地」播报去重锁 */
    private boolean tillActiveNotified = false;
    /** 「没有锄头」播报去重锁 */
    private boolean noHoeNotified = false;
    /** 回中心点等待的归位计时（看门狗防卡死） */
    private int idleReturnTicks = 0;
    /** 回中心点等待的播报去重锁 */
    private String lastIdleNotify = "";

    /** 回中心点寻路超时阈值（tick），超过则取消并原地等待，防止无限寻路卡死 */
    private static final int CENTER_WAIT_TIMEOUT = 200;

    /** 批量收割后的快速补种阶段标志（补种完成后需继续统一拾取掉落物） */
    private boolean harvestReplantPhase = false;

    /** 分波交错收割中待恢复的收割任务（收一波→补一波→捡一波后继续下一波） */
    private BatchHarvestTask interleavedHarvest;

    /** 动态配置（onActivate / 点位变更时更新） */
    private Map<SiteType, FarmSite> sites = Map.of();
    private double collectRange = 4;

    public FarmController(FarmScanner scanner, FarmResourceManager resources, FarmObserver observer,
                          FarmVerifier verifier, ContainerService broker, FarmDecision decision) {
        this.scanner = scanner;
        this.resources = resources;
        this.observer = observer;
        this.verifier = verifier;
        this.broker = broker;
        this.decision = decision;
    }

    /** 注入结果播报回调 */
    public void setLogger(Consumer<String> logger) {
        this.logger = logger == null ? msg -> {} : logger;
    }

    /** 更新动态配置 */
    public void configure(Map<SiteType, FarmSite> sites, double collectRange) {
        this.sites = sites;
        this.collectRange = collectRange;
    }

    public FarmState state() {
        return state;
    }

    public FarmTask currentTask() {
        return currentTask;
    }

    /** 每 tick 推进一次 */
    public void tick() {
        // 安全闸：世界未就绪不动作；玩家无法继续（死亡等）时暂停并清理任务与批量计划
        if (!observer.worldReady()) return;
        if (!observer.playerAlive()) {
            if (currentTask != null) {
                currentTask.cancel();
                currentTask = null;
            }
            if (interleavedHarvest != null) {
                interleavedHarvest.cancel();
                interleavedHarvest = null;
            }
            batchPlan = null;
            batchPlantPlan = null;
            batchTillPlan = null;
            tillActiveNotified = false;
            noHoeNotified = false;
            idleReturnTicks = 0;
            lastIdleNotify = "";
            harvestReplantPhase = false;
            FarmNav.cancel();
            setState(FarmState.OBSERVE);
            return;
        }

        // 持续观察 + 容器同步
        scanner.tick();
        broker.tick();

        // 有当前任务：只执行它
        if (currentTask != null) {
            TaskResult result = currentTask.tick();
            if (result.done()) {
                FarmTask finished = currentTask;
                currentTask = null;
                handleTaskResult(finished, result);
            }
            return;
        }

        // 无任务：先物流检查（触发则丢弃批量计划）
        FarmTask logistics = decision.decideLogistics(sites);
        if (logistics != null) {
            batchPlan = null;
            batchPlantPlan = null;
            batchTillPlan = null;
            tillActiveNotified = false;
            noHoeNotified = false;
            harvestReplantPhase = false;
            currentTask = logistics;
            setState(stateFor(logistics));
            return;
        }

        // 批量收割计划继续：一次性把全部有效目标交给瞬间多破坏任务
        if (batchPlan != null) {
            List<FarmTarget> all = new ArrayList<>();
            while (batchPlan.hasNext()) {
                FarmTarget target = batchPlan.next();
                if (verifier.targetStillValid(target)) all.add(target);
            }
            batchPlan = null;
            if (!all.isEmpty()) {
                currentTask = new BatchHarvestTask(all, verifier, decision.reachDistance());
                setState(FarmState.HARVEST);
                return;
            }
            logger.accept("§a✓ 本轮批量收割完成");
        }

        // 批量补种计划继续：一次性把全部有效目标交给瞬间多补种任务
        if (batchPlantPlan != null) {
            List<FarmTarget> all = new ArrayList<>();
            while (batchPlantPlan.hasNext()) {
                FarmTarget target = batchPlantPlan.next();
                if (verifier.targetStillValid(target)) all.add(target);
            }
            batchPlantPlan = null;
            if (!all.isEmpty()) {
                currentTask = new BatchPlantTask(all, verifier, decision.reachDistance());
                setState(FarmState.PLANT);
                return;
            }
        }

        // 批量锄地计划继续：逐个取目标，执行前校验仍可开垦，无效跳过
        if (batchTillPlan != null) {
            while (batchTillPlan.hasNext()) {
                FarmTarget target = batchTillPlan.next();
                if (verifier.targetStillValid(target)) {
                    currentTask = new TillTask(target, verifier, decision.reachDistance());
                    setState(FarmState.TILL);
                    return;
                }
            }
            batchTillPlan = null; // 计划耗尽，丢弃
            tillActiveNotified = false;
            logger.accept("§a✓ 锄地完成");
        }

        // 锄地决策：优先把草方块/泥土开垦成耕地，为后续补种准备底盘
        if (decision.tillWorkAvailable()) {
            List<FarmTarget> tillTargets = decision.selectTillTargets();
            if (tillTargets.isEmpty()) {
                // 有可锄目标但没有锄头：跳过锄地，继续补种/收割
                tillActiveNotified = false;
                if (!noHoeNotified) {
                    noHoeNotified = true;
                    logger.accept("§c✗ 没有锄头 §8▸ 已跳过锄地");
                }
            } else {
                noHoeNotified = false;
                if (!tillActiveNotified) {
                    tillActiveNotified = true;
                    logger.accept("§7正在锄地...");
                }
                if (tillTargets.size() > 1) {
                    batchTillPlan = new BatchTillPlan(tillTargets.subList(1, tillTargets.size()));
                }
                currentTask = new TillTask(tillTargets.get(0), verifier, decision.reachDistance());
                setState(FarmState.TILL);
                return;
            }
        } else {
            tillActiveNotified = false;
            noHoeNotified = false;
        }

        // 补种决策：优先批量补种空耕地（模块开启后先快速补满，再进入收割状态机等菜熟收）
        List<FarmTarget> plantTargets = decision.selectPlantTargets();
        if (!plantTargets.isEmpty()) {
            if (plantTargets.size() > 1) {
                // 批量补种：锁定全部目标，下一 tick 由 batchPlantPlan 分支瞬间补满
                batchPlantPlan = new BatchPlantPlan(plantTargets);
                setState(FarmState.PLANT);
            } else {
                currentTask = new PlantTask(plantTargets.get(0), verifier, decision.reachDistance());
                setState(FarmState.PLANT);
            }
            return;
        }

        // 收割决策：补满后（无空耕地）再收割成熟作物；单颗走单个收割，多颗走瞬间批量破坏
        List<FarmTarget> harvestTargets = decision.selectHarvestTargets();
        if (!harvestTargets.isEmpty()) {
            if (harvestTargets.size() > 1) {
                // 批量收割：锁定全部目标，下一 tick 由 batchPlan 分支瞬间破坏
                batchPlan = new BatchHarvestPlan(harvestTargets);
                setState(FarmState.HARVEST);
            } else {
                currentTask = new HarvestTask(harvestTargets.get(0), verifier, decision.reachDistance());
                setState(FarmState.HARVEST);
            }
            return;
        }

        // 无任何可做工作：回农场中心点等待菜成熟（带看门狗防卡死）
        updateIdleWait();
    }

    /** 农场范围中心点，用于无菜时站桩等待；Y 取玩家当前脚下高度，避免寻路到空气中卡住 */
    private BlockPos farmCenter() {
        BlockPos min = scanner.min();
        BlockPos max = scanner.max();
        Minecraft mc = Minecraft.getInstance();
        int y = mc.player != null ? mc.player.getBlockY() : (min.getY() + max.getY()) / 2;
        return new BlockPos(
            (min.getX() + max.getX()) / 2,
            y,
            (min.getZ() + max.getZ()) / 2);
    }

    /**
     * 无菜时的归位等待：回到农场中心点站桩，期间扫描器继续观察。
     * 带超时看门狗，寻路不可用或超时则原地等待，避免历史上回中心点卡死的问题。
     */
    private void updateIdleWait() {
        if (!scanner.bounded()) {
            idleReturnTicks = 0;
            lastIdleNotify = "";
            setState(FarmState.OBSERVE);
            return;
        }

        BlockPos center = farmCenter();

        // 已到达中心点附近：停下等待
        if (FarmNav.arrived(center, 1.5)) {
            FarmNav.cancel();
            idleReturnTicks = 0;
            if (!"arrived".equals(lastIdleNotify)) {
                lastIdleNotify = "arrived";
                logger.accept("§a✓ 已就位农场中心点 §8▸ 等待菜成熟");
            }
            setState(FarmState.OBSERVE);
            return;
        }

        // 寻路不可用：不归位，原地等待
        if (!FarmNav.available()) {
            idleReturnTicks = 0;
            lastIdleNotify = "";
            setState(FarmState.OBSERVE);
            return;
        }

        // 未在寻路：下发回中心点寻路（首次或被打断后重新归位）
        if (!FarmNav.pathing()) {
            idleReturnTicks = 0;
            if (!"returning".equals(lastIdleNotify)) {
                lastIdleNotify = "returning";
                logger.accept("§7正在回农场中心点等待...");
            }
            FarmNav.goTo(center, 1, true);
            setState(FarmState.OBSERVE);
            return;
        }

        // 正在归位寻路中：超时看门狗，避免无限寻路卡死
        if (++idleReturnTicks > CENTER_WAIT_TIMEOUT) {
            FarmNav.cancel();
            idleReturnTicks = 0;
            lastIdleNotify = "timeout";
            logger.accept("§e⚠ 回中心点超时 §8▸ 已原地等待");
        }
        setState(FarmState.OBSERVE);
    }

    /** 处理任务结束后的衔接与结果 */
    private void handleTaskResult(FarmTask task, TaskResult result) {
        // 收割成功 → 需要补种则接 PlantTask，否则接 CollectTask
        if (task instanceof HarvestTask harvest) {
            if (result.ok()) {
                // 收割坐标已破坏，立即从缓存移除，避免补种前被再次选中
                scanner.invalidate(harvest.target().pos());
                // 单个模式：补种 + 拾取
                CropProfile crop = harvest.target().profile();
                boolean willPlant = crop.needsReplant() && resources.countItem(crop.plantItem()) > 0;
                if (willPlant) {
                    BlockPos soil = harvest.target().pos().below();
                    currentTask = new PlantTask(FarmTarget.plant(crop, soil), verifier, decision.reachDistance());
                    setState(FarmState.PLANT);
                } else {
                    currentTask = new CollectTask(harvest.target().pos(), collectRange);
                    setState(FarmState.COLLECT);
                }
                return;
            }
            // 收割失败：导航不可用属系统级失败，目标失效只跳过当前目标
            scanner.invalidate(harvest.target().pos());
            setState(FarmState.OBSERVE);
            return;
        }

        // 批量瞬间收割：分波交错，收一波 → 补一波 → 捡一波 → 恢复继续下一波
        if (task instanceof BatchHarvestTask batch) {
            List<FarmTarget> harvested = batch.drainHarvested();
            for (FarmTarget t : harvested) scanner.invalidate(t.pos());
            // 本波完成但仍有剩余：保留任务实例，补种+拾取后恢复继续收下一波
            if (result == TaskResult.WAVE_DONE) {
                interleavedHarvest = batch;
            }
            startBatchReplant(harvested);
            return;
        }

        // 批量瞬间补种完成 → 失效已种坐标；若来自批量收割后的快速补种则继续统一拾取，否则回观察
        if (task instanceof BatchPlantTask batch) {
            for (FarmTarget t : batch.planted()) scanner.invalidate(t.pos());
            if (harvestReplantPhase) {
                harvestReplantPhase = false;
                startBatchCollect();
            } else {
                setState(FarmState.OBSERVE);
            }
            return;
        }

        // 补种完成 → 批量补种计划回观察接下一个；单个模式收割后补种则拾取掉落物
        if (task instanceof PlantTask plant) {
            scanner.invalidate(plant.target().pos());
            // 批量补种计划进行中：回观察，由 tick() 的批量补种计划分支接下一个空耕地
            if (batchPlantPlan != null) {
                setState(FarmState.OBSERVE);
                return;
            }
            // 单个模式收割后补种：拾取掉落物
            currentTask = new CollectTask(plant.target().pos().above(), collectRange);
            setState(FarmState.COLLECT);
            return;
        }

        // 锄地完成 → 批量锄地计划回观察接下一个；无锄头/导航失败丢弃整批计划
        if (task instanceof TillTask till) {
            scanner.invalidate(till.target().pos());
            tillActiveNotified = false;
            if (result == TaskResult.NAVIGATION_FAILED || result == TaskResult.RESOURCE_INSUFFICIENT) {
                batchTillPlan = null;
            }
            // 单目标锄地（无批量计划）在结束时播报结果；批量批次统一在计划耗尽处播报
            if (batchTillPlan == null) {
                if (result.ok()) {
                    logger.accept("§a✓ 锄地完成");
                } else if (result == TaskResult.RESOURCE_INSUFFICIENT) {
                    noHoeNotified = true;
                    logger.accept("§c✗ 没有锄头 §8▸ 已跳过锄地");
                } else if (result != TaskResult.TARGET_INVALID) {
                    logger.accept("§c✗ 锄地失败");
                }
            }
            setState(FarmState.OBSERVE);
            return;
        }

        // 拾取完成 → 若分波交错收割待恢复则继续下一波，否则重新观察
        if (task instanceof CollectTask) {
            if (interleavedHarvest != null) {
                currentTask = interleavedHarvest;
                interleavedHarvest = null;
                setState(FarmState.HARVEST);
            } else {
                setState(FarmState.OBSERVE);
            }
            return;
        }

        // 物流任务（卸货/补货/毒马铃薯）完成 → 播报结果并直接重新观察
        if (task.exclusive()) {
            if (task instanceof RestockTask restock && result == TaskResult.CONTAINER_EMPTY) {
                decision.suppressRestock(restock.crop());
                logger.accept("§e⚠ " + restock.crop().displayName() + "作物箱无货（可能被漏斗吸走） §8▸ 已跳过补货，先收菜");
            } else if (result.ok()) {
                logger.accept("§a✓ " + taskName(task) + "完成");
            } else {
                logger.accept("§c✗ " + taskName(task) + "失败");
            }
            // 直接重新观察：Scanner 扫描范围与玩家位置无关，无需先导航回农场中心，
            // 避免物流后长时间归位等待导致「不再继续收菜」。
            batchPlan = null;
            setState(FarmState.OBSERVE);
            return;
        }

        setState(FarmState.OBSERVE);
    }

    /** 物流任务的中文名，用于结果播报 */
    private String taskName(FarmTask task) {
        if (task instanceof UnloadTask) return "卸货";
        if (task instanceof RestockTask) return "补货";
        if (task instanceof PoisonDumpTask) return "杂物处理";
        return "物流";
    }

    /** 批量收割后的快速补种：把已收割目标里需要补种的交给 BatchPlantTask 瞬间补满，补完再统一拾取 */
    private void startBatchReplant(List<FarmTarget> harvested) {
        List<FarmTarget> toPlant = new ArrayList<>();
        for (FarmTarget target : harvested) {
            CropProfile crop = target.profile();
            if (crop.needsReplant()) {
                toPlant.add(FarmTarget.plant(crop, target.pos().below()));
            }
        }
        if (!toPlant.isEmpty()) {
            harvestReplantPhase = true;
            currentTask = new BatchPlantTask(toPlant, verifier, decision.reachDistance());
            setState(FarmState.PLANT);
        } else {
            harvestReplantPhase = false;
            startBatchCollect();
        }
    }

    /** 批量统一拾取：以农场范围中心为参考，覆盖整个农场拾取掉落物 */
    private void startBatchCollect() {
        BlockPos min = scanner.min();
        BlockPos max = scanner.max();
        BlockPos center = farmCenter();
        double range = Math.max(Math.max(max.getX() - min.getX(), max.getZ() - min.getZ()), 2) / 2.0 + 1;
        currentTask = new CollectTask(center, range);
        setState(FarmState.COLLECT);
    }

    private FarmState stateFor(FarmTask task) {
        if (task instanceof HarvestTask) return FarmState.HARVEST;
        if (task instanceof PlantTask) return FarmState.PLANT;
        if (task instanceof TillTask) return FarmState.TILL;
        if (task instanceof CollectTask) return FarmState.COLLECT;
        if (task instanceof UnloadTask) return FarmState.UNLOAD;
        if (task instanceof RestockTask) return FarmState.RESTOCK;
        if (task instanceof PoisonDumpTask) return FarmState.POISON_DUMP;
        return FarmState.OBSERVE;
    }

    private void setState(FarmState newState) {
        this.state = newState;
    }

    /** 模块关闭时清理全部状态，重新开启必须 Fresh Observe */
    public void reset() {
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }
        if (interleavedHarvest != null) {
            interleavedHarvest.cancel();
            interleavedHarvest = null;
        }
        batchPlan = null;
        batchPlantPlan = null;
        batchTillPlan = null;
        tillActiveNotified = false;
        noHoeNotified = false;
        idleReturnTicks = 0;
        lastIdleNotify = "";
        harvestReplantPhase = false;
        FarmNav.cancel();
        broker.reset();
        scanner.reset();
        state = FarmState.OBSERVE;
    }
}
