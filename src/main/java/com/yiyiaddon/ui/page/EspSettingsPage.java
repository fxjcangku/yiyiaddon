package com.yiyiaddon.ui.page;

import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.component.ModuleRow;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspGlobalSettings.ModeOverride;
import com.yiyiaddon.ui.render.world.EspGlobalSettings.OcclusionOverride;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingModule;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * ESP 全局设置：一套作用于<b>所有</b>世界 ESP 的公共选项。
 *
 * <p><b>与模块自己那套设置的分工：</b>模块自己管「画什么、什么颜色、什么模式」（星露谷点位渲染、
 * 自动箱子、ESP 测试项各自的页面）；这里管全局排版与开销——线宽、整体不透明度、显示距离、
 * 字号、单帧图元上限、以及「自己的 ESP 要不要画」。<b>本页不改任何模块的颜色设置</b>，
 * 所有倍率默认 1.0、覆盖项默认跟随各模块，因此不动它就等于没有这一层。</p>
 *
 * <p>改动即时写回 {@code config/yiyiaddon/esp-global.json}，渲染线程下一帧生效。</p>
 *
 * <p>首组「使用说明」默认展开，按钮打开 {@link HelpPanelScreen}：八章节逐项讲清每个设置的作用、
 * 取值域与默认值，并说明「颜色与画什么仍归各模块控制台」这条分工（{@link #HELP_SECTIONS}）。</p>
 */
public final class EspSettingsPage extends BasePage {

    /*
     * 行首图标。码点均已用脚本解析 MaterialSymbolsRounded.ttf 的 cmap 验真存在
     * （开发习惯第 140 条：字形直接进字体绘制，字体里没有就是一块豆腐）。
     */
    /** 问号（help）：使用说明入口。 */
    private static final String ICON_HELP = "\uE887";
    /** 电源（power_settings_new）：总开关。 */
    private static final String ICON_MASTER = "\uE8AC";
    /** 列表：各模块 ESP 逐项列示（码点取自 {@code InterfacePage.ICON_SCROLL}，同字体已验证存在）。 */
    private static final String ICON_LAYERS = "\uE429";
    /** 调色板（palette）：外观＝怎么画。 */
    private static final String ICON_LOOK = "\uE40A";
    /** 眼睛（visibility）：可见度＝不透明度与距离；与 ESP 分类图标同字形。 */
    private static final String ICON_VISIBILITY = "\uE8F4";
    /** 字形框（text_fields）：文字＝字牌字号与底板。 */
    private static final String ICON_TEXT = "\uE262";
    /** 速度表（speed）：性能＝成片 ESP 的保底。 */
    private static final String ICON_PERFORMANCE = "\uE9E4";
    /** 人像（person）：自己＝本机玩家的 ESP。 */
    private static final String ICON_SELF = "\uE7FD";

    /**
     * 使用说明八章节（用户 2026-09-18：「我没看懂 esp设置怎么弄 帮我弄个使用说明按钮详细的」）。
     *
     * <p>逐项对应当前实现：取值域、默认值、抑制范围全部取自本页 {@code addSub} 的实参与其底层
     * {@link EspGlobalSettings}，不写「大概 / 支持」这类没信息量的说法（开发习惯第 212 条：
     * 说明必须与实现保持最新，改设置项就要回来改这里）。</p>
     */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("这一页是干什么的",
            "  §a▸ §f这里是「全局层」，管所有模块共用的排版与开销",
            "  §a▸ §f「画什么 / 什么颜色 / 什么模式」仍归各模块自己的控制台",
            "  §a▸ §f默认值全部等于「什么都不改」，不动它就跟没有这一层一样",
            "  §a▸ §f改动即时生效，存到 §econfig/yiyiaddon/esp-global.json"
        ),
        new HelpPanelScreen.HelpSection("三步上手",
            "  §a[1] §f想一下子全关掉 §8→ §7关掉「ESP 总开关」",
            "  §a[2] §f只想关某一个模块 §8→ §7在「各模块 ESP」里关它那一项",
            "  §a[3] §f嫌线细 / 太亮 / 太远 §8→ §7调「外观」与「可见度」里的倍率",
            "  §7其余都是可选的微调，看不懂就先别动"
        ),
        new HelpPanelScreen.HelpSection("总开关与各模块 ESP",
            "  §a▸ §fESP 总开关 §8- §7关掉后所有 ESP 整帧不画，九个模块层一起停",
            "  §a▸ §f挖矿 §8- §7矿点的框、矿物箱 / 食物箱 / 挂机修复点、岩浆框、挖掘进度框",
            "  §a▸ §f星露谷 §8- §7农田框、错位格、洒水器范围预览与星露谷字牌",
            "  §a▸ §f自动箱子 §8- §7扫描到的箱子框与字牌",
            "  §a▸ §f村民容器 §8- §7绿宝石箱 / 成品交易箱的框与字牌",
            "  §a▸ §f管理员检测 §8- §7威胁框与追踪线",
            "  §a▸ §f透视 §8- §7方块与实体透视的框、射线",
            "  §a▸ §f传送 §8- §7调试用的目标点 / 穿墙射线 / 回弹点 / 候选格与移动对象框",
            "  §a▸ §f发包秒破 §8- §7破坏进度框、收缩框与百分比标签（含排队候选方块）",
            "  §a▸ §f自动骨粉 §8- §7候选目标框",
            "  §7关掉某一层只是不画，模块自己的设置与运行状态一概不动"
        ),
        new HelpPanelScreen.HelpSection("外观：怎么画",
            "  §a▸ §f线宽倍率 §8- §7全部线框的粗细统一乘它（0.5~3.0，默认 1.0）",
            "  §a▸ §f渲染模式覆盖 §8- §7默认跟随各模块；覆盖后统一成线框 / 面 / 两者",
            "  §a▸ §f透视覆盖 §8- §7默认跟随各模块；全部透视＝穿墙也画，全部遮挡＝被挡住不画",
            "  §a▸ §f瞄准方块高亮 §8- §7准星指的方块画一圈整格描边，只描边不填充",
            "  §d▸ §f自动挖矿运行期间这个白框自动不画 §7（准星乱扫会一直闪），关掉模块即恢复"
        ),
        new HelpPanelScreen.HelpSection("可见度：画多少",
            "  §a▸ §f不透明度倍率 §8- §7全部 ESP 颜色统一变淡（10%~100%，默认 100%）",
            "  §a▸ §f最远显示距离 §8- §7超过这个格数一律不画（0 = 不限，最大 256）",
            "  §a▸ §f距离淡出 §8- §7从「淡出起点」开始随距离变淡，到最远距离刚好看不见",
            "  §a▸ §f淡出起点 §8- §7这个距离以内完全不透明；没设最远距离时按 64 格为终点"
        ),
        new HelpPanelScreen.HelpSection("文字与性能",
            "  §a▸ §f文字大小倍率 §8- §7全部 ESP 字牌的字号统一乘它（0.5~2.5，默认 1.0）",
            "  §a▸ §f文字底板 §8- §7字牌下压一块深色底板；关掉只剩纯色字，亮背景下更难读",
            "  §a▸ §f每帧最多画多少个图形 §8- §70 = 不限；每个框 / 线 / 面 / 一段字各算一个",
            "  §7只有成片 ESP 拖慢帧率时才调它，超出后本帧不再画新的图形"
        ),
        new HelpPanelScreen.HelpSection("自己",
            "  §a▸ §f隐藏自己（第一人称）§8- §7默认开，免得自己的框糊在准星上挡视野",
            "  §a▸ §f隐藏自己（第三人称）§8- §7F5 视角下也不画自己，默认关"
        ),
        new HelpPanelScreen.HelpSection("常见问题",
            "  §c▸ §f颜色改不了？§7颜色归各模块控制台，本页只管粗细 / 透明 / 距离 / 字号",
            "  §c▸ §f改了没反应？§7先看那一项是不是默认值，再看「各模块 ESP」有没有关掉",
            "  §c▸ §f关掉总开关会丢设置吗？§7不会，模块自己的设置原样保留，开回来即恢复"
        )
    };

    private final EspGlobalSettings settings = EspGlobalSettings.get();

    public EspSettingsPage() {
        // ── 使用说明（用户 2026-09-18：全套设置看不懂，加一个按钮打开逐项说明）──
        // 默认展开：这是全页唯一的「从哪下手」入口，折叠起来等于没有
        SettingModule help = group("使用说明", "看不懂每一项是干什么的点右边按钮，逐项讲清作用、取值与默认值", ICON_HELP);
        help.addSub("打开逐项说明", "覆盖总开关 / 各模块 ESP / 外观 / 可见度 / 文字 / 性能 / 自己，以及常见问题",
            new Button("§e查看使用说明", this::openHelp));
        help.setExpanded(true);

        // ── 总开关 ──
        SettingModule master = group("总开关", "一处关掉全部世界 ESP", ICON_MASTER);
        master.addSub("ESP 总开关", "关掉后所有 ESP 绘制层整帧跳过：星露谷点位 / 自动箱子 / ESP 测试项都会消失",
            new SettingToggle(settings::enabled, settings::setEnabled));

        // ── 各模块 ESP（用户 2026-09-18：「我的 esp 全局设置是不是可以调的…可以调这些插件模块的配置」）──
        // 层次从粗到细：先「全部 ESP」总开关，再「某一个模块」的总闸；颜色与「画什么」不在这里，仍归各模块控制台
        SettingModule layers = group("各模块 ESP",
            "一处关掉某个模块的全部 ESP；颜色与「画什么」仍归各模块自己的控制台", ICON_LAYERS);
        for (EspGlobalSettings.Layer layer : EspGlobalSettings.Layer.values()) {
            layers.addSub(layer.label() + " ESP", layerHint(layer),
                new SettingToggle(() -> settings.layerEnabled(layer),
                    value -> settings.setLayerEnabled(layer, value)));
        }

        // ── 外观：怎么画 ──
        SettingModule look = group("外观", "线宽、渲染模式与透视；只改「怎么画」，不改模块自己的颜色", ICON_LOOK);
        look.addSub("线宽倍率", "所有线框的粗细统一乘这个系数（0.5~3.0，默认 1.0）",
            new SettingNumberBox(EspGlobalSettings.THICKNESS_MIN, EspGlobalSettings.THICKNESS_MAX, 0.1, "%.1fx",
                () -> settings.thicknessScale(), settings::setThicknessScale));
        // 两条循环行的说明末尾接可见提示（第 213 条）：循环控件看不出能点
        look.addSub("渲染模式覆盖", "默认跟随各模块；覆盖后全部 ESP 统一成线框 / 面 / 两者" + HINT_CYCLE,
            new SettingCycle(List.of(ModeOverride.labels()), () -> settings.modeOverride().ordinal(),
                index -> settings.setModeOverride(ModeOverride.values()[index])));
        look.addSub("透视覆盖", "默认跟随各模块；「全部透视」穿墙也画，「全部遮挡」被方块挡住就不画" + HINT_CYCLE,
            new SettingCycle(List.of(OcclusionOverride.labels()), () -> settings.occlusionOverride().ordinal(),
                index -> settings.setOcclusionOverride(OcclusionOverride.values()[index])));
        addBlockOutlineRows(look);

        // ── 可见度：整体透明度与距离 ──
        SettingModule visibility = group("可见度", "整体不透明度与显示距离", ICON_VISIBILITY);
        visibility.addSub("不透明度倍率", "所有 ESP 颜色统一按这个百分比变淡（10%~100%，默认 100%）",
            new SettingNumberBox(10, 100, 5, "%.0f%%",
                () -> (double) settings.alphaScale(), value -> settings.setAlphaScale(value / 100d)));
        visibility.addSub("最远显示距离", "超过这个距离的 ESP 一律不画（0 = 不限，单位格）",
            new SettingNumberBox(EspGlobalSettings.DISTANCE_MIN, EspGlobalSettings.DISTANCE_MAX, 1, "%.0f",
                () -> (double) settings.maxDistance(), settings::setMaxDistance));
        visibility.addSub("距离淡出", "开启后从「淡出起点」开始随距离变淡，到最远距离刚好淡到看不见",
            new SettingToggle(settings::fade, settings::setFade));
        visibility.addSub("淡出起点", "这个距离以内完全不透明（未设最远距离时以 64 格为最远）",
            new SettingNumberBox(EspGlobalSettings.DISTANCE_MIN, EspGlobalSettings.DISTANCE_MAX, 1, "%.0f",
                () -> (double) settings.fadeStart(), settings::setFadeStart));

        // ── 文字 ──
        SettingModule text = group("文字", "字牌字号与底板（模块自己设的颜色不受影响）", ICON_TEXT);
        text.addSub("文字大小倍率", "所有 ESP 字牌的字号统一乘这个系数（0.5~2.5，默认 1.0）",
            new SettingNumberBox(EspGlobalSettings.TEXT_SCALE_MIN, EspGlobalSettings.TEXT_SCALE_MAX, 0.1, "%.1fx",
                () -> (double) settings.textScale(), settings::setTextScale));
        text.addSub("文字底板", "字牌是否压一块深色底板；关掉只剩纯色字，亮背景下更难读",
            new SettingToggle(settings::textPlate, settings::setTextPlate));

        // ── 性能 ──
        SettingModule performance = group("性能", "成片 ESP 拖慢帧率时的保底", ICON_PERFORMANCE);
        performance.addSub("每帧最多画多少个图形", "一帧最多画多少个图形，超出后本帧不再画（0 = 不限）；每个框、每条线、每个面、每段字各算一个",
            new SettingNumberBox(EspGlobalSettings.BUDGET_MIN, EspGlobalSettings.BUDGET_MAX, 16, "%.0f",
                () -> (double) settings.primitiveBudget(), settings::setPrimitiveBudget));

        // ── 自己 ──
        SettingModule self = group("自己", "本机玩家的 ESP 是否显示", ICON_SELF);
        self.addSub("隐藏自己（第一人称）", "第一人称下不画自己的 ESP（默认开启，避免糊在准星上挡视野）",
            new SettingToggle(settings::hideSelfFirstPerson, settings::setHideSelfFirstPerson));
        self.addSub("隐藏自己（第三人称）", "第三人称（F5）下也不画自己（默认关闭）",
            new SettingToggle(settings::hideSelfThirdPerson, settings::setHideSelfThirdPerson));
    }

    /**
     * 打开使用说明窗口（固定走 {@link HelpPanelScreen}，格式不得改动——开发习惯第 137 条）。
     *
     * <p>parent 取当前屏幕：关窗 / 返回都回到打开它的这个设置页（{@code HelpPanelScreen} 的关闭语义）。</p>
     */
    private void openHelp() {
        Minecraft client = Minecraft.getInstance();
        client.setScreen(new HelpPanelScreen(getTitle(),
            HelpPanelScreen.buildHelpContent(HELP_SECTIONS), client.screen));
    }

    /**
     * 各模块总闸的说明：写清「关掉后这个模块的哪些绘制会消失」。
     *
     * <p>开关值写在各渲染层的入口（见 {@code EspGlobalSettings.Layer} 的注释），因此抑制期间只是不画，
     * 模块自身设置与运行状态一概不动。</p>
     */
    private static String layerHint(EspGlobalSettings.Layer layer) {
        return switch (layer) {
            case MINING -> "矿点 / 矿物箱 / 食物箱 / 挂机修复点的框、岩浆框、挖掘进度框与百分比，全部不画";
            case STARDEW -> "农田框、错位格、洒水器范围预览与星露谷字牌全部不画";
            case AUTO_CHEST -> "自动箱子扫描到的箱子框与字牌不画";
            case VILLAGER -> "村民容器（绿宝石箱 / 成品交易箱）的框与字牌不画";
            case ADMIN -> "管理员检测的威胁框与追踪线不画";
            case VISION -> "方块与实体透视的框、射线全部不画";
            case TELEPORT -> "传送调试渲染的目标点 / 穿墙射线 / 回弹点 / 候选格与移动对象框全部不画";
            case INSTANT_BREAK -> "发包秒破的进度框、收缩框与百分比标签（含排队候选方块）全部不画";
            case BONE_MEAL -> "自动骨粉的候选目标框全部不画";
        };
    }

    /**
     * 瞄准方块高亮：开关 + 描边颜色（用户 2026-09-18 需求「瞄准方块默认带白色的框」）。
     *
     * <p>颜色只写回 RGB：透明度由渲染层固定为不透明，避免调出看不见的描边。
     * 调色板关窗必定回调，所以按「与打开时是否相同」判改动，避免点开看一眼就把颜色钉住。</p>
     */
    private void addBlockOutlineRows(SettingModule look) {
        look.addSub("瞄准方块高亮", "准星指向的方块画一圈整格描边（默认开，只画描边不填充，不妨碍对准方块）",
            new SettingToggle(settings::blockOutline, settings::setBlockOutline));

        int current = settings.blockOutlineColor() & 0xFFFFFF;
        EspColor carried = new EspColor(current, 255);
        look.addSub("瞄准方块描边颜色", "默认 (255,255,255) 白",
            new SettingColorPicker("瞄准方块描边颜色", carried, () -> {
                int picked = carried.rgb() & 0xFFFFFF;
                if (picked == current) return;
                settings.setBlockOutlineColor(picked);
            }));
    }

    /**
     * 建一个只有子项的设置分组。
     *
     * <p><b>走图标行</b>（{@link SettingModule#icon(String)}）：本页六个分组原来是大卡片
     * （表头 56 高、标题 + 说明两行），实机反馈「esp设置按钮还是很大  你没改」——与模块中心、
     * 设置页、界面页的一级行不是同一个节奏。图标行把表头压到 {@code ModuleRow.HEIGHT}（24）：
     * 行首一个 Material 图标、标题与说明并排一行，四个页面这才真正一致。</p>
     */
    private SettingModule group(String title, String subtitle, String icon) {
        SettingModule module = new SettingModule(UiText.t(title, title), UiText.t(subtitle, subtitle), null)
                .icon(icon);
        modules.add(module);
        return module;
    }

    /** 行距与模块中心一致（图标行必须读这一个值，绘制/命中/总高度都走它）。 */
    @Override
    protected float moduleGap() {
        return ModuleRow.ROW_GAP;
    }

    @Override
    public String getTitle() {
        return UiText.t("ESP 全局设置", "ESP Global Settings");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("作用于全部世界 ESP 的公共选项：线宽 / 透明度 / 距离 / 字号 / 开销",
            "Shared options for every world ESP: thickness, opacity, distance, text and budget");
    }
}
