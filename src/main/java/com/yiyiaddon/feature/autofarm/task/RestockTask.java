package com.yiyiaddon.feature.autofarm.task;

import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.feature.autofarm.resource.FarmResourceManager;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.service.container.ContainerService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;

/**
 * 补货任务：从当前模式专用作物箱提取某个作物的种植材料，直到达到安全库存。
 *
 * 只补具体缺的那一种资源；箱子无货时返回 CONTAINER_EMPTY（明确资源耗尽），
 * 绝不把「有其它作物种子」当成「这个作物也补好了」。
 *
 * 取货采用「等待到账 + 本地菜单判空」双保险：快速移动后本地菜单立即变化而真实背包
 * 要等服务端同步，若下一 tick 直接按真实背包数量判断会误以为「没取到 / 箱子空」。
 */
public final class RestockTask extends ContainerTask {

    private final FarmResourceManager resources;
    private final CropProfile crop;

    /** 取货后等待真实背包同步到账的冷却（tick） */
    private int syncCooldown = 0;

    public RestockTask(BlockPos boxPos, ContainerService broker, double reachDistance,
                       FarmResourceManager resources, CropProfile crop) {
        super(boxPos, broker, reachDistance);
        this.resources = resources;
        this.crop = crop;
    }

    /** 当前补货的作物，供 Controller 在箱子无货时抑制补货 */
    public CropProfile crop() {
        return crop;
    }

    @Override
    protected TaskResult transfer() {
        Item plantItem = crop.plantItem();

        // 冷却中：等待上一次取货到账，避免本地菜单已空而真实背包未同步造成误判
        if (syncCooldown > 0) {
            syncCooldown--;
            return TaskResult.IN_PROGRESS;
        }

        // 已补到安全库存 → 完成
        if (resources.countItem(plantItem) >= resources.safetyStock(crop)) {
            broker.close();
            return TaskResult.SUCCESS;
        }

        // 从箱子提取一个种植材料
        if (broker.withdrawOne(plantItem)) {
            syncCooldown = 2; // 等 2 tick 让服务端同步真实背包
            return TaskResult.IN_PROGRESS;
        }

        // 取不出：用本地菜单确认箱子侧是否真的没有该物品，避免把「未同步/背包满」误判为箱子空
        if (countInChest(plantItem) == 0) {
            broker.close();
            return TaskResult.CONTAINER_EMPTY;
        }
        // 箱子侧还有该物品但暂时取不出，继续等待（交由物流优先级与卸货兜底）
        return TaskResult.IN_PROGRESS;
    }

    /** 统计箱子侧指定物品总数（本地菜单，与 withdrawOne 同源） */
    private int countInChest(Item item) {
        AbstractContainerMenu menu = ContainerAccess.openMenu();
        if (menu == null) return 0;
        return ContainerAccess.countInContainer(menu, item);
    }
}
