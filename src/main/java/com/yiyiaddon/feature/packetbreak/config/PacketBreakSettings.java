package com.yiyiaddon.feature.packetbreak.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.packetbreak.model.BreakMode;
import com.yiyiaddon.feature.packetbreak.model.EspStyle;
import com.yiyiaddon.feature.packetbreak.model.LabelStyle;
import com.yiyiaddon.feature.packetbreak.model.TargetMode;
import com.yiyiaddon.ui.render.world.ColorPresets;
import com.yiyiaddon.ui.render.world.EspColor;

import java.util.ArrayList;
import java.util.List;

/**
 * 发包秒破设置载体：21 项 / 4 组（目标选择 / 发包参数 / 防同步与反作弊 / 进度显示）。
 *
 * <p><b>逐字资产</b>：设置名、默认值、取值域、可见性条件、分组名全部来自旧项目
 * {@code tactical/packetbreak/PacketInstantBreak.java:111-272}；落盘键名直接用旧设置名本身
 * （与飞行绕过 / 服务器检测 / 发包防踢的做法一致），改一个键名都会让老档读不出来
 * （开发习惯第二十五章第 172-175 条）。</p>
 *
 * <p><b>框架适配（旧 → 新，逐条）</b></p>
 * <ol>
 *     <li><b>目标方块</b>：旧项目是 {@code BlockListSetting}（{@code List<Block>}），由旧框架序列化；
 *         本项目按既有 ESP 类模块的做法（{@code feature/mining/config/MiningSettings} /
 *         {@code feature/vision/config/VisionSettings}）改为<b>方块登记 ID 字符串名单</b>
 *         （{@code List<String>}），界面走通用选择器。判定处一律用
 *         {@code BuiltInRegistries.BLOCK.getValue(id)} 还原成方块比较，语义不变。</li>
 *     <li><b>五个颜色项</b>：旧项目是框架的 {@code SettingColor}；本项目统一用 {@link EspColor}
 *         承载（第 146 / 150 条：ESP 颜色只有一个入口），默认值取自 {@link ColorPresets} 预设色
 *         ——旧默认色 {@code (204,0,0)} 属红、{@code (0,204,0)} 属绿、{@code (255,255,255)} 属白，
 *         色义与透明度逐项照旧。落盘沿用 {@link EspColor} 的四键约定
 *         （{@code <设置名>Rgb / Alpha / Rainbow / RainbowSpeed}）。</li>
 *     <li><b>可见性条件</b>：旧设置的 {@code visible(...)} 由控制台设置页按「条件不满足就不把该行
 *         加入堆叠」等价实现（整页可重建，改开关立刻增删行，见 {@code PacketBreakSettingsPage}）。</li>
 *     <li><b>thread 可见性</b>：发包规则在网络线程被同步调用，会读 {@link #targetMode}，
 *         故该字段声明 {@code volatile}；其余字段只由主线程（每刻逻辑与设置页）读写，保持普通字段。</li>
 * </ol>
 */
public final class PacketBreakSettings {

    // ── 取值域（逐字 = 旧设置声明） ──

    /** 扫描半径域（旧 {@code IntSetting} 1~6） */
    public static final int RANGE_MIN = 1;
    public static final int RANGE_MAX = 6;

    /** 方块间隔域（旧 {@code IntSetting} 0~20） */
    public static final int DELAY_MIN = 0;
    public static final int DELAY_MAX = 20;

    // ── 颜色默认值（旧 SettingColor 字面量的色义映射，见类注释） ──

    /** {@code ColorPresets} 预设下标：0 = 红（旧 (204,0,0)） */
    private static final int PRESET_RED = 0;
    /** {@code ColorPresets} 预设下标：1 = 绿（旧 (0,204,0)） */
    private static final int PRESET_GREEN = 1;
    /** {@code ColorPresets} 预设下标：6 = 白（旧 (255,255,255)） */
    private static final int PRESET_WHITE = 6;

    /** 未完成方块面色默认不透明度（旧 {@code (204,0,0,10)} 的 alpha） */
    private static final int BUSY_SIDE_ALPHA = 10;
    /** 未完成方块线色默认不透明度（旧 {@code (204,0,0,255)} 的 alpha） */
    private static final int BUSY_LINE_ALPHA = 255;
    /** 可破坏方块面色默认不透明度（旧 {@code (0,204,0,10)} 的 alpha） */
    private static final int READY_SIDE_ALPHA = 10;
    /** 可破坏方块线色默认不透明度（旧 {@code (0,204,0,255)} 的 alpha） */
    private static final int READY_LINE_ALPHA = 255;
    /** 百分比颜色默认不透明度（旧 {@code (255,255,255,255)} 的 alpha） */
    private static final int PERCENT_ALPHA = 255;

    // ── 组① 目标选择 ──

    /** 瞄准破坏 / 范围自动（默认瞄准破坏；发包规则在网络线程读，故 volatile） */
    public volatile TargetMode targetMode = TargetMode.AIM;
    /** 极速卡点 / 原版速度（默认极速卡点） */
    public BreakMode breakMode = BreakMode.INSTANT;
    /** 范围自动模式的扫描半径（默认 4；仅范围自动时可见） */
    public int range = 4;
    /** 目标方块登记 ID 名单（默认空 = 挖所有可破坏方块；仅范围自动时可见） */
    public final List<String> targetBlocks = new ArrayList<>();

    // ── 组② 发包参数 ──

    /** 每个方块挖完确认后、开始下一块前的等待 tick（默认 1） */
    public int delay = 1;
    /** 挖掘时先发视角转向包（默认开） */
    public boolean rotate = true;
    /** 挖掘前自动切换到最快工具、挖完切回（默认开） */
    public boolean autoSwitch = true;
    /** 发包挖掘时同步挥动手臂（默认开） */
    public boolean swing = true;

    // ── 组③ 防同步与反作弊 ──

    /** 破坏结束后额外刷 ABORT 包掩盖裂纹（默认关） */
    public boolean obscureProgress;
    /** 破坏后额外补发 ABORT 包混淆时序（默认关） */
    public boolean bypassAnticheat;
    /** 服务器 TPS 过低时暂停发包（默认开） */
    public boolean respectLag = true;

    // ── 组④ 进度显示 ──

    /** 是否渲染正在发包挖掘的方块与进度（默认开） */
    public boolean render = true;
    /** 进度框渲染样式（默认线+面） */
    public EspStyle espStyle = EspStyle.BOTH;
    /** 方块框随进度向中心收缩（默认开） */
    public boolean shrinkProgress = true;
    /** 未完成方块的面色（旧 (204,0,0,10) → 预设红 + alpha 10） */
    public final EspColor sideColor = new EspColor();
    /** 未完成方块的线色（旧 (204,0,0,255) → 预设红 + alpha 255） */
    public final EspColor lineColor = new EspColor();
    /** 可破坏方块的面色（旧 (0,204,0,10) → 预设绿 + alpha 10） */
    public final EspColor readySideColor = new EspColor();
    /** 可破坏方块的线色（旧 (0,204,0,255) → 预设绿 + alpha 255） */
    public final EspColor readyLineColor = new EspColor();
    /** 是否在方块上方显示进度百分比标签（默认开） */
    public boolean showPercent = true;
    /** 百分比标签文字色（旧 (255,255,255,255) → 预设白 + alpha 255） */
    public final EspColor progressColor = new EspColor();
    /** 标签内容（默认仅百分比） */
    public LabelStyle labelStyle = LabelStyle.PERCENT;

    /** 构造期把五个颜色项置为旧默认值的等价预设色（{@link EspColor} 可变，不能在字段处写死） */
    public PacketBreakSettings() {
        sideColor.rgb(ColorPresets.rgb(PRESET_RED)).alpha(BUSY_SIDE_ALPHA);
        lineColor.rgb(ColorPresets.rgb(PRESET_RED)).alpha(BUSY_LINE_ALPHA);
        readySideColor.rgb(ColorPresets.rgb(PRESET_GREEN)).alpha(READY_SIDE_ALPHA);
        readyLineColor.rgb(ColorPresets.rgb(PRESET_GREEN)).alpha(READY_LINE_ALPHA);
        progressColor.rgb(ColorPresets.rgb(PRESET_WHITE)).alpha(PERCENT_ALPHA);
    }

    // ── 编解码（键名与旧设置名逐字对应） ──

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回取值域 */
    public void load(JsonObject json) {
        if (json == null) return;

        targetMode = enumOf(json, PacketBreakTexts.NAME_TARGET_MODE, TargetMode.class, targetMode);
        breakMode = enumOf(json, PacketBreakTexts.NAME_BREAK_MODE, BreakMode.class, breakMode);
        range = clamp(intOf(json, PacketBreakTexts.NAME_RANGE, range), RANGE_MIN, RANGE_MAX);
        replaceAll(targetBlocks, listOf(json, PacketBreakTexts.NAME_TARGET_BLOCKS));

        delay = clamp(intOf(json, PacketBreakTexts.NAME_DELAY, delay), DELAY_MIN, DELAY_MAX);
        rotate = boolOf(json, PacketBreakTexts.NAME_ROTATE, rotate);
        autoSwitch = boolOf(json, PacketBreakTexts.NAME_AUTO_SWITCH, autoSwitch);
        swing = boolOf(json, PacketBreakTexts.NAME_SWING, swing);

        obscureProgress = boolOf(json, PacketBreakTexts.NAME_OBSCURE_PROGRESS, obscureProgress);
        bypassAnticheat = boolOf(json, PacketBreakTexts.NAME_BYPASS_ANTICHEAT, bypassAnticheat);
        respectLag = boolOf(json, PacketBreakTexts.NAME_RESPECT_LAG, respectLag);

        render = boolOf(json, PacketBreakTexts.NAME_RENDER, render);
        espStyle = enumOf(json, PacketBreakTexts.NAME_ESP_STYLE, EspStyle.class, espStyle);
        shrinkProgress = boolOf(json, PacketBreakTexts.NAME_SHRINK_PROGRESS, shrinkProgress);
        sideColor.load(json, PacketBreakTexts.NAME_SIDE_COLOR);
        lineColor.load(json, PacketBreakTexts.NAME_LINE_COLOR);
        readySideColor.load(json, PacketBreakTexts.NAME_READY_SIDE_COLOR);
        readyLineColor.load(json, PacketBreakTexts.NAME_READY_LINE_COLOR);
        showPercent = boolOf(json, PacketBreakTexts.NAME_SHOW_PERCENT, showPercent);
        progressColor.load(json, PacketBreakTexts.NAME_PROGRESS_COLOR);
        labelStyle = enumOf(json, PacketBreakTexts.NAME_LABEL_STYLE, LabelStyle.class, labelStyle);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键，两端必须对称） */
    public void save(JsonObject json) {
        json.addProperty(PacketBreakTexts.NAME_TARGET_MODE, targetMode.name());
        json.addProperty(PacketBreakTexts.NAME_BREAK_MODE, breakMode.name());
        json.addProperty(PacketBreakTexts.NAME_RANGE, range);
        json.add(PacketBreakTexts.NAME_TARGET_BLOCKS, listToJson(targetBlocks));

        json.addProperty(PacketBreakTexts.NAME_DELAY, delay);
        json.addProperty(PacketBreakTexts.NAME_ROTATE, rotate);
        json.addProperty(PacketBreakTexts.NAME_AUTO_SWITCH, autoSwitch);
        json.addProperty(PacketBreakTexts.NAME_SWING, swing);

        json.addProperty(PacketBreakTexts.NAME_OBSCURE_PROGRESS, obscureProgress);
        json.addProperty(PacketBreakTexts.NAME_BYPASS_ANTICHEAT, bypassAnticheat);
        json.addProperty(PacketBreakTexts.NAME_RESPECT_LAG, respectLag);

        json.addProperty(PacketBreakTexts.NAME_RENDER, render);
        json.addProperty(PacketBreakTexts.NAME_ESP_STYLE, espStyle.name());
        json.addProperty(PacketBreakTexts.NAME_SHRINK_PROGRESS, shrinkProgress);
        sideColor.save(json, PacketBreakTexts.NAME_SIDE_COLOR);
        lineColor.save(json, PacketBreakTexts.NAME_LINE_COLOR);
        readySideColor.save(json, PacketBreakTexts.NAME_READY_SIDE_COLOR);
        readyLineColor.save(json, PacketBreakTexts.NAME_READY_LINE_COLOR);
        json.addProperty(PacketBreakTexts.NAME_SHOW_PERCENT, showPercent);
        progressColor.save(json, PacketBreakTexts.NAME_PROGRESS_COLOR);
        json.addProperty(PacketBreakTexts.NAME_LABEL_STYLE, labelStyle.name());
    }

    // ── 原语 ──

    /** 名单整表替换（保持同一实例，界面侧持有的引用不失效） */
    private static void replaceAll(List<String> target, List<String> source) {
        target.clear();
        target.addAll(source);
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsInt() : fallback;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /** 读枚举：非法名字一律回落默认值，不猜 */
    private static <E extends Enum<E>> E enumOf(JsonObject json, String key, Class<E> type, E fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive()) return fallback;
        try {
            return Enum.valueOf(type, element.getAsString());
        } catch (IllegalArgumentException ignored) {
            return fallback;
        }
    }

    /** 读字符串名单：非数组或非字符串项一律跳过，不猜 */
    private static List<String> listOf(JsonObject json, String key) {
        List<String> values = new ArrayList<>();
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonArray()) return values;
        for (JsonElement item : element.getAsJsonArray()) {
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
}
