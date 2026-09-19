package com.yiyiaddon.h;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class b {
   private static final Comparator<c> b = Comparator.comparingInt(c::i).thenComparing(c::s);
   private static final Map<String, c> aT = new LinkedHashMap<>();
   private static volatile List<c> cI;

   private b() {
   }

   public static synchronized void a(c var0) {
      if (var0 != null && var0.s() != null && !var0.s().isBlank()) {
         aT.put(var0.s(), var0);
         cI = null;
      }
   }

   public static synchronized c a(String var0) {
      return var0 == null ? null : aT.get(var0);
   }

   public static boolean E(String var0) {
      if (a(var0) != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2xgkxqr6xnz45","WVDv1XV5b0gvZqMkvk3xukD7fFnaNyxiJiBtsgQRCtI=",-7040826243478922835,512124192701816096,4211292781049535028,-6116697139069034120>()) {
            case -1008541198:
               switch ((int)com.yiyiaddon.m.b.a<"s1aa2wze4rf1we","ikLw8BhcpFW7sz4W/xrtMgOaI644Yosl66kkFiYV0A8=",-4953411605398328710,-7093495448049248373,-5539365784743261518,1512271024894744584>()) {
                  case 365066345:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2rrot1hxtf9ut","fYC7kVbaNt6lUNCdCfU9blcjx4gTzN4L12JxuSz+5kk=",1471708428814332173,-5844364675807104610,-6096755189363737053,6003161217912153254>()) {
            case 642081139:
               return false;
            default:
               throw null;
         }
      }
   }

   public static List<c> c() {
      List var0 = cI;
      if (var0 != null) {
         return var0;
      }

      synchronized (b.class) {
         if (cI == null) {
            ArrayList var2 = new ArrayList<>(aT.values());
            var2.sort(b);
            cI = List.copyOf(var2);
         }

         return cI;
      }
   }

   public static int b() {
      return c().size();
   }

   public static synchronized void b() {
      aT.clear();
      cI = null;
   }
}
