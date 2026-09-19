package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import java.util.function.Supplier;

public class l extends p {
   private static final float ob = 200.0F;
   private static final float oc = 11.0F;
   private static final String Hc = (String)com.yiyiaddon.m.b.a<"srh42q5240z9j","eLsS4NC2rsHR9dP7ihEdTLa3LzrG2mzq6n4BxPJJsq2XiLCGuGg=",4672689418143342780,-4031503311342815551,-4745250477073251621,-6250762669974156750>();
   private final Supplier<String> L;
   private final float od;
   private final Supplier<Float> M;
   private boolean gL;

   public l(Supplier<String> var1) {
      this(var1, 200.0F, null);
   }

   public l(Supplier<String> var1, float var2) {
      this(var1, var2, null);
   }

   public l(Supplier<String> var1, Supplier<Float> var2) {
      this.L = var1;
      this.od = 200.0F;
      this.M = var2;
   }

   private l(Supplier<String> var1, float var2, Supplier<Float> var3) {
      this.L = var1;
      this.od = var2;
      this.M = var3;
   }

   public l a() {
      this.gL = true;
      return this;
   }

   @Override
   public float c() {
      if (this.M == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1h48b4vrobnd7","x5M/vajxjjIXrV+JwmSy8jwpc/mLkHdgzmqkOy2k8Jc=",4528932128038370552,8569335413142645837,-3316537642254284862,6823845348987675958>()) {
            case 1361911602:
               return this.od;
            default:
               throw null;
         }
      } else {
         Float var1 = this.M.get();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1oj9kcgeepwb5","dwaMPIynRbjCxnm4MA+sdatPVEF3I4v1GZZpD0c7TG0=",-1627143972642942427,-3619785229182044874,-1781801597986654304,-1114222013391353262>()) {
               case 785554759:
                  float var10000 = this.od;
                  switch ((int)com.yiyiaddon.m.b.a<"s22ryjj6ygwvr6","wldRqsXm7p6FWWaZUG/gSoMAPVZ04I7WY93GuSUWZ6E=",-1826380124331142818,-5335183957709791014,8773482270347560266,-6835807523053777449>()) {
                     case -2071618405:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            float var2 = Math.max(1.0F, var1);
            switch ((int)com.yiyiaddon.m.b.a<"sserollbw8no7","r4fWu5UMyzh0vMhpaQbwJhyPxRkp1J5XKhvAHM+Pg8Q=",3882301405346573413,-4909687645313747886,-5713386294397316341,-6537453670430684212>()) {
               case 351886769:
                  return var2;
               default:
                  throw null;
            }
         }
      }
   }

   @Override
   public float d() {
      return 20.0F;
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      String var5 = this.gW();
      if (var5.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"sjbil0pwrykps","1USSYYFcumybe8ERoiKXHw+MXOWbowPwwgShdKCwJcE=",1301879588850068790,2479301296958275371,-6134281565503115817,-6240953411233357851>()) {
            case 737284667:
               return;
            default:
               throw null;
         }
      } else {
         float var6 = com.yiyiaddon.l.g.d.a(var5, 11.0F, false);
         if (var6 > this.od) {
            label32:
            switch ((int)com.yiyiaddon.m.b.a<"s3qzj58pg10nau","ZdlKXsY6jErSlAwEJEJOIKdR23hKorXrsPYlcyqYrmY=",1083779737858408845,-6256077969881700840,-8711603978386750297,5477520725799097372>()) {
               case 1847400780:
                  var5 = com.yiyiaddon.l.b.d.a(com.yiyiaddon.l.g.d.cn(var5), this.od, 11.0F);
                  var6 = com.yiyiaddon.l.g.a.b(var5, 11.0F);
                  switch ((int)com.yiyiaddon.m.b.a<"s2w3qkc3cpk1on","iF9S9eg+Hq5cDbEQEH5edTqurZvKG+erxPLfE4t+66o=",4226879289087103037,-4589434238844401739,-3984862383218419335,122280268987948095>()) {
                     case -2004442885:
                        break label32;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         float var10002;
         if (this.gL) {
            label25:
            switch ((int)com.yiyiaddon.m.b.a<"s2qh15iptlljbq","yCau82BmhHJiIcvT8/yCrnck4Fd1LtNB4KHqXgzY/Hk=",-653306419489258665,6851207170935274285,-5865848968583388501,297576280028778288>()) {
               case 1933919499:
                  var10002 = var2;
                  switch ((int)com.yiyiaddon.m.b.a<"s3qmbvvr76en3","PLrGzVaqhGgNUMJJ8bl8eH+/3jOPD26jLg40hRIBIIc=",-1488614806437005971,8300886056757381567,-4307881654273194979,-8189788113964239121>()) {
                     case -2144297681:
                        break label25;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10002 = var2 + this.od - var6;
            switch ((int)com.yiyiaddon.m.b.a<"s288hjqo993i6n","wgqrlIl0gKtg3kkjs3z2kWKf6Wft0QR9SOSDc6leR9w=",3223505447922509259,-7502583279223743724,6502551223690524621,-3938557044189225918>()) {
               case -1016089403:
                  break;
               default:
                  throw null;
            }
         }

         com.yiyiaddon.l.g.d.a(var1, var5, var10002, var3 + 14.0F, 11.0F, com.yiyiaddon.l.i.c.a().uU, var4);
      }
   }

   private String gW() {
      try {
         String var1 = this.L.get();
         return var1 == null
            ? (String)com.yiyiaddon.m.b.a<"s1jw9mxztxeggh","pvPbmtj6UQz5LvdVfKO7yf7TRxWYcxcfYUidWQ==",4459931916773078404,-3688692201718621288,6148637597343640651,-6708559648647749181>()
            : var1;
      } catch (Throwable var2) {
         return (String)com.yiyiaddon.m.b.a<"srh42q5240z9j","eLsS4NC2rsHR9dP7ihEdTLa3LzrG2mzq6n4BxPJJsq2XiLCGuGg=",4672689418143342780,-4031503311342815551,-4745250477073251621,-6250762669974156750>();
      }
   }
}
