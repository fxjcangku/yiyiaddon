package com.yiyiaddon.feature.librarian;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.librarian.config.LibrarianConfig;
import com.yiyiaddon.feature.librarian.config.LibrarianSettings;
import com.yiyiaddon.feature.librarian.config.SuccessSound;
import com.yiyiaddon.feature.librarian.fsm.LibrarianState;
import com.yiyiaddon.feature.librarian.integration.BaritoneMoveService;
import com.yiyiaddon.feature.librarian.model.EnchantmentTarget;
import com.yiyiaddon.feature.librarian.model.LibrarianContext;
import com.yiyiaddon.feature.librarian.model.TargetProgress;
import com.yiyiaddon.feature.librarian.platform.InventoryAccess;
import com.yiyiaddon.feature.librarian.platform.LecternOps;
import com.yiyiaddon.feature.librarian.platform.MerchantTradeOps;
import com.yiyiaddon.feature.librarian.platform.StationProbe;
import com.yiyiaddon.feature.librarian.platform.VillagerSearchProbe;
import com.yiyiaddon.feature.librarian.service.DebugLoggerService;
import com.yiyiaddon.feature.librarian.service.DebugSoundEvent;
import com.yiyiaddon.feature.librarian.service.DebugSoundService;
import com.yiyiaddon.feature.librarian.service.EnchantmentMatcher;
import com.yiyiaddon.feature.librarian.service.LibrarianOrchestrator;
import com.yiyiaddon.feature.librarian.service.MovementStatus;
import com.yiyiaddon.feature.librarian.ui.AutoLibrarianPage;
import com.yiyiaddon.platform.navigation.FarmNav;
import com.yiyiaddon.ui.page.ModulePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Set;

/**
 * 自动图书管理员模块（模块外壳）。
 *
 * <p>功能：自动寻路失业村民 → 检测岩浆块工位 → 放置讲台让村民转职图书管理员 →
 * <b>静默</b>读取交易列表（不显示交易界面）→ 命中目标附魔自动发包购买 →
 * 未命中拆除讲台刷新交易，循环往复。行为全部在
 * {@link LibrarianOrchestrator} 内，本类只做「事件订阅 + 运行参数装配 + 播报 + 自检」四件事
 * （与旧项目「模块只做外壳、编排器做行为」的分工一致）。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：模块名 {@code 自动图书管理员}、描述
 * {@code 自动寻路失业村民、放置讲台刷新交易、命中目标附魔自动购买，未命中自动拆台循环。详细参考下面使用说明。}
 * （旧 {@code :72}）、启动报告 4 行（旧 {@code reportStartupInfo :226-251}）、自检 5 类缺项
 * （旧 {@code :183-209}）、7 条状态播报（旧 {@code ModuleLogger.broadcastProgress :353-369}）、
 * 强制结束提示 {@code §c§l已强制结束}（旧 {@code :278}）、目标附魔解析失败文案
 * {@code 无法解析目标附魔: }（旧 {@code :324}）。</p>
 *
 * <p><b>框架 API 映射（旧 → 新）</b></p>
 * <ul>
 *   <li>{@code onActivate()} → {@link #onEnable()}；{@code onDeactivate()} → {@link #onDisable()}；</li>
 *   <li>旧 {@code toggle()} 关模块 → {@code ModuleManager.setEnabledSilently(MODULE_ID, false)}：旧项目
 *       {@code chatFeedback = false} 抑制了框架那条「已开启 / 已关闭」，而模块自己的「已强制结束」
 *       已经说明了原因，再播一条「已关闭」就是同一次状态变化两条提示；</li>
 *   <li>旧 {@code reportSelfCheck(selfCheck())} → 运行时的 {@link #selfCheck()}：缺项由
 *       {@link ModuleManager} 一次性多行播报并拦住启动，模块不再自己播报、不再自己关自己；</li>
 *   <li>旧 {@code if (mc.player == null || mc.level == null) { notifyError("必须在进入世界后才能启动模块。"); … }}
 *       → 由运行时统一把关（未进世界时 {@link #selfCheck()} 直接返回空列表，运行时给出同一条统一文案）；</li>
 *   <li>{@code @EventHandler TickEvent.Pre} → {@link #onTick(Minecraft)}（运行时按刻派发，只在启用时调用，
 *       因此旧首行的 {@code if (!isActive()) return;} 成为天然前提）；</li>
 *   <li>{@code @EventHandler OpenScreenEvent} → {@link #onOpenScreen(ClientEvent)}：载荷只带界面类名，
 *       {@code event.setCancelled(true)} → {@code event.cancel()}（与自动村民交易 / 自动农场同一等价改写）；</li>
 *   <li>旧 {@code Keybind.isPressed()} → 自研 {@code AddonKeybind#isPressed()} + 本类的「上一 tick 未按、
 *       这一 tick 按」跳变判定（{@link #lastPauseKeyDown}）；</li>
 *   <li>旧 {@code YiyiaddonModule.formatMessage(...)} → {@link ClientChat#send(String, String)}
 *       （前缀按第 110-113 条去掉 {@code yiyiaddon} 段，模块名白色加粗）；</li>
 *   <li>旧 {@code ModuleLogger.clean(...)} 的标点清理<b>逐字保留</b>（D5 拍板：属旧行为），
 *       因此玩家看到的播报不带任何中英标点，与旧项目完全一致。</li>
 * </ul>
 *
 * <p><b>静默交易</b>：模块照常让服务端打开交易菜单（协议要求），但取消容器屏显示，
 * 玩家看不到界面；{@code player.containerMenu} 仍正常同步，发包与读报价不受影响。
 * 背包（{@code InventoryScreen}）与创造背包（{@code CreativeModeInventoryScreen}）必须放行 ——
 * 拦掉后者会让 {@code RecipeBookComponent.book} 未初始化而 NPE 闪退（同一教训见自动村民交易）。</p>
 *
 * <p><b>与旧项目的差异登记</b>：① 无法解析的目标附魔 ID 由「抛异常」改为「播报 + 关闭模块」
 * （旧 {@code :324} 的 {@code orElseThrow} 会让启用过程直接失败，本项目改为同语义的可播报路径，D-15-09）；
 * ② 音效播放条件逐字照搬（只在 {@code COMPLETE_TARGET / FINISH} 两个状态发音，见
 * {@link SoundPlayer}，D8 拍板「照搬并登记现状」）。</p>
 */
public final class AutoLibrarianModule extends Module {

    /** 模块 ID，同时作为状态文件键与快捷键键名后缀 */
    public static final String MODULE_ID = "librarian";

    /** 播报前缀使用的模块名（旧项目模块名逐字） */
    public static final String MESSAGE_MODULE = "自动图书管理员";

    /**
     * 模块卡片图标：Material Symbols Rounded 的 {@code auto_stories}（翻开的书，对应「自动刷附魔书」）。
     *
     * <p><b>验真（开发习惯第 140 条）</b>：不是查表猜的码点 —— 用 {@code java.awt.Font.createFont}
     * 直接加载本模组打包的 {@code assets/yiyiaddon/fonts/MaterialSymbolsRounded.ttf}，
     * {@code canDisplay(0xE666)} 返回 true，并把该字形渲染成 PNG 目视确认为「翻开的书」
     * （同时确认 U+E86E / U+E666 / U+E0E0 三个候选都不是豆腐块，最终取语义最贴合的一个）。
     * 本项目既有模块无一占用该码点（既有图标：E798 / E8B8 / E8B6 / E8F4 / E65F / E25B / E002 /
     * EA79 / E1A1 / EF3D / EA12 / E663）。</p>
     */
    private static final String ICON = "\uE666";

    /** 状态播报流水上限：控制台「日志」页只看得到最近这么多条 */
    private static final int LOG_CAPACITY = 80;

    private final Minecraft mc = Minecraft.getInstance();

    /** 12 项设置的数据载体（文案与默认值来自旧项目，界面由控制台页面承载） */
    private final LibrarianSettings settings = new LibrarianSettings();

    /** 播报出口（实现 {@link DebugLoggerService}，编排器只按语义调用） */
    private final Messenger logger = new Messenger();

    /** 音效出口（实现 {@link DebugSoundService}） */
    private final SoundPlayer soundPlayer = new SoundPlayer();

    /** 运行上下文与编排器：每次启用重建，关闭时释放（旧 {@code :133-173} 同一节奏） */
    private LibrarianContext context;
    private LibrarianOrchestrator orchestrator;

    /**
     * 暂停键上一 tick 是否处于按下状态。
     *
     * <p>旧 {@code get().isPressed()} 只认一次「按下」事件（不是「当前是否按住」），
     * 这里自己保存上一刻状态来做同样的跳变判定；启用时按当前键位状态初始化，
     * 「进世界前就按住不放」不会被误判成一次新的按下。</p>
     */
    private boolean lastPauseKeyDown;

    /** 最近真正发进聊天的内容（新 → 旧），控制台「日志」页专用，不参与任何判定 */
    private final Deque<String> chatLog = new ArrayDeque<>(LOG_CAPACITY);

    public AutoLibrarianModule() {
        super(MODULE_ID, MESSAGE_MODULE, "automation",
            "自动寻路失业村民、放置讲台刷新交易、命中目标附魔自动购买，未命中自动拆台循环。详细参考下面使用说明。");
    }

    /** 英文名（模块中心检索用；旧项目模块只有中文名，这里取类名同形写法） */
    @Override
    public String name() {
        return "AutoLibrarian";
    }

    /** 分类内排序：自动化分类第六位（… → 自动附魔 → 自动图书管理员 → 自动村民交易） */
    @Override
    public int order() {
        return 60;
    }

    /** 模块卡片图标（见 {@link #ICON} 的验真记录） */
    @Override
    public String icon() {
        return ICON;
    }

    /**
     * 启动报告本身就是本次启用的完整结论，不再叠一条运行时的「已开启」
     * （等价旧项目 {@code chatFeedback = false} 对框架播报的抑制）。
     */
    @Override
    protected boolean suppressEnableAnnounce() {
        return true;
    }

    // ── 访问器（供控制台页面与选择器调用） ──

    public LibrarianSettings settings() {
        return settings;
    }

    /** 编排器当前状态；未启用时为 {@code null} */
    public LibrarianState currentState() {
        return orchestrator == null ? null : orchestrator.getState();
    }

    /** 运行上下文；未启用时为 {@code null} */
    public LibrarianContext currentContext() {
        return context;
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

    // ── 界面 ──

    @Override
    public ModulePage page() {
        return new AutoLibrarianPage(this);
    }

    // ── 日志（控制台「日志」页专用） ──

    /** 最近真正发进聊天的内容（新 → 旧） */
    public synchronized List<String> logs() {
        return new ArrayList<>(chatLog);
    }

    /** 清空控制台日志（只清历史，不影响运行状态） */
    public synchronized void clearLogs() {
        chatLog.clear();
    }

    /** 状态摘要（模块页与控制台概览共用同一句，禁止两处各拼一份） */
    public String statusSummary() {
        LibrarianState state = currentState();
        String stateText = !isEnabled() ? "§8未启用"
            : (state == null ? "§8未初始化" : "§f" + state.displayName());
        int targetCount = settings.targetEnchantments().size();
        return "§7当前状态 §8▸ " + stateText
            + " §8│ §7目标附魔 §8▸ §f" + (targetCount == 0 ? "未勾选" : targetCount + " 项")
            + " §8│ §7搜索半径 §8▸ §f" + settings.searchRadius + " 格"
            + " §8│ §7最高价格 §8▸ §f" + settings.maximumEmeraldPrice + " 绿宝石";
    }

    // ── 生命周期（旧 onActivate :122-175 / onDeactivate :253-263） ──

    @Override
    protected void onEnable() {
        // 暂停键跳变基准：以「本刻之前是否已按下」为起点，按住不放不会一开就停
        lastPauseKeyDown = settings.pauseKey.isPressed();

        List<EnchantmentTarget> targets = resolveTargets();
        if (targets.isEmpty()) {
            // 旧项目在这里抛 IllegalArgumentException（:322-324）导致启用直接失败；
            // 本项目改为「播报 + 静默关闭」，玩家看得到原因，也不会把异常丢进运行时日志
            notifyRaw("§c§l" + "无法解析目标附魔: " + String.join(", ", settings.targetEnchantments()));
            closeSilently();
            return;
        }

        LibrarianConfig defaults = LibrarianConfig.defaults();
        LibrarianConfig config = new LibrarianConfig(
            targets,
            settings.searchRadius,
            defaults.movementArrivalRadius(),
            settings.maximumEmeraldPrice,
            settings.professionTimeout,
            defaults.tradeScreenTimeoutTicks(),
            defaults.tradeSyncTimeoutTicks(),
            defaults.movementTimeoutTicks(),
            settings.actionDelay,
            settings.resetDelay,
            settings.removeTargetOnFound,
            settings.debugMode,
            settings.playNotificationSound
        );

        context = new LibrarianContext(new TargetProgress(targets));
        orchestrator = new LibrarianOrchestrator(
            config,
            context,
            new VillagerSearchProbe(logger, () -> settings.debugMode),
            new StationProbe(),
            new BaritoneMoveService(logger),
            new LecternOps(),
            new MerchantTradeOps(),
            new InventoryAccess(),
            new EnchantmentMatcher(),
            logger,
            soundPlayer
        );
        orchestrator.start();
        reportStartupInfo(targets);
    }

    /**
     * 关闭：收拾编排器（停移动 + 关交易 + 清上下文）。
     *
     * <p>旧 {@code onDeactivate} 还会 {@code notify("§c§l已关闭")}；本项目这条由运行时统一播报
     * （{@code ModuleManager.disable} 的同文案），模块不再重复播一遍。</p>
     */
    @Override
    protected void onDisable() {
        if (orchestrator != null) orchestrator.stop();
        orchestrator = null;
        context = null;
        lastPauseKeyDown = false;
    }

    /** 延后一帧静默关闭（避免在启用流程内重入运行时开关） */
    private void closeSilently() {
        mc.execute(() -> ModuleManager.setEnabledSilently(MODULE_ID, false));
    }

    // ── 事件（旧 @EventHandler 两处） ──

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK, ClientEventType.SCREEN_OPEN);
    }

    /**
     * 事件分派：{@code TICK} 只声明订阅（真正的每刻推进由运行时调 {@link #onTick(Minecraft)}），
     * 只有 {@code SCREEN_OPEN} 需要本模块判定。
     */
    @Override
    public void onEvent(ClientEvent event) {
        if (event == null) return;
        if (event.type() == ClientEventType.SCREEN_OPEN) onOpenScreen(event);
    }

    /**
     * 静默容器：运行中取消交易界面 / 箱子屏幕的显示（不抢鼠标），
     * 交易数据仍由 {@code player.containerMenu} 同步，选中与领取照常发包。
     *
     * <p>背包与创造模式背包必须放行：玩家手动按 E 打开背包不能被模块拦掉；
     * 创造背包被拦掉还会因 {@code RecipeBookComponent.book} 未初始化而 NPE 闪退。</p>
     */
    private void onOpenScreen(ClientEvent event) {
        if (mc.player == null || !isEnabled()) return;
        String screenClassName = event.payload();
        if (InventoryScreen.class.getName().equals(screenClassName)) return;
        if (CreativeModeInventoryScreen.class.getName().equals(screenClassName)) return;
        if (isContainerScreen(screenClassName)) event.cancel();
    }

    /** 该界面类名是否为原版容器界面（旧 {@code instanceof AbstractContainerScreen<?>} 的类名等价物） */
    private static boolean isContainerScreen(String screenClassName) {
        if (screenClassName == null || screenClassName.isBlank()) return false;
        try {
            return AbstractContainerScreen.class.isAssignableFrom(Class.forName(screenClassName));
        } catch (Throwable ignored) {
            return false;
        }
    }

    /**
     * 每刻推进（旧 {@code onTick :272-285} 顺序原样：先看暂停键，再推进编排器）。
     *
     * <p>旧首行 {@code if (!isActive()) return;} 由运行时保证：本方法只在模块启用时被调用。</p>
     */
    @Override
    public void onTick(Minecraft client) {
        boolean pauseDown = settings.pauseKey.isPressed();
        boolean pausePressed = pauseDown && !lastPauseKeyDown;
        lastPauseKeyDown = pauseDown;
        if (pausePressed) {
            // D2 拍板：照旧项目的实现（按下即强制结束并关闭模块），描述文本「切换暂停/继续，
            // 保留当前村民和附魔进度」逐字留在设置行的 tooltip 里
            notifyRaw("§c§l已强制结束");
            ModuleManager.setEnabledSilently(MODULE_ID, false);
            return;
        }
        if (orchestrator != null) orchestrator.tick();
    }

    // ── 自检（旧 selfCheck :183-218，5 类缺项文案逐字） ──

    /**
     * 启用前自检：收集全部缺项，交给运行时一次性多行播报并拦住启动。
     *
     * <p>未进入世界时不做自检（背包与 Baritone 都取不到有效状态），由运行时给出统一的
     * 「必须在进入世界后才能启动模块。」。</p>
     */
    @Override
    public List<String> selfCheck() {
        if (mc.player == null || mc.level == null) return List.of();

        List<String> missing = new ArrayList<>();
        if (settings.targetEnchantments().isEmpty()) {
            missing.add("§d目标附魔§f·未勾选");
        }
        if (!FarmNav.available()) {
            missing.add("§bBaritone§f·未安装或未启用");
        }
        if (countItem(Items.LECTERN) == 0) {
            missing.add("§a讲台§f·背包没有");
        }
        if (countItem(Items.BOOK) == 0) {
            missing.add("§a书§f·背包没有");
        }
        if (countItem(Items.EMERALD) == 0) {
            missing.add("§a绿宝石§f·背包没有");
        }
        return missing;
    }

    /** 背包 0~35 格内指定物品的总数量（旧 :196-206 同一口径；控制台概览页与自检共用一份统计） */
    public int countItem(Item item) {
        if (mc.player == null) return 0;
        int count = 0;
        for (int slot = 0; slot < 36; slot++) {
            var stack = mc.player.getInventory().getItem(slot);
            if (stack.is(item)) count += stack.getCount();
        }
        return count;
    }

    // ── 启动报告（旧 reportStartupInfo :226-251 整块逐字） ──

    /**
     * 启动播报：目标附魔（最多 3 个）/ 搜索半径 / 最高价格 / 命中策略，四行一次发出。
     *
     * <p>正文统一「标签　§8▸ 值」（全角空格 + §8▸），值走高亮函数
     * （文本 §a§l / 数值 §e§l / 功能 §b§l），与自动农场 / 自动挖矿的启动报告同一风格。</p>
     */
    private void reportStartupInfo(List<EnchantmentTarget> targets) {
        StringBuilder report = new StringBuilder();
        report.append("§a§l✓ 自动图书管理员 · 启动报告");

        // 目标附魔（最多显示前 3 个，其余折叠）
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < Math.min(3, targets.size()); i++) {
            if (i > 0) names.append("§f、");
            names.append(highlightText(targets.get(i).displayName() + " Lv." + targets.get(i).level()));
        }
        if (targets.size() > 3) {
            names.append("§f 等 ").append(highlightNumber(String.valueOf(targets.size()))).append("§f 种");
        }
        report.append("\n§7目标附魔　§8▸ ").append(names).append("§r");

        report.append("\n§7搜索半径　§8▸ ").append(highlightNumber(settings.searchRadius + " 格")).append("§r");
        report.append("\n§7最高价格　§8▸ ")
            .append(highlightNumber(settings.maximumEmeraldPrice + " 绿宝石")).append("§r");
        report.append("\n§7命中策略　§8▸ ")
            .append(highlightFunction(settings.removeTargetOnFound ? "找到后移除" : "找到后保留")).append("§r");

        notifyRaw(report.toString());
    }

    /** 物品/文本高亮（亮绿色粗体）—— 旧基类 {@code highlightText} 逐字 */
    private static String highlightText(String text) {
        return "§a§l" + (text == null ? "" : text) + "§r§f§l";
    }

    /** 数值高亮（黄色粗体）—— 旧基类 {@code highlightNumber} 逐字 */
    private static String highlightNumber(String text) {
        return "§e§l" + (text == null ? "" : text) + "§r§f§l";
    }

    /** 功能/模式高亮（亮青色粗体）—— 旧基类 {@code highlightFunction} 逐字 */
    private static String highlightFunction(String text) {
        return "§b§l" + (text == null ? "" : text) + "§r§f§l";
    }

    // ── 目标附魔解析（旧 createTarget :317-335） ──

    /**
     * 把设置里的附魔 ID 列表解析成目标（等级 = 该附魔的最高等级，显示名取本地化名）。
     *
     * <p>命中判据要求「报价的附魔等级 == 该附魔最高等级」，因此本模块一律按最高级刷取。</p>
     */
    private List<EnchantmentTarget> resolveTargets() {
        if (mc.level == null) return List.of();
        Registry<Enchantment> enchantments = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        List<EnchantmentTarget> targets = new ArrayList<>();
        for (String id : settings.targetEnchantments()) {
            Identifier identifier = Identifier.tryParse(id);
            if (identifier == null) return List.of();
            var holder = enchantments.get(ResourceKey.create(Registries.ENCHANTMENT, identifier));
            if (holder.isEmpty()) return List.of();
            Enchantment enchantment = holder.get().value();
            targets.add(EnchantmentTarget.resolved(
                identifier.toString(),
                enchantment.description().getString(),
                "minecraft:enchanted_book",
                enchantment.getMaxLevel()
            ));
        }
        return targets;
    }

    // ── 播报 ──

    /** 原始播报（不经标点清理）：启动报告与强制结束提示走这条，与旧项目 {@code notify} 同口径 */
    private void notifyRaw(String message) {
        ClientChat.send(MESSAGE_MODULE, message);
        recordLog(message);
    }

    /** 记一条日志：清掉前缀后入流水（口径照自动村民交易 {@code recordLog}） */
    private void recordLog(String message) {
        if (message == null) return;
        String firstLine = message.split("\n", 2)[0];
        String clean = firstLine.replace(ClientChat.prefix(MESSAGE_MODULE), "").strip();
        synchronized (chatLog) {
            while (chatLog.size() >= LOG_CAPACITY) chatLog.pollFirst();
            chatLog.addFirst(clean);
        }
    }

    /**
     * 编排器播报出口：状态 / 通知 / 调试 / 错误四通道，全部经旧项目同一条标点清理后发出。
     *
     * <p>清理口径逐字照搬旧 {@code ModuleLogger.clean}：抹掉 ASCII 标点与一批中文标点，
     * 因此玩家看到的正文没有标点（D5 拍板保留该旧行为，不做「顺手修正」）。</p>
     */
    private final class Messenger implements DebugLoggerService {
        /** 错误节流窗口（旧 {@code ERROR_THROTTLE_MILLIS = 2000L}） */
        private static final long ERROR_THROTTLE_MILLIS = 2000L;

        private String lastErrorMessage;
        private long lastErrorTimeMillis;
        /** 进度播报去重（旧 {@code lastNotifiedState}） */
        private String lastNotifiedState = "";

        @Override
        public void state(LibrarianState state, LibrarianContext currentContext, MovementStatus movementStatus) {
            if (settings.debugMode) {
                send("§7[调试] 状态 " + localizeState(state) + " 移动 " + localizeMovement(movementStatus));
            }
            broadcastProgress(state);
        }

        @Override
        public void info(String message) {
            if (settings.chatFeedback) send("§f§l" + message);
        }

        @Override
        public void debug(String message) {
            if (settings.debugMode) send("§7[调试] §f" + message);
        }

        @Override
        public void error(String message) {
            long now = System.currentTimeMillis();
            if (message.equals(lastErrorMessage) && now - lastErrorTimeMillis < ERROR_THROTTLE_MILLIS) return;
            lastErrorMessage = message;
            lastErrorTimeMillis = now;
            send("§c✗ " + message);
        }

        /** 7 条进度播报：只播有实质动作的工作状态，寻路 / 读取等过渡状态不播（旧 :353-369） */
        private void broadcastProgress(LibrarianState state) {
            if (!settings.chatFeedback) return;
            String message = switch (state) {
                case SEARCH_VILLAGER -> "§7正在搜索失业村民...";
                case FIND_LECTERN_POSITION -> "§7正在检测工位...";
                case BREAK_OBSTACLE -> "§7正在清除障碍方块...";
                case PLACE_LECTERN -> "§7正在放置讲台...";
                case WAIT_PROFESSION -> "§7等待村民成为图书管理员...";
                case BREAK_LECTERN -> "§7正在拆除讲台...";
                case RESET -> "§6⚠ 未命中目标 §8▸ 准备刷新交易";
                default -> null;
            };
            if (message == null) return;
            if (message.equals(lastNotifiedState)) return;
            lastNotifiedState = message;
            send(message);
        }

        /** 统一出口：清理标点 + 统一前缀（第 110-113 条），并落日志流水 */
        private void send(String message) {
            String cleaned = clean(message);
            ClientChat.send(MESSAGE_MODULE, cleaned);
            recordLog(cleaned);
        }

        /** 旧 {@code ModuleLogger.clean} 逐字：抹掉 ASCII 标点与常见中文标点 */
        private String clean(String message) {
            return message.replaceAll("[\\p{Punct}，。！？：；、（）【】「」《》“”‘’…]", "");
        }

        /** 状态中文名统一由枚举提供，避免模块与编排器各维护一份映射 */
        private String localizeState(LibrarianState state) {
            return state == null ? "未初始化" : state.displayName();
        }

        private String localizeMovement(MovementStatus status) {
            return status == null ? "未初始化" : status.displayName();
        }
    }

    /**
     * 音效出口：只在 {@code SUCCESS} 事件（{@code COMPLETE_TARGET} / {@code FINISH} 两个状态
     * 映射而来）发音，且受「提示音」开关控制 —— 与旧 {@code ModuleSound} 逐字一致。
     *
     * <p>设置描述写的是「找到目标和完成目标时播放提示音」，而旧实现只认 {@code SUCCESS}，
     * 即「找到目标附魔」（{@code SUCCESS_FOUND}）其实不发音。D8 拍板：照搬现状不改，
     * 差异登记在批次报告里（第 168 条：旧项目本来就一样的行为不得静默修正）。</p>
     */
    private final class SoundPlayer implements DebugSoundService {
        @Override
        public void play(DebugSoundEvent event) {
            if (event != DebugSoundEvent.SUCCESS) return;
            if (!settings.playNotificationSound) return;
            if (mc.player == null) return;
            mc.player.playSound(soundOf(settings.successSound), 1f, 1f);
        }

        /** 22 项中文音效 → 原版音效事件（逐条照搬旧 {@code ModuleSound} 的映射） */
        private SoundEvent soundOf(SuccessSound sound) {
            return switch (sound) {
                case BELL -> SoundEvents.BELL_BLOCK;
                case ATTACK_CRIT -> SoundEvents.PLAYER_ATTACK_CRIT;
                case CAT -> SoundEvents.CAT_AMBIENT_BABY.value();
                case THUNDER -> SoundEvents.LIGHTNING_BOLT_THUNDER;
                case EXPERIENCE_ORB -> SoundEvents.EXPERIENCE_ORB_PICKUP;
                case CHALLENGE_COMPLETE -> SoundEvents.UI_TOAST_CHALLENGE_COMPLETE;
                case PLAYER_LEVELUP -> SoundEvents.PLAYER_LEVELUP;
                case NOTE_PLING -> SoundEvents.NOTE_BLOCK_PLING.value();
                case CHEST_OPEN -> SoundEvents.CHEST_OPEN;
                case FIREWORK_BLAST -> SoundEvents.FIREWORK_ROCKET_BLAST;
                case VILLAGER_CELEBRATE -> SoundEvents.VILLAGER_CELEBRATE;
                case ZOMBIE_VILLAGER_CURE -> SoundEvents.ZOMBIE_VILLAGER_CURE;
                case GOAT_SCREAM -> SoundEvents.GOAT_SCREAMING_AMBIENT;
                case GHAST_SCREAM -> SoundEvents.GHAST_SCREAM;
                case ALLAY_AMBIENT -> SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
                case ENCHANTMENT_TABLE -> SoundEvents.ENCHANTMENT_TABLE_USE;
                case TRIDENT_THUNDER -> SoundEvents.TRIDENT_THUNDER.value();
                case PANDA_SNEEZE -> SoundEvents.PANDA_SNEEZE;
                case WARDEN_ROAR -> SoundEvents.WARDEN_ROAR;
                case DRAGON_GROWL -> SoundEvents.ENDER_DRAGON_GROWL;
                case END_PORTAL -> SoundEvents.END_PORTAL_SPAWN;
                case ELDER_GUARDIAN_CURSE -> SoundEvents.ELDER_GUARDIAN_CURSE;
            };
        }
    }
}
