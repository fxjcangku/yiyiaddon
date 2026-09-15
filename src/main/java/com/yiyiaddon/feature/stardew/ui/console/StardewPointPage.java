package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.feature.stardew.ui.StardewRegionListScreen;
import com.yiyiaddon.feature.stardew.ui.StardewRenderObjectScreen;
import com.yiyiaddon.feature.stardew.ui.StardewSprinklerListScreen;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.ButtonStrip;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.Ctl;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.ConsoleRow;
import com.yiyiaddon.feature.stardew.ui.console.StardewConsoleWidgets.Note;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 星露谷控制台「点位」页：绑定点位 + 显示设置。
 *
 * <p>逐字搬运自 {@code StardewConsoleScreen.buildPoints} 与其 {@code CardGrid} / {@code PointCard}
 * 两个页内构件；方法体、文案与结构一字未改，只把宿主窗口访问改为经字段读取。</p>
 */
public final class StardewPointPage {

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

        stack.add(new Note(owner, "§7§l显示与颜色", null,
            StardewConsoleScreen.SECTION_HEIGHT, StardewConsoleScreen.SECTION_SIZE));
        for (StardewSettings.RenderObject object : module.settings().renderObjects()) {
            stack.add(renderRow(object));
        }
        stack.add(labelSizeRow());
        stack.add(new Note(owner, "§8也可以用指令：§f.stardew 绑定 … §8/ §f.stardew 移除 …"));
    }

    /** 字牌大小：点位头顶文字的字号，与「点位字牌」的显示 / 颜色分开一行 */
    private CompactElement labelSizeRow() {
        StardewSettings s = module.settings();
        SettingNumberBox box = new SettingNumberBox(
            StardewSettings.LABEL_SIZE_MIN, StardewSettings.LABEL_SIZE_MAX, 1, "%.0f",
            () -> (double) s.labelSize,
            value -> {
                s.labelSize = (int) Math.round(value);
                module.persistSettings();
            });
        return new ConsoleRow(owner, () -> StardewSettings.NAME_LABEL_SIZE, StardewSettings.DESC_LABEL_SIZE,
            null, List.of(new Ctl(box)));
    }

    /** 一行渲染对象：对象名 + 显示开关 + 「设置」（旧项目 StardewRenderSetting 的行结构） */
    private CompactElement renderRow(StardewSettings.RenderObject object) {
        SettingToggle toggle = new SettingToggle(() -> object.show, value -> {
            object.show = value;
            module.persistSettings();
        });
        Button settings = new Button("设置", () -> {
            if (owner.client() != null) {
                owner.client().setScreen(new StardewRenderObjectScreen(owner.client().screen, module, object));
            }
        });
        return new ConsoleRow(owner, () -> object.name(), object.description(), null, List.of(
            new Ctl(toggle, "显示 / 隐藏「" + object.name() + "」"),
            new Ctl(settings, object.colorEditable()
                ? "打开「" + object.name() + "」的显示 / 颜色 / 渲染模式"
                : "打开「" + object.name() + "」的显示开关（颜色跟随对应点位的方框）")));
    }

    /** 清空全部点位：二次确认（正文与确认按钮逐字照旧控制台） */
    private void openClearConfirm() {
        if (owner.client() == null) return;
        owner.client().setScreen(new ConfirmPanelScreen("清空全部点位",
            List.of("§f将删除当前服务器已绑定的全部点位",
                "§7种子箱 · 成品箱 · 补水点 · 洒水器（种植区域在「农田」卡片里单独清空）",
                "",
                "§c此操作不可恢复。"),
            "§c§l确认", module::clearAllPoints, owner.client().screen));
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
                }))));
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
                            owner.client().setScreen(new StardewSprinklerListScreen(owner.client().screen, module));
                        }
                    }))));
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
                                owner.client().setScreen(new StardewRegionListScreen(owner.client().screen, module));
                            }
                        }),
                        new Button("§c清空", this::confirmClearRegions))));
        }

        /** 清空全部区域：与「清空全部点位」同款二次确认 */
        private void confirmClearRegions() {
            if (owner.client() == null) return;
            owner.client().setScreen(new ConfirmPanelScreen("清空全部种植区域",
                List.of("§f将删除当前服务器已划分的全部种植区域",
                    "§7地里的作物不会被挖掉，只是这些地不再被管理",
                    "",
                    "§c此操作不可恢复。"),
                "§c§l确认", module::clearRegions, owner.client().screen));
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

    /** 单张卡片：标题 / 状态两行 + 若干行按钮（每行 1~2 枚，行数与卡片高度一一对应） */
    private final class PointCard implements CompactElement {

        private static final float TITLE_HEIGHT = 20f;
        private static final float INFO_HEIGHT = 18f;
        private static final float BUTTON_HEIGHT = 24f;
        private static final float CARD_GAP = 6f;
        private static final float CARD_PAD = 10f;
        private static final float TITLE_SIZE = 11f;
        private static final float INFO_SIZE = 11f;
        /** 标题与两行信息加留白的固定高度；按钮区从这儿往下排 */
        private static final float HEAD_HEIGHT = 70f;

        private final String title;
        private final Supplier<String> info1;
        private final Supplier<String> info2;
        private final List<List<Button>> rows;

        /** 固定文案的两行信息（点位卡用：内容不随开关变化，取完就离开本页）。 */
        private PointCard(String title, String info1, String info2, List<List<Button>> rows) {
            this(title, () -> info1, () -> info2, rows);
        }

        /**
         * 每帧现读的两行信息（农田卡用）。
         *
         * <p>卡片只在页面构建时生成一次，写死字符串会让按钮看起来"点了没反应"——
         * 模式/作物/区域数都必须走 {@link Supplier} 现读。</p>
         */
        private PointCard(String title, Supplier<String> info1, Supplier<String> info2, List<List<Button>> rows) {
            this.title = title;
            this.info1 = info1;
            this.info2 = info2;
            this.rows = rows;
        }

        @Override
        public float height() {
            return HEAD_HEIGHT + rows.size() * (BUTTON_HEIGHT + CARD_GAP);
        }

        @Override
        public void update(float dt) {
            for (List<Button> row : rows) {
                for (Button button : row) button.update(dt);
            }
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            ClickGuiThemeColors tc = ClickGuiThemeColors.current();
            float height = height();
            float radius = GlassPanel.rowRadius(height);
            float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
            GlassPanel.frost(canvas, x, y, width, height, radius, tc.module, 0.70f, rowAlpha);
            GlassPanel.rim(canvas, x, y, width, height, radius, tc.rim, alpha, 0.10f);

            float centerX = x + width / 2f;
            float cursorY = y + CARD_GAP;
            drawCentered(canvas, title, centerX, cursorY, TITLE_HEIGHT, TITLE_SIZE, alpha, true);
            cursorY += TITLE_HEIGHT;
            drawCentered(canvas, info1.get(), centerX, cursorY, INFO_HEIGHT, INFO_SIZE, alpha, false);
            cursorY += INFO_HEIGHT + 2f;
            drawCentered(canvas, info2.get(), centerX, cursorY, INFO_HEIGHT, INFO_SIZE, alpha, false);
            cursorY = y + HEAD_HEIGHT;

            for (List<Button> row : rows) {
                float itemWidth = rowWidth(width, row.size());
                for (int i = 0; i < row.size(); i++) {
                    float buttonX = x + CARD_PAD + i * (itemWidth + CARD_GAP);
                    row.get(i).hover(mouseX, mouseY, buttonX, cursorY, itemWidth);
                    row.get(i).drawAt(canvas, buttonX, cursorY, itemWidth, alpha);
                }
                cursorY += BUTTON_HEIGHT + CARD_GAP;
            }
        }

        /** 一行 n 枚按钮时每枚的宽度（并排等分，中间留 CARD_GAP） */
        private float rowWidth(float cardWidth, int count) {
            float total = cardWidth - CARD_PAD * 2f;
            return (total - CARD_GAP * (count - 1)) / count;
        }

        private void drawCentered(Canvas canvas, String text, float centerX, float y, float rowHeight,
                                  float size, float alpha, boolean bold) {
            if (text == null || text.isEmpty()) return;
            float textWidth = MinecraftText.measure(text, size, bold);
            MinecraftText.draw(canvas, text, centerX - textWidth / 2f,
                CardLayout.baseline(y + rowHeight / 2f, size), size,
                ClickGuiThemeColors.current().primaryText, alpha, bold);
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            if (button != 0) return false;
            float cursorY = y + HEAD_HEIGHT;
            for (List<Button> row : rows) {
                float itemWidth = rowWidth(width, row.size());
                for (int i = 0; i < row.size(); i++) {
                    float buttonX = x + CARD_PAD + i * (itemWidth + CARD_GAP);
                    if (row.get(i).onClickAt(mx, my, buttonX, cursorY, itemWidth, button)) return true;
                }
                cursorY += BUTTON_HEIGHT + CARD_GAP;
            }
            return false;
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }
}
