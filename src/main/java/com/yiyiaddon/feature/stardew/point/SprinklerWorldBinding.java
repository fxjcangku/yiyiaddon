package com.yiyiaddon.feature.stardew.point;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * 用户人工确认的服务器专属洒水器世界绑定。
 *
 * <p>绑定不会修改通用 BlockIdentity，也不会宣称 {@code sugar_cane[age=9]} 在所有服务器都是
 * 洒水器。它只在 ServerKey + 资源指纹 + 逻辑洒水器 + 稳定世界载体状态四项全部一致时有效。</p>
 *
 * <p><b>载体有两种形态：</b>① 方块形态——载体是那一格的 BlockState；② 贴图形态——洒水器由
 * {@code item_display} 渲染，载体就是展示实体本身，<b>它所在的方块格是空气</b>。后者由
 * {@link #displayCarrierAt} 识别，不是「准星对着空气」。</p>
 */
public record SprinklerWorldBinding(String serverKey, String fingerprint, String sprinklerKey,
                                    String carrierBlockId, String carrierState, String semanticIdentity,
                                    String semanticModel) {

    /**
     * 这一格上是否挂着展示实体（{@code item_display}）。
     *
     * <p><b>为什么它是共享判据：</b>贴图形态洒水器所在格永远是空气，本方法是唯一能区分
     * 「合法的展示实体载体」与「准星对着空气乱指」的依据。准星取坐标（{@code StardewPointActions}）
     * 与载体捕捉（{@link #capture}）必须用同一份搜索范围（{@code inflate(1.0)} + {@code blockPosition}
     * 相等），否则会出现「坐标取到了、载体却读不出来」这种自相矛盾的失败。</p>
     */
    public static boolean displayCarrierAt(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || pos == null || !mc.level.isLoaded(pos)) return false;
        return !mc.level.getEntities((Entity) null, new AABB(pos).inflate(1.0),
            e -> e instanceof Display.ItemDisplay && e.blockPosition().equals(pos)).isEmpty();
    }

    /** 从准星当前真实 BlockState 建立一次人工确认绑定。 */
    public static SprinklerWorldBinding capture(BlockPos pos, String sprinklerKey) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || pos == null || sprinklerKey == null) return null;
        BlockState state = mc.level.getBlockState(pos);
        // 贴图形态洒水器的载体是展示实体而非方块，所在格本来就是空气：只在这一格确实挂着展示实体时
        // 放行。方块形态洒水器那一格非空气，与旧行为逐字相同。
        if (state.isAir() && !displayCarrierAt(pos)) return null;
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
