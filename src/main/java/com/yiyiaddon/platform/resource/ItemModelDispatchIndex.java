package com.yiyiaddon.platform.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 物品模型派发表索引（"基础物品 + {@code custom_model_data}" 布局的通用读取层）。
 *
 * <p><b>为什么需要它（真机取证，非推测）：</b>ItemsAdder / Nexo 这类插件生成的资源包<b>不提供</b>
 * {@code assets/<ns>/items/<id>.json} 物品定义，而是把自定义物品挂在原版基础物品上，用
 * {@code custom_model_data} 的 {@code range_dispatch} 按阈值派发到自定义模型。真实包实测
 * （{@code jmy.seasonmc.xyz} 的「季明月」包，客户端生效的 {@code ia_overlay_1_21_6_plus} 层）：</p>
 *
 * <pre>
 * assets/minecraft/items/paper.json
 *   model.type = range_dispatch, property = custom_model_data
 *   entries: 10548 → customcrops:item/basics/dry_pot
 *            10579 → customcrops:item/crops/corn/corn_seeds
 *            10634 → customcrops:item/sprinklers/sprinkler_1   …
 * assets/minecraft/items/apple.json        10007 → customcrops:item/crops/corn/corn
 * assets/minecraft/items/diamond_horse_armor.json 10000 → customcrops:item/wateringcans/watering_can_1
 * </pre>
 *
 * <p>即：物品的真实身份由「基础物品 + {@code custom_model_data} 第 0 位」决定，且这张表就躺在
 * 资源包里。本类把它读出来，供三处使用（全项目只此一份，禁止各写一套）：</p>
 * <ol>
 *   <li>资源扫描：得到「这个包到底有哪些自定义物品模型」（旧布局的唯一物品定义来源）；</li>
 *   <li>库存匹配：由物品栈反查模型键，{@code item_model} 组件缺失时仍能精确识别种子 / 工具；</li>
 *   <li>图标预览：由模型键反查「基础物品 + 阈值」，用与服务端完全相同的方式构造预览栈。</li>
 * </ol>
 *
 * <p><b>只认有据可查的两种写法</b>（{@code model} 与 {@code range_dispatch(property=custom_model_data)}），
 * 其余 item model 类型（{@code select} / {@code composite} / 特殊模型）一律跳过——宁可少认，不猜结构。
 * 阈值一律取第 0 个浮点值（{@code index} 缺省即为 0，与 {@code custom_model_data} 组件的读取口径一致）。</p>
 *
 * <p><b>两种包布局都读</b>（同一张表，同一种语义：基础物品 + {@code custom_model_data} → 自定义模型）：</p>
 * <ol>
 *   <li>新式（1.21.4+ 文件式物品定义）：{@code assets/<ns>/items/<载体>.json} 的
 *       {@code range_dispatch} —— 上面的真机样例；</li>
 *   <li>旧式（旧版 ItemsAdder / 手工包）：{@code assets/<ns>/models/item/<载体>.json} 的
 *       {@code overrides[].predicate.custom_model_data} —— 已用真实包核过形态
 *       （{@code frp-dad.com} 1036 个载体 / 3743 条、{@code jmy.seasonmc.xyz} 23 个载体 / 1147 条、
 *       {@code tingwanmc.top} 7 个载体 / 2048 条），其中非 {@code custom_model_data} 的原版谓词
 *       （{@code damage} / {@code trim_type} / {@code blocking} …）一律跳过。</li>
 * </ol>
 * <p>两次读取里新式排在前，且同键「先到先占」，因此同一模型键同时存在两种写法时以新式为准；
 * 跨资源包同样是高优先级先占。</p>
 *
 * <p><b>读取口径：</b>{@code ResourceManager.listPacks()} 低优先级在前，倒序遍历即高优先级优先；
 * 生效资源包（含 {@code pack.mcmeta} 里声明的 overlays）由原版 {@code CompositePackResources} 合并后
 * 提供，因此在这里读到的就是客户端真正使用的那一份。同一（基础物品, 阈值）与同一模型键都只保留
 * 首个命中，天然支持资源覆盖。</p>
 *
 * <p><b>开销与边界：</b>单次构建读取上限 {@link #MAX_FILES} 个文件、单文件上限 {@link #MAX_TEXT_BYTES}
 * 字节；构建结果缓存，资源重载时由资源生命周期服务调用 {@link #invalidate()} 作废。扫描只在玩家触发
 * 检测 / 重建索引时发生，不在 render / tick 里跑。</p>
 */
public final class ItemModelDispatchIndex {

    /** 单次构建最多解析的文件数（防异常巨大的资源包拖垮客户端） */
    private static final int MAX_FILES = 20000;

    /**
     * 单个派发文件的文本上限。
     *
     * <p>新式 {@code items/<载体>.json} 通常只有几百字节到几十 KB；旧式载体模型的 {@code overrides}
     * 数组会随该载体承载的物品数线性增长（真机取证：221 条目的新式表 22 KB，旧式同规模约 30–40 KB），
     * 数百件物品的服务器可能到一两百 KB，因此给到 {@value}。超过上限的单文件按读不到处理，
     * 宁可少读一个载体，也不让异常巨大的文件拖垮客户端。</p>
     */
    private static final int MAX_TEXT_BYTES = 256 * 1024;

    /** 物品定义所在目录（与 {@code FileToIdConverter.json("items")} 一致） */
    private static final String ITEMS_DIRECTORY = "items/";

    /**
     * 旧布局载体模型所在目录：{@code models/item/<载体>.json} 里的 {@code overrides} 就是旧式派发表。
     *
     * <p>真机场景：旧版 ItemsAdder / 手工包不生成 1.21.4+ 的 {@code items/} 物品定义，而把
     * 「基础物品 + {@code custom_model_data} → 自定义模型」写在载体模型里；客户端对两者是同一种语义，
     * 因此这里把旧式 {@code overrides} 也灌进同一张派发表。</p>
     */
    private static final String MODELS_ITEM_DIRECTORY = "models/item/";

    /** 物品定义文件后缀 */
    private static final String JSON_SUFFIX = ".json";

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/resource");

    /** 当前快照；{@code null} 表示需要重建（懒构建，避免每次读盘） */
    private static volatile Snapshot snapshot;

    private ItemModelDispatchIndex() {
    }

    /** 当前资源对应的派发表快照（没有则按需构建一次） */
    public static Snapshot get() {
        Snapshot current = snapshot;
        if (current != null) return current;
        synchronized (ItemModelDispatchIndex.class) {
            if (snapshot == null) snapshot = build();
            return snapshot;
        }
    }

    /** 资源重载后作废（由资源生命周期服务在重载完成时调用） */
    public static void invalidate() {
        snapshot = null;
    }

    /**
     * 模型键 → 扁平逻辑身份。
     *
     * <p>{@code customcrops:item/crops/corn/corn_seeds} → {@code customcrops:corn_seeds}。
     * 用于把「模型引用」交给按扁平身份工作的既有链路（学名、身份派生），避免带目录路径被
     * 身份派生规则拼成 {@code corn_corn_seeds} 这种假身份。</p>
     */
    public static String flatIdentityOf(String modelKey) {
        if (modelKey == null || modelKey.isBlank()) return null;
        int colon = modelKey.indexOf(':');
        if (colon <= 0 || colon == modelKey.length() - 1) return null;
        String path = modelKey.substring(colon + 1);
        int slash = path.lastIndexOf('/');
        String last = slash >= 0 ? path.substring(slash + 1) : path;
        return last.isBlank() ? null : modelKey.substring(0, colon) + ":" + last;
    }

    /** 派发表给出的预览规格：基础物品 + 阈值（阈值为 {@code null} 表示该物品定义直接指向模型） */
    public record PreviewSpec(String itemId, Integer threshold) {
    }

    /** 单条派发关系 */
    private record Dispatch(int threshold, String modelKey) {
    }

    /**
     * 一份派发表快照（只读）。
     *
     * <p>三张表都按「高优先级先到先占」构建，键一律是小写字符串，不含任何 Minecraft 对象，
     * 因此可以安全地跨线程读取。</p>
     */
    public static final class Snapshot {

        /** 基础物品 id → 直接指定的模型键（{@code model.type=model} 且没有阈值派发时） */
        private final Map<String, String> directByItem;
        /** 基础物品 id → 升序阈值表 */
        private final Map<String, List<Dispatch>> dispatchByItem;
        /** 模型键 → 预览规格 */
        private final Map<String, PreviewSpec> previewByModel;
        /** 命名空间 → （模型键 → 预览规格），供资源扫描按命名空间取用 */
        private final Map<String, Map<String, PreviewSpec>> byNamespace;

        private Snapshot(Map<String, String> directByItem, Map<String, List<Dispatch>> dispatchByItem,
                         Map<String, PreviewSpec> previewByModel,
                         Map<String, Map<String, PreviewSpec>> byNamespace) {
            this.directByItem = directByItem;
            this.dispatchByItem = dispatchByItem;
            this.previewByModel = previewByModel;
            this.byNamespace = byNamespace;
        }

        /** 某命名空间下的全部派发模型（模型键 → 预览规格）；没有返回空表 */
        public Map<String, PreviewSpec> modelsIn(String namespace) {
            if (namespace == null) return Map.of();
            Map<String, PreviewSpec> models = byNamespace.get(namespace.toLowerCase(Locale.ROOT));
            return models == null ? Map.of() : models;
        }

        /** 某模型键的预览规格；该模型不由派发表提供时返回 {@code null} */
        public PreviewSpec previewOf(String modelKey) {
            return modelKey == null ? null : previewByModel.get(modelKey.toLowerCase(Locale.ROOT));
        }

        /** 快照是否为空（没有任何派发条目） */
        public boolean isEmpty() {
            return previewByModel.isEmpty();
        }

        /**
         * 物品栈在当前资源里的派发模型键。
         *
         * <p>读「基础物品 + {@code custom_model_data} 第 0 位」，取不超过该值的最大阈值条目；
         * 取值低于全部阈值、或该基础物品没有阈值表时退回它的直接模型定义；都取不到返回 {@code null}
         * （绝不猜：宁可判不出来，也不乱认身份）。</p>
         */
        public String modelKeyOf(ItemStack stack) {
            if (stack == null || stack.isEmpty()) return null;
            String itemId = itemIdOf(stack);
            if (itemId == null) return null;

            List<Dispatch> entries = dispatchByItem.get(itemId);
            if (entries == null) return directByItem.get(itemId);

            Float value = customModelDataOf(stack, 0);
            if (value == null) return directByItem.get(itemId);
            String matched = null;
            for (Dispatch entry : entries) {
                if (value >= entry.threshold()) matched = entry.modelKey();
                else break;
            }
            return matched != null ? matched : directByItem.get(itemId);
        }

        /** 物品栈是否就是某个模型键（{@code item_model} 组件之外的第二条判据） */
        public boolean matches(ItemStack stack, String modelKey) {
            return modelKey != null && modelKey.equalsIgnoreCase(modelKeyOf(stack));
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  构建
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 构建器：按「先到先占」登记，等价于高优先级资源包覆盖低优先级 */
    private static final class Builder {
        final Map<String, String> directByItem = new LinkedHashMap<>();
        final Map<String, List<Dispatch>> dispatchByItem = new LinkedHashMap<>();
        final Map<String, PreviewSpec> previewByModel = new LinkedHashMap<>();
        final Map<String, Map<String, PreviewSpec>> byNamespace = new LinkedHashMap<>();

        void direct(String itemId, String modelKey) {
            directByItem.putIfAbsent(itemId, modelKey);
            register(modelKey, new PreviewSpec(itemId, null));
        }

        void dispatch(String itemId, int threshold, String modelKey) {
            List<Dispatch> entries = dispatchByItem.computeIfAbsent(itemId, key -> new ArrayList<>());
            for (Dispatch entry : entries) {
                if (entry.threshold() == threshold) return;   // 同阈值先到先占
            }
            entries.add(new Dispatch(threshold, modelKey));
            register(modelKey, new PreviewSpec(itemId, threshold));
        }

        /** 登记模型键（同键先到先占，并归入命名空间表） */
        private void register(String modelKey, PreviewSpec spec) {
            String key = modelKey.toLowerCase(Locale.ROOT);
            if (previewByModel.containsKey(key)) return;
            previewByModel.put(key, spec);
            int colon = key.indexOf(':');
            if (colon <= 0) return;
            String namespace = key.substring(0, colon);
            byNamespace.computeIfAbsent(namespace, ns -> new LinkedHashMap<>()).put(key, spec);
        }

        Snapshot toSnapshot() {
            Map<String, List<Dispatch>> dispatch = new LinkedHashMap<>();
            for (Map.Entry<String, List<Dispatch>> entry : dispatchByItem.entrySet()) {
                List<Dispatch> entries = new ArrayList<>(entry.getValue());
                entries.sort((a, b) -> Integer.compare(a.threshold(), b.threshold()));
                dispatch.put(entry.getKey(), Collections.unmodifiableList(entries));
            }
            Map<String, Map<String, PreviewSpec>> namespaces = new LinkedHashMap<>();
            for (Map.Entry<String, Map<String, PreviewSpec>> entry : byNamespace.entrySet()) {
                namespaces.put(entry.getKey(), Collections.unmodifiableMap(new LinkedHashMap<>(entry.getValue())));
            }
            return new Snapshot(Collections.unmodifiableMap(new LinkedHashMap<>(directByItem)),
                Collections.unmodifiableMap(dispatch),
                Collections.unmodifiableMap(new LinkedHashMap<>(previewByModel)),
                Collections.unmodifiableMap(namespaces));
        }
    }

    /** 读取当前生效资源包里的全部物品派发关系；任何异常都退化为"读不到"（绝不影响客户端） */
    private static Snapshot build() {
        Builder builder = new Builder();
        Minecraft minecraft = Minecraft.getInstance();
        ResourceManager resourceManager = minecraft == null ? null : minecraft.getResourceManager();
        if (resourceManager == null) return builder.toSnapshot();

        List<PackResources> packs;
        try {
            packs = resourceManager.listPacks().toList();
        } catch (Exception e) {
            return builder.toSnapshot();
        }

        int[] parsed = {0};
        long startedAt = System.nanoTime();
        for (int i = packs.size() - 1; i >= 0 && parsed[0] < MAX_FILES; i--) {
            PackResources pack = packs.get(i);
            try {
                for (String namespace : pack.getNamespaces(PackType.CLIENT_RESOURCES)) {
                    if (parsed[0] >= MAX_FILES) break;
                    // 新式（1.21.4+）：assets/<ns>/items/<载体>.json
                    pack.listResources(PackType.CLIENT_RESOURCES, namespace, "items",
                        (id, supplier) -> parse(itemIdOfFile(id, ITEMS_DIRECTORY), supplier, builder, parsed));
                    if (parsed[0] >= MAX_FILES) break;
                    // 旧式兜底：assets/<ns>/models/item/<载体>.json 的 overrides（新式先读、先到先占，
                    // 因此同一模型键同时存在两种写法时以新式为准）
                    pack.listResources(PackType.CLIENT_RESOURCES, namespace, "models/item",
                        (id, supplier) -> parseLegacy(itemIdOfFile(id, MODELS_ITEM_DIRECTORY), supplier, builder, parsed));
                }
            } catch (Throwable ignored) {
                // 单个包读取失败：静默跳过，继续读其余包（绝不影响客户端）
            }
        }

        Snapshot snapshot = builder.toSnapshot();
        LOGGER.info("[派发表] 已读取生效资源包的物品派发关系：文件 {} 个，自定义模型 {} 个，耗时 {} ms",
            parsed[0], snapshot.previewByModel.size(),
            Math.round((System.nanoTime() - startedAt) / 1_000_000.0));
        return snapshot;
    }

    /**
     * 列表回调给出的是<b>文件 id</b>（{@code minecraft:items/paper.json}），需要还原成<b>物品 id</b>
     * （{@code minecraft:paper}）：去掉目录前缀与 {@code .json} 后缀。
     *
     * <p>真机事故：先前直接把文件 id 当物品 id 存进派发表，于是预览构造去找
     * {@code minecraft:items/paper.json} 这个"物品"（注册表里当然没有），兜底渲染与运行期物品匹配
     * 双双落空——图标显示缺失、背包里有种子也认不出来。物品定义文件的命名规则与
     * {@code FileToIdConverter.json("items")} 一致，这里按同一规则还原。</p>
     *
     * @param directory 该文件所在目录的前缀（{@link #ITEMS_DIRECTORY} 或 {@link #MODELS_ITEM_DIRECTORY}）
     */
    private static String itemIdOfFile(Identifier fileId, String directory) {
        if (fileId == null) return null;
        String path = fileId.getPath();
        if (path.startsWith(directory)) path = path.substring(directory.length());
        if (path.endsWith(JSON_SUFFIX)) path = path.substring(0, path.length() - JSON_SUFFIX.length());
        return path.isBlank() ? null : fileId.getNamespace() + ":" + path;
    }

    /** 解析一个 {@code assets/<ns>/items/<id>.json}；读取 / 解析失败一律跳过 */
    private static void parse(String itemId, IoSupplier<InputStream> supplier, Builder builder, int[] parsed) {
        if (itemId == null || supplier == null || parsed[0] >= MAX_FILES) return;
        parsed[0]++;
        String text = readText(supplier);
        if (text == null) return;
        try {
            JsonElement root = JsonParser.parseString(text);
            if (!root.isJsonObject()) return;
            JsonElement modelEl = root.getAsJsonObject().get("model");
            if (modelEl == null || !modelEl.isJsonObject()) return;
            readModel(itemId, modelEl.getAsJsonObject(), builder);
        } catch (Exception ignored) {
            // 单个文件解析失败：静默跳过
        }
    }

    /**
     * 解析旧布局载体模型 {@code assets/<ns>/models/item/<载体>.json} 里的 {@code overrides} 派发表。
     *
     * <p>真机场景（跨版本进低版本服务器时常见）：旧版 ItemsAdder / 手工包不生成 1.21.4+ 的
     * {@code items/} 物品定义，而把派发写在载体模型里——
     * {@code {"overrides":[{"predicate":{"custom_model_data":10001},"model":"customcrops:item/..."}]}}。
     * 客户端对这两种写法是同一种语义（基础物品 + {@code custom_model_data} → 自定义模型），
     * 因此灌进同一张派发表：新式先读、同键先到先占，于是新式永远压过旧式。</p>
     *
     * <p>只认带 {@code custom_model_data} 谓词的条目：{@code damage} / {@code damage_state} /
     * {@code custom_name} 等原版谓词与本机制无关，混进来只会给出错误的预览栈。绝大多数载体模型
     * 根本没有 {@code overrides}，因此先做一次廉价的文本包含判断，不命中就不解析 JSON。</p>
     */
    private static void parseLegacy(String itemId, IoSupplier<InputStream> supplier, Builder builder, int[] parsed) {
        if (itemId == null || supplier == null || parsed[0] >= MAX_FILES) return;
        parsed[0]++;
        String text = readText(supplier);
        if (text == null) return;
        if (!text.contains("overrides")) return;
        try {
            JsonElement root = JsonParser.parseString(text);
            if (!root.isJsonObject()) return;
            JsonElement overridesEl = root.getAsJsonObject().get("overrides");
            if (overridesEl == null || !overridesEl.isJsonArray()) return;

            for (JsonElement el : overridesEl.getAsJsonArray()) {
                if (!el.isJsonObject()) continue;
                JsonObject override = el.getAsJsonObject();
                JsonElement predicateEl = override.get("predicate");
                if (predicateEl == null || !predicateEl.isJsonObject()) continue;
                Integer threshold = number(predicateEl.getAsJsonObject().get("custom_model_data"));
                String reference = qualify(string(override, "model"), itemId);
                if (threshold == null || reference == null) continue;
                builder.dispatch(itemId, threshold, reference);
            }
        } catch (Exception ignored) {
            // 单个文件解析失败：静默跳过
        }
    }

    /** 读取一层 item model 定义：{@code model} 记直接模型，{@code range_dispatch} 记阈值表 */
    private static void readModel(String itemId, JsonObject model, Builder builder) {
        String type = string(model, "type");
        if (type == null) return;
        if ("model".equals(type)) {
            String reference = qualify(string(model, "model"), itemId);
            if (reference != null) builder.direct(itemId, reference);
            return;
        }
        if (!"range_dispatch".equals(type)) return;
        if (!"custom_model_data".equals(string(model, "property"))) return;
        if (integer(model, "index", 0) != 0) return;
        JsonElement entriesEl = model.get("entries");
        if (entriesEl == null || !entriesEl.isJsonArray()) return;

        JsonArray entries = entriesEl.getAsJsonArray();
        for (JsonElement el : entries) {
            if (!el.isJsonObject()) continue;
            JsonObject entry = el.getAsJsonObject();
            Integer threshold = number(entry.get("threshold"));
            if (threshold == null) continue;
            JsonElement nestedEl = entry.get("model");
            if (nestedEl == null || !nestedEl.isJsonObject()) continue;
            JsonObject nested = nestedEl.getAsJsonObject();
            if (!"model".equals(string(nested, "type"))) continue;
            String reference = qualify(string(nested, "model"), itemId);
            if (reference == null) continue;
            builder.dispatch(itemId, threshold, reference);
        }
    }

    /**
     * 模型引用补全命名空间：原版 {@code model} 引用不带命名空间时与<b>所在文件</b>同命名空间
     * （真机取证：{@code 2d2l.cn} 的旧式表里写的是 {@code "model":"gui/icons/invisible_botton"}，
     * 即 {@code minecraft:gui/icons/invisible_botton}）。不补这一下，这类引用会变成永远匹配不上的
     * 裸字符串，白占内存且拿不到预览。
     */
    private static String qualify(String reference, String itemId) {
        if (reference == null || reference.isBlank()) return null;
        if (reference.indexOf(':') > 0) return reference;
        int colon = itemId == null ? -1 : itemId.indexOf(':');
        String namespace = colon > 0 ? itemId.substring(0, colon) : "minecraft";
        return namespace + ":" + reference;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  Minecraft 侧读取
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 物品栈的注册表 id；取不到返回 {@code null} */
    private static String itemIdOf(ItemStack stack) {
        try {
            Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
            return id == null ? null : id.toString().toLowerCase(Locale.ROOT);
        } catch (Exception e) {
            return null;
        }
    }

    /** 读取 {@code custom_model_data} 第 index 位浮点值；无该组件或无该位返回 {@code null} */
    private static Float customModelDataOf(ItemStack stack, int index) {
        try {
            CustomModelData data = stack.get(DataComponents.CUSTOM_MODEL_DATA);
            return data == null ? null : data.getFloat(index);
        } catch (Exception e) {
            return null;
        }
    }

    /** 按字节上限读取资源文本（超限视为读不到，绝不为一个异常文件吃满内存） */
    private static String readText(IoSupplier<InputStream> supplier) {
        try (InputStream in = supplier.get()) {
            if (in == null) return null;
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] buffer = new byte[4096];
            int total = 0;
            int read;
            while ((read = in.read(buffer)) != -1) {
                total += read;
                if (total > MAX_TEXT_BYTES) return null;
                out.write(buffer, 0, read);
            }
            return out.toString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    /** JSON 字符串字段（缺失 / 非字符串返回 {@code null}） */
    private static String string(JsonObject obj, String key) {
        if (obj == null || !obj.has(key)) return null;
        JsonElement el = obj.get(key);
        if (el == null || !el.isJsonPrimitive() || !el.getAsJsonPrimitive().isString()) return null;
        String value = el.getAsString();
        return value == null || value.isBlank() ? null : value;
    }

    /** JSON 整数字段（缺失 / 非数字返回 {@code fallback}） */
    private static int integer(JsonObject obj, String key, int fallback) {
        if (obj == null || !obj.has(key)) return fallback;
        Integer value = number(obj.get(key));
        return value == null ? fallback : value;
    }

    /** JSON 数值 → 整数（非数值返回 {@code null}） */
    private static Integer number(JsonElement el) {
        if (el == null || !el.isJsonPrimitive() || !el.getAsJsonPrimitive().isNumber()) return null;
        try {
            return (int) Math.round(el.getAsDouble());
        } catch (Exception e) {
            return null;
        }
    }
}
