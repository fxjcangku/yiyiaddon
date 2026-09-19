package com.yiyiaddon.feature.bonemeal;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.bonemeal.config.BonemealSettings;
import com.yiyiaddon.feature.bonemeal.config.BonemealTexts;
import com.yiyiaddon.feature.bonemeal.model.TriggerMode;
import com.yiyiaddon.feature.bonemeal.render.BoneMealEspRenderer;
import com.yiyiaddon.feature.bonemeal.service.BoneMealExecutor;
import com.yiyiaddon.feature.bonemeal.service.BoneMealScanner;
import com.yiyiaddon.feature.bonemeal.ui.BonemealPage;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Set;

/**
 * 自动骨粉：自动催熟周围的农作物 / 树苗 / 花卉 / 蘑菇 / 水下下界方块，缺骨粉时自动从背包补给。
 *
 * <p><b>旧项目对应物</b>：{@code bonemeal/AutoBoneMeal.java}（656 行）。触发模式分流、节流与每轮上限、
 * 发送队列的补入与去重、按距离排序、无目标提示与去抖、骨粉耗尽的自动暂停与恢复、启动报告、
 * 自检缺项、反作弊自动降速、ESP 候选绘制与 HUD 状态串，全部逐条对照搬运；设置项与全部用户可见
 * 文案逐字保留（清单见 {@link BonemealTexts}）。</p>
 *
 * <p><b>分层</b>（第 48 条八类）：本类是<b>功能入口与流程控制</b>；目标识别与合法性校验在
 * {@link BoneMealScanner}（service），骨粉查找与催熟发包在 {@link BoneMealExecutor}（service），
 * 世界渲染在 {@link BoneMealEspRenderer}（render），设置与文案在 config，界面在 ui。</p>
 *
 * <p><b>框架适配清单（旧 → 新，逐条）</b></p>
 * <ol>
 *     <li><b>每刻推进</b>：旧 {@code @EventHandler onTick(TickEvent.Pre)} → 本项目由运行时调度
 *         {@link #onTick(Minecraft)}（{@code subscribedEvents} 仍声明 {@code TICK}，与旧声明对齐）。</li>
 *     <li><b>世界就绪</b>：旧 {@code onActivate} 首段 {@code if (mc.player == null || mc.level == null
 *         || mc.gameMode == null) { notifyError("必须在进入世界后才能启动模块。"); mc.execute(this::toggle); }}
 *         → 本项目同形放在 {@link #onEnable()}（与自动箱子 / 自动挖矿同一写法），
 *         文案与「延到下一帧再关」的时序一字未改。</li>
 *     <li><b>自检</b>：旧 {@code reportSelfCheck(selfCheck())} → 运行时的 {@link #selfCheck()}：
 *         缺项由 {@code ModuleManager} 统一「聊天 + 屏幕中间弹窗」播报并拦住启动（第 176~179 条），
 *         缺项文案逐字保留。未进入世界时不做自检（由第 2 条的世界就绪判断负责）。</li>
 *     <li><b>反作弊联动</b>：旧订阅 {@code TacticalCoordinator.AntiCheatDetectedEvent}
 *         → {@link TacticalCoordinator#addListener}（启用注册 / 关闭注销），判定关键字与播报文案原样。</li>
 *     <li><b>视角包</b>：旧直接构造 {@code ServerboundMovePlayerPacket.Rot} → {@code ClientPacketSender}。</li>
 *     <li><b>背包</b>：旧 {@code InvUtils} → {@code InventoryAccess}（见 {@link BoneMealExecutor}）。</li>
 *     <li><b>3D 渲染</b>：旧 {@code Render3DEvent} → {@link WorldOverlay} 世界渲染层
 *         （模块启用注册、关闭注销），几何交给 {@link BoneMealEspRenderer}。</li>
 *     <li><b>HUD 状态串</b>：旧 {@code getInfoString()} 是旧框架 HUD 的接口，本项目没有 HUD 设施，
 *         因此该串原样保留为 {@link #hudStatus()}（含「§c无骨粉 / §aN 块 / §7等待准星 / §7扫描中」四态），
 *         显示位置改到控制台的状态条与概览页（旧文案一字未改，只是换承载）。</li>
 *     <li><b>播报</b>：旧基类 {@code notify}（正文前加 {@code §f}）与 {@code notifyError}（{@code §6§l}）
 *         在本类内逐字保留，正文经 {@link ClientChat#send} 输出；三个高亮包装（文本 §a§l /
 *         数值 §e§l / 功能 §b§l）与旧基类逐字一致。</li>
 * </ol>
 *
 * <p><b>有意保留的旧行为（不做「顺手修」，仅登记）</b>：换服 / 换维度时旧实现只清候选、不清发送队列，
 * 队列里可能残留上一个服务器的坐标；本项目按 1:1 口径同样保留（模块的会话状态复位只做旧实现做过的那些）。</p>
 *
 * @author yiyijia
 */
public final class AutoBoneMealModule extends Module implements TacticalCoordinator.Listener {

    /** 模块 ID（状态文件键 / 快捷键键名后缀 / 渲染层所有者） */
    public static final String MODULE_ID = "bonemeal";

    /** 模块中文显示名（播报前缀与控制台标题共用） */
    public static final String MESSAGE_MODULE = BonemealTexts.MODULE_NAME;

    /** 反作弊自动降速抬到的动作节流值（旧 {@code tickDelay.set(3)} 的字面量 3） */
    private static final int AUTO_THROTTLE_DELAY = 3;

    /** 触发自动降速的反作弊名关键字（逐字照旧 {@code contains("Grim")} 与 {@code contains("Matrix")}） */
    private static final String ANTI_CHEAT_GRIM = "Grim";
    private static final String ANTI_CHEAT_MATRIX = "Matrix";

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体（控制台页面读写） */
    private final BonemealSettings settings = new BonemealSettings();

    /** 目标识别器（候选收集 / 合法性校验 / 遮挡射线） */
    private final BoneMealScanner scanner = new BoneMealScanner();

    /** 催熟执行体（找骨粉 / 视角同步 / 发包催熟） */
    private final BoneMealExecutor executor = new BoneMealExecutor();

    /** ESP 绘制层（模块启用时注册、关闭时注销） */
    private final BoneMealEspRenderer renderer = new BoneMealEspRenderer(this);

    // ━━━ 内部状态（与旧实现逐一对应） ━━━

    /** 节流计数（旧 {@code tickCounter}） */
    private int tickCounter;

    /** 本刻扫描到的全部候选目标，供 ESP 渲染与催熟使用（旧 {@code candidates}） */
    private final List<BlockPos> candidates = new ArrayList<>();

    /** 待发送队列：范围扫描收集到的目标按节流分帧发出（旧 {@code sendQueue}） */
    private final Deque<BlockPos> sendQueue = new ArrayDeque<>();

    /** 是否因骨粉耗尽而自动暂停（旧 {@code pausedNoBoneMeal}） */
    private boolean pausedNoBoneMeal;

    /** 上一帧是否有候选目标（旧 {@code hadTargetsLastTick}，用于无目标提示防刷屏） */
    private boolean hadTargetsLastTick = true;

    public AutoBoneMealModule() {
        super(MODULE_ID, MESSAGE_MODULE, "automation", BonemealTexts.DESCRIPTION);
    }

    /**
     * 图标字形（Material Symbols 的 {@code compost}）。
     *
     * <p>已按开发习惯第 140 条验真：解析 {@code MaterialSymbolsRounded.ttf} 的 cmap 与 post 字形名，
     * 该码点存在且与项目内既有图标无占用冲突。用户 2026-09-18 反馈「加上图标」后补。</p>
     */
    private static final String ICON = "\uE761";

    @Override
    public String icon() {
        return ICON;
    }

    /** 分类内排序：自动化分类第三位（自动重生 → 自动农场 → 自动骨粉 → 自动挖矿 → …） */
    @Override
    public int order() {
        return 30;
    }

    /** 设置载体（控制台页面读写） */
    public BonemealSettings settings() {
        return settings;
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

    /** 立即写回设置（界面改动与自动降速改值时即时生效，第 173 条） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    // ── 只读状态（渲染层与控制台读它，不另建第二份状态） ──

    /** 本刻候选目标：只读视图，供渲染层按原顺序遍历（禁止修改） */
    public List<BlockPos> candidates() {
        return candidates;
    }

    /** 候选目标数（旧 HUD 串「§aN 块」的 N） */
    public int candidateCount() {
        return candidates.size();
    }

    /** 待发送队列长度 */
    public int queueSize() {
        return sendQueue.size();
    }

    /** 是否因骨粉耗尽处于暂停态 */
    public boolean pausedNoBoneMeal() {
        return pausedNoBoneMeal;
    }

    /** 主手 / 副手是否握着骨粉（旧 {@code getInfoString} 的首判据，逐字同口径） */
    public boolean hasBoneMealInHand() {
        if (mc.player == null) return false;
        return mc.player.getOffhandItem().is(Items.BONE_MEAL)
            || mc.player.getMainHandItem().is(Items.BONE_MEAL);
    }

    /**
     * 旧 HUD 状态串（旧 {@code getInfoString} 逐字，四态顺序与配色原样）。
     *
     * <p>本项目没有 HUD 设施，该串改由控制台的状态条与概览页显示；未进入世界时返回 {@code null}
     * （旧实现同样返回 {@code null}，表示框架不画这一格），由界面侧显示为「§8无」。</p>
     */
    public String hudStatus() {
        if (mc.player == null) return null;
        if (!hasBoneMealInHand()) return BonemealTexts.HUD_NO_BONE_MEAL;
        if (!candidates.isEmpty()) return "§a" + candidates.size() + " 块";
        return settings.triggerMode == TriggerMode.准星精准指向
            ? BonemealTexts.HUD_WAITING_CROSSHAIR
            : BonemealTexts.HUD_SCANNING;
    }

    // ── 生命周期 ──

    /**
     * 旧 {@code onActivate}：世界就绪判断 → 状态复位 → 启动报告。
     *
     * <p>旧实现的第二步 {@code reportSelfCheck(selfCheck())} 已由运行时在启用前执行
     * （缺项会拦住启动并给出聊天 + 弹窗两份提示），故这里不再重复。</p>
     */
    @Override
    protected void onEnable() {
        // 世界就绪判断：自检与扫描都会访问世界，先确保已进入世界（旧 onActivate 第一步）
        if (mc.player == null || mc.level == null || mc.gameMode == null) {
            notifyError(BonemealTexts.MUST_IN_WORLD);
            mc.execute(() -> ModuleManager.setEnabled(MODULE_ID, false));
            return;
        }

        // 状态复位（旧 onActivate 的清状态块，逐项对应）
        tickCounter = 0;
        candidates.clear();
        sendQueue.clear();
        scanner.resetHint();
        pausedNoBoneMeal = false;
        hadTargetsLastTick = true;

        // 反作弊检测联动（旧 @EventHandler onAntiCheatDetected 的订阅）
        TacticalCoordinator.addListener(this);
        // ESP 绘制层随模块开关注册（禁止常驻注册）
        WorldOverlay.register(MODULE_ID, renderer::render);

        reportStartupInfo();
    }

    /** 旧 {@code onDeactivate}：清候选与暂停标志；本项目另需注销渲染层与反作弊监听 */
    @Override
    protected void onDisable() {
        candidates.clear();
        pausedNoBoneMeal = false;
        TacticalCoordinator.removeListener(this);
        WorldOverlay.unregister(MODULE_ID);
    }

    /** 旧 {@code subscribedEvents}：旧实现订阅 TickEvent.Pre（真正的每刻推进由运行时调 {@link #onTick}） */
    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK);
    }

    @Override
    public void onEvent(ClientEvent event) {
        // 每刻推进统一走 onTick；本模块没有其它需要判定的核心事件
    }

    // ── 自检 ──

    /**
     * 启用前自检：五组目标方块至少勾选一种可催熟方块，否则不允许启用。
     *
     * <p>缺项文案逐字照旧 {@code selfCheck}：「§a目标方块§f·未勾选任何可催熟方块」。
     * 未进入世界时不做自检（旧实现在 {@link #onEnable()} 里先拦世界就绪）。</p>
     */
    @Override
    public List<String> selfCheck() {
        if (mc.player == null || mc.level == null || mc.gameMode == null) return List.of();

        List<String> missing = new ArrayList<>();
        if (settings.allTargetListsEmpty()) missing.add(BonemealTexts.MISSING_NO_TARGET);
        return missing;
    }

    // ── 反作弊联动 ──

    /**
     * 反作弊检测联动：检测到 Grim / Matrix 时自动抬高动作节流，
     * 规避高频右键连点被服务端判定为自动化交互而踢出（旧 {@code onAntiCheatDetected}）。
     *
     * <p>与旧实现的唯一差别是「改完即时落盘」：本项目第 173 条要求界面/运行期的设置改动都必须走
     * {@link ModuleManager#saveSettings}，否则重启即丢。判据（关键字、3 Tick、只在当前值小于 3 时抬）
     * 与播报文案一字未改。</p>
     */
    @Override
    public void onAntiCheat(String antiCheatName) {
        if (!isEnabled() || !settings.autoThrottle) return;
        if (!antiCheatName.contains(ANTI_CHEAT_GRIM) && !antiCheatName.contains(ANTI_CHEAT_MATRIX)) return;

        if (settings.tickDelay < AUTO_THROTTLE_DELAY) {
            settings.tickDelay = AUTO_THROTTLE_DELAY;
            persistSettings();
            notify(BonemealTexts.AUTO_THROTTLE_PREFIX + antiCheatName
                + BonemealTexts.AUTO_THROTTLE_SUFFIX);
        }
    }

    // ━━━ 每刻主逻辑（旧 onTick:388-469 顺序原样） ━━━

    @Override
    public void onTick(Minecraft client) {
        if (client == null || client.player == null || client.level == null || client.gameMode == null
                || client.getConnection() == null) return;

        // 服务器卡顿 / 拉回冷却时暂停催熟，避免顶风作案被踢
        if (settings.respectLag
            && (TacticalCoordinator.isServerLagging() || TacticalCoordinator.isRubberBandCooldown())) {
            return;
        }

        candidates.clear();

        // 节流
        if (++tickCounter <= settings.tickDelay) return;
        tickCounter = 0;

        // ① 先收集候选目标（每轮节流周期重新扫描一次）
        scanner.collect(settings, candidates, this::notify);

        // ② 范围模式：把新目标补入发送队列（按距离排序），去重已在队列中的
        if (settings.triggerMode == TriggerMode.范围自动扫描 && !candidates.isEmpty()) {
            candidates.sort(Comparator.comparingDouble(
                pos -> mc.player.getEyePosition().distanceToSqr(Vec3.atCenterOf(pos))
            ));
            for (BlockPos pos : candidates) {
                if (!sendQueue.contains(pos)) sendQueue.addLast(pos);
            }
        }

        // ③ 无目标且队列也空时提示
        boolean hasWork = !candidates.isEmpty() || !sendQueue.isEmpty();
        if (!hasWork) {
            if (settings.noTargetHint && hadTargetsLastTick) {
                notify(settings.triggerMode == TriggerMode.准星精准指向
                    ? BonemealTexts.HINT_NO_TARGET_CROSSHAIR
                    : BonemealTexts.HINT_NO_TARGET_NEARBY);
            }
            hadTargetsLastTick = false;
            return;
        }
        hadTargetsLastTick = true;

        // ④ 有目标后才找/换骨粉槽位，避免无目标时锁快捷栏
        InteractionHand hand = executor.findBoneMealHand(settings);
        if (hand == null) {
            if (!pausedNoBoneMeal) {
                pausedNoBoneMeal = true;
                notify(BonemealTexts.NO_BONE_MEAL);
            }
            return;
        }
        if (pausedNoBoneMeal) {
            pausedNoBoneMeal = false;
            notify(BonemealTexts.BONE_MEAL_RESTORED);
        }

        // ⑤ 直接按「每轮最大催熟数」限制本轮（0 = 不限制）
        int cap = settings.maxPerTick;

        if (settings.triggerMode == TriggerMode.范围自动扫描) {
            int sent = 0;
            while (!sendQueue.isEmpty() && (cap == 0 || sent < cap)) {
                BlockPos target = sendQueue.pollFirst();
                executor.fertilize(target, hand, settings);
                sent++;
            }
        } else {
            // 准星模式：只催熟一个
            if (!candidates.isEmpty()) {
                executor.fertilize(candidates.get(0), hand, settings);
            }
        }
    }

    // ━━━ 启动播报（旧 reportStartupInfo:347-376 整块逐字） ━━━

    /**
     * 启动播报：合并为一条多行消息块，只带一次模块前缀。
     *
     * <p>只报会影响本次结果的关键项（触发模式、目标方块、作用半径、节流、视角同步），
     * 正文统一「标签　§8▸ 值」，与自动农场 / 自动挖矿的启动报告风格一致。</p>
     */
    private void reportStartupInfo() {
        StringBuilder report = new StringBuilder();
        report.append(BonemealTexts.REPORT_TITLE);

        // 触发模式
        report.append("\n§7").append(BonemealTexts.REPORT_TRIGGER).append("　§8▸ ")
            .append(highlightFunction(settings.triggerMode.label()))
            .append("§r");

        // 目标方块总数
        report.append("\n§7").append(BonemealTexts.REPORT_TARGETS).append("　§8▸ ")
            .append(highlightNumber(settings.totalTargetCount() + " 种")).append("§r");

        // 作用半径（仅范围模式）
        if (settings.triggerMode == TriggerMode.范围自动扫描) {
            report.append("\n§7").append(BonemealTexts.REPORT_RANGE).append("　§8▸ ")
                .append(highlightNumber(settings.range + " 格")).append("§r");
        }

        // 动作节流
        report.append("\n§7").append(BonemealTexts.REPORT_TICK_DELAY).append("　§8▸ ")
            .append(highlightNumber(settings.tickDelay + " Tick")).append("§r");

        // 视角静默同步（开=绿、关=红）
        report.append("\n§7").append(BonemealTexts.REPORT_ROTATE_SILENT).append("　§8▸ ")
            .append(settings.rotateSilent ? BonemealTexts.REPORT_ON : BonemealTexts.REPORT_OFF)
            .append("§r");

        notify(report.toString());
    }

    // ── 播报（旧基类的颜色码包装逐字保留） ──

    /** 普通消息（旧基类：前缀 + {@code §f} + 正文） */
    private void notify(String message) {
        ClientChat.send(MESSAGE_MODULE, "§f" + message);
    }

    /** 错误消息（旧基类 {@code notifyError}：{@code §6§l} + 正文） */
    private void notifyError(String message) {
        ClientChat.send(MESSAGE_MODULE, "§6§l" + message);
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

    /** 配置页 = 薄壳模块页 {@link BonemealPage} + 整屏控制台（概览 + 旧 4 个设置组） */
    @Override
    public ModulePage page() {
        return new BonemealPage(this);
    }
}
