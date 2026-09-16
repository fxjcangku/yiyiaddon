package com.yiyiaddon.feature.enchant.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.enchant.gear.AnvilStrategy;
import com.yiyiaddon.feature.enchant.model.EnchantRunMode;
import com.yiyiaddon.feature.enchant.model.EnchantSuccessSound;
import com.yiyiaddon.feature.enchant.model.EnchantTargetMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动附魔全部设置项的数据载体。
 *
 * <p>设置名、描述、默认值与取值域逐字来自旧项目 {@code AutoEnchantBook}（行号见各字段注释），
 * 每个字段上方的注释格式为 {@code 设置名｜描述}。禁止增删设置项、禁止改名、禁止改默认值，
 * 唯一例外见 {@link #recordCraftLog} 的说明。</p>
 *
 * <p><b>旧项目 18 个分组（{@code :94-111}）在本项目的落法：</b>控制台页签 = 基础设置 + 模式专属页
 * （GEAR→原版装备附魔 / BOOK→原版附魔分类 / CUSTOM→自动附魔分类 + 自定义附魔），
 * 13 个多选组（五大属性组 + 八个原版组）作为「多选词条控件」出现，见
 * {@link #CUSTOM_GROUPS} / {@link #BOOK_GROUPS}。</p>
 *
 * <p><b>189 个词条为什么不是 189 个字段：</b>旧项目用 156 个 {@code BoolSetting}（五大属性组）+
 * 33 个动态 {@code BoolSetting}（八个原版组）承载勾选，再由多选控件按组读写。
 * 本项目用 {@link #enchantSelection}（键 = 词条名逐字）承载同一份语义——<b>词条名就是标识</b>，
 * 一个字都不能改（旧项目 {@code EnchantmentSelectSetting.apply} 也是按 name 写回）。</p>
 */
public final class EnchantSettings {

    // ━━━ 多选词条分组（旧项目 18 组里的 13 组） ━━━

    /** 一个多选组：{@code title} = 旧项目里该多选控件的名字，{@code entries} = 组内词条名（逐字） */
    public record EnchantGroup(String title, List<String> entries) {
    }

    /** 剑附魔属性（旧项目 {@code :180-234}，54 项） */
    public static final List<String> SWORD_ENCHANTS = List.of(
        "双刃剑 5", "背刺 5", "饕餮 5", "定身 5", "干扰 5", "势破 5", "折锋 5", "暗影突袭 5", "永夜 5",
        "灵魂收割 5", "生死判 5", "破败 5", "镇魂 5", "忍者 5", "忍术 5", "狂热 5", "破釜 5", "血怒 5",
        "跃斩 5", "退散 5", "速攻 5", "首击 5", "骑士 5", "法术大炮 5", "冷血术 5", "剑卫 5",
        "丛刃 5", "决斗 5", "利刃 5", "剑气 5", "名刀司命 5", "咒刃 5", "嗜血 5", "天谴 5", "天道 5",
        "奥术 5", "对决咒术 5", "弑君 5", "弑魔 5", "惩戒咒术 5", "慈悲 5", "生灵咒术 5", "终结 5",
        "脉冲刃 5", "蛇吻 5", "血偿 5", "钝锋 5", "涅槃 5", "心灵感应 5", "归一 5", "高傲 5", "蔑视 5",
        "焚天 5", "讨价还价 5");

    /** 斧头附魔属性（旧项目 {@code :237-241}，5 项） */
    public static final List<String> AXE_ENCHANTS = List.of(
        "定身 5", "涅槃 5", "心灵感应 5", "破釜 5", "跃斩 5");

    /** 弓附魔属性（旧项目 {@code :245-279}，34 项） */
    public static final List<String> BOW_ENCHANTS = List.of(
        "势破 5", "宣判 5", "折锋 5", "暗影突袭 5", "永夜 5", "浮尘 5", "灵魂收割 5", "生死判 5", "破败 5",
        "禁锢 6", "镇魂 5", "忍士 5", "标记 5", "狂热 5", "猎手 5", "穿颅 5", "五言 5", "空军 5", "首射 5",
        "骑射 5", "鸣踪 5", "玻璃大炮 5", "退散 5",
        "天道 5", "弑君 5", "真三言 5", "苍劲 5", "苍穹 5", "神射手 5", "创伤 5", "弓魄 5", "狂妄 5",
        "焚天 5", "夺金 5");

    /** 护甲附魔属性（旧项目 {@code :283-333}，50 项） */
    public static final List<String> ARMOR_ENCHANTS = List.of(
        "不懈 5", "不灭 5", "临阵脱逃 5", "光环 5", "启迪 5", "圣愈 5", "均衡之法 5", "均衡之遁 5", "幸运 5",
        "清风引 5", "狂潮 5", "磐石 5", "祭祀 5", "虚幻 5", "血罡 5", "霜滞 5", "倔强 5", "均衡之御 5",
        "均衡之攻 5", "时速 5", "金钟罩 5", "钢铁胃 5",
        "乾坤 5", "力场 5", "圣之守护 5", "复苏之风 5", "奥术壁垒 5", "奥术血统 5", "招架 5", "无畏契约 5",
        "星穹 5", "村庄英雄 5", "物法皆修 5", "狂骨 5", "玄煞 5", "生命源泉 5", "生命潮汐 5", "盛宴 5",
        "貔貅 5", "逆鳞 5", "旺盛 5", "捍卫 5", "决战 5", "混沌 5", "燃血 5", "祭血 5", "重型装甲 5",
        "意志 5", "噬灵 5", "破格 5");

    /** 工具与通用附魔属性（旧项目 {@code :335-347}，13 项） */
    public static final List<String> OTHER_ENCHANTS = List.of(
        "龙行 5", "海王 5", "催生 1", "矿脉 5", "自我修复 5", "血契 5", "地质学家 5", "破界 5", "贪欲 5",
        "立方 5", "龙之后裔 5", "龙脉 5", "挖金 5");

    /** 原版防具附魔（旧项目 {@code :546}，9 项） */
    public static final List<String> VANILLA_ARMOR_ENCHANTS = List.of(
        "保护 IV", "火焰保护 IV", "摔落缓冲 IV", "爆炸保护 IV", "弹射物保护 IV", "水下呼吸 III", "水下速掘",
        "荆棘 II", "深海探索者 III");

    /** 原版近战附魔（旧项目 {@code :547}，7 项） */
    public static final List<String> VANILLA_MELEE_ENCHANTS = List.of(
        "锋利 IV", "亡灵杀手 IV", "节肢杀手 IV", "击退 II", "火焰附加 II", "抢夺 III", "横扫之刃 III");

    /** 原版工具附魔（旧项目 {@code :548}，3 项） */
    public static final List<String> VANILLA_TOOL_ENCHANTS = List.of("效率 IV", "精准采集", "时运 III");

    /** 原版弓附魔（旧项目 {@code :549}，4 项） */
    public static final List<String> VANILLA_BOW_ENCHANTS = List.of("力量 IV", "冲击 II", "火矢", "无限");

    /** 原版钓竿附魔（旧项目 {@code :550}，2 项） */
    public static final List<String> VANILLA_FISHING_ENCHANTS = List.of("海之眷顾 III", "饵钓 III");

    /** 原版三叉戟附魔（旧项目 {@code :551}，4 项） */
    public static final List<String> VANILLA_TRIDENT_ENCHANTS = List.of("忠诚 III", "穿刺 V", "激流 III", "引雷");

    /** 原版弩附魔（旧项目 {@code :552}，3 项） */
    public static final List<String> VANILLA_CROSSBOW_ENCHANTS = List.of("多重射击", "快速装填 III", "穿透 IV");

    /** 原版通用附魔（旧项目 {@code :553}，1 项） */
    public static final List<String> VANILLA_COMMON_ENCHANTS = List.of("耐久 III");

    /** CUSTOM 模式的 5 个多选组（控件名逐字，旧项目 {@code :541-545}） */
    public static final List<EnchantGroup> CUSTOM_GROUPS = List.of(
        new EnchantGroup("剑附魔属性", SWORD_ENCHANTS),
        new EnchantGroup("斧头附魔属性", AXE_ENCHANTS),
        new EnchantGroup("弓附魔属性", BOW_ENCHANTS),
        new EnchantGroup("护甲附魔属性", ARMOR_ENCHANTS),
        new EnchantGroup("工具与通用附魔属性", OTHER_ENCHANTS));

    /** BOOK 模式的 8 个多选组（控件名逐字，旧项目 {@code :554-561}） */
    public static final List<EnchantGroup> BOOK_GROUPS = List.of(
        new EnchantGroup("原版防具附魔", VANILLA_ARMOR_ENCHANTS),
        new EnchantGroup("原版近战附魔", VANILLA_MELEE_ENCHANTS),
        new EnchantGroup("原版工具附魔", VANILLA_TOOL_ENCHANTS),
        new EnchantGroup("原版弓附魔", VANILLA_BOW_ENCHANTS),
        new EnchantGroup("原版钓竿附魔", VANILLA_FISHING_ENCHANTS),
        new EnchantGroup("原版三叉戟附魔", VANILLA_TRIDENT_ENCHANTS),
        new EnchantGroup("原版弩附魔", VANILLA_CROSSBOW_ENCHANTS),
        new EnchantGroup("原版通用附魔", VANILLA_COMMON_ENCHANTS));

    // ━━━ 基础设置（旧项目 {@code :114-176}） ━━━

    /** 目标模式｜原版装备附魔 / 原版附魔书 / 自定义附魔，三模式互斥切换（旧默认 BOOK） */
    public EnchantTargetMode targetMode = EnchantTargetMode.BOOK;

    /** 单轮抽取次数｜挂机循环每轮附魔最大次数，纯附魔模式忽略此项（默认 10，取值域 1~100） */
    public int singleRoundDraws = 10;

    /** GUI操作延迟(Tick)｜所有 GUI 点击之间的等待 Tick 数（默认 1，取值域 1~10） */
    public int guiDelayTick = 1;

    /** 发包打开距离｜发包开箱/开附魔台/开砂轮/开铁砧允许的最大距离（格），超出则先寻路靠近（默认 6，取值域 1~32） */
    public int openDistance = 6;

    /** 书本补给组数｜每次去书箱抓取的组数（1组=64本）（默认 1，取值域 1~10） */
    public int bookSupplyGroups = 1;

    /** 青金石补给组数｜每次去青金石箱抓取的组数（1组=64个）（默认 1，取值域 1~10） */
    public int lapisSupplyGroups = 1;

    /** 每批取用数量｜每次任务最多从装备箱取用的目标装备数量，铁砧合并会消耗装备，最终完成数可能小于此值（默认 4，取值域 1~16） */
    public int batchTakeCount = 4;

    /** 极品附魔数量｜本次运行最终产出的极品装备数量，达到后自动停机（默认 1，取值域 1~64） */
    public int topGearCount = 1;

    /** 装备运行模式｜原版装备附魔：纯附魔只消耗当前经验，不足则停机；挂机循环前往挂机点刷经验（默认 挂机循环） */
    public EnchantRunMode gearRunMode = EnchantRunMode.EXPERIENCE;

    /** 附魔书运行模式｜原版附魔书：纯附魔只消耗当前经验，不足则停机；挂机循环前往挂机点刷经验（默认 挂机循环） */
    public EnchantRunMode bookRunMode = EnchantRunMode.EXPERIENCE;

    /** 自定义运行模式｜自定义附魔：纯附魔只消耗当前经验，不足则停机；挂机循环前往挂机点刷经验（默认 挂机循环） */
    public EnchantRunMode customRunMode = EnchantRunMode.EXPERIENCE;

    /** ESP标点｜显示已设置点位的名称（默认 true；旧项目 {@code .visible(() -> false)}，仅界面不可见，值照旧生效） */
    public boolean espPoints = true;

    /** 返回挂机视角｜到达挂机位后恢复设置该点位时记录的视角（默认 true；旧项目同样 {@code .visible(() -> false)}） */
    public boolean restoreHangoutView = true;

    /** 成功提示音｜达成目标时播放本地提示音（命中附魔书 / 装备达成极品）（默认 true） */
    public boolean successSoundEnabled = true;

    /** 成功提示音类型｜选择达成目标时播放的音效（默认 挑战完成） */
    public EnchantSuccessSound successSoundType = EnchantSuccessSound.CHALLENGE_COMPLETE;

    // ━━━ 原版装备附魔（旧项目 {@code :451-462}，4 项中的 2 项） ━━━

    /**
     * 记录合成日志｜旧项目 {@code :451-455}，默认 false。
     *
     * <p><b>用户 2026-09-16 裁定：日志功能整体删除</b> → 本项<b>不落</b>（旧项目该设置项的唯一作用
     * 就是开关 {@code GearCraftReport}，功能删除后开关无意义，保留会变成必须解释的空转项）。
     * 差异已登记：本组设置项由 3 项变为 2 项。此处只留说明，不设字段、不落盘、控制台不显示。</p>
     */

    /** 合成策略｜铁砧装备+装备合并排序策略：简单=贡献优先、节能=低惩罚+低成本优先、快速=提升优先少步骤，用于对比经验消耗（默认 节能） */
    public AnvilStrategy anvilStrategy = AnvilStrategy.SAVE_XP;

    /**
     * 装备附魔配置｜配置原版装备的极品附魔目标。
     *
     * <p>编码沿用旧项目 {@code GearEnchantSetting}（{@code :26-31}）：{@code [0]} 装备 ID、
     * {@code [1]} 方案 ID、{@code [2..]} 每条 {@code enchantId:level:excludedFlag}（flag 0/1）。
     * 解析与写回在批次3 的装备附魔配置类里实现，本类只负责原样搬运。</p>
     */
    public final List<String> gearEnchantConfig = new ArrayList<>();

    // ━━━ 自定义附魔（旧项目 {@code :145-149}） ━━━

    /** 自定义附魔目标｜每行填写一个附魔名称和等级，例如：打雷 5。支持中文、阿拉伯数字、罗马数字和不带等级的附魔。 */
    public final List<String> customEnchantTargets = new ArrayList<>();

    // ━━━ 189 个多选词条（键 = 词条名逐字） ━━━

    /** 已勾选词条；键为词条名（逐字），值为是否勾选，默认全部 false */
    public final Map<String, Boolean> enchantSelection = new LinkedHashMap<>();

    public EnchantSettings() {
        for (EnchantGroup group : CUSTOM_GROUPS) resetGroup(group);
        for (EnchantGroup group : BOOK_GROUPS) resetGroup(group);
    }

    private void resetGroup(EnchantGroup group) {
        for (String entry : group.entries()) enchantSelection.putIfAbsent(entry, Boolean.FALSE);
    }

    // ━━━ 多选词条的读写入口（控制台多选控件唯一调这里） ━━━

    /** 该词条是否已勾选 */
    public boolean isSelected(String entry) {
        return Boolean.TRUE.equals(enchantSelection.get(entry));
    }

    /** 勾选 / 取消勾选一个词条 */
    public void setSelected(String entry, boolean selected) {
        if (entry == null || !enchantSelection.containsKey(entry)) return;
        enchantSelection.put(entry, selected);
    }

    /** 某组已勾选的词条（按组内原顺序）；旧项目 {@code EnchantmentSelectSetting.selected(backing)} 的等价物 */
    public List<String> selectedIn(List<String> entries) {
        List<String> result = new ArrayList<>();
        for (String entry : entries) {
            if (isSelected(entry)) result.add(entry);
        }
        return result;
    }

    /** 某组已勾选数量（旧项目计数文案 {@code 已选择 N 项} 的来源） */
    public int selectedCount(List<String> entries) {
        int count = 0;
        for (String entry : entries) {
            if (isSelected(entry)) count++;
        }
        return count;
    }

    /** 按组整体写回（旧项目 {@code EnchantmentSelectSetting.apply}：组内不在集合中的一律置 false） */
    public void applySelection(List<String> entries, Collection<String> selected) {
        for (String entry : entries) {
            enchantSelection.put(entry, selected != null && selected.contains(entry));
        }
    }

    /** 全部已勾选词条（三模式合并计数用） */
    public List<String> allSelected() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : enchantSelection.entrySet()) {
            if (Boolean.TRUE.equals(entry.getValue())) result.add(entry.getKey());
        }
        return result;
    }

    // ━━━ 持久化 ━━━

    /** 写入 JSON */
    public void save(JsonObject json) {
        json.addProperty("targetMode", targetMode.name());
        json.addProperty("singleRoundDraws", singleRoundDraws);
        json.addProperty("guiDelayTick", guiDelayTick);
        json.addProperty("openDistance", openDistance);
        json.addProperty("bookSupplyGroups", bookSupplyGroups);
        json.addProperty("lapisSupplyGroups", lapisSupplyGroups);
        json.addProperty("batchTakeCount", batchTakeCount);
        json.addProperty("topGearCount", topGearCount);

        json.addProperty("gearRunMode", gearRunMode.name());
        json.addProperty("bookRunMode", bookRunMode.name());
        json.addProperty("customRunMode", customRunMode.name());

        json.addProperty("espPoints", espPoints);
        json.addProperty("restoreHangoutView", restoreHangoutView);
        json.addProperty("successSoundEnabled", successSoundEnabled);
        json.addProperty("successSoundType", successSoundType.name());

        json.addProperty("anvilStrategy", anvilStrategy.name());
        json.add("gearEnchantConfig", stringArray(gearEnchantConfig));
        json.add("customEnchantTargets", stringArray(customEnchantTargets));

        // 只写已勾选词条：未勾选即默认 false，省略可让文件体积与旧项目 189 个布尔键保持同等可读性
        JsonObject selection = new JsonObject();
        for (Map.Entry<String, Boolean> entry : enchantSelection.entrySet()) {
            if (Boolean.TRUE.equals(entry.getValue())) selection.addProperty(entry.getKey(), true);
        }
        json.add("enchantSelection", selection);
    }

    /** 读取 JSON；缺项保留默认值，非法枚举回退默认，整数按各自取值域 clamp */
    public void load(JsonObject json) {
        if (json == null) return;

        targetMode = enumOf(json, "targetMode", EnchantTargetMode.class, targetMode);
        singleRoundDraws = clamp(intOf(json, "singleRoundDraws", singleRoundDraws), 1, 100);
        guiDelayTick = clamp(intOf(json, "guiDelayTick", guiDelayTick), 1, 10);
        openDistance = clamp(intOf(json, "openDistance", openDistance), 1, 32);
        bookSupplyGroups = clamp(intOf(json, "bookSupplyGroups", bookSupplyGroups), 1, 10);
        lapisSupplyGroups = clamp(intOf(json, "lapisSupplyGroups", lapisSupplyGroups), 1, 10);
        batchTakeCount = clamp(intOf(json, "batchTakeCount", batchTakeCount), 1, 16);
        topGearCount = clamp(intOf(json, "topGearCount", topGearCount), 1, 64);

        gearRunMode = enumOf(json, "gearRunMode", EnchantRunMode.class, gearRunMode);
        bookRunMode = enumOf(json, "bookRunMode", EnchantRunMode.class, bookRunMode);
        customRunMode = enumOf(json, "customRunMode", EnchantRunMode.class, customRunMode);

        espPoints = boolOf(json, "espPoints", espPoints);
        restoreHangoutView = boolOf(json, "restoreHangoutView", restoreHangoutView);
        successSoundEnabled = boolOf(json, "successSoundEnabled", successSoundEnabled);
        successSoundType = enumOf(json, "successSoundType", EnchantSuccessSound.class, successSoundType);

        anvilStrategy = enumOf(json, "anvilStrategy", AnvilStrategy.class, anvilStrategy);
        loadList(json, "gearEnchantConfig", gearEnchantConfig);
        loadList(json, "customEnchantTargets", customEnchantTargets);

        loadSelection(json);
    }

    /** 词条勾选读取：先整表置 false，再按文件内容置位（未知键忽略，不新增词条） */
    private void loadSelection(JsonObject json) {
        for (Map.Entry<String, Boolean> entry : enchantSelection.entrySet()) {
            entry.setValue(Boolean.FALSE);
        }
        if (!json.has("enchantSelection") || !json.get("enchantSelection").isJsonObject()) return;
        JsonObject selection = json.getAsJsonObject("enchantSelection");
        for (Map.Entry<String, JsonElement> entry : selection.entrySet()) {
            if (!enchantSelection.containsKey(entry.getKey())) continue;
            JsonElement value = entry.getValue();
            if (value != null && value.isJsonPrimitive()) {
                enchantSelection.put(entry.getKey(), value.getAsBoolean());
            }
        }
    }

    // ━━━ 内部 ━━━

    private static JsonArray stringArray(List<String> values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            if (value != null) array.add(value);
        }
        return array;
    }

    /** 列表读取：只有键存在且为数组时才覆盖，缺项保留默认列表 */
    private static void loadList(JsonObject json, String key, List<String> target) {
        if (!json.has(key) || !json.get(key).isJsonArray()) return;
        target.clear();
        for (JsonElement element : json.getAsJsonArray(key)) {
            if (element != null && element.isJsonPrimitive()) target.add(element.getAsString());
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
            return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsBoolean() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static <E extends Enum<E>> E enumOf(JsonObject json, String key, Class<E> type, E fallback) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive()) return fallback;
        try {
            return Enum.valueOf(type, json.get(key).getAsString());
        } catch (Exception ignored) {
            return fallback;
        }
    }
}
