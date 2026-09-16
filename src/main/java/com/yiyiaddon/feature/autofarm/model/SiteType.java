package com.yiyiaddon.feature.autofarm.model;

/**
 * 自动农场需要绑定的六个锚点类型。
 *
 * 点位一与点位二是农田范围的两个对角，只要坐标；
 * 单作物箱/种子补货箱/多作物箱与毒马铃薯箱必须指向真正的容器方块，绑定时做 Container 校验。
 *
 * 箱子按「物品去向」智能分类：
 * 单作物箱装单物品作物（种子==收获物，如马铃薯/胡萝卜/下界疣，以及甘蔗/竹子/仙人掌这类无种子作物），补货与卸货共用；
 * 种子补货箱只放双物品作物（小麦/甜菜根）的种子，多作物箱装双物品作物的成熟掉落物；
 * 杂物箱独立处理马铃薯附带的毒马铃薯与仙人掌自然长出的仙人掌花，绝不与普通作物箱混用。
 */
public enum SiteType {

    START("农场点位1", false),
    END("农场点位2", false),
    SINGLE_STORAGE("单作物箱", true),
    MULTI_STORAGE("多作物箱", true),
    SEED_STORAGE("种子补货箱", true),
    POISON_STORAGE("杂物箱", true);

    private final String cn;
    private final boolean requiresContainer;

    SiteType(String cn, boolean requiresContainer) {
        this.cn = cn;
        this.requiresContainer = requiresContainer;
    }

    /** 中文子命令字面量，也用于聊天提示 */
    public String cn() {
        return cn;
    }

    /** 绑定时是否必须命中容器方块 */
    public boolean requiresContainer() {
        return requiresContainer;
    }
}
