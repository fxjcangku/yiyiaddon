package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.gear.GearCatalog;
import com.yiyiaddon.feature.enchant.gear.GearEnchantConfig;
import com.yiyiaddon.feature.enchant.gear.GearEnchantData;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.feature.enchant.vanilla.VanillaEnchantDatabase;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleStateColumn;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.ItemIconCache;
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
import java.util.function.Supplier;

/**
 * 「原版装备附魔配置」独立窗口。
 *
 * <p><b>形态按用户口径</b>（2026-09-16：「我要的是点击选择原版附魔装备单独弹出一个窗口配置，
 * 我的旧项目就是这样的」）：控制台「原版装备附魔」页只留一行摘要 + 入口，装备选择、极品方案、
 * 目标附魔全部在本窗口内完成。旧项目 {@code GearEnchantScreen} 就是独立窗口，本类与它对齐。</p>
 *
 * <p><b>装备选择</b>：不再用「点一下跳下一档」的三级切换（用户同日：「为什么点击就直接帮我选择了？
 * 我都没看见列表」），改成打开候选列表，点行即选中 —— 列表按<b>大类 → 品质</b>两级折叠分组
 * （用户同日：「选择都是没按品质来的 哎 能不能弄好分类啊」，见 {@link #openGearPicker()}），
 * 默认全部收起，展开哪一档才铺出那一档的工具 / 武器 / 护甲。</p>
 *
 * <p><b>重建</b>：本窗口自己管理 {@code content()}，任何写配置的动作后调 {@link #rebuild()}；
 * 同时通知控制台 {@link EnchantConsoleScreen#reload()}，保证关窗后主页面的状态条与摘要同步。</p>
 */
final class GearEnchantConfigScreen extends PanelScreen implements ConsoleHost {

    /** 设置描述：旧 {@code GearEnchantSetting} 的 description（逐字） */
    private static final String GEAR_DESCRIPTION = "按大类分组的装备列表，点行即选中";
    /** 注册表不可用（未进世界）时的等级上限兜底；真实上限由 {@code GearEnchantData.maxLevelOf} 给出 */
    private static final int FALLBACK_MAX_LEVEL = 10;
    /** 大类标题前缀（与项目其它选择器一致的分组标题写法） */
    private static final String GROUP_PREFIX = "§b§l▌ ";
    /** 二级标题（品质 / 材质）前缀：比大类弱一档，选择器还会再右缩进并小一号 */
    private static final String GROUP_SUB_PREFIX = "§7";
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
        // 空态禁用：判据来自 gearEnchantConfig（与 resetConfig() 的清空读的是同一份数据）
        stack.add(new ConsoleRow(this, () -> "装备", GEAR_DESCRIPTION, null, List.of(
            new Ctl(new Button(gear == null ? "点击选择" : gear.name, this::openGearPicker)),
            // 空态禁用：判据来自 gearEnchantConfig（与 resetConfig 读同一份），逐帧求值见
            // IconButton#disabledWhen(Supplier)
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, this::resetConfig)
                .disabledWhen(() -> module.settings().gearEnchantConfig.isEmpty()),
                "清空装备附魔配置"))));

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
        //
        // 列宽不再随「当前方案名」逐帧变（那会让「点击选择」在切换方案时左右移动）：下界取本装备
        // 全部方案名里最宽的那个（见 ConsoleStateColumn），选哪套方案列宽都一样。
        ConsoleStateColumn profileColumn = new ConsoleStateColumn("");
        for (GearEnchantData.GearProfile profile : gear.profiles) {
            profileColumn.widthOf(() -> "§b" + profile.name);
        }
        List<Ctl> profileControls = new ArrayList<>();
        if (gear.profiles.size() > 1) {
            profileControls.add(new Ctl(new Button("点击选择", () -> openProfilePicker(gear))));
        }
        profileControls.add(new Ctl(new SettingText(() -> profileText(gear),
            () -> profileColumn.widthOf(() -> profileText(gear))).alignLeft()));
        // 行内 ↺：回到该装备的默认方案（与「换装备」同一入口，目标附魔随方案一起回默认值）
        profileControls.add(ConsoleWidgets.resetCtl(() -> {
            new GearEnchantConfig(module.settings().gearEnchantConfig).applyGear(gear.id);
            module.persistSettings();
            console.reload();
            rebuild();
            SelectionReceipt.reset();
        }, "极品方案"));
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
                // 回执说的是「点完之后」的状态：本行原本被排除时，点下去就是重新启用（反之亦然），
                // 因此取切换后的排除标志再取反交给回执，名称用本行原文（剥色由回执负责）
                SelectionReceipt.toggled(!config.isExcluded(target.id), target.name);
                // 名称配色随排除状态变化，整页重建后生效
                rebuild();
            })));
        } else {
            controls.add(new Ctl(new SettingText(() -> "§8核心", 40f)));
        }
        controls.add(new Ctl(levelBox(config, target)));
        // 行内 ↺：等级回到方案默认值、同时解除排除（两项都是这一行承载的设置）
        controls.add(ConsoleWidgets.resetCtl(() -> {
            config.setLevel(target.id, target.level);
            config.setExcluded(target.id, false);
            module.persistSettings();
            rebuild();
            SelectionReceipt.reset();
        }, target.name));

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
     * 装备候选：静态库里的全部装备，按「大类 → 品质（材质）」两级分组，带物品图标。
     *
     * <p><b>为什么要两级</b>（用户 2026-09-16：「选择都是没按品质来的 哎 能不能弄好分类啊 按品质 工具 装备」）：
     * 上一版只按大类分组，工具那一组把 7 种材质的镐斧锹锄铺成 28 行，木镐与下界合金镐之间没有任何分界，
     * 找「钻石镐」只能从头往下翻。装备库本身就是「大类 → 品质 → 类型」三级结构
     * （{@link GearCatalog}），这里把前两级交给选择器折叠分组、第三级就是组内的行
     * （同一品质下的镐 / 斧 / 锹 / 锄），因此展开「工具 → 钻石」时看到的正是钻石那一档的全部工具。</p>
     *
     * <p>顺序全部取自 {@link GearCatalog}（大类固定顺序、品质低→高、类型取静态库顺序），
     * 因此同一件装备在列表里的位置稳定，不会因过滤词变化而跳位。</p>
     */
    private void openGearPicker() {
        List<SelectorScreen.Entry> entries = new ArrayList<>();
        Set<String> added = new HashSet<>();

        for (GearCatalog.Category category : GearCatalog.categories()) {
            String parent = GROUP_PREFIX + category.title();
            for (GearCatalog.Material material : GearCatalog.materials(category.key())) {
                String group = GROUP_SUB_PREFIX + material.title();
                for (GearCatalog.Type type : GearCatalog.types(category.key(), material.key())) {
                    VanillaEnchantDatabase.GearCandidateRule rule =
                            GearCatalog.gear(category.key(), material.key(), type.key());
                    if (rule == null || !added.add(rule.itemId())) continue;
                    entries.add(gearChoice(rule).withGroup(parent, group));
                }
            }
        }

        // 兜底：静态库里出现了 GearCatalog 认不出的条目（没有 category 前缀，或前缀对不上任何大类），
        // 也要能选到——否则用户会「找不到某件装备」，而这类漏项从界面上完全看不出来。
        for (VanillaEnchantDatabase.GearCandidateRule rule : VanillaEnchantDatabase.get().gears()) {
            if (added.contains(rule.itemId())) continue;
            entries.add(gearChoice(rule).withGroup("", GROUP_OTHER));
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
            // 回执取「刚写进配置的那一个方案」（currentProfile 读的就是配置里的方案 ID），
            // 用方案名而不是回调参数里的 ID：玩家看得懂的是名字
            GearEnchantData.GearProfile picked = currentProfile(gear);
            SelectionReceipt.selected(picked == null ? profileId : picked.name);
            console.reload();
            rebuild();
        }));
    }

    /** 写入装备并重建（旧 :110-160 每次切换都 {@code rebuild()}） */
    private void applyGear(String gearId) {
        new GearEnchantConfig(module.settings().gearEnchantConfig).applyGear(gearId);
        module.persistSettings();
        // 回执用装备名（与列表行标题同一口径：静态库里查不到名字才回退 ID）
        GearEnchantData.GearDefinition picked = GearEnchantData.get().gear(gearId == null ? "" : gearId);
        SelectionReceipt.selected(picked == null || picked.name == null || picked.name.isBlank()
            ? gearId : picked.name);
        console.reload();
        rebuild();
    }

    /** ↻：清空装备附魔配置（旧设置行的重置按钮语义：回到默认空列表） */
    private void resetConfig() {
        // 回执的条数必须是清空前的真实值（清空后再读只会是 0），因此先读再清；
        // 传的是配置列表长度（含装备 ID / 方案 ID 两行头），与状态判断读的是同一份数据
        int count = module.settings().gearEnchantConfig.size();
        module.settings().gearEnchantConfig.clear();
        module.persistSettings();
        SelectionReceipt.cleared(count);
        console.reload();
        rebuild();
    }

    private static GearChoice gearChoice(VanillaEnchantDatabase.GearCandidateRule rule) {
        GearEnchantData.GearDefinition gear = GearEnchantData.get().gear(rule.itemId());
        String name = gear == null || gear.name == null || gear.name.isBlank()
            ? rule.itemId() : gear.name;
        return new GearChoice(rule.itemId(), "§f" + name);
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
        return client == null ? null : client.gui.screen();
    }

    private static void openScreen(Screen screen) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || screen == null) return;
        client.gui.setScreen(screen);
    }

    // ── 候选项 ──

    /** 装备候选：两级分组标题在构建时确定，图标懒构造（构建物品栈需要注册表已绑定） */
    private static final class GearChoice implements SelectorScreen.Entry {

        private final String key;
        private final String title;
        /** 二级标题（品质）；空串 = 只有一级 */
        private String group = "";
        /** 一级标题（大类）；空串 = 没有父级（兜底的「其他」组） */
        private String parentGroup = "";

        private GearChoice(String key, String title) {
            this.key = key;
            this.title = title;
        }

        private GearChoice withGroup(String parentGroup, String group) {
            this.parentGroup = parentGroup == null ? "" : parentGroup;
            this.group = group == null ? "" : group;
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
        public String parentGroup() {
            return parentGroup;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            Identifier id = Identifier.tryParse(key);
            if (id == null) return false;
            Item item = BuiltInRegistries.ITEM.getValue(id);
            if (item == null || item == Items.AIR) return false;
            return ItemIconCache.getInstance().draw(canvas, new ItemStack(item), x, y, size);
        }

        /** 与 drawIcon 同一份图标：按登记 ID 取到的物品（取不到返回 null）。 */
        @Override
        public ItemStack iconStack() {
            Identifier id = Identifier.tryParse(key);
            if (id == null) return null;
            Item item = BuiltInRegistries.ITEM.getValue(id);
            return item == null || item == Items.AIR ? null : item.getDefaultInstance();
        }
    }

    /**
     * 极品方案候选项：键取方案 ID、标题取方案名。
     *
     * <p><b>行图标</b>：用户 2026-09-16 原话「然后极品页面没有附魔书图标」——方案名与词条是同一类条目
     * （附魔方案本身不是物品），因此照抄 {@code EnchantSelectPage.EnchantEntry} 那一处的做法：
     * 统一用 {@link Items#ENCHANTED_BOOK} 的 {@link ItemStack} 做图标，惰性构造（构建物品栈需要
     * 注册表已绑定，不能在设置载入期做）。不按方案区分图标，是因为原版没有任何「方案名 → 物品」的映射，
     * 硬编一张表只会出现与真实物品不符的假图。</p>
     *
     * <p><b>不补图标的同类文本</b>：本文件里只剩分组标题（{@code §b§l▌ 工具} 这类装备大类原文）没有图标，
     * 那是纯分类名、不是可点选的记录；行图标只属于「一条可点选的记录」，且分组标题由选择器宿主
     * ({@link SelectorScreen}) 统一绘制，候选项不参与。</p>
     */
    private static final class LabelEntry implements SelectorScreen.Entry {

        private final String key;
        private final String title;
        /** 惰性构造：构建物品栈需要注册表已绑定，不能在设置载入期做 */
        private ItemStack iconStack;

        private LabelEntry(String key, String title) {
            this.key = key;
            this.title = title;
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
