package com.yiyiaddon.feature.mining.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.mining.model.LootMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动挖矿全部设置项的数据载体（旧项目 55 项中的 54 项 + 4 个隐藏项）。
 *
 * <p>设置名、描述、默认值与取值域逐字来自旧项目 {@code mining/AutoMinerModule.java:218-708}，
 * 每个字段上方的注释格式为 {@code 设置名｜描述}，供后续批次的配置页逐字对齐；禁止增删设置项、
 * 禁止改名、禁止改默认值（例外见 {@link #fastBreak} 与 {@link #espContainerTextScale} /
 * {@link #espContainerTextColor} —— 后两项是用户 2026-09-17 追加的容器标签文字设置，旧项目没有对应项；
 * 以及 {@link #lavaAvoidRadius} —— 用户 2026-09-17 追加的「透视到岩浆多少格就不主动靠近」，
 * {@link #veinMiner} 及其 4 项配置 —— 用户 2026-09-17 追加的连锁挖矿；
 * {@link #breakProgressEsp} 及其颜色两项 —— 用户 2026-09-18 追加的挖掘进度 ESP 独立开关与配色；
 * {@link #breakSpawner} —— 用户 2026-09-18 追加的刷怪笼优先破坏；
 * {@link #statusBroadcast} —— 用户 2026-09-18 追加的状态播报开关（含相同播报自动折叠）；
 * 以及 {@link #mobAvoidance} 与 {@link #lavaEspRange} 的<b>默认值</b>
 * —— 用户 2026-09-18 裁定分别改为「关」与 16 格，字段本体与旧项目一致；
 * {@link #autoDisconnect} 及其 {@link #autoDisconnectHealth} —— 用户 2026-09-18 追加的自动断线
 * （血量掉到设定格数立即退出服务器，防死亡掉落））。</p>
 *
 * <p>旧项目分组（{@code :99-103}）：{@code 目标选择} / {@code 传送指令} / {@code 触发条件} /
 * {@code 物品管理} / {@code Baritone调优}。</p>
 *
 * <p><b>本轮留白</b>：种子挖矿（{@code 启用种子挖矿} / {@code 世界种子} / {@code 渲染范围} /
 * {@code 矿石渲染颜色} / {@code 脉冲效果}）随种子模式（OrePredictor）一起留待后续批次，
 * 故本类不含这 5 项。</p>
 *
 * <p>颜色以 ARGB 打包整数保存，便于 JSON 持久化；解码用 {@link #r(int)} / {@link #g(int)} /
 * {@link #b(int)} / {@link #a(int)}。</p>
 */
public final class MiningSettings {

    // ━━━ 目标选择（旧项目 :218-243） ━━━

    /** 采集模式｜精准采集：目标选择器显示原矿；时运：目标选择器显示掉落物（粗铁/粗金/粗铜等）。切换模式时自动同步目标 */
    public LootMode lootMode = LootMode.FORTUNE;

    /** 主世界矿石｜时运模式选掉落物（粗铁/粗金/粗铜等），精准采集选原矿（铁矿石等）。存物品 ID，空串 = 未选择 */
    public String overworldOreTarget = "";

    /** 下界矿石｜时运模式选掉落物（下界残骸/金粒/石英），精准采集选原矿（下界残骸等）。存物品 ID，空串 = 未选择 */
    public String netherOreTarget = "";

    /** 普通方块｜选择普通方块（石头、泥土、原木等）。存方块 ID，空串 = 未选择 */
    public String blockTarget = "";

    // ━━━ 传送指令（旧项目 :249-308） ━━━

    /** 前往挖矿指令｜传送到挖矿区域的指令（支持带/或不带/） */
    public String wildCommand = "";

    /** RTP需要GUI选择｜指令后自动扫描GUI点击匹配按钮 */
    public boolean rtpGuiEnabled = false;

    /** GUI按钮关键词｜输入纯文本（如'主世界'会匹配'§a主 §e世 §b界'），自动忽略颜色和空格 */
    public String rtpGuiKeyword = "主世界";

    /** 返回卸货指令｜传送到卸货箱的指令 */
    public String unloadCommand = "";

    /** 前往补给指令｜传送到食物箱的指令 */
    public String supplyCommand = "";

    /** 前往修复指令｜传送到挂机修补点 */
    public String afkCommand = "";

    /** 死亡返回指令｜复活后返回挂机点 */
    public String respawnCommand = "";

    /** 传送等待时长｜执行传送指令后等待秒数（默认 8，取值域 1~120） */
    public int teleportDelay = 8;

    /** RTP冷却时长｜服务器 RTP 传送冷却秒数：传送失败后等这么久再重试，避免冷却期空发指令（默认 60，取值域 1~3600） */
    public int rtpCooldown = 60;

    /**
     * 传送失败自动重试｜关掉后：判定「传送未生效」也不再重发传送指令，只继续等传送生效。
     * 用户 2026-09-18 追加：服务器自身有传送冷却 / RTP 排队延迟时，重发会导致「传送成功之后又被传送一次」。
     */
    public boolean teleportRetryEnabled = true;

    // ━━━ 触发条件（旧项目 :314-345） ━━━

    /** 满载组数｜背包矿物达到多少组时触发卸货（默认 20，取值域 1~36） */
    public int unloadThreshold = 20;

    /** 食物阈值｜背包食物少于此数量时触发补给（默认 32，取值域 1~64） */
    public int hungerThreshold = 32;

    /** 耐久阈值｜工具剩余耐久低于此值时前往挂机点修补（下界合金镐耐久 2031，上限已放宽）（默认 100，取值域 1~3000） */
    public int durabilityThreshold = 100;

    /** 潜影盒打包机｜卸货时把矿物箱(潜影盒)填满，检测到满后等红石推盒换新盒，自动重开箱继续放，直到背包目标矿放完才RTP。给搭配潜影盒打包机的挂机用户使用。 */
    public boolean shulkerPacker = false;

    // ━━━ 自动断线（用户 2026-09-18 追加，旧项目没有对应项） ━━━

    /**
     * 自动断线｜血量掉到 {@link #autoDisconnectHealth} 设定的格数时，立即断开当前服务器连接。
     *
     * <p><b>为什么需要它</b>：服务器开启死亡掉落时，挂机挖矿一旦被打死就会丢掉身上全部装备与矿物；
     * 血量见底时抢先退出服务器，比「死了再自动重生」更能保住财产（重生换不回掉落物）。
     * 断线实体动作复用模块里的 {@code AdminDisconnect}（旧「自动断线」模块的唯一实现），
     * 本模块只负责判定时机，不再另写一套断线链路。</p>
     */
    public boolean autoDisconnect = true;

    /**
     * 断线血量｜血量低于或等于这一格数时触发自动断线（默认 2，取值域 1~20 格）。
     *
     * <p><b>单位是「格」不是「点」</b>：界面上血量条一格 = 2 点血量，玩家按格数思考，
     * 故设置项与播报都用格；判定时再换算成点数（{@code 格 × 2}）与 {@code Player#getHealth()} 比较。
     * 上限 20 格是给「额外生命」属性加成留的余量（原版满血玩家共 10 格）。</p>
     */
    public int autoDisconnectHealth = 2;

    // ━━━ 物品管理（旧项目 :351-378） ━━━

    /** 保留白名单｜默认保留任意品质工具、白名单食物、目标矿物；此名单内的额外物品/方块也不会被丢弃。存物品 ID */
    public final List<String> keepWhitelist = new ArrayList<>();

    /** 食物白名单｜从食物箱只拿选中的食物（只显示能吃的食物，默认常用食物，可自由增删）。存物品 ID */
    public final List<String> foodWhitelist = new ArrayList<>(List.of(
        "minecraft:cooked_beef",
        "minecraft:cooked_porkchop",
        "minecraft:golden_carrot",
        "minecraft:bread"
    ));

    /** 搭路方块白名单｜Baritone搭桥/填坑时使用这些方块，且只保留各一组（多余自动丢弃）。存方块 ID */
    public final List<String> placeBlocks = new ArrayList<>(List.of(
        "minecraft:cobblestone",
        "minecraft:netherrack"
    ));

    // ━━━ 秒破（旧项目 :433-452） ━━━

    /**
     * 快速破坏（秒破）｜使用 START→服务端 0.7 最早阈值→STOP 的真实发包流程加速破坏；硬方块会等待服务端所需 tick，不提前制造客户端空气墙。
     *
     * <p><b>默认值属有意改动</b>：旧项目源码为 {@code defaultValue(true)}（秒破默认开），
     * 用户硬约束要求秒破必须独立开关且默认关闭，故本项目落地为 {@code false}，
     * 已登记进差异清单。</p>
     */
    /**
     * 秒破（发包破坏）：默认开启，与旧项目一致。
     *
     * <p>原移植版本刻意默认关；用户 2026-09-17 裁定「秒破也默认打开」——
     * 连锁挖矿复用秒破的发包通道，秒破关着会让默认开启的连锁静默失效。
     * 注意旧存档 {@code module-state.json} 里已经写过 {@code fastBreak=false} 的仍按存档值生效。</p>
     */
    public boolean fastBreak = true;

    /** 绕过反作弊｜兼容旧配置：STOP 后对相邻位置补发一次 ABORT；不能保证绕过服务器反作弊，异常时请关闭 */
    public boolean bypassAnticheat = false;

    /**
     * 秒破间隔（tick）｜发出破坏请求（STOP）后，开始下一块前的最小等待 tick（默认 2，取值域 0~20）。
     *
     * <p>流水线之后这个间隔不再包含「等服务端确认方块变化」的那一段（那一段已经异步化，
     * 见 {@code MiningFastBreakController} 类注释），所以它的实际含义变成「两块之间的最小间隔」：
     * 想让秒破最快就把这里设 0，模块仍会强制留 1 刻保险；设大一点可降低被服务端/反作弊盯上的概率。</p>
     */
    public int breakInterval = 2;

    // ━━━ Baritone 开关类（旧项目 :455-586） ━━━

    /** 破坏阻挡方块｜挖掘时允许破坏阻挡路径的方块（石头、泥土等） */
    public boolean allowBreak = true;

    /** 寻路物流破坏方块｜前往矿物箱/食物箱/挂机点寻路时，是否允许破坏阻挡方块抄近路（关闭后旁边有路就绕行，不再挖墙） */
    public boolean logisticsBreakBlocks = false;

    /** 放置方块｜允许搭桥或填坑（需要背包里有方块） */
    public boolean allowPlace = true;

    /** 自动整理物品栏｜允许Baritone自动将物品从背包移到快捷栏（工具、方块等） */
    public boolean allowInventory = true;

    /** 自动切换工具｜挖掘时自动选择最佳工具（镐子挖石头、铲子挖土等） */
    public boolean autoTool = true;

    /** 避开岩浆｜禁止 Baritone 将岩浆作为正常寻路路径 */
    public boolean avoidLava = true;

    /** 岩浆透视｜高亮显示附近岩浆方块，挖矿时更直观看到岩浆位置 */
    public boolean lavaEsp = true;

    /** 岩浆透视范围｜透视岩浆的扫描半径（格），默认 16 —— 只有 8 时隔着十几格的岩浆湖看不到框（用户 2026-09-18） */
    public int lavaEspRange = 16;

    /** 岩浆安全距离｜透视到岩浆进入这个距离就停止挖矿并撤离（默认 2，取值域 1~4；用户 2026-09-17 追加，旧项目没有对应项） */
    public int lavaAvoidRadius = 2;

    // ━━━ 连锁挖矿（用户 2026-09-17 追加，旧项目没有对应项） ━━━

    /** 连锁挖矿｜挖到一个矿物时自动把与它连着的同类矿物一起挖掉（配合秒破效率最高，需要开启秒破） */
    public boolean veinMiner = true;

    /** 连锁最大方块数｜单次连锁最多挖多少块（默认 32，取值域 1~128；防止一条巨型矿脉长时间卡住挖矿流程） */
    public int veinMaxBlocks = 32;

    /** 连锁搜索距离｜以首个方块为中心最多向外连多少格（默认 4，取值域 1~8） */
    public int veinRange = 4;

    /** 连锁对角相邻｜把斜向相邻的矿物也算连在一起（关闭后只连上下左右前后 6 个面） */
    public boolean veinDiagonal = true;

    /** 连锁仅同类矿物｜只连锁同一种矿物（深板岩钻石矿与原版钻石矿算同类；关闭后旁边任何矿物都会被连锁） */
    public boolean veinFamilyOnly = true;

    // ━━━ 刷怪笼优先（用户 2026-09-18 追加，旧项目没有对应项） ━━━

    /** 刷怪笼优先破坏｜寻路途中发现附近有刷怪笼就先挖掉它，再继续打怪/挖矿，避免越打越多怪 */
    public boolean breakSpawner = true;

    /** 状态播报｜关闭后模块运行时不再发状态类播报（错误与致命提示仍保留）；相同内容的播报 5 秒内自动折叠，只显示一次 */
    public boolean statusBroadcast = true;

    /** 怪物规避｜提高怪物附近路径代价，尽量绕开危险区域（用户 2026-09-18 裁定默认关闭：开着会连刷怪笼一起绕，与本项目自己的战斗/刷怪笼先挖逻辑打架） */
    public boolean mobAvoidance = false;

    /** 掉落方块暂停｜遇到沙子、沙砾等掉落方块时暂停挖掘。关闭后不掉方块不暂停，挖矿更流畅（会塌方区域建议手动开启） */
    public boolean pauseMiningForFallingBlocks = false;

    /**
     * 寻路视角跟随｜视角跟着男中音的寻路方向走（默认开；用户 2026-09-18 追加，旧项目没有对应项）。
     *
     * <p><b>为什么需要它</b>：男中音默认 {@code freeLook = true}，走路时它只把朝向「静默」发给服务端
     * （{@code LookBehavior.Target.Mode#resolve} → {@code antiCheat ? SERVER : NONE}），本地视角一动不动
     * ——所以「寻路的那个视角」根本看不见。把 {@code freeLook} 关掉后走路也落到 CLIENT 分支，
     * 视角由男中音每刻写向下一个路径节点（挖矿时本来就一直是这样），全程只有一个写入者，因此不抖。</p>
     *
     * <p><b>关掉时</b>：完全恢复男中音原行为（走路视角不动、鼠标完全由玩家自己控制）。
     * 战斗期间视角临时让回战斗逻辑盯着怪（见 {@code MiningPathing#setCombatViewHold}）。</p>
     */
    public boolean pathViewFollow = true;

    /** 疾跑上坡｜上坡时提前一格疾跑+跳跃，提升速度 */
    public boolean sprintAscends = true;

    /** 允许跑酷｜允许跨越1-4格的跑酷跳跃（有一定风险） */
    public boolean allowParkour = false;

    /** 跑酷搭桥｜跑酷跳跃中途放置方块来延长距离（需开启放置方块） */
    public boolean allowParkourPlace = false;

    /** 对角线上升｜允许斜向上跳跃，速度更快但消耗更多饥饿值 */
    public boolean allowDiagonalAscend = false;

    /** 对角线下降｜允许斜向下降，速度更快但有一定风险（地狱慎用） */
    public boolean allowDiagonalDescend = false;

    /** 仅挖暴露矿石｜只挖掘能从指定距离看到的矿石，减少无效挖掘 */
    public boolean allowOnlyExposedOres = false;

    /** 失败目标暂时跳过｜矿点无法到达时跳过最近目标，避免反复卡住 */
    public boolean blacklistClosestOnFailure = true;

    /** 合法挖掘模式｜启用合法挖掘限制（关闭可提启效率但可能被检测） */
    public boolean legitMine = false;

    /** 合法挖掘检测对角矿石｜合法挖掘时检测与已发现矿石对角相邻的矿石 */
    public boolean legitMineIncludeDiagonals = false;

    // ━━━ 数值类（旧项目 :589-670） ━━━

    /** 矿点刷新间隔｜每隔多少tick重新扫描矿点（值越小越优先挖近矿；过小会导致寻路线乱闪、人物频繁停顿，40tick约2秒最稳定）（默认 40，取值域 1~100） */
    public int mineGoalUpdateInterval = 40;

    /** 矿点缓存数量｜Baritone一次缓存的最大矿点数量。太少会找不到矿（寻路失败），太多会路闪。64 缓存充足且稳定（默认 64，取值域 1~256） */
    public int mineMaxOreLocationsCount = 64;

    /** 怪物规避半径｜计算怪物危险区域的半径（默认 8，取值域 1~16） */
    public int mobAvoidanceRadius = 8;

    /** 最大坠落高度｜允许从多高的地方跳下（超过会绕路）（默认 3，取值域 0~20） */
    public int maxFallHeight = 3;

    /** 暴露矿石检测距离｜判断矿石是否暴露时使用的检测距离（默认 1，取值域 1~8） */
    public int allowOnlyExposedOresDistance = 1;

    /** 最低挖掘高度｜Baritone 挖矿时不会低于此高度（默认 -64，取值域 -64~320） */
    public int minYLevelWhileMining = -64;

    /** 最高挖掘高度｜Baritone 挖矿时不会高于此高度（默认 320，取值域 -64~320） */
    public int maxYLevelWhileMining = 320;

    /** 合法挖掘高度｜合法挖掘模式进行条带探索时使用的高度（默认 12，取值域 -64~320） */
    public int legitMineYLevel = 12;

    // ━━━ ESP（旧项目隐藏设置 :681-708，本轮进设置项并落盘） ━━━

    /** _esp_scale_internal｜ESP 字号倍率（默认 2.0） */
    public double espScale = 2.0;

    /** _mineral_color_internal｜矿物箱 ESP 颜色，默认 (255,215,0) */
    public int mineralColor = 0xFFFFD700;

    /** _food_color_internal｜食物箱 ESP 颜色，默认 (100,255,100) */
    public int foodColor = 0xFF64FF64;

    /** _afk_color_internal｜挂机修复点 ESP 颜色，默认 (255,100,255) */
    public int afkColor = 0xFFFF64FF;

    // ━━━ 容器标签文字（用户 2026-09-17 追加：矿物箱 / 食物箱的头顶文字要能单独调字号与颜色） ━━━

    /**
     * 容器标签字号倍率｜矿物箱 / 食物箱头顶文字在 {@link #espScale} 之上再乘的系数。
     *
     * <p><b>为什么默认 1.0 而不是 2.0</b>：整体字号已由 {@link #espScale}（默认 2.0）决定，本项是容器
     * 标签的<b>额外</b>倍率，取 1.0 时实际字号仍是 2.0 —— 不动这一项就与既有渲染完全一致（保证默认观感
     * 不变）。挂机修复点不是容器，不受本项影响，仍只乘 {@link #espScale}。</p>
     */
    public double espContainerTextScale = 1.0;

    /**
     * 容器标签文字主色｜矿物箱 / 食物箱头顶文字的 ARGB；{@code 0} = 跟随各点位自己的颜色（默认）。
     *
     * <p><b>为什么用 0 表示「跟随」而不是直接给一个颜色</b>：两个容器标签当前用的主色并不相同
     * （矿物箱 §6 金、食物箱 §2 绿），单一颜色字段不可能同时等于两者，直接给值必然改掉其中一类的观感；
     * 取 0 时渲染走原路径（标签头部保留类型色码、基准色取类型色），非 0 时才用它替换标签头部的主色码
     * —— 颜色码会逐段压过传入的基准色，不替换头部色码，改色就只改到标签里的空格（表现为「改了没反应」）。</p>
     */
    public int espContainerTextColor = 0;

    // ━━━ 挖掘进度 ESP（用户 2026-09-18 追加，旧项目没有对应项） ━━━

    /**
     * 挖掘进度显示｜自动挖矿时在被破坏的方块上显示「百分比 + 随进度收缩的框」（默认开）。
     *
     * <p>纯渲染开关：关掉只影响画面，不影响秒破的发包与挖矿行为。
     * 颜色两项是 <b>RGB</b>（{@code 0xRRGGBB}），面 / 描边的透明度由渲染层固定
     * （面 40 / 描边 255），用户只改色不改透明度，避免调出一个看不见的进度框。</p>
     */
    public boolean breakProgressEsp = true;

    /** 挖掘进度颜色｜挖掘中（默认 0xCC2020 红） */
    public int breakProgressBusyColor = 0xCC2020;

    /** 挖掘进度颜色｜已完成（默认 0x20CC50 绿） */
    public int breakProgressReadyColor = 0x20CC50;

    // ━━━ 颜色解码 ━━━

    public static int a(int argb) {
        return (argb >> 24) & 0xFF;
    }

    public static int r(int argb) {
        return (argb >> 16) & 0xFF;
    }

    public static int g(int argb) {
        return (argb >> 8) & 0xFF;
    }

    public static int b(int argb) {
        return argb & 0xFF;
    }

    /** 打包为 ARGB */
    public static int argb(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

    // ━━━ 持久化 ━━━

    /** 写入 JSON（键名与本项目其它模块配置文件保持一致风格） */
    public void save(JsonObject json) {
        json.addProperty("lootMode", lootMode.name());
        json.addProperty("overworldOreTarget", overworldOreTarget);
        json.addProperty("netherOreTarget", netherOreTarget);
        json.addProperty("blockTarget", blockTarget);

        json.addProperty("wildCommand", wildCommand);
        json.addProperty("rtpGuiEnabled", rtpGuiEnabled);
        json.addProperty("teleportRetryEnabled", teleportRetryEnabled);
        json.addProperty("rtpGuiKeyword", rtpGuiKeyword);
        json.addProperty("unloadCommand", unloadCommand);
        json.addProperty("supplyCommand", supplyCommand);
        json.addProperty("afkCommand", afkCommand);
        json.addProperty("respawnCommand", respawnCommand);
        json.addProperty("teleportDelay", teleportDelay);
        json.addProperty("rtpCooldown", rtpCooldown);

        json.addProperty("unloadThreshold", unloadThreshold);
        json.addProperty("hungerThreshold", hungerThreshold);
        json.addProperty("durabilityThreshold", durabilityThreshold);
        json.addProperty("shulkerPacker", shulkerPacker);
        json.addProperty("autoDisconnect", autoDisconnect);
        json.addProperty("autoDisconnectHealth", autoDisconnectHealth);

        json.add("keepWhitelist", stringArray(keepWhitelist));
        json.add("foodWhitelist", stringArray(foodWhitelist));
        json.add("placeBlocks", stringArray(placeBlocks));

        json.addProperty("fastBreak", fastBreak);
        json.addProperty("bypassAnticheat", bypassAnticheat);
        json.addProperty("breakInterval", breakInterval);

        json.addProperty("allowBreak", allowBreak);
        json.addProperty("logisticsBreakBlocks", logisticsBreakBlocks);
        json.addProperty("allowPlace", allowPlace);
        json.addProperty("allowInventory", allowInventory);
        json.addProperty("autoTool", autoTool);
        json.addProperty("avoidLava", avoidLava);
        json.addProperty("lavaEsp", lavaEsp);
        json.addProperty("lavaEspRange", lavaEspRange);
        json.addProperty("lavaAvoidRadius", lavaAvoidRadius);
        json.addProperty("veinMiner", veinMiner);
        json.addProperty("veinMaxBlocks", veinMaxBlocks);
        json.addProperty("veinRange", veinRange);
        json.addProperty("veinDiagonal", veinDiagonal);
        json.addProperty("veinFamilyOnly", veinFamilyOnly);
        json.addProperty("breakSpawner", breakSpawner);
        json.addProperty("statusBroadcast", statusBroadcast);
        json.addProperty("mobAvoidance", mobAvoidance);
        json.addProperty("pauseMiningForFallingBlocks", pauseMiningForFallingBlocks);
        json.addProperty("pathViewFollow", pathViewFollow);
        json.addProperty("sprintAscends", sprintAscends);
        json.addProperty("allowParkour", allowParkour);
        json.addProperty("allowParkourPlace", allowParkourPlace);
        json.addProperty("allowDiagonalAscend", allowDiagonalAscend);
        json.addProperty("allowDiagonalDescend", allowDiagonalDescend);
        json.addProperty("allowOnlyExposedOres", allowOnlyExposedOres);
        json.addProperty("blacklistClosestOnFailure", blacklistClosestOnFailure);
        json.addProperty("legitMine", legitMine);
        json.addProperty("legitMineIncludeDiagonals", legitMineIncludeDiagonals);

        json.addProperty("mineGoalUpdateInterval", mineGoalUpdateInterval);
        json.addProperty("mineMaxOreLocationsCount", mineMaxOreLocationsCount);
        json.addProperty("mobAvoidanceRadius", mobAvoidanceRadius);
        json.addProperty("maxFallHeight", maxFallHeight);
        json.addProperty("allowOnlyExposedOresDistance", allowOnlyExposedOresDistance);
        json.addProperty("minYLevelWhileMining", minYLevelWhileMining);
        json.addProperty("maxYLevelWhileMining", maxYLevelWhileMining);
        json.addProperty("legitMineYLevel", legitMineYLevel);

        json.addProperty("espScale", espScale);
        json.addProperty("mineralColor", mineralColor);
        json.addProperty("foodColor", foodColor);
        json.addProperty("afkColor", afkColor);
        json.addProperty("espContainerTextScale", espContainerTextScale);
        json.addProperty("espContainerTextColor", espContainerTextColor);
        json.addProperty("breakProgressEsp", breakProgressEsp);
        json.addProperty("breakProgressBusyColor", breakProgressBusyColor);
        json.addProperty("breakProgressReadyColor", breakProgressReadyColor);
    }

    /** 读取 JSON；缺项保留默认值，非法枚举值回退默认，整数按各自取值域 clamp */
    public void load(JsonObject json) {
        if (json == null) return;

        lootMode = enumOf(json, "lootMode", LootMode.class, lootMode);
        overworldOreTarget = stringOf(json, "overworldOreTarget", overworldOreTarget);
        netherOreTarget = stringOf(json, "netherOreTarget", netherOreTarget);
        blockTarget = stringOf(json, "blockTarget", blockTarget);

        wildCommand = stringOf(json, "wildCommand", wildCommand);
        rtpGuiEnabled = boolOf(json, "rtpGuiEnabled", rtpGuiEnabled);
        rtpGuiKeyword = stringOf(json, "rtpGuiKeyword", rtpGuiKeyword);
        teleportRetryEnabled = boolOf(json, "teleportRetryEnabled", teleportRetryEnabled);
        unloadCommand = stringOf(json, "unloadCommand", unloadCommand);
        supplyCommand = stringOf(json, "supplyCommand", supplyCommand);
        afkCommand = stringOf(json, "afkCommand", afkCommand);
        respawnCommand = stringOf(json, "respawnCommand", respawnCommand);
        teleportDelay = clamp(intOf(json, "teleportDelay", teleportDelay), 1, 120);
        rtpCooldown = clamp(intOf(json, "rtpCooldown", rtpCooldown), 1, 3600);

        unloadThreshold = clamp(intOf(json, "unloadThreshold", unloadThreshold), 1, 36);
        hungerThreshold = clamp(intOf(json, "hungerThreshold", hungerThreshold), 1, 64);
        durabilityThreshold = clamp(intOf(json, "durabilityThreshold", durabilityThreshold), 1, 3000);
        shulkerPacker = boolOf(json, "shulkerPacker", shulkerPacker);
        autoDisconnect = boolOf(json, "autoDisconnect", autoDisconnect);
        autoDisconnectHealth = clamp(intOf(json, "autoDisconnectHealth", autoDisconnectHealth), 1, 20);

        loadList(json, "keepWhitelist", keepWhitelist);
        loadList(json, "foodWhitelist", foodWhitelist);
        loadList(json, "placeBlocks", placeBlocks);

        fastBreak = boolOf(json, "fastBreak", fastBreak);
        bypassAnticheat = boolOf(json, "bypassAnticheat", bypassAnticheat);
        breakInterval = clamp(intOf(json, "breakInterval", breakInterval), 0, 20);

        allowBreak = boolOf(json, "allowBreak", allowBreak);
        logisticsBreakBlocks = boolOf(json, "logisticsBreakBlocks", logisticsBreakBlocks);
        allowPlace = boolOf(json, "allowPlace", allowPlace);
        allowInventory = boolOf(json, "allowInventory", allowInventory);
        autoTool = boolOf(json, "autoTool", autoTool);
        avoidLava = boolOf(json, "avoidLava", avoidLava);
        lavaEsp = boolOf(json, "lavaEsp", lavaEsp);
        lavaEspRange = clamp(intOf(json, "lavaEspRange", lavaEspRange), 2, 16);
        lavaAvoidRadius = clamp(intOf(json, "lavaAvoidRadius", lavaAvoidRadius), 1, 4);
        veinMiner = boolOf(json, "veinMiner", veinMiner);
        veinMaxBlocks = clamp(intOf(json, "veinMaxBlocks", veinMaxBlocks), 1, 128);
        veinRange = clamp(intOf(json, "veinRange", veinRange), 1, 8);
        veinDiagonal = boolOf(json, "veinDiagonal", veinDiagonal);
        veinFamilyOnly = boolOf(json, "veinFamilyOnly", veinFamilyOnly);
        breakSpawner = boolOf(json, "breakSpawner", breakSpawner);
        statusBroadcast = boolOf(json, "statusBroadcast", statusBroadcast);
        mobAvoidance = boolOf(json, "mobAvoidance", mobAvoidance);
        pauseMiningForFallingBlocks = boolOf(json, "pauseMiningForFallingBlocks", pauseMiningForFallingBlocks);
        pathViewFollow = boolOf(json, "pathViewFollow", pathViewFollow);
        sprintAscends = boolOf(json, "sprintAscends", sprintAscends);
        allowParkour = boolOf(json, "allowParkour", allowParkour);
        allowParkourPlace = boolOf(json, "allowParkourPlace", allowParkourPlace);
        allowDiagonalAscend = boolOf(json, "allowDiagonalAscend", allowDiagonalAscend);
        allowDiagonalDescend = boolOf(json, "allowDiagonalDescend", allowDiagonalDescend);
        allowOnlyExposedOres = boolOf(json, "allowOnlyExposedOres", allowOnlyExposedOres);
        blacklistClosestOnFailure = boolOf(json, "blacklistClosestOnFailure", blacklistClosestOnFailure);
        legitMine = boolOf(json, "legitMine", legitMine);
        legitMineIncludeDiagonals = boolOf(json, "legitMineIncludeDiagonals", legitMineIncludeDiagonals);

        mineGoalUpdateInterval = clamp(intOf(json, "mineGoalUpdateInterval", mineGoalUpdateInterval), 1, 100);
        mineMaxOreLocationsCount = clamp(intOf(json, "mineMaxOreLocationsCount", mineMaxOreLocationsCount), 1, 256);
        mobAvoidanceRadius = clamp(intOf(json, "mobAvoidanceRadius", mobAvoidanceRadius), 1, 16);
        maxFallHeight = clamp(intOf(json, "maxFallHeight", maxFallHeight), 0, 20);
        allowOnlyExposedOresDistance =
            clamp(intOf(json, "allowOnlyExposedOresDistance", allowOnlyExposedOresDistance), 1, 8);
        minYLevelWhileMining = clamp(intOf(json, "minYLevelWhileMining", minYLevelWhileMining), -64, 320);
        maxYLevelWhileMining = clamp(intOf(json, "maxYLevelWhileMining", maxYLevelWhileMining), -64, 320);
        legitMineYLevel = clamp(intOf(json, "legitMineYLevel", legitMineYLevel), -64, 320);

        espScale = doubleOf(json, "espScale", espScale);
        mineralColor = intOf(json, "mineralColor", mineralColor);
        foodColor = intOf(json, "foodColor", foodColor);
        afkColor = intOf(json, "afkColor", afkColor);
        // 容器标签字号倍率按界面取值域 clamp（0.5~4.0）；颜色不做取值域裁剪，非法/缺项保留默认（0 = 跟随）
        espContainerTextScale = clamp(doubleOf(json, "espContainerTextScale", espContainerTextScale), 0.5, 4.0);
        espContainerTextColor = intOf(json, "espContainerTextColor", espContainerTextColor);
        // 挖掘进度 ESP：开关默认开；颜色取 RGB，越界值（含带 alpha 的 ARGB）按 0xFFFFFF 裁剪
        breakProgressEsp = boolOf(json, "breakProgressEsp", breakProgressEsp);
        breakProgressBusyColor = intOf(json, "breakProgressBusyColor", breakProgressBusyColor) & 0xFFFFFF;
        breakProgressReadyColor = intOf(json, "breakProgressReadyColor", breakProgressReadyColor) & 0xFFFFFF;
    }

    // ━━━ 内部 ━━━

    private static JsonArray stringArray(List<String> values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            if (value != null) array.add(value);
        }
        return array;
    }

    /** 列表读取：只有键存在且为数组时才覆盖，缺项保留默认列表 */
    private static void loadList(JsonObject json, String key, List<String> target) {
        if (!json.has(key) || !json.get(key).isJsonArray()) return;
        target.clear();
        for (JsonElement element : json.getAsJsonArray(key)) {
            if (element != null && element.isJsonPrimitive()) target.add(element.getAsString());
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /** 小数设置项的取值域裁剪（与 {@link #clamp(int, int, int)} 同一口径） */
    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        try {
            return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsInt() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static double doubleOf(JsonObject json, String key, double fallback) {
        try {
            return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsDouble() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        try {
            return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsBoolean() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static String stringOf(JsonObject json, String key, String fallback) {
        try {
            return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsString() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static <E extends Enum<E>> E enumOf(JsonObject json, String key, Class<E> type, E fallback) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive()) return fallback;
        try {
            return Enum.valueOf(type, json.get(key).getAsString());
        } catch (Exception ignored) {
            return fallback;
        }
    }
}
