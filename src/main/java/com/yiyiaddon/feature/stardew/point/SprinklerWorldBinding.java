package com.yiyiaddon.feature.stardew.point;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 用户人工确认的服务器专属洒水器世界绑定。
 *
 * <p>绑定不会修改通用 BlockIdentity，也不会宣称 {@code sugar_cane[age=9]} 在所有服务器都是
 * 洒水器。它只在 ServerKey + 资源指纹 + 逻辑洒水器 + 稳定世界载体状态四项全部一致时有效。</p>
 */
public record SprinklerWorldBinding(String serverKey, String fingerprint, String sprinklerKey,
                                    String carrierBlockId, String carrierState, String semanticIdentity,
                                    String semanticModel) {

    /** 从准星当前真实 BlockState 建立一次人工确认绑定。 */
    public static SprinklerWorldBinding capture(BlockPos pos, String sprinklerKey) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || pos == null || sprinklerKey == null) return null;
        BlockState state = mc.level.getBlockState(pos);
        if (state.isAir()) return null;
        String serverKey = StardewContext.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        if (serverKey == null || fingerprint == null || fingerprint.isBlank()) return null;
        var blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if (blockId == null) return null;
        BlockSemantic semantic = BlockStateModelResolver.resolve(state);
        return new SprinklerWorldBinding(serverKey, fingerprint,
            sprinklerKey, blockId.toString(), state.toString(), semantic.identity(), semantic.model());
    }

    /** 当前世界必须仍命中建立绑定时的完整隔离域与稳定载体状态。 */
    public boolean matches(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || pos == null || !mc.level.isLoaded(pos)) return false;
        if (!java.util.Objects.equals(serverKey, StardewContext.serverKey())
            || !java.util.Objects.equals(fingerprint, ResourceExtractionService.fingerprint())) return false;
        BlockState state = mc.level.getBlockState(pos);
        var blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return blockId != null && java.util.Objects.equals(carrierBlockId, blockId.toString())
            && java.util.Objects.equals(carrierState, state.toString());
    }
}
