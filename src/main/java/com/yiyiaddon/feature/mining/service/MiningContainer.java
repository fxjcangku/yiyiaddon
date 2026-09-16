package com.yiyiaddon.feature.mining.service;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.platform.network.BlockPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

/**
 * 容器交互助手
 * 
 * 功能：
 * · 静默垃圾丢弃器（分频发包规避反作弊）
 * · 极速卸货流（高速 SlotClick 转移矿物）
 * · 食物提取与自动进食
 *
 * <p>对应旧项目 {@code mining/service/ContainerHelper.java}（584 行）逐条移植：
 * 常量、状态字段、槽位映射与发包语义全部照旧；换掉的两处外部依赖是</p>
 * <ul>
 *   <li>旧 {@code farm.FarmPacketOps.interactBlock} → {@code platform/network/BlockPacketSender.interactBlock}；</li>
 *   <li>旧 Meteor {@code InvUtils.move()/swap()} → 26.1.2 的
 *       {@code handleContainerInput(..., ContainerInput.SWAP, ...)} 与 {@code Inventory#setSelectedSlot}
 *       （与本项目 {@code feature/stardew/adapter/DefaultStardewAdapter} 同一做法）。</li>
 * </ul>
 */
public final class MiningContainer {

    private final AutoMinerModule module;
    private final Minecraft mc;

    private int trashDisposalCooldown = 0;
    private static final int TRASH_DISPOSAL_INTERVAL = 5; // 每5 tick丢一次垃圾

    private AbstractContainerMenu currentMenu = null;
    private int menuStateId = -1;
    private int stableStateTicks = 0;
    private static final int STABLE_REQUIRED = 2;

    private int openAttempts = 0;
    private BlockPos openingPos = null;
    private int openingCooldown = 0;
    private int actionCooldown = 0;
    private int foodCountBeforeWithdraw = -1;
    private int eatMoveCooldown = 0;
    private static final int MAX_OPEN_ATTEMPTS = 5;

    public MiningContainer(AutoMinerModule module) {
        this.module = module;
        this.mc = Minecraft.getInstance();
    }

    public void reset() {
        currentMenu = null;
        menuStateId = -1;
        stableStateTicks = 0;
        foodCountBeforeWithdraw = -1;
        actionCooldown = 0;
        trashDisposalCooldown = 0;
        openAttempts = 0;
        openingPos = null;
        openingCooldown = 0;
        foodCountBeforeWithdraw = -1;
        actionCooldown = 0;
        eatMoveCooldown = 0;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  垃圾丢弃
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 静默垃圾丢弃器（每 tick 调用）
     * 
     * 反逻辑：默认全丢，只保留白名单内的物品
     * 分频发包规避反作弊：每5 tick丢一个物品
     * 
     * @param keepWhitelist 保留白名单（此名单内的物品/方块不会被丢弃）
     * @param placeBlocks Baritone搭路方块白名单（只保留各一组，多余丢弃）
     */
    public void tickTrashDisposal(List<String> keepWhitelist, List<String> placeBlocks) {
        if (mc.player == null) return;

        trashDisposalCooldown--;
        if (trashDisposalCooldown > 0) return;

        Inventory inventory = mc.player.getInventory();

        // 副手优先：有经验修补/工具/食物/目标矿物/保留白名单/搭路方块才保留，其余丢弃
        ItemStack offhand = mc.player.getOffhandItem();
        if (!offhand.isEmpty()
            && !shouldKeep(offhand, keepWhitelist)
            && !isPlaceBlock(offhand, placeBlocks)) {
            dropOffhand();
            trashDisposalCooldown = TRASH_DISPOSAL_INTERVAL;
            return;
        }

        // 主背包 0-35
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty()) continue;

            // 搭路方块：只留一组，超出部分整组丢弃
            if (isPlaceBlock(stack, placeBlocks)) {
                if (countItem(inventory, stack.getItem()) > 64) {
                    dropStack(i);
                    trashDisposalCooldown = TRASH_DISPOSAL_INTERVAL;
                    return;
                }
                continue;
            }

            // 非搭路方块：不在保留白名单内的全部丢弃
            if (shouldKeep(stack, keepWhitelist)) continue;

            dropStack(i);
            trashDisposalCooldown = TRASH_DISPOSAL_INTERVAL;
            return;
        }
    }

    /** 是否为搭路方块白名单内的方块（旧项目按 Block 比较，本项目白名单存方块 ID） */
    private boolean isPlaceBlock(ItemStack stack, List<String> placeBlocks) {
        if (stack.isEmpty() || placeBlocks.isEmpty()) return false;
        Block block = Block.byItem(stack.getItem());
        return placeBlocks.contains(BuiltInRegistries.BLOCK.getKey(block).toString());
    }

    /** 背包中指定物品的总数量（主背包 0-35） */
    private int countItem(Inventory inventory, Item item) {
        int count = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) count += stack.getCount();
        }
        return count;
    }

    /**
     * 是否保留该物品。
     * 默认只留：任意品质工具（镐/铲/斧/剑/锄）、食物白名单、目标矿物、手动保留名单、经验修补物品。
     * 其余全部视为垃圾丢弃。
     */
    private boolean shouldKeep(ItemStack stack, List<String> keepWhitelist) {
        if (stack.isEmpty()) return true;
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        if (isTool(stack)) return true;                                    // 工具（任何品质）
        if (module.getFoodWhitelist().contains(itemId)) return true;        // 食物白名单
        if (isAllowedOre(stack)) return true;                              // 目标矿物
        if (hasMending(stack)) return true;                                // 经验修补附魔（保护好装备）
        return keepWhitelist.contains(itemId);                             // 手动保留名单
    }

    /** 是否为可保留的工具（镐/铲/斧/剑/锄，任意材质） */
    private boolean isTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return id.endsWith("_pickaxe") || id.endsWith("_shovel")
            || id.endsWith("_axe") || id.endsWith("_hoe") || id.endsWith("_sword");
    }

    /** 物品是否带经验修补附魔（26.x 附魔是动态注册表，需从世界注册表解析） */
    private boolean hasMending(ItemStack stack) {
        if (stack.isEmpty() || mc.level == null) return false;
        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null || enchantments.isEmpty()) return false;
        try {
            var lookup = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            var holder = lookup.get(Enchantments.MENDING).orElse(null);
            return holder != null && enchantments.getLevel(holder) > 0;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 丢弃指定槽位的物品（发送丢弃数据包）
     */
    private void dropStack(int slot) {
        if (mc.player == null || mc.gameMode == null) return;

        try {
            ItemStack stack = mc.player.getInventory().getItem(slot);
            if (stack.isEmpty()) return;
            // 玩家背包(containerId=0)的 InventoryMenu 槽位映射：快捷栏 0-8 → 36-44，主背包 9-35 → 9-35。
            // 之前直接传 inventory 下标导致快捷栏丢到错误的槽位（0-8 对应合成/盔甲区），快捷栏垃圾永远丢不掉。
            int menuSlot = slot < 9 ? 36 + slot : slot;
            // button=1 + THROW = 丢弃整组（等价 Ctrl+Q），与 Meteor InvUtils.drop() 同语义
            mc.gameMode.handleContainerInput(0, menuSlot, 1, ContainerInput.THROW, mc.player);
        } catch (Exception e) {
            // 静默失败
        }
    }

    /** 丢弃副手物品（InventoryMenu 中副手槽位固定为 45） */
    private void dropOffhand() {
        if (mc.player == null || mc.gameMode == null) return;
        try {
            if (mc.player.getOffhandItem().isEmpty()) return;
            mc.gameMode.handleContainerInput(0, 45, 1, ContainerInput.THROW, mc.player);
        } catch (Exception e) {
            // 静默失败
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  容器交互
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 打开容器（发送交互包）
     */
    public void openContainer(BlockPos pos) {
        if (mc.player == null || mc.level == null) return;
        int dx = Math.abs(mc.player.blockPosition().getX() - pos.getX());
        int dy = Math.abs(mc.player.blockPosition().getY() - pos.getY());
        int dz = Math.abs(mc.player.blockPosition().getZ() - pos.getZ());
        // 切比雪夫邻域（含对角）：与 MinerFSM.isAdjacentTo 同判定，
        // Baritone 停在对角格时同样允许开箱（interact 包距离校验足够宽松）
        if (dx > 1 || dy > 1 || dz > 1 || (dx | dy | dz) == 0) {
            return;
        }
        if (openingCooldown > 0) {
            openingCooldown--;
            return;
        }
        if (mc.player.containerMenu != null && mc.player.containerMenu.containerId != 0) {
            return;
        }

        openAttempts++;
        if (openAttempts > MAX_OPEN_ATTEMPTS) {
            // 打开失败多次后加长冷却再重试，而不是永久放弃（挂后台/网络抖动时可能连续失败，
            // 永久放弃会导致玩家站在箱子前傻等，切回窗口才能继续）。
            openingCooldown = 30;
            openAttempts = 0;
            return;
        }
        BlockEntity blockEntity = mc.level.getBlockEntity(pos);
        if (!(blockEntity instanceof Container)) {
            return;
        }

        openingPos = pos;
        openingCooldown = 5;

        // 直接发包开箱（带 sequence 预测处理），不依赖 mc.gameMode.useItemOn：
        // 鼠标切出窗口/窗口失焦时 useItemOn 的交互会被吞，导致箱子打不开。
        BlockPacketSender.interactBlock(InteractionHand.MAIN_HAND, pos, Direction.UP);
    }

    /**
     * 关闭容器
     *
     * 只在当前 Screen 确实是容器界面时才关：player.closeContainer() 会无条件关掉
     * 当前打开的任意 Screen，若在 Meteor GUI 打开时调用会把面板一起关掉。
     * 注意 containerMenu 判空没用——玩家自身背包菜单始终非 null。
     */
    public void closeContainer() {
        // 静默模式下没有 Screen，靠 containerMenu 判断是否真的开着容器；
        // inventoryMenu 是玩家自身背包，此时关闭会误发 close 包，无需处理。
        if (mc.player != null && mc.player.containerMenu != null
            && mc.player.containerMenu != mc.player.inventoryMenu) {
            mc.player.closeContainer();
        }
        openAttempts = 0;
        openingPos = null;
        currentMenu = null;
        menuStateId = -1;
        stableStateTicks = 0;
    }

    /**
     * 标点位置是否还存在容器（潜影盒/箱子等）。
     * 潜影盒打包机把盒推走后若没放新盒，此处会返回 false，用于检测「无容器可开」。
     */
    public boolean isContainerAt(BlockPos pos) {
        if (mc.level == null || pos == null) return false;
        return mc.level.getBlockEntity(pos) instanceof Container;
    }

    /**
     * 发送 Shift 快速移动包。
     * 26.1.2 已把旧 clickSlot + SlotActionType 换成
     * MultiPlayerGameMode#handleContainerInput(containerId, slot, button, ContainerInput, player)。
     * Meteor 的 InvUtils.shiftClick 仍走旧 API，在 26.1.2 下发包无效（物品不被移动）。
     */
    private void quickMove(AbstractContainerMenu menu, int slotIndex) {
        ContainerAccess.quickMove(menu, slotIndex);
    }

    /**
     * 容器是否已打开
     */
    public boolean isContainerOpen() {
        if (mc.player == null) return false;

        AbstractContainerMenu menu = mc.player.containerMenu;
        if (menu == null || menu.containerId == 0) {
            return false;
        }

        // 等待 stateId 稳定
        if (currentMenu != menu) {
            currentMenu = menu;
            menuStateId = menu.getStateId();
            stableStateTicks = 0;
            return false;
        }

        if (menu.getStateId() != menuStateId) {
            menuStateId = menu.getStateId();
            stableStateTicks = 0;
            return false;
        }

        stableStateTicks++;
        if (stableStateTicks >= STABLE_REQUIRED) {
            openAttempts = 0; // 容器成功稳定打开，重置开箱尝试计数
            return true;
        }
        return false;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  卸货操作
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 卸货：把背包里的矿物 Shift 点进箱子
     * 
     * 优化策略：等待GUI稳定后按顺序快速放入
     * 
     * @return 是否还有矿物需要继续转移
     */
    public boolean depositOres() {
        if (mc.player == null || mc.gameMode == null) return false;
        if (!isContainerOpen()) {
            // 静默模式下没有 Screen，判断容器是否已开但 stateId 尚未稳定（继续等待）
            return mc.player.containerMenu != null && mc.player.containerMenu.containerId != 0;
        }
        if (actionCooldown > 0) {
            actionCooldown--;
            return true;
        }

        AbstractContainerMenu menu = currentMenu;
        if (menu == null) return false;

        Inventory inventory = mc.player.getInventory();

        // 一次性把所有目标矿 Shift 点进箱子（不再一格一格等冷却），服务端按序处理即可
        int moved = 0;
        for (Slot slot : menu.slots) {
            if (slot.container != inventory) continue;

            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;

            if (isAllowedOre(stack)) {
                quickMove(menu, slot.index);
                moved++;
            }
        }

        if (moved > 0) {
            actionCooldown = 1; // 下一 tick 再补扫一次，防止有遗漏
            return true;
        }
        return false;
    }

    private boolean isAllowedOre(ItemStack stack) {
        if (stack.isEmpty()) return false;
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        // 精准采集按原矿方块判定，时运按掉落物判定（下界残骸掉落物=自身方块，两模式共用）
        if (module.isSilkTouchMode()) {
            return module.getTargetBlockIds().contains(itemId);
        }
        return itemId.equals(module.getTargetDropItemId());
    }

    /**
     * 检测当前容器是否已满（潜影盒所有存储槽均非空）。
     * 脚本直接遍历容器槽位判断满，与打包机的比较器无关；打包机自会检测满后推盒换新盒。
     * 静默模式下直接用 containerMenu，不依赖 Screen，与 isContainerOpen 判定一致。
     */
    public boolean isContainerFull() {
        if (mc.player == null) return false;
        AbstractContainerMenu menu = mc.player.containerMenu;
        if (menu == null || menu.containerId == 0) return false;

        Inventory inventory = mc.player.getInventory();
        for (Slot slot : menu.slots) {
            if (slot.container == inventory) continue; // 跳过玩家背包槽
            if (slot.getItem().isEmpty()) return false; // 存在空槽 → 未满
        }
        return true;
    }

    /**
     * 背包里是否还有目标矿（判断卸货是否彻底放完）。
     * 潜影盒打包机模式下：放完才允许 RTP，没放完就继续换盒重开。
     */
    public boolean hasOreInInventory() {
        if (mc.player == null) return false;
        Inventory inventory = mc.player.getInventory();
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && isAllowedOre(stack)) return true;
        }
        return false;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  补给操作
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 从食物箱提取食物（只拿白名单内的）
     * 
     * 拿满策略：循环 Shift 点击直到背包白名单食物达到「食物阈值」，
     * 或箱子里没有更多白名单食物（拿空即止）。
     * 每格点击间隔 5 tick，等待服务端到账后再拿下一格。
     * 
     * @return true=本次补给结束（已拿满或箱子拿空）；false=还在拿（继续调用）
     */
    public boolean withdrawFood() {
        if (!isContainerOpen() || mc.player == null || mc.gameMode == null) {
            return false;
        }

        if (actionCooldown > 0) {
            actionCooldown--;
            return false;
        }

        int currentFoodCount = countWhitelistedFood();

        // 已拿满（达到食物阈值），结束补给
        if (currentFoodCount >= module.getHungerThreshold()) {
            return true;
        }

        // 上一格等待到账：数量增长才视为成功
        if (foodCountBeforeWithdraw >= 0) {
            if (currentFoodCount > foodCountBeforeWithdraw) {
                foodCountBeforeWithdraw = -1; // 到账，继续拿下一格
            } else {
                return false; // 物品还在服务器端飞行，等下一tick
            }
        }

        AbstractContainerMenu menu = currentMenu;
        if (menu == null) return false;

        Inventory inventory = mc.player.getInventory();
        List<String> whitelist = module.getFoodWhitelist();

        // 扫描箱子侧的槽位
        for (Slot slot : menu.slots) {
            if (slot.container == inventory) continue;

            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;

            // 判断是否为食物且在白名单内
            var foodComp = stack.get(DataComponents.FOOD);
            if (foodComp != null && whitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) {
                foodCountBeforeWithdraw = currentFoodCount;
                quickMove(menu, slot.index);
                actionCooldown = 5;
                return false;
            }
        }

        // 箱子里已没有白名单食物（拿空即止），结束补给
        return true;
    }

    private int countWhitelistedFood() {
        int count = 0;
        List<String> whitelist = module.getFoodWhitelist();
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty()
                && whitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())
                && stack.has(DataComponents.FOOD)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    /**
     * 自动进食直到饥饿值回满。
     *
     * 修正要点：
     * 1. 从背包拿食物到热键栏这一步「只移动、不进食」，等物品到账后再吃，避免空手按住右键一直放方块。
     * 2. 用 gameMode.useItem 直接触发进食，不依赖 keyUse 按键状态（窗口失焦/开 GUI 时按键会被吞）。
     * 3. keyUse.setDown(true) 仅用于防止游戏循环主动 releaseUsingItem，保持持续进食。
     */
    public void autoEat() {
        if (mc.player == null) return;

        FoodData foodData = mc.player.getFoodData();
        if (foodData.getFoodLevel() >= 20) {
            mc.options.keyUse.setDown(false);
            return;
        }

        // 正在等待「背包→热键栏」的移动到账，期间不发重复包，也不按住右键
        if (eatMoveCooldown > 0) {
            eatMoveCooldown--;
            mc.options.keyUse.setDown(false);
            return;
        }

        List<String> foodWhitelist = module.getFoodWhitelist();
        Inventory inventory = mc.player.getInventory();

        // 第一步：热键栏找白名单中营养值最高的食物
        int bestHotbarSlot = -1;
        int bestHotbarNutrition = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || !foodWhitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) continue;
            var foodComp = stack.get(DataComponents.FOOD);
            if (foodComp == null) continue;
            if (foodComp.nutrition() > bestHotbarNutrition) {
                bestHotbarNutrition = foodComp.nutrition();
                bestHotbarSlot = i;
            }
        }

        // 第二步：热键栏没有，从背包拿一个到热键栏（只移动，不进食）
        if (bestHotbarSlot == -1) {
            int bestBackpackSlot = -1;
            int bestBackpackNutrition = 0;
            for (int i = 9; i < 36; i++) {
                ItemStack stack = inventory.getItem(i);
                if (stack.isEmpty() || !foodWhitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) continue;
                var foodComp = stack.get(DataComponents.FOOD);
                if (foodComp == null) continue;
                if (foodComp.nutrition() > bestBackpackNutrition) {
                    bestBackpackNutrition = foodComp.nutrition();
                    bestBackpackSlot = i;
                }
            }

            if (bestBackpackSlot == -1) {
                mc.options.keyUse.setDown(false);
                return;
            }

            // 找一个空热键栏槽位（优先8号位）
            int emptyHotbarSlot = -1;
            for (int i = 8; i >= 0; i--) {
                if (inventory.getItem(i).isEmpty()) {
                    emptyHotbarSlot = i;
                    break;
                }
            }
            if (emptyHotbarSlot == -1) emptyHotbarSlot = 8;

            moveToHotbar(bestBackpackSlot, emptyHotbarSlot);
            eatMoveCooldown = 5; // 等 5 tick 到账
            mc.options.keyUse.setDown(false);
            return;
        }

        // 第三步：切到食物槽，直接触发进食
        inventory.setSelectedSlot(bestHotbarSlot);
        mc.options.keyUse.setDown(true);
        if (!mc.player.isUsingItem()) {
            // 直接 useItem 触发进食（食物使用与准星/方块无关，窗口失焦也能吃到）
            if (mc.gameMode != null) mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
        }
    }

    /**
     * 背包格 → 热键栏格。
     *
     * <p>对应旧项目 {@code InvUtils.move().from(背包槽).to(空热键栏槽)}。26.1.2 的等价原语是
     * {@code ContainerInput.SWAP}：{@code slotNum} 传 InventoryMenu 的主背包槽下标（9~35，与 Inventory
     * 下标同值），{@code buttonNum} 传热键栏下标（0~8）；服务端在 {@code AbstractContainerMenu#doClick}
     * 里按 {@code inventory.getItem(buttonNum)} 与目标槽互换，热键栏为空时即完成整组搬运。</p>
     */
    private void moveToHotbar(int inventorySlot, int hotbarSlot) {
        if (mc.player == null || mc.gameMode == null) return;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, inventorySlot, hotbarSlot,
            ContainerInput.SWAP, mc.player);
    }
}
