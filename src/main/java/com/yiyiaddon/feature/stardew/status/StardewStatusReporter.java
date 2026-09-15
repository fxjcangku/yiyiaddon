package com.yiyiaddon.feature.stardew.status;

import com.yiyiaddon.core.CommandMessageFormatter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * 星露谷农场统一状态源与聊天播报器。
 *
 * <p>同一状态键只在进入时播报一次；普通状态尊重“状态播报”开关，关键错误永远输出。
 * Observe、Decide、Verify、Replan 与导航 tick 只更新内部流程，不进入玩家聊天。</p>
 *
 * <p>聊天显示完全复用项目统一的 {@link CommandMessageFormatter}：模块前缀、颜色、字段对齐、
 * {@code ▶} 分隔符和状态级别都由同一套排版器产出。本类只负责把状态机的业务状态转换成
 * 玩家可读的字段，不再拼接横向分隔符。</p>
 */
public final class StardewStatusReporter {

    private static final String MODULE_NAME = "星露谷农场";

    /** 控制台「日志」页保留的最近播报条数：够回溯一次操作，又不会把内存拖大。 */
    private static final int LOG_CAPACITY = 80;

    private final Consumer<String> chatSink;
    private BooleanSupplier chatEnabled = () -> true;
    private BooleanSupplier statusHints = () -> true;
    private StardewStatusSnapshot snapshot = StardewStatusSnapshot.idle();
    private String lastAnnouncementKey = "";
    /** 最近真正发进聊天的内容（新 → 旧），只留第一行；控制台「日志」页专用，不参与任何判定。 */
    private final Deque<String> chatLog = new ArrayDeque<>();
    private long revision;

    /**
     * @param chatSink 接收已经带统一模块前缀的完整消息；星露谷模块传入
     *                 {@code CommandMessageFormatter.sendRaw}，避免再次套前缀。
     */
    public StardewStatusReporter(Consumer<String> chatSink) {
        this.chatSink = chatSink == null ? ignored -> { } : chatSink;
    }

    public synchronized void setChatEnabled(BooleanSupplier enabled) {
        this.chatEnabled = enabled == null ? () -> true : enabled;
    }

    /**
     * 运行状态提示开关（例如「正在浇水」「种子回收完成」）。
     *
     * <p>只作用于任务状态播报：启动自检结论、季节限制 / 解除、错误与失效提示照常播报，
     * 因此关掉它不会让人「什么都看不到」。配置页状态行始终更新，与开关无关。</p>
     */
    public synchronized void setStatusHints(BooleanSupplier enabled) {
        this.statusHints = enabled == null ? () -> true : enabled;
    }

    public synchronized StardewStatusSnapshot snapshot() {
        return snapshot;
    }

    /** 更新配置页基础上下文，不产生聊天。 */
    public synchronized void context(String resource, String crops, String season) {
        if (Objects.equals(snapshot.resource(), resource)
            && Objects.equals(snapshot.crops(), crops)
            && Objects.equals(snapshot.season(), season)) return;
        snapshot = new StardewStatusSnapshot(safe(resource), safe(crops), safe(season), snapshot.task(),
            snapshot.detail(), snapshot.secondary(), snapshot.critical(), ++revision);
    }

    public void state(String key, String task, String detail) {
        publish(key, task, detail, "", false, true);
    }

    public void state(String key, String task, String detail, String secondary) {
        publish(key, task, detail, secondary, false, true);
    }

    /** 只更新配置页，不播报聊天。 */
    public void silent(String key, String task, String detail, String secondary) {
        publish(key, task, detail, secondary, false, false);
    }

    public void critical(String key, String task, String detail) {
        publish(key, task, detail, "", true, true);
    }

    /** 启动自检失败：汇总为一条纵向字段消息，避免逐项刷屏。 */
    public void startupCheckFailed(List<String> missing) {
        List<String> problems = missing == null ? List.of() : missing.stream()
            .filter(Objects::nonNull)
            .map(String::trim)
            .filter(value -> !value.isBlank())
            .toList();
        if (problems.isEmpty()) return;

        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "§c§l启动自检未通过")
            // 数量沿用项目统一的黄色数值高亮，和下面的失败项区分开。
            .raw(CommandMessageFormatter.line("缺失配置", "§e§l" + problems.size() + " 项"));
        appendSelfCheckFields(formatter, problems);
        formatter.status(CommandMessageFormatter.Level.FAILURE, "禁止启动");

        publishFormatted("SELF_CHECK:" + String.join("\u0000", problems),
            "启动自检未通过", "缺失配置：" + problems.size() + " 项", "", true, formatter.render(), true, true);
    }

    /** 启动自检通过：沿用资源检测的纵向字段风格，只保留玩家真正需要的信息。 */
    public void startupCheckPassed(String crops, String season) {
        String cropText = safe(crops);
        String seasonText = safe(season);
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "§a§l启动自检通过")
            .highlight("目标作物", cropText)
            .field("季节", seasonText)
            .highlight("配置状态", "正常")
            .status(CommandMessageFormatter.Level.SUCCESS, "允许启动");

        publishFormatted("SELF_CHECK_OK:" + cropText + ':' + seasonText,
            "启动自检通过", "目标作物：" + cropText, "季节：" + seasonText, false, formatter.render());
    }

    /**
     * 启动提醒：自检通过、照常开机，但有必须让玩家知道的事（当前只有一类：
     * 「区域绑的作物不在目标作物勾选里，那块地会被整块跳过」）。
     *
     * <p>不拦启动：取消勾选等于暂停种它、区域保留，是既有设计；但玩家看到的是「模块不管那块地」，
     * 所以开机时用与自检同一套排版说清楚出路（勾回来，或在「管理」里删掉该区域）。</p>
     */
    public void startupNotices(List<String> notices) {
        List<String> items = notices == null ? List.of() : notices.stream()
            .filter(Objects::nonNull)
            .map(String::trim)
            .filter(value -> !value.isBlank())
            .toList();
        if (items.isEmpty()) return;

        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "§e§l启动提醒")
            .raw(CommandMessageFormatter.line("提醒", "§e§l" + items.size() + " 项"));
        // 提醒是一句完整的话（区域号 + 出路），不是「标签 ▸ 值」，所以照原样逐条列出，
        // 不走自检那套按标签归并的字段排版（归并会把它们塞进「配置问题」一栏）。
        for (String item : items) {
            formatter.raw("§8· §f" + item);
        }
        formatter.status(CommandMessageFormatter.Level.SUCCESS, "照常启动，这些地块会被跳过");

        publishFormatted("STARTUP_NOTICE:" + String.join("\u0000", items),
            "启动提醒", "提醒：" + items.size() + " 项", "", false, formatter.render());
    }

    /**
     * 作物级季节阻塞：只暂停该作物的播种，其它作物与 Harvest / Collect / Water / DEAD / Unload 照常。
     *
     * <p>标题行只放事件标题，作物名必须是独立字段——绝不把作物名拼到模块前缀后面。
     * 同一作物 + 同一季节 + 同一原因的重复播报由协调器侧去重。</p>
     */
    public void seasonBlocked(String key, String cropLabel, String seasonLabel) {
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "§e§l季节限制")
            .highlight("作物", cropLabel)
            .field("季节", seasonLabel + "不可播种，已暂时跳过")
            .status(CommandMessageFormatter.Level.WARNING, "等待季节变化");
        publishFormatted(key, "季节限制", "作物：" + cropLabel, seasonLabel + "不可播种", false, formatter.render());
    }

    /** 季节变化后该作物重新允许播种：播一次解除消息，随后由协调器 Replan 自动恢复播种。 */
    public void seasonResumed(String key, String cropLabel, String seasonLabel) {
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "§a§l季节限制已解除")
            .highlight("作物", cropLabel)
            .field("季节", seasonLabel + "允许播种")
            .status(CommandMessageFormatter.Level.SUCCESS, "恢复播种");
        publishFormatted(key, "季节限制已解除", "作物：" + cropLabel, "恢复播种", false, formatter.render());
    }

    /** 没有任何可执行任务且所有待播种作物都被季节阻塞：模块保持运行，只播一次。 */
    public void waitingSeason(String key, String seasonLabel) {
        String season = safe(seasonLabel);
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "§e§l等待季节")
            .highlight("季节", season)
            .field("任务", "等待季节")
            .status(CommandMessageFormatter.Level.RUNNING, "模块保持运行");
        publishFormatted(key, "等待季节", "季节：" + season, "模块保持运行", false, formatter.render());
    }

    /**
     * 季节未能识别：给出最近一次疑似季节字段的来源与原文，便于玩家核对与反馈。
     *
     * <p>这不是故障，也不需要玩家处理：语义未知时业务层保持 UNKNOWN，不限制播种。</p>
     */
    public void seasonUnrecognized(String source, String rawText, String detail) {
        String origin = safe(source);
        String text = rawText == null || rawText.isBlank()
            ? "未捕获到季节字段" : compact(maskUnrenderable(rawText), 40);
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "§e§l季节识别")
            .field("来源", origin)
            .field("原文", text)
            .field("字符", detail == null || detail.isBlank() ? "无" : compact(detail, 40))
            .status(CommandMessageFormatter.Level.RUNNING, "暂不限制播种");
        publishFormatted("SEASON_UNKNOWN:" + origin + '\u0000' + text,
            "季节识别", "来源：" + origin, text, false, formatter.render());
    }

    /**
     * 把聊天栏无法渲染的字符折叠为 {@code [图标]}（原文展示统一入口，季节卡片也直接用）。
     *
     * <p>服务器把自定义贴图挂到某个码位上时（本服是韩文音节 U+B04A 这类码位），原文里就会混入
     * 这些字符；原样回显在聊天里只会得到一串方块。这里保留可读文字（ASCII、常见标点、
     * 汉字、中文标点、全角字符），其余<b>连续</b>片段统一折叠成一个 {@code [图标]}。</p>
     */
    public static String maskUnrenderable(String text) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder sb = new StringBuilder(text.length());
        boolean masking = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (isRenderable(c)) {
                masking = false;
                sb.append(c);
            } else if (!masking) {
                masking = true;
                sb.append("[图标]");
            }
        }
        return sb.toString();
    }

    /**
     * 原版字体能不能画出这个字符。
     *
     * <p>按「原版默认字体的实际覆盖范围」放行：ASCII、常见西文标点（省略号 / 间隔号 / 破折号 /
     * 圆点）、CJK 标点、CJK 汉字、全角字符。私有区（U+E000~U+F8FF）与韩文音节（服务器常拿来当
     * 图标载体）一律不放行 —— 它们在没有该资源包时就是方块。</p>
     */
    private static boolean isRenderable(char c) {
        return (c >= 0x20 && c <= 0x7E)          // ASCII 可打印
            || c == '\u2026' || c == '\u00B7'    // … ·
            || c == '\u2013' || c == '\u2014'    // – —
            || c == '\u2022'                     // •
            || (c >= 0x3000 && c <= 0x303F)      // 中文标点
            || (c >= 0x4E00 && c <= 0x9FFF)      // CJK 统一汉字
            || (c >= 0xFF01 && c <= 0xFF5E);     // 全角字符
    }

    /** 启动自检时季节未识别、稍后才识别出来：补播一次，避免玩家以为一直识别不到。 */
    public void seasonDetected(String seasonLabel) {
        String label = safe(seasonLabel);
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "§e§l季节已识别")
            .field("季节", label)
            .status(CommandMessageFormatter.Level.SUCCESS, "季节限制已生效");
        publishFormatted("SEASON_DETECTED:" + label,
            "季节已识别", "季节：" + label, "识别成功", false, formatter.render());
    }

    private static String compact(String value, int limit) {
        if (value == null) return "";
        String text = value.replace('\n', ' ').replace('\r', ' ').strip();
        return text.length() <= limit ? text : text.substring(0, limit) + "…";
    }

    /** 新会话清理去重键并回到未启动状态。 */
    public synchronized void reset() {
        lastAnnouncementKey = "";
        chatLog.clear();
        snapshot = new StardewStatusSnapshot(snapshot.resource(), snapshot.crops(), snapshot.season(),
            "未启动", "", "", false, ++revision);
    }

    private void publish(String key, String task, String detail, String secondary,
                         boolean critical, boolean announce) {
        String normalizedKey = safe(key);
        CommandMessageFormatter.Level level = levelOf(task, detail, secondary, critical);
        // 任务状态一律压成一行（正在浇水 ▸ 剩余干盆：8）；多行卡片只留给 critical 结论。
        // 原来每次任务切换都发「标题 + 位置 + 状态」三行，其中「状态 ▶ 进行中」不提供任何信息，
        // 真机上就是刷屏。
        String rendered = critical
            ? renderState(normalizedKey, task, detail, secondary, level)
            : renderInline(normalizedKey, task, detail, secondary, level);
        // 运行状态提示关闭时只静默「任务状态」这一类；critical 结论与 Reporter 自身的播报分支不受影响
        boolean hintsOn = critical || statusHints.getAsBoolean();
        publishFormatted(normalizedKey, task, detail, secondary, critical, rendered, announce && hintsOn);
    }

    /**
     * 任务状态的单行排版：{@code 彩色任务名 ▸ 白色细节}。
     *
     * <p>颜色仍按级别走（进行中黄 §e / 完成绿 §a / 警告橙 §6），任务名已经说明在做什么，
     * 因此丢掉原来的「位置 / 状态」两行字段。细节里若带「标签：值」原样保留，
     * 补充值则按业务标签补一个词头（例如「背包保留 ×8」）。</p>
     */
    private static String renderInline(String key, String task, String detail, String secondary,
                                       CommandMessageFormatter.Level level) {
        return CommandMessageFormatter.lineOf(MODULE_NAME, level.color() + safe(task),
            inlineTail(key, detail, secondary));
    }

    /** 抽取单行尾部：主细节 +（可选的）补充值。 */
    private static String inlineTail(String key, String detail, String secondary) {
        String base = safe(key);
        int colon = base.indexOf(':');
        if (colon >= 0) base = base.substring(0, colon);

        StringBuilder sb = new StringBuilder();
        String first = oneLine(detail);
        if (!first.isBlank()) sb.append(first);

        String second = oneLine(secondary);
        if (!second.isBlank()) {
            String label = secondaryLabel(base, second);
            if (sb.length() > 0) sb.append(" §8· §f");
            // 补充值本身已经以标签开头时不再重复（例如「种子保留 ×5」）
            sb.append(second.startsWith(label) ? second : label + " " + second);
        }
        return sb.toString();
    }

    /** 单行播报里不允许换行：多行细节折叠成一行，避免把一条消息撑成多行。 */
    private static String oneLine(String value) {
        if (value == null) return "";
        return value.replace('\n', ' ').replace('\r', ' ').strip();
    }

    private synchronized void publishFormatted(String key, String task, String detail, String secondary,
                                                boolean critical, String rendered) {
        publishFormatted(key, task, detail, secondary, critical, rendered, true, false);
    }

    private synchronized void publishFormatted(String key, String task, String detail, String secondary,
                                                boolean critical, String rendered, boolean announce) {
        publishFormatted(key, task, detail, secondary, critical, rendered, announce, false);
    }

    /**
     * @param force 跳过「同一状态键只播报一次」的去重。
     *              启动自检失败这类结论是玩家点击后必须得到的回执：内容不变不能等于静默，
     *              否则表现为「模块点了没反应、启动不了」。
     */
    private synchronized void publishFormatted(String key, String task, String detail, String secondary,
                                                boolean critical, String rendered, boolean announce, boolean force) {
        String normalizedKey = safe(key);
        snapshot = new StardewStatusSnapshot(snapshot.resource(), snapshot.crops(), snapshot.season(),
            safe(task), nullToEmpty(detail), nullToEmpty(secondary), critical, ++revision);
        if (!announce) return;
        if (!force && normalizedKey.equals(lastAnnouncementKey)) return;
        lastAnnouncementKey = normalizedKey;
        if (!critical && !chatEnabled.getAsBoolean()) return;
        chatSink.accept(rendered);
        recordLog(rendered);
    }

    /**
     * 控制台「日志」页数据：最近真正发进聊天的内容，新 → 旧。
     *
     * <p>记录的是「界面上实际看到的那一条」，不是内部状态；因此日志与聊天栏永远一致，
     * 也不会因为去重 / 开关而被塞进没播过的内容。</p>
     */
    public synchronized List<String> recentLog() {
        return new ArrayList<>(chatLog);
    }

    /** 清空控制台日志（只清历史，不影响状态与去重键）。 */
    public synchronized void clearLog() {
        chatLog.clear();
    }

    /**
     * 记一条日志：只留第一行，并去掉模块前缀。
     *
     * <p>多行卡片的第一行就是标题（例如「季节限制」「启动自检」），信息量最高；
     * 前缀在工作台里已经由页面标题表达，逐行重复只会把这一列挤爆。</p>
     */
    private void recordLog(String rendered) {
        String line = logLine(rendered);
        if (line.isBlank()) return;
        chatLog.addFirst(line);
        while (chatLog.size() > LOG_CAPACITY) chatLog.removeLast();
    }

    /** 取首行并剥掉 {@code §c§l[yiyiaddon]§r§f§l[星露谷农场]§r} 前缀。 */
    private static String logLine(String rendered) {
        if (rendered == null || rendered.isBlank()) return "";
        int firstLineEnd = rendered.indexOf('\n');
        String line = firstLineEnd < 0 ? rendered : rendered.substring(0, firstLineEnd);
        int prefixEnd = line.indexOf("]§r");
        return prefixEnd < 0 ? line : line.substring(prefixEnd + 3);
    }

    private static String renderState(String key, String task, String detail, String secondary,
                                      CommandMessageFormatter.Level level) {
        String title = colorFor(level) + safe(task);
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, title);
        appendStateFields(formatter, key, detail, secondary);
        formatter.status(level, levelDetail(level, task));
        return formatter.render();
    }

    private static void appendStateFields(CommandMessageFormatter formatter, String key,
                                          String detail, String secondary) {
        String base = key;
        int colon = base.indexOf(':');
        if (colon >= 0) base = base.substring(0, colon);

        if (detail != null && !detail.isBlank()) {
            String label = detailLabel(base);
            appendDetail(formatter, label, detail);
        }
        if (secondary != null && !secondary.isBlank()) {
            String label = secondaryLabel(base, secondary);
            appendDetail(formatter, label, secondary);
        }
    }

    /** 把状态机原有的「标签：值」或物品统计转换为统一字段，不改变业务内容。 */
    private static void appendDetail(CommandMessageFormatter formatter, String defaultLabel, String raw) {
        String normalized = normalizeValue(raw);
        if (normalized.isBlank()) return;

        String[] rows = normalized.split("\\n");
        for (String row : rows) {
            String value = row.strip();
            if (value.isBlank()) continue;
            int separator = value.indexOf('：');
            if (separator < 0) separator = value.indexOf(':');
            if (separator > 0 && separator < value.length() - 1) {
                String label = value.substring(0, separator).strip();
                String fieldValue = value.substring(separator + 1).strip();
                if (!label.isBlank() && !fieldValue.isBlank()) {
                    formatter.field(label, fieldValue);
                    continue;
                }
            }
            formatter.field(defaultLabel, value);
        }
    }

    private static String detailLabel(String base) {
        return switch (base) {
            case "WATER" -> "剩余干盆";
            case "REFILL", "REFILL_DONE" -> "水壶";
            case "PLANT", "PLANT_DONE", "HARVEST", "HARVEST_DONE" -> "作物";
            case "RESTOCK", "RESTOCK_DONE" -> "种子";
            case "SEED_RETURN_DONE" -> "种子";
            case "COLLECT" -> "目标";
            case "COLLECT_DONE" -> "产物";
            case "UNLOAD" -> "作物";
            case "UNLOAD_DONE" -> "产物";
            case "DEAD_CLEAR" -> "任务";
            case "DEAD_DONE" -> "结果";
            case "RETURN", "RETURN_DONE" -> "位置";
            case "FERTILIZE", "POTION", "SPRINKLER" -> "目标";
            case "SEASON" -> "季节";
            case "SPECIAL", "LEARN_FAIL", "POTION_UNVERIFIED", "OUTPUT_FULL" -> "结果";
            case "SEED_EMPTY" -> "结果";
            case "CONTAINER_UNREACHABLE", "BOUNDARY", "LOGISTICS_POINT", "WATER_POINT",
                 "SPRINKLER_POINT", "NAV_BLOCK", "RESTOCK_FAIL", "UNLOAD_FAIL", "REFILL_NO_PROGRESS" -> "原因";
            default -> "详情";
        };
    }

    private static String secondaryLabel(String base, String secondary) {
        String text = normalizeValue(secondary);
        if (base.equals("COLLECT_DONE") && text.startsWith("种子")) return "种子";
        if (base.equals("UNLOAD_DONE") && text.startsWith("种子保留")) return "种子保留";
        if (base.equals("UNLOAD_DONE")) return "保留";
        if (base.equals("SEED_RETURN_DONE")) return "背包保留";
        return "补充";
    }

    private static CommandMessageFormatter.Level levelOf(String task, String detail, String secondary, boolean critical) {
        if (critical) return CommandMessageFormatter.Level.FAILURE;
        String text = (task == null ? "" : task) + " "
            + (detail == null ? "" : detail) + " "
            + (secondary == null ? "" : secondary);
        if (text.contains("失败") || text.contains("失效") || text.contains("无法")) {
            return CommandMessageFormatter.Level.WARNING;
        }
        if (text.contains("完成") || text.contains("通过") || text.contains("已补满")
            || text.contains("已清除") || text.contains("已返回")) {
            return CommandMessageFormatter.Level.SUCCESS;
        }
        if (text.contains("缺少") || text.contains("不可") || text.contains("跳过")
            || text.contains("没有") || text.contains("未确认")) {
            return CommandMessageFormatter.Level.WARNING;
        }
        if (text.contains("已停止")) return CommandMessageFormatter.Level.INFO;
        return CommandMessageFormatter.Level.RUNNING;
    }

    private static String levelDetail(CommandMessageFormatter.Level level, String task) {
        return switch (level) {
            case SUCCESS -> "完成";
            case RUNNING -> "进行中";
            case WARNING -> "请处理";
            case FAILURE -> "已停止";
            case INFO -> "信息";
        };
    }

    private static String colorFor(CommandMessageFormatter.Level level) {
        return switch (level) {
            case SUCCESS -> "§a§l";
            case RUNNING -> "§e§l";
            case WARNING -> "§6§l";
            case FAILURE -> "§c§l";
            case INFO -> "§f§l";
        };
    }

    private static void appendSelfCheckFields(CommandMessageFormatter formatter, List<String> problems) {
        Map<String, List<String>> fields = new LinkedHashMap<>();
        for (String problem : problems) {
            String label;
            String value;
            if (problem.equals("未选择水壶") || problem.startsWith("背包缺少已选水壶")) {
                label = "水壶";
                value = problem.equals("未选择水壶") ? "未选择" : suffix(problem, "背包");
            } else if (problem.startsWith("背包缺少") && problem.substring(4).contains("种子")) {
                label = "背包种子";
                value = problem.substring(4);
            } else if (problem.startsWith("背包缺少")) {
                label = "背包物品";
                value = problem.substring(4);
            } else if (problem.startsWith("种子箱")) {
                label = "种子箱";
                value = suffix(problem, "种子箱");
            } else if (problem.startsWith("成品箱")) {
                label = "成品箱";
                value = suffix(problem, "成品箱");
            } else if (problem.startsWith("补水点")) {
                label = "补水点";
                value = suffix(problem, "补水点");
            } else if (problem.equals("未选择目标作物")) {
                label = "目标作物";
                value = "未选择";
            } else if (problem.equals("未选择种植盆")) {
                label = "种植盆";
                value = "未选择";
            } else if (problem.contains("未选择肥料")) {
                label = "肥料";
                value = "未选择";
            } else if (problem.contains("未选择药剂")) {
                label = "魔法药剂";
                value = "未选择";
            } else if (problem.contains("未选择洒水器")) {
                label = "洒水器";
                value = "未选择";
            } else if (problem.startsWith("洒水器点位")) {
                label = "洒水器点位";
                value = suffix(problem, "洒水器点位");
            } else if (problem.startsWith("当前维度还没有种植区域")) {
                label = "种植区域";
                value = "当前维度一块都没划";
            } else if (problem.startsWith("当前环境")) {
                label = "运行环境";
                value = problem;
            } else if (problem.startsWith("当前服务器资源档案")) {
                label = "资源档案";
                value = problem;
            } else {
                label = "配置问题";
                value = problem;
            }
            fields.computeIfAbsent(label, ignored -> new ArrayList<>()).add(value);
        }
        for (Map.Entry<String, List<String>> entry : fields.entrySet()) {
            String value = String.join("、", entry.getValue());
            formatter.raw(CommandMessageFormatter.line(entry.getKey(), selfCheckValue(entry.getKey(), value)));
        }
    }

    /** 自检失败项的值按既有全项目配色区分：缺种警告、点位/配置阻断失败。 */
    private static String selfCheckValue(String label, String value) {
        String color;
        if ("背包种子".equals(label)) {
            color = "§6§l"; // 缺种是可补充的警告，沿用 notifyError 的橙色。
        } else if ("运行环境".equals(label) || "资源档案".equals(label)
            || "配置问题".equals(label)
            || value.contains("未绑定") || value.contains("未选择")
            || value.contains("不匹配") || value.contains("失效")
            || value.contains("无法") || value.contains("未就绪")) {
            color = "§c§l"; // 会阻止启动的配置错误。
        } else {
            color = "§f";
        }
        return color + value;
    }

    private static String suffix(String text, String prefix) {
        String value = text.substring(Math.min(prefix.length(), text.length())).strip();
        if (value.startsWith("：") || value.startsWith(":")) value = value.substring(1).strip();
        if (value.isBlank()) return "未设置";
        return value;
    }

    private static String normalizeValue(String value) {
        if (value == null) return "";
        return value.replace('｜', '、').replace('│', '、').replace("\r", "");
    }

    private static String safe(String value) {
        return value == null || value.isBlank() ? "未知" : value;
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
