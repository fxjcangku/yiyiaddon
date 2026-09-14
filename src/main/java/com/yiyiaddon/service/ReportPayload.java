package com.yiyiaddon.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yiyiaddon.core.BackendLatency;
import com.yiyiaddon.model.ClientNetworkInfo;
import com.yiyiaddon.model.PlayerActivity;
import com.yiyiaddon.platform.ClientIdentity;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.NetworkInfoProbe;
import com.yiyiaddon.platform.PlayerSampler;

import java.util.TimeZone;

/**
 * 上报请求体装配：注册与心跳共用同一套身份、网络与活动字段，集中在此处避免两处字段漂移。
 */
final class ReportPayload {

    private ReportPayload() {
    }

    /** {@code /api/register} 请求体。 */
    static JsonObject register() {
        JsonObject body = identity();
        body.addProperty("version", ClientIdentity.version());
        body.addProperty("minecraft_version", ClientIdentity.minecraftVersion());
        body.addProperty("server_ip", GameProbe.serverIp());
        body.addProperty("server_name", GameProbe.serverName());

        ClientNetworkInfo network = NetworkInfoProbe.resolve();
        body.addProperty("real_ip", network.ip());
        body.addProperty("real_country", network.countryCode());
        body.addProperty("is_using_proxy", network.proxy());
        body.addProperty("proxy_type", network.proxyType());
        addNetworkFields(body, network);

        body.addProperty("server_latency", GameProbe.serverLatency());
        body.addProperty("network_latency", latencyOrNull());
        body.add("player_activity", activity());
        return body;
    }

    /** {@code /api/heartbeat} 请求体。 */
    static JsonObject heartbeat() {
        JsonObject body = identity();
        body.addProperty("status", GameProbe.status());
        body.addProperty("server_ip", GameProbe.serverIp());
        body.addProperty("server_name", GameProbe.serverName());
        body.addProperty("server_latency", GameProbe.serverLatency());
        body.addProperty("network_latency", latencyOrNull());

        ClientNetworkInfo network = NetworkInfoProbe.resolve();
        body.addProperty("real_country", network.countryCode());
        body.addProperty("is_using_proxy", network.proxy());
        addNetworkFields(body, network);

        body.add("player_activity", activity());
        return body;
    }

    /** 身份与功能字段。 */
    private static JsonObject identity() {
        JsonObject body = new JsonObject();
        String name = ClientIdentity.name();
        boolean premium = ClientIdentity.premium();

        body.addProperty("uuid", ClientIdentity.uuidString());
        body.addProperty("name", name);
        body.addProperty("gamertag", premium ? name : null);
        body.addProperty("xuid", ClientIdentity.xuid());

        JsonArray modules = new JsonArray();
        for (String module : GameProbe.enabledModules()) {
            if (module != null && !module.isBlank()) modules.add(module);
        }
        body.add("enabled_modules", modules);
        return body;
    }

    private static void addNetworkFields(JsonObject body, ClientNetworkInfo network) {
        body.addProperty("client_timezone", TimeZone.getDefault().getID());
        body.addProperty("client_isp", network.isp());
        body.addProperty("client_as_org", network.asOrg());
        body.addProperty("client_asn", network.asn() > 0 ? network.asn() : null);
    }

    private static Number latencyOrNull() {
        long latency = BackendLatency.millis();
        return latency < 0L ? null : latency;
    }

    private static JsonObject activity() {
        PlayerActivity activity = PlayerSampler.sample();
        return activity == null ? null : activity.toJson();
    }
}
