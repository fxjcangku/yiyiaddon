package com.yiyiaddon.feature.enchant.fsm;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.combat.KillAuraModule;
import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.config.EnchantSettings;
import com.yiyiaddon.feature.enchant.gear.AnvilPlan;
import com.yiyiaddon.feature.enchant.gear.AnvilPlanner;
import com.yiyiaddon.feature.enchant.gear.AnvilStep;
import com.yiyiaddon.feature.enchant.gear.EnchantEvaluationService;
import com.yiyiaddon.feature.enchant.gear.GearEnchantTask;
import com.yiyiaddon.feature.enchant.gear.GearSafetyGuard;
import com.yiyiaddon.feature.enchant.gear.GearTaskQueue;
import com.yiyiaddon.feature.enchant.gear.RecoveryValidator;
import com.yiyiaddon.feature.enchant.gear.TargetProfile;
import com.yiyiaddon.feature.enchant.gear.TaskErrorReason;
import com.yiyiaddon.feature.enchant.gear.XpPlanner;
import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.feature.enchant.model.EnchantRunMode;
import com.yiyiaddon.feature.enchant.model.EnchantTargetMode;
import com.yiyiaddon.feature.enchant.navigation.EnchantPathing;
import com.yiyiaddon.feature.enchant.service.EnchantContainer;
import com.yiyiaddon.feature.enchant.vanilla.EnchantPlanningEngine;
import com.yiyiaddon.feature.enchant.vanilla.TargetMatcher;
import com.yiyiaddon.feature.enchant.vanilla.VanillaEnchantDatabase;
import com.yiyiaddon.feature.enchant.vanilla.VanillaEnchantRuleValidator;
import com.yiyiaddon.platform.container.SilentContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动附魔状态机（旧项目 {@code AutoEnchantBook} 的运行时逻辑，逐字移植）。
 *
 * <p>本类承载旧项目 {@code :847-3229} 的全部运行时行为：启动检查链、关闭清理、
 * 主 Tick 前置门控、通用 12 态与 GEAR 18 态共 30 个 {@code tickXxx}、
 * {@code setState} 副作用与两套状态集合（{@code isWorkState} 11 个 / {@code 是容器操作状态} 8 个）。</p>
 *
 * <p><b>与旧项目的差异（只有以下几处，其余逐条一致）：</b></p>
 * <ol>
 *   <li><b>日志功能已删除</b>（用户 2026-09-16 裁定）：旧 {@code GearCraftReport} 的
 *       {@code begin / disable / flush / recordSkip / recordEnchant / recordAnvil / recordGrind /
 *       recordTooExpensive / recordComplete} 全部调用点不搬；随之 {@code 成品词条} 字段与
 *       {@code 目标词条摘要()} 方法（两处都只服务报告）也不再保留，
 *       {@code tickGearAnvil} phase2 里只为报告而取的 {@code mainGear} 副本一并去掉
 *       （它是一句 {@code .copy()} 的返回值，去掉后合并流程与费用判定完全不变）。</li>
 *   <li><b>「点击开启即识别」的关闭动作</b>：旧 {@code toggle()} → 本项目
 *       {@code mc.execute(() -> ModuleManager.setEnabledSilently(MODULE_ID, false))}
 *       （与 {@code AutoMinerModule.onEnable} 同一做法：延后一帧关，避免在 onActivate 栈内重入）。
 *       因为关闭是延后的，本类用 {@link #active} 门控，只有启动检查链全部通过才允许 tick，
 *       不会出现「半个运行态被 tick 一次」。</li>
 *   <li><b>KillAura 联动</b>：旧 {@code Modules.get().get(KillAura.class)} →
 *       {@code KillAuraModule.MODULE_ID} + {@link ModuleManager}，只关我们自己开的那一个
 *       （照 {@code feature/combat/KillAuraRepairHook} 的做法）。</li>
 *   <li><b>外部依赖</b>：旧 Baritone 直接调用 → {@link EnchantPathing}；
 *       旧 {@code FarmPacketOps.interactBlock} → {@link EnchantContainer}；
 *       旧点位字段（{@code posBook} 等 14 个）→ {@link com.yiyiaddon.feature.enchant.repository.EnchantPointStore}。</li>
 *   <li>旧 {@code 目标装备}</b>解析走 {@code BuiltInRegistries.ITEM}（旧项目走
 *       {@code level.registryAccess()}；物品是静态注册表，取值等价）。</li>
 * </ol>
 */
public final class EnchantStateMachine {

    private final EnchantModule module;
    private final EnchantContainer container;
    private final EnchantPathing pathing;
    private final Minecraft mc = Minecraft.getInstance();

    // ── 常量（旧 :399-412 逐字） ──

    private static final int GUI_OPEN_PENDING = 20;
    private static final int GUI_OPEN_TIMEOUT = 40;
    /** 补给空箱/取物失败重试上限（旧 {@code :412}） */
    private static final int 补给重试上限 = 3;
    /** 卸货失败重试上限（旧 {@code tickGearStoreOutput} 里的字面量 3） */
    private static final int 卸货重试上限 = 3;
    /** 玩家背包（主背包 + 快捷栏）格数，旧项目全部循环的上界 {@code 36} */
    private static final int INVENTORY_SLOTS = 36;

    // ── 内部状态（旧 :392-415） ──

    private EnchantState state = EnchantState.IDLE;
    /** 启动检查链全部通过后为 true；启动失败或关闭时为 false（见类注释第 2 条） */
    private boolean active;
    private String lastNotifiedState = "";
    /** 发包附魔提示去重锁：整次运行只在首次进入附魔阶段播一句，避免附魔→砂轮循环每轮刷屏 */
    private boolean 发包附魔提示已播 = false;
    private int remainingAttempts = 0;
    private int guiTick = 0;
    private int guiPhase = 0;

    private String hitTask = null;
    private int enchantedBookSlot = -1;
    private boolean hangoutViewRestored;

    /** 补给空箱/取物失败重试计数：服务器延迟高时箱子可能暂时同步不到，重试几次再停机 */
    private int 补给重试次数 = 0;

    private final List<String> activeTasks = new ArrayList<>();

    // ── 原版装备附魔（GEAR）运行态（旧 :417-439） ──

    private GearTaskQueue gearQueue;                    // 任务队列（每批取用数量件）
    private GearEnchantTask gearTask;                   // 当前任务
    private TargetProfile gearProfile;                  // 当前目标方案
    private Item gearTargetItem;                        // 目标装备物品类型（由 gearId 解析）
    private int gearEquipSlot = -1;                     // 当前装备在背包的槽位
    private int gearTargetXp = 30;                      // 当前挂机目标经验等级
    private EnchantState gearReturnState = EnchantState.GEAR_IDLE; // 挂机完返回的状态
    private AnvilPlan gearAnvilPlan;                    // 当前铁砧合并计划
    private int gearAnvilPhase = 0;                     // 铁砧发包子相位
    private int gearTakeCount = 0;                      // 本批已取装备数
    private int gearEnchantIndex = 0;                   // 已附魔装备数（批次附魔进度）
    private int 卸货重试次数 = 0;                        // 成品箱卸货失败重试计数
    private boolean gearGrindFromAnvil = false;         // 铁砧「太昂贵」送砂轮清零的标志（此时磨主装备而非找垃圾）
    private int gearAnvilChestSlot = -1;                // 铁砧箱里被拿起的那个铁砧槽位（跨 phase 记住，放回时用）
    private boolean gearAnvil原视角已存 = false;          // 放置铁砧前是否已保存原视角（放置后恢复用）
    private float gearAnvil原Yaw = 0;                    // 放置铁砧前的原 yaw（放置后恢复，避免视角被转走）
    private float gearAnvil原Pitch = 0;                  // 放置铁砧前的原 pitch
    private String 合并前主装备 = "";                     // 铁砧合并前主装备附魔摘要（详细播报用）
    private String 合并前材料 = "";                       // 铁砧合并前材料装备附魔摘要（详细播报用）
    private int 合并费用 = 0;                            // 铁砧合并费用（详细播报用）
    private String 砂轮磨前附魔 = "";                     // 砂轮清除前装备附魔摘要（详细播报用）

    // 运行统计（不影响核心流程，旧 :441-442）
    private int statTotal = 0, statDone = 0, statError = 0;
    private int statEnchant = 0, statGrind = 0, statAnvil = 0, statFarm = 0;

    /** 杀戮光环是否由本状态机开启（旧 {@code KillAura} 只在「我们开的」时关回去） */
    private boolean killAuraEnabledByUs = false;

    public EnchantStateMachine(EnchantModule module, EnchantContainer container, EnchantPathing pathing) {
        this.module = module;
        this.container = container;
        this.pathing = pathing;
    }

    /** 当前状态（只读，供控制台概览页显示） */
    public EnchantState state() {
        return state;
    }

    // ── 模块开关（旧 onActivate:846-948 / onDeactivate:950-956） ──

    /**
     * 启动检查链（旧 {@code onActivate}）：
     * 世界就绪 → GEAR 支线（5 项校验）/ BOOK·CUSTOM 支线（4 项校验）→ 初始化运行态 → 启动播报。
     *
     * <p>旧第 1 步 {@code reportSelfCheck(selfCheck())} 由本项目 {@link ModuleManager} 在启用前
     * 统一完成（自检不过就不会走到这里），其余每一步的顺序与文案逐字照旧。</p>
     */
    public void activate() {
        if (mc.player == null || mc.level == null) {
            module.info("§c必须进入世界后才能启动模块。");
            disableSelf();
            return;
        }

        if (module.settings().targetMode == EnchantTargetMode.GEAR) {
            activateGear();
            return;
        }
        activateBook();
    }

    /** GEAR 支线（旧 :855-908） */
    private void activateGear() {
        if (module.pointStore().pointServer() == null || module.pointStore().pointDimension() == null) {
            module.error("旧版点位没有服务器和维度信息，请执行 .fumo 清空 后重新设置！");
            disableSelf();
            return;
        }
        if (!module.pointStore().matchesCurrentContext()) {
            module.error("当前服务器或维度与点位不一致，已阻止启动！请切回原世界，或使用 .fumo 清空 重新设置。");
            disableSelf();
            return;
        }
        gearProfile = module.gearProfile();
        if (gearProfile == null || gearProfile.isEmpty()) {
            module.error("请先在「原版装备附魔」分类选择装备与极品方案！");
            disableSelf();
            return;
        }
        // 目标方案 26.1.2 规则校验：非法（不存在装备/互斥/必需附魔不可达）直接阻止启动
        List<String> profileErrors = VanillaEnchantRuleValidator.validateProfile(gearProfile);
        if (!profileErrors.isEmpty()) {
            module.error("目标方案违反 26.1.2 规则：§c" + profileErrors.get(0) + "§7，请重新选择！");
            disableSelf();
            return;
        }
        gearTargetItem = gearItemOf(gearProfile.gearId());
        if (gearTargetItem == null) {
            module.error("无法解析目标装备：" + gearProfile.gearName()
                + "（技术 ID：" + gearProfile.gearId() + "）");
            disableSelf();
            return;
        }

        // 初始化 GEAR 运行态（旧 :888-899，去掉合成报告开关两项）
        gearQueue = null;
        gearTask = null;
        gearEquipSlot = -1;
        gearAnvilPlan = null;
        gearAnvilPhase = 0;
        statTotal = statDone = statError = 0;
        statEnchant = statGrind = statAnvil = statFarm = 0;
        state = EnchantState.GEAR_IDLE;
        lastNotifiedState = "";
        guiTick = 0;
        guiPhase = 0;
        killAuraEnabledByUs = false;

        announceGearStartup();
        active = true;
    }

    /** BOOK / CUSTOM 支线（旧 :909-947） */
    private void activateBook() {
        if (module.pointStore().pointServer() == null || module.pointStore().pointDimension() == null) {
            module.error("旧版点位没有服务器和维度信息，请执行 .fumo 清空 后重新设置！");
            disableSelf();
            return;
        }
        if (module.currentRunMode() == EnchantRunMode.DRAIN && pos(EnchantPointType.AFK) != null) {
            module.info("§7当前为纯附魔模式，忽略挂机位，不会前往挂机区。");
        }
        if (!module.pointStore().matchesCurrentContext()) {
            module.error("当前服务器或维度与点位不一致，已阻止启动！请切回原世界，或使用 .fumo 清空 重新设置。");
            disableSelf();
            return;
        }

        rebuildActiveTasks();
        if (activeTasks.isEmpty()) {
            module.error("任务列表为空，请至少勾选一个附魔书属性！");
            disableSelf();
            return;
        }

        // 初始化 BOOK / CUSTOM 运行态（旧 :931-941）
        remainingAttempts = module.settings().singleRoundDraws;
        state = EnchantState.IDLE;
        lastNotifiedState = "";
        发包附魔提示已播 = false;
        guiTick = 0;
        guiPhase = 0;
        hangoutViewRestored = false;
        container.resetMergeBooks();
        补给重试次数 = 0;
        killAuraEnabledByUs = false;

        int vanillaTasks = countSelectedTasks(List.of(
            EnchantSettings.VANILLA_ARMOR_ENCHANTS,
            EnchantSettings.VANILLA_MELEE_ENCHANTS,
            EnchantSettings.VANILLA_TOOL_ENCHANTS,
            EnchantSettings.VANILLA_BOW_ENCHANTS,
            EnchantSettings.VANILLA_FISHING_ENCHANTS,
            EnchantSettings.VANILLA_TRIDENT_ENCHANTS,
            EnchantSettings.VANILLA_CROSSBOW_ENCHANTS,
            EnchantSettings.VANILLA_COMMON_ENCHANTS
        ));
        int extensionTasks = activeTasks.size() - vanillaTasks;
        announceStartup(activeTasks.size(), vanillaTasks, extensionTasks);
        active = true;
    }

    /** 关闭清理（旧 {@code onDeactivate:950-956}，{@code GearCraftReport.flush()} 已随日志功能删除） */
    public void deactivate() {
        active = false;
        stopKillAura();
        pathing.stop();
    }

    /** 旧 {@code toggle()}：本模块自己判定要停机时调用（延后一帧关，避免生命周期重入） */
    private void disableSelf() {
        mc.execute(() -> ModuleManager.setEnabledSilently(EnchantModule.MODULE_ID, false));
    }

    // ── 启动播报（旧 :958-982 / :2063-2087） ──

    /**
     * BOOK / CUSTOM 启动播报：标签后为<b>全角空格</b>（旧源码为 U+3000），逐字照抄。
     */
    private void announceStartup(int totalTasks, int vanillaTasks, int extensionTasks) {
        StringBuilder report = new StringBuilder();
        report.append("§a§l✓ 自动附魔 · 启动报告");

        // 运行模式：纯附魔 / 挂机循环
        report.append("\n§7当前模式　§8▸ ").append(highlightFunction(module.currentRunMode().toString())).append("§r");

        // 目标词条统计：总量 + 原版/扩展拆分
        report.append("\n§7目标词条　§8▸ ").append(highlightNumber(totalTasks + " 本")).append("§r");
        report.append("\n§7原版词条　§8▸ ").append(highlightNumber(vanillaTasks + " 本")).append("§r");
        report.append("\n§7扩展词条　§8▸ ").append(highlightNumber(extensionTasks + " 本")).append("§r");

        // 单轮抽取只在挂机循环生效，纯附魔模式忽略
        if (module.currentRunMode() == EnchantRunMode.EXPERIENCE) {
            report.append("\n§7单轮抽取　§8▸ ").append(highlightNumber(module.settings().singleRoundDraws + " 次")).append("§r");
        }

        module.info(report.toString());
    }

    /** GEAR 启动播报（目标装备 + 方案 + 目标附魔数） */
    private void announceGearStartup() {
        StringBuilder report = new StringBuilder();
        report.append("§a§l✓ 自动附魔 · 原版装备附魔启动");
        report.append("\n§7运行模式　§8▸ ").append(highlightFunction(module.currentRunMode().toString())).append("§r");
        report.append("\n§7目标装备　§8▸ ").append(highlightText(gearProfile.gearName())).append("§r");
        report.append("\n§7极品方案　§8▸ ").append(highlightFunction(gearProfile.profileName())).append("§r");
        report.append("\n§7目标附魔　§8▸ ").append(highlightNumber(gearProfile.activeTargets().size() + " 项")).append("§r");
        report.append("\n§7极品数量　§8▸ ").append(highlightNumber(module.settings().topGearCount + " 件")).append("§r");
        module.info(report.toString());
    }

    /** GEAR 批次结束播报（统计，不影响核心流程） */
    private void announceGearSummary() {
        StringBuilder report = new StringBuilder();
        report.append("§a§l✓ 原版装备附魔 · 批次完成");
        report.append("\n§7处理装备　§8▸ ").append(highlightNumber(statTotal + " 件")).append("§r");
        report.append("\n§7完成数量　§8▸ ").append(highlightNumber(statDone + " 件")).append("§r");
        report.append("\n§7失败跳过　§8▸ ").append(highlightNumber(statError + " 件")).append("§r");
        report.append("\n§7附魔次数　§8▸ ").append(highlightNumber(statEnchant + " 次")).append("§r");
        report.append("\n§7砂轮次数　§8▸ ").append(highlightNumber(statGrind + " 次")).append("§r");
        report.append("\n§7铁砧次数　§8▸ ").append(highlightNumber(statAnvil + " 次")).append("§r");
        report.append("\n§7挂机次数　§8▸ ").append(highlightNumber(statFarm + " 次")).append("§r");
        module.info(report.toString());
    }

    // ── 界面抑制（旧 onOpenScreen:986-1014 / shouldSuppressScreen:1021-1034） ──

    /**
     * 打开界面事件；返回 {@code true} 表示本次容器界面应被静默取消。
     *
     * <p>逐条照旧：玩家打开背包 → 先关掉模块静默开着的容器菜单，并把容器相位（含铁砧相位）
     * 归零；容器操作状态下模块自己发包打开的容器屏 → 取消显示（不抢鼠标），
     * 铁砧归零 {@code gearAnvilPhase}、补给不重置 {@code guiPhase}（{@code >=10} 表示青金石）、
     * 其余归零 {@code guiPhase}，最后统一把 {@code guiTick} 置为「GUI操作延迟」。</p>
     */
    public boolean onScreenOpen(String screenClassName) {
        if (mc.player == null) return false;

        // 玩家打开背包：模块静默打开的容器菜单还开着就先关掉，避免 containerMenu 状态不一致导致闪退
        if (SilentContainer.isPlayerInventory(screenClassName)) {
            SilentContainer.releaseSilentContainer();
            // 玩家打开背包打断了静默容器操作：重置容器相位，关闭背包后状态机能从头重新打开菜单，
            // 否则 guiPhase 停留在操作中相位，菜单已关闭却无法重开，状态机卡死
            guiPhase = 0;
            guiTick = 0;
            gearAnvilPhase = 0;
        }

        if (!shouldSuppressScreen(screenClassName)) return false;

        // 铁砧合并用独立的 gearAnvilPhase 相位，静默取消屏幕时须同步归零，与 guiPhase 对齐
        if (state == EnchantState.GEAR_ANVIL) {
            gearAnvilPhase = 0;
        } else if (state != EnchantState.RESTOCKING) {
            // 补给状态用 guiPhase 区分书/青金石（>=10 为青金石），重置会破坏 isLapis 判断
            guiPhase = 0;
        }
        guiTick = module.settings().guiDelayTick;
        return true;
    }

    /**
     * 是否静默取消容器屏幕。
     * 只在模块激活且处于容器操作状态时生效，避免干扰玩家手动开箱。
     */
    private boolean shouldSuppressScreen(String screenClassName) {
        if (!module.isEnabled() || !active) return false;
        if (screenClassName == null || screenClassName.isBlank()) return false;
        // 玩家手动打开背包（生存/创造）永远放行，只静默模块自己操作的容器。
        // 创造模式下 InventoryScreen.init 会立即切到 CreativeModeInventoryScreen，
        // 若误静默它会导致 InventoryScreen 停在未初始化状态（配方书 book 为 null）而闪退。
        if (SilentContainer.isPlayerInventory(screenClassName)) return false;
        if (!SilentContainer.isContainerScreen(screenClassName)) return false;
        return state == EnchantState.ENCHANTING || state == EnchantState.GRINDING
            || state == EnchantState.STORING || state == EnchantState.RESTOCKING
            // 原版装备附魔（GEAR）的容器操作同样静默，避免界面闪烁并抢走玩家输入
            || state == EnchantState.GEAR_TAKE_GEAR || state == EnchantState.GEAR_ENCHANTING
            || state == EnchantState.GEAR_RESTOCK_LAPIS || state == EnchantState.GEAR_GRINDING
            || state == EnchantState.GEAR_ANVIL || state == EnchantState.GEAR_TAKE_ANVIL
            || state == EnchantState.GEAR_STORE_OUTPUT;
    }

    // ── Tick 主循环（旧 :1036-1098） ──

    public void tick() {
        if (!active) return;
        if (mc.player == null || mc.level == null) return;
        if (!module.pointStore().matchesCurrentContext()) {
            module.error("检测到服务器或维度发生变化，已停止寻路并关闭模块！");
            stopKillAura();
            pathing.stop();
            disableSelf();
            return;
        }

        // 断点恢复：GEAR 运行中周期验证目标装备仍在背包（死亡掉落 / 掉线丢失即暂停，防盲跑）。
        // 容器操作状态（附魔台/砂轮/铁砧/箱子）下装备在容器里、不在背包是正常的，跳过检查避免误判停机
        if (module.settings().targetMode == EnchantTargetMode.GEAR && gearTargetItem != null
            && (gearQueue != null || gearEquipSlot >= 0)
            && (mc.player.tickCount & 31) == 0
            && !是容器操作状态(state)
            && !RecoveryValidator.hasGearInInventory(mc, new ItemStack(gearTargetItem))) {
            module.error("目标装备不在背包，原版装备极品附魔已暂停。请补充装备后重试。");
            stopKillAura();
            pathing.stop();
            disableSelf();
            return;
        }

        if (pathing.isPathing()) {
            if (!state.name().startsWith("WALK_") && !state.name().startsWith("GEAR_WALK_") && state != EnchantState.FARMING) return;
        }

        switch (state) {
            case IDLE -> tickIdle();
            case WALK_TO_FARM -> tickWalkToFarm();
            case FARMING -> tickFarming();
            case WALK_TO_ENCHANT -> tickWalkToEnchant();
            case ENCHANTING -> tickEnchanting();
            case CHECKING -> tickChecking();
            case WALK_TO_GRIND -> tickWalkToGrind();
            case GRINDING -> tickGrinding();
            case WALK_TO_STORE -> tickWalkToStore();
            case STORING -> tickStoring();
            case WALK_TO_RESTOCK -> tickWalkToRestock();
            case RESTOCKING -> tickRestocking();
            // 原版装备附魔（GEAR）
            case GEAR_IDLE -> tickGearIdle();
            case GEAR_WALK_EQUIPMENT -> tickGearWalkEquipment();
            case GEAR_TAKE_GEAR -> tickGearTakeGear();
            case GEAR_WALK_ENCHANT -> tickGearWalkEnchant();
            case GEAR_ENCHANTING -> tickGearEnchanting();
            case GEAR_WALK_LAPIS -> tickGearWalkLapis();
            case GEAR_RESTOCK_LAPIS -> tickGearRestockLapis();
            case GEAR_EVALUATE -> tickGearEvaluate();
            case GEAR_WALK_GRIND -> tickGearWalkGrind();
            case GEAR_GRINDING -> tickGearGrinding();
            case GEAR_WALK_ANVIL -> tickGearWalkAnvil();
            case GEAR_ANVIL -> tickGearAnvil();
            case GEAR_WALK_ANVIL_BOX -> tickGearWalkAnvilBox();
            case GEAR_TAKE_ANVIL -> tickGearTakeAnvil();
            case GEAR_WALK_ANVIL_POS -> tickGearWalkAnvilPos();
            case GEAR_PLACE_ANVIL -> tickGearPlaceAnvil();
            case GEAR_WALK_OUTPUT -> tickGearWalkOutput();
            case GEAR_STORE_OUTPUT -> tickGearStoreOutput();
        }
    }

    // ── 通用（BOOK / CUSTOM）状态处理器（旧 :1100-1591） ──

    private void tickIdle() {
        if (needRestock()) {
            setState(EnchantState.WALK_TO_RESTOCK);
            return;
        }
        int xpLevel = mc.player.experienceLevel;
        if (module.currentRunMode() == EnchantRunMode.DRAIN) {
            if (xpLevel >= 30) {
                setState(EnchantState.WALK_TO_ENCHANT);
            } else {
                module.info("§c✗ 停机 §8▸ 纯附魔经验低于 " + highlightNumber("30 级") + "§7，切换挂机循环后重新启动");
                disableSelf();
            }
            return;
        }

        if (remainingAttempts <= 0) {
            remainingAttempts = module.settings().singleRoundDraws;
            // 经验已够本轮用（≥ 本轮目标等级）就别白跑一趟挂机点：旧实现无条件去挂机点，
            // FARMING 当刻又满足条件立刻折返，表现为「附魔台 ↔ 挂机位」来回走
            // （用户 2026-09-20 单机把等级调到 2999999 复现）
            setState(xpLevel >= roundTargetLevel() ? EnchantState.WALK_TO_ENCHANT : EnchantState.WALK_TO_FARM);
            return;
        }
        if (xpLevel >= 30) {
            setState(EnchantState.WALK_TO_ENCHANT);
        } else {
            setState(EnchantState.WALK_TO_FARM);
        }
    }

    private void tickWalkToFarm() {
        if (arrivedAtHangout()) {
            setState(EnchantState.FARMING);
        } else {
            pathing.walkToHangout(pos(EnchantPointType.AFK));
        }
    }

    private void tickFarming() {
        // 挂机中玩家手动走离挂机位：先停掉我们开的杀戮光环，再转回寻路态让 Baritone 把玩家拉回挂机点
        // （用户 2026-09-20：挂机循环下手动走通挂机点后没被拉回，可以随意走动——旧项目缺这一判据）
        if (!arrivedAtHangout()) {
            stopKillAura();
            setState(EnchantState.WALK_TO_FARM);
            return;
        }
        restoreHangoutView();
        startKillAura();
        int targetLevel = roundTargetLevel();
        if (mc.player.experienceLevel >= targetLevel) {
            stopKillAura();
            remainingAttempts = module.settings().singleRoundDraws;
            if (module.settings().targetMode == EnchantTargetMode.GEAR) {
                statFarm++;
                setState(gearReturnState);
            } else {
                setState(EnchantState.WALK_TO_ENCHANT);
            }
        }
    }

    /**
     * 挂机循环「本轮」的目标经验等级（唯一实现，{@link #tickFarming} 与
     * {@link #tickIdle} 共用，避免同一判据留两份）：
     * 附魔书 / 自定义模式按「30 级 + 每多一次抽取 3 级」估算，装备模式用当前任务记录的需求等级。
     */
    private int roundTargetLevel() {
        return module.settings().targetMode == EnchantTargetMode.GEAR
            ? gearTargetXp
            : 30 + 3 * (module.settings().singleRoundDraws - 1);
    }

    private void tickWalkToEnchant() {
        BlockPos posEnchant = pos(EnchantPointType.ENCHANTING_TABLE);
        if (!isBlockAt(posEnchant, Blocks.ENCHANTING_TABLE)) {
            pathing.stop();
            module.error("附魔台不存在或已被挖掉，自动化已停止。请重新设置附魔台点位。");
            disableSelf();
            return;
        }
        if (container.canOpenNow(posEnchant)) {
            pathing.stop();
            if (needRestock() || container.countInInventory(Items.LAPIS_LAZULI) < 3) {
                setState(EnchantState.WALK_TO_RESTOCK);
                return;
            }
            guiTick = 0;
            guiPhase = 0;
            setState(EnchantState.ENCHANTING);
        } else {
            pathing.walkToBlock(posEnchant);
        }
    }

    private void tickEnchanting() {
        if (guiTick > 0) { guiTick--; return; }

        // 静默模式下没有 Screen，改为判断 containerMenu 是否已同步为附魔台菜单
        boolean menuOpen = container.enchantMenuOpen();
        BlockPos posEnchant = pos(EnchantPointType.ENCHANTING_TABLE);

        if (!menuOpen && !isBlockAt(posEnchant, Blocks.ENCHANTING_TABLE)) {
            pathing.stop();
            module.error("附魔台不存在或已被挖掉，自动化已停止。请重新设置附魔台点位。");
            disableSelf();
            return;
        }

        if (container.countInInventory(Items.LAPIS_LAZULI) < 3 && !menuOpen) {
            setState(EnchantState.WALK_TO_RESTOCK);
            return;
        }

        if (!menuOpen) {
            if (guiPhase == 0) {
                container.interactBlock(posEnchant);
                guiPhase = GUI_OPEN_PENDING;
                guiTick = GUI_OPEN_TIMEOUT;
                return;
            }
            if (guiPhase == GUI_OPEN_PENDING) {
                if (guiTick == 0) {
                    guiPhase = 0;
                    guiTick = module.settings().guiDelayTick;
                }
                return;
            }
        }
        if (!menuOpen) return;

        EnchantmentMenu handler = (EnchantmentMenu) mc.player.containerMenu;
        int syncId = handler.containerId;

        switch (guiPhase) {
            case 0 -> {
                int bookSlot = container.findInInventory(Items.BOOK);
                if (bookSlot < 0) {
                    module.error("背包无空白书，切至补给！");
                    mc.player.closeContainer();
                    setState(EnchantState.WALK_TO_RESTOCK);
                    return;
                }
                int invSlot = container.containerSlotOf(handler, bookSlot);
                // 用 QUICK_MOVE 只移送 1 本空白书进附魔槽，避免整叠拿起导致剩余书本散落背包
                mc.gameMode.handleContainerInput(syncId, invSlot, 0, ContainerInput.QUICK_MOVE, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 1;
            }
            case 1 -> {
                // 等待附魔槽同步到 1 本空白书后再取青金石
                if (!handler.getSlot(0).getItem().is(Items.BOOK)) return;
                guiTick = module.settings().guiDelayTick;
                guiPhase = 2;
            }
            case 2 -> {
                if (!handler.getSlot(0).getItem().is(Items.BOOK)) return;
                int lapisSlot = container.findInInventoryAtLeast(Items.LAPIS_LAZULI, 3);
                if (lapisSlot < 0) {
                    module.error("青金石不足 3 个，取消本次附魔并补给！");
                    mc.player.closeContainer();
                    setState(EnchantState.WALK_TO_RESTOCK);
                    return;
                }
                int lapisContSlot = container.containerSlotOf(handler, lapisSlot);
                mc.gameMode.handleContainerInput(syncId, lapisContSlot, 0, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 3;
            }
            case 3 -> {
                if (!mc.player.containerMenu.getCarried().is(Items.LAPIS_LAZULI)) return;
                mc.gameMode.handleContainerInput(syncId, 1, 0, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 4;
            }
            case 4 -> {
                if (!handler.getSlot(1).getItem().is(Items.LAPIS_LAZULI) || handler.getSlot(1).getItem().getCount() < 3) {
                    module.error("附魔台青金石未达到 3 个，取消本次附魔并补给！");
                    mc.player.closeContainer();
                    setState(EnchantState.WALK_TO_RESTOCK);
                    return;
                }
                // 30 级档：附魔按钮 index = 2
                mc.gameMode.handleInventoryButtonClick(syncId, 2);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 5;
            }
            case 5 -> {
                if (!handler.getSlot(0).getItem().is(Items.ENCHANTED_BOOK)) return;
                mc.gameMode.handleContainerInput(syncId, 0, 0, ContainerInput.QUICK_MOVE, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 6;
            }
            case 6 -> {
                mc.player.closeContainer();
                guiTick = module.settings().guiDelayTick;
                if (module.currentRunMode() == EnchantRunMode.EXPERIENCE) remainingAttempts--;
                setState(EnchantState.CHECKING);
            }
            default -> {
            }
        }
    }

    private void tickChecking() {
        enchantedBookSlot = container.findInInventory(Items.ENCHANTED_BOOK);
        if (enchantedBookSlot < 0) {
            setState(EnchantState.IDLE);
            return;
        }
        ItemStack book = mc.player.getInventory().getItem(enchantedBookSlot);
        hitTask = null;
        List<Component> tooltip = book.getTooltipLines(Item.TooltipContext.of(mc.level), mc.player, TooltipFlag.NORMAL);
        for (Component line : tooltip) {
            String text = line.getString();
            for (String task : activeTasks) {
                if (matchesEnchantmentTask(text, task)) {
                    hitTask = task;
                    break;
                }
            }
            if (hitTask != null) break;
        }
        if (hitTask != null) {
            playSuccessSound();
            module.info("§a✓ 命中目标附魔书 §8▸ " + highlightText(hitTask));
            setState(EnchantState.WALK_TO_STORE);
        } else {
            setState(EnchantState.WALK_TO_GRIND);
        }
    }

    /** 词条匹配（旧 {@code :1306-1338}，逐字） */
    private boolean matchesEnchantmentTask(String tooltipLine, String task) {
        String line = normalizeEnchantmentText(tooltipLine);
        String target = normalizeEnchantmentText(task);
        if (line.equals(target)) return true;

        String compactTarget = target.replace(" ", "");
        int separator = target.lastIndexOf(' ');
        String name;
        String level;
        if (separator > 0 && separator < target.length() - 1) {
            name = target.substring(0, separator);
            level = target.substring(separator + 1);
        } else if (compactTarget.matches(".+\\d+")) {
            int levelStart = compactTarget.length();
            while (levelStart > 0 && Character.isDigit(compactTarget.charAt(levelStart - 1))) levelStart--;
            name = compactTarget.substring(0, levelStart);
            level = compactTarget.substring(levelStart);
        } else {
            return line.replace(" ", "").contains(compactTarget);
        }

        if (!level.matches("\\d+") || name.isEmpty()) return line.replace(" ", "").contains(compactTarget);

        int nameIndex = line.indexOf(name);
        if (nameIndex < 0) nameIndex = line.replace(" ", "").indexOf(name.replace(" ", ""));
        if (nameIndex < 0) return false;

        String compactLine = line.replace(" ", "");
        int compactNameIndex = compactLine.indexOf(name.replace(" ", ""));
        if (compactNameIndex < 0) return false;
        String suffix = compactLine.substring(compactNameIndex + name.replace(" ", "").length()).trim();
        return suffix.matches("^(?:[：:\\-—|]?等级?)?" + level + "(?:\\D.*|$)");
    }

    /** 罗马数字 / 全角空格归一（旧 {@code normalizeEnchantmentText:1340-1364}，逐字） */
    private static String normalizeEnchantmentText(String text) {
        return text
            .replace('\u3000', ' ')
            .replace("Ⅹ", "10")
            .replace("Ⅸ", "9")
            .replace("Ⅷ", "8")
            .replace("Ⅶ", "7")
            .replace("Ⅵ", "6")
            .replace("Ⅴ", "5")
            .replace("Ⅳ", "4")
            .replace("Ⅲ", "3")
            .replace("Ⅱ", "2")
            .replace("Ⅰ", "1")
            .replaceAll("(?<![A-Za-z])VIII(?![A-Za-z])", "8")
            .replaceAll("(?<![A-Za-z])VII(?![A-Za-z])", "7")
            .replaceAll("(?<![A-Za-z])VI(?![A-Za-z])", "6")
            .replaceAll("(?<![A-Za-z])IV(?![A-Za-z])", "4")
            .replaceAll("(?<![A-Za-z])IX(?![A-Za-z])", "9")
            .replaceAll("(?<![A-Za-z])V(?![A-Za-z])", "5")
            .replaceAll("(?<![A-Za-z])III(?![A-Za-z])", "3")
            .replaceAll("(?<![A-Za-z])II(?![A-Za-z])", "2")
            .replaceAll("(?<![A-Za-z])I(?![A-Za-z])", "1")
            .replaceAll("\\s+", " ")
            .trim();
    }

    private void tickWalkToGrind() {
        BlockPos posGrindstone = pos(EnchantPointType.GRINDSTONE);
        if (container.canOpenNow(posGrindstone)) {
            pathing.stop();
            guiTick = 0;
            guiPhase = 0;
            setState(EnchantState.GRINDING);
        } else {
            pathing.walkToBlock(posGrindstone);
        }
    }

    private void tickGrinding() {
        if (guiTick > 0) { guiTick--; return; }

        boolean menuOpen = container.grindMenuOpen();
        BlockPos posGrindstone = pos(EnchantPointType.GRINDSTONE);

        if (!menuOpen) {
            if (guiPhase == 0) {
                container.interactBlock(posGrindstone);
                guiPhase = GUI_OPEN_PENDING;
                guiTick = GUI_OPEN_TIMEOUT;
                return;
            }
            if (guiPhase == GUI_OPEN_PENDING) {
                if (guiTick == 0) {
                    guiPhase = 0;
                    guiTick = module.settings().guiDelayTick;
                }
                return;
            }
        }
        if (!menuOpen) return;

        GrindstoneMenu handler = (GrindstoneMenu) mc.player.containerMenu;
        int syncId = handler.containerId;

        switch (guiPhase) {
            case 0 -> {
                if (enchantedBookSlot < 0 || mc.player.getInventory().getItem(enchantedBookSlot).getItem() != Items.ENCHANTED_BOOK) {
                    setState(EnchantState.IDLE);
                    return;
                }
                int bookContSlot = container.containerSlotOf(handler, enchantedBookSlot);
                mc.gameMode.handleContainerInput(syncId, bookContSlot, 0, ContainerInput.PICKUP, mc.player);
                mc.gameMode.handleContainerInput(syncId, 0, 0, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 1;
            }
            case 1 -> {
                ItemStack output = handler.getSlot(2).getItem();
                if (!output.is(Items.BOOK)) return;
                mc.gameMode.handleContainerInput(syncId, 2, 0, ContainerInput.QUICK_MOVE, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 2;
            }
            case 2 -> {
                // 把背包里分散的空白书合并到同一堆叠，避免分开放
                if (container.mergeBooksStep(handler, syncId)) {
                    guiTick = module.settings().guiDelayTick;
                } else {
                    container.resetMergeBooks();
                    guiTick = module.settings().guiDelayTick;
                    guiPhase = 3;
                }
            }
            case 3 -> {
                mc.player.closeContainer();
                guiTick = module.settings().guiDelayTick;
                setState(EnchantState.IDLE);
            }
            default -> {
            }
        }
    }

    private void tickWalkToStore() {
        BlockPos posOutput = pos(EnchantPointType.OUTPUT_STORAGE);
        if (container.canOpenNow(posOutput)) {
            pathing.stop();
            guiTick = 0;
            guiPhase = 0;
            setState(EnchantState.STORING);
        } else {
            pathing.walkToBlock(posOutput);
        }
    }

    private void tickStoring() {
        if (guiTick > 0) { guiTick--; return; }

        BlockPos posOutput = pos(EnchantPointType.OUTPUT_STORAGE);
        if (!container.chestMenuOpen()) {
            if (guiPhase == 0) {
                container.interactBlock(posOutput);
                guiTick = module.settings().guiDelayTick;
                return;
            }
        }
        if (!container.chestMenuOpen()) return;

        ChestMenu handler = (ChestMenu) mc.player.containerMenu;
        int syncId = handler.containerId;

        switch (guiPhase) {
            case 0 -> {
                boolean hasFreeSlot = false;
                for (int i = 0; i < handler.getRowCount() * 9; i++) {
                    if (handler.getSlot(i).getItem().isEmpty()) { hasFreeSlot = true; break; }
                }
                if (!hasFreeSlot) {
                    mc.player.closeContainer();
                    module.error("成品箱已满！自动停机。");
                    disableSelf();
                    return;
                }
                if (enchantedBookSlot < 0 || mc.player.getInventory().getItem(enchantedBookSlot).getItem() != Items.ENCHANTED_BOOK) {
                    mc.player.closeContainer();
                    setState(EnchantState.IDLE);
                    return;
                }
                mc.gameMode.handleContainerInput(syncId, container.containerSlotOf(handler, enchantedBookSlot), 0, ContainerInput.QUICK_MOVE, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 1;
            }
            case 1 -> {
                mc.player.closeContainer();
                if (hitTask != null) {
                    activeTasks.remove(hitTask);
                    module.info("§a已存入：§e" + hitTask + "§f，剩余任务：" + activeTasks.size());
                    hitTask = null;
                }
                if (activeTasks.isEmpty()) {
                    module.error("所有极品任务已完成！自动停机。");
                    disableSelf();
                    return;
                }
                setState(EnchantState.IDLE);
            }
            default -> {
            }
        }
    }

    private void tickWalkToRestock() {
        if (needBookRestock()) {
            BlockPos posBook = pos(EnchantPointType.BOOK_STORAGE);
            if (container.canOpenNow(posBook)) {
                pathing.stop();
                guiTick = 0;
                guiPhase = 0;
                setState(EnchantState.RESTOCKING);
            } else {
                pathing.walkToBlock(posBook);
            }
        } else if (needLapisRestock()) {
            BlockPos posLapis = pos(EnchantPointType.LAPIS_STORAGE);
            if (container.canOpenNow(posLapis)) {
                pathing.stop();
                setState(EnchantState.RESTOCKING);
                guiPhase = 10;
            } else {
                pathing.walkToBlock(posLapis);
            }
        } else {
            setState(EnchantState.IDLE);
        }
    }

    // phase 0-9 = 补书；phase 10-19 = 补青金石
    private void tickRestocking() {
        if (guiTick > 0) { guiTick--; return; }
        if (container.inventoryFull()) {
            mc.player.closeContainer();
            module.error("背包已满，无法继续补给！自动停机。");
            disableSelf();
            return;
        }

        boolean isLapis = (guiPhase >= 10);
        BlockPos targetPos = isLapis ? pos(EnchantPointType.LAPIS_STORAGE) : pos(EnchantPointType.BOOK_STORAGE);

        if (!container.chestMenuOpen()) {
            if (guiPhase == 0 || guiPhase == 10) {
                container.interactBlock(targetPos);
                guiTick = module.settings().guiDelayTick;
                guiPhase = isLapis ? 11 : 1;
                return;
            }
        }
        if (!container.chestMenuOpen()) return;

        ChestMenu handler = (ChestMenu) mc.player.containerMenu;
        int syncId = handler.containerId;

        Item targetItem = isLapis ? Items.LAPIS_LAZULI : Items.BOOK;
        int currentCount = container.countInInventory(targetItem);
        int needCount = (isLapis ? module.settings().lapisSupplyGroups : module.settings().bookSupplyGroups) * 64 - currentCount;

        if (needCount <= 0) {
            mc.player.closeContainer();
            if (!isLapis && needLapisRestock()) {
                setState(EnchantState.WALK_TO_RESTOCK);
                guiPhase = 10;
            } else {
                setState(EnchantState.IDLE);
            }
            return;
        }

        int grabbed = 0;
        for (int i = 0; i < handler.getRowCount() * 9 && grabbed < needCount; i++) {
            ItemStack stack = handler.getSlot(i).getItem();
            if (stack.getItem() == targetItem) {
                int movedCount = stack.getCount();
                mc.gameMode.handleContainerInput(syncId, i, 0, ContainerInput.QUICK_MOVE, mc.player);
                grabbed += movedCount;
                补给重试次数 = 0;
                guiTick = module.settings().guiDelayTick;
                return;
            }
        }

        String name = isLapis ? "青金石" : "空白书";
        mc.player.closeContainer();
        // 空箱可能是服务器延迟导致物品还没同步，重试几次再停机，避免误判
        if (++补给重试次数 < 补给重试上限) {
            module.info("§e⚠ " + name + "补给箱暂时没拿到，正在重试（第 " + 补给重试次数 + " 次）...");
            setState(EnchantState.WALK_TO_RESTOCK);
            guiPhase = isLapis ? 10 : 0;
            return;
        }
        补给重试次数 = 0;
        module.error(name + "补给箱已空！自动停机。");
        disableSelf();
    }

    // ── 原版装备附魔（GEAR）状态处理器（旧 :2061-3229） ──

    /** gearId（如 minecraft:diamond_sword）→ 物品类型 */
    private Item gearItemOf(String gearId) {
        if (gearId == null || mc.level == null) return null;
        Identifier id = Identifier.tryParse(gearId);
        if (id == null) return null;
        Item item = BuiltInRegistries.ITEM.getValue(id);
        return item == null || item == Items.AIR ? null : item;
    }

    /** 推进到下一件装备（清理单件运行态，保留批次） */
    private void gearNext() {
        if (gearQueue != null) gearQueue.advance();
        gearEquipSlot = -1;
        gearAnvilPlan = null;
        gearAnvilPhase = 0;
    }

    /** 标记当前装备处理失败并跳过，继续下一件（闭环为工具箱→附魔台→砂轮→铁砧→成品箱，失败件不再单独存箱） */
    private void gearFail(TaskErrorReason reason) {
        if (gearTask != null) gearTask.markError(reason);
        statError++;
        module.error("装备处理失败 → " + reason + "，已跳过该件。");
        gearNext();
        setState(EnchantState.GEAR_IDLE);
    }

    /** 是否为 GEAR 容器操作状态（装备在附魔台/砂轮/铁砧/箱子里，不在背包） */
    private boolean 是容器操作状态(EnchantState s) {
        return s == EnchantState.GEAR_ENCHANTING || s == EnchantState.GEAR_GRINDING
            || s == EnchantState.GEAR_ANVIL || s == EnchantState.GEAR_TAKE_ANVIL
            || s == EnchantState.GEAR_TAKE_GEAR || s == EnchantState.GEAR_RESTOCK_LAPIS
            || s == EnchantState.GEAR_STORE_OUTPUT || s == EnchantState.GEAR_PLACE_ANVIL;
    }

    /** 是否为需要播报进度的工作状态（排除寻路过渡、待机及附魔循环内的高速状态）—— 11 个，逐字照旧 */
    private boolean isWorkState(EnchantState s) {
        return s == EnchantState.FARMING || s == EnchantState.STORING || s == EnchantState.RESTOCKING
            || s == EnchantState.GEAR_TAKE_GEAR || s == EnchantState.GEAR_ENCHANTING
            || s == EnchantState.GEAR_RESTOCK_LAPIS
            || s == EnchantState.GEAR_GRINDING || s == EnchantState.GEAR_ANVIL
            || s == EnchantState.GEAR_TAKE_ANVIL || s == EnchantState.GEAR_PLACE_ANVIL
            || s == EnchantState.GEAR_STORE_OUTPUT;
    }

    /** 在玩家背包查找已附魔的目标装备（ENCHANTMENTS 非空），返回槽位，无则 -1 */
    private int findEnchantedGear() {
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.is(gearTargetItem) && !EnchantEvaluationService.readEnchantments(stack).isEmpty()) return i;
        }
        return -1;
    }

    /** 在玩家背包查找需砂轮的垃圾装备（禁止/互斥/零命中/低密度），返回槽位，无则 -1 */
    private int findJunkGear() {
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.is(gearTargetItem) && !EnchantEvaluationService.readEnchantments(stack).isEmpty()
                && TargetMatcher.shouldGrind(stack, gearProfile)) {
                return i;
            }
        }
        return -1;
    }

    /** 在玩家背包查找未附魔的裸装备（ENCHANTMENTS 为空），返回槽位，无则 -1 */
    private int findUnenchantedGear(Item item) {
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.is(item) && EnchantEvaluationService.readEnchantments(stack).isEmpty()) return i;
        }
        return -1;
    }

    /** 收集玩家背包里所有已附魔的目标装备（批次附魔结果） */
    private List<ItemStack> collectGears() {
        List<ItemStack> gears = new ArrayList<>();
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.is(gearTargetItem) && !EnchantEvaluationService.readEnchantments(stack).isEmpty()) {
                gears.add(stack);
            }
        }
        return gears;
    }

    /** 找对目标贡献最多的装备槽位（作为铁砧合并主装备），无则 -1 */
    private int findBestGearSlot() {
        int bestSlot = -1;
        int bestScore = -1;
        int bestRepair = Integer.MAX_VALUE;
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.is(gearTargetItem)) continue;
            // 排除垃圾装备（禁止/互斥/零命中），避免铁砧合并到含 fire_protection 等互斥附魔的装备导致费用=0 死循环
            if (TargetMatcher.isJunk(stack, gearProfile)) continue;
            int score = AnvilPlanner.contributionScore(stack, gearProfile);
            int rep = AnvilPlanner.repairCost(stack);
            // PWP 低优先（主装备 PWP 累积到后续合并），PWP 相同贡献高优先
            if (rep < bestRepair || (rep == bestRepair && score > bestScore)) {
                bestRepair = rep;
                bestScore = score;
                bestSlot = i;
            }
        }
        return bestSlot;
    }

    /** 在背包找第一件已达标的成品装备槽位（严格满级），无则 -1 */
    private int findCompleteGearSlot() {
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.is(gearTargetItem)) continue;
            if (TargetMatcher.isComplete(stack, gearProfile)) return i;
        }
        return -1;
    }

    /** 找带目标附魔的另一件装备槽位（作为铁砧合并材料，排除主装备槽位），无则 -1 */
    private int findMergeGearSlot(int excludeSlot) {
        int bestSlot = -1;
        int bestRepair = Integer.MAX_VALUE;
        int bestScore = -1;
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            if (i == excludeSlot) continue;
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.is(gearTargetItem)) continue;
            if (TargetMatcher.isJunk(stack, gearProfile)) continue;
            if (!AnvilPlanner.hasTargetContribution(stack, gearProfile)) continue;
            // PWP 低优先（低惩罚材料先合并），PWP 相同贡献高优先，替代原「按槽位顺序找第一个」
            int rep = AnvilPlanner.repairCost(stack);
            int score = AnvilPlanner.contributionScore(stack, gearProfile);
            if (rep < bestRepair || (rep == bestRepair && score > bestScore)) {
                bestRepair = rep;
                bestScore = score;
                bestSlot = i;
            }
        }
        return bestSlot;
    }

    /** 查找某个装备快照在背包里的槽位，无则 -1 */
    private int slotOfItem(ItemStack target) {
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            if (mc.player.getInventory().getItem(i) == target) return i;
        }
        return -1;
    }

    /** 当前批次应取装备数量：受「每批取用数量」与「剩余极品数量」双重限制（极品数量达标即不再多取） */
    private int 当前批次数量() {
        int remaining = module.settings().topGearCount - statDone;
        if (remaining <= 0) return 0;
        return Math.min(module.settings().batchTakeCount, remaining);
    }

    /** 根据背包里的目标装备构建批次队列（数量由「剩余极品数量」与「每批取用数量」共同决定） */
    private void buildGearQueue() {
        List<GearEnchantTask> tasks = new ArrayList<>();
        int taskId = 1;
        int batchSize = 当前批次数量();
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.is(gearTargetItem)) {
                tasks.add(new GearEnchantTask(taskId++, stack, gearProfile));
                if (tasks.size() >= batchSize) break;
            }
        }
        if (tasks.isEmpty()) {
            gearQueue = null;
            module.error("装备箱中没有可用的" + (gearProfile == null ? "目标装备" : gearProfile.gearName()) + "，原版装备极品附魔已暂停。");
            disableSelf();
            return;
        }
        gearQueue = new GearTaskQueue(tasks);
        statTotal += tasks.size();
    }

    private void tickGearIdle() {
        if (gearQueue == null) {
            // 优先复用背包里已有的装备（上次运行残留）：已附魔的送评估继续合并，裸装备送附魔，都没有才去装备箱取
            if (findEnchantedGear() >= 0) {
                gearEnchantIndex = 0;
                setState(EnchantState.GEAR_EVALUATE);
                return;
            }
            if (findUnenchantedGear(gearTargetItem) >= 0) {
                gearEnchantIndex = 0;
                setState(EnchantState.GEAR_WALK_ENCHANT);
                return;
            }
            setState(EnchantState.GEAR_WALK_EQUIPMENT);
            return;
        }
        if (gearQueue.hasNext()) {
            gearTask = gearQueue.current();
            gearEnchantIndex = 0;
            setState(EnchantState.GEAR_WALK_ENCHANT);
            return;
        }
        // 批次耗尽：极品数量未达标则继续去装备箱取下一批，达标才停机
        if (statDone >= module.settings().topGearCount) {
            announceGearSummary();
            disableSelf();
        } else {
            gearQueue = null;
            setState(EnchantState.GEAR_WALK_EQUIPMENT);
        }
    }

    private void tickGearWalkEquipment() {
        BlockPos posEquipment = pos(EnchantPointType.EQUIPMENT_STORAGE);
        if (container.canOpenNow(posEquipment)) {
            pathing.stop();
            guiTick = 0;
            guiPhase = 0;
            gearTakeCount = 0;
            setState(EnchantState.GEAR_TAKE_GEAR);
        } else {
            pathing.walkToBlock(posEquipment);
        }
    }

    private void tickGearTakeGear() {
        if (guiTick > 0) { guiTick--; return; }
        if (container.inventoryFull()) {
            mc.player.closeContainer();
            module.error("背包已满，无法继续取装备！自动停机。");
            disableSelf();
            return;
        }
        BlockPos posEquipment = pos(EnchantPointType.EQUIPMENT_STORAGE);
        if (!container.chestMenuOpen()) {
            if (guiPhase == 0) {
                container.interactBlock(posEquipment);
                guiTick = module.settings().guiDelayTick;
                return;
            }
        }
        if (!container.chestMenuOpen()) return;
        ChestMenu handler = (ChestMenu) mc.player.containerMenu;
        int syncId = handler.containerId;
        // 每 tick 取一件目标装备，最多「每批取用数量」件（只取 gearTargetItem，其他装备不拿）
        for (int i = 0; i < handler.getRowCount() * 9; i++) {
            ItemStack stack = handler.getSlot(i).getItem();
            if (stack.is(gearTargetItem)) {
                mc.gameMode.handleContainerInput(syncId, i, 0, ContainerInput.QUICK_MOVE, mc.player);
                guiTick = module.settings().guiDelayTick;
                gearTakeCount++;
                if (gearTakeCount >= 当前批次数量()) {
                    mc.player.closeContainer();
                    guiTick = module.settings().guiDelayTick;
                    gearTakeCount = 0;
                    buildGearQueue();
                    setState(EnchantState.GEAR_IDLE);
                }
                return;
            }
        }
        // 箱子空或没有更多目标装备
        mc.player.closeContainer();
        guiTick = module.settings().guiDelayTick;
        if (gearTakeCount > 0) {
            // 已取到部分装备（不足一批），按已取数量继续处理
            gearTakeCount = 0;
            buildGearQueue();
            setState(EnchantState.GEAR_IDLE);
        } else {
            // 一件都没取到：装备箱来源枯竭，直接停机
            module.error("装备箱中没有可用的" + (gearProfile == null ? "目标装备" : gearProfile.gearName()) + "，原版装备极品附魔已暂停。");
            disableSelf();
        }
    }

    private void tickGearWalkEnchant() {
        BlockPos posEnchant = pos(EnchantPointType.ENCHANTING_TABLE);
        if (!isBlockAt(posEnchant, Blocks.ENCHANTING_TABLE)) {
            pathing.stop();
            module.error("附魔台不存在或已被挖掉！自动停机。");
            disableSelf();
            return;
        }
        if (container.canOpenNow(posEnchant)) {
            pathing.stop();
            // 附魔台固定需要 30 级；纯附魔模式下不足直接停机，挂机循环则去挂机补经验
            if (XpPlanner.needsEnchantGrinding(mc.player.experienceLevel)) {
                if (module.currentRunMode() == EnchantRunMode.DRAIN) {
                    module.info("§c✗ 停机 §8▸ 纯附魔经验不足 " + highlightNumber("30 级") + "§7，切换挂机循环后重新启动");
                    disableSelf();
                    return;
                }
                gearTargetXp = XpPlanner.ENCHANT_TABLE_LEVEL;
                gearReturnState = EnchantState.GEAR_WALK_ENCHANT;
                setState(EnchantState.WALK_TO_FARM);
                return;
            }
            // 找一件未附魔的裸装备；没有则说明本批已全部附魔完，进入对比
            gearEquipSlot = findUnenchantedGear(gearTargetItem);
            if (gearEquipSlot < 0) {
                setState(EnchantState.GEAR_EVALUATE);
                return;
            }
            if (container.countInInventory(Items.LAPIS_LAZULI) < 3) {
                // 青金石不足：前往青金石箱补给（复用青金石补给组数 + 青金石箱点位）
                setState(EnchantState.GEAR_WALK_LAPIS);
                return;
            }
            guiTick = 0;
            guiPhase = 0;
            setState(EnchantState.GEAR_ENCHANTING);
        } else {
            pathing.walkToBlock(posEnchant);
        }
    }

    private void tickGearEnchanting() {
        if (guiTick > 0) { guiTick--; return; }
        boolean menuOpen = container.enchantMenuOpen();
        BlockPos posEnchant = pos(EnchantPointType.ENCHANTING_TABLE);
        if (!menuOpen && !isBlockAt(posEnchant, Blocks.ENCHANTING_TABLE)) {
            pathing.stop();
            module.error("附魔台不存在或已被挖掉！自动停机。");
            disableSelf();
            return;
        }
        if (!menuOpen) {
            if (guiPhase == 0) {
                container.interactBlock(posEnchant);
                guiPhase = GUI_OPEN_PENDING;
                guiTick = GUI_OPEN_TIMEOUT;
                return;
            }
            if (guiPhase == GUI_OPEN_PENDING) {
                if (guiTick == 0) {
                    guiPhase = 0;
                    guiTick = module.settings().guiDelayTick;
                }
                return;
            }
        }
        if (!menuOpen) return;
        EnchantmentMenu handler = (EnchantmentMenu) mc.player.containerMenu;
        int syncId = handler.containerId;
        switch (guiPhase) {
            case 0 -> {
                if (gearEquipSlot < 0 || mc.player.getInventory().getItem(gearEquipSlot).isEmpty()) {
                    mc.player.closeContainer();
                    gearQueue = null;
                    setState(EnchantState.GEAR_WALK_EQUIPMENT);
                    return;
                }
                int contSlot = container.containerSlotOf(handler, gearEquipSlot);
                mc.gameMode.handleContainerInput(syncId, contSlot, 0, ContainerInput.QUICK_MOVE, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 1;
            }
            case 1 -> {
                if (!handler.getSlot(0).getItem().is(gearTargetItem)) return;
                guiTick = module.settings().guiDelayTick;
                guiPhase = 2;
            }
            case 2 -> {
                if (!handler.getSlot(0).getItem().is(gearTargetItem)) return;
                int lapisSlot = container.findInInventoryAtLeast(Items.LAPIS_LAZULI, 3);
                if (lapisSlot < 0) {
                    mc.player.closeContainer();
                    module.error("青金石不足 3 个，无法附魔！自动停机。");
                    disableSelf();
                    return;
                }
                int lapisContSlot = container.containerSlotOf(handler, lapisSlot);
                mc.gameMode.handleContainerInput(syncId, lapisContSlot, 0, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 3;
            }
            case 3 -> {
                if (!mc.player.containerMenu.getCarried().is(Items.LAPIS_LAZULI)) return;
                mc.gameMode.handleContainerInput(syncId, 1, 0, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 4;
            }
            case 4 -> {
                if (!handler.getSlot(1).getItem().is(Items.LAPIS_LAZULI) || handler.getSlot(1).getItem().getCount() < 3) {
                    mc.player.closeContainer();
                    module.error("附魔台青金石未达到 3 个，取消附魔并停机。");
                    disableSelf();
                    return;
                }
                // 30 级档：附魔按钮 index = 2
                mc.gameMode.handleInventoryButtonClick(syncId, 2);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 5;
            }
            case 5 -> {
                // 附魔完成：slot 0 的装备已获得附魔（附魔前装备为裸装备）
                ItemStack result = handler.getSlot(0).getItem();
                if (!EnchantEvaluationService.readEnchantments(result).isEmpty()) {
                    // 详细播报本次附魔结果（大类 + 获得词条 + 目标进度 + 还缺词条）
                    播报附魔结果(result);
                    mc.gameMode.handleContainerInput(syncId, 0, 0, ContainerInput.QUICK_MOVE, mc.player);
                    guiTick = module.settings().guiDelayTick;
                    guiPhase = 6;
                }
            }
            case 6 -> {
                mc.player.closeContainer();
                guiTick = module.settings().guiDelayTick;
                statEnchant++;
                gearEnchantIndex++;
                // 还有裸装备没附魔则继续，否则进入「对比整批装备」环节
                if (gearQueue != null && gearEnchantIndex < gearQueue.size()) {
                    setState(EnchantState.GEAR_WALK_ENCHANT);
                } else {
                    setState(EnchantState.GEAR_EVALUATE);
                }
            }
            default -> {
            }
        }
    }

    /** 附魔完成后的详细播报：装备大类 + 获得词条 + 目标进度 + 还缺词条（多行合并） */
    private void 播报附魔结果(ItemStack stack) {
        if (gearProfile == null) return;
        TargetMatcher.Result m = TargetMatcher.match(stack, gearProfile);
        int 达标 = m.satisfied().size();
        int 总目标 = gearProfile.activeTargets().size();

        StringBuilder 缺 = new StringBuilder();
        for (String id : m.missing()) {
            if (缺.length() > 0) 缺.append("、");
            VanillaEnchantDatabase.EnchantmentRule r = VanillaEnchantDatabase.get().rule(id);
            缺.append(r != null ? r.name() : 缺条目兜底(id));
        }

        StringBuilder sb = new StringBuilder();
        sb.append("§a✓ 附魔完成 §8▸ ").append(highlightFunction(大类中文(gearProfile.category())))
          .append(" · ").append(highlightText(gearProfile.gearName()));
        sb.append("\n§7获得词条 §8▸ ").append(highlightText(附魔摘要(stack)));
        sb.append("\n§7目标进度 §8▸ ").append(highlightNumber(达标 + "/" + 总目标 + " 项"));
        if (缺.length() > 0) {
            sb.append("\n§7还缺词条 §8▸ ").append(highlightText(缺.toString()));
        }
        module.info(sb.toString());
    }

    /** 装备附魔摘要（中文名+等级，顿号连接），无附魔返回「无」 */
    private String 附魔摘要(ItemStack stack) {
        var ench = EnchantEvaluationService.readEnchantments(stack);
        if (ench.isEmpty()) return "无";
        VanillaEnchantDatabase db = VanillaEnchantDatabase.get();
        StringBuilder sb = new StringBuilder();
        for (var e : ench.entrySet()) {
            if (sb.length() > 0) sb.append("、");
            VanillaEnchantDatabase.EnchantmentRule r = db.rule(e.getKey());
            sb.append(r != null ? r.name() : 缺条目兜底(e.getKey())).append(e.getValue());
        }
        return sb.toString();
    }

    /** 铁砧合并完成播报：主装备 + 材料 + 产物 + 费用 + 进度 */
    private void 合并播报() {
        ItemStack 产物 = gearEquipSlot >= 0 ? mc.player.getInventory().getItem(gearEquipSlot) : ItemStack.EMPTY;
        StringBuilder sb = new StringBuilder();
        sb.append("§a✓ 铁砧合并 §8▸ ").append(highlightText(gearProfile.gearName()));
        sb.append("\n§7主装备 §8▸ ").append(highlightText(合并前主装备));
        sb.append("\n§7材料 §8▸ ").append(highlightText(合并前材料));
        sb.append("\n§7产物 §8▸ ").append(highlightText(附魔摘要(产物)));
        sb.append("\n§7费用 §8▸ ").append(highlightNumber(合并费用 + " 级"));
        if (!产物.isEmpty()) {
            TargetMatcher.Result m = TargetMatcher.match(产物, gearProfile);
            sb.append("\n§7进度 §8▸ ").append(highlightNumber(m.satisfied().size() + "/" + gearProfile.activeTargets().size() + " 项"));
        }
        module.info(sb.toString());
    }

    /** 砂轮清除完成播报：去除了什么词条 */
    private void 砂轮播报() {
        StringBuilder sb = new StringBuilder();
        sb.append("§a✓ 砂轮清除 §8▸ ").append(highlightText(gearProfile.gearName()));
        sb.append("\n§7去除词条 §8▸ ").append(highlightText(砂轮磨前附魔));
        module.info(sb.toString());
    }

    /** 装备大类前缀（TOOL/WEAPON/ARMOR）转中文名 */
    private String 大类中文(String category) {
        if (category == null) return "装备";
        if (category.startsWith("WEAPON")) return "武器";
        if (category.startsWith("ARMOR")) return "护甲";
        if (category.startsWith("TOOL")) return "工具";
        return "装备";
    }

    /** 附魔数据库缺条目时的兜底显示：中文主文案 + 技术 ID，绝不把内部 ID 当主显示 */
    private String 缺条目兜底(String id) {
        return id == null || id.isBlank() ? "未知词条" : "未知词条（技术 ID：" + id + "）";
    }

    private void tickGearWalkLapis() {
        BlockPos posLapis = pos(EnchantPointType.LAPIS_STORAGE);
        if (container.canOpenNow(posLapis)) {
            pathing.stop();
            guiTick = 0;
            guiPhase = 0;
            setState(EnchantState.GEAR_RESTOCK_LAPIS);
        } else {
            pathing.walkToBlock(posLapis);
        }
    }

    private void tickGearRestockLapis() {
        if (guiTick > 0) { guiTick--; return; }
        if (container.inventoryFull()) {
            mc.player.closeContainer();
            module.error("背包已满，无法继续取青金石！自动停机。");
            disableSelf();
            return;
        }
        BlockPos posLapis = pos(EnchantPointType.LAPIS_STORAGE);
        if (!container.chestMenuOpen()) {
            if (guiPhase == 0) {
                container.interactBlock(posLapis);
                guiTick = module.settings().guiDelayTick;
                return;
            }
        }
        if (!container.chestMenuOpen()) return;
        ChestMenu handler = (ChestMenu) mc.player.containerMenu;
        int syncId = handler.containerId;
        // 复用「青金石补给组数」计算补足数量（与附魔书模式同一标准）
        int current = container.countInInventory(Items.LAPIS_LAZULI);
        int need = module.settings().lapisSupplyGroups * 64 - current;
        if (need <= 0) {
            mc.player.closeContainer();
            setState(EnchantState.GEAR_WALK_ENCHANT);
            return;
        }
        // 从青金石箱 QUICK_MOVE 青金石
        for (int i = 0; i < handler.getRowCount() * 9; i++) {
            if (handler.getSlot(i).getItem().is(Items.LAPIS_LAZULI)) {
                mc.gameMode.handleContainerInput(syncId, i, 0, ContainerInput.QUICK_MOVE, mc.player);
                guiTick = module.settings().guiDelayTick;
                return;
            }
        }
        // 青金石箱空：停机，不无限循环
        mc.player.closeContainer();
        module.error("青金石箱已空，无法附魔！原版装备极品附魔已暂停。");
        disableSelf();
    }

    private void tickGearEvaluate() {
        // 收集背包里所有已附魔的目标装备（批次附魔结果 + 保留的高价值中间态）
        List<ItemStack> gears = collectGears();
        if (gears.isEmpty()) {
            // 成品产出后背包目标装备可能已消耗完，属批次结束，不该判失败：
            // 有裸装备则继续附魔，否则去装备箱取下一批
            if (findUnenchantedGear(gearTargetItem) >= 0) {
                gearEnchantIndex = 0;
                setState(EnchantState.GEAR_WALK_ENCHANT);
            } else {
                setState(EnchantState.GEAR_WALK_EQUIPMENT);
            }
            return;
        }
        // 规划引擎决策：成品 / 铁砧 / 砂轮（只磨垃圾）/ 继续附魔 / 不可达
        EnchantPlanningEngine.Decision decision = EnchantPlanningEngine.decide(
            gearProfile, gears, module.settings().anvilStrategy);
        switch (decision.action()) {
            case COMPLETE -> {
                gearEquipSlot = slotOfItem(decision.completeItem());
                setState(EnchantState.GEAR_WALK_OUTPUT);
            }
            case UNREACHABLE -> gearFail(TaskErrorReason.CANNOT_REACH_TARGET);
            case ANVIL -> {
                gearAnvilPlan = decision.anvilPlan();
                setState(EnchantState.GEAR_WALK_ANVIL);
            }
            case GRIND ->
                // 磨垃圾是正常清理，不累计失败（一次附魔可能产出多件垃圾，需逐一磨掉）
                setState(EnchantState.GEAR_WALK_GRIND);
            case CONTINUE -> {
                // 候选不足属正常推进：继续附魔或取下一批，不累计失败（失败计数只留给确定性异常）
                gearEnchantIndex = 0;
                if (findUnenchantedGear(gearTargetItem) >= 0) {
                    setState(EnchantState.GEAR_WALK_ENCHANT);
                } else {
                    setState(EnchantState.GEAR_WALK_EQUIPMENT);
                }
            }
        }
    }

    private void tickGearWalkGrind() {
        BlockPos posGrindstone = pos(EnchantPointType.GRINDSTONE);
        if (container.canOpenNow(posGrindstone)) {
            pathing.stop();
            // 铁砧「太昂贵」送来时磨主装备（清零 prior work penalty），否则按垃圾装备处理
            if (!gearGrindFromAnvil) {
                gearEquipSlot = findJunkGear();
            }
            if (gearEquipSlot < 0 || mc.player.getInventory().getItem(gearEquipSlot).isEmpty()) {
                // 没有垃圾装备了：有已附魔中间态则重新评估合并，否则重新附魔
                gearEnchantIndex = 0;
                gearGrindFromAnvil = false;
                setState(findEnchantedGear() >= 0 ? EnchantState.GEAR_EVALUATE : EnchantState.GEAR_WALK_ENCHANT);
                return;
            }
            guiTick = 0;
            guiPhase = 0;
            setState(EnchantState.GEAR_GRINDING);
        } else {
            pathing.walkToBlock(posGrindstone);
        }
    }

    private void tickGearGrinding() {
        if (guiTick > 0) { guiTick--; return; }
        boolean menuOpen = container.grindMenuOpen();
        BlockPos posGrindstone = pos(EnchantPointType.GRINDSTONE);
        if (!menuOpen) {
            if (guiPhase == 0) {
                container.interactBlock(posGrindstone);
                guiPhase = GUI_OPEN_PENDING;
                guiTick = GUI_OPEN_TIMEOUT;
                return;
            }
            if (guiPhase == GUI_OPEN_PENDING) {
                if (guiTick == 0) {
                    guiPhase = 0;
                    guiTick = module.settings().guiDelayTick;
                }
                return;
            }
        }
        if (!menuOpen) return;
        GrindstoneMenu handler = (GrindstoneMenu) mc.player.containerMenu;
        int syncId = handler.containerId;
        switch (guiPhase) {
            case 0 -> {
                if (gearEquipSlot < 0 || mc.player.getInventory().getItem(gearEquipSlot).isEmpty()) {
                    setState(EnchantState.GEAR_IDLE);
                    return;
                }
                // 记录砂轮清除前附魔（详细播报用）
                砂轮磨前附魔 = 附魔摘要(mc.player.getInventory().getItem(gearEquipSlot));
                int contSlot = container.containerSlotOf(handler, gearEquipSlot);
                mc.gameMode.handleContainerInput(syncId, contSlot, 0, ContainerInput.PICKUP, mc.player);
                mc.gameMode.handleContainerInput(syncId, 0, 0, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 1;
            }
            case 1 -> {
                ItemStack output = handler.getSlot(2).getItem();
                if (!output.is(gearTargetItem)) return;
                mc.gameMode.handleContainerInput(syncId, 2, 0, ContainerInput.QUICK_MOVE, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 2;
            }
            case 2 -> {
                mc.player.closeContainer();
                guiTick = module.settings().guiDelayTick;
                statGrind++;
                砂轮播报();
                // 太昂贵送来的主装备磨完后清标志并重新评估；否则继续磨下一件垃圾
                if (gearGrindFromAnvil) {
                    gearGrindFromAnvil = false;
                    gearEquipSlot = -1;
                    gearEnchantIndex = 0;
                    setState(findEnchantedGear() >= 0 ? EnchantState.GEAR_EVALUATE : EnchantState.GEAR_WALK_ENCHANT);
                } else {
                    gearEquipSlot = findJunkGear();
                    if (gearEquipSlot >= 0) {
                        setState(EnchantState.GEAR_WALK_GRIND);
                    } else {
                        gearEnchantIndex = 0;
                        setState(findEnchantedGear() >= 0 ? EnchantState.GEAR_EVALUATE : EnchantState.GEAR_WALK_ENCHANT);
                    }
                }
            }
            default -> {
            }
        }
    }

    private void tickGearWalkAnvil() {
        // 铁砧检测：使用前确认铁砧仍存在且有效，损坏/消失则进入自动更换
        if (!isAnvilAt(pos(EnchantPointType.ANVIL))) {
            pathing.stop();
            module.info("§e⚠ 铁砧已损坏或消失，正在自动更换...");
            setState(EnchantState.GEAR_WALK_ANVIL_BOX);
            return;
        }
        BlockPos posAnvil = pos(EnchantPointType.ANVIL);
        if (container.canOpenNow(posAnvil)) {
            pathing.stop();
            guiTick = 0;
            guiPhase = 0;
            gearAnvilPhase = 0;
            setState(EnchantState.GEAR_ANVIL);
        } else {
            pathing.walkToBlock(posAnvil);
        }
    }

    // ── 铁砧检测与自动更换（旧 :2789-2984） ──

    /** 检测坐标是否为有效铁砧（含微损/严重损坏铁砧） */
    private boolean isAnvilAt(BlockPos pos) {
        if (pos == null || mc.level == null) return false;
        Block block = mc.level.getBlockState(pos).getBlock();
        return block == Blocks.ANVIL || block == Blocks.CHIPPED_ANVIL || block == Blocks.DAMAGED_ANVIL;
    }

    /** 是否为铁砧物品（用于铁砧箱取用） */
    private boolean isAnvilItem(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.ANVIL || item == Items.CHIPPED_ANVIL || item == Items.DAMAGED_ANVIL;
    }

    /** 在背包查找铁砧物品槽位，无则 -1 */
    private int findAnvilItemInInventory() {
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            if (isAnvilItem(mc.player.getInventory().getItem(i))) return i;
        }
        return -1;
    }

    private void tickGearWalkAnvilBox() {
        BlockPos posAnvilBox = pos(EnchantPointType.ANVIL_BOX);
        if (container.canOpenNow(posAnvilBox)) {
            pathing.stop();
            guiTick = 0;
            guiPhase = 0;
            setState(EnchantState.GEAR_TAKE_ANVIL);
        } else {
            pathing.walkToBlock(posAnvilBox);
        }
    }

    private void tickGearTakeAnvil() {
        if (guiTick > 0) { guiTick--; return; }
        if (container.inventoryFull()) {
            mc.player.closeContainer();
            module.error("背包已满，无法继续取铁砧！自动停机。");
            disableSelf();
            return;
        }
        BlockPos posAnvilBox = pos(EnchantPointType.ANVIL_BOX);
        if (!container.chestMenuOpen()) {
            if (guiPhase == 0) {
                container.interactBlock(posAnvilBox);
                guiTick = module.settings().guiDelayTick;
                return;
            }
        }
        if (!container.chestMenuOpen()) return;
        ChestMenu handler = (ChestMenu) mc.player.containerMenu;
        int syncId = handler.containerId;

        // 铁砧可堆叠：QUICK_MOVE 会把整组一起拿走。改为「拿起整堆 → 右键放 1 个到背包 → 剩下的放回箱子」，只取 1 个。
        // 铁砧槽位必须在 case 0 记住（跨 phase 复用）：一旦拿起，该槽变空，后续每 tick 重新扫描会找到别的铁砧槽，导致「放回」放错槽而失败
        switch (guiPhase) {
            case 0 -> {
                gearAnvilChestSlot = -1;
                for (int i = 0; i < handler.getRowCount() * 9; i++) {
                    if (isAnvilItem(handler.getSlot(i).getItem())) {
                        gearAnvilChestSlot = i;
                        break;
                    }
                }
                if (gearAnvilChestSlot < 0) {
                    // 没有备用铁砧：暂停，不无限寻路
                    mc.player.closeContainer();
                    module.error("铁砧已损坏，铁砧箱没有备用铁砧，原版装备极品附魔已暂停。");
                    disableSelf();
                    return;
                }
                // 左键拿起整堆铁砧到光标
                mc.gameMode.handleContainerInput(syncId, gearAnvilChestSlot, 0, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 1;
            }
            case 1 -> {
                // 右键放 1 个到背包空格
                int freeSlot = mc.player.getInventory().getFreeSlot();
                if (freeSlot < 0) {
                    // 背包没空位：把光标上的铁砧放回箱子后停机
                    mc.gameMode.handleContainerInput(syncId, gearAnvilChestSlot, 0, ContainerInput.PICKUP, mc.player);
                    mc.player.closeContainer();
                    module.error("背包已满，无法继续取铁砧！自动停机。");
                    disableSelf();
                    return;
                }
                mc.gameMode.handleContainerInput(syncId, container.containerSlotOf(handler, freeSlot), 1, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 2;
            }
            case 2 -> {
                // 左键把剩下的铁砧放回「case 0 拿起的那个空槽」
                mc.gameMode.handleContainerInput(syncId, gearAnvilChestSlot, 0, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 3;
            }
            case 3 -> {
                // 放回后再关箱，避免「发包放回 → 立刻 closeContainer」让服务端还没处理放回、光标铁砧掉进背包
                mc.player.closeContainer();
                guiTick = module.settings().guiDelayTick;
                guiPhase = 0;
                gearAnvilChestSlot = -1;
                setState(EnchantState.GEAR_WALK_ANVIL_POS);
            }
            default -> {
            }
        }
    }

    private void tickGearWalkAnvilPos() {
        // 能摸到铁砧位就直接进入放置，放置前会程序化转对 yaw（铁砧朝向只由 yaw 决定，
        // 与站位无关），不需要走到朝向侧；距离远时才寻路靠近
        BlockPos posAnvil = pos(EnchantPointType.ANVIL);
        double range = mc.player.blockInteractionRange() + 0.5;
        double dist = mc.player.getEyePosition().distanceTo(Vec3.atCenterOf(posAnvil));
        if (dist <= range) {
            pathing.stop();
            setState(EnchantState.GEAR_PLACE_ANVIL);
        } else {
            Direction facing = anvilFacing();
            BlockPos stand = facing != null ? posAnvil.relative(facing) : posAnvil.above();
            pathing.walkTo(stand);
        }
    }

    private void tickGearPlaceAnvil() {
        if (guiTick > 0) { guiTick--; return; }
        BlockPos posAnvil = pos(EnchantPointType.ANVIL);
        // 铁砧已放置成功：清空主手残留后继续原任务，不强制转动玩家视角
        if (isAnvilAt(posAnvil)) {
            // 放置成功后清空主手残留的铁砧（铁砧可堆叠，useItemOn 有时不消耗主手，导致一直拿铁砧）
            if (!mc.player.getMainHandItem().isEmpty() && isAnvilItem(mc.player.getMainHandItem())) {
                mc.player.getMainHandItem().setCount(0);
            }
            // 恢复放置前视角：铁砧朝向已按 yaw 放正，恢复后玩家视角不被打扰
            if (gearAnvil原视角已存) {
                mc.player.setYRot(gearAnvil原Yaw);
                mc.player.setXRot(gearAnvil原Pitch);
                gearAnvil原视角已存 = false;
            }
            module.info("§a✓ 铁砧已更换完成，继续原任务。");
            setState(EnchantState.GEAR_WALK_ANVIL);
            return;
        }
        int anvilSlot = findAnvilItemInInventory();
        if (anvilSlot < 0) {
            module.error("铁砧已损坏，铁砧箱没有备用铁砧，原版装备极品附魔已暂停。");
            disableSelf();
            return;
        }
        container.selectItem(anvilSlot);
        // 首次转向前保存原视角，放置完成后恢复，避免视角被永久转走
        if (!gearAnvil原视角已存) {
            gearAnvil原Yaw = mc.player.getYRot();
            gearAnvil原Pitch = mc.player.getXRot();
            gearAnvil原视角已存 = true;
        }
        // 放置前把玩家水平朝向转到能还原 posAnvilFacing 的方向（铁砧 FACING 由放置时朝向决定，
        // 不转的话 Baritone 到达后朝向随机，铁砧会放歪）
        转向铁砧朝向(posAnvil);
        Vec3 hitVec = new Vec3(posAnvil.getX() + 0.5, posAnvil.getY(), posAnvil.getZ() + 0.5);
        BlockHitResult hitResult = new BlockHitResult(hitVec, Direction.UP, posAnvil.below(), false);
        mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, hitResult);
        guiTick = module.settings().guiDelayTick;
    }

    /** 程序化转向：枚举 4 个水平方向实测 getStateForPlacement，匹配铁砧朝向记录后锁定该 yaw */
    private void 转向铁砧朝向(BlockPos posAnvil) {
        Direction posAnvilFacing = anvilFacing();
        if (posAnvilFacing == null) return;
        ItemStack anvil = mc.player.getMainHandItem();
        if (!(anvil.getItem() instanceof BlockItem blockItem) || !(blockItem.getBlock() instanceof AnvilBlock anvilBlock)) {
            return;
        }
        Vec3 hitVec = new Vec3(posAnvil.getX() + 0.5, posAnvil.getY(), posAnvil.getZ() + 0.5);
        BlockHitResult hit = new BlockHitResult(hitVec, Direction.UP, posAnvil.below(), false);
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            float yaw = facing.toYRot();
            mc.player.setYRot(yaw);
            mc.player.setXRot(0);
            BlockPlaceContext ctx = new BlockPlaceContext(
                new UseOnContext(mc.level, mc.player, InteractionHand.MAIN_HAND, anvil, hit) {});
            BlockState placed = anvilBlock.getStateForPlacement(ctx);
            if (placed != null && placed.getValue(AnvilBlock.FACING) == posAnvilFacing) {
                mc.player.setYRot(yaw);
                mc.player.setXRot(0);
                return;
            }
        }
    }

    /** 铁砧朝向记录（{@code Direction.name()} 落盘）→ Direction；未记录或非法返回 null */
    private Direction anvilFacing() {
        String name = module.pointStore().anvilFacing();
        if (name == null) return null;
        try {
            return Direction.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void tickGearAnvil() {
        if (guiTick > 0) { guiTick--; return; }

        BlockPos posAnvil = pos(EnchantPointType.ANVIL);
        // 合并过程中铁砧损坏检测：立即切换自动更换
        if (!isAnvilAt(posAnvil)) {
            module.info("§e⚠ 铁砧已损坏或消失，正在自动更换...");
            setState(EnchantState.GEAR_WALK_ANVIL_BOX);
            return;
        }

        if (gearAnvilPlan == null || !gearAnvilPlan.hasNext()) {
            // 合并计划执行完：重新读取主装备并最终验收，只有 100% 达标才进成品箱
            gearEquipSlot = findBestGearSlot();
            if (gearEquipSlot < 0) {
                gearFail(TaskErrorReason.GEAR_IDENTITY_INVALID);
                return;
            }
            ItemStack finalGear = mc.player.getInventory().getItem(gearEquipSlot);
            if (TargetMatcher.isComplete(finalGear, gearProfile)) {
                setState(EnchantState.GEAR_WALK_OUTPUT);
            } else {
                // 合并后仍未 100% 达标：回到评估重新决策（继续附魔/合并凑满级），
                // 而不是直接判死——附魔台随机附魔常差 1 级（时运II/效率IV），应继续培养而非失败
                gearAnvilPlan = null;
                gearAnvilPhase = 0;
                setState(EnchantState.GEAR_EVALUATE);
            }
            return;
        }

        boolean menuOpen = container.anvilMenuOpen();
        if (!menuOpen) {
            if (gearAnvilPhase == 0) {
                container.interactBlock(posAnvil);
                gearAnvilPhase = GUI_OPEN_PENDING;
                guiTick = GUI_OPEN_TIMEOUT;
                return;
            }
            if (gearAnvilPhase == GUI_OPEN_PENDING) {
                if (guiTick == 0) {
                    gearAnvilPhase = 0;
                    guiTick = module.settings().guiDelayTick;
                }
                return;
            }
        }
        if (!menuOpen) return;

        AnvilMenu handler = (AnvilMenu) mc.player.containerMenu;
        int syncId = handler.containerId;
        // 高风险点：本行在旧项目 :3034，位于 switch (gearAnvilPhase) 之前、每次进入都调用一次，
        // 消费 AnvilPlan 的游标。移动此行会改变合并步数，禁止改动。
        AnvilStep step = gearAnvilPlan.next();

        switch (gearAnvilPhase) {
            case 0 -> {
                // 左槽：放主装备（对目标贡献最多的装备）
                gearEquipSlot = findBestGearSlot();
                if (gearEquipSlot < 0) {
                    mc.player.closeContainer();
                    gearFail(TaskErrorReason.GEAR_IDENTITY_INVALID);
                    return;
                }
                // 耐久保护：低于最低耐久比例不再合并，进异常
                if (!GearSafetyGuard.isSafe(mc.player.getInventory().getItem(gearEquipSlot))) {
                    mc.player.closeContainer();
                    gearFail(TaskErrorReason.DURABILITY_LOW);
                    return;
                }
                // 记录合并前主装备附魔（详细播报用）
                合并前主装备 = 附魔摘要(mc.player.getInventory().getItem(gearEquipSlot));
                int gearCont = container.containerSlotOf(handler, gearEquipSlot);
                mc.gameMode.handleContainerInput(syncId, gearCont, 0, ContainerInput.PICKUP, mc.player);
                mc.gameMode.handleContainerInput(syncId, 0, 0, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                gearAnvilPhase = 1;
            }
            case 1 -> {
                // 右槽：放材料装备（带目标附魔的另一件装备，装备+装备叠加）
                if (!handler.getSlot(0).getItem().is(gearTargetItem)) return;
                int materialSlot = findMergeGearSlot(gearEquipSlot);
                if (materialSlot < 0) {
                    mc.player.closeContainer();
                    gearFail(TaskErrorReason.CANNOT_PLAN);
                    return;
                }
                // 记录合并前材料附魔（详细播报用）
                合并前材料 = 附魔摘要(mc.player.getInventory().getItem(materialSlot));
                int matCont = container.containerSlotOf(handler, materialSlot);
                mc.gameMode.handleContainerInput(syncId, matCont, 0, ContainerInput.PICKUP, mc.player);
                mc.gameMode.handleContainerInput(syncId, 1, 0, ContainerInput.PICKUP, mc.player);
                guiTick = module.settings().guiDelayTick;
                gearAnvilPhase = 2;
            }
            case 2 -> {
                if (handler.getSlot(1).getItem().isEmpty()) return;
                int cost = AnvilPlanner.readCost(handler);
                step.actualXpCost(cost);
                合并费用 = cost;
                if (AnvilPlanner.isTooExpensive(cost)) {
                    step.tooExpensive(true);
                    mc.player.closeContainer();
                    // 太昂贵：prior work penalty 已到上限，无法继续合并。
                    // 绝不降级入箱（非极品不入成品箱），送砂轮清零重来重新培养，
                    // 配合高密度筛选避免频繁触发太昂贵
                    module.info("§e⚠ 铁砧费用过高 §8▸ 无法继续合并，送砂轮清零重新培养");
                    gearGrindFromAnvil = true;
                    setState(EnchantState.GEAR_WALK_GRIND);
                    return;
                }
                if (mc.player.experienceLevel < cost) {
                    mc.player.closeContainer();
                    // 铁砧经验不足：纯附魔模式下直接停机，挂机循环则去挂机补经验
                    if (module.currentRunMode() == EnchantRunMode.DRAIN) {
                        module.info("§c✗ 停机 §8▸ 纯附魔经验不足铁砧费用 " + highlightNumber(cost + " 级") + "§7，切换挂机循环后重新启动");
                        disableSelf();
                        return;
                    }
                    gearTargetXp = cost;
                    gearReturnState = EnchantState.GEAR_ANVIL;
                    setState(EnchantState.WALK_TO_FARM);
                    return;
                }
                mc.gameMode.handleContainerInput(syncId, 2, 0, ContainerInput.QUICK_MOVE, mc.player);
                guiTick = module.settings().guiDelayTick;
                gearAnvilPhase = 3;
            }
            case 3 -> {
                mc.player.closeContainer();
                guiTick = module.settings().guiDelayTick;
                step.executed(true);
                gearAnvilPlan.advance();
                gearAnvilPhase = 0;
                statAnvil++;
                gearEquipSlot = findBestGearSlot();
                合并播报();
            }
            default -> {
            }
        }
    }

    private void tickGearWalkOutput() {
        // 卸货前验收：沿用评估/铁砧阶段已精确定位的 gearEquipSlot，不得用贡献分重新挑装备，
        // 否则会误选贡献更高但未达标的中间态，造成「判成品→验收不过→回评估→再判成品」每 tick 死循环
        if (gearEquipSlot < 0 || mc.player.getInventory().getItem(gearEquipSlot).isEmpty()) {
            gearEquipSlot = findCompleteGearSlot();
        }
        if (gearEquipSlot < 0) {
            // 定位失效（成品被移动/消耗）：回评估重新决策，不判死
            gearAnvilPlan = null;
            gearAnvilPhase = 0;
            setState(EnchantState.GEAR_EVALUATE);
            return;
        }
        ItemStack finalGear = mc.player.getInventory().getItem(gearEquipSlot);
        // 严格验收：只有全部活动目标满级（COMPLETE）才算极品，未满级一律回评估继续培养
        boolean 验收通过 = TargetMatcher.isComplete(finalGear, gearProfile);
        if (!验收通过) {
            // 卸货前验收不达标：回到评估继续培养，不直接判死
            gearAnvilPlan = null;
            gearAnvilPhase = 0;
            setState(EnchantState.GEAR_EVALUATE);
            return;
        }
        BlockPos posOutput = pos(EnchantPointType.OUTPUT_STORAGE);
        if (container.canOpenNow(posOutput)) {
            pathing.stop();
            guiTick = 0;
            guiPhase = 0;
            卸货重试次数 = 0;
            setState(EnchantState.GEAR_STORE_OUTPUT);
        } else {
            pathing.walkToBlock(posOutput);
        }
    }

    private void tickGearStoreOutput() {
        if (guiTick > 0) { guiTick--; return; }
        BlockPos posOutput = pos(EnchantPointType.OUTPUT_STORAGE);
        if (!container.chestMenuOpen()) {
            if (guiPhase == 0) {
                container.interactBlock(posOutput);
                guiTick = module.settings().guiDelayTick;
                return;
            }
        }
        if (!container.chestMenuOpen()) return;
        ChestMenu handler = (ChestMenu) mc.player.containerMenu;
        int syncId = handler.containerId;
        switch (guiPhase) {
            case 0 -> {
                boolean hasFreeSlot = false;
                for (int i = 0; i < handler.getRowCount() * 9; i++) {
                    if (handler.getSlot(i).getItem().isEmpty()) { hasFreeSlot = true; break; }
                }
                if (!hasFreeSlot) {
                    mc.player.closeContainer();
                    module.error("成品箱空间不足，原版装备极品附魔已暂停。");
                    disableSelf();
                    return;
                }
                if (gearEquipSlot < 0 || mc.player.getInventory().getItem(gearEquipSlot).isEmpty()) {
                    mc.player.closeContainer();
                    setState(EnchantState.GEAR_IDLE);
                    return;
                }
                mc.gameMode.handleContainerInput(syncId, container.containerSlotOf(handler, gearEquipSlot), 0, ContainerInput.QUICK_MOVE, mc.player);
                guiTick = module.settings().guiDelayTick;
                guiPhase = 1;
            }
            case 1 -> {
                // 确认装备确实已离开背包（成功存入成品箱），失败则重试有限次数
                if (gearEquipSlot >= 0 && !mc.player.getInventory().getItem(gearEquipSlot).isEmpty()) {
                    if (++卸货重试次数 < 卸货重试上限) {
                        guiTick = module.settings().guiDelayTick;
                        guiPhase = 0;
                        return;
                    }
                    mc.player.closeContainer();
                    gearFail(TaskErrorReason.REPEATED_FAILURE);
                    return;
                }
                mc.player.closeContainer();
                guiTick = module.settings().guiDelayTick;
                if (gearTask != null) gearTask.markDone();
                statDone++;
                module.info("§a✓ 装备已达成极品目标，存入成品箱 §8▸ " + highlightText(gearProfile.gearName()));
                playSuccessSound();
                gearNext();
                // 达到极品数量立即停机，不再处理剩余装备
                if (statDone >= module.settings().topGearCount) {
                    gearQueue = null;
                    announceGearSummary();
                    disableSelf();
                    return;
                }
                setState(EnchantState.GEAR_IDLE);
            }
            default -> {
            }
        }
    }

    // ── 状态切换（旧 :2029-2059） ──

    private void setState(EnchantState newState) {
        state = newState;
        guiTick = 0;
        guiPhase = 0;
        if (newState == EnchantState.WALK_TO_FARM) hangoutViewRestored = false;
        // 发包附魔循环（附魔→检查→砂轮洗练→再附魔）不逐状态播报，避免每轮刷屏；
        // 进入附魔阶段只播一句「发包附魔中（不打开界面）」，且整次运行只播一次。
        if (newState == EnchantState.ENCHANTING && !发包附魔提示已播) {
            module.info("§7发包附魔中（不打开界面）...");
            发包附魔提示已播 = true;
        }
        // 状态播报：只播有实质动作的工作状态，寻路过渡（WALK_TO_*）不播防刷屏
        // 带去重锁，循环类流程每轮每个工作状态只播一次
        if (isWorkState(newState) && !newState.cn().equals(lastNotifiedState)) {
            module.info("§7正在" + newState.cn() + "...");
            lastNotifiedState = newState.cn();
        } else if (newState == EnchantState.IDLE || newState == EnchantState.GEAR_IDLE) {
            lastNotifiedState = "";
        }
    }

    // ── 任务列表与自检辅助（旧 :1719-1783、:1929-2027） ──

    /**
     * 重建附魔书 / 自定义模式的目标词条列表（旧 {@code rebuildActiveTasks:1720-1738}）。
     *
     * <p><b>用户 2026-09-16 裁定修正</b>：旧项目的 {@code enchantmentGroups} 数组
     * （{@code :1722-1726}）只列了剑、斧、弓、护甲与 8 个原版组，<b>漏掉了「工具与通用附魔属性」组</b>
     * （该组在 {@code :545} 是真实存在的多选控件，箱子里能勾却被这里忽略，表现为「勾了不干活」）。
     * 本项目按用户裁定补上该组，位置与 CUSTOM 页的控件顺序一致（护甲之后、原版组之前），
     * 差异登记见 {@code 45-阶段11-...差异清单.md} 的 D10。</p>
     *
     * <p><b>用户 2026-09-20 裁定修正（词条来源按模式隔离）</b>：旧实现在两种模式下<b>都</b>把
     * CUSTOM 的 5 组 + 自定义附魔目标并进任务列表，而控制台只在 {@code CUSTOM} 模式下才显示
     * 这两处入口。于是「原版附魔书」模式里，页面上 8 组原版词条全是「已选择 0 项」，
     * 模块却因为看不见的 CUSTOM 残留勾选 / 自定义目标照常启动并一直附魔
     * （用户实机：{@code customEnchantTargets=["永生 2","永生 1"]}、{@code enchantSelection={}}）。
     * 现按模式取源：{@code BOOK} 只用 {@link EnchantSettings#BOOK_GROUPS} 的 8 组，
     * {@code CUSTOM} 只用 {@link EnchantSettings#CUSTOM_GROUPS} 的 5 组 + 自定义附魔目标。</p>
     */
    private void rebuildActiveTasks() {
        activeTasks.clear();
        boolean customMode = module.settings().targetMode == EnchantTargetMode.CUSTOM;
        List<List<String>> enchantmentGroups = customMode
            ? List.of(
                EnchantSettings.SWORD_ENCHANTS,
                EnchantSettings.AXE_ENCHANTS,
                EnchantSettings.BOW_ENCHANTS,
                EnchantSettings.ARMOR_ENCHANTS,
                EnchantSettings.OTHER_ENCHANTS)
            : List.of(
                EnchantSettings.VANILLA_ARMOR_ENCHANTS,
                EnchantSettings.VANILLA_MELEE_ENCHANTS,
                EnchantSettings.VANILLA_TOOL_ENCHANTS,
                EnchantSettings.VANILLA_BOW_ENCHANTS,
                EnchantSettings.VANILLA_FISHING_ENCHANTS,
                EnchantSettings.VANILLA_TRIDENT_ENCHANTS,
                EnchantSettings.VANILLA_CROSSBOW_ENCHANTS,
                EnchantSettings.VANILLA_COMMON_ENCHANTS);
        for (List<String> group : enchantmentGroups) {
            for (String entry : group) {
                if (module.settings().isSelected(entry) && !activeTasks.contains(entry)) {
                    activeTasks.add(entry);
                }
            }
        }
        // 自定义附魔目标（输入框手填的词条）只服务 CUSTOM 模式
        if (!customMode) return;
        for (String task : module.settings().customEnchantTargets) {
            String normalized = normalizeEnchantmentText(task);
            if (!normalized.isEmpty() && !activeTasks.contains(normalized)) activeTasks.add(normalized);
        }
    }

    /** 统计若干组里已勾选的词条数（旧 {@code countSelectedTasks:1740-1748}） */
    private int countSelectedTasks(List<List<String>> groups) {
        int count = 0;
        for (List<String> group : groups) {
            for (String entry : group) {
                if (module.settings().isSelected(entry)) count++;
            }
        }
        return count;
    }

    private boolean isBlockAt(BlockPos pos, Block block) {
        return pos != null && mc.level != null && mc.level.getBlockState(pos).is(block);
    }

    private boolean needRestock() {
        return needBookRestock() || needLapisRestock();
    }

    private boolean needBookRestock() {
        return container.countInInventory(Items.BOOK) < module.settings().bookSupplyGroups * 64 * 0.2;
    }

    private boolean needLapisRestock() {
        return container.countInInventory(Items.LAPIS_LAZULI) < module.settings().lapisSupplyGroups * 64 * 0.2;
    }

    private boolean arrivedAtHangout() {
        BlockPos posHangout = pos(EnchantPointType.AFK);
        return posHangout != null && mc.player.blockPosition().equals(posHangout);
    }

    private void restoreHangoutView() {
        if (hangoutViewRestored || !module.settings().restoreHangoutView) return;
        Float yaw = module.pointStore().hangoutYaw();
        Float pitch = module.pointStore().hangoutPitch();
        if (yaw == null || pitch == null) return;
        mc.player.setYRot(yaw);
        mc.player.setYHeadRot(yaw);
        mc.player.setXRot(pitch);
        hangoutViewRestored = true;
    }

    private void playSuccessSound() {
        if (!module.settings().successSoundEnabled || mc.player == null) return;
        SoundEvent sound = switch (module.settings().successSoundType) {
            case CHALLENGE_COMPLETE -> SoundEvents.UI_TOAST_CHALLENGE_COMPLETE;
            case LEVEL_UP -> SoundEvents.PLAYER_LEVELUP;
            case ENCHANTMENT_TABLE -> SoundEvents.ENCHANTMENT_TABLE_USE;
            case NOTE_PLING -> SoundEvents.NOTE_BLOCK_PLING.value();
            case BELL -> SoundEvents.BELL_BLOCK;
            case FIREWORK -> SoundEvents.FIREWORK_ROCKET_BLAST;
            case EXPERIENCE -> SoundEvents.EXPERIENCE_ORB_PICKUP;
            case VILLAGER -> SoundEvents.VILLAGER_CELEBRATE;
            case TRIDENT_THUNDER -> SoundEvents.TRIDENT_THUNDER.value();
            case ATTACK_CRIT -> SoundEvents.PLAYER_ATTACK_CRIT;
            case CAT -> SoundEvents.CAT_AMBIENT_BABY.value();
            case THUNDER -> SoundEvents.LIGHTNING_BOLT_THUNDER;
        };
        mc.player.playSound(sound, 1.0f, 1.0f);
    }

    // ── 挂机刷经验三件套（旧 :1988-2019，判据逐字） ──

    /** 开杀戮光环：先换横扫之刃剑，再开（只开我们开的那个） */
    private void startKillAura() {
        equipSweepingSword();
        if (ModuleManager.isEnabled(KillAuraModule.MODULE_ID)) return;
        if (ModuleManager.setEnabled(KillAuraModule.MODULE_ID, true)) killAuraEnabledByUs = true;
    }

    /** 把带「横扫之刃」的剑换到当前选中槽（快捷栏直接选槽，主背包用 SWAP 换入） */
    private void equipSweepingSword() {
        int swordSlot = findSweepingSword();
        if (swordSlot < 0) return;
        container.selectItem(swordSlot);
    }

    /**
     * 找横扫之刃剑：tooltip 逐行 {@code contains("横扫之刃")} 且物品属于剑标签
     * （旧 {@code findSweepingSword:2006-2014}，判据逐字保留，语言环境为中文）。
     */
    private int findSweepingSword() {
        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.is(ItemTags.SWORDS)) {
                List<Component> tooltip = stack.getTooltipLines(Item.TooltipContext.of(mc.level), mc.player, TooltipFlag.NORMAL);
                for (Component line : tooltip) if (line.getString().contains("横扫之刃")) return i;
            }
        }
        return -1;
    }

    /** 关杀戮光环：只关我们自己开的那一个（用户运行前就开着的保持原样） */
    private void stopKillAura() {
        if (killAuraEnabledByUs && ModuleManager.isEnabled(KillAuraModule.MODULE_ID)) {
            ModuleManager.setEnabled(KillAuraModule.MODULE_ID, false);
        }
        killAuraEnabledByUs = false;
    }

    // ── 点位读取与配色辅助 ──

    /** 点位坐标（旧项目的 14 个落盘字段在本项目统一由点位存储承载） */
    private BlockPos pos(EnchantPointType type) {
        EnchantPoint point = module.pointStore().get(type);
        return point == null ? null : new BlockPos(point.x(), point.y(), point.z());
    }

    /** 物品/文本高亮（亮绿色粗体）—— 旧基类 {@code highlightText}，逐字 */
    private static String highlightText(String text) {
        return "§a§l" + text + "§r§f§l";
    }

    /** 功能/模式高亮（亮青色粗体）—— 旧基类 {@code highlightFunction}，逐字 */
    private static String highlightFunction(String text) {
        return "§b§l" + text + "§r§f§l";
    }

    /** 数值/阈值高亮（黄色粗体）—— 旧基类 {@code highlightNumber}，逐字 */
    private static String highlightNumber(String text) {
        return "§e§l" + text + "§r§f§l";
    }
}
