package com.yiyiaddon.model.autochest;

import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.CopperChestBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.TrappedChestBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * 容器类型注册表：维护自动箱子支持的全部合法容器类型。
 *
 * <p>默认内置五种：箱子、陷阱箱、16色潜影盒、木桶、铜箱。新增合法容器只需调用
 * {@link #register(ContainerType)} 追加一条，扫描核心与选择器无需改动——
 * 选择器会遍历本注册表自动呈现。</p>
 *
 * <p>匹配以 {@code Block} 类型为准（而非 BlockEntity）：普通箱子、陷阱箱、铜箱的
 * BlockEntity 都是 {@code ChestBlockEntity}，只有按 Block 才能区分。</p>
 *
 * <p>类型 id 与中文名逐字取自旧项目 {@code autochest/model/ContainerTypeRegistry.java:30-49}，
 * 顺序即界面展示顺序，禁止增删或改名。</p>
 */
public final class ContainerTypeRegistry {

    /** 全部已注册容器类型（注册顺序即界面展示顺序） */
    private static final List<ContainerType> TYPES = new ArrayList<>();

    static {
        // 箱子：普通箱子（排除陷阱箱与铜箱，二者各自独立成类型）
        register(new ContainerType("chest", "箱子", block ->
            block instanceof ChestBlock
                && !(block instanceof TrappedChestBlock)
                && !(block instanceof CopperChestBlock)));

        // 陷阱箱
        register(new ContainerType("trapped_chest", "陷阱箱", block ->
            block instanceof TrappedChestBlock));

        // 16色潜影盒（ShulkerBoxBlock 覆盖全部 16 种颜色）
        register(new ContainerType("shulker_box", "16色潜影盒", block ->
            block instanceof ShulkerBoxBlock));

        // 木桶
        register(new ContainerType("barrel", "木桶", block ->
            block instanceof BarrelBlock));

        // 铜箱（含各氧化阶段：WeatheringCopperChestBlock 继承 CopperChestBlock）
        register(new ContainerType("copper_chest", "铜箱", block ->
            block instanceof CopperChestBlock));
    }

    private ContainerTypeRegistry() {
        // 工具类，禁止实例化
    }

    /** 注册一个容器类型（id 重复则忽略） */
    public static void register(ContainerType type) {
        if (type == null) return;
        if (byId(type.id()) != null) return;
        TYPES.add(type);
    }

    /** 全部已注册容器类型（只读快照） */
    public static List<ContainerType> all() {
        return List.copyOf(TYPES);
    }

    /** 按稳定键查找容器类型，不存在返回 null */
    public static ContainerType byId(String id) {
        if (id == null) return null;
        for (ContainerType type : TYPES) {
            if (type.id().equals(id)) return type;
        }
        return null;
    }

    /** 判断给定方块是否命中指定 id 的容器类型 */
    public static boolean matches(String id, Block block) {
        ContainerType type = byId(id);
        return type != null && type.matches(block);
    }

    /** 在全部注册类型中查找第一个命中该方块的类型，未命中返回 null */
    public static ContainerType match(Block block) {
        if (block == null) return null;
        for (ContainerType type : TYPES) {
            if (type.matches(block)) return type;
        }
        return null;
    }

    /** 在给定启用类型集合中查找第一个命中该方块的类型，未命中返回 null */
    public static ContainerType match(Block block, List<ContainerType> enabled) {
        if (block == null || enabled == null) return null;
        for (ContainerType type : enabled) {
            if (type.matches(block)) return type;
        }
        return null;
    }
}
