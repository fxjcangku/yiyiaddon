package com.yiyiaddon.i.e;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

public final class c {
   private static final long aI = 524288L;
   private static final long aJ = 16777216L;
   private static final String Eg = (String)com.yiyiaddon.m.b.a<"s22kf5jqgpgd4a","ckqtskNwdUXdu5W/jYoZp2rtdD0futfrGEmmFxTcNaZ8NKzaHvU=",-241210868718943472,477737730616551236,5653342010967956357,-4280670353772923157>();
   private static final byte[] a = new byte[]{31};

   private c() {
   }

   public static c.a a(List<b.a> var0) {
      MessageDigest var1;
      try {
         var1 = MessageDigest.getInstance(
            (String)com.yiyiaddon.m.b.a<"s2w1g5xlh7ia33","JD2ADc/gR5PLUJIKdRacji5h2ROvacq648aawUsnpxHhoRlykmRRhzE7",6530721769539129486,7209194767723562783,8992689056385909913,-4946569107942895551>()
         );
      } catch (Exception var11) {
         return new c.a(null, 0, 0L, 0);
      }

      int var2 = 0;
      int var3 = 0;
      long var4 = 0L;

      for (b.a var7 : var0) {
         a(var1, var7.s());
         if (var7.gl() != null
            && var7.gl()
               .endsWith(
                  (String)com.yiyiaddon.m.b.a<"s22kf5jqgpgd4a","ckqtskNwdUXdu5W/jYoZp2rtdD0futfrGEmmFxTcNaZ8NKzaHvU=",-241210868718943472,477737730616551236,5653342010967956357,-4280670353772923157>()
               )) {
            if (var4 >= 16777216L) {
               var3++;
            } else {
               long var8 = Math.min(524288L, 16777216L - var4);
               byte[] var10 = a(var7, var8);
               if (var10 == null) {
                  var3++;
               } else {
                  var1.update(a);
                  var1.update(var10);
                  var2++;
                  var4 += var10.length;
               }
            }
         }
      }

      return new c.a(HexFormat.of().formatHex(var1.digest(), 0, 6), var2, var4, var3);
   }

   private static byte[] a(b.a var0, long var1) {
      try (InputStream var3 = var0.a().open()) {
         ByteArrayOutputStream var4 = new ByteArrayOutputStream();
         byte[] var5 = new byte[8192];
         long var6 = 0L;

         int var8;
         while ((var8 = var3.read(var5)) != -1) {
            int var9 = (int)Math.min(var8, var1 - var6);
            if (var9 > 0) {
               var4.write(var5, 0, var9);
            }

            var6 += var8;
            if (var6 >= var1) {
               break;
            }
         }

         return var4.toByteArray();
      } catch (Throwable var12) {
         return null;
      }
   }

   private static void a(MessageDigest var0, String var1) {
      var0.update(a);
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28hdzh0v80u61","L4HOY1RcYXuP9cdbvi2C27pqRM53hkiCDBpBp+oWn+4=",-4956986045486420072,-1224797658329403490,1132902556966025130,-8686333965043373326>()) {
            case 552771457:
               var0.update(var1.getBytes(StandardCharsets.UTF_8));
               switch ((int)com.yiyiaddon.m.b.a<"scz7mrebwnsnk","qUdcPVIGZJmpUtbZmf5fSVeIEqYTSEjVxnrW4imkzwU=",-3433011481453613387,7210070694772296845,7235784794688242412,3942761259080445762>()) {
                  case -2044792651:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public record a(String Eh, int sD, long aK, int sE) {
      public boolean fv() {
         if (this.sE == 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s3qo20fp9xzxp4","bbdjXVTXv6cd5Huuc7+M/kBYDK154X1OKs8LyOmMU60=",3102101080832532970,-7383115860974574432,478637646809840656,-5322021394044105849>()) {
               case 2080684924:
                  switch ((int)com.yiyiaddon.m.b.a<"s1qwllw1wspag4","hHZcdLdZticsJdvSEeGriDd+QAuCHCbVPQq49H6jpPw=",-5072472071617366454,4394060413506182900,244384662970740657,6306076099381853690>()) {
                     case -314715324:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3rmhlyljgplwg","9a4Q0hujcZh3Ef6i3+ZMyjMgekMPq2sfhaZxxBkIHY0=",5989193033536203893,-1425241702099521122,7948388527614499955,2284121438334998787>()) {
               case -632700545:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public String bi() {
         return this.Eh;
      }

      public int dA() {
         return this.sD;
      }

      public long y() {
         return this.aK;
      }

      public int dB() {
         return this.sE;
      }
   }
}
