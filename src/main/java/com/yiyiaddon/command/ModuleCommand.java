package com.yiyiaddon.command;

import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.module.ModuleCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 内置指令：功能模块的查看与开关。
 *
 * <p>子命令：{@code 列表} 查看、{@code 开} 开启、{@code 关} 关闭、{@code 切换} 切换、
 * {@code 状态} 状态。模块可用中文名或模块 ID 指定。</p>
 *
 * <p>候选与用法一律用中文（用户可见文字除指令前缀与指令名外不出现英文）；英文写法仍可输入，
 * 只是不再作为候选出现。</p>
 */
public final class ModuleCommand extends ClientCommand {

    private static final List<String> SUBCOMMANDS = List.of("列表", "开", "关", "切换", "状态");

    @Override
    public String name() {
        return "module";
    }

    @Override
    public String prefixName() {
        return "模块";
    }

    @Override
    public List<String> aliases() {
        return List.of("m", "模块");
    }

    @Override
    public String description() {
        return "查看模块列表与状态、开启或关闭模块";
    }

    @Override
    public String usage() {
        return CommandManager.prefix() + "module <列表|开|关|切换|状态> [模块]";
    }

    @Override
    public void execute(CommandContext context) {
        if (context.isEmpty()) {
            listModules(null);
            return;
        }
        String action = context.arg(0).toLowerCase(Locale.ROOT);
        switch (action) {
            case "list", "列表" -> listModules(context.arg(1));
            case "on", "开" -> switchModule(context, true);
            case "off", "关" -> switchModule(context, false);
            case "toggle", "切换" -> toggleModule(context);
            case "status", "状态" -> printStatus(context);
            default -> {
                context.error("未知子命令：" + context.arg(0));
                context.usage(usage());
            }
        }
    }

    /**
     * 子命令与参数补全。
     *
     * <p>旧项目这两个参数是 Brigadier 命令树上的两级节点，第二级是叶子：给出模块/分类之后按 Tab
     * 不会再出任何候选。这里按同一口径收口——本指令最多两个参数，第二个已给出即无候选，
     * 否则补完模块名再打一个空格就会继续往外冒模块名，越补越长。</p>
     */
    @Override
    public List<String> complete(CommandContext context) {
        if (context.isEmpty()) return SUBCOMMANDS;
        if (context.size() > 1) return List.of();
        String action = context.arg(0) == null ? "" : context.arg(0).toLowerCase(Locale.ROOT);
        if (isSwitchAction(action)) return moduleNames();
        if (action.equals("列表") || action.equals("list")) return categoryNames();
        return List.of();
    }

    /** 需要跟一个模块名/ID 的子命令（中英文写法都接受，候选只出中文） */
    private static boolean isSwitchAction(String action) {
        return switch (action) {
            case "开", "on", "关", "off", "切换", "toggle", "状态", "status" -> true;
            default -> false;
        };
    }

    // ── 子命令实现 ──

    private void listModules(String categoryToken) {
        List<Module> modules = ModuleManager.all();
        if (modules.isEmpty()) {
            ClientChat.send(prefixName(), "§7当前没有注册任何功能模块");
            return;
        }
        String categoryFilter = resolveCategoryId(categoryToken);
        if (categoryToken != null && !categoryToken.isBlank() && categoryFilter == null) {
            ClientChat.send(prefixName(), "§c未找到分类：" + categoryToken);
            return;
        }

        CommandMessageFormatter formatter = CommandMessageFormatter.of(prefixName(), "功能模块");
        int matched = 0;
        for (Module module : modules) {
            if (categoryFilter != null && !categoryFilter.equals(module.categoryId())) continue;
            matched++;
            ModuleCategory category = CategoryRegistry.byId(module.categoryId());
            String categoryName = category == null ? "未分类" : category.displayName();
            formatter.field("[" + categoryName + "] " + module.displayName() + " (" + module.id() + ")",
                    module.isEnabled() ? "§a已启用" : "§7未启用");
        }
        if (matched == 0) {
            ClientChat.send(prefixName(), "§7该分类下没有模块");
            return;
        }
        formatter.status(CommandMessageFormatter.Level.INFO,
                "共 " + modules.size() + " 个，启用 " + ModuleManager.enabledIds().size() + " 个").send();
    }

    private void switchModule(CommandContext context, boolean enabled) {
        Module module = requireModule(context);
        if (module == null) return;
        if (ModuleManager.setEnabled(module.id(), enabled)) return;
        if (module.isEnabled() == enabled) return;
        context.error("模块 " + module.displayName() + " 操作未生效");
    }

    private void toggleModule(CommandContext context) {
        Module module = requireModule(context);
        if (module == null) return;
        ModuleManager.toggle(module.id());
    }

    private void printStatus(CommandContext context) {
        Module module = requireModule(context);
        if (module == null) return;
        ModuleCategory category = CategoryRegistry.byId(module.categoryId());
        List<String> problems = ModuleManager.problemsOf(module);
        CommandMessageFormatter formatter = CommandMessageFormatter.of(prefixName(),
                        module.displayName() + " (" + module.id() + ")")
                .field("分类", category == null ? "未分类" : category.displayName())
                .field("版本", module.version())
                .field("状态", ModuleManager.statusText(module))
                .field("自检", problems.isEmpty() ? "通过" : String.join("；", problems));
        formatter.status(problems.isEmpty() ? CommandMessageFormatter.Level.SUCCESS
                        : CommandMessageFormatter.Level.FAILURE,
                problems.isEmpty() ? "可用" : "未通过，条件满足后会自动开启").send();
    }

    // ── 解析 ──

    private Module requireModule(CommandContext context) {
        String token = context.arg(1);
        if (token == null || token.isBlank()) {
            context.error("缺少模块参数");
            context.usage(usage());
            return null;
        }
        Module module = find(token);
        if (module == null) {
            context.error("未找到模块：" + token + "（可用： " + String.join("、", moduleNames()) + "）");
        }
        return module;
    }

    private static Module find(String token) {
        Module byId = ModuleManager.byId(token);
        if (byId != null) return byId;
        String needle = token.trim().toLowerCase(Locale.ROOT);
        for (Module module : ModuleManager.all()) {
            if (module.displayName().equalsIgnoreCase(needle) || module.name().equalsIgnoreCase(needle)) {
                return module;
            }
        }
        for (Module module : ModuleManager.all()) {
            if (module.displayName().toLowerCase(Locale.ROOT).contains(needle)) return module;
        }
        return null;
    }

    /** 全部模块的中文名（候选与提示都用它；ID 仍可输入，只是不出现在候选里） */
    private static List<String> moduleNames() {
        List<String> names = new ArrayList<>();
        for (Module module : ModuleManager.all()) names.add(module.displayName());
        return names;
    }

    /** 全部模块分类的中文名 + 全部 */
    private static List<String> categoryNames() {
        List<String> names = new ArrayList<>();
        for (ModuleCategory category : CategoryRegistry.all()) names.add(category.displayName());
        names.add("全部");
        return names;
    }

    private static String resolveCategoryId(String token) {
        if (token == null || token.isBlank() || token.equalsIgnoreCase("全部") || token.equals("all")) return null;
        ModuleCategory byId = CategoryRegistry.byId(token);
        if (byId != null) return byId.id();
        for (ModuleCategory category : CategoryRegistry.all()) {
            if (category.displayName().equalsIgnoreCase(token.trim())) return category.id();
        }
        return null;
    }
}
