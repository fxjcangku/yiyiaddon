package com.yiyiaddon.dev.seedpoc;

import com.yiyiaddon.seed.service.SeedMiningService;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第五阶段（233）· <b>关闭态回归装置（仅开发）</b>。
 *
 * <p>它回答的是「没开种子挖矿时，233 加进来的东西会不会自己动起来」（口径第六十二节）：</p>
 * <ul>
 *     <li>本地世界生成计算器<b>不启动</b>（没有任何 Worker 进程）；</li>
 *     <li>覆盖调度<b>不工作</b>（目标数 / 排队数恒为 0）；</li>
 *     <li>预测缓存为空、观察层为空、渲染快照为空；</li>
 *     <li>世界里<b>一个预测框都不会画</b>（运行时身份未建立 ⇒ 渲染闸门关闭）。</li>
 * </ul>
 *
 * <p>全程不碰任何开关、不填种子、不发指令 —— 就是「普通 runClient 进服后什么都不做」。
 * 因此本装置必须跑在<b>真实服务器</b>上（多人环境才会走 WorldIdentity 的服务器键那条路径）。
 * AutoMiner 行为不在本装置里改也不在这里测：233 没有触碰自动挖矿的任何代码路径。</p>
 */
public final class SeedOffRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 日志前缀（报告里按它抓证据）。 */
    private static final String TAG = "233关闭态回归";

    private static final int WAIT_WORLD_TICKS = 20 * 120;
    /** 进世界后先静置这么久再开始判定（给配置加载 / 世界事件留出正常的收敛时间）。 */
    private static final int SETTLE_TICKS = 20 * 3;
    /** 静置结束后的观察时长：全程都必须保持「什么都没发生」。 */
    private static final int OBSERVE_TICKS = 20 * 17;

    /** 「正在预测」那一行的空闲文本（覆盖调度没在跑时的固定值）。 */
    private static final String IDLE_ACTIVE = "—";

    private static int ticks;
    private static boolean started;

    private SeedOffRegression() {
    }

    /** 每个客户端刻推进一次（由 {@link SeedPocEntry} 调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            tick(client);
        } catch (Throwable error) {
            LOGGER.error("{}：回归装置异常，已停止", TAG, error);
            SeedPocEntry.onExperimentFinished();
        }
    }

    private static void tick(Minecraft client) {
        if (client.player == null || client.level == null) {
            if (++ticks > WAIT_WORLD_TICKS) {
                LOGGER.error("{}：用例失败 —— 等待进入世界超时（请先连上专用服务器）", TAG);
                SeedPocEntry.onExperimentFinished();
            }
            return;
        }
        ticks++;
        if (!started) {
            started = true;
            LOGGER.info("{}：已进入世界（{}），全程不碰任何开关，观察 {} 秒",
                    TAG, SeedMiningService.instance().dimensionDisplayCn(), (SETTLE_TICKS + OBSERVE_TICKS) / 20);
        }
        if (ticks < SETTLE_TICKS) {
            return;
        }
        String problem = check();
        if (problem != null) {
            LOGGER.error("{}：用例失败 —— {}", TAG, problem);
            LOGGER.error("{}：失败时读数 {}", TAG, SeedMiningService.instance().runtimeDiagnosticsCn());
            SeedPocEntry.onExperimentFinished();
            return;
        }
        if (ticks == SETTLE_TICKS) {
            LOGGER.info("{}：关闭态证据 #1（静置 {} 秒后）→ {}", TAG, SETTLE_TICKS / 20, describe());
        }
        if (ticks >= SETTLE_TICKS + OBSERVE_TICKS) {
            LOGGER.info("{}：关闭态证据 #2（再观察 {} 秒后）→ {}", TAG, OBSERVE_TICKS / 20, describe());
            LOGGER.info("{}：结论 —— 计算器未启动 / 覆盖不工作 / 缓存与观察与渲染快照全空 / 渲染闸门关闭",
                    TAG);
            SeedPocEntry.onExperimentFinished();
        }
    }

    /** 任一项不为「空转」，返回问题描述；全部正常返回 {@code null}。 */
    private static String check() {
        SeedMiningService service = SeedMiningService.instance();
        if (service.enabled()) {
            return "种子挖矿居然处于启用状态（本用例要求默认关闭）";
        }
        if (service.calculatorRunning()) {
            return "本地世界生成计算器被启动了（关闭态下不该有任何 Worker 进程）";
        }
        if (service.cachedChunkCount() != 0) {
            return "预测缓存不为空（" + service.cachedChunkCount() + " 个区块）";
        }
        if (service.coveragePredictedCount() != 0 || service.coveragePendingCount() != 0
                || !IDLE_ACTIVE.equals(service.coverageActiveChunkCn())) {
            return "覆盖调度在工作（已预测 " + service.coveragePredictedCount() + " / 排队 "
                    + service.coveragePendingCount() + " / 正在跑 " + service.coverageActiveChunkCn() + "）";
        }
        if (service.observationSnapshot().candidates() != 0) {
            return "观察层不为空（候选 " + service.observationSnapshot().candidates() + " 个）";
        }
        if (!service.renderSnapshot().empty()) {
            return "渲染快照不为空（" + service.renderSnapshot().size() + " 条）";
        }
        if (service.renderPrediction()) {
            return "「显示预测钻石」处于开启状态（本用例要求默认关闭）";
        }
        if (!"未建立".equals(service.runtimeIdentityCn())) {
            return "运行时身份被建立了（" + service.runtimeIdentityCn() + "），渲染闸门会因此打开";
        }
        return null;
    }

    private static String describe() {
        SeedMiningService service = SeedMiningService.instance();
        return "计算器运行 " + (service.calculatorRunning() ? "是" : "否")
                + " / 缓存 " + service.cachedChunkCount()
                + " 个区块 / 覆盖已预测 " + service.coveragePredictedCount()
                + " / 覆盖排队 " + service.coveragePendingCount()
                + " / 正在预测 " + service.coverageActiveChunkCn()
                + "（范围设定值 " + service.coverageTargetCount() + " 只是配置的函数，不代表在工作）"
                + " / 观察候选 " + service.observationSnapshot().candidates()
                + " / 渲染快照 " + service.renderSnapshot().size()
                + " 条 / 显示预测钻石 " + (service.renderPrediction() ? "开" : "关")
                + " / 运行时身份 " + service.runtimeIdentityCn();
    }
}
