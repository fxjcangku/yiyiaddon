/*
 * Decompiled with CFR 0.152.
 */
package com.yiyiaddon.m;

import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class b {
    static final private int vU = 12;
    static final private int vV = 128;

    private b() {
    }

    public static SecretKeySpec a(String string, long l, long l2, long l3, long l4) throws GeneralSecurityException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(ByteBuffer.allocate(32).putLong(l).putLong(l2).putLong(l3).putLong(l4).array());
        return new SecretKeySpec(messageDigest.digest(string.getBytes(StandardCharsets.UTF_8)), "AES");
    }

    public static Object a(MethodHandles.Lookup lookup, String string, Class<?> clazz, String string2, long l, long l2, long l3, long l4) throws GeneralSecurityException {
        byte[] byArray = Base64.getDecoder().decode(string2);
        if (byArray.length < 28) {
            throw new IllegalArgumentException("\u53d1\u5e03\u5e38\u91cf\u957f\u5ea6\u65e0\u6548");
        }
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(2, (Key)b.a(string, l, l2, l3, l4), new GCMParameterSpec(128, Arrays.copyOf(byArray, 12)));
        cipher.updateAAD(string.getBytes(StandardCharsets.UTF_8));
        byte[] byArray2 = cipher.doFinal(byArray, 12, byArray.length - 12);
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray2);
        if (clazz == Integer.TYPE && byArray2.length == 4) {
            return byteBuffer.getInt();
        }
        if (clazz != String.class || byArray2.length % 2 != 0) {
            throw new IllegalArgumentException("\u53d1\u5e03\u5e38\u91cf\u7c7b\u578b\u65e0\u6548");
        }
        char[] cArray = new char[byArray2.length / 2];
        for (int i = 0; i < cArray.length; ++i) {
            cArray[i] = byteBuffer.getChar();
        }
        return new String(cArray).intern();
    }
}
