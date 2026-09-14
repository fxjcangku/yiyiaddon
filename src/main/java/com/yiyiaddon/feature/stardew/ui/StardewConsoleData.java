package com.yiyiaddon.feature.stardew.ui;

import java.util.List;

/**
 * 星露谷农场控制台一次渲染所需的全部只读数据。
 *
 * <p><b>为什么用不可变快照而不是让界面直接读模块：</b>界面只负责画，取数逻辑（配额换算、
 * 库存计数、播报日志）全部留在模块侧。这样控制台不会绕过任何闸门去猜状态，
 * 也不会因为界面停留在某一页而持有会变的引用。</p>
 *
 * <p>这里只放「控制台自己要排版」的部分：选择器、点位、后勤阈值这些都由各自的
 * Setting 控件直接渲染（见 {@link StardewSettingsRenderer}），不在这份快照里复制一份。</p>
 */
public record StardewConsoleData(
    /** 资源包阶段，如「就绪」/「检测中」 */
    String resource,
    boolean resourceReady,
    /** 季节显示名，如「春季」/「未知」 */
    String season,
    /** 当前任务中文名，如「正在浇水」/「空闲」 */
    String task,
    /** 当前任务细节（已排版，可为空） */
    String taskDetail,
    /** 已选作物汇总，如「番茄、玉米」 */
    String cropsSummary,
    List<CropRow> crops,
    List<String> logs
) {

    /** 一种作物的配额与背包库存。 */
    public record CropRow(String name, int quota, int seeds, int produce) {
    }

    public static StardewConsoleData empty() {
        return new StardewConsoleData("未就绪", false, "未知", "未启动", "", "未选择",
            List.of(), List.of());
    }
}
