package com.yiyiaddon.feature.mining.model;

/**
 * 一份「自动挖矿配置记录」的元信息（设置本体与点位本体不在这里，由
 * {@code MiningConfigRecordStore} 按需读）。
 *
 * <p><b>怎么辨认是哪台服务器的</b>（用户 2026-09-18：「配置要带服务器ip 方便辨认 跟单人世界识别的」）：
 * 记录文件名是净化过的片段（{@code server_520mc.cc_25565.json}），单看文件名认不出是哪个服；
 * 记录里存的这份元信息带原始服务器键，列表与聊天播报都按 {@link #displayName()} 显示。</p>
 *
 * @param scopeKey   文件名安全键（读写 / 删除用它，{@code WorldIdentity.fileSafeServer()} 的产物）
 * @param serverKey  逻辑服务器键（展示用它）：多人 {@code host:port}，单人 {@code singleplayer:<存档目录名>}
 * @param savedAt    保存时刻（毫秒时间戳）
 * @param pointCount 记录里带了几个点位（0 ~ 3）；列表上显示「点位 3/3」，让玩家一眼看出记录是否完整
 */
public record ConfigRecord(String scopeKey, String serverKey, long savedAt, int pointCount) {

    private static final String SINGLEPLAYER_PREFIX = "singleplayer:";

    /** 是否来自单人存档 */
    public boolean singleplayer() {
        return serverKey != null && serverKey.startsWith(SINGLEPLAYER_PREFIX);
    }

    /** 单人存档目录名（不是单人返回空串） */
    public String worldName() {
        return singleplayer() ? serverKey.substring(SINGLEPLAYER_PREFIX.length()) : "";
    }

    /**
     * 展示名：多人是带端口的 {@code host:port}（用户要的「带服务器ip 方便辨认」），
     * 单人是 {@code 单人世界 · <存档目录名>}（与服务器记录一眼区分）。
     */
    public String displayName() {
        return serverKey == null || serverKey.isBlank() ? scopeKey : displayNameOf(serverKey);
    }

    /**
     * 逻辑服务器键 → 展示名（与 {@link #displayName()} 同一套写法）。
     *
     * <p>静态入口是给「当前这台服叫什么」这类没有记录对象的场合用的
     * （例如「不能复原：这张记录不是本服的」提示要写出当前是哪个服）；
     * 键为空或空白返回「未进入世界」。</p>
     */
    public static String displayNameOf(String serverKey) {
        if (serverKey == null || serverKey.isBlank()) return "未进入世界";
        if (serverKey.startsWith(SINGLEPLAYER_PREFIX)) {
            return "单人世界 · " + serverKey.substring(SINGLEPLAYER_PREFIX.length());
        }
        return serverKey;
    }
}
