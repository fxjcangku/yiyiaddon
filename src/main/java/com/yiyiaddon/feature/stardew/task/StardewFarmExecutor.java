package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.feature.stardew.navigation.ContainerApproachPlanner;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.profile.WateringCanDefinition;
import com.yiyiaddon.feature.stardew.recognition.CropRecognizer;
import com.yiyiaddon.feature.stardew.recognition.CropState;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.recognition.StardewCropDisplayProbe;
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator.Phase;
import com.yiyiaddon.platform.container.ContainerAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.CONTAINER_STAND_REACH;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.MAX_RETRY;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.MATERIAL_FETCH_RETRY_MS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.REFILL_BURST_UNKNOWN;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.REFILL_MAX_PACKETS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.REFILL_STABLE_LIMIT;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.SPRINKLER_BURST_PACKETS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.VIEW_ALIGN_TOLERANCE;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.VIEW_ALIGN_WAIT_TICKS;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.VIEW_TURN_STEP;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.learningKey;

/**
 * 星露谷农场执行器：视角同步 / 批量动作（右击 / 破坏）/ 收割浇水播种施肥药剂 / 水壶补水 / 洒水器维护 / 主手切换。
 *
 * <p>本类由 {@link StardewCoordinator} 机械拆分而来，共享协调器的全部可变状态
 * （通过 {@code owner} 直接读写），行为与拆分前完全一致。</p>
 */
final class StardewFarmExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/stardew");

    private static final String NO_FREE_HAND_TASK = "收割需要一只空手";
    private static final String NO_FREE_HAND_DETAIL =
        "热键栏 9 格、副手、背包空格全都占着，腾不出一只空手：清一个热键栏或背包空格";

    private final StardewCoordinator owner;
    /** 本轮「没有空手」是否已经播报过聊天（拿到手就清零，见 {@link #prepareHarvestHand()}） */
    private boolean noFreeHandNotified;
    /** 副手物品暂放在哪个背包槽（-1 = 没借过）；见 {@link #parkOffhand(Minecraft, String, String)} */
    private int offhandParkedSlot = -1;
    /** 暂放的那件东西（换回时用来核对那一格还是它） */
    private ItemStack offhandParkedItem;
    /**
     * 当前工具 / 种子在哪只手上：副手放的壶 / 种子 / 肥料一律走副手动作，其余走主手。
     *
     * <p>由 {@link #holdEntry}/{@link #holdSeed} 设置，动作与读数（壶水量 / 容量）都读它，
     * 任务结束时在 {@link #restoreHandNow()} 复位——这样「副手拿壶浇水」和「主手拿壶浇水」
     * 走的是同一套状态机，不在每个动作点上各判一次手。</p>
     */
    private InteractionHand activeHand = InteractionHand.MAIN_HAND;

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
            case HARVEST, LEARN_HARVEST, CLEAR_DEAD, CLEAR_MISMATCH, CLEAR_JUNK ->
                owner.targetPot == null ? null : owner.targetPot.above();
            case WATER, PLANT, FERTILIZE, POTION -> owner.targetPot;
            case REFILL -> {
                // 物料盆（下界 / 末地）的 REFILL 是去岩浆箱 / 龙息箱取料，视角必须对准箱子；
                // 沿用「看向水源」会让脚本对着补水点转头，箱子那边根本没人看（取料必然失败）。
                if (StardewPointType.materialBoxFor(targetPotGroup()) != null) {
                    yield owner.containerApproach != null ? owner.containerApproach.interactPos() : owner.targetContainer;
                }
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
     * 批量动作：主目标发包成功后，顺手把同一批里其它合格的格子也打掉，省掉每格一遍完整状态机。
     *
     * <p><b>两种批量：</b>收割 / 浇水 / 播种 / 施肥是右键（{@code interactBlock} / {@code useOnBlock}）；
     * 清枯苗 / 清错位 / 清杂物是左键破坏（{@code START_DESTROY_BLOCK + STOP}，这类方块硬度为 0，一包即毁）。
     * 两者都只在同一 tick 连发若干包，服务端按命中坐标独立判定，互不干扰；破坏始终只打作物本体
     * （盆上方那格），绝不碰盆。</p>
     *
     * <p><b>为什么不做学习探测：</b>{@code LEARN_HARVEST} 是「空手试探 + 观察证据」，必须一格格来，
     * 连发会把证据搅在一起，学习结论直接作废。</p>
     *
     * <p><b>筛选口径与决策完全一致：</b>复用 {@link #resolveTask}，只挑「同一任务类型、同一作物
     * （破坏不按作物匹配：枯苗 / 错位本就是要把这一格清掉）、同一区域、在交互距离内、未被导航退避」
     * 的格子；因此批量不会越过分区，也不会跨作物乱采、乱种。</p>
     *
     * <p><b>不做额外断言：</b>额外目标不参与 {@link #verifyResult()}，服务端若拒绝，下一轮观察
     * 会重新把它当待办，等于「最多白打一次」，不会出现假成功。</p>
     */
    void sendBatchActions() {
        if (owner.batchActions <= 1) return;
        if (owner.targetPot == null || owner.activeCell == null) return;
        boolean breaking = owner.taskType == TaskType.CLEAR_DEAD || owner.taskType == TaskType.CLEAR_MISMATCH
            || owner.taskType == TaskType.CLEAR_JUNK;
        if (!breaking && owner.taskType != TaskType.HARVEST && owner.taskType != TaskType.WATER
            && owner.taskType != TaskType.PLANT && owner.taskType != TaskType.FERTILIZE) return;
        // 物料盆（下界 / 末地）不参与批量：一次右键就把手上那一桶岩浆 / 那一个龙息消耗掉了，
        // 第二发手上已经不是物料（变成空桶 / 玻璃瓶），服务端只会白收一个包。
        // 水壶能连发是因为它是同一件工具、只减水量，物品本身不变。
        if (owner.taskType == TaskType.WATER && targetPotGroup().refillItem() != null) return;

        String cropKey = owner.activeCell.crop().cropKey();
        // 批量不得跨区域：空盆的作物键是 null，上面那道「同作物」判断对播种完全不起作用，
        // 拿着同一颗种子连发就会把种子种到隔壁区域（实机反馈的 13 处错位就是这条）。
        CropDefinition batchTarget = owner.planner.cropOf(owner.targetPot);
        if (batchTarget == null) return;
        int remaining = owner.batchActions - 1;
        Set<BlockPos> sent = new HashSet<>();
        for (StardewFarmScanner.Cell cell : owner.pending) {
            if (remaining <= 0) return;
            BlockPos pot = cell.potPos();
            if (pot.equals(owner.targetPot) || !sent.add(pot)) continue;
            if (!breaking && cropKey != null && !cropKey.equals(cell.crop().cropKey())) continue;
            CropDefinition want = owner.planner.cropOf(pot);
            if (want == null || !batchTarget.cropKey().equals(want.cropKey())) continue;
            if (owner.planner.resolveTask(cell) != owner.taskType) continue;
            if (owner.planner.isNavigationBlocked(owner.taskType, pot)) continue;
            BlockPos interactPos = breaking ? pot.above() : batchInteractPos(pot);
            if (!withinInteractReach(interactPos)) continue;
            remaining--;
            // 破坏的返回 false 只代表「那一格快照已过期（方块已是空气）」，跳过它继续清下一格；
            // 右键发不出包说明这次批量不再可靠，直接中止。
            if (!sendBatchOne(interactPos, breaking) && !breaking) return;
        }
    }

    /** 收割打作物本体（盆上方那格），其余动作打盆本体。 */
    private BlockPos batchInteractPos(BlockPos pot) {
        return owner.taskType == TaskType.HARVEST ? pot.above() : pot;
    }

    private boolean sendBatchOne(BlockPos interactPos, boolean breaking) {
        if (breaking) return owner.adapter.breakBlock(interactPos, Direction.UP);
        if (owner.taskType == TaskType.HARVEST) {
            InteractionHand hand = prepareHarvestHand();
            return hand != null && owner.adapter.interactBlock(hand, interactPos, Direction.UP);
        }
        return owner.adapter.useOnBlock(activeHand, interactPos);
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
        // 特殊变种（金色 / 巨型 / 变种）走「手持金锄头右键」，普通成熟 / 保株作物保持空手右键。
        if (isSpecialTarget()) return doSpecialHarvest();
        // 普通成熟作物统一右键采摘；重复结果植株会回退生长阶段，验证层据此保留原 cropKey。
        InteractionHand hand = prepareHarvestHand();
        if (hand == null) return false;
        boolean sent = owner.adapter.face(owner.targetPot.above())
            && owner.adapter.interactBlock(hand, owner.targetPot.above(), Direction.UP);
        // 收不动只有两种可能：作物其实不在方块里（展示实体服要右键实体），或者右键包发不出去。
        // 这条日志把「该格此刻是什么」直接写清楚，不用再靠猜。
        LOGGER.info("[星露谷] 收割交互 手={} 目标={} {} 发包={}",
            hand == InteractionHand.OFF_HAND ? "副手" : "主手", owner.targetPot.above(),
            cellSummary(owner.targetPot.above()), sent);
        return sent;
    }

    /**
     * 特殊变种（金色 / 巨型 / 变种）的收割工具：本服实测口径是「原版金锄头右键」，收完回退植株。
     *
     * <p>不写进 {@code StardewHarvestRule}：它就是一个原版物品，既没有 {@code item_model} 身份也没有
     * ID 绑定，而且所有特殊阶段共用同一个动作（用户实机确认：「金锄头就是普通原版物品，其他特殊都是
     * 右键一起做了」）。因此只按物品本体取手，不引入新的规则字段与服务端绑定。</p>
     */
    private static Item specialHarvestTool() {
        return Items.GOLDEN_HOE;
    }

    /** 背包 / 副手里有没有特殊变种收割工具；没有就绝不空手乱点（空手点特殊阶段收不掉，只会白跑一轮）。 */
    boolean hasSpecialHarvestTool() {
        return owner.inventory != null && owner.inventory.findSlotItem(specialHarvestTool(), false) >= 0;
    }

    /** 当前这一格是不是特殊变种——取派发时的扫描快照，与验证层同源。 */
    private boolean isSpecialTarget() {
        return owner.activeCell != null && owner.activeCell.crop() != null
            && owner.activeCell.crop().state() == CropState.SPECIAL;
    }

    /**
     * 特殊变种收割：手持金锄头对作物格右键。
     *
     * <p>它和普通作物是同一条 {@code HARVEST} 流程，这里只负责「换手 + 右键」——验证与后续扫描完全复用
     * 现成机制；手在任务结束（{@code replan}）或模块停机时由 {@link #restoreHandNow()} 复位。</p>
     */
    private boolean doSpecialHarvest() {
        if (!holdItem(specialHarvestTool())) {
            // 派发前已经确认背包里有金锄头，走到这里只可能是任务进行中被拿走：播一条、本轮放弃。
            owner.status.state("NO_SPECIAL_TOOL", "特殊作物需要金锄头", "背包里找不到金锄头，已跳过");
            return false;
        }
        boolean sent = owner.adapter.face(owner.targetPot.above())
            && owner.adapter.interactBlock(activeHand, owner.targetPot.above(), Direction.UP);
        LOGGER.info("[星露谷] 特殊变种收割交互 手={} 工具={} 目标={} {} 发包={}",
            activeHand == InteractionHand.OFF_HAND ? "副手" : "主手", specialHarvestTool(),
            owner.targetPot.above(), cellSummary(owner.targetPot.above()), sent);
        return sent;
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
        LOGGER.info("[星露谷] 收获试探交互 手={} 目标={} {} 发包={}",
            hand == InteractionHand.OFF_HAND ? "副手" : "主手", owner.targetPot.above(),
            cellSummary(owner.targetPot.above()), owner.learningInteractionSent);
        return owner.learningInteractionSent;
    }

    /**
     * 作物格「此刻真实构成」摘要：方块 ID + 该格实体（展示实体附带物品模型）。
     *
     * <p>「右键收不动 / 收获学习拿不到证据」只能靠它定位：作物到底是方块还是展示实体、该右键方块还是
     * 交互实体，一看日志就清楚，不必再靠猜。</p>
     */
    private static String cellSummary(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || pos == null) return "无";
        var blockId = net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(mc.level.getBlockState(pos).getBlock());
        StringBuilder out = new StringBuilder("方块 ").append(blockId == null ? "未知" : blockId);
        int count = 0;
        for (Entity entity : mc.level.getEntities((Entity) null, new AABB(pos).inflate(0.5))) {
            out.append(count++ == 0 ? " · 实体 " : "、");
            var typeId = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            out.append(typeId == null ? "未知" : typeId);
            String model = StardewCropDisplayProbe.modelOf(entity);
            if (model != null) out.append('(').append(model).append(')');
        }
        return count == 0 ? out.append(" · 该格无实体").toString() : out.toString();
    }

    /**
     * 左键破坏盆上方那格（清枯苗 / 清错位 / 清杂物共用；只打盆上方那格、绝不打盆本体）。
     *
     * <p>主目标走这里，同一批里余下的枯苗 / 错位 / 杂物由 {@link #sendBatchActions()} 在同一 tick 连发破坏包。</p>
     */
    boolean doBreakPlant() {
        return owner.targetPot != null && owner.adapter.breakBlock(owner.targetPot.above(), Direction.UP);
    }

    /**
     * 不自动切换物品（关掉「自动切换水壶」）时也要认副手：壶在副手就用副手动手，其余照旧用主手。
     *
     * <p>本方法<b>不移动任何物品</b>，只决定这个交互包用哪只手（服务端按包里的手取物品）。
     * 少了这一步，{@code activeHand} 会留着上一个动作的手，副手拿壶时浇水会静默失效。</p>
     */
    private void resolveHandFor(StardewToolDefinition entry) {
        boolean offhand = owner.inventory.findSlotEntry(entry, true) < 0
            && owner.inventory.findSlotEntry(entry, false) == StardewInventoryService.OFFHAND_SLOT;
        activeHand = offhand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
    }

    /**
     * 补水：按<b>盆型</b>分派物料，三种盆型互斥。
     *
     * <ul>
     *   <li>{@link PotGroup#NORMAL} 普通盆 → 走水壶链路（原有行为，未动）；</li>
     *   <li>{@link PotGroup#NETHER} 下界盆 → 手持<b>熔岩桶</b>右键盆；</li>
     *   <li>{@link PotGroup#END} 末地盆 → 手持<b>龙息</b>右键盆。</li>
     * </ul>
     *
     * <p>下界 / 末地盆不用水壶：它们的「水」是岩浆与龙息，水壶倒进去没有意义。</p>
     */
    boolean doWater() {
        if (owner.targetPot == null) return false;
        PotGroup group = targetPotGroup();
        if (group.refillItem() != null) return useMaterialOnPot(group);
        if (blockedByMissingCan()) return false;
        StardewToolDefinition can = preferredWateringCan();
        if (can == null) return false;
        if (owner.switchCan) {
            if (!holdEntry(can)) return false;
        } else {
            resolveHandFor(can);
        }
        return owner.adapter.face(owner.targetPot)
            && owner.adapter.useOnBlock(activeHand, owner.targetPot);
    }

    /**
     * 当前目标盆的盆型组；认不出目标盆（坐标丢失 / 世界未加载）时按普通盆处理。
     *
     * <p>现读方块状态而不是沿用扫描快照：扫描结果可能已经过期（盆被换掉 / 玩家换了维度），
     * 而补水正是一次会消耗背包物料的动作，宁可多读一次方块状态。</p>
     */
    PotGroup targetPotGroup() {
        Minecraft mc = Minecraft.getInstance();
        if (owner.targetPot == null || mc.level == null) return PotGroup.NORMAL;
        return PotGroup.ofPotKey(CropRecognizer.recognizePotDetailed(mc.level.getBlockState(owner.targetPot)).potKey());
    }

    /** 下界盆 / 末地盆补水：手持对应物料直接右键盆（熔岩桶 +3 返空桶，龙息 +1 返玻璃瓶，返还是服务端行为）。 */
    private boolean useMaterialOnPot(PotGroup group) {
        if (!holdItem(group.refillItem())) {
            owner.phase = Phase.REPLAN;
            owner.status.critical("POT_MATERIAL:" + group.name(),
                group.displayName() + "缺少" + group.materialName(),
                "背包里没有" + group.materialName() + "，已停止当前补水任务");
            return false;
        }
        return owner.adapter.face(owner.targetPot)
            && owner.adapter.useOnBlock(activeHand, owner.targetPot);
    }

    /**
     * 下界盆 / 末地盆的补料事务：去岩浆箱 / 龙息箱取一批料，顺手把用完的空容器放回去。
     *
     * <p>与水壶补水的根本区别：水壶要「灌满」，需要读水量、算差额、反复试探；料是<b>成品物品</b>，
     * 取到目标数量就结束，所以这里只有一个搬运循环，没有水量试探。</p>
     *
     * <p>取的量按盆型定（岩浆 3 桶、龙息 10 个），用完再来一趟——一趟多取只会占背包，
     * 这两种料都不堆叠。</p>
     */
    private void interactMaterialRefill() {
        PotGroup group = targetPotGroup();
        StardewPointType boxType = StardewPointType.materialBoxFor(group);
        if (boxType == null || group.refillItem() == null) {
            owner.phase = Phase.REPLAN;
            return;
        }
        String failure = owner.points.validationFailure(boxType, owner.points.get(boxType), owner.index);
        if (failure != null) {
            // 「世界数据还没同步」不等于点位失效：走过去的一瞬间区块 / 方块实体可能刚到，
            // 这时候报「点位失效 · 已暂停」纯属虚惊，改为普通状态 + 重规划，下一轮自然复核。
            if (StardewPointManager.isWorldPending(failure)) {
                owner.status.state("MATERIAL_BOX_PENDING:" + boxType,
                    boxType.title() + " 数据尚未同步", "稍后自动复核");
            } else {
                owner.status.critical("MATERIAL_BOX:" + boxType + ':' + failure,
                    boxType.title() + "点位失效", "已暂停当前补料任务");
            }
            owner.phase = Phase.REPLAN;
            return;
        }
        switch (owner.taskStep) {
            case 0 -> {
                if (owner.containerApproach == null || !ContainerApproachPlanner.readyToInteract(
                    owner.containerApproach, CONTAINER_STAND_REACH, owner.reach)) {
                    // 「已到达站位」但「够不到箱子」时，这里与 NAVIGATE 之间会原地弹跳、永不报错。
                    // 给它一个上限：站定 5 秒还够不到，就换一个外围站位重规划，并说清是站位问题。
                    if (++owner.taskTicks > 100) {
                        owner.taskTicks = 0;
                        BlockPos stand = owner.containerApproach == null
                            ? null : owner.containerApproach.standPos();
                        String key = "MATERIAL_STAND:" + boxType.name();
                        if (owner.reportedContainerFailures.add(key)) {
                            owner.status.critical(key, boxType.title() + "站位够不到箱子",
                                "已换一个外围站位重试");
                        }
                        owner.planner.blockNavigationTarget(TaskType.REFILL, stand);
                        owner.phase = Phase.REPLAN;
                        return;
                    }
                    owner.phase = Phase.NAVIGATE;
                    return;
                }
                owner.broker.reset();
                BlockPos interactPos = owner.containerApproach.interactPos();
                if (!owner.adapter.face(interactPos)
                    || !owner.adapter.interactBlock(InteractionHand.MAIN_HAND, interactPos, Direction.UP)) {
                    owner.retryCount++;
                    if (owner.retryCount >= MAX_RETRY) {
                        owner.status.critical("MATERIAL_BOX_OPEN:" + boxType,
                            boxType.title() + "打开失败", "已暂停当前补料任务");
                        owner.phase = Phase.REPLAN;
                    }
                    return;
                }
                owner.taskTicks = 0;
                owner.taskStep = 1;
            }
            case 1 -> {
                owner.broker.tick();
                owner.taskTicks++;
                if (owner.broker.isReady()) {
                    owner.taskStep = 2;
                    owner.taskTicks = 0;
                } else if (owner.taskTicks > 40) {
                    // 菜单迟迟没同步：关掉重来，连续失败才停机（与后勤同一套退避）
                    ContainerAccess.closeContainer();
                    owner.broker.reset();
                    owner.retryCount++;
                    if (owner.retryCount >= MAX_RETRY) {
                        owner.status.critical("MATERIAL_BOX_SYNC:" + boxType,
                            boxType.title() + "菜单未同步", "已暂停当前补料任务");
                        owner.phase = Phase.REPLAN;
                    } else {
                        owner.taskStep = 0;
                        owner.taskTicks = 0;
                    }
                }
            }
            case 2 -> {
                // 先取够一批；取够后把空桶 / 玻璃瓶存回同一个箱子（一步一格，tick 之间推进）
                int remaining = group.refillBatch() - owner.inventory.countItem(group.refillItem());
                boolean moved = remaining > 0 && owner.logistics.withdrawItemOne(group.refillItem(), remaining);
                if (!moved && group.emptyItem() != null && owner.inventory.countItem(group.emptyItem()) > 0) {
                    moved = owner.logistics.depositItemOne(group.emptyItem());
                }
                if (moved) {
                    owner.taskTicks = 0;
                    // 这趟确实取到料了：清掉「箱空」记录，下次再空还能再报一次
                    owner.reportedContainerFailures.remove("MATERIAL_EMPTY:" + boxType.name());
                } else if (remaining > 0 && owner.inventory.countItem(group.refillItem()) <= 0) {
                    // 手里一个料都没有、箱子也给不出来 = 箱子空了。这里必须退避：箱子里没料时脚本
                    // 唯一的判断手段就是「开箱看一眼」，不退避就会在「开箱 → 取不到 → 关箱 → 重规划
                    // → 开箱」之间原地空转，玩家看到的是站着不动。退避期内安静待命，补了料自动继续。
                    ContainerAccess.closeContainer();
                    owner.broker.reset();
                    String key = "MATERIAL_EMPTY:" + boxType.name();
                    if (owner.reportedContainerFailures.add(key)) {
                        owner.status.critical(key, boxType.title() + "里没有" + group.materialName(),
                            "往箱子里补" + group.materialName() + "后会自动继续");
                    }
                    owner.materialFetchBlockedUntil = System.currentTimeMillis() + MATERIAL_FETCH_RETRY_MS;
                    owner.phase = Phase.REPLAN;
                    owner.stepTick = 0;
                } else {
                    owner.taskStep = 3;
                    owner.taskTicks = 0;
                }
            }
            case 3 -> {
                ContainerAccess.closeContainer();
                owner.broker.reset();
                // 取完立刻回到规划：下一轮手里有料，就会对那口盆发出 WATER
                owner.phase = Phase.REPLAN;
                owner.stepTick = 0;
            }
            default -> owner.phase = Phase.REPLAN;
        }
    }

    /**
     * 水壶不见了（快捷栏 / 背包 / 副手都没有）→ 播报并停机，返回 true 表示本次动作已被拦下。
     *
     * <p><b>为什么必须拦</b>（实机反馈：壶放副手或挪走后「脚本以为补满了水，就以为完成了」）：
     * 旧口径里「读不到水量」= 水位未知 = 允许继续，于是找不到壶时补水直接判定收工、浇水静默失败，
     * 玩家只看到「已完成」。这里把「壶在不在」先判掉：不在就停机报错，让玩家知道是壶的问题。
     * 壶在、只是这个资源包没写 water 字段的情况完全不受影响（仍然允许试一次）。</p>
     */
    private boolean blockedByMissingCan() {
        if (owner.selectedCanKeys.isEmpty() || hasWateringCan()) return false;
        owner.phase = Phase.REPLAN;
        owner.stopForMissingCan();
        return true;
    }

    /** 已选水壶是否存在（快捷栏 / 背包 / 副手任意一处） */
    boolean hasWateringCan() {
        for (String key : owner.selectedCanKeys) {
            StardewToolDefinition entry = owner.index.entryByKey(key);
            if (entry instanceof WateringCanDefinition can && owner.inventory.findSlotEntry(can, false) >= 0) {
                return true;
            }
        }
        return false;
    }

    boolean doPlant() {
        if (owner.targetPot == null) return false;
        CropDefinition crop = owner.planner.cropOf(owner.targetPot);
        if (crop == null) return false;
        if (!holdSeed(crop)) return false;
        return owner.adapter.face(owner.targetPot)
            && owner.adapter.useOnBlock(activeHand, owner.targetPot);
    }

    boolean doFertilize() {
        if (owner.targetPot == null) return false;
        StardewToolDefinition fertilizer = firstSelectedEntry(owner.selectedFertilizerKeys);
        if (fertilizer == null) return false;
        if (!holdEntry(fertilizer)) return false;
        return owner.adapter.face(owner.targetPot)
            && owner.adapter.useOnBlock(activeHand, owner.targetPot);
    }

    boolean doPotion() {
        // 魔法药剂行为尚未建立可靠服务器规则，默认不执行危险猜测；开关开启时明确跳过。
        StardewToolDefinition potion = firstSelectedEntry(owner.selectedPotionKeys);
        if (potion == null) return false;
        owner.status.state("POTION_UNVERIFIED", "魔法药剂已跳过", "行为尚未通过真机确认");
        return false;
    }

    void interactRefill() {
        // 下界盆 / 末地盆的「补水」是去物料箱取岩浆 / 龙息，与水壶灌水是两条完全不同的链路
        if (targetPotGroup().refillItem() != null) {
            interactMaterialRefill();
            return;
        }
        if (blockedByMissingCan()) return;
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
            if (owner.switchCan) {
                if (!holdEntry(can)) {
                    owner.phase = Phase.REPLAN;
                    return;
                }
            } else {
                resolveHandFor(can);
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
            owner.adapter.interactBlock(activeHand, water.pos(), Direction.UP);
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
        return StardewInventoryService.readWaterCapacity(mc.player.getItemInHand(activeHand));
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
            // 壶没了：不能按「水量未知」收工，否则就是实机那个「以为补满了」
            if (blockedByMissingCan()) return;
            // 水量仍读不到（壶在，只是这套资源包没写 water 字段）：没有任何可判定的证据，按原口径收工
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
        if (blockedByMissingCan()) return;
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
            if (can != null) {
                if (owner.switchCan) holdEntry(can);
                else resolveHandFor(can);
            }
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
            owner.adapter.interactBlock(activeHand, pos, Direction.UP);
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
            activeHand = InteractionHand.MAIN_HAND;
            owner.adapter.selectHotbar(hotbar);
            return true;
        }
        int any = owner.inventory.findSlotSeed(crop, false);
        if (any < 0) return false;
        if (any == StardewInventoryService.OFFHAND_SLOT) return holdOffhand();
        saveHand();
        activeHand = InteractionHand.MAIN_HAND;
        owner.swappedInvSlot = any;
        owner.adapter.swapToHotbar(any);
        return true;
    }

    private boolean holdEntry(StardewToolDefinition entry) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || entry == null) return false;
        int hotbar = owner.inventory.findSlotEntry(entry, true);
        if (hotbar >= 0) {
            saveHand();
            activeHand = InteractionHand.MAIN_HAND;
            owner.adapter.selectHotbar(hotbar);
            return true;
        }
        int any = owner.inventory.findSlotEntry(entry, false);
        if (any < 0) return false;
        if (any == StardewInventoryService.OFFHAND_SLOT) return holdOffhand();
        saveHand();
        activeHand = InteractionHand.MAIN_HAND;
        owner.swappedInvSlot = any;
        owner.adapter.swapToHotbar(any);
        return true;
    }

    /**
     * 切到指定<b>原版物品</b>（熔岩桶 / 龙息）：与 {@link #holdEntry} 同一套换手流程。
     *
     * <p>之所以另开一条路：这两种物料既没有 {@code item_model} 组件、也没有 ID 配置身份，
     * 用 {@code StardewToolDefinition} 表达不出来，只能按物品本体找槽位。</p>
     */
    private boolean holdItem(Item item) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || item == null) return false;
        int hotbar = owner.inventory.findSlotItem(item, true);
        if (hotbar >= 0) {
            saveHand();
            activeHand = InteractionHand.MAIN_HAND;
            owner.adapter.selectHotbar(hotbar);
            return true;
        }
        int any = owner.inventory.findSlotItem(item, false);
        if (any < 0) return false;
        if (any == StardewInventoryService.OFFHAND_SLOT) return holdOffhand();
        saveHand();
        activeHand = InteractionHand.MAIN_HAND;
        owner.swappedInvSlot = any;
        owner.adapter.swapToHotbar(any);
        return true;
    }

    /**
     * 工具 / 种子本来就在副手：什么都不用挪，直接用副手动作。
     *
     * <p>与「切快捷栏」和「背包换到主手」两条路不同，这条路既不切槽也不换格子，所以
     * <b>不登记 handSwapped</b>（没有东西要还原）。服务端按包里的手取物品，副手那件照常生效
     * ——与收割用副手是同一条机制。</p>
     */
    private boolean holdOffhand() {
        activeHand = InteractionHand.OFF_HAND;
        return true;
    }

    /**
     * 收割要「一只手空着」（空手右键：拿工具 / 食物会改变服务端对这次交互的解释）：主手空 → 直接用主手；
     * 主手有东西 → 切到一个空热键栏槽；9 格全满 → 退到副手；副手也占着 → 把副手物品暂放到背包空格，
     * 用副手收割，收完由 {@link #restoreHandNow()} 放回。
     *
     * <p><b>最后那一步是本项目对旧实现唯一的改动</b>（实机反馈：「我要副手也不影响收菜」）。旧实现
     * （{@code StardewCoordinator.prepareHarvestHand()}）到「副手也占着」就直接返回 {@code null}——
     * 热键栏 9 格全满时拿着东西站田里，就永远收不了菜，而且一声不响。现在改成借用原版「F 键换副手」
     * 的同一个动作，把副手物品暂放到背包空格（优先靠后的空格，尽量不占用模块自己要放的格子），
     * 腾出一只空手照常收割；物品在本次任务结束（{@code replan}）或模块停机时放回。</p>
     *
     * <p><b>为什么只借副手、不动主手</b>：主手那件东西正在被模块用（种子 / 水壶 / 工具），动它等于
     * 打乱播种与补水节奏；副手对模块没用，借它最便宜，一次任务最多两次点击包。背包也一个空格都
     * 没有时才真正放弃（那时连卸货都做不了）。</p>
     *
     * <p><b>为什么要记日志</b>（实机反馈：一直显示「正在收获」，地里却一点动静都没有）：
     * 取手这条链路原本一声不响，玩家根本无从知道是「没手可用」。日志只在<strong>非平凡情形</strong>
     * 下打——副手占着东西、借用副手、或干脆没有空手——正常「主手本来就空」的情况不打，免得刷屏。</p>
     */
    private InteractionHand prepareHarvestHand() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return InteractionHand.MAIN_HAND;
        if (mc.player.getMainHandItem().isEmpty()) {
            noFreeHandNotified = false;
            return InteractionHand.MAIN_HAND;
        }
        String main = itemLabel(mc.player.getMainHandItem());
        String off = itemLabel(mc.player.getOffhandItem());
        for (int slot = 0; slot < 9; slot++) {
            if (!mc.player.getInventory().getItem(slot).isEmpty()) continue;
            saveHand();
            owner.adapter.selectHotbar(slot);
            noFreeHandNotified = false;
            if (offhandBusy(mc)) {
                LOGGER.info("[星露谷] 收割取手：切到空热键栏槽 {}（主手={} 副手={}）", slot + 1, main, off);
            }
            return InteractionHand.MAIN_HAND;
        }
        // 已经借过副手（本任务内）：服务站点的副手此刻是空的，直接用它。
        // 客户端那一格要等服务器回包才更新，所以不能只靠「副手是不是空的」来判。
        if (mc.player.getOffhandItem().isEmpty() || offhandParkedSlot >= 0) {
            noFreeHandNotified = false;
            return InteractionHand.OFF_HAND;
        }
        if (parkOffhand(mc, main, off)) return InteractionHand.OFF_HAND;
        LOGGER.info("[星露谷] 收割取手：没有空手 —— 热键栏 9 格全满、副手有物品且背包没有空格"
            + "（主手={} 副手={}），本次放弃", main, off);
        // 每次尝试都真的没有手，但聊天只播一次：状态键会被别的播报顶掉，靠去重键压不住，会按秒刷屏。
        // 状态行照旧每轮更新（silent），玩家随时能在配置页看到当前结论。
        if (noFreeHandNotified) {
            owner.status.silent("NO_FREE_HAND", NO_FREE_HAND_TASK, NO_FREE_HAND_DETAIL, "");
        } else {
            noFreeHandNotified = true;
            owner.status.state("NO_FREE_HAND", NO_FREE_HAND_TASK, NO_FREE_HAND_DETAIL);
        }
        return null;
    }

    /**
     * 把副手物品暂放到背包空格，腾出一只空手（原版「F 键换副手」的同一个动作）。
     *
     * <p>槽位从后往前找：模块自己的播种 / 卸货运货都往靠前的空格堆，借靠后的格子最不容易打架。
     * 目标格在放回前不允许再被借用（{@code offhandParkedSlot} 非负即视为「这台机器已经腾过手了」）。</p>
     *
     * @return true 表示副手已腾空（可以用副手收割）
     */
    private boolean parkOffhand(Minecraft mc, String main, String off) {
        int free = lastFreeBackpackSlot(mc);
        if (free < 0) return false;
        ItemStack parked = mc.player.getOffhandItem().copy();
        if (!owner.adapter.swapOffhandWith(free)) return false;
        offhandParkedSlot = free;
        offhandParkedItem = parked;
        noFreeHandNotified = false;
        LOGGER.info("[星露谷] 收割取手：副手物品 {} 暂放到物品栏槽 {}（背包区），改用副手收割（收完放回；主手={}）",
            off, free + 1, main);
        return true;
    }

    /** 靠后的空闲背包槽（9~35）；没有则 -1 */
    private int lastFreeBackpackSlot(Minecraft mc) {
        if (mc.player == null) return -1;
        for (int slot = 35; slot >= 9; slot--) {
            if (mc.player.getInventory().getItem(slot).isEmpty()) return slot;
        }
        return -1;
    }

    /**
     * 把暂放的副手物品换回副手（本次任务结束 / 模块停机时调用）。
     *
     * <p>只在那一格确实还放着当初挪过去的那件东西时才换回：玩家中途把别的东西放进去了，
     * 就不要动它，否则会把不相干的东西换到副手上。放不回时记一条日志，东西留在背包里不丢。</p>
     */
    private void restoreParkedOffhand() {
        if (offhandParkedSlot < 0) return;
        int slot = offhandParkedSlot;
        ItemStack parked = offhandParkedItem;
        offhandParkedSlot = -1;
        offhandParkedItem = null;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || parked == null) return;
        ItemStack now = mc.player.getInventory().getItem(slot);
        if (now.isEmpty() || !ItemStack.isSameItemSameComponents(now, parked)) {
            LOGGER.info("[星露谷] 副手物品未换回：物品栏槽 {} 已不是当初暂放的东西，留在背包里", slot + 1);
            return;
        }
        if (owner.adapter.swapOffhandWith(slot)) {
            LOGGER.info("[星露谷] 副手物品已换回（原暂放在物品栏槽 {}）", slot + 1);
        }
    }

    /** 副手是否占着东西（只有它非空时才值得记日志：那正是实机反馈里收不了菜的场景） */
    private static boolean offhandBusy(Minecraft mc) {
        return mc.player != null && !mc.player.getOffhandItem().isEmpty();
    }

    /** 物品名（日志用）；空栈回「空」 */
    private static String itemLabel(ItemStack stack) {
        return stack.isEmpty() ? "空" : stack.getHoverName().getString();
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
        // 借来的副手物品一定要还，与「是否恢复主手」这个开关无关；副手取用状态也一起复位。
        restoreParkedOffhand();
        activeHand = InteractionHand.MAIN_HAND;
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
        return StardewInventoryService.readWater(StardewInventoryService.stackAt(slot));
    }

    Integer currentHeldWater() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return null;
        return StardewInventoryService.readWater(mc.player.getItemInHand(activeHand));
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
