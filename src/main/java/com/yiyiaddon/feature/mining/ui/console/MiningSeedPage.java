package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.target.MiningTargetProvider;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.render.SeedRenderSnapshot;
import com.yiyiaddon.seed.runtime.SeedPredictionCoverageController;
import com.yiyiaddon.seed.service.SeedMiningService;
import com.yiyiaddon.seed.validation.SeedValidationSnapshot;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/**
 * 自动挖矿控制台「种子挖矿」页（正式化第二阶段）。
 *
 * <p><b>本页只做三件事</b>：读种子挖矿服务的状态、改两项配置（启用 / 服务器种子）、
 * 触发一次「测试当前区块预测」。它<b>不</b>自己 new 预测器、不开线程、不读维度、不写配置 ——
 * 那些全部由 {@link SeedMiningService} 负责（阶段口径第二十节）。</p>
 *
 * <p><b>为什么整页都是实时读数（没有本页的「刷新」）</b>：控制台的概览页靠「每秒整页重建」刷新，
 * 而重建会调用 {@code SettingTextBox.clearFocus()} —— 本页有输入框，整页重建等于把玩家正在输入的
 * 焦点与光标抹掉（在种子输入框上表现为「打字打到一半就断」）。因此本页的每一个动态值都用
 * {@code Supplier} 现读：Skija 每帧都会画面板，读数自然跟着变，<b>不需要也不允许</b>整页重建。
 * 这也是本页不接 {@code owner.reload()} 的原因（除了「恢复默认」那种必须重置输入框的动作）。</p>
 *
 * <p><b>文案口径</b>（阶段口径第二十四、二十八、二十九、三十一、四十六、四十七节）：</p>
 * <ul>
 *   <li>「未解析」只说明算法尚未证明，绝不写成「假矿 / 低可信 / 错误矿」；</li>
 *   <li>「确定性」本阶段恒为 0，如实显示，不藏；</li>
 *   <li>种子状态只有「未填写 / 格式无效 / 已填写」，<b>没有</b>「种子已验证」；</li>
 *   <li>本页不出现矿物选择、精准采集、时运、食物、回家、背包、Baritone 等属于自动挖矿的设置项。</li>
 * </ul>
 */
public final class MiningSeedPage {

    /** 种子输入框宽度与长度上限（与「传送指令」页同档：220 宽；种子最长 20 字符，64 足够宽松）。 */
    private static final float TEXT_BOX_WIDTH = 220f;
    private static final int TEXT_MAX_LENGTH = 64;

    /** 本页全部读数与动作的唯一来源（常驻服务，不随本窗口开关）。 */
    private final SeedMiningService service = SeedMiningService.instance();

    private final MiningConsoleScreen owner;

    /**
     * 自动挖矿模块（235 新增）：只为「自动挖矿接入」区服务 —— 那一行开关与两行只读状态都属于
     * 自动挖矿的目标来源，落盘在自动挖矿的设置里（按服务器隔离），不在种子模块另存一份。
     */
    private final AutoMinerModule module;

    /**
     * @param owner  控制台窗口（本页只用到它：登记 tooltip、开子窗口不需要、重建页面）
     * @param module 自动挖矿模块（「自动挖矿接入」区的开关与读数来源）
     */
    public MiningSeedPage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        stack.add(new Note(owner, "§7§l种子挖矿 §8（根据世界种子离线预测矿物位置；不读真实世界，"
            + "也不依赖服务器下发过哪些区块。预测由本机自动启动的「本地世界生成计算器」完成）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        // ── 配置 ──
        stack.add(section("配置"));

        stack.add(new ConsoleRow(owner, () -> "启用种子挖矿",
            "开启后才允许预测；关闭时不跑预测、不创建会话、不占用后台线程，也不影响现有自动挖矿",
            null,
            List.of(new Ctl(new SettingToggle(service::enabled, service::setEnabled)),
                ConsoleWidgets.resetCtl(() -> {
                    service.setEnabled(false);
                    owner.reload();
                }, "启用种子挖矿"))));

        stack.add(new ConsoleRow(owner, () -> "服务器种子",
            "手动填写服务器的世界种子：Java long 十进制整数，可带负号（例：0 / 12345 / -7777 / 20260922）。"
                + "本阶段不自动读取服务器种子、不发任何命令、不猜测",
            null,
            List.of(new Ctl(new SettingTextBox(service::seedText, service::setSeedText, TEXT_MAX_LENGTH)
                    .width(TEXT_BOX_WIDTH)),
                ConsoleWidgets.resetCtl(() -> {
                    service.setSeedText("");
                    owner.reload();
                }, "服务器种子"))));

        stack.add(dataRow("种子状态", service::seedStatusCn));
        stack.add(new Note(owner, "§8只做格式校验：填了合法数字也只表示「格式对」，"
            + "不表示这个种子属于当前服务器（本阶段没有种子真实性验证）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        // ── 世界渲染（种子预测渲染器；口径第三十七节：新增设置只允许属于这一组） ──
        stack.add(section("世界渲染"));

        stack.add(new ConsoleRow(owner, () -> "显示预测钻石",
            "开启后，你附近的区块会按范围逐个送进本地世界生成计算器预测，并在世界里画出预测钻石。"
                + "关闭只是不再画框（预测缓存与观察状态继续保留）—— 自动挖矿用种子目标时靠的正是这份预测",
            null,
            List.of(new Ctl(new SettingToggle(service::renderPrediction, service::setRenderPrediction)),
                ConsoleWidgets.resetCtl(() -> {
                    service.setRenderPrediction(false);
                    owner.reload();
                }, "显示预测钻石"))));

        stack.add(new ConsoleRow(owner, () -> "预测范围",
            "以你所在区块为中心、按距离由近到远预测：半径 N = (2N+1)×(2N+1) 个区块。"
                + "范围越大首次铺开越久，默认 3（7×7，最多 49 个区块）；允许 1~6",
            null,
            List.of(new Ctl(new SettingNumberBox(
                    SeedMiningService.coverageRadiusMin(), SeedMiningService.coverageRadiusMax(), 1, "%.0f 区块",
                    () -> (double) service.coverageRadius(),
                    value -> service.setCoverageRadius((int) Math.round(value)))),
                ConsoleWidgets.resetCtl(() -> {
                    service.setCoverageRadius(SeedPredictionCoverageController.RADIUS_DEFAULT);
                    owner.reload();
                }, "预测范围"))));

        stack.add(new ConsoleRow(owner, () -> "显示当前缺失",
            "把「预测位置现在实际不是钻石矿」也画成灰色细框。默认关闭，避免世界里出现大量无效框。"
                + "它只表示当前实际不是钻石，不代表假矿、不代表服务器作弊、也不代表种子填错",
            null,
            List.of(new Ctl(new SettingToggle(service::showMissing, service::setShowMissing)),
                ConsoleWidgets.resetCtl(() -> {
                    service.setShowMissing(false);
                    owner.reload();
                }, "显示当前缺失"))));

        stack.add(new Note(owner, "§8颜色：§b青框§8 = 预测钻石（客户端还没加载该区块）；"
            + "§a绿框§8 = 已确认钻石（当前实际就是钻石矿）；§7灰框§8 = 当前缺失（默认不画）；"
            + "§6内圈琥珀细框§8 = 调度敏感（原版世界生成顺序可能影响该位置）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, "§8调度敏感不等于假矿：真实世界里它同样可能出现。"
            + "本阶段只回答「预测」与「当前实际看到什么」，不做种子校验、不判定假矿", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        // ── 自动挖矿接入（正式化第七阶段 235） ──
        stack.add(section("自动挖矿接入"));

        stack.add(new ConsoleRow(owner, () -> "使用种子目标",
            "开启后自动挖矿只按「已通过验证的钻石种子预测」逐颗精确挖：先寻路到预测坐标，到了再看实际方块，"
                + "是钻石就交给秒破 / 连锁，不是钻石就换下一颗。它是硬开关 —— 验证没通过时不会开始挖矿，"
                + "也绝不会退回「按钻石矿石类型在附近全局搜」",
            null,
            List.of(new Ctl(new SettingToggle(module::isSeedTargetMode, module::setSeedTargetMode)),
                ConsoleWidgets.resetCtl(() -> {
                    module.setSeedTargetMode(false);
                    owner.reload();
                }, "使用种子目标"))));

        stack.add(dataRow("挖矿模式", () -> module.miningTargetProvider().modeNameCn()));
        stack.add(dataRow("扫描方式", () -> module.miningTargetProvider().scanModeCn()));
        stack.add(dataRow("目标状态", () -> module.miningTargetProvider().statusCn()));
        stack.add(dataRow("验证闸门", () -> service.mayUseForAutomatedMining()
            ? "已放行（验证通过）" : "未放行（不会启动种子挖矿）"));

        stack.add(new Note(owner, this::seedReadinessCn, null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        // ── 附近覆盖（覆盖进度与归类统计；不含「可疑」这一项） ──
        stack.add(section("附近覆盖"));
        stack.add(dataRow("覆盖进度", () -> service.coveragePredictedCount() + " / "
            + service.coverageTargetCount() + " 区块"));
        stack.add(dataRow("附近预测", () -> String.valueOf(stat(SeedRenderSnapshot.Stats::candidates))));
        stack.add(dataRow("尚未观察", () -> String.valueOf(stat(SeedRenderSnapshot.Stats::unobserved))));
        stack.add(dataRow("已确认", () -> String.valueOf(stat(SeedRenderSnapshot.Stats::confirmed))));
        stack.add(dataRow("当前缺失", () -> String.valueOf(stat(SeedRenderSnapshot.Stats::missing))));
        stack.add(dataRow("调度敏感", () -> String.valueOf(stat(SeedRenderSnapshot.Stats::scheduleSensitive))));
        stack.add(dataRow("正在预测", service::coverageActiveChunkCn));
        stack.add(dataRow("排队中", () -> service.coveragePendingCount() + " 个"));
        stack.add(dataRow("预测缓存", () -> service.cachedChunkCount() + " / "
            + service.cachedChunkLimit() + " 个区块"));

        stack.add(new Note(owner, "§8预测逐个区块到达、逐步出现在世界里，不是等全部算完才显示。"
            + "客户端只会读「服务器已经发给它的区块」，不会为了确认预测去请求加载任何区块", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        // ── 服务器种子验证（正式化第六阶段 234；只读服务层验证快照，界面不自己算） ──
        stack.add(section("服务器种子验证"));
        stack.add(dataRow("验证状态", () -> validation().stateCn()));
        stack.add(dataRow("有效样本区块", () -> validation().sampleChunks() + " 个"));
        stack.add(dataRow("有效确认单元", () -> validation().confirmedUnits() + " 个"));
        stack.add(dataRow("已确认候选", () -> validation().confirmedPositions() + " 个"));
        stack.add(dataRow("当前缺失", () -> validation().missingPositions() + " 个"));

        stack.add(new ConsoleRow(owner, () -> "重新开始验证",
            "清空当前会话已经收集的验证证据，从此刻起重新收集。它不改种子、不清世界、"
                + "也不清预测缓存；清空后需要产生新的观察样本（重新加载区块或方块发生变化）才会重新累积证据",
            null,
            List.of(new Ctl(new Button("§7重新开始验证", () -> service.restartValidation())))));

        stack.add(new Note(owner, () -> "§8" + validation().reasonCn(), null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, () -> "§8" + validation().policyCn(), null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, () -> "§8" + validation().scopeNoteCn(), null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, () -> "§8" + unverifiedNoteCn(), null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        // ── 环境 ──
        stack.add(section("环境"));
        stack.add(dataRow("当前维度", service::dimensionDisplayCn));
        stack.add(dataRow("支持状态", service::dimensionSupportCn));
        stack.add(dataRow("预测模型", service::predictModelCn));
        stack.add(dataRow("世界生成计算器", this::calculatorStateCn));
        stack.add(new Note(owner, () -> "§8" + service.predictModelNoteCn(), null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        // ── 预测状态 ──
        stack.add(section("预测状态"));
        stack.add(dataRow("状态", service::stateCn));
        stack.add(dataRow("当前区块", service::playerChunkCn));

        stack.add(new ButtonStrip(owner, List.of(new Ctl(
            new Button("§b测试当前区块预测", service::predictCurrentChunk)
                .disabledWhen(() -> !service.canPredict()), this::predictHint)), ButtonStrip.BUTTON_HEIGHT));

        stack.add(new Note(owner, "§8只做预测与展示：不加进自动挖矿目标、不寻路、不破坏方块、不调用秒破", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        // ── 上一次预测结果 ──
        stack.add(section("上一次预测结果"));
        stack.add(dataRow("预测区块", service::predictedChunkCn));
        stack.add(resultRow("候选钻石", this::count));
        stack.add(resultRow("调度敏感", this::scheduleSensitiveCount));
        stack.add(resultRow("未解析", this::unresolvedCount));
        stack.add(resultRow("确定性", this::deterministicCount));
        stack.add(resultRow("预测耗时", () -> elapsed() + " ms"));
        stack.add(resultRow("缓存区块", () -> heldChunks() + " 个"));
        stack.add(dataRow("失败原因", this::failureCn));

        stack.add(new Note(owner, "§8未解析不代表没有矿，只表示当前算法尚未证明该坐标是否受世界生成调度影响。", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, "§8调度敏感：原版世界生成顺序可能影响该位置最终是否为矿物。"
            + "调度敏感不等于假矿（真实世界里它同样可能出现）。", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, "§8确定性：本阶段算法尚不具备证明能力，因此恒为 0 —— 这是如实结果，不是故障。", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, "§8本阶段只支持：主世界 + 钻石。下界 / 末地 / 自定义维度不会调用主世界预测器。", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
    }

    // ── 行构件 ──

    /** 分区标题（与控制台其余页同一套样式）。 */
    private Note section(String title) {
        return new Note(owner, "§7§l" + title, null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE);
    }

    /** 只读数据行：标签 + 每帧现读的值（本页所有读数都走它）。 */
    private CompactElement dataRow(String label, Supplier<String> value) {
        return new Note(owner, () -> "§7" + label + "  §f" + safe(value));
    }

    /** 预测结果行：没有结果时显示「—」，不把「0 个矿」与「没预测过」混成一件事。 */
    private CompactElement resultRow(String label, IntSupplier value) {
        return resultRow(label, () -> String.valueOf(value.getAsInt()));
    }

    /** 预测结果行（数值形态自由：带单位的耗时 / 缓存区块走这一条）。 */
    private CompactElement resultRow(String label, Supplier<String> value) {
        return dataRow(label, () -> service.lastResult() == null ? "—" : value.get());
    }

    // ── 读数 ──

    /**
     * 本地世界生成计算器的界面文案（阶段 232 口径第二十五、六十一、七十四节）。
     *
     * <p>只回答「本机计算器现在怎么样」：未启动 / 正在启动 / 已就绪 / 异常。
     * 用户界面上<b>不出现</b> ServerLevel / IPC / PID / Socket 这类技术细节；
     * 计算器异常时说清是<b>本机</b>的问题，绝不写成「服务器不支持」。</p>
     */
    private String calculatorStateCn() {
        return switch (service.state()) {
            case DISABLED -> "已关闭";
            case WAITING_FOR_WORLD -> "等待进入世界";
            case CALCULATOR_STARTING -> "正在启动…";
            case CALCULATOR_FAILED -> "异常（已停止）";
            default -> service.calculatorRunning() ? "已就绪" : "未启动（首次预测时自动启动）";
        };
    }

    private int count() {
        PredictionResult result = service.lastResult();
        return result == null ? 0 : result.count();
    }

    /**
     * 附近覆盖统计的读数（口径第三十九节）。
     *
     * <p>取的是服务层的不可变渲染快照（volatile 引用，读一次 O(1)），
     * 因此本页每帧刷新这些格子不会触发任何重算。</p>
     */
    private int stat(ToIntFunction<SeedRenderSnapshot.Stats> field) {
        try {
            return field.applyAsInt(service.renderSnapshot().stats());
        } catch (Throwable error) {
            return 0;
        }
    }

    /**
     * 种子验证快照（正式化第六阶段 234）。
     *
     * <p>界面<b>只读</b>它，绝不自己计算验证逻辑（口径第七十三节）；读取失败时退回空快照，
     * 避免一帧异常把整页读数打崩。</p>
     */
    private SeedValidationSnapshot validation() {
        try {
            SeedValidationSnapshot snapshot = service.validationSnapshot();
            return snapshot == null ? SeedValidationSnapshot.EMPTY : snapshot;
        } catch (Throwable error) {
            return SeedValidationSnapshot.EMPTY;
        }
    }

    /**
     * 「种子尚未验证」提示（口径第九、五十一节：预测框能显示 ≠ 预测已验证）。
     *
     * <p>文案里刻意不出现「种子已确认 / 真实 Seed / 100% 正确」这类措辞（口径第五十三节），
     * 只说明「当前能不能作为可信依据」。验证通过时给出正式通过文案与范围声明。</p>
     */
    private String unverifiedNoteCn() {
        SeedValidationSnapshot snapshot = validation();
        return switch (snapshot.state()) {
            case VERIFIED -> "种子验证通过（基于已观察样本）：允许作为后续自动化挖矿的前置条件；"
                + "但它仍不是「服务器真实 Seed 已被唯一确定」的证明";
            case COLLECTING -> "当前 Seed 尚未完成验证（正在收集样本）：预测框可以照常显示，"
                + "但它现在还只是「按填写的种子算出来的结果」，不要当成已验证依据";
            case UNVERIFIED -> "当前 Seed 尚未验证（还没有有效观察样本）：预测框可以照常显示，"
                + "但它现在还只是「按填写的种子算出来的结果」，不要当成已验证依据";
            case INCONCLUSIVE -> "当前 Seed 证据不足：已看到不少数据但不足以验证，"
                + "可能因为区块被挖过、区块较旧或样本太少；这不等于种子填错";
            case CONFLICTING -> "当前 Seed 与已观察样本存在冲突证据（本阶段该状态不可达）";
        };
    }

    private int scheduleSensitiveCount() {
        PredictionResult result = service.lastResult();
        return result == null ? 0 : result.scheduleSensitiveCount();
    }

    private int unresolvedCount() {
        PredictionResult result = service.lastResult();
        return result == null ? 0 : result.unresolvedCount();
    }

    private int deterministicCount() {
        PredictionResult result = service.lastResult();
        return result == null ? 0 : result.deterministicCount();
    }

    private long elapsed() {
        PredictionResult result = service.lastResult();
        return result == null ? 0L : result.elapsedMillis();
    }

    /** 会话此刻持有的离线区块数（缓存规模）：取自结果快照，绝不在渲染线程上去锁预测器。 */
    private int heldChunks() {
        PredictionResult result = service.lastResult();
        return result == null ? 0 : result.stats().heldChunks();
    }

    /** 失败原因：优先用正式层的失败文案；没有结果但有计算器错误时（启动失败 / 崩溃）显示它。 */
    private String failureCn() {
        String failure = service.failureCn();
        if (!failure.isEmpty()) {
            return failure;
        }
        String calculatorError = service.calculatorErrorCn();
        return calculatorError.isEmpty() ? "—" : calculatorError;
    }

    /** 按钮的悬停说明：不能点的时候直接说清是哪一个条件没满足。 */
    private String predictHint() {
        if (service.canPredict()) {
            return "预测你当前所在区块的钻石（本机自动启动「本地世界生成计算器」，"
                + "画面不卡、不寻路、不破坏方块）";
        }
        return "现在不能预测（" + service.stateCn() + "）：需要「已启用 + 已进入世界 + "
            + "种子格式合法 + 当前维度为主世界」四项同时满足";
    }

    /**
     * 「使用种子目标」那一行的状态说明（235）。
     *
     * <p>判据直接取自动挖矿模块的那一处（它内部转发给种子目标提供者），本页<b>不</b>自己判任何条件
     * —— 与启动自检同源，避免出现「页面说可以、一启动却报不行」。</p>
     */
    private String seedReadinessCn() {
        if (!module.isSeedTargetMode()) {
            return "§8未开启种子目标模式：自动挖矿按原有方式工作（男中音按矿物类型扫描）";
        }
        String reason = module.seedTargetBlockReasonCn();
        if (reason.isEmpty()) {
            MiningTargetProvider provider = module.miningTargetProvider();
            return "§8前置条件齐备：允许按种子预测挖矿（当前 §f" + provider.statusCn() + "§8）";
        }
        return "§8当前不会开始种子挖矿：§e" + reason;
    }

    private static String safe(Supplier<String> value) {
        try {
            String text = value.get();
            return text == null ? "—" : text;
        } catch (Throwable error) {
            return "读数异常";
        }
    }
}
