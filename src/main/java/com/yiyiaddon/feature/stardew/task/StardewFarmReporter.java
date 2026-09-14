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

    private final StardewCoordinator owner;

    StardewFarmReporter(StardewCoordinator owner) {
        this.owner = owner;
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
            case SPRINKLER_CHECK, SPRINKLER_REFILL -> owner.status.state("SPRINKLER", "正在维护洒水器", "");
        }
        captureTaskInventory();
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
