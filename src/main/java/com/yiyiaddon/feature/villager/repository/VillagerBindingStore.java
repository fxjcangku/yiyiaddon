package com.yiyiaddon.feature.villager.repository;

import com.google.gson.JsonObject;
import com.yiyiaddon.feature.villager.model.VillagerBinding;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.repository.JsonFileStore;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 村民交易容器绑定仓库（绿宝石箱 / 成品交易箱）。
 *
 * <p>按服务器分文件持久化，语义逐字迁移自旧项目 {@code CunminCommand.CunminData} +
 * {@code loadData/saveData}：</p>
 * <ul>
 *   <li>键名固定 {@code emerald_chest} / {@code unload_chest}，<b>禁止改动</b>（旧存档靠它读回）；</li>
 *   <li>每条形如 {@code {"pos": "x y z", "dimension": "minecraft:the_end"}}，写法与旧项目一致，
 *       保证写出的文件旧版本也能读；</li>
 *   <li>维度只落三值（D2 拍板，见 {@link VillagerBinding#toDimensionId}）。</li>
 * </ul>
 *
 * <p><b>与旧项目的两处登记差异（第 168 条，均已在本阶段报告登记）：</b></p>
 * <ol>
 *   <li><b>服务器键</b>：旧项目文件名是 {@code ip} 直接净化（保留大小写、不补端口），新项目按第 45 条
 *       统一用 {@link WorldIdentity#fileSafeServer()}（小写 + 补默认端口），落在
 *       {@code config/yiyiaddon/villager/bindings/}；旧项目目录（{@code config/yiyiaddon/cunmin}）
 *       <b>不再读取</b>，从旧版本升级需重新绑定。</li>
 *   <li><b>读盘时机</b>：旧项目只在类加载时读一次当前服务器的文件，会话中切换服务器不会重读
 *       （旧实现的缺陷）；新项目改为「每次访问前核对当前服务器键，变了就重读」
 *       （{@link MiningPointStore} 的 {@code reload()} 口径）。</li>
 * </ol>
 *
 * <p>本类只负责读写与数据事务，不含任何播报（第 48 条：repository 不做界面/文本）。写盘失败以
 * {@code false} 回报，由调用方（`&lt;模块&gt;` 指令）按旧文案播报。</p>
 */
public final class VillagerBindingStore {

    /** 绿宝石箱键名（旧档不变） */
    public static final String KEY_EMERALD_CHEST = "emerald_chest";
    /** 成品交易箱键名（旧档不变） */
    public static final String KEY_UNLOAD_CHEST = "unload_chest";

    /** 新数据目录：{@code config/yiyiaddon/villager/bindings} */
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir()
        .resolve("yiyiaddon").resolve("villager").resolve("bindings");

    /** 当前服务器的绑定（键名 → 记录）；旧项目 DATA_STORE 的等价物 */
    private static final Map<String, VillagerBinding> BINDINGS = new LinkedHashMap<>();

    /** 最近一次装载时的服务器键；null = 尚未装载 */
    private static String loadedServer;

    private VillagerBindingStore() {
    }

    // ── 读盘 / 写盘 ─────────────────────────────────────────────────────────

    /**
     * 确保内存里装的是「当前服务器」的绑定。
     *
     * <p>每次对外访问前都会先过这里：服务器键变了就重读，避免把上一个服务器的绑定带过来。</p>
     */
    public static void ensureLoaded() {
        String server = WorldIdentity.fileSafeServer();
        if (server.equals(loadedServer)) return;
        reload();
    }

    /** 强制按当前服务器重读；先清空内存再读盘（不清空会串服）。 */
    public static void reload() {
        BINDINGS.clear();
        loadedServer = WorldIdentity.fileSafeServer();
        Path file = dataFile();
        JsonObject json = JsonFileStore.readJson(file);
        if (json == null) return;
        readEntry(json, KEY_EMERALD_CHEST);
        readEntry(json, KEY_UNLOAD_CHEST);
    }

    /** 会话失效（退出服务器 / 存档）：清内存并作废装载上下文。 */
    public static void invalidate() {
        BINDINGS.clear();
        loadedServer = null;
    }

    /** 从 JSON 读一条绑定；缺 {@code pos} 视为未绑定。 */
    private static void readEntry(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonObject()) return;
        JsonObject entry = json.getAsJsonObject(key);
        BlockPos pos = parsePos(entry.has("pos") && entry.get("pos").isJsonPrimitive()
            ? entry.get("pos").getAsString() : null);
        if (pos == null) return;
        String dimension = entry.has("dimension") && entry.get("dimension").isJsonPrimitive()
            ? entry.get("dimension").getAsString() : VillagerBinding.OVERWORLD_ID;
        BINDINGS.put(key, new VillagerBinding(pos, dimension));
    }

    /**
     * 解析旧项目的坐标写法 {@code "x y z"}。
     *
     * <p>旧项目就是空格分隔的三段整数；解析不出返回 {@code null}，由调用方按未绑定处理
     * （不抛异常：坏档不该让模块起不来）。</p>
     */
    private static BlockPos parsePos(String raw) {
        if (raw == null || raw.isBlank()) return null;
        String[] parts = raw.trim().split("\\s+");
        if (parts.length != 3) return null;
        try {
            return new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * 写盘（原子写 + 失败回滚由 {@link JsonFileStore} 负责）。
     *
     * @return 是否写入成功；失败由调用方播报（旧文案 {@code 保存失败：…}）
     */
    public static boolean save() {
        ensureLoaded();
        JsonObject json = new JsonObject();
        for (String key : new String[]{KEY_EMERALD_CHEST, KEY_UNLOAD_CHEST}) {
            VillagerBinding binding = BINDINGS.get(key);
            if (binding == null) continue;
            JsonObject entry = new JsonObject();
            BlockPos pos = binding.pos();
            entry.addProperty("pos", pos.getX() + " " + pos.getY() + " " + pos.getZ());
            entry.addProperty("dimension", binding.dimensionId());
            json.add(key, entry);
        }
        return JsonFileStore.writeAtomic(dataFile(), json);
    }

    private static Path dataFile() {
        return CONFIG_DIR.resolve(loadedServerKeyOrCurrent() + ".json");
    }

    /** 写盘时若还没装载过，用当前服务器键兜底，避免落成 {@code null.json}。 */
    private static String loadedServerKeyOrCurrent() {
        return loadedServer != null ? loadedServer : WorldIdentity.fileSafeServer();
    }

    // ── 查询 ───────────────────────────────────────────────────────────────

    /** 当前绑定视图（旧 {@code CunminCommand.getBinding()} 的等价物，四个取值方法同名保留）。 */
    public static ContainerBinding getBinding() {
        ensureLoaded();
        return new ContainerBinding();
    }

    /** 绿宝石箱坐标；未绑定返回 {@code null}。 */
    public static BlockPos getEmeraldChestPos() {
        return posOf(KEY_EMERALD_CHEST);
    }

    /** 成品交易箱坐标；未绑定返回 {@code null}。 */
    public static BlockPos getUnloadChestPos() {
        return posOf(KEY_UNLOAD_CHEST);
    }

    /** 绿宝石箱维度；未绑定返回 {@code null}（旧项目同口径）。 */
    public static ResourceKey<Level> getEmeraldChestDimension() {
        return dimensionOf(KEY_EMERALD_CHEST);
    }

    /** 成品交易箱维度；未绑定返回 {@code null}（旧项目同口径）。 */
    public static ResourceKey<Level> getUnloadChestDimension() {
        return dimensionOf(KEY_UNLOAD_CHEST);
    }

    /** 指定键是否已绑定。 */
    public static boolean hasBinding(String key) {
        ensureLoaded();
        return BINDINGS.containsKey(key);
    }

    /** 是否存在任一绑定（旧 {@code hasAnyBinding}）。 */
    public static boolean hasAnyBinding() {
        ensureLoaded();
        return !BINDINGS.isEmpty();
    }

    /** 指定键的绑定；未绑定返回 {@code null}。 */
    public static VillagerBinding binding(String key) {
        ensureLoaded();
        return BINDINGS.get(key);
    }

    private static BlockPos posOf(String key) {
        VillagerBinding binding = binding(key);
        return binding == null ? null : binding.pos();
    }

    private static ResourceKey<Level> dimensionOf(String key) {
        VillagerBinding binding = binding(key);
        return binding == null ? null : binding.dimensionKey();
    }

    // ── 写入 ───────────────────────────────────────────────────────────────

    /**
     * 写入一条绑定（不落盘；由调用方决定何时 {@link #save()}，旧项目是指令里连写）。
     *
     * <p>维度按 {@link VillagerBinding#toDimensionId} 归一成三值，与旧 {@code toDimensionId} 一致。</p>
     */
    public static void setBinding(String key, BlockPos pos, ResourceKey<Level> dimension) {
        ensureLoaded();
        BINDINGS.put(key, new VillagerBinding(pos, VillagerBinding.toDimensionId(dimension)));
    }

    /** 删除一条绑定（不落盘）。 */
    public static void removeBinding(String key) {
        ensureLoaded();
        BINDINGS.remove(key);
    }

    /** 清空当前服务器的全部绑定（不落盘）。 */
    public static void clear() {
        ensureLoaded();
        BINDINGS.clear();
    }

    /**
     * 绑定视图：把「两箱」的四个取值方法集中在一处，供状态机与界面卡片使用。
     *
     * <p>类名与四个方法名逐字沿用旧项目 {@code CunminCommand.ContainerBinding}，
     * 状态机里的调用行因而可以原样保留（只换持有者类名）。</p>
     */
    public static final class ContainerBinding {

        private ContainerBinding() {
        }

        /** 获取绿宝石箱坐标 */
        public BlockPos getEmeraldBox() {
            return posOf(KEY_EMERALD_CHEST);
        }

        /** 获取成品交易箱坐标 */
        public BlockPos getUnloadBox() {
            return posOf(KEY_UNLOAD_CHEST);
        }

        /** 获取绿宝石箱维度（裸 ID，未绑定为 null） */
        public String getEmeraldBoxDimension() {
            VillagerBinding binding = binding(KEY_EMERALD_CHEST);
            return binding == null ? null : binding.dimensionId();
        }

        /** 获取成品交易箱维度（裸 ID，未绑定为 null） */
        public String getUnloadBoxDimension() {
            VillagerBinding binding = binding(KEY_UNLOAD_CHEST);
            return binding == null ? null : binding.dimensionId();
        }
    }
}
