package com.yiyiaddon.feature.combat.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.combat.target.AttackableEntityTypes;
import com.yiyiaddon.feature.combat.target.SortPriority;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 杀戮光环（KillAura）全部设置项的数据载体。
 *
 * <p><b>蓝本</b>：{@code 01-开发参考库/Meteor原始源码/…/systems/modules/combat/KillAura.java}。
 * 设置面逐条对齐蓝本，共 26 项，一项不少、默认值与取值域逐字相同：</p>
 * <ul>
 *     <li>General 9 项：{@code KillAura.java:57-126}；</li>
 *     <li>Targeting 10 项：{@code KillAura.java:130-206}；</li>
 *     <li>Timing 7 项：{@code KillAura.java:210-262}。</li>
 * </ul>
 *
 * <p>蓝本用 {@code Setting<T>} + 三个设置组（默认组 / {@code Targeting} / {@code Timing}）承载；
 * 本项目按 {@code feature/autochest/config/AutoChestSettings} 的既有做法，用一个纯字段数据类承载，
 * 分组体现在下方三节注释里：界面（批次 5）按这三节落地，字段与默认值不再另立一份数据源。
 * 每个字段的注释都写明「蓝本设置名 + 蓝本默认值 + 蓝本行号」，便于日后逐项对账。</p>
 *
 * <p>蓝本里靠 {@code .visible(...)} 控制显隐的五项（武器白名单 / 切回原槽 / 多目标数 /
 * 命中延迟）在注释里标注了可见性条件，界面据此显隐，本类不参与显隐判定。</p>
 *
 * <p><b>与蓝本的差异（有意偏离，勿自行改回）</b></p>
 * <ol>
 *     <li><b>目标实体默认值</b>：蓝本 {@code KillAura.java:134} 默认只勾玩家（{@code onlyAttackable()} 过滤 +
 *         {@code defaultValue(EntityType.PLAYER)}），本项目默认<b>全部怪物</b>（{@link #defaultEntityTypes()}，
 *         字段 {@link #entityTypes} 的初值）——原因：用户 2026-09-16 要求开箱即用（「开启就能打怪」）。
 *         蓝本口径的常量 {@link #DEFAULT_ENTITY_TYPES} 原样保留，只用于识别老配置里的旧默认值。</li>
 *     <li><b>四项数值默认值</b>（用户 2026-09-17：「旋转时机默认改成不旋转 多目标数默认4个 距离默认6 穿墙 3」）：
 *         旋转时机 {@link #DEFAULT_ROTATION}（不旋转）、多目标数 {@link #DEFAULT_MAX_TARGETS}(4)、
 *         攻击范围 {@link #DEFAULT_RANGE}(6)、穿墙范围 {@link #DEFAULT_WALLS_RANGE}(3)。
 *         蓝本原默认值（{@code Always} / 1 / 4.5 / 3.5）保留为 {@code LEGACY_*} 常量，只用于识别老配置
 *         （见 {@link #migrateLegacyCombatDefaults()}）；<b>取值域一项未改</b>，仍按蓝本 clamp。</li>
 * </ol>
 */
public final class KillAuraSettings {

    // ━━━ 常量（蓝本常量逐字，:67 / :264 / :134） ━━━

    /** 武器白名单可选物品：蓝本 {@code FILTER}（{@code :264} 逐字，8 项） */
    public static final List<String> WEAPON_FILTER = List.of(
            "minecraft:diamond_sword",
            "minecraft:diamond_axe",
            "minecraft:diamond_pickaxe",
            "minecraft:diamond_shovel",
            "minecraft:diamond_hoe",
            "minecraft:mace",
            "minecraft:diamond_spear",
            "minecraft:trident");

    /** 武器白名单默认值：蓝本 {@code :67} 逐字（钻石剑 / 钻石斧 / 三叉戟） */
    public static final List<String> DEFAULT_WEAPONS =
            List.of("minecraft:diamond_sword", "minecraft:diamond_axe", "minecraft:trident");

    /** 目标实体默认值：蓝本 {@code :134}（{@code onlyAttackable()} + 仅玩家）。本项目不用它当默认值，只用于识别老配置，见 {@link #defaultEntityTypes()} */
    public static final List<String> DEFAULT_ENTITY_TYPES = List.of("minecraft:player");

    // ━━━ 本项目的默认值偏离（用户 2026-09-17 口径，蓝本原默认值保留在下面只用于识别老配置） ━━━

    /** 本项目默认旋转时机：**不旋转**（用户 2026-09-17：「旋转时机默认改成不旋转」；蓝本默认 {@code Always}） */
    public static final RotationMode DEFAULT_ROTATION = RotationMode.NONE;

    /** 本项目默认多目标数：**4**（用户 2026-09-17：「多目标数默认4个」；蓝本默认 1），取值域仍为蓝本的 1~5 */
    public static final int DEFAULT_MAX_TARGETS = 4;

    /** 本项目默认攻击范围：**6**（用户 2026-09-17：「距离默认6」；蓝本默认 4.5），取值域仍为蓝本的 0~6 */
    public static final double DEFAULT_RANGE = 6;

    /** 本项目默认穿墙范围：**3**（用户 2026-09-17：「穿墙 3」；蓝本默认 3.5），取值域仍为蓝本的 0~6 */
    public static final double DEFAULT_WALLS_RANGE = 3;

    /** 蓝本原默认旋转时机（{@code :73-78}）：只用于识别老配置里从未动过的值，见 {@link #migrateLegacyCombatDefaults()} */
    private static final RotationMode LEGACY_ROTATION = RotationMode.ALWAYS;

    /** 蓝本原默认多目标数（{@code :145-153}）：同上 */
    private static final int LEGACY_MAX_TARGETS = 1;

    /** 蓝本原默认攻击范围（{@code :155-162}）：同上 */
    private static final double LEGACY_RANGE = 4.5;

    /** 蓝本原默认穿墙范围（{@code :164-171}）：同上 */
    private static final double LEGACY_WALLS_RANGE = 3.5;

    /**
     * 本项目默认目标实体名单：<b>全部怪物</b>（{@code MobCategory.MONSTER}），按登记 ID 字典序。
     *
     * <p><b>可攻击口径复用何处</b>：{@link AttackableEntityTypes#attackable()}——本项目「可攻击实体」
     * 的唯一来源（遍历 {@code BuiltInRegistries.ENTITY_TYPE}，按蓝本 {@code EntityUtils.isAttackable}
     * 的 16 项黑名单过滤），本方法在它之上再按生物分类筛出怪物。界面层的选择器候选同样取自那里，
     * 两层口径不会漂移；设置层不反向依赖 UI（{@code config} 不 import {@code ui}）。</p>
     *
     * <p><b>顺序</b>：候选表本身按中文显示名排序，这里改按 ID 字典序，保证同一注册表下结果稳定
     * （落盘内容不抖动）。</p>
     *
     * <p><b>为何是怪物而不是全量</b>：用户 2026-09-16 原话「我让你选择怪物就好了」——
     * 船 / 竹筏 / 盔甲架 / 矿车这类非怪物不该默认进名单（它们既不该被打，也没有对应图标）。
     * 蓝本 {@code :134} 默认仅玩家的口径仍保留在 {@link #DEFAULT_ENTITY_TYPES}，只作老配置识别用。</p>
     */
    public static List<String> defaultEntityTypes() {
        List<String> ids = new ArrayList<>();
        for (EntityType<?> type : AttackableEntityTypes.attackable()) {
            if (type.getCategory() != MobCategory.MONSTER) continue;
            Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
            if (id != null) ids.add(id.toString());
        }
        ids.sort(Comparator.naturalOrder());
        return List.copyOf(ids);
    }

    /**
     * 上一版默认值（全选可攻击实体）；只用于识别老配置，见 {@link #migrateLegacyEntityTypes()}。
     *
     * <p>用户把默认值从「全量」改成「仅怪物」后，改字段初值只对<b>新配置</b>生效：已经按旧默认
     * 落过盘的配置会原样读回来，界面看上去还是「全选」。所以必须按内容识别这一次。</p>
     */
    private static List<String> legacyAllAttackableEntityTypes() {
        List<String> ids = new ArrayList<>();
        for (EntityType<?> type : AttackableEntityTypes.attackable()) {
            Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
            if (id != null) ids.add(id.toString());
        }
        ids.sort(Comparator.naturalOrder());
        return List.copyOf(ids);
    }

    // ━━━ 常规（蓝本默认设置组，:51 / :57-126） ━━━

    /** 蓝本 {@code attack-when-holding}，默认 {@code Weapons}（{@code :57-62}） */
    public AttackItems attackWhenHolding = AttackItems.WEAPONS;

    /** 蓝本 {@code selected-weapon-types}，默认见 {@link #DEFAULT_WEAPONS}；可见性：{@code attackWhenHolding == Weapons}（{@code :64-71}） */
    public final List<String> weapons = new ArrayList<>(DEFAULT_WEAPONS);

    /** 蓝本 {@code rotate}，默认 {@code Always}（{@code :73-78}）；**本项目默认改为 {@code None}（不旋转）**，见 {@link #DEFAULT_ROTATION} */
    public RotationMode rotation = DEFAULT_ROTATION;

    /** 蓝本 {@code auto-switch}，默认 {@code false}（{@code :80-85}） */
    public boolean autoSwitch;

    /** 蓝本 {@code swap-back}，默认 {@code false}；可见性：{@code autoSwitch}（{@code :87-93}） */
    public boolean swapBack;

    /** 蓝本 {@code shield-mode}，默认 {@code None}（{@code :95-105}） */
    public ShieldMode shieldMode = ShieldMode.NONE;

    /** 蓝本 {@code only-on-click}，默认 {@code false}（{@code :107-112}） */
    public boolean onlyOnClick;

    /** 蓝本 {@code only-on-look}，默认 {@code false}（{@code :114-119}） */
    public boolean onlyOnLook;

    /** 蓝本 {@code pause-baritone}（蓝本字段名 {@code pauseOnCombat}），默认 {@code true}（{@code :121-126}） */
    public boolean pauseBaritone = true;

    // ━━━ 目标（蓝本 {@code sgTargeting}，:52 / :130-206） ━━━

    /** 蓝本 {@code entities}，默认见 {@link #defaultEntityTypes()}（本项目默认<b>仅怪物</b>，偏离蓝本；蓝本原默认见 {@link #DEFAULT_ENTITY_TYPES}）（{@code :130-136}） */
    public final List<String> entityTypes = new ArrayList<>(defaultEntityTypes());

    /** 蓝本 {@code priority}，默认 {@code ClosestAngle}（{@code :138-143}） */
    public SortPriority priority = SortPriority.CLOSEST_ANGLE;

    /** 蓝本 {@code max-targets}，默认 {@code 1}，最小 {@code 1}，滑条 {@code 1~5}；可见性：{@code !onlyOnLook}（{@code :145-153}）；**本项目默认改为 4**，见 {@link #DEFAULT_MAX_TARGETS} */
    public int maxTargets = DEFAULT_MAX_TARGETS;

    /** 蓝本 {@code range}，默认 {@code 4.5}，最小 {@code 0}，滑条上界 {@code 6}（{@code :155-162}）；**本项目默认改为 6**，见 {@link #DEFAULT_RANGE} */
    public double range = DEFAULT_RANGE;

    /** 蓝本 {@code walls-range}，默认 {@code 3.5}，最小 {@code 0}，滑条上界 {@code 6}（{@code :164-171}）；**本项目默认改为 3**，见 {@link #DEFAULT_WALLS_RANGE} */
    public double wallsRange = DEFAULT_WALLS_RANGE;

    /** 蓝本 {@code passive-mob-age-filter}，默认 {@code Adult}（{@code :173-178}） */
    public EntityAge passiveMobAgeFilter = EntityAge.ADULT;

    /** 蓝本 {@code hostile-mob-age-filter}，默认 {@code Both}（{@code :180-185}） */
    public EntityAge hostileMobAgeFilter = EntityAge.BOTH;

    /** 蓝本 {@code ignore-named}，默认 {@code false}（{@code :187-192}） */
    public boolean ignoreNamed;

    /** 蓝本 {@code ignore-passive}，默认 {@code true}（{@code :194-199}） */
    public boolean ignorePassive = true;

    /** 蓝本 {@code ignore-tamed}，默认 {@code false}（{@code :201-206}） */
    public boolean ignoreTamed;

    // ━━━ 时机（蓝本 {@code sgTiming}，:53 / :210-262） ━━━

    /** 蓝本 {@code pause-on-lag}，默认 {@code true}（{@code :210-215}） */
    public boolean pauseOnLag = true;

    /** 蓝本 {@code pause-on-use}，默认 {@code false}（{@code :217-222}） */
    public boolean pauseOnUse;

    /** 蓝本 {@code pause-on-CA}，默认 {@code true}（{@code :224-229}）；本项目无 CrystalAura，判据恒假，见 {@code KillAuraModule} */
    public boolean pauseOnCA = true;

    /** 蓝本 {@code TPS-sync}，默认 {@code true}（{@code :231-236}） */
    public boolean tpsSync = true;

    /** 蓝本 {@code custom-delay}，默认 {@code false}（{@code :238-243}） */
    public boolean customDelay;

    /** 蓝本 {@code hit-delay}，默认 {@code 11}，最小 {@code 0}，滑条上界 {@code 60}；可见性：{@code customDelay}（{@code :245-253}） */
    public int hitDelay = 11;

    /** 蓝本 {@code switch-delay}，默认 {@code 0}，最小 {@code 0}，滑条上界 {@code 10}（{@code :255-262}） */
    public int switchDelay;

    // ━━━ 查询辅助 ━━━

    /**
     * 目标实体白名单是否包含该实体类型。
     *
     * <p>对应蓝本 {@code KillAura.java:411} 的 {@code entities.get().contains(entity.getType())}；
     * 本项目设置项存登记 ID 字符串，由调用方传入已解析好的 ID。</p>
     */
    public boolean includesEntityTypeId(String typeId) {
        return typeId != null && entityTypes.contains(typeId);
    }

    /**
     * 武器白名单是否包含该物品。
     *
     * <p>对应蓝本 {@code KillAura.java:484-491} 的 {@code weapons.get().contains(Items.XXX)}。</p>
     */
    public boolean includesWeapon(String itemId) {
        return itemId != null && weapons.contains(itemId);
    }

    // ━━━ 持久化（缺项留默认、非法枚举回退、数值 clamp） ━━━

    /** 写入 JSON */
    public void save(JsonObject json) {
        json.addProperty("attackWhenHolding", attackWhenHolding.name());
        json.add("weapons", stringArray(weapons));
        json.addProperty("rotation", rotation.name());
        json.addProperty("autoSwitch", autoSwitch);
        json.addProperty("swapBack", swapBack);
        json.addProperty("shieldMode", shieldMode.name());
        json.addProperty("onlyOnClick", onlyOnClick);
        json.addProperty("onlyOnLook", onlyOnLook);
        json.addProperty("pauseBaritone", pauseBaritone);
        json.add("entityTypes", stringArray(entityTypes));
        json.addProperty("priority", priority.name());
        json.addProperty("maxTargets", maxTargets);
        json.addProperty("range", range);
        json.addProperty("wallsRange", wallsRange);
        json.addProperty("passiveMobAgeFilter", passiveMobAgeFilter.name());
        json.addProperty("hostileMobAgeFilter", hostileMobAgeFilter.name());
        json.addProperty("ignoreNamed", ignoreNamed);
        json.addProperty("ignorePassive", ignorePassive);
        json.addProperty("ignoreTamed", ignoreTamed);
        json.addProperty("pauseOnLag", pauseOnLag);
        json.addProperty("pauseOnUse", pauseOnUse);
        json.addProperty("pauseOnCA", pauseOnCA);
        json.addProperty("tpsSync", tpsSync);
        json.addProperty("customDelay", customDelay);
        json.addProperty("hitDelay", hitDelay);
        json.addProperty("switchDelay", switchDelay);
    }

    /** 读取 JSON；缺项保留默认值，非法枚举值回退默认，数值按蓝本取值域 clamp */
    public void load(JsonObject json) {
        if (json == null) return;
        attackWhenHolding = enumOf(json, "attackWhenHolding", AttackItems.class, attackWhenHolding);
        readStrings(json, "weapons", weapons);
        rotation = enumOf(json, "rotation", RotationMode.class, rotation);
        autoSwitch = boolOf(json, "autoSwitch", autoSwitch);
        swapBack = boolOf(json, "swapBack", swapBack);
        shieldMode = enumOf(json, "shieldMode", ShieldMode.class, shieldMode);
        onlyOnClick = boolOf(json, "onlyOnClick", onlyOnClick);
        onlyOnLook = boolOf(json, "onlyOnLook", onlyOnLook);
        pauseBaritone = boolOf(json, "pauseBaritone", pauseBaritone);
        readStrings(json, "entityTypes", entityTypes);
        migrateLegacyEntityTypes();
        priority = enumOf(json, "priority", SortPriority.class, priority);
        maxTargets = Math.max(1, intOf(json, "maxTargets", maxTargets));
        range = Math.max(0, doubleOf(json, "range", range));
        wallsRange = Math.max(0, doubleOf(json, "wallsRange", wallsRange));
        migrateLegacyCombatDefaults();
        passiveMobAgeFilter = enumOf(json, "passiveMobAgeFilter", EntityAge.class, passiveMobAgeFilter);
        hostileMobAgeFilter = enumOf(json, "hostileMobAgeFilter", EntityAge.class, hostileMobAgeFilter);
        ignoreNamed = boolOf(json, "ignoreNamed", ignoreNamed);
        ignorePassive = boolOf(json, "ignorePassive", ignorePassive);
        ignoreTamed = boolOf(json, "ignoreTamed", ignoreTamed);
        pauseOnLag = boolOf(json, "pauseOnLag", pauseOnLag);
        pauseOnUse = boolOf(json, "pauseOnUse", pauseOnUse);
        pauseOnCA = boolOf(json, "pauseOnCA", pauseOnCA);
        tpsSync = boolOf(json, "tpsSync", tpsSync);
        customDelay = boolOf(json, "customDelay", customDelay);
        hitDelay = Math.max(0, intOf(json, "hitDelay", hitDelay));
        switchDelay = Math.max(0, intOf(json, "switchDelay", switchDelay));
    }

    /**
     * 老配置一次性迁移：名单为<b>空</b>，或<b>恰好等于某一份旧默认值</b>时，升级成
     * {@link #defaultEntityTypes()}（全部怪物）。
     *
     * <p><b>触发条件</b>：① 空名单（用户清空过，或旧版本落过空默认）；② 蓝本
     * {@link #DEFAULT_ENTITY_TYPES}（{@code ["minecraft:player"]}）；③ 本项目上一版
     * {@link #legacyAllAttackableEntityTypes()}（全选可攻击实体，用户 2026-09-16 看到的
     * 「没让你全选实体」那份名单）。</p>
     *
     * <p><b>为什么安全</b>：只要用户<b>自己挑过</b>——加过、减过、换成别的组合——列表就不等于以上三者，
     * 一律原样尊重；配置里没有 {@code entityTypes} 键时 {@link #readStrings} 保留字段当前值
     * （= 新默认值，非空），也不进分支。</p>
     *
     * <p><b>影响面（诚实记录）</b>：① 用户若恰好手动选出与旧默认值完全一致的组合，会被升级一次；
     * ② 用 ↻ 清空后<b>重启会回到怪物默认</b>——这是为了堵住「空名单静默哑火」，属于有意取舍
     * （本模块设置块没有版本号字段，不为此新增字段、不动落盘结构）。两处都登记在迁移记录里。</p>
     */
    private void migrateLegacyEntityTypes() {
        // 空名单也一并填回默认：空名单 = 一个目标都不打（includesEntityTypeId 就是 contains），
        // 模块会静默失效；用户 2026-09-16 的要求是「我只要怪物」= 打开就能打怪，不是打开什么都不打。
        // 代价：↻ 清空后重启会回到怪物默认，这是有意选择（空默认是个哑火陷阱），登记在迁移记录里。
        if (!entityTypes.isEmpty()
                && !entityTypes.equals(DEFAULT_ENTITY_TYPES)
                && !entityTypes.equals(legacyAllAttackableEntityTypes())) {
            return;
        }
        entityTypes.clear();
        entityTypes.addAll(defaultEntityTypes());
    }

    /**
     * 老配置一次性迁移：四项「从未动过」的常规 / 目标数值升级成本项目新默认值。
     *
     * <p>用户 2026-09-17 指令：「旋转时机默认改成不旋转 多目标数默认4个 距离默认6 穿墙 3」。
     * 改字段初值只对<b>新配置</b>生效 —— 已经落过盘的配置会把旧值原样读回来，界面看上去「默认没改」，
     * 所以必须按内容识别这一次（做法与 {@link #migrateLegacyEntityTypes()} 同）。</p>
     *
     * <p><b>触发条件逐项独立</b>：该项的值<b>恰好等于</b>蓝本原默认值（{@link #LEGACY_ROTATION} 始终 /
     * {@link #LEGACY_MAX_TARGETS} 1 / {@link #LEGACY_RANGE} 4.5 / {@link #LEGACY_WALLS_RANGE} 3.5）时升级为新默认；
     * 任何别的值（包括用户自己调过的）一律原样尊重。</p>
     *
     * <p><b>影响面（诚实记录）</b>：用户若<b>故意</b>把某一项设成蓝本原默认值（例如刻意只打 1 个目标），
     * 会被升级一次 —— 本模块设置块没有版本号字段，「从未动过」与「故意设成旧默认」无法区分，
     * 不为此新增字段、不动落盘结构。</p>
     */
    private void migrateLegacyCombatDefaults() {
        if (rotation == LEGACY_ROTATION) rotation = DEFAULT_ROTATION;
        if (maxTargets == LEGACY_MAX_TARGETS) maxTargets = DEFAULT_MAX_TARGETS;
        if (range == LEGACY_RANGE) range = DEFAULT_RANGE;
        if (wallsRange == LEGACY_WALLS_RANGE) wallsRange = DEFAULT_WALLS_RANGE;
    }

    private static JsonArray stringArray(List<String> values) {
        JsonArray array = new JsonArray();
        for (String value : values) array.add(value);
        return array;
    }

    /** 读取字符串列表；键缺失时保留原值，键存在但为空数组时按空列表（= 用户清空）处理 */
    private static void readStrings(JsonObject json, String key, List<String> target) {
        if (!json.has(key) || !json.get(key).isJsonArray()) return;
        target.clear();
        for (JsonElement element : json.getAsJsonArray(key)) {
            if (element.isJsonPrimitive()) target.add(element.getAsString());
        }
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        try {
            return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsInt() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static double doubleOf(JsonObject json, String key, double fallback) {
        try {
            return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsDouble() : fallback;
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

    // ━━━ 枚举（蓝本内嵌枚举逐条对应，:505-526） ━━━

    /** 攻击时手持物（蓝本 {@code AttackItems}，{@code :505-508}） */
    public enum AttackItems {

        /** 蓝本 {@code Weapons} */
        WEAPONS("武器"),

        /** 蓝本 {@code All} */
        ALL("全部");

        private final String displayName;

        AttackItems(String displayName) {
            this.displayName = displayName;
        }

        /** 中文文案，供界面使用 */
        public String displayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    /** 旋转时机（蓝本 {@code RotationMode}，{@code :510-514}） */
    public enum RotationMode {

        /** 蓝本 {@code Always} */
        ALWAYS("始终"),

        /** 蓝本 {@code OnHit} */
        ON_HIT("命中时"),

        /** 蓝本 {@code None} */
        NONE("不旋转");

        private final String displayName;

        RotationMode(String displayName) {
            this.displayName = displayName;
        }

        /** 中文文案，供界面使用 */
        public String displayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    /** 目标举盾时的处置（蓝本 {@code ShieldMode}，{@code :516-520}） */
    public enum ShieldMode {

        /** 蓝本 {@code Ignore} */
        IGNORE("忽略"),

        /** 蓝本 {@code Break} */
        BREAK("破坏"),

        /** 蓝本 {@code None} */
        NONE("无");

        private final String displayName;

        ShieldMode(String displayName) {
            this.displayName = displayName;
        }

        /** 中文文案，供界面使用 */
        public String displayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    /** 幼年 / 成年过滤（蓝本 {@code EntityAge}，{@code :522-526}） */
    public enum EntityAge {

        /** 蓝本 {@code Baby} */
        BABY("幼年"),

        /** 蓝本 {@code Adult} */
        ADULT("成年"),

        /** 蓝本 {@code Both} */
        BOTH("全部");

        private final String displayName;

        EntityAge(String displayName) {
            this.displayName = displayName;
        }

        /** 中文文案，供界面使用 */
        public String displayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
