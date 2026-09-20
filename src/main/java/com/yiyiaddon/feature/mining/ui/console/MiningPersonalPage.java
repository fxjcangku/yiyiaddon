package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.ui.MiningConsoleScreen;
import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleHeaderBar;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;

import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 自动挖矿控制台「自用模式」页（用户 2026-09-20 需求，2026-09-21 重排）。
 *
 * <p><b>自用模式下本页是这个模式的唯一落点</b>（用户 2026-09-21：「我要每个模式互不干扰」
 * 「开了自用模式隐藏 目标选择跟传送指令」）：控制台按模式换页签集合 —— 打开自用模式只留本页，
 * 「目标选择」与「传送指令」两页隐藏，而它们里面自用模式还要用的内容<b>原样挂到本页</b>
 * （直接调那两页的装配方法，一行都不复制）；关掉自用模式反过来隐藏本页
 * （见 {@code MiningConsoleScreen.Tab#visible}）。</p>
 *
 * <p><b>本页装什么（顺序即设置顺序，2026-09-21 重排）</b>：① 「目标选择」页整页（采集模式、三个单值目标、
 * 物品管理三个名单）；② 出售（出售物品只读回显 + 触发组数）；③ 「传送指令」页里自用模式要用的行
 * （前往挖矿指令、RTP 两项、前往补给指令、死亡返回指令、传送等待时长、RTP 冷却、传送失败重试）；
 * ④ 出售流程本身（流程指令 → 回城关键词 → 收购 NPC 坐标与名字 → 出售数量 → 确认出售 → 跨服关键词 →
 * 回程目标服）；⑤ 卡顿与重试（单步超时 / 单步重试次数）。</p>
 *
 * <p><b>为什么是这个顺序</b>（用户 2026-09-21：「重新整理自用模式里面的配置排序 方便操作填写人性化
 * 现在乱七八糟」）：按「先定挖什么、再定卖什么、再定去哪挖、最后定回城怎么卖」的因果链排，
 * 且 ④ 内部逐行就是运行时点屏幕的顺序（开菜单 → 点回城 → 走到 NPC → 点物品 → 点「全部」→
 * 点「确认出售」→ 点跨服 → 回勾选的服），与帮助页 [1]~[4] 的步骤逐条对上。
 * 装卸货绑定的行本模式一概不出现（「触发条件」页在自用模式下也已隐藏那两行）。</p>
 *
 * <p><b>出售物品不单独设项</b>（用户 2026-09-21：「选择出售方块为什么可以选择？我说同步选择器」）：
 * 它就是目标三选一在当前采集模式下的产物（选石头没开精准采集，卖的就是圆石），只读回显，
 * 「填错」这种状态不存在。</p>
 *
 * <p><b>模式开关在顶栏</b>（{@link ModeSwitch}，挂在模块开关右侧）：用户 2026-09-20 口径
 * 「在启用开关旁边的加一个自用模式的开关」；页内不再放第二份开关（同一开关两处出现必然互相对旧值，
 * 与「设置项只出现一次」的项目口径冲突）。</p>
 *
 * <p><b>与其它页同源</b>：文本行是 {@link SettingTextBox}，数值行是带加减的
 * {@link SettingNumberBox}（用户明确要「带加减选择框」），选择器行与采集模式行、传送行全部来自
 * 各自原页的装配方法，行构件与行高来自 {@link ConsoleWidgets}，每个可改行都带行内 ↺（第 214 条：空态禁用）。</p>
 */
public final class MiningPersonalPage {

    /** 文本类设置的输入框宽度与长度上限（与「传送指令」页同一口径） */
    private static final float TEXT_BOX_WIDTH = 220f;
    private static final int TEXT_MAX_LENGTH = 512;

    /** 出售物品的状态列宽：与行内文本框同档，文字更长时由 {@link SettingText} 自己截断 */
    private static final float SELL_ITEM_STATE_WIDTH = 210f;

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
        stack.add(new Note(owner, "§8挖满触发组数（或背包先满）就回城卖掉再回子服接着挖；只绑食物箱，"
            + "矿物箱与挂机修复点不需要", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, "§8本页是这个模式的全部设置：目标三选一、挖矿与补给的传送、出售链。"
            + "该模式用不到的页签已隐藏", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        // ── ① 挖什么：目标整页（采集模式 + 三个单值目标 + 物品管理三个名单）──
        // 摆最前：下面「出售」组的产物就是这一组推导出来的，先把挖什么定了，后面的行才有意义
        stack.add(new Note(owner, "§7§l目标 §8（自用模式下「目标选择」页隐藏，这里就是那一页）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        new MiningTargetPage(owner, module).build(stack);

        // ── ② 卖什么、攒到多少走 ──
        stack.add(new Note(owner, "§7§l出售 §8（上面选的产物攒够触发组数、或背包先满，就去卖）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(sellItemRow());
        stack.add(new ConsoleRow(owner, () -> "触发组数",
            "背包里「出售物品」攒到多少组就出发去卖（1~36 组）。背包先满也直接去卖", null,
            List.of(new Ctl(intBox(1, 36, () -> settings.personalSellStacks,
                    value -> settings.personalSellStacks = value)),
                resetInt("触发组数", () -> DEFAULTS.personalSellStacks,
                    value -> settings.personalSellStacks = value))));

        // ── ③ 去哪挖、去哪补、死了怎么回来 ──
        stack.add(new Note(owner, "§7§l传送 §8（自用模式下「传送指令」页隐藏；"
            + "卸货与挂机修复那两行本模式用不到，已略过）", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        // 整页挂过来：把传送那几条按原序装进本页
        new MiningTeleportPage(owner, module).build(stack, true);

        // ── ④ 回城怎么卖：按运行顺序排（与帮助页 [1]~[4] 的步骤逐条对上）──
        stack.add(new Note(owner, "§7§l出售流程 §8（全程静默：不弹界面、不抢鼠标；下面的顺序就是运行时点击顺序）", null,
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
        stack.add(new Note(owner, "§8出售顺序：点出售物品 → 点「全部」→ 点「确认出售」，循环到背包清零", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));
        stack.add(new Note(owner, "§8然后回勾选的那个服，落地直接继续 RTP 挖矿；死亡返回指令填 /back 即可", null,
            ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE));

        // ── ⑤ 卡顿与重试：传送与出售两步共用的兜底参数，摆最后 ──
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
    }

    // ── 行构件 ──

    /**
     * 出售物品行：<b>图标 + 名字，只读</b>（用户 2026-09-21：「出售物品下面不用带选择器懂？我目标选择器
     * 选择了什么 出售物品那一栏就出现选择的方块或者加图标名字就行了 而不是刷新按钮跟目标选择器」）。
     *
     * <p><b>图标挂在值那一格里</b>（用户 2026-09-21：「出售物品图标移动到右边的物品名字前面」）：
     * 早先挂在行首（{@code ConsoleRow#icon}），图标与名字分处一行两端，中间隔着标签与整段空白，
     * 「这个图标是什么」要横着跨半行去对。名字本身就是值的一部分，图标必须贴着名字
     * —— 于是改用 {@link SettingText#icon}，图标画在名字正左方，与名字同属右侧那一格。</p>
     *
     * <p>值恒等于目标三选一在当前采集模式下的产物（{@code AutoMinerModule#sellItemFilter()}），
     * 与背包计数、菜单点选、播报、状态条读的是同一个取值，所以行上看到什么就卖什么；
     * 推导不出来时（没选目标 / 产物没有物品形态）这里直接显示纠错那句话。</p>
     */
    private ConsoleRow sellItemRow() {
        return new ConsoleRow(owner, () -> "出售物品",
            "不用选：你在上面选什么目标，这里就是它挖出来的产物（选石头、没开精准采集，卖的就是圆石）"
                + " —— 背包数的是它、菜单点的是它、播报念的也是它",
            null, List.of(new Ctl(new SettingText(this::sellItemStatus, SELL_ITEM_STATE_WIDTH)
                .icon(module::sellItemIcon))));
    }

    /** 出售物品的当前值：产物名（不是目标本身时标出「XX掉落」，如「圆石 (石头掉落)」）；推导不出来时显示纠错那句话 */
    private String sellItemStatus() {
        String notice = module.sellItemNotice();
        if (notice != null) return notice;
        String name = module.getSellItemDisplayName();
        String source = module.sellItemSourceNote();
        return source == null ? name : name + " §8(" + source + ")";
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
     * <p>形态 = 文字 + 开关，画在整排<b>最右端</b>（模块开关右侧，用户 2026-09-21 定的方向）。
     * 切换后重建本页：点位的两行、自检项、状态条上的格子都随模式变（不重建就会留下上一个模式的读数）。</p>
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
            if (button != 0) return false;
            float centerY = y + ConsoleHeaderBar.HEIGHT / 2f;
            float toggleX = x + width - toggle.getWidth();
            float toggleY = centerY - toggle.getHeight() / 2f;
            // 必须先判悬停：SettingToggle.onClick 不做命中判断（调用方负责，见 SettingModule），
            // 落到开关框外就返回 true 会把右边的模块开关与快捷键徽章一起吞掉（2026-09-21 实机踩过：
            // 点「启用」实际拨了自用模式，徽章也录不进键）
            if (mx < toggleX || mx > toggleX + toggle.getWidth()
                || my < toggleY || my > toggleY + toggle.getHeight()) {
                return false;
            }
            return toggle.onClick(mx, my, toggleX, toggleY, button);
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }
}
