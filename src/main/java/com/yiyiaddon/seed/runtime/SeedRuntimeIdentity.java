package com.yiyiaddon.seed.runtime;

import java.util.Objects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * 种子挖矿正式模块 · <b>运行时身份</b>（正式化第五阶段 233）。
 *
 * <p><b>它解决什么</b>：客户端预测缓存（{@link SeedPredictionRepository}）与观察状态必须绑死在
 * 「这一次会话算的是谁」上面。只要下列任何一项改变，旧缓存就不再属于当前世界，
 * 必须整体失效（口径第十五、四十、四十一、四十二、四十三节）：</p>
 *
 * <ul>
 *     <li>{@link #worldKey} —— 世界身份（多人 {@code host:port} / 单人存档目录名，见
 *         {@code platform.world.WorldIdentity#server()}）。<b>服务器 A → B 换服由它识别</b>；</li>
 *     <li>{@link #seed} —— 用户填写的服务器种子；</li>
 *     <li>{@link #dimensionId} —— 维度标识（{@code minecraft:overworld} 形态）；</li>
 *     <li>{@link #minecraftVersion} —— Minecraft 版本（Worker 握手也按它拒绝跨版本共用）；</li>
 *     <li>{@link #epoch} —— 会话代号：<b>每次失效都会递增</b>。异步回来的预测结果靠它识别
 *         「我出发时的身份已经没了」，从而丢弃回写（口径第二十二节：沿用既有 generation /
 *         stale response guard 的口径，只是这一条覆盖的是覆盖式预测，而不是手动单次预测）。</li>
 * </ul>
 *
 * <p><b>线程模型</b>：只在客户端主线程构造与比较；后台线程只做「读一次快照再比较」这种只读判断，
 * 因此本类型是不可变 record，比较不需要锁。</p>
 *
 * @param worldKey         世界身份键（{@code host:port} 或 {@code singleplayer:<存档名>}）
 * @param seed             服务器种子
 * @param dimensionId      维度标识（{@code minecraft:overworld}）
 * @param minecraftVersion Minecraft 版本
 * @param epoch            会话代号（每次失效递增，用于丢弃过期响应）
 */
public record SeedRuntimeIdentity(String worldKey, long seed, String dimensionId, String minecraftVersion,
                                  long epoch) {

    public SeedRuntimeIdentity {
        Objects.requireNonNull(worldKey, "worldKey");
        Objects.requireNonNull(dimensionId, "dimensionId");
        Objects.requireNonNull(minecraftVersion, "minecraftVersion");
    }

    /**
     * 构造一个身份。
     *
     * @param epoch 会话代号；由调用方（服务层）统一递增，保证「谁失效就换号」
     */
    public static SeedRuntimeIdentity of(String worldKey, long seed, ResourceKey<Level> dimension,
                                         String minecraftVersion, long epoch) {
        Objects.requireNonNull(dimension, "dimension");
        return new SeedRuntimeIdentity(worldKey, seed, dimension.identifier().toString(),
                minecraftVersion, epoch);
    }

    /** 本维度是否为正式支持的主世界（口径第四十二节：Overworld only）。 */
    public boolean overworld() {
        return Level.OVERWORLD.identifier().toString().equals(dimensionId);
    }

    /** 一行中文摘要（诊断 / 报告用；不含任何「服务器一定用了这个种子」的结论）。 */
    public String describeCn() {
        return "世界 " + worldKey + " / 种子 " + seed + " / 维度 " + dimensionId
                + " / Minecraft " + minecraftVersion + " / 会话 #" + epoch;
    }
}
