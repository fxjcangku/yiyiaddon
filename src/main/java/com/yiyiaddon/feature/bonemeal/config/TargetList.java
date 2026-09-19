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
 */
public enum TargetList {

    /** 农作物（旧 {@code targetCrops}） */
    农作物(BonemealTexts.NAME_TARGET_CROPS, BonemealTexts.DESC_TARGET_CROPS),

    /** 树苗（旧 {@code targetSaplings}） */
    树苗(BonemealTexts.NAME_TARGET_SAPLINGS, BonemealTexts.DESC_TARGET_SAPLINGS),

    /** 花卉（旧 {@code targetFlowers}） */
    花卉(BonemealTexts.NAME_TARGET_FLOWERS, BonemealTexts.DESC_TARGET_FLOWERS),

    /** 蘑菇 / 菌类（旧 {@code targetMushrooms}） */
    蘑菇菌类(BonemealTexts.NAME_TARGET_MUSHROOMS, BonemealTexts.DESC_TARGET_MUSHROOMS),

    /** 水下 / 下界（旧 {@code targetAquaticNether}） */
    水下下界(BonemealTexts.NAME_TARGET_AQUATIC_NETHER, BonemealTexts.DESC_TARGET_AQUATIC_NETHER);

    /** 旧设置名（界面标签 + 选择器标题 + 落盘键，三者同源） */
    private final String label;
    /** 旧设置描述（控制台行的悬停提示；旧框架同样只在控件提示里给这段说明） */
    private final String description;

    TargetList(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String label() {
        return label;
    }

    public String description() {
        return description;
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
