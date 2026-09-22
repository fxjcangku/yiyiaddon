package com.yiyiaddon.feature.bonemeal.config;

import java.util.List;

/**
 * 五组目标方块名单的登记表（旧 {@code sgTargets} 的 5 个 {@code BlockListSetting}）。
 *
 * <p><b>为什么收成一张表</b>：这 5 组的「名称 / 描述 / 取哪一份名单 / 存哪个键 / 开哪个选择器」
 * 五件事完全同构，旧项目是 5 段 15 行重复声明。收成枚举后，设置页的行、选择器的目标、
 * 启动报告的合计、自检的判据都只走这一份，改一处即五处同步（第 169 条：同源逻辑不留两份）。</p>
 *
 * <p><b>顺序即旧声明顺序</b>（农作物 → 树苗 → 花卉 → 蘑菇 / 菌类 → 水下 / 下界），
 * 用户看到的行序与旧项目一致；{@code label()} 逐字等于旧设置名，同时就是落盘键名。</p>
 *
 * <p><b>成员名单的用途（2026-09-22 起）</b>：{@link #members()} 是「哪些方块属于这一类」的唯一定义
 * —— 旧项目那份默认名单原样搬过来。它<b>不再是出厂勾选</b>（用户 2026-09-22：「默认自己选择
 * 不要帮我全选」），只用来定选择器的候选范围：打开「农作物」的选择器就只列这几项，
 * 勾不勾由玩家自己决定（出厂五个名单全空）。</p>
 */
public enum TargetList {

    /** 农作物（旧 {@code targetCrops}） */
    农作物(BonemealTexts.NAME_TARGET_CROPS, BonemealTexts.DESC_TARGET_CROPS, List.of(
        "minecraft:wheat", "minecraft:carrots", "minecraft:potatoes", "minecraft:beetroots",
        "minecraft:torchflower_crop", "minecraft:pitcher_crop",
        "minecraft:melon_stem", "minecraft:pumpkin_stem",
        "minecraft:cocoa", "minecraft:sweet_berry_bush", "minecraft:cave_vines"
    )),

    /** 树苗（旧 {@code targetSaplings}） */
    树苗(BonemealTexts.NAME_TARGET_SAPLINGS, BonemealTexts.DESC_TARGET_SAPLINGS, List.of(
        "minecraft:oak_sapling", "minecraft:spruce_sapling", "minecraft:birch_sapling",
        "minecraft:jungle_sapling", "minecraft:acacia_sapling", "minecraft:dark_oak_sapling",
        "minecraft:cherry_sapling", "minecraft:mangrove_propagule", "minecraft:pale_oak_sapling"
    )),

    /** 花卉（旧 {@code targetFlowers}） */
    花卉(BonemealTexts.NAME_TARGET_FLOWERS, BonemealTexts.DESC_TARGET_FLOWERS, List.of(
        "minecraft:sunflower", "minecraft:lilac", "minecraft:rose_bush", "minecraft:peony",
        "minecraft:pink_petals", "minecraft:wildflowers",
        "minecraft:flowering_azalea", "minecraft:azalea"
    )),

    /** 蘑菇 / 菌类（旧 {@code targetMushrooms}） */
    蘑菇菌类(BonemealTexts.NAME_TARGET_MUSHROOMS, BonemealTexts.DESC_TARGET_MUSHROOMS, List.of(
        "minecraft:brown_mushroom", "minecraft:red_mushroom",
        "minecraft:crimson_fungus", "minecraft:warped_fungus"
    )),

    /** 水下 / 下界（旧 {@code targetAquaticNether}） */
    水下下界(BonemealTexts.NAME_TARGET_AQUATIC_NETHER, BonemealTexts.DESC_TARGET_AQUATIC_NETHER, List.of(
        "minecraft:kelp",
        "minecraft:twisting_vines", "minecraft:weeping_vines",
        "minecraft:moss_block", "minecraft:glow_lichen", "minecraft:small_dripleaf"
    ));

    /** 旧设置名（界面标签 + 选择器标题 + 落盘键，三者同源） */
    private final String label;
    /** 旧设置描述（控制台行的悬停提示；旧框架同样只在控件提示里给这段说明） */
    private final String description;
    /** 该类的成员方块登记 ID（旧默认名单；现只作选择器候选范围，不再当出厂勾选） */
    private final List<String> members;

    TargetList(String label, String description, List<String> members) {
        this.label = label;
        this.description = description;
        this.members = members;
    }

    public String label() {
        return label;
    }

    public String description() {
        return description;
    }

    /** 该类的成员方块登记 ID（不可变；选择器候选范围 = 它 ∪ 当前已选） */
    public List<String> members() {
        return members;
    }

    /** 该组名单在当前设置里的实例；选择器与判定层直接读写它，不另存副本 */
    public List<String> of(BonemealSettings settings) {
        return switch (this) {
            case 农作物 -> settings.targetCrops;
            case 树苗 -> settings.targetSaplings;
            case 花卉 -> settings.targetFlowers;
            case 蘑菇菌类 -> settings.targetMushrooms;
            case 水下下界 -> settings.targetAquaticNether;
        };
    }
}
