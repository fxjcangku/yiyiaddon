package com.yiyiaddon.feature.teleport;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.event.ServerPositionEvent;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.teleport.command.TpCommand;
import com.yiyiaddon.feature.teleport.config.TeleportSettings;
import com.yiyiaddon.feature.teleport.core.TeleportCoordinator;
import com.yiyiaddon.feature.teleport.model.TeleportMode;
import com.yiyiaddon.feature.teleport.model.TeleportRequest;
import com.yiyiaddon.feature.teleport.render.TeleportRenderer;
import com.yiyiaddon.feature.teleport.ui.TeleportPage;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.keybind.FunctionKeybinds;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 传送模块：TP地面 / TP穿墙 / TP坐标 三个独立功能，各绑独立按键（松开触发）。
 *
 * <p><b>旧项目对应物</b>：{@code teleport/TeleportModule.java}（302 行）。全部业务逻辑分类放在
 * {@code feature.teleport} 包：模型 model / 安全判据 safety / 几何扫描 geo / 发包执行 move /
 * 回弹验证 verify / 决策状态机 core / 调试渲染 render / 指令 command；本类只负责设置、
 * 事件接线与触发打包，与旧类职责边界一致。</p>
 *
 * <p><b>两处框架映射（旧项目没有、本项目框架必需，登记在迁移记录里）</b>：</p>
 * <ul>
 *     <li><b>功能键</b>：旧 {@code KeybindSetting.action} + 常驻 {@code KeyRemindListener}；
 *         本项目模块不启用就不 tick，故走常驻通道 {@link FunctionKeybinds}（核心每刻轮询松开边沿，
 *         模块关闭时也照常回调）。回调内按开关分流：开启 → 触发传送；关闭 → 提醒（3 秒节流，
 *         逐字照旧 {@code KeyRemindListener:77-82} 的**全局单一时间戳**口径）。</li>
 *     <li><b>回弹数据源</b>：旧模块直接读 {@code ClientboundPlayerPositionPacket} 包体；
 *         本项目改为订阅 {@link ClientEventType#SERVER_POSITION}，坐标由核心在收包瞬间按原版
 *         同源算法换算成绝对值（第 169 条：同源换算只留一份，战术模块将来复用同一份）。</li>
 * </ul>
 */
public final class TeleportModule extends Module {

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀，以及世界渲染层的所有者标识 */
    public static final String MODULE_ID = "teleport";

    /** 模块中文显示名：播报前缀与控制台标题共用一个来源 */
    public static final String MESSAGE_MODULE = "传送";

    /** 旧模型的眼高兜底（旧 {@code TeleportRequest.eyeHeight}） */
    private static final double DEFAULT_EYE_HEIGHT = 1.62;

    /** 未开启提醒的节流窗口（毫秒，逐字照旧 {@code KeyRemindListener} 的 3000） */
    private static final long REMIND_INTERVAL_MS = 3000L;

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体 */
    private final TeleportSettings settings = new TeleportSettings();

    /** 决策层：三模式共用状态机（Observe → Decide → Execute → Verify） */
    private final TeleportCoordinator coordinator = new TeleportCoordinator(sink());

    /** 未开启提醒的节流时间戳（**全局单一**，不是按键各自节流，照旧实现） */
    private long lastRemindMs;

    /**
     * 最近一次装载设置时所处的隔离作用域（{@code WorldIdentity.fileSafeServer()}）；
     * 装配期（主菜单）读到全局模板时为 {@code null}，换服判据见 {@link #refreshScopedSettings()}。
     */
    private String loadedSettingsScope;

    public TeleportModule() {
        super(MODULE_ID, MESSAGE_MODULE, "utility",
            "三种传送：回地表、穿墙、定点");
    }

    /**
     * 图标字形（Material Symbols 的 {@code near_me}）。
     *
     * <p>已按开发习惯第 140 条验真：解析 {@code MaterialSymbolsRounded.ttf} 的 cmap 与 post 字形名，
     * 该码点存在且与项目内既有图标无占用冲突。用户 2026-09-18 反馈「加上图标」后补。</p>
     */
    private static final String ICON = "\uE569";

    @Override
    public String icon() {
        return ICON;
    }

    /** 分类内排序：工具分类第四位（管理员检测 → 自动重生 → 自动重连 → 传送 → 自动登入） */
    @Override
    public int order() {
        return 40;
    }

    /** 设置载体（控制台页面读写；模块内部行为不变） */
    public TeleportSettings settings() {
        return settings;
    }

    /** 最近一次结束的传送上下文（调试渲染用，可空） */
    public TeleportCoordinator coordinator() {
        return coordinator;
    }

    // ── 设置持久化 ──

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
        // 记下这一份是从哪个作用域读来的：换服判据用它比对（见 refreshScopedSettings）
        loadedSettingsScope = settingsScope();
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
    }

    /**
     * 设置隔离键：进世界后取当前服务器 / 单人存档，未进世界返回 {@code null}（按全局模板处理）。
     *
     * <p><b>为什么需要隔离</b>（用户 2026-09-21 报的「换服把点位设置覆盖了」）：{@code 坐标 X/Y/Z}
     * 是只对某一个世界有意义的绝对坐标，原本全局共用一份 —— 在 B 服改坐标，A 服那套跟着变。
     * 现在一个服务器（单人则是一个存档）一套配置，切服自动切换，与自动挖矿、自动附魔点位同一口径。</p>
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
     * <p>调用点与自动挖矿一致：模块启用、打开配置页、{@code .tp} 指令入口；TP 三个功能键只在模块
     * 开启状态生效（关闭时按旧文案只提醒），因此启用时的这一次重读就覆盖了按键路径。
     * 读盘动作由运行时承担（{@link ModuleManager#reloadScopedSettings}），本方法只判「作用域变没变」。</p>
     */
    public void refreshScopedSettings() {
        if (Objects.equals(settingsScope(), loadedSettingsScope)) return;
        ModuleManager.reloadScopedSettings(this);
    }

    // ── 初始化与生命周期 ──

    /**
     * 功能键登记：**常驻**（不随模块开关注销），因此「模块未开启时按键提醒」在关闭状态下也能工作。
     */
    @Override
    protected void onInitialize() {
        FunctionKeybinds.register(MODULE_ID, "TP地面键", () -> settings.groundKey,
            keyName -> onFunctionKey(keyName, TeleportMode.GROUND));
        FunctionKeybinds.register(MODULE_ID, "TP穿墙键", () -> settings.wallKey,
            keyName -> onFunctionKey(keyName, TeleportMode.WALL));
        FunctionKeybinds.register(MODULE_ID, "TP坐标键", () -> settings.coordKey,
            keyName -> onFunctionKey(keyName, TeleportMode.COORD));
    }

    /** 旧 {@code onActivate}：注册世界渲染层（调试渲染由模块设置控制画不画） */
    @Override
    protected void onEnable() {
        // 装载本服的设置（坐标与阈值都按服务器分开存，先换这一份再开渲染）
        refreshScopedSettings();
        WorldOverlay.register(MODULE_ID, this::renderOverlay);
    }

    /** 旧 {@code onDeactivate}：注销渲染层 → 中止并清理状态；必须可重复调用而不出错 */
    @Override
    protected void onDisable() {
        WorldOverlay.unregister(MODULE_ID);
        coordinator.cancel("模块已关闭，传送中止");
        coordinator.clear();
    }

    // ── 事件 ──

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK, ClientEventType.SERVER_POSITION, ClientEventType.JOIN_SERVER);
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null) return;
        // 进入世界（含同一会话内「退出 A 服再进 B 服」）时换成本服的设置：
        // 本模块启用状态会跨世界保留，换服时不会再走 onEnable，靠这一处补上（2026-09-21 服务器隔离）
        if (event.type() == ClientEventType.JOIN_SERVER) {
            refreshScopedSettings();
            return;
        }
        // 每刻流程统一走 onTick（ModuleManager 调度），此处只处理权威位置
        if (event.type() != ClientEventType.SERVER_POSITION) return;

        ServerPositionEvent position = event.position();
        if (position == null || mc.player == null) return;
        coordinator.onServerPosition(position.position(), position.vehicle(), mc.player);
    }

    @Override
    public void onTick(Minecraft client) {
        if (mc.player == null || mc.level == null) return;
        coordinator.tick(mc.player, mc.level);
    }

    /** 每帧世界渲染（由世界渲染层驱动） */
    private void renderOverlay(EspRenderer renderer) {
        if (!settings.debugRender) return;
        TeleportRenderer.render(renderer, coordinator.last());
    }

    // ── 指令与页面 ──

    /** 配置页 = 薄壳模块页 {@link TeleportPage} + 整屏控制台（6 分页） */
    @Override
    public ModulePage page() {
        return new TeleportPage(this);
    }

    @Override
    public List<ClientCommand> commands() {
        return List.of(new TpCommand(this));
    }

    // ── 触发入口（三个独立按键 + 指令） ──

    /**
     * 功能键松开回调：开启状态下触发对应模式；关闭状态下播报提醒。
     *
     * <p>提醒节流是**全局单一 3 秒窗口**（旧实现里任一键提醒后三键都不再提醒），
     * 与「开启状态连续按键不受节流影响」这一旧行为严格对应。</p>
     */
    private void onFunctionKey(String keyName, TeleportMode mode) {
        if (!isEnabled()) {
            long now = System.currentTimeMillis();
            if (now - lastRemindMs < REMIND_INTERVAL_MS) return;
            lastRemindMs = now;
            ClientChat.send(MESSAGE_MODULE, "§c✗ 模块未开启 ▸ 请先开启 "
                + highlightFunction(MESSAGE_MODULE) + "，再按快捷键使用 " + highlightFunction(keyName));
            return;
        }

        switch (mode) {
            case GROUND -> triggerGround();
            case WALL -> triggerWall();
            case COORD -> triggerCoord();
        }
    }

    /** TP地面：回到真正露天地表 */
    private void triggerGround() {
        send(build(TeleportMode.GROUND, null));
    }

    /** TP穿墙：锁定当前真实视线快照（相机起点 + 观察方向 + 眼高） */
    private void triggerWall() {
        if (mc.player == null) return;
        TeleportRequest req = build(TeleportMode.WALL, null);
        req.rayOrigin = mc.player.getEyePosition(1.0F);
        req.rayDir = mc.player.getViewVector(1.0F);
        req.eyeHeight = mc.player.getEyeHeight();
        send(req);
    }

    /** TP坐标：传送到面板配置的坐标 */
    private void triggerCoord() {
        send(build(TeleportMode.COORD,
            new Vec3(settings.coordX + 0.5, settings.coordY, settings.coordZ + 0.5)));
    }

    /** 指令入口：.tp X Y Z */
    public void teleportToCoord(int x, int y, int z) {
        send(build(TeleportMode.COORD, new Vec3(x + 0.5, y, z + 0.5)));
    }

    /** 从设置打包请求参数（决策层不依赖设置对象） */
    private TeleportRequest build(TeleportMode mode, Vec3 coord) {
        TeleportRequest req = new TeleportRequest();
        req.mode = mode;
        req.coord = coord;
        req.maxRise = settings.maxRise;
        req.maxDistance = settings.maxDistance;
        req.maxDeviation = settings.maxDeviation;
        req.maxFall = settings.maxFall;
        req.noFallDamage = settings.noFallDamage;
        req.fallbackRadius = settings.fallbackRadius;
        req.verifyThreshold = settings.verifyThreshold;
        req.verifyWindow = settings.verifyWindow;
        return req;
    }

    private void send(TeleportRequest req) {
        if (mc.player == null || mc.level == null) return;
        coordinator.request(mc.player, mc.level, req);
    }

    // ── 播报回调：高亮统一走本地 highlight* 方法（旧基类同名方法，方法体逐字，禁止改写） ──

    private TeleportCoordinator.Sink sink() {
        return new TeleportCoordinator.Sink() {
            @Override
            public void broadcast(String message) {
                ClientChat.send(MESSAGE_MODULE, message);
            }

            @Override
            public String text(String s) {
                return highlightText(s);
            }

            @Override
            public String func(String s) {
                return highlightFunction(s);
            }

            @Override
            public String num(String s) {
                return highlightNumber(s);
            }

            @Override
            public String loc(String s) {
                return highlightLocation(s);
            }
        };
    }

    /** 物品 / 文本高亮（亮绿色粗体） */
    private static String highlightText(String text) {
        return "§a§l" + text + "§r§f§l";
    }

    /** 功能 / 模式高亮（亮青色粗体） */
    private static String highlightFunction(String text) {
        return "§b§l" + text + "§r§f§l";
    }

    /** 数值高亮（黄色粗体） */
    private static String highlightNumber(String text) {
        return "§e§l" + text + "§r§f§l";
    }

    /** 坐标高亮（粉色粗体） */
    private static String highlightLocation(String text) {
        return "§d§l" + text + "§r§f§l";
    }
}
