package com.yiyiaddon.repository.autochest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.model.autochest.ContainerRecord;
import com.yiyiaddon.platform.world.WorldIdentity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 已处理记录存储：记录哪些容器坐标已经处理过，避免重复开箱。
 *
 * <p>逐字复刻旧项目 {@code autochest/service/ContainerRecordManager.java}。数据持久化到
 * {@code <configDir>/yiyiaddon/autochest/{server}.json}（旧项目同路径 {@code config/yiyiaddon/autochest}，
 * 见 {@code ContainerRecordManager.java:25}），每个玩家各自一份，不打包进 JAR。按服务器隔离，切服自动重载；
 * 单条记录内再以「维度 + 坐标 + 容器类型」做身份判定，保证跨维度 / 换类型不误判。</p>
 *
 * <p>与旧项目差异仅在：路径根由 {@link FabricLoader#getConfigDir()} 解析、JSON 由手写拼接改为 Gson
 * （键名 / 数组结构 / 数值与字符串类型逐字一致）。</p>
 */
public final class ContainerRecordStore {

    /** 记录目录（旧项目 {@code ContainerRecordManager.java:25}） */
    private static final Path CONFIG_DIR =
        FabricLoader.getInstance().getConfigDir().resolve("yiyiaddon").resolve("autochest");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Minecraft mc;
    private final List<ContainerRecord> records = new ArrayList<>();

    public ContainerRecordStore(Minecraft mc) {
        this.mc = mc;
    }

    /** 当前服务器标识（单人世界为 singleplayer，多人取 IP 净化后字符串） */
    private String serverIdentifier() {
        return WorldIdentity.server();
    }

    private Path dataFile() {
        return CONFIG_DIR.resolve(WorldIdentity.fileSafeServer() + ".json");
    }

    /** 切换服务器或启动时调用，加载对应服务器的记录 */
    public void reload() {
        records.clear();
        Path file = dataFile();
        migrateLegacyMultiplayerFile(file);
        if (!Files.exists(file)) return;
        try {
            parse(Files.readString(file));
        } catch (Exception ignored) {
            // 读取失败不阻塞模块，视为无记录
        }
    }

    /** 首次使用新隔离键时复制旧版多人记录；旧单人文件缺少存档身份，禁止跨存档认领（旧项目 {@code ContainerRecordManager.java:57-70}）。 */
    private void migrateLegacyMultiplayerFile(Path target) {
        if (WorldIdentity.server().startsWith("singleplayer:") || Files.exists(target)) return;
        String legacyKey = WorldIdentity.legacyServerFileKey();
        if (legacyKey == null) return;
        Path legacy = CONFIG_DIR.resolve(legacyKey + ".json");
        if (!Files.isRegularFile(legacy) || legacy.equals(target)) return;
        try {
            Files.createDirectories(target.getParent());
            Files.copy(legacy, target);
            // 原文件必须保留；升级迁移只复制，避免回滚旧版本时丢失记录。
        } catch (Exception ignored) {
            // 迁移失败时保留旧文件，避免破坏现有数据
        }
    }

    /**
     * 某容器坐标是否已处理（且未过期）。
     *
     * <p>服务器隔离由文件路径保证；维度 + 坐标 + 容器类型在此逐一比对。
     * 若记录存在但容器类型不一致（原容器被换成另一种容器），视为未处理并失效旧记录。
     * 与旧项目 {@code ContainerRecordManager.java:78-90} 逐字一致。</p>
     */
    public boolean isProcessed(BlockPos pos, String dimension, String containerType, long expireMs) {
        ContainerRecord record = find(pos, dimension);
        if (record == null) return false;
        if (record.isExpired(expireMs)) {
            remove(pos, dimension);
            return false;
        }
        if (!record.sameType(containerType)) {
            remove(pos, dimension);
            return false;
        }
        return true;
    }

    /** 仅按「存在 + 未过期」判定的版本（不比对容器类型） */
    public boolean isProcessed(BlockPos pos, String dimension, long expireMs) {
        ContainerRecord record = find(pos, dimension);
        if (record == null) return false;
        if (record.isExpired(expireMs)) {
            remove(pos, dimension);
            return false;
        }
        return true;
    }

    /** 记录一个已处理容器并立即落盘 */
    public void markProcessed(BlockPos pos, String dimension, String containerType) {
        // 同一身份重复标记时先移除旧记录，避免列表内堆积
        remove(pos, dimension);
        records.add(new ContainerRecord(serverIdentifier(), pos, dimension, containerType));
        save();
    }

    /** 查找给定维度 + 坐标的记录（用于查看信息与失效判断），无则返回 null */
    public ContainerRecord find(BlockPos pos, String dimension) {
        for (ContainerRecord record : records) {
            if (record.pos().equals(pos) && record.dimension().equals(dimension)) {
                return record;
            }
        }
        return null;
    }

    /** 使某容器记录失效（容器被破坏 / 被换成其它类型时调用） */
    public boolean invalidate(BlockPos pos, String dimension) {
        return remove(pos, dimension);
    }

    private boolean remove(BlockPos pos, String dimension) {
        boolean removed = records.removeIf(r -> r.pos().equals(pos) && r.dimension().equals(dimension));
        if (removed) save();
        return removed;
    }

    /** 清空当前服务器的记录 */
    public void clear() {
        records.clear();
        save();
    }

    /** 清空当前服务器下指定维度的记录（维度 = {@code minecraft:overworld} 等） */
    public int clearDimension(String dimension) {
        int removed = records.size();
        records.removeIf(r -> r.dimension().equals(dimension));
        removed -= records.size();
        if (removed > 0) save();
        return removed;
    }

    /** 清空全部服务器的处理记录（删除记录目录下所有 json 文件） */
    public int clearAllServers() {
        int files = 0;
        try {
            if (Files.isDirectory(CONFIG_DIR)) {
                try (var stream = Files.list(CONFIG_DIR)) {
                    for (Path file : stream.toList()) {
                        if (file.getFileName().toString().endsWith(".json")) {
                            Files.deleteIfExists(file);
                            files++;
                        }
                    }
                }
            }
        } catch (Exception ignored) {
            // 删除失败不影响后续
        }
        records.clear();
        return files;
    }

    public int size() {
        return records.size();
    }

    // ── 持久化：JSON（键名 x / y / z / dim / type / t / ver，旧项目 ContainerRecordManager.java:163-186） ──

    private void save() {
        try {
            Path file = dataFile();
            Files.createDirectories(file.getParent());
            JsonArray array = new JsonArray();
            for (ContainerRecord r : records) {
                JsonObject obj = new JsonObject();
                obj.addProperty("x", r.pos().getX());
                obj.addProperty("y", r.pos().getY());
                obj.addProperty("z", r.pos().getZ());
                obj.addProperty("dim", r.dimension() == null ? "" : r.dimension());
                obj.addProperty("type", r.containerType() == null ? "" : r.containerType());
                obj.addProperty("t", r.processedAt());
                obj.addProperty("ver", r.dataVersion());
                array.add(obj);
            }
            Files.writeString(file, GSON.toJson(array));
        } catch (Exception ignored) {
            // 写失败不影响运行
        }
    }

    private void parse(String json) {
        String server = serverIdentifier();
        JsonElement root = JsonParser.parseString(json);
        if (root == null || !root.isJsonArray()) return;
        for (JsonElement element : root.getAsJsonArray()) {
            if (!element.isJsonObject()) continue;
            JsonObject obj = element.getAsJsonObject();
            int x = intOf(obj, "x");
            int y = intOf(obj, "y");
            int z = intOf(obj, "z");
            String dim = stringOf(obj, "dim");
            String type = stringOf(obj, "type");
            long t = longOf(obj, "t");
            int ver = intOf(obj, "ver");
            records.add(new ContainerRecord(server, new BlockPos(x, y, z), dim, type,
                ContainerRecord.Status.PROCESSED, t, ver));
        }
    }

    private static int intOf(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        if (element == null || element.isJsonNull()) return 0;
        try {
            return element.getAsInt();
        } catch (Exception ignored) {
            return 0;
        }
    }

    private static long longOf(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        if (element == null || element.isJsonNull()) return 0L;
        try {
            return element.getAsLong();
        } catch (Exception ignored) {
            return 0L;
        }
    }

    private static String stringOf(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        if (element == null || element.isJsonNull()) return "";
        try {
            return element.getAsString();
        } catch (Exception ignored) {
            return "";
        }
    }
}
