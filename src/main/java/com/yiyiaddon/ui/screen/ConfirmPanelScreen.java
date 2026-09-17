package com.yiyiaddon.ui.screen;

import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 通用二次确认窗口：正文逐行原样渲染，底部「确认 / 取消」等宽按钮。
 *
 * <p>用于不可逆操作（清空数据、删除记录）。正文支持 Minecraft 颜色码，因此旧项目的多行危险
 * 提示可以逐行照搬。确认按钮默认使用危险变体，取消按钮关闭窗口。</p>
 */
public final class ConfirmPanelScreen extends PanelScreen {

    private final List<String> lines;
    private final String confirmLabel;
    private final Runnable onConfirm;
    /** 只读提示窗：没有可选动作，底部只有一个「知道了」 */
    private final boolean notice;
    /** 「打开设置」的目标窗口（自检失败面板用）；为空则不摆这个按钮 */
    private final Supplier<Screen> settingsTarget;
    /** 点过「打开设置」后关窗动画要跳的目标；为空则按原语义回游戏 */
    private Screen nextScreen;

    /**
     * @param windowTitle  窗口标题
     * @param lines        正文行，支持颜色码，逐行渲染
     * @param confirmLabel 确认按钮文案
     * @param onConfirm    确认后的动作；窗口随后自动关闭
     * @param parent       上级屏幕
     */
    public ConfirmPanelScreen(String windowTitle, List<String> lines, String confirmLabel,
                              Runnable onConfirm, Screen parent) {
        this(windowTitle, lines, confirmLabel, onConfirm, parent, false, null, true);
    }

    /**
     * 原地二次确认：确认或返回后<b>回到上级窗口</b>，不退出整个界面（{@link #ConfirmPanelScreen} 的默认语义是回游戏）。
     *
     * <p><b>为什么要它</b>（用户 2026-09-18：「点击删除不要关闭我的ui 是保持在这个页面」）：
     * 配置记录窗里点「删除」→ 确认 → 应当回到记录窗看到列表刷新；默认构造器走的是
     * {@code exitToGame()}，确认完直接关掉整个界面回到游戏，把玩家刚在看的页面一起关了。</p>
     *
     * @param parent 上级窗口（确认后回到它；它自己负责刷新内容）
     */
    public static ConfirmPanelScreen inPlace(String windowTitle, List<String> lines, String confirmLabel,
                                             Runnable onConfirm, Screen parent) {
        return new ConfirmPanelScreen(windowTitle, lines, confirmLabel, onConfirm, parent, false, null, false);
    }

    /**
     * 原地只读提示窗：点掉后回到上级窗口，不退出整个界面（{@link #notice} 的原地版）。
     */
    public static ConfirmPanelScreen noticeInPlace(String windowTitle, List<String> lines, Screen parent) {
        return new ConfirmPanelScreen(windowTitle, lines, "§a§l知道了", null, parent, true, null, false);
    }

    private ConfirmPanelScreen(String windowTitle, List<String> lines, String confirmLabel,
                               Runnable onConfirm, Screen parent, boolean notice,
                               Supplier<Screen> settingsTarget, boolean closeToGame) {
        super(windowTitle, parent);
        if (closeToGame) exitToGame();
        this.lines = lines == null ? List.of() : List.copyOf(lines);
        this.confirmLabel = confirmLabel == null ? "确认" : confirmLabel;
        this.onConfirm = onConfirm;
        this.notice = notice;
        this.settingsTarget = settingsTarget;
        buildContent();
    }

    /**
     * 只读提示窗：屏幕中间的面板 + 一个「知道了」按钮。
     *
     * <p>用于「必须让玩家看见、但没有可选项」的结论（例如启动自检未通过）：聊天里那一份容易被
     * 后续消息刷走，这里用项目现成的面板窗与按钮再摆一份到屏幕中间，点掉即回游戏。</p>
     */
    public static ConfirmPanelScreen notice(String windowTitle, List<String> lines, Screen parent) {
        return new ConfirmPanelScreen(windowTitle, lines, "§a§l知道了", null, parent, true, null, true);
    }

    /**
     * 只读提示窗（按条目排版）：结论行 + 逐条提示。
     *
     * <p><b>排版与配色统一在这里做</b>，各模块只提供标题、结论行与条目：</p>
     * <ol>
     *   <li>每条提示**红色加粗**（{@code §c§l}），让玩家一眼看到问题在哪；条目里自带的颜色码先剥掉，
     *       否则原文的绿 / 白会把红色盖掉（文字本身一字不改）；</li>
     *   <li>条目里带「▸」的（结论 + 出路）拆成两行，避免一行顶出面板宽度。</li>
     * </ol>
     */
    public static ConfirmPanelScreen notice(String windowTitle, String headline, List<String> items, Screen parent) {
        return notice(windowTitle, headline, items, parent, null);
    }

    /**
     * 只读提示窗（按条目排版）＋「打开设置」。
     *
     * <p><b>为什么要有这个按钮</b>（实机反馈：点「知道了」之后还得自己再进模块中心、找到那个模块、
     * 再点进设置）：自检这类面板指向的就是「某个模块的配置」，出路就在那个设置页里。点它等于
     * 「关掉面板、直接落到该模块的设置页」，少两三层点击。目标窗口由调用方给（模块中心那一层才知道
     * 模块对应哪个设置页），本组件只负责按钮与跳转时机。</p>
     *
     * @param settingsTarget 生成目标设置窗口的工厂；为空则不摆「打开设置」按钮
     */
    public static ConfirmPanelScreen notice(String windowTitle, String headline, List<String> items,
                                           Screen parent, Supplier<Screen> settingsTarget) {
        List<String> lines = new ArrayList<>();
        lines.add(headline);
        lines.add("");
        if (items != null) {
            for (String item : items) {
                if (item == null || item.isBlank()) continue;
                String text = item.strip();
                int cut = text.indexOf(" ▸ ");
                if (cut < 0) {
                    lines.add("§8· §c§l" + plain(text));
                    continue;
                }
                lines.add("§8· §c§l" + plain(text.substring(0, cut)));
                lines.add("§8\u3000§c§l" + plain(text.substring(cut + 3)));
            }
        }
        return new ConfirmPanelScreen(windowTitle, lines, "§a§l知道了", null, parent, true, settingsTarget, true);
    }

    /** 剥掉 §x 颜色码：弹窗里统一红色加粗，原文配色不参与叠加（文字内容不动） */
    private static String plain(String text) {
        StringBuilder out = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\u00a7' && i + 1 < text.length()) {
                i++;
                continue;
            }
            out.append(c);
        }
        return out.toString();
    }

    private void buildContent() {
        for (String line : lines) {
            content().add(new TextLine(line));
        }
        addGap();
        if (notice) {
            // 只读结论：没有任何危险动作，不摆「确认 / 取消」那一对
            if (settingsTarget == null) {
                addButtons(new Button(confirmLabel, this::requestClose));
                return;
            }
            addButtons(
                    new Button("§b§l打开设置", this::openSettings),
                    new Button(confirmLabel, this::requestClose));
            return;
        }
        addDivider();
        addGap();
        addButtons(
                new Button(confirmLabel, this::confirm).danger(),
                new Button("§7取消", this::requestClose));
    }

    /** 关掉面板、直接落到目标设置页（跳转发生在关闭动画结束时，见 {@link #closing()}） */
    private void openSettings() {
        nextScreen = settingsTarget.get();
        requestClose();
    }

    @Override
    protected void closing() {
        if (nextScreen != null && this.minecraft != null) {
            this.minecraft.setScreen(nextScreen);
            return;
        }
        super.closing();
    }

    private void confirm() {
        Runnable action = onConfirm;
        requestClose();
        if (action != null) action.run();
    }
}
