package com.yiyiaddon.feature.bonemeal.service;

import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.feature.bonemeal.config.BonemealSettings;
import com.yiyiaddon.platform.container.InventoryAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

/**
 * 催熟执行体：找骨粉 → 静默视角同步 → 对方块使用骨粉 → 挥手。
 *
 * <p><b>逐字搬运</b>：方法体逐字照旧项目 {@code bonemeal/AutoBoneMeal.java} 的
 * {@code findBoneMealHand:581-598}、{@code sendSilentRotation:604-613} 与 {@code onTick:449-468}
 * 里的发包三步（视角包 → {@code BlockHitResult} 朝上、{@code inside=false} → {@code useItemOn} →
 * 仅当 {@code consumesAction()} 才挥手）。</p>
 *
 * <p><b>框架适配（旧 → 新，逐条）</b></p>
 * <ol>
 *     <li><b>背包查找</b>：旧 {@code InvUtils.findInHotbar / find / swap / move().toHotbar(selected)}
 *         → {@link InventoryAccess}。旧 {@code InvUtils.find(Items.BONE_MEAL)} 默认全搜 0~35 共 36 格，
 *         故本项目扫描上界取 36；{@code swap(slot,false)} 与 {@code move().toHotbar(选中槽)}
 *         在本项目都落到 {@link InventoryAccess#swapWithSelectedHotbar(int)}
 *         （换到当前选中槽 = 握进主手，选中槽下标不变，与旧语义一致）。</li>
 *     <li><b>视角包</b>：旧直接 {@code mc.getConnection().send(new ServerboundMovePlayerPacket.Rot(...))}
 *         → {@link ClientPacketSender#sendMoveRotation}（走核心的语义直发通道，包体同构）；
 *         yaw / pitch 计算公式逐字未改。</li>
 *     <li><b>物品使用</b>：旧 {@code mc.gameMode.useItemOn(player, hand, hit)} 原样保留
 *         （这里走原版链路而非直发：骨粉催熟需要客户端预测与物品消耗同步，
 *         与 {@code BlockPlacer} 的放置口径一致）。</li>
 * </ol>
 *
 * <p><b>线程</b>：全部在主线程（客户端刻）调用。</p>
 */
public final class BoneMealExecutor {

    /** 背包扫描上界：快捷栏 + 主背包（旧 {@code InvUtils.find(..., 0, 35)} 同口径，共 36 格） */
    private static final int BACKPACK_SCAN_LIMIT = 36;

    /** 快捷栏下标上界：0~8（旧 {@code inv.slot() > 8} 判据里的 8） */
    private static final int HOTBAR_LAST_INDEX = 8;

    private final Minecraft mc = Minecraft.getInstance();

    /** 骨粉判据：与旧 {@code Items.BONE_MEAL} 同源，只写一处 */
    private static final Predicate<ItemStack> BONE_MEAL = stack -> stack.is(Items.BONE_MEAL);

    // ── 找骨粉 ──

    /**
     * 查找骨粉持有手。
     *
     * <p>顺序逐字照旧：副手优先（可关）→ 快捷栏（含主手）→ 主背包；背包里找到时把它换到当前
     * 选中快捷栏格再用手。三处都没有时返回 {@code null}，由模块播报「骨粉耗尽」并暂停。</p>
     *
     * @return 该用手，找不到返回 {@code null}
     */
    public InteractionHand findBoneMealHand(BonemealSettings settings) {
        if (mc.player == null) return null;

        // 副手优先
        if (settings.offhandFirst && mc.player.getOffhandItem().is(Items.BONE_MEAL)) {
            return InteractionHand.OFF_HAND;
        }

        // 快捷栏（含主手）
        int hotbar = InventoryAccess.findInHotbar(BONE_MEAL);
        if (hotbar != InventoryAccess.NOT_FOUND) {
            InventoryAccess.swapWithSelectedHotbar(hotbar);
            return InteractionHand.MAIN_HAND;
        }

        // 背包（0~35 全搜，只取快捷栏之外的格：0~8 已在上面搜过）
        int backpack = InventoryAccess.find(BONE_MEAL, BACKPACK_SCAN_LIMIT);
        if (backpack != InventoryAccess.NOT_FOUND && backpack > HOTBAR_LAST_INDEX) {
            InventoryAccess.swapWithSelectedHotbar(backpack);
            return InteractionHand.MAIN_HAND;
        }

        return null;
    }

    // ── 催熟一次 ──

    /**
     * 对目标方块使用一次骨粉。
     *
     * @return 是否真的发生了交互（{@code InteractionResult.consumesAction()}，与旧实现同一判据）
     */
    public boolean fertilize(BlockPos target, InteractionHand hand, BonemealSettings settings) {
        if (mc.player == null || mc.gameMode == null) return false;

        Vec3 center = Vec3.atCenterOf(target);
        if (settings.rotateSilent) sendSilentRotation(center);

        BlockHitResult hit = new BlockHitResult(center, Direction.UP, target, false);
        InteractionResult result = mc.gameMode.useItemOn(mc.player, hand, hit);
        boolean consumed = result.consumesAction();
        if (consumed && settings.swingHand) mc.player.swing(hand);
        return consumed;
    }

    /**
     * 静默视角同步：发 Rot 包附带目标 yaw/pitch，
     * 客户端视角不变，服务端认为玩家正看着目标，防隔墙交互检测（旧 {@code sendSilentRotation}）。
     *
     * @return 是否发出了包（未进服时为 {@code false}）
     */
    public boolean sendSilentRotation(Vec3 target) {
        if (mc.player == null) return false;
        Vec3 eye = mc.player.getEyePosition();
        Vec3 diff = target.subtract(eye);
        double horizDist = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
        float pitch = (float) -Math.toDegrees(Math.atan2(diff.y, horizDist));
        float yaw = (float) Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90f;
        return ClientPacketSender.sendMoveRotation(yaw, pitch, mc.player.onGround(),
            mc.player.horizontalCollision);
    }
}
