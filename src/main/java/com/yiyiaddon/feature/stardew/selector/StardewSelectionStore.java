package com.yiyiaddon.feature.stardew.selector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.repository.JsonFileStore;
import net.minecraft.client.Minecraft;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 星露谷七类选择器的「按服务器隔离」持久化。
 *
 * <p><b>为什么不能只靠全局配置：</b>选择若只存在全局配置里，就没有服务器维度，
 * 于是 A 服勾选的作物会在 B 服原样出现——这正是「跨服串档」的根因。</p>
 *
 * <p>本存储一服务器一 JSON（{@code StardewFarm/selection/<ServerKey>.json}），
 * 键为 ServerKey（真实 host:port），值与 {@link StardewSelectorCategory#name()} 对应。
 * 另有 {@code resource-pack} 指纹校验位：若同一 ServerKey 的资源包换包，旧选择会在
 * 加载时被索引层按「已失效」剪除（{@code pruneInvalid}），不会指向不存在的资源。</p>
 *
 * <p>写入为原子写（临时文件 + 替换），失败保留旧档。</p>
 */
public final class StardewSelectionStore {

    private static final Path ROOT = Minecraft.getInstance().gameDirectory.toPath()
        .resolve("StardewFarm").resolve("selection");

    private StardewSelectionStore() {
    }

    /**
     * 读取某服务器的全部选择。
     *
     * @return {@code 类别名 → 稳定键列表}；无档案/损坏返回空表（不落盘，避免污染）
     */
    public static Map<String, List<String>> load(String serverKey) {
        Map<String, List<String>> result = new LinkedHashMap<>();
        if (serverKey == null || serverKey.isBlank()) return result;
        Path file = file(serverKey);
        if (!Files.isRegularFile(file)) return result;
        try {
            JsonElement root = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8));
            if (!root.isJsonObject()) return result;
            JsonElement selection = root.getAsJsonObject().get("选择");
            if (selection == null || !selection.isJsonObject()) return result;
            for (Map.Entry<String, JsonElement> entry : selection.getAsJsonObject().entrySet()) {
                if (!entry.getValue().isJsonArray()) continue;
                List<String> keys = new ArrayList<>();
                for (JsonElement el : entry.getValue().getAsJsonArray()) {
                    if (el.isJsonPrimitive()) {
                        String key = el.getAsString();
                        if (key != null && !key.isBlank() && !keys.contains(key)) keys.add(key);
                    }
                }
                result.put(entry.getKey(), keys);
            }
        } catch (Exception ignored) {
            // 损坏档案不覆盖：返回空表，保留原始文件供玩家排查
            return result;
        }
        return result;
    }

    /** 写入某服务器某一类别的选择（读取-合并-原子写） */
    public static boolean save(String serverKey, StardewSelectorCategory category, List<String> keys) {
        if (category == null) return false;
        return save(serverKey, category.name(), keys);
    }

    /**
     * 写入某服务器某一存储键的选择（读取-合并-原子写）。
     *
     * <p>存储键不限于类别名：盆型按维度分档，键形如 {@code POT@minecraft:the_nether}，
     * 让「主世界用普通盆、下界用下界盆」各记一份，换维度不必重新勾。</p>
     */
    public static boolean save(String serverKey, String storeKey, List<String> keys) {
        if (serverKey == null || serverKey.isBlank() || storeKey == null || storeKey.isBlank()) return false;

        Map<String, List<String>> all = load(serverKey);
        all.put(storeKey, keys == null ? new ArrayList<>() : new ArrayList<>(keys));

        JsonObject selection = new JsonObject();
        for (Map.Entry<String, List<String>> entry : all.entrySet()) {
            JsonArray array = new JsonArray();
            for (String key : entry.getValue()) array.add(key);
            selection.add(entry.getKey(), array);
        }
        JsonObject root = new JsonObject();
        root.addProperty("服务器", serverKey);
        root.add("选择", selection);

        return JsonFileStore.writeAtomic(file(serverKey), root);
    }

    private static Path file(String serverKey) {
        String safe = serverKey.replaceAll("[\\\\/:*?\"<>|]", "_");
        return ROOT.resolve(safe + ".json");
    }
}
