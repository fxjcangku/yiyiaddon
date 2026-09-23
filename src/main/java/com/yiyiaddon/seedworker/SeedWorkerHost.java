package com.yiyiaddon.seedworker;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import net.minecraft.CrashReport;
import net.minecraft.SharedConstants;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.WorldStem;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.notifications.NotificationManager;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.chunk.storage.RegionFileVersion;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.LevelDataAndDimensions;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 种子挖矿 · Worker · <b>Vanilla 世界生成宿主</b>（一个真正可用于 worldgen 的 {@code ServerLevel}）。
 *
 * <p><b>它解决什么</b>（阶段 232 口径第二、三、五、六节）：231 已证明「正式 Predictor 需要一个
 * {@code ServerLevel}」是原版类型契约，逃不掉；所以本阶段不再试图消灭它，改为<b>在一个与游戏客户端
 * 完全隔离的本地进程里把它造出来</b>，并只把它当「环境宿主」用。</p>
 *
 * <p><b>它不是什么</b>（口径第五、三十八、三十九、五十三节）：</p>
 * <ul>
 *   <li>不是玩家能进入的单人世界：没有客户端、没有画面、没有存档选择；</li>
 *   <li>不是给玩家连接的服务器：宿主起来后<b>立刻停用对外监听</b>，且只绑定 127.0.0.1；</li>
 *   <li>不连接目标服务器：进程里根本没有任何远程地址；</li>
 *   <li>不读目标服务器的任何区块：预测仍走 229 的离线链路（宿主 ChunkMap 查询必须为 0）。</li>
 * </ul>
 *
 * <p><b>宿主世界与预测种子无关</b>（口径第十四、十五节）：宿主世界只提供注册表 / 维度类型 / 世界高度 /
 * 结构模板管理器 / 调色板工厂 / 是否生成结构 —— 这些都与种子无关；被预测的种子由
 * {@code PredictionSession} 自己派生出 {@code RandomState} 与结构状态，<b>从不读取宿主世界里生成了什么</b>。
 * 宿主世界的 {@code level-seed} 固定为 0，只为让宿主可重复，不参与任何预测语义。</p>
 *
 * <p><b>本类不含任何客户端类型</b>：它只依赖 {@code net.minecraft.server.*} 与服务端注册表 / 数据包，
 * 因此同一个模组 jar 既能在客户端进程里（不加载本类）也能在隔离的 Worker 进程里正确工作。</p>
 */
public final class SeedWorkerHost implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/seed-worker");

    /** 宿主世界名（Worker 自己的临时世界，绝不碰玩家的 saves）。 */
    private static final String LEVEL_NAME = "host";

    /** 等待 ServerLevel 就绪的上限（毫秒）；超时即视为启动失败并写清楚。 */
    private static final long LEVEL_READY_TIMEOUT_MILLIS = 300_000L;

    /** 宿主监听端口（仅回环；由客户端选一个空闲端口传进来，避免与别的 Worker 撞车）。 */
    private final int hostPort;

    /** Worker 运行目录（子进程工作目录：server.properties / universe / logs 都在这里）。 */
    private final Path runtimeDir;

    /** 宿主世界的容器目录（自己的独立目录，绝不使用玩家 saves）。 */
    private final Path universeDir;

    private String levelId;
    private MinecraftServer server;
    private ServerLevel overworld;
    private boolean listenerStopped;

    public SeedWorkerHost(Path runtimeDir, int hostPort) {
        this.runtimeDir = runtimeDir;
        this.hostPort = hostPort;
        this.universeDir = runtimeDir.resolve("universe");
    }

    /**
     * 启动宿主：Bootstrap → 数据包 / 注册表 → 世界数据 → Vanilla 服务端 → 等 ServerLevel 就绪。
     *
     * <p>流程与 {@code net.minecraft.server.Main} 的启动序列逐段对应（本类是它的等价实现，
     * 唯一区别是：我们不读 {@code eula.txt}、不监听外部玩家、并对宿主做最小化配置）。</p>
     */
    public void boot() throws Exception {
        long startedAt = System.currentTimeMillis();
        Files.createDirectories(universeDir);
        Files.createDirectories(runtimeDir.resolve("logs"));

        LOGGER.info("宿主初始化：开始（运行目录 {}，版本 {}）", runtimeDir.toAbsolutePath(),
                SharedConstants.getCurrentVersion().id());

        // ── 1. Vanilla 引导：与 Main 同序（版本探测 → 崩溃报告预加载 → 注册表 → 校验）──
        SharedConstants.tryDetectVersion();
        CrashReport.preload();
        Bootstrap.bootStrap();
        Bootstrap.validate();
        Util.startTimerHackThread();
        LOGGER.info("宿主初始化：Vanilla 注册表已就绪（{} 个阶段完成，耗时 {} ms）",
                "Bootstrap", System.currentTimeMillis() - startedAt);

        // ── 2. 宿主专用 server.properties（最小化 + 只回环 + 不开放任何对外能力）──
        Path propertiesPath = runtimeDir.resolve("server.properties");
        writeServerProperties(propertiesPath);
        DedicatedServerSettings settings = new DedicatedServerSettings(propertiesPath);
        settings.forceSave();
        RegionFileVersion.configure(settings.getProperties().regionFileComression);
        DedicatedServerProperties properties = settings.getProperties();
        this.levelId = properties.levelName;
        LOGGER.info("宿主初始化：server.properties 就绪（世界 {}，种子 {}，生成结构 {}，对外监听端口 {}）",
                levelId, properties.worldOptions.seed(), properties.worldOptions.generateStructures(), hostPort);

        // ── 3. 存储 / 数据包 / 认证服务（全部落在 Worker 自己的目录里）──
        File runtimeFile = runtimeDir.toFile();
        Services services = Services.create(new YggdrasilAuthenticationService(Proxy.NO_PROXY), runtimeFile);
        LevelStorageSource storage = LevelStorageSource.createDefault(universeDir);
        LevelStorageSource.LevelStorageAccess access = storage.validateAndCreateAccess(levelId);

        Dynamic<?> existingWorldData = null;
        if (access.hasWorldData()) {
            try {
                existingWorldData = access.getUnfixedDataTagWithFallback();
            } catch (IOException error) {
                throw new IllegalStateException("宿主世界数据损坏，请删除 " + universeDir + " 后重试", error);
            }
        }
        PackRepository packRepository = ServerPacksSource.createPackRepository(access);
        WorldLoader.InitConfig initConfig = loadOrCreateConfig(properties, existingWorldData, packRepository,
                existingWorldData == null);
        WorldStem stem = loadWorldStem(initConfig, access, properties, existingWorldData);
        access.saveDataTag(stem.worldDataAndGenSettings().data());
        LOGGER.info("宿主初始化：数据包与注册表已加载（世界数据来自{}）",
                existingWorldData == null ? "新建" : "已有世界");

        // ── 4. Vanilla 服务端（DedicatedServer 等价物；起来后立即停用对外监听）──
        this.server = MinecraftServer.spin(thread -> new WorkerServer(thread, access, packRepository, stem,
                Optional.empty(), settings, DataFixers.getDataFixer(), services));
        this.overworld = awaitOverworld(server);
        stopNetworkListener();
        LOGGER.info("宿主初始化：完成（维度 {}，世界高度 {} ~ {}，总耗时 {} ms）",
                overworld.dimension().identifier(),
                overworld.getMinY(), overworld.getMaxY(), System.currentTimeMillis() - startedAt);
    }

    /** 环境宿主（主世界那一层）。 */
    public ServerLevel overworld() {
        if (overworld == null) {
            throw new IllegalStateException("宿主尚未就绪");
        }
        return overworld;
    }

    /** 宿主是否已就绪。 */
    public boolean ready() {
        return overworld != null;
    }

    /** 停用对外监听的结果说明（诊断用）。 */
    public String listenerStateCn() {
        return listenerStopped ? "已停用（不接受任何外部连接）" : "停用失败（详见日志）";
    }

    /** 宿主世界目录（清理规则用；见 {@code SeedWorkerMain}）。 */
    public Path universeDir() {
        return universeDir;
    }

    @Override
    public void close() {
        MinecraftServer current = server;
        if (current == null) {
            return;
        }
        try {
            current.halt(true);
            long deadline = System.currentTimeMillis() + 10_000L;
            while (!current.isStopped() && System.currentTimeMillis() < deadline) {
                Thread.sleep(50L);
            }
            LOGGER.info("宿主已停机（isStopped={}）", current.isStopped());
        } catch (Throwable error) {
            LOGGER.warn("宿主停机时出现异常（继续收尾）", error);
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // 内部实现
    // ────────────────────────────────────────────────────────────────────────

    /**
     * 写宿主专用 {@code server.properties}。
     *
     * <p>目标：<b>能构造 ServerLevel，且不含任何对外的服务器能力</b>。
     * 其中 {@code generate-structures=true} 是<b>语义必需</b>：正式预测器要读
     * {@code WorldGenSettings#options().generateStructures()}，必须与单人 Oracle 一致。</p>
     */
    private void writeServerProperties(Path path) throws IOException {
        String content = """
                # 由 yiyiaddon 自动生成：本地世界生成计算器专用配置，请勿手工修改。
                # 这不是给玩家连接的服务器：只绑定回环，且宿主起来后会立刻停用监听。
                server-ip=127.0.0.1
                server-port=%d
                online-mode=false
                enable-status=false
                enable-rcon=false
                enable-query=false
                max-players=1
                white-list=false
                enforce-secure-profile=false
                network-compression-threshold=-1
                hide-online-players=true
                log-ips=false
                prevent-proxy-connections=false
                player-idle-timeout=0
                rate-limit=0
                allow-flight=false
                allow-nether=false
                level-name=%s
                level-seed=0
                level-type=minecraft:normal
                generate-structures=true
                spawn-monsters=false
                spawn-animals=false
                spawn-npcs=false
                difficulty=peaceful
                gamemode=survival
                hardcore=false
                pvp=false
                view-distance=3
                simulation-distance=3
                sync-chunk-writes=false
                max-tick-time=-1
                """.formatted(hostPort, LEVEL_NAME);
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }

    /** 数据包 / 世界数据装载配置（与 {@code Main#loadOrCreateConfig} 同一口径）。 */
    private static WorldLoader.InitConfig loadOrCreateConfig(DedicatedServerProperties properties,
                                                             Dynamic<?> worldData, PackRepository repository,
                                                             boolean freshWorld) {
        WorldDataConfiguration dataConfiguration = freshWorld
                ? new WorldDataConfiguration(properties.initialDataPackConfiguration, FeatureFlags.DEFAULT_FLAGS)
                : LevelStorageSource.readDataConfig(worldData);
        WorldLoader.PackConfig packConfig = new WorldLoader.PackConfig(repository, dataConfiguration, false,
                freshWorld);
        return new WorldLoader.InitConfig(packConfig, Commands.CommandSelection.DEDICATED,
                properties.functionPermissions);
    }

    /** 装载 {@code WorldStem}（既支持读旧世界，也支持新建世界）。 */
    private static WorldStem loadWorldStem(WorldLoader.InitConfig initConfig,
                                            LevelStorageSource.LevelStorageAccess access,
                                            DedicatedServerProperties properties, Dynamic<?> worldData) throws Exception {
        return Util.blockUntilDone(executor -> WorldLoader.load(initConfig,
                context -> loadWorldData(context, access, properties, worldData),
                (resourceManager, resources, registries, cookie) ->
                        new WorldStem(resourceManager, resources, registries, cookie),
                Util.backgroundExecutor(), executor)).get();
    }

    /** 世界数据：有旧数据就修复读入，没有就按宿主配置新建（与 {@code Main#createNewWorldData} 同口径）。 */
    private static WorldLoader.DataLoadOutput<LevelDataAndDimensions.WorldDataAndGenSettings> loadWorldData(
            WorldLoader.DataLoadContext context, LevelStorageSource.LevelStorageAccess access,
            DedicatedServerProperties properties, Dynamic<?> worldData) {
        Registry<LevelStem> levelStems = context.datapackDimensions().lookupOrThrow(Registries.LEVEL_STEM);
        if (worldData != null) {
            LevelDataAndDimensions dimensions = LevelStorageSource.getLevelDataAndDimensions(access, worldData,
                    context.dataConfiguration(), levelStems, context.datapackWorldgen());
            return new WorldLoader.DataLoadOutput<>(dimensions.worldDataAndGenSettings(),
                    dimensions.dimensions().dimensionsRegistryAccess());
        }
        LevelSettings levelSettings = new LevelSettings(properties.levelName, properties.gameMode.get(),
                new LevelSettings.DifficultySettings(properties.difficulty.get(), properties.hardcore, false),
                false, context.dataConfiguration());
        WorldOptions worldOptions = properties.worldOptions;
        WorldDimensions dimensions = properties.createDimensions(context.datapackWorldgen());
        WorldDimensions.Complete complete = dimensions.bake(levelStems);
        Lifecycle lifecycle = complete.lifecycle().add(context.datapackWorldgen().allRegistriesLifecycle());
        WorldData levelData = new PrimaryLevelData(levelSettings, complete.specialWorldProperty(), lifecycle);
        return new WorldLoader.DataLoadOutput<>(
                new LevelDataAndDimensions.WorldDataAndGenSettings(levelData,
                        new WorldGenSettings(worldOptions, dimensions)),
                complete.dimensionsRegistryAccess());
    }

    /** 等主世界那一层出现（服务端在它自己的线程里建世界，这里只轮询）。 */
    private static ServerLevel awaitOverworld(MinecraftServer server) throws InterruptedException {
        long deadline = System.currentTimeMillis() + LEVEL_READY_TIMEOUT_MILLIS;
        while (System.currentTimeMillis() < deadline) {
            ServerLevel level = server.getLevel(Level.OVERWORLD);
            if (level != null) {
                return level;
            }
            if (server.isStopped()) {
                throw new IllegalStateException("Vanilla 宿主在建立主世界之前就停了");
            }
            TimeUnit.MILLISECONDS.sleep(100L);
        }
        throw new IllegalStateException("等待主世界就绪超时（" + LEVEL_READY_TIMEOUT_MILLIS + " ms）");
    }

    /**
     * 停用宿主的对外监听。
     *
     * <p>口径第三十八节：Worker 不是给玩家连接的服务器，禁止开放 Minecraft 端口。
     * 原版 {@code DedicatedServer} 起来时一定会绑一次端口（回环、端口由客户端随机选），
     * 因此这里在监听建立之后立刻把它关掉，并把结果写进日志（成功 / 失败都要留证据）。</p>
     */
    private void stopNetworkListener() {
        try {
            if (server.getConnection() != null) {
                server.getConnection().stop();
                listenerStopped = true;
            }
        } catch (Throwable error) {
            listenerStopped = false;
            LOGGER.warn("停用宿主对外监听失败（宿主只绑定 127.0.0.1，影响有限）", error);
        }
    }

    /**
     * Worker 专用 Vanilla 服务端：在 {@code initServer()} 之后立刻停用对外监听。
     *
     * <p>只覆写这一处，不改变任何 worldgen 行为 —— 世界生成路径与真正的 Vanilla 服务端完全相同。</p>
     */
    private static final class WorkerServer extends DedicatedServer {

        WorkerServer(Thread thread, LevelStorageSource.LevelStorageAccess access, PackRepository repository,
                     WorldStem stem, Optional<GameRules> gameRules, DedicatedServerSettings settings,
                     DataFixer fixer, Services services) {
            // 26.2 差异（第 237 条）：DedicatedServer 构造器尾部新增两个参数 ——
            // 管理用 JSON-RPC 服务端（@Nullable）与通知管理器（不可为空，MinecraftServer 会直接调它）。
            // Worker 是最小化宿主：不开管理端口（传 null，与「禁止开放对外通道」同一口径），
            // 通知管理器只用作空实现（不注册任何 NotificationService，等价于没有通知出口）。
            super(thread, access, repository, stem, gameRules, settings, fixer, services,
                    null, new NotificationManager());
        }

        @Override
        protected boolean initServer() throws IOException {
            boolean initialized = super.initServer();
            try {
                if (getConnection() != null) {
                    getConnection().stop();
                }
            } catch (Throwable error) {
                LOGGER.warn("停用对外监听失败（宿主只绑定 127.0.0.1）", error);
            }
            return initialized;
        }
    }
}
