package com.yiyiaddon.feature.identity.command;

import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandContext;
import com.yiyiaddon.command.CommandManager;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.identity.IdentityModule;
import com.yiyiaddon.feature.identity.config.IdentityModuleConfig;
import com.yiyiaddon.model.identity.IdentifyMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ID 模块指令 {@code .id}：模块功能的文本入口。
 *
 * <p><b>用户交互资产（与旧项目逐字一致）：</b>指令名 {@code .id} 无别名；裸 {@code .id} 输出
 * 「识别指令用法」帮助卡片；子命令主名为中文 {@code 物品} / {@code 实体} / {@code 方块}；
 * 全部回执走统一卡片排版。禁止改写这些文本或另造替代指令。</p>
 *
 * <p>额外提供 {@code 统计} 与 {@code 模式} 两个子命令，对应旧项目 GUI 内的统计与模式切换，
 * 属新增入口，不改动旧指令的任何既有行为。</p>
 */
public final class IdentityCommand extends ClientCommand {

    /** 回执前缀：旧项目 {@code IdCommand.MODULE_NAME} 原文 */
    private static final String MODULE_NAME = "ID识别";

    private static final List<String> SUBCOMMANDS = List.of("物品", "实体", "方块", "统计", "模式");

    private final IdentityModule module;

    public IdentityCommand(IdentityModule module) {
        this.module = module;
    }

    @Override
    public String name() {
        return "id";
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public String description() {
        return "识别物品、实体或方块并保存ID（.id 物品 / .id 实体 / .id 方块）";
    }

    @Override
    public String usage() {
        return CommandManager.PREFIX + "id [物品|实体|方块]";
    }

    @Override
    public void execute(CommandContext context) {
        if (context.isEmpty()) {
            showHelp();
            return;
        }
        switch (context.arg(0)) {
            case "物品" -> module.identifyItem();
            case "实体" -> module.identifyEntity();
            case "方块" -> module.identifyBlock();
            case "统计" -> module.reportStats();
            case "模式" -> handleMode(context);
            default -> {
                context.error("未知子命令：" + context.arg(0));
                context.usage(usage());
            }
        }
    }

    @Override
    public List<String> complete(CommandContext context) {
        if (context.isEmpty() || context.size() == 1) return SUBCOMMANDS;
        String action = context.arg(0) == null ? "" : context.arg(0).toLowerCase(Locale.ROOT);
        if (action.equals("模式")) {
            List<String> values = new ArrayList<>(module.modeLabels());
            for (IdentifyMode value : IdentifyMode.values()) values.add(value.name());
            return values;
        }
        return List.of();
    }

    // ── 子命令实现 ──

    /** 裸 {@code .id} 的帮助卡片，文本为旧项目原文 */
    private void showHelp() {
        CommandMessageFormatter.of(MODULE_NAME, "识别指令用法")
                .field("物品", ".id 物品 识别手持物品（主手 → 副手）并保存")
                .field("实体", ".id 实体 识别准星对准的实体并保存")
                .field("方块", ".id 方块 识别准星对准的方块并保存（原版 + 自定义统一采集）")
                .status(CommandMessageFormatter.Level.INFO, "输入 " + usage())
                .send();
    }

    private void handleMode(CommandContext context) {
        if (context.size() < 2) {
            CommandMessageFormatter.of(MODULE_NAME, "识别模式")
                    .field("当前模式", "§f" + module.config().mode().displayName())
                    .field("说明", "§7" + IdentityModuleConfig.describe(module.config().mode()))
                    .field("可选", "§f" + String.join("、", module.modeLabels()))
                    .status(CommandMessageFormatter.Level.INFO, "输入 " + CommandManager.PREFIX + "id 模式 <模式> 切换")
                    .send();
            return;
        }
        IdentifyMode target = parseMode(context.arg(1));
        if (target == null) {
            context.error("未知识别模式：" + context.arg(1) + "（可选： " + String.join("、", module.modeLabels()) + "）");
            return;
        }
        module.setModeIndex(target.ordinal());
    }

    /** 支持中文名、枚举名与序号 */
    private static IdentifyMode parseMode(String token) {
        if (token == null || token.isBlank()) return null;
        String value = token.trim();
        for (IdentifyMode mode : IdentifyMode.values()) {
            if (mode.displayName().equals(value) || mode.name().equalsIgnoreCase(value)) return mode;
        }
        try {
            int index = Integer.parseInt(value);
            IdentifyMode[] values = IdentifyMode.values();
            if (index >= 0 && index < values.length) return values[index];
        } catch (NumberFormatException ignored) {
            // 非序号，继续按包含匹配
        }
        for (IdentifyMode mode : IdentifyMode.values()) {
            if (mode.displayName().contains(value)) return mode;
        }
        return null;
    }
}
