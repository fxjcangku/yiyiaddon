package com.yiyiaddon.feature.stardew.profile;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.platform.resource.ResourcePackAccess;
import com.yiyiaddon.service.resourcepack.ResourcePackCache;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 资源包语言表：直接把资源包里的 {@code lang/*.json} 读成「语言键 → 名称」，供星露谷名称解析使用。
 *
 * <p><b>为什么不能只靠 {@code Language.getInstance()}：</b>客户端语言表只包含「游戏当前语言 + 已应用资源包」
 * 的那一份，而星露谷名称解析必须在这两种情况下也能出中文名：</p>
 * <ul>
 *   <li>服务器资源包<b>没被客户端应用</b>（资源包处理模式为「暴力绕过」、或玩家还没接受推送），
 *       但本模组已经把包下载落盘了（{@link ResourcePackCache}）；</li>
 *   <li>服务器把物品名写进了<b>非 minecraft 命名空间</b>的语言文件（{@code assets/customcrops/lang/zh_cn.json}
 *       或插件自己的命名空间），而 {@code Language} 只按当前语言取一份。</li>
 * </ul>
 *
 * <p><b>读取来源与优先级（前者先占，同键不覆盖）：</b></p>
 * <ol>
 *   <li>{@link ResourcePackAccess#packsByPriority()} —— 当前生效资源包，高优先级在前（服务器包最优先）；</li>
 *   <li>{@link ResourcePackCache#cachedZip(String)} —— 本模组下载落盘的服务器资源包 ZIP，
 *       只补生效资源包没给出的键。</li>
 * </ol>
 *
 * <p><b>语言优先级：</b>{@code zh_cn} → {@code en_us}（中文拿不到就退英文，绝不伪造）。</p>
 *
 * <p><b>只收三类键</b>（{@code item.} / {@code block.} / {@code plugin.customcrops.}）：
 * 整份语言表动辄上万条，把无关条目（成就、字幕、按键）也搬进内存没有意义。
 * {@code plugin.customcrops.} 开头的那批是服务端插件写的规则文案（如「你只能在下界维度种植它」），
 * 作物 × 盆型分组要读它，因此一并留下。</p>
 *
 * <p><b>缓存与失效：</b>按「生效资源包列表 + 缓存 ZIP 的大小/时间戳」算签名，签名变了才重读，
 * 因此换服、换包、客户端资源重载都会自动重读，而同一份资源下反复调用只读一次。
 * 读取全部按需 + 全量吞异常：单个包 / 单个文件坏掉不影响其余资源。</p>
 */
public final class StardewPackLang {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/stardew");

    /** 只保留的键前缀（其余条目与本模组无关，丢弃以省内存） */
    private static final String[] KEPT_PREFIXES = {"item.", "block.", "plugin.customcrops."};

    /**
     * 探测的命名空间。
     *
     * <p>{@code minecraft} 排第一是因为服务器资源包最常见的做法就是把物品名覆盖进原版语言表
     * （真机取证：moexd 的 {@code assets/minecraft/lang/zh_cn.json} 里有 105 条
     * {@code item.customcrops.*}）；{@code customcrops} 是本体自带位置；{@code default} 是部分
     * ItemsAdder 布局把星露谷物品挂上去的命名空间。</p>
     */
    private static final String[] LANG_NAMESPACES = {"minecraft", "customcrops", "default"};

    /** 语言优先级（中文优先，英文兜底） */
    private static final String[] LOCALES = {"zh_cn", "en_us"};

    /** 语言键 → 名称（只读快照，整体替换） */
    private static volatile Map<String, String> names = Map.of();

    /** 当前快照对应的资源签名（空串 = 尚无快照） */
    private static volatile String loadedSignature = "";

    private StardewPackLang() {
    }

    /**
     * 查一条语言键（{@code item.} / {@code block.} / {@code plugin.customcrops.} 前缀皆可）。
     *
     * @return 名称；没有返回 {@code null}（调用方继续用更弱的来源，绝不编名字）
     */
    public static String get(String key) {
        if (key == null || key.isBlank()) return null;
        String value = ensure().get(key);
        return value == null || value.isBlank() ? null : value;
    }

    /**
     * 按逻辑名查物品 / 方块名（{@code item.<ns>.<名>} 优先，其次 {@code block.<ns>.<名>}）。
     *
     * <p>名字同时按「整条路径扁平化」与「末段名」两种形态试：资源包既可能写
     * {@code item.customcrops.crops.tomato.tomato_seeds}（语言键跟着目录走），也可能写
     * {@code item.customcrops.tomato_seeds}（语言键只认末段）。两种都试才不会因为目录层级漏掉名字。</p>
     *
     * @param namespace 命名空间（星露谷 = {@code customcrops}）
     * @param modelName 逻辑名（可带子目录，如 {@code crops/tomato/tomato_seeds}）
     */
    public static String itemName(String namespace, String modelName) {
        if (namespace == null || modelName == null || modelName.isBlank()) return null;
        Map<String, String> table = ensure();
        String flat = modelName.replace('/', '.');
        String last = lastSegment(modelName);
        for (String candidate : flat.equals(last) ? new String[]{flat} : new String[]{flat, last}) {
            String item = table.get("item." + namespace + "." + candidate);
            if (item != null && !item.isBlank()) return item;
            String block = table.get("block." + namespace + "." + candidate);
            if (block != null && !block.isBlank()) return block;
        }
        return null;
    }

    /** 已读取的条目数（诊断用） */
    public static int size() {
        return ensure().size();
    }

    /** 强制下一次查询重读（资源重载 / 换服时由上层调用；不调用也会因签名变化自动重读） */
    public static void invalidate() {
        loadedSignature = "";
    }

    // ── 快照构建 ──

    private static Map<String, String> ensure() {
        String signature = signature();
        if (signature.equals(loadedSignature)) return names;

        Map<String, String> built = new LinkedHashMap<>();
        try {
            readFromPacks(built);
            readFromCachedZip(built);
        } catch (Throwable t) {
            // 语言表读取绝不冒泡：拿不到中文名只是显示退技术名，绝不能让索引构建失败
            LOGGER.warn("资源包语言表读取异常（已读取 {} 条）", built.size(), t);
        }
        names = built;
        loadedSignature = signature;
        LOGGER.info("[资源包语言] 已读取 {} 条（生效资源包 + 服务器缓存 ZIP）", built.size());
        return built;
    }

    /** 资源签名：生效资源包列表 + 缓存 ZIP 的大小/时间戳；变了才重读 */
    private static String signature() {
        StringBuilder sb = new StringBuilder();
        try {
            for (PackResources pack : ResourcePackAccess.packsByPriority()) {
                sb.append(pack.packId()).append('|');
            }
        } catch (Exception ignored) {
            sb.append("pack-list-failed|");
        }
        try {
            String serverKey = ResourcePackCache.currentServerKey();
            if (serverKey != null && !serverKey.isBlank()) {
                File zip = ResourcePackCache.cachedZip(serverKey);
                if (zip != null && zip.isFile()) {
                    sb.append(serverKey).append(':').append(zip.length()).append(':').append(zip.lastModified());
                }
            }
        } catch (Exception ignored) {
            // 缓存信息取不到不影响语言表读取（退化为只读生效资源包）
        }
        return sb.toString();
    }

    /** 从当前生效资源包读（高优先级在前，同键先到先占） */
    private static void readFromPacks(Map<String, String> into) {
        for (PackResources pack : ResourcePackAccess.packsByPriority()) {
            for (String namespace : LANG_NAMESPACES) {
                for (String locale : LOCALES) {
                    readPackFile(pack, namespace, locale, into);
                }
            }
        }
    }

    private static void readPackFile(PackResources pack, String namespace, String locale, Map<String, String> into) {
        IoSupplier<InputStream> supplier;
        try {
            Identifier id = Identifier.fromNamespaceAndPath(namespace, "lang/" + locale + ".json");
            supplier = pack.getResource(PackType.CLIENT_RESOURCES, id);
        } catch (Exception ignored) {
            return;
        }
        if (supplier == null) return;
        try (InputStream in = supplier.get()) {
            merge(into, in);
        } catch (Exception ignored) {
            // 单个文件读取 / 解析失败：跳过它，继续读下一个
        }
    }

    /** 从本模组下载落盘的服务器资源包 ZIP 补键（只补生效资源包没给出的） */
    private static void readFromCachedZip(Map<String, String> into) {
        String serverKey = ResourcePackCache.currentServerKey();
        if (serverKey == null || serverKey.isBlank()) return;
        File zip = ResourcePackCache.cachedZip(serverKey);
        if (zip == null || !zip.isFile() || zip.length() <= 0) return;

        try (ZipFile zipFile = new ZipFile(zip)) {
            for (String namespace : LANG_NAMESPACES) {
                for (String locale : LOCALES) {
                    ZipEntry entry = zipFile.getEntry("assets/" + namespace + "/lang/" + locale + ".json");
                    if (entry == null) continue;
                    try (InputStream in = zipFile.getInputStream(entry)) {
                        merge(into, in);
                    } catch (Exception ignored) {
                        // 单个条目坏掉：跳过
                    }
                }
            }
        } catch (Exception ignored) {
            // ZIP 读不了（被占用 / 格式异常）：退化为只读生效资源包
        }
    }

    /** 解析一份语言文件，把保留前缀的键并入总表（同键不覆盖 = 高优先级先占） */
    private static void merge(Map<String, String> into, InputStream in) {
        JsonElement parsed = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        if (parsed == null || !parsed.isJsonObject()) return;
        JsonObject object = parsed.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String key = entry.getKey();
            if (!keep(key)) continue;
            String value = entry.getValue() == null || !entry.getValue().isJsonPrimitive()
                ? null : entry.getValue().getAsString();
            if (value == null || value.isBlank()) continue;
            into.putIfAbsent(key, clean(value));
        }
    }

    /** 是否是我们需要的键 */
    private static boolean keep(String key) {
        if (key == null || key.isBlank()) return false;
        for (String prefix : KEPT_PREFIXES) {
            if (key.startsWith(prefix)) return true;
        }
        return false;
    }

    /** 剥离颜色代码（语言文件里偶有 §x 样式） */
    private static String clean(String text) {
        return text == null ? "" : text.replaceAll("§[0-9a-fk-orA-FK-ORx]", "").trim();
    }

    private static String lastSegment(String name) {
        if (name == null) return "";
        int slash = name.lastIndexOf('/');
        return slash >= 0 ? name.substring(slash + 1) : name;
    }
}
