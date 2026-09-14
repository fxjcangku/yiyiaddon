package com.yiyiaddon.repository.autochest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.platform.world.WorldIdentity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 标点存储：管理用户手动保存的容器点位（标点模式专用）。
 *
 * <p>逐字复刻旧项目 {@code autochest/service/ChestPointManager.java}。标点模式只处理这里保存的点位，
 * 不会因附近扫描到箱子而自动处理。每个点位保存服务器 / 维度 / 坐标 / 容器类型，内存与磁盘同步
 * （增删清立即落盘）。持久化到 {@code <configDir>/yiyiaddon/autochest/points/{server}.json}，
 * 按服务器隔离；旧项目同路径为 {@code config/yiyiaddon/autochest/points}（{@code ChestPointManager.java:25}）。</p>
 *
 * <p>与旧项目差异仅在：路径根由 {@link FabricLoader#getConfigDir()} 解析、JSON 由手写拼接改为 Gson
 * （键名 / 数组结构 / 数值与字符串类型逐字一致）。</p>
 */
public final class ChestPointStore {

    /** 标点目录（旧项目 {@code ChestPointManager.java:25}） */
    private static final Path CONFIG_DIR =
        FabricLoader.getInstance().getConfigDir().resolve("yiyiaddon").resolve("autochest").resolve("points");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Minecraft mc;
    private final List<ChestTarget> points = new ArrayList<>();

    public ChestPointStore(Minecraft mc) {
        this.mc = mc;
    }

    private String serverIdentifier() {
        return WorldIdentity.server();
    }

    private Path dataFile() {
        return CONFIG_DIR.resolve(WorldIdentity.fileSafeServer() + ".json");
    }

    /** 加载标点（切服 / 启动时调用） */
    public void reload() {
        points.clear();
        Path file = dataFile();
        migrateLegacyFile(file);
        if (!Files.exists(file)) return;
        try {
            parse(Files.readString(file));
        } catch (Exception ignored) {
            // 读取失败视为无标点
        }
    }

    /** 首次使用新隔离键时复制旧版多人标点；旧单人文件缺少存档身份，禁止跨存档认领（旧项目 {@code ChestPointManager.java:56-69}）。 */
    private void migrateLegacyFile(Path target) {
        if (Files.exists(target)) return;
        String legacyKey = WorldIdentity.legacyServerFileKey();
        if (legacyKey == null) return;
        Path legacy = CONFIG_DIR.resolve(legacyKey + ".json");
        if (!Files.isRegularFile(legacy) || legacy.equals(target)) return;
        try {
            Files.createDirectories(target.getParent());
            // 只复制不移动：旧文件是升级回滚依据，复制完成后仍允许旧版本读取。
            Files.copy(legacy, target);
        } catch (Exception ignored) {
            // 迁移失败时保留旧文件，避免破坏现有数据
        }
    }

    /** 新增一个标点（含服务器 + 容器类型），已存在返回 false */
    public boolean add(BlockPos pos, String dimension, String containerType) {
        if (pos == null || dimension == null) return false;
        for (ChestTarget p : points) {
            if (p.pos().equals(pos) && p.dimension().equals(dimension)) return false; // 已存在
        }
        points.add(new ChestTarget(pos, dimension, serverIdentifier(), containerType));
        save();
        return true;
    }

    /** 删除一个标点（内存 + 磁盘同步） */
    public boolean remove(BlockPos pos, String dimension) {
        boolean removed = points.removeIf(p -> p.pos().equals(pos) && p.dimension().equals(dimension));
        if (removed) save();
        return removed;
    }

    /** 清空全部标点；返回被清空的标点数量 */
    public int clear() {
        int removed = points.size();
        points.clear();
        save();
        return removed;
    }

    /** 查找指定维度 + 坐标的标点，无则返回 null */
    public ChestTarget find(BlockPos pos, String dimension) {
        for (ChestTarget p : points) {
            if (p.pos().equals(pos) && p.dimension().equals(dimension)) return p;
        }
        return null;
    }

    /** 当前维度的标点列表（只读快照） */
    public List<ChestTarget> pointsInCurrentDimension() {
        List<ChestTarget> result = new ArrayList<>();
        String currentDim = WorldIdentity.dimension();
        if (currentDim.isEmpty()) return result;
        for (ChestTarget p : points) {
            if (p.dimension().equals(currentDim)) result.add(p);
        }
        return result;
    }

    public boolean hasPoints() {
        return !points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    // ── 持久化：JSON（键名 x / y / z / dim / type，旧项目 ChestPointManager.java:124-145） ──

    private void save() {
        try {
            Path file = dataFile();
            Files.createDirectories(file.getParent());
            JsonArray array = new JsonArray();
            for (ChestTarget p : points) {
                JsonObject obj = new JsonObject();
                obj.addProperty("x", p.pos().getX());
                obj.addProperty("y", p.pos().getY());
                obj.addProperty("z", p.pos().getZ());
                obj.addProperty("dim", p.dimension() == null ? "" : p.dimension());
                obj.addProperty("type", p.containerType() == null ? "" : p.containerType());
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
            points.add(new ChestTarget(new BlockPos(x, y, z), dim, server, type));
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
