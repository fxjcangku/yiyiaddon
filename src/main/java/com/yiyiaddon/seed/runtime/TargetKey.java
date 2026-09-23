package com.yiyiaddon.seed.runtime;

import com.yiyiaddon.seed.model.OreType;
import java.util.Objects;
import net.minecraft.world.level.ChunkPos;

/**
 * 种子挖矿正式模块 · <b>预测缓存键</b>（正式化第八阶段 236）。
 *
 * <p><b>为什么键里必须有维度与矿物</b>：233 的键只有 {@link ChunkPos}，那在「只有主世界钻石」时
 * 是对的；236 起同一时刻会同时计算多种矿物，同一区块在主世界与下界也都是独立的目标。
 * 如果继续用区块做键，会出现两类<b>静默</b>错误：</p>
 * <ul>
 *     <li>钻石的缓存被当成红石的（同区块不同矿物互相顶掉）；</li>
 *     <li>主世界的缓存被当成下界的（同坐标不同维度互相顶掉，且下界的 Y 语义完全不同）。</li>
 * </ul>
 * <p>这两类错误都不会报错，只会给出「看起来正常」的错结果，因此键必须显式包含三件事。</p>
 *
 * @param dimensionId 维度标识（{@code minecraft:overworld} / {@code minecraft:the_nether}）
 * @param oreType     矿物种类
 * @param chunk       目标区块
 */
public record TargetKey(String dimensionId, OreType oreType, ChunkPos chunk) {

    public TargetKey {
        Objects.requireNonNull(dimensionId, "dimensionId");
        Objects.requireNonNull(oreType, "oreType");
        Objects.requireNonNull(chunk, "chunk");
    }

    /** 由一次预测的结果/请求构造键。 */
    public static TargetKey of(String dimensionId, OreType oreType, ChunkPos chunk) {
        return new TargetKey(dimensionId, oreType, chunk);
    }

    /** 一行中文摘要（日志 / 报告用）。 */
    public String describeCn() {
        return oreType.displayNameCn() + "@" + dimensionId + "(" + chunk.x() + "," + chunk.z() + ")";
    }
}
