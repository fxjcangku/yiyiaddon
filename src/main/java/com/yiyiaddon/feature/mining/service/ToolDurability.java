package com.yiyiaddon.feature.mining.service;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 工具耐久口径：可修复工具判据 + 满耐久取值。
 *
 * <p><b>为什么要有这个类（第 169 条：同类逻辑只留一份）</b>：挖矿状态机的修复判据与控制台
 * 「耐久阈值」输入框的上限必须是同一套判据。上限<b>不能写死</b>（用户 2026-09-19：「你要自动算手里
 * 拿的镐子自动计算耐久度」）：写死的值一旦高于工具满耐久（例如 3000 &gt; 下界合金镐 2031），
 * 「剩余耐久低于阈值」就恒为真，工具只要不是满耐久就被判需修复，修完回矿区又立刻被判需修复 ——
 * 实机表现就是无限循环往返挂机点。</p>
 */
public final class ToolDurability {

    private ToolDurability() {
    }

    /** 单件工具的满耐久；方块 / 食物 / 空手这类没有最大耐久的返回 0 */
    public static int maxDurability(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return 0;
        Integer max = stack.get(DataComponents.MAX_DAMAGE);
        return max == null ? 0 : max;
    }

    /** 可修复工具：镐 / 锹 / 斧 / 锄 / 剑，且有最大耐久（旧 {@code isRepairableTool}，{@code :1560-1567} 判据逐字） */
    public static boolean isRepairableTool(ItemStack stack) {
        if (maxDurability(stack) == 0) return false;
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return id.endsWith("_pickaxe") || id.endsWith("_shovel") || id.endsWith("_axe")
            || id.endsWith("_hoe") || id.endsWith("_sword");
    }

    /**
     * 手持工具的满耐久：<b>主手优先，主手不是工具时看副手</b>；两边都不是可修复工具（空手 / 拿着方块）时返回 0。
     *
     * <p>用作「耐久阈值」输入框的动态上限（用户 2026-09-19 拍板：「上限改成只看手持那把」，
     * 随后确认「副手也算作手持」）：手里是什么，上限就是它的满耐久 —— 木镐 59 / 石镐 131 / 铁镐 250 /
     * 钻石镐 1561 / 下界合金镐 2031，服务器自定义工具按其 {@code max_damage} 组件实际值走。</p>
     *
     * <p><b>为什么把副手也算进来</b>：本模块修复时会把要修的镐换到副手槽（修满再还回主手），
     * 习惯把镐常驻副手的玩家，配置阈值那一刻主手可能是空的；只认主手就会退回兜底上限 2031，
     * 与手里那把镐的满耐久对不上。仍然不扫背包 —— 避免「背包里放着一把合金镐，就给手里的木镐
     * 设了个远超它满耐久的阈值」。</p>
     */
    public static int heldToolDurability(Player player) {
        if (player == null) return 0;
        ItemStack mainHand = player.getMainHandItem();
        if (isRepairableTool(mainHand)) return maxDurability(mainHand);
        ItemStack offHand = player.getOffhandItem();
        return isRepairableTool(offHand) ? maxDurability(offHand) : 0;
    }
}
