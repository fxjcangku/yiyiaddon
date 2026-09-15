package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.profile.WateringCanDefinition;
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator.Phase;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.MAX_RETRY;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.REFILL_BURST_UNKNOWN;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.REFILL_MAX_PACKETS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.REFILL_STABLE_LIMIT;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.SPRINKLER_BURST_PACKETS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.VIEW_ALIGN_TOLERANCE;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.VIEW_ALIGN_WAIT_TICKS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.VIEW_TURN_STEP;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.learningKey;

/**
 * 星露谷农场执行器：视角同步 / 批量右击 / 收割浇水播种施肥药剂 / 水壶补水 / 洒水器维护 / 主手切换。
 *
 * <p>本类由 {@link StardewCoordinator} 机械拆分而来，共享协调器的全部可变状态
 * （通过 {@code owner} 直接读写），行为与拆分前完全一致。</p>
 */
final class StardewFarmExecutor {

    private final StardewCoordinator owner;

    StardewFarmExecutor(StardewCoordinator owner) {
        this.owner = owner;
    }

    /**
     * 视角同步：动手前把视线转向当前交互目标。
     *
     * <p>本模组的交互全是静默发包（{@code interactBlock} / {@code useOnBlock}），只带命中坐标、
     * <b>不改玩家朝向</b>。状态机自己走过去、自己动手时朝向就是走路方向，于是看起来像背对着作物干活。
     * 收菜、浇水、播种、施肥、用药剂、清枯苗、补水、洒水器维护、补货 / 卸货 / 种子回收全走同一套逻辑。</p>
     *
     * <p>做法：每 tick 朝目标转一小步，转过 {@link #VIEW_ALIGN_TOLERANCE} 才发交互包，
     * 最多等 {@link #VIEW_ALIGN_WAIT_TICKS} tick 就放行——转头失败绝不阻塞任务。
     * 客户端改朝向会让原版在下一帧发出朝向包，服务器与其他玩家看到的朝向与本机一致。</p>
     *
     * <p>拾取不在此列：拾取靠走近磁吸、不主动发包，末段直走时已经朝着掉落物了
     * （见 {@link #nudgeTowardDrop}），这里不必再插一脚。</p>
     *
     * @return true 表示可以继续执行交互
     */
    boolean faceInteractionTarget() {
        BlockPos pos = interactionFacingTarget();
        Minecraft mc = Minecraft.getInstance();
        if (pos == null || mc.player == null) {
            owner.viewAlignWait = 0;
            return true;
        }
        float delta = Mth.wrapDegrees(yawTo(pos, mc) - mc.player.getYRot());
        if (Math.abs(delta) <= VIEW_ALIGN_TOLERANCE || ++owner.viewAlignWait > VIEW_ALIGN_WAIT_TICKS) {
            owner.viewAlignWait = 0;
            return true;
        }
        mc.player.setYRot(mc.player.getYRot() + Mth.clamp(delta, -VIEW_TURN_STEP, VIEW_TURN_STEP));
        // 俯仰一起跟：盆、箱子、水源都比眼睛低，只转 yaw 会显得在平视前方
        mc.player.setXRot(turnToward(mc.player.getXRot(), pitchTo(pos, mc), VIEW_TURN_STEP));
        return false;
    }

    /**
     * 当前任务真正要交互的坐标，与发包坐标保持一致。
     *
     * <p>收割 / 学习 / 清枯苗打的是盆上方那格作物本体，其余动作打盆、箱子、水源本体。</p>
     */
    private BlockPos interactionFacingTarget() {
        if (owner.taskType == null) return null;
        return switch (owner.taskType) {
            case HARVEST, LEARN_HARVEST, CLEAR_DEAD -> owner.targetPot == null ? null : owner.targetPot.above();
            case WATER, PLANT, FERTILIZE, POTION -> owner.targetPot;
            case REFILL -> {
                StardewPointManager.StardewPoint p = owner.points == null
                    ? null : owner.points.get(StardewPointType.WATER_SOURCE);
                yield p == null ? null : p.pos();
            }
            case SPRINKLER_CHECK, SPRINKLER_REFILL -> owner.planner.sprinklerTarget();
            case RESTOCK, SEED_RETURN, UNLOAD -> owner.targetContainer != null
                ? owner.targetContainer : (owner.containerApproach == null ? null : owner.containerApproach.interactPos());
            // 拾取靠走近磁吸、返回中心不交互，都不需要提前摆视角
            default -> null;
        };
    }

    /** 看向方块中心所需的 yaw（度） */
    private static float yawTo(BlockPos pos, Minecraft mc) {
        double dx = pos.getX() + 0.5 - mc.player.getX();
        double dz = pos.getZ() + 0.5 - mc.player.getZ();
        return (float) Math.toDegrees(Math.atan2(-dx, dz));
    }

    /** 看向方块中心所需的 pitch（度） */
    private static float pitchTo(BlockPos pos, Minecraft mc) {
        double dy = pos.getY() + 0.5 - (mc.player.getY() + mc.player.getEyeHeight());
        double dx = pos.getX() + 0.5 - mc.player.getX();
        double dz = pos.getZ() + 0.5 - mc.player.getZ();
        return (float) Math.toDegrees(-Math.atan2(dy, Math.hypot(dx, dz)));
    }

    /** 朝目标角度靠拢，单 tick 最多转 step 度；wrapDegrees 处理 ±180° 环绕 */
    private static float turnToward(float current, float target, float step) {
        return current + Mth.clamp(Mth.wrapDegrees(target - current), -step, step);
    }

    /**
     * 批量右击：主目标发包成功后，顺手把同一批里其它合格的格子也右键掉，省掉每格一遍完整状态机。
     *
     * <p><b>为什么只做右键：</b>收割 / 浇水 / 播种 / 施肥都是右键（{@code interactBlock} /
     * {@code useOnBlock}），服务端按命中坐标独立判定，互不干扰；<b>清理枯苗是左键破坏</b>，
     * 一律走单目标，绝不进批量，避免一次连发打坏一片。</p>
     *
     * <p><b>为什么不做学习探测：</b>{@code LEARN_HARVEST} 是「空手试探 + 观察证据」，必须一格格来，
     * 连发会把证据搅在一起，学习结论直接作废。</p>
     *
     * <p><b>筛选口径与决策完全一致：</b>复用 {@link #resolveTask}，只挑「同一任务类型、同一作物、
     * 同一盆类型、在交互距离内、未被导航退避」的格子；因此批量不会越过分区，也不会跨作物乱采。</p>
     *
     * <p><b>不做额外断言：</b>额外目标不参与 {@link #verifyResult()}，服务端若拒绝，下一轮观察
     * 会重新把它当待办，等于「最多白打一次」，不会出现假成功。</p>
     */
    void sendBatchActions() {
        if (owner.batchActions <= 1) return;
        if (owner.taskType != TaskType.HARVEST && owner.taskType != TaskType.WATER
            && owner.taskType != TaskType.PLANT && owner.taskType != TaskType.FERTILIZE) return;
        if (owner.targetPot == null || owner.activeCell == null) return;

        String cropKey = owner.activeCell.crop().cropKey();
        int remaining = owner.batchActions - 1;
        Set<BlockPos> sent = new HashSet<>();
        for (StardewFarmScanner.Cell cell : owner.pending) {
            if (remaining <= 0) return;
            BlockPos pot = cell.potPos();
            if (pot.equals(owner.targetPot) || !sent.add(pot)) continue;
            if (cropKey != null && !cropKey.equals(cell.crop().cropKey())) continue;
            if (owner.planner.resolveTask(cell) != owner.taskType) continue;
            if (owner.planner.isNavigationBlocked(owner.taskType, pot)) continue;
            BlockPos interactPos = batchInteractPos(pot);
            if (!withinInteractReach(interactPos)) continue;
            if (!sendBatchOne(interactPos)) return;
            remaining--;
        }
    }

    /** 收割打作物本体（盆上方那格），其余动作打盆本体。 */
    private BlockPos batchInteractPos(BlockPos pot) {
        return owner.taskType == TaskType.HARVEST ? pot.above() : pot;
    }

    private boolean sendBatchOne(BlockPos interactPos) {
        if (owner.taskType == TaskType.HARVEST) {
            InteractionHand hand = prepareHarvestHand();
            return hand != null && owner.adapter.interactBlock(hand, interactPos, Direction.UP);
        }
        return owner.adapter.useOnBlock(InteractionHand.MAIN_HAND, interactPos);
    }

    /**
     * 用真实眼位到格子中心的距离判定可达。
     *
     * <p>服务端也按同一距离校验，够不着的多发只会被静默拒绝（不会误伤别的方块），
     * 但白白多一个包没有意义，所以这里先挡掉。</p>
     */
    private boolean withinInteractReach(BlockPos interactPos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        double dx = interactPos.getX() + 0.5 - mc.player.getX();
        double dy = interactPos.getY() + 0.5 - (mc.player.getY() + mc.player.getEyeHeight());
        double dz = interactPos.getZ() + 0.5 - mc.player.getZ();
        double limit = owner.reach + 0.5;
        return dx * dx + dy * dy + dz * dz <= limit * limit;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  交互实现（收割 / 浇水 / 种植 / 施肥 / 药剂 / 补水 / 洒水器）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    boolean doHarvest() {
        if (owner.targetPot == null) return false;
        // 普通成熟作物统一右键采摘；重复结果植株会回退生长阶段，验证层据此保留原 cropKey。
        InteractionHand hand = prepareHarvestHand();
        return hand != null && owner.adapter.face(owner.targetPot.above())
            && owner.adapter.interactBlock(hand, owner.targetPot.above(), Direction.UP);
    }

    /**
     * 对候选阶段只发送一次空手右键，并在发包前冻结世界、库存与附近掉落实况。
     *
     * <p>探测失败不会重试，也绝不降级左键；后续只有观察到新的阶段身份才会再探测。</p>
     */
    boolean doLearnHarvest() {
        if (owner.targetPot == null || owner.activeCell == null || owner.activeCell.crop().cropKey() == null
            || owner.activeCell.crop().stageName() == null) return false;
        String key = learningKey(owner.activeCell.crop().cropKey(), owner.activeCell.crop().stageName());
        if (owner.probedLearningStages.contains(key)) return false;
        owner.activeCrop = owner.index.cropByKey(owner.activeCell.crop().cropKey());
        if (owner.activeCrop == null) return false;
        InteractionHand hand = prepareHarvestHand();
        if (hand == null) {
            owner.probedLearningStages.add(key);
            if (owner.reportedLearningFailures.add(key)) {
                owner.status.state("LEARN_FAIL:" + key, "收获学习未确认",
                    owner.activeCrop.chineseName() + "：没有可用空手，本会话不再试探该阶段");
            }
            return false;
        }
        owner.probedLearningStages.add(key);
        owner.learningCropKey = owner.activeCell.crop().cropKey();
        owner.learningMatureStage = owner.activeCell.crop().stageName();
        owner.learningBeforeIdentity = owner.activeCell.crop().modelIdentity();
        owner.learningBeforeInventory = owner.inventory.countSeed(owner.activeCrop) + owner.inventory.countProduce(owner.activeCrop);
        owner.learningBeforeDrops = owner.verifier.countNearbyCropDrops(owner.activeCrop, owner.targetPot.above());
        owner.learningInteractionSent = owner.adapter.face(owner.targetPot.above())
            && owner.adapter.interactBlock(hand, owner.targetPot.above(), Direction.UP);
        return owner.learningInteractionSent;
    }

    /** 死亡作物才允许左键清除，禁止与普通成熟采摘共用破坏动作。 */
    boolean doClearDead() {
        return owner.targetPot != null && owner.adapter.breakBlock(owner.targetPot.above(), Direction.UP);
    }

    boolean doWater() {
        if (owner.targetPot == null) return false;
        StardewToolDefinition can = preferredWateringCan();
        if (can == null) return false;
        if (owner.switchCan && !holdEntry(can)) return false;
        return owner.adapter.face(owner.targetPot)
            && owner.adapter.useOnBlock(InteractionHand.MAIN_HAND, owner.targetPot);
    }

    boolean doPlant() {
        if (owner.targetPot == null) return false;
        CropDefinition crop = owner.planner.cropOf(owner.targetPot);
        if (crop == null) return false;
        if (!holdSeed(crop)) return false;
        return owner.adapter.face(owner.targetPot)
            && owner.adapter.useOnBlock(InteractionHand.MAIN_HAND, owner.targetPot);
    }

    boolean doFertilize() {
        if (owner.targetPot == null) return false;
        StardewToolDefinition fertilizer = firstSelectedEntry(owner.selectedFertilizerKeys);
        if (fertilizer == null) return false;
        if (!holdEntry(fertilizer)) return false;
        return owner.adapter.face(owner.targetPot)
            && owner.adapter.useOnBlock(InteractionHand.MAIN_HAND, owner.targetPot);
    }

    boolean doPotion() {
        // 魔法药剂行为尚未建立可靠服务器规则，默认不执行危险猜测；开关开启时明确跳过。
        StardewToolDefinition potion = firstSelectedEntry(owner.selectedPotionKeys);
        if (potion == null) return false;
        owner.status.state("POTION_UNVERIFIED", "魔法药剂已跳过", "行为尚未通过真机确认");
        return false;
    }

    void interactRefill() {
        StardewPointManager.StardewPoint water = owner.points.get(StardewPointType.WATER_SOURCE);
        String failure = owner.points.validationFailure(StardewPointType.WATER_SOURCE, water, owner.index);
        if (failure != null) {
            owner.status.critical("WATER_POINT:" + failure, "补水点已失效", "已停止当前补水任务");
            owner.phase = Phase.REPLAN;
            return;
        }
        if (owner.taskStep == 0) {
            StardewToolDefinition can = preferredWateringCan();
            if (can == null) {
                owner.phase = Phase.REPLAN;
                return;
            }
            if (owner.switchCan && !holdEntry(can)) {
                owner.phase = Phase.REPLAN;
                return;
            }
            owner.refillLastWater = currentHeldWater();
            owner.refillCapacity = currentHeldCanCapacity();
            owner.refillAttempts = 0;
            owner.refillMadeProgress = false;
            owner.refillStableChecks = 0;
            owner.refillBurstSent = 0;
            owner.taskStep = 1;
            owner.taskTicks = 0;
        }
        owner.adapter.face(water.pos());
        // 容量读不到的常见原因是「壶刚切到主手，Tooltip / LORE 还没跟着更新」——每轮再试一次，
        // 一旦读出来就能按差额一次发完，不必再靠批量档位试探。
        if (owner.refillCapacity == null) owner.refillCapacity = currentHeldCanCapacity();
        Integer current = currentHeldWater();
        if (refillFull(current)) {
            // 已经满壶（读 Tooltip 就能确认）就别再发无意义的包
            finishRefillSuccess();
            return;
        }
        int budget = REFILL_MAX_PACKETS - owner.refillAttempts;
        if (budget <= 0) {
            // 补到总量上限仍未满：按「已尽力」收工，避免卡在补水环节
            finishRefillSuccess();
            return;
        }
        // 一次性补满：容量与当前水量都读得到时，按「差多少发多少」在同一 tick 里一次打出去。
        // 服务端会吞掉同 tick 里的个别交互（容量 10 时实测最多丢 1 包），验证阶段发现没满会自动补齐，
        // 所以不会漏；读不到容量时退回保守批量，靠验证阶段一点点逼近。
        int batch;
        if (owner.refillCapacity != null && owner.refillCapacity > 0 && current != null) {
            batch = Math.max(1, Math.min(budget, owner.refillCapacity - current));
        } else {
            batch = Math.min(budget, REFILL_BURST_UNKNOWN);
        }
        for (int i = 0; i < batch; i++) {
            owner.adapter.interactBlock(InteractionHand.MAIN_HAND, water.pos(), Direction.UP);
        }
        owner.refillBurstSent = batch;
        owner.refillAttempts += batch;
        owner.taskTicks = 0;
        owner.stepTick = 0;
        owner.phase = Phase.VERIFY;
    }

    /** 当前手持水壶的容量上限；Tooltip / LORE / custom_data 都读不到返回 null。 */
    private Integer currentHeldCanCapacity() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return null;
        return StardewInventoryService.readWaterCapacity(mc.player.getMainHandItem());
    }

    /**
     * 补水验证。
     *
     * <p>水量继续增长 → 继续发包；读到的水量追平容量 → 收工。服务端偶尔会吞掉某一 tick 的
     * 交互（见 {@link StardewCoordinator#REFILL_ROUND_PACKETS}），所以「已经有过增量、但这几轮没涨」
     * 不能立刻收工，要连续 {@link StardewCoordinator#REFILL_STABLE_LIMIT} 轮无增量才认定推不动；
     * 而「从头到尾一点没涨」仍按 {@link StardewCoordinator#MAX_RETRY} 快速止损并报错。</p>
     */
    void verifyRefill() {
        Integer after = currentHeldWater();
        if (after == null) {
            // 水量仍读不到：没有任何可判定的证据，按原口径收工
            finishRefillSuccess();
            return;
        }
        // 起手读不到水量（空壶服务端可能不带 water 字段）时基线按 0 算。
        // 原来这种情况会在第一轮验证就「无证据地宣布已补满」，壶只补了几格就回报「水壶已补满」。
        int before = owner.refillLastWater == null ? 0 : owner.refillLastWater;
        if (after > before) {
            learnRefillRates(before, after);
            owner.refillMadeProgress = true;
            owner.refillLastWater = after;
            owner.refillStableChecks = 0;
            if (refillFull(after)) {
                finishRefillSuccess();
                return;
            }
        } else if (refillFull(after)) {
            finishRefillSuccess();
            return;
        } else if (!owner.refillMadeProgress && owner.refillAttempts >= MAX_RETRY) {
            owner.forceRefill = true;
            owner.status.critical("REFILL_NO_PROGRESS", "水壶补水失败", "连续交互无水量变化，已停止本次任务");
            owner.phase = Phase.REPLAN;
            return;
        } else if (owner.refillMadeProgress && ++owner.refillStableChecks >= REFILL_STABLE_LIMIT) {
            // 已经有进展、但连续若干轮读不到增量：服务端在节流或这壶补不动了，停止无谓发包
            finishRefillSuccess();
            return;
        }
        if (owner.refillAttempts >= REFILL_MAX_PACKETS) {
            finishRefillSuccess();
            return;
        }
        // 开新的一轮：包数从 0 起算，学单包增量时分母才是本轮真实包数
        owner.refillBurstSent = 0;
        owner.stepTick = 0;
        owner.phase = Phase.INTERACT;
    }

    /** 用「本轮增量 ÷ 本轮包数」向上取整学到单包增量；容量只用实测值向上校正，不凭空捏造。 */
    private void learnRefillRates(int before, int after) {
        int delta = after - before;
        if (delta > 0 && owner.refillBurstSent > 0) {
            owner.refillPerPacket = Math.max(1, (int) Math.ceil(delta / (double) owner.refillBurstSent));
        }
        if (owner.refillCapacity != null && after > owner.refillCapacity) owner.refillCapacity = after;
    }

    /** 读到的容量已知且当前水量已经追平 → 无需再等两次无增长，直接收工。 */
    private boolean refillFull(Integer water) {
        return owner.refillCapacity != null && water != null && water >= owner.refillCapacity;
    }

    private void finishRefillSuccess() {
        owner.forceRefill = false;
        owner.verifier.notifyTaskSuccess();
        owner.phase = Phase.REPLAN;
    }

    void interactSprinkler() {
        BlockPos pos = owner.planner.sprinklerTarget();
        if (pos == null) {
            owner.phase = Phase.REPLAN;
            return;
        }
        StardewPointManager.StardewPoint saved = owner.points.getAll(StardewPointType.SPRINKLER).stream()
            .filter(p -> p.inCurrentDimension() && p.pos().equals(pos)).findFirst().orElse(null);
        String failure = owner.points.validationFailure(StardewPointType.SPRINKLER, saved, owner.index);
        if (failure != null) {
            owner.status.critical("SPRINKLER_POINT:" + failure, "洒水器点位失效", "已停止当前维护任务");
            owner.phase = Phase.REPLAN;
            return;
        }
        if (owner.taskStep == 0) {
            StardewToolDefinition can = preferredWateringCan();
            if (can != null && owner.switchCan) holdEntry(can);
            owner.taskStep = 1;
        }
        // 壶已经空了：先去补水点补满，回来继续灌这一台（光标不动，不会跳过它）。
        // 必须先判空再点击：空壶右键洒水器什么都不会发生，会被验证阶段误判成「已经满了」。
        if (canWaterIsEmpty()) {
            owner.taskType = TaskType.REFILL;
            owner.stepTick = 0;
            owner.phase = Phase.NAVIGATE;
            return;
        }
        owner.adapter.face(pos);
        // 先记下这一批之前的水量，再连发（服务端下一 tick 才会把新水量同步回来）
        owner.sprinklerWaterBefore = currentHeldWater();
        // 一次灌一批：同一 tick 连发多包。多发的包对已满的洒水器是空操作，不会浪费壶里的水；
        // 下一 tick 读壶水差额就知道还差多少，没满再补一批（判满仍然靠「壶水还掉不掉」）。
        for (int i = 0; i < SPRINKLER_BURST_PACKETS; i++) {
            owner.adapter.interactBlock(InteractionHand.MAIN_HAND, pos, Direction.UP);
        }
        owner.sprinklerBursts = pos.equals(owner.sprinklerPourTarget) ? owner.sprinklerBursts + 1 : 1;
        owner.sprinklerPourTarget = pos;
        owner.stepTick = 0;
        owner.phase = Phase.VERIFY;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  主手切换与恢复
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private boolean holdSeed(CropDefinition crop) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        int hotbar = owner.inventory.findSlotSeed(crop, true);
        if (hotbar >= 0) {
            saveHand();
            owner.adapter.selectHotbar(hotbar);
            return true;
        }
        int any = owner.inventory.findSlotSeed(crop, false);
        if (any >= 0) {
            saveHand();
            owner.swappedInvSlot = any;
            owner.adapter.swapToHotbar(any);
            return true;
        }
        return false;
    }

    private boolean holdEntry(StardewToolDefinition entry) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || entry == null) return false;
        int hotbar = owner.inventory.findSlotEntry(entry, true);
        if (hotbar >= 0) {
            saveHand();
            owner.adapter.selectHotbar(hotbar);
            return true;
        }
        int any = owner.inventory.findSlotEntry(entry, false);
        if (any >= 0) {
            saveHand();
            owner.swappedInvSlot = any;
            owner.adapter.swapToHotbar(any);
            return true;
        }
        return false;
    }

    /** 右键采摘优先使用空手，避免水壶或其它工具改变服务器对交互动作的解释。 */
    private InteractionHand prepareHarvestHand() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.getMainHandItem().isEmpty()) return InteractionHand.MAIN_HAND;
        for (int slot = 0; slot < 9; slot++) {
            if (!mc.player.getInventory().getItem(slot).isEmpty()) continue;
            saveHand();
            owner.adapter.selectHotbar(slot);
            return InteractionHand.MAIN_HAND;
        }
        return mc.player.getOffhandItem().isEmpty() ? InteractionHand.OFF_HAND : null;
    }

    private void saveHand() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        owner.savedSelectedSlot = mc.player.getInventory().getSelectedSlot();
        owner.swappedInvSlot = -1;
        owner.handSwapped = true;
    }

    void restoreHandNow() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        if (!owner.restoreHand) {
            owner.handSwapped = false;
            owner.savedSelectedSlot = -1;
            owner.swappedInvSlot = -1;
            return;
        }
        if (owner.handSwapped) {
            if (owner.swappedInvSlot >= 0) {
                owner.adapter.swapToHotbar(owner.swappedInvSlot);
            } else if (owner.savedSelectedSlot >= 0) {
                owner.adapter.selectHotbar(owner.savedSelectedSlot);
            }
        }
        owner.handSwapped = false;
        owner.savedSelectedSlot = -1;
        owner.swappedInvSlot = -1;
    }

    boolean canUsable() {
        Integer water = canWaterLevel();
        if (water == null) return true; // 水量未知：允许尝试一次浇水，不做无限循环
        return water > 0;
    }

    boolean canWaterIsEmpty() {
        Integer water = canWaterLevel();
        return water != null && water <= 0;
    }

    /** 读取已选水壶的真实水量；找不到水壶或读不到水量返回 null（保持 UNKNOWN） */
    private Integer canWaterLevel() {
        if (owner.selectedCanKeys.isEmpty()) return null;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return null;
        StardewToolDefinition can = preferredWateringCan();
        if (can == null) return null;
        int slot = owner.inventory.findSlotEntry(can, false);
        if (slot < 0) return null;
        return StardewInventoryService.readWater(mc.player.getInventory().getItem(slot));
    }

    Integer currentHeldWater() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return null;
        return StardewInventoryService.readWater(mc.player.getMainHandItem());
    }

    private StardewToolDefinition firstSelectedEntry(List<String> keys) {
        for (String key : keys) {
            StardewToolDefinition entry = owner.index.entryByKey(key);
            if (entry != null) return entry;
        }
        return null;
    }

    /** 多选水壶时优先使用背包中实际存在的最高等级，避免列表顺序把黄金水壶降级成白银水壶。 */
    private StardewToolDefinition preferredWateringCan() {
        WateringCanDefinition bestAvailable = null;
        WateringCanDefinition bestSelected = null;
        for (String key : owner.selectedCanKeys) {
            StardewToolDefinition entry = owner.index.entryByKey(key);
            if (!(entry instanceof WateringCanDefinition can)) continue;
            if (bestSelected == null || can.canIndex() > bestSelected.canIndex()) bestSelected = can;
            if (owner.inventory.findSlotEntry(can, false) >= 0
                && (bestAvailable == null || can.canIndex() > bestAvailable.canIndex())) bestAvailable = can;
        }
        return bestAvailable == null ? bestSelected : bestAvailable;
    }

    boolean hasSelectedFertilizer() {
        return !owner.selectedFertilizerKeys.isEmpty();
    }
}
