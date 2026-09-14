package com.yiyiaddon.feature.stardew.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.ShapeMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 星露谷农场全部设置项的数据载体。
 *
 * <p>设置名、描述、默认值与取值域逐字来自旧项目
 * {@code stardew/StardewFarmModule.java:280-421}（Meteor 的 {@code Setting} 定义），
 * 禁止增删设置项、禁止改名、禁止改默认值。</p>
 *
 * <p><b>六类选择不在这里落盘：</b>旧项目里它们虽然也是设置项，但真正的持久化是
 * {@code StardewSelectionStore} 按 ServerKey 隔离写 {@code StardewFarm/selection/<服务器>.json}，
 * 内存值切服即被替换。本类只保留内存镜像，不写进模块设置文件。</p>
 *
 * <p><b>渲染配色：</b>每类渲染对象各自独立持有「显示 / 颜色 / 渲染模式」，颜色用
 * {@link EspColor}（含彩虹开关），渲染模式用 {@link ShapeMode}；点位字牌没有渲染模式。</p>
 */
public final class StardewSettings {

    // ━━━ 运行参数：启动 ━━━

    /** 进服自启，默认 false */
    public boolean autoStart = false;

    // ━━━ 运行参数：移动与交互 ━━━

    /** 交互距离，默认 4，取值域 3~8 */
    public int reach = 4;
    /** 扫描预算，默认 16，取值域 4~64 */
    public int scanBudget = 16;
    /** 回中心等待时间，默认 10，取值域 1~300 */
    public int returnCenterDelay = 10;
    /** 批量右击，默认 4，取值域 1~4 */
    public int batchActions = 4;

    // ━━━ 运行参数：浇水 ━━━

    /** 自动浇水，默认 true */
    public boolean autoWater = true;
    /** 自动切换水壶，默认 true */
    public boolean switchCan = true;
    /** 恢复原手持，默认 true */
    public boolean restoreHand = true;

    // ━━━ 运行参数：施肥 / 药剂 ━━━

    /** 自动施肥，默认 false */
    public boolean autoFertilize = false;
    /** 自动用药剂，默认 false */
    public boolean autoPotion = false;

    // ━━━ 运行参数：洒水器 ━━━

    /** 洒水器维护，默认 false */
    public boolean sprinklerMaintenance = false;
    /** 洒水器检查间隔（tick），默认 1200，取值域 200~12000 */
    public int sprinklerInterval = 1200;

    // ━━━ 运行参数：播报 ━━━

    /** 状态提示，默认 false */
    public boolean statusHints = false;

    // ━━━ 后勤参数 ━━━

    /** 简化后勤，默认 true */
    public boolean logisticsSimple = true;

    // ━━━ 渲染：点位渲染（每类独立） ━━━

    /** 农田边界：起点与终点围成的立方体 */
    public final RenderObject renderFarmBorder = new RenderObject("农田边界",
        "渲染起点与终点围成的农田范围", 0xFFFFFF, 50, ShapeMode.Lines);
    /** 农田起点 */
    public final RenderObject renderFarmStart = new RenderObject("农田起点",
        "高亮农田起点方块", 0x00FF00, 120, ShapeMode.Lines);
    /** 农田终点 */
    public final RenderObject renderFarmEnd = new RenderObject("农田终点",
        "高亮农田终点方块", 0xFFFF00, 120, ShapeMode.Lines);
    /** 洒水器本体方块 */
    public final RenderObject renderSprinklerBody = new RenderObject("洒水器本体",
        "高亮已绑定的洒水器本体方块（与覆盖范围互不影响）", 0x00B4FF, 60, ShapeMode.Both);
    /** 洒水器覆盖范围 */
    public final RenderObject renderSprinklerCoverage = new RenderObject("洒水器覆盖范围",
        "高亮洒水器覆盖范围（半径按洒水器等级 1~4 推导，仅用于观察，不参与决策）", 0x00C8FF, 40, ShapeMode.Lines);
    /** 洒水器点位标记 */
    public final RenderObject renderSprinklerPoint = new RenderObject("洒水器点位",
        "在每个已绑定洒水器中心显示点位标记（用于确认点位已绑定）", 0x00FFB4, 150, ShapeMode.Lines);
    /** 种子箱 */
    public final RenderObject renderSeedBox = new RenderObject("种子箱",
        "高亮种子箱方块", 0x00C8FF, 120, ShapeMode.Lines);
    /** 成品箱 */
    public final RenderObject renderOutputBox = new RenderObject("成品箱",
        "高亮成品箱方块", 0xFFAA00, 120, ShapeMode.Lines);
    /** 补水点 */
    public final RenderObject renderWaterSource = new RenderObject("补水点",
        "高亮补水点方块", 0xFF00FF, 120, ShapeMode.Lines);
    /** 点位字牌：只有显示 + 文字颜色，没有渲染模式（{@code mode()} 返回 null） */
    public final RenderObject renderLabels = new RenderObject("点位字牌",
        "各绑定点位头顶的文字标签（只有显示与文字颜色，没有渲染模式）", 0xFFFFFF, 255, null);

    /** 全部渲染对象，顺序即界面顺序（旧项目构造顺序） */
    private final List<RenderObject> renderObjects = List.of(
        renderFarmBorder, renderFarmStart, renderFarmEnd,
        renderSprinklerBody, renderSprinklerCoverage, renderSprinklerPoint,
        renderSeedBox, renderOutputBox, renderWaterSource, renderLabels);

    // ━━━ 六类选择（内存镜像，按 ServerKey 落盘见 StardewSelectionStore） ━━━

    public final List<String> selectedCropKeys = new ArrayList<>();
    public final List<String> selectedPotKeys = new ArrayList<>();
    public final List<String> selectedFertilizerKeys = new ArrayList<>();
    public final List<String> selectedPotionKeys = new ArrayList<>();
    public final List<String> selectedCanKeys = new ArrayList<>();
    public final List<String> selectedSprinklerKeys = new ArrayList<>();

    // ━━━ 取值域（界面输入框与服务层共用同一份，禁止各自再写一遍） ━━━

    public static final int REACH_MIN = 3;
    public static final int REACH_MAX = 8;
    public static final int SCAN_BUDGET_MIN = 4;
    public static final int SCAN_BUDGET_MAX = 64;
    public static final int RETURN_CENTER_DELAY_MIN = 1;
    public static final int RETURN_CENTER_DELAY_MAX = 300;
    public static final int BATCH_ACTIONS_MIN = 1;
    public static final int BATCH_ACTIONS_MAX = 4;
    public static final int SPRINKLER_INTERVAL_MIN = 200;
    public static final int SPRINKLER_INTERVAL_MAX = 12000;

    // ━━━ 界面文案：设置名与描述（逐字，禁止改写） ━━━

    public static final String NAME_AUTO_START = "进服自启";
    public static final String DESC_AUTO_START = "进入正确世界且世界就绪后自动启动农场（默认关闭，手动停止优先）";

    public static final String NAME_REACH = "交互距离";
    public static final String DESC_REACH = "能操作多远的方块，原版上限约 4.5 格";

    public static final String NAME_SCAN_BUDGET = "扫描预算";
    public static final String DESC_SCAN_BUDGET = "每 tick 最多扫描多少格，过大导致资源包解析卡顿";

    public static final String NAME_RETURN_CENTER_DELAY = "回中心等待时间";
    public static final String DESC_RETURN_CENTER_DELAY = "没有可执行任务时，玩家连续静止多少秒才自动返回农田中心";

    public static final String NAME_BATCH_ACTIONS = "批量右击";
    public static final String DESC_BATCH_ACTIONS = "同一 tick 最多对几个格子发右键（1 = 关闭）。只做右键：收割 / 浇水 / 播种 / 施肥；"
        + "清理枯苗这类破坏动作永远单目标。默认 4（上限）；调低更保守——同 tick 多个交互包"
        + "更容易被服务器反作弊注意到";

    public static final String NAME_AUTO_WATER = "自动浇水";
    public static final String DESC_AUTO_WATER = "发现干燥花盆时自动用水壶浇水";

    public static final String NAME_SWITCH_CAN = "自动切换水壶";
    public static final String DESC_SWITCH_CAN = "浇水 / 补水前自动把已选水壶切到主手（默认开启）";

    public static final String NAME_RESTORE_HAND = "恢复原手持";
    public static final String DESC_RESTORE_HAND = "浇水 / 补水完成后恢复切换前的主手物品（默认开启）";

    public static final String NAME_AUTO_FERTILIZE = "自动施肥";
    public static final String DESC_AUTO_FERTILIZE = "开启后才对空盆施肥（默认关闭，选择肥料不等于自动施肥）";

    public static final String NAME_AUTO_POTION = "自动用药剂";
    public static final String DESC_AUTO_POTION = "开启后才使用魔法药剂（默认关闭，选择药剂不等于自动用药剂）";

    public static final String NAME_SPRINKLER_MAINTENANCE = "洒水器维护";
    public static final String DESC_SPRINKLER_MAINTENANCE = "定时检查已绑定洒水器并按需补水（默认关闭）";

    public static final String NAME_SPRINKLER_INTERVAL = "洒水器检查间隔";
    public static final String DESC_SPRINKLER_INTERVAL = "洒水器维护的检查间隔（tick，20=1秒）";

    public static final String NAME_STATUS_HINTS = "状态提示";
    public static final String DESC_STATUS_HINTS = "在聊天栏显示农场运行状态（正在浇水 / 收获 / 播种 / 补货 / 卸货 / 种子回收等）。"
        + "默认关闭；启动自检结论、季节限制与解除、错误与失效提示不受本开关影响";

    public static final String NAME_LOGISTICS_SIMPLE = "简化后勤";
    public static final String DESC_LOGISTICS_SIMPLE = "开启时不用设置补货 / 卸货：种子少于 2 去补、补到 8；成品攒到 8 去卸、不留底。"
        + "关闭后才会逐作物显示那四个后勤阈值";

    // ━━━ 渲染对象 ━━━

    /** 一类渲染对象：显示开关 + 颜色 + 渲染模式（{@code null} = 该类没有渲染模式） */
    public static final class RenderObject {

        /** 对象显示名（界面行标题与「渲染设置」窗口标题都用它） */
        private final String name;
        /** 对象说明（行 tooltip 与设置项描述逐字取自旧项目） */
        private final String description;
        /** 显示开关 */
        public boolean show = true;
        /** 颜色（含透明度与彩虹开关） */
        public final EspColor color;
        /** 渲染模式；{@code null} 表示该类只有显示与颜色 */
        public ShapeMode mode;

        private RenderObject(String name, String description, int rgb, int alpha, ShapeMode mode) {
            this.name = name;
            this.description = description;
            this.color = new EspColor(rgb, alpha);
            this.mode = mode;
        }

        public String name() {
            return name;
        }

        public String description() {
            return description;
        }

        /** 是否有渲染模式（点位字牌为 false） */
        public boolean hasMode() {
            return mode != null;
        }
    }

    /** 全部渲染对象（顺序即界面顺序） */
    public List<RenderObject> renderObjects() {
        return renderObjects;
    }

    // ━━━ 持久化 ━━━

    /** 写入 JSON；渲染对象按对象名做键前缀，颜色走 {@link EspColor#save} */
    public void save(JsonObject json) {
        if (json == null) return;
        json.addProperty("autoStart", autoStart);
        json.addProperty("reach", reach);
        json.addProperty("scanBudget", scanBudget);
        json.addProperty("returnCenterDelay", returnCenterDelay);
        json.addProperty("batchActions", batchActions);
        json.addProperty("autoWater", autoWater);
        json.addProperty("switchCan", switchCan);
        json.addProperty("restoreHand", restoreHand);
        json.addProperty("autoFertilize", autoFertilize);
        json.addProperty("autoPotion", autoPotion);
        json.addProperty("sprinklerMaintenance", sprinklerMaintenance);
        json.addProperty("sprinklerInterval", sprinklerInterval);
        json.addProperty("statusHints", statusHints);
        json.addProperty("logisticsSimple", logisticsSimple);
        for (RenderObject object : renderObjects) {
            String prefix = "render." + object.name() + ".";
            json.addProperty(prefix + "show", object.show);
            if (object.mode != null) json.addProperty(prefix + "mode", object.mode.name());
            object.color.save(json, prefix + "color");
        }
    }

    /** 读取 JSON；缺项保留默认值，非法值一律夹到取值域内 */
    public void load(JsonObject json) {
        if (json == null) return;
        autoStart = boolOf(json, "autoStart", autoStart);
        reach = clamp(intOf(json, "reach", reach), REACH_MIN, REACH_MAX);
        scanBudget = clamp(intOf(json, "scanBudget", scanBudget), SCAN_BUDGET_MIN, SCAN_BUDGET_MAX);
        returnCenterDelay = clamp(intOf(json, "returnCenterDelay", returnCenterDelay),
            RETURN_CENTER_DELAY_MIN, RETURN_CENTER_DELAY_MAX);
        batchActions = clamp(intOf(json, "batchActions", batchActions), BATCH_ACTIONS_MIN, BATCH_ACTIONS_MAX);
        autoWater = boolOf(json, "autoWater", autoWater);
        switchCan = boolOf(json, "switchCan", switchCan);
        restoreHand = boolOf(json, "restoreHand", restoreHand);
        autoFertilize = boolOf(json, "autoFertilize", autoFertilize);
        autoPotion = boolOf(json, "autoPotion", autoPotion);
        sprinklerMaintenance = boolOf(json, "sprinklerMaintenance", sprinklerMaintenance);
        sprinklerInterval = clamp(intOf(json, "sprinklerInterval", sprinklerInterval),
            SPRINKLER_INTERVAL_MIN, SPRINKLER_INTERVAL_MAX);
        statusHints = boolOf(json, "statusHints", statusHints);
        logisticsSimple = boolOf(json, "logisticsSimple", logisticsSimple);
        for (RenderObject object : renderObjects) {
            String prefix = "render." + object.name() + ".";
            object.show = boolOf(json, prefix + "show", object.show);
            if (object.mode != null) object.mode = shapeModeOf(json, prefix + "mode", object.mode);
            object.color.load(json, prefix + "color");
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        try {
            return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsInt() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        try {
            JsonElement element = json.get(key);
            return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static ShapeMode shapeModeOf(JsonObject json, String key, ShapeMode fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive()) return fallback;
        try {
            return ShapeMode.valueOf(element.getAsString());
        } catch (Exception ignored) {
            return fallback;
        }
    }
}
