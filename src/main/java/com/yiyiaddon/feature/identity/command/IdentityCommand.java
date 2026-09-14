package com.yiyiaddon.feature.identity.command;

import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandContext;
import com.yiyiaddon.command.CommandManager;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.identity.IdIdentifyModule;

import java.util.List;

/**
 * ID 模块指令 {@code .id}：模块功能的文本入口。
 *
 * <p><b>用户交互资产（与旧项目逐字一致）：</b>指令名 {@code .id} 无别名；裸 {@code .id} 输出
 * 「识别指令用法」帮助卡片；子命令主名为中文 {@code 物品} / {@code 实体} / {@code 方块}；
 * 全部回执走统一卡片排版。禁止改写这些文本或另造替代指令。</p>
 */
public final class IdentityCommand extends ClientCommand {

    /** 回执前缀：旧项目 {@code IdCommand.MODULE_NAME} 原文 */
    private static final String MODULE_NAME = IdIdentifyModule.MESSAGE_MODULE;

    private static final List<String> SUBCOMMANDS = List.of("物品", "实体", "方块");

    private final IdIdentifyModule module;

    public IdentityCommand(IdIdentifyModule module) {
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
        return CommandManager.prefix() + "id [物品|实体|方块]";
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
            default -> {
                context.error("未知子命令：" + context.arg(0));
                context.usage(usage());
            }
        }
    }

    @Override
    public List<String> complete(CommandContext context) {
        if (context.isEmpty() || context.size() == 1) return SUBCOMMANDS;
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
}
