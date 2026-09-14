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
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
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
        }
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
            JsonObject settings = ModuleStateConfig.settingsOf(module.id());
            runSafely(module, "载入设置", () -> module.loadSettings(settings));
            if (BROKEN.contains(module.id())) continue;
            if (!ModuleStateConfig.isEnabled(module.id())) continue;
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
                    ClientChat.send("已自动启用模块：" + module.displayName());
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
        return switch (tryEnable(module, true)) {
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
            if (announce) ClientChat.send("§c模块 " + module.displayName() + " 初始化失败，本次会话不可用");
            return EnableResult.FAILED;
        }

        List<String> problems = problemsOf(module);
        if (!problems.isEmpty()) {
            if (announce) {
                ClientChat.send("§c模块 " + module.displayName() + " 暂无法启用：" + String.join("；", problems)
                        + "（条件满足后会自动开启）");
            }
            return EnableResult.BLOCKED;
        }

        module.applyEnabled(true);
        ModuleEventBridge.attach(module);
        if (!runSafely(module, "启用", module::onEnable)) {
            forceDisable(module);
            return EnableResult.FAILED;
        }
        persistEnabled(module, true);
        if (announce) ClientChat.send("已启用模块：" + module.displayName());
        return EnableResult.SUCCESS;
    }

    private static boolean disable(Module module, boolean announce) {
        if (!module.isEnabled()) return true;
        forceDisable(module);
        if (announce) ClientChat.send("已关闭模块：" + module.displayName());
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

    // ── 每刻与事件 ──

    private static void tickAll(Minecraft client) {
        if (client == null || BY_ID.isEmpty()) return;
        retryPending();
        for (Module module : BY_ID.values()) {
            if (!module.isEnabled()) continue;
            if (!runSafely(module, "运行", () -> module.onTick(client))) {
                forceDisable(module);
                ClientChat.send("§c模块 " + module.displayName() + " 运行异常，已自动关闭");
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
        ClientChat.send("§c模块 " + module.displayName() + " 事件处理异常，已自动关闭：" + describe(error));
        forceDisable(module);
    }

    // ── 设置持久化 ──

    /** 保存单个模块的设置；模块自身只需实现字段写入 */
    public static boolean saveSettings(Module module) {
        if (module == null) return false;
        JsonObject settings = ModuleStateConfig.settingsOf(module.id());
        try {
            module.saveSettings(settings);
        } catch (Throwable error) {
            LOGGER.error("模块 {} 保存设置异常", module.id(), error);
            ClientChat.send("§c模块 " + module.displayName() + " 设置保存失败：" + describe(error));
            return false;
        }
        ModuleStateConfig.putSettings(module.id(), settings);
        ModuleStateConfig.save();
        return true;
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
