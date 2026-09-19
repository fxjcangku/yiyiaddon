package com.yiyiaddon.feature.autologin.model;

/**
 * 欢迎界面入口方式枚举（逐字照旧项目 {@code autologin/config/LeyuanWelcomeEntryMode}）。
 * 决定进入主城后采用哪种欢迎入口识别策略。
 */
public enum LeyuanWelcomeEntryMode {
    LEYUAN_CITY("自用入口"),         // 自用城欢迎界面入口
    BOOK_DIRECT("书本直达主城");       // 通过书本菜单直达主城

    private final String title;

    LeyuanWelcomeEntryMode(String title) {
        this.title = title;
    }

    /** 显示文案（旧 {@code toString()} 的返回值） */
    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    /** 全部显示文案，顺序 = 枚举序（控制台分段控件的候选） */
    public static String[] labels() {
        LeyuanWelcomeEntryMode[] values = values();
        String[] labels = new String[values.length];
        for (int i = 0; i < values.length; i++) labels[i] = values[i].title;
        return labels;
    }

    /** 按枚举名读回；非法名字返回 {@code null}（调用方回落默认值，不猜） */
    public static LeyuanWelcomeEntryMode ofName(String name) {
        if (name == null) return null;
        for (LeyuanWelcomeEntryMode mode : values()) {
            if (mode.name().equals(name)) return mode;
        }
        return null;
    }
}
