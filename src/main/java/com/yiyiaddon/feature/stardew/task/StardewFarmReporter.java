package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.point.SprinklerWorldBinding;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestAction;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestRecipe;
import com.yiyiaddon.feature.stardew.recognition.CropState;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.recognition.PotState;
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.season.StardewSeasonService;
import com.yiyiaddon.feature.stardew.service.StardewItemRole;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator.Phase;
import com.yiyiaddon.platform.container.ContainerAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Map;

/**
 * 星露谷播报与范围：任务播报 / 库存快照统计 / 季节阻塞释放 / 点位方块巡检。
 *
 * <p>本类由 {@link StardewCoordinator} 机械拆分而来，共享协调器的全部可变状态
 * （通过 {@code owner} 直接读写），行为与拆分前完全一致。</p>
 */
final class StardewFarmReporter {

    /**
     * 「点位方块丢了」的确认时长（tick）：只有连续这么久都判定丢失才报警。
     *
     * <p>真实被挖掉 / 被换成别的方块会持续存在；开箱瞬间服务端临时替换方块、或方块实体稍晚同步
     * 这类瞬时状态不会。真机事故：箱子明明还在，却在开箱那一瞬被判「已被挖掉」，模块当场停机。</p>
     */
    private static final int LOST_CONFIRM_TICKS = 40;

    /** 已播报过「被挖掉」的点位键：方块恢复或点位重设后自动复位，避免每轮巡检重复刷屏 */
    private final java.util.Set<String> lostPointKeys = new java.util.HashSet<>();

    /** 连续判定丢失的 tick 计数（去抖用）：恢复即清零，不做任何跨会话保留 */
    private final java.util.Map<String, Integer> lostStreak = new java.util.HashMap<>();

    private final StardewCoordinator owner;

    StardewFarmReporter(StardewCoordinator owner) {
        this.owner = owner;
    }

    /**
     * 点位方块巡检：绑定的方块被挖掉（或容器被换成非容器）时立即报警一次。
     *
     * <p><b>为什么只判「方块没了」：</b>{@code validationFailure} 里还包含「所在区块尚未加载」
     * 「资源未就绪」这类与玩家操作无关的状态，拿它逐 tick 播报只会变成噪音。这里只认确定性事件
     * ——该坐标已经没有方块、或原本要求是容器而现在不是容器——报一次后记住，方块回来就自动复位。</p>
     *
     * <p><b>两类事件的后果不同：</b>方块没了 = 确定失效，直接停机；「读不到原版容器」只是客户端
     * 视角的事实——自定义容器方块（CraftEngine 系服务器把箱子做成展示实体 + 隐形载体）本来就没有
     * 原版方块实体，箱子其实还在，因此只提示不停机，真的不可用会在开箱任务里如实报错。</p>
     */
    void watchPointBlocks() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        // 开着容器界面时不判定：此刻模块/玩家正在跟箱子交互，服务端可能临时替换方块或卸载方块实体，
        // 这一刻的读数不能作为「箱子没了」的证据。
        boolean inContainerScreen = ContainerAccess.openMenu() != null;
        for (StardewPointType type : StardewPointType.values()) {
            if (type == StardewPointType.SPRINKLER) {
                for (StardewPointManager.StardewPoint point : owner.points.getAll(type)) {
                    watchPointBlock(mc, type, point, inContainerScreen);
                }
            } else {
                StardewPointManager.StardewPoint point = owner.points.get(type);
                if (point != null) watchPointBlock(mc, type, point, inContainerScreen);
            }
        }
    }

    /** 会话 / 服务器切换时清空「已播报」记录：换服后同一坐标要能重新报警 */
    void forgetLostPoints() {
        lostPointKeys.clear();
        lostStreak.clear();
    }

    /** 单点位判定；区块未加载 / 非当前维度 / 界面开着时一律视为「无法判定」，绝不当成被挖掉 */
    private void watchPointBlock(Minecraft mc, StardewPointType type, StardewPointManager.StardewPoint point,
                                 boolean inContainerScreen) {
        String key = "POINT_LOST:" + type + ':' + point.pos();
        if (inContainerScreen || !point.inCurrentDimension() || mc.level == null
            || !mc.level.isLoaded(point.pos())) {
            forgetLost(key);
            return;
        }
        BlockState state = mc.level.getBlockState(point.pos());
        // 贴图形态洒水器的载体是展示实体，那一格本来就是空气：空气但挂着展示实体 = 载体还在，
        // 不是被挖掉（与绑定点位、准星对点共用 SprinklerWorldBinding 的同一份判据，禁止两份逻辑）。
        // 真机事故：洒水器好端端立在田里，点位却每轮巡检都判「已被挖掉」并停机刷屏。
        boolean vanished = state.isAir()
            && !(type == StardewPointType.SPRINKLER && SprinklerWorldBinding.displayCarrierAt(point.pos()));
        boolean notContainer = !vanished && type.requiresContainer()
            && !(mc.level.getBlockEntity(point.pos()) instanceof Container);
        if (!vanished && !notContainer) {
            forgetLost(key);
            return;
        }
        // 去抖：瞬时状态不算数（见 LOST_CONFIRM_TICKS 说明）
        if (lostStreak.merge(key, 1, Integer::sum) < LOST_CONFIRM_TICKS) return;
        if (!lostPointKeys.add(key)) return;
        String coords = "X" + point.x() + " Y" + point.y() + " Z" + point.z();
        if (vanished) {
            owner.status.critical(key, type.title() + "已被挖掉", "坐标 " + coords + "；请重新设置该点位");
            return;
        }
        // 非原版容器：自定义容器方块的服务端本来就不下发原版方块实体，箱子还在，绝不停机
        owner.status.state(key, type.title() + "读取不到容器内容",
            "坐标 " + coords + " 的方块仍是 " + blockId(state)
                + "，但客户端读不到容器方块实体（自定义容器常见）；如开箱异常请重新设置该点位");
    }

    /** 判定恢复正常：解封播报，允许下次真正丢失时再报一次 */
    private void forgetLost(String key) {
        lostStreak.remove(key);
        lostPointKeys.remove(key);
    }

    private static String blockId(BlockState state) {
        var id = net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return id == null ? "未知方块" : id.toString();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  播报与范围
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    void broadcast(String task) {
        owner.waitingMatureNotified = false;
        announceTaskStart();
    }

    /** 任务开始统一进入状态源；重复同类任务由 Reporter 自动去重。 */
    void announceTaskStart() {
        CropDefinition crop = taskCrop();
        String cropName = crop == null ? "" : crop.chineseName();
        switch (owner.taskType) {
            // 文案按盆型分派：下界 / 末地盆用的是岩浆 / 龙息，不是水壶，
            // 原来的固定文案会让玩家以为脚本在找水（实机反馈：「为什么是水？」）
            case WATER -> {
                PotGroup group = owner.executor.targetPotGroup();
                if (group.refillItem() != null) {
                    owner.status.state("WATER", "正在补" + group.materialName(),
                        "剩余待补盆：" + remainingDryPots());
                } else {
                    owner.status.state("WATER", "正在浇水", "剩余干盆：" + remainingDryPots());
                }
            }
            case REFILL -> {
                PotGroup group = owner.executor.targetPotGroup();
                if (group.refillItem() != null) {
                    String box = group == PotGroup.NETHER ? "岩浆箱" : "龙息箱";
                    owner.status.state("REFILL", "缺少" + group.materialName(), "正在前往" + box);
                } else {
                    owner.status.state("REFILL", "水壶缺水", "正在前往水源");
                }
            }
            case PLANT -> owner.status.state("PLANT:" + cropName, "正在播种", cropName);
            case RESTOCK -> owner.status.state("RESTOCK:" + cropName, "正在补货",
                crop == null ? "目标种子" : crop.seedDisplayName());
            case HARVEST, LEARN_HARVEST -> owner.status.state("HARVEST:" + cropName, "正在收获", cropName);
            case COLLECT -> owner.status.state("COLLECT", "正在拾取", "农田掉落物");
            case UNLOAD -> owner.status.state("UNLOAD:" + cropName, "正在卸货", unloadingCrops(cropName));
            case CLEAR_DEAD -> owner.status.state("DEAD_CLEAR", "发现枯死作物", "正在清理");
            case CLEAR_MISMATCH -> owner.status.state("MISMATCH_CLEAR", "发现错位作物", "正在清理");
            // 杂物是常规保洁（挡住补种才挖），不占聊天栏，只在状态源里留痕
            case CLEAR_JUNK -> owner.status.silent("JUNK_CLEAR", "清理盆上杂物", "正在清理", "");
            case RETURN_CENTER -> owner.status.state("RETURN", "返回农田", "正在前往农田中心");
            case FERTILIZE -> owner.status.state("FERTILIZE", "正在施肥", cropName);
            case POTION -> owner.status.state("POTION", "正在使用魔法药剂", cropName);
            case SPRINKLER_CHECK, SPRINKLER_REFILL -> {
                BlockPos sprinkler = owner.planner.sprinklerTarget();
                // 点位键带上坐标：三种洒水器要能一眼看出「现在在维护哪一台、是哪种」
                owner.status.state("SPRINKLER:" + sprinkler, "正在维护洒水器", sprinklerLabel(sprinkler));
            }
        }
        captureTaskInventory();
    }

    /**
     * 本次卸货实际会送进成品箱的作物（按作物名列出，忽略品质变体）。
     *
     * <p>一次卸货会把背包里<b>所有已选作物</b>的成品都送走（{@code depositAnyProduceOne} 逐个作物试），
     * 不只触发这次任务的那一种；只写触发作物会让人以为只卸了一种（实机反馈：同时卸三种，提示只写一种）。
     * 盘点为空时退回触发作物，绝不显示空值。</p>
     */
    private String unloadingCrops(String fallback) {
        StringBuilder out = new StringBuilder();
        for (CropDefinition crop : owner.planner.targetCrops()) {
            if (owner.inventory == null || owner.inventory.countProduce(crop) <= 0) continue;
            if (!out.isEmpty()) out.append("、");
            out.append(crop.chineseName());
        }
        return out.isEmpty() ? fallback : out.toString();
    }

    /** 「高级洒水器 · 19875, 64, -1630」；点位已删或类型未知时如实降级，绝不编造类型名 */
    private String sprinklerLabel(BlockPos pos) {
        if (pos == null) return "";
        String coords = pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
        for (StardewPointManager.StardewPoint point : owner.points.getAll(StardewPointType.SPRINKLER)) {
            if (point.inCurrentDimension() && point.pos().equals(pos)) {
                return (point.typeName() == null ? "洒水器" : point.typeName()) + " · " + coords;
            }
        }
        return coords;
    }

    /** 单台洒水器维护结束：只在水量确实没再减少时才说「已灌满」 */
    void announceSprinklerDone(BlockPos pos, boolean filledFull) {
        owner.status.state("SPRINKLER_DONE:" + pos,
            filledFull ? "洒水器已灌满" : "洒水器维护完成", sprinklerLabel(pos));
    }

    /** 一轮洒水器维护结束 */
    void announceSprinklerRoundDone(int count) {
        owner.status.state("SPRINKLER_ROUND", "洒水器维护完成", "本轮 " + count + " 台");
    }

    /**
     * 已绑定的洒水器点位失效（实体没了 / 同格换成了别的型号）：提示一次，并给出收拾出路。
     *
     * <p><b>为什么要报</b>：不报的话玩家只会看到「洒水器维护完成 ▸ 本轮 1 台」这种假结论
     * （真机事故），根本想不到那台早就被挖了、或已经换成别的型号。同一台由协调器去重，
     * 不会每 tick 刷屏（见 {@code StardewCoordinator#markSprinklerMissingReported}）。</p>
     */
    void announceSprinklerMissing(BlockPos pos, String reason) {
        owner.status.critical("SPRINKLER_MISSING:" + pos, "洒水器点位已失效，已跳过维护",
            sprinklerLabel(pos) + " ▸ " + reason
                + "｜收拾办法：在控制台「点位」页的洒水器卡片点「管理」，删掉它并按现场重新绑定；"
                + "若这块地不再用洒水器，把「洒水器维护」关掉即可（自检会拦启动）");
    }

    /** 这台刚验过是满的：本轮跳过，只更新状态卡，不刷聊天（一轮最多一条汇总） */
    void announceSprinklerSkipped(BlockPos pos) {
        owner.status.silent("SPRINKLER_SKIP:" + pos, "洒水器已满，本轮跳过", sprinklerLabel(pos), "");
    }

    /** 整轮都是刚验过满的：本轮不跑腿，只给一条聊天提示 */
    void announceSprinklerAllSkipped(int count) {
        owner.status.state("SPRINKLER_SKIP_ROUND", "洒水器本轮全部已满，跳过",
            "共 " + count + " 台 · 到点后自动重查");
    }

    /**
     * 已学会本服的特殊变种口径（动作 + 手持那件）。
     *
     * <p>特意把「按服务器分开记」写给玩家看：他的原话就是担心在一个服学了左键，
     * 回另一个服把巨型作物全砸了（用户 2026-09-22）。</p>
     */
    void announceSpecialHarvestLearned(String cropKey, StardewSpecialHarvestRecipe recipe) {
        owner.status.state("SPECIAL_HARVEST_LEARNED:" + cropKey, "已学会特殊变种收割口径",
            cropLabel(cropKey) + " ▸ " + recipe.displayName()
                + "｜本服其它变种也按这条收；按服务器分开记，其它服务器不受影响");
    }

    /**
     * 特殊变种在本服还没学到口径、且默认口径（金锄头右键）连续收不动：给一条可执行的出路。
     *
     * <p>只在「这一格确实是特殊阶段 + 本服没学过（也没预置）+ 默认口径是右键」时提示，每个作物只提示一次
     * （判定与去重都在这里，调用点只管在收割连续失败时喊一声）。</p>
     */
    void announceSpecialHarvestUnknownOnce() {
        if (owner.activeCell == null || owner.activeCell.crop() == null) return;
        String cropKey = owner.activeCell.crop().cropKey();
        if (cropKey == null || owner.activeCell.crop().state() != CropState.SPECIAL) return;
        if (owner.specialHarvestLearned(owner.activeCell.crop())) return;
        if (owner.specialHarvestRecipe(owner.activeCell.crop()).action() != StardewSpecialHarvestAction.RIGHT_CLICK) return;
        if (!owner.announcedSpecialUnknown.add(cropKey)) return;
        owner.status.critical("SPECIAL_HARVEST_UNKNOWN:" + cropKey, "特殊变种收不动（本服口径未知）",
            owner.specialHarvestRecipe(owner.activeCell.crop()).displayName() + " 连点多次，这一格毫无变化"
                + "｜出路：对着它左键砸一棵告诉我（能砸掉就说明本服是左键口径），我记住后照做；"
                + "口径按服务器分开保存，其它服照旧");
    }

    /**
     * 学到的「左键破坏」口径也没砸掉这一格：报一条，别让玩家只看到对着同一格反复砸。
     *
     * <p>与 {@link #announceSpecialHarvestUnknownOnce()} 互补：那条管「本服口径还不知道」，
     * 这条管「口径知道了、服务端就是不放行」（保护方块 / 口径其实不对）。同一作物只报一次。</p>
     */
    void announceSpecialBreakBlockedOnce() {
        if (owner.activeCell == null || owner.activeCell.crop() == null) return;
        String cropKey = owner.activeCell.crop().cropKey();
        if (cropKey == null || owner.activeCell.crop().state() != CropState.SPECIAL) return;
        if (owner.specialHarvestRecipe(owner.activeCell.crop()).action() != StardewSpecialHarvestAction.BREAK) return;
        if (!owner.announcedSpecialBreakFailed.add(cropKey)) return;
        owner.status.critical("SPECIAL_BREAK_FAILED:" + cropKey, "特殊变种砸不掉",
            cropLabel(cropKey) + " ▸ 已按学到的口径（" + owner.specialHarvestRecipe(owner.activeCell.crop()).displayName()
                + "）挖了 " + StardewCoordinator.BREAK_MAX_ATTEMPTS + " 轮，这一格还是原样"
                + "｜多半是服务端保护了它，或本服收法其实不对：手动收一棵给我看，口径会自动更新");
    }

    /** 作物键 → 玩家认得的名字（索引里没有就照键报出来，绝不编一个）；执行层播报同样用它 */
    String cropLabel(String cropKey) {
        CropDefinition crop = owner.index == null ? null : owner.index.cropByKey(cropKey);
        return crop == null ? cropKey : crop.chineseName();
    }

    private CropDefinition taskCrop() {
        if (owner.activeCrop != null) return owner.activeCrop;
        if (owner.activeCell != null && owner.activeCell.crop().cropKey() != null) {
            owner.activeCrop = owner.index.cropByKey(owner.activeCell.crop().cropKey());
        } else if (owner.targetPot != null) {
            owner.activeCrop = owner.planner.cropOf(owner.targetPot);
        }
        return owner.activeCrop;
    }

    private int remainingDryPots() {
        int count = 0;
        for (StardewFarmScanner.Cell cell : owner.pending) {
            if (cell.potState() == PotState.DRY && owner.planner.potMatchesSelection(cell)) count++;
        }
        return count;
    }

    void captureTaskInventory() {
        CropDefinition crop = taskCrop();
        owner.taskSeedBefore = totalSelectedSeeds();
        owner.taskCropSeedBefore = crop == null || owner.inventory == null ? 0 : owner.inventory.countSeed(crop);
        owner.taskProduceBefore = totalSelectedProduce();
        owner.taskInventoryBefore = owner.taskType == TaskType.UNLOAD && crop != null
            ? countAllUnloadableByDisplay() : Map.of();
    }

    int totalSelectedSeeds() {
        int total = 0;
        for (CropDefinition crop : owner.planner.targetCrops()) total += owner.inventory.countSeed(crop);
        return total;
    }

    int totalSelectedProduce() {
        int total = 0;
        for (CropDefinition crop : owner.planner.targetCrops()) total += owner.inventory.countProduce(crop);
        return total;
    }

    static String formatRemovedItems(Map<String, Integer> before, Map<String, Integer> after) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : before.entrySet()) {
            int removed = Math.max(0, entry.getValue() - after.getOrDefault(entry.getKey(), 0));
            if (removed <= 0) continue;
            if (result.length() > 0) result.append("｜");
            result.append(entry.getKey()).append(" ×").append(removed);
        }
        return result.toString();
    }

    /** 每个真实背包槽只归类一次，SEED 优先并排除，PRODUCE / VARIANT 按显示名聚合。 */
    Map<String, Integer> countAllUnloadableByDisplay() {
        Map<String, Integer> result = new java.util.LinkedHashMap<>();
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return result;
        for (int slot = 0; slot < 36; slot++) {
            ItemStack stack = mc.player.getInventory().getItem(slot);
            if (stack.isEmpty()) continue;
            boolean unloadable = false;
            for (CropDefinition crop : owner.planner.targetCrops()) {
                StardewItemRole role = owner.inventory.roleOf(stack, crop);
                if (role == StardewItemRole.SEED) {
                    unloadable = false;
                    break;
                }
                if (role.unloadable()) unloadable = true;
            }
            if (unloadable) result.merge(stack.getHoverName().getString(), stack.getCount(), Integer::sum);
        }
        return result;
    }

    /** 玩家可见季节只显示可靠中文语义；未知字体令牌统一显示“当前季节”。 */
    String seasonPlayerLabel() {
        for (StardewSeasonService.SeasonToken token : owner.seasonService.snapshot().tokens()) {
            if (token.semantic() != StardewSeasonService.SeasonSemantic.UNKNOWN) {
                return token.semantic().displayName() + "季";
            }
        }
        return "当前季节";
    }

    /**
     * 季节代次变化后逐个重新判定被阻塞的作物。
     *
     * <p>只释放「当前季节已允许播种」的作物：立即播报一次「恢复播种」，其余保持阻塞且不重复播报。
     * 释放后由主循环 Replan 重新判定，全程不需要玩家重开模块。</p>
     *
     * @return true 表示本次确实释放过作物（调用方应立刻重规划）
     */
    boolean releaseSeasonBlocks() {
        if (owner.seasonBlockedCrops.isEmpty()) return false;
        String label = seasonPlayerLabel();
        boolean released = false;
        for (String cropKey : new ArrayList<>(owner.seasonBlockedCrops)) {
            CropDefinition crop = owner.index == null ? null : owner.index.cropByKey(cropKey);
            if (crop != null && owner.seasonService.plantingStatus(crop, owner.inventory.findSeedStack(crop))
                == StardewSeasonService.PlantingStatus.DISALLOWED) continue;
            owner.seasonBlockedCrops.remove(cropKey);
            owner.announcedSeasonBlocks.removeIf(key -> key.startsWith(cropKey + '\u0000'));
            released = true;
            if (crop != null) owner.status.seasonResumed(seasonKey("恢复", cropKey, label), crop.chineseName(), label);
        }
        if (owner.seasonBlockedCrops.isEmpty()) owner.waitingSeasonAnnouncedLabel = null;
        return released;
    }

    /** 季节播报去重键：同服务器 + 同作物 + 同季节 + 同结论 */
    String seasonKey(String kind, String cropKey, String label) {
        return "SEASON_" + kind + ':' + owner.serverKey + '\u0000' + cropKey + '\u0000' + label;
    }
}
