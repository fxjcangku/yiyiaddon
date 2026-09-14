package com.yiyiaddon.feature.stardew.ui.console;

import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.point.StardewPointManager;
import com.yiyiaddon.feature.stardew.point.StardewPointType;
import com.yiyiaddon.feature.stardew.ui.StardewConsoleScreen;
import com.yiyiaddon.feature.stardew.ui.StardewRenderObjectScreen;
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
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;

import java.util.ArrayList;
import java.util.List;

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
        stack.add(new Note(owner, "§8也可以用指令：§f.stardew 绑定 … §8/ §f.stardew 移除 …"));
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
            new Ctl(settings, "打开「" + object.name() + "」的显示 / 颜色 / 渲染模式")));
    }

    /** 清空全部点位：二次确认（正文与确认按钮逐字照旧控制台） */
    private void openClearConfirm() {
        if (owner.client() == null) return;
        owner.client().setScreen(new ConfirmPanelScreen("清空全部点位",
            List.of("§f将删除当前服务器已绑定的全部点位",
                "§7农田起点 · 农田终点 · 种子箱 · 成品箱 · 补水点 · 洒水器",
                "",
                "§c此操作不可恢复。"),
            "§c§l确认", module::clearAllPoints, owner.client().screen));
    }

    /**
     * 六张点位卡片（2 行 x 3 列）。
     *
     * <p>旧项目把六张卡铺在同一个表格行里；本项目面板宽度较窄，改成三列两行，
     * 卡片内容（标题 / 坐标行 / 维度行 / 主次按钮）与文案逐字不变。</p>
     */
    private final class CardGrid implements CompactElement {

        private static final int COLUMNS = 3;
        private static final float GAP_X = 10f;
        private static final float GAP_Y = 10f;

        private final List<PointCard> cards = new ArrayList<>();

        private CardGrid() {
            cards.add(locationCard(StardewPointType.START, "§a", "农田起点"));
            cards.add(locationCard(StardewPointType.END, "§e", "农田终点"));
            cards.add(locationCard(StardewPointType.SEED_BOX, "§b", "种子箱"));
            cards.add(locationCard(StardewPointType.OUTPUT_BOX, "§6", "成品箱"));
            cards.add(locationCard(StardewPointType.WATER_SOURCE, "§d", "补水点"));
            cards.add(sprinklerCard());
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
                info1 = "§8暂未绑定";
                info2 = "§8-";
            }
            return new PointCard(color + title, info1, info2,
                (point != null ? "§a" : "§8") + "设置",
                () -> {
                    if (module.setPointFromCrosshair(type)) owner.closeToGame();
                },
                "§c删除",
                () -> {
                    if (module.removePoint(type)) owner.closeToGame();
                });
        }

        private PointCard sprinklerCard() {
            int count = module.pointManager().count(StardewPointType.SPRINKLER);
            return new PointCard("§b洒水器",
                count > 0 ? "§7已绑定 §f" + count + " §7个" : "§8暂未绑定",
                "§7多点维护",
                "§a添加",
                () -> {
                    if (module.addSprinklerFromCrosshair()) owner.closeToGame();
                },
                "§c移除",
                () -> {
                    if (module.removeSprinklerFromCrosshair()) owner.closeToGame();
                });
        }

        @Override
        public float height() {
            return CardLayout.totalHeight(cards.size(), PointCard.HEIGHT, COLUMNS);
        }

        @Override
        public void update(float dt) {
            for (PointCard card : cards) card.update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            float cardWidth = CardLayout.cardWidth(width, COLUMNS);
            for (int i = 0; i < cards.size(); i++) {
                cards.get(i).draw(canvas, CardLayout.cardX(x, width, COLUMNS, i),
                    CardLayout.cardY(y, PointCard.HEIGHT, COLUMNS, i), cardWidth, alpha, mouseX, mouseY);
            }
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            float cardWidth = CardLayout.cardWidth(width, COLUMNS);
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).onClick(mx, my, CardLayout.cardX(x, width, COLUMNS, i),
                    CardLayout.cardY(y, PointCard.HEIGHT, COLUMNS, i), cardWidth, button)) {
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

    /** 单张点位卡片：标题 / 坐标行 / 维度行 + 主次按钮（文案与行为逐字照旧） */
    private final class PointCard implements CompactElement {

        private static final float HEIGHT = 130f;
        private static final float TITLE_HEIGHT = 20f;
        private static final float INFO_HEIGHT = 18f;
        private static final float BUTTON_HEIGHT = 24f;
        private static final float CARD_GAP = 6f;
        private static final float CARD_PAD = 10f;
        private static final float TITLE_SIZE = 11f;
        private static final float INFO_SIZE = 11f;

        private final String title;
        private final String info1;
        private final String info2;
        private final Button primary;
        private final Button secondary;

        private PointCard(String title, String info1, String info2, String primaryLabel, Runnable primaryAction,
                          String secondaryLabel, Runnable secondaryAction) {
            this.title = title;
            this.info1 = info1;
            this.info2 = info2;
            this.primary = new Button(primaryLabel, primaryAction);
            this.secondary = new Button(secondaryLabel, secondaryAction);
        }

        @Override
        public float height() {
            return HEIGHT;
        }

        @Override
        public void update(float dt) {
            primary.update(dt);
            secondary.update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            ClickGuiThemeColors tc = ClickGuiThemeColors.current();
            float radius = GlassPanel.rowRadius(HEIGHT);
            float rowAlpha = ClickGuiThemeColors.panelBackgroundAlpha(alpha);
            GlassPanel.frost(canvas, x, y, width, HEIGHT, radius, tc.module, 0.70f, rowAlpha);
            GlassPanel.rim(canvas, x, y, width, HEIGHT, radius, tc.rim, alpha, 0.10f);

            float centerX = x + width / 2f;
            float cursorY = y + CARD_GAP;
            drawCentered(canvas, title, centerX, cursorY, TITLE_HEIGHT, TITLE_SIZE, alpha, true);
            cursorY += TITLE_HEIGHT;
            drawCentered(canvas, info1, centerX, cursorY, INFO_HEIGHT, INFO_SIZE, alpha, false);
            cursorY += INFO_HEIGHT + 2f;
            drawCentered(canvas, info2, centerX, cursorY, INFO_HEIGHT, INFO_SIZE, alpha, false);
            cursorY += INFO_HEIGHT + CARD_GAP;

            float buttonWidth = width - CARD_PAD * 2f;
            float primaryX = x + CARD_PAD;
            primary.hover(mouseX, mouseY, primaryX, cursorY, buttonWidth);
            primary.drawAt(canvas, primaryX, cursorY, buttonWidth, alpha);
            cursorY += BUTTON_HEIGHT + CARD_GAP;
            secondary.hover(mouseX, mouseY, primaryX, cursorY, buttonWidth);
            secondary.drawAt(canvas, primaryX, cursorY, buttonWidth, alpha);
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
            float buttonWidth = width - CARD_PAD * 2f;
            float buttonX = x + CARD_PAD;
            float primaryY = y + CARD_GAP + TITLE_HEIGHT + INFO_HEIGHT + 2f + INFO_HEIGHT + CARD_GAP;
            if (primary.onClickAt(mx, my, buttonX, primaryY, buttonWidth, button)) return true;
            return secondary.onClickAt(mx, my, buttonX, primaryY + BUTTON_HEIGHT + CARD_GAP, buttonWidth, button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }
}
