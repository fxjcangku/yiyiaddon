package com.yiyiaddon.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.core.Json;
import com.yiyiaddon.model.StatsSnapshot;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 公开统计读取：{@code /api/stats} 的脱敏数据，供界面展示使用。
 */
public final class StatsService {

    private static final Duration TIMEOUT = Duration.ofSeconds(12);
    private static final long TIMESTAMP_MILLIS_THRESHOLD = 10_000_000_000L;

    private StatsService() {
    }

    /**
     * 拉取一次统计快照。
     *
     * @return 成功时返回数据，失败返回 {@link StatsSnapshot#empty()}，不会抛出异常
     */
    public static StatsSnapshot fetch() {
        HttpApi.Response response = HttpApi.get("/api/stats?t=" + System.currentTimeMillis(), TIMEOUT);
        JsonObject root = response.json();
        if (!response.ok() || root == null || root.get("total_users") == null) return StatsSnapshot.empty();

        int totalUsers = Json.integer(root, "total_users", 0);
        return new StatsSnapshot(
                totalUsers,
                Json.integer(root, "total_uses", totalUsers),
                // 后端在不同版本下发过 active_24h / active_users_24h 两个字段名，这里都兼容。
                Json.integer(root, "active_users_24h", Json.integer(root, "active_24h", 0)),
                Json.integer(root, "online_users", 0),
                recentUsers(root));
    }

    private static List<StatsSnapshot.RecentUser> recentUsers(JsonObject root) {
        JsonArray array = Json.array(root, "recent_users");
        List<StatsSnapshot.RecentUser> users = new ArrayList<>(array.size());
        for (JsonElement element : array) {
            if (!element.isJsonObject()) continue;
            JsonObject user = element.getAsJsonObject();
            users.add(new StatsSnapshot.RecentUser(
                    Json.string(user, "name", "未知玩家"),
                    Json.string(user, "server_name", null),
                    Json.bool(user, "is_online", false),
                    normalizeTimestamp(Json.decimal(user, "last_seen", 0L))));
        }
        return users;
    }

    /** 秒级时间戳统一换算为毫秒。 */
    private static long normalizeTimestamp(long timestamp) {
        return timestamp > 0L && timestamp < TIMESTAMP_MILLIS_THRESHOLD ? timestamp * 1000L : timestamp;
    }
}
