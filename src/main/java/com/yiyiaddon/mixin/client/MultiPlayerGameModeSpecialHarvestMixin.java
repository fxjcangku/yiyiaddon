package com.yiyiaddon.mixin.client;

import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.profile.StardewSpecialHarvestAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 星露谷特殊变种「收割口径」的学习接线层：把玩家自己的收割动作转给模块记住。
 *
 * <p><b>为什么必须从玩家动作里学</b>：特殊变种（金色 / 巨型 / 变种）的收法随服务器不同 ——
 * 老服是「手持金锄头右键，收完回退植株」，jmy.seasonmc.xyz 的巨型菠萝是「左键破坏，
 * 工具不限、拿锄头更快」（用户 2026-09-22 两次实机确认）。口径写死在代码里就会砸坏另一边的
 * 巨型作物，而自动试探（先右键、失败就砸）同样会把右键服上的巨型作物砸掉，所以只能观测玩家：</p>
 *
 * <ul>
 *   <li>{@code startDestroyBlock}（左键破坏）→ 记「破坏」；</li>
 *   <li>{@code useItemOn}（右键交互）→ 记「右键」。</li>
 * </ul>
 *
 * <p>只做转发：判定（手持工具、目标是不是特殊阶段作物、是否与本服已知口径不同）与
 * 落盘时机（那一格确实被收掉之后）全部在
 * {@link StardewFarmModule#coordinator()} 里，本层不持有任何状态。</p>
 *
 * <p><b>模块自己的收割不会走到这里</b>：星露谷的动作全部走 {@code StardewAdapter} 的发包通道，
 * 不经过 {@code MultiPlayerGameMode}，所以这里看到的必定是玩家手动操作。</p>
 */
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeSpecialHarvestMixin {

    /** 左键破坏方块：玩家手动砸特殊阶段作物 */
    @Inject(method = "startDestroyBlock", at = @At("HEAD"))
    private void yiyiaddon$observeSpecialBreak(BlockPos pos, net.minecraft.core.Direction direction,
                                               CallbackInfoReturnable<Boolean> cir) {
        yiyiaddon$observeSpecialHarvest(pos, StardewSpecialHarvestAction.BREAK);
    }

    /** 右键对方块使用：玩家手动右键特殊阶段作物 */
    @Inject(method = "useItemOn", at = @At("HEAD"))
    private void yiyiaddon$observeSpecialUse(LocalPlayer player, InteractionHand hand, BlockHitResult hitResult,
                                             CallbackInfoReturnable<InteractionResult> cir) {
        if (hitResult == null) return;
        yiyiaddon$observeSpecialHarvest(hitResult.getBlockPos(), StardewSpecialHarvestAction.RIGHT_CLICK);
    }

    /**
     * 把玩家的动作（连同当时手上那件）转给模块：目标是不是特殊阶段作物、要不要落盘，全在协调器里判。
     *
     * <p><b>为什么不再限制手持物</b>：左键口径下「空手也能砸、拿工具只是更快」是本服实机结论，
     * 限制手持会把空手砸掉的那一次漏学；右键口径下各服的专用工具也不止原版金锄头一种
     * （用户 2026-09-22：「要特殊工具收割的，写死用什么挖就好了」），限制成金锄头会让那些服永远学不到。
     * 拿什么收的就记什么：真正的滤网是「那一格确实被收掉」（协调器的证据链），不是手持什么。</p>
     */
    @Unique
    private static void yiyiaddon$observeSpecialHarvest(BlockPos pos, StardewSpecialHarvestAction action) {
        if (pos == null) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        StardewFarmModule module = yiyiaddon$stardewModule();
        if (module == null || !module.isEnabled()) return;
        // 主手上那件一起上报；空手 = 工具不限
        module.coordinator().observePlayerSpecialHarvest(pos, action, mc.player.getMainHandItem());
    }

    /** 取星露谷模块；未注册或类型不符返回 {@code null}（原版照常处理） */
    @Unique
    private static StardewFarmModule yiyiaddon$stardewModule() {
        Module module = ModuleManager.byId(StardewFarmModule.MODULE_ID);
        return module instanceof StardewFarmModule stardew ? stardew : null;
    }
}
