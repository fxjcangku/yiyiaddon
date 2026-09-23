package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.seed.render.SeedRenderFrameProfiler;
import com.yiyiaddon.seed.service.SeedMiningService;
import com.yiyiaddon.ui.screen.PanelScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第六阶段（234）· <b>ESP / UI 目视验收装置（仅开发）</b>。
 *
 * <p><b>它回答什么</b>（口径第三十五~三十八节）：233 没有采集界面与世界方框的截图，
 * 234 必须补。本装置<b>不判定对错</b> —— 它只负责把画面稳定停在几个「值得看」的状态上并打时间戳，
 * 让人（或截图工具）在那些时刻取画面：</p>
 * <ol>
 *     <li><b>画面 A</b>：主世界正坐标 + 负 Y（区块 {@code (0,0)} 的钻石层，玩家在石头里 → 同时验证透墙）；</li>
 *     <li><b>画面 B</b>：负 X / 负 Z + 负 Y（区块 {@code (-3,-3)}）；</li>
 *     <li><b>画面 C</b>：打开「显示当前缺失」（灰细框；口径第三十六节 H）；</li>
 *     <li><b>画面 D</b>：关闭「显示预测钻石」→ 世界里必须一个框都不剩（口径第三十六节 J）；</li>
 *     <li><b>画面 E</b>：重新打开渲染并打开「自动挖矿控制台 → 种子挖矿」页（口径第三十八、三十九节：
 *         新增的「服务器种子验证」区块与整体排版）。因为该页比一屏高（7 段内容），本装置分三段取证：
 *         <b>E-1</b> 面板顶部，<b>E-2</b> 中段（附近覆盖 + 服务器种子验证），<b>E-3</b> 底部（环境 / 预测状态 /
 *         上一次预测结果）—— 滚动走面板自己的滚轮代码路径；</li>
 *     <li><b>画面 F</b>：回到画面 A 的位置、保持「显示当前缺失」为开（灰细框取证）。</li>
 * </ol>
 *
 * <p><b>为什么用观察者模式</b>：目标位置在 y≈-58 的石头里，生存模式会被闷死；观察者模式能悬停在其中，
 * 画面里就是「石头 + 透过石头的预测框」，正好同时看到「透墙」与「框与方块是否严丝合缝」。</p>
 *
 * <p><b>UI 页签怎么切</b>：{@code MiningConsoleScreen} 的页签选择是私有的，本装置用反射把它切到
 * 「种子挖矿」。这是<b>开发期装置</b>专有的做法（正式代码里没有任何地方这么干）。</p>
 *
 * <p><b>前置条件</b>：连上种子 {@code 20260922} 的专用服务器（{@code runSeedWorkerServer}，端口 25565），
 * 账号有 OP。参数走系统属性，不落盘、不进正式产物。</p>
 */
public final class SeedVisualAcceptance {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 日志前缀（报告里按它抓时间戳，与截图文件名对齐）。 */
    private static final String TAG = "234目视验收";

    private static final long SEED = 20260922L;
    private static final int RADIUS = 3;

    /** 画面 A：区块 (0,0) 的钻石层上方（正坐标 + 负 Y；玩家埋在地层里 → 透墙）。 */
    private static final int[] VIEW_A = {9, -40, 8};

    /** 画面 B：区块 (-3,-3)（负 X / 负 Z + 负 Y）。 */
    private static final int[] VIEW_B = {-40, -40, -40};

    /** 画面 C：233 遗留调查里实测有「当前缺失」的那一格所在区块（(-402,382)，缺失候选在 (-6417,-48,6123)）。 */
    private static final int[] VIEW_C = {-6417, -40, 6123};

    /** 每个画面停留的刻数（15 秒：足够人工 / 脚本取图）。 */
    private static final int HOLD_TICKS = 20 * 15;

    /** 画面 E 分三段：面板顶部 / 中段（附近覆盖 + 服务器种子验证）/ 底部（口径第三十八、三十九节）。 */
    private static final int UI_TOP_TICKS = 20 * 6;
    private static final int UI_MID_TICKS = 20 * 6;
    private static final int UI_BOTTOM_TICKS = 20 * 6;

    /** 向下滚动时每刻发的滚轮格数（面板滚动带平滑，留够刻数让它到位）。 */
    private static final double UI_SCROLL_STEP = 4d;

    /** 两段滚动的持续刻数：第一段落在「附近覆盖 / 服务器种子验证」，第二段滚到底。 */
    private static final int UI_SCROLL_TICKS_MID = 8;
    private static final int UI_SCROLL_TICKS_BOTTOM = 20;

    private static final int WAIT_WORLD_TICKS = 20 * 180;

    private enum Step {
        WAIT_WORLD,
        SETUP,
        TELEPORT_A,
        HOLD_A,
        TELEPORT_B,
        HOLD_B,
        SHOW_MISSING,
        HOLD_C,
        HIDE_RENDER,
        HOLD_D,
        OPEN_UI,
        HOLD_E_TOP,
        SCROLL_1,
        HOLD_E_MID,
        SCROLL_2,
        HOLD_E_BOTTOM,
        BACK_TO_A_MISSING,
        HOLD_F,
        FINISHED
    }

    private static Step step = Step.WAIT_WORLD;
    private static int waitTicks;
    private static int holdTicks;

    private SeedVisualAcceptance() {
    }

    /** 每个客户端刻推进一次（由 {@link SeedPocEntry} 调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            tick(client);
        } catch (Throwable error) {
            LOGGER.error("{}：装置异常，已停止", TAG, error);
            step = Step.FINISHED;
            SeedPocEntry.onExperimentFinished();
        }
    }

    private static void tick(Minecraft client) {
        if (step == Step.FINISHED) {
            return;
        }
        if (client.player == null || client.level == null) {
            if (++waitTicks > WAIT_WORLD_TICKS) {
                fail(client, "等待进入世界超时（请先跑 runSeedWorkerServer 并连上 127.0.0.1:25565）");
            }
            return;
        }
        switch (step) {
            case WAIT_WORLD -> setup(client);
            case SETUP -> waitCoverage(client);
            case TELEPORT_A -> teleport(client, VIEW_A, "画面 A：正坐标 + 负 Y（区块 0,0，玩家埋在地层里 → 透墙）",
                    Step.HOLD_A);
            case HOLD_A -> hold(client, Step.TELEPORT_B, HOLD_TICKS);
            case TELEPORT_B -> teleport(client, VIEW_B, "画面 B：负 X / 负 Z + 负 Y（区块 -3,-3）", Step.HOLD_B);
            case HOLD_B -> hold(client, Step.SHOW_MISSING, HOLD_TICKS);
            case SHOW_MISSING -> showMissing(client);
            case HOLD_C -> hold(client, Step.HIDE_RENDER, HOLD_TICKS);
            case HIDE_RENDER -> hideRender(client);
            case HOLD_D -> hold(client, Step.OPEN_UI, HOLD_TICKS);
            case OPEN_UI -> openUi(client);
            case HOLD_E_TOP -> hold(client, Step.SCROLL_1, UI_TOP_TICKS);
            case SCROLL_1 -> scrollDown(client, "画面 E-2：中段（附近覆盖 / 服务器种子验证）",
                    UI_SCROLL_TICKS_MID, Step.HOLD_E_MID);
            case HOLD_E_MID -> hold(client, Step.SCROLL_2, UI_MID_TICKS);
            case SCROLL_2 -> scrollDown(client, "画面 E-3：底部（环境 / 预测状态 / 上一次预测结果）",
                    UI_SCROLL_TICKS_BOTTOM, Step.HOLD_E_BOTTOM);
            case HOLD_E_BOTTOM -> hold(client, Step.BACK_TO_A_MISSING, UI_BOTTOM_TICKS);
            case BACK_TO_A_MISSING -> backToAMissing(client);
            case HOLD_F -> hold(client, Step.FINISHED, HOLD_TICKS);
            case FINISHED -> {
            }
        }
    }

    /** 写设置 + 开覆盖；玩家切成观察者以便悬停在目标层。 */
    private static void setup(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        service.setEnabled(true);
        service.setSeedText(Long.toString(SEED));
        service.setCoverageRadius(RADIUS);
        service.setShowMissing(false);
        service.setRenderPrediction(true);
        sendCommand(client, "gamemode spectator");
        LOGGER.info("{}：开跑 —— 种子 {} / 范围 {} / 显示预测钻石 开 / 显示当前缺失 关；等待附近预测铺开",
                TAG, SEED, RADIUS);
        step = Step.SETUP;
        waitTicks = 0;
    }

    /** 等覆盖铺满（确保画面上有足够多的框可看）。 */
    private static void waitCoverage(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        int done = service.coveragePredictedCount();
        int total = service.coverageTargetCount();
        if (total == 0 || done < total) {
            if (++waitTicks > WAIT_WORLD_TICKS) {
                LOGGER.warn("{}：等待覆盖铺满超时（{}/{}），按当前画面继续", TAG, done, total);
                step = Step.TELEPORT_A;
                waitTicks = 0;
            }
            return;
        }
        LOGGER.info("{}：覆盖已铺满（{} 个目标区块），候选 {}（已确认 {} / 缺失 {} / 未观察 {}）",
                TAG, total, service.renderSnapshot().stats().candidates(),
                service.renderSnapshot().stats().confirmed(), service.renderSnapshot().stats().missing(),
                service.renderSnapshot().stats().unobserved());
        step = Step.TELEPORT_A;
        waitTicks = 0;
    }

    private static void teleport(Minecraft client, int[] pos, String what, Step next) {
        if (!sendCommand(client, "tp " + pos[0] + " " + pos[1] + " " + pos[2])) {
            fail(client, "发送 tp 失败（需要 OP）");
            return;
        }
        holdTicks = 0;
        LOGGER.info("{}：======== {} → 坐标 ({},{},{})，停留 {} 秒 ========",
                TAG, what, pos[0], pos[1], pos[2], HOLD_TICKS / 20);
        step = next;
    }

    /** 停留在当前画面（每 5 秒打一次心跳，便于与截图时间戳对齐）。 */
    private static void hold(Minecraft client, Step next, int ticks) {
        if (++holdTicks % 100 == 0) {
            LOGGER.info("{}：停留中 {}/{} 秒；当前 候选 {}（已确认 {} / 缺失 {} / 未观察 {} / 调度敏感 {}）",
                    TAG, holdTicks / 20, ticks / 20,
                    SeedMiningService.instance().renderSnapshot().stats().candidates(),
                    SeedMiningService.instance().renderSnapshot().stats().confirmed(),
                    SeedMiningService.instance().renderSnapshot().stats().missing(),
                    SeedMiningService.instance().renderSnapshot().stats().unobserved(),
                    SeedMiningService.instance().renderSnapshot().stats().scheduleSensitive());
        }
        if (holdTicks >= ticks) {
            holdTicks = 0;
            step = next;
            waitTicks = 0;
            // 每换一个画面回报一次帧级采样（口径第五十五节：采样默认关闭，只有本类运行配置里显式打开才有内容）
            LOGGER.info("{}：本画面帧级渲染采样 → {}", TAG, SeedRenderFrameProfiler.snapshotSummaryCn());
            if (next == Step.FINISHED) {
                finish(client);
            }
        }
    }

    private static void showMissing(Minecraft client) {
        SeedMiningService.instance().setShowMissing(true);
        // 先传到「确实存在当前缺失候选」的区域：233 遗留调查里实测那一格在 (-6417,-48,6123)（区块 -402,382），
        // 它的邻居区块 (-402,379) 还有 9 条调度敏感缺失 —— 一张图能同时看到灰细框（当前缺失）、
        // 绿框（已确认）与琥珀内圈（调度敏感）三种视觉元素
        sendCommand(client, "tp " + VIEW_C[0] + " " + VIEW_C[1] + " " + VIEW_C[2]);
        holdTicks = 0;
        LOGGER.info("{}：======== 画面 C：已打开「显示当前缺失」并传送到 ({},{},{}) → 灰细框（当前缺失）+ "
                        + "绿框（已确认）+ 琥珀内圈（调度敏感）同屏，停留 {} 秒 ========",
                TAG, VIEW_C[0], VIEW_C[1], VIEW_C[2], HOLD_TICKS / 20);
        step = Step.HOLD_C;
    }

    private static void hideRender(Minecraft client) {
        // 先取「关闭前」的读数：关闭动作本身就会把快照清空，晚一步读到的永远是 0（口径第三十六节 J 要的是「关掉前有多少框」）
        int before = SeedMiningService.instance().renderSnapshot().size();
        SeedMiningService.instance().setRenderPrediction(false);
        holdTicks = 0;
        LOGGER.info("{}：======== 画面 D：已关闭「显示预测钻石」→ 世界里必须一个框都不剩"
                        + "（关闭前渲染条目 {} 条），停留 {} 秒 ========",
                TAG, before, HOLD_TICKS / 20);
        step = Step.HOLD_D;
    }

    /** 重新打开渲染 + 打开「自动挖矿控制台 → 种子挖矿」页（画面 E）。 */
    private static void openUi(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        service.setRenderPrediction(true);
        service.setShowMissing(false);
        if (ModuleManager.byId(AutoMinerModule.MODULE_ID) instanceof AutoMinerModule module) {
            MiningConsoleScreen screen = new MiningConsoleScreen(client.screen, module);
            selectSeedTab(screen);
            client.setScreen(screen);
            LOGGER.info("{}：======== 画面 E-1：已打开「自动挖矿控制台 → 种子挖矿」页（面板顶部），停留 {} 秒 ========",
                    TAG, UI_TOP_TICKS / 20);
        } else {
            LOGGER.error("{}：拿不到自动挖矿模块，无法打开控制台页（画面 E 跳过）", TAG);
        }
        holdTicks = 0;
        LOGGER.info("{}：确认参数：显示预测钻石 开 / 显示当前缺失 关 / 范围 {}；验证读数 {}",
                TAG, RADIUS, service.validationDiagnosticsCn());
        step = Step.HOLD_E_TOP;
    }

    /**
     * 画面 E 的滚动步骤：把控制台面板向下滚一段。
     *
     * <p>本页内容比一屏高（配置 / 世界渲染 / 附近覆盖 / 服务器种子验证 / 环境 / 预测状态 / 上一次预测结果），
     * 面板本身支持滚轮滚动（{@code PanelScreen} 的 {@code ScrollViewport}）—— 这里直接调面板自己的
     * {@code mouseScrolled}，走的就是玩家滚轮那条代码路径，不碰任何私有字段。</p>
     */
    private static void scrollDown(Minecraft client, String what, int ticks, Step next) {
        if (client.screen instanceof PanelScreen screen) {
            double x = client.getWindow().getGuiScaledWidth() / 2d;
            double y = client.getWindow().getGuiScaledHeight() / 2d;
            if (++waitTicks <= ticks) {
                screen.mouseScrolled(x, y, 0d, -UI_SCROLL_STEP);
                return;
            }
            LOGGER.info("{}：======== {} -> 已向下滚动 {} 刻（每刻 {} 格滚轮），停留 {} 秒 ========",
                    TAG, what, ticks, UI_SCROLL_STEP, UI_MID_TICKS / 20);
            LOGGER.info("{}：验证读数（滚动到此处）→ {}", TAG,
                    SeedMiningService.instance().validationDiagnosticsCn());
        } else {
            LOGGER.error("{}：当前界面不是面板屏，无法滚动（{} 跳过）", TAG, what);
        }
        waitTicks = 0;
        holdTicks = 0;
        step = next;
    }

    /**
     * 把控制台切到「种子挖矿」页签。
     *
     * <p>走 {@link MiningConsoleScreen#openSeedTab()} 这条正式入口 —— 与点页签按钮是同一条代码路径
     * （都会延到下一 tick 重建）。<b>这里刻意不用反射</b>：反射字符串会被 ProGuard 改写成混淆名
     * （实测 {@code getDeclaredField("tab")} 被改写成 {@code getDeclaredField("a")}），
     * 而发布包的常量审计要求「解密后的常量必须能在加固前的输入里找到」，于是会误报构建失败。</p>
     */
    private static void selectSeedTab(MiningConsoleScreen screen) {
        try {
            screen.openSeedTab();
        } catch (Throwable error) {
            LOGGER.error("{}：切换「种子挖矿」页签失败（画面 E 会停在概览页）", TAG, error);
        }
    }

    /**
     * 画面 F：回到画面 A 的位置、<b>保持「显示当前缺失」开着</b>，用来观察灰细框。
     *
     * <p>它在被挖过的测试世界上才有东西可看（例如 234 的验证回归专用服务器 25567：
     * 那台服务器的出生点一圈已被 dev-only 移除约 40% 的钻石 → 大批「当前缺失」）。
     * 若本次世界里没有缺失候选，本步只会打出读数、画面里自然没有灰框 —— 那是如实结果，不伪造。</p>
     */
    private static void backToAMissing(Minecraft client) {
        // 关掉控制台界面，回到世界视图
        client.setScreen(null);
        SeedMiningService service = SeedMiningService.instance();
        service.setRenderPrediction(true);
        service.setShowMissing(true);
        sendCommand(client, "tp " + VIEW_A[0] + " " + VIEW_A[1] + " " + VIEW_A[2]);
        holdTicks = 0;
        LOGGER.info("{}：======== 画面 F：回到 ({},{},{}) 且「显示当前缺失」为开 → 灰细框（当前缺失 {} 条 / "
                        + "已确认 {} 条），停留 {} 秒 ========",
                TAG, VIEW_A[0], VIEW_A[1], VIEW_A[2], service.renderSnapshot().stats().missing(),
                service.renderSnapshot().stats().confirmed(), HOLD_TICKS / 20);
        step = Step.HOLD_F;
    }

    private static void finish(Minecraft client) {
        SeedMiningService service = SeedMiningService.instance();
        LOGGER.info("{}：====== 目视验收装置结束 ======", TAG);
        LOGGER.info("{}：读数 {}；验证 {}", TAG, service.runtimeDiagnosticsCn(), service.validationDiagnosticsCn());
        service.setRenderPrediction(false);
        step = Step.FINISHED;
        SeedPocEntry.onExperimentFinished();
    }

    private static void fail(Minecraft client, String reason) {
        LOGGER.error("{}：用例失败 —— {}", TAG, reason);
        step = Step.FINISHED;
        SeedPocEntry.onExperimentFinished();
    }

    private static boolean sendCommand(Minecraft client, String command) {
        LocalPlayer player = client.player;
        if (player == null || player.connection == null) {
            return false;
        }
        player.connection.sendCommand(command);
        return true;
    }
}
