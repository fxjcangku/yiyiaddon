package com.yiyiaddon.model;

import java.util.List;

/**
 * {@code /api/stats} 的公开脱敏统计快照。
 *
 * @param totalUsers   累计用户数
 * @param totalUses    累计启动次数
 * @param active24h    24 小时内活跃用户数
 * @param onlineUsers  当前在线用户数
 * @param recentUsers  最近活跃用户列表
 */
public record StatsSnapshot(int totalUsers, int totalUses, int active24h, int onlineUsers,
                            List<RecentUser> recentUsers) {

    /**
     * 最近活跃用户条目。
     *
     * @param lastSeen 毫秒时间戳；后端在部分接口下发秒级时间戳，解析层已归一为毫秒
     */
    public record RecentUser(String name, String serverName, boolean online, long lastSeen) {
    }

    public static StatsSnapshot empty() {
        return new StatsSnapshot(0, 0, 0, 0, List.of());
    }
}
