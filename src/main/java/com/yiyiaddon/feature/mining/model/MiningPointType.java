package com.yiyiaddon.feature.mining.model;

/**
 * 三点点位类型：矿物箱 / 食物箱 / 挂机修复点。
 *
 * <p>{@link #node()} 是旧项目 {@code WKCommand.saveData()}（{@code :609-636}）的 JSON 顶层键，
 * 落盘与旧档迁移都按它原样写入，禁止改名；{@link #displayName()} 是旧项目
 * {@code WKCommand.validateBinding}（{@code :388-393}、{@code :425-437}）与自检里使用的中文名。</p>
 */
public enum MiningPointType {

    MINERAL("矿物箱", "mineral"),
    FOOD("食物箱", "food"),
    AFK("挂机修复点", "afk");

    private final String displayName;
    private final String node;

    MiningPointType(String displayName, String node) {
        this.displayName = displayName;
        this.node = node;
    }

    /** 中文名（播报、自检、标签用） */
    public String displayName() {
        return displayName;
    }

    /** 旧项目 JSON 顶层键，落盘与读取的唯一口径 */
    public String node() {
        return node;
    }

    /** JSON 顶层键 → 点位类型；未知键返回 {@code null} */
    public static MiningPointType ofNode(String node) {
        if (node == null) return null;
        for (MiningPointType type : values()) {
            if (type.node.equals(node)) return type;
        }
        return null;
    }
}
