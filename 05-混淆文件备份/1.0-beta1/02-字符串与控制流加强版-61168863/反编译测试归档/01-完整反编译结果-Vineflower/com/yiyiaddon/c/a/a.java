package com.yiyiaddon.c.a;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.g.c.d;
import com.yiyiaddon.m.b;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;

public final class a {
   private static final Gson b = new GsonBuilder().setPrettyPrinting().create();
   private static final String y = (String)com.yiyiaddon.m.b.a<"s1nv2sceeuof20","3Jndz3kdvmOJe0y+d82MyjOF4ruDiz7GABBnl9tBUI5lrL72pK6zvjtl3jCOplOGSpwQ61+YnIimCnu7C9uUNNRMS5NsydRWo3lAFv7lIoyVupvAfmx14q1G",715551450329639436,8835772679751073907,-5920257273378325817,9154977137915671746>();
   private static final String z = (String)com.yiyiaddon.m.b.a<"s2bxf6ko6jraqf","N2LC9oHbmsMfqiZnVWuKLgvUs4CH7HBB+9NCdTaI3YLwm1bN",3714156651629567876,-3420661137357505184,5606930544153889717,-58972819708939614>();
   private static final Set<String> a = new LinkedHashSet<>();
   private static boolean f;

   private a() {
   }

   private static Path a() {
      return FabricLoader.getInstance()
         .getConfigDir()
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s1nv2sceeuof20","3Jndz3kdvmOJe0y+d82MyjOF4ruDiz7GABBnl9tBUI5lrL72pK6zvjtl3jCOplOGSpwQ61+YnIimCnu7C9uUNNRMS5NsydRWo3lAFv7lIoyVupvAfmx14q1G",715551450329639436,8835772679751073907,-5920257273378325817,9154977137915671746>()
         );
   }

   public static void d() {
      if (!f) {
         f = true;
         Path var0 = a();
         if (Files.isRegularFile(var0)) {
            try {
               String var1 = Files.readString(var0, StandardCharsets.UTF_8);
               JsonElement var2 = JsonParser.parseString(var1);
               if (var2 == null || !var2.isJsonObject()) {
                  return;
               }

               JsonObject var3 = var2.getAsJsonObject();
               JsonElement var4 = var3.get(
                  (String)com.yiyiaddon.m.b.a<"s2bxf6ko6jraqf","N2LC9oHbmsMfqiZnVWuKLgvUs4CH7HBB+9NCdTaI3YLwm1bN",3714156651629567876,-3420661137357505184,5606930544153889717,-58972819708939614>()
               );
               if (var4 == null || !var4.isJsonArray()) {
                  return;
               }

               a.clear();

               for (JsonElement var6 : var4.getAsJsonArray()) {
                  if (var6.isJsonPrimitive()) {
                     a.add(var6.getAsString());
                  }
               }
            } catch (Exception var7) {
            }
         }
      }
   }

   public static void e() {
      JsonObject var0 = new JsonObject();
      JsonArray var1 = new JsonArray();

      for (String var3 : a) {
         var1.add(var3);
      }

      var0.add(
         (String)com.yiyiaddon.m.b.a<"s2bxf6ko6jraqf","N2LC9oHbmsMfqiZnVWuKLgvUs4CH7HBB+9NCdTaI3YLwm1bN",3714156651629567876,-3420661137357505184,5606930544153889717,-58972819708939614>(),
         var1
      );

      try {
         Path var5 = a();
         Files.createDirectories(var5.getParent());
         Files.writeString(var5, b.toJson(var0), StandardCharsets.UTF_8);
      } catch (Exception var4) {
      }
   }

   public static Set<String> b() {
      return new LinkedHashSet<>(a);
   }

   public static void a(Iterable<String> var0) {
      a.clear();
      if (var0 != null) {
         label36:
         switch ((int)com.yiyiaddon.m.b.a<"s19j9oqaqkvtp8","HlxzB9O39BQ4pPO3FKIx3glUhLYrDGWAlC3kEZE+ghU=",-6225386397502608822,92757013075531505,-4040567177897737885,3483768760473652070>()) {
            case 1964155523:
               Iterator var1 = var0.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s26hqsg1bsr8w5","imvZyy7vDq6trUmcP3Mg28FpBVZbJcDG4AJiFNrRJKM=",-89871060940904023,8882166898198491360,2397101966333900119,-4773723541497930902>()) {
                  case -1511529998:
                     while (var1.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3rsfvyfi2uhwo","XzZIihqzUUqLekgiRxfKh/jccN9Udncf6Lr7aqzdPL4=",-1654146708423134775,8848238936189382307,4082259079557299678,-7958123783033275844>()) {
                           case -1724392786:
                              String var2 = (String)var1.next();
                              if (var2 != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s23nah5eo4xqdi","4RaX3WmJ6VF5uaFijLvcQOpxNUB/48naFWPgvSU7U9w=",3357813189175362714,-5393453359220328077,1335816161006434811,-5336540942450731673>()) {
                                    case -1930115649:
                                       if (!var2.isBlank()) {
                                          label28:
                                          switch ((int)com.yiyiaddon.m.b.a<"spfh0qpab3uhw","9AxlAz4evmiTuzFtrBzvcV4Qc/lIrc576obUcQZsgFs=",7269750964619077615,8036460053950875046,3135814151055803371,1561487704616416182>()) {
                                             case 630904086:
                                                a.add(var2);
                                                switch ((int)com.yiyiaddon.m.b.a<"sodghfyqga1ay","wjdflQR5dtLmNfSXAhGvGHJ95gc/z6MiINHkBWqPop4=",5494317844762927928,-7256418980345935646,8905471306219173130,8379727895679916993>()) {
                                                   case -1124423806:
                                                      break label28;
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

                              switch ((int)com.yiyiaddon.m.b.a<"sif5ge3rkbz7k","PvHuMHVn5f9K/tm5kCIId6ypDpqueseOqx7RXDOfxA4=",6178455627553742872,-9032938850494458169,-6881980136995521606,-2235903122278352082>()) {
                                 case -1608380484:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break label36;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      e();
   }

   public static void b(String var0, boolean var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s12jmd4uwvyplc","tYxWn1XeSs1VTAH5oWVh1vap+Hm0QE3Ipo8pFvsjdTs=",2262675439514873141,-277248413540941015,-7598976412959883587,1892814053567560167>()) {
            case -1181915588:
               if (!var0.isBlank()) {
                  if (var1) {
                     label22:
                     switch ((int)com.yiyiaddon.m.b.a<"s36injy6j4odbg","USWXrr4H4qG0r+HwOXq+ZwITpyei0+mBCz8dmdlelpA=",4338831931436934208,6233090333411378412,-3858400214298557695,-7566136738266067621>()) {
                        case -632599647:
                           a.add(var0);
                           switch ((int)com.yiyiaddon.m.b.a<"s3pnc0y4nc71jt","tVp/5yRwP6D5mP+uH05/RZLi5vYgBgNuQXz+rVhON7s=",2987126013259375733,7203199653838996573,-2295872687218702633,-8288268300914354472>()) {
                              case 249082894:
                                 break label22;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     a.remove(var0);
                     switch ((int)com.yiyiaddon.m.b.a<"s200udavbyld5i","n/LEbyaGbzVytAcrA0rjSPJ8YQTQUo43AAQWyw2uirY=",-6140697021752030865,-5781046780355224351,3993475575609546779,-8685326283703132694>()) {
                        case -1118069508:
                           break;
                        default:
                           throw null;
                     }
                  }

                  e();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s15ro49cdcef21","497UN5OVhrxxD67rc9ehv+hDljIcJr9IlzcMkuSOM2M=",3784660642582820488,5644220571365177446,5280250444298623139,-3807024241900128845>()) {
                     case -1019420225:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   public static void f() {
      a.clear();
      e();
   }

   public static List<d> a(com.yiyiaddon.k.b.a var0) {
      return var0.a(b());
   }

   public static int a(com.yiyiaddon.k.b.a var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = a.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1dcsexi732dvw","bomSQQ3rxw2aNXPwhyFzwENkU5OxnD2BLOjTny1vvZ4=",7581429388366268404,640446833310396456,1130465442686340473,-7993348980839893029>()) {
         case 430306518:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3ne30aikq05nw","KSlxxkZvKr2MYGtir1q5xEGgWZcyhhnoJEWhTllY8rk=",-7012899419031684904,-1035425745418321618,-4429386720282723673,1108463596512704129>()) {
                  case -2040707286:
                     String var3 = (String)var2.next();
                     if (var0.c(var3) != null) {
                        label33:
                        switch ((int)com.yiyiaddon.m.b.a<"sexzqdmt0iw2v","wwnt5mgzACc1/2LmMnGDwK/9/ctuGoEAwVek8aDpCyY=",1506399433383413456,-4499441496056230241,6547010721184862491,2665786884255120701>()) {
                           case -686260433:
                              var1.add(var3);
                              switch ((int)com.yiyiaddon.m.b.a<"s1bey5yq9vxln3","/Nv6O8+AMLGwREdvyqUZoON80xQF6riSDQiNQEEmHno=",8323084052316829445,-5406527470001454450,7854163247912575577,-5982881311043595478>()) {
                                 case -1220749833:
                                    break label33;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3ig5hkhxvzwnz","Z0bMgXvswaUrhKiKP2JbgUnWzldjGsbUftvxLKCTAlA=",-420568309539019621,-1491319538849462553,-5168950824557734621,8651256629099339135>()) {
                        case 2098719026:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            int var4 = a.size() - var1.size();
            if (var4 > 0) {
               switch ((int)com.yiyiaddon.m.b.a<"soupes35f3o8u","wO2tgO85KlN85ezLED8oi5542wyrFBgzragB3mBT8lM=",-7909835458537895590,5996942726278031782,5452297041183981537,4095740462462440654>()) {
                  case 1129783723:
                     a(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s1wwlezegur8yd","u7hBKcEki1qx4o4QWHnRJx9343Mf7w5h9/OV8eFT3+k=",3191119199830192325,7865917843159800595,-1398070630040111504,5916348305583379133>()) {
                        case -1329255107:
                           return var4;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var4;
         default:
            throw null;
      }
   }
}
