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
    public static String uiTheme = "white";
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

    // —— UI 测试值（仅用于验证控件行为，不参与任何游戏逻辑）——
    public static boolean testToggle = true;
    public static double testSlider = 35.0;
    public static int testCycle = 0;
    public static String testText = "yiyiaddon";
    public static int testColorPrimary = 0xFF6D8CFF;
    public static int testColorSecondary = 0xFFFF6B6B;
    public static boolean testColorSplit = false;
    public static String testKeybindAction = "测试快捷键";
    public static boolean testCondition = true;
    public static int testCounter = 0;

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
            testToggle = bool(json, "testToggle", testToggle);
            testSlider = number(json, "testSlider", testSlider);
            testCycle = integer(json, "testCycle", testCycle);
            testText = string(json, "testText", testText);
            testColorPrimary = integer(json, "testColorPrimary", testColorPrimary);
            testColorSecondary = integer(json, "testColorSecondary", testColorSecondary);
            testColorSplit = bool(json, "testColorSplit", testColorSplit);
            testKeybindAction = string(json, "testKeybindAction", testKeybindAction);
            testCondition = bool(json, "testCondition", testCondition);
            testCounter = integer(json, "testCounter", testCounter);
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
        json.addProperty("testToggle", testToggle);
        json.addProperty("testSlider", testSlider);
        json.addProperty("testCycle", testCycle);
        json.addProperty("testText", testText);
        json.addProperty("testColorPrimary", testColorPrimary);
        json.addProperty("testColorSecondary", testColorSecondary);
        json.addProperty("testColorSplit", testColorSplit);
        json.addProperty("testKeybindAction", testKeybindAction);
        json.addProperty("testCondition", testCondition);
        json.addProperty("testCounter", testCounter);
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

    private static double number(JsonObject json, String key, double fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) return fallback;
        try {
            return element.getAsDouble();
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
