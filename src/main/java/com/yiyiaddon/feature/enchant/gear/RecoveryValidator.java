package com.yiyiaddon.feature.enchant.gear;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 原版装备附魔 · 断点恢复验证器。
 *
 * <p>服务器重启 / 掉线重进 / 换维度 / 死亡后恢复任务时，<b>不得盲目相信旧状态</b>，
 * 必须重新读取实际世界状态：玩家位置、当前维度、当前经验、背包、当前装备、目标装备、
 * 铁砧结果、容器。本类提供恢复前的通用验证。</p>
 */
public final class RecoveryValidator {

    /** 恢复验证结果：是否通过 + 未通过项 */
    public record RecoveryResult(boolean valid, List<String> issues) {
        public static RecoveryResult ok() {
            return new RecoveryResult(true, List.of());
        }

        public static RecoveryResult fail(List<String> issues) {
            return new RecoveryResult(false, issues);
        }
    }

    private RecoveryValidator() {
    }

    /**
     * 恢复前通用验证：玩家在世界、维度匹配、目标装备在背包。
     *
     * @param mc                客户端
     * @param expectedDimension 点位绑定维度（如 minecraft:overworld），可为 null 跳过
     * @param expectedGear      目标装备，可为 null 跳过
     */
    public static RecoveryResult validate(Minecraft mc, String expectedDimension, ItemStack expectedGear) {
        List<String> issues = new ArrayList<>();
        if (mc == null || mc.player == null || mc.level == null) {
            issues.add("玩家不在世界");
            return RecoveryResult.fail(issues);
        }
        if (expectedDimension != null && !expectedDimension.equals(currentDimension(mc))) {
            issues.add("当前维度与点位维度不一致");
        }
        if (expectedGear != null && !hasGearInInventory(mc, expectedGear)) {
            issues.add("目标装备不在背包");
        }
        return issues.isEmpty() ? RecoveryResult.ok() : RecoveryResult.fail(issues);
    }

    /** 当前维度标识（如 minecraft:overworld） */
    public static String currentDimension(Minecraft mc) {
        return mc.level.dimension().identifier().toString();
    }

    /** 当前经验等级 */
    public static int currentXp(Minecraft mc) {
        return mc.player == null ? 0 : mc.player.experienceLevel;
    }

    /** 目标装备是否仍在主背包（按物品类型匹配） */
    public static boolean hasGearInInventory(Minecraft mc, ItemStack expected) {
        if (mc.player == null || expected == null || expected.isEmpty()) return false;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.is(expected.getItem())) return true;
        }
        return false;
    }
}
