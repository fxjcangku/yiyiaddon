package com.yiyiaddon.utils;

import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 发布字节码的动态常量引导器，普通开发代码不调用它。
 *
 * <p>构建工具把字符串和跳转选择值转换为 AES-256-GCM 密文；JVM 首次解析动态常量时
 * 调用本类，之后复用解析结果。密钥材料随客户端分发，只用于增加静态阅读成本，
 * 不构成对客户端持有者的保密边界，也不能阻止运行期提取明文。</p>
 */
public final class ReleaseProtection {
    private static final int NONCE_BYTES = 12;
    private static final int TAG_BITS = 128;

    private ReleaseProtection() {
    }

    /**
     * 按动态常量站点名称派生密钥；构建端与运行端共用，避免编码口径各自演化。
     * 四个随机长整数提供 256 位输入，站点名称同时作为 GCM 附加认证数据。
     */
    public static SecretKeySpec deriveKey(String name, long a, long b, long c, long d)
            throws GeneralSecurityException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(ByteBuffer.allocate(32).putLong(a).putLong(b).putLong(c).putLong(d).array());
        return new SecretKeySpec(digest.digest(name.getBytes(StandardCharsets.UTF_8)), "AES");
    }

    /**
     * JVM 动态常量入口。字符串按 UTF-16 码元还原，保留孤立代理项、空字符与引用相等语义；
     * 整数用作跳转分派键。密文损坏时传播认证错误，让 JVM 拒绝解析，绝不返回错误默认值。
     */
    public static Object bootstrap(MethodHandles.Lookup lookup, String name, Class<?> type,
                                   String encoded, long a, long b, long c, long d)
            throws GeneralSecurityException {
        byte[] payload = Base64.getDecoder().decode(encoded);
        if (payload.length < NONCE_BYTES + TAG_BITS / 8) {
            throw new IllegalArgumentException("发布常量长度无效");
        }
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, deriveKey(name, a, b, c, d),
                new GCMParameterSpec(TAG_BITS, Arrays.copyOf(payload, NONCE_BYTES)));
        cipher.updateAAD(name.getBytes(StandardCharsets.UTF_8));
        byte[] plain = cipher.doFinal(payload, NONCE_BYTES, payload.length - NONCE_BYTES);
        ByteBuffer buffer = ByteBuffer.wrap(plain);
        if (type == int.class && plain.length == Integer.BYTES) {
            return buffer.getInt();
        }
        if (type != String.class || plain.length % Character.BYTES != 0) {
            throw new IllegalArgumentException("发布常量类型无效");
        }
        char[] chars = new char[plain.length / Character.BYTES];
        for (int i = 0; i < chars.length; i++) chars[i] = buffer.getChar();
        // Java 字面量是驻留字符串，必须保持原有 == 判断与跨类常量身份。
        return new String(chars).intern();
    }
}
