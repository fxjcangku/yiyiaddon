package com.yiyiaddon.feature.combat.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.combat.KillAuraModule;
import com.yiyiaddon.feature.combat.config.KillAuraSettings;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.EntityAge;
import com.yiyiaddon.feature.combat.config.KillAuraTexts;
import com.yiyiaddon.feature.combat.target.SortPriority;
import com.yiyiaddon.feature.combat.ui.KillAuraConsoleScreen;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 杀戮光环控制台「目标」页：蓝本 {@code sgTargeting} 组 10 项。
 *
 * <p>逐字搬运自蓝本 {@code KillAura.java:130-206}；行顺序、设置名、描述、取值域与落盘时机一字未改，
 * 名称与描述取自 {@link KillAuraTexts}。{@code 多目标数} 的可见性（← 仅注视时攻击）用「整页可重建」
 * 实现：条件不满足时压根不加入堆叠（蓝本 {@code .visible(() -> !onlyOnLook.get())} 的等价物）。</p>
 *
 * <p><b>目标实体候选</b>按蓝本 {@code EntityUtils.isAttackable}（{@code EntityUtils.java:46-48}）
 * 的黑名单过滤，显示名用 {@code EntityType.getDescription()}，按中文显示名排序后分
 * 「原版实体 / 自定义实体」两组（分组口径照本项目其它选择器）。候选表惰性构建并静态缓存。</p>
 */
public final class KillAuraTargetingPage {

    /** 名单行的「点击选择」按钮（逐字照星露谷 / 挖矿控制台页） */
    private static final String SELECT_LABEL = "点击选择";
    /** 状态文字字号：与 {@code SettingText} 内部字号一致，用于按文本宽度算列宽 */
    private static final float STATE_FONT_SIZE = 11f;

    /** 选择器分组标题（与本项目其它选择器同一口径） */
    private static final String GROUP_VANILLA_ENTITY = "§a§l▌ 原版实体";
    private static final String GROUP_CUSTOM_ENTITY = "§d§l▌ 自定义实体";

    /**
     * 不可攻击的实体类型黑名单：蓝本 {@code EntityUtils.isAttackable}
     * （{@code EntityUtils.java:46-48} 逐字，16 项）。
     */
    private static final Set<EntityType<?>> NOT_ATTACKABLE = Set.of(
        EntityType.AREA_EFFECT_CLOUD, EntityType.ARROW, EntityType.FALLING_BLOCK, EntityType.FIREWORK_ROCKET,
        EntityType.ITEM, EntityType.LLAMA_SPIT, EntityType.SPECTRAL_ARROW, EntityType.ENDER_PEARL,
        EntityType.EXPERIENCE_BOTTLE, EntityType.SPLASH_POTION, EntityType.LINGERING_POTION, EntityType.TRIDENT,
        EntityType.LIGHTNING_BOLT, EntityType.FISHING_BOBBER, EntityType.EXPERIENCE_ORB, EntityType.EGG);

    /** 候选排序用：中文显示名（与挖矿选择器同一套口径） */
    private static final Collator COLLATOR = Collator.getInstance(Locale.CHINA);

    /** 分段文案：顺序即 {@link SortPriority} 的序数（最近距离 / 最远距离 / 最低血量 / 最高血量 / 最近角度） */
    private static final List<String> PRIORITY_LABELS = List.of(
        SortPriority.LOWEST_DISTANCE.displayName(), SortPriority.HIGHEST_DISTANCE.displayName(),
        SortPriority.LOWEST_HEALTH.displayName(), SortPriority.HIGHEST_HEALTH.displayName(),
        SortPriority.CLOSEST_ANGLE.displayName());

    /** 分段文案：顺序即 {@link EntityAge} 的序数（幼年 / 成年 / 全部） */
    private static final List<String> ENTITY_AGE_LABELS = List.of(
        EntityAge.BABY.displayName(), EntityAge.ADULT.displayName(), EntityAge.BOTH.displayName());

    /** 目标实体候选（惰性建一次并缓存：注册表运行期不变，显示名解析不便宜） */
    private static List<SelectorScreen.Entry> entityEntries;

    private final KillAuraConsoleScreen owner;
    private final KillAuraModule module;

    public KillAuraTargetingPage(KillAuraConsoleScreen owner, KillAuraModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        KillAuraSettings settings = module.settings();

        stack.add(listRow(KillAuraTexts.NAME_ENTITY_TYPES, KillAuraTexts.DESC_ENTITY_TYPES,
            this::entityStatusText, this::openEntitySelector, this::clearEntityTypes));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_PRIORITY,
            KillAuraTexts.DESC_PRIORITY, null,
            List.of(new Ctl(new SettingSegmented(PRIORITY_LABELS,
                () -> settings.priority.ordinal(), this::pickPriority)))));

        // 多目标数：只在「仅注视时攻击」为假时加入（对应蓝本 .visible(() -> !onlyOnLook.get())）
        if (!settings.onlyOnLook) {
            stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_MAX_TARGETS,
                KillAuraTexts.DESC_MAX_TARGETS, null,
                List.of(new Ctl(intBox(1, 5, () -> settings.maxTargets, value -> settings.maxTargets = value)))));
        }

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_RANGE,
            KillAuraTexts.DESC_RANGE, null,
            List.of(new Ctl(doubleBox(() -> settings.range, value -> settings.range = value)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_WALLS_RANGE,
            KillAuraTexts.DESC_WALLS_RANGE, null,
            List.of(new Ctl(doubleBox(() -> settings.wallsRange, value -> settings.wallsRange = value)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_PASSIVE_MOB_AGE_FILTER,
            KillAuraTexts.DESC_PASSIVE_MOB_AGE_FILTER, null,
            List.of(new Ctl(new SettingSegmented(ENTITY_AGE_LABELS,
                () -> settings.passiveMobAgeFilter.ordinal(), this::pickPassiveAge)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_HOSTILE_MOB_AGE_FILTER,
            KillAuraTexts.DESC_HOSTILE_MOB_AGE_FILTER, null,
            List.of(new Ctl(new SettingSegmented(ENTITY_AGE_LABELS,
                () -> settings.hostileMobAgeFilter.ordinal(), this::pickHostileAge)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_IGNORE_NAMED,
            KillAuraTexts.DESC_IGNORE_NAMED, null,
            List.of(new Ctl(toggle(() -> settings.ignoreNamed, value -> settings.ignoreNamed = value)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_IGNORE_PASSIVE,
            KillAuraTexts.DESC_IGNORE_PASSIVE, null,
            List.of(new Ctl(toggle(() -> settings.ignorePassive, value -> settings.ignorePassive = value)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_IGNORE_TAMED,
            KillAuraTexts.DESC_IGNORE_TAMED, null,
            List.of(new Ctl(toggle(() -> settings.ignoreTamed, value -> settings.ignoreTamed = value)))));
    }

    // ── 行构件 ──

    /** 名单行（行样式照星露谷 / 挖矿控制台页）：名称 + 说明 …… [点击选择] [状态文字] [↻] */
    private CompactElement listRow(String title, String description, Supplier<String> status,
                                   Runnable open, Runnable reset) {
        return new ConsoleRow(owner, () -> title, description, null, List.of(
            new Ctl(new Button(SELECT_LABEL, open)),
            new Ctl(new SettingText(status,
                () -> MinecraftText.measure(status.get(), STATE_FONT_SIZE, false)).alignLeft()),
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, reset))));
    }

    /** 开关行：改动落盘 */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return new SettingToggle(getter, value -> {
            setter.accept(value);
            persist();
        });
    }

    /** 整数设置框：步进 1（蓝本 {@code sliderRange(1, 5)} 的上界即输入框上限） */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, IntConsumer setter) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                persist();
            });
    }

    /** 浮点设置框：步进 0.1、格式 {@code %.1f}（蓝本 {@code sliderMax(6)} 的上界即输入框上限） */
    private SettingNumberBox doubleBox(Supplier<Double> getter, Consumer<Double> setter) {
        return new SettingNumberBox(0, 6, 0.1, "%.1f", getter, value -> {
            setter.accept(value);
            persist();
        });
    }

    // ── 设置写回 ──

    private void pickPriority(int index) {
        if (index < 0 || index >= SortPriority.values().length) return;
        module.settings().priority = SortPriority.values()[index];
        persist();
    }

    private void pickPassiveAge(int index) {
        if (index < 0 || index >= EntityAge.values().length) return;
        module.settings().passiveMobAgeFilter = EntityAge.values()[index];
        persist();
    }

    private void pickHostileAge(int index) {
        if (index < 0 || index >= EntityAge.values().length) return;
        module.settings().hostileMobAgeFilter = EntityAge.values()[index];
        persist();
    }

    /** 立即落盘（模块未提供 persistSettings，走 ModuleManager 的同一入口） */
    private void persist() {
        ModuleManager.saveSettings(module);
    }

    // ── 目标实体（候选 / 选择器 / 显示名） ──

    /**
     * 目标实体候选：全部实体类型中通过蓝本 {@code isAttackable} 黑名单的那些，按中文显示名排序后
     * 分「原版实体 / 自定义实体」两组。候选表惰性构建并静态缓存。
     */
    public static List<SelectorScreen.Entry> entityCandidates() {
        if (entityEntries == null) {
            List<SelectorScreen.Entry> vanilla = new ArrayList<>();
            List<SelectorScreen.Entry> custom = new ArrayList<>();
            for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
                Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
                if (id == null || NOT_ATTACKABLE.contains(type)) continue;
                SelectorScreen.Entry entry = new EntityEntry(id.toString(), type);
                ("minecraft".equals(id.getNamespace()) ? vanilla : custom).add(entry);
            }
            entityEntries = merge(vanilla, custom);
        }
        return entityEntries;
    }

    /** 两组各自按中文名排序后首尾相接：组内有序，组间固定「原版 → 自定义」（本项目选择器既有口径） */
    private static List<SelectorScreen.Entry> merge(List<SelectorScreen.Entry> vanilla,
                                                    List<SelectorScreen.Entry> custom) {
        Comparator<SelectorScreen.Entry> byName =
            Comparator.comparing(SelectorScreen.Entry::title, COLLATOR).thenComparing(SelectorScreen.Entry::key);
        vanilla.sort(byName);
        custom.sort(byName);
        List<SelectorScreen.Entry> all = new ArrayList<>(vanilla.size() + custom.size());
        all.addAll(vanilla);
        all.addAll(custom);
        return List.copyOf(all);
    }

    /** 候选总数（与选择器实际列出的候选同源）：懒算一次并随候选表一起缓存 */
    public static int entityCandidateTotal() {
        return entityCandidates().size();
    }

    /** 实体显示名（登记 ID → {@code EntityType.getDescription()}；认不出的 ID 原样显示，不猜） */
    public static String entityDisplayName(String typeId) {
        Identifier id = Identifier.tryParse(typeId);
        EntityType<?> type = id == null ? null : BuiltInRegistries.ENTITY_TYPE.getValue(id);
        return type == null ? typeId : type.getDescription().getString();
    }

    /** 名单状态文字（逐字照星露谷 / 挖矿口径）：未选 → {@code 未选择（共 N 项）}；已选 → {@code 已选 N / M 项} */
    private String entityStatusText() {
        List<String> types = module.settings().entityTypes;
        if (types.isEmpty()) return "未选择（共 " + entityCandidateTotal() + " 项）";
        return "已选 " + types.size() + " / " + entityCandidateTotal() + " 项";
    }

    /** 打开目标实体选择器：常规模式（左栏「+」加入 / 右栏「-」移除） */
    private void openEntitySelector() {
        if (owner.client() == null) return;
        List<String> types = module.settings().entityTypes;
        owner.client().setScreen(new SelectorScreen(KillAuraTexts.NAME_ENTITY_TYPES, owner.client().screen,
            entityCandidates(),
            () -> new ArrayList<>(types),
            key -> changeEntityTypes(key, true),
            key -> changeEntityTypes(key, false)));
    }

    /** 清空目标实体名单（↻ 语义同星露谷：空则静默 return） */
    private void clearEntityTypes() {
        if (module.settings().entityTypes.isEmpty()) return;
        module.settings().entityTypes.clear();
        persist();
    }

    /** 名单增删：有实际变化才落盘 */
    private void changeEntityTypes(String key, boolean add) {
        List<String> types = module.settings().entityTypes;
        boolean changed = add ? !types.contains(key) && types.add(key) : types.remove(key);
        if (!changed) return;
        persist();
    }

    /**
     * 实体候选条目。
     *
     * <p>不画图标（实体贴图要走实体渲染栈，选择器只要求一个回调）：{@link SelectorScreen} 只把
     * {@code drawIcon} 的返回值当绘制结果，返回 false 即纯文字行，不会崩。</p>
     */
    private record EntityEntry(String key, EntityType<?> type) implements SelectorScreen.Entry {

        @Override
        public String title() {
            return type.getDescription().getString();
        }

        @Override
        public String group() {
            return key.startsWith("minecraft:") ? GROUP_VANILLA_ENTITY : GROUP_CUSTOM_ENTITY;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            return false;
        }
    }
}
