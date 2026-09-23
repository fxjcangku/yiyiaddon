package com.yiyiaddon.seed.worker.protocol.dto;

import com.google.gson.JsonObject;
import com.yiyiaddon.seed.worker.protocol.WorkerJson;

/**
 * 种子挖矿 · Worker IPC · <b>会话描述</b>。
 *
 * <p>会话身份 = 「种子 + 维度」（阶段 232 口径第十四节）。换种子即换会话；旧会话必须被释放，
 * 绝不允许两个种子的离线世界在同一份缓存里串味。</p>
 *
 * @param sessionId 会话标识（Worker 侧生成，客户端只用于日志关联）
 * @param seed      会话种子
 * @param dimension 会话维度标识
 * @param status    会话状态（{@code OPEN} / {@code CLOSED}）
 */
public record WorkerSessionDto(String sessionId, long seed, String dimension, String status) {

    /** 已打开。 */
    public static final String STATUS_OPEN = "OPEN";

    /** 已关闭。 */
    public static final String STATUS_CLOSED = "CLOSED";

    /** 序列化。 */
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("sessionId", sessionId);
        object.addProperty("seed", seed);
        object.addProperty("dimension", dimension);
        object.addProperty("status", status);
        return object;
    }

    /** 反序列化。 */
    public static WorkerSessionDto fromJson(JsonObject object) {
        return new WorkerSessionDto(
                WorkerJson.reqString(object, "sessionId"),
                WorkerJson.reqLong(object, "seed"),
                WorkerJson.reqString(object, "dimension"),
                WorkerJson.reqString(object, "status"));
    }
}
