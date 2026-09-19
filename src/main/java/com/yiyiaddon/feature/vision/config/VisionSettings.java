package com.yiyiaddon.feature.vision.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.ui.render.world.ColorPresets;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.ShapeMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 透视模块设置项的数据载体：方块模式与实体模式各一套（用户口径「两个模式可以同时开启互不干扰」）。
 *
 * <p><b>默认值口径</b>：两个模式都默认关闭、目标名单都为空（用户 2026-09-18 拍板
 * 「默认什么都不选择用户自己选」）；范围默认 64 格（用户 2026-09-18 拍板「默认范围远点 我要看见底下的
 * 方块 实体」）；框默认开、射线默认关（92 号 D-16-04）。</p>
 *
 * <p><b>颜色</b>：走 {@link ColorPresets} 预设取值再各自给透明度，不在本类写 RGB 字面量以外的来源
 * （第 146 条）；对象可变，因此默认色只能在构造器里设，不能写在字段处。</p>
 *
 * <p><b>落盘</b>：本类只做字段编解码，读写由 {@code ModuleManager.saveSettings/loadSettings} 统一负责
 * （第 173 条），键名与 {@link #load} / {@link #save} 严格一一对应（第 174 条）。</p>
 */
public final class VisionSettings {

    /** 范围取值域与默认值：方块与实体一致（92 号 §5 / D-16-10） */
    public static final int RANGE_MIN = 16;
    public static final int RANGE_MAX = 128;
    public static final int RANGE_DEFAULT = 64;

    /** 颜色默认不透明度：描边够浓、填充不糊住本体 */
    private static final int DEFAULT_ALPHA = 200;

    /** 方块模式默认色：预设 3 号 = 青（与管理员检测的红、挖矿的配色区分开） */
    private static final int BLOCK_PRESET = 3;
    /** 实体模式默认色：预设 0 号 = 红 */
    private static final int ENTITY_PRESET = 0;

    // ── 方块模式 ──

    /** 方块透视总开关（关闭时方块扫描器不再推进） */
    public boolean blockEnabled;
    /** 目标方块登记 ID 名单（空 = 不画） */
    public final List<String> blockTargets = new ArrayList<>();
    /** 水平扫描半径（格）；竖直方向固定 ±{@code BlockTargetScanner.VERTICAL_REACH} */
    public int blockRange = RANGE_DEFAULT;
    /** 画框 */
    public boolean blockBox = true;
    /** 画射线 */
    public boolean blockTracer;
    /** 框样式（线框 / 面 / 两者），默认线框最清爽 */
    public ShapeMode blockShapeMode = ShapeMode.Lines;
    /** 方块框与射线颜色 */
    public final EspColor blockColor = new EspColor();

    // ── 实体模式 ──

    /** 实体透视总开关 */
    public boolean entityEnabled;
    /** 目标实体登记 ID 名单（空 = 不画） */
    public final List<String> entityTargets = new ArrayList<>();
    /** 水平扫描半径（格）；竖直方向不限制 */
    public int entityRange = RANGE_DEFAULT;
    /** 画框 */
    public boolean entityBox = true;
    /** 画射线 */
    public boolean entityTracer;
    /** 框样式（线框 / 面 / 两者） */
    public ShapeMode entityShapeMode = ShapeMode.Lines;
    /** 实体框与射线颜色 */
    public final EspColor entityColor = new EspColor();

    /** 构造期把颜色默认值置为预设值（{@link EspColor} 是可变对象，不能在字段处写死） */
    public VisionSettings() {
        blockColor.rgb(ColorPresets.rgb(BLOCK_PRESET)).alpha(DEFAULT_ALPHA);
        entityColor.rgb(ColorPresets.rgb(ENTITY_PRESET)).alpha(DEFAULT_ALPHA);
    }

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回取值域 */
    public void load(JsonObject json) {
        if (json == null) return;

        blockEnabled = boolOf(json, "blockEnabled", blockEnabled);
        replaceAll(blockTargets, listOf(json, "blockTargets"));
        blockRange = clamp(intOf(json, "blockRange", blockRange), RANGE_MIN, RANGE_MAX);
        blockBox = boolOf(json, "blockBox", blockBox);
        blockTracer = boolOf(json, "blockTracer", blockTracer);
        blockShapeMode = shapeOf(json, "blockShapeMode", blockShapeMode);
        loadColor(json, "blockColor", blockColor, ColorPresets.rgb(BLOCK_PRESET), DEFAULT_ALPHA);

        entityEnabled = boolOf(json, "entityEnabled", entityEnabled);
        replaceAll(entityTargets, listOf(json, "entityTargets"));
        entityRange = clamp(intOf(json, "entityRange", entityRange), RANGE_MIN, RANGE_MAX);
        entityBox = boolOf(json, "entityBox", entityBox);
        entityTracer = boolOf(json, "entityTracer", entityTracer);
        entityShapeMode = shapeOf(json, "entityShapeMode", entityShapeMode);
        loadColor(json, "entityColor", entityColor, ColorPresets.rgb(ENTITY_PRESET), DEFAULT_ALPHA);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键，两端必须对称） */
    public void save(JsonObject json) {
        json.addProperty("blockEnabled", blockEnabled);
        json.add("blockTargets", listToJson(blockTargets));
        json.addProperty("blockRange", blockRange);
        json.addProperty("blockBox", blockBox);
        json.addProperty("blockTracer", blockTracer);
        json.addProperty("blockShapeMode", blockShapeMode.name());
        saveColor(json, "blockColor", blockColor);

        json.addProperty("entityEnabled", entityEnabled);
        json.add("entityTargets", listToJson(entityTargets));
        json.addProperty("entityRange", entityRange);
        json.addProperty("entityBox", entityBox);
        json.addProperty("entityTracer", entityTracer);
        json.addProperty("entityShapeMode", entityShapeMode.name());
        saveColor(json, "entityColor", entityColor);
    }

    // ── 原语 ──

    /** 名单整表替换（保持同一实例，界面侧持有的引用不失效） */
    private static void replaceAll(List<String> target, List<String> source) {
        target.clear();
        target.addAll(source);
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        JsonElement e = json.get(key);
        return e != null && e.isJsonPrimitive() ? e.getAsBoolean() : fallback;
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        JsonElement e = json.get(key);
        return e != null && e.isJsonPrimitive() ? e.getAsInt() : fallback;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static ShapeMode shapeOf(JsonObject json, String key, ShapeMode fallback) {
        JsonElement e = json.get(key);
        if (e == null || !e.isJsonPrimitive()) return fallback;
        try {
            return ShapeMode.valueOf(e.getAsString());
        } catch (IllegalArgumentException ignored) {
            return fallback;
        }
    }

    /** 读字符串名单：非数组或非字符串项一律跳过，不猜 */
    private static List<String> listOf(JsonObject json, String key) {
        List<String> values = new ArrayList<>();
        JsonElement e = json.get(key);
        if (e == null || !e.isJsonArray()) return values;
        for (JsonElement item : e.getAsJsonArray()) {
            if (item != null && item.isJsonPrimitive()) values.add(item.getAsString());
        }
        return values;
    }

    private static JsonArray listToJson(List<String> values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            if (value != null && !value.isBlank()) array.add(value);
        }
        return array;
    }

    /** 颜色五键：rgb / alpha / 彩虹开关 / 彩虹速度（与自动农场、水源显示同一套键口径） */
    private static void loadColor(JsonObject json, String key, EspColor color, int fallbackRgb, int fallbackAlpha) {
        color.rgb(intOf(json, key + "Rgb", fallbackRgb));
        color.alpha(clamp(intOf(json, key + "Alpha", fallbackAlpha), 0, 255));
        color.rainbow(boolOf(json, key + "Rainbow", false));
        JsonElement speed = json.get(key + "RainbowSpeed");
        if (speed != null && speed.isJsonPrimitive()) color.rainbowSpeed(speed.getAsDouble());
    }

    private static void saveColor(JsonObject json, String key, EspColor color) {
        json.addProperty(key + "Rgb", color.rgb());
        json.addProperty(key + "Alpha", color.alpha());
        json.addProperty(key + "Rainbow", color.rainbow());
        json.addProperty(key + "RainbowSpeed", color.rainbowSpeed());
    }
}
