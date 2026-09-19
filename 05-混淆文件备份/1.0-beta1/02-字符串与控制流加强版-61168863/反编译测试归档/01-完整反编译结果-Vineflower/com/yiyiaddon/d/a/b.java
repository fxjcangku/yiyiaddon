package com.yiyiaddon.d.a;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class b {
   private static final Map<c, Map<String, Consumer<a>>> d = new EnumMap<>(c.class);
   private static volatile BiConsumer<String, Throwable> a = (var0, var1) -> {};

   private b() {
   }

   public static e a(String var0, c var1, Consumer<a> var2) {
      if (var0 != null && !var0.isBlank() && var1 != null && var2 != null) {
         synchronized (b.class) {
            d.get(var1).put(var0, var2);
         }

         return new e(var0, var1);
      } else {
         return e.a();
      }
   }

   static void a(String var0, c var1) {
      if (var0 != null && var1 != null) {
         synchronized (b.class) {
            d.get(var1).remove(var0);
         }
      }
   }

   public static void h(String var0) {
      if (var0 != null && !var0.isBlank()) {
         synchronized (b.class) {
            for (Map var3 : d.values()) {
               var3.remove(var0);
            }
         }
      }
   }

   public static boolean a(String var0, c var1) {
      if (var0 != null && var1 != null) {
         synchronized (b.class) {
            return d.get(var1).containsKey(var0);
         }
      } else {
         return false;
      }
   }

   public static int a(c var0) {
      if (var0 == null) {
         return 0;
      }

      synchronized (b.class) {
         return d.get(var0).size();
      }
   }

   public static int e() {
      int var0 = 0;
      synchronized (b.class) {
         for (Map var3 : d.values()) {
            var0 += var3.size();
         }

         return var0;
      }
   }

   public static void c(a var0) {
      if (var0 != null) {
         ArrayList var1;
         synchronized (b.class) {
            Map var3 = d.get(var0.a());
            if (var3.isEmpty()) {
               return;
            }

            var1 = new ArrayList(var3.entrySet());
         }

         for (Entry var8 : var1) {
            try {
               ((Consumer)var8.getValue()).accept(var0);
            } catch (Throwable var5) {
               a((String)var8.getKey(), var5);
            }
         }
      }
   }

   public static void a(BiConsumer<String, Throwable> var0) {
      BiConsumer var10000;
      if (var0 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s3qvq4mw5te63q","0BFx9b0eve/fSwn7KO/sjomqE3Cz6vFwKiSDKDmvMZ0=",-2693343959633199999,6001984308409896642,-8449217634341187065,-5048783620079494608>()) {
            case 1139717292:
               var10000 = (var0x, var1) -> {};
               switch ((int)com.yiyiaddon.m.b.a<"s3kv83v1se497y","Nlg/Ac/xFE2IoQeDy66psS8FeQ6/DHlYFPEBejeHAuo=",-6459100919310027030,3962609636586883925,410403456559113828,-3113802702479054740>()) {
                  case 57942867:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0;
         switch ((int)com.yiyiaddon.m.b.a<"s1632w7dhi9r5h","cf1Pe7OL4TPHeQRFE817apHjlrYCvxgYTscOqxMVaVk=",-1861054250163862323,8936547521377257716,-8910921577408903357,-3817332290684347883>()) {
            case 445360326:
               break;
            default:
               throw null;
         }
      }

      a = var10000;
   }

   public static void b() {
      synchronized (b.class) {
         for (Map var2 : d.values()) {
            var2.clear();
         }
      }
   }

   private static void a(String var0, Throwable var1) {
      BiConsumer var2 = a;

      try {
         var2.accept(var0, var1);
      } catch (Throwable var4) {
      }
   }

   static {
      for (c var3 : c.values()) {
         d.put(var3, new LinkedHashMap<>());
      }
   }
}
