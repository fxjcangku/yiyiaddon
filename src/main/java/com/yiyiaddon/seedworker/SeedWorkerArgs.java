package com.yiyiaddon.seedworker;

import com.yiyiaddon.seed.worker.protocol.SeedWorkerArguments;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * 种子挖矿 · Worker · <b>解析后的启动参数</b>。
 *
 * <p>参数名与编码格式的唯一契约在 {@link SeedWorkerArguments}（客户端与 Worker 共用一份），
 * 本类只把它变成强类型对象，并在缺项时直接失败 —— 宁可启动失败并写清原因，也不猜默认值。</p>
 *
 * @param runtimeDir       Worker 运行目录（子进程工作目录；内含 server.properties / universe / logs）
 * @param handshakeFile    握手文件（宿主就绪后原子写入；客户端以它作为「可以连接」的信号）
 * @param token            客户端生成的高熵令牌（Worker 只接受带这个令牌的请求）
 * @param protocolVersion  客户端协议版本
 * @param minecraftVersion 客户端 Minecraft 版本
 * @param modVersion       客户端模组版本
 * @param hostPort         Vanilla 宿主监听端口（仅回环；宿主起来后立即停用监听）
 * @param parentPid        父进程 PID（父进程消失即自我了断，杜绝孤儿进程）
 */
public record SeedWorkerArgs(Path runtimeDir, Path handshakeFile, String token, int protocolVersion,
                             String minecraftVersion, String modVersion, int hostPort, long parentPid) {

    /** 解析命令行；缺项 / 未知名 / 数值非法一律抛异常。 */
    public static SeedWorkerArgs parse(String[] args) {
        Map<String, String> values = SeedWorkerArguments.decode(args);
        return new SeedWorkerArgs(
                Paths.get(SeedWorkerArguments.require(values, SeedWorkerArguments.ARG_RUNTIME_DIR)),
                Paths.get(SeedWorkerArguments.require(values, SeedWorkerArguments.ARG_HANDSHAKE_FILE)),
                SeedWorkerArguments.require(values, SeedWorkerArguments.ARG_TOKEN),
                Integer.parseInt(SeedWorkerArguments.require(values, SeedWorkerArguments.ARG_PROTOCOL_VERSION)),
                SeedWorkerArguments.require(values, SeedWorkerArguments.ARG_MINECRAFT_VERSION),
                SeedWorkerArguments.require(values, SeedWorkerArguments.ARG_MOD_VERSION),
                Integer.parseInt(SeedWorkerArguments.require(values, SeedWorkerArguments.ARG_HOST_PORT)),
                Long.parseLong(SeedWorkerArguments.require(values, SeedWorkerArguments.ARG_PARENT_PID)));
    }
}
