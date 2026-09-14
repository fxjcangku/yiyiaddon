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
 * <p>子命令：{@code list} 查看、{@code on} 开启、{@code off} 关闭、{@code toggle} 切换、
 * {@code status} 状态。模块可用模块 ID 或中文名指定。</p>
 */
public final class ModuleCommand extends ClientCommand {

    private static final List<String> SUBCOMMANDS = List.of("list", "on", "off", "toggle", "status");

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
        return CommandManager.prefix() + "module <list|on|off|toggle|status> [模块]";
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

    @Override
    public List<String> complete(CommandContext context) {
        if (context.isEmpty()) return SUBCOMMANDS;
        String action = context.arg(0) == null ? "" : context.arg(0).toLowerCase(Locale.ROOT);
        if (context.size() == 1) return SUBCOMMANDS;
        if (action.equals("on") || action.equals("off") || action.equals("toggle")
                || action.equals("status") || action.equals("开") || action.equals("关")
                || action.equals("切换") || action.equals("状态")) {
            return moduleTokens();
        }
        if (action.equals("list") || action.equals("列表")) {
            List<String> categories = new ArrayList<>();
            for (ModuleCategory category : CategoryRegistry.all()) {
                categories.add(category.id());
                categories.add(category.displayName());
            }
            categories.add("全部");
            return categories;
        }
        return List.of();
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
            context.error("未找到模块：" + token + "（可用： " + String.join("、", moduleIds()) + "）");
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

    private static List<String> moduleIds() {
        List<String> ids = new ArrayList<>();
        for (Module module : ModuleManager.all()) ids.add(module.id());
        return ids;
    }

    private static List<String> moduleTokens() {
        List<String> tokens = new ArrayList<>();
        for (Module module : ModuleManager.all()) {
            tokens.add(module.id());
            tokens.add(module.displayName());
        }
        return tokens;
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
