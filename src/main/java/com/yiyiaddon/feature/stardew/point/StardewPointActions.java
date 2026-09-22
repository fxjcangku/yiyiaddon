package com.yiyiaddon.feature.stardew.point;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.profile.SprinklerDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewSprinklerRangeStore;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.profile.WateringCanDefinition;
import com.yiyiaddon.feature.stardew.render.StardewRenderState;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.platform.resource.ItemModelDispatchIndex;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 点位设置（GUI 按钮与 {@code .stardew} 指令共用同一实现，禁止各写一套）。
 *
 * <p>逐字搬运自旧项目 {@code stardew/StardewFarmModule.java:1059-1446}。只做框架适配：
 * 旧 {@code ServerResourceService} → {@link ResourceExtractionService} + {@link GameProbe}，
 * 旧模块字段 {@code pointManager / index / profile / coordinator / 六类选择 / reach}
 * 由构造器注入。</p>
 */
public final class StardewPointActions {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/stardew");

    /**
     * 视线拾取展示实体时允许的横向偏差（格）。
     *
     * <p>取约一个方块的半宽：既容得下「模型比碰撞范围大」的洒水器，又不会顺手把隔壁那一台也算进来。</p>
     */
    private static final double SIGHT_RADIUS = 0.9;

    /** 「工作范围」自学节流：每 40 刻（约 2 秒）扫一遍背包与附近展示实体 */
    private static final int RANGE_OBSERVE_INTERVAL_TICKS = 40;

    /** 自学观察附近展示实体的半径（格）：与「附近观察学名」同一口径 */
    private static final double RANGE_OBSERVE_RADIUS = 16.0;

    /** 自学扫界面槽位的上限：大箱子 54 格 + 玩家背包 36 格，留足余量即可，绝不扫超大合成界面 */
    private static final int RANGE_OBSERVE_MENU_LIMIT = 128;

    /** 自学倒计时（刻）；到点才扫，稳态下已学到的身份直接跳过 */
    private int rangeObserveCountdown;

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
        pointSuccess(type.title(), pos, verify, null, null, null);
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
                // 取证日志只留在玩家亲手触发的这条路上：这一格到底挂着什么，绑定失败的那一刻写进日志，
                // 否则「洒水器就在眼前却认不出」只能靠反复猜测（真机事故：jmy.seasonmc.xyz 品质随便绑）。
                List<String> carriers = displayCarriersAt(pos);
                if (!carriers.isEmpty()) {
                    LOGGER.info("[星露谷] 洒水器实物识别未命中：格={} 展示物={}",
                        pos.toShortString(), String.join("、", carriers));
                }
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
        // 贴图形态的型号核对：这一格挂的展示实体是另一款时当场拒绝，别绑成「装在高级洒水器上的
        // 优质洒水器点位」——那之后定位 / 覆盖范围 / 水位判断全按错的型号走。
        // 读不出展示身份（模型认不出）时不拦：那是「还不知道」，不是「不一致」。
        String tierConflict = SprinklerWorldBinding.displayTierMismatch(pos, definition.key());
        if (tierConflict != null) {
            var actual = index == null ? null : index.entryByKey(tierConflict);
            pointFailure(StardewPointType.SPRINKLER.title(),
                "这一格挂的是「" + (actual == null ? tierConflict : actual.displayName())
                    + "」，不是要绑的「" + definition.displayName()
                    + "」：请对准实物确认型号，或改用 .stardew 添加洒水器 <类型> 明确指定");
            return false;
        }
        String mismatch = sprinklerMismatch(pos, definition);
        if (mismatch != null) {
            pointFailure(StardewPointType.SPRINKLER.title(), mismatch);
            return false;
        }
        // 绑定这一刻顺手实测覆盖范围：难度最低、代价最小（玩家此刻就站在洒水器旁边），
        // 测不出来也不影响绑定——点位照旧落盘，渲染与统计退回物品说明 / 等级估算。
        // 归属参照给「当前维度全部已绑定洒水器」：一片田里湿盆会连成一片，不按最近归属过滤，
        // 隔壁那台浇的盆也会算到自己头上（真机：三台洒水器的实测范围一模一样且都顶到扫描边界）。
        SprinklerCoverage coverage = SprinklerCoverage.capture(pos, sprinklerPeers(pos));
        savePoint(StardewPointType.SPRINKLER, new StardewPointManager.StardewPoint(pos.getX(), pos.getY(), pos.getZ(),
            StardewContext.dimension(), verify,
            definition.key(), definition.displayName(), definition.statusLabel(), binding, coverage));
        pointSuccess("洒水器点位", pos, verify, definition.displayName(), definition.statusLabel(),
            coverageText(coverage, definition.key(), definition.sprinklerIndex()));
        return true;
    }

    /**
     * 覆盖范围播报文案：只报实测结论本身（实测外接矩形 + 湿盆格数）。
     *
     * <p>矩形内还有干盆、或湿盆顶到扫描边界时一并如实标出——有异常就说明那一次实测不可信，
     * 绝不粉饰。「跟资源包对不对齐」这件事上不能含糊。带 {@code sprinklerKey} 的重载会再点明
     * 画框实际用的是哪个来源。</p>
     */
    public static String coverageText(SprinklerCoverage coverage) {
        if (coverage == null) {
            return "未测出（周围没有湿润的盆，可稍后 .stardew 实测范围 重测）";
        }
        StringBuilder text = new StringBuilder("实测 ")
            .append(coverage.width()).append("×").append(coverage.depth())
            .append(" · 湿盆 ").append(coverage.cells().size()).append(" 格");
        if (coverage.dryInside() > 0) text.append(" · 矩形内干盆 ").append(coverage.dryInside()).append(" 格");
        if (coverage.truncated()) text.append("（顶到扫描边界，实际可能更大）");
        return text.toString();
    }

    /**
     * 覆盖范围播报文案：先报实测结论，再点明<b>画框实际用的是哪个来源</b>。
     *
     * <p><b>来源按可信度：</b>物品说明（服务器自己下发的「工作范围 A * B」，真机试验确认它就是
     * <b>真实覆盖</b>，见 {@link StardewSprinklerRangeStore}）&gt; 实测（数湿盆得来的<b>下限</b>，
     * 盆群比能力小就测不满）&gt; 等级估算。这一行既给出实测证据，也避免让人拿一个被盆群限制的
     * 小框当成洒水器的上限。</p>
     *
     * @param sprinklerKey 洒水器逻辑键，用来取自学到的物品说明范围
     * @param level        洒水器等级，物品说明取不到时退回等级估算
     */
    public static String coverageText(SprinklerCoverage coverage, String sprinklerKey, int level) {
        String text = coverage == null ? "未测出（周围没有湿润的盆）" : coverageText(coverage);
        String stated = StardewSprinklerRangeStore.sideText(sprinklerKey);
        if (stated != null) {
            return coverage == null
                ? text + " · 画框按物品说明 " + stated
                : text + "（下限）· 画框按物品说明 " + stated;
        }
        if (coverage != null) return text + " · 画框按实测结论";
        int side = StardewRenderState.radiusOfLevel(level) * 2 + 1;
        return text + " · 画框按等级估算 " + side + "×" + side;
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
     * 贴图实体识别：判断准星这一格挂着的展示实体（{@code item_display} / {@code block_display}）是哪种洒水器。
     *
     * <p><b>为什么必须有这条路：</b>不少服务器把「洒水器」做成隐形方块载体（如
     * {@code sugar_cane[age=9]} 映射到 {@code block/empty}）再加一个 {@code item_display} 实体当外观，
     * 于是三种洒水器<b>方块状态一模一样</b>，资源语义永远认不出；但实体是本机渲染的，客户端看得见。</p>
     *
     * <p><b>身份口径（2026-09-21 两次修正）：</b>展示物的模型键一律走
     * {@link StardewInventoryService#resolvedModelOf(ItemStack)} —— 资源包派发表按「载体 +
     * {@code custom_model_data}」阈值命中优先，其次 {@code item_model} 组件。
     * 旧实现只读 {@code ITEM_MODEL} 组件，于是 ItemsAdder 布局服务器的洒水器<b>永远认不出</b>：
     * 真机取证（jmy.seasonmc.xyz），初级洒水器 = {@code minecraft:paper} + {@code custom_model_data 10635}，
     * 组件里的 {@code minecraft:paper} 只是<b>载体</b>，真实身份在派发表里 ——
     * 解析结果退化成载体，世界识别三条判据全空，表现为「洒水器就在眼前，品质却写什么就绑什么」。</p>
     *
     * <p><b>多判据（2026-09-21 再补）：</b>同一格可能同时挂着「水花」展示物与承载实物的展示物，
     * 只认物品模型序号会漏；现在 {@code item_display} 三条证据依次比对、{@code block_display}
     * 另走方块语义（判据与方块路径同源），详见 {@link #sprinklerOfDisplay(Entity)}。</p>
     *
     * <p>只认「实体所在方块格 == 准星那一格」，因此不会误取隔壁那台洒水器。</p>
     *
     * @return 唯一命中的洒水器定义；没有 / 有歧义返回 null
     */
    private SprinklerDefinition matchSprinklerDisplay(BlockPos pos) {
        if (mc.level == null || !mc.level.isLoaded(pos)) return null;
        AABB box = new AABB(pos).inflate(1.0);
        SprinklerDefinition match = null;
        List<String> carriers = new ArrayList<>();
        for (Entity entity : mc.level.getEntities((Entity) null, box, e -> e instanceof Display)) {
            if (!entity.blockPosition().equals(pos)) continue;
            carriers.add(describeDisplay(entity));
            SprinklerDefinition hit = sprinklerOfDisplay(entity);
            if (hit == null) continue;
            if (match != null && !match.key().equals(hit.key())) {
                LOGGER.info("[星露谷] 洒水器实物有歧义（同一格挂着多种洒水器），按认不出处理：格={} 展示物={}",
                    pos.toShortString(), String.join("、", carriers));
                return null;
            }
            match = hit;
        }
        return match;
    }

    /**
     * 某一格挂着的全部展示实体摘要。
     *
     * <p>只给「玩家亲手触发的绑定」当取证日志用：识别在预览扫描里每秒都会被调一遍，
     * 把识别失败一并写在扫描路径上会把日志刷爆（真机：相邻 8 个展示物每秒 8 条，含根本不是
     * 洒水器的「稻草人」）。</p>
     */
    private List<String> displayCarriersAt(BlockPos pos) {
        if (mc.level == null || !mc.level.isLoaded(pos)) return List.of();
        List<String> carriers = new ArrayList<>();
        for (Entity entity : mc.level.getEntities((Entity) null, new AABB(pos).inflate(1.0), e -> e instanceof Display)) {
            if (!entity.blockPosition().equals(pos)) continue;
            carriers.add(describeDisplay(entity));
        }
        return carriers;
    }

    /**
     * 单个展示实体 → 洒水器定义：{@code item_display} 走物品模型，{@code block_display} 走方块模型。
     *
     * <p><b>三条证据依次比对：</b>① 物品模型末段序号（{@code …/sprinkler_1} → {@code sprinkler_1}）；
     * ② 压平身份（{@code customcrops:sprinkler_1}，即逻辑键本身）；③ 展示物名称（服务器下发的
     * 「初级洒水器」）。任一条能唯一对上就是它，三条全空才算认不出——绝不因为第一条对不上就放行，
     * 那正是「写明任意品质都能绑上」的成因。</p>
     */
    private SprinklerDefinition sprinklerOfDisplay(Entity entity) {
        if (entity instanceof Display.ItemDisplay display) {
            ItemStack stack = display.getItemStack();
            if (stack == null || stack.isEmpty()) return null;
            SprinklerDefinition hit = sprinklerOfStack(stack);
            if (hit != null) learnRange(hit, stack);
            return hit;
        }
        if (entity instanceof Display.BlockDisplay display) {
            return sprinklerBySemantic(BlockStateModelResolver.resolve(display.getBlockState()));
        }
        return null;
    }

    /**
     * 单个物品栈 → 洒水器定义（世界展示实体与背包共用同一条判据链）。
     *
     * <p>方块展示实体走方块语义，不经过这里。</p>
     */
    private SprinklerDefinition sprinklerOfStack(ItemStack stack) {
        String model = StardewInventoryService.resolvedModelOf(stack);
        SprinklerDefinition byModel = sprinklerByItemModel(model);
        if (byModel != null) return byModel;
        SprinklerDefinition byIdentity = sprinklerByFlatIdentity(ItemModelDispatchIndex.flatIdentityOf(model));
        return byIdentity != null ? byIdentity : sprinklerByName(stack.getHoverName().getString());
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  工作范围自学（物品说明 → 覆盖范围）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 自学洒水器的「工作范围」：服务器把范围写在物品说明里（真机：初级「工作范围 5 * 5」），
     * 见过一次实物就能读到，比写死的等级表准，且换服换包自动跟上
     * （见 {@link StardewSprinklerRangeStore}）。
     *
     * <p>由模块的<b>常驻 tick 订阅</b>驱动（与模块开关解耦：摆台 / 绑定通常发生在停机状态），
     * 内部自己节流。已学到的身份直接跳过（拼 tooltip 不便宜），所以稳态下这一拍几乎零开销。</p>
     */
    public void observeSprinklerRanges() {
        if (mc.player == null || mc.level == null) return;
        if (--rangeObserveCountdown > 0) return;
        rangeObserveCountdown = RANGE_OBSERVE_INTERVAL_TICKS;
        var inventory = mc.player.getInventory();
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            observeRange(inventory.getItem(slot));
        }
        observeRange(mc.player.getOffhandItem());
        // 开着箱子 / 商店 / 界面时也看一眼：玩家的洒水器多半存在箱子里，物品说明只挂在那种完整物品栈上
        // （世界展示实体上的栈常常是服务端的精简副本，没有说明文字）
        var menu = mc.player.containerMenu;
        if (menu != null) {
            for (int slot = 0; slot < Math.min(menu.slots.size(), RANGE_OBSERVE_MENU_LIMIT); slot++) {
                observeRange(menu.getSlot(slot).getItem());
            }
        }
        AABB box = mc.player.getBoundingBox().inflate(RANGE_OBSERVE_RADIUS);
        for (Entity entity : mc.level.getEntities((Entity) null, box, e -> e instanceof Display.ItemDisplay)) {
            if (entity instanceof Display.ItemDisplay display) observeRange(display.getItemStack());
        }
    }

    /** 认出是洒水器就把说明里的范围记下来；不是洒水器 / 认不出 / 已学过都什么都不做 */
    private void observeRange(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;
        SprinklerDefinition definition = sprinklerOfStack(stack);
        if (definition != null) learnRange(definition, stack);
    }

    private void learnRange(SprinklerDefinition definition, ItemStack stack) {
        StardewSprinklerRangeStore.observe(definition.key(), stack);
    }

    /** 压平身份（{@code customcrops:sprinkler_1}）→ 洒水器定义；它必须与洒水器逻辑键逐字相同 */
    private SprinklerDefinition sprinklerByFlatIdentity(String identity) {
        if (identity == null || identity.isBlank()) return null;
        for (StardewToolDefinition entry : index.entriesFor(StardewSelectorCategory.SPRINKLER)) {
            if (entry instanceof SprinklerDefinition sprinkler && identity.equalsIgnoreCase(sprinkler.key())) {
                return sprinkler;
            }
        }
        return null;
    }

    /** 展示物名称（服务器下发的「初级洒水器」）→ 洒水器定义；同名命中多个不同洒水器时按认不出处理 */
    private SprinklerDefinition sprinklerByName(String name) {
        if (name == null || name.isBlank()) return null;
        SprinklerDefinition match = null;
        for (StardewToolDefinition entry : index.entriesFor(StardewSelectorCategory.SPRINKLER)) {
            if (!(entry instanceof SprinklerDefinition sprinkler) || !name.equals(sprinkler.displayName())) continue;
            if (match != null && !match.key().equals(sprinkler.key())) return null;
            match = sprinkler;
        }
        return match;
    }

    /** 方块语义（{@code block_display} 携带的方块状态）→ 洒水器定义；判据与方块世界路径同源 */
    private SprinklerDefinition sprinklerBySemantic(BlockSemantic semantic) {
        List<SprinklerDefinition> candidates = new ArrayList<>();
        for (StardewToolDefinition entry : index.entriesFor(StardewSelectorCategory.SPRINKLER)) {
            if (entry instanceof SprinklerDefinition sprinkler) candidates.add(sprinkler);
        }
        return StardewPointManager.matchSprinklerBySemantic(semantic, candidates);
    }

    /** 展示实体摘要（诊断日志用）：形态 + 物品 / 方块状态 + 模型键 + 名称，一条里给全 */
    private static String describeDisplay(Entity entity) {
        if (entity instanceof Display.ItemDisplay display) {
            ItemStack stack = display.getItemStack();
            if (stack == null || stack.isEmpty()) return "item_display(空)";
            var itemId = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem());
            return "item_display(" + (itemId == null ? "未知" : itemId) + " 模型="
                + StardewInventoryService.resolvedModelOf(stack) + " 名=" + stack.getHoverName().getString() + ")";
        }
        if (entity instanceof Display.BlockDisplay display) {
            return "block_display(" + display.getBlockState() + ")";
        }
        var type = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return type == null ? "展示实体" : type.toString();
    }

    /**
     * 展示物模型键 → 洒水器定义（查全量索引，不受当前勾选影响：实物是哪一种就是哪一种）。
     *
     * <p><b>按末段序号名比较，两侧都先剥掉背包形态后缀 {@code _item}：</b>同一台洒水器在资源包里有两个
     * 模型（{@code sprinkler_1_item} 是背包图、{@code sprinkler_1} 是环境模型），世界里挂的、索引登记的
     * 可能是任一个（真机：某服把两者一起派发），逐字比较 {@code itemModel()} 必然漏。</p>
     */
    private SprinklerDefinition sprinklerByItemModel(String itemModel) {
        String serial = sprinklerSerialOf(itemModel);
        if (serial == null) return null;
        for (StardewToolDefinition entry : index.entriesFor(StardewSelectorCategory.SPRINKLER)) {
            if (entry instanceof SprinklerDefinition sprinkler
                && serial.equals(sprinklerSerialOf(sprinkler.itemModel()))) {
                return sprinkler;
            }
        }
        return null;
    }

    /** 洒水器模型键 → 末段序号名（{@code customcrops:item/sprinklers/sprinkler_1_item} → {@code sprinkler_1}）；非洒水器返回 null */
    private static String sprinklerSerialOf(String model) {
        String last = lastSegment(model);
        if (last == null) return null;
        String lower = last.toLowerCase(Locale.ROOT);
        String base = lower.endsWith(StardewResourceIndex.ITEM_FORM_SUFFIX)
            ? lower.substring(0, lower.length() - StardewResourceIndex.ITEM_FORM_SUFFIX.length())
            : lower;
        return base.startsWith("sprinkler_") ? base : null;
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
     * <p><b>空气必须单独放行（2026-09-21 补）：</b>贴图形态洒水器的载体是展示实体，那一格<b>根本
     * 没有方块</b>，方块语义核对无从谈起。但资源包会给空气一个模型（{@code minecraft:block/air}），
     * 语义层于是判成「已确认 · 空气」，不特判就会把真洒水器挡成「准星这个方块是空气，不是洒水器」。
     * 判据与 {@link SprinklerWorldBinding#capture} 同源——两边只要有一边没跟上，就会出现
     * 「能写绑定却过不了核对」或反过来的自相矛盾（真机事故：jmy.seasonmc.xyz）。</p>
     *
     * <p><b>贴图形态必须核实物品质（2026-09-21 再补）：</b>空气载体这一支以前只判「这一格有没有挂着
     * 展示实体」，而三种品质洒水器的载体与方块状态<b>完全一样</b>，等于没判——实机表现就是
     * <b>写明任意品质都能绑上</b>（jmy.seasonmc.xyz：初级洒水器被绑成「高级洒水器」，此后维护、
     * 等级、覆盖范围全按错的身份走）。现在改为认得出实物就比品质（同一格多个实体互相矛盾时
     * {@link #matchSprinklerDisplay} 返回 null，一律放行，不拿歧义当证据）。</p>
     *
     * @return null 表示允许绑定；否则为可读的中文失败原因
     */
    private String sprinklerMismatch(BlockPos pos, SprinklerDefinition definition) {
        if (mc.level == null || !mc.level.isLoaded(pos)) return null;
        var state = mc.level.getBlockState(pos);
        if (state.isAir()) {
            if (!SprinklerWorldBinding.displayCarrierAt(pos)) {
                return "准星这一格是空气，没有洒水器实物；请对准洒水器";
            }
            return displayMismatch(pos, definition);
        }
        BlockSemantic semantic = BlockStateModelResolver.resolve(state);
        if (!semantic.isConfirmed()) {
            // 隐形载体（模型 block/empty、语义「未知」）：方块这一侧给不出身份，同样只认展示实体
            return displayMismatch(pos, definition);
        }
        if (definition.identityKey() != null && definition.identityKey().equals(semantic.identity())) return null;
        if (definition.blockModel() != null && definition.blockModel().equals(semantic.model())) return null;
        String name = semantic.name() != null ? semantic.name()
            : semantic.identity() != null ? semantic.identity() : semantic.model();
        return "准星这个方块是「" + name + "」，不是洒水器；请对准洒水器实物";
    }

    /**
     * 展示实体实物核对：这一格挂着的洒水器外观，是不是所声明的这一种。
     *
     * <p>判据与 {@link #identifySprinklerAt(BlockPos)} 完全同源（都走 {@link #matchSprinklerDisplay}），
     * 因此「准星预览看到的类型」与「绑定时核对的类型」不会打架。认不出实物一律放行——那条路正是
     * 人工确认存在的意义，挡掉它会让 ItemsAdder 之外的服务器整台绑不上。</p>
     *
     * @return null 表示一致或实物认不出（允许绑定）；否则为可读的中文失败原因
     */
    private String displayMismatch(BlockPos pos, SprinklerDefinition definition) {
        SprinklerDefinition actual = matchSprinklerDisplay(pos);
        if (actual == null || actual.key().equals(definition.key())) return null;
        return "这一格上摆的是「" + actual.displayName() + "」，不是「" + definition.displayName()
            + "」；请对准实物，或改用正确类型：.stardew 添加洒水器 " + actual.displayName();
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
     * 洒水器点位专用目标坐标：方块命中 → 可拾取实体命中 →（必要时）展示实体视线拾取。
     *
     * <p><b>为什么必须接受可拾取实体命中：</b>这类服务器（CraftEngine 系，客户端没装对应模组）的洒水器是
     * 展示实体渲染的，世界上没有对应方块，只在同一格配了一个 {@code interaction} 实体承担交互。
     * 准星射线会命中那个实体而不是方块，若只认 {@code BlockHitResult}，对着实物正中间也会被判成
     * 「准星没有对准任何方块」（真机事故：moexd 设置洒水器失败）。</p>
     *
     * <p><b>为什么还要兜一条视线拾取：</b>{@code Display} 的 {@code isPickable()} 为 {@code false}，
     * 原版准星拾取只收可拾取实体，会把展示实体整个过滤掉，于是 {@code mc.hitResult} 落在洒水器
     * <b>背后的方块</b>上、或干脆 MISS，表现成「洒水器就在眼前，准星却怎么都对不上，命中结果还不是实体」。
     * {@code interaction} 载体可拾取，所以这条只在没配 interaction 的服务器上才会用到。</p>
     *
     * <p><b>多服务器适配的底线——原版结果永远优先：</b>视线拾取只在两种情况下接管：① 原版根本给不出
     * 目标（MISS）；② 原版给出的是方块、而那一格<b>既认不成洒水器、又不挂展示实体</b>。只要原版那一格
     * 能被资源语义认成洒水器（方块形态洒水器的服务器），或者它本身就挂着展示实体（贴图形态洒水器的服务器），
     * 一律沿用原版结果，<b>已有服务器的行为一字不变</b>。</p>
     */
    private BlockPos crosshairOrEntityBlock() {
        if (mc.player == null || mc.level == null) return null;
        BlockPos direct = directTarget();
        BlockPos sight = sightDisplayBlock();
        if (sight == null) return direct;
        if (direct == null) return sight;
        // 「这一格挂着展示实体」与载体捕捉共用 SprinklerWorldBinding 的同一份判据：若各写一套，
        // 会出现「坐标取到了、载体却读不出来」这种自相矛盾的失败（真机事故：jmy.seasonmc.xyz）。
        if (matchSprinkler(direct) != null || SprinklerWorldBinding.displayCarrierAt(direct)) return direct;
        return sight;
    }

    /** 原版命中结果给出的目标坐标：方块命中优先，其次可拾取实体（生物除外）；都没有返回 {@code null} */
    private BlockPos directTarget() {
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

    /**
     * 沿视线拾取最近的展示实体所在方块格；没有返回 {@code null}。
     *
     * <p>原版命中结果给不到展示实体（见 {@link #crosshairOrEntityBlock()}），所以这里自己扫：实体中心
     * 必须落在视线的<b>前方</b>（{@code 0 < 沿视线距离 ≤ reach}），且到视线的<b>垂直距离</b>不超过
     * {@link #SIGHT_RADIUS}；多台同时命中时取沿视线最近的那一台。</p>
     *
     * <p>只做几何判定，不在这里判是不是洒水器——认不出来正是「人工确认」存在的意义，
     * 识别与拒绝分别由调用方与 {@code sprinklerMismatch} 负责。</p>
     */
    private BlockPos sightDisplayBlock() {
        Vec3 eye = mc.player.getEyePosition(1.0F);
        Vec3 look = mc.player.getViewVector(1.0F);
        double reach = settings.reach;
        Vec3 end = eye.add(look.scale(reach));
        AABB path = new AABB(eye, end).inflate(SIGHT_RADIUS);
        BlockPos best = null;
        double bestAlong = Double.MAX_VALUE;
        for (Entity entity : mc.level.getEntities((Entity) null, path, e -> e instanceof Display.ItemDisplay)) {
            Vec3 delta = entity.getBoundingBox().getCenter().subtract(eye);
            double along = delta.dot(look);
            if (along <= 0.0 || along > reach) continue;
            if (delta.subtract(look.scale(along)).length() > SIGHT_RADIUS) continue;
            if (along >= bestAlong) continue;
            bestAlong = along;
            best = entity.blockPosition().immutable();
        }
        return best;
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

    /**
     * 覆盖范围实测的<b>归属参照</b>：当前维度全部已绑定洒水器坐标 + 本次这一格。
     *
     * <p>含本台的理由与 {@code SprinklerCoverage.capture} 的 javadoc 一致：算「离哪台最近」时
     * 自己必须参与比较，否则新绑的这一台没进集合，它的湿盆会全被判给旁边的老点位。</p>
     */
    private List<BlockPos> sprinklerPeers(BlockPos pos) {
        List<BlockPos> positions = new ArrayList<>();
        for (StardewPointManager.StardewPoint point
                : pointManager.getInCurrentDimension(StardewPointType.SPRINKLER)) {
            positions.add(point.pos());
        }
        if (pos != null && !positions.contains(pos)) positions.add(pos);
        return positions;
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
     *
     * @param coverage 覆盖范围文案（仅洒水器传；其它点位传 null 表示不显示该行）
     */
    private void pointSuccess(String title, BlockPos pos, String verify, String typeName, String evidence,
                              String coverage) {
        CommandMessageFormatter formatter = CommandMessageFormatter.of("星露谷农场", "已设置" + title)
            .world()
            .field("维度", StardewContext.dimension())
            .coord(pos.getX(), pos.getY(), pos.getZ());
        if (verify != null) formatter.field("验证", verify);
        formatter.field("类型", typeName == null ? title : typeName);
        if (coverage != null) formatter.field("覆盖范围", coverage);
        formatter.status(CommandMessageFormatter.Level.SUCCESS, evidence == null ? "成功" : "已验证").send();
    }
}
