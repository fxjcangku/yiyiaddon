package com.yiyiaddon.seed.worker.protocol;

import com.google.gson.JsonObject;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerHelloDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerPredictionDto;
import com.yiyiaddon.seed.worker.protocol.dto.WorkerSessionDto;

/**
 * 种子挖矿 · Worker IPC · <b>应答</b>（一行 JSON）。
 *
 * <p>一个应答最多带一种载荷（HELLO / 会话 / 预测）；失败时 {@code ok=false} 且必须给错误码与中文原因，
 * <b>绝不允许</b>用「成功的空预测」表达失败（阶段 232 口径第六十六、六十七节）。</p>
 *
 * @param id          请求代号（原样回传）
 * @param ok          本次请求是否成功
 * @param errorCode   错误码（成功时为 null）
 * @param errorMessage 中文错误原因（成功时为 null）
 * @param hello       握手载荷
 * @param session     会话载荷
 * @param prediction  预测载荷
 */
public record WorkerResponse(long id, boolean ok, String errorCode, String errorMessage, WorkerHelloDto hello,
                             WorkerSessionDto session, WorkerPredictionDto prediction) {

    /** 成功应答（空载荷）。 */
    public static WorkerResponse success(long id) {
        return new WorkerResponse(id, true, null, null, null, null, null);
    }

    /** 成功应答（带 HELLO）。 */
    public static WorkerResponse hello(long id, WorkerHelloDto hello) {
        return new WorkerResponse(id, true, null, null, hello, null, null);
    }

    /** 成功应答（带会话）。 */
    public static WorkerResponse session(long id, WorkerSessionDto session) {
        return new WorkerResponse(id, true, null, null, null, session, null);
    }

    /** 成功应答（带预测结果）。 */
    public static WorkerResponse prediction(long id, WorkerPredictionDto prediction) {
        return new WorkerResponse(id, true, null, null, null, null, prediction);
    }

    /** 失败应答。 */
    public static WorkerResponse error(long id, String errorCode, String errorMessage) {
        return new WorkerResponse(id, false, errorCode, errorMessage, null, null, null);
    }

    /** 序列化。 */
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("id", id);
        object.addProperty("ok", ok);
        WorkerJson.putNullable(object, "errorCode", errorCode);
        WorkerJson.putNullable(object, "errorMessage", errorMessage);
        object.add("hello", hello == null ? null : hello.toJson());
        object.add("session", session == null ? null : session.toJson());
        object.add("prediction", prediction == null ? null : prediction.toJson());
        return object;
    }

    /** 反序列化。 */
    public static WorkerResponse fromJson(JsonObject object) {
        JsonObject hello = WorkerJson.optObject(object, "hello");
        JsonObject session = WorkerJson.optObject(object, "session");
        JsonObject prediction = WorkerJson.optObject(object, "prediction");
        return new WorkerResponse(
                WorkerJson.reqLong(object, "id"),
                WorkerJson.reqBool(object, "ok"),
                WorkerJson.optString(object, "errorCode"),
                WorkerJson.optString(object, "errorMessage"),
                hello == null ? null : WorkerHelloDto.fromJson(hello),
                session == null ? null : WorkerSessionDto.fromJson(session),
                prediction == null ? null : WorkerPredictionDto.fromJson(prediction));
    }
}
