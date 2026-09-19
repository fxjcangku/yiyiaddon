package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import java.util.List;
import java.util.function.Supplier;

public final class l implements g {
   public static final float fS = 48.0F;
   private static final float fT = 16.0F;
   private static final float fU = 11.0F;
   private static final float fV = 11.0F;
   private static final float fW = 12.0F;
   private static final float fX = 6.0F;
   private static final float fY = 14.0F;
   private static final float fZ = 18.0F;
   private static final float ga = 37.0F;
   private static final String EP = (String)com.yiyiaddon.m.b.a<"s1vizci4fp863n","yOe/ncFc8afx5LI9LwlinhcM0wBsB8hNvSoUEWmmdig1iX/2QlA=",4338367459982239913,-8466453015391508314,-6102995932780071584,2977642839095618467>();
   private final String EQ;
   private final List<l.a> db;

   public l(String var1, List<l.a> var2) {
      this.EQ = var1 == null
         ? (String)com.yiyiaddon.m.b.a<"sksf1vf4844l4","6hY6ZewOFWMBmLduNAqFO9VbVqa6rVE8lly2gg==",127882374526465788,-4983429725019125640,1204059224858916571,-6322530911206358121>()
         : var1;
      this.db = List.copyOf(var2);
   }

   @Override
   public float b() {
      return 48.0F;
   }

   @Override
   public void a(float var1) {
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      com.yiyiaddon.l.i.c var8 = com.yiyiaddon.l.i.c.a();
      float var9 = j.h(48.0F);
      j.a(var1, var2, var3, var4, 48.0F, var9, var8.uQ, 0.7F, com.yiyiaddon.l.i.c.y(var5));
      j.c(var1, var2, var3, var4, 48.0F, var9, var8.uX, var5, 0.1F);
      if (!this.EQ.isEmpty()) {
         label43:
         switch ((int)com.yiyiaddon.m.b.a<"s24e6dhgbzv93z","O7l4UI2dkREX/COGCADcyWr/pd+pCnb5rBzWLNvU8z8=",1766628905579788641,-7190541294970458996,5840800439122435649,-3933107931164154697>()) {
            case -1350855049:
               com.yiyiaddon.l.g.a.b(var1, this.EQ, var2 + 16.0F, d.c(var3 + 18.0F, 11.0F), 11.0F, j.a(var8.va, var5));
               switch ((int)com.yiyiaddon.m.b.a<"s2kx8tien4xukg","7NXdKR+UO5uXWyfnIUvf7lUUtUnVTWSHYM10oVD7yP4=",7459127537931488497,4603438244676975223,1323826528293024824,7813581326476802949>()) {
                  case 1861794747:
                     break label43;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.db.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"seypkr64q658d","BNsa+h3SUtokYsWiLBiT6Z39F9Jjdjgs+VcfBneuWxU=",-5523443983152313791,318872796745938382,7206604138375530262,-3785594198676932594>()) {
            case 313519370:
               return;
            default:
               throw null;
         }
      } else {
         float var10 = d.c(var3 + 37.0F, 12.0F);
         if (this.db.size() == 1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1scrcmtlkezqv","6E1g9yCdsA2aj3EmilQjaxK3uK8e1PJELCUu/dx4+j8=",7533599828481363627,-6255378539033183933,-3912099744075803831,-478819581508214587>()) {
               case 1198917418:
                  this.a(var1, this.db.get(0), var2 + 16.0F, var10, var4 - 32.0F, var5, var8);
                  return;
               default:
                  throw null;
            }
         } else {
            float var11 = (var4 - 32.0F) / this.db.size();
            int var12 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s3csfrpluz8qha","CbeqOcjd7JNbJdy8S17oLqbYS4duy4aeSSO1tQL1DeQ=",8977608355687212948,4799702111511568039,-6520018435099057356,-3519856765281795720>()) {
               case 2126052485:
                  while (var12 < this.db.size()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2yz0o4jshfv56","JSqUgbUCiBrvzc7eNfcm0c2bS4JosOuAY9vxptk4rXU=",7426006012850607304,-3650349588180664289,-1132440474714884222,1518597099081295173>()) {
                        case 2052907884:
                           l.a var13 = this.db.get(var12);
                           float var14 = var2 + 16.0F + var12 * var11;
                           float var15 = com.yiyiaddon.l.g.a.b(var13.ER, 11.0F);
                           com.yiyiaddon.l.g.a.b(var1, var13.ER, var14, var10, 11.0F, j.a(var8.va, var5));
                           float var16 = var14 + var15 + 6.0F;
                           float var17 = var11 - var15 - 6.0F - 14.0F;
                           com.yiyiaddon.l.g.a.c(var1, d.a(b(var13.j), Math.max(0.0F, var17), 12.0F), var16, var10, 12.0F, j.a(var8.uT, var5));
                           var12++;
                           switch ((int)com.yiyiaddon.m.b.a<"s1493lpx0k5wkf","ndgWutdhM0rd8Y99jgtzJA5Rl1EB04ADQle7gwNgXF4=",-6983878810716596861,-1030812443636098698,5147773203366377277,-5779257160742519291>()) {
                              case 1854146694:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               default:
                  throw null;
            }
         }
      }
   }

   private void a(Canvas var1, l.a var2, float var3, float var4, float var5, float var6, com.yiyiaddon.l.i.c var7) {
      String var8 = b(var2.j);
      if (var2.ER.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1wmj0acob91gw","CxbQfsnUAqR7vIMd6fBJeHGka4mlPZnfg1KuzaEyn2U=",795553883910021483,-7469927067479304862,-3966607348498153728,5350701106437008125>()) {
            case -1516720908:
               com.yiyiaddon.l.g.a.c(var1, d.a(var8, var5, 12.0F), var3, var4, 12.0F, j.a(var7.uT, var6));
               return;
            default:
               throw null;
         }
      } else {
         float var9 = com.yiyiaddon.l.g.a.b(var2.ER, 11.0F);
         com.yiyiaddon.l.g.a.b(var1, var2.ER, var3, var4, 11.0F, j.a(var7.va, var6));
         float var10 = var3 + var9 + 6.0F;
         com.yiyiaddon.l.g.a.c(var1, d.a(var8, Math.max(0.0F, var5 - var9 - 6.0F), 12.0F), var10, var4, 12.0F, j.a(var7.uT, var6));
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      return false;
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5) {
      return false;
   }

   private static String b(Supplier<String> var0) {
      try {
         String var1 = (String)var0.get();
         return var1 == null
            ? (String)com.yiyiaddon.m.b.a<"sksf1vf4844l4","6hY6ZewOFWMBmLduNAqFO9VbVqa6rVE8lly2gg==",127882374526465788,-4983429725019125640,1204059224858916571,-6322530911206358121>()
            : var1;
      } catch (Throwable var2) {
         return (String)com.yiyiaddon.m.b.a<"s1vizci4fp863n","yOe/ncFc8afx5LI9LwlinhcM0wBsB8hNvSoUEWmmdig1iX/2QlA=",4338367459982239913,-8466453015391508314,-6102995932780071584,2977642839095618467>();
      }
   }

   public static final class a {
      private final String ER;
      private final Supplier<String> j;

      private a(String var1, Supplier<String> var2) {
         this.ER = var1 == null
            ? (String)com.yiyiaddon.m.b.a<"s1zzlvkk51874v","K/Tby/srnZdDBdcJVc5+lHYRMaJbhtSORenbpQ==",6748575345920827355,3389927574430065523,-6298322991770576237,-4316575442766876586>()
            : var1;
         this.j = var2;
      }

      public static l.a a(Supplier<String> var0) {
         return new l.a(
            (String)com.yiyiaddon.m.b.a<"s1zzlvkk51874v","K/Tby/srnZdDBdcJVc5+lHYRMaJbhtSORenbpQ==",6748575345920827355,3389927574430065523,-6298322991770576237,-4316575442766876586>(),
            var0
         );
      }

      public static l.a a(String var0, Supplier<String> var1) {
         return new l.a(var0, var1);
      }
   }
}
