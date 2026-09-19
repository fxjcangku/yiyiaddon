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
     * <p>两条判据：</p>
     * <ol>
     *   <li>{@code xuid} 非空 —— 原版正版会话一定会带 XUID 属性，离线会话恒为空；</li>
     *   <li>开发环境加载了 DevAuth Neo —— 它接管会话时只提供 ACCESS_TOKEN / UUID / USERNAME
     *       （见其 {@code MicrosoftAuthProvider} 的令牌集合），<b>不会填 XUID</b>，
     *       所以开发端即使真的登录了正版，第 1 条也永远不成立。</li>
     * </ol>
     * <p>DevAuth 只在开发环境存在（{@code localRuntime} 依赖，不进发布产物），
     * 且它只在真正完成微软登录后才接管会话，因此这条兜底不会把离线会话判成正版。</p>
     */
    public static boolean premium() {
        if (xuid() != null) return true;
        return FabricLoader.getInstance().isModLoaded(DEV_AUTH_MOD_ID);
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

    /** Minecraft 版本类型（release / snapshot 等）。 */
    public static String minecraftVersion() {
        try {
            return Minecraft.getInstance().getVersionType();
        } catch (Exception e) {
            return "unknown";
        }
    }

    /** 离线模式默认名（Player 后接数字）视为测试账号，不参与统计。 */
    public static boolean fakeName(String name) {
        return name == null || name.matches("^Player\\d+$");
    }
}
