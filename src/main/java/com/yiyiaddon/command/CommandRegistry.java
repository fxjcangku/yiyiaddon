package com.yiyiaddon.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 客户端指令注册表：名称与别名的唯一解析点。
 *
 * <p>名称与别名统一按小写索引，查找不区分大小写；注册同名指令时后注册者覆盖前者，并清理被覆盖者
 * 遗留的别名，避免出现「旧别名指向新指令」的错位。</p>
 */
public final class CommandRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/command");

    /** 主名（小写）→ 指令，保持注册顺序 */
    private static final Map<String, ClientCommand> PRIMARY = new LinkedHashMap<>();

    /** 全部名称与别名（小写）→ 指令 */
    private static final Map<String, ClientCommand> INDEX = new LinkedHashMap<>();

    private CommandRegistry() {
    }

    /** 注册指令；名称为空或重复时按规则处理 */
    public static synchronized void register(ClientCommand command) {
        if (command == null || command.name() == null || command.name().isBlank()) return;

        ClientCommand previous = PRIMARY.put(key(command.name()), command);
        if (previous != null) {
            LOGGER.warn("指令名重复，后注册的覆盖前者：{}", command.name());
            INDEX.values().removeIf(existing -> existing == previous);
        }
        INDEX.put(key(command.name()), command);
        for (String alias : command.aliases()) {
            if (alias == null || alias.isBlank()) continue;
            INDEX.put(key(alias), command);
        }
    }

    /** 按名称或别名查找；未注册返回 {@code null} */
    public static synchronized ClientCommand find(String token) {
        return token == null ? null : INDEX.get(key(token));
    }

    /** 全部指令，按注册顺序 */
    public static synchronized List<ClientCommand> all() {
        return List.copyOf(PRIMARY.values());
    }

    /**
     * 指令名补全候选：只出英文写法（主名与英文别名）。
     *
     * <p>指令前缀（指令名）不汉化——候选里绝不出现 {@code 帮助}、{@code 模块} 这类中文指令名；
     * 中文写法仍可作为输入被 {@link #find(String)} 解析，只是不进入候选。</p>
     */
    public static synchronized List<String> allNames() {
        List<String> names = new ArrayList<>();
        for (ClientCommand command : PRIMARY.values()) {
            for (String name : command.allNames()) {
                if (isEnglish(name)) names.add(name);
            }
        }
        return names;
    }

    /** 指令名是否英文写法（含 ASCII 之外的一律不视为可选指令名） */
    private static boolean isEnglish(String name) {
        if (name == null || name.isBlank()) return false;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) > 0x7F) return false;
        }
        return true;
    }

    public static synchronized int count() {
        return PRIMARY.size();
    }

    public static synchronized void clear() {
        PRIMARY.clear();
        INDEX.clear();
    }

    private static String key(String token) {
        return token.trim().toLowerCase(Locale.ROOT);
    }
}
