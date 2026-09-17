package com.yiyiaddon.feature.mining.repository;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.mining.model.ConfigRecord;
import com.yiyiaddon.repository.JsonFileStore;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 自动挖矿「配置记录」存储：把整份设置按<b>服务器 / 单人存档</b>存成一份手动快照。
 *
 * <p><b>与「设置自动按服务器隔离」的区别</b>（两者并存，用户 2026-09-18 拍板）：</p>
 * <ul>
 *   <li>自动隔离是设置本身就分桶存放（{@code config/module-state.json} 的 {@code 服务器设置}），
 *       切服自动切换，玩家不需要做任何事；</li>
 *   <li>本类是玩家<b>主动按下</b>的那一份快照，用途是「改乱了回滚」：保存一次，之后无论怎么改
 *       （甚至切服回来），都能一键复原到保存那一刻。快照与自动分桶互不覆盖，复原只写回当前
 *       服务器的设置桶，快照文件本身不动。</li>
 * </ul>
 *
 * <p><b>落盘位置</b>：{@code config/yiyiaddon/mining/records/<文件名安全键>.json}，
 * 键取 {@code WorldIdentity.fileSafeServer()}（与点位文件同一套命名，多人按 {@code host:port}、
 * 单人按存档目录名）。<b>每台服务器 / 每个存档各一份文件</b>，因此复原不会把 A 服的记录带到 B 服。</p>
 *
 * <p><b>文件结构</b>（中文键，与本项目其它落盘文件风格一致）：</p>
 * <pre>
 * {
 *   "服务器键": "520mc.cc:25565",       // 逻辑键原文（多人 host:port / 单人 singleplayer:存档名）
 *   "保存时间": 1758123456789,          // 毫秒时间戳，用于界面上显示「保存于 …」
 *   "设置": { ...MiningSettings 全字段... }   // 直接复用 MiningSettings#save 的键，保证整份可回灌
 * }
 * </pre>
 *
 * <p>只做「读 / 写 / 列 / 删一份快照」，不掺任何业务判定：是否可保存、保存后播报什么、复原后如何
 * 下发男中音设置，全部由模块负责。</p>
 */
public final class MiningConfigRecordStore {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/mining-record");

    /** 记录目录（与点位目录同级，命名同族） */
    private static final Path RECORD_DIR =
        FabricLoader.getInstance().getConfigDir().resolve("yiyiaddon").resolve("mining").resolve("records");

    private static final String KEY_SERVER = "服务器键";
    private static final String KEY_SAVED_AT = "保存时间";
    private static final String KEY_SETTINGS = "设置";
    private static final String KEY_POINTS = "点位";

    private MiningConfigRecordStore() {
    }

    private static Path fileOf(String scopeKey) {
        return RECORD_DIR.resolve(scopeKey + ".json");
    }

    /** 本服记录是否存在 */
    public static boolean exists(String scopeKey) {
        return find(scopeKey) != null;
    }

    /** 读某条记录的元信息（不含设置本体）；没有记录、文件损坏或结构不符返回 {@code null} */
    public static ConfigRecord find(String scopeKey) {
        if (scopeKey == null || scopeKey.isBlank()) return null;
        return read(fileOf(scopeKey), scopeKey);
    }

    /**
     * 写入一份记录（覆盖旧记录）。
     *
     * @param scopeKey  文件名安全键（{@code WorldIdentity.fileSafeServer()}），空则不写
     * @param serverKey 逻辑服务器键（{@code WorldIdentity.server()}）：多人 {@code host:port}、
     *                  单人 {@code singleplayer:<存档目录名>}；它是列表与播报里的辨认依据
     * @param settings  设置对象（{@code MiningSettings#save} 的产物），内部深拷贝后写入
     * @param points    点位快照（{@code MiningPointStore#snapshot()} 的产物）：矿物箱 / 食物箱 / 挂机修复点
     * @param savedAt   保存时刻的毫秒时间戳，仅用于界面显示
     * @return 是否写盘成功
     */
    public static boolean write(String scopeKey, String serverKey, JsonObject settings, JsonObject points,
                                long savedAt) {
        if (scopeKey == null || scopeKey.isBlank() || settings == null) return false;
        JsonObject root = new JsonObject();
        root.addProperty(KEY_SERVER, serverKey == null ? scopeKey : serverKey);
        root.addProperty(KEY_SAVED_AT, savedAt);
        root.add(KEY_SETTINGS, settings.deepCopy());
        root.add(KEY_POINTS, points == null ? new JsonObject() : points.deepCopy());
        return JsonFileStore.writeAtomic(fileOf(scopeKey), root);
    }

    /**
     * 列出全部已保存的记录，按保存时间从新到旧。
     *
     * <p><b>只列文件</b>：目录里有什么就有什么，因此「没点过保存」的服务器不会出现在列表里
     * （用户 2026-09-18 明确要求）。读不动的文件（损坏 / 结构不符）直接跳过，不拿半条记录糊弄界面。</p>
     */
    public static List<ConfigRecord> list() {
        if (!Files.isDirectory(RECORD_DIR)) return List.of();
        List<ConfigRecord> records = new ArrayList<>();
        try (Stream<Path> files = Files.list(RECORD_DIR)) {
            for (Path file : files.toList()) {
                String name = file.getFileName().toString();
                if (!name.endsWith(".json")) continue;
                ConfigRecord record = read(file, name.substring(0, name.length() - ".json".length()));
                if (record != null) records.add(record);
            }
        } catch (IOException error) {
            LOGGER.warn("读取自动挖矿配置记录目录失败：{}", RECORD_DIR, error);
            return List.of();
        }
        records.sort(Comparator.comparingLong(ConfigRecord::savedAt).reversed());
        return List.copyOf(records);
    }

    /** 删除一条记录 */
    public static boolean delete(String scopeKey) {
        if (scopeKey == null || scopeKey.isBlank()) return false;
        try {
            return Files.deleteIfExists(fileOf(scopeKey));
        } catch (IOException error) {
            LOGGER.warn("删除自动挖矿配置记录失败：{}", scopeKey, error);
            return false;
        }
    }

    /** 读一条记录的元信息；文件损坏 / 没有设置本体时返回 {@code null} */
    private static ConfigRecord read(Path file, String scopeKey) {
        JsonObject root = JsonFileStore.readJson(file);
        if (root == null || !root.has(KEY_SETTINGS) || !root.get(KEY_SETTINGS).isJsonObject()) return null;
        JsonObject points = asObject(root.get(KEY_POINTS));
        return new ConfigRecord(scopeKey, stringOf(root, KEY_SERVER, scopeKey),
            longOf(root, KEY_SAVED_AT), points == null ? 0 : points.size());
    }

    private static JsonObject asObject(JsonElement element) {
        return element != null && element.isJsonObject() ? element.getAsJsonObject() : null;
    }

    private static String stringOf(JsonObject object, String key, String fallback) {
        JsonElement value = object.get(key);
        if (value == null || !value.isJsonPrimitive()) return fallback;
        try {
            String text = value.getAsString();
            return text.isBlank() ? fallback : text;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static long longOf(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value == null || !value.isJsonPrimitive()) return 0L;
        try {
            return value.getAsLong();
        } catch (Exception ignored) {
            return 0L;
        }
    }

    /**
     * 读记录里的点位快照；没有记录或结构不符返回 {@code null}。
     *
     * <p>{@code null} 与「空对象」在读取侧同义：都表示这份记录里没有点位（{@link MiningPointStore#replaceAll}
     * 会据此把当前点位全部解绑），不拿当前点位凑数。</p>
     */
    public static JsonObject readPoints(String scopeKey) {
        if (scopeKey == null || scopeKey.isBlank()) return null;
        JsonObject root = JsonFileStore.readJson(fileOf(scopeKey));
        return root == null ? null : asObject(root.get(KEY_POINTS));
    }

    /**
     * 读记录里的设置对象；没有记录、文件损坏或结构不符时返回 {@code null}
     * （调用方据此判定「本服还没有记录」，不要用空对象冒充）。
     */
    public static JsonObject readSettings(String scopeKey) {
        if (scopeKey == null || scopeKey.isBlank()) return null;
        JsonObject root = JsonFileStore.readJson(fileOf(scopeKey));
        if (root == null) return null;
        JsonElement settings = root.get(KEY_SETTINGS);
        return settings != null && settings.isJsonObject() ? settings.getAsJsonObject() : null;
    }

    /** 记录的保存时间（毫秒）；没有记录或字段缺失返回 {@code 0} */
    public static long savedAt(String scopeKey) {
        ConfigRecord record = find(scopeKey);
        return record == null ? 0L : record.savedAt();
    }
}
