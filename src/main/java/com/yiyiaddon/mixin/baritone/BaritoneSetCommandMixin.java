package com.yiyiaddon.mixin.baritone;

import baritone.Baritone;
import baritone.api.Settings;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.utils.SettingsUtil;
import baritone.command.defaults.SetCommand;
import com.yiyiaddon.integration.baritone.BaritoneSettingTranslations;
import com.yiyiaddon.integration.baritone.BaritoneSettingTranslations.Translation;
import com.yiyiaddon.integration.baritone.BaritoneTranslationToggle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Baritone Set 指令设置项翻译 Mixin
 * 
 * 拦截设置列表的生成逻辑，将英文设置项替换为中文名称和描述
 * 鼠标悬停显示详细信息，点击自动填充指令
 */
@Mixin(value = SetCommand.class, remap = false)
public abstract class BaritoneSetCommandMixin {
    
    /**
     * 本地化设置项条目
     * 
     * 格式：中文名 [英文键] (类型)
     * 悬停：完整的中文说明、类型、当前值、默认值
     * 点击：自动填充 #set 指令
     */
    @Inject(
        method = "lambda$execute$4(Lbaritone/api/Settings$Setting;)Lnet/minecraft/network/chat/Component;",
        at = @At("HEAD"),
        cancellable = true,
        require = 0
    )
    private static void yiyiaddon$localizeSettingEntry(Settings.Setting<?> setting, CallbackInfoReturnable<Component> info) {
        if (!BaritoneTranslationToggle.enabled()) return;

        String key = setting.getName();
        Translation translation = BaritoneSettingTranslations.get(key);
        String type = SettingsUtil.settingTypeToString(setting);
        String value = SettingsUtil.settingValueToString(setting);
        String defaultValue = SettingsUtil.settingDefaultToString(setting);

        // 类型标签（深灰色）
        MutableComponent typeText = Component.literal(" (" + type + ")")
            .withStyle(ChatFormatting.DARK_GRAY);
            
        // 悬停提示（包含完整信息）
        MutableComponent hoverText = Component.literal(translation.name() + " (" + key + ")")
            .withStyle(ChatFormatting.GRAY)
            .append("\n\n说明：\n" + translation.description())
            .append("\n\n类型：" + type)
            .append("\n\n当前值：\n" + value)
            .append("\n\n默认值：\n" + defaultValue)
            .append("\n\n真实键：" + key);
            
        // 点击填充指令
        String suggestion = IBaritoneChatControl.FORCE_COMMAND_PREFIX
            + Baritone.settings().prefix.value
            + "set "
            + key;
            
        // 组装最终组件：中文名 [英文键] (类型)
        MutableComponent result = Component.literal(translation.name())
            .withStyle(ChatFormatting.GRAY)
            .append(Component.literal(" [" + key + "]").withStyle(ChatFormatting.WHITE))
            .append(typeText);
            
        result.setStyle(result.getStyle()
            .withHoverEvent(new HoverEvent.ShowText(hoverText))
            .withClickEvent(new ClickEvent.SuggestCommand(suggestion)));
            
        info.setReturnValue(result);
    }
}
