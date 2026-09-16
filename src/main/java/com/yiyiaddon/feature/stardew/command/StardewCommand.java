package com.yiyiaddon.feature.stardew.command;

import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandContext;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.profile.RuleEvidence;
import com.yiyiaddon.feature.stardew.recognition.CropRuntimeStateResolver;
import com.yiyiaddon.feature.stardew.recognition.StardewCropDisplayProbe;
import com.yiyiaddon.feature.stardew.region.StardewRegionManager;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code .stardew} 指令 —— 星露谷农场点位 / 记忆 / 成熟规则管理。
 *
 * <p>所有字面量统一走中文（如 {@code .stardew 绑定 种子箱}），回执播报同样使用中文。</p>
 *
 * <p><b>职责边界：</b>本类只做「参数解析 + 转发」，所有点位校验（真实 {@code BlockHitResult}、
 * 静止水源、洒水器资源身份匹配）与播报都在 {@link StardewFarmModule} 内完成——GUI 按钮与指令
 * 共用同一实现，禁止各写一套文案或各写一套校验。</p>
 *
 * <p><b>{@code 标记成熟} 四种入口，共用同一闸门、同一校验、同一保存逻辑：</b></p>
 * <pre>
 * .stardew 标记成熟                     ← 准星模式：对准作物直接人工确认
 * .stardew 标记成熟 强制                ← 准星纠错模式：只会覆盖与已有规则冲突的阶段
 * .stardew 标记成熟 清除 &lt;作物&gt;      ← 清除纠错：删掉该作物的收获规则（标错阶段的退路）
 * .stardew 标记成熟 &lt;作物&gt; &lt;阶段&gt;   ← 参数模式（高级 / 调试兜底，带 TAB 补全）
 * .stardew 季节                         ← 季节识别结论 + 最近一次真实证据
 * .stardew 季节 春|夏|秋|冬             ← 人工绑定当前季节图标（仅当前服务器 + 资源指纹）
 * .stardew 季节 清除                    ← 删除当前服务器的全部人工季节绑定
 * </pre>
 * <p>三者都先过「服务器资源是否 READY」闸门，再各自解析；最终都调用同一个
 * {@link StardewQuerySupport#markMature(String, String, boolean)}——{@code force} 只有
 * {@code 强制} 分支会传 true。准星模式绝不使用上一次目标、不猜 cropKey、不猜阶段、
 * 不使用附近的作物代替。</p>
 *
 * <p><b>逐字搬运自旧项目</b> {@code stardew/command/StardewCommand.java}（536 行）。框架适配点：
 * 旧 Meteor Brigadier 命令树 → 本项目 {@link ClientCommand} 的位置参数 + {@link #complete}；
 * 旧 {@code Modules.get().get(...)} → {@link ModuleManager#byId(String)}；
 * 旧 {@code MatureMarkOutcome/MatureMarkStatus}（挂在模块上）→
 * {@link StardewQuerySupport.MatureMarkOutcome}/{@link StardewQuerySupport.MatureMarkStatus}；
 * 旧 {@code ServerResourceService.environment()/statusLabel()} →
 * {@link WorldContextFormatter#environment()}/{@link ResourceExtractionService#statusLabel()}；
 * 旧 {@code module::openConsole} → 本项目模块未暴露该入口，直接在指令里打开
 * {@link StardewConsoleScreen}。指令名、子命令名、参数名、补全候选、回执与错误文案全部保持原样。</p>
 */
public final class StardewCommand extends ClientCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/stardew");

    private static final String MODULE_NAME = "星露谷农场";

    /** 参数模式的两个参数名（准星模式无参数，因此不冲突） */
    private static final String ARG_CROP_KEY = "作物ID";
    private static final String ARG_CROP_NAME = "作物名";
    private static final String ARG_STAGE = "阶段";

    private static final Minecraft mc = Minecraft.getInstance();

    // ── 补全候选（旧 Brigadier 命令树的字面量 / SuggestionProvider 逐字等价物）──

    /** 根节点子命令字面量（注册顺序） */
    private static final List<String> ROOT_SUBCOMMANDS = List.of(
        "绑定", "添加洒水器", "移除洒水器", "移除", "种植区域", "标记成熟", "控制台", "季节", "状态", "诊断", "清空", "预览范围");

    /** {@code 绑定} / {@code 移除} 的点位字面量 */
    private static final List<String> POINT_NAMES = List.of("种子箱", "成品箱", "补水点");

    /** {@code 种植区域} 的管理字面量（第二层固定候选，与「选择」和已勾选作物一起列） */
    private static final List<String> REGION_ACTIONS = List.of("选择", "混种", "取消", "列表", "删除", "清空");

    /** {@code 季节} 的字面量（先 {@code 清除}，再短名 / 全名成对） */
    private static final List<String> SEASON_NAMES = List.of(
        "清除", "春", "春季", "夏", "夏季", "秋", "秋季", "冬", "冬季");

    @Override
    public String name() {
        return "stardew";
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public String description() {
        return "星露谷农场点位、季节、记忆与成熟规则管理指令";
    }

    @Override
    public void execute(CommandContext context) {
        if (context.isEmpty()) {
            // 旧树根节点无 executes：等价于只给用法，不执行任何业务
            context.usage(usage());
            return;
        }
        switch (context.arg(0)) {
            case "绑定" -> bindPoint(context);
            case "添加洒水器" -> addSprinkler(context);
            case "移除洒水器" -> removeSprinkler();
            case "移除" -> removePointByName(context);
            case "种植区域" -> region(context);
            case "标记成熟" -> markMature(context);
            case "控制台" -> openConsole();
            case "季节" -> season(context);
            case "状态" -> status();
            case "诊断" -> diagnose();
            case "清空" -> clear();
            case "预览范围" -> toggleRangePreview();
            default -> {
                context.error("未知子命令：" + context.arg(0));
                context.usage(usage());
            }
        }
    }

    /**
     * 补全：位置参数已构成的上下文（不含正在输入的当前词）；候选由框架按前缀过滤。
     *
     * <p>逐字对应旧 Brigadier 命令树各级的字面量与 SuggestionProvider：
     * 根节点列全部子命令；{@code 绑定}/{@code 移除} 列点位；{@code 种植区域} 列管理字面量与作物；
     * {@code 标记成熟} 列 {@code 强制} + 本服作物键与中文名；{@code 季节} 列季节名与 {@code 清除}。</p>
     */
    @Override
    public List<String> complete(CommandContext context) {
        if (context.isEmpty()) return ROOT_SUBCOMMANDS;

        StardewFarmModule module = module();
        return switch (context.arg(0)) {
            case "绑定", "移除" -> context.size() == 1 ? POINT_NAMES : List.of();

            // 标记成熟：强制 / 清除 literal + 参数模式 A（cropKey）+ 参数模式 B（中文名，引号形式）
            case "标记成熟" -> {
                if (context.size() == 1) {
                    List<String> candidates = new ArrayList<>();
                    candidates.add("强制");
                    candidates.add("清除");
                    if (module != null) {
                        candidates.addAll(module.cropCompletions("", false));
                        candidates.addAll(module.cropCompletions("", true));
                    }
                    yield candidates;
                }
                // 清除 <作物>：只列「已经有收获规则」的作物（中文名 + 技术键），不列阶段
                if (context.size() == 2 && "清除".equals(context.arg(1)) && module != null) {
                    yield module.ruledCropCompletions();
                }
                // 阶段补全：只列该作物在当前服务器资源包里真实存在的阶段
                if (context.size() == 2 && !"强制".equals(context.arg(1)) && module != null) {
                    yield module.stageCompletions(context.arg(1), "");
                }
                yield List.of();
            }

            case "季节" -> context.size() == 1 ? SEASON_NAMES : List.of();

            // 种植区域：第二层列「取消 / 列表 / 删除 / 清空」+ 已勾选的作物；删除之后再列区域序号
            case "种植区域" -> {
                if (context.size() == 1) {
                    List<String> candidates = new ArrayList<>(REGION_ACTIONS);
                    if (module != null) candidates.addAll(module.selectedCropCompletions());
                    yield candidates;
                }
                if (context.size() == 2 && "删除".equals(context.arg(1)) && module != null) {
                    yield module.regionSequenceCompletions();
                }
                yield List.of();
            }

            // 添加洒水器：可选的类型参数，只列当前已选的洒水器类型
            case "添加洒水器" -> context.size() == 1 && module != null
                ? module.sprinklerTypeCompletions() : List.of();

            default -> List.of();
        };
    }

    // ── 子命令实现 ──

    /** {@code .stardew 绑定 <点位>}：中文字面量 → 点位类型（旧 {@code addSetBranch}） */
    private void bindPoint(CommandContext context) {
        StardewPointType type = pointTypeOf(context.arg(1));
        if (type == null) {
            context.error("未知点位：" + context.arg(1));
            context.usage(usage());
            return;
        }
        setPoint(type);
    }

    /** {@code .stardew 移除 <点位>}：中文字面量 → 点位类型（旧 {@code 移除} 分支） */
    private void removePointByName(CommandContext context) {
        StardewPointType type = pointTypeOf(context.arg(1));
        if (type == null) {
            context.error("未知点位：" + context.arg(1));
            context.usage(usage());
            return;
        }
        removePoint(type);
    }

    /** {@code .stardew 标记成熟}：准星模式 / 强制纠错 / 清除纠错 / 参数模式四分支 */
    private void markMature(CommandContext context) {
        if (context.size() == 1) {
            markMatureFromCrosshair(false);
            return;
        }
        if (context.size() == 2 && "强制".equals(context.arg(1))) {
            markMatureFromCrosshair(true);
            return;
        }
        // 清除纠错：把某个作物的收获规则整条删掉（标错阶段后唯一不需要重启客户端、也不用改文件的退路）
        if ("清除".equals(context.arg(1))) {
            String cropInput = context.arg(2);
            if (cropInput == null) {
                CommandMessageFormatter.of(MODULE_NAME, "标记成熟 ▶ 清除 ▶ 缺少参数")
                    .field("<作物>", "要清除的作物，如 .stardew 标记成熟 清除 辣椒（也可用 cropKey，TAB 只列已有规则的作物）")
                    .field("作用", "删除后模块会重新空手试探该作物，重新学习生命周期并恢复自动收菜")
                    .status(CommandMessageFormatter.Level.FAILURE, "未改动")
                    .send();
                return;
            }
            clearHarvestRule(cropInput);
            return;
        }
        // 参数模式 A / B：canonical cropKey 或中文显示名 → 同一 canonical 解析口径
        String cropInput = context.arg(1);
        String stageName = context.arg(2);
        if (cropInput == null || stageName == null) {
            context.error("缺少参数：需要 <作物> <阶段>");
            context.usage(usage());
            return;
        }
        markMatureWithArgs(cropInput, stageName, false);
    }

    /**
     * {@code .stardew 标记成熟 清除 <作物>}：删掉该作物的收获规则。
     *
     * <p>与人工标记共用同一个写入口，写盘成功即替换内存规则并重建档案，<b>立刻生效、不需要重启客户端</b>；
     * 「本来就没有规则」如实说明，绝不报成「已删除」。</p>
     */
    private void clearHarvestRule(String cropInput) {
        StardewFarmModule module = module();
        if (module == null) return;
        if (!matureResourceGate(module)) return;

        String cropKey = module.canonicalCropKey(cropInput);
        if (cropKey == null) {
            fail("清除收获规则失败", "无法唯一解析作物「" + cropInput
                + "」：请使用当前服务器资源里存在的 cropKey，或 TAB 补全后再执行");
            return;
        }
        StardewQuerySupport.RuleClearOutcome outcome = module.clearHarvestRule(cropKey);
        if (outcome == null) {
            fail("清除收获规则失败", "当前服务器资源未就绪，或该作物不在资源索引中");
            return;
        }
        CommandMessageFormatter card = CommandMessageFormatter.of(MODULE_NAME,
            outcome.previousStage() == null ? "该作物没有可清除的规则" : "已清除收获规则")
            .highlight("作物", module.cropDisplayName(outcome.cropKey()))
            .key("cropKey", outcome.cropKey());
        if (outcome.previousStage() != null) {
            card.field("删除的成熟阶段", outcome.previousStage());
            card.field("原规则来源", evidenceLabel(outcome.previousEvidence()));
            card.field("作用域", "当前服务器 · 资源指纹 · 作物");
        }
        if (!outcome.saved()) {
            LOGGER.info("[星露谷] 清除收获规则未写盘：{}", outcome.cropKey());
            card.field("原因", "收获规则档案写入失败")
                .status(CommandMessageFormatter.Level.FAILURE, "未改动");
            card.send();
            return;
        }
        if (outcome.previousStage() == null) {
            card.field("说明", "该作物当前没有已保存的收获规则，档案未做任何改动");
            // 「清错对象」的补救：把真正有规则的作物直接列出来，省得玩家一个个试（实机反馈）
            List<String> ruled = module.ruledCropCompletions();
            if (!ruled.isEmpty()) card.field("当前有规则的作物", String.join("、", ruled));
            card.status(CommandMessageFormatter.Level.INFO, "未改动").send();
            return;
        }
        LOGGER.info("[星露谷] 清除收获规则已保存：{}（原成熟阶段 {}）", outcome.cropKey(), outcome.previousStage());
        card.field("后续", "模块会重新对该作物做空手试探，确认生命周期后恢复自动收菜");
        if (outcome.previousEvidence() == RuleEvidence.DOCUMENTED) {
            // 内置攻略规则不在玩家档案里，删掉后仍会被重新推导出来，必须如实说明
            card.field("注意", "该阶段来自内置攻略，下次资源检测后会自动恢复；要长期压掉请对准真实成熟植株用「标记成熟 强制」");
        }
        card.status(CommandMessageFormatter.Level.SUCCESS, "已删除").send();
    }

    /** {@code .stardew 季节}：识别结论 / 人工绑定 / 清除（旧 {@code seasonNode}） */
    private void season(CommandContext context) {
        if (context.size() == 1) {
            seasonStatus();
            return;
        }
        String value = context.arg(1);
        if ("清除".equals(value)) {
            clearSeasonBinding();
            return;
        }
        if (SEASON_NAMES.contains(value)) {
            bindSeason(value);
            return;
        }
        context.error("未知季节：" + value);
        context.usage(usage());
    }

    private void setPoint(StardewPointType type) {
        StardewFarmModule module = module();
        if (module == null) return;
        // 校验 + 落盘 + 播报全部在模块内共用实现（与 GUI「设置」按钮完全同一路径）
        module.setPointFromCrosshair(type);
    }

    /** {@code .stardew 添加洒水器 [类型]}：不给类型时由程序自动识别（见 StardewPointActions） */
    private void addSprinkler(CommandContext context) {
        StardewFarmModule module = module();
        if (module == null) return;
        // 资源闸门最优先：没有当前服务器的资源身份，绑定只会把点位记到「认不出的方块」上
        if (!resourceGate(module, "设置洒水器失败", "未保存")) return;
        module.addSprinklerFromCrosshair(context.size() > 1 ? context.arg(1) : null);
    }

    private void removeSprinkler() {
        StardewFarmModule module = module();
        if (module == null) return;
        module.removeSprinklerFromCrosshair();
    }

    private void removePoint(StardewPointType type) {
        StardewFarmModule module = module();
        if (module == null) return;
        module.removePoint(type);
    }

    /**
     * {@code .stardew 种植区域 <作物|混种>} 进入选区模式。
     *
     * <p>四种管理动作（{@code 取消 / 列表 / 删除 <序号> / 清空}）与「圈一块新地」共用同一个子命令，
     * 与旧项目「一个子命令下挂中文字面量」的风格一致。</p>
     */
    private void region(CommandContext context) {
        StardewFarmModule module = module();
        if (module == null) return;
        String action = context.arg(1);
        // 资源闸门最优先：没资源时「连没带参数」也先说资源问题，管理动作同样一并拦下。
        // 唯一例外是「取消」——它是选区模式唯一的退出方式，任何情况下都必须能敲。
        if (!"取消".equals(action) && !resourceGate(module, "种植区域失败", "未圈地")) return;
        if (action == null) {
            // 光说「缺少参数」等于没说：玩家看不到这个子命令能干什么，这里直接把可选项摊开
            CommandMessageFormatter.of(MODULE_NAME, "种植区域 ▶ 缺少参数")
                .field("<作物>", "圈一块新地并绑定这种作物（如 .stardew 种植区域 番茄）")
                .field("选择", "圈一块新地，两个角点完之后弹窗选作物（勾了好几种时用这个）")
                .field("混种", "圈一块混种地：地里的空盆按后勤缺口挑已勾选作物种"
                    + "（控制台「点位」页「农田」卡里就叫「农场模式」）")
                .field("列表", "看当前已划分的区域")
                .field("删除 <序号>", "删掉其中一块地（不再管它，不挖作物）")
                .field("清空", "删掉全部区域")
                .field("取消", "退出正在圈的地")
                .status(CommandMessageFormatter.Level.FAILURE, "未圈地")
                .send();
            return;
        }
        switch (action) {
            case "取消" -> {
                if (!module.cancelRegionSelection()) fail("取消种植区域", "当前没有正在圈的地");
            }
            case "列表" -> listRegions(module);
            case "删除" -> removeRegion(module, context.arg(2));
            case "清空" -> {
                if (module.clearRegions() == 0) fail("清空种植区域", "当前还没有划分任何区域");
            }
            case "混种" -> {
                String failure = module.startMixedRegionSelection();
                if (failure != null) fail("种植区域", failure);
            }
            case "选择" -> {
                String failure = module.startRegionSelectionPickingCrop();
                if (failure != null) fail("种植区域", failure);
            }
            default -> {
                String failure = module.startRegionSelection(action);
                if (failure != null) fail("种植区域", failure);
            }
        }
    }

    /** {@code .stardew 种植区域 列表}：聊天里逐条列出区域 */
    private void listRegions(StardewFarmModule module) {
        List<StardewRegionManager.Region> list = module.regionsInDimension();
        if (list.isEmpty()) {
            fail("种植区域列表", "当前维度还没有划分任何区域（用 .stardew 种植区域 <作物|混种> 圈一块）");
            return;
        }
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "种植区域 ▶ 共 " + list.size() + " 个");
        for (StardewRegionManager.Region region : list) {
            formatter.field("区域 " + region.index(),
                region.cropName() + " · " + region.rangeText() + " · " + region.cellCount() + " 格 · "
                    + module.regionSprinklerInfo(region));
        }
        formatter.status(CommandMessageFormatter.Level.SUCCESS, "已列出").send();
    }

    /** {@code .stardew 种植区域 删除 <序号>}：删除即「不再管这块地」 */
    private void removeRegion(StardewFarmModule module, String sequenceInput) {
        int sequence = -1;
        try {
            if (sequenceInput != null) sequence = Integer.parseInt(sequenceInput.trim());
        } catch (Exception ignored) {
            sequence = -1;
        }
        if (sequence <= 0) {
            fail("删除种植区域", "请给出区域序号，例如 .stardew 种植区域 删除 1（序号可用 TAB 补全）");
            return;
        }
        if (!module.removeRegion(sequence)) fail("删除种植区域", "没有序号为 " + sequence + " 的区域");
    }

    /**
     * 资源闸门：人工标记成熟的最前置校验，必须先于任何 cropKey / stageKey 解析。
     *
     * <p>资源未就绪时真正的问题不是玩家输入，因此此时既不解析 crop 也不解析 stage，
     * 更不会写盘或创建规则文件。</p>
     *
     * @return true 表示资源就绪，可以继续解析
     */
    private boolean matureResourceGate(StardewFarmModule module) {
        return resourceGate(module, "标记成熟失败", "未保存");
    }

    /**
     * 通用资源闸门：{@code title} 是这次操作的失败标题，{@code status} 是状态栏收尾。
     *
     * <p>给「圈地」「绑洒水器」这类<b>要拿资源身份记账</b>的入口复用；查询类指令不受影响。</p>
     *
     * @return true 表示资源就绪，可以继续
     */
    private boolean resourceGate(StardewFarmModule module, String title, String status) {
        return module.ensureResourceReady(title, status, "该命令");
    }

    /** 「资源未就绪」统一中文提示（文案实现在模块，指令与 GUI 共用一份） */
    private void reportResourceNotReady(String moduleTitle, String status) {
        StardewFarmModule module = module();
        if (module != null) module.ensureResourceReady(moduleTitle, status, "该命令");
    }

    /**
     * 准星模式（普通 / 「强制」纠错）：对准当前自定义作物确认成熟阶段。
     *
     * <p>cropKey 与 stageKey 全部由统一真实状态解析自动取得，玩家不需要抄 ID。
     * 准星未命中 / MISS / 命中空气 / 命中生物 / 缺少阶段信息分别给出明确中文原因，
     * 绝不退回「上一次目标」「猜一个作物」「用附近的作物代替」。</p>
     *
     * <p>作物是展示实体的服务器（CraftEngine 系）命中的是实体而非方块：这条路径会读展示实体手里的
     * 物品模型取身份，与方块路径共用同一份判定，因此「准星点不到方块」不再等于「无法人工校准」。</p>
     *
     * @param force 仅 {@code .stardew 标记成熟 强制} 传入 true
     */
    private void markMatureFromCrosshair(boolean force) {
        StardewFarmModule module = module();
        if (module == null) return;

        LOGGER.info("[星露谷] 标记成熟（准星）执行 force={}", force);

        // ① 资源闸门最优先
        if (!matureResourceGate(module)) return;

        // ② 准星目标校验（普通模式与「强制」模式共用，force 不能绕过任何一条）
        CrosshairTarget target = crosshairTarget();
        if (target == null) {
            LOGGER.info("[星露谷] 标记成熟（准星）中止：准星没有指向有效目标（hitResult={}）",
                mc.hitResult == null ? "null" : mc.hitResult.getType());
            return;
        }

        // ③ 统一真实状态解析取得 cropKey / stageKey（与 .id 方块 同一套判定）
        CropRuntimeStateResolver.RuntimeResult crop = resolveAt(target);
        LOGGER.info("[星露谷] 标记成熟（准星）目标 {} 展示身份 {} → 作物 {} / 阶段 {} / 状态 {}",
            target.pos().toShortString(), target.displayIdentity(), crop.cropKey(), crop.stageName(),
            crop.state().displayName());

        if (crop.state() == CropRuntimeStateResolver.RuntimeState.DEAD) {
            CommandMessageFormatter.of("星露谷农场", "标记成熟失败")
                .world()
                .field("当前真实状态", "已死亡")
                .field("原因", "当前方块为枯死作物，不能标记成熟")
                .status(CommandMessageFormatter.Level.FAILURE, "未保存")
                .send();
            return;
        }
        if (!crop.isCrop()) {
            fail("标记成熟失败", crosshairSubject(target) + "不是已识别的自定义作物");
            return;
        }
        if (!crop.hasStage()) {
            fail("标记成熟失败", "已识别为自定义作物，但缺少阶段信息（无法确定 stageKey）");
            return;
        }

        StardewQuerySupport.MatureMarkOutcome outcome =
            module.markMature(crop.cropKey(), crop.stageName(), force, crop.identity());
        if (outcome.status() != StardewQuerySupport.MatureMarkStatus.SAVED) {
            LOGGER.info("[星露谷] 标记成熟（准星）未保存：{}", outcome.status());
            reportRejected(module, outcome, crop);
            return;
        }
        LOGGER.info("[星露谷] 标记成熟（准星）已保存：{} = {}", outcome.cropKey(), outcome.stageName());
        // 保存后重新解析一次：真实状态必须来自同一套判定，绝不手写「成熟」
        reportSaved(module, outcome, resolveAt(target), force);
    }

    /** 参数模式：解析中文名 / cropKey → 唯一 canonical → 与准星模式共用同一闸门、同一保存、同一播报 */
    private void markMatureWithArgs(String cropInput, String stageName, boolean force) {
        StardewFarmModule module = module();
        if (module == null) return;

        // ① 资源闸门最优先：资源未就绪时不进入 crop / stage 解析
        if (!matureResourceGate(module)) return;

        String cropKey = module.canonicalCropKey(cropInput);
        if (cropKey == null) {
            fail("标记成熟失败", "无法唯一解析作物「" + cropInput
                + "」：请使用当前服务器资源里存在的 cropKey，或 TAB 补全后再执行");
            return;
        }
        StardewQuerySupport.MatureMarkOutcome outcome = module.markMature(cropKey, stageName, force);
        if (outcome.status() != StardewQuerySupport.MatureMarkStatus.SAVED) {
            // 参数模式没有世界方块可观察，不编造「当前真实状态」
            reportRejected(module, outcome, null);
            return;
        }
        reportSaved(module, outcome, null, force);
    }

    /**
     * 准星目标：命中坐标 + 该位置可用的展示实体身份 + 命中实体名（未命中实体为 {@code null}）。
     *
     * @param pos             命中坐标（命中方块 = 该方块坐标；命中实体 = 实体所在格）
     * @param displayIdentity 展示实体携带的作物身份（如 {@code customcrops:chinese_cabbage_stage_3}），无则 null
     * @param entityLabel     命中实体名（仅用于如实播报「命中了什么」），无则 null
     */
    private record CrosshairTarget(BlockPos pos, String displayIdentity, String entityLabel) {
    }

    /**
     * 准星目标校验（{@code 标记成熟} 与 {@code 标记成熟 强制} 共用，{@code force} 不能绕过任何一条）。
     *
     * <p>按 26.1.2 真实 {@code mc.hitResult} 的形态分别给中文原因：</p>
     * <ul>
     *   <li>{@code null} → 当前没有有效的准星目标；</li>
     *   <li>{@code Type.MISS}（朝向天空 / 空气，没有真正命中方块）→ 当前准星指向空气 + 操作提示；</li>
     *   <li>命中方块但该方块 {@code isAir()} → 当前准星指向空气；</li>
     *   <li>命中实体 → 取实体所在格；展示实体（{@code item_display}）直接读手里的物品模型，
     *       {@code interaction} 这类点击载体则查同格 / 上下邻格的展示实体；</li>
     *   <li>命中生物或其它非作物实体 → 如实播报命中了什么，绝不拿附近的作物顶替。</li>
     * </ul>
     *
     * <p><b>为什么必须接受实体命中：</b>CraftEngine 系服务器把作物渲染成展示实体（客户端未装对应模组
     * 时的退化渲染），世界里没有作物方块，盆上方只有空气或不变的隐形载体。这类作物<b>没有碰撞箱</b>，
     * 若命中实体直接判失败，玩家只会看到「准星没有对准任何方块」，人工校准成熟阶段永远走不通。</p>
     *
     * <p>只读<b>当前</b> {@code mc.hitResult}：不使用上一次目标、不使用附近作物、不缓存、不猜。</p>
     */
    private CrosshairTarget crosshairTarget() {
        HitResult hit = mc.hitResult;
        if (hit == null) {
            fail("标记成熟失败", "当前没有有效的准星目标");
            return null;
        }
        if (hit.getType() == HitResult.Type.MISS) {
            CommandMessageFormatter.of(MODULE_NAME, "标记成熟失败")
                .field("原因", "当前准星指向空气")
                .field("操作", "请将准星对准需要校准的自定义作物后再执行")
                .status(CommandMessageFormatter.Level.FAILURE, "未保存")
                .send();
            return null;
        }
        if (hit instanceof BlockHitResult blockHit) {
            BlockPos pos = blockHit.getBlockPos();
            BlockState state = mc.level == null ? null : mc.level.getBlockState(blockHit.getBlockPos());
            if (state == null || state.isAir()) {
                fail("标记成熟失败", "当前准星指向空气");
                return null;
            }
            return new CrosshairTarget(pos, displayIdentityAt(pos), null);
        }
        if (hit instanceof EntityHitResult entityHit) {
            Entity entity = entityHit.getEntity();
            if (entity == null || entity instanceof LivingEntity) {
                fail("标记成熟失败", "当前准星没有指向自定义作物"
                    + (entity == null ? "" : "（命中的是 " + entity.getName().getString() + "）"));
                return null;
            }
            BlockPos pos = entity.blockPosition();
            // 展示实体自带物品模型 → 直接读；interaction 这类点击载体不带物品 → 查同格与上下邻格的展示实体
            String identity = StardewCropDisplayProbe.modelOf(entity);
            if (identity == null) identity = displayIdentityAt(pos);
            return new CrosshairTarget(pos, identity, entity.getName().getString());
        }
        fail("标记成熟失败", "当前没有有效的准星目标");
        return null;
    }

    /**
     * 准星位置的作物真实状态：展示实体通道优先，其次命中的方块本身，再其次它上面一格。
     *
     * <p>与 {@code .id 方块}、农田扫描共用 {@link CropRuntimeStateResolver} 这一条唯一判定入口，
     * 准星路径不另写一套规则。</p>
     *
     * <p>「上面一格」是种植盆的固定结构：作物永远种在盆上，准星对着盆（或盆上的隐形载体）时实际
     * 要校准的是它上面那株作物；只有该格身份确实解析成了带阶段的作物才会采用，绝不会因为「附近有作物」
     * 就随便认一株。</p>
     */
    private CropRuntimeStateResolver.RuntimeResult resolveAt(CrosshairTarget target) {
        if (target.displayIdentity() != null) {
            CropRuntimeStateResolver.RuntimeResult byDisplay =
                CropRuntimeStateResolver.resolveIdentity(target.displayIdentity());
            if (byDisplay.isCrop() && byDisplay.hasStage()) return byDisplay;
        }
        CropRuntimeStateResolver.RuntimeResult direct = CropRuntimeStateResolver.resolve(target.pos());
        if (direct.isCrop() && direct.hasStage()) return direct;
        CropRuntimeStateResolver.RuntimeResult above = CropRuntimeStateResolver.resolve(target.pos().above());
        if (above.isCrop() && above.hasStage()) return above;
        if (direct.isCrop() || direct.hasStage()) return direct;
        return above;
    }

    /**
     * 该格（含上下邻格）展示实体携带的作物身份。
     *
     * <p>展示实体没有碰撞箱，准星命中的往往是服务端放来接收点击的 {@code interaction} 实体，它本身
     * 不带物品；真正的作物模型挂在旁边的 {@code item_display} 上，而落点在不同服务器上会差一格。
     * 只读<b>此刻</b>的世界实体：先补录归档（不丢弃农田扫描已有归档）再查。</p>
     *
     * @return 已确认成作物（能解析出阶段）的身份；都不是则 {@code null}
     */
    private static String displayIdentityAt(BlockPos pos) {
        StardewCropDisplayProbe.archiveAround(pos);
        for (BlockPos candidate : List.of(pos, pos.above(), pos.below())) {
            for (String model : StardewCropDisplayProbe.modelsAt(candidate)) {
                CropRuntimeStateResolver.RuntimeResult result = CropRuntimeStateResolver.resolveIdentity(model);
                if (result.isCrop() && result.hasStage()) return model;
            }
        }
        return null;
    }

    /** 失败原因里的主语：命中实体时如实报出命中了谁，避免玩家以为是「对准的方块不对」 */
    private static String crosshairSubject(CrosshairTarget target) {
        return target.entityLabel() == null ? "当前方块" : "命中的 " + target.entityLabel();
    }

    /**
     * 未写入（或无需写入）的统一播报：冲突 / 特殊变种 / 阶段不存在 / 已一致 / 未就绪 / 落盘失败。
     *
     * @param observed 世界方块的实时解析结果（准星模式）；参数模式传 null，此时不编造真实状态
     */
    private void reportRejected(StardewFarmModule module, StardewQuerySupport.MatureMarkOutcome outcome,
                                CropRuntimeStateResolver.RuntimeResult observed) {
        switch (outcome.status()) {
            case UNCHANGED -> CommandMessageFormatter.of(MODULE_NAME, "成熟规则已确认")
                .highlight("作物", module.cropDisplayName(outcome.cropKey()))
                .key("cropKey", outcome.cropKey())
                .field("成熟阶段", outcome.previousStage())
                .field("规则来源", evidenceLabel(outcome.previousEvidence()))
                .status(CommandMessageFormatter.Level.SUCCESS, "当前阶段与已有成熟规则一致，未改动任何规则")
                .send();

            case CONFLICT -> {
                CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "标记成熟失败")
                    .highlight("作物", module.cropDisplayName(outcome.cropKey()))
                    .key("cropKey", outcome.cropKey())
                    .field("当前阶段", outcome.stageName());
                if (observed != null) formatter.field("当前真实状态", observed.state().displayName());
                formatter.field("已有成熟阶段", outcome.previousStage())
                    .field("已有规则来源", evidenceLabel(outcome.previousEvidence()))
                    .field("原因", "当前阶段与已有成熟规则冲突")
                    .field("操作", "若确认该阶段才是成熟阶段，请对准该方块使用「.stardew 标记成熟 强制」（人工纠错，会覆盖现有规则）")
                    .status(CommandMessageFormatter.Level.FAILURE, "未修改任何规则")
                    .send();
            }

            case SPECIAL_STAGE -> fail("标记成熟失败",
                "当前阶段属于特殊变种，需要独立收割 / 行为规则，不能作为普通成熟阶段人工确认");

            case UNKNOWN_STAGE -> CommandMessageFormatter.of(MODULE_NAME, "标记成熟失败")
                .highlight("作物", module.cropDisplayName(outcome.cropKey()))
                .key("cropKey", outcome.cropKey())
                .field("阶段", outcome.stageName())
                .field("原因", "当前服务器资源中不存在该阶段")
                .field("操作", "若该作物就在眼前，请把准星对准成熟植株后执行「.stardew 标记成熟」——"
                    + "准星模式会带上刚读到的世界实证阶段，不依赖资源包的阶段清单")
                .status(CommandMessageFormatter.Level.FAILURE, "未保存")
                .send();

            case UNKNOWN_CROP -> fail("标记成熟失败", "该作物（" + outcome.cropKey()
                + "）不在当前服务器资源索引中：请先在「服务器资源」检测 / 提取资源");

            case NOT_READY -> reportResourceNotReady("标记成熟失败", "未保存");

            case NO_STAGE_EVIDENCE -> CommandMessageFormatter.of(MODULE_NAME, "标记成熟失败")
                .highlight("作物", module.cropDisplayName(outcome.cropKey()))
                .key("cropKey", outcome.cropKey())
                .field("阶段", outcome.stageName())
                .field("原因", "当前服务器资源里找不到该作物的阶段证据，无法建立逐作物签名")
                .field("操作", "先走到农田（或打开种子界面）让该作物被扫描到，再执行「.stardew 检测」重建资源索引后重试")
                .status(CommandMessageFormatter.Level.FAILURE, "未保存")
                .send();

            case FAILED -> fail("标记成熟失败", "保存失败：请确认当前服务器资源已就绪（需为多人服务器且已完成资源检测）");

            case SAVED -> {
                // 调用方只在非 SAVED 时进入本方法；此处仅为 switch 穷尽
            }
        }
    }

    /**
     * 统一播报「已保存」。
     *
     * @param observed 世界方块的实时解析结果（准星模式保存后重新解析）；参数模式传 null，不编造真实状态
     * @param force    是否走了显式「强制」纠错分支
     */
    private void reportSaved(StardewFarmModule module, StardewQuerySupport.MatureMarkOutcome outcome,
                             CropRuntimeStateResolver.RuntimeResult observed, boolean force) {
        CommandMessageFormatter formatter = CommandMessageFormatter
            .of(MODULE_NAME, force ? "已纠正成熟规则" : "已人工确认成熟规则")
            .highlight("作物", module.cropDisplayName(outcome.cropKey()))
            .key("cropKey", outcome.cropKey());
        if (force) {
            formatter.field("旧成熟阶段", outcome.previousStage() == null ? "无（此前没有成熟规则）" : outcome.previousStage())
                .field("新成熟阶段", outcome.stageName());
        } else {
            formatter.field("阶段", outcome.stageName())
                .field("成熟阶段", outcome.stageName());
        }
        if (observed != null) {
            // 保存后重新解析的真实状态（对准 stage_4 标记 → 必为成熟），不是手写的结论
            formatter.field("真实状态", observed.state().displayName());
            formatter.field("生命周期", observed.lifecycleLabel());
        }
        if (!module.harvestRuleComplete(outcome.cropKey())) {
            // 成熟阶段只是收菜的一半条件：没有确认生命周期时规则不算完整，模块会先做一次空手试探。
            // 不说清楚，玩家只会看到「已保存」却等不到自动收菜（实机反馈：这条指令「没用」）。
            formatter.field("后续", "该作物还缺「生命周期」证据：模块下一轮会对准成熟植株做一次空手试探自动确认"
                + "（一次性 / 保株），确认后立即开始自动收菜，不需要再手动执行任何指令");
        }
        formatter.field("规则来源", RuleEvidence.VERIFIED.displayName()
                + (force ? "（VERIFIED · 人工纠错）" : "（VERIFIED · 人工校准）"))
            .field("作用域", "当前服务器 + 资源指纹 + 作物")
            .status(CommandMessageFormatter.Level.SUCCESS, "已保存")
            .send();
    }

    /** 规则来源显示文案：中文 + 技术名，与统一判定组件同一口径 */
    private static String evidenceLabel(RuleEvidence evidence) {
        return evidence == null ? "未知" : evidence.displayName() + "（" + evidence.name() + "）";
    }

    private void status() {
        StardewFarmModule module = module();
        if (module == null) return;
        // 整块一条多行消息：只带一次 [yiyiaddon][星露谷农场] 前缀，避免被其它消息插队割裂。
        // 标题由排版器统一给出（与其它指令同款），模块只提供「标签 ▶ 值」数据行。
        CommandMessageFormatter card = CommandMessageFormatter.of(MODULE_NAME, "当前状态");
        for (String line : module.statusLines()) card.raw(line);
        card.send();
    }

    /**
     * {@code .stardew 诊断}：资源扫描 / 索引 / 准星识别链路的运行时真相。
     *
     * <p>「这个服只识别出一两种作物」这类问题，先跑它就能看出断在哪一环：扫描到多少条、索引里实际
     * 有几种作物、有没有阶段模型、索引构建是否半途失败、准星方块的方块 ID 能否派生出语义身份。
     * 与 {@code 状态} 分开：{@code 状态} 讲模块与配置，{@code 诊断} 讲资源与识别。</p>
     */
    private void diagnose() {
        StardewFarmModule module = module();
        if (module == null) return;
        CommandMessageFormatter card = CommandMessageFormatter.of(MODULE_NAME, "运行时诊断");
        for (String line : module.diagnosticLines()) {
            card.raw(line);
            // 诊断只进聊天框时没法远程排查（截图才看得到），这里同时留一份到日志。
            // 玩家在商店 / 箱子等界面里根本无法输入指令，日志往往是唯一能拿到的证据。
            LOGGER.info("[星露谷] 诊断 {}", line.replaceAll("§[0-9a-fk-orA-FK-OR]", ""));
        }
        card.send();
    }

    /**
     * {@code .stardew 预览范围}：不启动模块也能看种植区域与附近洒水器覆盖范围；
     * 32 格内自动显示，不用绑定（再敲一次关闭）。
     *
     * <p>播报由模块统一给出（GUI 与指令共用同一实现）。</p>
     */
    private void toggleRangePreview() {
        StardewFarmModule module = module();
        if (module == null) return;
        // 关掉不拦（纯本地开关，任何情况下都得让你关）；开启才要求资源就绪——
        // 没读过资源就认不出洒水器类型，开了也是一片空白，只会让人以为功能坏了。
        if (!module.rangePreviewOn() && !resourceGate(module, "范围预览失败", "未开启")) return;
        module.toggleRangePreview();
    }

    /** {@code .stardew 控制台}：打开整屏控制台（概览 / 种植 / 后勤 / 点位 / 日志） */
    private void openConsole() {
        StardewFarmModule module = module();
        if (module == null) return;
        // 指令在聊天屏的 sendChat 流程里执行，紧接着聊天屏会关闭自身；此处同步 setScreen 会被
        // 立即覆盖（表现为「点了没反应」），因此必须推迟一帧再开（与 .id 指令同一套做法）。
        mc.execute(() -> mc.setScreen(new StardewConsoleScreen(mc.screen, module)));
    }

    /** {@code .stardew 季节}：季节识别结论 + 最近一次真实证据 */
    private void seasonStatus() {
        StardewFarmModule module = module();
        if (module == null) return;
        module.reportSeasonStatus();
    }

    /** {@code .stardew 季节 <季节名>}：人工绑定当前季节图标 */
    private void bindSeason(String seasonName) {
        StardewFarmModule module = module();
        if (module == null) return;
        module.bindSeasonFromProbe(seasonName);
    }

    /** {@code .stardew 季节 清除}：删除当前服务器的人工季节绑定 */
    private void clearSeasonBinding() {
        StardewFarmModule module = module();
        if (module == null) return;
        module.clearSeasonBinding();
    }

    private void clear() {
        StardewFarmModule module = module();
        if (module == null) return;
        // 播报由模块内部统一发出（与 GUI 同一实现）
        module.clearAllPoints();
    }

    // ── 解析辅助 ──

    /**
     * 点位中文字面量 → 点位类型（逐字对应旧 {@code addSetBranch} / {@code 移除} 分支的中文字面量）。
     *
     * @return 未匹配返回 {@code null}
     */
    private static StardewPointType pointTypeOf(String zh) {
        if (zh == null) return null;
        return switch (zh) {
            case "种子箱" -> StardewPointType.SEED_BOX;
            case "成品箱" -> StardewPointType.OUTPUT_BOX;
            case "补水点" -> StardewPointType.WATER_SOURCE;
            case "岩浆箱" -> StardewPointType.LAVA_BOX;
            case "龙息箱" -> StardewPointType.BREATH_BOX;
            default -> null;
        };
    }

    /** 实例所在模块：与 AutoChestCommand 同一套取实例方式，不自造全局单例 */
    private static StardewFarmModule module() {
        return ModuleManager.byId(StardewFarmModule.MODULE_ID) instanceof StardewFarmModule stardew ? stardew : null;
    }

    /** 失败播报：统一「标题 + 原因 + 状态」中文格式 */
    private void fail(String title, String reason) {
        CommandMessageFormatter.of(MODULE_NAME, title)
            .field("原因", reason)
            .status(CommandMessageFormatter.Level.FAILURE, "未保存")
            .send();
    }
}
