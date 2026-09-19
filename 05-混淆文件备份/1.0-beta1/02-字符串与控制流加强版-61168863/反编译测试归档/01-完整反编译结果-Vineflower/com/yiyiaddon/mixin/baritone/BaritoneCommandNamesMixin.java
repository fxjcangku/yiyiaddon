package com.yiyiaddon.mixin.baritone;

import baritone.api.command.ICommand;
import baritone.api.command.registry.Registry;
import baritone.command.manager.CommandManager;
import com.yiyiaddon.f.a.d;
import com.yiyiaddon.f.a.h;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.stream.Stream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CommandManager.class, remap = false)
public abstract class BaritoneCommandNamesMixin {
   @Shadow
   public abstract Registry<ICommand> getRegistry();

   @Inject(method = "getCommand(Ljava/lang/String;)Lbaritone/api/command/ICommand;", at = @At("HEAD"), cancellable = true)
   private void yiyiaddon$supportChineseCommandInput(String var1, CallbackInfoReturnable<ICommand> var2) {
      if (h.ar()) {
         String var3 = var1.toLowerCase(Locale.US);
         String var4 = d.bP(var3);
         if (!var4.equals(var3)) {
            for (ICommand var6 : this.getRegistry().entries) {
               if (var6.getNames().contains(var4)) {
                  var2.setReturnValue(var6);
                  return;
               }
            }
         }
      }
   }

   @Inject(method = "tabComplete(Ljava/lang/String;)Ljava/util/stream/Stream;", at = @At("RETURN"), cancellable = true)
   private void yiyiaddon$addChineseCommandSuggestions(String var1, CallbackInfoReturnable<Stream<String>> var2) {
      if (h.ar() && !var1.contains(" ")) {
         String var3 = var1.toLowerCase(Locale.US);
         LinkedHashSet var4 = new LinkedHashSet();
         ((Stream)var2.getReturnValue()).forEach(var4::add);

         for (ICommand var6 : this.getRegistry().entries) {
            for (String var8 : var6.getNames()) {
               String var9 = d.bO(var8);
               if (!var9.equals(var8) && var9.startsWith(var3)) {
                  var4.add(var9);
               }
            }
         }

         var2.setReturnValue(var4.stream());
      }
   }
}
