package com.yiyiaddon.feature.autofarm.model;

/**
 * 补种模式：决定空耕地（可补种底盘）应该补种哪种启用作物。
 *
 * 三种模式互斥，通过配置单选：
 * - 顺序优先：按作物枚举声明顺序种满一种再种下一种（默认，与旧行为一致）。
 * - 均匀轮转：启用作物轮流补种，保证各作物种植数量长期均衡。
 * - 就近跟随：空耕地种回周围已有作物的同类，保持手动规划的混种分区。
 *
 * 底盘隔离说明：下界疣的底盘是灵魂沙、普通作物的底盘是耕地，
 * 由 {@link CropProfile#isPlantable} 天然隔离，模式切换不会跨底盘误种。
 * 根茎/果实作物（竹子/甘蔗/仙人掌/南瓜/西瓜）needsReplant=false，不参与补种。
 */
public enum PlantMode {

    /** 顺序优先：按枚举声明顺序种满一种再种下一种 */
    SEQUENTIAL("顺序优先"),

    /** 均匀轮转：启用作物轮流补种，保证数量均衡 */
    ROTATE("均匀轮转"),

    /** 就近跟随：空耕地种回周围已有作物的同类 */
    FOLLOW_NEARBY("就近跟随");

    private final String cn;

    PlantMode(String cn) {
        this.cn = cn;
    }

    @Override
    public String toString() {
        return cn;
    }
}
