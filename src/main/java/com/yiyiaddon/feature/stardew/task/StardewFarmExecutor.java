package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.feature.stardew.navigation.ContainerApproachPlanner;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestAction;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestRecipe;
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
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.block.state.BlockState;
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
    /** 主手物品暂放在哪个背包槽（-1 = 没借过）；见 {@link #parkMainHand(Minecraft, int, String)} */
    private int mainHandParkedSlot = -1;
    /** 暂放主手物品时选中的快捷栏槽（换回前要先切回它，见 {@link #restoreParkedMainHand()}） */
    private int mainHandParkedSelected = -1;
    /** 暂放的那件主手东西（换回时用来核对那一格还是它） */
    private ItemStack mainHandParkedItem;
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
        boolean breaking = owner.taskType.breaksPlant();
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
        if (breaking) return breakInstantIfSafe(interactPos);
        if (owner.taskType == TaskType.HARVEST) {
            InteractionHand hand = prepareHarvestHand();
            return hand != null && owner.adapter.interactBlock(hand, interactPos, Direction.UP);
        }
        return owner.adapter.useOnBlock(activeHand, interactPos);
    }

    /**
     * 批量里的破坏只打「零硬度载体」。
     *
     * <p>非零硬度的格子必须走完整的「START → 等够 tick → STOP」，同 tick 连发只会被服务端按 0.7
     * 阈值驳回，还会因为本地置空气而看起来「假成功」。这里跳过它（返回 true = 不中止本次批量），
     * 下一轮它会作为主目标重新派发，走完整破坏流程。</p>
     */
    private boolean breakInstantIfSafe(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return true;
        // 有一格正在走完整挖掘会话：这一批绝不能再发 START —— 服务端只记一个 destroyPos，
        // 秒破会把刚登记好的会话顶掉，那一格的 STOP 随即落空（实机：会话走满 4 轮、按住
        // 120 tick 仍纹丝不动，而同批的零硬度格子每 tick 都在抢 destroyPos）。
        // 返回 true = 不中止本次批量，等会话收尾后这些格子下一轮照常处理。
        if (owner.breakDigPos != null) return true;
        BlockState state = mc.level.getBlockState(pos);
        if (state.isAir()) return true;
        float delta = Math.max(state.getDestroyProgress(mc.player, mc.level, pos), 1.0E-4F);
        if (requiredDigTicks(delta, SPECIAL_BREAK_THRESHOLD) != 0) return true;
        return owner.adapter.breakBlock(pos, Direction.UP);
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
        // 这条日志把「哪只手、两只手各拿着什么、该格此刻是什么」直接写清楚，不用再靠猜
        // （实机取证 2026-09-21：手=副手连发 40 多包零效果，手=主手一次就收掉，区别就在这里）。
        LOGGER.info("[星露谷] 收割交互 手={} 手持={} 另一手={} 目标={} {} 发包={}",
            hand == InteractionHand.OFF_HAND ? "副手" : "主手",
            itemLabel(handItem(hand)), itemLabel(itemLabelOtherHand(hand)),
            owner.targetPot.above(), cellSummary(owner.targetPot.above()), sent);
        return sent;
    }

    /** 指定那只手上的物品（日志用）；没有玩家时回空栈，绝不抛异常 */
    private static ItemStack handItem(InteractionHand hand) {
        Minecraft mc = Minecraft.getInstance();
        return mc.player == null ? ItemStack.EMPTY : mc.player.getItemInHand(hand);
    }

    /** 另一只手（相对本次动作那只手）上的物品，日志用 */
    private static ItemStack itemLabelOtherHand(InteractionHand hand) {
        return handItem(hand == InteractionHand.OFF_HAND ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
    }

    /**
     * 本服口径要求的特殊收割工具在不在背包（含副手）里。
     *
     * <p>口径「工具不限」时永远为真：有工具就拿工具挖、没有就拿任何东西（含空手）破坏
     * （用户 2026-09-22：「不用特殊工具破坏的服务器作物，有工具就拿工具挖，没有工具用任何东西破坏都可以」）。</p>
     */
    boolean hasSpecialHarvestToolFor(CropRecognizer.CropRecognition crop) {
        StardewSpecialHarvestRecipe recipe = owner.specialHarvestRecipe(crop);
        return recipe.toolUnlimited() || findToolSlot(recipe) >= 0;
    }

    /** 找口径要求的那件工具所在槽位（主背包 0~35 + 副手）；找不到返回 -1 */
    private int findToolSlot(StardewSpecialHarvestRecipe recipe) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return -1;
        for (int slot = 0; slot < StardewInventoryService.MAIN_SLOTS; slot++) {
            if (recipe.matchesTool(mc.player.getInventory().getItem(slot))) return slot;
        }
        return recipe.matchesTool(mc.player.getOffhandItem()) ? StardewInventoryService.OFFHAND_SLOT : -1;
    }

    /** 把口径要求的那件工具换到手（与 {@link #holdItem} 同一套换手流程）；找不到返回 false */
    private boolean holdTool(StardewSpecialHarvestRecipe recipe) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        int slot = findToolSlot(recipe);
        if (slot < 0) return false;
        if (slot == StardewInventoryService.OFFHAND_SLOT) return holdOffhand();
        if (slot < 9) {
            saveHand();
            activeHand = InteractionHand.MAIN_HAND;
            owner.adapter.selectHotbar(slot);
            return true;
        }
        saveHand();
        activeHand = InteractionHand.MAIN_HAND;
        owner.swappedInvSlot = slot;
        owner.adapter.swapToHotbar(slot);
        return true;
    }

    /** 当前这一格是不是特殊变种——取派发时的扫描快照，与验证层同源。 */
    private boolean isSpecialTarget() {
        return owner.activeCell != null && owner.activeCell.crop() != null
            && owner.activeCell.crop().state() == CropState.SPECIAL;
    }

    /**
     * 特殊变种收割：按<b>本服学到的口径</b>分发 —— 动作（右键 / 左键破坏）× 手持（不限 / 指定那件）。
     *
     * <p>它和普通作物是同一条 {@code HARVEST} 流程，这里只负责「换手 + 动作」——验证与后续扫描完全复用
     * 现成机制；手在任务结束（{@code replan}）或模块停机时由 {@link #restoreHandNow()} 复位。</p>
     *
     * <p><b>口径要求的那件工具不在背包时</b>：只播一条提示并跳过这一格 —— 不动别的格子、不停机、
     * 也不拿别的工具硬试（拿错工具在别人服上可能把作物砸坏），拿到那件后下一轮扫描自动接着收
     * （用户 2026-09-22：「如果背包没有破坏当前的巨型作物工具，提示一下就好了，不影响收割其他的菜」）。</p>
     */
    private boolean doSpecialHarvest() {
        if (owner.activeCell == null || owner.activeCell.crop() == null) return false;
        String cropKey = owner.activeCell.crop().cropKey();
        StardewSpecialHarvestRecipe recipe = owner.specialHarvestRecipe(owner.activeCell.crop());
        if (!recipe.toolUnlimited() && !holdTool(recipe)) {
            owner.status.state("NO_SPECIAL_TOOL", "特殊作物缺少专用工具",
                owner.reporter.cropLabel(cropKey) + " ▸ 本服口径：" + recipe.displayName() + "，背包/副手里没有"
                    + recipe.toolDisplayName() + "｜这一格先跳过，不影响收其他作物，拿到后自动继续");
            return false;
        }
        if (recipe.action() == StardewSpecialHarvestAction.BREAK) {
            // 口径指定了工具时：这一格破坏期间不许换手（服务端认「拿的就是那件」），
            // 由 ensureDigTool 读到 specialToolHold 后放弃自动挑工具；工具不限则清空、照常挑最快的
            owner.specialToolHold = recipe.toolUnlimited() ? null : recipe;
            return owner.targetPot != null && breakAt(owner.targetPot.above());
        }
        owner.specialToolHold = null;
        boolean sent = owner.adapter.face(owner.targetPot.above())
            && owner.adapter.interactBlock(activeHand, owner.targetPot.above(), Direction.UP);
        LOGGER.info("[星露谷] 特殊变种收割交互 手={} 口径={} 手持={} 目标={} {} 发包={}",
            activeHand == InteractionHand.OFF_HAND ? "副手" : "主手", recipe.displayName(),
            itemLabel(handItem(activeHand)), owner.targetPot.above(), cellSummary(owner.targetPot.above()), sent);
        return sent;
    }

    /**
     * 服务端接受 STOP 所需的破坏进度阈值（原版 0.7）。
     *
     * <p>与开发习惯第 169 条「同源计算只留一份」的说明：秒破（{@code PacketInstantBreakModule}）
     * 与自动挖矿的秒破控制器各自实现了同一份公式，本处按同一口径再实现一次，是为了不动那两个
     * 模块的成熟链路；公式本身（{@code delta × (已挖 tick + 1) ≥ 阈值}）与它们逐字一致。</p>
     */
    private static final float SPECIAL_BREAK_THRESHOLD = 0.7F;

    /**
     * 对一格执行「破坏」：**一律走「START → 等够 tick → STOP」的完整会话，放不放行交给服务端**。
     *
     * <p><b>为什么不按客户端算出的硬度分两条路</b>（用户 2026-09-22 实机）：
     * 以前「客户端算出零硬度」就走同 tick {@code START + STOP} 秒破，那条通道的前提是
     * 「客户端认定的硬度 == 服务端认定的硬度」。这台服的死作物在客户端算是 0 硬度（绊线承载、
     * 模型被资源包换掉），服务端却要按住若干刻才放行 —— 秒破被一律驳回、方块一直在，
     * 于是每轮重扫又重新派发，聊天栏反复「发现枯死作物 · 正在清理」而田里纹丝不动。
     * 现在真零硬度（绊线 / 农作物）与要按住的方块走<b>同一条</b>会话流程：
     * 前者服务端收到 START 就销毁、客户端下一 tick 见到空气即成功（只多 1 tick），
     * 后者按估算按住再 STOP，估算离谱时 {@link #holdTicksFor} 退回固定时长并逐轮翻倍。
     * 一句话：<b>模块不猜服务端要多久，只负责按到底并读结果</b> —— 这样换服务器不必重新适配。</p>
     *
     * <p>破坏前一律先换手拿工具（`ensureDigTool`）：服务端是在 STOP（或 START 即判定）那一刻按
     * **手上那件**算进度的，客户端算出的一包即毁与它无关 —— 这服的死作物空手要按住二十几刻、
     * 拿效率工具服务端收到 START 就销毁（用户 2026-09-22：「可以挖了 但是没有用包里的效率」）。</p>
     *
     * <p>挖掘序列的推进<b>不依赖本方法被反复调用</b>：真正的推进在 {@link #tickBreakDig()}，
     * 由协调器每客户端 tick 调用一次。这样即使中途换了任务 / 重扫农田 / 相位跳转，
     * START 之后也一定会等到 STOP 或 ABORT，不会把一次 START 永远悬在服务端
     * （实机事故：日志里只有「开始挖掘」，之后再无「结束挖掘」，方块永远挖不掉）。</p>
     */
    private boolean breakAt(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || pos == null) return false;
        // 有一格正在走完整挖掘会话：本方法只服务这一格，别的格子一律等它收尾。
        // **为什么必须在最前面**：服务端只记一个 destroyPos，会话期间的任何其它方块动作包
        // （零硬度秒破的 START+STOP、换目标的 ABORT）都会把它顶掉，于是那一格的 STOP 永远落空
        // —— 实机表现就是同一格「开始挖掘 → 结束挖掘」走满 4 轮、按住 120 tick（阈值的 14 倍）
        // 也纹丝不动，而同批的零硬度格子每 tick 都在发 START。
        if (owner.breakDigPos != null) {
            if (!pos.equals(owner.breakDigPos)) return true;
            if (mc.level.getBlockState(pos).isAir()) {
                clearBreakDig();
                return true;
            }
            tickBreakDig();
            return true;
        }
        BlockState state = mc.level.getBlockState(pos);
        // 已经砸掉了：服务端确认过，交给验证层判成功（不需要再发包）
        if (state.isAir()) {
            clearBreakDig();
            return true;
        }
        // 客户端算出的「每刻进度」是否为「一包即毁」。**只用来定首轮按住多少刻**，
        // 不再用来选「秒破」还是「按住」——是否一包即毁由服务端裁决（见下方会话流程）；
        // 也不再用它跳过换手：服务端是按手上那件算进度的，不换手就等于白等（见下方换手注释）。
        boolean instantBreak = false;
        if (requiredDigTicks(digProgress(mc, pos, state), SPECIAL_BREAK_THRESHOLD) == 0) {
            // 零硬度载体（绊线 / 农作物 / 竹子）：不必先换手，直接开挖。
            //
            // **但不再走「同 tick START + STOP 秒破」那条快捷**（用户 2026-09-22 实机）：那条通道的
            // 前提是「客户端算出的硬度 == 服务端认定的硬度」，而这台服的死作物在客户端算是 0 硬度、
            // 服务端却要按住若干刻才放行 —— 秒破一律被驳回，方块一直在，于是每轮重来
            // （实机：START/STOP 连发两秒方块纹丝不动，聊天栏反复「发现枯死作物 · 正在清理」）。
            // 现在**一律走完整会话**：真零硬度的方块，服务端收到 START 就销毁，客户端下一 tick 见到
            // 空气即判成功（只多 1 tick）；要按住的方块则等够 tick 再 STOP，放不放行**由服务端裁决**。
            instantBreak = true;
        }
        // 服务端刚驳回过这一格：退避期内不再开新会话。
        // 派发层（resolveTask）已经拦了一道，但「放弃」发生在会话进行中，之后本方法每 tick 都还会被
        // 调用一次 —— 不在这里守同一道闸，放弃的下一秒就会重开（实机：同一格空砸两分钟、播报说
        // 「60 秒内不再重试」却立刻又开挖）。
        if (owner.isBreakRejected(pos)) {
            clearBreakDig();
            return false;
        }
        // 工具不限，但拿工具更快：**一律先换手再算进度**（含"客户端算一包即毁"的那类格子）。
        //
        // **为什么连零硬度格子也换手**（用户 2026-09-22 实机）：「可以挖了 但是没有用包里的效率」——
        // 服务端在 STOP（或 START 即判定）那一刻按**手上那件**算进度，客户端算出来的一包即毁与
        // 服务端无关：这服的死作物客户端算 0 硬度、服务端却按自己的硬度算，拿效率工具时进度爆表、
        // 服务端收到 START 就销毁（玩家手动"点一下就掉"），空手则要按住二十几刻。
        // 不换手等于把这 20 倍的时间白白花掉。换手的成本只有一次选中槽切换 / 一次栏位交换，
        // 且真零硬度的格子在第一刻就被服务端拆掉、紧接着就换回来了。
        state = mc.level.getBlockState(pos);
        float delta = ensureDigTool(mc, pos, state);
        // 悬空时挖掘速度被服务端除以 5（{@code Player#getDestroySpeed} 的 {@code !onGround} 修正）：
        // 巨型作物的载体硬度 0.4，站定时 8 刻就能挖掉，悬空要 40 刻以上。而模块为了够近会站到
        // 作物本体上，那类方块（紫颂植株等）没有碰撞，玩家直接掉下去 —— 于是永远在悬空状态开挖，
        // 表现为「前面一格挖得掉、后面紧邻的几格怎么都不认」。
        // 这一轮先不开挖，等落地：协调器每刻都会再进来（返回 true = 这一格仍在推进，不算失败）。
        if (!mc.player.onGround()) {
            LOGGER.info("[星露谷] 破坏 悬空等待落地 目标={} 选中物品={}", pos, selectedHotbarLabel(mc));
            return true;
        }
        // 客户端算出的「需要多少刻」只用来定第一轮按住多久，**绝不用来拒挖**：本服的「方块破坏速度」
        // 属性在挖掘期间会被压到 0（发包记录实机：发出 START 后几刻就变 0.0、ABORT 后几秒回到 1.0），
        // 于是这里必然算出 1.0E-4 / 需要 7000 tick —— 但同一格手动按住左键照样能挖掉
        // （用户 2026-09-22 实机确认「能掉啊」）。估算离谱时由 {@link #holdTicksFor} 退回固定时长、
        // 逐轮翻倍，真正不放行由 {@link StardewCoordinator#BREAK_MAX_ATTEMPTS} 兜底。
        int required = requiredDigTicks(delta, SPECIAL_BREAK_THRESHOLD);
        owner.breakDigPos = pos;
        owner.breakDigAttempts = 1;
        owner.breakDigTickCount = 0;
        owner.breakDigStopSent = false;
        owner.breakDigHoldTicks = instantBreak
            // 客户端算「一包即毁」的格子：给一个最小按住时长，**不给 0**。
            // 给 0 会让「服务端其实要按住」的格子永远只按 0 tick（翻倍 0×2 还是 0），永远挖不掉；
            // 给最小档后每一轮翻倍（20 → 40 → 80 → 120），几轮内就收敛到服务端真正要的时长。
            // 真零硬度的方块不受影响：服务端收到 START 就销毁，客户端下一 tick 已是空气即判成功。
            ? StardewCoordinator.BREAK_MIN_HOLD_TICKS
            : holdTicksFor(required);
        syncHeldSlot(mc);
        // 挥一次手：原版「按住左键」的完整动作是「挥手 + START + 每刻推进」，模块直接拼包会漏掉挥手。
        // 服务端的破坏校验常要求破坏动作伴随挥手动画（反作弊的常见判据），缺了它就表现为
        // 「包全部正常、服务端一律不认」。
        mc.player.swing(InteractionHand.MAIN_HAND);
        boolean started = owner.adapter.face(pos) && ClientPacketSender.sendBreakStart(pos, Direction.UP, state);
        // 硬度 / 物品破坏速度 / 是否算「正确工具」三个原始量都打出来：客户端估算离谱时靠这一行
        // 才能定位是哪一项不对（实机：同一格 物品破坏速度=0.0 与 =1.0 交替，前者是手上那把假锄头）。
        LOGGER.info("[星露谷] 破坏 开始挖掘 目标={} {} 选中槽={} 选中物品={} 硬度={} 物品破坏速度={} 正确工具={} 原始每刻进度={} → 实际用 {} 客户端估算 {} tick 按住 {} tick 发包={} 玩家={} 离方块={} onGround={} 交互距离={} 发包规则={}",
            pos, cellSummary(pos), mc.player.getInventory().getSelectedSlot(), selectedHotbarLabel(mc),
            state.getDestroySpeed(mc.level, pos), mc.player.getDestroySpeed(state),
            mc.player.hasCorrectToolForDrops(state), rawDigProgress(mc, pos, state), delta,
            required, owner.breakDigHoldTicks, started,
            mc.player.blockPosition(), String.format("%.2f", mc.player.position().distanceTo(pos.getCenter())),
            mc.player.onGround(), String.format("%.2f", mc.player.blockInteractionRange()),
            ClientPacketSender.sendRuleCount());
        return started;
    }

    /**
     * 开挖前确保「手里那件真的挖得动这一格」，并把换手后的实测进度交回来。
     *
     * <p><b>为什么不能按物品类型认定「已经拿着锄头了」：</b>资源包里存在与金锄<b>同基底</b>
     * （{@code minecraft:golden_hoe}）的自定义道具，它的 {@code tool} 组件采掘速度是 0；
     * {@code ItemStack#is} 只比物品本体，于是手上的读数一天里在 {@code 0.0} 与 {@code 1.0} 之间跳
     * （实机：同一格同一把金锄头，一次算出 0.0、一次 1.0）。判据只能是<b>实测</b>：换到这一格会算出多少。</p>
     *
     * <p><b>挑选口径（工具不限）：</b>全背包 0~35 一起比，比的是「{@link #effectiveDigSpeed}」——
     * 物品本体破坏速度 + 该件的效率附魔加成；空槽按空手（采掘速度 1.0）算，于是「整包都是挖不动的
     * 假道具」时也会挑中空格子空手挖，而不是抱着 0 进度硬砸。两条判据与防抖见
     * {@link #bestDigSlot}：**算得出更快的换最快的；算不出快慢但手里不是工具、背包里有工具，
     * 就按口径「有工具就拿工具」换上去**（本服的载体对金锄 / 木镐都算不出速度，
     * 只比速度会导致工具一次都挑不出来）。</p>
     *
     * <p><b>为什么不再「够挖就别换手」：</b>原先的判据是「手上这件能在
     * {@code BREAK_MAX_REQUIRED_TICKS} 刻内挖掉就别动」，而空手挖硬度 0.4 的载体只要 9 刻、
     * 远在阈值内 —— 于是模块永远空手挖（或拿着手上的食物），背包里的效率附魔斧头一次都不上手
     * （用户 2026-09-22 实机：「没有主动拿背包的工具，或者带效率的东西挖」）。
     * 那个上限只该回答「这一格能不能挖」，不该回答「用哪件挖」。</p>
     *
     * <p><b>为什么不能只看快捷栏 + 一把金锄：</b>曾经只扫 0~8、并且只认配置的那把金锄，于是真能
     * 挖的那件躺在背包 9~35 时谁也找不到，手被留在 0 进度的道具上再也回不来（实机：同一格
     * {@code 0.0} 与 {@code 0.0833} 交替出现，模块砸了两分钟纹丝不动）。</p>
     *
     * <p><b>为什么换手后不等属性同步：</b>客户端的采掘速度属性由服务端同步、换手要一个来回才到，
     * 同 tick 读到的还是旧手的值 —— 但那个值只用来估算「按住多久」，估算离谱时本来就退回固定时长，
     * 放不放行由服务端裁决（第 199 条）。原先为它空转一轮，反被"任务之间还手"拖成连换 4 轮、
     * 轮数用尽后误报「砸不掉」并停手（见下方 {@link #ensureDigTool}）。</p>
     *
     * @return 当前选中槽对这一格的每刻进度（换手后直接读新手，不做延迟）
     */
    private float ensureDigTool(Minecraft mc, BlockPos pos, BlockState state) {
        // 本服特殊收割口径指定了工具：手里必须一直是那件（服务端认「拿的就是那件」），绝不换手
        if (owner.specialToolHold != null) return digProgress(mc, pos, state);
        int slot = bestDigSlot(mc, state);
        if (!holdDigSlot(mc, slot)) return digProgress(mc, pos, state);
        // 换手后**本轮直接开挖**，不空转一轮等属性同步。
        //
        // **为什么不再等同步**：换手要一个来回，客户端同 tick 读到的还是旧手的采掘速度 ——
        // 但那个读数只用来估算「按住多久」，而估算离谱时 `holdTicksFor` 本来就退回固定时长，
        // 真正放不放行由服务端裁决（第 199 条：客户端估算绝不用来拒挖）。空转一轮的代价却很大：
        // 任务之间会把借来的手还回去，于是每轮都重新换一次、永远开不了工 —— 实机连换 4 轮
        // （19:13:46~48、19:15:42~44），把这一格的轮数用尽，播报「特殊变种砸不掉」并停手，
        // 剩下的巨型作物就没人管了。
        //
        // 顺序安全：换手包（选中槽切换 / 热键栏交换）与随后的 START 走同一条连接、按序到达，
        // `syncHeldSlot` 还会在发 START 前再把当前选中槽声明一次。
        return digProgress(mc, pos, state);
    }

    /**
     * 背包里「该换上去挖这一格」的那一件（0~35；同分取下标小的，于是优先快捷栏）；没有就返回 -1。
     *
     * <p><b>两条判据，第一条优先：</b></p>
     * <ol>
     *   <li><b>真的更快</b>：{@link #effectiveDigSpeed} 严格大于手上这件 —— 效率附魔斧头这类。</li>
     *   <li><b>分一样但手上不是工具，而背包里有工具</b>：本服口径就是「有工具就拿工具挖，
     *       没有工具用任何东西破坏都可以」（用户 2026-09-22 第 19 条原话）。
     *       这一类里**同分优先带效率附魔的那件**（客户端算不出速度差，但附魔数据本身读得到），
     *       于是「金锄 + 效率 X 木镐」会选中效率木镐。</li>
     * </ol>
     *
     * <p><b>为什么需要第二条：</b>挑工具原本只比 {@code getDestroySpeed}，而它只认「这件是这一格的
     * 正确工具」才给速度 —— 实测机器的载体 {@code chorus_plant} 属 {@code mineable/axe}，
     * 金锄与木镐对它都算出 1.0（与空手同分），效率 X 因此也不叠加。于是「背包里明明有工具，
     * 模块却一直空手挖」（用户 2026-09-22 实机，日志连续三格 {@code 选中物品=air}）：
     * 严格更快永远不成立，工具一次都挑不出来。而**服务端**认的显然不是客户端这个读数 ——
     * 玩家手动拿它们挖得更快（同一次反馈：「效率 10 一挖就掉 点一下就行」）。
     * 因此第二层判据退回「是不是一件工具」（带 {@code DataComponents.TOOL} 的物品：镐 / 斧 / 锄 / 锹 / 剪），
     * 与客户端算得出多少无关。</p>
     *
     * <p><b>为什么不会来回倒手：</b>比较基准是手上这件、不是 0 —— 手里已经是最快的那件时返回 -1；
     * 手上已经是工具时第二条不再触发（不会在锄头与镐子之间互倒）；空槽按空手（1.0）算，
     * 于是手上那把 0 进度的假道具会被空槽比下去、自动退回空手。</p>
     */
    private int bestDigSlot(Minecraft mc, BlockState state) {
        Inventory inventory = mc.player.getInventory();
        ItemStack held = inventory.getItem(inventory.getSelectedSlot());
        float heldSpeed = effectiveDigSpeed(held, state);
        boolean heldIsTool = isTool(held);
        // 第一类：算得出更快的那件（效率附魔斧头这类），不限是不是工具
        int fastest = -1;
        float fastestSpeed = heldSpeed;
        // 第二类：算不出快慢、但确实是一把工具（同分优先带效率附魔的那件）
        int tool = -1;
        float toolEfficiency = -1.0F;
        for (int slot = 0; slot < StardewInventoryService.MAIN_SLOTS; slot++) {
            ItemStack stack = inventory.getItem(slot);
            float speed = effectiveDigSpeed(stack, state);
            if (speed > fastestSpeed) {
                fastestSpeed = speed;
                fastest = slot;
                continue;
            }
            if (heldIsTool || speed < heldSpeed || !isTool(stack)) continue;
            float efficiency = miningEfficiencyBonus(stack);
            if (tool < 0 || efficiency > toolEfficiency) {
                tool = slot;
                toolEfficiency = efficiency;
            }
        }
        return fastest >= 0 ? fastest : tool;
    }

    /** 是否是一件工具（带原版 {@code TOOL} 组件的镐 / 斧 / 锄 / 锹 / 剪等） */
    private static boolean isTool(ItemStack stack) {
        return !stack.isEmpty() && stack.has(DataComponents.TOOL);
    }

    /**
     * 一件工具对某一格的「有效破坏速度」= 物品本体的破坏速度 + 该件附魔给的采掘效率加成。
     *
     * <p><b>为什么不能只看 {@code ItemStack#getDestroySpeed}</b>：效率附魔不在物品本体里 ——
     * 它由附魔数据以「{@code Attributes.MINING_EFFICIENCY} 属性加成」的形式给到玩家
     * （{@code Enchantments.EFFICIENCY} 的 {@code ATTRIBUTES} 效果，原版数值是等级²，随数据包可变），
     * 也就是 {@code Player#getDestroySpeed} 里 {@code speed + MINING_EFFICIENCY} 那一项，物品侧读不到。
     * 实机（用户 2026-09-22）：「拿效率附魔挖会更快，只是脚本识别不到」—— 只看本体速度时，
     * 带效率的斧头和不带的是同一个读数，必然挑错那件。</p>
     *
     * <p><b>为什么不直接读玩家属性</b>：客户端的属性由服务端同步，换手后要一个来回才到，
     * 那一刻读到的还是旧手的值（这正是「同一格读数忽大忽小」的来源），而候选件根本还没上手。
     * 加成数值一律向引擎的附魔数据要（{@code LevelBasedValue#calculate}），模块里不写死等级²这类数值。</p>
     *
     * <p>加成只在物品本体速度 &gt; 1（这件确实是这一格的正确工具）时才计入 —— 与原版
     * {@code Player#getDestroySpeed} 的 {@code if (speed > 1.0F)} 同一口径：拿错工具时效率不生效。</p>
     */
    private static float effectiveDigSpeed(ItemStack stack, BlockState state) {
        float speed = stack.isEmpty() ? 1.0F : stack.getDestroySpeed(state);
        return speed > 1.0F ? speed + miningEfficiencyBonus(stack) : speed;
    }

    /** 一件物品上的附魔给玩家的「采掘效率」加成总和（读附魔数据，不写死等级²这类公式） */
    private static float miningEfficiencyBonus(ItemStack stack) {
        ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        if (enchantments.isEmpty()) return 0.0F;
        float bonus = 0.0F;
        for (Holder<Enchantment> holder : enchantments.keySet()) {
            int level = enchantments.getLevel(holder);
            if (level <= 0) continue;
            for (EnchantmentAttributeEffect effect : holder.value().getEffects(EnchantmentEffectComponents.ATTRIBUTES)) {
                if (effect.operation() == AttributeModifier.Operation.ADD_VALUE
                    && sameAttribute(effect.attribute(), Attributes.MINING_EFFICIENCY)) {
                    bonus += effect.amount().calculate(level);
                }
            }
        }
        return bonus;
    }

    /**
     * 两个属性 {@code Holder} 是不是同一个属性。
     *
     * <p>按注册键比而不用 {@code Holder#is(Holder)}：后者在 26.1.2 已标记 {@code @Deprecated}
     * （第 237 条：新代码不许沿用废弃写法）。</p>
     */
    private static boolean sameAttribute(Holder<Attribute> left, Holder<Attribute> right) {
        return left.unwrapKey().equals(right.unwrapKey());
    }

    /** 把指定槽位换到手（快捷栏直接切；背包里的换到当前选中槽）；已经是它则返回 false（不必换） */
    private boolean holdDigSlot(Minecraft mc, int slot) {
        if (slot < 0) return false;
        if (slot == mc.player.getInventory().getSelectedSlot()) return false;
        saveHand();
        activeHand = InteractionHand.MAIN_HAND;
        if (slot < 9) {
            owner.adapter.selectHotbar(slot);
        } else {
            owner.swappedInvSlot = slot;
            owner.adapter.swapToHotbar(slot);
        }
        return true;
    }

    /**
     * 这一格第一轮要按住多久才发 STOP。
     *
     * <p><b>估算只能当参考：</b>客户端算出的破坏速度在这台服上忽大忽小（实机：同一格同一把金锄头，
     * 一次 {@code 0.0}、一次 {@code 1.0}）。因此：离谱的估算退回
     * {@link StardewCoordinator#BREAK_FALLBACK_HOLD_TICKS}，其余夹在
     * {@link StardewCoordinator#BREAK_MIN_HOLD_TICKS} ~ {@link StardewCoordinator#BREAK_MAX_HOLD_TICKS}
     * 之间；估算偏小由 {@link #tickBreakDig()} 的逐轮翻倍兜底，服务端才是权威。</p>
     */
    private int holdTicksFor(int required) {
        if (required == 0) return 0;
        int wanted = required > StardewCoordinator.BREAK_MAX_REQUIRED_TICKS
            ? StardewCoordinator.BREAK_FALLBACK_HOLD_TICKS
            : required + 2;
        return Math.min(Math.max(wanted, StardewCoordinator.BREAK_MIN_HOLD_TICKS),
            StardewCoordinator.BREAK_MAX_HOLD_TICKS);
    }

    /**
     * 挖掘序列的每刻推进（由协调器在主循环最前面调用，与任务相位无关）。
     *
     * <p><b>为什么自己管 START / STOP、不用原版 {@code MultiPlayerGameMode}</b>：原版那套状态是给
     * 「玩家真的按住左键」用的 —— {@code Minecraft} 每刻都会检查攻击键，没按下就调
     * {@code stopDestroyBlock()}。模块借它的推进时，会话状态每刻被清掉，
     * {@code continueDestroyBlock} 就会每刻走「重开」分支、<b>每刻重发一次 START</b>；服务端的
     * {@code destroyProgressStart} 随之每刻重置，「已挖 tick 数」永远是 0，{@code 0.7} 永远达不到
     * （实机：原版流程挖满 201 刻仍纹丝不动）。</p>
     *
     * <p><b>一个 START 只配一个 STOP</b>：等够服务端要求的 tick 就发 STOP；STOP 之后给一个确认窗口，
     * 方块还没变就重来一轮（新的 START 重新登记服务端槽位），最多
     * {@link StardewCoordinator#BREAK_MAX_ATTEMPTS} 轮；仍不变则 ABORT + 清账，
     * 交回验证层判失败（退避 + 播报），绝不在这格上无限砸。</p>
     */
    void tickBreakDig() {
        Minecraft mc = Minecraft.getInstance();
        if (owner.breakDigPos == null || mc.player == null || mc.level == null) return;
        BlockPos pos = owner.breakDigPos;
        // 服务端已经把这格拆了：账清了，成功与否交给验证层（方块已是空气 → 验证判成功）
        if (mc.level.getBlockState(pos).isAir()) {
            LOGGER.info("[星露谷] 破坏 目标已消失 目标={}", pos);
            clearBreakDig();
            return;
        }
        owner.breakDigTickCount++;
        // **每刻挥一次手**：原版「按住左键」在挖掘期间（{@code Minecraft#continueAttack}）是每刻发一次
        // 挥手包，而不是只挥一次。发包记录实测（同一格、玩家手动）：START 之后紧跟着一连串
        // `挥手 MAIN_HAND`（一秒约二十次），而模块此前只在开挖时挥一次、之后二十刻静默 ——
        // 服务端的破坏校验因此认为「没在挖」，把 STOP 驳回（实机：START/STOP 都发出、
        // 每刻进度 0.0833、按住 120 刻仍纹丝不动）。
        mc.player.swing(InteractionHand.MAIN_HAND);
        if (!owner.breakDigStopSent) {
            if (owner.breakDigTickCount < owner.breakDigHoldTicks) return;
            // 松手前再确认一次手里那件真挖得动：服务端是在 STOP 那一刻按「当时手上那件」算进度的，
            // 中途被重规划 / 换手改掉手里的东西，这一轮就白按了（实机：同一格前一轮 0.0833、后一轮 0.0）。
            if (Float.isNaN(ensureDigTool(mc, pos, mc.level.getBlockState(pos)))) {
                owner.breakDigTickCount = 0;
                return;
            }
            // 服务端是在 STOP 那一刻按「当时的挖掘速度」算进度的，但**不能**用客户端这一刻的估算
            // 去拦这一轮：本服的「方块破坏速度」属性只要在挖就会被压到 0，按住期间读数必然是 0.0，
            // 而同一段里成功挖掉的那一格（发包记录实机 02:16:48 START、02:16:49 方块消失）读数也是 0.0。
            // 拦在这里等于把每一轮都主动废掉，表现成 START / ABORT 抖动、永远挖不动。
            owner.breakDigStopSent = true;
            syncHeldSlot(mc);
            boolean sent = ClientPacketSender.sendBreakStop(pos, Direction.UP);
            LOGGER.info("[星露谷] 破坏 结束挖掘 目标={} 已按住 {} tick（计划 {}）发包={}",
                pos, owner.breakDigTickCount, owner.breakDigHoldTicks, sent);
            return;
        }
        if (owner.breakDigTickCount < owner.breakDigHoldTicks + StardewCoordinator.BREAK_RESEND_AFTER_TICKS) return;
        if (owner.breakDigAttempts >= StardewCoordinator.BREAK_MAX_ATTEMPTS) {
            LOGGER.info("[星露谷] 破坏 放弃 目标={} 已尝试 {} 轮（服务端没放行这一格） 选中槽={} 选中物品={}",
                pos, owner.breakDigAttempts, mc.player.getInventory().getSelectedSlot(), selectedHotbarLabel(mc));
            clearBreakDig();
            ClientPacketSender.sendBreakAbort(pos, Direction.UP);
            owner.reportBreakRejected(pos, cellSummary(pos));
            return;
        }
        // 上一轮没挖掉：重新挥手 + 重新 START，按住时间翻倍再试（客户端估算在这台服上会偏小，
        // 只靠估算永远卡在「按一下就松」，翻倍才能收敛到服务端真正要的时长）。
        if (Float.isNaN(ensureDigTool(mc, pos, mc.level.getBlockState(pos)))) return;
        owner.breakDigAttempts++;
        owner.breakDigTickCount = 0;
        owner.breakDigStopSent = false;
        owner.breakDigHoldTicks = Math.min(owner.breakDigHoldTicks * 2,
            StardewCoordinator.BREAK_MAX_HOLD_TICKS);
        syncHeldSlot(mc);
        mc.player.swing(InteractionHand.MAIN_HAND);
        boolean sent = ClientPacketSender.sendBreakStart(pos, Direction.UP, mc.level.getBlockState(pos));
        LOGGER.info("[星露谷] 破坏 重试挖掘 目标={} 第 {} 轮 按住 {} tick 发包={}",
            pos, owner.breakDigAttempts, owner.breakDigHoldTicks, sent);
    }

    /**
     * 开挖 / 松手前，把「当前选中槽」再向服务端声明一次。
     *
     * <p>服务端算破坏进度用的是<b>它那边</b>的手持物，而本地换手只在真的换了槽位时才发包；客户端与
     * 服务端一旦对「手上那件」认知不一致（实机：客户端算出每刻 0.0833，服务端那边算出来是 0），
     * 表现就是「客户端一直按、服务端永远不认这一格」。一个包换一次确定性。</p>
     */
    private void syncHeldSlot(Minecraft mc) {
        if (mc.player == null) return;
        owner.adapter.selectHotbar(mc.player.getInventory().getSelectedSlot());
    }

    /** 这一格的每刻破坏进度（客户端与 {@code getDestroyProgress} 同一口径）；负值/极小值一律按「几乎为 0」处理 */
    private static float digProgress(Minecraft mc, BlockPos pos, BlockState state) {
        return Math.max(state.getDestroyProgress(mc.player, mc.level, pos), 1.0E-4F);
    }

    /** 未经夹取的原始每刻进度：排查「客户端估算离谱」时看这个值最直接 */
    private static float rawDigProgress(Minecraft mc, BlockPos pos, BlockState state) {
        return state.getDestroyProgress(mc.player, mc.level, pos);
    }

    /** 玩家当前选中的快捷栏物品（{@code getDestroyProgress} 读的就是它，不是 activeHand） */
    private static ItemStack selectedHotbarLabel(Minecraft mc) {
        return mc.player == null ? ItemStack.EMPTY : mc.player.getInventory().getSelectedItem();
    }

    /** 这一格的挖掘已经结束（方块没了 / 已放弃）：清掉进度账，不发 ABORT */
    private void clearBreakDig() {
        owner.breakDigPos = null;
        owner.breakDigAttempts = 0;
        owner.breakDigTickCount = 0;
        owner.breakDigStopSent = false;
        owner.breakDigHoldTicks = 0;
    }

    /** 是否正在破坏的挖掘过程中（协调器据此把控制权留在这一格，别让重试上限打断） */
    boolean digInProgress() {
        return owner.breakDigPos != null;
    }

    /**
     * 中止破坏：告诉服务端「不挖了」并清掉进度。
     *
     * <p>任务重规划 / 停机 / 换世界时必须调：服务端会一直记着「这个玩家在挖这一格」，
     * 不带 ABORT 就换了目标，下一格的 START 会被当成同一段挖掘的继续。</p>
     */
    void abortBreakDig() {
        if (owner.breakDigPos == null) return;
        BlockPos pos = owner.breakDigPos;
        clearBreakDig();
        ClientPacketSender.sendBreakAbort(pos, Direction.UP);
    }

    /** 服务端公式 {@code delta × (已挖 tick + 1) ≥ 阈值} 的最少已挖 tick（与秒破同源） */
    private static int requiredDigTicks(float delta, float threshold) {
        if (delta <= 0) return Integer.MAX_VALUE - 1;
        double samples = Math.ceil(threshold / (double) delta);
        int elapsed = Math.max(0, (int) samples - 1);
        while ((double) delta * (elapsed + 1) < threshold) elapsed++;
        return elapsed;
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
            // 与验证层同口径：同一作物每会话只播一条（逐阶段播会连刷好几条「未确认」）
            if (owner.reportedLearningFailures.add(owner.activeCell.crop().cropKey())) {
                owner.status.state("LEARN_FAIL:" + owner.activeCell.crop().cropKey(), "收获学习未确认",
                    owner.activeCrop.chineseName() + "：没有可用空手，其余阶段会在后台继续试探");
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
     *
     * <p><b>走 {@link #breakAt} 而不是直接 {@code breakBlock}</b>：承载体不一定是零硬度方块
     * （巨型作物常挂在硬度 0.4 的紫颂植株上），零硬度那条同 tick 通道对它只会「本地消失一下又回来」。</p>
     */
    boolean doBreakPlant() {
        return owner.targetPot != null && breakAt(owner.targetPot.above());
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
     * 收割要「一只手空着」（空手右键：拿工具 / 食物会改变服务端对这次交互的解释）。
     *
     * <p><b>取手顺序（主手优先，副手只兜底）</b>：主手空 → 直接用主手；主手有东西 → 切到一个空热键栏槽；
     * 9 格全满 → 把当前手持物暂放到背包空格，腾出一只空的主手；背包也没有空格 → 用空副手；
     * 副手也占着 → 把副手物品暂放到背包空格，用副手收割。两处暂放都在任务结束（{@code replan}）
     * 或模块停机时由 {@link #restoreHandNow()} 放回。</p>
     *
     * <p><b>为什么副手只能兜底（2026-09-21 实机取证）</b>：同一片农田、同一种绊线作物，主手空手右键
     * 收得掉，改成「副手空手、主手拿着东西」后连发 40 多包服务端一动不动（{@code latest.log} 里
     * 「收割交互 手=主手 … 发包=true」后面紧跟 16 次播种，而「手=副手」连发一整分钟零效果）。
     * 服务端插件对 {@code hand=OFF_HAND} 的方块交互与主手不一条路，只有「主手空手右键」才是各服
     * 通用的原版口径。因此腾手一律先腾主手，副手那条路留着兜底（背包也满时至少还有一只手能用）。</p>
     *
     * <p><b>为什么先把主手物暂放进背包</b>：主手那件东西此刻并没有被模块使用（收割是空手动作），
     * 暂放它只花两次点击包（换出 + 换回），换来的是「在只认主手的服上真的能收」。暂放只动背包空格、
     * 不动模块自己要用的格子，槽位从后往前找。</p>
     *
     * <p><b>为什么要记日志</b>（实机反馈：一直显示「正在收获」，地里却一点动静都没有）：
     * 取手这条链路原本一声不响，玩家根本无从知道是「没手可用」。日志只在<strong>非平凡情形</strong>
     * 下打——副手占着东西、借用主手 / 副手、或干脆没有空手——正常「主手本来就空」的情况不打，免得刷屏。</p>
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
        // 热键栏 9 格全满：把当前手持物暂放进背包空格，腾出一只空的主手。这一步排在副手兜底之前
        // ——副手交互在部分服务端不收（见方法注释），主手空手右键才是各服通用口径。
        int free = lastFreeBackpackSlot(mc);
        if (free >= 0 && parkMainHand(mc, free, main)) {
            noFreeHandNotified = false;
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
     * 把当前手持物暂放到背包空格，腾出一只空的主手。
     *
     * <p>动作用的是「背包槽 ↔ 选中快捷栏槽」的 SWAP（与 {@link #holdSeed} 把背包里的种子换进主手
     * 同一个原版动作，只是方向相反），所以不需要切槽：选中槽原地变空、那件东西落到背包空格里。</p>
     *
     * <p>槽位由调用方从后往前找：模块自己的播种 / 卸货运货都往靠前的空格堆，借靠后的格子最不容易打架。
     * 已经借过就直接复用（第二次调用时主手通常已经空了，会由 {@link #prepareHarvestHand()} 提前返回）。</p>
     *
     * @return true 表示主手已腾空（可以用主手收割）
     */
    private boolean parkMainHand(Minecraft mc, int free, String main) {
        if (mc.player == null) return false;
        if (mainHandParkedSlot >= 0) return mc.player.getMainHandItem().isEmpty();
        ItemStack parked = mc.player.getMainHandItem().copy();
        int selected = mc.player.getInventory().getSelectedSlot();
        if (!owner.adapter.swapToHotbar(free)) return false;
        mainHandParkedSlot = free;
        mainHandParkedSelected = selected;
        mainHandParkedItem = parked;
        LOGGER.info("[星露谷] 收割取手：主手物品 {} 暂放到物品栏槽 {}（背包区），改用空主手收割（收完放回）",
            main, free + 1);
        return true;
    }

    /**
     * 把暂放的主手物品换回主手（本次任务结束 / 模块停机时调用）。
     *
     * <p>与 {@link #restoreParkedOffhand()} 同一口径：只在那一格确实还放着当初挪过去的那件东西时才换回，
     * 玩家中途动了那一格就不碰它，东西留在背包里不丢。</p>
     *
     * <p>换回是「背包槽 ↔ 选中快捷栏槽」的 SWAP，必须先把选中槽切回当初那一格——任务期间模块可能
     * 切过槽（例如收完去播种）。</p>
     */
    private void restoreParkedMainHand() {
        if (mainHandParkedSlot < 0) return;
        int slot = mainHandParkedSlot;
        int selected = mainHandParkedSelected;
        ItemStack parked = mainHandParkedItem;
        mainHandParkedSlot = -1;
        mainHandParkedSelected = -1;
        mainHandParkedItem = null;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || parked == null) return;
        ItemStack now = mc.player.getInventory().getItem(slot);
        if (now.isEmpty() || !ItemStack.isSameItemSameComponents(now, parked)) {
            LOGGER.info("[星露谷] 主手暂放物品未换回：物品栏槽 {} 已不是当初暂放的东西，留在背包里", slot + 1);
            return;
        }
        if (selected >= 0 && mc.player.getInventory().getSelectedSlot() != selected) {
            owner.adapter.selectHotbar(selected);
        }
        if (owner.adapter.swapToHotbar(slot)) {
            LOGGER.info("[星露谷] 主手暂放物品已换回（原暂放在物品栏槽 {}）", slot + 1);
        }
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
        // 借来的手一定要还（主手 / 副手两处暂放），与「是否恢复主手」这个开关无关：
        // 两个还原方法都会先把暂放状态清干净再动手，所以放在玩家判空之前，避免残留状态带到下一轮。
        restoreParkedMainHand();
        restoreParkedOffhand();
        // 特殊收割口径的「不许换手」约束随任务一起结束：否则下一格（哪怕是普通菜）也不会再挑工具
        owner.specialToolHold = null;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
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
