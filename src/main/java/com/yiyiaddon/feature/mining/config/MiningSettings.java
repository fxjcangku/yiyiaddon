package com.yiyiaddon.feature.mining.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.mining.model.LootMode;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.render.world.ShapeMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
 * 以及 {@link #mobAvoidance}、{@link #lavaEspRange}、{@link #lavaAvoidRadius} 与
 * {@link #allowParkour} 等控制台开关的<b>默认值</b>
 * —— 2026-09-18 定稿：控制台开关与岩浆两项数值的默认值<b>一律以用户实机截图为准</b>
 * （疾跑上坡 / 允许跑酷 / 跑酷搭桥 / 对角线上升 / 失败目标暂时跳过 / 刷怪笼优先破坏 /
 * 寻路视角跟随 / 破坏阻挡方块 / 放置方块 / 自动整理物品栏 / 自动切换工具 / 避开岩浆 / 岩浆透视
 * 为<b>开</b>；怪物规避 / 掉落方块暂停 / 对角线下降 / 仅挖暴露矿石 / 合法挖掘模式（含对角检测）/
 * 寻路物流破坏方块为<b>关</b>；{@code lavaEspRange = 16}、{@code lavaAvoidRadius = 2}），
 * 其余设置项一律保持旧项目默认值；
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
 * <p>三类点位（矿物箱 / 食物箱 / 挂机修复点）的「显示 / 颜色 / 渲染模式」由共用件
 * {@link EspRenderObject} 承载（用户 2026-09-19：「把所有标点选择点位位置的模块参照星露谷农场的点位设置」），
 * 落盘键为 {@code render.<对象名>.<项>}，与星露谷点位同一套口径；挖掘进度两项颜色仍是 RGB 整数，
 * 解码用 {@link #r(int)} / {@link #g(int)} / {@link #b(int)} / {@link #a(int)}。</p>
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

    /** 耐久阈值兜底上限｜背包里一件工具都没有时，输入框用这个「原版最高工具耐久」（下界合金镐 2031） */
    public static final int DURABILITY_THRESHOLD_FALLBACK_MAX = 2031;

    /** 耐久阈值绝对上限｜只防配置里的离谱值；真正的上限由「你持有的工具满耐久」动态决定（见 {@code ToolDurability}） */
    public static final int DURABILITY_THRESHOLD_HARD_MAX = 65535;

    /**
     * 耐久阈值｜工具剩余耐久低于此值时前往挂机点修补（默认 100）。
     *
     * <p><b>上限不写死</b>（用户 2026-09-19：「你要自动算手里拿的镐子自动计算耐久度」）：控制台输入框的
     * 上限取「玩家身上可修复工具的满耐久最大值」（木镐 59 / 钻石镐 1561 / 下界合金镐 2031 / 服务器自定义
     * 按实际值），没带工具时才退回 {@link #DURABILITY_THRESHOLD_FALLBACK_MAX}。</p>
     *
     * <p><b>为什么不能有写死的宽上限</b>：旧值域是 1~3000，一旦设得比工具满耐久还高，
     * 「剩余耐久低于阈值」就恒为真 —— 工具只要不是满耐久就被判需修复，修完回矿区又立刻被判需修复，
     * 实机表现即用户 2026-09-19 反馈的「无限循环去挂机点修复」。运行期还有第二道夹紧
     * （{@code MiningStateMachine#needsRepair}：按该工具自己的满耐久取小 + 已满耐久不判需修复）。</p>
     */
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

    /**
     * 食物白名单｜自动挖矿的「食物清单」总口径（只显示能吃的食物）。
     *
     * <p>用户 2026-09-19 裁定「严格白名单：只吃也只留白名单食物」，四处判据全部走本名单：
     * 吃（{@code MiningContainer#isEdible} / 状态机 {@code hasFoodToEat}）、留（{@code shouldKeep}，
     * 白名单外的食物当垃圾丢弃）、统计（{@code countFoodStacks}）、补给取货（{@code withdrawFood}）。
     * 想保留白名单外的某件物品，用职责独立的「保留白名单」（{@link #keepWhitelist}）。存物品 ID</p>
     */
    public final List<String> foodWhitelist = new ArrayList<>(List.of(
        "minecraft:cooked_beef",
        "minecraft:cooked_porkchop",
        "minecraft:golden_carrot",
        "minecraft:bread"
    ));

    /** 搭路方块白名单｜Baritone搭桥/填坑/岩浆垫脚时使用这些方块，且只保留各一组（多余自动丢弃）。存方块 ID */
    public final List<String> placeBlocks = new ArrayList<>(List.of(
        // 2026-09-18 补全默认（用户：「踮脚方块白名单默认加上这些」）：挖矿沿途最常见的五种垫脚料，
        // 原先只有圆石+下界岩，深板岩层里挖到的深板岩/深板岩圆石/石头填不进白名单，垫脚就没料可用
        "minecraft:deepslate",
        "minecraft:cobbled_deepslate",
        "minecraft:stone",
        "minecraft:netherrack",
        "minecraft:cobblestone"
    ));

    // ━━━ 自用模式（用户 2026-09-20 追加，旧项目没有对应项） ━━━
    //
    // 自用模式 = 「挖够就自己去卖掉」：挖满触发组数（或背包先满）时不卸货，改走
    // 「回主城 → 寻路到收购 NPC → 发包交互一键出售 → 回子服 → 继续 RTP 挖」这条流程。
    // 除「不要求绑定矿物箱 / 挂机修复点」「挖满后不去卸货而去出售」两点外，其余一切照旧
    // （秒破、连锁、丢弃、补给、死亡处理都走同一套既有实现）。
    //
    // 页面口径（用户 2026-09-21）：模式决定页签集合 —— 自用模式打开时只留「自用模式」页
    // （目标三选一、挖矿/补给/死亡传送、出售链都在这一页），「目标选择」「传送指令」两页隐藏；
    // 关闭时反过来，「自用模式」页隐藏。两组设置各自只有一处落点，不会互相干扰。
    //
    // 出售物品不单独设项：直接跟随目标三选一（选什么就卖什么，选石头没开精准就卖圆石），
    // 见 AutoMinerModule#expectedSellItem()。
    //
    // 全程静默：所有菜单（快捷菜单 / 传送神兽 / 市场出售）都只读 player.containerMenu 的槽位、
    // 发包点击，界面一个都不弹（见 AutoMinerModule#onOpenScreen 的门控）。

    /** 自用模式｜打开后：不要求绑定矿物箱与挂机修复点，挖够就走去出售，其余设置与流程照旧 */
    public boolean personalMode = false;

    /** 触发组数｜背包里自用出售物品达到多少组就出发去卖（默认 20，取值域 1~36） */
    public int personalSellStacks = 20;

    /** 出售流程指令｜打开流程菜单的指令（默认 /cd；该菜单里既回主城也跨服传送） */
    public String personalSellCommand = "/cd";

    /** 回城点击关键词｜流程菜单里回主城大厅的槽位关键词（默认「返回主城」） */
    public String personalSellCityKeyword = "返回主城";

    /** 跨服点击关键词｜回程时打开子服传送界面的槽位关键词（默认「跨服传送」） */
    public String personalSellCrossServerKeyword = "跨服传送";

    /**
     * 回程目标服｜子服传送界面里点的那个服（默认「生存世界#1」）。
     *
     * <p>服务器只有生存与资源两个子服，控制台里用两个互斥勾选框呈现（勾一个另一个自动取消），
     * 存的就是勾中那条的服名关键词；到达后直接接现有 RTP 流程继续挖。</p>
     */
    public String personalSellReturnServer = "生存世界#1";

    /** 出售数量关键词｜出售界面里「设为全部数量」的槽位关键词（默认「全部」） */
    public String personalSellPickKeyword = "全部";

    /** 确认出售关键词｜提交出售的槽位关键词（默认「确认出售」） */
    public String personalSellConfirmKeyword = "确认出售";

    /** 收购 NPC 名字关键词｜在坐标附近按显示名匹配实体（默认「黑市商人」，剥掉颜色码与空格后比对） */
    public String personalSellNpcName = "黑市商人";

    /** 收购 NPC 坐标 X｜固定坐标，寻路主路径（默认 -24424） */
    public int personalSellNpcX = -24424;

    /** 收购 NPC 坐标 Y｜固定坐标（默认 66） */
    public int personalSellNpcY = 66;

    /** 收购 NPC 坐标 Z｜固定坐标（默认 -5355） */
    public int personalSellNpcZ = -5355;

    /**
     * 单步超时（秒）｜出售流程里每一步最长等多久（默认 10，取值域 1~60）。
     *
     * <p>服务器卡顿（TPS 掉、菜单不出、点击没反应）时就靠它把「这一步没成」判出来再重试，
     * 而不是傻等或往下瞎走。</p>
     */
    public int personalSellStepTimeout = 10;

    /** 单步重试次数｜单步超时后重试几次（默认 3，取值域 0~10）；整轮失败上限另由内部分级兜底 */
    public int personalSellRetries = 3;

    /** 回程目标服的两个勾选项（服务器只有这两个子服，界面按这两个名字出互斥勾选框） */
    public static final List<String> PERSONAL_RETURN_SERVERS = List.of("生存世界#1", "资源世界#1");

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
     * 秒破间隔（tick）｜发出破坏请求（STOP）后，开始下一块前的最小等待 tick（默认 0，取值域 0~20）。
     *
     * <p>流水线之后这个间隔不再包含「等服务端确认方块变化」的那一段（那一段已经异步化，
     * 见 {@code MiningFastBreakController} 类注释），所以它的实际含义变成「两块之间的最小间隔」：
     * 0 与 1 等价 —— 模块仍会强制留 1 刻保险（{@code PIPELINE_MIN_GAP_TICKS}），
     * 所以默认取 0（最快）；设大一点可降低被服务端/反作弊盯上的概率。</p>
     *
     * <p>默认值 2026-09-19 由旧项目的 2 改为 0（用户裁定）：间隔 2 等于每块白等 1 刻，
     * 按每块服务端最少 4 刻计就是约 17% 的损失。</p>
     */
    public int breakInterval = 0;

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

    /** 岩浆安全距离｜透视到岩浆进入这个距离就停止挖矿并撤离（默认 2，取值域 1~4；2026-09-18 曾提到 3，同日按实机截图收回 2） */
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

    /**
     * 怪物规避｜提高怪物附近路径代价，尽量绕开危险区域。
     *
     * <p><b>默认值裁定记录</b>：2026-09-18 裁定默认<b>关</b>——开着会连刷怪笼一起绕，
     * 与本项目自己的战斗 / 刷怪笼优先破坏逻辑打架。同日「默认全开」时曾短暂翻回开，
     * 用户看明利弊后定稿：这三项（怪物规避 / 仅挖暴露矿石 / 合法挖掘模式）<b>全关</b>。</p>
     */
    public boolean mobAvoidance = false;

    /** 掉落方块暂停｜遇到沙子、沙砾等掉落方块时暂停挖掘。关闭后不掉方块不暂停，挖矿更流畅（会塌方区域建议手动开启） */
    public boolean pauseMiningForFallingBlocks = false;

    /**
     * 寻路视角跟随｜视角跟着男中音的寻路方向走（默认开；用户 2026-09-18 追加，旧项目没有对应项）。
     *
     * <p><b>为什么需要它</b>：男中音默认 {@code freeLook = true}，走路时它只把朝向「静默」发给服务端
     * （{@code LookBehavior.Target.Mode#resolve} → {@code antiCheat ? SERVER : NONE}），本地视角一动不动
     * ——所以「寻路的那个视角」根本看不见。把 {@code freeLook} 关掉后走路也落到 CLIENT 分支，
     * 视角由男中音每刻写向下一个路径节点（挖矿时本来就一直是这样），模块再在每刻末尾用自己的
     * 平滑器（±180 环绕归一 + 指数趋近 + 静止贴合，见 {@code MiningPathing#writeSmoothedView}）
     * 把可见视角打磨丝滑；男中音写给服务端与射线检测的精确角度原样保留。</p>
     *
     * <p><b>关掉时</b>：完全恢复男中音原行为（走路视角不动、鼠标完全由玩家自己控制）。
     * 战斗期间视角临时让回战斗逻辑盯着怪（见 {@code MiningPathing#setCombatViewHold}）。</p>
     */
    public boolean pathViewFollow = true;

    /** 疾跑上坡｜上坡时提前一格疾跑+跳跃，提升速度 */
    public boolean sprintAscends = true;

    // 2026-09-18 定稿：跑酷 / 跑酷搭桥 / 对角线上升 默认开；对角线下降、掉落方块暂停、物流破坏方块、
    // 怪物规避、仅挖暴露矿石、合法挖掘模式（含对角检测）默认关 —— 全部以用户实机设置为准
    /** 允许跑酷｜允许跨越1-4格的跑酷跳跃（有一定风险） */
    public boolean allowParkour = true;

    /** 跑酷搭桥｜跑酷跳跃中途放置方块来延长距离（需开启放置方块） */
    public boolean allowParkourPlace = true;

    /** 对角线上升｜允许斜向上跳跃，速度更快但消耗更多饥饿值 */
    public boolean allowDiagonalAscend = true;

    /** 对角线下降｜允许斜向下降，速度更快但有一定风险（地狱慎用） */
    public boolean allowDiagonalDescend = false;

    // 下面三项 2026-09-18 定稿默认关（用户看明利弊后：「全关」）：仅挖暴露矿石会只挖看得见的矿、
    // 合法挖掘模式会加合法挖掘限制，两者都明显放慢节奏；与「怪物规避」同一批裁定
    /** 仅挖暴露矿石｜只挖掘能从指定距离看到的矿石，减少无效挖掘 */
    public boolean allowOnlyExposedOres = false;

    /** 失败目标暂时跳过｜矿点无法到达时跳过最近目标，避免反复卡住 */
    public boolean blacklistClosestOnFailure = true;

    /** 合法挖掘模式｜启用合法挖掘限制（关闭可提启效率但可能被检测） */
    public boolean legitMine = false;

    /** 合法挖掘检测对角矿石｜合法挖掘时检测与已发现矿石对角相邻的矿石（合法挖掘模式关着时不生效） */
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

    /**
     * 三类点位的渲染对象（显示 / 颜色 / 渲染模式），名称即落盘键的一部分，顺序即界面顺序。
     *
     * <p><b>为什么改成共用件 {@link EspRenderObject}</b>（用户 2026-09-19：「把所有标点选择点位位置的
     * 模块 参照星露谷农场的点位设置 全部更新」）：原先三类颜色各是一个 ARGB 整数，既没有显示开关也
     * 没有渲染模式，点位页只能摆三行色块。升级成与星露谷同款的渲染对象后，每类都能单独开关、
     * 单独调色（含彩虹）、单独选渲染模式（线框 / 面 / 两者），界面行与设置窗口也由共用件统一装配。</p>
     *
     * <p>对象名逐字对应旧设置项的语义（矿物箱 / 食物箱 / 挂机修复点），因此老存档的旧颜色键
     * 能在 {@link #load} 里按名称对应迁移，玩家调过的颜色不会丢。</p>
     */
    public final EspRenderObject renderMineralBox = new EspRenderObject("矿物箱",
        "高亮已绑定的矿物箱（潜影盒 / 箱子）", 0xFFD700, 255, ShapeMode.Lines);

    /** 食物箱：绿色（与矿物箱的金、挂机修复点的粉一眼区分） */
    public final EspRenderObject renderFoodBox = new EspRenderObject("食物箱",
        "高亮已绑定的食物箱", 0x64FF64, 255, ShapeMode.Lines);

    /** 挂机修复点：粉紫色 */
    public final EspRenderObject renderAfkPoint = new EspRenderObject("挂机修复点",
        "高亮已绑定的挂机修复点", 0xFF64FF, 255, ShapeMode.Lines);

    /** 全部点位渲染对象（顺序即界面顺序：矿物箱 → 食物箱 → 挂机修复点） */
    public List<EspRenderObject> renderObjects() {
        return List.of(renderMineralBox, renderFoodBox, renderAfkPoint);
    }

    /**
     * 旧版三类点位颜色键 → 渲染对象名。
     *
     * <p>只在「新键不存在」时用来读一次老存档（见 {@link #migrateLegacyColors(JsonObject)}）；
     * {@code save} 只写新键，故迁移只发生一次。</p>
     */
    private static final Map<String, String> LEGACY_COLOR_KEYS = Map.of(
        "矿物箱", "mineralColor",
        "食物箱", "foodColor",
        "挂机修复点", "afkColor");

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
     * 容器标签文字主色｜矿物箱 / 食物箱头顶文字的 ARGB；{@code 0} = 跟随各点位自己的方框色（默认）。
     *
     * <p><b>为什么用 0 表示「跟随」而不是直接给一个颜色</b>：两个容器标签的主色并不相同
     * （矿物箱取自己的方框色、食物箱取自己的方框色），单一颜色字段不可能同时等于两者，直接给值必然
     * 改掉其中一类的观感。取 0 时渲染侧取该点位 {@code EspRenderObject.color} 的当前 RGB
     * （与星露谷、自动农场、村民交易、自动附魔的字牌同一条口径，用户 2026-09-21），
     * 非 0 时才用它统一替换。</p>
     *
     * <p><b>本字段曾被记成「0 = 跟随界面主题色」</b>，那是 2026-09-19 字牌统一排版时期的旧口径
     * （当时字牌一律取 UI 主题强调色）；本版按用户新指令改为跟随各自方框色，挂机修复点不是容器、
     * 不受本项约束，始终跟随自己的方框色。</p>
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

        // 自用模式（用户 2026-09-20 追加；全部是新键，老存档缺项一律保留默认值，不需要迁移）
        json.addProperty("personalMode", personalMode);
        json.addProperty("personalSellStacks", personalSellStacks);
        json.addProperty("personalSellCommand", personalSellCommand);
        json.addProperty("personalSellCityKeyword", personalSellCityKeyword);
        json.addProperty("personalSellCrossServerKeyword", personalSellCrossServerKeyword);
        json.addProperty("personalSellReturnServer", personalSellReturnServer);
        json.addProperty("personalSellPickKeyword", personalSellPickKeyword);
        json.addProperty("personalSellConfirmKeyword", personalSellConfirmKeyword);
        json.addProperty("personalSellNpcName", personalSellNpcName);
        json.addProperty("personalSellNpcX", personalSellNpcX);
        json.addProperty("personalSellNpcY", personalSellNpcY);
        json.addProperty("personalSellNpcZ", personalSellNpcZ);
        json.addProperty("personalSellStepTimeout", personalSellStepTimeout);
        json.addProperty("personalSellRetries", personalSellRetries);

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
        // 点位渲染对象按对象名做键前缀（与星露谷同一口径）；旧颜色键不再写出，写新键后迁移自然失效
        for (EspRenderObject object : renderObjects()) {
            object.save(json, renderPrefix(object));
        }
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
        durabilityThreshold = clamp(intOf(json, "durabilityThreshold", durabilityThreshold),
            1, DURABILITY_THRESHOLD_HARD_MAX);
        shulkerPacker = boolOf(json, "shulkerPacker", shulkerPacker);
        autoDisconnect = boolOf(json, "autoDisconnect", autoDisconnect);
        autoDisconnectHealth = clamp(intOf(json, "autoDisconnectHealth", autoDisconnectHealth), 1, 20);

        loadList(json, "keepWhitelist", keepWhitelist);
        loadList(json, "foodWhitelist", foodWhitelist);
        loadList(json, "placeBlocks", placeBlocks);

        personalMode = boolOf(json, "personalMode", personalMode);
        personalSellStacks = clamp(intOf(json, "personalSellStacks", personalSellStacks), 1, 36);
        personalSellCommand = stringOf(json, "personalSellCommand", personalSellCommand);
        personalSellCityKeyword = stringOf(json, "personalSellCityKeyword", personalSellCityKeyword);
        personalSellCrossServerKeyword =
            stringOf(json, "personalSellCrossServerKeyword", personalSellCrossServerKeyword);
        personalSellReturnServer = stringOf(json, "personalSellReturnServer", personalSellReturnServer);
        personalSellPickKeyword = stringOf(json, "personalSellPickKeyword", personalSellPickKeyword);
        personalSellConfirmKeyword = stringOf(json, "personalSellConfirmKeyword", personalSellConfirmKeyword);
        personalSellNpcName = stringOf(json, "personalSellNpcName", personalSellNpcName);
        personalSellNpcX = intOf(json, "personalSellNpcX", personalSellNpcX);
        personalSellNpcY = intOf(json, "personalSellNpcY", personalSellNpcY);
        personalSellNpcZ = intOf(json, "personalSellNpcZ", personalSellNpcZ);
        personalSellStepTimeout = clamp(intOf(json, "personalSellStepTimeout", personalSellStepTimeout), 1, 60);
        personalSellRetries = clamp(intOf(json, "personalSellRetries", personalSellRetries), 0, 10);

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
        // 点位渲染对象按对象名做键前缀读回；再按旧颜色键迁移一次老存档（新键已存在时不覆盖）
        for (EspRenderObject object : renderObjects()) {
            object.load(json, renderPrefix(object));
        }
        migrateLegacyColors(json);
        // 容器标签字号倍率按界面取值域 clamp（0.5~4.0）；颜色不做取值域裁剪，非法/缺项保留默认（0 = 跟随）
        espContainerTextScale = clamp(doubleOf(json, "espContainerTextScale", espContainerTextScale), 0.5, 4.0);
        espContainerTextColor = intOf(json, "espContainerTextColor", espContainerTextColor);
        // 挖掘进度 ESP：开关默认开；颜色取 RGB，越界值（含带 alpha 的 ARGB）按 0xFFFFFF 裁剪
        breakProgressEsp = boolOf(json, "breakProgressEsp", breakProgressEsp);
        breakProgressBusyColor = intOf(json, "breakProgressBusyColor", breakProgressBusyColor) & 0xFFFFFF;
        breakProgressReadyColor = intOf(json, "breakProgressReadyColor", breakProgressReadyColor) & 0xFFFFFF;
    }

    // ━━━ 内部 ━━━

    /** 渲染对象的落盘键前缀（形如 {@code render.矿物箱.}，与星露谷点位同一口径） */
    private static String renderPrefix(EspRenderObject object) {
        return "render." + object.name() + ".";
    }

    /**
     * 老存档迁移：把旧颜色键（{@code mineralColor} / {@code foodColor} / {@code afkColor}，打包 ARGB）
     * 拆成 RGB 与透明度，写进对应的渲染对象。
     *
     * <p><b>为什么必须迁移</b>：三类颜色原先各存一个 ARGB 整数，现在归属到渲染对象自己的
     * {@link com.yiyiaddon.ui.render.world.EspColor}；{@code save} 只写新键，老存档里没有新键，
     * 不迁移就等于玩家调过的颜色在升级后全回默认。</p>
     *
     * <p><b>判据为什么取 {@code colorRgb}</b>：它是每个渲染对象必然写出的颜色键，一旦存在就说明
     * 这份存档已按新格式写过，旧键即便残留也不该再覆盖玩家后来改的颜色（迁移只发生一次）。</p>
     */
    private void migrateLegacyColors(JsonObject json) {
        for (EspRenderObject object : renderObjects()) {
            String legacyKey = LEGACY_COLOR_KEYS.get(object.name());
            if (legacyKey == null || !json.has(legacyKey) || !json.get(legacyKey).isJsonPrimitive()) continue;
            if (json.has(renderPrefix(object) + "colorRgb")) continue;
            int legacy;
            try {
                legacy = json.get(legacyKey).getAsInt();
            } catch (Exception ignored) {
                continue;
            }
            // 旧格式为 ARGB：高 8 位是透明度，低 24 位是 RGB（负整数也照位取，不能按符号判断）
            object.color.rgb(legacy & 0xFFFFFF).alpha((legacy >>> 24) & 0xFF);
        }
    }

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
