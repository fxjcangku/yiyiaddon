package com.yiyiaddon.ui.screen;

import com.yiyiaddon.ui.component.TextLine;
import net.minecraft.client.gui.screens.Screen;

import java.util.Arrays;

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
 * <p><b>关闭 / 返回行为（用户 2026-09-17 口径）</b>：不再直接回到游戏，而是<b>回到打开它的那个界面</b>
 * ——左上返回箭头与底部「关闭」都走 {@link PanelScreen#requestClose()} → {@link SkiaScreen#closing()}
 * → {@code setScreen(parent)}。用户原话「剩下的模块点击查看按钮之后，点返回会直接关掉 gui，
 * 我想返回还是查看按钮的那个界面」。旧项目该窗口是 {@code setScreen(null)}（连同整个 GUI 关掉），
 * 这里的改动已登记为差异；调用方一律传模块页 / 控制台作为 parent。</p>
 *
 * <p>底部固定一条分隔线与满宽「关闭」按钮（按钮文字仍为旧项目原文）。</p>
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
     * @param parent      上级屏幕（关闭 / 返回时回到它，见类注释的关闭行为说明）
     */
    public HelpPanelScreen(String moduleName, String[] helpContent, Screen parent) {
        super(moduleName + TITLE_SUFFIX, parent);
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

    /** 窗口外框占的行数：{@link #buildHelpContent} 的前三行（上框 / 标题行 / 下框） */
    public static final int FRAME_LINES = 3;

    /**
     * 内嵌到模块页用的说明行：去掉窗口外框三行，章节标题与正文逐字保留。
     *
     * <p><b>为什么去掉外框</b>：{@code §8┏━…┓ / §8┃ 使用说明 ┃ / §8┗━…┛} 是这个独立窗口的窗框，
     * 页面里已经有自己的面板与标题栏，再画一圈 51 字宽的框只会串行。
     * 章节标题（{@code §3[§b#§3] §f…}）与全部正文一字未改。</p>
     *
     * <p>用法（模块页把说明铺在「打开控制台」下方，超出时由模块页自身的滚动条上下查看）：</p>
     * <pre>
     * private static final String[] HELP_LINES =
     *     HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));
     * …
     * for (String line : HELP_LINES) addCore(new TextLine(line));
     * </pre>
     */
    public static String[] inlineContent(String[] panelContent) {
        if (panelContent == null || panelContent.length <= FRAME_LINES) return new String[0];
        return Arrays.copyOfRange(panelContent, FRAME_LINES, panelContent.length);
    }
}
