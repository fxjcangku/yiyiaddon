package com.yiyiaddon.feature.stardew.point;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.profile.SprinklerDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.recognition.CropRecognizer;
import com.yiyiaddon.feature.stardew.recognition.PotState;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.repository.JsonFileStore;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Container;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 星露谷点位持久化（按 serverKey 隔离，支持洒水器多点位）。
 *
 * <p>点位记录真实坐标 + 维度，不保存玩家朝向 / 背包等动态状态；切服 / 换档后
 * 按 {@code serverKey}（host:port 或 singleplayer）加载对应文件，跨服坐标绝不串用。</p>
 *
 * <p><b>为什么还要记录「验证 / 身份 / 类型 / 证据」：</b>补水点与洒水器点位不是「任意方块」
 * 都能绑的。玩家要求设置补水点时必须确认准星命中的是静止水源、洒水器必须命中当前服务器
 * 资源包定义的那种洒水器。把当时的判定依据一并存盘，才能在运行时复检「这个位置现在还是
 * 不是原来那个东西」，而不是每帧重新猜。旧版 JSON 没有这些字段，读取时一律回退为 null
 * （视为未验证），绝不让老存档变成假「已验证」。</p>
 */
public final class StardewPointManager {

    /**
     * 单个点位：坐标 + 维度 + 设置时的验证证据。
     *
     * @param verify   验证类型中文名（如「静止水源」/「已验证」）；null = 未验证
     * @param identity 身份键（洒水器 canonicalKey 等）；null = 无身份
     * @param typeName 中文类型名（如「高级洒水器」）；null = 用业务类型名
     * @param evidence 证据状态（已确认 / 候选 等）；null = 未知
     */
    public record StardewPoint(int x, int y, int z, String dimension,
                               String verify, String identity, String typeName, String evidence, String serverKey,
                               SprinklerWorldBinding sprinklerBinding) {

        /** 新点位记录当前服务器键，旧格式读取时则以经校验的文件作用域补齐。 */
        public StardewPoint(int x, int y, int z, String dimension, String verify, String identity, String typeName, String evidence) {
            this(x, y, z, dimension, verify, identity, typeName, evidence, StardewContext.serverKey(), null);
        }

        /** 洒水器点位同时保存人工确认的世界载体绑定。 */
        public StardewPoint(int x, int y, int z, String dimension, String verify, String identity,
                            String typeName, String evidence, SprinklerWorldBinding sprinklerBinding) {
            this(x, y, z, dimension, verify, identity, typeName, evidence,
                StardewContext.serverKey(), sprinklerBinding);
        }

        /** 无验证证据的点位（农田角点等纯坐标点位） */
        public StardewPoint(int x, int y, int z, String dimension) {
            this(x, y, z, dimension, null, null, null, null);
        }

        public BlockPos pos() {
            return new BlockPos(x, y, z);
        }

        public boolean inCurrentDimension() {
            return dimension != null && dimension.equals(StardewContext.dimension());
        }

        /** 是否有验证证据（用于状态页区分「已验证」与「旧版未验证」） */
        public boolean verified() {
            return verify != null && !verify.isBlank();
        }
    }

    private final Path root;
    private final Map<StardewPointType, List<StardewPoint>> points = new EnumMap<>(StardewPointType.class);
    private String loadedServer = null;

    public StardewPointManager() {
        this.root = Minecraft.getInstance().gameDirectory.toPath().resolve("StardewFarm").resolve("points");
    }

    private Path file(String serverKey) {
        String safe = serverKey.replaceAll("[\\\\/:*?\"<>|]", "_");
        return root.resolve(safe + ".json");
    }

    /** 加载指定服务器点位到内存；与已加载服务器相同则跳过 */
    public synchronized void load(String serverKey) {
        if (serverKey == null || serverKey.equals(loadedServer)) return;
        loadedServer = serverKey;
        for (StardewPointType type : StardewPointType.values()) points.put(type, new ArrayList<>());

        Path file = file(serverKey);
        if (!Files.isRegularFile(file)) return;
        try {
            JsonObject obj = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
            if (!serverKey.equals(string(obj, "服务器"))) return;
            for (StardewPointType type : StardewPointType.values()) {
                List<StardewPoint> list = points.get(type);
                list.clear();
                if (!obj.has(type.node()) || !obj.get(type.node()).isJsonArray()) continue;
                for (var el : obj.getAsJsonArray(type.node())) {
                    if (!el.isJsonObject()) continue;
                    JsonObject p = el.getAsJsonObject();
                    list.add(new StardewPoint(p.get("x").getAsInt(), p.get("y").getAsInt(), p.get("z").getAsInt(),
                        string(p, "维度"), string(p, "验证"), string(p, "身份"),
                        string(p, "类型"), string(p, "证据"),
                        string(p, "服务器") == null ? serverKey : string(p, "服务器"),
                        parseSprinklerBinding(p)));
                }
            }
        } catch (Exception ignored) {
            // 损坏文件返回空，不覆盖
        }
    }

    /** 读取字符串字段；缺失 / null 一律返回 null（旧版 JSON 兼容） */
    private static String string(JsonObject obj, String key) {
        if (!obj.has(key) || obj.get(key).isJsonNull()) return null;
        try {
            String value = obj.get(key).getAsString();
            return value == null || value.isBlank() ? null : value;
        } catch (Exception e) {
            return null;
        }
    }

    /** 旧点位没有世界绑定字段时返回 null，继续走原资源语义复检。 */
    private static SprinklerWorldBinding parseSprinklerBinding(JsonObject point) {
        if (!point.has("世界绑定") || !point.get("世界绑定").isJsonObject()) return null;
        JsonObject binding = point.getAsJsonObject("世界绑定");
        String serverKey = string(binding, "服务器");
        String fingerprint = string(binding, "资源指纹");
        String sprinklerKey = string(binding, "洒水器逻辑身份");
        String carrierBlockId = string(binding, "世界载体方块");
        String carrierState = string(binding, "世界载体状态");
        if (serverKey == null || fingerprint == null || sprinklerKey == null
            || carrierBlockId == null || carrierState == null) return null;
        return new SprinklerWorldBinding(serverKey, fingerprint, sprinklerKey, carrierBlockId, carrierState,
            string(binding, "资源语义身份"), string(binding, "资源模型"));
    }

    /** 保存当前内存点位（原子写入，失败保留旧档） */
    public synchronized boolean save(String serverKey) {
        if (serverKey == null || !serverKey.equals(loadedServer)) return false;
        JsonObject obj = new JsonObject();
        obj.addProperty("服务器", serverKey);
        for (StardewPointType type : StardewPointType.values()) {
            JsonArray arr = new JsonArray();
            for (StardewPoint point : points.getOrDefault(type, List.of())) {
                JsonObject p = new JsonObject();
                p.addProperty("x", point.x());
                p.addProperty("y", point.y());
                p.addProperty("z", point.z());
                p.addProperty("维度", point.dimension());
                p.addProperty("服务器", point.serverKey());
                if (point.verify() != null) p.addProperty("验证", point.verify());
                if (point.identity() != null) p.addProperty("身份", point.identity());
                if (point.typeName() != null) p.addProperty("类型", point.typeName());
                if (point.evidence() != null) p.addProperty("证据", point.evidence());
                if (point.sprinklerBinding() != null) {
                    SprinklerWorldBinding binding = point.sprinklerBinding();
                    JsonObject worldBinding = new JsonObject();
                    worldBinding.addProperty("服务器", binding.serverKey());
                    worldBinding.addProperty("资源指纹", binding.fingerprint());
                    worldBinding.addProperty("洒水器逻辑身份", binding.sprinklerKey());
                    worldBinding.addProperty("世界载体方块", binding.carrierBlockId());
                    worldBinding.addProperty("世界载体状态", binding.carrierState());
                    if (binding.semanticIdentity() != null) worldBinding.addProperty("资源语义身份", binding.semanticIdentity());
                    if (binding.semanticModel() != null) worldBinding.addProperty("资源模型", binding.semanticModel());
                    p.add("世界绑定", worldBinding);
                }
                arr.add(p);
            }
            obj.add(type.node(), arr);
        }
        return JsonFileStore.writeAtomic(file(serverKey), obj);
    }

    /** 设置单点位类型（替换旧值；洒水器请用 {@link #addSprinkler}） */
    public synchronized void set(StardewPointType type, StardewPoint point) {
        List<StardewPoint> list = points.computeIfAbsent(type, k -> new ArrayList<>());
        list.clear();
        list.add(point);
    }

    /** 设置单点位类型（无验证证据的纯坐标点位：农田角点等） */
    public synchronized void set(StardewPointType type, BlockPos pos, String dimension) {
        set(type, new StardewPoint(pos.getX(), pos.getY(), pos.getZ(), dimension));
    }

    /** 追加一个洒水器点位（按坐标去重；重复时用新的证据覆盖旧的） */
    public synchronized void addSprinkler(StardewPoint point) {
        List<StardewPoint> list = points.computeIfAbsent(StardewPointType.SPRINKLER, k -> new ArrayList<>());
        int existing = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).pos().equals(point.pos()) && java.util.Objects.equals(list.get(i).dimension(), point.dimension())) {
                existing = i;
                break;
            }
        }
        if (existing >= 0) list.set(existing, point);
        else list.add(point);
    }

    /** 清除单个点位类型 */
    public synchronized void clear(StardewPointType type) {
        points.computeIfAbsent(type, k -> new ArrayList<>()).clear();
    }

    /** 移除指定坐标的洒水器 */
    public synchronized void removeSprinkler(BlockPos pos) {
        List<StardewPoint> list = points.getOrDefault(StardewPointType.SPRINKLER, List.of());
        list.removeIf(p -> p.inCurrentDimension() && p.pos().equals(pos));
    }

    /** 读取单点位类型（首个），未绑定返回 null */
    public synchronized StardewPoint get(StardewPointType type) {
        List<StardewPoint> list = points.get(type);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    /** 读取某点位类型全部（洒水器多点位） */
    public synchronized List<StardewPoint> getAll(StardewPointType type) {
        return List.copyOf(points.getOrDefault(type, List.of()));
    }

    /** 某类型已绑定的数量 */
    public synchronized int count(StardewPointType type) {
        return points.getOrDefault(type, List.of()).size();
    }

    /** 清空全部点位 */
    public synchronized void clearAll() {
        for (StardewPointType type : StardewPointType.values()) points.put(type, new ArrayList<>());
    }

    /** 是否有农田范围（起点 + 终点都已绑定） */
    public synchronized boolean hasRegion() {
        return get(StardewPointType.START) != null && get(StardewPointType.END) != null;
    }

    /** 会话失效立即清空视图，不写磁盘；不能把 A 的点位展示成 B 的配置。 */
    public synchronized void invalidate() {
        clearAll();
        loadedServer = null;
    }

    /** 设置、自检与运行交互共用的实时点位校验。 */
    public String validationFailure(StardewPointType type, StardewPoint point, StardewResourceIndex index) {
        if (point == null) return "未绑定";
        if (!GameProbe.isMultiplayer() || !ResourceExtractionService.isReady()) return "当前服务器资源未就绪";
        if (!java.util.Objects.equals(point.serverKey(), StardewContext.serverKey())
            || !java.util.Objects.equals(loadedServer, StardewContext.serverKey())) return "点位属于其它服务器";
        if (!point.inCurrentDimension()) return "点位属于其它维度";
        var mc = Minecraft.getInstance();
        if (mc.level == null || !mc.level.isLoaded(point.pos())) return "所在区块尚未加载，无法验证";
        if (type == StardewPointType.START || type == StardewPointType.END) {
            PotState pot = CropRecognizer.recognizePot(mc.level.getBlockState(point.pos()));
            return pot == PotState.DRY || pot == PotState.WET ? null : "农田基础种植盆已不存在";
        }
        if (type == StardewPointType.WATER_SOURCE) return waterSourceFailure(point.pos());
        if (type.requiresContainer() && !(mc.level.getBlockEntity(point.pos()) instanceof Container)) return "原容器已不存在或类型已改变";
        if (type == StardewPointType.SPRINKLER) {
            SprinklerWorldBinding binding = point.sprinklerBinding();
            if (binding != null) {
                if (!java.util.Objects.equals(binding.serverKey(), StardewContext.serverKey())) return "洒水器绑定属于其它服务器";
                if (!java.util.Objects.equals(binding.fingerprint(), ResourceExtractionService.fingerprint())) return "资源指纹已改变，洒水器需重新人工确认";
                if (!java.util.Objects.equals(binding.sprinklerKey(), point.identity())) return "洒水器逻辑身份与点位记录不一致";
                if (!(index.entryByKey(binding.sprinklerKey()) instanceof SprinklerDefinition)) return "已选洒水器已不在当前资源索引";
                if (!binding.matches(point.pos())) return "当前世界载体状态与人工确认绑定不一致";
                return null;
            }
            SprinklerDefinition current = matchSprinkler(point.pos(), index);
            if (current == null || !current.key().equals(point.identity())) return "当前方块与已验证洒水器身份不一致";
        }
        if (mc.level.getBlockState(point.pos()).isAir()) return "原方块已不存在";
        return null;
    }

    /** 补水只接受真实静止水源，流动水和其它流体不能混用。 */
    public static String waterSourceFailure(BlockPos pos) {
        var level = Minecraft.getInstance().level;
        if (level == null || !level.isLoaded(pos)) return "所在区块尚未加载，无法验证";
        var fluid = level.getBlockState(pos).getFluidState();
        if (!fluid.is(FluidTags.WATER)) return "目标不是水源（普通方块、空气或其它流体）";
        return fluid.isSource() ? null : "目标是流动水，不是静止水源";
    }

    /** 完整模型身份唯一匹配当前索引，拒绝模糊末段、未知语义和多重候选。 */
    public static SprinklerDefinition matchSprinkler(BlockPos pos, StardewResourceIndex index) {
        var level = Minecraft.getInstance().level;
        if (level == null || !ResourceExtractionService.isReady() || !GameProbe.isMultiplayer()
            || !level.isLoaded(pos) || index == null) return null;
        BlockSemantic semantic = BlockStateModelResolver.resolve(level.getBlockState(pos));
        if (!"已确认".equals(semantic.certainty())) return null;
        SprinklerDefinition match = null;
        for (var entry : index.entriesFor(StardewSelectorCategory.SPRINKLER)) {
            if (!(entry instanceof SprinklerDefinition sprinkler)) continue;
            if ((sprinkler.blockModel() != null && sprinkler.blockModel().equals(semantic.model()))
                || (sprinkler.identityKey() != null && sprinkler.identityKey().equals(semantic.identity()))) {
                if (match != null && !match.key().equals(sprinkler.key())) return null;
                match = sprinkler;
            }
        }
        return match;
    }
}
