package com.yiyiaddon.feature.stardew;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.stardew.adapter.DefaultStardewAdapter;
import com.yiyiaddon.feature.stardew.adapter.StardewAdapter;
import com.yiyiaddon.feature.stardew.command.StardewCommand;
import com.yiyiaddon.feature.stardew.command.StardewQuerySupport;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.logistics.StardewLogisticsStore;
import com.yiyiaddon.feature.stardew.memory.FarmMemoryStore;
import com.yiyiaddon.feature.stardew.plan.StardewCropPlanStore;
import com.yiyiaddon.feature.stardew.point.StardewPointActions;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.SprinklerDefinition;
import com.yiyiaddon.feature.stardew.recognition.CropRuntimeStateResolver;
import com.yiyiaddon.feature.stardew.region.StardewRegionManager;
import com.yiyiaddon.feature.stardew.region.StardewRegionSelector;
import com.yiyiaddon.feature.stardew.render.StardewRenderState;
import com.yiyiaddon.feature.stardew.scan.StardewFarmScanner;
import com.yiyiaddon.feature.stardew.season.StardewSeasonBinding;
import com.yiyiaddon.feature.stardew.season.StardewSeasonService;
import com.yiyiaddon.feature.stardew.selector.StardewSelectionBinding;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.feature.stardew.service.StardewProfileAssembler;
import com.yiyiaddon.feature.stardew.service.StardewStartupCheck;
import com.yiyiaddon.feature.stardew.status.StardewStatusCard;
import com.yiyiaddon.feature.stardew.status.StardewStatusReporter;
import com.yiyiaddon.feature.stardew.status.StardewStatusSnapshot;
import com.yiyiaddon.feature.stardew.task.StardewCoordinator;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleData;
import com.yiyiaddon.feature.stardew.ui.StardewResourcePanelPage;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.identity.ItemIdentifier;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 * 星露谷农场 —— 全自动种植 / 浇水 / 收割系统（V1）。
 *
 * <p>核心原则：先看当前服务器资源包与 ID 配置里实际有什么，再决定能识别 / 能配置 /
 * 能执行什么；成熟判定只信服务器档案的成熟阶段规则（绝不猜 max(stage)），收割后以
 * 真实世界观察为准决定补种 / 保留。六类选择器 + 点位 + 农田记忆 + 服务器档案分层
 * 隔离，切服 / 换档互不串用。</p>
 *
 * <p>逐字搬运自旧项目 {@code stardew/StardewFarmModule.java}。框架适配点：旧 Meteor
 * {@code Setting} 体系 → {@link StardewSettings} 普通配置类；旧 {@code @EventHandler}
 * → 本类 {@code onEnable/onDisable} 里的 {@link ClientEventBus} 订阅退订；旧
 * {@code onRender3D/onRender2D} → {@link WorldOverlay} 注册的 {@link StardewRenderState}；
 * 旧 {@code isActive()/toggle()} → {@code isEnabled()/ModuleManager.setEnabled}；
 * 旧 {@code ServerResourceService} → {@link ResourceExtractionService} +
 * {@link GameProbe#isMultiplayer()}（等价旧 {@code resourceActionsAllowed()}）。</p>
 */
public final class StardewFarmModule extends Module {

    /** 模块 ID，同时作为状态文件键与世界渲染层标识 */
    public static final String MODULE_ID = "stardew";
    /** 范围预览渲染层：与模块自身那一层分开，模块启动后预览层就不再挂载 */
    private static final String PREVIEW_LAYER_ID = MODULE_ID + "-preview";
    /** 事件订阅所有者标识 */
    private static final String EVENT_OWNER = "module.stardew";

    /** 图标字形：拖拉机（Material Symbols agriculture，已确认存在于所引字体） */
    private static final String ICON = "\uEA79";

    private final Minecraft mc = Minecraft.getInstance();

    // ── 服务实例 ──
    private final IdentityService identityService;
    private final StardewResourceIndex index;
    private final StardewPointManager pointManager = new StardewPointManager();
    private final FarmMemoryStore memory = new FarmMemoryStore();
    private final StardewFarmScanner scanner = new StardewFarmScanner();
    private final StardewAdapter adapter = new DefaultStardewAdapter();
    private final StardewInventoryService inventory;
    private final StardewCoordinator coordinator;
    private final StardewStatusReporter statusReporter;
    private final StardewProfileAssembler profileAssembler;
    private final StardewQuerySupport query;
    private final StardewPointActions pointActions;
    private final StardewRenderState renderState;

    // ── 分区种植（实验功能）：区域数据 + 选区会话 ──
    private final StardewRegionManager regionManager = new StardewRegionManager();
    private final StardewRegionSelector regionSelector;

    /** 全部设置项的数据载体（旧项目 Setting 定义逐字搬入 {@link StardewSettings}） */
    private final StardewSettings settings = new StardewSettings();

    public static final String MODULE_NAME = "星露谷农场";
    /** 可靠识别出的四季中文名；不在此集合内即视为未识别（当前季节 / 未知）。 */
    public static final java.util.Set<String> NAMED_SEASONS =
        java.util.Set.of("春季", "夏季", "秋季", "冬季");
    /** 自检时季节未识别，再等这么久（tick）仍识别不出，才播证据卡；中途识别成功则不播。 */
    public static final int SEASON_DIAGNOSTIC_GRACE_TICKS = 200;

    /**
     * 启动自检时季节尚未识别（进服早于 BOSS 栏 / 记分板下发），等它稍后识别出来时补播一次。
     * 只补一次，避免和常规季节播报重复。
     */
    private boolean pendingSeasonFollowup;
    /** 证据卡宽限期倒计时；识别成功或被播报后归零。 */
    private int seasonDiagnosticGrace;

    /** 进服自启等待计时（-1=未在等待；>0 逐 tick 递减，到 0 后等资源就绪再启动） */
    private int autoStartPending = -1;
    /** 进服自启的资源等待上限（tick，约 60 秒）；超时则放弃本次自启 */
    private int autoStartWaitBudget = 0;
    /** 已消费的 READY 代次：避免重复重建索引 */
    private long consumedReadyGeneration = -1L;
    /** 死亡停机已排队：防止关闭模块前的相邻 tick 重复提示。 */
    private boolean deathStopPending = false;
    /** 启动自检失败关闭模块时，避免再播一条普通“已停止”覆盖失败原因。 */
    private boolean startupStopPending = false;
    /** 农田整片都是未选作物而停机：同样避免关闭时的普通“已停止”盖掉失败原因。 */
    private boolean cropMismatchStopPending = false;

    /**
     * 是否抑制框架的统一开关播报「已开启」。
     *
     * <p>旧项目的 {@code chatFeedback} 开关：启动自检已经用统一状态源播报完整结论时，
     * 屏蔽基类紧随其后的重复「已开启」。每次启用时重置，只对本帧这次启用生效。</p>
     */
    private boolean suppressEnableAnnounce = false;

    /** 是否已在本次会话内跑过启动自检：旧项目只在世界内激活，主菜单不跑自检 */
    private boolean startupSelfCheckDone = false;

    /** 范围预览开关（.stardew 预览范围）：不启动模块也能看农田边界与洒水器覆盖范围 */
    private boolean rangePreview = false;

    /** 附近洒水器自动预览的扫描半径（格）：够看整片田，又不必逐帧扫世界 */
    private static final int NEARBY_PREVIEW_RADIUS = 32;
    /** 附近洒水器自动预览的重扫间隔（毫秒）：站着不动时的刷新节奏 */
    private static final long NEARBY_PREVIEW_INTERVAL_MS = 1000L;
    /** 附近扫描的缓存结论：渲染层每帧都会问，绝不能每帧扫世界 */
    private List<StardewPointActions.NearbySprinkler> nearbyPreview = List.of();
    private long nearbyPreviewAt;
    private BlockPos nearbyPreviewOrigin;

    // ── 六类选择器（内存镜像在 StardewSettings，按 ServerKey 落盘走 StardewSelectionStore） ──
    private final StardewSelectionBinding selections;

    // ── 拆分出的专职类：各自持有本模块引用，公开 API 仍由本类转发 ──
    private final StardewStartupCheck startupCheck;
    private final StardewSeasonBinding seasonBinding;
    private final StardewStatusCard statusCard;

    // ── 渲染：每一类渲染对象各自独立（显示 / 颜色 / 渲染模式），禁止再用一个总 RenderMode 控制全部 ──

    public StardewFarmModule(IdentityService identityService) {
        super(MODULE_ID, MODULE_NAME, "stardew",
            "全自动种植、浇水、收割星露谷作物，按服务器资源包动态识别。点击按钮查看说明。");
        this.identityService = identityService;
        this.index = new StardewResourceIndex(identityService);
        this.inventory = new StardewInventoryService(identityService);
        // 状态播报直接交给项目统一排版器，避免 notify() 再套一层前缀。
        this.statusReporter = new StardewStatusReporter(CommandMessageFormatter::sendRaw);
        this.coordinator = new StardewCoordinator(scanner, adapter, StardewSeasonService.instance());
        this.profileAssembler = new StardewProfileAssembler(index, statusReporter);
        this.query = new StardewQuerySupport(index, profileAssembler, pointManager, memory, settings);
        this.pointActions = new StardewPointActions(pointManager, index, profileAssembler, coordinator, inventory, settings);
        this.renderState = new StardewRenderState(settings, pointManager, index, coordinator, regionManager,
            this::nearbySprinklerPreview);
        this.regionSelector = new StardewRegionSelector(this);
        // 洒水器新绑定：分区种植开启时必须落在某个种植区域内（存量点位不回溯，照常维护）
        this.pointActions.setSprinklerRegionGate(this::sprinklerRegionGate);

        // 六类选择器（数据源：资源包扫描 + ID 配置双源合并）
        selections = new StardewSelectionBinding(index, settings);
        startupCheck = new StardewStartupCheck(this);
        seasonBinding = new StardewSeasonBinding(this);
        statusCard = new StardewStatusCard(this);

        statusReporter.setChatEnabled(() -> true);
        startupCheck.applyStatusHints();
        coordinator.setStatusReporter(statusReporter);

        // 真实作物状态统一入口：把本模块「按 ServerKey + 指纹载入并复核过签名」的规则表
        // 注入唯一判定组件。` .id 方块 `、农田扫描、收割决策、`.stardew 标记成熟` 全部走它，
        // 因此不可能出现「一侧成熟、一侧生长中」，也不会串用其它服务器的规则。
        CropRuntimeStateResolver.install(query.runtimeSource());

        // 执行层读取「该作物自己的」后勤阈值：按 ServerKey + 资源指纹 + cropKey 取，
        // 取不到才是默认值。因此番茄的种子数永远不会顶掉玉米的补货缺口。
        // 「简化后勤」开启时统一返回默认四值，保证「界面上没显示的旧值」绝不会偷偷生效。
        coordinator.setLogisticsResolver(this::effectiveLogistics);
        coordinator.setCropPlanResolver(cropKey -> StardewCropPlanStore.get(
            ResourceExtractionService.serverKey(), ResourceExtractionService.fingerprint(), cropKey));
        coordinator.setHarvestRuleResolver(cropKey -> profileAssembler.activeHarvestRules().get(cropKey));
        coordinator.setHarvestLearningListener(profileAssembler::saveLearnedHarvestRule);
        coordinator.setCropMismatchHandler(this::stopForCropMismatch);
        coordinator.setRegionMismatchHandler(this::stopForRegionMismatch);

        // 资源生命周期订阅：资源真正 READY 后才重建索引 / 加载档案 / 绑定选择；
        // 切服或断线立即失效，绝不在资源未就绪时使用上一服务器的数据。
        ResourceExtractionService.addReadyListener(this::onResourceReady);
        ResourceExtractionService.addInvalidateListener(this::onResourceInvalidated);

        // ID 配置变更只做「身份/证据补充」：资源未就绪时绝不重建，
        // 因此 `.id 方块` 不再是星露谷的初始化开关。
        identityService.addListener(this::onIdsChanged);
    }

    // ── 设置持久化 ──

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
    }

    /** 立即写回设置（界面改动即时生效，与旧项目 Meteor 设置自动保存一致） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    // ── 界面 ──

    /**
     * 模块独立页面：只保留「服务器资源」面板这一张卡（旧项目设置页同样只留它，
     * 其余设置全部搬进控制台）。
     */
    @Override
    public ModulePage page() {
        return new StardewResourcePanelPage(this);
    }

    @Override
    public String icon() {
        return ICON;
    }

    /** 模块自带指令 {@code .stardew}；由运行时统一注册（与 AutoChestModule 同一注册点） */
    @Override
    public List<ClientCommand> commands() {
        return List.of(new StardewCommand());
    }

    // ── 供指令与界面读取 ──

    public StardewSettings settings() {
        return settings;
    }

    public StardewResourceIndex index() {
        return index;
    }

    public StardewPointManager pointManager() {
        return pointManager;
    }

    public StardewCoordinator coordinator() {
        return coordinator;
    }

    public StardewStatusReporter statusReporter() {
        return statusReporter;
    }

    // ── 拆分出的专职类读入口与状态读写（专职类需要，语义与原字段读写一致） ──

    public StardewSelectionBinding selections() {
        return selections;
    }

    public StardewSeasonBinding seasonBinding() {
        return seasonBinding;
    }

    public StardewStatusCard statusCard() {
        return statusCard;
    }

    public StardewProfileAssembler profileAssembler() {
        return profileAssembler;
    }

    public StardewInventoryService inventory() {
        return inventory;
    }

    public StardewPointActions pointActions() {
        return pointActions;
    }

    public void setStartupSelfCheckDone(boolean value) {
        startupSelfCheckDone = value;
    }

    public long consumedReadyGeneration() {
        return consumedReadyGeneration;
    }

    public void setStartupStopPending(boolean value) {
        startupStopPending = value;
    }

    public void setSuppressEnableAnnounce(boolean value) {
        suppressEnableAnnounce = value;
    }

    public boolean pendingSeasonFollowup() {
        return pendingSeasonFollowup;
    }

    public void setPendingSeasonFollowup(boolean value) {
        pendingSeasonFollowup = value;
    }

    public int seasonDiagnosticGrace() {
        return seasonDiagnosticGrace;
    }

    public void setSeasonDiagnosticGrace(int value) {
        seasonDiagnosticGrace = value;
    }

    /**
     * 六类选择的数据入口（界面壳使用）。
     *
     * <p>与旧项目的 {@code StardewTargetSetting} 一一对应：界面只负责把选中项加入 / 移除，
     * 写盘一律交回本对象，保证「选择的内存镜像」与「按 ServerKey 落盘」只有一份实现。</p>
     */
    public StardewSelectionBinding.Selection selection(StardewSelectorCategory category) {
        return selections.selection(category);
    }

    /** ID 配置变更：仅在资源已就绪时补充身份证据并清理失效选择（不写盘，避免误清） */
    private void onIdsChanged() {
        if (!ResourceExtractionService.isReady()) return;
        index.rebuild();
        pruneSelectors();
    }

    /**
     * 资源就绪：重建当前服务器的资源索引 → 绑定该服务器的选择集合 → 载入档案与点位。
     *
     * <p>这是索引唯一的「正常初始化入口」。触发者是 {@code ResourceExtractionService} 在
     * ResourceManager 资源重载真正完成后发出的 READY 信号，不再依赖进服事件、
     * 定时重扫或 {@code .id} 变更。</p>
     */
    public void onResourceReady() {
        String serverKey = ResourceExtractionService.serverKey();
        if (serverKey == null || !GameProbe.isMultiplayer() || !ResourceExtractionService.isReady()) return;

        // READY 代次变化代表资源上下文可能已换包；旧任务快照绝不能写进新指纹隔离域。
        coordinator.reset();
        consumedReadyGeneration = ResourceExtractionService.readyGeneration();
        index.rebuild();

        // 选择集合按服务器隔离：切服后这里会把 A 服的选择换成 B 服自己的
        selections.bindServer(serverKey);

        pruneSelectors();
        profileAssembler.reload(serverKey);
        pointManager.load(serverKey);
        regionManager.load(serverKey);
        pointActions.normalizeStoredFarmBoundaries(serverKey);

        if (isEnabled()) configureCoordinator(serverKey, StardewContext.dimension());
    }

    /**
     * 资源会话失效（切服 / 断线）：立即停掉当前任务并清空上一服务器的一切运行时数据。
     *
     * <p>磁盘上的资源包 ZIP 缓存与服务器档案保留；被清掉的只是「运行时 ResourceIndex /
     * 选择器视图 / 协调器状态」。此时选择器的持久化值不动，等下一个服务器 READY 时
     * 由 {@code bindServer} 换成它自己的。</p>
     */
    private void onResourceInvalidated() {
        index.clear();
        profileAssembler.reset();
        consumedReadyGeneration = -1L;
        coordinator.reset();
        pointManager.invalidate();
        // 区域数据同样按服务器隔离：切服 / 断线后清空内存视图，磁盘文件保留
        regionManager.invalidate();
        boolean wasSelecting = regionSelector.isActive();
        regionSelector.cancel(true);
        // 后勤参数缓存按 ServerKey + 指纹分组：切服 / 资源换包后必须丢弃内存缓存，
        // 磁盘文件保留（回到原服务器原资源时参数照旧）
        StardewLogisticsStore.invalidate();
        StardewCropPlanStore.invalidate();
        statusReporter.reset();
        // 选区模式是被动取消的：必须说明原因，否则玩家只会看到「框突然没了」
        if (wasSelecting) {
            mc.execute(() -> statusReporter.critical("REGION_RESOURCE_LOST", "已退出选区模式",
                "服务器资源已失效（切服 / 断线）：半成品已丢弃，请重新检测资源后再圈地"));
        }
        // 范围预览同样依赖资源（认不出洒水器类型就画不出东西）：一起收起来，别留一个永远空白的层
        if (rangePreview) {
            rangePreview = false;
            syncRangePreview();
            mc.execute(() -> statusReporter.critical("PREVIEW_RESOURCE_LOST", "已关闭范围预览",
                "服务器资源已失效（切服 / 断线）：请重新检测资源后再开启"));
        }
        if (isEnabled()) ModuleManager.setEnabled(MODULE_ID, false);
    }

    /** 清理六类选择器里已不在当前索引中的失效键，并写回当前服务器档案 */
    private void pruneSelectors() {
        selections.pruneAndPersist();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  生命周期
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    @Override
    protected void onEnable() {
        // 旧 Meteor：激活时才订阅事件。本项目由模块自行订阅，事件类型与旧 @EventHandler 一一对应。
        ClientEventBus.subscribe(EVENT_OWNER, ClientEventType.TICK, event -> onTick());
        ClientEventBus.subscribe(EVENT_OWNER, ClientEventType.JOIN_SERVER, event -> onGameJoined());
        ClientEventBus.subscribe(EVENT_OWNER, ClientEventType.DISCONNECT, event -> onGameLeft());
        ClientEventBus.subscribe(EVENT_OWNER, ClientEventType.SCREEN_OPEN, this::onOpenScreen);

        // 旧 onRender3D / onRender2D 的世界渲染层：启用时注册、关闭时注销
        WorldOverlay.register(MODULE_ID, renderState::render);
        syncRangePreview();

        deathStopPending = false;
        startupStopPending = false;
        suppressEnableAnnounce = false;
        // 旧 Meteor 在主菜单不激活模块（runInMainMenu=false），自检只发生在世界内；本框架恢复
        // 「上次开启」时会在主菜单直接调用 onEnable，因此这里用同一口径跳过，等进服事件再跑，
        // 避免主菜单凭空播报一次「当前环境不是多人服务器」。
        if (mc.player != null) startupCheck.runStartupCheck();
    }

    /** 框架统一播报前的钩子：本模块已自己播报启动结论时抑制重复的「已开启」 */
    @Override
    protected boolean suppressEnableAnnounce() {
        return suppressEnableAnnounce;
    }

    /**
     * 当前真正生效的后勤阈值（唯一入口，执行层与「是否需要种子箱 / 成品箱」都走它）。
     *
     * <p>「简化后勤」开启时直接返回 {@link StardewLogisticsStore.CropLogistics#DEFAULT}：
     * 界面上那四个输入框没显示，就不允许旧存档里的自定义值继续生效，
     * 否则会出现「界面上写 2/8/8/0、实际按旧值跑」的鬼故事。</p>
     */
    public StardewLogisticsStore.CropLogistics effectiveLogistics(String cropKey) {
        StardewLogisticsStore.CropLogistics stored = StardewLogisticsStore.get(
            ResourceExtractionService.serverKey(), ResourceExtractionService.fingerprint(), cropKey);
        return settings.logisticsSimple
            ? StardewLogisticsStore.CropLogistics.DEFAULT
            : stored;
    }

    /**
     * 当前业务是否真的需要种子箱。
     *
     * <p>判据是「任一已选作物的独立阈值启用了补货」，而不是某个全局开关——阈值按作物各自保存，
     * 只要有一种作物需要补货，就必须有种子箱；全部阈值都为 0（不补货）时才不要求。</p>
     */
    public boolean anyCropNeedsRestock() {
        for (String cropKey : selections.crop().selectedCropKeys()) {
            if (effectiveLogistics(cropKey).restockTrigger() > 0) return true;
        }
        return false;
    }

    /** 某作物是否配置了可执行的自动补货目标。 */
    public boolean canAutoRestock(String cropKey) {
        StardewLogisticsStore.CropLogistics logistics = effectiveLogistics(cropKey);
        return logistics.restockTrigger() > 0 && logistics.restockTarget() > 0;
    }

    /**
     * 当前业务是否真的需要成品箱。
     *
     * <p>与种子箱对称：只要有一种已选作物启用了卸货（卸货触发 &gt; 0），就必须有成品箱；
     * 全部作物都关闭卸货时才不要求，避免「不需要卸货却因缺箱子无法启动」。</p>
     */
    public boolean anyCropNeedsUnload() {
        for (String cropKey : selections.crop().selectedCropKeys()) {
            if (effectiveLogistics(cropKey).unloadTrigger() > 0) return true;
        }
        return false;
    }

    @Override
    protected void onDisable() {
        ClientEventBus.unsubscribeAll(EVENT_OWNER);
        WorldOverlay.unregister(MODULE_ID);
        syncRangePreview();
        // 选区模式属于运行中的会话状态：停机即丢弃半成品，避免下次开启时残留上一次的角
        regionSelector.cancel(true);

        boolean forcedStop = deathStopPending || startupStopPending || cropMismatchStopPending;
        coordinator.reset();
        deathStopPending = false;
        startupStopPending = false;
        cropMismatchStopPending = false;
        pendingSeasonFollowup = false;
        seasonDiagnosticGrace = 0;
        if (!forcedStop) statusReporter.state("STOPPED", "已停止", "自动任务已释放");
    }

    /**
     * 玩家死亡属于不可恢复的当前任务失效：先同步撤销寻路、容器与目标，再延后一帧关闭模块。
     *
     * <p>不能只等待复活后重新规划，否则协调器会保留死亡前的农田目标并再次接管 Baritone。</p>
     */
    private void stopAfterPlayerDeath() {
        if (deathStopPending) return;
        deathStopPending = true;
        autoStartPending = -1;
        autoStartWaitBudget = 0;
        coordinator.reset();
        statusReporter.critical("PLAYER_DEAD", "已强制停止", "玩家死亡，农场任务已中止");
        mc.execute(() -> { if (isEnabled()) ModuleManager.setEnabledSilently(MODULE_ID, false); });
    }

    /**
     * 农田整片都不是已选作物：报错并直接停机（等同玩家自己关掉模块）。
     *
     * <p>旧项目对「作物不在已选列表」的格子是一律静默跳过，于是整片田都种着别的作物时，
     * 模块既不收也不浇、直接回中心站着，玩家只会以为模块卡死。这里报一条红色错误并像
     * 「玩家死亡」那样强制关闭模块：玩家收获掉、或把那几种作物选进种植列表后重新打开即可。</p>
     *
     * <p>停机走 {@code mc.execute} 延后一帧（与死亡停机同口径），先同步撤销寻路与目标，
     * 避免本 tick 剩余的协调器步骤继续接管 Baritone。</p>
     */
    public void stopForCropMismatch(List<String> crops) {
        if (cropMismatchStopPending) return;
        cropMismatchStopPending = true;
        autoStartPending = -1;
        autoStartWaitBudget = 0;
        coordinator.reset();
        statusReporter.critical("CROP_MISMATCH", "农田作物与已选不符", mismatchDetail(crops));
        mc.execute(() -> { if (isEnabled()) ModuleManager.setEnabledSilently(MODULE_ID, false); });
    }

    /** 冲突详情：最多列三种作物，更多时补「等 N 种」，并给出两条出路。 */
    private static String mismatchDetail(List<String> crops) {
        if (crops == null || crops.isEmpty()) return "农田里的作物不在已选列表里 ▸ 请先收获，或把它们选进种植列表再启动";
        String names = String.join("、", crops.subList(0, Math.min(3, crops.size())));
        if (crops.size() > 3) names += " 等 " + crops.size() + " 种";
        return "田里种的是「" + names + "」，没有一种是已选作物 ▸ 请先收获，或把它们选进种植列表再启动";
    }

    /**
     * 种植区域里种了别的作物：同样报错并强制关闭模块。
     *
     * <p>分区模式的口径是「这块地由区域决定种什么」，所以区内出现异种不能将就：既不收也不浇，
     * 让玩家先处理掉。与旧口径的唯一差别是证据更具体——直接给出区域号、绑定作物、实际作物与坐标。</p>
     */
    public void stopForRegionMismatch(List<String> details) {
        if (cropMismatchStopPending) return;
        cropMismatchStopPending = true;
        autoStartPending = -1;
        autoStartWaitBudget = 0;
        coordinator.reset();
        statusReporter.critical("REGION_MISMATCH", "种植区域内作物与绑定不符", regionMismatchDetail(details));
        mc.execute(() -> { if (isEnabled()) ModuleManager.setEnabledSilently(MODULE_ID, false); });
    }

    /** 区域冲突详情：最多列三处，更多时补「等 N 处」，并给出唯一出路。 */
    private static String regionMismatchDetail(List<String> details) {
        if (details == null || details.isEmpty()) return "种植区域里的作物和区域绑定的作物不一致 ▸ 请先清理，或把区域删掉重划";
        return String.join("；", details) + " ▸ 请先清理，或把区域删掉重划";
    }

    private void onGameJoined() {
        // 单人世界不排自启：这里不做「先排 60 tick 再被 onActivate 拒绝」的无意义往返
        if (StardewContext.isSingleplayer()) {
            autoStartPending = -1;
            autoStartWaitBudget = 0;
            return;
        }
        // 主菜单里启用时跳过的那次启动自检，在这里补跑（旧项目在世界内激活才自检）
        if (isEnabled() && !startupSelfCheckDone) startupCheck.runStartupCheck();
        // 只安排「进服自启」计时；索引 / 档案 / 选择一律等 ResourceExtractionService 的 READY 信号，
        // 不再在进服事件里重建（进服时资源包通常还没加载完，重建只会得到空索引或旧数据）。
        if (!isEnabled()) {
            autoStartPending = settings.autoStart ? 60 : -1;
            autoStartWaitBudget = 1200;
        }
    }

    private void onGameLeft() {
        autoStartPending = -1;
        autoStartWaitBudget = 0;
        startupSelfCheckDone = false;
        regionSelector.cancel(true);
        if (isEnabled()) ModuleManager.setEnabled(MODULE_ID, false);
    }

    private void onTick() {
        if (mc.player == null || mc.level == null) return;

        // 进服自启：先等世界稳定，再等服务器资源 READY，最后才启动
        if (autoStartPending >= 0 && !isEnabled()) {
            if (autoStartPending > 0) {
                autoStartPending--;
                return;
            }
            if (!ResourceExtractionService.isReady()) {
                if (autoStartWaitBudget-- <= 0) {
                    autoStartPending = -1;
                    statusReporter.critical("AUTO_START_TIMEOUT", "无法自动启动", "服务器资源在 60 秒内未就绪");
                }
                return;
            }
            autoStartPending = -1;
            ModuleManager.setEnabled(MODULE_ID, true);
            return;
        }

        if (!isEnabled()) return;

        // 启动自检时季节还没下发 → 识别出来后补播一次
        seasonBinding.checkSeasonFollowup();

        // 死亡后旧目标已失去上下文，必须在任何资源刷新或状态机推进之前硬停机。
        if (mc.player.isDeadOrDying()) {
            stopAfterPlayerDeath();
            return;
        }

        // 补消费：资源在本模块运行期间才就绪（例如进服自启早于资源 READY）时，
        // 由 READY 监听器负责重建；这里只兜底防止监听器与代次不同步。
        if (ResourceExtractionService.isReady()
            && consumedReadyGeneration != ResourceExtractionService.readyGeneration()) {
            onResourceReady();
        }

        String serverKey = StardewContext.serverKey();
        String dimension = StardewContext.dimension();
        statusCard.refreshStatusContext();
        configureCoordinator(serverKey, dimension);
        coordinator.tick();
    }

    private void onOpenScreen(ClientEvent event) {
        String screenClassName = event.payload();
        if (!isEnabled()) return;
        if (mc.player == null) return;
        if (InventoryScreen.class.getName().equals(screenClassName)
            || CreativeModeInventoryScreen.class.getName().equals(screenClassName)) {
            coordinator.interruptSilentContainer();
            return;
        }
        // 后台物流只取消界面渲染，服务端同步的 containerMenu 继续供状态机安全发包。
        if (coordinator.usingSilentContainer() && isContainerScreen(screenClassName)) {
            event.cancel();
        }
    }

    /** 该界面类名是否为原版容器界面（旧 {@code AbstractContainerScreen<?>} 判定的类名等价物） */
    private static boolean isContainerScreen(String screenClassName) {
        if (screenClassName == null || screenClassName.isBlank()) return false;
        Class<?> type = null;
        try {
            type = Class.forName(screenClassName);
        } catch (Throwable ignored) {
            return false;
        }
        return net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.class.isAssignableFrom(type);
    }

    public void configureCoordinator(String serverKey, String dimension) {
        regionManager.load(serverKey);
        coordinator.configure(profileAssembler.profile(), index, identityService, memory, pointManager, inventory,
            selections.crop().selectedCropKeys(), selections.pot().selectedKeys(), selections.can().selectedKeys(),
            selections.fertilizer().selectedKeys(), selections.potion().selectedKeys(), selections.sprinkler().selectedKeys(),
            serverKey, dimension, settings.reach, settings.scanBudget,
            settings.autoWater, settings.switchCan, settings.restoreHand,
            settings.autoFertilize, settings.autoPotion,
            settings.sprinklerMaintenance, settings.sprinklerInterval,
            StardewLogisticsStore.CropLogistics.DEFAULT.restockTrigger(),
            StardewLogisticsStore.CropLogistics.DEFAULT.restockTarget(),
            StardewLogisticsStore.CropLogistics.DEFAULT.unloadTrigger(),
            StardewLogisticsStore.CropLogistics.DEFAULT.unloadKeep(),
            settings.returnCenterDelay, settings.batchActions, settings.regionPlanting, regionManager.inDimension(dimension));
    }

    // ── 点位设置 / 查询：旧模块公开入口，实现落在专职类（GUI 按钮与 .stardew 指令共用同一实现） ──

    public boolean setPointFromCrosshair(StardewPointType type) {
        return pointActions.setPointFromCrosshair(type);
    }

    public boolean addSprinklerFromCrosshair() {
        return pointActions.addSprinklerFromCrosshair(null);
    }

    /** 明确指定类型的添加（{@code .stardew 添加洒水器 <类型>} 走这条） */
    public boolean addSprinklerFromCrosshair(String typeInput) {
        return pointActions.addSprinklerFromCrosshair(typeInput);
    }

    /** 「添加洒水器」的 TAB 候选：当前已选的洒水器类型名 */
    public List<String> sprinklerTypeCompletions() {
        return pointActions.selectedSprinklerTypeNames();
    }

    public boolean removeSprinklerFromCrosshair() {
        return pointActions.removeSprinklerFromCrosshair();
    }

    /** 已绑定的洒水器点位（控制台「洒水器点位」列表用；按当前服务器档读取） */
    public List<StardewPointManager.StardewPoint> sprinklerPoints() {
        pointManager.load(StardewContext.serverKey());
        return pointManager.getAll(StardewPointType.SPRINKLER);
    }

    /** 删除一个洒水器点位（列表逐条删除） */
    public boolean removeSprinklerPoint(StardewPointManager.StardewPoint point) {
        return pointActions.removeSprinklerPoint(point);
    }

    /** 清空全部洒水器点位 */
    public void clearSprinklerPoints() {
        pointActions.clearSprinklerPoints();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  分区种植（实验）：区域数据、选区会话、判定
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 分区种植实验开关是否开启；关闭时区域数据照常留在盘上，只是没有任何地方读它 */
    public boolean regionPlantingOn() {
        return settings.regionPlanting;
    }

    public StardewRegionManager regionManager() {
        return regionManager;
    }

    /** 当前服务器的全部区域（不区分维度，回执用） */
    public List<StardewRegionManager.Region> regions() {
        regionManager.load(StardewContext.serverKey());
        return regionManager.all();
    }

    /** 当前维度内的区域（建区 / 任务判定按维度收口） */
    public List<StardewRegionManager.Region> regionsInDimension() {
        regionManager.load(StardewContext.serverKey());
        return regionManager.inDimension(StardewContext.dimension());
    }

    /** 当前玩家所在的选区是否进行中 */
    public boolean regionSelecting() {
        return regionSelector.isActive();
    }

    /** 选点工具提示（进入选区与「还没点角」的提示都用它，保证口径一致） */
    public String regionToolHint() {
        return "选点工具：" + regionToolDisplayName() + "。左键点第一个角，右键点对角";
    }

    /** 已记下第一个角之后的下一步提示 */
    public String regionSecondCornerHint() {
        return "右键点对角即可成区";
    }

    /** 区域字牌字号：跟随「字牌大小」设置，字牌关了也能看见预览 */
    public int regionLabelSize() {
        return settings.labelSize;
    }

    /**
     * 当前是否可以用准星选点。
     *
     * <p>默认只认空手；设置里指定了选点工具后，空手或手持该工具都算。指定工具的意义是
     * 避免「手滑点了方块」，界面上会明确警告手持它不再挖方块。</p>
     */
    public boolean regionToolUsable() {
        if (mc.player == null) return false;
        ItemStack held = mc.player.getMainHandItem();
        if (settings.regionToolKey == null) return held.isEmpty();
        if (held.isEmpty()) return true;
        return settings.regionToolKey.equals(ItemIdentifier.coreKeyOf(held));
    }

    /**
     * 一键设定选点工具：把主手物品记为圈地工具。
     *
     * <p>回执必须写清代价——手持它以后不会挖方块；想恢复就点「清除」回到空手。</p>
     */
    public void setRegionToolFromHand() {
        String failure = null;
        ItemStack held = mc.player == null ? ItemStack.EMPTY : mc.player.getMainHandItem();
        if (mc.player == null) {
            failure = "当前不在世界内";
        } else if (held.isEmpty()) {
            failure = "请先把要当工具的物品拿到主手（想只用空手就不用设）";
        } else if (ItemIdentifier.coreKeyOf(held).isEmpty()) {
            failure = "识别不出这个物品的稳定身份，换一个物品再试";
        }
        if (failure != null) {
            CommandMessageFormatter.of(MODULE_NAME, "设置选点工具失败")
                .field("原因", failure)
                .status(CommandMessageFormatter.Level.FAILURE, "未设置")
                .send();
            return;
        }
        settings.regionToolKey = ItemIdentifier.coreKeyOf(held);
        settings.regionToolName = held.getHoverName().getString();
        persistSettings();
        CommandMessageFormatter.of(MODULE_NAME, "已把 " + settings.regionToolName + " 设为选点工具")
            .field("提醒", "以后手持它不会挖方块")
            .status(CommandMessageFormatter.Level.SUCCESS, "可以开始圈地")
            .send();
    }

    /** 清除选点工具，回到「只能空手圈地」 */
    public void clearRegionTool() {
        settings.regionToolKey = null;
        settings.regionToolName = null;
        persistSettings();
        CommandMessageFormatter.of(MODULE_NAME, "选点工具已清除")
            .status(CommandMessageFormatter.Level.SUCCESS, "只能用空手圈地").send();
    }

    /** 选点工具的中文显示名；没设过返回「空手」 */
    public String regionToolDisplayName() {
        if (settings.regionToolKey == null) return "空手";
        return settings.regionToolName == null ? settings.regionToolKey : settings.regionToolName;
    }

    /**
     * 新建种植区域（选区模式两次点角的收口）：裁剪 → 校验 → 落盘 → 回执。
     *
     * <p><b>越出农田只裁不拒：</b>玩家圈得比农田大是常事，直接裁到农田矩形内更顺手；
     * 裁完什么都没有才拒绝。农田起止点未绑定时不裁（还没有可比的范围）。</p>
     *
     * @return {@code null} 表示成功（回执已发出）；否则是给玩家看的失败原因
     */
    public String createRegion(String cropKey, String cropName, BlockPos a, BlockPos b) {
        String serverKey = StardewContext.serverKey();
        if (serverKey == null) return "当前不在可用的服务器会话里";
        String dimension = StardewContext.dimension();
        String clipNote = null;
        BlockPos first = a;
        BlockPos second = b;
        BlockPos[] clipped = clipToFarm(a, b);
        if (clipped != null) {
            first = clipped[0];
            second = clipped[1];
            clipNote = "已按农田边界裁剪";
        }
        StardewRegionManager.AddResult result = regionManager.add(serverKey, cropKey, cropName, dimension, first, second);
        if (!result.ok()) return result.failure();
        StardewRegionManager.Region region = result.region();
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "已设置种植区域")
            .field("区域", "区域 " + region.index() + " · " + region.cropName())
            .field("范围", region.rangeText())
            .field("格子数", region.cellCount() + " 格")
            .field("洒水器", regionSprinklerInfo(region));
        if (clipNote != null) formatter.field("提示", clipNote);
        formatter.status(CommandMessageFormatter.Level.SUCCESS, "已写入").send();
        return null;
    }

    /**
     * 把两个角裁到农田矩形内（只比 XZ）。
     *
     * @return 发生裁剪时返回裁剪后的两角；没裁返回 {@code null}（含无法判定：起止点未绑定 / 其它维度）
     */
    private BlockPos[] clipToFarm(BlockPos a, BlockPos b) {
        StardewPointManager.StardewPoint start = pointManager.get(StardewPointType.START);
        StardewPointManager.StardewPoint end = pointManager.get(StardewPointType.END);
        if (start == null || end == null) return null;
        if (!start.inCurrentDimension() || !end.inCurrentDimension()) return null;
        int minX = Math.min(start.x(), end.x());
        int maxX = Math.max(start.x(), end.x());
        int minZ = Math.min(start.z(), end.z());
        int maxZ = Math.max(start.z(), end.z());
        int x1 = Math.max(minX, Math.min(maxX, a.getX()));
        int x2 = Math.max(minX, Math.min(maxX, b.getX()));
        int z1 = Math.max(minZ, Math.min(maxZ, a.getZ()));
        int z2 = Math.max(minZ, Math.min(maxZ, b.getZ()));
        if (x1 == a.getX() && x2 == b.getX() && z1 == a.getZ() && z2 == b.getZ()) return null;
        return new BlockPos[]{
            new BlockPos(x1, a.getY(), z1),
            new BlockPos(x2, b.getY(), z2)};
    }

    /**
     * 一块区域的洒水器覆盖情况（只做信息展示，不拦启动、不进红灯）。
     *
     * <p>口径：只算<b>落在该区域内</b>的洒水器（区域外的洒水器不为这块地服务）；
     * 覆盖率 = 区域内被这些洒水器方形范围盖到的格子占区域总格数的比例（只比 XZ）。</p>
     */
    public String regionSprinklerInfo(StardewRegionManager.Region region) {
        if (region == null) return "洒水器 0 台 · 覆盖 0%";
        java.util.Set<Long> covered = new java.util.HashSet<>();
        int machines = 0;
        for (StardewPointManager.StardewPoint point : pointManager.getAll(StardewPointType.SPRINKLER)) {
            if (!point.inCurrentDimension() || !region.contains(point.pos())) continue;
            machines++;
            int radius = StardewRenderState.radiusOfLevel(sprinklerLevel(point));
            for (int x = point.x() - radius; x <= point.x() + radius; x++) {
                for (int z = point.z() - radius; z <= point.z() + radius; z++) {
                    if (!region.containsXZ(x, z)) continue;
                    covered.add(((long) x << 32) ^ (z & 0xFFFFFFFFL));
                }
            }
        }
        int total = Math.max(1, region.cellCount());
        int percent = (int) Math.round(covered.size() * 100.0 / total);
        return "洒水器 " + machines + " 台 · 覆盖 " + Math.min(100, percent) + "%";
    }

    /** 洒水器等级；查不到按 1（与渲染层保守取值一致） */
    private int sprinklerLevel(StardewPointManager.StardewPoint point) {
        return index.entryByKey(point.identity()) instanceof SprinklerDefinition def ? def.sprinklerIndex() : 1;
    }

    /**
     * 「未分区 N 格」：农田范围内没有被任何区域覆盖的格子数。
     *
     * @return 无法统计（农田起止点未绑定）时返回 -1
     */
    public int unpartitionedCellCount() {
        StardewPointManager.StardewPoint start = pointManager.get(StardewPointType.START);
        StardewPointManager.StardewPoint end = pointManager.get(StardewPointType.END);
        if (start == null || end == null) return -1;
        List<StardewRegionManager.Region> list = regionsInDimension();
        int count = 0;
        for (int x = Math.min(start.x(), end.x()); x <= Math.max(start.x(), end.x()); x++) {
            for (int z = Math.min(start.z(), end.z()); z <= Math.max(start.z(), end.z()); z++) {
                boolean covered = false;
                for (StardewRegionManager.Region region : list) {
                    if (region.containsXZ(x, z)) {
                        covered = true;
                        break;
                    }
                }
                if (!covered) count++;
            }
        }
        return count;
    }

    /** 删除一个区域（该地块随即回到「未分区」，不再被种 / 收 / 浇 / 画） */
    public boolean removeRegion(int seq) {
        boolean removed = regionManager.remove(StardewContext.serverKey(), seq);
        if (removed) CommandMessageFormatter.of(MODULE_NAME, "已删除区域 " + seq)
            .status(CommandMessageFormatter.Level.SUCCESS, "这块地不再被管理").send();
        return removed;
    }

    /** 清空全部区域 */
    public int clearRegions() {
        int count = regionManager.clear(StardewContext.serverKey());
        if (count > 0) CommandMessageFormatter.of(MODULE_NAME, "已清空全部种植区域")
            .field("数量", count + " 个")
            .status(CommandMessageFormatter.Level.SUCCESS, "这些地不再被管理").send();
        return count;
    }

    /**
     * 进入选区模式（{@code .stardew 种植区域 <作物>}）。
     *
     * @return {@code null} 表示已进入；否则是给玩家看的失败原因
     */
    public String startRegionSelection(String cropInput) {
        if (regionSelecting()) {
            return "已经在选区模式里（当前作物 " + regionSelector.cropName()
                + "）：右键点对角即可成区，或先 .stardew 种植区域 取消";
        }
        if (!settings.regionPlanting) {
            return "「分区种植」还没开启：请先在设置里打开这个实验开关";
        }
        String canonical = canonicalCropKey(cropInput);
        if (canonical == null || !isKnownCrop(canonical)) {
            return "无法唯一解析作物「" + cropInput + "」：请用当前服务器资源里存在的作物名（TAB 补全）";
        }
        if (!selections.crop().selectedCropKeys().contains(canonical)) {
            return "「" + cropDisplayName(canonical) + "」还没被勾选：请先在「作物」选择器里勾上它";
        }
        String toolFailure = regionToolReadyFailure();
        if (toolFailure != null) return toolFailure;
        return regionSelector.enter(canonical, cropDisplayName(canonical));
    }

    /** 退出选区模式（{@code .stardew 种植区域 取消}）；返回是否本来就在模式内 */
    public boolean cancelRegionSelection() {
        boolean active = regionSelector.isActive();
        if (active) regionSelector.cancel(false);
        return active;
    }

    /**
     * 资源闸门读数：圈地 / 绑洒水器 这类「要拿资源身份记账」的入口必须先过这里。
     *
     * <p><b>为什么不能只看索引有没有内容：</b>资源分析结果是落盘缓存的，进游戏时会从缓存恢复索引进内存，
     * 于是「本会话根本没检测过资源」也能解析出作物名。这类入口与「标记成熟」同口径：
     * 多人服务器 + 本会话资源 READY + 已确定 ServerKey + 索引非空，四者缺一都拒绝。
     * 索引在切服 / 断线时会被清空，因此这个闸门天然是<b>按服务器</b>的。</p>
     */
    public boolean resourceReady() {
        return query.matureMarkAllowed();
    }

    /** 选区模式是否可接管左右键（选点工具是否就手） */
    public String regionToolReadyFailure() {
        if (regionToolUsable()) return null;
        return settings.regionToolKey == null
            ? "选点要空手：请先把主手换成空手"
            : "选点要用「" + regionToolDisplayName() + "」或空手：请先换到手上";
    }

    /**
     * 洒水器新绑定的分区闸门：分区种植开启时必须落在某个种植区域内。
     *
     * <p><b>为什么不改点位校验：</b>已经绑好的洒水器不回溯（玩家会突然发现一半洒水器失效），
     * 只有「新绑定」按新口径要求落在区域内。</p>
     *
     * @return {@code null} 表示放行；否则是给玩家看的失败原因
     */
    private String sprinklerRegionGate(BlockPos pos) {
        if (!settings.regionPlanting) return null;
        regionManager.load(StardewContext.serverKey());
        StardewRegionManager.Region region = regionManager.at(pos, StardewContext.dimension());
        if (region != null) return null;
        return "分区种植已开启：洒水器要落在某个种植区域内（当前这格不在任何区域内）";
    }

    /** 范围预览是否开启 */
    public boolean rangePreviewOn() {
        return rangePreview;
    }

    /**
     * {@code .stardew 预览范围}：不启动模块也能看农田边界与洒水器覆盖范围（再敲一次关闭）。
     *
     * <p><b>为什么要有：</b>世界渲染层原来只在模块启用时挂载，没开模块就看不到洒水器覆盖范围，
     * 没法照着范围规划分区。预览层只画图形、不产生任何任务，与模块开关完全解耦。</p>
     */
    public void toggleRangePreview() {
        rangePreview = !rangePreview;
        syncRangePreview();
        boolean showing = rangePreview && !isEnabled();
        CommandMessageFormatter.of(MODULE_NAME, rangePreview ? "范围预览已开启" : "范围预览已关闭")
            .status(CommandMessageFormatter.Level.SUCCESS,
                rangePreview
                    ? (showing ? "正在显示农田边界与附近洒水器覆盖范围（32 格内自动显示，无需绑定）"
                               : "模块已启动，本来就在显示")
                    : "已停止显示")
            .send();
    }

    /**
     * 附近洒水器自动预览：不绑定也能看「附近每台洒水器各盖多大」。
     *
     * <p><b>为什么不做准星跟随：</b>实机反馈「动一下就没了、还会闪」——准星只够到 5 格、换格就跳，
     * 看不出布局。改成以玩家为中心扫描，框常亮、走动转动都在。</p>
     *
     * <p><b>取数口径：</b>走与「添加洒水器」同一条识别链（资源语义 → 贴图实体），认不出类型的
     * 不画——凭空画框只会变成噪音。已经绑定的台由青色覆盖框负责，这里跳过，避免同一台画两个框。</p>
     *
     * <p>只有「预览范围」开着时才有值；扫描结果缓存，只在「距上次超过 1 秒」或「玩家移动超过 4 格」
     * 时重扫。只画不落盘、不参与任何决策，也不需要绑定。</p>
     */
    public List<StardewPointActions.NearbySprinkler> nearbySprinklerPreview() {
        if (!rangePreview || mc.player == null || mc.level == null) return List.of();
        BlockPos origin = mc.player.blockPosition();
        long now = System.currentTimeMillis();
        boolean moved = nearbyPreviewOrigin == null || nearbyPreviewOrigin.distSqr(origin) >= 16;
        if (!moved && now - nearbyPreviewAt < NEARBY_PREVIEW_INTERVAL_MS) return nearbyPreview;
        nearbyPreviewOrigin = origin.immutable();
        nearbyPreviewAt = now;
        nearbyPreview = pointActions.scanSprinklers(new AABB(origin).inflate(NEARBY_PREVIEW_RADIUS));
        return nearbyPreview;
    }

    /** 预览层只在「开着预览且模块未启动」时挂载：模块启动时它自己那一层就在画，避免同一份图形画两遍 */
    private void syncRangePreview() {
        if (rangePreview && !isEnabled()) {
            // 模块没启动时也要先把点位与区域读出来，否则算不出范围
            pointManager.load(StardewContext.serverKey());
            regionManager.load(StardewContext.serverKey());
            WorldOverlay.register(PREVIEW_LAYER_ID, renderState::render);
        } else {
            WorldOverlay.unregister(PREVIEW_LAYER_ID);
        }
    }

    public boolean removePoint(StardewPointType type) {
        return pointActions.removePoint(type);
    }

    /** 清空全部点位（GUI 与 {@code .stardew 清空} 共用） */
    public void clearAllPoints() {
        pointActions.clearAllPoints();
    }

    /** 作物 key 的中文显示名（指令播报用；找不到原样返回 key） */
    public String cropDisplayName(String cropKey) {
        return query.cropDisplayName(cropKey);
    }

    /** 该 cropKey 是否属于当前服务器资源索引（指令前置校验用） */
    public boolean isKnownCrop(String cropKey) {
        return query.isKnownCrop(cropKey);
    }

    public String canonicalCropKey(String input) {
        return query.canonicalCropKey(input);
    }

    public List<String> cropCompletions(String prefix, boolean quoted) {
        return query.cropCompletions(prefix, quoted);
    }

    /** 「种植区域 <作物>」的 TAB 候选：只列已勾选的作物（用中文名，圈地时看着最直观） */
    public List<String> selectedCropCompletions() {
        List<String> names = new java.util.ArrayList<>();
        for (String key : selections.crop().selectedCropKeys()) names.add(cropDisplayName(key));
        return names;
    }

    /** 「种植区域 删除 <序号>」的 TAB 候选：当前已有区域的序号 */
    public List<String> regionSequenceCompletions() {
        List<String> sequences = new java.util.ArrayList<>();
        for (StardewRegionManager.Region region : regionsInDimension()) sequences.add(String.valueOf(region.index()));
        return sequences;
    }

    public List<String> stageCompletions(String cropInput, String prefix) {
        return query.stageCompletions(cropInput, prefix);
    }

    /** 给整个农田范围设置种植目标（写入长期记忆） */
    public boolean setRegionPlantIntent(String cropKey) {
        return query.setRegionPlantIntent(cropKey);
    }

    /** 人工标记成熟的前置闸门：环境 / 资源 / 索引 / ServerKey 任一未满足都不得进入解析。 */
    public boolean matureMarkAllowed() {
        return query.matureMarkAllowed();
    }

    /** 人工标记某作物的成熟阶段（全项目唯一写入口）。 */
    public StardewQuerySupport.MatureMarkOutcome markMature(String cropKey, String stageName, boolean force) {
        return query.markMature(cropKey, stageName, force);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  状态读数与说明面板（读数落在专职类，公开入口与说明仍在本类）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 配置页只读消费统一状态源，绝不从 UI 反向修改状态机。 */
    public StardewStatusSnapshot statusSnapshot() {
        return statusCard.statusSnapshot();
    }

    /**
     * 控制台一次渲染所需的只读快照。
     *
     * <p>配额取 {@link StardewCropPlanStore} 换算后的<b>实际盆数</b>，库存取统一背包服务，
     * 季节与任务取统一状态源——控制台因此不可能显示一套、执行另一套。</p>
     */
    public StardewConsoleData consoleData() {
        return statusCard.consoleData();
    }

    // ── 季节人工绑定（聊天指令入口：解析与转发在此，规则写入季节服务数据层） ──

    /** {@code .stardew 季节}：输出当前识别结论；只有识别不出时才附上证据供人工核对。 */
    public void reportSeasonStatus() {
        seasonBinding.reportSeasonStatus();
    }

    /**
     * {@code .stardew 季节 春|夏|秋|冬}：把最近一次季节字段里的第一个图标字符绑定为该季节。
     *
     * <p>只写入当前 {@code ServerKey + fingerprint}，不会成为全局规则，也不会覆盖资源包自动证据：
     * 绑定仅填补自动识别不到的空缺，因此结果播报会如实区分「真的生效」与「被自动证据优先」。</p>
     */
    public void bindSeasonFromProbe(String input) {
        seasonBinding.bindSeasonFromProbe(input);
    }

    /** {@code .stardew 季节 清除}：删除当前服务器的全部人工季节绑定。 */
    public void clearSeasonBinding() {
        seasonBinding.clearSeasonBinding();
    }

    /**
     * 状态行（供 {@code .stardew status}）。
     *
     * <p>与配置页资源面板共用 {@link ResourceExtractionService} 同一状态源，绝不出现「面板一个
     * ServerKey、status 另一个」；主界面 / 单人 / 多人三种环境分别给中文结论，不裸露
     * {@code READY / NOT_CHECKED} 之类内部枚举作为主文案（枚举只作调试信息）。</p>
     */
    public List<String> statusLines() {
        return statusCard.statusLines();
    }

    /**
     * 当前服务器的「文档成熟」条数。
     *
     * <p>只统计「当前服务器资源索引里真实存在、且攻略基线有记录」的作物。服务器未建立
     * Stardew Profile（未检测 / 非星露谷资源）时恒为 0——成熟规则必须按 ServerKey / Profile
     * 隔离，禁止把攻略基线当作所有服务器的全局默认规则。</p>
     */
    public int documentedMatureCount() {
        return statusCard.documentedMatureCount();
    }

    /** 当前服务器档案里真机确认过的成熟规则条数（无档案 = 0） */
    public int verifiedMatureCount() {
        return statusCard.verifiedMatureCount();
    }

    /** 使用说明内容（「服务器资源」面板的「查看使用说明」按钮与 {@link HelpPanelScreen} 共用） */
    public String[] helpContent() {
        return HelpPanelScreen.buildHelpContent(
            new HelpPanelScreen.HelpSection("准备工作",
                "  §8├─ §f打开控制台：输入 §3.stardew 控制台§f，或设置页「服务器资源」里的「打开控制台」",
                "  §8├─ §f点「检测 / 提取当前服务器资源包」，六类选择器才会出现真实资源",
                "  §8├─ §f进服只识别当前服务器，不会自动下载资源（用 yiyiaddon 的玩家不一定玩星露谷）",
                "  §8├─ §f资源就绪后可点「查看资源包」在文件管理器里定位当前服务器的 ZIP 缓存",
                "  §8├─ §f在控制台「种植」页选择目标作物及相关工具（成熟产物图标 + 关联种子）",
                "  §8├─ §f在控制台「点位」页绑定农田范围：准星对准盆或其上作物，点起点 / 终点的「设置」",
                "  §8├─ §f（可选）在同一页绑定种子箱 / 成品箱 / 补水点 / 洒水器",
                "  §8├─ §f在控制台「后勤」页给每种作物定种植数量；默认「简化后勤」已够用，不必理解阈值",
                "  §8└─ §f季节由服务器原始文本与种子 Tooltip 自动判断；识别不出时用「.stardew 季节」人工绑定兜底"
            ),
            new HelpPanelScreen.HelpSection("服务器资源（设置页唯一保留的面板）",
                "  §8> §3检测 / 提取当前服务器资源包 §8— §7查缓存 → 复用或下载 → 解析 → 就绪",
                "  §8> §3打开控制台 §8— §7整屏页面：概览 / 种植 / 运行 / 后勤 / 点位 / 日志（也可用 §3.stardew 控制台§7）",
                "  §8> §3查看资源包 §8— §7用当前 ServerKey 定位真实 ZIP（不同服务器资源互不串用）",
                "  §8> §3查看使用说明 §8— §7打开本窗口（唯一入口，位于资源区）",
                "  §e▸ §f本模组的设置已全部收进控制台（种植配置 / 运行参数 / 后勤参数 / 点位渲染），"
                    + "设置页只保留本面板作为入口，不再重复平铺",
                "  §e▸ §f尚未建立本地缓存时会提示「请先检测 / 提取当前服务器资源」",
                "  §e▸ §f主界面 / 单人世界不可检测，按钮置灰"
            ),
            new HelpPanelScreen.HelpSection("控制台（六个页签）",
                "  §8> §3概览 §8— §7资源包 / 季节 / 当前任务 + 每种作物的配额与背包库存",
                "  §8> §3种植 §8— §7六类选择器：种植作物 / 种植盆 / 肥料 / 魔法药剂 / 水壶 / 洒水器",
                "  §e▸ §f「水壶」选择页顶部会提示：选中即自动联动补水——用尽自动回补水点、一次连发补满，无需额外设置",
                "  §8> §3运行 §8— §7全部运行参数：交互距离、扫描预算、自动浇水、状态提示、批量右击…",
                "  §8> §3后勤 §8— §7「简化后勤」开关 + 每种作物的数量模式、目标数量（可选四个阈值）",
                "  §8> §3点位 §8— §7六张绑定卡（设置 / 删除）+ 清空全部点位 + 各类点位的显示与颜色",
                "  §8> §3日志 §8— §7最近 80 条真正发进聊天的播报（新在最上），可刷新 / 清空",
                "  §e▸ §f只有「概览」每秒自动刷新；其余页带可交互控件，要看最新值按左下角 §3刷新",
                "  §e▸ §f页面上改的就是执行层在用的同一份值，不存在「界面一套、实际另一套」",
                "  §e▸ §f绑定 / 删除点位会关闭控制台（需要准星对准方块），重新打开即可看到新坐标"
            ),
            new HelpPanelScreen.HelpSection("运行参数（控制台「运行」页）",
                "  §8> §3状态提示 §8— §7开关聊天栏任务播报（默认开启）；启动自检、季节结论、报错不受它影响",
                "  §8> §3批量右击 §8— §7同一 tick 最多对几个格子发右键（1 = 关闭，默认 4，上限 8），只做收割 / 浇水 / 播种 / 施肥",
                "  §8> §3交互距离 / 扫描预算 §8— §7前者决定够不够得着，后者决定每 tick 扫多少格，跑得顺不顺主要看这两个",
                "  §8> §3自动浇水 / 自动施肥 / 自动用药剂 / 洒水器维护 §8— §7总开关，关了对应任务就不产生",
                "  §e▸ §f按主题成块排：启动 → 交互距离 / 扫描预算 / 回中心等待 → 浇水 → 施肥 / 药剂 → 洒水器 → 状态提示",
                "  §e▸ §f同主题的数值项与开关项挨着（如「洒水器维护」紧邻「洒水器检查间隔」），不按控件类型分家",
                "  §e▸ §f批量右击默认 4、上限 8；填得越激进越容易被服务器丢包或反作弊注意到（服务端实测同 tick 多包会被丢）",
                "  §e▸ §f清理枯苗是左键破坏，永远单目标；收获学习是空手探测，也永远一格格来，都不进批量",
                "  §8> §3补水节奏 §8— §7读得到容量就按「差多少发多少」一次打满（同一 tick 连发，服务端最多吞掉个别包，验证阶段自动补齐）；读不到容量才退回固定批量慢慢逼近",
                "  §e▸ §f读不到上限时先按「每包 +1」试探并记住单包增量，此后补水同样是一轮的事；喝水靠服务端判定，多发的包只会被忽略"
            ),
            new HelpPanelScreen.HelpSection("点位指令",
                "  §8> §3.stardew 绑定 起点 §8— §7准星对准农田对角起点",
                "  §8> §3.stardew 绑定 终点 §8— §7准星对准农田对角终点",
                "  §8> §3.stardew 绑定 种子箱 §8— §7准星对准种子箱",
                "  §8> §3.stardew 绑定 成品箱 §8— §7准星对准成品箱",
                "  §8> §3.stardew 绑定 补水点 §8— §7准星对准静止水源",
                "  §8> §3.stardew 添加洒水器 [类型] §8— §7准星对准洒水器；认得出类型就自动绑定，认不出时写明类型（TAB 补全）",
                "  §8> §3.stardew 移除洒水器 §8— §7准星对准要移除的洒水器",
                "  §8> §3.stardew 移除 <点位> §8— §7删除单个已绑定点位",
                "  §8> §3.stardew 状态 §8— §7查看当前农场完整状态",
                "  §8> §3.stardew 预览范围 §8— §7不启动模块也能看农田边界与附近洒水器覆盖范围；"
                    + "32 格内自动显示，不用绑定（再敲一次关闭）",
                "  §8> §3.stardew 清空 §8— §7删除当前服务器全部点位",
                "  §e▸ §f分区种植（实验，默认关闭）：§3.stardew 种植区域 <作物>§f 圈一块地并绑定这种作物"
                    + "（空手左键点一个角、右键点对角；作物要已在选择器里勾选，TAB 只补已勾选的）",
                "  §e▸ §f区域的查看与收拾：§3.stardew 种植区域 列表 / 删除 <序号> / 清空 / 取消§f"
                    + "（列表含「未分区 N 格」；删除 = 这块地不再被管；取消 = 退出选区，半成品丢弃）",
                "  §e▸ §f打开后每块地只种它绑定的那种作物，未分区的地不管；关掉开关一切照旧，划好的区域留着",
                "  §e▸ §f圈地工具默认空手；「运行」页可一键把手上物品设为圈地工具，但手持它以后不会挖方块",
                "  §e▸ §f控制台「点位」页也能做同样的事：六张卡片点「设置 / 删除」（需准星对准方块）",
                "  §e▸ §f控制台「点位」页的「§c清空全部点位§f」= §3.stardew 清空§f，点击后需二次确认",
                "  §e▸ §f洒水器卡片的「§e管理§f」打开洒水器点位列表：每格一行「删除」，或只清空洒水器（不动起点 / 终点 / 箱子 / 补水点）"
            ),
            new HelpPanelScreen.HelpSection("后勤参数（控制台「后勤」页 · 每种作物独立）",
                "  §e▸ §f默认勾选「简化后勤」：每种作物只显示数量模式与目标数量，四个阈值固定用默认值",
                "  §e▸ §f取消勾选后才逐作物展开四个阈值；界面上没显示的旧值绝不会偷偷生效",
                "  §8├─ §f每块标题就是作物名，右侧灰字是每行的含义，鼠标悬停有更细的说明",
                "  §8├─ §f种子补货触发 / 种子补货目标 / 成品卸货触发 / 成品卸货保留",
                "  §8├─ §f成品卸货触发 = 允许卸货的最低数量：达到后才有资格卸，实际时机在所有农田任务之后",
                "  §8├─ §f背包空格 ≤ 6 时会提前插队卸货，避免成品掉地；阈值只决定资格，不改变卸货时机",
                "  §8├─ §f每个 cropKey 各存一套：番茄的种子数不会顶掉玉米的补货缺口",
                "  §8├─ §f取消选择 → 参数块隐藏、数据保留；重新选择 → 恢复该作物之前的参数",
                "  §8├─ §f只有真实空盆产生播种需求时才会检查种子与补货；种满后种子为 0 也不会跑箱子",
                "  §8├─ §f空盆先检查季节；允许种植后，干盆先浇湿，再检查种子、补货与播种",
                "  §8├─ §f种子箱为空后按真实事件解锁，不再每约 60 秒反复白跑；无外部证据时 15 分钟保险复查",
                "  §8├─ §f空箱只暂停该种子的补货，收获 / 拾取 / 浇水 / 清枯苗 / 卸货继续运行",
                "  §8├─ §f补货与卸货会先走到完整箱体外围合法站位，再静默开箱；双箱按两格整体计算",
                "  §8├─ §f卸货只搬普通产物与品质变种；同 cropKey 的种子始终保留",
                "  §8└─ §f保株作物种满后不再消耗种子：背包种子超过「种子补货目标」时，空闲时会存回种子箱（静默执行）"
            ),
            new HelpPanelScreen.HelpSection("种子回收（保株作物）",
                "  §e▸ §f不需要开启任何开关：条件满足时自动执行，用于解决「种子越收越多」",
                "  §8├─ §f触发条件：该作物「配额已种满」且「背包种子数 > 该作物的种子补货目标」",
                "  §8├─ §f保留量就是该作物的「种子补货目标」：想多留种子就把它调大，想少留就调小",
                "  §8├─ §f优先级最低：收获 / 浇水 / 播种 / 拾取 / 施肥 / 补水 / 补货 / 卸货 全部优先",
                "  §8├─ §f与补货互斥：有空盆要播种时不会回收；配额满时没有播种需求，不会来回搬种子",
                "  §8├─ §f完成时播报一行「种子回收完成 §8▸ §f种子 ×N · 背包保留 ×M」，同一作物每次只播一次",
                "  §8├─ §f搬运按整叠走，不会一个一个慢慢放；一次最多 16 个，剩下的下一次继续",
                "  §8├─ §f种子箱已满 → 提示一次「种子箱已满 / 已暂停回收该种子」，清出空间后自动恢复",
                "  §8└─ §f只作用于已勾选作物，按 cropKey 各算各的；未绑定种子箱时静默跳过"
            ),
            new HelpPanelScreen.HelpSection("每作物目标数量（控制台「后勤」页）",
                "  §e▸ §f每种已选作物独立设置「数量模式：组数 / 个数」和一个目标数量",
                "  §8├─ §f目标数量 = 配额：种够这么多就停，同时它也是「可以回收多余种子」的前置条件",
                "  §8├─ §f一组按项目既有语义换算为 64 个；不设置“组数 + 零散数量”两套输入",
                "  §8├─ §f多选作物按「种植」页里的选择顺序依次分盆：前一种没种够配额，后一种一盆都轮不到",
                "  §8├─ §f举例：农场 60 盆，番茄「个数 20」+ 玉米「个数 40」→ 番茄 20 盆、玉米 40 盆",
                "  §8├─ §f多作物建议用「个数」直接写盆数；单一作物想占满全场，把「组数」调大到超过总盆数",
                "  §8├─ §f配额总和小于盆数时，多出来的盆空着不动，不会自动补别的作物",
                "  §8└─ §f执行层只读取换算后的实际计划个数，多种作物互不覆盖"
            ),
            new HelpPanelScreen.HelpSection("状态提示与播报",
                "  §e▸ §f「运行」页的「状态提示」默认关闭：不打开就不往聊天栏刷任务状态",
                "  §8├─ §f打开后任务状态只占一行（例：§f返回农田 §8▸ §7正在前往农田中心），不再三行一卡",
                "  §8├─ §f启动自检结论、季节识别与限制、点位失效、报错始终提示，不受本开关影响",
                "  §8├─ §f控制台「日志」页记录最近 80 条真正发进聊天的内容，可刷新 / 清空，用于事后回看",
                "  §8├─ §f只有进入新状态或任务完成才播报，不输出观察、验证、重规划等内部步骤",
                "  §8└─ §f控制台「概览」页顶部状态栏与聊天读的是同一份快照，不打开播报也能看"
            ),
            new HelpPanelScreen.HelpSection("种植目标与成熟规则",
                "  §8> §3.stardew 标记成熟 §8— §7准星对准作物，一键人工确认成熟阶段（无需手抄 ID）",
                "  §8> §3.stardew 标记成熟 强制 §8— §7准星纠错：仅当确认旧规则有误时覆盖冲突阶段（危险）",
                "  §8> §3.stardew 标记成熟 <作物> <阶段> §8— §7高级兜底；<TAB> 只补全本服真实存在的作物与阶段",
                "  §8> §3.stardew 种植 <作物ID> §8— §7给整片农田设置种植目标（<TAB> 补全本服作物）",
                "  §e▸ §f逐作物资源签名匹配 JAR 内置规则；只复用完全匹配作物的文档成熟阶段",
                "  §e▸ §f签名不匹配自动进入学习：每个候选阶段仅空手右键一次，绝不降级左键",
                "  §e▸ §f学习成功按 ServerKey + 资源指纹 + cropKey 保存完整已确认规则",
                "  §e▸ §f收获动作与生命周期分开：RIGHT_CLICK 不等于 REGROW；植株消失为 ONE_SHOT，植株回退才是 REGROW",
                "  §e▸ §f学习必须同时观察到世界变化、种植盆保留及对应掉落/库存增加，否则有限失败并停止重复试探",
                "  §e▸ §f人工确认写入 VERIFIED，只作用于当前服务器 + 当前资源指纹，不影响其它服务器",
                "  §e▸ §f已有可靠成熟规则时会拒绝覆盖冲突阶段（状态 ▶ 未修改任何规则），只有「标记成熟 强制」允许人工纠错",
                "  §e▸ §f中文名重复时必须用唯一 cropKey，绝不替你猜一个"
            ),
            new HelpPanelScreen.HelpSection("真实作物状态（唯一判定）",
                "  §e▸ §f「.id 方块」与星露谷农场读的是同一套判定，不存在两套说法",
                "  §8├─ §f成熟：当前阶段 == 本服务器已确认的成熟阶段",
                "  §8├─ §f生长中：已确认成熟阶段，当前阶段不是它",
                "  §8├─ §f再生中：保株作物收割后回退到的已记录阶段",
                "  §8├─ §f已死亡：资源包身份明确标注 dead；不能标记成熟，会独立清除且不等待掉落",
                "  §8└─ §f未知：没有成熟规则时如实报告，绝不猜、绝不把资源名当状态"
            ),
            new HelpPanelScreen.HelpSection("服务器季节与允许种植季节",
                "  §e▸ §f当前季节读取服务器原始 BossBar / 动作栏 / 标题 / 记分板 / 玩家列表等 Component，不读屏幕像素",
                "  §8├─ §f季节图标按资源包字体定义还原：英文 spring/summer/autumn·fall/winter、中文、拼音 chun/xia/qiu/dong 等命名都能自动识别",
                "  §8├─ §f字体定义同时读取当前生效资源与服务器 ZIP 缓存；同一码位出现矛盾语义时按未知处理，绝不乱猜",
                "  §8├─ §f种子允许季节读取真实 ItemStack Lore 与完整 Tooltip，两边共用同一套 token 规则",
                "  §8├─ §f只有双方都有可靠季节证据且无交集才判禁种；证据不足保持未知，未知绝不阻止播种",
                "  §8├─ §f季节不允许时只跳过该作物 Plant，收获 / 拾取 / 浇水 / 清枯苗 / 卸货继续运行",
                "  §8└─ §f季节变化自动重新规划；证据按 ServerKey + fingerprint 隔离，换服或资源包更新立即重建"
            ),
            new HelpPanelScreen.HelpSection("季节人工绑定（自动识别不出时使用）",
                "  §8> §3.stardew 季节 §8— §7查看当前季节与来源；识别不出时才附原文、候选字符码位与作用域",
                "  §8> §3.stardew 季节 春 §8— §7把当前季节图标绑定为春季（<TAB> 补全 春 / 夏 / 秋 / 冬）",
                "  §8> §3.stardew 季节 清除 §8— §7删除当前服务器的全部人工绑定",
                "  §e▸ §f只在资源包贴图给不出季节语义时才需要；自动识别成功时不要绑定",
                "  §e▸ §f绑定只作用于当前服务器 + 当前资源指纹，换服或资源包更新后自动失效，需重新确认",
                "  §e▸ §f资源包自动证据优先于人工绑定；绑定立即生效并自动重规划，不需要重开模块"
            ),
            new HelpPanelScreen.HelpSection("洒水器人工世界绑定",
                "  §e▸ §f服务器可能用 sugar_cane 等普通载体表示洒水器，资源模型显示未知并不代表实物不存在",
                "  §8├─ §f执行「添加洒水器」就是一次人工确认：先自动识别，认不出时可写明类型（多选也行）",
                "  §8├─ §f绑定保存 ServerKey + fingerprint + 逻辑类型 + 真实 BlockState，换服不会串用",
                "  §8├─ §ffingerprint 或载体状态改变后必须重新确认，旧绑定不会盲目继续 VERIFIED",
                "  §8└─ §f人工绑定只属于星露谷业务，不会把普通方块伪造成通用 .id 资源身份"
            ),
            new HelpPanelScreen.HelpSection("渲染显示（控制台「点位」页 · 对象独立）",
                "  §e▸ §f在控制台「点位」页下半部分：每类对象只占一行，按农田、洒水器、后勤点位排序",
                "  §8├─ §f每行结构：对象名 §8▸ 显示开关 §8▸ 「设置」按钮",
                "  §8├─ §f点「设置」进入该对象自己的配置：显示 / 颜色 / 彩虹 / 渲染模式",
                "  §8├─ §f农田边界、起点、终点的显示彼此独立；坐标、扫描范围与自检仍完整保留",
                "  §8├─ §f保留农田边界、洒水器本体 / 覆盖范围、种子箱、产物箱、补水点、洒水器点位与字牌",
                "  §8├─ §f渲染模式三选一：线框 / 面 / 两者，每类独立；点位字牌只有显示 + 文字颜色",
                "  §8└─ §f洒水器本体与覆盖范围完全独立：关一个绝不影响另一个"
            ),
            new HelpPanelScreen.HelpSection("工作流程",
                "  §a[0] §f观察 §8→ §7分帧扫描农田；作物层会归一到下方真实干/湿盆",
                "  §a[1] §f决策 §8→ §7空盆按季节、浇水、种子、补货、播种的固定顺序处理；全部空闲时回收多余种子",
                "  §a[2] §f执行 §8→ §7单任务独占寻路；容器必须先到外围站位，收获优先空手右键",
                "  §a[3] §f验证 §8→ §7读取服务端同步后的世界 / 盆 / 库存 / 掉落证据",
                "  §a[4] §f重规划 §8→ §7丢弃历史扫描结果，重新 Observe 当前世界状态"
            ),
            new HelpPanelScreen.HelpSection("注意事项",
                "  §c注意：§f依赖 Baritone 寻路，不可用时移动任务会失败重规划",
                "  §c注意：§f成熟判定只来自逐作物签名匹配或当前隔离域真机学习，绝不猜 max(stage)",
                "  §c注意：§f季节检测成功本身不刷聊天；只有确定禁种时提示一次，UNKNOWN 不擅自阻止",
                "  §c注意：§f金色番茄等特殊收割动作未确认时安全跳过并提示人工校准",
                "  §c注意：§f关闭模块立即停止，重新开启重新观察"
            )
        );
    }
}
