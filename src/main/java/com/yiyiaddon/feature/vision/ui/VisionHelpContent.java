package com.yiyiaddon.feature.vision.ui;

import com.yiyiaddon.feature.vision.config.VisionTexts;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.screen.HelpPanelScreen;

/**
 * 透视模块的使用说明：**逐行引用 {@link VisionTexts} 里已有的界面文案组装，不新写描述句**。
 *
 * <p><b>为什么是引用而不是抄一遍</b>：说明里的每一条都必须与控制台上看到的那一行完全一致
 * （第 212 条：说明指向的页签名 / 设置名必须与实现一致）。这里直接拼 {@link VisionTexts} 常量，
 * 改一处即两处同步，不可能出现「说明写 A、界面写 B」。</p>
 *
 * <p>章节标头 {@code §3[§b#§3] §f标题} 与窗框由 {@link HelpPanelScreen#buildHelpContent} 统一生成；
 * 行内沿用本项目既有的 {@code §8├─} / {@code §8└─} 树形符号与 {@code §8-} 分隔符
 * （与 {@code WaterHelpContent} 同一套排版）。</p>
 */
public final class VisionHelpContent {

    /** 中间项前缀（本项目说明正文既有符号） */
    private static final String BRANCH = "  §8├─ ";
    /** 末项前缀 */
    private static final String LAST = "  §8└─ ";

    /** 四章节（模块页内嵌说明的唯一数据源，禁止在页面里再拼一份） */
    public static final HelpPanelScreen.HelpSection[] SECTIONS = build();

    private VisionHelpContent() {
    }

    private static HelpPanelScreen.HelpSection[] build() {
        return new HelpPanelScreen.HelpSection[] {
            new HelpPanelScreen.HelpSection("方块透视",
                line(BRANCH, VisionTexts.NAME_BLOCK_ENABLED, VisionTexts.DESC_BLOCK_ENABLED),
                line(BRANCH, VisionTexts.NAME_BLOCK_RANGE, VisionTexts.DESC_BLOCK_RANGE),
                line(BRANCH, VisionTexts.NAME_BLOCK_BOX, VisionTexts.DESC_BLOCK_BOX),
                line(BRANCH, VisionTexts.NAME_BLOCK_TRACER, VisionTexts.DESC_BLOCK_TRACER),
                line(BRANCH, VisionTexts.NAME_BLOCK_SHAPE, VisionTexts.DESC_BLOCK_SHAPE),
                line(LAST, VisionTexts.NAME_BLOCK_COLOR, colorHint(VisionTexts.DESC_BLOCK_COLOR))
            ),
            new HelpPanelScreen.HelpSection("实体透视",
                line(BRANCH, VisionTexts.NAME_ENTITY_ENABLED, VisionTexts.DESC_ENTITY_ENABLED),
                line(BRANCH, VisionTexts.NAME_ENTITY_RANGE, VisionTexts.DESC_ENTITY_RANGE),
                line(BRANCH, VisionTexts.NAME_ENTITY_BOX, VisionTexts.DESC_ENTITY_BOX),
                line(BRANCH, VisionTexts.NAME_ENTITY_TRACER, VisionTexts.DESC_ENTITY_TRACER),
                line(BRANCH, VisionTexts.NAME_ENTITY_SHAPE, VisionTexts.DESC_ENTITY_SHAPE),
                line(LAST, VisionTexts.NAME_ENTITY_COLOR, colorHint(VisionTexts.DESC_ENTITY_COLOR))
            ),
            new HelpPanelScreen.HelpSection("目标选择器",
                line(BRANCH, VisionTexts.NAME_BLOCK_TARGETS, VisionTexts.DESC_BLOCK_TARGETS),
                line(BRANCH, VisionTexts.NAME_ENTITY_TARGETS, VisionTexts.DESC_ENTITY_TARGETS),
                "  §8├─ §f" + VisionTexts.SELECT_NAME + " §8- §7" + VisionTexts.SELECT_HINT_BLOCK,
                line(LAST, VisionTexts.CLEAR_NAME, VisionTexts.CLEAR_HINT)
            ),
            new HelpPanelScreen.HelpSection("透视控制台",
                "  §8├─ §b打开控制台",
                "  §8├─ §7刷新 §8- §7按最新设置值重排本页",
                "  §8└─ §7关闭"
            )
        };
    }

    /** 一行说明：{@code §f名称 §8- §7描述}（颜色行末尾补可见提示，与界面行尾提示逐字同源） */
    private static String line(String prefix, String name, String description) {
        return prefix + "§f" + name + " §8- §7" + description;
    }

    /** 颜色行的描述末尾接上界面上那条可见提示（{@link ConsoleWidgets#COMMENT_COLOR} 逐字） */
    private static String colorHint(String description) {
        return description + " §8· " + ConsoleWidgets.COMMENT_COLOR;
    }
}
