package com.yiyiaddon.platform.storage;

import net.minecraft.client.Minecraft;

import java.nio.file.Path;

/**
 * 游戏数据目录适配：统一解析本模组的持久化根路径。
 *
 * <p>数据根目录沿用 {@code <游戏目录>/AutoChest}，<b>这是刻意的兼容决定</b>：既有玩家数据
 * （items / entities / blocks / snapshots / block-snapshots）已经落在该目录下，改名会让
 * 全部历史数据失联。目录名虽带历史来源痕迹，但读写的字段结构与文件语义已按新架构重新定义。</p>
 */
public final class GamePaths {

    /** 身份数据根目录名（从历史目录继承，保证既有数据可继续读取） */
    private static final String IDENTITY_ROOT = "AutoChest";

    private GamePaths() {
    }

    /** 游戏根目录 */
    public static Path dataRoot() {
        return Minecraft.getInstance().gameDirectory.toPath();
    }

    /** 身份数据根目录 */
    public static Path identityRoot() {
        return dataRoot().resolve(IDENTITY_ROOT);
    }

    /** 物品身份库目录 */
    public static Path itemIdentities() {
        return identityRoot().resolve("items");
    }

    /** 实体身份库目录 */
    public static Path entityIdentities() {
        return identityRoot().resolve("entities");
    }

    /** 方块身份库目录 */
    public static Path blockIdentities() {
        return identityRoot().resolve("blocks");
    }

    /** 物品状态快照目录 */
    public static Path itemSnapshots() {
        return identityRoot().resolve("snapshots");
    }

    /** 方块状态快照目录 */
    public static Path blockSnapshots() {
        return identityRoot().resolve("block-snapshots");
    }
}
