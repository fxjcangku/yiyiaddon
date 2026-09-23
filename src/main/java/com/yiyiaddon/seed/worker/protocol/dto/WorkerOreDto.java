package com.yiyiaddon.seed.worker.protocol.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.seed.worker.protocol.WorkerJson;
import java.util.ArrayList;
import java.util.List;

/**
 * 种子挖矿 · Worker IPC · <b>一块被预测出来的矿</b>（传输层）。
 *
 * <p><b>为什么不能只回 {@code Set<BlockPos>}</b>（阶段 232 口径第十九节）：调用方必须能看到
 * 「确定性分类 / 来源分类 / 是哪一次 FEATURES 写进来的 / 有哪些 viewer 在抢这一格」。
 * 只回坐标等于把正式 `PredictedOre` 的语义砍掉一半，回到 228 之前的状态。</p>
 *
 * @param x                 方块 X
 * @param y                 方块 Y
 * @param z                 方块 Z
 * @param oreType           矿物种类（正式层枚举名，客户端 fail-closed 还原）
 * @param certainty         确定性分类（正式层枚举名）
 * @param source            来源分类（正式层枚举名）
 * @param originViewer      把它写进目标区块的那一次 FEATURES 所在区块；未知为 null
 * @param conflictingWriters 抢过这一格的 viewer 列表（按发生先后；只在调度敏感时非空）
 */
public record WorkerOreDto(int x, int y, int z, String oreType, String certainty, String source,
                           WorkerChunkRef originViewer, List<WorkerChunkRef> conflictingWriters) {

    public WorkerOreDto {
        conflictingWriters = List.copyOf(conflictingWriters);
    }

    /** 序列化。 */
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("x", x);
        object.addProperty("y", y);
        object.addProperty("z", z);
        object.addProperty("oreType", oreType);
        object.addProperty("certainty", certainty);
        object.addProperty("source", source);
        object.add("originViewer", originViewer == null ? null : originViewer.toJson());
        JsonArray writers = new JsonArray();
        for (WorkerChunkRef ref : conflictingWriters) {
            writers.add(ref.toJson());
        }
        object.add("conflictingWriters", writers);
        return object;
    }

    /** 反序列化。 */
    public static WorkerOreDto fromJson(JsonObject object) {
        JsonObject origin = WorkerJson.optObject(object, "originViewer");
        List<WorkerChunkRef> writers = new ArrayList<>();
        for (JsonElement item : WorkerJson.optArray(object, "conflictingWriters")) {
            if (!item.isJsonObject()) {
                continue;
            }
            writers.add(WorkerChunkRef.fromJson(item.getAsJsonObject()));
        }
        return new WorkerOreDto(
                WorkerJson.reqInt(object, "x"),
                WorkerJson.reqInt(object, "y"),
                WorkerJson.reqInt(object, "z"),
                WorkerJson.reqString(object, "oreType"),
                WorkerJson.reqString(object, "certainty"),
                WorkerJson.reqString(object, "source"),
                origin == null ? null : WorkerChunkRef.fromJson(origin),
                writers);
    }
}
