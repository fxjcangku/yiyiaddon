package com.yiyiaddon.l.g.a;

import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class l {
   private static final Map<String, l.a> bs = new ConcurrentHashMap<>();
   private static final Set<String> ba = ConcurrentHashMap.newKeySet();
   private static final Logger w = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s2jt09dty8e08q","YTf+Q8DxXvm5Zofy5u0tp+r/hijhjzOyMY3pR2cK1zdCbCuJKpfr9cRZTKuEeZD1EpTx1WdT3soajoiCHJaTBxQ6KjS05WAw82s=",-8105511550639174447,-2782297596624526425,1971018516074472511,8316769353147299740>()
   );
   private static final com.yiyiaddon.l.g.g b = new com.yiyiaddon.l.g.g();
   private static volatile boolean go;
   private static final List<f.a> dx = new ArrayList<>();
   private static boolean gp;

   private l() {
   }

   public static void a(String var0, l.a var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dqmh7gkmsqrx","sNohYDIUIcz8Ld+8AIbAuQRQ13k2Nb0KcLwL1QAxozY=",-8027795625899182766,201405073284429290,-7269183816266701192,-1849579770920706245>()) {
            case -1203431490:
               if (!var0.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1my5q7o25js8o","cq0uQnFqyXx8bfuB2Gi738C2uoT/V5oh906EustIr58=",1924349439451419401,-1713657010525771136,-3570707198360253299,-6277269169438146818>()) {
                     case 799378344:
                        if (var1 != null) {
                           bs.put(var0, var1);
                           ba.remove(var0);
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2hzxbxvsoyz1z","g9PQYjBV92NZ11ymh0E4CTe+mUM67ZpP2R1zqDdz1ho=",5907656216920848585,-852259421689168384,-8549462934203556832,8129231402080453438>()) {
                           case -1289839899:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }
   }

   public static void l(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2f3xmzznx7p12","Z9hH3j+jRtSDsSHRRGxaYU1rznC1vx5gtvvi7XOZgQE=",-3007002169854410580,-979451376802230545,-669881198911220939,-8527234735375437901>()) {
            case -1205491275:
               return;
            default:
               throw null;
         }
      } else {
         bs.remove(var0);
         ba.remove(var0);
      }
   }

   public static boolean l(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1nzw5ubb8rpe0","KrGTtwJ9zeNPNr4OgSsbw1pTjvGRClFx7HdYTWnx1Ac=",-267933131616555310,3676484310994876810,-5706155225794800334,-5801825074469085136>()) {
            case -1612172573:
               if (bs.containsKey(var0)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1y91jbc1ywchh","v+KKI2hetML77+tebIG9a0b1n8KXcs/dsIDG98O5cvo=",2596249327489636459,-494283746319931356,1560696816236648876,2149994234790949672>()) {
                     case 2035055210:
                        switch ((int)com.yiyiaddon.m.b.a<"s1397hal2qg6v","Q/asN5JbNil0BGyraUE1UdkYJbfk08w54Y3TglYSOh8=",-1080239585631854798,3370532293731737940,4956880115971113059,-1379724364914408594>()) {
                           case 135176385:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s3v8oz1vao2uyk","v3Kc2bvWJNzHJPQ8FBHF94Ev7AD5gA5NSAcseYnqlxg=",1577369784521500264,-7697346706703191202,4857186738379159517,-4833157696341474666>()) {
         case -1017378939:
            return false;
         default:
            throw null;
      }
   }

   public static void b() {
      bs.clear();
      ba.clear();
      dx.clear();
      gp = false;
   }

   public static int es() {
      return bs.size();
   }

   public static void kx() {
      dx.clear();
      gp = false;
      if (!bs.isEmpty()) {
         if (e.a().ar()) {
            h var0 = h.a();
            if (var0 != null) {
               f var1 = f.a(var0, dx);

               for (Entry var3 : bs.entrySet()) {
                  l.a var4 = (l.a)var3.getValue();
                  if (var4 != null) {
                     try {
                        var4.render(var1);
                     } catch (Throwable var6) {
                        if (ba.add((String)var3.getKey())) {
                           w.error(
                              (String)com.yiyiaddon.m.b.a<"s5at49q6sx6px","kYXfCOzpvN4fSxMpYWU7CrZP2FU78gMZtYNWXnpWxWbNS8pUkB/4uhUFY1DWY9ngHAUa2aQv",6083395057621759265,-5142913646962798104,-5499776333196645803,-806234324616159553>(),
                              var3.getKey(),
                              var6
                           );
                        }
                     }
                  }
               }

               gp = true;
            }
         }
      }
   }

   public static void ky() {
      if (gp) {
         gp = false;
         if (!dx.isEmpty()) {
            h var0 = h.a();
            if (var0 == null) {
               dx.clear();
            } else {
               Canvas var1 = b.a(com.yiyiaddon.l.g.g.ef());
               if (var1 == null) {
                  dx.clear();
               } else {
                  go = true;

                  try {
                     f var2 = f.a(var1, new m(var0), var0);
                     var2.t(dx);
                  } catch (Throwable var6) {
                     if (ba.add(
                        (String)com.yiyiaddon.m.b.a<"sxdnh9uc50h59","xCKhZX6zRj1ALqc+AgKbAJPi9hOJurlrqeF/thxKhuZVg1BguiKb5YVj",-7771636064817751138,-149567226060639128,3004540895528043194,515187719028689150>()
                     )) {
                        w.error(
                           (String)com.yiyiaddon.m.b.a<"s1k7qc98ld75fk","+z0kyAbg/XLicu+XGjvxds6fVwbZDa3OZ+kI9CGzATqUYIk/3pCAEM12e9YBnTNSTOM=",8079001709690180402,-4206443065483014878,7458426070833971702,-925940266837684828>(),
                           var6
                        );
                     }
                  } finally {
                     b.km();
                     dx.clear();
                  }
               }
            }
         }
      }
   }

   public static void kz() {
      if (!go) {
         switch ((int)com.yiyiaddon.m.b.a<"s1a9iehrwbajdk","jlkJTjIcqCyseDj82vjiwbCcIsqw/fOnWIwPaVfgzFQ=",5713521435147336159,4792427983103155357,8239139866991027104,-357500558644593144>()) {
            case 254862684:
               return;
            default:
               throw null;
         }
      } else {
         b.ko();
         go = false;
      }
   }

   static {
      LevelRenderEvents.BEFORE_GIZMOS.register(var0 -> kx());
   }

   @FunctionalInterface
   public interface a {
      void render(f var1);
   }
}
