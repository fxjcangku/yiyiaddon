package com.yiyiaddon.integration.baritone;

import baritone.api.command.ICommand;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class BaritoneCommandTranslations {
    private static final Map<String, String> COMMAND_NAMES = Map.ofEntries(
        Map.entry("help", "帮助"),
        Map.entry("?", "帮助"),
        Map.entry("set", "设置"),
        Map.entry("setting", "设置"),
        Map.entry("settings", "设置"),
        Map.entry("modified", "已修改"),
        Map.entry("mod", "已修改"),
        Map.entry("baritone", "已修改"),
        Map.entry("modifiedsettings", "已修改"),
        Map.entry("reset", "重置"),
        Map.entry("goal", "目标"),
        Map.entry("goto", "前往"),
        Map.entry("path", "寻路"),
        Map.entry("proc", "进程"),
        Map.entry("eta", "预计时间"),
        Map.entry("version", "版本"),
        Map.entry("repack", "重新整理"),
        Map.entry("rescan", "重新整理"),
        Map.entry("build", "建造"),
        Map.entry("litematica", "Litematica建造"),
        Map.entry("schematica", "Schematica建造"),
        Map.entry("come", "过来"),
        Map.entry("axis", "坐标轴"),
        Map.entry("highway", "坐标轴"),
        Map.entry("forcecancel", "强制取消"),
        Map.entry("gc", "垃圾回收"),
        Map.entry("invert", "反向"),
        Map.entry("tunnel", "隧道"),
        Map.entry("render", "渲染修复"),
        Map.entry("farm", "耕作"),
        Map.entry("follow", "跟随"),
        Map.entry("pickup", "拾取"),
        Map.entry("explorefilter", "探索过滤"),
        Map.entry("reloadall", "重载缓存"),
        Map.entry("saveall", "保存缓存"),
        Map.entry("explore", "探索"),
        Map.entry("blacklist", "黑名单"),
        Map.entry("find", "查找"),
        Map.entry("mine", "挖掘"),
        Map.entry("click", "点击"),
        Map.entry("surface", "地表"),
        Map.entry("top", "地表"),
        Map.entry("thisway", "沿当前方向"),
        Map.entry("forward", "沿当前方向"),
        Map.entry("waypoints", "路径点"),
        Map.entry("waypoint", "路径点"),
        Map.entry("wp", "路径点"),
        Map.entry("sethome", "设置家"),
        Map.entry("home", "回家"),
        Map.entry("sel", "选区"),
        Map.entry("selection", "选区"),
        Map.entry("s", "选区"),
        Map.entry("elytra", "鞘翅"),
        Map.entry("pause", "暂停"),
        Map.entry("p", "暂停"),
        Map.entry("paws", "暂停"),
        Map.entry("resume", "恢复"),
        Map.entry("r", "恢复"),
        Map.entry("unpause", "恢复"),
        Map.entry("unpaws", "恢复"),
        Map.entry("paused", "暂停状态"),
        Map.entry("cancel", "取消"),
        Map.entry("c", "取消"),
        Map.entry("stop", "取消")
    );

    private static final Map<String, List<String>> LONG_DESCRIPTIONS = Map.ofEntries(
        entry("help", "使用此命令可查看 Baritone 命令的详细帮助。", "", "用法：", "> help - 列出所有命令及其简短说明。", "> help <command> - 显示指定命令的帮助信息。"),
        entry("set", "使用 set 命令管理 Baritone 的全部设置。Baritone 几乎所有行为均由这些设置控制。", "", "用法：", "> set - 等同于 `set list`", "> set list [page] - 查看全部设置", "> set modified [page] - 查看已修改的设置", "> set <setting> - 查看设置的当前值", "> set <setting> <value> - 修改设置值", "> set reset all - 将全部设置恢复默认值", "> set reset <setting> - 将指定设置恢复默认值", "> set toggle <setting> - 切换开 / 关设置", "> set save - 保存全部设置（通常会自动保存）", "> set load - 从 settings.txt 加载设置", "> set load [filename] - 从 minecraft/baritone 目录中的其他文件加载设置"),
        entry("modified", "此命令是以下命令的别名：", "> set modified"),
        entry("reset", "此命令是以下命令的别名：", "> set reset"),
        entry("goal", "goal 命令用于设置或清除 Baritone 的目标。", "", "需要坐标时，可以像原版 Minecraft 命令一样使用 ~，也可以直接使用普通数字。", "", "用法：", "> goal - 将当前位置设为目标", "> goal <reset/clear/none> - 清除目标", "> goal <y> - 将目标设为指定 Y 高度", "> goal <x> <z> - 将目标设为指定 X、Z 坐标", "> goal <x> <y> <z> - 将目标设为指定 X、Y、Z 坐标"),
        entry("goto", "goto 命令让 Baritone 前往指定目标或方块。", "", "需要坐标时，可以像原版 Minecraft 命令一样使用 ~，也可以直接使用普通数字。", "", "用法：", "> goto <block> - 前往世界中该类方块的位置", "> goto <y> - 前往指定 Y 高度", "> goto <x> <z> - 前往指定 X、Z 坐标", "> goto <x> <y> <z> - 前往指定 X、Y、Z 坐标"),
        entry("path", "path 命令让 Baritone 开始前往当前目标。", "", "用法：", "> path - 开始寻路"),
        entry("proc", "proc 命令显示当前控制 Baritone 的进程信息。", "", "如果你不了解 Baritone 的工作方式，通常不需要理解这些信息。", "", "用法：", "> proc - 查看当前进程信息（如果存在）"),
        entry("eta", "ETA 命令显示到下一路径分段以及最终目标的预计时间。", "", "请注意，到达最终目标的预计时间可能非常不准确。", "", "用法：", "> eta - 查看预计到达时间（如果存在）"),
        entry("version", "version 命令显示当前运行的 Baritone 版本。", "", "用法：", "> version - 查看版本信息"),
        entry("repack", "重新整理并缓存你周围的区块。", "", "用法：", "> repack - 重新缓存区块"),
        entry("build", "从文件加载并建造原理图。", "", "用法：", "> build <filename> - 加载并建造 `<filename>.schematic`", "> build <filename> <x> <y> <z> - 在指定位置建造"),
        entry("litematica", "建造当前在 Litematica 中打开的原理图。", "", "用法：", "> litematica", "> litematica <#>"),
        entry("schematica", "建造当前在 Schematica 中打开的原理图。", "", "用法：", "> schematica"),
        entry("come", "come 命令让 Baritone 前往摄像机所在位置。", "", "在自由视角不移动玩家位置的客户端中，此命令会很有用。", "", "用法：", "> come"),
        entry("axis", "axis 命令将目标设为最近的坐标轴，即 X=0 或 Z=0。", "", "用法：", "> axis"),
        entry("forcecancel", "与 cancel 类似，但会更强制地取消当前任务。", "", "用法：", "> forcecancel"),
        entry("gc", "调用 System.gc()。", "", "用法：", "> gc"),
        entry("invert", "invert 命令让 Baritone 远离当前目标，而不是靠近它。", "", "用法：", "> invert - 反转当前目标"),
        entry("tunnel", "tunnel 命令设置一个沿当前朝向直线挖掘的目标。", "", "用法：", "> tunnel - 不带参数，以 1×2 的尺寸挖掘", "> tunnel <height> <width> <depth> - 按自定义尺寸和深度挖掘隧道"),
        entry("render", "render 命令修复区块渲染异常，无需重新加载所有区块。", "", "用法：", "> render"),
        entry("farm", "farm 命令开始耕作附近的植物：收获成熟作物并重新种植。", "", "用法：", "> farm - 耕作能够找到的全部作物", "> farm <range> - 耕作起点指定范围内的作物", "> farm <range> <waypoint> - 耕作指定路径点附近的作物"),
        entry("follow", "follow 命令让 Baritone 跟随指定类型的实体。", "", "用法：", "> follow entities - 跟随所有实体", "> follow entity <entity1> <entity2> <...> - 跟随指定实体类型（如 skeleton、horse）", "> follow players - 跟随玩家", "> follow player <username1> <username2> <...> - 跟随指定玩家"),
        entry("pickup", "用法：", "> pickup - 拾取任意物品", "> pickup <item1> <item2> <...> - 拾取指定物品"),
        entry("explorefilter", "在使用 explore 前应用探索过滤器，用于指定哪些区块已探索或未探索。", "", "JSON 文件格式为：[{\"x\":0,\"z\":0},...]", "", "指定 invert 后，文件中列出的区块会被视为未探索，而非已探索。", "", "用法：", "> explorefilter <path> [invert] - 加载指定 JSON 文件；若使用 invert，参数必须是单词 `invert`"),
        entry("reloadall", "reloadall 命令重新加载 Baritone 的世界缓存。", "", "用法：", "> reloadall"),
        entry("saveall", "saveall 命令保存 Baritone 的世界缓存。", "", "用法：", "> saveall"),
        entry("explore", "让 Baritone 随机探索；此前通过 explorefilter 设置的过滤器会同时生效。", "", "用法：", "> explore - 从当前位置开始探索", "> explore <x> <z> - 从指定 X、Z 坐标开始探索"),
        entry("blacklist", "前往某类方块时，将最近的目标方块加入黑名单，使 Baritone 不再尝试接近它。", "", "用法：", "> blacklist"),
        entry("find", "find 命令搜索 Baritone 缓存并尝试找出指定方块的位置。", "Tab 补全只会建议缓存中的方块；未缓存的方块无法找到。", "", "用法：", "> find <block> [...] - 尝试查找列出的方块"),
        entry("mine", "mine 命令让 Baritone 搜索并挖掘指定方块。", "", "指定内容既可以是矿石，也可以是其他方块。", "", "另请参阅 legitMine 设置（使用 #set l legitMine）。", "", "用法：", "> mine diamond_ore - 挖掘能够找到的全部钻石矿石"),
        entry("click", "打开指定位置或方块。", "", "用法：", "> click"),
        entry("surface", "surface/top 命令让 Baritone 前往最近的类似地表区域。", "", "根据具体环境，该位置可能是地表，也可能是最高的可用空气空间。", "", "用法：", "> surface - 用于离开洞穴、矿井等地下区域", "> top - 用于离开洞穴、矿井等地下区域"),
        entry("thisway", "在当前视线方向前方指定距离处创建 GoalXZ 目标。", "", "用法：", "> thisway <distance> - 在前方指定方块距离处创建 GoalXZ"),
        entry("waypoints", "waypoint 命令用于管理 Baritone 路径点。", "", "路径点可标记位置以供以后使用，每个路径点具有标签和可选名称。", "", "info、delete 和 goal 可按标签指定路径点；同一标签存在多个路径点时，可进一步选择。", "", "save 缺少参数时，默认使用 USER 标签、空名称和当前位置。", "", "用法：", "> wp [l/list] - 列出全部路径点", "> wp <l/list> <tag> - 按标签列出路径点", "> wp <s/save> - 在当前位置保存无名称的 USER 路径点", "> wp <s/save> [tag] [name] [location] - 保存指定路径点", "> wp <i/info/show> <tag/name> - 显示路径点信息", "> wp <d/delete> <tag/name> - 删除路径点", "> wp <restore> <count> - 恢复最近删除的指定数量路径点", "> wp <c/clear> <tag> - 删除具有指定标签的全部路径点", "> wp <g/goal> <tag/name> - 将目标设为路径点", "> wp <goto> <tag/name> - 将目标设为路径点并开始寻路"),
        entry("sethome", "此命令是以下命令的别名：", "> waypoints save home"),
        entry("home", "此命令是以下命令的别名：", "> waypoints goto home"),
        entry("sel", "sel 命令用于操作 Baritone 选区，功能类似 WorldEdit。", "", "可使用选区清理、填充或处理区域。", "", "expand、contract 和 shift 的目标选择器支持 a/all、n/newest 与 o/oldest。", "", "用法：", "> sel pos1/p1/1 [x y z] - 将位置 1 设为当前位置或相对坐标", "> sel pos2/p2/2 [x y z] - 将位置 2 设为当前位置或相对坐标", "> sel clear/c - 清除选区", "> sel undo/u - 撤销上一次选区操作", "> sel set/fill/s/f [block] - 使用方块完全填充全部选区", "> sel walls/w [block] - 填充选区墙面", "> sel shell/shl [block] - 填充选区墙面、顶部和底部", "> sel sphere/sph [block] - 在选区边界内填充球体", "> sel hsphere/hsph [block] - 填充空心球体", "> sel cylinder/cyl [block] <axis> - 沿指定轴填充圆柱体（默认 y）", "> sel hcylinder/hcyl [block] <axis> - 填充空心圆柱体", "> sel cleararea/ca - 相当于 `set air`", "> sel replace/r <blocks...> <with> - 替换方块", "> sel copy/cp <x> <y> <z> - 相对指定位置或当前位置复制选区", "> sel paste/p <x> <y> <z> - 相对指定位置或当前位置建造复制区域", "> sel expand <target> <direction> <blocks> - 扩展目标选区", "> sel contract <target> <direction> <blocks> - 收缩目标选区", "> sel shift <target> <direction> <blocks> - 平移目标选区（不改变尺寸）"),
        entry("elytra", "elytra 命令让 Baritone 在下界中使用鞘翅自动飞向当前目标。", "", "用法：", "> elytra - 飞向当前目标", "> elytra reset - 重置进程状态，并尽量继续飞向同一目标", "> elytra repack - 将渲染距离内的全部区块排队交给原生库", "> elytra supported - 检查 Baritone 是否带有兼容当前电脑的原生库"),
        entry("pause", "pause 命令让 Baritone 暂时停止当前任务。", "", "它可以暂停寻路、建造或跟随等任务；执行一次 resume 即可继续。", "", "用法：", "> pause"),
        entry("resume", "resume 命令让 Baritone 继续执行上次 pause 时的任务。", "", "用法：", "> resume"),
        entry("paused", "paused 命令显示 Baritone 当前是否因 pause 命令而暂停。", "", "用法：", "> paused"),
        entry("cancel", "cancel 命令让 Baritone 停止当前任务。", "", "用法：", "> cancel")
    );

    private BaritoneCommandTranslations() {}

    public static String translateCommandName(String commandName) {
        if (!BaritoneTranslationToggle.enabled() || commandName == null) return commandName;
        return COMMAND_NAMES.getOrDefault(commandName.toLowerCase(Locale.ROOT), commandName);
    }

    public static String reverseTranslate(String chineseName) {
        if (!BaritoneTranslationToggle.enabled() || chineseName == null) return chineseName;
        String lower = chineseName.toLowerCase(Locale.ROOT);
        // 反向查找：中文 → 英文
        for (Map.Entry<String, String> entry : COMMAND_NAMES.entrySet()) {
            if (entry.getValue().equals(lower)) {
                return entry.getKey();
            }
        }
        return chineseName;
    }

    public static List<String> translateLongDescription(ICommand command, List<String> fallback) {
        if (!BaritoneTranslationToggle.enabled() || command == null) return fallback;
        for (String name : command.getNames()) {
            List<String> translation = LONG_DESCRIPTIONS.get(name.toLowerCase(Locale.ROOT));
            if (translation != null) return translation;
        }
        return fallback;
    }

    public static int longDescriptionCount() {
        return LONG_DESCRIPTIONS.size();
    }

    private static Map.Entry<String, List<String>> entry(String name, String... lines) {
        return Map.entry(name, List.of(lines));
    }
}
