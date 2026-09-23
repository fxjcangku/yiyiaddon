package com.yiyiaddon.mixin.client;

import com.yiyiaddon.dev.seedpoc.FeatureWriteJournal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 开发期探针（种子挖矿 PoC 第七轮）· FEATURES 逐格写入 Journal 的注入端。
 *
 * <p><b>为什么选这个注入点</b>：用户口径第十节要求「不能只记 {@code applyBiomeDecoration} 的批号，
 * 批号说明不了并行情况下某个 BlockPos 到底谁最后写」。原版所有生成期的方块写入最终都会走到
 * {@code WorldGenRegion#setBlock(BlockPos, BlockState, int, int)}
 * （{@code Feature#setBlock(LevelWriter, …)} → {@code LevelWriter#setBlock} 的默认实现 →
 * 这里的四参重载），而这一版的 {@code WorldGenRegion#getCenter()} 恰好就是
 * <b>当前正在装饰的那个 viewer 区块</b> —— 于是「这一次写是谁写的」不需要任何额外推导。</p>
 *
 * <p><b>两个注入点的分工</b>：HEAD 只做一件事——如果位置落在被跟踪的 3×3 里，
 * 就把「写入前的方块」暂存到线程本地（RETURN 时已经读不到它了）；RETURN 才真正落账，
 * 且只有 {@code written == true}（= 没有被 {@code ensureCanWrite} 拒绝）才算一次写。</p>
 *
 * <p>探针本体不在这里判断开关：{@code FeatureWriteJournal} 第一件事就是查总闸与跟踪区域，
 * 未开启时只做一次集合查询就返回。</p>
 */
@Mixin(WorldGenRegion.class)
public abstract class FeatureWriteJournalMixin {

    /**
     * 必须写全描述符：{@code WorldGenRegion} 里只有这一个 {@code setBlock} 重载
     * （三参版本来自 {@code LevelWriter} 的默认方法，最终转发到这里），
     * 写全参数表可以避免 Mixin 解析到别的方法。
     */
    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
            at = @At("HEAD"))
    private void yiyiaddon$journalBeforeWrite(BlockPos pos, BlockState state, int flags, int recursionLeft,
                                              CallbackInfoReturnable<Boolean> callbackInfo) {
        FeatureWriteJournal.onBeforeWrite((WorldGenRegion) (Object) this, pos);
    }

    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
            at = @At("RETURN"))
    private void yiyiaddon$journalAfterWrite(BlockPos pos, BlockState state, int flags, int recursionLeft,
                                             CallbackInfoReturnable<Boolean> callbackInfo) {
        FeatureWriteJournal.onAfterWrite((WorldGenRegion) (Object) this, pos, state,
                callbackInfo.getReturnValueZ());
    }
}
