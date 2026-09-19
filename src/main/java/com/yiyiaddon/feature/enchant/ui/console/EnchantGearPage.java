package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.config.EnchantSettings;
import com.yiyiaddon.feature.enchant.gear.AnvilStrategy;
import com.yiyiaddon.feature.enchant.gear.GearEnchantConfig;
import com.yiyiaddon.feature.enchant.gear.GearEnchantData;
import com.yiyiaddon.feature.enchant.gear.TargetProfile;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingText;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_CYCLE;

import java.util.Arrays;
import java.util.List;

/**
 * 自动附魔控制台「原版装备附魔」页（GEAR 模式专属）：合成策略 + 配置入口。
 *
 * <p><b>本页只放两行</b>（用户 2026-09-16：「我要的是点击选择原版附魔装备单独弹出一个窗口配置，
 * 我的旧项目就是这样的」）：{@code 合成策略}（3 档，旧 {@code AnvilPlanner.Strategy}）与
 * {@code 装备附魔配置}（旧 {@code GearEnchantSetting} 的设置行：图标 + 摘要 + 入口）。装备选择、极品方案、
 * 目标附魔整块搬进 {@link GearEnchantConfigScreen} 独立窗口 —— 旧项目 {@code GearEnchantScreen}
 * 本身就是独立窗口，本页与它同形态。</p>
 *
 * <p>{@code 装备附魔配置} 行首显示当前装备的物品图标（旧项目 {@code GearEnchantSetting.currentIconStack()}；
 * 用户 2026-09-16：「要显示装备的物品图标（例如铜镐的贴图）」），未选装备时不画也不占位。</p>
 *
 * <p>{@code 记录合成日志} 已按用户裁定随日志功能删除，本页不出现。</p>
 */
public final class EnchantGearPage {

    /** 点行按钮的固定入口文案（与项目其它「弹窗配置」入口一致） */
    private static final String CONFIG_LABEL = "配 置";
    /** 设置描述：旧 {@code GearEnchantSetting} 的 description（逐字） */
    private static final String CONFIG_DESCRIPTION = "配置原版装备的极品附魔目标";
    /** 摘要文字列宽 */
    private static final float SUMMARY_WIDTH = 220f;

    /** 合成策略三档（顺序即枚举序，文案逐字取自 {@code AnvilPlanner.Strategy}） */
    private static final List<String> STRATEGY_LABELS = Arrays.stream(AnvilStrategy.values())
        .map(AnvilStrategy::title)
        .toList();

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final EnchantSettings DEFAULTS = new EnchantSettings();

    private final EnchantConsoleScreen owner;
    private final EnchantModule module;
    /** 装备图标缓存：缓存键为装备 id（{@code null} = 未选择，同样缓存为空堆） */
    private String iconGearId;
    private ItemStack iconStack = ItemStack.EMPTY;

    public EnchantGearPage(EnchantConsoleScreen owner, EnchantModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        EnchantSettings settings = module.settings();

        // ── 合成策略（旧 :460-462）──；行尾可见提示见第 213 条（循环控件看不出能点）
        stack.add(new ConsoleRow(owner, () -> "合成策略",
            "铁砧装备+装备合并排序策略：简单=贡献优先、节能=低惩罚+低成本优先、快速=提升优先少步骤，用于对比经验消耗",
            COMMENT_CYCLE, List.of(new Ctl(new SettingCycle(STRATEGY_LABELS,
                () -> settings.anvilStrategy.ordinal(),
                index -> {
                    settings.anvilStrategy = AnvilStrategy.values()[index];
                    module.persistSettings();
                })),
                ConsoleWidgets.resetCtl(() -> {
                    settings.anvilStrategy = DEFAULTS.anvilStrategy;
                    module.persistSettings();
                    owner.reload();
                }, "合成策略"))));

        // ── 装备附魔配置（旧 GearEnchantSetting 的设置行：图标 + 摘要 + 入口）──
        // 图标由本行承担：旧项目该设置行的横向列表就是「主题图标 + 配置按钮 + 摘要 + ↻」，
        // 图标即当前装备（用户 2026-09-16：「要显示装备的物品图标（例如铜镐的贴图）」）
        // 行尾 ↺ 与配置窗口里「装备」行那枚同一语义：清空整份装备附魔配置（回到出厂空配置）
        stack.add(new ConsoleRow(owner, () -> "装备附魔配置", CONFIG_DESCRIPTION, null, List.of(
            new Ctl(new SettingText(this::summary, SUMMARY_WIDTH).alignLeft()),
            new Ctl(new Button(CONFIG_LABEL, this::openConfig)),
            ConsoleWidgets.resetCtl(() -> {
                settings.gearEnchantConfig.clear();
                module.persistSettings();
                owner.reload();
            }, "装备附魔配置")))
            .icon(this::gearIcon));
    }

    /**
     * 当前装备的物品图标（旧 {@code GearEnchantSetting.currentIconStack():212-225} 逐字搬运）。
     *
     * <p>未选择装备 / id 非法 / 注册表里没有该物品（自定义装备）→ 空堆，此时 {@code ConsoleRow}
     * 既不画图标、也不占横向空间。主菜单等「组件未绑定」环境下构建物品堆会 NPE，故原样保留旧项目的
     * {@code try/catch} 兜底。</p>
     *
     * <p>按装备 id 缓存一份：摘要是每帧取一次的（{@link SettingText} 的取数口径），图标同样每帧取，
     * 没必要逐帧重建物品堆；本页实例随控制台重建而重建，切装备后缓存自然作废。</p>
     */
    private ItemStack gearIcon() {
        String gearId = new GearEnchantConfig(module.settings().gearEnchantConfig).gearId();
        if (gearId != null && gearId.equals(iconGearId)) return iconStack;
        iconGearId = gearId;
        iconStack = resolveIcon(gearId);
        return iconStack;
    }

    /** 装备 id → 物品堆（查不到一律空堆，不画） */
    private static ItemStack resolveIcon(String gearId) {
        if (gearId == null || gearId.isEmpty()) return ItemStack.EMPTY;
        Identifier id = Identifier.tryParse(gearId);
        if (id == null) return ItemStack.EMPTY;
        Item item = BuiltInRegistries.ITEM.getValue(id);
        if (item == null || item == Items.AIR) return ItemStack.EMPTY;
        try {
            return item.getDefaultInstance();
        } catch (NullPointerException e) {
            return ItemStack.EMPTY;
        }
    }

    /** 打开独立配置窗口（装备选择 + 极品方案 + 目标附魔都在里面） */
    private void openConfig() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new GearEnchantConfigScreen(owner, module));
    }

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

    /** 当前方案：按配置里的方案 ID 在装备的方案表里查，查不到返回 {@code null} */
    private GearEnchantData.GearProfile currentProfile(GearEnchantData.GearDefinition gear) {
        String profileId = new GearEnchantConfig(module.settings().gearEnchantConfig).profileId();
        if (profileId == null) return null;
        for (GearEnchantData.GearProfile profile : gear.profiles) {
            if (profile.id.equals(profileId)) return profile;
        }
        return null;
    }
}
