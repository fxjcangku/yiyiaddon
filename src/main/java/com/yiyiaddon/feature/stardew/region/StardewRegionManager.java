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
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 种植区域（分区种植）数据与落盘：一个服务器一份，按 {@code ServerKey} 隔离。
 *
 * <p><b>这一层是什么：</b>「分区种植」的数据底座，也是农田范围的唯一来源。每个区域是一个 XZ 矩形，
 * 分两类：<b>单一作物区</b>（绑定一种作物）与<b>混种区</b>（不绑作物，地里的空盆按后勤缺口
 * 从已勾选作物里挑一种种）。落盘走与 {@code StardewPointManager} 同一套做法
 * （目录 {@code run/StardewFarm/regions/<服务器>.json}、原子写、损坏文件返回空且不覆盖）。</p>
 *
 * <p><b>只比 XZ：</b>区域是一个平面矩形，判定成员 / 重叠 / 覆盖一律只比水平坐标，
 * 与洒水器「只比 XZ 不比高度」同口径（盆与作物常常不在同一格高度上）。</p>
 */
public final class StardewRegionManager {

    /** 区域数量上限：防止误操作圈出几十个区域把列表与扫描拖垮 */
    public static final int MAX_REGIONS = 32;
    /** 单边长度上限（格）：防止误点圈下整张图 */
    public static final int MAX_SIDE = 64;
    /** 混种区的作物键：它不是一个真实作物，任何真实作物键都不会等于它 */
    public static final String MIXED_KEY = "mixed";
    /** 混种区的显示名 */
    public static final String MIXED_NAME = "混种";

    /**
     * 一个种植区域：对角两点 + 绑定作物；{@code index} 是用户看到的「区域 N」，删除后不重排。
     *
     * <p>{@code mixed} 为 true 时 {@code cropKey} 恒为 {@link #MIXED_KEY}、{@code cropName} 恒为
     * 「混种」，两者都只用于显示与落盘，判定一律先看 {@link #mixed()}。</p>
     */
    public record Region(int index, boolean mixed, String cropKey, String cropName, String dimension,
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
    /**
     * 内存是否与磁盘一致（本次装载是否真的读成功）。
     *
     * <p><b>为什么必须有这个旗标：</b>{@link #load(String)} 在「文件损坏 / 服务器字段不符 / 结构缺失」
     * 时只把内存清空并 {@code return}，但 {@code loadedServer} 已经指向这台服务器。此后任何一次写入
     * （建区 / 换作物 / 删除）都会拿这份空内存整档覆盖磁盘 —— 玩家一格格点出来的区域就不可逆地没了。
     * 因此装载失败时禁止写盘：宁可这一笔操作不生效，也不能覆盖读不出来的旧档。</p>
     */
    private boolean writable;

    public StardewRegionManager() {
        this.root = Minecraft.getInstance().gameDirectory.toPath().resolve("StardewFarm").resolve("regions");
    }

    private Path file(String serverKey) {
        String safe = serverKey.replaceAll("[\\\\/:*?\"<>|]", "_");
        return root.resolve(safe + ".json");
    }

    /** 当前内存是否可安全写回磁盘（装载成功过才为 true） */
    public synchronized boolean writable() {
        return writable;
    }

    /** 加载指定服务器的区域到内存；与已加载服务器相同则跳过 */
    public synchronized void load(String serverKey) {
        if (serverKey == null || serverKey.equals(loadedServer)) return;
        loadedServer = serverKey;
        writable = false;
        regions.clear();

        Path file = file(serverKey);
        if (!Files.isRegularFile(file)) {
            // 还没有档：全新服务器，允许写入
            writable = true;
            return;
        }
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
            writable = true;
        } catch (Exception ignored) {
            // 损坏文件：保持不可写，避免下一次保存把旧档覆盖成空
        }
    }

    private static Region parse(JsonObject r) {
        try {
            boolean mixed = boolOf(r, "混种", false);
            String cropKey = mixed ? MIXED_KEY : string(r, "作物键");
            String cropName = mixed ? MIXED_NAME : string(r, "作物");
            if (cropKey == null || cropName == null) return null;
            return new Region(intOf(r, "序号", 0), mixed, cropKey, cropName, string(r, "维度"),
                intOf(r, "x1", 0), intOf(r, "y1", 0), intOf(r, "z1", 0),
                intOf(r, "x2", 0), intOf(r, "y2", 0), intOf(r, "z2", 0));
        } catch (Exception ignored) {
            return null;
        }
    }

    /** 保存当前内存区域（原子写入 + 覆盖前留一份 .bak；装载失败时拒绝写入） */
    public synchronized boolean save(String serverKey) {
        if (serverKey == null || !serverKey.equals(loadedServer)) return false;
        // 装载没成功就不许写：否则会把读不出来的旧档整档覆盖成空
        if (!writable) return false;
        JsonObject obj = new JsonObject();
        obj.addProperty("服务器", serverKey);
        obj.addProperty("资源指纹", com.yiyiaddon.service.resourcepack.ResourceExtractionService.fingerprint());
        JsonArray array = new JsonArray();
        for (Region region : regions) {
            JsonObject r = new JsonObject();
            r.addProperty("序号", region.index());
            r.addProperty("混种", region.mixed());
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
        Path file = file(serverKey);
        backup(file);
        return JsonFileStore.writeAtomic(file, obj);
    }

    /**
     * 覆盖前把旧档复制成 {@code <名字>.json.bak}。
     *
     * <p>区域是玩家一格格点出来的，一旦被误覆盖无法还原；多留一份备份的代价只是几 KB。</p>
     */
    private static void backup(Path file) {
        if (!Files.isRegularFile(file)) return;
        try {
            Files.copy(file, file.resolveSibling(file.getFileName() + ".bak"),
                StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ignored) {
            // 备份失败不阻断主写入
        }
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
     * <p>校验顺序与用户能看懂的顺序一致：两角同格 → 单边超限 → 数量上限 → 与已有区域重叠。</p>
     *
     * @param cropKey 单一作物区的作物键；{@code null} 或 {@link #MIXED_KEY} 表示混种区（忽略作物名）
     */
    public synchronized AddResult add(String serverKey, String cropKey, String cropName, String dimension,
                                      BlockPos a, BlockPos b) {
        // 先确保内存里是这台服务器的档：否则数量上限与重叠判定会拿别人的数据来算
        load(serverKey);
        if (!writable) {
            return new AddResult(null, "区域档读取失败，已暂停写入以免覆盖旧数据：" + file(serverKey).getFileName());
        }
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
        boolean mixed = cropKey == null || MIXED_KEY.equals(cropKey);
        Region candidate = new Region(nextIndex(), mixed,
            mixed ? MIXED_KEY : cropKey, mixed ? MIXED_NAME : cropName, dimension,
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
        if (!writable) return false;
        boolean removed = regions.removeIf(region -> region.index() == index);
        if (removed) save(serverKey);
        return removed;
    }

    /**
     * 换掉指定序号区域绑定的作物：范围 / 序号 / 维度一律不动，只换品种并落盘。
     *
     * <p><b>为什么要它：</b>实机反馈「想把某块地换个作物，只能删了重新圈」——区域是玩家用手
     * 一格一格点出来的，删掉重划要重来一遍；而这里要改的只是「这块地该长什么」，与范围无关。</p>
     *
     * <p>混种区可以被换成具体作物（换完就是单一作物区）；反过来把作物区改成混种不在这里做——
     * 那由圈地时的「混种」入口决定（{@link #MIXED_KEY} 会被拒绝）。</p>
     *
     * @return 成功（已找到该序号并落盘）返回 true
     */
    public synchronized boolean rebind(String serverKey, int index, String cropKey, String cropName) {
        if (cropKey == null || cropKey.isBlank() || MIXED_KEY.equals(cropKey)
            || cropName == null || cropName.isBlank()) {
            return false;
        }
        load(serverKey);
        if (!writable) return false;
        for (int i = 0; i < regions.size(); i++) {
            Region region = regions.get(i);
            if (region.index() != index) continue;
            regions.set(i, new Region(region.index(), false, cropKey, cropName, region.dimension(),
                region.x1(), region.y1(), region.z1(), region.x2(), region.y2(), region.z2()));
            save(serverKey);
            return true;
        }
        return false;
    }

    /** 清空全部区域 */
    public synchronized int clear(String serverKey) {
        load(serverKey);
        if (!writable) return 0;
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
        writable = false;
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

    private static boolean boolOf(JsonObject obj, String key, boolean fallback) {
        try {
            return obj.has(key) && obj.get(key).isJsonPrimitive() ? obj.get(key).getAsBoolean() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }
}
