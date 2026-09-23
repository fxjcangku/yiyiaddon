package com.yiyiaddon.dev.seedpoc;

import baritone.api.IBaritone;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.fastbreak.MiningFastBreakController;
import com.yiyiaddon.feature.mining.fsm.MinerState;
import com.yiyiaddon.feature.mining.model.LootMode;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.seed.model.OreType;
import com.yiyiaddon.seed.observation.OreObservationState;
import com.yiyiaddon.seed.ore.SeedDimensionProfile;
import com.yiyiaddon.seed.ore.SeedOreDefinition;
import com.yiyiaddon.seed.ore.SeedOreRegistry;
import com.yiyiaddon.seed.prediction.PredictedOre;
import com.yiyiaddon.seed.prediction.PredictionResult;
import com.yiyiaddon.seed.service.SeedMiningService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿正式化第七阶段（235）· <b>种子目标 → AutoMiner 接入回归装置</b>（开发期）。
 *
 * <p><b>它回答什么</b>：把已经 VERIFIED 的种子预测正式接进现有自动挖矿之后，用户口径里的
 * A~L 十二项是否逐条成立 —— 而<b>不是</b>重新研究预测 / 观察 / 验证本身（那三件事由 229~234 冻结）。</p>
 *
 * <p><b>238 起可换矿物</b>：默认钻石（与 235 逐字一致），用
 * {@code -Dyiyiaddon.seedpoc.target.ore=COAL} 换成主世界其它矿物即可用<b>同一套用例</b>
 * 复核「非钻石也能被自动挖」。本装置只覆盖主世界 8 种矿物；下界 3 种由
 * 「多矿物矩阵」与「下界真正多人」装置负责（那里才有真实下界身份）。</p>
 *
 * <h2>环境</h2>
 * <p>单人夹具世界：{@link SeedPocWorldFactory} 用固定种子（默认 20260922）新建，因此
 * 「Worker 按种子算出来的候选坐标」与「这个世界的真实矿物」可比 —— 这正是验证闸门能到达
 * 「已验证」的前提。测试用的指令（{@code give / spreadplayers / forceload / fill / setblock / tp}）
 * 走玩家自己的发包入口，单人存档自带指令权限，因此不需要专用服务器与 ops.json。</p>
 *
 * <h2>方法（每一条都写清「拿什么当证据」）</h2>
 * <ul>
 *     <li>玩家位置由 {@code /tp} 与 {@code /spreadplayers} 摆布；每次摆布前调用
 *         {@code MiningStateMachine#armOwnTeleportGraceForDev()}，否则那一次大跳变会被
 *         「手动传送守卫」如实读成玩家自己传送并把模块停掉（那是它对玩家指令的正确反应，
 *         但会让本装置观测到的不是种子目标行为）；</li>
 *     <li>方块真值一律读<b>集成服务端</b>（{@code ServerLevel#getBlockState}），不读客户端镜像；
 *         远端目标处置时用 {@code /forceload add} 保证服务端确实加载，指令才落得下去；</li>
 *     <li>「目标未加载」用例用 {@code /spreadplayers} 把玩家挪出渲染距离（250~300 格），
 *         目标区块因此在<b>客户端</b>卸载 —— 这正是产品里「远端 Seed 目标」的真实形态；</li>
 *     <li>「实际钻石 → 挖掉」用例先读真值、再把目标周围挖成空气口袋并把这颗钻石原样放回，
 *         然后把玩家送进口袋站在这颗矿上：秒破的方向与交互距离都是真实几何，不是被装置硬凑的；</li>
 *     <li>「寻路反复失败」用例以「每次寻路刚起来就取消掉」模拟算不出路 —— 提供者判「进程没了」
 *         的那条代码路径与真实算路失败完全相同（见 {@code SeedMiningTargetProvider#tick} 第 5 步）。</li>
 * </ul>
 *
 * <p><b>触发方式</b>（默认完全关闭；不给系统属性时本类不会被加载执行）：</p>
 * <pre>{@code
 * -Dyiyiaddon.seedpoc.enabled=1 -Dyiyiaddon.seedpoc.target=1
 * -Dyiyiaddon.seedpoc.target.seed=20260922 -Dyiyiaddon.seedpoc.target.radius=3
 * -Dyiyiaddon.seedpoc.exit=1
 * }</pre>
 */
public final class SeedTargetRegression {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seedpoc");

    /** 日志 / 报告前缀。 */
    private static final String TAG = "235目标接入";

    /** 报告文件名（落在运行目录）。 */
    private static final String REPORT_FILE = "seedpoc-目标接入回归.txt";

    private static final SeedMiningService SERVICE = SeedMiningService.instance();

    /** 模块 ID（自动挖矿）。 */
    private static final String MODULE_ID = AutoMinerModule.MODULE_ID;

    // ── 等待上限（刻） ──────────────────────────────────────────────────────

    /** 等待进入世界。 */
    private static final int WAIT_WORLD_TICKS = 20 * 180;
    /** 等待验证闸门打开（覆盖 49 区块 + 计算器冷启动）。 */
    private static final int WAIT_VERIFIED_TICKS = 20 * 60 * 15;
    /** 等待状态机进入 MINING。 */
    private static final int WAIT_MINING_TICKS = 20 * 90;
    /** 等待提供者锁定目标（覆盖 49 区块要几十秒，换区后还要重铺一遍）。 */
    private static final int WAIT_TARGET_TICKS = 20 * 150;
    /** 一般短等待（传送落地 / 观察生效 / 换目标）。 */
    private static final int WAIT_SHORT_TICKS = 20 * 20;
    /** 等待秒破把目标挖掉。 */
    private static final int WAIT_MINE_TICKS = 20 * 20;
    /** K 阶段：反复取消耗尽寻路的最长观察窗。 */
    private static final int WAIT_PATH_KILL_TICKS = 20 * 60;

    /**
     * D 阶段「远端落脚点」与目标的水平距离（格）。
     *
     * <p>本装置运行目录的渲染距离是 12 区块（192 格），400 格是它的两倍多，足以让目标区块在客户端卸载；
     * 而落脚点又是往目标的<b>反方向</b>走的，所以实际距离只会更远。</p>
     */
    private static final int FAR_SPOT_DISTANCE = 400;

    /** 执行阶段。 */
    private enum Stage {
        /** 标题界面：复位配置并新建夹具世界。 */
        TITLE,
        /** 进入世界、摆好装置（点位 / 装备 / 远处就位）。 */
        PREPARE,
        /** A：种子关闭 —— 原自动挖矿回归。 */
        A_RUN,
        /** A：核对并收场。 */
        A_CHECK,
        /** B：种子目标模式打开但闸门未开 —— 不许开始。 */
        B_GATE,
        /** B：核对。 */
        B_CHECK,
        /** 等待验证「已验证」。 */
        AWAIT_VERIFIED,
        /** C：启动模块并等待锁定真实种子目标。 */
        C_RUN,
        /** C：核对。 */
        C_CHECK,
        /** D：把玩家挪出渲染距离，目标区块在客户端卸载。 */
        D_RUN,
        /** D：核对「未加载仍按精确坐标导航」。 */
        D_CHECK,
        /** E：真值确认 + 开空气口袋 + 换下一颗重试。 */
        E_SETUP,
        /** E：把玩家送到目标旁并等待秒破挖掉。 */
        E_MINE,
        /** E：核对（服务端真值 + 掉落物）。 */
        E_CHECK,
        /** L：挖掉一颗后必须换下一颗。 */
        L_CHECK,
        /** F：锁定一颗 → 服务端置空气 → 必须立刻放弃并换颗。 */
        F_RUN,
        /** F：核对。 */
        F_CHECK,
        /** G：再置一次空气 —— 不许卡死。 */
        G_RUN,
        /** G：核对。 */
        G_CHECK,
        /** K：反复取消寻路进程 —— 不许无限重试同一点。 */
        K_RUN,
        /** K：核对。 */
        K_CHECK,
        /** H：关 ESP —— AutoMiner 必须照常工作。 */
        H_OFF,
        /** H：核对。 */
        H_CHECK,
        /** I：重开 ESP —— 渲染层恢复且不影响当前挖矿。 */
        I_ON,
        /** I：核对。 */
        I_CHECK,
        /** J：换维度。 */
        J_NETHER,
        /** J：核对（旧身份作废并换成下界身份 / 闸门关闭 / 目标清空 / fail-closed）。 */
        J_CHECK,
        /** J：换回主世界。 */
        J_BACK,
        /** J：核对（不把旧目标带回、闸门保持关闭）。 */
        J_BACK_CHECK,
        /** 收尾。 */
        FINISHED
    }

    // ── 状态（全部只在客户端主线程推进） ─────────────────────────────────────

    private static Stage stage = Stage.TITLE;
    private static int stageTick;
    private static int worldWaitTicks;
    private static boolean worldRequested;

    /** 玩家落地点（世界出生点附近、站立处）：所有「回家」与换区传送的安全落点。 */
    private static BlockPos homePos;

    /** D 阶段「远端落脚点」（区块中心对齐，脚下有石头地板）；收尾时解除强制加载。 */
    private static int farSpotX;
    private static int farSpotY;
    private static int farSpotZ;
    /** 落脚点与目标的水平距离（报告用）。 */
    private static int farSpotDistance;

    /** E 阶段子步骤：目标区块是否已请求强制加载 / 该坐标的真值是否已探过。 */
    private static boolean eChunkRequested;
    private static boolean eTruthProbed;

    /** 「前往野外」辅助：模块指令窗口空闲的连续刻数（够久才补那一次野外传送）。 */
    private static int goWildIdleTicks;
    /** 「前往野外」辅助：本轮是否已经补过传送（一轮只补一次）。 */
    private static boolean goWildInjected;

    /** 摆装置的子步骤标记。 */
    private static boolean itemsGiven;
    private static boolean equipVerified;

    /** 本阶段关注的目标（C/D/E/F/G/K 各自的那一颗）。 */
    private static BlockPos phaseTarget;

    /** E 阶段：目标真值（挖之前读到的方块 ID）与两个子步骤标记。 */
    private static String phaseTargetBlockId = "";
    private static int retries;
    private static boolean broken;
    private static boolean brokenInventoryProbed;

    /** 本阶段窗口内「类型扫描 mine 活跃」「精确坐标寻路活跃」「秒破活跃」是否出现过。 */
    private static boolean mineSeen;
    private static boolean pathSeen;
    private static boolean fastBreakSeen;
    /** K 阶段：装置主动取消寻路进程的次数。 */
    private static int pathKills;

    /** 锁定序列（按时间顺序）与每个坐标被锁定的次数。 */
    private static final List<BlockPos> LOCK_SEQUENCE = new ArrayList<>();
    private static final Map<BlockPos, Integer> LOCK_COUNTS = new LinkedHashMap<>();

    /** 方块真值探针（集成服务端线程上读；结果回读本线程）。 */
    private static volatile boolean blockProbeDone = true;
    private static volatile String blockProbeText = "";
    /** 背包真值探针（同上）。 */
    private static volatile boolean invProbeDone = true;
    private static volatile String invProbeText = "";
    private static volatile int invProbeTargetCount;

    /**
     * 本轮追的矿物（238；{@code -Dyiyiaddon.seedpoc.target.ore=COAL} 可换，默认钻石）。
     *
     * <p>同一套 A~L 用例因此可以逐个矿物复用：装置里凡是「钻石」字样的判据与文案都改成跟着它走，
     * 锚点设置也按它换算成自动挖矿认的产物物品 id。</p>
     */
    private static final OreType TARGET_ORE = SeedPocFlags.targetRegressionOre();

    /** 本轮被追矿物的中文名（报告用）。 */
    private static String oreNameCn() {
        return TARGET_ORE.displayNameCn();
    }

    /**
     * 把「追哪种矿」换算成自动挖矿设置里的锚点（<b>时运模式存的是产物物品 id</b>）。
     *
     * <p>装置只在开发期用这张表：正式层是自己从锚点反查矿石家族（{@code blockForTarget}），
     * 装置需要的是反方向 —— 从矿物得到锚点。表里没有的矿物一律抛错，避免装置悄悄跑成别的矿物。</p>
     */
    private static String anchorTargetId(OreType oreType) {
        return switch (oreType) {
            case DIAMOND -> "minecraft:diamond";
            case REDSTONE -> "minecraft:redstone";
            case LAPIS -> "minecraft:lapis_lazuli";
            case GOLD -> "minecraft:raw_gold";
            case IRON -> "minecraft:raw_iron";
            case COPPER -> "minecraft:raw_copper";
            case COAL -> "minecraft:coal";
            case EMERALD -> "minecraft:emerald";
            case NETHER_QUARTZ -> "minecraft:quartz";
            case NETHER_GOLD -> "minecraft:gold_nugget";
            case ANCIENT_DEBRIS -> "minecraft:ancient_debris";
        };
    }

    private static final List<String> REPORT = new ArrayList<>();
    private static final List<String> VERDICTS = new ArrayList<>();

    private SeedTargetRegression() {
    }

    /** 每客户端刻推进一次（由 {@link SeedPocEntry} 在目标接入回归模式下调用）。 */
    public static void onClientTick(Minecraft client) {
        try {
            tick(client);
        } catch (Throwable error) {
            LOGGER.error("{}：回归装置异常，已停止", TAG, error);
            report("**装置异常中断：" + error.getClass().getSimpleName() + " / " + error.getMessage() + "**");
            finish();
        }
    }

    private static void tick(Minecraft client) {
        // 装置窗口没焦点时原版会弹「游戏菜单」，单人服务端随之暂停 —— 那样整条挖矿链（服务端破坏、
        // 区块发送、指令执行）全部停摆，测出来的就不是产品行为。夹具世界里没有人需要这个菜单，
        // 一律关掉它（正式产物冒烟实测：不关的话 E 阶段会「目标真值是对的但秒破始终没被激活」）。
        if (client.screen instanceof PauseScreen) {
            client.setScreen(null);
        }
        AutoMinerModule module = module();
        if (module != null && module.isEnabled()) {
            // 夹具世界里的每一次位置大跳变都是装置自己造的（换区 / 摆现场 / 换维度），
            // 因此全程替它把「这一跳是我方传送」声明掉 —— 不这么做，装置自己发的那一跳会被
            // 手动传送守卫如实读成「玩家自己传送」并把模块停掉，装置就测不到种子目标行为了。
            armOwnTeleportGrace(client);
            // 「前往野外」在任何阶段都可能被状态机自己走进（换区域 / 换维度回来 / 重启模块）：
            // 统一在这里替它把那一次野外传送补上，否则它会一直卡在「等传送生效」那一步
            advanceIntoMining(client, module);
        }
        switch (stage) {
            case TITLE -> tickTitle(client);
            case PREPARE -> tickPrepare(client);
            case A_RUN -> tickARun(client);
            case A_CHECK -> tickACheck(client);
            case B_GATE -> tickBGate(client);
            case B_CHECK -> tickBCheck(client);
            case AWAIT_VERIFIED -> tickAwaitVerified(client);
            case C_RUN -> tickCRun(client);
            case C_CHECK -> tickCCheck(client);
            case D_RUN -> tickDRun(client);
            case D_CHECK -> tickDCheck(client);
            case E_SETUP -> tickESetup(client);
            case E_MINE -> tickEMine(client);
            case E_CHECK -> tickECheck(client);
            case L_CHECK -> tickLCheck(client);
            case F_RUN -> tickFRun(client);
            case F_CHECK -> tickFCheck(client);
            case G_RUN -> tickGRun(client);
            case G_CHECK -> tickGCheck(client);
            case K_RUN -> tickKRun(client);
            case K_CHECK -> tickKCheck(client);
            case H_OFF -> tickHOff(client);
            case H_CHECK -> tickHCheck(client);
            case I_ON -> tickIOn(client);
            case I_CHECK -> tickICheck(client);
            case J_NETHER -> tickJNether(client);
            case J_CHECK -> tickJCheck(client);
            case J_BACK -> tickJBack(client);
            case J_BACK_CHECK -> tickJBackCheck(client);
            case FINISHED -> {
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  一、标题界面：复位配置 + 建夹具世界
    // ════════════════════════════════════════════════════════════════════════

    private static void tickTitle(Minecraft client) {
        if (client.level != null) {
            advance(Stage.PREPARE);
            return;
        }
        boolean atTitle = client.screen == null || client.screen instanceof TitleScreen;
        if (!atTitle || worldRequested) {
            return;
        }
        worldRequested = true;
        // 配置复位成确定值：同一运行目录的历史配置会残留，不显式设置就没有可比性
        SERVICE.setRenderPrediction(false);
        SERVICE.setSeedText("");
        SERVICE.setEnabled(false);
        AutoMinerModule module = module();
        if (module != null) {
            module.setSeedTargetMode(false);
            if (module.isEnabled()) {
                ModuleManager.setEnabled(MODULE_ID, false);
            }
        }
        report("一、装置环境");
        report("  夹具世界：" + SeedPocWorldFactory.LEVEL_ID + "（种子 " + SeedPocFlags.targetSeed()
                + "，与「预测用的种子」同一颗 ⇒ 真值可比）");
        report("  覆盖半径：" + SeedPocFlags.targetRadius() + " 区块（出厂默认 3，本装置不改默认值）");
        report("  本轮追的矿物：" + oreNameCn() + "矿（" + TARGET_ORE + "；"
                + "-Dyiyiaddon.seedpoc.target.ore=<矿物> 可换，默认钻石）");
        if (!SeedOreRegistry.supports(SeedDimensionProfile.OVERWORLD, TARGET_ORE)) {
            // 下界矿物在本装置里跑不了（本装置全程主世界，J 才切一次维度且只验身份作废）：
            // 如实报「不适用」并停，绝不拿主世界的用例去冒充下界的验收。
            report("  **本轮矿物在主世界不受支持：本装置只覆盖主世界 8 种矿物；"
                    + "下界 3 种见「多矿物矩阵」与「下界真正多人」装置**");
            VERDICTS.add("【判定】本轮矿物 " + oreNameCn() + " 不适用于目标接入装置（主世界专用）");
            finish();
            return;
        }
        report("  复位：种子挖矿关闭 / 显示预测钻石关闭 / 种子原文清空 / 种子目标模式关闭");
        if (SeedPocFlags.autoCreateWorld()) {
            SeedPocWorldFactory.createFreshWorld(client, SeedPocFlags.targetSeed());
        } else {
            report("  未开自动建世界：请手动进入单人世界（需要指令权限）");
        }
        advance(Stage.PREPARE);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  二、摆装置：点位 / 装备 / 走远
    // ════════════════════════════════════════════════════════════════════════

    private static void tickPrepare(Minecraft client) {
        if (client.level == null || client.player == null) {
            if (++worldWaitTicks > WAIT_WORLD_TICKS) {
                report("  **等待进入世界超时**");
                VERDICTS.add("【判定】装置环境：不通过（未进入世界）");
                finish();
            }
            return;
        }
        if (client.getSingleplayerServer() == null) {
            report("  **当前不是单人夹具世界（集成服务端为空）：本装置要求单人存档**");
            VERDICTS.add("【判定】装置环境：不通过（非单人世界）");
            finish();
            return;
        }
        // 等玩家真正落地再记落地点：记一个悬空点会让后面每一次「回家」都摔一次
        if (!itemsGiven && !client.player.onGround()) {
            if (++stageTick > WAIT_WORLD_TICKS) {
                report("  **等待玩家落地超时**");
                VERDICTS.add("【判定】装置环境：不通过（玩家未落地）");
                finish();
            }
            return;
        }
        if (homePos == null) {
            homePos = client.player.blockPosition();
        }
        if (!itemsGiven) {
            configureMining();
            sendCommand(client, "give @s minecraft:diamond_pickaxe 1");
            sendCommand(client, "give @s minecraft:diamond_sword 1");
            sendCommand(client, "give @s minecraft:cooked_beef 8");
            itemsGiven = true;
            report("  落地点（回家点）：" + posText(homePos));
            probeInventory(client);
            return;
        }
        if (!equipVerified) {
            if (!invProbeDone) {
                return;
            }
            boolean equipOk = invProbeText.startsWith("镐=") && !invProbeText.contains("镐=0")
                    && !invProbeText.contains("剑=0");
            if (equipOk) {
                equipVerified = true;
                report("  装备到账：" + invProbeText);
            } else if (++stageTick > WAIT_SHORT_TICKS) {
                report("  **装备未到账：" + invProbeText + "**");
                VERDICTS.add("【判定】装置环境：不通过（装备未到账）");
                finish();
                return;
            }
            probeInventory(client);
            return;
        }
        advance(Stage.A_RUN);
    }

    /**
     * 驱动状态机走完「前往野外」（装置里唯一需要「传送生效」这一步的地方）。
     *
     * <p><b>为什么要装置插手</b>：状态机判「传送生效」看的是<b>单刻位置跳变</b>，而它自己发指令后
     * 会先被 {@code ServerCommandRunner} 阻塞若干刻（等落点稳定）—— 一次瞬移（{@code /tp}、
     * {@code /spreadplayers}）恰好落在那段阻塞里时，那一跳就看不见了，于是原地等满超时反复重发。
     * 装置因此把它拆开：<b>先</b>等模块自己的野指令窗口结束（runner 空闲、状态机进到「等传送生效」那一步），
     * <b>再</b>由装置自己发一条真实的野外传送 —— 那一跳发生在没有阻塞的时刻，必然被读成「我方传送已生效」。</p>
     *
     * <p>落点用 {@code spreadplayers}：它在半径 300 格内找一处<b>能站人的地表</b>，
     * 既一定构成跳变（不会像原地传送那样被判「未生效」），又不会把人塞进石头或虚空里。</p>
     */
    private static void advanceIntoMining(Minecraft client, AutoMinerModule module) {
        if (module.fsm().state() != MinerState.GO_WILD) {
            goWildInjected = false; // 离开「前往野外」即复位：下一次换区域可以重新补一次
            return;
        }
        if (client.player == null || goWildInjected) {
            return;
        }
        if (module.getCmdManager().isCommandExecuting()) {
            goWildIdleTicks = 0;
            return;
        }
        if (++goWildIdleTicks < 10) {
            return;
        }
        // 一轮「前往野外」只补一次：补第二次时状态机多半已经进了挖矿态，
        // 那一跳会变成「挖矿途中被传送」，反而干扰测试现场
        goWildInjected = true;
        goWildIdleTicks = 0;
        armOwnTeleportGrace(client);
        sendCommand(client, "spreadplayers " + client.player.getBlockX() + " "
                + client.player.getBlockZ() + " 0 300 false @s");
    }

    /**
     * 在「目标的反方向」上选一个区块对齐的安全落脚点（D 阶段构造远端目标用）。
     *
     * <p>方向由目标相对玩家的方位决定：落脚点往<b>反方向</b>走 {@link #FAR_SPOT_DISTANCE} 格，
     * 因此它与目标的水平距离必然 ≥ 该距离 —— 换成「随机换区传送」会碰巧落在目标旁边，
     * 那种情况下目标区块根本不会卸载，测出来的不是「未加载仍能导航」。</p>
     *
     * <p>坐标取区块中心、脚下另铺一层石头（见 {@link #tickDRun}）：落点于是与地形无关，
     * 山谷里是平台、石头里是口袋、水面上是浮台，三种都不会摔、也不会卡在方块里。</p>
     */
    private static void prepareFarSpot(Minecraft client, BlockPos target) {
        BlockPos from = client.player.blockPosition();
        int signX = target.getX() >= from.getX() ? -1 : 1;
        int signZ = target.getZ() >= from.getZ() ? -1 : 1;
        int chunkX = (from.getX() + signX * FAR_SPOT_DISTANCE) >> 4;
        int chunkZ = (from.getZ() + signZ * FAR_SPOT_DISTANCE) >> 4;
        farSpotX = (chunkX << 4) + 8;
        farSpotZ = (chunkZ << 4) + 8;
        farSpotY = from.getY();
        farSpotDistance = Math.max(Math.abs(farSpotX - target.getX()), Math.abs(farSpotZ - target.getZ()));
    }

    /** 把自动挖矿配置成「普通模式 + 秒破 + 本轮矿物」的可运行最小集（只为装置服务，不改出厂默认）。 */
    private static void configureMining() {
        AutoMinerModule module = module();
        MiningSettings settings = module.settings();
        // 装置全程关着「显示预测钻石」：进世界后配置作用域会切到本存档，标题界面那次复位会被覆盖，
        // 这里再压一次 —— 于是 B~G、K 全都在「ESP 关闭」下跑，H 再单独验一遍开关两侧的行为
        SERVICE.setRenderPrediction(false);
        settings.personalMode = false;
        // 238：本轮追哪种矿由启动参数决定 ——
        //   ① 种子页只勾这一种（一次只追一种，优先级因此无歧义，也不会串到别的矿物缓存上）；
        //   ② 自动挖矿的锚点换成该矿的产物 id（时运模式存产物）。
        // 钻石轮次因此与 235 逐字一致。
        for (OreType oreType : SERVICE.supportedOres()) {
            SERVICE.setOreSelected(oreType, oreType == TARGET_ORE);
        }
        settings.overworldOreTarget = anchorTargetId(TARGET_ORE);
        settings.netherOreTarget = "";
        settings.blockTarget = "";
        settings.lootMode = LootMode.FORTUNE;
        settings.fastBreak = true;
        settings.veinMiner = true;
        settings.breakSpawner = false;    // 刷怪笼优先会额外下发一次 mine，装置不需要这条干扰
        settings.hungerThreshold = 1;     // 补给阈值压到最低：装置运行期间不触发补给流程
        settings.unloadThreshold = 36;    // 满载阈值顶到上限：装置运行期间不触发卸货流程
        settings.autoDisconnect = false;  // 装置自己摆位置，不能被低血断线打断
        settings.statusBroadcast = false; // 播报关掉：装置读的是状态，不读聊天栏
        settings.pathViewFollow = false;  // 视角跟随与本次验收无关，少一路写入者
        settings.teleportDelay = 3;
        settings.rtpCooldown = 1;
        // 关掉「传送失败自动重试」：装置在野指令窗口结束后自己补一次真实传送（见 advanceIntoMining），
        // 打开重试的话状态机会每隔几秒重发一次野指令，反而把那个窗口搅乱
        settings.teleportRetryEnabled = false;
        // 「前往野外」绑成回到落脚点（安全、已知、幂等 —— 玩家本来就在那儿时它不挪人，
        // 真正的野外传送由装置在合适时机补上，见 advanceIntoMining）
        String tp = "/tp @s " + homePos.getX() + " " + homePos.getY() + " " + homePos.getZ();
        settings.wildCommand = tp;
        settings.unloadCommand = tp;
        settings.supplyCommand = tp;
        settings.afkCommand = tp;
        settings.respawnCommand = tp;
        settings.seedTargetMode = false;
        module.persistSettings();
        // 三个点位都绑在落脚点：装置不走物流，这里只为让启动自检通过
        for (MiningPointType type : MiningPointType.values()) {
            module.pointStore().set(type, new MiningPoint(homePos.getX(), homePos.getY(), homePos.getZ(),
                    WorldIdentity.dimension(), 0f, 0f));
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  三、A：种子关闭 —— 原自动挖矿回归
    // ════════════════════════════════════════════════════════════════════════

    private static void tickARun(Minecraft client) {
        AutoMinerModule module = module();
        if (module == null) {
            report("**自动挖矿模块未注册**");
            VERDICTS.add("【判定】A：不通过（模块未注册）");
            finish();
            return;
        }
        if (!module.isEnabled() && stageTick == 0) {
            ModuleManager.setEnabled(MODULE_ID, true);
        }
        stageTick++;
        mineSeen |= mineActive(module);
        if (module.fsm().state() == MinerState.MINING && mineSeen) {
            advance(Stage.A_CHECK);
            return;
        }
        if (stageTick > WAIT_MINING_TICKS) {
            report("  **等待「普通模式下发 mine」超时**（状态机 " + module.fsm().state()
                    + "，mine 活跃：" + mineSeen + "，模块启用：" + module.isEnabled() + "）");
            VERDICTS.add("【判定】A：不通过（超时未下发 mine）");
            advance(Stage.A_CHECK);
        }
    }

    private static void tickACheck(Minecraft client) {
        AutoMinerModule module = module();
        boolean normalProvider = module.miningTargetProvider().usesBlockTypeScan();
        boolean seedRuntimeOff = !SERVICE.calculatorRunning() && SERVICE.cachedChunkCount() == 0
                && "未建立".equals(SERVICE.runtimeIdentityCn()) && SERVICE.coveragePredictedCount() == 0
                && SERVICE.coveragePendingCount() == 0;
        String targetId = targetBlockId(module);
        report("");
        report("二、A · 种子关闭时的原自动挖矿回归");
        report("  目标提供者：" + module.miningTargetProvider().modeNameCn() + " / 扫描方式："
                + module.miningTargetProvider().scanModeCn() + "（应为「普通模式 / 视野内所有目标矿」）"
                + verdict(normalProvider));
        report("  目标方块：" + targetId + "（应为" + oreNameCn() + "矿家族）");
        report("  类型扫描 mine 是否真的下发了：" + mineSeen + verdict(mineSeen));
        report("  种子运行时是否完全没被牵动：" + seedRuntimeOff
                + "（计算器运行 " + SERVICE.calculatorRunning() + " / 缓存区块 " + SERVICE.cachedChunkCount()
                + " / 运行时身份 " + SERVICE.runtimeIdentityCn() + " / 覆盖已预测 "
                + SERVICE.coveragePredictedCount() + " / 排队 " + SERVICE.coveragePendingCount() + "）"
                + verdict(seedRuntimeOff));
        VERDICTS.add("【判定】A 种子关闭原自动挖矿回归：普通模式 " + verdict(normalProvider)
                + " / 类型扫描 mine 下发 " + verdict(mineSeen)
                + " / 种子运行时零牵动 " + verdict(seedRuntimeOff));
        if (module.isEnabled()) {
            ModuleManager.setEnabled(MODULE_ID, false);
        }
        advance(Stage.B_GATE);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  四、B：闸门未开 —— 不许开始
    // ════════════════════════════════════════════════════════════════════════

    private static void tickBGate(Minecraft client) {
        AutoMinerModule module = module();
        // 打开种子运行时 + 种子目标模式：需求源为真 ⇒ 就算「显示预测钻石」关着，覆盖也照常跑
        SERVICE.setSeedText(String.valueOf(SeedPocFlags.targetSeed()));
        SERVICE.setCoverageRadius(SeedPocFlags.targetRadius());
        // 本阶段起全程 ESP 关闭：预测仍应照跑（自动挖矿是第二个消费者）—— 这里再压一次，
        // 保证报告的读数就是现场的读数
        SERVICE.setRenderPrediction(false);
        module.setSeedTargetMode(true);
        SERVICE.setEnabled(true);
        // A 阶段（普通模式）出现过类型 mine，这里清零：后面 C 阶段要的是「种子模式期间一次都没有」
        mineSeen = false;
        pathSeen = false;
        // 主动尝试启动一次：此时验证必然未通过，它必须被自检拦住
        ModuleManager.setEnabled(MODULE_ID, true);
        report("");
        report("三、B · 种子目标模式打开但闸门未开");
        report("  显示预测钻石：" + SERVICE.renderPrediction() + "（本装置全程关着 —— 预测仍应照跑，"
                + "因为自动挖矿是第二个消费者）");
        advance(Stage.B_CHECK);
    }

    private static void tickBCheck(Minecraft client) {
        AutoMinerModule module = module();
        if (stageTick == 0) {
            // 自检未通过时框架会弹一块只读面板；装置不需要它，也不允许它一直占着界面（会压掉看门狗）
            Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(null));
            stageTick++;
            return;
        }
        if (stageTick < 20) {
            stageTick++;
            return;
        }
        boolean gateClosed = !SERVICE.mayUseForAutomatedMining();
        String reason = module.seedTargetBlockReasonCn();
        boolean reasonOk = reason.contains("验证未通过");
        boolean notStarted = !module.isEnabled();
        boolean nothingIssued = !mineActive(module) && !customGoalActive(module);
        boolean runtimeWorking = !"未建立".equals(SERVICE.runtimeIdentityCn());
        report("  验证闸门 mayUseForAutomatedMining：" + SERVICE.mayUseForAutomatedMining()
                + "（应为 false）" + verdict(gateClosed));
        report("  阻止启动的原因：" + reason + verdict(reasonOk));
        report("  模块是否被启动：" + module.isEnabled() + "（应为 false）" + verdict(notStarted));
        report("  是否偷偷下发过挖掘目标：" + (mineActive(module) ? "类型 mine 有" : "类型 mine 无") + " / "
                + (customGoalActive(module) ? "精确寻路有" : "精确寻路无") + verdict(nothingIssued));
        report("  种子运行时是否在替验证攒证据（闸门未开也必须预测）：运行时身份 "
                + SERVICE.runtimeIdentityCn() + " / 缓存区块 " + SERVICE.cachedChunkCount()
                + " / 覆盖已预测 " + SERVICE.coveragePredictedCount() + "/" + SERVICE.coverageTargetCount()
                + verdict(runtimeWorking));
        report("  验证读数：" + SERVICE.validationDiagnosticsCn());
        VERDICTS.add("【判定】B 未验证不得开始：闸门关 " + verdict(gateClosed)
                + " / 自检拦住 " + verdict(notStarted) + " / 未下发任何目标 " + verdict(nothingIssued)
                + " / 运行时仍在预测 " + verdict(runtimeWorking));
        advance(Stage.AWAIT_VERIFIED);
    }

    private static void tickAwaitVerified(Minecraft client) {
        if (SERVICE.mayUseForAutomatedMining()) {
            AutoMinerModule module = module();
            report("");
            report("  验证通过：状态「" + SERVICE.validationSnapshot().stateCn() + "」，用时 "
                    + (stageTick / 20) + " 秒；" + SERVICE.validationDiagnosticsCn());
            VERDICTS.add("【判定】闸门打开（已验证）：通过（用时 " + (stageTick / 20) + " 秒）");
            // 闸门一开就由装置自己启动：不依赖框架等待队列的放行时机（野外传送由 C_RUN 补）
            if (module != null && !module.isEnabled()) {
                ModuleManager.setEnabled(MODULE_ID, true);
            }
            advance(Stage.C_RUN);
            return;
        }
        if (stageTick % 200 == 0) {
            report("  …… 等待验证（" + (stageTick / 20) + " 秒）状态「" + SERVICE.validationSnapshot().stateCn()
                    + "」缓存区块 " + SERVICE.cachedChunkCount() + " 覆盖已预测 "
                    + SERVICE.coveragePredictedCount() + "/" + SERVICE.coverageTargetCount()
                    + " 正在预测 " + SERVICE.coverageActiveChunkCn());
        }
        if (++stageTick > WAIT_VERIFIED_TICKS) {
            report("  **等待「已验证」超时（" + (WAIT_VERIFIED_TICKS / 20) + " 秒）："
                    + SERVICE.validationDiagnosticsCn() + "**");
            VERDICTS.add("【判定】闸门打开（已验证）：不通过（等待超时）");
            finish();
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  五、C：锁定真实种子目标
    // ════════════════════════════════════════════════════════════════════════

    private static void tickCRun(Minecraft client) {
        AutoMinerModule module = module();
        // 闸门已开：没在跑就重启（每 10 秒最多一次）
        if (!module.isEnabled() && stageTick % 200 == 0 && SERVICE.mayUseForAutomatedMining()) {
            ModuleManager.setEnabled(MODULE_ID, true);
        }
        stageTick++;
        mineSeen |= mineActive(module);
        pathSeen |= customGoalActive(module);
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        if (module.fsm().state() == MinerState.MINING && target != null && !mineSeen) {
            phaseTarget = target;
            noteLock(target);
            advance(Stage.C_CHECK);
            return;
        }
        if (stageTick > WAIT_TARGET_TICKS) {
            report("  **等待「状态机 MINING + 锁定种子目标」超时**（状态机 " + module.fsm().state()
                    + "，当前目标 " + posText(target) + "，类型 mine 出现过：" + mineSeen
                    + "，模块启用：" + module.isEnabled() + "）");
            VERDICTS.add("【判定】C：不通过（超时未锁定目标）");
            finish();
        }
    }

    private static void tickCCheck(Minecraft client) {
        AutoMinerModule module = module();
        boolean seedProvider = !module.miningTargetProvider().usesBlockTypeScan();
        boolean targetIsPredictedDiamond = isPredictedTarget(phaseTarget);
        boolean inCoverage = inCoverage(phaseTarget);
        boolean noTypeScan = !mineSeen;
        boolean navigating = pathSeen || withinReach(client, phaseTarget);
        report("");
        report("四、C · 正确种子已验证后自动选择真实 Diamond Seed Target");
        report("  目标提供者：" + module.miningTargetProvider().modeNameCn() + " / 扫描方式："
                + module.miningTargetProvider().scanModeCn() + verdict(seedProvider));
        report("  锁定目标：" + posText(phaseTarget) + "（玩家 " + playerPosText(client) + "，区块距离 "
                + chunkDistance(client, phaseTarget) + "）");
        report("  该坐标属于正式预测集里的" + oreNameCn() + "：" + targetIsPredictedDiamond + verdict(targetIsPredictedDiamond));
        report("  落在当前覆盖方框内：" + inCoverage + verdict(inCoverage));
        report("  期间是否出现过「按矿物类型 mine」（不许偷偷 fallback）：" + mineSeen + verdict(noTypeScan));
        report("  是否按精确坐标工作（自定义目标 / 已在交互距离内）：" + navigating + verdict(navigating));
        VERDICTS.add("【判定】C 选定真实种子目标：种子模式 " + verdict(seedProvider)
                + " / 目标在预测集内 " + verdict(targetIsPredictedDiamond)
                + " / 覆盖内 " + verdict(inCoverage) + " / 无类型扫描 " + verdict(noTypeScan));
        if (!seedProvider || !targetIsPredictedDiamond || !noTypeScan) {
            finish();
            return;
        }
        mineSeen = false;
        pathSeen = false;
        advance(Stage.D_RUN);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  六、D：目标未加载 —— 能按精确坐标导航过去
    // ════════════════════════════════════════════════════════════════════════

    private static void tickDRun(Minecraft client) {
        AutoMinerModule module = module();
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        if (target == null || withinReach(client, target)) {
            // 等一颗「够不着」的目标（近处那颗会被秒破立刻挖掉，测不了导航）
            if (++stageTick > WAIT_TARGET_TICKS) {
                report("  **等待「远距离目标」超时（当前目标 " + posText(target) + "）**");
                VERDICTS.add("【判定】D：不通过（没有可用的远端目标）");
                finish();
            }
            return;
        }
        phaseTarget = target;
        noteLock(target);
        // 三步分开发指令：强制加载 → 等半秒 → 挖口袋 → 再等半秒 → 传送。
        // 三条指令同刻整批发出去时，区块刚被强制加载未必已经就绪，fill 会直接失败，
        // 人就可能被 tp 进石头里 —— 那样测到的是「卡在方块里」，不是「远端目标导航」。
        if (stageTick == 0) {
            prepareFarSpot(client, target);
            sendCommand(client, "forceload add " + farSpotX + " " + farSpotZ);
            mineSeen = false;
            pathSeen = false;
            report("");
            report("五、D · 目标未加载（远端 Seed 目标）");
            report("  锁定目标：" + posText(phaseTarget) + "（玩家 " + playerPosText(client) + "，区块距离 "
                    + chunkDistance(client, phaseTarget) + "）");
        } else if (stageTick == 10) {
            sendCommand(client, "fill " + (farSpotX - 2) + " " + farSpotY + " " + (farSpotZ - 2) + " "
                    + (farSpotX + 2) + " " + (farSpotY + 4) + " " + (farSpotZ + 2) + " minecraft:air");
            sendCommand(client, "fill " + (farSpotX - 2) + " " + (farSpotY - 1) + " " + (farSpotZ - 2) + " "
                    + (farSpotX + 2) + " " + (farSpotY - 1) + " " + (farSpotZ + 2) + " minecraft:stone");
        } else if (stageTick == 20) {
            armOwnTeleportGrace(client);
            sendCommand(client, "tp @s " + (farSpotX + 0.5) + " " + farSpotY + " " + (farSpotZ + 0.5));
            report("  把玩家挪到 " + posText(new BlockPos(farSpotX, farSpotY, farSpotZ)) + "（与目标水平相距 "
                    + farSpotDistance + " 格，落脚点已挖成空气口袋 ⇒ 目标区块应在客户端卸载）");
            advance(Stage.D_CHECK);
            return;
        }
        stageTick++;
    }

    private static void tickDCheck(Minecraft client) {
        AutoMinerModule module = module();
        pathSeen |= customGoalActive(module);
        mineSeen |= mineActive(module);
        if (++stageTick < WAIT_SHORT_TICKS) {
            return;
        }
        boolean unloaded = !client.level.isLoaded(phaseTarget);
        BlockPos held = module.miningTargetProvider().lockedTargetOrNull();
        boolean stillHeld = phaseTarget.equals(held);
        boolean noTypeScan = !mineSeen;
        report("  目标区块是否已卸载：" + unloaded + "（客户端 isLoaded=" + client.level.isLoaded(phaseTarget)
                + "，玩家 " + playerPosText(client) + "）" + verdict(unloaded));
        report("  提供者是否仍持有同一颗目标：" + stillHeld + "（当前 " + posText(held) + "）" + verdict(stillHeld));
        report("  是否按精确坐标继续导航（自定义目标进程活跃）：" + pathSeen + "（类型 mine 出现过："
                + mineSeen + "）" + verdict(pathSeen && noTypeScan));
        VERDICTS.add("【判定】D 目标未加载仍能导航：区块已卸载 " + verdict(unloaded)
                + " / 目标保持 " + verdict(stillHeld) + " / 精确坐标寻路 " + verdict(pathSeen)
                + " / 无类型扫描 " + verdict(noTypeScan));
        sendCommand(client, "forceload remove " + farSpotX + " " + farSpotZ);
        if (!unloaded || !stillHeld) {
            finish();
            return;
        }
        // 这里刻意<b>不</b>停男中音：那一下会被提供者读成「这一颗寻路失败」并消耗一次重试
        // （见 SeedMiningTargetProvider 的 MAX_PATH_ISSUES）——装置不该去动被测对象的目标状态
        advance(Stage.E_SETUP);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  七、E：加载后实际是本轮矿物 → 交给现有挖矿链
    // ════════════════════════════════════════════════════════════════════════

    private static void tickESetup(Minecraft client) {
        AutoMinerModule module = module();
        BlockPos held = module.miningTargetProvider().lockedTargetOrNull();
        if (phaseTarget == null || !phaseTarget.equals(held)) {
            if (held == null) {
                if (++stageTick > WAIT_TARGET_TICKS) {
                    report("  **E 等待目标超时**");
                    VERDICTS.add("【判定】E：不通过（没有可用目标）");
                    finish();
                }
                return;
            }
            phaseTarget = held;
            phaseTargetBlockId = "";
            eChunkRequested = false;
            eTruthProbed = false;
            noteLock(phaseTarget);
            stageTick = 0;
            return;
        }
        stageTick++;
        // 玩家此刻离目标几百格：区块没加载时 getBlockState 只会读到虚空，必须先把区块要过来
        if (!eChunkRequested) {
            eChunkRequested = true;
            sendCommand(client, "forceload add " + phaseTarget.getX() + " " + phaseTarget.getZ());
            report("  目标 " + posText(phaseTarget) + "：已请求强制加载其区块（否则读到的只是虚空）");
            return;
        }
        if (stageTick < 20) {
            return; // 等强制加载生效
        }
        if (!eTruthProbed) {
            eTruthProbed = true;
            probeBlockState(client, phaseTarget);
            return;
        }
        if (!blockProbeDone) {
            return;
        }
        if (!isTargetOreId(blockProbeText)) {
            // 真值不是本轮矿物：这一颗按 MISSING 处理（换下一颗再试），最多三次
            retries++;
            report("  目标 " + posText(phaseTarget) + " 的真值是 " + blockProbeText
                    + "（不是" + oreNameCn() + "矿）—— 换下一颗重试（" + retries + "/3）");
            sendCommand(client, "forceload remove " + phaseTarget.getX() + " " + phaseTarget.getZ());
            if (retries >= 3) {
                VERDICTS.add("【判定】E：不执行（三个目标的真值都不是" + oreNameCn() + "矿）");
                finish();
                return;
            }
            module.miningTargetProvider().reissue();
            phaseTarget = null;
            stageTick = 0;
            return;
        }
        phaseTargetBlockId = blockProbeText;
        int x = phaseTarget.getX();
        int y = phaseTarget.getY();
        int z = phaseTarget.getZ();
        // 3×3×3 空气口袋 + 把这颗矿原样放回：秒破的方向与交互距离于是都是真实几何
        sendCommand(client, "fill " + (x - 1) + " " + y + " " + (z - 1) + " " + (x + 1) + " " + (y + 2)
                + " " + (z + 1) + " minecraft:air");
        sendCommand(client, "setblock " + x + " " + y + " " + z + " " + phaseTargetBlockId);
        report("  目标真值：" + phaseTargetBlockId + "（" + posText(phaseTarget) + "）；已挖出 3×3×3 空气口袋"
                + "并把这颗矿原样放回（保证秒破的方向与交互距离都是真实几何）");
        broken = false;
        brokenInventoryProbed = false;
        advance(Stage.E_MINE);
    }

    private static void tickEMine(Minecraft client) {
        stageTick++;
        if (stageTick == 1) {
            // 把玩家送进口袋：站在那颗矿上（脚下就是目标格）
            armOwnTeleportGrace(client);
            sendCommand(client, "tp @s " + (phaseTarget.getX() + 0.5) + " " + (phaseTarget.getY() + 1)
                    + " " + (phaseTarget.getZ() + 0.5));
            return;
        }
        fastBreakSeen |= MiningFastBreakController.instance().isActive();
        if (broken) {
            if (!brokenInventoryProbed) {
                brokenInventoryProbed = true;
                probeInventory(client);
                return;
            }
            if (invProbeDone) {
                advance(Stage.E_CHECK);
            }
            return;
        }
        if (blockProbeDone && stageTick % 5 == 2) {
            probeBlockState(client, phaseTarget);
            return;
        }
        if (blockProbeDone && "minecraft:air".equals(blockProbeText)) {
            broken = true;
            return;
        }
        if (stageTick > WAIT_MINE_TICKS) {
            report("  **等待秒破挖掉 " + posText(phaseTarget) + " 超时**（服务端真值 " + blockProbeText
                    + "，玩家 " + playerPosText(client) + "，秒破活跃出现过：" + fastBreakSeen
                    + "，模块启用：" + module().isEnabled() + "，状态机：" + module().fsm().state() + "）");
            VERDICTS.add("【判定】E：不通过（目标未被挖掉）");
            finish();
        }
    }

    private static void tickECheck(Minecraft client) {
        report("  服务端真值（目标格）：" + blockProbeText + verdict(broken));
        report("  秒破通道是否被用过：" + fastBreakSeen + verdict(fastBreakSeen));
        report("  背包读数（服务端）：" + invProbeText);
        // 松手：撤掉强制加载（玩家就在旁边，区块照常是加载的）
        sendCommand(client, "forceload remove " + phaseTarget.getX() + " " + phaseTarget.getZ());
        VERDICTS.add("【判定】E 加载后实际" + oreNameCn() + "矿走现有挖矿链：目标被挖掉 " + verdict(broken)
                + " / 秒破通道 " + verdict(fastBreakSeen));
        advance(Stage.L_CHECK);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  八、L：挖掉一颗 → 换下一颗
    // ════════════════════════════════════════════════════════════════════════

    private static void tickLCheck(Minecraft client) {
        AutoMinerModule module = module();
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        if (target != null && !target.equals(phaseTarget)) {
            noteLock(target);
            report("");
            report("六、L · 挖掉一颗后换下一颗");
            report("  已挖掉：" + posText(phaseTarget) + "（真值 " + blockProbeText + "）→ 新目标："
                    + posText(target));
            report("  锁定序列（本装置观测）：" + describeLocks());
            VERDICTS.add("【判定】L 挖掉一颗换下一颗：通过（新目标 " + posText(target) + "）");
            advance(Stage.F_RUN);
            return;
        }
        if (++stageTick > WAIT_TARGET_TICKS) {
            report("");
            report("六、L · 挖掉一颗后换下一颗");
            report("  **超时：挖掉 " + posText(phaseTarget) + " 后没有换下一颗（当前 " + posText(target) + "）**");
            VERDICTS.add("【判定】L：不通过（未换目标）");
            advance(Stage.F_RUN);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  九、F：实际 MISSING → 立即放弃并换下一颗
    // ════════════════════════════════════════════════════════════════════════

    private static void tickFRun(Minecraft client) {
        AutoMinerModule module = module();
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        if (target == null || withinReach(client, target) || !isPredictedTarget(target)) {
            if (++stageTick > WAIT_TARGET_TICKS) {
                report("  **F 等待可用目标超时（当前 " + posText(target) + "）**");
                VERDICTS.add("【判定】F：不通过（没有可用目标）");
                finish();
            }
            return;
        }
        phaseTarget = target;
        noteLock(target);
        sendCommand(client, "forceload add " + target.getX() + " " + target.getZ());
        sendCommand(client, "setblock " + target.getX() + " " + target.getY() + " " + target.getZ()
                + " minecraft:air");
        report("");
        report("七、F · 加载后实际 MISSING（/setblock air）");
        report("  目标：" + posText(phaseTarget) + "（区块已加载：" + client.level.isLoaded(phaseTarget)
                + "）→ 服务端已置为空气");
        advance(Stage.F_CHECK);
    }

    private static void tickFCheck(Minecraft client) {
        AutoMinerModule module = module();
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        boolean swapped = target != null && !target.equals(phaseTarget);
        if (!swapped && ++stageTick < WAIT_SHORT_TICKS) {
            return;
        }
        OreObservationState state = SERVICE.observationState(phaseTarget);
        int repeats = countLock(phaseTarget);
        boolean notReselected = repeats <= 1;
        report("  观察层对该格的结论：" + state.displayNameCn() + "（期望「当前缺失」）"
                + verdict(state == OreObservationState.MISSING));
        report("  是否立刻换了下一颗：" + swapped + "（当前 " + posText(target) + "）" + verdict(swapped));
        report("  被放弃的坐标是否被反复重选：" + repeats + " 次" + verdict(notReselected));
        report("  锁定序列：" + describeLocks());
        sendCommand(client, "forceload remove " + phaseTarget.getX() + " " + phaseTarget.getZ());
        VERDICTS.add("【判定】F MISSING 立刻放弃换颗：观察为缺失 "
                + verdict(state == OreObservationState.MISSING) + " / 已换目标 " + verdict(swapped)
                + " / 未反复重选 " + verdict(notReselected));
        if (swapped) {
            noteLock(target);
        }
        advance(Stage.G_RUN);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  十、G：手动置空气不得卡死
    // ════════════════════════════════════════════════════════════════════════

    private static void tickGRun(Minecraft client) {
        AutoMinerModule module = module();
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        if (target == null || withinReach(client, target) || !isPredictedTarget(target)) {
            if (++stageTick > WAIT_TARGET_TICKS) {
                report("  **G 等待可用目标超时（当前 " + posText(target) + "）**");
                VERDICTS.add("【判定】G：不通过（没有可用目标）");
                finish();
            }
            return;
        }
        phaseTarget = target;
        noteLock(target);
        sendCommand(client, "forceload add " + target.getX() + " " + target.getZ());
        sendCommand(client, "setblock " + target.getX() + " " + target.getY() + " " + target.getZ()
                + " minecraft:air");
        report("");
        report("八、G · 手动 /setblock air 不得卡死");
        report("  目标：" + posText(phaseTarget));
        advance(Stage.G_CHECK);
    }

    private static void tickGCheck(Minecraft client) {
        AutoMinerModule module = module();
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        boolean swapped = target != null && !target.equals(phaseTarget);
        if (!swapped && ++stageTick < WAIT_SHORT_TICKS) {
            return;
        }
        boolean alive = module.isEnabled();
        boolean mining = module.fsm().state() == MinerState.MINING;
        boolean notReselected = countLock(phaseTarget) <= 1;
        report("  模块是否仍在运行：" + alive + " / 状态机：" + module.fsm().state()
                + verdict(alive && mining));
        report("  是否换了下一颗：" + swapped + "（当前 " + posText(target) + "）" + verdict(swapped));
        report("  被置空气的坐标是否被反复重选：" + countLock(phaseTarget) + " 次" + verdict(notReselected));
        report("  锁定序列：" + describeLocks());
        sendCommand(client, "forceload remove " + phaseTarget.getX() + " " + phaseTarget.getZ());
        VERDICTS.add("【判定】G 手动置空气不卡死：模块仍在跑 " + verdict(alive && mining)
                + " / 换下一颗 " + verdict(swapped) + " / 未重选同一点 " + verdict(notReselected));
        advance(Stage.K_RUN);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  十一、K：寻路失败不无限重试同一点
    // ════════════════════════════════════════════════════════════════════════

    private static void tickKRun(Minecraft client) {
        AutoMinerModule module = module();
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        if (phaseTarget == null && target != null && !withinReach(client, target)) {
            phaseTarget = target;
            pathKills = 0;
            noteLock(target);
            report("");
            report("九、K · 寻路失败不得无限重试同一点");
            report("  目标：" + posText(phaseTarget) + "（玩家 " + playerPosText(client) + "，区块距离 "
                    + chunkDistance(client, phaseTarget) + "）");
            report("  方法：每次寻路刚起来就取消掉（等价于男中音算不出路 —— 提供者判「进程没了」是同一段代码）");
        }
        if (++stageTick > WAIT_PATH_KILL_TICKS) {
            report("  **超时：反复取消耗尽寻路后仍未放弃该目标**");
            VERDICTS.add("【判定】K：不通过（超时未放弃）");
            finish();
            return;
        }
        if (phaseTarget == null) {
            return;
        }
        if (target == null || !target.equals(phaseTarget)) {
            advance(Stage.K_CHECK);
            return;
        }
        if (customGoalActive(module)) {
            module.getBaritone().stop();
            pathKills++;
            if (pathKills > 8) {
                report("  **取消次数已达 8 次而目标仍未放弃：无限重试**");
                VERDICTS.add("【判定】K：不通过（无限重试同一点）");
                finish();
            }
        }
    }

    private static void tickKCheck(Minecraft client) {
        AutoMinerModule module = module();
        BlockPos now = module.miningTargetProvider().lockedTargetOrNull();
        boolean swapped = now != null && !now.equals(phaseTarget);
        boolean bounded = pathKills <= 4;
        boolean notReselected = countLock(phaseTarget) <= 1;
        report("  装置在「同一坐标」上取消掉的寻路次数：" + pathKills + "（提供者上限 3 次重发）"
                + verdict(bounded));
        report("  是否换下一颗：" + swapped + "（当前 " + posText(now) + "）" + verdict(swapped));
        report("  该坐标是否被反复重选：" + countLock(phaseTarget) + " 次" + verdict(notReselected));
        report("  锁定序列：" + describeLocks());
        VERDICTS.add("【判定】K 寻路失败不无限重试：重试有上界 " + verdict(bounded)
                + " / 已换目标 " + verdict(swapped) + " / 未重选同一点 " + verdict(notReselected));
        if (swapped) {
            noteLock(now);
        }
        advance(Stage.H_OFF);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  十二、H：关闭 ESP 不影响 AutoMiner
    // ════════════════════════════════════════════════════════════════════════

    private static void tickHOff(Minecraft client) {
        AutoMinerModule module = module();
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        if (target == null) {
            if (++stageTick > WAIT_TARGET_TICKS) {
                report("  **H 等待目标超时**");
                VERDICTS.add("【判定】H：不通过（没有可用目标）");
                finish();
            }
            return;
        }
        phaseTarget = target;
        noteLock(target);
        SERVICE.setRenderPrediction(false);
        report("");
        report("十、H · 关闭「显示预测钻石」（ESP）后自动挖矿是否继续");
        report("  当前目标：" + posText(phaseTarget) + "；缓存区块 " + SERVICE.cachedChunkCount());
        advance(Stage.H_CHECK);
    }

    private static void tickHCheck(Minecraft client) {
        AutoMinerModule module = module();
        if (++stageTick < WAIT_SHORT_TICKS) {
            return;
        }
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        boolean espOff = !SERVICE.renderPrediction();
        boolean snapshotEmpty = SERVICE.renderSnapshot().empty();
        boolean cacheKept = SERVICE.cachedChunkCount() > 0;
        boolean coverageAlive = SERVICE.coveragePredictedCount() > 0 || SERVICE.coverageStillWorking();
        boolean stillMining = module.isEnabled() && target != null
                && module.fsm().state() == MinerState.MINING;
        boolean movingOn = target != null && !phaseTarget.equals(target);
        report("  ESP 开关：" + SERVICE.renderPrediction() + "（应关）" + verdict(espOff));
        report("  渲染快照是否已空（世界里不画框）：" + snapshotEmpty + verdict(snapshotEmpty));
        report("  预测缓存是否仍在（不许被 ESP 关掉）：" + cacheKept + " / 覆盖是否仍在工作：" + coverageAlive
                + "（已预测 " + SERVICE.coveragePredictedCount() + "/" + SERVICE.coverageTargetCount()
                + "，排队 " + SERVICE.coveragePendingCount() + "）" + verdict(cacheKept && coverageAlive));
        report("  自动挖矿是否照常（模块在跑 + 状态机在挖 + 手上仍有一颗预测目标）：" + module.isEnabled()
                + " / " + module.fsm().state() + " / " + posText(target)
                + verdict(stillMining));
        report("    这一窗口里目标是否推进过：" + movingOn + "（关 ESP 前 " + posText(phaseTarget)
                + " ⇒ 关掉后 " + posText(target) + "；推进说明它仍在按预测坐标继续挖，不是停摆）");
        VERDICTS.add("【判定】H 关 ESP 不影响：ESP 关 " + verdict(espOff) + " / 缓存保留 " + verdict(cacheKept)
                + " / 覆盖继续 " + verdict(coverageAlive) + " / 挖矿继续 " + verdict(stillMining));
        advance(Stage.I_ON);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  十三、I：重开 ESP 恢复渲染且不影响挖矿
    // ════════════════════════════════════════════════════════════════════════

    private static void tickIOn(Minecraft client) {
        SERVICE.setRenderPrediction(true);
        report("");
        report("十一、I · 重新打开 ESP");
        advance(Stage.I_CHECK);
    }

    private static void tickICheck(Minecraft client) {
        AutoMinerModule module = module();
        boolean espOn = SERVICE.renderPrediction();
        // 等渲染快照重建（重开时由 applyRendererAttachment 标脏并立刻补建一次）
        if (++stageTick < WAIT_SHORT_TICKS && SERVICE.renderSnapshot().empty()) {
            return;
        }
        boolean rendered = !SERVICE.renderSnapshot().empty();
        BlockPos target = module.miningTargetProvider().lockedTargetOrNull();
        boolean stillMining = module.isEnabled() && target != null
                && module.fsm().state() == MinerState.MINING;
        report("  ESP 开关：" + SERVICE.renderPrediction() + verdict(espOn));
        report("  渲染快照是否已重建（世界里重新有框）：" + rendered + "（" + SERVICE.renderSnapshot().size()
                + " 条）" + verdict(rendered));
        report("  当前挖矿是否被打断（模块在跑 + 状态机在挖 + 手上仍有一颗预测目标）：" + module.isEnabled()
                + " / " + module.fsm().state() + " / " + posText(target) + verdict(stillMining));
        VERDICTS.add("【判定】I 重开 ESP 恢复：渲染层恢复 " + verdict(rendered)
                + " / 挖矿未受影响 " + verdict(stillMining));
        advance(Stage.J_NETHER);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  十四、J：换维度 → 旧身份作废 + 目标清空 + 闸门关闭（fail-closed）
    // ════════════════════════════════════════════════════════════════════════

    private static void tickJNether(Minecraft client) {
        if (stageTick == 0) {
            report("");
            report("十二、J · 换维度（主世界 → 下界 → 主世界）");
            report("  切换前：运行时身份 " + SERVICE.runtimeIdentityCn() + " / 当前目标 "
                    + posText(module().miningTargetProvider().lockedTargetOrNull()));
            armOwnTeleportGrace(client);
            sendCommand(client, "execute in minecraft:the_nether run tp @s 0 70 0");
        }
        if (++stageTick > WAIT_SHORT_TICKS) {
            report("  **等待进入下界超时**");
            VERDICTS.add("【判定】J：不通过（未进入下界）");
            finish();
            return;
        }
        if (client.level != null && Level.NETHER.equals(client.level.dimension())) {
            advance(Stage.J_CHECK);
        }
    }

    private static void tickJCheck(Minecraft client) {
        AutoMinerModule module = module();
        if (++stageTick < 40) {
            return;
        }
        // 236 口径修正：下界不再「不受支持」，而是换成了另一个受支持维度。
        // 因此本项要核对的实质没有变 ——「旧身份必须整批作废、旧维度的结果一条都不许带过来」——
        // 只是判据换成 236 的形态：身份必须是下界身份（≠ 切换前的主世界身份），
        // 且预测缓存里不允许残留主世界条目。
        String identity = SERVICE.runtimeIdentityCn();
        boolean identitySwitched = identity.contains("minecraft:the_nether");
        boolean gateClosed = !SERVICE.mayUseForAutomatedMining();
        boolean targetCleared = module.miningTargetProvider().lockedTargetOrNull() == null;
        boolean noOverworldLeft = SERVICE.cachedPredictions().stream().allMatch(result ->
                "minecraft:the_nether".equals(result.request().dimension().identifier().toString()));
        String reason = module.seedTargetBlockReasonCn();
        boolean stopped = !module.isEnabled();
        report("  下界：维度 " + SERVICE.dimensionDisplayCn() + " / 运行时身份 " + identity
                + verdict(identitySwitched));
        report("  闸门 mayUseForAutomatedMining：" + SERVICE.mayUseForAutomatedMining() + "（应关）"
                + verdict(gateClosed));
        report("  当前目标是否清空：" + targetCleared + " / 预测缓存里是否还有主世界条目："
                + !noOverworldLeft + "（缓存 " + SERVICE.cachedChunkCount() + " 个区块，"
                + "全部属于下界 " + verdict(noOverworldLeft) + "）");
        report("  模块是否被 fail-closed 停掉：" + stopped + "；停机原因：" + reason);
        report("    （口径：换维度 ⇒ 旧身份整批作废 ⇒ 验证随之清空 ⇒ 提供者报「未验证」并停机，"
                + "绝不退回按矿物类型扫描；236 起下界也是受支持维度，因此预测 / 观察 / ESP 会以"
                + "「下界身份」重新开始；238 起下界的自动挖矿由「下界专属验证 + 证据覆盖该矿」放行，"
                + "刚切过去必然是未验证 ⇒ 本装置此处仍应看到 fail-closed 停机）");
        VERDICTS.add("【判定】J 换维度：身份换成下界（主世界身份作废）" + verdict(identitySwitched)
                + " / 闸门关闭 " + verdict(gateClosed)
                + " / 目标清空 " + verdict(targetCleared)
                + " / 无主世界残留条目 " + verdict(noOverworldLeft)
                + " / fail-closed 停机 " + verdict(stopped));
        advance(Stage.J_BACK);
    }

    private static void tickJBack(Minecraft client) {
        if (stageTick == 0) {
            armOwnTeleportGrace(client);
            sendCommand(client, "execute in minecraft:overworld run tp @s " + homePos.getX() + " "
                    + homePos.getY() + " " + homePos.getZ());
        }
        if (++stageTick > WAIT_SHORT_TICKS) {
            report("  **等待返回主世界超时**");
            VERDICTS.add("【判定】J 返回主世界：不通过（超时）");
            finish();
            return;
        }
        if (client.level != null && Level.OVERWORLD.equals(client.level.dimension())) {
            advance(Stage.J_BACK_CHECK);
        }
    }

    private static void tickJBackCheck(Minecraft client) {
        AutoMinerModule module = module();
        if (++stageTick < 60) {
            return;
        }
        boolean identityRebuilt = !"未建立".equals(SERVICE.runtimeIdentityCn());
        boolean noStaleTarget = module.miningTargetProvider().lockedTargetOrNull() == null;
        boolean demandAlive = module.wantsSeedRuntime();
        report("  返回主世界：运行时身份 " + SERVICE.runtimeIdentityCn() + verdict(identityRebuilt));
        report("  是否把旧世界的目标带了过来："
                + posText(module.miningTargetProvider().lockedTargetOrNull()) + verdict(noStaleTarget));
        report("  闸门 mayUseForAutomatedMining：" + SERVICE.mayUseForAutomatedMining()
                + "（本装置不判它：验证锁存绑定运行时身份，换维度会把证据与锁存一起清空并从零重收 ——"
                + "回到同一世界+同一种子后若又收满阈值，闸门重新打开正是 234 定案的既定语义）");
        report("  自动挖矿是否仍需要种子预测（需求源）：" + demandAlive + verdict(demandAlive));
        VERDICTS.add("【判定】J 返回主世界：身份重建 " + verdict(identityRebuilt) + " / 未带旧目标 "
                + verdict(noStaleTarget) + " / 需求源在位 " + verdict(demandAlive));
        finish();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  工具
    // ════════════════════════════════════════════════════════════════════════

    private static void advance(Stage next) {
        if (stage == Stage.FINISHED) {
            return;
        }
        stage = next;
        stageTick = 0;
    }

    private static AutoMinerModule module() {
        return ModuleManager.byId(MODULE_ID) instanceof AutoMinerModule module ? module : null;
    }

    /** 发一条指令（与玩家自己敲走同一条发包路径）。 */
    private static void sendCommand(Minecraft client, String command) {
        if (client.player == null) {
            return;
        }
        client.player.connection.sendCommand(command);
    }

    /** 声明「接下来这一跳是我方传送」：不这么做，装置自己发的 /tp 会被手动传送守卫判成玩家传送。 */
    private static void armOwnTeleportGrace(Minecraft client) {
        AutoMinerModule module = module();
        if (module != null) {
            module.fsm().armOwnTeleportGraceForDev();
        }
    }

    /** 男中音「按矿物类型 mine」的进程是否活跃（种子模式必须恒为否）。 */
    private static boolean mineActive(AutoMinerModule module) {
        try {
            IBaritone baritone = module.getBaritone().getBaritoneInstance();
            return baritone != null && baritone.getMineProcess().isActive();
        } catch (Throwable ignored) {
            return false;
        }
    }

    /** 男中音「精确坐标自定义目标」的进程是否活跃。 */
    private static boolean customGoalActive(AutoMinerModule module) {
        try {
            IBaritone baritone = module.getBaritone().getBaritoneInstance();
            return baritone != null && baritone.getCustomGoalProcess().isActive();
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static boolean withinReach(Minecraft client, BlockPos pos) {
        LocalPlayer player = client.player;
        return player != null && pos != null && player.isWithinBlockInteractionRange(pos, 1.0);
    }

    /** 该坐标是否落在当前覆盖方框内（与目标选择同一口径：区块切比雪夫距离）。 */
    private static boolean inCoverage(BlockPos pos) {
        Minecraft client = Minecraft.getInstance();
        if (pos == null || client.player == null) {
            return false;
        }
        int radius = SERVICE.coverageRadius();
        int centerX = client.player.blockPosition().getX() >> 4;
        int centerZ = client.player.blockPosition().getZ() >> 4;
        return Math.abs((pos.getX() >> 4) - centerX) <= radius
                && Math.abs((pos.getZ() >> 4) - centerZ) <= radius;
    }

    private static int chunkDistance(Minecraft client, BlockPos pos) {
        if (pos == null || client.player == null) {
            return -1;
        }
        int dx = Math.abs((pos.getX() >> 4) - (client.player.blockPosition().getX() >> 4));
        int dz = Math.abs((pos.getZ() >> 4) - (client.player.blockPosition().getZ() >> 4));
        return Math.max(dx, dz);
    }

    /** 该坐标是不是正式预测集里的本轮矿物。 */
    private static boolean isPredictedTarget(BlockPos pos) {
        if (pos == null) {
            return false;
        }
        for (PredictionResult result : SERVICE.cachedPredictions()) {
            if (result == null || result.failed()) {
                continue;
            }
            for (PredictedOre ore : result.ores()) {
                if (ore.oreType() == TARGET_ORE && ore.position().equals(pos)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 该方块 id 是不是本轮被追矿物的任一形态（含深层变种）。
     *
     * <p>判定直接走正式层的矿物定义（{@code SeedOreDefinition#matches}），装置不另写一份方块名单
     * —— 否则 238 放开多矿物后，装置会拿旧名单把「煤 / 红石也都是目标矿」判成 MISSING。</p>
     */
    private static boolean isTargetOreId(String blockId) {
        if (blockId == null || blockId.isBlank()) {
            return false;
        }
        Identifier id = Identifier.tryParse(blockId);
        if (id == null) {
            return false;
        }
        Block block = BuiltInRegistries.BLOCK.getOptional(id).orElse(null);
        if (block == null) {
            return false;
        }
        SeedOreDefinition definition = SeedOreRegistry.of(SeedDimensionProfile.OVERWORLD, TARGET_ORE);
        return definition != null && definition.matches(block.defaultBlockState());
    }

    private static String targetBlockId(AutoMinerModule module) {
        Block block = module.getTargetBlock();
        return block == null ? "（空）" : BuiltInRegistries.BLOCK.getKey(block).toString();
    }

    private static void noteLock(BlockPos pos) {
        if (pos == null) {
            return;
        }
        // 同一个坐标被连续观测到多次只算一次「锁定」：装置在多个阶段都会看一眼当前目标，
        // 那不是「又选了同一颗」。计数因此等于「这一格被重新选中的次数」，正是失败用例要判的东西。
        if (!LOCK_SEQUENCE.isEmpty() && LOCK_SEQUENCE.get(LOCK_SEQUENCE.size() - 1).equals(pos)) {
            return;
        }
        LOCK_SEQUENCE.add(pos);
        LOCK_COUNTS.merge(pos, 1, Integer::sum);
    }

    private static int countLock(BlockPos pos) {
        return pos == null ? 0 : LOCK_COUNTS.getOrDefault(pos, 0);
    }

    private static String describeLocks() {
        StringBuilder text = new StringBuilder();
        for (BlockPos pos : LOCK_SEQUENCE) {
            if (!text.isEmpty()) {
                text.append(" → ");
            }
            text.append(posText(pos));
        }
        return text.toString();
    }

    private static String posText(BlockPos pos) {
        return pos == null ? "（无）" : "(" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")";
    }

    private static String playerPosText(Minecraft client) {
        return client.player == null ? "（无玩家）" : posText(client.player.blockPosition());
    }

    // ── 服务端探针 ──────────────────────────────────────────────────────────

    private static void probeBlockState(Minecraft client, BlockPos pos) {
        blockProbeDone = false;
        IntegratedServer server = client.getSingleplayerServer();
        if (server == null) {
            blockProbeDone = true;
            blockProbeText = "（无集成服务端）";
            return;
        }
        server.execute(() -> {
            try {
                ServerLevel level = server.overworld();
                BlockState state = level.getBlockState(pos);
                blockProbeText = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
            } catch (Throwable error) {
                blockProbeText = "异常：" + error;
            } finally {
                blockProbeDone = true;
            }
        });
    }

    private static void probeInventory(Minecraft client) {
        invProbeDone = false;
        invProbeTargetCount = 0;
        IntegratedServer server = client.getSingleplayerServer();
        if (server == null) {
            invProbeDone = true;
            invProbeText = "（无集成服务端）";
            return;
        }
        // 计数的产物物品跟着本轮矿物走（时运 / 精准两种模式的产物 id 由正式层给出）
        String dropItemId = module().getTargetDropItemId();
        server.execute(() -> {
            try {
                int picks = 0;
                int swords = 0;
                int food = 0;
                int oreCount = 0;
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
                        ItemStack stack = player.getInventory().getItem(slot);
                        if (stack.isEmpty()) {
                            continue;
                        }
                        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
                        if (id.endsWith("_pickaxe")) {
                            picks += stack.getCount();
                        }
                        if (id.endsWith("_sword")) {
                            swords += stack.getCount();
                        }
                        if (id.equals("minecraft:cooked_beef")) {
                            food += stack.getCount();
                        }
                        if (id.equals(dropItemId)) {
                            oreCount += stack.getCount();
                        }
                    }
                }
                invProbeText = "镐=" + picks + ",剑=" + swords + ",食物=" + food
                        + "," + oreNameCn() + "=" + oreCount;
                invProbeTargetCount = oreCount;
            } catch (Throwable error) {
                invProbeText = "异常：" + error;
            } finally {
                invProbeDone = true;
            }
        });
    }

    // ── 报告 ────────────────────────────────────────────────────────────────

    private static String verdict(boolean ok) {
        return ok ? "（通过）" : "（**不通过**）";
    }

    private static void report(String line) {
        LOGGER.info("{}｜{}", TAG, line);
        REPORT.add(line);
    }

    /** 收尾：写报告并让装置退出。 */
    private static void finish() {
        if (stage == Stage.FINISHED) {
            return;
        }
        AutoMinerModule module = module();
        if (module != null && module.isEnabled()) {
            ModuleManager.setEnabled(MODULE_ID, false);
        }
        SERVICE.setEnabled(false);
        stage = Stage.FINISHED;

        List<String> lines = new ArrayList<>();
        lines.add("《238 · 种子目标（本轮：" + oreNameCn() + "矿）→ AutoMiner 正式接入 · 目标接入回归结果》");
        lines.add("装置：单人夹具世界（固定种子，与预测种子同一颗）+ 现有自动挖矿模块（未另造第二套）");
        lines.add("测试世界：" + SeedPocWorldFactory.LEVEL_ID + "；种子 " + SeedPocFlags.targetSeed()
                + "；覆盖半径 " + SeedPocFlags.targetRadius() + " 区块");
        lines.add("装置新鲜度：" + SeedPocWorldFactory.lastFreshNote());
        lines.add("指令通道：玩家发包入口（单人存档自带指令权限）—— give / spreadplayers / forceload / fill / setblock / tp");
        lines.add("真值来源：集成服务端 ServerLevel#getBlockState（不读客户端镜像）");
        lines.add("");
        lines.addAll(REPORT);
        lines.add("");
        lines.add("十三、判定汇总");
        lines.addAll(VERDICTS);
        boolean allPass = !VERDICTS.isEmpty() && VERDICTS.stream().noneMatch(line -> line.contains("不通过"));
        lines.add("全部判定：" + (allPass ? "通过" : "**存在不通过项，见上**"));
        // 238：报告文件名带上本轮矿物 —— 同一套装置要逐个矿物跑，文件名不带就会互相覆盖，
        // 钻石轮次沿用 235 的老文件名（证据索引里的历史路径不动）。
        SeedPocReport.output(lines, TARGET_ORE == OreType.DIAMOND
                ? REPORT_FILE
                : REPORT_FILE.replace(".txt", "-" + TARGET_ORE.name().toLowerCase(java.util.Locale.ROOT) + ".txt"));
        SeedPocEntry.onExperimentFinished();
    }
}
