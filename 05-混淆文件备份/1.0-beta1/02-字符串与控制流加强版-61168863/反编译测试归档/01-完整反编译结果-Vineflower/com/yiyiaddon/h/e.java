package com.yiyiaddon.h;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class e {
   private static final Map<String, d> aU = new LinkedHashMap<>();
   private static final Map<String, List<d>> aV = new HashMap<>();
   private static volatile List<d> cI;

   private e() {
   }

   public static synchronized void a(d var0) {
      if (var0 != null && var0.s() != null && !var0.s().isBlank()) {
         aU.put(var0.s(), var0);
         cy();
      }
   }

   public static synchronized d a(String var0) {
      return var0 == null ? null : aU.get(var0);
   }

   public static List<d> c() {
      List var0 = cI;
      if (var0 != null) {
         return var0;
      }

      synchronized (e.class) {
         if (cI == null) {
            ArrayList var2 = new ArrayList<>(aU.values());
            var2.sort(Comparator.<d>comparingInt(var0x -> q(var0x.u())).thenComparingInt(d::i).thenComparing(d::s));
            cI = List.copyOf(var2);
         }

         return cI;
      }
   }

   public static List<d> k(String var0) {
      if (var0 == null) {
         return List.of();
      }

      synchronized (e.class) {
         List var2 = aV.get(var0);
         if (var2 != null) {
            return var2;
         }

         ArrayList var3 = new ArrayList();

         for (d var5 : c()) {
            if (var0.equals(var5.u())) {
               var3.add(var5);
            }
         }

         List var8 = List.copyOf(var3);
         aV.put(var0, var8);
         return var8;
      }
   }

   public static int b() {
      return c().size();
   }

   public static int p(String var0) {
      return k(var0).size();
   }

   public static boolean a() {
      return c().isEmpty();
   }

   public static synchronized void b() {
      aU.clear();
      cy();
   }

   private static int q(String var0) {
      c var1 = b.a(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zlvl3qify1sc","ZZ/rnISIA6iX4mf+dolwcByEOGraXk5pfnd0iEfG8N8=",-7592119073212571285,-7491541316363622217,-9119407454073559790,658611595936406606>()) {
            case 876839544:
               switch ((int)com.yiyiaddon.m.b.a<"s1cevdqn32jccp","nQzKVe/rMTfOHsZYmAlBK/UtzLh7PoJLoKPFWvUkktI=",-2877821654533690968,2631535494188449449,7321509250100031211,-8903254123473576189>()) {
                  case -79794859:
                     return Integer.MAX_VALUE;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var10000 = var1.i();
         switch ((int)com.yiyiaddon.m.b.a<"s1h9wat1t9elri","ArvGsWmWJquC6odeqAmWSB5EXICSEeZMe3+icRUJc5E=",682529010071009765,6823191626353815334,531883253668648075,949575652256367802>()) {
            case 510144976:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static void cy() {
      cI = null;
      aV.clear();
   }
}
