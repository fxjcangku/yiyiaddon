package com.yiyiaddon.command;

import com.yiyiaddon.core.ClientChat;
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
 * <p>子命令分工：{@code check} 走完整生命周期（检查缓存 / 下载 / 等待生效 / 解析，仅多人服务器可用）；
 * {@code analyze} 直接对当前已加载资源跑一次探针分析，不依赖服务器资源包，单人世界也能用；
 * {@code status} 只读展示生命周期状态与最近一次分析结果；{@code cache} 展示分析缓存统计。</p>
 *
 * <p>全部输出中文，且只输出状态本身，不输出资源包下载地址等敏感信息。</p>
 */
public final class ResourceCommand extends ClientCommand {

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
    public List<String> aliases() {
        return List.of("res", "资源");
    }

    @Override
    public String description() {
        return "检测服务器资源包、解析当前已加载资源并查看资源状态";
    }

    @Override
    public String usage() {
        return CommandManager.PREFIX + "resource <check|analyze|status|cache>";
    }

    @Override
    public void execute(CommandContext context) {
        if (context.isEmpty()) {
            printStatus();
            return;
        }
        String action = context.arg(0).toLowerCase(Locale.ROOT);
        switch (action) {
            case "check", "检测" -> check(context);
            case "analyze", "analyse", "解析", "分析" -> analyze();
            case "status", "状态" -> printStatus();
            case "cache", "缓存" -> ClientChat.send(ResourceAnalysisCache.describe());
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

    private void check(CommandContext context) {
        if (ResourceExtractionService.isBusy()) {
            context.error("资源正在处理中：" + ResourceExtractionService.statusLabel());
            return;
        }
        ResourceExtractionService.requestExtract();
    }

    private void analyze() {
        ResourceAnalysisResult result = probe.analyzeDetailed();
        ClientChat.send("§b资源解析" + (result.fromCache() ? "（命中缓存）" : "（完整分析）"));
        printAnalysis(result);
    }

    private void printStatus() {
        ClientChat.send("§b资源状态");
        ClientChat.send("§7生命周期：" + ResourceExtractionService.statusLabel()
            + " §8| §7来源：" + ResourceExtractionService.resourceSource().label()
            + " §8| §7缓存文件：" + ResourceExtractionService.cacheLabel());
        String serverKey = ResourceExtractionService.serverKey();
        ClientChat.send("§7服务器：" + (serverKey == null || serverKey.isBlank() ? "未识别" : serverKey)
            + " §8| §7指纹：" + ResourceExtractionService.fingerprintLabel());
        String failReason = ResourceExtractionService.failReason();
        if (failReason != null && !failReason.isBlank()) {
            ClientChat.send("§c失败原因：" + failReason);
        }

        ResourceAnalysisResult result = probe.lastResult();
        if (result == null) {
            ClientChat.send("§7尚未解析资源（可输入 " + CommandManager.PREFIX + "resource analyze 直接解析当前资源）");
            return;
        }
        ClientChat.send("§7最近解析：" + result.state().label()
            + (result.fromCache() ? "（命中缓存）" : "（完整分析）"));
        printAnalysis(result);
    }

    /** 输出一份分析结果；不输出任何下载地址 */
    private void printAnalysis(ResourceAnalysisResult result) {
        if (result.state() == ResourceParseState.FAILED) {
            ClientChat.send("§c解析失败：" + result.reason());
            return;
        }
        ClientChat.send("§7指纹：" + (result.fingerprint() == null ? "未建立" : result.fingerprint())
            + " §8| §7资源数量：" + result.total()
            + " §8| §7未知资源：" + result.unknownCount());

        Map<String, Integer> counts = result.nonZeroCounts();
        if (counts.isEmpty()) {
            ClientChat.send("§7资源分类：无");
        } else {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                if (builder.length() > 0) builder.append("§8 / §7");
                builder.append(entry.getKey()).append(' ').append(entry.getValue());
            }
            ClientChat.send("§7资源分类：" + builder);
        }

        ClientChat.send("§7目标命名空间：" + textOf(result.targetNamespaces()));
        ClientChat.send("§7忽略命名空间：" + textOf(result.ignoredNamespaces()));
        if (!result.contentFull()) {
            ClientChat.send("§6注意：本次指纹未覆盖全部定义类资源（部分文件超限或读取失败）");
        }
        if (result.state() == ResourceParseState.EMPTY) {
            ClientChat.send("§6当前已加载资源中没有目标命名空间的内容（原版与模组命名空间不计入）");
        }
    }

    private static String textOf(List<String> values) {
        return values == null || values.isEmpty() ? "无" : String.join("、", values);
    }
}
