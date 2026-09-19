package com.yiyiaddon.platform.world;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.service.resourcepack.ResourcePackCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.locale.Language;

/** 玩家可见的世界信息唯一入口；显示名不参与任何存档键或服务器隔离计算。 */
public final class WorldContextFormatter {
    /** 环境以真实客户端世界为准，不能拿上一次连接留下的服务器数据判断。 */
    public enum Environment { MAIN_MENU, SINGLEPLAYER, MULTIPLAYER }

    private WorldContextFormatter() { }

    /** 单人同样拥有 ClientLevel，优先检查世界再区分连接类型。 */
    public static Environment environment() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return Environment.MAIN_MENU;
        return mc.hasSingleplayerServer() ? Environment.SINGLEPLAYER : Environment.MULTIPLAYER;
    }

    /** 展示名称取服务器列表，不能拿地址或内部键冒充名称。 */
    public static String serverName() {
        if (environment() == Environment.MAIN_MENU) return "未连接";
        if (environment() == Environment.SINGLEPLAYER) return "单人世界";
        var data = Minecraft.getInstance().getCurrentServer();
        return data == null || data.name == null || data.name.isBlank() ? "未命名服务器" : data.name;
    }

    /** 复用已有规范化地址逻辑，保留端口及 IPv6，不依赖资源是否已检测。 */
    public static String serverAddress() {
        if (environment() == Environment.MAIN_MENU) return "未连接";
        if (environment() == Environment.SINGLEPLAYER) return "不适用";
        String key = ResourcePackCache.currentServerKey();
        return key == null || key.isBlank() ? "暂不可用" : key;
    }

    /** 使用集成服务器实际存档标题，仅用于显示，不替换持久化目录身份。 */
    public static String singleplayerWorldName() {
        var server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null) return "未进入存档";
        String name = server.getWorldData().getLevelName();
        return name == null || name.isBlank() || name.startsWith("FAILED_") ? "存档名称暂不可用" : name;
    }

    /** 直接读取当前 ClientLevel，单人世界也返回真实维度键。 */
    public static String dimensionId() {
        var level = Minecraft.getInstance().level;
        return level == null ? null : level.dimension().identifier().toString();
    }

    /** 当前维度的中文主显示。 */
    public static String dimensionDisplayName() { return dimensionDisplayName(dimensionId()); }

    /** 历史点位也使用同一套名称规则；只接受已有中文翻译，不猜测自定义维度含义。 */
    public static String dimensionDisplayName(String id) {
        if (id == null || id.isBlank()) return "无";
        String normalized = normalizeDimensionId(id);
        switch (normalized) {
            case "minecraft:overworld": return "主世界";
            case "minecraft:the_nether": return "下界";
            case "minecraft:the_end": return "末地";
        }
        String key = normalized.replace(':', '.').replace('/', '.');
        for (String prefix : new String[]{"dimension.", "dimension_name."}) {
            if (!Language.getInstance().has(prefix + key)) continue;
            String name = I18n.get(prefix + key);
            if (name.codePoints().anyMatch(c -> c >= 0x3400 && c <= 0x9fff)) return name;
        }
        return "自定义维度";
    }

    /** 兼容旧存档里的 ResourceKey 包装，不用 contains 把自定义名称误认成原版维度。 */
    public static String normalizeDimensionId(String id) {
        if (id == null) return "";
        int slash = id.indexOf(" / ");
        return slash >= 0 && id.endsWith("]") ? id.substring(slash + 3, id.length() - 1) : id;
    }

    /** 短行展示保留自定义维度技术键，完整信息卡使用单独维度 ID 行。 */
    public static String dimensionSummary(String id) {
        String name = dimensionDisplayName(id);
        return "自定义维度".equals(name) ? name + "（维度 ID：" + normalizeDimensionId(id) + "）" : name;
    }

    /** 添加当前环境字段；主界面、单人和多人各自采用适当标签。 */
    public static CommandMessageFormatter appendTo(CommandMessageFormatter card) {
        switch (environment()) {
            case MAIN_MENU -> card.field("世界", "未进入世界").field("服务器", "未连接");
            case SINGLEPLAYER -> card.field("世界", "单人世界").field("存档", singleplayerWorldName());
            case MULTIPLAYER -> card.key("服务器名称", serverName()).key("服务器地址", serverAddress());
        }
        return card.dimension(dimensionId());
    }
}
