package com.yiyiaddon.feature.stardew.adapter;

import com.yiyiaddon.model.identity.ItemIdentity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Set;

/**
 * 星露谷世界交互适配器接口。
 *
 * <p>把「移动 / 破坏 / 使用 / 物品栏切换」抽象成接口，隔离 Baritone 与发包层，
 * 便于将来替换导航实现或接入测试替身。默认实现走 {@link DefaultStardewAdapter}。</p>
 */
public interface StardewAdapter {

    // ── 移动 ──

    /** 前往目标坐标附近（停靠半径 radius） */
    boolean goTo(BlockPos pos, int radius);

    /** 更新当前农田内经资源身份与空碰撞双重确认的可穿行载体 */
    void updatePassableCarriers(Set<Block> carriers);

    /** 是否正在寻路 */
    boolean pathing();

    /** 取消寻路 */
    void cancelPath();

    /** 玩家是否已到达目标附近（reach 为交互距离） */
    boolean arrived(BlockPos pos, double reach);

    /** 静默同步服务端视角到目标，避免补水点近身后仍因朝向错误拒绝交互 */
    boolean face(BlockPos pos);

    // ── 世界交互 ──

    /** 破坏方块（收割用） */
    boolean breakBlock(BlockPos pos, Direction face);

    /** 对方块使用物品（播种 / 浇水用，作用于目标方块上方） */
    boolean useOnBlock(InteractionHand hand, BlockPos soilPos);

    /** 对方块本体右键（开箱 / 补水点交互） */
    boolean interactBlock(InteractionHand hand, BlockPos pos, Direction face);

    // ── 物品栏 ──

    /** 在快捷栏（0-8）查找命中身份的物品槽，未找到返回 -1 */
    int findHotbar(ItemIdentity identity);

    /** 在整个背包（0-35）查找命中身份的物品槽，未找到返回 -1 */
    int findAny(ItemIdentity identity);

    /** 切换快捷栏选中槽 */
    void selectHotbar(int slot);

    /** 把背包槽（0-35）换到当前快捷栏选中槽 */
    boolean swapToHotbar(int invSlot);

    /** 当前主手物品 */
    ItemStack heldItem();
}
