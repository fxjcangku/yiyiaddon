package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.platform.network.ConnectionCloser;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.service.SeedMiningRuntimeState;
import com.yiyiaddon.seed.service.SeedMiningService;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿第四阶段（报告 232）· <b>Worker 进程生命周期与崩溃恢复装置</b>（开发期）。
 *
 * <p><b>它回答什么</b>（口径第二十四、三十二、三十三、六十五、七十六、七十七节）：
 * 隔离 Worker 是本阶段唯一的正式宿主，所以「进程出事时正式层怎么办」必须实测，不能只写在注释里。
 * 本装置在<b>客户端线程</b>按顺序做四件事：</p>
 *
 * <ol>
 *   <li><b>基线</b>：开启种子挖矿 + Seed 20260922，预测区块 (0,0) 必须得到 45 个候选，
 *       并记下 Worker 的 PID 与累计启动次数；</li>
 *   <li><b>崩溃</b>：从本进程的子进程里找出 Worker 并<b>强杀</b>，然后核对正式层是否
 *       在不需要任何点击的情况下就进入「本地世界生成计算器异常」（口径第三十三节）；</li>
 *   <li><b>恢复</b>：再点一次预测，允许自动重启一次 —— 必须拿到同一个 45，且 PID 变了、
 *       启动次数 +1（这就是「重启过」的直接证据，口径第三十三节）；</li>
 *   <li><b>清理</b>：断开连接，核对计算器已停止、结果已清、没有残留子进程。</li>
 * </ol>
 *
 * <p>全过程中客户端必须活着（本装置自己还在跑刻），这就是口径第六十五节要求的
 * 「Worker 崩溃不拖死客户端」。</p>
 *
 * <p><b>触发方式</b>：{@code -Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.workerLifecycle=1
 * -Dyiyiaddon.seedpoc.exit=1}</p>
 */
public final class WorkerLifecycleRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 报告文件名（落在运行目录）。 */
    private static final String REPORT_FILE = "seedpoc-232-Worker生命周期.txt";

    /** 单次等待上限（客户端刻）。 */
    private static final int WAIT_TIMEOUT_TICKS = 20 * 180;

    /** 崩溃后等待正式层进入失败态的窗口（客户端刻）：进程退出回调是本地事件，几秒足够。 */
    private static final int FAILED_STATE_WAIT_TICKS = 20 * 30;

    /** 固定用例：Seed 20260922 区块 (0,0) = 45（229/230 冻结数字）。 */
    private static final long SEED = 20260922L;
    private static final int CHUNK_X = 0;
    private static final int CHUNK_Z = 0;
    private static final int EXPECTED_COUNT = 45;

    private static final SeedMiningService SERVICE = SeedMiningService.instance();

    private enum Stage {
        /** 等进入单人世界（本装置需要一个世界会话才能开预测）。 */
        ENTER_WORLD,
        /** 基线预测。 */
        BASELINE,
        /** 强杀 Worker 并核对失败态。 */
        KILL,
        /** 再次预测，核对自动重启。 */
        RECOVER,
        /** 断开连接，核对清理。 */
        EXIT_WORLD,
        FINISHED
    }

    private static Stage stage = Stage.ENTER_WORLD;
    private static int waitTicks;
    private static boolean worldRequested;
    private static boolean submitted;

    private static long baselinePid = -1L;
    private static int baselineStartCount = -1;
    private static long restartedPid = -1L;

    private static final List<String> REPORT = new ArrayList<>();
    private static final List<String> VERDICTS = new ArrayList<>();

    /** 收尾只允许执行一次（报告落盘 + 自动退出）。 */
    private static boolean reportWritten;

    private WorkerLifecycleRegression() {
    }

    /** 每客户端刻推进一次（由 {@link SeedPocEntry} 在 Worker 生命周期模式下调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            switch (stage) {
                case ENTER_WORLD -> tickEnterWorld(client);
                case BASELINE -> tickBaseline(client);
                case KILL -> tickKill(client);
                case RECOVER -> tickRecover(client);
                case EXIT_WORLD -> tickExitWorld(client);
                case FINISHED -> {
                }
            }
        } catch (Throwable error) {
            LOGGER.error("{}：Worker 生命周期装置中断", SeedPocConstants.LOG_KEY, error);
            REPORT.add("装置异常中断：" + error.getClass().getSimpleName() + " / " + error.getMessage());
            finish();
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 一、进入世界
    // ────────────────────────────────────────────────────────────────────────

    private static void tickEnterWorld(Minecraft client) {
        if (client.level == null || client.player == null) {
            boolean atTitle = client.screen == null
                || client.screen instanceof net.minecraft.client.gui.screens.TitleScreen;
            if (!worldRequested && SeedPocFlags.autoCreateWorld() && atTitle) {
                worldRequested = true;
                SeedPocWorldFactory.createFreshWorld(client, SeedPocFlags.fixedTestSeed());
            }
            return;
        }
        if (client.getSingleplayerServer() == null) {
            abort("未进入单人世界：本装置需要一个世界会话，本轮不成立");
            return;
        }
        report("零、环境");
        report("  世界夹具：" + SeedPocWorldFactory.LEVEL_ID + "（" + SeedPocWorldFactory.lastFreshNote() + "）");
        report("  固定用例：Seed " + SEED + " 区块 (" + CHUNK_X + "," + CHUNK_Z + ") 必须 "
            + EXPECTED_COUNT + " 个候选");
        report("  说明：本轮的宿主是本机隔离的计算器进程，崩溃与恢复都是它的进程事件；");
        report("        世界生成仍然只走离线管线（宿主 ChunkMap 查询必须为 0）。");
        report("");
        report("一、基线（未崩溃前）");
        SERVICE.setSeedText(String.valueOf(SEED));
        SERVICE.setEnabled(true);
        stage = Stage.BASELINE;
        waitTicks = 0;
        submitted = false;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 二、基线预测
    // ────────────────────────────────────────────────────────────────────────

    private static void tickBaseline(Minecraft client) {
        if (!submitted) {
            SERVICE.predictChunk(CHUNK_X, CHUNK_Z);
            if (!SERVICE.predicting()) {
                abort("基线预测未能启动（状态 " + SERVICE.stateCn() + "）");
                return;
            }
            submitted = true;
            waitTicks = 0;
            return;
        }
        if (SERVICE.predicting()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                abort("基线预测超时");
            }
            return;
        }
        PredictionResult result = SERVICE.lastResult();
        boolean countOk = result != null && result.count() == EXPECTED_COUNT;
        baselinePid = SERVICE.calculatorPid();
        baselineStartCount = SERVICE.calculatorStartCount();
        report("  基线结果：候选 " + (result == null ? -1 : result.count())
            + "（期望 " + EXPECTED_COUNT + "）" + verdict(countOk)
            + "；宿主 ChunkMap 查询 " + (result == null ? -1 : result.stats().hostChunkSourceQueries())
            + (result != null && result.stats().hostChunkSourceQueries() == 0 ? "" : " **异常**"));
        report("  基线进程：PID=" + baselinePid + "，累计启动次数=" + baselineStartCount
            + "；状态「" + SERVICE.stateCn() + "」");
        VERDICTS.add("【判定】基线预测：" + verdict(countOk && baselinePid > 0));
        stage = Stage.KILL;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 三、强杀 Worker，核对失败态
    // ────────────────────────────────────────────────────────────────────────

    private static void tickKill(Minecraft client) {
        if (waitTicks == 0) {
            report("");
            report("二、崩溃（按正式层自报的 PID 强杀计算器）");
            // 定位方式刻意不用「扫子进程 + 匹配命令行」：那依赖平台能否读到别人的命令行，
            // 而 PID 是正式层自己报出来的握手结果，用它去 ProcessHandle.of() 拿到句柄最可靠，
            // 同时顺手证明「Worker 确实是一个真实存在的本机进程」。
            ProcessHandle handle = ProcessHandle.of(baselinePid).orElse(null);
            boolean alive = handle != null && handle.isAlive();
            report("  正式层自报 PID " + baselinePid + " → 系统中该 PID " + (alive ? "存在且在运行" : "**不存在**")
                + verdict(alive));
            if (!alive) {
                abort("按 PID 找不到计算器进程，崩溃注入不成立");
                return;
            }
            report("  该进程命令行：" + handle.info().commandLine().orElse("（本平台不可读）"));
            StringBuilder childPids = new StringBuilder();
            ProcessHandle.current().children().forEach(
                child -> childPids.append(childPids.isEmpty() ? "" : ", ").append(child.pid()));
            report("  本客户端进程 " + ProcessHandle.current().pid() + " 的直接子进程："
                + (childPids.isEmpty() ? "（无可读子进程）" : childPids));
            report("  该进程的父进程 PID：" + handle.parent().map(p -> String.valueOf(p.pid()))
                .orElse("（不可读）") + "（预期就是本客户端进程）");
            report("  强制结束该进程：" + (handle.destroyForcibly() ? "已发出" : "**失败**"));
            waitTicks = 1;
            return;
        }
        // 正式层必须在不需要任何点击的情况下就发现「计算器没了」（口径第三十三节）。
        // 这里给一个有限等待窗口：窗口内进失败态 = 通过，窗口用尽还停在旧状态 = 不通过。
        if (++waitTicks <= FAILED_STATE_WAIT_TICKS
                && SERVICE.state() != SeedMiningRuntimeState.CALCULATOR_FAILED) {
            return;
        }
        boolean failedState = SERVICE.state() == SeedMiningRuntimeState.CALCULATOR_FAILED;
        boolean running = SERVICE.calculatorRunning();
        boolean alive = client.level != null && client.player != null;
        report("  崩溃后状态：「" + SERVICE.stateCn() + "」（期望「"
            + SeedMiningRuntimeState.CALCULATOR_FAILED.displayNameCn() + "」，等待 "
            + (FAILED_STATE_WAIT_TICKS / 20) + " 秒）" + verdict(failedState));
        report("  失败原因：" + SERVICE.calculatorErrorCn());
        report("  计算器进程还在跑？" + running + "（期望 false）" + verdict(!running));
        report("  客户端是否仍然存活？" + alive + "（本装置还在推进刻，本身就是证据）" + verdict(alive));
        VERDICTS.add("【判定】崩溃后自动进入失败态：" + verdict(failedState)
            + " / 进程已回收 " + verdict(!running) + " / 客户端未受影响 " + verdict(alive));
        stage = Stage.RECOVER;
        waitTicks = 0;
        submitted = false;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 四、再点一次：自动重启一次
    // ────────────────────────────────────────────────────────────────────────

    private static void tickRecover(Minecraft client) {
        if (!submitted) {
            // 口径第三十三节的退避窗口：连续失败有最小间隔，这里先等一段再提交（也顺便证明
            // 「失败态停留期间界面没有被卡住」——期间客户端一直在推进刻）
            if (waitTicks == 0) {
                report("");
                report("三、恢复（再点一次预测，允许自动重启一次）");
            }
            if (++waitTicks < 20 * 7) {
                return;
            }
            SERVICE.predictChunk(CHUNK_X, CHUNK_Z);
            if (!SERVICE.predicting()) {
                report("  **恢复预测未能启动**（状态 " + SERVICE.stateCn() + "）");
                VERDICTS.add("【判定】崩溃后自动重启：**不通过**（预测未启动）");
                stage = Stage.EXIT_WORLD;
                waitTicks = 0;
                return;
            }
            submitted = true;
            waitTicks = 0;
            return;
        }
        if (SERVICE.predicting()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("  **恢复预测超时**");
                VERDICTS.add("【判定】崩溃后自动重启：**不通过**（超时）");
                stage = Stage.EXIT_WORLD;
                waitTicks = 0;
            }
            return;
        }
        PredictionResult result = SERVICE.lastResult();
        long pid = SERVICE.calculatorPid();
        int startCount = SERVICE.calculatorStartCount();
        restartedPid = pid;
        boolean countOk = result != null && result.count() == EXPECTED_COUNT;
        boolean restarted = pid > 0 && pid != baselinePid;
        boolean counted = startCount == baselineStartCount + 1;
        report("  恢复结果：候选 " + (result == null ? -1 : result.count())
            + "（期望 " + EXPECTED_COUNT + "）" + verdict(countOk)
            + "；宿主 ChunkMap 查询 " + (result == null ? -1 : result.stats().hostChunkSourceQueries())
            + (result != null && result.stats().hostChunkSourceQueries() == 0 ? "" : " **异常**"));
        report("  进程：PID=" + pid + "（基线 " + baselinePid + "，必须不同）" + verdict(restarted)
            + "；累计启动次数=" + startCount + "（基线 " + baselineStartCount + " + 1）" + verdict(counted));
        boolean baselineDead = ProcessHandle.of(baselinePid).map(h -> !h.isAlive()).orElse(true);
        report("  被强杀的旧 PID " + baselinePid + " 是否确已消失：" + baselineDead + verdict(baselineDead));
        boolean single = ProcessHandle.of(pid).map(ProcessHandle::isAlive).orElse(false);
        VERDICTS.add("【判定】崩溃后自动重启一次：" + verdict(countOk && restarted && counted && baselineDead)
            + " / 候选 " + (result == null ? -1 : result.count()) + verdict(countOk)
            + " / PID 已更换 " + verdict(restarted) + " / 启动次数 +1 " + verdict(counted)
            + " / 新进程存活 " + verdict(single));
        stage = Stage.EXIT_WORLD;
        waitTicks = 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // 五、断开连接：清理核对
    // ────────────────────────────────────────────────────────────────────────

    private static void tickExitWorld(Minecraft client) {
        if (waitTicks == 0) {
            report("");
            report("四、退出世界（断开连接）");
            if (!ConnectionCloser.disconnect(Component.literal("种子挖矿 Worker 生命周期：测试断开连接"))) {
                report("    **断线请求未发出（当前没有连接）**");
                finish();
                return;
            }
            waitTicks = 1;
            return;
        }
        if (client.level != null) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("    **等待离开世界超时**");
                VERDICTS.add("【判定】退出世界清理：未完成（超时）");
                finish();
            }
            return;
        }
        // 退世界后的收尾异步执行（停机指令 → 优雅销毁 → 强杀三级超时），给一个有限等待窗口
        if (SERVICE.calculatorRunning()) {
            if (++waitTicks > WAIT_TIMEOUT_TICKS) {
                report("    **等待计算器退出超时**");
                VERDICTS.add("【判定】退出世界清理：未完成（计算器未在窗口内停止）");
                finish();
            }
            return;
        }
        boolean waiting = SERVICE.state() == SeedMiningRuntimeState.WAITING_FOR_WORLD;
        boolean cleared = SERVICE.lastResult() == null;
        boolean restartedWorkerGone = ProcessHandle.of(restartedPid).map(h -> !h.isAlive()).orElse(true);
        report("  已回到标题界面：状态「" + SERVICE.stateCn() + "」（期望「"
            + SeedMiningRuntimeState.WAITING_FOR_WORLD.displayNameCn() + "）」" + verdict(waiting));
        report("  运行时结果已清：" + verdict(cleared) + "；计算器已停止：" + verdict(true)
            + "；重启出来的进程 " + restartedPid + " 也已退出：" + restartedWorkerGone
            + verdict(restartedWorkerGone));
        VERDICTS.add("【判定】退出世界清理：状态回「等待进入世界」" + verdict(waiting)
            + " / 结果已清 " + verdict(cleared) + " / 无残留进程 " + verdict(restartedWorkerGone));
        finish();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 收尾
    // ────────────────────────────────────────────────────────────────────────

    private static void abort(String summary) {
        report("**" + summary + "**");
        VERDICTS.add("【判定】Worker 生命周期：不通过（" + summary + "）");
        finish();
    }

    private static void finish() {
        if (reportWritten) {
            return;
        }
        reportWritten = true;
        stage = Stage.FINISHED;
        List<String> lines = new ArrayList<>();
        lines.add("《232 · 种子挖矿正式化第四阶段 · 计算器进程生命周期与崩溃恢复结果》");
        lines.add("装置：从客户端线程调用 SeedMiningService 公开 API（与界面按钮同一条路径），");
        lines.add("      崩溃注入方式 = 从本进程的子进程里 destroyForcibly() 掉计算器。");
        lines.add("世界夹具：" + SeedPocWorldFactory.LEVEL_ID + "（" + SeedPocWorldFactory.lastFreshNote() + "）");
        lines.add("");
        lines.addAll(REPORT);
        lines.add("");
        lines.add("五、判定汇总");
        lines.addAll(VERDICTS);
        lines.add("");
        lines.add("六、计算器诊断");
        lines.add("  " + SERVICE.calculatorDiagnosticsCn());
        boolean allPass = !VERDICTS.isEmpty() && VERDICTS.stream().noneMatch(line -> line.contains("不通过"));
        lines.add("全部判定：" + (allPass ? "通过" : "**存在不通过项，见上**"));
        SeedPocReport.output(lines, REPORT_FILE);
        SeedPocEntry.onExperimentFinished();
    }

    // ────────────────────────────────────────────────────────────────────────
    // 工具
    // ────────────────────────────────────────────────────────────────────────

    private static String verdict(boolean ok) {
        return ok ? "（通过）" : "（**不通过**）";
    }

    private static void report(String line) {
        REPORT.add(line);
        LOGGER.info("{}：232生命周期｜{}", SeedPocConstants.LOG_KEY, line);
    }
}
