package com.yiyiaddon.j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.m.b;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Stream;

public final class a {
   private static final Gson d = new GsonBuilder().setPrettyPrinting().create();

   private a() {
   }

   public static List<Path> a(Path var0) {
      if (var0 != null && Files.isDirectory(var0)) {
         try (Stream var1 = Files.list(var0)) {
            return var1.filter(var0x -> Files.isRegularFile(var0x))
               .filter(
                  var0x -> var0x.getFileName()
                     .toString()
                     .endsWith(
                        (String)b.a<"sjkds0xwp4weu","6tFEavFhK9bOwSu++IYGWLM8UNaRFhk8Bnp/UrYZBpHU9OY7Hrw=",2512205343792434844,-5391815870834613411,7114210992382663702,2900557877283853834>()
                     )
               )
               .toList();
         } catch (Exception var6) {
            return List.of();
         }
      } else {
         return List.of();
      }
   }

   public static List<JsonObject> b(Path var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = a(var0).iterator();
      switch ((int)b.a<"s37lnnrf49hlh7","Im49tb0SytuDRj4KYq9WYj7A7Gx0TfhiGU9/lJzVQNE=",-1504623750025299663,985276154240171829,-3233535249925078135,-3003395961379624426>()) {
         case 807287557:
            while (var2.hasNext()) {
               switch ((int)b.a<"skuzi2jt2a12g","/97Nw6Ts79nQph4kMiEk1ER2kxT57wNKDOuDDkM52uo=",-398741911773767490,2882121685484415923,8334722831549523190,-3819912461009477710>()) {
                  case -806747081:
                     Path var3 = (Path)var2.next();
                     JsonObject var4 = a(var3);
                     if (var4 != null) {
                        label23:
                        switch ((int)b.a<"s1m7llg8drlxdh","PaP5wjqxrnXwoLiRJSM34BOddhGuHQYbwDPs+EsBkNI=",-2896048492876881340,5594036478506003879,-7403272053056661133,-5316441273471834851>()) {
                           case -797480269:
                              var1.add(var4);
                              switch ((int)b.a<"s2ugu25cg7pha8","7K0MIZh05i/qitwgcstS93gxB7EbgfixkK1VSVNUqfI=",1570500895745932158,5732641568108737362,8329721489588051121,97199223035701732>()) {
                                 case 335792614:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"sorhrwxx2zvwh","wfxw0KbIibm6u1VgNQjMqmyWICQFvXd3X/GKuWL8kkc=",-4193594782949385560,7313819536102690245,6419082949922601683,2230932352763365643>()) {
                        case -816348406:
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

   public static JsonObject a(Path var0) {
      try {
         String var1 = Files.readString(var0, StandardCharsets.UTF_8);
         JsonElement var2 = JsonParser.parseString(var1);
         return var2 != null && var2.isJsonObject() ? var2.getAsJsonObject() : null;
      } catch (Exception var3) {
         return null;
      }
   }

   public static long a(Path var0) {
      try {
         return Files.getLastModifiedTime(var0).toMillis();
      } catch (Exception var2) {
         return 0L;
      }
   }

   public static Path a(Path var0, String var1) {
      Path var2 = var0.resolve(var1 + "");
      int var3 = 2;
      switch ((int)b.a<"s2as4fojsdkak2","H3WXW7vUxT7XewGBTVh+wmjvaP3wh7C/EAqiVJ5PPPg=",-5677113877121654658,-4460283490192870971,-4699572214529088703,-2813882005760707353>()) {
         case -1376406638:
            while (Files.exists(var2)) {
               switch ((int)b.a<"s27dbn19k472oq","OA34I1nqA7B7XNehMYekLX364XNx8JYJcxoNpidg8l0=",2193515154673667421,3148290133078985403,-7258612067587105039,7649861944279075338>()) {
                  case -1912938905:
                     var2 = var0.resolve(var1 + var3);
                     var3++;
                     switch ((int)b.a<"swyrlny5pv9ch","iC/nU+0IOdQaLsaJQ4vp48TWZ4Wy54EYdgNrq4/XcGA=",-989143719986631615,-2166414785169112745,-7062456858740236382,8861659475351092215>()) {
                        case 1046293967:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var2;
         default:
            throw null;
      }
   }

   public static String a(String var0, Set<String> var1) {
      String var2 = var0 + "";
      int var3 = 2;
      switch ((int)b.a<"s1mor28cnuxyti","gzb2YsqGIHqZUh41z93wGzYnGIyRG9n5VRzWkh2zvdc=",5224693842102190133,8565575668568757100,4578469651459298106,1834354857116000794>()) {
         case 183878096:
            while (var1.contains(var2)) {
               switch ((int)b.a<"s3cf0iwnvif7g","gY4qQTSxPpAgCipC8d8h/DAd0osV5je+nhsUc75TKfw=",7170841738377476742,-2805461180106551962,8631344372582910772,-2476266731355005139>()) {
                  case -943830917:
                     var2 = var0 + var3++;
                     switch ((int)b.a<"s2u5aw7hgvrnx9","sZPhvMgTNOLVLf8XzoSfSFcrCUnU5hrcq3cukh2SzBw=",-3051685842839043523,-6971617336184064006,815315507492916033,4975782220210955638>()) {
                        case -584822363:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.add(var2);
            return var2;
         default:
            throw null;
      }
   }

   public static boolean a(Path var0, JsonObject var1) {
      if (var0 != null && var1 != null) {
         Path var2 = var0.resolveSibling("" + var0.getFileName() + UUID.randomUUID());

         try {
            Files.createDirectories(var0.getParent());
            Files.writeString(var2, d.toJson(var1), StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            a(var2, var0);
            return true;
         } catch (Exception var4) {
            e(var2);
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean a(Path var0, List<com.yiyiaddon.j.a.a> var1, String var2) {
      if (var0 == null) {
         return false;
      }

      Map var3 = a(var0);
      if (var3 == null) {
         return false;
      }

      LinkedHashSet var4 = new LinkedHashSet();
      ArrayList var5 = new ArrayList();

      for (com.yiyiaddon.j.a.a var7 : var1) {
         var5.add(new com.yiyiaddon.j.a.a(a(var7.bg(), var4), var7.a()));
      }

      Path var14 = var0.resolveSibling(var2 + UUID.randomUUID());
      LinkedHashSet var15 = new LinkedHashSet();

      try {
         Files.createDirectories(var14);

         for (com.yiyiaddon.j.a.a var9 : var5) {
            if (!a(var14.resolve(var9.bg()), var9.a())) {
               throw new IOException(
                  (String)b.a<"s1lnvex27x5av3","wWTIz3fnKC40A7FILOCtN8uAYH/G3Z9sxECiGXsLaZniwo/+k0dChAoX6T4=",4037873987234499085,9101556737996698912,-4173989000853828778,-1493542166704807498>()
               );
            }

            var15.add(var0.resolve(var9.bg()));
         }

         Files.createDirectories(var0);

         try (Stream var16 = Files.list(var14)) {
            for (Path var10 : var16.toList()) {
               a(var10, var0.resolve(var10.getFileName().toString()));
            }
         }

         for (Path var19 : var3.keySet()) {
            if (!var15.contains(var19)) {
               Files.deleteIfExists(var19);
            }
         }

         e(var14);
         return true;
      } catch (Exception var13) {
         a(var0, var3);
         e(var14);
         return false;
      }
   }

   public static boolean b(Path var0) {
      if (var0 != null && Files.isDirectory(var0)) {
         boolean var1 = true;

         for (Path var3 : a(var0)) {
            try {
               Files.deleteIfExists(var3);
            } catch (Exception var5) {
               var1 = false;
            }
         }

         return var1;
      } else {
         return true;
      }
   }

   public static void a(Path var0, Path var1) throws IOException {
      try {
         Files.move(var0, var1, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
      } catch (AtomicMoveNotSupportedException var3) {
         Files.move(var0, var1, StandardCopyOption.REPLACE_EXISTING);
      }
   }

   private static Map<Path, byte[]> a(Path var0) {
      HashMap var1 = new HashMap();

      for (Path var3 : a(var0)) {
         try {
            var1.put(var3, Files.readAllBytes(var3));
         } catch (Exception var5) {
            return null;
         }
      }

      return var1;
   }

   private static void a(Path var0, Map<Path, byte[]> var1) {
      try {
         Files.createDirectories(var0);
      } catch (Exception var7) {
         return;
      }

      for (Path var3 : a(var0)) {
         if (!var1.containsKey(var3)) {
            try {
               Files.deleteIfExists(var3);
            } catch (Exception var6) {
            }
         }
      }

      for (Entry var9 : var1.entrySet()) {
         try {
            Files.write((Path)var9.getKey(), (byte[])var9.getValue());
         } catch (Exception var5) {
         }
      }
   }

   public static void e(Path var0) {
      if (var0 != null && Files.exists(var0)) {
         try (Stream var1 = Files.walk(var0)) {
            var1.sorted(Comparator.reverseOrder()).forEach(var0x -> {
               try {
                  Files.deleteIfExists(var0x);
               } catch (Exception var2) {
               }
            });
         } catch (Exception var6) {
         }
      }
   }

   public static Map<Path, byte[]> b(Path var0) {
      return a(var0);
   }

   public static void b(Path var0, Map<Path, byte[]> var1) {
      if (var1 == null) {
         switch ((int)b.a<"s2ozfqajsxu7uz","h7JnEHloO+cdKP4HCJC/5C5FHampuefDHQzx/T/A898=",5029173917900930823,615382551469213741,3965412564686572333,-4246605683570632249>()) {
            case 166972760:
               return;
            default:
               throw null;
         }
      } else {
         a(var0, var1);
      }
   }

   public static boolean a(Path var0, Path var1) {
      if (var0 != null && var1 != null && Files.isDirectory(var0)) {
         boolean var2 = true;

         try {
            Files.createDirectories(var1);
         } catch (Exception var7) {
            return false;
         }

         for (Path var4 : a(var0)) {
            try {
               Files.copy(var4, var1.resolve(var4.getFileName().toString()), StandardCopyOption.COPY_ATTRIBUTES);
            } catch (Exception var6) {
               var2 = false;
            }
         }

         return var2;
      } else {
         return false;
      }
   }

   public static Map<Path, JsonObject> c(Path var0) {
      LinkedHashMap var1 = new LinkedHashMap();
      Iterator var2 = a(var0).iterator();
      switch ((int)b.a<"s28e0s5drpcexd","lZDuzd48K9dbS/eRrDHMLnFl0z70WOeYvgUKX4ZpUHI=",8057620627975992572,5269889471703854055,-4074012438141476491,-6843400057868837567>()) {
         case 433139979:
            while (var2.hasNext()) {
               switch ((int)b.a<"s1c5a1gteqsmo6","6N+HjieiyjmJd/r7ABddS5gEs2Rn21/X9xRRPgD9LRk=",4308568300480699417,3591608102188743856,-2279159443991526848,-8004790760156523320>()) {
                  case -2031845343:
                     Path var3 = (Path)var2.next();
                     JsonObject var4 = a(var3);
                     if (var4 != null) {
                        label23:
                        switch ((int)b.a<"s2f3grp1tdbjup","+zHhmZZHedTLsQI8Bzqj7sYpJFlYD+KyAleI4bSw0B4=",5492344050098672958,-3017526276947188384,6115504320411728503,-7283261579167535796>()) {
                           case 2064781181:
                              var1.put(var3, var4);
                              switch ((int)b.a<"s2x3es4uo7rmx8","qLSzhjUzY+Co0nKFDPKfJIXl8G90d4P3kOAQsB6KsCo=",-6520701010038707275,4522212975908032967,7786134831511304806,-5637871581351660352>()) {
                                 case -564476577:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"s29xkffa1ia4tf","am8FeMhZ8+QOdfZu380JgdeXi/tflADepLhjv4UHe2s=",4564847897532644630,1178109478640574218,1855576076989664885,304314736851085735>()) {
                        case -348603965:
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

   public record a(String El, JsonObject b) {
      public String bg() {
         return this.El;
      }

      public JsonObject a() {
         return this.b;
      }
   }
}
