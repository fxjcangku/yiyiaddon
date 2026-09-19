package com.yiyiaddon.e.n.o;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;

public final class c {
   private static final Path l = Minecraft.getInstance()
      .gameDirectory
      .toPath()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s2k7bycbq7n7cm","EmB+jQkkFzSKF45UVydim0siHryWgEs3Qjs/7tlp1+VyMbgrynax++2KDiiyiBK7uKg=",-760109929172729024,-800591455060347617,4892099124717959204,8619741217610020055>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s1yhubp5hnrliw","nXoXzU6KlwvAKNci9A7QLhBjxgLEH+9VRGg7n/tft+xJYXQsToJeuteu+3Ndjg==",-7361598280529784632,2239398413346603743,-2888537034991870989,2803771601789993793>()
      );

   private c() {
   }

   public static Map<String, List<String>> d(String var0) {
      LinkedHashMap var1 = new LinkedHashMap();
      if (var0 != null && !var0.isBlank()) {
         Path var2 = b(var0);
         if (!Files.isRegularFile(var2)) {
            return var1;
         }

         try {
            JsonElement var3 = JsonParser.parseString(Files.readString(var2, StandardCharsets.UTF_8));
            if (!var3.isJsonObject()) {
               return var1;
            }

            JsonElement var4 = var3.getAsJsonObject()
               .get(
                  (String)com.yiyiaddon.m.b.a<"s2ablslsiod1tb","S+IdmtlLKcR2/8dWnK3HLKPl5qpuez1V3/QR9UOhtUo=",1096049535404042366,2849978490062481182,-4057461554700747480,-4888748382136643986>()
               );
            if (var4 != null && var4.isJsonObject()) {
               for (Entry var6 : var4.getAsJsonObject().entrySet()) {
                  if (((JsonElement)var6.getValue()).isJsonArray()) {
                     ArrayList var7 = new ArrayList();

                     for (JsonElement var9 : ((JsonElement)var6.getValue()).getAsJsonArray()) {
                        if (var9.isJsonPrimitive()) {
                           String var10 = var9.getAsString();
                           if (var10 != null && !var10.isBlank() && !var7.contains(var10)) {
                              var7.add(var10);
                           }
                        }
                     }

                     var1.put((String)var6.getKey(), var7);
                  }
               }

               return var1;
            } else {
               return var1;
            }
         } catch (Exception var11) {
            return var1;
         }
      } else {
         return var1;
      }
   }

   public static boolean a(String var0, d var1, List<String> var2) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jwnsqce7bow","BN/kGwbuC313bSmxTKPUqpIieqozgJ5qxROaH8k1kGg=",7748267666779749398,-543216409509106961,8538679139677214002,5604540837550840486>()) {
            case 660509497:
               return false;
            default:
               throw null;
         }
      } else {
         return a(var0, var1.name(), var2);
      }
   }

   public static boolean a(String var0, String var1, List<String> var2) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17rfrf0cgckqy","bblUyNSceNCrs6ARuh/cU7SA4Yr2z1Xbc0Ne2IG7xbs=",-2138349092911682973,4761011776450018923,1446874400310451470,-7796900638219437858>()) {
            case -1679298989:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ntcx2rr2zeoh","bs0niAwMHdY7B1azFutK1KNjIJg+zsAWXX1+Z4rnxQQ=",9164887883788941435,7815906682297886327,-7238673355514943149,-1893234705967894963>()) {
                     case -1904991166:
                        if (var1 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s24gdrbao3z5c9","Je/By5g1hoRTSGBxVmei0/aBUBd73Y3mzPvr4mjcE4s=",3492140372878332422,-6191773061943829229,1171257219792281822,7415562537770884619>()) {
                              case -1764918945:
                                 if (!var1.isBlank()) {
                                    Map var3 = d(var0);
                                    ArrayList var10002;
                                    if (var2 == null) {
                                       label58:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3fi7ocqn2p06o","gK6FlUk/Wzq4kI5WKEurmz/bPSXXElxlfqHcDHEARqY=",-312152646514068962,4724392694180543895,1291307952086155252,8386959508565491878>()) {
                                          case -1261655897:
                                             var10002 = new ArrayList();
                                             switch ((int)com.yiyiaddon.m.b.a<"stbr1qsf3tozk","TiROVaZp8LsWgsANufJmAA4mATEkigR8+M1FyTGpVKo=",-8994442841884141105,-3984651487127704579,-7295713096708942256,973954335401055536>()) {
                                                case 1268004257:
                                                   break label58;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var10002 = new ArrayList(var2);
                                       switch ((int)com.yiyiaddon.m.b.a<"sl7lve3y1scc0","rWIykQVvjM7OVW/ccvIvNeEnmmcfxqBHebEb3DA6ZNQ=",-7279534342809712783,-6104788399956032324,-8548979569882731614,-7002244964447322322>()) {
                                          case -501850349:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    var3.put(var1, var10002);
                                    JsonObject var4 = new JsonObject();
                                    Iterator var5 = var3.entrySet().iterator();
                                    switch ((int)com.yiyiaddon.m.b.a<"s1ejkw95jg8ryv","+tBoYQd9dcVHnJVtfkNFgPN80HowB1twkTsIqfvPVlU=",631720774170564216,6805998337921210090,5143534835510967597,-1026211917800526265>()) {
                                       case -74080510:
                                          while (var5.hasNext()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"srtdg9y8jmg54","TLrzEMDcdh1J6AV0wvfZvLcarszsZJkqXyFDTKE46rs=",3823514282891713765,-5348033787207034106,-7953126952338675355,-7128140720940724914>()) {
                                                case 1111737208:
                                                   Entry var6 = (Entry)var5.next();
                                                   JsonArray var7 = new JsonArray();
                                                   Iterator var8 = ((List)var6.getValue()).iterator();
                                                   switch ((int)com.yiyiaddon.m.b.a<"sxwol3ue8aso9","VwIjPyyLoICqNF19CnKjJyH87ICnbpOMGbDZ30w10UE=",3839413242530143201,6107348431368859465,-2312147519068232519,798312612923472766>()) {
                                                      case 772530538:
                                                         while (var8.hasNext()) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1mf189t1gbppf","q+4GHd+9LT05wCYwPgiHoKGYvN5q5yK39gssPuFPE9U=",7947888790123112843,-6980442604256821692,-958913452194462630,5526116833570142106>()) {
                                                               case -1174679592:
                                                                  String var9 = (String)var8.next();
                                                                  var7.add(var9);
                                                                  switch ((int)com.yiyiaddon.m.b.a<"sprtc2z1b9s20","2ebALjARKQcvPm6LTxtEFUdlQEm5yfzbqX4kMxTkrik=",3099926414672271256,-1435230828251246778,-1865597152649995256,-4216530489284921826>()) {
                                                                     case 1480706391:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         var4.add((String)var6.getKey(), var7);
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1ozfog806fns7","bJEuI/fJ0rErw2yOmT/ThDRQRa37CasWG9y+pZ/2sE0=",-3384012648095923947,-8146495927515080543,-7554020444755114189,-649147877002419960>()) {
                                                            case -430823485:
                                                               continue;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          JsonObject var10 = new JsonObject();
                                          var10.addProperty(
                                             (String)com.yiyiaddon.m.b.a<"s2dgiv3gx44hxv","E8q2lyfLvNl0Sb73bimQJv/2J6mRezGdm78jlrTAwb3PQQ==",8021188124366979729,-6229809354594567234,269924574608586489,6177682491447061684>(),
                                             var0
                                          );
                                          var10.add(
                                             (String)com.yiyiaddon.m.b.a<"s2ablslsiod1tb","S+IdmtlLKcR2/8dWnK3HLKPl5qpuez1V3/QR9UOhtUo=",1096049535404042366,2849978490062481182,-4057461554700747480,-4888748382136643986>(),
                                             var4
                                          );
                                          return com.yiyiaddon.j.a.a(b(var0), var10);
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3ftys45blxll8","ntWnsolkz5INOzoZoVnU+ERgzPGKGZn19QH/I/7qHf4=",-5843537297179294504,-2547013360984140874,8647443379451896494,2864349989475961415>()) {
                                    case -206081925:
                                       return false;
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
               break;
            default:
               throw null;
         }
      }

      return false;
   }

   private static Path b(String var0) {
      String var1 = var0.replaceAll(
         (String)com.yiyiaddon.m.b.a<"s3gtat522cdq6t","ocJLmep2TIZ11taL1qJJwvff1EkLyO8DAyqJwym0pJB8nFKXqGm49KmfPw4ng9J9+hiDJA==",-4773495643243465919,7722267083696810148,-1201110534696851282,-6829375944999363922>(),
         (String)com.yiyiaddon.m.b.a<"s19gqg0ubl3asa","8w9w7Z1OVcCk1h1KLHJWcr+Irt7QijnJRh9ppTzZ",4245624537632037128,6938034762646193338,5059621756978411172,-4016655969078299006>()
      );
      return l.resolve(var1 + "");
   }
}
