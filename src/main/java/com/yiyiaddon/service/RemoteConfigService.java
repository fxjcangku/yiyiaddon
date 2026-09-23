package com.yiyiaddon.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.model.RemoteFlags;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 远程配置：定期拉取 {@code /api/config}，由后台实时下发功能开关。
 */
public final class RemoteConfigService {

    /** 统计上报总开关。 */
    public static final String STATS_REPORT = "stats_report_enabled";
    /** 在线心跳与模块活动上报开关。 */
    public static final String HEARTBEAT_REPORT = "heartbeat_report_enabled";
    /** 高速移动 / 瞬移等异常行为上报开关。 */
    public static final String ANOMALY_REPORT = "anomaly_report_enabled";
    /** 崩溃上报开关。 */
    public static final String CRASH_REPORT = "crash_report_enabled";
    /** 消息轮询开关。 */
    public static final String MESSAGE_POLL = "message_poll_enabled";

    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    /**
     * 拉取周期 300 秒（原 60 秒）。
     *
     * <p>Cloudflare Workers 免费额度按请求数算（10 万/天，UTC 0 点重置），远端开关是三条轮询通道里
     * 最不着急的一条：后台改开关后客户端最多晚 5 分钟生效，而本通道降下来的额度能让在线人数统计
     * 多撑一个玩家（60 秒 = 1 440 请求/玩家/天 → 300 秒 = 288）。</p>
     */
    private static final long INTERVAL_SECONDS = 300L;

    private static final RemoteFlags FLAGS = new RemoteFlags();
    private static volatile boolean started;

    private RemoteConfigService() {
    }

    /** 启动轮询：立即拉取一次，之后每 {@link #INTERVAL_SECONDS} 秒刷新。幂等。 */
    public static void start() {
        if (started) return;
        started = true;
        BackgroundTasks.schedule("yiyiaddon-remote-config", INTERVAL_SECONDS, TimeUnit.SECONDS,
                RemoteConfigService::refresh);
    }

    public static RemoteFlags flags() {
        return FLAGS;
    }

    /** 同步拉取一次；失败时保留上一次结果。 */
    public static void refresh() {
        HttpApi.Response response = HttpApi.get("/api/config", TIMEOUT);
        if (!response.ok()) return;

        JsonObject root = response.json();
        if (root == null) return;
        JsonElement config = root.get("config");
        if (config == null || !config.isJsonObject()) return;

        Map<String, String> values = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : config.getAsJsonObject().entrySet()) {
            JsonElement value = entry.getValue();
            if (value != null && value.isJsonPrimitive()) values.put(entry.getKey(), value.getAsString());
        }
        FLAGS.replace(values);
    }
}
