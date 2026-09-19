package com.yiyiaddon.feature.autologin.service;

/**
 * 播报里的高亮包装（逐字搬旧基类 {@code YiyiaddonModule} 的同名方法，一个颜色码都没改）。
 *
 * <p>本项目没有旧框架的模块基类，几处流程播报都要用同几个包装，收在这里一处；
 * 与自动骨粉在模块内自带两个包装的做法同理，只是本模块用到的种类多，单独放一个类
 * 免得模块与服务各写一份（第 169 条）。</p>
 */
public final class AutoLoginStyle {

    private AutoLoginStyle() {
    }

    /** 文本高亮（绿色粗体）—— 旧 {@code highlightText} */
    public static String text(String value) {
        return "§a§l" + value + "§r§f§l";
    }

    /** 功能 / 模式高亮（亮青色粗体）—— 旧 {@code highlightFunction} */
    public static String function(String value) {
        return "§b§l" + value + "§r§f§l";
    }

    /** 数值 / 阈值高亮（黄色粗体）—— 旧 {@code highlightNumber} */
    public static String number(String value) {
        return "§e§l" + value + "§r§f§l";
    }

    /** 服务器名高亮（金色粗体）—— 旧 {@code highlightServer} */
    public static String server(String value) {
        return "§6§l" + value + "§r§f§l";
    }

    /** 地点高亮（紫粉色粗体）—— 旧 {@code highlightLocation} */
    public static String location(String value) {
        return "§d§l" + value + "§r§f§l";
    }

    /** 指令高亮（黄色粗体）—— 旧 {@code highlightCommand} */
    public static String command(String value) {
        return "§e§l" + value + "§r§f§l";
    }
}
