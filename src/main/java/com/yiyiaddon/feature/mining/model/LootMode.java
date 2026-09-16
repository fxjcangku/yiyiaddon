package com.yiyiaddon.feature.mining.model;

/**
 * 采集模式：决定矿物按「原矿方块」还是「掉落物」计数判定。
 *
 * <p>逐字搬运旧项目 {@code mining/AutoMinerModule.java:68-86}：枚举显示名即 {@link #toString()}
 * 的返回值（{@code 精准采集} / {@code 时运}），用户在设置页看到的就是这两个词，禁止改写。</p>
 *
 * <p>下界残骸（{@code ancient_debris}）掉落物就是自身方块，两模式天然共用，无需特判。</p>
 */
public enum LootMode {

    /** 精准采集：目标选择器显示原矿 */
    SILK_TOUCH("精准采集"),

    /** 时运：目标选择器显示掉落物（粗铁/粗金/粗铜等） */
    FORTUNE("时运");

    private final String cn;

    LootMode(String cn) {
        this.cn = cn;
    }

    @Override
    public String toString() {
        return cn;
    }
}
