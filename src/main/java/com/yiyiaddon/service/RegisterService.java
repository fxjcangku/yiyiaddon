package com.yiyiaddon.service;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.core.Json;
import com.yiyiaddon.model.RegisterOutcome;
import com.yiyiaddon.platform.ClientIdentity;

import java.time.Duration;

/**
 * 用户注册与欢迎播报：进服后上报身份并展示使用排名。
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
                if (!response.ok() || root == null) {
                    ClientChat.send("§c注册上报失败（HTTP " + response.status() + "）");
                    return;
                }
                showWelcome(new RegisterOutcome(
                        Json.bool(root, "is_new_user", true),
                        Json.integer(root, "rank", -1),
                        Json.integer(root, "total_users", -1),
                        Json.integer(root, "is_premium", 0) == 1));
            } catch (Exception e) {
                ClientChat.send("§c注册上报异常：" + e.getMessage());
            }
        });
    }

    /** 断开连接时重置，下次进服重新注册。 */
    public static void reset() {
        registered = false;
    }

    private static void showWelcome(RegisterOutcome outcome) {
        String name = ClientIdentity.name();
        String account = outcome.premium() ? "§a§l[正版]" : "§c§l[离线]";
        String rankLine = outcome.newUser()
                ? "§7你是第 §e§l#" + outcome.rank() + " §7个使用者 §a§l✓"
                : "§7欢迎回来，你是第 §e§l#" + outcome.rank() + " §7个使用者";

        ClientChat.raw("§3§m───────────────────────────────────");
        ClientChat.raw("§3│ " + account + " §6§l" + name);
        ClientChat.raw("§3│ " + rankLine);
        ClientChat.raw("§7当前已有 §2§l" + outcome.totalUsers() + " §f§l位玩家使用");
        ClientChat.raw("§3§m───────────────────────────────────");
    }
}
