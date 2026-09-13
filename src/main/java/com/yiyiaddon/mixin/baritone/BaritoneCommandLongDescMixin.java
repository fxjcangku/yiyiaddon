package com.yiyiaddon.mixin.baritone;

import baritone.api.command.ICommand;
import com.yiyiaddon.integration.baritone.BaritoneCommandTranslations;
import com.yiyiaddon.integration.baritone.BaritoneTranslationToggle;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Baritone 指令详细说明翻译 Mixin
 * 
 * 拦截 Baritone 所有指令类的 getLongDesc() 方法，返回中文化的详细说明
 * 目标：37个 Baritone 默认指令类
 */
@Mixin(targets = {
    "baritone.command.defaults.AxisCommand",
    "baritone.command.defaults.BlacklistCommand",
    "baritone.command.defaults.BuildCommand",
    "baritone.command.defaults.ClickCommand",
    "baritone.command.defaults.ComeCommand",
    "baritone.command.defaults.CommandAlias",
    "baritone.command.defaults.ETACommand",
    "baritone.command.defaults.ElytraCommand",
    "baritone.command.defaults.ExecutionControlCommands$2",
    "baritone.command.defaults.ExecutionControlCommands$3",
    "baritone.command.defaults.ExecutionControlCommands$4",
    "baritone.command.defaults.ExecutionControlCommands$5",
    "baritone.command.defaults.ExploreCommand",
    "baritone.command.defaults.ExploreFilterCommand",
    "baritone.command.defaults.FarmCommand",
    "baritone.command.defaults.FindCommand",
    "baritone.command.defaults.FollowCommand",
    "baritone.command.defaults.ForceCancelCommand",
    "baritone.command.defaults.GcCommand",
    "baritone.command.defaults.GoalCommand",
    "baritone.command.defaults.GotoCommand",
    "baritone.command.defaults.HelpCommand",
    "baritone.command.defaults.InvertCommand",
    "baritone.command.defaults.LitematicaCommand",
    "baritone.command.defaults.MineCommand",
    "baritone.command.defaults.PathCommand",
    "baritone.command.defaults.PickupCommand",
    "baritone.command.defaults.ProcCommand",
    "baritone.command.defaults.ReloadAllCommand",
    "baritone.command.defaults.RenderCommand",
    "baritone.command.defaults.RepackCommand",
    "baritone.command.defaults.SaveAllCommand",
    "baritone.command.defaults.SelCommand",
    "baritone.command.defaults.SetCommand",
    "baritone.command.defaults.SchematicaCommand",
    "baritone.command.defaults.SurfaceCommand",
    "baritone.command.defaults.ThisWayCommand",
    "baritone.command.defaults.TunnelCommand",
    "baritone.command.defaults.VersionCommand",
    "baritone.command.defaults.WaypointsCommand"
}, remap = false)
public abstract class BaritoneCommandLongDescMixin {
    
    /**
     * 翻译 Baritone 指令的详细说明
     * 
     * 注入点：getLongDesc() 方法头部
     * 效果：返回中文化的多行指令说明
     */
    @Inject(method = "getLongDesc()Ljava/util/List;", at = @At("HEAD"), cancellable = true, require = 0)
    private void yiyiaddon$translateLongDescription(CallbackInfoReturnable<List<String>> info) {
        if (!BaritoneTranslationToggle.enabled()) return;
        List<String> translation = BaritoneCommandTranslations.translateLongDescription((ICommand) (Object) this, null);
        if (translation != null) info.setReturnValue(translation);
    }
}
