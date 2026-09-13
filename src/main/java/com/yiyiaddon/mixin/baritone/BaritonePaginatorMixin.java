package com.yiyiaddon.mixin.baritone;

import baritone.api.command.helpers.Paginator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Baritone 分页器翻译 Mixin
 * 
 * 翻译分页器的提示文本
 * 影响范围：Baritone 指令输出的分页导航提示
 */
@Mixin(value = Paginator.class, remap = false)
public abstract class BaritonePaginatorMixin {
    
    /**
     * 翻译"点击查看上一页"提示
     */
    @ModifyConstant(method = "display(Ljava/util/function/Function;Ljava/lang/String;)V", constant = @Constant(stringValue = "Click to view previous page"))
    private String yiyiaddon$translatePreviousPageHint(String value) {
        return "点击查看上一页";
    }

    /**
     * 翻译"点击查看下一页"提示
     */
    @ModifyConstant(method = "display(Ljava/util/function/Function;Ljava/lang/String;)V", constant = @Constant(stringValue = "Click to view next page"))
    private String yiyiaddon$translateNextPageHint(String value) {
        return "点击查看下一页";
    }

    /**
     * 翻译"有效页码"错误提示
     */
    @ModifyConstant(method = "paginate(Lbaritone/api/command/argument/IArgConsumer;Lbaritone/api/command/helpers/Paginator;Ljava/lang/Runnable;Ljava/util/function/Function;Ljava/lang/String;)V", constant = @Constant(stringValue = "a valid page (1-%d)"))
    private static String yiyiaddon$translateValidPageError(String value) {
        return "有效页码（1-%d）";
    }
}
