package com.yiyiaddon.feature.autologin.model;

/**
 * 认证指令规则（逐字照旧项目 {@code autologin/model/AuthRule}）。
 * 存储从聊天消息中解析出的命令模板，例如：
 *   /l {password}
 *   /reg {password} {password}
 */
public final class AuthRule {

    public enum Type { LOGIN, REGISTER }

    /** LOGIN 或 REGISTER */
    public final Type type;
    /** 原始模板，如 "/l {password}" */
    public final String template;
    /** 识别来源（服务器消息原文，用于调试） */
    public final String sourceMessage;

    public AuthRule(Type type, String template, String sourceMessage) {
        this.type          = type;
        this.template      = template;
        this.sourceMessage = sourceMessage;
    }

    /**
     * 将模板中的 {password} 替换为实际密码，生成可直接发送的指令（不含斜杠）。
     */
    public String buildCommand(String password) {
        // 去掉开头的 /，sendCommand 不需要斜杠
        String cmd = template.startsWith("/") ? template.substring(1) : template;
        return cmd.replace("{password}", password);
    }

    @Override
    public String toString() {
        return "[" + type + "] " + template;
    }
}
