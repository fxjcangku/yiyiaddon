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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /** 客户端日志：只给「隐藏判定命中」留证据行用（见 {@link #logHiddenEvidence}） */
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDetectorModule.class);

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
     * 本次会话里<b>在 Tab 界面上真显示过</b>的玩家（UUID → 名字）。
     *
     * <p>用来把「插件 NPC」与「vanish 的管理员」分开，见 {@link #isHiddenFromTab(Player)}。
     * <b>只能读 {@code getListedOnlinePlayers()}</b>（真机 2026-09-22 04:33 实证：读错这一份，
     * 主城 NPC「苍涟绝岛」被写进记忆、随后判成隐藏管理员直接断线）—— 服务端下发玩家列表项时带
     * {@code listed} 标志，插件给 NPC 的条目是 {@code listed=false}：客户端拿得到它的名字与皮肤
     * （实体照常渲染），但它在 Tab 里不显示；{@code getOnlinePlayers()} 把这种条目也算「在线」，
     * 只有 {@link net.minecraft.client.multiplayer.ClientPacketListener#getListedOnlinePlayers()}
     * 才是「界面上真显示出来的人」。模块启用时清空（同 {@link #nearbyThreats} 的生命周期），
     * 因此在世界里中途开关模块会丢掉「之前见过谁」这份记忆，此后 vanish 的人判不出来
     * （残留边界见 {@link #isHiddenFromTab(Player)}）。</p>
     */
    private final Map<UUID, String> seenInTab = new HashMap<>();

    /** 断线防重锁：断线后本刻流程仍在跑，防止同一刻重复断线（旧 {@code :50}） */
    private boolean disconnecting;

    /** 威胁 ESP：挂在世界渲染层上，随模块开关注册 / 注销 */
    private final AdminThreatRenderer threatRenderer = new AdminThreatRenderer(this);

    public AdminDetectorModule() {
        super(MODULE_ID, MESSAGE_MODULE, "utility",
            "识别隐身/创造管理员并断线");
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

        // 先记下本刻 Tab 显示名单上的人：隐藏判据要靠「这个人曾经显示在 Tab 名单上」把
        // 插件 NPC（listed=false，从没进过显示名单）与 vanish 的管理员（先可见、后消失）分开，
        // 见 isHiddenFromTab。
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

        // ── 第二条通道：Tab 列表里标着「旁观」、但世界里拿不到实体的人 ──
        scanTabSpectators(whitelist, currentThreats);

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
     * 补扫 Tab 列表里标着「旁观」、但世界里没有实体的玩家。
     *
     * <p><b>为什么必须有这条通道</b>（用户 2026-09-19：「旁观者啊 为什么没检测？」）：
     * 「旁观者对其他玩家不可见」是原版机制，服务器通常连实体都不下发 —— 主循环只遍历
     * {@code mc.level.players()}，这类人一辈子进不来，检测项开着也白开。他们连坐标都拿不到，
     * <b>无法判距离</b>，因此按「可能在身边」处理：命中、播报（标注位置未知）、照常断线保命 ——
     * 开着旁观盯你的人本来就不会让你看见，这里宁可错杀。</p>
     *
     * <p>世界里找得到实体的旁观者由主循环按距离判（判定见 {@link #isSpectator(Player)}），
     * 这里跳过，免得同一个人被两条通道各播报一次。</p>
     */
    private void scanTabSpectators(List<String> whitelist, Set<UUID> currentThreats) {
        if (!settings.detectSpectator || mc.getConnection() == null
                || mc.level == null || mc.player == null) {
            return;
        }
        for (PlayerInfo info : mc.getConnection().getListedOnlinePlayers()) {
            if (info == null || info.getProfile() == null) continue;
            if (info.getGameMode() != GameType.SPECTATOR) continue;
            UUID id = info.getProfile().id();
            if (id == null || id.equals(mc.player.getUUID())) continue;
            String name = info.getProfile().name();
            if (name == null || name.isBlank()) continue;
            if (AdminDetectorSettings.containsName(whitelist, name)) continue;
            if (currentThreats.contains(id)) continue;
            // 世界里找得到实体：交给主循环按距离判（不在范围内就不该命中）
            if (mc.level.getPlayerByUUID(id) != null) continue;
            currentThreats.add(id);
            if (nearbyThreats.containsKey(id)) continue;
            nearbyThreats.put(id, name);
            ClientChat.send(MESSAGE_MODULE, DETECTED_PREFIX + highlightText(name) + " §f· "
                    + highlightFunction("旁观者") + " §8（位置未知）");
        }
    }

    /**
     * 判定玩家命中的危险特征，未命中返回 null。
     * 黑名单优先级最高，其次按旁观 / 创造 / 隐身 / 隐藏顺序。
     *
     * <p><b>只有 Tab 名单里真显示出来的玩家才走「旁观 / 创造 / 隐身」三条</b>（用户 2026-09-21：
     * 「管理员检测会把 npc 当成管理员直接把我 t 了 那个出售商人就是创造」）：别人的游戏模式只能从 Tab 的
     * {@code PlayerInfo} 里读，读不到时 {@link #isCreative} 会退回实体自身的能力位 {@code instabuild}
     * —— 而插件 NPC 正是「创造能力的假玩家实体」（实体类型就是 {@code minecraft:player}、又不在 Tab 名单），
     * 于是自用模式去收购商人那里卖矿，一进检测范围就被判成「创造模式」管理员并直接断线。</p>
     *
     * <p>不在 Tab 名单的实体只走「隐藏」通道，而 {@link #isHiddenFromTab} 要求「本次会话里在 Tab 名单
     * <b>显示过</b>他」，不在 Tab 又从没显示过的插件 NPC 被前置排除放行（并自动进白名单）。</p>
     */
    private String getThreatReason(Player player, String name, List<String> blacklist) {
        if (AdminDetectorSettings.containsName(blacklist, name)) return "黑名单";
        boolean inTab = isInTab(player);
        if (inTab) {
            if (settings.detectSpectator && isSpectator(player)) return "旁观者";
            if (settings.detectCreative && isCreative(player)) return "创造模式";
            if (settings.detectInvisible && player.isInvisible()) return "隐身";
        }
        // 插件 NPC 前置排除（用户 2026-09-22：「npc 还是当成管理员了」）：不在 Tab 名单、且本次会话
        // 从没在 Tab 名单里<b>显示</b>过 —— 真玩家进服时服务端必定把他的条目以 listed=true 下发，
        // vanish 只可能「先显示、后消失」，因此这种实体只可能是插件 NPC，四条形态判定一条都不该走。
        // 按 UUID 比对（旧实现按名字）：同名不同人是有的（NPC 与被顶号/回档的玩家同名），
        // 名字撞上就会让 NPC 借了别人的「曾在 Tab」记忆去命中「隐藏」。
        if (!inTab && !seenInTab.containsKey(player.getUUID())) {
            registerNpc(name);
            return null;
        }
        if (settings.detectHidden && isHiddenFromTab(player)) {
            logHiddenEvidence(player, name);
            return "隐藏";
        }
        return null;
    }

    /**
     * 「隐藏」判定命中时留一行证据（客户端日志，不打扰聊天栏）。
     *
     * <p>这条通道是<b>唯一</b>允许误断真人的地方（形态与 vanish 同形），一旦断错，用户丢掉的是
     * 整局挂机 —— 所以把当时的全部判据原样写进日志，下次误断能直接归因，不必再靠猜：
     * 名 / UUID / 现在是否在显示名单 / 记忆里有没有他 / 条目是否仍在下发 / 延迟 / 游戏模式 / 距离。</p>
     */
    private void logHiddenEvidence(Player player, String name) {
        PlayerInfo info = mc.getConnection() == null
            ? null : mc.getConnection().getPlayerInfo(player.getUUID());
        LOGGER.info("[管理员检测] 隐藏判定命中：名={} uuid={} 在显示名单={} 记忆里有他={} 条目仍在下发={}"
                + " 延迟={} 模式={} 隐身={} 距离={}",
            name, player.getUUID(), isInTab(player), seenInTab.containsKey(player.getUUID()),
            info != null, info == null ? -1 : info.getLatency(),
            info == null ? "-" : info.getGameMode(), player.isInvisible(),
            mc.player == null ? -1 : (int) mc.player.distanceTo(player));
    }

    /**
     * 这个实体在 Tab 玩家名单里吗 —— 客户端能拿到的「是不是真玩家」的唯一权威来源。
     *
     * <p><b>必须问「显示出来的那一份」</b>（真机 2026-09-22 04:33 实证：主城 NPC「苍涟绝岛」被判成
     * 隐藏管理员、当场断线）：服务端下发玩家列表项时带 {@code listed} 标志，插件给 NPC 的条目是
     * <b>{@code listed=false}</b> —— 客户端因此拿得到它的名字与皮肤（实体照常渲染、{@code getPlayerInfo}
     * 也有它），但它在 Tab 界面上<b>根本不显示</b>。能区分这两份的只有
     * {@link net.minecraft.client.multiplayer.ClientPacketListener#getListedOnlinePlayers()}；
     * {@code getPlayerInfo(uuid) != null} / {@code getOnlinePlayers()} 读的是底层全部条目，把
     * 「服务端故意不显示在 Tab 的人」也算成「在 Tab 名单里」，于是：① NPC 的名字被写进记忆；
     * ② 它的条目被移除、实体还在时，命中「曾在 Tab、现在不在」的 vanish 形态 → 误断线。</p>
     */
    private boolean isInTab(Player player) {
        if (mc.getConnection() == null) return false;
        PlayerInfo info = mc.getConnection().getPlayerInfo(player.getUUID());
        // PlayerInfo 没有覆写 equals：列名集里存的是同一批实例，contains 走引用比较，正是所需
        return info != null && mc.getConnection().getListedOnlinePlayers().contains(info);
    }

    /**
     * 判断玩家是否为旁观者。
     *
     * <p><b>为什么不能只看 {@code player.isSpectator()}</b>（用户 2026-09-19：「旁观者啊 为什么
     * 没检测？我不是添加了吗 检查一下是不是又 bug」）：那个标志只有<b>自己</b>（{@code LocalPlayer}）
     * 有真值 —— 别人的游戏模式不随实体下发到客户端，远程玩家实体上恒为 {@code false}。
     * 唯一可靠来源是 Tab 列表里的 {@code PlayerInfo#getGameMode()}（与创造检测同一个字段，
     * 服务端必定下发），因此这里先问实体、再退回 Tab。</p>
     *
     * <p>仍拿不到的情况见 {@link #scanTabSpectators}：旁观者对其他玩家不可见，
     * 服务器往往连实体都不下发，那时连这个循环都进不来。</p>
     */
    private boolean isSpectator(Player player) {
        if (player.isSpectator()) return true;
        return getGameMode(player) == GameType.SPECTATOR;
    }

    /**
     * 判断玩家是否为创造模式：优先 Tab 列表游戏模式，缺失时退回实体能力位。
     *
     * <p>能力位这条兜底只对「Tab 名单内、但 Tab 没带游戏模式」的玩家有意义 —— 调用方
     * {@link #getThreatReason} 已把不在 Tab 名单的实体（插件 NPC）挡在外面，否则它们的
     * {@code instabuild} 会被当成管理员。</p>
     */
    private boolean isCreative(Player player) {
        GameType gameMode = getGameMode(player);
        if (gameMode != null) return gameMode == GameType.CREATIVE;
        return player.getAbilities().instabuild;
    }

    /**
     * 判断玩家是否被 vanish 插件隐藏（曾经显示在 Tab 名单里，现在不在显示名单里）。
     *
     * <p><b>本项目加强（用户 2026-09-16 裁定「能正常识别玩家过滤 npc 就行」，登记在迁移记录里）</b>：
     * 旧实现只看「不在 Tab 列表」，在带插件 NPC 的服务器上会把 NPC 判成管理员 —— 实测
     * {@code long.kkwmc.cn} 的新手向导 NPC（名字本身就是提示语）就命中了，导致模块一开就断线。
     * 客户端无法直接问「你是不是 NPC」，因此只认一条信号：</p>
     *
     * <ul>
     *     <li><b>本次会话里在 Tab 显示名单上见过他（按 UUID）</b>：vanish 的轨迹必然是「先显示、后消失」，
     *         而插件 NPC 从建立那一刻就是 {@code listed=false}，永远进不了这份记忆。</li>
     * </ul>
     *
     * <p><b>两个渠道都算消失</b>：vanish 插件有的把条目移除（{@code ClientboundPlayerInfoRemovePacket}），
     * 有的只是把它改回 {@code listed=false}（条目仍在、Tab 里看不见）—— 对玩家而言两者一样，
     * 因此这里统一用 {@link #isInTab}（显示名单）判，不再用「{@code getPlayerInfo} 还在不在」。</p>
     *
     * <p><b>为什么删掉了「他没有自定义名字」那条兜底</b>（用户 2026-09-22：「npc 还是当成管理员了」）：
     * 出售商人这类插件 NPC 用的是假玩家实体，名字来自档案名、并不占用自定义名字组件，因此恰好落进
     * 「不在 Tab + 没自定义名字」这个组合，被当成 vanish 的隐藏管理员，走到收购点卖矿就断线。
     * 拿「有没有铭牌」判 NPC 一开始就选错了信号 —— 铭牌是插件给 NPC 加的装饰，不是它的必要条件。</p>
     *
     * <p><b>残留边界</b>：管理员若<b>在你进服前就已经隐身</b>（本次会话里从没在 Tab 名单显示过），
     * 或模块在世界里中途开启、丢掉了前半份记忆，则判不出来 —— 这是客户端侧无法消除的固有盲区：
     * 这种形态与插件 NPC 在客户端完全同形，宁可漏检也不能误断。名单页的黑名单可以按名字强制命中。</p>
     */
    private boolean isHiddenFromTab(Player player) {
        if (mc.getConnection() == null) return false;
        return !isInTab(player) && seenInTab.containsKey(player.getUUID());
    }

    /**
     * 把识别出的插件 NPC 自动登记进白名单，并播报一条（用户 2026-09-22：「不能自动识别 npc 自己
     * 添加到白名单吗」）。
     *
     * <p><b>走到这里的实体是什么</b>：既不在 Tab 显示名单、本次会话也从没在显示名单上出现过 —— 即
     * {@link #getThreatReason} 判定的插件 NPC。登记后它不再进检测/播报/ESP，且下次会话依然豁免
     * （白名单随设置一起落盘）。写进去的就是「名单」页那份白名单，用户看得见、删得掉，
     * 因此这里只播报一次「新登记」，已在名单里的直接返回、不重复打扰。</p>
     *
     * <p><b>两个保险</b>：</p>
     * <ul>
     *     <li>只在实体内进过检测范围时登记 —— 主循环的距离判断在 {@link #getThreatReason} 之前，
     *         远处的 NPC 不会被写进名单；</li>
     *     <li>显示名单为空时不登记 —— 那种情况说明玩家列表根本没拿到（连接异常 / 服务端不下发），
     *         「显示名单」这条判据失效，此时任何一个真人都会被当成 NPC，绝不能拿它往白名单里写。</li>
     * </ul>
     */
    private void registerNpc(String name) {
        if (name == null || name.isBlank()) return;
        if (seenInTab.isEmpty()) return;
        if (AdminDetectorSettings.containsName(settings.whitelist, name)) return;
        settings.whitelist.add(name);
        ModuleManager.saveSettings(this);
        ClientChat.send(MESSAGE_MODULE, "§a✓ 识别到插件 NPC §8▸ " + highlightText(name)
            + " §7已自动加入白名单 §8（可在「名单」页移除）");
    }

    /** 记下本刻 Tab <b>显示名单</b>上的全部玩家（UUID → 名字，只增不减：要的就是「曾经显示过」这份记忆）。 */
    private void rememberTabNames() {
        if (mc.getConnection() == null) return;
        for (PlayerInfo info : mc.getConnection().getListedOnlinePlayers()) {
            if (info == null || info.getProfile() == null) continue;
            UUID id = info.getProfile().id();
            String name = info.getProfile().name();
            if (id == null || name == null || name.isBlank()) continue;
            seenInTab.put(id, name);
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
