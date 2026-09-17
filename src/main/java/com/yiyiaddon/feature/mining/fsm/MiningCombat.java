package com.yiyiaddon.feature.mining.fsm;

import baritone.api.pathing.goals.GoalNear;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

/**
 * 挖矿自动战斗（用户 2026-09-17 新增需求，默认开启、无设置项）。
 *
 * <p>需求原文：<i>「能不能加个默认配置不用加设置，发现被怪物攻击、或者检测玩家自身附近 6 格
 * 扫描实体附近有怪物，自动切换合适的工具杀死怪物，自动调用杀戮光环还是你自己写一个简单的，
 * 继续进入状态，播报也要提示 xx 怪物，因为老是被怪物打死」</i>。</p>
 *
 * <p>2026-09-18 用户改口径：<i>「不躲避了，苦力怕也直接打，控制好距离，跟其他怪物做区分，
 * 防止被炸死，动态识别走位」</i>——原来的 {@code EVADE} 撤离态与逃跑寻路已整段删除，
 * 苦力怕改成在战斗态里用「打了就退」的走位处理。{@link MinerState} 因此由 11 态回到 10 态。</p>
 *
 * <p>本类自带一套最简战斗循环（不依赖杀戮光环模块的设置，避免「挖矿模块被别人的配置改变行为」）：</p>
 * <ul>
 *   <li><b>发现（主动出击，不等挨打）</b>：玩家自身 {@link #THREAT_RADIUS} 格内扫描 {@link Monster}，
 *       一进入 6 格就立刻寻路过去击杀——不需要等它先打我们（用户 2026-09-17 明确要求
 *       「检测实体进入 6 格范围就提前杀死，防止我被打掉血」）；最近被打过时（远程怪）扫描半径放宽到
 *       {@link #HURT_SCAN_RADIUS} 并同样主动靠近。
 *       <b>墙后的怪不算</b>（用户 2026-09-18：「检测怪物在墙后面不管」，见 {@link #hasClearSight}）——
 *       打不到它、它也不该把我们拖进战斗态。</li>
 *   <li><b>贴近</b>：距离大于 {@link #ATTACK_RANGE} 时用 Baritone 自定义目标点寻路，目标是
 *       「怪物附近」（{@code GoalNear} 到 {@link #CHASE_STOP_DISTANCE} 格内即停，不走到它脸上），
 *       并按它的速度预判 {@link #CHASE_LEAD_TICKS} 刻后的落点；
 *       同一种走位每 {@link #REPATH_INTERVAL} 刻才重发一次，避免 Baritone 反复重算路径。
 *       进入战斗时会<b>临时关掉 Baritone 的怪物规避</b>（{@code avoidance}）——否则 Baritone 会因为
 *       「怪物附近路径代价更高」而不肯靠近，表现成「明明要杀它却在原地绕圈」。
 *       目标跑出威胁半径后最多再追 {@link #CHASE_GIVE_UP_TICKS}，追不上就放弃回挖矿。</li>
 *   <li><b>出手</b>：优先切快捷栏里的剑 → 斧 → 其它武器；分步转头对准目标；攻击冷却满
 *       （{@code getAttackStrengthScale >= 1}）且进入 {@link #ATTACK_RANGE} 才攻击，
 *       走原版 {@code gameMode.attack + swing}，不额外拼包。
 *       <b>不检查视线遮挡</b>：隔着墙也照发攻击包（用户 2026-09-17：「可以穿墙攻击也没事」），
 *       服务端能不能吃下这一刀由服务端自己判，客户端不做多余拦截。</li>
 *   <li><b>苦力怕走位（与其他怪物唯一的区别）</b>：见 {@link #tickCreeper}——贴到出手距离就砍，
 *       砍完立刻拉开等冷却；引信一旦点燃就撤到熄灭距离（依据 26.1.2 原版
 *       {@code SwellGoal} / {@code Creeper#tick} 的引信熄灭条件，逐条写在 {@link #CREEPER_DEFUSE_DISTANCE}）。</li>
 *   <li><b>收工</b>：连续 {@link #CLEAR_TICKS} 刻没有威胁 → 判定安全，交回状态机继续挖矿。</li>
 * </ul>
 *
 * <p>播报：进入战斗 / 引信点燃时各一条，带怪物名与距离
 * （「§c⚠ 发现 僵尸 §8(3.2格) §8▸ 主动出击」），同一条敌情 {@link #REPORT_COOLDOWN_TICKS} 刻内不重复刷屏。</p>
 *
 * <p><b>视角</b>：本类只做「转头盯住目标」，不做整段视角接管——挖矿与寻路的视角归 Baritone
 * （见 {@code AutoMinerModule} 类注释的口径说明），战斗期间 Baritone 的移动朝向是 SERVER 静默模式，
 * 本类的 {@link #turnTowards} 是唯一可见写入者，不存在互相覆盖。</p>
 */
public final class MiningCombat {

    /** 威胁扫描半径（格）：用户指定的「自身周围 6 格」 */
    private static final double THREAT_RADIUS = 6.0;

    /** 被怪物打过之后的扫描半径（格）：远程怪在 6 格外也要处理 */
    private static final double HURT_SCAN_RADIUS = 16.0;

    /** 主动追怪的半径上限（格）：超出即判定追不上，放弃这批目标回去挖矿 */
    private static final double CHASE_RADIUS = 16.0;

    /** 出手距离（格）：原版实体交互距离 3.0 + 余量 */
    private static final double ATTACK_RANGE = 3.2;

    /** 判定「被怪物打过」的时长（刻）：玩家 hurtTime 归零后仍保持这么久的警戒 */
    private static final int HURT_MEMORY_TICKS = 60;

    /** 无威胁持续这么多刻才判定安全、回挖矿 */
    private static final int CLEAR_TICKS = 30;

    /** 单刻最大转角（度） */
    private static final float TURN_STEP = 18.0f;

    /** 战斗寻路的重发间隔（刻）：Baritone 的 setGoalAndPath 不能每刻重发，否则一直重算 */
    private static final int REPATH_INTERVAL = 20;

    /** 目标跑出威胁半径后最多再追这么多刻（10 秒），追不上就放弃回挖矿（避免被一路引走） */
    private static final int CHASE_GIVE_UP_TICKS = 200;

    /** 追击预判刻数：按怪物速度往它前方推这么多刻的位置当目标（比追它当前位置有提前量） */
    private static final double CHASE_LEAD_TICKS = 4.0;

    /** 追击的停止距离（格）：走到怪物这么近就停手，不走到它脸上 */
    private static final int CHASE_STOP_DISTANCE = 2;

    /**
     * 苦力怕「打了就退」的保持距离（格）：出手后拉回到这里等攻击冷却。
     *
     * <p>为什么不贴脸站桩：攻击冷却要等（剑约 12 刻），这段时间贴着它只是白给——它的引信在
     * 3 格内就会点燃（见 {@link #CREEPER_DEFUSE_DISTANCE} 的原文依据）。拉到 5 格既在它爆炸半径
     * （3 格）之外，也还在下一次冲刺的可达范围内。</p>
     */
    private static final double CREEPER_KITE_DISTANCE = 5.0;

    /**
     * 苦力怕引信点燃后的熄灭距离（格）：<b>必须大于 7</b>。
     *
     * <p>26.1.2 原版依据（{@code net/minecraft/world/entity/ai/goal/SwellGoal.java:41-53}）：
     * 引信点燃期间它每刻检查，只要「距目标 &gt; 7 格」或「失去视线」就把 {@code swellDir} 改成 -1，
     * 而 {@code Creeper#tick}（{@code Creeper.java:126-148}）在 {@code swellDir < 0} 时每刻把
     * swell 减 1（降到 0 即完全熄灭），满 30 刻（1.5 秒）才会真的炸。所以撤到 8 格就能叫停引信。</p>
     */
    private static final double CREEPER_DEFUSE_DISTANCE = 8.0;

    /** 闪电苦力怕的熄灭距离（格）：爆炸半径翻倍到 6 格（{@code Creeper#explodeCreeper}），要撤得更远 */
    private static final double CHARGED_CREEPER_DEFUSE_DISTANCE = 12.0;

    /** 后撤时额外多走一点（格）：贴着阈值停会因位置修正又落回危险区 */
    private static final double RETREAT_MARGIN = 1.0;

    /** 后撤目标点的到达半径（格）：不必站到点上，走到附近即可 */
    private static final int RETREAT_STOP_DISTANCE = 1;

    /** 同一条敌情播报的最小间隔（刻） */
    private static final int REPORT_COOLDOWN_TICKS = 100;

    /** 「快捷栏没有武器时去背包取」的最小间隔（刻）：避免一次点背包失败后逐刻重复点击 */
    private static final int WEAPON_FETCH_INTERVAL = 20;

    private final AutoMinerModule module;
    private final Minecraft mc = Minecraft.getInstance();

    /** 当前锁定的威胁（最近的怪物）；无威胁为 null */
    private LivingEntity target;
    /** 最近一次被怪物打过的 tick（{@code -100000} 表示没有） */
    private int lastHurtTick = -100000;
    /** 无威胁连续刻数 */
    private int threatFreeTicks;
    /** 目标跑出威胁半径后已追击的连续刻数（用于放弃追击） */
    private int chaseTicks;
    /** 上一次重发战斗寻路的 tick */
    private int lastRepathTick;
    /** 上一次下发的走位类型；null 表示本轮战斗还没下发过（见 {@link #issuePath}） */
    private PathKind lastPathKind;
    /** 上一条播报的键（类别 + 敌情名）与 tick（节流用） */
    private String lastReportKey = "";
    private int lastReportTick = -100000;
    /** 上一次「去背包取武器」的 tick */
    private int lastWeaponFetchTick;

    public MiningCombat(AutoMinerModule module) {
        this.module = module;
    }

    // ── 发现 ────────────────────────────────────────────────────────────────

    /**
     * 是否应当进入战斗拦截（可关闭「怪物靠近」这条触发）。
     *
     * <p>判据：玩家自身 {@link #THREAT_RADIUS} 格内扫到怪物（持续威胁），或最近 3 秒内被怪物打过
     * （扫到 {@link #HURT_SCAN_RADIUS} 格内的打人者，远程怪也算）。</p>
     *
     * <p>用户 2026-09-18：「寻路途中发现刷怪笼就挖掉，先挖再打怪，不然越打越多怪」——
     * 正在去挖刷怪笼的路上，周围的怪都是它刷出来的，见怪就转头只会被无限拖着打。
     * 此时传 {@code proximityEnabled = false}：忽略「6 格内扫到怪物」这条，但<b>挨打仍然反击</b>
     * （{@link #hurtRecently()} 触发的那条照旧生效），不会站着被白打。</p>
     */
    public boolean threatDetected(boolean proximityEnabled) {
        refreshHurtMemory();
        LivingEntity found = scanThreat(proximityEnabled);
        target = found;
        return found != null;
    }

    /** 安全后清空敌情记录（状态机回到挖矿时调用） */
    public void reset() {
        target = null;
        threatFreeTicks = 0;
        chaseTicks = 0;
        lastRepathTick = 0;
        lastPathKind = null;
    }

    /** 每刻刷新「最近被怪物打过」的记忆窗口 */
    private void refreshHurtMemory() {
        if (mc.player == null || mc.level == null) return;
        if (mc.player.hurtTime <= 0) return;
        LivingEntity attacker = mc.player.getLastHurtByMob();
        if (attacker instanceof Monster && attacker.isAlive()) {
            lastHurtTick = mc.player.tickCount;
        }
    }

    private boolean hurtRecently() {
        return mc.player != null && mc.player.tickCount - lastHurtTick <= HURT_MEMORY_TICKS;
    }

    /** 扫描最近的怪物：被打过时放宽半径（{@link #scanThreat(boolean)} 的默认形态） */
    private LivingEntity scanThreat() {
        return scanThreat(true);
    }

    /**
     * 扫描最近的威胁怪。
     *
     * @param proximityEnabled 是否允许「没被打过也主动出击」（6 格范围）。false 时只有
     *                         {@link #hurtRecently()} 成立（16 格寻敌）才会返回目标
     */
    private LivingEntity scanThreat(boolean proximityEnabled) {
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) return null;

        double radius;
        if (hurtRecently()) {
            radius = HURT_SCAN_RADIUS;
        } else if (proximityEnabled) {
            radius = THREAT_RADIUS;
        } else {
            return null;
        }
        List<Monster> monsters = mc.level.getEntitiesOfClass(Monster.class,
            player.getBoundingBox().inflate(radius),
            monster -> monster.isAlive() && !monster.isRemoved() && !monster.isInvulnerable()
                && isThreat(monster, player)
                && hasClearSight(player, monster));

        LivingEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (Monster monster : monsters) {
            double distance = player.distanceTo(monster);
            if (distance > radius) continue;
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = monster;
            }
        }
        return nearestDistance > CHASE_RADIUS ? null : nearest;
    }

    /**
     * 是否看得见该怪物（射线不被方块挡住）。
     *
     * <p>用户 2026-09-18：「检测怪物在墙后面不管——刚刚检测到墙后的苦力怕，导致我一直躲避
     * 又打不到，卡死状态机」。看不见就不该被锁定：既打不到它，它也不该让我们进战斗态。</p>
     *
     * <p>与「出手是否检查遮挡」是两件事：<b>攻击仍然不检查</b>（用户 2026-09-17：
     * 「可以穿墙攻击也没事」，见类头说明），这里只影响「要不要把它当成威胁目标」。</p>
     */
    private boolean hasClearSight(LocalPlayer player, LivingEntity monster) {
        return player.hasLineOfSight(monster);
    }

    /**
     * 该怪物是否算威胁。
     *
     * <p>中立怪（僵尸猪灵、猪灵这类 {@link NeutralMob}）在<b>没被激怒</b>时不算威胁——否则一进下界就
     * 跟中立怪打起来，既是白挨打也是白费补给；正锁定玩家或已被激怒的中立怪照常算威胁。</p>
     */
    private boolean isThreat(Monster monster, LocalPlayer player) {
        if (monster.getTarget() == player) return true;
        if (!(monster instanceof NeutralMob neutral)) return true;
        return neutral.isAngry();
    }

    // ── 战斗态 ──────────────────────────────────────────────────────────────

    /**
     * 战斗态单刻推进。
     *
     * @return true 表示敌情仍在（保持战斗态）；false 表示已安全或放弃追击，状态机应回挖矿
     */
    public boolean tickCombat() {
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) return false;

        refreshHurtMemory();
        target = scanThreat();
        if (target == null) {
            // 没扫到怪但仍处于挨打窗口内：保持警戒，别刚被打完就低头挖矿
            if (hurtRecently()) return true;
            threatFreeTicks++;
            return threatFreeTicks < CLEAR_TICKS;
        }
        threatFreeTicks = 0;

        // 追击超时：目标跑出威胁半径后只在限定时间内继续追（避免被远程怪一路引到岩浆 / 怪堆里）
        if (player.distanceTo(target) > THREAT_RADIUS) {
            chaseTicks++;
            if (chaseTicks > CHASE_GIVE_UP_TICKS) {
                module.getBaritone().stop();
                module.info("§e⚠ 追不上 " + target.getName().getString() + " §8▸ 放弃追击，返回挖矿");
                target = null;
                chaseTicks = 0;
                return false;
            }
        } else {
            chaseTicks = 0;
        }

        ensureWeapon();
        turnTowards(target);
        if (target instanceof Creeper creeper) {
            tickCreeper(player, creeper);
        } else {
            tickMelee(player, target);
        }
        return true;
    }

    /**
     * 普通怪物打法：贴上去按攻击冷却砍。
     *
     * <p>「不躲避」是用户 2026-09-18 的裁定：血量高低都不是不出手或躲避的理由，
     * 不出手又不躲就是站着挨打。</p>
     */
    private void tickMelee(LocalPlayer player, LivingEntity monster) {
        report("出击", "§c⚠ 发现 " + monster.getName().getString()
            + " §8(" + String.format("%.1f", player.distanceTo(monster)) + "格) §8▸ 主动出击", monster);

        if (player.distanceTo(monster) > ATTACK_RANGE) {
            issuePath(PathKind.APPROACH, predictedPos(monster), CHASE_STOP_DISTANCE);
            return;
        }
        swing(player, monster);
    }

    /**
     * 苦力怕打法：贴到出手距离砍一刀 → 立刻拉开等冷却 → 冷却满了再贴上去，引信点燃则撤远。
     *
     * <p>整个循环只有三个动作，优先级从高到低：</p>
     * <ol>
     *   <li><b>避爆</b>（{@link PathKind#FLEE}）：{@code swellDir > 0} 说明引信已点燃，
     *       撤到 {@link #CREEPER_DEFUSE_DISTANCE}。原版依据：{@code SwellGoal#tick} 在距目标
     *       &gt; 7 格或失去视线时把 {@code swellDir} 置 -1，{@code Creeper#tick} 随即每刻减 1 直至熄灭；
     *       且点燃期间它自己 {@code getNavigation().stop()}（{@code SwellGoal#start}）不再移动，
     *       所以这一段后撤不会被它咬着追。</li>
     *   <li><b>等冷却</b>（{@link PathKind#KITE}）：冷却没好就回到 {@link #CREEPER_KITE_DISTANCE}
     *       格等，不贴着它站——它 3 格内就会点燃引信（{@code SwellGoal#canUse} 的
     *       {@code distanceToSqr(target) < 9.0}）。</li>
     *   <li><b>出手</b>：冷却满且够得到就砍，砍完当刻挂上后撤目标（打了就退）。</li>
     * </ol>
     */
    private void tickCreeper(LocalPlayer player, Creeper creeper) {
        report("出击", "§c⚠ 发现 苦力怕 §8(" + String.format("%.1f", player.distanceTo(creeper))
            + "格) §8▸ 保持距离击杀", creeper);

        // 1) 引信点燃：撤到熄灭距离（闪电苦力怕爆炸半径翻倍，撤得更远）
        if (creeper.getSwellDir() > 0) {
            report("引信", "§e⚠ 苦力怕引信点燃 §8▸ 后撤熄灭", creeper);
            retreat(player, creeper,
                creeper.isPowered() ? CHARGED_CREEPER_DEFUSE_DISTANCE : CREEPER_DEFUSE_DISTANCE,
                PathKind.FLEE);
            return;
        }

        // 2) 冷却没好：拉开距离等冷却（打了就退）
        if (player.getAttackStrengthScale(0.5f) < 1.0f) {
            retreat(player, creeper, CREEPER_KITE_DISTANCE, PathKind.KITE);
            return;
        }

        // 3) 冷却好了但够不到：贴近（预判落点，走到附近即停，不走到它脸上）
        if (player.distanceTo(creeper) > ATTACK_RANGE) {
            issuePath(PathKind.APPROACH, predictedPos(creeper), CHASE_STOP_DISTANCE);
            return;
        }

        // 4) 够得到且冷却满：出手，出手当刻就挂上后撤目标，不在它面前恋战
        mc.gameMode.attack(player, creeper);
        player.swing(InteractionHand.MAIN_HAND);
        retreat(player, creeper, CREEPER_KITE_DISTANCE, PathKind.KITE);
    }

    // ── 武器 / 视角 ─────────────────────────────────────────────────────────

    /** 切到快捷栏里最合适的武器（剑 → 斧 → 其它非挖矿工具 → 保持当前手持） */
    private void ensureWeapon() {
        LocalPlayer player = mc.player;
        if (player == null) return;

        int slot = bestWeaponSlot();
        if (slot == -1 && player.tickCount - lastWeaponFetchTick >= WEAPON_FETCH_INTERVAL) {
            // 快捷栏一把武器都没有：让容器服务把背包里的武器搬进来。
            // 间隔发起（不是每刻），否则一次点背包失败会逐刻重复点击、把快捷栏搅乱
            lastWeaponFetchTick = player.tickCount;
            module.getContainer().ensureToolsInHotbar();
            slot = bestWeaponSlot();
        }
        if (slot == -1) return;
        if (player.getInventory().getSelectedSlot() == slot) return;

        player.getInventory().setSelectedSlot(slot);
        if (mc.getConnection() != null) {
            mc.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
        }
    }

    /** 快捷栏里最好的武器槽：剑 → 斧 → 其它非挖矿工具；都没有返回 -1 */
    private int bestWeaponSlot() {
        int sword = findWeaponSlot(stack -> stack.is(ItemTags.SWORDS));
        if (sword != -1) return sword;
        int axe = findWeaponSlot(stack -> stack.is(ItemTags.AXES));
        if (axe != -1) return axe;
        return findWeaponSlot(stack -> !stack.isEmpty() && !isMiningTool(stack));
    }

    private static boolean isMiningTool(ItemStack stack) {
        return stack.is(ItemTags.PICKAXES) || stack.is(ItemTags.SHOVELS) || stack.is(ItemTags.HOES);
    }

    private int findWeaponSlot(Predicate<ItemStack> accept) {
        LocalPlayer player = mc.player;
        if (player == null) return -1;
        for (int slot = 0; slot <= 8; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (!stack.isEmpty() && accept.test(stack)) return slot;
        }
        return -1;
    }

    /** 分步转头对准目标（战斗期间 Baritone 的移动朝向是 SERVER 静默模式，这里是唯一可见写入者） */
    private void turnTowards(Entity entity) {
        LocalPlayer player = mc.player;
        if (player == null || entity == null) return;
        Vec3 eye = player.getEyePosition();
        Vec3 center = entity.position().add(0, entity.getBbHeight() * 0.6, 0);
        double dx = center.x - eye.x;
        double dy = center.y - eye.y;
        double dz = center.z - eye.z;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        float pitch = (float) Math.toDegrees(-Math.atan2(dy, horizontal));
        player.setYRot(player.getYRot() + stepToward(player.getYRot(), yaw));
        player.setXRot(player.getXRot() + stepToward(player.getXRot(), pitch));
    }

    private float stepToward(float current, float targetAngle) {
        return Mth.clamp(Mth.wrapDegrees(targetAngle - current), -TURN_STEP, TURN_STEP);
    }

    /** 按原版攻击冷却出手（走原版 {@code gameMode.attack + swing}，不额外拼包） */
    private void swing(LocalPlayer player, LivingEntity monster) {
        if (player.getAttackStrengthScale(0.5f) < 1.0f) return;
        mc.gameMode.attack(player, monster);
        player.swing(InteractionHand.MAIN_HAND);
    }

    // ── 寻路（贴近 / 后撤） ─────────────────────────────────────────────────

    /**
     * 战斗寻路的走位类型。
     *
     * <p>类型是「当刻立刻重发」的触发条件（见 {@link #issuePath}）：贴近与后撤必须能立刻切换，
     * 否则引信点燃后要等最多 {@link #REPATH_INTERVAL} 刻才开始跑，人就站在爆炸范围里了。</p>
     *
     * <ul>
     *   <li>{@link #APPROACH} 贴近：走到目标附近（{@link #CHASE_STOP_DISTANCE} 格内）；</li>
     *   <li>{@link #KITE} 拉开：出手后 / 攻击冷却未满时退到 {@link #CREEPER_KITE_DISTANCE} 格；</li>
     *   <li>{@link #FLEE} 避爆：引信点燃后撤到熄灭距离。与 {@link #KITE} 同为「后退」但目标距离不同，
     *       必须分开，否则从拉开切到避爆会被节流吃掉。</li>
     * </ul>
     */
    private enum PathKind {
        APPROACH,
        KITE,
        FLEE
    }

    /**
     * 目标落点预判：按它的速度往前推 {@link #CHASE_LEAD_TICKS} 刻。
     *
     * <p>用户 2026-09-18：「寻路会寻路到怪物的面前，这不是挨打吗，没有动态计算坐标」——
     * 把目标设成「它现在在哪」是没用的，Baritone 走过去要十几刻，到的时候它早就挪了。</p>
     */
    private BlockPos predictedPos(Entity entity) {
        Vec3 motion = entity.getDeltaMovement();
        return BlockPos.containing(entity.getX() + motion.x * CHASE_LEAD_TICKS,
            entity.getY(), entity.getZ() + motion.z * CHASE_LEAD_TICKS);
    }

    /**
     * 下发一次战斗寻路目标。
     *
     * <p>同一种走位按 {@link #REPATH_INTERVAL} 刻节流（Baritone 的 {@code setGoalAndPath}
     * 每刻重发会一直重算路径、人站着不动）；<b>走位类型一变当刻就发</b>——从贴近改成后撤是保命动作，
     * 不能等节流窗口（见 {@link PathKind}）。</p>
     *
     * @param stopDistance 距目标点多少格内即算到达（{@code GoalNear} 的半径）
     */
    private void issuePath(PathKind kind, BlockPos goal, int stopDistance) {
        LocalPlayer player = mc.player;
        if (player == null) return;
        if (kind == lastPathKind && player.tickCount - lastRepathTick < REPATH_INTERVAL) return;
        lastRepathTick = player.tickCount;
        lastPathKind = kind;
        pathToNear(goal, stopDistance);
    }

    /**
     * 后撤：朝「远离目标」的方向寻路，把与目标的水平距离拉到 {@code distance} 格以外。
     *
     * <p>与旧版的区别：不再找「候选落脚点」（那是躲避态的产物，已删除），只按当下的实时坐标算一个
     * 朝外方向的目标点，交给 Baritone 处理地形。已经够远时不重发目标——否则会把它从后撤路上拽回来。</p>
     *
     * @param kind {@link PathKind#KITE} 等冷却 / {@link PathKind#FLEE} 避爆
     */
    private void retreat(LocalPlayer player, LivingEntity threat, double distance, PathKind kind) {
        double awayX = player.getX() - threat.getX();
        double awayZ = player.getZ() - threat.getZ();
        double length = Math.sqrt(awayX * awayX + awayZ * awayZ);
        // 与目标几乎重合（它压在脚下）：水平没有远离方向可言，本刻不设目标，等下一刻
        if (length < 1.0E-4) return;
        awayX /= length;
        awayZ /= length;

        double step = distance - horizontalDistance(player, threat) + RETREAT_MARGIN;
        if (step <= 0.0) return;

        BlockPos goal = BlockPos.containing(player.getX() + awayX * step, player.getY(),
            player.getZ() + awayZ * step);
        issuePath(kind, goal, RETREAT_STOP_DISTANCE);
    }

    /** 与目标的水平（X/Z）距离：走位只看水平面，垂直差交给 Baritone 自己上下 */
    private static double horizontalDistance(LocalPlayer player, LivingEntity threat) {
        double dx = player.getX() - threat.getX();
        double dz = player.getZ() - threat.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /** 寻路到「距目标点 {@code distance} 格以内」即算到达 */
    private void pathToNear(BlockPos pos, int distance) {
        var baritone = module.getBaritone().getBaritoneInstance();
        if (baritone == null) return;
        try {
            baritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(pos, distance));
        } catch (Throwable ignored) {
            // Baritone 未就绪不影响战斗：原地等目标靠近
        }
    }

    // ── 播报 ────────────────────────────────────────────────────────────────

    /**
     * 敌情播报：同一条（类别 + 敌情名）{@link #REPORT_COOLDOWN_TICKS} 刻内不重复。
     *
     * <p>带 {@code key} 是因为同一次交战的「发现」与「引信点燃」是两条不同消息，
     * 若只用怪物名做键，后一条会被前一条的冷却吃掉。</p>
     */
    private void report(String key, String message, LivingEntity entity) {
        if (mc.player == null) return;
        String name = entity == null ? "" : entity.getName().getString();
        String fullKey = key + ":" + name;
        if (fullKey.equals(lastReportKey) && mc.player.tickCount - lastReportTick < REPORT_COOLDOWN_TICKS) return;
        lastReportKey = fullKey;
        lastReportTick = mc.player.tickCount;
        module.info(message);
    }
}
