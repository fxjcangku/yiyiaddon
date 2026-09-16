package com.yiyiaddon.ui.console;

/**
 * 控制台窗口宿主：{@link ConsoleWidgets} 的行构件只依赖这一项能力——把悬停提示登记给窗口，
 * 由窗口在帧末统一绘制（本项目通用控件没有 tooltip 原语，面板内自绘）。
 *
 * <p><b>为什么是接口</b>：构件原先把宿主写死为星露谷控制台窗口类，任何别的模块都用不了；
 * 2026-09-16 抽成接口后，实现方只需要把调用转给自己的 tooltip 层，不涉及任何业务类型。</p>
 */
public interface ConsoleHost {

    /** 登记本帧要显示的一条悬停提示（同帧重复登记时以最后一次为准，由窗口统一绘制）。 */
    void tip(String text, float x, float y);
}
