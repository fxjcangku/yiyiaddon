package com.yiyiaddon.mixin.client;

import com.yiyiaddon.seed.service.SeedMiningService;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 客户端世界「服务端权威方块更新」钩子（正式化第五阶段 233）。
 *
 * <p><b>为什么需要它</b>：口径第二十八节要求「方块更新后如果更新的位置正好是预测候选，
 * 就立刻重新观察那个 BlockPos」，而口径第二十九节要求「优先用已有事件，没有就用最小 Mixin，
 * 绝不允许每 Tick 把所有候选重新 getBlockState 一遍」。26.1.2 的 Fabric API 里
 * <b>没有</b>客户端方块状态变更事件（{@code ClientChunkEvents} 只有加载 / 卸载，
 * {@code ClientBlockEntityEvents} 只管方块实体），因此这里用最小注入。</p>
 *
 * <p><b>注入点怎么选的</b>（对着 26.1.2 源码逐条确认）：</p>
 * <ul>
 *     <li>{@code ClientPacketListener#handleBlockUpdate}（单方块更新包）→
 *         {@code this.level.setServerVerifiedBlockState(pos, state, 19)}；</li>
 *     <li>{@code ClientPacketListener} 处理多方块更新包 → {@code packet.runUpdates((pos, state) ->
 *         this.level.setServerVerifiedBlockState(pos, state, 19))}；</li>
 *     <li>因此 {@code ClientLevel#setServerVerifiedBlockState} 是这两条路径的<b>唯一汇合点</b>，
 *         注入它的 TAIL 就能覆盖「服务端告诉客户端某个方块变了」的全部情况，且仍在客户端主线程
 *         （包处理与预测回放都在主线程）。</li>
 * </ul>
 *
 * <p><b>它不做任何判定</b>：只把「哪个位置变了」转交给种子挖矿运行时；对方会先查这张表里
 * 有没有这个候选，没有就直接丢掉（不是候选的更新 O(1) 忽略）。区块加载 / 卸载走 Fabric 官方事件，
 * 不在这里。</p>
 */
@Mixin(ClientLevel.class)
public abstract class ClientLevelBlockUpdateMixin {

    /** 服务端权威方块状态写入之后（原方法返回前）转交一次位置。 */
    @Inject(method = "setServerVerifiedBlockState", at = @At("TAIL"))
    private void yiyiaddon$afterServerVerifiedBlockState(BlockPos pos, BlockState state, int updateFlags,
                                                         CallbackInfo info) {
        try {
            SeedMiningService.instance().onClientBlockUpdated((ClientLevel) (Object) this, pos);
        } catch (Throwable ignored) {
            // 观察钩子绝不能把异常扩散进原版包处理链路：失败就当这一帧没观察到，
            // 下一次区块加载 / 方块更新会自然重来
        }
    }
}
