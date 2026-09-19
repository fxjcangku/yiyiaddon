package com.yiyiaddon.feature.teleport.model;

/**
 * 传送模式枚举：三个模式相互独立，各自由独立按键/指令触发。
 */
public enum TeleportMode {
    /** 回到头顶真正的露天地表（洞穴脱身） */
    GROUND("TP地面"),
    /** 穿过准心瞄准的墙体，落在墙后安全点 */
    WALL("TP穿墙"),
    /** 传送到配置坐标或指令指定的坐标 */
    COORD("TP坐标");

    private final String cn;

    TeleportMode(String cn) {
        this.cn = cn;
    }

    @Override
    public String toString() {
        return cn;
    }
}
