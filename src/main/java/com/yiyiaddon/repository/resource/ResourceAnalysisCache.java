package com.yiyiaddon.repository.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.model.resource.ResourceAnalysisResult;
import com.yiyiaddon.repository.JsonFileStore;
import com.yiyiaddon.service.resourcepack.ResourcePackCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 资源分析结果缓存：以<b>内容指纹</b>为键保存一次完整分析的产出。
 *
 * <p><b>为什么以指纹为键而不是以服务器地址或 ZIP 文件名为键：</b>同一个服务器可以换资源包，
 * 不同服务器也可能用完全相同的资源包。按地址缓存会在资源包更新后返回过期结果，按文件名缓存
 * 更是把「文件名相同」当成「内容相同」。指纹由资源内容算出，内容一样才复用，内容变了必然失配。</p>
 *
 * <p>复用第二次分析：命中缓存时跳过「逐分类判定 + 逐文件读取」这一步，只从缓存取分类计数与
 * 命名空间归属；资源 id 仍由枚举现算，保证就绪判断始终基于当前真实加载的资源。</p>
 *
 * <p>存放位置：{@code <游戏目录>/yiyiaddon_resourcepacks/.analysis/<指纹>.json}，与资源包 ZIP
 * 缓存同目录下的独立子目录。ZIP 缓存扫描只接受文件且只认 {@code .zip} 后缀，因此两者不会相互干扰。</p>
 *
 * <p>写入走 {@link JsonFileStore} 原子写；读取遇到缺字段、类型不符、指纹不一致一律视为未命中，
 * 且不删除原文件（可能是并发写坏，删除会连带丢掉其它可用记录）。缓存数量上限 64，超出按写入
 * 时间淘汰最旧的记录，避免长期累积。</p>
 */
public final class ResourceAnalysisCache {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/resource-cache");

    private static final String DIR_NAME = ".analysis";
    private static final String SUFFIX = ".json";
    private static final int MAX_ENTRIES = 64;

    private static final String KEY_FINGERPRINT = "资源指纹";
    private static final String KEY_SOURCE = "资源来源";
    private static final String KEY_TOTAL = "资源数量";
    private static final String KEY_COUNTS = "资源分类";
    private static final String KEY_UNKNOWN = "未知资源";
    private static final String KEY_TARGETS = "目标命名空间";
    private static final String KEY_IGNORED = "忽略命名空间";
    private static final String KEY_STATE = "解析状态";
    private static final String KEY_CONTENT_FULL = "内容覆盖完整";
    private static final String KEY_SAVED_AT = "记录时间";

    private ResourceAnalysisCache() {
    }

    /**
     * 缓存记录。
     *
     * @param counts       分类中文名 → 数量
     * @param total        数量合计
     * @param unknown      未知资源数量
     * @param targets      目标命名空间
     * @param ignored      忽略命名空间
     * @param contentFull  写入时指纹是否覆盖了全部定义类内容
     * @param savedAt      写入时间
     */
    public record Cached(Map<String, Integer> counts, int total, int unknown, List<String> targets,
                         List<String> ignored, boolean contentFull, long savedAt) {
    }

    /** 缓存目录 */
    public static Path dir() {
        return ResourcePackCache.dir().toPath().resolve(DIR_NAME);
    }

    /** 按指纹读取缓存；未命中返回 {@code null} */
    public static Cached load(String fingerprint) {
        if (fingerprint == null || fingerprint.isBlank()) return null;
        Path file = dir().resolve(fingerprint + SUFFIX);
        if (!Files.isRegularFile(file)) return null;

        JsonObject root = JsonFileStore.readJson(file);
        if (root == null) return null;
        if (!fingerprint.equals(optionalString(root, KEY_FINGERPRINT))) return null;

        try {
            Map<String, Integer> counts = new LinkedHashMap<>();
            JsonElement countsElement = root.get(KEY_COUNTS);
            if (countsElement != null && countsElement.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : countsElement.getAsJsonObject().entrySet()) {
                    if (entry.getValue() != null && entry.getValue().isJsonPrimitive()) {
                        counts.put(entry.getKey(), entry.getValue().getAsInt());
                    }
                }
            }
            return new Cached(
                counts,
                optionalInt(root, KEY_TOTAL, 0),
                optionalInt(root, KEY_UNKNOWN, 0),
                stringList(root, KEY_TARGETS),
                stringList(root, KEY_IGNORED),
                root.has(KEY_CONTENT_FULL) && root.get(KEY_CONTENT_FULL).isJsonPrimitive()
                    && root.get(KEY_CONTENT_FULL).getAsBoolean(),
                optionalLong(root, KEY_SAVED_AT, 0L)
            );
        } catch (Exception error) {
            LOGGER.warn("资源分析缓存解析失败，按未命中处理：{}", file, error);
            return null;
        }
    }

    /** 写入一次分析结果；指纹缺失或写入失败返回 false */
    public static boolean save(ResourceAnalysisResult result) {
        if (result == null || result.fingerprint() == null || result.fingerprint().isBlank()) return false;

        JsonObject counts = new JsonObject();
        for (Map.Entry<String, Integer> entry : result.counts().entrySet()) {
            counts.addProperty(entry.getKey(), entry.getValue());
        }
        JsonArray targets = new JsonArray();
        for (String value : result.targetNamespaces()) targets.add(value);
        JsonArray ignored = new JsonArray();
        for (String value : result.ignoredNamespaces()) ignored.add(value);

        JsonObject root = new JsonObject();
        root.addProperty(KEY_FINGERPRINT, result.fingerprint());
        root.addProperty(KEY_SOURCE, result.source().label());
        root.addProperty(KEY_TOTAL, result.total());
        root.add(KEY_COUNTS, counts);
        root.addProperty(KEY_UNKNOWN, result.unknownCount());
        root.add(KEY_TARGETS, targets);
        root.add(KEY_IGNORED, ignored);
        root.addProperty(KEY_STATE, result.state().label());
        root.addProperty(KEY_CONTENT_FULL, result.contentFull());
        root.addProperty(KEY_SAVED_AT, result.parsedAtMillis());

        Path file = dir().resolve(result.fingerprint() + SUFFIX);
        if (!JsonFileStore.writeAtomic(file, root)) {
            LOGGER.warn("资源分析缓存写入失败：{}", file);
            return false;
        }
        prune();
        return true;
    }

    /** 缓存记录数 */
    public static int count() {
        try (Stream<Path> files = Files.list(dir())) {
            return (int) files.filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().endsWith(SUFFIX))
                .count();
        } catch (Exception e) {
            return 0;
        }
    }

    /** 按写入时间淘汰超限记录；返回删除数量 */
    public static int prune() {
        try (Stream<Path> files = Files.list(dir())) {
            List<Path> sorted = files.filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().endsWith(SUFFIX))
                .sorted(Comparator.comparingLong(ResourceAnalysisCache::modifiedAt).reversed())
                .toList();
            int removed = 0;
            for (int i = MAX_ENTRIES; i < sorted.size(); i++) {
                try {
                    Files.deleteIfExists(sorted.get(i));
                    removed++;
                } catch (Exception ignored) {
                    // 单条删除失败不影响其它记录
                }
            }
            return removed;
        } catch (Exception e) {
            return 0;
        }
    }

    /** 诊断文案 */
    public static String describe() {
        return "分析缓存 " + count() + " 项（上限 " + MAX_ENTRIES + "）";
    }

    // ── 读取工具 ──

    private static long modifiedAt(Path path) {
        try {
            return Files.getLastModifiedTime(path).toMillis();
        } catch (Exception e) {
            return 0L;
        }
    }

    private static String optionalString(JsonObject json, String key) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsString() : null;
    }

    private static int optionalInt(JsonObject json, String key, int fallback) {
        JsonElement element = json.get(key);
        try {
            return element != null && element.isJsonPrimitive() ? element.getAsInt() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    private static long optionalLong(JsonObject json, String key, long fallback) {
        JsonElement element = json.get(key);
        try {
            return element != null && element.isJsonPrimitive() ? element.getAsLong() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    private static List<String> stringList(JsonObject json, String key) {
        List<String> result = new ArrayList<>();
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonArray()) return result;
        for (JsonElement item : element.getAsJsonArray()) {
            if (item != null && item.isJsonPrimitive()) result.add(item.getAsString());
        }
        return result;
    }
}
