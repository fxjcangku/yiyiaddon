package com.yiyiaddon.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.core.Json;
import com.yiyiaddon.model.ChatMessage;
import com.yiyiaddon.platform.ClientIdentity;
import com.yiyiaddon.ui.render.HudBanner;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 跨服聊天与管理员消息。
 *
 * <p>与后端的三个交互：在线玩家查询、频道/私聊发送、未读消息轮询与回复。
 * 发送与回复都是同步阻塞方法，调用方需自行放到后台线程或在 UI 动作中直接调用。</p>
 */
public final class ChatService {

    /** 后端限制的单条消息最大长度。 */
    public static final int MESSAGE_LIMIT = 300;

    /** 管理员消息横幅的标题与底部提示（提示逐字对应 {@link com.yiyiaddon.command.ReplyCommand} 的用法）。 */
    private static final String ADMIN_TITLE = "管理员消息";
    private static final String REPLY_HINT = "使用 .回复 <内容> 回复";

    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final Duration SHORT_TIMEOUT = Duration.ofSeconds(5);
    private static final long POLL_INTERVAL_SECONDS = 3L;

    private static volatile ScheduledExecutorService poller;

    private ChatService() {
    }

    /** 开始轮询未读消息；进服后调用，幂等。 */
    public static synchronized void start() {
        if (poller != null) return;
        if (!RemoteConfigService.flags().enabled(RemoteConfigService.MESSAGE_POLL)) return;
        poller = BackgroundTasks.schedule("yiyiaddon-message-poller", POLL_INTERVAL_SECONDS, TimeUnit.SECONDS,
                ChatService::poll);
    }

    /** 停止轮询；断开连接时调用。 */
    public static synchronized void stop() {
        if (poller == null) return;
        poller.shutdownNow();
        poller = null;
    }

    /** 查询当前在线玩家名单；失败返回空列表。 */
    public static List<String> onlinePlayers() {
        HttpApi.Response response = HttpApi.get("/api/chat/online", SHORT_TIMEOUT);
        JsonArray players = Json.array(response.json(), "players");
        List<String> names = new ArrayList<>(players.size());
        for (JsonElement element : players) {
            if (!element.isJsonObject()) continue;
            String name = Json.string(element.getAsJsonObject(), "name", null);
            if (name != null && !name.isBlank()) names.add(name);
        }
        return names;
    }

    /** 发到公共频道。 */
    public static boolean broadcast(String message) {
        return send(null, message);
    }

    /**
     * 发送消息。
     *
     * @param targetName 目标玩家名；{@code null} 表示公共频道
     */
    public static boolean send(String targetName, String message) {
        String text = message == null ? "" : message.trim();
        if (text.isEmpty() || text.length() > MESSAGE_LIMIT) return false;

        JsonObject body = new JsonObject();
        body.addProperty("uuid", ClientIdentity.uuidString());
        body.addProperty("username", ClientIdentity.name());
        body.addProperty("target_name", targetName);
        body.addProperty("message", text);
        return HttpApi.post("/api/chat/send", body, SHORT_TIMEOUT).ok();
    }

    /** 回复管理员。 */
    public static boolean reply(String message) {
        String text = message == null ? "" : message.trim();
        if (text.isEmpty()) return false;

        JsonObject body = new JsonObject();
        body.addProperty("uuid", ClientIdentity.uuidString());
        body.addProperty("username", ClientIdentity.name());
        body.addProperty("message", text);
        return HttpApi.post("/api/messages/reply", body, TIMEOUT).ok();
    }

    /** 拉取一次未读消息并展示；返回本次收到的消息。 */
    public static List<ChatMessage> pollNow() {
        String uuid = ClientIdentity.uuidString();
        if (uuid == null) return List.of();

        JsonObject body = new JsonObject();
        body.addProperty("uuid", uuid);
        HttpApi.Response response = HttpApi.post("/api/messages/poll", body, TIMEOUT);
        JsonObject root = response.json();
        if (!response.ok() || root == null) return List.of();

        List<ChatMessage> messages = parse(root);
        for (ChatMessage message : messages) {
            if (message.fromAdmin()) {
                // 管理员消息走 HUD 顶部横幅（十秒），不进聊天栏：聊天栏那行会混在服务器刷屏里被冲走，
                // 而这条消息是要玩家当场看见并回复的（用户 2026-09-21 的界面要求）。
                HudBanner.show(ADMIN_TITLE, message.message(), REPLY_HINT);
                continue;
            }
            ClientChat.raw(render(message));
        }
        return messages;
    }

    private static void poll() {
        try {
            if (ClientIdentity.uuidString() == null) return;
            pollNow();
        } catch (Exception ignored) {
            // 轮询失败静默跳过，等待下一轮。
        }
    }

    private static List<ChatMessage> parse(JsonObject root) {
        JsonArray array = Json.array(root, "messages");
        List<ChatMessage> messages = new ArrayList<>(array.size());
        for (JsonElement element : array) {
            if (!element.isJsonObject()) continue;
            JsonObject item = element.getAsJsonObject();
            messages.add(new ChatMessage(
                    Json.decimal(item, "id", 0L),
                    Json.string(item, "message", ""),
                    Json.string(item, "sender", ""),
                    Json.bool(item, "from_admin", false),
                    Json.bool(item, "is_premium", false),
                    Json.decimal(item, "created_at", 0L)));
        }
        return messages;
    }

    private static String render(ChatMessage message) {
        String account = message.premium() ? "§a[正版] " : "§7[离线] ";
        return "§d[聊天] " + account + "§e" + message.sender() + "§8：§f" + message.message();
    }
}
