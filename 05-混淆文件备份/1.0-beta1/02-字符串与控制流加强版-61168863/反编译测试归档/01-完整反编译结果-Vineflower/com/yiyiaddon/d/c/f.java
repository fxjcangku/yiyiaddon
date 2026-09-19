package com.yiyiaddon.d.c;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class f {
   public static final int o = 0;
   private static final Map<String, f.b> h = new LinkedHashMap<>();
   private static long j = 0L;
   private static volatile f.a[] a = new f.a[0];

   private f() {
   }

   public static synchronized void a(String var0, h var1) {
      a(var0, 0, var1);
   }

   public static synchronized void a(String var0, int var1, h var2) {
      if (var0 != null && !var0.isBlank() && var2 != null) {
         h.put(var0, new f.b(var0, var1, j++, var2));
         u();
      }
   }

   public static synchronized void l(String var0) {
      if (var0 != null && h.remove(var0) != null) {
         u();
      }
   }

   public static synchronized void b() {
      if (!h.isEmpty()) {
         h.clear();
         u();
      }
   }

   public static synchronized boolean l(String var0) {
      return var0 != null && h.containsKey(var0);
   }

   public static synchronized int n() {
      return h.size();
   }

   static d a(i var0) {
      f.a[] var1 = a;

      for (f.a var5 : var1) {
         d var6;
         try {
            var6 = var5.a().decide(var0);
         } catch (Throwable var8) {
            continue;
         }

         if (var6 != null && !var6.l()) {
            return var6;
         }
      }

      return d.a();
   }

   private static void u() {
      ArrayList var0 = new ArrayList<>(h.values());
      var0.sort(
         (var0x, var1x) -> {
            if (var0x.o() != var1x.o()) {
               switch ((int)com.yiyiaddon.m.b.a<"sgh47p8mc16cm","5YvNjfwF8CHFSQIdF0UiC1GMhRAtGZc+4hrTZtUoZ5A=",-3705077134578926006,-7377760928273423505,-8273823664396312897,3176834575905661577>()) {
                  case 998132040:
                     int var10000 = Integer.compare(var1x.o(), var0x.o());
                     switch ((int)com.yiyiaddon.m.b.a<"s32m3r1geyb3xp","/jJ2LCHEyQDPw1XMX9VPA8Mi8faayQdl2aMdYJAl2DY=",2013757381937995655,7608056280084813638,334909023923374966,6704458654624336949>()) {
                        case 320229813:
                           return var10000;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               int var2x = Long.compare(var0x.f(), var1x.f());
               switch ((int)com.yiyiaddon.m.b.a<"s2hn8ym1ocaz7o","UWNeKDbyCm7QRJjkJ28m0QUlSxSNz+kjek6Nxeu5EiM=",8710444281917279321,-5698198463192265885,-749432528975374198,-5517436043011085901>()) {
                  case -528098466:
                     return var2x;
                  default:
                     throw null;
               }
            }
         }
      );
      f.a[] var1 = new f.a[var0.size()];
      int var2 = 0;
      Iterator var3 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s5a9e1m42c8rx","4dy4S9hb7AjoBzt/pZdZ0GulAYJq3Tvczy3iYExJ6T0=",-5435170265503483715,-6822930308427647595,8993927388924422164,4135631101090621376>()) {
         case -1659835865:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s25zfg4wsell12","JW9VtrENkch/NGDq/rXLi4guggAgGtCiaI/jUBTIsZ0=",-2371595524549783527,4618231933221825714,175625854110485666,4228782936824129678>()) {
                  case 137662772:
                     f.b var4 = (f.b)var3.next();
                     var1[var2++] = new f.a(var4.q(), var4.a());
                     switch ((int)com.yiyiaddon.m.b.a<"s2svv94omq5s3g","G5luZohtcVRqudInyQj5zbVqWV2QUtWzD0qMIqf28Ww=",-544472011035244019,4309960151040857738,1379359410867417383,-1618881237831532822>()) {
                        case -1651897270:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            a = var1;
            return;
         default:
            throw null;
      }
   }

   private record a(String ao, h a) {
      public String q() {
         return this.ao;
      }
   }

   private record b(String ap, int p, long k, h b) {
      public String q() {
         return this.ap;
      }

      public int o() {
         return this.p;
      }

      public long f() {
         return this.k;
      }

      public h a() {
         return this.b;
      }
   }
}
