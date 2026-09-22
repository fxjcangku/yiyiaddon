package com.yiyiaddon.feature.bonemeal.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.bonemeal.model.TriggerMode;
import com.yiyiaddon.ui.render.world.ColorPresets;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.ShapeMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动骨粉设置载体：21 项 / 4 组（基础参数 / 目标方块 / 防作弊绕过 / ESP渲染）。
 *
 * <p><b>逐字资产</b>：设置名、默认值、取值域、可见性条件、分组全部来自旧项目
 * {@code bonemeal/AutoBoneMeal.java:59-243}；落盘键名直接用旧设置名本身
 * （与发包秒破 / 飞行绕过 / 传送的做法一致），改一个键名都会让老档读不出来
 * （开发习惯第二十五章第 172-175 条）。</p>
 *
 * <p><b>框架适配（旧 → 新，逐条）</b></p>
 * <ol>
 *     <li><b>五组目标方块</b>：旧项目是 {@code BlockListSetting}（{@code List<Block>}），由旧框架序列化，
 *         候选窗口带 {@code filter(block -> block instanceof BonemealableBlock)}；本项目按既有 ESP 类模块
 *         的做法（{@code feature/packetbreak/config/PacketBreakSettings} /
 *         {@code feature/vision/config/VisionSettings}）改为<b>方块登记 ID 字符串名单</b>
 *         （{@code List<String>}），界面走通用选择器。判定处一律用
 *         {@code BuiltInRegistries.BLOCK.getValue(id)} 还原成方块比较，语义不变；
 *         旧 filter 的等价物是选择器候选表的「只列 {@code BonemealableBlock}」过滤，
 *         见 {@code ui/BonemealSelectors}。</li>
 *     <li><b>两个颜色项</b>：旧项目是框架的 {@code SettingColor}；本项目统一用 {@link EspColor}
 *         承载（第 146 / 150 条：ESP 颜色只有一个入口），默认值取自 {@link ColorPresets} 预设色
 *         ——旧默认色 {@code (0,255,100)} 属绿，线框 alpha 255 / 填充 alpha 45 逐项照旧。
 *         落盘沿用 {@link EspColor} 的四键约定（{@code <设置名>Rgb / Alpha / Rainbow / RainbowSpeed}）。</li>
 *     <li><b>形状模式</b>：旧项目是旧框架的 {@code ShapeMode}，本项目用自研
 *         {@link ShapeMode}（标签「线框 / 面 / 两者」逐字取自旧项目 {@code stardew/render/EspShapeMode}，
 *         第 143 条）。两者枚举名同为 {@code Lines / Sides / Both}，因此按 {@code name()} 存读，
 *         旧存档里的形状模式仍能读回。</li>
 *     <li><b>「无上限」的两个数字项</b>：旧 {@code IntSetting} 只给了 {@code min(0)}、
 *         没有给 {@code max}，旧框架默认上界是 {@code Integer.MAX_VALUE}。本项目如实沿用同一上界
 *         （控件仍是禁用滑块的 {@code SettingNumberBox}，第 123 条）。</li>
 *     <li><b>可见性条件</b>：旧设置的 {@code visible(...)} 由控制台设置页按「条件不满足就不把该行
 *         加入堆叠」等价实现（整页可重建，改模式 / 改开关立刻增删行，见 {@code BonemealSettingsPage}）。</li>
 * </ol>
 */
public final class BonemealSettings {

    // ── 取值域（逐字 = 旧设置声明） ──

    /** 作用半径域（旧 {@code IntSetting} 1~8） */
    public static final int RANGE_MIN = 1;
    public static final int RANGE_MAX = 8;

    /** 动作节流域（旧 {@code IntSetting} 只给 {@code min(0)}，上界为旧框架默认的 int 最大值） */
    public static final int TICK_DELAY_MIN = 0;
    public static final int TICK_DELAY_MAX = Integer.MAX_VALUE;

    /** 每轮最大催熟数域（同上，只给 {@code min(0)}） */
    public static final int MAX_PER_TICK_MIN = 0;
    public static final int MAX_PER_TICK_MAX = Integer.MAX_VALUE;

    // ── 颜色默认值（旧 SettingColor 字面量的色义映射） ──

    /** {@code ColorPresets} 预设下标：1 = 绿（旧 {@code (0,255,100)} 属绿） */
    private static final int PRESET_GREEN = 1;
    /** 线框颜色默认不透明度（旧 {@code (0,255,100,255)} 的 alpha） */
    private static final int LINE_ALPHA = 255;
    /** 填充颜色默认不透明度（旧 {@code (0,255,100,45)} 的 alpha） */
    private static final int FILL_ALPHA = 45;

    // ── 组① 基础参数（旧默认组 sgGeneral 的 5 项，顺序 = 旧声明顺序） ──

    /** 触发模式（默认范围自动扫描） */
    public TriggerMode triggerMode = TriggerMode.范围自动扫描;
    /** 范围扫描的最大半径（默认 4；仅范围自动扫描时可见） */
    public int range = 4;
    /** 准星对着未登记的可催熟方块时聊天提示一次（默认开；仅准星精准指向时可见） */
    public boolean crosshairHint = true;
    /** 视线被方块遮挡的目标跳过（默认开；仅范围自动扫描时可见） */
    public boolean checkOcclusion = true;
    /** 附近 / 准星处没有可催熟目标时提示一次（默认关） */
    public boolean noTargetHint;

    // ── 组② 目标方块（登记 ID 名单） ──
    //
    // 出厂一律空名单：用户 2026-09-22「默认自己选择 不要帮我全选」—— 不给玩家预先勾满，
    // 要催熟什么由玩家在「目标方块」页自己点「选择」勾（旧项目那份默认名单（11/9/8/4/6 项）
    // 没有丢，已搬到 TargetList#members()，用作每组选择器的候选范围，即「哪些方块属于这一类」）。

    /** 农作物（出厂空；候选范围 = {@link TargetList#members()}） */
    public final List<String> targetCrops = new ArrayList<>();

    /** 树苗（出厂空；候选范围 = {@link TargetList#members()}） */
    public final List<String> targetSaplings = new ArrayList<>();

    /** 花卉（出厂空；候选范围 = {@link TargetList#members()}） */
    public final List<String> targetFlowers = new ArrayList<>();

    /** 蘑菇 / 菌类（出厂空；候选范围 = {@link TargetList#members()}） */
    public final List<String> targetMushrooms = new ArrayList<>();

    /** 水下 / 下界（出厂空；候选范围 = {@link TargetList#members()}） */
    public final List<String> targetAquaticNether = new ArrayList<>();

    // ── 组③ 防作弊绕过 ──

    /** 每隔多少 Tick 执行一轮催熟（默认 0 = 每帧最暴力） */
    public int tickDelay;
    /** 每轮最多催熟多少个（默认 0 = 不限制） */
    public int maxPerTick;
    /** 发包瞬间附加视角包（默认开） */
    public boolean rotateSilent = true;
    /** 优先检测副手骨粉（默认开） */
    public boolean offhandFirst = true;
    /** 催熟成功后挥手（默认开） */
    public boolean swingHand = true;
    /** TPS 过低或被拉回时暂停（默认开） */
    public boolean respectLag = true;
    /** 检测到 Grim / Matrix 自动抬高节流（默认开） */
    public boolean autoThrottle = true;

    // ── 组④ ESP渲染 ──

    /** 对候选目标绘制边界框（默认开） */
    public boolean espEnabled = true;
    /** 形状模式（默认两者；枚举名与旧框架同名，按名字存读） */
    public ShapeMode shapeMode = ShapeMode.Both;
    /** 线框颜色（旧 {@code (0,255,100,255)} → 预设绿 + alpha 255） */
    public final EspColor lineColor = new EspColor();
    /** 填充颜色（旧 {@code (0,255,100,45)} → 预设绿 + alpha 45） */
    public final EspColor fillColor = new EspColor();

    /** 构造期把两个颜色项置为旧默认值的等价预设色（{@link EspColor} 可变，不能在字段处写死） */
    public BonemealSettings() {
        lineColor.rgb(ColorPresets.rgb(PRESET_GREEN)).alpha(LINE_ALPHA);
        fillColor.rgb(ColorPresets.rgb(PRESET_GREEN)).alpha(FILL_ALPHA);
    }

    // ── 派生只读 ──

    /** 五组目标名单是否全空（旧 {@code selfCheck} 的判据：五组至少勾选一种可催熟方块） */
    public boolean allTargetListsEmpty() {
        for (TargetList list : TargetList.values()) {
            if (!list.of(this).isEmpty()) return false;
        }
        return true;
    }

    /** 五组目标名单的登记 ID 合计条数（旧启动报告里的「N 种」） */
    public int totalTargetCount() {
        int count = 0;
        for (TargetList list : TargetList.values()) {
            count += list.of(this).size();
        }
        return count;
    }

    // ── 编解码（键名与旧设置名逐字对应） ──

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回取值域 */
    public void load(JsonObject json) {
        if (json == null) return;

        TriggerMode loaded = TriggerMode.ofName(stringOf(json, BonemealTexts.NAME_TRIGGER_MODE));
        if (loaded != null) triggerMode = loaded;
        range = clamp(intOf(json, BonemealTexts.NAME_RANGE, range), RANGE_MIN, RANGE_MAX);
        crosshairHint = boolOf(json, BonemealTexts.NAME_CROSSHAIR_HINT, crosshairHint);
        checkOcclusion = boolOf(json, BonemealTexts.NAME_CHECK_OCCLUSION, checkOcclusion);
        noTargetHint = boolOf(json, BonemealTexts.NAME_NO_TARGET_HINT, noTargetHint);

        // 名单只在配置里真有这个键时才整表替换 —— 缺字段要「保持字段里的默认值」（本方法开头就写明的契约，
        // 与 boolOf / intOf / enumOf 的 fallback 口径一致）。原先无条件 replaceAll 是错的：
        // listOf 在键不存在时返回空表，于是每次载入都把默认名单清成空的。
        //
        // 用户 2026-09-22 报的「自动骨粉五组全是『未选择（共 73 项）』」就是它：磁盘里
        // module-state.json 的 bonemeal 段只有「启用」（设置从没存过），ModuleStateConfig.settingsOf
        // 因此返回空对象，五个默认名单（11 / 9 / 8 / 4 / 6 项）全部被清空 —— 模块开箱即无任何目标，
        // 自检、启动报告、催熟全都不成立。同项目里 AutoLoginSettings#listOf(json, key, fallback)
        // 用的就是「缺键回落原名单」，本处按同一口径修。
        replaceIfPresent(json, BonemealTexts.NAME_TARGET_CROPS, targetCrops);
        replaceIfPresent(json, BonemealTexts.NAME_TARGET_SAPLINGS, targetSaplings);
        replaceIfPresent(json, BonemealTexts.NAME_TARGET_FLOWERS, targetFlowers);
        replaceIfPresent(json, BonemealTexts.NAME_TARGET_MUSHROOMS, targetMushrooms);
        replaceIfPresent(json, BonemealTexts.NAME_TARGET_AQUATIC_NETHER, targetAquaticNether);

        tickDelay = clamp(intOf(json, BonemealTexts.NAME_TICK_DELAY, tickDelay),
            TICK_DELAY_MIN, TICK_DELAY_MAX);
        maxPerTick = clamp(intOf(json, BonemealTexts.NAME_MAX_PER_TICK, maxPerTick),
            MAX_PER_TICK_MIN, MAX_PER_TICK_MAX);
        rotateSilent = boolOf(json, BonemealTexts.NAME_ROTATE_SILENT, rotateSilent);
        offhandFirst = boolOf(json, BonemealTexts.NAME_OFFHAND_FIRST, offhandFirst);
        swingHand = boolOf(json, BonemealTexts.NAME_SWING_HAND, swingHand);
        respectLag = boolOf(json, BonemealTexts.NAME_RESPECT_LAG, respectLag);
        autoThrottle = boolOf(json, BonemealTexts.NAME_AUTO_THROTTLE, autoThrottle);

        espEnabled = boolOf(json, BonemealTexts.NAME_ESP_ENABLED, espEnabled);
        shapeMode = shapeModeOf(json, BonemealTexts.NAME_SHAPE_MODE, shapeMode);
        lineColor.load(json, BonemealTexts.NAME_LINE_COLOR);
        fillColor.load(json, BonemealTexts.NAME_FILL_COLOR);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键，两端必须对称） */
    public void save(JsonObject json) {
        json.addProperty(BonemealTexts.NAME_TRIGGER_MODE, triggerMode.label());
        json.addProperty(BonemealTexts.NAME_RANGE, range);
        json.addProperty(BonemealTexts.NAME_CROSSHAIR_HINT, crosshairHint);
        json.addProperty(BonemealTexts.NAME_CHECK_OCCLUSION, checkOcclusion);
        json.addProperty(BonemealTexts.NAME_NO_TARGET_HINT, noTargetHint);

        json.add(BonemealTexts.NAME_TARGET_CROPS, listToJson(targetCrops));
        json.add(BonemealTexts.NAME_TARGET_SAPLINGS, listToJson(targetSaplings));
        json.add(BonemealTexts.NAME_TARGET_FLOWERS, listToJson(targetFlowers));
        json.add(BonemealTexts.NAME_TARGET_MUSHROOMS, listToJson(targetMushrooms));
        json.add(BonemealTexts.NAME_TARGET_AQUATIC_NETHER, listToJson(targetAquaticNether));

        json.addProperty(BonemealTexts.NAME_TICK_DELAY, tickDelay);
        json.addProperty(BonemealTexts.NAME_MAX_PER_TICK, maxPerTick);
        json.addProperty(BonemealTexts.NAME_ROTATE_SILENT, rotateSilent);
        json.addProperty(BonemealTexts.NAME_OFFHAND_FIRST, offhandFirst);
        json.addProperty(BonemealTexts.NAME_SWING_HAND, swingHand);
        json.addProperty(BonemealTexts.NAME_RESPECT_LAG, respectLag);
        json.addProperty(BonemealTexts.NAME_AUTO_THROTTLE, autoThrottle);

        json.addProperty(BonemealTexts.NAME_ESP_ENABLED, espEnabled);
        json.addProperty(BonemealTexts.NAME_SHAPE_MODE, shapeMode.name());
        lineColor.save(json, BonemealTexts.NAME_LINE_COLOR);
        fillColor.save(json, BonemealTexts.NAME_FILL_COLOR);
    }

    // ── 原语 ──

    /** 名单整表替换（保持同一实例，界面侧持有的引用不失效） */
    private static void replaceAll(List<String> target, List<String> source) {
        target.clear();
        target.addAll(source);
    }

    /**
     * 名单载入：**只有配置里确有该键**（且是数组）时才整表替换，缺字段保持字段里的默认值。
     *
     * <p>不能直接用 {@code replaceAll(target, listOf(json, key))}：{@link #listOf} 对缺键 / 非数组返回空表，
     * 那样「没存过设置的存档」会把默认名单清空（见 {@link #load} 里的注释）。</p>
     */
    private static void replaceIfPresent(JsonObject json, String key, List<String> target) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonArray()) return;
        replaceAll(target, listOf(json, key));
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsInt() : fallback;
    }

    private static String stringOf(JsonObject json, String key) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsString() : null;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /** 读形状模式：按枚举名读回（旧框架同名枚举），非法名字回落默认值，不猜 */
    private static ShapeMode shapeModeOf(JsonObject json, String key, ShapeMode fallback) {
        String name = stringOf(json, key);
        if (name == null) return fallback;
        for (ShapeMode mode : ShapeMode.values()) {
            if (mode.name().equals(name)) return mode;
        }
        return fallback;
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
