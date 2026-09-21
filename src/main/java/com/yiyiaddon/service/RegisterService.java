package com.yiyiaddon.service;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.core.Json;
import com.yiyiaddon.platform.ClientIdentity;

import java.time.Duration;

/**
 * 用户注册：进服后上报身份，并把后端返回的排名与累计用户数交给首页展示。
 *
 * <p>玩家连接完成后调用 {@link #register()}，断开连接时调用 {@link #reset()}。</p>
 */
public final class RegisterService {

    private static final Duration TIMEOUT = Duration.ofSeconds(8);

    private static volatile boolean registered;

    private RegisterService() {
    }

    /** 异步注册；同一会话内重复调用只生效一次。 */
    public static void register() {
        if (registered) return;
        if (!RemoteConfigService.flags().enabled(RemoteConfigService.STATS_REPORT)) return;
        if (ClientIdentity.fakeName(ClientIdentity.name())) return;

        registered = true;
        BackgroundTasks.run("yiyiaddon-register", () -> {
            try {
                HttpApi.Response response = HttpApi.post("/api/register", ReportPayload.register(), TIMEOUT);
                JsonObject root = response.json();
                if (!response.ok() || root == null) return;
                // is_premium 是后端按 Mojang 档案核验的正版结论（后台面板同一口径）；拿不到（老后端）时为 -1，按未核验处理
                HomeStats.acceptRegister(Json.integer(root, "rank", -1),
                        Json.integer(root, "total_users", -1),
                        Json.integer(root, "is_premium", -1) == 1);
            } catch (Exception ignored) {
                // 注册失败只影响首页排名展示，不打扰玩家
            }
        });
    }

    /** 断开连接时重置，下次进服重新注册。 */
    public static void reset() {
        registered = false;
        HomeStats.resetRank();
    }
}
