package com.yiyiaddon.feature.stardew.point;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.profile.SprinklerDefinition;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
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
import net.minecraft.world.level.block.state.BlockState;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
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
     * @param sprinklerCoverage 覆盖范围实测结论（仅洒水器；null = 还没测出来，渲染与统计退回物品说明 / 等级估算）
     */
    public record StardewPoint(int x, int y, int z, String dimension,
                               String verify, String identity, String typeName, String evidence, String serverKey,
                               SprinklerWorldBinding sprinklerBinding, SprinklerCoverage sprinklerCoverage) {

        /** 新点位记录当前服务器键，旧格式读取时则以经校验的文件作用域补齐。 */
        public StardewPoint(int x, int y, int z, String dimension, String verify, String identity, String typeName, String evidence) {
            this(x, y, z, dimension, verify, identity, typeName, evidence, StardewContext.serverKey(), null, null);
        }

        /** 洒水器点位：人工确认的世界载体绑定 + 覆盖范围实测结论（后者可能还没有）。 */
        public StardewPoint(int x, int y, int z, String dimension, String verify, String identity,
                            String typeName, String evidence, SprinklerWorldBinding sprinklerBinding,
                            SprinklerCoverage sprinklerCoverage) {
            this(x, y, z, dimension, verify, identity, typeName, evidence,
                StardewContext.serverKey(), sprinklerBinding, sprinklerCoverage);
        }

        /** 无验证证据的点位（农田角点等纯坐标点位） */
        public StardewPoint(int x, int y, int z, String dimension) {
            this(x, y, z, dimension, null, null, null, null);
        }

        public BlockPos pos() {
            return new BlockPos(x, y, z);
        }

        /** 换上一份实测覆盖范围（其余字段一律不动；传 null 表示丢掉实测结论，退回物品说明 / 等级估算） */
        public StardewPoint withCoverage(SprinklerCoverage coverage) {
            return new StardewPoint(x, y, z, dimension, verify, identity, typeName, evidence, serverKey,
                sprinklerBinding, coverage);
        }

        /**
         * <b>仍适用</b>的实测覆盖范围：换服 / 重新提取资源（指纹变化）后自动视为无效，返回 {@code null}。
         *
         * <p>渲染与统计一律走这个方法，不直接读 {@link #sprinklerCoverage()}——否则换了服务器还会
         * 拿旧结论画框（三级洒水器的范围被套到另一台服务器的初级洒水器上）。</p>
         */
        public SprinklerCoverage measuredCoverage() {
            return sprinklerCoverage != null && sprinklerCoverage.appliesNow() ? sprinklerCoverage : null;
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
                        parseSprinklerBinding(p), parseSprinklerCoverage(p)));
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

    /**
     * 覆盖范围实测结论：字段不全 / 一格都没有时返回 {@code null}（退回物品说明 / 等级估算）。
     *
     * <p>单格偏移格式是 {@code "dx,dz"}；某一格写坏了只跳过那一格，不让一个坏值废掉整份实测。</p>
     */
    private static SprinklerCoverage parseSprinklerCoverage(JsonObject point) {
        if (!point.has("实测范围") || !point.get("实测范围").isJsonObject()) return null;
        JsonObject coverage = point.getAsJsonObject("实测范围");
        String serverKey = string(coverage, "服务器");
        String fingerprint = string(coverage, "资源指纹");
        if (serverKey == null || fingerprint == null
            || !coverage.has("范围") || !coverage.get("范围").isJsonArray()) return null;
        List<Long> cells = new ArrayList<>();
        for (var element : coverage.getAsJsonArray("范围")) {
            Long cell = parseCoverageCell(element);
            if (cell != null) cells.add(cell);
        }
        if (cells.isEmpty()) return null;
        return new SprinklerCoverage(serverKey, fingerprint, List.copyOf(cells), intOr(coverage, "矩形内干盆", 0));
    }

    /** 实测单格偏移（{@code "-1,2"}）→ 打包键；格式不对返回 null */
    private static Long parseCoverageCell(JsonElement element) {
        try {
            String[] parts = element.getAsString().split(",");
            if (parts.length != 2) return null;
            return SprinklerCoverage.key(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
        } catch (Exception e) {
            return null;
        }
    }

    /** 读取整数字段；缺失 / 类型不对一律回退默认值（旧版与手改档兼容） */
    private static int intOr(JsonObject obj, String key, int fallback) {
        try {
            return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsInt() : fallback;
        } catch (Exception e) {
            return fallback;
        }
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
                if (point.sprinklerCoverage() != null) {
                    SprinklerCoverage coverage = point.sprinklerCoverage();
                    JsonObject measured = new JsonObject();
                    measured.addProperty("服务器", coverage.serverKey());
                    measured.addProperty("资源指纹", coverage.fingerprint());
                    JsonArray cells = new JsonArray();
                    for (long cell : coverage.cells()) {
                        cells.add(SprinklerCoverage.dxOf(cell) + "," + SprinklerCoverage.dzOf(cell));
                    }
                    measured.add("范围", cells);
                    measured.addProperty("矩形内干盆", coverage.dryInside());
                    p.add("实测范围", measured);
                }
                arr.add(p);
            }
            obj.add(type.node(), arr);
        }
        return JsonFileStore.writeAtomic(file(serverKey), obj);
    }

    /**
     * 设置单点位类型：只替换<b>同维度</b>的旧值，其它维度的同名点位保留。
     *
     * <p>一个箱子只存在于一个维度，但「岩浆箱」这个业务类型在主世界和下界可以各有一个。
     * 旧实现是 {@code list.clear()} 后加入，等于「在别的维度标一次就把这里的抹掉」——
     * 实机反馈就是「切换维度老是要重新标点」。</p>
     */
    public synchronized void set(StardewPointType type, StardewPoint point) {
        List<StardewPoint> list = points.computeIfAbsent(type, k -> new ArrayList<>());
        list.removeIf(p -> java.util.Objects.equals(p.dimension(), point.dimension()));
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

    /**
     * 清除单个点位类型：只清<b>当前维度</b>的，其它维度保留。
     *
     * <p>与 {@link #set} 同一口径——删除按钮不能把别的维度辛苦标好的箱子一起删掉。</p>
     */
    public synchronized void clear(StardewPointType type) {
        points.computeIfAbsent(type, k -> new ArrayList<>()).removeIf(StardewPoint::inCurrentDimension);
    }

    /** 移除指定坐标的洒水器 */
    public synchronized void removeSprinkler(BlockPos pos) {
        List<StardewPoint> list = points.getOrDefault(StardewPointType.SPRINKLER, List.of());
        list.removeIf(p -> p.inCurrentDimension() && p.pos().equals(pos));
    }

    /** 按坐标 + 维度移除一个洒水器点位（点位列表逐条删除用，不限于当前维度） */
    public synchronized void removeSprinkler(StardewPoint point) {
        points.getOrDefault(StardewPointType.SPRINKLER, List.of()).removeIf(p ->
            p.pos().equals(point.pos()) && java.util.Objects.equals(p.dimension(), point.dimension()));
    }

    /**
     * 读取单点位类型：<b>只返回当前维度</b>的那个，当前维度没标就是 {@code null}。
     *
     * <p>为什么不能回退到别的维度：调用方把这个结果当「本维度有没有可用点位」用——
     * 绑定时会报「已绑定，请先删除」，启动自检会去校验它。一旦回退，在主世界里会拿到下界那条，
     * 结果是「主世界想标一个却被要求先删掉下界的」（实机反馈：换个维度就得删了重标）。
     * 同名类型在别的维度的点位只是在列表里存着，本维度看不到、也不影响本维度新标一个。</p>
     */
    public synchronized StardewPoint get(StardewPointType type) {
        List<StardewPoint> list = points.get(type);
        if (list == null) return null;
        for (StardewPoint p : list) {
            if (p.inCurrentDimension()) return p;
        }
        return null;
    }

    /**
     * 读取某类型在其它维度是否已有点位（只用于界面提示「别的维度已绑过」）。
     *
     * <p>与 {@link #get} 分开：那个是「本维度能不能用」，这个是「别处有没有」，
     * 混在一起就会重演上面那个「必须删掉别维度的点」的问题。</p>
     */
    public synchronized boolean hasOtherDimension(StardewPointType type) {
        List<StardewPoint> list = points.get(type);
        if (list == null) return false;
        for (StardewPoint p : list) {
            if (!p.inCurrentDimension()) return true;
        }
        return false;
    }

    /**
     * 读取某点位类型<b>本维度</b>的全部条目（洒水器这类多点位用）。
     *
     * <p>与 {@link #getAll} 分开：那个是「管理界面要看全部维度的记录」，这个是「本维度能用的有哪些」。
     * 启动自检与运行时任务一律用这个——否则在主世界标过的洒水器会在下界被逐台校验，报出
     * 「点位属于其它维度」并拦住启动（实机反馈：到了下界被要求把主世界的洒水器删掉重标）。</p>
     */
    public synchronized List<StardewPoint> getInCurrentDimension(StardewPointType type) {
        List<StardewPoint> result = new ArrayList<>();
        for (StardewPoint point : points.getOrDefault(type, List.of())) {
            if (point.inCurrentDimension()) result.add(point);
        }
        return List.copyOf(result);
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

    /** 会话失效立即清空视图，不写磁盘；不能把 A 的点位展示成 B 的配置。 */
    public synchronized void invalidate() {
        clearAll();
        loadedServer = null;
    }

    /**
     * 「世界数据还没同步好」类失败：区块 / 方块实体尚未到达客户端。
     *
     * <p>这类失败是<b>暂时</b>的——进服或切维度后区块与方块实体分批到达，服务器也可能在玩家靠近时
     * 才把占位方块换成真容器。调用方必须复核后再判，绝不能拿它当配置错误拦启动。</p>
     */
    public static final String PENDING_CHUNK = "所在区块尚未加载，无法验证";

    /** @see #PENDING_CHUNK 容器方块实体尚未同步（区块已加载但方块实体还没到） */
    public static final String PENDING_CONTAINER = "容器数据尚未同步，无法验证";

    /** 该失败是否属于「世界数据未就绪」（可稍后复核） */
    public static boolean isWorldPending(String failure) {
        return PENDING_CHUNK.equals(failure) || PENDING_CONTAINER.equals(failure);
    }

    /** 设置、自检与运行交互共用的实时点位校验。 */
    public String validationFailure(StardewPointType type, StardewPoint point, StardewResourceIndex index) {
        if (point == null) return "未绑定";
        if (!GameProbe.isMultiplayer() || !ResourceExtractionService.isReady()) return "当前服务器资源未就绪";
        if (!java.util.Objects.equals(point.serverKey(), StardewContext.serverKey())
            || !java.util.Objects.equals(loadedServer, StardewContext.serverKey())) return "点位属于其它服务器";
        if (!point.inCurrentDimension()) return "点位属于其它维度";
        var mc = Minecraft.getInstance();
        if (mc.level == null || !mc.level.isLoaded(point.pos())) return PENDING_CHUNK;
        if (type == StardewPointType.WATER_SOURCE) return waterSourceFailure(point.pos());
        if (type.requiresContainer()) {
            BlockState state = mc.level.getBlockState(point.pos());
            // 顺序很重要：先判「方块没了 / 还没同步」，再判「类型变了」。旧实现把三者并成
            // 「原容器已不存在或类型已改变」，于是「区块刚加载、方块实体还在路上」也被说成
            // 配置错误并直接禁止启动——实机反馈是「我什么都没动，重新开关模块就好了」。
            if (state.isAir()) return "原方块已不存在";
            if (!(mc.level.getBlockEntity(point.pos()) instanceof Container)) {
                return state.hasBlockEntity() ? PENDING_CONTAINER : "原容器已不存在或类型已改变";
            }
        }
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

    /**
     * 在「用户已选的洒水器类型」范围内按世界方块语义匹配。
     *
     * <p>与 {@link #matchSprinkler} 的差别只有两点：只比对已选类型；不要求语义确定性为「已确认」。
     * 很多服务器的洒水器方块 blockstates 会命中多个候选（含水 / 朝向等属性各指向一份模型），语义只能
     * 判成「候选」，但候选串里已经含真实模型路径，足以在已选类型中唯一确定是哪一种。</p>
     *
     * @return 唯一命中的已选洒水器；未命中或命中多个（有歧义）返回 null
     */
    public static SprinklerDefinition matchSelectedSprinkler(BlockPos pos, StardewResourceIndex index,
                                                             Collection<String> selectedKeys) {
        var level = Minecraft.getInstance().level;
        if (level == null || !ResourceExtractionService.isReady() || !GameProbe.isMultiplayer()
            || !level.isLoaded(pos) || index == null || selectedKeys == null || selectedKeys.isEmpty()) return null;
        List<SprinklerDefinition> candidates = new ArrayList<>();
        for (String key : selectedKeys) {
            if (index.entryByKey(key) instanceof SprinklerDefinition sprinkler) candidates.add(sprinkler);
        }
        return matchSprinklerBySemantic(BlockStateModelResolver.resolve(level.getBlockState(pos)), candidates);
    }

    /**
     * 方块语义 → 洒水器定义：在给定候选里按「语义身份 == 洒水器身份键」或「语义模型 ∈ 候选世界模型」唯一命中。
     *
     * <p><b>判据只此一份：</b>世界方块路径（{@link #matchSelectedSprinkler}）与展示实体路径
     * （{@code StardewPointActions} 读 {@code block_display} 携带的方块状态）必须走同一条比对，
     * 否则会出现「同一台洒水器，站在世界里认得出、摆成展示物就认不出」这种自相矛盾。</p>
     *
     * <p>语义模型可能是一串候选（含水 / 朝向等属性各指向一份模型，用 {@code |} 连接），
     * 因此按候选逐个比，只要有一个相等就算命中；命中多个<b>不同</b>洒水器时返回 {@code null}（歧义不当证据）。</p>
     */
    public static SprinklerDefinition matchSprinklerBySemantic(BlockSemantic semantic,
                                                               List<SprinklerDefinition> candidates) {
        if (semantic == null || candidates == null || candidates.isEmpty()) return null;
        List<String> models = new ArrayList<>();
        if (semantic.model() != null) {
            for (String candidate : semantic.model().split("\\|")) models.add(candidate.trim());
        }
        SprinklerDefinition match = null;
        for (SprinklerDefinition sprinkler : candidates) {
            boolean hit = (semantic.identity() != null && semantic.identity().equals(sprinkler.identityKey()))
                || (sprinkler.blockModel() != null && models.contains(sprinkler.blockModel()));
            if (!hit) continue;
            if (match != null && !match.key().equals(sprinkler.key())) return null;
            match = sprinkler;
        }
        return match;
    }
}
