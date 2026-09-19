package com.yiyiaddon.feature.tactical.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 服务端核心与反作弊的指纹库。
 *
 * <p>抽出来单独放，是因为这两张表需要频繁增补，混在检测逻辑里会越改越乱。</p>
 *
 * <p>关于匹配顺序：所有表都用 LinkedHashMap 保证插入序，检测时按序命中即返回。
 * 派生核心必须排在上游核心之前 —— Purpur 基于 Paper、Leaves 基于 Purpur，
 * 它们的 brand 里往往同时含有上游名字，先匹配 Paper 就永远认不出 Purpur。</p>
 *
 * <p>逐字移植旧项目 {@code tactical/core/ServerFingerprints.java}（256 行），
 * 四张表条目、插入顺序、{@link #isHighRisk(String)} 命中列表全部保持原样；
 * 唯一差异是包名。</p>
 */
public final class ServerFingerprints {

    private ServerFingerprints() {
    }

    /**
     * 核心 brand / version 关键词 → 展示名。
     *
     * <p>顺序：混合端 → 高度派生 → 派生 → 基础 → 代理 → 跨平台</p>
     */
    public static final Map<String, String> CORES = new LinkedHashMap<>();

    static {
        // 混合端（同时跑 Forge/Fabric 模组与 Bukkit 插件），特征最独特，优先判定
        CORES.put("mohist", "Mohist（混合端）");
        CORES.put("arclight", "Arclight（混合端）");
        CORES.put("catserver", "CatServer（混合端）");
        CORES.put("magma", "Magma（混合端）");
        CORES.put("banner", "Banner（混合端）");
        CORES.put("youer", "Youer（混合端）");
        CORES.put("silkard", "Silkard（Fabric 混合端）");
        CORES.put("ketting", "Ketting（Forge 混合端）");
        CORES.put("crucible", "Crucible（Forge 混合端）");
        CORES.put("thermos", "Thermos（Forge 混合端）");
        CORES.put("kettle", "Kettle（Forge 混合端）");

        // Paper 系深度派生：这几个的 brand 常带上游名，必须排在 Paper 前面
        CORES.put("leaves", "Leaves（生电优化）");
        CORES.put("lightingluminol", "LightingLuminol（国内 Folia 分支）");
        CORES.put("luminol", "Luminol（国内 Folia 分支）");
        CORES.put("shreddedpaper", "ShreddedPaper（多线程）");
        CORES.put("multipaper", "MultiPaper（多服同步）");
        CORES.put("canvas", "Canvas（性能向）");
        CORES.put("purpur", "Purpur（高度可配）");
        CORES.put("folia", "Folia（区域化多线程）");
        CORES.put("pufferfish", "Pufferfish（性能向）");
        CORES.put("sparklypaper", "SparklyPaper（性能向）");
        CORES.put("divinemc", "DivineMC（性能向）");
        CORES.put("plazma", "Plazma（性能向）");
        CORES.put("gale", "Gale（性能向）");
        CORES.put("airplane", "Airplane（已停更）");
        CORES.put("petal", "Petal（性能向）");
        CORES.put("slice", "Slice（性能向）");
        CORES.put("kaiiju", "Kaiiju（性能向）");
        CORES.put("leaf", "Leaf（性能向）");

        // Paper 与 Spigot 系基础核心
        CORES.put("paper", "Paper");
        CORES.put("spigot", "Spigot");
        CORES.put("craftbukkit", "CraftBukkit");
        CORES.put("bukkit", "Bukkit 系");
        CORES.put("glowstone", "Glowstone（独立实现）");
        CORES.put("sponge", "Sponge");

        // 模组服务端：没有 Bukkit 指令树时主要依靠 brand/version 识别
        CORES.put("neoforge", "NeoForge");
        CORES.put("quilt", "Quilt");
        CORES.put("fabric", "Fabric");
        CORES.put("forge", "Forge");

        // 代理层：玩家实际连的是代理，后端核心要等进子服才能判定
        CORES.put("velocity", "Velocity（代理）");
        CORES.put("waterfall", "Waterfall（代理）");
        CORES.put("bungeecord", "BungeeCord（代理）");
        CORES.put("travertine", "Travertine（代理）");
        CORES.put("flamecord", "FlameCord（代理）");
        CORES.put("gate", "Gate（代理）");

        // 跨平台与非 Java 实现
        CORES.put("geyser", "Geyser（基岩互通）");
        CORES.put("nukkit", "Nukkit（基岩）");
        CORES.put("powernukkit", "PowerNukkit（基岩）");
        CORES.put("cloudburst", "Cloudburst（基岩）");
        CORES.put("pocketmine", "PocketMine（基岩）");
        CORES.put("cuberite", "Cuberite（C++ 实现）");
        CORES.put("minestom", "Minestom（自研实现）");
        CORES.put("limbo", "Limbo（登录前置）");
        CORES.put("nanolimbo", "NanoLimbo（登录前置）");
    }

    /**
     * 指令树命名空间 / 指令名 → 核心展示名。
     *
     * <p>brand 可以被服务端随手改掉，但指令树是插件注册的实际结果，伪造成本高得多。
     * Bukkit 系会把插件指令注册成 {@code 插件名:指令} 的形式，命名空间因此可用。</p>
     */
    public static final Map<String, String> CORE_COMMANDS = new LinkedHashMap<>();

    static {
        CORE_COMMANDS.put("purpur", "Purpur（高度可配）");
        CORE_COMMANDS.put("leaves", "Leaves（生电优化）");
        CORE_COMMANDS.put("lightingluminol", "LightingLuminol（国内 Folia 分支）");
        CORE_COMMANDS.put("luminol", "Luminol（国内 Folia 分支）");
        CORE_COMMANDS.put("shreddedpaper", "ShreddedPaper（多线程）");
        CORE_COMMANDS.put("multipaper", "MultiPaper（多服同步）");
        CORE_COMMANDS.put("canvas", "Canvas（性能向）");
        CORE_COMMANDS.put("divinemc", "DivineMC（性能向）");
        CORE_COMMANDS.put("folia", "Folia（区域化多线程）");
        CORE_COMMANDS.put("paper", "Paper");
        CORE_COMMANDS.put("spigot", "Spigot");
        CORE_COMMANDS.put("bukkit", "Bukkit 系");
        CORE_COMMANDS.put("minecraft", "");   // 原版命名空间，出现不代表任何核心
    }

    /**
     * 反作弊指纹：指令树命名空间 / 指令名 → 展示名。
     *
     * <p>这是唯一相对可靠的反作弊识别途径。绝大多数反作弊都会注册自己的管理指令，
     * 且为了避免与其它插件冲突，命名空间基本等于插件名。</p>
     *
     * <p>收录范围覆盖国际主流、国内常见与已停更但仍在小服使用的实现。</p>
     */
    public static final Map<String, String> ANTICHEAT_COMMANDS = new LinkedHashMap<>();

    static {
        // 国际主流（当前活跃）
        ANTICHEAT_COMMANDS.put("grim", "GrimAC");
        ANTICHEAT_COMMANDS.put("grimac", "GrimAC");
        ANTICHEAT_COMMANDS.put("vulcan", "Vulcan");
        ANTICHEAT_COMMANDS.put("intave", "Intave");
        ANTICHEAT_COMMANDS.put("polar", "Polar");
        ANTICHEAT_COMMANDS.put("matrix", "Matrix");
        ANTICHEAT_COMMANDS.put("spartan", "Spartan");
        ANTICHEAT_COMMANDS.put("themis", "Themis");
        ANTICHEAT_COMMANDS.put("verus", "Verus");
        ANTICHEAT_COMMANDS.put("karhu", "Karhu");
        ANTICHEAT_COMMANDS.put("kauri", "Kauri");
        ANTICHEAT_COMMANDS.put("negativity", "Negativity");
        ANTICHEAT_COMMANDS.put("antihaxerman", "AntiHaxerman");
        ANTICHEAT_COMMANDS.put("ahm", "AntiHaxerman");
        ANTICHEAT_COMMANDS.put("witherac", "WitherAC");
        ANTICHEAT_COMMANDS.put("abc", "AntiBotCheck");
        ANTICHEAT_COMMANDS.put("guardian", "Guardian");
        ANTICHEAT_COMMANDS.put("horizon", "Horizon");
        ANTICHEAT_COMMANDS.put("lightanticheat", "LightAntiCheat");
        ANTICHEAT_COMMANDS.put("lac", "LightAntiCheat");
        ANTICHEAT_COMMANDS.put("soaroma", "SoaromaSAC");
        ANTICHEAT_COMMANDS.put("soaromasac", "SoaromaSAC");
        ANTICHEAT_COMMANDS.put("watchturtle", "WatchTurtle");
        ANTICHEAT_COMMANDS.put("turtleac", "WatchTurtle");
        ANTICHEAT_COMMANDS.put("alice", "Alice");
        ANTICHEAT_COMMANDS.put("reflex", "Reflex");
        ANTICHEAT_COMMANDS.put("janitor", "Janitor");
        ANTICHEAT_COMMANDS.put("godseye", "GodsEye");
        ANTICHEAT_COMMANDS.put("dcheck", "DCheck");
        ANTICHEAT_COMMANDS.put("wolfac", "WolfAC");
        ANTICHEAT_COMMANDS.put("firefly", "Firefly");
        ANTICHEAT_COMMANDS.put("agc", "AGC");
        ANTICHEAT_COMMANDS.put("antiaura", "AntiAura");
        ANTICHEAT_COMMANDS.put("kauripp", "Kauri");
        ANTICHEAT_COMMANDS.put("frequency", "Frequency");
        ANTICHEAT_COMMANDS.put("anticheatelite", "AntiCheatElite");
        ANTICHEAT_COMMANDS.put("updatedncp", "Updated-NoCheatPlus");
        ANTICHEAT_COMMANDS.put("wardenac", "WardenAC");
        ANTICHEAT_COMMANDS.put("watchdog", "Watchdog");

        // 已停更但小服仍有部署
        ANTICHEAT_COMMANDS.put("aac", "AAC（已停更）");
        ANTICHEAT_COMMANDS.put("advancedantoicheat", "AAC（已停更）");
        ANTICHEAT_COMMANDS.put("nocheatplus", "NoCheatPlus（已停更）");
        ANTICHEAT_COMMANDS.put("ncp", "NoCheatPlus（已停更）");
        ANTICHEAT_COMMANDS.put("warden", "Warden（已停更）");
        ANTICHEAT_COMMANDS.put("wraith", "Wraith（已停更）");
        ANTICHEAT_COMMANDS.put("hawk", "Hawk（已停更）");
        ANTICHEAT_COMMANDS.put("anticheatreloaded", "AntiCheatReloaded（已停更）");
        ANTICHEAT_COMMANDS.put("acr", "AntiCheatReloaded（已停更）");
        ANTICHEAT_COMMANDS.put("ness", "NESS（已停更）");
        ANTICHEAT_COMMANDS.put("nochat", "NoCheat（已停更）");

        // 国内常见
        ANTICHEAT_COMMANDS.put("funnyac", "FunnyAC（国内）");
        ANTICHEAT_COMMANDS.put("kkac", "KKAC（国内）");
        ANTICHEAT_COMMANDS.put("lemonac", "LemonAC（国内）");
        ANTICHEAT_COMMANDS.put("catac", "CatAC（国内）");
        ANTICHEAT_COMMANDS.put("mochaac", "MochaAC（国内）");
        ANTICHEAT_COMMANDS.put("rainac", "RainAC（国内）");
        ANTICHEAT_COMMANDS.put("flareac", "FlareAC（国内）");
        ANTICHEAT_COMMANDS.put("sakuraac", "SakuraAC（国内）");
        ANTICHEAT_COMMANDS.put("goodac", "GoodAC（国内）");
        ANTICHEAT_COMMANDS.put("nekoac", "NekoAC（国内）");
        ANTICHEAT_COMMANDS.put("lolac", "LolAC（国内）");
        ANTICHEAT_COMMANDS.put("iceac", "IceAC（国内）");
        ANTICHEAT_COMMANDS.put("mangoac", "MangoAC（国内）");
        ANTICHEAT_COMMANDS.put("arteryac", "ArteryAC（国内）");
        ANTICHEAT_COMMANDS.put("fairyac", "FairyAC（国内）");
        ANTICHEAT_COMMANDS.put("xkac", "XKAC（国内）");

        // 辅助类：不是反作弊本体，但常与反作弊同时出现，值得报出来
        ANTICHEAT_COMMANDS.put("illegalstack", "IllegalStack（物品校验）");
        ANTICHEAT_COMMANDS.put("exploitfixer", "ExploitFixer（漏洞修补）");
        ANTICHEAT_COMMANDS.put("packetlimiter", "PacketLimiter（发包限流）");
        ANTICHEAT_COMMANDS.put("anticheataddition", "AntiCheatAddition");
        ANTICHEAT_COMMANDS.put("nochatreports", "NoChatReports（聊天签名）");
    }

    /**
     * 反作弊在插件消息频道上的指纹。
     *
     * <p>少数反作弊会开自己的 CustomPayload 频道做客户端校验，
     * 收到这类频道基本可以确诊，比指令树更硬。</p>
     */
    public static final Map<String, String> ANTICHEAT_CHANNELS = new LinkedHashMap<>();

    static {
        ANTICHEAT_CHANNELS.put("grim", "GrimAC");
        ANTICHEAT_CHANNELS.put("vulcan", "Vulcan");
        ANTICHEAT_CHANNELS.put("matrix", "Matrix");
        ANTICHEAT_CHANNELS.put("intave", "Intave");
        ANTICHEAT_CHANNELS.put("themis", "Themis");
        ANTICHEAT_CHANNELS.put("spartan", "Spartan");
        ANTICHEAT_CHANNELS.put("negativity", "Negativity");
        ANTICHEAT_CHANNELS.put("sentinel", "Sentinel");
        ANTICHEAT_CHANNELS.put("frequency", "Frequency");
        ANTICHEAT_CHANNELS.put("lightanticheat", "LightAntiCheat");
        ANTICHEAT_CHANNELS.put("witherac", "WitherAC");
        ANTICHEAT_CHANNELS.put("watchdog", "Watchdog");
        ANTICHEAT_CHANNELS.put("anticheat", "未知反作弊");
    }

    /** 视为高风险、需要策略收紧（飞行发包模式拒入 + 发包限速打折）的反作弊。 */
    public static boolean isHighRisk(String displayName) {
        if (displayName == null) return false;
        String lower = displayName.toLowerCase();
        return lower.contains("grim")
            || lower.contains("matrix")
            || lower.contains("intave")
            || lower.contains("vulcan")
            || lower.contains("polar")
            || lower.contains("themis")
            || lower.contains("verus")
            || lower.contains("soaroma")
            || lower.contains("watchturtle")
            || lower.contains("alice")
            || lower.contains("reflex")
            || lower.contains("janitor")
            || lower.contains("godseye")
            || lower.contains("agc")
            || lower.contains("spartan")
            || lower.contains("karhu")
            || lower.contains("kauri")
            || lower.contains("negativity")
            || lower.contains("lightanticheat")
            || lower.contains("witherac")
            || lower.contains("horizon")
            || lower.contains("warden")
            || lower.contains("hawk")
            || lower.contains("antiaura")
            || lower.contains("antihaxerman")
            || lower.contains("nocheat")
            || lower.contains("aac")
            || lower.contains("（国内）")
            || lower.contains("watchdog")
            || lower.contains("frequency")
            || lower.contains("packetlimiter")
            || lower.contains("拉回频繁")
            || lower.contains("已确认存在移动校验");
    }
}
