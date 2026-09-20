package com.yiyiaddon.feature.stardew.selector;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.platform.resource.ItemModelDispatchIndex;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomModelData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 星露谷图标预览工厂（真实资源校验版）。
 *
 * <p><b>为什么必须有校验：</b>Minecraft 26.1.2 的物品渲染链路是
 * {@code ItemModelResolver.appendItemLayers} 读 {@code DataComponents.ITEM_MODEL}
 * → {@code ModelManager.getItemModel(id)}；ModelManager 里的表由
 * {@code assets/<ns>/items/<id>.json}（{@code ClientItemInfoLoader} 用
 * {@code FileToIdConverter.json("items")} 加载）构建。</p>
 *
 * <p>只要把一个「不是 items/ 键」的字符串（例如 {@code customcrops:item/tomato} 这种
 * 模型引用，或 {@code customcrops:block/misc/dry_pot_1} 这种方块模型键）塞进
 * {@code ITEM_MODEL}，查表必然落空，渲染出来就是黑紫 Missing Model。</p>
 *
 * <p>因此本工厂只接受「当前资源包里确实存在 {@code items/<id>.json}」的键，不存在时
 * 直接返回 {@link ItemStack#EMPTY} 并输出完整诊断链（namespace / modelKey / itemModel /
 * 模型引用 / texture / 失败原因），由界面显示可读的「缺失」提示而不是静默黑紫。</p>
 *
 * <p><b>读取方式：</b>按 {@code ResourceManager.listPacks()} 倒序（高优先级在前，即当前
 * 生效的服务器资源包优先）逐个 {@code PackResources.getResource(PackType.CLIENT_RESOURCES, id)}
 * 读取，与项目内其它资源解析器一致；不直接使用被 Fabric 织入过的 {@code Resource} 类型。</p>
 */
public final class StardewPreview {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/stardew");

    /** 模型 parent 链的深度上限：层模型的贴图常声明在父模型上，但要防资源包里出现环。 */
    private static final int MAX_MODEL_DEPTH = 8;

    /** 物品图集定义：物品模型的贴图引用优先在这一张图集里反查 sprite 名。 */
    private static final Identifier ITEM_ATLAS =
        Identifier.fromNamespaceAndPath("minecraft", "atlases/items.json");

    /** 图集定义所在目录（图集定义一律挂在 {@code minecraft} 命名空间下）。 */
    private static final String ATLAS_DIRECTORY = "atlases";

    /**
     * sprite 名 → 贴图 PNG 资源路径（惰性构建一次；{@code null} 表示还没建过）。
     *
     * <p>资源包重载后由资源生命周期服务调用 {@link #invalidate()} 作废。</p>
     */
    private static volatile Map<String, String> spriteTextures;

    private StardewPreview() {
    }

    /**
     * 判断一个 ITEM_MODEL 取值是否可渲染。
     *
     * <p>判据：当前生效资源包里存在 {@code assets/<ns>/items/<path>.json}。该文件就是
     * {@code ClientItemInfoLoader} 构建 {@code ModelManager} 物品模型表的唯一来源，
     * 有文件就一定有模型。</p>
     */
    public static boolean isRenderable(String itemModelId) {
        Identifier id = Identifier.tryParse(itemModelId == null ? "" : itemModelId);
        if (id == null) return false;
        return exists(itemDefLocation(id));
    }

    /**
     * 构造可交给界面渲染的真实预览 ItemStack。
     *
     * <p>优先「真实注册物品」：若该 id 在物品注册表中存在，直接用注册物品实例（保留其默认组件）。
     * 否则按 26.1.2 真实管线构造：基础载体 + {@code ITEM_MODEL} 组件指向 items/ 键。
     * 键不合法（资源包无对应 items/ 定义）时返回 {@link ItemStack#EMPTY}。</p>
     */
    public static ItemStack of(String itemModelId) {
        Identifier id = Identifier.tryParse(itemModelId == null ? "" : itemModelId);
        if (id == null) return ItemStack.EMPTY;
        if (!isRenderable(itemModelId)) {
            // 旧布局（ItemsAdder 等）：没有 items/ 定义，物品图标由"原版基础物品 + custom_model_data"
            // 在基础物品自己的 items/*.json 里派发出来 —— 与服务端完全同一条渲染链
            ItemStack dispatched = dispatchPreview(itemModelId);
            if (!dispatched.isEmpty()) return dispatched;
            LOGGER.warn("[StardewPreview] 缺失物品模型：{}", diagnose(itemModelId));
            return ItemStack.EMPTY;
        }
        try {
            Item registered = BuiltInRegistries.ITEM.getValue(id);
            if (registered != null && registered != Items.AIR) {
                // 真实注册物品：直接用它的默认实例，最贴近真实渲染
                ItemStack stack = new ItemStack(registered);
                if (stack.get(DataComponents.ITEM_MODEL) == null) {
                    stack.set(DataComponents.ITEM_MODEL, id);
                }
                return stack;
            }
            // 未被注册表收录的自定义物品（服务器资源包 items/ 定义）：按真实管线构造
            ItemStack stack = new ItemStack(Items.PAPER);
            stack.set(DataComponents.ITEM_MODEL, id);
            return stack;
        } catch (Exception e) {
            LOGGER.warn("[StardewPreview] 构造预览失败：{}（{}）", itemModelId, e.getMessage());
            return ItemStack.EMPTY;
        }
    }

    /**
     * 完整诊断链：namespace / modelKey / itemModel / 模型引用 / texture / 失败原因。
     *
     * <p>用于日志与 GUI「缺失」提示，拒绝静默黑紫。</p>
     */
    public static String diagnose(String itemModelId) {
        Identifier id = Identifier.tryParse(itemModelId == null ? "" : itemModelId);
        if (id == null) {
            return "失败原因=非法物品模型 id（itemModel=" + itemModelId + "）";
        }
        Identifier defLocation = itemDefLocation(id);
        if (!exists(defLocation)) {
            return "namespace=" + id.getNamespace()
                + " modelKey=" + defLocation.getPath()
                + " itemModel=" + itemModelId
                + " texture=（未知，物品定义缺失）"
                + " 失败原因=资源包未提供该 items/ 物品定义";
        }
        JsonObject def = readJson(defLocation);
        String modelRef = def == null ? null : modelReference(def);
        String texture = modelRef == null ? null : readModelTexture(modelRef);
        return "namespace=" + id.getNamespace()
            + " modelKey=" + defLocation.getPath()
            + " itemModel=" + itemModelId
            + " 模型引用=" + (modelRef == null ? "（解析失败）" : modelRef)
            + " texture=" + (texture == null ? "（未在模型里声明）" : texture)
            + " 结论=可渲染";
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  模型键 → 贴图路径
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 物品模型键 → 贴图 PNG 资源路径（例 {@code customcrops:textures/item/crops/corn/corn.png}）。
     *
     * <p><b>用途</b>：ESP 世界字牌的图标只能直接读资源包 PNG（见 {@code TextureImageCache} 的说明），
     * 拿不到物品渲染链路，因此需要这一层「模型键 → 贴图」解析。解析不出来返回 {@code null}，
     * 调用方退化成纯文字字牌——图标缺失不该让字牌消失。</p>
     *
     * <p>链路与真实渲染一致：{@code items/<id>.json} 的 {@code model.model} 指向模型引用，
     * 模型文件里 {@code textures.layer0} 就是贴图；旧布局（ItemsAdder 等）没有 {@code items/}
     * 定义，模型键本身就是 {@code models/<path>.json} 的键（真机取证：
     * {@code customcrops:item/crops/corn/corn}）。层模型常把贴图留在父模型上，故沿 parent 链找。</p>
     */
    public static String textureOf(String itemModelId) {
        String modelRef = modelReferenceOf(itemModelId);
        if (modelRef == null) return null;
        String textureKey = readModelTexture(modelRef);
        if (textureKey == null) return null;
        // 一、按原版语义：layer0 就是裸贴图路径（标准资源包走这条）
        String direct = existingPng(textureKey);
        if (direct != null) return direct;
        // 二、按图集 sprite 名反查：ItemsAdder 旧布局的 layer0 是它自建图集里的 sprite 名
        //     （真机取证：customcrops 玉米模型写的是 "ia:75"，真实贴图在
        //      {"type":"single","resource":"customcrops:item/crops/corn/corn","sprite":"ia:75"} 里）
        String key = canonical(textureKey);
        String mapped = key == null ? null : spriteTextures().get(key);
        if (mapped == null) return null;
        // 表里存的已经是拼好的 PNG 路径，这里只校验它真实存在（不能再走 existingPng，
        // 那会把 textures/ 前缀再拼一次，路径直接作废）
        Identifier png = Identifier.tryParse(mapped);
        return png != null && exists(png) ? mapped : null;
    }

    /** 资源包重载后作废图集反查表（与 {@code ItemModelDispatchIndex.invalidate()} 同一时机） */
    public static void invalidate() {
        spriteTextures = null;
    }

    /** 物品模型键 → 模型引用：新布局走 {@code items/} 定义，旧布局把键本身当模型文件键 */
    private static String modelReferenceOf(String itemModelId) {
        Identifier id = Identifier.tryParse(itemModelId == null ? "" : itemModelId);
        if (id == null) return null;
        Identifier defLocation = itemDefLocation(id);
        if (exists(defLocation)) {
            String ref = modelReference(readJson(defLocation));
            if (ref != null) return ref;
        }
        return itemModelId;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  图集 sprite 反查（ItemsAdder 旧布局的 layer0 专用）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 惰性构建一次的图集反查表；资源包没就绪时返回空表且不缓存这个结论 */
    private static Map<String, String> spriteTextures() {
        Map<String, String> local = spriteTextures;
        if (local != null) return local;
        List<PackResources> packs = packs();
        if (packs.isEmpty()) return Map.of();
        synchronized (StardewPreview.class) {
            if (spriteTextures == null) spriteTextures = buildSpriteTextures(packs);
            return spriteTextures;
        }
    }

    /**
     * 扫描当前生效资源包里的图集定义，建立「sprite 名 → 真实贴图 PNG」的反查表。
     *
     * <p><b>为什么需要它</b>（本服真机取证）：ItemsAdder 旧布局的作物模型只写
     * {@code "textures":{"layer0":"ia:75"}} —— {@code ia:75} 不是贴图路径，而是它自建图集里的
     * sprite 名，真实贴图写在图集定义的
     * {@code {"type":"single","resource":"customcrops:item/crops/corn/corn","sprite":"ia:75"}}
     * 条目里。图集定义本身放在 overlay 目录（本服为 {@code ia_overlay_modern_atlas}），由
     * {@code pack.mcmeta} 的 {@code overlays} 声明、原版合并后提供，因此按资源包列表读到的就是
     * 客户端真正生效的那一份，图集类型（{@code items} / {@code blocks}）也不用写死。</p>
     *
     * <p>顺序：物品图集先整表登记，其余图集再兜底；同一 sprite 名先到先占，于是生效资源包压过
     * 原版包（与 {@link #exists(Identifier)} 同一口径）。IA 的 sprite 序号全局唯一，不会跨图集撞名。</p>
     */
    private static Map<String, String> buildSpriteTextures(List<PackResources> packs) {
        Map<String, String> out = new HashMap<>();
        // packs() 是「低优先级在前」，倒序遍历即高优先级优先
        for (int i = packs.size() - 1; i >= 0; i--) collectAtlas(packs.get(i), ITEM_ATLAS, out);
        for (int i = packs.size() - 1; i >= 0; i--) collectAtlases(packs.get(i), out);
        return out;
    }

    /** 读一个具体图集定义文件并登记其中的 sprite；单个包读不到 / 读坏就跳过 */
    private static void collectAtlas(PackResources pack, Identifier atlasId, Map<String, String> out) {
        try {
            IoSupplier<InputStream> supplier = pack.getResource(PackType.CLIENT_RESOURCES, atlasId);
            if (supplier != null) registerSprites(readJson(supplier), out);
        } catch (Exception ignored) {
            // 单个包异常：继续读其余包
        }
    }

    /** 扫完某个包的全部图集定义（含 {@code pack.mcmeta} overlays 合并后的那一份） */
    private static void collectAtlases(PackResources pack, Map<String, String> out) {
        try {
            pack.listResources(PackType.CLIENT_RESOURCES, "minecraft", ATLAS_DIRECTORY, (id, supplier) -> {
                if (id == null || !id.getPath().endsWith(".json")) return;
                registerSprites(readJson(supplier), out);
            });
        } catch (Exception ignored) {
            // 单个包异常：继续读其余包
        }
    }

    /**
     * 登记一个图集定义里的 sprite 映射。
     *
     * <p>只认 {@code sources[].type = single}：它的 {@code sprite}（缺省等于 {@code resource}）
     * 就是模型里写的那个名字，{@code resource} 才是真实贴图。其它类型（{@code directory} /
     * {@code filter} / {@code paletted_permutations} / {@code unstitch}）一律跳过——宁可少认，不猜结构；
     * {@code directory} 那种「sprite 名 = 资源名」的写法，一的裸路径分支本来就能直接命中。</p>
     */
    private static void registerSprites(JsonObject atlas, Map<String, String> out) {
        if (atlas == null || !atlas.has("sources") || !atlas.get("sources").isJsonArray()) return;
        for (JsonElement element : atlas.getAsJsonArray("sources")) {
            if (!element.isJsonObject()) continue;
            JsonObject source = element.getAsJsonObject();
            String type = primitive(source, "type");
            if (type != null && !"single".equals(type) && !"minecraft:single".equals(type)) continue;
            String resource = primitive(source, "resource");
            if (resource == null) continue;
            String sprite = primitive(source, "sprite");
            String name = canonical(sprite == null ? resource : sprite);
            String png = pngPathOf(resource);
            if (name != null && png != null) out.putIfAbsent(name, png);
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  派发表兜底预览
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 用资源包自带的派发表构造预览栈（旧布局专用）。
     *
     * <p>ItemsAdder / Nexo 生成的包不给 {@code items/<id>.json}，而是让原版基础物品按
     * {@code custom_model_data} 阈值派发到自定义模型（真机取证：{@code minecraft:paper} + 阈值 10579
     * → {@code customcrops:item/crops/corn/corn_seeds}）。这里按同一组「基础物品 + 阈值」构造栈，
     * 渲染结果与服务端发给玩家手里的物品一致；取不到派发规格时返回空栈，由调用方显示可读缺失提示。</p>
     */
    private static ItemStack dispatchPreview(String modelKey) {
        ItemModelDispatchIndex.PreviewSpec spec = ItemModelDispatchIndex.get().previewOf(modelKey);
        if (spec == null) return ItemStack.EMPTY;
        Identifier itemId = Identifier.tryParse(spec.itemId() == null ? "" : spec.itemId());
        if (itemId == null) return ItemStack.EMPTY;
        Item base = BuiltInRegistries.ITEM.getValue(itemId);
        if (base == null || base == Items.AIR) return ItemStack.EMPTY;
        try {
            ItemStack stack = new ItemStack(base);
            if (spec.threshold() != null) {
                // 与服务端同口径：custom_model_data 第 0 位即派发用到的阈值
                stack.set(DataComponents.CUSTOM_MODEL_DATA,
                    new CustomModelData(List.of(spec.threshold().floatValue()), List.of(), List.of(), List.of()));
            }
            return stack;
        } catch (Exception e) {
            LOGGER.warn("[StardewPreview] 派发表预览构造失败：{}（{}）", modelKey, e.getMessage());
            return ItemStack.EMPTY;
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  资源读取
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** items/ 物品定义的位置：{@code <ns>:items/<path>.json} */
    private static Identifier itemDefLocation(Identifier itemModelId) {
        return Identifier.fromNamespaceAndPath(itemModelId.getNamespace(), "items/" + itemModelId.getPath() + ".json");
    }

    /** 当前生效资源包里是否存在该资源（高优先级优先判定） */
    private static boolean exists(Identifier resourceId) {
        List<PackResources> packs = packs();
        for (int i = packs.size() - 1; i >= 0; i--) {
            try {
                if (packs.get(i).getResource(PackType.CLIENT_RESOURCES, resourceId) != null) return true;
            } catch (Exception ignored) {
                // 单个包读取异常：继续找下一个包
            }
        }
        return false;
    }

    /** 读取 JSON 资源（高优先级优先，命中即返回）；不可读返回 null */
    private static JsonObject readJson(Identifier resourceId) {
        List<PackResources> packs = packs();
        for (int i = packs.size() - 1; i >= 0; i--) {
            try {
                IoSupplier<InputStream> supplier = packs.get(i).getResource(PackType.CLIENT_RESOURCES, resourceId);
                if (supplier == null) continue;
                try (InputStream in = supplier.get()) {
                    JsonElement root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                    return root.isJsonObject() ? root.getAsJsonObject() : null;
                }
            } catch (Exception ignored) {
                // 命中但读取/解析失败：继续找低优先级包
            }
        }
        return null;
    }

    /** 当前生效资源包列表（低优先级在前，高优先级在后） */
    private static List<PackResources> packs() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.getResourceManager() == null) return List.of();
        try {
            return mc.getResourceManager().listPacks().toList();
        } catch (Exception ignored) {
            return List.of();
        }
    }

    /** 贴图引用（{@code <ns>:<path>}）→ 贴图 PNG 资源路径；非法返回 {@code null} */
    private static String pngPathOf(String textureRef) {
        Identifier id = Identifier.tryParse(textureRef == null ? "" : textureRef);
        if (id == null) return null;
        return Identifier.fromNamespaceAndPath(id.getNamespace(), "textures/" + id.getPath() + ".png").toString();
    }

    /** 贴图引用 → 它在当前资源包里真实存在的 PNG 路径；不存在返回 {@code null} */
    private static String existingPng(String textureRef) {
        String png = pngPathOf(textureRef);
        if (png == null) return null;
        Identifier id = Identifier.tryParse(png);
        return id != null && exists(id) ? png : null;
    }

    /** 贴图引用的规范形式（{@code ns:path}）；非法返回 {@code null}（两侧都过这一道，避免写法差异漏配） */
    private static String canonical(String reference) {
        Identifier id = Identifier.tryParse(reference == null ? "" : reference);
        return id == null ? null : id.toString();
    }

    /** 读取一个已拿到的 JSON 资源；不可读 / 非对象返回 {@code null} */
    private static JsonObject readJson(IoSupplier<InputStream> supplier) {
        if (supplier == null) return null;
        try (InputStream in = supplier.get()) {
            JsonElement root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            return root != null && root.isJsonObject() ? root.getAsJsonObject() : null;
        } catch (Exception ignored) {
            return null;
        }
    }

    /** JSON 字符串字段（缺失 / 非字符串返回 {@code null}） */
    private static String primitive(JsonObject obj, String key) {
        if (obj == null || !obj.has(key)) return null;
        JsonElement element = obj.get(key);
        if (element == null || !element.isJsonPrimitive()) return null;
        String value = element.getAsString();
        return value == null || value.isBlank() ? null : value;
    }

    /** 读取 {@code items/<id>.json} 里 {@code model.model} 指向的模型引用 */
    private static String modelReference(JsonObject def) {
        if (def == null || !def.has("model") || !def.get("model").isJsonObject()) return null;
        JsonObject model = def.getAsJsonObject("model");
        if (!model.has("model") || !model.get("model").isJsonPrimitive()) return null;
        return model.get("model").getAsString();
    }

    /**
     * 读取模型（含 parent 链）里声明的贴图：层模型 {@code layer0} / 方块模型 {@code textures.0}。
     *
     * <p>链上都没有返回 {@code null}。{@code #layer0} 这类值是对本模型 {@code textures} 里变量的引用，
     * 不是贴图路径，跳过它继续往父模型找。</p>
     */
    private static String readModelTexture(String modelRef) {
        String ref = modelRef;
        for (int depth = 0; ref != null && depth < MAX_MODEL_DEPTH; depth++) {
            Identifier modelId = Identifier.tryParse(ref);
            if (modelId == null) return null;
            JsonObject model = readJson(modelsLocation(modelId));
            if (model == null) return null;
            String texture = declaredTexture(model);
            if (texture != null && !texture.startsWith("#")) return texture;
            ref = parentOf(model);
        }
        return null;
    }

    /** 模型自己声明的贴图（层模型 layer0 / 方块模型 textures.0） */
    private static String declaredTexture(JsonObject model) {
        if (model == null || !model.has("textures") || !model.get("textures").isJsonObject()) return null;
        JsonObject textures = model.getAsJsonObject("textures");
        for (String key : new String[]{"layer0", "0", "particle", "all"}) {
            if (textures.has(key) && textures.get(key).isJsonPrimitive()) {
                return textures.get(key).getAsString();
            }
        }
        return null;
    }

    /** 模型的 {@code parent} 引用；没有返回 {@code null} */
    private static String parentOf(JsonObject model) {
        if (model == null || !model.has("parent") || !model.get("parent").isJsonPrimitive()) return null;
        return model.get("parent").getAsString();
    }

    /** 模型文件的位置：{@code <ns>:models/<path>.json} */
    private static Identifier modelsLocation(Identifier modelId) {
        return Identifier.fromNamespaceAndPath(modelId.getNamespace(), "models/" + modelId.getPath() + ".json");
    }
}
