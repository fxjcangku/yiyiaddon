package com.yiyiaddon.feature.stardew.logistics;

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

/**
 * 星露谷「每种作物独立后勤参数」的持久化与内存缓存。
 *
 * <p><b>为什么必须按作物独立：</b>原先只有一组全局 {@code 2 / 8 / 8 / 0}，番茄与玉米共用同一
 * 阈值——番茄种子数量会被拿去满足玉米的补货缺口，卸货保留量也会互相牵连。现在的口径是
 * 每个 {@code cropKey} 各持一份阈值，互不影响。</p>
 *
 * <p><b>持久化键：</b>{@code ServerKey + resourceFingerprint + cropKey}。同一台服务器的资源包
 * 换了内容（指纹变化）即视为另一套资源，参数重新从默认值开始，避免把旧资源版本的阈值套到
 * 新资源上。同一服务器 + 同一指纹下取消选择再选回来，参数原样恢复。</p>
 *
 * <p><b>一服务器一文件：</b>{@code <游戏目录>/StardewFarm/logistics/<ServerKey>.json}，
 * 与 {@code selection} / {@code points} / {@code profiles} 同级，端口不同即不同文件
 * （{@code host_25565.json} / {@code host_25566.json}），绝不共用。</p>
 *
 * <p><b>内存缓存：</b>协调器每 tick 都会读阈值，因此按「ServerKey + 指纹」缓存整份映射，
 * 只有键变化时才重新读盘；写入走「先改缓存再原子落盘」。</p>
 */
public final class StardewLogisticsStore {

    private static final Path ROOT = Minecraft.getInstance().gameDirectory.toPath()
        .resolve("StardewFarm").resolve("logistics");

    /** 单种作物的一套后勤阈值 */
    public record CropLogistics(int restockTrigger, int restockTarget, int unloadTrigger, int unloadKeep) {

        /** 首次创建某作物参数时的默认值（仅作初始值，之后完全由该作物自己保存的值决定） */
        public static final CropLogistics DEFAULT = new CropLogistics(2, 8, 8, 0);

        public CropLogistics {
            restockTrigger = clamp(restockTrigger, 0, 64);
            restockTarget = clamp(restockTarget, 1, 64);
            unloadTrigger = clamp(unloadTrigger, 1, 64);
            unloadKeep = clamp(unloadKeep, 0, 64);
        }

        private static int clamp(int value, int min, int max) {
            return Math.max(min, Math.min(max, value));
        }
    }

    /** 触发重载的（ServerKey + 指纹）组合 */
    private static String loadedKey;
    private static long revision;
    /** 当前组合下的 {@code cropKey → 阈值} */
    private static final Map<String, CropLogistics> CACHE = new LinkedHashMap<>();

    private StardewLogisticsStore() {
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  读取
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 取某服务器某指纹下某种作物的后勤阈值。
     *
     * <p>没有记录时返回 {@link CropLogistics#DEFAULT}，<b>不会</b>把「没配过」当成「不允许后勤」。</p>
     */
    public static CropLogistics get(String serverKey, String fingerprint, String cropKey) {
        if (cropKey == null || cropKey.isBlank()) return CropLogistics.DEFAULT;
        ensureLoaded(serverKey, fingerprint);
        return CACHE.getOrDefault(cropKey, CropLogistics.DEFAULT);
    }

    /** 该组合下是否已有某种作物的记录（区分「玩家配过」与「默认值」） */
    public static boolean has(String serverKey, String fingerprint, String cropKey) {
        if (cropKey == null || cropKey.isBlank()) return false;
        ensureLoaded(serverKey, fingerprint);
        return CACHE.containsKey(cropKey);
    }

    /** 当前组合下已保存的全部作物参数（键为 cropKey） */
    public static Map<String, CropLogistics> all(String serverKey, String fingerprint) {
        ensureLoaded(serverKey, fingerprint);
        return new LinkedHashMap<>(CACHE);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  写入
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 保存某种作物的后勤阈值（先改内存缓存，再原子落盘） */
    public static boolean put(String serverKey, String fingerprint, String cropKey, CropLogistics value) {
        if (serverKey == null || serverKey.isBlank() || cropKey == null || cropKey.isBlank()) return false;
        ensureLoaded(serverKey, fingerprint);
        CACHE.put(cropKey, value == null ? CropLogistics.DEFAULT : value);
        boolean saved = persist(serverKey, fingerprint);
        if (saved) revision++;
        return saved;
    }

    /** 丢弃内存缓存（切服 / 断线时调用，磁盘文件保留） */
    public static void invalidate() {
        loadedKey = null;
        CACHE.clear();
        revision++;
    }

    public static long revision() {
        return revision;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  内部
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private static void ensureLoaded(String serverKey, String fingerprint) {
        String key = combination(serverKey, fingerprint);
        if (Objects.equals(key, loadedKey)) return;
        loadedKey = key;
        CACHE.clear();
        if (serverKey == null || serverKey.isBlank()) return;

        Path file = file(serverKey);
        if (!Files.isRegularFile(file)) return;
        try {
            JsonElement root = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8));
            if (!root.isJsonObject()) return;
            JsonElement block = root.getAsJsonObject().get("后勤参数");
            if (block == null || !block.isJsonObject()) return;
            for (Map.Entry<String, JsonElement> entry : block.getAsJsonObject().entrySet()) {
                if (!entry.getValue().isJsonObject()) continue;
                // 键格式：<指纹>@<cropKey>；只接受当前指纹的记录，换包后重新用默认值
                String entryKey = entry.getKey();
                int at = entryKey.lastIndexOf('@');
                if (at <= 0 || at == entryKey.length() - 1) continue;
                String entryFingerprint = entryKey.substring(0, at);
                if (!Objects.equals(entryFingerprint, safeFingerprint(fingerprint))) continue;
                JsonObject value = entry.getValue().getAsJsonObject();
                CACHE.put(entryKey.substring(at + 1), new CropLogistics(
                    readInt(value, "种子补货触发", CropLogistics.DEFAULT.restockTrigger()),
                    readInt(value, "种子补货目标", CropLogistics.DEFAULT.restockTarget()),
                    readInt(value, "成品卸货触发", CropLogistics.DEFAULT.unloadTrigger()),
                    readInt(value, "成品卸货保留", CropLogistics.DEFAULT.unloadKeep())));
            }
        } catch (Exception ignored) {
            // 损坏档案不覆盖：保留原始文件供玩家排查，本次按空表（默认值）运行
        }
    }

    private static boolean persist(String serverKey, String fingerprint) {
        JsonObject block = new JsonObject();
        for (Map.Entry<String, CropLogistics> entry : CACHE.entrySet()) {
            JsonObject value = new JsonObject();
            CropLogistics cfg = entry.getValue();
            value.addProperty("种子补货触发", cfg.restockTrigger());
            value.addProperty("种子补货目标", cfg.restockTarget());
            value.addProperty("成品卸货触发", cfg.unloadTrigger());
            value.addProperty("成品卸货保留", cfg.unloadKeep());
            block.add(safeFingerprint(fingerprint) + "@" + entry.getKey(), value);
        }
        JsonObject root = new JsonObject();
        root.addProperty("服务器", serverKey);
        root.addProperty("资源指纹", safeFingerprint(fingerprint));
        root.add("后勤参数", block);
        return JsonFileStore.writeAtomic(file(serverKey), root);
    }

    private static int readInt(JsonObject object, String name, int fallback) {
        try {
            JsonElement element = object.get(name);
            return element == null || !element.isJsonPrimitive() ? fallback : element.getAsInt();
        } catch (Exception ignored) {
            return fallback;
        }
    }

    /** 资源指纹缺失（资源未就绪）时用一个稳定占位，避免键里出现 null */
    private static String safeFingerprint(String fingerprint) {
        return fingerprint == null || fingerprint.isBlank() ? "unresolved" : fingerprint;
    }

    private static String combination(String serverKey, String fingerprint) {
        return (serverKey == null ? "" : serverKey) + "|" + safeFingerprint(fingerprint);
    }

    private static Path file(String serverKey) {
        return ROOT.resolve(serverKey.replaceAll("[\\\\/:*?\"<>|]", "_") + ".json");
    }
}
