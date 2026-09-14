package com.yiyiaddon.module;

import com.yiyiaddon.command.CommandManager;
import com.yiyiaddon.command.CommandRegistry;
import com.yiyiaddon.command.ResourceCommand;
import com.yiyiaddon.core.event.EventDispatcher;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.identity.IdConfigModule;
import com.yiyiaddon.feature.identity.IdIdentifyModule;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.service.resourcepack.ResourceIndexProbe;

import java.util.List;

/**
 * 模组装配总入口：分类、事件入口、资源探针、功能模块与指令的唯一引导点。
 *
 * <p>引导顺序固定，顺序本身有依赖关系，不可调换：</p>
 * <ol>
 *     <li>分类：模块条目的排序依赖分类权重；</li>
 *     <li>事件入口：先于模块注册，模块启用时订阅的事件立刻可被派发；</li>
 *     <li>资源探针：注册给资源生命周期服务，使解析阶段能真正产出内容；</li>
 *     <li>功能模块：注册元数据、初始化、恢复启用状态、发布模块中心条目；</li>
 *     <li>指令：内置指令与模组自带指令最后注册，此时模块与探针数据都已就绪。</li>
 * </ol>
 *
 * <p>新增功能模块只需在 {@link #createModules()} 里补一条构造，模块中心、模块列表、页面路由、
 * 快捷键与指令都会自动接入。禁止另建第二个引导入口。</p>
 */
public final class AddonModules {

    /** 资源内容探针：同时交给资源生命周期服务与资源指令，必须只有一份实例 */
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
        registerAddonCommands();
    }

    /** 全部功能模块；新增模块只改这里 */
    private static List<Module> createModules() {
        return List.of(
                new IdIdentifyModule(),
                new IdConfigModule()
        );
    }

    /** 注册资源内容探针（未注册时资源解析必然收尾于「未发现目标资源」） */
    private static void registerResourceProbe() {
        ResourceExtractionService.registerProbe(RESOURCE_PROBE);
    }

    /** 不属于任何模块的模组自带指令；模块自带指令由模块运行时统一注册 */
    private static void registerAddonCommands() {
        CommandRegistry.register(new ResourceCommand(RESOURCE_PROBE));
    }

    /**
     * 注册模块分组。
     *
     * <p><b>只注册一个分组：</b>本项目的模块中心只有一个模块分组，全部功能模块都归入其中，不按功能
     * 再划分多个分类。因此这里只有一条注册，模块的 {@code categoryId} 统一使用
     * {@link CategoryRegistry#MODULE_GROUP_ID}。</p>
     *
     * <p>分组不属迁移范围：旧项目的多个分类是旧框架界面的产物，禁止按旧项目增删或改名。</p>
     */
    private static void registerCategories() {
        CategoryRegistry.register(new ModuleCategory(
                CategoryRegistry.MODULE_GROUP_ID, "功能模块", "全部功能模块", "\uEBBC", 10));
    }
}
