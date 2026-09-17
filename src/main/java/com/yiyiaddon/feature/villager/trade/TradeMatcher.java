package com.yiyiaddon.feature.villager.trade;

import com.yiyiaddon.feature.villager.model.VillagerTradeTarget;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;

/**
 * 交易匹配器
 * 
 * 根据目标白名单和价格限制筛选 MerchantOffer。
 * 
 * 匹配规则：
 * · 输出物品必须在目标白名单内
 * · 绿宝石价格 <= 价格上限
 * · 交易未售罄
 * · 普通物品通过 Item 匹配
 * · 附魔书通过 EnchantmentMatcher 三重匹配
 */
public final class TradeMatcher {

    /**
     * 判断交易是否匹配目标列表
     * 
     * @param offer 村民交易
     * @param targets 目标白名单
     * @param maxPrice 绿宝石价格上限
     * @return true 表示匹配
     */
    public static boolean matches(MerchantOffer offer, List<VillagerTradeTarget> targets, int maxPrice) {
        // 基础验证
        if (offer == null || offer.isOutOfStock()) {
            return false;
        }

        // 价格验证（绿宝石数量）
        int emeraldCost = getEmeraldCost(offer);
        if (emeraldCost > maxPrice) {
            return false;
        }

        // 输出物品验证
        ItemStack result = offer.getResult();
        if (result.isEmpty()) {
            return false;
        }

        // 遍历目标白名单
        for (VillagerTradeTarget target : targets) {
            if (matchesTarget(result, target)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 只判断交易输出物品是否命中目标白名单（忽略售罄与价格，用于区分「未刷出」与「售罄/超价」）。
     */
    public static boolean matchesItemOnly(MerchantOffer offer, List<VillagerTradeTarget> targets) {
        if (offer == null) return false;
        ItemStack result = offer.getResult();
        if (result.isEmpty()) return false;
        for (VillagerTradeTarget target : targets) {
            if (matchesTarget(result, target)) return true;
        }
        return false;
    }

    /**
     * 判断 ItemStack 是否匹配单个目标
     */
    private static boolean matchesTarget(ItemStack stack, VillagerTradeTarget target) {
        // 物品类型必须匹配
        if (stack.getItem() != target.getItem()) {
            return false;
        }

        // 附魔书需要进一步匹配附魔和等级
        if (target.isEnchantedBook()) {
            return EnchantmentMatcher.matches(stack, target);
        }

        // 普通物品：Item 匹配即可
        return true;
    }

    /**
     * 计算交易的绿宝石实际成本（含 demand 涨价）。
     *
     * <p>MerchantOffer 的 getBaseCostA() 返回的是不含 demand 的基础价，
     * 村民被大量购买后 demand 上涨会让实际价格远超基础价，用基础价判断价格上限会漏买超价物品。
     * 因此这里改用 getCostA()/getCostB()（getCostA 已叠加 demand 与 specialPriceDiff）。</p>
     *
     * @param offer 村民交易
     * @return 绿宝石实际数量
     */
    public static int getEmeraldCost(MerchantOffer offer) {
        ItemStack baseCost = offer.getCostA();

        // 绿宝石在主要成本（「绿宝石 → 物品」这类交易）
        if (!baseCost.isEmpty() && baseCost.is(Items.EMERALD)) {
            return baseCost.getCount();
        }

        // 如果主要成本不是绿宝石，检查次要成本（「物品 + 绿宝石 → 物品」这类交易）
        ItemStack costB = offer.getCostB();
        if (!costB.isEmpty() && costB.is(Items.EMERALD)) {
            return costB.getCount();
        }

        // 都不是绿宝石，返回 0（此交易不消耗绿宝石）
        return 0;
    }

    /**
     * 获取交易详情（用于调试）
     * 
     * @param offer 村民交易
     * @return 交易描述字符串
     */
    public static String getOfferDetails(MerchantOffer offer) {
        if (offer == null) {
            return "null";
        }

        ItemStack baseCost = offer.getCostA();
        ItemStack costB = offer.getCostB();
        ItemStack result = offer.getResult();

        // 玩家只应看到物品中文名；Item 的 toString() 是 "Item{minecraft:xxx}"，不能直接拼给玩家
        String costStr = baseCost.getCount() + "x" + baseCost.getHoverName().getString();
        if (!costB.isEmpty()) {
            costStr += " + " + costB.getCount() + "x" + costB.getHoverName().getString();
        }

        String resultStr = result.getCount() + "x" + result.getHoverName().getString();
        String stockStr = offer.isOutOfStock() ? " [售罄]" : " [剩余:" + (offer.getMaxUses() - offer.getUses()) + "]";

        return costStr + " → " + resultStr + stockStr;
    }

    private TradeMatcher() {
        // 工具类禁止实例化
    }
}
