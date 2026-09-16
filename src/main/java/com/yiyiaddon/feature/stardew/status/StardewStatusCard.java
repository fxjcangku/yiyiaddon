package com.yiyiaddon.feature.stardew.status;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.plan.StardewCropPlanStore;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRule;
import com.yiyiaddon.feature.stardew.profile.StardewServerProfile;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleData;
import com.yiyiaddon.model.resource.ResourcePhase;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;

import java.util.ArrayList;
import java.util.List;

/**
 * 星露谷农场状态行与档案计数（只读取值）。
 *
 * <p>逐字搬运自 {@code StardewFarmModule}（{@code statusLines}、{@code statusSummary}、
 * {@code selectedCropNames}、{@code dimensionLabel}、{@code refreshStatusContext}、
 * {@code statusSnapshot}、{@code consoleData}、资源档案计数与高亮工具方法）；方法体、注释与文案
 * 一字未改，只把模块字段访问改为经模块读取。</p>
 */
public final class StardewStatusCard {

    private final StardewFarmModule module;

    public StardewStatusCard(StardewFarmModule module) {
        this.module = module;
    }

    public void refreshStatusContext() {
        module.statusReporter().context(ResourceExtractionService.isReady() ? "就绪" : ResourceExtractionService.phase().label(),
            selectedCropNames(), module.seasonBinding().playerSeasonLabel());
    }

    /** 配置页只读消费统一状态源，绝不从 UI 反向修改状态机。 */
    public StardewStatusSnapshot statusSnapshot() {
        refreshStatusContext();
        return module.statusReporter().snapshot();
    }

    /**
     * 控制台一次渲染所需的只读快照。
     *
     * <p>配额取 {@link StardewCropPlanStore} 换算后的<b>实际盆数</b>，库存取统一背包服务，
     * 季节与任务取统一状态源——控制台因此不可能显示一套、执行另一套。</p>
     */
    public StardewConsoleData consoleData() {
        refreshStatusContext();
        StardewStatusSnapshot snapshot = module.statusReporter().snapshot();

        String serverKey = ResourceExtractionService.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();

        List<StardewConsoleData.CropRow> crops = new ArrayList<>();
        for (String key : module.selections().crop().selectedCropKeys()) {
            CropDefinition crop = module.index().cropByKey(key);
            if (crop == null) continue;
            StardewCropPlanStore.CropPlan plan = StardewCropPlanStore.get(serverKey, fingerprint, key);
            crops.add(new StardewConsoleData.CropRow(crop.chineseName(), plan.actualAmount(),
                module.inventory().countSeed(crop), module.inventory().countProduce(crop)));
        }

        String task = module.coordinator().currentTask() == null
            ? (module.coordinator().waitingForSeason() ? "等待季节" : "空闲")
            : module.coordinator().currentTask().cn();

        return new StardewConsoleData(
            ResourceExtractionService.phase().label(), ResourceExtractionService.isReady(),
            module.seasonBinding().playerSeasonLabel(), task, snapshot.secondaryLine(), selectedCropNames(),
            crops, module.statusReporter().recentLog());
    }

    private String statusSummary() {
        int crops = module.index().crops().size();
        String water = module.settings().autoWater ? "§a开" : "§c关";
        String res = (ResourceExtractionService.isReady() ? "§a" : "§e") + ResourceExtractionService.phase().label();
        // 只有「没有任何可执行任务 + 仍有待播种作物被季节阻塞」时才显示等待季节；
        // 还有真实任务在跑时一律显示真实任务，绝不把整个模块写成等待季节。
        String task = module.coordinator().waitingForSeason() ? highlightFunction("等待季节")
            : (module.coordinator().currentTask() == null ? "§8空闲" : highlightFunction(module.coordinator().currentTask().cn()));
        return "§7资源包 §8▸ " + res
            + " §8│ §7识别作物 §8▸ " + highlightNumber(crops + " 种")
            + " §8│ §7浇灌 §8▸ " + water
            + " §8│ §7任务 §8▸ " + task;
    }

    // ── 高亮工具方法（逐字取自旧项目 YiyiaddonModule，本项目基类未提供） ──

    /** 功能/模式高亮（亮青色粗体） */
    private static String highlightFunction(String text) {
        return "§b§l" + text + "§r§f§l";
    }

    /** 数值/阈值高亮（黄色粗体） */
    private static String highlightNumber(String text) {
        return "§e§l" + text + "§r§f§l";
    }

    public String selectedCropNames() {
        StringBuilder names = new StringBuilder();
        for (String key : module.selections().crop().selectedCropKeys()) {
            CropDefinition crop = module.index().cropByKey(key);
            if (crop == null) continue;
            if (names.length() > 0) names.append('、');
            names.append(crop.chineseName());
        }
        return names.length() == 0 ? "未选择" : names.toString();
    }

    /**
     * 状态行（供 {@code .stardew status}）。
     *
     * <p>与配置页资源面板共用 {@link ResourceExtractionService} 同一状态源，绝不出现「面板一个
     * ServerKey、status 另一个」；主界面 / 单人 / 多人三种环境分别给中文结论，不裸露
     * {@code READY / NOT_CHECKED} 之类内部枚举作为主文案（枚举只作调试信息）。</p>
     */
    public List<String> statusLines() {
        List<String> lines = new ArrayList<>();

        // ── 服务器资源生命周期（名称 / 地址 / 状态 / 来源 / 缓存 / 指纹 / 档案）──
        // 直接复用同一个「世界上下文」构件，单人 / 多人 / 主界面各给对应字段，
        // 绝不出现「服务器 ▶ 无 / 地址 ▶ singleplayer / 维度 ▶ 未知维度」这类兜底假值。
        String context = CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "").world().render();
        lines.addAll(java.util.Arrays.asList(context.substring(context.indexOf('\n') + 1).split("\n")));
        String reason = ResourceExtractionService.failReason();
        String phaseColor = ResourceExtractionService.isReady() ? "§a"
            : (reason != null || ResourceExtractionService.phase() == ResourcePhase.FAILED
                || ResourceExtractionService.phase() == ResourcePhase.NO_CONTENT ? "§c" : "§e");
        lines.add(CommandMessageFormatter.line("资源状态", phaseColor + ResourceExtractionService.statusLabel()
            + (reason == null ? "" : " §8" + reason)));
        lines.add(CommandMessageFormatter.line("资源来源", "§f" + ResourceExtractionService.resourceSource().label()));
        lines.add(CommandMessageFormatter.line("资源缓存", "§f" + ResourceExtractionService.cacheLabel()));
        lines.add(CommandMessageFormatter.line("资源指纹", (ResourceExtractionService.fingerprint() == null
            ? "§8" + ResourceExtractionService.fingerprintLabel()
            : "§f" + ResourceExtractionService.fingerprintLabel())));
        String profileText = resourceProfileLabel();
        lines.add(CommandMessageFormatter.line("资源档案",
            (resourceHasProfile() ? "§a" + profileText : "§c" + profileText)
            + (resourceHasProfile()
                ? " §8(作物 " + module.index().crops().size() + " / 逻辑工具 " + toolResourceCount() + ")"
                : "")));

        for (StardewPointType type : StardewPointType.values()) {
            StardewPointManager.StardewPoint p = module.pointManager().get(type);
            if (type == StardewPointType.SPRINKLER) {
                lines.add(CommandMessageFormatter.line("洒水器", highlightNumber(module.pointManager().count(type) + " 个")));
            } else if (p != null) {
                lines.add(CommandMessageFormatter.line(type.title(), "§fX:" + p.x() + " Y:" + p.y() + " Z:" + p.z() + " §7" + WorldContextFormatter.dimensionSummary(p.dimension())));
            } else {
                lines.add(CommandMessageFormatter.line(type.title(), "§8未绑定"));
            }
        }
        lines.add(CommandMessageFormatter.line("作物候选", highlightNumber(module.index().crops().size() + " 种")));
        int linked = 0;
        for (CropDefinition crop : module.index().crops()) {
            if (crop.hasProduce()) linked++;
        }
        lines.add(CommandMessageFormatter.line("关联产物", highlightNumber(linked + " 种")));
        lines.add(CommandMessageFormatter.line("目标作物", highlightNumber(module.selections().crop().selectedCropKeys().size() + " 种")));
        // 成熟规则严格按当前服务器档案统计：未建 Profile（未检测 / 非星露谷资源）一律显示 0，
        // 绝不把攻略基线 18 条当作所有服务器的全局默认规则注入。
        lines.add(CommandMessageFormatter.line("文档成熟", highlightNumber(documentedMatureCount() + " 条")));
        lines.add(CommandMessageFormatter.line("确认成熟", highlightNumber(verifiedMatureCount() + " 条")));
        lines.add(CommandMessageFormatter.line("待学习作物", highlightNumber(learningCropCount() + " 种")));
        lines.add(CommandMessageFormatter.line("工具资源",
            highlightNumber(module.index().entriesFor(StardewSelectorCategory.POT).size() + " / "
            + module.index().entriesFor(StardewSelectorCategory.FERTILIZER).size() + " / "
            + module.index().entriesFor(StardewSelectorCategory.POTION).size() + " / "
            + module.index().entriesFor(StardewSelectorCategory.WATERING_CAN).size() + " / "
            + module.index().entriesFor(StardewSelectorCategory.SPRINKLER).size() + " / "
            + module.index().entriesFor(StardewSelectorCategory.SHELTER).size())));
        return lines;
    }

    // ── 资源档案统计（新项目 ResourceExtractionService 无 hasProfile/profileLabel/cropCount/toolCount） ──
    // TODO 待确认：按旧 ServerResourceService 的语义用等价数据源推导：
    //   hasProfile    → ResourceExtractionService.hasContent()（READY 且解析出内容）
    //   profileLabel  → NO_CONTENT 视为旧 NOT_STARDEW「非星露谷资源」
    //   cropCount     → index.crops().size()；toolCount → 五类逻辑工具项合计

    private boolean resourceHasProfile() {
        return ResourceExtractionService.hasContent();
    }

    private String resourceProfileLabel() {
        if (ResourceExtractionService.phase() == ResourcePhase.NO_CONTENT) return "非星露谷资源";
        if (!ResourceExtractionService.isReady()) return "未建立";
        return resourceHasProfile() ? "已建立" : "未建立";
    }

    private int toolResourceCount() {
        return module.index().entriesFor(StardewSelectorCategory.POT).size()
            + module.index().entriesFor(StardewSelectorCategory.FERTILIZER).size()
            + module.index().entriesFor(StardewSelectorCategory.POTION).size()
            + module.index().entriesFor(StardewSelectorCategory.WATERING_CAN).size()
            + module.index().entriesFor(StardewSelectorCategory.SPRINKLER).size()
            + module.index().entriesFor(StardewSelectorCategory.SHELTER).size();
    }

    /**
     * 当前服务器的「文档成熟」条数。
     *
     * <p>只统计「当前服务器资源索引里真实存在、且攻略基线有记录」的作物。服务器未建立
     * Stardew Profile（未检测 / 非星露谷资源）时恒为 0——成熟规则必须按 ServerKey / Profile
     * 隔离，禁止把攻略基线当作所有服务器的全局默认规则。</p>
     */
    public int documentedMatureCount() {
        StardewServerProfile profile = module.profileAssembler().profile();
        if (!resourceHasProfile() || profile == null) return 0;
        int count = 0;
        for (CropDefinition crop : module.index().crops()) {
            if (profile.documentedStages() != null && profile.documentedStages().containsKey(crop.cropKey())) count++;
        }
        return count;
    }

    /** 当前服务器档案里真机确认过的成熟规则条数（无档案 = 0） */
    public int verifiedMatureCount() {
        StardewServerProfile profile = module.profileAssembler().profile();
        return !resourceHasProfile() || profile == null ? 0 : profile.verifiedMatureCount();
    }

    /** 已选作物中尚未形成完整 VERIFIED 收获规则的数量。 */
    private int learningCropCount() {
        int count = 0;
        for (String cropKey : module.selections().crop().selectedCropKeys()) {
            StardewHarvestRule rule = module.profileAssembler().activeHarvestRules().get(cropKey);
            if (rule == null || !rule.completeVerified()) count++;
        }
        return count;
    }

    /** 维度标识 → 中文短名（卡片 / 播报用；未知维度原样返回） */
    private static String dimensionLabel(String dimension) {
        return WorldContextFormatter.dimensionSummary(dimension);
    }
}
