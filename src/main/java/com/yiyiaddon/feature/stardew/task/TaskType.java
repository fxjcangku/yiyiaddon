package com.yiyiaddon.feature.stardew.task;

/**
 * 星露谷任务类型。
 *
 * <p>固定枚举只定义「动作 / 状态 / 任务类别」，具体物品与玩法规则由服务器 Profile 管理，
 * 绝不为了加第 19 种作物改枚举。调度优先级在 Coordinator 决策层实现。</p>
 */
public enum TaskType {

    /** 收割成熟 / 特殊变种作物 */
    HARVEST("收割"),
    /** 对未知服务器阶段执行一次低风险空手右键探测 */
    LEARN_HARVEST("学习收割"),
    /** 清除死亡作物 */
    CLEAR_DEAD("清理死亡"),
    /** 分区模式下清除种错区域的作物（左键破坏，永远单目标） */
    CLEAR_MISMATCH("清理错位"),
    /** 给干燥的盆浇水 */
    WATER("浇水"),
    /** 播种 */
    PLANT("种植"),
    /** 独立拾取当前农场掉落 */
    COLLECT("拾取"),
    /** 去补水点给水壶补水 */
    REFILL("补水"),
    /** 给空盆施肥 */
    FERTILIZE("施肥"),
    /** 使用魔法药剂 */
    POTION("用药剂"),
    /** 检查已绑定洒水器 */
    SPRINKLER_CHECK("检查洒水器"),
    /** 给洒水器补水 */
    SPRINKLER_REFILL("洒水器补水"),
    /** 去种子箱补货种子 */
    RESTOCK("补货"),
    /** 把超出保留量的种子存回种子箱（最低优先级，静默执行） */
    SEED_RETURN("回收种子"),
    /** 去成品箱卸货产物 */
    UNLOAD("卸货"),
    /** 回农田中心点等待资源（仅无任何可执行任务时） */
    RETURN_CENTER("回中心");

    private final String cn;

    TaskType(String cn) {
        this.cn = cn;
    }

    /** 中文动作名，用于状态播报 */
    public String cn() {
        return cn;
    }
}
