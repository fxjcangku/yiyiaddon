package com.yiyiaddon.feature.stardew.point;

import com.yiyiaddon.feature.stardew.recognition.PotGroup;

/**
 * 星露谷农场点位业务类型。
 *
 * <p>与自动附魔的 {@code PointType} 同构：GUI 按钮、.stardew 指令、启动自检、
 * 状态机共享同一份点位数据源，禁止出现「箱子坐标」这类模糊类型。</p>
 */
public enum StardewPointType {

    /** 种子箱 */
    SEED_BOX("种子箱", "seedbox", true),
    /** 成品箱 */
    OUTPUT_BOX("成品箱", "output", true),
    /** 补水点 */
    WATER_SOURCE("补水点", "water", false),
    /** 洒水器（支持多个点位） */
    SPRINKLER("洒水器", "sprinkler", false),
    /** 岩浆箱：下界种植盆从这里取熔岩桶、用完的空桶放回这里 */
    LAVA_BOX("岩浆箱", "lavabox", true),
    /** 龙息箱：末地种植盆从这里取龙息、用完的玻璃瓶放回这里 */
    BREATH_BOX("龙息箱", "breathbox", true);

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

    /**
     * 某种盆型该用的物料箱。
     *
     * <p>普通盆返回 {@code null}——它不走箱子，走水壶 + 补水点。这是「三套物料互斥」的
     * 唯一判定入口，绑定校验与启动自检都读它，不在别处再写一遍 if。</p>
     */
    public static StardewPointType materialBoxFor(PotGroup group) {
        if (group == PotGroup.NETHER) return LAVA_BOX;
        if (group == PotGroup.END) return BREATH_BOX;
        return null;
    }

    /**
     * 本点位与某种盆型是否冲突。
     *
     * <p><b>为什么要在绑定这一刻拦：</b>脚本按盆型自己挑物料（盆是下界盆就用岩浆），
     * 所以绑错点位不会让脚本做错事；但玩家会以为「配好了」，而那条链路其实永远用不上，
     * 等到缺料停机才发现——实机反馈里这类「配了但没生效」最难排查。宁可绑定时就拒绝。</p>
     *
     * <p>种子箱 / 成品箱对三种盆都成立：都要收发种子与产物。</p>
     *
     * @return 冲突原因（中文，可直接播报）；兼容时返回 {@code null}
     */
    public String conflictWith(PotGroup group) {
        if (group == null) return null;
        StardewPointType required = materialBoxFor(group);
        return switch (this) {
            case SEED_BOX, OUTPUT_BOX -> null;
            case WATER_SOURCE -> group == PotGroup.NORMAL ? null
                : group.displayName() + "用" + group.materialName() + "补水，不需要补水点";
            case SPRINKLER -> group == PotGroup.NORMAL ? null
                : "洒水器只对普通种植盆生效，" + group.displayName() + "不适用";
            case LAVA_BOX, BREATH_BOX -> {
                if (this == required) yield null;
                if (required == null) {
                    yield title + "只给下界 / 末地种植盆用，普通种植盆走水壶 + 补水点";
                }
                String owner = this == LAVA_BOX ? "下界种植盆" : "末地种植盆";
                yield title + "配" + owner + "，" + group.displayName() + "要用" + required.title();
            }
        };
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
