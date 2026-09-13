package com.yiyiaddon;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/**
 * yiyiaddon 客户端入口：界面主题恢复与快捷键轮询。
 */
public final class YiyiAddonClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AddonConfig.load();
        ClickGuiThemeManager.applyConfig();
        ModuleKeybindManager.initialize();
        ClientTickEvents.END_CLIENT_TICK.register(ModuleKeybindManager::tick);
    }
}
