package com.yiyiaddon.feature.enchant.model;

import java.util.List;

/**
 * 业务点位类型，共 9 类。
 *
 * <p>逐字来自旧项目 {@code point/PointType.java:15-31}，一个不多一个不少
 * （旧项目<b>没有</b>异常装备箱点位）。三个字符串字段各自用途不同，禁止互相替代：</p>
 * <ul>
 *   <li>{@link #title()} —— 显示名（播报、自检、状态页、卡片标题）。注意 {@code 工具/护甲箱} 带斜杠、
 *       {@code 挂机点} 与指令节点不同名。</li>
 *   <li>{@link #node()} —— {@code .fumo 设置/移除} 的字面量。{@code 工具护甲箱} 无斜杠、
 *       {@code 青晶石} 不是「青金石」、{@code 挂机位} 不是「挂机点」。</li>
 *   <li>{@link #posKey()} —— 旧项目 {@code toTag/fromTag}（{@code :1621-1659}）的落盘键，逐字，
 *       例：挂机点存 {@code posHangout} 而非 {@code posAfk}。</li>
 * </ul>
 *
 * <p>{@link #espLabel()} / {@link #espColor()} 取自旧项目 {@code onRender2D}（{@code :1594-1603}）：
 * <b>GEAR 专属的三个点位（工具护甲箱 / 铁砧 / 铁砧箱）旧项目不画 ESP</b>，此处保持
 * {@code espLabel == null}，不得自行补标签。</p>
 */
public enum EnchantPointType {

    BOOK_STORAGE("空白书箱", "书", "posBook", "书本箱", 0xC850E6A0, "§a"),
    LAPIS_STORAGE("青金石箱", "青晶石", "posLapis", "青金石箱", 0xC84682FF, "§9"),
    EQUIPMENT_STORAGE("工具/护甲箱", "工具护甲箱", "posEquipment", null, 0, "§b"),
    ENCHANTING_TABLE("附魔台", "附魔台", "posEnchant", "附魔台", 0xC8C864FF, "§d"),
    GRINDSTONE("砂轮", "砂轮", "posGrindstone", "砂轮", 0xC8A0A0A0, "§7"),
    ANVIL("铁砧", "铁砧", "posAnvil", null, 0, "§6"),
    ANVIL_BOX("铁砧箱", "铁砧箱", "posAnvilBox", null, 0, "§e"),
    AFK("挂机点", "挂机位", "posHangout", "挂机位", 0xC8FF5050, "§c"),
    OUTPUT_STORAGE("成品箱", "成品箱", "posOutput", "成品箱", 0xC8FFC832, "§6");

    /** GEAR 模式必填点位（旧项目 {@code requiredPoints} 的 GEAR 分支，{@code :761-764}） */
    private static final List<EnchantPointType> GEAR_POINTS = List.of(
        EQUIPMENT_STORAGE, LAPIS_STORAGE, ENCHANTING_TABLE, GRINDSTONE,
        ANVIL, ANVIL_BOX, AFK, OUTPUT_STORAGE);

    /** BOOK / CUSTOM 模式必填点位（旧项目 {@code requiredPoints} 的另一分支，{@code :765-768}） */
    private static final List<EnchantPointType> BOOK_POINTS = List.of(
        BOOK_STORAGE, LAPIS_STORAGE, OUTPUT_STORAGE, ENCHANTING_TABLE, GRINDSTONE, AFK);

    private final String title;
    private final String node;
    private final String posKey;
    private final String espLabel;
    private final int espColor;
    private final String titleColor;

    EnchantPointType(String title, String node, String posKey, String espLabel, int espColor, String titleColor) {
        this.title = title;
        this.node = node;
        this.posKey = posKey;
        this.espLabel = espLabel;
        this.espColor = espColor;
        this.titleColor = titleColor;
    }

    /** 中文显示名（逐字，含斜杠等原样写法） */
    public String title() {
        return title;
    }

    /** {@code .fumo} 指令节点字面量（逐字） */
    public String node() {
        return node;
    }

    /** 旧项目落盘键（逐字） */
    public String posKey() {
        return posKey;
    }

    /** ESP 标签；{@code null} = 旧项目本来就不渲染该点位 */
    public String espLabel() {
        return espLabel;
    }

    /** ESP 颜色（ARGB）；仅当 {@link #espLabel()} 非 null 时有意义 */
    public int espColor() {
        return espColor;
    }

    /** 标题配色（旧项目点位卡片 {@code titleColor}，{@code :667-677}） */
    public String titleColor() {
        return titleColor;
    }

    /** 该模式需要的点位（唯一实现：指令门禁、启动自检、控制台点位页都调这里） */
    public static List<EnchantPointType> requiredFor(EnchantTargetMode mode) {
        return mode == EnchantTargetMode.GEAR ? GEAR_POINTS : BOOK_POINTS;
    }

    /** 指令节点 / 落盘键 → 点位类型；未知返回 {@code null} */
    public static EnchantPointType ofNode(String node) {
        if (node == null) return null;
        for (EnchantPointType type : values()) {
            if (type.node.equals(node)) return type;
        }
        return null;
    }

    /** 落盘键 → 点位类型；未知返回 {@code null} */
    public static EnchantPointType ofPosKey(String posKey) {
        if (posKey == null) return null;
        for (EnchantPointType type : values()) {
            if (type.posKey.equals(posKey)) return type;
        }
        return null;
    }

    @Override
    public String toString() {
        return title;
    }
}
