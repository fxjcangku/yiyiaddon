package com.yiyiaddon.feature.stardew.profile;

import com.yiyiaddon.platform.resource.ItemModelDispatchIndex;
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
        /**
         * 资源包派发表（{@code assets/<ns>/items/*.json} 里"基础物品 + custom_model_data"的阈值派发）
         * 指向的自定义模型。
         *
         * <p>ItemsAdder / Nexo 这类插件生成的包<b>不提供 items/ 物品定义</b>，自定义物品挂在原版基础物品上
         * 按阈值派发（真机取证：jmy.seasonmc.xyz 的包用 {@code minecraft:paper} 阈值表派发出全部作物种子与工具）。
         * 它是这类布局下唯一的物品定义来源，因此与 {@link #ITEM_DEF} 同级参与建档与计数，但预览要走
         * "基础物品 + custom_model_data"（见 ItemModelDispatchIndex）。</p>
         */
        DISPATCH,
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

        /** 是否由资源包派发表给出的自定义模型（旧布局的物品定义来源） */
        public boolean dispatch() {
            return kind == Kind.DISPATCH;
        }

        /**
         * 是否一条"可建逻辑对象的物品条目"（items/ 物品定义，或派发表给出的自定义模型）。
         *
         * <p>计数（资源生命周期服务）与建档（索引层）必须用同一个判据，禁止各写一份，否则会出现
         * 「服务说已就绪、选择器里却没有条目」这类两边口径不一致的事故。</p>
         */
        public boolean logicalItem() {
            return kind == Kind.ITEM_DEF || kind == Kind.DISPATCH;
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

        // 派发表（"基础物品 + custom_model_data"布局）：物品定义不在 items/ 下，需要额外取一遍。
        // 放在最后：同一模型键若已有 items/ 物品定义（更优，可直接渲染图标），先到先占保留它。
        if (seen.size() < MAX_ENTRIES) scanDispatch(seen);

        result.addAll(seen.values());
        return result;
    }

    /**
     * 枚举资源包派发表里属于星露谷命名空间的模型（旧布局的物品定义来源）。
     *
     * <p>事实来源是 {@link ItemModelDispatchIndex}（它已经把生效资源包、overlays 与优先级都处理好了），
     * 这里只做一件事：把每个模型键变成一个条目，逻辑名取末段（{@code customcrops:item/crops/corn/corn_seeds}
     * → {@code corn_seeds}）。取末段而不是整条路径，是因为后续建档、分类、阶段清单全部按扁平逻辑名工作，
     * 收敛成同一种形态才不会出现 {@code crops/corn/corn_seeds} 与 {@code corn_seeds} 两份身份。</p>
     */
    private static void scanDispatch(Map<String, ScannedModel> seen) {
        Map<String, ItemModelDispatchIndex.PreviewSpec> models =
            ItemModelDispatchIndex.get().modelsIn(STARDEW_NAMESPACE);
        for (Map.Entry<String, ItemModelDispatchIndex.PreviewSpec> entry : models.entrySet()) {
            if (seen.size() >= MAX_ENTRIES) return;
            String modelId = entry.getKey();
            if (modelId == null) continue;
            ItemModelDispatchIndex.PreviewSpec spec = entry.getValue();
            String source = "资源包派发:" + spec.itemId()
                + (spec.threshold() == null ? "" : " 阈值 " + spec.threshold());
            String modelName = lastSegment(modelId);
            if (modelName.isBlank()) continue;
            ScannedModel dispatched = new ScannedModel(modelId, modelName,
                resolveLocalizedName(STARDEW_NAMESPACE, modelName), modelName, source, Kind.DISPATCH);

            ScannedModel existing = seen.get(modelId);
            if (existing != null) {
                // 真机事故：包里的 models/item/** 与派发模型是同一批模型键，先前的 models/item 条目会把
                // 派发条目整批挡掉（同一 id 先到先占），于是"扫到 148 条、0 条物品定义"——
                // 物品定义明明就在包里，却被低价值的模型引用条目顶掉了。
                // 处置：同一模型键若已被 models/item/** 收下且不是 items/ 物品定义，就用派发条目覆盖它
                // （同一个资源、更完整的信息，LinkedHashMap 覆盖保留原位置，不打乱世界模型分支顺序）。
                if (existing.itemDef() || existing.block()) continue;
                seen.put(modelId, dispatched);
                continue;
            }
            seen.put(modelId, dispatched);
        }
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
     * 按语言文件解析本地化名称：资源包自带语言表（{@link StardewPackLang}）→ 客户端已加载语言表
     * （{@code Language}）；{@code item.<ns>.<名>} 优先，其次 {@code block.<ns>.<名>}。
     *
     * <p><b>为什么资源包语言表排在前：</b>客户端语言表只覆盖「游戏当前语言 + 已被应用的那份资源」，
     * 而服务器资源包可能压根没被客户端应用（资源包处理模式「暴力绕过」、玩家还没接受推送），
     * 那时这里什么都查不到，选择器就只剩技术名（真机：moexd 的肥料全部显示 {@code soil_retain_2}）。
     * 本模组自己下载落盘的包里有完整的 {@code lang/zh_cn.json}，直接读它才是可靠来源。</p>
     *
     * <p><b>两种形态都要试：</b>语言键既可能跟着目录走（{@code item.customcrops.crops.tomato.tomato_seeds}），
     * 也可能只认末段（{@code item.customcrops.quality_1}）——服务器爱用后者，漏掉末段就白白丢名字。</p>
     *
     * <p>查不到返回 {@code null}——绝不把资源文件名伪装成中文名，语义层会改用文档化兜底名。</p>
     */
    private static String resolveLocalizedName(String namespace, String modelName) {
        String fromPack = StardewPackLang.itemName(namespace, modelName);
        if (fromPack != null) return fromPack;

        Language language = Language.getInstance();
        if (language == null) return null;
        String flat = modelName.replace('/', '.');
        String last = lastSegment(modelName);
        for (String candidate : flat.equals(last) ? new String[]{flat} : new String[]{flat, last}) {
            String itemKey = "item." + namespace + "." + candidate;
            if (language.has(itemKey)) return clean(language.getOrDefault(itemKey));
            String blockKey = "block." + namespace + "." + candidate;
            if (language.has(blockKey)) return clean(language.getOrDefault(blockKey));
        }
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
