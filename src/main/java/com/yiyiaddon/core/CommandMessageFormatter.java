package com.yiyiaddon.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 指令回执统一排版器（移植自旧项目 {@code com.example.addon.core.CommandMessageFormatter}）。
 *
 * <p><b>属于用户交互资产，输出格式禁止改动。</b>旧项目全部指令回执都经此器输出，格式固定为：</p>
 * <ul>
 *   <li>标题行：模块前缀 + 白色标题（整块只带一次前缀）；</li>
 *   <li>字段行：{@code §7标签 §8▸ §f值}，标签按像素宽度补齐到 5 全角列；</li>
 *   <li>状态行：{@code §7状态 §8▸ §{级别色}详情}；</li>
 *   <li>整块合并为一条多行消息发送，禁止逐行发送（会被插队割裂且刷屏）。</li>
 * </ul>
 *
 * <p>空值兜底固定为 {@code 无}：显示层绝不出现 null。</p>
 */
public final class CommandMessageFormatter {

    /** 键值分隔符：只能是 {@code §8▸ } */
    private static final String SEPARATOR = " §8▸ ";

    /** 标签对齐列数 */
    private static final int LABEL_COLUMNS = 5;

    /** 全角字符宽度（像素） */
    private static final int FULL_WIDTH_PX = 9;

    /** 半角字符宽度（像素） */
    private static final int HALF_WIDTH_PX = 4;

    /** 状态级别：文字与颜色固定，与旧项目逐字一致 */
    public enum Level {
        /** 成功 / 完成 / 已设置 */
        SUCCESS("成功", "§a"),
        /** 处理中 / 进行中 */
        RUNNING("处理中", "§e"),
        /** 警告 / 降级 / 部分成功 */
        WARNING("警告", "§6"),
        /** 失败 / 中止 */
        FAILURE("失败", "§c"),
        /** 纯信息，无褒贬 */
        INFO("信息", "§7");

        private final String label;
        private final String color;

        Level(String label, String color) {
            this.label = label;
            this.color = color;
        }

        public String label() {
            return label;
        }

        public String color() {
            return color;
        }
    }

    private final String module;
    private final String title;
    private final List<String> fields = new ArrayList<>();
    private Level level;
    private String statusDetail;

    private CommandMessageFormatter(String module, String title) {
        this.module = module;
        this.title = title;
    }

    public static CommandMessageFormatter of(String module, String title) {
        return new CommandMessageFormatter(module, title);
    }

    /** 追加一行字段 */
    public CommandMessageFormatter field(String label, String value) {
        fields.add(line(label, value));
        return this;
    }

    /** 设置状态行 */
    public CommandMessageFormatter status(Level level, String detail) {
        this.level = level;
        this.statusDetail = detail;
        return this;
    }

    /** 渲染为最终文本（标题行带模块前缀，其余行裸文本） */
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append(ClientChat.prefix(module)).append("§f").append(safe(title));
        for (String field : fields) sb.append('\n').append(field);
        if (level != null) {
            String text = statusDetail == null || statusDetail.isBlank() ? level.label() : statusDetail;
            sb.append('\n').append(line("状态", level.color() + text));
        }
        return sb.toString();
    }

    /** 渲染并作为一条多行消息发送 */
    public void send() {
        ClientChat.raw(render());
    }

    /** 单行字段：{@code §7标签 §8▸ §f值}；标签按像素补齐 */
    public static String line(String label, String value) {
        return "§7" + pad(label) + SEPARATOR + value;
    }

    /** 单行播报：模块前缀 + 文本（非结构化提示，如错误原因、帮助行） */
    public static void sendLine(String module, String text) {
        ClientChat.send(module, text);
    }

    /** 按像素宽度补齐标签，保证同一块内所有值从同一 X 起 */
    private static String pad(String label) {
        String text = label == null ? "" : label;
        StringBuilder builder = new StringBuilder(text);
        int width = 0;
        for (int i = 0; i < text.length(); i++) {
            width += isHalfWidth(text.charAt(i)) ? HALF_WIDTH_PX : FULL_WIDTH_PX;
        }
        int target = LABEL_COLUMNS * FULL_WIDTH_PX;
        while (width < target) {
            builder.append(' ');
            width += HALF_WIDTH_PX;
        }
        return builder.toString();
    }

    private static boolean isHalfWidth(char c) {
        return c < 0x100;
    }

    /** 空值兜底：显示层绝不出现 null */
    private static String safe(String value) {
        return value == null || value.isBlank() ? "无" : value;
    }
}
