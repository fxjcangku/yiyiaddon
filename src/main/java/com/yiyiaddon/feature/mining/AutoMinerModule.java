package com.yiyiaddon.feature.mining;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.combat.KillAuraRepairHook;
import com.yiyiaddon.feature.mining.command.WkCommand;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.fsm.MiningStateMachine;
import com.yiyiaddon.feature.mining.model.LootMode;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.navigation.MiningPathing;
import com.yiyiaddon.feature.mining.notification.SoundNotifier;
import com.yiyiaddon.feature.mining.render.MiningPointRenderer;
import com.yiyiaddon.feature.mining.repository.MiningPointStore;
import com.yiyiaddon.feature.mining.service.MiningBindingService;
import com.yiyiaddon.feature.mining.service.MiningContainer;
import com.yiyiaddon.feature.mining.service.ServerCommandRunner;
import com.yiyiaddon.feature.mining.ui.AutoMinerPage;
import com.yiyiaddon.integration.baritone.BaritoneChatTranslations;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自动挖矿模块（旧项目 {@code mining/AutoMinerModule.java} 的移植），本批次只落地骨架与数据。
 *
 * <p><b>用户交互资产：</b>模块中文名 {@code 自动挖矿}、description、全部设置名与描述、自检缺项
 * 文案均沿用旧项目原文，禁止改写。</p>
 *
 * <p><b>本批次范围</b>：设置载体（{@link MiningSettings}）、点位存储（{@link MiningPointStore}）、
 * 三点位 ESP（{@link MiningPointRenderer}）、启动自检、生命周期与设置持久化。</p>
 *
 * <p><b>已接入（批次 3）</b>：识别层与判据（{@link #isTargetFamily} / {@link #getTargetBlock} /
 * {@link #getTargetBlocks} / {@link #getTargetBlockIds} / {@link #getTargetDropItemId} /
 * {@link #isSilkTouchMode} / {@link #isShulkerPackerEnabled} / {@link #isInNether}）、
 * 全部子组件访问器与四个逻辑层组件（{@link ServerCommandRunner}（旧 {@code CommandManager}）、
 * {@link MiningPathing}（旧 {@code BaritoneExecutor}）、{@link MiningContainer}（旧 {@code ContainerHelper}）、
 * {@link SoundNotifier}），以及 9 态状态机 {@link MiningStateMachine}（旧 {@code MinerFSM}）——
 * 状态机已挂到 {@link #onEnable()} / {@link #onDisable()} / {@link #onTick} 上。</p>
 *
 * <p><b>已接入（批次 4）</b>：修补联动战斗真实现
 * {@link com.yiyiaddon.feature.combat.KillAuraRepairHook}（构造时注入状态机，进入修补开杀戮光环、
 * 离开修补只关我们自己开的那一个）。</p>
 *
 * <p><b>未做（留待后续批次）</b>：秒破发包、种子模式（OrePredictor，随之一并留白的还有
 * {@code .wk 检测假矿} 子命令与按钮、帮助页该行）、岩浆透视渲染（下界自动开启岩浆透视随之一并留白）。</p>
 *
 * <p><b>已接入（批次 5A）</b>：配置页面 {@code feature/mining/ui/AutoMinerPage}
 * （帮助页 + 三行点位卡片 + 5 个设置分组）与点位绑定 / 移除的唯一实现
 * {@link MiningBindingService}（配置页与 {@code .wk} 指令共用同一处校验与播报）。</p>
 *
 * <p><b>已接入（批次 5B）</b>：{@code .wk} 指令
 * （{@link com.yiyiaddon.feature.mining.command.WkCommand}，随 {@link #commands()} 注册）与
 * 启动报告 {@link #reportStartupInfo()}（旧 {@code onActivate} 的最后一步）。</p>
 *
 * <p><b>自检条数</b>：旧项目 16 条，本批次落地 15 条；第 16 条（{@code §e种子挖矿§f·种子格式错误
 * （需Long数字，或留空）}）随种子模式留白。</p>
 */
public final class AutoMinerModule extends Module {

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀与世界渲染层标识 */
    public static final String MODULE_ID = "mining";

    /** 播报前缀使用的模块名：旧项目 {@code AutoMinerModule} 的模块名为 {@code 自动挖矿} */
    public static final String MESSAGE_MODULE = "自动挖矿";

    /** 图标字形（Material Symbols，项目已验证可显示；用户 2026-09-16 拍板沿用该字形） */
    private static final String ICON = "\uEA79";

    /** 主背包 + 快捷栏格数（{@code Inventory#getItem} 的存储下标上界） */
    private static final int INVENTORY_STORAGE_SLOTS = 36;

    // ─── 时运产物映射（掉落物 → 矿石方块，旧项目 :113-130 逐字） ───
    // 主世界矿石：时运挖矿的掉落物 → 对应的矿石方块（铁/金/铜挖出粗矿，不是锭）
    private static final Map<String, String> OVERWORLD_FORTUNE = Map.ofEntries(
        Map.entry("minecraft:raw_iron", "minecraft:iron_ore"),
        Map.entry("minecraft:raw_gold", "minecraft:gold_ore"),
        Map.entry("minecraft:raw_copper", "minecraft:copper_ore"),
        Map.entry("minecraft:redstone", "minecraft:redstone_ore"),
        Map.entry("minecraft:lapis_lazuli", "minecraft:lapis_ore"),
        Map.entry("minecraft:diamond", "minecraft:diamond_ore"),
        Map.entry("minecraft:emerald", "minecraft:emerald_ore"),
        Map.entry("minecraft:coal", "minecraft:coal_ore")
    );
    // 下界矿石：时运挖矿的掉落物 → 对应的矿石方块（残骸掉残骸本身，下界金矿掉金粒，石英矿掉石英）
    private static final Map<String, String> NETHER_FORTUNE = Map.ofEntries(
        Map.entry("minecraft:ancient_debris", "minecraft:ancient_debris"),
        Map.entry("minecraft:gold_nugget", "minecraft:nether_gold_ore"),
        Map.entry("minecraft:quartz", "minecraft:nether_quartz_ore")
    );

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体（文案与默认值来自旧项目） */
    private final MiningSettings settings = new MiningSettings();

    /** 三点点位存储（按服务器隔离，切换服务器重载） */
    private final MiningPointStore pointStore = new MiningPointStore();

    private final MiningPointRenderer renderer;

    /** 指令执行与防卡死（旧 {@code CommandManager}） */
    private final ServerCommandRunner cmdManager;

    /** Baritone 寻路封装（旧 {@code BaritoneExecutor}） */
    private final MiningPathing baritone;

    /** 容器交互（旧 {@code ContainerHelper}） */
    private final MiningContainer container;

    /** 音效通知（旧 {@code SoundNotifier}，无设置项） */
    private final SoundNotifier soundNotifier;

    /** 点位绑定 / 移除的唯一实现（配置页与 {@code .wk} 指令共用） */
    private final MiningBindingService bindingService;

    /** 9 态挖矿状态机（旧 {@code MinerFSM}） */
    private final MiningStateMachine fsm = new MiningStateMachine(this);

    /** 颜色设置的取色载体：调色板直接改这三个对象，随后同步回设置项 */
    private final EspColor mineralColor = new EspColor();
    private final EspColor foodColor = new EspColor();
    private final EspColor afkColor = new EspColor();

    /** 最近一次装载点位时所处的世界上下文（{@code server@dimension}） */
    private String storeContext;

    public AutoMinerModule() {
        super(MODULE_ID, MESSAGE_MODULE, "automation",
                "Baritone驱动全自动挖矿，物流循环，耐久修补，死亡自愈。点击按钮查看说明。");

        this.renderer = new MiningPointRenderer(this);
        this.cmdManager = new ServerCommandRunner(this);
        this.baritone = new MiningPathing(this);
        this.container = new MiningContainer(this);
        this.soundNotifier = new SoundNotifier();
        this.bindingService = new MiningBindingService(this);
        // 语音播报默认启用，音量1.0（旧项目 :676-678 写死，无设置项）
        soundNotifier.setEnabled(true);
        soundNotifier.setVolume(1.0f);
        // 修补联动战斗真实现（批次 4）：进入修补时开杀戮光环、离开时只关我们自己开的那一个
        fsm.setRepairCombat(new KillAuraRepairHook());
        syncColorsFromSettings();
    }

    @Override
    public String name() {
        return "AutoMiner";
    }

    @Override
    public String icon() {
        return ICON;
    }

    @Override
    public int order() {
        return 30;
    }

    // ── 设置持久化 ──

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
        syncColorsFromSettings();
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
    }

    /** 立即写回设置（界面改动即时生效，与旧项目设置自动保存一致） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    // ── 界面与指令 ──

    @Override
    public ModulePage page() {
        return new AutoMinerPage(this);
    }

    /** 点位绑定 / 移除的唯一实现（配置页与 {@code .wk} 指令共用同一处） */
    public MiningBindingService bindingService() {
        return bindingService;
    }

    @Override
    public List<ClientCommand> commands() {
        return List.of(new WkCommand());
    }

    // ── 生命周期 ──

    /**
     * 启用前自检：收集全部缺项，交给运行时一次性多行播报。
     *
     * <p>收集全部而不是遇到第一个就返回，用户一次就能看到还差什么，配好一项下次启动就少一条。
     * 未进入世界时不做自检（旧项目此时直接提示「必须在进入世界后才能启动模块。」并关闭模块，
     * 该判断放在 {@link #onEnable()}，避免把世界状态混进自检缺项里）。</p>
     */
    @Override
    public List<String> selfCheck() {
        if (mc.player == null || mc.level == null || mc.gameMode == null) return List.of();

        // 自检依赖当前服务器的点位：换服 / 换存档时才重新读盘，同一上下文内复用内存快照
        refreshStoreIfContextChanged();

        List<String> missing = new ArrayList<>();

        // 目标选择：主世界矿石 / 下界矿石 / 普通方块三项只能选 1 个
        int selectedCount = 0;
        if (!settings.overworldOreTarget.isBlank()) selectedCount++;
        if (!settings.netherOreTarget.isBlank()) selectedCount++;
        if (!settings.blockTarget.isBlank()) selectedCount++;
        if (selectedCount == 0) {
            missing.add("§e目标§f·未选择");
        } else if (selectedCount > 1) {
            missing.add("§e目标§f·选了" + selectedCount + "个（只能选1个）");
        }

        // 选中的目标必须能解析成真实方块：配错 ID / 旧档残留 / 卸载竞品模组后的残留 ID
        // 会让状态机拿不到任何目标，在 MINING↔GO_WILD 之间无限空转（不停 RTP）
        if (selectedCount == 1 && getTargetBlocks().isEmpty()) {
            missing.add("§e目标§f·无法解析成方块（请重新选择）");
        }

        // 点位绑定检测（旧 :938-943 的三条缺项）
        addMissingPoint(missing, MiningPointType.MINERAL, "§6矿物箱§f·未绑定");
        addMissingPoint(missing, MiningPointType.FOOD, "§2食物箱§f·未绑定");
        addMissingPoint(missing, MiningPointType.AFK, "§d挂机点§f·未绑定");

        // 指令配置检测
        if (settings.wildCommand.trim().isEmpty()) missing.add("§b前往挖矿指令§f·未填写");
        if (settings.unloadCommand.trim().isEmpty()) missing.add("§b返回卸货指令§f·未填写");
        if (settings.supplyCommand.trim().isEmpty()) missing.add("§b前往补给指令§f·未填写");
        if (settings.afkCommand.trim().isEmpty()) missing.add("§b前往修复指令§f·未填写");
        if (settings.respawnCommand.trim().isEmpty()) missing.add("§b死亡返回指令§f·未填写");

        // 装备检测（含副手）：镐子 / 武器 / 食物数量，以及采集模式与镐子附魔是否匹配
        boolean hasPickaxe = false;
        boolean hasWeapon = false;
        boolean hasSilkPickaxe = false;    // 有精准采集附魔的镐
        boolean hasFortunePickaxe = false; // 有时运附魔的镐（任意等级）
        boolean hasPlainPickaxe = false;   // 无时运/精准采集的普通镐
        int foodCount = 0;

        for (int i = 0; i < INVENTORY_STORAGE_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;
            String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
            if (isPickaxe(stack)) {
                hasPickaxe = true;
                if (hasEnchant(stack, Enchantments.SILK_TOUCH)) hasSilkPickaxe = true;
                else if (hasEnchant(stack, Enchantments.FORTUNE)) hasFortunePickaxe = true;
                else hasPlainPickaxe = true;
            }
            if (itemId.endsWith("_sword")) hasWeapon = true;
            if (settings.foodWhitelist.contains(itemId) && stack.has(DataComponents.FOOD)) foodCount += stack.getCount();
        }

        ItemStack offhand = mc.player.getOffhandItem();
        if (!offhand.isEmpty()) {
            String offhandId = BuiltInRegistries.ITEM.getKey(offhand.getItem()).toString();
            if (isPickaxe(offhand)) {
                hasPickaxe = true;
                if (hasEnchant(offhand, Enchantments.SILK_TOUCH)) hasSilkPickaxe = true;
                else if (hasEnchant(offhand, Enchantments.FORTUNE)) hasFortunePickaxe = true;
                else hasPlainPickaxe = true;
            }
            if (offhandId.endsWith("_sword")) hasWeapon = true;
            if (settings.foodWhitelist.contains(offhandId) && offhand.has(DataComponents.FOOD)) {
                foodCount += offhand.getCount();
            }
        }

        if (!hasPickaxe) missing.add("§7镐子§f·背包里没有");
        if (!hasWeapon) missing.add("§7武器§f·背包里没有");
        if (foodCount < settings.hungerThreshold) {
            missing.add("§7食物§f·白名单只有" + foodCount + "个（低于阈值" + settings.hungerThreshold + "）");
        }

        // 采集模式匹配检测：模式与镐子不符则阻断启动（普通无附魔镐按掉落物处理，视作时运兼容）
        if (hasPickaxe) {
            if (settings.lootMode == LootMode.SILK_TOUCH) {
                if (!hasSilkPickaxe) {
                    missing.add("§d精准采集镐§f·背包里没有（当前是精准采集模式，请换成精准采集镐）");
                }
            } else if (!hasFortunePickaxe && !hasPlainPickaxe) {
                missing.add("§d时运镐§f·背包里没有（当前是时运模式，请换成时运镐或普通镐）");
            }
        }

        return missing;
    }

    /** 记一条「没绑点位」的缺项：文案一字不改，未绑才加（旧 {@code :938-943}） */
    private void addMissingPoint(List<String> missing, MiningPointType type, String text) {
        if (pointStore.has(type)) return;
        missing.add(text);
    }

    @Override
    protected void onEnable() {
        // 世界就绪判断：自检会访问点位，先确保已进入世界
        if (mc.player == null || mc.level == null || mc.gameMode == null) {
            notifyError("必须在进入世界后才能启动模块。");
            mc.execute(() -> ModuleManager.setEnabled(MODULE_ID, false));
            return;
        }

        // 点位重读（旧 onActivate 第一步 :717-718）：自检读的也是这份点位，必须最先装载
        reloadStore();

        // 按当前设置下发 Baritone 调优（旧 onActivate :727-751，23 个入参顺序逐条一致）
        baritone.applySettings(
            settings.avoidLava, settings.mobAvoidance, settings.mobAvoidanceRadius,
            settings.allowBreak, settings.allowPlace, settings.maxFallHeight,
            settings.pauseMiningForFallingBlocks,
            settings.allowInventory, settings.autoTool, settings.sprintAscends,
            settings.allowParkour, settings.allowParkourPlace,
            settings.allowDiagonalAscend, settings.allowDiagonalDescend,
            settings.allowOnlyExposedOres,
            settings.allowOnlyExposedOresDistance, settings.minYLevelWhileMining,
            settings.maxYLevelWhileMining, settings.mineMaxOreLocationsCount,
            settings.blacklistClosestOnFailure, settings.legitMine,
            settings.legitMineYLevel, settings.legitMineIncludeDiagonals);

        // 各组件复位（旧 onActivate :753-756 在 applySettings 之后）
        fsm.reset();
        container.reset();
        cmdManager.reset();

        // ESP：注册世界渲染层，关闭时注销
        WorldOverlay.register(MODULE_ID, renderer::render);

        // 启动报告：旧 onActivate 的最后一步（旧 :758），让用户一眼确认这次跑的是什么配置
        reportStartupInfo();
    }

    @Override
    protected void onDisable() {
        // 顺序照旧 onDeactivate :895-903（去掉种子缓存失效那一句）
        baritone.stop();
        container.closeContainer();
        // shutdown 而不是 reset：reset 不经过状态退场，在修补 / 进食 / 物流态关模块会漏掉
        // 「停杀戮光环 + 还原快捷栏 + 松开右键 + 还原 Baritone allowBreak」
        fsm.shutdown();
        container.reset();
        cmdManager.reset();
        WorldOverlay.unregister(MODULE_ID);
    }

    // ── 事件 ──

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK, ClientEventType.SCREEN_OPEN, ClientEventType.DISCONNECT);
    }

    /**
     * 事件分派（旧项目的两个 {@code @EventHandler}）。
     *
     * <ul>
     *   <li>{@code DISCONNECT} ← 旧 {@code onGameLeft :1167-1170}：退出世界即自动关模块，
     *       剩下的清理由 {@code onDisable} 走完。先作废点位视图与装载上下文，避免关模块过程中
     *       再按上一个服务器的数据落盘。</li>
     *   <li>{@code SCREEN_OPEN} ← 旧 {@code onOpenScreen :1172-1182}：静默容器。</li>
     * </ul>
     */
    @Override
    public void onEvent(ClientEvent event) {
        if (event == null) return;
        switch (event.type()) {
            case DISCONNECT -> {
                pointStore.invalidate();
                storeContext = null;
                // 旧写法 isActive() → toggle()，即「在开才关」；本项目同样只在开着时关
                if (isEnabled()) ModuleManager.setEnabled(MODULE_ID, false);
            }
            case SCREEN_OPEN -> onOpenScreen(event);
            default -> {
            }
        }
    }

    /**
     * 静默容器：挖矿运行中取消矿物箱 / 食物箱界面的显示（不抢鼠标）。
     *
     * <p>箱子数据仍由 {@code mc.player.containerMenu} 同步，卸货 / 补给照常发包，
     * 只是不弹界面（旧 :1175-1177 的原注释）。逐条照旧 :1172-1182：</p>
     * <ul>
     *   <li>玩家未进世界直接返回；</li>
     *   <li>模块没开不拦（旧 {@code isActive()}）；</li>
     *   <li><b>背包放行</b>——玩家按 E 必须能开背包，不能被模块吞掉；</li>
     *   <li>其余容器界面一律取消（{@code EventDispatcher} 收到 cancel 后放弃该界面）。</li>
     * </ul>
     */
    private void onOpenScreen(ClientEvent event) {
        if (mc.player == null || !isEnabled()) return;
        String screenClassName = event.payload();
        // 旧判定 `!(screen instanceof InventoryScreen)`
        if (InventoryScreen.class.getName().equals(screenClassName)) return;
        if (isContainerScreen(screenClassName)) event.cancel();
    }

    /**
     * 该界面类名是否为原版容器界面 —— 旧 {@code event.screen instanceof AbstractContainerScreen<?>}
     * 的类名等价物（事件载荷只带类名，不带界面对象；与 {@code StardewFarmModule} 同一做法）。
     */
    private static boolean isContainerScreen(String screenClassName) {
        if (screenClassName == null || screenClassName.isBlank()) return false;
        try {
            return AbstractContainerScreen.class.isAssignableFrom(Class.forName(screenClassName));
        } catch (Throwable ignored) {
            return false;
        }
    }

    /** 每刻推进：垃圾丢弃分频 + 状态机（旧 onTick :1076-1088，去掉种子扫描队列那一句） */
    @Override
    public void onTick(Minecraft client) {
        if (client.player == null || client.level == null) return;
        container.tickTrashDisposal(settings.keepWhitelist, settings.placeBlocks);
        fsm.tick();
    }

    // ── 播报（配色语义与旧基类 YiyiaddonModule 一致） ──

    /** 普通信息 */
    public void info(String message) {
        ClientChat.send(MESSAGE_MODULE, message);
    }

    /** 警告：黄色加粗 */
    public void warning(String message) {
        ClientChat.send(MESSAGE_MODULE, "§e§l" + message);
    }

    /** 错误：橙色加粗 */
    public void error(String message) {
        ClientChat.send(MESSAGE_MODULE, "§6§l" + message);
    }

    private void notifyError(String message) {
        error(message);
    }

    // ── 启动报告（旧 reportStartupInfo :772-836 逐字） ──

    /**
     * 启动报告：整份报告合并成一条多行消息输出，只带一次模块前缀。
     *
     * <p>只报会影响本次结果的关键项（维度、目标矿、采集模式、挖矿模式、扫描方式、三个阈值）与两条
     * 风险提醒，不把整个设置面板念一遍，否则聊天栏刷屏反而看不清。正文统一「标签 + 全角空格 +
     * {@code §8▸ } + 值」结构，行首标签宽度一致。</p>
     *
     * <p><b>本轮与旧项目的唯一差异</b>：旧项目「扫描方式」与「挖矿模式」按种子模式（{@code OrePredictor}）
     * 二选一，本轮种子模式留白，故 {@code §7挖矿模式} 固定为 {@code 普通模式}、
     * {@code §7扫描方式} 只保留 {@code §f视野内所有目标矿} 这条（种子模式那条连同 {@code 渲染范围}
     * 不写）。其余 7 行与末尾的条件行逐字照抄。</p>
     *
     * <p>取值口径逐条照旧源码：目标矿物取「已选产物的显示名」（矿石取物品悬停名，
     * 时运模式即粗铁/粗金这类掉落物名，普通方块走 {@link BaritoneChatTranslations#translateBlockId}，
     * 都没选时为 {@code 未选择}）；维度取当前世界的维度中文名（未进入世界为 {@code 未知}）。</p>
     */
    private void reportStartupInfo() {
        StringBuilder report = new StringBuilder();
        report.append("§a§l✓ 自动挖矿 · 启动报告");
        report.append("\n§7当前维度　§8▸ ").append(highlightText(startupDimensionName())).append("§r");
        report.append("\n§7目标矿物　§8▸ ").append(highlightText(startupTargetName())).append("§r");
        report.append("\n§7采集模式　§8▸ ")
              .append(highlightText(isSilkTouchMode() ? "精准采集" : "时运")).append("§r");
        report.append("\n§7挖矿模式　§8▸ ").append(highlightText("普通模式")).append("§r");
        report.append("\n§7扫描方式　§8▸ §f视野内所有目标矿");

        // 触发阈值：三项合并一行，高亮数值
        report.append("\n§7触发阈值　§8▸ §f满载 ").append(highlightText(settings.unloadThreshold + " 组")).append("§r")
              .append("§f · 饥饿 ").append(highlightText(String.valueOf(settings.hungerThreshold))).append("§r")
              .append("§f · 耐久 ").append(highlightText(String.valueOf(settings.durabilityThreshold))).append("§r");

        // 丢弃规则提醒（默认全丢，防止玩家误丢重要物品）
        report.append("\n§c⚠ 丢弃规则：除保留项外全部自动丢弃！想留下的物品请先加进「保留白名单」");

        // 经验修补软提示（不阻断）：挂机修复依赖经验修补，没有则耐久低了修不了
        if (!hasMendingPickaxe()) {
            report.append("\n§e⚠ 镐子无经验修补附魔：耐久低时将无法自动修复，建议换有经验修补的镐子");
        }

        info(report.toString());
    }

    /**
     * 高亮包裹（亮绿加粗），逐字等于旧基类 {@code YiyiaddonModule.highlightText :167-170}。
     *
     * <p>本项目的 {@link CommandMessageFormatter} 只有「整字段上色」的 {@code highlight(标签, 值)}，
     * 没有「夹在行内任意位置的高亮包裹」，故此处用与旧基类相同的色码包裹。</p>
     */
    private static String highlightText(String text) {
        return "§a§l" + text + "§r§f§l";
    }

    /**
     * 启动报告的「目标矿物」取值（旧 {@code getTargetDisplayName :843-858}）。
     *
     * <p>主世界 / 下界产物取物品悬停名（时运=粗铁这类掉落物名，精准=原矿名），普通方块取方块中文名，
     * 都没选时为 {@code 未选择}。本项目设置项存登记 ID，故先还原成物品 / 方块再走同一口径；
     * 不用 {@code getTargetBlock()} 的方块名代替：那会把「时运模式的掉落物名」显示成原矿名。</p>
     */
    private String startupTargetName() {
        Item overworld = itemOf(settings.overworldOreTarget);
        if (overworld != null) return new ItemStack(overworld).getHoverName().getString();
        Item nether = itemOf(settings.netherOreTarget);
        if (nether != null) return new ItemStack(nether).getHoverName().getString();
        Block block = blockOf(settings.blockTarget);
        if (block != Blocks.AIR) {
            return BaritoneChatTranslations.translateBlockId(BuiltInRegistries.BLOCK.getKey(block).toString());
        }
        return "未选择";
    }

    /**
     * 启动报告的「当前维度」取值（旧 {@code getDimensionName :863-871}）。
     *
     * <p>未进入世界为 {@code 未知}；三大原版维度用 {@link WorldContextFormatter} 的中文名
     * （该名称与旧源码的包含判断结果一致，且自定义维度同报 {@code 自定义维度}）。</p>
     */
    private static String startupDimensionName() {
        String id = WorldIdentity.dimension();
        return id.isEmpty() ? "未知" : WorldContextFormatter.dimensionDisplayName(id);
    }

    /**
     * 背包（含副手）里是否有带经验修补附魔的镐子（旧 {@code hasMendingPickaxe :945-959}）。
     *
     * <p>只为末尾那条软提示服务：没有经验修补镐时挂机修复修不了耐久。</p>
     */
    private boolean hasMendingPickaxe() {
        if (mc.player == null) return false;
        for (int i = 0; i < INVENTORY_STORAGE_SLOTS; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (isPickaxe(stack) && hasEnchant(stack, Enchantments.MENDING)) return true;
        }
        ItemStack offhand = mc.player.getOffhandItem();
        return isPickaxe(offhand) && hasEnchant(offhand, Enchantments.MENDING);
    }

    /**
     * 镐子识别口径，与 {@code MiningStateMachine#isPickaxe} 保持一致（物品路径以 {@code _pickaxe} 结尾）。
     *
     * <p>自检原用 {@code itemId.contains("pickaxe")}，与状态机 / 卸货逻辑的 {@code endsWith("_pickaxe")}
     * 不同源：自检放行、模块一启动就报「缺少镐子」停机。这里统一到后者。</p>
     */
    private static boolean isPickaxe(ItemStack stack) {
        return !stack.isEmpty() && BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().endsWith("_pickaxe");
    }

    // ── 供渲染器 / 后续批次读取 ──

    public MiningSettings settings() {
        return settings;
    }

    public MiningPointStore pointStore() {
        return pointStore;
    }

    public EspColor mineralColor() {
        return mineralColor;
    }

    public EspColor foodColor() {
        return foodColor;
    }

    public EspColor afkColor() {
        return afkColor;
    }

    // ── 识别层与判据（旧项目 :1188-1359，语义逐条一致） ──

    /**
     * 目标矿石方块：主世界矿石 → 下界矿石 → 普通方块，三选一互斥。
     *
     * <p>对应旧项目 {@code AutoMinerModule.getTargetBlock()}（{@code :1188-1203}）。
     * 旧项目三项各存 {@code Item}/{@code Block}，本项目 {@link MiningSettings} 存登记 ID 字符串，
     * 因此这里先把 ID 还原成方块再走同一套优先级。</p>
     */
    public Block getTargetBlock() {
        if (!settings.overworldOreTarget.isBlank()) {
            return blockForTarget(settings.overworldOreTarget, false);
        }
        if (!settings.netherOreTarget.isBlank()) {
            return blockForTarget(settings.netherOreTarget, true);
        }
        if (!settings.blockTarget.isBlank()) {
            Block block = blockOf(settings.blockTarget);
            if (block != Blocks.AIR) return block;
        }
        return Blocks.AIR;
    }

    /** 是否精准采集模式（按原矿方块判定，否则按时运掉落物判定） */
    public boolean isSilkTouchMode() {
        return settings.lootMode == LootMode.SILK_TOUCH;
    }

    /** 是否开启潜影盒打包机模式（容器放满后等红石换盒重开，直到背包目标矿放完才 RTP） */
    public boolean isShulkerPackerEnabled() {
        return settings.shulkerPacker;
    }

    /**
     * 当前是否在下界维度（下界挖矿自动开岩浆透视用，旧项目 {@code :881-883}）。
     *
     * <p>旧实现取 {@code level.dimension().toString()} 再做 {@code contains("the_nether")}，
     * 拿到的是 {@code ResourceKey[minecraft:dimension / minecraft:the_nether]} 这种包装串，
     * 任何维度只要标识里出现该子串都会被误判。这里改用
     * {@link WorldIdentity#dimension()} 的稳定标识做等值比较。</p>
     */
    public boolean isInNether() {
        return "minecraft:the_nether".equals(WorldIdentity.dimension());
    }

    /** 岩浆透视是否已开启（下界自动开启前的判据） */
    public boolean isLavaEspEnabled() {
        return settings.lavaEsp;
    }

    /**
     * 开启岩浆透视（旧项目 {@code enableLavaEsp}：设置项置真即自动落盘）。
     *
     * <p>由状态机在「进入挖矿且在下界」时调用一次，播报由状态机负责（旧 {@code MinerFSM :234-238}）。</p>
     */
    public void enableLavaEsp() {
        if (settings.lavaEsp) return;
        settings.lavaEsp = true;
        persistSettings();
    }

    /** 目标矿石家族是否包含该方块（含深层/浅层变种，下界矿与普通方块无变种） */
    public boolean isTargetFamily(Block block) {
        Block anchor = getTargetBlock();
        if (anchor == null || anchor == Blocks.AIR || block == null) return false;
        if (block == anchor) return true;
        String anchorPath = BuiltInRegistries.BLOCK.getKey(anchor).getPath();
        String blockPath = BuiltInRegistries.BLOCK.getKey(block).getPath();
        // 仅主世界矿石存在 deepslate_ 变种互换；下界矿/残骸/普通方块 replace 后不相等，天然排除
        return anchorPath.replace("deepslate_", "").equals(blockPath.replace("deepslate_", ""));
    }

    /** 目标矿石家族方块列表（含深层变种），普通模式 Baritone mine 传入挖多种 */
    public List<Block> getTargetBlocks() {
        Block anchor = getTargetBlock();
        List<Block> family = new ArrayList<>();
        if (anchor == null || anchor == Blocks.AIR) return family;
        family.add(anchor);

        String path = BuiltInRegistries.BLOCK.getKey(anchor).getPath();
        // 变种只在主世界矿石里存在（deepslate_ 前缀互换）；下界矿/残骸/普通方块无变种
        if (!path.contains("_ore")) return family;

        String basePath = path.replace("deepslate_", "");
        if (path.startsWith("deepslate_")) {
            // 锚点本身是深层变种，补上浅层原矿
            BuiltInRegistries.BLOCK.getOptional(Identifier.fromNamespaceAndPath("minecraft", basePath))
                .ifPresent(b -> { if (!family.contains(b)) family.add(b); });
        } else {
            // 锚点是浅层原矿，补上深层变种（存在才加）
            BuiltInRegistries.BLOCK.getOptional(Identifier.fromNamespaceAndPath("minecraft", "deepslate_" + basePath))
                .ifPresent(b -> { if (!family.contains(b)) family.add(b); });
        }
        return family;
    }

    /** 目标矿石家族方块完整 ID 集合（精准采集计数/卸货判定用） */
    public Set<String> getTargetBlockIds() {
        Set<String> ids = new HashSet<>();
        for (Block b : getTargetBlocks()) {
            ids.add(BuiltInRegistries.BLOCK.getKey(b).toString());
        }
        return ids;
    }

    /** 目标矿石对应的掉落物完整 ID（时运模式计数/卸货判定用） */
    public String getTargetDropItemId() {
        Block target = getTargetBlock();
        String blockId = BuiltInRegistries.BLOCK.getKey(target).getPath();
        return switch (blockId) {
            case "lapis_ore", "deepslate_lapis_ore" -> "minecraft:lapis_lazuli";
            case "redstone_ore", "deepslate_redstone_ore" -> "minecraft:redstone";
            case "coal_ore", "deepslate_coal_ore" -> "minecraft:coal";
            case "diamond_ore", "deepslate_diamond_ore" -> "minecraft:diamond";
            case "emerald_ore", "deepslate_emerald_ore" -> "minecraft:emerald";
            case "gold_ore", "deepslate_gold_ore" -> "minecraft:raw_gold";
            case "nether_gold_ore" -> "minecraft:gold_nugget";
            case "iron_ore", "deepslate_iron_ore" -> "minecraft:raw_iron";
            case "copper_ore", "deepslate_copper_ore" -> "minecraft:raw_copper";
            case "nether_quartz_ore" -> "minecraft:quartz";
            case "ancient_debris" -> "minecraft:ancient_debris";
            default -> BuiltInRegistries.ITEM.getKey(target.asItem()).toString();
        };
    }

    // ── 配置访问器（供子组件调用，旧项目 :1361-1392 逐条对应） ──

    public String getWildCommand() { return settings.wildCommand; }
    public boolean isRtpGuiEnabled() { return settings.rtpGuiEnabled; }
    public String getRtpGuiKeyword() { return settings.rtpGuiKeyword; }
    public String getUnloadCommand() { return settings.unloadCommand; }
    public String getSupplyCommand() { return settings.supplyCommand; }
    public String getAFKCommand() { return settings.afkCommand; }
    public String getRespawnCommand() { return settings.respawnCommand; }

    public int getUnloadThreshold() { return settings.unloadThreshold; }
    public int getFullLoadStacks() { return settings.unloadThreshold; }
    public int getHungerThreshold() { return settings.hungerThreshold; }
    public int getDurabilityThreshold() { return settings.durabilityThreshold; }
    public int getTeleportDelay() { return settings.teleportDelay; }
    public int getRtpCooldown() { return settings.rtpCooldown; }
    public int getMineGoalUpdateInterval() { return settings.mineGoalUpdateInterval; }
    public boolean getAllowBreak() { return settings.allowBreak; }
    public boolean getAutoTool() { return settings.autoTool; }
    public boolean getFastBreak() { return settings.fastBreak; }
    public boolean getBypassAnticheat() { return settings.bypassAnticheat; }
    public int getBreakInterval() { return settings.breakInterval; }
    public boolean isLogisticsBreakBlocks() { return settings.logisticsBreakBlocks; }

    /** 食物白名单（旧项目存 {@code List<Item>}，本项目存登记 ID 字符串，语义不变） */
    public List<String> getFoodWhitelist() { return settings.foodWhitelist; }

    public ServerCommandRunner getCmdManager() { return cmdManager; }
    public MiningPathing getBaritone() { return baritone; }
    public MiningContainer getContainer() { return container; }
    public SoundNotifier getSoundNotifier() { return soundNotifier; }

    /** 9 态挖矿状态机（旧 {@code AutoMinerModule.getFsm()} 对应物） */
    public MiningStateMachine fsm() { return fsm; }

    /**
     * 目标产物物品 → 目标矿石方块（按当前采集模式反查，旧 {@code blockForTarget} {@code :1245-1256}）。
     *
     * <p>精准采集：原矿物品本身对应方块；时运：烧制产物（锭/石英/粗矿）经映射表反查矿石方块。</p>
     */
    private Block blockForTarget(String itemId, boolean nether) {
        if (itemId == null || itemId.isBlank()) return Blocks.AIR;
        if (isSilkTouchMode()) {
            Item item = itemOf(itemId);
            return item == null ? Blocks.AIR : Block.byItem(item);
        }
        String oreId = nether ? NETHER_FORTUNE.get(itemId) : OVERWORLD_FORTUNE.get(itemId);
        if (oreId == null) return Blocks.AIR;
        return blockOf(oreId);
    }

    /**
     * 目标候选过滤：该产物是否符合当前采集模式（旧 {@code isOverworldTargetItem :1216-1228} 与
     * {@code isNetherTargetItem :1231-1242}，两条合并为一个 {@code nether} 参数）。
     *
     * <p>精准采集列原矿物品（主世界排除深层变种与下界矿，下界只列残骸 / 下界金矿 / 石英矿）；
     * 时运列掉落物（映射表的键）。空气始终放行：它就是「未选择」，配置页用它把已选目标清空。</p>
     *
     * @param itemId 物品登记 ID
     * @param nether 是否下界产物
     */
    public boolean isOreTargetCandidate(String itemId, boolean nether) {
        if (itemId == null || itemId.isBlank()) return false;
        // 空气 = 未选择（旧 filter 首行的 Items.AIR 放行）
        if (itemOf(itemId) == null) return true;

        if (isSilkTouchMode()) {
            if (nether) {
                return itemId.equals("minecraft:nether_gold_ore")
                    || itemId.equals("minecraft:nether_quartz_ore")
                    || itemId.equals("minecraft:ancient_debris");
            }
            return itemId.startsWith("minecraft:") && itemId.endsWith("_ore")
                && !itemId.contains("deepslate") && !itemId.contains("nether");
        }
        return (nether ? NETHER_FORTUNE : OVERWORLD_FORTUNE).containsKey(itemId);
    }

    /**
     * 切换采集模式后把已选目标同步成等价产物（旧 {@code syncTargetsOnModeSwitch :1258-1269}）。
     *
     * <p>时运的掉落物 ↔ 精准的原矿，改的是同一座矿：不这么做，切模式后目标会落在候选集合之外，
     * 自检与状态机的目标判定一起失效。认不出的产物保持原值（不猜、不清空）。</p>
     */
    public void syncTargetsOnModeSwitch() {
        boolean silk = isSilkTouchMode();
        if (!settings.overworldOreTarget.isBlank()) {
            settings.overworldOreTarget = equivalentTargetId(settings.overworldOreTarget, silk, false);
        }
        if (!settings.netherOreTarget.isBlank()) {
            settings.netherOreTarget = equivalentTargetId(settings.netherOreTarget, silk, true);
        }
    }

    /** 旧产物 ID → 新模式等价产物 ID（旧 {@code equivalentItem :1272-1296}） */
    private String equivalentTargetId(String itemId, boolean silk, boolean nether) {
        Map<String, String> fortune = nether ? NETHER_FORTUNE : OVERWORLD_FORTUNE;
        if (silk) {
            // 旧 = 时运（粗矿/锭/石英）→ 反查矿石方块 → 原矿物品
            String oreId = fortune.get(itemId);
            if (oreId == null) return itemId;
            Item ore = blockOf(oreId).asItem();
            return ore == Items.AIR ? itemId : BuiltInRegistries.ITEM.getKey(ore).toString();
        }
        // 旧 = 精准（原矿物品）→ 方块 → 反查时运产物
        Item item = itemOf(itemId);
        if (item == null) return itemId;
        Block ore = Block.byItem(item);
        if (ore == Blocks.AIR) return itemId;
        String oreBlockId = BuiltInRegistries.BLOCK.getKey(ore).toString();
        for (Map.Entry<String, String> entry : fortune.entrySet()) {
            if (entry.getValue().equals(oreBlockId)) return entry.getKey();
        }
        return itemId;
    }

    /** 登记 ID → 物品；ID 非法或不存在返回 {@code null} */
    private static Item itemOf(String itemId) {
        Identifier id = Identifier.tryParse(itemId);
        if (id == null) return null;
        Item item = BuiltInRegistries.ITEM.getValue(id);
        return item == null || item == Items.AIR ? null : item;
    }

    /** 登记 ID → 方块；ID 非法或不存在返回 {@link Blocks#AIR} */
    private static Block blockOf(String blockId) {
        Identifier id = Identifier.tryParse(blockId);
        if (id == null) return Blocks.AIR;
        Block block = BuiltInRegistries.BLOCK.getValue(id);
        return block == null ? Blocks.AIR : block;
    }

    /** 设置项的 ARGB 值 → 调色板载体（载入设置、构造时调用） */
    private void syncColorsFromSettings() {
        applyToEsp(mineralColor, settings.mineralColor);
        applyToEsp(foodColor, settings.foodColor);
        applyToEsp(afkColor, settings.afkColor);
    }

    /**
     * 调色板载体 → 设置项的 ARGB 值；有变化立即落盘。
     *
     * <p>由配置页每帧调用：调色板窗口直接改载体，关闭后页面下一帧把 RGB 与透明度写回设置项，
     * 彩虹相位不参与（设置项只承载 ARGB 整数）。</p>
     */
    public void syncColorsToSettings() {
        int mineral = pack(mineralColor);
        int food = pack(foodColor);
        int afk = pack(afkColor);
        if (mineral == settings.mineralColor && food == settings.foodColor && afk == settings.afkColor) return;
        settings.mineralColor = mineral;
        settings.foodColor = food;
        settings.afkColor = afk;
        persistSettings();
    }

    private static void applyToEsp(EspColor color, int argb) {
        color.rgb(argb & 0xFFFFFF);
        color.alpha((argb >>> 24) & 0xFF);
    }

    /** 调色板载体打包为设置项使用的 ARGB */
    private static int pack(EspColor color) {
        return ((color.alpha() & 0xFF) << 24) | (color.rgb() & 0xFFFFFF);
    }

    // ── 内部 ──

    /**
     * 判断物品是否带指定附魔（26.x 附魔为动态注册表，需从世界注册表解析）。
     *
     * <p>与旧项目 {@code AutoMinerModule.hasEnchant}（{@code :927-942}）写法一致。</p>
     */
    private boolean hasEnchant(ItemStack stack, ResourceKey<Enchantment> enchantKey) {
        if (stack.isEmpty() || mc.level == null) return false;
        ItemEnchantments ench = stack.get(DataComponents.ENCHANTMENTS);
        if (ench == null || ench.isEmpty()) return false;
        try {
            var lookup = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            var holder = lookup.get(enchantKey).orElse(null);
            return holder != null && ench.getLevel(holder) > 0;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 装载当前服务器的点位（自检与启用都要用）。
     *
     * <p>配置页进入时也调这里：页面必须显示磁盘上的真实绑定，而点位装载只发生在自检 / 启用，
     * 玩家「先进页面配点位、再开模块」或重启后直接开页面时，读到的会是空表。点位写入全部即时落盘，
     * 因此重读不会丢数据。</p>
     */
    public void reloadStore() {
        pointStore.reload();
        storeContext = currentStoreContext();
    }

    /**
     * 自检用的装载：同一世界上下文内不重复读盘。
     *
     * <p>启动自检未通过的模块会留在等待队列里按刻重试，若每次自检都重读点位文件，模块一直卡在
     * 等待状态就等于磁盘一直在转。点位的唯一外部变化来源是换服务器 / 换存档（会话内增删点位
     * 直接改内存对象），因此以「服务器 + 维度」为界，上下文变了才重新读盘；
     * 真正启动时走的 {@link #reloadStore()} 不节流。</p>
     */
    private void refreshStoreIfContextChanged() {
        if (currentStoreContext().equals(storeContext)) return;
        reloadStore();
    }

    /** 当前世界上下文标识：换服 / 换存档 / 换维度都会变 */
    private static String currentStoreContext() {
        return WorldIdentity.server() + "@" + WorldIdentity.dimension();
    }
}
