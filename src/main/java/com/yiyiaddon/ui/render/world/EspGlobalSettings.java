package com.yiyiaddon.ui.render.world;

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
 * ESP 全局设置：作用于<b>所有</b>走 {@link EspRenderer} 的绘制层（星露谷点位、自动箱子、ESP 测试项…）。
 *
 * <p><b>与各模块自己的设置是什么关系：</b>模块自己管的是「画什么、什么颜色、什么模式」；这里管的是
 * <b>全局排版与开销</b>——线宽、不透明度、可见距离、字号、单帧图元上限。两项互不覆盖：
 * 本类不改任何 {@link EspColor}，也不替模块决定画不画某一类目标，只是在绘制瞬间统一缩放与裁剪。
 * 因此关掉这里的一项不会让模块「记不住自己的设置」。</p>
 *
 * <p><b>默认值一律等于「什么也不改」</b>：倍率 1.0、覆盖项跟随各模块、距离不限、图元不限。
 * 打开即恢复到未启用本设置时的观感，这样即使本设置出问题也不会凭空改变既有 ESP 的样子。</p>
 *
 * <p>写入 {@code config/yiyiaddon/esp-global.json}；文件缺失 / 损坏一律回落默认值。</p>
 */
public final class EspGlobalSettings {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "esp-global.json";
    private static final String DIR_NAME = "yiyiaddon";

    /** 全局渲染模式覆盖。 */
    public enum ModeOverride {
        /** 跟随各模块自己设的线框 / 面 / 两者 */
        FOLLOW("跟随各模块"),
        /** 全部只画线框 */
        LINES("全部线框"),
        /** 全部只画面 */
        SIDES("全部面"),
        /** 全部线框 + 面 */
        BOTH("全部两者");

        private final String label;

        ModeOverride(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }

        public static String[] labels() {
            ModeOverride[] values = values();
            String[] out = new String[values.length];
            for (int i = 0; i < values.length; i++) out[i] = values[i].label;
            return out;
        }
    }

    /** 全局遮挡（透视）覆盖。 */
    public enum OcclusionOverride {
        /** 跟随各模块；模块没开遮挡时就是透视 */
        FOLLOW("跟随各模块"),
        /** 全部透视：穿墙也画 */
        XRAY("全部透视"),
        /** 全部遮挡：被方块挡住就不画 */
        OCCLUDE("全部遮挡");

        private final String label;

        OcclusionOverride(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }

        public static String[] labels() {
            OcclusionOverride[] values = values();
            String[] out = new String[values.length];
            for (int i = 0; i < values.length; i++) out[i] = values[i].label;
            return out;
        }
    }

    // ── 取值域（界面级与服务级共用同一份） ──

    public static final double THICKNESS_MIN = 0.5;
    public static final double THICKNESS_MAX = 3.0;
    public static final double ALPHA_MIN = 0.1;
    public static final double ALPHA_MAX = 1.0;
    public static final int DISTANCE_MIN = 0;
    public static final int DISTANCE_MAX = 256;
    public static final double TEXT_SCALE_MIN = 0.5;
    public static final double TEXT_SCALE_MAX = 2.5;
    public static final int BUDGET_MIN = 0;
    public static final int BUDGET_MAX = 4096;

    private static volatile EspGlobalSettings instance;

    // ── 设置项 ──

    private boolean enabled = true;
    private float thicknessScale = 1.0f;
    private ModeOverride modeOverride = ModeOverride.FOLLOW;
    private OcclusionOverride occlusionOverride = OcclusionOverride.FOLLOW;
    private float alphaScale = 1.0f;
    /** 0 = 不限 */
    private int maxDistance = 0;
    private boolean fade;
    /** 淡出起点：这个距离以内完全不透明；未开「最远距离」时按 64 格为终点 */
    private int fadeStart = 24;
    private float textScale = 1.0f;
    private boolean textPlate = true;
    /** 0 = 不限 */
    private int primitiveBudget = 0;
    private boolean hideSelfFirstPerson = true;
    private boolean hideSelfThirdPerson;

    private EspGlobalSettings() {
    }

    /** 全局唯一实例；首次访问时从磁盘读取一次。 */
    public static EspGlobalSettings get() {
        EspGlobalSettings local = instance;
        if (local != null) return local;
        synchronized (EspGlobalSettings.class) {
            if (instance == null) {
                EspGlobalSettings created = new EspGlobalSettings();
                created.loadFromDisk();
                instance = created;
            }
            return instance;
        }
    }

    // ── 取值 ──

    /** ESP 总开关：关掉后所有 ESP 绘制层跳过本帧绘制。 */
    public boolean enabled() {
        return enabled;
    }

    /** 线宽倍率：所有线框的粗细统一乘这个系数。 */
    public float thickness(float thickness) {
        return thickness * thicknessScale;
    }

    /** 渲染模式：覆盖项为「跟随各模块」时原样返回。 */
    public ShapeMode mode(ShapeMode mode) {
        return switch (modeOverride) {
            case LINES -> ShapeMode.Lines;
            case SIDES -> ShapeMode.Sides;
            case BOTH -> ShapeMode.Both;
            case FOLLOW -> mode;
        };
    }

    /** 遮挡（透视）：覆盖项为「跟随各模块」时原样返回。 */
    public boolean occlusion(boolean occlusion) {
        return switch (occlusionOverride) {
            case XRAY -> false;
            case OCCLUDE -> true;
            case FOLLOW -> occlusion;
        };
    }

    /** 不透明度倍率：所有 ESP 颜色的 alpha 统一乘这个系数。 */
    public float alphaScale() {
        return alphaScale;
    }

    /** 最远显示距离（格）；0 = 不限。 */
    public int maxDistance() {
        return maxDistance;
    }

    public boolean fade() {
        return fade;
    }

    public int fadeStart() {
        return fadeStart;
    }

    /** 文字大小倍率：所有 ESP 字牌的字号统一乘这个系数。 */
    public float textScale() {
        return textScale;
    }

    /** 字牌是否压深色底板（关掉只剩纯色字，亮背景下更难读）。 */
    public boolean textPlate() {
        return textPlate;
    }

    /** 每帧最大图元数；0 = 不限。超出后本帧不再画新图元（成片 ESP 拖垮帧率时的保底）。 */
    public int primitiveBudget() {
        return primitiveBudget;
    }

    /** 是否隐藏自己：第一人称 / 第三人称各一项。 */
    public boolean hideSelf(boolean firstPerson) {
        return firstPerson ? hideSelfFirstPerson : hideSelfThirdPerson;
    }

    // ── 写入（界面调用后立即落盘） ──

    public void setEnabled(boolean value) {
        enabled = value;
        save();
    }

    public void setThicknessScale(double value) {
        thicknessScale = (float) clamp(value, THICKNESS_MIN, THICKNESS_MAX);
        save();
    }

    public void setModeOverride(ModeOverride value) {
        modeOverride = value == null ? ModeOverride.FOLLOW : value;
        save();
    }

    public void setOcclusionOverride(OcclusionOverride value) {
        occlusionOverride = value == null ? OcclusionOverride.FOLLOW : value;
        save();
    }

    public void setAlphaScale(double value) {
        alphaScale = (float) clamp(value, ALPHA_MIN, ALPHA_MAX);
        save();
    }

    public void setMaxDistance(double value) {
        maxDistance = (int) Math.round(clamp(value, DISTANCE_MIN, DISTANCE_MAX));
        save();
    }

    public void setFade(boolean value) {
        fade = value;
        save();
    }

    public void setFadeStart(double value) {
        fadeStart = (int) Math.round(clamp(value, DISTANCE_MIN, DISTANCE_MAX));
        save();
    }

    public void setTextScale(double value) {
        textScale = (float) clamp(value, TEXT_SCALE_MIN, TEXT_SCALE_MAX);
        save();
    }

    public void setTextPlate(boolean value) {
        textPlate = value;
        save();
    }

    public void setPrimitiveBudget(double value) {
        primitiveBudget = (int) Math.round(clamp(value, BUDGET_MIN, BUDGET_MAX));
        save();
    }

    public void setHideSelfFirstPerson(boolean value) {
        hideSelfFirstPerson = value;
        save();
    }

    public void setHideSelfThirdPerson(boolean value) {
        hideSelfThirdPerson = value;
        save();
    }

    // ── 取值器（界面读当前值） ──

    public double thicknessScale() {
        return thicknessScale;
    }

    public ModeOverride modeOverride() {
        return modeOverride;
    }

    public OcclusionOverride occlusionOverride() {
        return occlusionOverride;
    }

    /** 第一人称是否隐藏自己。 */
    public boolean hideSelfFirstPerson() {
        return hideSelfFirstPerson;
    }

    /** 第三人称是否隐藏自己。 */
    public boolean hideSelfThirdPerson() {
        return hideSelfThirdPerson;
    }

    // ── 持久化 ──

    private static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve(DIR_NAME).resolve(FILE_NAME);
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private void loadFromDisk() {
        Path file = path();
        if (!Files.isRegularFile(file)) return;
        try {
            JsonElement root = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8));
            if (!root.isJsonObject()) return;
            JsonObject json = root.getAsJsonObject();
            enabled = boolOf(json, "enabled", enabled);
            thicknessScale = (float) clamp(numberOf(json, "thicknessScale", thicknessScale),
                THICKNESS_MIN, THICKNESS_MAX);
            modeOverride = modeOf(json, "modeOverride", modeOverride);
            occlusionOverride = occlusionOf(json, "occlusionOverride", occlusionOverride);
            alphaScale = (float) clamp(numberOf(json, "alphaScale", alphaScale), ALPHA_MIN, ALPHA_MAX);
            maxDistance = (int) Math.round(clamp(numberOf(json, "maxDistance", maxDistance),
                DISTANCE_MIN, DISTANCE_MAX));
            fade = boolOf(json, "fade", fade);
            fadeStart = (int) Math.round(clamp(numberOf(json, "fadeStart", fadeStart),
                DISTANCE_MIN, DISTANCE_MAX));
            textScale = (float) clamp(numberOf(json, "textScale", textScale), TEXT_SCALE_MIN, TEXT_SCALE_MAX);
            textPlate = boolOf(json, "textPlate", textPlate);
            primitiveBudget = (int) Math.round(clamp(numberOf(json, "primitiveBudget", primitiveBudget),
                BUDGET_MIN, BUDGET_MAX));
            hideSelfFirstPerson = boolOf(json, "hideSelfFirstPerson", hideSelfFirstPerson);
            hideSelfThirdPerson = boolOf(json, "hideSelfThirdPerson", hideSelfThirdPerson);
        } catch (Exception ignored) {
            // 文件损坏 / 不可读：保持默认值，绝不阻断渲染
        }
    }

    /** 写回配置文件；失败静默（下次改动再试）。 */
    private void save() {
        JsonObject json = new JsonObject();
        json.addProperty("enabled", enabled);
        json.addProperty("thicknessScale", thicknessScale);
        json.addProperty("modeOverride", modeOverride.name());
        json.addProperty("occlusionOverride", occlusionOverride.name());
        json.addProperty("alphaScale", alphaScale);
        json.addProperty("maxDistance", maxDistance);
        json.addProperty("fade", fade);
        json.addProperty("fadeStart", fadeStart);
        json.addProperty("textScale", textScale);
        json.addProperty("textPlate", textPlate);
        json.addProperty("primitiveBudget", primitiveBudget);
        json.addProperty("hideSelfFirstPerson", hideSelfFirstPerson);
        json.addProperty("hideSelfThirdPerson", hideSelfThirdPerson);
        try {
            Path file = path();
            Files.createDirectories(file.getParent());
            Files.writeString(file, GSON.toJson(json), StandardCharsets.UTF_8);
        } catch (IOException ignored) {
            // 磁盘不可写：忽略
        }
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()
            ? element.getAsBoolean() : fallback;
    }

    private static double numberOf(JsonObject json, String key, double fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) {
            return fallback;
        }
        try {
            return element.getAsDouble();
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private static ModeOverride modeOf(JsonObject json, String key, ModeOverride fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive()) return fallback;
        try {
            return ModeOverride.valueOf(element.getAsString());
        } catch (IllegalArgumentException e) {
            return fallback;
        }
    }

    private static OcclusionOverride occlusionOf(JsonObject json, String key, OcclusionOverride fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive()) return fallback;
        try {
            return OcclusionOverride.valueOf(element.getAsString());
        } catch (IllegalArgumentException e) {
            return fallback;
        }
    }
}
