package com.yiyiaddon.e.h.b;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.f;
import com.yiyiaddon.g.c.c;
import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class a {
   private static final String kI = (String)b.a<"s39b6klo00p922","7OJm/wUCJC3ycHwe+fHoRcWDnVyQ8zixwA7U3wYR56UxIW/z",4277645199336488558,2230922826193186923,-8920219589051680608,8045502043532667186>();
   private static final String kJ = (String)b.a<"s1y3u9tevon8wq","GekkTW8P87lhhsItzt0gHkGz8PBP8M0LPDgxhQ6DAVDIBZ7HoHD6ow==",-1260496510752314295,-7072445790885282151,4488550012985045135,-3111670968869579514>();
   private c a = c.AUTO_SAVE;
   private boolean bv;

   public c b() {
      return this.a;
   }

   public void a(c var1) {
      c var10001;
      if (var1 == null) {
         label15:
         switch ((int)b.a<"s1xzu0gper0m","bv6S0MRi1tGgl+36oSnJ1P0h7wfnOiAjo/NXdcj5USk=",4997267328765686223,-8190526172757440649,5363658676778003241,3612177922463813434>()) {
            case -2031526130:
               var10001 = c.AUTO_SAVE;
               switch ((int)b.a<"sdqyh71rykyo1","kz2Z0d+ef2zwYUf2YGDeDXnUQ2qhsrjhjVoDeE4eMUY=",-4137670937769901156,-1961045170442555448,-5318336769571872276,-1753484361520000554>()) {
                  case 620900066:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1;
         switch ((int)b.a<"sldr0o8whxmk6","vA9/E1RJ8PlVd8hUFQuAucUcqlDWmuMbFdynOcxAPtY=",4517217699311520346,-2725629242444650640,-7465778863719268838,-3007938998624964431>()) {
            case 1496670106:
               break;
            default:
               throw null;
         }
      }

      this.a = var10001;
   }

   public boolean bd() {
      return this.bv;
   }

   public void i(boolean var1) {
      this.bv = var1;
   }

   public List<String> aa() {
      ArrayList var1 = new ArrayList();
      c[] var2 = c.values();
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)b.a<"s1336hmecfht9z","YmJTTMfbkf2ovmgpcmjrZmJg5R6DYsm36wE2PLFPwDI=",-5270570588201853340,3412209659301187259,-4113100036820169577,1445251413159073358>()) {
         case -371654988:
            while (var4 < var3) {
               switch ((int)b.a<"s2oyyrmo3ntls4","CX75avzGpTIzEHJAALrZf6sP6/m1XWuNgtFgXANLodU=",-9067455612154821301,7224504760627373376,4651094498772015166,5194301801290587167>()) {
                  case -1073016744:
                     c var5 = var2[var4];
                     var1.add(var5.m());
                     var4++;
                     switch ((int)b.a<"s3q52aviaoarnq","MYS5NeLEUw+K8nR7vSYIHZ9HURH3IvTE9HvSmlJQVHQ=",-574224483201825083,-6244995516570435034,6822021969773560759,6983104716080166800>()) {
                        case 1212527964:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }

   public int aK() {
      return this.a.ordinal();
   }

   public void r(int var1) {
      c[] var2 = c.values();
      this.a(var2[Math.floorMod(var1, var2.length)]);
   }

   public void d(JsonObject var1) {
      this.a = a(
         f.a(
            var1,
            (String)b.a<"s39b6klo00p922","7OJm/wUCJC3ycHwe+fHoRcWDnVyQ8zixwA7U3wYR56UxIW/z",4277645199336488558,2230922826193186923,-8920219589051680608,8045502043532667186>(),
            this.a.name()
         )
      );
      this.bv = f.a(
         var1,
         (String)b.a<"s1y3u9tevon8wq","GekkTW8P87lhhsItzt0gHkGz8PBP8M0LPDgxhQ6DAVDIBZ7HoHD6ow==",-1260496510752314295,-7072445790885282151,4488550012985045135,-3111670968869579514>(),
         this.bv
      );
   }

   public void c(JsonObject var1) {
      var1.addProperty(
         (String)b.a<"s39b6klo00p922","7OJm/wUCJC3ycHwe+fHoRcWDnVyQ8zixwA7U3wYR56UxIW/z",4277645199336488558,2230922826193186923,-8920219589051680608,8045502043532667186>(),
         this.a.name()
      );
      var1.addProperty(
         (String)b.a<"s1y3u9tevon8wq","GekkTW8P87lhhsItzt0gHkGz8PBP8M0LPDgxhQ6DAVDIBZ7HoHD6ow==",-1260496510752314295,-7072445790885282151,4488550012985045135,-3111670968869579514>(),
         this.bv
      );
   }

   private static c a(String var0) {
      if (var0 != null && !var0.isBlank()) {
         try {
            return c.valueOf(var0.trim().toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException var2) {
            return c.AUTO_SAVE;
         }
      } else {
         return c.AUTO_SAVE;
      }
   }
}
