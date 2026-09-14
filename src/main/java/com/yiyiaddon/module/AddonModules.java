package com.yiyiaddon.module;

import com.yiyiaddon.command.CommandManager;
import com.yiyiaddon.core.event.EventDispatcher;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.feature.identity.IdConfigModule;
import com.yiyiaddon.feature.identity.IdIdentifyModule;
import com.yiyiaddon.feature.visuals.EspTestModule;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.service.resourcepack.ResourceIndexProbe;

import java.util.List;

/**
 * 模组装配总入口：分类、事件入口、资源探针与功能模块的唯一引导点。
 *
 * <p>引导顺序固定，顺序本身有依赖关系，不可调换：</p>
 * <ol>
 *     <li>分类：模块条目的排序依赖分类权重；</li>
 *     <li>事件入口：先于模块注册，模块启用时订阅的事件立刻可被派发；</li>
 *     <li>资源探针：注册给资源生命周期服务，使解析阶段能真正产出内容；</li>
 *     <li>功能模块：注册元数据、初始化、恢复启用状态、发布模块中心条目；</li>
 *     <li>指令：内置指令最后注册（新增模组自带指令时在此之后追加）。</li>
 * </ol>
 *
 * <p>新增功能模块只需在 {@link #createModules()} 里补一条构造，模块中心、模块列表、页面路由、
 * 快捷键与指令都会自动接入。禁止另建第二个引导入口。</p>
 */
public final class AddonModules {

    /** 资源内容探针：交给资源生命周期服务，必须只有一份实例 */
    private static final ResourceIndexProbe RESOURCE_PROBE = new ResourceIndexProbe();

    private static boolean bootstrapped;

    private AddonModules() {
    }

    /** 装配全部内置内容；重复调用无效 */
    public static synchronized void bootstrap() {
        if (bootstrapped) return;
        bootstrapped = true;

        registerCategories();
        EventDispatcher.init();
        registerResourceProbe();
        ModuleManager.bootstrap(createModules());
        CommandManager.bootstrap();
    }

    /** 全部功能模块；新增模块只改这里 */
    private static List<Module> createModules() {
        return List.of(
                new IdIdentifyModule(),
                new IdConfigModule(),
                new AutoChestModule(),
                new EspTestModule()
        );
    }

    /** 注册资源内容探针（未注册时资源解析必然收尾于「未发现目标资源」） */
    private static void registerResourceProbe() {
        ResourceExtractionService.registerProbe(RESOURCE_PROBE);
    }

    /**
     * 注册模块分类（模块分组）。
     *
     * <p><b>分类名与顺序按旧项目复刻</b>：旧项目 5 个分类的中文名与注册顺序为
     * 工具 → 自动化 → 绕过 → 辅助 → 星露谷（{@code core/AddonTemplate.java:84-95,242-249}）。
     * 按用户指示（2026-09-14），分类显示名去掉旧项目的 {@code §c§lyiyiaddon } 前缀段，
     * 只保留分类名本身；顺序不变，不得自行增删分类。</p>
     */
    private static void registerCategories() {
        CategoryRegistry.register(new ModuleCategory("tools", "工具", "管理工具类功能", "\uF06A", 10));
        CategoryRegistry.register(new ModuleCategory("automation", "自动化", "管理自动执行类功能", "\uEBBC", 20));
        CategoryRegistry.register(new ModuleCategory("tactical", "绕过", "管理绕过类功能", "\uE659", 30));
        CategoryRegistry.register(new ModuleCategory("assist", "辅助", "管理辅助工具功能", "\uEF76", 40));
        CategoryRegistry.register(new ModuleCategory("stardew", "星露谷", "管理农场模拟功能", "\uE8CD", 50));
    }
}
