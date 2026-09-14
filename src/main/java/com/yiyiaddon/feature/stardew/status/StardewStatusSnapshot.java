package com.yiyiaddon.feature.stardew.status;

/**
 * 星露谷农场统一只读状态快照。
 *
 * <p>协调器只更新这一份状态，配置页持续读取它，聊天播报则只在快照状态发生变化时输出。
 * 资源、作物、季节和任务因此不会再由两个界面分别推测。</p>
 */
public record StardewStatusSnapshot(
    String resource,
    String crops,
    String season,
    String task,
    String detail,
    String secondary,
    boolean critical,
    long revision
) {
    public static StardewStatusSnapshot idle() {
        return new StardewStatusSnapshot("未就绪", "未选择", "未知", "未启动", "", "", false, 0L);
    }

    /** 配置页第一行：稳定基础信息 + 当前真实任务。 */
    public String primaryLine() {
        return "§7资源包：§f" + compact(resource, 12)
            + " §8│ §7作物：§f" + compact(crops, 20)
            + " §8│ §7季节：§f" + compact(season, 8)
            + " §8│ §7任务：§f" + compact(task, 16);
    }

    /** 配置页第二行：当前任务最多两项动态信息；无信息时不占额外内容。 */
    public String secondaryLine() {
        if (detail == null || detail.isBlank()) return "";
        if (secondary == null || secondary.isBlank()) return "§7" + compact(detail, 42);
        return "§7" + compact(detail, 28) + " §8│ §7" + compact(secondary, 28);
    }

    private static String compact(String value, int max) {
        String safe = value == null || value.isBlank() ? "未知" : value;
        return safe.length() <= max ? safe : safe.substring(0, Math.max(1, max - 1)) + "…";
    }
}
