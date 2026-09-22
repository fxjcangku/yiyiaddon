package com.yiyiaddon.feature.stardew.service;

import com.yiyiaddon.core.module.ModuleEntries;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.region.StardewRegionManager;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.ModuleScreen;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

/**
 * 星露谷农场启动自检与启动播报。
 *
 * <p>逐字搬运自 {@code StardewFarmModule}（{@code runStartupCheck}、{@code collectStartupProblems}、
 * {@code startupPoint}、{@code notReadyReason}、{@code reportStartup}、{@code applyStatusHints}）；
 * 方法体、注释与文案一字未改，只把模块字段访问改为经模块读取 / 回写。</p>
 */
public final class StardewStartupCheck {

    private final StardewFarmModule module;
    private final Minecraft mc = Minecraft.getInstance();

    /** 世界数据未就绪时的复核间隔（tick）：进服后区块 / 方块实体是分批到的，等一拍再看 */
    private static final int WORLD_PENDING_RETRY_TICKS = 20;

    /** 复核上限：15 秒还没等到，就按真实配置问题处理（如实报出「区块尚未加载」） */
    private static final int WORLD_PENDING_MAX_RETRIES = 15;

    private int worldPendingRetries;
    private int worldPendingCountdown;
    private boolean worldPendingDeferred;

    public StardewStartupCheck(StardewFarmModule module) {
        this.module = module;
    }

    /**
     * 每刻调用：世界数据未就绪时的复核倒计时。
     *
     * @return true 表示本次启动仍在等待世界数据同步，这一拍不得进入运行循环
     */
    public boolean tickWorldPending() {
        if (!worldPendingDeferred) return false;
        if (--worldPendingCountdown > 0) return true;
        // 复核会按结果重新置位（仍不齐 → 继续等；齐了 → 正常启动；超限 → 按真问题拦下）
        worldPendingDeferred = false;
        runStartupCheck();
        return true;
    }

    /** 关闭 / 重新启用时清掉等待状态，避免下一次启动沿用上一次的倒计时与次数 */
    public void resetWorldPending() {
        worldPendingDeferred = false;
        worldPendingCountdown = 0;
        worldPendingRetries = 0;
    }

    /**
     * 启动自检与启动装配：资源档案与点位装载、启动条件判定、协调器参数、启动播报。
     *
     * <p>旧项目在激活时执行；本框架的激活点可能是主菜单，故由 {@code onEnable()} 在世界内时调用，
     * 或由 {@code onGameJoined()} 在进服后补跑；一次会话只跑一次。</p>
     */
    public void runStartupCheck() {
        module.setStartupSelfCheckDone(true);
        if (ResourceExtractionService.isReady() && !StardewContext.isSingleplayer()) {
            if (module.consumedReadyGeneration() != ResourceExtractionService.readyGeneration() || module.index().isEmpty()) module.onResourceReady();
            else {
                module.profileAssembler().reload(ResourceExtractionService.serverKey());
                module.pointManager().load(ResourceExtractionService.serverKey());
                module.regionManager().load(ResourceExtractionService.serverKey());
            }
            // 「分区错位」要现场扫世界，必须先按当前服务器 / 维度把协调器装配好（档案 + 区域快照）。
            // 与下面成功路径那次是同一个入口，重复调用幂等；失败停机时由 onDisable 统一 reset。
            module.configureCoordinator(ResourceExtractionService.serverKey(), StardewContext.dimension());
        }
        List<String> missing = collectStartupProblems();
        if (!missing.isEmpty()) {
            // 识别结果可能来自进服早期的旧扫描（当时资源包尚未生效）：先用「当前已加载资源」
            // 就地重建一次索引再判一次。旧实现直接禁止启动，玩家只能靠 F3+T 触发资源重载
            // 拿到新代次才会重建，因此在没有新代次时表现为「禁止启动后再也启动不了」。
            if (module.profileAssembler().rebuildIndexForRetry()) missing = collectStartupProblems();
        }
        if (!missing.isEmpty()) {
            // 「区块 / 容器数据还没同步」是暂时状态，不是配置错误：直接禁止启动等于把时滞判成玩家
            // 配错（实机事故：什么都没动，重新开关模块就好了）。这里等最多 15 秒复核，期间不启动、
            // 不播报、不拦人；等不到再按真问题处理，如实报出「区块尚未加载」。
            if (allWorldPending(missing) && ++worldPendingRetries <= WORLD_PENDING_MAX_RETRIES) {
                worldPendingDeferred = true;
                worldPendingCountdown = WORLD_PENDING_RETRY_TICKS;
                if (worldPendingRetries == 1) {
                    module.statusReporter().state("STARTUP_WORLD_PENDING", "启动暂缓：等待世界数据同步",
                        "绑定的容器所在区块 / 方块实体还没到客户端，自动复核中，不需要重新开关模块");
                }
                return;
            }
            module.setStartupStopPending(true);
            module.setSuppressEnableAnnounce(true);
            mc.execute(() -> { if (module.isEnabled()) ModuleManager.setEnabledSilently(StardewFarmModule.MODULE_ID, false); });
            module.statusReporter().startupCheckFailed(missing);
            showNotice("星露谷农场 · 启动自检未通过",
                "§7共 §e" + missing.size() + " §7项问题，已禁止启动：", missing);
            return;
        }
        worldPendingDeferred = false;
        worldPendingRetries = 0;
        module.configureCoordinator(ResourceExtractionService.serverKey(), StardewContext.dimension());
        // 自检通过：上一次留下的「错位红框」作废（玩家已经清好了），模块自己那层高亮接管
        module.coordinator().clearStartupMismatch();
        module.syncStartupMismatchOverlay();
        // 统一启动播报已经给出完整自检结论，屏蔽基类紧随其后的重复「已开启」。
        module.setSuppressEnableAnnounce(true);
        reportStartup();
        // 提醒类（不拦启动）：区域绑的作物不在目标作物勾选里 → 那块地会被整块跳过。
        // 聊天与屏幕中间的面板各一份，样式与自检失败同一个窗口，只是标题与结语写「提醒 / 照常启动」。
        List<String> notices = collectStartupNotices();
        if (!notices.isEmpty()) {
            module.statusReporter().startupNotices(notices);
            showNotice("星露谷农场 · 启动提醒",
                "§7共 §e" + notices.size() + " §7项提醒，照常启动：", notices);
        }
    }

    /** 一次收集所有当前功能所需配置，不把 JSON 存在误当成世界目标仍合法。 */
    private List<String> collectStartupProblems() {
        List<String> missing = new ArrayList<>();
        if (!GameProbe.isMultiplayer()) {
            missing.add("当前环境不是多人服务器");
            return missing;
        }
        // 资源档案未就绪时只报这一条：下面每一把判据（物品名、水壶、作物、点位校验）都建立在
        // 资源包解析出来的索引上，包没下就逐条列「未选择 / 未绑定」，玩家只会看到一屏与自己
        // 操作无关的缺项，还得先猜「水壶为什么说缺少」。所以先给一条可执行提示，下载并解析
        // 成功后再开启模块做完整自检。
        if (!module.profileAssembler().resourceProfileReady()) {
            missing.add("当前服务器资源未就绪，请先下载资源包（服务器资源 → 重新检测 / 更新资源）");
            return missing;
        }
        if (module.selections().crop().selectedCropKeys().isEmpty()) missing.add("未选择目标作物");
        if (module.selections().pot().selectedKeys().isEmpty()) missing.add("未选择种植盆");
        for (String cropKey : module.cropsPlantableInDimension()) {
            CropDefinition crop = module.index().cropByKey(cropKey);
            if (crop == null || module.inventory().countSeed(crop) > 0) continue;
            // 背包没种子 ≠ 种不下去：种子本来就可以放在种子箱里，模块启动后会按 RESTOCK 自己走过去取。
            // 因此「这批种子能不能拿到」在开机这一刻无法判定（读箱子必须先走到箱子跟前开箱等菜单同步，
            // 自检是同步一次性判定，做不到）；只要该作物开着补货阈值、且种子箱已绑定并通过校验，
            // 这里就不拦启动，改成启动提醒（见 collectStartupNotices），
            // 运行时箱里真的没有时由 SEED_EMPTY「缺少X种子」接手。
            if (module.effectiveLogistics(cropKey).restockTrigger() > 0 && seedBoxUsable()) continue;
            missing.add("背包缺少" + crop.seedDisplayName());
        }
        // 农田范围来源就是种植区域：当前维度一块都没有时无从下手，直接不启动
        if (module.regionsInDimension().isEmpty()) {
            missing.add("当前维度还没有种植区域（用 .stardew 种植区域 <作物|混种> 圈地）");
        } else {
            // 盆型不匹配排在最前：地里一口已选盆型的盆都没有时，下面那些作物级判据只会给出噪音
            // （每格都会被静默跳过），先把「你选的盆型和地里对不上」说清楚。
            String potMismatch = module.coordinator().startupPotMismatchProblem();
            if (potMismatch != null) missing.add(potMismatch);
            // 分区错位（单一作物区里已经长着别的作物）：开机就说清楚，别让模块先启动、跑完一轮扫描
            // 才停机（实机反馈：三块区域里有一块被换成了别的作物，照样能开机）。
            // 这条与「自动清理错位作物」无关：那条只管开机之后才出现的错位；开机时就存在的错位
            // 一律先拦住，由玩家决定挖掉还是删区域。
            // 口径与运行中一致：只认活着的、身份可辨的作物——枯死株（冬季冻死一片）与盆上杂物
            // 不算错位，它们由运行中的清理链静默处理，不在开机时拦启动。
            String mismatch = module.coordinator().startupRegionMismatchProblem();
            if (mismatch != null) missing.add(mismatch);
        }
        startupPoint(missing, StardewPointType.SEED_BOX, module.anyCropNeedsRestock());
        startupPoint(missing, StardewPointType.OUTPUT_BOX, module.anyCropNeedsUnload());
        // 盆型决定补水物料：普通盆要水壶 + 补水点，下界盆要岩浆箱、末地盆要龙息箱，三者互斥。
        PotGroup potGroup = module.index().potGroupOfSelected(module.selections().pot().selectedKeys());
        boolean materialPot = potGroup.refillItem() != null;
        if (potGroup.dimensionRestricted() && !potGroup.allowsDimension(WorldIdentity.dimension())) {
            missing.add(potGroup.displayName() + "只能种在"
                + (potGroup == PotGroup.NETHER ? "下界" : "末地")
                + "，当前维度是" + WorldIdentity.dimensionDisplayName(WorldIdentity.dimension()));
        }
        // 「自动浇灌」是三种盆型共用的总开关：普通盆浇水壶 / 补水点，下界盆浇岩浆、末地盆浇龙息。
        // 开着才需要料源；关掉它就没有任何补给任务，这些配置一律不必绑。
        if (module.settings().autoWater || module.settings().sprinklerMaintenance) {
            // 洒水器维护始终要水壶；纯补水时，下界 / 末地盆不用水壶，就不该拦「未选择水壶」
            if (!materialPot || module.settings().sprinklerMaintenance) {
                if (module.selections().can().selectedKeys().isEmpty()) missing.add("未选择水壶");
                if (!module.selections().can().selectedKeys().isEmpty() && module.pointActions().preferredAvailableCan() == null) missing.add("背包缺少已选水壶");
                // 补水运行中水壶迟早会耗尽，补水点属于启动必需配置，不能等空壶后才发现未绑定。
                startupPoint(missing, StardewPointType.WATER_SOURCE, true);
            }
            // 下界 / 末地盆的料从专属箱里取；只有开着「自动浇灌」时才需要这条来源
            if (module.settings().autoWater && materialPot) {
                StardewPointType box = StardewPointType.materialBoxFor(potGroup);
                startupPoint(missing, box, true);
            }
        }
        if (module.settings().autoFertilize && module.selections().fertilizer().selectedKeys().isEmpty()) missing.add("已开启自动施肥，但未选择肥料");
        if (module.settings().autoPotion && module.selections().potion().selectedKeys().isEmpty()) missing.add("已开启自动用药剂，但未选择药剂");
        if (module.settings().sprinklerMaintenance) {
            if (module.selections().sprinkler().selectedKeys().isEmpty()) missing.add("已开启洒水器维护，但未选择洒水器");
            // 只校验<b>本维度</b>的洒水器：点位按维度分档，主世界标过的那几台在下界既不该拦启动、
            // 也不该报「属于其它维度」（实机反馈：到了下界被要求把主世界的洒水器点位删掉重标）。
            List<StardewPointManager.StardewPoint> sprinklers =
                module.pointManager().getInCurrentDimension(StardewPointType.SPRINKLER);
            if (sprinklers.isEmpty()) {
                int elsewhere = module.pointManager().getAll(StardewPointType.SPRINKLER).size();
                missing.add(elsewhere > 0
                    ? "本维度洒水器点位未绑定（其它维度已绑 " + elsewhere + " 台，切回那个维度即可用）"
                    : "洒水器点位未绑定");
            }
            for (StardewPointManager.StardewPoint point : sprinklers) {
                String failure = module.pointManager().validationFailure(StardewPointType.SPRINKLER, point, module.index());
                if (failure != null) missing.add("洒水器 " + point.x() + ", " + point.y() + ", " + point.z() + "：" + failure);
            }
        }
        return missing;
    }

    /**
     * 全部问题是否都属于「世界数据还没同步」（区块未加载 / 容器方块实体未到达）。
     *
     * <p>只要掺进一条真配置问题（未选作物、未绑点位、区域为空…）就不算「暂时」，立刻按原逻辑拦下，
     * 不让玩家白等 15 秒。点位类问题带类型前缀（如 {@code 成品箱：…}），因此按后缀判定。</p>
     */
    private static boolean allWorldPending(List<String> problems) {
        for (String problem : problems) {
            if (problem == null) continue;
            if (!problem.endsWith(StardewPointManager.PENDING_CHUNK)
                && !problem.endsWith(StardewPointManager.PENDING_CONTAINER)) return false;
        }
        return true;
    }

    /** 非必需点位若已配置且该功能在运行，也检查其维度和真实方块。 */
    private void startupPoint(List<String> missing, StardewPointType type, boolean required) {
        var point = module.pointManager().get(type);
        if (point == null) { if (required) missing.add(type.title() + "未绑定"); return; }
        String failure = module.pointManager().validationFailure(type, point, module.index());
        if (failure != null) missing.add(type.title() + "：" + failure);
    }

    /**
     * 种子箱此刻能不能当作种子来源：已绑定、且通过点位校验（方块还在、是容器、维度对得上）。
     *
     * <p>只判「来源可用」，不去读箱内容——内容要在运行时走到箱子跟前开箱才知道。
     * 判定的用途是决定「背包没种子」是拦启动还是只提醒。</p>
     */
    private boolean seedBoxUsable() {
        StardewPointManager.StardewPoint box = module.pointManager().get(StardewPointType.SEED_BOX);
        return box != null
            && module.pointManager().validationFailure(StardewPointType.SEED_BOX, box, module.index()) == null;
    }

    /**
     * 启动提醒（不拦启动）：① 绑定的作物已经不在「目标作物」勾选里的单一作物区；
     * ② 背包没种子但种子箱可用（模块启动后会自己去箱里取）。
     *
     * <p><b>为什么要提醒：</b>这类地块会被整块跳过（不种也不收），玩家看到的是「模块不管这块地」，
     * 却没有任何解释——实机反馈就是「区域里换了一种作物，模块照常开机，什么都不说」。</p>
     *
     * <p><b>为什么不拦：</b>「取消勾选 = 暂停种它、区域保留，重新勾选自动恢复」是既有设计，
     * 不是故障；所以照常开机，只把出路写清楚（勾回来，或在「管理」里删掉该区域）。</p>
     *
     * <p><b>背包没种子为什么不拦：</b>种子放种子箱是正常玩法，模块启动后按补货链路自己走过去取
     * （实机反馈：冬天清完地要补种，种子全在箱里，却被「背包缺少茄子种子」挡在启动之外）。
     * 箱里到底有没有，只能运行时开箱才知道——那一步由运行时的「缺少X种子」播报负责。
     * 只有「没有可用来源」（未开补货 / 种子箱未绑或失效）才由 {@link #collectStartupProblems()} 拦启动。</p>
     */
    private List<String> collectStartupNotices() {
        List<String> notices = new ArrayList<>();
        List<String> selected = module.selections().crop().selectedCropKeys();
        for (StardewRegionManager.Region region : module.regionsInDimension()) {
            // 混种区不绑品种：勾选里的任一作物都能种，不存在「绑的作物没勾选」
            if (region.mixed()) continue;
            String cropKey = region.cropKey();
            if (cropKey == null || selected.contains(cropKey)) continue;
            CropDefinition crop = module.index() == null ? null : module.index().cropByKey(cropKey);
            String name = crop != null ? crop.chineseName()
                : (region.cropName() != null ? region.cropName() : cropKey);
            notices.add("区域 " + region.index() + "（" + name + "）绑的作物不在目标作物里，这块地会被跳过"
                + " ▸ 把它勾回来，或在「管理」里删掉该区域");
        }
        // 背包一个空格都没有：这时「去种子箱取种子」是死路（取回来也放不下），
        // 唯一该让玩家知道的是「先清背包」（实机反馈：背包塞满，弹窗却写着「启动后自动去种子箱取」）。
        if (module.inventory() != null && module.inventory().freeMainSlots() <= 0) {
            notices.add("背包一个空格都没有 ▸ 模块收不了菜、也取不回种子；"
                + "先清空背包腾出空格，腾出后自动继续");
            return notices;
        }
        if (seedBoxUsable()) {
            for (String cropKey : module.cropsPlantableInDimension()) {
                CropDefinition crop = module.index() == null ? null : module.index().cropByKey(cropKey);
                if (crop == null || module.inventory().countSeed(crop) > 0) continue;
                if (module.effectiveLogistics(cropKey).restockTrigger() <= 0) continue;
                notices.add("背包无" + crop.seedDisplayName() + " ▸ 启动后自动去种子箱取；"
                    + "箱里没有时会提示「缺少" + crop.seedDisplayName() + "」并暂停这种作物的播种");
            }
        }
        return notices;
    }

    /**
     * 屏幕中间弹一块只读面板（自检失败用「启动自检未通过」，提醒用「启动提醒」）。
     *
     * <p><b>为什么还要弹面板：</b>聊天里那份容易被后面的状态播报刷走，实机反馈是「点了没反应、
     * 不知道哪里不对」。面板走项目现成的面板窗与按钮（{@link ConfirmPanelScreen#notice}），
     * 排版与配色（提示项红色加粗、带「▸」的拆两行）都在那里做，不在这里也不在各模块各写一份；
     * 点「知道了」即回游戏，聊天记录照旧留档。</p>
     *
     * <p><b>「打开设置」直达本模块设置页</b>：出路都在设置页里（资源、点位、作物勾选），
     * 不用先关面板再自己进模块中心找一遍。</p>
     */
    private void showNotice(String title, String headline, List<String> items) {
        mc.execute(() -> {
            if (mc.gui.screen() instanceof ConfirmPanelScreen) return;
            mc.gui.setScreen(ConfirmPanelScreen.notice(title, headline, items, mc.gui.screen(),
                () -> new ModuleScreen(ModuleEntries.of(module), null)));
        });
    }

    /** 资源未就绪时给玩家的可执行提示：按真实阶段区分，绝不误导成「稍后自动就好」 */
    private String notReadyReason() {
        return switch (ResourceExtractionService.phase()) {
            case NOT_CHECKED, IDLE ->
                "尚未检测当前服务器资源，请先在「服务器资源」区域点击「检测 / 提取当前服务器资源包」。";
            case NO_CONTENT -> "当前服务器未检测到可识别的星露谷资源，未建立资源档案。";
            case FAILED -> "资源准备失败：" + (ResourceExtractionService.failReason() == null
                ? "未知原因" : ResourceExtractionService.failReason()) + "。";
            default -> "服务器资源正在处理中（当前状态：" + ResourceExtractionService.phase().label() + "），请稍候再启动。";
        };
    }

    /**
     * 启动报告：与 {@code .stardew status}、资源面板共用同一排版器与同一数据源。
     *
     * <p>以前这里是手拼 {@code §7标签§8▸ 值}，没有对齐、也没有统一配色，和其余指令长得不一样。
     * 现在整块交给 {@code CommandMessageFormatter}，所有「▶」与值从同一 X 开始。</p>
     */
    private void reportStartup() {
        module.statusCard().refreshStatusContext();
        String season = module.seasonBinding().playerSeasonLabel();
        module.statusReporter().startupCheckPassed(module.statusCard().selectedCropNames(), season);
        // 进服瞬间 BOSS 栏 / 记分板往往还没下发，季节通常在自检之后才识别出来。
        // 所以这里先只登记，给一段宽限期：期间识别成功就只补播「季节已识别」；
        // 宽限期结束仍是「当前季节 / 未知」，才播一次可核对的证据（来源 + 原文 + 码位）。
        // 无论哪种情况，业务层都保持 UNKNOWN 不限制播种，这不是故障。
        if (!StardewFarmModule.NAMED_SEASONS.contains(season)) {
            module.setPendingSeasonFollowup(true);
            module.setSeasonDiagnosticGrace(StardewFarmModule.SEASON_DIAGNOSTIC_GRACE_TICKS);
        }
    }

    /** 「状态提示」开关接到统一播报器：关闭时只静默任务状态播报，结论类消息照常。 */
    public void applyStatusHints() {
        if (module.statusReporter() == null) return;
        module.statusReporter().setStatusHints(() -> module.settings().statusHints);
    }
}
