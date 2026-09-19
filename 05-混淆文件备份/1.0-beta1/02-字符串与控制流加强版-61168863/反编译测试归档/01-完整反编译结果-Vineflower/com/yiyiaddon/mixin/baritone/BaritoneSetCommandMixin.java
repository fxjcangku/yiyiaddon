package com.yiyiaddon.mixin.baritone;

import baritone.Baritone;
import baritone.api.Settings.Setting;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.utils.SettingsUtil;
import baritone.command.defaults.SetCommand;
import com.yiyiaddon.f.a.e;
import com.yiyiaddon.f.a.h;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.ClickEvent.SuggestCommand;
import net.minecraft.network.chat.HoverEvent.ShowText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SetCommand.class, remap = false)
public abstract class BaritoneSetCommandMixin {
   @Inject(
      method = "lambda$execute$4(Lbaritone/api/Settings$Setting;)Lnet/minecraft/network/chat/Component;",
      at = @At("HEAD"),
      cancellable = true,
      require = 0
   )
   private static void yiyiaddon$localizeSettingEntry(Setting<?> var0, CallbackInfoReturnable<Component> var1) {
      if (h.ar()) {
         String var2 = var0.getName();
         e.a var3 = e.a(var2);
         String var4 = SettingsUtil.settingTypeToString(var0);
         String var5 = SettingsUtil.settingValueToString(var0);
         String var6 = SettingsUtil.settingDefaultToString(var0);
         MutableComponent var7 = Component.literal(" (" + var4 + ")").withStyle(ChatFormatting.DARK_GRAY);
         MutableComponent var8 = Component.literal(var3.a() + " (" + var2 + ")")
            .withStyle(ChatFormatting.GRAY)
            .append("\n\n说明：\n" + var3.c())
            .append("\n\n类型：" + var4)
            .append("\n\n当前值：\n" + var5)
            .append("\n\n默认值：\n" + var6)
            .append("\n\n真实键：" + var2);
         String var9 = IBaritoneChatControl.FORCE_COMMAND_PREFIX + Baritone.settings().prefix.value + "set " + var2;
         MutableComponent var10 = Component.literal(var3.a())
            .withStyle(ChatFormatting.GRAY)
            .append(Component.literal(" [" + var2 + "]").withStyle(ChatFormatting.WHITE))
            .append(var7);
         var10.setStyle(var10.getStyle().withHoverEvent(new ShowText(var8)).withClickEvent(new SuggestCommand(var9)));
         var1.setReturnValue(var10);
      }
   }
}
