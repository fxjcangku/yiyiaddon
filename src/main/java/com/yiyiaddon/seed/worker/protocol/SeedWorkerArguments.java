package com.yiyiaddon.seed.worker.protocol;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 种子挖矿 · Worker <b>启动参数编解码</b>（客户端与 Worker 共用的唯一契约）。
 *
 * <p>为什么要有这个类：客户端要把「运行目录 / 握手文件 / 令牌 / 版本 / 端口 / 父进程 PID」交给
 * 子进程，Worker 要把它读回来。两端各写一遍字符串字面量，早晚会漂移成两个不兼容的版本，
 * 而且这种漂移只在正式运行时才炸。所以参数名与顺序<b>只有这一份定义</b>。</p>
 *
 * <p>参数一律是「名字 + 值」成对出现，客户端用 {@code ProcessBuilder(List)} 逐项传入，
 * 因此路径里的空格 / 中文都不需要转义（阶段 232 口径第八十九节）。</p>
 */
public final class SeedWorkerArguments {

    /** 参数名（顺序即 {@link #encode} 的输出顺序，与 {@link #decode} 无关）。 */
    public static final String ARG_RUNTIME_DIR = "--runtime-dir";
    public static final String ARG_HANDSHAKE_FILE = "--handshake-file";
    public static final String ARG_TOKEN = "--token";
    public static final String ARG_PROTOCOL_VERSION = "--protocol-version";
    public static final String ARG_MINECRAFT_VERSION = "--minecraft-version";
    public static final String ARG_MOD_VERSION = "--mod-version";
    public static final String ARG_HOST_PORT = "--host-port";
    public static final String ARG_PARENT_PID = "--parent-pid";

    private SeedWorkerArguments() {
    }

    /** 客户端侧：生成子进程参数序列。 */
    public static List<String> encode(String runtimeDir, String handshakeFile, String token, int protocolVersion,
                                      String minecraftVersion, String modVersion, int hostPort, long parentPid) {
        List<String> arguments = new ArrayList<>(16);
        arguments.add(ARG_RUNTIME_DIR);
        arguments.add(runtimeDir);
        arguments.add(ARG_HANDSHAKE_FILE);
        arguments.add(handshakeFile);
        arguments.add(ARG_TOKEN);
        arguments.add(token);
        arguments.add(ARG_PROTOCOL_VERSION);
        arguments.add(Integer.toString(protocolVersion));
        arguments.add(ARG_MINECRAFT_VERSION);
        arguments.add(minecraftVersion);
        arguments.add(ARG_MOD_VERSION);
        arguments.add(modVersion);
        arguments.add(ARG_HOST_PORT);
        arguments.add(Integer.toString(hostPort));
        arguments.add(ARG_PARENT_PID);
        arguments.add(Long.toString(parentPid));
        return arguments;
    }

    /**
     * Worker 侧：解析子进程参数。
     *
     * @throws IllegalArgumentException 未知参数名 / 名字后面没有值
     */
    public static Map<String, String> decode(String[] args) {
        Map<String, String> values = new LinkedHashMap<>();
        for (int index = 0; index < args.length; index += 2) {
            if (index + 1 >= args.length) {
                throw new IllegalArgumentException("参数 " + args[index] + " 缺少值");
            }
            String key = args[index];
            if (!isKnown(key)) {
                throw new IllegalArgumentException("未知启动参数：" + key);
            }
            values.put(key, args[index + 1]);
        }
        return values;
    }

    /** 取必填参数。 */
    public static String require(Map<String, String> values, String key) {
        String value = values.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("缺少启动参数：" + key);
        }
        return value;
    }

    private static boolean isKnown(String key) {
        return ARG_RUNTIME_DIR.equals(key) || ARG_HANDSHAKE_FILE.equals(key) || ARG_TOKEN.equals(key)
                || ARG_PROTOCOL_VERSION.equals(key) || ARG_MINECRAFT_VERSION.equals(key)
                || ARG_MOD_VERSION.equals(key) || ARG_HOST_PORT.equals(key) || ARG_PARENT_PID.equals(key);
    }
}
