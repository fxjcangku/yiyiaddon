package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.gear.GearCatalog;
import com.yiyiaddon.feature.enchant.gear.GearEnchantConfig;
import com.yiyiaddon.feature.enchant.gear.GearEnchantData;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.feature.enchant.vanilla.VanillaEnchantDatabase;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingText;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 「原版装备附魔配置」独立窗口。
 *
 * <p><b>形态按用户口径</b>（2026-09-16：「我要的是点击选择原版附魔装备单独弹出一个窗口配置，
 * 我的旧项目就是这样的」）：控制台「原版装备附魔」页只留一行摘要 + 入口，装备选择、极品方案、
 * 目标附魔全部在本窗口内完成。旧项目 {@code GearEnchantScreen} 就是独立窗口，本类与它对齐。</p>
 *
 * <p><b>装备选择</b>：不再用「点一下跳下一档」的三级切换（用户同日：「为什么点击就直接帮我选择了？
 * 我都没看见列表」），改成打开按<b>大类分组</b>的候选列表，点行即选中 —— 候选天然分组、
 * 一屏内可见，不需要滚动查找。</p>
 *
 * <p><b>重建</b>：本窗口自己管理 {@code content()}，任何写配置的动作后调 {@link #rebuild()}；
 * 同时通知控制台 {@link EnchantConsoleScreen#reload()}，保证关窗后主页面的状态条与摘要同步。</p>
 */
final class GearEnchantConfigScreen extends PanelScreen implements ConsoleHost {

    /** 设置描述：旧 {@code GearEnchantSetting} 的 description（逐字） */
    private static final String GEAR_DESCRIPTION = "按大类分组的装备列表，点行即选中";
    /** 状态文字字号：与 {@code SettingText} 内部字号一致，用于按文本宽度算列宽 */
    private static final float STATE_FONT_SIZE = 11f;
    /** 注册表不可用（未进世界）时的等级上限兜底；真实上限由 {@code GearEnchantData.maxLevelOf} 给出 */
    private static final int FALLBACK_MAX_LEVEL = 10;
    /** 大类标题前缀（与项目其它选择器一致的分组标题写法） */
    private static final String GROUP_PREFIX = "§b§l▌ ";
    /** 认不出大类的装备归到这一组，避免漏项 */
    private static final String GROUP_OTHER = "§7§l▌ 其他";

    private final EnchantConsoleScreen console;
    private final EnchantModule module;
    /** 目标附魔折叠块的收起状态：窗口生命周期内有效，重建不丢 */
    private final Set<String> collapsed = new HashSet<>();

    GearEnchantConfigScreen(EnchantConsoleScreen console, EnchantModule module) {
        super("原版装备附魔配置", console);
        this.console = console;
        this.module = module;
        rebuild();
    }

    @Override
    public void tip(String text, float x, float y) {
        // 悬停提示层只有控制台有；本窗口的行控件登记上来的提示一律忽略
    }

    // ── 装配 ──

    /** 重建整页（写配置后调用；控件树每次重建，读值一律走 getter） */
    private void rebuild() {
        CompactStack stack = content();
        stack.clear();

        GearEnchantConfig config = new GearEnchantConfig(module.settings().gearEnchantConfig);
        String gearId = config.gearId();
        GearEnchantData.GearDefinition gear = GearEnchantData.get().gear(gearId == null ? "" : gearId);

        // ── 装备选择（旧 :95-160 的三级下拉，本项目换成一次分组列表）──
        stack.add(new ConsoleRow(this, () -> "装备", GEAR_DESCRIPTION, null, List.of(
            new Ctl(new Button(gear == null ? "点击选择" : gear.name, this::openGearPicker)),
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, this::resetConfig)))));

        if (gear == null) {
            stack.add(new Note(this, "§7当前未选择装备：点上面的按钮挑一件，即可配置极品方案与目标附魔。"));
            return;
        }
        if (gear.profiles.isEmpty()) {
            stack.add(new Note(this, "§7该装备 §c暂无极品方案§7，仅作为官方装备数据收录。"));
            return;
        }

        // ── 极品方案（旧 :168-180：多方案才是下拉，单方案给标签）──
        // 方案名文本的列宽按实际文本量给（同星露谷 / 挖矿 / 杀戮光环三页的状态列口径）：
        // SettingText 默认 200 宽，而方案名只有几十像素，多出来的空白会把「点击选择」顶到行中间去
        // ——用户 2026-09-16 原话「怎么在中间？」。收紧列宽后本行与「装备」行一样贴右。
        List<Ctl> profileControls = new ArrayList<>();
        if (gear.profiles.size() > 1) {
            profileControls.add(new Ctl(new Button("点击选择", () -> openProfilePicker(gear))));
        }
        profileControls.add(new Ctl(new SettingText(() -> profileText(gear),
            () -> MinecraftText.measure(profileText(gear), STATE_FONT_SIZE, false)).alignLeft()));
        stack.add(new ConsoleRow(this, () -> "极品方案", null, null, profileControls));

        // ── 目标附魔逐条（旧 :187-236）：名称 + 排除 / 核心 + 等级加减 ──
        GearEnchantData.GearProfile profile = currentProfile(gear);
        if (profile == null) return;

        FoldSection section = new FoldSection("§7§l目标附魔 §8(点标题可收起)",
            "gear:" + gear.id, collapsed);
        for (GearEnchantData.TargetDefinition target : profile.targets) {
            section.content().add(targetRow(config, target));
        }
        stack.add(section);
    }

    // ── 行构件 ──

    /**
     * 极品方案行的文本：{@code §b<方案名>}；配置里认不出方案时给方案表的第一项（与旧行渲染同一口径：
     * 认不出就按默认方案显示，不显示空值）。
     */
    private String profileText(GearEnchantData.GearDefinition gear) {
        GearEnchantData.GearProfile current = currentProfile(gear);
        return "§b" + (current == null ? gear.profiles.get(0).name : current.name);
    }

    /**
     * 单个目标附魔行（旧 {@code addEnchantRow:204-236}）：
     * 名称（被排除时压暗为 {@code §8}）+ 排除 / 启用按钮（不可排除的核心附魔显示 {@code §8核心}）
     * + 等级加减（上限 = 注册表真实最大等级）。
     */
    private CompactElement targetRow(GearEnchantConfig config, GearEnchantData.TargetDefinition target) {
        boolean excluded = config.isExcluded(target.id);

        List<Ctl> controls = new ArrayList<>();
        if (target.excludable) {
            controls.add(new Ctl(new Button(excluded ? "§c已排除" : "§a启用", () -> {
                config.setExcluded(target.id, !excluded);
                module.persistSettings();
                // 名称配色随排除状态变化，整页重建后生效
                rebuild();
            })));
        } else {
            controls.add(new Ctl(new SettingText(() -> "§8核心", 40f)));
        }
        controls.add(new Ctl(levelBox(config, target)));

        return new ConsoleRow(this,
            () -> (config.isExcluded(target.id) ? "§8" : "§f") + target.name + " §e"
                + roman(currentLevel(config, target)),
            null, null, controls);
    }

    /** 等级加减框：范围 [1, 注册表真实最大等级]，步进 1、无滑块（旧界面是 −/＋ 两个按钮） */
    private SettingNumberBox levelBox(GearEnchantConfig config, GearEnchantData.TargetDefinition target) {
        int max = GearEnchantData.get().maxLevelOf(target.id);
        return new SettingNumberBox(1, max >= 1 ? max : FALLBACK_MAX_LEVEL, 1, "%.0f",
            () -> (double) currentLevel(config, target),
            value -> {
                config.setLevel(target.id, (int) Math.round(value));
                module.persistSettings();
            });
    }

    /** 当前等级：配置里没有该条时用方案默认等级（旧 {@code levelOf < 1 ? target.level : levelOf}） */
    private static int currentLevel(GearEnchantConfig config, GearEnchantData.TargetDefinition target) {
        int level = config.levelOf(target.id);
        return level < 1 ? target.level : level;
    }

    /** 当前方案：按配置里的方案 ID 在装备的方案表里查，查不到返回 {@code null} */
    private GearEnchantData.GearProfile currentProfile(GearEnchantData.GearDefinition gear) {
        String profileId = new GearEnchantConfig(module.settings().gearEnchantConfig).profileId();
        if (profileId == null) return null;
        for (GearEnchantData.GearProfile profile : gear.profiles) {
            if (profile.id.equals(profileId)) return profile;
        }
        return null;
    }

    // ── 选择器 ──

    /**
     * 装备候选：静态库里的全部装备，按大类分组，带物品图标。
     *
     * <p>分组顺序固定取 {@link GearCatalog#categories()} 的顺序（组内顺序取静态库顺序），
     * 因此同一件装备在列表里的位置稳定，不会因过滤词变化而跳位。</p>
     */
    private void openGearPicker() {
        List<SelectorScreen.Entry> entries = new ArrayList<>();
        List<VanillaEnchantDatabase.GearCandidateRule> rules = VanillaEnchantDatabase.get().gears();

        for (GearCatalog.Category category : GearCatalog.categories()) {
            List<SelectorScreen.Entry> group = new ArrayList<>();
            for (VanillaEnchantDatabase.GearCandidateRule rule : rules) {
                if (!category.key().equals(categoryKeyOf(rule))) continue;
                group.add(gearChoice(rule));
            }
            if (group.isEmpty()) continue;
            String title = GROUP_PREFIX + category.title();
            for (int i = 0; i < group.size(); i++) {
                entries.add(((GearChoice) group.get(i)).withGroup(title));
            }
        }

        // 兜底：静态库里出现了不认识的 category，也要能选到（否则用户会找不到某件装备）
        for (VanillaEnchantDatabase.GearCandidateRule rule : rules) {
            if (knownCategory(categoryKeyOf(rule))) continue;
            entries.add(gearChoice(rule).withGroup(GROUP_OTHER));
        }

        if (entries.isEmpty()) return;
        openScreen(SelectorScreen.pick("选择装备", currentScreen(), entries, this::applyGear));
    }

    /** 极品方案选择器：候选 = 当前装备的方案表，点行即选中（旧 :168-180 的下拉） */
    private void openProfilePicker(GearEnchantData.GearDefinition gear) {
        List<SelectorScreen.Entry> entries = new ArrayList<>();
        for (GearEnchantData.GearProfile profile : gear.profiles) {
            entries.add(new LabelEntry(profile.id, profile.name));
        }
        openScreen(SelectorScreen.pick("极品方案", currentScreen(), entries, profileId -> {
            new GearEnchantConfig(module.settings().gearEnchantConfig).applyProfile(profileId);
            module.persistSettings();
            console.reload();
            rebuild();
        }));
    }

    /** 写入装备并重建（旧 :110-160 每次切换都 {@code rebuild()}） */
    private void applyGear(String gearId) {
        new GearEnchantConfig(module.settings().gearEnchantConfig).applyGear(gearId);
        module.persistSettings();
        console.reload();
        rebuild();
    }

    /** ↻：清空装备附魔配置（旧设置行的重置按钮语义：回到默认空列表） */
    private void resetConfig() {
        module.settings().gearEnchantConfig.clear();
        module.persistSettings();
        console.reload();
        rebuild();
    }

    private static boolean knownCategory(String categoryKey) {
        for (GearCatalog.Category category : GearCatalog.categories()) {
            if (category.key().equals(categoryKey)) return true;
        }
        return false;
    }

    private static GearChoice gearChoice(VanillaEnchantDatabase.GearCandidateRule rule) {
        GearEnchantData.GearDefinition gear = GearEnchantData.get().gear(rule.itemId());
        String name = gear == null || gear.name == null || gear.name.isBlank()
            ? rule.itemId() : gear.name;
        return new GearChoice(rule.itemId(), "§f" + name, "");
    }

    /** 装备的 category 前缀（{@code TOOL_PICKAXE → TOOL}）；认不出时给空串（落进「其他」组） */
    private static String categoryKeyOf(VanillaEnchantDatabase.GearCandidateRule rule) {
        String category = rule.category();
        int idx = category == null ? -1 : category.indexOf('_');
        return idx <= 0 ? "" : category.substring(0, idx);
    }

    /** 附魔等级罗马数字（1-5 → I-V，超出范围回退阿拉伯数字；旧 {@code roman:301-309}） */
    private static String roman(int level) {
        return switch (level) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            default -> String.valueOf(level);
        };
    }

    private static Screen currentScreen() {
        Minecraft client = Minecraft.getInstance();
        return client == null ? null : client.screen;
    }

    private static void openScreen(Screen screen) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || screen == null) return;
        client.setScreen(screen);
    }

    // ── 候选项 ──

    /** 装备候选：分组标题在构建时确定，图标懒构造（构建物品栈需要注册表已绑定） */
    private static final class GearChoice implements SelectorScreen.Entry {

        private final String key;
        private final String title;
        private String group;

        private GearChoice(String key, String title, String group) {
            this.key = key;
            this.title = title;
            this.group = group;
        }

        private GearChoice withGroup(String value) {
            this.group = value;
            return this;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public String title() {
            return title;
        }

        @Override
        public String group() {
            return group;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            Identifier id = Identifier.tryParse(key);
            if (id == null) return false;
            Item item = BuiltInRegistries.ITEM.getValue(id);
            if (item == null || item == Items.AIR) return false;
            return ItemIconCache.getInstance().draw(canvas, new ItemStack(item), x, y, size);
        }
    }

    /** 纯文字候选项：方案名没有对应物品，因此不画图标 */
    private record LabelEntry(String key, String title) implements SelectorScreen.Entry {

        @Override
        public String group() {
            return null;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            return false;
        }
    }
}
