package com.yiyiaddon.feature.autologin.service;

import com.yiyiaddon.feature.autologin.model.AuthRule;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 聊天消息分析器（逐字照旧项目 {@code autologin/service/ChatAnalyzer}）。
 * 从服务器聊天消息中识别登录/注册提示，并提取指令模板。
 */
public final class ChatAnalyzer {

    // 去除 Minecraft 颜色代码（§x）
    private static final Pattern COLOR_CODE = Pattern.compile("§[0-9a-fk-orA-FK-OR]");

    // 登录关键词
    private static final String[] LOGIN_KEYWORDS = {
        "login", "password", "log in",
        "密码", "登录", "登入", "登陆",
        "请输入密码", "请登录", "请登入",
        "已注册", "输入密码", "验证密码",
        "authme", "auth", "/login", "/l "
    };
    // 注册关键词
    private static final String[] REGISTER_KEYWORDS = {
        "register", "reg", "sign up", "signup",
        "注册", "请注册", "首次登录", "新用户",
        "未注册", "还没有注册", "尚未注册",
        "/register", "/reg"
    };
    private static final String[] LOGIN_SUCCESS_KEYWORDS = {
        "登录成功", "成功登录", "登入成功", "成功登入", "登陆成功", "成功登陆",
        "已登录", "已登入", "已经登录", "已经登入",
        "login successful", "successfully logged in", "logged in successfully",
        "you are now logged in", "already logged in", "already authenticated"
    };
    private static final String[] LOGIN_FAILURE_KEYWORDS = {
        "登录失败", "登入失败", "登陆失败", "密码错误", "密码不正确",
        "login failed", "wrong password", "incorrect password", "not logged in"
    };

    // 只提取命令名，参数不解析（服务器帮助文本中的多余词汇会污染计数）
    private static final Pattern CMD_PATTERN = Pattern.compile(
        "/([\\w\u4e00-\u9fa5]+)"
    );

    private ChatAnalyzer() {}

    /** 清除颜色代码并 trim */
    public static String stripColors(String raw) {
        return COLOR_CODE.matcher(raw).replaceAll("").trim();
    }

    /**
     * 尝试从一条聊天消息中解析出 AuthRule。
     * 返回 null 表示本条消息不含有效规则。
     */
    public static AuthRule analyze(String rawMessage) {
        String clean = stripColors(rawMessage).toLowerCase();

        // 先检测注册（优先级高于登录，因为有些服务器注册消息也含 password）
        if (containsAny(clean, REGISTER_KEYWORDS)) {
            String template = extractTemplate(rawMessage, AuthRule.Type.REGISTER);
            if (template != null) {
                return new AuthRule(AuthRule.Type.REGISTER, template, rawMessage);
            }
        }

        if (containsAny(clean, LOGIN_KEYWORDS)) {
            String template = extractTemplate(rawMessage, AuthRule.Type.LOGIN);
            if (template != null) {
                return new AuthRule(AuthRule.Type.LOGIN, template, rawMessage);
            }
        }

        return null;
    }

    public static boolean isLoginSuccess(String rawMessage) {
        String clean = stripColors(rawMessage).toLowerCase();
        return !containsAny(clean, LOGIN_FAILURE_KEYWORDS)
            && containsAny(clean, LOGIN_SUCCESS_KEYWORDS);
    }

    /**
     * 从消息里提取指令模板。
     * 只提取命令名，密码数量固定（登录1个，注册2个），不受服务器帮助文本影响。
     * 支持中文命令名，例如 "/注册" → "/注册 {password} {password}"。
     */
    private static String extractTemplate(String rawMessage, AuthRule.Type type) {
        Matcher m = CMD_PATTERN.matcher(rawMessage);
        List<String[]> candidates = new ArrayList<>();
        while (m.find()) {
            String cmdName  = m.group(1).toLowerCase();
            String fullMatch = m.group(0);
            candidates.add(new String[]{ cmdName, fullMatch });
        }

        // 按命令名筛选：登录优先 l/login/log/auth，注册优先 reg/register
        String[] preferred = type == AuthRule.Type.LOGIN
            ? new String[]{ "l", "login", "log", "auth", "登录", "登入", "登陆" }
            : new String[]{ "reg", "register", "注册" };

        // 先在候选里找优先命令
        for (String pref : preferred) {
            for (String[] cand : candidates) {
                if (cand[0].equals(pref)) {
                    return buildTemplate(cand[1], type);
                }
            }
        }

        // 找不到优先命令：如果消息里有任意 /cmd 并且关键词匹配就用第一个
        if (!candidates.isEmpty()) {
            return buildTemplate(candidates.get(0)[1], type);
        }

        return null;
    }

    /**
     * 将命令名规范化为模板。
     * 密码个数固定：登录=1个，注册=2个。
     * 不从服务器消息推断参数数量，避免帮助文本里的多余词污染。
     */
    private static String buildTemplate(String cmdStr, AuthRule.Type type) {
        cmdStr = COLOR_CODE.matcher(cmdStr).replaceAll("").trim();
        // cmdStr 现在只是命令名，如 "/reg" 或 "/login"
        String cmd = cmdStr.split("\\s+")[0];
        if (cmd.isEmpty()) return null;
        return type == AuthRule.Type.REGISTER
            ? cmd + " {password} {password}"
            : cmd + " {password}";
    }

    private static boolean containsAny(String text, String[] keywords) {
        for (String kw : keywords) {
            if (text.contains(kw)) return true;
        }
        return false;
    }
}
