package com.yiyiaddon.feature.enchant.repository;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.repository.JsonFileStore;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * 附魔点位存储：9 类点位<b>每类一条</b>，外加挂机视角、铁砧朝向与「点位归属的世界」三项附加数据。
 *
 * <p><b>与旧项目一致的口径</b>（{@code AutoEnchantBook:350-363}、{@code toTag:1621-1639}）：</p>
 * <ul>
 *   <li>落盘键逐字沿用旧项目：{@code posBook / posLapis / posOutput / posEnchant / posGrindstone /
 *       posHangout / posAnvil / posAnvilBox / posEquipment}（挂机点是 {@code posHangout}，不是 posAfk），
 *       加 {@code hangoutYaw / hangoutPitch / posAnvilFacing / pointServer / pointDimension}。</li>
 *   <li>同一份点位只归属<b>一个服务器的一个维度</b>：文件内记 {@code pointServer / pointDimension}，
 *       换维度后点位不生效（{@link #matchesCurrentContext()}），切回原维度可直接用。</li>
 * </ul>
 *
 * <p><b>服务器隔离（用户 2026-09-21 定稿）</b>：文件名取 {@link WorldIdentity#fileSafeServer()}，
 * A 服 / B 服 / 单人存档<b>各一份文件</b>，换服直接设点、不再需要先 {@code .fumo 清空}
 * （与挖矿 {@code MiningPointStore} 同一口径）。内存里记着 {@code loadedServer}，
 * 换服后首次读写会重读本服文件，{@link #save()} 里还有一道「不属于当前服务器就拒绝写盘」的兜底。</p>
 *
 * <p><b>旧档迁移</b>：旧版单份文件 {@code enchant/points.json}（文件内已带 {@code pointServer}）在目标
 * 文件不存在时按归属复制过来，玩家不必重设；属于别的服务器的旧档不动，等切回那台服务器时自然认领。</p>
 *
 * <p><b>有意差异</b>：旧项目 {@code currentServer()} 取服务器 ip 小写（单人写死 {@code "singleplayer"}），
 * 本项目统一走 {@link WorldIdentity#server()}（多人 {@code host:port} 规范化、单人
 * {@code singleplayer:<存档名>}，第 45 条「禁止另建第二套隔离口径」）。因两侧取值不同，
 * <b>旧项目（{@code D:/mcaddon/26.1.2}）的附魔点位不做自动迁移</b>，需玩家重新绑定，已登记进差异清单。</p>
 */
public final class EnchantPointStore {

    /** 点位目录：每服一份（与挖矿 {@code mining/points} 同一命名口径） */
    private static final Path CONFIG_DIR =
        FabricLoader.getInstance().getConfigDir().resolve("yiyiaddon").resolve("enchant").resolve("points");

    /** 旧版单份点位文件（{@code enchant/points.json}），仅迁移时读，不删不改 */
    private static final Path LEGACY_FILE =
        FabricLoader.getInstance().getConfigDir().resolve("yiyiaddon").resolve("enchant").resolve("points.json");

    /** 每类型一条 */
    private final Map<EnchantPointType, EnchantPoint> points = new EnumMap<>(EnchantPointType.class);

    private String pointServer;
    private String pointDimension;

    /** 最近一次装载（读盘）时的文件名安全服务器键；null = 尚未装载 */
    private String loadedServer;

    /** 挂机视角（旧 {@code hangoutYaw / hangoutPitch}） */
    private Float hangoutYaw;
    private Float hangoutPitch;

    /** 铁砧朝向名（旧 {@code posAnvilFacing}，{@code Direction.getName()}） */
    private String anvilFacing;

    /** 是否已读盘；写操作前若未读盘会先读一次，避免整份写盘抹掉磁盘上的绑定 */
    private boolean loaded;

    /** 当前服务器对应的点位文件（文件名安全键取 {@link WorldIdentity#fileSafeServer()}） */
    private Path dataFile() {
        return CONFIG_DIR.resolve(WorldIdentity.fileSafeServer() + ".json");
    }

    /** 从磁盘重读本服文件；覆盖内存中的全部点位与附加数据 */
    public void reload() {
        points.clear();
        pointServer = null;
        pointDimension = null;
        hangoutYaw = null;
        hangoutPitch = null;
        anvilFacing = null;
        loaded = true;
        loadedServer = WorldIdentity.fileSafeServer();

        Path file = dataFile();
        migrateLegacyFile(file);
        JsonObject root = JsonFileStore.readJson(file);
        if (root == null) return;

        for (EnchantPointType type : EnchantPointType.values()) {
            JsonElement element = root.get(type.posKey());
            EnchantPoint point = readPoint(element);
            if (point != null) points.put(type, point);
        }
        hangoutYaw = floatOrNull(root, "hangoutYaw");
        hangoutPitch = floatOrNull(root, "hangoutPitch");
        anvilFacing = stringOrNull(root, "posAnvilFacing");
        pointServer = stringOrNull(root, "pointServer");
        pointDimension = stringOrNull(root, "pointDimension");
    }

    /** 会话失效：清内存并标记未读盘（下次读写会重新读盘） */
    public void invalidate() {
        points.clear();
        pointServer = null;
        pointDimension = null;
        hangoutYaw = null;
        hangoutPitch = null;
        anvilFacing = null;
        loaded = false;
        loadedServer = null;
    }

    /**
     * 读路径自愈：没读盘过就先读一次。
     *
     * <p><b>这里刻意不比对服务器键</b>：点位渲染每帧都要取点位（{@code EnchantPointRenderer}），
     * 每次都算一遍服务器键等于给渲染热路径加字符串开销。换服后的读数据由四条入口的
     * {@link #reload()} 保证最新（模块启用 / 启动自检 / 打开控制台 / {@code .fumo} 指令）；
     * 万一内存里还是上一台服务器的快照，{@link #matchesCurrentContext()} 会判「不匹配」，
     * 方向也是安全的（模块停机，而不是拿着旧坐标乱跑）。</p>
     */
    private void ensureLoaded() {
        if (!loaded) reload();
    }

    /**
     * 写路径自愈：没读盘过、或内存里装的是别的服务器，先按当前服务器重读再改。
     *
     * <p>写操作少（都是玩家动作），这里必须比对服务器键：不换的话会拿着上一台服务器的内存快照
     * 整份写盘（{@link #save()} 的兜底会直接拒掉，表现为「设置了没反应」），
     * 或者把另一台服务器的点位当成自己的覆盖保护依据。</p>
     */
    private void ensureLoadedForWrite() {
        if (loaded && Objects.equals(loadedServer, WorldIdentity.fileSafeServer())) return;
        reload();
    }

    /**
     * 首次使用新隔离键时复制旧版单份点位，玩家不必重设。
     *
     * <p>只有三种情况会复制：目标文件不存在、旧文件里有至少一个点位、旧点位归属当前服务器
     * （旧档没记归属时按当前服务器认领）。属于别的服务器的旧档保持原样，等玩家切回那台服务器时再认领。</p>
     */
    private void migrateLegacyFile(Path target) {
        if (Files.exists(target)) return;
        JsonObject legacy = JsonFileStore.readJson(LEGACY_FILE);
        if (legacy == null || !hasAnyLegacyPoint(legacy)) return;

        String current = WorldIdentity.server();
        String legacyServer = stringOrNull(legacy, "pointServer");
        if (legacyServer != null && !legacyServer.equals(current)) return;

        // 旧档没有归属记录：按当前世界补齐（看不到世界身份时不动，等进世界后下次读盘再迁移）
        if (legacyServer == null) {
            if (current == null || current.isBlank() || current.endsWith(":unknown")) return;
            legacy.addProperty("pointServer", current);
            String dimension = WorldIdentity.dimension();
            if (legacy.get("pointDimension") == null && dimension != null && !dimension.isBlank()) {
                legacy.addProperty("pointDimension", dimension);
            }
        }
        JsonFileStore.writeAtomic(target, legacy);
    }

    /** 旧文件里是否至少有一个点位（只有附加数据的空档不迁移，避免凭空造一个空点位文件） */
    private static boolean hasAnyLegacyPoint(JsonObject root) {
        for (EnchantPointType type : EnchantPointType.values()) {
            JsonElement element = root.get(type.posKey());
            if (element != null && element.isJsonObject()) return true;
        }
        return false;
    }

    // ── 点位 ──

    /** 取点位；未绑定返回 {@code null} */
    public EnchantPoint get(EnchantPointType type) {
        ensureLoaded();
        return type == null ? null : points.get(type);
    }

    /** 该点位是否已绑定 */
    public boolean has(EnchantPointType type) {
        return get(type) != null;
    }

    /**
     * 写动作前的装载保证：内存里不是当前服务器的点位就先重读。
     *
     * <p>「已设置」的覆盖保护、{@code .fumo 清空} 的清空范围、点位归属世界的判定都读内存快照，
     * 因此绑定 / 移除 / 清空三条动作在动判定之前先过这里 —— 否则换服后第一次操作会拿着上一台服务器的
     * 点位下结论（表现为「明明没设过，却提示已设置」或提示点位属于其他服务器）。</p>
     */
    public void ensureCurrentServer() {
        ensureLoadedForWrite();
    }

    /** 已绑定数量 */
    public int size() {
        ensureLoaded();
        return points.size();
    }

    /** 写入点位并立即落盘（覆盖保护由绑定服务在写入前拦，本类不做策略判定） */
    public void bind(EnchantPointType type, EnchantPoint point) {
        if (type == null || point == null) return;
        ensureLoadedForWrite();
        points.put(type, point);
        save();
    }

    /**
     * 删除单个点位并立即落盘。
     *
     * @return 是否真的删掉了
     */
    public boolean unbind(EnchantPointType type) {
        if (type == null) return false;
        ensureLoadedForWrite();
        if (points.remove(type) == null) return false;
        save();
        return true;
    }

    /**
     * 清空全部点位 + 挂机视角 + 铁砧朝向 + 服务器维度绑定（{@code .fumo 清空} 的语义，
     * 旧项目 {@code clearPoints:1701-1716} 清的就是这 14 项）。
     *
     * @return 被清掉的点位数
     */
    public int clearAll() {
        ensureLoadedForWrite();
        int removed = points.size();
        points.clear();
        hangoutYaw = null;
        hangoutPitch = null;
        anvilFacing = null;
        pointServer = null;
        pointDimension = null;
        save();
        return removed;
    }

    // ── 附加数据 ──

    /** 挂机视角 yaw；未记录返回 {@code null} */
    public Float hangoutYaw() {
        ensureLoaded();
        return hangoutYaw;
    }

    /** 挂机视角 pitch；未记录返回 {@code null} */
    public Float hangoutPitch() {
        ensureLoaded();
        return hangoutPitch;
    }

    /** 记录挂机视角（绑定挂机点时调用，旧项目 {@code FumoCommand:115-118}） */
    public void setHangoutView(float yaw, float pitch) {
        ensureLoadedForWrite();
        hangoutYaw = yaw;
        hangoutPitch = pitch;
        save();
    }

    /** 铁砧朝向名；未记录返回 {@code null} */
    public String anvilFacing() {
        ensureLoaded();
        return anvilFacing;
    }

    /** 记录铁砧朝向（绑定铁砧时调用，旧项目 {@code FumoCommand:119-122}） */
    public void setAnvilFacing(String facing) {
        ensureLoadedForWrite();
        anvilFacing = facing;
        save();
    }

    /**
     * 清除挂机视角。
     *
     * <p>旧项目 {@code .fumo 移除 挂机位}（{@code FumoCommand:143-146}）会连带清掉
     * {@code hangoutYaw / hangoutPitch}，与点位同时失效，故此处单独提供入口。</p>
     */
    public void clearHangoutView() {
        ensureLoadedForWrite();
        hangoutYaw = null;
        hangoutPitch = null;
        save();
    }

    /** 清除铁砧朝向（旧项目 {@code .fumo 移除 铁砧}，{@code FumoCommand:147-149}） */
    public void clearAnvilFacing() {
        ensureLoadedForWrite();
        anvilFacing = null;
        save();
    }

    // ── 归属世界 ──

    /** 点位所属服务器键；未绑定过返回 {@code null} */
    public String pointServer() {
        ensureLoaded();
        return pointServer;
    }

    /** 点位所属维度标识；未绑定过返回 {@code null} */
    public String pointDimension() {
        ensureLoaded();
        return pointDimension;
    }

    /** 记录点位归属世界（首次绑定某个点位时调用） */
    public void bindContext(String server, String dimension) {
        ensureLoadedForWrite();
        pointServer = server;
        pointDimension = dimension;
        save();
    }

    /**
     * 当前世界是否与点位归属一致。
     *
     * <p>旧项目 {@code matchesCurrentPointContext()}（{@code :1691-1693}）的等价物；
     * 点位没有归属记录（旧版点位）时返回 {@code false}，由调用方给出「请先清空后重新设置」的提示。</p>
     */
    public boolean matchesCurrentContext() {
        ensureLoaded();
        if (pointServer == null || pointDimension == null) return false;
        return Objects.equals(pointServer, WorldIdentity.server())
            && Objects.equals(pointDimension, WorldIdentity.dimension());
    }

    /** 是否存在任一绑定点位（旧 {@code hasAnyPosition()}，{@code :1696-1699}） */
    public boolean hasAnyPoint() {
        ensureLoaded();
        return !points.isEmpty();
    }

    // ── 持久化 ──

    private void save() {
        if (!loaded) return;
        // 兜底：内存快照不属于当前服务器时拒绝写盘，宁可少写一次也不污染另一个服务器的点位文件
        if (loadedServer == null || !loadedServer.equals(WorldIdentity.fileSafeServer())) return;
        JsonObject root = new JsonObject();
        for (EnchantPointType type : EnchantPointType.values()) {
            EnchantPoint point = points.get(type);
            if (point == null) continue;
            JsonObject entry = new JsonObject();
            entry.addProperty("x", point.x());
            entry.addProperty("y", point.y());
            entry.addProperty("z", point.z());
            root.add(type.posKey(), entry);
        }
        if (hangoutYaw != null) root.addProperty("hangoutYaw", hangoutYaw);
        if (hangoutPitch != null) root.addProperty("hangoutPitch", hangoutPitch);
        if (anvilFacing != null) root.addProperty("posAnvilFacing", anvilFacing);
        if (pointServer != null) root.addProperty("pointServer", pointServer);
        if (pointDimension != null) root.addProperty("pointDimension", pointDimension);
        JsonFileStore.writeAtomic(dataFile(), root);
    }

    private static EnchantPoint readPoint(JsonElement element) {
        if (element == null || !element.isJsonObject()) return null;
        JsonObject obj = element.getAsJsonObject();
        return new EnchantPoint(intOf(obj, "x"), intOf(obj, "y"), intOf(obj, "z"));
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

    private static Float floatOrNull(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        if (element == null || element.isJsonNull()) return null;
        try {
            return element.getAsFloat();
        } catch (Exception ignored) {
            return null;
        }
    }

    private static String stringOrNull(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        if (element == null || element.isJsonNull()) return null;
        try {
            String value = element.getAsString();
            return value.isBlank() ? null : value;
        } catch (Exception ignored) {
            return null;
        }
    }
}
