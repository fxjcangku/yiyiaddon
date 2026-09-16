package com.yiyiaddon.feature.stardew.point;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.profile.SprinklerDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.profile.WateringCanDefinition;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 点位设置（GUI 按钮与 {@code .stardew} 指令共用同一实现，禁止各写一套）。
 *
 * <p>逐字搬运自旧项目 {@code stardew/StardewFarmModule.java:1059-1446}。只做框架适配：
 * 旧 {@code ServerResourceService} → {@link ResourceExtractionService} + {@link GameProbe}，
 * 旧模块字段 {@code pointManager / index / profile / coordinator / 六类选择 / reach}
 * 由构造器注入。</p>
 */
public final class StardewPointActions {

    private final Minecraft mc = Minecraft.getInstance();
    private final StardewPointManager pointManager;
    private final StardewResourceIndex index;
    private final StardewCoordinator coordinator;
    private final StardewInventoryService inventory;
    private final StardewSettings settings;

    /**
     * 洒水器新绑定的「分区」闸门，由模块注入。
     *
     * <p>为什么放在这里而不是点位校验里：已经绑好的洒水器一律不回溯（玩家会突然发现一半洒水器
     * 失效）；只有「新绑定」才要求落在某个种植区域内。未注入时一律放行。</p>
     */
    private java.util.function.Function<BlockPos, String> sprinklerRegionGate = pos -> null;

    public StardewPointActions(StardewPointManager pointManager, StardewResourceIndex index,
                               StardewCoordinator coordinator,
                               StardewInventoryService inventory, StardewSettings settings) {
        this.pointManager = pointManager;
        this.index = index;
        this.coordinator = coordinator;
        this.inventory = inventory;
        this.settings = settings;
    }

    /** 注入洒水器新绑定的分区闸门（模块构造时注入一次） */
    public void setSprinklerRegionGate(java.util.function.Function<BlockPos, String> gate) {
        if (gate != null) this.sprinklerRegionGate = gate;
    }

    /**
     * 准星设置单点位：真实 {@link BlockHitResult} → 严格校验 → 落盘 → 统一播报。
     *
     * <p>校验按点位业务语义分流：容器类必须命中 {@link Container}；补水点必须是静止水源。
     * 失败时给出明确中文原因，绝不出现「对着普通方块也能保存成功」。</p>
     */
    public boolean setPointFromCrosshair(StardewPointType type) {
        if (!pointEnvironmentAllowed()) return false;
        // 盆型互斥：选了什么盆就决定了哪些点位有意义。绑错不会让脚本做错事（脚本按盆型自己挑物料），
        // 但玩家会以为配好了、那条链路其实永远用不上，所以在这一刻就拒绝并说明原因。
        String conflict = type.conflictWith(index.potGroupOfSelected(settings.selectedPotKeys));
        if (conflict != null) {
            pointFailure(type.title(), conflict);
            return false;
        }
        StardewPointManager.StardewPoint existing = pointManager.get(type);
        if (existing != null) {
            pointFailure(type.title(), "已绑定在 X:" + existing.x() + " Y:" + existing.y() + " Z:" + existing.z()
                + "，请先删除原点位再重新设置");
            return false;
        }
        BlockPos pos = type == StardewPointType.WATER_SOURCE ? crosshairFluidBlock() : crosshairBlock();
        if (pos == null) {
            pointFailure(type.title(), "准星没有对准任何方块");
            return false;
        }
        if (type.requiresContainer() && !(mc.level.getBlockEntity(pos) instanceof Container)) {
            pointFailure(type.title(), "准星目标不是容器（箱子 / 木桶 / 潜影盒）");
            return false;
        }

        // 补水点：必须是静止水源（26.1.2 真实 FluidState.isSource() + water 流体标签）
        String verify = null;
        if (type == StardewPointType.WATER_SOURCE) {
            String failure = waterSourceFailure(pos);
            if (failure != null) {
                pointFailure(type.title(), failure);
                return false;
            }
            verify = "静止水源";
        }

        savePoint(type, new StardewPointManager.StardewPoint(pos.getX(), pos.getY(), pos.getZ(),
            StardewContext.dimension(), verify, null, type.title(), null));
        pointSuccess(type.title(), pos, verify, null, null);
        return true;
    }

    /**
     * 准星添加洒水器点位：资源语义能识别时自动匹配，否则由本次明确用户操作建立服务器专属绑定。
     *
     * <p>人工绑定仅属于 Stardew 业务层，不回写通用 BlockIdentity；同一载体在其它 ServerKey 或
     * 资源 fingerprint 下不会继承为洒水器。</p>
     */
    public boolean addSprinklerFromCrosshair() {
        return addSprinklerFromCrosshair(null);
    }

    /**
     * 同上，但允许玩家写明类型。
     *
     * <p>类型判定顺序：① 全局资源语义唯一命中 → 直接采用；② 只在「已选洒水器类型」里按世界方块模型
     * 再对一次（多候选语义也能对上，见 {@link StardewPointManager#matchSelectedSprinkler}）→ 采用并记
     * 「人工确认」；③ 都没对上时，只选一个类型就直接用它（与旧版行为一致），多选则要求玩家写明类型；
     * ④ 玩家写明类型时不再猜，按写明的绑定。</p>
     *
     * @param typeInput 玩家写明的洒水器类型（中文名或逻辑键）；null / 空白表示交给程序自动识别
     */
    public boolean addSprinklerFromCrosshair(String typeInput) {
        if (!pointEnvironmentAllowed()) return false;
        BlockPos pos = crosshairOrEntityBlock();
        if (pos == null) {
            pointFailure(StardewPointType.SPRINKLER.title(),
                "准星没有对准洒水器（准星命中：" + crosshairHitLabel() + "）");
            return false;
        }
        String plantingRegionFailure = sprinklerRegionGate.apply(pos);
        if (plantingRegionFailure != null) {
            pointFailure(StardewPointType.SPRINKLER.title(), plantingRegionFailure);
            return false;
        }
        List<SprinklerDefinition> chosen = selectedSprinklers();
        String wanted = typeInput == null ? null : typeInput.trim();
        SprinklerDefinition definition = null;
        String verify = null;
        if (wanted == null || wanted.isEmpty()) {
            definition = matchSprinkler(pos);
            if (definition != null) verify = "资源语义已验证";
            if (definition == null) {
                definition = matchSprinklerDisplay(pos);
                if (definition != null) verify = "贴图实体已验证";
            }
            if (definition == null) {
                definition = StardewPointManager.matchSelectedSprinkler(pos, index, settings.selectedSprinklerKeys);
            }
            if (definition == null) {
                if (chosen.isEmpty()) {
                    pointFailure(StardewPointType.SPRINKLER.title(),
                        "一个洒水器类型都没选：请先在「洒水器」选择器里勾选，再对准实物设置");
                    return false;
                }
                if (chosen.size() == 1) {
                    // 只选了一种：与旧版一致直接用它，并如实记为人工确认（此时并没有任何东西被验证过）
                    definition = chosen.get(0);
                } else {
                    pointFailure(StardewPointType.SPRINKLER.title(),
                        "认不出这一格是哪一种洒水器；请写明类型：.stardew 添加洒水器 "
                            + chosen.get(0).displayName() + "（当前已选：" + String.join("、", selectedSprinklerTypeNames()) + "）");
                    return false;
                }
            }
            if (verify == null) verify = "人工确认世界载体";
        } else {
            definition = selectedSprinkler(wanted);
            if (definition == null) {
                pointFailure(StardewPointType.SPRINKLER.title(), "未选中该洒水器类型：" + wanted
                    + "（当前已选：" + String.join("、", selectedSprinklerTypeNames()) + "）");
                return false;
            }
            verify = "人工确认世界载体";
        }
        SprinklerWorldBinding binding = SprinklerWorldBinding.capture(pos, definition.key());
        if (binding == null) {
            pointFailure(StardewPointType.SPRINKLER.title(), "无法读取准星方块的稳定世界载体状态");
            return false;
        }
        String mismatch = sprinklerMismatch(pos, definition);
        if (mismatch != null) {
            pointFailure(StardewPointType.SPRINKLER.title(), mismatch);
            return false;
        }
        savePoint(StardewPointType.SPRINKLER, new StardewPointManager.StardewPoint(pos.getX(), pos.getY(), pos.getZ(),
            StardewContext.dimension(), verify,
            definition.key(), definition.displayName(), definition.statusLabel(), binding));
        pointSuccess("洒水器点位", pos, verify, definition.displayName(), definition.statusLabel());
        return true;
    }

    /**
     * 附近扫描出来的洒水器（预览用）：坐标 + 认出来的类型。
     *
     * <p>它<b>不是点位</b>：不落盘、不参与决策、不进任何列表，只给「预览范围」画框用。</p>
     */
    public record NearbySprinkler(BlockPos pos, SprinklerDefinition definition) {
    }

    /**
     * 扫描一片区域里「认得出类型」的洒水器：一次实体查询，不逐格遍历世界。
     *
     * <p>路线与绑定一致（{@code item_display} 实体 → {@link #identifySprinklerAt(BlockPos)}），
     * 因此扫描看到的类型与实际绑定结果不会打架。同一格有多个展示实体时只算一次。</p>
     */
    public List<NearbySprinkler> scanSprinklers(AABB box) {
        if (mc.level == null || box == null) return List.of();
        List<NearbySprinkler> found = new ArrayList<>();
        java.util.Set<BlockPos> seen = new java.util.HashSet<>();
        for (Entity entity : mc.level.getEntities((Entity) null, box, e -> e instanceof Display.ItemDisplay)) {
            BlockPos pos = entity.blockPosition();
            if (!seen.add(pos)) continue;
            SprinklerDefinition definition = identifySprinklerAt(pos);
            if (definition != null) found.add(new NearbySprinkler(pos.immutable(), definition));
        }
        return found;
    }

    /**
     * 准星这一格上是哪种洒水器：方块资源语义优先，认不出再走贴图实体。
     *
     * <p>与「添加洒水器」用的是同一条识别链，因此准星预览看到的结果与实际绑定结果不会打架。
     * 只用于预览显示，不落盘、不参与任何决策。</p>
     *
     * @return 认不出返回 {@code null}
     */
    public SprinklerDefinition identifySprinklerAt(BlockPos pos) {
        if (pos == null || mc.level == null) return null;
        SprinklerDefinition definition = matchSprinkler(pos);
        return definition != null ? definition : matchSprinklerDisplay(pos);
    }

    /**
     * 贴图实体识别：判断准星这一格挂着的展示实体（{@code item_display}）用的是哪种洒水器模型。
     *
     * <p><b>为什么必须有这条路：</b>不少服务器把「洒水器」做成隐形方块载体（如
     * {@code sugar_cane[age=9]} 映射到 {@code block/empty}）再加一个 {@code item_display} 实体当外观，
     * 于是三种洒水器<b>方块状态一模一样</b>，资源语义永远认不出；但实体是本机渲染的，客户端看得见，
     * 其物品的 {@code ITEM_MODEL} 就是洒水器本体模型（如 {@code customcrops:sprinkler_2}）。</p>
     *
     * <p>只认「实体所在方块格 == 准星那一格」，因此不会误取隔壁那台洒水器。</p>
     *
     * @return 唯一命中的洒水器定义；没有 / 有歧义返回 null
     */
    private SprinklerDefinition matchSprinklerDisplay(BlockPos pos) {
        if (mc.level == null || !mc.level.isLoaded(pos)) return null;
        AABB box = new AABB(pos).inflate(1.0);
        SprinklerDefinition match = null;
        for (Entity entity : mc.level.getEntities((Entity) null, box, e -> e instanceof Display.ItemDisplay)) {
            if (!entity.blockPosition().equals(pos)) continue;
            SlotAccess slot = entity.getSlot(0);
            ItemStack stack = slot == null ? ItemStack.EMPTY : slot.get();
            if (stack.isEmpty()) continue;
            var model = stack.get(DataComponents.ITEM_MODEL);
            if (model == null) continue;
            SprinklerDefinition hit = sprinklerByItemModel(model.toString());
            if (hit == null) continue;
            if (match != null && !match.key().equals(hit.key())) return null;
            match = hit;
        }
        return match;
    }

    /** 物品模型 → 洒水器定义（查全量索引，不受当前勾选影响：实物是哪一种就是哪一种） */
    private SprinklerDefinition sprinklerByItemModel(String itemModel) {
        for (StardewToolDefinition entry : index.entriesFor(StardewSelectorCategory.SPRINKLER)) {
            if (entry instanceof SprinklerDefinition sprinkler && itemModel.equals(sprinkler.itemModel())) {
                return sprinkler;
            }
        }
        return null;
    }

    /** 当前已选的洒水器类型（保持选择顺序） */
    private List<SprinklerDefinition> selectedSprinklers() {
        List<SprinklerDefinition> selected = new ArrayList<>();
        for (String key : settings.selectedSprinklerKeys) {
            if (index.entryByKey(key) instanceof SprinklerDefinition sprinkler) selected.add(sprinkler);
        }
        return selected;
    }

    /** 当前已选洒水器类型的中文名（TAB 补全与失败提示共用） */
    public List<String> selectedSprinklerTypeNames() {
        List<String> names = new ArrayList<>();
        for (SprinklerDefinition sprinkler : selectedSprinklers()) names.add(sprinkler.displayName());
        return names;
    }

    /** 玩家写明的类型（中文名或逻辑键）对应的已选洒水器；没写对 / 没选它返回 null */
    private SprinklerDefinition selectedSprinkler(String input) {
        for (SprinklerDefinition sprinkler : selectedSprinklers()) {
            if (sprinkler.displayName() != null && sprinkler.displayName().equals(input)) return sprinkler;
            if (sprinkler.key().equals(input)) return sprinkler;
        }
        return null;
    }

    /**
     * 绑定前最后一道核对：准星这个方块被资源包<b>确认</b>认成了别人就拒绝。
     *
     * <p><b>为什么必须加：</b>旧实现只要类型能定下来就直接落盘，对着沙子、种植盆也能绑成洒水器。
     * <b>为什么只挡「已确认」：</b>本服真洒水器多是隐形载体（模型 {@code block/empty}、语义「未知」），
     * 资源包压根认不出来——认不出来的方块正是人工确认存在的意义，挡掉它等于功能作废。
     * 而沙子、种植盆这类能明确认出来的方块，绝不允许被绑成洒水器。</p>
     *
     * @return null 表示允许绑定；否则为可读的中文失败原因
     */
    private String sprinklerMismatch(BlockPos pos, SprinklerDefinition definition) {
        if (mc.level == null || !mc.level.isLoaded(pos)) return null;
        BlockSemantic semantic = BlockStateModelResolver.resolve(mc.level.getBlockState(pos));
        if (!semantic.isConfirmed()) return null;
        if (definition.identityKey() != null && definition.identityKey().equals(semantic.identity())) return null;
        if (definition.blockModel() != null && definition.blockModel().equals(semantic.model())) return null;
        String name = semantic.name() != null ? semantic.name()
            : semantic.identity() != null ? semantic.identity() : semantic.model();
        return "准星这个方块是「" + name + "」，不是洒水器；请对准洒水器实物";
    }

    /** 准星移除洒水器点位（GUI「移除」按钮与 {@code .stardew 移除洒水器} 共用） */
    public boolean removeSprinklerFromCrosshair() {
        if (!pointEnvironmentAllowed()) return false;
        BlockPos pos = crosshairOrEntityBlock();
        if (pos == null) {
            pointFailure(StardewPointType.SPRINKLER.title(),
                "准星没有对准洒水器（准星命中：" + crosshairHitLabel() + "）");
            return false;
        }
        StardewPointManager.StardewPoint existing = findSprinkler(pointManager, pos);
        if (existing == null) {
            pointFailure(StardewPointType.SPRINKLER.title(), "该坐标没有洒水器点位");
            return false;
        }
        pointManager.load(StardewContext.serverKey());
        pointManager.removeSprinkler(pos);
        pointManager.save(StardewContext.serverKey());
        coordinator.reset();
        CommandMessageFormatter.of("星露谷农场", "已删除洒水器点位")
            .world()
            .field("维度", StardewContext.dimension())
            .coord(pos.getX(), pos.getY(), pos.getZ())
            .field("类型", existing.typeName() == null ? "洒水器" : existing.typeName())
            .status(CommandMessageFormatter.Level.SUCCESS, "已删除")
            .send();
        return true;
    }

    /**
     * 删除指定洒水器点位（控制台「洒水器点位」列表逐条删除用）。
     *
     * <p>与准星删除共用同一套落盘与播报，只是一条播报对应真正删掉的那一格——不存在
     * 「删了全部却只报一个坐标」这种情况：要全删请用 {@link #clearSprinklerPoints()}。</p>
     */
    public boolean removeSprinklerPoint(StardewPointManager.StardewPoint target) {
        if (!pointEnvironmentAllowed() || target == null) return false;
        pointManager.load(StardewContext.serverKey());
        pointManager.removeSprinkler(target);
        pointManager.save(StardewContext.serverKey());
        coordinator.reset();
        CommandMessageFormatter.of("星露谷农场", "已删除洒水器点位")
            .world()
            .field("维度", target.dimension())
            .coord(target.x(), target.y(), target.z())
            .field("类型", target.typeName() == null ? "洒水器" : target.typeName())
            .status(CommandMessageFormatter.Level.SUCCESS, "已删除")
            .send();
        return true;
    }

    /** 清空当前服务器的全部洒水器点位（控制台「洒水器点位」列表底部按钮） */
    public void clearSprinklerPoints() {
        if (!pointEnvironmentAllowed()) return;
        pointManager.load(StardewContext.serverKey());
        int bound = pointManager.count(StardewPointType.SPRINKLER);
        pointManager.clear(StardewPointType.SPRINKLER);
        pointManager.save(StardewContext.serverKey());
        coordinator.reset();
        CommandMessageFormatter.of("星露谷农场", "已清空全部洒水器点位")
            .world()
            .field("数量", bound + " 个")
            .status(CommandMessageFormatter.Level.SUCCESS, "已清空")
            .send();
    }

    /** 删除单点位（GUI「删除」按钮与 {@code .stardew 移除 xxx} 共用） */
    public boolean removePoint(StardewPointType type) {
        if (!pointEnvironmentAllowed()) return false;
        pointManager.load(StardewContext.serverKey());
        StardewPointManager.StardewPoint existing = pointManager.get(type);
        if (existing == null) {
            pointFailure(type.title(), "本来就没有绑定");
            return false;
        }
        pointManager.clear(type);
        pointManager.save(StardewContext.serverKey());
        coordinator.reset();
        CommandMessageFormatter.of("星露谷农场", "已删除" + type.title())
            .world()
            .field("维度", existing.dimension())
            .coord(existing.x(), existing.y(), existing.z())
            .field("类型", existing.typeName() == null ? type.title() : existing.typeName())
            .status(CommandMessageFormatter.Level.SUCCESS, "已删除")
            .send();
        return true;
    }

    /** 清空全部点位（GUI 与 {@code .stardew 清空} 共用） */
    public void clearAllPoints() {
        if (!pointEnvironmentAllowed()) return;
        pointManager.load(StardewContext.serverKey());
        int bound = 0;
        for (StardewPointType type : StardewPointType.values()) bound += pointManager.count(type);
        pointManager.clearAll();
        pointManager.save(StardewContext.serverKey());
        coordinator.reset();
        CommandMessageFormatter.of("星露谷农场", "已清空全部点位")
            .world()
            .field("数量", bound + " 个")
            .status(CommandMessageFormatter.Level.SUCCESS, "已清空")
            .send();
    }

    /** 点位只允许写入当前已检测的多人服务器。 */
    private boolean pointEnvironmentAllowed() {
        if (GameProbe.isMultiplayer() && ResourceExtractionService.isReady()) {
            pointManager.load(StardewContext.serverKey());
            return true;
        }
        pointFailure("点位", "仅支持已检测资源的多人服务器");
        return false;
    }

    // ── 点位校验与播报（私有） ──

    /** 准星命中的方块坐标；未命中返回 null（统一 BlockHitResult 判定，绝不强转） */
    private BlockPos crosshairBlock() {
        if (mc.player == null || mc.level == null) return null;
        HitResult hit = mc.hitResult;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) return null;
        if (!(hit instanceof BlockHitResult blockHit)) return null;
        return blockHit.getBlockPos().immutable();
    }

    /**
     * 洒水器点位专用目标坐标：方块命中优先，其次<b>实体命中</b>。
     *
     * <p><b>为什么必须接受实体命中：</b>这类服务器（CraftEngine 系，客户端没装对应模组）的洒水器是
     * 展示实体渲染的，世界上没有对应方块，只在同一格配了一个 {@code interaction} 实体承担交互。
     * 准星射线会命中那个实体而不是方块，若只认 {@code BlockHitResult}，对着实物正中间也会被判成
     * 「准星没有对准任何方块」（真机事故：moexd 设置洒水器失败）。</p>
     *
     * <p>取值口径与 {@code matchSprinklerDisplay} 完全一致——它也要求展示实体的 {@code blockPosition}
     * 等于绑定坐标，因此两边不可能指到不同的格子。</p>
     */
    private BlockPos crosshairOrEntityBlock() {
        if (mc.player == null || mc.level == null) return null;
        HitResult hit = mc.hitResult;
        if (hit == null) return null;
        if (hit.getType() == HitResult.Type.BLOCK && hit instanceof BlockHitResult blockHit) {
            return blockHit.getBlockPos().immutable();
        }
        if (hit.getType() == HitResult.Type.ENTITY && hit instanceof EntityHitResult entityHit
            && entityHit.getEntity() != null) {
            // 只认「无碰撞箱的交互载体」（interaction / 载具等）。生物一律不接受：对着牛也能绑成
            // 洒水器是明显的误绑，宁可失败并如实报出命中目标。
            if (entityHit.getEntity() instanceof LivingEntity) return null;
            return entityHit.getEntity().blockPosition().immutable();
        }
        return null;
    }

    /** 准星实际命中了什么（失败提示用：区分「没命中」与「命中的是什么」） */
    private String crosshairHitLabel() {
        HitResult hit = mc.hitResult;
        if (hit == null || hit.getType() == HitResult.Type.MISS) return "无";
        if (hit instanceof BlockHitResult blockHit) {
            var id = net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(
                mc.level == null ? net.minecraft.world.level.block.Blocks.AIR
                    : mc.level.getBlockState(blockHit.getBlockPos()).getBlock());
            return "方块 " + (id == null ? "未知" : id);
        }
        if (hit instanceof EntityHitResult entityHit && entityHit.getEntity() != null) {
            var id = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(entityHit.getEntity().getType());
            return "实体 " + (id == null ? "未知" : id) + " @" + entityHit.getEntity().blockPosition().toShortString();
        }
        return "未知";
    }

    /** 补水点使用包含流体的射线检测，否则原版准星只会命中水底或水后的实体方块。 */
    private BlockPos crosshairFluidBlock() {
        if (mc.player == null || mc.level == null) return null;
        HitResult hit = mc.player.pick(settings.reach, 1.0F, true);
        if (!(hit instanceof BlockHitResult blockHit) || hit.getType() != HitResult.Type.BLOCK) return null;
        return blockHit.getBlockPos().immutable();
    }

    /** 多选水壶时按背包中实际存在的最高等级完成启动自检，与执行层选择规则保持一致。 */
    public WateringCanDefinition preferredAvailableCan() {
        WateringCanDefinition best = null;
        for (String key : settings.selectedCanKeys) {
            StardewToolDefinition entry = index.entryByKey(key);
            if (!(entry instanceof WateringCanDefinition can) || inventory.findSlotEntry(can, false) < 0) continue;
            if (best == null || can.canIndex() > best.canIndex()) best = can;
        }
        return best;
    }

    /**
     * 静止水源校验。
     *
     * <p>必须同时满足「流体非空」+「{@code FluidState.isSource()}（等价旧写法的 level=0）」+
     * 「属于 {@code FluidTags.WATER}」。流动水、空气、普通方块、岩浆、未知流体全部拒绝。</p>
     *
     * @return null 表示通过；否则为可读的中文失败原因
     */
    private String waterSourceFailure(BlockPos pos) {
        return StardewPointManager.waterSourceFailure(pos);
    }

    /** 世界方块 → 当前服务器洒水器定义；无法可靠匹配返回 null */
    private SprinklerDefinition matchSprinkler(BlockPos pos) {
        return StardewPointManager.matchSprinkler(pos, index);
    }

    /** 取资源 id / 模型路径的末段；空值返回 null */
    private static String lastSegment(String value) {
        if (value == null || value.isBlank()) return null;
        int slash = value.lastIndexOf('/');
        String tail = slash >= 0 ? value.substring(slash + 1) : value;
        int colon = tail.lastIndexOf(':');
        return colon >= 0 ? tail.substring(colon + 1) : tail;
    }

    /** 在当前维度的洒水器点位里找指定坐标 */
    public static StardewPointManager.StardewPoint findSprinkler(StardewPointManager pointManager, BlockPos pos) {
        for (StardewPointManager.StardewPoint p : pointManager.getAll(StardewPointType.SPRINKLER)) {
            if (p.inCurrentDimension() && p.pos().equals(pos)) return p;
        }
        return null;
    }

    /** 保存点位（先加载当前服务器文件，避免写到别的服务器档） */
    private void savePoint(StardewPointType type, StardewPointManager.StardewPoint point) {
        pointManager.load(StardewContext.serverKey());
        if (type == StardewPointType.SPRINKLER) pointManager.addSprinkler(point);
        else pointManager.set(type, point);
        pointManager.save(StardewContext.serverKey());
        coordinator.reset();
    }

    /** 点位设置失败：统一「标题 + 原因 + 状态」中文播报 */
    private void pointFailure(String title, String reason) {
        CommandMessageFormatter.of("星露谷农场", "设置" + title + "失败")
            .world()
            .field("维度", StardewContext.dimension())
            .field("原因", reason)
            .status(CommandMessageFormatter.Level.FAILURE, "未保存")
            .send();
    }

    /**
     * 点位设置成功：统一「服务器 / 地址 / 维度 / 坐标 / 验证」播报。
     *
     * <p>多服务器、多维度环境下只报「设置成功」等于没报——玩家无法判断这条点位落在哪台服。</p>
     */
    private void pointSuccess(String title, BlockPos pos, String verify, String typeName, String evidence) {
        CommandMessageFormatter formatter = CommandMessageFormatter.of("星露谷农场", "已设置" + title)
            .world()
            .field("维度", StardewContext.dimension())
            .coord(pos.getX(), pos.getY(), pos.getZ());
        if (verify != null) formatter.field("验证", verify);
        formatter.field("类型", typeName == null ? title : typeName);
        formatter.status(CommandMessageFormatter.Level.SUCCESS, evidence == null ? "成功" : "已验证").send();
    }
}
