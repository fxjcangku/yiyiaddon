package com.yiyiaddon.feature.stardew.season;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.repository.JsonFileStore;
import net.minecraft.client.Minecraft;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 服务器专属季节人工绑定（数据层）。
 *
 * <p>用于资源包完全无法自动解释季节的服务器：把 {@code (fontId, codepoint) → 季节} 绑定为
 * <b>仅当前 ServerKey + fingerprint 有效</b>的规则。它不会成为全局规则，也不会覆盖自动资源
 * 证据：{@link StardewSeasonGlyphMap} 只在自动证据缺失时采用本表。</p>
 *
 * <p>fingerprint 变化（资源包升级）后旧绑定不再匹配，等同于失效、需重新确认，因此不会把旧
 * 服务器的语义套到新资源上。本轮只提供数据层接口，不新增 UI；后续命令入口可直接调用
 * {@link #bind} / {@link #clear}。</p>
 */
public final class StardewSeasonManualBinding {

    private static final String ROOT_DIR = "StardewFarm";
    private static final String FILE_NAME = "season-bindings.json";

    private StardewSeasonManualBinding() {
    }

    /**
     * 写入 / 覆盖一条绑定。
     *
     * @return 是否成功落盘
     */
    public static synchronized boolean bind(String serverKey, String fingerprint, String fontId,
                                            int codepoint, StardewSeasonService.SeasonSemantic semantic) {
        if (serverKey == null || fingerprint == null || fontId == null
            || semantic == null || semantic == StardewSeasonService.SeasonSemantic.UNKNOWN) {
            return false;
        }
        JsonObject root = readRoot();
        JsonArray bindings = arrayOf(root);
        JsonObject target = find(bindings, serverKey, fingerprint, fontId, codepoint);
        if (target == null) {
            target = new JsonObject();
            bindings.add(target);
        }
        target.addProperty("服务器", serverKey);
        target.addProperty("指纹", fingerprint);
        target.addProperty("字体", fontId);
        target.addProperty("码位", hex(codepoint));
        target.addProperty("季节", semantic.name());
        return writeRoot(root);
    }

    /** 删除某服务器的全部人工绑定；@return 是否成功落盘。 */
    public static synchronized boolean clear(String serverKey) {
        if (serverKey == null) return false;
        JsonObject root = readRoot();
        JsonArray bindings = arrayOf(root);
        JsonArray kept = new JsonArray();
        for (var element : bindings) {
            if (!element.isJsonObject()) continue;
            JsonObject obj = element.getAsJsonObject();
            if (serverKey.equals(string(obj, "服务器"))) continue;
            kept.add(obj);
        }
        root.add("绑定", kept);
        return writeRoot(root);
    }

    /**
     * 读取当前 ServerKey + fingerprint 的绑定。
     *
     * @return fontId → codepoint → 语义；无绑定或文件损坏返回空表
     */
    public static synchronized Map<String, Map<Integer, StardewSeasonService.SeasonSemantic>> load(
        String serverKey, String fingerprint) {
        Map<String, Map<Integer, StardewSeasonService.SeasonSemantic>> result = new HashMap<>();
        if (serverKey == null || fingerprint == null) return result;
        JsonArray bindings = arrayOf(readRoot());
        for (var element : bindings) {
            if (!element.isJsonObject()) continue;
            JsonObject obj = element.getAsJsonObject();
            if (!serverKey.equals(string(obj, "服务器")) || !fingerprint.equals(string(obj, "指纹"))) continue;
            String fontId = string(obj, "字体");
            String codepoint = string(obj, "码位");
            if (fontId == null || codepoint == null) continue;
            StardewSeasonService.SeasonSemantic semantic = semanticOf(string(obj, "季节"));
            if (semantic == StardewSeasonService.SeasonSemantic.UNKNOWN) continue;
            int value;
            try {
                value = Integer.parseInt(codepoint, 16);
            } catch (NumberFormatException ignored) {
                continue;
            }
            result.computeIfAbsent(fontId, ignored -> new LinkedHashMap<>()).put(value, semantic);
        }
        return result;
    }

    // ── 内部 ────────────────────────────────────────────────────────────

    private static Path file() {
        return Minecraft.getInstance().gameDirectory.toPath().resolve(ROOT_DIR).resolve(FILE_NAME);
    }

    private static JsonObject readRoot() {
        Path file = file();
        if (!Files.isRegularFile(file)) return new JsonObject();
        try {
            JsonObject root = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
            return root == null ? new JsonObject() : root;
        } catch (Exception ignored) {
            // 损坏文件不覆盖：返回空表，磁盘内容保留供玩家排查
            return new JsonObject();
        }
    }

    private static boolean writeRoot(JsonObject root) {
        return JsonFileStore.writeAtomic(file(), root);
    }

    private static JsonArray arrayOf(JsonObject root) {
        if (root.has("绑定") && root.get("绑定").isJsonArray()) return root.getAsJsonArray("绑定");
        JsonArray array = new JsonArray();
        root.add("绑定", array);
        return array;
    }

    private static JsonObject find(JsonArray bindings, String serverKey, String fingerprint,
                                   String fontId, int codepoint) {
        String hex = hex(codepoint);
        for (var element : bindings) {
            if (!element.isJsonObject()) continue;
            JsonObject obj = element.getAsJsonObject();
            if (serverKey.equals(string(obj, "服务器"))
                && fingerprint.equals(string(obj, "指纹"))
                && fontId.equals(string(obj, "字体"))
                && hex.equalsIgnoreCase(String.valueOf(string(obj, "码位")))) {
                return obj;
            }
        }
        return null;
    }

    private static String string(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || !obj.get(key).isJsonPrimitive()) return null;
        try {
            return obj.get(key).getAsString();
        } catch (Exception ignored) {
            return null;
        }
    }

    private static String hex(int codepoint) {
        return Integer.toHexString(codepoint).toUpperCase(Locale.ROOT);
    }

    private static StardewSeasonService.SeasonSemantic semanticOf(String name) {
        if (name == null) return StardewSeasonService.SeasonSemantic.UNKNOWN;
        try {
            return StardewSeasonService.SeasonSemantic.valueOf(name.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ignored) {
            return StardewSeasonService.SeasonSemantic.UNKNOWN;
        }
    }
}
