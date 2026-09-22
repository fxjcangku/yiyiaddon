package com.yiyiaddon.feature.stardew.recognition;

import com.yiyiaddon.feature.stardew.profile.StardewCropNameStore;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作物「展示实体」探测：识别那些**把作物渲染成展示实体**的服务器（CraftEngine 系，
 * 客户端没装对应模组时的退化渲染）。
 *
 * <p><b>为什么需要它：</b>标准 CustomCrops 服里作物是盆上方一格的方块，模组按
 * {@code blockstates → 模型路径 → 语义身份} 就能认出阶段。但这类服把作物渲染成
 * {@code item_display} 实体（手里挂着 {@code customcrops:<作物>_stage_N} 物品），
 * 世界里根本没有对应的作物方块——盆上方永远是空气。展示实体<b>没有碰撞箱</b>，
 * 所以准星点不中、{@code .id 方块} 也读不到，只能靠本探测。</p>
 *
 * <p><b>身份口径与方块路径完全一致：</b>模型键走 {@code resolvedModelOf}（资源包派发表按「载体 +
 * {@code custom_model_data}」阈值命中优先，其次 {@code item_model} 组件），再归一成
 * {@code customcrops:chinese_cabbage_stage_3} 这种身份串，直接喂给
 * {@link CropRuntimeStateResolver} 得到的作物键 / 阶段 / 成熟判定与方块路径同源，
 * 不存在「两条链路结论不同」的问题。</p>
 *
 * <p><b>兜底性质：</b>只在「方块侧认不出作物、且这一格是已确认的盆」时被查询，
 * 标准服务器永远不会走到这里，行为完全不变。</p>
 */
public final class StardewCropDisplayProbe {

    /** 盆坐标 → 该格展示实体携带的物品模型（可能多个：作物 / 稻草人 / 洒水器同在附近） */
    private static Map<BlockPos, List<String>> byPot = Map.of();

    private StardewCropDisplayProbe() {
    }

    /**
     * 按农田范围刷新一轮归档。
     *
     * <p>展示实体会随作物生长被服务器重建，因此每轮扫描开始时重新归档，不复用上一轮结果。</p>
     */
    public static void refresh(BlockPos min, BlockPos max) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || min == null || max == null) {
            byPot = Map.of();
            return;
        }
        Map<BlockPos, List<String>> map = new HashMap<>();
        collect(map, mc, min, max);
        byPot = map;
    }

    /**
     * 围绕某中心（半径 8 格）补录，<b>不丢弃</b>已有归档。
     *
     * <p>指令路径用它：玩家在收菜任务进行中敲 {@code 标记成熟} 时，若把整片农田的归档换成一个
     * 小方块，紧接着的收获验证就会读不到其它盆上的展示实体。归档只增不减，扫描轮开始时
     * {@link #refresh} 会整体重建，不会积累过期数据。</p>
     */
    public static void archiveAround(BlockPos center) {
        if (center == null) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        Map<BlockPos, List<String>> map = new HashMap<>(byPot);
        collect(map, mc, center.offset(-8, -4, -8), center.offset(8, 4, 8));
        byPot = map;
    }

    private static void collect(Map<BlockPos, List<String>> map, Minecraft mc, BlockPos min, BlockPos max) {
        // 实体可能悬在作物格上、也可能贴在盆格上，范围上下各放宽 2 格，具体归属仍按真实坐标判定
        AABB box = new AABB(min.getX() - 2, min.getY() - 2, min.getZ() - 2,
            max.getX() + 3, max.getY() + 3, max.getZ() + 3);
        for (Entity entity : mc.level.getEntities((Entity) null, box)) {
            String model = modelOf(entity);
            if (model == null) continue;
            BlockPos pos = entity.blockPosition();
            // 展示实体的落点在不同服上可能落在作物格本身或盆格，两个候选都归档；
            // 具体是哪一格由调用方在「已确认的盆」上查询来决定，绝不按固定偏移猜。
            add(map, pos, model);
            add(map, pos.below(), model);
        }
    }

    /**
     * 单个展示实体携带的作物模型（如 {@code customcrops:chinese_cabbage_stage_3}）。
     *
     * <p>准星直接命中展示实体时用这个：读的是<b>此刻</b>的实体，不依赖归档。
     * 非展示实体 / 非 customcrops 物品一律返回 {@code null}。
     * 顺带学名——这类服上「白菜幼苗 / 白菜生长期」正是作物中文名的唯一证据。</p>
     *
     * <p><b>身份口径（2026-09-21 修正）：</b>模型键走
     * {@link StardewInventoryService#resolvedModelOf(ItemStack)} —— {@code item_model} 组件优先，
     * 缺失时由资源包派发表按「基础物品 + {@code custom_model_data}」反查，再压平成扁平身份。
     * 旧实现只读 {@code ITEM_MODEL}，于是 ItemsAdder 旧布局服务器（整服自定义物品挂在
     * {@code minecraft:paper} 上、没有 {@code item_model}）的展示实体<b>整批被忽略</b>：
     * 真机取证，jmy.seasonmc.xyz 的初级洒水器 = {@code minecraft:paper} +
     * {@code custom_model_data 10635} —— 世界上明明挂着 {@code item_display}，
     * 诊断却报「附近 8 格内没有展示实体」，作物与洒水器一并失明。</p>
     */
    public static String modelOf(Entity entity) {
        if (!(entity instanceof Display.ItemDisplay display)) return null;
        ItemStack stack = display.getItemStack();
        if (stack == null || stack.isEmpty()) return null;
        StardewCropNameStore.observe(stack);
        return identityOf(StardewInventoryService.resolvedModelOf(stack));
    }

    /**
     * 展示物模型键 → 带命名空间的语义身份（如 {@code customcrops:cabbage_stage_1}）。
     *
     * <p><b>旧布局的目录形态要借目录里的作物名（2026-09-21 修正）：</b>真机派发表取证
     * （jmy.seasonmc.xyz，{@code assets/minecraft/items/paper.json}）里阶段模型是
     * {@code customcrops:item/crops/cabbage/stage_1} —— <b>阶段名里没有作物名</b>，
     * 压平取末段只剩 {@code stage_1}，而身份派生规则对「只有 stage_N、没有父目录」的形态
     * 如实返回 null（索引层就是这样，才只有极少数作物有真实阶段）。因此末段是纯阶段名时
     * 交给 {@code deriveIdentityPath}，它会拼回父目录里的作物名得到 {@code cabbage_stage_1}。</p>
     *
     * <p>其余末段自身就是一条完整身份：{@code cabbage}（成熟产物）、{@code cabbage_seeds}（种子）、
     * {@code gigantic_cabbage}（特殊阶段）、{@code sprinkler_1} / {@code dry_pot}（工具）。
     * 这些若走「去掉首段再整串拼接」会得到 {@code cabbage_cabbage} 这种假身份，所以直接取末段。</p>
     */
    private static String identityOf(String model) {
        if (model == null || model.isBlank()) return null;
        int colon = model.indexOf(':');
        if (colon < 0 || !"customcrops".equals(model.substring(0, colon))) return null;
        String path = model.substring(colon + 1);
        int slash = path.lastIndexOf('/');
        String last = slash >= 0 ? path.substring(slash + 1) : path;
        if (last.isBlank()) return null;
        String identity = last.startsWith("stage")
            ? BlockStateModelResolver.deriveIdentityPath(model)
            : last;
        return identity == null || identity.isBlank() ? null : "customcrops:" + identity;
    }

    /**
     * 围绕某个中心（半径 8 格）整体重建归档：供「只关心这一小片」的即时校验使用
     * （收获验证前刷新，判据必须是此刻的实体，不能用扫描期归档）。
     *
     * <p>会丢弃中心以外的归档；指令里的即时查询请用 {@link #archiveAround}。</p>
     */
    public static void refreshAround(BlockPos center) {
        if (center == null) {
            byPot = Map.of();
            return;
        }
        refresh(center.offset(-8, -4, -8), center.offset(8, 4, 8));
    }

    /**
     * 这一格上挂着的展示实体<b>此刻</b>携带的模型身份（没有 / 读不出返回 {@code null}）。
     *
     * <p>与 {@link #modelsAt} 的区别是数据来源：这里直接读世界里的实体，<b>不看扫描期归档</b>。
     * 「绑定还成不成立」「刚才那个收割动作到底生效没有」这类判据必须看当下，
     * 拿扫描期快照对比会把上一轮的结果当成事实（贴图形态洒水器被挖掉后仍判有效就是这个坑）。</p>
     */
    public static String liveIdentityAt(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || pos == null || !mc.level.isLoaded(pos)) return null;
        for (Entity entity : mc.level.getEntities((Entity) null, new AABB(pos).inflate(1.0),
            e -> e instanceof Display.ItemDisplay && e.blockPosition().equals(pos))) {
            String model = modelOf(entity);
            if (model != null) return model;
        }
        return null;
    }

    /** 这一格盆上的全部展示物品模型（无则空表，顺序即发现顺序） */
    public static List<String> modelsAt(BlockPos potPos) {
        if (potPos == null) return List.of();
        List<String> models = byPot.get(potPos);
        return models == null ? List.of() : models;
    }

    /** 归档结果是否非空（诊断用：区分「附近没有展示实体」与「有但没解析成作物」） */
    public static boolean hasAny() {
        return !byPot.isEmpty();
    }

    public static void reset() {
        byPot = Map.of();
    }

    private static void add(Map<BlockPos, List<String>> map, BlockPos pos, String model) {
        List<String> models = map.computeIfAbsent(pos, key -> new ArrayList<>(2));
        if (!models.contains(model)) models.add(model);
    }
}
