package com.yiyiaddon.feature.stardew.service;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
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

    public StardewStartupCheck(StardewFarmModule module) {
        this.module = module;
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
                module.pointActions().normalizeStoredFarmBoundaries(ResourceExtractionService.serverKey());
            }
        }
        List<String> missing = collectStartupProblems();
        if (!missing.isEmpty()) {
            // 识别结果可能来自进服早期的旧扫描（当时资源包尚未生效）：先用「当前已加载资源」
            // 就地重建一次索引再判一次。旧实现直接禁止启动，玩家只能靠 F3+T 触发资源重载
            // 拿到新代次才会重建，因此在没有新代次时表现为「禁止启动后再也启动不了」。
            if (module.profileAssembler().rebuildIndexForRetry()) missing = collectStartupProblems();
        }
        if (!missing.isEmpty()) {
            module.setStartupStopPending(true);
            module.setSuppressEnableAnnounce(true);
            mc.execute(() -> { if (module.isEnabled()) ModuleManager.setEnabledSilently(StardewFarmModule.MODULE_ID, false); });
            module.statusReporter().startupCheckFailed(missing);
            return;
        }
        module.configureCoordinator(ResourceExtractionService.serverKey(), StardewContext.dimension());
        // 统一启动播报已经给出完整自检结论，屏蔽基类紧随其后的重复「已开启」。
        module.setSuppressEnableAnnounce(true);
        reportStartup();
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
        for (String cropKey : module.selections().crop().selectedCropKeys()) {
            CropDefinition crop = module.index().cropByKey(cropKey);
            if (crop == null || module.inventory().countSeed(crop) > 0) continue;
            missing.add("背包缺少" + crop.seedDisplayName());
        }
        // 农田范围来源：分区种植开启时由种植区域决定（起止点完全不参与，没绑也不拦启动）；
        // 关闭时与旧口径逐字一致——起止点必绑。
        if (module.regionPlantingOn()) {
            if (module.regionsInDimension().isEmpty()) {
                missing.add("分区种植已开启但当前维度还没有种植区域（用 .stardew 种植区域 <作物> 圈地）");
            }
        } else {
            startupPoint(missing, StardewPointType.START, true);
            startupPoint(missing, StardewPointType.END, true);
        }
        startupPoint(missing, StardewPointType.SEED_BOX, module.anyCropNeedsRestock());
        startupPoint(missing, StardewPointType.OUTPUT_BOX, module.anyCropNeedsUnload());
        if (module.settings().autoWater || module.settings().sprinklerMaintenance) {
            if (module.selections().can().selectedKeys().isEmpty()) missing.add("未选择水壶");
            if (!module.selections().can().selectedKeys().isEmpty() && module.pointActions().preferredAvailableCan() == null) missing.add("背包缺少已选水壶");
            // 自动浇水运行中水壶迟早会耗尽，补水点属于启动必需配置，不能等空壶后才发现未绑定。
            startupPoint(missing, StardewPointType.WATER_SOURCE, true);
        }
        if (module.settings().autoFertilize && module.selections().fertilizer().selectedKeys().isEmpty()) missing.add("已开启自动施肥，但未选择肥料");
        if (module.settings().autoPotion && module.selections().potion().selectedKeys().isEmpty()) missing.add("已开启自动用药剂，但未选择药剂");
        if (module.settings().sprinklerMaintenance) {
            if (module.selections().sprinkler().selectedKeys().isEmpty()) missing.add("已开启洒水器维护，但未选择洒水器");
            if (module.pointManager().getAll(StardewPointType.SPRINKLER).isEmpty()) missing.add("洒水器点位未绑定");
            for (var point : module.pointManager().getAll(StardewPointType.SPRINKLER)) {
                String failure = module.pointManager().validationFailure(StardewPointType.SPRINKLER, point, module.index());
                if (failure != null) missing.add("洒水器 " + point.x() + ", " + point.y() + ", " + point.z() + "：" + failure);
            }
        }
        return missing;
    }

    /** 非必需点位若已配置且该功能在运行，也检查其维度和真实方块。 */
    private void startupPoint(List<String> missing, StardewPointType type, boolean required) {
        var point = module.pointManager().get(type);
        if (point == null) { if (required) missing.add(type.title() + "未绑定"); return; }
        String failure = module.pointManager().validationFailure(type, point, module.index());
        if (failure != null) missing.add(type.title() + "：" + failure);
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
