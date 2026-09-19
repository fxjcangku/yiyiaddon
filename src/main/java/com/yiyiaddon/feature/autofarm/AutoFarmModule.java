package com.yiyiaddon.feature.autofarm;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autofarm.command.FarmCommand;
import com.yiyiaddon.feature.autofarm.config.AutoFarmSettings;
import com.yiyiaddon.feature.autofarm.controller.FarmController;
import com.yiyiaddon.feature.autofarm.controller.FarmDecision;
import com.yiyiaddon.feature.autofarm.controller.FarmObserver;
import com.yiyiaddon.feature.autofarm.controller.FarmSelfCheck;
import com.yiyiaddon.feature.autofarm.controller.FarmVerifier;
import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.feature.autofarm.model.FarmSite;
import com.yiyiaddon.feature.autofarm.model.FarmState;
import com.yiyiaddon.feature.autofarm.model.HarvestMode;
import com.yiyiaddon.feature.autofarm.model.PlantMode;
import com.yiyiaddon.feature.autofarm.model.SiteType;
import com.yiyiaddon.feature.autofarm.render.FarmRenderer;
import com.yiyiaddon.feature.autofarm.region.FarmSiteSelector;
import com.yiyiaddon.feature.autofarm.resource.FarmResourceManager;
import com.yiyiaddon.feature.autofarm.scan.FarmScanner;
import com.yiyiaddon.feature.autofarm.task.FarmTask;
import com.yiyiaddon.feature.autofarm.task.HarvestTask;
import com.yiyiaddon.feature.autofarm.task.PlantTask;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.service.container.ContainerService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 自动农场模块入口：熟一颗收一颗，自动补种拾取，四箱物流自动化。
 *
 * <p><b>用户交互资产（逐字）</b>：模块名 {@code 自动农场}、描述
 * {@code 熟一颗收一颗，自动补种拾取，单作物箱/种子补货箱/多作物箱与杂物箱物流自动化。点击按钮查看说明。}
 * （旧 {@code AutoFarmMatrix:133-134}）、启动报告全文（旧 {@code reportStartupInfo :348-412}）、
 * 8 个状态名与物流进度播报（旧 {@code broadcastState :466-476}）、收割模式切换提示
 * （旧 {@code onHarvestModeChanged :428-434}）、自检缺项文案（{@code FarmSelfCheck} 逐字）。</p>
 *
 * <p><b>结构照旧项目</b>：状态只写不读，真实行为由 {@link FarmController} 的当前任务对象决定；
 * 本类只做事件订阅、运行配置同步与锚点管理（旧 {@code onTick :441-464} 顺序原样）。
 * 六条锚点沿用旧项目隐藏设置的键名（{@code _anchor_start} 等）直接持久化，
 * 不再落隐藏设置项（差异 D-13-02）。</p>
 */
public final class AutoFarmModule extends Module {

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀与世界渲染层标识 */
    public static final String MODULE_ID = "autofarm";

    /** 播报前缀使用的模块名：旧 {@code NongChangCommand.MODULE_NAME} 原文 */
    public static final String MESSAGE_MODULE = "自动农场";

    /** 图标字形（Material Symbols：agriculture，与模块中心既有字形同一验证链路） */
    private static final String ICON = "\uE25B";

    /** 状态播报流水上限：控制台「日志」页只看得到最近这么多条 */
    private static final int LOG_CAPACITY = 80;

    // ── 服务实例（旧 :71-78 同一份组装） ──
    private final FarmScanner scanner = new FarmScanner();
    private final ContainerService broker = new ContainerService();
    private final FarmResourceManager resources = new FarmResourceManager();
    private final FarmObserver observer = new FarmObserver();
    private final FarmVerifier verifier = new FarmVerifier();
    private final FarmSelfCheck selfChecker = new FarmSelfCheck();
    private final FarmDecision decision;
    private final FarmController controller;

    /** 全部设置项的数据载体（文案与默认值来自旧项目） */
    private final AutoFarmSettings settings = new AutoFarmSettings();

    /** 世界渲染层（边界 / 目标 / 字牌三项），启用注册、关闭注销 */
    private final FarmRenderer renderer = new FarmRenderer(this);

    /** 六锚点原始串：键 = 旧隐藏设置键名，值 = {@code x,y,z,维度} 或空串（未绑定） */
    private final Map<SiteType, String> rawSites = new HashMap<>();

    /** 物流状态播报去重锁（旧 {@code lastNotifiedState}） */
    private String lastNotifiedState = "";

    /** 启动前记录的「失焦暂停」原值，关闭模块时还原（后台挂机时鼠标失焦不应暂停游戏） */
    private Boolean prevPauseOnLostFocus = null;

    /** 最近真正发进聊天的内容（新 → 旧），只留第一行；控制台「日志」页专用，不参与任何判定。
     *  口径照星露谷参照实现（StardewStatusReporter.chatLog）：容量 80、多行卡片只留标题行、
     *  去掉模块前缀（页面标题已表达）。 */
    private final java.util.Deque<String> chatLog = new ArrayDeque<>(LOG_CAPACITY);

    private final Minecraft mc = Minecraft.getInstance();

    /**
     * 锚点点选模式（控制台「点位」页的「设置」按钮进入）：手持任意物品，左键 / 右键在游戏里点方块
     * 完成绑定，手感与星露谷种植区域同一套（见 {@link FarmSiteSelector}）。
     *
     * <p>构造期就建实例：它只注册 Fabric 事件回调（不碰注册表、不读世界），
     * 事件回调内部按「是否在模式内」决定是否接管。</p>
     */
    private final FarmSiteSelector siteSelector = new FarmSiteSelector(this);

    public AutoFarmModule() {
        super(MODULE_ID, MESSAGE_MODULE, "automation",
            "熟一颗收一颗，自动补种拾取，单作物箱/种子补货箱/多作物箱与杂物箱物流自动化。点击按钮查看说明。");

        decision = new FarmDecision(scanner, resources, observer, verifier, broker,
            settings.poisonUnloadThreshold, settings.bpt, settings.reachDistance);
        controller = new FarmController(scanner, resources, observer, verifier, broker, decision);
        // 结果播报回调（旧 :287 同一口径）：controller 的任务结果与进度文案统一走这里
        controller.setLogger(this::notify);
    }

    @Override
    public String name() {
        return "AutoFarm";
    }

    @Override
    public String icon() {
        return ICON;
    }

    /** 锚点点选模式载体（控制台「点位」页的「设置」按钮用，见 {@link FarmSiteSelector}） */
    public FarmSiteSelector siteSelector() {
        return siteSelector;
    }

    /** 分类内排序：自动化分类第二位（自动重生 → 自动农场 → 自动骨粉 → 自动挖矿 → …） */
    @Override
    public int order() {
        return 20;
    }

    public AutoFarmSettings settings() {
        return settings;
    }

    public FarmController controller() {
        return controller;
    }

    public FarmScanner scanner() {
        return scanner;
    }

    /** 运行态观察器（控制台概览页读背包空格等实况） */
    public FarmObserver observer() {
        return observer;
    }

    /** 最近真正发进聊天的内容（新 → 旧；控制台「日志」页专用） */
    public synchronized List<String> recentLog() {
        return new ArrayList<>(chatLog);
    }

    /** 清空控制台日志（只清历史，不影响状态与去重锁） */
    public synchronized void clearLog() {
        chatLog.clear();
    }

    // ── 设置持久化 ──

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
        // 锚点串沿用旧隐藏设置键名；旧存档里就是这几个键，原样读回
        for (SiteType type : SiteType.values()) {
            JsonElement e = json.get(anchorKey(type));
            rawSites.put(type, e != null && e.isJsonPrimitive() ? e.getAsString() : FarmSite.UNBOUND);
        }
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
        for (SiteType type : SiteType.values()) {
            json.addProperty(anchorKey(type), rawSites.getOrDefault(type, FarmSite.UNBOUND));
        }
    }

    /** 立即写回设置（界面改动即时生效） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    private static String anchorKey(SiteType type) {
        return switch (type) {
            case START -> "_anchor_start";
            case END -> "_anchor_end";
            case SINGLE_STORAGE -> "_anchor_single";
            case MULTI_STORAGE -> "_anchor_multi";
            case SEED_STORAGE -> "_anchor_seed";
            case POISON_STORAGE -> "_anchor_poison";
        };
    }

    // ── 界面与指令 ──

    @Override
    public com.yiyiaddon.ui.page.ModulePage page() {
        return new com.yiyiaddon.feature.autofarm.ui.AutoFarmPage(this);
    }

    @Override
    public List<ClientCommand> commands() {
        return List.of(new FarmCommand());
    }

    // ── 自检 ──

    /**
     * 启用前自检：作物数量、点位、维度、范围一次性列全（旧 {@code onActivate :316} 同一份判据）。
     *
     * <p>未进入世界时不做自检（点位与作物判据不依赖世界，但维度一致性检测依赖；
     * 世界判断放在 {@link #onEnable()}，与自动挖矿同一分工）。</p>
     */
    @Override
    public List<String> selfCheck() {
        if (mc.player == null || mc.level == null) return List.of();
        return selfChecker.check(getEnabledCrops(), getSitesMap());
    }

    // ── 生命周期 ──

    @Override
    protected void onEnable() {
        if (mc.player == null || mc.level == null) {
            error("必须在进入世界后才能启动模块。");
            mc.execute(() -> ModuleManager.setEnabled(MODULE_ID, false));
            return;
        }

        // 清理历史脏数据，确保四个作物选择器只保留本分类方块
        sanitizeCropSelectors();

        // 后台挂机：关闭失焦暂停，否则鼠标失焦后游戏暂停、Baritone 停止寻路
        if (prevPauseOnLostFocus == null) {
            prevPauseOnLostFocus = mc.options.pauseOnLostFocus;
        }
        mc.options.pauseOnLostFocus = false;

        // 配置扫描器与资源管理器
        scanner.setEnabledCrops(getEnabledCrops());
        FarmSite start = site(SiteType.START);
        FarmSite end = site(SiteType.END);
        if (start != null && end != null) {
            scanner.setBounds(start.pos(), end.pos());
        }
        // 一次性全量扫描：立即拿到全部成熟/可补种/待锄地目标，补种「一瞬间补满」，
        // 避免分帧扫描导致空耕地一批一批慢慢出现
        scanner.fullScan();
        resources.configure(getEnabledCrops(), buildUnloadGroups(), buildRestockGroups());

        // 重置状态并同步运行配置
        lastNotifiedState = "";
        controller.configure(getSitesMap(), settings.reachDistance);
        decision.update(settings.poisonUnloadThreshold, settings.bpt, settings.reachDistance);
        decision.updateMode(settings.harvestMode);
        decision.updatePlantMode(settings.plantMode);
        decision.updateTill(settings.autoTill);

        // ESP：注册世界渲染层，关闭时注销
        com.yiyiaddon.ui.render.world.WorldOverlay.register(MODULE_ID, renderer::renderLayer);

        reportStartupInfo();
    }

    @Override
    protected void onDisable() {
        // 点选模式是「半成品」：模块一关就丢弃本次点选（没有写入的点位一个都不落盘）
        FarmSiteSelector.cancelIfActive(true);
        controller.reset();
        com.yiyiaddon.platform.container.ContainerAccess.closeContainer();
        broker.reset();
        com.yiyiaddon.ui.render.world.WorldOverlay.unregister(MODULE_ID);
        lastNotifiedState = "";

        // 还原失焦暂停原值
        if (prevPauseOnLostFocus != null) {
            mc.options.pauseOnLostFocus = prevPauseOnLostFocus;
            prevPauseOnLostFocus = null;
        }
    }

    /** 收割模式切换时的中文提示（旧 {@code :428-434} 逐字） */
    public void onHarvestModeChanged(HarvestMode mode) {
        if (mode == HarvestMode.BATCH) {
            notify("§b已切换为批量收割 §8▸ 一次性暴力收割全部成熟目标");
        } else {
            notify("§a已切换为单个收割 §8▸ 每次只处理一个成熟目标");
        }
    }

    // ── 事件 ──

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK, ClientEventType.DISCONNECT, ClientEventType.SCREEN_OPEN);
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null) return;
        switch (event.type()) {
            case DISCONNECT -> {
                // 旧 onGameLeft :522-525：退出世界即自动关模块
                if (isEnabled()) ModuleManager.setEnabled(MODULE_ID, false);
            }
            case SCREEN_OPEN -> onOpenScreen(event);
            default -> {
            }
        }
    }

    /**
     * 静默容器：仅在物流任务（卸货/补货/杂物处理，exclusive=true）执行期间取消箱子界面显示，
     * 避免自动化开箱时抢鼠标/焦点。玩家手动开箱（模块空闲/观察/收割/补种/拾取阶段）必须放行
     * （旧 {@code onOpenScreen :527-540}，事件载荷只带类名，与自动挖矿同一等价改写）。
     */
    private void onOpenScreen(ClientEvent event) {
        if (mc.player == null || !isEnabled()) return;
        String screenClassName = event.payload();
        // 旧判定 `!(screen instanceof InventoryScreen)`：背包必须放行
        if (InventoryScreen.class.getName().equals(screenClassName)) return;
        if (!isContainerScreen(screenClassName)) return;

        FarmTask task = controller.currentTask();
        if (task != null && task.exclusive()) event.cancel();
    }

    /** 该界面类名是否为原版容器界面（旧 {@code instanceof AbstractContainerScreen<?>} 的类名等价物） */
    private static boolean isContainerScreen(String screenClassName) {
        if (screenClassName == null || screenClassName.isBlank()) return false;
        try {
            return AbstractContainerScreen.class.isAssignableFrom(Class.forName(screenClassName));
        } catch (Throwable ignored) {
            return false;
        }
    }

    // ── 每刻推进（旧 onTick :441-464 顺序原样） ──

    @Override
    public void onTick(Minecraft client) {
        if (client.player == null || client.level == null) return;

        // 防踩踏：农田范围内拦截跳跃
        if (settings.antiTrample && scanner.contains(client.player.blockPosition())) {
            if (client.options.keyJump.isDown()) {
                client.options.keyJump.setDown(false);
            }
        }

        // 同步最新运行配置
        decision.update(settings.poisonUnloadThreshold, settings.bpt, settings.reachDistance);
        decision.updateMode(settings.harvestMode);
        decision.updatePlantMode(settings.plantMode);
        decision.updateTill(settings.autoTill);
        resources.configure(getEnabledCrops(), buildUnloadGroups(), buildRestockGroups());
        controller.configure(getSitesMap(), settings.reachDistance);

        // 推进状态机
        controller.tick();

        // 状态播报（只播物流状态，工作状态高频循环不播）
        broadcastState(controller.state());
    }

    private void broadcastState(FarmState state) {
        // 物流工作状态：进入时播进度（§7正在XXX...），带去重锁
        if (isLogisticsState(state)) {
            if (!state.cn().equals(lastNotifiedState)) {
                lastNotifiedState = state.cn();
                notify("§7正在" + state.cn() + "...");
            }
        } else if (state == FarmState.OBSERVE) {
            lastNotifiedState = "";
        }
    }

    /** 是否为需要播报进度的物流工作状态（收割/补种/拾取高频循环不播防刷屏） */
    private boolean isLogisticsState(FarmState state) {
        return state == FarmState.UNLOAD || state == FarmState.RESTOCK || state == FarmState.POISON_DUMP;
    }

    // ── 播报 ──

    /** 普通信息（旧基类 notify 的等价物），同时落状态播报流水 */
    public void notify(String message) {
        if (message == null || message.isBlank()) return;
        ClientChat.send(MESSAGE_MODULE, message);
        recordLog(message);
    }

    /** 错误：橙色加粗（旧 notifyError 的等价物） */
    public void error(String message) {
        ClientChat.send(MESSAGE_MODULE, "§6§l" + message);
        recordLog(message);
    }

    /** 记一条日志：只留第一行，并去掉统一前缀（口径照星露谷参照实现） */
    private void recordLog(String message) {
        String firstLine = message.split("\n", 2)[0];
        String clean = firstLine.replace(ClientChat.prefix(MESSAGE_MODULE), "").strip();
        synchronized (chatLog) {
            while (chatLog.size() >= LOG_CAPACITY) chatLog.pollFirst();
            chatLog.addFirst(clean);
        }
    }

    // ── 启动报告（旧 reportStartupInfo :348-412 逐字） ──

    /** 启动播报：合并为一条多行消息块，只带一次模块前缀 */
    private void reportStartupInfo() {
        StringBuilder report = new StringBuilder();
        report.append("§a§l✓ 自动农场 · 启动报告");

        Set<CropProfile> enabled = getEnabledCrops();
        StringBuilder crops = new StringBuilder();
        int count = 0;
        for (CropProfile profile : enabled) {
            if (count > 0) crops.append("§f、");
            crops.append(highlightText(profile.displayName()));
            count++;
        }
        report.append("\n§7启用作物　§8▸ ").append(crops).append("§r");

        // 智能识别：单物品作物（种子==收获物）与不补种作物（柱状物/果实）→单作物箱；
        // 双作物判定（需要补种且种子≠收获物）→种子补货箱（种子），多作物箱（成熟掉落物）与种子补货箱同判定
        boolean hasSingle = enabled.stream().anyMatch(p -> !p.needsReplant() || p.plantItem() == p.harvestItem());
        boolean hasDualSeed = enabled.stream().anyMatch(p -> p.needsReplant() && p.plantItem() != p.harvestItem());

        StringBuilder boxes = new StringBuilder();
        if (hasSingle) boxes.append("单作物箱 ");
        if (hasDualSeed) boxes.append("种子补货箱 ");
        if (hasDualSeed) boxes.append("多作物箱 ");
        report.append("\n§7作物箱　　§8▸ ").append(highlightText(boxes.toString().trim())).append("§r");

        // 物品类型状态提示：单物品 / 双物品 / 混合
        String kindDesc;
        if (hasDualSeed && hasSingle) {
            kindDesc = "混合（单物品 + 双物品）";
        } else if (hasDualSeed) {
            kindDesc = "双物品（种子 + 收获物分离）";
        } else {
            kindDesc = "单物品（仅一种产物）";
        }
        report.append("\n§7物品类型　§8▸ ").append(highlightText(kindDesc)).append("§r");

        // 农田范围
        FarmSite start = site(SiteType.START);
        FarmSite end = site(SiteType.END);
        if (start != null && end != null) {
            int rangeX = Math.abs(end.pos().getX() - start.pos().getX()) + 1;
            int rangeZ = Math.abs(end.pos().getZ() - start.pos().getZ()) + 1;
            report.append("\n§7农田范围　§8▸ ").append(highlightText(rangeX + "×" + rangeZ)).append("§r");
        }

        report.append("\n§7自动锄地　§8▸ ").append(settings.autoTill ? "§a开" : "§c关").append("§r");

        // 每种启用作物的独立卸货/补货数量（逐作物配置里逐项可调）
        for (CropProfile profile : enabled) {
            report.append("\n§7").append(highlightText(profile.displayName()))
                .append(" §8▸ §7卸 ").append(highlightText(settings.unloadGroups(profile) + " 组"));
            if (profile.needsReplant()) {
                report.append(" §r· 补 ").append(highlightText(settings.restockGroups(profile) + " 组"));
            }
            report.append("§r");
        }

        String modeText = settings.harvestMode == HarvestMode.BATCH
            ? "批量收割 · 无上限"
            : "单个收割";
        report.append("\n§7收割模式　§8▸ ").append(highlightText(modeText)).append("§r");

        notify(report.toString());
    }

    /** 物品/文本高亮（亮绿色粗体）——旧基类 {@code highlightText :168-170} 逐字，用于物品名、成功值 */
    private static String highlightText(String text) {
        return "§a§l" + (text == null ? "" : text) + "§r§f§l";
    }

    /** 功能/模式高亮（亮青色粗体）——旧基类 {@code highlightFunction :173-175} 逐字，用于模式名、状态名 */
    private static String highlightFunction(String text) {
        return "§b§l" + (text == null ? "" : text) + "§r§f§l";
    }

    // ── 锚点管理（旧 :546-594，供指令与页面调用） ──

    public FarmSite site(SiteType type) {
        return FarmSite.parse(rawSites.getOrDefault(type, FarmSite.UNBOUND));
    }

    public void bindSite(SiteType type, FarmSite site) {
        rawSites.put(type, site.serialize());

        // 农场范围变更时更新扫描器
        if (type == SiteType.START || type == SiteType.END) {
            FarmSite start = site(SiteType.START);
            FarmSite end = site(SiteType.END);
            if (start != null && end != null) {
                scanner.setBounds(start.pos(), end.pos());
            }
        }
        persistSettings();
    }

    public void clearSite(SiteType type) {
        rawSites.put(type, FarmSite.UNBOUND);
        persistSettings();
    }

    public void clearAllSites() {
        for (SiteType type : SiteType.values()) {
            rawSites.put(type, FarmSite.UNBOUND);
        }
        scanner.reset();
        persistSettings();
    }

    // ── 作物选择汇总（旧 :600-674 等价） ──

    /** 从四组作物选择器汇总启用作物集合（按各自分类二次过滤，防御历史脏数据） */
    public Set<CropProfile> getEnabledCrops() {
        Set<CropProfile> enabled = EnumSet.noneOf(CropProfile.class);
        addCrops(enabled, settings.cropsDouble.keySet(), this::isDoubleCrop);
        addCrops(enabled, settings.cropsSingle.keySet(), this::isSingleCrop);
        addCrops(enabled, settings.cropsPillar.keySet(), this::isPillar);
        addCrops(enabled, settings.cropsFruit.keySet(), this::isFruit);
        // 仙人掌花跟随仙人掌自动启用：种了仙人掌就会自然长出仙人掌花
        if (enabled.contains(CropProfile.CACTUS)) {
            enabled.add(CropProfile.CACTUS_FLOWER);
        }
        return enabled;
    }

    /** 把符合分类的方块 ID 转成作物图鉴并加入集合 */
    private void addCrops(Set<CropProfile> enabled, Set<String> blockIds, Predicate<Block> valid) {
        for (String id : blockIds) {
            Block block = blockOf(id);
            if (block == null || !valid.test(block)) continue;
            CropProfile profile = CropProfile.byBlock(block);
            if (profile != null) enabled.add(profile);
        }
    }

    /** 注册表 ID → 方块；解析失败（旧档残留 / 拼写错误）返回 null */
    public static Block blockOf(String id) {
        if (id == null || id.isBlank()) return null;
        Identifier identifier = Identifier.tryParse(id);
        if (identifier == null) return null;
        Block block = BuiltInRegistries.BLOCK.getValue(identifier);
        return block == null ? null : block;
    }

    /** 双作物：普通农作物且种子与产物分离（小麦、甜菜根） */
    public boolean isDoubleCrop(Block block) {
        CropProfile profile = CropProfile.byBlock(block);
        return profile != null && profile.kind() == CropProfile.Kind.CROP
            && profile.plantItem() != profile.harvestItem();
    }

    /** 单作物：普通农作物且产物即种子（胡萝卜、马铃薯、下界疣） */
    public boolean isSingleCrop(Block block) {
        CropProfile profile = CropProfile.byBlock(block);
        return profile != null && profile.kind() == CropProfile.Kind.CROP
            && profile.plantItem() == profile.harvestItem();
    }

    /** 柱状物：竹子、甘蔗、仙人掌 */
    public boolean isPillar(Block block) {
        CropProfile profile = CropProfile.byBlock(block);
        return profile != null && profile.kind() == CropProfile.Kind.PILLAR;
    }

    /** 果实：南瓜、西瓜（仙人掌花是杂物，跟随仙人掌联动，不单独出现在果实选择器） */
    public boolean isFruit(Block block) {
        CropProfile profile = CropProfile.byBlock(block);
        return profile != null && profile.kind() == CropProfile.Kind.FRUIT && !profile.junk();
    }

    /** 清理四个作物选择器里的历史脏数据，确保每个选择器只保留本分类方块 */
    public void sanitizeCropSelectors() {
        sanitize(settings.cropsDouble, this::isDoubleCrop);
        sanitize(settings.cropsSingle, this::isSingleCrop);
        sanitize(settings.cropsPillar, this::isPillar);
        sanitize(settings.cropsFruit, this::isFruit);
        persistSettings();
    }

    /** 移除选择器中不属于指定分类的方块 */
    private void sanitize(Map<String, Boolean> selection, Predicate<Block> valid) {
        selection.keySet().removeIf(id -> {
            Block block = blockOf(id);
            return block == null || !valid.test(block);
        });
    }

    /** 汇总每作物独立卸货数量（组），供资源管理器热更新 */
    private Map<CropProfile, Integer> buildUnloadGroups() {
        Map<CropProfile, Integer> map = new HashMap<>();
        for (Map.Entry<String, Integer> e : settings.perCropUnload.entrySet()) {
            try {
                map.put(CropProfile.valueOf(e.getKey()), e.getValue());
            } catch (IllegalArgumentException ignored) {
                // 旧档残留的未知键，跳过
            }
        }
        return map;
    }

    /** 汇总每作物独立补货种子数量（组），供资源管理器热更新 */
    private Map<CropProfile, Integer> buildRestockGroups() {
        Map<CropProfile, Integer> map = new HashMap<>();
        for (Map.Entry<String, Integer> e : settings.perCropRestock.entrySet()) {
            try {
                map.put(CropProfile.valueOf(e.getKey()), e.getValue());
            } catch (IllegalArgumentException ignored) {
                // 旧档残留的未知键，跳过
            }
        }
        return map;
    }

    /** 构建六点位映射 */
    public Map<SiteType, FarmSite> getSitesMap() {
        Map<SiteType, FarmSite> map = new HashMap<>();
        for (SiteType type : SiteType.values()) {
            FarmSite site = site(type);
            if (site != null) map.put(type, site);
        }
        return map;
    }

    // ── 模块页状态摘要（旧 statusSummary :739-754 逐字，供模块页与控制台概览共用） ──

    /** 一眼看清启用了什么、锄地是否开启、收割模式 */
    public String statusSummary() {
        Set<CropProfile> enabled = getEnabledCrops();
        StringBuilder crops = new StringBuilder();
        int index = 0;
        for (CropProfile profile : enabled) {
            if (index++ > 0) crops.append("§f、");
            crops.append(highlightText(profile.displayName()));
        }
        String till = settings.autoTill ? "§a开" : "§c关";
        String mode = settings.harvestMode == HarvestMode.BATCH
            ? highlightFunction("批量")
            : highlightFunction("单个");
        return "§7作物 §8▸ " + (enabled.isEmpty() ? "§8未选择" : crops.toString())
            + " §8│ §7锄地 §8▸ " + till
            + " §8│ §7收割 §8▸ " + mode;
    }

    /** 维度简名（旧 dimensionName :798-800 的等价物，走本项目共用格式化器） */
    public static String dimensionName(net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> dim) {
        return WorldContextFormatter.dimensionSummary(dim == null ? null : dim.identifier().toString());
    }
}
