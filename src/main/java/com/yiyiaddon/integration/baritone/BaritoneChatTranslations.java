package com.yiyiaddon.integration.baritone;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.stream.Collectors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.block.Block;

public final class BaritoneChatTranslations {
    private static final Map<String, String> EXACT = Map.ofEntries(
        Map.entry("Paused", "已暂停"),
        Map.entry("Resumed", "已恢复"),
        Map.entry("Baritone is paused", "Baritone 已暂停"),
        Map.entry("Baritone is not paused", "Baritone 未暂停"),
        Map.entry("ok canceled", "已取消"),
        Map.entry("ok force canceled", "已强制取消"),
        Map.entry("Cleared goal", "已清除目标"),
        Map.entry("There was no goal to clear", "没有可清除的目标"),
        Map.entry("No goal set", "尚未设置目标"),
        Map.entry("No goal has been set", "尚未设置目标"),
        Map.entry("No process in control", "当前没有控制中的进程"),
        Map.entry("Not currently pathing", "当前未在寻路"),
        Map.entry("Failed", "失败"),
        Map.entry("No waypoints found", "未找到路径点"),
        Map.entry("No waypoints found by that tag", "未找到具有该标签的路径点"),
        Map.entry("Multiple waypoints were found", "找到了多个路径点"),
        Map.entry("Multiple waypoints were found:", "找到了多个路径点："),
        Map.entry("All waypoints:", "全部路径点："),
        Map.entry("Waypoint added: ", "已添加路径点："),
        Map.entry("Click to show a command to recreate this waypoint", "点击显示重新创建此路径点的命令"),
        Map.entry("That waypoint has successfully been deleted, click to restore it", "路径点已删除，点击恢复"),
        Map.entry("Timestamp was specified but no waypoint was found", "指定了时间戳，但未找到对应路径点"),
        Map.entry("<empty>", "未命名"),
        Map.entry("Set pos1 first before using pos2", "使用 pos2 前请先设置 pos1"),
        Map.entry("Invalid type", "类型无效"),
        Map.entry("Invalid value", "值无效"),
        Map.entry("Invalid position", "位置无效"),
        Map.entry("Invalid block", "方块无效"),
        Map.entry("Invalid item", "物品无效"),
        Map.entry("Invalid entity", "实体无效"),
        Map.entry("Unknown error", "未知错误"),
        Map.entry("Death position saved.", "死亡位置已保存"),
        Map.entry("Now pathing", "开始寻路"),
        Map.entry("Coming", "正在前往"),
        Map.entry("Already at surface", "已经位于地表"),
        Map.entry("No higher location found", "未找到更高的位置"),
        Map.entry("No positions known, are you sure the blocks are cached?", "没有已知位置，请确认目标方块已被缓存"),
        Map.entry("Blacklisted closest instances", "已将最近的目标加入黑名单"),
        Map.entry("Farming", "开始耕作"),
        Map.entry("Picking up all items", "正在拾取所有物品"),
        Map.entry("Picking up these items:", "正在拾取以下物品："),
        Map.entry("Settings saved", "设置已保存"),
        Map.entry("Please specify 'all' as an argument to reset to confirm you'd really like to do this", "如需确认重置全部设置，请指定参数 'all'"),
        Map.entry("ALL settings will be reset. Use the 'set modified' or 'modified' commands to see what will be reset.", "全部设置都将被重置。使用 'set modified' 或 'modified' 命令查看将被重置的项目。"),
        Map.entry("Specify a setting name instead of 'all' to only reset one setting", "指定设置名称而不是 'all' 可仅重置单项设置"),
        Map.entry("Click to set the setting back to this value", "点击将设置恢复为此值"),
        Map.entry("Warning: Chat commands will no longer work. If you want to revert this change, use prefix control (if enabled) or click the old value listed above.", "警告：聊天命令将不再可用。如需撤销，请使用带前缀命令（若已启用）或点击上方旧值。"),
        Map.entry("Warning: Prefixed commands will no longer work. If you want to revert this change, use chat control (if enabled) or click the old value listed above.", "警告：带前缀命令将不再可用。如需撤销，请使用聊天命令（若已启用）或点击上方旧值。"),
        Map.entry("All settings have been reset to their default values", "所有设置已恢复默认值"),
        Map.entry("Reloaded", "已重新加载"),
        Map.entry("Saved", "已保存"),
        Map.entry("Done", "已完成"),
        Map.entry("Position 1 has been set", "位置 1 已设置"),
        Map.entry("Selection added", "选区已添加"),
        Map.entry("Selection copied", "选区已复制"),
        Map.entry("Filling now", "开始填充"),
        Map.entry("Building now", "开始建造"),
        Map.entry("Pathing complete", "寻路完成"),
        Map.entry("Done building", "建造完成"),
        Map.entry("Exploration failed", "探索失败"),
        Map.entry("Explored all chunks", "已探索全部区块"),
        Map.entry("Farm failed", "耕作失败"),
        Map.entry("No path found =(", "未找到路径 =("),
        Map.entry("Reset state but still flying to same goal", "已重置状态，仍将飞向同一目标"),
        Map.entry("Queued all loaded chunks for repacking", "已将所有已加载区块加入重新整理队列"),
        Map.entry("Only works in the nether", "仅可在下界使用"),
        Map.entry("Invalid action", "操作无效"),
        Map.entry("yes", "是"),
        Map.entry("Nether seed changed, recalculating path", "下界种子已更改，正在重新计算路径"),
        Map.entry("elytraPredictTerrain setting changed, recalculating path", "elytraPredictTerrain 设置已更改，正在重新计算路径"),
        Map.entry("Emergency landing - almost out of elytra durability or fireworks", "紧急降落——鞘翅耐久或烟花即将耗尽"),
        Map.entry("almost out of elytra durability or fireworks, but I'm going to continue since elytraAllowEmergencyLand is false", "鞘翅耐久或烟花即将耗尽，但 elytraAllowEmergencyLand 已关闭，将继续飞行"),
        Map.entry("Path complete, picking a nearby safe landing spot...", "航线完成，正在选择附近的安全降落点……"),
        Map.entry("Above the landing spot, landing...", "已到达降落点上方，正在降落……"),
        Map.entry("bad landing spot, trying again...", "降落点不安全，正在重试……"),
        Map.entry("Landed, but still moving, waiting for velocity to die down... ", "已着陆但仍在移动，正在等待速度降低……"),
        Map.entry("Done :)", "完成 :)"),
        Map.entry("Not taking off, because elytra durability or fireworks are so low that I would immediately emergency land anyway.", "未起飞：鞘翅耐久或烟花过低，起飞后会立即紧急降落。"),
        Map.entry("Failed to compute path to destination", "无法计算到目的地的航线"),
        Map.entry("Failed to recompute segment", "无法重新计算航线分段"),
        Map.entry("Failed to compute next segment", "无法计算下一航线分段"),
        Map.entry("no fireworks", "没有烟花"),
        Map.entry("Click to set goal to this position", "点击将目标设为此位置"),
        Map.entry("Click to rerun command", "点击重新执行命令"),
        Map.entry("Click to select", "点击选择"),
        Map.entry("Click to delete this waypoint", "点击删除此路径点"),
        Map.entry("Click to set goal to this waypoint", "点击将目标设为此路径点"),
        Map.entry("Click to return to the waypoints list", "点击返回路径点列表"),
        Map.entry("Old value: ", "旧值："),
        Map.entry("Warning: PathingBehaivor illegal state! Discarding invalid path!", "警告：寻路行为状态异常！正在丢弃无效路径！"),
        Map.entry("Unable to climb vines. Consider disabling allowVines.", "无法攀爬藤蔓，可考虑关闭 allowVines 设置。"),
        Map.entry("It looks like you're on 2b2t, but elytraNetherSeed is incorrect.", "你似乎位于 2b2t，但 elytraNetherSeed 设置不正确。"),
        Map.entry("Unable to mine when allowBreak is false and target block is not in allowBreakAnyway!", "allowBreak 已关闭且目标方块不在 allowBreakAnyway 列表中，无法挖掘！"),
        Map.entry("Following these entities:", "正在跟随以下实体："),
        Map.entry("Following these types of entities:", "正在跟随以下类型的实体："),
        Map.entry("Right click timed out", "右键点击超时"),
        Map.entry("Arrived but failed to right click open", "已到达，但右键打开失败"),
        Map.entry("ok called System.gc()", "已调用 System.gc()"),
        Map.entry("Poor little kitten forgot to set a selection while BuildOnlySelection is true", "开启 BuildOnlySelection 时忘记设置选区"),
        Map.entry("No schematic currently open", "当前没有打开的原理图"),
        Map.entry("Schematica is not present", "未安装 Schematica"),
        Map.entry("Litematica is not present", "未安装 Litematica"),
        Map.entry("Unable to do it. Pausing. resume to resume, cancel to cancel", "无法继续。已暂停。执行 resume 恢复，cancel 取消"),
        Map.entry("Missing materials for at least:", "至少缺少以下材料："),
        Map.entry("Unreplaceable liquids at at least:", "至少以下位置存在无法替换的液体："),
        Map.entry("Backfill cannot be used with allowParkour true", "开启 allowParkour 时无法使用回填"),
        Map.entry("Already paused", "已处于暂停状态"),
        Map.entry("Not paused", "当前未暂停"),
        Map.entry("No goal", "尚未设置目标"),
        Map.entry("File not found", "找不到文件"),
        Map.entry("Invalid JSON syntax", "JSON 语法无效"),
        Map.entry("Nothing to undo!", "没有可撤销的操作！"),
        Map.entry("No selections", "没有选区"),
        Map.entry("No selections found", "未找到选区"),
        Map.entry("You need to copy a selection first", "请先复制选区"),
        Map.entry("Invalid transform type", "变换类型无效"),
        Map.entry("GetToBlockProcess is not currently active", "GetToBlockProcess 当前未激活"),
        Map.entry("No known locations, unable to blacklist", "没有已知位置，无法加入黑名单"),
        Map.entry("Null version (this is normal in a dev environment)", "版本为空（开发环境中属正常现象）"),
        Map.entry("Undid pos1", "已撤销位置 1"),
        Map.entry("Undid pos2", "已撤销位置 2"),
        Map.entry("Width and depth must at least be 1 block; Height must at least be 2 blocks, and cannot be greater than the build limit.", "宽和深至少为 1 格，高至少为 2 格且不能超过建造高度上限。"),
        Map.entry("Couldn't load the schematic. Either your schematic is corrupt or this is a bug.", "无法加载原理图。原理图可能损坏，或这是一个程序错误。"),
        Map.entry("Baritone settings file not found, resetting.", "未找到 Baritone 设置文件，正在重置。"),
        Map.entry("Exception while reading Baritone settings, some settings may be reset to default values!", "读取 Baritone 设置时发生异常，部分设置可能已恢复默认值！"),
        Map.entry("Exception thrown while saving Baritone settings!", "保存 Baritone 设置时抛出异常！"),
        Map.entry("An unhandled exception occurred. The error is in your game's log, please report this at https://github.com/cabaletta/baritone/issues", "发生未处理的异常。错误详情在游戏日志中，请前往 https://github.com/cabaletta/baritone/issues 报告此问题。"),
        Map.entry("aight dude", "好的，兄弟"),
        Map.entry("Within a few hundred blocks of spawn/axis/highways/etc, the terrain is too fragmented to be predictable. Baritone Elytra will still work, just with backtracking. ", "在出生点/坐标轴/高速通道等附近数百格内，地形过于破碎无法预测。Baritone 鞘翅仍可工作，只是会走回头路。 "),
        Map.entry("However, once you get more than a few thousand blocks out, you should try ", "不过，当你离出生点数千格以外时，建议尝试 "),
        Map.entry("the older seed (click here)", "旧种子（点击此处）"),
        Map.entry(". Once you're further out into newer terrain generation (this includes everything up through 1.12), you should try ", "。当你深入到较新版本生成的地形（包括 1.12 及更早版本）时，建议尝试 "),
        Map.entry("the newer seed (click here)", "新种子（点击此处）"),
        Map.entry(". Once you get into 1.19 terrain, the terrain becomes unpredictable again, due to custom non-vanilla generation, and you should set #elytraPredictTerrain to false. ", "。进入 1.19 地形后，由于非原版的自定义生成，地形再次变得不可预测，建议执行 #set elytraPredictTerrain false。 "),
        Map.entry("To disable this message, enable the setting elytraTermsAccepted\n", "要关闭此提示，请开启 elytraTermsAccepted 设置\n"),
        Map.entry("Baritone Elytra is an experimental feature. It is only intended for long distance travel in the Nether using fireworks for vanilla boost. It will not work with any other mods (\"hacks\") for non-vanilla boost. ", "Baritone 鞘翅是实验性功能，仅用于在下界通过烟花原版加速进行长距离飞行，不兼容任何非原版加速的外挂模组。 "),
        Map.entry("If you want Baritone to attempt to take off from the ground for you, you can enable the elytraAutoJump setting (not advisable on laggy servers!). ", "如希望 Baritone 自动从地面起飞，可开启 elytraAutoJump 设置（不建议在卡顿严重的服务器使用！）。 "),
        Map.entry("If you want Baritone to go slower, enable the elytraConserveFireworks setting and/or decrease the elytraFireworkSpeed setting. ", "如希望 Baritone 飞得更慢，可开启 elytraConserveFireworks 设置或调低 elytraFireworkSpeed 设置。 "),
        Map.entry("Baritone Elytra ", "Baritone 鞘翅 "),
        Map.entry("wants to know the seed", "需要知道世界种子"),
        Map.entry(" of the world you are in. If it doesn't have the correct seed, it will frequently backtrack. It uses the seed to generate terrain far beyond what you can see, since terrain obstacles in the Nether can be much larger than your render distance. ", "。如果种子不正确，它会频繁走回头路。它用种子生成视野之外的地形，因为下界的地形障碍可能远大于渲染距离。 "),
        Map.entry("It looks like you're on 2b2t. ", "你似乎位于 2b2t。 "),
        Map.entry("elytraPredictTerrain is currently disabled. ", "elytraPredictTerrain 当前已关闭。 "),
        Map.entry("You are using the newer seed. ", "你正在使用新种子。 "),
        Map.entry("You are using the older seed. ", "你正在使用旧种子。 "),
        Map.entry("Defaulting to the newer seed. ", "默认使用新种子。 "),
        Map.entry("For the time being, elytraPredictTerrain is defaulting to false since the seed is unknown.", "由于种子未知，elytraPredictTerrain 暂时默认为关闭。"),
        Map.entry("All Baritone commands (clickable):", "全部 Baritone 命令（可点击）："),
        Map.entry("Click to view previous page", "点击查看上一页"),
        Map.entry("Click to view next page", "点击查看下一页"),
        Map.entry("Click to return to the help menu", "点击返回帮助菜单"),
        Map.entry("no block found by that id", "未找到该 ID 对应的方块"),
        Map.entry("no entity found by that id", "未找到该 ID 对应的实体"),
        Map.entry("No item found by that id", "未找到该 ID 对应的物品"),
        Map.entry("(the 0.6 number is just an example, tweak to your liking)", "（0.6 仅为示例数值，可自行调整）"),
        Map.entry("Click to view full help", "点击查看完整帮助")
    );

    private static final List<Template> TEMPLATES = List.of(
        // 目标展示（Goal 系列），必须先于通用「Goal: (.+)」
        template("Goal: GoalAxis", match -> "目标：坐标轴"),
        template("Goal: Goal([A-Za-z]+)\\{([^}]*)\\}", match -> "目标：" + translateGoalBody(match.group(1), match.group(2))),
        template("Going to: Goal([A-Za-z]+)\\{([^}]*)\\}", match -> "正在前往：" + translateGoalBody(match.group(1), match.group(2))),
        template("Exploring from Goal([A-Za-z]+)\\{([^}]*)\\}", match -> "从 " + translateGoalBody(match.group(1), match.group(2)) + " 开始探索"),
        // 挖掘指令里「Mining 目标方块：X」要优先于「Mining (.+)」
        template("Mining 目标方块：(.+)", match -> "正在挖掘 " + match.group(1)),
        // 建造指令
        template("Successfully loaded schematic for building\nOrigin: (.+)", match -> "已成功加载待建造的原理图\n原点：" + match.group(1)),
        template("Cannot load (.+) because I do not know which schematic format that is\\. Please rename the file to include the correct file extension\\.", match -> "无法加载 " + match.group(1) + "：无法识别该原理图格式，请将文件重命名并包含正确的扩展名。"),
        template("Cannot find (.+)", match -> "找不到 " + match.group(1)),
        template("Unsupported schematic format\\. Reckognized file extensions are: (.+)", match -> "不支持的原理图格式。可识别的文件扩展名：" + match.group(1)),
        // eta 指令
        template("Next segment: (.+)s \\((.+) ticks\\)\nGoal: (.+)s \\((.+) ticks\\)", match -> "下一分段：" + match.group(1) + " 秒（" + match.group(2) + " tick）\n目标：" + match.group(3) + " 秒（" + match.group(4) + " tick）"),
        // proc 指令
        template("Class: (.+)\nPriority: (.+)\nTemporary: (.+)\nDisplay name: (.+)\nLast command: (.+)", match -> "类：" + match.group(1) + "\n优先级：" + match.group(2) + "\n临时：" + translateBoolean(match.group(3)) + "\n显示名称：" + match.group(4) + "\n上一条命令：" + translateNone(match.group(5))),
        // 鞘翅提示（前缀动态时补充匹配）
        template("(.*)elytraPredictTerrain is currently disabled\\. ", match -> match.group(1) + "elytraPredictTerrain 当前已关闭。 "),
        template("Baritone doesn't know the seed of your world\\. Set it with: (.*)set elytraNetherSeed seedgoeshere\n", match -> "Baritone 不知道当前世界的种子。请使用 " + match.group(1) + "set elytraNetherSeed 种子 进行设置\n"),
        template("Baritone Elytra is predicting terrain assuming that (.+) is the correct seed\\. Change that with (.*)set elytraNetherSeed seedgoeshere, or disable it with (.*)set elytraPredictTerrain false", match -> "Baritone 鞘翅正在以种子 " + match.group(1) + " 预测地形。如需更改，请使用 " + match.group(2) + "set elytraNetherSeed 种子；如需关闭，请使用 " + match.group(3) + "set elytraPredictTerrain false"),
        template("Baritone Elytra is not predicting terrain\\. If you don't know the seed, this is the correct thing to do\\. If you do know the seed, input it with (.*)set elytraNetherSeed seedgoeshere, and then enable it with (.*)set elytraPredictTerrain true", match -> "Baritone 鞘翅未在预测地形。如果不知道种子，保持现状即可；如果知道种子，请使用 " + match.group(1) + "set elytraNetherSeed 种子 输入，再使用 " + match.group(2) + "set elytraPredictTerrain true 开启"),
        template("Goal: (.+)", match -> "目标：" + match.group(1)),
        template("All waypoints by tag (.+):", match -> "标签 " + match.group(1) + " 下的全部路径点："),
        template("Cleared (\\d+) waypoints, click to restore them", match -> "已清除 " + match.group(1) + " 个路径点，点击恢复"),
        template("Invalid tag, \"(.+)\"", match -> "无效标签：\"" + match.group(1) + "\""),
        template("Setting (.+) can only be used via the api\\.", match -> "设置 " + match.group(1) + " 只能通过 API 使用。"),
        template("Toggled setting (.+) to (.+)", match -> "已将设置 " + match.group(1) + " 切换为 " + match.group(2)),
        template("Successfully set (.+) to (.+)", match -> "已将 " + match.group(1) + " 设置为 " + match.group(2)),
        template("Successfully reset (.+) to (.+)", match -> "已将 " + match.group(1) + " 重置为 " + match.group(2)),
        template("All modified settings containing the string '(.+)':", match -> "名称包含 '" + match.group(1) + "' 的全部已修改设置："),
        template("All settings containing the string '(.+)':", match -> "名称包含 '" + match.group(1) + "' 的全部设置："),
        template("All modified settings:", match -> "全部已修改设置："),
        template("All settings:", match -> "全部设置："),
        template("Command not found: (.+)", match -> "找不到命令：" + match.group(1)),
        template("Not enough arguments \\(expected at least (\\d+)\\)", match -> "参数不足（至少需要 " + match.group(1) + " 个）"),
        template("Too many arguments \\(expected at most (\\d+)\\)", match -> "参数过多（最多允许 " + match.group(1) + " 个）"),
        template("Error at argument #(.+): (.+)", match -> "第 " + match.group(1) + " 个参数出错：" + translateExpectedDetail(match.group(2))),
        template("Expected (.+), but got (.+) instead", match -> "应为 " + translateExpectedName(match.group(1)) + "，但实际得到 " + match.group(2)),
        template("Expected (.+)", match -> "应为 " + translateExpectedName(match.group(1))),
        template("Could not find a handler for type (.+)", match -> "找不到类型 " + match.group(1) + " 对应的参数处理器"),
        template("Invalid (.+): (.+)", match -> "无效的" + match.group(1) + "：" + match.group(2)),
        template("Going to: (.+)", match -> "正在前往：" + match.group(1)),
        template("Exploring from (.+)", match -> "从 " + match.group(1) + " 开始探索"),
        template("Mining (.+)", match -> "正在挖掘 " + translateBlockName(match.group(1))),
        template("mine (.+)", match -> "挖掘 " + translateBlockName(match.group(1))),
        template("> mine (.+)", match -> "> 挖掘 " + translateBlockName(match.group(1))),
        template("#mine\\s+(.+)", match -> "#挖掘 " + translateBlockName(match.group(1))),
        template(".*BlockOptionalMeta\\{block=Block\\{([^,}]+).*", match -> "目标方块：" + translateBlockName(match.group(1))),
        template("Following all (.+)", match -> "正在跟随所有" + match.group(1)),
        template("Queued (\\d+) chunks for repacking", match -> "已将 " + match.group(1) + " 个区块加入重新整理队列"),
        template("Removed (\\d+) selections", match -> "已移除 " + match.group(1) + " 个选区"),
        template("Transformed (\\d+) selections", match -> "已变换 " + match.group(1) + " 个选区"),
        template("Restored (\\d+) waypoints", match -> "已恢复 " + match.group(1) + " 个路径点"),
        template("Position: (.+)", match -> "位置：" + match.group(1)),
        template("Old value: (.+)", match -> "旧值：" + match.group(1)),
        template("You are running Baritone v(.+)", match -> "当前运行 Baritone v" + match.group(1)),
        template("Value of setting (.+):", match -> "设置 " + match.group(1) + " 的值："),
        template("Settings reloaded from (.+)", match -> "已从 " + match.group(1) + " 重新加载设置"),
        template("Failed loading native library\\. Your CPU is (.+) and your operating system is (.+)\\. Supported architectures are 64 bit x86, and 64 bit ARM\\. Supported operating systems are Windows, Linux, and Mac", match -> "无法加载原生库。CPU 架构为 " + match.group(1) + "，操作系统为 " + match.group(2) + "。支持 64 位 x86、64 位 ARM，以及 Windows、Linux 和 Mac。"),
        template("unable to land at (.+)", match -> "无法在 " + match.group(1) + " 降落"),
        template("Starting to search for path from (.+) to (.+)", match -> "开始搜索从 " + match.group(1) + " 到 " + match.group(2) + " 的路径"),
        template("Finished finding a path from (.+) to (.+)\\. (.+) nodes considered", match -> "已找到从 " + match.group(1) + " 到 " + match.group(2) + " 的路径，共检查 " + match.group(3) + " 个节点"),
        template("Found path segment from (.+) towards (.+)\\. (.+) nodes considered", match -> "已找到从 " + match.group(1) + " 前往 " + match.group(2) + " 的路径分段，共检查 " + match.group(3) + " 个节点"),
        template("Pathing exception: (.+)", match -> "寻路异常：" + match.group(1)),
        template("Successfully loaded schematic for building", match -> "已成功加载待建造的原理图"),
        template("Origin: (.+)", match -> "原点：" + match.group(1)),
        template("Explore filter applied\\. Inverted: (.+)", match -> "探索过滤器已应用，反转：" + match.group(1)),
        template("Unable to find any path to (.+), blacklisting presumably unreachable closest instance\\.\\.\\.", match -> "无法找到前往 " + match.group(1) + " 的路径，正在将可能无法到达的最近目标加入黑名单……"),
        template("Unable to find any path to (.+), canceling mine", match -> "无法找到前往 " + match.group(1) + " 的路径，已取消挖掘"),
        template("No locations for (.+) known, cancelling", match -> "没有 " + match.group(1) + " 的已知位置，已取消"),
        template("Creating a tunnel (.+) block\\(s\\) high, (.+) block\\(s\\) wide, and (.+) block\\(s\\) deep", match -> "正在创建高 " + match.group(1) + "、宽 " + match.group(2) + "、深 " + match.group(3) + " 格的隧道"),
        template("Invalid syntax in setting file: (.+)", match -> "设置文件语法无效：" + match.group(1)),
        template("Unable to parse line (.+)", match -> "无法解析行 " + match.group(1)),
        template("Have (\\d+) valid items", match -> "持有 " + match.group(1) + " 个有效物品"),
        template("No known locations of (.+), canceling GetToBlock", match -> "没有 " + match.group(1) + " 的已知位置，已取消前往该方块"),
        template("Unable to find any path to (.+), canceling GetToBlock", match -> "无法找到前往 " + match.group(1) + " 的路径，已取消前往该方块"),
        template("Unable to find any path to (.+), blacklisting presumably unreachable closest instances\\.\\.\\.", match -> "无法找到前往 " + match.group(1) + " 的路径，正在将可能无法到达的最近目标加入黑名单……"),
        template("Loaded (\\d+) positions", match -> "已加载 " + match.group(1) + " 个位置"),
        template("Starting layer (\\d+)", match -> "开始第 " + match.group(1) + " 层"),
        template("Repeating build in vector (.+), new origin is (.+)", match -> "在向量 " + match.group(1) + " 处重复建造，新原点为 " + match.group(2)),
        template("Skipping layer that I cannot construct! Layer #(\\d+)", match -> "跳过无法建造的层！第 #" + match.group(1) + " 层")
    );

    /**
     * 命令参数类型英文名 → 中文，用于翻译「应为 XXX」类报错中的类型名
     */
    private static final Map<String, String> EXPECTED_TYPE_NAMES = Map.ofEntries(
        Map.entry("Integer", "整数"),
        Map.entry("Boolean", "布尔值"),
        Map.entry("Double", "小数"),
        Map.entry("Float", "小数"),
        Map.entry("Long", "长整数"),
        Map.entry("String", "字符串"),
        Map.entry("Vec3i", "整数坐标"),
        Map.entry("Goal", "目标"),
        Map.entry("Axis", "坐标轴"),
        Map.entry("Direction", "方向"),
        Map.entry("BlockOptionalMeta", "方块"),
        Map.entry("BlockById", "方块"),
        Map.entry("ItemById", "物品"),
        Map.entry("EntityClassById", "实体类型"),
        Map.entry("NearbyPlayer", "附近玩家"),
        Map.entry("RelativeCoordinate", "相对坐标"),
        Map.entry("RelativeBlockPos", "相对方块坐标"),
        Map.entry("RelativeFile", "相对文件路径"),
        Map.entry("RelativeGoal", "相对目标"),
        Map.entry("RelativeGoalBlock", "相对目标方块"),
        Map.entry("RelativeGoalXZ", "相对目标坐标"),
        Map.entry("RelativeGoalYLevel", "相对目标高度"),
        Map.entry("ForAxis", "坐标轴"),
        Map.entry("ForDirection", "方向"),
        Map.entry("ForBlockOptionalMeta", "方块"),
        Map.entry("ForWaypoints", "路径点"),
        Map.entry("an action", "动作"),
        Map.entry("a valid setting", "有效设置"),
        Map.entry("a toggleable setting", "可切换的设置"),
        Map.entry("a valid value", "有效值"),
        Map.entry("either \"invert\" or nothing", "\"invert\" 或留空")
    );

    /**
     * 翻译「第 N 个参数出错：」后面的细节文本
     * 支持嵌套形式「Expected X, but got Y instead」与「Expected X」
     */
    private static String translateExpectedDetail(String detail) {
        Matcher both = Pattern.compile("Expected (.+), but got (.+) instead").matcher(detail);
        if (both.matches()) {
            return "应为 " + translateExpectedName(both.group(1)) + "，但实际得到 " + both.group(2);
        }
        Matcher only = Pattern.compile("Expected (.+)").matcher(detail);
        if (only.matches()) {
            return "应为 " + translateExpectedName(only.group(1));
        }
        return detail;
    }

    /**
     * 翻译单个类型名，支持「a valid page (1-N)」动态页码与 Datatype 后缀剥离
     */
    private static String translateExpectedName(String name) {
        String direct = EXPECTED_TYPE_NAMES.get(name);
        if (direct != null) return direct;
        Matcher page = Pattern.compile("a valid page \\((\\d+)-(\\d+)\\)").matcher(name);
        if (page.matches()) return "有效页码（" + page.group(1) + "-" + page.group(2) + "）";
        if (name.endsWith("Datatype")) {
            return translateExpectedName(name.substring(0, name.length() - "Datatype".length()));
        }
        return name;
    }

    /**
     * 翻译 Goal 目标体，例如「GoalBlock{x=1,y=2,z=3}」→「方块坐标（1, 2, 3）」
     */
    private static String translateGoalBody(String type, String body) {
        String values = Arrays.stream(body.split(","))
            .map(pair -> {
                int i = pair.indexOf('=');
                return (i >= 0 ? pair.substring(i + 1) : pair).trim();
            })
            .collect(Collectors.joining(", "));
        return switch (type) {
            case "Block" -> "方块坐标（" + values + "）";
            case "XZ" -> "坐标（" + values + "）";
            case "YLevel" -> "高度 " + values;
            case "GetToBlock" -> "前往方块（" + values + "）";
            case "TwoBlocks" -> "双格（" + values + "）";
            case "Near" -> "靠近（" + values + "）";
            case "StrictDirection" -> "沿方向（" + values + "）";
            case "Inverted" -> "（远离）";
            case "Axis" -> "坐标轴";
            default -> "Goal" + type + "{" + body + "}";
        };
    }

    /**
     * 翻译 proc 指令里的 true/false
     */
    private static String translateBoolean(String value) {
        return switch (value) {
            case "true" -> "是";
            case "false" -> "否";
            default -> value;
        };
    }

    /**
     * 翻译 proc 指令里的 None
     */
    private static String translateNone(String value) {
        return "None".equals(value) ? "无" : value;
    }

    /**
     * 指令简介（getShortDesc）英文 → 中文，用于 help 列表与悬浮提示
     */
    private static final Map<String, String> SHORT_DESCRIPTIONS = Map.ofEntries(
        Map.entry("View all commands or help on specific ones", "查看全部命令或指定命令的帮助"),
        Map.entry("View or change settings", "查看或修改设置"),
        Map.entry("List modified settings", "列出已修改的设置"),
        Map.entry("Reset all settings or just one", "重置全部或单项设置"),
        Map.entry("Set or clear the goal", "设置或清除目标"),
        Map.entry("Go to a coordinate or block", "前往指定坐标或方块"),
        Map.entry("Start heading towards the goal", "开始前往目标"),
        Map.entry("View process state information", "查看进程状态信息"),
        Map.entry("View the current ETA", "查看当前预计到达时间"),
        Map.entry("View the Baritone version", "查看 Baritone 版本"),
        Map.entry("Re-cache chunks", "重新缓存区块"),
        Map.entry("Build a schematic", "建造原理图"),
        Map.entry("Builds the loaded schematic", "建造已加载的原理图"),
        Map.entry("Start heading towards your camera", "开始前往摄像机位置"),
        Map.entry("Set a goal to the axes", "将目标设为坐标轴"),
        Map.entry("Force cancel", "强制取消"),
        Map.entry("Call System.gc()", "调用 System.gc()"),
        Map.entry("Run away from the current goal", "远离当前目标"),
        Map.entry("Set a goal to tunnel in your current direction", "沿当前方向挖掘隧道"),
        Map.entry("Fix glitched chunks", "修复区块渲染异常"),
        Map.entry("Farm nearby crops", "耕作附近作物"),
        Map.entry("Follow entity things", "跟随实体"),
        Map.entry("Pickup items", "拾取物品"),
        Map.entry("Explore chunks from a json", "从 JSON 探索区块"),
        Map.entry("Reloads Baritone's cache for this world", "重新加载当前世界的缓存"),
        Map.entry("Saves Baritone's cache for this world", "保存当前世界的缓存"),
        Map.entry("Explore things", "探索世界"),
        Map.entry("Blacklist closest block", "将最近的方块加入黑名单"),
        Map.entry("Find positions of a certain block", "查找指定方块的位置"),
        Map.entry("Mine some blocks", "挖掘方块"),
        Map.entry("Open click", "打开正前方方块"),
        Map.entry("Used to get out of caves, mines, ...", "用于离开洞穴、矿洞等"),
        Map.entry("Travel in your current direction", "沿当前方向前进"),
        Map.entry("Manage waypoints", "管理路径点"),
        Map.entry("Sets your home waypoint", "设置家路径点"),
        Map.entry("Path to your home waypoint", "前往家路径点"),
        Map.entry("WorldEdit-like commands", "类 WorldEdit 选区操作"),
        Map.entry("elytra time", "鞘翅飞行"),
        Map.entry("Pauses Baritone until you use resume", "暂停 Baritone，使用 resume 恢复"),
        Map.entry("Resumes Baritone after a pause", "恢复已暂停的 Baritone"),
        Map.entry("Tells you if Baritone is paused", "查看 Baritone 是否暂停"),
        Map.entry("Cancel what Baritone is currently doing", "取消 Baritone 当前任务")
    );

    /**
     * 翻译消息末尾/悬浮提示中的指令简介
     * 形态一：「命令名 - 简介」；形态二：「命令名\n简介\n\n...」（help 悬浮）
     */
    private static String translateShortDescriptions(String text) {
        for (Map.Entry<String, String> entry : SHORT_DESCRIPTIONS.entrySet()) {
            String suffix = " - " + entry.getKey();
            if (text.endsWith(suffix)) {
                return text.substring(0, text.length() - suffix.length()) + " - " + entry.getValue();
            }
            String anchor = "\n" + entry.getKey() + "\n\n";
            int index = text.indexOf(anchor);
            if (index >= 0) {
                return text.substring(0, index) + "\n" + entry.getValue() + text.substring(index + anchor.length());
            }
        }
        return text;
    }

    private BaritoneChatTranslations() {}

    /**
     * 翻译方块名称（使用 Minecraft 内置翻译）
     * 例如：acacia_fence_gate -> 金合欢木栅栏门
     */
    public static String translateBlockId(String blockId) {
        if (blockId == null || blockId.isEmpty()) return blockId;
        
        try {
            // 遍历所有已注册的方块，查找匹配的方块
            for (Block block : BuiltInRegistries.BLOCK) {
                String registeredId = BuiltInRegistries.BLOCK.getKey(block).toString();
                String path = registeredId.contains(":") ? registeredId.split(":")[1] : registeredId;
                
                // 匹配方块ID（支持带命名空间和不带命名空间）
                if (path.equals(blockId) || registeredId.equals(blockId)) {
                    // 使用 Minecraft 内置翻译获取中文名
                    String translated = block.getName().getString();
                    // 如果翻译成功，返回翻译结果
                    if (translated != null && !translated.isEmpty() && !translated.equals(blockId)) {
                        return translated;
                    }
                    break;
                }
            }
        } catch (Exception e) {
            // 解析失败，返回原始文本
        }
        
        return blockId;
    }

    private static String translateBlockName(String blockId) {
        return translateBlockId(blockId);
    }

    public static String translate(String text) {
        if (!BaritoneTranslationToggle.enabled() || text == null || text.isEmpty()) return text;
        try {
            Matcher blockMatcher = Pattern.compile("BlockOptionalMeta(?:Lookup)?(?:\\{|\\[)block=Block\\{([^,}]+)(?:,properties=\\{\\})?(?:\\}|\\])").matcher(text);
            StringBuffer translatedBlocks = new StringBuffer();
            while (blockMatcher.find()) {
                blockMatcher.appendReplacement(translatedBlocks,
                    Matcher.quoteReplacement("目标方块：" + translateBlockName(blockMatcher.group(1))));
            }
            blockMatcher.appendTail(translatedBlocks);
            text = translatedBlocks.toString();
            text = text.replace(",properties={}", "");
            text = text.replace("BlockOptionalMetaLookup", "");
            text = text.replace("BlockOptionalMeta", "");
        } catch (RuntimeException ignored) {
            return text;
        }
        String exact = EXACT.get(text);
        if (exact != null) return exact;
        for (Template template : TEMPLATES) {
            var matcher = template.pattern().matcher(text);
            if (matcher.matches()) return template.translation().apply(matcher.toMatchResult());
        }
        return translateShortDescriptions(text);
    }

    public static Component translate(Component component) {
        if (!BaritoneTranslationToggle.enabled() || component == null) return component;
        String fullText = component.getString();
        String translatedText = translate(fullText);
        if (!translatedText.equals(fullText)) {
            return Component.literal(translatedText).withStyle(component.getStyle());
        }
        MutableComponent translated = Component.empty();
        component.visit((style, text) -> {
            translated.append(Component.literal(translate(text)).withStyle(style));
            return Optional.empty();
        }, Style.EMPTY);
        return translated;
    }

    public static Component[] translate(Component[] components) {
        if (!BaritoneTranslationToggle.enabled() || components == null) return components;
        Component[] translated = new Component[components.length];
        for (int i = 0; i < components.length; i++) translated[i] = translate(components[i]);
        return translated;
    }

    private static Template template(String pattern, Function<MatchResult, String> translation) {
        return new Template(Pattern.compile(pattern, Pattern.DOTALL), translation);
    }

    private record Template(Pattern pattern, Function<MatchResult, String> translation) {}
}
