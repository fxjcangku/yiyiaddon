package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.feature.mining.ui.MiningRegistry;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleHeaderBar;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 自动挖矿控制台「自用模式」页（用户 2026-09-20 需求）。
 *
 * <p><b>本页只装「挖够就自己去卖掉」这一条链的设置</b>：卖什么、攒多少组触发、走哪几个菜单、
 * 找哪个 NPC、每步等多久。除本页外，自用模式与普通模式完全共用同一套设置与流程（目标选择、秒破、
 * 连锁、丢弃、补给、死亡处理、Baritone 调优都在各自页签里，<b>一项都不复制</b>）。</p>
 *
 * <p><b>模式开关在顶栏</b>（{@link ModeSwitch}，挂在模块开关左侧）：用户 2026-09-20 口径
 * 「在启用开关旁边的加一个自用模式的开关」；页内不再放第二份开关（同一开关两处出现必然互相对旧值，
 * 与「设置项只出现一次」的项目口径冲突）。</p>
 *
 * <p><b>与其它页同源</b>：文本行是 {@link SettingTextBox}，数值行是带加减的
 * {@link SettingNumberBox}（用户明确要「带加减选择框」），选择器行照「目标选择」页那一套
 * （{@code 点击选择 + 当前值 + ↻}），行构件与行高全部来自 {@link ConsoleWidgets}，
 * 每个可改行都带行内 ↺（第 214 条：空态禁用）。</p>
 *
 * <p><b>出售物品留空 = 跟随目标选择页</b>（用户 2026-09-20：「至于出售物品那些自动联动目标选择器
 * 我选什么就显示出售什么 这样子就不会错了」）：留空时卖的就是目标页选中的那一个目标，天然与正在挖的矿
 * 同源；服务器另收别的方块（如圆石）时点「点击选择」手选一个覆盖它，↻ 清空即回到跟随。</p>
 */
public final class MiningPersonalPage {

    /** 文本类设置的输入框宽度与长度上限（与「传送指令」页同一口径） */
    private static final float TEXT_BOX_WIDTH = 220f;
    private static final int TEXT_MAX_LENGTH = 512;

    /** 出售物品的状态列宽：与行内文本框同档，文字更长时由 {@link SettingText} 自己截断 */
    private static final float SELL_ITEM_STATE_WIDTH = 210f;

    /** 选择器行的「点击选择」按钮（逐字与「目标选择」页一致） */
    private static final String SELECT_LABEL = "点击选择";

    /** NPC 坐标取值域：与方块坐标同一档（-3000 万 ~ 3000 万） */
    private static final double COORD_MIN = -30000000;
    private static final double COORD_MAX = 30000000;

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final MiningSettings DEFAULTS = new MiningSettings();

    private final MiningConsoleScreen owner;
    private final AutoMinerModule module;

    public MiningPersonalPage(MiningConsoleScreen owner, AutoMinerModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        MiningSettings settings = module.settings();

        stack.add(new Note(owner, "§7§l自用模式 §8（顶栏「自用模式」开关打开后生效）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, "§8打开后：不要求绑定矿物箱与挂机修复点，挖满触发组数（或背包先满）就去卖；", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, "§8其余设置与流程照旧（目标选择、秒破、连锁、丢弃、补给都不变）。", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, "§8「出售物品」默认跟随目标选择页：你在目标页选什么就卖什么，不会卖错；", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        stack.add(sellItemRow());

        stack.add(new ConsoleRow(owner, () -> "触发组数",
            "背包里「出售物品」攒到多少组就出发去卖（1~36 组）。背包先满也直接去卖", null,
            List.of(new Ctl(intBox(1, 36, () -> settings.personalSellStacks,
                    value -> settings.personalSellStacks = value)),
                resetInt("触发组数", () -> DEFAULTS.personalSellStacks,
                    value -> settings.personalSellStacks = value))));

        stack.add(new Note(owner, "§7§l出售流程 §8（全程静默：不弹界面、不抢鼠标）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        stack.add(new ConsoleRow(owner, () -> "出售流程指令",
            "打开流程菜单的指令（默认 /cd）：回主城与跨服传送都在这个菜单里", null,
            List.of(new Ctl(textBox(() -> settings.personalSellCommand,
                    value -> settings.personalSellCommand = value)),
                resetText("出售流程指令", () -> DEFAULTS.personalSellCommand,
                    value -> settings.personalSellCommand = value))));

        stack.add(new ConsoleRow(owner, () -> "回城点击关键词",
            "流程菜单里点这个槽回主城大厅（浮空提示里那个名字，颜色码与空格自动忽略）", null,
            List.of(new Ctl(textBox(() -> settings.personalSellCityKeyword,
                    value -> settings.personalSellCityKeyword = value)),
                resetText("回城点击关键词", () -> DEFAULTS.personalSellCityKeyword,
                    value -> settings.personalSellCityKeyword = value))));

        stack.add(new ConsoleRow(owner, () -> "跨服点击关键词",
            "卖完回程时，流程菜单里打开子服列表的那个槽（默认「跨服传送」，图标是指南针）", null,
            List.of(new Ctl(textBox(() -> settings.personalSellCrossServerKeyword,
                    value -> settings.personalSellCrossServerKeyword = value)),
                resetText("跨服点击关键词", () -> DEFAULTS.personalSellCrossServerKeyword,
                    value -> settings.personalSellCrossServerKeyword = value))));

        for (String server : MiningSettings.PERSONAL_RETURN_SERVERS) {
            stack.add(returnServerRow(server));
        }
        stack.add(new Note(owner, "§8两个服只能勾一个：勾另一个会自动切过去（勾是「回哪个服继续挂机」）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        stack.add(new ConsoleRow(owner, () -> "出售数量关键词",
            "出售界面里「设为全部数量」那个槽的关键词（默认「全部」）", null,
            List.of(new Ctl(textBox(() -> settings.personalSellPickKeyword,
                    value -> settings.personalSellPickKeyword = value)),
                resetText("出售数量关键词", () -> DEFAULTS.personalSellPickKeyword,
                    value -> settings.personalSellPickKeyword = value))));

        stack.add(new ConsoleRow(owner, () -> "确认出售关键词",
            "提交出售那个槽的关键词（默认「确认出售」）", null,
            List.of(new Ctl(textBox(() -> settings.personalSellConfirmKeyword,
                    value -> settings.personalSellConfirmKeyword = value)),
                resetText("确认出售关键词", () -> DEFAULTS.personalSellConfirmKeyword,
                    value -> settings.personalSellConfirmKeyword = value))));

        stack.add(new Note(owner, "§7§l收购 NPC", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        stack.add(new ConsoleRow(owner, () -> "NPC 坐标 X",
            "收购 NPC 的固定坐标（回主城后按它寻路，到了再用名字核对）", null,
            List.of(new Ctl(coordBox(() -> settings.personalSellNpcX,
                    value -> settings.personalSellNpcX = value)),
                resetInt("NPC 坐标 X", () -> DEFAULTS.personalSellNpcX,
                    value -> settings.personalSellNpcX = value))));

        stack.add(new ConsoleRow(owner, () -> "NPC 坐标 Y",
            "收购 NPC 的固定坐标（Y 只作核对，寻路按地面走）", null,
            List.of(new Ctl(coordBox(() -> settings.personalSellNpcY,
                    value -> settings.personalSellNpcY = value)),
                resetInt("NPC 坐标 Y", () -> DEFAULTS.personalSellNpcY,
                    value -> settings.personalSellNpcY = value))));

        stack.add(new ConsoleRow(owner, () -> "NPC 坐标 Z",
            "收购 NPC 的固定坐标", null,
            List.of(new Ctl(coordBox(() -> settings.personalSellNpcZ,
                    value -> settings.personalSellNpcZ = value)),
                resetInt("NPC 坐标 Z", () -> DEFAULTS.personalSellNpcZ,
                    value -> settings.personalSellNpcZ = value))));

        stack.add(new ConsoleRow(owner, () -> "NPC 名字关键词",
            "NPC 是插件伪装成的玩家实体，只能按显示名找（默认「黑市商人」，颜色码与空格自动忽略）", null,
            List.of(new Ctl(textBox(() -> settings.personalSellNpcName,
                    value -> settings.personalSellNpcName = value)),
                resetText("NPC 名字关键词", () -> DEFAULTS.personalSellNpcName,
                    value -> settings.personalSellNpcName = value))));

        stack.add(new Note(owner, "§7§l卡顿与重试 §8（服务器卡了就原地重试，不带着矿乱走）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        stack.add(new ConsoleRow(owner, () -> "单步超时",
            "每一步最长等多少秒（1~60）。超时就重试这一步，不会跳过去", null,
            List.of(new Ctl(intBox(1, 60, () -> settings.personalSellStepTimeout,
                    value -> settings.personalSellStepTimeout = value)),
                resetInt("单步超时", () -> DEFAULTS.personalSellStepTimeout,
                    value -> settings.personalSellStepTimeout = value))));

        stack.add(new ConsoleRow(owner, () -> "单步重试次数",
            "单步超时后重试几次（0~10）。用完仍失败就停机播报，不会继续往下点", null,
            List.of(new Ctl(intBox(0, 10, () -> settings.personalSellRetries,
                    value -> settings.personalSellRetries = value)),
                resetInt("单步重试次数", () -> DEFAULTS.personalSellRetries,
                    value -> settings.personalSellRetries = value))));

        stack.add(new Note(owner, "§8出售顺序：点出售物品 → 点「全部」→ 点「确认出售」，循环到背包清零", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, "§8然后回勾选的那个服，落地直接继续 RTP 挖矿；死亡返回指令填 /back 即可", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
    }

    // ── 行构件 ──

    /**
     * 出售物品行：<b>留空 = 跟随目标选择页</b>（用户 2026-09-20：「自动联动目标选择器 我选什么就
     * 显示出售什么 这样子就不会错了」），需要卖服务器另收的方块（如圆石）时点「点击选择」自己挑一个。
     *
     * <p>形态与「目标选择」页的选择器行一致：名称 + 说明 …… [点击选择] [当前值] [↻]；↻ 清空 = 回到跟随。</p>
     */
    private ConsoleRow sellItemRow() {
        return new ConsoleRow(owner, () -> "出售物品",
            "留空 = 跟随目标选择页（挖什么就卖什么）；服务器另收别的方块（如圆石）时点「点击选择」自己挑一个",
            null,
            List.of(new Ctl(new Button(SELECT_LABEL, this::openSellItemSelector)),
                new Ctl(new SettingText(this::sellItemStatus, SELL_ITEM_STATE_WIDTH).alignLeft()),
                ConsoleWidgets.resetCtl(this::clearSellItemTarget, "出售物品")));
    }

    /** 出售物品的当前值：跟随目标时给出跟随到的<b>产物</b>名，手选时回显那一件 */
    private String sellItemStatus() {
        MiningSettings settings = module.settings();
        if (!settings.personalSellFollowsTarget()) return settings.personalSellTarget;
        String name = module.getSellItemDisplayName();
        return name.isEmpty() ? "跟随目标（目标未选）" : "跟随目标 · " + name;
    }

    /**
     * 打开出售物品选择器：候选 = 全部物品（剔除空气），单值准入（要换先移除，与目标页一致）。
     *
     * <p><b>为什么用物品列表而不是方块列表</b>：时运模式卖的是掉落物（「钻石」不是方块，方块表里根本没有），
     * 而方块物品（圆石、钻石矿石、原木）本来就在物品列表里；方块表里那些没有物品形态的（水 / 岩浆 / 火 /
     * 耕地）选了也进不了背包、卖不掉。</p>
     */
    private void openSellItemSelector() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        List<SelectorScreen.Entry> entries =
            MiningRegistry.filter(MiningRegistry.itemEntries(), key -> !MiningRegistry.isAirItem(key));
        client.setScreen(new SelectorScreen("出售物品", client.screen, entries,
            this::selectedSellItem, this::setSellItemTarget, key -> setSellItemTarget(null))
            .addGuard(this::sellItemGuardReason));
    }

    /** 已选手选值（单值 → 至多一项；留空 = 跟随，返回空列表） */
    private List<String> selectedSellItem() {
        String current = module.settings().personalSellTarget;
        return current == null || current.isBlank() ? List.of() : List.of(current);
    }

    /** 写入手选值；{@code null} / 非物品 = 清空（清空即回到跟随目标） */
    private void setSellItemTarget(String itemId) {
        module.settings().personalSellTarget =
            itemId == null || MiningRegistry.itemOf(itemId) == null ? "" : itemId;
        module.persistSettings();
    }

    /**
     * 加入准入：① 单值 —— 已手选着别的物品时先移除再换；② 纠错 —— 手选的这件在当前目标 + 采集模式下
     * 根本掉不出来（用户 2026-09-20：「我想卖的方块是圆石，我选了精准采集模式，那不是掉的是石头吗」）
     * 就直接拒收，理由原样进那个渐入渐出的提示框。
     */
    private String sellItemGuardReason(String key) {
        String current = module.settings().personalSellTarget;
        if (current != null && !current.isBlank() && !current.equals(key)) {
            return "§e出售物品只能选一个 §8▸ 先移除「§f"
                + MiningRegistry.itemDisplayName(current) + "§8」";
        }
        return module.sellItemMismatchReason(key);
    }

    /** ↻ 清空手选值：回到「跟随目标选择页」 */
    private void clearSellItemTarget() {
        module.settings().personalSellTarget = "";
        module.persistSettings();
        owner.reload();
    }

    /**
     * 回程目标服行（每个子服一行，勾选框形态 —— 用户明确要「一个框 打勾的那种」）。
     *
     * <p>两个勾选框互斥：勾上就把设置切到该服；取消勾选不生效（必须有一个选中项），
     * 但会重建本页把视觉状态拉回来，不会出现「两个都空着」的假象。</p>
     */
    private ConsoleRow returnServerRow(String server) {
        MiningSettings settings = module.settings();
        SettingToggle toggle = new SettingToggle(() -> server.equals(settings.personalSellReturnServer), value -> {
            if (!value) {
                owner.reload();
                return;
            }
            settings.personalSellReturnServer = server;
            module.persistSettings();
            owner.reload();
        });
        return new ConsoleRow(owner, () -> "回程目标服 · " + server,
            "勾上表示卖完回到这个服，落地后直接继续 RTP 挖矿（两个只能勾一个）", null,
            List.of(new Ctl(toggle),
                ConsoleWidgets.resetCtl(() -> {
                    settings.personalSellReturnServer = DEFAULTS.personalSellReturnServer;
                    module.persistSettings();
                    owner.reload();
                }, "回程目标服 · " + server)));
    }

    /** 带加减按钮的整数框（步长 1，改完立即落盘；用户口径：组数要「带加减选择框」） */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, IntConsumer setter) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                module.persistSettings();
            });
    }

    /** 坐标框（取值域按方块坐标档，步长 1） */
    private SettingNumberBox coordBox(Supplier<Integer> getter, IntConsumer setter) {
        return intBox((int) COORD_MIN, (int) COORD_MAX, getter, setter);
    }

    /** 单行文本设置 */
    private SettingTextBox textBox(Supplier<String> getter, java.util.function.Consumer<String> setter) {
        return new SettingTextBox(getter, value -> {
            setter.accept(value);
            module.persistSettings();
        }, TEXT_MAX_LENGTH).width(TEXT_BOX_WIDTH);
    }

    private Ctl resetInt(String label, Supplier<Integer> defaultValue, IntConsumer setter) {
        return ConsoleWidgets.resetCtl(() -> {
            setter.accept(defaultValue.get());
            module.persistSettings();
            owner.reload();
        }, label);
    }

    private Ctl resetText(String label, Supplier<String> defaultValue,
                          java.util.function.Consumer<String> setter) {
        return ConsoleWidgets.resetCtl(() -> {
            setter.accept(defaultValue.get());
            module.persistSettings();
            owner.reload();
        }, label);
    }

    // ── 顶栏开关 ──

    /**
     * 顶栏「自用模式」开关（用户 2026-09-20：「在启用开关旁边的加一个自用模式的开关」）。
     *
     * <p>形态 = 文字 + 开关，画在模块开关左侧（{@link ConsoleHeaderBar.Extra}）。切换后重建本页：
     * 点位的两行、自检项、状态条上的格子都随模式变（不重建就会留下上一个模式的读数）。</p>
     */
    public static final class ModeSwitch implements ConsoleHeaderBar.Extra {

        private static final float LABEL_SIZE = 12f;
        private static final float LABEL_GAP = 8f;

        private final AutoMinerModule module;
        private final Runnable afterToggle;
        private final SettingToggle toggle;
        private final String label = "自用模式";

        /** @param afterToggle 切换后要做的事（控制台传 {@code owner::reload}） */
        public ModeSwitch(AutoMinerModule module, Runnable afterToggle) {
            this.module = module;
            this.afterToggle = afterToggle;
            this.toggle = new SettingToggle(() -> module.settings().personalMode, value -> {
                module.settings().personalMode = value;
                module.persistSettings();
                afterToggle.run();
            });
        }

        @Override
        public float width() {
            return MinecraftText.measure(label, LABEL_SIZE, true) + LABEL_GAP + toggle.getWidth();
        }

        @Override
        public float height() {
            return ConsoleHeaderBar.HEIGHT;
        }

        @Override
        public void update(float dt) {
            toggle.update(dt);
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            float centerY = y + ConsoleHeaderBar.HEIGHT / 2f;
            float toggleX = x + width - toggle.getWidth();
            float toggleY = centerY - toggle.getHeight() / 2f;
            toggle.hover(mouseX, mouseY, toggleX, toggleY, toggle.getWidth());
            toggle.draw(canvas, toggleX, toggleY, alpha);

            float labelWidth = MinecraftText.measure(label, LABEL_SIZE, true);
            MinecraftText.draw(canvas, label, toggleX - LABEL_GAP - labelWidth,
                CardLayout.baseline(centerY, LABEL_SIZE), LABEL_SIZE,
                ClickGuiThemeColors.current().primaryText, alpha, true);
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            float centerY = y + ConsoleHeaderBar.HEIGHT / 2f;
            return toggle.onClick(mx, my, x + width - toggle.getWidth(),
                centerY - toggle.getHeight() / 2f, button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }
}
