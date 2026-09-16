package com.yiyiaddon.feature.combat.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.combat.KillAuraModule;
import com.yiyiaddon.feature.combat.config.KillAuraSettings;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.EntityAge;
import com.yiyiaddon.feature.combat.config.KillAuraTexts;
import com.yiyiaddon.feature.combat.target.AttackableEntityTypes;
import com.yiyiaddon.feature.combat.target.SortPriority;
import com.yiyiaddon.feature.combat.ui.KillAuraConsoleScreen;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
 * 的黑名单过滤，显示名用 {@code EntityType.getDescription()}，<b>按生物分类分六组</b>
 * （玩家 / 怪物 / 动物 / 水生生物 / 环境生物 / 其他），组内按中文显示名排序。候选表惰性构建并静态缓存。</p>
 */
public final class KillAuraTargetingPage {

    /** 名单行的「点击选择」按钮（逐字照星露谷 / 挖矿控制台页） */
    private static final String SELECT_LABEL = "点击选择";
    /** 状态文字字号：与 {@code SettingText} 内部字号一致，用于按文本宽度算列宽 */
    private static final float STATE_FONT_SIZE = 11f;

    /**
     * 选择器分组标题：<b>按生物分类分组</b>（用户 2026-09-16：「并不是像 meteo r一样分类好的」），
     * 由 {@link #groupOf(EntityType)} 按 {@code MobCategory} 归类，与蓝本选择器口径一致。
     *
     * <p>原文含 {@code §} 颜色码与 {@code ▌} 前缀，由本类给出完整原文（{@code SelectorScreen} 不做拼接）。
     * 顺序即左栏显示顺序，见 {@link #GROUP_ORDER}。</p>
     */
    private static final String GROUP_PLAYER = "§b§l▌ 玩家";
    private static final String GROUP_MONSTER = "§c§l▌ 怪物";
    private static final String GROUP_ANIMAL = "§a§l▌ 动物";
    private static final String GROUP_WATER = "§9§l▌ 水生生物";
    private static final String GROUP_AMBIENT = "§e§l▌ 环境生物";
    private static final String GROUP_MISC = "§7§l▌ 其他";

    /** 分组显示顺序：要打的排前面（玩家 → 怪物 → 动物 → 水生 → 环境 → 其他） */
    private static final List<String> GROUP_ORDER = List.of(
        GROUP_PLAYER, GROUP_MONSTER, GROUP_ANIMAL, GROUP_WATER, GROUP_AMBIENT, GROUP_MISC);

    // 可攻击实体黑名单已下沉到 feature/combat/target/AttackableEntityTypes（设置层与本页共用一份）

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
     * 目标实体候选：全部实体类型中通过蓝本 {@code isAttackable} 黑名单的那些，
     * <b>按生物分类分「玩家 / 怪物 / 动物 / 水生生物 / 环境生物 / 其他」六组</b>，组内按中文显示名排序。
     * 候选表惰性构建并静态缓存。
     *
     * <p><b>为什么不再分「原版 / 自定义」</b>：用户 2026-09-16 要求「像 Meteor 一样分类好的」——
     * 按用途分类比按命名空间分类有用得多（原来的两栏里，船、TNT、盔甲架、僵尸混在一起）。
     * Mod 实体同样按其生物分类进组，不再单列一栏。</p>
     */
    public static List<SelectorScreen.Entry> entityCandidates() {
        if (entityEntries == null) {
            Map<String, List<SelectorScreen.Entry>> byGroup = new LinkedHashMap<>();
            for (String group : GROUP_ORDER) byGroup.put(group, new ArrayList<>());
            for (EntityType<?> type : AttackableEntityTypes.attackable()) {
                Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
                if (id == null) continue;
                byGroup.get(groupOf(type)).add(new EntityEntry(id.toString(), type));
            }

            Comparator<SelectorScreen.Entry> byName =
                Comparator.comparing(SelectorScreen.Entry::title, COLLATOR).thenComparing(SelectorScreen.Entry::key);
            List<SelectorScreen.Entry> all = new ArrayList<>();
            for (String group : GROUP_ORDER) {
                List<SelectorScreen.Entry> bucket = byGroup.get(group);
                bucket.sort(byName);
                all.addAll(bucket);
            }
            entityEntries = List.copyOf(all);
        }
        return entityEntries;
    }

    /**
     * 实体所属分组：玩家单列一组（{@code MobCategory} 里玩家属于 {@code MISC}，不单拎出来就会掉进「其他」），
     * 其余按 {@code MobCategory} 归类。
     */
    private static String groupOf(EntityType<?> type) {
        if (type == EntityType.PLAYER) return GROUP_PLAYER;
        return switch (type.getCategory()) {
            case MONSTER -> GROUP_MONSTER;
            case CREATURE -> GROUP_ANIMAL;
            case AXOLOTLS, WATER_CREATURE, UNDERGROUND_WATER_CREATURE, WATER_AMBIENT -> GROUP_WATER;
            case AMBIENT -> GROUP_AMBIENT;
            case MISC -> GROUP_MISC;
        };
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
     * <p><b>行图标三级回退</b>（用户 2026-09-16：「而且有一些还不显示方块实体图片」）：</p>
     * <ol>
     *     <li><b>刷怪蛋</b> —— {@link ItemIconCache#drawEntity}，绝大多数生物走这条；</li>
     *     <li><b>显式映射表 / 同名物品</b> —— 先查 {@link #ICON_OVERRIDES}（原版没有刷怪蛋、
     *         或实体 id 与物品 id 不同名的，逐条登记依据）；其余按<b>同名物品</b>解析：
     *         船 / 竹筏 / 盔甲架 / 矿车 / 展示框 / 画 的物品 id 与实体 id 同名，一条通用规则即可覆盖；</li>
     *     <li><b>首字占位</b> —— 两者都没有的（「标记」与三个展示实体这类，原版根本没有对应物品），
     *         画一个弱色圆角框 + 显示名首字。保证每一行都有图标，不留空位。</li>
     * </ol>
     */
    private static final class EntityEntry implements SelectorScreen.Entry {

        private final String key;
        private final EntityType<?> type;
        private final String group;
        /** 第 2 级回退图标的物品；{@code null} = 表中没有、也没有同名物品，走首字占位 */
        private final Item fallbackItem;
        /** 第 2 级回退的物品堆：首次绘制时才构造（构建物品栈要注册表已绑定，不能在设置载入期做） */
        private ItemStack fallbackStack;

        private EntityEntry(String key, EntityType<?> type) {
            this.key = key;
            this.type = type;
            this.group = groupOf(type);
            this.fallbackItem = fallbackItem(key, type);
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public String title() {
            return type.getDescription().getString();
        }

        @Override
        public String group() {
            return group;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            if (ItemIconCache.getInstance().drawEntity(canvas, type, x, y, size)) return true;
            if (fallbackItem != null) {
                if (fallbackStack == null) fallbackStack = new ItemStack(fallbackItem);
                return ItemIconCache.getInstance().draw(canvas, fallbackStack, x, y, size);
            }
            drawInitialPlaceholder(canvas, x, y, size);
            return true;
        }

        /**
         * 首字占位：弱色圆角框 + 显示名首字。
         *
         * <p>颜色走当前主题（不硬编码），框用模块底色、字用次级文字色，与真图标同尺寸同位。</p>
         */
        private void drawInitialPlaceholder(Canvas canvas, float x, float y, float size) {
            ClickGuiThemeColors tc = ClickGuiThemeColors.current();
            GlassPanel.fill(canvas, x, y, size, size, size * 0.24f, tc.module, 1f);
            GlassPanel.rim(canvas, x, y, size, size, size * 0.24f, tc.rim, 1f, 0.18f);

            String name = title();
            if (name.isEmpty()) return;
            // 按码点取首字：中文/英文都是一个码点，避免劈开代理对
            String initial = new String(Character.toChars(name.codePointAt(0)));
            float initialSize = size * 0.5f;
            float width = FontRenderer.measureTextWidth(initial, initialSize);
            FontRenderer.drawText(canvas, initial, x + (size - width) / 2f,
                    CardLayout.baseline(y + size / 2f, initialSize), initialSize,
                    GlassPanel.withAlpha(tc.secondaryText, 1f));
        }
    }

    /**
     * 没有刷怪蛋（或实体 id 与物品 id 不同名）的实体的<b>显式图标映射表</b>。
     *
     * <p><b>为什么要表：</b>刷怪蛋那一级走的是「遍历物品注册表找带 {@code ENTITY_DATA} 的蛋」，
     * 没有蛋的实体（用户 2026-09-16 截图里的「巨人」「幻术师」等）会一路退到首字占位，
     * 于是列表里混着汉字方块与真图标。表中每一条都注明依据，<b>只登记确实没有刷怪蛋的实体</b>，
     * 其余实体一律保持原回退链（刷怪蛋 → 同名物品 → 首字占位），不做任何推断性映射。</p>
     *
     * <p><b>依据来源（26.1.2 原版源码，两份本地源码树各有一份）：</b></p>
     * <ul>
     *     <li>{@code net/minecraft/world/item/Items.java:1510-1596} 的 {@code registerSpawnEgg}
     *         调用表 —— 原版全部刷怪蛋（86 项）都在这里，不在其中的实体即「没有刷怪蛋」；</li>
     *     <li>{@code net/minecraft/world/entity/EntityType.java} 的实体清单 —— 与上表逐一比对，
     *         得到本节登记的实体（巨人 / 幻术师 / 玩家 / 人偶 + 若干投射物与展示类实体）；</li>
     *     <li>第二类（id 不同名）来自 {@code Items.java} 的物品登记名：实体 {@code eye_of_ender}
     *         对应物品 {@code ender_eye}、实体 {@code leash_knot} 对应物品 {@code lead}……
     *         同名规则抓不到，只能逐条写明。</li>
     * </ul>
     *
     * <p>表中查不到、也没有同名物品的（{@code marker} 标记、三个展示实体、{@code interaction}），
     * 保持首字占位：原版确实没有贴切物品，不硬编假图。</p>
     *
     * <p>表里存的是 {@link Item} 实例本身（不是 {@link ItemStack}），因此可以静态初始化：
     * 本类只由控制台界面加载，取用时机晚于注册表绑定；物品堆仍在首次绘制时才构造。</p>
     */
    private static final Map<EntityType<?>, Item> ICON_OVERRIDES = Map.ofEntries(
        // ── 一、原版没有刷怪蛋的生物 ──
        // 巨人是放大版的僵尸（模型与贴图就是僵尸），头颅最贴切
        Map.entry(EntityType.GIANT, Items.ZOMBIE_HEAD),
        // 幻术师是灾厄村民里的施法者（隐身 + 幻象 + 失明），书比药水更贴「施法」
        Map.entry(EntityType.ILLUSIONER, Items.ENCHANTED_BOOK),
        // 玩家没有刷怪蛋；脑袋是唯一且最直白的对应物
        Map.entry(EntityType.PLAYER, Items.PLAYER_HEAD),
        // 人偶（26.1.2 新增的可摆姿势假人）与盔甲架同类，注册表里没有对应物品
        Map.entry(EntityType.MANNEQUIN, Items.ARMOR_STAND),
        // ── 二、实体 id 与物品 id 不同名，同名规则抓不到 ──
        // 拴绳结：物品 id 是 lead（旧代码在这一处单开过特例，现统一收进本表）
        Map.entry(EntityType.LEASH_KNOT, Items.LEAD),
        // 荧光展示框：原版就是用荧光墨囊点出来的，注册表里没有 glow_item_frame 物品
        Map.entry(EntityType.GLOW_ITEM_FRAME, Items.GLOW_INK_SAC),
        Map.entry(EntityType.EYE_OF_ENDER, Items.ENDER_EYE),
        // 旋风人的风弹：实体 breeze_wind_charge，物品只有 wind_charge
        Map.entry(EntityType.BREEZE_WIND_CHARGE, Items.WIND_CHARGE),
        // 火球三件套：注册表里只有 fire_charge / dragon_breath，没有火球物品
        Map.entry(EntityType.FIREBALL, Items.FIRE_CHARGE),
        Map.entry(EntityType.SMALL_FIREBALL, Items.FIRE_CHARGE),
        Map.entry(EntityType.DRAGON_FIREBALL, Items.DRAGON_BREATH),
        // 凋灵之首与凋灵骷髅头同形
        Map.entry(EntityType.WITHER_SKULL, Items.WITHER_SKELETON_SKULL),
        // 潜影贝子弹：用壳指代（潜影贝本体走刷怪蛋）
        Map.entry(EntityType.SHULKER_BULLET, Items.SHULKER_SHELL),
        // 唤魔者尖牙：用唤魔者的刷怪蛋指代来源，原版没有尖牙物品
        Map.entry(EntityType.EVOKER_FANGS, Items.EVOKER_SPAWN_EGG),
        // 不祥之物生成器：与不祥之瓶同属试炼密室体系，瓶子上就是那个图案
        Map.entry(EntityType.OMINOUS_ITEM_SPAWNER, Items.OMINOUS_BOTTLE)
    );

    /**
     * 没有刷怪蛋的实体的兜底图标：先查显式映射表，再按<b>同名物品</b>解析。
     *
     * <p>船 / 竹筏 / 盔甲架 / 矿车 / 展示框 / 画 的物品 id 与实体 id 同名，一条通用规则即可覆盖；
     * 表里的那些（见 {@link #ICON_OVERRIDES}）不走同名规则。解析不到的返回 {@code null}（走首字占位）。</p>
     */
    private static Item fallbackItem(String entityId, EntityType<?> type) {
        Item mapped = ICON_OVERRIDES.get(type);
        if (mapped != null) return mapped;
        Identifier id = Identifier.tryParse(entityId);
        if (id == null) return null;
        Item item = BuiltInRegistries.ITEM.getValue(id);
        return item == null || item == Items.AIR ? null : item;
    }
}
