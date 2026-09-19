package com.yiyiaddon.feature.admindetect;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.admindetect.config.AdminDetectorSettings;
import com.yiyiaddon.feature.admindetect.render.AdminThreatRenderer;
import com.yiyiaddon.feature.admindetect.service.AdminDisconnect;
import com.yiyiaddon.feature.admindetect.ui.AdminDetectorPage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 管理员检测：监测附近玩家，识别管理员视察的典型形态（旁观 / 创造 / 隐身 / 隐藏），
 * 命中即警报 + ESP 标记 + 断线保命；危险玩家离开范围同样播报。
 *
 * <p><b>旧项目来源</b>：{@code admdetector/AdminDetectorModule.java}（223 行）+
 * {@code autodisconnect/CometDisconnectModule.java}（77 行）两个模块。用户 2026-09-16 裁定
 * <b>合并为一个模块</b>：旧「自动断线」的静态断线入口搬进
 * {@link AdminDisconnect}，它的「开启即断线一次」语义取消（改成控制台「概览」页的
 * {@code §c立即断线} 按钮，与旧模块 {@code getWidget} 里那个按钮同名同位），旧上的
 * {@code 使用说明} 中的第一条注意（强制关闭自动重连）随该步一并删除。模块名保留
 * <b>管理员检测</b>（用户 2026-09-16 拍板）。</p>
 *
 * <p><b>配置页</b>：{@link #page()} 返回薄壳模块页 {@link AdminDetectorPage}
 * （控制台入口 + 使用说明），7 项设置与 3 项新增显示设置由整屏控制台分页承载
 * （概览 / 检测 / 名单 / 显示与警报）。</p>
 *
 * <p><b>与旧项目的行为差异（用户 2026-09-16 拍板，勿自行改回）</b>：</p>
 * <ul>
 *     <li>检测范围默认 {@code 64}（旧 10）；判定仍是「距离 &le; 范围即命中」，一处未改；</li>
 *     <li>命中后除聊天播报外，<b>新增</b>警报音效、ESP 描边框、射线（三项默认开）；</li>
 *     <li>白 / 黑名单的录入方式由逗号分隔文本改为在线玩家选择器（不手输名字）；</li>
 *     <li>断线不再走独立模块，改为本模块内置调用（原因文案与旧完全一致）。</li>
 * </ul>
 */
public final class AdminDetectorModule extends Module {

    /**
     * 图标字形（Material Symbols：{@code admin_panel_settings}）。
     *
     * <p>已用脚本解析 {@code MaterialSymbolsRounded.ttf} 的 cmap（format 4）与 {@code post} 表字形名
     * 验真存在，满足第 140 条；此前未被本项目任何位置占用。</p>
     */
    private static final String ICON = "\uEF3D";

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀，以及世界渲染层的所有者标识 */
    public static final String MODULE_ID = "admindetector";

    /** 模块中文显示名：播报前缀、断线界面前缀、控制台标题、使用说明窗口共用一个来源 */
    public static final String MESSAGE_MODULE = "管理员检测";

    // ── 播报文案（旧项目 notify 原文逐字，只有前缀按第 110/119 条换成本项目格式） ──

    /** 旧 {@code :149} 的正文（去掉 {@code notify} 追加的冗余 {@code §f}，渲染结果一致） */
    private static final String DETECTED_PREFIX = "§c✗ 检测到危险玩家 §8▸ ";

    /** 旧 {@code :159} 的正文 */
    private static final String LEFT_PREFIX = "§a✓ 危险玩家已离开 §8▸ ";

    /** 旧 {@code :193} 的正文 */
    private static final String DISCONNECT_NOTICE = "§c✗ 已触发自动断线，正在退出服务器";

    /** 旧 {@code :194} 传给断线模块的原因 */
    public static final String DISCONNECT_REASON = "检测到危险玩家接近";

    /** 旧「自动断线」模块手动触发时传的原因（{@code CometDisconnectModule:41 / :68}） */
    public static final String MANUAL_DISCONNECT_REASON = "手动触发自动断线";

    /** 警报音高（照 {@code SoundNotifier.notifyDeath} 的 0.8f；音效同为 {@code WITHER_SPAWN}） */
    private static final float ALARM_PITCH = 0.8f;

    /** 警报音量：本项目固定 1.0（挖矿那套广播用 0.5 是因为它与其它提示音混在一起，警报要更醒目） */
    private static final float ALARM_VOLUME = 1.0f;

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体 */
    private final AdminDetectorSettings settings = new AdminDetectorSettings();

    /** 本刻范围内命中的危险玩家（ESP 层每帧读；每刻整体重建，不跨刻持有旧世界的实体） */
    private final List<Entity> threats = new ArrayList<>();

    /** 进出边沿记录（UUID → 玩家名）：只有「首次进入范围」才播报，离开时按它补一条 */
    private final Map<UUID, String> nearbyThreats = new HashMap<>();

    /**
     * 本次会话里在 Tab 玩家列表出现过的名字。
     *
     * <p>用来把「插件 NPC」与「vanish 的管理员」分开，见 {@link #isHiddenFromTab(Player)}。
     * 模块启用时清空（同 {@link #nearbyThreats} 的生命周期），因此在世界里中途开关模块会丢失
     * 「之前见过谁」这半份记忆 —— 此时仍由「没有自定义名字」那一半判据兜住。</p>
     */
    private final Set<String> seenInTab = new HashSet<>();

    /** 断线防重锁：断线后本刻流程仍在跑，防止同一刻重复断线（旧 {@code :50}） */
    private boolean disconnecting;

    /** 威胁 ESP：挂在世界渲染层上，随模块开关注册 / 注销 */
    private final AdminThreatRenderer threatRenderer = new AdminThreatRenderer(this);

    public AdminDetectorModule() {
        super(MODULE_ID, MESSAGE_MODULE, "utility",
            "监测附近玩家，识别旁观/创造/隐身/隐藏的管理员，命中即断线保命。点击按钮查看说明。");
    }

    /** 设置载体（只读暴露给控制台页；模块内部行为不变） */
    public AdminDetectorSettings settings() {
        return settings;
    }

    /** 本刻命中的威胁（只读；ESP 渲染层每帧遍历） */
    public List<Entity> threats() {
        return Collections.unmodifiableList(threats);
    }

    /** 是否已触发断线（概览页状态条用） */
    public boolean isDisconnecting() {
        return disconnecting;
    }

    @Override
    public int order() {
        return 10;
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

    /** 旧项目本模块没有自检（{@code AdminDetectorModule} 未覆写），本项目同样不设门槛 */
    @Override
    public List<String> selfCheck() {
        return List.of();
    }

    /** 配置页 = 薄壳模块页 {@link AdminDetectorPage} + 整屏控制台 */
    @Override
    public ModulePage page() {
        return new AdminDetectorPage(this);
    }

    // ── 生命周期 ──

    /** 旧 {@code onActivate}（{@code :105-110}）：重置状态、重新武装检测、清空历史进出记录 */
    @Override
    protected void onEnable() {
        disconnecting = false;
        nearbyThreats.clear();
        seenInTab.clear();
        threats.clear();
        // ESP：注册世界渲染层，关闭时注销（照 KillAuraModule / AutoMinerModule 的既有做法）
        WorldOverlay.register(MODULE_ID, threatRenderer::render);
    }

    /** 旧 {@code onDeactivate}（{@code :112-116}）；必须可重复调用而不出错 */
    @Override
    protected void onDisable() {
        disconnecting = false;
        nearbyThreats.clear();
        seenInTab.clear();
        threats.clear();
        WorldOverlay.unregister(MODULE_ID);
    }

    // ── 事件 ──

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK, ClientEventType.DISCONNECT);
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null) return;
        // 每刻流程统一走 onTick（ModuleManager 调度），此处不重复执行
        if (event.type() != ClientEventType.DISCONNECT) return;

        // 旧 onGameLeft（{@code :169-173}）：断线退出后自动关闭，避免重连后残留开启态误断。
        // 照 AutoMinerModule 的既有做法先清本模块状态，再关模块。
        disconnecting = false;
        nearbyThreats.clear();
        seenInTab.clear();
        threats.clear();
        if (isEnabled()) ModuleManager.setEnabled(MODULE_ID, false);
    }

    // ── 每刻主流程（旧 onTick {@code :118-167}，逐句对应） ──

    @Override
    public void onTick(Minecraft client) {
        if (mc.player == null || mc.level == null) return;

        int range = settings.detectRange;
        List<String> whitelist = settings.whitelist;
        List<String> blacklist = settings.blacklist;

        // 先记下本刻 Tab 玩家列表里的人名：隐藏判据要靠「这个人曾经在 Tab 里出现过」把
        // 插件 NPC（从没进过 Tab）与 vanish 的管理员（先可见、后消失）分开，见 isHiddenFromTab。
        rememberTabNames();

        // 本次 tick 在范围内的危险玩家（旧 currentThreats）
        Set<UUID> currentThreats = new HashSet<>();
        threats.clear();

        for (Player player : mc.level.players()) {
            if (player == mc.player) continue;

            String name = player.getName().getString();

            // 白名单豁免：来了不退出，也不提示
            if (AdminDetectorSettings.containsName(whitelist, name)) continue;

            // 距离判断：超出范围不处理
            if (mc.player.distanceTo(player) > range) continue;

            // 命中危险特征
            String reason = getThreatReason(player, name, blacklist);
            if (reason == null) continue;

            currentThreats.add(player.getUUID());
            threats.add(player);

            // 首次进入：聊天栏提示 + 记录
            if (!nearbyThreats.containsKey(player.getUUID())) {
                nearbyThreats.put(player.getUUID(), name);
                ClientChat.send(MESSAGE_MODULE,
                    DETECTED_PREFIX + highlightText(name) + " §f· " + highlightFunction(reason));
            }
        }

        // 离开提示：之前记录但本次已不在范围内的危险玩家
        Iterator<Map.Entry<UUID, String>> it = nearbyThreats.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, String> entry = it.next();
            if (!currentThreats.contains(entry.getKey())) {
                it.remove();
                ClientChat.send(MESSAGE_MODULE, LEFT_PREFIX + highlightText(entry.getValue()));
            }
        }

        // 存在危险玩家且未断线 → 警报 + 断线保命（用户要求：先响警报，再立刻断线）
        if (!currentThreats.isEmpty() && !disconnecting) {
            triggerDisconnect();
        }
    }

    /**
     * 手动断线（旧「自动断线」模块 {@code getWidget} 里那个 {@code §c立即断线} 按钮的语义）。
     *
     * <p>无连接时与旧实现一样静默 —— 旧 {@code CometDisconnectModule.disconnect} 首行就是静默 return。</p>
     */
    public void disconnectNow() {
        AdminDisconnect.disconnect(MESSAGE_MODULE, MANUAL_DISCONNECT_REASON);
    }

    // ── 与自动化模块的联动 ────────────────────────────────────────────────────

    /**
     * 自动化模块（自动挖矿 / 星露谷农场）启动时自动打开本模块
     * （用户 2026-09-19 需求：「打开自动挖矿跟星露谷农场自动打开管理员检测」）。
     *
     * <p><b>为什么需要</b>：这两个模块都是长时间无人值守跑图，最怕管理员悄悄摸过来 ——
     * 检测靠「记得手动开」就一定会漏。因此它们每次真正启用成功时，把本模块一起拉起来。</p>
     *
     * <p><b>只开不关</b>：自动化模块关闭时<b>不会</b>反过来关掉本模块。用户可能本来就想让它一直盯着，
     * 关掉自动化不等于解除警戒（与「修补联动杀戮光环」那种成对开关的语义不同 ——
     * 那里关掉的是我们自己临时开的战斗）。</p>
     *
     * <p>用 {@link ModuleManager#setEnabledSilently} 开，再补一条带原因的单行播报：本模块命中即
     * <b>强制断线</b>，用户必须分得清它是「自己开的」还是「被联动开的」，否则会以为模块自己抽风。
     * 已经开着时直接返回，不重复播报。</p>
     *
     * @param sourceDisplayName 触发联动的模块显示名（自动挖矿 / 星露谷农场）
     */
    public static void linkFromAutomation(String sourceDisplayName) {
        if (ModuleManager.isEnabled(MODULE_ID)) return;
        // 自检没过 / 本次会话初始化失败：静默放弃，不打扰用户
        if (!ModuleManager.setEnabledSilently(MODULE_ID, true)) return;
        ClientChat.send(MESSAGE_MODULE, "§a§l已开启 §8▸ §e随「" + sourceDisplayName + "」自动打开");
    }

    // ── 判定（旧 {@code :175-230}，方法体逐字） ──

    /**
     * 判定玩家命中的危险特征，未命中返回 null。
     * 黑名单优先级最高，其次按旁观 / 创造 / 隐身 / 隐藏顺序。
     */
    private String getThreatReason(Player player, String name, List<String> blacklist) {
        if (AdminDetectorSettings.containsName(blacklist, name)) return "黑名单";
        if (settings.detectSpectator && player.isSpectator()) return "旁观者";
        if (settings.detectCreative && isCreative(player)) return "创造模式";
        if (settings.detectInvisible && player.isInvisible()) return "隐身";
        if (settings.detectHidden && isHiddenFromTab(player)) return "隐藏";
        return null;
    }

    /** 判断玩家是否为创造模式，优先 Tab 列表游戏模式，缺失时退回实体能力位。 */
    private boolean isCreative(Player player) {
        GameType gameMode = getGameMode(player);
        if (gameMode != null) return gameMode == GameType.CREATIVE;
        return player.getAbilities().instabuild;
    }

    /**
     * 判断玩家是否被 vanish 插件隐藏（实体存在但不在 Tab 列表）。
     *
     * <p><b>本项目加强（用户 2026-09-16 裁定「能正常识别玩家过滤 npc 就行」，登记在迁移记录里）</b>：
     * 旧实现只看「不在 Tab 列表」，在带插件 NPC 的服务器上会把 NPC 判成管理员 —— 实测
     * {@code long.kkwmc.cn} 的新手向导 NPC（名字本身就是提示语）就命中了，导致模块一开就断线。
     * 客户端无法直接问「你是不是 NPC」，因此用两条独立信号过滤：</p>
     *
     * <ul>
     *     <li><b>本次会话里在 Tab 列表见过他</b>：vanish 的轨迹必然是「先可见、后消失」，
     *         而插件 NPC 从建立那一刻就不在 Tab 列表，永远进不了这份记忆；</li>
     *     <li><b>他没有自定义名字</b>：真实玩家的名牌来自档案名 / 计分板队伍，不占用自定义名字组件；
     *         插件 NPC 靠自定义名字显示「导游」「右键我前往新手任务地点」这类铭牌，因此带自定义名字。
     *         这一条兜住「模块中途开启、丢掉前半份记忆」与「管理员在你进服前就已经隐身」两种情况。</li>
     * </ul>
     *
     * <p><b>残留边界</b>：服务器若给<b>真实玩家</b>设了自定义名字，且该玩家在你见到他之前就已隐身，
     * 则判不出来 —— 这是客户端侧无法消除的固有盲区，登记在迁移记录的未验证项里。</p>
     */
    private boolean isHiddenFromTab(Player player) {
        if (mc.getConnection() == null) return false;
        if (mc.getConnection().getPlayerInfo(player.getUUID()) != null) return false;
        return seenInTab.contains(player.getName().getString()) || !player.hasCustomName();
    }

    /** 记下本刻 Tab 玩家列表里的全部人名（只增不减：要的就是「曾经见过」这份记忆）。 */
    private void rememberTabNames() {
        if (mc.getConnection() == null) return;
        for (PlayerInfo info : mc.getConnection().getOnlinePlayers()) {
            if (info == null || info.getProfile() == null) continue;
            String name = info.getProfile().name();
            if (name != null && !name.isBlank()) seenInTab.add(name);
        }
    }

    /** 读取玩家游戏模式，连接或 Tab 列表缺失返回 null。 */
    private GameType getGameMode(Player player) {
        if (mc.getConnection() == null) return null;
        PlayerInfo info = mc.getConnection().getPlayerInfo(player.getUUID());
        return info == null ? null : info.getGameMode();
    }

    // ── 断线与警报 ──

    /**
     * 触发断线：播报 + 警报音 + 调用断线。
     *
     * <p>旧实现只有「播报 + 断线」（旧 {@code :191-195}）；警报音是用户 2026-09-16 要求新增的。</p>
     */
    private void triggerDisconnect() {
        disconnecting = true;
        ClientChat.send(MESSAGE_MODULE, DISCONNECT_NOTICE);
        playAlarm();
        AdminDisconnect.disconnect(MESSAGE_MODULE, DISCONNECT_REASON);
    }

    /** 警报音：原版凋零生成音效（旧项目 {@code SoundNotifier.notifyDeath} 用的同一个音效） */
    private void playAlarm() {
        if (!settings.alarmSound) return;
        if (mc.player == null || mc.level == null) return;
        mc.level.playLocalSound(mc.player.getX(), mc.player.getY(), mc.player.getZ(),
            SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, ALARM_VOLUME, ALARM_PITCH, false);
    }

    // ── 高亮工具（旧基类 {@code YiyiaddonModule} 的同名方法，方法体逐字，禁止改写） ──

    /** 物品 / 文本高亮（亮绿色粗体） */
    private static String highlightText(String text) {
        return "§a§l" + text + "§r§f§l";
    }

    /** 功能 / 模式高亮（亮青色粗体） */
    private static String highlightFunction(String text) {
        return "§b§l" + text + "§r§f§l";
    }
}
