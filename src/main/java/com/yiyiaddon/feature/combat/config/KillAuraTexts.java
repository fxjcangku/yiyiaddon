package com.yiyiaddon.feature.combat.config;

/**
 * 杀戮光环（KillAura）26 项设置的中文界面文案。
 *
 * <p><b>为什么单独一份：</b>用户 2026-09-16 拍板「翻成中文」。蓝本 GUI 显示的是
 * {@code Utils.nameToTitle(name)}（{@code Utils.java:443-445}）把 {@code attack-when-holding}
 * 这类登记名转成的英文标题，中文为新增文案；蓝本英文原句逐条留在下方 javadoc 里以便对账。</p>
 *
 * <p><b>顺序</b>与 {@link KillAuraSettings} 的字段顺序一致（即蓝本设置面顺序）：
 * General 9 项（{@code KillAura.java:57-126}）、Targeting 10 项（{@code :130-206}）、
 * Timing 7 项（{@code :210-262}），共 26 项。常量名 = 字段名的大写下划线形式
 * （名称 {@code NAME_xxx} / 描述 {@code DESC_xxx}）。</p>
 */
public final class KillAuraTexts {

    private KillAuraTexts() {
    }

    // ━━━ 常规（蓝本默认设置组，KillAura.java:57-126） ━━━

    /** 蓝本 {@code attack-when-holding}（{@code KillAura.java:57-62}）："Only attacks an entity when a specified item is in your hand." */
    public static final String NAME_ATTACK_WHEN_HOLDING = "持械攻击";

    /** 蓝本 {@code attack-when-holding}（{@code KillAura.java:57-62}） */
    public static final String DESC_ATTACK_WHEN_HOLDING = "仅当手中持有指定物品时才攻击实体。";

    /** 蓝本 {@code selected-weapon-types}（{@code KillAura.java:64-71}）："Which types of weapons to attack with (if you select the diamond sword, any type of sword may be used to attack)." */
    public static final String NAME_WEAPONS = "武器白名单";

    /** 蓝本 {@code selected-weapon-types}（{@code KillAura.java:64-71}） */
    public static final String DESC_WEAPONS = "用哪些武器攻击（选中钻石剑时，任意剑类都可用来攻击）。";

    /** 蓝本 {@code rotate}（{@code KillAura.java:73-78}）："Determines when you should rotate towards the target." */
    public static final String NAME_ROTATION = "旋转时机";

    /** 蓝本 {@code rotate}（{@code KillAura.java:73-78}） */
    public static final String DESC_ROTATION = "决定何时转向目标。";

    /** 蓝本 {@code auto-switch}（{@code KillAura.java:80-85}）："Switches to an acceptable weapon when attacking the target." */
    public static final String NAME_AUTO_SWITCH = "自动切换武器";

    /** 蓝本 {@code auto-switch}（{@code KillAura.java:80-85}） */
    public static final String DESC_AUTO_SWITCH = "攻击目标时自动切换到可用的武器。";

    /** 蓝本 {@code swap-back}（{@code KillAura.java:87-93}）："Switches to your previous slot when done attacking the target." */
    public static final String NAME_SWAP_BACK = "切回原槽位";

    /** 蓝本 {@code swap-back}（{@code KillAura.java:87-93}） */
    public static final String DESC_SWAP_BACK = "打完目标后切回原来的快捷栏槽位。";

    /** 蓝本 {@code shield-mode}（{@code KillAura.java:95-105}）："What to do when your target is blocking with a shield:\n- Ignore:   Don't attack them if they are blocking\n- Break:    Swap to an axe to disable the shield (Only if Auto Switch is enabled)\n- None:     Attack them as normal" */
    public static final String NAME_SHIELD_MODE = "目标举盾时";

    /** 蓝本 {@code shield-mode}（{@code KillAura.java:95-105}），四行文案（换行照蓝本文本块） */
    public static final String DESC_SHIELD_MODE = "目标用盾牌格挡时怎么办：\n"
        + "- 忽略：格挡时不攻击\n"
        + "- 破坏：换斧头破盾（需开启自动切换武器）\n"
        + "- 无：照常攻击";

    /** 蓝本 {@code only-on-click}（{@code KillAura.java:107-112}）："Only attacks when holding left click." */
    public static final String NAME_ONLY_ON_CLICK = "仅按住左键时攻击";

    /** 蓝本 {@code only-on-click}（{@code KillAura.java:107-112}） */
    public static final String DESC_ONLY_ON_CLICK = "只有按住左键时才攻击。";

    /** 蓝本 {@code only-on-look}（{@code KillAura.java:114-119}）："Only attacks when looking at an entity." */
    public static final String NAME_ONLY_ON_LOOK = "仅注视时攻击";

    /** 蓝本 {@code only-on-look}（{@code KillAura.java:114-119}） */
    public static final String DESC_ONLY_ON_LOOK = "只有准星看着实体时才攻击。";

    /** 蓝本 {@code pause-baritone}（{@code KillAura.java:121-126}）："Freezes Baritone temporarily until you are finished attacking the entity." */
    public static final String NAME_PAUSE_BARITONE = "攻击时暂停Baritone";

    /** 蓝本 {@code pause-baritone}（{@code KillAura.java:121-126}） */
    public static final String DESC_PAUSE_BARITONE = "暂时冻结 Baritone，直到打完这个实体。";

    // ━━━ 目标（蓝本 sgTargeting，KillAura.java:130-206） ━━━

    /** 蓝本 {@code entities}（{@code KillAura.java:130-136}）："Entities to attack."（蓝本另带 {@code onlyAttackable()} 过滤） */
    public static final String NAME_ENTITY_TYPES = "目标实体";

    /** 蓝本 {@code entities}（{@code KillAura.java:130-136}） */
    public static final String DESC_ENTITY_TYPES = "要攻击的实体（只列可攻击的实体）。";

    /** 蓝本 {@code priority}（{@code KillAura.java:138-143}）："How to filter targets within range." */
    public static final String NAME_PRIORITY = "排序优先级";

    /** 蓝本 {@code priority}（{@code KillAura.java:138-143}） */
    public static final String DESC_PRIORITY = "范围内目标怎么挑。";

    /** 蓝本 {@code max-targets}（{@code KillAura.java:145-153}）："How many entities to target at once." */
    public static final String NAME_MAX_TARGETS = "多目标数";

    /** 蓝本 {@code max-targets}（{@code KillAura.java:145-153}） */
    public static final String DESC_MAX_TARGETS = "一次锁定多少个实体。";

    /** 蓝本 {@code range}（{@code KillAura.java:155-162}）："The maximum range the entity can be to attack it." */
    public static final String NAME_RANGE = "攻击范围";

    /** 蓝本 {@code range}（{@code KillAura.java:155-162}） */
    public static final String DESC_RANGE = "能攻击到实体的最大距离。";

    /** 蓝本 {@code walls-range}（{@code KillAura.java:164-171}）："The maximum range the entity can be attacked through walls." */
    public static final String NAME_WALLS_RANGE = "穿墙范围";

    /** 蓝本 {@code walls-range}（{@code KillAura.java:164-171}） */
    public static final String DESC_WALLS_RANGE = "隔墙能攻击到实体的最大距离。";

    /** 蓝本 {@code passive-mob-age-filter}（{@code KillAura.java:173-178}）："Determines the age of passive mobs to target (animals, villagers)." */
    public static final String NAME_PASSIVE_MOB_AGE_FILTER = "被动生物年龄";

    /** 蓝本 {@code passive-mob-age-filter}（{@code KillAura.java:173-178}） */
    public static final String DESC_PASSIVE_MOB_AGE_FILTER = "选择要攻击的被动生物年龄（动物、村民）。";

    /** 蓝本 {@code hostile-mob-age-filter}（{@code KillAura.java:180-185}）："Determines the age of hostile mobs to target (zombies, piglins, hoglins, zoglins)." */
    public static final String NAME_HOSTILE_MOB_AGE_FILTER = "敌对生物年龄";

    /** 蓝本 {@code hostile-mob-age-filter}（{@code KillAura.java:180-185}） */
    public static final String DESC_HOSTILE_MOB_AGE_FILTER = "选择要攻击的敌对生物年龄（僵尸、猪灵、疣猪兽、僵尸疣猪兽）。";

    /** 蓝本 {@code ignore-named}（{@code KillAura.java:187-192}）："Whether or not to attack mobs with a name." */
    public static final String NAME_IGNORE_NAMED = "不攻击命名生物";

    /** 蓝本 {@code ignore-named}（{@code KillAura.java:187-192}） */
    public static final String DESC_IGNORE_NAMED = "是否攻击带名字的生物。";

    /** 蓝本 {@code ignore-passive}（{@code KillAura.java:194-199}）："Will only attack sometimes passive mobs if they are targeting you." */
    public static final String NAME_IGNORE_PASSIVE = "被动生物仅反击";

    /** 蓝本 {@code ignore-passive}（{@code KillAura.java:194-199}） */
    public static final String DESC_IGNORE_PASSIVE = "被动生物只有在盯上你时才攻击。";

    /** 蓝本 {@code ignore-tamed}（{@code KillAura.java:201-206}）："Will avoid attacking mobs you tamed." */
    public static final String NAME_IGNORE_TAMED = "不攻击已驯服生物";

    /** 蓝本 {@code ignore-tamed}（{@code KillAura.java:201-206}） */
    public static final String DESC_IGNORE_TAMED = "避开自己驯服的生物。";

    // ━━━ 时机（蓝本 sgTiming，KillAura.java:210-262） ━━━

    /** 蓝本 {@code pause-on-lag}（{@code KillAura.java:210-215}）："Pauses if the server is lagging." */
    public static final String NAME_PAUSE_ON_LAG = "服务器卡顿时暂停";

    /** 蓝本 {@code pause-on-lag}（{@code KillAura.java:210-215}） */
    public static final String DESC_PAUSE_ON_LAG = "服务器卡顿时暂停攻击。";

    /** 蓝本 {@code pause-on-use}（{@code KillAura.java:217-222}）："Does not attack while using an item." */
    public static final String NAME_PAUSE_ON_USE = "使用物品时暂停";

    /** 蓝本 {@code pause-on-use}（{@code KillAura.java:217-222}） */
    public static final String DESC_PAUSE_ON_USE = "正在使用物品时不攻击。";

    /** 蓝本 {@code pause-on-CA}（{@code KillAura.java:224-229}）："Does not attack while CA is placing." */
    public static final String NAME_PAUSE_ON_CA = "CrystalAura放置时暂停";

    /** 蓝本 {@code pause-on-CA}（{@code KillAura.java:224-229}） */
    public static final String DESC_PAUSE_ON_CA = "CrystalAura 正在放置时不攻击。";

    /** 蓝本 {@code TPS-sync}（{@code KillAura.java:231-236}）："Tries to sync attack delay with the server's TPS." */
    public static final String NAME_TPS_SYNC = "同步服务器TPS";

    /** 蓝本 {@code TPS-sync}（{@code KillAura.java:231-236}） */
    public static final String DESC_TPS_SYNC = "按服务器 TPS 调整攻击间隔。";

    /** 蓝本 {@code custom-delay}（{@code KillAura.java:238-243}）："Use a custom delay instead of the vanilla cooldown." */
    public static final String NAME_CUSTOM_DELAY = "自定义攻击间隔";

    /** 蓝本 {@code custom-delay}（{@code KillAura.java:238-243}） */
    public static final String DESC_CUSTOM_DELAY = "用自定义间隔代替原版冷却。";

    /** 蓝本 {@code hit-delay}（{@code KillAura.java:245-253}）："How fast you hit the entity in ticks." */
    public static final String NAME_HIT_DELAY = "攻击间隔（刻）";

    /** 蓝本 {@code hit-delay}（{@code KillAura.java:245-253}） */
    public static final String DESC_HIT_DELAY = "每隔多少刻打一次。";

    /** 蓝本 {@code switch-delay}（{@code KillAura.java:255-262}）："How many ticks to wait before hitting an entity after switching hotbar slots." */
    public static final String NAME_SWITCH_DELAY = "换槽后延迟（刻）";

    /** 蓝本 {@code switch-delay}（{@code KillAura.java:255-262}） */
    public static final String DESC_SWITCH_DELAY = "切换快捷栏后等多少刻再打。";
}
