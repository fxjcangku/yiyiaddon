package com.yiyiaddon.feature.mining.fsm;

import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.pathing.goals.GoalNear;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.fastbreak.MiningFastBreakController;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.service.MiningContainer;
import com.yiyiaddon.feature.mining.service.ServerCommandRunner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 挖矿状态机 - FSM 核心引擎（旧项目 {@code mining/fsm/MinerFSM.java}，1842 行，逐条移植）。
 *
 * <p>状态转换流程（旧项目类注释 {@code :34-42} 逐字，战斗拦截为本项目新增）：</p>
 * <pre>
 * IDLE → GO_WILD → MINING → [UNLOADING/SUPPLY/REPAIR] → GO_WILD → MINING ...
 *
 * 战斗拦截（仅在 GO_WILD / MINING 触发）：
 * GO_WILD/MINING → COMBAT → MINING
 *
 * 死亡事件拦截：
 * ANY_STATE → DEATH_HANDLING → RESPAWN_WAIT → GO_WILD
 * </pre>
 *
 * <p><b>对照口径：</b>本类每个方法上方的注释都写明对应的旧方法名与行号，便于逐字比对；
 * 全部判据、顺序、阈值、常量值、计时口径与旧项目一致（{@code stateTick} 在分派前自增，
 * 因此每个状态首帧 {@code stateTick == 1}；{@code transitionTo} 内把 {@code stateTick} 归零）。
 * 用户可见文案（含 {@code §} 色码、全角空格、{@code §8▸} 分隔符）逐字照抄旧项目。</p>
 *
 * <p><b>本轮留白（一行都没写，遇到旧源码对应分支整段跳过）：</b></p>
 * <ul>
 *   <li>种子模式全部：{@code tickSeedMining}（旧 {@code :614-671}）、{@code findNextSeedTarget}
 *       （{@code :674-693}）、{@code isTargetOreAt}（{@code :696-698}）、{@code breakSeedBlock}
 *       （{@code :769-805}）、{@code ensureBestToolForSeedTarget}（{@code :808-834}）、
 *       {@code ensurePickaxeInHand}（{@code :853-868}）、{@code seedVisited} / {@code seedTarget} /
 *       {@code seedBreakState} / {@code seedBreakTicks} / {@code seedBreakFace} / {@code seedPathRetries}
 *       六个字段，以及全部对 {@code OrePredictor} 的调用（{@code :480/622/625/645/657/679/683/801}）；
 *       旧源码里每一处 {@code module.isSeedMiningEnabled()} 分支都整段删除，只保留普通模式路径。</li>
 *   <li>假矿检测（{@code AutoMinerModule.checkFakeOres}）、秒破发包（{@code fastbreak} 两文件 + mixin）、
 *       {@code .wk} 指令树、配置页面：均属后续批次，本类不出现。</li>
 * </ul>
 *
 * <p>原「下界挖矿自动开岩浆透视」留白项已在 ESP 批次接上：进 {@code MINING} 且在下界时
 * {@code module.enableLavaEsp()} + 逐字播报，渲染见 {@code render/MiningPointRenderer}。</p>
 *
 * <p><b>与旧项目的差异（其余逐条一致）：</b></p>
 * <ol>
 *   <li><b>死亡自动重生的归属</b>（用户 2026-09-16 拍板）：旧项目 {@code tryMeteorAutoRespawn()}
 *       （{@code :1781-1793}，toggle 第三方框架的模块）整段删除。本项目有自研
 *       {@code feature/respawn/AutoRespawnModule}（默认开启）承担这件事，状态机不再切换任何模块，
 *       只保留 {@code mc.player.respawn()} 兜底（旧 {@code :1477-1479}）。随之两条死亡播报按最小改动
 *       换成本项目模块名（其余一字不改）：旧 {@code :1472} {@code "§c✗ 已调用流星自动重生模块"}
 *       → {@code "§c✗ 已调用自动重生模块"}；旧 {@code :1723}
 *       {@code "§c✗ 检测到死亡 §8▸ 已调用流星自动重生"} → {@code "§c✗ 检测到死亡 §8▸ 已调用自动重生"}。</li>
 *   <li><b>KillAura 修补联动</b>：旧 {@code startKillAura / stopKillAura}（{@code :1763-1779}）
 *       改为接口 seam {@link RepairCombatHook}（实现由批次 4 的 KillAura 模块注入），
 *       状态机侧调用点与 {@code killAuraWasOnBefore} 卫语句语义不变。</li>
 *   <li><b>外部依赖</b>：旧 {@code Meteor InvUtils} 换成 26.1.2 原语——选槽走
 *       {@code Inventory#setSelectedSlot} + {@code ServerboundSetCarriedItemPacket}，
 *       与副手互换走 {@code handleContainerInput(..., ContainerInput.SWAP, ...)}（按钮 40 = 副手），
 *       与本项目 {@code DefaultStardewAdapter} 同一做法；旧 {@code WKCommand.WKData} 三点位换成
 *       {@code pointStore().get(MiningPointType.*)}；{@code module.isActive() / toggle()} 换成
 *       {@code isEnabled() / ModuleManager.setEnabled(MODULE_ID, false)}。</li>
 *   <li>旧 {@code tickUnloading}（{@code :1080-1085}）与 {@code tickSupply}（{@code :1205-1210}）
 *       里两段「维度限制已临时关闭」的注释代码不再保留（被注释掉的旧逻辑，无行为）。</li>
 *   <li><b>起 mine 前等世界就绪</b>（用户 2026-09-17 实机裁定）：旧项目 {@code tickMining} 阶段 1
 *       在进入 MINING 那一瞬就执行 {@code mine}，而 MINING 总是紧跟 RTP 传送之后进入，落地当刻客户端
 *       区块还没到齐 → Baritone 的 {@code MineProcess.rescan} 在已加载区块里一个目标矿都扫不到、
 *       又因 {@code exploreForBlocks=false} 直接 cancel，播报「无法找到前往…的路径，已取消挖掘」
 *       （实测每次第一次传送必现）。现改为：预热 {@code MINE_START_WARMUP_TICKS} 刻且所在区块与四邻
 *       区块均已加载后才起 mine（最长等 {@code MINE_START_MAX_WAIT_TICKS} 刻），mine 进程退出后的
 *       重启同样重新等待。除起动时机外，本状态其余判据、顺序、文案一字未改。</li>
 *   <li><b>背包读数瞬时空窗防护</b>（用户 2026-09-17 实机裁定）：旧项目单刻读不到镐子即停机，
 *       而服务器「重生式传送」后客户端重建世界与玩家、背包内容由服务端重发，中间若干刻
 *       {@code getInventory()} 读出来是空的，于是出现「自检通过却一启动就报缺少镐子」。现改为
 *       世界实例变化后 {@code INVENTORY_TRUST_TICKS} 刻内不按背包读数下结论，且需连续
 *       {@code PICKAXE_MISSING_TICKS_TO_STOP} 刻读不到才停机（文案未改）。</li>
 *   <li><b>掉落物捡取节奏修正</b>（用户 2026-09-17 实机：「挖矿老是莫名暂停 + 刷屏」）：见
 *       {@link #tryPickupNearbyOre()} 注释 —— 只捡存在满 {@code PICKUP_MIN_AGE_TICKS} 刻的漏捡物，
 *       每次捡取后冷却 {@code PICKUP_COOLDOWN_TICKS} 刻，够不到时 1 秒内判不可达并进黑名单。</li>
 *   <li><b>水中卡死判据重做</b>（用户 2026-09-17：「卡在水里一直上下弹跳、动来动去，就是不自动脱困」）：
 *       旧实现把水中位移判据挂在前面的原地抖动采样上，用的是含 Y 轴的 {@code distSqr}——水里上下弹跳
 *       本身就制造 Y 位移，位移判据永远命中不了，只剩「速度满 30 秒」这条，于是表现为长时间不脱困。
 *       现独立计米 {@link #updateWaterStuckMeter}：<b>只看 X/Z 的水平净位移</b>，每 1 秒采样一次、
 *       1 秒内水平移动不足 1 格就累计，满 {@code WATER_STUCK_TICKS} 即破坏方块 / 寻路到最近陆地脱困
 *       （用户 2026-09-18 要求「缩短时间」后该阈值由 10 秒收到 <b>3 秒</b>）；同一轮挖矿里反复触发
 *       {@code MAX_WATER_ESCAPES} 次仍过不去，直接判定「这片水过不去」并 RTP 换区。</li>
 *   <li><b>工具自动回快捷栏</b>（用户 2026-09-17：「镐子不在快捷栏时根本不会从背包里换过来」）：
 *       反编译本版 Baritone 的 {@code ToolSet#getBestSlot} 循环上界为 9，自动选工具只扫快捷栏，
 *       背包里的工具它看不到；现由 {@code MiningContainer#ensureToolsInHotbar()} 在起 mine 前与挖矿期间
 *       定期把缺失的工具类型（镐/铲/斧/锄/剑）搬进快捷栏。腾位置策略同进食：空槽 → 垃圾格 →
 *       数量最少的一组目标矿 → 最后才动垫脚方块（快捷栏被几组矿塞满时也能把铲子换进来）。</li>
 *   <li><b>背包点击的容器守卫</b>（用户 2026-09-17 日志：刷 {@code Ignoring click in mismatching container}）：
 *       模块自己的箱子会话被死亡/岩浆/卡死分支打断时会残留，残留期间客户端会丢弃所有 containerId=0 的点击，
 *       换食物、丢垃圾全部静默失效。现于离开 {@code UNLOADING/SUPPLY} 时强制收尾容器，
 *       且所有玩家背包点击前先判 {@code isPlayerInventoryClickBlocked()}。</li>
 *   <li><b>进食换槽与播报修正</b>（用户 2026-09-17：「金苹果不在快捷栏就换不回来 / 进食中 ▸ 下界合金镐」）：
 *       旧实现没空槽时固定顶掉快捷栏 8 号位且不归还，进食进度又播报主手物品名。现改为优先空槽、
 *       其次只顶「最不重要」的一格并记录，退出进食时换回（{@code restoreEatDisplacedItem()}），
 *       播报改用白名单食物名（{@code describeEatingFood()}）。</li>
 *   <li><b>搭路方块只留一组</b>（用户 2026-09-17 复核：「我的默认是一组」，截图里留下 3 组原石）：
 *       回到旧项目的「搭路方块只留一组（≤64）」，但丢弃时更保守——只丢数量较少的那些多余组，
 *       手持那一格永不动，同数量时保留靠前的（避免两组 64 互相判定为多余而全丢光）。</li>
 *   <li><b>战斗拦截</b>（用户 2026-09-17：「老是被怪物打死」）：新增 {@link MinerState#COMBAT}
 *       （2026-09-18 用户裁定「不躲了，苦力怕也直接打，控制好距离」后，原来的 {@code EVADE} 撤离态
 *       已删除，苦力怕改在战斗态里用「打了就退」走位处理），只在 MINING / GO_WILD 触发
 *       （物流、进食、修补、死亡流程不打断），行为全在 {@link MiningCombat}，默认开启且不占任何设置项。
 *       <b>主动出击、不等挨打</b>：怪物一进 6 格就寻路过去击杀（被杀过则扫描半径放宽到 16 格）；
 *       <b>进战斗时临时关掉 Baritone 的怪物规避（{@code avoidance}）</b>——开着它 Baritone 会因
 *       「怪物附近代价更高」不肯靠近，表现成「要杀它却在原地绕圈」；目标跑出 6 格后最多再追 10 秒，
 *       追不上就回挖矿；攻击不做视线检查（用户裁定：可以穿墙攻击）。</li>
 *   <li><b>岩浆避险加强</b>（用户 2026-09-17：「透视岩浆没生效 / 老是擦着岩浆边走被烧」）：
 *       避险半径改用新增设置「岩浆安全距离」{@code lavaAvoidRadius}（默认 2 格，旧实现只看相邻 1 格）；
 *       玩家已在岩浆里或身上着火时立即触发（不等 10 刻采样）；挑安全点时要求该点距岩浆也 ≥ 避险半径
 *       （否则脱困一结束立刻又触发）；脱困成功后给 {@code LAVA_ESCAPE_COOLDOWN_TICKS} 刻冷却，
 *       避免在岩浆矿区反复「触发→脱困→再触发」把挖矿打断成碎片。岩浆透视渲染自身「一次都不画」的
 *       根因（{@code lastLavaScanTick = Integer.MIN_VALUE} 参与减法溢出）已在
 *       {@code MiningPointRenderer} 修掉。</li>
 *   <li><b>离开挖矿态时收掉秒破会话</b>：{@code onStateExit(MINING)} 里先
 *       {@code MiningFastBreakController#release}（发 ABORT 清服务端槽位 + 清裂纹）再停 Baritone，
 *       否则残留的 START 槽位会顶掉下一个方块的破坏进度（表现为「下一块怎么挖都不烂」）。</li>
 *   <li><b>手动传送检测</b>（用户 2026-09-17：「挖矿中敲 /home 回家，模块还在继续挖」）：见
 *       {@link #manualTeleportDetected()} —— MINING 态下位置单刻跳变 &gt;32 格即判定服务器传送类指令，
 *       立刻停机播报（用户裁定：停机最安全，绝不在家里/别人基地继续挖）。</li>
 *   <li><b>开箱距离按原版交互距离判</b>（用户 2026-09-17：「打包机比玩家高 2 格，寻路会自动垫方块上去」）：
 *       {@link #isAdjacentTo} 由「切比雪夫 ≤1」放宽到「水平 ≤2、垂直 -2~+2、眼到方块中心 ≤4 格」，
 *       与 {@code MiningContainer#withinOpenRange} 同一口径；容器高两层时站在下面直接开箱，不再垫方块。</li>
 *   <li><b>补给取食物重做</b>（用户 2026-09-17：「有时候根本没拿到就回状态机了」）：旧实现里
 *       {@code withdrawFood()} 在「Shift 点击刚发出、物品还在服务器飞行」时也会返回 true，
 *       状态机当刻读到的还是旧数量 → 被判「补给箱无白名单食物」直接回矿区。
 *       现改为三态结果（{@link MiningContainer.FoodWithdrawResult}）：每格点击都要等到账才发下一格、
 *       取完再等 {@code SUPPLY_SETTLE_TICKS} 刻结算帧、成功判据改成<b>本次增量 &gt; 0</b>；
 *       取食目标也由「凑够食物阈值」改为「每个白名单食物补到自己的一组」
 *       （上限自动取该物品的最大堆叠数：普通食物 64、蜂蜜瓶 16、汤类/蛋糕 1）；
 *       背包满导致「点了拿不进来」会单独报出来，不再混成「箱内无食物」。</li>
 * </ol>
 */
public final class MiningStateMachine {

    private final AutoMinerModule module;
    private final Minecraft mc;

    /**
     * 副手在 {@code Inventory} 里的索引（0~8 快捷栏 / 9~35 背包 / 36~39 护甲 / 40 副手）。
     *
     * <p>供 {@code ContainerInput.SWAP} 的 button 参数使用：button=40 表示与被点击的槽位交换副手物品，
     * 与本项目 {@code DefaultStardewAdapter.swapOffhandWith} 同一口径。</p>
     */
    private static final int OFFHAND_INV_INDEX = 40;

    // ── 状态与传送监测（旧 :48-56） ──
    private MinerState state = MinerState.IDLE;
    /** 上一个状态（进入副作用要用它区分「从哪来」：例如刚落地进挖矿要补传送宽限窗） */
    private MinerState previousState = MinerState.IDLE;
    private int stateTick = 0;
    private BlockPos teleportStartPos = BlockPos.ZERO;
    private int teleportTimeout = 0;
    private int teleportRetries = 0;
    private int teleportCooldownTicks = 0;   // RTP 冷却剩余 tick（服务器 RTP 有冷却，失败后等冷却再重试）
    private boolean teleportRetryNoticeShown = false; // 「自动重试已关闭」提示只播一次（关掉重试后每 tick 都在超时）
    private static final int MAX_TELEPORT_RETRIES = 3; // 传送失败最多自动重试 3 次

    /** 当前状态（只读，供控制台概览页显示）；状态机内部行为不变 */
    public MinerState state() {
        return state;
    }

    /**
     * 是否正在「水中脱困破坏」。
     *
     * <p>连锁挖矿要用它避让：两者都占同一个服务端破坏槽位（连锁靠秒破的单槽状态机、
     * 脱困破坏直接走原版 {@code startDestroyBlock} 再被秒破 Mixin 接管），同时发包会互相顶槽，
     * 结果两边都挖不烂。</p>
     */
    public boolean isWaterBreaking() {
        return waterBreaker.isActive();
    }

    /**
     * 是否处于战斗态。
     *
     * <p>用户 2026-09-18：「打怪的途中打死第一个捡到了掉落物，自动丢弃会先丢东西再打怪」——
     * 自动丢弃挂在模块的每刻推进里，与状态无关，于是战斗中每捡到一件垃圾都会先丢一次再挥刀。
     * 现在由调用方用本方法给丢弃逻辑上闸：COMBAT 态不丢，等回到挖矿再清背包。</p>
     */
    public boolean isInCombat() {
        return state == MinerState.COMBAT;
    }

    // 普通模式 mine 进程退出自愈：连续重启失败判定附近无矿，RTP 换区（旧 :66-68）
    private int mineRestartCount = 0;    // mine 退出重启计数
    private int mineRestartCooldown = 0; // 重启后冷却 tick（等 mine 启动，避免误判又退出）

    // ── 战斗拦截（用户 2026-09-17 需求：怪物自动战斗，默认开启、无设置项；苦力怕走位见 MiningCombat） ──
    private final MiningCombat combat;

    /**
     * 战斗结束后回到哪个主动态。
     *
     * <p>被战斗打断时可能是「采掘中」，也可能是「前往野外」（正在等服务器传送，例如 /wild 冷却中）。
     * 后者必须回到 GO_WILD 重新走传送流程，否则打完怪会就地开始挖矿——人其实还在上一片区域。</p>
     */
    private MinerState combatReturnState = MinerState.MINING;

    // 修补模式数据（旧 :70-82）
    private ItemStack savedTool = ItemStack.EMPTY;
    private ItemStack savedWeapon = ItemStack.EMPTY;
    private int savedToolSlot = -1;
    private int savedWeaponSlot = -1;
    private boolean repairMode = false;
    private boolean repairPathIssued = false;
    private int repairSwapAttempts = 0;
    private int repairSwapRequestedTick = -1;
    private boolean unloadingPathIssued = false;
    private boolean supplyPathIssued = false;

    // ── 物流寻路停滞监测（卸货/补给共用，用户 2026-09-18） ──────────────────────
    // 「卸货阈值到了却没走到容器旁，绿框一直闪却到达不了」：Baritone 卡住时
    // isCustomGoalActive 仍为 true（绿框还在闪），只看它活跃与否的重发分支永远走不到，
    // 旧实现会干等到 PATH_TIMEOUT 静默停机。改为按「与容器的实际距离」判定有没有进展：
    // 连续多轮无接近就逐级脱困——先重发目标 → 再临时允许破坏挡路方块 → 仍无进展才停机播报。
    /** 上次采样时与目标容器的距离平方（Double.MAX_VALUE 表示本态尚未采样） */
    private double logisticsLastDist = Double.MAX_VALUE;
    /** 连续无进展的采样轮数 */
    private int logisticsStallCount;

    /** 停滞采样间隔（刻）：3 秒一轮 */
    private static final int LOGISTICS_SAMPLE_TICKS = 60;
    /** 单轮内必须接近的量（格平方）：3 秒走不到 0.7 格就算无进展 */
    private static final double LOGISTICS_MIN_APPROACH = 0.5;
    /** 进入补给态时的白名单食物数量：回来时用它判断这次到底有没有拿到东西（用户 2026-09-17） */
    private int supplyFoodBefore = 0;
    /** 补给取完后的结算帧计数：连续 DONE 够 SUPPLY_SETTLE_TICKS 刻才收摊（等最后一格点击到账） */
    private int supplySettleTicks = 0;
    private static final int SUPPLY_SETTLE_TICKS = 4;

    /**
     * 我方传送的「等待落地」倒计时（刻）：指令发出后置位，位置真的跳变一次即消费掉。
     *
     * <p>用户 2026-09-18 实机：「我第一次启动传送 给我停机了」（本机制要解决的）与
     * 「挖矿途中手动 /home 还是会在家里挖」（本机制要避免的漏检）：</p>
     * <ul>
     *   <li>旧版用「发指令后固定 20 秒不判」的宽限窗，结果把玩家自己的 /home 一起挡掉了；</li>
     *   <li>现在改成<b>按事件消费</b>：我方指令发出后只等<b>一次</b>位置跳变（那才是我们自己的传送落地），
     *       消费掉后再给 {@link #OWN_TELEPORT_SETTLE_TICKS} 刻的尾巴（落地位置修正、客户端世界重建），
     *       窗口过后立刻恢复正常判定——挖矿几秒后手敲 /home 照样能抓到。</li>
     * </ul>
     */
    private int ownTeleportPendingTicks = 0;
    /** 我方传送指令发出后等待落地跳变的最长时间（刻）：30 秒，覆盖慢速 RTP 排队 */
    private static final int OWN_TELEPORT_PENDING_TICKS = 600;
    /** 落地跳变被消费后，再容忍位置修正的时长（刻）：5 秒 */
    private static final int OWN_TELEPORT_SETTLE_TICKS = 100;
    /** 落地后的位置修正窗口（刻）：跳变被消费后置位，期间的所有跳变都算我方落地修正 */
    private int ownTeleportSettleTicks = 0;
    /** 补给硬超时（刻）：箱子交互异常时不至于永远开着箱子（旧实现是 400，这里放宽到 30 秒） */
    private static final int SUPPLY_TIMEOUT_TICKS = 600;
    private boolean killAuraWasOnBefore = false; // 进入修补前 KillAura 是否本来就开着（避免误关用户自己的 KA）
    private int supplyFailCount = 0;             // 补给空手连续计数（2 次箱空直接停机，防 SUPPLY↔MINING 死循环）

    // 潜影盒打包机换盒等待（旧 :84-91）
    private boolean boxSwapWaiting = false;      // 关箱后等待打包机推盒+放新盒
    private int boxSwapTicks = 0;                // 换盒等待计时
    private int boxSwapCount = 0;                // 本轮卸货累计换盒次数
    private static final int BOX_SWAP_WAIT_TICKS = 40; // 换盒等待 2 秒（40 tick）
    private static final int MAX_BOX_SWAPS = 10;       // 换盒次数上限（防打包机坏了死循环）
    private int noContainerTicks = 0;                  // 标点位置无容器持续 tick
    private static final int NO_CONTAINER_TIMEOUT = 200; // 无容器 10 秒（200 tick）后停机

    // ── 背包读数可信窗口（防止把「客户端背包还没同步完」误判成「真的没有镐子」） ──
    // 用户 2026-09-17 实机证据（chatlog + latest.log）：自检通过（同一背包里镐子、食物都在）→
    // /wild 传送 → 客户端背包被服务端重发（服务器「重生式传送」会重建世界与玩家）→ 进入 MINING 的
    // 第 2 刻读到空背包，直接播报「缺少镐子」停机；同一会话稍后同一背包又能读到镐子与 64 个食物，
    // 且每次重启模块自检都还能通过 —— 说明镐子一直在背包里，只是那一刻客户端读出来是空的。
    // 因此：世界实例变化后给一段信任窗口，且停机判据改为「连续多刻读不到」。
    private Level lastSeenLevel;                        // 上一次看到的世界实例，变了即视为世界重载
    private int inventoryTrustTicks = 0;                // 世界（重）载入后的背包不信任窗口剩余 tick
    private int pickaxeMissingTicks = 0;                // 连续读不到镐子的 tick 数
    private static final int INVENTORY_TRUST_TICKS = 200;         // 重载后 10 秒内不按背包读数下停机结论
    private static final int PICKAXE_MISSING_TICKS_TO_STOP = 100; // 连续 5 秒读不到才算真的没有

    // ── 起 mine 前的「世界就绪」等待（防止落地瞬间起 mine 被 Baritone 立刻取消） ──
    // Baritone 的 mine 只扫描「已加载区块」找目标矿（矿石不进 Baritone 方块缓存），一个都扫不到
    // 且 exploreForBlocks=false 时，rescan 直接 cancel 整条 mine 并播报
    // 「无法找到前往…的路径，已取消挖掘」（用户 2026-09-17 实机：每次第一次传送必现）。
    // 而 MINING 总是紧跟 RTP 传送之后进入，落地瞬间区块还没到齐 → 改为等区块就绪再起 mine。
    private boolean mineStartIssued = false; // 本轮 MINING 是否已真正起过 mine
    private int mineStartWait = 0;           // 起 mine 前的剩余等待刻
    private int mineStartWaited = 0;         // 已等待总刻数（兜底上限用）
    private static final int MINE_START_WARMUP_TICKS = 40;    // 基础预热：2 秒
    private static final int MINE_START_RECHECK_TICKS = 10;   // 邻区块未就绪时的复查步长
    private static final int MINE_START_MAX_WAIT_TICKS = 200; // 兜底：最多等 10 秒，不阻塞流程

    // 死亡标志（旧 :93-94）
    private boolean playerWasDead = false;

    // 卡死监测：改用速度监测而非位置监测（旧 :96-107）
    private int lowSpeedTicks = 0;
    /**
     * 「用户开着界面」的持续刻数（用户 2026-09-18：「我在配置页面 调配置 模块在开启的情况下 好像秒破跟连锁会失效」）。
     *
     * <p>原版 {@code Minecraft#handleKeybinds} 在 {@code screen != null} 时整段跳过 —— 客户端不处理挖掘按键，
     * Baritone 的按键注入同样失效，所以界面开着时挖矿必然停摆。这是原版语义，<b>不是卡死</b>：
     * 下面所有「没进展」判据（低速 / 挖不动 / 原地抖动 / 水中停滞）都必须忽略这段时间，
     * 否则调个配置 15 秒就被判「挖不动」重下发、30 秒直接 RTP 换区。</p>
     *
     * <p>关闭界面后再走一次正常的 mine 启动流程：Baritone 的 mine 进程可能已被它自己的卡死检测取消。</p>
     */
    private int uiOpenTicks = 0;
    private static final int UI_RELAX_TICKS = 20; // 界面开着超过 1 秒才算「真在调配置」，关闭后才需要重下发 mine
    private int waterStuckTicks = 0;
    // 水中「按水平净位移」卡死计时（本轮加强，见 updateWaterStuckMeter）：只统计 X/Z 的净位移。
    // 旧实现把它挂在原地抖动采样的 distSqr 上，而 distSqr 含 Y 轴 —— 水里上下弹跳时 Y 一直在变，
    // 判据永远命中不了（用户 2026-09-17：「卡在水里一直上下弹跳，就是不自动脱困」）
    private int waterNoMoveTicks = 0;
    private boolean waterEscapeActive = false; // 水中脱困寻路是否进行中
    private int waterEscapeTicks = 0;          // 水中脱困寻路已持续时间
    private int waterEscapeCount = 0;          // 本轮挖矿中已触发过几次水中脱困（反复触发说明这片水过不去）
    private BlockPos waterEscapeTarget = null; // 本次水中脱困的目的地（到达即可判定脱困，见 tickMining 的收尾分支）
    // 水中脱困第一级：破坏卡住玩家的方块（用户 2026-09-18：「检测到被水卡住上下跳动 自动破坏周围的方块逃离」）。
    // 先挖开头顶/四周再寻路，因为「被封在水里」时那个「最近陆地」Baritone 根本走不到（见 WaterEscapeBreaker 类注释）
    private final WaterEscapeBreaker waterBreaker; // 构造器里初始化（它要拿 module，字段初始化顺序早于构造器赋值）
    private boolean waterBreakTried = false;   // 本轮水中卡死是否已经试过「破坏脱困」（试过就不再重复，直接升级到寻路脱困）
    private BlockPos lastWaterSample = null;   // 水中水平位移的采样点
    private static final int WATER_SAMPLE_TICKS = 20;    // 水中位移采样步长（1 秒）
    /**
     * 水中水平停滞判定阈值（刻）：连续这么多个采样点水平位移都不到 1 格，就判定卡死并开始脱困。
     *
     * <p>旧实现 600 刻（30 秒）、本项目第一版 200 刻（10 秒），用户 2026-09-18 实机反馈
     * 「反应太慢」——脱困本身有效（沉底会自动浮上来），只是等得太久。现取 3 秒：
     * 判据要求连续 3 次采样水平位移都 &lt; 1 格，正常游泳每秒水平位移远超 1 格，不会误判。</p>
     */
    private static final int WATER_STUCK_TICKS = 60;
    /** 瞬时速度兜底阈值（刻）：水里连续 10 秒速度贴零（被水流推着原地打转/完全静止）也判卡死 */
    private static final int WATER_SPEED_STUCK_TICKS = 200;
    private static final int MAX_WATER_ESCAPES = 3;      // 连续脱困 3 次仍过不去 → 直接换区
    private BlockPos lastPosSample = BlockPos.ZERO; // 原地抖动卡死的位置采样点
    private int noMoveTicks = 0;                    // 位移长时间不变的累计 tick
    private int stuckResetCount = 0;                // 连续原地抖动卡死次数（超过阈值才传送去野外）

    // ── 「挖不动」快速自愈（用户 2026-09-18） ────────────────────────────────
    // 「卸完货 RTP 切状态后莫名其妙卡住，拿着镐子不挖东西，绿框一直闪却过不去，重启模块才恢复——
    //  寻路没问题，就是挖不掉东西导致它寻不了路」。旧实现只有 3 分钟一档的位移判据（NO_MOVE_THRESHOLD），
    // 玩家要干等 3 分钟才等到自愈。这里用更精确的判据把响应压到 15 秒：Baritone 在寻路（绿框在闪）
    // 却原地不动，同时秒破没有任何活跃目标——说明不是「正在挖、只是在等方块破」，而是根本挖不动。
    private int mineNoProgressTicks = 0;            // 「在寻路却毫无进展」的连续 tick
    private int mineNoProgressResets = 0;           // 已重下发 mine 脱困的次数（连续两次才升级为换区）
    private static final int MINE_NO_PROGRESS_LIMIT = 300; // 15 秒毫无进展即判定挖不动
    /**
     * mine 启动宽限（用户 2026-09-18：「⚠ 挖不动面前的方块…我怀疑是这个弄的 老是失效 秒破跟连锁」）。
     *
     * <p>实机 chatlog：`21:22:15 开始挖矿` → `21:22:18 Baritone 已启动挖掘` → `21:22:38 挖不动面前的方块`，
     * 刚好 20 秒；而 10 秒后的 `21:22:48` 就出了连锁。原因是落地后 Baritone 要扫矿 + 算路径 + 等区块加载，
     * <b>头 15~20 秒秒破本来就不可能 active</b>，而自愈计时器从进 {@code MINING} 那一刻就开始数，
     * 于是每次 RTP 落地后必然误触发一次；触发动作是 {@code baritone.stop()} + 重下发 mine，
     * 正好把连锁的接管状态掀翻（表现为「秒破跟连锁失效」）。</p>
     */
    private static final int MINE_START_GRACE_TICKS = 600; // mine 启动后 30 秒内不判「挖不动」
    /** 最近 5 秒内实际破坏过方块就不算「毫无进展」：块与块之间的间隙不该被计成卡死 */
    private static final int MINE_PROGRESS_GRACE_TICKS = 100;
    /** mine 最近一次启动的 tickCount（进入 MINING 与真正下发 mine 两处刷新） */
    private int mineStartTick = 0;
    private static final double MIN_SPEED_THRESHOLD = 0.05; // 速度低于0.05判定为卡住
    private static final int STUCK_TIME_THRESHOLD = 3600;
    private static final int NO_MOVE_THRESHOLD = 3600; // 3分钟位移<2格判定原地抖动
    private static final int PATH_TIMEOUT_TICKS = 2400;

    // 手动传送监测（用户 2026-09-17：挖矿中敲 /home 回家后还在继续挖）：位置单刻跳变超过该值即判定
    private BlockPos lastWatchPos = null;
    private static final double MANUAL_TELEPORT_DISTANCE_SQR = 32.0 * 32.0;

    /** 开箱允许的最大「眼到方块中心」距离的平方（4.0 格，与容器层同一口径） */
    private static final double CONTAINER_OPEN_DISTANCE_SQR = 16.0;

    // 自动捡取掉落物（旧 :109-114）
    private ItemEntity pickupTarget = null;
    private int pickupTimeout = 0;
    // 捡取节奏控制（本项目修正，见 tryPickupNearbyOre 注释）：只捡躺够久的漏捡物 + 每次捡取后冷却，
    // 避免「刚挖出来的掉落物 → 停下 mine 去捡 → 捡完重启 mine」的 1~2 秒刷屏循环
    private int pickupCooldownTicks = 0;
    private static final int PICKUP_MIN_AGE_TICKS = 100;   // 掉落物至少存在 5 秒才算漏捡，新掉落交给挖掘流程自然拾取
    private static final int PICKUP_PATH_FAIL_TICKS = 20;  // 开始捡取 1 秒后仍无寻路进程＝够不到，直接判不可达
    private static final int PICKUP_COOLDOWN_TICKS = 200;  // 一次捡取结束后 10 秒内不再开捡
    // 捡取失败黑名单：掉落物卡角落捡不起来时记录位置，避免反复寻路捡同一个
    private final Set<BlockPos> pickupBlacklist = new HashSet<>();
    private static final int MAX_PICKUP_BLACKLIST = 64; // 黑名单上限，防止无限增长

    // 岩浆避险：附近有岩浆时停止挖矿并寻路到安全位置（旧 :116-119）
    private boolean lavaEscapeActive = false;   // 岩浆脱困寻路进行中
    private int lavaEscapeTicks = 0;            // 岩浆脱困已持续 tick
    private int lavaEscapeCooldown = 0;         // 脱困成功后的避险冷却（避免在岩浆矿区反复触发）
    private static final int LAVA_ESCAPE_COOLDOWN_TICKS = 200;
    private boolean diedInLava = false;         // 死亡时是否在岩浆里（复活后跳过 back 用）

    /**
     * 修补联动战斗钩子（旧 {@code :1763-1779} 的 KillAura 联动）；
     * 实现由批次 4 的 KillAura 模块通过 {@link #setRepairCombat(RepairCombatHook)} 注入。
     */
    private volatile RepairCombatHook repairCombat = RepairCombatHook.NONE;

    public MiningStateMachine(AutoMinerModule module) {
        this.module = module;
        this.mc = Minecraft.getInstance();
        this.combat = new MiningCombat(module);
        this.waterBreaker = new WaterEscapeBreaker(module);
    }

    /**
     * 注入修补联动战斗实现（批次 4 由 {@code AutoMinerModule} 调用）。
     *
     * <p>传 {@code null} 等价于 {@link RepairCombatHook#NONE}。</p>
     */
    public void setRepairCombat(RepairCombatHook hook) {
        this.repairCombat = hook == null ? RepairCombatHook.NONE : hook;
    }

    /** 清零全部运行期状态字段（旧 {@code reset}，{@code :126-170}；种子模式六个字段随留白去掉） */
    public void reset() {
        state = MinerState.IDLE;
        stateTick = 0;
        teleportStartPos = BlockPos.ZERO;
        teleportTimeout = 0;
        teleportRetries = 0;
        teleportCooldownTicks = 0;
        mineRestartCount = 0;
        mineRestartCooldown = 0;
        savedTool = ItemStack.EMPTY;
        savedWeapon = ItemStack.EMPTY;
        savedToolSlot = -1;
        savedWeaponSlot = -1;
        repairMode = false;
        repairPathIssued = false;
        repairSwapAttempts = 0;
        repairSwapRequestedTick = -1;
        unloadingPathIssued = false;
        supplyPathIssued = false;
        supplyFoodBefore = 0;
        supplySettleTicks = 0;
        ownTeleportPendingTicks = 0;
        ownTeleportSettleTicks = 0;
        previousState = MinerState.IDLE;
        killAuraWasOnBefore = false;
        supplyFailCount = 0;
        boxSwapWaiting = false;
        boxSwapTicks = 0;
        boxSwapCount = 0;
        noContainerTicks = 0;
        inventoryTrustTicks = 0;
        pickaxeMissingTicks = 0;
        mineStartIssued = false;
        mineStartWait = MINE_START_WARMUP_TICKS;
        mineStartWaited = 0;
        playerWasDead = false;
        lowSpeedTicks = 0;
        waterStuckTicks = 0;
        waterEscapeActive = false;
        waterEscapeTicks = 0;
        lastPosSample = BlockPos.ZERO;
        noMoveTicks = 0;
        stuckResetCount = 0;
        pickupTarget = null;
        pickupTimeout = 0;
        pickupCooldownTicks = 0;
        pickupBlacklist.clear();
        lavaEscapeActive = false;
        lavaEscapeTicks = 0;
        waterNoMoveTicks = 0;
        waterEscapeCount = 0;
        waterEscapeTarget = null;
        lastWaterSample = null;
        waterBreakTried = false;
        waterBreaker.reset();
        lastWatchPos = null;
        lavaEscapeCooldown = 0;
        diedInLava = false;
        combat.reset();
        combatReturnState = MinerState.MINING;
    }

    /**
     * 关模块 / 强制停机时的退场清理，替代 {@link #reset()}。
     *
     * <p>{@link #reset()} 直接把状态置回 IDLE，<b>不经过 {@code onStateExit}</b>（旧项目 {@code reset}
     * 亦如此，属旧实现遗留）。后果是三处副作用被跳过，玩家关掉模块后仍留在世界里：</p>
     * <ul>
     *   <li>在 <b>REPAIR</b> 态：{@code stopRepairCombat()} 不执行 → 我们开的杀戮光环一直开着；
     *       {@code restoreHotbar()} 不执行 → 镐子／武器留在副手（{@code savedToolSlot} 被清成 -1，已无法回放）。</li>
     *   <li>在 <b>EATING</b> 态：自动进食按下的右键不释放（释放点全在 {@code tickEating} 内部）。</li>
     *   <li>在 <b>物流</b>态（卸货 / 补给 / 修补）：Baritone 全局 {@code allowBreak} 停在物流值上。</li>
     * </ul>
     *
     * <p>顺序：先补做退出副作用（此时 {@code killAuraWasOnBefore} 等字段还在，能正确判断该不该还原），
     * 再 {@link #reset()}。</p>
     */
    public void shutdown() {
        onStateExit(state);
        mc.options.keyUse.setDown(false);
        if (state == MinerState.UNLOADING || state == MinerState.SUPPLY || state == MinerState.REPAIR) {
            module.getBaritone().updateSetting("allowBreak", module.getAllowBreak());
            // 物流态被关模块时同样要把放置/跑酷还回用户设置，否则它们会一直停在 false
            module.getBaritone().updateSetting("allowPlace", module.settings().allowPlace);
            module.getBaritone().updateSetting("allowParkour", module.settings().allowParkour);
            module.getBaritone().updateSetting("allowParkourPlace", module.settings().allowParkourPlace);
        }
        reset();
    }

    /**
     * 传送指令「生效判定」的等待窗口（tick）。
     *
     * <p>统一取设置里的「传送等待时长」（{@code GET_WILD} 与 {@code ServerCommandRunner} 的
     * {@code maxWaitTicks} 都是这个口径），下限 40 tick 与旧实现写死的 2 秒一致。
     * 旧实现三处写死 40 tick，服务器排队传送（RTP 冷却、多人在线）时指令还没落地就被判「指令无效」
     * 并直接停机，玩家看到的是模块莫名关闭。</p>
     */
    private int teleportEffectiveTicks() {
        return Math.max(40, module.getTeleportDelay() * 20);
    }

    /**
     * 世界（重）载入后的背包读数信任窗口维护。
     *
     * <p>世界实例变化 = 换维度 / 服务器「重生式传送」/死亡重生：客户端会重建世界与玩家对象，
     * 背包内容由服务端重新下发，中间若干刻 {@code mc.player.getInventory()} 读出来是空的。
     * 此窗口内不按背包读数下停机结论，窗口时长 {@link #INVENTORY_TRUST_TICKS}。</p>
     */
    private void refreshInventoryTrustWindow() {
        if (mc.level != lastSeenLevel) {
            lastSeenLevel = mc.level;
            inventoryTrustTicks = INVENTORY_TRUST_TICKS;
            pickaxeMissingTicks = 0; // 新的世界实例，重新累计
        } else if (inventoryTrustTicks > 0) {
            inventoryTrustTicks--;
        }
    }

    /**
     * 玩家所在区块及四邻区块是否都已加载。
     *
     * <p>Baritone 的 mine 只扫「已加载区块」找目标矿，邻区块没到齐就可能一个都扫不到、直接取消整条
     * mine。因此起 mine 前用它与预热刻数一起判断世界是否就绪（判据用 {@code Level#isLoaded}，
     * 与本项目星露谷侧同一套）。</p>
     */
    private boolean isMiningAreaLoaded() {
        if (mc.player == null || mc.level == null) return false;
        BlockPos p = mc.player.blockPosition();
        return mc.level.isLoaded(p)
            && mc.level.isLoaded(p.offset(16, 0, 0)) && mc.level.isLoaded(p.offset(-16, 0, 0))
            && mc.level.isLoaded(p.offset(0, 0, 16)) && mc.level.isLoaded(p.offset(0, 0, -16));
    }

    /** 每刻主干（旧 {@code tick}，{@code :172-201} 逐字） */
    public void tick() {
        if (mc.player == null || mc.level == null) return;

        refreshInventoryTrustWindow();

        // 手动传送检测（用户 2026-09-17：挖矿途中敲 /home、/spawn 之类回家，模块还在原地继续挖）
        if (manualTeleportDetected()) return;

        // 死亡事件拦截（最高优先级）
        if (mc.player.isDeadOrDying() && !playerWasDead) {
            playerWasDead = true;
            transitionTo(MinerState.DEATH_HANDLING);
            return;
        }

        // 复活检测
        if (playerWasDead && !mc.player.isDeadOrDying()) {
            playerWasDead = false;
            transitionTo(MinerState.RESPAWN_WAIT);
        }

        // 战斗拦截（用户 2026-09-17 需求，最高优先级之一，仅次于死亡）：
        // 只在「采掘中 / 前往野外」两个主动态触发——卸货、补给、修补、进食跑到一半去打怪会丢东西 / 打断物流
        if (state == MinerState.MINING || state == MinerState.GO_WILD) {
            // 「刷怪笼优先」期间不主动出击（用户 2026-09-18：「先挖再打怪，不然越打越多怪」）：
            // 周围这些怪就是刷怪笼刷出来的，见怪就转头只会被无限拖着打。挨打仍然反击
            // —— threatDetected(false) 内部保留「最近被打过」那条判据。
            boolean suppressProximity = state == MinerState.MINING && module.spawnerPriority();
            if (combat.threatDetected(!suppressProximity)) {
                combatReturnState = state;
                transitionTo(MinerState.COMBAT);
                return;
            }
        }

        stateTick++;

        switch (state) {
            case IDLE -> tickIdle();
            case GO_WILD -> tickGoWild();
            case MINING -> tickMining();
            case UNLOADING -> tickUnloading();
            case SUPPLY -> tickSupply();
            case EATING -> tickEating();
            case REPAIR -> tickRepair();
            case DEATH_HANDLING -> tickDeathHandling();
            case RESPAWN_WAIT -> tickRespawnWait();
            case COMBAT -> tickCombat();
        }
    }

    /** 状态切换（旧 {@code transitionTo}，{@code :203-218} 逐字） */
    private void transitionTo(MinerState newState) {
        if (state == newState) return;

        // 状态退出清理
        onStateExit(state);

        MinerState oldState = state;
        previousState = oldState;
        state = newState;
        stateTick = 0;

        // 状态转换播报
        broadcastStateTransition(oldState, newState);

        // 状态进入初始化
        onStateEnter(newState);
    }

    /** 状态进入副作用（旧 {@code onStateEnter}，{@code :221-251} 逐条一致；战斗态为本项目新增） */
    private void onStateEnter(MinerState newState) {
        if (newState == MinerState.UNLOADING || newState == MinerState.SUPPLY || newState == MinerState.REPAIR
            || newState == MinerState.DEATH_HANDLING || newState == MinerState.COMBAT) {
            module.getContainer().closeContainer();
            module.getBaritone().stop();
        }

        // 物流寻路（卸货/补给/修补）用独立开关控制是否破坏方块；其余状态一律回到全局破坏设置。
        //
        // 旧实现只在进入 MINING 时恢复全局值，于是 GO_WILD / EATING / DEATH_HANDLING 期间 Baritone 一直
        // 停在物流值上；玩家在物流态关模块时全局 allowBreak 会被永久改掉（关模块不会回到挖矿态来恢复）。
        boolean logistics = newState == MinerState.UNLOADING || newState == MinerState.SUPPLY
            || newState == MinerState.REPAIR;
        module.getBaritone().updateSetting("allowBreak", switch (newState) {
            case UNLOADING, SUPPLY, REPAIR -> module.isLogisticsBreakBlocks();
            default -> module.getAllowBreak();
        });
        // 物流态禁用搭路（用户 2026-09-18：「卸货的时候还是会搭路爬上去卸货」）：
        // 物流只需要走到箱子旁边站定，开了放置 Baritone 就会为了凑目标位而垫方块爬高，
        // 箱子高两层本身已经能直接开（isAdjacentTo 按原版交互距离判），完全不需要它搭路。
        //
        // 跑酷不再强制关（用户 2026-09-18：「你可以加快一些卸货寻路速度」）：跑酷是跳跃跨坑，
        // 是物流路上提速最直接的一项；真正被抱怨的是「搭路」和「跑酷中途垫桥」，
        // 所以只关放置与跑酷搭桥，跳跃保留
        module.getBaritone().updateSetting("allowPlace", !logistics && module.settings().allowPlace);
        module.getBaritone().updateSetting("allowParkour", module.settings().allowParkour);
        module.getBaritone().updateSetting("allowParkourPlace", !logistics && module.settings().allowParkourPlace);
        if (newState == MinerState.GO_WILD) {
            // 进「前往野外」一律重新计一次传送尝试：被战斗打断过的话旧起点已不作数（人已经跑开），
            // 不重置的话阶段 3 的距离判定会立刻误判「传送成功」，人就地开始挖矿
            teleportRetries = 0;
        }

        if (newState == MinerState.MINING) {
            // 新一轮挖矿开始，清零 mine 退出重启计数
            mineRestartCount = 0;
            // 刚由「前往野外」落地进挖矿：落地后的位置修正、客户端世界重建都可能在进 MINING 之后才发生
            if (previousState == MinerState.GO_WILD) {
                ownTeleportSettleTicks = Math.max(ownTeleportSettleTicks, OWN_TELEPORT_SETTLE_TICKS);
            }
            mineRestartCooldown = 0;
            // 水中脱困计数随新一轮挖矿清零（同一轮里反复脱困才升级为「换区」）
            waterEscapeCount = 0;
            // 破坏脱困也随新一轮清零：下一轮如果再卡水，重新先试挖开
            waterBreakTried = false;
            waterBreaker.reset();
            // 「挖不动」自愈计数：同一轮挖矿里连续两次才升级为换区，所以这里只清「连续刻数」，
            // 不清「已重试次数」——否则第二级永远到不了（每轮都被清零）
            mineNoProgressTicks = 0;
            // mine 启动宽限基准：进 MINING 当刻先记一次，真正下发 mine 时（启动分支）再刷新一次
            mineStartTick = mc.player.tickCount;
            // 起 mine 前先等「世界就绪」（见字段区注释）：传送落地瞬间起 mine 会被 Baritone 立刻取消。
            // 例外：刚由战斗 / 进食回来时玩家就在原地附近、区块早已加载，等 40 刻纯粹是干站——
            // 用户 2026-09-18：「刚刚杀完怪站着丢，没有边走边丢」（打怪时 Baritone 被停，回来又要干等
            // 2 秒才起步，垃圾就在这 2 秒里一个个丢）。跳过预热后 Baritone 当刻起步，边走边丢
            mineStartIssued = false;
            boolean resumeInPlace = previousState == MinerState.COMBAT
                || previousState == MinerState.EATING;
            mineStartWait = resumeInPlace ? 0 : MINE_START_WARMUP_TICKS;
            mineStartWaited = 0;
            // 下界挖矿自动开启岩浆透视并提示一次（旧 :234-238）
            if (module.isInNether() && !module.isLavaEspEnabled()) {
                module.enableLavaEsp();
                module.info("§e⚠ 检测到下界挖矿 §8▸ 已自动开启岩浆透视");
            }
        }

        if (newState == MinerState.REPAIR) {
            repairPathIssued = false;
            repairSwapAttempts = 0;
            repairSwapRequestedTick = -1;
            repairMode = false;
            savedToolSlot = -1;
            savedWeaponSlot = -1;
        }

        if (newState == MinerState.COMBAT) {
            // 进战斗先把武器工具归位：Baritone 的自动选工具只认快捷栏 0-8，
            // 打怪用的是我们自己那套（MiningCombat#ensureWeapon），同样需要武器在快捷栏里
            module.getContainer().ensureToolsInHotbar();
            // 关键：战斗期间临时关掉 Baritone 的怪物规避。开着它时「怪物附近路径代价更高」，
            // Baritone 会不肯靠近目标 → 表现成「明明要杀它却在原地绕圈」，用户看到的就是「不主动去打」
            module.getBaritone().updateSetting("avoidance", false);
            // 视角让回战斗逻辑：战斗期间 MiningCombat 自己转视角盯怪，男中音转 SERVER 静默（否则两个写入者 → 抖）
            module.getBaritone().setCombatViewHold(true);
        }
    }

    /** 状态退出副作用（旧 {@code onStateExit}，{@code :253-261} 逐字） */
    private void onStateExit(MinerState oldState) {
        if (oldState == MinerState.MINING) {
            // 离开挖矿态：把秒破手上那个方块的会话收掉（发 ABORT 清服务端槽位 + 清裂纹），
            // 否则收摊后残留的 START 槽位会顶掉下一个方块的破坏进度
            MiningFastBreakController.instance().release(mc, true);
            // 连锁队列一并清空（连锁只在采掘中成立；留着会带着旧坐标回到挖矿态）
            module.getVeinMiner().reset();
            module.getBaritone().stop();
        }
        if (oldState == MinerState.REPAIR) {
            stopRepairCombat();
            restoreHotbar();
        }
        if (oldState == MinerState.EATING) {
            // 进食结束：把临时顶掉的热键栏物品换回原位（用户 2026-09-17：垫脚方块被顶进背包换不回来）
            module.getContainer().restoreEatDisplacedItem();
        }
        if (oldState == MinerState.UNLOADING || oldState == MinerState.SUPPLY) {
            // 容器会话收尾：中途被死亡/岩浆/卡死等分支打断时箱子可能还开着，
            // 残留期间所有背包点击都会被客户端当「窗口不匹配」吞掉（换食物、丢垃圾全部失效）
            module.getContainer().closeContainer();
        }
        if (oldState == MinerState.COMBAT) {
            // 还原 Baritone 的怪物规避（战斗期间临时关掉了，见 onStateEnter）
            module.getBaritone().updateSetting("avoidance", module.settings().mobAvoidance);
            // 交回寻路视角（战斗期间让给了 MiningCombat 的转视角）
            module.getBaritone().setCombatViewHold(false);
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  状态处理
    // ═══════════════════════════════════════════════════════════════════

    /** 旧 {@code tickIdle}，{@code :267-270} 逐字 */
    private void tickIdle() {
        // 启动时立即进入去野外
        transitionTo(MinerState.GO_WILD);
    }

    /** 旧 {@code tickGoWild}，{@code :272-320} 逐字 */
    private void tickGoWild() {
        ServerCommandRunner cmdMgr = module.getCmdManager();

        // RTP 冷却等待中：等服务器冷却结束再重发指令（避免冷却期空发失败）
        if (teleportCooldownTicks > 0) {
            teleportCooldownTicks--;
            // 冷却期内传送才落地也算成功（用户 2026-09-18：「我传送成功之后 又被传送了一次」）：
            // 原写法冷却期只倒计时、完全不看位置 —— 服务器 RTP 排队延迟超过「传送等待时长」时，
            // 第一次其实已经传过去了，冷却一结束又重发一条指令，人就被连传两次。
            if (Math.sqrt(mc.player.blockPosition().distSqr(teleportStartPos)) > 2) {
                teleportCooldownTicks = 0;
                teleportRetries = 0;
                teleportRetryNoticeShown = false;
                module.getSoundNotifier().notifyTeleportSuccess();
                module.info("§a✓ 传送已生效 §8▸ 开始挖矿");
                transitionTo(MinerState.MINING);
                return;
            }
            if (teleportCooldownTicks == 0) {
                stateTick = 0; // 冷却结束，下一 tick 重新从阶段 1 发指令
            }
            return;
        }

        // 阶段 1：记录传送前位置并发送传送命令
        if (stateTick == 1) {
            // 只有首次尝试需要记录起点，重试时起点不变（人还在原地）
            if (teleportRetries == 0) teleportStartPos = mc.player.blockPosition();
            teleportRetryNoticeShown = false;
            executeOwnTeleport(module.getWildCommand(), true);
            teleportTimeout = module.getTeleportDelay() * 20; // 转换为tick
            return;
        }

        // 阶段 2：等待命令执行完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 3：检测传送是否成功（原地没动才算失败；RTP 插件可能只移几十格，同样算成功）
        BlockPos currentPos = mc.player.blockPosition();
        double distance = Math.sqrt(currentPos.distSqr(teleportStartPos));

        if (distance > 2) {
            teleportRetries = 0;
            module.getSoundNotifier().notifyTeleportSuccess();
            transitionTo(MinerState.MINING);
            return;
        }

        // 阶段 4：超时仍在原地 → 进入 RTP 冷却等待，冷却结束后重发（最多 3 次），仍失败才停机
        if (stateTick > teleportTimeout) {
            if (!module.isTeleportRetryEnabled()) {
                // 用户 2026-09-18 关掉「传送失败自动重试」：不重发指令，只继续等传送生效
                // （阶段 3 每 tick 都在查位置变化），提示只播一次，避免每 tick 刷屏
                if (!teleportRetryNoticeShown) {
                    teleportRetryNoticeShown = true;
                    module.info("§e⚠ 传送未生效 §8▸ 自动重试已关闭，继续等待传送生效");
                }
                return;
            }
            if (teleportRetries < MAX_TELEPORT_RETRIES) {
                teleportRetries++;
                teleportCooldownTicks = module.getRtpCooldown() * 20;
                module.error("§e⚠ 传送未生效 §8▸ 等待 " + module.getRtpCooldown() + " 秒冷却后重试 " + teleportRetries + "/" + MAX_TELEPORT_RETRIES);
                return;
            }
            module.error("§c✗ 传送失败 §8▸ 已重试 " + MAX_TELEPORT_RETRIES + " 次，自动停止挖矿");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
        }
    }

    /** 旧 {@code tickMining}，{@code :322-607}（种子模式分支整段留白，其余逐条一致） */
    private void tickMining() {
        // 阶段 1：启动采集引擎（普通模式启动 Baritone mine；旧 :326-340）
        // 起动时机（本项目相对旧项目唯一改动，见字段区注释）：先等「预热 N 刻 + 所在区块与四邻区块
        // 已加载」再起 mine。旧项目在传送落地那一瞬就起，Baritone 当刻扫不到目标矿会直接 cancel 并
        // 播报「无法找到前往…的路径，已取消挖掘」——即用户每次第一次传送必现的那条。
        if (!mineStartIssued) {
            if (mineStartWait > 0) {
                mineStartWait--;
                mineStartWaited++;
                return;
            }
            if (!isMiningAreaLoaded() && mineStartWaited < MINE_START_MAX_WAIT_TICKS) {
                mineStartWait = MINE_START_RECHECK_TICKS;
                return;
            }
            // 起 mine 前先把缺失的工具类型搬进快捷栏：Baritone 的自动选工具只扫快捷栏 0-8
            // （反编译 ToolSet#getBestSlot 的循环上界就是 9），工具躺在背包里它根本看不到。
            // 注意：这里要在置 mineStartIssued 之前判断，否则本 tick 直接 return 会让 mine 永远起不来
            if (module.getContainer().ensureToolsInHotbar()) return; // 刚发出换槽包，下一 tick 再起 mine
            mineStartIssued = true;
            lowSpeedTicks = 0;
            // 宽限从「真正下发 mine」这一刻算起（进 MINING 到下发之间还有预热 + 等区块的时间）
            mineStartTick = mc.player.tickCount;
            module.getBaritone().startMining(module.getMiningTargets());
            // 播放开始挖矿音效
            module.getSoundNotifier().notifyMiningStart();
            return;
        }

        // 耐久预警（旧 :343）
        checkToolDurabilityWarning();

        // 优先级 1：死亡检测（已在 tick() 最开始处理）

        // 优先级 2：耐久检测（镐子必备；镐/铲/斧/锄/剑任一工具低于阈值都触发修复，旧 :348-367）
        ItemStack tool = findMiningPickaxe();
        if (tool.isEmpty()) {
            // 停机前先排掉「客户端背包瞬时读空」：世界刚重载（换维度 / 服务器重生式传送）后的窗口内
            // 直接返回等待同步；窗口外也要求连续 PICKAXE_MISSING_TICKS_TO_STOP 刻都读不到才停机。
            // 单刻就停机正是用户 2026-09-17 实机「自检通过却在启动时报缺少镐子」的成因。
            pickaxeMissingTicks++;
            if (inventoryTrustTicks > 0 || pickaxeMissingTicks < PICKAXE_MISSING_TICKS_TO_STOP) {
                return;
            }
            module.getBaritone().stop();
            module.error("§c✗ 缺少镐子 §8▸ 自动挖矿已停止");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }
        pickaxeMissingTicks = 0;

        // 运行期定期补一次「工具回快捷栏」：玩家中途手动整理背包后工具可能又被放回背包，
        // 而 Baritone 的自动选工具只认快捷栏 0-8，看不到背包里的工具
        if (stateTick % 100 == 0) {
            // 进食顶掉的物品若因容器还开着没换回，这里补做（无记录时是空操作）
            module.getContainer().restoreEatDisplacedItem();
            if (module.getContainer().ensureToolsInHotbar()) return;
        }
        if (findDamagedToolSlot() != -1) {
            ItemStack damaged = findDamagedTool();
            // 挂机修复依赖经验修补附魔（打怪掉经验修工具），没有则去修复点也白挂到超时，直接停机
            if (!hasMending(damaged)) {
                module.getBaritone().stop();
                module.error("§c✗ " + toolName(damaged) + "无经验修补附魔 §8▸ 无法自动修复，请换有经验修补的工具");
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                return;
            }
            module.getSoundNotifier().notifyLowDurability();
            transitionTo(MinerState.REPAIR);
            return;
        }

        // 优先级 2.5：饱食度检测（低于15时暂停进食；背包没白名单食物则直接去补给，旧 :369-380）
        FoodData foodData = mc.player.getFoodData();
        if (foodData.getFoodLevel() < 15) {
            module.getBaritone().stop();
            if (hasFoodToEat()) {
                transitionTo(MinerState.EATING);
            } else {
                // 没吃的还进进食状态会死循环（超时→MINING→又饿→又进食），必须转补给
                transitionTo(MinerState.SUPPLY);
            }
            return;
        }

        // 优先级 3：食物不足检测（检查背包食物组数，旧 :382-387）
        if (countFoodStacks() < module.getHungerThreshold()) {
            module.getSoundNotifier().notifyLowFood();
            transitionTo(MinerState.SUPPLY);
            return;
        }

        // 优先级 4：满载检测（旧 :389-394）
        int oreStacks = countOreStacks();
        if (oreStacks >= module.getUnloadThreshold()) {
            transitionTo(MinerState.UNLOADING);
            return;
        }

        // 自动捡起目标矿掉落物（漏捡补偿；背包未满时才捡，旧 :396-397）
        // 连锁挖矿进行中不捡：捡取会 stop 掉 mine 并与连锁抢服务端的单槽破坏位（用户 2026-09-17 追加）
        if (!module.getVeinMiner().isActive() && tryPickupNearbyOre()) return;

        // 普通模式自愈：仅当 Baritone mine 进程意外退出时才重启。（旧 :403-428，种子分支留白）
        // 正常挖掘中并非时刻处于寻路状态（扫描/破坏时 isPathing 为 false），
        // 不能一见「没在寻路」就重启，否则每几秒重扫一遍矿、打断破坏进度。
        if (stateTick > 120) {
            if (mineRestartCooldown > 0) {
                // 重启后冷却：等 mine 进程启动，避免启动延迟被误判成「又退出」
                mineRestartCooldown--;
            } else if (module.getVeinMiner().isActive()) {
                // 连锁挖矿正在用秒破通道逐块清矿脉，期间是我方主动停的 Baritone（服务端单槽位不能两头发包）：
                // 这里必须放行，否则会把「停 mine」误判成「mine 退出」并去重启它，两边抢槽 → 方块挖不烂
            } else if (!module.getBaritone().isPathing() && !module.getBaritone().isMiningActive()) {
                // 目标解析不出方块时重启永远不会成功：直接停机，否则 3 次重启→GO_WILD→回 MINING（计数清零）→
                // 再 3 次重启，变成永不停止的 RTP 循环 + 「挖矿进程已退出」刷屏
                if (module.getTargetBlocks().isEmpty()) {
                    module.error("§c✗ 采掘目标解析不出方块 §8▸ 请在配置页重新选择目标");
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
                // mine 进程真的退出了：重启并播报；连续 3 次仍退出判定附近无矿，RTP 换区
                mineRestartCount++;
                if (mineRestartCount >= 3) {
                    module.info("§e⚠ 附近目标矿已挖完 §8▸ 重新前往野外换区域");
                    mineRestartCount = 0;
                    mineRestartCooldown = 0;
                    transitionTo(MinerState.GO_WILD);
                    return;
                }
                module.info("§e⚠ 挖矿进程已退出 §8▸ 正在重启（" + mineRestartCount + "/3）");
                // 重启也重新走「等世界就绪」的前置等待：mine 被立刻取消多半是当刻已加载区块里扫不到矿，
                // 区块到齐后重启才有意义（重启次数照常累计，连续 3 次拿不到矿仍走 RTP 换区兜底）
                mineStartIssued = false;
                mineStartWait = MINE_START_WARMUP_TICKS;
                mineStartWaited = 0;
                mineRestartCooldown = 100; // 重启后等 5 秒再判断
            } else {
                // 正在正常挖掘，清零重启计数
                mineRestartCount = 0;
            }
        }

        // 界面开合守卫（见 uiOpenTicks 字段注释）：界面开着时挖矿必然停摆，不能算卡死。
        // 关闭界面后若开了较久，重走一次 mine 启动流程，把 Baritone 可能已被自己的卡死检测取消的 mine 拉回来。
        if (mc.screen != null) {
            uiOpenTicks++;
        } else if (uiOpenTicks > 0) {
            boolean needRestart = uiOpenTicks > UI_RELAX_TICKS;
            uiOpenTicks = 0;
            if (needRestart) {
                mineStartIssued = false;
                mineStartWait = MINE_START_WARMUP_TICKS;
                mineStartWaited = 0;
                mineRestartCooldown = 100; // 与「区块未就绪重启」同一口径：重启后给 5 秒观察期
            }
        }
        boolean uiOpen = mc.screen != null;

        // 卡死检测（两种模式共用）：速度监测，3 分钟持续低速判定卡死（旧 :430-449）
        double currentSpeed = Math.sqrt(
            mc.player.getDeltaMovement().x * mc.player.getDeltaMovement().x +
            mc.player.getDeltaMovement().z * mc.player.getDeltaMovement().z
        );

        if (!uiOpen && currentSpeed < MIN_SPEED_THRESHOLD) {
            lowSpeedTicks++;
        } else {
            lowSpeedTicks = 0;
        }

        if (lowSpeedTicks > STUCK_TIME_THRESHOLD) {
            module.error("§c✗ 检测到卡死（速度过低）§8▸ 重新前往野外");
            module.getSoundNotifier().notifyStuck();
            module.getBaritone().stop();
            lowSpeedTicks = 0;
            transitionTo(MinerState.GO_WILD);
            return;
        }

        // 挖不动快速自愈（见字段区注释）：在寻路却原地不动、且秒破没有活跃目标 → 根本挖不动。
        // 界面开着时不算（原版不处理挖掘按键，见 uiOpenTicks）；此外还要排除两种「正常却静止」：
        // ① mine 启动宽限内（落地后 Baritone 扫矿 + 算路径 + 等区块，头 15~20 秒秒破必然空闲）
        // ② 最近 5 秒内实际破坏过方块（块与块之间的间隙不该被计成卡死）
        int sinceLastBreak = mc.player.tickCount - MiningFastBreakController.instance().lastBrokenTick();
        boolean mineNoProgress = !uiOpen
            && mc.player.tickCount - mineStartTick > MINE_START_GRACE_TICKS
            && sinceLastBreak > MINE_PROGRESS_GRACE_TICKS
            && module.getBaritone().isPathing()
            && currentSpeed < MIN_SPEED_THRESHOLD
            && !MiningFastBreakController.instance().isActive();
        mineNoProgressTicks = mineNoProgress ? mineNoProgressTicks + 1 : 0;
        // 真的动起来了就算脱困成功：清零「已重试次数」，这样两次判定必须是「重下发后立刻又卡」，
        // 而不是「挖了半小时偶尔卡一次就直接被判换区」
        if (!mineNoProgress && currentSpeed >= MIN_SPEED_THRESHOLD) mineNoProgressResets = 0;
        if (mineNoProgressTicks > MINE_NO_PROGRESS_LIMIT) {
            mineNoProgressTicks = 0;
            mineNoProgressResets++;
            module.getSoundNotifier().notifyStuck();
            module.getBaritone().stop();
            if (mineNoProgressResets >= 2) {
                // 换了矿点还是挖不动：这片区域的方块挖不穿（受保护 / 地形封死），换区兜底
                mineNoProgressResets = 0;
                module.error("§c✗ 方块挖不动反复卡死 §8▸ 重新前往野外换区域");
                transitionTo(MinerState.GO_WILD);
            } else {
                // 先把够不到的刷怪笼甩掉：mine 目标里它最近，不甩掉重下发等于白重发
                module.ignoreSpawnerForNow();
                module.info("§e⚠ 挖不动面前的方块 §8▸ 重置采掘目标脱困");
                // 重下发等于一次新的 mine 启动：宽限重新计时，否则重下发后的「扫矿 + 算路径」空窗
                // 会立刻把第二次判定顶到，直接跳到「换区」（保守优先：宁可多等，不乱换区）
                mineStartTick = mc.player.tickCount;
                module.getBaritone().startMining(module.getMiningTargets());
            }
            return;
        }

        // 原地抖动卡死检测（每 10 秒采样位置，旧 :451-490）：Baritone 在点位附近原地抖动的通病，
        // 抖动时速度不为 0 抓不到，需按位移判断。连续 3 分钟位移<2格判定卡死。
        // 界面开着时不采样（见 uiOpenTicks）：那段时间人必然不动，采样会白白累计
        if (!uiOpen && stateTick % 200 == 0) {
            BlockPos curPos = mc.player.blockPosition();
            if (!lastPosSample.equals(BlockPos.ZERO) && curPos.distSqr(lastPosSample) < 4.0) {
                noMoveTicks += 200;
            } else {
                noMoveTicks = 0;
                stuckResetCount = 0; // 玩家在正常移动，重置连续卡死计数
            }
            lastPosSample = curPos;
        }

        // 水中停滞计米（本轮加强，见方法注释）
        updateWaterStuckMeter(currentSpeed);

        if (noMoveTicks > NO_MOVE_THRESHOLD) {
            module.getSoundNotifier().notifyStuck();
            module.getBaritone().stop();
            noMoveTicks = 0;
            stuckResetCount++;
            if (stuckResetCount >= 2) {
                // 连续两次抖动卡死，重置采掘目标也脱不了困，才传送去野外
                stuckResetCount = 0;
                module.error("§c✗ 原地抖动卡死（连续两次）§8▸ 重新前往野外");
                transitionTo(MinerState.GO_WILD);
            } else {
                // 先重置采掘目标脱困（换一个矿点），不急着传送
                module.info("§e⚠ 原地抖动卡死 §8▸ 重置采掘目标脱困");
                module.getBaritone().startMining(module.getMiningTargets());
            }
            return;
        }

        // 水中卡死：① 破坏卡住玩家的方块 → ② 寻路到最近陆地 → ③ RTP 换区（①为本轮新增，②③照旧 :492-551）
        if (mc.player.isInWater()) {
            // 触发条件（用户 2026-09-18 要求「缩短时间」后）：
            //   · 水平净位移判据满 WATER_STUCK_TICKS（3 秒水平几乎没动，含「被水面顶住上下弹跳」）；
            //   · 或瞬时速度判据满 200 刻 10 秒（兜底，例如被水流推着原地打转）。
            // 旧实现只有 30 秒一条，且位移判据含 Y 轴（弹跳时永远命中不了）——所以用户看到的是「一直弹就是不脱困」。
            if (waterNoMoveTicks >= WATER_STUCK_TICKS || waterStuckTicks > WATER_SPEED_STUCK_TICKS) {
                if (waterEscapeActive || waterBreaker.isActive()) {
                    // 正在脱困（破坏 / 寻路）：不重复触发，由下面的推进分支收尾
                } else if (waterEscapeCount >= MAX_WATER_ESCAPES) {
                    // 反复脱困说明这片水（或水对岸的矿）根本过不去，别再耗时间，直接换区
                    module.getSoundNotifier().notifyStuck();
                    module.getBaritone().stop();
                    waterStuckTicks = 0;
                    waterNoMoveTicks = 0;
                    waterEscapeCount = 0;
                    module.error("§c✗ 水中卡死反复脱困失败（" + MAX_WATER_ESCAPES + " 次）§8▸ 重新前往野外");
                    transitionTo(MinerState.GO_WILD);
                    return;
                } else if (!waterBreakTried) {
                    // 第一级：先把卡住玩家的方块挖开（用户 2026-09-18：
                    // 「检测到被水卡住上下跳动 自动破坏周围的方块逃离 继续进入状态机」）
                    waterBreakTried = true;
                    waterStuckTicks = 0;
                    waterNoMoveTicks = 0;
                    module.getSoundNotifier().notifyStuck();
                    module.getBaritone().stop();
                    if (waterBreaker.start()) {
                        module.info("§e⚠ 水中卡死 §8▸ 破坏周围方块脱困");
                        return;
                    }
                    // 周围没有可破坏的方块（开阔水域卡住）：直接升级到寻路脱困
                    if (!startWaterEscapePathing()) {
                        transitionTo(MinerState.GO_WILD);
                    }
                    return;
                } else {
                    // 第二级：寻路到最近陆地
                    waterStuckTicks = 0;
                    waterNoMoveTicks = 0;
                    if (!startWaterEscapePathing()) {
                        transitionTo(MinerState.GO_WILD);
                    }
                    return;
                }
            }
        } else {
            waterStuckTicks = 0;
            waterNoMoveTicks = 0;
            lastWaterSample = null;
            // 上岸了：脱困流程无论停在哪一级（破坏 / 寻路），都要给玩家一个交代并把 mine 拉回来。
            // 这件事不能只写在下面的推进分支里——玩家一离开水，isInWater 为假就走到了本分支，
            // 推进分支根本没机会执行，于是只能干等状态机的 mine 自愈分支（最多 6 秒）才重新开工
            boolean wasEscaping = waterEscapeActive || waterBreaker.isActive();
            waterEscapeActive = false;
            waterBreaker.reset();
            waterBreakTried = false;
            if (wasEscaping) {
                module.getBaritone().stop();
                module.info("§a✓ 已脱离水域 §8▸ 继续挖矿");
                module.getBaritone().startMining(module.getMiningTargets());
                return;
            }
        }

        // 水中脱困推进：破坏级 → 寻路级（已上岸由上面的分支收尾；超时仍未脱困则 RTP 兜底，旧 :529-551）
        if (waterBreaker.isActive()) {
            BlockPos breaking = waterBreaker.target();
            if (breaking != null) faceBlock(breaking); // Baritone 已停，这里是唯一写入者，对准作业面
            if (waterBreaker.tick()) return;           // 还在破坏：本刻到此为止
            // 破坏级收摊但仍在水里（挖不动 / 候选挖完了）：接着走寻路级
            if (!startWaterEscapePathing()) {
                transitionTo(MinerState.GO_WILD);
            }
            return;
        }

        if (waterEscapeActive) {
            waterEscapeTicks++;
            // 「到目的地」也算脱困：岸边那格水常年淹着脚，只按 isInWater 判会白等到超时然后被 RTP
            boolean reachedTarget = waterEscapeTarget != null
                && horizontalDistSqr(mc.player.blockPosition(), waterEscapeTarget) <= 1.0;
            if (!mc.player.isInWater() || reachedTarget) {
                waterEscapeActive = false;
                waterEscapeTicks = 0;
                waterEscapeTarget = null;
                module.getBaritone().stop();
                module.info("§a✓ 已脱离水域 §8▸ 继续挖矿");
                module.getBaritone().startMining(module.getMiningTargets());
                return;
            }
            if (waterEscapeTicks > 400) { // 20 秒仍未脱困，RTP 兜底
                waterEscapeActive = false;
                waterEscapeTicks = 0;
                waterEscapeTarget = null;
                module.getBaritone().stop();
                module.error("§c✗ 水中脱困超时 §8▸ 重新前往野外");
                transitionTo(MinerState.GO_WILD);
                return;
            }
            return; // 脱困寻路中，暂停其它挖矿逻辑
        }

        // 岩浆避险（本轮加强，用户 2026-09-17：「老是擦着岩浆边走，老是被烧」）：
        //   ① 判据半径改用可配置的「岩浆安全距离」（settings.lavaAvoidRadius，默认 2 格）——
        //      旧实现只看相邻 1 格（hasLavaNear(1)），贴着岩浆走完全不算危险；
        //   ② 除了岩浆方块，玩家已在岩浆里 / 身上着火也立即触发（这两条不等采样）；
        //   ③ 脱困成功后给一段冷却，避免在岩浆矿区反复「触发→脱困→再触发」打断挖矿；
        //   ④ 采样间隔 10 刻 → 5 刻（用户 2026-09-17：「老是擦着岩浆边走，老是被烧」）：
        //      判据只是 (2·半径+1)³ 次方块读取（半径 4 也才 729 次），加密采样几乎不花钱，
        //      但贴边时能早 0.25 秒刹车。
        if (lavaEscapeCooldown > 0) lavaEscapeCooldown--;
        int lavaRadius = Math.max(1, module.settings().lavaAvoidRadius);
        boolean lavaDanger = mc.player.isInLava() || mc.player.getRemainingFireTicks() > 0;
        if (!lavaEscapeActive && (lavaDanger
            || (lavaEscapeCooldown <= 0 && stateTick % 5 == 0 && hasLavaNear(lavaRadius)))) {
            module.getBaritone().stop();
            BlockPos safe = findNearestSafeSpot(lavaRadius);
            if (safe != null) {
                var baritone = module.getBaritone().getBaritoneInstance();
                if (baritone != null) {
                    baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(safe));
                    lavaEscapeActive = true;
                    lavaEscapeTicks = 0;
                    module.info(lavaDanger
                        ? "§e⚠ 已接触岩浆/着火 §8▸ 寻路到安全位置"
                        : "§e⚠ 附近 " + lavaRadius + " 格内有岩浆 §8▸ 寻路到安全位置");
                } else {
                    module.error("§c✗ Baritone 未加载 §8▸ 重新前往野外");
                    transitionTo(MinerState.GO_WILD);
                    return;
                }
            } else {
                module.error("§c✗ 附近全是岩浆 §8▸ 重新前往野外");
                transitionTo(MinerState.GO_WILD);
                return;
            }
        }

        // 岩浆脱困推进：已远离岩浆则恢复挖矿；超时仍未脱困则 RTP 兜底（旧 :576-598）
        if (lavaEscapeActive) {
            lavaEscapeTicks++;
            if (!hasLavaNear(lavaRadius) && !mc.player.isInLava() && mc.player.getRemainingFireTicks() <= 0) {
                lavaEscapeActive = false;
                lavaEscapeTicks = 0;
                lavaEscapeCooldown = LAVA_ESCAPE_COOLDOWN_TICKS;
                module.getBaritone().stop();
                module.info("§a✓ 已远离岩浆 §8▸ 继续挖矿");
                module.getBaritone().startMining(module.getMiningTargets());
                return;
            }
            if (lavaEscapeTicks > 400) { // 20 秒仍未脱困，RTP 兜底
                lavaEscapeActive = false;
                lavaEscapeTicks = 0;
                module.getBaritone().stop();
                module.error("§c✗ 岩浆脱困超时 §8▸ 重新前往野外");
                transitionTo(MinerState.GO_WILD);
                return;
            }
            return; // 脱困寻路中，暂停其它挖矿逻辑
        }

        // Baritone 卡死检测（普通模式专用，种子模式由 seedPathRetries 兜底——种子模式本轮留白，旧 :600-606）
        if (stateTick > 6000 && stateTick % 1200 == 0) {
            if (module.getBaritone().isStuck()) {
                module.getBaritone().stop();
                transitionTo(MinerState.GO_WILD);
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  战斗拦截（用户 2026-09-17 新增需求；旧项目无对应实现，逐条见 MiningCombat）
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 战斗态每刻：锁定怪物 → 切武器 → 转头 → 按原版攻击冷却出手（苦力怕另走「打了就退」走位）。
     *
     * <p>具体行为都在 {@link MiningCombat}（自带一套最简战斗循环，不依赖杀戮光环模块的设置）；
     * 本方法只负责状态迁移与播报：威胁消失后回「采掘中」——回 MINING 会走
     * {@code onStateEnter(MINING)}，于是 mine 重启自然带上「等世界就绪」那一套，
     * 不会传送到新区域就空挖。</p>
     */
    private void tickCombat() {
        if (combat.tickCombat()) return;
        module.info(combatReturnState == MinerState.GO_WILD
            ? "§a✓ 威胁解除 §8▸ 继续前往野外"
            : "§a✓ 威胁解除 §8▸ 继续挖矿");
        combat.reset();
        transitionTo(combatReturnState);
    }

    /**
     * 发一条<b>我方自己</b>的传送类指令（/rtp、/home、/back、挂机点、重生…），并开启手动传送检测的宽限窗。
     *
     * <p>为什么统一包一层：这些指令发出后玩家位置会出现大跳变（落地修正、客户端世界重建），
     * 若不加宽限窗，首次启动「GO_WILD 发 RTP → 落地进 MINING」就会被自家的手动传送检测误判停机
     * （用户 2026-09-18 实机）。玩家真的在挖矿中途手敲 /home 时，距上次我方指令早就超过宽限窗，照样能判出来。</p>
     */
    private void executeOwnTeleport(String command) {
        executeOwnTeleport(command, false);
    }

    /**
     * 发一条我方的传送指令。
     *
     * @param allowGuiClick 是否启用 RTP 的 GUI 自动点击。只有 RTP 走 GUI 选单（插件弹菜单要等它），
     *                      卸货 / 补给 / 挂机 / 回家都是瞬移——旧实现无条件等 GUI，导致每次卸货
     *                      白站 5 秒（用户 2026-09-18：「传送回去卸货站在原地的时间太久了」）
     */
    private void executeOwnTeleport(String command, boolean allowGuiClick) {
        ownTeleportPendingTicks = OWN_TELEPORT_PENDING_TICKS;
        ownTeleportSettleTicks = 0;
        module.getCmdManager().executeCommand(command, allowGuiClick);
    }

    /**
     * 手动传送检测（用户 2026-09-17 需求：「挖矿状态下输入了 /home 回家，模块还在继续挖」；
     * 2026-09-18 补宽限窗）。
     *
     * <p>判据：玩家位置在<b>单刻内</b>跳变超过 {@link #MANUAL_TELEPORT_DISTANCE_SQR}（32 格）。
     * 正常移动/跌落单刻最多几格，只有服务器传送类指令（/home、/spawn、/tpa…）能达到这个量级。</p>
     *
     * <p>基准位置每刻都更新（所有状态），但只有 {@code MINING} 才判定：卸货 / 补给 / 修补三态是我们
     * 自己发传送指令的地方，判了会误伤自己。检测到就按用户裁定<b>立刻停机并播报</b>——
     * 最安全：绝不会在家里、别人基地里继续挖。</p>
     *
     * <p><b>我方传送的处理</b>（用户 2026-09-18：「我第一次启动传送 给我停机了」与
     * 「挖矿途中手动 /home 还是会在家里挖」两条并存）：{@link #executeOwnTeleport} 发指令时置位
     * {@link #ownTeleportPendingTicks}，检测到<b>第一次</b>大跳变就把它消费掉（那是我方落地），
     * 随后 {@link #OWN_TELEPORT_SETTLE_TICKS} 刻内的跳变算落地修正；窗口过后立刻恢复判定。
     * 因此既不会把自家 RTP 当成手动传送，也不会像固定 20 秒宽限窗那样把玩家的 /home 一起挡掉。</p>
     *
     * @return true 表示本刻检测到手动传送并已停机（调用方直接结束本刻）
     */
    private boolean manualTeleportDetected() {
        if (mc.player == null) return false;
        BlockPos current = mc.player.blockPosition();

        boolean jumped = lastWatchPos != null
            && state == MinerState.MINING
            && current.distSqr(lastWatchPos) > MANUAL_TELEPORT_DISTANCE_SQR;

        // 我方传送的落地跳变（以及落地后的位置修正）：消费掉，绝不当成玩家手动传送
        if (jumped && (ownTeleportPendingTicks > 0 || ownTeleportSettleTicks > 0)) {
            ownTeleportPendingTicks = 0;
            ownTeleportSettleTicks = OWN_TELEPORT_SETTLE_TICKS;
            lastWatchPos = current;
            return false;
        }

        if (ownTeleportPendingTicks > 0) ownTeleportPendingTicks--;
        if (ownTeleportSettleTicks > 0) ownTeleportSettleTicks--;

        lastWatchPos = current;
        if (!jumped) return false;

        module.getSoundNotifier().notifyStuck();
        module.getBaritone().stop();
        // 用户 2026-09-18 裁定「自动重新去野外挖」（此前是 `setEnabled(false)` 直接关掉模块，
        // 用户实机表现为「传送之后秒破跟连锁都失效，重启模块才恢复」——chatlog 19:48:21 那条
        // 「✗ 检测到手动传送 ▸ 已停止挖矿」就是它）：
        // 任何来源的传送都意味着「当前位置不是我们选定的矿区」，所以复位到 GO_WILD 重走一遍传送流程，
        // 在新矿区继续挖。既保住原来「不在主城/家里乱挖」的诉求，也不用玩家手动重开模块。
        module.info("§e⚠ 检测到传送 §8▸ 重新前往野外挖矿");
        transitionTo(MinerState.GO_WILD);
        return true;
    }

    /**
     * 水中停滞计米（每刻调用，只在水中累计）。
     *
     * <p>用户 2026-09-17 实机：「卡在水里、前面有矿一直想过去，被水顶住一直上下弹跳动来动去，
     * 就是不自动脱困」。旧实现的两条判据都抓不到这个形态：</p>
     * <ul>
     *   <li>瞬时速度判据：上下弹跳时 {@code getDeltaMovement} 一直不为 0，永远累计不到；</li>
     *   <li>净位移判据：挂在前面的原地抖动采样上，而那里用的是 {@code distSqr}（<b>含 Y 轴</b>），
     *       弹跳本身就制造了 Y 位移，10 秒的位移阈值永远满足不了。</li>
     * </ul>
     *
     * <p>现在独立计米，<b>只看 X/Z 的水平净位移</b>：每 {@link #WATER_SAMPLE_TICKS} 采样一次，
     * 1 秒内水平移动不足 1 格就累计；累计满 {@link #WATER_STUCK_TICKS}（用户 2026-09-18 要求缩短后为 3 秒）
     * 即判定水中卡死。水里来回游动（净位移接近 0）同样会被判卡死，正是想要的语义。</p>
     *
     * <p>瞬时速度判据保留作兜底（例如被水流推着原地打转，水平速度不为 0 但也没有净位移时，
     * 位移判据已经能命中；速度判据只负责「完全静止」这种更极端的情况）。</p>
     */
    private void updateWaterStuckMeter(double currentSpeed) {
        if (mc.player == null) return;
        if (!mc.player.isInWater()) {
            waterStuckTicks = 0;
            waterNoMoveTicks = 0;
            lastWaterSample = null;
            return;
        }
        // 界面开着时不计米（见 uiOpenTicks 字段注释）：那段时间挖不停是原版语义，不是被水卡住
        if (mc.screen != null) {
            waterStuckTicks = 0;
            waterNoMoveTicks = 0;
            lastWaterSample = null;
            return;
        }

        if (stateTick % WATER_SAMPLE_TICKS == 0) {
            BlockPos current = mc.player.blockPosition();
            if (lastWaterSample != null && horizontalDistSqr(current, lastWaterSample) < 1.0) {
                waterNoMoveTicks += WATER_SAMPLE_TICKS;
            } else {
                waterNoMoveTicks = 0;
            }
            lastWaterSample = current;
        }

        if (currentSpeed < MIN_SPEED_THRESHOLD) {
            waterStuckTicks++;
        } else {
            waterStuckTicks = 0;
        }
    }

    /** 只算水平方向（X/Z）的平方距离：水中判据必须剔除上下弹跳的 Y 分量 */
    private static double horizontalDistSqr(BlockPos a, BlockPos b) {
        double dx = a.getX() - b.getX();
        double dz = a.getZ() - b.getZ();
        return dx * dx + dz * dz;
    }

    /**
     * 水中脱困第二级：寻路到最近陆地（旧 {@code :501-522} 的整段，抽成方法供「破坏级」复用）。
     *
     * <p>「到达目的地」也算脱困的判定在调用方的推进分支里（岸边那格水常年淹着脚，
     * 只按 {@code isInWater} 判会白等到超时然后被 RTP）。</p>
     *
     * @return true 表示已下发寻路目标；false 表示附近没有可站立的陆地或 Baritone 未就绪，
     *         调用方应直接走换区兜底
     */
    private boolean startWaterEscapePathing() {
        if (mc.player == null) return false;
        BlockPos land = findNearestLand();
        if (land == null) {
            waterStuckTicks = 0;
            module.error("§c✗ 水中卡死且周围无陆地 §8▸ 重新前往野外");
            return false;
        }
        var baritone = module.getBaritone().getBaritoneInstance();
        if (baritone == null) {
            waterStuckTicks = 0;
            return false;
        }
        waterEscapeCount++;
        waterEscapeTarget = land;
        baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(land));
        waterEscapeActive = true;
        waterEscapeTicks = 0;
        module.info("§e⚠ 水中卡死 §8▸ 寻路到最近陆地脱困（第 " + waterEscapeCount + "/"
            + MAX_WATER_ESCAPES + " 次）");
        return true;
    }

    /** 旧 {@code findNearestLand}，{@code :704-725} 逐字 */
    private BlockPos findNearestLand() {
        if (mc.player == null || mc.level == null) return null;
        BlockPos feet = mc.player.blockPosition();
        for (int r = 0; r <= 40; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) continue; // 切比雪夫环，只扫当前半径一圈
                    for (int dy = -2; dy <= 2; dy++) {
                        BlockPos p = feet.offset(dx, dy, dz);
                        // 该格无流体、非空气、上方可站（空气且无流体）
                        if (mc.level.getFluidState(p).isEmpty()
                            && !mc.level.getBlockState(p).isAir()
                            && mc.level.getBlockState(p.above()).isAir()
                            && mc.level.getFluidState(p.above()).isEmpty()) {
                            return p;
                        }
                    }
                }
            }
        }
        return null;
    }

    /** 旧 {@code hasLavaNear}，{@code :728-741} 逐字（半径 1，死亡点判定等内容仍用这个口径） */
    private boolean hasLavaNear(int radius) {
        return mc.player != null && hasLavaNear(mc.player.blockPosition(), radius);
    }

    /** 以指定位置为中心扫描岩浆（避险半径与安全点挑选共用） */
    private boolean hasLavaNear(BlockPos center, int radius) {
        if (mc.level == null) return false;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (mc.level.getBlockState(center.offset(dx, dy, dz)).getBlock() == Blocks.LAVA) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 找最近的、连避险半径内都没有岩浆的安全落脚点。
     *
     * <p>旧 {@code findNearestSafeSpot}，{@code :744-766} 只判「候选点本身/上下没有岩浆」，
     * 于是站在岩浆湖边上时，找出来的「安全点」可能离岩浆不到 1 格，脱困一完成立刻又触发避险。
     * 现追加 {@code !hasLavaNear(candidate.above(), avoidRadius)}，一步就走到真正安全的距离。</p>
     */
    private BlockPos findNearestSafeSpot(int avoidRadius) {
        if (mc.player == null || mc.level == null) return null;
        BlockPos feet = mc.player.blockPosition();
        for (int r = 1; r <= 16; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) continue;
                    for (int dy = -2; dy <= 2; dy++) {
                        BlockPos p = feet.offset(dx, dy, dz);
                        // 脚下固体、身体空气、脚/身/头三格都无岩浆
                        if (mc.level.getBlockState(p).getBlock() != Blocks.LAVA
                            && !mc.level.getBlockState(p).isAir()
                            && mc.level.getBlockState(p.above()).isAir()
                            && mc.level.getBlockState(p.above()).getBlock() != Blocks.LAVA
                            && mc.level.getBlockState(p.above(2)).getBlock() != Blocks.LAVA
                            // 站点本身也要离岩浆够远（先判便宜的条件，绝大多数候选点在这里就被筛掉）
                            && !hasLavaNear(p.above(), avoidRadius)) {
                            return p;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 扫描快捷栏，返回挖掘指定方块最快的工具槽位（无快于空手的工具返回 -1）。
     *
     * <p>旧 {@code findBestToolSlot}，{@code :837-850} 逐字。旧项目唯一调用方是种子模式的
     * {@code ensureBestToolForSeedTarget}（本轮留白），本方法按规格保留备用。</p>
     */
    private int findBestToolSlot(BlockState state) {
        int best = -1;
        double bestSpeed = 1.0; // 空手破坏速度基准
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;
            double speed = stack.getDestroySpeed(state);
            if (speed > bestSpeed) {
                bestSpeed = speed;
                best = i;
            }
        }
        return best;
    }

    /** 旧 {@code findNearbyOreDrop}，{@code :871-894} 逐字 */
    private ItemEntity findNearbyOreDrop(double radius) {
        if (mc.level == null || mc.player == null) return null;
        Set<String> acceptIds = module.isSilkTouchMode()
            ? module.getTargetBlockIds()
            : Set.of(module.getTargetDropItemId());

        List<ItemEntity> items = mc.level.getEntitiesOfClass(
            ItemEntity.class, mc.player.getBoundingBox().inflate(radius), e -> true);
        ItemEntity nearest = null;
        double best = radius * radius;
        for (ItemEntity item : items) {
            ItemStack stack = item.getItem();
            if (stack.isEmpty()) continue;
            if (pickupBlacklist.contains(item.blockPosition())) continue; // 卡角落捡不起来的位置，跳过
            // 只认「已经躺了一会儿」的漏捡物：刚挖出来的掉落物就在脚下，Baritone 挖掘过程自己会走到并
            // 拾取；为它停下 mine 再重启，就变成用户看到的「挖一下→停→捡→重启」1~2 秒刷屏循环
            if (item.tickCount < PICKUP_MIN_AGE_TICKS) continue;
            String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
            if (!acceptIds.contains(id)) continue;
            double d = item.distanceToSqr(mc.player);
            if (d < best) {
                best = d;
                nearest = item;
            }
        }
        return nearest;
    }

    /**
     * 自动捡起附近掉落的目标矿（漏捡补偿）。
     * 返回 true 表示正在捡取（暂停本帧挖矿逻辑），false 表示未在捡取。
     *
     * <p>旧 {@code tryPickupNearbyOre}，{@code :900-936}；旧 {@code isSeedMiningEnabled()} 分支留白，
     * 放弃捡取后一律恢复普通模式挖掘。</p>
     *
     * <p><b>本项目修正（用户 2026-09-17 实机：挖矿老是莫名暂停 + 聊天栏刷屏）</b>：旧实现只要 6 格内
     * 出现目标矿掉落物就 {@code stop} 掉 mine 去捡，捡完再 {@code startMining} 重启，而刚挖出的掉落物
     * 本来就在脚下、挖掘流程自然会拾取 —— 于是每 1~2 秒循环一次「已取消 (x2) / 正在挖掘 /
     * Baritone 已启动挖掘」。现改为：只捡存在满 {@code PICKUP_MIN_AGE_TICKS} 刻的漏捡物；捡取结束或
     * 放弃后冷却 {@code PICKUP_COOLDOWN_TICKS} 刻；够不到（寻路进程起不来）时 1 秒内即判不可达并进黑名单，
     * 不再空等满 300 刻。判据之外的文案与恢复挖掘的落点不变。</p>
     */
    private boolean tryPickupNearbyOre() {
        if (mc.player == null || mc.level == null) return false;
        // 背包已满时不捡，否则捡不起来反而卡住（掉落物一直存在→反复寻路→原地打转）
        if (countOreStacks() >= module.getUnloadThreshold()) return false;
        if (pickupCooldownTicks > 0) pickupCooldownTicks--;

        if (pickupTarget == null) {
            if (pickupCooldownTicks > 0) return false;
            // 每 10 tick 扫一次，避免每帧全量扫实体
            if (stateTick % 10 != 0) return false;
            ItemEntity drop = findNearbyOreDrop(6.0);
            if (drop == null) return false;
            pickupTarget = drop;
            pickupTimeout = 0;
            module.getBaritone().stop();
            var baritone = module.getBaritone().getBaritoneInstance();
            if (baritone != null) {
                baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(drop.blockPosition()));
            }
            return true;
        }

        // 已有捡取目标：消失/超时/太远/够不到 → 放弃并恢复挖矿
        pickupTimeout++;
        boolean picked = pickupTarget.isRemoved() || !pickupTarget.isAlive();
        // 已过 1 秒却连寻路进程都不在（Baritone 算不出路径或已放弃）＝ 掉落物卡在够不到的地方
        boolean unreachable = !picked && pickupTimeout > PICKUP_PATH_FAIL_TICKS
            && !module.getBaritone().isCustomGoalActive();
        boolean tooFar = !picked && pickupTarget.distanceTo(mc.player) > 16;
        if (picked || unreachable || tooFar || pickupTimeout > 300) {
            // 捡到了就不记黑名单；够不到/超时/太远说明这位置捡不起来，记黑名单避免反复寻路捡同一个
            if (!picked) addPickupBlacklist(pickupTarget.blockPosition());
            pickupTarget = null;
            pickupCooldownTicks = PICKUP_COOLDOWN_TICKS;
            module.getBaritone().stop();
            module.getBaritone().startMining(module.getMiningTargets());
            return false;
        }
        return true;
    }

    /** 旧 {@code addPickupBlacklist}，{@code :939-944} 逐字 */
    private void addPickupBlacklist(BlockPos pos) {
        pickupBlacklist.add(pos);
        if (pickupBlacklist.size() > MAX_PICKUP_BLACKLIST) {
            pickupBlacklist.clear();
        }
    }

    /** 旧 {@code checkToolDurabilityWarning}，{@code :946-989} 逐字 */
    private void checkToolDurabilityWarning() {
        if (mc.player == null) return;

        // 找出耐久最低的可修复工具（镐/铲/斧/锄/剑，含副手），预警提示跟修复触发用同一套判定
        ItemStack lowest = ItemStack.EMPTY;
        int lowestRemaining = Integer.MAX_VALUE;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!isRepairableTool(stack)) continue;
            Integer maxDamage = stack.get(DataComponents.MAX_DAMAGE);
            Integer damage = stack.get(DataComponents.DAMAGE);
            if (maxDamage == null || damage == null) continue;
            int remaining = maxDamage - damage;
            if (remaining < lowestRemaining) {
                lowestRemaining = remaining;
                lowest = stack;
            }
        }

        ItemStack offhand = mc.player.getOffhandItem();
        if (isRepairableTool(offhand)) {
            Integer maxDamage = offhand.get(DataComponents.MAX_DAMAGE);
            Integer damage = offhand.get(DataComponents.DAMAGE);
            if (maxDamage != null && damage != null) {
                int remaining = maxDamage - damage;
                if (remaining < lowestRemaining) {
                    lowestRemaining = remaining;
                    lowest = offhand;
                }
            }
        }

        if (lowest.isEmpty()) return;

        int threshold = module.getDurabilityThreshold();
        // 耐久进入预警区（阈值 1.5 倍以内但未触发修复），每 30 秒提醒一次
        if (lowestRemaining <= threshold * 1.5 && lowestRemaining > threshold) {
            if (stateTick % 600 == 0) {
                module.info("§e⚠ " + toolName(lowest) + " 剩余耐久 " + lowestRemaining + " §8▸ 即将触发修复流程");
                module.getSoundNotifier().notifyLowDurability();
            }
        }
    }

    /** 旧 {@code toolName}，{@code :992-1001} 逐字 */
    private String toolName(ItemStack stack) {
        if (stack.isEmpty()) return "工具";
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        if (id.endsWith("_pickaxe")) return "镐子";
        if (id.endsWith("_shovel")) return "铲子";
        if (id.endsWith("_axe")) return "斧头";
        if (id.endsWith("_hoe")) return "锄头";
        if (id.endsWith("_sword")) return "剑";
        return "工具";
    }

    /**
     * 是否已到「可以从这里开箱」的位置。
     *
     * <p>旧 {@code isAdjacentTo}（{@code :1011-1017}）要求切比雪夫距离 ≤1（玩家与容器同层前后左右），
     * Baritone 的 {@code GoalGetToBlock} 合法终点就是切比雪夫邻域，斜着接近时停在对角格也算到达。
     * 但用户 2026-09-17 实机：潜影盒打包机比玩家高 2 格时，为了凑到容器那一层 Baritone 会
     * <b>自动垫方块爬上去</b>——又慢又难看。现改为按<b>原版交互距离</b>判：</p>
     * <ul>
     *   <li>水平切比雪夫 ≤2 格，垂直 -2 ~ +2 格；</li>
     *   <li>眼睛到方块中心 ≤4.0 格（原版交互距离 4.5 格留余量）。</li>
     * </ul>
     *
     * <p>于是「容器高两层、站在下面」也算到位，直接开箱，不再垫方块。
     * 与 {@code MiningContainer.withinOpenRange} 的开箱邻域判据必须同一套，
     * 否则会出现「状态机认为到了、容器层却拒绝开」的开箱死锁。</p>
     */
    private boolean isAdjacentTo(BlockPos target) {
        if (mc.player == null || mc.level == null || target == null) return false;
        BlockPos player = mc.player.blockPosition();
        int dx = Math.abs(player.getX() - target.getX());
        int dz = Math.abs(player.getZ() - target.getZ());
        int dy = player.getY() - target.getY();
        if (dx > 2 || dz > 2 || dy > 2 || dy < -2) return false;
        if (dx == 0 && dy == 0 && dz == 0) return false;
        Vec3 eye = mc.player.getEyePosition();
        Vec3 center = new Vec3(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5);
        return eye.distanceToSqr(center) <= CONTAINER_OPEN_DISTANCE_SQR;
    }

    /** 物流寻路重发：GoalNear(2)，走到容器 2 格内即满足开箱判据（不用 GoalGetToBlock，理由见阶段 3 注释） */
    private void repathLogistics(BlockPos pos) {
        var baritone = module.getBaritone().getBaritoneInstance();
        if (baritone != null) {
            baritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(pos, 2));
        }
    }

    /**
     * 物流寻路停滞监测（卸货 / 补给阶段 3 每采样轮调用一次）。
     *
     * <p>按「与目标容器的实际距离」判定进展：Baritone 卡住时 isCustomGoalActive 仍为 true，
     * 绿框一直闪，却原地不动（想搭路又搭不了）。连续无接近就逐级脱困——
     * 第 1 轮重发目标，第 2 轮临时允许破坏挡路方块（物流态默认禁挖，面前有方块挡着
     * 又绕不过去就是死局），第 3 轮仍无进展判定走不到。</p>
     *
     * @return true 表示已判定「走不到」，调用方应停机
     */
    private boolean logisticsStalled(BlockPos target, String actionName) {
        if (mc.player == null || target == null) return false;
        if (stateTick < LOGISTICS_SAMPLE_TICKS || stateTick % LOGISTICS_SAMPLE_TICKS != 0) return false;
        double dist = mc.player.blockPosition().distSqr(target);
        double last = logisticsLastDist;
        logisticsLastDist = dist;
        // 第一次采样只记基准（用户 2026-09-18 实机：「我只是传送回去卸货、站着没动好几秒，
        // 就提示寻路无进展」——旧写法首次采样没有可比的距离，直接算「无进展」，
        // 于是每次卸货只要 3 秒内没挪够就必报一条，纯误报）
        if (last == Double.MAX_VALUE) return false;
        if (last - dist >= LOGISTICS_MIN_APPROACH) {
            logisticsStallCount = 0;
            return false;
        }
        logisticsStallCount++;
        // 再宽限一轮：连续 6 秒无进展才动作（Baritone 绕远路 / 重新算路都要几秒）
        if (logisticsStallCount < 2) return false;
        switch (logisticsStallCount) {
            case 2 -> {
                module.getBaritone().stop();
                repathLogistics(target);
                module.info("§e⚠ " + actionName + "寻路无进展 §8▸ 重新计算路线");
            }
            case 3 -> {
                if (!module.isLogisticsBreakBlocks()) {
                    module.getBaritone().updateSetting("allowBreak", true);
                    module.info("§e⚠ " + actionName + "寻路仍无进展 §8▸ 临时允许破坏挡路方块");
                }
                module.getBaritone().stop();
                repathLogistics(target);
            }
            default -> {
                module.getBaritone().stop();
                module.error("§c✗ 无法走到" + actionName + "容器 §8▸ 自动停止（请检查容器周边地形是否可通行）");
                return true;
            }
        }
        return false;
    }

    /**
     * 智能识别矿物箱位置的潜影盒颜色，返回「§颜色代码 + 中文色名 + 潜影盒」，用于换盒公屏提示。
     * 16 色潜影盒都是独立方块变体，颜色编码在方块 ID 里（如 white_shulker_box / purple_shulker_box）。
     *
     * <p>旧 {@code shulkerColorLabel}，{@code :1023-1051} 逐字。</p>
     */
    private String shulkerColorLabel(BlockPos pos) {
        if (mc.level == null || pos == null) return "§7潜影盒";
        Block block = mc.level.getBlockState(pos).getBlock();
        String id = BuiltInRegistries.BLOCK.getKey(block).getPath();
        if (id.equals("shulker_box")) return "§7默认潜影盒";
        if (!id.endsWith("_shulker_box")) return "§7容器";

        String key = id.substring(0, id.length() - "_shulker_box".length());
        String color = switch (key) {
            case "white" -> "§f白色";
            case "orange" -> "§6橙色";
            case "magenta" -> "§d品红";
            case "light_blue" -> "§b淡蓝";
            case "yellow" -> "§e黄色";
            case "lime" -> "§a黄绿";
            case "pink" -> "§d粉色";
            case "gray" -> "§8灰色";
            case "light_gray" -> "§7淡灰";
            case "cyan" -> "§b青色";
            case "purple" -> "§5紫色";
            case "blue" -> "§9蓝色";
            case "brown" -> "§6棕色";
            case "green" -> "§2绿色";
            case "red" -> "§c红色";
            case "black" -> "§0黑色";
            default -> "§7" + key;
        };
        return color + "潜影盒";
    }

    /** 旧 {@code tickUnloading}，{@code :1053-1191} 逐字（点位来源换成 {@code MiningPointStore}） */
    private void tickUnloading() {
        ServerCommandRunner cmdMgr = module.getCmdManager();
        MiningPoint mineralChest = module.pointStore().get(MiningPointType.MINERAL);

        if (stateTick == 1) {
            unloadingPathIssued = false;
            boxSwapCount = 0;
            noContainerTicks = 0;
            logisticsLastDist = Double.MAX_VALUE;
            logisticsStallCount = 0;
        }

        if (mineralChest == null) {
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 潜影盒打包机换盒等待：关箱后等 2 秒让打包机推盒+放新盒，再重走开箱流程
        if (boxSwapWaiting) {
            boxSwapTicks++;
            if (boxSwapTicks >= BOX_SWAP_WAIT_TICKS) {
                boxSwapWaiting = false;
                boxSwapTicks = 0;
                module.info("§7换盒完成 §8▸ 重新打开 " + shulkerColorLabel(mineralChest.pos()) + " §7继续卸货...");
            } else {
                return; // 继续等打包机换盒
            }
        }

        // 阶段 1：传送到矿物箱（指令已在自检强制填写，此处直接执行）
        if (stateTick == 1) {
            teleportStartPos = mc.player.blockPosition(); // 记录传送起点，用于检测指令是否生效
            executeOwnTeleport(module.getUnloadCommand());
            return;
        }

        // 阶段 2：等待传送完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 2.5：检测传送是否生效（原地没动 = 指令无效，停机而非继续走到箱子）
        if (stateTick > teleportEffectiveTicks() && mc.player.blockPosition().distSqr(teleportStartPos) < 4) {
            module.error("§c✗ 卸货指令无效（未传送）§8▸ 自动停止模块");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 阶段 3：走到箱子相邻的一格（Baritone；寻路中断每 0.5 秒自动重发，不再一断就干等超时）
        if (!isAdjacentTo(mineralChest.pos())) {
            if (!unloadingPathIssued || (!module.getBaritone().isCustomGoalActive() && stateTick % 10 == 0)) {
                unloadingPathIssued = true;
                // 发起/重发 Baritone 走到箱子
                var baritone = module.getBaritone().getBaritoneInstance();
                if (baritone == null) {
                    module.error("§cBaritone 未加载，无法寻路到矿物箱，自动停止");
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
                // 目标点用 GoalNear(2)，不是 GoalGetToBlock：后者的合法终点是「箱子本体及其邻域」，
                // 站不上去时 Baritone 就垫方块爬（用户 2026-09-18：「还是会搭路爬上去卸货」）。
                // 只要走到箱子 2 格内就满足开箱判据（isAdjacentTo 按原版交互距离判），一步都不用搭。
                baritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(mineralChest.pos(), 2));
            }

            // 停滞监测（用户 2026-09-18：「还是想搭路，发现搭不了路卡在原地了…绿框一直闪却到达不了」）：
            // 目标活跃≠有进展，按实际距离逐级脱困（重发 → 临时开破坏 → 停机播报）
            if (logisticsStalled(mineralChest.pos(), "卸货")) {
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                return;
            }

            if (stateTick > PATH_TIMEOUT_TICKS) {
                module.getBaritone().stop();
                module.error("§c✗ 卸货寻路超时 §8▸ 自动停止模块");
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                return;
            }
            return;
        }

        // 阶段 4：停止寻路，面向箱子后开箱（避免背对开箱）
        module.getBaritone().stop();
        faceBlock(mineralChest.pos());

        if (mc.screen != null && !(mc.screen instanceof AbstractContainerScreen<?>)) {
            if (stateTick % 20 == 0) module.getContainer().closeContainer();
            return;
        }
        if (!module.getContainer().isContainerOpen()) {
            // 标点位置无容器（潜影盒被推走后未放新盒）→ 累计计时，超时停机提示
            if (!module.getContainer().isContainerAt(mineralChest.pos())) {
                noContainerTicks++;
                if (noContainerTicks > NO_CONTAINER_TIMEOUT) {
                    module.error("§c✗ 矿物箱位置已无容器 §8▸ 自动停止模块");
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
            } else {
                noContainerTicks = 0;
            }
            module.getContainer().openContainer(mineralChest.pos());
            return;
        }

        // 阶段 5：持续倒货，直到目标矿石全部转移
        module.getContainer().depositOres();

        // 背包目标矿放完 → 关箱走人（放完才 RTP）
        if (!module.getContainer().hasOreInInventory()) {
            module.getContainer().closeContainer();
            transitionTo(MinerState.GO_WILD);
            return;
        }

        // 箱子满但背包还有矿
        if (module.getContainer().isContainerFull()) {
            if (module.isShulkerPackerEnabled()) {
                // 打包机模式：关箱等打包机换盒，再重开新盒继续放
                boxSwapCount++;
                if (boxSwapCount > MAX_BOX_SWAPS) {
                    module.error("§c✗ 潜影盒换盒超过 " + MAX_BOX_SWAPS + " 次仍未放完 §8▸ 自动停止");
                    module.getContainer().closeContainer();
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
                module.getContainer().closeContainer();
                boxSwapWaiting = true;
                boxSwapTicks = 0;
                module.info("§e⚠ " + shulkerColorLabel(mineralChest.pos()) + " §e已满 §8▸ 等打包机换盒后重开继续放");
                return;
            }
            // 普通箱子模式：箱子满了放不下 → 停止模块并提示，避免空塞后带矿 RTP 跑掉
            module.getContainer().closeContainer();
            module.error("§c✗ 矿物容器已满 §8▸ 无法继续卸货，自动停止模块");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 超时保护（非打包机模式兜底）
        if (!module.isShulkerPackerEnabled() && stateTick > 400) {
            module.getContainer().closeContainer();
            transitionTo(MinerState.GO_WILD);
        }
    }

    /** 旧 {@code tickSupply}，{@code :1193-1283} 逐字（点位来源换成 {@code MiningPointStore}） */
    private void tickSupply() {
        ServerCommandRunner cmdMgr = module.getCmdManager();
        MiningPoint foodChest = module.pointStore().get(MiningPointType.FOOD);
        if (stateTick == 1) {
            supplyPathIssued = false;
            // 记录取食物前的数量：收摊时用「增量」判成功，避免「刚点完还没到账」被当成箱子没食物
            supplyFoodBefore = countFoodStacks();
            supplySettleTicks = 0;
            logisticsLastDist = Double.MAX_VALUE;
            logisticsStallCount = 0;
        }

        if (foodChest == null) {
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 阶段 1：传送到补给点（指令已在自检强制填写，此处直接执行）
        if (stateTick == 1) {
            teleportStartPos = mc.player.blockPosition(); // 记录传送起点，用于检测指令是否生效
            executeOwnTeleport(module.getSupplyCommand());
            return;
        }

        // 阶段 2：等待传送完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 2.5：检测传送是否生效（原地没动 = 指令无效，停机）
        if (stateTick > teleportEffectiveTicks() && mc.player.blockPosition().distSqr(teleportStartPos) < 4) {
            module.error("§c✗ 补给指令无效（未传送）§8▸ 自动停止模块");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 阶段 3：走到箱子相邻的一格（Baritone；寻路中断每 0.5 秒自动重发）
        if (!isAdjacentTo(foodChest.pos())) {
            if (!supplyPathIssued || (!module.getBaritone().isCustomGoalActive() && stateTick % 10 == 0)) {
                supplyPathIssued = true;
                var baritone = module.getBaritone().getBaritoneInstance();
                if (baritone == null) {
                    module.error("§cBaritone 未加载，无法寻路到食物箱，自动停止");
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
                baritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(foodChest.pos(), 2));
            }

            // 停滞监测（与卸货同一套：目标活跃≠有进展，按实际距离逐级脱困）
            if (logisticsStalled(foodChest.pos(), "补给")) {
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                return;
            }

            if (stateTick > 1200) {
                module.getBaritone().stop();
                module.error("§c✗ 补给寻路超时 §8▸ 自动停止模块");
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            }
            return;
        }

        // 阶段 4：停止寻路，面向箱子后开箱取食物
        module.getBaritone().stop();
        faceBlock(foodChest.pos());

        if (!module.getContainer().isContainerOpen()) {
            // 屏幕被其它界面占用时定期强制关闭，避免永远打不开箱子
            if (mc.screen instanceof AbstractContainerScreen<?>) return;
            if (mc.screen != null && stateTick % 20 == 0) module.getContainer().closeContainer();
            module.getContainer().openContainer(foodChest.pos());
            return;
        }

        // 阶段 5：取食物 —— 每个白名单食物补到「一组」（上限取物品自己的最大堆叠数），
        // 取完再等 SUPPLY_SETTLE_TICKS 刻结算帧，确认最后一格 Shift 点击真的到账才收摊。
        // （用户 2026-09-17 修复：旧实现「点击刚发出、物品还在服务器飞行」时就判定取完，
        //   状态机当刻读到的还是旧数量 → 被判「补给箱无白名单食物」直接回矿区 = 根本没拿到）
        MiningContainer.FoodWithdrawResult result = module.getContainer().withdrawFood();
        boolean timedOut = stateTick > SUPPLY_TIMEOUT_TICKS;
        if (result == MiningContainer.FoodWithdrawResult.WORKING) {
            supplySettleTicks = 0;
            if (!timedOut) return;
        } else if (result == MiningContainer.FoodWithdrawResult.DONE) {
            if (++supplySettleTicks < SUPPLY_SETTLE_TICKS && !timedOut) return;
        }

        module.getContainer().closeContainer();
        int foodNow = countFoodStacks();
        int gained = foodNow - supplyFoodBefore;
        boolean enough = foodNow >= module.getHungerThreshold();

        // 拿到了东西（哪怕没到阈值）就算这次补给成功：目标本身是「每个白名单食物补一组」，
        // 箱子不够时能拿多少拿多少，不能因为没凑够阈值就当成失败回去重来
        if (gained > 0 || enough) {
            supplyFailCount = 0;
            module.info("§a✓ 食物已补充 §8▸ +" + Math.max(0, gained) + " 个 §7(共 " + foodNow + "/"
                + module.getHungerThreshold() + ") §8返回矿区");
            transitionTo(MinerState.GO_WILD);
            return;
        }

        // 点了却拿不进来（背包满 / 服务器拒绝）：单独报出来，别混在「补给箱没食物」里
        if (result == MiningContainer.FoodWithdrawResult.INVENTORY_BLOCKED) {
            if (supplyFailCount++ >= 1) {
                module.error("§c✗ 食物箱连续 2 次取不进来 §8▸ 自动停止（请清理背包或补充食物箱）");
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            } else {
                module.warning("§e⚠ 食物取不进来 §8▸ 背包可能已满，先回去挖矿，饥饿时再来");
                transitionTo(MinerState.GO_WILD);
            }
            return;
        }

        // 箱子里确实没有白名单食物（拿空即止）
        if (supplyFailCount++ >= 1) {
            // 连续 2 次补给空手：箱子没白名单食物，再循环也只是空转 RTP，停机让玩家补货
            module.error("§c✗ 补给箱连续 2 次无白名单食物 §8▸ 自动停止（请补充食物箱）");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
        } else {
            module.warning("§e⚠ 补给箱内无白名单食物 §8▸ 返回矿区继续挖（饥饿时仍会再试一次）");
            transitionTo(MinerState.GO_WILD);
        }
    }

    /** 旧 {@code tickEating}，{@code :1285-1323} 逐字 */
    private void tickEating() {
        FoodData foodData = mc.player.getFoodData();

        // 检查饱食度是否回满（满值20）
        if (foodData.getFoodLevel() >= 20) {
            mc.options.keyUse.setDown(false); // 释放右键
            module.info("§a✓ 饱食度已恢复 §8▸ 继续挖矿");
            module.getSoundNotifier().notifyMiningStart();
            transitionTo(MinerState.MINING);
            return;
        }

        // 食物耗尽（拿到手上的最后一块也吃完了）：别傻等 2 分钟超时，直接去补给
        if (!hasFoodToEat()) {
            mc.options.keyUse.setDown(false);
            module.info("§6⚠ 食物已吃完 §8▸ 前往补给点");
            transitionTo(MinerState.SUPPLY);
            return;
        }

        // 每 tick 都尝试进食：autoEat 内部幂等，未在进食时触发一次 useItem，
        // 已在使用中则仅保持按键。窗口失焦/开 GUI 时按键会被吞，靠 useItem 兜底。
        module.getContainer().autoEat();

        // 每 2 秒播报一次进食进度（避免刷屏）
        if (stateTick % 40 == 0) {
            // 播报要吃的食物名，而不是主手物品名：食物还没换进快捷栏时主手还是镐子，
            // 旧写法会打出「进食中 ▸ 下界合金镐」（用户 2026-09-17）
            module.info("§e进食中 §8▸ " + module.getContainer().describeEatingFood()
                + " §7(饱食度: " + foodData.getFoodLevel() + "/20)");
        }

        // 超时保护：2分钟还没吃饱就放弃，回到挖矿
        if (stateTick > 2400) {
            mc.options.keyUse.setDown(false);
            module.warning("§c⚠ 进食超时 §8▸ 放弃等待");
            module.getSoundNotifier().notifyLowFood();
            transitionTo(MinerState.MINING);
        }
    }

    /** 旧 {@code tickRepair}，{@code :1325-1446} 逐字（自动换槽换成 26.1.2 原语） */
    private void tickRepair() {
        MiningPoint afkPoint = module.pointStore().get(MiningPointType.AFK);

        if (afkPoint == null) {
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        ServerCommandRunner cmdMgr = module.getCmdManager();

        // 阶段 1：传送到挂机点（指令已在自检强制填写，此处直接执行）
        if (stateTick == 1) {
            teleportStartPos = mc.player.blockPosition(); // 记录传送起点，用于检测指令是否生效
            executeOwnTeleport(module.getAFKCommand());
            return;
        }

        // 阶段 2：等待传送完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 2.5：检测传送是否生效（原地没动 = 指令无效，停机）
        if (stateTick > teleportEffectiveTicks() && mc.player.blockPosition().distSqr(teleportStartPos) < 4) {
            module.error("§c✗ 挂机修补指令无效（未传送）§8▸ 自动停止模块");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 阶段 3：走到挂机点（Baritone；寻路中断每 0.5 秒自动重发）
        if (!mc.player.blockPosition().closerThan(afkPoint.pos(), 3.0)) {
            if (!repairPathIssued || (!module.getBaritone().isCustomGoalActive() && stateTick % 10 == 0)) {
                repairPathIssued = true;
                var baritone = module.getBaritone().getBaritoneInstance();
                if (baritone == null) {
                    module.error("§cBaritone 未加载，无法寻路到挂机点，自动停止");
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                    return;
                }
                baritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(afkPoint.pos(), 2));
            }

            if (stateTick > 1200) {
                module.getBaritone().stop();
                if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            }
            return;
        }

        // 阶段 4：停止寻路，调整视角到记录的 Yaw/Pitch
        module.getBaritone().stop();

        if (stateTick < 100 && !isViewAligned(afkPoint.yaw(), afkPoint.pitch())) {
            smoothRotateTo(afkPoint.yaw(), afkPoint.pitch());
            return;
        }

        // 阶段 5：执行 Auto-Swap（只做一次）
        if (!repairMode) {
            if (repairSwapRequestedTick == -1) {
                int toolSlot = findDamagedToolSlot();
                if (toolSlot == -1) {
                    // 没有需要修的工具了（可能已被其它机制修好），直接返回矿区
                    transitionTo(MinerState.GO_WILD);
                    return;
                }
                savedToolSlot = toolSlot;
                savedTool = toolSlot == -2 ? mc.player.getOffhandItem().copy()
                    : mc.player.getInventory().getItem(toolSlot).copy();
                savedWeaponSlot = findWeaponSlotInHotbar();
                repairSwapAttempts++;
                repairSwapRequestedTick = stateTick;
                if (toolSlot >= 0) swapWithOffhand(toolSlot);
                if (toolSlot == -2) {
                    repairMode = true;
                    startRepairCombat();
                }
                return;
            }

            ItemStack offhandTool = mc.player.getOffhandItem();
            boolean toolMoved = ItemStack.isSameItemSameComponents(savedTool, offhandTool);
            if (toolMoved) {
                if (savedWeaponSlot >= 0) {
                    selectHotbar(savedWeaponSlot);
                }
                repairMode = true;
                startRepairCombat();
                return;
            }

            // 工具还没到副手：最多等 15 tick（服务端到账通常 1~2 tick），超时才重发，
            // 避免像旧逻辑那样每 2 tick 就重发一次、把正在移动的光标打乱反而更慢。
            if (stateTick - repairSwapRequestedTick > 15) {
                repairSwapRequestedTick = -1;
                if (repairSwapAttempts >= 3) {
                    if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
                }
            }
            return;
        }

        // 阶段 6：持续监控耐久
        ItemStack currentTool = mc.player.getOffhandItem();
        if (currentTool.isEmpty() || isFullyRepaired(currentTool)) {
            transitionTo(MinerState.GO_WILD);
            return;
        }

        // 超时保护：10分钟没修满
        if (stateTick > 12000) {
            stopRepairCombat();
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
        }
    }

    /** 旧 {@code isViewAligned}，{@code :1448-1461} 逐字（旧文件里第 1457/1458 行是同一句重复，此处只写一次） */
    private boolean isViewAligned(float targetYaw, float targetPitch) {
        if (mc.player == null) return false;

        float currentYaw = mc.player.getYRot();
        float currentPitch = mc.player.getXRot();

        float deltaYaw = Math.abs(targetYaw - currentYaw);
        float deltaPitch = Math.abs(targetPitch - currentPitch);

        if (deltaYaw > 180) deltaYaw = 360 - deltaYaw;

        return deltaYaw < 5.0f && deltaPitch < 5.0f;
    }

    /**
     * 旧 {@code tickDeathHandling}，{@code :1463-1485}。
     *
     * <p>差异（用户 2026-09-16 拍板）：旧 {@code tryMeteorAutoRespawn()}（切换第三方框架的自动重生模块）
     * 整段删除，自动重生由本项目 {@code feature/respawn/AutoRespawnModule}（默认开启）承担；
     * 因此旧 {@code :1472} 的「已调用流星自动重生模块」改成本项目的「已调用自动重生模块」，其余逐字不变。</p>
     */
    private void tickDeathHandling() {
        if (mc.player == null) return;

        // 阶段 1：首次进入时播报死亡并记录死亡点环境
        if (stateTick == 1) {
            module.getSoundNotifier().notifyDeath();
            // 死亡后玩家位置还停在死亡点，检测脚下是否有岩浆（在岩浆湖里死亡）
            diedInLava = hasLavaNear(1);
            module.error("§c✗ 已调用自动重生模块");
        }

        // 阶段 2：等待复活
        if (mc.player.isDeadOrDying()) {
            if (stateTick % 20 == 0) {
                mc.player.respawn(); // 后备方案
            }
            return;
        }

        // 阶段 3：复活完成，进入等待
        transitionTo(MinerState.RESPAWN_WAIT);
    }

    /** 旧 {@code tickRespawnWait}，{@code :1487-1514} 逐字 */
    private void tickRespawnWait() {
        ServerCommandRunner cmdMgr = module.getCmdManager();

        // 等待复活完成（玩家不再是死亡状态）
        if (mc.player.isDeadOrDying()) {
            return;
        }

        // 阶段 1：执行死亡重返指令
        if (stateTick == 1) {
            // 死亡点在岩浆里：不执行 back（会再回岩浆湖），直接去野外安全点
            if (diedInLava) {
                module.info("§e⚠ 死亡点有岩浆 §8▸ 跳过重返指令，直接前往野外");
                transitionTo(MinerState.GO_WILD);
                return;
            }
            executeOwnTeleport(module.getRespawnCommand());
            return;
        }

        // 阶段 2：等待传送完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 3：返回野外恢复挖矿
        transitionTo(MinerState.GO_WILD);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  辅助方法
    // ═══════════════════════════════════════════════════════════════════

    /** 旧 {@code needsRepair}，{@code :1520-1530} 逐字 */
    private boolean needsRepair(ItemStack tool) {
        if (tool.isEmpty()) return false;
        Integer maxDamage = tool.get(DataComponents.MAX_DAMAGE);
        Integer damage = tool.get(DataComponents.DAMAGE);
        if (maxDamage == null || damage == null) return false;
        int remaining = maxDamage - damage;
        // 阈值不能超过工具最大耐久：否则满耐久（remaining == maxDamage）仍被判为
        // 「需修复」，修完回矿区又立刻触发修复，形成修复↔挖矿死循环。
        int effectiveThreshold = Math.min(module.getDurabilityThreshold(), maxDamage);
        return remaining < effectiveThreshold;
    }

    /** 旧 {@code isFullyRepaired}，{@code :1532-1536} 逐字 */
    private boolean isFullyRepaired(ItemStack tool) {
        if (tool.isEmpty()) return true;
        Integer damage = tool.get(DataComponents.DAMAGE);
        return damage == null || damage <= 5; // 接近满耐久
    }

    /** 旧 {@code findMiningPickaxe}，{@code :1538-1545} 逐字 */
    private ItemStack findMiningPickaxe() {
        ItemStack mainHand = mc.player.getMainHandItem();
        if (isPickaxe(mainHand)) return mainHand;
        ItemStack offhand = mc.player.getOffhandItem();
        if (isPickaxe(offhand)) return offhand;
        int slot = findMiningPickaxeSlot();
        return slot < 0 ? ItemStack.EMPTY : mc.player.getInventory().getItem(slot);
    }

    /** 旧 {@code findMiningPickaxeSlot}，{@code :1547-1554} 逐字 */
    private int findMiningPickaxeSlot() {
        ItemStack offhand = mc.player.getOffhandItem();
        if (isPickaxe(offhand)) return -2;
        for (int i = 0; i < 36; i++) {
            if (isPickaxe(mc.player.getInventory().getItem(i))) return i;
        }
        return -1;
    }

    /** 旧 {@code isPickaxe}，{@code :1556-1558} 逐字 */
    private boolean isPickaxe(ItemStack stack) {
        return !stack.isEmpty() && BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().endsWith("_pickaxe");
    }

    /** 旧 {@code isRepairableTool}，{@code :1560-1567} 逐字 */
    private boolean isRepairableTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (stack.get(DataComponents.MAX_DAMAGE) == null) return false;
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return id.endsWith("_pickaxe") || id.endsWith("_shovel") || id.endsWith("_axe")
            || id.endsWith("_hoe") || id.endsWith("_sword");
    }

    /** 旧 {@code hasMending}，{@code :1569-1581} 逐字（附魔 holder 从世界注册表取） */
    private boolean hasMending(ItemStack stack) {
        if (stack.isEmpty() || mc.level == null) return false;
        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null || enchantments.isEmpty()) return false;
        try {
            var lookup = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            var holder = lookup.get(Enchantments.MENDING).orElse(null);
            return holder != null && enchantments.getLevel(holder) > 0;
        } catch (Exception ignored) {
            return false;
        }
    }

    /** 旧 {@code findDamagedToolSlot}，{@code :1583-1606} 逐字（副手 -2 优先） */
    private int findDamagedToolSlot() {
        if (mc.player == null) return -1;

        // 副手工具优先（已就位，直接修）
        if (isRepairableTool(mc.player.getOffhandItem()) && needsRepair(mc.player.getOffhandItem())) {
            return -2;
        }

        int bestSlot = -1;
        int lowestRemaining = Integer.MAX_VALUE;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!isRepairableTool(stack) || !needsRepair(stack)) continue;
            Integer maxDamage = stack.get(DataComponents.MAX_DAMAGE);
            Integer damage = stack.get(DataComponents.DAMAGE);
            int remaining = maxDamage - damage;
            if (remaining < lowestRemaining) {
                lowestRemaining = remaining;
                bestSlot = i;
            }
        }
        return bestSlot;
    }

    /** 旧 {@code findDamagedTool}，{@code :1608-1614} 逐字 */
    private ItemStack findDamagedTool() {
        int slot = findDamagedToolSlot();
        if (slot == -2) return mc.player.getOffhandItem();
        if (slot >= 0) return mc.player.getInventory().getItem(slot);
        return ItemStack.EMPTY;
    }

    /**
     * 统计背包中的食物数量（只统计食物白名单内的物品合计，不判 FOOD 组件）。
     *
     * <p>旧 {@code countFoodStacks}，{@code :1619-1635}；白名单在本项目存登记 ID 字符串，
     * 判据由 {@code whitelist.contains(stack.getItem())} 等值换成 ID 比较，语义不变。</p>
     */
    private int countFoodStacks() {
        if (mc.player == null) return 0;

        List<String> whitelist = module.getFoodWhitelist();
        int count = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;

            // 只统计白名单内的食物
            if (whitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) {
                count += stack.getCount(); // 统计实际数量
            }
        }
        return count;
    }

    /**
     * 背包里是否还有白名单内的可吃食物（有 FOOD 组件才算）。
     *
     * <p>旧 {@code hasFoodToEat}，{@code :1640-1650}。</p>
     */
    private boolean hasFoodToEat() {
        if (mc.player == null) return false;
        List<String> whitelist = module.getFoodWhitelist();
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty()
                && whitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())
                && stack.has(DataComponents.FOOD)) {
                return true;
            }
        }
        return false;
    }

    /** 旧 {@code countOreStacks}，{@code :1652-1672} 逐字（返回组数 = 总数 / 64） */
    private int countOreStacks() {
        if (mc.player == null) return 0;

        // 精准采集按原矿方块（含深层变种）计数；时运按掉落物计数（下界残骸掉落物=自身方块，两模式共用）
        Set<String> acceptIds = module.isSilkTouchMode()
            ? module.getTargetBlockIds()
            : Set.of(module.getTargetDropItemId());

        int totalCount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;

            String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
            if (acceptIds.contains(itemId)) {
                totalCount += stack.getCount(); // 统计目标矿物数量
            }
        }
        // 转换为完整组数
        return totalCount / 64;
    }

    /** 旧 {@code findWeaponInHotbar}，{@code :1674-1684} 逐字（返回副本） */
    private ItemStack findWeaponInHotbar() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;
            String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
            if (id.endsWith("_sword")) {
                return stack.copy();
            }
        }
        return ItemStack.EMPTY;
    }

    /** 旧 {@code findWeaponSlotInHotbar}，{@code :1686-1695} 逐字 */
    private int findWeaponSlotInHotbar() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty() && BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().endsWith("_sword")) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 旧 {@code broadcastStateTransition}，{@code :1707-1728}（DEATH_HANDLING 一条按批准改点换名）。
     *
     * <p><b>「开始挖矿」「开始卸货」两条改报「采集模式 + 目标矿名」</b>（用户 2026-09-18 两次指正：
     * 「改成目前选择的矿石 模式」→「改成 当前选择的模式跟矿石的名字 粗铁 铁矿石」）：
     * 数量那一套在这里给不出信息——进矿那一刻矿石必然是 0/20 组，而卸货本身就只有「矿石满了」一个原因，
     * 玩家真正要确认的是<b>这次挖的是什么、什么模式</b>。矿名取 {@code getTargetDisplayName()}：
     * 时运模式是掉落物名（粗铁），精准模式是原矿方块名（铁矿石），与设置页选择器、启动报告同一处口径。</p>
     *
     * <p>其余状态（补给 / 进食 / 修补 / 死亡等）文案未动。</p>
     */
    private void broadcastStateTransition(MinerState from, MinerState to) {
        if (from == to) return;

        int foodCount = countFoodStacks();
        int foodThreshold = module.getHungerThreshold();

        // 目标矿名 + 采集模式（枚举显示名即「精准采集」/「时运」，与设置页逐字一致）
        String target = module.getTargetDisplayName();
        String lootMode = String.valueOf(module.settings().lootMode);

        String message = switch (to) {
            case IDLE -> "§7待机中";
            case GO_WILD -> "§a✓ 前往野外";
            case MINING -> String.format("§a✓ 开始挖矿 §8▸ §f%s §8· §f%s", lootMode, target);
            case UNLOADING -> String.format("§b开始卸货 §8▸ §f%s §8· §f%s", lootMode, target);
            case SUPPLY -> String.format("§6⚠ 前往补给 §8▸ 食物不足 %d/%d 个", foodCount, foodThreshold);
            case EATING -> "§d补充饥饿值";
            case REPAIR -> "§c⚠ " + toolName(findDamagedTool()) + "耐久过低 §8▸ 联动杀戮光环修复中";
            case DEATH_HANDLING -> "§c✗ 检测到死亡 §8▸ 已调用自动重生";
            case RESPAWN_WAIT -> "§6复活完成 §8▸ 返回挂机点";
            // 战斗态的播报由 MiningCombat 带怪物名发出（「发现 xx → 主动出击」/「苦力怕引信点燃 → 后撤熄灭」），
            // 这里返回空串不重复播报
            case COMBAT -> "";
        };

        if (!message.isEmpty()) module.info(message);
    }

    /** 旧 {@code smoothRotateTo}，{@code :1730-1748} 逐字（±180 归一 + 0.3f 插值） */
    private void smoothRotateTo(float targetYaw, float targetPitch) {
        if (mc.player == null) return;
        float currentYaw = mc.player.getYRot();
        float currentPitch = mc.player.getXRot();

        float deltaYaw = targetYaw - currentYaw;
        float deltaPitch = targetPitch - currentPitch;

        // 归一化角度到 [-180, 180]
        while (deltaYaw > 180) deltaYaw -= 360;
        while (deltaYaw < -180) deltaYaw += 360;

        // 平滑插值
        float smoothYaw = currentYaw + deltaYaw * 0.3f;
        float smoothPitch = currentPitch + deltaPitch * 0.3f;

        mc.player.setYRot(smoothYaw);
        mc.player.setXRot(smoothPitch);
    }

    /** 旧 {@code faceBlock}，{@code :1750-1761} 逐字（卸货/补给开箱前面向箱子） */
    private void faceBlock(BlockPos pos) {
        if (mc.player == null) return;
        Vec3 eye = mc.player.getEyePosition();
        Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        double dx = center.x - eye.x;
        double dy = center.y - eye.y;
        double dz = center.z - eye.z;
        double horiz = Math.sqrt(dx * dx + dz * dz);
        mc.player.setYRot((float) Math.toDegrees(Math.atan2(-dx, dz)));
        mc.player.setXRot((float) Math.toDegrees(-Math.atan2(dy, horiz)));
    }

    /**
     * 开启修补联动战斗（旧 {@code startKillAura}，{@code :1763-1770}）。
     *
     * <p>旧实现的「未开启才开启」判定（{@code killAura != null && !wasActive}）由注入的真实现承担
     * （批次 4 的 KillAura 模块），状态机侧只保留 {@code killAuraWasOnBefore} 标记：
     * 只要调用过 start，退出修补时就走 stop。</p>
     */
    private void startRepairCombat() {
        repairCombat.start();
        killAuraWasOnBefore = true; // 标记为「我们开的」，退出修补时才能关
    }

    /**
     * 关闭修补联动战斗（旧 {@code stopKillAura}，{@code :1772-1779}）。
     *
     * <p>只关我们自己开启的那一个：用户进入模块前就开着的杀戮光环保持原样（避免状态污染）。</p>
     */
    private void stopRepairCombat() {
        if (killAuraWasOnBefore) {
            repairCombat.stop();
        }
        killAuraWasOnBefore = false;
    }

    /**
     * 把修好的工具从副手放回原槽位（旧 {@code restoreHotbar}，{@code :1795-1815}）。
     *
     * <p>旧实现固定放回 0 号槽，若 0 号槽已被武器占据，会把武器顶进副手
     * （bug：修完镐子副手变成别的东西），旧注释 {@code :1798-1799} 记录了这一点，本移植保持「放回原槽」。</p>
     */
    private void restoreHotbar() {
        if (mc.player == null) return;

        // 把修好的工具从副手放回原槽位。
        if (!mc.player.getOffhandItem().isEmpty()) {
            if (savedToolSlot >= 0) {
                swapWithOffhand(savedToolSlot);
            } else if (savedToolSlot == -2) {
                // 工具原本就在副手，无需移动
            } else {
                swapWithOffhand(0);
            }
        }

        repairMode = false;
        savedTool = ItemStack.EMPTY;
        savedWeapon = ItemStack.EMPTY;
        savedToolSlot = -1;
        savedWeaponSlot = -1;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  物品栏原语（旧项目走 Meteor InvUtils；本项目换 26.1.2 原语）
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 切到指定快捷栏槽位（旧 {@code InvUtils.swap(slot, false)}）。
     *
     * <p>{@code setSelectedSlot} 只改本地选择槽，必须同时发一次携带物同步包，
     * 否则服务端仍按旧手持物处理右键（与本项目 {@code DefaultStardewAdapter.selectHotbar} 同一做法）。</p>
     */
    private void selectHotbar(int slot) {
        if (mc.player == null || slot < 0 || slot > 8) return;
        mc.player.getInventory().setSelectedSlot(slot);
        if (mc.getConnection() != null) {
            mc.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
        }
    }

    /**
     * 与副手交换物品（旧 {@code InvUtils.move().from(invSlot).toOffhand()}
     * 与 {@code InvUtils.move().fromOffhand().to(invSlot)}，两个方向同一次交换）。
     *
     * <p>走 {@code ContainerInput.SWAP}，button = 40 即「与被点击槽位交换副手物品」；
     * 快捷栏 0~8 在 {@code InventoryMenu} 里对应槽位 36~44，主背包 9~35 与 {@code Inventory}
     * 下标同值，因此只有快捷栏需要加 36。与本项目 {@code DefaultStardewAdapter} 同一口径。</p>
     */
    private void swapWithOffhand(int invSlot) {
        if (mc.player == null || mc.gameMode == null) return;
        if (invSlot < 0 || invSlot >= 36) return;
        int menuSlot = invSlot < 9 ? 36 + invSlot : invSlot;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, menuSlot,
            OFFHAND_INV_INDEX, ContainerInput.SWAP, mc.player);
    }
}
