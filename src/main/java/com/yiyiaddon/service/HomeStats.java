package com.yiyiaddon.service;

import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.model.ClientNetworkInfo;
import com.yiyiaddon.model.StatsSnapshot;
import com.yiyiaddon.platform.ClientIdentity;
import com.yiyiaddon.platform.NetworkInfoProbe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 首页「玩家 / 后端数据」状态快照。
 *
 * <p>数据来源分三类：会话身份（本地读取）、出口归属（第三方探测，内部缓存 10 分钟）、
 * 后端统计与存活（{@code /api/stats} 与延迟测量）。全部请求都在后台守护线程上执行，
 * 渲染线程只读 volatile 字段，不做任何网络等待。</p>
 *
 * <p>刷新周期 60 秒；同一时刻只允许一次刷新在跑，避免慢请求堆积。</p>
 */
public final class HomeStats {

    private static final long INTERVAL_SECONDS = 60L;

    private static final AtomicBoolean STARTED = new AtomicBoolean();
    private static final AtomicBoolean REFRESHING = new AtomicBoolean();

    private static volatile int rank = -1;
    private static volatile int totalUsers = -1;
    private static volatile boolean backendOnline;
    private static volatile long lastSyncMillis;

    private static volatile String ip;
    private static volatile String countryCode;
    private static volatile boolean networkReachable;

    private HomeStats() {
    }

    /** 启动后台刷新；幂等。 */
    public static void start() {
        if (!STARTED.compareAndSet(false, true)) return;
        BackgroundTasks.schedule("yiyiaddon-home-stats", INTERVAL_SECONDS, TimeUnit.SECONDS, HomeStats::refresh);
    }

    /** 注册结果回填：排名与累计用户数只有注册接口才下发。 */
    public static void acceptRegister(int rankValue, int totalUsersValue) {
        if (rankValue > 0) rank = rankValue;
        if (totalUsersValue > 0) totalUsers = totalUsersValue;
    }

    /** 本轮排名失效：断开连接后重新注册会拿到新排名。 */
    public static void resetRank() {
        rank = -1;
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

    public static int rank() {
        return rank;
    }

    public static int totalUsers() {
        return totalUsers;
    }

    /** 账户身份：正版 / 离线。 */
    public static boolean premium() {
        return ClientIdentity.premium();
    }

    private static void refresh() {
        if (!REFRESHING.compareAndSet(false, true)) return;
        try {
            ClientNetworkInfo network = NetworkInfoProbe.resolve();
            ip = network.ip();
            countryCode = network.countryCode();
            networkReachable = network.ip() != null;

            StatsSnapshot stats = StatsService.fetch();
            if (stats.totalUsers() > 0) totalUsers = stats.totalUsers();

            boolean online = HttpApi.measureLatency() >= 0L;
            backendOnline = online;
            if (online) lastSyncMillis = System.currentTimeMillis();
        } finally {
            REFRESHING.set(false);
        }
    }
}