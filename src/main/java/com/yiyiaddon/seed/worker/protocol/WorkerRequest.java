package com.yiyiaddon.seed.worker.protocol;

import com.google.gson.JsonObject;

/**
 * 种子挖矿 · Worker IPC · <b>请求</b>（一行 JSON）。
 *
 * <p>刻意做成「一个扁平结构 + 操作码」而不是多态子类：IPC 两端是两个独立进程、两个独立 JVM，
 * 扁平的字段集合最容易一眼核对，也不需要任何多态反序列化机制（阶段 232 口径第二十节要求
 * 明确 DTO、禁用 Java 序列化）。</p>
 *
 * @param id              请求代号（端到端原样回传；客户端据此丢弃过期响应）
 * @param op              操作码（{@code SeedWorkerProtocol} 的 {@code OP_*} 常量）
 * @param token           启动时客户端生成的高熵令牌（每一次请求都要带）
 * @param protocolVersion 客户端协议版本
 * @param minecraftVersion 客户端 Minecraft 版本（HELLO 时必填）
 * @param seed            种子（OPEN_SESSION / PREDICT）
 * @param dimension       维度标识（OPEN_SESSION / PREDICT）
 * @param chunkX          目标区块 X（PREDICT）
 * @param chunkZ          目标区块 Z（PREDICT）
 * @param oreType         矿物种类枚举名（PREDICT）
 */
public record WorkerRequest(long id, String op, String token, int protocolVersion, String minecraftVersion,
                            long seed, String dimension, int chunkX, int chunkZ, String oreType) {

    /** 握手。 */
    public static WorkerRequest hello(long id, String token, int protocolVersion, String minecraftVersion) {
        return new WorkerRequest(id, SeedWorkerProtocol.OP_HELLO, token, protocolVersion, minecraftVersion,
                0L, "", 0, 0, null);
    }

    /** 心跳。 */
    public static WorkerRequest ping(long id, String token) {
        return new WorkerRequest(id, SeedWorkerProtocol.OP_PING, token, SeedWorkerProtocol.VERSION, "", 0L, "",
                0, 0, null);
    }

    /** 打开会话。 */
    public static WorkerRequest openSession(long id, String token, long seed, String dimension) {
        return new WorkerRequest(id, SeedWorkerProtocol.OP_OPEN_SESSION, token, SeedWorkerProtocol.VERSION, "",
                seed, dimension, 0, 0, null);
    }

    /** 预测一个目标区块里的指定矿物。 */
    public static WorkerRequest predict(long id, String token, long seed, String dimension,
                                        int chunkX, int chunkZ, String oreType) {
        return new WorkerRequest(id, SeedWorkerProtocol.OP_PREDICT, token, SeedWorkerProtocol.VERSION, "",
                seed, dimension, chunkX, chunkZ, oreType);
    }

    /** 关闭会话。 */
    public static WorkerRequest closeSession(long id, String token) {
        return new WorkerRequest(id, SeedWorkerProtocol.OP_CLOSE_SESSION, token, SeedWorkerProtocol.VERSION, "",
                0L, "", 0, 0, null);
    }

    /** 优雅停机。 */
    public static WorkerRequest shutdown(long id, String token) {
        return new WorkerRequest(id, SeedWorkerProtocol.OP_SHUTDOWN, token, SeedWorkerProtocol.VERSION, "", 0L, "",
                0, 0, null);
    }

    /** 序列化。 */
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("id", id);
        object.addProperty("op", op);
        object.addProperty("token", token);
        object.addProperty("protocolVersion", protocolVersion);
        WorkerJson.putNullable(object, "minecraftVersion", minecraftVersion);
        object.addProperty("seed", seed);
        object.addProperty("dimension", dimension);
        object.addProperty("chunkX", chunkX);
        object.addProperty("chunkZ", chunkZ);
        WorkerJson.putNullable(object, "oreType", oreType);
        return object;
    }

    /** 反序列化。 */
    public static WorkerRequest fromJson(JsonObject object) {
        return new WorkerRequest(
                WorkerJson.reqLong(object, "id"),
                WorkerJson.reqString(object, "op"),
                WorkerJson.reqString(object, "token"),
                WorkerJson.reqInt(object, "protocolVersion"),
                WorkerJson.optString(object, "minecraftVersion", ""),
                WorkerJson.optLong(object, "seed", 0L),
                WorkerJson.optString(object, "dimension", ""),
                WorkerJson.optInt(object, "chunkX", 0),
                WorkerJson.optInt(object, "chunkZ", 0),
                WorkerJson.optString(object, "oreType"));
    }
}
