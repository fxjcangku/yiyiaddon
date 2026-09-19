package com.yiyiaddon.mixin.client;

import com.yiyiaddon.core.event.EventDispatcher;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 原版内部收尾不许动玩家自己开的界面（用户 2026-09-19：「传送还是会关掉我这个界面」）。
 *
 * <p>两处无条件 {@code setScreen(null)} 会在传送/过门时把玩家的游戏菜单拍掉：
 * <ul>
 *   <li>{@code LocalPlayer#clientSideCloseContainer()}：重生式传送时
 *       {@code ClientPacketListener#handleRespawn} 先调 {@code oldPlayer.closeContainer()}
 *       （只要那一刻有任何容器开着，包括我方静默容器），收尾里就顺手关了界面；</li>
 *   <li>{@code LocalPlayer#handlePortalTransitionEffect()}：进传送门时关掉「不允许出现在传送门里」的界面。</li>
 * </ul>
 * 两处都只是原版自己的清理，交给 {@link EventDispatcher#closeScreenUnlessPlayerOwned} 判断：
 * 目标本来就是「关界面」时，若当前是玩家自己开的非容器界面（游戏菜单等）就保留，
 * 其余一律照原样执行。玩家按 ESC 关菜单走 {@code Screen#onClose}，不经过这两处，不会被吞。</p>
 *
 * <p><b>26.2 口径</b>：这两处原版调的都是 {@code Gui#setScreen}（不再是
 * {@code Minecraft#setScreen}），因此重定向的接收者由 {@code Minecraft} 换成 {@code Gui}。
 * 语义与改动前逐字一致。</p>
 */
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerScreenGuardMixin {

    @Redirect(method = "clientSideCloseContainer",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"))
    private void yiyiaddon$keepPlayerScreenOnContainerClose(Gui gui, Screen screen) {
        EventDispatcher.closeScreenUnlessPlayerOwned(gui, screen);
    }

    @Redirect(method = "handlePortalTransitionEffect",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"))
    private void yiyiaddon$keepPlayerScreenInPortal(Gui gui, Screen screen) {
        EventDispatcher.closeScreenUnlessPlayerOwned(gui, screen);
    }
}
