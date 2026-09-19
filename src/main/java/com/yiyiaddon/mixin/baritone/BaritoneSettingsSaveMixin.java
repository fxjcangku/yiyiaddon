package com.yiyiaddon.mixin.baritone;

import baritone.api.Settings;
import baritone.api.utils.SettingsUtil;
import com.yiyiaddon.integration.baritone.BaritoneRenderTakeover;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * 接管期间不让 Baritone 把「被压掉的渲染开关」写进 {@code run/baritone/settings.txt}。
 *
 * <p><b>为什么必须拦</b>：{@link SettingsUtil#save} 写盘的判据是「值 != 默认值的设置」
 * （{@link SettingsUtil#modifiedSettings}）。接管期间本项目把 {@code renderPath} 等五项强制改成
 * {@code false}，与默认值 {@code true} 不同，于是<b>任何一次</b>写盘都会把它们一起落到文件里 ——
 * 触发路径至少有两条：Baritone 的 {@code #set} 命令（{@code SetCommand} 改完就
 * {@code SettingsUtil.save}，改任何一项都会写全量）、以及本项目 Baritone 设置页的任意一次改动
 * （{@code BaritoneSettingsPage#write} 同样每次都 save）。一旦落盘，关掉接管、甚至卸掉本模组，
 * Baritone 的渲染也不会再出来，用户只能手动改回。</p>
 *
 * <p><b>为什么拦在 {@code modifiedSettings} 而不是 {@code save}</b>：{@code save} 的值只从这一处取，
 * 而本方法的语义恰好就是「哪些设置算被用户改过」——把接管项从结果里剔除是最小、最难漏的切点，
 * 不用去包 {@code save} 的方法体做「临时还原再压回」。副作用仅有三处，且都是改进：
 * {@code #set reset} 不会去 reset 这几项（本来也会被 tick 立刻压回）、{@code #set modified} 列表里
 * 不出现这几项（它们不是用户改的）、以及 {@code #set reset} 的补全少几项。</p>
 *
 * <p><b>找不到方法时静默跳过</b>（本配置文件 {@code defaultRequire: 0}）：Baritone 换实现只会让这道
 * 闸门失效、不会崩客户端；发现失效时按上面的落盘判据补回来即可。</p>
 */
@Mixin(value = SettingsUtil.class, remap = false)
public abstract class BaritoneSettingsSaveMixin {

    @Inject(method = "modifiedSettings", at = @At("RETURN"), cancellable = true)
    private static void yiyiaddon$skipTakeoverSettings(Settings settings,
                                                       CallbackInfoReturnable<List<Settings.Setting<?>>> info) {
        if (!BaritoneRenderTakeover.active()) return;
        List<Settings.Setting<?>> modified = info.getReturnValue();
        if (modified == null || modified.isEmpty()) return;
        modified.removeIf(setting -> BaritoneRenderTakeover.isSuppressed(setting.getName()));
    }
}
