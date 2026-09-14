package com.yiyiaddon.platform.resource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

/**
 * 资源内容指纹：按资源内容计算，不按文件名计算。
 *
 * <p><b>计算口径（这是本类唯一需要记住的规则）：</b></p>
 * <ol>
 *     <li>全部资源的<b>标识（命名空间 + 路径）</b>都进入哈希：资源增删、改名、移动目录都会改变指纹；</li>
 *     <li>定义类资源（{@code .json}：方块状态、模型、语言，以及任何其它 JSON）的<b>文件内容</b>进入哈希：
 *         同名不同内容必然得到不同指纹；</li>
 *     <li>贴图、字体、声音等二进制资源只计路径，不计字节：它们只影响外观，不影响资源结构，
 *         逐字节读取会让每次检测都在主线程读几十兆数据。</li>
 * </ol>
 *
 * <p>因此本指纹保证「资源结构或定义变化一定变指纹」，但不保证「只换贴图像素也变指纹」。
 * 需要整包字节级指纹时由生命周期服务退回 ZIP 整包 SHA-256，两条路互不冲突。</p>
 *
 * <p>读取有上限：单文件 512 KB、单次总读取 16 MB。超限的文件只计路径，并把数量记入
 * {@code skippedFiles}，如实反映「本次指纹未覆盖全部内容」。因为遍历顺序固定（按 id 排序），
 * 同一次资源状态多次计算结果一致，缓存不会因随机顺序而失效。</p>
 */
public final class ResourceFingerprint {

    /** 单文件读取上限 */
    private static final long PER_FILE_LIMIT = 512L * 1024L;
    /** 单次总读取上限 */
    private static final long TOTAL_LIMIT = 16L * 1024L * 1024L;
    /** 计入内容的资源后缀 */
    private static final String CONTENT_SUFFIX = ".json";
    /** 字段分隔符，避免 "a/b" 与 "ab" 之类的拼接歧义 */
    private static final byte[] FIELD_SEPARATOR = {(byte) 0x1F};

    private ResourceFingerprint() {
    }

    /**
     * 指纹计算结果。
     *
     * @param value        12 位十六进制指纹
     * @param hashedFiles  计入内容的文件数
     * @param hashedBytes  计入内容的字节数
     * @param skippedFiles 因超限或读取失败而未计入内容的文件数
     */
    public record Result(String value, int hashedFiles, long hashedBytes, int skippedFiles) {

        /** 是否覆盖了全部定义类资源的内容 */
        public boolean full() {
            return skippedFiles == 0;
        }
    }

    /** 计算指纹；入参必须是已排序的枚举结果 */
    public static Result compute(List<ResourceEnumerator.Entry> entries) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            return new Result(null, 0, 0, 0);
        }

        int hashedFiles = 0;
        int skippedFiles = 0;
        long hashedBytes = 0L;

        for (ResourceEnumerator.Entry entry : entries) {
            updateText(digest, entry.id());
            if (entry.path() == null || !entry.path().endsWith(CONTENT_SUFFIX)) continue;

            if (hashedBytes >= TOTAL_LIMIT) {
                skippedFiles++;
                continue;
            }
            long limit = Math.min(PER_FILE_LIMIT, TOTAL_LIMIT - hashedBytes);
            byte[] content = readContent(entry, limit);
            if (content == null) {
                skippedFiles++;
                continue;
            }
            digest.update(FIELD_SEPARATOR);
            digest.update(content);
            hashedFiles++;
            hashedBytes += content.length;
        }

        return new Result(HexFormat.of().formatHex(digest.digest(), 0, 6), hashedFiles, hashedBytes, skippedFiles);
    }

    private static byte[] readContent(ResourceEnumerator.Entry entry, long limit) {
        try (InputStream in = entry.resource().open()) {
            java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
            byte[] chunk = new byte[8192];
            long total = 0L;
            int read;
            while ((read = in.read(chunk)) != -1) {
                int allowed = (int) Math.min(read, limit - total);
                if (allowed > 0) buffer.write(chunk, 0, allowed);
                total += read;
                if (total >= limit) break;
            }
            return buffer.toByteArray();
        } catch (Throwable error) {
            // 资源在重载过程中可能已被关闭：按未计入处理，由跳过计数如实反映
            return null;
        }
    }

    private static void updateText(MessageDigest digest, String text) {
        digest.update(FIELD_SEPARATOR);
        if (text != null) digest.update(text.getBytes(StandardCharsets.UTF_8));
    }
}
