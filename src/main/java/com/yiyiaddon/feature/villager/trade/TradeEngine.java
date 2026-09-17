package com.yiyiaddon.feature.villager.trade;

import com.yiyiaddon.feature.villager.model.VillagerTradeTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 交易引擎（非阻塞工具类，全部在渲染线程调用）
 * 
 * 26.1.2 交易协议要点：
 * · ServerboundSelectTradePacket 只携带 offer 序号，服务端要求玩家当前
 *   containerMenu 必须是 MerchantMenu，未打开村民交易界面时发包会被直接忽略。
 * · 因此交易必须在打开村民交易界面后执行，本类只负责「查找 / 发包 / 计数」，
 *   打开界面与结果确认由状态机驱动，禁止在 tick 内 sleep。
 */
public final class TradeEngine {

    private TradeEngine() {
    }

    /**
     * 查找本次应交易的 offer 序号（单次遍历，选择与成本判断严格一致）。
     *
     * <p>过滤条件：未列入黑名单、未售罄、价格不超上限、输出物品匹配目标白名单。</p>
     *
     * <p>轮换策略：优先买「已购数量最少」的目标物品，同类物品内部再选最便宜的报价。
     * 这样勾选多个目标物品时能轮流买入，而不是一直买最便宜的那一种。</p>
     *
     * <p>客户端只能从 MerchantMenu（村民交易界面）读取 offer，禁止调用
     * Villager.getOffers()（会抛 IllegalStateException: Cannot load Villager offers on the client）。</p>
     *
     * @param offers           交易界面同步下来的报价列表（来自 MerchantMenu.getOffers()）
     * @param perItemPurchased 每个物品本任务累计已购数量（用于轮换，缺失视为 0）
     * @return offer 序号，无可交易项返回 -1
     */
    public static int findBestOfferIndex(MerchantOffers offers, List<VillagerTradeTarget> targets, int maxPrice,
                                         Set<Integer> skipIndexes, Map<Item, Integer> perItemPurchased) {
        if (offers == null || offers.isEmpty()) return -1;

        int bestIndex = -1;
        int bestPurchased = Integer.MAX_VALUE;
        int bestCost = Integer.MAX_VALUE;
        for (int i = 0; i < offers.size(); i++) {
            if (skipIndexes.contains(i)) continue;

            MerchantOffer offer = offers.get(i);
            if (offer.isOutOfStock()) continue;
            if (!TradeMatcher.matches(offer, targets, maxPrice)) continue;

            int purchased = perItemPurchased.getOrDefault(offer.getResult().getItem(), 0);
            int cost = TradeMatcher.getEmeraldCost(offer);
            // 优先选已购最少的物品实现轮换；同一物品内部选最便宜
            if (purchased < bestPurchased || (purchased == bestPurchased && cost < bestCost)) {
                bestPurchased = purchased;
                bestCost = cost;
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    /**
     * 发送交易选择包（调用方保证村民交易界面已打开）。
     *
     * <p>为什么不能省：服务端 {@code handleSelectTrade} 只在当前 containerMenu 是 MerchantMenu
     * 时才认可该包，界面未开时这是空包。此发包不做任何本地容器改动，真正的成交由
     * {@link #executeTrade(int)} 的 QUICK_MOVE 触发。</p>
     */
    public static void sendSelectTrade(int offerIndex) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || player.connection == null) return;

        // 交易选择包本身不依赖挥手动画，取消 swing 避免每笔交易都有手臂挥动视觉残留
        player.connection.send(new ServerboundSelectTradePacket(offerIndex));
    }

    /**
     * 执行一笔完整交易（一次性买走最大量）。
     *
     * <p>26.1.2 的村民交易协议需要两步，缺一不可：
     * <ol>
     *   <li>选中交易：客户端本地把付款物品挪进付款槽，并向服务端发送 SelectTrade。
     *       服务端 {@code handleSelectTrade} 只会「选中 + 挪付款物品」，并不会真正成交。</li>
     *   <li>shift 点击结果槽（QUICK_MOVE）：{@code AbstractContainerMenu.doClick} 对
     *       QUICK_MOVE 会进入 {@code while} 循环反复调用 {@code quickMoveStack}，
     *       每次 {@code quickMoveStack} 末尾 {@code slot.onTake} 触发 {@code offer.take} 真正成交一次，
     *       直到绿宝石耗尽/背包满/售罄自动停止 —— 即按住 shift 的「快速买满」效果。</li>
     * </ol>
     *
     * <p>结果经 {@code moveItemStackTo} 自动合并进背包同类堆叠，无需再手动点背包槽。</p>
     *
     * @param offerIndex 交易序号（来自 {@link MerchantMenu#getOffers()}）
     */
    public static void executeTrade(int offerIndex) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.gameMode == null || player.connection == null) return;
        if (!(player.containerMenu instanceof MerchantMenu menu)) return;

        // 第一步：选中交易并同步付款物品（本地 + 服务端各执行一遍）
        menu.setSelectionHint(offerIndex);
        menu.tryMoveItems(offerIndex);
        sendSelectTrade(offerIndex);

        // 第二步：shift 点击结果槽（QUICK_MOVE），服务端循环快速购买，一次买走最大量
        mc.gameMode.handleContainerInput(menu.containerId, 2, 0, ContainerInput.QUICK_MOVE, player);
    }

    /**
     * 统计玩家背包（含副手与盔甲槽）中指定物品的数量。
     */
    public static int countItem(Item item) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return 0;

        int count = 0;
        var inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) {
                count += stack.getCount();
            }
        }
        // 副手也计入：绿宝石/目标物品可能拿在副手，漏计会导致绿宝石差额误判
        ItemStack offhand = player.getOffhandItem();
        if (!offhand.isEmpty() && offhand.getItem() == item) count += offhand.getCount();
        return count;
    }

    /**
     * 统计玩家背包绿宝石数量。
     */
    public static int countEmeralds() {
        return countItem(Items.EMERALD);
    }

    /**
     * 主背包（0-35）是否还有空槽位。
     */
    public static boolean hasSpace() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return false;

        for (int i = 0; i < 36; i++) {
            if (player.getInventory().getItem(i).isEmpty()) return true;
        }
        return false;
    }

    /**
     * 读当前村民 offer 序号对应的结果数量（offers 可能已被服务端刷新替换，需防御越界）。
     */
    public static int safeResultCount(MerchantOffers offers, int offerIndex) {
        if (offers == null || offerIndex < 0 || offerIndex >= offers.size()) return 1;

        ItemStack result = offers.get(offerIndex).getResult();
        return result.isEmpty() ? 1 : result.getCount();
    }
}
