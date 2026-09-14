package com.yiyiaddon.feature.stardew.selector;

import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.model.identity.ItemIdentity;

import java.util.Locale;

/**
 * 星露谷六类物品选择器类别。
 *
 * <p>分类严格限定在 {@code customcrops} 命名空间内（星露谷 = CustomCrops 盆栽系统），并按
 * 语义家族前缀归类。绝对禁止 {@code contains("magic") / contains("potion") / contains("seed")}
 * 这类跨命名空间、跨语义的宽泛字符串匹配——它会把 {@code customfurniture:item/magic_store...}
 * 家具误判成魔法药剂，也会把农夫乐事 {@code default:item/watering_can} 误判成星露谷水壶。</p>
 *
 * <p>肥料（{@code quality_/soil_retain_/speed_grow_/yield_increase_}）与魔法药剂
 * （{@code variation_}）语义分离，不再把 variation 归入肥料。</p>
 */
public enum StardewSelectorCategory {

    /** 作物种子（按种子选择种植目标） */
    CROP("作物"),
    /** 种植盆 */
    POT("种植盆"),
    /** 肥料 */
    FERTILIZER("肥料"),
    /** 魔法药剂 */
    POTION("魔法药剂"),
    /** 水壶 */
    WATERING_CAN("水壶"),
    /** 洒水器 */
    SPRINKLER("洒水器");

    private final String title;

    StardewSelectorCategory(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    /**
     * 把物品身份归类到六类之一；无法归类或非 {@code customcrops} 命名空间返回 null。
     */
    public static StardewSelectorCategory classify(ItemIdentity id) {
        if (id == null) return null;
        String model = id.itemModel() != null && !id.itemModel().isBlank()
            ? id.itemModel()
            : id.customLogicId();
        String namespace = StardewResourceIndex.namespaceOf(model);
        String name = StardewResourceIndex.normalizeName(id);
        return classify(namespace, name);
    }

    /**
     * 按命名空间 + 已剥离 {@code item/}/{@code block/} 前缀的逻辑名归类（资源包扫描与 ID 识别共用）。
     *
     * <p>逻辑名可能带子目录（如 {@code wateringcan/watering_can_1}、{@code sprinkler/sprinkler_1}、
     * {@code misc/dry_pot_1}），因此同时检查完整逻辑名与末段名。只有 {@code customcrops} 命名空间
     * 参与分类，其余命名空间（{@code default} 农夫乐事、{@code customfurniture} 家具等）一律返回 null。</p>
     */
    public static StardewSelectorCategory classify(String namespace, String name) {
        if (namespace == null || name == null || name.isBlank()) return null;
        if (!"customcrops".equals(namespace)) return null;

        String lower = name.toLowerCase(Locale.ROOT);
        String last = lower;
        int slash = lower.lastIndexOf('/');
        if (slash >= 0) last = lower.substring(slash + 1);

        if (lower.endsWith("_seeds") || last.endsWith("_seeds")) return CROP;
        if (last.startsWith("dry_pot") || last.startsWith("wet_pot")) return POT;
        if (last.startsWith("sprinkler_")) return SPRINKLER;
        if (last.startsWith("watering_can")) return WATERING_CAN;
        if (last.startsWith("quality_") || last.startsWith("soil_retain_")
            || last.startsWith("speed_grow_") || last.startsWith("yield_increase_")) return FERTILIZER;
        if (last.startsWith("variation_")) return POTION;
        return null;
    }
}
