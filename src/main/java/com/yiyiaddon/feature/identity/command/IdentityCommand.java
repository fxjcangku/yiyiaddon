package com.yiyiaddon.feature.identity.command;

import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandContext;
import com.yiyiaddon.command.CommandManager;
import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.feature.identity.IdentityModule;
import com.yiyiaddon.feature.identity.config.IdentityModuleConfig;
import com.yiyiaddon.model.identity.IdentifyMode;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.service.identity.IdentityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ID 模块指令 {@code .id}：模块功能的文本入口。
 *
 * <p>子命令：无参按当前识别模式识别、{@code item|block|entity} 指定识别对象、{@code stat} 查看身份库
 * 统计、{@code mode} 查看或切换识别模式。</p>
 */
public final class IdentityCommand extends ClientCommand {

    private static final List<String> SUBCOMMANDS = List.of("item", "block", "entity", "stat", "mode");

    private final IdentityModule module;

    public IdentityCommand(IdentityModule module) {
        this.module = module;
    }

    @Override
    public String name() {
        return "id";
    }

    @Override
    public List<String> aliases() {
        return List.of("identify", "识别", "身份");
    }

    @Override
    public String description() {
        return "识别物品、方块、实体并管理身份库";
    }

    @Override
    public String usage() {
        return CommandManager.PREFIX + "id <item|block|entity|stat|mode> [模式]";
    }

    @Override
    public void execute(CommandContext context) {
        if (context.isEmpty()) {
            module.identifyByMode();
            return;
        }
        String action = context.arg(0).toLowerCase(Locale.ROOT);
        switch (action) {
            case "item", "物品" -> module.identifyItem();
            case "block", "方块" -> module.identifyBlock();
            case "entity", "实体" -> module.identifyEntity();
            case "stat", "统计" -> printStat();
            case "mode", "模式" -> handleMode(context);
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
        if (action.equals("mode") || action.equals("模式")) {
            List<String> values = new ArrayList<>(module.modeLabels());
            for (IdentifyMode value : IdentifyMode.values()) values.add(value.name());
            return values;
        }
        return List.of();
    }

    // ── 子命令实现 ──

    private void printStat() {
        IdentityService service = IdentityService.shared();
        ClientChat.send("§b身份库统计");
        ClientChat.send("§7物品 " + service.itemCount() + " 项 ｜ 实体 " + service.entityCount()
                + " 项 ｜ 方块 " + service.blockCount() + " 项");
        ClientChat.send("§7物品快照 " + service.itemSnapshotCount() + " 项 ｜ 方块快照 "
                + service.blockSnapshotCount() + " 项");
        ClientChat.send("§7已选目标：" + IdentityTargetConfig.countText(service));
        ClientChat.send("§7数据目录：" + GamePaths.identityRoot());
    }

    private void handleMode(CommandContext context) {
        if (context.size() < 2) {
            ClientChat.send("§b当前识别模式：" + module.config().mode().displayName()
                    + "（" + IdentityModuleConfig.describe(module.config().mode()) + "）");
            ClientChat.send("§7可选：" + String.join("、", module.modeLabels()));
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
