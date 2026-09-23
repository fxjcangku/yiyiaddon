package com.yiyiaddon.seed.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.seed.model.OreType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 种子挖矿正式模块 · <b>可持久化配置</b>（正式化第二阶段）。
 *
 * <p><b>只装这一阶段真正需要落盘的两项</b>（阶段口径第十、三十八节）：</p>
 * <ul>
 *     <li>{@link #enabled} —— 「启用种子挖矿」开关；</li>
 *     <li>{@link #seedText} —— 手动填写的服务器种子<b>原文</b>（保留玩家输入的原始形态，含非法输入）。</li>
 * </ul>
 *
 * <p><b>正式化第五阶段（233）新增的三项</b>（口径第三十七节：只允许新增属于「种子预测渲染器」的设置）：</p>
 * <ul>
 *     <li>{@link #renderPrediction} —— 「显示预测钻石」开关；它是覆盖调度的开关（开着才会预测附近区块）；</li>
 *     <li>{@link #coverageRadius} —— 预测覆盖半径（区块，1~6，默认 3）；</li>
 *     <li>{@link #showMissing} —— 是否把「当前缺失」也画到世界里（默认关，避免满世界无效框）。</li>
 * </ul>
 *
 * <p><b>明确不装</b>：矿物白名单 / 精准采集 / 时运 / 食物 / 回家 / Baritone 相关项 ——
 * 那些属于现有自动挖矿模块（{@code MiningSettings}），种子模块不得维护第二套
 * （阶段口径第十、四十六、四十七节）。{@code PredictionSession}、缓存、{@code PredictionResult}
 * 全部是运行时状态，<b>禁止</b>序列化（阶段口径第十一、三十八节）。</p>
 *
 * <p><b>落盘位置</b>：复用项目现有配置系统 {@code ModuleStateConfig}（{@code module-state.json}），
 * 由 {@code SeedMiningService} 按「本服务器 / 本存档」的隔离键读写 —— 种子是典型的「世界相关数据」，
 * 与点位、设置同一口径按服务器隔离（{@code 开发习惯} 第 236 条），
 * <b>不新建第二套 JSON 文件系统</b>（阶段口径第十一节）。</p>
 */
public final class SeedMiningConfig {

    /** 「启用种子挖矿」开关（默认关）。 */
    private boolean enabled;

    /** 服务器种子原文（默认空串 = 未填写；原样保存，是否合法由 {@link #parseSeed(String)} 判定）。 */
    private String seedText = "";

    /**
     * 「显示预测钻石」开关（默认关）。
     *
     * <p>它是<b>覆盖调度</b>的开关：打开后玩家附近的目标区块才会被逐个送进 Worker 预测，
     * 结果才会出现在世界里（口径第十八、二十三节）。关掉即整条链停止并清空渲染。</p>
     */
    private boolean renderPrediction;

    /** 预测覆盖半径（区块，1~6，默认 3；口径第十九节：默认 3，上限 6，不默认 8）。 */
    private int coverageRadius = 3;

    /** 是否把「当前缺失」也画到世界里（默认关；口径第三十七节第一版建议）。 */
    private boolean showMissing;

    /**
     * 要预测的矿物集合（正式化第八阶段 236）。
     *
     * <p><b>默认只有钻石</b>：这样「没碰过矿物多选的用户」行为与 235 逐字一致
     * （覆盖调度只跑钻石，界面读数与耗时都不变）。用户勾上其它矿物之后，
     * 覆盖调度会按「近→远、同距离按矿物声明序」逐个交给 Worker。</p>
     *
     * <p>存的是<b>枚举名</b>；界面只展示当前维度支持的那些，因此这里可以放心存全集
     * （切维度后没用到的那些条目不会生效，切回来还在）。</p>
     */
    private final List<OreType> selectedOres = new ArrayList<>();

    /** 出厂默认矿物集合（钻石；与 235 行为一致）。 */
    private static final List<OreType> DEFAULT_ORES = List.of(OreType.DIAMOND);

    /**
     * 矿物集合是否被<b>显式改写过</b>。
     *
     * <p>没有这个标志就无法区分「用户还没碰过多选」与「用户把所有矿物都取消勾选」：
     * 只看集合是否为空，会把后者静默当成前者、把钻石重新勾回来（进而让取消勾选这个动作失效）。
     * 因此：没改写过 = 出厂默认钻石；改写过 = 以用户写的为准，<b>包括空集</b>。
     * 空集在服务层由 {@code effectiveOres()} 回落到本维度第一种矿物，不会出现「什么都不预测」。</p>
     */
    private boolean oresExplicitlySet;

    /** 要预测的矿物集合（未改写过时 = 出厂默认钻石）。 */
    public List<OreType> selectedOres() {
        return oresExplicitlySet ? List.copyOf(selectedOres) : DEFAULT_ORES;
    }

    /** 是否勾选了这个矿物。 */
    public boolean isOreSelected(OreType oreType) {
        return oreType != null && selectedOres().contains(oreType);
    }

    /**
     * 改写矿物集合（自动去重、按枚举声明序排列）。
     *
     * <p>空集是合法输入（= 用户全部取消勾选）；按声明序排列让覆盖调度的同距离优先级稳定可复现。</p>
     */
    public void selectedOres(Collection<OreType> values) {
        List<OreType> ordered = new ArrayList<>();
        for (OreType oreType : OreType.values()) {
            if (values != null && values.contains(oreType)) {
                ordered.add(oreType);
            }
        }
        oresExplicitlySet = true;
        selectedOres.clear();
        selectedOres.addAll(ordered);
    }

    /** 当前维度下真正生效的矿物集合（与维度支持集合求交，保持声明序）。 */
    public List<OreType> effectiveOres(List<OreType> supported) {
        List<OreType> out = new ArrayList<>();
        List<OreType> selected = selectedOres();
        for (OreType oreType : supported) {
            if (selected.contains(oreType)) {
                out.add(oreType);
            }
        }
        return out;
    }

    /** 「启用种子挖矿」开关。 */
    public boolean enabled() {
        return enabled;
    }

    /** 写回「启用种子挖矿」开关。 */
    public void enabled(boolean value) {
        this.enabled = value;
    }

    /** 服务器种子原文（永不为 null）。 */
    public String seedText() {
        return seedText == null ? "" : seedText;
    }

    /** 写回服务器种子原文（null 视作空串）。 */
    public void seedText(String value) {
        this.seedText = value == null ? "" : value;
    }

    // ── 种子预测渲染器（正式化第五阶段 233） ──

    /** 「显示预测钻石」开关。 */
    public boolean renderPrediction() {
        return renderPrediction;
    }

    /** 写回「显示预测钻石」开关。 */
    public void renderPrediction(boolean value) {
        this.renderPrediction = value;
    }

    /** 预测覆盖半径（区块）。合法性由覆盖调度器判定（取值域只在那里声明一份）。 */
    public int coverageRadius() {
        return coverageRadius;
    }

    /** 写回预测覆盖半径（调用方负责夹进允许区间；本类刻意不另立一份取值域）。 */
    public void coverageRadius(int value) {
        this.coverageRadius = value;
    }

    /** 是否显示「当前缺失」。 */
    public boolean showMissing() {
        return showMissing;
    }

    /** 写回「显示当前缺失」开关。 */
    public void showMissing(boolean value) {
        this.showMissing = value;
    }

    // ── 种子解析（本阶段唯一的种子真值判据） ──

    /**
     * 把玩家填写的文本解析成合法的 {@code long} 世界种子。
     *
     * <p><b>只接受 Java long 十进制字面量</b>（阶段口径第九节）：允许前导 {@code +} / {@code -}，
     * 允许 {@code Long.MIN_VALUE} ~ {@code Long.MAX_VALUE} 全域；空串、空白、小数（{@code 12.3}）、
     * 非数字（{@code abc}）、残缺符号（{@code --}）一律返回 {@code null}。
     * <b>不实现</b>字符串世界种子哈希 —— 本阶段只做数字种子。</p>
     *
     * @return 合法种子；不合法或未填写返回 {@code null}
     */
    public static Long parseSeed(String text) {
        if (text == null) {
            return null;
        }
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(trimmed);
        } catch (NumberFormatException notALong) {
            return null;
        }
    }

    /**
     * 种子输入状态的中文文案（<b>只有三种</b>，阶段口径第三十一节）。
     *
     * <p>刻意不产出「种子已验证」：本阶段没有实现任何 Seed 真实性验证，
     * 仅仅 {@code Long.parseLong} 成功<b>不等于</b>这个种子属于当前世界，因此这里只回答
     * 「填没填 / 格式对不对」，不回答「对不对得上服务器」。验证属于后续阶段。</p>
     */
    public String seedStatusCn() {
        String text = seedText();
        if (text.isBlank()) {
            return "未填写";
        }
        return parseSeed(text) == null ? "格式无效" : "已填写";
    }

    // ── 持久化（键名与本项目其它模块配置文件保持同一英文本地风格） ──

    /** 写入 JSON。 */
    public void save(JsonObject json) {
        json.addProperty("enabled", enabled);
        json.addProperty("seedText", seedText());
        json.addProperty("renderPrediction", renderPrediction);
        json.addProperty("coverageRadius", coverageRadius);
        json.addProperty("showMissing", showMissing);
        JsonArray ores = new JsonArray();
        for (OreType oreType : selectedOres()) {
            ores.add(oreType.name());
        }
        json.add("selectedOres", ores);
    }

    /** 读取 JSON；缺项 / 类型不符保留默认值，绝不抛异常。 */
    public void load(JsonObject json) {
        if (json == null) {
            return;
        }
        enabled = boolOf(json, "enabled", enabled);
        seedText = stringOf(json, "seedText", seedText());
        renderPrediction = boolOf(json, "renderPrediction", renderPrediction);
        coverageRadius = intOf(json, "coverageRadius", coverageRadius);
        showMissing = boolOf(json, "showMissing", showMissing);
        loadOres(json);
    }

    /** 读矿物集合：只认枚举名，陌生条目跳过（配置被手改坏了也不影响启动）。 */
    private void loadOres(JsonObject json) {
        JsonElement element = json.get("selectedOres");
        if (element == null || !element.isJsonArray()) {
            return;
        }
        List<OreType> parsed = new ArrayList<>();
        for (JsonElement item : element.getAsJsonArray()) {
            if (!item.isJsonPrimitive()) {
                continue;
            }
            OreType oreType = OreType.parse(item.getAsString());
            if (oreType != null) {
                parsed.add(oreType);
            }
        }
        // 键存在即视为「用户改写过」——包括空数组，这样 save/load 往返一致
        // （空集在服务层回落到本维度第一种矿物，不会出现「什么都不预测」）
        selectedOres(parsed);
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        try {
            JsonElement element = json.get(key);
            return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
        } catch (RuntimeException ignored) {
            return fallback;
        }
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        try {
            JsonElement element = json.get(key);
            return element != null && element.isJsonPrimitive() ? element.getAsInt() : fallback;
        } catch (RuntimeException ignored) {
            return fallback;
        }
    }

    private static String stringOf(JsonObject json, String key, String fallback) {
        try {
            JsonElement element = json.get(key);
            return element != null && element.isJsonPrimitive() ? element.getAsString() : fallback;
        } catch (RuntimeException ignored) {
            return fallback;
        }
    }
}
