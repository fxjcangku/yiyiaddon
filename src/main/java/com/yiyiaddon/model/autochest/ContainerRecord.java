package com.yiyiaddon.model.autochest;

import com.yiyiaddon.platform.world.WorldIdentity;
import net.minecraft.core.BlockPos;

/**
 * 已处理容器记录：标记某个容器坐标已被处理过，避免重复开箱。
 *
 * <p>至少保存：服务器/世界、维度、X/Y/Z、容器类型、状态、处理时间、数据版本。
 * 服务器 + 维度 + 坐标三者共同决定一个容器身份——服务器 A 主世界与服务器 B 主世界、
 * 以及同服务器的主世界与下界，即便 XYZ 相同也必须视为不同容器，不得复用记录。</p>
 *
 * <p>由容器记录管理器持久化到客户端目录 {@code config/yiyiaddon/autochest/{server}.json}，
 * 按服务器隔离（路径根与文件名规则统一走 {@link WorldIdentity}）。</p>
 */
public final class ContainerRecord {

    /** 记录状态：本阶段只有「已处理」，处理中是运行时瞬态不落盘 */
    public enum Status {
        PROCESSED("已处理");

        private final String displayName;

        Status(String displayName) {
            this.displayName = displayName;
        }

        /** 中文文案 */
        public String displayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    /** 服务器/世界标识 */
    private final String server;

    /** 容器所在维度标识（{@code minecraft:overworld} 等） */
    private final String dimension;

    /** 容器坐标 */
    private final BlockPos pos;

    /** 容器类型稳定键（如 {@code chest}），用于「重新放置不同类型容器」时识别变更 */
    private final String containerType;

    /** 记录状态 */
    private final Status status;

    /** 处理完成时间（毫秒） */
    private final long processedAt;

    /** 记录时的 Minecraft 数据版本 */
    private final int dataVersion;

    public ContainerRecord(String server, BlockPos pos, String dimension, String containerType) {
        this(server, pos, dimension, containerType, Status.PROCESSED,
            System.currentTimeMillis(), WorldIdentity.dataVersion());
    }

    public ContainerRecord(String server, BlockPos pos, String dimension, String containerType,
                           Status status, long processedAt, int dataVersion) {
        this.server = server;
        this.pos = pos.immutable();
        this.dimension = dimension;
        this.containerType = containerType;
        this.status = status == null ? Status.PROCESSED : status;
        this.processedAt = processedAt;
        this.dataVersion = dataVersion;
    }

    public String server() {
        return server;
    }

    public BlockPos pos() {
        return pos;
    }

    public String dimension() {
        return dimension;
    }

    public String containerType() {
        return containerType;
    }

    public Status status() {
        return status;
    }

    public long processedAt() {
        return processedAt;
    }

    public int dataVersion() {
        return dataVersion;
    }

    /** 记录是否已过期（超过给定毫秒时长）；0 或负数表示立即过期 */
    public boolean isExpired(long expireMs) {
        if (expireMs <= 0) return true;
        return System.currentTimeMillis() - processedAt > expireMs;
    }

    /** 记录是否指向给定服务器 + 坐标 + 维度（服务器/维度隔离判据） */
    public boolean matches(String otherServer, BlockPos otherPos, String otherDim) {
        return server.equals(otherServer) && pos.equals(otherPos) && dimension.equals(otherDim);
    }

    /** 记录指向的容器类型是否与当前方块类型一致（用于「破坏后重放」识别） */
    public boolean sameType(String typeId) {
        return containerType != null && containerType.equals(typeId);
    }
}
