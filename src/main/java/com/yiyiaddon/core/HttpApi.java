package com.yiyiaddon.core;

import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * 后端 HTTP 传输层：只负责发请求、取响应，不做任何业务判断。
 *
 * <p>全部方法都不抛异常：网络不可用时返回 {@link Response#FAILED}（状态码 0），
 * 调用方只需判断 {@link Response#ok()}，不会因为后台不可达影响游戏运行。</p>
 */
public final class HttpApi {

    /** 后端根地址（Cloudflare Workers，自定义域名）。 */
    public static final String BASE_URL = "https://yiyiaddon.asia";

    private static final String USER_AGENT = "yiyiaddon-minecraft-client";
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(5);

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(CONNECT_TIMEOUT)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private HttpApi() {
    }

    /** 一次请求的结果快照。 */
    public record Response(int status, String body) {

        public static final Response FAILED = new Response(0, "");

        public boolean ok() {
            return status >= 200 && status < 300;
        }

        /** 响应体解析为 JSON 对象；非 JSON 或请求失败返回 {@code null}。 */
        public JsonObject json() {
            return Json.parse(body);
        }
    }

    public static Response get(String path, Duration timeout) {
        return send(HttpRequest.newBuilder(uri(path)).timeout(timeout).header("Accept", "application/json").GET());
    }

    /** 对任意绝对地址发起 GET，用于第三方公开查询接口。 */
    public static Response getAbsolute(String url, Duration timeout) {
        return send(HttpRequest.newBuilder(URI.create(url)).timeout(timeout).header("Accept", "application/json").GET());
    }

    public static Response post(String path, JsonObject body, Duration timeout) {
        return send(HttpRequest.newBuilder(uri(path))
                .timeout(timeout)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString())));
    }

    /** 请求体为原始字符串的 POST，缺省 JSON 头。 */
    public static Response postRaw(String path, String json, Duration timeout) {
        return send(HttpRequest.newBuilder(uri(path))
                .timeout(timeout)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)));
    }

    /**
     * 测量到后端的往返延迟，用于心跳上报的 {@code network_latency}。
     *
     * @return 毫秒；失败返回 -1
     */
    public static long measureLatency() {
        long begin = System.nanoTime();
        Response response = get("/api/stats", Duration.ofSeconds(3));
        if (response.status() == 0) return -1L;
        return (System.nanoTime() - begin) / 1_000_000L;
    }

    private static URI uri(String path) {
        return URI.create(BASE_URL + path);
    }

    private static Response send(HttpRequest.Builder builder) {
        try {
            HttpResponse<String> response = CLIENT.send(builder.header("User-Agent", USER_AGENT).build(),
                    HttpResponse.BodyHandlers.ofString());
            return new Response(response.statusCode(), response.body() == null ? "" : response.body());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Response.FAILED;
        } catch (Exception e) {
            return Response.FAILED;
        }
    }
}
