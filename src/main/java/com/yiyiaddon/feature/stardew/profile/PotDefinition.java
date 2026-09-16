package com.yiyiaddon.feature.stardew.profile;

import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;

/**
 * 种植盆逻辑定义。
 *
 * <p>把同一盆型的 item 身份 + dry 世界模型 + wet 世界模型聚合为一个用户选择项。例如
 * {@code dry_pot_1} / {@code wet_pot_1} 同属「普通种植盆」一个逻辑对象，选择器只展示一个
 * 盆型，Scanner 同时认识它的 DRY 与 WET 世界状态，自动浇水只管理该逻辑盆型。</p>
 *
 * <ul>
 *   <li>{@code potIndex}    盆型序号（1=普通 / 2=下界 / 3=末地，按资源实际索引）；</li>
 *   <li>{@code dryModel}    干燥世界模型（如 {@code customcrops:block/misc/dry_pot_1}）；</li>
 *   <li>{@code wetModel}    湿润世界模型（如 {@code customcrops:block/misc/wet_pot_1}）；</li>
 *   <li>{@code itemModel}   用于预览 / 匹配的物品模型（可空）。</li>
 * </ul>
 */
public record PotDefinition(
    String key,
    String displayName,
    String itemModel,
    String identityKey,
    RuleEvidence evidence,
    String source,
    int potIndex,
    String dryModel,
    String wetModel
) implements StardewToolDefinition {

    @Override
    public StardewSelectorCategory category() {
        return StardewSelectorCategory.POT;
    }

    /**
     * 世界识别：给定 {@code potKey}（如 {@code dry_pot_1} / {@code wet_pot_1}）是否命中本逻辑盆型。
     *
     * <p>盆键来自 {@code CropRecognizer} 语义身份末段，与本盆型的 dry / wet 世界模型末段对齐。</p>
     */
    public boolean matchesPotKey(String potKey) {
        if (potKey == null) return false;
        String lower = potKey.toLowerCase(java.util.Locale.ROOT);
        if (modelEndsWith(dryModel, lower) || modelEndsWith(wetModel, lower)) return true;
        // 兜底判等：同一盆族 + 同一盆型组。
        //
        // 为什么必须加：世界身份与索引条目可能只差「变体序号」，甚至索引条目里根本没有世界模型可比。
        // 真机事故（moexd）：资源包把盆的世界模型放在 models/item/ 下、方块 blockstates 直接指向它，
        // 世界身份因此是 dry_pot（不带序号），而索引里那口盆的条目没有任何 block 模型，
        // 于是「地里 普通种植盆 ×8，已选 普通种植盆」左右同名却判定不匹配、直接禁止启动。
        //
        // 口径说明：干湿是同一口盆的两种状态（浇水后世界身份由 dry_pot 变 wet_pot），因此盆族只看
        // dry_pot / wet_pot（与识别层 potKeyOf 同一判据）；盆型组（普通 / 下界 / 末地）必须一致，
        // 这样「选普通盆」不会被下界盆 / 末地盆蒙混命中。
        if (!isPotFamily(lower)) return false;
        return PotGroup.ofPotKey(lower) == PotGroup.ofIndex(potIndex);
    }

    /** 是否属于 customcrops 盆族键（与识别层 potKeyOf 同一判据） */
    private static boolean isPotFamily(String lower) {
        return lower.contains("dry_pot") || lower.contains("wet_pot");
    }

    /** 判断模型路径末段是否等于给定键（含 {@code .../dry_pot_1} 与 {@code dry_pot_1} 两种形态） */
    private static boolean modelEndsWith(String model, String lower) {
        if (model == null) return false;
        String m = model.toLowerCase(java.util.Locale.ROOT);
        return m.endsWith("/" + lower) || m.equals(lower);
    }
}
