package com.yiyiaddon.feature.tactical;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.feature.tactical.config.FlightBypassSettings;
import com.yiyiaddon.feature.tactical.core.FlightPolicy;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.feature.tactical.ui.FlightBypassPage;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.container.InventoryAccess;
import com.yiyiaddon.ui.page.ModulePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.Set;

/**
 * 飞行绕过模块：五种基于官方机制的移动方式，统一由 {@link TacticalCoordinator} 裁决。
 *
 * <p><b>旧项目对应物</b>：{@code tactical/FlightBypass.java}（576 行）。五个模式的执行逻辑、
 * 常量、播报文案、内部状态字段逐条对照搬运。</p>
 *
 * <p><b>职责边界（与旧实现一致）</b>：只负责「移动执行」（速度注入 / 真实起跳 / 鞘翅起滑 /
 * 烟花推进 / 垫脚放置），不负责决策（每刻向协调器请求 {@link FlightPolicy.FlightDecision}），
 * 不处理拉回包（冷却统计归协调器），不监听反作弊检测事件换模式。</p>
 *
 * <p><b>框架适配（本项目没有、旧框架白送的能力）</b>：</p>
 * <ul>
 *     <li>播报：旧 {@code notify/warning} → {@link ClientChat#send(String, String)}；</li>
 *     <li>单人世界不可开启：旧 {@code chatFeedback=false; toggle(); chatFeedback=true;}
 *         只写在 {@code onActivate} 里 → 改为 {@link Module#environmentRefusal()} 交给框架，
 *         启用与进世界两个时机都拦（旧写法对默认开启的模块拦不住）；</li>
 *     <li>会话重置：旧订阅 {@code SessionResetEvent} → 本项目订阅 {@link ClientEventType#JOIN_SERVER}
 *         与 {@link ClientEventType#DISCONNECT}（协调器同样订阅这两个，各清各的状态，顺序无关）；</li>
 *     <li>发包：旧直接构造包 → {@link ClientPacketSender} 的语义直发（走核心闸门的绕行通道）；</li>
 *     <li>背包：旧 {@code InvUtils} → {@link InventoryAccess}。</li>
 * </ul>
 *
 * @author yiyijia
 */
public final class FlightBypassModule extends Module {

    /** 模块 ID（状态文件键 / 快捷键键名后缀 / 渲染层所有者） */
    public static final String MODULE_ID = "flightbypass";

    /** 模块中文显示名（播报前缀与控制台标题共用） */
    public static final String MESSAGE_MODULE = "飞行绕过";

    /** 缺鞘翅 / 缺烟花提示的节流窗口（毫秒，逐字照旧 {@code 5000}） */
    private static final long HINT_INTERVAL_MS = 5000L;

    /** 连跳水平推力系数（逐字照旧 {@code 0.28}） */
    private static final double JUMP_PUSH = 0.28;

    /** 烟花使用周期（tick，逐字照旧 {@code % 10}） */
    private static final int ROCKET_PERIOD_TICKS = 10;

    /** 垫脚放置周期（tick，逐字照旧 {@code % 5}） */
    private static final int SCAFFOLD_PERIOD_TICKS = 5;

    /** 每 N 次垫脚留一块不拆（逐字照旧 {@code % 5}） */
    private static final int SCAFFOLD_KEEP_EVERY = 5;

    /** 拆除时刻随机抖动上限（毫秒，逐字照旧 {@code nextInt(40)}） */
    private static final int SCAFFOLD_DESTROY_JITTER_MS = 40;

    /** 背包扫描上限（旧 {@code InvUtils.find(..., 0, 35)}：快捷栏 + 主背包） */
    private static final int BACKPACK_SCAN_LIMIT = 36;

    /** 滑翔速度档位换算除数（逐字照旧 {@code 100.0}：1 档 = 0.01 格/tick） */
    private static final double GLIDE_SPEED_DIVISOR = 100.0;

    /** 潜行下降倍率（逐字照旧 {@code -speed * 3.0}） */
    private static final double GLIDE_DESCEND_FACTOR = 3.0;

    /** 无输入时的微降倍率（逐字照旧 {@code -speed * 0.5}） */
    private static final double GLIDE_IDLE_FACTOR = 0.5;

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体 */
    private final FlightBypassSettings settings = new FlightBypassSettings();

    // ━━━ 内部执行状态（全部为私有态，会话重置时统一清零） ━━━

    /** tick 计数：驱动跳跃间隔与烟花周期 */
    private int tickCounter;

    /** 原版连跳最近一次起跳的 tick：落地判定抖动时保证两次起跳之间的最小间隔 */
    private int lastJumpTick;

    /** 垫脚已放置计数：每 5 次留一块不拆，模拟手动失误 */
    private int scaffoldCounter;

    /** 垫脚延迟拆除登记（主线程 tick 驱动，预测处理器非线程安全） */
    private BlockPos pendingDestroyPos;
    private long pendingDestroyAt;

    /** 空中开伞请求去重：一次离地只发一条 START_FALL_FLYING */
    private boolean glideDeployRequested;

    /** 发包飞行是否由本模块置位了飞行态（关闭/重置时需还原，避免关模块后仍悬空） */
    private boolean packetFlyEnabled;

    /** 缺鞘翅提示节流（5 秒一次，防每 tick 刷屏） */
    private long lastGlideHintAt;

    /** 缺烟花提示节流（5 秒一次） */
    private long lastRocketHintAt;

    private final Random random = new Random();

    // ━━━ 决策播报去重锁（状态变化才播，防每 tick 刷屏） ━━━

    private FlightPolicy.FlightReason lastNotifiedReason;
    private FlightPolicy.FlightMode lastNotifiedMode;

    /** 经历过拒绝/降级后恢复放行，补一条恢复播报 */
    private boolean wasBlocked;

    public FlightBypassModule() {
        super(MODULE_ID, MESSAGE_MODULE, "combat",
            "五种飞行模式，协调器统一决策");
    }

    /**
     * 图标字形（Material Symbols 的 {@code flight}）。
     *
     * <p>已按开发习惯第 140 条验真：解析 {@code MaterialSymbolsRounded.ttf} 的 cmap 与 post 字形名，
     * 该码点存在且与项目内既有图标无占用冲突。用户 2026-09-18 反馈「加上图标」后补。</p>
     */
    private static final String ICON = "\uE539";

    @Override
    public String icon() {
        return ICON;
    }

    /** 分类内排序：战斗分类第二位（杀戮光环 → 飞行绕过 → 发包防踢 → 发包秒破） */
    @Override
    public int order() {
        return 20;
    }

    /** 设置载体（控制台页面读写） */
    public FlightBypassSettings settings() {
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

    // ── 生命周期 ──

    /**
     * 单人世界闸门：旧 {@code onActivate} 的第一段（原因文案照旧）。
     *
     * <p>旧实现只拦「在单人世界里点开启」。本模块虽非默认开启，但玩家在主菜单（或进服前的
     * 任意时刻）开启后进单人世界同样绕得过去，因此与其它战术模块统一交给框架的
     * {@link Module#environmentRefusal()}，两个时机都拦。</p>
     */
    @Override
    public String environmentRefusal() {
        return GameProbe.isSingleplayer() ? "§c单人世界无需飞行绕过" : null;
    }

    /** 旧 {@code onActivate}：复位本地状态（单人世界闸门已交给框架） */
    @Override
    protected void onEnable() {
        resetLocalState();
    }

    /** 旧 {@code onDeactivate}：先拆欠的方块，再还原本模块置位的飞行态 */
    @Override
    protected void onDisable() {
        if (pendingDestroyPos != null) {
            destroyScaffoldBlock(pendingDestroyPos);
            pendingDestroyPos = null;
        }
        releasePacketFly();
    }

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK, ClientEventType.JOIN_SERVER, ClientEventType.DISCONNECT);
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null) return;
        // 每刻流程统一走 onTick（ModuleManager 调度）
        if (event.type() == ClientEventType.JOIN_SERVER || event.type() == ClientEventType.DISCONNECT) {
            resetLocalState();
        }
    }

    /** 清本模块私有执行状态；全局状态由协调器自行清零，本模块不碰任何共享字段 */
    private void resetLocalState() {
        tickCounter = 0;
        lastJumpTick = 0;
        scaffoldCounter = 0;
        pendingDestroyPos = null;
        pendingDestroyAt = 0L;
        glideDeployRequested = false;
        packetFlyEnabled = false;
        lastGlideHintAt = 0L;
        lastRocketHintAt = 0L;
        lastNotifiedReason = null;
        lastNotifiedMode = null;
        wasBlocked = false;
    }

    /**
     * 还原发包飞行置位的飞行态。
     *
     * <p>仅当本模块确实置位过（{@code packetFlyEnabled}）才动作；创造/旁观下原版飞行本就合法，
     * 不回收以免打断原版操作。</p>
     */
    private void releasePacketFly() {
        if (!packetFlyEnabled || mc.player == null) return;
        packetFlyEnabled = false;

        Abilities abilities = mc.player.getAbilities();
        if (abilities.flying && !abilities.instabuild && !mc.player.isSpectator()) {
            ClientPacketSender.sendAbilities(false);
        }
    }

    // ━━━ 主循环：请求决策 → 播报状态变化 → 按决策执行 ━━━
    //
    // 旧实现挂在 TickEvent.Post 而非 Pre：原版连跳的速度注入在 Pre 会被当 tick 的
    // move/travel 摩擦系数（0.6~0.91）衰减，落地帧的起跳推力几乎被吃光，表现为
    // 「跳一下顿一下」；Post 阶段 travel 已结束，注入的速度完整保留到下一帧。
    // 本项目模块 tick 由核心在客户端刻末尾统一调度（等价 Post 相位），列为实机验证项。

    @Override
    public void onTick(Minecraft client) {
        if (client == null || client.player == null || client.level == null) return;

        tickCounter++;

        // 垫脚拆除到点就执行：必须在决策门槛之前处理，否则方块残留
        if (pendingDestroyPos != null && System.currentTimeMillis() >= pendingDestroyAt) {
            destroyScaffoldBlock(pendingDestroyPos);
            pendingDestroyPos = null;
        }

        // 唯一决策入口：协调器按「冷却 → 降级档权衡 → 准入」顺序产出本刻决策
        FlightPolicy.FlightDecision decision = TacticalCoordinator.evaluateFlight(
            settings.mode, settings.adaptiveSlowdown, settings.rubberBandThreshold);

        // 状态播报（带去重锁，只在状态变化时输出）
        broadcastDecision(decision);

        // 冷却或模式降级时必须撤销本模块先前置位的飞行态；否则 abilities.flying 仍为 true，
        // 表面暂停、实际仍由原版输入继续飞。
        if (!decision.granted()) {
            releasePacketFly();
            return;
        }
        if (decision.mode() != FlightPolicy.FlightMode.PACKET_FLY) releasePacketFly();

        switch (decision.mode()) {
            case PACKET_FLY -> handlePacketFly();
            case VANILLA_MIMIC -> handleVanillaMimic();
            case SAFE_GLIDE -> handleSafeGlide();
            case FIREWORK_BOOST -> handleFireworkBoost();
            case SEQUENCE_SCAFFOLD -> handleSequenceScaffold();
        }
    }

    /**
     * 决策状态播报（去重锁：同原因同模式只播一次）。
     *
     * <p>冷却暂停静默不播，拒绝与降级播 ⚠，恢复放行补一条 ✓ 恢复播报。</p>
     */
    private void broadcastDecision(FlightPolicy.FlightDecision decision) {
        if (decision.reason() == lastNotifiedReason && decision.mode() == lastNotifiedMode) return;
        lastNotifiedReason = decision.reason();
        lastNotifiedMode = decision.mode();

        switch (decision.reason()) {
            case DEGRADED -> {
                wasBlocked = true;
                notify("§e⚠ 连续拉回触发降级 §8▸ " + highlightFunction(decision.mode().displayName));
            }
            case NO_FLY_ABILITY -> {
                wasBlocked = true;
                String suffix = decision.mode() == FlightPolicy.FlightMode.VANILLA_MIMIC
                    ? "；当前无鞘翅，只能执行非飞行连跳"
                    : "，已自动切换 " + highlightFunction(decision.mode().displayName);
                notify("§e⚠ 服务器未授予飞行能力 §8▸ " + highlightFunction("发包飞行") + " 不可用" + suffix);
            }
            case HIGH_RISK_AC -> {
                wasBlocked = true;
                notify("§e⚠ 命中高风险反作弊 §8▸ " + highlightFunction("发包飞行") + " 已自动降级为 "
                    + highlightFunction(decision.mode().displayName));
            }
            case GRANTED -> {
                if (wasBlocked) {
                    wasBlocked = false;
                    notify("§a✓ 已恢复执行 §8▸ " + highlightFunction(decision.mode().displayName));
                }
            }
            case COOLDOWN -> {
                // 拉回冷却暂停：静默，防刷屏
            }
        }
    }

    // ━━━ 模式 1：发包飞行 —— 零注入，纯原版飞行（服务端速度权威） ━━━

    /**
     * 只负责「起飞」这一步：协调器已校验服务端授予了飞行能力（mayfly/flying），
     * 这里把客户端置为飞行态并回发 abilities 包。26.1.2 服务端只在自身 mayfly 为真时
     * 接受 flying 标志，因此这是合法起飞而非发包伪造；水平速度由服务端权威计算，
     * 客户端注入任何速度都只会与服务端轨迹漂移。
     */
    private void handlePacketFly() {
        Abilities abilities = mc.player.getAbilities();
        if (!abilities.flying) {
            packetFlyEnabled = true;
            ClientPacketSender.sendAbilities(true);
        }
    }

    // ━━━ 模式 2：原版连跳（非飞行）—— 落地即真实起跳，地面接触重置浮空计时 ━━━

    /**
     * 落地即跳的连续兔子跳。
     *
     * <p>落地帧立即起跳；推力方向取身体朝向 yaw 而非视线向量（视线含俯仰，低头时水平推力
     * 会被吃掉，造成忽快忽慢的乱跳感）。每跳弧线约 12~14 tick，远低于服务端浮空上限。</p>
     */
    private void handleVanillaMimic() {
        // 落地帧才起跳：空中 onGround 恒假，每一跳都由真实地面接触发起
        if (!mc.player.onGround()) return;

        // 最小起跳间隔（间隔档位-1 tick）：防止落地判定抖动导致的同帧连跳
        if (tickCounter - lastJumpTick < Math.max(1, settings.vanillaJumpInterval - 1)) return;

        // 起跳前置为疾跑：凑足原版疾跑跳的冲刺推力，前进速度更接近连续飞行
        if (!mc.player.isSprinting()) {
            mc.player.setSprinting(true);
        }

        mc.player.jumpFromGround();

        // 水平推进沿身体朝向（yaw），与镜头俯仰无关
        float yawRad = mc.player.getYRot() * Mth.DEG_TO_RAD;
        Vec3 motion = mc.player.getDeltaMovement();
        mc.player.setDeltaMovement(
            motion.x - Mth.sin(yawRad) * JUMP_PUSH,
            motion.y,
            motion.z + Mth.cos(yawRad) * JUMP_PUSH
        );
        lastJumpTick = tickCounter;
    }

    // ━━━ 模式 3：安全滑翔 —— 自动装备鞘翅 + 官方起伞命令 ━━━

    private void handleSafeGlide() {
        if (!prepareGlide()) return;

        // 档位换算：1 档 = 0.01 格/tick
        double speed = settings.glideSpeed / GLIDE_SPEED_DIVISOR;
        double vy;
        if (mc.options.keyJump.isDown()) {
            vy = speed;                                        // 上升：缓爬升
        } else if (mc.options.keyShift.isDown()) {
            vy = -speed * GLIDE_DESCEND_FACTOR;                // 下降：快速脱离危险高度
        } else {
            vy = -speed * GLIDE_IDLE_FACTOR;                   // 无输入：微降，外观贴近自然滑翔
        }

        Vec3 motion = mc.player.getDeltaMovement();
        mc.player.setDeltaMovement(motion.x, vy, motion.z);
    }

    // ━━━ 模式 4：烟花火箭 —— 滑翔中周期性使用烟花推进（服务端完全合法） ━━━

    private void handleFireworkBoost() {
        // 起滑流程与安全滑翔共享（补鞘翅 → 起跳 → 开伞）
        if (!prepareGlide()) return;

        if (tickCounter % ROCKET_PERIOD_TICKS != 0) return;

        // 判定哪只手真的握着烟花；若烟花在快捷栏则临时切换并在发包后恢复
        InteractionHand hand;
        boolean swapped = false;
        int previousSlot = InventoryAccess.NOT_FOUND;
        if (mc.player.getOffhandItem().getItem() == Items.FIREWORK_ROCKET) {
            hand = InteractionHand.OFF_HAND;
        } else if (mc.player.getMainHandItem().getItem() == Items.FIREWORK_ROCKET) {
            hand = InteractionHand.MAIN_HAND;
        } else {
            int rocketSlot = InventoryAccess.findInHotbar(stack -> stack.getItem() == Items.FIREWORK_ROCKET);
            if (rocketSlot < 0 || (previousSlot = InventoryAccess.selectedHotbar()) < 0
                || !InventoryAccess.selectHotbar(rocketSlot)) {
                hintNoRocket();
                return;
            }
            hand = InteractionHand.MAIN_HAND;
            swapped = true;
        }

        // 鞘翅还有耐久才允许推进，否则服务端拒绝
        ItemStack elytra = mc.player.getItemBySlot(EquipmentSlot.CHEST);
        if (elytra.isEmpty() || elytra.getDamageValue() >= elytra.getMaxDamage()) return;

        int sequence = ClientPacketSender.nextPredictionSequence();
        if (sequence < 0) return;
        ClientPacketSender.sendUseItem(hand, sequence, mc.player.getYRot(), mc.player.getXRot());
        if (swapped) InventoryAccess.selectHotbar(previousSlot);
    }

    /**
     * 共享起滑流程：补鞘翅 → 地面真实起跳 → 空中官方起伞。
     *
     * <p>起伞走 26.1.2 官方同款命令包（{@code START_FALL_FLYING}），服务端校验鞘翅与条件后
     * 置 {@code fallFlying}，豁免浮空判定。</p>
     *
     * @return 是否已进入滑翔状态（fallFlying）
     */
    private boolean prepareGlide() {
        ItemStack chest = mc.player.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.getItem() != Items.ELYTRA || chest.nextDamageWillBreak()) {
            ensureElytraEquipped();
            return false;
        }

        if (mc.player.onGround()) {
            // 地面：真实起跳离地，下一 tick 空中开伞
            mc.player.jumpFromGround();
            glideDeployRequested = false;
            return false;
        }

        if (!mc.player.isFallFlying()) {
            // 空中：发官方起伞命令（一次离地只发一条）
            if (!glideDeployRequested) {
                ClientPacketSender.sendStartFallFlying();
                glideDeployRequested = true;
            }
            return false;
        }

        glideDeployRequested = false;
        return true;
    }

    /** 背包没穿鞘翅时自动补装到胸甲槽；包里没有则节流提示 */
    private void ensureElytraEquipped() {
        int slot = InventoryAccess.find(
            stack -> stack.getItem() == Items.ELYTRA && !stack.nextDamageWillBreak(), BACKPACK_SCAN_LIMIT);
        if (slot == InventoryAccess.NOT_FOUND) {
            long now = System.currentTimeMillis();
            if (now - lastGlideHintAt >= HINT_INTERVAL_MS) {
                lastGlideHintAt = now;
                notify("§e⚠ 背包没有鞘翅 §8▸ 安全滑翔/烟花火箭需要 " + highlightText("鞘翅"));
            }
            return;
        }
        InventoryAccess.moveToChest(slot);
    }

    private void hintNoRocket() {
        long now = System.currentTimeMillis();
        if (now - lastRocketHintAt >= HINT_INTERVAL_MS) {
            lastRocketHintAt = now;
            notify("§e⚠ 快捷栏没有烟花 §8▸ 烟花火箭模式无法推进");
        }
    }

    // ━━━ 模式 5：序列垫脚 —— 预测放置真实方块提供物理支撑 ━━━

    private void handleSequenceScaffold() {
        if (tickCounter % SCAFFOLD_PERIOD_TICKS != 0) return;

        ItemStack main = mc.player.getMainHandItem();
        if (!(main.getItem() instanceof BlockItem)) return;

        ClientLevel level = mc.level;
        BlockPos belowPos = mc.player.blockPosition().below();
        BlockState belowState = level.getBlockState(belowPos);
        if (!belowState.isAir() || !belowState.getFluidState().isEmpty()
            || !belowState.getCollisionShape(level, belowPos).isEmpty()) {
            return;
        }

        BlockPos supportPos = belowPos.below();
        BlockState supportState = level.getBlockState(supportPos);
        if (supportState.isAir() || supportState.getCollisionShape(level, supportPos).isEmpty()) return;

        BlockHitResult hit = new BlockHitResult(
            new Vec3(supportPos.getX() + 0.5, supportPos.getY() + 1.0, supportPos.getZ() + 0.5),
            Direction.UP, supportPos, false);
        if (!sendPredictedPlace(belowPos, hit)) return;
        scaffoldCounter++;

        // 每 5 次留一块不拆（模拟手动失误），拆过的那块登记延迟拆除
        if (scaffoldCounter % SCAFFOLD_KEEP_EVERY == 0) return;
        pendingDestroyPos = belowPos;
        pendingDestroyAt = System.currentTimeMillis() + settings.scaffoldDelay + random.nextInt(SCAFFOLD_DESTROY_JITTER_MS);
    }

    /** 预测放置：取新序列号并登记目标坐标的已知服务端状态，随后挥手臂 */
    private boolean sendPredictedPlace(BlockPos target, BlockHitResult hit) {
        boolean placed = ClientPacketSender.sendUseItemOn(InteractionHand.MAIN_HAND, hit, target);
        if (placed && mc.player != null) {
            mc.player.swing(InteractionHand.MAIN_HAND);
        }
        return placed;
    }

    /** 拆除垫脚方块：START 与 STOP 各自独立取号（旧实现同样两次取号） */
    private void destroyScaffoldBlock(BlockPos pos) {
        if (pos == null) return;
        ClientLevel level = mc.level;
        if (level == null) return;
        BlockState original = level.getBlockState(pos);
        if (original.isAir()) return;

        ClientPacketSender.sendBreakStart(pos, Direction.UP, original);
        ClientPacketSender.sendBreakStop(pos, Direction.UP);
        if (mc.player != null) {
            mc.player.swing(InteractionHand.MAIN_HAND);
        }
    }

    // ── 播报与高亮（旧基类同款包装：绿字=文本 / 蓝字=功能名） ──

    private static String highlightText(String text) {
        return "§a§l" + text + "§r§f§l";
    }

    private static String highlightFunction(String text) {
        return "§b§l" + text + "§r§f§l";
    }

    private void notify(String message) {
        ClientChat.send(MESSAGE_MODULE, message);
    }

    // ── 界面 ──

    /** 配置页 = 薄壳模块页 {@link FlightBypassPage} + 整屏控制台（3 分页：概览 / 模式选择 / 参数调整） */
    @Override
    public ModulePage page() {
        return new FlightBypassPage(this);
    }
}
