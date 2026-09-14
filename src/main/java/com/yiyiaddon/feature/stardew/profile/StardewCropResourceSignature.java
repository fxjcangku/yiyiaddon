package com.yiyiaddon.feature.stardew.profile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 逐作物资源签名计算器。
 *
 * <p>签名只吸收某个 {@code cropKey} 自己的种子/产物 items 定义、关联 item model、
 * {@code models/block/crop/<cropKey>/} 下全部阶段模型，以及语言文件中命中这些资源身份的条目。
 * 其它作物与纯贴图变化不会污染该作物签名，因此新服务器可以逐作物安全复用已知规则，
 * 而不是用整个 ZIP 相同与否做粗粒度判断。</p>
 *
 * <p>资源覆盖顺序与客户端一致：高优先级资源包先占位，同一资源键只读取实际生效的一份。
 * 任一相关 JSON 内容、路径、阶段集合或身份条目变化都会改变签名。</p>
 */
public final class StardewCropResourceSignature {

    /** 持久化与内置规则库共同校验的签名算法版本。 */
    public static final String ALGORITHM = "crop-resource-v1";
    private static final Minecraft mc = Minecraft.getInstance();
    private static final int MAX_ENTRIES = 20000;
    private static final int BUFFER_SIZE = 8192;

    /** 单作物签名结果；阶段数为零时调用方不得拿它匹配参考成熟规则。 */
    public record Signature(String value, int resourceCount, int stageCount) {

        /** 是否包含足以描述生长阶段的完整最低证据。 */
        public boolean usable() {
            return value != null && !value.isBlank() && resourceCount > 0 && stageCount > 0;
        }
    }

    private StardewCropResourceSignature() {
        // 工具类，禁止实例化
    }

    /**
     * 一次遍历当前 ResourceManager，为资源索引中的所有作物建立独立签名。
     *
     * @return cropKey → 签名；没有阶段模型或读取失败的作物不会出现在结果中
     */
    public static Map<String, Signature> compute(Collection<CropDefinition> crops) {
        Map<String, CropDefinition> definitions = new LinkedHashMap<>();
        if (crops != null) {
            for (CropDefinition crop : crops) {
                if (crop != null && crop.cropKey() != null && !crop.cropKey().isBlank()) {
                    definitions.put(crop.cropKey(), crop);
                }
            }
        }
        if (definitions.isEmpty()) return Map.of();

        ResourceManager manager = mc.getResourceManager();
        if (manager == null) return Map.of();
        List<PackResources> packs;
        try {
            packs = manager.listPacks().toList();
        } catch (Exception ignored) {
            return Map.of();
        }

        Map<String, Map<String, String>> entriesByCrop = new LinkedHashMap<>();
        Map<String, Integer> stagesByCrop = new LinkedHashMap<>();
        for (String cropKey : definitions.keySet()) {
            entriesByCrop.put(cropKey, new LinkedHashMap<>());
            stagesByCrop.put(cropKey, 0);
        }

        int[] visited = {0};
        for (int i = packs.size() - 1; i >= 0 && visited[0] < MAX_ENTRIES; i--) {
            PackResources pack = packs.get(i);
            try {
                if (!pack.getNamespaces(PackType.CLIENT_RESOURCES).contains(StardewResourceScanner.STARDEW_NAMESPACE)) {
                    continue;
                }
                collectJson(pack, "items", definitions, entriesByCrop, stagesByCrop, visited);
                collectJson(pack, "models/item", definitions, entriesByCrop, stagesByCrop, visited);
                collectJson(pack, "models/block/crop", definitions, entriesByCrop, stagesByCrop, visited);
                collectLanguages(pack, definitions, entriesByCrop, visited);
            } catch (Exception ignored) {
                // 单个资源包异常不能让其它有效资源失去签名。
            }
        }

        Map<String, Signature> result = new LinkedHashMap<>();
        for (String cropKey : definitions.keySet()) {
            Map<String, String> entries = entriesByCrop.get(cropKey);
            int stages = stagesByCrop.getOrDefault(cropKey, 0);
            String value = digest(entries);
            Signature signature = new Signature(value, entries.size(), stages);
            if (signature.usable()) result.put(cropKey, signature);
        }
        return result;
    }

    /** 枚举普通 JSON 资源并按精确作物归属写入签名输入。 */
    private static void collectJson(PackResources pack, String directory,
                                    Map<String, CropDefinition> definitions,
                                    Map<String, Map<String, String>> entriesByCrop,
                                    Map<String, Integer> stagesByCrop, int[] visited) {
        if (visited[0] >= MAX_ENTRIES) return;
        try {
            pack.listResources(PackType.CLIENT_RESOURCES, StardewResourceScanner.STARDEW_NAMESPACE,
                directory, (id, supplier) -> {
                    if (visited[0]++ >= MAX_ENTRIES) return;
                    String path = id.getPath();
                    if (!path.endsWith(".json")) return;
                    for (Map.Entry<String, CropDefinition> cropEntry : definitions.entrySet()) {
                        String cropKey = cropEntry.getKey();
                        if (!belongsToCrop(path, directory, cropKey)) continue;
                        Map<String, String> entries = entriesByCrop.get(cropKey);
                        String resourceKey = id.toString();
                        if (entries.containsKey(resourceKey)) continue;
                        String hash = hash(supplier);
                        if (hash == null) continue;
                        entries.put(resourceKey, hash);
                        if (isStageModel(path, cropKey)) {
                            stagesByCrop.put(cropKey, stagesByCrop.getOrDefault(cropKey, 0) + 1);
                        }
                    }
                });
        } catch (Exception ignored) {
            // 单目录读取失败只让对应作物无法匹配参考，不允许伪造签名。
        }
    }

    /** 语言文件只吸收与该作物资源身份精确相关的键值，避免其它翻译改动使所有作物失配。 */
    private static void collectLanguages(PackResources pack, Map<String, CropDefinition> definitions,
                                         Map<String, Map<String, String>> entriesByCrop, int[] visited) {
        if (visited[0] >= MAX_ENTRIES) return;
        try {
            pack.listResources(PackType.CLIENT_RESOURCES, StardewResourceScanner.STARDEW_NAMESPACE,
                "lang", (id, supplier) -> {
                    if (visited[0]++ >= MAX_ENTRIES || !id.getPath().endsWith(".json")) return;
                    byte[] bytes = read(supplier);
                    if (bytes == null) return;
                    JsonObject language;
                    try {
                        language = JsonParser.parseString(new String(bytes, StandardCharsets.UTF_8)).getAsJsonObject();
                    } catch (Exception ignored) {
                        return;
                    }
                    for (Map.Entry<String, CropDefinition> cropEntry : definitions.entrySet()) {
                        Set<String> identities = relatedNames(cropEntry.getKey());
                        for (var translation : language.entrySet()) {
                            if (!containsIdentity(translation.getKey(), identities)) continue;
                            String key = id + "#" + translation.getKey();
                            entriesByCrop.get(cropEntry.getKey()).putIfAbsent(key,
                                digestText(translation.getValue().toString()));
                        }
                    }
                });
        } catch (Exception ignored) {
            // 没有语言文件是合法情况；损坏语言资源只会让签名安全失配。
        }
    }

    /** 路径必须属于精确作物家族，避免 tomato 意外命中其它相似键。 */
    private static boolean belongsToCrop(String path, String directory, String cropKey) {
        String lower = path.toLowerCase(Locale.ROOT);
        if ("models/block/crop".equals(directory)) {
            return lower.startsWith("models/block/crop/" + cropKey.toLowerCase(Locale.ROOT) + "/");
        }
        String base = baseName(lower);
        return relatedNames(cropKey).contains(base);
    }

    /** 数值阶段与特殊阶段都属于阶段映射证据。 */
    private static boolean isStageModel(String path, String cropKey) {
        String prefix = "models/block/crop/" + cropKey.toLowerCase(Locale.ROOT) + "/stage_";
        return path.toLowerCase(Locale.ROOT).startsWith(prefix);
    }

    /** 作物种子、普通/品质产物与已知变种的稳定资源身份集合。 */
    private static Set<String> relatedNames(String cropKey) {
        String key = cropKey.toLowerCase(Locale.ROOT);
        Set<String> names = new LinkedHashSet<>();
        names.add(key);
        names.add(key + "_seeds");
        names.add(key + "_silver_star");
        names.add(key + "_golden_star");
        names.add("golden_" + key);
        names.add("giant_" + key);
        names.add("gigantic_" + key);
        names.add(key + "_variation");
        return names;
    }

    /** 语言键按点号/斜杠分段后匹配完整身份，禁止子串串作物。 */
    private static boolean containsIdentity(String translationKey, Set<String> identities) {
        if (translationKey == null) return false;
        String[] parts = translationKey.toLowerCase(Locale.ROOT).split("[./]");
        for (String part : parts) {
            if (identities.contains(part)) return true;
        }
        return false;
    }

    /** 去目录与扩展名后的资源末段。 */
    private static String baseName(String path) {
        int slash = path.lastIndexOf('/');
        String base = slash >= 0 ? path.substring(slash + 1) : path;
        return base.endsWith(".json") ? base.substring(0, base.length() - 5) : base;
    }

    /** 读取资源并返回 SHA-256 前 8 字节；读取失败返回 null。 */
    private static String hash(IoSupplier<InputStream> supplier) {
        byte[] bytes = read(supplier);
        return bytes == null ? null : digestBytes(bytes, 8);
    }

    /** 单次安全读取资源字节，防止重复打开同一供应器。 */
    private static byte[] read(IoSupplier<InputStream> supplier) {
        try (InputStream in = supplier.get()) {
            if (in == null) return null;
            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = in.read(buffer)) != -1) out.write(buffer, 0, read);
            return out.toByteArray();
        } catch (Exception ignored) {
            return null;
        }
    }

    /** 对稳定排序后的资源键与内容摘要做最终签名。 */
    private static String digest(Map<String, String> entries) {
        if (entries == null || entries.isEmpty()) return null;
        List<String> keys = new ArrayList<>(entries.keySet());
        Collections.sort(keys);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (String key : keys) {
                digest.update(key.getBytes(StandardCharsets.UTF_8));
                digest.update((byte) 0);
                digest.update(entries.get(key).getBytes(StandardCharsets.UTF_8));
                digest.update((byte) '\n');
            }
            return HexFormat.of().formatHex(digest.digest(), 0, 6);
        } catch (Exception ignored) {
            return null;
        }
    }

    /** 合成语言条目的内容摘要，保持与普通资源相同的摘要宽度。 */
    private static String digestText(String value) {
        return digestBytes(value.getBytes(StandardCharsets.UTF_8), 8);
    }

    /** SHA-256 截断只用于签名分层，最终仍会再做一次完整 SHA-256 聚合。 */
    private static String digestBytes(byte[] bytes, int outputBytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(bytes), 0, outputBytes);
        } catch (Exception ignored) {
            return "unreadable";
        }
    }
}
