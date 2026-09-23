package com.yiyiaddon.seed.worker.protocol.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.seed.worker.protocol.WorkerJson;
import java.util.ArrayList;
import java.util.List;

/**
 * 种子挖矿 · Worker IPC · <b>一次预测的统计与诊断</b>（传输层，与正式层
 * {@code PredictionResult.Stats} 一一对应）。
 *
 * <p>宿主 ChunkMap 查询次数（{@link #hostChunkSourceQueries}）必须为 0：它是「预测器有没有偷读
 * 真实世界」的唯一硬指标（阶段 232 口径第十六、四十七节）。</p>
 */
public record WorkerStatsDto(int protoChunks, long stageExecutions, long cacheHits, int heldChunks,
                             int hostChunkSourceQueries, int foreignWriterViewers,
                             boolean scheduleAnalysisExecuted, int deterministicCount,
                             int scheduleSensitiveCount, int unresolvedCount, List<String> notes) {

    public WorkerStatsDto {
        notes = List.copyOf(notes);
    }

    /** 序列化。 */
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("protoChunks", protoChunks);
        object.addProperty("stageExecutions", stageExecutions);
        object.addProperty("cacheHits", cacheHits);
        object.addProperty("heldChunks", heldChunks);
        object.addProperty("hostChunkSourceQueries", hostChunkSourceQueries);
        object.addProperty("foreignWriterViewers", foreignWriterViewers);
        object.addProperty("scheduleAnalysisExecuted", scheduleAnalysisExecuted);
        object.addProperty("deterministicCount", deterministicCount);
        object.addProperty("scheduleSensitiveCount", scheduleSensitiveCount);
        object.addProperty("unresolvedCount", unresolvedCount);
        JsonArray noteArray = new JsonArray();
        for (String note : notes) {
            noteArray.add(note);
        }
        object.add("notes", noteArray);
        return object;
    }

    /** 反序列化。 */
    public static WorkerStatsDto fromJson(JsonObject object) {
        List<String> notes = new ArrayList<>();
        for (JsonElement item : WorkerJson.optArray(object, "notes")) {
            notes.add(item.isJsonNull() ? "" : item.getAsString());
        }
        return new WorkerStatsDto(
                WorkerJson.reqInt(object, "protoChunks"),
                WorkerJson.reqLong(object, "stageExecutions"),
                WorkerJson.reqLong(object, "cacheHits"),
                WorkerJson.reqInt(object, "heldChunks"),
                WorkerJson.reqInt(object, "hostChunkSourceQueries"),
                WorkerJson.reqInt(object, "foreignWriterViewers"),
                WorkerJson.reqBool(object, "scheduleAnalysisExecuted"),
                WorkerJson.reqInt(object, "deterministicCount"),
                WorkerJson.reqInt(object, "scheduleSensitiveCount"),
                WorkerJson.reqInt(object, "unresolvedCount"),
                notes);
    }
}
