package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.config.EnchantSettings;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingText;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 自动附魔控制台的多选词条页：<b>原版附魔分类</b>（BOOK 的 8 组）与 <b>自动附魔分类</b>
 * （CUSTOM 的 5 组）共用本页，按传入的分组集合装配。
 *
 * <p>控件的设置名、候选词条与描述逐字沿旧：控件名 = 分组名（{@code 剑附魔属性} 等 13 个），
 * 描述统一 {@code 选择需要收集的附魔属性}（旧 {@code EnchantmentSelectSetting} 的 description）；
 * 状态文字沿用旧计数格式 {@code 已选择 N 项}（旧 {@code countText()}），
 * 收尾的 {@code ↻} 清空本组已选（旧重置按钮，图标同旧 {@code GuiRenderer.RESET}）。</p>
 *
 * <p><b>交互按本项目控制台</b>：点行文字固定 {@code 点击选择}，打开本项目的双栏加减选择器
 * （{@link SelectorScreen}，窗口标题 = 控件名，与旧多选窗口同名）；读写走
 * {@link EnchantSettings#applySelection(List, java.util.Collection)} /
 * {@link EnchantSettings#selectedIn(List)}，改完立即 {@code persistSettings()} 落盘。
 * 13 个组各自折叠（{@link FoldSection}，与自动挖矿 Baritone 页同做法），折叠状态存窗口侧、重建不丢。</p>
 */
public final class EnchantSelectPage {

    /** 点行文字（本项目选择器行的固定入口文案，与自动挖矿「目标选择」页同源） */
    private static final String SELECT_LABEL = "点击选择";
    /** 控件描述：13 个多选控件的 description 在旧项目里统一是这一句（逐字） */
    private static final String DESCRIPTION = "选择需要收集的附魔属性";
    /** 状态文字列宽：只放「已选择 N 项」，够用且不挤压左侧说明 */
    private static final float STATE_WIDTH = 96f;

    private final EnchantConsoleScreen owner;
    private final EnchantModule module;
    private final List<EnchantSettings.EnchantGroup> groups;
    /** 折叠状态的键前缀（两页各用一套键，避免同名的展开状态互相串） */
    private final String stateKeyPrefix;

    /**
     * @param owner          控制台宿主
     * @param module         归属模块
     * @param groups         本页要装配的分组（{@link EnchantSettings#BOOK_GROUPS} 或 {@link EnchantSettings#CUSTOM_GROUPS}）
     * @param stateKeyPrefix 折叠状态键前缀（{@code book} / {@code custom}）
     */
    public EnchantSelectPage(EnchantConsoleScreen owner, EnchantModule module,
                             List<EnchantSettings.EnchantGroup> groups, String stateKeyPrefix) {
        this.owner = owner;
        this.module = module;
        this.groups = groups;
        this.stateKeyPrefix = stateKeyPrefix;
    }

    /** 装配本页内容：一组一个折叠块，块内一行「点击选择 + 计数 + ↻」。 */
    public void build(CompactStack stack) {
        for (EnchantSettings.EnchantGroup group : groups) {
            FoldSection section = new FoldSection("§7§l" + group.title() + " §8(点标题可收起)",
                stateKeyPrefix + ":" + group.title(), owner.collapsedSections());
            section.content().add(row(group));
            stack.add(section);
        }
    }

    // ── 行构件 ──

    private CompactElement row(EnchantSettings.EnchantGroup group) {
        return new ConsoleRow(owner, () -> "", null, DESCRIPTION, List.of(
            new Ctl(new Button(SELECT_LABEL, () -> openSelector(group))),
            new Ctl(new SettingText(
                () -> "已选择 " + module.settings().selectedCount(group.entries()) + " 项",
                STATE_WIDTH).alignLeft()),
            // 空态禁用：判据来自本组已选计数（与 reset(group) 的整组置 false 同源），逐帧求值见
            // IconButton#disabledWhen(Supplier)；悬停说明按组名给出（本行的行尾注释已经是
            // 「选择需要收集的附魔属性」，故说明只写清空的对象，不重复那句）
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, () -> reset(group))
                .disabledWhen(() -> module.settings().selectedCount(group.entries()) == 0),
                "清空本组已选" + group.title())))
            // 控件入口行的物品图标：旧 EnchantmentSelectSetting:55 用附魔书（Items.ENCHANTED_BOOK）
            .icon(() -> Items.ENCHANTED_BOOK.getDefaultInstance());
    }

    /** 打开本组的双栏选择器：标题 = 控件名（旧多选窗口标题口径），左加右减即时写回 */
    private void openSelector(EnchantSettings.EnchantGroup group) {
        List<SelectorScreen.Entry> entries = new ArrayList<>();
        for (String name : group.entries()) entries.add(new EnchantEntry(name));
        Screen parent = Minecraft.getInstance() == null ? null : Minecraft.getInstance().gui.screen();
        SelectorScreen screen = new SelectorScreen(group.title(), parent, entries,
            () -> module.settings().selectedIn(group.entries()),
            key -> change(group, key, true),
            key -> change(group, key, false));
        if (Minecraft.getInstance() != null) Minecraft.getInstance().gui.setScreen(screen);
    }

    /** 增 / 减一个词条：整组回写（旧 {@code apply} 语义）并立即落盘 */
    private void change(EnchantSettings.EnchantGroup group, String entry, boolean add) {
        List<String> selected = module.settings().selectedIn(group.entries());
        if (add) {
            if (!selected.contains(entry)) selected.add(entry);
        } else {
            selected.remove(entry);
        }
        module.settings().applySelection(group.entries(), selected);
        module.persistSettings();
    }

    /** ↻：清空本组已选（旧重置按钮语义：整组置 false），立即落盘 */
    private void reset(EnchantSettings.EnchantGroup group) {
        module.settings().applySelection(group.entries(), List.of());
        module.persistSettings();
    }

    /**
     * 词条候选项：键与标题都是词条名（旧项目 {@code EnchantmentSelectSetting} 的候选就是 189 个词条名），
     * 不分组。
     *
     * <p><b>行图标</b>：词条本身没有专属物品（附魔不是物品），因此统一用附魔书做图标 —— 旧窗口这里是
     * 纯文字列表，本项目按「列表行要有物品图」的统一口径补上；不按词条区分图标，是因为原版没有任何
     * 词条到物品的映射，硬编一张表只会出现与真实物品不符的假图。</p>
     */
    private static final class EnchantEntry implements SelectorScreen.Entry {

        private final String key;
        /** 惰性构造：构建物品栈需要注册表已绑定，不能在设置载入期做 */
        private ItemStack iconStack;

        private EnchantEntry(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public String title() {
            return key;
        }

        @Override
        public String group() {
            return null;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            if (iconStack == null) iconStack = new ItemStack(Items.ENCHANTED_BOOK);
            return ItemIconCache.getInstance().draw(canvas, iconStack, x, y, size);
        }

        /** 与 drawIcon 同一份图标：统一的附魔书。 */
        @Override
        public ItemStack iconStack() {
            return new ItemStack(Items.ENCHANTED_BOOK);
        }
    }
}
