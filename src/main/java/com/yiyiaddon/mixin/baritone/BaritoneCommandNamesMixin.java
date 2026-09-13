package com.yiyiaddon.mixin.baritone;

import baritone.api.command.ICommand;
import baritone.api.command.registry.Registry;
import baritone.command.manager.CommandManager;
import com.yiyiaddon.integration.baritone.BaritoneCommandTranslations;
import com.yiyiaddon.integration.baritone.BaritoneTranslationToggle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 支持中文指令输入
 * 
 * 原理：拦截 getCommand() 方法，当用户输入中文指令名时，自动转换为英文再查找
 * 效果：用户可以输入 #帮助 或 #help，都能找到对应的指令
 */
@Mixin(value = CommandManager.class, remap = false)
public abstract class BaritoneCommandNamesMixin {
    
    @Shadow
    public abstract Registry<ICommand> getRegistry();
    
    @Inject(method = "getCommand(Ljava/lang/String;)Lbaritone/api/command/ICommand;", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$supportChineseCommandInput(String name, CallbackInfoReturnable<ICommand> cir) {
        if (!BaritoneTranslationToggle.enabled()) return;
        
        String lowerName = name.toLowerCase(Locale.US);
        
        // 尝试将中文指令名反向映射为英文
        String englishName = BaritoneCommandTranslations.reverseTranslate(lowerName);
        
        // 如果找到了对应的英文名，且与输入不同，说明用户输入的是中文
        if (!englishName.equals(lowerName)) {
            // 用英文名查找指令
            for (ICommand command : getRegistry().entries) {
                if (command.getNames().contains(englishName)) {
                    cir.setReturnValue(command);
                    return;
                }
            }
        }
    }

    @Inject(method = "tabComplete(Ljava/lang/String;)Ljava/util/stream/Stream;", at = @At("RETURN"), cancellable = true)
    private void yiyiaddon$addChineseCommandSuggestions(String prefix, CallbackInfoReturnable<Stream<String>> cir) {
        if (!BaritoneTranslationToggle.enabled() || prefix.contains(" ")) return;

        String lowerPrefix = prefix.toLowerCase(Locale.US);
        Set<String> suggestions = new LinkedHashSet<>();
        cir.getReturnValue().forEach(suggestions::add);
        for (ICommand command : getRegistry().entries) {
            for (String name : command.getNames()) {
                String chineseName = BaritoneCommandTranslations.translateCommandName(name);
                if (!chineseName.equals(name) && chineseName.startsWith(lowerPrefix)) suggestions.add(chineseName);
            }
        }
        cir.setReturnValue(suggestions.stream());
    }
}
