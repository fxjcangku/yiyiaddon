package com.yiyiaddon.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 最近活动流水：记录本次会话里发生过的状态变化，供首页展示。
 *
 * <p>只保留最近 {@link #CAPACITY} 条、不落盘：它描述的是「这一局刚才发生了什么」，
 * 存到磁盘反而会让人分不清哪些是本次发生的。</p>
 *
 * <p>写入来自模块运行时的开关与异常路径，读取只发生在渲染线程，因此用同步块保护双端队列。</p>
 */
public final class ActivityLog {

    /** 保留条数。 */
    private static final int CAPACITY = 40;
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    /** 最新在最前。 */
    private static final Deque<String> LINES = new ArrayDeque<>();

    private ActivityLog() {
    }

    /** 记一条活动；时间戳由本类补，调用方只给正文。 */
    public static void record(String text) {
        if (text == null || text.isBlank()) return;
        String line = TIME.format(Instant.now().atZone(ZoneId.systemDefault())) + "  " + text.strip();
        synchronized (LINES) {
            LINES.addFirst(line);
            while (LINES.size() > CAPACITY) LINES.removeLast();
        }
    }

    /**
     * 最近 {@code limit} 条，最新在前。
     *
     * @return 条数不足时返回实际条数，永不为 null
     */
    public static List<String> recent(int limit) {
        if (limit <= 0) return List.of();
        synchronized (LINES) {
            List<String> out = new ArrayList<>(Math.min(limit, LINES.size()));
            for (String line : LINES) {
                if (out.size() >= limit) break;
                out.add(line);
            }
            return out;
        }
    }
}
