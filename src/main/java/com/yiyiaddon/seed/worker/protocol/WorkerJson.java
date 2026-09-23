package com.yiyiaddon.seed.worker.protocol;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;

/**
 * 种子挖矿 · Worker IPC 的 JSON 读写工具（唯一入口）。
 *
 * <p><b>为什么要集中一层</b>：协议两端必须对「缺字段 / 类型不对 / 枚举不认识」有一致且<b>显式</b>的处理，
 * 否则最容易出现的 bug 是「解析失败 → 得到空列表 → 被当成这个区块没有矿」
 * （阶段 232 口径第六十六节明令禁止）。因此这里所有取值方法要么给默认值，
 * 要么抛 {@link WorkerProtocolException}，绝不静默返回看似合法的空值。</p>
 */
public final class WorkerJson {

    private WorkerJson() {
    }

    /** 解析一行 JSON 对象；不是对象 / 不是 JSON 一律抛协议异常。 */
    public static JsonObject parseObject(String line, String errorCode) {
        if (line == null) {
            throw new WorkerProtocolException(errorCode, "空行");
        }
        JsonElement element;
        try {
            element = JsonParser.parseString(line);
        } catch (RuntimeException error) {
            throw new WorkerProtocolException(errorCode, "不是合法 JSON：" + brief(line));
        }
        if (!element.isJsonObject()) {
            throw new WorkerProtocolException(errorCode, "不是 JSON 对象：" + brief(line));
        }
        return element.getAsJsonObject();
    }

    /** 编码为一行紧凑 JSON。 */
    public static String encode(JsonObject object) {
        return object.toString();
    }

    /** 必填字符串字段。 */
    public static String reqString(JsonObject object, String key) {
        JsonElement element = object.get(key);
        if (element == null || !element.isJsonPrimitive()) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_REQUEST,
                    "字段 " + key + " 缺失或不是字符串");
        }
        return element.getAsString();
    }

    /** 可空字符串字段（缺失或 JSON null 都返回 null）。 */
    public static String optString(JsonObject object, String key) {
        JsonElement element = object.get(key);
        if (element == null || element.isJsonNull() || !element.isJsonPrimitive()) {
            return null;
        }
        return element.getAsString();
    }

    /** 字符串字段，缺失时取默认值。 */
    public static String optString(JsonObject object, String key, String fallback) {
        String value = optString(object, key);
        return value == null ? fallback : value;
    }

    /** 必填整数字段。 */
    public static int reqInt(JsonObject object, String key) {
        return (int) reqLong(object, key);
    }

    /** 必填长整数字段。 */
    public static long reqLong(JsonObject object, String key) {
        JsonElement element = object.get(key);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_REQUEST,
                    "字段 " + key + " 缺失或不是数字");
        }
        try {
            return element.getAsLong();
        } catch (RuntimeException error) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_REQUEST,
                    "字段 " + key + " 不是整数");
        }
    }

    /** 整数字段，缺失时取默认值。 */
    public static int optInt(JsonObject object, String key, int fallback) {
        JsonElement element = object.get(key);
        if (element == null || element.isJsonNull()) {
            return fallback;
        }
        return reqInt(object, key);
    }

    /** 长整数字段，缺失时取默认值。 */
    public static long optLong(JsonObject object, String key, long fallback) {
        JsonElement element = object.get(key);
        if (element == null || element.isJsonNull()) {
            return fallback;
        }
        return reqLong(object, key);
    }

    /** 必填布尔字段。 */
    public static boolean reqBool(JsonObject object, String key) {
        JsonElement element = object.get(key);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isBoolean()) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_REQUEST,
                    "字段 " + key + " 缺失或不是布尔");
        }
        return element.getAsBoolean();
    }

    /** 对象字段（缺失返回 null）。 */
    public static JsonObject optObject(JsonObject object, String key) {
        JsonElement element = object.get(key);
        if (element == null || !element.isJsonObject()) {
            return null;
        }
        return element.getAsJsonObject();
    }

    /** 必填对象字段。 */
    public static JsonObject reqObject(JsonObject object, String key) {
        JsonObject child = optObject(object, key);
        if (child == null) {
            throw new WorkerProtocolException(SeedWorkerProtocol.ERROR_MALFORMED_REQUEST,
                    "字段 " + key + " 缺失或不是对象");
        }
        return child;
    }

    /** 数组字段（缺失返回空表，元素类型由调用方逐个校验）。 */
    public static List<JsonElement> optArray(JsonObject object, String key) {
        JsonElement element = object.get(key);
        if (element == null || !element.isJsonArray()) {
            return List.of();
        }
        JsonArray array = element.getAsJsonArray();
        List<JsonElement> result = new ArrayList<>(array.size());
        for (JsonElement item : array) {
            result.add(item);
        }
        return result;
    }

    /** 写入可空字符串（null 写成 JSON null，保证键一定存在）。 */
    public static void putNullable(JsonObject object, String key, String value) {
        object.add(key, value == null ? null : new JsonPrimitive(value));
    }

    /** 日志用的短摘要（截断，避免把整行 JSON 刷进日志）。 */
    private static String brief(String line) {
        return line.length() > 120 ? line.substring(0, 120) + "..." : line;
    }
}
