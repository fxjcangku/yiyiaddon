package com.yiyiaddon.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * yiyiaddon 最小配置系统。
 *
 * <p>本阶段只保存 UI 框架自用的少量状态：主题、GUI 缩放、面板模糊、GUI 快捷键、
 * UI 测试值，以及 Baritone 汉化开关。不承载任何业务模块配置。</p>
 *
 * <p>全部字段以显式 {@link JsonObject} 读写实现，不依赖反射序列化：
 * 任何字段缺失、类型不符或文件损坏都会静默回落到默认值，不会阻断客户端启动。</p>
 */
public final class AddonConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "yiyiaddon.json";

    // —— UI 外观 ——
    public static String uiTheme = "apple_dark";
    public static int uiScale = 1;
    public static boolean panelBlur = true;
    public static float blurStrength = 0.6f;
    public static int blurTint = 0x50101014;
    public static float scrollSpeed = 1.0f;

    // —— 快捷键 ——
    /** {@code id=key} 以 {@code ;} 分隔的序列化形式，由 ModuleKeybindManager 读写。 */
    public static String moduleKeybinds = "";

    // —— Baritone 汉化 ——
    public static boolean baritoneChinese = true;

    private static boolean loaded;

    private AddonConfig() {
    }

    private static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
    }

    /** 幂等载入：只执行一次，失败时保持默认值。 */
    public static void load() {
        if (loaded) return;
        loaded = true;
        Path file = path();
        if (!Files.isRegularFile(file)) return;
        try {
            String raw = Files.readString(file, StandardCharsets.UTF_8);
            JsonElement root = JsonParser.parseString(raw);
            if (!root.isJsonObject()) return;
            JsonObject json = root.getAsJsonObject();
            uiTheme = string(json, "uiTheme", uiTheme);
            uiScale = integer(json, "uiScale", uiScale);
            panelBlur = bool(json, "panelBlur", panelBlur);
            blurStrength = number(json, "blurStrength", blurStrength);
            blurTint = integer(json, "blurTint", blurTint);
            scrollSpeed = number(json, "scrollSpeed", scrollSpeed);
            moduleKeybinds = string(json, "moduleKeybinds", moduleKeybinds);
            baritoneChinese = bool(json, "baritoneChinese", baritoneChinese);
        } catch (Exception ignored) {
            // 配置文件损坏：保持默认值，不阻断启动。
        }
    }

    /** 写回配置文件；失败时静默忽略。 */
    public static void save() {
        JsonObject json = new JsonObject();
        json.addProperty("uiTheme", uiTheme);
        json.addProperty("uiScale", uiScale);
        json.addProperty("panelBlur", panelBlur);
        json.addProperty("blurStrength", blurStrength);
        json.addProperty("blurTint", blurTint);
        json.addProperty("scrollSpeed", scrollSpeed);
        json.addProperty("moduleKeybinds", moduleKeybinds);
        json.addProperty("baritoneChinese", baritoneChinese);
        try {
            Path file = path();
            Files.createDirectories(file.getParent());
            Files.writeString(file, GSON.toJson(json), StandardCharsets.UTF_8);
        } catch (IOException ignored) {
            // 磁盘不可写：忽略。
        }
    }

    /** 面板模糊后叠加的底色（含 alpha）。 */
    public static int blurTintColor() {
        return blurTint;
    }

    private static String string(JsonObject json, String key, String fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsString() : fallback;
    }

    private static int integer(JsonObject json, String key, int fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) return fallback;
        try {
            return element.getAsInt();
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private static float number(JsonObject json, String key, float fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) return fallback;
        try {
            return element.getAsFloat();
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private static boolean bool(JsonObject json, String key, boolean fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()
                ? element.getAsBoolean()
                : fallback;
    }
}
