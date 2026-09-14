package com.yiyiaddon.feature.stardew.task;

import com.yiyiaddon.feature.stardew.navigation.ContainerApproachPlanner;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator.Phase;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator.RestockBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.CONTAINER_STAND_REACH;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.MAX_RETRY;
import static com.yiyiaddon.feature.stardew.task.StardewCoordinator.MAX_TRANSFER_BURST;

/**
 * 星露谷容器后勤：补货 / 卸货 / 种子回收的静默开箱事务与空箱负缓存。
 *
 * <p>本类由 {@link StardewCoordinator} 机械拆分而来，共享协调器的全部可变状态
 * （通过 {@code owner} 直接读写），行为与拆分前完全一致。</p>
 */
final class StardewContainerLogistics {

    private final StardewCoordinator owner;

    StardewContainerLogistics(StardewCoordinator owner) {
        this.owner = owner;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  后勤交互（补货 / 卸货）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    void interactLogistics() {
        if (owner.targetContainer == null || owner.containerApproach == null || owner.activeCrop == null
            || !owner.selectedCropKeys.contains(owner.activeCrop.cropKey())) {
            owner.phase = Phase.REPLAN;
            return;
        }
        StardewPointType pointType = owner.taskType == TaskType.UNLOAD ? StardewPointType.OUTPUT_BOX : StardewPointType.SEED_BOX;
        String pointFailure = owner.points.validationFailure(pointType, owner.points.get(pointType), owner.index);
        if (pointFailure != null) {
            owner.status.critical("LOGISTICS_POINT:" + pointType + ':' + pointFailure,
                pointType.title() + "点位失效", "已暂停当前后勤任务");
            owner.phase = Phase.REPLAN;
            return;
        }
        switch (owner.taskStep) {
            case 0 -> {
                if (!ContainerApproachPlanner.readyToInteract(
                    owner.containerApproach, CONTAINER_STAND_REACH, owner.reach)) {
                    owner.phase = Phase.NAVIGATE;
                    return;
                }
                owner.broker.reset();
                BlockPos interactPos = owner.containerApproach.interactPos();
                if (!owner.adapter.face(interactPos)
                    || !owner.adapter.interactBlock(InteractionHand.MAIN_HAND, interactPos, Direction.UP)) {
                    owner.retryCount++;
                    if (owner.retryCount >= MAX_RETRY) {
                        owner.verifier.markLogisticsBlocked("箱子交互发包失败");
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
                    if (owner.taskType == TaskType.RESTOCK) refreshRestockBlocksFromOpenSeedBox();
                    owner.taskStep = 2;
                    owner.taskTicks = 0;
                } else if (owner.taskTicks > 40) {
                    ContainerAccess.closeContainer();
                    owner.broker.reset();
                    owner.retryCount++;
                    if (owner.retryCount >= MAX_RETRY) {
                        owner.verifier.markLogisticsBlocked("后台箱子菜单未同步");
                        owner.phase = Phase.REPLAN;
                    } else {
                        owner.taskStep = 0;
                        owner.taskTicks = 0;
                    }
                }
            }
            case 2 -> {
                boolean moved = switch (owner.taskType) {
                    case RESTOCK -> withdrawSeedOne(owner.activeCrop);
                    case SEED_RETURN -> depositExcessSeedOne(owner.activeCrop);
                    default -> depositAnyProduceOne();
                };
                if (moved) {
                    owner.taskTicks = 0;
                } else {
                    boolean complete = switch (owner.taskType) {
                        case RESTOCK -> owner.inventory.countSeed(owner.activeCrop)
                            >= owner.logisticsOf(owner.activeCrop.cropKey()).restockTarget();
                        case SEED_RETURN -> owner.planner.seedReturnComplete(owner.activeCrop);
                        default -> allUnloadComplete();
                    };
                    if (!complete) owner.verifier.markLogisticsBlocked();
                    owner.taskStep = 3;
                    owner.taskTicks = 0;
                }
            }
            case 3 -> {
                ContainerAccess.closeContainer();
                owner.broker.reset();
                owner.phase = Phase.VERIFY;
                owner.stepTick = 0;
            }
        }
    }

    private boolean withdrawSeedOne(CropDefinition crop) {
        if (!owner.broker.isReady()) return false;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null) return false;
        if (owner.inventory.countSeed(crop) >= owner.logisticsOf(crop.cropKey()).restockTarget()) return false;

        AbstractContainerMenu menu = ContainerAccess.openMenu();
        if (menu == null) return false;
        var inv = mc.player.getInventory();
        for (Slot slot : menu.slots) {
            if (slot.container == inv) continue;
            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;
            if (owner.inventory.matchesSeed(stack, crop)) {
                int needed = owner.logisticsOf(crop.cropKey()).restockTarget() - owner.inventory.countSeed(crop);
                return transferUpTo(menu, slot, needed, true);
            }
        }
        return false;
    }

    private boolean depositProduceOne(CropDefinition crop) {
        if (owner.inventory.countProduce(crop) <= owner.logisticsOf(crop.cropKey()).unloadKeep()) return false;
        var mc = Minecraft.getInstance();
        var menu = ContainerAccess.openMenu();
        if (menu == null || mc.player == null || !owner.broker.isReady()) return false;
        for (Slot slot : menu.slots) {
            ItemStack stack = slot.getItem();
            if (slot.container != mc.player.getInventory() || !owner.inventory.matchesProduce(stack, crop)) continue;
            if (owner.index.crops().stream().anyMatch(known -> owner.inventory.matchesSeed(stack, known))) continue;
            int excess = owner.inventory.countProduce(crop) - owner.logisticsOf(crop.cropKey()).unloadKeep();
            return transferUpTo(menu, slot, excess, false);
        }
        return false;
    }

    /**
     * 把背包里多余的种子存回种子箱，只保留「种子补货目标」的数量。
     *
     * <p>与 {@link #withdrawSeedOne} 完全对称：一边取到目标值，一边存回目标值，因此背包种子数
     * 会在该值附近稳定，不再被收获持续推高。</p>
     */
    private boolean depositExcessSeedOne(CropDefinition crop) {
        int excess = owner.inventory.countSeed(crop) - owner.planner.seedReturnKeep(crop);
        if (excess <= 0) return false;
        var mc = Minecraft.getInstance();
        var menu = ContainerAccess.openMenu();
        if (menu == null || mc.player == null || !owner.broker.isReady()) return false;
        for (Slot slot : menu.slots) {
            if (slot.container != mc.player.getInventory()) continue;
            ItemStack stack = slot.getItem();
            if (stack.isEmpty() || !owner.inventory.matchesSeed(stack, crop)) continue;
            return transferUpTo(menu, slot, excess, false);
        }
        return false;
    }

    /**
     * 把源格物品移出，最多 {@code limit} 个。
     *
     * <p>整叠数量不超过 limit 时用 shift 整叠移出；否则拾起整叠、在同一次交互内连发右键逐个放入，
     * 余量再放回源格——一次把需要移出的量搬完，不再每 tick 只挪一个。
     * 连发上限 {@link #MAX_TRANSFER_BURST} 个/次，避免单 tick 发送过多点击。</p>
     */
    private boolean transferUpTo(AbstractContainerMenu menu, Slot source, int limit, boolean toPlayer) {
        var mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null || !menu.getCarried().isEmpty()) return false;
        ItemStack moving = source.getItem();
        if (moving.isEmpty() || limit <= 0) return false;
        Slot target = null;
        for (Slot candidate : menu.slots) {
            if ((candidate.container == mc.player.getInventory()) != toPlayer || !candidate.mayPlace(moving)) continue;
            ItemStack existing = candidate.getItem();
            if (existing.isEmpty() || (ItemStack.isSameItemSameComponents(existing, moving)
                && existing.getCount() < candidate.getMaxStackSize(moving))) { target = candidate; break; }
        }
        if (target == null) return false;
        if (limit >= moving.getCount()) {
            mc.gameMode.handleContainerInput(menu.containerId, source.index, 0, ContainerInput.QUICK_MOVE, mc.player);
            return true;
        }
        int burst = Math.min(limit, MAX_TRANSFER_BURST);
        mc.gameMode.handleContainerInput(menu.containerId, source.index, 0, ContainerInput.PICKUP, mc.player);
        for (int i = 0; i < burst; i++) {
            if (menu.getCarried().isEmpty()) break;
            mc.gameMode.handleContainerInput(menu.containerId, target.index, 1, ContainerInput.PICKUP, mc.player);
        }
        if (!menu.getCarried().isEmpty()) {
            mc.gameMode.handleContainerInput(menu.containerId, source.index, 0, ContainerInput.PICKUP, mc.player);
        }
        return true;
    }

    /** 满成品箱仍使用短时有界退避；它不涉及无种时的反复白跑问题。 */
    boolean logisticsBackedOff(Map<String, Long> blockedUntil, String cropKey) {
        Long until = blockedUntil.get(cropKey);
        if (until == null) return false;
        if (System.currentTimeMillis() >= until) {
            blockedUntil.remove(cropKey);
            return false;
        }
        return true;
    }

    /** 一次静默开箱事务连续处理全部已选作物，避免多作物重复开关箱和逐作物刷完成消息。 */
    private boolean depositAnyProduceOne() {
        for (CropDefinition crop : owner.planner.targetCrops()) {
            if (depositProduceOne(crop)) return true;
        }
        return false;
    }

    boolean allUnloadComplete() {
        for (CropDefinition crop : owner.planner.targetCrops()) {
            if (owner.inventory.countProduce(crop) > owner.logisticsOf(crop.cropKey()).unloadKeep()) return false;
        }
        return true;
    }

    /** 空种子箱以真实状态变化解锁；仅在客户端无法得知关闭箱更新时保留十五分钟保险重查。 */
    boolean restockBackedOff(CropDefinition crop) {
        RestockBlock blocked = owner.restockBlocks.get(crop.cropKey());
        if (blocked == null) return false;
        StardewPointManager.StardewPoint seedBox = owner.points.get(StardewPointType.SEED_BOX);
        BlockPos currentBox = seedBox == null ? null : seedBox.pos();
        boolean changed = owner.inventory.countSeed(crop) > blocked.seedCountAtBlock()
            || hasLoadedSeedDrop(crop)
            || !java.util.Objects.equals(currentBox, blocked.seedBoxPos())
            || System.currentTimeMillis() >= blocked.safetyRetryAt();
        if (changed) {
            owner.restockBlocks.remove(crop.cropKey());
            owner.lastBlockedSeedCounts.remove(crop.cropKey());
            return false;
        }
        return true;
    }

    /** 每 tick 只做已加载实体/背包的只读事件检查，不增加扫描频率也不触碰关闭箱。 */
    void refreshRestockBlocks() {
        if (owner.restockBlocks.isEmpty() || owner.inventory == null || owner.index == null) return;
        for (String cropKey : List.copyOf(owner.restockBlocks.keySet())) {
            CropDefinition crop = owner.index.cropByKey(cropKey);
            if (crop == null || !owner.selectedCropKeys.contains(cropKey)) {
                owner.restockBlocks.remove(cropKey);
                owner.lastBlockedSeedCounts.remove(cropKey);
            } else {
                int current = owner.inventory.countSeed(crop);
                Integer previous = owner.lastBlockedSeedCounts.put(cropKey, current);
                if (previous != null && current > previous) owner.restockBlocks.remove(cropKey);
                else restockBackedOff(crop);
            }
        }
    }

    /** 地面出现目标种子是箱外真实新证据，立即解除该 cropKey 的空箱负缓存。 */
    private boolean hasLoadedSeedDrop(CropDefinition crop) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return false;
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity instanceof ItemEntity item && owner.inventory.matchesSeed(item.getItem(), crop)) return true;
        }
        return false;
    }

    /** 同一静默种子箱因其它作物被打开时，菜单内容就是可靠更新证据，可解除相关负缓存。 */
    private void refreshRestockBlocksFromOpenSeedBox() {
        AbstractContainerMenu menu = ContainerAccess.openMenu();
        Minecraft mc = Minecraft.getInstance();
        if (menu == null || mc.player == null || owner.restockBlocks.isEmpty()) return;
        var playerInventory = mc.player.getInventory();
        for (String cropKey : List.copyOf(owner.restockBlocks.keySet())) {
            CropDefinition crop = owner.index.cropByKey(cropKey);
            if (crop == null) continue;
            for (Slot slot : menu.slots) {
                if (slot.container == playerInventory) continue;
                if (owner.inventory.matchesSeed(slot.getItem(), crop)) {
                    owner.restockBlocks.remove(cropKey);
                    owner.lastBlockedSeedCounts.remove(cropKey);
                    break;
                }
            }
        }
    }
}
