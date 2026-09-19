package com.yiyiaddon.l.g.a;

import java.awt.Color;

public final class g {
   public static final double bP = 0.125;
   public static final double bQ = 4.0;

   private g() {
   }

   public static int a(double var0, double var2, int var4) {
      float var5 = (float)((r() * b(var0) + var2) % 1.0);
      if (var5 < 0.0F) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s1n3cn4aregfze","7BFTafLjeM5/mBQ7fe/xpFrrfbDvCrD22KCWcz0+ho0=",-3214647913151443136,3741871491322688633,6219339312162386874,-8637957639145993689>()) {
            case 719091006:
               var5++;
               switch ((int)com.yiyiaddon.m.b.a<"sod2c10w6516w","NzYEE9M4ZiZUOr0Mf/YqkCelMc60M/LIWTLD3tQhGBE=",-9008735745836537212,8100284193471565572,9021713120734642887,6193669873875191477>()) {
                  case 1355109747:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      int var6 = Color.HSBtoRGB(var5, 1.0F, 1.0F) & 16777215;
      return (var4 & 0xFF) << 24 | var6;
   }

   public static int t(int var0) {
      return a(0.125, 0.0, var0);
   }

   public static double b(double var0) {
      if (var0 < 0.0) {
         switch ((int)com.yiyiaddon.m.b.a<"s162fe0sp4hxdi","CpRVJPVVrNBK/huPrr8TESXiSAQTGqKBCuUkUG1N25s=",6808621079782749337,645105870945155187,1836525903951761062,-947343213849732610>()) {
            case -322056424:
               return 0.0;
            default:
               throw null;
         }
      } else {
         return Math.min(var0, 4.0);
      }
   }

   private static double r() {
      return System.nanoTime() / 1.0E9;
   }
}
