package com.yiyiaddon.command;

import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.model.resource.ResourceAnalysisResult;
import com.yiyiaddon.model.resource.ResourceParseState;
import com.yiyiaddon.repository.resource.ResourceAnalysisCache;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.service.resourcepack.ResourceIndexProbe;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 资源指令 {@code .resource}：资源链路的唯一文本入口。
 *
 * <p>子命令分工：{@code check} 走完整生命周期（检查缓存 / 下载 / 等待生效 / 解析，多人服务器专用，
 * 对应旧项目界面上的「检测 / 提取当前服务器资源包」）；{@code analyze} 直接对当前已加载资源跑一次
 * 探针分析，不依赖服务器资源包，单人世界也能用；{@code status} 只读展示生命周期状态与最近一次
 * 分析结果；{@code cache} 展示分析缓存统计。</p>
 *
 * <p>回执前缀为旧项目原文 {@code 服务器核心}，全部输出走统一卡片排版；只输出状态本身，
 * 不输出资源包下载地址等敏感信息。</p>
 */
public final class ResourceCommand extends ClientCommand {

    /** 回执前缀：旧项目 {@code ServerResourceService} 的模块名原文 */
    private static final String MODULE_NAME = "服务器核心";

    private static final List<String> SUBCOMMANDS = List.of("check", "analyze", "status", "cache");

    private final ResourceIndexProbe probe;

    public ResourceCommand(ResourceIndexProbe probe) {
        this.probe = probe;
    }

    @Override
    public String name() {
        return "resource";
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public List<String> aliases() {
        return List.of("res", "资源");
    }

    @Override
    public String description() {
        return "检测 / 提取当前服务器资源包，并查看资源解析状态";
    }

    @Override
    public String usage() {
        return CommandManager.prefix() + "resource <check|analyze|status|cache>";
    }

    @Override
    public void execute(CommandContext context) {
        if (context.isEmpty()) {
            printStatus();
            return;
        }
        String action = context.arg(0).toLowerCase(Locale.ROOT);
        switch (action) {
            case "check", "检测" -> check();
            case "analyze", "analyse", "解析", "分析" -> analyze();
            case "status", "状态" -> printStatus();
            case "cache", "缓存" -> ClientChat.send(MODULE_NAME, "§7" + ResourceAnalysisCache.describe());
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

    private void check() {
        if (ResourceExtractionService.isBusy()) {
            ClientChat.send(MODULE_NAME, "§e资源正在处理中（" + ResourceExtractionService.statusLabel() + "），请稍候…");
            return;
        }
        ResourceExtractionService.requestExtract();
    }

    private void analyze() {
        printAnalysis(probe.analyzeDetailed(), "资源解析");
    }

    private void printStatus() {
        String serverKey = ResourceExtractionService.serverKey();
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "资源状态")
                .field("生命周期", ResourceExtractionService.statusLabel())
                .field("来源", ResourceExtractionService.resourceSource().label())
                .field("缓存文件", ResourceExtractionService.cacheLabel())
                .field("服务器", serverKey == null || serverKey.isBlank() ? "未识别" : serverKey)
                .field("指纹", ResourceExtractionService.fingerprintLabel());
        String failReason = ResourceExtractionService.failReason();
        if (failReason != null && !failReason.isBlank()) {
            formatter.field("失败原因", "§c" + failReason);
        }

        ResourceAnalysisResult result = probe.lastResult();
        if (result == null) {
            formatter.field("最近解析", "§7尚未解析资源（" + CommandManager.prefix() + "resource analyze 可直接解析）");
            formatter.status(CommandMessageFormatter.Level.INFO, ResourceExtractionService.statusLabel()).send();
            return;
        }
        formatter.field("最近解析", "§f" + result.state().label()
                + (result.fromCache() ? "（命中缓存）" : "（完整分析）"));
        formatter.status(result.state() == ResourceParseState.FAILED
                        ? CommandMessageFormatter.Level.FAILURE
                        : CommandMessageFormatter.Level.INFO,
                result.state().label()).send();
        printAnalysis(result, null);
    }

    /** 输出一份分析结果卡片；不输出任何下载地址 */
    private void printAnalysis(ResourceAnalysisResult result, String title) {
        if (result.state() == ResourceParseState.FAILED) {
            CommandMessageFormatter.of(MODULE_NAME, title == null ? "资源解析" : title)
                    .field("原因", "§c" + result.reason())
                    .status(CommandMessageFormatter.Level.FAILURE, "解析失败")
                    .send();
            return;
        }

        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, title == null ? "资源解析" : title);
        if (title != null) {
            formatter.field("解析方式", result.fromCache() ? "§f命中缓存" : "§f完整分析");
        }
        formatter.field("指纹", result.fingerprint() == null ? "未建立" : result.fingerprint())
                .field("资源数量", String.valueOf(result.total()))
                .field("未知资源", String.valueOf(result.unknownCount()))
                .field("资源分类", countsText(result))
                .field("目标命名空间", textOf(result.targetNamespaces()))
                .field("忽略命名空间", textOf(result.ignoredNamespaces()));
        if (!result.contentFull()) {
            formatter.field("注意", "§6本次指纹未覆盖全部定义类资源（部分文件超限或读取失败）");
        }
        if (result.state() == ResourceParseState.EMPTY) {
            formatter.field("说明", "§6当前已加载资源中没有目标命名空间的内容（原版与模组命名空间不计入）");
        }
        formatter.status(result.state() == ResourceParseState.EMPTY
                        ? CommandMessageFormatter.Level.WARNING
                        : CommandMessageFormatter.Level.SUCCESS,
                result.state().label()).send();
    }

    private static String countsText(ResourceAnalysisResult result) {
        Map<String, Integer> counts = result.nonZeroCounts();
        if (counts.isEmpty()) return "无";
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (builder.length() > 0) builder.append(" / ");
            builder.append(entry.getKey()).append(' ').append(entry.getValue());
        }
        return builder.toString();
    }

    private static String textOf(List<String> values) {
        return values == null || values.isEmpty() ? "无" : String.join("、", values);
    }
}
