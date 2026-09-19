package com.yiyiaddon.feature.autologin.service;

import net.minecraft.client.Minecraft;
import net.minecraft.client.User;

import java.util.Optional;
import java.util.UUID;

/**
 * 正版账号检测（逐字照旧项目 {@code autologin/service/AccountChecker}）。
 *
 * <p>基于本地会话特征判断，不发起任何网络请求：
 *   - xuid 存在      → 微软/Xbox 账户，正版特征
 *   - UUID 版本 == 4 → 正版分配的随机 UUID
 *   - UUID 版本 == 3 → 离线模式由用户名 MD5 生成
 *   - accessToken 有效长度 → 已通过验证服务器登录
 * </p>
 *
 * <p>注意：这是客户端本地推断，不等同于服务器端的 Mojang 验证结果。</p>
 *
 * <p>用途仅限于记录当前连接环境，<b>不得</b>用于决定是否执行自动登录/注册——
 * 正版账号同样可能进入离线模式服务器并被要求 /login。
 * 是否登录一律以服务器聊天提示为准。</p>
 */
public final class AccountChecker {

    /** 检测结论 */
    public enum Result {
        /** 正版（微软账户 + 有效令牌） */
        PREMIUM,
        /** 离线 / 盗版（UUID 为 v3 或缺少 xuid） */
        OFFLINE,
        /** 无法判断（会话信息缺失） */
        UNKNOWN
    }

    private AccountChecker() {}

    /** 执行检测 */
    public static Result check(Minecraft mc) {
        User user = mc.getUser();
        if (user == null) return Result.UNKNOWN;

        UUID uuid = user.getProfileId();
        if (uuid == null) return Result.UNKNOWN;

        boolean hasXuid  = user.getXuid().filter(s -> !s.isBlank()).isPresent();
        boolean hasToken = isValidToken(user.getAccessToken());
        int version = uuid.version();

        // 离线模式的 UUID 由用户名生成，固定为版本 3
        if (version == 3) return Result.OFFLINE;

        // 正版：随机 UUID(v4) + 微软账户标识 + 有效令牌
        if (version == 4 && hasXuid && hasToken) return Result.PREMIUM;

        // v4 但缺少 xuid 或 token：第三方启动器可能伪造 v4 UUID，无法确定
        // 不再直接断定 OFFLINE，避免误报
        return Result.UNKNOWN;
    }

    /** 生成给玩家看的检测报告（不含令牌等敏感信息） */
    public static String describe(Minecraft mc) {
        User user = mc.getUser();
        if (user == null) return "无法读取会话信息。";

        Result result = check(mc);
        UUID uuid = user.getProfileId();
        UUID worldUuid = mc.player != null ? mc.player.getUUID() : null;

        String label = switch (result) {
            case PREMIUM -> "§a§l正版账号";
            case OFFLINE -> "§6§l离线账号";
            case UNKNOWN -> "§7§l无法确定";
        };

        String sessionUuid = uuid != null ? uuid.toString() : "未知";
        String currentUuid = worldUuid != null ? worldUuid.toString() : "未进入世界";
        String comparison = uuid != null && worldUuid != null
            ? (uuid.equals(worldUuid) ? "一致" : "不一致")
            : "无法比较";

        return "账号检测: " + label + " §r§f§l| 用户名: " + user.getName()
            + " | 会话UUID: " + sessionUuid
            + " | 世界UUID: " + currentUuid
            + " | UUID状态: " + comparison;
    }

    /** 生成 Debug 详情（仅显示令牌长度，不显示令牌本身） */
    public static String describeDebug(Minecraft mc) {
        User user = mc.getUser();
        if (user == null) return "会话为空。";

        Optional<String> xuid = user.getXuid();
        UUID uuid = user.getProfileId();

        return "xuid=" + (xuid.isPresent() ? "存在" : "缺失")
            + "  clientId=" + (user.getClientId().isPresent() ? "存在" : "缺失")
            + "  token长度=" + user.getAccessToken().length()
            + "  uuid=" + uuid;
    }

    private static boolean isValidToken(String token) {
        // 离线启动器常填 "0"、"null" 或留空
        if (token == null || token.isBlank()) return false;
        if (token.equals("0") || token.equalsIgnoreCase("null")) return false;
        return token.length() >= 16;
    }
}
