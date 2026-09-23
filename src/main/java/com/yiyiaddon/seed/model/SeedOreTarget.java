package com.yiyiaddon.seed.model;

import java.util.Objects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

/**
 * 种子挖矿正式模块 · 一次预测请求（输入边界的数据载体）。
 *
 * <p>它把「预测什么」这件事完整描述成四个种子无关的字段：种子 / 维度 / 目标区块 / 矿物种类。
 * <b>这里没有、也不允许有</b>：真实 Chunk、真实 pre-diamond、真实矿石坐标、真实装饰批号、
 * 真实 FEATURES 调度顺序（正式化第一阶段口径第十四节）。</p>
 *
 * <p>正式预测器只吃这四项 + 当前版本原版注册表 / worldgen 配置，因此同一个请求在任何一台机器、
 * 任何一个已经生成过的真实世界旁边都算出同一份结果。</p>
 */
public record SeedOreTarget(long seed, ResourceKey<Level> dimension, ChunkPos chunk, OreType oreType) {

    public SeedOreTarget {
        Objects.requireNonNull(dimension, "dimension");
        Objects.requireNonNull(chunk, "chunk");
        Objects.requireNonNull(oreType, "oreType");
    }

    /** 主世界钻石预测请求（本阶段唯一被支持的组合）。 */
    public static SeedOreTarget diamond(long seed, ChunkPos chunk) {
        return new SeedOreTarget(seed, Level.OVERWORLD, chunk, OreType.DIAMOND);
    }

    /** 目标区块的方块区域描述（日志 / 报告用）。 */
    public String describeCn() {
        return oreType.displayNameCn() + " @ " + dimension.identifier() + " 区块(" + chunk.x() + "," + chunk.z() + ")"
                + " 种子 " + seed;
    }
}
