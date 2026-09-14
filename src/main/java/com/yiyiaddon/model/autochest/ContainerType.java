package com.yiyiaddon.model.autochest;

import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * 容器类型：一种可被自动箱子识别并处理的合法容器。
 *
 * <p>每种容器类型由一个稳定英文键 {@code id}、一个中文显示名 {@code displayName}
 * 和一个方块匹配谓词 {@code matcher} 组成。新增容器类型只需向
 * {@link ContainerTypeRegistry} 注册一条即可，无需改动扫描核心。</p>
 */
public final class ContainerType {

    /** 稳定英文键，如 {@code chest}、{@code shulker_box}，用于持久化与去重 */
    private final String id;

    /** 中文显示名，如「箱子」「16色潜影盒」，用于配置界面展示 */
    private final String displayName;

    /** 方块匹配谓词：判断一个 Block 是否属于该容器类型 */
    private final Predicate<Block> matcher;

    public ContainerType(String id, String displayName, Predicate<Block> matcher) {
        this.id = id;
        this.displayName = displayName;
        this.matcher = matcher;
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    /** 判断给定方块是否属于该容器类型 */
    public boolean matches(Block block) {
        return block != null && matcher.test(block);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContainerType other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
