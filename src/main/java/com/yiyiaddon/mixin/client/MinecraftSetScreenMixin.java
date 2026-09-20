package com.yiyiaddon.mixin.client;

import com.yiyiaddon.core.event.EventDispatcher;
import com.yiyiaddon.platform.container.SilentContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 把 {@code SCREEN_OPEN} 事件提前到「界面被设置之前」，让「静默容器」真正静默。
 *
 * <p>用户 2026-09-19：「开潜影盒子会抢我鼠标，之前我记得不会的呀，卸货的时候我挂机都没事」。</p>
 *
 * <p>26.1.2 的切换界面入口在 {@code Minecraft#setScreen}；26.2 把它移进了 {@code Gui}
 * （{@code Minecraft.setScreen} 已移除，新增的 {@code Minecraft#setScreenAndShow} 最终也走
 * {@code Gui#setScreen}），因此注入点改挂 {@code Gui#setScreen}，语义与改动前完全一致。</p>
 *
 * <p>执行顺序是
 * {@code this.screen = screen → mouseHandler.releaseMouse() → screen.init(...)}，
 * 而 {@code ScreenEvents.AFTER_INIT} 在最后那步之后才回调 —— 那时界面已经建好、鼠标已经交还系统，
 * 取消只能靠再调一次 {@code setScreen(null)}：表现为「鼠标被抢一下」，
 * 且那次嵌套调用在 {@code level != null} 时会把聊天界面恢复出来。
 * 挂在头部则可以直接取消整个调用：界面根本不建、鼠标状态一动不动，
 * 与旧项目 {@code OpenScreenEvent} 的时机一致（{@link EventDispatcher#onScreenOpen} 里
 * {@code mc.player.containerMenu} 仍照常同步，后台物流发包不受影响）。</p>
 *
 * <p>同一处顺手管「系统过渡界面顶掉玩家界面」这件事（用户 2026-09-22：「还是关闭了我的 esc 返回
 * 那个键」）：跨服「重新配置」那个界面拦不得（拦了连接冻住），只能在被顶掉时记账、收场时归还 ——
 * 见 {@link SilentContainer#stashPlayerScreenBefore} / {@link SilentContainer#reclaimPlayerScreen}。</p>
 */
@Mixin(Gui.class)
public abstract class MinecraftSetScreenMixin {

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$silentContainerBeforeOpen(Screen screen, CallbackInfo ci) {
        Gui self = (Gui) (Object) this;
        Minecraft client = Minecraft.getInstance();
        if (screen == null) {
            // 关界面（含内部收尾）一律放行，但若这次关屏是「过渡结束」就把被顶掉的玩家界面还回去
            Screen restore = SilentContainer.reclaimPlayerScreen(client);
            if (restore != null) {
                ci.cancel();
                self.setScreen(restore);
            }
            return;
        }
        if (EventDispatcher.onScreenOpen(screen)) {
            ci.cancel();
            return;
        }
        // 界面真的要被换上去了：若是系统过渡界面顶掉玩家自己的界面，先记一笔（拦不得，只能还）
        SilentContainer.stashPlayerScreenBefore(screen, client);
    }
}
