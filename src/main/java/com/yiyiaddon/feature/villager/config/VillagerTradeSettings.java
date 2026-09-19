package com.yiyiaddon.feature.villager.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.villager.model.VillagerProfessionChoice;
import com.yiyiaddon.feature.villager.model.VillagerTradeMode;
import com.yiyiaddon.ui.keybind.AddonKeybind;
import com.yiyiaddon.ui.render.world.ColorPresets;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.render.world.ShapeMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自动村民交易模块设置项的数据载体。
 *
 * <p>设置项的名称、描述、默认值、取值域与控制台分组逐字来自旧项目
 * {@code AutoVillagerTradeModule :113-267}（逐项见 55 号 §2.4），本类只做字段编解码与分组。

 * <p><b>落盘键名沿用旧项目的设置名（中文）</b>（第 103 / 174 条：写盘键名不得改动），
 * 结构如下：</p>
 *
 * <pre>
 * {
 *   "运行模式": "LOCAL",
 *   "绿宝石补给量(组)": 1,            // 域 1~27
 *   "搜索范围(格)": 96,               // 域 8~256
 *   "挂机循环(按钮)": false,
 *   "补货等待(秒)": 120,              // 域 20~600
 *   "目标职业": "图书管理员",
 *   "快速停止键": { "isKey": true, "value": -1, "modifiers": 0 },   // 未绑定（字段名照旧框架 Keybind）
 *   "目标物品": { "盔甲匠交易": ["minecraft:iron_helmet"], "图书管理员附魔书": ["minecraft:efficiency"] },
 *   "价格上限": { "盔甲匠价格上限": 32 },
 *   "多任务职业": { "盔甲匠": true }
 * }
 * </pre>
 *
 * <p><b>点位渲染（用户 2026-09-19）</b>：绿宝石箱 / 成品交易箱各自持有一套「显示 / 颜色 / 渲染模式」
 * （载体 {@link EspRenderObject}，落盘键 {@code render.<对象名>.show / mode / colorRgb…}），
 * 外加持一个全局的字牌大小 {@link #labelSize}（域 6~32，默认 12）。</p>
 *
 * <p><b>购买量(组) 13 项不落设置（D6 拍板）</b>：旧项目该行是 {@code visible(() -> false)} 恒隐藏
 * （旧 {@code :243-254}，描述「已改为默认榨干模式，购买量不再生效」），界面不承载、
 * 状态机按默认 1 组（{@link #SUPPLY_GROUPS}）传入，行为与旧项目一致。</p>
 */
public final class VillagerTradeSettings {

    /** 旧购买量设置的固定取值（组）：默认 1 → 64 件，榨干模式下不参与退出判定 */
    public static final int SUPPLY_GROUPS = 1;

    /** 价格上限默认值（旧 {@code IntSetting defaultValue 32}，域 1~64） */
    public static final int DEFAULT_PRICE_LIMIT = 32;

    /** 各职业价格上限取值域（旧 min 1 / max 64） */
    public static final int PRICE_LIMIT_MIN = 1;
    public static final int PRICE_LIMIT_MAX = 64;

    /** 物品选择器落盘用的键后缀（旧设置名 = 职业名 + 该后缀） */
    private static final String ITEM_KEY_SUFFIX = "交易";
    /** 价格上限落盘用的键后缀（旧设置名 = 职业名 + 该后缀） */
    private static final String PRICE_KEY_SUFFIX = "价格上限";
    /** 图书管理员附魔书选择器的落盘键（旧设置名逐字） */
    private static final String LIBRARIAN_ENCHANT_KEY = "图书管理员附魔书";

    // ── 点位渲染默认值（颜色只从 ColorPresets 取，不散落 RGB 字面量） ──

    /** 绿宝石箱默认配色在 {@link ColorPresets} 里的下标（「绿」，沿用旧 ContainerESP 的 {@code Color.GREEN} 角色位） */
    private static final int EMERALD_PRESET = 1;
    /** 成品交易箱默认配色在 {@link ColorPresets} 里的下标（「青」，沿用旧 ContainerESP 的 {@code Color.CYAN} 角色位） */
    private static final int UNLOAD_PRESET = 3;
    /** 点位渲染对象默认不透明度：与旧 ContainerESP 一致（实心描边，255 = 完全不透明） */
    private static final int RENDER_ALPHA = 255;

    /** 字牌大小取值域（界面输入框与设置读取共用同一份） */
    public static final int LABEL_SIZE_MIN = 6;
    public static final int LABEL_SIZE_MAX = 32;

    /** 字牌大小行文案（逐字，禁止改写） */
    public static final String NAME_LABEL_SIZE = "字牌大小";
    public static final String DESC_LABEL_SIZE = "点位头顶文字的字号（取值域 6~32，默认 12）；"
        + "字越大越远也看得清，越容易挡住视线";

    // ── 默认组（旧 sgGeneral：基础 6 项 + 快速停止键） ──

    /** 运行模式（描述「选择交易模式」，默认 LOCAL） */
    public VillagerTradeMode mode = VillagerTradeMode.LOCAL;
    /** 绿宝石补给量(组)：补到「底限32个 + 该组数×64」即返回交易（默认 1，域 1~27） */
    public int emeraldSupplyStacks = 1;
    /** 搜索范围(格)：寻路模式搜索目标村民的半径（默认 96，域 8~256） */
    public int searchRange = 96;
    /** 挂机循环(按钮)：仅寻路单点/多任务模式的榨干模式生效（默认 false） */
    public boolean idleLoop = false;
    /** 补货等待(秒)：与村民补货冷却(2分钟)一致（默认 120，域 20~600） */
    public int restockWaitSeconds = 120;
    /** 目标职业（默认 图书管理员；多任务模式下由「多任务职业」勾选决定，此选项隐藏） */
    public VillagerProfessionChoice profession = VillagerProfessionChoice.图书管理员;
    /**
     * 快速停止键（旧 {@code KeybindSetting}，默认 none → 界面显示 {@code None}）。
     *
     * <p>承载方式：自研键位控件 {@link AddonKeybind}（照旧框架 {@code Keybind} 自研，见其类 javadoc），
     * 界面由 {@code SettingKeybind} 录制，落盘在本设置对象里（键名 {@code 快速停止键} 逐字照旧设置名）。</p>
     */
    public AddonKeybind stopKey = AddonKeybind.none();

    // ── 目标物品组（旧 sgTarget） ──

    /** 职业名 → 已选物品注册表 ID 列表（旧 13 个 {@code ItemListSetting}） */
    private final Map<String, List<String>> itemTargets = new LinkedHashMap<>();
    /** 图书管理员附魔书：已选附魔 ID 列表（旧 {@code EnchantmentListSetting}） */
    private final List<String> librarianEnchantments = new ArrayList<>();

    // ── 价格上限组（旧 sgPrice） ──

    /** 职业名 → 绿宝石价格上限（未设置时用 {@link #DEFAULT_PRICE_LIMIT}） */
    private final Map<String, Integer> priceLimits = new LinkedHashMap<>();

    // ── 多任务职业组（旧 sgPipeline） ──

    /** 已勾选加入多任务队列的职业名集合（旧 13 个勾选框） */
    private final Set<String> pipelineProfessions = new LinkedHashSet<>();

    // ── 渲染：点位渲染（每类独立，界面行与设置窗口走共用件） ──

    /**
     * 绿宝石箱：交易货币来源箱，默认绿（{@link ColorPresets} 下标 {@link #EMERALD_PRESET}），实心线框。
     *
     * <p>载体是共用件 {@link EspRenderObject}（用户 2026-09-19：「所有标点选择点位位置的模块 参照
     * 星露谷农场的点位设置」）：显示 / 颜色（可自定义）/ 渲染模式三件与落盘键
     * {@code render.绿宝石箱.*} 全走共用口径，与星露谷 / 自动挖矿等点位页完全一致。</p>
     */
    public final EspRenderObject renderEmeraldBox = new EspRenderObject("绿宝石箱",
        "高亮已绑定的绿宝石箱（交易货币来源箱）",
        ColorPresets.rgb(EMERALD_PRESET), RENDER_ALPHA, ShapeMode.Lines);

    /**
     * 成品交易箱：交易产物卸货箱，默认青（{@link ColorPresets} 下标 {@link #UNLOAD_PRESET}），实心线框。
     */
    public final EspRenderObject renderUnloadBox = new EspRenderObject("成品交易箱",
        "高亮已绑定的成品交易箱（交易产物卸货箱）",
        ColorPresets.rgb(UNLOAD_PRESET), RENDER_ALPHA, ShapeMode.Lines);

    /**
     * 点位字牌字号（GUI 缩放坐标），默认 12，读取时收拢到
     * {@link #LABEL_SIZE_MIN}~{@link #LABEL_SIZE_MAX}。
     *
     * <p>字号是「一类排版参数」，不随单个点位类别变化，因此单独一个字段，不塞进
     * {@link EspRenderObject}；字牌颜色跟随对应箱子的方框颜色。</p>
     */
    public int labelSize = 12;

    /** 全部渲染对象，顺序即界面顺序（绿宝石箱 → 成品交易箱） */
    private final List<EspRenderObject> renderObjects = List.of(renderEmeraldBox, renderUnloadBox);

    /** 全部点位渲染对象（界面行、设置窗口出厂件与落盘共用同一份） */
    public List<EspRenderObject> renderObjects() {
        return renderObjects;
    }

    // ── 分组访问器（K 控制台与模块入口共用同一套读写，避免两处各判一次） ──

    /** 指定职业已选的物品 ID 列表（返回活引用，供选择器写入） */
    public List<String> itemTargets(String professionName) {
        return itemTargets.computeIfAbsent(professionName, key -> new ArrayList<>());
    }

    /** 覆盖指定职业的物品选择（选择器确认时调用） */
    public void setItemTargets(String professionName, List<String> ids) {
        itemTargets.put(professionName, new ArrayList<>(ids));
    }

    /** 图书管理员附魔书已选附魔 ID 列表（返回活引用，供选择器写入） */
    public List<String> librarianEnchantments() {
        return librarianEnchantments;
    }

    /** 覆盖附魔书选择（选择器确认时调用） */
    public void setLibrarianEnchantments(List<String> ids) {
        librarianEnchantments.clear();
        librarianEnchantments.addAll(ids);
    }

    /** 指定职业的价格上限（未设置时返回默认 32） */
    public int priceLimit(String professionName) {
        return priceLimits.getOrDefault(professionName, DEFAULT_PRICE_LIMIT);
    }

    /** 设置指定职业的价格上限（自动收拢回 1~64） */
    public void setPriceLimit(String professionName, int value) {
        priceLimits.put(professionName, clamp(value, PRICE_LIMIT_MIN, PRICE_LIMIT_MAX));
    }

    /** 指定职业是否已加入多任务队列 */
    public boolean isPipelineSelected(String professionName) {
        return pipelineProfessions.contains(professionName);
    }

    /** 勾选 / 取消勾选某职业加入多任务队列 */
    public void setPipelineSelected(String professionName, boolean selected) {
        if (selected) {
            pipelineProfessions.add(professionName);
        } else {
            pipelineProfessions.remove(professionName);
        }
    }

    // ── 编解码（键名与旧设置名逐字对应） ──

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回旧项目取值域。 */
    public void load(JsonObject json) {
        if (json == null) return;

        mode = modeOf(json, "运行模式", mode);
        emeraldSupplyStacks = clamp(intOf(json, "绿宝石补给量(组)", emeraldSupplyStacks), 1, 27);
        searchRange = clamp(intOf(json, "搜索范围(格)", searchRange), 8, 256);
        idleLoop = boolOf(json, "挂机循环(按钮)", idleLoop);
        restockWaitSeconds = clamp(intOf(json, "补货等待(秒)", restockWaitSeconds), 20, 600);
        profession = professionOf(json, "目标职业", profession);
        stopKey = AddonKeybind.fromJson(json.get("快速停止键"), stopKey);

        loadItemTargets(json);
        loadPriceLimits(json);
        loadPipelineProfessions(json);

        labelSize = clamp(intOf(json, "labelSize", labelSize), LABEL_SIZE_MIN, LABEL_SIZE_MAX);
        for (EspRenderObject object : renderObjects) {
            object.load(json, "render." + object.name() + ".");
        }
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键）。 */
    public void save(JsonObject json) {
        json.addProperty("运行模式", mode.name());
        json.addProperty("绿宝石补给量(组)", emeraldSupplyStacks);
        json.addProperty("搜索范围(格)", searchRange);
        json.addProperty("挂机循环(按钮)", idleLoop);
        json.addProperty("补货等待(秒)", restockWaitSeconds);
        json.addProperty("目标职业", profession.name());
        json.add("快速停止键", stopKey.toJson());

        JsonObject items = new JsonObject();
        for (Map.Entry<String, List<String>> entry : itemTargets.entrySet()) {
            items.add(entry.getKey() + ITEM_KEY_SUFFIX, stringsToJson(entry.getValue()));
        }
        items.add(LIBRARIAN_ENCHANT_KEY, stringsToJson(librarianEnchantments));
        json.add("目标物品", items);

        JsonObject prices = new JsonObject();
        for (Map.Entry<String, Integer> entry : priceLimits.entrySet()) {
            prices.addProperty(entry.getKey() + PRICE_KEY_SUFFIX, entry.getValue());
        }
        json.add("价格上限", prices);

        JsonObject pipeline = new JsonObject();
        for (String name : pipelineProfessions) {
            pipeline.addProperty(name, true);
        }
        json.add("多任务职业", pipeline);

        json.addProperty("labelSize", labelSize);
        for (EspRenderObject object : renderObjects) {
            object.save(json, "render." + object.name() + ".");
        }
    }

    /** 读「目标物品」组：13 个 `<职业>交易` 数组 + `图书管理员附魔书` 数组。 */
    private void loadItemTargets(JsonObject json) {
        JsonObject items = objectOf(json, "目标物品");
        if (items == null) return;
        itemTargets.clear();
        for (VillagerProfessionChoice choice : VillagerProfessionChoice.values()) {
            String key = choice.name() + ITEM_KEY_SUFFIX;
            if (items.has(key)) {
                itemTargets.put(choice.name(), stringsOf(items.get(key)));
            }
        }
        if (items.has(LIBRARIAN_ENCHANT_KEY)) {
            librarianEnchantments.clear();
            librarianEnchantments.addAll(stringsOf(items.get(LIBRARIAN_ENCHANT_KEY)));
        }
    }

    /** 读「价格上限」组：13 个 `<职业>价格上限`。 */
    private void loadPriceLimits(JsonObject json) {
        JsonObject prices = objectOf(json, "价格上限");
        if (prices == null) return;
        priceLimits.clear();
        for (VillagerProfessionChoice choice : VillagerProfessionChoice.values()) {
            String key = choice.name() + PRICE_KEY_SUFFIX;
            if (prices.has(key)) {
                priceLimits.put(choice.name(),
                    clamp(prices.get(key).getAsInt(), PRICE_LIMIT_MIN, PRICE_LIMIT_MAX));
            }
        }
    }

    /** 读「多任务职业」组：13 个职业名 → 布尔。 */
    private void loadPipelineProfessions(JsonObject json) {
        JsonObject pipeline = objectOf(json, "多任务职业");
        if (pipeline == null) return;
        pipelineProfessions.clear();
        for (VillagerProfessionChoice choice : VillagerProfessionChoice.values()) {
            if (pipeline.has(choice.name()) && pipeline.get(choice.name()).getAsBoolean()) {
                pipelineProfessions.add(choice.name());
            }
        }
    }

    // ── 原语 ──

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsInt() : fallback;
    }

    private static JsonObject objectOf(JsonObject json, String key) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonObject() ? element.getAsJsonObject() : null;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /** 枚举名解析：非法值回落默认（与旧框架设置反序列化同口径）。 */
    private static VillagerTradeMode modeOf(JsonObject json, String key, VillagerTradeMode fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive()) return fallback;
        try {
            return VillagerTradeMode.valueOf(element.getAsString());
        } catch (IllegalArgumentException ignored) {
            return fallback;
        }
    }

    private static VillagerProfessionChoice professionOf(JsonObject json, String key, VillagerProfessionChoice fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive()) return fallback;
        try {
            return VillagerProfessionChoice.valueOf(element.getAsString());
        } catch (IllegalArgumentException ignored) {
            return fallback;
        }
    }

    private static List<String> stringsOf(JsonElement element) {
        List<String> out = new ArrayList<>();
        if (element == null || !element.isJsonArray()) return out;
        for (JsonElement item : element.getAsJsonArray()) {
            if (item.isJsonPrimitive()) out.add(item.getAsString());
        }
        return out;
    }

    private static com.google.gson.JsonArray stringsToJson(List<String> values) {
        com.google.gson.JsonArray array = new com.google.gson.JsonArray();
        for (String value : values) array.add(value);
        return array;
    }
}
