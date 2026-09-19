package com.yiyiaddon.build;

import com.yiyiaddon.utils.ReleaseProtection;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/** 仅构建期使用：每个常量独立随机密钥材料、认证数据与 nonce，不使用自造密码算法。 */
final class ConstantEncryption {
    static final String RUNTIME = Type.getInternalName(ReleaseProtection.class);
    private static final Handle BOOTSTRAP = new Handle(Opcodes.H_INVOKESTATIC, RUNTIME, "bootstrap",
            "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/Class;"
                    + "Ljava/lang/String;JJJJ)Ljava/lang/Object;", false);
    private final SecureRandom random;

    ConstantEncryption(SecureRandom random) {
        this.random = random;
    }

    /** 按码元写入，不能用会替换孤立代理项的字符集编码器。 */
    ConstantDynamic string(String value) throws GeneralSecurityException {
        ByteBuffer data = ByteBuffer.allocate(value.length() * Character.BYTES);
        for (int i = 0; i < value.length(); i++) data.putChar(value.charAt(i));
        return encrypt(data.array(), "Ljava/lang/String;");
    }

    ConstantDynamic integer(int value) throws GeneralSecurityException {
        return encrypt(ByteBuffer.allocate(Integer.BYTES).putInt(value).array(), "I");
    }

    /** 密文载荷为 nonce + 密文 + GCM 标签；使用标准 Base64 以满足 class 常量编码限制。 */
    private ConstantDynamic encrypt(byte[] plain, String descriptor) throws GeneralSecurityException {
        String name = "s" + Long.toUnsignedString(random.nextLong(), 36);
        long a = random.nextLong(), b = random.nextLong(), c = random.nextLong(), d = random.nextLong();
        byte[] nonce = new byte[12];
        random.nextBytes(nonce);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, ReleaseProtection.deriveKey(name, a, b, c, d),
                new GCMParameterSpec(128, nonce));
        cipher.updateAAD(name.getBytes(StandardCharsets.UTF_8));
        byte[] encrypted = cipher.doFinal(plain);
        byte[] payload = ByteBuffer.allocate(nonce.length + encrypted.length).put(nonce).put(encrypted).array();
        return new ConstantDynamic(name, descriptor, BOOTSTRAP,
                Base64.getEncoder().encodeToString(payload), a, b, c, d);
    }
}
