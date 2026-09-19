package com.yiyiaddon.m;

import java.lang.invoke.MethodHandles.Lookup;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class b {
   private static final int vU = 12;
   private static final int vV = 128;

   private b() {
   }

   public static SecretKeySpec a(String var0, long var1, long var3, long var5, long var7) throws GeneralSecurityException {
      MessageDigest var9 = MessageDigest.getInstance("SHA-256");
      var9.update(ByteBuffer.allocate(32).putLong(var1).putLong(var3).putLong(var5).putLong(var7).array());
      return new SecretKeySpec(var9.digest(var0.getBytes(StandardCharsets.UTF_8)), "AES");
   }

   public static Object a(Lookup var0, String var1, Class<?> var2, String var3, long var4, long var6, long var8, long var10) throws GeneralSecurityException {
      byte[] var12 = Base64.getDecoder().decode(var3);
      if (var12.length < 28) {
         throw new IllegalArgumentException("发布常量长度无效");
      }

      Cipher var13 = Cipher.getInstance("AES/GCM/NoPadding");
      var13.init(2, a(var1, var4, var6, var8, var10), new GCMParameterSpec(128, Arrays.copyOf(var12, 12)));
      var13.updateAAD(var1.getBytes(StandardCharsets.UTF_8));
      byte[] var14 = var13.doFinal(var12, 12, var12.length - 12);
      ByteBuffer var15 = ByteBuffer.wrap(var14);
      if (var2 == int.class && var14.length == 4) {
         return var15.getInt();
      }

      if (var2 == String.class && var14.length % 2 == 0) {
         char[] var16 = new char[var14.length / 2];

         for (int var17 = 0; var17 < var16.length; var17++) {
            var16[var17] = var15.getChar();
         }

         return new String(var16).intern();
      } else {
         throw new IllegalArgumentException("发布常量类型无效");
      }
   }
}
