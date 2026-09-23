package com.yiyiaddon.seed.worker.protocol.dto;

import com.google.gson.JsonObject;
import com.yiyiaddon.seed.worker.protocol.WorkerJson;

/**
 * 种子挖矿 · Worker IPC · <b>区块坐标</b>（传输层最小单元）。
 *
 * @param x 区块 X
 * @param z 区块 Z
 */
public record WorkerChunkRef(int x, int z) {

    /** 序列化。 */
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("x", x);
        object.addProperty("z", z);
        return object;
    }

    /** 反序列化。 */
    public static WorkerChunkRef fromJson(JsonObject object) {
        return new WorkerChunkRef(WorkerJson.reqInt(object, "x"), WorkerJson.reqInt(object, "z"));
    }
}
