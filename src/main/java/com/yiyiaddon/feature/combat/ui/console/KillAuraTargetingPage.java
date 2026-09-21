package com.yiyiaddon.feature.combat.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.combat.KillAuraModule;
import com.yiyiaddon.feature.combat.config.KillAuraSettings;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.EntityAge;
import com.yiyiaddon.feature.combat.config.KillAuraTexts;
import com.yiyiaddon.feature.combat.target.AttackableEntityTypes;
import com.yiyiaddon.feature.combat.target.SortPriority;
import com.yiyiaddon.feature.combat.ui.KillAuraConsoleScreen;
import com.yiyiaddon.platform.identity.EntityDisplayNames;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleStateColumn;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.ItemIconCache;
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
import net.minecraft.world.entity.MobCategory;
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
    /**
     * 状态列的下界：本页状态文字只有「未选择（共 N 项）」与「已选 N / M 项」两种，按 999 项量宽即可
     * 覆盖（更宽时由 {@link ConsoleStateColumn} 按实测值加宽，列宽本身不回落）。
     */
    private static final String STATE_LONGEST = "未选择（共 999 项）";

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

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final KillAuraSettings DEFAULTS = new KillAuraSettings();

    private final KillAuraConsoleScreen owner;
    private final KillAuraModule module;
    /** 名单行共用的状态列宽度（见 {@link ConsoleStateColumn}：浮动会把「点击选择」顶得左右移动） */
    private final ConsoleStateColumn stateColumn = new ConsoleStateColumn(STATE_LONGEST);

    public KillAuraTargetingPage(KillAuraConsoleScreen owner, KillAuraModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        KillAuraSettings settings = module.settings();

        stack.add(listRow(KillAuraTexts.NAME_ENTITY_TYPES, KillAuraTexts.DESC_ENTITY_TYPES,
            this::entityStatusText, this::openEntitySelector, this::clearEntityTypes,
            () -> module.settings().entityTypes.isEmpty()));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_PRIORITY,
            KillAuraTexts.DESC_PRIORITY, null,
            List.of(new Ctl(new SettingSegmented(PRIORITY_LABELS,
                    () -> settings.priority.ordinal(), this::pickPriority)),
                resetCtl(KillAuraTexts.NAME_PRIORITY,
                    () -> settings.priority = DEFAULTS.priority))));

        // 多目标数：只在「仅注视时攻击」为假时加入（对应蓝本 .visible(() -> !onlyOnLook.get())）
        if (!settings.onlyOnLook) {
            stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_MAX_TARGETS,
                KillAuraTexts.DESC_MAX_TARGETS, null,
                List.of(new Ctl(intBox(1, 5, () -> settings.maxTargets, value -> settings.maxTargets = value)),
                    resetCtl(KillAuraTexts.NAME_MAX_TARGETS,
                        () -> settings.maxTargets = DEFAULTS.maxTargets))));
        }

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_RANGE,
            KillAuraTexts.DESC_RANGE, null,
            List.of(new Ctl(doubleBox(() -> settings.range, value -> settings.range = value)),
                resetCtl(KillAuraTexts.NAME_RANGE, () -> settings.range = DEFAULTS.range))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_WALLS_RANGE,
            KillAuraTexts.DESC_WALLS_RANGE, null,
            List.of(new Ctl(doubleBox(() -> settings.wallsRange, value -> settings.wallsRange = value)),
                resetCtl(KillAuraTexts.NAME_WALLS_RANGE,
                    () -> settings.wallsRange = DEFAULTS.wallsRange))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_PASSIVE_MOB_AGE_FILTER,
            KillAuraTexts.DESC_PASSIVE_MOB_AGE_FILTER, null,
            List.of(new Ctl(new SettingSegmented(ENTITY_AGE_LABELS,
                    () -> settings.passiveMobAgeFilter.ordinal(), this::pickPassiveAge)),
                resetCtl(KillAuraTexts.NAME_PASSIVE_MOB_AGE_FILTER,
                    () -> settings.passiveMobAgeFilter = DEFAULTS.passiveMobAgeFilter))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_HOSTILE_MOB_AGE_FILTER,
            KillAuraTexts.DESC_HOSTILE_MOB_AGE_FILTER, null,
            List.of(new Ctl(new SettingSegmented(ENTITY_AGE_LABELS,
                    () -> settings.hostileMobAgeFilter.ordinal(), this::pickHostileAge)),
                resetCtl(KillAuraTexts.NAME_HOSTILE_MOB_AGE_FILTER,
                    () -> settings.hostileMobAgeFilter = DEFAULTS.hostileMobAgeFilter))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_IGNORE_NAMED,
            KillAuraTexts.DESC_IGNORE_NAMED, null,
            List.of(new Ctl(toggle(() -> settings.ignoreNamed, value -> settings.ignoreNamed = value)),
                resetCtl(KillAuraTexts.NAME_IGNORE_NAMED,
                    () -> settings.ignoreNamed = DEFAULTS.ignoreNamed))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_IGNORE_PASSIVE,
            KillAuraTexts.DESC_IGNORE_PASSIVE, null,
            List.of(new Ctl(toggle(() -> settings.ignorePassive, value -> settings.ignorePassive = value)),
                resetCtl(KillAuraTexts.NAME_IGNORE_PASSIVE,
                    () -> settings.ignorePassive = DEFAULTS.ignorePassive))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_IGNORE_TAMED,
            KillAuraTexts.DESC_IGNORE_TAMED, null,
            List.of(new Ctl(toggle(() -> settings.ignoreTamed, value -> settings.ignoreTamed = value)),
                resetCtl(KillAuraTexts.NAME_IGNORE_TAMED,
                    () -> settings.ignoreTamed = DEFAULTS.ignoreTamed))));
    }

    // ── 行构件 ──

    /**
     * 行内「恢复默认」：写回出厂值 → 落盘（与改动同一入口 {@link #persist()}）→ 刷新本页。
     *
     * @param writeDefault 只负责把这一行的设置写回出厂值，持久化与刷新由本方法统一收口
     */
    private Ctl resetCtl(String label, Runnable writeDefault) {
        return ConsoleWidgets.resetCtl(() -> {
            writeDefault.run();
            persist();
            owner.reload();
        }, label);
    }

    /**
     * 名单行（行样式照星露谷 / 挖矿控制台页）：名称 + 说明 …… [点击选择] [状态文字] [↻]。
     *
     * @param empty ↻ 的空态判据；与 {@link #clearEntityTypes()} 读的是同一份名单
     */
    private CompactElement listRow(String title, String description, Supplier<String> status,
                                   Runnable open, Runnable reset, Supplier<Boolean> empty) {
        return new ConsoleRow(owner, () -> title, description, null, List.of(
            new Ctl(new Button(SELECT_LABEL, open)),
            // 列宽走共用固定列：按当前文案各自量宽会把「点击选择」顶着左右浮动
            new Ctl(new SettingText(status, () -> stateColumn.widthOf(status)).alignLeft()),
            // 空态禁用：判据与 clearEntityTypes() 读同一份名单，逐帧求值见 IconButton#disabledWhen(Supplier)
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, reset).disabledWhen(empty),
                "清空本行已选" + title)));
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
     * <p><b>为什么不再分「原版 / 自定义」</b>：用户 2026-09-16 要求「像旧框架一样分类好的」——
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

    /**
     * 实体显示名（登记 ID → {@link EntityDisplayNames}；认不出的 ID 原样显示，不猜）。
     *
     * <p>名字不再直接取 {@code EntityType#getDescription()}：客户端若没带中文实体译名，那里给的是
     * 英文原名（用户 2026-09-21 报的「没汉化」），随包中文表会兜住，见 {@link EntityDisplayNames}。</p>
     */
    public static String entityDisplayName(String typeId) {
        Identifier id = Identifier.tryParse(typeId);
        EntityType<?> type = id == null ? null : BuiltInRegistries.ENTITY_TYPE.getValue(id);
        return type == null ? typeId : EntityDisplayNames.display(type);
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
     * <p><b>行图标四级回退</b>（用户 2026-09-16：「要不你就全部改成这种类型的 不要生怪蛋了」）：</p>
     * <ol>
     *     <li><b>实体渲染图</b> —— {@link ItemIconCache#drawEntityModel}，<b>所有生物（非 MISC）都走它</b>。
     *         这是真正的实体模型渲染，整列风格统一；上一版是「有刷怪蛋的走蛋、没蛋的才走模型」，
     *         一列里混着两种画法，用户看到巨人（模型）夹在一片刷怪蛋里直接问「巨人是什么？？」；</li>
     *     <li><b>刷怪蛋物品图标</b> —— {@link ItemIconCache#hasSpawnEgg} + {@link ItemIconCache#drawEntity}，
     *         只给模型渲不出来的（玩家 / 假人这类没有注册渲染器的）与非生物（MISC）；</li>
     *     <li><b>显式映射表 / 同名物品</b> —— 见 {@link #ICON_OVERRIDES}（每条都注明为什么只能是静态物品）
     *         与同名物品规则（船 / 竹筏 / 盔甲架 / 矿车 / 展示框 / 画 的实体 id 与物品 id 同名）；</li>
     *     <li><b>首字占位</b> —— 三者都没有的，画一个弱色圆角框 + 显示名首字。保证每一行都有图标，不留空位。</li>
     * </ol>
     *
     * <p>模型渲染有「本帧还没截取好」的中间态：此时<b>保持空图标</b>而不落回下一级，
     * 否则列表会先闪一张刷怪蛋再换成模型，看得见的抖动比空一帧更糟。</p>
     */
    private static final class EntityEntry implements SelectorScreen.Entry {

        private final String key;
        private final EntityType<?> type;
        private final String group;
        /** 第 3 级回退图标的物品；{@code null} = 表中没有、也没有同名物品，走首字占位 */
        private final Item fallbackItem;
        /** 第 3 级回退的物品堆：首次绘制时才构造（构建物品栈要注册表已绑定，不能在设置载入期做） */
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
            return EntityDisplayNames.display(type);
        }

        /**
         * 名称右侧显示实体 ID。
         *
         * <p>用户 2026-09-16 对着「巨人」一行问「巨人是什么？？」——那是原版译名（{@code minecraft:giant}，
         * 原版不刷出来的六倍僵尸），光看名字认不出。把真实 ID 摆在名字旁边，任何一个看不懂的显示名
         * 都能自己对回原版实体。</p>
         */
        @Override
        public String detail() {
            return key;
        }

        @Override
        public String group() {
            return group;
        }

        /** 与 drawIcon 同一份图标：实体模型 / 刷怪蛋都走这个实体类型。 */
        @Override
        public EntityType<?> iconEntity() {
            return type;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            ItemIconCache cache = ItemIconCache.getInstance();
            // ① 生物（非 MISC）：渲实体自己的模型 —— 与「已选」栏里的行同一来源，整列风格一致
            if (type.getCategory() != MobCategory.MISC) {
                if (cache.drawEntityModel(canvas, type, x, y, size)) return true;
                // 这一帧还在截取：先空着（宁可空一帧，也不要先闪一张兜底图标再换成实体渲染图）
                if (!cache.isEntityModelUnsupported(type)) return true;
            }
            // ② 模型不可用（玩家 / 投射物 / 标记这类没有渲染器的）或本来就是非生物 → 刷怪蛋
            if (cache.drawEntity(canvas, type, x, y, size)) return true;
            // ③ 静态物品兜底
            if (fallbackItem != null) {
                if (fallbackStack == null) fallbackStack = new ItemStack(fallbackItem);
                if (cache.draw(canvas, fallbackStack, x, y, size)) return true;
            }
            // ④ 首字占位
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
     * 没有刷怪蛋（或实体 id 与物品 id 不同名）的实体的<b>显式图标映射表 = 静态兜底</b>。
     *
     * <p><b>它现在是第 3 级回退，不再是「没有刷怪蛋」的最终答案：</b>第 ② 级会用
     * {@link ItemIconCache#drawEntityModel} 渲实体自己的模型（巨人 / 幻术师这类原版不给刷怪蛋的生物），
     * 只有模型路径确实不可用时才会落到本表。本表每一条都注明<b>为什么它只能是静态物品</b>。</p>
     *
     * <p><b>依据来源（26.1.2 原版源码，两份本地源码树各有一份）：</b></p>
     * <ul>
     *     <li>{@code net/minecraft/world/item/Items.java:1510-1596} 的 {@code registerSpawnEgg}
     *         调用表 —— 原版全部刷怪蛋（86 项）都在这里，不在其中的实体即「没有刷怪蛋」；</li>
     *     <li>{@code net/minecraft/client/renderer/entity/EntityRenderers.java} 的渲染器登记表 ——
     *         判断某实体能不能走第 ② 级的实体渲染图（巨人 {@code GiantMobRenderer}、幻术师
     *         {@code IllusionerRenderer} 都在表里；玩家 / 假人不在，只有专用渲染器）；</li>
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
        // ── 一、没有刷怪蛋的生物：这一级只在实体渲染图不可用时兜底 ──
        // 巨人：原版 GiantMobRenderer 用的就是僵尸模型 + 僵尸贴图（放大 6 倍），正常走实体渲染图；
        // 模型渲不出来时以僵尸刷怪蛋兜底 —— 同样是「僵尸形象」，不会出现用户点名的绿色方块
        Map.entry(EntityType.GIANT, Items.ZOMBIE_SPAWN_EGG),
        // 幻术师：灾厄村民里的施法者，同族中形象最接近唤魔者，模型渲不出来时以唤魔者刷怪蛋兜底
        Map.entry(EntityType.ILLUSIONER, Items.EVOKER_SPAWN_EGG),
        // 玩家：玩家渲染器只认 AbstractClientPlayer（合成的玩家实体拿不到渲染器，模型路径必然不可用）；
        // 真实玩家头像另有出路（管理员检测页走 PlayerFaceCache），这里是通用兜底，脑袋最直白
        Map.entry(EntityType.PLAYER, Items.PLAYER_HEAD),
        // 人偶：假人渲染器只认 ClientMannequin（由客户端世界生成的实体），合成的 MANNEQUIN 实体拿不到
        // 渲染器；它与盔甲架同类（可摆姿势的摆件），注册表里也没有对应物品，用盔甲架图标
        Map.entry(EntityType.MANNEQUIN, Items.ARMOR_STAND),
        // ── 二、非生物实体（都是 MISC 分类，不进第 ② 级）：没有「生物形象」可取，静态物品更清楚 ──
        // 拴绳结：本体只是拴在栅栏上的一个绳结，实体渲染图是几个像素的结，拴绳物品更易辨识
        Map.entry(EntityType.LEASH_KNOT, Items.LEAD),
        // 荧光展示框：空框的渲染图与普通物品展示框完全同形，只有荧光墨囊图标能体现「荧光」
        Map.entry(EntityType.GLOW_ITEM_FRAME, Items.GLOW_INK_SAC),
        // 以下全是抛射物 / 攻击效果 / 不可见实体：原版要么用 ThrownItemRenderer 把物品模型画进 3D
        // （渲染图与物品图标本就是同一个东西），要么（不祥之物生成器）本体根本不渲染，故一律静态物品
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
        // 唤魔者尖牙：瞬时攻击效果实体（不是生物），用唤魔者的刷怪蛋指代来源，原版没有尖牙物品
        Map.entry(EntityType.EVOKER_FANGS, Items.EVOKER_SPAWN_EGG),
        // 不祥之物生成器：原版本体不渲染任何东西（不可见实体），渲染图必然是空的，只能用物品表示
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
