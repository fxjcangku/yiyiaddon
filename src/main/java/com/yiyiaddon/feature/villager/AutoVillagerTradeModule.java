package com.yiyiaddon.feature.villager;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.villager.command.CunminCommand;
import com.yiyiaddon.feature.villager.config.VillagerTradeSettings;
import com.yiyiaddon.feature.villager.data.VillagerProfessionRegistry;
import com.yiyiaddon.feature.villager.fsm.VillagerTradeFSM;
import com.yiyiaddon.feature.villager.model.PipelineTask;
import com.yiyiaddon.feature.villager.model.VillagerProfessionChoice;
import com.yiyiaddon.feature.villager.model.VillagerTradeMode;
import com.yiyiaddon.feature.villager.model.VillagerTradeTarget;
import com.yiyiaddon.feature.villager.render.ContainerESP;
import com.yiyiaddon.feature.villager.repository.VillagerBindingStore;
import com.yiyiaddon.feature.villager.ui.AutoVillagerTradePage;
import com.yiyiaddon.platform.container.SilentContainer;
import com.yiyiaddon.platform.navigation.FarmNav;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Set;

/**
 * 自动村民交易模块
 * 
 * 功能：
 * · 原地交易模式（不寻路工作站，直接与身边村民交易，绿宝石不足/背包满自动补给/卸货）
 * · 寻路单点模式（Baritone 寻路到工作站，绿宝石不够自动去绿宝石箱补给、满包自动卸货）
 * · 多任务流水线模式（所有已选择物品的职业自动组成队列，完成 1 再做 2，可为不同村民）
 * · 交易通过真实打开村民交易界面发包（26.1.2 协议，无静默交易）
 * · 附魔书精准匹配（忽略等级）
 * · 价格限制
 * · 启动播报 + 每笔成功/失败提示 + 成功提示音
 * 
 * 使用流程：
 * 1. 选择模式
 * 2. 选择职业
 * 3. 选择目标物品
 * 4. 设置价格上限
 * 5. 启动模块
 *
 * <p><b>移植来源</b>：旧项目 {@code villager/AutoVillagerTradeModule}
 * （旧 {@code :65-835}，835 行）。包名换为 {@code com.yiyiaddon.feature.villager}，类名逐字保留；
 * 本类只做「事件订阅 + 运行配置同步 + 播报」三件事，真实交易行为全部在
 * {@link VillagerTradeFSM} 里，与旧项目「模块只做外壳、状态机做行为」的分工一致。</p>
 *
 * <p><b>用户交互资产</b>：模块名 {@code 自动村民交易}、描述
 * {@code 自动与村民交易，支持寻路}（原为旧 {@code :311} 的「自动与村民交易，支持原地和寻路模式。
 * 点击按钮查看说明。」，2026-09-21 去掉「说明按钮」指引并按「一行放得下的中文短注」重写 ——
 * 说明按钮已按第 210 条撤销、正文内嵌模块页，指针本身已是旧项目的事实，见 166 号复盘第十一节）、
 * 启动报告全文（旧 {@code announceStartup :386-428}）、自检 7 类缺项文案（旧 {@code :488-554}）、
 * 快速停止键提示 {@code §e检测到快速停止键，停止交易}（旧 {@code :463}）、
 * 启动失败文案 {@code 启动失败（状态机未就绪）}（旧 {@code :371}）、
 * 异常原因 {@code 玩家无效} / {@code 世界无效}（旧 {@code :470 / :476}）。</p>
 *
 * <p><b>框架 API 映射（旧 → 新）</b></p>
 * <ul>
 *   <li>{@code onActivate()} → {@link #onEnable()}；{@code onDeactivate()} → {@link #onDisable()}；</li>
 *   <li>{@code reportSelfCheck(selfCheck())} → 运行时的 {@link #selfCheck()}：缺项由
 *       {@link ModuleManager} 一次性多行播报并拦住启动，模块自己不再重复播报、不再自己关自己；</li>
 *   <li>{@code toggle()} → {@code ModuleManager.setEnabled(MODULE_ID, false)}（带统一「已关闭」播报，
 *       等价旧 {@code toggle()} 在 {@code chatFeedback == true} 下的行为）；</li>
 *   <li>旧 {@code chatFeedback = false} 抑制的重复播报 → {@code ModuleManager.setEnabledSilently(...)}
 *       （见 {@link #closeAfterResult()}）；</li>
 *   <li>{@code info()} / {@code error()} → {@link ClientChat} 播报（错误沿用旧 {@code notifyError}
 *       的 {@code §6§l} 前缀）+ 控制台日志环形缓冲；</li>
 *   <li>{@code @EventHandler TickEvent.Pre} → {@link #onTick(Minecraft)}（由运行时按刻派发，只在启用时调用，
 *       因此旧 {@code if (!isActive()) return;} 成为天然前提）；</li>
 *   <li>{@code @EventHandler OpenScreenEvent} → {@link #onOpenScreen(ClientEvent)}：事件载荷只带界面类名，
 *       {@code event.setCancelled(true)} → {@code event.cancel()}，判定口径与
 *       {@code AutoFarmModule#onOpenScreen} 一致；</li>
 *   <li>{@code @EventHandler Render3DEvent} → {@link WorldOverlay} 注册 / 注销（启用注册、关闭注销，
 *       旧 {@code isActive()} 守卫由注册时机天然保证）；</li>
 *   <li>旧 {@code Keybind.isPressed()} → 自研 {@link com.yiyiaddon.ui.keybind.AddonKeybind#isPressed()}
 *       （同一份语义：当前是否按下，键盘绑定还需修饰键按住）+ 本类自做的「上一 tick 未按、
 *       这一 tick 按」跳变判定（{@link #lastStopKeyDown}）；</li>
 *   <li>旧内嵌枚举 {@code Mode} / {@code ProfessionChoice} → 已落位的
 *       {@link VillagerTradeMode} / {@link VillagerProfessionChoice}（取值与中文名未改）。</li>
 * </ul>
 *
 * <p><b>设置项只承载不新造</b>：7 项可视静态（运行模式 / 绿宝石补给量(组) / 搜索范围(格) /
 * 挂机循环(按钮) / 补货等待(秒) / 目标职业 / 快速停止键）与 13 职业物品选择器、13 价格上限、
 * 13 多任务勾选、图书管理员附魔书全部走 {@link VillagerTradeSettings} 的字段与读写方法；
 * 界面不写在本类（旧 {@code getWidget / buildHelpContent / buildLocationCard / getDimensionDisplayName}
 * 由并行落地的 {@link AutoVillagerTradePage} 承载）。购买量 13 项按 D6 不做：旧项目该设置恒隐藏
 * （旧 {@code :243-254}），榨干模式下不参与退出判定。</p>
 *
 * <p><b>旧 {@code initializeItemSettings :193-280} / {@code updateItemSettings :285-288}</b>
 * （动态建 13 职业设置项、职业切换刷新可见性）在新项目里没有对应代码：设置载体
 * {@link VillagerTradeSettings} 的字段与分组访问器就是那批设置项，可见性由控制台页面自己按
 * 模式 / 职业勾选计算（旧 {@code isProfessionVisible :294-301} 的等价逻辑随界面一起走）。</p>
 *
 * <p><b>与旧项目的差异登记</b>：① 旧 {@code CunminCommand.getEmeraldChestPos() / getUnloadChestPos()}
 * 换成 {@link VillagerBindingStore}（同一份两箱绑定数据，新项目由 repository 承载）；② 旧
 * {@code BaritoneAPI.getProvider().getPrimaryBaritone() == null} 的可用性判定换成
 * {@link FarmNav#available()}（内部已吞掉异常，等价旧 {@code try/catch (Throwable)}）；
 * ③ 目标物品选择器在本项目存注册表 ID 字符串，构建目标列表时还原成 {@code Item}（旧项目直接持
 * {@code Item} 对象）。</p>
 */
public final class AutoVillagerTradeModule extends Module {

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀与世界渲染层标识 */
    public static final String MODULE_ID = "villager";

    /** 播报前缀使用的模块名：旧项目 {@code AutoVillagerTradeModule} 的模块名为 {@code 自动村民交易} */
    public static final String MESSAGE_MODULE = "自动村民交易";

    /**
     * 模块卡片图标：Material 符号 {@code storefront}（带遮阳棚的店面，对应「与村民做买卖」）。
     *
     * <p><b>验真（开发习惯第 140 条）</b>：旧项目没有模块图标可搬（该模块在旧项目里也是中文名 + 文字卡片），
     * 故按本项目既有做法自选一个码点。U+EA12 的验真方式是本机实测而非查表：
     * 用 {@code java.awt.Font.createFont} 直接加载本模组打包的
     * {@code assets/yiyiaddon/fonts/MaterialSymbolsRounded.ttf}，{@code canDisplay(0xEA12)} 返回 true，
     * 并把该字形渲染成 PNG 目视确认是「店面」而非豆腐块。此前本项目无任何位置占用该码点
     * （既有模块图标见 {@code WaterESPModule}/{@code AutoFarmModule} 等，无一使用 U+EA12）。</p>
     */
    private static final String ICON = "\uEA12";

    /**
     * 状态播报流水上限：控制台「日志」页只看得到最近这么多条
     */
    private static final int LOG_CAPACITY = 80;

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体（文案与默认值来自旧项目，界面由控制台页面承载） */
    private final VillagerTradeSettings settings = new VillagerTradeSettings();

    /** 村民交易状态机（旧 {@code :307-314} 同一份组装，行为全在状态机内） */
    private final VillagerTradeFSM fsm;

    /** 容器交易界面 ESP（旧 {@code :308 / :316}） */
    private final ContainerESP containerESP;

    /**
     * 快速停止键上一 tick 是否处于按下状态。
     *
     * <p>旧项目 {@code stopKey.get().isPressed()} 只认一次「按下」事件（不是「当前是否按住」），
     * 这里自己保存上一刻状态来做同样的跳变判定；启用时按当前键位状态初始化，
     * 「进世界前就按住不放」不会被误判成一次新的按下。</p>
     */
    private boolean lastStopKeyDown;

    /** 最近真正发进聊天的内容（新 → 旧），只留第一行；控制台「日志」页专用，不参与任何判定。
     *  口径照 {@code AutoFarmModule.chatLog}：容量 80、多行卡片只留标题行、
     *  去掉模块前缀（页面标题已表达）。 */
    private final Deque<String> chatLog = new ArrayDeque<>(LOG_CAPACITY);

    public AutoVillagerTradeModule() {
        super(MODULE_ID, MESSAGE_MODULE, "automation",
            "自动与村民交易，支持寻路");

        this.fsm = new VillagerTradeFSM();
        this.fsm.setLogger(this::info);

        // 容器 ESP 现读渲染设置（显示 / 颜色 / 渲染模式 / 字牌字号都由「点位」页写入本设置对象）
        this.containerESP = new ContainerESP(settings);
    }

    /** 英文名（模块中心的检索 / 列表用；旧项目模块只有中文名，这里取类名同形写法） */
    @Override
    public String name() {
        return "AutoVillagerTrade";
    }

    /** 分类内排序：自动化分类第七位（… → 自动图书管理员 → 自动村民交易） */
    @Override
    public int order() {
        return 70;
    }

    /** 模块卡片图标（{@link #ICON}）；此前本模块没有这条覆盖，模块中心里图标位是空的 */
    @Override
    public String icon() {
        return ICON;
    }

    // ── 访问器（供控制台页面、指令与渲染层调用） ──

    public VillagerTradeSettings settings() {
        return settings;
    }

    public VillagerTradeFSM fsm() {
        return fsm;
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

    /** 立即写回设置（界面改动即时生效） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    // ── 界面与指令 ──

    @Override
    public ModulePage page() {
        return new AutoVillagerTradePage(this);
    }

    @Override
    public List<ClientCommand> commands() {
        return List.of(new CunminCommand());
    }

    // ── 日志（控制台「日志」页专用） ──

    /** 最近真正发进聊天的内容（新 → 旧；控制台「日志」页专用） */
    public synchronized List<String> logs() {
        return new ArrayList<>(chatLog);
    }

    /** 清空控制台日志（只清历史，不影响状态机） */
    public synchronized void clearLogs() {
        chatLog.clear();
    }

    // ── 生命周期（旧 onActivate :322-378 / onDeactivate :430-433） ──

    @Override
    protected void onEnable() {
        // 快速停止键的跳变基准：以「本刻之前是否已按下」为起点，按住不放不会一开就停
        lastStopKeyDown = settings.stopKey.isPressed();

        VillagerProfession prof = getProfessionEnum();
        List<VillagerTradeTarget> targets = buildTargets();

        // 多任务模式：所有「已选择物品」的职业自动组成队列（顺序=职业枚举顺序）
        List<PipelineTask> pipelineTasks = settings.mode == VillagerTradeMode.PIPELINE
            ? buildPipelineTasks() : null;

        String profName = settings.profession.name();
        int maxPrice = settings.priceLimit(profName);
        int quantity = VillagerTradeSettings.SUPPLY_GROUPS * 64;

        // 完成 / 出错时自动关闭模块（结果日志由状态机 logger 已输出，这里只负责关模块）
        fsm.setCompleteHandler(summary -> closeAfterResult());
        fsm.setErrorHandler(reason -> closeAfterResult());

        fsm.configure(prof, targets, maxPrice, 32, quantity);
        fsm.setSupplyStacks(settings.emeraldSupplyStacks);
        fsm.setSearchRange(settings.searchRange);
        fsm.setIdleLoop(settings.idleLoop);
        fsm.setRestockWaitTicks(settings.restockWaitSeconds * 20);

        // ESP：注册世界渲染层，关闭时注销
        WorldOverlay.register(MODULE_ID, this::renderLayer);

        // 启动播报：模式 / 职业 / 目标物品 / 价格上限 / 购买总量（三种模式都要）
        announceStartup(pipelineTasks, quantity);

        boolean started;
        switch (settings.mode) {
            case LOCAL -> started = fsm.start(VillagerTradeMode.LOCAL);
            case SINGLE_PATH -> started = fsm.start(VillagerTradeMode.SINGLE_PATH);
            case PIPELINE -> started = fsm.startPipeline(pipelineTasks);
            default -> started = false;
        }

        if (!started) {
            error("启动失败（状态机未就绪）");
            mc.execute(() -> ModuleManager.setEnabledSilently(MODULE_ID, false));
        }
    }

    @Override
    protected void onDisable() {
        WorldOverlay.unregister(MODULE_ID);
        fsm.stop();
        lastStopKeyDown = false;
    }

    /**
     * 状态机给出最终结果后的自动关闭（旧 {@code :337-351} 的 {@code chatFeedback} 口径）。
     *
     * <p>为什么延后一帧：结果回调是在 {@code fsm.tick()} 栈内触发的，就地关模块会在状态机自己的
     * tick 里重入 {@code stop()}；旧项目同样用 {@code mc.execute} 延后，保持等价。
     * 为什么静默关：结果文案（交易完成 / 运行出错）状态机已经播报，旧项目用
     * {@code chatFeedback = false} 抑制了这一条统一开关播报，这里用
     * {@link ModuleManager#setEnabledSilently} 得到同样的「不重复刷屏」效果。</p>
     */
    private void closeAfterResult() {
        mc.execute(() -> {
            if (isEnabled()) ModuleManager.setEnabledSilently(MODULE_ID, false);
        });
    }

    // ── 事件（旧 @EventHandler 三处） ──

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK, ClientEventType.SCREEN_OPEN);
    }

    /**
     * 事件分派。
     *
     * <p>{@code TICK} 只声明订阅（与自动农场 / 自动挖矿同一写法），真正的每刻推进由运行时调
     * {@link #onTick(Minecraft)}，故这里不处理；只有 {@code SCREEN_OPEN} 需要本模块判定。</p>
     */
    @Override
    public void onEvent(ClientEvent event) {
        if (event == null) return;
        switch (event.type()) {
            case SCREEN_OPEN -> onOpenScreen(event);
            default -> {
            }
        }
    }

    /**
     * 静默容器：交易运行中打开村民交易界面/箱子屏幕时取消显示（不抢鼠标），
     * 交易界面数据仍由 mc.player.containerMenu 同步，SelectTrade/取绿宝石照常发包。
     *
     * <p>界面判据（背包放行 / 容器界面 / 传送加载界面劫持）统一走
     * {@link SilentContainer}，本模块只保留自己的相位复位出口
     * {@link VillagerTradeFSM#onPlayerInventoryOpened()}（第 169 条：同类逻辑只留一份）。</p>
     */
    private void onOpenScreen(ClientEvent event) {
        if (mc.player == null) return;
        String screenClassName = event.payload();
        if (!isEnabled()) return;

        // 玩家自己开着界面时，原版传送会把「加载地形中」无条件盖上来（用户 2026-09-19）：拦掉
        if (SilentContainer.isLevelLoadingHijack(screenClassName)) {
            event.cancel();
            return;
        }
        // 背包放行：玩家按 E 必须能开背包（生存 / 创造都算）。收掉我方静默容器并退回开箱相位，
        // 否则玩家在背包里的点击会按箱子的 containerId 发出去（错位、丢物品）
        if (SilentContainer.isPlayerInventory(screenClassName)) {
            fsm.onPlayerInventoryOpened();
            return;
        }
        // 只有「本模块自己在用容器 / 菜单」时才静默（用户 2026-09-19）：挂机时玩家手动去开自己的箱子，
        // 界面必须照常显示，不能被当成「模块自己在开界面」拦掉
        if (fsm.isUsingContainerScreen() && SilentContainer.isContainerScreen(screenClassName)) {
            // 玩家手动开的箱子：压掉 + 收掉那个容器（真不给开）+ 动作栏提示
            SilentContainer.rejectPlayerContainer();
            event.cancel();
        }
    }

    /**
     * 每刻推进（旧 {@code onTick :457-482} 顺序原样）。
     *
     * <p>旧首行 {@code if (!isActive()) return;} 由运行时保证：本方法只在模块启用时被调用。</p>
     */
    @Override
    public void onTick(Minecraft client) {
        // 检查快速停止键：绑定值在设置里（自研 AddonKeybind，照旧框架 Keybind 语义），
        // 只在「上一 tick 未按、这一 tick 按」的跳变时触发——旧实现直接调 isPressed()，
        // 触发后模块随即关闭、onTick 不再跑，等效只生效一次；加跳变判定是为了
        // 「按住不放时重新启用模块」不会立刻又被停掉。
        boolean stopDown = settings.stopKey.isPressed();
        boolean stopPressed = stopDown && !lastStopKeyDown;
        lastStopKeyDown = stopDown;
        if (stopPressed) {
            info("§e检测到快速停止键，停止交易");
            ModuleManager.setEnabled(MODULE_ID, false);
            return;
        }

        // 检查异常情况
        if (client.player == null) {
            fsm.handleException("玩家无效");
            ModuleManager.setEnabled(MODULE_ID, false);
            return;
        }

        if (client.level == null) {
            fsm.handleException("世界无效");
            ModuleManager.setEnabled(MODULE_ID, false);
            return;
        }

        fsm.tick();
    }

    // ── 世界渲染（旧 onRender3D :435-439） ──

    /**
     * 由 {@link WorldOverlay} 每帧回调（模块启用时注册、关闭时注销）。
     *
     * <p>旧 {@code if (!isActive()) return;} 由注册时机保证：只有启用期间才会被回调。</p>
     */
    private void renderLayer(EspRenderer renderer) {
        containerESP.render(renderer);
    }

    // ── 播报 ──

    /** 普通信息（旧基类 {@code info} 的等价物），同时落状态播报流水 */
    public void info(String message) {
        if (message == null || message.isBlank()) return;
        ClientChat.send(MESSAGE_MODULE, message);
        recordLog(message);
    }

    /** 错误：橙色加粗（旧 {@code notifyError} 的等价物） */
    public void error(String message) {
        ClientChat.send(MESSAGE_MODULE, "§6§l" + message);
        recordLog(message);
    }

    /** 记一条日志：只留第一行，并去掉统一前缀（口径照 {@code AutoFarmModule.recordLog}） */
    private void recordLog(String message) {
        String firstLine = message.split("\n", 2)[0];
        String clean = firstLine.replace(ClientChat.prefix(MESSAGE_MODULE), "").strip();
        synchronized (chatLog) {
            while (chatLog.size() >= LOG_CAPACITY) chatLog.pollFirst();
            chatLog.addFirst(clean);
        }
    }

    // ── 启动报告（旧 announceStartup :386-428 整块逐字） ──

    /**
     * 启动播报：把本次运行参数完整打印到聊天栏，三个模式通用。
     *
     * 整份报告合并成一个消息块输出（一条 info 多行），只带一次模块前缀，
     * 正文统一「标签 + §8▸ + 值」结构。多任务模式逐条列出每个任务；原地模式附带自行管理提示。
     *
     * @param quantity 旧实现即未参与拼接（旧 {@code :386-428} 同），保留参数以与旧调用点逐字对应
     */
    private void announceStartup(List<PipelineTask> pipelineTasks, int quantity) {
        StringBuilder report = new StringBuilder();
        report.append("§a§l✓ 自动村民交易 · 启动报告");

        // 默认榨干：始终展示，覆盖购买总量的语义
        report.append("\n§7交易模式　§8▸ ").append(highlightText("榨干模式"))
            .append("§r§f（无视购买总量，售罄/锁死才收工）");

        if (settings.mode == VillagerTradeMode.PIPELINE) {
            // 多任务模式：逐条列出每个任务的职业与参数
            report.append("\n§7执行模式　§8▸ ").append(highlightText(settings.mode.toString())).append("§r")
                .append("§f（共 ").append(highlightText(String.valueOf(pipelineTasks.size())))
                .append("§r§f 个任务，按顺序执行）");
            for (int i = 0; i < pipelineTasks.size(); i++) {
                PipelineTask task = pipelineTasks.get(i);
                List<String> names = new ArrayList<>();
                for (VillagerTradeTarget t : task.getTargets()) names.add(t.getDisplayName());
                report.append("\n§7任务 ").append(String.valueOf(i + 1)).append("　§8▸ §f职业=")
                    .append(highlightText(VillagerProfessionRegistry.getDisplayName(task.getProfession()))).append("§r")
                    .append("§f · 目标=").append(highlightText(String.join(",", names))).append("§r")
                    .append("§f · 价格≤").append(highlightText(String.valueOf(task.getMaxPrice()))).append("§r")
                    .append("§f · 总量=").append(highlightText("不限(榨干)")).append("§r");
            }
        } else {
            // 单职业模式：职业 / 目标 / 价格 / 总量 一行一项
            List<String> names = new ArrayList<>();
            for (VillagerTradeTarget t : buildTargets()) names.add(t.getDisplayName());
            report.append("\n§7执行模式　§8▸ ").append(highlightText(settings.mode.toString())).append("§r");
            report.append("\n§7交易职业　§8▸ ").append(highlightText(settings.profession.name())).append("§r");
            report.append("\n§7目标物品　§8▸ ").append(highlightText(String.join(",", names))).append("§r");
            report.append("\n§7价格上限　§8▸ ")
                .append(highlightText(String.valueOf(settings.priceLimit(settings.profession.name())))).append("§r");
            report.append("\n§7购买总量　§8▸ ").append(highlightText("不限(榨干)")).append("§r");
            if (settings.mode == VillagerTradeMode.LOCAL) {
                report.append("\n§e⚠ 原地模式　§8▸ 请靠近").append(highlightText(settings.profession.name())).append("§r§f村民（约 3 格内）")
                    .append("\n§e⚠ 玩家自主　§8▸ 只自动交易，绿宝石/背包由你手动管理");
            }
            if (settings.idleLoop && settings.mode != VillagerTradeMode.LOCAL) {
                report.append("\n§d⚠ 挂机循环　§8▸ 榨干后等待补货 ")
                    .append(highlightText(settings.restockWaitSeconds + " 秒")).append("§r§f 自动循环");
            }
        }

        info(report.toString());
    }

    /**
     * 物品/文本高亮（亮绿色粗体）——旧基类 {@code YiyiaddonModule.highlightText :168-170} 逐字，
     * 用于物品名、职业名、价格等值。
     *
     * <p>本项目的 {@code CommandMessageFormatter} 只有「整字段上色」的 {@code highlight(标签, 值)}，
     * 没有「夹在行内任意位置的高亮包裹」，故照 {@code AutoFarmModule} 的同一写法在本类内实现一份。</p>
     */
    private static String highlightText(String text) {
        return "§a§l" + (text == null ? "" : text) + "§r§f§l";
    }

    /**
     * 功能/模式高亮（亮青色粗体）——旧基类 {@code YiyiaddonModule.highlightFunction :173-175} 逐字。
     *
     * <p>本类现有正文（启动报告）只用 {@link #highlightText}；这一份与旧基类同形保留，
     * 避免后续补播报时再各写一套色码包装（旧基类通用件，不是新增功能）。</p>
     */
    private static String highlightFunction(String text) {
        return "§b§l" + (text == null ? "" : text) + "§r§f§l";
    }

    // ── 自检（旧 selfCheck :484-554，7 类缺项文案逐字） ──

    /**
     * 启用前自检：收集全部缺项，交给运行时一次性多行播报并拦住启动。
     *
     * <p>收集全部而不是遇到第一个就返回，用户一次就能看到还差什么。
     * 未进入世界时不做自检（旧实现的村民距离检测本就有 {@code player != null && level != null}
     * 前置条件，这里直接提前返回，与自动农场 / 自动挖矿同一分工）。</p>
     */
    @Override
    public List<String> selfCheck() {
        if (mc.player == null || mc.level == null) return List.of();

        List<String> missing = new ArrayList<>();

        // 1. 职业验证
        VillagerProfession prof = getProfessionEnum();
        if (prof == null) {
            missing.add("§e目标职业§f·未选择");
        }

        // 2. 目标物品验证（多任务模式校验整个队列 13 个职业，而不是当前职业）
        List<PipelineTask> pipelineTasks = settings.mode == VillagerTradeMode.PIPELINE ? buildPipelineTasks() : null;
        if (settings.mode == VillagerTradeMode.PIPELINE) {
            if (pipelineTasks == null || pipelineTasks.isEmpty()) {
                missing.add("§e多任务§f·未勾选任何职业（请在「多任务职业」组勾选并选好物品）");
            }
        } else if (buildTargets().isEmpty()) {
            missing.add("§e目标物品§f·未选择");
        }

        // 3. 容器绑定检测（三种模式都需要：绿宝石不足/背包满时自动补给/卸货）
        if (VillagerBindingStore.getEmeraldChestPos() == null) {
            missing.add("§a绿宝石箱§f·未绑定");
        }
        if (VillagerBindingStore.getUnloadChestPos() == null) {
            missing.add("§b成品交易箱§f·未绑定");
        }

        // 5. 目标职业村民检测：搜索半径内必须存在目标职业村民
        //    原地模式用实际交易交互距离 3.2 格（与状态机 isValidTarget 一致）；
        //    寻路/多任务模式与状态机一致，使用可配置的搜索范围。多任务模式对队列里每个职业都检测一遍
        List<VillagerProfession> checkProfessions = new ArrayList<>();
        if (settings.mode == VillagerTradeMode.PIPELINE && pipelineTasks != null) {
            for (PipelineTask task : pipelineTasks) {
                if (!checkProfessions.contains(task.getProfession())) {
                    checkProfessions.add(task.getProfession());
                }
            }
        } else if (prof != null) {
            checkProfessions.add(prof);
        }

        double radius = settings.mode == VillagerTradeMode.LOCAL ? 3.2 : settings.searchRange;
        for (VillagerProfession p : checkProfessions) {
            boolean found = !mc.level.getEntitiesOfClass(Villager.class,
                mc.player.getBoundingBox().inflate(radius),
                v -> isProfessionMatch(v, p)
            ).isEmpty();
            if (!found) {
                missing.add(String.format("§e%s村民§f·%.0f 格内未找到%s",
                    VillagerProfessionRegistry.getDisplayName(p), radius,
                    settings.mode == VillagerTradeMode.LOCAL ? "，请靠近村民（原地模式需站在村民旁约 3 格）" : ""));
            }
        }

        // 6. Baritone 验证（补给/卸货寻路都需要，三种模式通用）
        //    FarmNav.available() 内部已吞掉异常，等价旧 try/catch(Throwable) 的两条同文案缺项
        if (!FarmNav.available()) {
            missing.add("§cBaritone§f·未安装或未启用");
        }

        return missing;
    }

    /**
     * 村民职业是否匹配目标职业（职业数据异常时视为不匹配）
     */
    private static boolean isProfessionMatch(Villager villager, VillagerProfession prof) {
        if (villager == null || !villager.isAlive() || prof == null) return false;
        try {
            // 26.1.2：VillagerProfession 是 Record，常量是 ResourceKey，
            // 必须用注册表 Identifier 比较，value().equals() 会把傻子/失业村民误匹配进来
            Identifier targetId = BuiltInRegistries.VILLAGER_PROFESSION.getKey(prof);
            return targetId != null && villager.getVillagerData().profession().is(targetId);
        } catch (Exception e) {
            return false;
        }
    }

    // ── 运行参数构建（旧 :571-661，语义逐条一致） ──

    /**
     * 获取当前选择的职业枚举
     */
    private VillagerProfession getProfessionEnum() {
        VillagerProfessionChoice choice = settings.profession;
        return VillagerProfessionRegistry.getProfessionByDisplayName(choice.name());
    }

    /**
     * 构建当前职业的目标列表
     */
    private List<VillagerTradeTarget> buildTargets() {
        return buildTargetsFor(settings.profession);
    }

    /**
     * 构建指定职业的目标列表（多任务队列生成时复用）。
     *
     * <p>本项目选择器存注册表 ID 字符串（旧项目直接持 {@code Item} 对象），故先还原成物品：
     * 解析不出来的 ID（旧档残留 / 拼错 / 卸载模组后的残留）直接跳过——旧项目的选择器已被白名单
     * 过滤过，不存在这种输入，这里不猜、不补默认物品。附魔书部分逐条照旧 {@code :609-632}：
     * 动态附魔注册表必须走 level 的 {@code registryAccess} 解析本地化名，取不到回落注册表路径。</p>
     */
    private List<VillagerTradeTarget> buildTargetsFor(VillagerProfessionChoice choice) {
        List<VillagerTradeTarget> targets = new ArrayList<>();

        VillagerProfession prof = VillagerProfessionRegistry.getProfessionByDisplayName(choice.name());
        if (prof == null) return targets;

        String profName = choice.name();
        boolean isLibrarian = profName.equals("图书管理员");

        // 物品显示名走本地化（跟随客户端语言），不再用英文注册表 ID（旧 :604 口径）
        for (String itemId : settings.itemTargets(profName)) {
            Item item = itemOf(itemId);
            if (item == null) continue;
            String displayName = item.getDefaultInstance().getHoverName().getString();
            targets.add(new VillagerTradeTarget(item, displayName));
        }

        if (isLibrarian) {
            // 附魔是动态注册表，必须走 level 的 registryAccess 解析本地化名（回退到命名空间 ID）
            var enchantRegistry = (mc.level != null)
                ? mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                : null;
            for (String enchantmentId : settings.librarianEnchantments()) {
                Identifier identifier = Identifier.tryParse(enchantmentId);
                if (identifier == null) continue;
                // 只保留村民真能刷出来的附魔类型（白名单之外的已选值丢弃）
                if (!VillagerProfessionRegistry.getLibrarianEnchantments().contains(identifier.getPath())) continue;

                ResourceKey<Enchantment> enchantment = ResourceKey.create(Registries.ENCHANTMENT, identifier);
                String enchantName = (enchantRegistry != null)
                    ? enchantRegistry.get(enchantment)
                        .map(holder -> holder.value().description().getString())
                        .orElse(identifier.getPath())
                    : identifier.getPath();

                VillagerTradeTarget target = new VillagerTradeTarget(
                    Items.ENCHANTED_BOOK,
                    "附魔书·" + enchantName
                );
                target.setEnchantmentId(enchantmentId);
                targets.add(target);
            }
        }

        return targets;
    }

    /**
     * 构建多任务队列：只遍历「多任务职业组」里勾选的职业（且该职业已选物品）。
     * 顺序 = 职业枚举顺序（盔甲匠 → 武器匠）；每个任务独立价格上限与购买总量，
     * 由状态机 NEXT_TASK 逐个推进：完成任务 1 后自动开始任务 2，可为不同职业村民。
     */
    private List<PipelineTask> buildPipelineTasks() {
        List<PipelineTask> tasks = new ArrayList<>();
        for (VillagerProfessionChoice choice : VillagerProfessionChoice.values()) {
            String profName = choice.name();
            // 未在「多任务职业」勾选的职业直接跳过
            if (!settings.isPipelineSelected(profName)) continue;

            List<VillagerTradeTarget> targets = buildTargetsFor(choice);
            if (targets.isEmpty()) continue;

            VillagerProfession prof = VillagerProfessionRegistry.getProfessionByDisplayName(profName);
            if (prof == null) continue;

            int price = settings.priceLimit(profName);
            // 购买量按 D6 不落设置：旧项目该行恒隐藏且默认 1 组（64 件），榨干模式下不参与退出判定
            int qty = VillagerTradeSettings.SUPPLY_GROUPS * 64;
            tasks.add(new PipelineTask(prof, targets, price, qty));
        }
        return tasks;
    }

    /** 注册表 ID → 物品；解析失败（旧档残留 / 拼写错误 / 非物品 ID）返回 {@code null} */
    private static Item itemOf(String itemId) {
        if (itemId == null || itemId.isBlank()) return null;
        Identifier identifier = Identifier.tryParse(itemId);
        if (identifier == null) return null;
        Item item = BuiltInRegistries.ITEM.getValue(identifier);
        return item == null || item == Items.AIR ? null : item;
    }
}
