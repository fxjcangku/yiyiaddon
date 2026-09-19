package com.yiyiaddon.mixin.client;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.suggestion.Suggestions;
import com.yiyiaddon.a.b;
import com.yiyiaddon.a.d;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.CommandSuggestions.SuggestionsList;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.util.FormattedCharSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CommandSuggestions.class)
public abstract class CommandSuggestionsCompletionMixin {
   @Shadow
   @Final
   private EditBox input;
   @Shadow
   @Final
   private List<FormattedCharSequence> commandUsage;
   @Shadow
   private ParseResults<ClientSuggestionProvider> currentParse;
   @Shadow
   private boolean currentParseIsCommand;
   @Shadow
   private boolean currentParseIsMessage;
   @Shadow
   private CompletableFuture<Suggestions> pendingSuggestions;
   @Shadow
   private SuggestionsList suggestions;
   @Shadow
   private boolean keepSuggestions;
   private static final Logger YIYIADDON$LOGGER = LoggerFactory.getLogger("yiyiaddon/command");
   @Unique
   private b.a yiyiaddon$plan;
   @Unique
   private boolean yiyiaddon$overflowReported;

   @Shadow
   protected abstract void updateUsageInfo(ParseResults<ClientSuggestionProvider> var1, Suggestions var2);

   @Inject(method = "updateCommandInfo", at = @At("HEAD"), cancellable = true)
   private void yiyiaddon$suggestClientCommands(CallbackInfo var1) {
      String var2 = this.input.getValue();
      if (b.a(var2)) {
         if (this.currentParse != null && !this.currentParse.getReader().getString().equals(var2)) {
            this.currentParse = null;
            this.currentParseIsCommand = false;
            this.currentParseIsMessage = false;
         }

         if (!this.keepSuggestions) {
            this.input.setSuggestion(null);
            this.suggestions = null;
         }

         this.commandUsage.clear();
         this.yiyiaddon$plan = b.a(var2);
         this.currentParse = this.yiyiaddon$plan.a();
         int var3 = this.input.getCursorPosition();
         if (var3 >= d.e().length() && (this.suggestions == null || !this.keepSuggestions)) {
            this.pendingSuggestions = this.yiyiaddon$plan.a().getCompletionSuggestions(this.currentParse, var3);
            this.pendingSuggestions.thenAccept(var1x -> {
               if (this.pendingSuggestions.isDone()) {
                  this.updateUsageInfo(this.currentParse, var1x);
               }
            });
         }

         var1.cancel();
      }
   }

   @Inject(method = "formatChat", at = @At("HEAD"), cancellable = true)
   private void yiyiaddon$guardUnparsedHighlight(String var1, int var2, CallbackInfoReturnable<FormattedCharSequence> var3) {
      ParseResults var4 = this.currentParse;
      if (var4 != null && yiyiaddon$wouldOverflow(var4, var1, var2)) {
         var3.setReturnValue(null);
         if (!this.yiyiaddon$overflowReported) {
            this.yiyiaddon$overflowReported = true;
            StringBuilder var5 = new StringBuilder();

            for (ParsedArgument var7 : var4.getContext().getLastChild().getArguments().values()) {
               var5.append(var7.getRange()).append(' ');
            }

            YIYIADDON$LOGGER.error(
               "已拦截一次补全高亮越界：text=[{}] len={} offset={} reader={} cursor={} canRead={} 参数范围=[{}]",
               var1,
               var1.length(),
               var2,
               var4.getReader().getString().length(),
               var4.getReader().getCursor(),
               var4.getReader().canRead(),
               var5.toString().trim()
            );
         }
      }
   }

   @Unique
   private static boolean yiyiaddon$wouldOverflow(ParseResults<ClientSuggestionProvider> var0, String var1, int var2) {
      int var3 = 0;

      for (ParsedArgument var5 : var0.getContext().getLastChild().getArguments().values()) {
         int var6 = Math.max(var5.getRange().getStart() - var2, 0);
         if (var6 >= var1.length()) {
            break;
         }

         int var7 = Math.min(var5.getRange().getEnd() - var2, var1.length());
         if (var7 > 0) {
            if (var3 > var6) {
               return true;
            }

            var3 = var7;
         }
      }

      if (var0.getReader().canRead()) {
         int var8 = Math.max(var0.getReader().getCursor() - var2, 0);
         if (var8 < var1.length() && var3 > var8) {
            return true;
         }
      }

      return false;
   }
}
