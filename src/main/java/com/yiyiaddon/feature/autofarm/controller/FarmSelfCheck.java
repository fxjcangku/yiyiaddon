package com.yiyiaddon.feature.autofarm.controller;

import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.feature.autofarm.model.FarmSite;
import com.yiyiaddon.feature.autofarm.model.SiteType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 启动前配置自检。
 *
 * 根据启用作物类型智能推断所需箱子（单物品作物与柱状物/果实→单作物箱、
 * 双作物判定（需要补种且种子≠收获物）→种子补货箱（种子）+ 多作物箱（成熟掉落物）同判定、
 * 毒马铃薯箱独立），并一次性收集全部缺项，
 * 避免「配好一项下次还缺一项」的挤牙膏式报错。
 * 自检失败时不启动 Controller、不启动 Baritone、不扫描、不操作箱子。
 */
public final class FarmSelfCheck {

    /** 允许同时启用的作物数量上限 */
    public static final int MAX_CROPS = 3;

    /**
     * 执行自检。
     *
     * @param enabledCrops 启用作物集合
     * @param sites        六个点位（SiteType → FarmSite，未绑定为 null）
     * @return 缺失项清单，为空表示自检通过
     */
    public List<String> check(Set<CropProfile> enabledCrops, Map<SiteType, FarmSite> sites) {
        List<String> missing = new ArrayList<>();

        // 作物数量检测
        if (enabledCrops.isEmpty()) {
            missing.add("§e作物§f·未启用任何农作物");
            return missing;
        }
        if (enabledCrops.size() > MAX_CROPS) {
            missing.add("§c作物§f·当前版本最多支持 " + MAX_CROPS + " 种农作物（当前 " + enabledCrops.size() + " 种）");
            return missing;
        }

        // 农场范围点位（必选）
        requireSite(missing, sites, SiteType.START, "§a农场点位1");
        requireSite(missing, sites, SiteType.END, "§e农场点位2");

        // 智能箱子需求：单物品作物（种子==收获物）与不补种作物（柱状物/果实）→单作物箱；
        // 双作物判定（需要补种且种子≠收获物）→种子补货箱（种子）+ 多作物箱（成熟掉落物）
        boolean needsSingle = enabledCrops.stream().anyMatch(p -> !p.needsReplant() || p.plantItem() == p.harvestItem());
        boolean needsDual = enabledCrops.stream().anyMatch(p -> p.needsReplant() && p.plantItem() != p.harvestItem());
        // 种子补货箱与多作物箱完全同判定：只有双物品作物才需要这两个箱子
        boolean needsSeed = needsDual;
        boolean needsMulti = needsDual;

        if (needsSingle) {
            requireSite(missing, sites, SiteType.SINGLE_STORAGE, "§6单作物箱");
        }
        if (needsSeed) {
            requireSite(missing, sites, SiteType.SEED_STORAGE, "§b种子补货箱");
        }
        if (needsMulti) {
            requireSite(missing, sites, SiteType.MULTI_STORAGE, "§d多作物箱");
        }

        // 杂物箱仅在会产生杂物（毒马铃薯/仙人掌花）时才需要：马铃薯附带毒马铃薯，仙人掌长出仙人掌花
        boolean needsJunkBox = enabledCrops.stream().anyMatch(p -> !p.extraLoot().isEmpty() || p.junk());
        if (needsJunkBox) {
            requireSite(missing, sites, SiteType.POISON_STORAGE, "§c杂物箱");
        }

        // 维度一致性检测
        checkDimension(missing, sites, SiteType.START, "§a农场点位1");
        checkDimension(missing, sites, SiteType.END, "§e农场点位2");
        if (needsSingle) checkDimension(missing, sites, SiteType.SINGLE_STORAGE, "§6单作物箱");
        if (needsSeed) checkDimension(missing, sites, SiteType.SEED_STORAGE, "§b种子补货箱");
        if (needsMulti) checkDimension(missing, sites, SiteType.MULTI_STORAGE, "§d多作物箱");
        if (needsJunkBox) checkDimension(missing, sites, SiteType.POISON_STORAGE, "§c杂物箱");

        // 农场范围有效性：两个对角不能重合
        FarmSite start = sites.get(SiteType.START);
        FarmSite end = sites.get(SiteType.END);
        if (start != null && end != null
            && start.pos().equals(end.pos())) {
            missing.add("§c农田范围§f·农场点位1与农场点位2重合了");
        }

        return missing;
    }

    private void requireSite(List<String> missing, Map<SiteType, FarmSite> sites, SiteType type, String label) {
        if (sites.get(type) == null) missing.add(label + "§f·未绑定");
    }

    private void checkDimension(List<String> missing, Map<SiteType, FarmSite> sites, SiteType type, String label) {
        FarmSite site = sites.get(type);
        if (site != null && !site.inCurrentDimension()) {
            missing.add(label + "§f·不在当前维度");
        }
    }
}
