package com.yiyiaddon.feature.stardew.region;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.repository.JsonFileStore;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 种植区域（分区种植）数据与落盘：一个服务器一份，按 {@code ServerKey} 隔离。
 *
 * <p><b>这一层是什么：</b>「分区种植」实验功能的数据底座。每个区域是一个 XZ 矩形 + 绑定的作物，
 * 取代「农田起点 / 终点」成为范围来源。落盘走与 {@code StardewPointManager} 同一套做法
 * （目录 {@code run/StardewFarm/regions/<服务器>.json}、原子写、损坏文件返回空且不覆盖）。</p>
 *
 * <p><b>只比 XZ：</b>区域是一个平面矩形，判定成员 / 重叠 / 覆盖一律只比水平坐标，
 * 与洒水器「只比 XZ 不比高度」同口径（盆与作物常常不在同一格高度上）。</p>
 *
 * <p><b>本类不做决定：</b>实验开关是否开启由模块判断；开关关闭时本类的数据照常留在盘上，
 * 只是没有任何调用方读它。</p>
 */
public final class StardewRegionManager {

    /** 区域数量上限：防止误操作圈出几十个区域把列表与扫描拖垮 */
    public static final int MAX_REGIONS = 32;
    /** 单边长度上限（格）：防止误点圈下整张图 */
    public static final int MAX_SIDE = 64;

    /** 一个种植区域：对角两点 + 绑定作物；{@code index} 是用户看到的「区域 N」，删除后不重排 */
    public record Region(int index, String cropKey, String cropName, String dimension,
                         int x1, int y1, int z1, int x2, int y2, int z2) {

        public int minX() {
            return Math.min(x1, x2);
        }

        public int maxX() {
            return Math.max(x1, x2);
        }

        public int minY() {
            return Math.min(y1, y2);
        }

        public int maxY() {
            return Math.max(y1, y2);
        }

        public int minZ() {
            return Math.min(z1, z2);
        }

        public int maxZ() {
            return Math.max(z1, z2);
        }

        public int sizeX() {
            return maxX() - minX() + 1;
        }

        public int sizeZ() {
            return maxZ() - minZ() + 1;
        }

        /** 区域格数（按 XZ 平面计数） */
        public int cellCount() {
            return sizeX() * sizeZ();
        }

        public boolean containsXZ(int x, int z) {
            return x >= minX() && x <= maxX() && z >= minZ() && z <= maxZ();
        }

        /** 该坐标是否落在区域内（只比 XZ） */
        public boolean contains(BlockPos pos) {
            return pos != null && containsXZ(pos.getX(), pos.getZ());
        }

        /** 与另一个区域的 XZ 重叠格数；不重叠返回 0 */
        public int overlapCells(Region other) {
            if (other == null) return 0;
            int dx = Math.min(maxX(), other.maxX()) - Math.max(minX(), other.minX()) + 1;
            int dz = Math.min(maxZ(), other.maxZ()) - Math.max(minZ(), other.minZ()) + 1;
            return dx <= 0 || dz <= 0 ? 0 : dx * dz;
        }

        /** 人类可读范围，回执与列表逐字使用：{@code X19860~19870 Z-1630~-1620} */
        public String rangeText() {
            return "X" + minX() + "~" + maxX() + " Z" + minZ() + "~" + maxZ();
        }

        /** 中心点（回中心用；取矩形几何中心，高度沿用区域自身高度） */
        public BlockPos center() {
            return new BlockPos((minX() + maxX()) / 2, maxY() + 1, (minZ() + maxZ()) / 2);
        }
    }

    private final Path root;
    private final List<Region> regions = new ArrayList<>();
    private String loadedServer = null;

    public StardewRegionManager() {
        this.root = Minecraft.getInstance().gameDirectory.toPath().resolve("StardewFarm").resolve("regions");
    }

    private Path file(String serverKey) {
        String safe = serverKey.replaceAll("[\\\\/:*?\"<>|]", "_");
        return root.resolve(safe + ".json");
    }

    /** 加载指定服务器的区域到内存；与已加载服务器相同则跳过 */
    public synchronized void load(String serverKey) {
        if (serverKey == null || serverKey.equals(loadedServer)) return;
        loadedServer = serverKey;
        regions.clear();

        Path file = file(serverKey);
        if (!Files.isRegularFile(file)) return;
        try {
            JsonObject obj = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
            if (!serverKey.equals(string(obj, "服务器"))) return;
            if (!obj.has("区域") || !obj.get("区域").isJsonArray()) return;
            for (var element : obj.getAsJsonArray("区域")) {
                if (!element.isJsonObject()) continue;
                JsonObject r = element.getAsJsonObject();
                Region region = parse(r);
                if (region != null) regions.add(region);
            }
        } catch (Exception ignored) {
            // 损坏文件返回空，不覆盖
        }
    }

    private static Region parse(JsonObject r) {
        try {
            String cropKey = string(r, "作物键");
            String cropName = string(r, "作物");
            if (cropKey == null || cropName == null) return null;
            return new Region(intOf(r, "序号", 0), cropKey, cropName, string(r, "维度"),
                intOf(r, "x1", 0), intOf(r, "y1", 0), intOf(r, "z1", 0),
                intOf(r, "x2", 0), intOf(r, "y2", 0), intOf(r, "z2", 0));
        } catch (Exception ignored) {
            return null;
        }
    }

    /** 保存当前内存区域（原子写入，失败保留旧档） */
    public synchronized boolean save(String serverKey) {
        if (serverKey == null || !serverKey.equals(loadedServer)) return false;
        JsonObject obj = new JsonObject();
        obj.addProperty("服务器", serverKey);
        obj.addProperty("资源指纹", com.yiyiaddon.service.resourcepack.ResourceExtractionService.fingerprint());
        JsonArray array = new JsonArray();
        for (Region region : regions) {
            JsonObject r = new JsonObject();
            r.addProperty("序号", region.index());
            r.addProperty("作物", region.cropName());
            r.addProperty("作物键", region.cropKey());
            r.addProperty("维度", region.dimension());
            r.addProperty("x1", region.x1());
            r.addProperty("y1", region.y1());
            r.addProperty("z1", region.z1());
            r.addProperty("x2", region.x2());
            r.addProperty("y2", region.y2());
            r.addProperty("z2", region.z2());
            array.add(r);
        }
        obj.add("区域", array);
        return JsonFileStore.writeAtomic(file(serverKey), obj);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  查询
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 全部区域（按序号升序快照） */
    public synchronized List<Region> all() {
        List<Region> copy = new ArrayList<>(regions);
        copy.sort(java.util.Comparator.comparingInt(Region::index));
        return List.copyOf(copy);
    }

    /** 指定维度内的区域（建区 / 判定一律按「当前维度」） */
    public synchronized List<Region> inDimension(String dimension) {
        List<Region> result = new ArrayList<>();
        for (Region region : all()) {
            if (Objects.equals(region.dimension(), dimension)) result.add(region);
        }
        return result;
    }

    public synchronized int count() {
        return regions.size();
    }

    /** 该坐标所属区域；不在任何区域内返回 {@code null} */
    public synchronized Region at(BlockPos pos, String dimension) {
        if (pos == null) return null;
        for (Region region : all()) {
            if (Objects.equals(region.dimension(), dimension) && region.contains(pos)) return region;
        }
        return null;
    }

    /** 与给定矩形重叠的第一个区域；不重叠返回 {@code null} */
    public synchronized Region overlapping(Region candidate, String dimension) {
        for (Region region : all()) {
            if (!Objects.equals(region.dimension(), dimension)) continue;
            if (region.overlapCells(candidate) > 0) return region;
        }
        return null;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  增删
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 建区结果：成功时 {@code region} 非空，失败时 {@code failure} 是给玩家看的中文原因 */
    public record AddResult(Region region, String failure) {

        public boolean ok() {
            return region != null;
        }
    }

    /**
     * 新建区域：校验 → 落盘。
     *
     * <p>校验顺序与用户能看懂的顺序一致：两角同格 → 单边超限 → 数量上限 → 与已有区域重叠。
     * 越出农田由调用方先裁好再进来（裁剪需要农田起止点，属于模块层的事）。</p>
     */
    public synchronized AddResult add(String serverKey, String cropKey, String cropName, String dimension,
                                      BlockPos a, BlockPos b) {
        // 先确保内存里是这台服务器的档：否则数量上限与重叠判定会拿别人的数据来算
        load(serverKey);
        if (a.getX() == b.getX() && a.getZ() == b.getZ()) {
            return new AddResult(null, "两个角在同一格，圈不出范围");
        }
        int sizeX = Math.abs(a.getX() - b.getX()) + 1;
        int sizeZ = Math.abs(a.getZ() - b.getZ()) + 1;
        if (sizeX > MAX_SIDE || sizeZ > MAX_SIDE) {
            return new AddResult(null,
                "区域太大（单边上限 " + MAX_SIDE + " 格，这次是 " + sizeX + " × " + sizeZ + "）");
        }
        if (regions.size() >= MAX_REGIONS) {
            return new AddResult(null, "区域太多（上限 " + MAX_REGIONS + " 个），请先删除不用的");
        }
        Region candidate = new Region(nextIndex(), cropKey, cropName, dimension,
            a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ());
        Region hit = overlapping(candidate, dimension);
        if (hit != null) {
            return new AddResult(null,
                "与区域 " + hit.index() + "（" + hit.cropName() + "）重叠 " + hit.overlapCells(candidate) + " 格");
        }
        regions.add(candidate);
        save(serverKey);
        return new AddResult(candidate, null);
    }

    /** 删除指定序号；成功返回 true（该地块随即回到「未分区」，不再被种 / 收 / 浇 / 画） */
    public synchronized boolean remove(String serverKey, int index) {
        load(serverKey);
        boolean removed = regions.removeIf(region -> region.index() == index);
        if (removed) save(serverKey);
        return removed;
    }

    /** 清空全部区域 */
    public synchronized int clear(String serverKey) {
        load(serverKey);
        int count = regions.size();
        regions.clear();
        save(serverKey);
        return count;
    }

    /** 下一个序号：取当前最大序号 + 1（删除不重排，保证用户看到的号不乱跳） */
    private int nextIndex() {
        int max = 0;
        for (Region region : regions) max = Math.max(max, region.index());
        return max + 1;
    }

    /** 会话失效（切服 / 掉线）：清空内存视图，不写盘 */
    public synchronized void invalidate() {
        regions.clear();
        loadedServer = null;
    }

    private static String string(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || obj.get(key).isJsonNull()) return null;
        try {
            String value = obj.get(key).getAsString();
            return value == null || value.isBlank() ? null : value;
        } catch (Exception ignored) {
            return null;
        }
    }

    private static int intOf(JsonObject obj, String key, int fallback) {
        try {
            return obj.has(key) && obj.get(key).isJsonPrimitive() ? obj.get(key).getAsInt() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }
}
