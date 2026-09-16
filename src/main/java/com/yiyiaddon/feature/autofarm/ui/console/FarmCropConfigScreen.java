package com.yiyiaddon.feature.autofarm.ui.console;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.config.AutoFarmSettings;
import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 逐作物独立配置窗口（D3 拍板：15 个动态项进独立窗口、按作物分组折叠）。
 *
 * <p><b>行文案逐字</b>（旧 {@code AutoFarmMatrix :181-208} 的生成式）：
 * {@code <作物名>-卸货数量}（范围 1~36，默认 8）与 {@code <作物名>-补货种子数量}
 * （范围 1~10，默认 3，仅需补种的作物有）；描述原文照搬，双物品作物的卸货描述里
 * {@code §d多作物箱§r} / {@code §6单作物箱} 颜色码原样。</p>
 *
 * <p><b>分组头带图标</b>（D9）：每组组头行挂主产物物品贴图；折叠状态记在会话级集合
 * （第 196 条：重建不重置）。改动即写盘（第 173 条）。tooltip 走项目全局
 * {@link TooltipLayer}（{@code PanelScreen} 每帧自动绘制，窗口零自绘）。</p>
 */
public final class FarmCropConfigScreen extends PanelScreen implements ConsoleHost {

    private final AutoFarmConsoleScreen host;
    private final AutoFarmModule module;

    /** 已收起的作物分组键（会话级；默认全部展开，玩家进来就要看到数量） */
    private final Set<String> collapsedSections = new HashSet<>();

    /** @param host 打开本窗口的控制台（关窗回它，并触发其 reload 同步摘要） */
    public FarmCropConfigScreen(AutoFarmConsoleScreen host, AutoFarmModule module) {
        super("逐作物独立配置", host);
        this.host = host;
        this.module = module;
        rebuild();
    }

    @Override
    protected void init() {
        super.init();
        if (minecraft != null) minecraft.execute(this::rebuild);
    }

    @Override
    public void removed() {
        super.removed();
        // 关窗回控制台：主页面摘要随关窗刷新（第 201 条同款联动）
        if (minecraft != null && minecraft.screen == host) minecraft.execute(host::reload);
    }

    /** 登记本帧要显示的 tooltip：走项目全局 TooltipLayer，窗口不自绘（实现 {@link ConsoleHost}） */
    @Override
    public void tip(String text, float x, float y) {
        TooltipLayer.show(text, x, y);
    }

    /** 已收起的作物分组键集合（{@code FoldSection} 折叠记忆，重建不丢） */
    public Set<String> collapsedSections() {
        return collapsedSections;
    }

    /** 重排整窗内容：每种启用的非杂物作物一个折叠分组（仙人掌花走全局「杂物卸货」阈值） */
    private void rebuild() {
        var content = content();
        content.clear();

        boolean any = false;
        for (CropProfile profile : module.getEnabledCrops()) {
            if (profile.junk()) continue;
            any = true;
            content.add(cropSection(profile));
        }
        if (!any) {
            content.add(new com.yiyiaddon.ui.console.ConsoleWidgets.Note(this,
                "  §8未启用任何作物：先到「设置 → 作物选择」里勾选"));
        }
    }

    /** 单个作物的折叠分组：组头行 = 图标 + 作物名，行 = 卸货数量（+ 补货种子数量） */
    private FoldSection cropSection(CropProfile profile) {
        AutoFarmSettings settings = module.settings();
        FoldSection section = new FoldSection(
            "§f" + profile.displayName(), "crop:" + profile.name(), collapsedSections());
        section.content().add(headerRow(profile));

        boolean dual = profile.needsReplant() && profile.plantItem() != profile.harvestItem();
        // 卸货数量：双物品作物描述含「多作物箱/种子补货箱」配色（旧 :185-187 逐字）
        String unloadDesc = dual
            ? "该作物成熟掉落物超过此组数才卸入 §d多作物箱§r（种子的盈余仍卸入种子补货箱）"
            : "该作物产物超过此组数才卸入 §6单作物箱";
        section.content().add(numberRow(profile.displayName() + "-卸货数量", unloadDesc,
            1, 36, () -> (double) settings.unloadGroups(profile),
            value -> settings.perCropUnload.put(profile.name(), value.intValue())));

        // 补货种子数量仅对需要补种的作物有意义，柱状物/果实不显示（旧 :199-208）
        if (profile.needsReplant()) {
            section.content().add(numberRow(profile.displayName() + "-补货种子数量",
                "该作物种植材料低于多少组时自动去作物箱补货，卸货时始终保留这批材料",
                1, 10, () -> (double) settings.restockGroups(profile),
                value -> settings.perCropRestock.put(profile.name(), value.intValue())));
        }
        return section;
    }

    /**
     * 组头图标行（D9）：{@code FoldSection} 的标题只收文字，图标挂在组头下一行的行首
     * （图标 = 主产物物品贴图，ItemIconCache 全屏统一链路）。
     */
    private ConsoleRow headerRow(CropProfile profile) {
        ConsoleRow row = new ConsoleRow(this, () -> profile.displayName(), null, null, List.of());
        row.icon(() -> new ItemStack(profile.harvestItem()));
        return row;
    }

    /** 数字行（旧滑条 → 数字框，D-13-01）：步进 1、整数值，改动即写盘 */
    private ConsoleRow numberRow(String label, String hint, double min, double max,
                                 java.util.function.Supplier<Double> getter,
                                 java.util.function.Consumer<Double> setter) {
        return new ConsoleRow(this, () -> label, hint, null,
            List.of(new Ctl(new SettingNumberBox(min, max, 1, "%.0f",
                getter, value -> {
                setter.accept(value);
                module.persistSettings();
            }))));
    }
}
