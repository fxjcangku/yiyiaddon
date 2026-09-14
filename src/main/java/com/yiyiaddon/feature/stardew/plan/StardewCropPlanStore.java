package com.yiyiaddon.feature.stardew.plan;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.repository.JsonFileStore;
import net.minecraft.client.Minecraft;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** 按 ServerKey + 资源指纹 + cropKey 保存每作物数量模式与输入值。 */
public final class StardewCropPlanStore {

    private static final int GROUP_SIZE = 64;
    private static final Path ROOT = Minecraft.getInstance().gameDirectory.toPath()
        .resolve("StardewFarm").resolve("crop-plans");

    public record CropPlan(StardewAmountMode amountMode, int amountValue) {
        public static final CropPlan DEFAULT = new CropPlan(StardewAmountMode.GROUPS, 1);

        public CropPlan {
            amountMode = amountMode == null ? StardewAmountMode.GROUPS : amountMode;
            amountValue = Math.max(1, Math.min(9999, amountValue));
        }

        /** 状态机唯一消费值；“组数”和“个数”不会形成两套业务真值。 */
        public int actualAmount() {
            long actual = amountMode == StardewAmountMode.GROUPS
                ? (long) amountValue * GROUP_SIZE : amountValue;
            return (int) Math.min(Integer.MAX_VALUE, actual);
        }
    }

    private static String loadedKey;
    private static long revision;
    private static final Map<String, CropPlan> CACHE = new LinkedHashMap<>();

    private StardewCropPlanStore() {
    }

    public static CropPlan get(String serverKey, String fingerprint, String cropKey) {
        if (cropKey == null || cropKey.isBlank()) return CropPlan.DEFAULT;
        ensureLoaded(serverKey, fingerprint);
        return CACHE.getOrDefault(cropKey, CropPlan.DEFAULT);
    }

    public static boolean put(String serverKey, String fingerprint, String cropKey, CropPlan plan) {
        if (serverKey == null || serverKey.isBlank() || cropKey == null || cropKey.isBlank()) return false;
        ensureLoaded(serverKey, fingerprint);
        CACHE.put(cropKey, plan == null ? CropPlan.DEFAULT : plan);
        boolean saved = persist(serverKey, fingerprint);
        if (saved) revision++;
        return saved;
    }

    public static void invalidate() {
        loadedKey = null;
        CACHE.clear();
        revision++;
    }

    public static long revision() {
        return revision;
    }

    private static void ensureLoaded(String serverKey, String fingerprint) {
        String key = String.valueOf(serverKey) + '|' + safeFingerprint(fingerprint);
        if (Objects.equals(key, loadedKey)) return;
        loadedKey = key;
        CACHE.clear();
        if (serverKey == null || serverKey.isBlank()) return;
        Path file = file(serverKey);
        if (!Files.isRegularFile(file)) return;
        try {
            JsonObject root = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
            JsonElement block = root.get("作物数量");
            if (block == null || !block.isJsonObject()) return;
            for (Map.Entry<String, JsonElement> entry : block.getAsJsonObject().entrySet()) {
                int at = entry.getKey().lastIndexOf('@');
                if (at <= 0 || !safeFingerprint(fingerprint).equals(entry.getKey().substring(0, at))) continue;
                JsonObject value = entry.getValue().getAsJsonObject();
                StardewAmountMode mode;
                try { mode = StardewAmountMode.valueOf(value.get("数量模式").getAsString()); }
                catch (Exception ignored) { mode = StardewAmountMode.GROUPS; }
                CACHE.put(entry.getKey().substring(at + 1), new CropPlan(mode, value.get("目标数量").getAsInt()));
            }
        } catch (Exception ignored) {
            // 损坏文件保留给玩家排查，本次使用默认值。
        }
    }

    private static boolean persist(String serverKey, String fingerprint) {
        JsonObject block = new JsonObject();
        for (Map.Entry<String, CropPlan> entry : CACHE.entrySet()) {
            JsonObject value = new JsonObject();
            value.addProperty("数量模式", entry.getValue().amountMode().name());
            value.addProperty("目标数量", entry.getValue().amountValue());
            block.add(safeFingerprint(fingerprint) + '@' + entry.getKey(), value);
        }
        JsonObject root = new JsonObject();
        root.addProperty("服务器", serverKey);
        root.addProperty("资源指纹", safeFingerprint(fingerprint));
        root.add("作物数量", block);
        return JsonFileStore.writeAtomic(file(serverKey), root);
    }

    private static String safeFingerprint(String fingerprint) {
        return fingerprint == null || fingerprint.isBlank() ? "unresolved" : fingerprint;
    }

    private static Path file(String serverKey) {
        return ROOT.resolve(serverKey.replaceAll("[\\\\/:*?\"<>|]", "_") + ".json");
    }
}
