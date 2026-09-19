package com.yiyiaddon.e.e.b;

import com.yiyiaddon.m.b;
import java.util.Arrays;

public enum a {
   范围自动扫描,
   准星精准指向;

   public String h() {
      return this.name();
   }

   public static String[] b() {
      return Arrays.stream(values()).map(a::h).toArray(String[]::new);
   }

   public static a a(String var0) {
      a[] var1 = values();
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)b.a<"s3o4wq7o28txb1","2mQCd7pLf03n9DDRRCLt+y4M4A9mgAWOxz+WfrEXQBU=",6986489504976365378,-2159680123857279014,-8307349272225314677,8721805268583832298>()) {
         case -1367232942:
            while (var3 < var2) {
               switch ((int)b.a<"s30dr2utsx13zp","9qqD9AbI/K9uAqBlGs02x4RkrKkgc/GztXMmB7dNqtQ=",-7313510555329020426,-7206507248218080746,400334389683879397,-7206714732670147259>()) {
                  case -1393032970:
                     a var4 = var1[var3];
                     if (var4.name().equals(var0)) {
                        switch ((int)b.a<"sjozyb1fendp0","4nTleVudCxbtDdO9Jnsf6JYX/4A4v1PLb7hLZXq6ZEc=",-4219398451351617641,-501924100635047095,-2939081658580092683,-6662782190533942873>()) {
                           case 834923963:
                              return var4;
                           default:
                              throw null;
                        }
                     }

                     var3++;
                     switch ((int)b.a<"s1wa2prh87j5h8","zO3+gIyKbYe85yunsuMBPNI/VZjCytPPTRsf9JhJPdI=",-4403964227137161156,-7559628130749707723,745279081218175899,4639469216773400465>()) {
                        case 926990149:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return null;
         default:
            throw null;
      }
   }
}
