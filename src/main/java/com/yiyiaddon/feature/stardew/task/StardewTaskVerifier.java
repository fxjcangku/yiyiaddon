package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewCropLifecycle;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRule;
import com.yiyiaddon.feature.stardew.recognition.CropRecognizer;
import com.yiyiaddon.feature.stardew.recognition.CropState;
import com.yiyiaddon.feature.stardew.recognition.PotState;
import com.yiyiaddon.feature.stardew.recognition.StardewCropDisplayProbe;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator.LearnedHarvest;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator.RestockBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.CENTER_REACH;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.DEFAULT_VERIFY_TICKS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.LOGISTICS_BLOCK_RETRY_MS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.RESTOCK_SAFETY_RECHECK_MS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.learningKey;
import static com.yiyiaddon.feature.stardew.task.StardewFarmReporter.formatRemovedItems;

/**
 * 星露谷任务验证：世界/库存证据聚合、收获学习快照与成功播报。
 *
 * <p>本类由 {@link StardewCoordinator} 机械拆分而来，共享协调器的全部可变状态
 * （通过 {@code owner} 直接读写），行为与拆分前完全一致。</p>
 */
final class StardewTaskVerifier {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/stardew");

    private final StardewCoordinator owner;

    StardewTaskVerifier(StardewCoordinator owner) {
        this.owner = owner;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  验证实现
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    boolean verifyResult() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return true;
        return switch (owner.taskType) {
            case HARVEST -> {
                if (owner.targetPot == null) yield true;
                // 与识别、收获学习同源：方块优先 + 展示实体兜底。只读方块状态在「作物是展示实体 /
                // 盆上是隐形载体」的服务器上永远看不到收获后的变化。
                CropRecognizer.CropRecognition crop = CropRecognizer.recognizeAtPot(owner.targetPot, owner.profile);
                // 特殊变种（金色 / 巨型 / 变种）：手持金锄头收完回退到「植株」。判据只看「这一格不再是
                // 特殊变种阶段」——特殊阶段本来就不在成熟阶段表里，普通规则的 completeVerified /
                // afterHarvestStage 都不适用于它（比对回退阶段只会把成功判成失败，然后对着收干净的格子反复右键）。
                if (owner.activeCell != null && owner.activeCell.crop().state() == CropState.SPECIAL) {
                    yield crop.state() != CropState.SPECIAL && potStillPresent(owner.targetPot);
                }
                StardewHarvestRule rule = owner.activeCell == null ? null
                    : owner.harvestRuleResolver.apply(owner.activeCell.crop().cropKey());
                if (rule == null || !rule.completeVerified()) yield false;
                String expected = owner.activeCell.crop().cropKey();
                if (rule.lifecycle() == StardewCropLifecycle.ONE_SHOT) {
                    // 一次性作物：盆上不再是原来那株作物即算摘掉。绝不能只认「空气」——隐形载体服收获后
                    // 那一格仍然是方块（carrots / tripwire 的另一个状态），只认空气会每轮都判失败，
                    // 于是对着已经收干净的格子反复右键（实机反馈：收了还在那点）。
                    boolean stillThere = expected != null && expected.equals(crop.cropKey());
                    yield !stillThere && potStillPresent(owner.targetPot);
                }
                yield crop.state() == CropState.GROWING
                    && expected != null && expected.equals(crop.cropKey())
                    && rule.afterHarvestStage() != null && rule.afterHarvestStage().equals(crop.stageName());
            }
            case LEARN_HARVEST -> false; // 学习走独立证据聚合，禁止落入普通布尔验证。
            case CLEAR_DEAD, CLEAR_MISMATCH, CLEAR_JUNK -> {
                if (owner.targetPot == null) yield true;
                CropRecognizer.CropRecognition crop = CropRecognizer.recognize(mc.level.getBlockState(owner.targetPot.above()), owner.profile);
                // 三者的成功判据同一条：盆上方那格被清空（EMPTY）且盆本体还在——绝不把整盆破坏当成功。
                yield crop.state() == CropState.EMPTY && potStillPresent(owner.targetPot);
            }
            case WATER -> {
                if (owner.targetPot == null) yield true;
                yield CropRecognizer.recognizePot(mc.level.getBlockState(owner.targetPot)) == PotState.WET;
            }
            case PLANT -> {
                if (owner.targetPot == null) yield true;
                CropRecognizer.CropRecognition crop = CropRecognizer.recognize(mc.level.getBlockState(owner.targetPot.above()), owner.profile);
                yield crop.state() != CropState.EMPTY && crop.state() != CropState.UNKNOWN;
            }
            case FERTILIZE -> true; // 施肥是否生效以单盆肥料槽为准，V1 保守按交互完成处理
            case POTION -> true;
            case COLLECT -> !owner.drops.hasFarmDrop();
            case REFILL -> {
                // 补水需要循环到水量稳定，统一由 verifyRefill 处理。
                yield false;
            }
            case SPRINKLER_CHECK -> {
                // 「点一下读壶水」判据（本服实测得出）：
                //   壶水掉了 = 服务端收下了水，说明这台还没灌满 → 继续灌；
                //   壶水没掉 = 已经灌满（满的洒水器再点不扣水）→ 换下一台。
                // 读不到水量（没识别水壶 / 服务端不暴露水量）时退回旧口径：点一次就算完成。
                Integer before = owner.sprinklerWaterBefore;
                Integer now = owner.executor.currentHeldWater();
                boolean measured = before != null && now != null;
                if (measured && now < before
                    && owner.sprinklerBursts < StardewCoordinator.SPRINKLER_MAX_BURSTS) {
                    // 还在吃水：取消「已满」跳过窗口，恢复正常检查节奏
                    owner.markSprinklerConsuming(owner.planner.sprinklerTarget());
                    yield false;
                }
                // 这台到此为止：报一条状态提示，再推进到下一个洒水器
                // 「已灌满」只在读得到水量、且这一下确实没再减少时才说；点满兜底上限不冒充灌满。
                BlockPos finished = owner.planner.sprinklerTarget();
                boolean filledFull = measured && !(now < before);
                owner.reporter.announceSprinklerDone(finished, filledFull);
                if (filledFull) owner.markSprinklerFull(finished);
                owner.sprinklerCursor++;
                if (owner.sprinklerCursor >= owner.planner.sprinklerPointsInDimension().size()) {
                    owner.sprinklerCursor = 0;
                    owner.nextSprinklerCheckTick = System.currentTimeMillis() + owner.sprinklerInterval * 50L;
                    owner.sprinklerPourTarget = null;
                    owner.reporter.announceSprinklerRoundDone(owner.planner.sprinklerPointsInDimension().size());
                }
                yield true;
            }
            case SPRINKLER_REFILL -> true;
            case RESTOCK -> {
                // 补货以「真实库存达标或种子箱无更多可转」为成功，见 interactLogistics 收尾
                yield true;
            }
            case SEED_RETURN -> {
                // 回收以「背包种子降到保留量或种子箱无法再收」为成功，见 interactLogistics 收尾
                yield true;
            }
            case UNLOAD -> {
                yield owner.logisticsBlocked || owner.logistics.allUnloadComplete();
            }
            case RETURN_CENTER -> {
                BlockPos center = owner.planner.regionCenter();
                yield center == null || owner.adapter.arrived(center, CENTER_REACH);
            }
        };
    }

    /** 不同交互按服务端反馈成本设置验证等待；补水现在一轮连发多包，等待窗口可以短一档。 */
    int verifyDelayTicks() {
        return switch (owner.taskType) {
            case PLANT -> 4;
            case WATER -> 12;
            case REFILL -> 4;
            case LEARN_HARVEST -> 20;
            case COLLECT -> 4;
            default -> DEFAULT_VERIFY_TICKS;
        };
    }

    /**
     * 聚合收获后的世界、库存与地面掉落证据；只有生命周期可确定时才提交 VERIFIED。
     *
     * <p>世界变空直接证明一次性；同一作物身份切换到其它阶段证明保株。库存或掉落增加作为
     * 额外成功证据，但不能单独猜生命周期。右键无任何变化时只结束本次探测。</p>
     */
    void verifyLearningHarvest() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || owner.activeCrop == null || owner.targetPot == null
            || owner.learningCropKey == null || owner.learningMatureStage == null) return;
        // 展示实体通道的数据是扫描期间归档的，而整片农田扫一轮要好几秒。收获验证必须看「此刻」，
        // 否则会把刚摘掉的作物当成还在（或把还在的当成没了），学出错误的生命周期。
        StardewCropDisplayProbe.refreshAround(owner.targetPot);
        CropRecognizer.CropRecognition after = CropRecognizer.recognizeAtPot(owner.targetPot, owner.profile);
        boolean potRetained = potStillPresent(owner.targetPot);
        int inventoryAfter = owner.inventory.countSeed(owner.activeCrop) + owner.inventory.countProduce(owner.activeCrop);
        int dropsAfter = countNearbyCropDrops(owner.activeCrop, owner.targetPot.above());
        boolean itemEvidence = inventoryAfter > owner.learningBeforeInventory || dropsAfter > owner.learningBeforeDrops;

        // 展示实体作物（CraftEngine 系服务器）收获后是「整株消失」，而盆上方往往还留着一个永远不变的
        // 隐形载体方块（carrots / tripwire 之类）。那个载体的语义身份同样非空——用「身份串是不是 null」
        // 判「作物没了」在真机上永远不成立（实机事故：收菜成功、掉落物都捡了，却一直报「未发生可验证变化」）。
        // 判据只能是：这一格此刻还认不认得出原来那株作物。
        boolean stillSameCrop = owner.learningCropKey.equals(after.cropKey());

        StardewCropLifecycle lifecycle = StardewCropLifecycle.UNKNOWN;
        String afterStage = null;
        boolean worldEvidence = false;
        if (after.state() == CropState.EMPTY || !stillSameCrop) {
            lifecycle = StardewCropLifecycle.ONE_SHOT;
            worldEvidence = true;
        } else if (after.stageName() != null
            && !after.stageName().equals(owner.learningMatureStage)
            && !java.util.Objects.equals(after.modelIdentity(), owner.learningBeforeIdentity)) {
            lifecycle = StardewCropLifecycle.REGROW;
            afterStage = after.stageName();
            worldEvidence = true;
        }

        boolean confirmed = worldEvidence && potRetained && itemEvidence;

        // 学习结论直接决定后续能否自动收菜，任何一处判据出问题都要能远程复盘：整条证据链进日志。
        LOGGER.info("[星露谷] 收获学习验证 作物={} 成熟阶段={} 交互包已发={} | 收获前[身份={} 库存={} 掉落={}]"
                + " 收获后[状态={} 作物={} 阶段={} 身份={}] 盆保留={} 掉落={} 库存={} → 结论={}",
            owner.learningCropKey, owner.learningMatureStage, owner.learningInteractionSent,
            owner.learningBeforeIdentity, owner.learningBeforeInventory, owner.learningBeforeDrops,
            after.state(), after.cropKey(), after.stageName(), after.modelIdentity(),
            potRetained, dropsAfter, inventoryAfter,
            confirmed ? (lifecycle == StardewCropLifecycle.ONE_SHOT ? "一次性（已确认）" : "保株（已确认）")
                : "未确认：" + (!owner.learningInteractionSent ? "交互包未发送"
                    : !potRetained ? "盆未保留" : !worldEvidence ? "世界状态无变化" : "无掉落/库存证据"));

        if (confirmed) {
            owner.harvestLearningListener.accept(new LearnedHarvest(
                owner.learningCropKey, owner.learningMatureStage, lifecycle, afterStage));
            owner.reportedLearningFailures.remove(learningKey(owner.learningCropKey, owner.learningMatureStage));
        } else {
            String reason;
            if (!owner.learningInteractionSent) reason = "空手右键交互包未发送";
            else if (!potRetained) reason = "下方种植盆未保留，证据不满足安全学习条件";
            else if (!worldEvidence) reason = "成熟植株状态未发生可验证变化";
            else reason = "未观察到对应掉落物或库存增加";
            String key = learningKey(owner.learningCropKey, owner.learningMatureStage);
            if (owner.reportedLearningFailures.add(key)) {
                owner.status.state("LEARN_FAIL:" + key, "收获学习未确认",
                    owner.activeCrop.chineseName() + "：" + reason + "，本会话不再试探该阶段");
            }
        }
    }

    /** DEAD 清理和 ONE_SHOT 收获都必须验证真实盆仍存在，绝不把整盆破坏当成功。 */
    private boolean potStillPresent(BlockPos potPos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || potPos == null) return false;
        PotState state = CropRecognizer.recognizePot(mc.level.getBlockState(potPos));
        return state == PotState.DRY || state == PotState.WET;
    }

    /** 明确记录箱子耗尽或无可用槽位，结束本次事务并给出一次可读提示。 */
    void markLogisticsBlocked() {
        markLogisticsBlocked(null);
    }

    /** 交互链路失败与箱内确实无物品分别提示，便于实机区分而不保留调试埋点。 */
    void markLogisticsBlocked(String failure) {
        if (owner.activeCrop == null) return;
        owner.logisticsBlocked = true;
        if (owner.taskType == TaskType.RESTOCK) {
            owner.restockBlocks.put(owner.activeCrop.cropKey(), new RestockBlock(
                System.currentTimeMillis() + RESTOCK_SAFETY_RECHECK_MS,
                owner.inventory.countSeed(owner.activeCrop), owner.targetContainer));
            owner.lastBlockedSeedCounts.put(owner.activeCrop.cropKey(), owner.inventory.countSeed(owner.activeCrop));
            if (failure == null) {
                owner.status.state("SEED_EMPTY:" + owner.activeCrop.cropKey(), "缺少" + owner.activeCrop.seedDisplayName(),
                    "该作物播种已暂停，其他任务继续");
            } else {
                owner.status.critical("RESTOCK_FAIL:" + owner.activeCrop.cropKey() + ':' + failure,
                    "种子补货失败", failure + "，已停止循环开箱");
            }
        } else {
            long until = System.currentTimeMillis() + LOGISTICS_BLOCK_RETRY_MS;
            if (owner.taskType == TaskType.SEED_RETURN) {
                owner.seedReturnBlockedUntil.put(owner.activeCrop.cropKey(), until);
                if (failure == null) {
                    // 箱子放不下才会走到这里：说明种子箱已满，需要玩家处理，不能静默吞掉
                    owner.status.state("SEED_BOX_FULL:" + owner.activeCrop.cropKey(), "种子箱已满",
                        "已暂停回收" + owner.activeCrop.seedDisplayName());
                } else {
                    owner.status.critical("SEED_RETURN_FAIL:" + owner.activeCrop.cropKey() + ':' + failure,
                        "种子回收失败", failure + "，已停止循环开箱");
                }
            } else {
                owner.unloadBlockedUntil.put(owner.activeCrop.cropKey(), until);
                if (failure == null) {
                    owner.status.state("OUTPUT_FULL:" + owner.activeCrop.cropKey(), "产物箱没有可用空间",
                        "已结束本次卸货，其他任务继续");
                } else {
                    owner.status.critical("UNLOAD_FAIL:" + owner.activeCrop.cropKey() + ':' + failure,
                        "产物卸货失败", failure + "，已停止循环开箱");
                }
            }
        }
    }

    /** 统计目标附近属于当前作物的种子/产物掉落数量，排除农田其它格的并发收获噪声。 */
    int countNearbyCropDrops(CropDefinition crop, BlockPos center) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || crop == null || center == null) return 0;
        int total = 0;
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (!(entity instanceof ItemEntity item) || item.distanceToSqr(
                center.getX() + 0.5, center.getY() + 0.5, center.getZ() + 0.5) > 36.0) continue;
            ItemStack stack = item.getItem();
            if (owner.inventory.matchesSeed(stack, crop) || owner.inventory.matchesProduce(stack, crop)) {
                total += stack.getCount();
            }
        }
        return total;
    }

    /** 清理单次学习快照；已探测阶段集合保留到模块重启，承担防刷包职责。 */
    void clearLearningSnapshot() {
        owner.learningCropKey = null;
        owner.learningMatureStage = null;
        owner.learningBeforeIdentity = null;
        owner.learningBeforeInventory = 0;
        owner.learningBeforeDrops = 0;
        owner.learningInteractionSent = false;
    }

    /** 只对玩家明确需要的关键完成事件播报一次，普通逐格任务保持安静。 */
    void notifyTaskSuccess() {
        if (owner.logisticsBlocked && (owner.taskType == TaskType.RESTOCK || owner.taskType == TaskType.UNLOAD)) return;
        if (owner.taskType == TaskType.REFILL) {
            Integer after = owner.executor.currentHeldWater();
            owner.status.state("REFILL_DONE:" + String.valueOf(after), "水壶已补满",
                after == null ? "继续浇水" : "当前水量 " + after + "，继续浇水");
        } else if (owner.taskType == TaskType.CLEAR_DEAD) {
            owner.status.state("DEAD_DONE:" + owner.targetPot, "枯死作物已清除", "已重新检查农田");
        } else if (owner.taskType == TaskType.CLEAR_MISMATCH) {
            owner.status.state("MISMATCH_DONE:" + owner.targetPot, "错位作物已清除", "空出的盆按本区域作物补种");
        } else if (owner.taskType == TaskType.CLEAR_JUNK) {
            // 杂物清理全程静默：不清盆上那格就没法补种，属于常规保洁，不占聊天栏
            owner.status.silent("JUNK_DONE:" + owner.targetPot, "盆上杂物已清除", "这一格可以补种了", "");
        } else if (owner.taskType == TaskType.PLANT && owner.activeCrop != null) {
            owner.status.silent("PLANT_DONE:" + owner.activeCrop.cropKey(), "播种完成", owner.activeCrop.chineseName() + " ×1", "");
        } else if (owner.taskType == TaskType.HARVEST && owner.activeCrop != null) {
            owner.status.silent("HARVEST_DONE:" + owner.activeCrop.cropKey(), "收获完成", owner.activeCrop.chineseName(), "");
        } else if (owner.taskType == TaskType.COLLECT) {
            int seeds = owner.reporter.totalSelectedSeeds();
            int produce = owner.reporter.totalSelectedProduce();
            owner.status.state("COLLECT_DONE:" + seeds + ':' + produce, "拾取完成",
                "产物 ×" + Math.max(0, produce - owner.taskProduceBefore),
                "种子 ×" + Math.max(0, seeds - owner.taskSeedBefore));
        } else if (owner.taskType == TaskType.RESTOCK && owner.activeCrop != null) {
            // 基准必须与右边同口径：左边数的是「这一种作物的种子」，右边就得用
            // taskCropSeedBefore（开始补货时这一种作物的种子数），不能用 taskSeedBefore
            // ——后者是「全部已选作物的种子总数」。两者相减：只要别的已选作物也占着种子，
            // 差额就会被抹掉甚至为负，实机表现就是「明明拿了种子却报 ×0」。
            int moved = Math.max(0, owner.inventory.countSeed(owner.activeCrop) - owner.taskCropSeedBefore);
            owner.status.state("RESTOCK_DONE:" + owner.activeCrop.cropKey() + ':' + moved, "补货完成",
                owner.activeCrop.seedDisplayName() + " ×" + moved);
        } else if (owner.taskType == TaskType.UNLOAD && owner.activeCrop != null) {
            String moved = formatRemovedItems(owner.taskInventoryBefore, owner.reporter.countAllUnloadableByDisplay());
            int retained = owner.reporter.totalSelectedSeeds();
            String retain = retained > 0 ? "种子保留 ×" + retained : "";
            owner.status.state("UNLOAD_DONE:" + owner.activeCrop.cropKey() + ':' + moved + ':' + retained,
                "卸货完成", moved.isBlank() ? "未转移产物" : moved, retain);
        } else if (owner.taskType == TaskType.SEED_RETURN && owner.activeCrop != null) {
            int keep = owner.planner.seedReturnKeep(owner.activeCrop);
            int remaining = owner.inventory.countSeed(owner.activeCrop);
            int moved = Math.max(0, owner.taskCropSeedBefore - remaining);
            String keepText = keep > 0 ? "×" + remaining : "";
            String task = "种子回收完成";
            String detail = owner.activeCrop.seedDisplayName() + " ×" + moved;
            // 保株作物每次收获都会补一次回收，逐次播报会刷屏：每种作物本次会话只播第一次，其余只更新配置页
            if (owner.announcedSeedReturns.add(owner.activeCrop.cropKey())) {
                owner.status.state("SEED_RETURN_DONE:" + owner.activeCrop.cropKey() + ':' + moved, task, detail, keepText);
            } else {
                owner.status.silent("SEED_RETURN_DONE:" + owner.activeCrop.cropKey() + ':' + moved, task, detail, keepText);
            }
        } else if (owner.taskType == TaskType.RETURN_CENTER) {
            owner.status.state("RETURN_DONE", "后勤任务完成", "已返回农田");
        }
    }
}
