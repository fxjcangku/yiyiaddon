package com.yiyiaddon.feature.stardew.service;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.season.StardewFontResourceParser;
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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 水量条字形表：从资源包 font 定义里挑出「水位条格子」字形，用它在 Tooltip / LORE 里的个数
 * 还原水壶容量。
 *
 * <p><b>证据链：</b>服务端插件（CustomCrops）的 {@code dynamic-lore} 用 {@code {water_bar}} 画水位条，
 * 条上每个格子就是一个字形；字形由资源包 {@code assets/<ns>/font/*.json} 定义，指向
 * {@code textures/font/bars/*}。实测「晴海小镇9.7」包的映射是：</p>
 * <pre>
 * 뀂 font/bars/left.png        左端帽（不计入格子）
 * 뀃 font/bars/full_default.png 满格
 * 뀄 font/bars/full_nether.png  满格（下界配色）
 * 뀅 font/bars/full_end.png     满格（末格）
 * 뀆 font/bars/empty.png        空格
 * 뀇 font/bars/right.png       右端帽（不计入格子）
 * </pre>
 *
 * <p><b>为什么不按文件名区分满格 / 空格：</b>哪个贴图当「空格」由服务端 yml 的
 * {@code water-bar.empty} 决定（官方示例把 {@code full_nether} 当空格用），按文件名猜会算错。
 * 这里只用「左端帽 / 右端帽各出现一次，其余每个字形恰好一格」这一条结构事实：
 * {@code 容量 = 去掉左右端帽后的字形总数}，与配色命名无关。</p>
 *
 * <p>即便某个服务器把条做成固定长度（缩放条），最坏结果也只是容量估小 → 多补一轮，
 * 绝不多发无意义的水或改变补水语义；真正的发包次数仍由 {@code verifyRefill} 每轮验证兜底。</p>
 *
 * <p><b>生命周期：</b>只在客户端线程构建，按 ServerKey + fingerprint 缓存为只读快照；
 * 状态机与网络线程只做 O(1) 查询，绝不在包处理里扫描资源。</p>
 */
public final class StardewWaterBarGlyphMap {

    /** 单次构建最多读取的 font 文件数：资源包异常时不至于拖死客户端线程。 */
    private static final int MAX_FONT_FILES = 512;
    private static final int MAX_ZIP_ENTRIES = 200_000;
    /** 端帽名：left / right，出现次数恒为 1，不计入格子数。 */
    private static final Set<String> CAP_TOKENS = Set.of("left", "right");

    private static final StardewWaterBarGlyphMap EMPTY =
        new StardewWaterBarGlyphMap(Set.of(), Set.of(), Set.of());

    private static volatile StardewWaterBarGlyphMap cached = EMPTY;
    private static volatile String cachedKey;

    /** 计入容量统计的字形（bars 目录下、非左右端帽）。 */
    private final Set<Integer> barGlyphs;
    /** 端帽字形，只用于描述与排查。 */
    private final Set<Integer> capGlyphs;
    /** 文件名带 full 的字形，仅用于排查（不代表「满格」语义）。 */
    private final Set<Integer> fullNamedGlyphs;

    private StardewWaterBarGlyphMap(Set<Integer> barGlyphs, Set<Integer> capGlyphs,
                                    Set<Integer> fullNamedGlyphs) {
        this.barGlyphs = barGlyphs;
        this.capGlyphs = capGlyphs;
        this.fullNamedGlyphs = fullNamedGlyphs;
    }

    /** 当前 ServerKey + fingerprint 对应字形表；资源包没有水位条字形时返回空表。 */
    public static StardewWaterBarGlyphMap current() {
        String key = String.valueOf(StardewContext.serverKey())
            + '\u0000' + String.valueOf(ResourceExtractionService.fingerprint());
        if (Objects.equals(cachedKey, key)) return cached;
        StardewWaterBarGlyphMap built = build();
        cached = built;
        cachedKey = key;
        return built;
    }

    /** 丢弃缓存，下次 {@link #current()} 重新扫描。 */
    public static void invalidate() {
        cachedKey = null;
    }

    /** 是否真的在资源包里找到了水位条格子字形。 */
    public boolean configured() {
        return !barGlyphs.isEmpty();
    }

    /** 数一段文本里的水位条格子数；端帽不计入。 */
    public int countBarGlyphs(String text) {
        return countLine(text).cells();
    }

    /** 一行文本的字形统计。 */
    public record LineCount(int cells, int caps) {
        /** 是否像一条完整水位条：至少两格，且带端帽（端帽是水位条独有的强证据）。 */
        public boolean looksLikeBar() {
            return cells >= 2 && caps > 0;
        }
    }

    /** 分别数出一行里的格子字形与端帽字形。 */
    public LineCount countLine(String text) {
        if (text == null || text.isEmpty() || (barGlyphs.isEmpty() && capGlyphs.isEmpty())) {
            return new LineCount(0, 0);
        }
        int cells = 0;
        int caps = 0;
        int index = 0;
        while (index < text.length()) {
            int codepoint = text.codePointAt(index);
            if (barGlyphs.contains(codepoint)) cells++;
            else if (capGlyphs.contains(codepoint)) caps++;
            index += Character.charCount(codepoint);
        }
        return new LineCount(cells, caps);
    }

    /** 便于排查：字形表构成。 */
    public String describe() {
        if (!configured()) return "未发现水位条字形";
        return "格子 " + barGlyphs.size() + " 个字形 / 端帽 " + capGlyphs.size()
            + " 个 / 其中命名含 full " + fullNamedGlyphs.size() + " 个";
    }

    /** 调试用：字形 → 是否计入格子数。 */
    public Map<Integer, Boolean> glyphs() {
        Map<Integer, Boolean> result = new HashMap<>();
        for (int codepoint : barGlyphs) result.put(codepoint, true);
        for (int codepoint : capGlyphs) result.put(codepoint, false);
        return result;
    }

    // ── 构建 ─────────────────────────────────────────────────────────────

    private static StardewWaterBarGlyphMap build() {
        Map<String, JsonObject> fonts = new LinkedHashMap<>();
        collectFontsFromResourceManager(fonts);
        collectFontsFromZip(fonts);
        if (fonts.isEmpty()) return EMPTY;

        Set<Integer> bars = new HashSet<>();
        Set<Integer> caps = new HashSet<>();
        Set<Integer> fullNamed = new HashSet<>();
        for (Map.Entry<String, JsonObject> entry : fonts.entrySet()) {
            Map<Integer, String> glyphs =
                StardewFontResourceParser.parse(entry.getKey(), entry.getValue(), null);
            for (Map.Entry<Integer, String> glyph : glyphs.entrySet()) {
                classify(glyph.getValue(), glyph.getKey(), bars, caps, fullNamed);
            }
        }
        return new StardewWaterBarGlyphMap(bars, caps, fullNamed);
    }

    /** 贴图路径 → 字形归类：只认 {@code .../bars/...}；left / right 归端帽。 */
    private static void classify(String resourcePath, int codepoint, Set<Integer> bars,
                                 Set<Integer> caps, Set<Integer> fullNamed) {
        if (resourcePath == null) return;
        String lower = resourcePath.toLowerCase(Locale.ROOT);
        if (!lower.contains("bars/")) return;
        if (hasCapToken(lower)) {
            caps.add(codepoint);
            return;
        }
        bars.add(codepoint);
        if (lower.contains("full")) fullNamed.add(codepoint);
    }

    /** 文件名末段是否恰为 left / right（按分隔符切段精确匹配，避免 fullright 之类误判）。 */
    private static boolean hasCapToken(String lowerPath) {
        String name = lowerPath;
        int slash = name.lastIndexOf('/');
        if (slash >= 0) name = name.substring(slash + 1);
        int dot = name.indexOf('.');
        if (dot >= 0) name = name.substring(0, dot);
        for (String segment : name.split("[^a-z0-9]+")) {
            if (CAP_TOKENS.contains(segment)) return true;
        }
        return false;
    }

    private static void collectFontsFromResourceManager(Map<String, JsonObject> fonts) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        if (manager == null) return;
        List<PackResources> packs;
        try {
            packs = manager.listPacks().toList();
        } catch (Exception ignored) {
            return;
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
                    // 单个资源包的 font 目录异常不影响其它资源包。
                }
            }
        }
    }

    /** {@code font/<name>.json} → {@code <ns>:<name>}；嵌套路径不是直系字体定义，跳过。 */
    private static String fontIdOf(String namespace, String path) {
        if (path == null || !path.endsWith(".json")) return null;
        String rest = path.startsWith("font/") ? path.substring("font/".length()) : path;
        if (rest.isEmpty() || rest.indexOf('/') >= 0) return null;
        return namespace + ':' + rest.substring(0, rest.length() - ".json".length());
    }

    /**
     * 缓存 ZIP 补充：服务器资源包未必进入 ResourceManager（例如资源仍在加载）。
     * 只补 ResourceManager 缺失的 fontId，绝不覆盖当前真正生效的定义。
     */
    private static void collectFontsFromZip(Map<String, JsonObject> fonts) {
        String serverKey = StardewContext.serverKey();
        if (serverKey == null) return;
        File zip = ResourcePackCache.cachedZip(serverKey);
        if (zip == null) return;
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
            // ZIP 损坏 / 不可读时只用 ResourceManager 的结果。
        }
    }

    /** {@code assets/<ns>/font/<name>.json} → {@code <ns>:<name>}；其它路径返回 null。 */
    private static String zipFontId(String name) {
        if (name == null || !name.startsWith("assets/") || !name.endsWith(".json")) return null;
        String[] parts = name.split("/");
        if (parts.length != 4 || !"font".equals(parts[2])) return null;
        String base = parts[3];
        return parts[1] + ':' + base.substring(0, base.length() - ".json".length());
    }

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
}
