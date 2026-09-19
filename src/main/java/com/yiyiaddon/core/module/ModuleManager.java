package com.yiyiaddon.core.module;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandRegistry;
import com.yiyiaddon.config.ModuleStateConfig;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.event.EventDispatcher;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.service.ActivityLog;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.ModuleScreen;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 模块管理器：模块生命周期的唯一调度者。
 *
 * <p>职责：注册模块、初始化、启用与关闭、状态恢复与保存、每刻派发、事件订阅退订、异常隔离。
 * 界面、指令、快捷键都只与本类交互，不直接操作模块内部状态。</p>
 *
 * <p><b>异常隔离</b>：模块在初始化 / 自检 / 启用 / 关闭 / 每刻 / 事件回调任一环节抛出异常，
 * 都会被本类捕获：输出中文错误、自动关闭该模块、写回状态，其余模块不受影响。初始化阶段出错的
 * 模块会被永久标记为不可用，避免每次启动重复报错。</p>
 *
 * <p><b>恢复与等待</b>：状态文件记录为启用、但自检条件暂不满足（例如尚未进入世界）的模块，
 * 不会被写回「未启用」，而是进入等待队列，在进入世界时自动开启。否则「上次开着、下次启动时
 * 还在主菜单」就会把玩家的开关状态抹掉。</p>
 *
 * <p><b>启动顺序</b>：载入状态配置 → 注册模块 → 初始化并注册界面条目与指令 → 装配快捷键 →
 * 恢复启用状态 → 注册运行时事件订阅 → 提供「已启用模块」来源给后端统计。</p>
 */
public final class ModuleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/module");

    /** 运行时自身的事件订阅所有者 */
    private static final String RUNTIME_OWNER = "core.module.runtime";

    private static final Map<String, Module> BY_ID = new LinkedHashMap<>();

    /** 初始化阶段失败、永久不可用的模块 */
    private static final Set<String> BROKEN = new LinkedHashSet<>();

    /** 状态为启用但自检未通过、等待条件满足后自动开启的模块 */
    private static final Set<String> PENDING = new LinkedHashSet<>();

    /** 等待队列的重试间隔（客户端刻） */
    private static final int PENDING_RETRY_TICKS = 20;

    /**
     * 各模块的出厂设置快照：装配期（任何 {@code loadSettings} 之前）序列化一次，供「恢复默认」整份读回。
     *
     * <p>存快照而不是给每个模块写一份 {@code reset()}：默认值本来就写在各自的设置类字段初始化里，
     * 再手写一遍必然有抄错、漏改的风险（第 173 条要求 saveSettings 覆盖全部设置项、loadSettings 一一对应，
     * 快照正好是这条要求的免费产物）。</p>
     */
    private static final Map<String, JsonObject> FACTORY_DEFAULTS = new LinkedHashMap<>();

    private static int pendingRetryCounter;

    private static boolean bootstrapped;

    private ModuleManager() {
    }

    /** 装配全部模块；重复调用无效 */
    public static void bootstrap(List<Module> modules) {
        if (bootstrapped) return;
        bootstrapped = true;

        ModuleStateConfig.load();
        collect(modules);
        initializeAll();

        ClientEventBus.setFailureHandler(ModuleManager::onEventFailure);
        ClientEventBus.subscribe(RUNTIME_OWNER, ClientEventType.TICK, event -> tickAll(Minecraft.getInstance()));
        ClientEventBus.subscribe(RUNTIME_OWNER, ClientEventType.JOIN_SERVER, event -> enablePending());
        ClientEventBus.subscribe(RUNTIME_OWNER, ClientEventType.DISCONNECT,
                event -> EventDispatcher.clearPendingPackets());

        ModuleKeybinds.install();
        restoreStates();
        GameProbe.setModuleSource(ModuleManager::enabledDisplayNames);
    }

    // ── 注册与初始化 ──

    private static void collect(List<Module> modules) {
        if (modules == null) return;
        for (Module module : modules) {
            if (module == null) continue;
            if (BY_ID.putIfAbsent(module.id(), module) != null) {
                LOGGER.warn("模块 ID 重复，保留先注册的一个：{}", module.id());
            }
            snapshotFactoryDefaults(module);
        }
    }

    /**
     * 记下模块的「出厂设置」快照。
     *
     * <p><b>时机是唯一的关键</b>：此刻模块刚构造完、{@link #restoreStates()} 还没跑，字段里就是代码里写的默认值，
     * 因此 {@code saveSettings} 序列化出来的就是一份完整的出厂配置。之后玩家怎么改都不影响它。</p>
     *
     * <p>失败只是让该模块没有「恢复默认」，不影响装配流程：日志记一笔、快照不存。</p>
     */
    private static void snapshotFactoryDefaults(Module module) {
        JsonObject snapshot = new JsonObject();
        try {
            module.saveSettings(snapshot);
        } catch (Throwable error) {
            LOGGER.error("模块 {} 的出厂设置无法序列化，该模块将不提供「恢复默认」", module.id(), error);
            return;
        }
        FACTORY_DEFAULTS.put(module.id(), snapshot);
    }

    /**
     * 把模块的设置整份恢复为出厂值并立刻落盘（控制台底部「恢复默认」用）。
     *
     * <p>走的是「装配期快照 → {@code loadSettings} 整份读回 → {@link #saveSettings(Module)} 写盘」这条路，
     * 因此不需要每个模块各写一份 reset：凡是按第 173 条做到「saveSettings 覆盖全部设置项、
     * loadSettings 与之一一对应」的模块，出厂值天然与代码里的默认值同源。点位 / 名单这类数据也在快照里，
     * 所以会被一起清空（界面上的确认弹窗已明说）。</p>
     *
     * @return 是否成功写入（模块没快照、或读回 / 落盘抛异常时为 false）
     */
    public static boolean resetToDefaults(Module module) {
        if (module == null) return false;
        JsonObject defaults = FACTORY_DEFAULTS.get(module.id());
        if (defaults == null) {
            LOGGER.warn("模块 {} 没有出厂设置快照，「恢复默认」跳过", module.id());
            return false;
        }
        try {
            // deepCopy：loadSettings 的实现可能持有传入对象的引用，不能让快照本身被改写
            module.loadSettings(defaults.deepCopy());
        } catch (Throwable error) {
            LOGGER.error("模块 {} 恢复默认失败", module.id(), error);
            ClientChat.send(module.displayName(), "§c恢复默认失败：" + describe(error));
            return false;
        }
        boolean saved = saveSettings(module);
        if (saved) ClientChat.send(module.displayName(), "§a已恢复默认设置");
        return saved;
    }

    private static void initializeAll() {
        for (Module module : BY_ID.values()) {
            if (!runSafely(module, "初始化", module::onInitialize)) {
                BROKEN.add(module.id());
                continue;
            }
            ModuleEntries.publish(module);
            for (ClientCommand command : safeCommands(module)) {
                CommandRegistry.register(command);
            }
        }
    }

    private static List<ClientCommand> safeCommands(Module module) {
        try {
            List<ClientCommand> commands = module.commands();
            return commands == null ? List.of() : commands;
        } catch (Throwable error) {
            LOGGER.error("模块 {} 声明指令时异常", module.id(), error);
            return List.of();
        }
    }

    private static void restoreStates() {
        for (Module module : BY_ID.values()) {
            // 装配期通常还没进世界，隔离模块的 settingsScope() 返回 null，读到的就是全局模板；
            // 进世界后由模块自己调 reloadScopedSettings 换成那一份（见 Module#settingsScope()）。
            JsonObject settings = ModuleStateConfig.settingsOf(module.id(), module.settingsScope());
            runSafely(module, "载入设置", () -> module.loadSettings(settings));
            if (BROKEN.contains(module.id())) continue;
            if (!shouldEnableOnRestore(module)) continue;
            switch (tryEnable(module, false)) {
                case BLOCKED -> PENDING.add(module.id());
                case FAILED -> {
                    PENDING.remove(module.id());
                    persistEnabled(module, false);
                }
                case SUCCESS -> PENDING.remove(module.id());
            }
        }
    }

    /**
     * 恢复启用状态时该模块是否应尝试开启。
     *
     * <p>有历史记录（状态文件里已有该模块的记录）时按记录值恢复；完全没有记录时才用
     * {@link Module#enabledByDefault()}，并把该值立刻落盘，使玩家后续的手动关闭能覆盖默认值。
     * 既有模块未覆写该方法（默认 {@code false}），行为与改动前完全一致。</p>
     */
    private static boolean shouldEnableOnRestore(Module module) {
        if (ModuleStateConfig.ids().contains(module.id())) return ModuleStateConfig.isEnabled(module.id());
        if (!module.enabledByDefault()) return false;
        persistEnabled(module, true);
        return true;
    }

    /** 进入世界后开启等待中的模块 */
    private static void enablePending() {
        if (PENDING.isEmpty()) return;
        for (String moduleId : List.copyOf(PENDING)) {
            Module module = BY_ID.get(moduleId);
            if (module == null) {
                PENDING.remove(moduleId);
                continue;
            }
            switch (tryEnable(module, false)) {
                case SUCCESS -> {
                    PENDING.remove(moduleId);
                    ClientChat.send(module.displayName(), "§a§l已开启");
                }
                case FAILED -> {
                    PENDING.remove(moduleId);
                    persistEnabled(module, false);
                }
                case BLOCKED -> {
                    // 条件仍不满足：继续等待下一次进入世界
                }
            }
        }
    }

    // ── 开关 ──

    /** 启用结果 */
    private enum EnableResult {
        /** 已启用 */
        SUCCESS,
        /** 自检未通过：状态保留，条件满足后自动开启 */
        BLOCKED,
        /** 初始化失败或启用过程抛异常：状态写回未启用 */
        FAILED
    }

    /** 启用 / 关闭模块；返回是否达到期望状态 */
    public static boolean setEnabled(String moduleId, boolean enabled) {
        Module module = byId(moduleId);
        if (module == null) return false;
        return enabled ? enable(module) : disable(module, true);
    }

    /**
     * 静默开关：不播报「已开启 / 已关闭」。
     *
     * <p>供模块自己已经用统一状态源播报过结论的场景使用（如自检失败禁止启动、死亡强制停止、
     * 状态源已给出「已停止」），避免同一次状态变化出现两条提示。</p>
     */
    public static boolean setEnabledSilently(String moduleId, boolean enabled) {
        Module module = byId(moduleId);
        if (module == null) return false;
        return enabled ? enable(module, false) : disable(module, false);
    }

    /** 反转开关状态 */
    public static boolean toggle(String moduleId) {
        Module module = byId(moduleId);
        if (module == null) return false;
        return module.isEnabled() ? disable(module, true) : enable(module);
    }

    public static boolean isEnabled(String moduleId) {
        Module module = byId(moduleId);
        return module != null && module.isEnabled();
    }

    private static boolean enable(Module module) {
        return enable(module, true);
    }

    private static boolean enable(Module module, boolean announce) {
        return switch (tryEnable(module, announce)) {
            case SUCCESS -> {
                PENDING.remove(module.id());
                yield true;
            }
            case BLOCKED -> {
                PENDING.add(module.id());
                yield false;
            }
            case FAILED -> {
                PENDING.remove(module.id());
                yield false;
            }
        };
    }

    private static EnableResult tryEnable(Module module, boolean announce) {
        if (module.isEnabled()) return EnableResult.SUCCESS;

        if (BROKEN.contains(module.id())) {
            if (announce) ClientChat.send(module.displayName(), "§c初始化失败，本次会话不可用");
            return EnableResult.FAILED;
        }

        List<String> problems = problemsOf(module);
        if (!problems.isEmpty()) {
            if (announce) {
                ClientChat.send(module.displayName(), "§6§l还差 " + problems.size() + " 项没配好，配完再开：");
                for (int i = 0; i < problems.size(); i++) {
                    ClientChat.send(module.displayName(), "§6  " + (i + 1) + ". §f" + problems.get(i));
                }
                // 再给一份屏幕中间的只读面板：聊天会被后面的消息刷走，实机反馈是「点了没反应、
                // 不知道哪里不对」。所有模块共用这一条路径，模块自己不用再造弹窗
                // （见开发习惯「启动自检提示」铁律：聊天 + 弹窗两份，弹窗提示项红色加粗）。
                //
                // 只在玩家主动开启时弹：模块会留在等待队列里按刻重试（见 retryPending），
                // 重试再弹一次就变成「关了又自己开」的死循环。后台重试静默跳过。
                showSelfCheckNotice(module, problems);
                // 只在玩家主动开启时记流水：后台重试每 20 刻都会走到这里，记了就成刷屏
                ActivityLog.record(module.displayName() + " 启动未通过自检（" + problems.size() + " 项）");
            }
            return EnableResult.BLOCKED;
        }

        module.applyEnabled(true);
        ModuleEventBridge.attach(module);
        if (!runSafely(module, "启用", module::onEnable)) {
            forceDisable(module);
            return EnableResult.FAILED;
        }
        // onEnable 内部可能已经把自己关掉（旧项目「点击开启即识别」这类一次性模块，激活后立刻
        // closeQuietly）。此时内存状态已是关闭，配置必须跟着落成关闭、也不能再播报「已开启」，
        // 否则会出现「配置说开着、内存是关的」这种自相矛盾，下次启动还会把它恢复成启用。
        if (!module.isEnabled()) {
            persistEnabled(module, false);
            return EnableResult.SUCCESS;
        }
        persistEnabled(module, true);
        if (announce && !module.suppressEnableAnnounce()) ClientChat.send(module.displayName(), "§a§l已开启");
        ActivityLog.record(module.displayName() + " 已开启");
        return EnableResult.SUCCESS;
    }

    private static boolean disable(Module module, boolean announce) {
        if (!module.isEnabled()) return true;
        forceDisable(module);
        if (announce) ClientChat.send(module.displayName(), "§c§l已关闭");
        ActivityLog.record(module.displayName() + " 已关闭");
        return true;
    }

    /** 强制关闭：退订事件、回调 onDisable、写回状态；关闭回调异常不再向外扩散 */
    private static void forceDisable(Module module) {
        ModuleEventBridge.detach(module);
        module.applyEnabled(false);
        runSafely(module, "关闭", module::onDisable);
        persistEnabled(module, false);
    }

    private static void persistEnabled(Module module, boolean enabled) {
        ModuleStateConfig.setEnabled(module.id(), enabled);
        ModuleStateConfig.save();
    }

    /** 模块自检结果；自检本身抛异常时按「无法自检」处理 */
    public static List<String> problemsOf(Module module) {
        if (module == null) return List.of("模块不存在");
        try {
            List<String> problems = module.selfCheck();
            if (problems == null || problems.isEmpty()) return List.of();
            List<String> cleaned = new ArrayList<>();
            for (String problem : problems) {
                if (problem != null && !problem.isBlank()) cleaned.add(problem);
            }
            return cleaned;
        } catch (Throwable error) {
            LOGGER.error("模块 {} 自检异常", module.id(), error);
            return List.of("自检异常：" + describe(error));
        }
    }

    /**
     * 自检缺项的屏幕提示：项目统一的面板窗 + 「打开设置」+「知道了」，提示项红色加粗。
     *
     * <p>所有模块共用这一条路径（模块自己的启动自检另有流程时，也用同一个
     * {@link ConfirmPanelScreen#notice}），排版与配色只在组件里做一份。</p>
     *
     * <p>「打开设置」直接落到出错模块的设置页：实机反馈是「点完知道了还得自己再进模块中心
     * 找那个模块」，少两层点击。</p>
     */
    private static void showSelfCheckNotice(Module module, List<String> problems) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.execute(() -> {
            // 已经在看这类结论时不叠窗：多个模块同时缺项时聊天里有全部，面板不重复弹
            if (client.gui.screen() instanceof ConfirmPanelScreen) return;
            client.gui.setScreen(ConfirmPanelScreen.notice(module.displayName() + " · 启动自检未通过",
                "§7共 §e" + problems.size() + " §7项问题，已禁止启动：", problems, client.gui.screen(),
                () -> new ModuleScreen(ModuleEntries.of(module), null)));
        });
    }

    // ── 每刻与事件 ──

    private static void tickAll(Minecraft client) {
        if (client == null || BY_ID.isEmpty()) return;
        retryPending();
        for (Module module : BY_ID.values()) {
            if (!module.isEnabled()) continue;
            if (!runSafely(module, "运行", () -> module.onTick(client))) {
                forceDisable(module);
                ClientChat.send(module.displayName(), "§c运行异常，已自动关闭");
                ActivityLog.record(module.displayName() + " 运行异常，已自动关闭");
            }
        }
    }

    /**
     * 定时重试等待中的模块。
     *
     * <p>进服事件早于玩家实体完全就绪的场景下，等待队列里的模块在进服那一刻自检仍会失败；
     * 因此除进服事件外，再按固定间隔重试，直到条件满足。自检未通过时静默跳过，不产生提示。</p>
     */
    private static void retryPending() {
        if (PENDING.isEmpty()) return;
        if (++pendingRetryCounter < PENDING_RETRY_TICKS) return;
        pendingRetryCounter = 0;
        enablePending();
    }

    private static void onEventFailure(String ownerId, Throwable error) {
        String moduleId = ModuleEventBridge.moduleIdOf(ownerId);
        if (moduleId == null) {
            LOGGER.error("事件监听异常（所有者 {}）", ownerId, error);
            return;
        }
        Module module = byId(moduleId);
        if (module == null) return;
        LOGGER.error("模块 {} 事件处理异常", moduleId, error);
        ClientChat.send(module.displayName(), "§c事件处理异常，已自动关闭：" + describe(error));
        forceDisable(module);
    }

    // ── 设置持久化 ──

    /** 保存单个模块的设置；模块自身只需实现字段写入 */
    public static boolean saveSettings(Module module) {
        if (module == null) return false;
        // 隔离键取一次（同一时刻只可能有一个）：同时决定「读哪一份、往哪一份写」
        String scope = module.settingsScope();
        JsonObject settings = ModuleStateConfig.settingsOf(module.id(), scope);
        try {
            module.saveSettings(settings);
        } catch (Throwable error) {
            LOGGER.error("模块 {} 保存设置异常", module.id(), error);
            ClientChat.send(module.displayName(), "§c设置保存失败：" + describe(error));
            return false;
        }
        // 全局模板照常更新：它同时是「新服务器 / 新存档的初始值」（玩家最近用过的一套）
        ModuleStateConfig.putSettings(module.id(), settings);
        // 声明了隔离键的模块（如自动挖矿）再往本服务器 / 本存档的桶写一份
        ModuleStateConfig.putSettings(module.id(), scope, settings);
        ModuleStateConfig.save();
        return true;
    }

    /**
     * 按模块当前的隔离键重读它的设置（换服 / 进世界时由模块自己触发）。
     *
     * <p>设置只在模块装配时读过一次，那时还没有服务器身份；隔离模块（{@code settingsScope()} 非空）
     * 必须在自己「进世界 / 换服」的入口调用本方法，否则会把上一个服务器的配置带进新服务器。
     * 该方法与启停无关：模块关着也要能读到正确的一套配置（配置页显示的就是它）。</p>
     *
     * @return 是否成功执行了载入
     */
    public static boolean reloadScopedSettings(Module module) {
        if (module == null) return false;
        JsonObject settings = ModuleStateConfig.settingsOf(module.id(), module.settingsScope());
        return runSafely(module, "载入设置", () -> module.loadSettings(settings));
    }

    // ── 查询 ──

    public static Module byId(String moduleId) {
        return moduleId == null ? null : BY_ID.get(moduleId);
    }

    /** 全部模块，按注册顺序 */
    public static List<Module> all() {
        return List.copyOf(BY_ID.values());
    }

    public static int count() {
        return BY_ID.size();
    }

    /** 已启用模块 ID */
    public static Set<String> enabledIds() {
        Set<String> result = new LinkedHashSet<>();
        for (Module module : BY_ID.values()) {
            if (module.isEnabled()) result.add(module.id());
        }
        return result;
    }

    /** 已启用模块的中文名，供后端统计功能使用情况 */
    public static List<String> enabledDisplayNames() {
        List<String> result = new ArrayList<>();
        for (Module module : BY_ID.values()) {
            if (module.isEnabled()) result.add(module.displayName());
        }
        return result;
    }

    /** 模块状态中文描述，供指令与模块页面展示 */
    public static String statusText(Module module) {
        if (module == null) return "模块不存在";
        StringBuilder text = new StringBuilder(module.isEnabled() ? "已启用" : "未启用");
        String keyName = ModuleKeybindManager.keyName(module.keybindId());
        text.append("，快捷键 ").append(keyName.isBlank() ? "未绑定" : keyName);
        int listeners = 0;
        for (ClientEventType type : module.subscribedEvents()) {
            if (ClientEventBus.isSubscribed(ModuleEventBridge.ownerOf(module), type)) listeners++;
        }
        text.append("，事件订阅 ").append(listeners).append(" 项");
        if (PENDING.contains(module.id())) text.append("，等待进入世界");
        if (BROKEN.contains(module.id())) text.append("，初始化失败");
        return text.toString();
    }

    /** 运行时诊断文案 */
    public static String describe() {
        return "模块 " + BY_ID.size() + " 个，启用 " + enabledIds().size() + " 个，事件监听 "
                + ClientEventBus.totalSubscribers() + " 条，" + ModuleStateConfig.describe();
    }

    /** 统一的异常捕获入口；返回是否执行成功 */
    private static boolean runSafely(Module module, String action, Runnable body) {
        try {
            body.run();
            return true;
        } catch (Throwable error) {
            LOGGER.error("模块 {} 在{}时抛出异常", module.id(), action, error);
            return false;
        }
    }

    /** 异常中文描述：类型名 + 截断后的消息 */
    private static String describe(Throwable error) {
        if (error == null) return "未知异常";
        String message = error.getMessage();
        if (message == null || message.isBlank()) return error.getClass().getSimpleName();
        String trimmed = message.length() > 80 ? message.substring(0, 80) + "..." : message;
        return error.getClass().getSimpleName() + ": " + trimmed;
    }
}
