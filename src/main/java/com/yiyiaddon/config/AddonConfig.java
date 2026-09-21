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
 * UI 测试值、Baritone 汉化开关，以及客户端指令前缀。不承载任何业务模块配置。</p>
 *
 * <p>全部字段以显式 {@link JsonObject} 读写实现，不依赖反射序列化：
 * 任何字段缺失、类型不符或文件损坏都会静默回落到默认值，不会阻断客户端启动。</p>
 */
public final class AddonConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "yiyiaddon.json";

    // —— UI 外观 ——
    public static String uiTheme = "deep_gray";
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

    // —— 客户端指令 ——
    /** 客户端指令前缀的原始输入值；合法性与回落由 {@code CommandManager.normalizePrefix} 负责。 */
    public static String commandPrefix = ".";

    // —— 模块中心 ——
    /** 收藏（顶部「常用」区与首页「常用模块」）的模块 id，以 {@code ;} 分隔；由模块行的星标按钮读写。 */
    public static String favoriteModules = "";
    /**
     * 模块中心分类的自定义顺序：分类 id 以 {@code ;} 分隔；空 = 按注册表权重。
     *
     * <p>由分类头上的「上移 / 下移」按钮写入（用户 2026-09-21 要求分类顺序自己调）。
     * 只影响<b>模块中心的显示顺序</b>：注册表权重仍是默认顺序，文件里没记到的分类按权重补在后面，
     * 因此版本更新新增分类时不会丢、也不会打乱玩家已经排好的顺序。</p>
     */
    public static String moduleCategoryOrder = "";

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
            commandPrefix = string(json, "commandPrefix", commandPrefix);
            favoriteModules = string(json, "favoriteModules", favoriteModules);
            moduleCategoryOrder = string(json, "moduleCategoryOrder", moduleCategoryOrder);
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
        json.addProperty("commandPrefix", commandPrefix);
        json.addProperty("favoriteModules", favoriteModules);
        json.addProperty("moduleCategoryOrder", moduleCategoryOrder);
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
