package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.config.EnchantSettings;
import com.yiyiaddon.feature.enchant.gear.AnvilStrategy;
import com.yiyiaddon.feature.enchant.gear.GearCatalog;
import com.yiyiaddon.feature.enchant.gear.GearEnchantConfig;
import com.yiyiaddon.feature.enchant.gear.GearEnchantData;
import com.yiyiaddon.feature.enchant.gear.TargetProfile;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.feature.enchant.vanilla.VanillaEnchantDatabase;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingText;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自动附魔控制台「原版装备附魔」页（GEAR 模式专属）：合成策略 + 装备附魔配置。
 *
 * <p>本页只装旧 {@code sgGear} 分组的可见项：{@code 合成策略}（3 档）与
 * {@code 装备附魔配置}（旧 {@code GearEnchantSetting}）。{@code 记录合成日志} 已按用户裁定随日志
 * 功能删除，本页不出现。</p>
 *
 * <p><b>文案逐字沿旧</b>：设置名 / 描述 / 取值域取 {@link EnchantSettings} 的字段注释；
 * 装备配置的摘要 {@code §a<装备> §8▸ §b<方案> §8▸ §e<N> 项目标}、未选占位 {@code 未选择装备} /
 * {@code 未选择方案}、无方案提示 {@code §7该装备 §c暂无极品方案§7，仅作为官方装备数据收录。}
 * 与附魔行的 {@code §c已排除} / {@code §a启用} / {@code §8核心} 全部取自旧
 * {@code GearEnchantSetting.summaryText} 与 {@code GearEnchantScreen:100-236}。</p>
 *
 * <p><b>交互按本项目控制台（旧壳不作一比一复刻）</b>：旧界面用「大类 → 品质 → 类型」三级下拉定位
 * 装备，控制台改为一个可搜索的单选选择器（窗口标题沿用旧窗口名 {@code 原版装备附魔配置}，
 * 候选项按大类分组，只有大类中文名沿用旧 {@code GearCatalog} 的文案）；方案用一个同形态的选择器
 * （多方案才给入口，单方案直接显示方案名，与旧 {@code :170-180} 一致）；每个目标附魔一行，
 * 排除 / 启用按钮与等级加减沿用旧控件语义，等级上限取注册表真实最大等级。</p>
 */
public final class EnchantGearPage {

    /** 点行文字（本项目选择器行的固定入口文案） */
    private static final String SELECT_LABEL = "点击选择";
    /** 设置描述：旧 {@code GearEnchantSetting} 的 description（逐字） */
    private static final String CONFIG_DESCRIPTION = "配置原版装备的极品附魔目标";
    /** 摘要文字列宽 */
    private static final float SUMMARY_WIDTH = 220f;
    /** 注册表不可用（未进世界）时的等级上限兜底；真实上限由 {@code GearEnchantData.maxLevelOf} 给出 */
    private static final int FALLBACK_MAX_LEVEL = 10;
    /**
     * 空状态占位行的键前缀。
     *
     * <p>旧项目 {@code GearEnchantScreen:118/:137} 在某个类别没有装备时显示 {@code §c该类别暂无装备}；
     * 本项目选择器里用不可选的占位行承载同一句话（点它不写配置），文案逐字。</p>
     */
    private static final String EMPTY_ENTRY_PREFIX = "__empty__:";

    /** 合成策略三档（顺序即枚举序，文案逐字取自 {@code AnvilPlanner.Strategy}） */
    private static final List<String> STRATEGY_LABELS = Arrays.stream(AnvilStrategy.values())
        .map(AnvilStrategy::title)
        .toList();

    private final EnchantConsoleScreen owner;
    private final EnchantModule module;

    public EnchantGearPage(EnchantConsoleScreen owner, EnchantModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        EnchantSettings settings = module.settings();
        GearEnchantConfig config = new GearEnchantConfig(settings.gearEnchantConfig);

        // ── 合成策略（旧 :460-462）──
        stack.add(new ConsoleRow(owner, () -> "合成策略",
            "铁砧装备+装备合并排序策略：简单=贡献优先、节能=低惩罚+低成本优先、快速=提升优先少步骤，用于对比经验消耗",
            null, List.of(new Ctl(new SettingCycle(STRATEGY_LABELS,
                () -> settings.anvilStrategy.ordinal(),
                index -> {
                    settings.anvilStrategy = AnvilStrategy.values()[index];
                    module.persistSettings();
                })))));

        // ── 装备附魔配置（旧 GearEnchantSetting 的设置行：入口 + 摘要 + ↻）──
        stack.add(new ConsoleRow(owner, () -> "装备附魔配置", CONFIG_DESCRIPTION, null, List.of(
            new Ctl(new Button(SELECT_LABEL, this::openGearSelector)),
            new Ctl(new SettingText(this::summary, SUMMARY_WIDTH).alignLeft()),
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, this::resetConfig)))));

        // 旧 GearEnchantScreen:152-155：三级定位不到装备时提示 `§c未找到对应装备`。
        // 本项目改造为一个选择器后，等价的「定位不到」只可能是静态库为空（资源未加载 / 注册表未就绪）
        if (VanillaEnchantDatabase.get().gears().isEmpty()) {
            stack.add(new Note(owner, "§c未找到对应装备"));
            return;
        }

        String gearId = config.gearId();
        if (gearId == null) return;

        // 装备不在极品方案数据里 / 该装备没有方案：都按旧界面同一句提示（旧 :157-165：
        // data.gear(rule.itemId()) 为 null 或 profiles 为空都落到这一句）
        GearEnchantData.GearDefinition gear = GearEnchantData.get().gear(gearId);
        if (gear == null || gear.profiles.isEmpty()) {
            stack.add(new Note(owner, "§7该装备 §c暂无极品方案§7，仅作为官方装备数据收录。"));
            return;
        }

        // ── 极品方案（旧 :168-180：多方案才是下拉，单方案给标签）──
        List<Ctl> profileControls = new ArrayList<>();
        if (gear.profiles.size() > 1) {
            profileControls.add(new Ctl(new Button(SELECT_LABEL, () -> openProfileSelector(gear))));
        }
        profileControls.add(new Ctl(new SettingText(() -> {
            GearEnchantData.GearProfile current = currentProfile(gear);
            return "§b" + (current == null ? gear.profiles.get(0).name : current.name);
        }, SUMMARY_WIDTH).alignLeft()));
        stack.add(new ConsoleRow(owner, () -> "极品方案", null, null, profileControls));

        // ── 目标附魔逐条（旧 :187-236）：名称 + 排除 / 核心 + 等级加减 ──
        GearEnchantData.GearProfile profile = currentProfile(gear);
        if (profile == null) return;

        FoldSection section = new FoldSection("§7§l目标附魔 §8(点标题可收起)",
            "gear:" + gear.id, owner.collapsedSections());
        for (GearEnchantData.TargetDefinition target : profile.targets) {
            section.content().add(targetRow(config, target));
        }
        stack.add(section);
    }

    // ── 行构件 ──

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
                owner.reload();
            })));
        } else {
            controls.add(new Ctl(new SettingText(() -> "§8核心", 40f)));
        }
        controls.add(new Ctl(levelBox(config, target)));

        return new ConsoleRow(owner,
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

    // ── 摘要与选择器 ──

    /**
     * 设置行摘要（旧 {@code GearEnchantSetting.summaryText():227-236} 逐字）：
     * {@code §a<装备> §8▸ §b<方案> §8▸ §e<N> 项目标}；未选装备 {@code 未选择装备}，
     * 装备在但方案认不出 {@code 未选择方案}。
     */
    private String summary() {
        GearEnchantConfig config = new GearEnchantConfig(module.settings().gearEnchantConfig);
        String gearId = config.gearId();
        GearEnchantData.GearDefinition gear = GearEnchantData.get().gear(gearId == null ? "" : gearId);
        if (gear == null) return "未选择装备";
        GearEnchantData.GearProfile profile = currentProfile(gear);
        String profileText = profile == null ? "未选择方案" : profile.name;
        TargetProfile current = config.currentProfile();
        int active = current == null ? 0 : current.activeTargets().size();
        return "§a" + gear.name + " §8▸ §b" + profileText + " §8▸ §e" + active + " 项目标";
    }

    /** 装备选择器：候选 = 官方装备全集（{@link VanillaEnchantDatabase}，75 件），按大类分组，点行即选中 */
    private void openGearSelector() {
        List<VanillaEnchantDatabase.GearCandidateRule> gears = VanillaEnchantDatabase.get().gears();
        List<SelectorScreen.Entry> entries = new ArrayList<>();
        for (VanillaEnchantDatabase.GearCandidateRule rule : gears) {
            entries.add(new GearEntry(rule.itemId(), rule.name(), categoryGroup(rule.category())));
        }
        // 旧 :118 / :137：该类别没有装备时显示 `§c该类别暂无装备`（占位行不可选）
        for (GearCatalog.Category category : GearCatalog.categories()) {
            String group = "§a§l▌ " + category.title();
            boolean any = false;
            for (VanillaEnchantDatabase.GearCandidateRule rule : gears) {
                if (group.equals(categoryGroup(rule.category()))) {
                    any = true;
                    break;
                }
            }
            if (!any) entries.add(new GearEntry(
                EMPTY_ENTRY_PREFIX + category.key(), "§c该类别暂无装备", group));
        }
        if (entries.isEmpty()) return;
        SelectorScreen screen = SelectorScreen.pick("原版装备附魔配置", currentScreen(), entries, itemId -> {
            // 占位行只是提示，点它不写配置
            if (itemId == null || itemId.startsWith(EMPTY_ENTRY_PREFIX)) return;
            new GearEnchantConfig(module.settings().gearEnchantConfig).applyGear(itemId);
            module.persistSettings();
            owner.reload();
        });
        openScreen(screen);
    }

    /** 极品方案选择器：候选 = 当前装备的方案表，点行即选中并重置为目标默认等级 */
    private void openProfileSelector(GearEnchantData.GearDefinition gear) {
        List<SelectorScreen.Entry> entries = new ArrayList<>();
        for (GearEnchantData.GearProfile profile : gear.profiles) {
            entries.add(new ProfileEntry(profile.id, profile.name));
        }
        SelectorScreen screen = SelectorScreen.pick("极品方案", currentScreen(), entries, profileId -> {
            new GearEnchantConfig(module.settings().gearEnchantConfig).applyProfile(profileId);
            module.persistSettings();
            owner.reload();
        });
        openScreen(screen);
    }

    /** ↻：清空装备附魔配置（旧设置行的重置按钮语义：回到默认空列表） */
    private void resetConfig() {
        module.settings().gearEnchantConfig.clear();
        module.persistSettings();
        owner.reload();
    }

    /** 大类分组标题：{@code §a§l▌ 工具 / 武器 / 护甲}（大类中文名取 {@link GearCatalog#categories()}，未知不分组） */
    private static String categoryGroup(String category) {
        if (category == null || !category.contains("_")) return null;
        String key = category.substring(0, category.indexOf('_'));
        for (GearCatalog.Category candidate : GearCatalog.categories()) {
            if (candidate.key().equals(key)) return "§a§l▌ " + candidate.title();
        }
        return null;
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

    /** 装备候选（键 = 装备 ID，标题 = 官方装备名，不画图标） */
    private record GearEntry(String key, String title, String group) implements SelectorScreen.Entry {

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            return false;
        }
    }

    /** 方案候选（键 = 方案 ID，标题 = 方案名） */
    private record ProfileEntry(String key, String title) implements SelectorScreen.Entry {

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
