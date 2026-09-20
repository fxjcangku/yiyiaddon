package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.feature.stardew.ui.StardewRegionListScreen;
import com.yiyiaddon.feature.stardew.ui.StardewSprinklerListScreen;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.console.PointCardGrid.PointCard;
import com.yiyiaddon.ui.console.PointRenderSection;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.RenderObjectScreen;
import com.yiyiaddon.ui.widget.Button;
import io.github.humbleui.skija.Canvas;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * 星露谷控制台「点位」页：绑定点位 + 显示设置。
 *
 * <p>逐字搬运自 {@code StardewConsoleScreen.buildPoints} 与其 {@code CardGrid} / {@code PointCard}
 * 两个页内构件；方法体、文案与结构一字未改，只把宿主窗口访问改为经字段读取。</p>
 *
 * <p>2026-09-16：「点位卡」（原页内 {@code PointCard}）抽为公共件
 * {@link com.yiyiaddon.ui.console.PointCardGrid.PointCard}，本页与自动挖矿点位页共用；
 * 本页的引用点只是换了类型名，卡片常量、绘制与命中一字未动。农田卡 + 用法说明 + 卡片网格
 * 的混合布局（{@link CardGrid}）仍留在这里。</p>
 */
public final class StardewPointPage {

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final StardewSettings DEFAULTS = new StardewSettings();

    private final StardewConsoleScreen owner;
    private final StardewFarmModule module;

    public StardewPointPage(StardewConsoleScreen owner, StardewFarmModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        stack.add(new Note(owner, "§7§l点位绑定 §8（准星对准方块后点「设置」）", null,
            StardewConsoleScreen.SECTION_HEIGHT, StardewConsoleScreen.SECTION_SIZE));
        stack.add(new CardGrid());
        stack.add(new ButtonStrip(owner, List.of(new Ctl(new Button("§c清空全部点位", this::openClearConfirm),
            "删除当前服务器已绑定的全部点位（不可恢复，会二次确认）")), ButtonStrip.BUTTON_HEIGHT));

        stack.add(PointRenderSection.title(owner));
        // 每类渲染对象一行（显示开关 + 「设置」窗口 + 行尾 ↺）+ 字牌大小一行：
        // 行构件是共用件 PointRenderSection（用户 2026-09-19：所有点位模块统一成星露谷这套）
        PointRenderSection renderSection = new PointRenderSection(owner,
            module::persistSettings, owner::reload, this::openRenderScreen, DEFAULTS.renderObjects());
        for (CompactElement row : renderSection.rows(module.settings().renderObjects())) {
            stack.add(row);
        }
        stack.add(renderSection.labelSizeRow(StardewSettings.NAME_LABEL_SIZE, StardewSettings.DESC_LABEL_SIZE,
            StardewSettings.LABEL_SIZE_MIN, StardewSettings.LABEL_SIZE_MAX,
            () -> module.settings().labelSize,
            value -> module.settings().labelSize = value,
            DEFAULTS.labelSize));
        stack.add(new Note(owner, "§8也可以用指令：§f.stardew 绑定 … §8/ §f.stardew 移除 …"));
    }

    /** 打开「渲染设置 · 对象名」窗口（共用件 RenderObjectScreen，六个点位模块同一份实现） */
    private void openRenderScreen(EspRenderObject object) {
        if (owner.client() == null) return;
        owner.client().gui.setScreen(new RenderObjectScreen(owner.client().gui.screen(), object,
            defaultsOf(object), module::persistSettings));
    }

    /**
     * 该渲染对象的出厂设置实例：按对象名在出厂设置里取同一项。
     *
     * <p>渲染对象是设置类里的固定字段（名字唯一且不变），取不到时返回自身（等价于不动作）。</p>
     */
    private static EspRenderObject defaultsOf(EspRenderObject object) {
        for (EspRenderObject candidate : DEFAULTS.renderObjects()) {
            if (candidate.name().equals(object.name())) return candidate;
        }
        return object;
    }

    /**
     * 清空全部点位：二次确认（正文与确认按钮逐字照旧控制台）。
     *
     * <p><b>必须用原地版确认窗</b>（用户 2026-09-22：「二次确认之后就直接关闭 ui 了，不应该到模块设置页面吗」）：
     * 默认构造器的语义是「确认完回到游戏」，于是清完点位把整个控制台一起关了，
     * 玩家还得重新按键打开。{@link ConfirmPanelScreen#inPlace} 收尾回上级窗口，
     * 控制台 {@code init()} 会重建正文，数量当场变 0。</p>
     */
    private void openClearConfirm() {
        if (owner.client() == null) return;
        owner.client().gui.setScreen(ConfirmPanelScreen.inPlace("清空全部点位",
            List.of("§f将删除当前服务器已绑定的全部点位",
                "§7种子箱 · 成品箱 · 补水点 · 洒水器（种植区域在「农田」卡片里单独清空）",
                "",
                "§c此操作不可恢复。"),
            "§c§l确认", module::clearAllPoints, owner.client().gui.screen()));
    }

    /**
     * 点位区：上面一整行「农田」卡（圈地入口，控件最多），下面四张点位卡按 2 列排。
     *
     * <p>为什么「农田」卡单独一行：它比点位卡多一行按钮（模式 + 作物 + 圈地），网格要求等高，
     * 挤进网格只能把四张点位卡也拉高留白。单独一行全宽反而层次更清楚，点位卡布局一个字没动。</p>
     */
    private final class CardGrid implements CompactElement {

        private static final int COLUMNS = 2;
        private static final float GAP_Y = 10f;
        /** 农田卡与下面用法说明之间、说明与点位卡网格之间的间距 */
        private static final float NOTE_GAP = 6f;

        private final PointCard farmCard;
        private final List<PointCard> pointCards = new ArrayList<>();

        /**
         * 农田卡的用法说明（两行灰字）。
         *
         * <p>{@code Note} 单行绘制、不换行也不截断，一行写太长就会顶出卡片右边被裁掉（实机反馈
         * 「字体都超出界面了」）；写四行又太占地方，压成两行，只说按钮干什么。</p>
         */
        private final List<Note> farmNotes = List.of(
            new Note(owner, "§8用法：§f区域§8 = 一块地一种作物（圈完弹窗选品种）；§f农场§8 = 混种已勾选作物"),
            new Note(owner, "§8「圈地」回游戏圈地（左键一角、右键对角）；「管理」看已划分的地"));

        private CardGrid() {
            farmCard = farmlandCard();
            pointCards.add(locationCard(StardewPointType.SEED_BOX, "§b", "种子箱"));
            pointCards.add(locationCard(StardewPointType.OUTPUT_BOX, "§6", "成品箱"));
            pointCards.add(locationCard(StardewPointType.WATER_SOURCE, "§d", "补水点"));
            // 岩浆箱 / 龙息箱：只有选了对应盆型才用得上，绑错会在绑定时直接拒绝并说明原因
            pointCards.add(locationCard(StardewPointType.LAVA_BOX, "§c", "岩浆箱"));
            pointCards.add(locationCard(StardewPointType.BREATH_BOX, "§5", "龙息箱"));
            pointCards.add(sprinklerCard());
        }

        /** 用法说明占的总高度 */
        private float notesHeight() {
            float total = 0f;
            for (Note note : farmNotes) total += note.height();
            return total;
        }

        private PointCard locationCard(StardewPointType type, String color, String title) {
            StardewPointManager.StardewPoint point = module.pointManager().get(type);
            String info1;
            String info2;
            if (point != null) {
                info1 = String.format("§7X§f%d §7Y§f%d §7Z§f%d", point.x(), point.y(), point.z());
                String dimension = WorldContextFormatter.dimensionSummary(point.dimension());
                info2 = dimension != null ? "§7维度 §f" + dimension : "§8-";
            } else {
                // 其它维度标过就说清楚，避免玩家以为点位丢了、跑去删了重标
                info1 = module.pointManager().hasOtherDimension(type)
                    ? "§8本维度未绑定 §7（其它维度已绑）" : "§8暂未绑定";
                info2 = "§8-";
            }
            return new PointCard(color + title, info1, info2, List.of(
                List.of(new Button((point != null ? "§a" : "§8") + "设置", () -> {
                    if (module.setPointFromCrosshair(type)) owner.closeToGame();
                })),
                List.of(new Button("§c删除", () -> {
                    if (module.removePoint(type)) owner.closeToGame();
                })))).icon(() -> pointIcon(type));
        }

        /**
         * 点位卡图标（与原版语义一一对应，未绑定也显示、不留空洞）。
         *
         * <p>「哪一类点位画什么物品」只在 {@link StardewPointType#iconItemId()} 里写一次
         * （用户 2026-09-22 要求世界字牌也带图标，两处必须同源）；本页与洒水器列表行都读
         * {@link StardewPointType#icon()}。</p>
         */
        private ItemStack pointIcon(StardewPointType type) {
            return type.icon();
        }

        private PointCard sprinklerCard() {
            int count = module.pointManager().count(StardewPointType.SPRINKLER);
            return new PointCard("§b洒水器",
                count > 0 ? "§7已绑定 §f" + count + " §7个" : "§8暂未绑定",
                "§7多点维护",
                List.of(
                    List.of(new Button("§a添加", () -> {
                        if (module.addSprinklerFromCrosshair()) owner.closeToGame();
                    })),
                    List.of(new Button("§e管理", () -> {
                        if (owner.client() != null) {
                            owner.client().gui.setScreen(new StardewSprinklerListScreen(owner.client().gui.screen(), module));
                        }
                    })))).icon(() -> pointIcon(StardewPointType.SPRINKLER));
        }

        /**
         * 农田卡片：圈地入口（地块就是唯一的范围来源）。
         *
         * <p>两种模式共用同一套选区手感（左下角实时范围预览、左键点一个角、右键点对角），
         * 切换只决定圈出来的地按哪套规则种：<b>区域模式</b> = 一块地只种一种作物（两个角点完弹窗选品种）、
         * <b>农场模式</b> = 混种（地里的空盆按后勤缺口挑已勾选作物）。</p>
         */
        private PointCard farmlandCard() {
            // 本卡的两处会变的东西（区域数 / 模式）全部现读：卡片只在构建时生成一次，
            // 写死就会出现「点模式按钮没反应」（状态变了、界面还停在旧值），圈地更会走错模式。
            return new PointCard("§a农田",
                () -> {
                    int count = module.regionsInDimension().size();
                    // 种植区域按维度划分：把当前维度写出来，避免玩家以为「我划的地丢了」
                    String dimension = WorldContextFormatter.dimensionDisplayName();
                    return count > 0 ? "§7" + dimension + " §8│ §7已划分 §f" + count + " §7个区域"
                        : "§7" + dimension + " §8│ §8暂未划分区域";
                },
                () -> module.consoleMixedMode()
                    ? "§7一块地混着种（圈完直接成区）"
                    : "§7一块地只种一种（圈完弹窗选作物）",
                List.of(
                    List.of(
                        new Button(() -> module.consoleMixedMode() ? "§b模式：农场" : "§b模式：区域",
                            module::toggleConsoleMixedMode)),
                    List.of(new Button("§a圈地", () -> {
                        // 圈地要回到游戏里点方块，所以进选区即关掉控制台（与「设置点位」同一个做法）
                        // 模式必须现读：可能刚点了「模式」按钮，卡片不会重建
                        boolean started = module.consoleMixedMode()
                            ? module.startMixedRegionSelectionFromConsole()
                            : module.startRegionSelectionFromConsole();
                        if (started) owner.closeToGame();
                    })),
                    List.of(
                        new Button("§e管理", () -> {
                            if (owner.client() != null) {
                                owner.client().gui.setScreen(new StardewRegionListScreen(owner.client().gui.screen(), module));
                            }
                        }),
                        new Button("§c清空", this::confirmClearRegions))))
                // 农田卡图标＝耕地（本卡就是圈地入口）
                .icon(() -> new ItemStack(Items.FARMLAND));
        }

        /** 清空全部区域：与「清空全部点位」同款二次确认（原地版，确认后留在控制台） */
        private void confirmClearRegions() {
            if (owner.client() == null) return;
            owner.client().gui.setScreen(ConfirmPanelScreen.inPlace("清空全部种植区域",
                List.of("§f将删除当前服务器已划分的全部种植区域",
                    "§7地里的作物不会被挖掉，只是这些地不再被管理",
                    "",
                    "§c此操作不可恢复。"),
                "§c§l确认", module::clearRegions, owner.client().gui.screen()));
        }

        @Override
        public float height() {
            return farmCard.height() + NOTE_GAP + notesHeight() + GAP_Y
                + CardLayout.totalHeight(pointCards.size(), pointCards.get(0).height(), COLUMNS);
        }

        @Override
        public void update(float dt) {
            farmCard.update(dt);
            for (PointCard card : pointCards) card.update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            farmCard.draw(canvas, x, y, width, alpha, mouseX, mouseY);
            float noteY = y + farmCard.height() + NOTE_GAP;
            for (Note note : farmNotes) {
                note.draw(canvas, x, noteY, width, alpha, mouseX, mouseY);
                noteY += note.height();
            }
            float gridY = noteY + GAP_Y;
            float cardWidth = CardLayout.cardWidth(width, COLUMNS);
            float cardHeight = pointCards.get(0).height();
            for (int i = 0; i < pointCards.size(); i++) {
                pointCards.get(i).draw(canvas, CardLayout.cardX(x, width, COLUMNS, i),
                    CardLayout.cardY(gridY, cardHeight, COLUMNS, i), cardWidth, alpha, mouseX, mouseY);
            }
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            if (farmCard.onClick(mx, my, x, y, width, button)) return true;
            float gridY = y + farmCard.height() + NOTE_GAP + notesHeight() + GAP_Y;
            float cardWidth = CardLayout.cardWidth(width, COLUMNS);
            float cardHeight = pointCards.get(0).height();
            for (int i = 0; i < pointCards.size(); i++) {
                if (pointCards.get(i).onClick(mx, my, CardLayout.cardX(x, width, COLUMNS, i),
                    CardLayout.cardY(gridY, cardHeight, COLUMNS, i), cardWidth, button)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }
}
