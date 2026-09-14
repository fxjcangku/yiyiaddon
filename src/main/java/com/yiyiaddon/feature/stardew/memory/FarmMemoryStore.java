package com.yiyiaddon.feature.stardew.memory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.repository.JsonFileStore;
import net.minecraft.client.Minecraft;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 农田长期记忆持久化（按服务器 + 维度隔离）。
 *
 * <p>重启后只恢复「种植意图」，必须重新观察世界，不重放旧收割 / 种植 / 浇水包。
 * 文件位置 {@code StardewFarm/memory/{serverKey}_{维度}.json}，原子写入 + 失败保护。</p>
 */
public final class FarmMemoryStore {

    private final Path root;

    public FarmMemoryStore() {
        this.root = Minecraft.getInstance().gameDirectory.toPath().resolve("StardewFarm").resolve("memory");
    }

    private Path file(String serverKey, String dimension) {
        String safe = (serverKey + "_" + dimension).replaceAll("[\\\\/:*?\"<>|]", "_");
        return root.resolve(safe + ".json");
    }

    /** 读取全部记忆（按坐标键去重）；无文件返回空映射 */
    public Map<String, FarmCellMemory> load(String serverKey, String dimension) {
        Map<String, FarmCellMemory> result = new LinkedHashMap<>();
        Path file = file(serverKey, dimension);
        if (!Files.isRegularFile(file)) return result;
        try {
            JsonObject obj = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
            if (obj.has("农田记忆") && obj.get("农田记忆").isJsonArray()) {
                for (var el : obj.getAsJsonArray("农田记忆")) {
                    if (!el.isJsonObject()) continue;
                    JsonObject cell = el.getAsJsonObject();
                    int x = cell.get("坐标X").getAsInt();
                    int y = cell.get("坐标Y").getAsInt();
                    int z = cell.get("坐标Z").getAsInt();
                    String cropKey = cell.has("目标作物") ? cell.get("目标作物").getAsString() : "";
                    boolean enabled = !cell.has("启用") || cell.get("启用").getAsBoolean();
                    FarmCellMemory memory = new FarmCellMemory(x, y, z, cropKey, enabled);
                    result.put(key(memory.pos()), memory);
                }
            }
        } catch (Exception ignored) {
            // 损坏文件不覆盖，返回空
        }
        return result;
    }

    /** 保存全部记忆（原子写入，失败保留旧档） */
    public boolean save(String serverKey, String dimension, Map<String, FarmCellMemory> memories) {
        Path file = file(serverKey, dimension);
        JsonObject obj = new JsonObject();
        obj.addProperty("服务器", serverKey);
        obj.addProperty("维度", dimension);
        JsonArray arr = new JsonArray();
        for (FarmCellMemory memory : memories.values()) {
            JsonObject cell = new JsonObject();
            cell.addProperty("坐标X", memory.x());
            cell.addProperty("坐标Y", memory.y());
            cell.addProperty("坐标Z", memory.z());
            cell.addProperty("目标作物", memory.cropKey());
            cell.addProperty("启用", memory.enabled());
            arr.add(cell);
        }
        obj.add("农田记忆", arr);
        return JsonFileStore.writeAtomic(file, obj);
    }

    /** 坐标 → 记忆键 */
    public static String key(net.minecraft.core.BlockPos pos) {
        return pos.getX() + "," + pos.getY() + "," + pos.getZ();
    }

    /** 读取某格记忆；不存在返回空记忆 */
    public FarmCellMemory get(String serverKey, String dimension, net.minecraft.core.BlockPos pos) {
        Map<String, FarmCellMemory> all = load(serverKey, dimension);
        return all.getOrDefault(key(pos), FarmCellMemory.empty(pos.getX(), pos.getY(), pos.getZ()));
    }

    /** 覆盖式写回全部记忆的辅助：读 → 改一格 → 存 */
    public boolean upsert(String serverKey, String dimension, FarmCellMemory memory) {
        Map<String, FarmCellMemory> all = load(serverKey, dimension);
        all.put(key(memory.pos()), memory);
        return save(serverKey, dimension, all);
    }
}
