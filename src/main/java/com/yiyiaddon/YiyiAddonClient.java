package com.yiyiaddon;

import com.yiyiaddon.config.AddonConfig;
import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.stardew.season.StardewSeasonService;
import com.yiyiaddon.feature.tactical.core.TacticalCoordinator;
import com.yiyiaddon.integration.baritone.BaritoneOverlay;
import com.yiyiaddon.module.AddonModules;
import com.yiyiaddon.service.HeartbeatService;
import com.yiyiaddon.service.HomeStats;
import com.yiyiaddon.service.RegisterService;
import com.yiyiaddon.service.RemoteConfigService;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.ui.keybind.FunctionKeybinds;
import com.yiyiaddon.ui.keybind.ModuleKeybindManager;
import com.yiyiaddon.ui.render.world.BlockOutlineRenderer;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import com.yiyiaddon.ui.theme.ClickGuiThemeManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/**
 * yiyiaddon 客户端入口：界面主题恢复、快捷键轮询，以及基础服务的挂载。
 */
public final class YiyiAddonClient implements ClientModInitializer {

    /** 统计链路的订阅所有者。 */
    private static final String STATS_OWNER = "service.stats";

    @Override
    public void onInitializeClient() {
        AddonConfig.load();
        ClickGuiThemeManager.applyConfig();
        AddonModules.bootstrap();
        // 战术协调器：四个战术模块共用的常驻决策中枢（旧项目静态自挂总线，
        // 本项目以固定所有者订阅核心事件，生命周期同样不依赖任何模块开关）
        TacticalCoordinator.init();
        ModuleKeybindManager.initialize();
        ClientTickEvents.END_CLIENT_TICK.register(ModuleKeybindManager::tick);
        // 常驻功能键通道：模块的功能键（非开关绑定）在模块关闭时也必须可检测——
        // 旧项目靠常驻事件总线监听器实现，本项目挂在同一个 tick 上（不新增 Fabric 注册点）
        ClientTickEvents.END_CLIENT_TICK.register(FunctionKeybinds::tick);

        // 身份识别体系：选择配置 + 身份数据（一次性载入，失败不阻断启动）
        IdentityTargetConfig.load();
        IdentityService.shared().load();

        // 资源生命周期服务：进服识别、断线失效、每 tick 推进状态机与下载兜底
        ResourceExtractionService.init();

        // 统计链路：远程开关 → 心跳 → 注册（进服上报身份并回填首页排名）
        RemoteConfigService.start();
        HeartbeatService.start();
        HomeStats.start();
        ClientEventBus.subscribe(STATS_OWNER, ClientEventType.JOIN_SERVER, event -> RegisterService.register());
        ClientEventBus.subscribe(STATS_OWNER, ClientEventType.DISCONNECT, event -> {
            HeartbeatService.reportOffline();
            RegisterService.reset();
        });

        // 星露谷季节识别：常驻挂载（不依赖模块开关），必须排在资源服务之后——
        // 它要订阅资源的就绪 / 失效事件，且模块启用前收到的服务器季节组件也不能丢
        // （旧项目 AddonTemplate 里两者同样是先后相邻的两次 init）
        StardewSeasonService.init();

        // 瞄准方块高亮：全局常驻渲染层（不随任何模块开关，开关在「ESP 全局设置 ▸ 外观」里，默认开）
        WorldOverlay.register(BlockOutlineRenderer.LAYER_ID, BlockOutlineRenderer::render);

        // Baritone 渲染接管：路径 / 目标 / 挖掘框 / 选区四类改由本项目自绘，同样常驻，
        // 所有用 Baritone 寻路的模块（挖矿 / 附魔 / 农场 / 村民 / 箱子…）共用一层。
        // 接管同步放在 tick 上而不是这里：Baritone 的静态 Settings 由它自己的模组初始化建立，
        // 本入口执行时可能还没就绪（拿到 null），只在入口调一次会永久失手（见 BaritoneOverlay#syncTakeover）
        WorldOverlay.register(BaritoneOverlay.LAYER_ID, BaritoneOverlay::render);
        ClientTickEvents.END_CLIENT_TICK.register(client -> BaritoneOverlay.syncTakeover());
    }
}
