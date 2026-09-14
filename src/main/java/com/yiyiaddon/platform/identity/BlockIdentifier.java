package com.yiyiaddon.platform.identity;

import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.platform.world.WorldIdentity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * 方块识别适配层：从玩家准星真实命中提取完整 {@link BlockIdentity}。
 *
 * <p>采集链路：准星 → {@link BlockHitResult} → 坐标 → 方块状态 →（可选）方块实体。
 * 不做「玩家坐标 + 方向 + 固定偏移」的猜测，也不扫描世界。方块实体数据通过
 * {@code saveWithoutMetadata(registries)} 读取，能读多少存多少，失败静默降级为「无实体数据」，
 * 仍保留基础方块身份。</p>
 */
public final class BlockIdentifier {

    private BlockIdentifier() {
    }

    /** 该方块 ID 是否存在于当前注册表 */
    public static boolean exists(String blockId) {
        if (blockId == null || blockId.isBlank()) return false;
        Identifier id = Identifier.tryParse(blockId);
        if (id == null) return false;
        return BuiltInRegistries.BLOCK.getValue(id) != null;
    }

    /**
     * 识别当前准星命中的方块；未命中方块或不可识别返回 {@code null}。
     *
     * <p>准星可能命中实体而非方块，此时不误判，返回 {@code null} 由调用方提示「未指向方块」。</p>
     */
    public static BlockIdentity identify() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return null;
        HitResult hit = mc.hitResult;
        if (!(hit instanceof BlockHitResult blockHit)) return null;

        var pos = blockHit.getBlockPos();
        BlockState state = mc.level.getBlockState(pos);
        Identifier blockKey = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        String blockId = blockKey.toString();

        // 方块实体：能读则读，读不到保留基础身份
        String blockEntityTypeId = null;
        String blockEntityData = null;
        BlockEntity blockEntity = mc.level.getBlockEntity(pos);
        if (blockEntity != null) {
            blockEntityTypeId = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType()).toString();
            blockEntityData = serializeBlockEntity(blockEntity);
        }

        // 资源包语义解析：原版载体方块映射到自定义模型，隐形方块回退注册 ID；读不到时如实标注未知
        BlockSemantic semantic = BlockStateModelResolver.resolve(state);

        return new BlockIdentity(blockId, localizedBlockName(blockId), state.toString(),
            blockEntityTypeId, blockEntityData,
            WorldIdentity.dimension(), WorldIdentity.server(),
            pos.getX(), pos.getY(), pos.getZ(), WorldIdentity.dataVersion(),
            semantic.model(), semantic.identity(), semantic.name(),
            semantic.source(), semantic.certainty(), semantic.reason());
    }

    /** 方块注册表中文名；查不到返回 {@code null} */
    public static String localizedBlockName(String blockId) {
        try {
            Identifier id = Identifier.tryParse(blockId);
            if (id == null) return null;
            Block block = BuiltInRegistries.BLOCK.getValue(id);
            if (block == null) return null;
            String name = block.getName().getString();
            return name == null ? null : name.replaceAll("§[0-9a-fk-orA-FK-ORx]", "").trim();
        } catch (Exception ignored) {
            return null;
        }
    }

    /** 读取方块实体数据为 SNBT 原始结构；失败返回 {@code null}（不崩溃） */
    private static String serializeBlockEntity(BlockEntity blockEntity) {
        try {
            CompoundTag tag = blockEntity.saveWithoutMetadata(Minecraft.getInstance().level.registryAccess());
            return tag == null ? null : tag.toString();
        } catch (Exception ignored) {
            // 客户端无法完整访问该方块实体数据时静默降级
            return null;
        }
    }
}
