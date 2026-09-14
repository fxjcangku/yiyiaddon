package com.yiyiaddon.service;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.platform.ClientIdentity;
import com.yiyiaddon.platform.GameProbe;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 在线心跳与离线通知。
 *
 * <p>每 3 秒上报一次，后端 12 秒收不到心跳即判定离线；玩家断开连接时调用
 * {@link #reportOffline()} 可立即下线，无需等待超时。</p>
 */
public final class HeartbeatService {

    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final long INTERVAL_SECONDS = 3L;

    private static volatile boolean started;
    private static volatile String lastUuid;

    private HeartbeatService() {
    }

    /** 启动心跳；幂等，在客户端初始化时调用一次即可。 */
    public static void start() {
        if (started) return;
        started = true;
        BackgroundTasks.schedule("yiyiaddon-heartbeat", INTERVAL_SECONDS, TimeUnit.SECONDS, HeartbeatService::tick);
    }

    /** 玩家断开连接时立即上报离线。 */
    public static void reportOffline() {
        String uuid = lastUuid;
        if (uuid == null) return;
        lastUuid = null;

        JsonObject body = new JsonObject();
        body.addProperty("uuid", uuid);
        BackgroundTasks.run("yiyiaddon-offline-report",
                () -> HttpApi.post("/api/offline", body, Duration.ofSeconds(5)));
    }

    private static void tick() {
        if (!RemoteConfigService.flags().enabled(RemoteConfigService.STATS_REPORT)) return;
        if (!GameProbe.inWorld()) return;

        String name = ClientIdentity.name();
        String uuid = ClientIdentity.uuidString();
        if (uuid == null || name == null || ClientIdentity.fakeName(name)) return;

        // 本地回环与局域网测试不参与在线统计，避免开发期污染后台数据。
        if ("multiplayer".equals(GameProbe.status()) && isLocalAddress(GameProbe.serverIp())) return;

        if (HttpApi.post("/api/heartbeat", ReportPayload.heartbeat(), TIMEOUT).ok()) lastUuid = uuid;
    }

    private static boolean isLocalAddress(String ip) {
        if (ip == null) return false;
        String value = ip.trim().toLowerCase();
        return value.equals("localhost")
                || value.startsWith("127.")
                || value.startsWith("192.168.")
                || value.startsWith("10.")
                || value.startsWith("0.")
                || value.matches("^172\\.(1[6-9]|2\\d|3[01])\\..*")
                || value.equals("::1")
                || value.equals("[::1]");
    }
}
