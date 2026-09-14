package com.yiyiaddon;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.module.AddonModules;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/**
 * yiyiaddon 客户端入口：界面主题恢复、快捷键轮询，以及基础服务的挂载。
 */
public final class YiyiAddonClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AddonConfig.load();
        ClickGuiThemeManager.applyConfig();
        AddonModules.bootstrap();
        ModuleKeybindManager.initialize();
        ClientTickEvents.END_CLIENT_TICK.register(ModuleKeybindManager::tick);

        // 身份识别体系：选择配置 + 身份数据（一次性载入，失败不阻断启动）
        IdentityTargetConfig.load();
        IdentityService.shared().load();

        // 资源生命周期服务：进服识别、断线失效、每 tick 推进状态机与下载兜底
        ResourceExtractionService.init();
    }
}
