package com.yiyiaddon.core;

import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * yiyiaddon 指令 / 播报统一排版器（全项目唯一入口）。
 *
 * <p><b>为什么必须收敛到一处：</b>过去 {@code .stardew}、{@code .id}、{@code .cunmin} 等指令
 * 各写一套拼接，同一种「设置成功」在不同指令里长成三种样子，玩家看到的是碎片化提示。
 * 本类把「标题 + 字段 + 状态」固化成唯一排版，任何指令只提供数据，不再自己拼颜色代码。</p>
 *
 * <p><b>统一格式（标题行带一次模块前缀，其余行不带）：</b></p>
 * <pre>
 * §f§l[星露谷农场]§r§l§f已设置补水点
 * §7服务器　§8▸ §f生存服
 * §7地址　　§8▸ §bmc.example.com:25565
 * §7维度　　§8▸ §fminecraft:overworld
 * §7坐标　　§8▸ §7X§f123 §7Y§f64 §7Z§f-456
 * §7验证　　§8▸ §a静止水源
 * </pre>
 *
 * <p><b>为什么整块只发一条消息：</b>逐行 {@code sendSystemMessage} 会在聊天框里被服务器插件、
 * 其他模组消息插队割裂，也会刷屏；合并成一条多行消息只带一次前缀，视觉上是一个整体。</p>
 *
 * <p><b>配色（延续规范 5.8 总表）：</b>标签 §7 灰、分隔符 §8▸ 深灰、普通值 §f 白、
 * 关键值 §b 亮青、成功 §a 亮绿、处理中 §e 黄、警告 §6 橙黄、失败 §c 亮红。</p>
 *
 * @author yiyijia
 */
public final class CommandMessageFormatter {

    /** 播报级别：决定「状态」行的文案与颜色 */
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

    /** 标签对齐宽度（全角列数）：标签 + 全角空格补齐到该列数，保证所有字段的值从同一 X 开始 */
    private static final int LABEL_COLUMNS = 5;
    /** 单个全角字符的像素宽度（Minecraft 默认字体下中文 = 9px） */
    private static final int FULL_WIDTH_PX = 9;
    /** 单个半角字符的像素宽度（Minecraft 默认字体下 ASCII 约 4px） */
    private static final int HALF_WIDTH_PX = 4;
    /** 键值分隔符（规范 5.8 唯一合法分隔符） */
    private static final String SEPARATOR = " §8▸ ";

    private final String module;
    private final String title;
    private final List<String> fields = new ArrayList<>();
    private Level level;
    private String statusDetail;

    private CommandMessageFormatter(String module, String title) {
        this.module = module;
        this.title = title;
    }

    /** 开始构建一条结构化播报：模块名 + 标题行 */
    public static CommandMessageFormatter of(String module, String title) {
        return new CommandMessageFormatter(module, title);
    }

    /** 普通字段：值用白色（默认） */
    public CommandMessageFormatter field(String label, String value) {
        if ("维度".equals(label)) return dimension(value);
        if (label.contains("状态") || label.equals("验证")) value = com.yiyiaddon.platform.world.PlayerText.status(value);
        fields.add(line(label, "§f" + safe(value)));
        return this;
    }

    /** 环境信息统一追加，防止各指令把单人世界显示成服务器地址。 */
    public CommandMessageFormatter world() {
        return com.yiyiaddon.platform.world.WorldContextFormatter.appendTo(this);
    }

    /** 已保存点位可传原维度键，技术键单独保留而不是冒充中文名称。 */
    public CommandMessageFormatter dimension(String id) {
        fields.removeIf(line -> line.startsWith("§7维度"));
        var name = com.yiyiaddon.platform.world.WorldContextFormatter.dimensionDisplayName(id);
        if (id != null && !id.contains(":")) name = id;
        fields.add(line("维度", "§f" + name));
        if (id != null && id.contains(":") && !java.util.Set.of("minecraft:overworld", "minecraft:the_nether", "minecraft:the_end")
            .contains(com.yiyiaddon.platform.world.WorldContextFormatter.normalizeDimensionId(id))) {
            fields.add(line("维度 ID", "§b" + com.yiyiaddon.platform.world.WorldContextFormatter.normalizeDimensionId(id)));
        }
        return this;
    }

    /** 关键字段：值用亮青（服务器名 / 地址 / 身份 ID 等需要一眼抓住的信息） */
    public CommandMessageFormatter key(String label, String value) {
        fields.add(line(label, "§b" + safe(value)));
        return this;
    }

    /** 高亮字段：值用亮绿（成功值 / 物品名等） */
    public CommandMessageFormatter highlight(String label, String value) {
        fields.add(line(label, "§a" + safe(value)));
        return this;
    }

    /** 坐标字段：统一走「§7X§f123 §7Y§f64 §7Z§f-456」格式 */
    public CommandMessageFormatter coord(int x, int y, int z) {
        fields.add(line("坐标", "§7X§f" + x + " §7Y§f" + y + " §7Z§f" + z));
        return this;
    }

    /** 原始行（已自带颜色代码，不再加工）：给确有特殊排版需求的地方保留的逃生口 */
    public CommandMessageFormatter raw(String line) {
        if (line != null && !line.isBlank()) fields.add(line);
        return this;
    }

    /** 状态行：统一「§{色}状态 §8▸ §{色}详情/结果」，不写详情时用级别名（成功/警告/失败） */
    public CommandMessageFormatter status(Level level, String detail) {
        this.level = level;
        this.statusDetail = detail;
        return this;
    }

    /** 状态行（无详情） */
    public CommandMessageFormatter status(Level level) {
        return status(level, null);
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

    /** 发送到客户端本地聊天（绝不发服务器公屏） */
    public void send() {
        sendRaw(render());
    }

    /** 单行播报：模块前缀 + 文本（非结构化提示，如错误原因、帮助行） */
    public static void sendLine(String module, String text) {
        sendRaw(ClientChat.prefix(module) + text);
    }

    /**
     * 单行结构化播报文本（带模块前缀，不含状态行）。
     *
     * <p>给「把多行卡片压成一行」用：任务状态播报只留 {@code 正在浇水 ▸ 剩余干盆：8}，
     * 标题自带颜色码（按级别上色），尾部可空。返回的是带前缀的完整文本，直接交给
     * {@link #sendRaw(String)} 或状态播报器即可。</p>
     */
    public static String lineOf(String module, String coloredTitle, String tail) {
        String text = coloredTitle == null ? "" : coloredTitle;
        if (tail != null && !tail.isBlank()) text += " §8▸ §f" + tail;
        return ClientChat.prefix(module) + text;
    }

    /** 单行播报：模块前缀 + 文本 */
    public static void sendRaw(String message) {
        if (message == null) return;
        ClientChat.rawComponent(alignedComponent(message));
    }

    /** 使用实际游戏字体测量标签，再以专用一像素空白补齐；不再估算中文或 ASCII 宽度。 */
    private static Component alignedComponent(String message) {
        var mc = net.minecraft.client.Minecraft.getInstance();
        var result = Component.empty();
        int target = mc.font.width("服务器名称") + 8;
        String[] rows = message.split("\n", -1);
        for (int i = 0; i < rows.length; i++) {
            if (i > 0) result.append("\n");
            String row = rows[i];
            int split = row.indexOf("§8▸");
            if (split < 0) split = row.indexOf("§8▶");
            if (!row.startsWith("§7") || split < 0) { result.append(Component.literal(row)); continue; }
            String label = row.substring(0, split).replaceAll("§[0-9a-fk-or]", "").strip();
            if (mc.font.width(label) > target) label = mc.font.plainSubstrByWidth(label, target - mc.font.width("…")) + "…";
            int pad = Math.max(0, target - mc.font.width(label));
            result.append(Component.literal("§7" + label));
            result.append(Component.literal("\uE000".repeat(pad)).withStyle(net.minecraft.network.chat.Style.EMPTY
                .withFont(new net.minecraft.network.chat.FontDescription.Resource(net.minecraft.resources.Identifier.fromNamespaceAndPath("yiyiaddon", "spacing")))));
            result.append(Component.literal("§8▶ " + row.substring(split + 3).stripLeading()));
        }
        return result;
    }

    /** 「标签 + 分隔符 + 值」单行渲染，标签按全角列对齐 */
    public static String line(String label, String value) {
        return "§7" + pad(label) + SEPARATOR + value;
    }

    /**
     * 标签补齐到固定全角列宽。
     *
     * <p>Minecraft 默认字体里中文 9px、ASCII 4px，直接按字符数补空格会错位；
     * 这里按像素宽度换算需要补多少全角空格，保证同一块内所有值从同一 X 开始。</p>
     */
    private static String pad(String label) {
        String text = label == null ? "" : label;
        int width = pixelWidth(text);
        int target = LABEL_COLUMNS * FULL_WIDTH_PX;
        if (width >= target) return text;
        int remainder = target - width;
        int fullCount = remainder / FULL_WIDTH_PX;
        int halfCount = (remainder % FULL_WIDTH_PX) / HALF_WIDTH_PX;
        return text + "　".repeat(fullCount) + " ".repeat(halfCount);
    }

    /** 估算字符串像素宽度（全角 9px / 半角 4px） */
    private static int pixelWidth(String text) {
        int width = 0;
        for (int i = 0; i < text.length(); i++) {
            width += isFullWidth(text.charAt(i)) ? FULL_WIDTH_PX : HALF_WIDTH_PX;
        }
        return width;
    }

    /** 是否为全角 / CJK 字符（覆盖中日韩、全角标点、全角字母数字） */
    private static boolean isFullWidth(char c) {
        return (c >= 0x1100 && c <= 0x115F)
            || (c >= 0x2E80 && c <= 0xA4CF)
            || (c >= 0xAC00 && c <= 0xD7A3)
            || (c >= 0xF900 && c <= 0xFAFF)
            || (c >= 0xFE30 && c <= 0xFE6F)
            || (c >= 0xFF00 && c <= 0xFF60)
            || (c >= 0xFFE0 && c <= 0xFFE6);
    }

    /** 空值兜底：显示层绝不出现 null */
    private static String safe(String value) {
        return value == null || value.isBlank() ? "无" : value;
    }
}
