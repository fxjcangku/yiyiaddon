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

    /** 本地正版判定：仅作为展示兜底，最终以后端返回的 {@code is_premium} 为准。 */
    public static boolean premium() {
        return xuid() != null;
    }

    /** 本模组版本号，取 fabric.mod.json 中的 version。 */
    public static String version() {
        return FabricLoader.getInstance().getModContainer(MOD_ID)
                .map(container -> container.getMetadata().getVersion().getFriendlyString())
                .orElse("unknown");
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
