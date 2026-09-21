package com.yiyiaddon.feature.mining.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.repository.JsonFileStore;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;

/**
 * 三点点位存储：矿物箱 / 食物箱 / 挂机修复点，<b>每类型一条</b>（旧项目 {@code WKCommand} 的
 * {@code WKData} 三字段，无维度隔离）。
 *
 * <p><b>与旧项目一致的口径：</b></p>
 * <ul>
 *   <li>一个类型全局只有一条坐标，绑过即「已绑定」，重设必须先删除（唯一性只按<b>类型</b>判，
 *       由 {@code MiningBindingService} 的覆盖保护执行）。</li>
 * </ul>
 *
 * <p><b>有意差异（用户 2026-09-16 裁定，去掉旧项目的两道校验）：</b>旧
 * {@code WKCommand.validateBinding}（{@code :378-446}）除了类型唯一，还有「任一点位维度与当前维度
 * 不一致即拒」与「三点位两两距离不得超过 32 格」。两道<b>都已去掉</b>，因为三个点位允许各在不同维度
 * （下界食物箱 + 主世界矿物箱 + 末地挂机修复点）：跨维度拒绝会让这套用法不可配，而跨维度之后
 * 「两两 32 格」对不在同一世界的点位没有意义。点位上仍记录当时所在维度，用于显示与回执。</p>
 *
 * <p><b>服务器隔离</b>（旧项目 {@code getServerIdentifier()} 的等价物）：文件名取
 * {@link WorldIdentity#fileSafeServer()}，A 服与 B 服各一份文件；内存里记着 {@code loadedServer}，
 * 装载上下文与当前服务器不一致时先重读再写，绝不让 A 服的点位写进 B 服的文件
 * （{@link #save()} 里还有一道拒绝写盘的兜底）。<b>这一层与维度无关</b>，是「换服不串档」。</p>
 *
 * <p><b>落盘结构</b>：顶层键 {@code mineral / food / afk}，值为单个坐标对象，键名与字段名沿用旧项目
 * {@code saveData} / {@code parseJson}：
 * <pre>
 * { "mineral": {"x":..,"y":..,"z":..,"dimension":"..","yaw":..,"pitch":..}, "food":{...}, "afk":{...} }
 * </pre>
 * 读取时对数组写法做兼容（曾存在过的旧档：[第一项] 生效），不做任何写回迁移。</p>
 *
 * <p><b>兼容差异</b>：早期档里 {@code dimension} 存的是
 * {@code mc.level.dimension().toString()}，即 {@code ResourceKey[minecraft:dimension/minecraft:overworld]}
 * 包装格式；本类读入时<b>剥壳归一</b>成 {@code minecraft:overworld}（{@link #normalizeDimension}），
 * 使早期点位立即可用，不要求玩家重新绑点。</p>
 *
 * <p>旧项目点位目录（{@code config/yiyiaddon/wk}）<b>不再读取</b>：新项目只认自己的
 * {@code yiyiaddon/mining/points}，从旧版本升级需重新标点。</p>
 */
public final class MiningPointStore {

    /** 点位目录（旧项目为 {@code config/yiyiaddon/wk}，本项目统一到 mining/points） */
    private static final Path CONFIG_DIR =
        FabricLoader.getInstance().getConfigDir().resolve("yiyiaddon").resolve("mining").resolve("points");

    /** 每类型一条（旧 {@code WKData.mineral / food / afk} 三个字段） */
    private final Map<MiningPointType, MiningPoint> points = new EnumMap<>(MiningPointType.class);

    /** 最近一次装载（读盘）时的服务器键；null = 尚未装载，写盘前必须先重读 */
    private String loadedServer;

    private Path dataFile() {
        return CONFIG_DIR.resolve(WorldIdentity.fileSafeServer() + ".json");
    }

    /**
     * 按当前服务器重读点位；切换服务器必须调用。
     *
     * <p>先清空内存再读盘：不清空会把上一个服务器的点位带到新服务器。</p>
     */
    public void reload() {
        points.clear();
        loadedServer = WorldIdentity.fileSafeServer();
        Path file = dataFile();
        JsonObject root = JsonFileStore.readJson(file);
        if (root == null) return;
        for (MiningPointType type : MiningPointType.values()) {
            JsonElement element = root.get(type.node());
            if (element == null) continue;
            MiningPoint point = readPoint(element);
            if (point != null) points.put(type, point);
        }
    }

    /**
     * 读一个点位节点。
     *
     * <p>正常档是单个对象；数组写法是中途出现过的一版格式，取第一项（旧语义本就只有一条）。</p>
     */
    private static MiningPoint readPoint(JsonElement element) {
        if (element.isJsonObject()) return parse(element.getAsJsonObject());
        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            for (JsonElement item : array) {
                if (item.isJsonObject()) return parse(item.getAsJsonObject());
            }
        }
        return null;
    }

    /**
     * 写入口自愈：内存没装载过、或装载的是别的服务器，先按当前服务器重读再改。
     *
     * <p>没有这一步，{@code .wk} 指令这类从不主动重读的入口会在内存为空时改一个空表然后整份写盘，
     * 把磁盘上原有的绑定抹掉；换服务器后更是会把上一个服务器的点位写进这台服务器的文件。</p>
     */
    private void ensureLoaded() {
        if (WorldIdentity.fileSafeServer().equals(loadedServer)) return;
        reload();
    }

    /** 取点位；本类型没绑返回 {@code null} */
    public MiningPoint get(MiningPointType type) {
        return type == null ? null : points.get(type);
    }

    /** 该类型是否已绑定 */
    public boolean has(MiningPointType type) {
        return type != null && points.containsKey(type);
    }

    /** 写入点位并立即落盘（覆盖旧值；覆盖保护由 {@code MiningBindingService} 在写入前拦） */
    public void set(MiningPointType type, MiningPoint point) {
        if (type == null || point == null) return;
        ensureLoaded();
        points.put(type, point);
        save();
    }

    /**
     * 删除点位并立即落盘。
     *
     * @return 是否真的删掉了
     */
    public boolean remove(MiningPointType type) {
        if (type == null) return false;
        ensureLoaded();
        if (points.remove(type) == null) return false;
        save();
        return true;
    }

    /** 清空全部点位；返回被清掉的条数（{@code .wk 清空} 的语义） */
    public int clearAll() {
        ensureLoaded();
        int removed = points.size();
        points.clear();
        save();
        return removed;
    }

    /** 已绑定的条数（{@code .wk 状态} 的「N / 3 已绑定」与 {@code .wk 清空} 的判空口径） */
    public int size() {
        return points.size();
    }

    /** 会话失效：清内存并作废装载上下文（下次读 / 写会重新按当前服务器读盘） */
    public void invalidate() {
        points.clear();
        loadedServer = null;
    }

    // ── 配置记录用：整份点位快照 / 整体替换 ──

    /**
     * 当前三点位的整份快照（键名与点位文件一致：{@code mineral / food / afk}）。
     *
     * <p>「一键保存全部配置」要把点位与设置一起存下来（用户 2026-09-18：「包括设置 跟坐标点位懂吗」）。
     * 取快照只读内存，不落盘、不改任何状态。</p>
     */
    public JsonObject snapshot() {
        JsonObject root = new JsonObject();
        for (MiningPointType type : MiningPointType.values()) {
            MiningPoint point = points.get(type);
            if (point == null) continue;
            root.add(type.node(), writePoint(point));
        }
        return root;
    }

    /**
     * 用一份快照<b>整体替换</b>当前服务器的点位并立即落盘（「配置记录」的读取 / 导入）。
     *
     * <p>语义是整体替换而不是合并：记录里没有的类型就是未绑定 —— 否则「读取了 A 服的记录，
     * 结果还留着本服半张点位表」无法解释。写盘走 {@link #save()}，与点位的服务器守卫同一口径
     * （内存快照不属于当前服务器时拒绝落盘）。</p>
     *
     * @param snapshot 点位快照；{@code null} 视为空快照（全部解绑）
     * @return 替换后已绑定的条数（0 ~ 3），供回执显示
     */
    public int replaceAll(JsonObject snapshot) {
        ensureLoaded();
        points.clear();
        points.putAll(parseSnapshot(snapshot));
        save();
        return points.size();
    }

    /**
     * 解析一份点位快照（键名 {@code mineral / food / afk}）为点位表。
     *
     * <p>与 {@link #reload()} 用同一套读取口径（含数组旧写法兼容、维度剥壳），
     * 「读取记录」与「详情窗显示坐标」共用它，避免两处各写一遍解析。</p>
     *
     * @param snapshot 快照；{@code null} 返回空表
     */
    public static Map<MiningPointType, MiningPoint> parseSnapshot(JsonObject snapshot) {
        Map<MiningPointType, MiningPoint> parsed = new EnumMap<>(MiningPointType.class);
        if (snapshot == null) return parsed;
        for (MiningPointType type : MiningPointType.values()) {
            JsonElement element = snapshot.get(type.node());
            if (element == null) continue;
            MiningPoint point = readPoint(element);
            if (point != null) parsed.put(type, point);
        }
        return parsed;
    }

    // ── 持久化（键名与字段名沿用旧项目 WKCommand.saveData / parseJson） ──

    private void save() {
        // 兜底：内存快照不属于当前服务器时拒绝写盘，宁可少写一次也不污染另一个服务器的文件
        if (loadedServer == null || !loadedServer.equals(WorldIdentity.fileSafeServer())) return;
        JsonObject root = new JsonObject();
        for (MiningPointType type : MiningPointType.values()) {
            MiningPoint point = points.get(type);
            if (point == null) continue;
            root.add(type.node(), writePoint(point));
        }
        JsonFileStore.writeAtomic(dataFile(), root);
    }

    private static JsonObject writePoint(MiningPoint point) {
        JsonObject entry = new JsonObject();
        entry.addProperty("x", point.x());
        entry.addProperty("y", point.y());
        entry.addProperty("z", point.z());
        entry.addProperty("dimension", point.dimension() == null ? "" : point.dimension());
        entry.addProperty("yaw", point.yaw());
        entry.addProperty("pitch", point.pitch());
        return entry;
    }

    private static MiningPoint parse(JsonObject obj) {
        return new MiningPoint(
            intOf(obj, "x"),
            intOf(obj, "y"),
            intOf(obj, "z"),
            normalizeDimension(stringOf(obj, "dimension")),
            floatOf(obj, "yaw"),
            floatOf(obj, "pitch"));
    }

    /**
     * 归一化旧档维度写法：{@code ResourceKey[minecraft:dimension / minecraft:overworld]}
     * → {@code minecraft:overworld}。
     *
     * <p>旧项目 {@code WKCommand} 写盘用的是 {@code mc.level.dimension().toString()}（带壳写法），
     * 本项目统一口径是 {@code dimension().identifier().toString()}（第 45 条）。两种写法字符串不相等，
     * 不归一化会让旧档点位被 {@code inCurrentDimension()} 判成别的维度——ESP 不再画它、状态页与回执
     * 显示的维度名也不对。此处只做读取时的一次性剥壳，不认识的写法原样保留（不猜、不丢弃）。</p>
     */
    private static String normalizeDimension(String dimension) {
        if (dimension == null || dimension.isBlank()) return dimension;
        int slash = dimension.lastIndexOf('/');
        if (!dimension.startsWith("ResourceKey[") || slash < 0 || !dimension.endsWith("]")) return dimension;
        String bare = dimension.substring(slash + 1, dimension.length() - 1).trim();
        return bare.isEmpty() ? dimension : bare;
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

    private static float floatOf(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        if (element == null || element.isJsonNull()) return 0f;
        try {
            return element.getAsFloat();
        } catch (Exception ignored) {
            return 0f;
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
