package com.yiyiaddon.feature.stardew.service;

import com.yiyiaddon.feature.stardew.profile.ShelterDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 温室玻璃探测：这一口盆上方是否罩着已选温室玻璃。
 *
 * <p><b>为什么按盆逐格判：</b>服务器对「有没有温室罩」的判定是<b>每口盆各自算</b>的——玻璃只摆在田地
 * 一角时，边缘那些盆照样会因季节枯萎。按区域取一个代表点会在区域大于玻璃覆盖面积时把没罩的盆
 * 误判成能种（种下去白瞎一茬）。逐格只在「这一格要种、却被动植物季节判据拦下」时才读方块，
 * 一轮扫描的量级完全无压力。</p>
 *
 * <p><b>为什么不缓存结论：</b>玻璃可能被玩家随时挖掉 / 补上，缓存「这口盆有没有罩」会让结论滞后一整个
 * 种植周期；每轮现读世界方块才能做到「挖掉即恢复当季不种、补上即照常种」。区块未加载时按「无罩」
 * 处理（保守不种），不额外报错。</p>
 *
 * <p><b>但方块语义必须缓存：</b>{@link BlockStateModelResolver#resolve} 的契约是「按需一次性动作、
 * 非每 tick 调用，故不做缓存」，而本探测在决策循环里会对整片地的每一口盆重复询问——同一格玻璃会被问
 * 上千次。故这里按「方块 ID + 属性」缓存解析结果（纯函数，与盆位无关）：一轮里真正读资源的只有场上
 * 出现的那几种方块，每口盆的判定退化为几次字符串比较。<b>不缓存</b>的是「哪口盆有罩」这个结论。</p>
 *
 * <p><b>命中口径：</b>与洒水器的世界识别同源——语义身份 / 世界模型 / 物品模型三选一命中已选
 * 温室玻璃即可，不要求语义确定性为「已确认」（很多资源包的 blockstates 会命中多个模型候选，
 * 候选串里已含真实模型路径，足以在已选集合内唯一确定）。</p>
 */
public final class StardewShelterProbe {

    /** 判定高度：物品 lore「可放置种植盆上方5格内」，即从盆上方第 1 格到第 5 格 */
    public static final int SHELTER_HEIGHT = 5;

    /** 方块语义缓存上限：场地同时出现的方块种类远小于此，超出直接清空重建（不引入 LRU 复杂度） */
    private static final int SEMANTIC_CACHE_LIMIT = 256;

    /**
     * 「资源指纹 + 方块 ID + 属性」→ 方块语义。
     *
     * <p>带资源指纹：资源包重载后旧语义立即作废，不需要额外的失效钩子。</p>
     */
    private static final Map<String, BlockSemantic> SEMANTIC_CACHE = new ConcurrentHashMap<>();

    private StardewShelterProbe() {
    }

    /**
     * 盆上方 {@link #SHELTER_HEIGHT} 格内是否有已选温室玻璃。
     *
     * @param potPos       种植盆坐标（判定从它正上方第 1 格开始）
     * @param index        当前服务器资源索引
     * @param selectedKeys 玩家已选的温室玻璃键；空 = 这条机制未启用
     * @return true 表示这一格受罩，可无视季节限制照常播种
     */
    public static boolean sheltered(BlockPos potPos, StardewResourceIndex index, Collection<String> selectedKeys) {
        if (potPos == null || index == null || selectedKeys == null || selectedKeys.isEmpty()) return false;
        // 资源未就绪时解析不出语义，直接按「无罩」处理（与洒水器世界识别同一前置条件）
        if (!ResourceExtractionService.isReady()) return false;
        var level = Minecraft.getInstance().level;
        if (level == null) return false;
        for (int dy = 1; dy <= SHELTER_HEIGHT; dy++) {
            BlockPos pos = potPos.above(dy);
            // 区块没加载：无法确认有没有罩，按「无罩」保守处理（宁可当季不种，也不乱种）
            if (!level.isLoaded(pos)) return false;
            BlockState state = level.getBlockState(pos);
            if (state.isAir()) continue;
            if (matches(semanticOf(state), index, selectedKeys)) return true;
        }
        return false;
    }

    /** 方块语义（按方块 ID + 属性缓存；同一格玻璃在整片地上只解析一次） */
    private static BlockSemantic semanticOf(BlockState state) {
        // state.toString() 含方块 ID 与全部属性（与 SprinklerWorldBinding 的状态键同一口径）
        String key = ResourceExtractionService.fingerprint() + '\u0000' + state;
        BlockSemantic cached = SEMANTIC_CACHE.get(key);
        if (cached != null) return cached;
        BlockSemantic resolved = BlockStateModelResolver.resolve(state);
        if (SEMANTIC_CACHE.size() >= SEMANTIC_CACHE_LIMIT) SEMANTIC_CACHE.clear();
        SEMANTIC_CACHE.put(key, resolved);
        return resolved;
    }

    /** 方块语义是否命中任一已选温室玻璃 */
    private static boolean matches(BlockSemantic semantic, StardewResourceIndex index,
                                   Collection<String> selectedKeys) {
        if (semantic == null) return false;
        List<String> models = new ArrayList<>();
        if (semantic.model() != null) {
            // 候选语义会给出「模型A | 模型B」串，逐段比对
            for (String candidate : semantic.model().split("\\|")) {
                models.add(candidate.trim());
            }
        }
        for (String key : selectedKeys) {
            if (!(index.entryByKey(key) instanceof ShelterDefinition shelter)) continue;
            if (semantic.identity() != null && semantic.identity().equals(shelter.identityKey())) return true;
            if (shelter.blockModel() != null && models.contains(shelter.blockModel())) return true;
            if (shelter.itemModel() != null && models.contains(shelter.itemModel())) return true;
        }
        return false;
    }
}
