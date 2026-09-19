package com.yiyiaddon.feature.mining.ui;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.ConfigRecord;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.ui.console.MiningConfigDigest;
import com.yiyiaddon.feature.mining.ui.console.MiningTargetPage;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.console.ConsoleHost;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets.ButtonStrip;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.console.PointCardGrid;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配置记录窗：一键保存整套配置，并按服务器 / 单人存档读取、替换、详情、删除。
 *
 * <p><b>入口只有一处</b>（用户两次纠正后的最终位置，2026-09-18）：模块页「自动挖矿」里
 * <b>「打开控制台」按钮正下方</b>的 {@code §b服务器记录复原} 按钮（{@link AutoMinerPage}）。
 * 曾把它放在控制台页脚（六个页签底部各一份）与模块页缺失两种形态，均被用户否掉：
 * 「你弄去每个页面干嘛」「按钮在哪里」，因此只保留模块页这一处。</p>
 *
 * <p><b>记录内容 = 全部设置 + 三个点位</b>（用户 2026-09-18：「我要的是一键保存 全部配置 包括设置
 * 跟坐标点位懂吗」）：设置取 {@link MiningSettings#save} 的全部字段，点位取
 * {@code MiningPointStore#snapshot()}（矿物箱 / 食物箱 / 挂机修复点，含坐标与维度）。</p>
 *
 * <p><b>辨认与细节</b>（用户 2026-09-18：「配置要带服务器ip 方便辨认 跟单人世界识别的」、
 * 「选择了什么矿石 什么食物 那些都要 细节做好知道吗」）：每条记录一张
 * {@link PointCardGrid.PointCard} —— 卡片头是逻辑服务器键（多人 {@code host:port}，
 * 单人 {@code 单人世界 · 存档目录名}），两行信息是「目标矿 · 食物白名单」与
 * 「点位数 · 保存时间」，本服那条带 {@code ✓ 本服}；再往下是读取 / 替换 / 详情 / 删除。
 * 「详情」窗把这条记录的完整内容摊开（含三条传送指令与三个点位坐标），不读取也能看全。</p>
 *
 * <p><b>读写都只认本服（严格 IP 审核）</b>（用户 2026-09-18 先要「如果去别的服务器导入其他服要提示
 * 要复原的话要跟服务器的ip审核一致」，看过后当场裁定「卡死：不一致就不能复原」；
 * 随后写方向也被否掉：「还是能互相保存 根本没有识别服务器ip 拦截」）：
 * 只有记录键与当前服务器 / 单人存档一致时「读取」「替换」才生效；别的服务器 / 存档的记录点了只弹提示
 * （说明它属于哪个服、为什么不能动），不动任何设置与点位 —— 这类记录仍可「详情 / 删除」。
 * 读取 / 替换 / 删除一律二次确认；模块运行中不允许读取（与「运行中不能改点位」同一条口径，提示由模块播报）。</p>
 *
 * <p><b>只列点过保存的</b>（用户 2026-09-18：「没点保存 不要出现在列表里面」）：列表由记录文件枚举而来
 * （{@code MiningConfigRecordStore#list}），没保存过的服务器不会凭空出现一条。</p>
 */
public final class MiningRecordScreen extends PanelScreen implements ConsoleHost {

    /**
     * 卡片信息行里最多列几个白名单食物。
     *
     * <p>卡片信息行是<b>居中直画、不做省略号截断</b>（{@link PointCardGrid.PointCard} 的既有口径），
     * 列太长会压到隔壁卡片上，因此这里把长度控制在两列卡片宽度以内。</p>
     */
    private static final int SUMMARY_FOOD_LIMIT = 2;

    /** 记录卡图标：成书的书＝「已保存的一整套配置」 */
    private static final ItemStack RECORD_ICON = new ItemStack(Items.WRITTEN_BOOK);

    private final AutoMinerModule module;

    /**
     * @param parent 上级屏幕（自动挖矿控制台）——ESC / 返回键回到它
     * @param module 归属模块：记录读写与当前服务器身份都从它取
     */
    public MiningRecordScreen(Screen parent, AutoMinerModule module) {
        super("配置记录", parent);
        this.module = module;
        setSubtitle(() -> "一键保存整套配置（设置 + 三个点位）；按服务器 / 单人存档各存一份");
        rebuild();
    }

    /** 悬停提示登记（{@link com.yiyiaddon.ui.console.ConsoleWidgets} 的行构件只依赖这一项能力，帧末统一绘制） */
    @Override
    public void tip(String text, float x, float y) {
        TooltipLayer.show(text, x, y);
    }

    // ── 内容装配 ──

    /** 记录一变（保存 / 读取 / 替换 / 删除）就整页重建：卡片上的摘要与按钮都是构建时快照 */
    private void rebuild() {
        content().clear();

        content().add(new ButtonStrip(this, List.of(new Ctl(
            new Button("§b一键保存当前配置", this::saveCurrent)
                .disabledWhen(() -> !module.hasRecordScope()),
            "把当前全部设置与三个点位一起存成这台服务器 / 当前存档的记录（本服已有记录时会先确认覆盖）")),
            ButtonStrip.BUTTON_HEIGHT));

        if (!module.hasRecordScope()) {
            content().add(new Note(this, "§8未进入世界 §7——进入服务器或单人存档后才能保存 / 读取 / 替换",
                null, ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        }

        content().add(new Note(this, "§7§l记录列表 §8（只列点过「一键保存当前配置」的）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        List<ConfigRecord> records = module.serverRecords();
        if (records.isEmpty()) {
            content().add(new Note(this, "§8还没有任何记录 §7——点上面「一键保存当前配置」就会多一条"));
            return;
        }
        List<PointCardGrid.PointCard> cards = new ArrayList<>();
        for (ConfigRecord record : records) cards.add(card(record));
        content().add(new PointCardGrid.Grid(cards));
        content().add(new FlushNote("§8读取 / 替换 §7= 只认 §a✓ 本服§7 的记录（IP / 存档一致）　"
            + "§8详情 §7= 看完整内容　§8删除 §7= 只删记录"));
    }

    /** 一条记录一张卡：头 = 服务器身份，两行信息 = 目标/食物 与 点位数/保存时间，四个动作 */
    private PointCardGrid.PointCard card(ConfigRecord record) {
        MiningSettings settings = module.recordSettings(record);
        boolean current = module.sameScopeAs(record);
        String title = "§f" + record.displayName() + (current ? " §a✓ 本服" : "");
        String info1 = settings == null ? "§c记录读不出来" : (targetText(settings) + " §8· §7食物 " + foodText(settings));
        String info2 = "§7点位 §f" + record.pointCount() + "§7/3 §8· §7保存于 §f"
            + AutoMinerModule.recordTimeText(record.savedAt());
        // 非本服那条的「读取 / 替换」压成灰色：不是禁用（点它要弹出「为什么不能动」），
        // 而是提前说明它不是本服的（用户 2026-09-18 要求写方向也按 IP 卡死）
        return new PointCardGrid.PointCard(title, info1, info2, List.of(
            List.of(new Button(current ? "§e读取" : "§7读取", () -> read(record)),
                new Button(current ? "§7替换" : "§8替换", () -> confirmReplace(record))),
            List.of(new Button("§b详情", () -> showDetails(record)),
                new Button("§c删除", () -> confirmDelete(record)))))
            .icon(() -> RECORD_ICON);
    }

    /** 目标显示：三个单值目标三选一（与模块自检同一口径），并标出矿石还是方块、哪个维度 */
    private String targetText(MiningSettings settings) {
        if (!settings.overworldOreTarget.isBlank()) {
            return "§7目标 §f" + MiningTargetPage.itemDisplayName(settings.overworldOreTarget) + "§8主世界";
        }
        if (!settings.netherOreTarget.isBlank()) {
            return "§7目标 §f" + MiningTargetPage.itemDisplayName(settings.netherOreTarget) + "§8下界";
        }
        if (!settings.blockTarget.isBlank()) {
            return "§7目标 §f" + MiningTargetPage.blockDisplayName(settings.blockTarget) + "§8方块";
        }
        return "§7目标 §8未选择";
    }

    /** 食物显示：白名单前几个（超出折成 {@code +N}），空名单说成未选择 */
    private String foodText(MiningSettings settings) {
        List<String> foods = settings.foodWhitelist;
        if (foods.isEmpty()) return "§8未选择";
        List<String> names = new ArrayList<>();
        for (int i = 0; i < Math.min(SUMMARY_FOOD_LIMIT, foods.size()); i++) {
            names.add(MiningTargetPage.itemDisplayName(foods.get(i)));
        }
        String text = "§f" + String.join("§7/§f", names);
        int rest = foods.size() - names.size();
        return rest > 0 ? text + "§8+" + rest : text;
    }

    // ── 动作 ──

    /** 一键保存：本服已有记录时先确认覆盖；没有记录时直接存，保持「一键」 */
    private void saveCurrent() {
        if (!module.hasServerRecord()) {
            module.saveServerRecord();
            rebuild();
            return;
        }
        confirm("覆盖本服记录",
            List.of("§f本服已有一条记录 §7保存于 §f" + module.serverRecordTimeText(),
                "§7继续保存会用当前配置覆盖它。",
                "",
                "§c旧记录内容不可恢复。"),
            "§e§l确认覆盖", () -> {
                module.saveServerRecord();
                rebuild();
            });
    }

    /**
     * 读取：<b>只认本服</b>。记录键与当前服务器 / 单人存档一致才复原；不一致只弹提示、什么也不动
     * （用户 2026-09-18 裁定「卡死：不一致就不能复原（严格 IP 审核）」）。
     */
    private void read(ConfigRecord record) {
        if (minecraft == null) return;
        if (module.sameScopeAs(record)) {
            if (module.restoreRecord(record)) rebuild();
            return;
        }
        boolean singleplayer = WorldIdentity.isSingleplayer();
        List<String> lines = new ArrayList<>();
        lines.add("§f这条记录属于 §f" + record.displayName());
        if (!module.hasRecordScope()) {
            lines.add("§7当前还没进入服务器或单人存档，没有可复原的目标。");
        } else {
            lines.add("§7当前" + (singleplayer ? "存档" : "服务器") + "是 §f"
                + module.currentScopeName() + "§7，与它不一致。");
        }
        lines.add("");
        lines.add("§e复原只认服务器 IP（单人则认存档）一致的记录 §7——");
        lines.add("§7别的服务器 / 存档的点位坐标在本服不适用，所以不允许复原。");
        lines.add("");
        lines.add("§7这条记录仍可用「详情」查看、「替换」覆盖、「删除」清掉。");
        minecraft.gui.setScreen(ConfirmPanelScreen.noticeInPlace("不能复原：不是本服的记录", lines, this));
    }

    /**
     * 替换：<b>只认本服</b>（写方向与读取同一条口径）。别的服务器 / 存档的记录点了只弹提示、
     * 不动那条记录 —— 用户 2026-09-18：「还是能互相保存 根本没有识别服务器ip 拦截」，
     * 即在一个服上点另一条的「替换」就把那个服盖成了这台服的配置。
     */
    private void confirmReplace(ConfigRecord record) {
        if (!module.sameScopeAs(record)) {
            minecraft.gui.setScreen(ConfirmPanelScreen.noticeInPlace("不能替换：不是本服的记录",
                List.of("§f这条记录属于 §f" + record.displayName(),
                    "§7当前" + (WorldIdentity.isSingleplayer() ? "存档" : "服务器") + "是 §f"
                        + module.currentScopeName() + "§7，与它不一致。",
                    "",
                    "§e写入只认本服的记录 §7——",
                    "§7否则就是把这台服的配置盖到别的服务器上（记录会串）。",
                    "",
                    "§7这条记录仍可用「详情」查看、「删除」清掉。"), this));
            return;
        }
        confirm("替换配置记录",
            List.of("§f用当前配置覆盖这条记录：§f" + record.displayName(),
                "§7当前配置 = 全部设置 + 三个点位",
                "§7记录里的旧内容将丢失。",
                "",
                "§c此操作不可恢复。"),
            "§e§l确认替换", () -> {
                module.replaceRecord(record);
                rebuild();
            });
    }

    /** 删除：只删记录，不动当前设置与点位（二次确认） */
    private void confirmDelete(ConfigRecord record) {
        confirm("删除配置记录",
            List.of("§f将删除这条记录：§f" + record.displayName(),
                "§7保存于 §f" + AutoMinerModule.recordTimeText(record.savedAt()),
                "",
                "§c此操作不可恢复；当前设置与点位不受影响。"),
            "§c§l确认删除", () -> {
                module.deleteRecord(record);
                rebuild();
            });
    }

    /** 只读详情窗：把这条记录里的内容摊开（含传送指令与三个点位坐标），不读取也能看全存了什么 */
    private void showDetails(ConfigRecord record) {
        if (minecraft == null) return;
        MiningSettings settings = module.recordSettings(record);
        List<String> lines = new ArrayList<>();
        lines.add("§7识别 §8▸ §f" + record.displayName());
        lines.add("§7保存于 §8▸ §f" + AutoMinerModule.recordTimeText(record.savedAt()));
        lines.add("§7点位 §8▸ §f" + record.pointCount() + " §7/ 3");
        Map<MiningPointType, MiningPoint> points = module.recordPoints(record);
        for (MiningPointType type : MiningPointType.values()) {
            MiningPoint point = points.get(type);
            lines.add("§7" + type.displayName() + " §8▸ §f" + (point == null ? "§8未绑定"
                : point.x() + " " + point.y() + " " + point.z()
                    + " §7" + WorldIdentity.dimensionDisplayName(point.dimension())));
        }
        lines.add("");
        // 整份设置：控制台全部设置页 + 秒破 + 男中音全部调优 + ESP，逐项摊开（用户 2026-09-18：
        // 「保存的信息 我全配置页面的设置包括秒破 男中音设置的」）
        lines.addAll(MiningConfigDigest.lines(settings));
        minecraft.gui.setScreen(ConfirmPanelScreen.noticeInPlace("配置记录详情", lines, this));
    }

    // ── 小工具 ──

    /**
     * 二次确认窗（原地版）：确认或返回后回到本窗，不退出整个界面
     * （默认构造器确认完就 {@code exitToGame()}，会把玩家正看的记录窗一起关掉 —— 用户 2026-09-18 反馈）。
     * 确认动作里若改了记录，调用方自己调 {@link #rebuild()}。
     */
    private void confirm(String title, List<String> lines, String confirmLabel, Runnable action) {
        if (minecraft == null) return;
        minecraft.gui.setScreen(ConfirmPanelScreen.inPlace(title, lines, confirmLabel, action, this));
    }

    /**
     * 贴左对齐的说明行。
     *
     * <p>{@code ConsoleWidgets.Note} 与 {@code TextLine} 都自带 6px 左内边距，画在卡片网格上方时
     * 文字比卡片框的左边多出半格（用户反馈「下面那一行字没对齐 框」）；卡片框左边界就是内容区原点
     * （{@code CardLayout.cardX} 第 0 列返回 originX），所以这里按内容区原点直画。</p>
     */
    private static final class FlushNote implements CompactElement {

        private static final float HEIGHT = 18f;
        private static final float SIZE = 11f;

        /** 中文基线比例：与 {@code TextLine} 同一套（本构件只为对齐左边界，字号与配色取默认值） */
        private static final float BASELINE_RATIO = 1.6f;

        private final String text;

        private FlushNote(String text) {
            this.text = text;
        }

        @Override
        public float height() {
            return HEIGHT;
        }

        @Override
        public void update(float dt) {
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
            MinecraftText.draw(canvas, text, x, y + HEIGHT / 2f + BASELINE_RATIO,
                SIZE, ClickGuiThemeColors.current().primaryText, alpha);
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            return false;
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }
}
