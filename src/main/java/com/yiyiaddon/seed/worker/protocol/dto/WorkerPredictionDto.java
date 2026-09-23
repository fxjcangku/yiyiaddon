package com.yiyiaddon.seed.worker.protocol.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.seed.worker.protocol.WorkerJson;
import java.util.ArrayList;
import java.util.List;

/**
 * 种子挖矿 · Worker IPC · <b>一次预测的完整结果</b>（传输层，对应正式层 {@code PredictionResult}）。
 *
 * <p><b>成功与失败必须可区分</b>（阶段 232 口径第六十七节）：{@code success=true, ores=[]} 的含义是
 * 「这个区块确实没有钻石」，{@code success=false} 的含义是「这次预测不成立」。
 * 两者绝不允许被 IPC 抹平成同一种样子。</p>
 *
 * @param success       本次预测是否成立
 * @param failureReason 失败原因（中文；成功时为 null）
 * @param chunkX        目标区块 X
 * @param chunkZ        目标区块 Z
 * @param seed          被试种子
 * @param dimension     维度标识（{@code minecraft:overworld}）
 * @param oreType       矿物种类（正式层枚举名）
 * @param elapsedMillis 预测耗时（毫秒）
 * @param stats         统计与诊断
 * @param ores          预测出来的矿物
 */
public record WorkerPredictionDto(boolean success, String failureReason, int chunkX, int chunkZ, long seed,
                                  String dimension, String oreType, long elapsedMillis, WorkerStatsDto stats,
                                  List<WorkerOreDto> ores) {

    public WorkerPredictionDto {
        ores = List.copyOf(ores);
    }

    /** 会话此刻累计持有的离线区块数（口径第十九节要求的读数，与 {@code stats.heldChunks} 同源）。 */
    public int heldChunks() {
        return stats.heldChunks();
    }

    /** 序列化。 */
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("success", success);
        WorkerJson.putNullable(object, "failureReason", failureReason);
        object.add("targetChunk", new WorkerChunkRef(chunkX, chunkZ).toJson());
        object.addProperty("chunkX", chunkX);
        object.addProperty("chunkZ", chunkZ);
        object.addProperty("seed", seed);
        object.addProperty("dimension", dimension);
        object.addProperty("oreType", oreType);
        object.addProperty("elapsedMillis", elapsedMillis);
        object.addProperty("heldChunks", stats.heldChunks());
        object.add("stats", stats.toJson());
        JsonArray oreArray = new JsonArray();
        for (WorkerOreDto ore : ores) {
            oreArray.add(ore.toJson());
        }
        object.add("ores", oreArray);
        return object;
    }

    /** 反序列化。 */
    public static WorkerPredictionDto fromJson(JsonObject object) {
        JsonObject chunk = WorkerJson.reqObject(object, "targetChunk");
        List<WorkerOreDto> ores = new ArrayList<>();
        for (JsonElement item : WorkerJson.optArray(object, "ores")) {
            if (!item.isJsonObject()) {
                continue;
            }
            ores.add(WorkerOreDto.fromJson(item.getAsJsonObject()));
        }
        return new WorkerPredictionDto(
                WorkerJson.reqBool(object, "success"),
                WorkerJson.optString(object, "failureReason"),
                WorkerJson.reqInt(chunk, "x"),
                WorkerJson.reqInt(chunk, "z"),
                WorkerJson.reqLong(object, "seed"),
                WorkerJson.reqString(object, "dimension"),
                WorkerJson.reqString(object, "oreType"),
                WorkerJson.optLong(object, "elapsedMillis", 0L),
                WorkerStatsDto.fromJson(WorkerJson.reqObject(object, "stats")),
                ores);
    }
}
