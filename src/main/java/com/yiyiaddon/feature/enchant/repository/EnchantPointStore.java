package com.yiyiaddon.feature.enchant.repository;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.repository.JsonFileStore;
import net.fabricmc.loader.api.FabricLoader;

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
 *   <li><b>一套点位记录归属世界</b>：换服务器或换维度后点位不生效（{@link #matchesCurrentContext()}），
 *       切回原世界可直接用 —— 所以这里是<b>单份文件</b>，不按服务器分文件（与挖矿点位相反）。</li>
 * </ul>
 *
 * <p><b>有意差异</b>：旧项目 {@code currentServer()} 取服务器 ip 小写（单人写死 {@code "singleplayer"}），
 * 本项目统一走 {@link WorldIdentity#server()}（多人 {@code host:port} 规范化、单人
 * {@code singleplayer:<存档名>}，第 45 条「禁止另建第二套隔离口径」）。因两侧取值不同，
 * <b>旧项目附魔点位不做自动迁移</b>，需玩家重新绑定，已登记进差异清单。</p>
 */
public final class EnchantPointStore {

    /** 点位文件（旧项目点位存在 module-state 里，本项目独立成文件，便于与设置项解耦） */
    private static final Path FILE =
        FabricLoader.getInstance().getConfigDir().resolve("yiyiaddon").resolve("enchant").resolve("points.json");

    /** 每类型一条 */
    private final Map<EnchantPointType, EnchantPoint> points = new EnumMap<>(EnchantPointType.class);

    private String pointServer;
    private String pointDimension;

    /** 挂机视角（旧 {@code hangoutYaw / hangoutPitch}） */
    private Float hangoutYaw;
    private Float hangoutPitch;

    /** 铁砧朝向名（旧 {@code posAnvilFacing}，{@code Direction.getName()}） */
    private String anvilFacing;

    /** 是否已读盘；写操作前若未读盘会先读一次，避免整份写盘抹掉磁盘上的绑定 */
    private boolean loaded;

    /** 从磁盘重读；覆盖内存中的全部点位与附加数据 */
    public void reload() {
        points.clear();
        pointServer = null;
        pointDimension = null;
        hangoutYaw = null;
        hangoutPitch = null;
        anvilFacing = null;
        loaded = true;

        JsonObject root = JsonFileStore.readJson(FILE);
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
    }

    private void ensureLoaded() {
        if (!loaded) reload();
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

    /** 已绑定数量 */
    public int size() {
        ensureLoaded();
        return points.size();
    }

    /** 写入点位并立即落盘（覆盖保护由绑定服务在写入前拦，本类不做策略判定） */
    public void bind(EnchantPointType type, EnchantPoint point) {
        if (type == null || point == null) return;
        ensureLoaded();
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
        ensureLoaded();
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
        ensureLoaded();
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
        ensureLoaded();
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
        ensureLoaded();
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
        ensureLoaded();
        hangoutYaw = null;
        hangoutPitch = null;
        save();
    }

    /** 清除铁砧朝向（旧项目 {@code .fumo 移除 铁砧}，{@code FumoCommand:147-149}） */
    public void clearAnvilFacing() {
        ensureLoaded();
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
        JsonFileStore.writeAtomic(FILE, root);
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
