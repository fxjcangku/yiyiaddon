package com.yiyiaddon.service;

import com.yiyiaddon.core.BackendLatency;
import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.model.ClientNetworkInfo;
import com.yiyiaddon.model.StatsSnapshot;
import com.yiyiaddon.platform.ClientIdentity;
import com.yiyiaddon.platform.NetworkInfoProbe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 首页「玩家 / 后端数据」状态快照。
 *
 * <p>数据来源分四类：会话身份（本地读取）、出口归属（第三方探测，内部缓存 10 分钟）、
 * 后端统计（<b>双通道</b>）与后端存活（{@link BackendLatency} 的往返测量）。
 * 全部请求都在后台守护线程上执行，渲染线程只读 volatile 字段，不做任何网络等待。</p>
 *
 * <p><b>统计的双通道</b>（2026-09-20 改造，目标是「省额度 + 刷得快」）：</p>
 * <ol>
 *   <li><b>心跳通道</b>（世界内，零额外请求）：客户端每 15 秒一次的心跳本来就带身份、位置、延迟上报，
 *       后端在写库心跳（30 秒一次）时把累计用户数与在线人数一并回带，被节流的心跳读后端内存缓存回带 ——
 *       于是首页的「累计使用人数」与「在线人数」每 30 秒跟一次新，客户端一次 /api/stats 都不用发；</li>
 *   <li><b>轮询通道</b>（主菜单 / 老后端兜底）：{@code /api/stats} 每 {@link #INTERVAL_SECONDS} 秒一次。
 *       心跳通道最近 {@link #HEARTBEAT_FRESH_MILLIS} 内送回数据时这一轮直接跳过。</li>
 * </ol>
 *
 * <p>「后端状态」不再单独打一次 {@code /api/stats}：改由 {@link BackendLatency} 提供 ——
 * 它测的是零 D1 查询的 {@code /api/ping}，且失败会立即反映出来，不会沿用旧的成功值。</p>
 */
public final class HomeStats {

    /**
     * 轮询通道周期；心跳通道不在供数时（主菜单 / 老后端）才按这个节奏拉统计。
     *
     * <p>{@code /api/stats} 是 4 条 D1 查询的重路径，而 Cloudflare Workers 免费额度按请求数算，
     * 所以挂主菜单时也放慢到 60 秒（原 20 秒 = 4 320 请求/天，纯挂机也在烧额度）。</p>
     */
    private static final long INTERVAL_SECONDS = 60L;
    /**
     * 心跳通道的新鲜窗口：必须大于后端的写库节流周期（30 秒）+ 一次心跳间隔（15 秒），
     * 否则世界内会误判「通道没在供数」而恢复轮询。45 秒是下限，留 15 秒余量。
     */
    private static final long HEARTBEAT_FRESH_MILLIS = 60_000L;

    private static final AtomicBoolean STARTED = new AtomicBoolean();
    private static final AtomicBoolean REFRESHING = new AtomicBoolean();

    private static volatile int rank = -1;
    private static volatile int totalUsers = -1;
    /**
     * 本扩展<b>此刻在线</b>的人数（后端按心跳判定）；{@code -1} = 还没拿到过数据。
     *
     * <p>与 {@link #totalUsers}（累计用户数）不同：这个是实时值，会随别人上下线变化，
     * 首页账户条把它显示在玩家名后面（用户 2026-09-20：「名字后面」+「是当前在线人数」）。</p>
     */
    private static volatile int onlineUsers = -1;
    private static volatile boolean backendOnline;
    private static volatile long lastSyncMillis;
    /** 心跳通道最近一次送回统计的时刻；0 表示从未（老后端或还没进过世界）。 */
    private static volatile long heartbeatDataMillis;

    private static volatile String ip;
    private static volatile String countryCode;
    private static volatile String region;
    private static volatile String proxyType;
    private static volatile boolean networkReachable;

    /**
     * 后端已确认<b>本会话是正版</b>（只升不降，断线后复位）。
     *
     * <p><b>为什么要听后端的</b>（用户 2026-09-21：「我明明正版号给我判成离线了 好多个号都这样」）：
     * 客户端本地只能看会话特征，而线上库里这些号的 {@code xuid} 全是 NULL ——
     * 只看 XUID 必然把正版判成离线。后端 {@code /api/register} 会用
     * {@code resolvePremium} 按名字查 Mojang 官方档案（含镜像回退）并把结论放在 {@code is_premium}
     * 里回带，这正是后台面板显示的口径，首页跟着它走，两处才不会一个说正版、一个说离线。</p>
     *
     * <p><b>只升不降</b>：后端在 Mojang 暂时不可达时也会保留既有结论、绝不把已确认正版降级
     * （见 {@code worker.js} 的 {@code resolvePremium} 注释），这里同样只在为真时置位 ——
     * 一次查询失败不该把界面上的「正版」打回「离线」。</p>
     */
    private static volatile boolean premiumVerified;

    private HomeStats() {
    }

    /** 启动后台刷新；幂等。 */
    public static void start() {
        if (!STARTED.compareAndSet(false, true)) return;
        BackgroundTasks.schedule("yiyiaddon-home-stats", INTERVAL_SECONDS, TimeUnit.SECONDS, HomeStats::refresh);
    }

    /**
     * 注册结果回填：排名、累计用户数与后端核验的正版结论只有注册接口才下发。
     *
     * @param premiumVerifiedValue 后端 {@code is_premium == 1}；只有为真才置位（见 {@link #premiumVerified}）
     */
    public static void acceptRegister(int rankValue, int totalUsersValue, boolean premiumVerifiedValue) {
        if (rankValue > 0) rank = rankValue;
        if (totalUsersValue > 0) totalUsers = totalUsersValue;
        if (premiumVerifiedValue) premiumVerified = true;
    }

    /**
     * 心跳通道回填：心跳响应里带回的累计用户数与在线人数（写库心跳与被节流的心跳都带）。
     *
     * @param totalUsersValue 后端返回的累计用户数；{@code <= 0} 视为未带回（老后端），保留旧值
     * @param onlineValue     后端返回的在线人数；{@code <= 0} 视为未带回，保留旧值
     *                        （世界内在心跳的客户端自己必然在线，因此真值至少为 1）
     * @return 是否带回数据；调用方不需要它，但保留返回值便于自检与埋点
     */
    public static boolean acceptHeartbeat(int totalUsersValue, int onlineValue) {
        if (totalUsersValue <= 0) return false;
        totalUsers = totalUsersValue;
        if (onlineValue > 0) onlineUsers = onlineValue;
        heartbeatDataMillis = System.currentTimeMillis();
        return true;
    }

    /** 本轮排名失效：断开连接后重新注册会拿到新排名。 */
    public static void resetRank() {
        rank = -1;
        premiumVerified = false;
    }

    /** 最近一次同步成功的时间；从未成功过返回 0。 */
    public static long lastSyncMillis() {
        return lastSyncMillis;
    }

    public static boolean backendOnline() {
        return backendOnline;
    }

    public static boolean networkReachable() {
        return networkReachable;
    }

    public static String ip() {
        return ip;
    }

    public static String countryCode() {
        return countryCode;
    }

    /** 一级行政区英文原名；探测源未给出时为 null。 */
    public static String region() {
        return region;
    }

    /**
     * 出口被判定为代理的类型：{@code VPN} / {@code TOR} / {@code PROXY} / {@code MOBILE}。
     *
     * <p>取探测源的原始类型而不是 {@code proxy} 布尔值 —— {@code MOBILE}（蜂窝网络）也会被探测源
     * 标成 proxy，但那是运营商出口、不是代理，界面上不能当成 VPN 提示。</p>
     */
    public static String proxyType() {
        return proxyType;
    }

    public static int rank() {
        return rank;
    }

    public static int totalUsers() {
        return totalUsers;
    }

    /** 本扩展当前在线人数；{@code -1} = 还没拿到过数据（界面显示占位，不显示 0）。 */
    public static int onlineUsers() {
        return onlineUsers;
    }

    /** 账户身份：正版 / 离线。后端核验为真即正版，否则回落到本地会话特征（见 {@link ClientIdentity#premium()}）。 */
    public static boolean premium() {
        return premiumVerified || ClientIdentity.premium();
    }

    /** 心跳通道是否正在供数：最近 {@link #HEARTBEAT_FRESH_MILLIS} 内送回过统计。 */
    private static boolean heartbeatFresh() {
        long at = heartbeatDataMillis;
        return at > 0L && System.currentTimeMillis() - at < HEARTBEAT_FRESH_MILLIS;
    }

    private static void refresh() {
        if (!REFRESHING.compareAndSet(false, true)) return;
        try {
            // 出口归属走 10 分钟缓存，这里只是把缓存读出来，不产生网络请求。
            ClientNetworkInfo network = NetworkInfoProbe.resolve();
            ip = network.ip();
            countryCode = network.countryCode();
            region = network.region();
            proxyType = network.proxyType();
            networkReachable = network.ip() != null;

            // 心跳通道正在供数时不拉 /api/stats：世界内的刷新由心跳响应驱动，省掉这条 4 查询的重路径。
            if (!heartbeatFresh()) {
                StatsSnapshot stats = StatsService.fetch();
                if (stats.totalUsers() > 0) totalUsers = stats.totalUsers();
                if (stats.onlineUsers() > 0) onlineUsers = stats.onlineUsers();
            }

            // 后端存活与延迟：30 秒缓存，世界内由心跳刷新、主菜单由本轮刷新。
            boolean online = BackendLatency.millis() >= 0L;
            backendOnline = online;
            if (online) lastSyncMillis = System.currentTimeMillis();
        } finally {
            REFRESHING.set(false);
        }
    }
}