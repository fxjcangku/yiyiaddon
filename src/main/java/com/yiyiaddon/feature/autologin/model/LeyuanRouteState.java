package com.yiyiaddon.feature.autologin.model;

/**
 * 自用服回服路线的阶段状态（逐字照旧项目 {@code autologin/fsm/LeyuanRouteState}）。
 *
 * <p>旧项目里这条路线以服务器名命名，本项目按用户 2026-09-18 口径全树去壳，
 * 只改注释里的称呼，枚举常量本身一个未动（落盘与判定都按常量名走）。</p>
 */
public enum LeyuanRouteState {
    IDLE,
    OPEN_LOGIN_MENU,
    CLICK_LOGIN_SURVIVAL,
    WAIT_WELCOME,
    SCAN_WELCOME,
    WAIT_MAIN_CITY,
    OPEN_AFK_MENU,
    CLICK_RETURN_MAIN_CITY_HALL,
    WAIT_MAIN_CITY_HALL,
    OPEN_MAIN_CITY_HALL_MENU,
    OPEN_CITY_MENU,
    CLICK_WORLD_TRANSFER,
    CLICK_SURVIVAL_FIRST,
    CLICK_SURVIVAL_SECOND,
    CLICK_TARGET_SERVER,
    WAIT_TARGET,
    COMPLETE,
    FAILED
}
