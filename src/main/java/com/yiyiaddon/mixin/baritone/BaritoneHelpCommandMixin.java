package com.yiyiaddon.mixin.baritone;

import baritone.command.defaults.HelpCommand;
import baritone.api.command.ICommand;
import com.yiyiaddon.integration.baritone.BaritoneCommandTranslations;
import com.yiyiaddon.integration.baritone.BaritoneTranslationToggle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Baritone Help 指令翻译 Mixin
 * 
 * 拦截 HelpCommand 类的多处文本输出，实现帮助系统的完整中文化
 * 包括：指令描述、提示文字、列表标题等
 */
@Mixin(value = HelpCommand.class, remap = false)
public abstract class BaritoneHelpCommandMixin {
    
    /**
     * 翻译 help 指令自身的简短描述
     */
    @Inject(method = "getShortDesc()Ljava/lang/String;", at = @org.spongepowered.asm.mixin.injection.At("HEAD"), cancellable = true)
    private void yiyiaddon$translateShortDescription(CallbackInfoReturnable<String> info) {
        if (BaritoneTranslationToggle.enabled()) info.setReturnValue("查看全部命令或指定命令的帮助");
    }

    /**
     * 翻译"点击返回帮助菜单"提示
     */
    @ModifyConstant(method = "execute(Ljava/lang/String;Lbaritone/api/command/argument/IArgConsumer;)V", constant = @Constant(stringValue = "Click to return to the help menu"))
    private String yiyiaddon$translateReturnHint(String value) {
        return BaritoneTranslationToggle.enabled() ? "点击返回帮助菜单" : value;
    }

    /**
     * 翻译"所有 Baritone 命令"标题
     */
    @ModifyConstant(method = "lambda$execute$1()V", constant = @Constant(stringValue = "All Baritone commands (clickable):"))
    private String yiyiaddon$translateCommandListTitle(String value) {
        return BaritoneTranslationToggle.enabled() ? "所有 Baritone 命令（可点击）：" : value;
    }

    /**
     * 翻译"点击查看完整帮助"提示
     */
    @ModifyConstant(method = "lambda$execute$2(Ljava/lang/String;Lbaritone/api/command/ICommand;)Lnet/minecraft/network/chat/Component;", constant = @Constant(stringValue = "\n\nClick to view full help"))
    private static String yiyiaddon$translateFullHelpHint(String value) {
        return BaritoneTranslationToggle.enabled() ? "\n\n点击查看完整帮助" : value;
    }

    /**
     * 拦截指令列表中的描述文本，返回中文化版本
     */
    @Redirect(
        method = "lambda$execute$2(Ljava/lang/String;Lbaritone/api/command/ICommand;)Lnet/minecraft/network/chat/Component;",
        at = @At(value = "INVOKE", target = "Lbaritone/api/command/ICommand;getShortDesc()Ljava/lang/String;")
    )
    private static String yiyiaddon$translateListedDescription(ICommand command) {
        return yiyiaddon$translateShortDescription(command.getShortDesc());
    }

    /**
     * 拦截详细帮助页面中的描述文本，返回中文化版本
     */
    @Redirect(
        method = "execute(Ljava/lang/String;Lbaritone/api/command/argument/IArgConsumer;)V",
        at = @At(value = "INVOKE", target = "Lbaritone/api/command/ICommand;getShortDesc()Ljava/lang/String;")
    )
    private String yiyiaddon$translateDetailedDescription(ICommand command) {
        return yiyiaddon$translateShortDescription(command.getShortDesc());
    }

    /**
     * 核心翻译方法：将英文指令描述转换为中文
     * 
     * @param text 英文描述
     * @return 中文描述（如果未找到映射则返回原文）
     */
    private static String yiyiaddon$translateShortDescription(String text) {
        if (!BaritoneTranslationToggle.enabled()) return text;
        return switch (text) {
            case "View all commands or help on specific ones" -> "查看全部命令或指定命令的帮助";
            case "View or change settings" -> "查看或修改设置";
            case "List modified settings" -> "列出已修改的设置";
            case "Reset all settings or just one" -> "重置全部设置或指定设置";
            case "Set or clear the goal" -> "设置或清除目标";
            case "Go to a coordinate or block" -> "前往指定坐标或方块";
            case "Start heading towards the goal" -> "开始前往当前目标";
            case "View process state information" -> "查看进程状态信息";
            case "View the current ETA" -> "查看当前预计到达时间";
            case "View the Baritone version" -> "查看 Baritone 版本";
            case "Re-cache chunks" -> "重新缓存区块";
            case "Build a schematic" -> "建造原理图";
            case "Builds the loaded schematic" -> "建造已加载的 Litematica 原理图";
            case "Start heading towards your camera" -> "开始前往摄像机所在位置";
            case "Set a goal to the axes" -> "将目标设置到坐标轴";
            case "Force cancel" -> "强制取消";
            case "Call System.gc()" -> "调用 System.gc()";
            case "Run away from the current goal" -> "远离当前目标";
            case "Set a goal to tunnel in your current direction" -> "设置沿当前方向挖掘隧道的目标";
            case "Fix glitched chunks" -> "修复渲染异常的区块";
            case "Farm nearby crops" -> "收割附近的作物";
            case "Follow entity things" -> "跟随实体";
            case "Pickup items" -> "拾取物品";
            case "Explore chunks from a json" -> "按 JSON 文件探索区块";
            case "Reloads Baritone's cache for this world" -> "重新加载 Baritone 在当前世界的缓存";
            case "Saves Baritone's cache for this world" -> "保存 Baritone 在当前世界的缓存";
            case "Explore things" -> "探索区域";
            case "Blacklist closest block" -> "将最近的方块加入黑名单";
            case "Find positions of a certain block" -> "查找指定方块的位置";
            case "Mine some blocks" -> "挖掘指定方块";
            case "Open click" -> "打开指定位置或方块";
            case "Used to get out of caves, mines, ..." -> "用于离开洞穴、矿井等地下区域";
            case "Travel in your current direction" -> "沿当前方向移动";
            case "Manage waypoints" -> "管理路径点";
            case "Sets your home waypoint" -> "设置家路径点";
            case "Path to your home waypoint" -> "前往家路径点";
            case "WorldEdit-like commands" -> "类似 WorldEdit 的选区命令";
            case "elytra time" -> "使用鞘翅导航";
            case "Pauses Baritone until you use resume" -> "暂停 Baritone，直到执行恢复命令";
            case "Resumes Baritone after a pause" -> "恢复已暂停的 Baritone";
            case "Tells you if Baritone is paused" -> "查看 Baritone 是否处于暂停状态";
            case "Cancel what Baritone is currently doing" -> "取消 Baritone 当前正在执行的任务";
            default -> text;
        };
    }
}
