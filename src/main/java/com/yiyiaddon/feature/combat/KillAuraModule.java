package com.yiyiaddon.feature.combat;

import baritone.api.BaritoneAPI;
import baritone.api.process.IBaritoneProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import com.google.gson.JsonObject;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.feature.combat.config.KillAuraSettings;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.AttackItems;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.RotationMode;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.ShieldMode;
import com.yiyiaddon.feature.combat.target.AimAngles;
import com.yiyiaddon.feature.combat.target.SortPriority;
import com.yiyiaddon.feature.combat.target.TargetScanner;
import com.yiyiaddon.feature.combat.ui.KillAuraPage;
import com.yiyiaddon.ui.page.ModulePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.GameType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 杀戮光环：攻击周围指定实体的独立战斗模块。
 *
 * <p><b>蓝本</b>：{@code 01-开发参考库/Meteor原始源码/…/systems/modules/combat/KillAura.java}
 * （527 行）逐条对齐。主流程与蓝本 {@code onTick} 同一结构：</p>
 * <pre>
 * 停机闸（:289-308）→ 选目标（:309-327）→ 自动切武器（:331-347）→ 手持物可用性（:349-352）
 * → 旋转与暂停 Baritone（:354-360）→ 冷却判定（:362 → delayCheck :453-468）→ 出手（:470-478）
 * </pre>
 *
 * <p>每个环节的注释都标了蓝本行号；设置项在 {@link KillAuraSettings}，目标过滤链在
 * {@link TargetScanner}，角度计算在 {@link AimAngles}，排序在 {@link SortPriority} —— 框架层
 * （{@code TargetUtils} / {@code PlayerUtils} / {@code Rotations} / {@code InvUtils} / {@code TickRate}）
 * 全部按本项目做法重写，无任何第三方依赖。</p>
 *
 * <p><b>与蓝本的差异（逐条，均不可自行改回）</b>：</p>
 * <ol>
 *     <li><b>好友过滤删除</b>：蓝本 {@code :427} 的 {@code Friends.get().shouldAttack(player)} 整条去掉，
 *         本项目不建好友体系（用户裁定，依据 {@code 39-阶段10-附录A} 12.4）；</li>
 *     <li><b>CrystalAura 互斥闸门留空</b>：蓝本 {@code :305-308} 依赖第三方 {@code CrystalAura} 模块，
 *         本项目无该模块 → 判据恒假（{@link #CRYSTAL_AURA_PLACING}），{@code pauseOnCA} 设置项保留以对齐设置面；</li>
 *     <li><b>假人免伤留空</b>：蓝本 {@code :429} 的 {@code FakePlayerEntity.noHit} 分支在本项目无对应类
 *         （无假人体系）→ 不排除任何目标；</li>
 *     <li><b>假人候选留空</b>：蓝本 {@code TargetUtils.java:48-50} 会把假人名单并入候选，本项目无假人体系；</li>
 *     <li><b>切槽只在真正换槽时发包</b>：蓝本 {@code InvUtils.swap(slot, false)}（{@code :346}）每刻调用，
 *         本项目只在目标槽与当前槽不同时切（{@link #swapToSlot(int)}）——否则每刻都会发携带物同步包，
 *         使 {@code switchDelay} 永远无法倒数完毕；</li>
 *     <li><b>旋转为分步转头</b>：蓝本 {@code Rotations.rotate} 是「瞬移 + 补发旋转包」，本项目按既有做法
 *         用 {@code LocalPlayer#setYRot/setXRot} 每刻最多转 {@link #VIEW_TURN_STEP} 度
 *         （与自动箱子 {@code syncViewToTarget} 同一口径，不动网络层）；</li>
 *     <li><b>暂停 Baritone 的落点不同</b>：蓝本 {@code PathManagers.pause()/resume()} 内部是自管的
 *         {@code pathingPaused} 标志（{@code BaritonePathManager.java:58-65}）；本项目按同一机制自己注册一个
 *         Baritone 进程（{@code IBaritoneProcess} + {@code PathingCommandType.REQUEST_PAUSE}，
 *         见 {@code BaritonePathManager.java:193-223}），只用 Baritone 公开 API；</li>
 *     <li><b>TPS 观测入口</b>：蓝本 {@code TickRate} 直接订阅收包事件；本项目数据包事件只带类名
 *         （{@code ClientEventType.PACKET_RECEIVE}），因此按类名匹配 {@link ClientboundSetTimePacket}，
 *         采样口径与蓝本一致（见 {@link TickRateTracker}）；</li>
 *     <li><b>对外面收窄</b>：蓝本暴露 {@code attacking} / {@code swapped} / {@code static previousSlot}
 *         三个公有字段与 {@code getInfoString()}，本项目无消费方，只保留 {@link #getTarget()}，
 *         其余为私有字段（{@code previousSlot} 由静态改为实例字段，模块单例下语义不变）。</li>
 * </ol>
 *
 * <p><b>配置页</b>：{@link #page()} 返回薄壳模块页 {@link KillAuraPage}（状态条 + 一个「打开控制台」入口），
 * 26 项设置由整屏控制台分页承载（概览 / 常规 / 目标 / 时机）；模块自身无播报（蓝本 KillAura 无播报）。</p>
 */
public final class KillAuraModule extends Module {

    /**
     * 图标字形（Material Symbols：warning，已用字体 cmap 实测存在，且此前未被本项目任何位置占用）。
     *
     * <p>用户 2026-09-16 要求先给一个可看的字形、实机观感不对再换（改这一行即可）。</p>
     */
    private static final String ICON = "\uE002";

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀，以及挖矿修补联动的查询键 */
    public static final String MODULE_ID = "killaura";

    /** 蓝本 {@code FILTER} 里的物品（{@code KillAura.java:264}）在 {@code acceptableWeapon} 中逐个比对（{@code :484-491}） */
    private static final String ITEM_DIAMOND_SWORD = "minecraft:diamond_sword";
    private static final String ITEM_DIAMOND_AXE = "minecraft:diamond_axe";
    private static final String ITEM_DIAMOND_PICKAXE = "minecraft:diamond_pickaxe";
    private static final String ITEM_DIAMOND_SHOVEL = "minecraft:diamond_shovel";
    private static final String ITEM_DIAMOND_HOE = "minecraft:diamond_hoe";
    private static final String ITEM_MACE = "minecraft:mace";
    private static final String ITEM_DIAMOND_SPEAR = "minecraft:diamond_spear";
    private static final String ITEM_TRIDENT = "minecraft:trident";

    /** 单刻最大转头角度（本项目既有口径，与 {@code AutoChestModule.VIEW_TURN_STEP} 一致） */
    private static final float VIEW_TURN_STEP = 15.0f;

    /**
     * CrystalAura 互斥闸门的判据（蓝本 {@code :305}）。
     *
     * <p>恒为 {@code false}：本项目没有 CrystalAura 模块，也没有它那套 {@code kaTimer}，
     * 因此该闸门在本项目里永远不触发（用户裁定留空，{@code pauseOnCA} 设置项保留以对齐设置面）。
     * 将来若真的移植 CrystalAura，把这里换成真实判据即可，主流程不必改。</p>
     */
    private static final boolean CRYSTAL_AURA_PLACING = false;

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体 */
    private final KillAuraSettings settings = new KillAuraSettings();

    /** 设置载体（只读暴露给控制台页；模块内部行为不变） */
    public KillAuraSettings settings() {
        return settings;
    }

    /** 目标筛选（蓝本 {@code entityCheck} + {@code TargetUtils.getList}） */
    private final TargetScanner scanner = new TargetScanner(settings);

    /** 服务端 TPS / 卡顿观测（蓝本 {@code TickRate}） */
    private final TickRateTracker tickRate = new TickRateTracker();

    /** 当前锁定目标（蓝本公有字段 {@code targets}，本项目收为私有） */
    private final List<Entity> targets = new ArrayList<>();

    /** 切槽后的等待刻数（蓝本 {@code switchTimer}，{@code :266}） */
    private int switchTimer;

    /** 自定义延迟模式下已等待的刻数（蓝本 {@code hitTimer}，{@code :266}） */
    private int hitTimer;

    /** 是否因出手而暂停了 Baritone（蓝本 {@code wasPathing}，{@code :267}） */
    private boolean wasPathing;

    /** 是否正在攻击（蓝本公有字段 {@code attacking}，{@code :268}） */
    private boolean attacking;

    /** 本次攻击期间是否已切过武器（蓝本公有字段 {@code swapped}，{@code :268}） */
    private boolean swapped;

    /** 切武器前的原槽位（蓝本 {@code static previousSlot}，{@code :269}） */
    private int previousSlot = -1;

    /** Baritone 冻结标志（蓝本 {@code BaritonePathManager.pathingPaused}，{@code :34}） */
    private boolean pathingPaused;

    /** 冻结进程是否注册成功（Baritone 未加载时会失败，之后按需重试） */
    private boolean pauseProcessRegistered;

    public KillAuraModule() {
        super(MODULE_ID, "杀戮光环", "combat", "攻击周围的指定实体。");
        registerPauseProcess();
    }

    @Override
    public int order() {
        return 20;
    }

    @Override
    public String icon() {
        return ICON;
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

    // ── 自检与页面 ──

    /** 蓝本无自检（{@code KillAura} 直接 {@code onActivate}），本项目同样不设门槛 */
    @Override
    public List<String> selfCheck() {
        return List.of();
    }

    /** 配置页 = 薄壳模块页 {@link KillAuraPage} + 整屏控制台；控制台分页承载 26 项设置 */
    @Override
    public ModulePage page() {
        return new KillAuraPage(this);
    }

    // ── 生命周期 ──

    /** 蓝本 {@code onActivate}，{@code :275-279}（另复位本模块的计时与目标缓存） */
    @Override
    protected void onEnable() {
        previousSlot = -1;
        swapped = false;
        attacking = false;
        wasPathing = false;
        pathingPaused = false;
        switchTimer = 0;
        hitTimer = 0;
        targets.clear();
        tickRate.reset();
    }

    /** 蓝本 {@code onDeactivate}，{@code :281-285}；由挖矿修补钩子在 tick 内同步调用，必须幂等且不抛异常 */
    @Override
    protected void onDisable() {
        targets.clear();
        stopAttacking();
        // 兜底解冻：停机路径之外若残留冻结标志，会让 Baritone 被永久停住
        pathingPaused = false;
    }

    // ── 事件 ──

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK, ClientEventType.PACKET_SEND, ClientEventType.PACKET_RECEIVE);
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null) return;
        switch (event.type()) {
            // 蓝本 onSendPacket，:365-370：切槽包发出后开始计 switchDelay
            case PACKET_SEND -> {
                if (ServerboundSetCarriedItemPacket.class.getName().equals(event.payload())) {
                    switchTimer = settings.switchDelay;
                }
            }
            // 蓝本 TickRate 的收包口（TickRate.java:33-42）：只认 SetTime 包
            case PACKET_RECEIVE -> {
                if (tickRate.isSetTimePacket(event.payload())) tickRate.onSetTimePacket();
            }
            // 每刻流程统一走 onTick（ModuleManager 调度），此处不重复执行
            default -> {
            }
        }
    }

    // ── 每刻主流程（蓝本 onTick，:287-363） ──

    @Override
    public void onTick(Minecraft client) {
        if (mc.player == null || mc.level == null || mc.gameMode == null) return;

        // 蓝本 :289-292 玩家死亡或旁观
        if (!mc.player.isAlive() || gameMode() == GameType.SPECTATOR) {
            stopAttacking();
            return;
        }
        // 蓝本 :293-296 正在破坏方块或正在使用物品
        if (settings.pauseOnUse && (mc.gameMode.isDestroying() || mc.player.isUsingItem())) {
            stopAttacking();
            return;
        }
        // 蓝本 :297-300 未按住左键
        if (settings.onlyOnClick && !mc.options.keyAttack.isDown()) {
            stopAttacking();
            return;
        }
        // 蓝本 :301-304 服务器卡顿
        if (tickRate.timeSinceLastTick() >= 1f && settings.pauseOnLag) {
            stopAttacking();
            return;
        }
        // 蓝本 :305-308 CrystalAura 互斥：本项目无该模块 → 判据恒假（留空，见 CRYSTAL_AURA_PLACING）
        if (settings.pauseOnCA && CRYSTAL_AURA_PLACING) {
            stopAttacking();
            return;
        }

        // 蓝本 :309-322 选目标
        if (settings.onlyOnLook) {
            Entity targeted = mc.crosshairPickEntity;
            if (targeted == null || !scanner.entityCheck(targeted)) {
                stopAttacking();
                return;
            }
            targets.clear();
            targets.add(targeted);
        } else {
            targets.clear();
            scanner.scan(targets, settings.priority, settings.maxTargets);
        }

        if (targets.isEmpty()) {
            stopAttacking();
            return;
        }

        Entity primary = targets.get(0);

        // 蓝本 :331-347 自动切武器（只搜快捷栏 0~8）
        if (settings.autoSwitch) {
            int weaponSlot = mc.player.getInventory().getSelectedSlot();
            if (settings.attackWhenHolding == AttackItems.WEAPONS) {
                weaponSlot = findInHotbar(this::acceptableWeapon);
            }
            if (shouldShieldBreak()) {
                int axeSlot = findInHotbar(stack -> stack.getItem() instanceof AxeItem);
                if (axeSlot >= 0) weaponSlot = axeSlot;
            }
            if (!swapped) {
                previousSlot = mc.player.getInventory().getSelectedSlot();
                swapped = true;
            }
            swapToSlot(weaponSlot);
        }

        // 蓝本 :349-352 手上不是可用武器就不打
        if (!acceptableWeapon(mc.player.getMainHandItem())) {
            stopAttacking();
            return;
        }

        // 蓝本 :354-360
        attacking = true;
        if (settings.rotation == RotationMode.ALWAYS) turnTowards(primary);
        if (settings.pauseBaritone && isPathing() && !wasPathing) {
            pauseBaritone();
            wasPathing = true;
        }

        // 蓝本 :362 冷却到了就对全部目标出手
        if (delayCheck()) {
            for (Entity target : targets) attack(target);
        }
    }

    // ── 停机（蓝本 stopAttacking，:372-384） ──

    private void stopAttacking() {
        if (!attacking) return;

        attacking = false;
        if (wasPathing) {
            resumeBaritone();
            wasPathing = false;
        }
        if (settings.swapBack && swapped) {
            swapToSlot(previousSlot);
            swapped = false;
        }
    }

    // ── 盾牌与武器判定（蓝本 :386-396 / :480-492） ──

    /** 蓝本 {@code shouldShieldBreak}，{@code :386-396} */
    private boolean shouldShieldBreak() {
        for (Entity target : targets) {
            if (target instanceof Player player) {
                if (player.isBlocking() && settings.shieldMode == ShieldMode.BREAK) {
                    return true;
                }
            }
        }
        return false;
    }

    /** 蓝本 {@code acceptableWeapon}，{@code :480-492}：按物品标签判定，不是按具体物品 */
    private boolean acceptableWeapon(ItemStack stack) {
        if (shouldShieldBreak()) return stack.getItem() instanceof AxeItem;
        if (settings.attackWhenHolding == AttackItems.ALL) return true;

        if (settings.includesWeapon(ITEM_DIAMOND_SWORD) && stack.is(ItemTags.SWORDS)) return true;
        if (settings.includesWeapon(ITEM_DIAMOND_AXE) && stack.is(ItemTags.AXES)) return true;
        if (settings.includesWeapon(ITEM_DIAMOND_PICKAXE) && stack.is(ItemTags.PICKAXES)) return true;
        if (settings.includesWeapon(ITEM_DIAMOND_SHOVEL) && stack.is(ItemTags.SHOVELS)) return true;
        if (settings.includesWeapon(ITEM_DIAMOND_HOE) && stack.is(ItemTags.HOES)) return true;
        if (settings.includesWeapon(ITEM_MACE) && stack.getItem() instanceof MaceItem) return true;
        if (settings.includesWeapon(ITEM_DIAMOND_SPEAR) && stack.is(ItemTags.SPEARS)) return true;
        return settings.includesWeapon(ITEM_TRIDENT) && stack.getItem() instanceof TridentItem;
    }

    // ── 冷却判定（蓝本 delayCheck，:453-468） ──

    private boolean delayCheck() {
        if (switchTimer > 0) {
            switchTimer--;
            return false;
        }

        float delay = (settings.customDelay) ? settings.hitDelay : 0.5f;
        if (settings.tpsSync) delay /= (tickRate.tickRate() / 20);

        if (settings.customDelay) {
            if (hitTimer < delay) {
                hitTimer++;
                return false;
            }
            return true;
        }
        return mc.player.getAttackStrengthScale(delay) >= 1;
    }

    // ── 出手（蓝本 attack，:470-478） ──

    /**
     * 出手一次：命中时转头 → 原版攻击 → 挥手 → 冷却清零。
     *
     * <p>{@code mc.gameMode.attack} + {@code mc.player.swing} 就是原版左键攻击的同一条路，
     * 不新增网络层、不自己拼包（本项目 {@code platform/network/BlockPacketSender} 也只用这两个原语）。</p>
     */
    private void attack(Entity target) {
        if (settings.rotation == RotationMode.ON_HIT) turnTowards(target);

        mc.gameMode.attack(mc.player, target);
        mc.player.swing(InteractionHand.MAIN_HAND);

        hitTimer = 0;
    }

    /** 当前锁定的首个目标（蓝本 {@code getTarget()}，{@code :494-497}） */
    public Entity getTarget() {
        return targets.isEmpty() ? null : targets.get(0);
    }

    // ── 视角（本项目做法，蓝本 Rotations 对应物） ──

    /**
     * 朝目标分步转头。
     *
     * <p>蓝本用 {@code Rotations.rotate(yaw, pitch)} 瞬移视角并补发旋转包；本项目不改网络层，
     * 只改客户端朝向、由原版在下一帧自己发包（与 {@code AutoChestModule.syncViewToTarget}、
     * {@code MiningStateMachine.faceBlock} 同一口径）。角度由 {@link AimAngles} 按蓝本公式算出。</p>
     */
    private void turnTowards(Entity target) {
        LocalPlayer player = mc.player;
        if (player == null || target == null) return;
        player.setYRot(turnToward(player.getYRot(), (float) AimAngles.yaw(target), VIEW_TURN_STEP));
        player.setXRot(turnToward(player.getXRot(), (float) AimAngles.pitch(target), VIEW_TURN_STEP));
    }

    /** 朝目标角度靠拢，单刻最多转 step 度；{@code wrapDegrees} 处理 ±180° 环绕 */
    private static float turnToward(float current, float target, float step) {
        float delta = Mth.wrapDegrees(target - current);
        return current + Mth.clamp(delta, -step, step);
    }

    // ── 物品栏（本项目做法，蓝本 InvUtils 对应物） ──

    /**
     * 在快捷栏 0~8 里找第一个满足条件的槽位。
     *
     * <p>对应蓝本 {@code InvUtils.find(predicate, 0, 8)}（{@code InvUtils.java:111-126}）：
     * 只搜快捷栏，找不到返回 {@code -1}（蓝本返回 {@code FindItemResult(-1, 0)}，
     * 随后 {@code InvUtils.swap(-1, false)} 直接返回 false 不切槽，{@code :148-150}）。</p>
     */
    private int findInHotbar(Predicate<ItemStack> isGood) {
        if (mc.player == null) return -1;
        for (int slot = 0; slot <= 8; slot++) {
            if (isGood.test(mc.player.getInventory().getItem(slot))) return slot;
        }
        return -1;
    }

    /**
     * 切到指定快捷栏槽位（蓝本 {@code InvUtils.swap(slot, false)}，{@code :346}）。
     *
     * <p>{@code setSelectedSlot} 只改本地选择槽，必须同时发一次携带物同步包，否则服务端仍按旧手持物
     * 处理攻击（与本项目 {@code MiningStateMachine.selectHotbar} 同一做法）；
     * 槽位相同则不发包，避免每刻重置 {@code switchDelay} 计时。</p>
     */
    private void swapToSlot(int slot) {
        if (mc.player == null || slot < 0 || slot > 8) return;
        if (mc.player.getInventory().getSelectedSlot() == slot) return;
        mc.player.getInventory().setSelectedSlot(slot);
        if (mc.getConnection() != null) {
            mc.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
        }
    }

    // ── 游戏模式（蓝本 PlayerUtils.getGameMode，:351-356） ──

    /** 玩家在服务器上的游戏模式；玩家列表还没就绪时返回 {@code null}（等价于蓝本的空返回） */
    private GameType gameMode() {
        if (mc.player == null || mc.getConnection() == null) return null;
        PlayerInfo info = mc.getConnection().getPlayerInfo(mc.player.getUUID());
        return info == null ? null : info.getGameMode();
    }

    // ── Baritone 冻结（蓝本 PathManagers.pause()/resume()，:357-360 / :376-379） ──

    /** Baritone 是否正在寻路（蓝本 {@code BaritonePathManager.isPathing}，{@code :53-55}） */
    private boolean isPathing() {
        try {
            return BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing();
        } catch (Throwable ignored) {
            return false;
        }
    }

    /** 冻结 Baritone（蓝本 {@code PathManagers.pause()}） */
    private void pauseBaritone() {
        registerPauseProcess();
        pathingPaused = true;
    }

    /** 解冻 Baritone（蓝本 {@code PathManagers.resume()}） */
    private void resumeBaritone() {
        pathingPaused = false;
    }

    /**
     * 注册「请求暂停」的 Baritone 进程（蓝本 {@code BaritonePathManager.java:44} 的注册点，
     * 进程体见 {@code :193-223}）。
     *
     * <p>Baritone 的公开 API 没有 pause/resume，蓝本的做法就是注册一个
     * {@code IBaritoneProcess}：{@code isActive()} 返回自己的冻结标志，激活时清空按键并返回
     * {@code REQUEST_PAUSE}。本项目照抄这一机制，只依赖 Baritone 公开接口。
     * Baritone 未加载时注册失败，冻结退化为仅记录标志（不影响攻击本身）。</p>
     */
    private void registerPauseProcess() {
        if (pauseProcessRegistered) return;
        try {
            BaritoneAPI.getProvider().getPrimaryBaritone().getPathingControlManager()
                    .registerProcess(new BaritonePauseProcess());
            pauseProcessRegistered = true;
        } catch (Throwable ignored) {
            // Baritone 缺失：pauseOnCombat 只保留开关语义，不阻塞其它逻辑
        }
    }

    /** 冻结进程体（蓝本 {@code BaritonePathManager.BaritoneProcess}，{@code :193-223} 逐条对应） */
    private final class BaritonePauseProcess implements IBaritoneProcess {

        @Override
        public boolean isActive() {
            return pathingPaused;
        }

        @Override
        public PathingCommand onTick(boolean calcFailed, boolean isSafeToCancel) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getInputOverrideHandler().clearAllKeys();
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }

        @Override
        public boolean isTemporary() {
            return true;
        }

        @Override
        public void onLostControl() {
        }

        @Override
        public double priority() {
            return 0d;
        }

        @Override
        public String displayName0() {
            return "yiyiaddon";
        }
    }

    // ── 服务端 TPS 观测（蓝本 utils/world/TickRate，:21-70 逐字重写） ──

    /**
     * 用 {@code ClientboundSetTimePacket} 的到达间隔估算服务端 TPS 与「距上次服务器刻」。
     *
     * <p>蓝本 {@code TickRate} 的采样算法、20 个样本的滑动窗口、进服后 4 秒的宽限期全部照抄：
     * {@code tickRates[i] = clamp(20 / 间隔秒, 0, 20)}，取非零样本的平均值；
     * 进服（本项目为模块启用）后 4 秒内 {@code tickRate()} 恒 20、{@code timeSinceLastTick()} 恒 0。</p>
     *
     * <p>本项目的数据包事件不携带包对象（只带类名），因此采样时机是主线程每刻出队派发之时，
     * 与蓝本在主线程处理收包事件的时机同量级。</p>
     */
    private static final class TickRateTracker {

        /** 蓝本认的包：{@code ClientboundSetTimePacket}（{@code TickRate.java:35}） */
        private static final String SET_TIME_PACKET = ClientboundSetTimePacket.class.getName();

        /** 样本数（蓝本 {@code new float[20]}，{@code TickRate.java:24}） */
        private static final int SAMPLE_COUNT = 20;

        /** 进服宽限：这段时间内不报卡顿（蓝本 {@code TickRate.java:53 / :68}） */
        private static final long JOIN_GRACE_MILLIS = 4000L;

        private final float[] tickRates = new float[SAMPLE_COUNT];
        private int nextIndex;
        private long timeLastTimeUpdate = -1;
        private long timeGameJoined;

        /** 复位：等价于蓝本的「进服」事件（{@code TickRate.java:44-49}） */
        void reset() {
            Arrays.fill(tickRates, 0f);
            nextIndex = 0;
            timeLastTimeUpdate = timeGameJoined = System.currentTimeMillis();
        }

        boolean isSetTimePacket(String packetName) {
            return SET_TIME_PACKET.equals(packetName);
        }

        /** 收到一次时间同步（蓝本 {@code onReceivePacket}，{@code TickRate.java:34-42}） */
        void onSetTimePacket() {
            long now = System.currentTimeMillis();
            float timeElapsed = (now - timeLastTimeUpdate) / 1000.0F;
            tickRates[nextIndex] = Mth.clamp(20.0f / timeElapsed, 0.0f, 20.0f);
            nextIndex = (nextIndex + 1) % tickRates.length;
            timeLastTimeUpdate = now;
        }

        /** 服务端 TPS（蓝本 {@code getTickRate}，{@code TickRate.java:51-64}） */
        float tickRate() {
            Minecraft client = Minecraft.getInstance();
            // 蓝本 Utils.canUpdate()：不在世界里按 0 处理
            if (client.level == null || client.player == null) return 0;
            if (System.currentTimeMillis() - timeGameJoined < JOIN_GRACE_MILLIS) return 20;

            int samples = 0;
            float sum = 0.0f;
            for (float rate : tickRates) {
                if (rate > 0) {
                    sum += rate;
                    samples++;
                }
            }
            return samples == 0 ? 0 : sum / samples;
        }

        /** 距上次时间同步的秒数（蓝本 {@code getTimeSinceLastTick}，{@code TickRate.java:66-70}） */
        float timeSinceLastTick() {
            long now = System.currentTimeMillis();
            if (now - timeGameJoined < JOIN_GRACE_MILLIS) return 0;
            return (now - timeLastTimeUpdate) / 1000f;
        }
    }
}
