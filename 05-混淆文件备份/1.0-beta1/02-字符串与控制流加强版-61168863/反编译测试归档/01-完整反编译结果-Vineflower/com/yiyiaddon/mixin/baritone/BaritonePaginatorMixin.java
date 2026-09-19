package com.yiyiaddon.mixin.baritone;

import baritone.api.command.helpers.Paginator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Paginator.class, remap = false)
public abstract class BaritonePaginatorMixin {
   @ModifyConstant(method = "display(Ljava/util/function/Function;Ljava/lang/String;)V", constant = @Constant(stringValue = "Click to view previous page"))
   private String yiyiaddon$translatePreviousPageHint(String var1) {
      return "点击查看上一页";
   }

   @ModifyConstant(method = "display(Ljava/util/function/Function;Ljava/lang/String;)V", constant = @Constant(stringValue = "Click to view next page"))
   private String yiyiaddon$translateNextPageHint(String var1) {
      return "点击查看下一页";
   }

   @ModifyConstant(
      method = "paginate(Lbaritone/api/command/argument/IArgConsumer;Lbaritone/api/command/helpers/Paginator;Ljava/lang/Runnable;Ljava/util/function/Function;Ljava/lang/String;)V",
      constant = @Constant(stringValue = "a valid page (1-%d)")
   )
   private static String yiyiaddon$translateValidPageError(String var0) {
      return "有效页码（1-%d）";
   }
}
