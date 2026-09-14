package com.yiyiaddon.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * JSON 读写助手。
 *
 * <p>后端返回的字段类型在不同接口间并不统一（布尔可能以 0/1 数字下发，时间戳可能是数字或数字字符串），
 * 因此这里统一提供「类型不符即回退」的取值方法，任何异常都不会向外抛出。</p>
 */
public final class Json {

    private Json() {
    }

    /** 新建空对象，用于拼装请求体。 */
    public static JsonObject obj() {
        return new JsonObject();
    }

    /** 解析为对象；非法内容返回 {@code null}。 */
    public static JsonObject parse(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try {
            JsonElement root = JsonParser.parseString(raw);
            return root != null && root.isJsonObject() ? root.getAsJsonObject() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String string(JsonObject json, String key, String fallback) {
        JsonElement element = json == null ? null : json.get(key);
        if (element == null || element.isJsonNull()) return fallback;
        try {
            return element.isJsonPrimitive() ? element.getAsString() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static int integer(JsonObject json, String key, int fallback) {
        JsonElement element = json == null ? null : json.get(key);
        if (element == null || element.isJsonNull()) return fallback;
        try {
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) return element.getAsInt();
            return Integer.parseInt(element.getAsString().trim());
        } catch (Exception e) {
            return fallback;
        }
    }

    public static long decimal(JsonObject json, String key, long fallback) {
        JsonElement element = json == null ? null : json.get(key);
        if (element == null || element.isJsonNull()) return fallback;
        try {
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) return element.getAsLong();
            return Long.parseLong(element.getAsString().trim());
        } catch (Exception e) {
            return fallback;
        }
    }

    /** 兼容布尔、数字（非 0 为真）与字符串 "true" / "1"。 */
    public static boolean bool(JsonObject json, String key, boolean fallback) {
        JsonElement element = json == null ? null : json.get(key);
        if (element == null || element.isJsonNull()) return fallback;
        try {
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) return element.getAsBoolean();
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) return element.getAsInt() != 0;
            String text = element.getAsString().trim();
            if (text.equalsIgnoreCase("true") || text.equals("1")) return true;
            if (text.equalsIgnoreCase("false") || text.equals("0")) return false;
            return fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /** 取数组；缺失或类型不符返回空数组，调用方无需判空。 */
    public static JsonArray array(JsonObject json, String key) {
        JsonElement element = json == null ? null : json.get(key);
        return element != null && element.isJsonArray() ? element.getAsJsonArray() : new JsonArray();
    }
}
