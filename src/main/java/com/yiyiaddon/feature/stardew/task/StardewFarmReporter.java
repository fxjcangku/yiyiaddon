package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
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

import java.util.ArrayList;
import java.util.Map;

/**
 * 星露谷播报与范围：任务播报 / 库存快照统计 / 季节阻塞释放 / 农田边界校验。
 *
 * <p>本类由 {@link StardewCoordinator} 机械拆分而来，共享协调器的全部可变状态
 * （通过 {@code owner} 直接读写），行为与拆分前完全一致。</p>
 */
final class StardewFarmReporter {

    /** 已播报过「被挖掉」的点位键：方块恢复或点位重设后自动复位，避免每轮巡检重复刷屏 */
    private final java.util.Set<String> lostPointKeys = new java.util.HashSet<>();

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
     * <p><b>农田起点 / 终点不在此列：</b>它们已有逐 tick 的停机逻辑（{@link #farmBoundaryFailure()}），
     * 重复报会变成两条消息说同一件事。</p>
     */
    void watchPointBlocks() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        for (StardewPointType type : StardewPointType.values()) {
            if (type == StardewPointType.START || type == StardewPointType.END) continue;
            if (type == StardewPointType.SPRINKLER) {
                for (StardewPointManager.StardewPoint point : owner.points.getAll(type)) {
                    watchPointBlock(mc, type, point);
                }
            } else {
                StardewPointManager.StardewPoint point = owner.points.get(type);
                if (point != null) watchPointBlock(mc, type, point);
            }
        }
    }

    /** 会话 / 服务器切换时清空「已播报」记录：换服后同一坐标要能重新报警 */
    void forgetLostPoints() {
        lostPointKeys.clear();
    }

    /** 单点位判定；区块未加载 / 非当前维度一律视为「无法判定」，绝不当成被挖掉 */
    private void watchPointBlock(Minecraft mc, StardewPointType type, StardewPointManager.StardewPoint point) {
        String key = "POINT_LOST:" + type + ':' + point.pos();
        if (!point.inCurrentDimension() || mc.level == null || !mc.level.isLoaded(point.pos())) {
            lostPointKeys.remove(key);
            return;
        }
        boolean gone = mc.level.getBlockState(point.pos()).isAir()
            || (type.requiresContainer() && !(mc.level.getBlockEntity(point.pos()) instanceof Container));
        if (!gone) {
            lostPointKeys.remove(key);
            return;
        }
        if (!lostPointKeys.add(key)) return;
        owner.status.critical(key, type.title() + "已被挖掉",
            "坐标 X" + point.x() + " Y" + point.y() + " Z" + point.z() + "；请重新设置该点位");
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
            case WATER -> owner.status.state("WATER", "正在浇水", "剩余干盆：" + remainingDryPots());
            case REFILL -> owner.status.state("REFILL", "水壶缺水", "正在前往水源");
            case PLANT -> owner.status.state("PLANT:" + cropName, "正在播种", cropName);
            case RESTOCK -> owner.status.state("RESTOCK:" + cropName, "正在补货",
                crop == null ? "目标种子" : crop.seedDisplayName());
            case HARVEST, LEARN_HARVEST -> owner.status.state("HARVEST:" + cropName, "正在收获", cropName);
            case COLLECT -> owner.status.state("COLLECT", "正在拾取", "农田掉落物");
            case UNLOAD -> owner.status.state("UNLOAD:" + cropName, "正在卸货", cropName);
            case CLEAR_DEAD -> owner.status.state("DEAD_CLEAR", "发现枯死作物", "正在清理");
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

    /** 这台刚验过是满的：本轮跳过，只更新状态卡，不刷聊天（一轮最多一条汇总） */
    void announceSprinklerSkipped(BlockPos pos) {
        owner.status.silent("SPRINKLER_SKIP:" + pos, "洒水器已满，本轮跳过", sprinklerLabel(pos), "");
    }

    /** 整轮都是刚验过满的：本轮不跑腿，只给一条聊天提示 */
    void announceSprinklerAllSkipped(int count) {
        owner.status.state("SPRINKLER_SKIP_ROUND", "洒水器本轮全部已满，跳过",
            "共 " + count + " 台 · 到点后自动重查");
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

    /**
     * START / END 在运行时只验证稳定种植盆。真失效立即撤销任务和寻路并保持阻塞，
     * 不进入“导航失败 → Replan”的循环；用户重新设置有效点位后自动恢复观察。
     */
    boolean farmBoundaryFailure() {
        Minecraft mc = Minecraft.getInstance();
        StardewPointManager.StardewPoint start = owner.points.get(StardewPointType.START);
        StardewPointManager.StardewPoint end = owner.points.get(StardewPointType.END);
        String failure = loadedBoundaryFailure(StardewPointType.START, start);
        StardewPointType failedType = StardewPointType.START;
        if (failure == null) {
            failure = loadedBoundaryFailure(StardewPointType.END, end);
            failedType = StardewPointType.END;
        }
        if (failure == null) {
            if (owner.farmBoundaryBlocked) {
                owner.farmBoundaryBlocked = false;
                owner.phase = Phase.OBSERVE;
                owner.scanner.reset();
            }
            return false;
        }
        if (!owner.farmBoundaryBlocked) {
            owner.adapter.cancelPath();
            ContainerAccess.closeContainer();
            owner.broker.reset();
            owner.scanner.reset();
            owner.pending.clear();
            owner.taskType = null;
            owner.targetPot = null;
            owner.activeCell = null;
            owner.farmBoundaryBlocked = true;
        }
        owner.status.critical("BOUNDARY:" + failedType + ':' + failure, failedType.title() + "已失效",
            "请重新设置点位，自动寻路已停止");
        return true;
    }

    /** 远离农田造成区块暂时卸载时不误判基础被破坏；回到已加载范围后才做真实方块复检。 */
    private String loadedBoundaryFailure(StardewPointType type, StardewPointManager.StardewPoint point) {
        Minecraft mc = Minecraft.getInstance();
        if (point == null) return "未绑定";
        if (!java.util.Objects.equals(point.serverKey(), owner.serverKey)) return "点位属于其它服务器";
        if (!point.inCurrentDimension()) return "点位属于其它维度";
        if (mc.level == null || !mc.level.isLoaded(point.pos())) return null;
        return owner.points.validationFailure(type, point, owner.index);
    }
}
