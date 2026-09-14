package com.yiyiaddon.feature.stardew.selector;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    /** 读取 {@code items/<id>.json} 里 {@code model.model} 指向的模型引用 */
    private static String modelReference(JsonObject def) {
        if (def == null || !def.has("model") || !def.get("model").isJsonObject()) return null;
        JsonObject model = def.getAsJsonObject("model");
        if (!model.has("model") || !model.get("model").isJsonPrimitive()) return null;
        return model.get("model").getAsString();
    }

    /** 读取模型文件里声明的贴图（层模型 layer0 / 方块模型 textures.0） */
    private static String readModelTexture(String modelRef) {
        Identifier modelId = Identifier.tryParse(modelRef);
        if (modelId == null) return null;
        Identifier modelLocation = Identifier.fromNamespaceAndPath(modelId.getNamespace(),
            "models/" + modelId.getPath() + ".json");
        JsonObject model = readJson(modelLocation);
        if (model == null || !model.has("textures") || !model.get("textures").isJsonObject()) return null;
        JsonObject textures = model.getAsJsonObject("textures");
        for (String key : new String[]{"layer0", "0", "particle", "all"}) {
            if (textures.has(key) && textures.get(key).isJsonPrimitive()) {
                return textures.get(key).getAsString();
            }
        }
        return null;
    }
}
