package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.server.IntegratedServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 PoC · 实验入口（本包与正式业务之间<b>唯一</b>的接触点）。
 *
 * <p>接触点只有一个方法调用：{@code YiyiAddonClient#onInitializeClient} 结尾处一行
 * {@code SeedPocEntry.installIfEnabled()}。不给系统属性 {@code -Dyiyiaddon.seedpoc.enabled=1}
 * 时它立刻返回，不订阅任何事件、不加载任何类以外的资源、对正式功能零影响。</p>
 *
 * <p>刻意不做成指令 / 界面 / 配置项：本阶段只证明算法，任何玩家可见入口都属于后续阶段
 * （用户 2026-09-22 拍板：正式页面、真实性验证、目标来源接入都要等 BlockPos 级 PoC 通过）。</p>
 *
 * <p>触发时机：进世界后第一次客户端刻，把实验体整体投递到<b>服务端线程</b>执行——
 * 因为要读真实区块真值、并在原地清空/重放/还原。单人世界开菜单会暂停服务端线程，
 * 实验在那期间不会推进，这是预期行为。</p>
 */
public final class SeedPocEntry {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 事件总线所有者标识（开发期实验，不占用任何业务所有者）。 */
    private static final String OWNER = "dev.seedpoc";

    private static boolean dispatched;
    private static boolean worldRequested;

    private SeedPocEntry() {
    }

    /** 挂载实验入口；未开启系统属性时什么都不做。 */
    public static void installIfEnabled() {
        if (!SeedPocFlags.enabled()) {
            return;
        }
        // 捕获区域必须在任何区块生成之前定下来：第二轮要在真实生成过程中取阶段快照，
        // 而测试世界是进世界时才创建/生成的，所以这里（客户端初始化）就把区域算好。
        GenStageCapture.prepareRegion();
        // 第七轮：写入台账 / 候选点诊断也必须在任何区块生成之前武装
        // （出生点附近的目标区块会在世界加载时就被装饰，晚一步就一句证据都拿不到）
        java.util.List<net.minecraft.world.level.ChunkPos> round7Targets = round7TargetChunks();
        FeatureWriteJournal.arm(round7Targets, 1);
        OreCandidateJournal.arm(round7Targets);
        ClientEventBus.subscribe(OWNER, ClientEventType.TICK, SeedPocEntry::onTick);
        // 233 观察回归：参数只读一次并打在日志最前面，便于从日志头部确认本次口径
        if (SeedPocFlags.observation()) {
            ObservationRenderRegression.configure();
        }
        // 234 验证回归：同样在日志最前面打一遍参数与阈值（便于取证时确认本次用的是什么口径）
        if (SeedPocFlags.validationRegression()) {
            SeedValidationRegression.configure();
        }
        LOGGER.info("{}：开发期实验已挂载，进单人世界后自动运行一次（系统属性 yiyiaddon.seedpoc.enabled=1）",
                SeedPocConstants.LOG_KEY);
    }

    private static void onTick(ClientEvent event) {
        Minecraft client = Minecraft.getInstance();
        // 正式化第二阶段：服务层回归（报告 230）走客户端线程自驱（它自己会先核对「未进入世界」
        // 状态，再自行新建测试世界），与服务层的线程模型一致，因此不进服务端线程分支。
        if (SeedPocFlags.serviceRegression()) {
            ServiceRegression.onClientTick(client);
            return;
        }
        // 正式化第四阶段：Worker 对照（报告 232）与多人验收同样走客户端线程 —— 服务层的公开 API
        // 本来就约定在客户端线程调用，而这两轮要验收的正是「界面那条路径」。
        if (SeedPocFlags.workerParity()) {
            WorkerParityRegression.onClientTick(client);
            return;
        }
        if (SeedPocFlags.workerMultiplayer()) {
            WorkerMultiplayerRegression.onClientTick(client);
            return;
        }
        // 第四阶段：Worker 进程生命周期与崩溃恢复（同样是界面那条路径上的客户端线程自驱）
        if (SeedPocFlags.workerLifecycle()) {
            WorkerLifecycleRegression.onClientTick(client);
            return;
        }
        // 第五阶段（233）：实际 Chunk 观察 + 世界渲染（真实客户端 + 真实服务器的实机证据）
        if (SeedPocFlags.observation()) {
            ObservationRenderRegression.onClientTick(client);
            return;
        }
        // 第五阶段（233）：覆盖 / 观察组合（错误种子 · 调度敏感观察 · 默认范围渲染开销）
        if (SeedPocFlags.coverageRegression()) {
            SeedCoverageRegression.onClientTick(client);
            return;
        }
        // 第五阶段（233）：关闭态回归（没开种子挖矿 = 计算器不启动 / 缓存与渲染全空）
        if (SeedPocFlags.offRegression()) {
            SeedOffRegression.onClientTick(client);
            return;
        }
        // 第六阶段（234）：种子验证（正确种子必须验证通过 / 错误种子绝不允许 / 被挖矿容错 / 清理矩阵）
        if (SeedPocFlags.validationRegression()) {
            SeedValidationRegression.onClientTick(client);
            return;
        }
        // 第六阶段（234）：233 遗留「1 个 MISSING」定位与复现
        if (SeedPocFlags.missingCandidateRegression()) {
            SeedMissingCandidateRegression.onClientTick(client);
            return;
        }
        // 第六阶段（234）：ESP / UI 目视验收（把画面停在五个状态上供取图）
        if (SeedPocFlags.visualAcceptance()) {
            SeedVisualAcceptance.onClientTick(client);
            return;
        }
        // 第六阶段（234）：半径 6 压力烟测（169 个目标区块的规模 / 帧率 / 卡顿 / 内存读数）
        if (SeedPocFlags.radius6Smoke()) {
            SeedRadius6Smoke.onClientTick(client);
            return;
        }
        // 第七阶段（235）：钻石 Seed Target → AutoMiner 正式接入（A~L 十二项实机回归）
        if (SeedPocFlags.targetRegression()) {
            SeedTargetRegression.onClientTick(client);
            return;
        }
        // 第八阶段（236）：多矿物 · 多维度实机矩阵（通用化零回归 + 其它 10 种矿物 + 下界）
        if (SeedPocFlags.oreMatrix()) {
            SeedOreMatrixRegression.onClientTick(client);
            return;
        }
        // 第九阶段（237）：下界真正多人服务器验收（主世界 → 下界切换 + 远端未加载 + fail-closed）
        if (SeedPocFlags.netherMultiplayer()) {
            NetherMultiplayerRegression.onClientTick(client);
            return;
        }
        if (dispatched) {
            return;
        }
        if (client.level == null || client.player == null) {
            // 还没进世界：按需自动建一个固定种子的测试世界，保证实验可重复。
            // 允许在「无界面」或「原版标题界面」两种时机触发——正常启动后停在的就是标题界面；
            // 其它界面（登录提示、错误弹窗等）一律不抢占，避免把实验塞进用户正在操作的流程里。
            boolean atTitle = client.screen == null || client.screen instanceof TitleScreen;
            if (!worldRequested && SeedPocFlags.autoCreateWorld() && atTitle) {
                worldRequested = true;
                SeedPocWorldFactory.createFreshWorld(client, SeedPocFlags.fixedTestSeed());
            }
            return;
        }
        IntegratedServer server = client.getSingleplayerServer();
        if (server == null) {
            dispatched = true;
            LOGGER.warn("{}：本实验只在单人世界的集成服务端上运行（需要读真实区块真值），当前环境不支持，已停止",
                    SeedPocConstants.LOG_KEY);
            return;
        }
        dispatched = true;
        LOGGER.info("{}：已投递到服务端线程，开始实验", SeedPocConstants.LOG_KEY);
        server.execute(() -> {
            try {
                if (SeedPocFlags.formal()) {
                    // 正式化第一阶段（报告 229）：正式 Predictor 迁移回归。
                    // 依赖方向是 dev → 正式（正式层不依赖本包），它只做纯 Seed 对照，不写真实世界。
                    FormalSeedRegression.run(server);
                } else if (SeedPocFlags.round7()) {
                    // 第七轮：FEATURES 调度因果定案（case = 单次真实世界全量取证 + Debug 重放；
                    // correlate = 跨运行相关性）。它自带请求顺序驱动，不走第五轮场景。
                    Round7Runner.run(server);
                } else if (SeedPocFlags.orderScenario() != null) {
                    // 第五轮：Chunk 生成顺序决定性实验（一个进程 = 一个全新世界 + 一种请求顺序）
                    ChunkOrderTestRunner.run(server);
                } else {
                    SeedPocRunner.run(server);
                }
            } catch (Throwable error) {
                // 实验代码绝不允许把异常扩散到游戏主循环
                LOGGER.error("{}：实验中断", SeedPocConstants.LOG_KEY, error);
            } finally {
                onExperimentFinished();
            }
        });
    }

    /** 第七轮要跟踪的区块清单（复用第五轮的 order.targets 属性；没配就退回默认测试区块）。 */
    private static java.util.List<net.minecraft.world.level.ChunkPos> round7TargetChunks() {
        java.util.List<net.minecraft.world.level.ChunkPos> chunks = new java.util.ArrayList<>();
        for (long[] pair : SeedPocFlags.round7Targets()) {
            chunks.add(new net.minecraft.world.level.ChunkPos((int) pair[0], (int) pair[1]));
        }
        return chunks;
    }

    /** 实验收尾：按需自动退出客户端，让脚本化重复运行能一次跑完。 */
    static void onExperimentFinished() {
        if (!SeedPocFlags.exitWhenDone()) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        client.execute(() -> {
            LOGGER.info("{}：按系统属性要求退出客户端", SeedPocConstants.LOG_KEY);
            client.stop();
        });
    }
}
