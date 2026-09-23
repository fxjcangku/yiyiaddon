package com.yiyiaddon.seed.model;

/**
 * 种子挖矿正式模块 · 矿物种类。
 *
 * <p><b>为什么用枚举而不是「钻石坐标」这类专用类型</b>：正式化第一阶段就定下
 * 「模型的名字与结构要能直接扩到其它矿物，不能出现 DiamondPosition 这种一扩就废的命名」。
 * 正式化第八阶段（236）兑现这一点：枚举从 1 个扩到 11 个，而 {@code SeedOreTarget} /
 * {@code PredictedOre} / {@code PredictionResult} / 渲染快照 / 观察层的类型<b>一行都不用改</b>。</p>
 *
 * <p><b>本枚举只回答「是哪种矿」</b>：它不含任何世界生成参数、不含方块集合、不含扫描范围 ——
 * 那些全部在 {@code com.yiyiaddon.seed.ore.SeedOreRegistry} 里按 {@code (维度, 矿物)} 声明一份
 * （同一个矿物在不同维度可以有不同的方块集合与来源）。这样做的目的是：<b>「这是什么矿」
 * 与「它在本维度怎么生成」是两个正交的问题</b>，混进枚举会让下界与主世界互相污染。</p>
 *
 * <p><b>哪些矿物在哪个维度可用</b>：见 {@link com.yiyiaddon.seed.ore.SeedOreRegistry}；
 * 主世界 8 种（钻石 / 红石 / 青金石 / 金 / 铁 / 铜 / 煤 / 绿宝石），
 * 下界 3 种（远古残骸 / 下界石英 / 下界金）。</p>
 */
public enum OreType {

    // ── 主世界 ──

    /** 钻石：{@code diamond_ore} / {@code deepslate_diamond_ore}。 */
    DIAMOND("钻石"),

    /** 红石：{@code redstone_ore} / {@code deepslate_redstone_ore}。 */
    REDSTONE("红石"),

    /** 青金石：{@code lapis_ore} / {@code deepslate_lapis_ore}。 */
    LAPIS("青金石"),

    /** 金：{@code gold_ore} / {@code deepslate_gold_ore}（下界金是 {@link #NETHER_GOLD}，两者不可混）。 */
    GOLD("金"),

    /** 铁：{@code iron_ore} / {@code deepslate_iron_ore}。 */
    IRON("铁"),

    /** 铜：{@code copper_ore} / {@code deepslate_copper_ore}。 */
    COPPER("铜"),

    /** 煤：{@code coal_ore} / {@code deepslate_coal_ore}。 */
    COAL("煤"),

    /** 绿宝石：{@code emerald_ore} / {@code deepslate_emerald_ore}。 */
    EMERALD("绿宝石"),

    // ── 下界 ──

    /** 远古残骸：{@code ancient_debris}（只有一种形态）。 */
    ANCIENT_DEBRIS("远古残骸"),

    /** 下界石英：{@code nether_quartz_ore}（只有一种形态）。 */
    NETHER_QUARTZ("下界石英"),

    /** 下界金：{@code nether_gold_ore}。它<b>不是</b> {@link #GOLD} 的变种，两者是不同的方块与不同的地物。 */
    NETHER_GOLD("下界金");

    /** 中文显示名（玩家可见文本一律中文；内部类名保持英文）。 */
    private final String displayNameCn;

    OreType(String displayNameCn) {
        this.displayNameCn = displayNameCn;
    }

    /** 中文显示名。 */
    public String displayNameCn() {
        return displayNameCn;
    }

    /**
     * 从枚举名解析；不认识返回 {@code null}（调用方据此 fail-closed）。
     *
     * <p>不要用 {@code OreType.valueOf}：IPC 载荷来自另一个进程，收到陌生字符串时
     * 抛异常的写法容易在异常处理里被顺手吞掉，还不如显式返回 null 让调用点表态。</p>
     */
    public static OreType parse(String name) {
        if (name == null) {
            return null;
        }
        for (OreType type : values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
