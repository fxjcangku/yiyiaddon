package com.yiyiaddon.feature.stardew.point;

import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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
     * 本点位在图示上代表自己的物品（原版物品 id）。
     *
     * <p>控制台点位卡的图标与世界字牌的图标同读这一份：种子箱＝小麦种子、成品箱＝小麦（收成的作物）、
     * 补水点＝水桶、岩浆箱＝岩浆桶、龙息箱＝龙息、洒水器＝滴水石锥。两处各写一套必然走形
     * （卡片画水桶、字牌画别的），故只此一份。</p>
     *
     * <p><b>洒水器为什么不用药水类</b>（真机取证）：{@code item/splash_potion} 模型的
     * {@code layer0} 是 {@code minecraft:item/potion_overlay}——一张只给瓶身染色用的白色叠加图，
     * 单独画出来是一团没有颜色的糊块；世界字牌那条路径只取 {@code layer0}、不做染色。滴水石锥是
     * 一张正常全彩图，且与「补水点＝水桶」一眼分得开。</p>
     *
     * <p>字牌那条路径要的是贴图路径，由 {@code StardewPreview#textureOf} 从本 id 解析
     * （物品 id → items 定义 → 模型 → layer0），解析不出来字牌退化成纯文字。</p>
     */
    public String iconItemId() {
        return switch (this) {
            case SEED_BOX -> "minecraft:wheat_seeds";
            case OUTPUT_BOX -> "minecraft:wheat";
            case WATER_SOURCE -> "minecraft:water_bucket";
            case LAVA_BOX -> "minecraft:lava_bucket";
            case BREATH_BOX -> "minecraft:dragon_breath";
            case SPRINKLER -> "minecraft:pointed_dripstone";
        };
    }

    /**
     * 本点位的图标物品栈（控制台点位卡、列表行都读它）。
     *
     * <p>取不到（id 写错 / 该版本没有这个物品）返回空栈，界面据此不画图标 ——
     * 不留空洞、也不画黑紫缺失模型。</p>
     */
    public ItemStack icon() {
        Identifier id = Identifier.tryParse(iconItemId());
        Item item = id == null ? null : BuiltInRegistries.ITEM.getValue(id);
        return item == null || item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
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
