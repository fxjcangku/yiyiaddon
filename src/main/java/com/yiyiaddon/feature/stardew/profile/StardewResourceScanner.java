package com.yiyiaddon.feature.stardew.profile;

import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 星露谷资源包扫描层。
 *
 * <p>只做一件事：从「当前实际生效资源包」里枚举三类真实资源，交给
 * {@link StardewResourceIndex} 归类为六类候选。</p>
 *
 * <p><b>三类资源与它们的真实用途（Minecraft 26.1.2 真实管线）：</b></p>
 * <ul>
 *   <li>{@link Kind#ITEM_DEF} —— {@code assets/<ns>/items/<id>.json}（{@code ClientItemInfoLoader}
 *       用 {@code FileToIdConverter.json("items")} 加载，键为 {@code <ns>:<id>}）。
 *       这是 {@code DataComponents.ITEM_MODEL} 的**合法取值**，也是唯一能真正渲染出图标的 id。
 *       {@code ModelManager.getItemModel(id)} 就是按这个键查的，查不到就渲染黑紫缺失模型。</li>
 *   <li>{@link Kind#ITEM_MODEL} —— {@code assets/<ns>/models/item/**.json}（键 {@code <ns>:item/<name>}）。
 *       它只是「模型引用」，不能直接塞进 {@code ITEM_MODEL} 组件，仅作世界/预览兜底与排查用。</li>
 *   <li>{@link Kind#BLOCK_MODEL} —— {@code assets/<ns>/models/block/**.json}（键 {@code <ns>:block/<name>}）。
 *       种植盆 dry/wet 世界状态、洒水器世界模型的识别依据，绝不当作物品图标。</li>
 * </ul>
 *
 * <p>资源优先级：{@link ResourceManager#listPacks()} 低优先级在前、高优先级（服务器资源包）在后，
 * 倒序遍历即高优先级优先；同一 id 只保留首个命中（即最高优先级包），天然支持资源覆盖。</p>
 *
 * <p>安全边界：只枚举上述三个前缀、{@code .json} 后缀，单次扫描有数量上限；单个包 / 命名空间异常
 * 单独跳过并继续扫描其余有效资源，绝不因一个损坏条目中断整体发现。</p>
 */
public final class StardewResourceScanner {

    /** 单次扫描最多纳入的资源条目（防资源包异常大时拖垮客户端） */
    private static final int MAX_ENTRIES = 12000;

    /**
     * 只扫描星露谷（CustomCrops）命名空间。
     *
     * <p>星露谷 = CustomCrops 盆栽系统，其它命名空间（农夫乐事 {@code default}、
     * {@code customfurniture}、{@code camellia} 等）与作物识别无关。限定命名空间既能
     * 避免大资源包把扫描上限挤爆导致 customcrops 被截断，也能让扫描耗时可控。</p>
     */
    public static final String STARDEW_NAMESPACE = "customcrops";

    /** 资源种类（决定它的真实用途，绝不混用） */
    public enum Kind {
        /** {@code items/*.json}：ITEM_MODEL 组件的合法取值（可渲染物品图标） */
        ITEM_DEF,
        /** {@code models/item/*.json}：物品模型引用（仅兜底/排查） */
        ITEM_MODEL,
        /** {@code models/block/*.json}：方块世界模型（盆 dry/wet、洒水器识别用） */
        BLOCK_MODEL
    }

    /**
     * 扫描结果：一个原始资源条目。
     *
     * @param modelId    资源键（ITEM_DEF 时即合法的 ITEM_MODEL 取值）
     * @param modelName  逻辑名（已剥离 {@code item/} / {@code block/} 前缀，可带子目录）
     * @param displayName 语言文件解析出的中文名；无翻译为 null（绝不伪造）
     * @param rawName    资源原始末段名（无翻译时的可读兜底）
     * @param source     来源说明（排查用）
     * @param kind       资源种类
     */
    public record ScannedModel(String modelId, String modelName, String displayName,
                               String rawName, String source, Kind kind) {

        /** 是否方块世界模型 */
        public boolean block() {
            return kind == Kind.BLOCK_MODEL;
        }

        /** 是否 items/ 物品定义（可渲染的 ITEM_MODEL 取值） */
        public boolean itemDef() {
            return kind == Kind.ITEM_DEF;
        }

        /** 供选择器展示的名称：有翻译用翻译，无翻译用原始末段名 */
        public String label() {
            return displayName != null && !displayName.isBlank() ? displayName : rawName;
        }
    }

    private StardewResourceScanner() {
    }

    /**
     * 扫描当前实际生效资源包的全部物品定义 + 物品模型 + 方块模型（不分类，分类与作物关联交给索引层）。
     *
     * @return 去重后的原始资源列表（高优先级覆盖低优先级，按发现顺序稳定排列）
     */
    public static List<ScannedModel> scan() {
        List<ScannedModel> result = new ArrayList<>();
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if (resourceManager == null) return result;

        Map<String, ScannedModel> seen = new LinkedHashMap<>();

        List<PackResources> packs;
        try {
            packs = resourceManager.listPacks().toList();
        } catch (Exception ignored) {
            return result;
        }

        for (int i = packs.size() - 1; i >= 0 && seen.size() < MAX_ENTRIES; i--) {
            PackResources pack = packs.get(i);
            try {
                for (String namespace : pack.getNamespaces(PackType.CLIENT_RESOURCES)) {
                    if (!STARDEW_NAMESPACE.equals(namespace)) continue;
                    scanPrefix(pack, namespace, "items", Kind.ITEM_DEF, seen);
                    scanPrefix(pack, namespace, "models/item", Kind.ITEM_MODEL, seen);
                    scanPrefix(pack, namespace, "models/block", Kind.BLOCK_MODEL, seen);
                }
            } catch (Exception ignored) {
                // 单个包损坏/读取异常：跳过该包，继续扫描其它包
            }
        }

        result.addAll(seen.values());
        return result;
    }

    /** 枚举某个包 / 命名空间下指定目录的资源，回填到去重映射 */
    private static void scanPrefix(PackResources pack, String namespace, String directory,
                                   Kind kind, Map<String, ScannedModel> seen) {
        if (seen.size() >= MAX_ENTRIES) return;
        try {
            pack.listResources(PackType.CLIENT_RESOURCES, namespace, directory, (id, supplier) -> {
                if (seen.size() >= MAX_ENTRIES) return;
                ScannedModel model = toModel(id, directory, kind);
                if (model != null && !seen.containsKey(model.modelId())) {
                    seen.put(model.modelId(), model);
                }
            });
        } catch (Exception ignored) {
            // 单个目录枚举失败不影响其它目录
        }
    }

    /** 把 listResources 回调的 Identifier 转成资源条目；非法路径返回 null */
    private static ScannedModel toModel(Identifier id, String directory, Kind kind) {
        String path = id.getPath();
        String prefix = directory + "/";
        if (!path.startsWith(prefix) || !path.endsWith(".json")) return null;

        // 去掉目录前缀与 .json 后缀，得到「资源键路径」
        String keyPath = path.substring(prefix.length(), path.length() - ".json".length());
        if (keyPath.isBlank() || keyPath.contains("..")) return null;

        String namespace = id.getNamespace();
        String modelId;
        String modelName;
        if (kind == Kind.ITEM_DEF) {
            // items/<id>.json → 键就是 <ns>:<id>，这是 ITEM_MODEL 组件的合法取值
            modelId = namespace + ":" + keyPath;
            modelName = keyPath;
        } else {
            // models/item/** → <ns>:item/**；models/block/** → <ns>:block/**
            String sub = directory.substring("models/".length()); // item / block
            modelId = namespace + ":" + sub + "/" + keyPath;
            modelName = keyPath;
        }

        String rawName = lastSegment(keyPath);
        String displayName = resolveLocalizedName(namespace, modelName);

        String source = "资源包:" + path;
        return new ScannedModel(modelId, modelName, displayName, rawName, source, kind);
    }

    /**
     * 按语言文件解析本地化名称（{@code item.<ns>.<name>} 优先，其次 {@code block.<ns>.<name>}）。
     *
     * <p>查不到返回 null——绝不把资源文件名伪装成中文名，语义层会改用文档化兜底名。</p>
     */
    private static String resolveLocalizedName(String namespace, String modelName) {
        Language language = Language.getInstance();
        if (language == null) return null;
        String flat = modelName.replace('/', '.');
        String itemKey = "item." + namespace + "." + flat;
        if (language.has(itemKey)) return clean(language.getOrDefault(itemKey));
        String blockKey = "block." + namespace + "." + flat;
        if (language.has(blockKey)) return clean(language.getOrDefault(blockKey));
        return null;
    }

    private static String lastSegment(String name) {
        if (name == null) return "";
        int slash = name.lastIndexOf('/');
        return slash >= 0 ? name.substring(slash + 1) : name;
    }

    private static String clean(String text) {
        if (text == null) return "";
        return text.replaceAll("§[0-9a-fk-orA-FK-ORx]", "").trim();
    }

    /** 判断一个资源条目是否属于星露谷命名空间 */
    public static boolean isStardew(String modelId) {
        return modelId != null && modelId.toLowerCase(Locale.ROOT).startsWith("customcrops:");
    }
}
