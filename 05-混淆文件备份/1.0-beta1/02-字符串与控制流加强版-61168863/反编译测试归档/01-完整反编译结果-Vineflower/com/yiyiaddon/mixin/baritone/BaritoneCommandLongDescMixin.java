package com.yiyiaddon.mixin.baritone;

import baritone.api.command.ICommand;
import com.yiyiaddon.f.a.d;
import com.yiyiaddon.f.a.h;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   targets = {
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
   },
   remap = false
)
public abstract class BaritoneCommandLongDescMixin {
   @Inject(method = "getLongDesc()Ljava/util/List;", at = @At("HEAD"), cancellable = true, require = 0)
   private void yiyiaddon$translateLongDescription(CallbackInfoReturnable<List<String>> var1) {
      if (h.ar()) {
         List var2 = d.a((ICommand)this, null);
         if (var2 != null) {
            var1.setReturnValue(var2);
         }
      }
   }
}
