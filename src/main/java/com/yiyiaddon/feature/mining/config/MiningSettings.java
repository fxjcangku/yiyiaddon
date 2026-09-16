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
 * 禁止改名、禁止改默认值（唯一例外见 {@link #fastBreak}）。</p>
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

    // ━━━ 触发条件（旧项目 :314-345） ━━━

    /** 满载组数｜背包矿物达到多少组时触发卸货（默认 20，取值域 1~36） */
    public int unloadThreshold = 20;

    /** 食物阈值｜背包食物少于此数量时触发补给（默认 32，取值域 1~64） */
    public int hungerThreshold = 32;

    /** 耐久阈值｜工具剩余耐久低于此值时前往挂机点修补（下界合金镐耐久 2031，上限已放宽）（默认 100，取值域 1~3000） */
    public int durabilityThreshold = 100;

    /** 潜影盒打包机｜卸货时把矿物箱(潜影盒)填满，检测到满后等红石推盒换新盒，自动重开箱继续放，直到背包目标矿放完才RTP。给搭配潜影盒打包机的挂机用户使用。 */
    public boolean shulkerPacker = false;

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
    public boolean fastBreak = false;

    /** 绕过反作弊｜兼容旧配置：STOP 后对相邻位置补发一次 ABORT；不能保证绕过服务器反作弊，异常时请关闭 */
    public boolean bypassAnticheat = false;

    /** 秒破间隔（tick）｜服务端确认方块变化后，开始下一块前的最小等待 tick；不会用于提前重复发送 STOP（默认 2，取值域 0~20） */
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

    /** 岩浆透视范围｜透视岩浆的扫描半径（格）（默认 8，取值域 2~16） */
    public int lavaEspRange = 8;

    /** 怪物规避｜提高怪物附近路径代价，尽量绕开危险区域 */
    public boolean mobAvoidance = true;

    /** 掉落方块暂停｜遇到沙子、沙砾等掉落方块时暂停挖掘。关闭后不掉方块不暂停，挖矿更流畅（会塌方区域建议手动开启） */
    public boolean pauseMiningForFallingBlocks = false;

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
        json.addProperty("mobAvoidance", mobAvoidance);
        json.addProperty("pauseMiningForFallingBlocks", pauseMiningForFallingBlocks);
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
        mobAvoidance = boolOf(json, "mobAvoidance", mobAvoidance);
        pauseMiningForFallingBlocks = boolOf(json, "pauseMiningForFallingBlocks", pauseMiningForFallingBlocks);
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
