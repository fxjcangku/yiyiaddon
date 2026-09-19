package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class e extends p {
   private final List<String> dC;
   private final Supplier<Integer> F;
   private final Consumer<Integer> s;
   private final com.yiyiaddon.l.a.b h = new com.yiyiaddon.l.a.b();
   private final Paint H = new Paint().setAntiAlias(true);
   private int vL = Integer.MIN_VALUE;
   private String GK = (String)com.yiyiaddon.m.b.a<"s3igiemfu8b74","f2CrydYT4L8rqv8l66Bpo+Jr+T5fsCZ4Vk+tJw==",7750990194344713084,2681760309024866506,5276682713542247992,3313528281293456620>();
   private float nl = 0.0F;

   public e(List<String> var1, Supplier<Integer> var2, Consumer<Integer> var3) {
      this.dC = var1;
      this.F = var2;
      this.s = var3;
   }

   @Override
   public float c() {
      return 92.0F;
   }

   @Override
   public float d() {
      return 24.0F;
   }

   @Override
   public void a(float var1) {
      this.h.a(var1);
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      int var5 = this.F.get() % this.dC.size();
      if (var5 != this.vL) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"szwjgybj16x8u","q6GA3z0ag/+mpfG8X1+uJ19gQgqYuD4Ywngm29MfUFY=",-7669107849031322953,-7522107307453717973,4997518198918028898,1167737592100854538>()) {
            case 1231981158:
               this.vL = var5;
               this.GK = this.dC.get(var5);
               this.nl = com.yiyiaddon.l.g.a.b(this.GK, 12.0F);
               switch ((int)com.yiyiaddon.m.b.a<"s16w8wzjpoy4vp","cscpv0clBHEW0en7y48JFgQXtiJQrtJXLcUvAENpIog=",-3723131446337384592,-2855370398570278088,-5876217314588512656,71310830746638213>()) {
                  case -1507734495:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
      this.H.setColor(a(var6.vd, com.yiyiaddon.l.i.c.y(var4)));
      boolean var7 = this.h.a(var1, var2, var3, this.c(), this.d());
      var1.drawRRect(RRect.makeXYWH(var2, var3, this.c(), this.d(), 6.0F), this.H);
      com.yiyiaddon.l.g.a.b(var1, this.GK, var2 + (this.c() - this.nl) / 2.0F, var3 + 16.0F, 12.0F, a(var6.vh, var4));
      if (var7) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ypnuk1yb9ym8","dxvrKQKTm3OPBI7GsdRa1k6/RRiyGin/bG3MsA7WYpg=",7122917459990919441,3365390890637127243,-5420537048251481847,-7208270567016503546>()) {
            case 979278341:
               var1.restore();
               switch ((int)com.yiyiaddon.m.b.a<"s1b2g7obkr26rl","OdHOyqxOW0KZRbhzNuR8B0oBQLcdVvRqgItItjmpYIg=",6884906785957694910,4137789492458732511,4417287730214875907,6389608239533099633>()) {
                  case 1183861055:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean cB() {
      if (!this.h.fP()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3exh2oinwfgzw","z9oySSpQ9MHaEitMrwddyKmyYTRjYpBLCYe1C9VjdAQ=",-3300429918644660598,9059623178822713661,4210142789183428630,-291157694267121980>()) {
            case -1468307639:
               switch ((int)com.yiyiaddon.m.b.a<"s19hr7zwoc92wr","pPGDO2tGVwQRquXTtHhKxkBX3t93XUPkz9LQqwPhWfA=",7573557258052969692,-7202247411586694815,7947021465827370295,-37844042521411683>()) {
                  case -113669503:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2y3p0da0ckn6i","emIvLqDxtJu4uSUIcgoDX7107kx3AhlAZ58Dv6s3dvQ=",2739857173834493338,4296188264105149383,-1579413394828751284,7338924603959399332>()) {
            case -407470329:
               return false;
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, int var5) {
      if (var5 != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"sh0isbc0yffky","q6SM7wljQ5XcMqQPnbWMbXDdwoC1o3dwrB9GYcXqIMg=",7393905353564348815,-2809402138601832621,-1894776359364425,-6080697688033727238>()) {
            case -166254297:
               return false;
            default:
               throw null;
         }
      } else {
         this.h.jL();
         this.s.accept((this.F.get() + 1) % this.dC.size());
         return true;
      }
   }
}
