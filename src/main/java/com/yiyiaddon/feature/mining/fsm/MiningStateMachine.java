package com.yiyiaddon.feature.mining.fsm;

import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.pathing.goals.GoalNear;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.fastbreak.MiningFastBreakController;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.navigation.BlockPlacer;
import com.yiyiaddon.feature.mining.service.MiningContainer;
import com.yiyiaddon.feature.mining.service.MiningPersonalSell;
import com.yiyiaddon.feature.mining.service.ServerCommandRunner;
import com.yiyiaddon.feature.mining.service.ToolDurability;
import com.yiyiaddon.feature.mining.service.WardenWarningGuard;
import com.yiyiaddon.platform.identity.ItemIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 *   <li><b>死亡自动重生的归属</b>（用户 2026-09-16 拍板）：旧项目里切换第三方框架自动重生模块的调用
 *       （{@code :1781-1793}）整段删除。本项目有自研
 *       {@code feature/respawn/AutoRespawnModule}（默认开启）承担这件事，状态机不再切换任何模块，
 *       只保留 {@code mc.player.respawn()} 兜底（旧 {@code :1477-1479}）。随之两条死亡播报按最小改动
 *       换成本项目模块名（其余一字不改）：旧 {@code :1472} 的死亡播报模块名
 *       → {@code "§c✗ 已调用自动重生模块"}；旧 {@code :1723} 的死亡播报模块名
 *       → {@code "§c✗ 检测到死亡 §8▸ 已调用自动重生"}。</li>
 *   <li><b>KillAura 修补联动</b>：旧 {@code startKillAura / stopKillAura}（{@code :1763-1779}）
 *       改为接口 seam {@link RepairCombatHook}（实现由批次 4 的 KillAura 模块注入），
 *       状态机侧调用点与 {@code killAuraWasOnBefore} 卫语句语义不变。</li>
 *   <li><b>外部依赖</b>：旧框架 {@code InvUtils} 换成 26.1.2 原语——选槽走
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
 *   <li><b>掉落物捡取</b>：见 {@link #tryPickupNearbyOre()} 注释 —— 只捡存在满
 *       {@code PICKUP_MIN_AGE_TICKS} 刻的漏捡物，{@code PICKUP_RADIUS} 格内来一趟就捡完
 *       （捡到一个接着找下一个），一轮收工后才冷却 {@code PICKUP_COOLDOWN_TICKS} 刻；
 *       够不到（寻路进程 1 秒起不来）判不可达并进黑名单；脚边 {@code PICKUP_IGNORE_RADIUS_SQR}
 *       内的不算漏捡（自然吸拾），认领扫描每 5 刻一次。</li>
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
 *   <li><b>岩浆处理改口径</b>（用户 2026-09-18：「碰到岩浆就进入状态机 现在改成封边直接绕过去就好
 *       了」）：原先「附近 {@code lavaAvoidRadius} 格内有岩浆就撤离」的触发已废——那套触发的实际表现
 *       是来回跑（撤离→冷却→mine 把路径又规划回岩浆边→再撤离），挖矿被打断成碎片。现在：
 *       <b>撤离只兜「已经出事」</b>（人泡在岩浆里 / 身上着火），完成判据也收到 1 格（脱离接触即可）；
 *       <b>挡路的岩浆用垫脚方块铺过去</b>——脚底 / 脚层 / 头顶三层里、<b>手长范围内</b>的岩浆格直接填实
 *       （{@link #tickLavaBridging}，填的是岩浆自己那一格，含嵌在墙体里的岩浆源）。只在玩家
 *       已经走到的位置<b>顺手封</b>（一轮最多 {@code BRIDGE_MAX_PLACES_PER_ROUND} 块、两轮之间歇
 *       {@code BRIDGE_ROUND_COOLDOWN_TICKS} 刻），不专门寻路过去封。挖开贴岩浆矿物后的
 *       流入口封堵在 {@code MiningVeinMiner}（共用 {@code BlockPlacer} 放置链路）。</li>
 *   <li><b>离开挖矿态时收掉秒破会话</b>：{@code onStateExit(MINING)} 里先
 *       {@code MiningFastBreakController#release}（发 ABORT 清服务端槽位 + 清裂纹）再停 Baritone，
 *       否则残留的 START 槽位会顶掉下一个方块的破坏进度（表现为「下一块怎么挖都不烂」）。</li>
 *   <li><b>手动传送检测</b>（用户 2026-09-17：「挖矿中敲 /home 回家，模块还在继续挖」）：见
 *       {@link #manualTeleportDetected()} —— MINING 态下位置单刻跳变 &gt;32 格即判定服务器传送类指令，
 *       立刻停机播报（用户裁定：停机最安全，绝不在家里/别人基地继续挖）。</li>
 *   <li><b>GO_WILD 的「传送是否生效」改判单刻瞬移</b>（用户 2026-09-19 实测）：旧 {@code tickGoWild}
 *       两处都用「离起点超过 2 格即算传送生效」。而本服 RTP 先提示「請勿移動」、3 秒后才随机传送 ——
 *       玩家在这几秒里自己走动会让服务器取消传送，可位置照样变了 &gt;2 格，于是被判成「传送已生效」，
 *       人还在原地就进 MINING 让 Baritone 就地找矿挖。现改为看 {@link #teleportJumpThisTick}
 *       （单刻位移 ≥ {@link #TELEPORT_JUMP_DISTANCE_SQR} 8 格）：只有服务器真把人挪走才成立，
 *       走动再也构不成「传送成功」。<b>三个指定点传送状态（卸货/补给/修补）本轮未改</b>，
 *       仍用「超过等待时长还在起点 2 格内 = 指令无效」的原判据。</li>
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
 *   <li><b>自动回血 + 发包连吃</b>（用户 2026-09-19：「自动挖矿加一个发包功能……自动回血」，
 *       不占设置项）：{@code tickMining} 新增优先级 2.6 —— 血量未满且饱食度未满（原版普通食物
 *       只在饿的时候吃得下）就停 Baritone 进 {@link MinerState#EATING}；进食本体由
 *       {@code MiningContainer#packetEat()} 发包驱动（起手走原版 {@code gameMode.useItem}、补发直发
 *       {@code ServerboundUseItemPacket}；起手后立刻本地取消用食状态，所以没有吃东西的动画），
 *       吃完一件立刻接下一件（零间隔）。
 *       <b>吃什么严格按食物白名单</b>——当天曾放宽成「任何带 {@code FOOD} 组件的物品」，
 *       结果怪物掉的腐肉被吃掉（用户实机反馈），现按用户裁定收口：吃（{@link #hasFoodToEat()} /
 *       {@code MiningContainer#isEdible}）与留（{@code MiningContainer#shouldKeep}）统一走
 *       {@code foodWhitelist}，与补给取货、{@link #countFoodStacks()} 同源。
 *       单件食物的用食时长由服务端结算（原版 32 刻），发包改不了它；连吃与「不等按键」
 *       才是这个功能省下的时间。</li>
 * </ol>
 */
public final class MiningStateMachine {

    /** 挖矿诊断日志：只写日志文件（yiyiaddon/mining），不进聊天 */
    private static final Logger MINING_LOG = LoggerFactory.getLogger("yiyiaddon/mining");

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

    /** 这次进进食态是因为饿了（饱食度低于阈值） */
    private static final int EAT_REASON_HUNGER = 1;
    /** 这次进进食态是因为掉血（自动回血） */
    private static final int EAT_REASON_HEAL = 2;
    /**
     * 本次进食的原因，只用于区分播报文案（用户 2026-09-19：「能不能分清楚没血吃跟饿了吃的文案啊」）。
     * 由触发进食的那两个优先级分支在 {@code transitionTo(EATING)} 之前设置。
     */
    private int eatReason = EAT_REASON_HUNGER;

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
     * 是否正在「岩浆垫脚铺设」。
     *
     * <p>连锁挖矿必须一并让位（{@code AutoMinerModule} 把它与 {@code isWaterBreaking} 并列传进
     * {@code veinMiner.tick} 的 active）：铺设与连锁都写主手 —— 连锁的秒破每刻把主手换成镐、
     * 垫脚每块把主手换成搭路方块，两边交替覆盖的结果是「方块放不下去 / 墙挖不动」。
     * 垫脚期间 Baritone 也被停掉了，连锁的扫脉与派发本就没有意义。</p>
     */
    public boolean isLavaBridging() {
        return lavaBridging;
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
    /** 修复态超时｜10 分钟（20 tick/秒 × 600 秒）没修满就停机并播报，见 {@link #tickRepair} */
    private static final int REPAIR_TIMEOUT_TICKS = 12000;
    /**
     * 可用镐的剩余耐久下限：没有经验修补的镐，剩余必须 <b>大于</b> 这个值才算「有镐子」。
     *
     * <p>用户 2026-09-21：「剩下1点耐久停下来 不要把镐子挖爆」—— 剩 1 点再挖一块就爆，
     * 等于当场失去挖矿能力，宁可提前停机。带经验修补的镐不受这条约束（挖矿得经验会自己修回来），
     * 见 {@link #isUsablePickaxe(ItemStack)}。</p>
     */
    private static final int USABLE_DURABILITY_FLOOR = 1;
    private ItemStack savedTool = ItemStack.EMPTY;
    private ItemStack savedWeapon = ItemStack.EMPTY;
    private int savedToolSlot = -1;
    private int savedWeaponSlot = -1;
    private boolean repairMode = false;
    private boolean repairPathIssued = false;
    private int repairSwapAttempts = 0;
    private int repairSwapRequestedTick = -1;
    /**
     * 「没有经验修补、修不了」提示记账（用户 2026-09-19）。
     *
     * <p>键 = 物品登记 ID + {@code "@"} + 槽位，同一件工具只提示一次，避免每 tick 刷屏；
     * 背包里已无受损工具时清空，工具被修好或换走后再出现照样重新提示。</p>
     */
    private String noMendingNotifiedKey = "";
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

    // ── 自用模式出售流程（用户 2026-09-20） ──
    // 「挖够就自己去卖掉」的四态链：SELL_TRAVEL（回主城）→ SELL_PATH（寻路到收购 NPC）→
    // SELL_TRADE（交互 + 循环出售到背包清零）→ SELL_RETURN（回子服）→ 交回 GO_WILD 继续 RTP。
    // 每一步都是「等 → 超时 → 重试 → 用完停机」三级兜底：服务器卡顿（TPS 掉、菜单不出、
    // 点了没反应）时原地重试，绝不带着一身矿往下瞎走。
    private final MiningPersonalSell personalSell;
    /** 当前步骤已等待刻数（每步推进时清零） */
    private int sellStepTicks = 0;
    /** 当前步骤已重试次数（推进到下一步时清零） */
    private int sellStepRetries = 0;
    /** 当前步骤序号（各态自己解释自己的取值） */
    private int sellPhase = 0;
    /** 出售中：已完成的「点矿石 → 点全部 → 点确认出售」轮数 */
    private int sellRounds = 0;
    /** 出售中：点「确认出售」之前的背包读数（这一轮的基线；一掉就说明这一轮真卖掉了） */
    private int sellPreConfirmCount = 0;
    /** 点「全部」之前「确认出售」格的指纹：变了 = 插件的选量已生效，可以立刻点确认，不必盲等 */
    private String sellConfirmFingerprint = "";
    /** 本轮起点（毫秒）：只是把每轮实际耗时写进日志，方便盯「还能再快哪一段」 */
    private long sellRoundStartMs = 0;
    /** 出售中：连续「点了却一颗没少」的轮数（攒到上限说明菜单没在收，停机而不是死循环） */
    private int sellNoProgressRounds = 0;
    /** 出售中：上一次点击后的菜单状态号，用来等「服务端已处理完这一次点击」 */
    private int sellLastMenuState = -1;
    /** 本态是否已下发过寻路目标（SELL_PATH 用） */
    private boolean sellPathIssued = false;
    /**
     * SELL_PATH 的寻路目标格：收购 NPC 正面朝向前方一格（{@link MiningPersonalSell#npcStandPos()}）。
     *
     * <p>缓存成字段而不是每刻重算：算一次要扫一遍实体表按名字匹配，而 NPC 是站着不动的。
     * 每 {@link #SELL_TARGET_REFRESH_TICKS} 刻刷一次 —— 只为兜住「刚落地时实体还没同步、
     * 那一刻退回配置坐标」的情况。</p>
     */
    private BlockPos sellTargetPos = null;
    /** SELL_PATH：上次采样时与 NPC 的距离平方（Double.MAX_VALUE = 本态尚未采样） */
    private double sellLastDist = Double.MAX_VALUE;
    /** SELL_PATH：连续无接近的采样轮数 */
    private int sellStallCount = 0;
    /** SELL_PATH：寻路目标格的刷新间隔（刻）；NPC 不动，只为兜住落地瞬间实体未同步 */
    private static final int SELL_TARGET_REFRESH_TICKS = 20;
    /** SELL_TRADE：距上次戳 NPC 的刻数（每 {@link #SELL_INTERACT_INTERVAL} 刻重试一次） */
    private int sellInteractTicks = 0;
    /** SELL_TRADE 阶段 0：已等商品列表刷出来的刻数（每次重开菜单 / 换屏归零） */
    private int sellListingWait = 0;
    /** SELL_TRADE：上一次看到的商店容器号（商店卖完一轮会当场重开一屏，换屏要记日志 + 重新等列表） */
    private int sellLastContainerId = -1;
    /** 出售轮数上限：一轮最多卖 64 个，20 组最坏 20 轮，64 轮足够且不会死循环 */
    private static final int SELL_MAX_ROUNDS = 64;
    /** 连续无进展轮数上限：两轮点下去一颗没少，说明菜单根本没在收 */
    private static final int SELL_NO_PROGRESS_LIMIT = 2;
    /** 单步重试上限（设置值再按它封顶，防手滑填大数字后无限重试） */
    private static final int SELL_RETRY_HARD_LIMIT = 10;
    /** 交互 NPC 的重试间隔（刻）：一次没打开菜单，1 秒后再戳一次 */
    private static final int SELL_INTERACT_INTERVAL = 20;
    /** 落地稳定刻数：位置跳变后再等这么多刻（跨服传送后有位置修正 / 客户端世界重建） */
    private static final int SELL_LAND_SETTLE_TICKS = 10;
    /** 一轮出售的结算等待刻数：确认出售后等这么久再看背包（原版菜单同步一拍 32 刻以内） */
    private static final int SELL_ROUND_SETTLE_TICKS = 20;
    /**
     * 等商店把商品列表异步刷出来的上限刻数（3 秒；列表格带出价格说明行即视为就绪）。
     *
     * <p>用户 2026-09-22：「npc 打开菜单的时候确实会等一下才会刷新出来」—— 商店先开一张空表格，
     * 商品格子是随后异步填进去的，且<b>只有背包里真有该商品时才可点</b>。上限给到 3 秒是因为
     * 这一等每屏只发生一次，等多久都不亏；反过来在列表没到时往下走，能点到的只有自己背包，
     * 一点就把矿石抓到手心（读数当场变 0 → 误报卖完）。</p>
     */
    private static final int SELL_LISTING_GRACE_TICKS = 60;
    /**
     * 点完「全部」后停的刻数再点「确认出售」（0.5 秒）。
     *
     * <p>用户 2026-09-22：「要先点全部 才能点 全部出售」。两次点击挨在同一刻发出时，插件把
     * 「已选数量」落进自己会话多半还没轮到下一拍，确认那一下会读到「还没选数量」→ 卖 0；实测
     * 日志里 {@code #22 全部} 与 {@code #15 确认出售} 同刻发出、之后一颗没少。隔半秒再点，
     * 代价可忽略（一轮就一次）。</p>
     */
    private static final int SELL_CONFIRM_DELAY_TICKS = 10;
    /**
     * 点完「全部」后最少要过几刻才允许点「确认出售」（两拍）。
     *
     * <p>留着这一拍是因为两次点击必须分两个 tick 发出去；超过它之后就看 {@code sellConfirmFingerprint}
     * 有没有变（插件把已选数量落进会话会改写「确认出售」那一格），变了就立刻点，不再等满
     * {@link #SELL_CONFIRM_DELAY_TICKS}。</p>
     */
    private static final int SELL_CONFIRM_MIN_TICKS = 2;
    /**
     * 真正「站到收购 NPC 身旁」的距离平方（眼睛到实体中心 ≤ 2.5 格）。
     *
     * <p>用户 2026-09-22 复现「都没走到npc身边 隔着好远」：老值是 16.0（4 格），恰好压在原版
     * 交互判据的边界上 —— 服务端 {@code ServerGamePacketListenerImpl#handleInteract} 用的是
     * 「实体 AABB 外扩 3.0」({@code isWithinEntityInteractionRange})，人在 3.5~4 格时右键照样通得过、
     * 收购菜单也确实开出来了，但商店插件在<i>点商品格</i>那一步按更近的距离校验，于是连着十几次点击
     * 全被当成无效点击丢掉（实测菜单状态号只跟着商店每 10 秒的定时刷新在涨、内容一直是商品列表）。
     * 收紧到 2.5 格后 Baritone 会继续走到 GoalNear(1) 收尾，人才真的贴到 NPC 跟前再动手。</p>
     */
    private static final double NPC_ARRIVE_DISTANCE_SQR = 6.25;
    /**
     * 交易中「人还站在 NPC 身旁」的上限距离平方（3.5 格）：超出就回 {@code SELL_PATH} 重新走位。
     *
     * <p>比到达阈值宽一格是刻意留的回差：到位后实体挤一下、脚下一滑不至于立刻被踢回寻路态来回抖，
     * 但真被人挤开 / 传送偏了又会老老实实走回来再点。</p>
     */
    private static final double NPC_TALK_DISTANCE_SQR = 12.25;

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
    /**
     * 进进食态时是否刻意保留了挖矿现场（没停 Baritone / 秒破 / 连锁）。
     *
     * <p>用户 2026-09-19「要边走边吃，不是停下来吃」：MINING → EATING 不停挖矿，回来时若 mine 还活着
     * 就不重下发（重下发会把正在破坏那一块的进度丢掉）。见 {@code onStateExit} / {@code onStateEnter}。</p>
     */
    private boolean mineKeptDuringEating = false;
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

    // ── 「挖不动」快速自愈（用户 2026-09-18，2026-09-19 扩一档） ──────────────
    // 「卸完货 RTP 切状态后莫名其妙卡住，拿着镐子不挖东西，绿框一直闪却过不去，重启模块才恢复——
    //  寻路没问题，就是挖不掉东西导致它寻不了路」。旧实现只有 3 分钟一档的位移判据（NO_MOVE_THRESHOLD），
    // 玩家要干等 3 分钟才等到自愈。这里用更精确的判据把响应压到 15 秒，共两档：
    //   ① 在寻路（绿框在闪）却原地不动，同时秒破没有任何活跃目标 → 根本挖不动（2026-09-18）；
    //   ② mine 进程还活着（isMiningActive）却根本没在寻路 → 算路失败 / 目标不可达，mine 停摆
    //      （2026-09-19：幽冥脉络下的钻石矿，状态机完全静默卡住；旧判据要求「必须在寻路」，
    //      于是①②两条都不满足、只剩 3 分钟兜底）。
    //   ③ 人还在寻路（所以①②都不成立）却长时间没打穿任何方块 → 目标矿够不到 / 打不穿
    //      （2026-09-19 用户复报「幽冥脉络下面的钻石原矿挖不了，然后卡死」；判据刻意与速度无关）。
    private int mineNoProgressTicks = 0;            // 「在寻路却毫无进展」的连续 tick
    private int mineNoProgressResets = 0;           // 已重下发 mine 脱困的次数（连续两次才升级为换区）
    /**
     * 本次「毫无进展」属于哪一档（只影响播报用词）：
     * 1 = 在寻路却挖不动面前的方块；2 = mine 进程活着却没在寻路（进程停摆）；3 = 长时间没打穿任何方块。
     */
    private int mineStallKind = 0;
    /** 第三档的连续计数（刻）：连续满足才判定，避免单刻抖动误伤 */
    private int mineNoBreakTicks = 0;
    private static final int MINE_NO_PROGRESS_LIMIT = 300; // 15 秒毫无进展即判定挖不动
    /**
     * 第三档门槛（刻）：mine 活着、人还在寻路（所以前两档都不成立），但这么久没打穿过任何方块
     * → 判「目标矿够不到 / 打不穿」。
     *
     * <p>用户 2026-09-19：「幽冥脉络下面的钻石原矿挖不了，然后卡死」——现场就是被 Baritone 带着来回走、
     * 目标矿永远到不了，而前两档都要「速度低」、原地抖动那档要「位移 &lt; 2 格 / 10 秒」，
     * 一条都不满足，于是聊天栏一条播报都没有。90 秒足够长：正常挖矿几刻就破一块，
     * 就算长途赶路也不会有 90 秒一块不破；且连续两次才升级为换区。</p>
     */
    private static final int MINE_NO_BREAK_LIMIT_TICKS = 1800;
    /** 第三档的确认刻数：条件连续满足这么多刻才触发（避免单刻抖动） */
    private static final int MINE_NO_BREAK_CONFIRM_TICKS = 20;
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

    /**
     * 「服务器真把人挪走了」的单刻位移阈值（8 格）。
     *
     * <p>玩家正常移动单刻最多约 1.5 格（鞘翅），8 格是安全分界；而服务器传送是一瞬间完成的，
     * 哪怕只挪几十格也会超过它。与 {@link #MANUAL_TELEPORT_DISTANCE_SQR}（32 格，抓「玩家自己敲指令传送」）
     * 同族但更灵敏：那边放宽是为了不误伤落地位置修正，这边收紧是因为它要充当「传送成功」的判据。</p>
     */
    private static final double TELEPORT_JUMP_DISTANCE_SQR = 8.0 * 8.0;

    /** 本刻是否发生瞬移级位移：由 {@link #manualTeleportDetected()} 每刻刷新，GO_WILD 用它判传送是否生效 */
    private boolean teleportJumpThisTick = false;

    /** 玩家自己敲的最后一条服务器指令原文（不含前导斜杠，仅用于播报）与它的 tick */
    private String playerCommandText = "";
    private int playerCommandTick = -100000;

    /**
     * 「玩家指令后这一跳算玩家的」窗口（刻，5 秒）。
     *
     * <p>用户 2026-09-18 选择<b>按结果判</b>（而非「一敲就停」）：敲了指令先不动，
     * 看它在窗口内有没有真的把位置挪走——{@code /w}、{@code /msg}、{@code /ping} 这类
     * 不改位置的指令不该打断挖矿，{@code /home}、{@code /spawn}、{@code /tpa} 才该停。
     * 服务器瞬移通常一两刻就到，插件排队也是秒级，5 秒足够；窗口过期自动失效。</p>
     */
    private static final int PLAYER_COMMAND_WINDOW_TICKS = 100;

    /**
     * 玩家指令窗口内认定的跳变阈值（格）：比常规的 32 格小得多。
     *
     * <p>为什么能小：这条线索的前提是「玩家刚敲了一条服务器指令」，剩下的只是确认它有没有挪动位置
     * ——服务器瞬移也可能只挪几格（{@code /home} 就在附近、{@code /tpa} 到身边的人、插件小位移）。
     * 存活状态下单刻位移远达不到 8 格（自由落体极限约 3.9 格/刻、鞘翅约 1.5 格/刻），
     * 所以这条线索上用 8 格不会误判成传送。</p>
     */
    private static final double PLAYER_COMMAND_DISTANCE_SQR = 8.0 * 8.0;

    /**
     * 坚守者预警守卫（本项目新增，用户 2026-09-18 需求；检测口径与源码依据见
     * {@link WardenWarningGuard} 的类注释）。
     *
     * <p>远古城市里挖到尖啸体一响就往野外跑：守卫每刻查「身边 32 格内有没有尖啸体正在尖叫」，
     * 命中即由 {@link #escapeFromWardenWarning()} 走既有的「前往野外」传送链路逃离。</p>
     */
    private final WardenWarningGuard wardenGuard;

    /** 开箱允许的最大「眼到方块中心」距离的平方（4.0 格，与容器层同一口径） */
    private static final double CONTAINER_OPEN_DISTANCE_SQR = 16.0;

    // 自动捡取掉落物（旧 :109-114）
    private ItemEntity pickupTarget = null;
    private int pickupTimeout = 0;
    // 捡取节奏控制（本项目修正，见 tryPickupNearbyOre 注释）：脚边的掉落物交给挖掘流程自然吸拾，
    // 只为「已经拉开距离的漏捡物」停一次 mine；一轮之内一趟捡完，收工后才计冷却
    private int pickupCooldownTicks = 0;
    private static final int PICKUP_MIN_AGE_TICKS = 40;    // 落地满 2 秒仍没被自然吸走＝漏捡，立刻认领（原先 5 秒太久：人已走出 8 格，只能回头跑＝白跑一趟）
    private static final int PICKUP_PATH_FAIL_TICKS = 20;  // 认领 1 秒后寻路进程仍未起步＝够不到，直接判不可达进黑名单（原先 2 秒，白等一秒）
    private static final int PICKUP_COOLDOWN_TICKS = 60;   // 一轮捡取结束后 3 秒内不另开新轮次（原先 10 秒：漏的攒成一批，又得多跑一趟）
    private static final double PICKUP_RADIUS = 8.0;       // 漏捡物扫描半径（原先 6 格：矿脉边缘/侧边小洞的掉落物常落在 6 格外，判成「漏检」白丢）
    private static final double PICKUP_IGNORE_RADIUS_SQR = 2.25; // 脚边 1.5 格内的掉落物不专门去捡：走过去就自动吸到，为它停 mine 会退化成「挖一下停一下」
    // 捡取失败黑名单：掉落物卡角落捡不起来时记录位置，避免反复寻路捡同一个
    private final Set<BlockPos> pickupBlacklist = new HashSet<>();
    private static final int MAX_PICKUP_BLACKLIST = 64; // 黑名单上限，防止无限增长

    // 岩浆避险：附近有岩浆时停止挖矿并寻路到安全位置（旧 :116-119）
    private boolean lavaEscapeActive = false;   // 岩浆脱困寻路进行中
    private int lavaEscapeTicks = 0;            // 岩浆脱困已持续 tick
    private int lavaEscapeCooldown = 0;         // 脱困成功后的避险冷却（避免在岩浆矿区反复触发）
    // 2026-09-18 由 200 收到 100（用户：「老是走到岩浆旁边然后被烧」）：10 秒冷却太长，逃离后
    // Baritone 立刻又把路径规划回岩浆边缘，整个冷却窗内都在贴边走；5 秒足够走出一个矿位
    private static final int LAVA_ESCAPE_COOLDOWN_TICKS = 100;
    private boolean diedInLava = false;         // 死亡时是否在岩浆里（复活后跳过 back 用）

    // 岩浆垫脚（用户 2026-09-18：「岩浆边用踮脚方块……填满附近就行 能通行就行」「封堵是在我寻路的路上
    // 顺便」「堵岩浆加一下距离把玩家原版默认最大手长就行了」）：玩家脚底/脚层/头顶三层里、
    // 手长范围内的岩浆格直接填实（含嵌在墙体里的岩浆源），铺出通路直接走，取代原先「附近有岩浆就撤离」。
    // 只在玩家已经走到的位置顺手封，绝不专门寻路过去封。
    // 用户 2026-09-19：「放岩浆的时候会罚站放，应该边走边放」——不再停 Baritone，人沿路径继续走，
    // 我们每刻把够得着的岩浆格填上（见 tickLavaBridging）。
    private boolean lavaBridging = false;       // 垫脚铺设进行中（不停 Baritone，只临时占用主手）
    private int bridgeTicks = 0;                // 本轮铺设已持续 tick（超时兜底用）
    private int bridgeIdleTicks = 0;            // 连续没有可放目标的刻数（判定铺完）
    private int bridgePlaced = 0;               // 本轮已放置块数（上限见 BRIDGE_MAX_PLACES_PER_ROUND）
    private final Set<BlockPos> bridgeBlacklist = new HashSet<>(); // 本轮放不进的格子，不再重试
    private boolean bridgeNoBlockWarned = false; // 「身上没垫脚方块」一次性提示（reset 不清，避免刷屏）
    // 扫描循环上界（格）：真正的距离判据是玩家手长，见 findNextBridgeTarget。
    // 循环只负责圈出候选范围（6 略大于服务端接受的「手长 + 1」上限），多扫几十格代价可忽略
    private static final int BRIDGE_SCAN_RADIUS = 6;
    // 每刻连放几块（用户 2026-09-19：「填岩浆的时候是一个一个放的，能不能一次性铺满，跟投影打印机那种」）：
    // 旧实现是「3 刻一块」且停着不动，一轮 16 块要站 2.4 秒。现在每刻连放一批，每块重新挑目标
    // （BlockPlacer 本地立刻落块，所以后一块可以拿刚放的那块当锚点，涟漪式铺出去）；
    // 一批限 4 块是给服务端留的呼吸量——放置包走平台层绕行直发（不再被「发包防踢」每秒 8 个的限速掐），
    // 单刻 4 个小包是安全的突发量
    private static final int BRIDGE_PLACES_PER_TICK = 4;
    private static final int BRIDGE_IDLE_DONE_TICKS = 10;   // 0.5 秒没有新目标＝铺完（岩浆流动会带出新面）
    private static final int BRIDGE_MAX_TICKS = 400;        // 一轮铺设 20 秒封顶（防「放不进又不进黑名单」的病态循环）
    // 一轮最多铺几块（用户 2026-09-18：「封堵是在我寻路的路上 顺便」）：三层 3×7×7 全铺满最坏 147 格，
    // 一轮只铺最近的一批（先脚底后脚层再头顶，见 findNextBridgeTarget）；人往前走，新进入手长范围的
    // 岩浆格触发下一轮 —— 与手动垫脚一样是「一块贴一块」往前推进。
    // 安全性由 Baritone 保证：岩浆在它的 blocksToAvoid 里，它不会自己走进没填的岩浆
    private static final int BRIDGE_MAX_PLACES_PER_ROUND = 16;
    // 两轮之间歇 2 秒：给「随手填」留出间隔，不然岩浆区里会一刻不停地触发下一轮，
    // 主手被垫脚方块反复占用、秒破的破坏进度被拖慢（挖矿时间被吃光）
    private static final int BRIDGE_ROUND_COOLDOWN_TICKS = 40;
    private int bridgeCooldown = 0;             // 距离下一轮可触发还差几刻

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
        this.wardenGuard = new WardenWarningGuard();
        this.personalSell = new MiningPersonalSell(module);
    }

    /**
     * 自用模式出售流程的动作层（用户 2026-09-20）。
     *
     * <p>模块的界面门控要用它判「当前这个容器界面是不是我方出售菜单」（是就压掉，不弹不抢鼠标），
     * 见 {@code AutoMinerModule#onOpenScreen}。</p>
     */
    public MiningPersonalSell personalSell() {
        return personalSell;
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
        teleportJumpThisTick = false;
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
        noMendingNotifiedKey = "";
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
        lavaBridging = false;
        bridgeTicks = 0;
        bridgeIdleTicks = 0;
        bridgePlaced = 0;
        bridgeCooldown = 0;
        bridgeBlacklist.clear();
        combat.reset();
        wardenGuard.reset();
        playerCommandText = "";
        playerCommandTick = -100000;
        // 自用模式出售流程：连静默门控一起清（停机后服务端再推容器界面时不该被压掉）
        personalSell.reset();
        sellStepTicks = 0;
        sellStepRetries = 0;
        sellPhase = 0;
        sellRounds = 0;
        sellPreConfirmCount = 0;
        sellNoProgressRounds = 0;
        sellLastMenuState = -1;
        sellPathIssued = false;
        sellLastDist = Double.MAX_VALUE;
        sellStallCount = 0;
        sellInteractTicks = 0;
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
        // 停机不是「转进食」：照常收掉秒破会话与寻路（nextState 传 IDLE 只作分类用）
        onStateExit(state, MinerState.IDLE);
        mc.options.keyUse.setDown(false);
        if (state == MinerState.UNLOADING || state == MinerState.SUPPLY || state == MinerState.REPAIR
            || isSellState(state)) {
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

        // 坚守者预警（本项目新增，用户 2026-09-18 需求）：地下古城里尖啸体一响就往野外跑。
        // 排在手动传送检测之前、且不受当前状态限制——4.5 秒的逃生窗口不能被别分支吃掉
        WardenWarningGuard.Alarm wardenAlarm = wardenGuard.tick();
        if (wardenAlarm != WardenWarningGuard.Alarm.NONE && escapeFromWardenWarning(wardenAlarm)) return;

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
            case SELL_TRAVEL -> tickSellTravel();
            case SELL_PATH -> tickSellPath();
            case SELL_TRADE -> tickSellTrade();
            case SELL_RETURN -> tickSellReturn();
        }
    }

    /** 状态切换（旧 {@code transitionTo}，{@code :203-218} 逐字） */
    private void transitionTo(MinerState newState) {
        if (state == newState) return;

        // 状态退出清理（把目标状态一并传进去：转进食时要保留挖矿现场，见 onStateExit）
        onStateExit(state, newState);

        MinerState oldState = state;
        previousState = oldState;
        state = newState;
        stateTick = 0;

        // 状态转换播报
        broadcastStateTransition(oldState, newState);

        // 落一行日志（诊断自用模式跨服卖矿卡住，用户 2026-09-22）：卖矿链（SELL_*）如果不往下走了，
        // 日志最后一行就是它停住的那一步，配合 [卖矿流程] 的指令 / 配置阶段记录能直接看出卡在哪
        MINING_LOG.info("[卖矿流程] 状态机 {} → {}", oldState, newState);

        // 状态进入初始化
        onStateEnter(newState);
    }

    /** 状态进入副作用（旧 {@code onStateEnter}，{@code :221-251} 逐条一致；战斗态为本项目新增） */
    private void onStateEnter(MinerState newState) {
        if (newState == MinerState.UNLOADING || newState == MinerState.SUPPLY || newState == MinerState.REPAIR
            || newState == MinerState.DEATH_HANDLING || newState == MinerState.COMBAT || isSellState(newState)) {
            module.getContainer().closeContainer();
            module.getBaritone().stop();
        }

        // 物流寻路（卸货/补给/修补）用独立开关控制是否破坏方块；其余状态一律回到全局破坏设置。
        //
        // 旧实现只在进入 MINING 时恢复全局值，于是 GO_WILD / EATING / DEATH_HANDLING 期间 Baritone 一直
        // 停在物流值上；玩家在物流态关模块时全局 allowBreak 会被永久改掉（关模块不会回到挖矿态来恢复）。
        boolean logistics = newState == MinerState.UNLOADING || newState == MinerState.SUPPLY
            || newState == MinerState.REPAIR || isSellState(newState);
        module.getBaritone().updateSetting("allowBreak", switch (newState) {
            case UNLOADING, SUPPLY, REPAIR, SELL_TRAVEL, SELL_PATH, SELL_TRADE, SELL_RETURN ->
                module.isLogisticsBreakBlocks();
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
            // 手上还留着垫脚方块就先换回镐子（用户 2026-09-18：「有时候会拿着踮脚方块 挖矿进入状态机」）——
            // 只在「主手是搭路白名单方块」时生效，见 ensureMiningToolInHand
            ensureMiningToolInHand();
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
            // 进食期间刻意没停挖矿（见 onStateExit）：mine 还活着就直接接着挖，不重下发 ——
            // 重下发会丢掉正在破坏那一块的进度与裂纹，等于「吃完回来从头挖」，正是要去掉的停顿。
            // mine 万一在进食期间退了，这里不接管，下一轮走正常启动流程自愈
            if (resumeInPlace && mineKeptDuringEating && module.getBaritone().isMiningActive()) {
                mineStartIssued = true;
            }
            mineKeptDuringEating = false;
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

    /**
     * 状态退出副作用（旧 {@code onStateExit}，{@code :253-261} 逐字）。
     *
     * @param nextState 正在切换到的目标状态：转 {@link MinerState#EATING} 时不停挖（见下）
     */
    private void onStateExit(MinerState oldState, MinerState nextState) {
        if (oldState == MinerState.MINING) {
            // 转进食时<b>什么也不停</b>（用户 2026-09-19：「要边走边吃，不是停下来吃」）：
            // 进食全程走副手，主手、选定槽、寻路、秒破、连锁都不需要让位。停一次会清掉正在破坏
            // 那一块的进度与连锁队列，回来还得重新下发 mine —— 表现就是「站住 1.6 秒 + 重新起步」，
            // 正是用户要去掉的停顿。mine 全程存活，回来时也不再重下发（见 onStateEnter 的 MINING）
            if (nextState == MinerState.EATING) {
                mineKeptDuringEating = true;
            } else {
                // 离开挖矿态：把秒破手上那个方块的会话收掉（发 ABORT 清服务端槽位 + 清裂纹），
                // 否则收摊后残留的 START 槽位会顶掉下一个方块的破坏进度
                MiningFastBreakController.instance().release(mc, true);
                // 连锁队列一并清空（连锁只在采掘中成立；留着会带着旧坐标回到挖矿态）
                module.getVeinMiner().reset();
                module.getBaritone().stop();
            }
        }
        if (oldState == MinerState.REPAIR) {
            stopRepairCombat();
            restoreHotbar();
        }
        if (oldState == MinerState.EATING) {
            // 进食结束：停掉发包连吃（清在吃的那一件 + 松开右键），再把临时顶掉的热键栏物品换回原位
            // （用户 2026-09-17：垫脚方块被顶进背包换不回来）
            module.getContainer().stopPacketEat();
            module.getContainer().restoreEatDisplacedItem();
        }
        if (oldState == MinerState.UNLOADING || oldState == MinerState.SUPPLY) {
            // 容器会话收尾：中途被死亡/岩浆/卡死等分支打断时箱子可能还开着，
            // 残留期间所有背包点击都会被客户端当「窗口不匹配」吞掉（换食物、丢垃圾全部失效）
            module.getContainer().closeContainer();
        }
        if (isSellState(oldState)) {
            // 出售流程的容器收尾（同上：市场菜单开着时被打断不能留着）
            module.getContainer().closeContainer();
            // 离开整条出售链（转 GO_WILD / 停机）才解除界面静默门控；
            // 链内态与态之间（回城 → 寻路 → 出售 → 回服）保持静默，不中途松开
            if (!isSellState(nextState)) personalSell.endFlow();
        }
        if (oldState == MinerState.COMBAT) {
            // 还原 Baritone 的怪物规避（战斗期间临时关掉了，见 onStateEnter）
            module.getBaritone().updateSetting("avoidance", module.settings().mobAvoidance);
            // 交回寻路视角（战斗期间让给了 MiningCombat 的转视角）
            module.getBaritone().setCombatViewHold(false);
            // 清敌情记录（含苦力怕了结锁定）：离开战斗态就重新算，别带着上一场的实体引用
            combat.reset();
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
            // 判据同样是「单刻瞬移」：玩家在等待期自己走动不算传送生效（用户 2026-09-19 实测）。
            if (teleportJumpThisTick) {
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

        // 阶段 1：发送传送命令
        if (stateTick == 1) {
            teleportRetryNoticeShown = false;
            executeOwnTeleport(module.getWildCommand(), true);
            teleportTimeout = module.getTeleportDelay() * 20; // 转换为tick
            return;
        }

        // 阶段 2：等待命令执行完成
        if (cmdMgr.isCommandExecuting()) {
            return;
        }

        // 阶段 3：检测传送是否成功（只看单刻瞬移）。
        // 旧判据「离起点超过 2 格即算生效」会把玩家自己的走动当成传送成功：服务器 RTP 先提示
        // 「請勿移動」，这几秒里玩家一动服务器就取消传送，可位置照样变了 > 2 格 → 人还在原地就进
        // MINING，Baritone 就地找矿挖（用户 2026-09-19 实测）。瞬移判据则只有服务器真把人挪走才成立。
        if (teleportJumpThisTick) {
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
        // 垫脚铺设期间照常允许重启 mine（用户 2026-09-19 改「边走边放」）：旧实现进铺设前会 stop Baritone，
        // 这里重发 mine 会把铺到一半的人拖走，所以当时专门避让 lavaBridging；现在 Baritone 全程不停，
        // 反而必须让它照常重启——否则 mine 退出后没人重新下发，人就在原地站着不动了
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

        // 优先级 2.0：够得着的镐里没有「现在就能直接拿来挖」的（模式对得上 + 还能挖）→ 换一把，
        // 换不到就停机（用户 2026-09-21：「剩下1点耐久停下来 不要把镐子挖爆」＋「精准采集不放行
        // 一定要带精准采集的」）。
        //   · 模式这一维与启动自检同源（module.matchesLootMode）：精准采集模式必须带精准采集附魔，
        //     时运模式要时运镐或普通镐 —— 自检放行什么，运行期就只认什么；
        //   · 耐久只管**没有经验修补**的镐（剩 1 点算报废），带经验修补的一律算能用；
        //   · 独立于下面的耐久阈值：阈值被调到 1 或 0 时 2b 不再命中，这条照样成立；
        //   · 只看快捷栏 0-8：副手是常驻食物位（自动进食一直占着它），且 Baritone 的自动选工具
        //     也只认快捷栏，副手与背包 9~35 的镐都不能当「还有镐」的依据；
        //   · 背包同步窗口内不判（与上面的「缺少镐子」同一道保护），避免换维度 / 传送式重生时误停机。
        if (inventoryTrustTicks <= 0 && !hasReadyPickaxeInReach()) {
            // 先试着换一把能用的继续挖（顶上来的可能来自主背包），真换不到才停机
            if (swapInReadyPickaxe() || module.getContainer().ensureToolsInHotbar()) return;
            module.getBaritone().stop();
            // 副手放着镐这种配置很隐蔽（副手是常驻食物位、Baritone 又用不到它），停机时点一句免得白停
            String offhandHint = isReadyPickaxe(mc.player.getOffhandItem())
                ? "（副手那把镐用不上，请放进快捷栏）" : "";
            module.error("§c✗ 没有能用的镐子 §8▸ 当前是「" + module.settings().lootMode + "」模式，快捷栏与背包里都"
                + "没有" + module.requiredPickaxeName() + "（或剩余耐久不足）" + offhandHint + "，自动挖矿已停止");
            if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
            return;
        }

        // 运行期定期补一次「工具回快捷栏」：玩家中途手动整理背包后工具可能又被放回背包，
        // 而 Baritone 的自动选工具只认快捷栏 0-8，看不到背包里的工具
        if (stateTick % 100 == 0) {
            // 进食顶掉的物品若因容器还开着没换回，这里补做（无记录时是空操作）
            module.getContainer().restoreEatDisplacedItem();
            if (module.getContainer().ensureToolsInHotbar()) return;
        }

        // 运行期定期补一次「主手不是方块」：残留方块可能来自任何一条路径——Baritone 自己搭桥 / 垫脚
        // 留下的投放方块、上一槽本来就是方块时 BlockPlacer 还原不掉、垫脚出事早退（用户 2026-09-19
        // 「搭完路不切换回镐子，手里还拿着搭路的方块，然后卡死状态机」，是重犯）。拿着方块挖矿的
        // 破坏速度约 210 刻（镐约 7 刻），表现就是「卡住不动」，所以这里 1 秒兜一次；
        // 垫脚正在铺的那一刻跳过（那时主手本来就该是方块）
        if (stateTick % 20 == 0 && !lavaBridging) {
            ensureMiningToolInHand();
        }
        // 2a：带经验修补的工具低于阈值 → 前往挂机点联动杀戮光环修复（旧 :348-367）
        //     自用模式跳过这一条（用户 2026-09-20）：自用模式不绑挂机修复点，时运镐挖矿自带经验、
        //     靠经验修补自修，「耐久」不是要处理的输入；真走下去只会去找一个根本没绑定的挂机点。
        if (!module.isPersonalMode() && findDamagedToolSlot(true) != -1) {
            module.getSoundNotifier().notifyLowDurability();
            transitionTo(MinerState.REPAIR);
            return;
        }

        // 2b：低于阈值但**没有经验修补**的镐子 → 修不了：改用背包内其余镐子继续挖，真没得换才停机
        //     （旧 :348-367 与 handleUnrepairableTool()）。
        //     自用模式**照走这一条**（用户 2026-09-21：「加上吧 我原来的代码 不是没经验修补的稿子快没耐用
        //     了 就会换一把稿子继续挖吗 实在没耐久了才停下」）：自用模式只是不去挂机修复点，
        //     「镐子用不了就停」这件事必须在 —— 否则镐子报废后挖矿彻底没产出，状态机还一直空跑。
        if (handleUnrepairableTool()) return;

        // 优先级 2.5：饱食度检测（低于15时进食；背包没白名单食物则直接去补给，旧 :369-380）
        FoodData foodData = mc.player.getFoodData();
        if (foodData.getFoodLevel() < 15) {
            if (hasFoodToEat()) {
                eatReason = EAT_REASON_HUNGER; // 播报区分「饿了」与「掉血」（见 transitionTo 的 EATING 文案）
                transitionTo(MinerState.EATING);
            } else {
                // 没吃的还进进食状态会死循环（超时→MINING→又饿→又进食），必须转补给
                transitionTo(MinerState.SUPPLY);
            }
            return;
        }

        // 优先级 2.6：自动回血（用户 2026-09-19 追加，不占设置项）
        // 血量未满、且饱食度还没满（原版普通食物只在饿的时候吃得下）时就吃一顿；
        // 复用进食态 —— 它自己负责搬食物、发包连吃、超时保护、退场还原，不另造第二套进食流程。
        // 回血原理：发包连吃把饱食度顶满并带上饱和度，原版自然再生的条件（饱食度 ≥ 18）随之满足，
        // 饱和度 > 0 时 10 刻回 1 点血（FoodData#tick），血会在回到挖矿态后继续自己涨。
        //
        // 用户 2026-09-19「边挖边吃」：进食改走副手（见 MiningContainer#packetEat 的副手模式），
        // 主手与选定槽全程不动，所以这里<b>不再停 Baritone</b> —— 那 1.6 秒/件（服务端 Consumable
        // 规则，客户端改不了）不再打断挖矿。食物由 {@code tickOffhandRation} 常驻副手（用户当天
        // 追加「不能一直放在副手？」），只有挂机修复点（REPAIR）需要副手放要修的镐子 —— 它整组
        // 换走、修完由 restoreHotbar 换回，与进食不会同时占副手。
        if (mc.player.getHealth() < mc.player.getMaxHealth() && foodData.getFoodLevel() < 20 && hasFoodToEat()) {
            // 播报由 transitionTo 的 EATING 文案统一出（按 eatReason 区分「饿了 / 掉血」），
            // 这里不再自己打一条，否则同刻两行重复（用户 2026-09-19：「有点刷屏了」）
            eatReason = EAT_REASON_HEAL;
            transitionTo(MinerState.EATING);
            return;
        }

        // 优先级 3：食物不足检测（检查背包食物组数，旧 :382-387）
        if (countFoodStacks() < module.getHungerThreshold()) {
            module.getSoundNotifier().notifyLowFood();
            transitionTo(MinerState.SUPPLY);
            return;
        }

        // 优先级 3.5：自用模式出售触发（用户 2026-09-20）——挖够触发组数、或背包先满，就去卖。
        // 放在卸货检测之前：自用模式根本不走卸货流程，同一刻只可能命中这一条（普通模式这条恒不成立）。
        if (module.isPersonalMode()) {
            if (personalSellReady()) {
                transitionTo(MinerState.SELL_TRAVEL);
                return;
            }
            if (personalSell.isInventoryFull()) {
                module.info("§e⚠ 背包已满 §8▸ 提前前往出售");
                transitionTo(MinerState.SELL_TRAVEL);
                return;
            }
        }

        // 优先级 4：满载检测（旧 :389-394）。自用模式**整条跳过**：自用模式的「满载」含义是「去卖」，
        // 上面 3.5 已经判过（触发组数 / 背包满）；这里再判一次会把「触发组数比满载组数大」的配置
        // 拐进 UNLOADING，而自用模式不绑卸货点、不走卸货流程。设置与生效保持一致：
        // 「触发条件」页的「满载组数」在自用模式下也已隐藏（用户 2026-09-21）。
        if (!module.isPersonalMode()) {
            int oreStacks = countOreStacks();
            if (oreStacks >= module.getUnloadThreshold()) {
                transitionTo(MinerState.UNLOADING);
                return;
            }
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
                // 连锁挖矿正在用秒破通道逐块清矿脉，期间是**我方主动停的** Baritone（服务端单槽位不能两头发包）：
                // 这里必须放行，否则会把「停 mine」误判成「mine 退出」并去重启它——用户 2026-09-18 实机：
                // 「刚放两个就提示『挖矿进程已退出 ▸ 正在重启（1/3）』然后重新寻路了」，就是这条看门狗误触发。
                // 岩浆垫脚**不在**这条豁免里了（用户 2026-09-19 改「边走边放」）：垫脚期间 Baritone 全程不停，
                // mine 真退出就该照常重启，否则人会站在原地不动
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
        boolean idleNoBreak = !uiOpen
            && mc.player.tickCount - mineStartTick > MINE_START_GRACE_TICKS
            && sinceLastBreak > MINE_PROGRESS_GRACE_TICKS
            && currentSpeed < MIN_SPEED_THRESHOLD
            && !MiningFastBreakController.instance().isActive()
            // 原版/Baritone 正在破坏某个方块时不算静止：硬方块（黑曜石、远古残骸）慢速破坏、
            // 以及没走秒破通道的那些块，都会让「没寻路 + 没破坏过」同时成立，不能误判成挖不动
            && !mc.gameMode.isDestroying();
        boolean mineNoProgress = idleNoBreak && module.getBaritone().isPathing();
        // 用户 2026-09-19：「幽冥脉络下面的钻石矿挖不了，状态机完全静默卡住」——mine 进程还活着
        // （isMiningActive=true）却根本没在寻路，就是算路失败 / 目标不可达。旧判据要求「必须在寻路」，
        // 于是这种停摆一条自愈都不触发（挖不动 15 秒不满足、mine 退出重启不满足、原地抖动要 3 分钟），
        // 表现出来就是「站着不动、聊天栏一条播报都没有」。这里把「mine 活着但不推进」也纳入 15 秒自愈。
        if (!mineNoProgress && idleNoBreak && module.getBaritone().isMiningActive()) {
            mineNoProgress = true;
        }
        // 记下这一档是哪种停摆（只影响播报用词）：没在寻路 = mine 停摆，在寻路 = 挖不动方块
        mineStallKind = mineNoProgress ? (module.getBaritone().isPathing() ? 1 : 2) : 0;
        mineNoProgressTicks = mineNoProgress ? mineNoProgressTicks + 1 : 0;

        // 第三档（判据与速度无关，见 MINE_NO_BREAK_LIMIT_TICKS 注释）：人还在寻路、目标矿却永远到不了。
        // 前两档都要求「速度低」，这一档专抓「一直在走、一直没打穿任何方块」——
        // 就是用户复报的「幽冥脉络下面的钻石原矿挖不了，然后卡死」那种现场。
        boolean mineAliveNoBreak = !mineNoProgress
            && !uiOpen
            && mc.player.tickCount - mineStartTick > MINE_START_GRACE_TICKS
            && sinceLastBreak > MINE_NO_BREAK_LIMIT_TICKS
            && !MiningFastBreakController.instance().isActive()
            && !mc.gameMode.isDestroying()
            && (module.getBaritone().isMiningActive() || module.getBaritone().isPathing());
        mineNoBreakTicks = mineAliveNoBreak ? mineNoBreakTicks + 1 : 0;
        if (mineNoBreakTicks >= MINE_NO_BREAK_CONFIRM_TICKS) {
            mineNoBreakTicks = 0;
            mineStallKind = 3;
            mineNoProgress = true;
            mineNoProgressTicks = MINE_NO_PROGRESS_LIMIT + 1; // 直接进下面同一套恢复动作
            MINING_LOG.warn("长时间无进展 ▸ {} 刻没打穿方块（寻路={} mine={} 速度={}）→ 重置采掘目标",
                sinceLastBreak, module.getBaritone().isPathing(), module.getBaritone().isMiningActive(),
                String.format("%.3f", currentSpeed));
        }
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
                module.info(switch (mineStallKind) {
                    case 2 -> "§e⚠ 挖矿进程停摆（没在寻路）§8▸ 重置采掘目标脱困";
                    case 3 -> "§e⚠ 目标矿长时间够不到 §8▸ 重置采掘目标脱困";
                    default -> "§e⚠ 挖不动面前的方块 §8▸ 重置采掘目标脱困";
                });
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

        // 岩浆处理（用户 2026-09-18 定稿改口径：「碰到岩浆就进入状态机 现在改成封边直接绕过去就好
        // 了 不用『附近 N 格内有岩浆 ▸ 寻路到安全位置』」）：
        //   ① <b>撤离只兜「已经出事」</b>：人泡在岩浆里 / 身上着火才撤离。原先「附近 lavaAvoidRadius
        //      格内有岩浆就撤离」的触发已废——那套触发的实际表现是来回跑：撤离→冷却→mine 把路径
        //      又规划回岩浆边（矿就在那边）→冷却一过再撤离，挖矿被打断成碎片（用户：「来来回回的跑动」）。
        //   ② <b>挡路的岩浆用垫脚方块铺过去</b>（见 {@link #tickLavaBridging}）：脚层 / 脚底 / 头顶三层里、
        //      手长范围内的岩浆格直接填实（填的是岩浆自己那一格），铺完 Baritone
        //      直接走新路，不再贴边绕。不同层（脚底以下的岩浆湖 / 头顶）不挡路就不碰——「静止的岩浆不管，
        //      能通行就行」。
        if (lavaEscapeCooldown > 0) lavaEscapeCooldown--;
        if (bridgeCooldown > 0) bridgeCooldown--;

        // 垫脚推进最优先：铺路期间其它挖矿逻辑全停（放置与秒破换工具共用主手，不能并行）
        if (lavaBridging) {
            tickLavaBridging();
            return;
        }

        int lavaRadius = Math.max(1, module.settings().lavaAvoidRadius);
        boolean lavaDanger = mc.player.isInLava() || mc.player.getRemainingFireTicks() > 0;
        if (!lavaEscapeActive && lavaDanger) {
            module.getBaritone().stop();
            BlockPos safe = findNearestSafeSpot(lavaRadius);
            if (safe != null) {
                var baritone = module.getBaritone().getBaritoneInstance();
                if (baritone != null) {
                    baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(safe));
                    lavaEscapeActive = true;
                    lavaEscapeTicks = 0;
                    module.info("§e⚠ 已接触岩浆/着火 §8▸ 寻路到安全位置");
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

        // 岩浆脱困推进：脱离接触（1 格内无岩浆）即恢复挖矿；超时仍未脱困则 RTP 兜底（旧 :576-598）。
        // 完成判据用 1 格而不是避险半径：撤离的目的是「别再被烧」，不是「离岩浆远远的」——
        // 站在 1 格外本来就不烧，再远就是白跑（用户 2026-09-18：「来来回回的跑动」的一半成因）
        if (lavaEscapeActive) {
            lavaEscapeTicks++;
            if (!hasLavaNear(1) && !mc.player.isInLava() && mc.player.getRemainingFireTicks() <= 0) {
                lavaEscapeActive = false;
                lavaEscapeTicks = 0;
                lavaEscapeCooldown = LAVA_ESCAPE_COOLDOWN_TICKS;
                module.getBaritone().stop();
                module.info("§a✓ 已远离岩浆 §8▸ 继续挖矿");
                // 恢复挖矿当刻就把主手换回镐：撤离路上可能为挖开挡路方块换了方块/工具
                ensureMiningToolInHand();
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

        // 垫脚触发：脚层 / 脚底两条线上出现挡路的岩浆格 → 顺手把它填实（用户 2026-09-19：「边走边放」——
        // 不再停 Baritone，人继续沿路径走，我们每刻把够得着的岩浆格填上）。
        // 两处避让：连锁进行中不触发、秒破正在挖某一块时不触发——三者都要写主手（连锁的换镐、
        // 秒破的换镐、垫脚的换方块），跨刻互顶的结果是「方块放不下去 / 墙挖不动」
        if (!lavaEscapeActive && bridgeCooldown <= 0 && stateTick % 10 == 0
            && !module.getVeinMiner().isActive()
            && !MiningFastBreakController.instance().isActive()
            && findNextBridgeTarget() != null) {
            lavaBridging = true;
            bridgeTicks = 0;
            bridgeIdleTicks = 0;
            bridgePlaced = 0;
            bridgeBlacklist.clear();
            module.info("§b垫脚过岩浆 ▸ 开始铺设通路");
            return;
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
     * 坚守者预警逃离：播报 + 走「前往野外」那条既有传送链路（本项目新增，用户 2026-09-18 需求）。
     *
     * <p><b>触发时机</b>：{@link WardenWarningGuard} 在幽匿尖啸体开始尖叫（且该尖啸体会召唤坚守者）时
     * 命中，此刻距坚守者钻出地面还有 90 刻（4.5 秒）。用户明确要求在「准备复活」阶段就动手——
     * 等它出来就跑不掉了。第二种警报是「坚守者已经在场」的兜底（尖啸漏检 / 别人叫出来的），
     * 同样立刻走，只是播报说实话。</p>
     *
     * <p><b>范围口径</b>：玩家自身附近 15×15 格（{@link WardenWarningGuard#SCAN_SIZE}，
     * 用户 2026-09-18：「32太大了」）。</p>
     *
     * <p><b>为什么切 GO_WILD 而不是另发一条指令</b>：GO_WILD 就是「前往矿区」那条链路本身
     * （{@code executeOwnTeleport(module.getWildCommand(), true)}，含 RTP 弹窗自动点选与
     * 我方传送宽限窗），落地后照常进 MINING 继续挖：逃离与正常换区是同一条路，不另造第二套
     * 指令发送逻辑（开发习惯第 169 条：同源逻辑只留一份）。</p>
     *
     * <p><b>两个必须显式处理的点：</b></p>
     * <ul>
     *   <li>{@code teleportCooldownTicks} 清零：那是「传送没生效、等服务器冷却后重试」用的等待窗，
     *       逃离是救命动作，不能因为上一轮留下的冷却在原地干等几十秒；</li>
     *   <li>先 {@code baritone.stop()}：从 COMBAT / 物流态切走时 {@code onStateExit} 不会停寻路
     *       （只有离开 MINING 才停），不停就会出现「一边打架一边被传送」。</li>
     * </ul>
     *
     * <p><b>播报用 {@code error} 而不是 {@code info/warning}</b>：后两者会被「状态播报」开关静默
     * （见 {@code AutoMinerModule#broadcastBlocked} 的三道闸门），而这是安全告警，必须让玩家看到。</p>
     *
     * <p><b>两种状态不触发</b>：已经在 GO_WILD（正逃离，重复触发无意义）、死亡流程里
     * （DEATH_HANDLING / RESPAWN_WAIT，人已经死了，跑不掉也不必跑）。</p>
     *
     * @param alarm 警报类型：尖啸（准备出现）还是坚守者已在场
     * @return true 表示已切走状态（调用方结束本刻）
     */
    private boolean escapeFromWardenWarning(WardenWarningGuard.Alarm alarm) {
        if (state == MinerState.GO_WILD || state == MinerState.DEATH_HANDLING || state == MinerState.RESPAWN_WAIT) {
            return false;
        }
        module.error(alarm == WardenWarningGuard.Alarm.SUMMONING
            ? "§6⚠ 附近有坚守者准备复活 §8▸ 立刻传送逃离"
            : "§6⚠ 附近已有坚守者 §8▸ 立刻传送逃离");
        teleportCooldownTicks = 0;
        module.getBaritone().stop();
        transitionTo(MinerState.GO_WILD);
        return true;
    }

    /**
     * 手动传送检测（用户 2026-09-17 需求：「挖矿状态下输入了 /home 回家，模块还在继续挖」；
     * 2026-09-18 补宽限窗）。
     *
     * <p>判据：玩家位置在<b>单刻内</b>跳变超过 {@link #MANUAL_TELEPORT_DISTANCE_SQR}（32 格）。
     * 正常移动/跌落单刻最多几格，只有服务器传送类指令（/home、/spawn、/tpa…）能达到这个量级。</p>
     *
     * <p>基准位置每刻都更新，<b>所有状态都判定</b>（死亡/重生流程除外：重生本身就会跳变，
     * 且返回流程有它自己发的传送指令）。卸货 / 补给 / 修补三态我们自己也会发传送指令，那些跳变
     * 由 {@link #ownTeleportPendingTicks} / {@link #ownTeleportSettleTicks} 认领掉，不会误判。</p>
     *
     * <p><b>用户 2026-09-18 拍板「换一种办法检测」</b>：不依赖指令内容，就看<b>位置瞬移了多远</b>
     * ——「玩家突然瞬移到多少格去了……一般回家都隔得很远」。玩家敲的指令（{@link #onPlayerCommand}）
     * 只是把阈值降到 8 格、把判定放开得更早，两者走<b>同一条处置</b>：
     * 非我方原因的位置跳变 → <b>暂停挖矿</b>（不在这里挖、也不去别处挖，避免在家 / 出生点 / 别人基地凿）。</p>
     *
     * <p>因此菜单、GUI 点击、插件触发这些<b>没有指令线索</b>的传送同样会被抓住——这正是只靠指令
     * 事件做不到的那一半（第 86 号报告 7.4）。</p>
     *
     * @return true 表示本刻命中并已处理（调用方直接结束本刻）
     */
    private boolean manualTeleportDetected() {
        if (mc.player == null) return false;
        BlockPos current = mc.player.blockPosition();

        boolean playerCommandWindow = mc.player.tickCount - playerCommandTick <= PLAYER_COMMAND_WINDOW_TICKS;
        // 死亡流程里不判：重生本身就会跳变，返回流程有它自己发的传送指令
        boolean deathFlow = state == MinerState.DEATH_HANDLING || state == MinerState.RESPAWN_WAIT;

        double distanceSqr = lastWatchPos == null ? 0.0 : current.distSqr(lastWatchPos);
        // 玩家指令窗口内阈值放低：要确认的只是「这条指令挪没挪动位置」，几格的瞬移也算
        double threshold = playerCommandWindow ? PLAYER_COMMAND_DISTANCE_SQR : MANUAL_TELEPORT_DISTANCE_SQR;
        boolean jumped = lastWatchPos != null && !deathFlow && distanceSqr > threshold;

        // GO_WILD 判「服务器有没有真把人挪走」只看单刻瞬移，不看「离起点几格」：
        // 服务器 RTP 先提示「請勿移動」再传送，玩家在这几秒里自己走动同样会让位置变化，
        // 旧判据（离起点 > 2 格即算传送生效）会把走动当成功 —— 人还在原地就进 MINING 就地挖矿
        // （用户 2026-09-19 在首次启动路径实测）。
        teleportJumpThisTick = lastWatchPos != null && !deathFlow
            && distanceSqr > TELEPORT_JUMP_DISTANCE_SQR;

        // 这一跳算不算「我方传送」：
        // · 正在等自己落地（pending）——一律认领，玩家指令窗口内也认领。RTP 插件是<b>两段传送</b>
        //   （「請勿移動」几秒后才是「你已被隨機傳送」），第一段被 pending 收掉后进 MINING，
        //   第二段落在 MINING 里；玩家这时要是敲了句 /w，不认领就会白停一次（2026-09-18 实机）。
        // · 落地后的修正窗（settle）——玩家指令窗口内<b>不</b>认领：那一跳是你的传送，
        //   吞掉就回到「在家里继续挖」（第 86 号 7.4 实测）。窗口外照旧认领（落地位置修正、
        //   插件第二段传送都发生在这里）。
        boolean ownLanding = jumped
            && (ownTeleportPendingTicks > 0
                || (!playerCommandWindow && ownTeleportSettleTicks > 0));
        if (ownLanding) {
            ownTeleportPendingTicks = 0;
            ownTeleportSettleTicks = OWN_TELEPORT_SETTLE_TICKS;
            lastWatchPos = current;
            return false;
        }

        if (ownTeleportPendingTicks > 0) ownTeleportPendingTicks--;
        if (ownTeleportSettleTicks > 0) ownTeleportSettleTicks--;

        lastWatchPos = current;
        if (!jumped) return false;

        // 窗口只认一次：处理完立刻关掉，免得我们随后自己那一跳又被算成玩家的
        playerCommandTick = -100000;

        module.getSoundNotifier().notifyStuck();
        module.getBaritone().stop();
        // 用户 2026-09-18 口径：不管有没有指令线索，只要不是我们自己传的，就暂停挖矿
        // （此前无指令线索的走「重新前往野外挖矿」= 第 72 号裁定；用户要求改成按位置判之后，
        //  两条路合并：在人家家里原地挖和把人家传走都不对，「停」才是他要的）
        module.error(playerCommandWindow
            ? "§c✗ 检测到手动指令 §8/" + playerCommandText + " §8▸ 已暂停挖矿（避免在新位置继续挖）"
            : "§c✗ 检测到传送 §8▸ 已暂停挖矿（避免在新位置继续挖）");
        if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
        return true;
    }

    /**
     * 记录玩家自己敲了一条服务器指令（由模块从 {@code ClientEventType#CLIENT_COMMAND} 事件转来）。
     *
     * <p>只记录、不当场动作（用户 2026-09-18 口径「按结果判」）：指令本身不是判据，
     * 真正决定的是它有没有把位置挪走——{@code /w}、{@code /msg}、{@code /ping} 这类不动的指令
     * 不打断挖矿。有了它，这条线索的阈值能从 32 格降到 8 格、并且当场就能认账
     * （不必等同状态那 32 格才反应）。</p>
     *
     * @param command 指令原文（不含前导斜杠），仅用于播报
     */
    public void onPlayerCommand(String command) {
        if (mc.player == null) return;
        playerCommandText = command;
        playerCommandTick = mc.player.tickCount;
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

    // ── 岩浆垫脚（用户 2026-09-18：挡路的岩浆铺过去，不撤离） ──────────────────────

    /**
     * 垫脚铺设每刻推进：<b>一次刻连放一批</b>（最多 {@link #BRIDGE_PLACES_PER_TICK} 块），
     * 铺完（或超时 / 出事）收尾（见 {@link #finishBridging}）。
     *
     * <p><b>边走边放</b>（用户 2026-09-19：「放岩浆的时候会罚站放，应该边走边放吧」）：本轮<b>不停
     * Baritone</b>，人沿路径继续走，我们每刻把够得着的岩浆格填上——旧实现是「停下站着一块一块铺」。
     * 放置链路复用 {@link BlockPlacer#placeAt}（与连锁封堵同一份），每块都<b>重新挑目标</b>：
     * 人在走动、本地又立刻落块，"最近的岩浆格"每刻都在变，重新挑才跟得上
     * （用户 2026-09-19：「一次性铺满，跟投影打印机那种」）。放不进的格子进 {@link #bridgeBlacklist}
     * 本轮不再试；连续 {@link #BRIDGE_IDLE_DONE_TICKS} 刻没有新目标即视为铺完（岩浆流动 / 玩家挪动
     * 会带出新目标，空窗只是暂时的）；一轮最多 {@link #BRIDGE_MAX_PLACES_PER_ROUND} 块
     * （「顺便封」的份额，见该常量注释），总时长 {@link #BRIDGE_MAX_TICKS} 封顶。
     * 铺设途中掉进岩浆 / 着火：立刻收摊交给撤离分支。</p>
     */
    private void tickLavaBridging() {
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) {
            lavaBridging = false;
            return;
        }
        bridgeTicks++;
        if (bridgeTicks > BRIDGE_MAX_TICKS) {
            finishBridging(false);
            return;
        }
        // 铺路途中出事：不硬铺，收摊后由上面的 lavaDanger 分支撤离
        if (player.isInLava() || player.getRemainingFireTicks() > 0) {
            finishBridging(false);
            return;
        }

        int placedThisTick = 0;
        while (placedThisTick < BRIDGE_PLACES_PER_TICK) {
            BlockPos target = findNextBridgeTarget();
            if (target == null) break; // 手长范围内没有可填的岩浆了：本刻收手，由空窗判定是否铺完
            if (BlockPlacer.placeAt(mc, player, module.settings().placeBlocks, target)) {
                placedThisTick++;
                bridgeIdleTicks = 0;
                // 一轮的份额用完就收工回挖矿（人往前走会带出下一轮，见 BRIDGE_MAX_PLACES_PER_ROUND 注释）
                if (++bridgePlaced >= BRIDGE_MAX_PLACES_PER_ROUND) {
                    finishBridging(true);
                    return;
                }
                continue;
            }
            // 放不进（没锚点 / 没方块 / 目标格不可替换）：黑名单这块，别在同一格打转；本刻收手，下一刻再说
            bridgeBlacklist.add(target);
            if (!bridgeNoBlockWarned
                && !BlockPlacer.hasPlaceBlock(player, module.settings().placeBlocks)) {
                bridgeNoBlockWarned = true;
                module.warning("§e⚠ 垫脚过岩浆缺方块 §8▸ 搭路方块白名单里的一种都没带，建议带上圆石");
            }
            break;
        }

        if (placedThisTick == 0) {
            bridgeIdleTicks++;
            if (bridgeIdleTicks >= BRIDGE_IDLE_DONE_TICKS) finishBridging(true);
        }
    }

    /**
     * 下一个要封的岩浆格：<b>脚底 / 脚层 / 头顶三层</b>里、<b>玩家手长范围内</b>的岩浆格，
     * 取离玩家最近的（从脚边往外推进，一块贴一块铺）。
     *
     * <p><b>距离判据 = 原版手长</b>（用户 2026-09-18：「堵岩浆加一下距离把玩家原版默认最大手长就行了」）：
     * {@code isWithinBlockInteractionRange(pos, 1.0)}，与 26.1.2 服务端接受放置请求用的是同一条判据
     * （{@code ServerGamePacketListenerImpl#handleUseItemOn} → 手长属性 {@code BLOCK_INTERACTION_RANGE}
     * 默认 4.5，+1 缓冲）。扫描循环只圈候选（{@link #BRIDGE_SCAN_RADIUS}），能被真正封的就是手够得到的那些。</p>
     *
     * <p><b>填的是岩浆自己那一格</b>（用户 2026-09-18：「并没有直接放在流体岩浆那一格，而是放在流体岩浆
     * 上面了」——上一版算的是 {@code lava.above()}，等于把方块放进玩家身体空间：既不挡岩浆也走不了路）。
     * 三层各有各的用处，都在「玩家已经走到的地方」，不专门寻路过去封：</p>
     * <ol>
     *   <li><b>脚底同层</b>（池子 / 洞）：填完地面与脚底齐平，直接走过去；</li>
     *   <li><b>脚层</b>（流水漫到脚面，或墙体里的岩浆源）：填完是一级台阶 / 把墙面源头堵住，
     *       Baritone 跳得上去，也不再有新岩浆往路上流；</li>
     *   <li><b>头顶层</b>（用户 2026-09-18：「堵上上面静止的那一层岩浆」）：填掉上方那层静止源，
     *       走过时不会被浇下来——头顶淋一滴就是一身火。</li>
     * </ol>
     *
     * <p>填之前要求「填完上方到玩家头顶这一路仍然通得过」（见 {@link #isPassableAfterFill}）：
     * 上方被实体方块堵死的格子填了也走不了，跳过；头顶层本身不再要求——那里是「顺便堵源头」，
     * 墙上嵌着的岩浆源上方多半是岩体，要求过头就一个都封不住。</p>
     */
    private BlockPos findNextBridgeTarget() {
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) return null;
        BlockPos feet = player.blockPosition();
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;
        // dy 由低到高遍历：距离相同时先取下层（通路格优先于头顶格）——`<` 比较让先遍历到的留下
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -BRIDGE_SCAN_RADIUS; dx <= BRIDGE_SCAN_RADIUS; dx++) {
                for (int dz = -BRIDGE_SCAN_RADIUS; dz <= BRIDGE_SCAN_RADIUS; dz++) {
                    BlockPos pos = feet.offset(dx, dy, dz);
                    if (!isLavaAt(pos) || bridgeBlacklist.contains(pos)) continue;
                    if (!player.isWithinBlockInteractionRange(pos, 1.0)) continue;
                    if (!isPassableAfterFill(pos, feet)) continue;
                    double dist = pos.distSqr(feet);
                    if (dist < bestDist) {
                        bestDist = dist;
                        best = pos;
                    }
                }
            }
        }
        return best;
    }

    /** 该格是不是岩浆（源与流动都算——挖开后都会流，也都挡路） */
    private boolean isLavaAt(BlockPos pos) {
        return mc.level != null && mc.level.getBlockState(pos).getBlock() == Blocks.LAVA;
    }

    /** 该格能不能通行 / 能不能填：空气或流体（流体可替换，填进去就把那一格变成实体地面） */
    private boolean isOpenAt(BlockPos pos) {
        if (mc.level == null) return false;
        BlockState state = mc.level.getBlockState(pos);
        return state.isAir() || state.canBeReplaced();
    }

    /**
     * 把 {@code pos} 填实之后，它上方到玩家头顶这一路是否仍然通得过。
     *
     * <p>头顶层（{@code pos} 已在玩家头顶）没有更上方要检查，直接放行——那是顺手堵源头，
     * 见 {@link #findNextBridgeTarget()} 的说明。</p>
     */
    private boolean isPassableAfterFill(BlockPos pos, BlockPos feet) {
        int headY = feet.getY() + 1;
        for (int y = pos.getY() + 1; y <= headY; y++) {
            if (!isOpenAt(new BlockPos(pos.getX(), y, pos.getZ()))) return false;
        }
        return true;
    }

    /**
     * 一轮垫脚收尾。
     *
     * <p><b>不再重启 mine</b>：边走边放之后本轮从没停过 Baritone（旧实现进铺设前先 {@code stop}，
     * 收尾才要把它拉回来），它的 mine 一直在跑，重发一次等于白重启。</p>
     *
     * <p><b>换回镐子放在最前面，出事时也要换</b>（用户 2026-09-19：「搭完路不切换回镐子，手里还拿着
     * 搭路的方块，然后卡死状态机」）：旧实现把 {@link #ensureMiningToolInHand()} 放在「出事就 return」
     * 之后，而踩进岩浆 / 着火恰恰是最常见的收尾方式（铺路铺到脚边就是岩浆），于是那一条路径上主手
     * 一直留着方块——方块破坏速度约 210 刻（镐约 7 刻），撤离要挖挡路的方块时挖不动、回到挖矿也挖不动，
     * 表现就是「手里拿着搭路方块 + 状态机卡死」。出事时更需要手上是镐：撤离往往要先挖开一条路。</p>
     */
    private void finishBridging(boolean done) {
        LocalPlayer player = mc.player;
        boolean urgent = player != null && (player.isInLava() || player.getRemainingFireTicks() > 0);
        lavaBridging = false;
        bridgeBlacklist.clear();
        // 看门狗计数必须清掉：否则铺几轮就攒到 3 次，直接触发「附近目标矿已挖完 ▸ 重新前往野外」的误换区
        mineRestartCount = 0;
        ensureMiningToolInHand();
        if (urgent) return;
        bridgeCooldown = BRIDGE_ROUND_COOLDOWN_TICKS;
        module.info(done
            ? "§a✓ 垫脚完成 §8▸ 继续挖矿"
            : "§e⚠ 垫脚中止 §8▸ 交回挖掘流程");
    }

    /**
     * 主手还留着垫脚方块时换回镐子。
     *
     * <p>用户 2026-09-18：「垫完脚的方块 老拿手上 能不能切回镐子 因为有时候会拿着踮脚方块 挖矿进入状态机」。
     * {@link BlockPlacer} 自己每次放完都会同刻还原槽位，但<b>「上一槽本来就是方块」这一种还原不掉</b>
     * （还原目标就是方块本身），Baritone 自己搭桥/垫脚时也会把方块留在手上——主手破坏速度决定服务端
     * 0.7 判定所需刻数（青金石矿配镐约 7 刻，拿方块约 210 刻），表现为「卡住不动」。</p>
     *
     * <p>判据收得很窄：<b>只在手上拿着方块类物品时才动手</b>（换成快捷栏里第一把镐）；手上不是方块
     * （剑 / 斧 / 空手 / 别的镐）一律不碰——那些由战斗的 {@code restoreHotbar} 与秒破的
     * {@code ensureFasterTool} 各自负责，多写一手就会互顶。</p>
     *
     * <p><b>为什么放宽到「任意方块」而不是只认搭路白名单</b>（用户 2026-09-19：「搭完路不切换回镐子，
     * 手里还拿着搭路的方块，然后卡死状态机」，且是重犯）：残留的方块不一定是白名单里的那种——
     * 白名单比对只在「玩家自己配的搭路方块」上成立，而 Baritone 自己搭桥 / 垫脚用的是<b>它的</b>投放方块
     * （与挖矿白名单无关），上一槽本来就是方块时 {@link BlockPlacer} 也还原不掉。窄判据补不到这两种，
     * 一旦留在手上，破坏速度约 210 刻（镐约 7 刻），表现就是「卡住不动」。</p>
     */
    private void ensureMiningToolInHand() {
        LocalPlayer player = mc.player;
        if (player == null) return;
        var inventory = player.getInventory();

        // 手上这把镐不能用（不符合当前采集模式，或没有经验修补且只剩 1 点耐久）→ 换成快捷栏里
        // 最合适的那把（模式对得上、剩余耐久最多），别把它挖爆、也别拿时运镐去挖精准模式的目标
        // （用户 2026-09-21）。快捷栏里没有合适的就什么都不做 —— 停机与否交给优先级 2.0 的单一判据，
        // 避免同一件事两处下结论。
        ItemStack selected = inventory.getSelectedItem();
        if (isPickaxe(selected) && !isReadyPickaxe(selected)) {
            int best = findBestHotbarReadyPickaxe();
            if (best >= 0) {
                selectHotbar(best);
                return;
            }
        }

        // 主手不是方块（Baritone 会认成「已经拿着挖矿工具」）：切到快捷栏里最合适的那把镐。
        // 这里同样只认「能用」的镐 —— 拿一把不符合模式的镐去挖，等于产出直接错。
        if (Block.byItem(inventory.getSelectedItem().getItem()) == Blocks.AIR) return;
        int best = findBestHotbarReadyPickaxe();
        if (best >= 0) selectHotbar(best);
    }

    /**
     * 快捷栏（0~8）里最合适的一把镐：{@link #isReadyPickaxe(ItemStack)} 通过，取剩余耐久最多的
     * （带经验修补的视为无限，见 {@link #sortableRemaining(ItemStack)}）。没有返回 -1。
     */
    private int findBestHotbarReadyPickaxe() {
        if (mc.player == null) return -1;
        var inventory = mc.player.getInventory();
        int best = -1;
        int bestRemaining = USABLE_DURABILITY_FLOOR;
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (!isReadyPickaxe(stack)) continue;
            int remaining = sortableRemaining(stack);
            if (remaining > bestRemaining) {
                bestRemaining = remaining;
                best = slot;
            }
        }
        return best;
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
            // 脚边的同样不认领：再走一步就自动吸到，专门为它规划一次路径纯属白跑
            if (item.distanceToSqr(mc.player) <= PICKUP_IGNORE_RADIUS_SQR) continue;
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
     *
     * <p><b>一趟捡完（用户 2026-09-18：「偶尔会漏检掉落物 然后白跑一趟 回去捡起来 寻路 加强一下拾取逻辑」）</b>：
     * 旧实现一次只认一个目标、捡完就冷却是 10 秒，漏了三个就要来回跑三趟（每趟都得停 mine 再重启）。
     * 现在一轮之内<b>接着捡</b>：捡到一个立刻在同一趟里找下一个（{@link #beginPickup} 延续目标不走冷却），
     * 只有一个都找不到了才 {@link #endPickupRound()} 收工回挖矿；扫不到的角落（够不到/太远）也是
     * 「先看附近还有没有别的」再收工。半径同时放宽到 {@link #PICKUP_RADIUS}。</p>
     *
     * <p><b>加速拾取（用户 2026-09-18：「我说 提升一下捡东西的速度 逻辑算法 加速拾取」）</b>：上一版
     * 的慢不在寻路，而在「发现得太晚」——认领门槛 5 秒、一轮收工冷却 10 秒、够不到空等 2 秒，等它被
     * 认领时人早被挖矿带出 {@link #PICKUP_RADIUS}，只能回头跑一趟。本轮<b>只收节奏不改判据</b>：
     * 门槛收到 {@code PICKUP_MIN_AGE_TICKS}、冷却收到 {@code PICKUP_COOLDOWN_TICKS}、够不到判定收到
     * {@code PICKUP_PATH_FAIL_TICKS}、扫描间隔 10 刻收到 5 刻；并把脚边 {@code PICKUP_IGNORE_RADIUS_SQR}
     * 内的掉落物排除出认领名单——它本来就靠自然吸拾，专门停一次 mine 反而退化成「挖一下停一下」。</p>
     */
    private boolean tryPickupNearbyOre() {
        if (mc.player == null || mc.level == null) return false;
        // 背包已满时不捡，否则捡不起来反而卡住（掉落物一直存在→反复寻路→原地打转）
        if (countOreStacks() >= module.getUnloadThreshold()) return false;
        if (pickupCooldownTicks > 0) pickupCooldownTicks--;

        if (pickupTarget == null) {
            if (pickupCooldownTicks > 0) return false;
            // 每 5 tick 扫一次：漏捡物必须在被带出 PICKUP_RADIUS 之前认领，扫得太稀就等于白跑
            if (stateTick % 5 != 0) return false;
            ItemEntity drop = findNearbyOreDrop(PICKUP_RADIUS);
            if (drop == null) return false;
            beginPickup(drop);
            return true;
        }

        // 已有捡取目标：消失/超时/太远/够不到 → 收尾
        pickupTimeout++;
        boolean picked = pickupTarget.isRemoved() || !pickupTarget.isAlive();
        if (picked) {
            // 捡到了：同一趟接着捡附近剩下的漏捡物，别为下一个再停一次 mine
            ItemEntity next = findNearbyOreDrop(PICKUP_RADIUS);
            if (next != null) {
                beginPickup(next);
                return true;
            }
            endPickupRound();
            return false;
        }

        // 已过 2 秒却连寻路进程都不在（Baritone 算不出路径或已放弃）＝ 掉落物卡在够不到的地方
        boolean unreachable = pickupTimeout > PICKUP_PATH_FAIL_TICKS
            && !module.getBaritone().isCustomGoalActive();
        boolean tooFar = pickupTarget.distanceTo(mc.player) > 16;
        if (unreachable || tooFar || pickupTimeout > 300) {
            // 够不到/超时/太远说明这位置捡不起来，记黑名单避免反复寻路捡同一个
            addPickupBlacklist(pickupTarget.blockPosition());
            // 这一个够不到不代表别处也没有：先看附近还有没有能捡的，有就接着捡，别白跑一趟就收工
            ItemEntity next = findNearbyOreDrop(PICKUP_RADIUS);
            if (next != null) {
                beginPickup(next);
                return true;
            }
            endPickupRound();
            return false;
        }
        return true;
    }

    /** 选定掉落物并下发寻路：捡取轮次里的延续目标不走冷却（见 {@link #tryPickupNearbyOre} 注释） */
    private void beginPickup(ItemEntity drop) {
        pickupTarget = drop;
        pickupTimeout = 0;
        module.getBaritone().stop();
        var baritone = module.getBaritone().getBaritoneInstance();
        if (baritone != null) {
            baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(drop.blockPosition()));
        }
    }

    /** 一轮捡取收工：恢复挖矿并进入冷却（避免刚挖出来的新掉落物立刻把我们拽回来） */
    private void endPickupRound() {
        pickupTarget = null;
        pickupCooldownTicks = PICKUP_COOLDOWN_TICKS;
        module.getBaritone().stop();
        module.getBaritone().startMining(module.getMiningTargets());
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

        // 找出耐久最低的可修复工具（镐/铲/斧/锄/剑，含副手）；与修复触发同一套判定，
        // 只预警带经验修补、真正修得回来的工具（用户 2026-09-19）
        ItemStack lowest = ItemStack.EMPTY;
        int lowestRemaining = Integer.MAX_VALUE;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!isRepairableTool(stack)) continue;
            // 无经验修补的工具修不了，不会触发修复流程，不进本预警（用户 2026-09-19；
            // 「修不了」的提示由 handleUnrepairableTool 负责，避免同一件工具出现两套口径）
            if (!ItemIdentifier.hasMending(stack)) continue;
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
        if (isRepairableTool(offhand) && ItemIdentifier.hasMending(offhand)) {
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

        // 玩家自己开着容器界面（背包 / 创造背包）时只等不做（用户 2026-09-19 统一口径：
        // 静默容器只在没有玩家界面时跑）。往下走会在玩家看背包时静默开箱，把 containerMenu
        // 悄悄换成箱子菜单，玩家背包里的点击就按箱子的 containerId 发出去（错位、丢物品）。
        // 我方开箱的界面一律被 SCREEN_OPEN 拦掉，所以这里能看到的容器界面就是玩家自己的。
        if (mc.screen instanceof AbstractContainerScreen<?>) return;
        if (mc.screen != null && !(mc.screen instanceof PauseScreen)) {
            // 游戏菜单 / 控制台等非容器界面：没必要占着容器，周期性收掉，但不打断流程
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

    // ═══════════════════════════════════════════════════════════════════
    //  自用模式：出售流程四态（用户 2026-09-20）
    //
    //  挖满触发组数（或背包先满）→ SELL_TRAVEL → SELL_PATH → SELL_TRADE → SELL_RETURN → GO_WILD。
    //  四态与卸货 / 补给同构：每态一个 tick 方法 + 显式阶段号 + 「等超时 → 重试 → 用完停机」，
    //  底层动作（读菜单 / 点槽位 / 找 NPC）在 MiningPersonalSell。
    //
    //  两条贯穿全程的约定：
    //    · 全程静默：只读 player.containerMenu 的槽位，界面不弹不抢鼠标（模块侧门控见具体实现）；
    //    · 全程按「我方传送」认领位置跳变：回城 / 回子服都是点菜单触发的、没有指令线索，
    //      不认领就会被手动传送检测判成「玩家把号传走了」而直接停机。
    // ═══════════════════════════════════════════════════════════════════

    /** 是否处于自用模式出售链的任意一态（物流口径与静默门控共用一处判据） */
    private static boolean isSellState(MinerState s) {
        return s == MinerState.SELL_TRAVEL || s == MinerState.SELL_PATH
            || s == MinerState.SELL_TRADE || s == MinerState.SELL_RETURN;
    }

    /** 自用模式：背包里「出售物品」是否已达触发组数（组数 = 总数 / 64，与卸货口径一致） */
    private boolean personalSellReady() {
        String target = module.sellItemFilter();
        if (target == null || target.isBlank()) return false;
        return personalSell.countTargetInBag() / 64 >= module.settings().personalSellStacks;
    }

    /** 自用模式：背包里出售物品的组数（进入出售流程时播报带上「带了多少组」） */
    private int countPersonalOre() {
        return personalSell.countTargetInBag() / 64;
    }

    /** 出售链播报里的物品名：取目标在当前采集模式下的产物显示名，取不到（目标没选）退回目标矿名 */
    private String sellItemName(String fallback) {
        String name = module.getSellItemDisplayName();
        return name.isEmpty() ? fallback : name;
    }

    /** 单步推进结果：继续等 / 已重试（调用方回到本步起点）/ 已停机（调用方直接 return） */
    private enum SellStep { CONTINUE, RETRY, ABORT }

    /**
     * 单步超时判定与重试（服务器卡顿时靠它原地重试，而不是傻等或往下瞎走）。
     *
     * <p>判据只看「本步已经等了多久」：每步推进时 {@code sellStepTicks} 清零、每次重试时也清零，
     * 所以同一个超时窗口对每一步都成立。</p>
     *
     * @return {@link SellStep#CONTINUE} 继续等；{@link SellStep#RETRY} 已重试（调用方回本步起点）；
     *         {@link SellStep#ABORT} 重试用完已停机（调用方必须立即 return）
     */
    private SellStep sellStepOutcome(String actionName) {
        if (sellStepTicks < personalSell.stepTimeoutTicks()) return SellStep.CONTINUE;
        // 把卡住那一刻的菜单原样写进日志（哪一格叫什么、lore 写了什么）：只有它能说明
        // 「是按钮名字与配置对不上，还是这一屏压根不是流程以为的那一屏」
        MINING_LOG.info("[卖矿流程] {} 未响应，当前菜单：{}", actionName, personalSell.menuDump());
        int limit = Math.min(personalSell.stepRetries(), SELL_RETRY_HARD_LIMIT);
        if (sellStepRetries < limit) {
            sellStepRetries++;
            sellStepTicks = 0;
            module.warning("§e⚠ " + actionName + "未响应 §8▸ 重试 " + sellStepRetries + "/" + limit);
            return SellStep.RETRY;
        }
        return sellAbort(actionName);
    }

    /** 出售流程单步彻底失败：停机 + 播报（绝不带着一身矿继续往下走） */
    private SellStep sellAbort(String actionName) {
        module.getBaritone().stop();
        module.error("§c✗ " + actionName + "失败 §8▸ 出售流程中断，自动挖矿已停止"
            + "（可在控制台调大「单步超时 / 单步重试次数」）");
        if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
        return SellStep.ABORT;
    }

    /** 菜单状态号：服务端每处理完一次点击就 +1；-1 = 菜单没开（判「这一次点击生效了没有」） */
    private int sellMenuStateId() {
        var menu = personalSell.menu();
        return menu == null ? -1 : menu.getStateId();
    }

    /** 当前商店菜单的容器号（没开菜单给 {@code -1}）；用来发现「商店卖完一轮换了新一屏」 */
    private int sellMenuContainerId() {
        var menu = personalSell.menu();
        return menu == null ? -1 : menu.containerId;
    }

    /** 重发「寻路到收购 NPC 前面那一格」（GoalNear 1：站在它前面一格或紧邻格，都在交互距离内） */
    private void repathNpc(BlockPos pos) {
        var baritone = module.getBaritone().getBaritoneInstance();
        if (baritone != null) baritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(pos, 1));
    }

    /**
     * 玩家到收购 NPC 实体的距离平方；实体没扫到时返回 {@code -1}。
     *
     * <p>距离只算这一处：既判「到了没有 / 要不要走回去」，也写进日志 —— 下次再出现「隔着好远」时
     * 照着日志里的数字看即可，不用再猜。</p>
     */
    private double npcDistanceSqr() {
        if (mc.player == null) return -1;
        var npc = personalSell.findNpc();
        if (npc == null) return -1;
        return mc.player.getEyePosition().distanceToSqr(npc.position());
    }

    /** 距离平方的日志文本（保留一位小数；扫不到实体给 {@code ?}） */
    private static String distanceText(double distSqr) {
        return distSqr < 0 ? "?" : String.valueOf(Math.round(Math.sqrt(distSqr) * 10.0) / 10.0);
    }

    /**
     * 是否真的站到了收购 NPC 身旁（能右键到它，且商店插件认这个距离）。
     *
     * <p>{@code findNpc()} 扫不到实体时<b>不再退回配置坐标</b>：那种情况就是「NPC 还没同步出来」，
     * 按坐标判「到了」会让流程站在一个没有 NPC 的地方开始点菜单（用户 2026-09-22「都没走到npc身边
     * 隔着好远」）。实体没扫到就继续寻路 —— 人真走到跟前，实体必然在客户端追踪范围内。</p>
     */
    private boolean nearNpc() {
        double distSqr = npcDistanceSqr();
        return distSqr >= 0 && distSqr <= NPC_ARRIVE_DISTANCE_SQR;
    }

    /**
     * 阶段：SELL_TRAVEL（回主城大厅 —— 收购 NPC 所在）。
     *
     * <p>阶段 0 发流程指令（{@code /cd}）→ 1 等菜单出现并点「返回主城」→ 2 等传送落地 →
     * 3 落地稳定（跨服传送后有位置修正 / 客户端世界重建）→ SELL_PATH。</p>
     */
    private void tickSellTravel() {
        if (stateTick == 1) {
            sellPhase = 0;
            sellStepTicks = 0;
            sellStepRetries = 0;
            personalSell.beginFlow();
        }
        // 玩家自己开着界面（背包 / 控制台）时只等不做：我方只读 containerMenu 的槽位，
        // 这时往下点会按错菜单的 containerId 发包
        if (mc.screen instanceof AbstractContainerScreen<?>) return;
        // 回城是点菜单触发的、没有指令线索：整个等待期持续按「我方传送」认领位置跳变
        ownTeleportPendingTicks = Math.max(ownTeleportPendingTicks, OWN_TELEPORT_PENDING_TICKS);

        sellStepTicks++;
        switch (sellPhase) {
            case 0 -> {
                module.getCmdManager().sendMenuCommand(module.settings().personalSellCommand);
                sellPhase = 1;
                sellStepTicks = 0;
            }
            case 1 -> {
                if (!personalSell.hasMenu()) {
                    if (sellStepOutcome("打开流程菜单") == SellStep.RETRY) sellPhase = 0;
                    return;
                }
                String keyword = module.settings().personalSellCityKeyword;
                if (!personalSell.clickKeyword(keyword)) {
                    if (sellStepOutcome("点击「" + keyword + "」") == SellStep.RETRY) sellPhase = 0;
                    return;
                }
                sellStepRetries = 0;
                sellPhase = 2;
                sellStepTicks = 0;
            }
            case 2 -> {
                if (!teleportJumpThisTick) {
                    if (sellStepOutcome("回主城传送") == SellStep.RETRY) sellPhase = 0; // 重开菜单再点一次
                    return;
                }
                sellStepRetries = 0;
                sellPhase = 3;
                sellStepTicks = 0;
            }
            case 3 -> {
                // 落地稳定：跨服传送后还有一次位置修正，紧接着开始寻路会按旧坐标算
                if (teleportJumpThisTick) { // 第二段跳变 → 重新计时
                    sellStepTicks = 0;
                    return;
                }
                if (sellStepTicks < SELL_LAND_SETTLE_TICKS) return;
                module.getContainer().closeContainer();
                transitionTo(MinerState.SELL_PATH);
            }
            default -> sellAbort("回主城");
        }
    }

    /**
     * 阶段：SELL_PATH（从主城落点寻路到收购 NPC <b>前面那一格</b>）。
     *
     * <p>目标格来自 {@link MiningPersonalSell#npcStandPos()}（NPC 正面朝向前方一格；实体没扫到时退回
     * 配置坐标）。<b>到位判据是「人真的贴到 NPC 身旁」</b>（{@link #NPC_ARRIVE_DISTANCE_SQR}，2.5 格内），
     * 不是「进了原版右键距离」—— 原版右键判据是实体 AABB 外扩 3.0，站在 3.5 格开外照样能戳开菜单，
     * 但商店插件点商品格时按更近的距离校验，于是整条链卡在「点商品 → 点全部」（用户 2026-09-22
     * 「都没走到npc身边 隔着好远」）。走不到就重发目标，连续两轮无接近直接停机（城里地形复杂，
     * 硬绕只会越走越远）。</p>
     */
    private void tickSellPath() {
        if (stateTick == 1) {
            sellPathIssued = false;
            sellTargetPos = null;
            sellLastDist = Double.MAX_VALUE;
            sellStallCount = 0;
            sellStepTicks = 0;
            sellStepRetries = 0;
            personalSell.beginFlow(); // 链路中途进入（异常重启）时也要保证静默门控是开的
        }
        if (mc.screen instanceof AbstractContainerScreen<?>) return;

        if (nearNpc()) {
            module.getBaritone().stop();
            var npc = personalSell.findNpc();
            MINING_LOG.info("[卖矿流程] 已走到收购 NPC 身旁：玩家 {} 距 {} {} 格（目标格 {}，配置坐标 {}）",
                mc.player.blockPosition(), npc == null ? "?" : npc.getName().getString(),
                distanceText(npcDistanceSqr()), sellTargetPos, personalSell.npcPos());
            transitionTo(MinerState.SELL_TRADE);
            return;
        }

        if (module.getBaritone().getBaritoneInstance() == null) {
            sellAbort("寻路到收购 NPC（Baritone 未加载）");
            return;
        }
        // 目标格按需刷新：落地那一刻实体表可能还没同步（当时会退回配置坐标），隔一秒再取一次真身朝向
        if (sellTargetPos == null || stateTick % SELL_TARGET_REFRESH_TICKS == 0) {
            sellTargetPos = personalSell.npcStandPos();
        }
        if (!sellPathIssued || (!module.getBaritone().isCustomGoalActive() && stateTick % 10 == 0)) {
            sellPathIssued = true;
            repathNpc(sellTargetPos);
        }
        if (sellPathStalled(sellTargetPos)) return;
        if (stateTick > PATH_TIMEOUT_TICKS) {
            module.getBaritone().stop();
            sellAbort("寻路到收购 NPC（超时）");
        }
    }

    /**
     * 收购 NPC 寻路的停滞监测（与卸货那套同口径：首次采样只记基准 → 无进展重发 → 再不行停机）。
     *
     * <p>单独写一条而不是复用 {@code logisticsStalled}：那一条的文案与脱困话术都按「容器」写死
     * （含「临时允许破坏挡路方块」这一级 —— 城里拆人家方块是事故），目标是坐标点时直接复用会串味。</p>
     */
    private boolean sellPathStalled(BlockPos target) {
        if (mc.player == null || target == null) return false;
        if (stateTick < LOGISTICS_SAMPLE_TICKS || stateTick % LOGISTICS_SAMPLE_TICKS != 0) return false;
        double dist = mc.player.blockPosition().distSqr(target);
        double last = sellLastDist;
        sellLastDist = dist;
        if (last == Double.MAX_VALUE) return false; // 首次采样没有可比的距离，只记基准
        if (last - dist >= LOGISTICS_MIN_APPROACH) {
            sellStallCount = 0;
            return false;
        }
        sellStallCount++;
        if (sellStallCount < 2) return false; // 宽限一轮：Baritone 绕路 / 重新算路都要几秒
        if (sellStallCount == 2) {
            module.getBaritone().stop();
            repathNpc(target);
            module.info("§e⚠ 收购 NPC 寻路无进展 §8▸ 重新计算路线");
            return false;
        }
        module.getBaritone().stop();
        sellAbort("寻路到收购 NPC（无进展）");
        return true;
    }

    /**
     * 阶段：SELL_TRADE（交互收购 NPC → 循环出售到背包清零）。
     *
     * <p>一轮 = 点矿石 → 点「全部」→ 点「确认出售」→ 等结算。<b>每一步的「能往下走了」判据是
     * 「下一屏的格子出现了」，不是「菜单状态号变了」</b>：本服商店每 10 秒定时全量刷新一次，
     * 状态号自己就会往前走，拿它当凭据会把「点商品格根本没开选量菜单」误判成刷新成功，
     * 然后一直去点一个不存在的「全部」（用户 2026-09-22：连点十几次「点击「全部」未响应」）。
     * 而且先等新一屏到齐再点，也天然避开了「一屏里这些槽同时存在、不等刷新会点到上一屏按钮」
     * （数量还没选上就点确认 → 一颗都卖不出去）。</p>
     *
     * <p>每次戳 NPC 之前先确认<b>人真的站在它身旁</b>（{@link #NPC_TALK_DISTANCE_SQR}）；
     * 被挤开 / 传送偏了就回 {@code SELL_PATH} 走回来再点 —— 站在原版右键距离的边缘（AABB 外扩 3.0）
     * 是能戳开菜单的，但商店插件点商品格时会按更近的距离把这些点击全丢掉。</p>
     *
     * <p>卖多轮是必须的：一次「全部」只提交一屏的上限（实测 64 个），20 组要跑多轮，所以整段做成
     * 「点一轮 → 看背包少没少 → 没卖完继续」的循环，直到背包清零；连续两轮一颗没少就停机
     * （菜单没在收：服务器卡 / 商品被下架 / 背包读数不可信）。</p>
     */
    private void tickSellTrade() {
        if (stateTick == 1) {
            sellPhase = 0;
            sellStepTicks = 0;
            sellStepRetries = 0;
            sellRounds = 0;
            sellPreConfirmCount = 0;
            sellNoProgressRounds = 0;
            sellLastMenuState = -1;
            sellInteractTicks = 0;
            sellListingWait = 0;
            sellLastContainerId = -1;
            module.getBaritone().stop();
            personalSell.beginFlow();
        }
        if (mc.screen instanceof AbstractContainerScreen<?>) return;

        String filter = module.sellItemFilter();
        if (filter == null || filter.isBlank()) {
            sellAbort("出售（没选目标：在「自用模式」页选一个要挖的矿或方块）");
            return;
        }

        // 背包已清零：整条出售完成
        if (sellRounds > 0 && personalSell.countTargetInBag() == 0) {
            transitionTo(MinerState.SELL_RETURN);
            return;
        }
        // 菜单被服务端关掉：卖完一轮后商店自己关（背包已空 → 整条就是卖完了，直接收工）/ 掉线重开
        // （背包还有 → 回「戳 NPC 开菜单」，但别把轮数清零）。实测 2026-09-22 20:41:51 卖完关菜单后，
        // 这里没做判断就回阶段 0 重新戳 NPC 开菜单，白花 1~2 秒才发现背包已经空了。
        if (sellPhase != 0 && !personalSell.hasMenu()) {
            if (personalSell.countTargetInBag() == 0) {
                transitionTo(MinerState.SELL_RETURN);
                return;
            }
            sellPhase = 0;
            sellStepTicks = 0;
            sellInteractTicks = 0;
            sellListingWait = 0;
            sellLastContainerId = -1;
            return;
        }
        // 商店每卖完一轮会当场重开一屏（实测 2026-09-22：点完「确认出售」后立刻换成容器 #3）：
        // 换屏这件事写进日志，新一屏的内容也 dump 出来，免得下一轮在没见过的屏上瞎点
        if (sellPhase != 0 && sellMenuContainerId() != sellLastContainerId) {
            sellLastContainerId = sellMenuContainerId();
            sellListingWait = 0;
            MINING_LOG.info("[卖矿流程] 商店换屏：{}", personalSell.menuDump());
        }

        sellStepTicks++;
        switch (sellPhase) {
            case 0 -> {
                if (personalSell.hasMenu()) {
                    // 商店是「先开一张空表格、隔一会儿再异步刷出商品列表」，而且商品格<b>只有背包里真有
                    // 该商品时才可点</b>（用户 2026-09-22 实测）。列表没到就往下点，能点到的只有自己背包
                    // 那 36 格（菜单 = 54 格容器 + 36 格自己背包）：点下去等于把手里的矿石抓到光标上，
                    // 那一格预测性变空 → 读数变 0 → 误报「卖完了」→ 用户 2026-09-22「根本没卖出去
                    // 一直说我出售成功」。等列表带出价格说明行再进下一相，上限 SELL_LISTING_GRACE_TICKS。
                    if (!personalSell.hasListedTarget() && sellListingWait < SELL_LISTING_GRACE_TICKS) {
                        sellListingWait++;
                        return;
                    }
                    MINING_LOG.info("[卖矿流程] 收购菜单已开：{}", personalSell.menuDump());
                    sellLastContainerId = sellMenuContainerId();
                    sellPhase = 1;
                    sellStepTicks = 0;
                    sellStepRetries = 0;
                    sellListingWait = 0;
                    return;
                }
                // 后自增：进本态的第一刻就戳一次（原来第一次要等满 20 刻，光站着白等 1 秒 ——
                // 实测 2026-09-22 20:41:49 进 SELL_TRADE、20:41:50 才看到第一次右键），之后每 20 刻补一次
                if (sellInteractTicks++ % SELL_INTERACT_INTERVAL == 0) {
                    var npc = personalSell.findNpc();
                    double distSqr = npcDistanceSqr();
                    if (npc == null) {
                        module.warning("§e⚠ 附近找不到「" + module.settings().personalSellNpcName + "」§8▸ 按坐标再找一次");
                        // 名字对不上时把附近实体的真实名字写进日志（头顶看到的字可能来自队伍装饰或全息实体）
                        MINING_LOG.info("[卖矿流程] 关键词「{}」扫不到收购 NPC，附近实体：{}",
                            module.settings().personalSellNpcName, personalSell.nearbyEntityDump());
                    } else if (distSqr > NPC_TALK_DISTANCE_SQR) {
                        // 人没站到 NPC 身旁就别戳、别点：服务端右键判据（AABB 外扩 3.0）会放行、菜单也能开，
                        // 但商店插件在点商品格那一步按更近的距离校验，站远了连点十几次全被丢掉
                        // （用户 2026-09-22「都没走到npc身边 隔着好远」）。这一档唯一正确的动作是走回去。
                        module.warning("§e⚠ 还没站到收购 NPC 身旁 §8▸ 回去接着走 §8(§7距 "
                            + distanceText(distSqr) + " 格§8)");
                        MINING_LOG.info("[卖矿流程] 距收购 NPC {} 格，超出交易距离，回 SELL_PATH 重新走位（玩家 {}，NPC {}，配置坐标 {}）",
                            distanceText(distSqr), mc.player.blockPosition(), npc.blockPosition(),
                            personalSell.npcPos());
                        transitionTo(MinerState.SELL_PATH);
                        return;
                    } else {
                        personalSell.interactNpc(npc);
                        MINING_LOG.info("[卖矿流程] 已右键收购 NPC：name={} display={} 类型={} 距玩家 {} 格",
                            npc.getName().getString(), npc.getDisplayName().getString(), npc.getType().toString(),
                            distanceText(distSqr));
                    }
                }
                if (sellStepOutcome("打开收购菜单") == SellStep.RETRY) sellInteractTicks = 0;
            }
            case 1 -> {
                if (sellStepTicks == 1) sellRoundStartMs = System.currentTimeMillis();
                if (personalSell.countTargetInBag() == 0) {
                    transitionTo(MinerState.SELL_RETURN);
                    return;
                }
                // 商店卖完一轮会重开一屏，新屏的商品列表同样是异步刷出来的：列表还没到就点，命中的
                // 只有带名字的镜像格或自己背包（点自己背包 = 把东西抓到手心，读数当场变 0，
                // 用户 2026-09-22「根本没卖出去 一直说我出售成功」）。等商品列表带出说明行再点。
                if (!personalSell.hasListedTarget() && sellListingWait < SELL_LISTING_GRACE_TICKS) {
                    sellListingWait++;
                    return;
                }
                if (!personalSell.clickTarget()) {
                    if (sellStepOutcome("点击要卖的矿物") == SellStep.RETRY) sellPhase = 0;
                    return;
                }
                sellPhase = 2;
                sellStepTicks = 0;
            }
            case 2 -> {
                // 等选量菜单：判据是「「全部」那一格出现了没有」，不是「菜单状态号变没变」——
                // 商店每 10 秒定时全量刷新一次，状态号自己就会往前走，拿它当「上一步生效」的凭据
                // 会把「点商品格根本没开选量菜单」误判成刷新成功，然后一直去点一个不存在的「全部」
                // （用户 2026-09-22：连点十几次「点击「全部」未响应」）。
                String keyword = module.settings().personalSellPickKeyword;
                if (!personalSell.menuHasKeyword(keyword)) {
                    if (sellStepOutcome("点了商品格，但菜单里没出现「" + keyword + "」") == SellStep.RETRY) sellPhase = 1;
                    return;
                }
                sellStepRetries = 0;
                // 点「全部」之前先记下「确认出售」那一格的指纹：选量一生效插件就会改写它
                // （实测量: 灰染料+「请先选择至少1个物品」→ 绿宝石块+「数量:64个」），
                // 下一相靠这个变化判断「不必再等」，一轮省掉大半秒
                sellConfirmFingerprint = personalSell.keywordSlotFingerprint(module.settings().personalSellConfirmKeyword);
                if (!personalSell.clickKeyword(keyword)) {
                    if (sellStepOutcome("点击「" + keyword + "」") == SellStep.RETRY) sellPhase = 1;
                    return;
                }
                sellPhase = 3;
                sellStepTicks = 0;
            }
            case 3 -> {
                // 同理：等「确认出售」那一格出现（有的服这两格在同一屏，那就往下走）
                String keyword = module.settings().personalSellConfirmKeyword;
                if (!personalSell.menuHasKeyword(keyword)) {
                    if (sellStepOutcome("点了「" + module.settings().personalSellPickKeyword
                        + "」，但菜单里没出现「" + keyword + "」") == SellStep.RETRY) sellPhase = 2;
                    return;
                }
                sellStepRetries = 0;
                // 「先点全部、再点全部出售」之间要留一拍：同刻连点两下时插件还没把已选数量落进会话，
                // 确认那一下等于「数量 0 确认」，一颗都卖不出去（用户 2026-09-22 实测）。
                // 但不必盲等满：选量一生效插件就会改写「确认出售」那一格（见 sellConfirmFingerprint），
                // 指纹一变就可以立刻点；插件不写这一格时退回等满 SELL_CONFIRM_DELAY_TICKS，行为不变。
                if (sellStepTicks < SELL_CONFIRM_MIN_TICKS) return;
                if (sellStepTicks < SELL_CONFIRM_DELAY_TICKS
                    && personalSell.keywordSlotFingerprint(keyword).equals(sellConfirmFingerprint)) {
                    return;
                }
                // 记下「确认前」的背包读数当这一轮的基线：下一相只要读数掉下来就算这轮成了，
                // 不必死等满额结算（一轮省半秒到一秒，20 组就是十几秒）
                sellPreConfirmCount = personalSell.countTargetInBag();
                if (!personalSell.clickKeyword(keyword)) {
                    if (sellStepOutcome("点击「" + keyword + "」") == SellStep.RETRY) sellPhase = 2;
                    return;
                }
                sellPhase = 4;
                sellStepTicks = 0;
            }
            case 4 -> {
                // 等结算：这一屏的矿石从背包扣掉要服务端几拍（原版一轮同步 32 刻以内）。
                // 一到就往下走 —— 读数比「确认前」少 = 这一轮真卖出去了；读数没掉才继续等，
                // 等到 SELL_ROUND_SETTLE_TICKS 还没掉就是「这轮一颗没少」。
                int now = personalSell.countTargetInBag();
                if (now == 0) {
                    transitionTo(MinerState.SELL_RETURN);
                    return;
                }
                boolean sold = now < sellPreConfirmCount;
                if (!sold && sellStepTicks < SELL_ROUND_SETTLE_TICKS) return;
                if (sold) {
                    sellNoProgressRounds = 0;
                } else {
                    sellNoProgressRounds++;
                }
                sellRounds++;
                MINING_LOG.info("[卖矿流程] 第 {} 轮：{} → {}（耗时 {} ms）",
                    sellRounds, sellPreConfirmCount, now, System.currentTimeMillis() - sellRoundStartMs);
                if (sellRounds >= SELL_MAX_ROUNDS) {
                    sellAbort("出售（连续 " + sellRounds + " 轮仍未卖完）");
                    return;
                }
                if (sellNoProgressRounds >= SELL_NO_PROGRESS_LIMIT) {
                    // 一颗未少 = 「点商品 → 点数量 → 点确认」这条链有一环没真正生效。
                    // 不猜原因（用户 2026-09-22：「就叫钻石啊我选的也没错」—— 之前那句「菜单里没找到」
                    // 是流程自己下的结论，实际卡在后面的按钮上），只给出链路上有问题的三步 +
                    // 当屏菜单已进日志，照着日志里的槽位名改「出售数量 / 确认出售关键词」即可。
                    sellAbort("出售（连续 " + sellNoProgressRounds + " 轮一颗未少：「"
                        + sellItemName(module.sellItemFilter()) + "」没卖出去 —— 「点商品 → 点「"
                        + module.settings().personalSellPickKeyword + "」→ 点「"
                        + module.settings().personalSellConfirmKeyword
                        + "」」有一环没生效，当屏菜单已写进 logs/latest.log）");
                    return;
                }
                sellPhase = 1;
                sellStepTicks = 0;
                sellStepRetries = 0;
                sellListingWait = 0;
            }
            default -> sellAbort("出售");
        }
    }

    /** 上一步点击是否已被服务端处理（菜单状态号变化）；超时就按「这一步没生效」走重试 */
    private boolean menuRefreshed() {
        if (sellLastMenuState < 0) return true; // 没记过基准（异常路径）：不拦
        if (sellMenuStateId() != sellLastMenuState) {
            sellStepRetries = 0;
            return true;
        }
        sellStepOutcome("等待菜单刷新");
        return false;
    }

    /**
     * 阶段：SELL_RETURN（回子服 → 交回 GO_WILD 继续 RTP 挖矿）。
     *
     * <p>阶段 0 收菜单并发流程指令 → 1 点「跨服传送」→ 2 点勾选的目标服 → 3 等传送落地 →
     * 4 落地稳定后转 {@link MinerState#GO_WILD}（前往挖矿指令 + RTP 选单那条既有链路，不另造）。</p>
     */
    private void tickSellReturn() {
        if (stateTick == 1) {
            sellPhase = 0;
            sellStepTicks = 0;
            sellStepRetries = 0;
            personalSell.beginFlow();
        }
        if (mc.screen instanceof AbstractContainerScreen<?>) return;
        ownTeleportPendingTicks = Math.max(ownTeleportPendingTicks, OWN_TELEPORT_PENDING_TICKS);

        sellStepTicks++;
        switch (sellPhase) {
            case 0 -> {
                module.getContainer().closeContainer(); // 先收掉收购菜单，再开流程菜单
                module.getCmdManager().sendMenuCommand(module.settings().personalSellCommand);
                sellPhase = 1;
                sellStepTicks = 0;
            }
            case 1 -> {
                if (!personalSell.hasMenu()) {
                    if (sellStepOutcome("打开流程菜单") == SellStep.RETRY) sellPhase = 0;
                    return;
                }
                String keyword = module.settings().personalSellCrossServerKeyword;
                if (!personalSell.clickKeyword(keyword)) {
                    if (sellStepOutcome("点击「" + keyword + "」") == SellStep.RETRY) sellPhase = 0;
                    return;
                }
                sellLastMenuState = sellMenuStateId();
                sellPhase = 2;
                sellStepTicks = 0;
            }
            case 2 -> {
                if (!personalSell.hasMenu()) {
                    if (sellStepOutcome("打开子服列表") == SellStep.RETRY) sellPhase = 0;
                    return;
                }
                if (!menuRefreshed()) return;
                String server = module.settings().personalSellReturnServer;
                if (!personalSell.clickKeyword(server)) {
                    if (sellStepOutcome("点击「" + server + "」") == SellStep.RETRY) sellPhase = 1;
                    return;
                }
                sellPhase = 3;
                sellStepTicks = 0;
            }
            case 3 -> {
                if (!teleportJumpThisTick) {
                    if (sellStepOutcome("回子服传送") == SellStep.RETRY) sellPhase = 0;
                    return;
                }
                sellStepRetries = 0;
                sellPhase = 4;
                sellStepTicks = 0;
            }
            case 4 -> {
                if (teleportJumpThisTick) { // 第二段跳变（跨服传送常分两段）→ 重新计时
                    sellStepTicks = 0;
                    return;
                }
                if (sellStepTicks < SELL_LAND_SETTLE_TICKS) return;
                module.getContainer().closeContainer();
                // 回到「前往野外」：/wild + RTP 选单 + 落地进挖矿，全是既有实现
                transitionTo(MinerState.GO_WILD);
            }
            default -> sellAbort("返回服务器");
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
            // 屏幕被其它界面占用时定期强制关闭，避免永远打不开箱子；
            // 但玩家自己按 ESC 打开的「游戏菜单」既不必关、也不该卡住流程（用户 2026-09-19：
            // 「我本来是这个界面的，然后卸货触发就把我这个界面关掉了」）——发包开箱、点槽位、发指令
            // 全都与客户端界面无关，关掉它只会把人从菜单里踢回游戏（镜头被抢）
            // 玩家自己开着容器界面（背包 / 创造背包）时只等不做（同卸货阶段，见上）
            if (mc.screen instanceof AbstractContainerScreen<?>) return;
            if (mc.screen != null && !(mc.screen instanceof PauseScreen) && stateTick % 20 == 0) {
                module.getContainer().closeContainer();
            }
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
            // 结束不再自己播一条（用户 2026-09-19：「§a✓ 吃饱了 不用两条 一条就够了」）：
            // 紧跟着的 transitionTo(MINING) 会播「§a✓ 开始挖矿 ▸ …」，那就是收尾消息
            module.getSoundNotifier().notifyMiningStart();
            transitionTo(MinerState.MINING);
            return;
        }

        // 食物耗尽（拿到手上的最后一块也吃完了）：别傻等 2 分钟超时，直接去补给
        // 搬运/进食进行中的那几刻不判「吃完」：副手模式刚把食物换走时客户端还没同步到副手，
        // 此刻扫背包必为空（见 MiningContainer#isEatingInProgress）
        if (!hasFoodToEat() && !module.getContainer().isEatingInProgress()) {
            mc.options.keyUse.setDown(false);
            module.info("§6⚠ 食物已吃完 §8▸ 前往补给点");
            transitionTo(MinerState.SUPPLY);
            return;
        }

        // 每 tick 都尝试进食：packetEat 内部幂等 —— 没在吃就起手发一包，正在吃就推进那一件
        // （吃完一件接下一件，零间隔）；用户 2026-09-19 定为发包驱动 + 严格按食物白名单吃。
        // 参数 true = 走副手：主手继续拿镐，Baritone 的挖矿不中断（「边挖边吃」）
        module.getContainer().packetEat(true);

        // 进食期间不再每刻/每 2 秒重复播报（用户 2026-09-19：「有点刷屏了」）：
        // 「正在吃 xx」已由进态的 transitionTo 文案一次说清，结束由回挖矿那条收尾

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
                int toolSlot = findDamagedToolSlot(true);
                if (toolSlot == -1) {
                    // 没有需要修的工具了（可能已被其它机制修好；无经验修补的修不了、已被跳过），直接返回矿区
                    // 这一路过去是静默的，用户 2026-09-19：「修好了没提示」，所以退出修复态统一播报一次
                    module.info("§a✓ 修复结束 §8▸ 已无低于阈值的可修工具，返回矿区继续挖矿");
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
            // 修复成功收工：旧实现静默转回挖矿，用户 2026-09-19 实机反馈「修好了没提示」，这里补播报
            if (!currentTool.isEmpty()) {
                module.info("§a✓ 修复完成 §8▸ " + toolName(currentTool) + " 耐久已回满，返回矿区继续挖矿");
            }
            transitionTo(MinerState.GO_WILD);
            return;
        }

        // 超时保护：10 分钟没修满 → 停机。旧实现这一路完全静默（用户 2026-09-19：「静默的，一句提示都没有」），
        // 这里在越线那一拍播报一次。注意：越线后本分支每刻都会进（停机失败也会继续进），所以用 stateTick
        // 精确等于「越线首拍」来保证只播一次，不能写成 stateTick > XXX 里直接播。
        if (stateTick > REPAIR_TIMEOUT_TICKS) {
            if (stateTick == REPAIR_TIMEOUT_TICKS + 1) {
                module.info("§c✗ 修复超时 §8▸ " + (REPAIR_TIMEOUT_TICKS / 1200) + " 分钟未修满，已自动停机");
            }
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
     * <p>差异（用户 2026-09-16 拍板）：旧自动重生切换方法（切换第三方框架的自动重生模块）
     * 整段删除，自动重生由本项目 {@code feature/respawn/AutoRespawnModule}（默认开启）承担；
     * 因此旧 {@code :1472} 的死亡播报模块名改成本项目的「已调用自动重生模块」，其余逐字不变。</p>
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
        //
        // 自用模式例外（用户 2026-09-20）：死亡返回指令填的是 /back，人已经回到被发现的那个位置，
        // 再走 GO_WILD 会把刚落地的位置又用 /wild + RTP 送走 —— 等于 /back 白做。所以自用模式下
        // 直接进 MINING 就地接着挖（死亡点在岩浆里那条已在阶段 1 跳过 /back，照样去野外换区）。
        if (module.isPersonalMode()) {
            module.info("§a✓ 已返回死亡点 §8▸ 就地继续挖矿");
            transitionTo(MinerState.MINING);
            return;
        }
        transitionTo(MinerState.GO_WILD);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  辅助方法
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 旧 {@code needsRepair}，{@code :1520-1530} 逐字，另加两道防死循环夹紧（本项目）。
     *
     * <p><b>用户 2026-09-19 实机反馈</b>：「耐久阈值上限是 2031，实际可以输入 3000，
     * 输入 3000 就会无限循环去挂机点修复」。上游已把输入框上限改成「跟随手持工具的满耐久」
     * （{@code ToolDurability#heldToolDurability}），这里再兜两道，保证任何配置值
     * （含老存档里残留的 3000、或服务器自定义的高耐久工具）都不会卡成「挂机点 ↔ 矿区」来回跑：</p>
     * <ol>
     *   <li>阈值先与<b>该工具自己的满耐久</b>取小 —— 阈值高于满耐久时，工具永远处于「需修复」；</li>
     *   <li>已经算「满耐久」（{@link #isFullyRepaired}，剩 5 点以内）的工具一律不判需修复 ——
     *       否则修完回矿区又立刻被判需修复，一步都挖不了就再回挂机点。</li>
     * </ol>
     */
    private boolean needsRepair(ItemStack tool) {
        if (tool.isEmpty()) return false;
        Integer maxDamage = tool.get(DataComponents.MAX_DAMAGE);
        Integer damage = tool.get(DataComponents.DAMAGE);
        if (maxDamage == null || damage == null) return false;
        if (isFullyRepaired(tool)) return false;
        int remaining = maxDamage - damage;
        // 阈值不能超过工具最大耐久：否则满耐久（remaining == maxDamage）仍被判为「需修复」，
        // 修完回矿区又立刻触发修复，形成修复↔挖矿死循环。
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

    /**
     * 旧 {@code isRepairableTool}，{@code :1560-1567} 逐字。
     *
     * <p>判据实现搬去了 {@link ToolDurability}（第 169 条：控制台「耐久阈值」的动态上限要用同一套判据），
     * 这里保留同名转发，改动面最小。</p>
     */
    private boolean isRepairableTool(ItemStack stack) {
        return ToolDurability.isRepairableTool(stack);
    }

    /**
     * 处理「剩余耐久低于阈值、但没有经验修补附魔」的工具（用户 2026-09-19 需求）。
     *
     * <p><b>为什么不去挂机点</b>：挂机修复靠杀戮光环打怪掉经验、再由经验修补把经验换成耐久，
     * 工具自己不带经验修补，传送到修复点挂到超时也修不回一格耐久（旧实现是「检测到就整体停机」，
     * 于是背包里明明还有别的镐子也不挖了）。现在改为：</p>
     * <ul>
     *   <li>不传送、不换槽，就地跳过这件工具的修复流程；</li>
     *   <li>同一件工具只提示一次（按「物品 + 槽位」记账，见 {@link #noMendingNotifiedKey}）；</li>
     *   <li>受损的是<b>镐子</b>且背包（含副手）已没有别的镐子 → 停机（没镐子挖不动了）；</li>
     *   <li>背包还有别的镐子（不要求带经验修补）→ 继续挖矿。</li>
     * </ul>
     *
     * <p><b>只扫镐子</b>（用户 2026-09-19 裁定）：剑 / 铲 / 斧 / 锄不带经验修补对挖矿没有任何影响，
     * 既不该刷提示，也不该参与这里「当前受损工具」的挑选 —— 否则一把僵尸掉的烂铲子会因剩余耐久
     * 天然最低而顶掉真正受损的镐子，使「修不了 → 换镐 / 停机」那条判据失效。</p>
     *
     * @return true 表示已停机，调用方必须立即 return
     */
    private boolean handleUnrepairableTool() {
        int slot = findDamagedToolSlot(false);
        if (slot == -1) {
            noMendingNotifiedKey = ""; // 已无受损镐子：解除记账，下次再出现照样提示
            return false;
        }

        ItemStack tool = slot == -2 ? mc.player.getOffhandItem() : mc.player.getInventory().getItem(slot);
        if (tool.isEmpty()) return false; // 镐子必定无经验修补（findDamagedToolSlot 已筛过）

        String key = BuiltInRegistries.ITEM.getKey(tool.getItem()) + "@" + slot;
        if (key.equals(noMendingNotifiedKey)) return false; // 这件镐子已提示过，不再刷屏
        noMendingNotifiedKey = key;

        // 物品全名（如「下界合金镐」，自定义重命名也照实显示）
        String name = tool.getHoverName().getString();

        // 镐子：背包还有别的镐子就继续挖，没有才停机
        if (hasOtherPickaxe(slot)) {
            module.warning("§e⚠ " + name + " 没有经验修补 §8▸ 不回去挂机点修复，改用背包内其余镐子继续挖矿");
            return false;
        }

        module.getBaritone().stop();
        module.error("§c✗ " + name + " 没有经验修补 §8▸ 不回去挂机点修复，背包已没有能继续挖的"
            + module.requiredPickaxeName() + "（剩 1 点耐久的也停），自动挖矿已停止");
        if (module.isEnabled()) ModuleManager.setEnabled(AutoMinerModule.MODULE_ID, false);
        return true;
    }

    /**
     * 背包（含副手）里是否还有除 {@code excludeSlot} 之外<b>能接着挖的</b>镐：判据
     * {@link #isReadyPickaxe(ItemStack)}（模式对得上 + 还能挖）。
     *
     * <p>判据里的「模式对得上」这一维是用户 2026-09-21 追加的（「万一手边没有第二把精准采集的镐，
     * 就变成掉落物了」）：原先只看「是不是镐子」，精准模式下会提示「改用背包内其余镐子继续挖矿」——
     * 那把可能是时运镐或普通镐，挖出来的是掉落物而不是要卖的原矿方块。模式不符就当没有别的镐，
     * 宁可停机播报，也不换一把会把产出搞错的镐。</p>
     *
     * <p>带经验修补的镐在 {@link #isUsablePickaxe(ItemStack)} 里一律算能用（挖矿得经验会自己修回来），
     * 所以「只有一把带经验修补的精准镐」这种配置永远不会被这里判成没镐、也不会被换走。</p>
     *
     * <p>不数副手（用户 2026-09-21：「我的吃东西一直放副手的」）：副手是常驻食物位，且 Baritone
     * 只认快捷栏，副手那把镐用不上。判定是「还能不能接着挖」，够不着的位置不算。</p>
     *
     * @param excludeSlot 受损镐子所在槽位（{@code -2} = 副手，此时等于全背包都要扫），不计入
     */
    private boolean hasOtherPickaxe(int excludeSlot) {
        if (mc.player == null) return false;
        for (int i = 0; i < 36; i++) {
            if (i == excludeSlot) continue;
            if (isReadyPickaxe(mc.player.getInventory().getItem(i))) return true;
        }
        return false;
    }

    /**
     * 快捷栏（0~8）里有没有<b>现在就能直接拿来挖</b>的镐，判据 {@link #isReadyPickaxe(ItemStack)}。
     *
     * <p><b>只看快捷栏</b>（用户 2026-09-21：「我的吃东西一直放副手的 你检测副手干吊」）：</p>
     * <ul>
     *   <li>副手是常驻食物位 —— 自动进食那一套一直占着它，检测它没有意义；</li>
     *   <li>Baritone 的自动选工具也只认快捷栏 0-8（见下方补工具那段注释），副手与背包 9~35 的镐
     *       都够不着，拿来当「还有镐」的依据只会让手上的报废镐一路挖到爆。</li>
     * </ul>
     *
     * <p>背包 9~35 里有镐时不用慌：优先级 2.0 会先 {@link #swapInReadyPickaxe()} 把它换进快捷栏。</p>
     */
    private boolean hasReadyPickaxeInReach() {
        if (mc.player == null) return false;
        for (int i = 0; i < 9; i++) {
            if (isReadyPickaxe(mc.player.getInventory().getItem(i))) return true;
        }
        return false;
    }

    /**
     * 把主背包里<b>最合适</b>的一把镐换到快捷栏，顶掉手上 / 快捷栏里那把不能用的（用户 2026-09-21）。
     *
     * <p>优先顶掉<b>手上</b>那把（不符合模式或已报废）；手上不是镐时先找快捷栏空槽，实在没有就顶掉
     * 快捷栏里第一把不符合模式或已报废的镐。只发一次交换包，换上来之后
     * {@link #hasReadyPickaxeInReach()} 立刻为真，所以不会连发。</p>
     *
     * @return true = 已发包换槽（调用方本刻直接 return，下一刻再判）
     */
    private boolean swapInReadyPickaxe() {
        if (mc.player == null || mc.gameMode == null) return false;
        var inventory = mc.player.getInventory();

        int target = -1;
        ItemStack selected = inventory.getSelectedItem();
        if (isPickaxe(selected) && !isReadyPickaxe(selected)) {
            target = inventory.getSelectedSlot();
        } else {
            for (int i = 0; i < 9 && target < 0; i++) {
                if (inventory.getItem(i).isEmpty()) target = i;
            }
            for (int i = 0; i < 9 && target < 0; i++) {
                if (isPickaxe(inventory.getItem(i)) && !isReadyPickaxe(inventory.getItem(i))) target = i;
            }
        }
        if (target < 0) return false;

        int source = -1;
        int bestRemaining = USABLE_DURABILITY_FLOOR;
        for (int i = 9; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!isReadyPickaxe(stack)) continue;
            int remaining = sortableRemaining(stack);
            if (remaining > bestRemaining) {
                bestRemaining = remaining;
                source = i;
            }
        }
        if (source < 0) return false;

        swapWithHotbar(source, target);
        return true;
    }

    /**
     * 这把镐现在能不能直接拿来挖：<b>模式对得上</b>（{@code AutoMinerModule#matchesLootMode}，
     * 精准采集模式必须带精准采集附魔）+ 还能挖。
     *
     * <p>模式这一维与启动自检同源 —— 自检放行了什么，运行期就只认什么，不会出现
     * 「自检通过、Baritone 却拿时运镐去挖精准模式的目标」。</p>
     */
    private boolean isReadyPickaxe(ItemStack stack) {
        return isUsablePickaxe(stack) && module.matchesLootMode(stack);
    }

    /** 排序用的剩余耐久：带经验修补的镐视为无限（挖矿得经验会自己修回来，不该被淘汰） */
    private int sortableRemaining(ItemStack stack) {
        return ItemIdentifier.hasMending(stack) ? Integer.MAX_VALUE : remainingDurability(stack);
    }

    /** 剩余耐久；没有耐久组件（不可损坏）返回 {@link Integer#MAX_VALUE} */
    private int remainingDurability(ItemStack stack) {
        Integer maxDamage = stack.get(DataComponents.MAX_DAMAGE);
        Integer damage = stack.get(DataComponents.DAMAGE);
        if (maxDamage == null || damage == null) return Integer.MAX_VALUE;
        return maxDamage - damage;
    }

    /**
     * 这把镐还能不能继续挖。
     *
     * <p><b>带经验修补的镐一律算能用</b>（用户 2026-09-21：「我说的是没经验修补的稿子」）——
     * 挖矿得经验时会自己修回来，自用模式的口径就是「不管它」，剩多少耐久都不在这里拦。</p>
     *
     * <p>没有经验修补的镐：剩余耐久必须 &gt; {@value #USABLE_DURABILITY_FLOOR}。剩 1 点再挖一块就爆，
     * 等于当场失去挖矿能力，所以宁可提前停机；没有耐久组件（不可损坏）的模组镐永远算能用 ——
     * 不能因为读不到 {@code MAX_DAMAGE} 就当报废。</p>
     */
    private boolean isUsablePickaxe(ItemStack stack) {
        if (!isPickaxe(stack)) return false;
        if (ItemIdentifier.hasMending(stack)) return true;
        return remainingDurability(stack) > USABLE_DURABILITY_FLOOR;
    }

    /**
     * 旧 {@code findDamagedToolSlot}，{@code :1583-1606} 逐字（副手 -2 优先）。
     *
     * <p>新增 {@code requireMending} 形参（用户 2026-09-19），两种用途的工具面不同：</p>
     * <ul>
     *   <li>{@code true} —— 触发挂机修复与换槽。挂机修复只有带经验修补的工具修得回来，
     *       所以只认带经验修补的「可修复工具」（镐/铲/斧/锄/剑）；</li>
     *   <li>{@code false} —— 「修不了」的提示扫描。<b>只认镐子</b>：挖矿真正离不开的只有镐，
     *       剑/铲/斧/锄缺经验修补对挖矿毫无影响，不该刷提示，也不该顶掉受损镐子的判据
     *       （僵尸掉的烂铲子剩余耐久天然最低，旧写法会把它当成「当前受损工具」）。</li>
     * </ul>
     *
     * @param requireMending true = 只取带经验修补的可修复工具；false = 只取无经验修补的受损镐子
     * @return 槽位（-2 = 副手，0~35 = 背包），没有则 -1
     */
    private int findDamagedToolSlot(boolean requireMending) {
        if (mc.player == null) return -1;

        // 副手工具优先（已就位，直接修）
        ItemStack offhand = mc.player.getOffhandItem();
        if (isDamagedToolCandidate(offhand, requireMending)) return -2;

        int bestSlot = -1;
        int lowestRemaining = Integer.MAX_VALUE;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!isDamagedToolCandidate(stack, requireMending)) continue;
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

    /**
     * {@link #findDamagedToolSlot(boolean)} 的单件判据：已受损 + 工具面符合该用途。
     *
     * @param requireMending true = 可修复工具且带经验修补；false = 镐子且无经验修补
     */
    private boolean isDamagedToolCandidate(ItemStack stack, boolean requireMending) {
        if (!needsRepair(stack)) return false;
        if (requireMending) {
            return isRepairableTool(stack) && ItemIdentifier.hasMending(stack);
        }
        return isPickaxe(stack) && !ItemIdentifier.hasMending(stack);
    }

    /**
     * 旧 {@code findDamagedTool}，{@code :1608-1614} 逐字。
     *
     * <p>只服务 REPAIR 态的状态播报：那一态修的是带经验修补的工具（{@code findDamagedToolSlot(true)}）。</p>
     */
    private ItemStack findDamagedTool() {
        int slot = findDamagedToolSlot(true);
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
        // 副手也是玩家的食物（「边挖边吃」时食物就临时放在这儿，见 MiningContainer#packetEat 副手模式）：
        // 不数副手会让「食物不足检测」在进食期间反复触发补给（用户 2026-09-19 实机）
        ItemStack offhand = mc.player.getOffhandItem();
        if (whitelist.contains(BuiltInRegistries.ITEM.getKey(offhand.getItem()).toString())) {
            count += offhand.getCount();
        }
        return count;
    }

    /**
     * 背包里是否还有能吃的食物（必须同时是「食物白名单内」且带 FOOD 组件）。
     *
     * <p>旧 {@code hasFoodToEat}，{@code :1640-1650}。用户 2026-09-19 追加自动回血时曾把进食口径
     * 放宽成「任何带 {@code FOOD} 组件的物品」，结果怪物掉的腐肉也被吃掉（用户当天实机反馈：
     * 「白名单食物只有金苹果，怎么会吃腐肉」）—— 现按用户裁定<b>严格白名单</b>收口：
     * 吃什么、留什么、囤什么，三处判据统一走 {@code foodWhitelist}，与补给取货
     * （{@code MiningContainer#withdrawFood()}）和 {@link #countFoodStacks()} 完全同源。</p>
     */
    private boolean hasFoodToEat() {
        if (mc.player == null) return false;
        List<String> whitelist = module.getFoodWhitelist();
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.has(DataComponents.FOOD)
                && whitelist.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())) {
                return true;
            }
        }
        // 副手同样算「有得吃」：进食走副手模式后食物被搬离背包，只扫 0~35 会当场判成「食物已吃完」
        // → 退出进食态 → restoreEatDisplacedItem 把食物换回背包 → 下一轮又搬过去，来回打转并卡死
        // 状态机（用户 2026-09-19 实机：「放到副手之后食物检测到没有食物了，然后卡死状态机」）
        ItemStack offhand = mc.player.getOffhandItem();
        return !offhand.isEmpty() && offhand.has(DataComponents.FOOD)
            && whitelist.contains(BuiltInRegistries.ITEM.getKey(offhand.getItem()).toString());
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
            // 进食文案按原因分两种（用户 2026-09-19：「能不能分清楚没血吃跟饿了吃的文案啊」），
            // 并在这条上直接带上「正在吃什么」—— 用户当天追加：「应该显示玩家在吃上面食物呀」。
            // 吃哪种食物只有这里播一次；结束时不另外播「✓ 吃饱了」，由下面回挖矿那条收尾，避免两条
            case EATING -> {
                String eatFood = module.getContainer().describeEatingFood();
                yield eatReason == EAT_REASON_HEAL
                    ? "§d自动回血 §8▸ 正在吃 " + eatFood
                    : "§e补充饥饿值 §8▸ 正在吃 " + eatFood;
            }
            case REPAIR -> "§c⚠ " + toolName(findDamagedTool()) + "耐久过低 §8▸ 联动杀戮光环修复中";
            case DEATH_HANDLING -> "§c✗ 检测到死亡 §8▸ 已调用自动重生";
            case RESPAWN_WAIT -> module.isPersonalMode()
                ? "§6复活完成 §8▸ 返回死亡点"   // 自用模式的死亡返回指令是 /back，回到死亡点就地接着挖
                : "§6复活完成 §8▸ 返回挂机点";
            // 战斗态的播报由 MiningCombat 带怪物名发出（「发现 xx → 主动出击」/「苦力怕引信点燃 → 后撤熄灭」），
            // 这里返回空串不重复播报
            case COMBAT -> "";
            // 自用模式出售链（用户 2026-09-20）：四态各播一条。卖的东西单独取名字——它是目标在当前
            // 采集模式下的产物（选石头没开精准就是圆石），不一定等于目标本身；取不到就退回目标矿名
            case SELL_TRAVEL -> String.format("§b开始出售 §8▸ 回主城 · 带 %d 组 %s",
                countPersonalOre(), sellItemName(target));
            case SELL_PATH -> "§b回城完成 §8▸ 正在寻路到收购 NPC";
            case SELL_TRADE -> "§b出售中 §8▸ 与收购 NPC 交易";
            case SELL_RETURN -> "§b出售完成 §8▸ 返回服务器继续挖矿";
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
    //  物品栏原语（旧项目走旧框架的 InvUtils；本项目换 26.1.2 原语）
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
     * 把主背包槽与快捷栏槽对调（旧项目走 {@code InvUtils.move().from(...).toHotbar(...)}，
     * 本项目换 26.1.2 原语）。
     *
     * <p>走 {@code ContainerInput.SWAP}：点击<b>主背包</b>槽、button 传快捷栏下标（0~8）即「与那个
     * 快捷栏槽交换物品」，与 {@link #swapWithOffhand(int)}（button = 40 表示副手）同一口径。
     * 主背包 9~35 在 {@code InventoryMenu} 里槽位下标同值。</p>
     */
    private void swapWithHotbar(int invSlot, int hotbarSlot) {
        if (mc.player == null || mc.gameMode == null) return;
        if (invSlot < 9 || invSlot >= 36 || hotbarSlot < 0 || hotbarSlot > 8) return;
        mc.gameMode.handleContainerInput(mc.player.inventoryMenu.containerId, invSlot,
            hotbarSlot, ContainerInput.SWAP, mc.player);
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
