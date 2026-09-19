package com.yiyiaddon.feature.mining;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.admindetect.AdminDetectorModule;
import com.yiyiaddon.feature.admindetect.service.AdminDisconnect;
import com.yiyiaddon.feature.combat.KillAuraRepairHook;
import com.yiyiaddon.feature.mining.command.WkCommand;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.fastbreak.MiningFastBreakController;
import com.yiyiaddon.feature.mining.fsm.MinerState;
import com.yiyiaddon.feature.mining.fsm.MiningStateMachine;
import com.yiyiaddon.feature.mining.model.ConfigRecord;
import com.yiyiaddon.feature.mining.model.LootMode;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.navigation.MiningPathing;
import com.yiyiaddon.feature.mining.notification.SoundNotifier;
import com.yiyiaddon.feature.mining.render.MiningBreakProgressRenderer;
import com.yiyiaddon.feature.mining.render.MiningPointRenderer;
import com.yiyiaddon.feature.mining.repository.MiningConfigRecordStore;
import com.yiyiaddon.feature.mining.repository.MiningPointStore;
import com.yiyiaddon.feature.mining.service.MiningBindingService;
import com.yiyiaddon.feature.mining.service.MiningContainer;
import com.yiyiaddon.feature.mining.service.ServerCommandRunner;
import com.yiyiaddon.feature.mining.ui.AutoMinerPage;
import com.yiyiaddon.feature.mining.vein.MiningVeinMiner;
import com.yiyiaddon.integration.baritone.BaritoneChatTranslations;
import com.yiyiaddon.platform.container.SilentContainer;
import com.yiyiaddon.platform.eat.OffhandRationLock;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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
 * {@link SoundNotifier}），以及状态机 {@link MiningStateMachine}（旧 {@code MinerFSM}）——
 * 状态机已挂到 {@link #onEnable()} / {@link #onDisable()} / {@link #onTick} 上。</p>
 *
 * <p><b>已接入（批次 4）</b>：修补联动战斗真实现
 * {@link com.yiyiaddon.feature.combat.KillAuraRepairHook}（构造时注入状态机，进入修补开杀戮光环、
 * 离开修补只关我们自己开的那一个）。</p>
 *
 * <p><b>已接入（批次 6，用户 2026-09-17 实机驱动）</b>：秒破发包破坏
 * （{@link MiningFastBreakController}，每刻由 {@link #onTick} 驱动推进）、
 * 挖掘进度 ESP（{@link MiningBreakProgressRenderer}，另一层世界渲染）、
 * 战斗拦截（{@code MinerState#COMBAT} + {@link MiningCombat}，默认开启、无设置项）、
 * 连锁挖矿（{@link MiningVeinMiner}，5 项设置，复用秒破的单槽发包通道逐块清矿脉）——
 * 状态机因此由 9 态扩到 10 态。</p>
 *
 * <p><b>视角口径（用户 2026-09-18 定稿）</b>：挖掘与寻路的可见视角交回 Baritone，模块在
 * <b>这些状态下</b>一行角度都不写。依据 26.1.2 原版与 Baritone 源码：默认设置下
 * （{@code freeLook=true}、{@code blockFreeLook=false}）{@code MineProcess} 会以
 * {@code blockInteract=true} 调用 {@code LookBehavior}，解析为 CLIENT——破坏方块时 Baritone 自己就把
 * 可见视角对准目标方块（含俯仰）。此前模块自己也每刻写角度，两个写入者互相覆盖，才是用户看到的
 * 「剧烈抖动」，故整类删除。</p>
 *
 * <p><b>「寻路视角跟随」（用户 2026-09-18 追加，默认开）</b>：默认设置下平面走路解析为 SERVER 静默模式
 * （只写服务端朝向、本地视角还原）——所以走路时看不到寻路视角。开关打开时模块把 Baritone 的
 * {@code freeLook} 关掉，走路也落到 CLIENT 分支，视角由男中音每刻写向路径下一节点；同时把每刻随机偏移
 * （{@code randomLooking / randomLooking113}）归零，那是「视角抖」的直接来源。此时可见视角仍然只有
 * 男中音一个写入者，因此不抖。开关关闭或模块关闭时三项设置全部还原。</p>
 *
 * <p><b>分段归属（避免双写入者）</b>：战斗态由 {@code MiningCombat} 转头盯怪（此时把 {@code freeLook}
 * 让回默认值，男中音转 SERVER 静默）；物流开箱、挂机修复对准、水中脱困对准作业面这三处由状态机写角度，
 * 但都发生在 {@code baritone.stop()} 之后，男中音已无寻路目标、不写角度。</p>
 *
 * <p><b>未做（留待后续批次）</b>：种子模式（OrePredictor，随之一并留白的还有
 * {@code .wk 检测假矿} 子命令与按钮、帮助页该行）。</p>
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

    /** 挖掘进度 ESP 的世界渲染层标识（与点位层分开注册，互不覆盖） */
    private static final String BREAK_LAYER_ID = MODULE_ID + ":break";

    /** 事件订阅所有者标识（与其他模块同一命名：{@code module.<模块ID>}） */
    private static final String EVENT_OWNER = "module." + MODULE_ID;

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

    /** 挖掘进度 ESP：秒破正在破坏的方块显示百分比 + 收缩框（用户 2026-09-17 需求） */
    private final MiningBreakProgressRenderer breakRenderer;

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

    /** 10 态挖矿状态机（旧 {@code MinerFSM}） */
    private final MiningStateMachine fsm = new MiningStateMachine(this);

    /** 连锁挖矿：清掉整条连通矿脉（用户 2026-09-17 新增需求，复用秒破的单槽发包通道） */
    private final MiningVeinMiner veinMiner = new MiningVeinMiner(this);

    /** 最近一次装载点位时所处的世界上下文（{@code server@dimension}） */
    private String storeContext;

    /**
     * 最近一次装载设置时所处的设置作用域（{@code WorldIdentity.fileSafeServer()}）；
     * {@code null} = 全局模板（未进世界或尚未装载）。换服判据用它比对，见 {@link #refreshSettingsIfScopeChanged()}。
     */
    private String loadedSettingsScope;

    public AutoMinerModule() {
        super(MODULE_ID, MESSAGE_MODULE, "automation",
                "Baritone驱动全自动挖矿，物流循环，耐久修补，死亡自愈。点击按钮查看说明。");

        this.renderer = new MiningPointRenderer(this);
        this.breakRenderer = new MiningBreakProgressRenderer(this);
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
        // 玩家自己敲的服务器指令（用户 2026-09-18 需求）走 subscribedEvents() 声明式订阅，见那里的注释
    }

    /**
     * 玩家自己敲的服务器指令 → 交给状态机做「按结果判」的手动传送判定（用户 2026-09-18 口径）。
     *
     * <p>用户原话：<i>「我在挖矿的途中，我输入了 /spawn，然后脚本他就会寻路 spawn 附近接着挖，
     * 包括 /home 也是，能不能检测一下」</i>、<i>「我的意思是让他不要挖，我传送回家的时候会把家里挖烂，
     * 包括 spawn」</i>、以及追问 <i>「要是我要跟别人私聊怎么办 /w」</i> 之后的拍板：<b>按结果判</b>
     * ——敲指令先不动，看它有没有真的把位置挪走。</p>
     *
     * <p><b>为什么不能只靠位置跳变</b>（{@code MiningStateMachine#manualTeleportDetected()}）：
     * 那条判据只在 {@code MINING} 态生效（卸货 / 补给 / 修补 / 前往野外都是我们自己发传送指令的地方，
     * 判了会误伤自己），玩家在打怪、进食、卸货途中敲 /spawn 就漏了；就算在挖矿态，我方传送的宽限窗
     * 也可能把玩家这一跳当成「我方落地」消费掉。结果就是人到了家/出生点、脚本在他落地的地方继续凿。</p>
     *
     * <p><b>现在的口径</b>：从发包出口拿原文（{@code ClientEventType#CLIENT_COMMAND}），
     * 只要不是本模块自己发的（{@link ServerCommandRunner#isOwnCommand}，含前往挖矿 / 卸货 / 补给 /
     * 挂机修复 / 死亡返回）就交给 {@link MiningStateMachine#onPlayerCommand} 记下窗口；
     * 窗口内位置真的跳变才<b>暂停挖矿</b>（不在这里挖、也不去别处挖）。这样
     * {@code /w}、{@code /msg}、{@code /ping} 这类不改位置的指令不打断挖矿，
     * {@code /home}、{@code /spawn}、{@code /tpa} 一律停。</p>
     *
     * <p>播报走 {@code error} 而不是 {@code info/warning}：后两者会被「状态播报」开关静默，
     * 而这个暂停必须让你看见原因。纯客户端指令（{@code .wk} 之类）不会产生这个包，因此不会误报。</p>
     *
     * <p><b>订阅方式</b>：写在 {@link #subscribedEvents()} 里由事件桥在启用时挂上，
     * <b>不能</b>在构造函数里 {@code subscribe}（会被 {@code ModuleEventBridge#attach} 的
     * {@code unsubscribeAll} 清掉且不再恢复，见该方法的注释）。</p>
     */
    private void onClientCommand(ClientEvent event) {
        if (!isEnabled()) return;
        String command = event.payload();
        if (command == null || command.isBlank()) return;
        if (cmdManager.isOwnCommand(command)) return;
        fsm.onPlayerCommand(command);
    }

    @Override
    public String name() {
        return "AutoMiner";
    }

    @Override
    public String icon() {
        return ICON;
    }

    /** 分类内排序：自动化分类第四位（自动重生 → 自动农场 → 自动骨粉 → 自动挖矿 → …） */
    @Override
    public int order() {
        return 40;
    }

    // ── 设置持久化 ──

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
        // 记下这一份是从哪个作用域读来的：换服判据用它比对（见 refreshSettingsIfScopeChanged）。
        // 装配期（主菜单）读到的是全局模板，此时作用域为 null。
        loadedSettingsScope = settingsScope();
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
    }

    /** 立即写回设置（界面改动即时生效，与旧项目设置自动保存一致） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    /**
     * 设置隔离键：进世界后取当前服务器 / 单人存档，未进世界返回 {@code null}（按全局模板处理）。
     *
     * <p><b>为什么需要隔离</b>（用户 2026-09-18 报的「换服把配置覆盖了」）：三点点位早就按服务器分文件
     * （{@code MiningPointStore}），但设置原本全局共用一份 —— 在 B 服改「返回卸货指令」，A 服的同一项
     * 跟着变，两服的配置互相覆盖。现在设置与点位同口径隔离：一个服务器（单人则是一个存档）一套配置，
     * 切服自动切换。</p>
     *
     * <p><b>未进世界必须返回 null</b>：主菜单也能打开模块中心改设置，那时没有服务器身份，
     * 随便造一个键会把编辑内容写到一个并不存在的世界上。返回 null 时读写都落在全局模板上，
     * 而全局模板同时是「新服务器 / 新存档的初始值」（见 {@code ModuleStateConfig}）。</p>
     */
    @Override
    public String settingsScope() {
        if (mc.level == null || mc.player == null) return null;
        return WorldIdentity.fileSafeServer();
    }

    /**
     * 换服 / 首次进世界时按当前服务器重读设置。
     *
     * <p>调用点只有 {@link #reloadStore()} 一处，而它已经覆盖了三条必经路径：启动自检、模块启用、
     * 打开配置页。设置与点位属于同一类「这台服务器的东西」，因此挂在同一个时机上一起换；
     * 读盘动作由运行时承担（{@link ModuleManager#reloadScopedSettings}），本方法只判「作用域变没变」。</p>
     */
    private void refreshSettingsIfScopeChanged() {
        if (Objects.equals(settingsScope(), loadedSettingsScope)) return;
        ModuleManager.reloadScopedSettings(this);
    }

    // ── 配置记录（控制台：底部快捷按钮 + 「配置记录」页，用户 2026-09-18 要求） ──
    // 与「设置自动按服务器隔离」互补：自动隔离管「切服自动换成那一套」，这里管「改乱了能回滚」。
    // 快照按服务器 / 单人存档各存一份文件（MiningConfigRecordStore），复原只写回当前服务器的设置桶。

    /** 记录时间的显示格式（简短到够用：同一天的记录一眼看出是什么时候存的） */
    private static final DateTimeFormatter RECORD_TIME_FORMAT = DateTimeFormatter.ofPattern("MM-dd HH:mm");

    /** 时间戳 → 记录列表 / 播报里用的显示文本（{@code MM-dd HH:mm}）；非正数返回空串 */
    public static String recordTimeText(long millis) {
        if (millis <= 0L) return "";
        return RECORD_TIME_FORMAT.format(Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()));
    }

    /** 当前是否处于可记录状态：已进入服务器 / 单人存档（主菜单为 false，两个按钮据此禁用与提示） */
    public boolean hasRecordScope() {
        return settingsScope() != null;
    }

    /** 当前服务器 / 单人存档的记录键；未进入世界返回空串（「配置记录」页据此标出「本服」那一条） */
    public String recordScopeKey() {
        String scope = settingsScope();
        return scope == null ? "" : scope;
    }

    /** 当前服务器 / 单人存档的展示名（写法与记录卡片一致）；未进入世界返回「未进入世界」 */
    public String currentScopeName() {
        return ConfigRecord.displayNameOf(WorldIdentity.server());
    }

    /** 本服记录是否存在（控制台「复原本服记录」按钮的可用性依据） */
    public boolean hasServerRecord() {
        return !recordScopeKey().isEmpty() && MiningConfigRecordStore.exists(recordScopeKey());
    }

    /** 本服记录的保存时间文本（{@code MM-dd HH:mm}）；无记录返回空串 */
    public String serverRecordTimeText() {
        String scope = recordScopeKey();
        return scope.isEmpty() ? "" : recordTimeText(MiningConfigRecordStore.savedAt(scope));
    }

    /** 全部已保存的配置记录（只列点过「保存本服记录」的，按保存时间从新到旧） */
    public List<ConfigRecord> serverRecords() {
        return MiningConfigRecordStore.list();
    }

    /**
     * 一键保存当前配置：把当前<b>全部</b>设置与<b>三个点位</b>一起写成这台服务器（单人则是当前存档）
     * 的快照。
     *
     * <p>内容包括：设置（{@link MiningSettings#save} 全字段）+ 点位（{@link MiningPointStore#snapshot()}：
     * 矿物箱 / 食物箱 / 挂机修复点，含坐标与维度）。用户 2026-09-18：「我要的是一键保存 全部配置
     * 包括设置 跟坐标点位懂吗」。同时写入逻辑服务器键，列表里才能认出是哪个服。</p>
     */
    public void saveServerRecord() {
        String scope = settingsScope();
        if (scope == null) {
            warning("§e⚠ 未进入世界 §8▸ 配置记录只能在进入服务器或单人存档后保存");
            return;
        }
        JsonObject snapshot = new JsonObject();
        settings.save(snapshot);
        if (MiningConfigRecordStore.write(scope, WorldIdentity.server(), snapshot, pointStore.snapshot(),
            System.currentTimeMillis())) {
            // 播报里带上服务器 IP：用户 2026-09-18 反馈「根本没有识别服务器ip」，
            // 单说「本服」看不出到底写到了哪一台，把键摆出来才可核对。
            info("§a✓ 已保存本服配置 §8▸ " + currentScopeName() + " §8· §7设置 + "
                + pointStore.size() + " 个点位 §8· §7" + serverRecordTimeText());
        } else {
            error("§c✗ 配置保存失败 §8▸ 配置文件无法写入");
        }
    }

    /**
     * 用当前配置（全部设置 + 三个点位）覆盖<b>本服</b>那条记录。
     *
     * <p><b>只认本服</b>（用户 2026-09-18：「还是能互相保存 根本没有识别服务器ip 拦截」）：
     * 上一版允许把当前配置推到任何一条记录上（写方向不限本服），实机里被当成漏洞 ——
     * 在一个服上点另一条的「替换」，就把那个服的记录盖成了这台服的配置。
     * 现在与「复原」同一条口径：记录键与当前服务器 / 单人存档不一致时直接拒绝，
     * 界面侧同样会先拦一道（见 {@code MiningRecordScreen#confirmReplace}）。</p>
     */
    public boolean replaceRecord(ConfigRecord record) {
        String scope = settingsScope();
        if (record == null || scope == null) {
            warning("§e⚠ 未进入世界 §8▸ 配置记录只能在进入服务器或单人存档后写入");
            return false;
        }
        if (!sameScopeAs(record)) {
            warning("§e⚠ 不可替换 §8▸ 这条记录属于 " + record.displayName()
                + "，与本服不一致（写入只认本服的记录）");
            return false;
        }
        JsonObject snapshot = new JsonObject();
        settings.save(snapshot);
        if (!MiningConfigRecordStore.write(record.scopeKey(), record.serverKey(), snapshot,
            pointStore.snapshot(), System.currentTimeMillis())) {
            error("§c✗ 记录替换失败 §8▸ " + record.displayName());
            return false;
        }
        info("§a✓ 已用当前配置替换记录 §8▸ " + record.displayName()
            + " §8· §7设置 + " + pointStore.size() + " 个点位");
        return true;
    }

    /** 记录是否与当前同源（同一台服务器 / 同一个存档）：不一致时读取与替换都要先提示（用户 2026-09-18 要求） */
    public boolean sameScopeAs(ConfigRecord record) {
        return record != null && !recordScopeKey().isEmpty()
            && record.scopeKey().equals(recordScopeKey());
    }

    /**
     * 记录里的设置，还原成一份可读的 {@link MiningSettings}（「详情」窗与列表摘要按类型字段读）。
     *
     * <p>还原成设置对象而不是把 JSON 直接甩给界面：键名只在设置类里写一次，界面读的是字段，
     * 不会因为落盘键改名而与列表显示脱节。没有记录返回 {@code null}。</p>
     */
    public MiningSettings recordSettings(ConfigRecord record) {
        JsonObject json = record == null ? null : MiningConfigRecordStore.readSettings(record.scopeKey());
        if (json == null) return null;
        MiningSettings snapshot = new MiningSettings();
        snapshot.load(json);
        return snapshot;
    }

    /** 记录里的点位（「详情」窗显示坐标与维度）；没有记录或没有点位返回空表 */
    public Map<MiningPointType, MiningPoint> recordPoints(ConfigRecord record) {
        JsonObject json = record == null ? null : MiningConfigRecordStore.readPoints(record.scopeKey());
        return MiningPointStore.parseSnapshot(json);
    }

    /**
     * 读取一条记录：设置与点位<b>整份替换</b>成记录里的内容（「配置记录」页每行的「读取」）。
     *
     * <p><b>只认本服（严格 IP 审核）</b>：记录键必须与当前服务器 / 单人存档一致才复原 ——
     * 用户 2026-09-18 看到「在别的服务器也能读取别的服」后当场裁定「卡死：不一致就不能复原」。
     * 早期版本允许跨服读取（提示后确认即可），现已否决：别的服务器的点位坐标在本服不适用，
     * 复原过去只会把本服的点位冲掉。跨服的记录仍可看详情 / 替换 / 删除，界面侧还会再拦一道
     * （见 {@code MiningRecordScreen#read}）。</p>
     *
     * <p><b>模块运行中拒绝</b>：点位与目标矿都参与状态机，跑着的时候整份换掉等于换了个配置去跑
     * （与「模块运行中无法修改点位」同一条口径）。写入的仍是<b>当前</b>服务器 / 存档的设置桶与点位文件，
     * 记录文件本身不动。</p>
     *
     * @return 是否读取成功
     */
    public boolean restoreRecord(ConfigRecord record) {
        String scope = settingsScope();
        JsonObject snapshot = record == null ? null : MiningConfigRecordStore.readSettings(record.scopeKey());
        if (record == null || snapshot == null || scope == null) {
            warning("§e⚠ 记录不可用 §8▸ 请进入服务器或单人存档后再读取");
            return false;
        }
        if (!sameScopeAs(record)) {
            warning("§e⚠ 不可复原 §8▸ 这条记录属于 " + record.displayName()
                + "，与本服不一致（复原只认服务器 IP / 存档一致的记录）");
            return false;
        }
        if (isEnabled()) {
            warning("§e⚠ 模块运行中 §8▸ 请先关闭自动挖矿，再读取配置（运行中不能改点位与目标）");
            return false;
        }
        applyRecord(snapshot);
        int bound = pointStore.replaceAll(MiningConfigRecordStore.readPoints(record.scopeKey()));
        info("§a✓ 已读取配置 §8▸ " + record.displayName()
            + " §8· §7设置 + " + bound + " 个点位 §8· §7保存于 " + recordTimeText(record.savedAt()));
        return true;
    }

    /**
     * 删除一条记录（「配置记录」页每行的「删除」）。
     *
     * <p>只删记录文件，<b>不动任何设置</b>：当前设置、以及该服务器的自动隔离桶都原样保留。</p>
     *
     * @return 是否删除成功
     */
    public boolean deleteRecord(ConfigRecord record) {
        if (record == null) return false;
        if (!MiningConfigRecordStore.delete(record.scopeKey())) {
            error("§c✗ 记录删除失败 §8▸ " + record.displayName());
            return false;
        }
        info("§a✓ 已删除记录 §8▸ " + record.displayName());
        return true;
    }

    /**
     * 把一份快照灌进当前设置（读取记录用）：设置整份替换 + 立即落盘，
     * 模块正在运行时男中音那一批参数按新值重下发（此时点位已由 {@link #restoreRecord} 拦在关模块之后）。
     */
    private void applyRecord(JsonObject snapshot) {
        settings.load(snapshot);
        persistSettings();
        if (isEnabled()) applyBaritoneSettings();
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

        // 按当前设置下发 Baritone 调优（旧 onActivate :727-751）
        applyBaritoneSettings();

        // 各组件复位（旧 onActivate :753-756 在 applySettings 之后）
        fsm.reset();
        container.reset();
        cmdManager.reset();
        // 秒破状态机复位：换服 / 重启模块后不带入上一个会话的目标、冷却与暂停计时
        MiningFastBreakController.instance().resetTimers();
        veinMiner.reset();
        resetSpawnerPriority();
        // 断线防重锁复位：上次会话靠断线退出后重新进服再开模块，必须能再次触发断线
        disconnecting = false;
        // 接管 Baritone 聊天输出：模块运行期间不再刷 [Baritone] 那套消息，改由本模块播报关键事件
        // （用户 2026-09-18：「太刷屏了，取而代之的是我的自动挖矿播报」）
        baritone.suppressChat();
        // 运行期标志：让「瞄准方块高亮」在自动挖矿期间闭嘴（准星随挖掘目标扫，白框会一直闪）
        EspGlobalSettings.get().setAutoMinerRunning(true);

        // ESP：注册世界渲染层，关闭时注销（点位层 + 挖掘进度层）
        WorldOverlay.register(MODULE_ID, renderer::render);
        WorldOverlay.register(BREAK_LAYER_ID, breakRenderer::render);

        // 走路提速：默认给一点移速（用户 2026-09-18），关模块时由 onDisable 摘掉
        applyWalkSpeed();

        // 联动：自动挖矿开着的时候自动打开管理员检测（用户 2026-09-19 需求）——
        // 无人值守跑图时最怕管理员摸过来，「记得手动开检测」一定会漏
        AdminDetectorModule.linkFromAutomation(MESSAGE_MODULE);

        // 启动报告：旧 onActivate 的最后一步（旧 :758），让用户一眼确认这次跑的是什么配置
        reportStartupInfo();
    }

    /**
     * 按当前设置下发男中音（Baritone）调优（旧 {@code onActivate :727-751}，23 个入参顺序逐条一致）。
     *
     * <p><b>两处调用</b>：模块启用时，以及控制台「复原本服记录」把整份设置换掉之后
     * （设置页里的单项改动由各页面自己按字段下发，不走这里）。</p>
     */
    private void applyBaritoneSettings() {
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
    }

    @Override
    protected void onDisable() {
        // 副手口粮锁兜底解锁：运行中玩家换不动副手，停模块必须立刻还手（即使本模块是被断线自动关的，
        // 那时 onTick 已经不跑了，锁会一直留在 true）
        OffhandRationLock.setLocked(false);
        // 顺序照旧 onDeactivate :895-903（去掉种子缓存失效那一句）
        // 秒破：先收摊（发 ABORT 清服务端槽位 + 清裂纹 + 清状态），再停 Baritone
        MiningFastBreakController.instance().release(mc, true);
        // 连锁：清队列与「已接管 Baritone」标志（标志清了 mine 才会被状态机的自愈分支重新拉起）
        veinMiner.reset();
        resetSpawnerPriority();
        // 断线防重锁复位：断线退出后本模块会被自动关闭，本次触发就此收尾
        disconnecting = false;
        baritone.stop();
        container.closeContainer();
        // shutdown 而不是 reset：reset 不经过状态退场，在修补 / 进食 / 物流态关模块会漏掉
        // 「停杀戮光环 + 还原快捷栏 + 松开右键 + 还原 Baritone allowBreak」
        fsm.shutdown();
        container.reset();
        cmdManager.reset();
        // 还回 Baritone 的日志输出（接管期间聊天栏是被我们过滤的）
        baritone.restoreChat();
        // 还回寻路视角类设置（模块运行期间把 freeLook 关了、随机偏移归零；视角涉及玩家手感，必须还原）
        baritone.restoreViewSettings();
        // 还回瞄准方块高亮（模块运行期间它是被压掉的）
        EspGlobalSettings.get().setAutoMinerRunning(false);
        // 摘掉走路提速（瞬态修饰符，别留在玩家身上）
        clearWalkSpeed();
        WorldOverlay.unregister(MODULE_ID);
        WorldOverlay.unregister(BREAK_LAYER_ID);
    }

    // ── 事件 ──

    /**
     * 声明本模块订阅的事件类型（{@code ModuleEventBridge} 在启用时按这份清单订阅、关闭时整体退订）。
     *
     * <p><b>{@link ClientEventType#CLIENT_COMMAND} 必须写在这里，不能在构造函数里订阅</b>：
     * 事件桥 {@code attach} 的第一件事就是 {@code unsubscribeAll("module." + id)}
     * （{@code ModuleEventBridge#attach :31}），而本模块的 {@code EVENT_OWNER} 正是这个所有者；
     * 构造函数只在模块注册时跑一次，所以「构造期订阅 + 桥的启用期重订阅」会被第一次启用清掉，
     * 且永远不会挂回来。用户 2026-09-18 实机「我输入了还是在寻路」的根因就是这个：
     * 出包口的指令事件根本没送到本模块（日志里 01:22:34 敲 /home 时模块毫无反应）。</p>
     */
    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK, ClientEventType.SCREEN_OPEN, ClientEventType.DISCONNECT,
                ClientEventType.CLIENT_COMMAND);
    }

    /**
     * 事件分派（旧项目的两个 {@code @EventHandler}）。
     *
     * <ul>
     *   <li>{@code DISCONNECT} ← 旧 {@code onGameLeft :1167-1170}：退出世界即自动关模块，
     *       剩下的清理由 {@code onDisable} 走完。先作废点位视图与装载上下文，避免关模块过程中
     *       再按上一个服务器的数据落盘。</li>
     *   <li>{@code SCREEN_OPEN} ← 旧 {@code onOpenScreen :1172-1182}：静默容器。</li>
     *   <li>{@code CLIENT_COMMAND}：玩家敲的服务器指令（见 {@link #onClientCommand}）。</li>
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
            case CLIENT_COMMAND -> onClientCommand(event);
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

        // 玩家自己开着界面时别让「加载地形中」把它顶掉（用户 2026-09-19：「没用，只要传送成功，
        // 就会把我这个页面关闭」）：本服传送走「重生式传送」→ 客户端收到 ClientboundRespawnPacket
        // → 原版**无条件**把我方界面替换成 LevelLoadingScreen
        // （ClientPacketListener#handleRespawn → startWaitingForNewLevel → setScreenAndShow）。
        // 跳过显示它不影响任何流程：加载进度与「客户端已加载」上报都由 levelLoadTracker 在
        // ClientPacketListener#tick 里推进（notifyPlayerLoaded），与这个界面无关。
        if (SilentContainer.isLevelLoadingHijack(screenClassName)) {
            event.cancel();
            return;
        }

        // 玩家按 E 开背包（生存 / 创造都算）：背包永远放行，但要把我方静默容器收掉，
        // 否则玩家在背包里的点击会按箱子的 containerId 发出去（错位、丢物品）。
        // 走 MiningContainer 的收箱口（除关菜单外还要清开箱重试 / 精确补组等相位）。
        // 收掉后状态机在卸货 / 补给阶段的玩家界面守卫处「只等不做」，玩家关背包自然续上。
        if (SilentContainer.isPlayerInventory(screenClassName)) {
            getContainer().closeContainer();
            return;
        }
        // 只有「本模块自己在做容器事务」时才静默（用户 2026-09-19）：挂机时玩家手动去开自己的箱子，
        // 界面必须照常显示，不能被当成「模块自己在开箱」拦掉（卸货 / 补给期间的静默开箱照旧生效）
        if (getContainer().isOperatingContainer() && SilentContainer.isContainerScreen(screenClassName)) {
            // 玩家手动开的箱子：压掉 + 收掉那个容器（真不给开）+ 动作栏提示
            SilentContainer.rejectPlayerContainer();
            event.cancel();
        }
    }

    /** 每刻推进：低血断线优先 + 垃圾丢弃分频 + 秒破发包循环 + 状态机 */
    @Override
    public void onTick(Minecraft client) {
        if (client.player == null || client.level == null) return;
        // 副手口粮锁：模块运行中且副手确实是口粮时，玩家手动的 F 换手会被发包闸门丢弃
        // （用户 2026-09-19：「能不能运行期间锁死副手食物不让切换？除非停止模块」）。
        // 每刻按实况开合，两种情况自动解锁：
        //   ① 副手不是口粮（例如挂机修复点把要修的镐子换进副手了）；
        //   ② 状态机处于挂机修复点（REPAIR）—— 用户 2026-09-19 特别交代「修复工具要切换副手，
        //      这个时候要放行」。REPAIR 的进入动作要把工具换到副手，此刻副手还端着食物、
        //      ①并不成立，所以必须按状态显式放行（它换手走容器点击、本就不经闸门，这里再兜一层）
        OffhandRationLock.setLocked(
            fsm.state() != MinerState.REPAIR && container.isOffhandRationHeld());
        // 玩家试图换手（F）而被闸门拦下：告诉他「为什么按了没反应」（用户 2026-09-19 要求）
        if (OffhandRationLock.consumeBlocked() > 0) warnOffhandLocked();
        // 自动断线最优先：血量到线就退出服务器，本刻不再推进挖矿（多挖一刻就多挨一下，可能直接打死掉落）
        if (lowHealthDisconnect()) return;
        // 秒破的发包破坏循环必须由模块每刻驱动：Baritone 在算路 / 换目标的那几刻不会调到
        // continueDestroyBlock，旧实现靠那个回调推进，于是 START 发出后再没人发 STOP → 方块挖不烂
        MiningFastBreakController.instance().tick(client, this);
        // 连锁挖矿：秒破推进之后再跑，这样「上一块刚被权威同步确认破坏」就能同刻派发下一块。
        // 水中脱困破坏期间不连锁：脱困破坏也占那个单槽（它走原版入口 → 被秒破 Mixin 接管），两边同时发包会互相顶槽
        // 岩浆垫脚期间同样不连锁（用户 2026-09-18）：垫脚每块都要把主手换成搭路方块，连锁的秒破每刻又换回镐，
        // 交替覆盖就是「方块放不下去」；而且那期间 Baritone 已被停掉，扫脉派发也没有意义
        // EATING 也放行（用户 2026-09-19「边挖边吃」）：进食走副手，主手与选定槽不动，
        // 连锁/秒破照常跑；旧版主手进食必须停 Baritone，现在不停了
        veinMiner.tick(this, (fsm.state() == MinerState.MINING || fsm.state() == MinerState.EATING)
            && !fsm.isWaterBreaking() && !fsm.isLavaBridging());
        // 刷怪笼优先扫描：低频，只维护优先级标志；状态翻转发由状态机下发新的 mine 目标。
        // 连锁挖矿接管 Baritone 期间不参与：那时候服务端的破坏槽位归连锁，重起 mine 会两边顶槽（方块挖不烂）
        if (settings.breakSpawner && fsm.state() == MinerState.MINING && !veinMiner.isActive()
            && client.player.tickCount % SPAWNER_SCAN_INTERVAL == 0) {
            onSpawnerPriorityChanged(refreshSpawnerPriority());
        }
        // 副手常驻口粮：模块一开始跑就把白名单食物备进副手，不等进 MINING —— 旧写法挂在 tickMining 里，
        // 而 tickMining 开头有「预热 + 等区块」的早退（起 mine 前每刻 return），人已经跑了十几秒才搬。
        // 用户 2026-09-19：「不是模块已启动就放副手，而是等了几秒才放」。REPAIR 要占副手放要修的镐子、
        // COMBAT 期间不动背包，这两种状态跳过；IDLE 只存在一刻（下一 tick 就转 GO_WILD），也跳过。
        // 卸货 / 补给的容器开着，必须跳过：此时背包点击归容器会话（客户端对 containerId 不匹配的点击
        // 直接丢弃），而且补给流程自己会把食物直接补进副手（MiningContainer#withdrawFood 的副手分支）
        MinerState rationState = fsm.state();
        if (rationState != MinerState.IDLE && rationState != MinerState.REPAIR
            && rationState != MinerState.COMBAT && rationState != MinerState.SUPPLY
            && rationState != MinerState.UNLOADING) {
            container.tickOffhandRation();
        }
        fsm.tick();
        // 自动丢弃放在状态机之后、并跳过战斗态（用户 2026-09-18：「打怪的途中打死第一个捡到了
        // 掉落物，自动丢弃会先丢东西再打怪」）。放最后还有一个好处：本刻状态机刚判定的「进入战斗」
        // 立刻生效，不会多丢一刻。
        // 视角不在这里补写：挖掘时 Baritone 自己以 CLIENT 模式对准方块（含俯仰），平面走路时是
        // SERVER 静默模式，模块再写一次就会变成两个写入者互相覆盖（用户 2026-09-18 定稿删掉视角跟随）
        if (!fsm.isInCombat()) {
            container.tickTrashDisposal(settings.keepWhitelist, settings.placeBlocks);
        }
        // 走路提速补挂：服务端每次同步属性包都会冲掉客户端的瞬态修饰符（已经在就直接返回）
        applyWalkSpeed();
    }

    // ── 走路提速（用户 2026-09-18：默认加点移速，不做设置项） ────────────────────

    /** 移速加成修饰符的 id（瞬态，只在本模块开着期间挂在玩家身上） */
    private static final Identifier WALK_SPEED_MODIFIER_ID =
        Identifier.fromNamespaceAndPath("yiyiaddon", "miner_walk_speed");

    /**
     * 移速加成档位（用户 2026-09-18：「速度二试一下 如果被 t 我让你改成 1」）。
     *
     * <p>档位按药水那套命名：<b>1 档 = 速度一（+20%）</b>、<b>2 档 = 速度二（+40%）</b>。
     * 要调就改这一个数字，下面那行自己算——客户端跑得比服务端认的快就会被拉回，
     * 被拉回就往下调一档。</p>
     */
    private static final int WALK_SPEED_LEVEL = 2;

    /** 客户端移速加成：每档 +20% 玩家基础移速 0.1（见 {@link #WALK_SPEED_LEVEL}） */
    private static final double WALK_SPEED_BONUS = 0.02 * WALK_SPEED_LEVEL;

    /**
     * 给玩家挂上走路提速。
     *
     * <p>用户原话：<i>「在帮我默认加点移速 发点包也行 走快一点点就行了 不用加设置 就默认写在代码里面」</i>。
     * 实现用<b>瞬态</b>属性修饰符：不落盘、模块关掉就摘干净、不新增任何设置项。</p>
     *
     * <p><b>关于幅度</b>：客户端跑多快，服务端仍按自己那份移速做移动校验，差距超出容差就是
     * 「你移动得太快」被拉回。所以做成了可调档位（见 {@link #WALK_SPEED_LEVEL}）：
     * 按用户要求先上 2 档（+40%）试，真被拉回就降 1 档（+20%）。</p>
     *
     * <p>写在世界同步之后调用：服务端下发属性包会整体重置客户端的属性实例，
     * 瞬态修饰符会被冲掉，所以每刻补挂一次（已挂上时是两次查表，可忽略）。</p>
     */
    private void applyWalkSpeed() {
        if (mc.player == null) return;
        AttributeInstance speed = mc.player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null || speed.getModifier(WALK_SPEED_MODIFIER_ID) != null) return;
        speed.addTransientModifier(new AttributeModifier(
            WALK_SPEED_MODIFIER_ID, WALK_SPEED_BONUS, AttributeModifier.Operation.ADD_VALUE));
    }

    /** 摘掉走路提速（关模块时调用，玩家身上不留任何本模块的修饰符） */
    private void clearWalkSpeed() {
        if (mc.player == null) return;
        AttributeInstance speed = mc.player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) return;
        speed.removeModifier(WALK_SPEED_MODIFIER_ID);
    }

    // ── 自动断线（用户 2026-09-18 追加：服务器死亡掉落，血量到线先退服保命） ──────────────
    // 断线实体动作复用 AdminDisconnect（旧「自动断线」模块在本项目的唯一实现，见
    // feature/admindetect/service/AdminDisconnect），本模块只负责「什么时候断」，不碰断线本身，
    // 也不再写第二条断线链路（第 169 条：同源逻辑只留一份）。

    /** 一格血 = 2 点血量（Minecraft 血量条口径；设置项与播报都用「格」） */
    private static final float POINTS_PER_HEART = 2f;

    /** 断线防重锁：断线包发出后本刻流程仍在跑、退出世界事件也要下一拍才到，防止同一次触发的重复断线 */
    private boolean disconnecting;

    /** 上次提示「副手已锁定」的刻（每 20 刻最多提示一次，玩家连按 F 时不刷屏） */
    private int offhandLockWarnTick = -1000;

    /** 玩家在模块运行期间试图换手（F）被闸门拦下：告诉他为什么没反应（用户 2026-09-19 要求） */
    private void warnOffhandLocked() {
        if (mc.player == null) return;
        if (mc.player.tickCount - offhandLockWarnTick < 20) return;
        offhandLockWarnTick = mc.player.tickCount;
        warning("§e⚠ 副手已锁定 §8▸ 运行期间不可切换副手（停止模块后恢复）");
    }

    /**
     * 低血量自动断线：血量掉到设定格数（含）时断开当前服务器连接，返回本次是否已触发。
     *
     * <p><b>为什么由 {@code onTick} 最前面调用</b>：血量见底那一刻最要紧的是先退出服务器，
     * 触发后调用方直接结束本刻流程，状态机 / 秒破 / 连锁都不再推进（断线途中继续发包没有意义）。</p>
     *
     * <p><b>只对活着的玩家生效</b>：死亡瞬间血量归零，此时掉落已经发生，再断线救不回东西，
     * 还会抢在自动重生之前把玩家踢出游戏，因此 {@code isDeadOrDying()} 直接放行、不断线。</p>
     *
     * <p><b>判定口径</b>：{@code Player#getHealth()}（20 点满血）与「设置格数 × 2」比较；
     * 设置项取值域在 {@code MiningSettings#load} 里已 clamp 到 1~20 格，此处不再二次兜底。</p>
     *
     * @return true 表示已发出断线，调用方应立即结束本刻流程
     */
    private boolean lowHealthDisconnect() {
        if (disconnecting || !settings.autoDisconnect) return false;
        if (mc.player == null || mc.player.connection == null) return false;
        if (mc.player.isDeadOrDying()) return false;

        float health = mc.player.getHealth();
        if (health > settings.autoDisconnectHealth * POINTS_PER_HEART) return false;

        disconnecting = true;
        info("§c✗ 血量过低 §8▸ 已触发自动断线，正在退出服务器");
        AdminDisconnect.disconnect(MESSAGE_MODULE, "血量过低（剩余 " + heartsText(health) + "）");
        return true;
    }

    /**
     * 血量文本：整格只写整数（{@code 2 格}），半格才带一位小数（{@code 1.5 格}）。
     *
     * <p>血量条本身按半格递进，直接打印点数（{@code 3 点}）玩家对不上自己看到的血条。</p>
     */
    private static String heartsText(float health) {
        float hearts = health / POINTS_PER_HEART;
        int whole = (int) hearts;
        return hearts == whole ? whole + " 格" : String.format(Locale.ROOT, "%.1f 格", hearts);
    }

    // ── 刷怪笼优先（用户 2026-09-18 新增设置，默认开） ───────────────────────
    // 「寻路途中发现刷怪笼就挖掉，先挖再打怪，不然越打越多怪」。
    // 实现口径：把刷怪笼临时并进 Baritone 的 mine 目标列表——附近有刷怪笼时它必然比矿点近，
    // Baritone 自然先走过去挖掉；挖完（扫描不到）再撤回普通目标。不另造一套寻路 / 破坏流程。

    /** 刷怪笼扫描半径（格）：以玩家为中心找这个范围内的刷怪笼（用户 2026-09-18 定 6 格） */
    private static final int SPAWNER_SCAN_RADIUS = 6;
    /** 刷怪笼扫描间隔（刻）：40 刻一次，够快也不会把 13³ 的方块读取压在每刻 */
    private static final int SPAWNER_SCAN_INTERVAL = 40;
    /** 同一个刷怪笼最长尝试时长（刻）：够不到就不耗着，暂时忽略，避免整片区域卡在「非挖它不可」
     *  （用户 2026-09-18 实机：卡住时「绿框一直闪却过不去」多半就是它。20 秒，比状态机的
     *  「挖不动」自愈窗口略长——正常情况由自愈先甩掉它，这里只是兜底） */
    private static final int SPAWNER_TRY_LIMIT_TICKS = 400;
    /** 忽略刷怪笼的时长（刻）：超时失败后先当没看见，过一段再重新考虑 */
    private static final int SPAWNER_IGNORE_TICKS = 2400;

    /** 当前是否需要把刷怪笼并进挖掘目标 */
    private boolean spawnerPriority;
    /** 已经在刷怪笼上耗掉的刻数 */
    private int spawnerPriorityTicks;
    /** 忽略刷怪笼的截止 tick（超时失败后的冷却） */
    private int spawnerIgnoreUntilTick;
    /** 上一次退出刷怪笼优先是否因为尝试超时（够不到 / 挖不动），仅供播报区分用词 */
    private boolean spawnerTimedOut;

    /** 本刻的挖掘目标：开启刷怪笼优先且附近有刷怪笼时，把刷怪笼一并交给 Baritone 先挖掉 */
    public List<Block> getMiningTargets() {
        List<Block> targets = getTargetBlocks();
        if (!spawnerPriority || targets.contains(Blocks.SPAWNER)) return targets;
        List<Block> withSpawner = new ArrayList<>(targets.size() + 1);
        withSpawner.addAll(targets);
        withSpawner.add(Blocks.SPAWNER);
        return withSpawner;
    }

    /**
     * 每 {@link #SPAWNER_SCAN_INTERVAL} 刻推进一次刷怪笼优先级。
     *
     * @return true 表示优先级状态翻转（附近出现刷怪笼 / 刷怪笼已被清掉 / 尝试超时），调用方应重下发挖掘目标
     */
    public boolean refreshSpawnerPriority() {
        if (!settings.breakSpawner || mc.player == null || mc.level == null) {
            boolean changed = spawnerPriority;
            spawnerPriority = false;
            spawnerPriorityTicks = 0;
            return changed;
        }
        int tick = mc.player.tickCount;
        if (spawnerPriority) {
            spawnerPriorityTicks += SPAWNER_SCAN_INTERVAL;
            if (!hasSpawnerNearby(SPAWNER_SCAN_RADIUS) || spawnerPriorityTicks > SPAWNER_TRY_LIMIT_TICKS) {
                spawnerTimedOut = spawnerPriorityTicks > SPAWNER_TRY_LIMIT_TICKS;
                if (spawnerTimedOut) {
                    spawnerIgnoreUntilTick = tick + SPAWNER_IGNORE_TICKS;
                }
                spawnerPriority = false;
                spawnerPriorityTicks = 0;
                return true;
            }
            return false;
        }
        if (tick < spawnerIgnoreUntilTick) return false;
        if (!hasSpawnerNearby(SPAWNER_SCAN_RADIUS)) return false;
        spawnerPriority = true;
        spawnerPriorityTicks = 0;
        return true;
    }

    /**
     * 当前是否把刷怪笼列为优先目标。
     *
     * <p>状态机用它决定战斗拦截是否还认「6 格内扫到怪物」这条：去挖刷怪笼的路上不主动出击，
     * 挨打才反击（见 {@code MiningCombat#threatDetected(boolean)}）。</p>
     */
    public boolean spawnerPriority() {
        return spawnerPriority;
    }

    /** 以玩家为中心扫描刷怪笼（只认原版刷怪笼方块） */
    private boolean hasSpawnerNearby(int radius) {
        if (mc.level == null || mc.player == null) return false;
        BlockPos center = mc.player.blockPosition();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (mc.level.getBlockState(center.offset(dx, dy, dz)).getBlock() == Blocks.SPAWNER) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** 清空刷怪笼优先状态（模块启停 / 换世界时调用，避免把上一片区域的判断带过来） */
    private void resetSpawnerPriority() {
        spawnerPriority = false;
        spawnerPriorityTicks = 0;
        spawnerIgnoreUntilTick = 0;
        spawnerTimedOut = false;
    }

    /**
     * 立刻放弃当前刷怪笼并忽略它一段时间（状态机的「挖不动」自愈调用）。
     *
     * <p>够不到的刷怪笼会让 Baritone 反复尝试走过去挖它（mine 目标里它最近），表现成
     * 「绿框一直闪却过不去、也不挖别的」——重下发 mine 时若仍带着它，等于白重发。
     * 所以自愈前先甩掉它，并进忽略窗避免 40 刻后又被扫出来。</p>
     */
    public void ignoreSpawnerForNow() {
        if (!spawnerPriority) return;
        spawnerPriority = false;
        spawnerPriorityTicks = 0;
        if (mc.player != null) {
            spawnerIgnoreUntilTick = mc.player.tickCount + SPAWNER_IGNORE_TICKS;
        }
    }

    /** 刷怪笼优先级翻转后的重下发：停旧 mine 进程，按新目标重起 */
    private void onSpawnerPriorityChanged(boolean changed) {
        if (!changed) return;
        baritone.stop();
        baritone.startMining(getMiningTargets(), false);
        if (spawnerPriority) {
            info("§e⚠ 附近发现刷怪笼 §8▸ 优先挖掉，避免持续刷怪");
        } else if (spawnerTimedOut) {
            warning("§e⚠ 刷怪笼挖不到 §8▸ 暂时跳过，先继续挖矿");
        } else {
            info("§a✓ 刷怪笼已清除 §8▸ 恢复常规挖矿目标");
        }
    }

    // ── 播报（配色语义与旧基类 YiyiaddonModule 一致） ──

    // 相同播报的折叠窗口（用户 2026-09-18：「狂刷屏」——连锁每挖一块矿脉就
    // 「接管 / 完成 / Baritone 已启动挖掘」各来一条，挖一组矿刷几十条）：
    // 连续同一条文本 5 秒内只播一次。单槽「上一次文本 + 时间戳」就够：
    // 折叠的目标是「同一句反复出现」，不同文本交错着来时每条都有自己的信息量
    private String lastBroadcastText;
    private long lastBroadcastMillis;
    private static final long BROADCAST_FOLD_MILLIS = 5000;

    /** 播报出口统一闸门：状态播报开关 + 相同文本折叠（error 不受开关限制，超错也照折） */
    private boolean broadcastBlocked(String message) {
        if (message != null && message.equals(lastBroadcastText)
            && System.currentTimeMillis() - lastBroadcastMillis < BROADCAST_FOLD_MILLIS) {
            return true;
        }
        lastBroadcastText = message;
        lastBroadcastMillis = System.currentTimeMillis();
        return false;
    }

    /** 普通信息 */
    public void info(String message) {
        if (!settings.statusBroadcast || broadcastBlocked(message)) return;
        ClientChat.send(MESSAGE_MODULE, message);
    }

    /** 警告：黄色加粗 */
    public void warning(String message) {
        if (!settings.statusBroadcast || broadcastBlocked(message)) return;
        ClientChat.send(MESSAGE_MODULE, "§e§l" + message);
    }

    /** 错误：橙色加粗 */
    public void error(String message) {
        if (broadcastBlocked(message)) return;
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
        report.append("\n§7目标矿物　§8▸ ").append(highlightText(getTargetDisplayName())).append("§r");
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
            report.append("\n§e⚠ 镐子无经验修补附魔：耐久低时不会前往挂机点修复，背包还有其它镐子则继续挖矿");
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
     * 当前选择的目标矿物显示名（旧 {@code getTargetDisplayName :843-858}）。
     *
     * <p>主世界 / 下界产物取物品悬停名（时运=粗铁这类掉落物名，精准=原矿名），普通方块取方块中文名，
     * 都没选时为 {@code 未选择}。本项目设置项存登记 ID，故先还原成物品 / 方块再走同一口径；
     * 不用 {@code getTargetBlock()} 的方块名代替：那会把「时运模式的掉落物名」显示成原矿名。</p>
     *
     * <p>两处消费方：启动报告的「目标矿物」行，与进矿状态播报（「✓ 开始挖矿 ▸ 目标矿 · 模式」）。
     * 后者由 {@code MiningStateMachine#broadcastStateTransition} 调用，故为 public。</p>
     */
    public String getTargetDisplayName() {
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
    public boolean isTeleportRetryEnabled() { return settings.teleportRetryEnabled; }
    public int getMineGoalUpdateInterval() { return settings.mineGoalUpdateInterval; }
    public boolean getAllowBreak() { return settings.allowBreak; }
    public boolean getAutoTool() { return settings.autoTool; }
    public boolean getFastBreak() { return settings.fastBreak; }
    public boolean getBypassAnticheat() { return settings.bypassAnticheat; }
    public int getBreakInterval() { return settings.breakInterval; }
    public boolean isLogisticsBreakBlocks() { return settings.logisticsBreakBlocks; }

    // 连锁挖矿（用户 2026-09-17 追加）
    /** 连锁挖矿的总开关（注意：连锁挖矿实例的取用是 {@link #getVeinMiner()}，不是这个名字） */
    public boolean isVeinMinerEnabled() { return settings.veinMiner; }
    /** 连锁挖矿实例（状态机与渲染层用它避让 / 查询队列状态） */
    public MiningVeinMiner getVeinMiner() { return veinMiner; }
    public int getVeinMaxBlocks() { return settings.veinMaxBlocks; }
    public int getVeinRange() { return settings.veinRange; }
    public boolean getVeinDiagonal() { return settings.veinDiagonal; }
    public boolean getVeinFamilyOnly() { return settings.veinFamilyOnly; }

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
     * 装载当前服务器的东西（设置 + 点位）；自检、启用、打开配置页三条路径都要用。
     *
     * <p>配置页进入时也调这里：页面必须显示磁盘上的真实绑定，而点位装载只发生在自检 / 启用，
     * 玩家「先进页面配点位、再开模块」或重启后直接开页面时，读到的会是空表。点位写入全部即时落盘，
     * 因此重读不会丢数据。</p>
     *
     * <p><b>设置先于点位</b>（用户 2026-09-18「设置按服务器隔离」）：自检读的是设置
     * （目标矿与三条指令），必须先换成这台服务器的那一套，否则自检会拿着上一个服务器的配置下结论。</p>
     */
    public void reloadStore() {
        refreshSettingsIfScopeChanged();
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
