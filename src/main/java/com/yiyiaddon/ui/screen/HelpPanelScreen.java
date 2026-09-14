package com.yiyiaddon.ui.screen;

import com.yiyiaddon.ui.component.TextLine;
import net.minecraft.client.gui.screens.Screen;

/**
 * 通用「使用说明」独立窗口（对应旧项目 {@code ui/HelpScreen}）。
 *
 * <p>旧项目里每个模块面板都有一个说明入口：{@code IdConfigModule} 是「§e使用说明」，
 * 其余模块是「§e查看使用说明」，点击后打开的都是同一个基类，窗口标题为
 * <b>{@code <模块名> - 使用说明}</b>。本类逐字复刻该结构，模块只需提供内容行。</p>
 *
 * <p><b>内容格式（与旧项目 {@link #buildHelpContent} 的输出逐字一致）</b>：</p>
 *
 * <pre>
 * §8┏━━…（51 个 ━）…━━┓
 * §8┃ §3§l&gt; §b§l使用说明（38 空格）§8┃
 * §8┗━━…（51 个 ━）…━━┛
 * ────────────────（空行渲染为分隔线）
 * §3[§b#§3] §f使用说明章节标题
 *   正文行…
 * </pre>
 *
 * <p>底部固定一条分隔线与满宽「关闭」按钮；关闭行为与旧项目一致，直接回到游戏
 * （{@link PanelScreen#exitToGame()}），而不是返回上级窗口。</p>
 */
public class HelpPanelScreen extends PanelScreen {

    /** 标题后缀：旧项目 {@code module.title + " - 使用说明"}。 */
    public static final String TITLE_SUFFIX = " - 使用说明";

    /** 底部关闭按钮文字：旧项目原文。 */
    public static final String CLOSE_LABEL = "关闭";

    /** 边框横线重复次数：与旧项目 HelpScreen 完全一致。 */
    private static final int FRAME_RULE = 51;
    /** 标题行补齐到右边框所需空格数：与旧项目 HelpScreen 完全一致。 */
    private static final int TITLE_PADDING = 38;

    private static final String FRAME_TOP = "§8┏" + "━".repeat(FRAME_RULE) + "┓";
    private static final String FRAME_MIDDLE =
            "§8┃ §3§l> §b§l使用说明" + " ".repeat(TITLE_PADDING) + "§8┃";
    private static final String FRAME_BOTTOM = "§8┗" + "━".repeat(FRAME_RULE) + "┛";

    /**
     * @param moduleName 模块中文名（与模块面板标题一致）
     * @param helpContent 说明内容行；空字符串渲染为一条分隔线
     * @param parent      上级屏幕（本窗口关闭后直接回游戏，parent 仅用于 GUI 栈）
     */
    public HelpPanelScreen(String moduleName, String[] helpContent, Screen parent) {
        super(moduleName + TITLE_SUFFIX, parent);
        exitToGame();
        buildContent(helpContent);
    }

    private void buildContent(String[] helpContent) {
        if (helpContent != null) {
            for (String line : helpContent) {
                if (line == null || line.isEmpty()) {
                    addDivider();
                } else {
                    content().add(new TextLine(line));
                }
            }
        }
        addDivider();
        addButton(CLOSE_LABEL, this::requestClose);
    }

    /**
     * 生成标准格式的说明内容，输出与旧项目 {@code HelpScreen.buildHelpContent} 逐字一致。
     *
     * <p>总行数 = 3（标题框）+ Σ(1 空行 + 1 章节标题 + 章节行数)。</p>
     */
    public static String[] buildHelpContent(HelpSection... sections) {
        HelpSection[] list = sections == null ? new HelpSection[0] : sections;
        int totalLines = 3;
        for (HelpSection section : list) {
            totalLines += 2 + section.lines().length;
        }

        String[] content = new String[totalLines];
        int index = 0;
        content[index++] = FRAME_TOP;
        content[index++] = FRAME_MIDDLE;
        content[index++] = FRAME_BOTTOM;
        for (HelpSection section : list) {
            content[index++] = "";
            content[index++] = "§3[§b#§3] §f" + section.title();
            for (String line : section.lines()) {
                content[index++] = line;
            }
        }
        return content;
    }

    /** 说明章节：标题 + 若干正文行。 */
    public record HelpSection(String title, String... lines) {
    }
}
