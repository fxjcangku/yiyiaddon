package com.yiyiaddon.feature.stardew.point;

/**
 * 星露谷农场点位业务类型。
 *
 * <p>与自动附魔的 {@code PointType} 同构：GUI 按钮、.stardew 指令、启动自检、
 * 状态机共享同一份点位数据源，禁止出现「箱子坐标」这类模糊类型。</p>
 */
public enum StardewPointType {

    /** 农田起点（对角之一） */
    START("农田起点", "start", false),
    /** 农田终点（对角之二） */
    END("农田终点", "end", false),
    /** 种子箱 */
    SEED_BOX("种子箱", "seedbox", true),
    /** 成品箱 */
    OUTPUT_BOX("成品箱", "output", true),
    /** 补水点 */
    WATER_SOURCE("补水点", "water", false),
    /** 洒水器（支持多个点位） */
    SPRINKLER("洒水器", "sprinkler", false);

    private final String title;
    private final String node;
    private final boolean requiresContainer;

    StardewPointType(String title, String node, boolean requiresContainer) {
        this.title = title;
        this.node = node;
        this.requiresContainer = requiresContainer;
    }

    /** 中文显示名 */
    public String title() {
        return title;
    }

    /** .stardew 指令节点字面量 */
    public String node() {
        return node;
    }

    /** 绑定时是否必须命中容器方块 */
    public boolean requiresContainer() {
        return requiresContainer;
    }

    @Override
    public String toString() {
        return title;
    }

    /** 按节点字面量解析，未匹配返回 null */
    public static StardewPointType fromNode(String node) {
        for (StardewPointType type : values()) {
            if (type.node.equals(node)) return type;
        }
        return null;
    }
}
