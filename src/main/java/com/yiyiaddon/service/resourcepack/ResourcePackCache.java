package com.yiyiaddon.service.resourcepack;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * 服务器资源包缓存与下载服务（全局唯一合法下载器）。
 *
 * <p>职责只有一件事：把服务器下发的资源包落到本地并记住位置。它<b>不持有任何策略</b>——
 * 是否下载、是否使用由上层生命周期服务决定。</p>
 *
 * <p><b>缓存目录与命名（兼容既有数据）：</b>{@code <游戏目录>/yiyiaddon_resourcepacks}，
 * 文件名为 {@code <ServerKey>.zip}（ServerKey = 真实 host:port，净化后）。
 * 旧版 {@code <host>_<中文名>.zip} 命名仍可被 {@link #cachedZip(String)} 识别并复用，
 * 不重复下载、不丢旧缓存。</p>
 *
 * <p><b>端口隔离铁律：</b>同一 host 的不同端口是两台不同服务器，缓存绝不跨端口共用。</p>
 */
public final class ResourcePackCache {

    /** 缓存目录名（沿用既有目录，保证老缓存可直接复用） */
    private static final String CACHE_DIR_NAME = "yiyiaddon_resourcepacks";

    /** 正在下载的资源包 packId，避免同一资源包并发重复下载触发服务器限流 */
    private static final Set<UUID> DOWNLOADING = ConcurrentHashMap.newKeySet();

    /** 进服前只能按 URL host 命名的文件，进服后重命名为真实 ServerKey */
    private static final Map<String, UUID> PENDING_RENAMES = new ConcurrentHashMap<>();

    /** 旁路下载失败（一次性 token 等）的资源包，进服后从原版 downloads 缓存复制 */
    private static final Set<UUID> PENDING_COPIES = ConcurrentHashMap.newKeySet();

    /** 玩家尚未进入世界时缓存的提示，待玩家就绪后补发 */
    private static final Queue<String> PENDING_NOTICES = new ConcurrentLinkedQueue<>();

    /** 提示订阅者（上层注册自己的输出通道） */
    private static final List<Consumer<String>> NOTICE_LISTENERS = new CopyOnWriteArrayList<>();

    private ResourcePackCache() {
    }

    private static Minecraft mc() {
        return Minecraft.getInstance();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  目录 / ServerKey / 缓存文件
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 缓存目录（不存在时创建） */
    public static File dir() {
        File dir = new File(mc().gameDirectory, CACHE_DIR_NAME);
        if (!dir.exists() && !dir.mkdirs()) {
            // 创建失败时仍返回路径，后续写入会各自失败并回报
            return dir;
        }
        return dir;
    }

    /**
     * 当前服务器唯一键：真实 {@code host:port}（小写）。拿不到服务器信息返回 {@code null}。
     *
     * <p>优先读 {@link ServerData#ip}（玩家实际输入的地址），缺端口时补原版默认端口 25565；
     * ServerData 缺失（极端直连场景）时退回网络连接的远端 {@code host:port}。</p>
     */
    public static String currentServerKey() {
        ServerData data = serverData();
        if (data != null && data.ip != null && !data.ip.isBlank()) {
            return canonicalKey(data.ip);
        }
        ClientPacketListener listener = mc().getConnection();
        if (listener != null && listener.getConnection() != null) {
            java.net.SocketAddress remote = listener.getConnection().getRemoteAddress();
            if (remote instanceof InetSocketAddress inet) {
                String host = inet.getAddress() != null ? inet.getAddress().getHostAddress() : inet.getHostString();
                if (host != null && !host.isBlank()) {
                    return (host + ":" + inet.getPort()).toLowerCase(Locale.ROOT);
                }
            }
        }
        return null;
    }

    /** 把玩家输入的地址规范化为 {@code host:port} 小写形式 */
    public static String canonicalKey(String address) {
        if (address == null) return null;
        String raw = address.trim().toLowerCase(Locale.ROOT);
        if (raw.isEmpty()) return null;
        // IPv6 字面量：[::1]:25565 或 ::1
        if (raw.startsWith("[")) {
            int end = raw.indexOf(']');
            if (end > 0) {
                String host = raw.substring(1, end);
                String rest = raw.substring(end + 1);
                if (rest.startsWith(":") && rest.length() > 1) return "[" + host + "]:" + rest.substring(1);
                return "[" + host + "]:25565";
            }
        }
        int colon = raw.lastIndexOf(':');
        if (colon > 0 && raw.indexOf(':') == colon) {
            String port = raw.substring(colon + 1);
            if (!port.isEmpty() && port.chars().allMatch(Character::isDigit)) return raw;
        }
        return raw + ":25565";
    }

    /** 缓存文件名：{@code <ServerKey>.zip} */
    public static String cacheFileName(String serverKey) {
        return sanitize(serverKey) + ".zip";
    }

    /**
     * 查找某服务器已缓存的资源包 ZIP。
     *
     * <p>查找顺序：</p>
     * <ol>
     *   <li>精确 {@code <host>_<port>.zip}（当前标准命名）；</li>
     *   <li>历史命名 {@code <host>_<port>_<任意名>.zip} / {@code <host>.<port>.zip}
     *       ——端口必须完全一致；</li>
     *   <li>历史无端口命名 {@code <host>.zip} / {@code <host>_<中文名>.zip}
     *       ——只在候选唯一时接受，多个候选一律放弃，宁可重新下载也不跨端口串资源。</li>
     * </ol>
     *
     * @return 存在的缓存文件；没有返回 {@code null}
     */
    public static File cachedZip(String serverKey) {
        if (serverKey == null || serverKey.isBlank()) return null;
        File exact = new File(dir(), cacheFileName(serverKey));
        if (isUsable(exact)) return exact;

        String host = hostOf(serverKey);
        String port = portOf(serverKey);
        if (host == null || port == null) return null;

        File[] files = dir().listFiles();
        if (files == null) return null;

        List<File> portMatched = new ArrayList<>();
        List<File> hostOnly = new ArrayList<>();
        for (File f : files) {
            if (!isUsable(f)) continue;
            String stem = stripZipSuffix(f.getName().toLowerCase(Locale.ROOT));
            if (stem == null) continue;
            String tail = hostTail(stem, host);
            if (tail == null) continue;
            if (tail.isEmpty()) {
                hostOnly.add(f);
                continue;
            }
            String first = firstSegment(tail);
            if (!first.isEmpty() && first.chars().allMatch(Character::isDigit)) {
                // 形如 <host>_25565 / <host>.25566_xxx：端口必须完全一致才认
                if (first.equals(port)) portMatched.add(f);
            } else {
                // 形如 <host>_服务器中文名：无端口信息的历史命名，只能作弱兜底
                hostOnly.add(f);
            }
        }
        if (!portMatched.isEmpty()) return portMatched.get(0);
        return hostOnly.size() == 1 ? hostOnly.get(0) : null;
    }

    /** 取 ServerKey 的端口部分；无端口返回 {@code null} */
    public static String portOf(String serverKey) {
        if (serverKey == null) return null;
        String key = serverKey.trim().toLowerCase(Locale.ROOT);
        if (key.startsWith("[")) {
            int end = key.indexOf(']');
            if (end < 0) return null;
            String rest = key.substring(end + 1);
            return rest.startsWith(":") && rest.length() > 1 ? rest.substring(1) : null;
        }
        int colon = key.lastIndexOf(':');
        if (colon > 0 && key.indexOf(':') == colon) return key.substring(colon + 1);
        return null;
    }

    /** 从 ServerKey 中取 host 部分（去掉端口与方括号） */
    public static String hostOf(String serverKey) {
        if (serverKey == null) return null;
        String key = serverKey.toLowerCase(Locale.ROOT);
        if (key.startsWith("[")) {
            int end = key.indexOf(']');
            return end > 0 ? sanitize(key.substring(1, end)) : null;
        }
        int colon = key.lastIndexOf(':');
        String host = colon > 0 ? key.substring(0, colon) : key;
        return host.isBlank() ? null : sanitize(host);
    }

    /** 从 URL 提取 host（含非默认端口），用作进服前无法确定 ServerKey 时的临时文件名 */
    public static String hostOfUrl(String url) {
        try {
            URI uri = URI.create(url);
            String host = uri.getHost();
            if (host == null || host.isEmpty()) return "unknown";
            int port = uri.getPort();
            if (port > 0 && port != 80 && port != 443) return host + "_" + port;
            return host;
        } catch (Exception e) {
            return "unknown";
        }
    }

    /** 净化文件名非法字符（Windows 路径分隔符等） */
    public static String sanitize(String label) {
        if (label == null || label.isBlank()) return "unknown";
        return label.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    /** 计算文件 SHA-1 十六进制摘要（服务器下发的 hash 校对） */
    public static String sha1OfFile(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        try (InputStream in = new java.io.FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = in.read(buffer)) != -1) digest.update(buffer, 0, read);
        }
        return HexFormat.of().formatHex(digest.digest());
    }

    /**
     * 资源指纹：ZIP <b>全部字节</b>的 SHA-256 前 12 位。
     *
     * <p>必须整包哈希，不能用「长度 + 头尾采样」：目录结构一致、仅中间模型或贴图不同的两个
     * 资源包，采样窗口可能完全相同，会导致不同服务器算出同一指纹而串档。</p>
     *
     * @return 短指纹（12 位十六进制）；失败返回 {@code null}
     */
    public static String fingerprintOf(File file) {
        if (!isUsable(file)) return null;
        try (InputStream in = new java.io.BufferedInputStream(new java.io.FileInputStream(file), 65536)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[8192];
            int read;
            while ((read = in.read(buffer)) != -1) digest.update(buffer, 0, read);
            return HexFormat.of().formatHex(digest.digest(), 0, 6);
        } catch (Exception ignored) {
            return null;
        }
    }

    /** 是否存在可用的缓存文件（存在且非空） */
    public static boolean hasUsableCache(String serverKey) {
        return isUsable(cachedZip(serverKey));
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  提示通道
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 注册提示订阅者（上层构造时注册一次） */
    public static void addNoticeListener(Consumer<String> listener) {
        if (listener != null) NOTICE_LISTENERS.add(listener);
    }

    /** 下载线程里发提示：投递回主线程，玩家未就绪先入队 */
    public static void asyncNotify(String message) {
        mc().execute(() -> {
            if (mc().player != null) {
                broadcast(message);
            } else {
                PENDING_NOTICES.add(message);
            }
        });
    }

    /** 主线程补发：玩家就绪后把缓存的提示逐条广播 */
    public static void flushNotices() {
        if (mc().player == null) return;
        String message;
        while ((message = PENDING_NOTICES.poll()) != null) {
            broadcast(message);
        }
    }

    private static void broadcast(String message) {
        for (Consumer<String> listener : NOTICE_LISTENERS) {
            try {
                listener.accept(message);
            } catch (Exception ignored) {
                // 单个订阅者异常不影响其它订阅者
            }
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  主线程兜底（重命名 / 原版缓存复制）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 每 tick 兜底：补发提示、重命名、从原版缓存复制（由常驻服务驱动，不依赖任何模块开关） */
    public static void tick() {
        if (mc().player == null) return;
        flushNotices();
        if (!PENDING_RENAMES.isEmpty()) renamePendingFiles();
        if (!PENDING_COPIES.isEmpty()) copyPendingFromVanilla();
    }

    /** 断线清理：只清会话级队列，磁盘缓存保留 */
    public static void clearSession() {
        PENDING_RENAMES.clear();
        PENDING_COPIES.clear();
        PENDING_NOTICES.clear();
    }

    /** 进服后 ServerKey 就绪，把按 URL host 命名的文件重命名为 {@code <ServerKey>.zip} */
    private static void renamePendingFiles() {
        String key = currentServerKey();
        if (key == null) return;

        String target = cacheFileName(key);
        for (Map.Entry<String, UUID> entry : List.copyOf(PENDING_RENAMES.entrySet())) {
            String oldName = entry.getKey();
            PENDING_RENAMES.remove(oldName);
            if (target.equals(oldName)) continue;

            File oldFile = new File(dir(), oldName);
            if (!oldFile.exists()) continue;

            File newFile = new File(dir(), target);
            if (newFile.exists()) {
                deleteQuietly(oldFile);
                asyncNotify("§7已存在同名缓存，保留既有文件 §8» §f" + target);
            } else if (oldFile.renameTo(newFile)) {
                asyncNotify("§a资源包已下载并落盘 §8» §f" + target);
            }
        }
    }

    /**
     * 旁路下载失败（一次性 token 等）的资源包，进服后从原版 downloads 缓存复制兜底。
     *
     * <p>原版结构：{@code downloads/<downloadId>/<packId>/<sha1>}，downloadId 每次随机，按 packId 匹配。</p>
     */
    private static void copyPendingFromVanilla() {
        String key = currentServerKey();
        if (key == null) return;

        File downloadsDir = new File(mc().gameDirectory, "downloads");
        if (!downloadsDir.isDirectory()) return;

        for (UUID packId : List.copyOf(PENDING_COPIES)) {
            File[] downloadDirs = downloadsDir.listFiles();
            if (downloadDirs == null) break;

            boolean copied = false;
            for (File d : downloadDirs) {
                if (!d.isDirectory()) continue;
                File packDir = new File(d, packId.toString());
                if (!packDir.isDirectory()) continue;

                File[] files = packDir.listFiles();
                if (files == null) continue;
                for (File src : files) {
                    if (!src.isFile() || src.length() == 0) continue;
                    File target = new File(dir(), cacheFileName(key));
                    try {
                        java.nio.file.Files.copy(src.toPath(), target.toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        PENDING_COPIES.remove(packId);
                        asyncNotify("§a资源包已下载并落盘 §8» §f" + target.getName() + " §8(原版缓存)");
                        copied = true;
                    } catch (Exception ignored) {
                        // 复制失败（文件被占用等）：保持待复制，下帧重试
                    }
                    break;
                }
                if (copied) break;
            }
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  下载
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 异步下载资源包到本地缓存（支持重试 / 超时 / 断点续传 / SHA-1 校验）。
     *
     * <p>纯旁路：只把文件存到缓存目录，不碰资源包确认协议。同一 packId 并发调用只下载一次；
     * 同一服务器已有缓存直接复用不重下。</p>
     *
     * @param packId    服务器分配的包 ID
     * @param url       下载地址
     * @param hash      服务器给的 SHA-1（可空）
     * @param retries   重试次数
     * @param timeoutMs 单次读取超时
     * @param resume    是否断点续传
     * @return true 表示本次调用真正发起了下载（false = 已有缓存 / 正在下载 / 参数非法）
     */
    public static boolean downloadAsync(UUID packId, String url, String hash,
                                        int retries, int timeoutMs, boolean resume) {
        if (packId == null || url == null || url.isBlank()) return false;
        if (!DOWNLOADING.add(packId)) return false;

        // 主线程先取 ServerKey（虚拟线程里读连接不安全）
        String serverKey = currentServerKey();
        String label = serverKey != null ? cacheFileName(serverKey) : null;
        if (label != null && isUsable(new File(dir(), label))) {
            DOWNLOADING.remove(packId);
            return false;
        }

        Thread.ofVirtual().start(() -> {
            try {
                File dir = dir();
                // 未拿到 ServerKey（进服流程中）则按 URL host 命名，进服后重命名
                String fileName = label != null ? label : sanitize(hostOfUrl(url)) + ".zip";
                File targetFile = new File(dir, fileName);
                File partFile = new File(dir, fileName + ".part");

                if (isUsable(targetFile)) return;

                for (int attempt = 1; attempt <= Math.max(1, retries); attempt++) {
                    try {
                        if (downloadOnce(url, hash, partFile, targetFile, timeoutMs, resume)) {
                            if (label != null) {
                                asyncNotify("§a资源包已下载并落盘 §8» §f" + fileName);
                            } else {
                                // 名字是 URL host 临时名，进服后重命名为 <ServerKey>.zip 再提示
                                PENDING_RENAMES.put(fileName, packId);
                            }
                            return;
                        }
                    } catch (Exception ignored) {
                        // 单次下载异常，按指数退避重试
                    }

                    if (attempt < retries) {
                        try {
                            Thread.sleep(1000L * (1 << (attempt - 1)));
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }

                // 重试耗尽：进服后从原版 downloads 缓存复制兜底
                PENDING_COPIES.add(packId);
            } finally {
                DOWNLOADING.remove(packId);
            }
        });
        return true;
    }

    /** 单次下载尝试；返回 true 表示下载并校验成功 */
    private static boolean downloadOnce(String url, String hash, File partFile, File targetFile,
                                        int timeoutMs, boolean resume) throws Exception {
        long existingSize = 0;
        if (resume && partFile.exists()) existingSize = partFile.length();

        // 先手动跟随重定向（http→https 跨协议也能走通），否则 HttpURLConnection 跨协议直接失败
        String finalUrl = followRedirects(url, timeoutMs);

        HttpURLConnection conn = (HttpURLConnection) URI.create(finalUrl).toURL().openConnection();
        conn.setConnectTimeout(timeoutMs);
        conn.setReadTimeout(timeoutMs);
        // 必须伪装成原版客户端的 UA：不少自建资源包服务器会校验 User-Agent
        conn.setRequestProperty("User-Agent",
            "Minecraft Java/" + SharedConstants.getCurrentVersion().name());
        if (existingSize > 0) conn.setRequestProperty("Range", "bytes=" + existingSize + "-");

        int code = conn.getResponseCode();
        boolean append = code == 206;
        if (code != 200 && code != 206) {
            conn.disconnect();
            return false;
        }

        if (append) {
            try (InputStream in = conn.getInputStream();
                 RandomAccessFile raf = new RandomAccessFile(partFile, "rw")) {
                raf.seek(existingSize);
                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) raf.write(buffer, 0, read);
            }
        } else {
            try (InputStream in = conn.getInputStream();
                 FileOutputStream out = new FileOutputStream(partFile)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) out.write(buffer, 0, read);
            }
        }
        conn.disconnect();

        // SHA-1 校验：续传场景必须哈希全文件
        if (hash != null && !hash.isEmpty()) {
            String actual = sha1OfFile(partFile);
            if (!actual.equalsIgnoreCase(hash)) {
                deleteQuietly(partFile);
                return false;
            }
        }

        if (!partFile.renameTo(targetFile)) {
            try (InputStream in = new java.io.FileInputStream(partFile);
                 FileOutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) out.write(buffer, 0, read);
            }
            deleteQuietly(partFile);
        }
        return true;
    }

    /** 手动跟随 HTTP 重定向（最多 5 层，支持跨协议） */
    private static String followRedirects(String url, int timeoutMs) throws Exception {
        String current = url;
        for (int i = 0; i < 5; i++) {
            HttpURLConnection conn = (HttpURLConnection) URI.create(current).toURL().openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.setConnectTimeout(timeoutMs);
            conn.setReadTimeout(timeoutMs);
            conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            int code = conn.getResponseCode();
            if (code >= 300 && code < 400) {
                String location = conn.getHeaderField("Location");
                conn.disconnect();
                if (location == null || location.isEmpty()) throw new java.io.IOException("重定向缺少 Location");
                current = URI.create(current).resolve(location).toString();
                continue;
            }
            conn.disconnect();
            return current;
        }
        throw new java.io.IOException("重定向次数过多（超过 5 层）");
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  内部工具
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 去掉 {@code .zip} 后缀；不是 zip 返回 {@code null} */
    private static String stripZipSuffix(String name) {
        return name.endsWith(".zip") ? name.substring(0, name.length() - 4) : null;
    }

    /**
     * 判断缓存文件主干是否属于某 host 的命名，返回 host 之后的剩余部分。
     *
     * @return 剩余部分（空串 = 裸 host 命名）；不是该 host 的命名返回 {@code null}
     */
    private static String hostTail(String stem, String host) {
        if (stem.equals(host)) return "";
        if (stem.startsWith(host + "_") || stem.startsWith(host + ".")) {
            return stem.substring(host.length() + 1);
        }
        return null;
    }

    /** 取首个分隔片段（{@code _} 或 {@code .} 切分） */
    private static String firstSegment(String value) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '_' || c == '.') return value.substring(0, i);
        }
        return value;
    }

    private static boolean isUsable(File file) {
        return file != null && file.isFile() && file.length() > 0;
    }

    private static void deleteQuietly(File file) {
        try {
            java.nio.file.Files.deleteIfExists(file.toPath());
        } catch (Exception ignored) {
            // 删除失败不影响主流程
        }
    }

    /** 读取当前服务器信息（优先 getCurrentServer，其次连接） */
    private static ServerData serverData() {
        Minecraft minecraft = mc();
        ServerData data = minecraft.getCurrentServer();
        if (data == null) {
            ClientPacketListener connection = minecraft.getConnection();
            data = connection != null ? connection.getServerData() : null;
        }
        return data;
    }
}
