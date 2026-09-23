package com.yiyiaddon.seed.service;

import com.google.gson.JsonObject;
import com.yiyiaddon.config.ModuleStateConfig;
import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.seed.config.SeedMiningConfig;
import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.model.SeedOreTarget;
import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.observation.SeedObservationSnapshot;
import com.yiyiaddon.seed.observation.SeedOreObservationTracker;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.ore.SeedOreRegistry;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.render.SeedOreWorldRenderer;
import com.yiyiaddon.seed.render.SeedRenderSnapshot;
import com.yiyiaddon.seed.runtime.SeedPredictionCoverageController;
import com.yiyiaddon.seed.runtime.SeedPredictionRepository;
import com.yiyiaddon.seed.runtime.SeedRuntimeIdentity;
import com.yiyiaddon.seed.runtime.TargetKey;
import com.yiyiaddon.seed.validation.SeedValidationEvidence;
import com.yiyiaddon.seed.validation.SeedValidationEvidenceGroup;
import com.yiyiaddon.seed.validation.SeedValidationService;
import com.yiyiaddon.seed.validation.SeedValidationSnapshot;
import com.yiyiaddon.seed.worker.client.SeedWorkerException;
import com.yiyiaddon.seed.worker.client.SeedWorkerState;
import com.yiyiaddon.seed.worker.client.SeedWorldgenWorkerClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.BooleanSupplier;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式模块 · <b>运行时服务</b>（正式化第四阶段：本地隔离世界生成 Worker）。
 *
 * <p><b>它是谁</b>：界面与「本地世界生成计算器」之间的唯一一层控制器。界面只做两件事 ——
 * 读状态、提交用户动作；配置 / 当前维度 / 后台任务 / 取消 / 最后一次结果 / 错误状态由本类持有。</p>
 *
 * <p><b>本阶段最大的变化</b>（阶段 232 口径第三、五、九十二、九十三节）：本类<b>不再</b>从
 * {@code Minecraft#getSingleplayerServer()} 取世界生成宿主 —— 那条路在多人服务器上必然是空的
 * （231 已证明是原版类型契约，逃不掉）。现在宿主由<b>本机隔离的 Worker 进程</b>提供，
 * 因此单人 / 多人走<b>同一条</b>正式路径：</p>
 *
 * <pre>
 * 界面 → SeedMiningService → SeedWorldgenWorkerClient → 本地隔离 Worker → Vanilla ServerLevel
 *      → DiamondSeedPredictor → PredictionResult → 回投客户端
 * </pre>
 *
 * <p><b>它仍然不碰</b>（口径第二十三、七十八~八十三节）：进程管理器 / Socket / JSON 解析 / 握手 / 宿主启动
 * 这些全部在 {@code seed.worker} 与 {@code seedworker} 两个包里；本类只负责业务状态与请求。另外仍然
 * <b>没有</b> SeedValidation、AutoMiner 接入、其它矿物、26.2。</p>
 *
 * <p><b>正式化第五阶段（233）新增的三条只读接线</b>（口径第六、十四、十七节：职责拆到专门类里，
 * 本类只做「谁在什么时候驱动谁」）：</p>
 * <ol>
 *     <li>{@link SeedPredictionRepository} —— 客户端预测缓存（有界、绑定 {@link SeedRuntimeIdentity}）；</li>
 *     <li>{@link SeedPredictionCoverageController} —— 玩家附近的目标调度（近→远、1 个在跑 + 有限排队）；</li>
 *     <li>{@link SeedOreObservationTracker} —— 只读「客户端已加载区块」的真实方块状态
 *         （绝不主动加载区块、绝不扫全区块），{@link SeedOreWorldRenderer} 只读它的快照。</li>
 * </ol>
 * 三者都<b>不含</b>预测算法本身：预测永远只发生在 Worker 里（口径第七节）。</p>
 *
 * <p><b>正式化第六阶段（234）新增的第四条接线</b>：{@link SeedValidationService} —— 种子验证。
 * 它只消费「预测缓存 + 观察层」，按<b>有效证据单元</b>聚票后给出验证结论
 * （未验证 / 收集中 / 已验证 / 证据不足 / 与当前模型冲突），并向界面暴露只读快照。
 * 服务层在这里只负责三件事（口径第七十二节）：建立身份时 {@code bind}、
 * 观察变更时 {@code update}、运行时失效时 {@code reset}；
 * 验证阈值与判据全部在 {@code seed.validation} 包里，<b>不</b>塞进本类。</p>
 *
 * <p><b>正式化第七阶段（235）</b>：自动挖矿成为种子预测的<b>第二个消费者</b>。因此本阶段把
 * 「世界渲染」从「运行时是否工作」里拆出来（口径：Renderer 与 Coverage 解耦）——</p>
 * <pre>
 * Seed Runtime / Coverage
 * ├── Renderer consumer（「显示预测钻石」开关，只决定画不画框）
 * └── AutoMiner consumer（{@code MiningSettings#seedTargetMode}，决定要不要一直预测下去）
 * </pre>
 * 只有<b>「启用种子挖矿」总开关关闭 / 退世界 / 换维度 / 改种子</b>才真的停止整个运行时；
 * 关掉「显示预测钻石」只是不画框，<b>不再</b>清预测缓存、覆盖队列与观察状态。</p>
 *
 * <p><b>线程模型</b>：公开方法都在客户端主线程调用；真正的「启动 Worker + 会话 + 预测」在单线程
 * 背景 Executor（{@code yiyiaddon-seed-predict}）上执行，结果通过 {@code Minecraft#execute} 回投主线程，
 * 并用任务代号（{@link #generation} 管手动单次预测、{@link #identityEpoch} 管覆盖式预测）丢弃过期回写
 * （口径第二十一、二十二、五十八节）。</p>
 */
public final class SeedMiningService {

    /** 日志器（与正式预测层同一个 logger 名，便于一次检索到种子挖矿的全部输出）。 */
    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed");

    /** 日志关键词（中文，便于一眼认出）。 */
    private static final String LOG_KEY = "种子挖矿";

    /** 事件总线所有者标识（常驻服务，不占用任何模块所有者）。 */
    private static final String TICK_OWNER = "seed.mining.service";

    /** 配置记录键（落进现有 {@code module-state.json} 的模块记录位；刻意不用真实模块 id）。 */
    private static final String RECORD_ID = "seed_mining";

    /** 本阶段被支持的维度档案（236 起：主世界与下界，各自由 {@link SeedOreRegistry} 声明矿物）。 */
    private static final List<SeedDimensionProfile> SUPPORTED_DIMENSIONS = List.of(
            SeedDimensionProfile.OVERWORLD, SeedDimensionProfile.NETHER);

    /**
     * 下界自动挖矿闸门（<b>编译期常量，恒 false</b>，正式化第八阶段 236）。
     *
     * <p>写成常量而不是配置项，是为了让它<b>无法被界面或配置悄悄打开</b>：
     * 要打开它必须改这里，而改这里就等于声明「已经建立下界专属验证证据」。</p>
     */
    private static final boolean NETHER_AUTOMINER_ENABLED = false;

    /** 本模组 id（取模组版本用）。 */
    private static final String MOD_ID = "yiyiaddon";

    /** 后台线程名（单线程守护线程，随客户端进程结束）。 */
    private static final String WORKER_NAME = "yiyiaddon-seed-predict";

    private static final SeedMiningService INSTANCE = new SeedMiningService();

    // ── 配置（仅客户端主线程访问） ──

    /** 可持久化配置（本阶段只有「启用」与「种子原文」两项）。 */
    private final SeedMiningConfig config = new SeedMiningConfig();

    /** 当前配置所属的作用域（{@code WorldIdentity.fileSafeServer()}）；null = 全局模板 / 未进世界。 */
    private String configScope;

    /** 当前种子原文解析出来的合法种子；不合法或未填写为 null（文本一改就重算，避免每帧解析）。 */
    private volatile Long parsedSeed;

    // ── 运行时状态（跨线程可见的一律 volatile） ──

    /** 后台执行线程（整个客户端进程一个，不随世界 / 界面开关重建）。 */
    private final ExecutorService worker = Executors.newSingleThreadExecutor(task -> {
        Thread thread = new Thread(task, WORKER_NAME);
        thread.setDaemon(true);
        return thread;
    });

    /** 本地世界生成计算器客户端（进程生命周期 + IPC 会话 + 预测请求都在它里面）。 */
    private final SeedWorldgenWorkerClient calculator = new SeedWorldgenWorkerClient();

    /** 客户端预测缓存（正式化第五阶段 233；有界、绑定运行时身份）。 */
    private final SeedPredictionRepository repository = new SeedPredictionRepository();

    /** 附近预测覆盖调度器（正式化第五阶段 233；近→远、单执行位 + 有限排队）。 */
    private final SeedPredictionCoverageController coverage = new SeedPredictionCoverageController();

    /** 实际 Chunk 观察器（正式化第五阶段 233；单例，因为区块事件与方块更新钩子都要找到它）。 */
    private final SeedOreObservationTracker observer = SeedOreObservationTracker.instance();

    /**
     * 种子验证服务（正式化第六阶段 234）。
     *
     * <p>它<b>只消费</b>「预测缓存 + 观察层」两样东西（口径第五十八节），
     * 负责把观察样本聚成有效证据单元、按正式阈值给出验证结论，并向界面暴露只读快照。
     * 服务层只做「谁在什么时候驱动它、失效时清它」这点接线（口径第七十二节）。</p>
     */
    private final SeedValidationService validation = new SeedValidationService();

    /**
     * 渲染层此刻是否可见（volatile：客户端主线程写、渲染线程每帧读）。
     *
     * <p>它是渲染器的第一道闸；第二道是「渲染层本身有没有注册」。两道都在
     * {@link #invalidateRuntime(String)} 里被同时收掉，因此关功能 / 退世界后世界里
     * 一个预测框都不会残留（口径第四十节）。</p>
     */
    private volatile boolean renderActive;

    /** 「是否显示当前缺失」的镜像（同样是主线程写、渲染线程读）。 */
    private volatile boolean showMissingMirror;

    /**
     * 预测钻石世界渲染器（正式化第五阶段 233）。
     *
     * <p>两个判断都读本类的 volatile 快照，因此渲染线程读到的永远是主线程写好的值，
     * 不会出现「渲染线程去读配置对象」这种跨线程访问（口径第二十七节）。</p>
     */
    private final SeedOreWorldRenderer renderer =
            new SeedOreWorldRenderer(() -> renderActive, () -> showMissingMirror);

    /** 当前有效的任务代号；提交任务时递增，取消时也递增（旧任务据此识别自己已失效）。 */
    private volatile long generation;

    /**
     * 运行时身份代号（正式化第五阶段 233）。
     *
     * <p>与 {@link #generation} 的分工：{@code generation} 管<b>手动单次预测</b>（保持 232 的原样语义），
     * 本代号管<b>覆盖式预测</b>。任何身份失效（换种子 / 换维度 / 换服 / 退世界 / 关功能 / 关显示）
     * 都会递增它，于是「出发时还是旧身份」的覆盖结果一律被丢弃（口径第二十二节）。</p>
     */
    private volatile long identityEpoch;

    /** 渲染快照重建次数 / 累计耗时（纳秒）：只在客户端主线程写，开发诊断读数。 */
    private long snapshotBuildCount;
    private long snapshotBuildTotalNanos;

    /** 当前运行时身份；{@code null} = 不成立（此时不预测、不观察、不渲染）。 */
    private volatile SeedRuntimeIdentity identity;

    /**
     * <b>自动挖矿（235）对种子预测的需求源</b>。
     *
     * <p>由自动挖矿模块在构造时挂上（{@link #setAutoMiningDemandSource}）：它回答
     * 「自动挖矿现在需不需要种子目标」。只要为真，即使「显示预测钻石」关着，覆盖调度也照常跑
     * —— 这正是本轮要的「两个消费者」语义：渲染器关掉只影响画框，不影响自动挖矿用的预测缓存。</p>
     *
     * <p>默认无需求（没有自动挖矿模块的构建里，行为与 233/234 完全一致）。</p>
     */
    private volatile BooleanSupplier autoMiningDemand = () -> false;

    /** 是否有一个预测在后台跑（含 Worker 正在启动、结果还没回来的窗口）。 */
    private volatile boolean predicting;

    /** 最后一次预测结果；被取消 / 换种子 / 换维度 / 退世界时置空。 */
    private volatile PredictionResult lastResult;

    /** 当前运行时状态（唯一产生者是 {@link #refreshState()}）。 */
    private volatile SeedMiningRuntimeState state = SeedMiningRuntimeState.DISABLED;

    /**
     * 上一次被服务层观察到的计算器进程状态（仅客户端线程访问）。
     *
     * <p>它的唯一用途是「空转期也要发现计算器出了事」：口径第三十三节要求 Worker 意外退出必须
     * 立刻进失败态并在界面提示，而 {@link #onTick()} 原来的刷新只发生在预测期间 —— 于是进程在
     * 空闲时被系统杀掉（OOM、用户手杀、崩溃）会一直显示上一次的旧状态。改成状态一变就重算，
     * 既不每刻做重活，也不会漏掉退出事件。</p>
     */
    private SeedWorkerState observedCalculatorState = SeedWorkerState.STOPPED;

    /** 上一次 tick 看到的观察修订号（仅客户端线程访问；用来决定渲染快照要不要重建）。 */
    private long observedObserverRevision = -1L;

    /** 当前维度键（未进世界为 null）。 */
    private volatile ResourceKey<Level> dimensionKey;

    /** 当前维度标识（{@code minecraft:overworld} 形式，未进世界为空串）。 */
    private volatile String dimensionId = "";

    /** 是否已在世界里（{@code level != null && player != null}）。 */
    private volatile boolean worldReady;

    /** 服务是否已挂载（重复 init 无副作用）。 */
    private volatile boolean installed;

    /** 上一次预测请求的目标区块（仅用于界面回显）；{@code Integer.MIN_VALUE} = 没有。 */
    private volatile int requestChunkX = Integer.MIN_VALUE;
    private volatile int requestChunkZ = Integer.MIN_VALUE;

    // ── 仅客户端主线程访问 ──

    /** 上一次 tick 看到的 {@code Minecraft#level}；换世界 / 换维度 / 退世界都由它识别。 */
    private Level levelRef;

    private SeedMiningService() {
    }

    /** 服务实例（常驻单例）。 */
    public static SeedMiningService instance() {
        return INSTANCE;
    }

    /**
     * 挂载服务；由客户端入口调用一次即可，重复调用无副作用。
     *
     * <p>刻意<b>不</b>依赖任何模块开关：种子挖矿的运行时资源必须在「退出世界」这一刻被清掉，
     * 而那一刻模块可能是关着的。</p>
     */
    public static void init() {
        INSTANCE.install();
    }

    private void install() {
        if (installed) {
            return;
        }
        installed = true;
        loadConfig();
        // 实际 Chunk 观察的发现入口：Fabric 官方客户端区块加载 / 卸载事件（口径第二十四节优先官方事件）
        SeedOreObservationTracker.installHooks();
        ClientEventBus.subscribe(TICK_OWNER, ClientEventType.TICK, event -> onTick());
        // 客户端退出必须带走 Worker 进程（口径第三十二、七十九节：绝不留下孤儿 Java 进程）
        Runtime.getRuntime().addShutdownHook(
                new Thread(INSTANCE::shutdownForExit, "yiyiaddon-seed-calculator-shutdown"));
        LOGGER.info("{}：运行时服务已挂载（本地世界生成计算器 + 预测缓存 / 覆盖调度 / 实际区块观察 / 世界渲染；"
                + "后台单线程；配置项 启用/种子/显示预测钻石/预测范围/显示当前缺失）", LOG_KEY);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 配置
    // ────────────────────────────────────────────────────────────────────────

    /** 「启用种子挖矿」开关。 */
    public boolean enabled() {
        return config.enabled();
    }

    /**
     * 设置「启用种子挖矿」开关。
     *
     * <p>关闭 → 取消在途任务、清空结果、<b>直接停掉本地世界生成计算器</b>（口径第六十八节：
     * 第一版不做空闲复用）；开启 → 只重新评估状态，不自动开始预测。</p>
     */
    public void setEnabled(boolean value) {
        if (config.enabled() == value) {
            return;
        }
        config.enabled(value);
        persist();
        LOGGER.info("{}：启用开关改为 {}", LOG_KEY, value ? "开" : "关");
        if (value) {
            refreshState();
            return;
        }
        cancelPending();
        invalidateRuntime("功能关闭");
        stopCalculator("功能关闭");
        refreshState();
    }

    /** 服务器种子原文（界面输入框直接读写它）。 */
    public String seedText() {
        return config.seedText();
    }

    /**
     * 改写服务器种子原文（输入框每敲一个字符都会走到这里）。
     *
     * <p>口径第二十九节：保存配置 → 取消在途请求（{@link #generation} 递增，旧响应永不许回写）→
     * 关闭 Worker 侧会话（旧种子的离线世界立即释放）→ 清空旧结果 → 重新落到「已就绪 / 格式无效」。</p>
     */
    public void setSeedText(String text) {
        String next = text == null ? "" : text;
        if (Objects.equals(config.seedText(), next)) {
            return;
        }
        Long oldSeed = parsedSeed;
        config.seedText(next);
        parsedSeed = SeedMiningConfig.parseSeed(config.seedText());
        persist();
        cancelPending();
        // 口径第四十一节：种子一改，身份即变 —— 旧 pending / 缓存 / 观察 / 渲染快照全部作废，
        // 旧结果即使回来了也没有身份可写（identityEpoch 识别）
        invalidateRuntime("种子已修改");
        if (oldSeed != null) {
            // 关会话要拿 Worker 侧预测器的锁，绝不能放在渲染线程上等
            submitQuietly(calculator::closeSession);
        }
        LOGGER.info("{}：种子已修改（状态：{}）", LOG_KEY, config.seedStatusCn());
        refreshState();
    }

    /** 种子输入状态文案（未填写 / 格式无效 / 已填写；不含任何「已验证」结论）。 */
    public String seedStatusCn() {
        return config.seedStatusCn();
    }

    /** 当前合法种子；未填写或格式不合法返回 {@code null}。 */
    public Long seedValue() {
        return parsedSeed;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 配置：种子预测渲染器（正式化第五阶段 233）
    // ────────────────────────────────────────────────────────────────────────

    /** 「显示预测钻石」开关（口径第三十七节）。 */
    public boolean renderPrediction() {
        return config.renderPrediction();
    }

    /**
     * 设置「显示预测钻石」。
     *
     * <p><b>235 起它只是渲染开关</b>（口径：Renderer 与 Coverage 解耦）：关掉时只注销渲染层、
     * 清渲染快照（世界里一个框都不留），<b>不</b>再清预测缓存 / 覆盖队列 / 观察状态 ——
     * 自动挖矿可能正拿着这些预测在挖矿。整个运行时只在「启用种子挖矿」关闭或身份失效时停止。</p>
     */
    public void setRenderPrediction(boolean value) {
        if (config.renderPrediction() == value) {
            return;
        }
        config.renderPrediction(value);
        persist();
        LOGGER.info("{}：显示预测钻石改为 {}", LOG_KEY, value ? "开" : "关");
        // 立刻生效（渲染线程每帧读 volatile renderActive），下一 tick 的 syncRuntime 再收敛其余状态
        applyRendererAttachment();
        refreshState();
    }

    /** 预测覆盖半径（区块）；取值域由覆盖调度器统一判定。 */
    public int coverageRadius() {
        return SeedPredictionCoverageController.clampRadius(config.coverageRadius());
    }

    /** 设置预测覆盖半径（自动夹进 1~6）。 */
    public void setCoverageRadius(int value) {
        int clamped = SeedPredictionCoverageController.clampRadius(value);
        if (coverageRadius() == clamped) {
            return;
        }
        config.coverageRadius(clamped);
        persist();
        LOGGER.info("{}：预测范围改为 {} 区块（目标区块最多 {} 个）", LOG_KEY, clamped, (clamped * 2 + 1) * (clamped * 2 + 1));
        refreshState();
    }

    /** 覆盖半径下限（界面与设置同源）。 */
    public static int coverageRadiusMin() {
        return SeedPredictionCoverageController.RADIUS_MIN;
    }

    /** 覆盖半径上限（界面与设置同源；口径第十九节上限 6，不默认 8）。 */
    public static int coverageRadiusMax() {
        return SeedPredictionCoverageController.RADIUS_MAX;
    }

    /** 是否把「当前缺失」画到世界里（默认关）。 */
    public boolean showMissing() {
        return config.showMissing();
    }

    /** 设置「显示当前缺失」。 */
    public void setShowMissing(boolean value) {
        if (config.showMissing() == value) {
            return;
        }
        config.showMissing(value);
        persist();
        LOGGER.info("{}：显示当前缺失改为 {}", LOG_KEY, value ? "开" : "关");
        showMissingMirror = value;
        renderer.markDirty();
        refreshState();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 配置：要预测的矿物集合（正式化第八阶段 236）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 当前维度支持的矿物清单（界面按它渲染多选；顺序即展示序）。
     *
     * <p>维度未支持时返回空表 —— 界面据此显示「当前维度暂未支持」。</p>
     */
    public List<OreType> supportedOres() {
        SeedDimensionProfile profile = dimensionProfile();
        return profile == null ? List.of() : SeedOreRegistry.oresOf(profile);
    }

    /**
     * 当前维度下<b>真正生效</b>的矿物集合（覆盖调度就用它）。
     *
     * <p>两条规则：</p>
     * <ol>
     *     <li>取「用户勾选」与「本维度支持」的交集；</li>
     *     <li>交集为空时回落到本维度的<b>第一种</b>矿物（主世界=钻石、下界=远古残骸）——
     *         否则用户刚进下界会因为默认勾的是钻石而看不到任何预测，容易误判成「下界不支持」。</li>
     * </ol>
     */
    public List<OreType> effectiveOres() {
        SeedDimensionProfile profile = dimensionProfile();
        if (profile == null) {
            return List.of();
        }
        List<OreType> supported = SeedOreRegistry.oresOf(profile);
        List<OreType> effective = config.effectiveOres(supported);
        if (!effective.isEmpty()) {
            return effective;
        }
        return List.of(supported.get(0));
    }

    /** 手动「测试当前区块预测」用的主矿物（生效集合里的第一个）。 */
    public OreType primaryOre() {
        List<OreType> effective = effectiveOres();
        return effective.isEmpty() ? null : effective.get(0);
    }

    /** 该矿物此刻是否被勾选（界面勾选框读它）。 */
    public boolean isOreSelected(OreType oreType) {
        return config.isOreSelected(oreType);
    }

    /**
     * 勾选 / 取消一个矿物。
     *
     * <p>切集合会改变覆盖调度的目标集合，但<b>不</b>清缓存、不换身份：下一个 tick 的
     * {@code replan} 会重算目标顺序（已经算过的目标直接从缓存复用）。</p>
     */
    public void setOreSelected(OreType oreType, boolean selected) {
        if (oreType == null) {
            return;
        }
        boolean current = config.isOreSelected(oreType);
        if (current == selected) {
            return;
        }
        List<OreType> next = new ArrayList<>(config.selectedOres());
        if (selected) {
            next.add(oreType);
        } else {
            next.remove(oreType);
        }
        config.selectedOres(next);
        persist();
        LOGGER.info("{}：矿物勾选变化 {} → {}（当前生效集合 {}）", LOG_KEY, oreType.displayNameCn(),
                selected ? "已选" : "未选", describeOresCn(effectiveOres()));
        refreshState();
    }

    /** 矿物集合的中文清单。 */
    public static String describeOresCn(List<OreType> ores) {
        if (ores == null || ores.isEmpty()) {
            return "无";
        }
        StringBuilder builder = new StringBuilder();
        for (OreType oreType : ores) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(oreType.displayNameCn());
        }
        return builder.toString();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 读数：预测缓存 / 覆盖 / 观察（正式化第五阶段 233）
    // ────────────────────────────────────────────────────────────────────────

    /** 渲染快照（界面统计与渲染层共用同一份；重建在服务层的 {@link #onTick()}）。 */
    public SeedRenderSnapshot renderSnapshot() {
        return renderer.snapshot();
    }

    /** 观察状态快照（候选 / 未观察 / 已确认 / 当前缺失；不含可疑计数）。 */
    public SeedObservationSnapshot observationSnapshot() {
        return observer.snapshot();
    }

    /**
     * 某个位置最近一次看到的真实方块注册名（开发诊断 / 回归装置取证据用；从未看到返回空串）。
     *
     * <p>正式界面不显示它，避免把「服务器实际给了什么方块」当成结论展示（口径第十一、四十八节）。</p>
     */
    public String observedBlockId(BlockPos pos) {
        return observer.lastObservedBlockId(pos);
    }

    /** 某个位置当前的观察状态（开发诊断 / 回归装置用）。 */
    public OreObservationState observationState(BlockPos pos) {
        return observer.stateOf(pos);
    }

    /** 预测缓存规模（目标区块数）。 */
    public int cachedChunkCount() {
        return repository.size();
    }

    /** 预测缓存上限（界面说明用）。 */
    public int cachedChunkLimit() {
        return SeedPredictionRepository.MAX_TARGET_CHUNKS;
    }

    /** 覆盖进度分子：已经拿到正式结果、且落在当前覆盖范围内的任务数（区块 × 矿物）。 */
    public int coveragePredictedCount() {
        return coverage.predictedInCoverage(repository, effectiveOres());
    }

    /** 覆盖进度分母：当前覆盖范围内的任务总数（{@code (2r+1)² × 生效矿物数}）。 */
    public int coverageTargetCount() {
        return coverage.desiredCount();
    }

    /** 排队中的任务数。 */
    public int coveragePendingCount() {
        return coverage.pendingCount();
    }

    /**
     * 覆盖调度是否还会产出新的预测（235：自动挖矿用它区分「再等等」与「这片区域没有目标了」）。
     *
     * <p>{@code true} = 还有任务在排队或正在 Worker 上跑；{@code false} = 当前覆盖方框已经收敛
     * （全部出结果，或剩下的都在失败态不再重试）。</p>
     */
    public boolean coverageStillWorking() {
        return coverage.pendingCount() > 0 || coverage.activeTask() != null;
    }

    /** 正在 Worker 上预测的任务显示文本（空闲为「—」）。 */
    public String coverageActiveChunkCn() {
        return coverage.activeTaskCn();
    }

    /** 运行时身份的一行摘要（界面诊断行；未成立时返回「未建立」）。 */
    public String runtimeIdentityCn() {
        SeedRuntimeIdentity current = identity;
        return current == null ? "未建立" : current.describeCn();
    }

    /** 预测缓存 + 覆盖调度的一行诊断（日志用）。 */
    public String runtimeDiagnosticsCn() {
        return repository.describeCn() + "；" + coverage.describeCn() + "；观察 " + observer.describeCn()
                + "；渲染 " + renderer.snapshot().describeCn() + "；" + validation.describeCn();
    }

    /** 渲染快照重建次数（开发诊断 / 性能记录用）。 */
    public long snapshotBuildCount() {
        return snapshotBuildCount;
    }

    /**
     * 渲染快照累计重建耗时（纳秒；开发诊断 / 性能记录用）。
     *
     * <p>用来回答「预测框多了以后，每刻重建一次快照要花多少 CPU」这一问：由于只在
     * 「观察层有变化」时才重建（口径第二十六节），它天然不是每帧成本。窗口内的平均耗时 =
     * 本读数的增量 ÷ {@link #snapshotBuildCount()} 的增量。</p>
     */
    public long snapshotBuildTotalNanos() {
        return snapshotBuildTotalNanos;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 读数：种子验证（正式化第六阶段 234）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 种子验证快照（界面唯一读数入口，口径第七十三节）。
     *
     * <p>界面<b>不得</b>自己计算验证逻辑，只读这份不可变读数。</p>
     */
    public SeedValidationSnapshot validationSnapshot() {
        return validation.snapshot();
    }

    /**
     * <b>自动挖矿的唯一正式安全门</b>（口径第十节）。
     *
     * <p>只有验证状态为「已验证」才返回 {@code true}；其余状态一律 {@code false}。</p>
     *
     * <p><b>236 追加的维度闸门</b>：本方法只在<b>主世界</b>成立。下界一律 {@code false}，
     * 因为「主世界验证通过」不等于「下界 worldgen 也验证通过」—— 服务器完全可以是
     * 主世界原版 + 下界自定义。下界专属证据建立之前，这条门不许开
     * （见 {@link #netherAutoMiningAllowedCn()} 与 {@link #mayUseForAutomatedMining(OreType)}）。</p>
     */
    public boolean mayUseForAutomatedMining() {
        return dimensionProfile() == SeedDimensionProfile.OVERWORLD && validation.mayUseForAutomatedMining();
    }

    /**
     * 某个矿物能否进入自动挖矿（236）。
     *
     * <p>三道门同时成立才为真：</p>
     * <ol>
     *     <li><b>维度</b>：只有主世界（下界恒 false）；</li>
     *     <li><b>矿物资格</b>：{@link SeedOreRegistry#autoMinerEligible} —— 236 只有钻石为 true
     *         （235 已过 A~L 实机验证），其余矿物<b>尚未建立</b>「候选 ↔ 真实 BlockState」对照证据；</li>
     *     <li><b>验证状态</b>：当前会话的种子验证为「已验证」。</li>
     * </ol>
     */
    public boolean mayUseForAutomatedMining(OreType oreType) {
        SeedDimensionProfile profile = dimensionProfile();
        if (profile != SeedDimensionProfile.OVERWORLD || oreType == null) {
            return false;
        }
        return SeedOreRegistry.autoMinerEligible(profile, oreType) && validation.mayUseForAutomatedMining();
    }

    /**
     * 下界自动挖矿闸门的中文说明（<b>恒为关闭</b>）。
     *
     * <p>236 的口径：下界只开放 预测 / 观察 / ESP，自动挖矿一律 fail-closed。
     * 理由不是「下界算法没实现」（算法已实现并用真实 26.1.2 参数验证过预测链路），
     * 而是<b>验证证据</b>层面：某一台服务器的主世界是原版、下界被替换是完全可能的，
     * 而当前还没有任何「下界候选 ↔ 下界真实 BlockState」的实机对照证据。</p>
     */
    public String netherAutoMiningAllowedCn() {
        return NETHER_AUTOMINER_ENABLED
                ? "已开启（存在下界专属验证证据）"
                : "关闭（fail-closed）：尚未建立下界专属验证证据；下界当前只提供预测 / 观察 / ESP";
    }

    /**
     * 自动挖矿（235 的第二个消费者）此刻应当追的矿物。
     *
     * <p>规则：生效集合里第一个<b>允许进自动挖矿</b>的矿物；一个都没有返回 {@code null}
     * （调用方必须 fail-closed，不许退回「按矿物类型全局搜」）。</p>
     *
     * <p>236 的实际效果：主世界默认（只勾钻石）⇒ 返回钻石，与 235 逐字一致；
     * 若用户只勾了红石等尚未开放自动挖矿的矿物 ⇒ 返回 {@code null}，自动挖矿如实停机并说明原因。</p>
     */
    public OreType autoMiningTargetOre() {
        SeedDimensionProfile profile = dimensionProfile();
        if (profile == null) {
            return null;
        }
        for (OreType oreType : effectiveOres()) {
            if (SeedOreRegistry.autoMinerEligible(profile, oreType)) {
                return oreType;
            }
        }
        return null;
    }

    /** 当前验证证据属于哪个维度（开发诊断 / 报告用；未绑定返回空串）。 */
    public String validationDimensionId() {
        return validation.evidenceDimensionId();
    }

    /** 本阶段允许进自动挖矿的矿物清单（失败原因文案 / 报告用；236 在主世界是「钻石」）。 */
    public String autoMinerEligibleOresCn() {
        SeedDimensionProfile profile = dimensionProfile();
        if (profile == null) {
            return "无";
        }
        List<OreType> eligible = new ArrayList<>();
        for (OreType oreType : SeedOreRegistry.oresOf(profile)) {
            if (SeedOreRegistry.autoMinerEligible(profile, oreType)) {
                eligible.add(oreType);
            }
        }
        return describeOresCn(eligible);
    }

    /**
     * 「重新开始验证」（口径第四十一节）：清空当前 runtime 验证证据并从此刻重新收集。
     *
     * <p>它只作用于验证层：不清预测缓存、不清观察层、不改种子、不碰世界。
     * 由于验证证据必须来自观察样本，清空后需要产生<b>新的</b>观察才会重新累积证据
     * （重新加载区块或发生方块更新）。</p>
     *
     * @return 被清掉的证据条数
     */
    public int restartValidation() {
        return validation.restart();
    }

    /** 种子验证的一行诊断（日志 / 报告用）。 */
    public String validationDiagnosticsCn() {
        return validation.describeCn();
    }

    /**
     * 当前验证证据清单（<b>开发诊断 / 回归装置取证据用</b>；正式界面不显示它）。
     *
     * <p>回归装置需要逐条读到「位置 / 目标区块 / 确定性 / 来源 / 写入者 / 观察状态 / 实际方块 /
     * 首次与最近观察时间」这些字段（口径第二十七节要求逐项输出），因此这里按只读副本暴露；
     * 界面侧仍然只读 {@link #validationSnapshot()}。</p>
     */
    public java.util.List<SeedValidationEvidence> validationEvidence() {
        return validation.evidenceList();
    }

    /** 当前有效证据单元清单（开发诊断 / 回归装置用）。 */
    public java.util.List<SeedValidationEvidenceGroup> validationGroups() {
        return validation.groups();
    }

    /**
     * 逐单元诊断明细（<b>dev-only</b>；口径第十三节要求把每个已确认单元的 provenance 与全部成员坐标
     * 打印成一组，便于事后追溯「N 个单元究竟来自几次世界生成事件」）。
     */
    public java.util.List<String> validationUnitDiagnostics(boolean confirmedOnly) {
        return validation.unitDiagnosticsCn(confirmedOnly);
    }

    /**
     * 预测缓存里的全部正式结果（<b>开发诊断 / 回归装置取证据用</b>）。
     *
     * <p>回归装置要逐字段输出某个候选的完整记录（确定性 / 来源 / 写入者 / 冲突写入者），
     * 那些字段只在 {@code PredictedOre} 上，因此这里按只读副本暴露；正式界面不读它。</p>
     */
    public java.util.Collection<PredictionResult> cachedPredictions() {
        return repository.results();
    }

    /** 硬冲突判据的可用性说明（报告用；当前必为「不可达」）。 */
    public String validationConflictAvailabilityCn() {
        return validation.conflictAvailabilityCn();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 读数：自动挖矿接入（正式化第七阶段 235）
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 挂上「自动挖矿需不需要种子预测」的需求源（235）。
     *
     * <p>只有一个消费者会调用它（自动挖矿模块），因此不需要多播：本类只关心「有没有人在用」。
     * 传 {@code null} 等价于撤销需求源（回到「没有自动挖矿消费者」的 233/234 行为）。</p>
     */
    public void setAutoMiningDemandSource(BooleanSupplier source) {
        this.autoMiningDemand = source == null ? () -> false : source;
        refreshState();
    }

    /**
     * 当前运行时身份；未建立（未启用 / 未进世界 / 种子不合法 / 非主世界 / 已失效）返回 {@code null}。
     *
     * <p>235 的自动挖矿目标提供者用它做生命周期判据：身份对象一变（改种子 / 换服 / 换维度 / 退世界
     * 都会在 {@link #invalidateRuntime(String)} 里换号并重建），旧选出来的目标必须<b>立刻</b>作废。</p>
     */
    public SeedRuntimeIdentity runtimeIdentity() {
        return identity;
    }

    /**
     * 自动挖矿消费者此刻是否在要预测（内部判据；需求源抛异常一律按「不要」处理，绝不拖垮主循环）。
     */
    private boolean autoMiningConsumerDemand() {
        try {
            return autoMiningDemand.getAsBoolean();
        } catch (Throwable ignored) {
            return false;
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 环境（维度）
    // ────────────────────────────────────────────────────────────────────────

    /** 当前维度标识（{@code minecraft:overworld} 形式）；未进世界为空串。 */
    public String dimensionId() {
        return dimensionId;
    }

    /** 当前维度的中文显示名（主世界 / 下界 / 末地 / 自定义维度：{@code <id>}）。 */
    public String dimensionDisplayCn() {
        String id = dimensionId();
        if (id.isEmpty()) {
            return "未进入世界";
        }
        String name = WorldIdentity.dimensionDisplayName(id);
        return "自定义维度".equals(name) ? name + "：" + id : name;
    }

    /** 当前维度的支持状态文案（236：按当前维度的真实矿物清单给出，不再写死一句「只支持主世界钻石」）。 */
    public String dimensionSupportCn() {
        SeedDimensionProfile profile = dimensionProfile();
        if (profile == null) {
            return dimensionId().isEmpty() ? "等待进入世界" : "当前维度的世界生成规则暂未支持";
        }
        return "支持：" + SeedOreRegistry.describeSupportedOresCn(profile) + " 预测";
    }

    /** 当前维度是否被支持（主世界 / 下界）。 */
    public boolean dimensionSupported() {
        return dimensionProfile() != null;
    }

    /** 当前维度档案；未进世界 / 不支持的维度返回 {@code null}。 */
    public SeedDimensionProfile dimensionProfile() {
        if (!worldReady) {
            return null;
        }
        String id = dimensionId;
        for (SeedDimensionProfile profile : SUPPORTED_DIMENSIONS) {
            if (profile.dimensionId().equals(id)) {
                return profile;
            }
        }
        return null;
    }

    /**
     * 「预测模型」声明。
     *
     * <p>它回答的是「我们按哪一套世界生成规则算的」，<b>不是</b>「服务器一定用了这套规则」。
     * 236 起按当前维度给出：主世界与下界是两套预设、两套噪声设置（下界还换随机算法，
     * 见 {@code SeedDimensionProfile}）。</p>
     */
    public String predictModelCn() {
        SeedDimensionProfile profile = dimensionProfile();
        if (profile == null) {
            return "Minecraft " + minecraftVersion() + "（当前维度未支持）";
        }
        return "Minecraft " + minecraftVersion() + " 原版" + profile.displayNameCn();
    }

    /** 「预测模型」的适用范围小字（界面与报告同源，避免把「能跑」误读成「已验证」）。 */
    public String predictModelNoteCn() {
        return "预测模型只声明「按哪套规则算」：尚未验证服务器是否使用自定义世界生成。"
                + "服务器若使用数据包 / 插件生成器 / 改 worldgen 的 Mod，本预测不保证成立。";
    }

    // ────────────────────────────────────────────────────────────────────────
    // 运行时状态
    // ────────────────────────────────────────────────────────────────────────

    /** 当前运行时状态。 */
    public SeedMiningRuntimeState state() {
        return state;
    }

    /** 当前运行时状态的中文文案。 */
    public String stateCn() {
        return state.displayNameCn();
    }

    /** 是否有预测在后台跑（含计算器正在启动）。 */
    public boolean predicting() {
        return predicting;
    }

    /** 现在能不能发起「测试当前区块预测」。 */
    public boolean canPredict() {
        return config.enabled() && worldReady && !predicting && parsedSeed != null && dimensionSupported();
    }

    /** 上一次预测结果；从未预测 / 已被取消返回 {@code null}。 */
    public PredictionResult lastResult() {
        return lastResult;
    }

    /** 本地世界生成计算器是否正在运行（退出世界 / 关闭功能之后必须为 false）。 */
    public boolean calculatorRunning() {
        return calculator.processRunning();
    }

    /**
     * 计算器进程状态的中文文案（<b>开发诊断用</b>；正式界面只显示业务文案，口径第七十四节）。
     */
    public String calculatorStateCn() {
        return calculator.state().displayNameCn();
    }

    /** 计算器进程的一行诊断（只进日志与开发装置）。 */
    public String calculatorDiagnosticsCn() {
        return calculator.diagnosticsCn();
    }

    /** 计算器进程 PID（诊断；没有进程时 -1。口径第七十五节：PID 只进诊断，不进正式界面）。 */
    public long calculatorPid() {
        return calculator.workerPid();
    }

    /** 计算器累计启动次数（诊断；崩溃后自动重启会 +1，是「重启了一次」的直接证据）。 */
    public int calculatorStartCount() {
        return calculator.startCount();
    }

    /** 计算器最近一次错误的中文原因（没有错误返回空串）；用于界面「失败原因」行。 */
    public String calculatorErrorCn() {
        return calculator.lastErrorCn();
    }

    /** 预测失败原因（成功或没有结果返回空串）；已由正式层给出中文，界面直接显示。 */
    public String failureCn() {
        PredictionResult result = lastResult;
        if (result == null || result.success() || result.failureReason() == null) {
            return "";
        }
        return result.failureReason();
    }

    /** 玩家当前所在区块的显示文本（{@code 12, -3}）；未进世界返回「未进入世界」。 */
    public String playerChunkCn() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return "未进入世界";
        }
        BlockPos pos = client.player.blockPosition();
        return (pos.getX() >> 4) + ", " + (pos.getZ() >> 4);
    }

    /** 上一次预测的目标区块显示文本（{@code 12, -3}）；没有结果返回「—」。 */
    public String predictedChunkCn() {
        if (requestChunkX == Integer.MIN_VALUE) {
            return "—";
        }
        return requestChunkX + ", " + requestChunkZ;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 用户动作：预测
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 预测玩家当前所在区块（界面按钮的唯一入口）。
     *
     * <p>必须由客户端主线程调用。真正的世界生成在后台线程 + 隔离进程里执行，
     * 本方法只做「取参数 → 校验 → 提交」，因此点击不会卡住界面（口径第二十一节）。</p>
     */
    public void predictCurrentChunk() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }
        BlockPos pos = client.player.blockPosition();
        predictChunk(pos.getX() >> 4, pos.getZ() >> 4);
    }

    /**
     * 预测指定的目标区块。
     *
     * <p>与 {@link #predictCurrentChunk()} 是同一条链路，只是目标由调用方给出：
     * 开发者回归装置用它来核对固定目标（口径第四十二~四十八节要求「远端未加载区块也能预测」，
     * 而实机测试时玩家并不在那个区块里）。</p>
     */
    public void predictChunk(int chunkX, int chunkZ) {
        if (!canPredict()) {
            return;
        }
        Long seed = parsedSeed;
        SeedDimensionProfile profile = dimensionProfile();
        OreType oreType = primaryOre();
        if (seed == null || profile == null || oreType == null) {
            return;
        }
        ChunkPos chunk = new ChunkPos(chunkX, chunkZ);
        long id = ++generation;
        requestChunkX = chunk.x();
        requestChunkZ = chunk.z();
        lastResult = null;
        predicting = true;
        refreshState();
        LOGGER.info("{}：提交预测 种子 {} {} 区块 ({},{})（后台线程 + 本地隔离计算器，渲染线程不参与）",
                LOG_KEY, seed, oreType.displayNameCn(), chunk.x(), chunk.z());
        submitQuietly(() -> runPredict(id, seed, profile, oreType, chunk));
    }

    /** 客户端退出时收尾（JVM 关闭钩子调用；有上界，绝不无限阻塞退出）。 */
    public void shutdownForExit() {
        LOGGER.info("{}：客户端正在退出，开始回收本地世界生成计算器", LOG_KEY);
        Thread stopper = new Thread(calculator::stop, "yiyiaddon-seed-calculator-stop");
        stopper.setDaemon(true);
        stopper.start();
        try {
            stopper.join(10_000L);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 后台线程
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 后台跑一次预测：启动计算器（懒启动）→ 打开会话 → 预测。
     *
     * <p>三段都会重新校验任务代号：期间发生「换种子 / 换维度 / 退世界 / 关功能」时，
     * 后面的步骤直接放弃，结果也不回写（口径第二十一、二十九节）。</p>
     */
    private void runPredict(long id, long seed, SeedDimensionProfile profile, OreType oreType, ChunkPos chunk) {
        if (id != generation) {
            LOGGER.info("{}：排队中的预测已被取消，直接丢弃（种子 {} {} 区块 ({},{}))",
                    LOG_KEY, seed, oreType.displayNameCn(), chunk.x(), chunk.z());
            return;
        }
        String dimensionId = profile.dimensionId();
        long startedAt = System.currentTimeMillis();
        PredictionResult result;
        try {
            calculator.ensureStarted(minecraftVersion(), modVersion());
            if (id != generation) {
                LOGGER.info("{}：计算器就绪时该请求已被取消，丢弃（种子 {} 区块 ({},{}))",
                        LOG_KEY, seed, chunk.x(), chunk.z());
                return;
            }
            calculator.ensureSession(seed, dimensionId);
            if (id != generation) {
                LOGGER.info("{}：会话就绪时该请求已被取消，丢弃（种子 {} 区块 ({},{}))",
                        LOG_KEY, seed, chunk.x(), chunk.z());
                return;
            }
            result = calculator.predict(seed, dimensionId, oreType, chunk);
        } catch (Throwable error) {
            // 异常绝不外泄到客户端主线程：转成正式层的失败结果（失败 ≠ 没有矿）
            LOGGER.error("{}：预测失败（种子 {} {} 区块 ({},{}))", LOG_KEY, seed,
                    oreType.displayNameCn(), chunk.x(), chunk.z(), error);
            result = PredictionResult.failure(SeedOreTarget.of(seed, profile.levelKey(), chunk, oreType),
                    describeFailure(error), System.currentTimeMillis() - startedAt);
        }
        deliver(id, result, seed, chunk);
    }

    /** 把结果投递回客户端主线程；只有仍然是当前任务的结果才允许落地。 */
    private void deliver(long id, PredictionResult result, long seed, ChunkPos chunk) {
        try {
            Minecraft.getInstance().execute(() -> {
                if (id != generation) {
                    LOGGER.info("{}：预测已完成但已被取消，结果丢弃（种子 {} 区块 ({},{}))",
                            LOG_KEY, seed, chunk.x(), chunk.z());
                    return;
                }
                predicting = false;
                lastResult = result;
                if (result.failed()) {
                    LOGGER.warn("{}：预测失败 种子 {} 区块 ({},{}) → {}",
                            LOG_KEY, seed, chunk.x(), chunk.z(), result.failureReason());
                } else {
                    LOGGER.info("{}：预测成功 种子 {} 区块 ({},{}) → {} 个"
                                    + "（调度敏感 {} / 未解析 {} / 确定性 {}），耗时 {} ms，缓存区块 {}，"
                                    + "宿主 ChunkMap 查询 {}（必须为 0）",
                            LOG_KEY, seed, chunk.x(), chunk.z(), result.count(),
                            result.scheduleSensitiveCount(), result.unresolvedCount(),
                            result.deterministicCount(), result.elapsedMillis(),
                            result.stats().heldChunks(), result.stats().hostChunkSourceQueries());
                    // 手动预测的正式结果同样进预测缓存：它和覆盖式预测是同一类东西，
                    // 因此观察与渲染都能看到它（口径第十五节：Repository 保存的就是「Worker 已返回的正式结果」）
                    acceptPrediction(result, "手动");
                }
                LOGGER.info("{}：计算器诊断 {}", LOG_KEY, calculator.diagnosticsCn());
                refreshState();
            });
        } catch (Throwable error) {
            // 客户端已在收尾：不扩散异常，状态由下一次 tick 的「离开世界」分支收尾
            LOGGER.warn("{}：结果回投失败（客户端可能正在关闭）", LOG_KEY, error);
        }
    }

    /** 投递一个后台动作；线程已不可用时只记日志（绝不抛回调用方）。 */
    private void submitQuietly(Runnable task) {
        try {
            worker.execute(task);
        } catch (RejectedExecutionException rejected) {
            LOGGER.warn("{}：后台线程已不可用，任务被放弃", LOG_KEY);
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 世界 / 维度生命周期
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 每刻的低成本检查。
     *
     * <p>只做三件事：{@code level} 引用变没变、玩家有没有就绪、维度键变没变。
     * 另外在预测期间每刻刷新一次状态，让「正在启动本地世界生成计算器」能自动推进到
     * 「正在预测当前区块」（读的是计算器的 volatile 状态）。</p>
     *
     * <p><b>正式化第五阶段（233）追加的三件轻活</b>：同步运行时身份（{@link #syncRuntime()}）、
     * 推进覆盖调度（{@link #driveCoverage()}）、按脏标记重建渲染快照。三者都是 O(1) 判断 +
     * 最多一次 O(候选数) 重建，且<b>都不在渲染帧里做</b>（口径第二十七、三十二、五十一节）。</p>
     */
    private void onTick() {
        Minecraft client = Minecraft.getInstance();
        Level level = client.level;
        if (level != levelRef) {
            levelRef = level;
            onLevelChanged(client, level);
            return;
        }
        if (level == null) {
            return;
        }
        boolean ready = client.player != null;
        if (ready != worldReady) {
            worldReady = ready;
            refreshState();
        }
        ResourceKey<Level> key = level.dimension();
        ResourceKey<Level> known = dimensionKey;
        if (known != null && !known.equals(key)) {
            onDimensionChanged(key);
            return;
        }
        // 233：身份同步 + 覆盖调度 + 渲染快照重建（都在客户端主线程）
        syncRuntime();
        driveCoverage();
        // 观察这一维变了（区块加载 / 卸载 / 候选方块被更新）→ 标脏，本刻末统一重建一次快照
        if (observer.revision() != observedObserverRevision) {
            observedObserverRevision = observer.revision();
            // 234：验证只消费「预测 + 观察」，因此与渲染快照共用同一个「观察变了」的触发点，
            // 既不多跑一次 O(候选数) 的活，也不会漏掉任何一次新观察（口径第七十四节）
            validation.update(repository.results(), observer);
            renderer.markDirty();
        }
        // 快照只在渲染层真的挂着时才重建（235）：关掉「显示预测钻石」后自动挖矿仍在消费预测，
        // 但那一层一个框都不画 —— 再每刻重建 O(候选数) 的快照纯属白做（重开时会由
        // applyRendererAttachment 标脏并立刻补建一次，世界里不会因此空着）
        if (renderer.attached() && renderer.dirty()) {
            long buildStart = System.nanoTime();
            SeedRenderSnapshot built = SeedRenderSnapshot.build(repository, observer);
            long buildCost = System.nanoTime() - buildStart;
            renderer.updateSnapshot(built);
            // 快照重建成本读数（开发诊断 / 性能记录用：正式渲染路径不受影响）
            snapshotBuildCount++;
            snapshotBuildTotalNanos += buildCost;
        }
        if (predicting) {
            refreshState();
            return;
        }
        // 空转期只做一件轻活：盯住计算器进程的状态变化（READY → FAILED/STOPPED 等）。
        // 进程意外退出必须马上反映到界面（口径第三十三节），但状态没变时绝不重算。
        SeedWorkerState calculatorState = calculator.state();
        if (calculatorState != observedCalculatorState) {
            observedCalculatorState = calculatorState;
            refreshState();
        }
    }

    /** {@code Minecraft#level} 换了对象：退世界 / 进世界 / 换维度 / 换服都在这里收口。 */
    private void onLevelChanged(Minecraft client, Level level) {
        cancelPending();
        // 233：换世界对象（含换服 A → B、退世界、换维度）必须让运行时身份整体失效，
        // 否则 B 世界会短暂闪出 A 的预测框（口径第四十二条、Server A→B）
        invalidateRuntime(level == null ? "退出世界" : "进入世界 / 换服");
        if (level == null) {
            stopCalculator("退出世界");
            worldReady = false;
            dimensionKey = null;
            dimensionId = "";
            configScope = null;
            LOGGER.info("{}：已离开世界，计算结果已清除、计算器已停止（配置保留）", LOG_KEY);
        } else {
            worldReady = client.player != null;
            dimensionKey = level.dimension();
            dimensionId = dimensionKey.identifier().toString();
            // 新世界 = 新的重试预算（口径第三十三节：允许下一次点击自动重启一次）
            calculator.resetRestartGuard();
            String scope = client.player == null ? null : WorldIdentity.fileSafeServer();
            if (!Objects.equals(scope, configScope)) {
                configScope = scope;
                loadConfig();
                LOGGER.info("{}：配置作用域切换为 {}", LOG_KEY,
                        scope == null ? "（未进入世界，按全局模板）" : scope);
            }
            LOGGER.info("{}：进入世界 维度={}（世界生成宿主由本地隔离计算器提供）", LOG_KEY, dimensionId);
        }
        refreshState();
    }

    /** 维度真的换了：旧维度的在途任务与结果一律作废，Worker 侧会话关闭（口径第三十节）。 */
    private void onDimensionChanged(ResourceKey<Level> key) {
        dimensionKey = key;
        dimensionId = key.identifier().toString();
        cancelPending();
        // 233：下界 / 末地一律停止覆盖并清空预测、观察与渲染（口径第四十二条）
        invalidateRuntime("维度切换");
        submitQuietly(calculator::closeSession);
        LOGGER.info("{}：维度切换为 {}，上一维度的结果已清除、计算器会话已关闭", LOG_KEY, dimensionId);
        refreshState();
    }

    /** 取消在途任务 + 清空结果（不停止计算器，也不碰配置）。 */
    private void cancelPending() {
        generation++;
        predicting = false;
        lastResult = null;
        requestChunkX = Integer.MIN_VALUE;
        requestChunkZ = Integer.MIN_VALUE;
    }

    /** 停止本地世界生成计算器（关闭功能 / 退出世界 / 换服；口径第三十一、六十八节）。 */
    private void stopCalculator(String reason) {
        if (!calculator.processRunning() && calculator.state() == SeedWorkerState.STOPPED) {
            return;
        }
        LOGGER.info("{}：停止本地世界生成计算器（原因：{}）", LOG_KEY, reason);
        submitQuietly(calculator::stop);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 正式化第五阶段（233）：运行时身份 / 覆盖调度 / 预测缓存 / 渲染
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 同步运行时身份与渲染开关（每刻一次，全部是 O(1) 判断）。
     *
     * <p>覆盖是否工作的条件（235 起为七个）：种子挖矿已启用 + 已进入世界 + 种子合法 +
     * 维度受支持（236 起为主世界或下界）+ <b>至少有一个消费者</b>（「显示预测钻石」打开，
     * 或自动挖矿在要种子目标）。
     * 任一不满足：不提交任何预测，并让已经建立的身份整体失效。</p>
     *
     * <p>注意最后一条是<b>或</b>关系：自动挖矿要目标时，即使「显示预测钻石」关着，
     * 预测缓存 / 覆盖队列 / 观察状态也必须照常运转（Renderer 与 Coverage 解耦，235）。</p>
     */
    private void syncRuntime() {
        boolean wantsPrediction = config.renderPrediction() || autoMiningConsumerDemand();
        boolean want = config.enabled() && worldReady && parsedSeed != null
                && dimensionSupported() && levelRef instanceof ClientLevel && wantsPrediction;
        if (!want) {
            if (identity != null || renderer.attached()) {
                invalidateRuntime(config.enabled() ? "覆盖条件不满足" : "功能关闭");
            }
            syncRendererFlags();
            return;
        }
        ClientLevel level = (ClientLevel) levelRef;
        if (identity == null) {
            SeedRuntimeIdentity created = SeedRuntimeIdentity.of(WorldIdentity.server(), parsedSeed,
                    level.dimension(), minecraftVersion(), identityEpoch);
            identity = created;
            repository.bind(created);
            observer.bind(level);
            validation.bind(created);
            coverage.setRadius(config.coverageRadius());
            LOGGER.info("{}：运行时身份已建立（{}），预测缓存与观察层已就绪（渲染层按开关单独挂载）",
                    LOG_KEY, created.describeCn());
        }
        coverage.setRadius(config.coverageRadius());
        // 渲染层单独挂载：ESP 关着时运行时照常跑（自动挖矿是另一个消费者）
        applyRendererAttachment();
        syncRendererFlags();
    }

    /**
     * 按「显示预测钻石」开关挂载 / 注销世界渲染层（235）。
     *
     * <p><b>它只碰渲染</b>：不触碰预测缓存、覆盖队列、观察状态、验证证据。关掉时靠
     * {@link SeedOreWorldRenderer#detach()} 的「注销 + 清快照」双保险保证世界里一个框都不留；
     * 重新打开时重新挂载并从现有缓存重建快照（世界下一刻就能看到正在挖的那些预测）。</p>
     */
    private void applyRendererAttachment() {
        boolean visible = identity != null && config.enabled() && config.renderPrediction();
        renderActive = visible;
        if (visible) {
            if (!renderer.attached()) {
                renderer.attach();
                // 刚挂上必须重建一次快照：detach 时快照已清空，而观察层此后可能一直没变化，
                // 不标脏的话世界里会一直空着。**只在挂载这一刻标脏** —— 每刻标脏等于每刻重建
                // O(候选数) 的快照，那是本阶段明令避免的常态开销
                renderer.markDirty();
            }
        } else if (renderer.attached()) {
            renderer.detach();
        }
    }

    /** 把「显示当前缺失」开关同步到渲染线程可读的 volatile 镜像。 */
    private void syncRendererFlags() {
        showMissingMirror = config.showMissing();
    }

    /**
     * 让当前运行时整体失效（退出世界 / 换服 / 换维度 / 改种子 / 关功能 / 关显示）。
     *
     * <p>口径第四十~四十三条的唯一收口：递增身份代号（旧覆盖结果一律作废）→ 清覆盖队列 →
     * 清预测缓存 → 解除观察绑定 → 注销渲染层并清快照。执行完这五步之后，
     * 世界里不可能再有上一次身份的任何一个预测框。</p>
     */
    private void invalidateRuntime(String reason) {
        boolean hadSomething = identity != null || renderer.attached() || repository.size() > 0
                || observer.candidateCount() > 0;
        identityEpoch++;
        identity = null;
        coverage.reset();
        repository.reset();
        observer.unbind();
        // 234：验证证据与结论同样绑在运行时身份上（口径第十九、四十七~五十节）：
        // 改种子 / 换服 / 换维度 / 退世界 / 关功能 一律清空，绝不把上一次会话的验证带过来
        validation.reset(reason);
        renderer.detach();
        renderActive = false;
        if (hadSomething) {
            LOGGER.info("{}：运行时已失效（原因：{}）—— 预测缓存 / 覆盖队列 / 观察状态 / 渲染快照 / 种子验证全部清空",
                    LOG_KEY, reason);
        }
    }

    /**
     * 推进覆盖调度：玩家当前区块附近的目标按近→远逐个交给 Worker。
     *
     * <p>单执行位 + 有限排队都在覆盖调度器内部保证（口径第二十一节）；计算器处于失败态时不提交，
     * 避免在坏掉的进程上反复排队。</p>
     *
     * <p>235：触发条件与 {@link #syncRuntime()} 同源 —— 「显示预测钻石」开着<b>或</b>自动挖矿在要
     * 种子目标。因此关掉 ESP 后，自动挖矿需要的预测仍在持续铺开。</p>
     */
    private void driveCoverage() {
        if (identity == null) {
            return;
        }
        if (!config.renderPrediction() && !autoMiningConsumerDemand()) {
            return;
        }
        SeedDimensionProfile profile = dimensionProfile();
        if (profile == null) {
            return;
        }
        List<OreType> ores = effectiveOres();
        if (ores.isEmpty()) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }
        if (calculator.state() == SeedWorkerState.FAILED) {
            return;
        }
        ChunkPos playerChunk = ChunkPos.containing(client.player.blockPosition());
        coverage.tick(playerChunk, profile.dimensionId(), ores, repository, this::submitCoverage);
    }

    /** 把覆盖调度选出的任务交给后台单线程（真正的 worldgen 只在 Worker 里跑）。 */
    private void submitCoverage(SeedPredictionCoverageController.Task task) {
        SeedRuntimeIdentity current = identity;
        SeedDimensionProfile profile = dimensionProfile();
        if (current == null || profile == null) {
            coverage.onFailed(task);
            return;
        }
        long epoch = current.epoch();
        submitQuietly(() -> runCoverage(epoch, current, profile, task));
    }

    /**
     * 后台跑一次覆盖式预测（与手动预测同一条 Worker 链路，只是不动界面上的「上一次预测结果」）。
     *
     * <p>三段（启动 / 会话 / 预测）各自复核身份代号：期间发生换种子 / 换维度 / 换服 / 退世界 /
     * 关功能时，后面的步骤直接放弃，结果也不回写（口径第二十二节）。</p>
     */
    private void runCoverage(long epoch, SeedRuntimeIdentity snapshot, SeedDimensionProfile profile,
                             SeedPredictionCoverageController.Task task) {
        if (epoch != identityEpoch) {
            return;
        }
        PredictionResult result = null;
        Throwable failure = null;
        try {
            calculator.ensureStarted(minecraftVersion(), modVersion());
            if (epoch != identityEpoch) {
                return;
            }
            calculator.ensureSession(snapshot.seed(), profile.dimensionId());
            if (epoch != identityEpoch) {
                return;
            }
            result = calculator.predict(snapshot.seed(), profile.dimensionId(), task.oreType(), task.chunk());
        } catch (Throwable error) {
            failure = error;
        }
        deliverCoverage(epoch, task, result, failure);
    }

    /** 覆盖结果的回投（客户端主线程）：身份已变一律丢弃，成功结果写缓存 + 观察 + 标脏。 */
    private void deliverCoverage(long epoch, SeedPredictionCoverageController.Task task,
                                 PredictionResult result, Throwable failure) {
        try {
            Minecraft.getInstance().execute(() -> {
                if (epoch != identityEpoch) {
                    // 身份已变：连覆盖队列都不必通知（它已经 reset 过）
                    LOGGER.info("{}：覆盖预测 {} 返回时身份已变，结果丢弃", LOG_KEY, task.describeCn());
                    return;
                }
                if (failure != null || result == null) {
                    LOGGER.warn("{}：覆盖预测失败 {} — {}", LOG_KEY, task.describeCn(),
                            describeFailure(failure));
                    coverage.onFailed(task);
                    refreshState();
                    return;
                }
                acceptPrediction(result, "覆盖");
                coverage.onCompleted(task, true);
                renderer.markDirty();
            });
        } catch (Throwable error) {
            LOGGER.warn("{}：覆盖结果回投失败（客户端可能正在关闭）", LOG_KEY, error);
        }
    }

    /**
     * 把一条正式预测结果收进客户端缓存并观察（手动 / 覆盖共用一条通路）。
     *
     * <p>缓存有界（{@link SeedPredictionRepository#MAX_TARGET_CHUNKS}）：超限时按
     * 「覆盖范围外 → 最远 → 最久未用」淘汰，被淘汰的目标区块连同它的候选与观察状态一起清掉。</p>
     */
    private void acceptPrediction(PredictionResult result, String source) {
        if (!repository.put(result)) {
            return;
        }
        observer.trackChunk(result);
        ChunkPos playerChunk = playerChunkOrNull();
        if (playerChunk != null) {
            for (TargetKey evicted : repository.evict(playerChunk, coverageRadius())) {
                observer.untrackTarget(evicted);
            }
        }
        renderer.markDirty();
        LOGGER.info("{}：{}预测已收录 {} → {} 个（{}），缓存 {} 条 / 每矿物上限 {}",
                LOG_KEY, source,
                result.request().oreType().displayNameCn() + " 区块 ("
                        + result.request().chunk().x() + "," + result.request().chunk().z() + ")",
                result.count(), observer.snapshot().describeCn(), repository.size(),
                SeedPredictionRepository.MAX_TARGET_CHUNKS);
    }

    /**
     * 客户端收到方块更新（由 {@code ClientLevelBlockUpdateMixin} 在
     * {@code ClientLevel#setServerVerifiedBlockState} 之后调用，口径第二十八、二十九节）。
     *
     * <p>只有「这次更新的位置正好是候选」时才会读一次方块状态；不是候选的更新在这里
     * 一次哈希查表就被丢掉 —— 所以不存在「每刻重扫全部候选」那种开销。</p>
     */
    public void onClientBlockUpdated(Level level, BlockPos pos) {
        if (identity == null || !(level instanceof ClientLevel clientLevel)) {
            return;
        }
        if (observer.onBlockUpdated(clientLevel, pos)) {
            renderer.markDirty();
        }
    }

    /** 玩家当前区块（未进世界返回 null）。 */
    private ChunkPos playerChunkOrNull() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return null;
        }
        return ChunkPos.containing(client.player.blockPosition());
    }

    /** 重新计算运行时状态（唯一产生者；全部判据都来自本类自己的快照与配置）。 */
    private void refreshState() {
        SeedMiningRuntimeState next;
        if (!config.enabled()) {
            next = SeedMiningRuntimeState.DISABLED;
        } else if (!worldReady) {
            next = SeedMiningRuntimeState.WAITING_FOR_WORLD;
        } else if (config.seedText().isBlank()) {
            next = SeedMiningRuntimeState.WAITING_FOR_SEED;
        } else if (parsedSeed == null) {
            next = SeedMiningRuntimeState.INVALID_SEED;
        } else if (!dimensionSupported()) {
            next = SeedMiningRuntimeState.UNSUPPORTED_DIMENSION;
        } else if (predicting) {
            next = calculator.state() == SeedWorkerState.STARTING || !calculator.processRunning()
                    ? SeedMiningRuntimeState.CALCULATOR_STARTING
                    : SeedMiningRuntimeState.PREDICTING;
        } else if (calculator.state() == SeedWorkerState.FAILED) {
            next = SeedMiningRuntimeState.CALCULATOR_FAILED;
        } else if (lastResult != null) {
            next = lastResult.success() ? SeedMiningRuntimeState.SUCCESS : SeedMiningRuntimeState.FAILED;
        } else {
            next = SeedMiningRuntimeState.READY;
        }
        state = next;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 版本信息与失败描述
    // ────────────────────────────────────────────────────────────────────────

    /** 当前 Minecraft 版本（会话隔离与 Worker 握手的版本键）。 */
    public static String minecraftVersion() {
        return SharedConstants.getCurrentVersion().id();
    }

    /** 当前模组版本（Worker 握手要比对，防止「客户端与 Worker 来自不同模组构建」）。 */
    public static String modVersion() {
        return FabricLoader.getInstance().getModContainer(MOD_ID)
                .map(container -> container.getMetadata().getVersion().getFriendlyString())
                .orElse("unknown");
    }

    /**
     * 失败结果的中文说明（<b>给界面看的短句</b>）。
     *
     * <p>Worker 侧异常自己的 message 里带日志路径 / 退出码 / 日志尾部（可能含 Worker 的 Java 异常栈），
     * 那些已经由调用点的 {@code LOGGER.error(..., error)} 记进日志；界面这里只取
     * {@link SeedWorkerException#userMessageCn()} 的短句，避免把技术细节丢给普通用户
     * （阶段 232-P 口径第十四节）。</p>
     */
    private static String describeFailure(Throwable error) {
        if (error instanceof SeedWorkerException calculatorError) {
            return "本地世界生成计算器：" + calculatorError.userMessageCn();
        }
        if (error == null) {
            return "未知异常";
        }
        String message = error.getMessage();
        if (message == null || message.isBlank()) {
            return error.getClass().getSimpleName();
        }
        return error.getClass().getSimpleName() + ": "
                + (message.length() > 120 ? message.substring(0, 120) + "..." : message);
    }

    // ────────────────────────────────────────────────────────────────────────
    // 持久化（复用现有 module-state.json；种子按服务器 / 存档隔离）
    // ────────────────────────────────────────────────────────────────────────

    /** 按当前作用域读回配置（未进世界读全局模板）。 */
    private void loadConfig() {
        JsonObject json = ModuleStateConfig.settingsOf(RECORD_ID, configScope);
        config.load(json);
        parsedSeed = SeedMiningConfig.parseSeed(config.seedText());
    }

    /** 写回配置（与 {@code ModuleManager#saveSettings} 同一口径：先全局模板，再当前作用域）。 */
    private void persist() {
        JsonObject json = ModuleStateConfig.settingsOf(RECORD_ID, configScope);
        config.save(json);
        ModuleStateConfig.putSettings(RECORD_ID, json);
        if (configScope != null) {
            ModuleStateConfig.putSettings(RECORD_ID, configScope, json);
        }
        ModuleStateConfig.save();
    }
}
