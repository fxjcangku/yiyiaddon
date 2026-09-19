package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.command.WkCommand;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.console.PointCardGrid;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_COLOR;

/**
 * 自动挖矿控制台「点位」页：三点点位的绑定 / 删除 + 显示与颜色。
 *
 * <p><b>样式与星露谷点位页同款</b>（用户 2026-09-16 指令「自动挖矿箱子esp 点位 给我跟星露谷点位设置
 * 带我那个一样」）：小节标题行 → 2 列卡片网格 → 「清空全部点位」按钮 + 二次确认 →
 * 「显示与颜色」小节（三行颜色 + 一行字牌大小 + 容器标签的字号倍率 / 文字颜色两行
 * + 挖掘进度 ESP 的开关 / 两个颜色三行）→ 底部指令提示。卡片本体是公共件
 * {@link com.yiyiaddon.ui.console.PointCardGrid.PointCard}（由星露谷点位页抽出），卡片外观、
 * 等高口径与命中算法两处完全一致。</p>
 *
 * <p><b>交互与文案与控制台入口所在配置页刻意同源：</b>按钮文案（{@code §a设置} / {@code §8设置} /
 * {@code §c删除}）、绑定 / 解绑调用（{@code bind(type, true)} / {@code remove(type, true)}）、
 * 点击后回到游戏的做法沿用旧写法；坐标串 {@code §7X§f%d §7Y§f%d §7Z§f%d} 与维度名
 * {@code §7维度 §f<名>} 沿用本页原有取值，未绑定 {@code §8暂未绑定} / {@code §8-}。</p>
 *
 * <p><b>清空与指令同源：</b>二次确认的确认动作直接调 {@link WkCommand#clearAllBindings()}
 * ——{@code .wk 清空} 与这里共用同一处清空实现与同一份回执（不写第二套）。</p>
 *
 * <p><b>颜色与字牌大小接的是模块既有机制：</b>调色板关窗回调 {@code module::syncColorsToSettings}
 * （把载体 ARGB 写回 {@code settings.*Color} 并落盘，与自动箱子渲染页同一构造），
 * 字牌大小走 {@code module.persistSettings()}；容器标签的两项（字号倍率 / 文字颜色）同样只读写
 * {@link MiningSettings} 并由 {@code persistSettings()} 落盘，不新增第二套持久化。</p>
 */
public final class MiningPointPage {

    // ── 「显示与颜色」行文案（名称与默认值取自 MiningSettings 里这四项的中文注释） ──

    private static final String NAME_MINERAL_COLOR = "矿物箱 ESP 颜色";
    private static final String DESC_MINERAL_COLOR = "默认 (255,215,0)";
    private static final String NAME_FOOD_COLOR = "食物箱 ESP 颜色";
    private static final String DESC_FOOD_COLOR = "默认 (100,255,100)";
    private static final String NAME_AFK_COLOR = "挂机修复点 ESP 颜色";
    private static final String DESC_AFK_COLOR = "默认 (255,100,255)";
    private static final String NAME_ESP_SCALE = "ESP 字号倍率";
    private static final String DESC_ESP_SCALE = "默认 2.0";
    private static final String NAME_CONTAINER_TEXT_SCALE = "容器标签字号倍率";
    private static final String DESC_CONTAINER_TEXT_SCALE = "矿物箱/食物箱头顶文字在「ESP 字号倍率」之上再乘的系数（默认 1.0）";
    private static final String NAME_CONTAINER_TEXT_COLOR = "容器标签文字颜色";
    private static final String DESC_CONTAINER_TEXT_COLOR = "默认跟随各点位颜色";

    // ── 「显示与颜色」里挖掘进度 ESP 三行（用户 2026-09-18 追加，独立开关 + 两个颜色） ──

    private static final String NAME_BREAK_PROGRESS = "挖掘进度显示";
    private static final String DESC_BREAK_PROGRESS = "自动挖矿时在被挖的方块上显示百分比 + 收缩框（纯显示，关掉不影响挖矿）";
    private static final String NAME_BREAK_BUSY_COLOR = "挖掘进度颜色（挖掘中）";
    private static final String DESC_BREAK_BUSY_COLOR = "默认 (204,32,32) 红";
    private static final String NAME_BREAK_READY_COLOR = "挖掘进度颜色（已完成）";
    private static final String DESC_BREAK_READY_COLOR = "默认 (32,204,80) 绿";

    /** 字号倍率取值域 / 步长（与设置默认值同一口径） */
    private static final double ESP_SCALE_MIN = 0.5;
    private static final double ESP_SCALE_MAX = 8.0;
    private static final double ESP_SCALE_STEP = 0.5;

    /** 容器标签字号倍率取值域 / 步长（与 {@link MiningSettings#espContainerTextScale} 的读取裁剪同一口径） */
    private static final double CONTAINER_TEXT_SCALE_MIN = 0.5;
    private static final double CONTAINER_TEXT_SCALE_MAX = 4.0;
    private static final double CONTAINER_TEXT_SCALE_STEP = 0.5;

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final MiningSettings DEFAULTS = new MiningSettings();

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;

    public MiningPointPage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        stack.add(new Note(owner, "§7§l点位绑定 §8（准星对准方块后点「设置」）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new PointCardGrid.Grid(pointCards()));
        stack.add(new ButtonStrip(owner, List.of(new Ctl(new Button("§c清空全部点位", this::openClearConfirm),
            "删除当前服务器已绑定的全部点位（不可恢复，会二次确认）")), ButtonStrip.BUTTON_HEIGHT));

        stack.add(new Note(owner, "§7§l显示与颜色", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(colorRow(NAME_MINERAL_COLOR, DESC_MINERAL_COLOR, module.mineralColor(), DEFAULTS.mineralColor));
        stack.add(colorRow(NAME_FOOD_COLOR, DESC_FOOD_COLOR, module.foodColor(), DEFAULTS.foodColor));
        stack.add(colorRow(NAME_AFK_COLOR, DESC_AFK_COLOR, module.afkColor(), DEFAULTS.afkColor));
        stack.add(labelSizeRow());
        stack.add(containerTextScaleRow());
        stack.add(containerTextColorRow());
        stack.add(breakProgressToggleRow());
        stack.add(breakProgressColorRow(NAME_BREAK_BUSY_COLOR, DESC_BREAK_BUSY_COLOR, false,
            DEFAULTS.breakProgressBusyColor));
        stack.add(breakProgressColorRow(NAME_BREAK_READY_COLOR, DESC_BREAK_READY_COLOR, true,
            DEFAULTS.breakProgressReadyColor));
        stack.add(new Note(owner, "§8也可以用指令：§f.wk 设置 … §8/ §f.wk 移除 …"));
    }

    // ── 点位卡网格 ──

    /** 三张点位卡：矿物箱 / 食物箱 / 挂机修复点（顺序取自枚举，题目配色沿用本页 {@link #pointTitleColor}） */
    private List<PointCardGrid.PointCard> pointCards() {
        List<PointCardGrid.PointCard> cards = new ArrayList<>();
        for (MiningPointType type : MiningPointType.values()) cards.add(pointCard(type));
        return cards;
    }

    /**
     * 点位卡：标题 + 坐标与维度两行 + 「设置」「删除」两行按钮。
     *
     * <p>已绑定：坐标串 + {@code §7维度 §f<维度名>}；已绑定但不在当前维度：
     * {@code §8本维度未绑定 §7（其它维度已绑）} + {@code §8-}；未绑定：{@code §8暂未绑定} + {@code §8-}。
     * 删除按钮无条件显示，条件门控在点击回调内（与配置页卡片同一写法）。</p>
     */
    private PointCardGrid.PointCard pointCard(MiningPointType type) {
        MiningPoint point = module.pointStore().get(type);
        String info1;
        String info2;
        if (point != null && !point.inCurrentDimension()) {
            // 其它维度标过就说清楚，避免玩家以为点位丢了、跑去删了重标
            info1 = "§8本维度未绑定 §7（其它维度已绑）";
            info2 = "§8-";
        } else if (point != null) {
            info1 = String.format("§7X§f%d §7Y§f%d §7Z§f%d", point.x(), point.y(), point.z());
            info2 = "§7维度 §f" + WorldIdentity.dimensionDisplayName(point.dimension());
        } else {
            info1 = "§8暂未绑定";
            info2 = "§8-";
        }
        return new PointCardGrid.PointCard(pointTitleColor(type) + type.displayName(), info1, info2, List.of(
            List.of(new Button((point != null ? "§a" : "§8") + "设置", () -> {
                // 绑定失败（准星未命中 / 非容器 / 该类型已绑）时留在页面：错误已经播报，玩家可当场重设
                if (module.bindingService().bind(type, true)) closeToGame();
            })),
            List.of(new Button("§c删除", () -> {
                if (module.bindingService().remove(type, true)) closeToGame();
            })))).icon(() -> cardIcon(type));
    }

    /**
     * 卡片头图标（与原版语义一一对应，未绑定也显示、不留空洞）：
     * 矿物箱＝原金矿（矿物）、食物箱＝烤牛肉（挖矿口粮）、挂机修复点＝附魔之瓶
     * （模块自检里「挂机修复依赖经验修补」的同一语义）。
     */
    private static ItemStack cardIcon(MiningPointType type) {
        return switch (type) {
            case MINERAL -> new ItemStack(Items.RAW_GOLD);
            case FOOD -> new ItemStack(Items.COOKED_BEEF);
            case AFK -> new ItemStack(Items.EXPERIENCE_BOTTLE);
        };
    }

    /** 清空全部点位：二次确认（确认动作与 {@code .wk 清空} 同源，正文按本模块三个点位写） */
    private void openClearConfirm() {
        if (owner.client() == null) return;
        owner.client().setScreen(new ConfirmPanelScreen("清空全部点位",
            List.of("§f将删除当前服务器已绑定的全部点位",
                "§7矿物箱 · 食物箱 · 挂机修复点",
                "",
                "§c此操作不可恢复。"),
            "§c§l确认", WkCommand::clearAllBindings, owner.client().screen));
    }

    // ── 显示与颜色 ──

    /**
     * 颜色行：调色板关窗时先把载体颜色同步回设置项再落盘。
     *
     * <p>只传 {@code persistSettings} 是不行的：颜色还没同步进 {@code settings.*Color}，
     * 存下去的是旧值（与自动箱子渲染页同一构造）。</p>
     *
     * <p>行尾带可见提示（第 213 条）：色块本身只是纯色底，看不出能点。</p>
     *
     * <p>行内 ↺ 把出厂色就地写回行内控件持有的那个颜色对象（字段是 final，不能换引用），
     * 再走与调色板同一条同步链路（{@code syncColorsToSettings} 内部落盘）。</p>
     */
    private ConsoleRow colorRow(String name, String description, EspColor color, int defaultArgb) {
        return new ConsoleRow(owner, () -> name, description, COMMENT_COLOR,
            List.of(new Ctl(new SettingColorPicker(name, color, module::syncColorsToSettings)),
                ConsoleWidgets.resetCtl(() -> {
                    copyColor(color, defaultArgb);
                    module.syncColorsToSettings();
                    owner.reload();
                }, name)));
    }

    /** 把出厂色就地写给行内控件持有的那个颜色对象（字段是 final，不能换引用） */
    private static void copyColor(EspColor target, int argb) {
        EspColor source = new EspColor(argb & 0xFFFFFF, (argb >>> 24) & 0xFF);
        target.rgb(source.rgb()).alpha(source.alpha()).rainbow(source.rainbow())
            .rainbowSpeed(source.rainbowSpeed()).rainbowOffset(source.rainbowOffset());
    }

    /** 字牌大小：ESP 头顶文字的字号倍率，与三行颜色分开一行（改动落盘） */
    private CompactElement labelSizeRow() {
        MiningSettings settings = module.settings();
        SettingNumberBox box = new SettingNumberBox(ESP_SCALE_MIN, ESP_SCALE_MAX, ESP_SCALE_STEP, "%.1f",
            () -> settings.espScale,
            value -> {
                settings.espScale = value;
                module.persistSettings();
            });
        return new ConsoleRow(owner, () -> NAME_ESP_SCALE, DESC_ESP_SCALE, null, List.of(new Ctl(box),
            ConsoleWidgets.resetCtl(() -> {
                settings.espScale = DEFAULTS.espScale;
                module.persistSettings();
                owner.reload();
            }, NAME_ESP_SCALE)));
    }

    /** 容器标签字号倍率：矿物箱/食物箱头顶文字在整体字号之上再乘的系数（改动落盘） */
    private CompactElement containerTextScaleRow() {
        MiningSettings settings = module.settings();
        SettingNumberBox box = new SettingNumberBox(CONTAINER_TEXT_SCALE_MIN, CONTAINER_TEXT_SCALE_MAX,
            CONTAINER_TEXT_SCALE_STEP, "%.1f",
            () -> settings.espContainerTextScale,
            value -> {
                settings.espContainerTextScale = value;
                module.persistSettings();
            });
        return new ConsoleRow(owner, () -> NAME_CONTAINER_TEXT_SCALE, DESC_CONTAINER_TEXT_SCALE, null,
            List.of(new Ctl(box),
                ConsoleWidgets.resetCtl(() -> {
                    settings.espContainerTextScale = DEFAULTS.espContainerTextScale;
                    module.persistSettings();
                    owner.reload();
                }, NAME_CONTAINER_TEXT_SCALE)));
    }

    /**
     * 容器标签文字颜色：调色板改的是本行自带的临时载体，关窗时按「与打开时不同」判改动再写设置。
     *
     * <p><b>为什么要判改动</b>：设置项 {@code 0} 表示「跟随各点位颜色」（默认，观感与旧项目一致）。
     * 调色板关窗一定会回调，若照三行点位颜色那样无条件写盘，「点开看一眼再关掉」就会把跟随态钉成固定色
     * —— 那不是用户的操作意图。</p>
     *
     * <p>跟随态下色块显示矿物箱当前的颜色，作为「此刻文字主色」的可视锚点（两个容器颜色不同，
     * 单一色块只能取其中一个代表）。</p>
     */
    private ConsoleRow containerTextColorRow() {
        MiningSettings settings = module.settings();
        int current = settings.espContainerTextColor;
        EspColor carried = current != 0
            ? new EspColor(current & 0xFFFFFF, (current >>> 24) & 0xFF)
            : new EspColor(module.mineralColor().rgb(), module.mineralColor().alpha());
        SettingColorPicker picker = new SettingColorPicker(NAME_CONTAINER_TEXT_COLOR, carried, () -> {
            int picked = ((carried.alpha() & 0xFF) << 24) | (carried.rgb() & 0xFFFFFF);
            if (picked == current) return;
            settings.espContainerTextColor = picked;
            module.persistSettings();
        });
        return new ConsoleRow(owner, () -> NAME_CONTAINER_TEXT_COLOR, DESC_CONTAINER_TEXT_COLOR, COMMENT_COLOR,
            List.of(new Ctl(picker),
                // 出厂值 0 = 跟随各点位颜色；整页重建后色块回到「此刻文字主色」的显示口径
                ConsoleWidgets.resetCtl(() -> {
                    settings.espContainerTextColor = DEFAULTS.espContainerTextColor;
                    module.persistSettings();
                    owner.reload();
                }, NAME_CONTAINER_TEXT_COLOR)));
    }

    /**
     * 挖掘进度 ESP 独立开关（用户 2026-09-18）：默认开，纯显示项，关掉不影响秒破与挖矿行为。
     *
     * <p>与「秒破」开关刻意解耦：秒破关着也允许显示（此进度只反映正在被破坏的方块），
     * 想安静时直接关这一行即可。</p>
     */
    private ConsoleRow breakProgressToggleRow() {
        MiningSettings settings = module.settings();
        SettingToggle toggle = new SettingToggle(() -> settings.breakProgressEsp, value -> {
            settings.breakProgressEsp = value;
            module.persistSettings();
        });
        return new ConsoleRow(owner, () -> NAME_BREAK_PROGRESS, DESC_BREAK_PROGRESS, null, List.of(new Ctl(toggle),
            ConsoleWidgets.resetCtl(() -> {
                settings.breakProgressEsp = DEFAULTS.breakProgressEsp;
                module.persistSettings();
                owner.reload();
            }, NAME_BREAK_PROGRESS)));
    }

    /**
     * 挖掘进度框颜色（{@code ready=false} 挖掘中 / {@code true} 已完成）。
     *
     * <p>设置项存 <b>RGB</b>，透明度由渲染层固定（面 40 / 描边 255），所以这里把调色板的 alpha 丢掉
     * —— 否则用户把 alpha 调到 0 会得到一个看不见的进度框。</p>
     */
    private ConsoleRow breakProgressColorRow(String name, String description, boolean ready, int defaultRgb) {
        MiningSettings settings = module.settings();
        int current = ready ? settings.breakProgressReadyColor : settings.breakProgressBusyColor;
        EspColor carried = new EspColor(current & 0xFFFFFF, 255);
        SettingColorPicker picker = new SettingColorPicker(name, carried, () -> {
            int picked = carried.rgb() & 0xFFFFFF;
            if (picked == current) return;
            if (ready) {
                settings.breakProgressReadyColor = picked;
            } else {
                settings.breakProgressBusyColor = picked;
            }
            module.persistSettings();
        });
        return new ConsoleRow(owner, () -> name, description, COMMENT_COLOR, List.of(new Ctl(picker),
            // 出厂值只有 RGB（透明度由渲染层固定），所以只把色相写回载体
            ConsoleWidgets.resetCtl(() -> {
                carried.rgb(defaultRgb);
                if (ready) {
                    settings.breakProgressReadyColor = defaultRgb;
                } else {
                    settings.breakProgressBusyColor = defaultRgb;
                }
                module.persistSettings();
                owner.reload();
            }, name)));
    }

    /** 执行后直接回到游戏（旧项目 {@code mc.setScreen(null)}） */
    private void closeToGame() {
        owner.client().setScreen(null);
    }

    /** 卡片标题配色（旧 {@code :1561-1566}）：矿物箱金 / 食物箱绿 / 挂机修复点粉 */
    private static String pointTitleColor(MiningPointType type) {
        return switch (type) {
            case MINERAL -> "§6";
            case FOOD -> "§2";
            case AFK -> "§d";
        };
    }
}
