package com.yiyiaddon.feature.packetbreak;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.event.ServerBlockEvent;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.core.net.BlockWatchService;
import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.core.net.SendDecision;
import com.yiyiaddon.core.net.SendGate;
import com.yiyiaddon.core.net.SendView;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.packetbreak.config.PacketBreakSettings;
import com.yiyiaddon.feature.packetbreak.config.PacketBreakTexts;
import com.yiyiaddon.feature.packetbreak.model.BreakMode;
import com.yiyiaddon.feature.packetbreak.model.PacketBreakTarget;
import com.yiyiaddon.feature.packetbreak.model.TargetMode;
import com.yiyiaddon.feature.packetbreak.render.PacketBreakRenderer;
import com.yiyiaddon.feature.packetbreak.ui.PacketBreakPage;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.container.InventoryAccess;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 发包秒破模块（26.1.2 服务端机制重做版）。
 *
 * <p>26.1.2 服务端已封死「START 后立刻 STOP」的旧秒破套路：官方源码
 * ServerPlayerGameMode.handleBlockBreakAction 中，STOP 只有在
 * 「每 tick 破坏进度 δ ×（已挖 tick + 1）≥ 0.7」时才会真正破坏方块，
 * 否则进入延迟破坏缓冲。且服务端只有单个挖掘槽位，多个方块交错发
 * START 会互相覆盖，导致全部挖不烂。</p>
 *
 * <p>重做核心（逐字照旧项目 {@code tactical/packetbreak/PacketInstantBreak.java}，985 行）：</p>
 * <ul>
 *   <li>串行单槽：任意时刻只对 1 个方块发 START，挖完确认后再挖下一个</li>
 *   <li>卡点停挖：按服务端同源公式 δ = getDestroyProgress 计算达标 tick，
 *       到 0.7 阈值（极速）或 1.0（原版速度）瞬间发 STOP 一次破坏</li>
 *   <li>确认重试：STOP 后等服务器方块消失，超时自动重发，杜绝网络抖动丢判</li>
 *   <li>预测同步：登记服务端已知状态，服务端确认前客户端不提前置空方块</li>
 *   <li>进度 ESP 按服务端真实进度渲染</li>
 * </ul>
 *
 * <p><b>框架适配清单（旧 → 新，逐条）</b></p>
 * <ol>
 *     <li><b>接管原版破坏</b>：旧 {@code StartBreakingBlockEvent} + {@code event.cancel()}
 *         → {@link SendGate#register} 注册本模块的 {@link #onSendRule(SendView)}，命中
 *         {@code PLAYER_ACTION / START_DESTROY_BLOCK} 时 {@link SendDecision#cancel()}；
 *         被取消的坐标经 {@code volatile} 请求槽交给主线程 {@link #consumeBreakRequest()}
 *         入队（规则在网络线程，禁止碰世界状态，故不能在规则里直接入队）。</li>
 *     <li><b>服务端确认</b>：旧 {@code PacketEvent.Receive} + 自建反馈桥
 *         → {@link BlockWatchService#watch}/{@code clear} 登记关注坐标 + 订阅
 *         {@link ClientEventType#SERVER_BLOCK}（ACK 无条件派发，方块更新只派发关注坐标），
 *         判定顺序与旧实现完全一致（ACK 只挡重复包，权威方块状态才判定成功与回弹）。</li>
 *     <li><b>破坏协议</b>：旧自建协议层（取预测序列号 + 登记已知服务端状态）
 *         → {@link ClientPacketSender#sendBreakStart}/{@code sendBreakStop}/{@code sendBreakAbort}
 *         （取号与预测登记都在核心内部完成，语义等价）。</li>
 *     <li><b>预测序列号不可见</b>：本项目把取号封装在核心内部、不外露序列号，因此旧
 *         {@code isAcknowledged(sequence)} 改为「发包前记下的累计 ACK 基线 + 1」比较
 *         （见 {@link #isAcknowledged(int)}），用途与判定时机一字未改。</li>
 *     <li><b>转向发包</b>：旧 {@code Rotations.rotate(yaw, pitch, 50, callback)}
 *         → 本项目没有旋转调度层，本刻直接发一次旋转包并置客户端朝向，回调排在下一句 tick
 *         执行（{@code ROTATE_FALLBACK_TICKS} 兜底逻辑原样保留）。</li>
 *     <li><b>自动换工具</b>：旧 {@code InvUtils.findFastestTool / swap}
 *         → 扫描复合槽 0~35（口径同 {@code InventoryAccess.find} 的上界约定）取破坏速度最快的一把，
 *         用 {@code InventoryMenu} 的 {@code ContainerInput.SWAP} 与当前选中快捷栏槽交换
 *         （选中槽下标不变，与旧 {@code swap(slot, false)} 语义一致）。</li>
 *     <li><b>可破坏 / 朝向判据</b>：旧框架 {@code BlockUtils.canBreak} / {@code BlockUtils.getDirection}
 *         → 本模块私有 {@link #canBreak} / {@link #breakFace}，判据对齐本项目既有口径
 *         （{@code MiningVeinMiner} / {@code WaterEscapeBreaker}）。</li>
 *     <li><b>生命周期</b>：旧 {@code GameJoinedEvent} / {@code GameLeftEvent}
 *         → {@link ClientEventType#JOIN_SERVER} / {@link ClientEventType#DISCONNECT}；
 *         旧 {@code chatFeedback=false; toggle(); chatFeedback=true;}（单人世界闸门）
 *         → {@link Module#environmentRefusal()} 交给框架，启用与进世界两个时机都拦
 *         （旧写法只拦「在单人世界里点开启」，拦不住在主菜单就先开启的模块）。</li>
 *     <li><b>播报</b>：旧基类 {@code notify / warning / notifyError} 与
 *         {@code highlightText / highlightNumber / highlightFunction} 的颜色码包装在本类内逐字保留，
 *         正文经 {@link ClientChat#send} 输出。</li>
 * </ol>
 *
 * <p><b>保留缺陷（旧项目原样，禁止顺手修复，已在开发报告登记）</b>：</p>
 * <ol>
 *     <li>{@code PacketMiningFeedback.startAcknowledged} 只写不读（旧实现里是死字段）；
 *         本项目按同样形状保留 {@code PacketBreakTarget#startAcknowledged} 的赋值。</li>
 *     <li>{@link #restoreSlotIfNeeded()} 的恢复判据在换工具成功后必然成立（
 *         {@code previousSlot == miningSlot}：交换语义下选中槽下标不变），因此「挖完自动切回」
 *         实际是空操作，工具会留在手上。</li>
 *     <li>{@code sendStartNow} 里「{@code destroyDelta >= 1.0F} 就只发 START、不发 STOP」的近路：
 *         服务端实际进度落在 {@code [0.7, 1.0)} 时该近路会漏掉 STOP，只能靠确认超时重试兜底。</li>
 * </ol>
 *
 * <p><b>冲突检测表（第 4 条约束：只保留指向本项目自动挖矿秒破的那一条）</b>：旧表的其余条目
 * 全部指向旧项目框架自带的模块，本项目不存在对应物，已按 96 号 D-18-08 删除（清单见开发报告）。</p>
 *
 * <p><b>与「瞄准方块高亮」的关系（用户 2026-09-19）</b>：秒破开挖前会把视角转向目标方块
 * （{@code rotate}），准星一路扫过去，ESP 全局设置里的「瞄准方块高亮」（那个白框）就会一直闪、
 * 挖东西时一直出现。因此模块运行期间写入 {@code EspGlobalSettings#setInstantBreakRunning(true)}，
 * 由 {@code BlockOutlineRenderer} 统一压掉这一层；关闭模块立刻恢复。处理口径与自动挖矿那次
 * （用户 2026-09-18）完全一致。</p>
 *
 * @author yiyijia
 */
public final class PacketInstantBreakModule extends Module implements TacticalCoordinator.Listener {

    /** 模块 ID（状态文件键 / 快捷键键名 / 发包闸门所有者 / 方块关注表所有者 / 渲染层所有者） */
    public static final String MODULE_ID = "packetbreak";

    /** 模块中文显示名（播报前缀与控制台标题共用） */
    public static final String MESSAGE_MODULE = PacketBreakTexts.MODULE_NAME;

    /**
     * 发包闸门里的判定优先级（越大越先判定）：必须排在「发包防踢」之前。
     *
     * <p><b>为什么用优先级而不是靠模块注册顺序</b>（用户 2026-09-19：「发包秒破跟发包防踢有没有冲突」）：
     * 本模块默认关、由玩家运行时打开，而防踢默认开、进服就注册好了 —— 无论 {@code AddonModules} 里谁写在前面，
     * 运行时的注册顺序都必然是「防踢先、秒破后」。而 {@link SendGate} 取第一个非放行就返回：防踢的限速
     * （默认每秒 8 个，服务器卡顿 / 拉回冷却期一律丢）会把超限的原版 START 直接丢掉，本模块的规则
     * 根本不会被执行 —— 那一下既没走原版、也没走秒破，方块原地不动。</p>
     */
    private static final int GATE_PRIORITY = 100;

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  服务器破坏阈值（26.1.2 官方机制，见 ServerPlayerGameMode）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 极速卡点的破坏阈值：STOP 时服务端要求 δ×(tick+1) ≥ 0.7 才破坏 */
    private static final double THRESHOLD_INSTANT = 0.7;

    /** 原版速度的破坏阈值：等同于原版挖掘进度满格才 STOP */
    private static final double THRESHOLD_VANILLA = 1.0;

    /** STOP 发出后超过此 tick 数仍无 ACK 才允许重发，ACK 到达后绝不重复 STOP */
    private static final int STOP_ACK_TIMEOUT_TICKS = 10;

    /** STOP 无 ACK 时的重发次数上限；TCP 正常情况下不会走到这里 */
    private static final int STOP_RESEND_LIMIT = 2;

    /** 确认超时的安全边际 tick：确认时限 = 服务器完整挖掘时长 + 本值 */
    private static final int STOP_DEADLINE_MARGIN = 20;

    /** 整轮 START 在超时后只重试一次，防保护区方块形成无限发包循环 */
    private static final int START_RETRY_LIMIT = 1;

    /** 失败坐标冷却，范围模式不会每 tick 重新把不可破坏方块塞回队列 */
    private static final int FAILURE_COOLDOWN_TICKS = 100;

    /** 自动扫描队列上限 */
    private static final int MAX_QUEUE = 64;

    /** 成功播报节流：每挖坏 N 块汇总播报一次，防 RANGE 模式刷屏 */
    private static final int SUCCESS_REPORT_STEP = 8;

    /** 旋转回调兜底：开启转向后若回调 10 tick 内未执行，直接发包防卡死 */
    private static final int ROTATE_FALLBACK_TICKS = 10;

    /** 冲突巡检间隔（旧 {@code ++conflictCheckTick >= 20}） */
    private static final int CONFLICT_CHECK_INTERVAL_TICKS = 20;

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  框架适配常量
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 背包扫描上界：快捷栏 + 主背包（旧框架 {@code InvUtils.find(..., 0, 35)} 同口径） */
    private static final int BACKPACK_SCAN_LIMIT = 36;

    /** 发包动作名：开始破坏（核心 {@link SendView#detail()} 传来的原名） */
    private static final String ACTION_START_DESTROY = "START_DESTROY_BLOCK";

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体（控制台页面读写） */
    private final PacketBreakSettings settings = new PacketBreakSettings();

    /** 进度渲染层（模块启用时注册、关闭时注销） */
    private final PacketBreakRenderer renderer = new PacketBreakRenderer(this);

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  内部状态
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 等待挖掘的方块队列（串行流水线，一次只挖一个） */
    private final List<PacketBreakTarget> queue = new ArrayList<>();

    /** 当前唯一活跃方块：START 已发出、尚未确认破坏（对应服务端唯一挖掘槽） */
    private PacketBreakTarget active;

    /** 范围模式失败抑制表，键为不可变坐标，值为允许再次尝试的客户端 tick。 */
    private final Map<BlockPos, Integer> retryAfterTick = new HashMap<>();

    /** 本次会话已成功破坏的方块计数（播报节流用） */
    private int successCount;

    /** 冲突巡检计数（每 20 tick 查一次，避免高频遍历模块列表） */
    private int conflictCheckTick;

    /** 服务端累计确认的预测序列号（ACK 在主线程派发；旧反馈桥最高序列号的等价字段） */
    private int highestAcknowledgedSequence = -1;

    /** 最近一次权威方块更新（主线程写入并消费；旧反馈桥 update 槽的等价字段） */
    private PacketBreakUpdate pendingUpdate;

    /**
     * 被取消的原版 START 请求（网络线程写、主线程取）。
     *
     * <p>发包规则在网络线程执行，禁止触碰世界状态，因此只把坐标存进这个单槽，由主线程
     * {@link #consumeBreakRequest()} 取走并按旧实现同一顺序做「可破坏性判定 → 入队」。</p>
     */
    private volatile BlockPos breakRequest;

    /** 等待执行的转向回调（本刻发旋转包，下一句 tick 执行；见类注释「转向发包」） */
    private Runnable pendingRotateAction;

    /** 一次权威方块更新的只读快照（旧反馈桥 {@code AuthoritativeUpdate} 的等价形态） */
    private record PacketBreakUpdate(BlockPos pos, BlockState state, boolean direct) {
    }

    public PacketInstantBreakModule() {
        super(MODULE_ID, MESSAGE_MODULE, "combat", PacketBreakTexts.DESCRIPTION);
    }

    /**
     * 图标字形（Material Symbols 的 {@code bolt}）。
     *
     * <p>已按开发习惯第 140 条验真：解析 {@code MaterialSymbolsRounded.ttf} 的 cmap 与 post 字形名，
     * 该码点存在且与项目内既有图标无占用冲突。用户 2026-09-18 反馈「加上图标」后补。</p>
     */
    private static final String ICON = "\uEA0B";

    @Override
    public String icon() {
        return ICON;
    }

    /** 分类内排序：战斗分类第四位（杀戮光环 → 飞行绕过 → 发包防踢 → 发包秒破） */
    @Override
    public int order() {
        return 40;
    }

    /** 设置载体（控制台页面读写） */
    public PacketBreakSettings settings() {
        return settings;
    }

    // ── 只读状态（渲染层与控制台概览页读它，不另建第二份状态） ──

    /** 待挖掘队列：只读视图，仅供本模块渲染层按队列顺序遍历（禁止修改） */
    public List<PacketBreakTarget> queuedTargets() {
        return queue;
    }

    /** 当前活跃目标；无目标返回 {@code null} */
    public PacketBreakTarget activeTarget() {
        return active;
    }

    /** 本次会话已成功破坏的方块数 */
    public int successCount() {
        return successCount;
    }

    /** 失败冷却中的坐标数（概览读数） */
    public int failureCooldownCount() {
        return retryAfterTick.size();
    }

    /** 目标方块名单项数（概览读数） */
    public int targetBlockCount() {
        return settings.targetBlocks.size();
    }

    /** 队列长度（概览读数） */
    public int queueSize() {
        return queue.size();
    }

    /**
     * 计算服务器侧真实进度（0~1）：与 ServerPlayerGameMode 破坏公式同源。
     *
     * <p>逐字照旧实现的 {@code computeProgress}，渲染层据此画百分比与收缩框。</p>
     */
    public double progressOf(PacketBreakTarget target) {
        if (target == null) return 0;
        if (target.phase == PacketBreakTarget.Phase.AWAITING_CONFIRM) return 1;
        if ((target.phase != PacketBreakTarget.Phase.MINING
            && target.phase != PacketBreakTarget.Phase.STOP_PENDING)
            || target.startTick == PacketBreakTarget.TICK_NONE) return 0;

        double threshold = currentThreshold();
        if (mc.player == null) return 0;
        return Math.min(1, target.destroyDelta * (mc.player.tickCount - target.startTick + 1) / threshold);
    }

    // ── 设置持久化 ──

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
    }

    /** 立即写回设置（界面改动即时生效，第 173 条） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    // ── 生命周期 ──

    /**
     * 单人世界闸门：旧 {@code onActivate} 的第一段（原因文案照旧）。
     *
     * <p>旧实现只拦「在单人世界里点开启」。本模块虽非默认开启，但玩家在主菜单（或进服前的
     * 任意时刻）开启后进单人世界同样绕得过去，因此与本包其它环境受限模块统一交给框架的
     * {@link Module#environmentRefusal()}，两个时机都拦。</p>
     */
    @Override
    public String environmentRefusal() {
        return GameProbe.isSingleplayer() ? "§c单人世界无需发包秒破" : null;
    }

    /**
     * 旧 {@code onActivate}：冲突检测拒启 → 结束原版残留挖掘状态 → 清状态 → 启动报告
     * （单人世界闸门已交给框架）。
     */
    @Override
    protected void onEnable() {
        // 冲突检测：本项目自动挖矿的快速破坏（秒破）开启时拒启
        List<String> conflicts = collectConflicts();
        if (!conflicts.isEmpty()) {
            ModuleManager.setEnabledSilently(MODULE_ID, false);
            notifyError("你已开启以下自带功能，请关闭后再打开发包秒破：");
            for (String conflict : conflicts) {
                notify("§c  §6· §f" + conflict);
            }
            return;
        }

        // 接管前先结束原版客户端可能残留的挖掘状态，避免它与纯发包状态机同时占槽
        if (mc.gameMode != null && mc.gameMode.isDestroying()) {
            mc.gameMode.stopDestroyBlock();
        }

        // 重置内部状态
        queue.clear();
        active = null;
        retryAfterTick.clear();
        clearFeedback();
        successCount = 0;
        conflictCheckTick = 0;
        breakRequest = null;
        pendingRotateAction = null;

        // 唯一参与发包链路的入口：规则在网络线程被同步调用（见类注释的线程模型）
        // 用高优先级注册：必须抢在「发包防踢」的限速判据之前拿到原版 START（见 GATE_PRIORITY 注释）
        SendGate.register(MODULE_ID, GATE_PRIORITY, this::onSendRule);
        TacticalCoordinator.addListener(this);
        // 运行期标志：让 ESP 全局设置里的「瞄准方块高亮」（那个白框）在秒破期间闭嘴
        // （用户 2026-09-19：「发包秒破跟我 esp 的白色选择框有冲突」；与自动挖矿同一处理）
        EspGlobalSettings.get().setInstantBreakRunning(true);
        // 渲染层随模块开关注册 / 注销，禁止常驻注册
        WorldOverlay.register(MODULE_ID, renderer::render);

        reportStartupInfo();
    }

    /** 旧 {@code onDeactivate}：清队列 → 中止在挖方块 → 恢复槽位 → 清状态与闸门 */
    @Override
    protected void onDisable() {
        queue.clear();
        if (active != null && active.startTick != PacketBreakTarget.TICK_NONE && mc.player != null) {
            ClientPacketSender.sendBreakAbort(active.pos, active.direction);
        }
        // 恢复被自动换工具改掉的槽位，避免离服后槽位错乱
        restoreSlotIfNeeded();
        active = null;
        retryAfterTick.clear();
        clearFeedback();
        breakRequest = null;
        pendingRotateAction = null;
        SendGate.unregister(MODULE_ID);
        TacticalCoordinator.removeListener(this);
        // 还回 ESP 全局设置里的「瞄准方块高亮」（运行期间它是被压掉的）
        EspGlobalSettings.get().setInstantBreakRunning(false);
        WorldOverlay.unregister(MODULE_ID);
    }

    /** 旧 {@code subscribedEvents}：每刻 + 方块反馈 + 进服 / 离服（TICK 由运行时走 onTick） */
    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(
            ClientEventType.TICK,
            ClientEventType.SERVER_BLOCK,
            ClientEventType.JOIN_SERVER,
            ClientEventType.DISCONNECT
        );
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null || !isEnabled()) return;
        switch (event.type()) {
            case SERVER_BLOCK -> onServerBlock(event.block());
            case JOIN_SERVER -> onGameJoined();
            case DISCONNECT -> onGameLeft();
            default -> {
                // TICK 由运行时统一走 onTick，其余类型未订阅
            }
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  启动播报 / 冲突检测
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 启动报告：合并成一条多行消息块，只带一次模块前缀 */
    private void reportStartupInfo() {
        StringBuilder report = new StringBuilder();
        report.append("§a§l✓ 发包秒破 · 启动报告");
        report.append("\n§7破坏方式　§8▸ ").append(highlightFunction(settings.breakMode.displayName)).append("§r");
        report.append("\n§7目标模式　§8▸ ").append(highlightFunction(settings.targetMode.displayName)).append("§r");
        if (settings.targetMode == TargetMode.RANGE) {
            report.append("\n§7扫描半径　§8▸ ").append(highlightNumber(settings.range + " 格")).append("§r");
        }
        report.append("\n§7方块间隔　§8▸ ").append(highlightNumber(settings.delay + " tick")).append("§r");
        report.append("\n§7自动换工具§8▸ ").append(settings.autoSwitch ? highlightText("开") : highlightNumber("关")).append("§r");
        notify(report.toString());
    }

    /**
     * 收集当前与发包秒破冲突的功能清单。
     *
     * <p>旧实现的冲突来源有两类：本项目自动挖矿的「快速破坏（秒破）」——与发包秒破同走秒破语义，
     * 会双重发包；以及旧项目框架自带的 7 个发包挖掘类模块。后者在本项目<b>不存在对应物</b>，
     * 按 96 号 D-18-08 全部删除，只保留前者。</p>
     */
    private List<String> collectConflicts() {
        List<String> conflicts = new ArrayList<>();

        Module minerModule = ModuleManager.byId(AutoMinerModule.MODULE_ID);
        if (minerModule instanceof AutoMinerModule miner && miner.isEnabled() && miner.getFastBreak()) {
            conflicts.add("自动挖矿 · 快速破坏（秒破）");
        }

        return conflicts;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  监听反作弊检测（联动服务器检测模块）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    @Override
    public void onAntiCheat(String antiCheatName) {
        if (!isEnabled()) return;

        // 只播报运行时策略，不永久改写用户配置；currentThreshold 每 tick 读取
        // 协调器现态，因此模块晚开启、换服重置与后续恢复都能正确联动。
        if (TacticalCoordinator.hasAdvancedAntiCheat() && settings.breakMode == BreakMode.INSTANT) {
            notify("检测到 " + antiCheatName + "，本会话发包破坏将使用原版速度阈值");
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  瞄准破坏：拦截原版开始破坏（发包规则，网络线程）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 唯一参与发包链路的判据：取消原版 START 并登记请求，改由本模块发包挖掘。
     *
     * <p><b>线程约束</b>：本方法在网络线程被同步调用，因此<b>只读 volatile 设置、只写 volatile 请求槽</b>，
     * 不读世界、不入队、不派发（可破坏性判定与入队由主线程 {@link #consumeBreakRequest()} 完成）。</p>
     */
    private SendDecision onSendRule(SendView view) {
        if (view == null || view.kind() != SendView.Kind.PLAYER_ACTION) return SendDecision.pass();
        if (settings.targetMode != TargetMode.AIM) return SendDecision.pass();
        if (!ACTION_START_DESTROY.equals(view.detail())) return SendDecision.pass();

        BlockPos pos = view.pos();
        if (pos == null) return SendDecision.pass();

        breakRequest = pos.immutable();
        return SendDecision.cancel();
    }

    /** 主线程消费被取消的原版 START；旧 {@code onStartBreakingBlock} 的判定与入队顺序逐字照旧 */
    private void consumeBreakRequest() {
        BlockPos pos = breakRequest;
        if (pos == null) return;
        breakRequest = null;
        if (mc.player == null || mc.level == null) return;

        if (!canBreak(pos)) return;

        // 旧实现的事件带命中面；本项目发包视图不含朝向，改按玩家→方块的最近方向求面（语义一致）
        enqueue(pos, breakFace(pos));
    }

    /**
     * 原版破坏入口的主线程接管（由 {@code MultiPlayerGameModePacketBreakMixin} 调用）。
     *
     * <p>旧实现在开始破坏事件里 {@code cancel()}，整条原版链路（本地破坏预测 + 原版 START 包）
     * 一并被拦下；本项目发包闸门只能拦包，因此由专属 Mixin 在原版 {@code startDestroyBlock}
     * 入口再拦一次。判据与 {@link #onSendRule(SendView)} 一致（仅 AIM 模式），
     * 命中面沿用原版传入的 {@code direction}（等价旧事件的 {@code event.direction}）。</p>
     *
     * @return {@code true} 表示本模块接管，调用方须让原版方法直接返回 {@code true}
     */
    public boolean interceptStart(BlockPos pos, Direction direction) {
        if (!isEnabled()) return false;
        if (settings.targetMode != TargetMode.AIM) return false;
        if (mc.player == null || mc.level == null) return false;
        if (pos == null || direction == null) return false;
        if (!canBreak(pos)) return false;

        enqueue(pos, direction);
        return true;
    }

    /**
     * 记录服务端累计 ACK 与权威方块更新。
     *
     * <p>旧实现发生在收包事件里（原版处理之前），反馈桥只做线程安全快照，状态推进统一留给
     * 每刻主循环，避免网络线程直接修改队列。本项目由核心在网络线程按关注坐标筛出快照、
     * 到主线程派发 {@link ClientEventType#SERVER_BLOCK}，判定顺序与旧实现完全一致。</p>
     */
    private void onServerBlock(ServerBlockEvent block) {
        if (block == null) return;
        if (block.kind() == ServerBlockEvent.Kind.ACK) {
            // 服务端 ACK 是累计确认，保留收到的最高序列号即可
            highestAcknowledgedSequence = Math.max(highestAcknowledgedSequence, block.sequence());
            return;
        }
        if (block.kind() == ServerBlockEvent.Kind.UPDATE) {
            pendingUpdate = new PacketBreakUpdate(block.pos(), block.state(), block.direct());
        }
    }

    /** 离服时清掉坐标、序列号和跨线程反馈，禁止旧世界状态泄漏到下一台服务器。 */
    private void onGameLeft() {
        restoreSlotIfNeeded();
        queue.clear();
        active = null;
        retryAfterTick.clear();
        breakRequest = null;
        pendingRotateAction = null;
        clearFeedback();
    }

    /** 模块跨服保持开启时，新世界必须从空状态重新接收目标。 */
    private void onGameJoined() {
        queue.clear();
        active = null;
        retryAfterTick.clear();
        breakRequest = null;
        pendingRotateAction = null;
        clearFeedback();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  主循环
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    @Override
    public void onTick(Minecraft client) {
        if (client == null || client.player == null || client.level == null) return;

        // 框架适配：先消化网络线程留下的请求与上一刻排队的转向回调
        consumeBreakRequest();
        flushRotation();
        if (client.player == null || client.level == null) return;

        int currentTick = client.player.tickCount;
        retryAfterTick.entrySet().removeIf(entry -> entry.getValue() <= currentTick);

        // 冲突巡检：运行中检测到冲突功能开启，自动停机
        if (++conflictCheckTick >= CONFLICT_CHECK_INTERVAL_TICKS) {
            conflictCheckTick = 0;
            if (!collectConflicts().isEmpty()) {
                ModuleManager.setEnabledSilently(MODULE_ID, false);
                notifyError("你已开启冲突的自带功能，已自动停止发包秒破");
                return;
            }
        }

        // 服务端反馈优先于本地推断；ACK 只证明收包，权威方块更新才证明成功或回弹
        if (consumeServerFeedback()) return;

        if (active != null && hasServerChanged(active)) {
            finishActive(true, null, false);
            return;
        }

        // 服务器卡顿 / 拉回冷却时暂停发包，避免顶风作案
        if (settings.respectLag
            && (TacticalCoordinator.isServerLagging() || TacticalCoordinator.isRubberBandCooldown())) {
            return;
        }

        // 范围自动模式：扫描周围目标方块入队
        if (settings.targetMode == TargetMode.RANGE) {
            scanRange();
        }

        // 有过期任务先清理（方块已变化才清，距离留给出列时判定）
        queue.removeIf(this::hasServerChanged);

        if (active == null) {
            // 无活跃方块：队首出列，进入等待间隔倒计时
            if (queue.isEmpty()) return;
            active = queue.remove(0);
            active.phase = PacketBreakTarget.Phase.SCHEDULED;
            active.waitTicks = settings.delay;
        }

        // 活跃方块超距离 / 已变化 → 放弃（范围模式静默丢弃，瞄准模式播报）
        if (isOutOfRange(active)) {
            failActive("超出服务器可交互距离", settings.targetMode == TargetMode.RANGE);
            return;
        }

        switch (active.phase) {
            case SCHEDULED -> {
                if (active.waitTicks-- > 0) return;
                startActive();
            }
            case START_PENDING -> {
                // 未完成的旋转回调兜底：超时直接发包，防卡死
                if (currentTick - active.actionRequestTick >= ROTATE_FALLBACK_TICKS) {
                    sendStartNow(active);
                }
            }
            case MINING -> {
                if (hasMiningToolChanged(active)) {
                    retryActive("挖掘期间手持工具发生变化");
                    return;
                }

                active.destroyDelta = currentDestroyDelta(active);
                if (active.destroyDelta <= 0) {
                    failActive("当前工具破坏速度为 0", false);
                    return;
                }

                // 服务端公式为 delta×(gameTicks-startTick+1)，这里取满足阈值的最早安全 tick
                int elapsed = currentTick - active.startTick;
                if (elapsed >= requiredElapsedTicks(active.destroyDelta, currentThreshold())) {
                    requestStop(active);
                }
            }
            case STOP_PENDING -> {
                if (hasMiningToolChanged(active)) {
                    retryActive("停挖发包前手持工具发生变化");
                    return;
                }
                if (currentTick - active.actionRequestTick >= ROTATE_FALLBACK_TICKS) {
                    sendStopNow(active, false);
                }
            }
            case AWAITING_CONFIRM -> {
                // 正常 TCP 链路只发一次 STOP；仅在未收到该 sequence 的 ACK 时有限重发
                if (!active.stopAcknowledged
                    && currentTick - active.lastStopPacketTick >= STOP_ACK_TIMEOUT_TICKS
                    && active.stopResends < STOP_RESEND_LIMIT) {
                    sendStopNow(active, true);
                } else if (currentTick >= active.confirmDeadlineTick) {
                    retryActive("服务器未确认方块破坏");
                }
            }
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  挖掘流程
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 入队一个目标方块；已在队列或正在挖掘的跳过 */
    private void enqueue(BlockPos pos, Direction dir) {
        if (mc.player == null || mc.level == null) return;
        if (active != null && active.pos.equals(pos)) return;
        Integer retryTick = retryAfterTick.get(pos);
        if (retryTick != null && retryTick > mc.player.tickCount) return;

        for (PacketBreakTarget target : queue) {
            if (target.pos.equals(pos)) return;
        }

        BlockState state = mc.level.getBlockState(pos);
        queue.add(new PacketBreakTarget(pos, state, dir));
    }

    /** 开始挖掘活跃方块：切换工具 → 计算速度 → 转向 → 发 START（带 sequence） */
    private void startActive() {
        PacketBreakTarget target = active;
        if (target == null || mc.player == null || mc.level == null) return;

        // 必须真实切换客户端选中槽；只发槽位包会被 MultiPlayerGameMode 下一 tick 自动改回
        if (settings.autoSwitch) {
            int fastestSlot = findFastestTool(target.originalState);
            int selected = mc.player.getInventory().getSelectedSlot();
            if (fastestSlot >= 0 && selected != fastestSlot) {
                target.previousSlot = selected;
                if (!InventoryAccess.swapWithSelectedHotbar(fastestSlot)) {
                    failActive("最佳工具槽位切换失败", false);
                    return;
                }
                target.switched = true;
            }
        }

        target.miningSlot = mc.player.getInventory().getSelectedSlot();
        target.destroyDelta = currentDestroyDelta(target);
        if (target.destroyDelta <= 0) {
            failActive("手上工具破坏速度为 0", false);
            return;
        }

        target.phase = PacketBreakTarget.Phase.START_PENDING;
        target.actionRequestTick = mc.player.tickCount;
        BlockWatchService.watch(MODULE_ID, target.pos);

        if (settings.rotate) {
            rotateToTarget(target.pos, () -> sendStartNow(target));
        } else {
            sendStartNow(target);
        }
    }

    /** 发送 START_DESTROY_BLOCK：取号 + 登记服务端已知状态（不预测置空，防空气墙） */
    private void sendStartNow(PacketBreakTarget target) {
        if (active != target || target.phase != PacketBreakTarget.Phase.START_PENDING) return;
        if (mc.player == null || mc.level == null) return;
        if (hasServerChanged(target)) {
            finishActive(true, null, false);
            return;
        }
        if (hasMiningToolChanged(target)) {
            retryActive("开始发包前手持工具发生变化");
            return;
        }

        target.destroyDelta = currentDestroyDelta(target);
        // 本项目取号封装在核心内部：把「发包前的累计 ACK 基线 + 1」记成本次序列，
        // 判定语义与旧实现的 isAcknowledged(sequence) 等价（见 isAcknowledged）
        target.startSequence = highestAcknowledgedSequence + 1;
        if (!ClientPacketSender.sendBreakStart(target.pos, target.direction,
            mc.level.getBlockState(target.pos))) {
            failActive("发包通道不可用", false);
            return;
        }
        target.phase = PacketBreakTarget.Phase.MINING;
        target.startTick = mc.player.tickCount;

        if (settings.swing) {
            mc.player.swing(InteractionHand.MAIN_HAND);
        }

        // 硬度为零或创造模式类方块由服务端收到 START 时直接破坏，不再多发一次 STOP
        if (target.destroyDelta >= 1.0F) {
            target.phase = PacketBreakTarget.Phase.AWAITING_CONFIRM;
            target.stopSequence = target.startSequence;
            target.stopTick = target.startTick;
            target.lastStopPacketTick = target.startTick;
            target.confirmDeadlineTick = target.startTick + STOP_DEADLINE_MARGIN;
        } else if (requiredElapsedTicks(target.destroyDelta, currentThreshold()) == 0) {
            // delta 已满足 0.7 时在同一客户端 tick 严格按 START→STOP 顺序真正瞬破
            target.phase = PacketBreakTarget.Phase.STOP_PENDING;
            target.actionRequestTick = mc.player.tickCount;
            sendStopNow(target, false);
        }
    }

    /** 到达服务端 0.7 阈值后请求 STOP；旋转回调完成前保持独立阶段。 */
    private void requestStop(PacketBreakTarget target) {
        if (active != target || target.phase != PacketBreakTarget.Phase.MINING) return;
        target.phase = PacketBreakTarget.Phase.STOP_PENDING;
        target.actionRequestTick = mc.player.tickCount;
        if (settings.rotate) {
            rotateToTarget(target.pos, () -> sendStopNow(target, false));
        } else {
            sendStopNow(target, false);
        }
    }

    /**
     * 实际发送 STOP；只有未收到 ACK 才允许 resend=true 的有限重发。
     *
     * <p>每次重发都取得新 sequence，并刷新最后发包 tick，避免旧实现达到阈值后
     * 连续每 tick 洪泛 STOP。</p>
     */
    private void sendStopNow(PacketBreakTarget target, boolean resend) {
        if (active != target || mc.player == null || mc.level == null) return;
        if (resend) {
            if (target.phase != PacketBreakTarget.Phase.AWAITING_CONFIRM || target.stopAcknowledged) return;
        } else if (target.phase != PacketBreakTarget.Phase.STOP_PENDING) {
            return;
        }
        if (hasServerChanged(target)) {
            finishActive(true, null, false);
            return;
        }
        if (hasMiningToolChanged(target)) {
            retryActive("STOP 发包前手持工具发生变化");
            return;
        }

        target.stopSequence = highestAcknowledgedSequence + 1;
        ClientPacketSender.sendBreakStop(target.pos, target.direction);
        target.stopAcknowledged = false;
        target.lastStopPacketTick = mc.player.tickCount;
        if (resend) {
            target.stopResends++;
        } else {
            target.stopTick = mc.player.tickCount;
            target.stopResends = 0;
            int fullElapsed = requiredElapsedTicks(target.destroyDelta, (float) THRESHOLD_VANILLA);
            int delayedDestroyDeadline = target.startTick + fullElapsed + STOP_DEADLINE_MARGIN;
            int ackRetryDeadline = target.stopTick
                + STOP_ACK_TIMEOUT_TICKS * (STOP_RESEND_LIMIT + 1) + STOP_DEADLINE_MARGIN;
            target.confirmDeadlineTick = Math.max(delayedDestroyDeadline, ackRetryDeadline);
        }
        target.phase = PacketBreakTarget.Phase.AWAITING_CONFIRM;

        // 兼容原配置：ABORT 跟在 STOP 后清裂纹；原版 delayedDestroy 不会被它取消
        if (settings.obscureProgress) {
            ClientPacketSender.sendBreakAbort(target.pos, target.direction);
        }

        // 绕过反作弊：额外补发 ABORT 混淆破坏时序
        if (settings.bypassAnticheat) {
            ClientPacketSender.sendBreakAbort(target.pos.above(), target.direction);
        }
    }

    /** 主线程消费网络反馈；ACK 只阻止重复包，方块权威状态负责判定成功与回弹。 */
    private boolean consumeServerFeedback() {
        if (active == null) return false;

        if (isAcknowledged(active.startSequence)) active.startAcknowledged = true;
        if (isAcknowledged(active.stopSequence)) active.stopAcknowledged = true;

        PacketBreakUpdate update = pendingUpdate;
        pendingUpdate = null;
        if (update == null) return false;
        if (!update.pos().equals(active.pos)) return false;
        if (update.state().getBlock() != active.block) {
            finishActive(true, null, false);
            return true;
        }

        // 原版对合法 START/STOP 不会回发同状态单方块包；该包代表权限拒绝、破坏失败或回弹
        if (update.direct() && active.startSequence >= 0) {
            failActive("服务器拒绝破坏或发生方块回弹", false);
            return true;
        }
        return false;
    }

    /**
     * 累计 ACK 是否已覆盖给定序列。
     *
     * <p>本项目把预测取号封装在核心内部、不外露序列号，因此旧实现的
     * {@code 最高确认序号 >= 指定序号} 改为「指定序号」这里存的是<b>发包前累计 ACK 基线 + 1</b>：
     * 只要收到过任何一条晚于本次发包的 ACK，就说明本次包已被服务端确认，判定时机与用途一字未改。</p>
     */
    private boolean isAcknowledged(int baselinePlusOne) {
        return baselinePlusOne >= 0 && highestAcknowledgedSequence >= baselinePlusOne;
    }

    /** 超时等可恢复失败会先 ABORT，再以全新 sequence 完整重走一次 START。 */
    private void retryActive(String reason) {
        if (active == null) return;
        if (active.attempts >= START_RETRY_LIMIT) {
            failActive(reason, false);
            return;
        }

        PacketBreakTarget retry = active;
        if (retry.startTick != PacketBreakTarget.TICK_NONE && mc.player != null) {
            ClientPacketSender.sendBreakAbort(retry.pos, retry.direction);
        }
        restoreSlotIfNeeded();
        clearFeedback();
        retry.attempts++;
        retry.resetForRetry(settings.delay);
        active = retry;
    }

    /** 不可恢复失败统一清理服务端槽位、工具槽和反馈，并给范围扫描设置冷却。 */
    private void failActive(String reason, boolean silent) {
        if (active == null) return;
        if (active.startTick != PacketBreakTarget.TICK_NONE && mc.player != null) {
            ClientPacketSender.sendBreakAbort(active.pos, active.direction);
        }
        if (mc.player != null) {
            retryAfterTick.put(active.pos, mc.player.tickCount + FAILURE_COOLDOWN_TICKS);
        }
        finishActive(false, reason, silent);
    }

    /** 结束当前方块：恢复槽位并按节流播报；silent 用于范围模式静默丢弃（防刷屏） */
    private void finishActive(boolean success, String reason, boolean silent) {
        if (active == null) return;
        restoreSlotIfNeeded();
        clearFeedback();

        PacketBreakTarget done = active;
        active = null;

        if (success) {
            successCount++;
            // 成功播报始终按节流，范围模式同样可见进度
            if (successCount % SUCCESS_REPORT_STEP == 0) {
                notify("§a✓ 发包秒破 §8▸ 已破坏 " + highlightNumber(successCount + " 块"));
            }
        } else if (!silent) {
            notify("§c✗ 放弃方块 §8▸ " + highlightText(done.block.getName().getString())
                + (reason != null ? " §8▸ " + reason : ""));
        }
    }

    /** 自动换工具切走后恢复原槽位；用户手动换槽时尊重用户选择，不强行覆盖。 */
    private void restoreSlotIfNeeded() {
        if (active != null && active.switched && mc.player != null) {
            if (mc.player.getInventory().getSelectedSlot() == active.miningSlot) {
                InventoryAccess.swapWithSelectedHotbar(active.previousSlot);
            }
            active.switched = false;
        }
    }

    /** 清空跨线程反馈与关注坐标（旧反馈桥 {@code clear()} 的等价形态） */
    private void clearFeedback() {
        pendingUpdate = null;
        highestAcknowledgedSequence = -1;
        BlockWatchService.clear(MODULE_ID);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  判定工具方法
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 服务器是否已改变该位置方块（已破坏或变更 → 客户端收到同步） */
    private boolean hasServerChanged(PacketBreakTarget target) {
        return mc.level == null || mc.level.getBlockState(target.pos).getBlock() != target.block;
    }

    /** 是否超出服务器挖掘作用距离（官方 START/STOP 都要过 isWithinBlockInteractionRange） */
    private boolean isOutOfRange(PacketBreakTarget target) {
        return mc.player == null || !mc.player.isWithinBlockInteractionRange(target.pos, 1.0);
    }

    /** 当前选中工具是否已偏离 START 时工具；偏离后旧速度快照不再等于服务端速度。 */
    private boolean hasMiningToolChanged(PacketBreakTarget target) {
        return mc.player == null || mc.player.getInventory().getSelectedSlot() != target.miningSlot;
    }

    /** 每 tick 重算官方破坏速度，药水、落地/水下状态变化不会造成过早 STOP。 */
    private float currentDestroyDelta(PacketBreakTarget target) {
        if (mc.player == null || mc.level == null) return 0;
        BlockState currentState = mc.level.getBlockState(target.pos);
        return currentState.getDestroyProgress(mc.player, mc.level, target.pos);
    }

    /** 返回服务端公式中满足 threshold 的最小 elapsed tick，修正旧实现提前一 tick 的问题。 */
    private int requiredElapsedTicks(float delta, float threshold) {
        if (delta <= 0) return Integer.MAX_VALUE;
        double requiredSamples = Math.ceil(threshold / (double) delta);
        if (requiredSamples >= Integer.MAX_VALUE) return Integer.MAX_VALUE - 1;
        int elapsed = Math.max(0, (int) requiredSamples - 1);
        // 用 float 乘法复核，与服务端源码的 float destroyProgress 完全一致
        while (delta * (elapsed + 1) < threshold && elapsed < Integer.MAX_VALUE - 1) elapsed++;
        while (elapsed > 0 && delta * elapsed >= threshold) elapsed--;
        return elapsed;
    }

    /** 当前模式对应的服务端 STOP 阈值。 */
    private float currentThreshold() {
        boolean runtimeSafeMode = settings.breakMode == BreakMode.VANILLA
            || TacticalCoordinator.hasAdvancedAntiCheat();
        return runtimeSafeMode ? (float) THRESHOLD_VANILLA : (float) THRESHOLD_INSTANT;
    }

    /**
     * 当前阈值下「满足服务端卡点所需的最少已挖 tick」。
     *
     * <p>供渲染层的「百分比+剩余tick」标签复用同一份公式（第 169 条：同源计算只留一份）。
     * {@code delta <= 0} 时返回 {@link Integer#MAX_VALUE}，调用方按旧实现的
     * {@code Math.max(delta, 1.0E-4F)} 兜底后再传入。</p>
     */
    public int requiredElapsedTicksToStop(float delta) {
        return requiredElapsedTicks(delta, currentThreshold());
    }

    /** 范围自动模式：扫描以玩家为中心的立方体，匹配目标方块则入队 */
    private void scanRange() {
        if (queue.size() >= MAX_QUEUE) return;
        if (mc.player == null) return;

        Set<Block> targets = targetBlockSet();
        int radius = settings.range;
        BlockPos center = mc.player.blockPosition();

        for (int dx = -radius; dx <= radius && queue.size() < MAX_QUEUE; dx++) {
            for (int dy = -radius; dy <= radius && queue.size() < MAX_QUEUE; dy++) {
                for (int dz = -radius; dz <= radius && queue.size() < MAX_QUEUE; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (pos.equals(center)) continue;
                    if (active != null && active.pos.equals(pos)) continue;
                    if (isQueued(pos)) continue;
                    if (!mc.player.isWithinBlockInteractionRange(pos, 1.0)) continue;

                    BlockState state = mc.level.getBlockState(pos);
                    if (!canBreak(pos, state)) continue;

                    // 白名单非空时只挖白名单内方块
                    if (!targets.isEmpty() && !targets.contains(state.getBlock())) continue;

                    enqueue(pos, breakFace(pos));
                }
            }
        }
    }

    /** 目标方块名单（登记 ID → 方块，认不出的条目跳过）；名单为空表示不筛方块 */
    private Set<Block> targetBlockSet() {
        Set<Block> blocks = new HashSet<>();
        for (String id : settings.targetBlocks) {
            Block block = blockOf(id);
            if (block != null) blocks.add(block);
        }
        return blocks;
    }

    /** 登记 ID → 方块；ID 非法或不存在返回 {@code null}（不猜） */
    private static Block blockOf(String blockId) {
        if (blockId == null || blockId.isBlank()) return null;
        Identifier id = Identifier.tryParse(blockId);
        return id == null ? null : BuiltInRegistries.BLOCK.getValue(id);
    }

    /** 目标是否已在队列中 */
    private boolean isQueued(BlockPos pos) {
        for (PacketBreakTarget target : queue) {
            if (target.pos.equals(pos)) return true;
        }
        return false;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  框架适配原语（旧框架白送、本项目需自备）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 该坐标此刻是否可破坏。
     *
     * <p>判据对齐本项目既有口径（{@code MiningVeinMiner} / {@code WaterEscapeBreaker} /
     * {@code MiningFastBreakController}）：非空气、非流体、硬度不为负（基岩 / 屏障类不可挖）。</p>
     */
    private boolean canBreak(BlockPos pos) {
        if (mc.level == null || pos == null) return false;
        return canBreak(pos, mc.level.getBlockState(pos));
    }

    private boolean canBreak(BlockPos pos, BlockState state) {
        if (mc.level == null || pos == null || state == null) return false;
        if (state.isAir() || !state.getFluidState().isEmpty()) return false;
        return state.getBlock().defaultDestroyTime() >= 0.0F;
    }

    /** 破坏朝向：从玩家指向方块的方向（原版对破坏包不校验朝向，这里只求语义自然） */
    private Direction breakFace(BlockPos pos) {
        if (mc.player == null || pos == null) return Direction.UP;
        return Direction.getNearest(pos.subtract(mc.player.blockPosition()), Direction.UP);
    }

    /**
     * 最快工具所在槽（复合索引 0~35；未找到返回 -1）。
     *
     * <p>判据用「严格更快才替换」，当前手持物品的破坏速度作为初始值：两把速度相同的工具保持
     * 玩家自己的选择，不破坏原有偏好（与 {@code MiningFastBreakController#ensureFasterTool} 同口径）。
     * 扫描范围与 {@code InventoryAccess.find(filter, 0, 35)} 的上界约定一致。</p>
     */
    private int findFastestTool(BlockState state) {
        if (mc.player == null || state == null) return -1;
        int selected = mc.player.getInventory().getSelectedSlot();
        float bestSpeed = mc.player.getInventory().getItem(selected).getDestroySpeed(state);
        int bestSlot = -1;
        for (int slot = 0; slot < BACKPACK_SCAN_LIMIT; slot++) {
            float speed = mc.player.getInventory().getItem(slot).getDestroySpeed(state);
            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot = slot;
            }
        }
        return bestSlot;
    }

    /**
     * 转向请求：本刻发一次旋转包并置客户端朝向，回调排在下一句 tick 执行。
     *
     * <p>旧实现的 {@code Rotations.rotate(yaw, pitch, 50, callback)} 会在若干刻内平滑转头再执行回调，
     * 因此旧状态机为「等待旋转回调」单独留了 {@code START_PENDING} / {@code STOP_PENDING}
     * 两个阶段与 {@code ROTATE_FALLBACK_TICKS} 兜底。本项目没有旋转调度层，故本刻直接把朝向转到位、
     * 回调下一句 tick 执行：阶段与兜底逻辑一字未动，只是回调延迟从「若干刻」缩短为 1 刻。</p>
     */
    private void rotateToTarget(BlockPos pos, Runnable afterRotate) {
        if (mc.player == null || pos == null) {
            afterRotate.run();
            return;
        }
        float yaw = yawTowards(pos);
        float pitch = pitchTowards(pos);
        ClientPacketSender.sendMoveRotation(yaw, pitch, mc.player.onGround(), mc.player.horizontalCollision);
        mc.player.setYRot(yaw);
        mc.player.setXRot(pitch);
        pendingRotateAction = afterRotate;
    }

    /** 执行上一刻排队的转向回调（旧旋转调度层完成后回调解锁状态机的等价物） */
    private void flushRotation() {
        Runnable action = pendingRotateAction;
        if (action == null) return;
        pendingRotateAction = null;
        action.run();
    }

    /** 对准方块中心所需偏航角（旧 {@code Rotations.getYaw(BlockPos)} 的等价计算） */
    private float yawTowards(BlockPos pos) {
        double dx = pos.getX() + 0.5 - mc.player.getX();
        double dz = pos.getZ() + 0.5 - mc.player.getZ();
        return (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
    }

    /** 对准方块中心所需俯仰角（旧 {@code Rotations.getPitch(BlockPos)} 的等价计算） */
    private float pitchTowards(BlockPos pos) {
        double dx = pos.getX() + 0.5 - mc.player.getX();
        double dy = pos.getY() + 0.5 - (mc.player.getY() + mc.player.getEyeHeight());
        double dz = pos.getZ() + 0.5 - mc.player.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        return (float) (-Math.toDegrees(Math.atan2(dy, horizontal)));
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  播报（旧基类的颜色码包装逐字保留）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 普通消息（旧基类：前缀 + {@code §f} + 正文） */
    private void notify(String message) {
        ClientChat.send(MESSAGE_MODULE, "§f" + message);
    }

    /** 警告消息（旧基类：{@code §e§l} + 正文） */
    private void warning(String message) {
        notify("§e§l" + message);
    }

    /** 错误消息（旧基类：{@code §6§l} + 正文） */
    private void notifyError(String message) {
        notify("§6§l" + message);
    }

    /** 文本高亮（绿色粗体）——旧基类 {@code highlightText} 逐字 */
    private static String highlightText(String text) {
        return "§a§l" + text + "§r§f§l";
    }

    /** 数值 / 阈值高亮（黄色粗体）——旧基类 {@code highlightNumber} 逐字 */
    private static String highlightNumber(String text) {
        return "§e§l" + text + "§r§f§l";
    }

    /** 功能名高亮（蓝色粗体）——旧基类 {@code highlightFunction} 逐字 */
    private static String highlightFunction(String text) {
        return "§b§l" + text + "§r§f§l";
    }

    // ── 界面 ──

    /** 配置页 = 薄壳模块页 {@link PacketBreakPage} + 整屏控制台（概览 + 旧 4 个设置组） */
    @Override
    public ModulePage page() {
        return new PacketBreakPage(this);
    }
}
