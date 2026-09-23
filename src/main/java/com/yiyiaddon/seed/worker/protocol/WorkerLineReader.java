package com.yiyiaddon.seed.worker.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 种子挖矿 · Worker IPC · <b>长度受限的一行读取</b>（两端共用）。
 *
 * <p>刻意不用 {@code BufferedReader#readLine()}：它无法对单行长度设上限，一段错乱或恶意的输入
 * 就能把内存撑爆。协议要求「长度受限」（阶段 232 口径第十七节），因此这里按字节读到 {@code \n}
 * 为止，超过上限直接抛错（fail-closed）。</p>
 */
public final class WorkerLineReader {

    private WorkerLineReader() {
    }

    /**
     * 读一行（不含行尾）。
     *
     * @param input    输入流
     * @param buffer   复用的缓冲（会被 reset）
     * @param maxBytes 单行字节上限
     * @return 一行文本；流结束且无剩余内容时返回 null
     * @throws IOException 读失败或超过长度上限
     */
    public static String readLine(InputStream input, ByteArrayOutputStream buffer, int maxBytes)
            throws IOException {
        buffer.reset();
        while (true) {
            int value = input.read();
            if (value < 0) {
                return buffer.size() == 0 ? null : buffer.toString(StandardCharsets.UTF_8);
            }
            if (value == '\n') {
                return buffer.toString(StandardCharsets.UTF_8);
            }
            if (value == '\r') {
                continue;
            }
            if (buffer.size() >= maxBytes) {
                throw new IOException("协议行超过上限 " + maxBytes + " 字节");
            }
            buffer.write(value);
        }
    }
}
