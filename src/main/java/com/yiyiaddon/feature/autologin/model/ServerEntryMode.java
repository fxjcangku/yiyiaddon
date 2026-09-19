package com.yiyiaddon.feature.autologin.model;

/**
 * 服务器进入方式枚举（逐字照旧项目 {@code autologin/config/ServerEntryMode}）。
 * 决定自动登录模块采用哪种进服策略：直连、菜单传送、子服网络或自用服定制路线。
 *
 * <p>落盘按枚举名（{@link #name()}）读写，与旧框架 {@code EnumSetting} 的存档口径一致；
 * 显示文案取 {@link #title()}。</p>
 */
public enum ServerEntryMode {
    DIRECT("直接进入"),                    // 直接连接当前服务器
    MENU_TRANSFER("菜单传送"),             // 通过服务器菜单切换
    SUBSERVER_NETWORK("子服网络"),         // 识别并进入目标子服
    LEYUAN_CUSTOM("自用配置");             // 自用配置专属定制路线

    private final String title;

    ServerEntryMode(String title) {
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
        ServerEntryMode[] values = values();
        String[] labels = new String[values.length];
        for (int i = 0; i < values.length; i++) labels[i] = values[i].title;
        return labels;
    }

    /** 按枚举名读回；非法名字返回 {@code null}（调用方回落默认值，不猜） */
    public static ServerEntryMode ofName(String name) {
        if (name == null) return null;
        for (ServerEntryMode mode : values()) {
            if (mode.name().equals(name)) return mode;
        }
        return null;
    }
}
