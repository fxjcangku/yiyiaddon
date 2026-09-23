package com.yiyiaddon.service;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.core.Json;
import com.yiyiaddon.platform.ClientIdentity;
import com.yiyiaddon.platform.GameProbe;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 在线心跳与离线通知。
 *
 * <p>每 15 秒上报一次（后端 90 秒收不到心跳才判定离线，见 {@code worker.js} 的
 * {@code HEARTBEAT_WRITE_INTERVAL} 与在线超时注释）；玩家断开连接时调用
 * {@link #reportOffline()} 可立即下线，无需等待超时。</p>
 *
 * <p><b>为什么不是 3 秒</b>：Cloudflare Workers 免费版的每日额度按<b>请求数</b>算（10 万/天，
 * UTC 0 点重置），后端写库节流只省 D1、不省额度。3 秒心跳 = 2.88 万请求/玩家/天，
 * 加上同频的消息轮询，一个玩家在线一天就能吃掉大半额度，配额打满后整站返回
 * {@code error code: 1027}（2026-09-23 实机发生）。15 秒下本通道降到 5 760 请求/玩家/天，
 * 在线感的代价只是「某人下线后最多 90 秒内仍显示在线」，与原本的 90 秒超时口径一致。</p>
 *
 * <p><b>顺带当统计通道</b>：心跳响应里带回后端的累计用户数与在线人数（写库心跳与被节流的心跳都带），
 * 由 {@link HomeStats#acceptHeartbeat(int, int)} 交给首页 —— 首页因此不必再单独轮询 {@code /api/stats}，
 * 刷新粒度就是后端的写库节流周期（30 秒）。</p>
 */
public final class HeartbeatService {

    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final long INTERVAL_SECONDS = 15L;

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
        if (!reportEnabled()) return;

        JsonObject body = new JsonObject();
        body.addProperty("uuid", uuid);
        BackgroundTasks.run("yiyiaddon-offline-report",
                () -> HttpApi.post("/api/offline", body, Duration.ofSeconds(5)));
    }

    /**
     * 心跳链路是否允许上报。
     *
     * <p>两道远程开关是「与」关系：{@code stats_report_enabled} 是统计总闸（旧口径），
     * {@code heartbeat_report_enabled} 是本通道单独的口子（后台面板「在线状态、延迟、模块和活动数据是否上报」）。
     * 键未下发时默认开启，因此后台没有配过任何开关时行为与旧版一致。</p>
     */
    private static boolean reportEnabled() {
        return RemoteConfigService.flags().enabled(RemoteConfigService.STATS_REPORT)
                && RemoteConfigService.flags().enabled(RemoteConfigService.HEARTBEAT_REPORT);
    }

    private static void tick() {
        if (!reportEnabled()) return;
        if (!GameProbe.inWorld()) return;

        String name = ClientIdentity.name();
        String uuid = ClientIdentity.uuidString();
        if (uuid == null || name == null || ClientIdentity.fakeName(name)) return;

        // 本地回环与局域网测试不参与在线统计，避免开发期污染后台数据。
        if ("multiplayer".equals(GameProbe.status()) && isLocalAddress(GameProbe.serverIp())) return;

        HttpApi.Response response = HttpApi.post("/api/heartbeat", ReportPayload.heartbeat(), TIMEOUT);
        if (!response.ok()) return;
        lastUuid = uuid;

        // 响应里带回累计用户数与在线人数（写库心跳与被节流的心跳都带）：交给首页，首页因此不必再轮询 /api/stats。
        // 老后端不返回这些字段时取 -1，HomeStats 会保留旧值并恢复自己的轮询兜底。
        JsonObject root = response.json();
        if (root != null) {
            HomeStats.acceptHeartbeat(Json.integer(root, "total_users", -1),
                    Json.integer(root, "online", -1));
        }
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
