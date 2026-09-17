package com.yiyiaddon.feature.villager.model;

/**
 * 目标职业下拉选择项：13 个村民职业。
 *
 * <p>逐字迁移自旧项目 {@code AutoVillagerTradeModule.ProfessionChoice}（旧 {@code :71-95}）：
 * 枚举常量名就是界面与播报里显示的中文职业名（旧项目刻意如此，启动报告直接取
 * {@code profession.get().name()} 输出），因此常量名不可改为英文；{@code id} 为注册表 ID。</p>
 *
 * <p>旧项目把它内嵌在模块类里，这里独立成文件（与 {@link VillagerTradeMode} 同理）：
 * 设置载体、模块入口、控制台页与选择器都要引用它，内嵌会让这些类互相纠缠。
 * 类名由 {@code ProfessionChoice} 具体化为 {@code VillagerProfessionChoice}，取值与 {@code getId()} 未改。</p>
 */
public enum VillagerProfessionChoice {

    盔甲匠("minecraft:armorer"),
    屠夫("minecraft:butcher"),
    制图师("minecraft:cartographer"),
    牧师("minecraft:cleric"),
    农民("minecraft:farmer"),
    渔夫("minecraft:fisherman"),
    制箭师("minecraft:fletcher"),
    皮匠("minecraft:leatherworker"),
    图书管理员("minecraft:librarian"),
    石匠("minecraft:mason"),
    牧羊人("minecraft:shepherd"),
    工具匠("minecraft:toolsmith"),
    武器匠("minecraft:weaponsmith");

    private final String id;

    VillagerProfessionChoice(String id) {
        this.id = id;
    }

    /** 村民职业注册表 ID（如 {@code minecraft:armorer}） */
    public String getId() {
        return id;
    }
}
