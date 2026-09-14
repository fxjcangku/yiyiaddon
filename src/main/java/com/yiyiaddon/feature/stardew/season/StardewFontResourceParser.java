package com.yiyiaddon.feature.stardew.season;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Minecraft 字体定义解析器：font JSON → (codepoint → 贴图 / 资源路径)。
 *
 * <p>只做结构解析，不判断季节语义：语义推导由 {@link StardewSeasonGlyphMap} 负责。
 * 与具体服务器、namespace、字体 id 无关，任何资源包都用同一套规则解析。</p>
 *
 * <p>支持当前资源包真实出现的 provider 类型：{@code bitmap}（含 {@code chars} 字符矩阵）、
 * {@code reference}（递归展开被引用字体，带去重防环）。{@code space / ttf / unihex /
 * legacy_unicode} 不含「codepoint → 贴图」证据，直接跳过；后续如需可在此扩展。</p>
 */
public final class StardewFontResourceParser {

    private StardewFontResourceParser() {
    }

    /**
     * 解析一个字体定义。
     *
     * @param fontId            字体 id（{@code namespace:name}），用于防环与定位
     * @param font              字体 JSON 根对象
     * @param referenceResolver {@code reference} provider 的 id → 被引用字体 JSON；可为 null
     * @return codepoint → 资源路径（例如 {@code gui:item/caidan/spring.png}）
     */
    public static Map<Integer, String> parse(String fontId, JsonObject font,
                                             Function<String, JsonObject> referenceResolver) {
        Map<Integer, String> result = new HashMap<>();
        collect(fontId, font, referenceResolver, result, new HashSet<>());
        return result;
    }

    private static void collect(String fontId, JsonObject font,
                                Function<String, JsonObject> referenceResolver,
                                Map<Integer, String> out, Set<String> visited) {
        if (font == null || fontId == null || !visited.add(fontId)) return;
        if (!font.has("providers") || !font.get("providers").isJsonArray()) return;

        for (JsonElement element : font.getAsJsonArray("providers")) {
            if (!element.isJsonObject()) continue;
            JsonObject provider = element.getAsJsonObject();
            String type = string(provider, "type");

            if ("reference".equals(type)) {
                String id = string(provider, "id");
                if (id != null && referenceResolver != null) {
                    collect(id, referenceResolver.apply(id), referenceResolver, out, visited);
                }
                continue;
            }

            // 只接收 bitmap：file + chars 是唯一可靠的「codepoint → 贴图」证据。
            // 旧版资源包可能省略 type，但同样带 file + chars，这里一并兼容。
            boolean bitmap = "bitmap".equals(type)
                || type == null && provider.has("file") && provider.has("chars");
            if (!bitmap) continue;

            String file = string(provider, "file");
            if (file == null || !provider.has("chars") || !provider.get("chars").isJsonArray()) continue;

            JsonArray chars = provider.getAsJsonArray("chars");
            for (JsonElement row : chars) {
                if (!row.isJsonPrimitive()) continue;
                // chars 是字符矩阵：每一项是一行，行内每个 codepoint 对应一列，全部指向同一个贴图。
                row.getAsString().codePoints().forEach(codepoint -> out.putIfAbsent(codepoint, file));
            }
        }
    }

    private static String string(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || !obj.get(key).isJsonPrimitive()) return null;
        try {
            return obj.get(key).getAsString();
        } catch (Exception ignored) {
            return null;
        }
    }
}
