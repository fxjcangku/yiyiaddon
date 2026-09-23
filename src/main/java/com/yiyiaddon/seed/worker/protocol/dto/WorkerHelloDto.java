package com.yiyiaddon.seed.worker.protocol.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.seed.worker.protocol.WorkerJson;
import java.util.ArrayList;
import java.util.List;

/**
 * 种子挖矿 · Worker IPC · <b>HELLO 应答</b>（Worker 自述身份）。
 *
 * <p>客户端据此做三项硬校验（阶段 232 口径第十节）：协议版本、Minecraft 版本、模组版本。
 * 任何一项不符都直接拒绝预测 —— 本阶段只实现 26.1.2 Worker，跨版本共用 Vanilla worldgen 是禁止项。</p>
 *
 * @param protocolVersion  业务协议版本（{@code SeedWorkerProtocol.VERSION}）
 * @param minecraftVersion Worker 侧的 Minecraft 版本
 * @param modVersion       Worker 侧的模组版本
 * @param workerPid        Worker 进程 PID（诊断用）
 * @param capabilities     能力声明
 */
public record WorkerHelloDto(int protocolVersion, String minecraftVersion, String modVersion, long workerPid,
                             List<String> capabilities) {

    public WorkerHelloDto {
        capabilities = List.copyOf(capabilities);
    }

    /** 是否声明了某项能力。 */
    public boolean hasCapability(String capability) {
        return capabilities.contains(capability);
    }

    /** 序列化。 */
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("protocolVersion", protocolVersion);
        object.addProperty("minecraftVersion", minecraftVersion);
        object.addProperty("modVersion", modVersion);
        object.addProperty("workerPid", workerPid);
        JsonArray caps = new JsonArray();
        for (String capability : capabilities) {
            caps.add(capability);
        }
        object.add("capabilities", caps);
        return object;
    }

    /** 反序列化。 */
    public static WorkerHelloDto fromJson(JsonObject object) {
        List<String> capabilities = new ArrayList<>();
        for (JsonElement item : WorkerJson.optArray(object, "capabilities")) {
            if (!item.isJsonNull()) {
                capabilities.add(item.getAsString());
            }
        }
        return new WorkerHelloDto(
                WorkerJson.reqInt(object, "protocolVersion"),
                WorkerJson.reqString(object, "minecraftVersion"),
                WorkerJson.reqString(object, "modVersion"),
                WorkerJson.reqLong(object, "workerPid"),
                capabilities);
    }
}
