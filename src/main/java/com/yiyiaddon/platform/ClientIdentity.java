package com.yiyiaddon.platform;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;

import java.util.Optional;
import java.util.UUID;

/**
 * 会话身份适配：统一提供后端需要的 uuid / name / xuid 与模组版本号。
 *
 * <p>身份一律取会话账户（{@code Minecraft.getUser()}）而不是玩家实体：
 * 正版链路下服务器会重写实体 UUID，用实体 UUID 会导致同一玩家被统计成多个。</p>
 */
public final class ClientIdentity {

    private static final String MOD_ID = "yiyiaddon";

    /** 开发环境正版登录模组 id，仅开发端存在（见 build.gradle 的 localRuntime）。 */
    private static final String DEV_AUTH_MOD_ID = "dev-auth-neo";

    private ClientIdentity() {
    }

    /** 会话 UUID；不可用时返回 {@code null}。 */
    public static UUID uuid() {
        User user = Minecraft.getInstance().getUser();
        return user == null ? null : user.getProfileId();
    }

    public static String uuidString() {
        UUID uuid = uuid();
        return uuid == null ? null : uuid.toString();
    }

    public static String name() {
        User user = Minecraft.getInstance().getUser();
        return user == null ? null : user.getName();
    }

    /** 微软 XUID；未注入 authlib-injector 时客户端会给出占位符，这里只接受纯数字。 */
    public static String xuid() {
        try {
            User user = Minecraft.getInstance().getUser();
            if (user == null) return null;
            Optional<String> xuid = user.getXuid();
            if (xuid.isEmpty()) return null;
            String value = xuid.get().trim();
            return value.matches("\\d{8,20}") ? value : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 本地正版判定：仅作为展示兜底，最终以后端返回的 {@code is_premium} 为准。
     *
     * <p>三条判据，命中任意一条即算正版：</p>
     * <ol>
     *   <li>{@code xuid} 非空 —— 原版正版会话一定会带 XUID 属性，离线会话恒为空；</li>
     *   <li>{@link #hasSessionCredentials()} —— 会话 UUID 是 v4 且令牌有效。
     *       <b>这条是 2026-09-21 补的</b>：用户反馈「我明明正版号给我判成离线了 好多个号都这样」，
     *       查线上库发现这些号的 {@code xuid} 全是 NULL（第三方启动器/环境不给 XUID 属性），
     *       单靠第 1 条等于把正版会话一律判成离线；</li>
     *   <li>开发环境加载了 DevAuth Neo —— 它接管会话时只提供 ACCESS_TOKEN / UUID / USERNAME
     *       （见其 {@code MicrosoftAuthProvider} 的令牌集合），<b>不会填 XUID</b>，
     *       所以开发端即使真的登录了正版，第 1 条也永远不成立。</li>
     * </ol>
     * <p>DevAuth 只在开发环境存在（{@code localRuntime} 依赖，不进发布产物），
     * 且它只在真正完成微软登录后才接管会话，因此第 3 条不会把离线会话判成正版。</p>
     */
    public static boolean premium() {
        if (xuid() != null) return true;
        if (hasSessionCredentials()) return true;
        return FabricLoader.getInstance().isModLoaded(DEV_AUTH_MOD_ID);
    }

    /**
     * 会话凭据是否像一次真实登录：UUID 为 v4（正版随机分配）且 access token 有效。
     *
     * <p><b>为什么需要它</b>：很多启动器不把 XUID 塞进会话属性，而正版会话的 UUID 一定是随机分配的 v4；
     * 离线模式由用户名 MD5 派生，固定是 v3（线上库里 {@code wosinima} 那条就是
     * {@code 00000000-0000-3008-…}）。离线启动器还惯于把令牌填成 {@code 0} / {@code null} / 空串，
     * 因此「v4 + 有效令牌」把两者分得很开。判据与 {@code AccountChecker} 的两条本地特征同源，
     * 差别只在这里没有「无法确定」这一档：界面上只能显示正版或离线，因此按「像正版就算正版」收口，
     * 目标是<strong>不再把正版误判成离线</strong> —— 单看 XUID 的旧口径在无 XUID 的启动器上必然误判。</p>
     *
     * <p>最终口径仍以后端为准：后端会按名字查 Mojang 官方档案（{@code resolvePremium}），
     * 其结果经 {@code /api/register} 的 {@code is_premium} 回带，由 {@code HomeStats} 采纳。</p>
     */
    private static boolean hasSessionCredentials() {
        UUID uuid = uuid();
        if (uuid == null || uuid.version() != 4) return false;
        return validToken(accessToken());
    }

    /** 会话访问令牌；读不到返回 {@code null}。 */
    private static String accessToken() {
        try {
            User user = Minecraft.getInstance().getUser();
            return user == null ? null : user.getAccessToken();
        } catch (Exception e) {
            return null;
        }
    }

    /** 令牌是否像真实凭据：离线启动器常填 {@code 0} / {@code null} / 空串，长度普遍也短。 */
    private static boolean validToken(String token) {
        if (token == null || token.isBlank()) return false;
        if (token.equals("0") || token.equalsIgnoreCase("null")) return false;
        return token.length() >= 16;
    }

    /** 本模组版本号，取 fabric.mod.json 中的 version。 */
    public static String version() {
        return FabricLoader.getInstance().getModContainer(MOD_ID)
                .map(container -> container.getMetadata().getVersion().getFriendlyString())
                .orElse("unknown");
    }

    /**
     * 运行中的 Minecraft 版本号（26.1.2 / 26.2 这种）；拿不到时返回空串。
     *
     * <p>两条版本线的模组版本号相同，界面上靠它区分装的是哪条线，
     * 更新检查也用它只认本线的发布附件（空串等于不筛，宁可多提示也不要漏提示）。</p>
     *
     * <p>别和 {@link #minecraftVersion()} 混了：那个是版本<b>类型</b>（release / snapshot）。</p>
     */
    public static String gameVersion() {
        return FabricLoader.getInstance().getModContainer("minecraft")
                .map(container -> container.getMetadata().getVersion().getFriendlyString())
                .orElse("");
    }

    /**
     * Minecraft 版本类型（release / snapshot 等）。
     *
     * <p><b>26.2 口径</b>：{@code Minecraft#getVersionType()} 已移除，改用
     * {@code SharedConstants#getCurrentVersion()} 的 {@code stable()} 判定 ——
     * 正式版为 {@code release}，其余（快照 / 预发布）为 {@code snapshot}。</p>
     */
    public static String minecraftVersion() {
        try {
            return net.minecraft.SharedConstants.getCurrentVersion().stable() ? "release" : "snapshot";
        } catch (Exception e) {
            return "unknown";
        }
    }

    /** 离线模式默认名（Player 后接数字）视为测试账号，不参与统计。 */
    public static boolean fakeName(String name) {
        return name == null || name.matches("^Player\\d+$");
    }
}
