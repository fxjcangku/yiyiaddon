package com.yiyiaddon.feature.stardew.season;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.status.StardewStatusReporter;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 星露谷农场季节播报与人工绑定。
 *
 * <p>逐字搬运自 {@code StardewFarmModule}（{@code checkSeasonFollowup}、{@code reportSeasonStatus}、
 * {@code bindSeasonFromProbe}、{@code clearSeasonBinding}、{@code parseSeasonName}、
 * {@code glyphSummary}、{@code displayOr}、{@code playerSeasonLabel}）；方法体、注释与文案一字未改，
 * 只把模块字段访问改为经模块读取 / 回写。</p>
 */
public final class StardewSeasonBinding {

    private final StardewFarmModule module;

    public StardewSeasonBinding(StardewFarmModule module) {
        this.module = module;
    }

    public String playerSeasonLabel() {
        for (StardewSeasonService.SeasonToken token : StardewSeasonService.instance().snapshot().tokens()) {
            if (token.semantic() != StardewSeasonService.SeasonSemantic.UNKNOWN) {
                return token.semantic().displayName() + "季";
            }
        }
        return StardewSeasonService.instance().snapshot().known() ? "当前季节" : "未知";
    }

    /**
     * 启动自检时季节未识别时的善后：
     * 稍后识别出来 → 补播一次「季节已识别」；宽限期内一直没识别 → 播一次证据卡便于核对。
     */
    public void checkSeasonFollowup() {
        if (!module.pendingSeasonFollowup()) return;
        String season = playerSeasonLabel();
        if (StardewFarmModule.NAMED_SEASONS.contains(season)) {
            module.setPendingSeasonFollowup(false);
            module.setSeasonDiagnosticGrace(0);
            module.statusReporter().seasonDetected(season);
            return;
        }
        if (module.seasonDiagnosticGrace() <= 0) return;
        module.setSeasonDiagnosticGrace(module.seasonDiagnosticGrace() - 1);
        if (module.seasonDiagnosticGrace() > 0) return;
        module.setPendingSeasonFollowup(false);
        StardewSeasonService service = StardewSeasonService.instance();
        // 「字符」行优先给出码位 + 自动识别结论（例如 U+B04A(春季)），没有候选字符时回落到区间信息
        String glyphs = glyphSummary(service);
        module.statusReporter().seasonUnrecognized(service.probeSource(), service.probeText(),
            "暂无".equals(glyphs) ? service.probeDetail() : glyphs);
    }

    /** {@code .stardew 季节}：输出当前识别结论；只有识别不出时才附上证据供人工核对。 */
    public void reportSeasonStatus() {
        StardewSeasonService service = StardewSeasonService.instance();
        String season = playerSeasonLabel();
        CommandMessageFormatter card = CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "季节识别");
        card.raw("§7当前季节 §8▸ §f" + season);
        card.raw("§7来源 §8▸ §f" + displayOr(service.probeSource(), "暂无"));
        // 识别成功时不再回显原文 / 码位 / 作用域：服务器把贴图挂在韩文音节等码位上，
        // 原文原样打出来就是一串方块和「金币」之类的无关文本（证据行本身就在那句原文里），
        // 结论已经有了就没必要再看。识别不出时才把证据打出来，那时它才真的有用。
        if (!StardewFarmModule.NAMED_SEASONS.contains(season)) {
            card.raw("§7原文 §8▸ §f"
                + displayOr(StardewStatusReporter.maskUnrenderable(service.probeText()), "暂无"));
            card.raw("§7字符 §8▸ §f" + glyphSummary(service));
            card.raw("§7作用域 §8▸ §f" + displayOr(ResourceExtractionService.serverKey(), "未就绪")
                + " §8+ §f" + displayOr(ResourceExtractionService.fingerprint(), "未就绪"));
        }
        card.send();
    }

    /**
     * {@code .stardew 季节 春|夏|秋|冬}：把最近一次季节字段里的第一个图标字符绑定为该季节。
     *
     * <p>只写入当前 {@code ServerKey + fingerprint}，不会成为全局规则，也不会覆盖资源包自动证据：
     * 绑定仅填补自动识别不到的空缺，因此结果播报会如实区分「真的生效」与「被自动证据优先」。</p>
     */
    public void bindSeasonFromProbe(String input) {
        StardewSeasonService.SeasonSemantic semantic = parseSeasonName(input);
        if (semantic == StardewSeasonService.SeasonSemantic.UNKNOWN) {
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "季节绑定失败")
                .field("原因", "无法识别季节名「" + input + "」")
                .field("可用取值", "春 / 夏 / 秋 / 冬")
                .status(CommandMessageFormatter.Level.FAILURE, "未写入")
                .send();
            return;
        }
        String serverKey = ResourceExtractionService.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        if (serverKey == null || fingerprint == null) {
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "季节绑定失败")
                .field("原因", "当前服务器资源尚未就绪")
                .field("操作", "请先在「服务器资源」完成检测 / 提取后再绑定")
                .status(CommandMessageFormatter.Level.FAILURE, "未写入")
                .send();
            return;
        }
        StardewSeasonService service = StardewSeasonService.instance();
        List<StardewSeasonService.GlyphRef> refs = service.probeGlyphs();
        if (refs.isEmpty()) {
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "季节绑定失败")
                .field("原因", "尚未捕获到季节图标字符")
                .field("操作", "请先进入服务器并让季节显示出现一次，再执行本指令")
                .status(CommandMessageFormatter.Level.FAILURE, "未写入")
                .send();
            return;
        }
        StardewSeasonService.GlyphRef target = refs.get(0);
        String targetLabel = semantic.displayName() + "季";
        String before = playerSeasonLabel();
        if (!StardewSeasonManualBinding.bind(serverKey, fingerprint, target.fontKey(),
            target.codepoint(), semantic)) {
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "季节绑定失败")
                .field("原因", "写入绑定文件失败")
                .status(CommandMessageFormatter.Level.FAILURE, "未写入")
                .send();
            return;
        }
        service.refreshSemantics();
        String after = playerSeasonLabel();

        // 人工绑定只补资源包自动识别的空缺，不覆盖自动证据；因此必须如实说明绑定有没有真的改变当前季节。
        CommandMessageFormatter.Level level;
        String status;
        if (targetLabel.equals(after) && !targetLabel.equals(before)) {
            level = CommandMessageFormatter.Level.SUCCESS;
            status = "已生效，当前季节 " + after;
        } else if (targetLabel.equals(after)) {
            level = CommandMessageFormatter.Level.INFO;
            status = "已记录，与资源包自动识别一致";
        } else {
            level = CommandMessageFormatter.Level.WARNING;
            status = "已记录，但资源包自动证据优先，当前季节仍为 " + after;
        }
        CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "季节绑定成功")
            .highlight("季节", targetLabel)
            .field("字符", target.code() + " §8(" + target.fontKey() + ")")
            .field("作用域", serverKey + " §8+ §f" + fingerprint)
            .field("当前季节", after)
            .status(level, status)
            .send();
    }

    /** {@code .stardew 季节 清除}：删除当前服务器的全部人工季节绑定。 */
    public void clearSeasonBinding() {
        String serverKey = ResourceExtractionService.serverKey();
        if (serverKey == null) {
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "清除季节绑定失败")
                .field("原因", "当前服务器资源尚未就绪")
                .status(CommandMessageFormatter.Level.FAILURE, "未修改")
                .send();
            return;
        }
        if (!StardewSeasonManualBinding.clear(serverKey)) {
            CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "清除季节绑定失败")
                .field("原因", "写入绑定文件失败")
                .status(CommandMessageFormatter.Level.FAILURE, "未修改")
                .send();
            return;
        }
        StardewSeasonService.instance().refreshSemantics();
        CommandMessageFormatter.of(StardewFarmModule.MODULE_NAME, "已清除季节绑定")
            .field("作用域", serverKey)
            .field("当前季节", playerSeasonLabel())
            .status(CommandMessageFormatter.Level.SUCCESS, "已恢复为资源包自动识别")
            .send();
    }

    /** 季节名解析：中文、英文与拼音都接受；无法识别返回 UNKNOWN。 */
    private static StardewSeasonService.SeasonSemantic parseSeasonName(String input) {
        if (input == null) return StardewSeasonService.SeasonSemantic.UNKNOWN;
        return switch (input.strip().toLowerCase(Locale.ROOT)) {
            case "春", "春季", "spring", "spr", "chun" -> StardewSeasonService.SeasonSemantic.SPRING;
            case "夏", "夏季", "summer", "sum", "xia" -> StardewSeasonService.SeasonSemantic.SUMMER;
            case "秋", "秋季", "autumn", "fall", "qiu" -> StardewSeasonService.SeasonSemantic.AUTUMN;
            case "冬", "冬季", "winter", "win", "dong" -> StardewSeasonService.SeasonSemantic.WINTER;
            default -> StardewSeasonService.SeasonSemantic.UNKNOWN;
        };
    }

    /** 最近一次季节字段候选字符的展示文本（含自动识别结论）。 */
    private static String glyphSummary(StardewSeasonService service) {
        List<StardewSeasonService.GlyphRef> refs = service.probeGlyphs();
        if (refs.isEmpty()) return "暂无";
        List<String> parts = new ArrayList<>();
        StardewSeasonGlyphMap map = StardewSeasonGlyphMap.current();
        for (int i = 0; i < refs.size() && i < 4; i++) {
            StardewSeasonService.GlyphRef ref = refs.get(i);
            StardewSeasonService.SeasonSemantic semantic = map.semanticOf(ref.fontKey(), ref.codepoint());
            parts.add(ref.code() + (semantic == StardewSeasonService.SeasonSemantic.UNKNOWN
                ? "" : "(" + semantic.displayName() + "季)"));
        }
        return String.join(" §8/ §f", parts);
    }

    private static String displayOr(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}
