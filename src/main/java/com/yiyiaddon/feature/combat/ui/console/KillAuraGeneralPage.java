package com.yiyiaddon.feature.combat.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.combat.KillAuraModule;
import com.yiyiaddon.feature.combat.config.KillAuraSettings;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.AttackItems;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.RotationMode;
import com.yiyiaddon.feature.combat.config.KillAuraSettings.ShieldMode;
import com.yiyiaddon.feature.combat.config.KillAuraTexts;
import com.yiyiaddon.feature.combat.ui.KillAuraConsoleScreen;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.MinecraftText;
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
    /** 状态文字字号：与 {@code SettingText} 内部字号一致，用于按文本宽度算列宽 */
    private static final float STATE_FONT_SIZE = 11f;

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

    private final KillAuraConsoleScreen owner;
    private final KillAuraModule module;

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
                () -> settings.attackWhenHolding.ordinal(), this::pickAttackWhenHolding)))));

        // 武器白名单：只在「持械攻击 = 武器」时加入（对应蓝本 .visible(attackWhenHolding == Weapons)）
        if (settings.attackWhenHolding == AttackItems.WEAPONS) {
            stack.add(listRow(KillAuraTexts.NAME_WEAPONS, KillAuraTexts.DESC_WEAPONS,
                this::weaponStatusText, this::openWeaponSelector, this::clearWeapons));
        }

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_ROTATION,
            KillAuraTexts.DESC_ROTATION, null,
            List.of(new Ctl(new SettingSegmented(ROTATION_LABELS,
                () -> settings.rotation.ordinal(), this::pickRotation)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_AUTO_SWITCH,
            KillAuraTexts.DESC_AUTO_SWITCH, null,
            List.of(new Ctl(toggle(() -> settings.autoSwitch, value -> settings.autoSwitch = value, true)))));

        // 切回原槽位：只在「自动切换武器」为真时加入（对应蓝本 .visible(autoSwitch::get)）
        if (settings.autoSwitch) {
            stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_SWAP_BACK,
                KillAuraTexts.DESC_SWAP_BACK, null,
                List.of(new Ctl(toggle(() -> settings.swapBack, value -> settings.swapBack = value, false)))));
        }

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_SHIELD_MODE,
            KillAuraTexts.DESC_SHIELD_MODE, null,
            List.of(new Ctl(new SettingSegmented(SHIELD_LABELS,
                () -> settings.shieldMode.ordinal(), this::pickShieldMode)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_ONLY_ON_CLICK,
            KillAuraTexts.DESC_ONLY_ON_CLICK, null,
            List.of(new Ctl(toggle(() -> settings.onlyOnClick, value -> settings.onlyOnClick = value, false)))));

        // 仅注视时攻击：目标页的「多目标数」可见性依赖它，改动后重排一次保证两页一致
        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_ONLY_ON_LOOK,
            KillAuraTexts.DESC_ONLY_ON_LOOK, null,
            List.of(new Ctl(toggle(() -> settings.onlyOnLook, value -> settings.onlyOnLook = value, true)))));

        stack.add(new ConsoleRow(owner, () -> KillAuraTexts.NAME_PAUSE_BARITONE,
            KillAuraTexts.DESC_PAUSE_BARITONE, null,
            List.of(new Ctl(toggle(() -> settings.pauseBaritone, value -> settings.pauseBaritone = value, false)))));
    }

    // ── 行构件 ──

    /**
     * 名单行（行样式照星露谷 / 挖矿控制台页）：名称 + 说明 …… [点击选择] [状态文字] [↻]。
     *
     * <p>↻ 的图标与动作与星露谷一致——清空本行已选，空则静默。</p>
     */
    private CompactElement listRow(String title, String description, Supplier<String> status,
                                   Runnable open, Runnable reset) {
        return new ConsoleRow(owner, () -> title, description, null, List.of(
            new Ctl(new Button(SELECT_LABEL, open)),
            new Ctl(new SettingText(status,
                () -> MinecraftText.measure(status.get(), STATE_FONT_SIZE, false)).alignLeft()),
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, reset))));
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
     * 武器白名单候选：蓝本 {@code FILTER}（{@code KillAura.java:264}）的 8 个登记 ID，显示名用物品的
     * hoverName。候选表惰性构建并缓存（提前取会把翻译键缓存进静态表），且必须在用户点开选择器时
     * 才取——页面可能在客户端初始化阶段被构造。
     */
    public static List<SelectorScreen.Entry> weaponCandidates() {
        if (weaponEntries == null) {
            List<SelectorScreen.Entry> entries = new ArrayList<>();
            for (String itemId : KillAuraSettings.WEAPON_FILTER) {
                Identifier id = Identifier.tryParse(itemId);
                Item item = id == null ? null : BuiltInRegistries.ITEM.getValue(id);
                if (item != null) entries.add(new WeaponEntry(itemId, item));
            }
            weaponEntries = List.copyOf(entries);
        }
        return weaponEntries;
    }

    /** 候选总数（= 蓝本 FILTER 的 8 项）：不触发候选表构建 */
    public static int weaponCandidateTotal() {
        return KillAuraSettings.WEAPON_FILTER.size();
    }

    /** 武器显示名（登记 ID → 物品 hoverName；认不出的 ID 原样显示，不猜） */
    public static String weaponDisplayName(String itemId) {
        Identifier id = Identifier.tryParse(itemId);
        Item item = id == null ? null : BuiltInRegistries.ITEM.getValue(id);
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

    /** 清空武器白名单（↻ 语义同星露谷：空则静默 return） */
    private void clearWeapons() {
        if (module.settings().weapons.isEmpty()) return;
        module.settings().weapons.clear();
        persist();
    }

    /** 名单增删：有实际变化才落盘 */
    private void changeWeapons(String key, boolean add) {
        List<String> weapons = module.settings().weapons;
        boolean changed = add ? !weapons.contains(key) && weapons.add(key) : weapons.remove(key);
        if (!changed) return;
        persist();
    }

    /** 武器候选条目：贴图走物品图标缓存（与挖矿 / 身份选择器同一形态） */
    private record WeaponEntry(String key, Item item) implements SelectorScreen.Entry {

        @Override
        public String title() {
            return item.getDefaultInstance().getHoverName().getString();
        }

        @Override
        public String group() {
            return null;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            return ItemIconCache.getInstance().draw(canvas, item.getDefaultInstance(), x, y, size);
        }
    }
}
