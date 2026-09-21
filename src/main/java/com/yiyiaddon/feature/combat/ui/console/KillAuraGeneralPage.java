package com.yiyiaddon.feature.combat.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.combat.KillAuraModule;
import com.yiyiaddon.feature.combat.config.KillAuraSettings;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.AttackItems;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.RotationMode;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.ShieldMode;
import com.yiyiaddon.feature.combat.config.KillAuraTexts;
import com.yiyiaddon.feature.combat.ui.KillAuraConsoleScreen;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleStateColumn;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 杀戮光环控制台「常规」页：蓝本默认设置组 9 项。
 *
 * <p>逐字搬运自蓝本 {@code KillAura.java:57-126}（默认设置组，{@code sgGeneral}）；行顺序、设置名、
 * 描述、取值域与落盘时机一字未改，名称与描述取自 {@link KillAuraTexts}。与配置页不同的地方是
 * <b>可见性联动</b>的实现方式：控制台页整页可重建，因此 {@code 武器白名单}（← 持械攻击）与
 * {@code 切回原槽位}（← 自动切换武器）在条件不满足时压根不加入堆叠（蓝本 {@code .visible(...)} 的等价物），
 * 依赖项改动后重建本页。</p>
 *
 * <p>模块没有 {@code persistSettings()}（本任务禁止改模块），落盘统一走
 * {@link ModuleManager#saveSettings} 这个同一入口。</p>
 */
public final class KillAuraGeneralPage {

    /** 名单行的「点击选择」按钮（逐字照星露谷 / 挖矿控制台页） */
    private static final String SELECT_LABEL = "点击选择";
    /**
     * 状态列的下界：本页状态文字只有「未选择（共 N 项）」与「已选 N / M 项」两种，按 999 项量宽即可
     * 覆盖（更宽时由 {@link ConsoleStateColumn} 按实测值加宽，列宽本身不回落）。
     */
    private static final String STATE_LONGEST = "未选择（共 999 项）";

    /** 分段文案：顺序即枚举序数（与 {@link AttackItems} 的声明顺序逐条对应） */
    private static final List<String> ATTACK_ITEMS_LABELS =
        List.of(AttackItems.WEAPONS.displayName(), AttackItems.ALL.displayName());
    /** 分段文案：顺序即 {@link RotationMode} 的序数（始终 / 命中时 / 不旋转） */
    private static final List<String> ROTATION_LABELS = List.of(
        RotationMode.ALWAYS.displayName(), RotationMode.ON_HIT.displayName(), RotationMode.NONE.displayName());
    /** 分段文案：顺序即 {@link ShieldMode} 的序数（忽略 / 破坏 / 无） */
    private static final List<String> SHIELD_LABELS = List.of(
        ShieldMode.IGNORE.displayName(), ShieldMode.BREAK.displayName(), ShieldMode.NONE.displayName());

    /** 武器白名单候选（惰性建一次并缓存：候选就是蓝本 {@code FILTER} 的 8 个登记 ID，注册表运行期不变） */
    private static List<SelectorScreen.Entry> weaponEntries;

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final KillAuraSettings DEFAULTS = new KillAuraSettings();

    private final KillAuraConsoleScreen owner;
    private final KillAuraModule module;
    /** 名单行共用的状态列宽度（见 {@link ConsoleStateColumn}：浮动会把「点击选择」顶得左右移动） */
    private final ConsoleStateColumn stateColumn = new ConsoleStateColumn(STATE_LONGEST);

    public KillAuraGeneralPage(KillAuraConsoleScreen owner, KillAuraModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        KillAuraSettings settings = module.settings();

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_ATTACK_WHEN_HOLDING,
            KillAuraTexts.DESC_ATTACK_WHEN_HOLDING, null,
            List.of(new Ctl(new SettingSegmented(ATTACK_ITEMS_LABELS,
                    () -> settings.attackWhenHolding.ordinal(), this::pickAttackWhenHolding)),
                resetCtl(KillAuraTexts.NAME_ATTACK_WHEN_HOLDING,
                    () -> settings.attackWhenHolding = DEFAULTS.attackWhenHolding))));

        // 武器白名单：只在「持械攻击 = 武器」时加入（对应蓝本 .visible(attackWhenHolding == Weapons)）
        if (settings.attackWhenHolding == AttackItems.WEAPONS) {
            stack.add(listRow(KillAuraTexts.NAME_WEAPONS, KillAuraTexts.DESC_WEAPONS,
                this::weaponStatusText, this::openWeaponSelector, this::clearWeapons,
                () -> module.settings().weapons.isEmpty()));
        }

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_ROTATION,
            KillAuraTexts.DESC_ROTATION, null,
            List.of(new Ctl(new SettingSegmented(ROTATION_LABELS,
                    () -> settings.rotation.ordinal(), this::pickRotation)),
                resetCtl(KillAuraTexts.NAME_ROTATION,
                    () -> settings.rotation = DEFAULTS.rotation))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_AUTO_SWITCH,
            KillAuraTexts.DESC_AUTO_SWITCH, null,
            List.of(new Ctl(toggle(() -> settings.autoSwitch, value -> settings.autoSwitch = value, true)),
                resetCtl(KillAuraTexts.NAME_AUTO_SWITCH,
                    () -> settings.autoSwitch = DEFAULTS.autoSwitch))));

        // 切回原槽位：只在「自动切换武器」为真时加入（对应蓝本 .visible(autoSwitch::get)）
        if (settings.autoSwitch) {
            stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_SWAP_BACK,
                KillAuraTexts.DESC_SWAP_BACK, null,
                List.of(new Ctl(toggle(() -> settings.swapBack, value -> settings.swapBack = value, false)),
                    resetCtl(KillAuraTexts.NAME_SWAP_BACK,
                        () -> settings.swapBack = DEFAULTS.swapBack))));
        }

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_SHIELD_MODE,
            KillAuraTexts.DESC_SHIELD_MODE, null,
            List.of(new Ctl(new SettingSegmented(SHIELD_LABELS,
                    () -> settings.shieldMode.ordinal(), this::pickShieldMode)),
                resetCtl(KillAuraTexts.NAME_SHIELD_MODE,
                    () -> settings.shieldMode = DEFAULTS.shieldMode))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_ONLY_ON_CLICK,
            KillAuraTexts.DESC_ONLY_ON_CLICK, null,
            List.of(new Ctl(toggle(() -> settings.onlyOnClick, value -> settings.onlyOnClick = value, false)),
                resetCtl(KillAuraTexts.NAME_ONLY_ON_CLICK,
                    () -> settings.onlyOnClick = DEFAULTS.onlyOnClick))));

        // 仅注视时攻击：目标页的「多目标数」可见性依赖它，改动后重排一次保证两页一致
        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_ONLY_ON_LOOK,
            KillAuraTexts.DESC_ONLY_ON_LOOK, null,
            List.of(new Ctl(toggle(() -> settings.onlyOnLook, value -> settings.onlyOnLook = value, true)),
                resetCtl(KillAuraTexts.NAME_ONLY_ON_LOOK,
                    () -> settings.onlyOnLook = DEFAULTS.onlyOnLook))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_PAUSE_BARITONE,
            KillAuraTexts.DESC_PAUSE_BARITONE, null,
            List.of(new Ctl(toggle(() -> settings.pauseBaritone, value -> settings.pauseBaritone = value, false)),
                resetCtl(KillAuraTexts.NAME_PAUSE_BARITONE,
                    () -> settings.pauseBaritone = DEFAULTS.pauseBaritone))));
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
     * <p>↻ 的图标与动作与星露谷一致——清空本行已选；{@code empty} 为空态判据（与清空动作读同一份
     * 名单），空态时按钮为禁用态。</p>
     */
    private CompactElement listRow(String title, String description, Supplier<String> status,
                                   Runnable open, Runnable reset, Supplier<Boolean> empty) {
        return new ConsoleRow(owner, () -> title, description, null, List.of(
            new Ctl(new Button(SELECT_LABEL, open)),
            // 列宽走共用固定列：按当前文案各自量宽会把「点击选择」顶着左右浮动
            new Ctl(new SettingText(status, () -> stateColumn.widthOf(status)).alignLeft()),
            // 空态禁用：判据与 clearWeapons() 读同一份名单，逐帧求值见 IconButton#disabledWhen(Supplier)
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, reset).disabledWhen(empty),
                "清空本行已选" + title)));
    }

    /** 开关行：改动落盘；{@code reload} 为真时重建本页（下一行的可见性依赖它） */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter, boolean reload) {
        return new SettingToggle(getter, value -> {
            setter.accept(value);
            persist();
            if (reload) owner.reload();
        });
    }

    // ── 设置写回 ──

    private void pickAttackWhenHolding(int index) {
        if (index < 0 || index >= AttackItems.values().length) return;
        module.settings().attackWhenHolding = AttackItems.values()[index];
        persist();
        // 下一行（武器白名单）的可见性依赖它
        owner.reload();
    }

    private void pickRotation(int index) {
        if (index < 0 || index >= RotationMode.values().length) return;
        module.settings().rotation = RotationMode.values()[index];
        persist();
    }

    private void pickShieldMode(int index) {
        if (index < 0 || index >= ShieldMode.values().length) return;
        module.settings().shieldMode = ShieldMode.values()[index];
        persist();
    }

    /** 立即落盘（模块未提供 persistSettings，走 ModuleManager 的同一入口） */
    private void persist() {
        ModuleManager.saveSettings(module);
    }

    // ── 武器白名单（候选 / 选择器 / 显示名） ──

    /**
     * 武器白名单候选：蓝本 {@code FILTER}（{@code KillAura.java:264}）的 8 个登记 ID。
     *
     * <p><b>显示名与图标走 {@link KillAuraTexts#WEAPON_LABELS}</b>（用户 2026-09-17 口径）：
     * 名字是**类别**（剑 / 斧 / 镐 / 锹 / 锄 / 重锤 / 矛 / 三叉戟），不再写「钻石剑」这种品质名
     * ——键仍是原登记 ID，匹配照旧走物品标签（{@code KillAuraModule#acceptableWeapon}），
     * 所以拿任何品质的同类别武器都算命中；图标统一贴下界合金品质（重锤 / 三叉戟用原物品），
     * 图标 ID 解析不到时退回该键自身的物品。</p>
     *
     * <p>候选表惰性构建并缓存（提前取会把翻译键缓存进静态表），且必须在用户点开选择器时才取
     * ——页面可能在客户端初始化阶段被构造。</p>
     */
    public static List<SelectorScreen.Entry> weaponCandidates() {
        if (weaponEntries == null) {
            List<SelectorScreen.Entry> entries = new ArrayList<>();
            for (String itemId : KillAuraSettings.WEAPON_FILTER) {
                Item item = itemOf(itemId);
                if (item == null) continue;
                KillAuraTexts.WeaponLabel label = KillAuraTexts.weaponLabel(itemId);
                String name = label == null ? item.getDefaultInstance().getHoverName().getString() : label.label();
                Item icon = label == null ? null : itemOf(label.iconId());
                entries.add(new WeaponEntry(itemId, name, icon == null ? item : icon));
            }
            weaponEntries = List.copyOf(entries);
        }
        return weaponEntries;
    }

    /** 按登记 ID 解析物品；解析不到返回 {@code null} */
    private static Item itemOf(String itemId) {
        Identifier id = Identifier.tryParse(itemId);
        return id == null ? null : BuiltInRegistries.ITEM.getValue(id);
    }

    /** 候选总数（= 蓝本 FILTER 的 8 项）：不触发候选表构建 */
    public static int weaponCandidateTotal() {
        return KillAuraSettings.WEAPON_FILTER.size();
    }

    /**
     * 武器显示名（登记 ID → **类别名**，用户 2026-09-17 口径）。
     *
     * <p>名单上写的是「剑 / 斧 / 三叉戟」这种类别，不写「钻石剑」——玩家手里是下界合金剑时，
     * 写品质会让人以为「只认钻石剑」（其实按物品标签匹配，任何品质都吃）。
     * 不在 {@link KillAuraTexts#WEAPON_LABELS} 里的 ID（老配置里的自定义项）回退物品 hoverName，
     * 连物品都认不出就原样显示 ID，不猜。</p>
     */
    public static String weaponDisplayName(String itemId) {
        KillAuraTexts.WeaponLabel label = KillAuraTexts.weaponLabel(itemId);
        if (label != null) return label.label();
        Item item = itemOf(itemId);
        return item == null ? itemId : item.getDefaultInstance().getHoverName().getString();
    }

    /** 名单状态文字（逐字照星露谷 / 挖矿口径）：未选 → {@code 未选择（共 N 项）}；已选 → {@code 已选 N / M 项} */
    private String weaponStatusText() {
        List<String> weapons = module.settings().weapons;
        if (weapons.isEmpty()) return "未选择（共 " + weaponCandidateTotal() + " 项）";
        return "已选 " + weapons.size() + " / " + weaponCandidateTotal() + " 项";
    }

    /** 打开武器白名单选择器：常规模式（左栏「+」加入 / 右栏「-」移除） */
    private void openWeaponSelector() {
        if (owner.client() == null) return;
        List<String> weapons = module.settings().weapons;
        owner.client().setScreen(new SelectorScreen(KillAuraTexts.NAME_WEAPONS, owner.client().screen,
            weaponCandidates(),
            () -> new ArrayList<>(weapons),
            key -> changeWeapons(key, true),
            key -> changeWeapons(key, false)));
    }

    /**
     * 清空武器白名单（↻ 语义同星露谷：空则不动）。
     *
     * <p>回执走 {@link SelectionReceipt}：面板开着时聊天框被藏起来，清空结果只在面板内顶部弹窗看得见；
     * 条数取清空前的真实 size（行尾 ↻ 在空名单上是禁用态，通常拿不到 0）。</p>
     */
    private void clearWeapons() {
        List<String> weapons = module.settings().weapons;
        int count = weapons.size();
        if (count > 0) {
            weapons.clear();
            persist();
        }
        SelectionReceipt.cleared(count);
    }

    /** 名单增删：有实际变化才落盘 */
    private void changeWeapons(String key, boolean add) {
        List<String> weapons = module.settings().weapons;
        boolean changed = add ? !weapons.contains(key) && weapons.add(key) : weapons.remove(key);
        if (!changed) return;
        persist();
    }

    /**
     * 武器候选条目：名字是类别名（剑 / 斧 / …），图标是下界合金品质的代表物品
     * （贴图走物品图标缓存，与挖矿 / 身份选择器同一形态）。
     *
     * @param key  落盘用的登记 ID（匹配判据，永远是蓝本 FILTER 里那一份）
     * @param name 列表上显示的名字（类别名）
     * @param icon 贴图用的物品
     */
    private record WeaponEntry(String key, String name, Item icon) implements SelectorScreen.Entry {

        @Override
        public String title() {
            return name;
        }

        @Override
        public String group() {
            return null;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            return ItemIconCache.getInstance().draw(canvas, icon.getDefaultInstance(), x, y, size);
        }

        /** 与 drawIcon 同一份图标：类别代表物品。 */
        @Override
        public ItemStack iconStack() {
            return icon.getDefaultInstance();
        }
    }
}
