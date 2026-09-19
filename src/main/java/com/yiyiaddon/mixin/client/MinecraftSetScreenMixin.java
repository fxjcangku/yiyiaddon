package com.yiyiaddon.mixin.client;

import com.yiyiaddon.core.event.EventDispatcher;
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
 */
@Mixin(Gui.class)
public abstract class MinecraftSetScreenMixin {

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void yiyiaddon$silentContainerBeforeOpen(Screen screen, CallbackInfo ci) {
        if (screen == null) return; // 关界面（含内部收尾）一律放行
        if (EventDispatcher.onScreenOpen(screen)) ci.cancel();
    }
}
