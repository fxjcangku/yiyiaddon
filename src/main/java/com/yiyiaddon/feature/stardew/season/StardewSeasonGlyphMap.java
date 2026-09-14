package com.yiyiaddon.feature.stardew.season;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.service.resourcepack.ResourcePackCache;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 服务器季节语义表：把「字体 + codepoint」解析为四季语义。
 *
 * <p>不针对任何服务器写死：不假设 namespace、字体 id、glyph、资源路径。构建时扫描当前
 * ServerKey + fingerprint 下<b>真正生效</b>的资源包字体定义，按
 * {@code (fontId, codepoint) → texture path → 季节词} 推导语义。因此换一个服务器、换一套
 * glyph，都会自动建立该服务器自己的映射。</p>
 *
 * <p>数据来源优先级：</p>
 * <ol>
 *   <li>{@link ResourceManager}：当前客户端真实生效的资源栈，天然尊重多资源包覆盖优先级；</li>
 *   <li>缓存 ZIP（{@link ResourcePackCache#cachedZip(String)}）：仅当上面拿不到任何字体
 *       证据时作为 fallback，例如资源仍在加载 / 已卸载。</li>
 * </ol>
 *
 * <p>生命周期：构建只在客户端线程、每 ServerKey + fingerprint 一次；结果发布为只读快照，
 * 网络线程与状态机只做 O(1) 查询，任何时刻都不会在包处理里扫描 ZIP 或解析字体 JSON。</p>
 *
 * <p>证据不足（例如 {@code icon_1.png}）绝不猜季节：保持 UNKNOWN，避免误判 DISALLOWED。
 * 同一 glyph 被两个可靠证据映射成不同季节时标记冲突，同样按 UNKNOWN 处理。</p>
 */
public final class StardewSeasonGlyphMap {

    /** 单条 glyph 证据的来源等级；UNKNOWN/CONFLICT 用 {@link #CONFLICT} 表达冲突。 */
    public enum Evidence {
        /** 资源路径/文件名里存在明确季节词（最高可信）。 */
        RESOURCE_EXPLICIT,
        /** 玩家针对当前 ServerKey + fingerprint 的人工绑定。 */
        MANUAL_VERIFIED,
        /** 同一 glyph 出现互相矛盾的可靠证据：按 UNKNOWN 处理，禁止 DISALLOWED。 */
        CONFLICT
    }

    /** 单个 (fontId, codepoint) 的语义证据。 */
    public record Glyph(StardewSeasonService.SeasonSemantic semantic, Evidence evidence) {
    }

    private static final int MAX_FONT_FILES = 2048;
    private static final int MAX_ZIP_ENTRIES = 200_000;

    private static final StardewSeasonGlyphMap EMPTY =
        new StardewSeasonGlyphMap(Map.of(), Map.of(), null, null, "无");

    private static volatile StardewSeasonGlyphMap cached = EMPTY;
    private static volatile String cachedKey;

    /** 字体 id → codepoint → 证据（构建完成后不再修改）。 */
    private final Map<String, Map<Integer, Glyph>> glyphs;
    /**
     * codepoint → 语义的跨字体反查表。
     *
     * <p>服务器组件声明的 {@code Style.font} 未必与字体定义里的 id 完全同形（可能是 atlas sprite
     * 等其它 FontDescription 子类），此时精确 (fontId, codepoint) 查不到。只要该 codepoint 在
     * <b>全部字体下语义唯一一致</b>，即可安全复用；出现矛盾则整条不收录，避免误判。</p>
     */
    private final Map<Integer, StardewSeasonService.SeasonSemantic> byCodepoint;
    private final String serverKey;
    private final String fingerprint;
    private final String source;

    private StardewSeasonGlyphMap(Map<String, Map<Integer, Glyph>> glyphs,
                                  Map<Integer, StardewSeasonService.SeasonSemantic> byCodepoint,
                                  String serverKey, String fingerprint, String source) {
        this.glyphs = glyphs;
        this.byCodepoint = byCodepoint;
        this.serverKey = serverKey;
        this.fingerprint = fingerprint;
        this.source = source;
    }

    /** 空白表：任何查询都返回 UNKNOWN。 */
    public static StardewSeasonGlyphMap empty() {
        return EMPTY;
    }

    /** 丢弃当前缓存，下次 {@link #current()} 重新构建（人工绑定后立即生效）。 */
    public static void invalidate() {
        cachedKey = null;
    }

    /**
     * 当前 ServerKey + fingerprint 对应的语义表；两者任一变化都会重新构建。
     *
     * <p>只允许在客户端线程调用（会读取 ResourceManager / ZIP）。网络线程请读取
     * {@link StardewSeasonService} 持有的 volatile 快照。</p>
     */
    public static StardewSeasonGlyphMap current() {
        String serverKey = StardewContext.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        String key = String.valueOf(serverKey) + '\u0000' + String.valueOf(fingerprint);
        StardewSeasonGlyphMap local = cached;
        if (Objects.equals(cachedKey, key)) return local;
        StardewSeasonGlyphMap built = build(serverKey, fingerprint);
        cached = built;
        cachedKey = key;
        return built;
    }

    /**
     * 查询某个字体下的 codepoint 是否对应明确季节。
     *
     * <p>先精确匹配 (fontId, codepoint)；未命中时用「同 codepoint 在所有字体下语义唯一」的
     * 反查表兜底。仍然无证据或冲突都返回 UNKNOWN。</p>
     */
    public StardewSeasonService.SeasonSemantic semanticOf(String fontKey, int codepoint) {
        Map<Integer, Glyph> font = glyphs.get(fontKey);
        if (font != null) {
            Glyph glyph = font.get(codepoint);
            if (glyph != null) return glyph.semantic();
        }
        StardewSeasonService.SeasonSemantic shared = byCodepoint.get(codepoint);
        return shared == null ? StardewSeasonService.SeasonSemantic.UNKNOWN : shared;
    }

    /** 查询证据来源；无证据返回 null。用于调试与后续人工校准入口。 */
    public Evidence evidenceOf(String fontKey, int codepoint) {
        Map<Integer, Glyph> font = glyphs.get(fontKey);
        if (font != null) {
            Glyph glyph = font.get(codepoint);
            if (glyph != null) return glyph.evidence();
        }
        return byCodepoint.containsKey(codepoint) ? Evidence.RESOURCE_EXPLICIT : null;
    }

    /** 该表绑定的服务器键；空表为 null。 */
    public String serverKey() {
        return serverKey;
    }

    /** 该表绑定的资源指纹；空表为 null。 */
    public String fingerprint() {
        return fingerprint;
    }

    /** 证据来源描述，仅用于报告 / 调试。 */
    public String source() {
        return source;
    }

    /** 已建立多少条 glyph 季节证据。 */
    public int size() {
        int count = 0;
        for (Map<Integer, Glyph> font : glyphs.values()) count += font.size();
        return count;
    }

    // ── 构建 ─────────────────────────────────────────────────────────────

    private static StardewSeasonGlyphMap build(String serverKey, String fingerprint) {
        Map<String, JsonObject> fonts = new LinkedHashMap<>();
        boolean fromManager = collectFontsFromResourceManager(fonts);

        // 缓存 ZIP 作为补充：服务器资源包未必进入 ResourceManager（例如资源由外部解析、
        // 或尚未接入资源栈），这时只有 ZIP 里有它的字体定义。只补 ResourceManager 缺失的
        // fontId，绝不覆盖当前真正生效的定义。
        Map<String, JsonObject> zipFonts = new LinkedHashMap<>();
        boolean fromZip = collectFontsFromZip(serverKey, zipFonts);
        if (fromZip) {
            for (Map.Entry<String, JsonObject> entry : zipFonts.entrySet()) {
                fonts.putIfAbsent(entry.getKey(), entry.getValue());
            }
        }

        Map<String, Map<Integer, Glyph>> glyphs = new HashMap<>();
        parseAll(fonts, glyphs);
        String source = fromManager && fromZip ? "当前生效资源 + 缓存 ZIP"
            : fromManager ? "当前生效资源"
            : fromZip ? "缓存 ZIP" : "无";

        mergeManualBindings(serverKey, fingerprint, glyphs);
        return new StardewSeasonGlyphMap(glyphs, sharedSemantics(glyphs), serverKey, fingerprint, source);
    }

    /** 汇总「同 codepoint 语义唯一」的跨字体反查表；出现矛盾语义的 codepoint 一律不收录。 */
    private static Map<Integer, StardewSeasonService.SeasonSemantic> sharedSemantics(
        Map<String, Map<Integer, Glyph>> glyphs) {
        Map<Integer, Set<StardewSeasonService.SeasonSemantic>> collected = new HashMap<>();
        for (Map<Integer, Glyph> font : glyphs.values()) {
            for (Map.Entry<Integer, Glyph> entry : font.entrySet()) {
                StardewSeasonService.SeasonSemantic semantic = entry.getValue().semantic();
                if (semantic == StardewSeasonService.SeasonSemantic.UNKNOWN) continue;
                collected.computeIfAbsent(entry.getKey(), ignored -> new LinkedHashSet<>()).add(semantic);
            }
        }
        Map<Integer, StardewSeasonService.SeasonSemantic> result = new HashMap<>();
        collected.forEach((codepoint, semantics) -> {
            if (semantics.size() == 1) result.put(codepoint, semantics.iterator().next());
        });
        return result;
    }

    /** 解析全部字体定义，把有季节证据的 glyph 写入结果表。 */
    private static void parseAll(Map<String, JsonObject> fonts, Map<String, Map<Integer, Glyph>> out) {
        if (fonts.isEmpty()) return;
        Function<String, JsonObject> resolver = fonts::get;
        for (Map.Entry<String, JsonObject> entry : fonts.entrySet()) {
            Map<Integer, String> parsed =
                StardewFontResourceParser.parse(entry.getKey(), entry.getValue(), resolver);
            if (parsed.isEmpty()) continue;
            Map<Integer, Glyph> target = out.computeIfAbsent(entry.getKey(), ignored -> new HashMap<>());
            for (Map.Entry<Integer, String> glyph : parsed.entrySet()) {
                putEvidence(target, glyph.getKey(), glyph.getValue());
            }
        }
    }

    /** 单条证据写入：同语义保留，不同语义标记冲突，冲突后不再被覆盖。 */
    private static void putEvidence(Map<Integer, Glyph> target, int codepoint, String resourcePath) {
        StardewSeasonService.SeasonSemantic semantic = semanticOfResourcePath(resourcePath);
        if (semantic == null) return;
        Glyph existing = target.get(codepoint);
        if (existing == null) {
            target.put(codepoint, new Glyph(semantic, Evidence.RESOURCE_EXPLICIT));
            return;
        }
        if (existing.evidence() == Evidence.CONFLICT || existing.semantic() == semantic) return;
        target.put(codepoint, new Glyph(StardewSeasonService.SeasonSemantic.UNKNOWN, Evidence.CONFLICT));
    }

    /** 自动资源证据优先；只有自动没有结论时才采用当前 ServerKey + fingerprint 的人工绑定。 */
    private static void mergeManualBindings(String serverKey, String fingerprint,
                                            Map<String, Map<Integer, Glyph>> out) {
        if (serverKey == null || fingerprint == null) return;
        Map<String, Map<Integer, StardewSeasonService.SeasonSemantic>> manual =
            StardewSeasonManualBinding.load(serverKey, fingerprint);
        if (manual.isEmpty()) return;
        for (Map.Entry<String, Map<Integer, StardewSeasonService.SeasonSemantic>> font : manual.entrySet()) {
            Map<Integer, Glyph> target = out.computeIfAbsent(font.getKey(), ignored -> new HashMap<>());
            for (Map.Entry<Integer, StardewSeasonService.SeasonSemantic> binding : font.getValue().entrySet()) {
                if (binding.getValue() == StardewSeasonService.SeasonSemantic.UNKNOWN) continue;
                target.putIfAbsent(binding.getKey(), new Glyph(binding.getValue(), Evidence.MANUAL_VERIFIED));
            }
        }
    }

    // ── 数据来源 1：当前生效 ResourceManager ──────────────────────────────

    /** @return 是否真的读到字体定义 */
    private static boolean collectFontsFromResourceManager(Map<String, JsonObject> fonts) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        if (manager == null) return false;
        List<PackResources> packs;
        try {
            packs = manager.listPacks().toList();
        } catch (Exception ignored) {
            return false;
        }

        int[] files = {0};
        // 高优先级资源包在列表尾部：先遍历高优先级并 putIfAbsent，复刻客户端真实覆盖顺序。
        for (int i = packs.size() - 1; i >= 0 && files[0] < MAX_FONT_FILES; i--) {
            PackResources pack = packs.get(i);
            Set<String> namespaces;
            try {
                namespaces = pack.getNamespaces(PackType.CLIENT_RESOURCES);
            } catch (Exception ignored) {
                continue;
            }
            for (String namespace : namespaces) {
                if (files[0] >= MAX_FONT_FILES) break;
                try {
                    pack.listResources(PackType.CLIENT_RESOURCES, namespace, "font", (id, supplier) -> {
                        if (files[0] >= MAX_FONT_FILES) return;
                        String fontId = fontIdOf(namespace, id.getPath());
                        if (fontId == null) return;
                        files[0]++;
                        if (fonts.containsKey(fontId)) return;
                        JsonObject json = readJson(supplier);
                        if (json != null) fonts.put(fontId, json);
                    });
                } catch (Exception ignored) {
                    // 单个资源包字体目录异常不影响其它资源包与其它 namespace。
                }
            }
        }
        return !fonts.isEmpty();
    }

    /** {@code assets/<ns>/font/<name>.json} 的资源路径 → {@code <ns>:<name>}；非直系 font JSON 返回 null。 */
    private static String fontIdOf(String namespace, String path) {
        if (path == null || !path.endsWith(".json")) return null;
        String rest = path.startsWith("font/") ? path.substring("font/".length()) : path;
        if (rest.isEmpty() || rest.indexOf('/') >= 0) return null;
        return namespace + ':' + rest.substring(0, rest.length() - ".json".length());
    }

    // ── 数据来源 2：缓存 ZIP fallback ─────────────────────────────────────

    /** 按 entry 读取 ZIP 中的字体定义，不整体解压；@return 是否读到任何字体定义。 */
    private static boolean collectFontsFromZip(String serverKey, Map<String, JsonObject> fonts) {
        if (serverKey == null) return false;
        File zip = ResourcePackCache.cachedZip(serverKey);
        if (zip == null) return false;
        int[] scanned = {0};
        try (ZipFile zipFile = new ZipFile(zip)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements() && scanned[0] < MAX_ZIP_ENTRIES) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) continue;
                scanned[0]++;
                String fontId = zipFontId(entry.getName());
                if (fontId == null || fonts.containsKey(fontId)) continue;
                try (InputStream in = zipFile.getInputStream(entry)) {
                    JsonObject json = readJson(in);
                    if (json != null) fonts.put(fontId, json);
                }
            }
        } catch (Exception ignored) {
            return !fonts.isEmpty();
        }
        return !fonts.isEmpty();
    }

    /** {@code assets/<ns>/font/<name>.json} → {@code <ns>:<name>}；其它路径返回 null。 */
    private static String zipFontId(String name) {
        if (name == null || !name.startsWith("assets/") || !name.endsWith(".json")) return null;
        String[] parts = name.split("/");
        if (parts.length != 4 || !"font".equals(parts[2])) return null;
        String base = parts[3];
        return parts[1] + ':' + base.substring(0, base.length() - ".json".length());
    }

    // ── 资源语义推导（通用，不绑定任何服务器） ────────────────────────────

    /**
     * 资源路径 → 季节语义。
     *
     * <p>按路径分段（{@code [^a-z0-9\u4e00-\u9fff]}）逐段精确匹配季节词，因此
     * {@code gui:item/caidan/spring.png} 判 SPRING，而 {@code springboard.png} 不会误判。
     * 支持中英文：spring/春/春季、summer/夏/夏季、autumn·fall/秋/秋季、winter/冬/冬季。</p>
     */
    static StardewSeasonService.SeasonSemantic semanticOfResourcePath(String path) {
        if (path == null || path.isBlank()) return null;
        String lower = path.toLowerCase(Locale.ROOT);
        for (String segment : lower.split("[^a-z0-9\u4e00-\u9fff]+")) {
            if (segment.isEmpty()) continue;
            StardewSeasonService.SeasonSemantic semantic = semanticOfToken(segment);
            if (semantic != null) return semantic;
        }
        return null;
    }

    /**
     * 单个 segment 精确匹配季节词；不是完整季节词一律返回 null（禁止 substring 猜测）。
     *
     * <p>覆盖四类常见命名：英文（spring/summer/autumn·fall/winter）、中文（春/春季/春天）、
     * 拼音（chun/xia/qiu/dong 及全拼）、日语罗马字与常见三字母缩写。实测服务器的季节图标
     * 常用拼音命名贴图（{@code .../font/images/chun.png}），只认英文会漏。</p>
     */
    static StardewSeasonService.SeasonSemantic semanticOfToken(String token) {
        if (token == null || token.isEmpty()) return null;
        return switch (token) {
            case "spring", "spr", "chun", "chuntian", "haru", "春", "春季", "春天" ->
                StardewSeasonService.SeasonSemantic.SPRING;
            case "summer", "sum", "xia", "xiadian", "natsu", "夏", "夏季", "夏天" ->
                StardewSeasonService.SeasonSemantic.SUMMER;
            case "autumn", "fall", "aut", "fal", "qiu", "qiutian", "aki", "秋", "秋季", "秋天" ->
                StardewSeasonService.SeasonSemantic.AUTUMN;
            case "winter", "win", "dong", "dongtian", "fuyu", "冬", "冬季", "冬天" ->
                StardewSeasonService.SeasonSemantic.WINTER;
            default -> null;
        };
    }

    // ── JSON 读取 ───────────────────────────────────────────────────────

    private static JsonObject readJson(IoSupplier<InputStream> supplier) {
        try (InputStream in = supplier.get()) {
            return readJson(in);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static JsonObject readJson(InputStream in) {
        if (in == null) return null;
        try {
            byte[] bytes = in.readAllBytes();
            JsonElement parsed = JsonParser.parseString(new String(bytes, StandardCharsets.UTF_8));
            return parsed != null && parsed.isJsonObject() ? parsed.getAsJsonObject() : null;
        } catch (Exception ignored) {
            return null;
        }
    }

    /** 便于外部（命令 / 调试）列出当前表内全部 font id。 */
    public Set<String> fontIds() {
        return new LinkedHashSet<>(glyphs.keySet());
    }
}
