package com.yiyiaddon.platform.world;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

import java.nio.file.Path;
import java.util.Locale;

/**
 * 世界与服务器身份适配：统一提供「服务器键 / 世界标识 / 维度标识 / 数据版本」。
 *
 * <p>跨服务器与跨维度的数据隔离全部以本类为准，避免各持久化层各写一套导致隔离失效。</p>
 *
 * <p><b>统一规范（本项目唯一口径，禁止另建第二套）：</b></p>
 * <ul>
 *   <li>服务器键：多人用规范化 {@code host:port} 小写形式（缺端口补 25565，IPv6 加方括号）；
 *       单人用 {@code singleplayer:<存档目录名>}。</li>
 *   <li>维度标识：{@code mc.level.dimension().identifier().toString()}，即
 *       {@code minecraft:overworld} 这种稳定形式，<b>禁止</b>使用 {@code ResourceKey[...]} 包装格式。</li>
 * </ul>
 */
public final class WorldIdentity {

    private static final String SINGLEPLAYER_PREFIX = "singleplayer:";

    private WorldIdentity() {
    }

    /**
     * 逻辑层服务器键。
     *
     * <p>单人世界的身份取存档目录名（由世界路径反推），而不是展示标题——同名展示标题的不同存档
     * 必须被隔离，否则数据会串档。</p>
     */
    public static String server() {
        Minecraft mc = Minecraft.getInstance();
        ServerData data = mc.getCurrentServer();
        if (data != null && data.ip != null && !data.ip.isBlank()) {
            String key = canonicalServerKey(data.ip);
            if (key != null) return key;
        }
        String worldName = singleplayerWorldName();
        return SINGLEPLAYER_PREFIX + (worldName == null ? "unknown" : worldName);
    }

    /** 当前会话是否是单人世界 */
    public static boolean isSingleplayer() {
        return Minecraft.getInstance().hasSingleplayerServer();
    }

    /**
     * 单人存档目录名；无法获取返回 {@code null}。
     *
     * <p>取 {@code level.dat} 的父目录名，即存档文件夹的唯一标识。</p>
     */
    public static String singleplayerWorldName() {
        try {
            var server = Minecraft.getInstance().getSingleplayerServer();
            if (server == null) return null;
            Path levelFile = server.getWorldPath(net.minecraft.world.level.storage.LevelResource.LEVEL_DATA_FILE);
            Path dir = levelFile.getParent();
            if (dir == null) return null;
            String name = dir.getFileName().toString();
            return name.isBlank() ? null : name;
        } catch (Exception ignored) {
            // 单人服务端尚未就绪或路径不可用
            return null;
        }
    }

    /** 逻辑服务器键 → 文件名安全片段（JSON 内仍保留逻辑键原文） */
    public static String fileSafeKey(String key) {
        if (key == null || key.isBlank()) return "unknown";
        boolean singleplayer = key.startsWith(SINGLEPLAYER_PREFIX);
        String value = singleplayer ? key.substring(SINGLEPLAYER_PREFIX.length()) : key;
        String prefix = singleplayer ? "singleplayer_" : "server_";
        String safe = value.replaceAll("[\\\\/:*?\"<>|\\x00-\\x1F]", "_")
            .replaceAll("\\s+", " ")
            .replaceAll("^[. ]+|[. ]+$", "");
        return prefix + (safe.isBlank() ? "unknown" : safe);
    }

    /** 当前服务器键对应的文件名安全片段 */
    public static String fileSafeServer() {
        return fileSafeKey(server());
    }

    /**
     * 旧版多人文件名键（仅地址净化，不补端口、不小写）。
     *
     * <p>仅供读取历史文件时兼容识别，<b>不得</b>用于新数据写入。单人旧文件没有存档身份，
     * 因此返回 {@code null}，禁止自动认领。</p>
     */
    public static String legacyServerFileKey() {
        ServerData data = Minecraft.getInstance().getCurrentServer();
        if (data == null || data.ip == null) return null;
        String safe = data.ip.replaceAll("[^a-zA-Z0-9._-]", "_");
        return safe.isBlank() ? "unknown" : safe;
    }

    /** 当前维度标识（如 {@code minecraft:overworld}）；世界未加载返回空串 */
    public static String dimension() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return "";
        return mc.level.dimension().identifier().toString();
    }

    /**
     * 维度中文名：主世界 / 下界 / 末地，其余回退为原始标识。
     *
     * <p>用包含判断而非相等判断，兼容历史上可能落盘的 {@code ResourceKey[...]} 包装格式。</p>
     */
    public static String dimensionDisplayName(String dimension) {
        if (dimension == null || dimension.isBlank()) return "未知";
        if (dimension.contains("overworld")) return "主世界";
        if (dimension.contains("the_nether")) return "下界";
        if (dimension.contains("the_end")) return "末地";
        return dimension;
    }

    /** 当前 Minecraft 数据版本；获取失败返回 0 */
    public static int dataVersion() {
        try {
            return SharedConstants.getCurrentVersion().dataVersion().version();
        } catch (Exception ignored) {
            return 0;
        }
    }

    /**
     * 规范化服务器地址为 {@code host:port} 小写形式。
     *
     * <p>缺端口时补原版默认端口；IPv6 字面量加方括号；无法解析返回 {@code null}。</p>
     */
    public static String canonicalServerKey(String address) {
        if (address == null || address.isBlank()) return null;
        String raw = address.trim().toLowerCase(Locale.ROOT);

        // IPv6 字面量：[::1]:25565 或 ::1
        if (raw.startsWith("[")) {
            int end = raw.indexOf(']');
            if (end > 0) {
                String host = raw.substring(1, end);
                String rest = raw.substring(end + 1);
                if (rest.startsWith(":") && rest.length() > 1) return "[" + host + "]:" + rest.substring(1);
                return "[" + host + "]:25565";
            }
        }
        int colon = raw.lastIndexOf(':');
        if (colon > 0 && raw.indexOf(':') == colon) {
            String port = raw.substring(colon + 1);
            if (!port.isEmpty() && port.chars().allMatch(Character::isDigit)) return raw;
        }
        return raw + ":25565";
    }
}
